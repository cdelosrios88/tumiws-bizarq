package pe.com.tumi.cobranza.test.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;

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
			
			PlanillaFacadeLocal facade = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			
			Envioconcepto o = new Envioconcepto();
			o.setId(new EnvioconceptoId());
			o.getId().setIntEmpresacuentaPk(1);
			o.getId().setIntItemenvioconcepto(1);
			o.setIntCuentaPk(1);
			o.setIntIndidestacado(1);
			
			o = facade.grabarEnvioconcepto(o);
			out.println(o.getId().getIntEmpresacuentaPk()+"<BR/>");
			out.println(o.getId().getIntItemenvioconcepto()+"<BR/>");
			out.println(o.getIntCuentaPk()+"<BR/>");
			out.println(o.getIntIndidestacado()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");
		/*} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();*/
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	public static void getPorPK(PrintWriter out){
		try {			
			PlanillaFacadeLocal facade = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			
			EnvioconceptoId oId = new EnvioconceptoId();
			oId.setIntEmpresacuentaPk(1);
			oId.setIntItemenvioconcepto(1);
			Envioconcepto o = facade.getEnvioconceptoPorPk(oId);
			out.println(o.getId().getIntEmpresacuentaPk()+"<BR/>");
			out.println(o.getId().getIntItemenvioconcepto()+"<BR/>");
			out.println(o.getIntCuentaPk()+"<BR/>");
			out.println(o.getIntIndidestacado()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}	

}
