package pe.com.tumi.framework.negocio.ejb.exception;

public class EJBException extends Exception
{
  public EJBException()
  {
  }

  public EJBException(String paramString)
  {
    super(paramString);
  }

  public EJBException(Exception paramException)
  {
    super(paramException);
  }

  public EJBException(String paramString, Exception paramException)
  {
    super(paramString, paramException);
  }
}