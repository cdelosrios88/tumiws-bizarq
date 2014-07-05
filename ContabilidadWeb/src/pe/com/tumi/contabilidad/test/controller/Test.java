package pe.com.tumi.contabilidad.test.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;

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
			
			PlanCuentaFacadeLocal facade = (PlanCuentaFacadeLocal)EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
			
			PlanCuenta o = new PlanCuenta();
			o.setId(new PlanCuentaId());
			o.getId().setIntEmpresaCuentaPk(1);
			o.getId().setIntPeriodoCuenta(1);
			o.getId().setStrNumeroCuenta("1");
			o.setStrDescripcion("descricpcion de la Cuenta");
			o.setIntMovimiento(1);
			o.setIntIdentificadorExtranjero(1);
			o.setIntEmpresaUsuarioPk(1);
			o.setIntPersonaUsuarioPk(1);
			o.setIntEstadoCod(1);
			o = facade.grabarPlanCuenta(o);
			out.println(o.getId().getIntEmpresaCuentaPk()+"<BR/>");
			out.println(o.getId().getIntPeriodoCuenta()+"<BR/>");
			out.println(o.getId().getStrNumeroCuenta()+"<BR/>");
			out.println(o.getStrDescripcion()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	public static void getPorPK(PrintWriter out){
		try {			
			PlanCuentaFacadeLocal facade = (PlanCuentaFacadeLocal)EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
			
			PlanCuentaId oId = new PlanCuentaId();
			oId.setIntEmpresaCuentaPk(1);
			oId.setIntPeriodoCuenta(1);
			oId.setStrNumeroCuenta("1");
			PlanCuenta o = facade.getPlanCuentaPorPk(oId);
			out.println(o.getId().getIntEmpresaCuentaPk()+"<BR/>");
			out.println(o.getId().getIntPeriodoCuenta()+"<BR/>");
			out.println(o.getId().getStrNumeroCuenta()+"<BR/>");
			out.println(o.getStrDescripcion()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}	

}
