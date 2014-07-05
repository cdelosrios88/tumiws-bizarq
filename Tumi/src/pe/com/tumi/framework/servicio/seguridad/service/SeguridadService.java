package pe.com.tumi.framework.servicio.seguridad.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import pe.com.tumi.framework.servicio.seguridad.dto.Ticket;
import pe.com.tumi.framework.servicio.seguridad.exception.SeguridadException;

public class SeguridadService
{
  public static boolean strNombre(HttpServletRequest paramHttpServletRequest)
  {
    Ticket localTicket = null;
    Boolean localBoolean = Boolean.valueOf(false);
    try
    {
      localTicket = TicketService.strNombre(paramHttpServletRequest);
      if ((localTicket != null) && (!localTicket.isExpired()) && (localTicket.getUsuario() != null))
      {
        paramHttpServletRequest.getSession().setAttribute("ticket", localTicket);
        paramHttpServletRequest.getSession().setAttribute("usuario", localTicket.getUsuario());
        localBoolean = Boolean.valueOf(true);
      }
    }
    catch (SeguridadException localSeguridadException)
    {
      localSeguridadException.printStackTrace();
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localBoolean.booleanValue();
  }

  public static boolean strCodigo(HttpServletRequest paramHttpServletRequest)
  {
    Boolean localBoolean = Boolean.valueOf(false);
    Ticket localTicket = null;
    Object localObject = null;
    try
    {
      localTicket = (Ticket)paramHttpServletRequest.getSession().getAttribute("ticket");
      if (localTicket.isExpired())
        paramHttpServletRequest.getSession().invalidate();
      else
        localBoolean = Boolean.valueOf(true);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return localBoolean.booleanValue();
  }
}