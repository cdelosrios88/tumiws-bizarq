package pe.com.tumi.framework.vista.recurso.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pe.com.tumi.framework.application.Cliente;
import pe.com.tumi.framework.util.stream.JStream;
import pe.com.tumi.framework.util.stream.common.StreamUtil;
import pe.com.tumi.framework.util.stream.exception.StreamException;
import pe.com.tumi.framework.vista.recurso.Recursoable;

public class RecursoImpl
  implements Recursoable
{
  public void getRecurso(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    String str1 = null;
    String str2 = null;
    ServletOutputStream localServletOutputStream = null;
    try
    {
      str1 = paramHttpServletRequest.getParameter("parametro");
      if (str1.endsWith(".js"))
        str2 = String.valueOf(Cliente.getPoperties().get("recurso.path.script")) + str1;
      else if (str1.endsWith(".css"))
        str2 = String.valueOf(Cliente.getPoperties().get("recurso.path.style")) + str1;
      else if ((str1.endsWith(".gif")) || (str1.endsWith(".jpg")) || (str1.endsWith(".png")))
        str2 = String.valueOf(Cliente.getPoperties().get("recurso.path.image")) + str1;
      if (str2 != null)
      {
        localServletOutputStream = paramHttpServletResponse.getOutputStream();
        JStream.escribirRecursoDePathAOutputStream(str2, localServletOutputStream);
      }
      else
      {
        System.out.println(String.format("Recurso [%s] no ha sido ingresado adecuadamente.", new Object[] { str2 }));
      }
    }
    catch (StreamException localStreamException)
    {
      localStreamException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    finally
    {
      StreamUtil.strNombre(localServletOutputStream);
    }
  }

  public static void main(String[] paramArrayOfString)
  {
    FileOutputStream localFileOutputStream = null;
    try
    {
      localFileOutputStream = new FileOutputStream("C:\\menuColapsar.gif");
      JStream.escribirRecursoDePathAOutputStream("pe/jorata/vista/recurso/img/menuColapsar.gif", localFileOutputStream);
      StreamUtil.strNombre(localFileOutputStream);
      localFileOutputStream = new FileOutputStream("C:\\menuItem.gif");
      JStream.escribirRecursoDePathAOutputStream("pe/jorata/vista/recurso/img/menuItem.gif", localFileOutputStream);
    }
    catch (StreamException localStreamException)
    {
      localStreamException.printStackTrace();
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
    }
    finally
    {
      StreamUtil.strNombre(localFileOutputStream);
    }
  }
}