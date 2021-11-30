/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
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
import org.openhab.binding.skyq.internal.models.SkyChannel;
import org.openhab.binding.skyq.internal.protocols.ControlProtocol;
import org.openhab.binding.skyq.internal.protocols.RESTProtocol;
import org.openhab.core.OpenHAB;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
import org.openhab.core.types.StateOption;
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

    private Map<String, String> sidDispNumMap = new HashMap<>();

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
     * The check status event - will only be created when we are connected.
     */
    private final AtomicReference<@Nullable Future<?>> checkStatus = new AtomicReference<>(null);

    /**
     * The retry connection event - will only be created when we are disconnected.
     */
    private final AtomicReference<@Nullable Future<?>> retryConnection = new AtomicReference<>(null);

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        String id = channelUID.getIdWithoutGroup();

        if (command instanceof RefreshType) {
            // TODO: handle data refresh
            return;
        }

        // TODO: handle command

        // Note: if communication with thing fails for some reason,
        // indicate that by setting the status with detail information:
        // updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
        // "Could not control device at IP address x.x.x.x");
        switch (id) {
            case CHANNEL_CONTROL_COMMAND:
                logger.debug("Handle command : {}", command.toString());
                if (controlProtocol != null) {
                    controlProtocol.sendCommand(command.toString());
                }
                break;
            case CHANNEL_PRESET:
                logger.debug("Handle command : {}", command.toString());
                if (restProtocol != null) {
                    // restProtocol.getChannels();
                    if (RESTProtocol.PRESET_REFRESH.equals(command.toString())) {
                        refreshPresetChannelStateDescription();
                    } else {
                        controlProtocol.sendCommand(Arrays.asList(command.toString().split("")));
                    }
                }
        }
    }

    @Override
    public void initialize() {
        logger.debug("{}: Thing initialize()", thing.getLabel());
        cancel(retryConnection.getAndSet(this.scheduler.submit(this::doConnect)));
    }

    private void doConnect() {
        updateStatus(ThingStatus.UNKNOWN, ThingStatusDetail.NONE, "Initializing ...");
        config = getConfigAs(SkyQConfiguration.class);
        if (isReachable()) {
            restProtocol = new RESTProtocol(config.hostname, httpClient);
            refreshPresetChannelStateDescription();
            /*
             * if (skyChannels != null && !skyChannels.isEmpty()) {
             * final List<StateOption> options = skyChannels.stream().map(c -> new StateOption(c.dispNum, c.title))
             * .collect(Collectors.toList());
             * stateDescriptionProvider.setStateOptions(
             * new ChannelUID(getThing().getUID(), CHANNEL_GROUP_CONTROL, CHANNEL_PRESET),
             * options);
             * }
             */

            controlProtocol = new ControlProtocol(config.hostname);
            updateStatus(ThingStatus.ONLINE);
        } else {
            controlProtocol = null;
            restProtocol = null;
            logger.debug("Device with ip/host {} - not reachable. Giving-up connection attempt", config.hostname);
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                    "Error connecting to device: not reachable");
            return;
        }
    }

    private void refreshPresetChannelStateDescription() {
        List<SkyChannel> skyChannels = restProtocol.getChannels();
        if (skyChannels == null || skyChannels.isEmpty())
            return;
        sidDispNumMap.clear();
        final List<StateOption> stateOptions = new ArrayList<>();

        if (config != null && config.configurablePresets) {
            final Path path = Paths.get(OpenHAB.getUserDataFolder(), "config", "skyq", "channel_presets.csv");
            HashMap<String, Integer> rankMap = new HashMap();
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
                final String title = e.title;
                final String dispNum = e.dispNum;
                Optional<StateOption> si = Optional.empty();
                if (dispNum != null && !dispNum.isEmpty()) {
                    si = Optional.of(new StateOption(dispNum, title));
                }
                sidDispNumMap.put(e.id, e.dispNum);
                return si;
            }).filter(Optional::isPresent).map(Optional::get)
                    .filter(a -> (rankMap.getOrDefault(a.getValue(), Integer.MAX_VALUE)) >= 0)
                    .sorted(Comparator.<StateOption> comparingInt(a -> {
                        Integer r = rankMap.getOrDefault(a.getValue(), 0);
                        return r == 0 ? Integer.MAX_VALUE : r;
                    }).thenComparing(a -> a.getValue())).collect(Collectors.toList());

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
                StateOption si = new StateOption(c.dispNum, c.title);
                sidDispNumMap.put(c.id, c.dispNum);
                return si;
            }).collect(Collectors.toList());
            stateOptions.addAll(stateOptionsToAdd);
        }
        // add special refresh option at beginning
        stateOptions.add(0, new StateOption(RESTProtocol.PRESET_REFRESH, RESTProtocol.PRESET_REFRESH));
        stateDescriptionProvider.setStateOptions(
                new ChannelUID(getThing().getUID(), CHANNEL_GROUP_CONTROL, CHANNEL_PRESET), stateOptions);
    }

    @Override
    protected void updateStatus(final ThingStatus status, final ThingStatusDetail statusDetail,
            final @Nullable String description) {
        super.updateStatus(status, statusDetail, description);
        config = getConfigAs(SkyQConfiguration.class);
        if (status == ThingStatus.ONLINE) {
            scheduleCheckStatus();
        } else if (status == ThingStatus.UNKNOWN) {
            // probably in the process of reconnecting - ignore
            logger.trace("Ignoring thing status of UNKNOWN");
        } else {
            controlProtocol = null;
            cancel(refreshState.getAndSet(null));
            cancel(checkStatus.getAndSet(null));

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
     * Checks if device is reachable
     *
     * @return true if device is reachable, otherweise false
     */
    private boolean isReachable() {
        if (config.hostname != null && !config.hostname.isEmpty()) {
            try (Socket soc = new Socket()) {
                // return InetAddress.getByName(config.hostname).isReachable(500);
                soc.connect(new InetSocketAddress(config.hostname, ControlProtocol.DEFAULT_PORT), 3000);
                return true;
            } catch (IOException ex) {
                return false;
            }
        }
        return false;
    }

    private void scheduleReconnect() {
        if (config.retryInterval > 0) {
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

    private void scheduleCheckStatus() {
        if (config.hostname != null && !config.hostname.isEmpty() && config.checkStatusInterval > 0) {

            cancel(checkStatus.getAndSet(scheduler.schedule(() -> {
                try {
                    if (!Thread.currentThread().isInterrupted() && !isRemoved()) {
                        try (Socket soc = new Socket()) {
                            soc.connect(new InetSocketAddress(config.hostname, ControlProtocol.DEFAULT_PORT), 3000);
                        }
                        logger.debug("Checking connectivity to {}:{} - successful", config.hostname,
                                ControlProtocol.DEFAULT_PORT);
                        scheduleCheckStatus();
                    }
                } catch (final IOException ex) {
                    logger.debug("Checking connectivity to {}:{} - unsuccessful - going offline: {}", config.hostname,
                            ControlProtocol.DEFAULT_PORT, ex.getMessage(), ex);
                    updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR,
                            "Could not connect to " + config.hostname + ":" + ControlProtocol.DEFAULT_PORT);
                }
            }, config.checkStatusInterval, TimeUnit.SECONDS)));
        }
    }

    private static void cancel(final @Nullable Future<?> future) {
        if (future != null) {
            future.cancel(true);
        }
    }

    private static void checkInterrupt() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("thread interrupted");
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
        cancel(checkStatus.getAndSet(null));
    }
}
