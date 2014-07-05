package pe.com.tumi.framework.application;

import java.util.HashMap;
import java.util.Map;

import pe.com.tumi.framework.configuracion.info.bean.InformacionBean;
import pe.com.tumi.framework.configuracion.info.server.InformacionServidor;

public class Deploy extends Cliente
{
  private static String strNombre = "org.jnp.interfaces.NamingContextFactory";
  private static String strCodigo = "com.ibm.websphere.naming.WsnInitialContextFactory";
  private static String strEnlace = "local:";
  private static String strSession = "local:ejb/";
  private static String strIp = "ejblocal:";
  private static String strValor = "java:";
  private static String strCliente = "";
  private static String strCaso = "";
  private static Map strEstado = new HashMap();
  private static Map strVar1 = new HashMap();
  private static Map strVar2 = new HashMap();
  private static Map strVar3 = new HashMap();
  protected static String sFactory = null;
  protected static String sProviderLocal = null;
  protected static String sProviderRemoto = null;
  protected static String sPrefijoJNDI = null;
  protected static String sPrefijoDatasource = null;

  static
  {
    strEstado.put("JBOSS403", strNombre);
    strEstado.put("WAS6.1", strCodigo);
    strEstado.put("WAS7.0", strCodigo);
    sFactory = obtenerStringDeConfiguracion(strEstado);
    strVar2.put("JBOSS403", strEnlace);
    strVar2.put("WAS6.1", strSession);
    strVar2.put("WAS7.0", strIp);
    sPrefijoJNDI = obtenerStringDeConfiguracion(strVar2);
    strVar3.put("JBOSS403", strValor);
    strVar3.put("WAS6.1", strCliente);
    strVar3.put("WAS7.0", strCaso);
    sPrefijoDatasource = obtenerStringDeConfiguracion(strVar3);
    sProviderLocal = InformacionServidor.getInformacion().getUrlAccesoLocal();
    sProviderRemoto = InformacionServidor.getInformacion().getUrlAccesoRemoto();
  }
}