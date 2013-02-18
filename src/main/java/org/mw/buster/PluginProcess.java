package org.mw.buster;

import org.apache.maven.plugin.logging.Log;

import java.io.IOException;

public class PluginProcess {

    private ProcessBuilder pb;
    private Process process;
    private String port = "1111";
    private Log log;

    public PluginProcess(Log log) {
        this.log = log;
        this.pb = new ProcessBuilder("buster", "server", "-p", port);
    }

    public void start(){
        try {
            process = pb.start();
        } catch (IOException e) {
            log.error("Buster server failed to start", e);
        }
    }

    public void stop(){
        process.destroy();
    }

    public String getPort() {
        return port;
    }
}
