package org.mw.buster.junit_xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

@XStreamAlias("testcase")
public class TestCase
{
    @XStreamAsAttribute
    private String time;

    @XStreamAsAttribute
    private String classname;

    @XStreamAsAttribute
    private String name;

    @XStreamImplicit(itemFieldName="failure")
    private List<TestFailure> failures;

    public String getClassname()
    {
        return classname;
    }

    public void setClassname(final String classname)
    {
        this.classname = classname;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(final String time)
    {
        this.time = time;
    }

    public List<TestFailure> getFailures()
    {
        return failures;
    }

    public void setFailures(final List<TestFailure> failures)
    {
        this.failures = failures;
    }

    public String toLogFriendlyString()
    {
        StringBuilder logMessage = new StringBuilder(String.format("--- %s [%s] [%s] %s", hasFailures() ? "FAILED" : "PASSED", time, classname, name));

        if(hasFailures())
        {
            for(TestFailure testFailure : failures)
            {
                logMessage.append("\n    " + testFailure.getContent());
            }
        }

        return logMessage.toString();
    }

    public boolean hasFailures()
    {
        return failures != null;
    }
}
