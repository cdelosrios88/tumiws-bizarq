package pe.com.tumi.framework.vista.recurso.servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.framework.vista.recurso.JRecurso;
import pe.com.tumi.framework.vista.recurso.Recursoable;

public class Remoting extends HttpServlet
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
    String str = paramHttpServletRequest.getParameter("parametro");
    if ((str != null) && (str.equals("modificarMenu")))
      modificarMenu(paramHttpServletRequest, paramHttpServletResponse);
    else
      getRecurso(paramHttpServletRequest, paramHttpServletResponse);
  }

  protected void getRecurso(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    try
    {
      Recursoable localRecursoable = (Recursoable)TumiFactory.get(JRecurso.class);
      localRecursoable.getRecurso(paramHttpServletRequest, paramHttpServletResponse);
    }
    catch (Exception localException)
    {
      System.out.println("Error RemotingServlet" + localException.getMessage());
    }
  }

  protected void modificarMenu(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws ServletException, IOException
  {
    String str1 = null;
    String str2 = (String)paramHttpServletRequest.getSession().getAttribute("estadoMenu");
    if ((str2 != null) && (str2.equals("estadoVisible")))
      str1 = "estadoOculto";
    else if ((str2 != null) && (str2.equals("estadoOculto")))
      str1 = "estadoVisible";
    else if ((str2 == null) || (str2.equals("")))
      str1 = "estadoOculto";
    paramHttpServletRequest.getSession().setAttribute("estadoMenu", str1);
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.write(str1);
  }
}