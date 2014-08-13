package pe.com.tumi.seguridad.login.filter;

import java.io.IOException;
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
import pe.com.tumi.common.util.StringHelper;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.seguridad.login.domain.Session;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.facade.LoginFacadeLocal;
import pe.com.tumi.seguridad.login.facade.LoginFacadeRemote;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeLocal;

/**
 * Servlet Filter implementation class SeguridadFilter
 * Objetivo: Evitar acceder desde cualquier URL a la aplicacion , luego de cerrar sesiòn.
 * Autor: Cèsar Pèrez  Fecha:25/04/2013 16:20:00
**/
public class SeguridadFilter implements Filter {
	
	private static String[] URL_REDIRECT = new String[] {"/portal/autentica/login.jsf"};
	private static String[] URL_ACCESO = new String[] {"/empresa.jsf","/portal/autentica/empresa.jsf", "/portal/autoriza/principal.jsp"};
	/**
	 * @uml.property  name="stringHelper"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private StringHelper stringHelper = new StringHelper();
	
    public SeguridadFilter() {
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
		
		//Procede a realizar validaciones
		if(stringHelper.findStringInArray(httpRequest.getServletPath(), URL_ACCESO)){
			if (session == null || session.getAttribute(Constante.USUARIO_LOGIN) == null ){
				//Sesiòn inactiva, redirecciona a pàgina de Login
				httpResponse.sendRedirect(httpRequest.getContextPath() + URL_REDIRECT[0]);
			}else{
				//Continua
				chain.doFilter(request, response);
			}
		}else{
			//Inicio: REQ14-003 - Bizarq - 08/08/2014
			if(httpRequest.getServletPath() != null
					&& httpRequest.getServletPath().indexOf("login.jsf") != -1){
				chain.doFilter(request, response);
				return;
			}
			/*
			 * 1- Buscar que el usuario autenticado tenga la sesion activa
			 * 	1.1- Si la sesion esta activa en la BD 
			 * 		-> chain.doFilter(request, response);
			 * 	1.2- Si la sesion no esta activa en la BD 
			 * 		-> Eliminar el objeto sesion 
			 * 		-> httpResponse.sendRedirect(httpRequest.getContextPath() + URL_REDIRECT[0]);
			 */	
			if(session != null){
				Session objSession = (Session)session.getAttribute("objSession");
				Usuario objUsuario = (Usuario)session.getAttribute("usuario");
				objUsuario.getObjSession().setTsFechaActividad(new Timestamp(new Date().getTime()));
				session.setAttribute("usuario", objUsuario);
				Session objSessionDB = null;
				if(objSession != null){
					LoginFacadeLocal loginFacade;
					try {
						//Instanciar el loginFacade para consulta a base de datos
						loginFacade = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
						objSessionDB = loginFacade.getSessionPorPk(objSession.getId().getIntSessionPk());
					} catch (EJBFactoryException e) {
						e.printStackTrace();
					} catch (BusinessException e) {
						e.printStackTrace();
					}	
					if(objSessionDB != null && objSessionDB.getIntIdEstado()!= null &&
								objSessionDB.getIntIdEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO)){
							session.removeAttribute(Constante.USUARIO_LOGIN);
							session.removeAttribute("objSession");
							session.invalidate();
							httpResponse.sendRedirect(httpRequest.getContextPath() + URL_REDIRECT[0]);
							return;
					}else{
						
						chain.doFilter(request, response);
						return;
					}
				}
			}else{
				chain.doFilter(request, response);
				return;
			}
			//Continua
			//chain.doFilter(request, response);
			//return;
		}
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}