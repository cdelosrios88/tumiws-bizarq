package pe.com.tumi.framework.negocio.ejb.proxy;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import pe.com.tumi.framework.negocio.ejb.exception.EJBException;
import pe.com.tumi.framework.negocio.ejb.lookup.EJBLookup;

public class EJBProxy
{
  public static Object obtenerBeanLocal(String paramString, InitialContext paramInitialContext)
    throws EJBException
  {
    Object localObject = null;
    try
    {
      localObject = EJBLookup.buscarEJB(paramInitialContext, paramString);
    }
    catch (EJBException localEJBException)
    {
      throw localEJBException;
    }
    return localObject;
  }

  public static Object obtenerBeanRemoto(String paramString, InitialContext paramInitialContext, Class paramClass)
    throws EJBException
  {
    Object localObject1 = null;
    try
    {
      Object localObject2 = EJBLookup.buscarEJB(paramInitialContext, paramString);
      localObject1 = PortableRemoteObject.narrow(localObject2, paramClass);
    }
    catch (EJBException localEJBException)
    {
      throw localEJBException;
    }
    return localObject1;
  }
}