package pe.com.tumi.framework.vista.listener;

import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pe.com.tumi.framework.configuracion.info.bean.InformacionBean;
import pe.com.tumi.framework.configuracion.info.server.InformacionServidor;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.util.log.LoggerParameters;
import pe.com.tumi.framework.util.reflection.JReflection;

public class LoadInitial
  implements ServletContextListener
{
  public void contextDestroyed(ServletContextEvent paramServletContextEvent)
  {
  }

  public void contextInitialized(ServletContextEvent paramServletContextEvent)
  {
    String str = null;
    Map localMap = null;
    ServletContext localServletContext = paramServletContextEvent.getServletContext();
    try
    {
      WebApplicationContext localWebApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(localServletContext);
      TumiFactory.asignarContexto(localWebApplicationContext);
      InformacionServidor.establecerInformacionServidor(localServletContext.getServerInfo());
      InformacionBean localInformacionBean = null;
      localInformacionBean = (InformacionBean)TumiFactory.obtenerBean("infoBean");
      InformacionServidor.establecerInformacionAccesoLocal(localInformacionBean.getUrlAccesoLocal());
      InformacionServidor.establecerInformacionAccesoRemoto(localInformacionBean.getUrlAccesoRemoto());
      if (localInformacionBean.getClassConstante() != null)
      {
        Class localClass = JReflection.getClass(localInformacionBean.getClassConstante());
        str = localClass.getSimpleName();
        localMap = JReflection.getMapaConstante(localClass);
        localServletContext.setAttribute(str, localMap);
      }
      if (localInformacionBean.getPathLog4j() != null)
        LoggerParameters.getConfigureLog4j(localInformacionBean.getPathLog4j());
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}