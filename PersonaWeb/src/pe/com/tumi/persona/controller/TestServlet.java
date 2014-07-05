package pe.com.tumi.persona.controller;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeLocal;

public class TestServlet extends HttpServlet {
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
		//insertaPersona(out);
		//insertaDireccion(out);
		obtenerPersonaNaturalYSusEmpresas(out);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
	
	public static void obtenerPersonaNaturalYSusEmpresas(PrintWriter out){
		PersonaEmpresaPK pk = new PersonaEmpresaPK();
		pk.setIntIdEmpresa(2);
		pk.setIntIdPersona(96);
		Integer pIntTipoVinculo = 6; //Tiene empresa
		Persona o = null;
		try {
			PersonaFacadeLocal local = (PersonaFacadeLocal)EJBFactory.getLocal(PersonaFacadeLocal.class);
			o = local.getPersonaNaturalPorPersonaEmpresaPKYTipoVinculo(pk, pIntTipoVinculo);
			out.println("Se obtuvo persona con id: "+o.getIntIdPersona());
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}
	/*public static void insertaDireccion(PrintWriter out){
		try {
			Domicilio o = new Domicilio();
			DomicilioPK pk = new DomicilioPK();
			pk.setIntIdPersona(1);
			o.setId(pk);
			o.setIntTipoDomicilioCod(1);
			o.setIntTipoDireccionCod(1);
			o.setIntTipoViviendaCod(1);
			o.setIntTipoViaCod(1);
			o.setIntTipoZonaCod(1);
			o.setStrNombreVia("ali");
			o.setIntEnviaCorrespondencia(1);
			o.setIntEstadoCod(1);
			PersonaFacadeLocal local = (PersonaFacadeLocal)EJBFactory.getLocal(PersonaFacadeLocal.class);
			o = local.grabarDomicilio(o);
			out.println("IntIdPersona: "+o.getId().getIntIdPersona() + " IntIdDomicilio: "+o.getId().getIntIdDomicilio());
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}*/
	/*
	public static void insertaPersona(PrintWriter out){
		try {
			Persona o = new Persona();
			o.setIntTipoPersonaCod(1);
			o.setStrRuc("0088");
			o.setDtFechaBajaRuc(null);
			o.setIntEstadoCod(1);
			PersonaFacadeLocal local = (PersonaFacadeLocal)EJBFactory.getLocal(PersonaFacadeLocal.class);
			o = local.grabarPersona(o);
			out.println("IntIdPersona: "+o.getIntIdPersona() + " IntTipoPersonaCod: "+o.getIntTipoPersonaCod());
			out.println("StrRuc: "+o.getStrRuc() + " DtFechaBajaRuc: "+o.getDtFechaBajaRuc());
			out.println("IntEstadoCod: "+o.getIntEstadoCod());
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}*/
	
	/*public static void insertNatural(PrintWriter out){
		try {
			Natural o = new Natural();
			o.setStrApellidoPaterno("");
			o.setStrApellidoMaterno("");
			o.setStrNombres("");
			o.setDtFechaNacimiento(null);
			o.setIntTipoIdentidadCod(null);
			o.setStrNroIdentidad("");
			o.setIntSexoCod(null);
			o.setIntEstadoCivilCod(null);
			o.setIntTieneEmpresa(null);
			o.setStrFoto(null);
			o.setStrFirma(null);
			
			Persona per = new Persona();
			per.setIntTipoPersonaCod(1);
			per.setIntEstadoCod(1);
			//per.setIntIdPersona(null);
			o.setPersona(per);
			PersonaFacadeLocal local = (PersonaFacadeLocal)EJBFactory.getLocal(PersonaFacadeLocal.class);
			o = local.grabarPersonaNatural(o);
			out.println(" ApellidoPaterno: 	 " 	+o.getStrApellidoPaterno() + "<br/>" + 
						" ApellidoMaterno: " 	+o.getStrApellidoMaterno() + "<br/>" +
						" Nombres: " 			+o.getStrNombres());
			out.println("---------------------------------------" + "<br/>");
		} catch (BusinessException e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("no se grabo correctamente");
			e.printStackTrace();
		}
	}*/
}
