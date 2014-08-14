package pe.com.tumi.seguridad.login.filter;

import java.io.IOException;
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
		
		//Procede a realizar validaciones
		if(session != null){
			LoginFacadeRemote loginFacade;
			/*
			 * 1- Buscar que el usuario autenticado tenga la sesion activa
			 * 	1.1- Si la sesion esta activa en la BD 
			 * 		-> chain.doFilter(request, response);
			 * 	1.2- Si la sesion no esta activa en la BD 
			 * 		-> Eliminar el objeto sesion 
			 * 		-> httpResponse.sendRedirect(httpRequest.getContextPath() + URL_REDIRECT[0]);
			 */			
			Session objSession = (Session)session.getAttribute("objSession");
			Session objSessionDB = null;
			if(objSession != null){
				try {
					//Instanciar el loginFacade para consulta a base de datos
					loginFacade = (LoginFacadeRemote)EJBFactory.getLocal(LoginFacadeRemote.class);
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
						httpResponse.sendRedirect(httpRequest.getContextPath() + URL_REDIRECT[0]);
						return;
				}else{
					chain.doFilter(request, response);
				}
			}else{
				chain.doFilter(request, response);
			}
		}
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}