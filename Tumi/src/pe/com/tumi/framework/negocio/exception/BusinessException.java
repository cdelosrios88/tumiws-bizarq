package pe.com.tumi.framework.negocio.exception;

import pe.com.tumi.framework.negocio.comun.JException;

public class BusinessException extends JException
{
  public BusinessException(String paramString)
  {
    super(paramString);
  }

  public BusinessException(Exception paramException)
  {
    super(paramException);
  }

  public BusinessException(String paramString, Exception paramException)
  {
    super(paramString, paramException);
  }
}