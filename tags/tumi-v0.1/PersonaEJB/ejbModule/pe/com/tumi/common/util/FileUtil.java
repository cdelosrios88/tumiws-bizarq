package pe.com.tumi.common.util;

import pe.com.tumi.framework.negocio.exception.BusinessException;

public class FileUtil {
	
	public static void renombrarArchivo(String oldName, String nuevoNombre) throws BusinessException{
		try{
			System.out.println("oldName: " + oldName);
			System.out.println("newName: " + nuevoNombre);
			java.io.File oldFile = new java.io.File(oldName);
			java.io.File newFile = new java.io.File(nuevoNombre);
			oldFile.renameTo(newFile);
		}catch(Exception e){
			System.out.println("El renombrado no se ha podido realizar: " + e);
			throw new BusinessException(e);
		}
	}
}
