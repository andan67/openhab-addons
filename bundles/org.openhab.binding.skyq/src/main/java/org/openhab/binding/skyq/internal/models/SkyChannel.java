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
public class SkyChannel {

    @SerializedName(value = "c")
    public @Nullable String dispNum;

    @SerializedName(value = "t")
    public @Nullable String title;

    @SerializedName(value = "sf")
    public @Nullable String format;

    @SerializedName(value = "servicetype")
    public @Nullable String servicetype;

    @SerializedName(value = "sg")
    public @Nullable String servicegroup;

    @SerializedName(value = "sid")
    public @Nullable String id;
}
