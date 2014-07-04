package pe.com.tumi.framework.vista.tag.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import pe.com.tumi.framework.util.reflection.JReflection;

public class TagUtil
{
  public static String getPropertyDeExpresion(String paramString1, String paramString2)
  {
    Pattern localPattern = Pattern.compile("(\\{" + paramString1 + ".)(.*?)(})");
    Matcher localMatcher = localPattern.matcher(paramString2);
    if ((localMatcher.find()) && (localMatcher.groupCount() >= 3))
      return localMatcher.group(2);
    return null;
  }

  public static String[] getParametroDeOnClick(String paramString)
  {
    String[] arrayOfString = (String[])null;
    String str = null;
    Pattern localPattern = Pattern.compile("(.*?,)('.*')(,|s|.)*");
    Matcher localMatcher = localPattern.matcher(paramString);
    while (localMatcher.find())
      str = localMatcher.group(2);
    str = str.replace("'", "");
    arrayOfString = str.split(",");
    return arrayOfString;
  }

  public static Object getValueDeBindingPorExpresion(FacesContext paramFacesContext, String paramString)
  {
    ValueBinding localValueBinding = paramFacesContext.getApplication().createValueBinding(paramString);
    Object localObject = localValueBinding != null ? localValueBinding.getValue(paramFacesContext) : null;
    return localObject;
  }

  public static Object buscarValorByListaCodigoProperty(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Boolean localBoolean = Boolean.valueOf(true);
    Object localObject3 = null;
    if ((paramObject1 instanceof Object[]))
    {
      localObject3 = Arrays.asList(new Object[] { paramObject1 });
      if (((Collection)localObject3).size() > 0)
        localObject2 = strNombre((Collection)localObject3, paramObject2, paramObject3, paramObject4);
    }
    else if ((paramObject1 instanceof Collection))
    {
      localObject3 = (Collection)paramObject1;
      if (((Collection)localObject3).size() > 0)
        localObject2 = strNombre((Collection)localObject3, paramObject2, paramObject3, paramObject4);
    }
    else if ((paramObject1 instanceof Map))
    {
      Map localMap = (Map)paramObject1;
      Map.Entry localEntry = null;
      if (localMap.size() > 0)
      {
        Iterator localIterator = localMap.entrySet().iterator();
        while ((localIterator.hasNext()) && (localBoolean.booleanValue()))
        {
          localEntry = (Map.Entry)localIterator.next();
          localObject1 = JReflection.getProperty(localEntry.getValue(), paramObject3.toString());
          if ((localObject1 == null) || (!localObject1.toString().equals(paramObject2.toString())))
            continue;
          localObject2 = JReflection.getProperty(localEntry, paramObject4.toString());
          localBoolean = Boolean.valueOf(false);
        }
      }
    }
    return localObject2;
  }

  private static Object strNombre(Collection paramCollection, Object paramObject1, Object paramObject2, Object paramObject3)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Boolean localBoolean = Boolean.valueOf(true);
    Iterator localIterator = paramCollection.iterator();
    while ((localIterator.hasNext()) && (localBoolean.booleanValue()))
    {
      localObject3 = localIterator.next();
      localObject1 = JReflection.getProperty(localObject3, paramObject2.toString());
      if ((localObject1 == null) || (!localObject1.toString().equals(paramObject1.toString())))
        continue;
      localObject2 = JReflection.getProperty(localObject3, paramObject3.toString());
      localBoolean = Boolean.valueOf(false);
    }
    return localObject2;
  }
}