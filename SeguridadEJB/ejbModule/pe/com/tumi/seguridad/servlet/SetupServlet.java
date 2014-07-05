package pe.com.tumi.seguridad.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.util.Properties;

public class SetupServlet extends HttpServlet {

	private static final long serialVersionUID = -8455883082126987155L;
	private Properties props;

	//private static String ruta=((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("") + ConstanteReporte.RUTA_LOG4J;
	private static String ruta = "C:/var/log/log4j/log4j.properties";

	public SetupServlet(){
	}
	
	private void cargaPropiedades() {
		try {
			props = new java.util.Properties();
			java.io.FileInputStream fis = new java.io.FileInputStream(new java.io.File(ruta));
			props.load(fis);
			fis.close();
		} catch (Exception e) {
			System.out.println("No se puede kooooo cargar el archivo de propiedades de la ruta: " + ruta);
			e.printStackTrace();
		}
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cargaPropiedades();
		PropertyConfigurator.configure(props);
		Logger log = Logger.getLogger(SetupServlet.class);
		log.info("*** MODULO DE SEGURIDAD - FACTORING ***");
		log.info("inicializando...");
	}

	public void destroy() {
		super.destroy();
	}
}