package pe.com.tumi.tesoreria.test.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.BancofondoId;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;

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
		try {
			
			BancoFacadeLocal facade = (BancoFacadeLocal)EJBFactory.getLocal(BancoFacadeLocal.class);
			/*
			Bancofondo o = new Bancofondo();
			o.setId(new BancofondoId());
			o.getId().setIntEmpresaPk(1);
			o.getId().setIntItembancofondo(1);
			o.setStrNombrefondo("Fondo de Prueba");
			o.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			o = facade.grabarBancofondo(o);
			out.println(o.getId().getIntEmpresaPk()+"<BR/>");
			out.println(o.getId().getIntItembancofondo()+"<BR/>");
			out.println(o.getStrNombrefondo()+"<BR/>");
			out.println(o.getIntEstadoCod()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");*/
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	public static void getPorPK(PrintWriter out){
		try {			
			BancoFacadeLocal facade = (BancoFacadeLocal)EJBFactory.getLocal(BancoFacadeLocal.class);
			/*
			BancofondoId oId = new BancofondoId();
			oId.setIntEmpresaPk(1);
			oId.setIntItembancofondo(1);
			Bancofondo o = facade.getBancofondoPorPk(oId);
			out.println(o.getId().getIntEmpresaPk()+"<BR/>");
			out.println(o.getId().getIntItembancofondo()+"<BR/>");
			out.println(o.getStrNombrefondo()+"<BR/>");
			out.println(o.getIntEstadoCod()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");*/
		/*} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
	*/	} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}	

}
