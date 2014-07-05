package pe.com.tumi.framework.vista.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.seguridad.login.facade.LoginFacadeLocal;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TestServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Estoy en el servlet");
		LoginFacadeLocal local = null;
		try {
			local = (LoginFacadeLocal)EJBFactory.getLocal(LoginFacadeLocal.class);
			//log.info(local.saludo());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}

}
