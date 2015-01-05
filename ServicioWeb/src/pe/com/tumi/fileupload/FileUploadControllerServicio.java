package pe.com.tumi.fileupload;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;


public class FileUploadControllerServicio {
	
	protected   static Logger	log = Logger.getLogger(FileUploadControllerServicio.class);

	//Form atributes
	private String strTitle = null;
	private String strCloseIconPath = null;
	private String strDescripcion = null;
	private String strJsFunction = null;
	private String strJsFunction2 = null;
	
	//File atributes
	private Integer intItemArchivo = null; 
	private Integer intItemHistorico = null; 
	private Integer intTipoArchivo = null;
	private Archivo objArchivo;
	private	Archivo archivoCartaPoder;
	private	Archivo archivoReniec;
	private	Archivo archivoInfoCorp;
	
	private	Archivo archivoDeJu;
	private	Archivo archivoReniecAut;
	
	private Archivo archivoGiroCredito;
	private Archivo archivoGiroPrevision;
	private Archivo archivoGiroLiquidacion;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 16.12.2014
	private Archivo archivoSolicitudPrestamo;
	
	public Archivo getArchivoDeJu() {
		return archivoDeJu;
	}

	public void setArchivoDeJu(Archivo archivoDeJu) {
		this.archivoDeJu = archivoDeJu;
	}

	public Archivo getArchivoReniecAut() {
		return archivoReniecAut;
	}

	public void setArchivoReniecAut(Archivo archivoReniecAut) {
		this.archivoReniecAut = archivoReniecAut;
	}

	public Archivo getArchivoInfoCorp() {
		return archivoInfoCorp;
	}

	public void setArchivoInfoCorp(Archivo archivoInfoCorp) {
		this.archivoInfoCorp = archivoInfoCorp;
	}

	private String fileType = null;
	private byte[] dataImage;
	
	public FileUploadControllerServicio(){
		strCloseIconPath = "/images/icons/remove_20.png";
		strTitle = "Subir archivos";
		strDescripcion = "Seleccionar archivo";
		//strJsFunction = "putFile()";
		strJsFunction = "putFileDocAdjunto()";
		strJsFunction2 = "putFileDocAdjuntoEspecial()";
	}
	// 
	public void adjuntarCartaPoder(UploadEvent event){
		try{
			intTipoArchivo = 1; //Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPODER;
			
			archivoCartaPoder = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoCartaPoder.getRutaAntigua());
			log.info("rutaNueva:"+archivoCartaPoder.getRutaActual());
			renombrarArchivo(archivoCartaPoder.getRutaAntigua(), archivoCartaPoder.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
		
	public void adjuntarInfoCorp(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_INFOCORP;
			
			archivoInfoCorp = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoInfoCorp.getRutaAntigua());
			log.info("rutaNueva:"+archivoInfoCorp.getRutaActual());
			renombrarArchivo(archivoInfoCorp.getRutaAntigua(), archivoInfoCorp.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarReniec(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_RENIEC;
			
			archivoReniec = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoReniec.getRutaAntigua());
			log.info("rutaNueva:"+archivoReniec.getRutaActual());
			renombrarArchivo(archivoReniec.getRutaAntigua(), archivoReniec.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void adjuntarDeJu(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_DECLARACIONJURADA;
			
			archivoDeJu = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoDeJu.getRutaAntigua());
			log.info("rutaNueva:"+archivoDeJu.getRutaActual());
			renombrarArchivo(archivoDeJu.getRutaAntigua(), archivoDeJu.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarReniecAut(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_RENIEC;
			
			archivoReniecAut = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoReniecAut.getRutaAntigua());
			log.info("rutaNueva:"+archivoReniecAut.getRutaActual());
			renombrarArchivo(archivoReniecAut.getRutaAntigua(), archivoReniecAut.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	/**
	 * Adjunto Giro Credito por Sede Central
	 * @param event
	 */
	public void adjuntarGiroCredito(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADJUNTOS_CREDITOS;
			
			archivoGiroCredito = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoGiroCredito.getRutaAntigua());
			log.info("rutaNueva:"+archivoGiroCredito.getRutaActual());
			renombrarArchivo(archivoGiroCredito.getRutaAntigua(), archivoGiroCredito.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	/**
	 * Adjunto Giro Previsión por Sede Central
	 * @param event
	 */
	public void adjuntarGiroPrevision(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADJUNTOS_GIROPREVISION;
			
			archivoGiroPrevision = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoGiroPrevision.getRutaAntigua());
			log.info("rutaNueva:"+archivoGiroPrevision.getRutaActual());
			renombrarArchivo(archivoGiroPrevision.getRutaAntigua(), archivoGiroPrevision.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	/**
	 * Adjunto Giro Liquidación por Sede Central
	 * @param event
	 */
	public void adjuntarGiroLiquidacion(UploadEvent event){
		try{

			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADJUNTOS_GIROLIQUIDACION;
			
			archivoGiroLiquidacion = procesarArchivoAutorizacion(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoGiroLiquidacion.getRutaAntigua());
			log.info("rutaNueva:"+archivoGiroLiquidacion.getRutaActual());
			renombrarArchivo(archivoGiroLiquidacion.getRutaAntigua(), archivoGiroLiquidacion.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	public Archivo procesarArchivo(UploadEvent event, Integer intTipoArch) throws Exception{
		log.info("intTipoArchivo:"+intTipoArch);
		Archivo archivo = new Archivo();
		
		try {
			String strNombreArchivo = subirArchivo(event, intTipoArch);
			GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			TipoArchivo tipoArchivo = facade.getTipoArchivoPorPk(intTipoArch);
			
			archivo.setId(new ArchivoId());
			archivo.getId().setIntParaTipoCod(intTipoArch);
			
			if(intItemArchivo!=null)archivo.getId().setIntItemArchivo(intItemArchivo);
			if(intItemHistorico!=null)archivo.getId().setIntItemHistorico(intItemHistorico);
			archivo.setStrNombrearchivo(strNombreArchivo);
			archivo.setTipoarchivo(tipoArchivo);
			archivo.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			archivo = facade.grabarArchivo(archivo);
			
			//CGD-12.11.2013
			existeDirectorio(tipoArchivo.getStrRuta()+"\\");
			
			archivo.setRutaActual(tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
			archivo.setRutaAntigua(tipoArchivo.getStrRuta()+"\\"+strNombreArchivo);
			
			String strExtension = strNombreArchivo.substring(strNombreArchivo.lastIndexOf(".")+1);
			resetDataImage(tipoArchivo.getStrRuta()+"\\"+strNombreArchivo,strExtension);
			setObjArchivo(archivo);
			log.info("archivo:"+archivo);
			log.info("archivo item:"+archivo.getId().getIntItemArchivo());
			
		} catch (Exception e) {
			log.error("Error en procesarArchivo --- "+e);
		}
		return archivo;
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
	

	public void adjuntarArchivo(UploadEvent event) throws Exception{
		try{			
			objArchivo = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+objArchivo.getRutaAntigua());
			log.info("rutaNueva:"+objArchivo.getRutaActual());
			renombrarArchivo(objArchivo.getRutaAntigua(), objArchivo.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}		 
	}
	
	public void adjuntarArchivoSolicitudPrestamo(UploadEvent event) throws Exception{
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PRESTAMO;
			
			objArchivo = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+objArchivo.getRutaAntigua());
			log.info("rutaNueva:"+objArchivo.getRutaActual());
			renombrarArchivo(objArchivo.getRutaAntigua(), objArchivo.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}		 
	}
	
	public void resetDataImage(String strPath, String strExtension) throws IOException{
		
		try {
			File iofile = new File(strPath);
			BufferedImage image = ImageIO.read(iofile);
			// O P E N
			ByteArrayOutputStream baos = new ByteArrayOutputStream(10000);
			// W R I T E
			ImageIO.write(image,strExtension.trim(),baos);
			// C L O S E
			baos.flush();
			byte[] byteImg = baos.toByteArray();
			baos.close();
			setDataImage(byteImg);
		} catch (Exception e) {
			log.error("Error en resetDataImage --- "+e);
		}
		
	}
	
	public void setParamArchivo(Integer pIntItemArchivo, Integer pIntItemHistorico, Integer pIntTipoArchivo){
		setIntItemArchivo(pIntItemArchivo);
		setIntItemHistorico(pIntItemHistorico);
		setIntTipoArchivo(pIntTipoArchivo);
	}
	
	
	/**
	 * Verifica si existe el directorio, si no existe lo crea.
	 * @param strRuta
	 * CGD - 02.11.2013
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
	
	/**
	 * Autor: jchavez / Tarea: Creación / Fecha: 16.12.2014
	 * Funcionalidad: Procesa y renombra el archivo adjunto en la Solicitud de Préstamo.
	 * @param event
	 */
//	public void adjuntarSolicitudPrestamo(UploadEvent event) throws Exception{
//		try{
//			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PRESTAMO;
//			
//			archivoSolicitudPrestamo = procesarArchivo(event, intTipoArchivo);
//			log.info("rutaAntigua:"+objArchivo.getRutaAntigua());
//			log.info("rutaNueva:"+objArchivo.getRutaActual());
//			renombrarArchivo(archivoSolicitudPrestamo.getRutaAntigua(), archivoSolicitudPrestamo.getRutaActual());
//			
//		}catch(Exception e){
//			log.error(e.getMessage(),e);
//		}		 
//	}
	public void adjuntarSolicitudPrestamo(UploadEvent event) throws Exception{
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PRESTAMO;
			
			objArchivo = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+objArchivo.getRutaAntigua());
			log.info("rutaNueva:"+objArchivo.getRutaActual());
			renombrarArchivo(objArchivo.getRutaAntigua(), objArchivo.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}		 
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 16.12.2014
	public void descargarArchivo(ActionEvent event)throws Exception{
		try{
			Archivo archivo = (Archivo)event.getComponent().getAttributes().get("archivo");
			descargarArchivo(archivo);
		}catch(Exception e){
			throw e;
		}
	}
	
	//Autor: jchavez / Tarea: Creación / Fecha: 16.12.2014
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
	//Getters & Setters
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
	public Integer getIntItemArchivo() {
		return intItemArchivo;
	}
	public void setIntItemArchivo(Integer intItemArchivo) {
		this.intItemArchivo = intItemArchivo;
	}
	public Integer getIntItemHistorico() {
		return intItemHistorico;
	}
	public void setIntItemHistorico(Integer intItemHistorico) {
		this.intItemHistorico = intItemHistorico;
	}
	public Integer getIntTipoArchivo() {
		return intTipoArchivo;
	}
	public void setIntTipoArchivo(Integer intTipoArchivo) {
		this.intTipoArchivo = intTipoArchivo;
	}
	public Archivo getObjArchivo() {
		return objArchivo;
	}
	public void setObjArchivo(Archivo objArchivo) {
		this.objArchivo = objArchivo;
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
	public Archivo getArchivoCartaPoder() {
		return archivoCartaPoder;
	}
	public void setArchivoCartaPoder(Archivo archivoCartaPoder) {
		this.archivoCartaPoder = archivoCartaPoder;
	}	
	public Archivo getArchivoReniec() {
		return archivoReniec;
	}
	public void setArchivoReniec(Archivo archivoReniec) {
		this.archivoReniec = archivoReniec;
	}
	
	public String getStrJsFunction2() {
		return strJsFunction2;
	}
	public void setStrJsFunction2(String strJsFunction2) {
		this.strJsFunction2 = strJsFunction2;
	}
	public Archivo getArchivoGiroCredito() {
		return archivoGiroCredito;
	}
	public void setArchivoGiroCredito(Archivo archivoGiroCredito) {
		this.archivoGiroCredito = archivoGiroCredito;
	}
	public Archivo getArchivoGiroPrevision() {
		return archivoGiroPrevision;
	}
	public void setArchivoGiroPrevision(Archivo archivoGiroPrevision) {
		this.archivoGiroPrevision = archivoGiroPrevision;
	}
	public Archivo getArchivoGiroLiquidacion() {
		return archivoGiroLiquidacion;
	}
	public void setArchivoGiroLiquidacion(Archivo archivoGiroLiquidacion) {
		this.archivoGiroLiquidacion = archivoGiroLiquidacion;
	}
	public Archivo getArchivoSolicitudPrestamo() {
		return archivoSolicitudPrestamo;
	}
	public void setArchivoSolicitudPrestamo(Archivo archivoSolicitudPrestamo) {
		this.archivoSolicitudPrestamo = archivoSolicitudPrestamo;
	}	
}