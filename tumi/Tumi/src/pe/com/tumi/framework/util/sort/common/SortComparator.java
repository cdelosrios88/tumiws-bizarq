package pe.com.tumi.framework.util.sort.common;

import java.util.Comparator;

import pe.com.tumi.framework.util.reflection.JReflection;

public class SortComparator
  implements Comparator
{
  private String strNombre;

  public SortComparator(String paramString)
  {
    this.strNombre = paramString;
  }

  public int compare(Object paramObject1, Object paramObject2)
  {
    int i = 0;
    Object localObject1 = null;
    Object localObject2 = null;
    localObject1 = JReflection.getProperty(paramObject1, this.strNombre);
    localObject2 = JReflection.getProperty(paramObject2, this.strNombre);
    if (localObject1.getClass().getName().equals("java.lang.Integer"))
    {
      if (localObject2.getClass().getName().equals("java.lang.Integer"))
        i = ((Integer)localObject1).compareTo((Integer)localObject2);
      else
        i = -1;
    }
    else
      i = ((String)localObject1).compareTo((String)localObject2);
    return i;
  }

  public boolean equals(Object paramObject)
  {
    return this == paramObject;
  }
}