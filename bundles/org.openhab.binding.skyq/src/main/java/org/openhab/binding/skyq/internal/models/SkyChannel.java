package org.openhab.binding.skyq.internal.models;

import com.google.gson.annotations.SerializedName;

public class SkyChannel {

    @SerializedName(value = "c")
    public String dispNumner;

    @SerializedName(value = "t")
    public String title;

    @SerializedName(value = "sf")
    public String format;
}
