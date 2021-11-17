/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 * <p>
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 * <p>
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * <p>
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.skyq.internal;

import static org.openhab.binding.skyq.internal.SkyQBindingConstants.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.openhab.binding.skyq.internal.protocols.ControlProtocol;
import org.openhab.binding.skyq.internal.protocols.RESTProtocol;
import org.openhab.core.thing.ChannelUID;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingStatus;
import org.openhab.core.thing.ThingStatusDetail;
import org.openhab.core.thing.binding.BaseThingHandler;
import org.openhab.core.types.Command;
import org.openhab.core.types.RefreshType;
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

    private @Nullable SkyQConfiguration config;

    private @Nullable ControlProtocol controlProtocol;
    private @Nullable RESTProtocol restProtocol;

    public SkyQHandler(Thing thing, WebSocketClient webSocketClient, HttpClient httpClient) {
        super(thing);
        this.webSocketClient = webSocketClient;
        this.httpClient = httpClient;
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
                logger.info("Handle command : {}", command.toString());
                if (controlProtocol != null) {
                    controlProtocol.sendCommand(command.toString());
                }
                break;
            case CHANNEL_PRESET:
                logger.info("Handle command : {}", command.toString());
                if (restProtocol != null) {
                    restProtocol.getChannels();
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

    @Override
    protected void updateStatus(final ThingStatus status, final ThingStatusDetail statusDetail,
            final @Nullable String description) {
        super.updateStatus(status, statusDetail, description);
        config = getConfigAs(SkyQConfiguration.class);
        if (status == ThingStatus.ONLINE) {
            restProtocol = new RESTProtocol(config.hostname, httpClient);
            controlProtocol = new ControlProtocol(config.hostname);
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
