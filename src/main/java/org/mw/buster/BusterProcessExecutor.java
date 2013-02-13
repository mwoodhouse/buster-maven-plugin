package org.mw.buster;

import org.apache.maven.plugin.logging.Log;
import org.mw.buster.junit_xstream.TestSuites;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BusterProcessExecutor
{
    private final static String[] FAILURE_CONDITIONS = new String[] {"No such file or directory"};

    private Log log;

    public BusterProcessExecutor(final Log log)
    {
        this.log = log;
    }

    public TestSuites execute(final String[] processArgs) throws BusterProcessExecutorException
    {
        final StringBuilder output = new StringBuilder();

        Process process = null;
        try
        {
            process = create(processArgs);

            final BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            for (line = inputReader.readLine(); line != null; line = inputReader.readLine())
            {
                if(!line.matches("^JOIN.*")){
                    output.append(line).append("\n");
                }
            }

            process.waitFor();

            checkForFailure(output.toString());

            return TestSuites.createFrom(output.toString());
        }
        catch (Exception e)
        {
            throw new BusterProcessExecutorException(e);
        }
        finally
        {
            if(process != null) process.destroy();
        }
    }

    private Process create(final String[] processArgs) throws IOException
    {
        final ProcessBuilder pb = new ProcessBuilder(processArgs);

        // todo - this is not very platform independent
        // set env path variable for node / buster
//        pb.environment().put("PATH", pb.environment().get("PATH") + ":/usr/local/bin");
        pb.redirectErrorStream(true);

        return pb.start();
    }

    private void checkForFailure(final String processOutput) throws BusterProcessExecutorException
    {
        for (String failureCondition : FAILURE_CONDITIONS)
        {
            if (processOutput.contains(failureCondition))
            {
                throw new BusterProcessExecutorException(failureCondition);
            }
        }
    }
}
