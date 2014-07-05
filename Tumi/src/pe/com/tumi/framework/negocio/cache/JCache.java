package pe.com.tumi.framework.negocio.cache;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.configuracion.cache.Cache;
import pe.com.tumi.framework.configuracion.cache.Cacheable;
import pe.com.tumi.framework.negocio.cache.exception.CacheException;
import pe.com.tumi.framework.util.reflection.JReflection;

public class JCache extends Cache
{
  public static List getListaTablaByIdTabla(String paramString)
    throws CacheException
  {
    List localList = null;
    try
    {
      Cacheable localCacheable = (Cacheable)JReflection.getInstance(sClass, (String)mProperties.get("cache.jndi"));
      if (localCacheable.containsKey(paramString))
        localList = localCacheable.get(paramString);
      else
        System.out.println("No Se obtuvo " + paramString + " de cache");
    }
    catch (Exception localException)
    {
      System.out.println("Se muestra un error al obtener la tabla : " + paramString);
      throw new CacheException(localException);
    }
    return localList;
  }
}