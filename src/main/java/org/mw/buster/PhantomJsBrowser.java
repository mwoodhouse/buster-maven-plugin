package org.mw.buster;

import org.apache.maven.plugin.logging.Log;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJsBrowser {

    private static final String DEFAULT_BUSTER_SERVER_URL = "http://localhost:1111";

    private WebDriver phantomJSDriver;
    private Log log;

    public PhantomJsBrowser(WebDriver phantomJSDriver, Log log) {
        this.phantomJSDriver = phantomJSDriver;
        this.log = log;
    }

    public boolean capturePhantomBrowser(String url) {
        String captureUrl = (url != null ? url : DEFAULT_BUSTER_SERVER_URL) + "/capture";
        log.info(String.format("Trying to capture browser at: %s", captureUrl));

        phantomJSDriver.get(captureUrl);
        sleep();
        String currentUrl = phantomJSDriver.getCurrentUrl();
        if(currentUrl.contains("slaves")){
            log.info(String.format("Browser captured at: %s", currentUrl));
            return true;
        } else {
            log.warn(String.format("Unable to capture browser. Current URL: %s", currentUrl));
            return false;
        }
    }

    public void stop(){
        phantomJSDriver.quit();
    }

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignore) {
        }
    }


}