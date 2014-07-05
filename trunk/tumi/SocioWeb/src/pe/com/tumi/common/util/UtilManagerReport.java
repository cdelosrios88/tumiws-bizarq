package pe.com.tumi.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;

/**
 * @author cdelosrios Clase Utilitaria para exportar Reportes
 * 
 */
public class UtilManagerReport {
	protected static Logger log = Logger.getLogger(UtilManagerReport.class);

	/**
	 * Utilitario que se encarga de generar el reporte.
	 * 
	 * @param strNombreReporte
	 *            - nombre del reporte creado
	 * @param parameters
	 *            - mapa que contendra los parametros, si los tuviera.
	 * @param beanDataList
	 *            - Colección de objetos utilizado para añadir los datos
	 *            almacenados y no utilizar una nueva conexión a la BD
	 * @param intTipoReporte
	 *            - Parámetro que indica en que tipo de reporte se exportará: 
	 *            1->PDF   2->HTML   3->XLS
	 */
	public static void generateReport(String strNombreReporte,
			HashMap<String, Object> parameters,
			Collection<Object> beanDataList, Integer intTipoReporte) {
		String rutaFolder = "";
		String webRoot = "";
		String strJasper = "";
		String strExtension = "";
		JRExporter exporter = null;
		try {
			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			rutaFolder = Constante.REPORT_RUTA;
			webRoot = servletContext.getRealPath(rutaFolder);
			
			parameters.put("SUBREPORT_DIR", webRoot);
			parameters.put("REPORT_LOCALE", Locale.ENGLISH);
			
			strJasper = strNombreReporte + Constante.REPORT_EXT_JASPER;
			if(intTipoReporte.equals(Constante.PARAM_T_TIPOREPORTE_PDF)){
				strExtension = strNombreReporte + Constante.REPORT_EXT_PDF;
			}else if(intTipoReporte.equals(Constante.PARAM_T_TIPOREPORTE_HTML)){
				strExtension = strNombreReporte + Constante.REPORT_EXT_HTML;
			}else if(intTipoReporte.equals(Constante.PARAM_T_TIPOREPORTE_EXCEL)){
				strExtension = strNombreReporte + Constante.REPORT_EXT_XLS;
			}

			InputStream inputStream = new FileInputStream(webRoot + strJasper);

			JasperReport reporte = (JasperReport) JRLoader.loadObject(inputStream);
			JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,
					parameters, new JRBeanCollectionDataSource(beanDataList));
			if(intTipoReporte.equals(Constante.PARAM_T_TIPOREPORTE_PDF)){
				exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE,
						new java.io.File(webRoot + strExtension));
			}else if(intTipoReporte.equals(Constante.PARAM_T_TIPOREPORTE_HTML)){
				exporter = new JRHtmlExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE,
						new java.io.File(webRoot + strExtension));
			}else if(intTipoReporte.equals(Constante.PARAM_T_TIPOREPORTE_EXCEL)){
				exporter = new JRXlsExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE,
						new java.io.File(webRoot + strExtension));
			}
			exporter.exportReport();
			log.info("rutaReporte: " + webRoot + strExtension);
			forceDownloadFile(webRoot + strExtension, strExtension);
		} catch (Exception e) {
			log.error("Error en la descarga del reporte ---> "
					+ strNombreReporte + e);
		}
	}

	/**
	 * Forzara la descarga o visualizacion del reporte
	 * 
	 * @param ruta
	 * @param strNombreArchivo
	 */
	public static void forceDownloadFile(String strRaiz, String strNombreArchivo) {
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/force-download");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ strNombreArchivo);

		byte[] buf = new byte[1024 * 1024 * 10];// maximo 10 megas
		try {
			String realPath = strRaiz;
			File file = new File(realPath);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(
					new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int) length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int) length);
			}
			in.close();
			out.flush();
			out.close();

			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception exc) {
			log.error("Error en forceDownloadFile ---> " + exc);
			exc.printStackTrace();
		}
	}
}
