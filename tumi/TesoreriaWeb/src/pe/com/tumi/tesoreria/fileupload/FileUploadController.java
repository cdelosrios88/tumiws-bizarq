package pe.com.tumi.tesoreria.fileupload;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.common.util.ManejadorExcel;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.solicitudPersonal.controller.SolicitudPersonalController;

public class FileUploadController {

	protected   static Logger	log = Logger.getLogger(FileUploadController.class);
	
	//Form atributes
	private String strTitle = null;
	private String strCloseIconPath = null;
	private String strDescripcion = null;
	private String strJsFunction = null;
	
	//File atributes
	private Integer intItemArchivo = null; 
	private Integer intItemHistorico = null; 
	private Integer intTipoArchivo = null;
	private Archivo objArchivo = null;
	private	Archivo archivoFoto = null;
	private	Archivo archivoFirma = null;
	private Archivo archivoCartaPoder = null;
	private Archivo archivoVoucher = null;
	private Archivo archivoDocumento = null;
	private Archivo archivoSustento = null;
	private List<SolicitudPersonal> listaSolicitudPersonal;
	
	private String fileType = null;
	private byte[] dataImage;
	
	public FileUploadController(){
		strCloseIconPath = "/images/icons/remove_20.png";
		strTitle = "Subir archivos";
		strDescripcion = "Seleccionar archivo";
		strJsFunction = "putFile()";
	}
	
	public void adjuntarArchivo(UploadEvent event) throws BusinessException, EJBFactoryException, IOException{
		try{
			//Subir archivo
			String strNombreArchivo = FileUtil.subirArchivo(event, intTipoArchivo);
			//Obtener parámetros por tipo de archivo
			GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			TipoArchivo tipoArchivo = facade.getTipoArchivoPorPk(intTipoArchivo);
			//Instanciar archivo
			Archivo archivo = new Archivo();
			archivo.setId(new ArchivoId());
			archivo.getId().setIntParaTipoCod(intTipoArchivo);
			if(intItemArchivo!=null)archivo.getId().setIntItemArchivo(intItemArchivo);
			if(intItemHistorico!=null)archivo.getId().setIntItemHistorico(intItemHistorico);
			archivo.setStrNombrearchivo(strNombreArchivo);
			archivo.setTipoarchivo(tipoArchivo);
			archivo.setIntParaEstadoCod(intTipoArchivo);
			//setear contrato a PerLaboral 
			setObjArchivo(archivo);
			//Validar si es imagen
			String strExtension = strNombreArchivo.substring(strNombreArchivo.lastIndexOf(".")+1);
			System.out.println("strExtension: "+ strExtension);
			if(FileUtil.imageTypes.contains(strExtension)){
				System.out.println("El archivo es una imagen.");
				resetDataImage(tipoArchivo.getStrRuta()+"\\"+strNombreArchivo,strExtension);
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarArchivoSolicitudPersonal(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_COMPRASINFORMEGERENCIA;
			UploadItem uploadItem = event.getUploadItem();
			java.io.File file = uploadItem.getFile();
			listaSolicitudPersonal = ManejadorExcel.manejarExcelSolicitudPersonal(file);
			
			((SolicitudPersonalController)getSessionBean("solicitudPersonalController")).mostrarListaSolicitudPersonalCarga();			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarArchivoSustento(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUDPAGOPERSONAL;
			
			archivoSustento = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoSustento.getRutaAntigua());
			log.info("rutaNueva:"+archivoSustento.getRutaActual());
			renombrarArchivo(archivoSustento.getRutaAntigua(), archivoSustento.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarArchivoDocumento(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_COMPRASINFORMEGERENCIA;
			
			archivoDocumento = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoDocumento.getRutaAntigua());
			log.info("rutaNueva:"+archivoDocumento.getRutaActual());
			renombrarArchivo(archivoDocumento.getRutaAntigua(), archivoDocumento.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarVoucher(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_VOUCHER;
			
			archivoVoucher = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoVoucher.getRutaAntigua());
			log.info("rutaNueva:"+archivoVoucher.getRutaActual());
			renombrarArchivo(archivoVoucher.getRutaAntigua(), archivoVoucher.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarCartaPoder(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTAPODER;
			
			archivoCartaPoder = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoCartaPoder.getRutaAntigua());
			log.info("rutaNueva:"+archivoCartaPoder.getRutaActual());
			renombrarArchivo(archivoCartaPoder.getRutaAntigua(), archivoCartaPoder.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	public void adjuntarCroquis(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_CROQUIS;
			
			objArchivo = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+objArchivo.getRutaAntigua());
			log.info("rutaNueva:"+objArchivo.getRutaActual());
			renombrarArchivo(objArchivo.getRutaAntigua(), objArchivo.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarFoto(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_FOTOPER;
			
			archivoFoto = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoFoto.getRutaAntigua());
			log.info("rutaNueva:"+archivoFoto.getRutaActual());
			renombrarArchivo(archivoFoto.getRutaAntigua(), archivoFoto.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarFirma(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_FIRMAPER;
			
			archivoFirma = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoFirma.getRutaAntigua());
			log.info("rutaNueva:"+archivoFirma.getRutaActual());
			renombrarArchivo(archivoFirma.getRutaAntigua(), archivoFirma.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public Archivo procesarArchivo(UploadEvent event, Integer intTipoArch) throws Exception{
		log.info("intTipoArchivo:"+intTipoArch);
		String strNombreArchivo = subirArchivo(event, intTipoArch);
		GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
		TipoArchivo tipoArchivo = facade.getTipoArchivoPorPk(intTipoArch);
		
		Archivo archivo = new Archivo();
		archivo.setId(new ArchivoId());
		archivo.getId().setIntParaTipoCod(intTipoArch);
		
		//CGD-12.11.2013
		existeDirectorio(tipoArchivo.getStrRuta()+"\\");
		existeDirectorio(tipoArchivo.getStrRuta()+"\\");
		
		if(intItemArchivo!=null)archivo.getId().setIntItemArchivo(intItemArchivo);
		if(intItemHistorico!=null)archivo.getId().setIntItemHistorico(intItemHistorico);
		archivo.setStrNombrearchivo(strNombreArchivo);
		archivo.setTipoarchivo(tipoArchivo);
		archivo.setIntParaEstadoCod(1);
		archivo = facade.grabarArchivo(archivo);
		
		archivo.setRutaActual(tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
		archivo.setRutaAntigua(tipoArchivo.getStrRuta()+"\\"+strNombreArchivo);
		
		setObjArchivo(archivo);
		log.info("archivo:"+archivo);
		log.info("archivo item:"+archivo.getId().getIntItemArchivo());
		String strExtension = strNombreArchivo.substring(strNombreArchivo.lastIndexOf(".")+1);
		if(FileUtil.imageTypes.contains(strExtension)){
			System.out.println("El archivo es una imagen.");
			//resetDataImage(tipoArchivo.getStrRuta()+"\\"+strNombreArchivo,strExtension);
		}
		return archivo;
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
	
	public static void renombrarArchivo(String strRuta, String nuevoNombre) throws BusinessException{
		try{
			java.io.File oldFile = new java.io.File(strRuta);
			java.io.File newFile = new java.io.File(nuevoNombre);
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
	
	public void resetDataImage(String strPath, String strExtension) throws IOException{
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
	}
	
	public void setParamArchivo(Integer pIntItemArchivo, Integer pIntItemHistorico, Integer pIntTipoArchivo){
		setIntItemArchivo(pIntItemArchivo);
		setIntItemHistorico(pIntItemHistorico);
		setIntTipoArchivo(pIntTipoArchivo);
	}
	
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
	
	//Agregado por cdelosrios, 03/11/2013
	public void adjuntarArchivoDocumentoSunat(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_DOCUMENTOSUNAT;
			
			archivoDocumento = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoDocumento.getRutaAntigua());
			log.info("rutaNueva:"+archivoDocumento.getRutaActual());
			renombrarArchivo(archivoDocumento.getRutaAntigua(), archivoDocumento.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void adjuntarArchivoDocumentoContrato(UploadEvent event){
		try{
			intTipoArchivo = Constante.PARAM_T_TIPOARCHIVOADJUNTO_COMPRASCONTRATO;
			
			archivoDocumento = procesarArchivo(event, intTipoArchivo);
			log.info("rutaAntigua:"+archivoDocumento.getRutaAntigua());
			log.info("rutaNueva:"+archivoDocumento.getRutaActual());
			renombrarArchivo(archivoDocumento.getRutaAntigua(), archivoDocumento.getRutaActual());
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	//Fin agregado por cdelosrios, 03/11/2013
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
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
	public Archivo getArchivoFoto() {
		return archivoFoto;
	}
	public void setArchivoFoto(Archivo archivoFoto) {
		this.archivoFoto = archivoFoto;
	}
	public Archivo getArchivoFirma() {
		return archivoFirma;
	}
	public void setArchivoFirma(Archivo archivoFirma) {
		this.archivoFirma = archivoFirma;
	}
	public Archivo getArchivoCartaPoder() {
		return archivoCartaPoder;
	}
	public void setArchivoCartaPoder(Archivo archivoCartaPoder) {
		this.archivoCartaPoder = archivoCartaPoder;
	}
	public Archivo getArchivoVoucher() {
		return archivoVoucher;
	}
	public void setArchivoVoucher(Archivo archivoVoucher) {
		this.archivoVoucher = archivoVoucher;
	}
	public Archivo getArchivoDocumento() {
		return archivoDocumento;
	}
	public void setArchivoDocumento(Archivo archivoDocumento) {
		this.archivoDocumento = archivoDocumento;
	}
	public Archivo getArchivoSustento() {
		return archivoSustento;
	}
	public void setArchivoSustento(Archivo archivoSustento) {
		this.archivoSustento = archivoSustento;
	}
	public List<SolicitudPersonal> getListaSolicitudPersonal() {
		return listaSolicitudPersonal;
	}
	public void setListaSolicitudPersonal(List<SolicitudPersonal> listaSolicitudPersonal) {
		this.listaSolicitudPersonal = listaSolicitudPersonal;
	}
}