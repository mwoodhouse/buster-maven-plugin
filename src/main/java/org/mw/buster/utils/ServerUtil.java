package org.mw.buster.utils;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerUtil {

    public static String newAvailablePort() {
        ServerSocket socket = null;
        try {
            socket = new ServerSocket(0);
            return Integer.toString(socket.getLocalPort());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
}