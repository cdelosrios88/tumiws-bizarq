package pe.com.tumi.test.controller;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuarioId;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursalId;
import pe.com.tumi.seguridad.login.facade.LoginFacadeLocal;

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
		out.println("  Esto es una pagina de prueba");
		test(out);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public static void test(PrintWriter out){
		UsuarioSucursal lUsuarioSucursal = null;
		UsuarioSucursalId lUsuarioSucursalId =null;
		try {
			LoginFacadeLocal local = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
			EmpresaUsuarioId id = new EmpresaUsuarioId();
			id.setIntPersEmpresaPk(new Integer(163));
			id.setIntPersPersonaPk(new Integer(179));
			List<UsuarioSucursal> lista = local.getListaUsuarioSucursalPorPkEmpresaUsuario(id);
			for(int i=0;i<lista.size();i++){
				lUsuarioSucursal = lista.get(i);
				lUsuarioSucursalId = lUsuarioSucursal.getId();
				out.println("idEmpresa:"+lUsuarioSucursalId.getIntPersEmpresaPk() +" idPersona:"+ lUsuarioSucursalId.getIntPersPersonaPk() + " idSucursal: "+lUsuarioSucursalId.getIntIdSucursal() + " fecha:" +lUsuarioSucursalId.getDtFechaRegistro());
				lUsuarioSucursal = local.getUsuarioSucursalPorPk(lUsuarioSucursalId);
				if(lUsuarioSucursal!=null){
					out.println("obtuvo la sucursal por la pk");
					lUsuarioSucursal.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					local.modificarUsuarioSucursal(lUsuarioSucursal);
				}else{
					out.println("no hay resultado por la pk");
				}
			}
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
