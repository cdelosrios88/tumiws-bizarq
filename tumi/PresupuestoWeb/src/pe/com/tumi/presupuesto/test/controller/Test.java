package pe.com.tumi.presupuesto.test.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println("Inserta <BR/>");
		inserta(out);
		out.println("Get Por PK <BR/>");
		getPorPK(out);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	public static void inserta(PrintWriter out){
	/*	try {
			
			ArchivoRiesgoFacadeLocal facade = (ArchivoRiesgoFacadeLocal)EJBFactory.getLocal(ArchivoRiesgoFacadeLocal.class);
			
			Configuracion o = new Configuracion();
			o.setId(new ConfiguracionId());
			o.getId().setIntPersEmpresaPk(1);
			o.getId().setIntItemConfiguracion(1);
			o.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			//o = facade.grabarConfiguracion(o);
			out.println(o.getId().getIntPersEmpresaPk()+"<BR/>");
			out.println(o.getId().getIntItemConfiguracion()+"<BR/>");
			out.println(o.getIntParaEstadoCod()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");
		//} catch (BusinessException e) {
		//	System.out.println("no se grabo correctamente");
		//	e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}*/
	}
	
	public static void getPorPK(PrintWriter out){
		/*try {			
			ArchivoRiesgoFacadeLocal facade = (ArchivoRiesgoFacadeLocal)EJBFactory.getLocal(ArchivoRiesgoFacadeLocal.class);
			
			ConfiguracionId oId = new ConfiguracionId();
			oId.setIntPersEmpresaPk(1);
			oId.setIntItemConfiguracion(1);
			Configuracion o = facade.getConfiguracionPorPk(oId);
			out.println(o.getId().getIntPersEmpresaPk()+"<BR/>");
			out.println(o.getId().getIntItemConfiguracion()+"<BR/>");
			out.println(o.getIntParaEstadoCod()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}*/
	}	

}
