package pe.com.tumi.framework.negocio.ejb.factory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import pe.com.tumi.framework.application.Deploy;
import pe.com.tumi.framework.negocio.ejb.exception.EJBException;
import pe.com.tumi.framework.negocio.ejb.proxy.EJBProxy;

public class EJBFactory extends Deploy
{
  private static Map strNombre;
  private static Map strCodigo;

  private static void strNombre()
    throws EJBFactoryException
  {
    if ((strNombre == null) || (strCodigo == null))
      try
      {
        strNombre = Collections.synchronizedMap(new HashMap());
        strCodigo = Collections.synchronizedMap(new HashMap());
      }
      catch (Exception localException)
      {
        throw new EJBFactoryException("EJBFactory: " + localException.getMessage());
      }
  }

  public static DataSource getDataSource(String paramString)
    throws EJBFactoryException
  {
    DataSource localDataSource = null;
    try
    {
      strNombre();
      paramString = sPrefijoDatasource + paramString;
      localDataSource = (DataSource)strNombre(sProviderLocal).lookup(paramString);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new EJBFactoryException("ServiceLocator Exception" + localClassCastException.getMessage());
    }
    catch (NamingException localNamingException)
    {
      throw new EJBFactoryException("ServiceLocator Exception" + localNamingException.getMessage());
    }
    return localDataSource;
  }

  public static Object getLocal(Class paramClass)
    throws EJBFactoryException
  {
    Object localObject = null;
    String str = sPrefijoJNDI + paramClass.getName();
    strNombre();
    if (strNombre.containsKey(str))
      localObject = strNombre.get(str);
    else
      try
      {
        localObject = EJBProxy.obtenerBeanLocal(str, strNombre(sProviderLocal));
        strNombre.put(str, localObject);
      }
      catch (EJBException localEJBException)
      {
        throw new EJBFactoryException("ServiceLocator Exception" + localEJBException.getMessage());
      }
    return localObject;
  }

  public static Object getRemote(Class paramClass)
    throws EJBFactoryException
  {
    Object localObject = null;
    strNombre();
    String str = paramClass.getName();
    if (strNombre.containsKey(str))
      localObject = strNombre.get(str);
    else
      try
      {
        localObject = EJBProxy.obtenerBeanRemoto(str, strNombre(sProviderRemoto), paramClass);
        strNombre.put(str, localObject);
      }
      catch (EJBException localEJBException)
      {
        throw new EJBFactoryException("ServiceLocator Exception" + localEJBException.getMessage());
      }
      catch (ClassCastException localClassCastException)
      {
        throw new EJBFactoryException("ServiceLocator Exception" + localClassCastException.getMessage());
      }
    return localObject;
  }

  private static InitialContext strNombre(String paramString)
    throws EJBFactoryException
  {
    InitialContext localInitialContext = null;
    try
    {
      if (strCodigo.containsKey(paramString))
      {
        localInitialContext = (InitialContext)strCodigo.get(paramString);
      }
      else
      {
        Hashtable localHashtable = new Hashtable();
        localHashtable.put("java.naming.factory.initial", sFactory);
        localHashtable.put("java.naming.provider.url", paramString);
        localInitialContext = new InitialContext(localHashtable);
        strCodigo.put(paramString, localInitialContext);
      }
    }
    catch (NamingException localNamingException)
    {
      throw new EJBFactoryException("ServiceLocator Exception" + localNamingException.getMessage());
    }
    return localInitialContext;
  }

  public static InitialContext getContextoLocal()
    throws EJBFactoryException
  {
    strNombre();
    return strNombre(sProviderLocal);
  }
}