package pe.com.tumi.framework.configuracion.cache.was;

import com.ibm.websphere.cache.DistributedMap;
import java.io.PrintStream;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pe.com.tumi.framework.configuracion.cache.Cacheable;
import pe.com.tumi.framework.configuracion.cache.bean.CacheBean;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;

public class CacheWas61
  implements Cacheable
{
  private static CacheBean strNombre = null;

  public CacheWas61(String paramString)
  {
    DistributedMap localDistributedMap = null;
    try
    {
      if (strNombre == null)
        strNombre = new CacheBean();
      InitialContext localInitialContext = EJBFactory.getContextoLocal();
      localDistributedMap = (DistributedMap)localInitialContext.lookup(paramString);
      strNombre.setOCache(localDistributedMap);
    }
    catch (ClassCastException localClassCastException)
    {
      System.out.println(localClassCastException.getMessage());
    }
    catch (NamingException localNamingException)
    {
      localNamingException.printStackTrace();
      System.out.println(localNamingException.getMessage());
    }
    catch (EJBFactoryException localEJBFactoryException)
    {
      localEJBFactoryException.printStackTrace();
    }
  }

  public List get(String paramString)
  {
    List localList = null;
    DistributedMap localDistributedMap = (DistributedMap)strNombre.getOCache();
    localList = (List)localDistributedMap.get(paramString);
    return localList;
  }

  public void put(String paramString, List paramList)
  {
    DistributedMap localDistributedMap = (DistributedMap)strNombre.getOCache();
    localDistributedMap.put(paramString, paramList);
  }

  public boolean containsKey(String paramString)
  {
    DistributedMap localDistributedMap = (DistributedMap)strNombre.getOCache();
    return localDistributedMap.containsKey(paramString);
  }

  public void remove(Object paramObject)
  {
    DistributedMap localDistributedMap = (DistributedMap)strNombre.getOCache();
    localDistributedMap.remove(paramObject);
  }
}