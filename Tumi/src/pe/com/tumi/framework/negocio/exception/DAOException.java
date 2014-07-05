package pe.com.tumi.framework.negocio.exception;

import pe.com.tumi.framework.negocio.comun.JException;

public class DAOException extends JException
{
  public DAOException(String paramString)
  {
    super(paramString);
  }

  public DAOException(Exception paramException)
  {
    super(paramException);
  }

  public DAOException(String paramString, Exception paramException)
  {
    super(paramString, paramException);
  }
}