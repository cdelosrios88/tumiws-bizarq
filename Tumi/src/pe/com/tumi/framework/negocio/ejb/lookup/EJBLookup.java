package pe.com.tumi.framework.negocio.ejb.lookup;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import pe.com.tumi.framework.negocio.ejb.exception.EJBException;

public class EJBLookup
{
  public static Object buscarEJB(InitialContext paramInitialContext, String paramString)
    throws EJBException
  {
    Object localObject = null;
    try
    {
      localObject = paramInitialContext.lookup(paramString);
    }
    catch (NamingException localNamingException)
    {
      localNamingException.printStackTrace();
      throw new EJBException(localNamingException.getMessage());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new EJBException(localException.getMessage());
    }
    return localObject;
  }
}