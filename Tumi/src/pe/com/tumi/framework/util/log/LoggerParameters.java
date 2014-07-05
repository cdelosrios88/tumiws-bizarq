package pe.com.tumi.framework.util.log;

import java.io.PrintStream;
import java.net.URL;
import org.apache.log4j.PropertyConfigurator;

public class LoggerParameters
{
  public static void getConfigureLog4j(String paramString)
  {
    try
    {
      Thread localThread = Thread.currentThread();
      ClassLoader localClassLoader = localThread.getContextClassLoader();
      URL localURL = localClassLoader.getResource(paramString);
      PropertyConfigurator.configure(localURL);
      System.out.println("Logs fue Configurado Correctamente");
    }
    catch (Exception localException)
    {
      System.err.println("Logs no fue Inicializado Correctamente");
    }
  }

  public static void getConfigureJCL(String paramString)
  {
    try
    {
      Thread localThread = Thread.currentThread();
      ClassLoader localClassLoader = localThread.getContextClassLoader();
      localClassLoader.getResource(paramString);
      System.out.println("Logs fue Inicializado Correctamente");
    }
    catch (Exception localException)
    {
      System.err.println("Logs no fue Inicializado Correctamente");
    }
  }
}