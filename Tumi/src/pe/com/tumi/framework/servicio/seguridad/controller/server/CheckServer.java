package pe.com.tumi.framework.servicio.seguridad.controller.server;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.framework.servicio.seguridad.dto.Ticket;
import pe.com.tumi.framework.servicio.seguridad.exception.SeguridadException;
import pe.com.tumi.framework.servicio.seguridad.factory.SeguridadFactory;
import pe.com.tumi.framework.util.stream.JStream;
import pe.com.tumi.framework.util.stream.exception.StreamException;

public class CheckServer extends HttpServlet
  implements Servlet
{
  protected void doGet(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    doPost(paramHttpServletRequest, paramHttpServletResponse);
  }

  protected void doPost(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    String str1 = "application/x-java-serialized-object";
    paramHttpServletResponse.setContentType(str1);
    Ticket localTicket = null;
    String str2 = null;
    try
    {
      str2 = (String)JStream.recibir(paramHttpServletRequest.getInputStream());
    }
    catch (StreamException localStreamException1)
    {
      str2 = null;
    }
    try
    {
      if (str2 != null)
        localTicket = SeguridadFactory.getTicket(paramHttpServletRequest, str2);
      if ((str2 == null) || (localTicket == null))
      {
        localTicket = new Ticket();
        localTicket.setExpired(true);
        localTicket.setId(str2);
      }
      JStream.enviar(paramHttpServletResponse.getOutputStream(), localTicket);
    }
    catch (SeguridadException localSeguridadException)
    {
      localSeguridadException.printStackTrace();
    }
    catch (StreamException localStreamException2)
    {
      localStreamException2.printStackTrace();
    }
  }
}