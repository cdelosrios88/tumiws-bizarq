package pe.com.tumi.framework.servicio.seguridad.controller.server;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispatchServer extends HttpServlet
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
    RequestDispatcher localRequestDispatcher = null;
    String str = "portal/error/principal.jsp";
    localRequestDispatcher = paramHttpServletRequest.getRequestDispatcher(str);
    localRequestDispatcher.forward(paramHttpServletRequest, paramHttpServletResponse);
  }
}