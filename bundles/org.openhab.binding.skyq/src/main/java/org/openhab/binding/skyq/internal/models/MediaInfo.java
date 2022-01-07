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

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class represents the deserialized results of an SOAP response
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
@NonNullByDefault
public class MediaInfo {

    @XStreamAlias("NrTracks")
    int nrTracks;

    @XStreamAlias("MediaDuration")
    public @Nullable String mediaDuration;

    @XStreamAlias("CurrentURI")
    public @Nullable String currentURI;

    @XStreamAlias("CurrentURIMetaData")
    public @Nullable String currentURIMetaData;

    @XStreamAlias("PlayMedium")
    public @Nullable String playMedium;

    @XStreamAlias("RecordMedium")
    public @Nullable String recordMedium;

    @XStreamAlias("WriteStatus")
    public @Nullable String writeStatus;
}
