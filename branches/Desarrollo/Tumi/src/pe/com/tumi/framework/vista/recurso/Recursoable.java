package pe.com.tumi.framework.vista.recurso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface Recursoable
{
  public static final String RESOURCE_PATH = "resource";

  public abstract void getRecurso(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse);
}