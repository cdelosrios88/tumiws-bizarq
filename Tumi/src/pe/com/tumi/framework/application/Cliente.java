package pe.com.tumi.framework.application;

import java.util.Map;

import pe.com.tumi.framework.configuracion.info.bean.InformacionBean;
import pe.com.tumi.framework.configuracion.info.server.InformacionServidor;
import pe.com.tumi.framework.util.properties.JProperties;

public class Cliente
{
  private static String strNombre = null;
  protected static Map mProperties = JProperties.getMapDePropertiesPorPathRelativo("META-INF/j.properties");

  protected static String obtenerStringDeConfiguracion(Map paramMap)
  {
    if (strNombre == null)
      if (InformacionServidor.getInformacion().getNombre() == null)
        strNombre = "NINGUNO";
      else if (InformacionServidor.getInformacion().getNombre().contains("Tomcat"))
        strNombre = "TOMCAT";
      else if (InformacionServidor.getInformacion().getNombre().contains("JBossWeb"))
        strNombre = "JBOSS403";
      else if (InformacionServidor.getInformacion().getNombre().contains("IBM"))
        strNombre = "WAS7.0";
      else
        strNombre = "WAS6.1";
    return (String)paramMap.get(strNombre);
  }

  public static Map getPoperties()
  {
    return mProperties;
  }
}