package org.openhab.binding.skyq.internal.protocols;

import static java.util.Map.entry;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControlProtocol {

    private final Logger logger = LoggerFactory.getLogger(ControlProtocol.class);

    static final Map<String, Integer> COMMAND_MAP = Map.ofEntries(entry("power", 0), entry("select", 1),
            entry("backup", 2), entry("dismiss", 2), entry("channelup", 6), entry("channeldown", 7),
            entry("interactive", 8), entry("sidebar", 8), entry("help", 9), entry("services", 10), entry("search", 10),
            entry("tvguide", 11), entry("home", 11), entry("i", 14), entry("text", 15), entry("up", 16),
            entry("down", 17), entry("left", 18), entry("right", 19), entry("red", 32), entry("green", 33),
            entry("yellow", 34), entry("blue", 35), entry("0", 48), entry("1", 49), entry("2", 50), entry("3", 51),
            entry("4", 52), entry("5", 53), entry("6", 54), entry("7", 55), entry("8", 56), entry("9", 57),
            entry("play", 64), entry("pause", 65), entry("stop", 66), entry("record", 67), entry("fastforward", 69),
            entry("rewind", 71), entry("boxoffice", 240), entry("sky", 241));

    static final int PROTOCOL_TIMEOUT = 1000;

    protected String host;
    protected int port;

    public ControlProtocol(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendCommand(String command) {
        sendCommand(List.of(command));
    }

    public void sendCommand(List<String> commandList) {
        boolean doPause = false;
        for (String command : commandList) {
            try {
                if (doPause)
                    Thread.sleep(500);
                if (COMMAND_MAP.containsKey(command)) {
                    transmitCommand(COMMAND_MAP.get(command));
                }
                doPause = true;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    void transmitCommand(int code) {
        byte[] commandBytes = { 0x04, 0x01, 0x00, 0x00, 0x00, 0x00, (byte) (Math.floor(224 + (code / 16))),
                (byte) (code % 16) };
        try {
            Socket client = new Socket();
            client.connect(new InetSocketAddress(host, port), 1000);
            OutputStream dataOut = client.getOutputStream();
            InputStream dataIn = client.getInputStream();

            int strlen = 12;

            long timeout = System.currentTimeMillis() + PROTOCOL_TIMEOUT;

            while (true) {
                byte[] data = dataIn.readNBytes(1024);
                if (data.length < 24) {
                    dataOut.write(Arrays.copyOfRange(data, 0, strlen));
                    strlen = 1;
                } else {
                    dataOut.write(commandBytes);
                    commandBytes[1] = 0x00;
                    dataOut.write(commandBytes);
                    client.close();
                    break;
                }

                // handle timeout during handshake
                if (System.currentTimeMillis() > timeout) {
                    logger.error("Timeout error sending command: {}", code);
                    break;
                }
            }
        } catch (IOException ex) {
            logger.error("Error sending command: {}", ex.getMessage());
        }
    }
}
