package org.mw.buster;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJsBrowser {

    private static final String DEFAULT_BUSTER_SERVER_URL = "http://localhost:1111";

    private DesiredCapabilities desiredCapabilities;
    private WebDriver phantomJSDriver;

    public PhantomJsBrowser() {
        desiredCapabilities = new DesiredCapabilities();
    }

    public void capturePhantomBrowser(String url) {
        phantomJSDriver = new PhantomJSDriver(desiredCapabilities);
        String captureUrl = (url != null ? url : DEFAULT_BUSTER_SERVER_URL) + "/capture";
        phantomJSDriver.get(captureUrl);
    }

    public void stop(){
        phantomJSDriver.quit();
    }


}