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
package org.openhab.binding.skyq.internal.models;

import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class represents the deserialized results of an UPNP service description XML. The following is an example of the
 * results that will be deserialized (http://192.168.178.23:49153/description1.xml)
 *
 * <pre>
 * {@code
    <?xml version="1.0"?>
    <root xmlns="urn:schemas-upnp-org:device-1-0">
        <specVersion>
            <major>1</major>
            <minor>0</minor>
        </specVersion>
        <device>
            <deviceType>urn:schemas-nds-com:device:GatewaySkyControl:2</deviceType>
            <modelDescription>6763A3</modelDescription>
            <modelName>Sky+HD</modelName>
            <modelNumber>Q180.000.16.01L (5f5ezi9)</modelNumber>
            <friendlyName>BSKYB_GATEWAY</friendlyName>
            <manufacturer>HUMAX</manufacturer>
            <nds:X_NDS_ChipID xmlns:nds="urn:schemas-nds-com:device-1-0">2631057532</nds:X_NDS_ChipID>
            <nds:X_NDS_Configuration_Manifest xmlns:nds=
"urn:schemas-nds-com:device-1-0">http://192.168.178.23:0//</nds:X_NDS_Configuration_Manifest>
            <nds:X_NDS_PortNumber xmlns:nds="urn:schemas-nds-com:device-1-0">1234</nds:X_NDS_PortNumber>
            <nds:X_NDS_DSSURI xmlns:nds="urn:schemas-nds-com:device-1-0">http://192.168.178.23:8080</nds:X_NDS_DSSURI>
            <nds:X_NDS_LowPowerCapability xmlns:nds=
"urn:schemas-nds-com:device-1-0">false</nds:X_NDS_LowPowerCapability>
            <nds:X_NDS_StreamingCapable xmlns:nds="urn:schemas-nds-com:device-1-0">false</nds:X_NDS_StreamingCapable>
            <UDN>uuid:444D5276-3247-4761-7465-3438b7405094</UDN>
            <serviceList>
                <service>
                    <serviceType>urn:schemas-nds-com:service:SkyPlay:2</serviceType>
                    <serviceId>urn:nds-com:serviceId:SkyPlay</serviceId>
                    <SCPDURL>/player_avt.xml</SCPDURL>
                    <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyPlay</controlURL>
                    <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyPlay</eventSubURL>
                </service>
                <service>
                    <serviceType>urn:schemas-nds-com:service:SkyCM:2</serviceType>
                    <serviceId>urn:nds-com:serviceId:SkyCM</serviceId>
                    <SCPDURL>/player_cm.xml</SCPDURL>
                    <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyCM</controlURL>
                    <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyCM</eventSubURL>
                </service>
                <service>
                    <serviceType>urn:schemas-nds-com:service:SkyRC:2</serviceType>
                    <serviceId>urn:nds-com:serviceId:SkyRC</serviceId>
                    <SCPDURL>/player_rc.xml</SCPDURL>
                    <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyRC</controlURL>
                    <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyRC</eventSubURL>
                </service>
            </serviceList>
        </device>
        <URLBase>http://192.168.178.23:49153/</URLBase>
    </root>
 * }
 * </pre>
 *
 * @author andan - Initial contribution
 */

@XStreamAlias("root")
@NonNullByDefault
public class ServiceDescription {
    @XStreamAlias("device")
    public @Nullable Device device;

    @XStreamAlias("URLBase")
    public @Nullable String urlBase;

    public static class Device {

        @XStreamAlias("deviceType")
        public @Nullable String deviceType;

        @XStreamAlias("modelName")
        public @Nullable String modelName;

        @XStreamAlias("serviceList")
        public @Nullable List<Service> serviceList;
    }

    @XStreamAlias("service")
    public static class Service {
        @XStreamAlias("serviceType")
        public @Nullable String serviceType;

        @XStreamAlias("serviceId")
        public @Nullable String serviceId;

        @XStreamAlias("SCPDURL")
        public @Nullable String SCPDURL;

        @XStreamAlias("controlURL")
        public @Nullable String controlURL;

        @XStreamAlias("eventSubURL")
        public @Nullable String eventSubURL;
    }
}
