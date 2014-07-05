package pe.com.tumi.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

import org.apache.log4j.Logger;

public class FileHelper{
	
	protected static Logger log = Logger.getLogger(FileHelper.class);
	public static Reader readerHelp(String rutaArchivo){
		FileReader fileReader = null;
		try {
			File file = new File(rutaArchivo);
	        fileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			log.debug(e);
		}
		return fileReader;
		
	}
	
	public static void writeFile(String fileName, String data){
		File file = new File(fileName);
		FileWriter fichero = null;
		PrintWriter pw = null;
		try {
			fichero = new FileWriter(file);
			pw = new PrintWriter(fichero);
			pw.println(data);
		} catch (Exception e) {
			log.error("Error: "+e.getMessage());
		} finally {
			if (null != fichero)
				try {
					fichero.close();
					log.debug("Se escribio el archivo: " + fileName);
				} catch (IOException e) {
					log.error("Error: "+e.getMessage());
				}
		}
	}

	public static void deleteFile(String filename){
		File file = new File(filename);
		if (file.exists()){
			if (file.canWrite()){
				if (file.delete()){
					log.debug("se elimino el archivo: " + filename + " con exito");
				}else{
					log.debug("No se puede eliminar el archivo: " + filename );
				}
			}else{
				log.debug("No se tienen permisos de escritura para el archivo: " + filename);
			}
		}
	}
	
	public static byte[] getBytesFromFile(File file) throws IOException {
		if(!fileExists(file)){
			return null;
		}
		
		InputStream is = new FileInputStream(file);
		// Get the size of the file
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			
		}  
		byte[] bytes = new byte[(int)length];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
			offset += numRead;
		} 
		if (offset < bytes.length) {
			throw new IOException("No se pudo completar la lectura del archivo "+ file.getName());
		} // Close the input stream and return bytes
		is.close();
		return bytes;
	} 
	
	public static boolean fileExists(File file){
		if(file == null){
			return false;
		}else{
			if(!file.exists()){
				return false;
			}
		}
		return true;
	}
		
}
