package pe.com.tumi.framework.util.file.common;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class FileUtil
{
  public static void strNombre(FileReader paramFileReader)
  {
    try
    {
      if (paramFileReader != null)
        paramFileReader.close();
    }
    catch (IOException localIOException)
    {
      System.out.println("No se cerro el FileReader adecuadamente.");
    }
  }
}