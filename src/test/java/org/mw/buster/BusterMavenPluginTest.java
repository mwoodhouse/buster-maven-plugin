package org.mw.buster;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Test;

public class BusterMavenPluginTest
{
    @Test
    public void shouldRunTests() throws MojoExecutionException, MojoFailureException
    {
        BusterMavenPlugin busterMavenPlugin = new BusterMavenPlugin();

        busterMavenPlugin.setBusterJsFilePath("/Users/mwoodhouse/projects/mooTagify/test/buster.js");
        busterMavenPlugin.setTestOutputPath("/Users/mwoodhouse/buster.xml");
        busterMavenPlugin.setHostname("localhost");
        busterMavenPlugin.setPort("1111");

        busterMavenPlugin.execute();
    }

}
