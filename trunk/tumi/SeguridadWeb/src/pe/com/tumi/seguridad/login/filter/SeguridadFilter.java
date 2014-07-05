package pe.com.tumi.seguridad.login.filter;

import java.io.IOException;

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
			//Continua
			chain.doFilter(request, response);
			return;
		}

	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}