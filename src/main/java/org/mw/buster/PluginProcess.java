package org.mw.buster;

import org.apache.maven.plugin.logging.Log;

import java.io.IOException;

public class PluginProcess {

    ProcessBuilder pb;

    private Process process;
    private String port = "1111";
    private Log log;

    public PluginProcess(String port, Log log, String executablesFilePath) {
        this.log = log;
        this.port = port;
        this.pb = new ProcessBuilder(executablesFilePath + "buster-server", "-p", port);
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
