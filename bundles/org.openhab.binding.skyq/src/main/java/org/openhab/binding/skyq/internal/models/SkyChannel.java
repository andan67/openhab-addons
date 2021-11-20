package org.openhab.binding.skyq.internal.models;

import com.google.gson.annotations.SerializedName;

public class SkyChannel {

    @SerializedName(value = "c")
    public String dispNum;

    @SerializedName(value = "t")
    public String title;

    @SerializedName(value = "sf")
    public String format;

    @SerializedName(value = "servicetype")
    public String servicetype;

    @SerializedName(value = "sg")
    public String servicegroup;

    @SerializedName(value = "sid")
    public String id;
}
