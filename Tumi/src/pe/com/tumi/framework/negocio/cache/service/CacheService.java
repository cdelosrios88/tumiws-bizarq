package pe.com.tumi.framework.negocio.cache.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.configuracion.cache.Cache;
import pe.com.tumi.framework.configuracion.cache.Cacheable;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.util.reflection.JReflection;

public class CacheService extends Cache
{
  public static void inicializaCache()
  {
    try
    {
      Cacheable localCacheable = (Cacheable)JReflection.getInstance(sClass, (String)mProperties.get("cache.jndi"));
      strNombre(localCacheable);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private static void strNombre(Cacheable paramCacheable)
  {
    Method localMethod = null;
    String str = null;
    Object localObject1 = null;
    Object localObject2 = null;
    Integer localInteger = null;
    Class localClass = null;
    Class[] arrayOfClass = new Class[1];
    try
    {
      localClass = JReflection.getClass((String)mProperties.get("cache.remoto.nombre"));
      Object localObject3 = EJBFactory.getLocal(localClass);
      str = (String)mProperties.get("cache.remoto.metodo.maestro");
      localMethod = localObject3.getClass().getDeclaredMethod(str, new Class[0]);
      localObject1 = localMethod.invoke(localObject3, new Object[0]);
      List localList1 = (List)localObject1;
      List localList2 = null;
      Object localObject4 = null;
      if (localList1 != null)
      {
        paramCacheable.put("0", localList1);
        str = (String)mProperties.get("cache.remoto.metodo.detalle");
        localClass = JReflection.getClass("java.lang.Integer");
        for (int i = 0; i < localList1.size(); i++)
        {
          localObject4 = localList1.get(i);
          arrayOfClass[0] = localClass;
          localMethod = localObject3.getClass().getDeclaredMethod(str, arrayOfClass);
          localInteger = (Integer)JReflection.getProperty(localObject4, "intIdDetalle");
          localObject1 = localMethod.invoke(localObject3, new Object[] { localInteger });
          localList2 = (List)localObject1;
          if (localList2 == null)
            continue;
          paramCacheable.put(String.valueOf(localInteger), localList2);
        }
      }
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    catch (EJBFactoryException localEJBFactoryException)
    {
      localEJBFactoryException.printStackTrace();
    }
  }
}