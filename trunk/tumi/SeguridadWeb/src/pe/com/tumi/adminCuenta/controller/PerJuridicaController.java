package pe.com.tumi.adminCuenta.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

import pe.com.tumi.adminCuenta.domain.ActividadEconomica;
import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.adminCuenta.service.impl.AdminCuentaServiceImpl;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.RepresentanteLegal;
import pe.com.tumi.popup.controller.ComunicacionController;
import pe.com.tumi.popup.controller.CtaBancariaController;
import pe.com.tumi.popup.controller.DomicilioController;
import pe.com.tumi.popup.controller.RepLegalController;
import pe.com.tumi.popup.domain.Comunicacion;
import pe.com.tumi.popup.domain.CuentaBancaria;
import pe.com.tumi.popup.domain.Domicilio;
import pe.com.tumi.popup.service.impl.ComunicacionServiceImpl;
import pe.com.tumi.popup.service.impl.CtaBancariaServiceImpl;
import pe.com.tumi.popup.service.impl.DomicilioServiceImpl;
import pe.com.tumi.popup.service.impl.RepLegalServiceImpl;

public class PerJuridicaController extends GenericController{

	private 	AdminCuentaServiceImpl 		perJuridicaService;
	private 	PersonaJuridica				perJuridica = new PersonaJuridica();
	private 	PersonaJuridica 			beanPerJuridicaN1 = new PersonaJuridica();
	private 	PersonaJuridica 			beanPerJuridicaN2 = new PersonaJuridica();
	private 	PersonaJuridica 			beanPerJuridicaN3 = new PersonaJuridica();
	private 	ActividadEconomica			actEconomica = new ActividadEconomica();
	private 	Integer						intCboTipoRelPerJuri = 1;
	private 	Integer 					intCboTiposPersona = 2;
	private 	Integer 					intCboTiposDocumento = 4;
	private 	Integer						intCboEstados;
	private 	Integer 					intCboCondiContri;
	private 	Integer 					intCboTipoContri;
	private 	Integer 					intCboTipoContabilidad;
	private 	Integer 					intCboTipoEmisionComprobante;
	private 	Integer 					intCboComercioExterior;
	private 	Long						intTxtRuc;
	private 	String 						strIdBtnAgregarPerJuri;
	private 	String 						idModalPanel = "pAgregarPerJuri";
	private 	CtaBancariaController		ctaBancariaController = (CtaBancariaController) getSpringBean("ctaBancariaController");
	private 	DomicilioController			domicilioController = (DomicilioController) getSpringBean("domicilioController");
	private 	ComunicacionController  	comunicacionController = (ComunicacionController) getSpringBean("comunicacionController");
	private 	RepLegalController			repLegalController = (RepLegalController) getSpringBean("repLegalController");
	private 	ComunicacionServiceImpl		comunicacionService;
	private 	DomicilioServiceImpl		domicilioService;
	private 	CtaBancariaServiceImpl		ctaBancariaService;
	private 	RepLegalServiceImpl			repLegalService;
	
	
	public void grabarPerJuridica(ActionEvent event) throws DaoException{
		log.info("-------------------------------------Debugging grabarPerJuridica-------------------------------------");
		
		log.info("intCboTiposPersona: "+getIntCboTiposPersona());
		log.info("dtFechaInscripcion: "+getPerJuridica().getDtFechaInscripcion());
		Date dtFechaInscripcion = getPerJuridica().getDtFechaInscripcion();
		String strFechaInscripcion = dtFechaInscripcion!=null?sdfshort.format(dtFechaInscripcion):null;
		log.info("dtFechaInicioActi: "+getPerJuridica().getDtFechaInicioActi());
		Date dtFechaInicioActi = getPerJuridica().getDtFechaInscripcion();
		String strFechaInicioActi = dtFechaInicioActi!=null?sdfshort.format(dtFechaInicioActi):null;
		
		PersonaJuridica juri = new PersonaJuridica();
		juri.setIntIdPersona(getPerJuridica().getIntIdPersona());
		log.info("juri.getIntIdPersona(): "+juri.getIntIdPersona());
		juri.setStrRazonSocial(getPerJuridica().getStrRazonSocial());
		log.info("juri.getStrRazonSocial(): "+juri.getStrRazonSocial());
		juri.setStrNombreComercial(getPerJuridica().getStrNombreComercial());
		juri.setStrSiglas(getPerJuridica().getStrSiglas());
		juri.setIntRuc(getPerJuridica().getIntRuc());
		juri.setIntNumTrabajadores(getPerJuridica().getIntNumTrabajadores());
		
		log.info("intEstadoContribuyente: "+getPerJuridica().getIntEstadoContribuyente());
		juri.setIntEstadoContribuyente(getPerJuridica().getIntEstadoContribuyente().equals(0)?null:getPerJuridica().getIntEstadoContribuyente());
		log.info("intCondContribuyente: "+getPerJuridica().getIntCondContribuyente());
		juri.setIntCondContribuyente(getPerJuridica().getIntCondContribuyente().equals(0)?null:getPerJuridica().getIntCondContribuyente());
		log.info("intTipoContribuyente: "+getPerJuridica().getIntTipoContribuyente());
		juri.setIntTipoContribuyente(getPerJuridica().getIntTipoContribuyente().equals(0)?null:getPerJuridica().getIntTipoContribuyente());
		log.info("intSistemaContable: "+getPerJuridica().getIntSistemaContable());
		juri.setIntSistemaContable(getPerJuridica().getIntSistemaContable().equals(0)?null:getPerJuridica().getIntSistemaContable());
		log.info("intEmisionComprobante: "+getPerJuridica().getIntEmisionComprobante());
		juri.setIntEmisionComprobante(getPerJuridica().getIntEmisionComprobante().equals(0)?null:getPerJuridica().getIntEmisionComprobante());
		log.info("intComercioExterior: "+getPerJuridica().getIntComercioExterior());
		juri.setIntComercioExterior(getPerJuridica().getIntComercioExterior().equals(0)?null:getPerJuridica().getIntComercioExterior());
		
		juri.setIntTipoPersona(getIntCboTiposPersona());
		juri.setStrFechaInscripcion(strFechaInscripcion);
		juri.setStrFechaInicioActi(strFechaInicioActi);
		juri.setIntEstado(1);
		
		setAperCuentaService(perJuridicaService);
		getAperCuentaService().grabarPerJuridica(juri);
		
		//Seteando lista de Ctas Bancarias de Persona Juridica
		setCtaBancariaService(ctaBancariaService);
		ArrayList arrayCtas = (ArrayList) ctaBancariaController.getBeanListCtaBancaria();
		log.info("arrayCtas.size: "+arrayCtas.size());
		for(int i=0; i<arrayCtas.size(); i++){
			CuentaBancaria cta = (CuentaBancaria) arrayCtas.get(i);
			cta.setIntIdPersona(juri.getIntIdPersona());
			getCtaBancariaService().grabarCtaBancaria(cta);
			log.info("Se ha grabado cta["+i+"]: "+cta.getIntIdCtaBancaria());
		}
		
		//Seteando lista de Direcciones de Persona Juridica
		setPopupService(domicilioService);
		ArrayList arrayDomi = (ArrayList) domicilioController.getBeanListDirecPerJuri();
		log.info("arrayDomi.size: "+arrayDomi.size());
		for(int i=0; i<arrayDomi.size(); i++){
			Domicilio domi = (Domicilio) arrayDomi.get(i);
			domi.setIntIdPersona(juri.getIntIdPersona());
			getPopupService().grabarDomicilio(domi);
			log.info("Se ha grabado domi["+i+"]: "+domi.getIntIdDireccion());
		}
		
		//Seteando lista de Comunicaciones de Persona Juridica
		setPopupService(comunicacionService);
		ArrayList arrayComunica = (ArrayList) comunicacionController.getBeanListComuniPerJuri();
		log.info("arrayComunica.size: "+arrayComunica.size());
		for(int i=0; i<arrayComunica.size(); i++){
			Comunicacion comunica = (Comunicacion) arrayComunica.get(i);
			comunica.setIntIdPersona(juri.getIntIdPersona());
			getPopupService().grabarComunicacion(comunica);
			log.info("Se ha grabado comunica["+i+"]: "+comunica.getIntIdComunicacion());
		}
		
		//Seteando listas de Representantes Legales de Persona Juridica
		setPopupService(repLegalService);
		ArrayList arrayRepLegal = (ArrayList) repLegalController.getBeanListRepLegal();
		log.info("arrayRepLegal.size: "+arrayRepLegal.size());
		for(int i=0; i<arrayRepLegal.size(); i++){
			RepresentanteLegal replegal = (RepresentanteLegal) arrayRepLegal.get(i);
			replegal.setIntIdPerJuridica(juri.getIntIdPersona());
			getPopupService().grabarRepLegal(replegal);
			log.info("Se ha grabado replegal.getIntIdPersona(): "+replegal.getIntIdPersona());
			
			//Grabar Direccion de Representante Legal
			setPopupService(domicilioService);
			ArrayList listDomiRep = new ArrayList();
			listDomiRep = replegal.getListDirecciones();
			log.info("listDomiRep.size(): "+listDomiRep.size());
			for(int j=0; j<listDomiRep.size(); j++){
				Domicilio domiRep = (Domicilio)listDomiRep.get(j);
				domiRep.setIntIdPersona(replegal.getIntIdPersona());
				getPopupService().grabarDomicilio(domiRep);
				log.info("Se ha grabado domiRep["+j+"]: "+domiRep.getIntIdDireccion());
			}
			
			//Graba Comunicacion de Representante Legal
			setPopupService(comunicacionService);
			ArrayList listComunicaRep = new ArrayList();
			listComunicaRep = replegal.getListComunicaciones();
			log.info("listComunicaRep.size(): "+listComunicaRep.size());
			for(int j=0; j<listComunicaRep.size(); j++){
				Comunicacion comunicaRep = (Comunicacion)listComunicaRep.get(j);
				comunicaRep.setIntIdPersona(replegal.getIntIdPersona());
				getPopupService().grabarComunicacion(comunicaRep);
				log.info("Se ha grabado comunicaRep["+j+"]: "+comunicaRep.getIntIdComunicacion());
			}
		}

		//Agregar al bean de Persona Juridica
		log.info("strIdBtnAgregarPerJuri: "+getStrIdBtnAgregarPerJuri());
		if(getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN1")){
			setBeanPerJuridicaN1(juri);
		}else if (getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN2")){
			setBeanPerJuridicaN2(juri);
		}else if (getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN3")){
			setBeanPerJuridicaN3(juri);
		}
		
		//Limpiar bean perJuridica
		PersonaJuridica juridica = new PersonaJuridica();
		setPerJuridica(juridica);
		setIntTxtRuc(null);
		
		//Limpiar list Cta Bancaria, Direcciones, Comunicaciones, Representantes Legales
		ctaBancariaController.getBeanListCtaBancaria().clear();
		domicilioController.getBeanListDirecPerJuri().clear();
		comunicacionController.getBeanListComuniPerJuri().clear();
		repLegalController.getBeanListRepLegal().clear();
	}
	
	public void validarPerJuridica(ActionEvent event) throws DaoException, ParseException {
		log.info("-------------------------------------Debugging validarPerJuridica-------------------------------------");
		setAperCuentaService(perJuridicaService);
		log.info("intTxtRuc: "+getIntTxtRuc());
		PersonaJuridica juri = new PersonaJuridica();
		juri.setIntRuc(getIntTxtRuc());
		
		ArrayList arrayJuri = new ArrayList();
		arrayJuri = getAperCuentaService().obtenerPerJuridica(juri);
		log.info("arrayJuri.size(): "+arrayJuri.size());
		if(arrayJuri.size()>0){
			juri = (PersonaJuridica)arrayJuri.get(0);
			log.info("intIdPersona: "+juri.getIntIdPersona());
			log.info("strRazonSocial: "+juri.getStrRazonSocial());
			log.info("strNombreComercial"+juri.getStrNombreComercial());
			log.info("strSiglas: "+juri.getStrSiglas());
			log.info("strFechaInscripcion: "+juri.getStrFechaInscripcion());
			log.info("strFechaInicioActi: "+juri.getStrFechaInicioActi());
			log.info("intEstadoContribuyente: "+juri.getIntEstadoContribuyente());
			log.info("intRuc: "+juri.getIntRuc());
			log.info("intNumTrabajadores: "+juri.getIntNumTrabajadores());
			log.info("intCondContribuyente: "+juri.getIntCondContribuyente());
			log.info("intTipoContribuyente: "+juri.getIntTipoContribuyente());
			log.info("intSistemaContable: "+juri.getIntSistemaContable());
			log.info("intEmisionComprobante: "+juri.getIntEmisionComprobante());
			log.info("intComercioExterior: "+juri.getIntComercioExterior());
			log.info("intTipoPersona: "+juri.getIntTipoPersona());
			juri.setDtFechaInscripcion(juri.getStrFechaInscripcion()!=null?sdfshort.parse(juri.getStrFechaInscripcion()):null);
			juri.setDtFechaInicioActi(juri.getStrFechaInicioActi()!=null?sdfshort.parse(juri.getStrFechaInicioActi()):null);
		}else{
			juri = new PersonaJuridica();
			setMessageError("No existe persona jurídica con Ruc: "+getIntTxtRuc());
		}
		setPerJuridica(juri);
		
		//Obteniendo Comunicaciones de Persona Juridica
		setPopupService(comunicacionService);
		HashMap hmapComunica = new HashMap();
		hmapComunica.put("pIntIdPersona", juri.getIntIdPersona());
		ArrayList arrayComunica = getPopupService().listarComunicacion(hmapComunica);
		log.info("arrayComunica.size(): "+arrayComunica.size());
		comunicacionController.setBeanListComuniPerJuri(arrayComunica);
		
		//Obteniendo Direcciones de Persona Juridica
		setPopupService(domicilioService);
		HashMap hmapDomi = new HashMap();
		hmapDomi.put("pIntIdPersona", juri.getIntIdPersona());
		ArrayList arrayDomi = getPopupService().listarDomicilio(hmapDomi);
		log.info("arrayDomi.size(): "+arrayDomi.size());
		domicilioController.setBeanListDirecPerJuri(arrayDomi);
		
		//Obteniendo Ctas Bancarias de Persona Juridica
		setPopupService(ctaBancariaService);
		CuentaBancaria ctaban = new CuentaBancaria();
		ctaban.setIntIdPersona(juri.getIntIdPersona());
		ArrayList arrayCta = getPopupService().listarCtaBancaria(ctaban);
		log.info("arrayCta.size(): "+arrayCta.size());
		ctaBancariaController.setBeanListCtaBancaria(arrayCta);
		
		//Obteniendo Representantes Legales de Persona Juridica
		setPopupService(repLegalService);
		PersonaJuridica perjuri = new PersonaJuridica();
		perjuri.setIntIdPersona(juri.getIntIdPersona());
		ArrayList arrayRepLegal = getPopupService().listarPerNaturalVinculo(perjuri);
		log.info("arrayRepLegal.size(): "+arrayRepLegal.size());
		
		for(int i=0; i<arrayRepLegal.size(); i++){
			RepresentanteLegal replegal = (RepresentanteLegal)arrayRepLegal.get(i);
			log.info("replegal.intIdPersona"+replegal.getIntIdPersona());
			replegal.setStrNombreCompleto(replegal.getStrNombres()+" "+replegal.getStrApePat()+" "+replegal.getStrApeMat());
			
			//Obteniendo Comunicaciones de Representante Legal
			setPopupService(comunicacionService);
			HashMap hmapComu = new HashMap();
			hmapComu.put("pIntIdPersona", replegal.getIntIdPersona());
			ArrayList arrayComu = getPopupService().listarComunicacion(hmapComu);
			log.info("arrayComu.size(): "+arrayComu.size());
			comunicacionController.setBeanListComuniRepLegal(arrayComu);
			replegal.setListComunicaciones(arrayComu);
			
			//Obteniendo Direcciones de Persona Juridica
			setPopupService(domicilioService);
			HashMap hmapDirec = new HashMap();
			hmapDirec.put("pIntIdPersona", replegal.getIntIdPersona());
			ArrayList arrayDirec = getPopupService().listarDomicilio(hmapDirec);
			log.info("arrayDirec.size(): "+arrayDirec.size());
			domicilioController.setBeanListDirecRepLegal(arrayDirec);
			replegal.setListDirecciones(arrayDirec);
		}
		repLegalController.setBeanListRepLegal(arrayRepLegal);
	}
	
	public void addPerJuridicaEstruc(ActionEvent event){
		log.info("-------------------------------------Debugging addPerJuridicaEstruc-------------------------------------");
		log.info("intIdPersona: "+getPerJuridica().getIntIdPersona());
		log.info("strRazonSocial: "+getPerJuridica().getStrRazonSocial());
		log.info("id btnAgregarPerJuri: "+getStrIdBtnAgregarPerJuri());
		PersonaJuridica beanPerJuri = new PersonaJuridica();
		beanPerJuri.setIntIdPersona(getPerJuridica().getIntIdPersona());
		beanPerJuri.setStrRazonSocial(getPerJuridica().getStrRazonSocial());
		if(getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN1")){
			setBeanPerJuridicaN1(beanPerJuri);
		}else if (getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN2")){
			setBeanPerJuridicaN2(beanPerJuri);
		}else if (getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN3")){
			setBeanPerJuridicaN3(beanPerJuri);
		}
		//Limpiar bean perJuridica
		PersonaJuridica juri = new PersonaJuridica();
		setPerJuridica(juri);
		setIntTxtRuc(null);
		log.info("strRazonSocial N1: "+getBeanPerJuridicaN1().getStrRazonSocial());
		log.info("strRazonSocial N2: "+getBeanPerJuridicaN2().getStrRazonSocial());
	}
	
	public void setIdBtnAgregarPerJuri(ActionEvent event){ 
		log.info("-------------------------------------Debugging setIdBtnAgregarPerJuri-------------------------------------");
		UIComponent uicomp = event.getComponent();
		String idcomp = uicomp.getId();
		log.info("uicomp.getId: "+uicomp.getId());
		setStrIdBtnAgregarPerJuri(idcomp);
	}
	
	//--------------------------------------------------------------------------------------------
	// Metodos Getters y Setters
	//--------------------------------------------------------------------------------------------
	public Integer getIntCboTipoRelPerJuri() {
		return intCboTipoRelPerJuri;
	}
	public void setIntCboTipoRelPerJuri(Integer intCboTipoRelPerJuri) {
		this.intCboTipoRelPerJuri = intCboTipoRelPerJuri;
	}
	public Integer getIntCboTiposPersona() {
		return intCboTiposPersona;
	}
	public void setIntCboTiposPersona(Integer intCboTiposPersona) {
		this.intCboTiposPersona = intCboTiposPersona;
	}
	public Integer getIntCboTiposDocumento() {
		return intCboTiposDocumento;
	}
	public void setIntCboTiposDocumento(Integer intCboTiposDocumento) {
		this.intCboTiposDocumento = intCboTiposDocumento;
	}
	public Integer getIntCboEstados() {
		return intCboEstados;
	}
	public void setIntCboEstados(Integer intCboEstados) {
		this.intCboEstados = intCboEstados;
	}
	public Integer getIntCboCondiContri() {
		return intCboCondiContri;
	}
	public void setIntCboCondiContri(Integer intCboCondiContri) {
		this.intCboCondiContri = intCboCondiContri;
	}
	public Integer getIntCboTipoContri() {
		return intCboTipoContri;
	}
	public void setIntCboTipoContri(Integer intCboTipoContri) {
		this.intCboTipoContri = intCboTipoContri;
	}
	public Integer getIntCboTipoContabilidad() {
		return intCboTipoContabilidad;
	}
	public void setIntCboTipoContabilidad(Integer intCboTipoContabilidad) {
		this.intCboTipoContabilidad = intCboTipoContabilidad;
	}
	public Integer getIntCboTipoEmisionComprobante() {
		return intCboTipoEmisionComprobante;
	}
	public void setIntCboTipoEmisionComprobante(Integer intCboTipoEmisionComprobante) {
		this.intCboTipoEmisionComprobante = intCboTipoEmisionComprobante;
	}
	public Integer getIntCboComercioExterior() {
		return intCboComercioExterior;
	}
	public void setIntCboComercioExterior(Integer intCboComercioExterior) {
		this.intCboComercioExterior = intCboComercioExterior;
	}
	public AdminCuentaServiceImpl getPerJuridicaService() {
		return perJuridicaService;
	}
	public void setPerJuridicaService(AdminCuentaServiceImpl perJuridicaService) {
		this.perJuridicaService = perJuridicaService;
	}
	public PersonaJuridica getPerJuridica() {
		return perJuridica;
	}
	public void setPerJuridica(PersonaJuridica perJuridica) {
		this.perJuridica = perJuridica;
	}
	public ActividadEconomica getActEconomica() {
		return actEconomica;
	}
	public void setActEconomica(ActividadEconomica actEconomica) {
		this.actEconomica = actEconomica;
	}
	public Long getIntTxtRuc() {
		return intTxtRuc;
	}
	public void setIntTxtRuc(Long intTxtRuc) {
		this.intTxtRuc = intTxtRuc;
	}
	public PersonaJuridica getBeanPerJuridicaN1() {
		return beanPerJuridicaN1;
	}
	public void setBeanPerJuridicaN1(PersonaJuridica beanPerJuridicaN1) {
		this.beanPerJuridicaN1 = beanPerJuridicaN1;
	}
	public PersonaJuridica getBeanPerJuridicaN2() {
		return beanPerJuridicaN2;
	}
	public void setBeanPerJuridicaN2(PersonaJuridica beanPerJuridicaN2) {
		this.beanPerJuridicaN2 = beanPerJuridicaN2;
	}
	public String getStrIdBtnAgregarPerJuri() {
		return strIdBtnAgregarPerJuri;
	}
	public void setStrIdBtnAgregarPerJuri(String strIdBtnAgregarPerJuri) {
		this.strIdBtnAgregarPerJuri = strIdBtnAgregarPerJuri;
	}
	public PersonaJuridica getBeanPerJuridicaN3() {
		return beanPerJuridicaN3;
	}
	public void setBeanPerJuridicaN3(PersonaJuridica beanPerJuridicaN3) {
		this.beanPerJuridicaN3 = beanPerJuridicaN3;
	}
	public String getIdModalPanel() {
		return idModalPanel;
	}
	public void setIdModalPanel(String idModalPanel) {
		this.idModalPanel = idModalPanel;
	}
	public CtaBancariaController getCtaBancariaController() {
		return ctaBancariaController;
	}
	public void setCtaBancariaController(CtaBancariaController ctaBancariaController) {
		this.ctaBancariaController = ctaBancariaController;
	}
	public DomicilioController getDomicilioController() {
		return domicilioController;
	}
	public void setDomicilioController(DomicilioController domicilioController) {
		this.domicilioController = domicilioController;
	}
	public ComunicacionController getComunicacionController() {
		return comunicacionController;
	}
	public void setComunicacionController(
			ComunicacionController comunicacionController) {
		this.comunicacionController = comunicacionController;
	}
	public RepLegalController getRepLegalController() {
		return repLegalController;
	}
	public void setRepLegalController(RepLegalController repLegalController) {
		this.repLegalController = repLegalController;
	}
	public ComunicacionServiceImpl getComunicacionService() {
		return comunicacionService;
	}
	public void setComunicacionService(ComunicacionServiceImpl comunicacionService) {
		this.comunicacionService = comunicacionService;
	}
	public DomicilioServiceImpl getDomicilioService() {
		return domicilioService;
	}
	public void setDomicilioService(DomicilioServiceImpl domicilioService) {
		this.domicilioService = domicilioService;
	}
	public CtaBancariaServiceImpl getCtaBancariaService() {
		return ctaBancariaService;
	}
	public void setCtaBancariaService(CtaBancariaServiceImpl ctaBancariaService) {
		this.ctaBancariaService = ctaBancariaService;
	}
	public RepLegalServiceImpl getRepLegalService() {
		return repLegalService;
	}
	public void setRepLegalService(RepLegalServiceImpl repLegalService) {
		this.repLegalService = repLegalService;
	}
	
}
