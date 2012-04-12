package org.mw.buster.result;

import org.mw.buster.junit_xstream.TestSuites;

import java.io.FileWriter;
import java.io.PrintWriter;

public class JUnitFileAppender implements TestResultHandler
{
    private String outputFilePath;

    public JUnitFileAppender(final String outputFilePath)
    {
        this.outputFilePath = outputFilePath;
    }

    @Override public void handle(final TestSuites testSuites) throws Exception
    {
        PrintWriter out = new PrintWriter(new FileWriter(outputFilePath));
        out.print(testSuites.getXml());
        out.close();
    }
}
