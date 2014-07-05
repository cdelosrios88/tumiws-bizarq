package pe.com.tumi.framework.vista.tag.jsf.components;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class UITextInput extends UIOutput
{
  public static final String COMPONENT_TYPE = "component.UITextInput";
  public static final String COMPONENT_FAMILY = "UITextInput";
  public static final String RENDERER_TYPE = "renderer.UITextInput";
  private String strNombre = null;
  private Object strCodigo = null;
  private Object strEnlace = null;
  private Object strSession = null;
  private String strIp = null;
  private String strValor = null;
  private String strCliente = null;
  private String strCaso = null;
  private String strEstado = null;
  private String strVar1 = null;
  private String strVar2 = null;

  public UITextInput()
  {
    setRendererType("renderer.UITextInput");
  }

  public Object saveState(FacesContext paramFacesContext)
  {
    Object[] arrayOfObject = new Object[12];
    arrayOfObject[0] = super.saveState(paramFacesContext);
    arrayOfObject[1] = this.strNombre;
    arrayOfObject[2] = this.strCodigo;
    arrayOfObject[3] = this.strEnlace;
    arrayOfObject[4] = this.strSession;
    arrayOfObject[5] = this.strIp;
    arrayOfObject[6] = this.strValor;
    arrayOfObject[7] = this.strCliente;
    arrayOfObject[8] = this.strCaso;
    arrayOfObject[9] = this.strEstado;
    arrayOfObject[10] = this.strVar1;
    arrayOfObject[11] = this.strVar2;
    return arrayOfObject;
  }

  public void restoreState(FacesContext paramFacesContext, Object paramObject)
  {
    Object[] arrayOfObject = (Object[])paramObject;
    super.restoreState(paramFacesContext, arrayOfObject[0]);
    this.strNombre = ((String)arrayOfObject[1]);
    this.strCodigo = arrayOfObject[2];
    this.strEnlace = arrayOfObject[3];
    this.strSession = arrayOfObject[4];
    this.strIp = ((String)arrayOfObject[5]);
    this.strValor = ((String)arrayOfObject[6]);
    this.strCliente = ((String)arrayOfObject[7]);
    this.strCaso = ((String)arrayOfObject[8]);
    this.strEstado = ((String)arrayOfObject[9]);
    this.strVar1 = ((String)arrayOfObject[10]);
    this.strVar2 = ((String)arrayOfObject[11]);
  }

  public String getFamily()
  {
    return "UITextInput";
  }

  public String getId()
  {
    return this.strNombre;
  }

  public void setId(String paramString)
  {
    this.strNombre = paramString;
  }

  public Object getValue()
  {
    if (this.strCodigo != null)
      return this.strCodigo;
    ValueBinding localValueBinding = getValueBinding("value");
    Object localObject = localValueBinding != null ? localValueBinding.getValue(getFacesContext()) : null;
    return localObject;
  }

  public void setValue(Object paramObject)
  {
    this.strCodigo = paramObject;
  }

  public Object getCache()
  {
    if (this.strEnlace != null)
      return this.strEnlace;
    ValueBinding localValueBinding = getValueBinding("cache");
    Object localObject = localValueBinding != null ? localValueBinding.getValue(getFacesContext()) : null;
    return localObject;
  }

  public void setCache(Object paramObject)
  {
    this.strEnlace = paramObject;
  }

  public Object getProperty()
  {
    if (this.strSession != null)
      return this.strSession;
    ValueBinding localValueBinding = getValueBinding("property");
    Object localObject = localValueBinding != null ? localValueBinding.getValue(getFacesContext()) : null;
    return localObject;
  }

  public void setProperty(Object paramObject)
  {
    this.strSession = paramObject;
  }

  public String getItemValue()
  {
    return this.strIp;
  }

  public void setItemValue(String paramString)
  {
    this.strIp = paramString;
  }

  public String getItemLabel()
  {
    return this.strValor;
  }

  public void setItemLabel(String paramString)
  {
    this.strValor = paramString;
  }

  public String getStyle()
  {
    return this.strVar1;
  }

  public void setStyle(String paramString)
  {
    this.strVar1 = paramString;
  }

  public String getStyleClass()
  {
    return this.strVar2;
  }

  public void setStyleClass(String paramString)
  {
    this.strVar2 = paramString;
  }

  public String getDisabled()
  {
    return this.strCliente;
  }

  public void setDisabled(String paramString)
  {
    this.strCliente = paramString;
  }

  public String getSize()
  {
    return this.strCaso;
  }

  public void setSize(String paramString)
  {
    this.strCaso = paramString;
  }

  public String getReadonly()
  {
    return this.strEstado;
  }

  public void setReadonly(String paramString)
  {
    this.strEstado = paramString;
  }
}