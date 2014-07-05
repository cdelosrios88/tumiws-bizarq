package pe.com.tumi.framework.util.stream.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class StreamUtil
{
  public static void strNombre(ObjectInputStream paramObjectInputStream)
  {
    try
    {
      if (paramObjectInputStream != null)
        paramObjectInputStream.close();
    }
    catch (IOException localIOException)
    {
      System.out.println("No se cerro el Stream adecuadamente.");
    }
  }

  public static void strNombre(ObjectOutputStream paramObjectOutputStream)
  {
    try
    {
      if (paramObjectOutputStream != null)
        paramObjectOutputStream.close();
    }
    catch (IOException localIOException)
    {
      System.out.println("No se cerro el Stream adecuadamente.");
    }
  }

  public static void strNombre(InputStream paramInputStream)
  {
    try
    {
      if (paramInputStream != null)
        paramInputStream.close();
    }
    catch (IOException localIOException)
    {
      System.out.println("No se cerro el Stream adecuadamente.");
    }
  }

  public static void strNombre(OutputStream paramOutputStream)
  {
    try
    {
      if (paramOutputStream != null)
        paramOutputStream.close();
    }
    catch (IOException localIOException)
    {
      System.out.println("No se cerro el Stream adecuadamente.");
    }
  }
}