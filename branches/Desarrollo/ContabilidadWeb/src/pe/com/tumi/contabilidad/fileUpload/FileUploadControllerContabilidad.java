package pe.com.tumi.contabilidad.fileUpload;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;

public class FileUploadControllerContabilidad {
	protected   static Logger	log = Logger.getLogger(FileUploadControllerContabilidad.class);
	//Form atributes
	private String strTitle = null;
	private String strCloseIconPath = null;
	private String strDescripcion = null;
	private String strJsFunction = null;
	private String strJsFunction2 = null;
	
	private Integer intTipoArchivo = null;
	private Integer intItemHistorico = null;
	private Integer intItemArchivo = null;
	private Archivo objArchivo;
	
	
	private Archivo archivoReclamoDev;
	private Archivo archivoCertificadoLeg;
	private Archivo archivoDeclaracionPDT;
	
	/**
	 * Adjunto Giro Credito por Sede Central
	 * @param event
	 */
	public void adjuntarReclamoDevolucion(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECLAMOS_DEVOLUCIONES;
			
			archivoReclamoDev = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoReclamoDev.getRutaAntigua());
			log.info("rutaNueva:"+archivoReclamoDev.getRutaActual());
			renombrarArchivo(archivoReclamoDev.getRutaAntigua(), archivoReclamoDev.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}

	public void adjuntarCertificadoLegalizacion(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_CERTIFICADO_LEGALIZACION;
			
			archivoCertificadoLeg = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoCertificadoLeg.getRutaAntigua());
			log.info("rutaNueva:"+archivoCertificadoLeg.getRutaActual());
			renombrarArchivo(archivoCertificadoLeg.getRutaAntigua(), archivoCertificadoLeg.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}

	public void adjuntarDeclaracionPDT(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_DECLARACION_PDT;
			
			archivoDeclaracionPDT = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoDeclaracionPDT.getRutaAntigua());
			log.info("rutaNueva:"+archivoDeclaracionPDT.getRutaActual());
			renombrarArchivo(archivoDeclaracionPDT.getRutaAntigua(), archivoDeclaracionPDT.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public Archivo procesarArchivoAutorizacion(UploadEvent event, Integer intTipoArch) throws Exception{
		log.info("intTipoArchivo:"+intTipoArch);
		String strNombreArchivo = subirArchivo(event, intTipoArch);
		GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
		TipoArchivo tipoArchivo = facade.getTipoArchivoPorPk(intTipoArch);
		
		Archivo archivo = new Archivo();
		archivo.setId(new ArchivoId());
		archivo.getId().setIntParaTipoCod(intTipoArch);
		if(intItemArchivo!=null)archivo.getId().setIntItemArchivo(intItemArchivo);
		if(intItemHistorico!=null)archivo.getId().setIntItemHistorico(intItemHistorico);
		archivo.setStrNombrearchivo(strNombreArchivo);
		archivo.setTipoarchivo(tipoArchivo);
		archivo.setIntParaEstadoCod(1);
		archivo = facade.grabarArchivo(archivo);
		
		//CGD-12.11.2013
		existeDirectorio(tipoArchivo.getStrRuta()+"\\");
		existeDirectorio(tipoArchivo.getStrRuta()+"\\");
		archivo.setRutaActual(tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
		archivo.setRutaAntigua(tipoArchivo.getStrRuta()+"\\"+strNombreArchivo);
		
		setObjArchivo(archivo);
		log.info("archivo:"+archivo);
		log.info("archivo item:"+archivo.getId().getIntItemArchivo());
		String strExtension = strNombreArchivo.substring(strNombreArchivo.lastIndexOf(".")+1);

		return archivo;
	}
	
	//Para Renombrar Archivo
	public static void renombrarArchivo(String strRuta, String nuevoNombre) throws BusinessException{
		try{
			java.io.File oldFile = new java.io.File(strRuta);
			java.io.File newFile = new java.io.File(nuevoNombre);
			//CGD-12.11.2013
			existeDirectorio(strRuta);
			oldFile.renameTo(newFile);
		}catch(Exception e){
			System.out.println("El renombrado no se ha podido realizar: " + e);
			log.error(e.getMessage(),e);
			throw new BusinessException(e);
		}
	}
	
	public static String subirArchivo(UploadEvent event, Integer intTipoArchivo) throws BusinessException, Exception{
		TipoArchivo tipoArchivo;
		UploadItem uploadItem = event.getUploadItem();
		java.io.File file = uploadItem.getFile();
		
		try {
			log.info("intTipoArchivo: "+intTipoArchivo);
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			tipoArchivo = generalFacade.getTipoArchivoPorPk(intTipoArchivo);
			log.info("tipoArchivo: "+tipoArchivo);
			log.info("tipoArchivo.getStrDescripcion(): "+tipoArchivo.getStrDescripcion());
		} catch (BusinessException e) {
			System.out.println("error: "+ e);
			throw e;
		} catch (EJBFactoryException e) {
			System.out.println("error: "+ e);
			throw new BusinessException(e);
		}
		//**Parche local para IE
		String nombreArchivo = event.getUploadItem().getFileName();
		log.info("nombreArchivo:"+nombreArchivo);
		String nombreArchivoPartes[] = nombreArchivo.split("\\\\");			
		nombreArchivo = nombreArchivoPartes[nombreArchivoPartes.length-1];
		log.info("nombreArchivo:"+nombreArchivo);
		//**
		//String fileName = tipoArchivo.getStrPrefijo()+"-"+uploadItem.getFileName();
		String fileName = tipoArchivo.getStrPrefijo()+"-"+nombreArchivo;
		//CGD-12.11.2013
		existeDirectorio(tipoArchivo.getStrRuta() + "\\");
		
		String target = tipoArchivo.getStrRuta() + "\\" +fileName;
		log.info("target:"+target);
		try {
			InputStream in = new FileInputStream(file);
			OutputStream out = new FileOutputStream(target);
			try {
				// Transfer bytes from in to out
				byte[] buf = new byte[1024*1024*50];//Máximo 50MB
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
			}catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e);
			}finally {
				in.close();
				out.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return fileName;
	}
	
	/**
	 * Verifica si existe el directorio, si no existe lo crea.
	 * @param strRuta
	 * Rodolfo Villarreal - 11.08.2014
	 */
	public static void existeDirectorio(String strRuta){
		try {
			java.io.File folder = new java.io.File(strRuta);
			if (folder.exists())
				  System.out.println("El fichero " + folder + " existe");
			else
				folder.mkdirs();
		} catch (Exception e) {
			log.error("Error en existeDirectorio ---> "+e);
		}
	}

	//Descargar Archivo
	public void descargarArchivo(ActionEvent event)throws Exception{
		try{
			Archivo archivo = (Archivo)event.getComponent().getAttributes().get("archivo");
			descargarArchivo(archivo);
		}catch(Exception e){
			throw e;
		}
	}
	
	public void descargarArchivo(Archivo archivo)throws Exception{
		try{
			
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		
			TipoArchivo tipoArchivo = generalFacade.getTipoArchivoPorPk(archivo.getId().getIntParaTipoCod());
			
			byte[] buf = new byte[1024];
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/force-download");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + archivo.getStrNombrearchivo() + "\"");
			
			String ruta = tipoArchivo.getStrRuta()+ "\\"+ archivo.getStrNombrearchivo();
			log.info(ruta);
			
			//String realPath = sCon.getRealPath(strRutaActual+"/" + strNombreArchivo);
			//String realPath = "C:\\Tumi\\ArchivosAdjuntos\\Documentos\\ExpedientePrestamos\\CopiaDNI"+"\\" + strNombreArchivo;
			String realPath = ruta;
			File file = new File(realPath);
			log.info("Ruta del archivo a descargar (ruta): "+ruta);
			log.info("Ruta del archivo a descargar (realPath): "+realPath);
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
		}catch(Exception e){
			throw e;
		}
	}
	
	
	//Getter y Setters
	public Integer getIntTipoArchivo() {
		return intTipoArchivo;
	}
	public void setIntTipoArchivo(Integer intTipoArchivo) {
		this.intTipoArchivo = intTipoArchivo;
	}
	public Archivo getArchivoReclamoDev() {
		return archivoReclamoDev;
	}
	public void setArchivoReclamoDev(Archivo archivoReclamoDev) {
		this.archivoReclamoDev = archivoReclamoDev;
	}
	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		FileUploadControllerContabilidad.log = log;
	}
	public Integer getIntItemHistorico() {
		return intItemHistorico;
	}
	public void setIntItemHistorico(Integer intItemHistorico) {
		this.intItemHistorico = intItemHistorico;
	}
	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}
	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}
	public Archivo getObjArchivo() {
		return objArchivo;
	}
	public void setObjArchivo(Archivo objArchivo) {
		this.objArchivo = objArchivo;
	}
	private String fileType = null;
	private byte[] dataImage;
	
	public FileUploadControllerContabilidad(){
		strCloseIconPath = "/images/icons/remove_20.png";
		strTitle = "Subir archivos";
		strDescripcion = "Seleccionar archivo";
		//strJsFunction = "putFile()";
		strJsFunction = "putFileDocAdjunto()";
		strJsFunction2 = "putFileDocAdjuntoEspecial()";
	}
	public String getStrTitle() {
		return strTitle;
	}
	public void setStrTitle(String strTitle) {
		this.strTitle = strTitle;
	}
	public String getStrCloseIconPath() {
		return strCloseIconPath;
	}
	public void setStrCloseIconPath(String strCloseIconPath) {
		this.strCloseIconPath = strCloseIconPath;
	}
	public String getStrDescripcion() {
		return strDescripcion;
	}
	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}
	public String getStrJsFunction() {
		return strJsFunction;
	}
	public void setStrJsFunction(String strJsFunction) {
		this.strJsFunction = strJsFunction;
	}
	public String getStrJsFunction2() {
		return strJsFunction2;
	}
	public void setStrJsFunction2(String strJsFunction2) {
		this.strJsFunction2 = strJsFunction2;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public byte[] getDataImage() {
		return dataImage;
	}
	public void setDataImage(byte[] dataImage) {
		this.dataImage = dataImage;
	}

	public Archivo getArchivoCertificadoLeg() {
		return archivoCertificadoLeg;
	}

	public void setArchivoCertificadoLeg(Archivo archivoCertificadoLeg) {
		this.archivoCertificadoLeg = archivoCertificadoLeg;
	}

	public Archivo getArchivoDeclaracionPDT() {
		return archivoDeclaracionPDT;
	}

	public void setArchivoDeclaracionPDT(Archivo archivoDeclaracionPDT) {
		this.archivoDeclaracionPDT = archivoDeclaracionPDT;
	}

}

