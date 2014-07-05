package pe.com.tumi.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import pe.com.tumi.file.domain.File;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;

public class FileUtil {
	private ArrayList<File> files = new ArrayList<File>();
	private int uploadsAvailable = 5;
	private boolean autoUpload = false;
	private String acceptedTypes="jpg, gif, png, bmp, xls, pdf, doc, docx, avi, mp4, exe, txt, dbf";
	public static final String allDocumentTypes="pdf,doc,docx,sdw,xls,xlsx,sdc,jpg,png,gif";
	public static final String imageTypes="jpg,gif,png";
	private String dataTypes="xls,pdf,txt,dbf,sdc";
	private String mediaTYpes="avi,mp4";
	private String strNombreArchivo;
	private boolean useFlash = false;
	static TipoArchivo tipoArchivo = null;
	protected static Logger log = Logger.getLogger(FileUtil.class);

	
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
	
	
	/**
	 * Verifica si existe el directorio, si no existe lo crea.
	 * @param strRuta
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
	
	//Getters y Setters

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

	public static TipoArchivo getTipoArchivo() {
		return tipoArchivo;
	}

	public static void setTipoArchivo(TipoArchivo tipoArchivo) {
		FileUtil.tipoArchivo = tipoArchivo;
	}

	public String getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(String dataTypes) {
		this.dataTypes = dataTypes;
	}

	public String getMediaTYpes() {
		return mediaTYpes;
	}

	public void setMediaTYpes(String mediaTYpes) {
		this.mediaTYpes = mediaTYpes;
	}
}
