package org.mw.buster;

public class BusterProcessExecutorException extends Exception
{
    public BusterProcessExecutorException(Throwable throwable)
    {
        super(throwable);
    }

    public BusterProcessExecutorException(String s)
    {
        super(s);
    }
}
