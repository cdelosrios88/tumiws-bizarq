package pe.com.tumi.framework.util.sort;

import java.util.Collections;
import java.util.List;

import pe.com.tumi.framework.util.sort.common.SortComparator;

public class JSort
{
  public static void sort(Object paramObject, String paramString)
  {
    Collections.sort((List)paramObject, new SortComparator(paramString));
  }
}