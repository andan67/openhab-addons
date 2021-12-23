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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.core.thing.ThingTypeUID;

/**
 * The {@link SkyQBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Andreas - Initial contribution
 */
@NonNullByDefault
public class SkyQBindingConstants {

    private static final String BINDING_ID = "skyq";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_SKYQ = new ThingTypeUID(BINDING_ID, "skyqreceiver");

    // List of all Channel ids
    public static final String CHANNEL_GROUP_CONTROL = "control";
    public static final String CHANNEL_CONTROL_COMMAND = "controlCommand";
    public static final String CHANNEL_PRESET = "channelPreset";
    public static final String CHANNEL_FAVORITES = "channelFavorites";
    public static final String CHANNEL_POWER = "power";

    public static final String CHANNEL_GROUP_STATUS = "statusChannels";
    public static final String CHANNEL_CURRENT_CHANNEL_TITLE = "currentChannelTitle";
    public static final String CHANNEL_POWER_STATUS = "powerStatus";
}
