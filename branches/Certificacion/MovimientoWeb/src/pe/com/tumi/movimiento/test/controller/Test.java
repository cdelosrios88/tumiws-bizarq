package pe.com.tumi.movimiento.test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeLocal;

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
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	public static void inserta(PrintWriter out){
		try {
			Integer intIdPersona = null;
			ConceptoFacadeLocal facade = (ConceptoFacadeLocal)EJBFactory.getLocal(ConceptoFacadeLocal.class);
			
			CuentaConcepto o = new CuentaConcepto();
			o.setId(new CuentaConceptoId());
			o.getId().setIntPersEmpresaPk(1);
			o.getId().setIntCuentaPk(1);
			o.getId().setIntItemCuentaConcepto(1);
			o.setBdSaldo(new BigDecimal(1));
			
			o = facade.grabarCuentaConcepto(o, intIdPersona);
			out.println(o.getId().getIntPersEmpresaPk()+"<BR/>");
			out.println(o.getId().getIntCuentaPk()+"<BR/>");
			out.println(o.getId().getIntItemCuentaConcepto()+"<BR/>");
			out.println(o.getBdSaldo()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");
		/*} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();*/
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}

}
