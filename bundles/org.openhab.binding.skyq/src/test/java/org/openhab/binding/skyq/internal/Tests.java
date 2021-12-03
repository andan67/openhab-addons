package org.openhab.binding.skyq.internal;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.eclipse.jdt.annotation.Nullable;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class Tests {


    @Test
    public void testXML2Description() {

        XStream xStream = new XStream();
        xStream.allowTypesByWildcard(new String[] { SkyQHandlerFactory.class.getPackageName() + ".**" });
        xStream.setClassLoader(getClass().getClassLoader());
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(Tests.class.getClasses());
        String xml =
                "<?xml version=\"1.0\"?>\n" +
                "<root xmlns=\"urn:schemas-upnp-org:device-1-0\">\n" +
                "    <specVersion>\n" +
                "        <major>1</major>\n" +
                "        <minor>0</minor>\n" +
                "    </specVersion>\n" +
                "    <device>\n" +
                "        <deviceType>urn:schemas-nds-com:device:GatewaySkyControl:2</deviceType>\n" +
                "        <modelDescription>6763A3</modelDescription>\n" +
                "        <modelName>Sky+HD</modelName>\n" +
                "        <modelNumber>Q180.000.16.01L (5f5ezi9)</modelNumber>\n" +
                "        <friendlyName>BSKYB_GATEWAY</friendlyName>\n" +
                "        <manufacturer>HUMAX</manufacturer>\n" +
                "        <nds:X_NDS_ChipID xmlns:nds=\"urn:schemas-nds-com:device-1-0\">2631057532</nds:X_NDS_ChipID>\n" +
                "        <nds:X_NDS_Configuration_Manifest xmlns:nds=\"urn:schemas-nds-com:device-1-0\">http://192.168.178.23:0//</nds:X_NDS_Configuration_Manifest>\n" +
                "        <nds:X_NDS_PortNumber xmlns:nds=\"urn:schemas-nds-com:device-1-0\">1234</nds:X_NDS_PortNumber>\n" +
                "        <nds:X_NDS_DSSURI xmlns:nds=\"urn:schemas-nds-com:device-1-0\">http://192.168.178.23:8080</nds:X_NDS_DSSURI>\n" +
                "        <nds:X_NDS_LowPowerCapability xmlns:nds=\"urn:schemas-nds-com:device-1-0\">false</nds:X_NDS_LowPowerCapability>\n" +
                "        <nds:X_NDS_StreamingCapable xmlns:nds=\"urn:schemas-nds-com:device-1-0\">false</nds:X_NDS_StreamingCapable>\n" +
                "        <UDN>uuid:444D5276-3247-4761-7465-3438b7405094</UDN>\n" +
                "        <serviceList>\n" +
                "            <service>\n" +
                "                <serviceType>urn:schemas-nds-com:service:SkyPlay:2</serviceType>\n" +
                "                <serviceId>urn:nds-com:serviceId:SkyPlay</serviceId>\n" +
                "                <SCPDURL>/player_avt.xml</SCPDURL>\n" +
                "                <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyPlay</controlURL>\n" +
                "                <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyPlay</eventSubURL>\n" +
                "            </service>\n" +
                "            <service>\n" +
                "                <serviceType>urn:schemas-nds-com:service:SkyCM:2</serviceType>\n" +
                "                <serviceId>urn:nds-com:serviceId:SkyCM</serviceId>\n" +
                "                <SCPDURL>/player_cm.xml</SCPDURL>\n" +
                "                <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyCM</controlURL>\n" +
                "                <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyCM</eventSubURL>\n" +
                "            </service>\n" +
                "            <service>\n" +
                "                <serviceType>urn:schemas-nds-com:service:SkyRC:2</serviceType>\n" +
                "                <serviceId>urn:nds-com:serviceId:SkyRC</serviceId>\n" +
                "                <SCPDURL>/player_rc.xml</SCPDURL>\n" +
                "                <controlURL>/444D5276-3247-4761-7465-3438b7405094SkyRC</controlURL>\n" +
                "                <eventSubURL>/444D5276-3247-4761-7465-3438b7405094SkyRC</eventSubURL>\n" +
                "            </service>\n" +
                "        </serviceList>\n" +
                "    </device>\n" +
                "    <URLBase>http://192.168.178.23:49153/</URLBase>\n" +
                "</root>";

        ServiceDescription serviceDescription = (ServiceDescription) xStream.fromXML(xml);

        assertEquals(serviceDescription.device.modelName,"Sky+HD");
        assertEquals(serviceDescription.device.serviceList.get(0).serviceType,"urn:schemas-nds-com:service:SkyPlay:2");
    }

    @XStreamAlias("root")
    public static class ServiceDescription {
        @XStreamAlias("device")
        Device device;

        @XStreamAlias("URLBase")
        String urlBase;

    }

    @XStreamAlias("device")
    public static class Device {

        @XStreamAlias("deviceType")
        String deviceType;

        @XStreamAlias("modelName")
        String modelName;

        @XStreamAlias("serviceList")
        private List<Service> serviceList;

    }

    public static class ServiceList {
        @XStreamImplicit
        private @Nullable List<Service> services;
    }

    @XStreamAlias("service")
    public static class Service {
        @XStreamAlias("serviceType")
        String serviceType;

        @XStreamAlias("SCPDURL")
        String SCPDURL;

        @XStreamAlias("controlURL")
        String controlURL;
    }

}
