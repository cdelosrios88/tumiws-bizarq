package pe.com.tumi.framework.configuracion.info.server;

import java.io.PrintStream;

import pe.com.tumi.framework.configuracion.info.bean.InformacionBean;

public class InformacionServidor
{
  private static final InformacionBean strNombre = new InformacionBean();

  public static InformacionBean getInformacion()
  {
    return strNombre;
  }

  public static void establecerInformacionServidor(String paramString)
  {
    strNombre.setNombre(strNombre(paramString));
    strNombre.setVersion(strCodigo(paramString));
    System.out.println("pInformacion : " + paramString);
    System.out.println("nombre : " + strNombre.getNombre());
    System.out.println("version : " + strNombre.getVersion());
  }

  public static void establecerInformacionAccesoLocal(String paramString)
  {
    strNombre.setUrlAccesoLocal(paramString);
    System.out.println("URL local: " + strNombre.getUrlAccesoLocal());
  }

  public static void establecerInformacionAccesoRemoto(String paramString)
  {
    strNombre.setUrlAccesoRemoto(paramString);
    System.out.println("URL remoto: " + strNombre.getUrlAccesoRemoto());
  }

  private static String strNombre(String paramString)
  {
    int i = paramString.indexOf('/');
    if (i == -1)
      return paramString;
    return (String)paramString.subSequence(0, i);
  }

  private static String strCodigo(String paramString)
  {
    int i = paramString.indexOf('/');
    if (i == -1)
      return null;
    return paramString.substring(i + 1);
  }
}