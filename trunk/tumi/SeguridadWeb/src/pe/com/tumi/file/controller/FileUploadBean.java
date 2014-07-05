package pe.com.tumi.file.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.faces.component.UIComponent;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.ConstanteReporte;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.file.domain.File;
import pe.com.tumi.file.service.impl.FileUploadServiceImpl;

public class FileUploadBean extends GenericController {
	private FileUploadServiceImpl fileUploadService;
	private ArrayList<File> files = new ArrayList<File>();
	private int uploadsAvailable = 5;
	private boolean autoUpload = false;
	private String acceptedTypes="jpg, gif, png, bmp, xls, pdf, doc, avi, mp4, exe, txt";
	private String strNombreArchivo;
	private static String strNombreReglamento;
	private static String strNombrePolitica;
	private static String strNombreCartaPresent;
	private static String strNombreConvSugerido;
	private static String strNombreAdendaSugerida;
	
	//Formulario de Beneficiario
	private static String strNombreFotoBeneficiario;
	private static String strNombreFirmaBeneficiario;
	
	//Formulario de Domicilio
	private static String strCroquisDomicilio;

	public FileUploadServiceImpl getFileUploadService() {
		return fileUploadService;
	}

	public void setFileUploadService(FileUploadServiceImpl fileUploadService) {
		this.fileUploadService = fileUploadService;
	}

	private boolean useFlash = false;

	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public FileUploadBean() {
	}

	public void paint(OutputStream stream, Object object) throws IOException {
		stream.write(getFiles().get((Integer) object).getData());
	}

	public void listener(UploadEvent event)throws DaoException {
		log.info("----------------Debugging FileUploadController.listener-----------------");
		setService(fileUploadService);
		log.info("Se ha seteado el Service");
		UploadItem uploadItem = event.getUploadItem();
		 String fileName = uploadItem.getFileName();
		 java.io.File file = uploadItem.getFile();
		 
		 String target = ConstanteReporte.RUTA_UPLOADED + fileName;
		 File f = new File();
		 f.setName(fileName);
		 setFileName(fileName);
		 
		 log.info("target: "+target);
		 try {
			 InputStream in = new FileInputStream(file);
			 OutputStream out = new FileOutputStream(target);
			 try {
			 // Transfer bytes from in to out
			 byte[] buf = new byte[1024*1024*10];//Máximo 10MB
			 int len;
			 while ((len = in.read(buf)) > 0) {
				 out.write(buf, 0, len);
			 }
			 
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 finally {
				 in.close();
				 out.close();
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 UIComponent componente = event.getComponent();
		 String uiName = componente.getId();
		 log.info("componente.getId(): "+componente.getId());
		 if(uiName.equals("uploadReglamento")){
			 setStrNombreReglamento(fileName);
		 }else if(uiName.equals("uploadPolitica")){
			 setStrNombrePolitica(fileName);
		 }else if(uiName.equals("uploadCartaPresent")){
			 setStrNombreCartaPresent(fileName);
		 }else if(uiName.equals("uploadConvSug")){
			 setStrNombreConvSugerido(fileName);
		 }else if(uiName.equals("uploadAdenda")){
			 setStrNombreAdendaSugerida(fileName);
		 }
	}

	public void upldPhoto(UploadEvent event)throws DaoException {
		log.info("----------------Debugging FileUploadController.upldPhoto-----------------");
		setService(fileUploadService);
		log.info("Se ha seteado el Service");
		UploadItem uploadItem = event.getUploadItem();
		 String fileName = uploadItem.getFileName();
		 java.io.File file = uploadItem.getFile();
		 
		 String RUTA_IMG = getServletContext().getRealPath("") + "images\\photographs\\";
		 String RUTA_FOTOS = RUTA_IMG.replace("\\", "/");
		 String target = RUTA_FOTOS + fileName;
		 File f = new File();
		 f.setName(fileName);
		 setFileName(fileName);
		 
		 log.info("target: "+target);
		 try {
			 InputStream in = new FileInputStream(file);
			 OutputStream out = new FileOutputStream(target);
			 try {
			 // Transfer bytes from in to out
			 byte[] buf = new byte[1024*1024*10];//Máximo 10MB
			 int len;
			 while ((len = in.read(buf)) > 0) {
				 out.write(buf, 0, len);
			 }
			 
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 finally {
				 in.close();
				 out.close();
			 }
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 UIComponent componente = event.getComponent();
		 String uiName = componente.getId();
		 log.info("componente.getId(): "+componente.getId());
		 if(uiName.equals("upldFotoBenef")){
			 setStrNombreFotoBeneficiario(fileName);
		 }else if(uiName.equals("upldFirmaBenef")){
			 setStrNombreFirmaBeneficiario(fileName);
		 }else if(uiName.equals("upldCroquisDomicilio")){
			 setStrCroquisDomicilio(fileName);
		 }
	}
	
	/*
	 * public void listener(UploadEvent event) throws Exception{ UploadItem item
	 * = event.getUploadItem(); AdmFormDoc file = new AdmFormDoc();
	 * file.setLength(item.getData().length); file.setName(item.getFileName());
	 * file.setData(item.getData()); files.add(file); uploadsAvailable--; }
	 */

	public String clearUploadData() {
		files.clear();
		setUploadsAvailable(5);
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public ArrayList<File> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<File> files) {
		this.files = files;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public String getAcceptedTypes() {
		return acceptedTypes;
	}

	public void setAcceptedTypes(String acceptedTypes) {
		this.acceptedTypes = acceptedTypes;
	}

	public String getStrNombreArchivo() {
		return strNombreArchivo;
	}

	public void setStrNombreArchivo(String strNombreArchivo) {
		this.strNombreArchivo = strNombreArchivo;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

	public static String getStrNombreReglamento() {
		return strNombreReglamento;
	}

	public static void setStrNombreReglamento(String strNombreReglamento) {
		FileUploadBean.strNombreReglamento = strNombreReglamento;
	}

	public static String getStrNombrePolitica() {
		return strNombrePolitica;
	}

	public static void setStrNombrePolitica(String strNombrePolitica) {
		FileUploadBean.strNombrePolitica = strNombrePolitica;
	}

	public static String getStrNombreCartaPresent() {
		return strNombreCartaPresent;
	}

	public static void setStrNombreCartaPresent(String strNombreCartaPresent) {
		FileUploadBean.strNombreCartaPresent = strNombreCartaPresent;
	}

	public static String getStrNombreConvSugerido() {
		return strNombreConvSugerido;
	}

	public static void setStrNombreConvSugerido(String strNombreConvSugerido) {
		FileUploadBean.strNombreConvSugerido = strNombreConvSugerido;
	}

	public static String getStrNombreAdendaSugerida() {
		return strNombreAdendaSugerida;
	}

	public static void setStrNombreAdendaSugerida(String strNombreAdendaSugerida) {
		FileUploadBean.strNombreAdendaSugerida = strNombreAdendaSugerida;
	}

	public static String getStrNombreFotoBeneficiario() {
		return strNombreFotoBeneficiario;
	}

	public static void setStrNombreFotoBeneficiario(String strNombreFotoBeneficiario) {
		FileUploadBean.strNombreFotoBeneficiario = strNombreFotoBeneficiario;
	}

	public static String getStrNombreFirmaBeneficiario() {
		return strNombreFirmaBeneficiario;
	}

	public static void setStrNombreFirmaBeneficiario(
			String strNombreFirmaBeneficiario) {
		FileUploadBean.strNombreFirmaBeneficiario = strNombreFirmaBeneficiario;
	}

	public static String getStrCroquisDomicilio() {
		return strCroquisDomicilio;
	}

	public static void setStrCroquisDomicilio(String strCroquisDomicilio) {
		FileUploadBean.strCroquisDomicilio = strCroquisDomicilio;
	}

}
