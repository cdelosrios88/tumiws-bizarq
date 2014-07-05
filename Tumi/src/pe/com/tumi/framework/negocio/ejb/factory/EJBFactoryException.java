package pe.com.tumi.framework.negocio.ejb.factory;

public class EJBFactoryException extends Exception
{
  public EJBFactoryException()
  {
  }

  public EJBFactoryException(String paramString)
  {
    super(paramString);
  }

  public EJBFactoryException(Exception paramException)
  {
    super(paramException);
  }

  public EJBFactoryException(String paramString, Exception paramException)
  {
    super(paramString, paramException);
  }
}