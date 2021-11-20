package org.openhab.binding.skyq.internal.protocols;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.openhab.binding.skyq.internal.models.SkyChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class RESTProtocol {

    private final Logger logger = LoggerFactory.getLogger(RESTProtocol.class);

    static final int PROTOCOL_TIMEOUT = 1000;
    public static final int DEFAULT_PORT = 9006;
    static final String REST_BASE_URL_PATTERN = "http://%s:%s/as/";
    static final String REST_CHANNEL_LIST = "services";
    public static final String PRESET_REFRESH = "--REFRESH--";

    private final String host;
    private final int port;
    protected String baseUrl;
    private final HttpClient httpClient;
    private final Gson gson = new Gson();

    public RESTProtocol(String host, HttpClient httpClient) {
        this(host, DEFAULT_PORT, httpClient);
    }

    public RESTProtocol(String host, int port, HttpClient httpClient) {
        this.host = host;
        this.port = port;
        this.baseUrl = String.format(REST_BASE_URL_PATTERN, host, port);
        this.httpClient = httpClient;
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
            logger.info(res.getContentAsString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return new ArrayList<SkyChannel>();
    }
}
