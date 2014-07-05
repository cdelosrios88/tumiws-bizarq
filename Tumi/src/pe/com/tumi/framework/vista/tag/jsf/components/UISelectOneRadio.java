package pe.com.tumi.framework.vista.tag.jsf.components;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class UISelectOneRadio extends UIInput
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

  public String returnValueBindingAsString(String paramString)
  {
    Object localObject = null;
    ValueBinding localValueBinding = getValueBinding(paramString);
    if (localValueBinding != null)
      localObject = localValueBinding.getValue(getFacesContext());
    if (localObject != null)
      return localObject.toString();
    return null;
  }

  public String getDisabled()
  {
    if (this.strIp != null)
      return this.strIp;
    return returnValueBindingAsString("disabled");
  }

  public String getItemLabel()
  {
    if (this.strValor != null)
      return this.strValor;
    return returnValueBindingAsString("itemLabel");
  }

  public String getItemValue()
  {
    if (this.strCliente != null)
      return this.strCliente;
    return returnValueBindingAsString("itemValue");
  }

  public String getName()
  {
    if (this.strNombre != null)
      return this.strNombre;
    return returnValueBindingAsString("name");
  }

  public String getOnBlur()
  {
    if (this.strVar3 != null)
      return this.strVar3;
    return returnValueBindingAsString("onBlur");
  }

  public String getOnClick()
  {
    if (this.strCaso != null)
      return this.strCaso;
    return returnValueBindingAsString("onClick");
  }

  public String getOnFocus()
  {
    if (this.strVar2 != null)
      return this.strVar2;
    return returnValueBindingAsString("onFocus");
  }

  public String getOnMouseOut()
  {
    if (this.strVar1 != null)
      return this.strVar1;
    return returnValueBindingAsString("onMouseOut");
  }

  public String getOnMouseOver()
  {
    if (this.strEstado != null)
      return this.strEstado;
    return returnValueBindingAsString("onMouseOver");
  }

  public String getOverrideName()
  {
    if (this.strCodigo != null)
      return this.strCodigo;
    return returnValueBindingAsString("overrideName");
  }

  public String getStyle()
  {
    if (this.strSession != null)
      return this.strSession;
    return returnValueBindingAsString("style");
  }

  public String getStyleClass()
  {
    if (this.strEnlace != null)
      return this.strEnlace;
    return returnValueBindingAsString("styleClass");
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
    this.strCodigo = paramString;
  }

  public void setStyle(String paramString)
  {
    this.strSession = paramString;
  }

  public void setStyleClass(String paramString)
  {
    this.strEnlace = paramString;
  }

  public Object saveState(FacesContext paramFacesContext)
  {
    Object[] arrayOfObject = new Object[13];
    arrayOfObject[0] = super.saveState(paramFacesContext);
    arrayOfObject[1] = this.strEnlace;
    arrayOfObject[2] = this.strSession;
    arrayOfObject[3] = this.strIp;
    arrayOfObject[4] = this.strValor;
    arrayOfObject[5] = this.strCliente;
    arrayOfObject[6] = this.strCaso;
    arrayOfObject[7] = this.strEstado;
    arrayOfObject[8] = this.strVar1;
    arrayOfObject[9] = this.strVar2;
    arrayOfObject[10] = this.strVar3;
    arrayOfObject[11] = this.strNombre;
    arrayOfObject[12] = this.strCodigo;
    return arrayOfObject;
  }

  public void restoreState(FacesContext paramFacesContext, Object paramObject)
  {
    Object[] arrayOfObject = (Object[])paramObject;
    super.restoreState(paramFacesContext, arrayOfObject[0]);
    this.strEnlace = ((String)arrayOfObject[1]);
    this.strSession = ((String)arrayOfObject[2]);
    this.strIp = ((String)arrayOfObject[3]);
    this.strValor = ((String)arrayOfObject[4]);
    this.strCliente = ((String)arrayOfObject[5]);
    this.strCaso = ((String)arrayOfObject[6]);
    this.strEstado = ((String)arrayOfObject[7]);
    this.strVar1 = ((String)arrayOfObject[8]);
    this.strVar2 = ((String)arrayOfObject[9]);
    this.strVar3 = ((String)arrayOfObject[10]);
    this.strNombre = ((String)arrayOfObject[11]);
    this.strCodigo = ((String)arrayOfObject[12]);
  }

  public String getFamily()
  {
    return "CustomSelectOneRadio";
  }
}