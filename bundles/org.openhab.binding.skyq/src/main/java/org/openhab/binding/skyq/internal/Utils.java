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
import org.eclipse.jdt.annotation.Nullable;

/**
 * Container for static utility methods
 *
 * @author Andreas - Initial contribution
 */
@NonNullByDefault
public class Utils {
    public static String defaultIfEmpty(final @Nullable String str, final @Nullable String defStr) {
        return str != null && !str.isEmpty() ? str : (defStr != null ? defStr : "");
    }
}
