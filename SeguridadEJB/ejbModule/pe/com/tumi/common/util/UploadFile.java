package pe.com.tumi.common.util;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.*;
import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

import pe.com.tumi.common.controller.GenericController;

import java.util.*;

public class UploadFile extends HttpServlet {

	protected static Logger log = Logger.getLogger(UploadFile.class);
	private static final long serialVersionUID = 5913209936132568370L;
	private pe.com.tumi.common.util.File myFile;
	private int flag;
	
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        procesaFicheros(request);
    }

    public void procesaFicheros(HttpServletRequest req) {
        try {
            // construimos el objeto que es capaz de parsear la perición
            DiskFileUpload fu = new DiskFileUpload();

            // maximo numero de bytes
            fu.setSizeMax(1024*512); // 512 K
            fu.setSizeThreshold(4096);
            List fileItems = fu.parseRequest(req);

            if(fileItems == null){
                return;
            }
            // Iteramos por cada fichero

            Iterator i = fileItems.iterator();
            FileItem actual = null;
            myFile = new pe.com.tumi.common.util.File();
            while (i.hasNext()){
               actual = (FileItem)i.next();
                myFile.setName(actual.getName());
                myFile.setData(actual.get());
                flag = 3;
            }
        }
        catch(Exception e) {
        	log.error(e);
            return;
        }
        return;
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    	PrintWriter out = response.getWriter();
    	if(myFile == null || myFile.getName() == null || myFile.getName().equals("")) {
    		out.println("<p style='font-size: 11px; color:red; font-family: Tahoma,Verdana,Arial,Helvetica,sans-serif;'>Debe subir un archivo</p>");
    	}else if(myFile != null ) out.println("<p style='font-size: 11px; color:blue; font-family: Tahoma,Verdana,Arial,Helvetica,sans-serif;'>Se ha subido el archivo " + myFile.getName() + "</p>");
        WebUtils.setSessionAttribute(request, "file", myFile);
    }

	public void setMyFile(pe.com.tumi.common.util.File myFile) {
		this.myFile = myFile;
	}

	public pe.com.tumi.common.util.File getMyFile() {
		return myFile;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}