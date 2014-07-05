package pe.com.tumi.fileupload;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.richfaces.event.UploadEvent;

import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;

public class FileUploadController {

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
	private String fileType = null;
	private byte[] dataImage;
	
	public FileUploadController(){
		strCloseIconPath = "/images/icons/remove_20.png";
		strTitle = "Subir archivos";
		strDescripcion = "Seleccionar archivo";
		strJsFunction = "putFile(),putFileBeneficiario()";
	}
	
	public void adjuntarArchivo(UploadEvent event) throws BusinessException, EJBFactoryException, IOException{
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
	
	//Agregado por cdelosrios, 08/12/2013
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
			
			//String realPath = sCon.getRealPath(strRutaActual+"/" + strNombreArchivo);
			//String realPath = "C:\\Tumi\\ArchivosAdjuntos\\Documentos\\ExpedientePrestamos\\CopiaDNI"+"\\" + strNombreArchivo;
			String realPath = ruta;
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
		}catch(Exception e){
			throw e;
		}
	}
	//Fin agregado cdelosrios, 08/12/2013
	
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
	
}
