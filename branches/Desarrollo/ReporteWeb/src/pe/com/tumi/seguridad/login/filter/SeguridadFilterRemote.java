/************************************************************************
/* Nombre de componente: SeguridadFilterRemote 
 * Descripción: Componente que actua como filtro a todos los request
 * del aplicativo - Modulo ReporteWeb.
 * Cod. Req.: REQ14-003   
 * Autor : Bizarq Technologies 
 * Versión : v1.0 - Creacion de componente 
 * Fecha creación : 11/08/2014 
/* ********************************************************************* */
package pe.com.tumi.seguridad.login.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.login.domain.Session;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.facade.LoginFacadeLocal;
import pe.com.tumi.seguridad.login.facade.LoginFacadeRemote;

/**
 * Servlet Filter implementation class SeguridadFilter
 * Objetivo: Verificar la actividad de la sesion
 * Autor: Bizarq
**/
public class SeguridadFilterRemote implements Filter {
	
	private static String[] URL_REDIRECT = new String[] {"/portal/autentica/login.jsf"};
	private static final String USUARIO_LOGIN = "usuarioLogueado";
	private static final String OBJETO_SESSION = "objSession";
	private static final String STR_CONTEXT_SEGURIDAD = "/SeguridadWeb";

    public SeguridadFilterRemote() {
    }
    public void destroy() {
	}
    
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//Obtiene request, response
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		//Obtiene sesiòn actual
		HttpSession session = ((HttpServletRequest) request).getSession();
		
		/*
		 * 1- Buscar que el usuario autenticado tenga la sesion activa
		 * 	1.1- Si la sesion esta activa en la BD 
		 * 		-> chain.doFilter(request, response);
		 * 	1.2- Si la sesion no esta activa en la BD 
		 * 		-> Eliminar el objeto sesion 
		 * 		-> httpResponse.sendRedirect(httpRequest.getContextPath() + URL_REDIRECT[0]);
		 */		
		
		//Procede a realizar validaciones
		if(session != null){	
			Usuario objUsuario = (Usuario)session.getAttribute("usuario");
			Session objSession = (Session)session.getAttribute("objSession");
			if(objSession == null){
				objSession = objUsuario.getObjSession();
			}
			objUsuario.getObjSession().setTsFechaActividad(new Timestamp(new Date().getTime()));
			session.setAttribute("usuario", objUsuario);
			Session objSessionDB = null;
			if(objSession != null){
				LoginFacadeRemote loginFacade;
				try {
					//Instanciar el loginFacade para consulta a base de datos
					loginFacade = (LoginFacadeRemote) EJBFactory.getRemote(LoginFacadeRemote.class);
					objSessionDB = loginFacade.getSessionPorPk(objSession.getId().getIntSessionPk());
				} catch (EJBFactoryException e) {
					e.printStackTrace();
				} catch (BusinessException e) {
					e.printStackTrace();
				}	
				if(objSessionDB != null && objSessionDB.getIntIdEstado()!= null &&
							objSessionDB.getIntIdEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO)){
						session.removeAttribute(USUARIO_LOGIN);
						session.removeAttribute(OBJETO_SESSION);
						session.invalidate();
						PrintWriter out = response.getWriter();
						out.println("<script>console.log('entro');top.frames['alto'].document.getElementById('hdnIndSesionSalir').value=1;top.frames['alto'].document.getElementById('linkLogin').click();</script>");
				}else{
					chain.doFilter(request, response);
					return;
				}
			}else{
				chain.doFilter(request, response);
				return;
			}
		}
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}