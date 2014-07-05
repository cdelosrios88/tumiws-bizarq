package pe.com.tumi.servicio.test.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.util.fecha.JFecha;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitudId;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;

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
			
			ConfSolicitudFacadeLocal facade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			
			ConfServSolicitud o = new ConfServSolicitud();
			o.setId(new ConfServSolicitudId());
			o.getId().setIntPersEmpresaPk(1);
			o.getId().setIntItemSolicitud(1);
			o.setIntParaTipoRequertoAutorizaCod(1);
			o.setIntParaTipoOperacionCod(1);
			o.setIntParaSubtipoOperacionCod(1);
			o.setDtDesde(new Date());
			o.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			o.setTsFechaRegistro(JFecha.obtenerTimestampDeFechayHoraActual());
			o.setIntPersPersonaUsuarioPk(1);
			
			o = facade.grabarConfiguracion(o);
			out.println(o.getId().getIntPersEmpresaPk()+"<BR/>");
			out.println(o.getId().getIntItemSolicitud()+"<BR/>");
			out.println(o.getIntParaEstadoCod()+"<BR/>");
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	public static void getPorPK(PrintWriter out){
		try {			
			ConfSolicitudFacadeLocal facade = (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			
			ConfServSolicitudId oId = new ConfServSolicitudId();
			oId.setIntPersEmpresaPk(1);
			oId.setIntItemSolicitud(1);
			ConfServSolicitud o = facade.getConfiguracionPorPk(oId);
			if(o != null){
				out.println(o.getId().getIntPersEmpresaPk()+"<BR/>");
				out.println(o.getId().getIntItemSolicitud()+"<BR/>");
				out.println(o.getIntParaEstadoCod()+"<BR/>");
				out.println("---------------------------------------" + "<br/>");
			}
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}	

}
