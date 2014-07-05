package pe.com.tumi.framework.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pe.com.tumi.framework.util.file.common.FileUtil;
import pe.com.tumi.framework.util.file.exception.FileException;

public class JFile
{
  public static void main(String[] paramArrayOfString)
  {
    obtenerArchivo("C:\\", "holitas.txt");
  }

  public static File obtenerArchivo(String paramString1, String paramString2)
  {
    File localFile = null;
    try
    {
      localFile = new File(paramString1, paramString2);
      if (!localFile.exists())
        localFile.createNewFile();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localFile;
  }

  public static FileWriter obtenerArchivoDeEscritura(String paramString1, String paramString2)
  {
    FileWriter localFileWriter = null;
    File localFile = null;
    try
    {
      localFile = obtenerArchivo(paramString1, paramString2);
      localFileWriter = new FileWriter(localFile);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return localFileWriter;
  }

  public static List obtenerListaStringDeLineaPorFile(File paramFile)
  {
    ArrayList localArrayList = new ArrayList();
    FileReader localFileReader = null;
    BufferedReader localBufferedReader = null;
    try
    {
      localFileReader = new FileReader(paramFile);
      localBufferedReader = new BufferedReader(localFileReader);
      Object localObject1 = null;
      while ((localObject1 = localBufferedReader.readLine()) != null)
        localArrayList.add(localObject1);
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    finally
    {
      FileUtil.strNombre(localFileReader);
    }
    return (List)localArrayList;
  }

  public static InputStream obtieneInputStreamDeArchivoPorPath(String paramString)
  {
    InputStream localInputStream = null;
    Thread localThread = Thread.currentThread();
    ClassLoader localClassLoader = localThread.getContextClassLoader();
    localInputStream = localClassLoader.getResourceAsStream(paramString);
    return localInputStream;
  }

  public static void convertirInputStreamAOutputStream(InputStream paramInputStream, OutputStream paramOutputStream)
  {
    byte[] arrayOfByte = new byte[1024];
    int i = 0;
    try
    {
      while ((i = paramInputStream.read(arrayOfByte)) >= 0)
        paramOutputStream.write(arrayOfByte, 0, i);
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static List obtenerListaFileDePathYFiltro(String paramString, FileFilter paramFileFilter)
  {
    ArrayList localArrayList = null;
    List localList = null;
    File localFile1 = new File(paramString);
    File[] arrayOfFile = localFile1.listFiles(paramFileFilter);
    File localFile2 = null;
    if (arrayOfFile.length > 0)
    {
      localArrayList = new ArrayList();
      for (int i = 0; i < arrayOfFile.length; i++)
      {
        localFile2 = arrayOfFile[i];
        if (localFile2.isDirectory())
        {
          localList = obtenerListaFileDePathYFiltro(localFile2.getPath(), paramFileFilter);
          if (localList == null)
            continue;
          localArrayList.addAll(localList);
        }
        else
        {
          localArrayList.add(localFile2);
        }
      }
    }
    return localArrayList;
  }

  public static String getPathAbsolutoDePathRelativo(String paramString)
  {
    String str = null;
    URL localURL = null;
    try
    {
      localURL = new File(paramString).toURI().toURL();
      if (localURL != null)
        str = localURL.getPath().substring(1);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }

  public static boolean mkdir(String paramString1, String paramString2)
    throws FileException
  {
    String str1 = null;
    boolean bool = false;
    String str2 = null;
    try
    {
      str2 = System.getProperty("os.name");
      if (str2.equals("OS/400"))
      {
        if (!paramString1.endsWith("/"))
          str1 = paramString1 + "/" + paramString2;
        else
          str1 = paramString1 + paramString2;
      }
      else if (!paramString1.endsWith("\\"))
        str1 = paramString1 + "\\" + paramString2;
      else
        str1 = paramString1 + paramString2;
      System.out.println("filePath : " + str1);
      File localFile = new File(str1);
      if (!localFile.exists())
      {
        bool = localFile.mkdir();
        if (str2.equals("OS/400"))
          Runtime.getRuntime().exec(new String[] { "chmod", "755", str1 });
      }
      else
      {
        System.out.println("filePath : " + str1 + " la carpeta ya existia");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new FileException();
    }
    return bool;
  }
}