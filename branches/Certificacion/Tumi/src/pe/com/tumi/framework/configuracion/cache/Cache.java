package pe.com.tumi.framework.configuracion.cache;

import java.util.HashMap;
import java.util.Map;
import pe.com.tumi.framework.application.Cliente;

public class Cache extends Cliente
{
  private static String strNombre = (String)mProperties.get("cache.jboss");
  private static String strCodigo = (String)mProperties.get("cache.was");
  private static Map strEnlace = null;
  protected static String sClass = null;

  static
  {
    if (strEnlace == null)
    {
      strEnlace = new HashMap();
      strEnlace.put("JBOSS403", strNombre);
      strEnlace.put("WAS6.1", strCodigo);
      strEnlace.put("WAS7.0", strCodigo);
    }
    sClass = obtenerStringDeConfiguracion(strEnlace);
  }
}