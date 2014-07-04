package pe.com.tumi.report.engine;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaResumenPrestamos;
import pe.com.tumi.report.business.MakerEstadoCuentaTabResumen;

public class Reporteador {

	protected static Logger log = Logger.getLogger(Reporteador.class);
	/**
	 * Clase que se encarga de generar el reporte.
	 * @param strNombreReporte - nombre del reporte creado
	 * @param parameters - mapa que contendra los parametros, si los tuviera.
	 */
	public void executeReport(String strNombreReporte, HashMap<String,Object> parameters){
		String rutaFolder="";
		String webRoot = "";
		String strJRXML = "";
		String strPDF = "";
		try {
	       
			ServletContext servletContext2 = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			rutaFolder= Constante.REPORT_RUTA;
	        webRoot =servletContext2.getRealPath(rutaFolder);
			
			strJRXML = strNombreReporte + Constante.REPORT_EXT_JRXML;
			strPDF = strNombreReporte + Constante.REPORT_EXT_PDF;

			InputStream inputStream = new FileInputStream (webRoot + strJRXML);
			//Tab Cabecera - Sucursales
//			MakerEstadoCuentaCabecera makerCabecera = new MakerEstadoCuentaCabecera();
//			List<Juridica> dataBeanListSucursales = makerCabecera.getDataBeanListSucursales();
//			JRBeanCollectionDataSource beanColDataSource2 = new JRBeanCollectionDataSource(dataBeanListSucursales);		
			
			//Tab Resumen
			MakerEstadoCuentaTabResumen makerTabResumen = new MakerEstadoCuentaTabResumen();
			List<DataBeanEstadoCuentaResumenPrestamos> dataBeanList = makerTabResumen.getDataBeanList();
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataBeanList);			 
			
			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = new JasperPrint();
			
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);//beanColDataSource,);
			
			JasperExportManager.exportReportToPdfFile(jasperPrint, webRoot + strPDF);
			String strArch = webRoot + strPDF;
			forceDownloadFile(strArch , strPDF);

		} catch (Exception e) {
			log.error("Error en rptEstCtaResumen ---> "+e);
		}
	}
		
	
	/**
	 * Forzara la descarga o visualizacion del reporte
	 * @param ruta
	 * @param strNombreArchivo
	 */
	public void forceDownloadFile(String strRaiz, String strNombreArchivo) {
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/force-download");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + strNombreArchivo);
		
		byte[] buf = new byte[1024*1024*10];// maximo 10 megas
		try{
			String realPath = strRaiz;
			File file = new File(realPath);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int)length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int)length);
			}
			in.close();
			out.flush();
			out.close();
			
			FacesContext.getCurrentInstance().responseComplete();
		}catch (Exception exc){
			log.error("Error en forceDownloadFile ---> "+exc);
			exc.printStackTrace();
		} 
	}
}
