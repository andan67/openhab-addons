/**
 * Copyright (c) 2010-2023 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.skyq.internal;

import static org.openhab.binding.skyq.internal.SkyQBindingConstants.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.openhab.binding.skyq.internal.models.Favorite;
import org.openhab.binding.skyq.internal.models.MediaInfo;
import org.openhab.binding.skyq.internal.models.SkyChannel;
import org.openhab.binding.skyq.internal.models.SystemInformation;
import org.openhab.binding.skyq.internal.models.TransportInfo;
import org.openhab.binding.skyq.internal.protocols.ControlProtocol;
import org.openhab.binding.skyq.internal.protocols.RESTProtocol;
import org.openhab.binding.skyq.internal.protocols.UpnpProtocol;
import org.openhab.core.OpenHAB;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.StringType;
import org.openhab.core.thing.Channel;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.State;
import org.openhab.core.types.StateOption;
import org.openhab.core.types.UnDefType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link SkyQHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Andreas - Initial contribution
 */
@NonNullByDefault
public class SkyQHandler extends BaseThingHandler {

    private final Logger logger = LoggerFactory.getLogger(SkyQHandler.class);

    private final WebSocketClient webSocketClient;
    private final HttpClient httpClient;
    private final SkyQStateDescriptionOptionProvider stateDescriptionProvider;

    private @Nullable SkyQConfiguration config;

    private @Nullable ControlProtocol controlProtocol;
    private @Nullable RESTProtocol restProtocol;
    private @Nullable UpnpProtocol upnpProtocol;

    private Map<@Nullable String, SkyChannel> sidToSkyChannelMap = new LinkedHashMap<>();

    State powerState = UnDefType.UNDEF;
    SystemInformation.PowerStatus powerStatus = SystemInformation.PowerStatus.UNDEF;

    public SkyQHandler(Thing thing, WebSocketClient webSocketClient, HttpClient httpClient,
            SkyQStateDescriptionOptionProvider stateDescriptionProvider) {
        super(thing);
        this.webSocketClient = webSocketClient;
        this.httpClient = httpClient;
        this.stateDescriptionProvider = stateDescriptionProvider;
    }

    /**
     * The refresh state event - will only be created when we are connected.
     */
    private final AtomicReference<@Nullable Future<?>> refreshState = new AtomicReference<>(null);

    /**
     * The retry connection event - will only be created when we are disconnected.
     */
    private final AtomicReference<@Nullable Future<?>> retryConnection = new AtomicReference<>(null);

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        String id = channelUID.getIdWithoutGroup();

        if (command instanceof RefreshType) {
            // TODO handle data refresh
            logger.debug("Refresh command for {}", channelUID);
            return;
        }

        if (controlProtocol == null) {
            return;
        }

        logger.debug("Handle command : {}", command.toString());

        switch (id) {
            case CHANNEL_CONTROL_COMMAND:
                controlProtocol.sendCommand(Arrays.asList(command.toString().split(";")));
                break;
            case CHANNEL_PRESET:
            case CHANNEL_FAVORITES:
                if (restProtocol != null) {
                    if (RESTProtocol.PRESET_REFRESH.equals(command.toString())) {
                        refreshSkyChannels();
                    } else {
                        List<String> commandList = new ArrayList<>();
                        commandList.add("backup");
                        commandList.addAll(Arrays.asList(command.toString().split("")));
                        controlProtocol.sendCommand(commandList);
                    }
                }
                break;
            case CHANNEL_POWER:
                if (controlProtocol != null) {
                    if (command instanceof OnOffType) {
                        if (command.equals(OnOffType.ON) && !powerState.equals(OnOffType.ON)) {
                            // power on via sky command. This prevents powering off if valus of powerState doesn't
                            // represent real state
                            controlProtocol.sendCommand("sky");
                            controlProtocol.sendCommand("sleep");
                            controlProtocol.sendCommand("sleep");
                            controlProtocol.sendCommand("backup");
                            powerState = OnOffType.ON;
                            powerStatus = SystemInformation.PowerStatus.ON;
                        } else if (command.equals(OnOffType.OFF) && powerState.equals(OnOffType.ON)) {
                            controlProtocol.sendCommand("power");
                            powerState = OnOffType.OFF;
                            // immediately after power of the receiver should be in standby mode
                            powerStatus = SystemInformation.PowerStatus.STANDBY;
                        }
                        updateState(new ChannelUID(thing.getUID(), CHANNEL_GROUP_STATUS, CHANNEL_POWER_STATUS),
                                new StringType(powerStatus.toString()));
                    }
                }
                break;
            default:
                break;
        }
        // refresh states from handling event for channel id
        // refreshState(id);
    }

    @Override
    public void initialize() {
        logger.debug("{}: Thing initialize()", thing.getLabel());
        config = getConfigAs(SkyQConfiguration.class);
        initChannels();
        if (config == null || config.hostname == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.CONFIGURATION_ERROR, "Configuration error.");
        } else {
            restProtocol = new RESTProtocol(config.hostname, httpClient);
            upnpProtocol = new UpnpProtocol(config.hostname, httpClient);
            controlProtocol = new ControlProtocol(config.hostname);
            cancel(retryConnection.getAndSet(this.scheduler.submit(this::doConnect)));
        }
    }

    private void doConnect() {
        if (isOnline() && upnpProtocol != null) {
            updateStatus(ThingStatus.UNKNOWN, ThingStatusDetail.NONE, "Initializing ...");
            // The control url for the upnp services may change over time, so get actual values on start-up
            upnpProtocol.findServices();
            updateStatus(ThingStatus.ONLINE);
        } else {
            logger.debug("Device with ip/host {} - not reachable. Giving-up connection attempt",
                    config != null ? config.hostname : "n/a");
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    "Error connecting to device: not reachable");
        }
    }

    private void refreshSkyChannels() {
        logger.debug("Refreshing channels/services from SkyQ");
        if (restProtocol == null) {
            return;
        }

        List<SkyChannel> skyChannels = restProtocol.getChannels();
        if (skyChannels.isEmpty()) {
            return;
        }

        sidToSkyChannelMap = new LinkedHashMap<>();
        skyChannels.forEach(s -> sidToSkyChannelMap.put(s.id, s));

        List<StateOption> stateOptions = new ArrayList<>();

        if (config != null && config.configurablePresets) {
            final Path path = Paths.get(OpenHAB.getUserDataFolder(), "config", "skyq", "channel_presets.csv");
            HashMap<String, Integer> rankMap = new HashMap<>();
            if (Files.exists(path)) {
                try {
                    // regex to parse csv formatted lines (with limitations)
                    String regexCSV = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
                    String regexQuotes = "^\"|\"$";
                    final String content = Files.readString(path);
                    Scanner scanner = new Scanner(content);
                    // skip header row
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        try {
                            final String line = scanner.nextLine();
                            final String[] values = line.split(regexCSV);
                            final String dispNum = values[0].trim().replaceAll(regexQuotes, "");
                            final Integer rank = Integer.parseInt(values[6].trim().replaceAll(regexQuotes, ""));
                            rankMap.put(dispNum, rank);
                        } catch (final Exception ex) {
                            // ignore
                        }
                    }
                    scanner.close();
                } catch (final Exception ex) {
                    logger.debug("Exception '{}' while trying to read from file {}", ex.getMessage(), path);
                }
            }

            List<StateOption> stateOptionsToAdd = skyChannels.stream().map(e -> {
                final String title = e.title != null ? e.title : "";
                final String dispNum = e.dispNum != null ? e.dispNum : "";
                Optional<StateOption> si = Optional.empty();
                if (!dispNum.isEmpty()) {
                    si = Optional.of(new StateOption(dispNum, title));
                }
                return si;
            }).filter(Optional::isPresent).map(Optional::get)
                    .filter(a -> (rankMap.getOrDefault(a.getValue(), Integer.MAX_VALUE)) >= 0)
                    .sorted(Comparator.<StateOption> comparingInt(a -> {
                        Integer r = rankMap.getOrDefault(a.getValue(), 0);
                        return r == 0 ? Integer.MAX_VALUE : r;
                    }).thenComparing(StateOption::getValue)).collect(Collectors.toList());

            stateOptions.addAll(stateOptionsToAdd);

            StringBuilder content = new StringBuilder();
            content.append("Display Number, Title, Format, Service Id, Service Group, Service Type, Rank\n");

            for (final SkyChannel channel : skyChannels) {
                content.append(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n", channel.dispNum,
                        channel.title, channel.format, channel.id, channel.servicegroup, channel.servicetype,
                        rankMap.getOrDefault(channel.dispNum, 0)));
            }

            try {
                Files.createDirectories(path.getParent());
                Files.writeString(path, content.toString());
            } catch (final IOException e) {
                logger.debug("IOException trying to write channel list to path {}", path);
            }

        } else {
            final List<StateOption> stateOptionsToAdd = skyChannels.stream().map(c -> {
                StateOption si = new StateOption(Utils.defaultIfEmpty(c.dispNum, "000"), c.title);
                return si;
            }).collect(Collectors.toList());
            stateOptions.addAll(stateOptionsToAdd);
        }
        // add special refresh option at beginning
        stateOptions.add(0, new StateOption(RESTProtocol.PRESET_REFRESH, RESTProtocol.PRESET_REFRESH));
        stateDescriptionProvider.setStateOptions(
                new ChannelUID(getThing().getUID(), CHANNEL_GROUP_CONTROL, CHANNEL_PRESET), stateOptions);

        // fill favorite channels
        List<Favorite> favorites = restProtocol.getFavorites();
        if (favorites.isEmpty()) {
            return;
        }

        stateOptions = new ArrayList<>();
        final List<StateOption> stateOptionsToAdd = favorites.stream().map(f -> {
            SkyChannel c = sidToSkyChannelMap.get(f.id);
            StateOption si = new StateOption(Utils.defaultIfEmpty(c.dispNum, "000"), c.title);
            return si;
        }).collect(Collectors.toList());
        stateOptions.add(new StateOption(RESTProtocol.PRESET_REFRESH, RESTProtocol.PRESET_REFRESH));
        stateOptions.addAll(stateOptionsToAdd);
        stateDescriptionProvider.setStateOptions(
                new ChannelUID(getThing().getUID(), CHANNEL_GROUP_CONTROL, CHANNEL_FAVORITES), stateOptions);
    }

    @Override
    protected void updateStatus(final ThingStatus status, final ThingStatusDetail statusDetail,
            final @Nullable String description) {
        super.updateStatus(status, statusDetail, description);
        config = getConfigAs(SkyQConfiguration.class);
        if (status == ThingStatus.ONLINE) {
            schedulePolling();
            // scheduleCheckStatus();
        } else if (status == ThingStatus.UNKNOWN) {
            // probably in the process of reconnecting - ignore
            logger.trace("Ignoring thing status of UNKNOWN");
        } else {
            cancel(refreshState.getAndSet(null));
            // cancel(checkStatus.getAndSet(null));

            // don't bother reconnecting - won't fix a configuration error
            if (statusDetail != ThingStatusDetail.CONFIGURATION_ERROR) {
                scheduleReconnect();
            }
        }
    }

    /**
     * This routine is called every time the Thing configuration has been changed
     */
    @Override
    public void handleConfigurationUpdate(Map<String, Object> configurationParameters) {
        super.handleConfigurationUpdate(configurationParameters);
        logger.debug("{}: Thing config updated, re-initialize", thing.getLabel());
    }

    /**
     * Checks if device is online
     *
     * @return true if device is online, otherweise false
     */
    private boolean isOnline() {
        refreshPowerStatus(false);
        return (powerStatus == SystemInformation.PowerStatus.ON
                || powerStatus == SystemInformation.PowerStatus.STANDBY);
    }

    private void scheduleReconnect() {
        if (config != null && config.retryInterval > 0) {
            cancel(retryConnection.getAndSet(this.scheduler.schedule(() -> {
                if (!Thread.currentThread().isInterrupted() && !isRemoved()) {
                    logger.debug("Do reconnect for {}", thing.getLabel());
                    doConnect();
                }
            }, config.retryInterval, TimeUnit.SECONDS)));
        } else {
            logger.debug("Retry connection has been disabled via configuration setting");
        }
    }

    /**
     * Starts the polling process. The polling process will refresh the state of the device if the refresh time (in
     * seconds) is greater than 0. This process will continue until cancelled.
     */
    private void schedulePolling() {
        if (config.refreshInterval > 0) {
            logger.debug("Starting state polling every {} seconds", config.refreshInterval);
            cancel(refreshState.getAndSet(this.scheduler.scheduleWithFixedDelay(new RefreshState(), 0,
                    config.refreshInterval, TimeUnit.SECONDS)));
        } else {
            logger.debug("Refresh not a positive number - polling has been disabled");
        }
    }

    /**
     * This helper class is used to manage refreshing of the state
     */
    private class RefreshState implements Runnable {

        private boolean initial = true;

        @Override
        public void run() {
            // throw exceptions to prevent future tasks under these circumstances
            if (isRemoved()) {
                throw new IllegalStateException("Thing has been removed - ending state polling");
            }
            if (Thread.currentThread().isInterrupted()) {
                throw new IllegalStateException("State polling has been cancelled");
            }

            try {
                if (thing.getStatus() == ThingStatus.ONLINE) {
                    refreshState(initial);
                    initial = false;
                } else {
                    initial = true;
                }
            } catch (final Exception ex) {
                final @Nullable String message = ex.getMessage();
                if (message != null && message.contains("Connection refused")) {
                    logger.debug("Connection refused - device is probably turned off");
                } else {
                    logger.debug("Uncaught exception (refreshstate) : {}", ex.getMessage(), ex);
                }
            }
        }
    }

    private void initChannels() {
        for (Channel channel : thing.getChannels()) {
            ChannelUID channelUID = channel.getUID();
            if (isLinked(channelUID)) {
                String id = channelUID.getIdWithoutGroup();
                switch (id) {
                    case CHANNEL_PRESET:
                        if (config != null && config.configurablePresets) {
                            final List<StateOption> stateOptions = new ArrayList<>();
                            stateOptions.add(0,
                                    new StateOption(RESTProtocol.PRESET_REFRESH, RESTProtocol.PRESET_REFRESH));
                            stateDescriptionProvider.setStateOptions(channelUID, stateOptions);
                        }
                        updateState(channelUID, UnDefType.UNDEF);
                        break;
                    default:
                        updateState(channelUID, UnDefType.UNDEF);
                        break;
                }
            }
        }
    }

    private void refreshPowerStatus(boolean performUpdate) {
        if (restProtocol != null) {
            powerStatus = SystemInformation.PowerStatus.UNDEF;
            powerState = UnDefType.UNDEF;
            SystemInformation systemInformation = restProtocol.getSystemInformation();
            if (systemInformation != null) {
                if (systemInformation.activeStandby) {
                    powerState = OnOffType.OFF;
                    powerStatus = SystemInformation.PowerStatus.STANDBY;
                } else if (systemInformation.ipAddress != null) {
                    powerState = OnOffType.ON;
                    powerStatus = SystemInformation.PowerStatus.ON;
                }
            } else {
                powerState = OnOffType.OFF;
                powerStatus = SystemInformation.PowerStatus.OFF;
            }
            if (performUpdate) {
                updateState(new ChannelUID(thing.getUID(), CHANNEL_GROUP_STATUS, CHANNEL_POWER_STATUS),
                        new StringType(powerStatus.toString()));
                updateState(new ChannelUID(thing.getUID(), CHANNEL_GROUP_CONTROL, CHANNEL_POWER), powerState);
                if (powerStatus == SystemInformation.PowerStatus.UNDEF
                        || powerStatus == SystemInformation.PowerStatus.OFF) {
                    // offline
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, "Could not connect to "
                            + (config != null ? config.hostname : "n/a") + ":" + ControlProtocol.DEFAULT_PORT);
                }
            }
        }
    }

    private void refreshState(boolean initial) {
        refreshState(initial, "");
    }

    private void refreshState(String handledChannel) {
        refreshState(false, handledChannel);
    }

    private void refreshState(boolean initial, String handledChannel) {
        // get current system information
        if (restProtocol != null) {
            refreshPowerStatus(true);
            if (sidToSkyChannelMap.isEmpty()) {
                refreshSkyChannels();
            }
        }
        // get current media info
        if (upnpProtocol != null) {
            @Nullable
            MediaInfo mediaInfo = upnpProtocol.requestMediaInfo();
            if (mediaInfo != null) {
                @Nullable
                String currentURI = mediaInfo.currentURI;
                if (currentURI != null && currentURI.startsWith("xsi://")) {
                    String currentSid = String.valueOf(Integer.parseInt(currentURI.substring(6), 16));
                    SkyChannel skyChannel = sidToSkyChannelMap.get(currentSid);
                    updateState(new ChannelUID(thing.getUID(), CHANNEL_GROUP_STATUS, CHANNEL_CURRENT_CHANNEL_TITLE),
                            new StringType(skyChannel != null ? skyChannel.title : "UNDEF"));
                    updateState(new ChannelUID(thing.getUID(), CHANNEL_GROUP_CONTROL, CHANNEL_PRESET),
                            new StringType(skyChannel != null ? skyChannel.dispNum : "UNDEF"));
                    updateState(new ChannelUID(thing.getUID(), CHANNEL_GROUP_CONTROL, CHANNEL_FAVORITES),
                            new StringType(skyChannel != null ? skyChannel.dispNum : "UNDEF"));
                }
            }
            @Nullable
            TransportInfo transportInfo = upnpProtocol.requestTransportInfo();
            if (transportInfo != null) {
                updateState(new ChannelUID(thing.getUID(), CHANNEL_GROUP_STATUS, CHANNEL_CURRENT_TRANSPORT_STATE),
                        new StringType(transportInfo.currentTransportState != null ? transportInfo.currentTransportState
                                : "UNDEF"));
            }
        }
    }

    private static void cancel(final @Nullable Future<?> future) {
        if (future != null) {
            future.cancel(true);
        }
    }

    /**
     * Helper method to determine if the thing is being removed (or is removed)
     *
     * @return true if removed, false otherwise
     */
    private boolean isRemoved() {
        final ThingStatus status = getThing().getStatus();
        return status == ThingStatus.REMOVED || status == ThingStatus.REMOVING;
    }

    @Override
    public void dispose() {
        super.dispose();
        logger.debug("Disposing {}", thing.getLabel());
        cancel(refreshState.getAndSet(null));
        cancel(retryConnection.getAndSet(null));
        // cancel(checkStatus.getAndSet(null));
    }
}
