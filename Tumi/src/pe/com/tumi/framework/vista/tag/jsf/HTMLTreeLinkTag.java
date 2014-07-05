package pe.com.tumi.framework.vista.tag.jsf;

import java.util.Map;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

public class HTMLTreeLinkTag extends UIComponentTag
{
  private String strNombre = null;
  private String strCodigo = null;
  private String strEnlace = null;
  private String strSession = null;
  private String strIp = null;
  private String strValor = null;
  private String strCliente = null;
  private String strCaso = null;

  public String getComponentType()
  {
    return "component.UITreeLinkOut";
  }

  public String getRendererType()
  {
    return "renderer.UITreeLinkOut";
  }

  public void setProperties(UIComponent paramUIComponent)
  {
    super.setProperties(paramUIComponent);
    setString(paramUIComponent, "id", this.strNombre);
    setString(paramUIComponent, "value", this.strCodigo);
    setString(paramUIComponent, "property", this.strEnlace);
    setString(paramUIComponent, "linkOnClick", this.strSession);
    setString(paramUIComponent, "linkTarget", this.strIp);
    setString(paramUIComponent, "linkLabel", this.strValor);
    setString(paramUIComponent, "style", this.strCliente);
    setString(paramUIComponent, "styleClass", this.strCaso);
  }

  public void setString(UIComponent paramUIComponent, String paramString1, String paramString2)
  {
    if (paramString2 == null)
      return;
    if (isValueReference(paramString2))
      setValueBinding(paramUIComponent, paramString1, paramString2);
    else
      paramUIComponent.getAttributes().put(paramString1, paramString2);
  }

  public void setValueBinding(UIComponent paramUIComponent, String paramString1, String paramString2)
  {
    FacesContext localFacesContext = FacesContext.getCurrentInstance();
    Application localApplication = localFacesContext.getApplication();
    ValueBinding localValueBinding = localApplication.createValueBinding(paramString2);
    paramUIComponent.setValueBinding(paramString1, localValueBinding);
  }

  public void release()
  {
    super.release();
    this.strNombre = null;
    this.strCodigo = null;
    this.strEnlace = null;
    this.strSession = null;
    this.strIp = null;
    this.strValor = null;
    this.strCliente = null;
    this.strCaso = null;
  }

  public String getId()
  {
    return this.strNombre;
  }

  public void setId(String paramString)
  {
    this.strNombre = paramString;
  }

  public String getValue()
  {
    return this.strCodigo;
  }

  public void setValue(String paramString)
  {
    this.strCodigo = paramString;
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