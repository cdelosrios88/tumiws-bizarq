package pe.com.tumi.framework.vista.tag.jsf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

import pe.com.tumi.framework.negocio.cache.JCache;
import pe.com.tumi.framework.negocio.cache.exception.CacheException;
import pe.com.tumi.framework.util.reflection.JReflection;
import pe.com.tumi.framework.util.sort.common.SortComparator;
import pe.com.tumi.framework.vista.tag.common.TagUtil;

public class HTMLSelectItemsTag extends UIComponentTag
{
  private String strNombre;
  private String strCodigo;
  private String strEnlace;
  private String strSession;
  private String strIp;
  private String strValor;
  private String strCliente;

  public String getComponentType()
  {
    return "component.UISelectItems";
  }

  protected void setProperties(UIComponent paramUIComponent)
  {
    super.setProperties(paramUIComponent);
    Object localObject1;
    if (this.strNombre != null)
      if (isValueReference(this.strNombre))
      {
        localObject1 = getFacesContext().getApplication().createValueBinding(this.strNombre);
        paramUIComponent.setValueBinding("var", (ValueBinding)localObject1);
      }
      else
      {
        paramUIComponent.getAttributes().put("var", this.strNombre);
      }
    if (this.strEnlace != null)
      if (isValueReference(this.strEnlace))
      {
        localObject1 = getFacesContext().getApplication().createValueBinding(this.strEnlace);
        paramUIComponent.setValueBinding("itemLabel", (ValueBinding)localObject1);
      }
      else
      {
        paramUIComponent.getAttributes().put("itemLabel", this.strEnlace);
      }
    if (this.strSession != null)
      if (isValueReference(this.strSession))
      {
        localObject1 = getFacesContext().getApplication().createValueBinding(this.strSession);
        paramUIComponent.setValueBinding("itemValue", (ValueBinding)localObject1);
      }
      else
      {
        paramUIComponent.getAttributes().put("itemValue", this.strSession);
      }
    if (this.strCliente != null)
      if (isValueReference(this.strCliente))
      {
        localObject1 = getFacesContext().getApplication().createValueBinding(this.strCliente);
        paramUIComponent.setValueBinding("propertySort", (ValueBinding)localObject1);
      }
      else
      {
        paramUIComponent.getAttributes().put("propertySort", this.strCliente);
      }
    if (this.strValor != null)
      if (isValueReference(this.strValor))
      {
        localObject1 = getFacesContext().getApplication().createValueBinding(this.strValor);
        paramUIComponent.setValueBinding("value", (ValueBinding)localObject1);
      }
      else
      {
        paramUIComponent.getAttributes().put("value", this.strValor);
      }
    if (this.strCodigo != null)
      if (isValueReference(this.strCodigo))
        this.strCodigo = ((String)TagUtil.getValueDeBindingPorExpresion(getFacesContext(), this.strCodigo));
      else
        paramUIComponent.getAttributes().put("tipoVista", this.strCodigo);
    if (this.strIp != null)
    {
      Object localObject2 = null;
      Object localObject3 = null;
      try
      {
        if (isValueReference(this.strIp))
        {
          this.strIp = ((String)TagUtil.getValueDeBindingPorExpresion(getFacesContext(), this.strIp));
          ValueBinding localValueBinding = getFacesContext().getApplication().createValueBinding(this.strIp);
          paramUIComponent.setValueBinding("cache", localValueBinding);
        }
        else
        {
          paramUIComponent.getAttributes().put("cache", this.strIp);
        }
        localObject1 = JCache.getListaTablaByIdTabla(this.strIp);
        if ((localObject1 != null) && (((List)localObject1).size() > 1))
        {
          if ((this.strCodigo == null) || (this.strCodigo.equals("0")))
          {
            ArrayList localArrayList = new ArrayList();
            for (int i = 0; i < ((List)localObject1).size(); i++)
            {
              localObject2 = ((List)localObject1).get(i);
              localObject3 = JReflection.getProperty(localObject2, "tipoVisualizacion");
              if ((localObject3 == null) || (!localObject3.toString().equals("0")))
                continue;
              localArrayList.add(localObject2);
            }
            localObject1 = localArrayList;
          }
          if (this.strCliente != null)
            Collections.sort((List)localObject1, new SortComparator(this.strCliente));
          else
            Collections.sort((List)localObject1, new SortComparator(TagUtil.getPropertyDeExpresion(this.strNombre, this.strEnlace)));
        }
        paramUIComponent.getAttributes().put("value", localObject1);
      }
      catch (CacheException localCacheException)
      {
        localCacheException.printStackTrace();
      }
    }
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
  }

  public String getVar()
  {
    return this.strNombre;
  }

  public String getValue()
  {
    return this.strValor;
  }

  public void setValue(String paramString)
  {
    this.strValor = paramString;
  }

  public void setVar(String paramString)
  {
    this.strNombre = paramString;
  }

  public String getItemLabel()
  {
    return this.strEnlace;
  }

  public void setItemLabel(String paramString)
  {
    this.strEnlace = paramString;
  }

  public String getItemValue()
  {
    return this.strSession;
  }

  public void setItemValue(String paramString)
  {
    this.strSession = paramString;
  }

  public String getRendererType()
  {
    return null;
  }

  public String getCache()
  {
    return this.strIp;
  }

  public void setCache(String paramString)
  {
    this.strIp = paramString;
  }

  public String getTipoVista()
  {
    return this.strCodigo;
  }

  public void setTipoVista(String paramString)
  {
    this.strCodigo = paramString;
  }

  public String getPropertySort()
  {
    return this.strCliente;
  }

  public void setPropertySort(String paramString)
  {
    this.strCliente = paramString;
  }
}