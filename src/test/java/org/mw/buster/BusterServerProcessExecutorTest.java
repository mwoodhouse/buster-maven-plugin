package org.mw.buster;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class BusterServerProcessExecutorTest {

    private BusterServerProcessExecutor busterServer;

    private PluginProcess mockProcess;
    private PhantomJsBrowser mockBrowser;

    @Before
    public void setUp(){
        mockProcess = mock(PluginProcess.class);
        mockBrowser = mock(PhantomJsBrowser.class);
        busterServer = new BusterServerProcessExecutor(mockProcess, mockBrowser);
    }

    @Test
    public void shouldStartBusterProcess(){
        busterServer.start();

        verify(mockProcess).start();
    }

    @Test
    public void shouldStopBusterProcess(){
        busterServer.stop();

        verify(mockProcess).stop();
        verify(mockBrowser).stop();
    }

    @Test
    public void shouldCaptureBrowser() throws IOException {
        when(mockProcess.getPort()).thenReturn("1111");
        when(mockBrowser.capturePhantomBrowser(any(String.class))).thenReturn(true);

        busterServer.captureBrowser();

        verify(mockBrowser, times(1)).capturePhantomBrowser(String.format("http://localhost:1111"));
        verifyNoMoreInteractions(mockBrowser);
    }

    @Test
    public void shouldCaptureBrowserOnThirdAttempt() throws IOException {
        when(mockProcess.getPort()).thenReturn("1111");
        when(mockBrowser.capturePhantomBrowser(any(String.class)))
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true);

        busterServer.captureBrowser();

        verify(mockBrowser, times(3)).capturePhantomBrowser(String.format("http://localhost:1111"));
        verifyNoMoreInteractions(mockBrowser);
    }

    @Test(expected = BusterServerProcessExecutor.UnableToCaptureWebBrowserException.class)
    public void shouldTryToCapture10TimesAndThrowException() throws Exception {
        when(mockBrowser.capturePhantomBrowser(any(String.class))).thenReturn(false);
        when(mockProcess.getPort()).thenReturn("1111");

        busterServer.captureBrowser();

        verify(mockBrowser, times(10)).capturePhantomBrowser(any(String.class));
        verifyNoMoreInteractions(mockBrowser);
    }

}
