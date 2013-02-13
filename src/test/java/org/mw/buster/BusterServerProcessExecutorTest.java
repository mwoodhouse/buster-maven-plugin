package org.mw.buster;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BusterServerProcessExecutorTest {

    private BusterServerProcessExecutor busterServer;

    @Before
    public void setUp(){
        busterServer = new BusterServerProcessExecutor(null);
    }

    @Test
    public void shouldMatchUrlInLine(){
        String result = busterServer.matchUrl("buster-server-http:/sdsd running on http://localhost:1111");
        Assert.assertEquals("http://localhost:1111", result);
    }
}
