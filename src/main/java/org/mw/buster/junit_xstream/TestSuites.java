package org.mw.buster.junit_xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import java.util.ArrayList;
import java.util.List;

@XStreamAlias("testsuites")
public class TestSuites
{
    @XStreamOmitField
    private String xml;
    
    @XStreamImplicit(itemFieldName="testsuite")
    private List<TestSuite> testSuites = new ArrayList<TestSuite>();

    public List<TestSuite> getTestSuites()
    {
        return testSuites;
    }

    public void setTestSuites(final List<TestSuite> testSuites)
    {
        this.testSuites = testSuites;
    }
    
    public void setXml(final String xml)
    {
        this.xml = xml;
    }

    public String getXml()
    {
        return xml;
    }

    public static TestSuites createFrom(final String xml)
    {
        XStream xStream = new XStream();
        xStream.processAnnotations(TestSuites.class);

        TestSuites testSuites = (TestSuites) xStream.fromXML(xml);
        testSuites.setXml(xml);

        return testSuites;
    }

    public boolean shouldStopBuild()
    {
        for(TestSuite testSuite : testSuites)
        {
            if(testSuite.hasFailures() || testSuite.hasErrors()) return true;
        }

        return false;
    }
}
