package pe.com.tumi.framework.servicio.seguridad.service;

import java.io.IOException;
import java.net.URLConnection;
import javax.servlet.http.HttpServletRequest;

import pe.com.tumi.framework.servicio.seguridad.common.SeguridadUtil;
import pe.com.tumi.framework.servicio.seguridad.dto.Ticket;
import pe.com.tumi.framework.servicio.seguridad.exception.SeguridadException;
import pe.com.tumi.framework.util.stream.JStream;
import pe.com.tumi.framework.util.stream.exception.StreamException;
import pe.com.tumi.framework.util.url.JUrl;

public class TicketService
{
  public static Ticket strNombre(HttpServletRequest paramHttpServletRequest)
    throws SeguridadException
  {
    Ticket localTicket = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    try
    {
      str1 = paramHttpServletRequest.getParameter("ticket");
      str2 = SeguridadUtil.strNombre(paramHttpServletRequest);
      if (str2 != null)
      {
        str3 = str2 + "/CheckServer";
        URLConnection localURLConnection = JUrl.getConnection(str3);
        localURLConnection.setDoOutput(true);
        localURLConnection.setRequestProperty("Connection", "close");
        localURLConnection.setRequestProperty("CONTENT_TYPE", "application/octet-stream");
        JStream.enviar(localURLConnection.getOutputStream(), str1);
        localTicket = (Ticket)JStream.recibir(localURLConnection.getInputStream());
      }
    }
    catch (StreamException localStreamException)
    {
      throw new SeguridadException(localStreamException);
    }
    catch (IOException localIOException)
    {
      throw new SeguridadException(localIOException);
    }
    return localTicket;
  }
}