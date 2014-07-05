package pe.com.tumi.framework.negocio.domain;

import java.io.Serializable;

public abstract class TumiDomain
  implements Serializable
{
  protected Boolean checked;
  protected Boolean persiste = new Boolean(false);

  public Boolean getChecked()
  {
    return this.checked;
  }

  public void setChecked(Boolean paramBoolean)
  {
    this.checked = paramBoolean;
  }

  public Boolean getPersiste()
  {
    return this.persiste;
  }

  public void setPersiste(Boolean paramBoolean)
  {
    this.persiste = paramBoolean;
  }
}