package org.mw.buster;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomJsBrowser {

    private static final String DEFAULT_BUSTER_SERVER_URL = "http://localhost:1111";

    private final DesiredCapabilities desiredCapabilities;
    private final WebDriver phantomJSDriver;

    public PhantomJsBrowser() {
        System.out.println("");
        desiredCapabilities = new DesiredCapabilities();
        phantomJSDriver = new PhantomJSDriver(desiredCapabilities);
    }

    public void capturePhantomBrowser(String url) {
        String captureUrl = (url != null ? url : DEFAULT_BUSTER_SERVER_URL) + "/capture";
        phantomJSDriver.get(captureUrl);
    }

    public void stop(){
        phantomJSDriver.quit();
    }


}