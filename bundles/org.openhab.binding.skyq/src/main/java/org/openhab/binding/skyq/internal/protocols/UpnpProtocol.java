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
package org.openhab.binding.skyq.internal.protocols;

import java.text.MessageFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.openhab.binding.skyq.internal.SkyQHandlerFactory;
import org.openhab.binding.skyq.internal.models.MediaInfo;
import org.openhab.binding.skyq.internal.models.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @author andan - Initial contribution
 */
public class UpnpProtocol {

    private final Logger logger = LoggerFactory.getLogger(UpnpProtocol.class);

    static final int PROTOCOL_TIMEOUT = 1000;

    static final String SOAP_CONTROL_BASE_URL = "http://{0}:49153{1}";
    static final String SOAP_DESCRIPTION_BASE_URL = "http://{0}:49153/description{1}.xml";
    static final String SKY_PLAY_URN = "urn:nds-com:serviceId:SkyPlay";
    static final String SOAP_ACTION = "\"urn:schemas-nds-com:service:SkyPlay:2#{0}\"";
    static final String SOAP_PAYLOAD = "<s:Envelope xmlns:s=''http://schemas.xmlsoap.org/soap/envelope/'' s:encodingStyle=''http://schemas.xmlsoap.org/soap/encoding/''>\n"
            + "   <s:Body>\n" + "       <u:{0} xmlns:u=\"urn:schemas-nds-com:service:SkyPlay:2\">\n"
            + "           <InstanceID>0</InstanceID>\n" + "       </u:{0}>\n" + "   </s:Body>\n" + "</s:Envelope>\n";

    static final String SOAP_RESPONSE = "u:{0}Response";
    static final String SOAP_USER_AGENT = "SKYPLUS_skyplus";
    static final String UPNP_GET_MEDIA_INFO = "GetMediaInfo";
    static final String UPNP_GET_TRANSPORT_INFO = "GetTransportInfo";

    private final String host;
    protected String baseUrl;
    private final HttpClient httpClient;
    private ServiceDescription.Service playService;
    private XStream xStream;

    public UpnpProtocol(String host, HttpClient httpClient) {
        this.host = host;
        this.httpClient = httpClient;

        xStream = new XStream(new StaxDriver());
        xStream.allowTypesByWildcard(new String[] { SkyQHandlerFactory.class.getPackageName() + ".**" });
        xStream.setClassLoader(getClass().getClassLoader());
        xStream.ignoreUnknownElements();
        xStream.processAnnotations(new Class[] { ServiceDescription.class, MediaInfo.class });
        this.findServices();
    }

    public void findServices() {
        playService = findPlayService();
    }

    private ServiceDescription.Service findPlayService() {
        for (int id = 0; id < 50; id++) {
            String url = MessageFormat.format(SOAP_DESCRIPTION_BASE_URL, host, Integer.toString(id));
            Request req = httpClient.newRequest(url);
            req.agent(SOAP_USER_AGENT);
            req.method(HttpMethod.GET);
            try {
                ContentResponse res = req.send();
                if (res.getStatus() == HttpStatus.OK_200) {
                    ServiceDescription serviceDescription = (ServiceDescription) xStream
                            .fromXML(res.getContentAsString());
                    for (ServiceDescription.Service service : serviceDescription.device.serviceList) {
                        if (SKY_PLAY_URN.equals(service.serviceId)) {
                            return service;
                        }
                    }
                }
            } catch (InterruptedException | TimeoutException | ExecutionException ex) {
                break;
            }
        }
        return null;
    }

    public MediaInfo requestMediaInfo() {
        // lazy find play service
        if (playService == null) {
            playService = findPlayService();
        }
        if (playService != null) {
            String url = MessageFormat.format(SOAP_CONTROL_BASE_URL, host, playService.controlURL);
            String soapAction = MessageFormat.format(SOAP_ACTION, UPNP_GET_MEDIA_INFO);
            String soapPayload = MessageFormat.format(SOAP_PAYLOAD, UPNP_GET_MEDIA_INFO);
            Request req = httpClient.newRequest(url);
            req.agent(SOAP_USER_AGENT);
            req.method(HttpMethod.POST);
            req.header("SOAPACTION", soapAction);
            req.header(HttpHeader.CONTENT_TYPE, "text/xml");
            req.content(new StringContentProvider(soapPayload));
            try {
                ContentResponse res = req.send();
                if (res.getStatus() == HttpStatus.OK_200) {
                    MediaInfo mediaInfo = (MediaInfo) xStream.fromXML(res.getContentAsString());
                    return mediaInfo;
                }
            } catch (InterruptedException | TimeoutException | ExecutionException ex) {
                return null;
            }
        }
        return null;
    }
}
