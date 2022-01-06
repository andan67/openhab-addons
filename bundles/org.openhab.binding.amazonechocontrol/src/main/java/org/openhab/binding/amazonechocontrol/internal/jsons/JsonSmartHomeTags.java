/**
 * Copyright (c) 2010-2022 Contributors to the openHAB project
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
package org.openhab.binding.amazonechocontrol.internal.jsons;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.amazonechocontrol.internal.jsons.JsonSmartHomeGroupIdentity.SmartHomeGroupIdentity;

/**
 * @author Lukas Knoeller - Initial contribution
 */
@NonNullByDefault
public class JsonSmartHomeTags {
    public static class JsonSmartHomeTag {
        public @Nullable SmartHomeGroupIdentity tagNameToValueSetMap;
    }

    public @Nullable SmartHomeGroupIdentity tagNameToValueSetMap;
}
