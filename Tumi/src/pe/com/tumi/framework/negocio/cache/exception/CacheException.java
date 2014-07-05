package pe.com.tumi.framework.negocio.cache.exception;

import pe.com.tumi.framework.negocio.comun.JException;

public class CacheException extends JException
{
  public CacheException(String paramString)
  {
    super(paramString);
  }

  public CacheException(Exception paramException)
  {
    super(paramException);
  }

  public CacheException(String paramString, Exception paramException)
  {
    super(paramString, paramException);
  }
}