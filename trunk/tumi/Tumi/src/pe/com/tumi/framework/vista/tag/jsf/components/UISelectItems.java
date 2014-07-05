package pe.com.tumi.framework.vista.tag.jsf.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import pe.com.tumi.framework.util.sort.common.SortComparator;

public class UISelectItems extends javax.faces.component.UISelectItems
{
  public static final String COMPONENT_TYPE_EX = "component.UISelectItems";
  public static final String COMPONENT_FAMILY_EX = "component.UISelectItems";
  private String strNombre;
  private Object strCodigo;
  private Object strEnlace;
  private String strSession;
  private String strIp;
  private String strValor;

  public String getVar()
  {
    String str = null;
    if (this.strNombre != null)
    {
      str = this.strNombre;
    }
    else
    {
      ValueBinding localValueBinding = getValueBinding("var");
      str = localValueBinding != null ? (String)localValueBinding.getValue(getFacesContext()) : null;
    }
    return str;
  }

  public void setVar(String paramString)
  {
    this.strNombre = paramString;
  }

  public String getPropertySort()
  {
    String str = null;
    if (this.strIp != null)
    {
      str = this.strIp;
    }
    else
    {
      ValueBinding localValueBinding = getValueBinding("propertySort");
      str = localValueBinding != null ? (String)localValueBinding.getValue(getFacesContext()) : null;
    }
    return str;
  }

  public void setPropertySort(String paramString)
  {
    this.strIp = paramString;
  }

  public String getCache()
  {
    String str = null;
    if (this.strSession != null)
    {
      str = this.strSession;
    }
    else
    {
      ValueBinding localValueBinding = getValueBinding("cache");
      str = localValueBinding != null ? (String)localValueBinding.getValue(getFacesContext()) : null;
    }
    return str;
  }

  public Object getItemLabel()
  {
    Object localObject = null;
    if (this.strCodigo != null)
    {
      localObject = this.strCodigo;
    }
    else
    {
      ValueBinding localValueBinding = getValueBinding("itemLabel");
      localObject = localValueBinding != null ? localValueBinding.getValue(getFacesContext()) : null;
    }
    return localObject;
  }

  public void setItemLabel(Object paramObject)
  {
    this.strCodigo = paramObject;
  }

  public Object getItemValue()
  {
    Object localObject = null;
    if (this.strEnlace != null)
    {
      localObject = this.strEnlace;
    }
    else
    {
      ValueBinding localValueBinding = getValueBinding("itemValue");
      localObject = localValueBinding != null ? localValueBinding.getValue(getFacesContext()) : null;
    }
    return localObject;
  }

  public void setItemValue(Object paramObject)
  {
    this.strEnlace = paramObject;
  }

  public String getTipoVista()
  {
    String str = null;
    if (this.strValor != null)
    {
      str = this.strValor;
    }
    else
    {
      ValueBinding localValueBinding = getValueBinding("tipoVista");
      str = localValueBinding != null ? (String)localValueBinding.getValue(getFacesContext()) : null;
    }
    return str;
  }

  public void setTipoVista(String paramString)
  {
    this.strValor = paramString;
  }

  public Object getValue()
  {
    Object localObject = super.getValue();
    if (localObject != null)
    {
      String str1 = null;
      String str2 = getCache();
      if (str2 == null)
      {
        List localList = (List)localObject;
        if ((localList != null) && (localList.size() > 1))
        {
          str1 = getPropertySort();
          if (str1 != null)
            Collections.sort(localList, new SortComparator(str1));
        }
      }
    }
    return strNombre(localObject);
  }

  private SelectItem[] strNombre(Object paramObject)
  {
    SelectItem[] arrayOfSelectItem = (SelectItem[])null;
    if (paramObject == null)
      arrayOfSelectItem = new SelectItem[0];
    else if ((paramObject instanceof SelectItem[]))
      arrayOfSelectItem = (SelectItem[])paramObject;
    else if ((paramObject instanceof Collection))
      arrayOfSelectItem = strNombre((Collection)paramObject);
    else if ((paramObject instanceof Map))
      arrayOfSelectItem = strNombre((Map)paramObject);
    return arrayOfSelectItem;
  }

  private SelectItem[] strNombre(Collection paramCollection)
  {
    Object localObject = null;
    SelectItemGroup localSelectItemGroup = null;
    SelectItem[] arrayOfSelectItem = (SelectItem[])null;
    ArrayList localArrayList = new ArrayList();
    Collection localCollection = paramCollection;
    Iterator localIterator = localCollection.iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if ((localObject instanceof SelectItemGroup))
      {
        localSelectItemGroup = (SelectItemGroup)localObject;
        arrayOfSelectItem = localSelectItemGroup.getSelectItems();
        for (int i = 0; i < arrayOfSelectItem.length; i++)
          localArrayList.add(arrayOfSelectItem[i]);
      }
      else
      {
        strCodigo(localObject);
        SelectItem localSelectItem = strNombre();
        strCodigo();
        localArrayList.add(localSelectItem);
      }
    }
    return (SelectItem[])localArrayList.toArray(new SelectItem[0]);
  }

  private SelectItem[] strNombre(Map paramMap)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      strCodigo(localEntry.getValue());
      SelectItem localSelectItem = strNombre();
      strCodigo();
      localArrayList.add(localSelectItem);
    }
    return (SelectItem[])localArrayList.toArray(new SelectItem[0]);
  }

  private SelectItem strNombre()
  {
    Object localObject = getItemValue();
    String str = getItemLabel() != null ? getItemLabel().toString() : "";
    SelectItem localSelectItem = new SelectItem(localObject, str);
    return localSelectItem;
  }

  private void strCodigo(Object paramObject)
  {
    FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put(getVar(), paramObject);
  }

  private void strCodigo()
  {
    FacesContext.getCurrentInstance().getExternalContext().getRequestMap().remove(getVar());
  }

  public Object saveState(FacesContext paramFacesContext)
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = super.saveState(paramFacesContext);
    arrayOfObject[1] = this.strNombre;
    arrayOfObject[2] = this.strValor;
    arrayOfObject[3] = this.strSession;
    arrayOfObject[4] = this.strCodigo;
    arrayOfObject[5] = this.strEnlace;
    arrayOfObject[6] = this.strIp;
    return arrayOfObject;
  }

  public void restoreState(FacesContext paramFacesContext, Object paramObject)
  {
    Object[] arrayOfObject = (Object[])paramObject;
    super.restoreState(paramFacesContext, arrayOfObject[0]);
    this.strNombre = ((String)arrayOfObject[1]);
    this.strValor = ((String)arrayOfObject[2]);
    this.strSession = ((String)arrayOfObject[3]);
    this.strCodigo = arrayOfObject[4];
    this.strEnlace = arrayOfObject[5];
    this.strIp = ((String)arrayOfObject[6]);
  }

  public String getFamily()
  {
    return "component.UISelectItems";
  }
}