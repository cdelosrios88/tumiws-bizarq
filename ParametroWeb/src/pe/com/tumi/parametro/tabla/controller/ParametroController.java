package pe.com.tumi.parametro.tabla.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeLocal;

/**
 * Servlet implementation class ParametroController
 */
public class ParametroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		try {
			grabarArchivo(out);
			//getArchivoPorTipoArchivoYStrCampos(out);
			//getTipoArchivoPorPk(out);
			System.out.println("se grabo correctamente");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	public void getTipoArchivoPorPk(PrintWriter out) throws BusinessException,EJBFactoryException{
		GeneralFacadeLocal local = null;
		try{
			local = (GeneralFacadeLocal)EJBFactory.getLocal(GeneralFacadeLocal.class);
			TipoArchivo o = local.getTipoArchivoPorPk(new Integer(1));
			imprimirTipoArchivo(out,o);
			out.println("---------------------------------------" + "<br/>");
		}catch(BusinessException e){
			throw e;
		} catch (EJBFactoryException e) {
			throw e;
		}
	}
	
	private void imprimirTipoArchivo(PrintWriter out,TipoArchivo o){
		out.println("intParaTipoCod: "+o.getIntParaTipoCod() + " descripcion: "+o.getStrDescripcion());
		out.println("<br>");
		out.println("StrRuta: "+o.getStrRuta() + " IntParaGrupoArchivoCod: "+o.getIntParaGrupoArchivoCod());
		out.println("<br>");
		out.println("StrPrefijo: "+o.getStrPrefijo());
	}
	
	public void grabarArchivoInicial(PrintWriter out) throws BusinessException,EJBFactoryException{
		GeneralFacadeLocal local = null;
		try{
			Archivo o = new Archivo();
			o.setId(new ArchivoId());
			o.getId().setIntParaTipoCod(1);
			o.setStrNombrearchivo("Nombre.jpg");
			o.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			local = (GeneralFacadeLocal)EJBFactory.getLocal(GeneralFacadeLocal.class);
			o = local.grabarArchivo(o);
			imprimirArchivo(out,o);
			out.println("---------------------------------------" + "<br/>");
		}catch(BusinessException e){
			throw e;
		} catch (EJBFactoryException e) {
			throw e;
		}
	}

	public void grabarArchivo(PrintWriter out) throws BusinessException,EJBFactoryException{
		GeneralFacadeLocal local = null;
		try{
			Archivo o = new Archivo();
			o.setId(new ArchivoId());
			o.getId().setIntParaTipoCod(1);
			o.getId().setIntItemArchivo(21);
			o.getId().setIntItemHistorico(1);
			o.setStrNombrearchivo("Nombre.jpg");
			o.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			local = (GeneralFacadeLocal)EJBFactory.getLocal(GeneralFacadeLocal.class);
			o = local.grabarArchivo(o);
			imprimirArchivo(out,o);
			out.println("---------------------------------------" + "<br/>");
		}catch(BusinessException e){
			throw e;
		} catch (EJBFactoryException e) {
			throw e;
		}
	}
	
	private void imprimirArchivo(PrintWriter out,Archivo o){
		out.println("IntParaTipoCod: "+o.getId().getIntParaTipoCod());
		out.println("<br>");
		out.println("IntItemHistorico: "+o.getId().getIntItemHistorico() + " IntItemArchivo: "+o.getId().getIntItemArchivo());
		out.println("<br>");
		out.println("Nombrearchivo: "+o.getStrNombrearchivo() + " FechaRegistro: "+o.getTsFechaRegistro());
		out.println("<br>");
		out.println("IntParaEstadoCod: " + o.getIntParaEstadoCod() + " FechaEliminacion: " + o.getTsFechaEliminacion());
	}
	
}
