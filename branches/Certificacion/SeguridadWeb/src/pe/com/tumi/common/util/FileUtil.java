package pe.com.tumi.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

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
	private String acceptedTypes="jpg, gif, png, bmp, xls, pdf, doc, avi, mp4, exe, txt";
	private String strNombreArchivo;
	private boolean useFlash = false;
	static TipoArchivo tipoArchivo = null;
	
	public static String subirArchivo(UploadEvent event, Integer intTipoArchivo) throws BusinessException{
		UploadItem uploadItem = event.getUploadItem();
		java.io.File file = uploadItem.getFile();
		
		try {
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			tipoArchivo = generalFacade.getTipoArchivoPorPk(intTipoArchivo);
		} catch (BusinessException e) {
			System.out.println("error: "+ e);
			throw e;
		} catch (EJBFactoryException e) {
			System.out.println("error: "+ e);
			throw new BusinessException(e);
		}
		String fileName = tipoArchivo.getStrPrefijo()+"-"+uploadItem.getFileName();
		String target = tipoArchivo.getStrRuta() + "\\" +fileName;
		try {
			existeDirectorio(tipoArchivo.getStrRuta());
			
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
		return fileName;
	}
	
	public static void renombrarArchivo(String strRuta, String nuevoNombre) throws BusinessException{
		try{
			java.io.File oldFile = new java.io.File(strRuta);
			java.io.File newFile = new java.io.File(nuevoNombre);
			oldFile.renameTo(newFile);
		}catch(Exception e){
			System.out.println("El renombrado no se ha podido realizar: " + e);
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
			e.printStackTrace();
		}
	}
	
	/**
	 * Recupera imagen...
	 * @param strPath
	 * @return
	 * @throws IOException
	 */
	public static byte[] getDataImage(String strPath) throws IOException{
		String strExtension = strPath.substring(strPath.lastIndexOf(".")+1);
		java.io.File iofile = new java.io.File(strPath);
		BufferedImage image = ImageIO.read(iofile);
		// O P E N
		ByteArrayOutputStream baos = new ByteArrayOutputStream((int)iofile.length());
		// W R I T E
		ImageIO.write(image,strExtension.trim(),baos);
		// C L O S E
		baos.flush();
		byte[] byteImg = baos.toByteArray();
		baos.close();
		return byteImg;
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

}
