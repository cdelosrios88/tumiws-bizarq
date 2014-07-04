package pe.com.tumi.seguridad.filter;

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
 * Servlet Filter implementation class Seguridad
**/

public class Seguridad implements Filter {
	
	private static String[] URL_ACCESO = new String[] {"/index.jsp","/login.jsf","/pages/login.jsf"};
	private StringHelper stringHelper = new StringHelper();
	
    public Seguridad() {
    }

    public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		HttpSession session = ((HttpServletRequest) request).getSession();
		if(stringHelper.findStringInArray(httpRequest.getServletPath(), URL_ACCESO)){
			chain.doFilter(request, response);
			return;
		}
		if (session == null || session.getAttribute(Constante.SESSION_USER) == null || session.getAttribute(Constante.SESSION_USER)==""){
			httpResponse.sendRedirect(httpRequest.getContextPath() + URL_ACCESO[0]);
		}else{
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}