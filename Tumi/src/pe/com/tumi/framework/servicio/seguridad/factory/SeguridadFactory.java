package pe.com.tumi.framework.servicio.seguridad.factory;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pe.com.tumi.framework.servicio.seguridad.dto.Ticket;
import pe.com.tumi.framework.servicio.seguridad.exception.SeguridadException;

public class SeguridadFactory
{
  private static Map strNombre;

  private static void strNombre()
    throws SeguridadException
  {
    if (strNombre == null)
      try
      {
        strNombre = Collections.synchronizedMap(new HashMap());
      }
      catch (Exception localException)
      {
        throw new SeguridadException("No se inicializo el Cache de Tickets adecuadamente");
      }
  }

  public static Ticket getTicket(HttpServletRequest paramHttpServletRequest, String paramString)
    throws SeguridadException
  {
    Ticket localTicket = null;
    try
    {
      if (paramHttpServletRequest == null)
        throw new SeguridadException("Request es requerido para la obtencion de Ticket");
      strNombre();
      if (strNombre.containsKey(paramString))
        localTicket = (Ticket)strNombre.get(paramString);
    }
    catch (SeguridadException localSeguridadException)
    {
      throw localSeguridadException;
    }
    return localTicket;
  }

  public static void setTicket(HttpServletRequest paramHttpServletRequest, Object paramObject)
    throws SeguridadException
  {
    Ticket localTicket = null;
    HttpSession localHttpSession = null;
    try
    {
      if (paramHttpServletRequest == null)
        throw new SeguridadException("Request es requerido para la obtencion de Ticket");
      strNombre();
      localHttpSession = paramHttpServletRequest.getSession();
      if (!strNombre.containsKey(localHttpSession.getId()))
      {
        localTicket = new Ticket();
        localTicket.setUsuario(paramObject);
        localTicket.setId(localHttpSession.getId());
        localTicket.setTsFechaInicio(new Timestamp(System.currentTimeMillis()));
        localTicket.setExpired(false);
        localHttpSession.setAttribute("ticket", localTicket);
        localHttpSession.setAttribute("usuario", paramObject);
        strNombre.put(localTicket.getId(), localTicket);
      }
    }
    catch (SeguridadException localSeguridadException)
    {
      throw localSeguridadException;
    }
  }

  public static void cancelarTicket(HttpServletRequest paramHttpServletRequest)
    throws SeguridadException
  {
    HttpSession localHttpSession = null;
    try
    {
      if (paramHttpServletRequest == null)
        throw new SeguridadException("Request es requerido para la obtencion de Ticket");
      strNombre();
      localHttpSession = paramHttpServletRequest.getSession();
      if (strNombre.containsKey(localHttpSession.getId()))
      {
        localHttpSession.removeAttribute("ticket");
        localHttpSession.removeAttribute("usuario");
        strNombre.remove(localHttpSession.getId());
      }
    }
    catch (SeguridadException localSeguridadException)
    {
      throw localSeguridadException;
    }
  }
}