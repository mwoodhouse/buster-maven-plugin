package org.mw.buster;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.mw.buster.junit_xstream.TestSuites;
import org.mw.buster.result.JUnitFileAppender;
import org.mw.buster.result.MavenTestResultLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

/**
 * runs buster.js within maven
 *
 * @requiresDependencyResolution
 * @goal test
 * @phase test
 */
public class BusterMavenPlugin extends AbstractMojo
{
    /**
     * config file path
     *
     * @parameter
     * @required
     */
    private String busterJsFilePath;

    /**
     * hostname
     *
     * @parameter
     * @required
     */
    private String hostname;

    /**
     * port
     *
     * @parameter
     * @required
     */
    private String port;

    /**
     * use local buster sever
     *
     * @parameter
     */
    private boolean localBusterServer = false;

    /**
     * Directory containing the build files.
     * @parameter expression="${project.build.directory}"
     */
    private File buildDirectory;

    /**
     * test output path
     *
     * @parameter
     */
    private String testOutputPath = "buster-tests";

    private BusterServerProcessExecutor server;

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        printBanner();
        System.out.println("Localbuster: " + localBusterServer);
        if(localBusterServer) {
            executeWithLocalBuster();

        } else {
            executeWithEmbeddedBuster();
        }
    }

    private void executeWithLocalBuster() throws MojoFailureException, MojoExecutionException {
        run(getArgs());
    }

    private void executeWithEmbeddedBuster() throws MojoFailureException, MojoExecutionException {
        server = new BusterServerProcessExecutor(getLog());
        try{
            server.start()
                    .captureBrowser();

            run(getArgs());
            server.stop();
        }
        catch (IOException e){
            getLog().error(e);
        }
    }

    private void run(String[] args) throws MojoExecutionException, MojoFailureException
    {
        try
        {
            final TestSuites testSuites = new BusterProcessExecutor(getLog()).execute(args);

            // todo put into TestResultHandler runner
            new MavenTestResultLogger(getLog()).handle(testSuites);
            new JUnitFileAppender(buildDirectory + File.separator + testOutputPath).handle(testSuites);

            if (testSuites.shouldStopBuild())
            {
                throw new BusterProcessExecutorException("There are failed tests... :(");
            }
        }
        catch(BusterProcessExecutorException bpe)
        {
            throw new MojoFailureException(bpe.getMessage());
        }
        catch (Exception e)
        {
            throw new MojoFailureException(e.getMessage());
        }

        System.out.println("\n");
        System.out.println(" Buster.js Maven Plugin - Complete... :)");
        System.out.println("\n");
    }

    private String[] getArgs()
    {
        final ArrayList<String> args = new ArrayList<String>();

        // todo - sort out referencing of buster script, not very platform independent
        args.add("buster");

        args.add("test");
        args.add("--config");
        args.add(busterJsFilePath);

        if (testOutputPath != null)
        {
            args.add("-r");
            args.add("xml");
        }

        args.add("-s");
        args.add("http://"+hostname+":"+port);

        return args.toArray(new String[] {});
    }

    private void printBanner()
    {
        getLog().info("-------------------------------------------");
        getLog().info(" Buster.js Maven PLUGIN ");
        getLog().info("-------------------------------------------");
    }

    public void setBusterJsFilePath(final String busterJsFilePath)
    {
        this.busterJsFilePath = busterJsFilePath;
    }

    public void setHostname(final String hostname)
    {
        this.hostname = hostname;
    }

    public void setPort(final String port)
    {
        this.port = port;
    }

    public void setLocalBusterServer(final boolean localBusterServer) {
        this.localBusterServer = localBusterServer;
    }

    public void setTestOutputPath(final String testOutputPath)
    {
        this.testOutputPath = testOutputPath;
    }
}