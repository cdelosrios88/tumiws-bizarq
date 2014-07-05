package pe.com.tumi.framework.servicio.seguridad.controller.cliente;

import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pe.com.tumi.framework.servicio.seguridad.common.SeguridadUtil;
import pe.com.tumi.framework.servicio.seguridad.dto.Ticket;
import pe.com.tumi.framework.servicio.seguridad.service.SeguridadService;

public class CheckClient
  implements Filter
{
  public void init(FilterConfig paramFilterConfig)
    throws ServletException
  {
  }

  public void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain)
    throws IOException, ServletException
  {
    String str1 = null;
    Ticket localTicket = null;
    String str2 = null;
    int i = 0;
    localTicket = (Ticket)((HttpServletRequest)paramServletRequest).getSession().getAttribute("ticket");
    if (localTicket == null)
    {
      if (!SeguridadService.strNombre((HttpServletRequest)paramServletRequest))
      {
        str1 = SeguridadUtil.strCodigo((HttpServletRequest)paramServletRequest);
        if (str1 != null)
        {
          str1 = str1 + "/DispatchServer";
          ((HttpServletResponse)paramServletResponse).sendRedirect(str1);
        }
        i = 1;
      }
      else
      {
        str2 = paramServletRequest.getParameter("pagina");
        str1 = SeguridadUtil.strEnlace((HttpServletRequest)paramServletRequest);
        str1 = str1 + "/inicio.jsp";
        ((HttpServletRequest)paramServletRequest).getSession().setAttribute("pagina", str2);
        ((HttpServletResponse)paramServletResponse).sendRedirect(str1);
        i = 1;
      }
    }
    else if (SeguridadService.strCodigo((HttpServletRequest)paramServletRequest))
    {
      str2 = paramServletRequest.getParameter("pagina");
      if (str2 != null)
      {
        if (!SeguridadService.strNombre((HttpServletRequest)paramServletRequest))
        {
          str1 = SeguridadUtil.strCodigo((HttpServletRequest)paramServletRequest);
          if (str1 != null)
          {
            str1 = str1 + "/DispatchServer";
            ((HttpServletResponse)paramServletResponse).sendRedirect(str1);
          }
          i = 1;
        }
        else
        {
          str2 = paramServletRequest.getParameter("pagina");
          str1 = SeguridadUtil.strEnlace((HttpServletRequest)paramServletRequest);
          str1 = str1 + "/inicio.jsp";
          ((HttpServletRequest)paramServletRequest).getSession().setAttribute("pagina", str2);
          ((HttpServletResponse)paramServletResponse).sendRedirect(str1);
          i = 1;
        }
      }
      else
        paramFilterChain.doFilter(paramServletRequest, paramServletResponse);
    }
    else
    {
      str1 = SeguridadUtil.strEnlace((HttpServletRequest)paramServletRequest);
      if (str1 != null)
      {
        str1 = str1 + "/DispatchServer";
        ((HttpServletResponse)paramServletResponse).sendRedirect(str1);
      }
      i = 1;
    }
    if (i != 0);
  }

  protected String getURI(ServletRequest paramServletRequest)
  {
    if ((paramServletRequest instanceof HttpServletRequest))
      return ((HttpServletRequest)paramServletRequest).getRequestURI();
    return "Not an HttpServletRequest";
  }

  public void destroy()
  {
    System.out.println("SecurityCheckClient method : destroy");
  }
}