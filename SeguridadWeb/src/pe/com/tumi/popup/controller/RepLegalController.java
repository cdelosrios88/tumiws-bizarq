package pe.com.tumi.popup.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.event.ActionEvent;

import pe.com.tumi.adminCuenta.controller.PerJuridicaController;
import pe.com.tumi.adminCuenta.domain.PersonaJuridica;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.empresa.domain.RepresentanteLegal;
import pe.com.tumi.popup.domain.Comunicacion;
import pe.com.tumi.popup.domain.CuentaBancaria;
import pe.com.tumi.popup.domain.Domicilio;
import pe.com.tumi.popup.service.impl.ComunicacionServiceImpl;
import pe.com.tumi.popup.service.impl.DomicilioServiceImpl;
import pe.com.tumi.popup.service.impl.RepLegalServiceImpl;
import pe.com.tumi.seguridad.domain.BeanSesion;
import pe.com.tumi.seguridad.service.impl.RolServiceImpl;

public class RepLegalController extends GenericController {
	
	private 	RepLegalServiceImpl		repLegalService;
	private 	RolServiceImpl			rolService;
	private 	RepresentanteLegal		beanRepLegal = new RepresentanteLegal();
	private 	List 					beanListRepLegal = new ArrayList(); 		
	private 	Integer 				intCboTipoVinculo = 4; //Representante Legal
	private 	Integer 				intCboTipoPersona = 1; //Persona Natural
	private 	Integer 				intCboTipoDoc;
	private 	String 					strNroDocIdentidad;
	private 	DomicilioController		domicilioController = (DomicilioController) getSpringBean("domicilioController");
	private 	ComunicacionController  comunicacionController = (ComunicacionController) getSpringBean("comunicacionController");
	private 	ComunicacionServiceImpl	comunicacionService;
	private 	DomicilioServiceImpl	domicilioService;

	
	public void validarRepLegal(ActionEvent event) throws DaoException, ParseException{
		log.info("-------------------------------------Debugging validarRepLegal-------------------------------------");
		setPopupService(repLegalService);
		
		log.info("IntCboTipoVinculo: "+getIntCboTipoVinculo());
		log.info("IntCboTipoPersona: "+getIntCboTipoPersona());
		log.info("IntCboTipoDoc_ "+getIntCboTipoDoc());
		log.info("strNroDocIdentidad: "+getStrNroDocIdentidad());
		
		RepresentanteLegal replegal = new RepresentanteLegal();
		replegal.setIntTipoVinculo(getIntCboTipoVinculo());
		replegal.setIntTipoPersona(getIntCboTipoPersona());
		replegal.setIntTipoDocIdentidad(getIntCboTipoDoc());
		replegal.setStrNroDocIdentidad(getStrNroDocIdentidad());
		
		ArrayList arrayRepLegal = new ArrayList();
		arrayRepLegal = getPopupService().listarRepLegal(replegal);
		log.info("arrayRepLegal.size: "+arrayRepLegal.size());
		
		if(arrayRepLegal.size()>0){
			replegal = (RepresentanteLegal)arrayRepLegal.get(0);
			logRepLegalVal(replegal); //valores de las propiedades de Representante Legal
			
			//Seteando las fechas
			String fNac = replegal.getStrFechaNac();
			replegal.setDtFechaNac(fNac!=null?sdfshort.parse(fNac):null);
		}else{
			replegal = new RepresentanteLegal();
			setMessageError("No existe persona natural con documento: "+getStrNroDocIdentidad());
		}
		
		setBeanRepLegal(replegal);
		
		//Obteniendo Comunicaciones de Representante Legal
		setPopupService(comunicacionService);
		HashMap hmapComunica = new HashMap();
		hmapComunica.put("pIntIdPersona", replegal.getIntIdPersona());
		ArrayList arrayComunica = getPopupService().listarComunicacion(hmapComunica);
		comunicacionController.setBeanListComuniRepLegal(arrayComunica);
		
		//Obteniendo Direcciones de Representante Legal
		setPopupService(domicilioService);
		HashMap hmapDomi = new HashMap();
		hmapDomi.put("pIntIdPersona", replegal.getIntIdPersona());
		ArrayList arrayDomi = getPopupService().listarDomicilio(hmapDomi);
		domicilioController.setBeanListDirecRepLegal(arrayDomi);
	}
	
	public void logRepLegalVal(RepresentanteLegal replegal){
		log.info("-------------------------------------Debugging logRepLegalVal-------------------------------------");
		log.info("intIdEmpresa: "+replegal.getIntIdEmpresa());
		log.info("intIdPersona: "+replegal.getIntIdPersona());
		log.info("strApePat: "+replegal.getStrApePat());
		log.info("strApeMat: "+replegal.getStrApeMat());
		log.info("strNombres: "+replegal.getStrNombres());
		log.info("strFechaNac: "+replegal.getStrFechaNac());
		log.info("intTipoDocIdentidad: "+replegal.getIntTipoDocIdentidad());
		log.info("strNroDocIdentidad: "+replegal.getStrNroDocIdentidad());
		log.info("intSexo: "+replegal.getIntSexo());
		log.info("intEstadoCivil: "+replegal.getIntEstadoCivil());
		log.info("intTipoPersona: "+replegal.getIntTipoPersona());
		log.info("intTipoVinculo: "+replegal.getIntTipoVinculo());
		log.info("strPathFoto: "+replegal.getStrPathFoto());
		log.info("strPathFirma: "+replegal.getStrPathFirma());
		log.info("intEstado: "+replegal.getIntEstado());
	}
	
	public void addRepLegal(ActionEvent event){
		log.info("-------------------------------------Debugging addRepLegal-------------------------------------");
		ArrayList listRepLegal = new ArrayList();
		if(beanListRepLegal!=null){
			listRepLegal = (ArrayList)getBeanListRepLegal();
		}
		
		RepresentanteLegal rep = getBeanRepLegal();
		
		//Seteando datos al bean
		RepresentanteLegal replegal = new RepresentanteLegal();
		replegal.setIntIdEmpresa(rep.getIntIdEmpresa());
		replegal.setIntIdPerJuridica(rep.getIntIdPerJuridica());
		replegal.setIntIdPersona(rep.getIntIdPersona());
		replegal.setStrApePat(rep.getStrApePat());
		replegal.setStrApeMat(rep.getStrApeMat());
		replegal.setStrNombres(rep.getStrNombres());
		replegal.setStrFechaNac(rep.getStrFechaNac());
		replegal.setIntTipoDocIdentidad(rep.getIntTipoDocIdentidad());
		replegal.setStrNroDocIdentidad(rep.getStrNroDocIdentidad());
		replegal.setIntSexo(rep.getIntSexo());
		replegal.setIntEstadoCivil(rep.getIntEstadoCivil());
		replegal.setIntTipoPersona(rep.getIntTipoPersona());
		replegal.setIntTipoVinculo(rep.getIntTipoVinculo());
		replegal.setStrPathFirma(rep.getStrPathFirma());
		replegal.setStrPathFoto(replegal.getStrPathFoto());
		replegal.setIntEstado(rep.getIntEstado());
		//Nombre Completo
		replegal.setStrNombreCompleto(replegal.getStrNombres()+" "+replegal.getStrApePat()+" "+replegal.getStrApeMat());
		
		//Obteniendo lista de direcciones para este Representante Legal
		DomicilioController domicilio = (DomicilioController) getSpringBean("domicilioController");
		ArrayList arrayDomi = (ArrayList) domicilio.getBeanListDirecRepLegal();
		ArrayList listDomi = new ArrayList();
		for(int i=0; i<arrayDomi.size(); i++){
			Domicilio domi = (Domicilio) arrayDomi.get(i);
			listDomi.add(domi);
		}
		log.info("beanListDirecRepLegal.size(): "+listDomi.size());
		replegal.setListDirecciones(listDomi);
		domicilio.getBeanListDirecRepLegal().clear();
		
		//Obteniendo lista de comunicaciones para este Representante Legal
		ComunicacionController comunicacion = (ComunicacionController) getSpringBean("comunicacionController");
		ArrayList arrayComunicacion = (ArrayList) comunicacion.getBeanListComuniRepLegal();
		ArrayList listComunicacion = new ArrayList();
		for(int i=0; i<arrayComunicacion.size(); i++){
			Comunicacion comunica = (Comunicacion) arrayComunicacion.get(i);
			listComunicacion.add(comunica);
		}
		log.info("beanListComuniRepLegal.size(): "+listComunicacion.size());
		replegal.setListComunicaciones(listComunicacion);
		comunicacion.getBeanListComuniRepLegal().clear();
		
		listRepLegal.add(replegal);
		setBeanListRepLegal(listRepLegal);
	}
	
	public void cleanBeanRepLegal(ActionEvent event){
		log.info("-----------------------------------Debugging cleanBeanRepLegal-----------------------------------");
		//Limpiar el BeanRepLegal
		RepresentanteLegal cleanRep = new RepresentanteLegal();
		setBeanRepLegal(cleanRep);
		
		//Limpiar list direcciones
		domicilioController.getBeanListDirecRepLegal().clear();
		comunicacionController.getBeanListComuniRepLegal().clear();
	}
	
	public void grabarRepLegal(ActionEvent event) throws DaoException{
		log.info("-------------------------------------Debugging grabarRepLegal-------------------------------------");
		setPopupService(repLegalService);
		
		RepresentanteLegal repLegal = new RepresentanteLegal();
		repLegal = getBeanRepLegal();
		log.info("dtFechaNac: "+repLegal.getDtFechaNac());
		log.info("strRoles: "+repLegal.getStrRoles());
		Date fNac = repLegal.getDtFechaNac();
		repLegal.setStrFechaNac(fNac!=null?sdfshort.format(fNac):null);
		
		getPopupService().grabarRepLegal(repLegal);
	}
	
	public void verRepLegal(ActionEvent event) throws DaoException{
		log.info("-------------------------------------Debugging verRepLegal-------------------------------------");
		setPopupService(repLegalService);
		
		log.info("IntTipoVinculo: "+getRequestParameter("idTipoVinculo"));
		log.info("IntTipoPersona: "+getRequestParameter("idTipoPersona"));
		log.info("IntTipoDoc "+getRequestParameter("idTipoDoc"));
		log.info("strNroDocIdentidad: "+getRequestParameter("nroDocIdentidad"));
		Integer intVinculo = getRequestParameter("idTipoVinculo")!=null?Integer.parseInt(getRequestParameter("idTipoVinculo")):null;
		Integer intTipoPersona = getRequestParameter("idTipoPersona")!=null?Integer.parseInt(getRequestParameter("idTipoPersona")):null;
		Integer intTipoDoc = getRequestParameter("idTipoDoc")!=null?Integer.parseInt(getRequestParameter("idTipoDoc")):null;
		String 	strNroDoc = getRequestParameter("nroDocIdentidad")!=null?getRequestParameter("nroDocIdentidad"):null;
		
		ArrayList arrayRepLegal = new ArrayList();
		arrayRepLegal = (ArrayList) getBeanListRepLegal();
		log.info("arrayRepLegal.size: "+arrayRepLegal.size());
		RepresentanteLegal replegal = new RepresentanteLegal();
		for(int i=0; i<arrayRepLegal.size();i++){
			replegal = (RepresentanteLegal) arrayRepLegal.get(i);
			if(replegal.getIntTipoVinculo().equals(intVinculo)
					&& replegal.getIntTipoPersona().equals(intTipoPersona)
					&& replegal.getIntTipoDocIdentidad().equals(intTipoDoc)
					&& replegal.getStrNroDocIdentidad().equals(strNroDoc)){
				break;
			}
		}
		logRepLegalVal(replegal); //valores de las propiedades de Representante Legal
		setBeanRepLegal(replegal);
		
		//Obteniendo las direcciones
		ArrayList arrayDirecciones = replegal.getListDirecciones();
		ArrayList listDirecciones = new ArrayList();
		log.info("arrayDirecciones.size(): "+arrayDirecciones.size());
		for(int i=0; i<arrayDirecciones.size(); i++){
			Domicilio domi = (Domicilio) arrayDirecciones.get(i);
			listDirecciones.add(domi);
		}
		log.info("Representante Legal listDirecciones.size(): "+listDirecciones.size());
		domicilioController.setBeanListDirecRepLegal(listDirecciones);
		
		//Obteniendo las comunicaciones
		ArrayList arrayComunica = replegal.getListComunicaciones();
		ArrayList listComunica = new ArrayList();
		log.info("arrayComunica.size(): "+arrayComunica.size());
		for(int i=0; i<arrayComunica.size(); i++){
			Comunicacion comunica = (Comunicacion) arrayComunica.get(i);
			listComunica.add(comunica);
		}
		log.info("Representante Legal listComunica.size(): "+listComunica.size());
		comunicacionController.setBeanListComuniRepLegal(listComunica);
	}
	
	//Getters y Setters
	public RepresentanteLegal getBeanRepLegal() {
		this.beanRepLegal.setIntIdEmpresa(BeanSesion.getIntIdEmpresa());
		this.beanRepLegal.setIntEstado(1); //Activo por default
		this.beanRepLegal.setIntTipoDocIdentidad(1);// DNI por default
		this.beanRepLegal.setIntTipoPersona(intCboTipoPersona);
		this.beanRepLegal.setIntTipoVinculo(intCboTipoVinculo);
		return beanRepLegal;
	}
	public void setBeanRepLegal(RepresentanteLegal beanRepLegal) {
		this.beanRepLegal = beanRepLegal;
	}
	public RepLegalServiceImpl getRepLegalService() {
		return repLegalService;
	}
	public void setRepLegalService(RepLegalServiceImpl repLegalService) {
		this.repLegalService = repLegalService;
	}
	public Integer getIntCboTipoVinculo() {
		return intCboTipoVinculo;
	}
	public void setIntCboTipoVinculo(Integer intCboTipoVinculo) {
		this.intCboTipoVinculo = intCboTipoVinculo;
	}
	public Integer getIntCboTipoPersona() {
		return intCboTipoPersona;
	}
	public void setIntCboTipoPersona(Integer intCboTipoPersona) {
		this.intCboTipoPersona = intCboTipoPersona;
	}
	public Integer getIntCboTipoDoc() {
		return intCboTipoDoc;
	}
	public void setIntCboTipoDoc(Integer intCboTipoDoc) {
		this.intCboTipoDoc = intCboTipoDoc;
	}
	public String getStrNroDocIdentidad() {
		return strNroDocIdentidad;
	}
	public void setStrNroDocIdentidad(String strNroDocIdentidad) {
		this.strNroDocIdentidad = strNroDocIdentidad;
	}
	public RolServiceImpl getRolService() {
		return rolService;
	}
	public void setRolService(RolServiceImpl rolService) {
		this.rolService = rolService;
	}
	public List getBeanListRepLegal() {
		return beanListRepLegal;
	}
	public void setBeanListRepLegal(List beanListRepLegal) {
		this.beanListRepLegal = beanListRepLegal;
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
	
}
