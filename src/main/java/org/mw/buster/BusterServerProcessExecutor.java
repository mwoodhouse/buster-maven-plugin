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
        String captureUrl = String.format("http://localhost:%s", pluginProcess.getPort());

        for (int retryTimesLeft = 10; retryTimesLeft >= 0; retryTimesLeft--){
            boolean isCaptured = browser.capturePhantomBrowser(captureUrl);
            if(isCaptured){
                break;
            } else if(!isCaptured && retryTimesLeft == 0){
                throw new UnableToCaptureWebBrowserException(String.format("Could not capture browser at: %s", captureUrl));
            } else {
                sleep();
            }
        }
        return this;
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignore) {
        }
    }

    static class UnableToCaptureWebBrowserException extends RuntimeException {
        UnableToCaptureWebBrowserException(String message) {
            super(message);
        }
    }

}
