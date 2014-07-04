package pe.com.tumi.framework.util.reflection;

import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.PropertyUtils;

import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.framework.util.reflection.exception.ReflectionException;

public class JReflection
{
  public static Object getProperty(Object paramObject, String paramString)
  {
    Object localObject = null;
    try
    {
      localObject = PropertyUtils.getProperty(paramObject, paramString);
      if (localObject == null)
        localObject = "";
    }
    catch (Exception localException)
    {
      System.out.println("Exception : " + localException.getMessage());
    }
    return localObject;
  }

  public static Object getInstance(Class paramClass)
  {
    Object localObject = null;
    try
    {
      localObject = paramClass.newInstance();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
    }
    return localObject;
  }

  public static Object getInstance(String paramString)
  {
    Object localObject = null;
    try
    {
      Thread localThread = Thread.currentThread();
      ClassLoader localClassLoader = localThread.getContextClassLoader();
      Class localClass = localClassLoader.loadClass(paramString);
      localObject = localClass.newInstance();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return localObject;
  }

  public static Object getInstance(Class paramClass, String paramString)
  {
    Object localObject = null;
    try
    {
      Class[] arrayOfClass = { String.class };
      Object[] arrayOfObject = { new String(paramString) };
      Constructor localConstructor = paramClass.getConstructor(arrayOfClass);
      localObject = localConstructor.newInstance(arrayOfObject);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (InstantiationException localInstantiationException)
    {
      localInstantiationException.printStackTrace();
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return localObject;
  }

  public static Object getInstance(String paramString1, String paramString2)
  {
    Object localObject = null;
    try
    {
      Thread localThread = Thread.currentThread();
      ClassLoader localClassLoader = localThread.getContextClassLoader();
      Class localClass = localClassLoader.loadClass(paramString1);
      localObject = getInstance(localClass, paramString2);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return localObject;
  }

  public static Class getClass(String paramString)
  {
    Class localClass = null;
    try
    {
      Thread localThread = Thread.currentThread();
      ClassLoader localClassLoader = localThread.getContextClassLoader();
      localClass = localClassLoader.loadClass(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
    }
    return localClass;
  }

  public static Class getPropertyType(Object paramObject, String paramString)
  {
    Class localClass = null;
    try
    {
      localClass = PropertyUtils.getPropertyType(paramObject, paramString);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localClass;
  }

  public static void asignarPropiedad(Object paramObject1, String paramString, Object paramObject2)
    throws ReflectionException
  {
    Class localClass = null;
    try
    {
      if (paramObject2 != null)
      {
        localClass = getPropertyType(paramObject1, paramString);
        if (localClass != null)
        {
          Object localObject;
          if (localClass.getName().equals("java.lang.Long"))
          {
            localObject = new Long(paramObject2.toString());
            PropertyUtils.setProperty(paramObject1, paramString, localObject);
          }
          else if (localClass.getName().equals("java.lang.Float"))
          {
            localObject = new Float(paramObject2.toString());
            PropertyUtils.setProperty(paramObject1, paramString, localObject);
          }
          else if (localClass.getName().equals("java.util.Date"))
          {
            if (paramObject2.getClass().getName().equals("java.lang.String"))
            {
              if (!paramObject2.toString().trim().equals(""))
              {
                localObject = JFecha.convertirStringAUtilDate(paramObject2.toString().trim());
                PropertyUtils.setProperty(paramObject1, paramString, localObject);
              }
            }
            else
            {
              localObject = JFecha.formatearFechaHoraMeridiano(paramObject2);
              Date localDate = JFecha.convertirStringAUtilDate(localObject.toString());
              PropertyUtils.setProperty(paramObject1, paramString, localDate);
            }
          }
          else if (localClass.getName().equals("java.math.BigDecimal"))
          {
            localObject = new BigDecimal(paramObject2.toString());
            PropertyUtils.setProperty(paramObject1, paramString, localObject);
          }
          else if (localClass.getName().equals("java.lang.String"))
          {
            PropertyUtils.setProperty(paramObject1, paramString, paramObject2.toString());
          }
          else
          {
            PropertyUtils.setProperty(paramObject1, paramString, paramObject2);
          }
        }
        else
        {
          throw new ReflectionException("No se puede asignar valor para el atributo : " + paramString);
        }
      }
    }
    catch (ReflectionException localReflectionException)
    {
      throw localReflectionException;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new ReflectionException(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new ReflectionException(localInvocationTargetException);
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      throw new ReflectionException(localNoSuchMethodException);
    }
  }

  public static void asignarPropiedadDePadre(Object paramObject1, String paramString, Object paramObject2)
    throws ReflectionException
  {
    try
    {
      Field localField = paramObject1.getClass().getSuperclass().getDeclaredField(paramString);
      localField.setAccessible(true);
      localField.set(paramObject1, paramObject2);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new ReflectionException(localIllegalArgumentException);
    }
    catch (SecurityException localSecurityException)
    {
      throw new ReflectionException(localSecurityException);
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      throw new ReflectionException(localNoSuchFieldException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new ReflectionException(localIllegalAccessException);
    }
  }

  public static Map getMapaConstante(Class paramClass)
    throws ReflectionException
  {
    HashMap localHashMap = new HashMap();
    Field[] arrayOfField = paramClass.getFields();
    for (int i = 0; i < arrayOfField.length; i++)
    {
      Field localField = arrayOfField[i];
      if ((!Modifier.isStatic(localField.getModifiers())) || (!Modifier.isFinal(localField.getModifiers())))
        continue;
      String str = localField.getName();
      try
      {
        localHashMap.put(str, localField.get(null));
      }
      catch (Exception localException)
      {
        throw new ReflectionException("La obtencion de constantes ha fallado!");
      }
    }
    return localHashMap;
  }
}