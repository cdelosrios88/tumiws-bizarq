package pe.com.tumi.framework.vista.tag.jsf;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

import pe.com.tumi.framework.negocio.cache.JCache;
import pe.com.tumi.framework.negocio.cache.exception.CacheException;
import pe.com.tumi.framework.vista.tag.common.TagUtil;

public class HTMLTextInputTag extends UIComponentTag
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

  public String getComponentType()
  {
    return "component.UITextInput";
  }

  public String getRendererType()
  {
    return "renderer.UITextInput";
  }

  public void setProperties(UIComponent paramUIComponent)
  {
    super.setProperties(paramUIComponent);
    setString(paramUIComponent, "id", this.strNombre);
    setString(paramUIComponent, "value", this.strCodigo);
    setString(paramUIComponent, "cache", this.strEnlace);
    setString(paramUIComponent, "property", this.strSession);
    setString(paramUIComponent, "itemValue", this.strIp);
    setString(paramUIComponent, "itemLabel", this.strValor);
    setString(paramUIComponent, "size", this.strCliente);
    setString(paramUIComponent, "disabled", this.strEstado);
    setString(paramUIComponent, "readonly", this.strCaso);
    setString(paramUIComponent, "style", this.strVar1);
    setString(paramUIComponent, "styleClass", this.strVar2);
  }

  public void setString(UIComponent paramUIComponent, String paramString1, String paramString2)
  {
    if (paramString2 == null)
      return;
    try
    {
      if (isValueReference(paramString2))
      {
        if (paramString1.equals("cache"))
        {
          Object localObject = TagUtil.getValueDeBindingPorExpresion(getFacesContext(), this.strEnlace);
          List localList = null;
          if (localObject != null)
            localList = JCache.getListaTablaByIdTabla(localObject.toString());
          else
            System.out.println("No hay valor en Cache para expresion: " + this.strEnlace);
          paramUIComponent.getAttributes().put("cache", localList);
        }
        else
        {
          setValueBinding(paramUIComponent, paramString1, paramString2);
        }
      }
      else
        paramUIComponent.getAttributes().put(paramString1, paramString2);
    }
    catch (CacheException localCacheException)
    {
      localCacheException.printStackTrace();
    }
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
    this.strEstado = null;
    this.strCaso = null;
    this.strVar1 = null;
    this.strVar2 = null;
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

  public String getCache()
  {
    return this.strEnlace;
  }

  public void setCache(String paramString)
  {
    this.strEnlace = paramString;
  }

  public String getProperty()
  {
    return this.strSession;
  }

  public void setProperty(String paramString)
  {
    this.strSession = paramString;
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

  public String getSize()
  {
    return this.strCliente;
  }

  public void setSize(String paramString)
  {
    this.strCliente = paramString;
  }

  public String getReadonly()
  {
    return this.strCaso;
  }

  public void setReadonly(String paramString)
  {
    this.strCaso = paramString;
  }

  public String getDisabled()
  {
    return this.strEstado;
  }

  public void setDisabled(String paramString)
  {
    this.strEstado = paramString;
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
}