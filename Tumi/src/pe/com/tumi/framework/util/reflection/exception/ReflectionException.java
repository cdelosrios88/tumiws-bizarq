package pe.com.tumi.framework.util.reflection.exception;

public class ReflectionException extends Exception
{
  public ReflectionException()
  {
  }

  public ReflectionException(String paramString)
  {
    super(paramString);
  }

  public ReflectionException(Exception paramException)
  {
    super(paramException);
  }
}