/**
 * Copyright (c) 2010-2024 Contributors to the openHAB project
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
package org.openhab.binding.sony.internal.scalarweb.protocols;

import java.util.Collection;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.openhab.binding.sony.internal.scalarweb.ScalarWebChannel;
import org.openhab.binding.sony.internal.scalarweb.ScalarWebChannelDescriptor;
import org.openhab.binding.sony.internal.scalarweb.models.ScalarWebService;
import org.openhab.binding.sony.internal.scalarweb.models.api.NotifySettingUpdate;
import org.openhab.core.types.Command;

/**
 * The interface definition for all protocols
 *
 * @author Tim Roberts - Initial contribution
 */
@NonNullByDefault
public interface ScalarWebProtocol {
    /**
     * Gets the channel descriptors
     *
     * @param dynamicOnly true to retrieve channel descriptors that are defined as dynamic
     * @return the channel descriptors
     */
    Collection<ScalarWebChannelDescriptor> getChannelDescriptors(final boolean dynamicOnly);

    /**
     * Refresh state
     * 
     * @param initial true if this is the initial refresh state after going online, false otherwise
     */
    void refreshState(final boolean initial);

    /**
     * Refresh channel
     *
     * @param channel the non-null channel
     */
    void refreshChannel(final ScalarWebChannel channel);

    /**
     * Sets the channel
     *
     * @param channel the non-null channel
     * @param command the non-null command
     */
    void setChannel(final ScalarWebChannel channel, final Command command);

    /**
     * Get the underlying service
     * 
     * @return a non-null underlying serivce
     */
    ScalarWebService getService();

    /**
     * Called when notifying the protocol of a settings update
     * 
     * @param setting a non-null setting
     */
    void notifySettingUpdate(final NotifySettingUpdate setting);

    /**
     * Defines a close method to release resources. We do NOT implement AutoCloseable since that forces an exception
     * onto this method (which we don't need)
     */
    void close();
}
