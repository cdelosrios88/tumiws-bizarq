package pe.com.tumi.framework.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import pe.com.tumi.framework.util.file.JFile;
import pe.com.tumi.framework.util.stream.common.StreamUtil;

public class JProperties
{
  public static void main(String[] paramArrayOfString)
  {
    Map localMap = null;
    localMap = getMapDePropertiesPorPathRelativo("src/META-INF/j.properties");
    System.out.println(localMap.get("cache.was"));
    System.out.println(localMap.get("cache.jboss"));
  }

  public static Map getMapDePropertiesPorPathRelativo(String paramString)
  {
    HashMap localHashMap = null;
    Object localObject1 = null;
    InputStream localInputStream = null;
    Properties localProperties = null;
    try
    {
      localInputStream = JFile.obtieneInputStreamDeArchivoPorPath(paramString);
      localProperties = new Properties();
      localProperties.load(localInputStream);
      if (!localProperties.isEmpty())
      {
        localHashMap = new HashMap();
        Enumeration localEnumeration = localProperties.keys();
        while (localEnumeration.hasMoreElements())
        {
          localObject1 = localEnumeration.nextElement();
          localHashMap.put(localObject1.toString(), localProperties.getProperty(localObject1.toString()));
        }
      }
      localProperties.clear();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    finally
    {
      StreamUtil.strNombre(localInputStream);
    }
    return localHashMap;
  }
}