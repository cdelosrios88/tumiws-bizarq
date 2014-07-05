package pe.com.tumi.framework.vista.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import pe.com.tumi.framework.negocio.cache.service.CacheService;

public class LoadCache
  implements ServletContextListener
{
  public void contextDestroyed(ServletContextEvent paramServletContextEvent)
  {
  }

  public void contextInitialized(ServletContextEvent paramServletContextEvent)
  {
    try
    {
      CacheService.inicializaCache();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}