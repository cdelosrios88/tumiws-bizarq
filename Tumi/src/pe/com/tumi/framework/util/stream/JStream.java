package pe.com.tumi.framework.util.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import pe.com.tumi.framework.util.stream.common.StreamUtil;
import pe.com.tumi.framework.util.stream.exception.StreamException;

public class JStream
{
  public static void enviar(OutputStream paramOutputStream, Object paramObject)
    throws StreamException
  {
    ObjectOutputStream localObjectOutputStream = null;
    try
    {
      localObjectOutputStream = new ObjectOutputStream(paramOutputStream);
      localObjectOutputStream.writeObject(paramObject);
    }
    catch (IOException localIOException)
    {
      throw new StreamException("no se ha enviado la informacion al destino por " + localIOException.getMessage());
    }
    finally
    {
      StreamUtil.strNombre(localObjectOutputStream);
    }
  }

  public static Object recibir(InputStream paramInputStream)
    throws StreamException
  {
    ObjectInputStream localObjectInputStream = null;
    Object localObject1 = null;
    try
    {
      localObjectInputStream = new ObjectInputStream(paramInputStream);
      localObject1 = localObjectInputStream.readObject();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new StreamException("no se ha recibido informacion del destino por " + localClassNotFoundException.getMessage());
    }
    catch (IOException localIOException)
    {
      throw new StreamException("no se ha recibido informacion del destino " + localIOException.getMessage());
    }
    finally
    {
      StreamUtil.strNombre(localObjectInputStream);
    }
    return localObject1;
  }

  public static void escribirRecursoDePathAOutputStream(String paramString, OutputStream paramOutputStream)
    throws StreamException
  {
    Thread localThread = Thread.currentThread();
    ClassLoader localClassLoader = localThread.getContextClassLoader();
    InputStream localInputStream = localClassLoader.getResourceAsStream(paramString);
    byte[] arrayOfByte = (byte[])null;
    int i = 0;
    try
    {
      arrayOfByte = new byte[1024];
      i = localInputStream.read(arrayOfByte);
      while (i != -1)
      {
        paramOutputStream.write(arrayOfByte, 0, i);
        i = localInputStream.read(arrayOfByte);
        paramOutputStream.flush();
      }
    }
    catch (IOException localIOException)
    {
      throw new StreamException("no se ha leido adecuadamente el recurso" + localIOException.getMessage());
    }
    catch (Exception localException)
    {
      throw new StreamException(String.format("Recurso [%s] no encontrado.", new Object[] { paramString }));
    }
    finally
    {
      StreamUtil.strNombre(localInputStream);
    }
  }
}