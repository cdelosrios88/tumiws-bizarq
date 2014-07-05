package pe.com.tumi.common.controller;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.ConstanteReporte;
import pe.com.tumi.common.util.ItemHelper;

public class GenericReportController extends GenericController{
	
	/** HashMap con los parÃ¡metros que servirÃ¡n como filtros para el reporte*/
	private Map parameters;
	/** Ruta donde se ubica el reporte como archivo jasper (en la mayoría de casos de hace referencia al 
	 * reporte genérico ya sea vertical u horizontal)*/
	private String jasperFile;
	/** Objeto para la conexión a base de datos */
	private Connection connection;
	/** Cadena de texto que define a cual tipo de archivo será exportado el reporte*/
	private String typeExportFile;
	/** Nombre del reporte */
	private String reportName;
	/** Objeto para formatear una fecha*/
	private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-hhmmss");
	/** Destino en disco duro del reporte generado*/
	private String destination;
	/** Flag que define si es que se generará en disco disco o en response
	 * <b>true</b> para generar en disco disco y <b>false</b> para generarlo con el response*/
	private boolean inHardDisk;	

	public boolean validate(){
		return true;
	}
	
	public void createReportExcel(HSSFWorkbook libro)throws Exception{
		getResponse().setContentType("application/vnd.ms-excel");
		getResponse().setHeader("Content-Disposition","inline; filename=" + getReportName() + ".xls");
		getResponse().setHeader("Pragma", "no-cache");
		ServletOutputStream outputStream = getResponse().getOutputStream();
		libro.write(outputStream);
		outputStream.flush();
		outputStream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}

	public void createReport() throws Exception{
		try{
			log.info("SE COMIENZA CON EL PROCESO DE CREACIÓN DE ARCHIVOS");
			byte[] bytes = null;
			
			if(getJasperFile()==null || getJasperFile().equals("")){
				throw new Exception("No se ha definido la ubicación del archivo jasper.");
			}
			log.info("JASPERFILE : " + getJasperFile());
			
			if(getTypeExportFile()==null){
				setTypeExportFile(ConstanteReporte.ARCHIVO_PDF);
			}
			log.info("TIPO DE ARCHIVO : " + getTypeExportFile());
			
			File reportFile = new File(getJasperFile());
			
			if (!reportFile.exists()) {
		        throw new JRRuntimeException("El archivo jasper no existe en la siguiente ubicación definida.");
		    }
			JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, getParameters(), getConnection());
			log.info("EL REPORTE HA SIDO LLENADO CON DATOS");
			JRExporter exporter = null;
			PrintWriter out = null;
			
			//Si es que no se ha definido el tipo de archivo al cual será exportado el reporte, se 
			//generará un archivo PDF.
			if(getReportName()==null || getReportName().equals("")){
				Calendar hoy = new GregorianCalendar();
				setReportName("reporte-"+df.format(hoy.getTime()));
			}
			log.info("NOMBRE DEL REPORTE : " + getReportName());
			
			if(getTypeExportFile().equals(ConstanteReporte.ARCHIVO_PDF)){
				log.info("SE REALIZARA LA EXPORTACION A PDF");
				if(!isInHardDisk()){
					
					getResponse().setContentType("application/pdf");
					getResponse().addHeader("Content-Disposition","attachment; filename=" + getReportName() + ".pdf");
					
					bytes = JasperExportManager.exportReportToPdf(jasperPrint);
					//Para el caso del PDF, se necesita capturar el jasperFile exportado en un arreglo de bytes 
					//para que éste sirva para la escritura del response.getOutputStream()
					ServletOutputStream outputStream = getResponse().getOutputStream();
					outputStream.write(bytes);
					outputStream.flush();
					outputStream.close();
					FacesContext.getCurrentInstance().responseComplete();
					
					log.info("ARCHIVO EXPORTADO SATISFACTORIAMENTE");
					
					return;
						
				}else{
					if(getDestination()==null){
						throw new Exception("La ruta destino del reporte no ha sido especificada.");
					}
					JasperExportManager.exportReportToPdfFile(jasperPrint,getDestination()+"\\"+getReportName()+ ".pdf");
				}
				
			}else if(getTypeExportFile().equals(ConstanteReporte.ARCHIVO_EXCEL)){
				log.info("SE REALIZARA LA EXPORTACION A EXCEL");
				//Aun no se implementa la posibilidad de exportación a Excel
				getResponse().setContentType("application/vnd.ms-excel");
				getResponse().addHeader("Content-Disposition","attachment; filename=" + getReportName() + ".xls");
				
		        exporter = new JRXlsExporter();
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, getResponse().getOutputStream());
		        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,false); 
		        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS ,true);
		        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS ,true);
		        exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN ,true);
		        exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE ,true);
			}else if(getTypeExportFile().equals(ConstanteReporte.ARCHIVO_HTML)){ 
	            getResponse().setContentType("text/html");
				getResponse().addHeader("Content-Disposition","attachment; filename=" + getReportName() + ".html");
				
				out = getResponse().getWriter();
				exporter = new JRHtmlExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,	jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
				exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "charset=ISO-8859-1");
				exporter.exportReport();
				out.flush();
				out.close();
				FacesContext.getCurrentInstance().responseComplete();
				return;
			}else{
				throw new Exception("Tipo de archivo para exportar el reporte es desconocido.");
			}
			if(!isInHardDisk()){
				exporter.exportReport();
				ServletOutputStream outputStream = getResponse().getOutputStream();
				outputStream.flush();
				outputStream.close();
				FacesContext.getCurrentInstance().responseComplete();
			}
			log.info("ARCHIVO EXPORTADO SATISFACTORIAMENTE");
			
		}catch(JRException e){
			log.error("Error al exportar y generar la vista del reporte : " + ItemHelper.getStackTrace(e));
			throw new Exception("Error al exportar y generar la vista del reporte : " + ItemHelper.getStackTrace(e));
		}
	}
	/**
	 * Método para realizar acciones antes de generar el reporte
	 * @param event
	 */
	public void beforeGenerate(ActionEvent event) throws Exception{
		
	}
	/**
	 * Método para realizar acciones después de generar el reporte
	 * @param event
	 */
	public void afterGenerate(ActionEvent event){
		
	}
	/**
	 * Método para la generación del reporte
	 * @param event
	 * @throws Exception
	 */
	public void doGenerate(ActionEvent event) throws Exception{
		try{
			createConnection();
			//Generar reporte
			beforeCreateReport();
			createReport();
		}catch(Exception e){
			log.error("Error en la generación del reporte : " + ItemHelper.getStackTrace(e));
			throw new Exception("Error en la generación del reporte : " + ItemHelper.getStackTrace(e));
		}
	}
	
	public void beforeCreateReport(){
		if(parameters != null && typeExportFile != null){
			if(typeExportFile.equals(ConstanteReporte.ARCHIVO_EXCEL) ||
				typeExportFile.equals(ConstanteReporte.ARCHIVO_HTML)){
				parameters.put("IS_IGNORE_PAGINATION", new Boolean(true));
			}
		}
	}
	
	/**
	 * Obtiene la conexión en caso ésta sea nula. 
	 * @throws Exception 
	 */
	public void createConnection() throws Exception{
		if(connection==null){
			log.info("CONEXIÓN NULA>> SE TOMA LA CONEXIÓN DEL SERVICE");
			connection = getService().getConnection();
		}
	}
	/**
	 * Método que unifica el ciclo completo en la generación de un reporte<br/>
	 * 1. {@link #beforeGenerate(ActionEvent)}<br/>
	 * 2. {@link #validate()}<br/>
	 * 3. {@link #doGenerate(ActionEvent)}<br/>
	 * 4. {@link #afterGenerate(ActionEvent)}
	 * @param event
	 * @throws Exception
	 */
	public void generate(ActionEvent event) throws Exception{
		try{
			beforeGenerate(event);
			if(validate()){
				doGenerate(event);
				afterGenerate(event);
			}	
		}catch(Exception e){
			setMessageError("Error en la generaciön del reporte");
			log.error(ItemHelper.getStackTrace(e));
			throw e;
		}
		
	}
	
	/**
	 * Setea el tipo de archivo al cual se exportará el jasperFile. En caso que el 
	 * tipo sea nulo, se asignará PDF.
	 * @param fileType tipo de archivo al cual se exportará
	 */
	public void setExportFileType(String fileType){
		if(fileType==null){
			setTypeExportFile(ConstanteReporte.ARCHIVO_PDF);
		}
		
		if(fileType.equals(ConstanteReporte.ARCHIVO_PDF)){
			setTypeExportFile(ConstanteReporte.ARCHIVO_PDF);	
		}else if(fileType.equals(ConstanteReporte.ARCHIVO_EXCEL)){
			setTypeExportFile(ConstanteReporte.ARCHIVO_EXCEL);
		}else if(fileType.equals(ConstanteReporte.ARCHIVO_HTML)){
			setTypeExportFile(ConstanteReporte.ARCHIVO_HTML);
		}else{
			setTypeExportFile(ConstanteReporte.ARCHIVO_PDF);
		}
	}
	
	public Map getParameters() {
		return parameters;
	}
	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public String getTypeExportFile() {
		return typeExportFile;
	}
	public void setTypeExportFile(String typeExportFile) {
		this.typeExportFile = typeExportFile;
	}
	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getJasperFile() {
		return jasperFile;
	}

	public void setJasperFile(String jasperFile) {
		this.jasperFile = jasperFile;
	}
	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	public boolean isInHardDisk() {
		return inHardDisk;
	}

	public void setInHardDisk(boolean inHardDisk) {
		this.inHardDisk = inHardDisk;
	}
}
