package org.mw.buster.result;

import org.apache.maven.plugin.logging.Log;
import org.mw.buster.junit_xstream.TestCase;
import org.mw.buster.junit_xstream.TestSuite;
import org.mw.buster.junit_xstream.TestSuites;

public class MavenTestResultLogger implements TestResultHandler
{
    private Log logger;

    public MavenTestResultLogger(final Log logger)
    {
        this.logger = logger;
    }

    @Override public void handle(final TestSuites testSuites)
    {
        for(TestSuite testSuite : testSuites.getTestSuites())
        {
            logger.info(testSuite.toLogFriendlyString());

            for(TestCase testCase : testSuite.getTestCases())
            {
                logger.info(testCase.toLogFriendlyString());
            }
        }
    }
}
