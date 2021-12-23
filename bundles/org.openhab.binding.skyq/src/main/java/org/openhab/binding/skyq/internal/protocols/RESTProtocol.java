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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.openhab.binding.skyq.internal.models.Favorite;
import org.openhab.binding.skyq.internal.models.SkyChannel;
import org.openhab.binding.skyq.internal.models.SystemInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * @author andan - Initial contribution
 */
public class RESTProtocol {

    private final Logger logger = LoggerFactory.getLogger(RESTProtocol.class);

    static final int PROTOCOL_TIMEOUT = 1000;
    public static final int DEFAULT_PORT = 9006;
    static final String REST_BASE_URL_PATTERN = "http://{0}:{1}/as/";
    static final String REST_CHANNEL_LIST = "services";
    static final String REST_FAVORITE_LIST = "services/favourites";
    static final String REST_SYSTEM_INFORMATION = "system/information";
    public static final String PRESET_REFRESH = "--REFRESH--";

    private final String host;
    private final int port;
    protected String baseUrl;
    private final HttpClient httpClient;
    private final Gson gson = new Gson();

    public enum PowerStatus {
        ON,
        OFF,
        STANDBY
    };

    public RESTProtocol(String host, HttpClient httpClient) {
        this(host, DEFAULT_PORT, httpClient);
    }

    public RESTProtocol(String host, int port, HttpClient httpClient) {
        this.host = host;
        this.port = port;
        this.baseUrl = MessageFormat.format(REST_BASE_URL_PATTERN, host, Integer.toString(port));
        this.httpClient = httpClient;
    }

    public SystemInformation getSystemInformation() {
        try {
            ContentResponse res = httpClient.GET(baseUrl + REST_SYSTEM_INFORMATION);
            if (res.getStatus() == HttpStatus.OK_200) {
                SystemInformation systemInformation = gson.fromJson(res.getContentAsString(), SystemInformation.class);
                return systemInformation;
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            // logger.error("{}", e.getMessage(), e);
            return null;
        }
        return null;
    }

    public List<SkyChannel> getChannels() {
        try {
            ContentResponse res = httpClient.GET(baseUrl + REST_CHANNEL_LIST);
            if (res.getStatus() == HttpStatus.OK_200) {
                JsonElement jsonElement = JsonParser.parseString(res.getContentAsString());
                List<SkyChannel> channels = gson.fromJson(
                        jsonElement.getAsJsonObject().get("services").getAsJsonArray(),
                        new TypeToken<List<SkyChannel>>() {
                        }.getType());
                return channels;
            }
            // logger.info("{}", res.getContentAsString());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("{}", e.getMessage(), e);
        }
        // return empty list in case of error
        return new ArrayList<SkyChannel>();
    }

    public List<Favorite> getFavorites() {
        try {
            ContentResponse res = httpClient.GET(baseUrl + REST_FAVORITE_LIST);
            if (res.getStatus() == HttpStatus.OK_200) {
                JsonElement jsonElement = JsonParser.parseString(res.getContentAsString());
                List<Favorite> favorites = gson.fromJson(
                        jsonElement.getAsJsonObject().get("favourites").getAsJsonArray(),
                        new TypeToken<List<Favorite>>() {
                        }.getType());
                return favorites;
            }
            // logger.info("{}", res.getContentAsString());
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            logger.error("{}", e.getMessage(), e);
        }
        // return empty list in case of error
        return new ArrayList<Favorite>();
    }
}
