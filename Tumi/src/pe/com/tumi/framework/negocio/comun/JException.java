package pe.com.tumi.framework.negocio.comun;

public class JException extends Exception
{
  public JException(String paramString)
  {
    super(paramString);
  }

  public JException(Exception paramException)
  {
    super(paramException);
  }

  public JException(String paramString, Exception paramException)
  {
    super(paramString, paramException);
  }
}