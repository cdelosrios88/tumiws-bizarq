package pe.com.tumi.framework.vista.recurso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.framework.vista.recurso.impl.RecursoImpl;

public class JRecurso
  implements Recursoable
{
  private Recursoable strNombre = null;

  private void strNombre()
  {
    if (this.strNombre == null)
      this.strNombre = new RecursoImpl();
  }

  public void getRecurso(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
  {
    try
    {
      strNombre();
      this.strNombre.getRecurso(paramHttpServletRequest, paramHttpServletResponse);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}