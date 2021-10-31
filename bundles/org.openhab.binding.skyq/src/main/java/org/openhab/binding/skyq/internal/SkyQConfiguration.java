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

import org.eclipse.jdt.annotation.NonNullByDefault;

/**
 * The {@link SkyQConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Andreas - Initial contribution
 */
@NonNullByDefault
public class SkyQConfiguration {
    public static final String HOSTNAME = "hostname";

    /**
     * Sample configuration parameters. Replace with your own.
     */
    public String hostname = "";
    public String deviceMacAddress = "";
    public int refreshInterval;
    public int retryInterval;
    public int checkStatusInterval;
    public boolean configurablePresets;
}