package pe.com.tumi.framework.vista.tag.jsf;

import java.util.Map;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

import pe.com.tumi.framework.vista.tag.jsf.components.UISelectOneRadio;

public class HTMLSelectOneRadioTag extends UIComponentTag
{
  private String strNombre = null;
  private String strCodigo = null;
  private String strEnlace = null;
  private String strSession = null;
  private String strIp = null;
  private String strValor = null;
  private String strCliente = null;
  private String strCaso = null;
  private String strEstado = null;
  private String strVar1 = null;
  private String strVar2 = null;
  private String strVar3 = null;
  private String strVar4 = null;

  public String getComponentType()
  {
    return "component.CustomSelectOneRadio";
  }

  public String getRendererType()
  {
    return "renderer.CustomSelectOneRadio";
  }

  public String getDisabled()
  {
    return this.strIp;
  }

  public String getItemLabel()
  {
    return this.strValor;
  }

  public String getItemValue()
  {
    return this.strCliente;
  }

  public String getName()
  {
    return this.strNombre;
  }

  public String getOnBlur()
  {
    return this.strVar3;
  }

  public String getOnClick()
  {
    return this.strCaso;
  }

  public String getOnFocus()
  {
    return this.strVar2;
  }

  public String getOnMouseOut()
  {
    return this.strVar1;
  }

  public String getOnMouseOver()
  {
    return this.strEstado;
  }

  public String getOverrideName()
  {
    return this.strVar4;
  }

  public String getStyle()
  {
    return this.strSession;
  }

  public String getStyleClass()
  {
    return this.strEnlace;
  }

  public String getValue()
  {
    return this.strCodigo;
  }

  public void setDisabled(String paramString)
  {
    this.strIp = paramString;
  }

  public void setItemLabel(String paramString)
  {
    this.strValor = paramString;
  }

  public void setItemValue(String paramString)
  {
    this.strCliente = paramString;
  }

  public void setName(String paramString)
  {
    this.strNombre = paramString;
  }

  public void setOnBlur(String paramString)
  {
    this.strVar3 = paramString;
  }

  public void setOnClick(String paramString)
  {
    this.strCaso = paramString;
  }

  public void setOnFocus(String paramString)
  {
    this.strVar2 = paramString;
  }

  public void setOnMouseOut(String paramString)
  {
    this.strVar1 = paramString;
  }

  public void setOnMouseOver(String paramString)
  {
    this.strEstado = paramString;
  }

  public void setOverrideName(String paramString)
  {
    this.strVar4 = paramString;
  }

  public void setStyle(String paramString)
  {
    this.strSession = paramString;
  }

  public void setStyleClass(String paramString)
  {
    this.strEnlace = paramString;
  }

  public void setValue(Object paramObject)
  {
    this.strCodigo = paramObject.toString();
  }

  public void setValue(String paramString)
  {
    this.strCodigo = paramString;
  }

  protected void setProperties(UIComponent paramUIComponent)
  {
    super.setProperties(paramUIComponent);
    UISelectOneRadio localUISelectOneRadio = (UISelectOneRadio)paramUIComponent;
    if (this.strNombre != null)
      if (isValueReference(this.strNombre))
        localUISelectOneRadio.setValueBinding("name", getValueBinding(this.strNombre));
      else
        localUISelectOneRadio.getAttributes().put("name", this.strNombre);
    if (this.strCodigo != null)
      if (isValueReference(this.strCodigo))
        localUISelectOneRadio.setValueBinding("value", getValueBinding(this.strCodigo));
      else
        localUISelectOneRadio.getAttributes().put("value", this.strCodigo);
    if (this.strEnlace != null)
      if (isValueReference(this.strEnlace))
        localUISelectOneRadio.setValueBinding("styleClass", getValueBinding(this.strEnlace));
      else
        localUISelectOneRadio.getAttributes().put("styleClass", this.strEnlace);
    if (this.strSession != null)
      if (isValueReference(this.strSession))
        localUISelectOneRadio.setValueBinding("style", getValueBinding(this.strSession));
      else
        localUISelectOneRadio.getAttributes().put("style", this.strSession);
    if (this.strIp != null)
      if (isValueReference(this.strIp))
      {
        ValueBinding localValueBinding = getFacesContext().getApplication().createValueBinding(this.strIp);
        paramUIComponent.setValueBinding("disabled", localValueBinding);
      }
      else
      {
        localUISelectOneRadio.getAttributes().put("disabled", this.strIp);
      }
    if (this.strValor != null)
      if (isValueReference(this.strValor))
        localUISelectOneRadio.setValueBinding("itemLabel", getValueBinding(this.strValor));
      else
        localUISelectOneRadio.getAttributes().put("itemLabel", this.strValor);
    if (this.strCliente != null)
      if (isValueReference(this.strCliente))
        localUISelectOneRadio.setValueBinding("itemValue", getValueBinding(this.strCliente));
      else
        localUISelectOneRadio.getAttributes().put("itemValue", this.strCliente);
    if (this.strCaso != null)
      if (isValueReference(this.strCaso))
        localUISelectOneRadio.setValueBinding("onClick", getValueBinding(this.strCaso));
      else
        localUISelectOneRadio.getAttributes().put("onClick", this.strCaso);
    if (this.strEstado != null)
      if (isValueReference(this.strEstado))
        localUISelectOneRadio.setValueBinding("onMouseOver", getValueBinding(this.strEstado));
      else
        localUISelectOneRadio.getAttributes().put("onMouseOver", this.strEstado);
    if (this.strVar1 != null)
      if (isValueReference(this.strVar1))
        localUISelectOneRadio.setValueBinding("onMouseOut", getValueBinding(this.strVar1));
      else
        localUISelectOneRadio.getAttributes().put("onMouseOut", this.strVar1);
    if (this.strVar2 != null)
      if (isValueReference(this.strVar2))
        localUISelectOneRadio.setValueBinding("onFocus", getValueBinding(this.strVar2));
      else
        localUISelectOneRadio.getAttributes().put("onFocus", this.strVar2);
    if (this.strVar3 != null)
      if (isValueReference(this.strVar3))
        localUISelectOneRadio.setValueBinding("onBlur", getValueBinding(this.strVar3));
      else
        localUISelectOneRadio.getAttributes().put("onBlur", this.strVar3);
    if (this.strVar4 != null)
      if (isValueReference(this.strVar4))
        localUISelectOneRadio.setValueBinding("overrideName", getValueBinding(this.strVar4));
      else
        localUISelectOneRadio.getAttributes().put("overrideName", this.strVar4);
  }

  public ValueBinding getValueBinding(String paramString)
  {
    ApplicationFactory localApplicationFactory = (ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
    Application localApplication = localApplicationFactory.getApplication();
    return localApplication.createValueBinding(paramString);
  }
}