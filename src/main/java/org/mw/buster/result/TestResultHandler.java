package org.mw.buster.result;

import org.mw.buster.junit_xstream.TestSuites;

public interface TestResultHandler
{
    public void handle(final TestSuites testSuites) throws Exception;
}
