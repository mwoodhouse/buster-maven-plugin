package org.mw.buster;

import java.io.IOException;

public class BusterServerProcessExecutor {

    private PhantomJsBrowser browser;
    private PluginProcess pluginProcess;

    public BusterServerProcessExecutor(PluginProcess pluginProcess, PhantomJsBrowser browser) {
        this.pluginProcess = pluginProcess;
        this.browser = browser;
    }

    public BusterServerProcessExecutor start(){
        pluginProcess.start();
        return  this;
    }

    public BusterServerProcessExecutor stop() {
        if(browser != null) {
            browser.stop();
        }
        if(pluginProcess != null){
            pluginProcess.stop();
        }
        return this;
    }

    public BusterServerProcessExecutor captureBrowser() throws IOException {
        browser.capturePhantomBrowser(String.format("http://localhost:%s", pluginProcess.getPort()));
        return this;
    }
}
