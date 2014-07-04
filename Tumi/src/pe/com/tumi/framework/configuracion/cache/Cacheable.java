package pe.com.tumi.framework.configuracion.cache;

import java.util.List;

public abstract interface Cacheable
{
  public abstract List get(String paramString);

  public abstract void put(String paramString, List paramList);

  public abstract boolean containsKey(String paramString);

  public abstract void remove(Object paramObject);
}