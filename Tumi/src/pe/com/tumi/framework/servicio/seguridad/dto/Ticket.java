package pe.com.tumi.framework.servicio.seguridad.dto;

import java.sql.Timestamp;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Ticket extends TumiDomain
{
  private String strNombre;
  private boolean strCodigo;
  private Object strEnlace;
  private Timestamp strSession;

  public String getId()
  {
    return this.strNombre;
  }

  public void setId(String paramString)
  {
    this.strNombre = paramString;
  }

  public boolean isExpired()
  {
    return this.strCodigo;
  }

  public void setExpired(boolean paramBoolean)
  {
    this.strCodigo = paramBoolean;
  }

  public Object getUsuario()
  {
    return this.strEnlace;
  }

  public void setUsuario(Object paramObject)
  {
    this.strEnlace = paramObject;
  }

  public Timestamp getTsFechaInicio()
  {
    return this.strSession;
  }

  public void setTsFechaInicio(Timestamp paramTimestamp)
  {
    this.strSession = paramTimestamp;
  }
}