package org.mw.buster;

import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhantomJsBrowserTest {

    private static final String BUSTER_SERVER_URL = "http://localhost:1111";
    private static final String BUSTER_SERVER_CAPTURE_URL = "http://localhost:1111/capture";

    @Mock
    private WebDriver mockPhantom;
    @Mock
    private Log mockLog;
    private PhantomJsBrowser browser;

    @Before
    public void setUp(){
        browser = new PhantomJsBrowser(mockPhantom, mockLog);
    }

    @Test
    public void shouldCaptureBrowser(){
        when(mockPhantom.getCurrentUrl()).thenReturn("http://localhost:1111/slaves/1239238293823");

        boolean result = browser.capturePhantomBrowser(BUSTER_SERVER_URL);

        verify(mockPhantom).get(BUSTER_SERVER_CAPTURE_URL);
        assertThat(result, is(true));

    }

    @Test
    public void shouldFailToCaptureBrowser(){
        when(mockPhantom.getCurrentUrl()).thenReturn("http://localhost:1111/capture");

        boolean result = browser.capturePhantomBrowser(BUSTER_SERVER_URL);

        verify(mockPhantom).get(BUSTER_SERVER_CAPTURE_URL);
        assertThat(result, is(false));
    }

    @Test
    public void shouldStopDriver(){
        browser.stop();

        verify(mockPhantom).quit();
    }
}
