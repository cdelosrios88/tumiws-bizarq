package pe.com.tumi.framework.configuracion.info.bean;

public class InformacionBean
{
  private String strNombre;
  private String strCodigo;
  private String strEnlace;
  private String strSession;
  private String strIp;
  private String strValor;

  public String getNombre()
  {
    return this.strNombre;
  }

  public void setNombre(String paramString)
  {
    this.strNombre = paramString;
  }

  public String getVersion()
  {
    return this.strCodigo;
  }

  public void setVersion(String paramString)
  {
    this.strCodigo = paramString;
  }

  public String getUrlAccesoLocal()
  {
    return this.strEnlace;
  }

  public void setUrlAccesoLocal(String paramString)
  {
    this.strEnlace = paramString;
  }

  public String getUrlAccesoRemoto()
  {
    return this.strSession;
  }

  public void setUrlAccesoRemoto(String paramString)
  {
    this.strSession = paramString;
  }

  public String getClassConstante()
  {
    return this.strIp;
  }

  public void setClassConstante(String paramString)
  {
    this.strIp = paramString;
  }

  public String getPathLog4j()
  {
    return this.strValor;
  }

  public void setPathLog4j(String paramString)
  {
    this.strValor = paramString;
  }
}