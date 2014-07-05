package pe.com.tumi.common;

import java.io.FileInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DownloadFile {
	
	protected  static Logger 	log 			= Logger.getLogger(DownloadFile.class);
	
	public static String downloadFile(String fileName) {
	    java.io.File file = new java.io.File(fileName);
	    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();  

	    writeOutContent(response, file, file.getName());

	    FacesContext.getCurrentInstance().responseComplete();
	    return null;
	}

	private static void writeOutContent(final HttpServletResponse res, final java.io.File content, final String theFilename) {
		if (content == null) {
	        return;
	    }
	    try {
	        res.setHeader("Pragma", "no-cache");
	        res.setDateHeader("Expires", 0);
	        res.setHeader("Content-disposition", "attachment; filename=" + theFilename);
	        FileInputStream fis = new FileInputStream(content);
	        ServletOutputStream os = res.getOutputStream();
	        int bt = fis.read();
	        while (bt != -1) {
	            os.write(bt);
	            bt = fis.read();
	        }
	        os.flush();
	        fis.close();
	        os.close();
	    } catch (final IOException ex) {
	    	log.error("error: " + ex);
	    }
	}
}
