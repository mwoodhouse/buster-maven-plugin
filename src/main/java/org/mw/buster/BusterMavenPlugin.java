package org.mw.buster;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.mw.buster.junit_xstream.TestSuites;
import org.mw.buster.result.JUnitFileAppender;
import org.mw.buster.result.MavenTestResultLogger;
import org.mw.buster.utils.ServerUtil;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

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
    private String configFilePath;

    /**
     * path to the buster executables' directory
     *
     * @parameter
     * @required
     */
    private String executablesDirectory = "";

    /**
     * path to the phantomjs executable
     *
     * @parameter
     * @required
     */
    private String phantomjsFilePath;

    /**
     * hostname
     *
     * @parameter
     */
    private String hostname;

    /**
     * port
     *
     * @parameter
     */
    private String port;

    /**
     * start buster sever from the plugin and use phantomjs to run the tests
     *
     * @parameter
     */
    private boolean embeddedBusterServer = false;

    /**
     * Directory containing the build files.
     * @parameter expression="${project.build.directory}"
     */
    private File buildDirectory;

    /**
     * Fail on warnings at this level. One of fatal, error, warning.
     * @parameter
     */
    private String failOn;

    /**
     * Whether the test should be skipped
     *
     * @parameter
     */
    private boolean skip;

    /**
     * test output path
     *
     * @parameter
     */
    private String testOutputPath = "buster-tests";

    private BusterServerProcessExecutor server;

    public void execute() throws MojoExecutionException, MojoFailureException
    {
        if (skip) {
            getLog().info("Skipping Buster tests.");
            return;
        }

        printBanner();
        if(embeddedBusterServer) {
            executeWithEmbeddedBuster();
        } else {
            executeWithLocalBuster();
        }
    }

    private void executeWithLocalBuster() throws MojoFailureException, MojoExecutionException {
        getLog().info("Running with local buster.");
        run(getArgs());
    }

    private void executeWithEmbeddedBuster() throws MojoFailureException, MojoExecutionException {
        getLog().info("Running with embedded buster.");

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        if (phantomjsFilePath != null) {
            desiredCapabilities.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjsFilePath);
        }

        PhantomJSDriver driver = new PhantomJSDriver(desiredCapabilities);

        // Setting a random available port, and setting hostname to localhost
        port = ServerUtil.newAvailablePort();
        hostname = "localhost";

        server = new BusterServerProcessExecutor(new PluginProcess(port, getLog(), executablesDirectory),
                                                 new PhantomJsBrowser(driver, getLog()));
        try{
            server.start()
                  .captureBrowser();

            run(getArgs());
        }
        catch (IOException e){
            getLog().error(e);
        } finally {
            server.stop();
        }
    }

    static String join(String[] s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if (i > 0) {
                builder.append(delimiter);
            }
            builder.append(s[i]);
        }
        return builder.toString();
    }

    private void run(String[] args) throws MojoExecutionException, MojoFailureException
    {
        try
        {
            getLog().info("Running this command for buster:");
            getLog().info(join(args, " "));

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
        args.add(executablesDirectory + "buster-test");
        args.add("--config");
        args.add(configFilePath);

        if (testOutputPath != null)
        {
            args.add("-r");
            args.add("xml");
        }

        if (failOn != null)
        {
            if (!(failOn.equals("warning") ||
                  failOn.equals("error") ||
                  failOn.equals("fatal")))
            {
                throw new IllegalArgumentException("failOn was " + failOn +
                                                   ", but can only be one of " +
                                                   "warning, error or fatal.");
            }
            args.add("-F");
            args.add(failOn);
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

    public void setConfigFilePath(final String configFilePath)
    {
        this.configFilePath = configFilePath;
    }

    public void setHostname(final String hostname)
    {
        this.hostname = hostname;
    }

    public void setPort(final String port)
    {
        this.port = port;
    }

    public void setEmbeddedBusterServer(boolean embeddedBusterServer) {
        this.embeddedBusterServer = embeddedBusterServer;
    }

    public void setTestOutputPath(final String testOutputPath)
    {
        this.testOutputPath = testOutputPath;
    }
}
