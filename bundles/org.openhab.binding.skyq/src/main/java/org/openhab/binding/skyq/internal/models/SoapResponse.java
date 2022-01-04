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

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class represents the deserialized results of a SOAP response
 *
 * <pre>
 * {@code
 * <s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/" s:encodingStyle=
"http://schemas.xmlsoap.org/soap/encoding/">
 *     <s:Body>
 *         <u:GetMediaInfoResponse xmlns:u="nds-comSkyPlay:2">
 *             <NrTracks>0</NrTracks>
 *             <MediaDuration>00:00:00</MediaDuration>
 *             <CurrentURI>xsi://1332</CurrentURI>
 *             <CurrentURIMetaData>NOT_IMPLEMENTED</CurrentURIMetaData>
 *             <PlayMedium>NONE</PlayMedium>
 *             <RecordMedium>NOT_IMPLEMENTED</RecordMedium>
 *             <WriteStatus>NOT_IMPLEMENTED</WriteStatus>
 *         </u:GetMediaInfoResponse>
 *     </s:Body>
 * </s:Envelope>
 * }
 * </pre>
 *
 * @author andan - Initial contribution
 */
@XStreamAlias("Envelope")
public class SoapResponse {

    @XStreamAlias("Body")
    Body body;

    static class Body {
        @XStreamAlias("GetMediaInfoResponse")
        MediaInfo mediaInfo;

        @XStreamAlias("GetTransportInfoResponse")
        TransportInfo transportInfo;
    }

    public MediaInfo getMediaInfo() {
        return body.mediaInfo;
    }

    public TransportInfo getTransportInfo() {
        return body.transportInfo;
    }
}
