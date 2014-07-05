package pe.com.tumi.credito.socio.testing.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import pe.com.tumi.credito.socio.captacion.bo.CaptacionBO;
import pe.com.tumi.credito.socio.captacion.dao.CaptacionDao;
import pe.com.tumi.credito.socio.captacion.dao.impl.CaptacionDaoIbatis;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.credito.socio.captacion.domain.VinculoId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacade;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeLocal;
import pe.com.tumi.credito.socio.captacion.facade.CondicionFacadeLocal;
import pe.com.tumi.credito.socio.captacion.facade.VinculoFacadeLocal;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Logger log = Logger.getLogger(Test.class);

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
		
		this.insertaConvenio3(out);
		log.info("Estoy en el servlet");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	public static void insertaConvenio2(PrintWriter out){
		try {
			
			CaptacionFacadeLocal local = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			
			CaptacionId oid = new CaptacionId();
			oid.setIntPersEmpresaPk(163);
			oid.setIntParaTipoCaptacionCod(1);
			oid.setIntItem(1);
			
			List<Captacion> lista=local.listarCaptacion(oid);
			//List<Captacion> lista=local.listarCaptacion();
			out.println("-------lista--------------------------------" + lista+"<br/>");
			out.println("-------lista--size()------------------------" + lista.size()+"<br/>");
			
			for(Captacion oi:lista){				
				out.println(oi.getId().getIntPersEmpresaPk()+"<BR/>");
				out.println(oi.getDtFechaRegistro()+" "+oi.getStrDescripcion()+"<BR/>");
			}
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	
	

	

	public static void insertaConvenio3(PrintWriter out){
		try {
			
			
			CondicionFacadeLocal socio = (CondicionFacadeLocal)EJBFactory.getLocal(CondicionFacadeLocal.class);
			CaptacionId oid = new CaptacionId();
			oid.setIntPersEmpresaPk(163);
			oid.setIntParaTipoCaptacionCod(1);
			oid.setIntItem(12);			
			List<Condicion> lista=socio.listarCondicion(oid);
						
			/*
			
			VinculoFacadeLocal local = (VinculoFacadeLocal)EJBFactory.getLocal(VinculoFacadeLocal.class);
			
			VinculoId oid = new VinculoId();
			oid.setIntEmpresaPk(1);
			oid.setParaTipoCaptacionCod(1);
			oid.setIntItem(1);
			oid.setParaTipoVinculoCod(1);
			oid.setIntItemVinculo(1);
			
			List<Vinculo> lista=local.listarVinculo(oid);
			//List<Captacion> lista=local.listarCaptacion();*/
			out.println("-------lista--------------------------------" + lista+"<br/>");
			out.println("-------lista--ssize()------------------------" + lista.size()+"<br/>");
			
			for(Condicion oi:lista){				
				out.println(oi.getId().getIntPersEmpresaPk()+"<BR/>");
				out.println(oi.getId().getIntItem()+"<BR/>");
				out.println(oi.getId().getIntParaTipoCaptacionCod()+"<BR/>");
				out.println(oi.getId().getIntParaCondicionSocioCod()+"<BR/>");
				
				//out.println(oi.getCaptacion()+" "+oi.getIntCuotaCancelada()+"<BR/>");
			}
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	public static void insertaConvenio4(PrintWriter out){
		try {
			
			VinculoFacadeLocal local = (VinculoFacadeLocal)EJBFactory.getLocal(VinculoFacadeLocal.class);
			
			VinculoId oid = new VinculoId();
			oid.setIntEmpresaPk(163);
			oid.setParaTipoCaptacionCod(1);
			oid.setIntItem(1);
			oid.setParaTipoVinculoCod(1);
			oid.setIntItemVinculo(2);
			
			
			Vinculo v=new Vinculo();
			v.setId(oid);
			local.grabarVinculo(v);
			//List<Captacion> lista=local.listarCaptacion();
			out.println("-------se grabó el vínculo-------------" + v+"<br/>");
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	public static void insertaConvenioXXX(PrintWriter out){
		//private CaptacionDao dao = (CaptacionDao)TumiFactory.get(CaptacionDaoIbatis.class);
		try {
			
			CaptacionId o = new CaptacionId();
			o.setIntPersEmpresaPk(1);
			o.setIntParaTipoCaptacionCod(1);
			o.setIntItem(1);
			
			/*Captacion capt=new Captacion();
			capt.setId(o);*/
			
			CaptacionFacadeLocal local = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			
			//List<CaptacionId> lista=local.listarAportaciones(o);//tarCaptacion();
			
			

		/*} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();*/
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	public static void listarCaptacionPorPK(PrintWriter out){
		try {			
			CaptacionFacadeLocal local = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			
			//o = local.grabarConvenio(o);
			CaptacionId oid = new CaptacionId();
			oid.setIntPersEmpresaPk(163);
			oid.setIntParaTipoCaptacionCod(1);
			oid.setIntItem(1);
			
			Captacion o=new Captacion();
			o.setId(oid);
			
			Captacion lista=new Captacion();
			lista=local.listarCaptacionPorPK(o.getId());
		
			out.println("LISTA: "+lista+"<BR/>");

			if(o != null){
				out.println("IntIdPersona: "+o.getStrDescripcion()+"<BR/>");
				out.println("IntIdPersona: "+o.getDtInicio()+"<BR/>");
				out.println("IntIdPersona: "+o.getDtFin()+"<BR/>");				
			}else{
				out.println("no hay datos del convenio: ");
			}
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}	
	
	public static void listaPorPK(PrintWriter out){
		try {
			
			
			CaptacionFacadeLocal local = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			CaptacionId oid = new CaptacionId();
			oid.setIntPersEmpresaPk(163);
			oid.setIntParaTipoCaptacionCod(1);
			oid.setIntItem(1);			
			List<Captacion> lista=local.listarCaptacion(oid);
						
			/*
			
			VinculoFacadeLocal local = (VinculoFacadeLocal)EJBFactory.getLocal(VinculoFacadeLocal.class);
			
			VinculoId oid = new VinculoId();
			oid.setIntEmpresaPk(1);
			oid.setParaTipoCaptacionCod(1);
			oid.setIntItem(1);
			oid.setParaTipoVinculoCod(1);
			oid.setIntItemVinculo(1);
			
			List<Vinculo> lista=local.listarVinculo(oid);
			//List<Captacion> lista=local.listarCaptacion();*/
			out.println("-------lista--------------------------------" + lista+"<br/>");
			out.println("-------lista--size()------------------------" + lista.size()+"<br/>");
			
			for(Captacion oi:lista){				
				out.println(oi.getId().getIntPersEmpresaPk()+"<BR/>");
				out.println(oi.getId().getIntItem()+"<BR/>");
				out.println(oi.getId().getIntParaTipoCaptacionCod()+"<BR/>");
				out.println(oi.getStrDescripcion()+"<BR/>");
				
				//out.println(oi.getCaptacion()+" "+oi.getIntCuotaCancelada()+"<BR/>");
			}
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	
	public static void eliminarylistaPorPK(PrintWriter out){
		try {
			
			
			CaptacionFacadeLocal local = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			CaptacionId oid = new CaptacionId();
			oid.setIntPersEmpresaPk(163);
			oid.setIntParaTipoCaptacionCod(1);
			oid.setIntItem(296);
			
			Captacion o=new Captacion();
			o.setId(oid);
			//local.eliminarCaptacion(o);
			List<Captacion> lista=local.listarCaptacion(oid);
						
			/*
			
			VinculoFacadeLocal local = (VinculoFacadeLocal)EJBFactory.getLocal(VinculoFacadeLocal.class);
			
			VinculoId oid = new VinculoId();
			oid.setIntEmpresaPk(1);
			oid.setParaTipoCaptacionCod(1);
			oid.setIntItem(1);
			oid.setParaTipoVinculoCod(1);
			oid.setIntItemVinculo(1);
			
			List<Vinculo> lista=local.listarVinculo(oid);
			//List<Captacion> lista=local.listarCaptacion();*/
			out.println("-------eliminar y listar--------------------------------" + lista+"<br/>");
			out.println("-------lista--size()------------------------" + lista.size()+"<br/>");
			
			for(Captacion oi:lista){				
				out.println(oi.getId().getIntPersEmpresaPk()+"<BR/>");
				out.println(oi.getId().getIntItem()+"<BR/>");
				out.println(oi.getId().getIntParaTipoCaptacionCod()+"<BR/>");
				out.println(oi.getStrDescripcion()+"<BR/>");
				
				//out.println(oi.getCaptacion()+" "+oi.getIntCuotaCancelada()+"<BR/>");
			}
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}	

}
