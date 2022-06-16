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
package org.openhab.binding.sony.internal.transports;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.openhab.binding.sony.internal.net.NetUtil;
import org.openhab.binding.sony.internal.scalarweb.gson.GsonUtilities;
import org.openhab.binding.sony.internal.scalarweb.models.ScalarWebMethod;
import org.openhab.binding.sony.internal.scalarweb.models.ScalarWebRequest;
import org.openhab.binding.sony.internal.scalarweb.models.ScalarWebService;
import org.openhab.binding.sony.internal.scalarweb.models.api.ActRegisterId;
import org.openhab.binding.sony.internal.scalarweb.models.api.ActRegisterOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * This class represents authorization filter used to reauthorize our sony connection
 *
 * @author Tim Roberts - Initial contribution
 * @author andan - Changed cookie handling
 */
@NonNullByDefault
public class SonyAuthFilter implements ClientRequestFilter, ClientResponseFilter {
    /** The logger */
    private final Logger logger = LoggerFactory.getLogger(SonyAuthFilter.class);

    /** The map to hold authorization cookies per host */
    private static final ConcurrentMap<String, NewCookie> hostAuthCookieMap = new ConcurrentHashMap<>();
    private static final NewCookie EMPTY_COOKIE = new NewCookie("auth", "");

    /** The name of the authorization cookie */
    private static final String AUTHCOOKIENAME = "auth";

    /** The base URL of the access control service */
    private final URI baseUri;

    /** The host for which the access control service applies */
    private final String host;

    /** The function interface to determine whether we should automatically apply the auth logic */
    private final AutoAuth autoAuth;

    /**
     * The clientBuilder used in HttpRequest
     */
    private final ClientBuilder clientBuilder;

    /**
     * A boolean to determine if we've already tried the authorization and failed (true to continue trying, false
     * otherwise)
     */
    private final AtomicBoolean tryAuth = new AtomicBoolean(true);

    /**
     * Instantiates a new sony auth filter from the device information
     *
     * @param baseUri the non-null, base URI for the access control service
     * @param autoAuth the non-null auto auth callback
     * @param clientBuilder the client builder used to request new authorization cookie if needed
     */
    public SonyAuthFilter(final URI baseUri, final AutoAuth autoAuth, final ClientBuilder clientBuilder) {
        Objects.requireNonNull(baseUri, "baseUrl cannot be empty");
        Objects.requireNonNull(autoAuth, "autoAuth cannot be null");
        this.baseUri = baseUri;
        this.autoAuth = autoAuth;
        this.host = baseUri.getHost();
        // this.autoAuth = () -> true;
        this.clientBuilder = clientBuilder;
        logger.debug(
                "Created SonyAuthFilter for host: {}, baseUri: {}, isAutoAuth: {} with initial authorization cookie: {} having expiry date: {}",
                host, baseUri.getPath(), this.autoAuth.isAutoAuth(), hostAuthCookieMap.getOrDefault(host, EMPTY_COOKIE),
                hostAuthCookieMap.getOrDefault(host, EMPTY_COOKIE).getExpiry());
    }

    @Override
    public void filter(final @Nullable ClientRequestContext requestCtx) throws IOException {
        Objects.requireNonNull(requestCtx, "requestCtx cannot be null");

        // get expiry date from cookie and check for expiry
        // Note: ConcurrentHashMap is optimzed for get, so only apply putIfAbsent if necessary
        final Date expiryDate = hostAuthCookieMap.computeIfAbsent(host, k -> EMPTY_COOKIE).getExpiry();
        // NewCookie authCookie = hostAuthCookieMap.getOrDefault(host, EMPTY_COOKIE);
        if (expiryDate == null || new Date().after(expiryDate)) {
            logger.debug("SonyAuthFilter for baseUri: {} Cookie {} expired or null, isAutoAuth {}", baseUri, expiryDate,
                    autoAuth.isAutoAuth());

            if (tryAuth.get() && autoAuth.isAutoAuth()) {
                // request new cookie value for host
                logger.debug("SonyAuthFilter for baseUri: {} Trying to renew our authorization cookie for host: {}",
                        baseUri, host);
                NewCookie newAuthCookie = hostAuthCookieMap.compute(host, (k, v) -> computeAndSetNewAuthCookie());
                if (!EMPTY_COOKIE.equals(newAuthCookie)) {
                    logger.debug("New auth cookie: {} with expiry date: {} for host: {}", newAuthCookie.getValue(),
                            newAuthCookie.getExpiry(), host);
                } else {
                    logger.debug("No authorization cookie was returned");
                }
            }
        }
        requestCtx.getHeaders().putSingle("Cookie", hostAuthCookieMap.get(host).toCookie());
    }

    private NewCookie computeAndSetNewAuthCookie() {
        final Client client = clientBuilder.build();
        final String actControlUrl = NetUtil.getSonyUri(baseUri, ScalarWebService.ACCESSCONTROL);

        final WebTarget target = client.target(actControlUrl);
        final Gson gson = GsonUtilities.getDefaultGson();

        final String json = gson.toJson(new ScalarWebRequest(ScalarWebMethod.ACTREGISTER, ScalarWebMethod.V1_0,
                new ActRegisterId(), new Object[] { new ActRegisterOptions() }));

        try {
            final Response rsp = target.request().post(Entity.json(json));

            final Map<String, NewCookie> newCookies = rsp.getCookies();
            if (newCookies != null) {
                final NewCookie newAuthCookie = newCookies.get(AUTHCOOKIENAME);
                if (newAuthCookie != null) {
                    // create cookie with expiry date using 80% of maxAge given in seconds
                    final Date newExpiryDate = new Date(new Date().getTime() + 800L * newAuthCookie.getMaxAge());
                    final NewCookie newAuthCookieToStore = new NewCookie(newAuthCookie.toCookie(),
                            newAuthCookie.getComment(), newAuthCookie.getMaxAge(), newExpiryDate,
                            newAuthCookie.isSecure(), newAuthCookie.isHttpOnly());
                    return newAuthCookieToStore;
                }
            }
        } catch (final ProcessingException e) {
            if (e.getCause() instanceof ConnectException) {
                tryAuth.set(false);
            }
            logger.debug("Could not renew authorization cookie: {}", e.getMessage());
        }
        return EMPTY_COOKIE;
    }

    @Override
    public void filter(final @Nullable ClientRequestContext requestCtx,
            final @Nullable ClientResponseContext responseCtx) throws IOException {
        Objects.requireNonNull(responseCtx, "responseCtx cannot be null");

        // The response may include an auth cookie that we need to save
        final Map<String, NewCookie> newCookies = responseCtx.getCookies();
        if (newCookies != null && !newCookies.isEmpty()) {
            final NewCookie authCookie = newCookies.get(AUTHCOOKIENAME);
            if (authCookie != null) {
                logger.debug("New auth cookie: {} for host: {}", authCookie.getValue(), host);
                hostAuthCookieMap.put(host, authCookie);
                logger.debug("Auth cookie found and saved");
            }
        }
    }

    /**
     * This is the functional interface to determing if auto auth is needed
     */
    public interface AutoAuth {
        /**
         * Determines if auto auth is needed
         * 
         * @return true if needed, false otherwise
         */
        boolean isAutoAuth();
    }
}
