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

import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.openhab.core.io.net.http.HttpClientFactory;
import org.openhab.core.io.net.http.WebSocketFactory;
import org.openhab.core.thing.Thing;
import org.openhab.core.thing.ThingTypeUID;
import org.openhab.core.thing.binding.BaseThingHandlerFactory;
import org.openhab.core.thing.binding.ThingHandler;
import org.openhab.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The {@link SkyQHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Andreas - Initial contribution
 */
@NonNullByDefault
@Component(configurationPid = "binding.skyq", service = ThingHandlerFactory.class)
public class SkyQHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Set.of(THING_TYPE_SKYQ);
    private final WebSocketClient webSocketClient;
    private final HttpClient httpClient;
    private final SkyQStateDescriptionOptionProvider stateDescriptionProvider;

    @Activate
    public SkyQHandlerFactory(final @Reference WebSocketFactory webSocketFactory,
            final @Reference HttpClientFactory httpClientFactory,
            final @Reference SkyQStateDescriptionOptionProvider stateDescriptionProvider) {
        this.webSocketClient = webSocketFactory.getCommonWebSocketClient();
        this.httpClient = httpClientFactory.getCommonHttpClient();
        this.stateDescriptionProvider = stateDescriptionProvider;
    }

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_SKYQ.equals(thingTypeUID)) {
            return new SkyQHandler(thing, webSocketClient, httpClient, stateDescriptionProvider);
        }

        return null;
    }
}
