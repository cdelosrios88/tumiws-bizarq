package pe.com.tumi.framework.servicio.seguridad.exception;

public class SeguridadException extends Exception
{
  public SeguridadException()
  {
  }

  public SeguridadException(String paramString)
  {
    super(paramString);
  }

  public SeguridadException(Exception paramException)
  {
    super(paramException);
  }
}