package org.mw.buster.junit_xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("testsuite")
public class TestSuite
{
    @XStreamAsAttribute
    private int errors;

    @XStreamAsAttribute
    private int tests;

    @XStreamAsAttribute
    private String time;

    @XStreamAsAttribute
    private int failures;

    @XStreamAsAttribute
    private String name;

    @XStreamImplicit(itemFieldName = "testcase")
    private List<TestCase> testCases = new ArrayList<TestCase>();;

    public int getErrors()
    {
        return errors;
    }

    public void setErrors(final int errors)
    {
        this.errors = errors;
    }

    public int getFailures()
    {
        return failures;
    }

    public void setFailures(final int failures)
    {
        this.failures = failures;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public List<TestCase> getTestCases()
    {
        return testCases;
    }

    public void setTestCases(final List<TestCase> testCases)
    {
        this.testCases = testCases;
    }

    public int getTests()
    {
        return tests;
    }

    public void setTests(final int tests)
    {
        this.tests = tests;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(final String time)
    {
        this.time = time;
    }

    public String toLogFriendlyString()
    {
        return String.format("Tests Run:%d, Failures:%d, Errors:%d, Time:%s, Browser(%s)", tests, failures, errors, time, name);
    }

    public boolean hasFailures()
    {
        return failures > 0;
    }

    public boolean hasErrors()
    {
        return errors > 0;
    }
}
