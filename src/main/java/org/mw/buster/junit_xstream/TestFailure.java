package org.mw.buster.junit_xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("failure")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"content"})
public class TestFailure
{
    @XStreamAsAttribute
    private String type;

    @XStreamAsAttribute
    private String message;

    private String content;

    public String getContent()
    {
        return content;
    }

    public void setContent(final String content)
    {
        this.content = content;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }

    public String getType()
    {
        return type;
    }

    public void setType(final String type)
    {
        this.type = type;
    }
}
