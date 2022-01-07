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
package org.openhab.binding.skyq.internal.models;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * @author andan - Initial contribution
 */
@NonNullByDefault
public class SystemInformation {

    public enum PowerStatus {
        ON,
        OFF,
        STANDBY
    };

    @SerializedName(value = "IPAddress")
    public @Nullable String ipAddress;

    @SerializedName(value = "MACAddress")
    public @Nullable String macAddress;

    @SerializedName(value = "systemUptime")
    public long systemUptime;

    @SerializedName(value = "activeStandby")
    public boolean activeStandby;
}
