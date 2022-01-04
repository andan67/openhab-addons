/**
 * Copyright (c) 2010-2021 Contributors to the openHAB project
 * <p>
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 * <p>
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 * <p>
 * SPDX-License-Identifier: EPL-2.0
 */

package org.openhab.binding.skyq.internal.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class represents the deserialized results of an SOAP response
 *
 * <pre>
 * {@code
 * <s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/" s:encodingStyle=
"http://schemas.xmlsoap.org/soap/encoding/">
 *     <s:Body>
 *         <u:GetTransportInfoResponse xmlns:u="nds-comSkyPlay:2">
 *             <CurrentTransportState>PLAYING</CurrentTransportState>
 *             <CurrentTransportStatus>OK</CurrentTransportStatus>
 *             <CurrentSpeed>1</CurrentSpeed>
 *         </u:GetTransportInfoResponse>
 *     </s:Body>
 * </s:Envelope>
 * }
 * </pre>
 *
 * @author andan - Initial contribution
 */
public class TransportInfo {

    @XStreamAlias("CurrentSpeed")
    int currentSpeed;

    @XStreamAlias("CurrentTransportState")
    String currentTransportState;

    @XStreamAlias("CurrentTransportStatus")
    String currentTransportStatus;

    public int getCurrentSpeed() {
        return currentSpeed;
    }

    public String getCurrentTransportState() {
        return currentTransportState;
    }

    public String getCurrentTransportStatus() {
        return currentTransportStatus;
    }
}
