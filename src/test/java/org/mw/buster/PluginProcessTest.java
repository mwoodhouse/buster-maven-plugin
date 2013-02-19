package org.mw.buster;

import junit.framework.Assert;
import org.junit.Test;

public class PluginProcessTest {

    @Test
    public void shoudlCreateAnInstanceOfPluginProcessThatCreatesAnProcessBuilder(){
        PluginProcess pluginProcess = new PluginProcess("1111", null);

        Assert.assertNotNull(pluginProcess.pb);

    }
}
