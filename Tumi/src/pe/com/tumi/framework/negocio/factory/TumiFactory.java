package pe.com.tumi.framework.negocio.factory;

import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.context.WebApplicationContext;

import pe.com.tumi.framework.util.reflection.JReflection;
import pe.com.tumi.framework.util.reflection.exception.ReflectionException;

public class TumiFactory
{
  private static Map strNombre;
  private static WebApplicationContext strCodigo;

  private static void strNombre()
  {
    if (strNombre == null)
      try
      {
        strNombre = Collections.synchronizedMap(new HashMap());
      }
      catch (Exception localException)
      {
        System.out.println("TumiDaoFactory Exception");
      }
  }

  public static void asignarContexto(WebApplicationContext paramWebApplicationContext)
  {
    strCodigo = paramWebApplicationContext;
  }

  public static Object obtenerBean(String paramString)
  {
    Object localObject = null;
    if (strCodigo != null)
      localObject = strCodigo.getBean(paramString);
    return localObject;
  }

  public static Object get(Class paramClass)
  {
    Object localObject = null;
    strNombre();
    try
    {
      if (paramClass.getSimpleName().contains("Dao"))
      {
        SqlMapClientImpl localSqlMapClientImpl = (SqlMapClientImpl)obtenerBean("sqlMapClient");
        if (localSqlMapClientImpl != null)
        {
          if (strNombre.containsKey(paramClass.getName()))
          {
            localObject = strNombre.get(paramClass.getName());
          }
          else
          {
            localObject = JReflection.getInstance(paramClass);
            JReflection.asignarPropiedad(localObject, "sqlMapClientTemplate.sqlMapClient", localSqlMapClientImpl);
            JReflection.asignarPropiedad(localObject, "nameSpace", paramClass.getSimpleName());
            strNombre.put(paramClass.getName(), localObject);
          }
        }
        else
          System.out.println("no se realizo la injection del Dao");
      }
      else if (strNombre.containsKey(paramClass.getName()))
      {
        localObject = strNombre.get(paramClass.getName());
      }
      else
      {
        localObject = JReflection.getInstance(paramClass);
        strNombre.put(paramClass.getName(), localObject);
      }
    }
    catch (ReflectionException localReflectionException)
    {
      localReflectionException.printStackTrace();
    }
    return localObject;
  }
}