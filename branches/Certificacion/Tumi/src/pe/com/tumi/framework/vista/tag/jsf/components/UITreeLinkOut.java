package pe.com.tumi.framework.vista.tag.jsf.components;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

public class UITreeLinkOut extends UIOutput
{
  public static final String COMPONENT_TYPE = "component.UITreeLinkOut";
  public static final String COMPONENT_FAMILY = "UITreeLinkOut";
  public static final String RENDERER_TYPE = "renderer.UITreeLinkOut";
  private String strNombre = null;
  private Object strCodigo = null;
  private String strEnlace = null;
  private String strSession = null;
  private String strIp = null;
  private String strValor = null;
  private String strCliente = null;
  private String strCaso = null;

  public UITreeLinkOut()
  {
    setRendererType("renderer.UITreeLinkOut");
  }

  public Object saveState(FacesContext paramFacesContext)
  {
    Object[] arrayOfObject = new Object[9];
    arrayOfObject[0] = super.saveState(paramFacesContext);
    arrayOfObject[1] = this.strNombre;
    arrayOfObject[2] = this.strCodigo;
    arrayOfObject[3] = this.strEnlace;
    arrayOfObject[4] = this.strSession;
    arrayOfObject[5] = this.strIp;
    arrayOfObject[6] = this.strValor;
    arrayOfObject[7] = this.strCliente;
    arrayOfObject[8] = this.strCaso;
    return arrayOfObject;
  }

  public void restoreState(FacesContext paramFacesContext, Object paramObject)
  {
    Object[] arrayOfObject = (Object[])paramObject;
    super.restoreState(paramFacesContext, arrayOfObject[0]);
    this.strNombre = ((String)arrayOfObject[1]);
    this.strCodigo = arrayOfObject[2];
    this.strEnlace = ((String)arrayOfObject[3]);
    this.strSession = ((String)arrayOfObject[4]);
    this.strIp = ((String)arrayOfObject[5]);
    this.strValor = ((String)arrayOfObject[6]);
    this.strCliente = ((String)arrayOfObject[7]);
    this.strCaso = ((String)arrayOfObject[8]);
  }

  public String getFamily()
  {
    return "UITreeLinkOut";
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

  public String getProperty()
  {
    return this.strEnlace;
  }

  public void setProperty(String paramString)
  {
    this.strEnlace = paramString;
  }

  public String getLinkOnClick()
  {
    return this.strSession;
  }

  public void setLinkOnClick(String paramString)
  {
    this.strSession = paramString;
  }

  public String getLinkTarget()
  {
    return this.strIp;
  }

  public void setLinkTarget(String paramString)
  {
    this.strIp = paramString;
  }

  public String getLinkLabel()
  {
    return this.strValor;
  }

  public void setLinkLabel(String paramString)
  {
    this.strValor = paramString;
  }

  public String getStyle()
  {
    return this.strCliente;
  }

  public void setStyle(String paramString)
  {
    this.strCliente = paramString;
  }

  public String getStyleClass()
  {
    return this.strCaso;
  }

  public void setStyleClass(String paramString)
  {
    this.strCaso = paramString;
  }
}