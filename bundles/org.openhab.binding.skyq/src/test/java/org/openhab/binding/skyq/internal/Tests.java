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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.junit.jupiter.api.Test;
import org.openhab.binding.skyq.internal.models.MediaInfo;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * Various unit test methods
 *
 * @author Andreas - Initial contribution
 */
@NonNullByDefault
public class Tests {

    @Test
    public void testXML2Description() {
        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[] { SkyQHandlerFactory.class.getPackageName() + ".**" });
        xStream.setClassLoader(getClass().getClassLoader());
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(org.openhab.binding.skyq.internal.models.ServiceDescription.class);
        String xml = "<?xml version=\"1.0\"?>\n" + "<root xmlns=\"urn:schemas-upnp-org:device-1-0\">\n"
                + "    <specVersion>\n" + "        <major>1</major>\n" + "        <minor>0</minor>\n"
                + "    </specVersion>\n" + "    <device>\n"
                + "        <deviceType>urn:schemas-nds-com:device:GatewaySkyControl:2</deviceType>\n"
                + "        <modelDescription>6763A3</modelDescription>\n" + "        <modelName>Sky+HD</modelName>\n"
                + "        <modelNumber>Q180.000.16.01L (5f5ezi9)</modelNumber>\n"
                + "        <friendlyName>BSKYB_GATEWAY</friendlyName>\n"
                + "        <manufacturer>HUMAX</manufacturer>\n"
                + "        <nds:X_NDS_ChipID xmlns:nds=\"urn:schemas-nds-com:device-1-0\">2631057532</nds:X_NDS_ChipID>\n"
                + "        <nds:X_NDS_Configuration_Manifest xmlns:nds=\"urn:schemas-nds-com:device-1-0\">http://192.168.178.23:0//</nds:X_NDS_Configuration_Manifest>\n"
                + "        <nds:X_NDS_PortNumber xmlns:nds=\"urn:schemas-nds-com:device-1-0\">1234</nds:X_NDS_PortNumber>\n"
                + "        <nds:X_NDS_DSSURI xmlns:nds=\"urn:schemas-nds-com:device-1-0\">http://192.168.178.23:8080</nds:X_NDS_DSSURI>\n"
                + "        <nds:X_NDS_LowPowerCapability xmlns:nds=\"urn:schemas-nds-com:device-1-0\">false</nds:X_NDS_LowPowerCapability>\n"
                + "        <nds:X_NDS_StreamingCapable xmlns:nds=\"urn:schemas-nds-com:device-1-0\">false</nds:X_NDS_StreamingCapable>\n"
                + "        <UDN>uuid:444D5276-3247-4761-7465-3438b7405094</UDN>\n" + "        <serviceList>\n"
                + "            <service>\n"
                + "                <serviceType>urn:schemas-nds-com:service:SkyPlay:2</serviceType>\n"
                + "                <serviceId>urn:nds-com:serviceId:SkyPlay</serviceId>\n"
                + "                <SCPDURL>/player_avt.xml</SCPDURL>\n"
                + "                <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyPlay</controlURL>\n"
                + "                <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyPlay</eventSubURL>\n"
                + "            </service>\n" + "            <service>\n"
                + "                <serviceType>urn:schemas-nds-com:service:SkyCM:2</serviceType>\n"
                + "                <serviceId>urn:nds-com:serviceId:SkyCM</serviceId>\n"
                + "                <SCPDURL>/player_cm.xml</SCPDURL>\n"
                + "                <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyCM</controlURL>\n"
                + "                <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyCM</eventSubURL>\n"
                + "            </service>\n" + "            <service>\n"
                + "                <serviceType>urn:schemas-nds-com:service:SkyRC:2</serviceType>\n"
                + "                <serviceId>urn:nds-com:serviceId:SkyRC</serviceId>\n"
                + "                <SCPDURL>/player_rc.xml</SCPDURL>\n"
                + "                <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyRC</controlURL>\n"
                + "                <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyRC</eventSubURL>\n"
                + "            </service>\n" + "        </serviceList>\n" + "    </device>\n"
                + "    <URLBase>http://192.168.178.23:49153/</URLBase>\n" + "</root>";

        org.openhab.binding.skyq.internal.models.ServiceDescription serviceDescription = (org.openhab.binding.skyq.internal.models.ServiceDescription) xStream
                .fromXML(xml);

        assertEquals(serviceDescription.device.modelName, "Sky+HD");
        assertEquals(serviceDescription.device.serviceList.get(0).serviceType, "urn:schemas-nds-com:service:SkyPlay:2");
    }

    @Test
    public void testSOAPResponseXML() {
        XStream xStream = new XStream(new StaxDriver());
        xStream.allowTypesByWildcard(new String[] { SkyQHandlerFactory.class.getPackageName() + ".**" });
        xStream.setClassLoader(getClass().getClassLoader());
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(MediaInfo.class);
        String xml = "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"><s:Body>\n"
                + "<u:GetMediaInfoResponse xmlns:u=\"nds-comSkyPlay:2\">\n" + "<NrTracks>0</NrTracks>\n"
                + "<MediaDuration>00:00:00</MediaDuration>\n" + "<CurrentURI>xsi://7A</CurrentURI>\n"
                + "<CurrentURIMetaData>NOT_IMPLEMENTED</CurrentURIMetaData>\n" + "<PlayMedium>NONE</PlayMedium>\n"
                + "<RecordMedium>NOT_IMPLEMENTED</RecordMedium>\n" + "<WriteStatus>NOT_IMPLEMENTED</WriteStatus>\n"
                + "</u:GetMediaInfoResponse>\n" + "</s:Body> </s:Envelope>";
        MediaInfo mediaInfo = (MediaInfo) xStream.fromXML(xml);
        assertEquals(mediaInfo.currentURI, "xsi://7A");
    }
}
