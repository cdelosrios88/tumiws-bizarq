package pe.com.tumi.credito.popup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.core.controller.SocioController;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.estructura.controller.EstructuraOrganicaController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.message.MessageController;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.TipoComprobante;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class JuridicaController {

	protected   static Logger 		    	log = Logger.getLogger(JuridicaController.class);
	protected 	static SimpleDateFormat 	sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	protected 	static SimpleDateFormat 	sdfshort = new SimpleDateFormat("dd/MM/yyyy");
	private 	Persona						perJuridica = null;
	private 	Persona 					beanPerJuridicaN1 = null;
	private 	Persona 					beanPerJuridicaN2 = null;
	private 	Persona 					beanPerJuridicaN3 = null;
	private 	ActividadEconomica			actEconomica = new ActividadEconomica();
	private 	Integer						intCboTipoRelPerJuri;
	private 	Integer 					intCboTiposPersona;
	private 	Integer 					intCboTiposDocumento;
	private 	Integer						intCboEstados;
	private 	Integer 					intCboCondiContri;
	private 	Integer 					intCboTipoContri;
	private 	Integer 					intCboTipoContabilidad;
	private 	Integer 					intCboTipoEmisionComprobante;
	private 	Integer 					intCboComercioExterior;
	private 	String						strTxtRuc;
	private 	String 						strIdBtnAgregarPerJuri;
	private 	String 						idModalPanel = "pAgregarPerJuri";
	private 	String 						strRoles;
	private 	Boolean 					isJuridicaSaved;
	private 	List<Persona> 				listPerJuridicaSocioNatu;
	private 	String 						strCallingFormId;
	private 	String 						strFormIdSocioNatu;
	
	//Atributos de sesión
	private 	Integer 					SESION_IDEMPRESA;
	@SuppressWarnings("unused")
	private 	Integer 					sesionIntIdUsuario = 1;
	
	
	public JuridicaController(){
		perJuridica = new Persona();
		perJuridica.setJuridica(new Juridica());
		
		beanPerJuridicaN1 = new Persona();
		beanPerJuridicaN1.setJuridica(new Juridica());
		
		beanPerJuridicaN2 = new Persona();
		beanPerJuridicaN2.setJuridica(new Juridica());
		
		beanPerJuridicaN3 = new Persona();
		beanPerJuridicaN3.setJuridica(new Juridica());
		
		intCboTipoRelPerJuri = 1;
		intCboTiposPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
		intCboTiposDocumento = 4;
		
		isJuridicaSaved = false;
		
		strFormIdSocioNatu = "frmSocioNatural";
		
		//atributos de sesión
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
	}
	
	public Persona getDetallePerJuridica(Persona perjuri){
		
		//Seteando list de Ctas Bancarias de Persona Juridica
		CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		List<CuentaBancaria> arrayCtas = ctaBancariaController.getListCtaBancariaJuriConve();
		if(arrayCtas!=null && arrayCtas.size()>0){
			log.info("arrayCtas.size: "+arrayCtas.size());
			perjuri.setListaCuentaBancaria(new ArrayList<CuentaBancaria>());
			for(CuentaBancaria ctaBan : arrayCtas){
				perjuri.getListaCuentaBancaria().add(ctaBan);
			}
		}
		
		//Seteando list de Direcciones de Persona Juridica
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		List<Domicilio> arrayDomi = domicilioController.getBeanListDirecPerJuri();

		if(arrayDomi!=null && arrayDomi.size()>0){
			log.info("arrayDomi.size: "+arrayDomi.size());
			perjuri.setListaDomicilio(new ArrayList<Domicilio>());
			for(Domicilio domi : arrayDomi){
				perjuri.getListaDomicilio().add(domi);
			}
		}
		
		//Seteando list de Comunicaciones de Persona Juridica
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		List<Comunicacion> arrayComunica = comunicacionController.getBeanListComuniPerJuri();
		if(arrayComunica!=null && arrayComunica.size()>0){
			log.info("arrayComunica.size: "+arrayComunica.size());
			perjuri.setListaComunicacion(new ArrayList<Comunicacion>());
			for(Comunicacion comunica : arrayComunica){
				perjuri.getListaComunicacion().add(comunica);
			}
		}
		
		//Seteando lista de Actividades Económicas de Persona Juridica
		ActividadEconomicaController actEconomicaController = (ActividadEconomicaController)getSessionBean("actEconomicaController");
		List<ActividadEconomica> arrayActEconomica = actEconomicaController.getListActEconomicaJuridica();
		if(arrayActEconomica!=null && arrayActEconomica.size()>0){
			log.info("arrayActEconomica.size: "+arrayActEconomica.size());
			perjuri.getJuridica().setListaActividadEconomica(new ArrayList<ActividadEconomica>());
			for(ActividadEconomica actEco : arrayActEconomica){
				perjuri.getJuridica().getListaActividadEconomica().add(actEco);
			}
		}
		
		//Seteando lista de Tipo de Comprobante de Persona Juridica
		TipoComprobanteController tipoComprobanteController = (TipoComprobanteController)getSessionBean("tipoComprobanteController");
		List<TipoComprobante> arrayComprobante = tipoComprobanteController.getListComprobanteJuridica();
		if(arrayComprobante!=null && arrayComprobante.size()>0){
			log.info("arrayComprobante.size: "+arrayComprobante.size());
			perjuri.getJuridica().setListaTipoComprobante(new ArrayList<TipoComprobante>());
			for(TipoComprobante comprobante : arrayComprobante){
				perjuri.getJuridica().getListaTipoComprobante().add(comprobante);
			}
		}
		
		//Limpiar list Cta Bancaria, Direcciones, Comunicaciones
		ctaBancariaController.getListCtaBancariaJuriConve().clear();
		domicilioController.getBeanListDirecPerJuri().clear();
		comunicacionController.getBeanListComuniPerJuri().clear();
		actEconomicaController.getListActEconomicaJuridica().clear();
		tipoComprobanteController.getListComprobanteJuridica().clear();
		
		return perjuri;
	}
	
	public void savePerJuridicaEstructura(ActionEvent event) throws BusinessException {
		log.info("-------------------------------------Debugging savePerJuridicaEstructura-------------------------------------");
		Persona perjuri = new Persona();
		perjuri.setJuridica(new Juridica());
		
		perjuri = getDetallePerJuridica(perjuri);
		
		//Seteando lista de Representantes Legales y Contactos de Persona Juridica
		NaturalController naturalController = (NaturalController)getSessionBean("naturalController");
		List<Persona> arrayRepLegal = naturalController.getBeanListRepLegal();
		List<Persona> arrayContacto = naturalController.getBeanListContactoNatu();
		
		ArrayList<Persona> arrayPersona = new ArrayList<Persona>();
		if(arrayRepLegal!=null){
			log.info("arrayRepLegal.size: "+arrayRepLegal.size());
			for(Persona replegal : arrayRepLegal){
				arrayPersona.add(replegal);
			}
		}
		if(arrayContacto!=null){
			log.info("arrayContacto.size: "+arrayContacto.size());
			for(Persona contacto : arrayContacto){
				arrayPersona.add(contacto);
			}
		}
		perjuri.setListaPersona(arrayPersona);
		
		//Grabar la Persona Juridica
		perjuri = grabarPerJuridica(perjuri);
		
		//Agregar al bean de Persona Juridica
		log.info("strIdBtnAgregarPerJuri: "+getStrIdBtnAgregarPerJuri());
		if(getStrIdBtnAgregarPerJuri()!=null){
			if(getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN1")){
				setBeanPerJuridicaN1(perjuri);
			}else if (getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN2")){
				setBeanPerJuridicaN2(perjuri);
			}else if (getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN3")){
				setBeanPerJuridicaN3(perjuri);
			}
		}
		
		//Limpiar list de Representantes Legales y Contactos
		naturalController.setBeanListRepLegal(null);
		naturalController.setBeanListContactoNatu(null);
	}
	
	public void savePerJuridicaSocioNatuEmp(ActionEvent event) throws BusinessException {
		log.info("-------------------------------------Debugging savePerJuridicaSocioNatuEmp-------------------------------------");
		Persona perjuri = new Persona();
		perjuri.setJuridica(new Juridica());
		
		perjuri = getDetallePerJuridica(perjuri);
		
		//Seteando lista de Contactos de Persona Juridica
		NaturalController naturalController = (NaturalController)getSessionBean("naturalController");
		List<Persona> arrayContacto = naturalController.getBeanListContactoNatu();
		perjuri.setListaPersona(arrayContacto);
		
		//Valores por default de PersonaEmpresa y Vinculo
		SocioController socioController = (SocioController) getSessionBean("socioController");
		Socio socio = socioController.getBeanSocioComp().getSocio();
		
		perjuri.setPersonaEmpresa(new PersonaEmpresa());
		perjuri.getPersonaEmpresa().setVinculo(new Vinculo());
		perjuri.getPersonaEmpresa().getVinculo().setIntIdEmpresa(SESION_IDEMPRESA);
		perjuri.getPersonaEmpresa().getVinculo().setIntIdPersona(socio.getId().getIntIdPersona());
		perjuri.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(Constante.PARAM_T_TIPOVINCULO_TIENEEMPRESA);
		perjuri.getPersonaEmpresa().getVinculo().setIntEmpresaVinculo(SESION_IDEMPRESA);
		perjuri.getPersonaEmpresa().getVinculo().setIntPersonaVinculo(getPerJuridica().getIntIdPersona());
		perjuri.getPersonaEmpresa().getVinculo().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		//Grabar la Persona Juridica
		perjuri = grabarPerJuridica(perjuri);
		log.info("perjuri.intIdPersona: "+perjuri.getIntIdPersona());
		log.info("perjuri.juridica.persona: "+perjuri.getJuridica().getPersona());
		
		//Agregar a la lista dinámica
		if(listPerJuridicaSocioNatu==null){
			listPerJuridicaSocioNatu = new ArrayList<Persona>();
		}
		List<Persona> listAux = new ArrayList<Persona>();
		Persona aux = null;
		for(int i=0; i<listPerJuridicaSocioNatu.size(); i++){
			aux = listPerJuridicaSocioNatu.get(i);
			if(!aux.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				listAux.add(aux);
			}
		}
		listAux.add(perjuri);
		for(int i=0; i<listPerJuridicaSocioNatu.size(); i++){
			aux = listPerJuridicaSocioNatu.get(i);
			if(aux.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				listAux.add(aux);
			}
		}
		setListPerJuridicaSocioNatu(listAux);
		
		//Limpiar list de Representantes Legales y Contactos
		naturalController.getBeanListContactoNatu().clear();
	}
	
	public Persona grabarPerJuridica(Persona perjuri) throws BusinessException {
		log.info("-------------------------------------Debugging grabarPerJuridica-------------------------------------");
		
		log.info("perJuridica.juridica.intIdPersona: "+getPerJuridica().getJuridica().getIntIdPersona());
		log.info("intCboTiposPersona: "+getIntCboTiposPersona());
		log.info("dtFechaInscripcion: "+getPerJuridica().getJuridica().getDtFechaInscripcion());
		log.info("dtFechaInicioActi: "+getPerJuridica().getJuridica().getDtFechaInicioAct());
		log.info("perJuridica.intIdPersona: "+getPerJuridica().getIntIdPersona());
		log.info("juri.getStrRazonSocial(): "+getPerJuridica().getJuridica().getStrRazonSocial());
		log.info("intEstadoContribuyente: "+getPerJuridica().getJuridica().getIntEstadoContribuyenteCod());
		log.info("intCondContribuyente: "+getPerJuridica().getJuridica().getIntCondContribuyente());
		log.info("intTipoContribuyente: "+getPerJuridica().getJuridica().getIntTipoContribuyenteCod());
		log.info("intSistemaContable: "+getPerJuridica().getJuridica().getIntSistemaContable());
		log.info("intEmisionComprobante: "+getPerJuridica().getJuridica().getIntEmisionComprobante());
		log.info("intComercioExterior: "+getPerJuridica().getJuridica().getIntComercioExterior());
		log.info("strFichaRegPublico: "+getPerJuridica().getJuridica().getStrFichaRegPublico());
		
		Persona persona = new Persona();
		persona.setJuridica(new Juridica());
		persona.setPersonaEmpresa(new PersonaEmpresa());
		persona.getPersonaEmpresa().setId(new PersonaEmpresaPK());
		persona.getPersonaEmpresa().setVinculo(new Vinculo());
		
		//Parametros de Persona
		persona.setIntIdPersona(getPerJuridica().getIntIdPersona());
		persona.setStrRuc(getPerJuridica().getStrRuc());
		persona.setIntTipoPersonaCod(getIntCboTiposPersona());
		persona.setIntEstadoCod(1);
		
		//Parametros de Juridica
		persona.getJuridica().setIntIdPersona(getPerJuridica().getJuridica().getIntIdPersona());
		persona.getJuridica().setStrRazonSocial(getPerJuridica().getJuridica().getStrRazonSocial());
		persona.getJuridica().setStrNombreComercial(getPerJuridica().getJuridica().getStrNombreComercial());
		persona.getJuridica().setStrSiglas(getPerJuridica().getJuridica().getStrSiglas());
		persona.getJuridica().setDtFechaInscripcion(getPerJuridica().getJuridica().getDtFechaInscripcion());
		persona.getJuridica().setDtFechaInicioAct(getPerJuridica().getJuridica().getDtFechaInicioAct());
		persona.getJuridica().setIntEstadoContribuyenteCod(getPerJuridica().getJuridica().getIntEstadoContribuyenteCod().equals(0)?null:getPerJuridica().getJuridica().getIntEstadoContribuyenteCod());
		persona.getJuridica().setIntCondContribuyente(getPerJuridica().getJuridica().getIntCondContribuyente().equals(0)?null:getPerJuridica().getJuridica().getIntCondContribuyente());
		persona.getJuridica().setIntNroTrabajadores(getPerJuridica().getJuridica().getIntNroTrabajadores());
		persona.getJuridica().setIntTipoContribuyenteCod(getPerJuridica().getJuridica().getIntTipoContribuyenteCod().equals(0)?null:getPerJuridica().getJuridica().getIntTipoContribuyenteCod());
		persona.getJuridica().setIntSistemaContable(getPerJuridica().getJuridica().getIntSistemaContable().equals(0)?null:getPerJuridica().getJuridica().getIntSistemaContable());
		persona.getJuridica().setIntEmisionComprobante(getPerJuridica().getJuridica().getIntEmisionComprobante().equals(0)?null:getPerJuridica().getJuridica().getIntEmisionComprobante());
		persona.getJuridica().setIntComercioExterior(getPerJuridica().getJuridica().getIntComercioExterior().equals(0)?null:getPerJuridica().getJuridica().getIntComercioExterior());
		persona.getJuridica().setStrFichaRegPublico(getPerJuridica().getJuridica().getStrFichaRegPublico());
		
		//Parametros de PersonaEmpresa
		if(perjuri.getPersonaEmpresa()==null || perjuri.getPersonaEmpresa().getId()==null
				|| perjuri.getPersonaEmpresa().getId().getIntIdEmpresa()==null){
			if(perjuri.getPersonaEmpresa()==null){
				perjuri.setPersonaEmpresa(new PersonaEmpresa());
			}
			if(perjuri.getPersonaEmpresa().getId()==null){
				perjuri.getPersonaEmpresa().setId(new PersonaEmpresaPK());
			}
			perjuri.getPersonaEmpresa().getId().setIntIdEmpresa(SESION_IDEMPRESA);
			persona.setPersonaEmpresa(perjuri.getPersonaEmpresa());
		}
		
		//Valores de Vinculo
		if(perjuri.getPersonaEmpresa()!=null && perjuri.getPersonaEmpresa().getVinculo()!=null){
			persona.getPersonaEmpresa().getVinculo().setIntIdEmpresa(perjuri.getPersonaEmpresa().getVinculo().getIntIdEmpresa());
			persona.getPersonaEmpresa().getVinculo().setIntIdPersona(perjuri.getPersonaEmpresa().getVinculo().getIntIdPersona());
			persona.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(perjuri.getPersonaEmpresa().getVinculo().getIntTipoVinculoCod());
			persona.getPersonaEmpresa().getVinculo().setIntPersonaVinculo(perjuri.getPersonaEmpresa().getVinculo().getIntPersonaVinculo());
			persona.getPersonaEmpresa().getVinculo().setIntEmpresaVinculo(perjuri.getPersonaEmpresa().getVinculo().getIntEmpresaVinculo());
			persona.getPersonaEmpresa().getVinculo().setIntEstadoCod(perjuri.getPersonaEmpresa().getVinculo().getIntEstadoCod());
		}
		
		//Seteando su detalle
		persona.setListaCuentaBancaria(perjuri.getListaCuentaBancaria());
		persona.setListaDomicilio(perjuri.getListaDomicilio());
		persona.setListaComunicacion(perjuri.getListaComunicacion());
		persona.setListaPersona(perjuri.getListaPersona());
		persona.getJuridica().setListaActividadEconomica(perjuri.getJuridica().getListaActividadEconomica());
		persona.getJuridica().setListaTipoComprobante(perjuri.getJuridica().getListaTipoComprobante());
		
		try {
			//Mensaje de Error que sólo se mostrará si no se graba correctamente. 
			isJuridicaSaved = false;
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setErrorMessage("Ocurrió un error de validación al guardar Persona Jurídica. Verifique y corrija.");
			
			//Graba la persona jurídica
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			log.info("persona.intIdPersona: "+persona.getIntIdPersona());

			/* Inicio - Auditoria - GTorresBrousset 24.abr.2014 */
			SocioController socioController = new SocioController();
			List<Auditoria> listaAuditoriaPersona = new ArrayList<Auditoria>();
			if(persona.getIntIdPersona() == null){
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				persona = personaFacade.grabarPersonaJuridicaTotal(persona);
				listaAuditoriaPersona = socioController.generarAuditoriaPersona(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, persona);
			} else {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_UPDATE");
				persona = personaFacade.modificarPersonaJuridicaTotal(persona);
				listaAuditoriaPersona = socioController.generarAuditoriaPersona(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE, persona);
			}
			
			for(Auditoria auditoriaPersona : listaAuditoriaPersona) {
				socioController.grabarAuditoria(auditoriaPersona);
			}
			/* Fin - Auditoria - GTorresBrousset 24.abr.2014 */
			
    		if(persona.getIntIdPersona()!=null){
    			isJuridicaSaved = true; //Ya no muestra el mensaje de Error
    			setMessageSuccess("Se grabó satisfactoriamente Persona Jurídica.");
    		}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} 
		
		//Limpiar bean perJuridica
		setPerJuridica(new Persona());
		getPerJuridica().setJuridica(new Juridica());
		setStrTxtRuc(null);
		
		return persona;
	}
	
	public void cleanPerJuridicaEstructura(){
		log.info("-----------------------------------Debugging cleanBeanPerJuridica-----------------------------------");
		//Limpiar el PerJuridica
		Persona perJuridica = new Persona();
		perJuridica.setJuridica(new Juridica());
		setPerJuridica(perJuridica);
		setStrTxtRuc(null);
		
		//Limpiando lista de Ctas Bancarias de Persona Juridica
		CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		if(ctaBancariaController!=null)ctaBancariaController.getListCtaBancariaJuriConve().clear();
		
		//Limpiando lista de Direcciones de Persona Juridica
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		if(domicilioController!=null)domicilioController.getBeanListDirecPerJuri().clear();
		
		//Limpiando lista de Comunicaciones de Persona Juridica
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		if(comunicacionController!=null)comunicacionController.getBeanListComuniPerJuri().clear();
		
		//Limpiando listas de Representantes Legales y Contactos de Persona Juridica
		NaturalController naturalController = (NaturalController)getSessionBean("naturalController");
		if(naturalController!=null){
			naturalController.setBeanListRepLegal(null);
			naturalController.setBeanListContactoNatu(null);
		}
		
		//Limpiando listas de Actividades Economicas de Persona Juridica
		ActividadEconomicaController actEconomicaController = (ActividadEconomicaController)getSessionBean("actEconomicaController");
		if(actEconomicaController!=null)actEconomicaController.getListActEconomicaJuridica().clear();
		
		//Limpiando listas de Tipo Comprobante de Persona Juridica
		TipoComprobanteController tipoComprobanteController = (TipoComprobanteController)getSessionBean("tipoComprobanteController");
		if(tipoComprobanteController!=null)tipoComprobanteController.getListComprobanteJuridica().clear();
		
		//Limpiando roles de Persona Juridica
		setStrRoles("");
	}
	
	public void cleanSocioNatuEmp(ActionEvent event){
		log.info("-----------------------------------Debugging cleanSocioNatuEmp-----------------------------------");
		cleanPerJuridicaSocioNatuEmp();
	}
	
	public void cleanPerJuridicaSocioNatuEmp(){
		log.info("-----------------------------------Debugging cleanPerJuridicaSocioNatuEmp-----------------------------------");
		//Limpiar el PerJuridica
		Persona perJuridica = new Persona();
		perJuridica.setJuridica(new Juridica());
		setPerJuridica(perJuridica);
		setStrTxtRuc(null);
		
		//Limpiando lista de Ctas Bancarias de Persona Juridica
		CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		if(ctaBancariaController!=null)ctaBancariaController.setListCtaBanSocioNatuEmp(null);
		
		//Limpiando lista de Direcciones de Persona Juridica
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		if(domicilioController!=null)domicilioController.setListDirecSocioNatuEmp(null);
		
		//Limpiando lista de Comunicaciones de Persona Juridica
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		if(comunicacionController!=null)comunicacionController.setListComuniSocioNatuEmp(null);
		
		//Limpiando listas de Representantes Legales y Contactos de Persona Juridica
		NaturalController naturalController = (NaturalController)getSessionBean("naturalController");
		if(naturalController!=null)naturalController.setBeanListContactoNatu(null);
		
		//Limpiando listas de Actividades Economicas de Persona Juridica
		ActividadEconomicaController actEconomicaController = (ActividadEconomicaController)getSessionBean("actEconomicaController");
		if(actEconomicaController!=null)actEconomicaController.setListActiEcoSocioNatuEmp(null);
		
		//Limpiando listas de Tipo Comprobante de Persona Juridica
		TipoComprobanteController tipoComprobanteController = (TipoComprobanteController)getSessionBean("tipoComprobanteController");
		if(tipoComprobanteController!=null)tipoComprobanteController.setListTipoComprobSocioNatuEmp(null);
		
		//Limpiando roles de Persona Juridica
		setStrRoles("");
	}
	
	public void validarPerJuriEstructura(ActionEvent event) {
		log.info("-------------------------------------Debugging validarPerJuriEstructura-------------------------------------");
		Persona perjuri = validarPerJuridica();
		
		if(perjuri.getListaComunicacion()!=null){
			//Obteniendo Comunicaciones de Persona Juridica
			log.info("perJuridica.listaComunicacion.size(): "+perjuri.getListaComunicacion().size());
			ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
			comunicacionController.setBeanListComuniPerJuri(perjuri.getListaComunicacion());
		}
		if(perjuri.getListaDomicilio()!=null){
			//Obteniendo Direcciones de Persona Juridica
			log.info("perJuridica.listaDomicilio.size(): "+perjuri.getListaDomicilio().size());
			DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
			domicilioController.setBeanListDirecPerJuri(perjuri.getListaDomicilio());
		}
		if(perjuri.getListaCuentaBancaria()!=null){
			//Obteniendo Ctas Bancarias de Persona Juridica
			log.info("perJuridica.listaCuentaBancaria.size(): "+perjuri.getListaCuentaBancaria().size());
			CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
			ctaBancariaController.setListCtaBancariaJuriConve(perjuri.getListaCuentaBancaria());
		}
		if(perjuri.getListaPersona()!=null){
			//Obteniendo Representantes Legales
			log.info("perJuridica.listaPersonaNatural.size(): "+perjuri.getListaPersona().size());
			ArrayList<Persona> arrayReplegal = new ArrayList<Persona>();
			ArrayList<Persona> arrayContacto = new ArrayList<Persona>();
			NaturalController naturalController = (NaturalController)getSessionBean("naturalController");
			for(Persona per : perjuri.getListaPersona()){
				log.info("getPersonaEmpresa().getVinculo().getIntTipoVinculoCod(): "+per.getPersonaEmpresa().getVinculo().getIntTipoVinculoCod());
				if(per.getPersonaEmpresa().getVinculo().getIntTipoVinculoCod().equals(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL)){
					arrayReplegal.add(per);
				}else if(per.getPersonaEmpresa().getVinculo().getIntTipoVinculoCod().equals(Constante.PARAM_T_TIPOVINCULO_CONTACTO)){
					arrayContacto.add(per);
				}
			}
			log.info("arrayReplegal.size(): "+arrayReplegal.size());
			log.info("arrayContacto.size(): "+arrayContacto.size());
			naturalController.setBeanListRepLegal(arrayReplegal);
			naturalController.setBeanListContactoNatu(arrayContacto);
		}
		if(perjuri.getJuridica().getListaActividadEconomica()!=null){
			//Obteniendo Direcciones de Persona Juridica
			log.info("perJuridica.juridica.listaActividadEconomica.size: "+perjuri.getJuridica().getListaActividadEconomica().size());
			ActividadEconomicaController actEconomicaController = (ActividadEconomicaController)getSessionBean("actEconomicaController");
			actEconomicaController.setListActEconomicaJuridica(perjuri.getJuridica().getListaActividadEconomica());
		}
		if(perjuri.getJuridica().getListaTipoComprobante()!=null){
			//Obteniendo Direcciones de Persona Juridica
			log.info("perJuridica.juridica.listaTipoComprobante.size: "+perjuri.getJuridica().getListaTipoComprobante().size());
			TipoComprobanteController tipoComprobanteController = (TipoComprobanteController)getSessionBean("tipoComprobanteController");
			tipoComprobanteController.setListComprobanteJuridica(perjuri.getJuridica().getListaTipoComprobante());
		}
	}
	
	public void validarPerJuriSocioNatuEmp(ActionEvent event) {
		log.info("-------------------------------------Debugging validarPerJuriSocioNatuEmp-------------------------------------");
		Persona perjuri = validarPerJuridica();
		
		if(perjuri.getListaComunicacion()!=null){
			//Obteniendo Comunicaciones de Persona Juridica
			log.info("perJuridica.listaComunicacion.size(): "+perjuri.getListaComunicacion().size());
			ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
			comunicacionController.setListComuniSocioNatuEmp(perjuri.getListaComunicacion());
		}
		if(perjuri.getListaDomicilio()!=null){
			//Obteniendo Direcciones de Persona Juridica
			log.info("perJuridica.listaDomicilio.size(): "+perjuri.getListaDomicilio().size());
			DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
			domicilioController.setListDirecSocioNatuEmp(perjuri.getListaDomicilio());
		}
		if(perjuri.getListaCuentaBancaria()!=null){
			//Obteniendo Ctas Bancarias de Persona Juridica
			log.info("perJuridica.listaCuentaBancaria.size(): "+perjuri.getListaCuentaBancaria().size());
			CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
			ctaBancariaController.setListCtaBanSocioNatuEmp(perjuri.getListaCuentaBancaria());
		}
		if(perjuri.getListaPersona()!=null){
			//Obteniendo Representantes Legales
			log.info("perJuridica.listaPersonaNatural.size(): "+perjuri.getListaPersona().size());	
			ArrayList<Persona> arrayContacto = new ArrayList<Persona>();
			NaturalController naturalController = (NaturalController)getSessionBean("naturalController");
			for(Persona per : perjuri.getListaPersona()){
				if(per.getPersonaEmpresa().getVinculo().getIntTipoVinculoCod().equals(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL)){
					arrayContacto.add(per);
				}
			}
			naturalController.setBeanListContactoNatu(arrayContacto);
		}
		if(perjuri.getJuridica().getListaActividadEconomica()!=null){
			//Obteniendo Direcciones de Persona Juridica
			log.info("perJuridica.juridica.listaActividadEconomica.size: "+perjuri.getJuridica().getListaActividadEconomica().size());
			ActividadEconomicaController actEconomicaController = (ActividadEconomicaController)getSessionBean("actEconomicaController");
			actEconomicaController.setListActiEcoSocioNatuEmp(perjuri.getJuridica().getListaActividadEconomica());
		}
		if(perjuri.getJuridica().getListaTipoComprobante()!=null){
			//Obteniendo Direcciones de Persona Juridica
			log.info("perJuridica.juridica.listaTipoComprobante.size: "+perjuri.getJuridica().getListaTipoComprobante().size());
			TipoComprobanteController tipoComprobanteController = (TipoComprobanteController)getSessionBean("tipoComprobanteController");
			tipoComprobanteController.setListTipoComprobSocioNatuEmp(perjuri.getJuridica().getListaTipoComprobante());
		}
	}
	
	public Persona validarPerJuridica() {
		log.info("-------------------------------------Debugging validarPerJuridica-------------------------------------");
		log.info("intTxtRuc: "+getStrTxtRuc());
		
		try {
    		PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
    		perJuridica = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa(getStrTxtRuc(),SESION_IDEMPRESA);
    		log.info("persona.intIdPersona: "+perJuridica.getIntIdPersona());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if(perJuridica!=null){
			log.info("intIdPersona: "+perJuridica.getJuridica().getIntIdPersona());
			log.info("strRazonSocial: "+perJuridica.getJuridica().getStrRazonSocial());
			log.info("strNombreComercial"+perJuridica.getJuridica().getStrNombreComercial());
			log.info("strSiglas: "+perJuridica.getJuridica().getStrSiglas());
			log.info("strFechaInscripcion: "+perJuridica.getJuridica().getStrFechaInscripcion());
			log.info("dtFechaInicioAct: "+perJuridica.getJuridica().getDtFechaInicioAct());
			log.info("intEstadoContribuyente: "+perJuridica.getJuridica().getIntEstadoContribuyenteCod());
			log.info("intRuc: "+perJuridica.getStrRuc());
			log.info("intNumTrabajadores: "+perJuridica.getJuridica().getIntNroTrabajadores());
			log.info("intCondContribuyente: "+perJuridica.getJuridica().getIntCondContribuyente());
			log.info("intTipoContribuyente: "+perJuridica.getJuridica().getIntTipoContribuyenteCod());
			log.info("intSistemaContable: "+perJuridica.getJuridica().getIntSistemaContable());
			log.info("intEmisionComprobante: "+perJuridica.getJuridica().getIntEmisionComprobante());
			log.info("intComercioExterior: "+perJuridica.getJuridica().getIntComercioExterior());
			log.info("intTipoPersona: "+perJuridica.getIntTipoPersonaCod());
			perJuridica.getJuridica().setDtFechaInscripcion(perJuridica.getJuridica().getDtFechaInscripcion()!=null?perJuridica.getJuridica().getDtFechaInscripcion():null);
		}else{
			perJuridica = new Persona();
			perJuridica.setJuridica(new Juridica());
			setMessageError("No existe persona jurídica con Ruc: "+getStrTxtRuc());
		}
		
		if(perJuridica.getListaPersona()!=null){
			//Obteniendo Representantes Legales
			log.info("perJuridica.listaPersonaNatural.size(): "+perJuridica.getListaPersona().size());
			for(int i=0; i<perJuridica.getListaPersona().size(); i++){
				Persona perNatural = perJuridica.getListaPersona().get(i);
				perNatural.getNatural().setStrNombreCompleto(perNatural.getNatural().getStrNombres()+" "+
															 perNatural.getNatural().getStrApellidoPaterno()+" "+
															 perNatural.getNatural().getStrApellidoMaterno());
			}
		}
		
		if(perJuridica.getPersonaEmpresa().getListaPersonaRol()!=null){
			//Obteniedo Roles de Persona Juridica
			strRoles = "";
			log.info("perJuridica.personaEmpresa.listaPersonaRol.size: "+perJuridica.getPersonaEmpresa().getListaPersonaRol().size());
			List<PersonaRol> listPersonaRol =  perJuridica.getPersonaEmpresa().getListaPersonaRol();
			PersonaRol personaRol = null;
			for(int i=0; i<listPersonaRol.size(); i++){
				personaRol = listPersonaRol.get(i);
				log.info("personaRol.tabla.strDescripcion: "+personaRol.getTabla().getStrDescripcion());
				strRoles = strRoles+personaRol.getTabla().getStrDescripcion()+",";
			}
			if(strRoles.length()>0){
				strRoles=strRoles.substring(0,strRoles.length()-1);
			}
			log.info("strRoles: "+strRoles);
		}
		
		setPerJuridica(perJuridica);
		return perJuridica;
	}
	
	public void addPerJuridicaEstruc(ActionEvent event){
		log.info("-------------------------------------Debugging addPerJuridicaEstruc-------------------------------------");
		log.info("intIdPersona: "+getPerJuridica().getIntIdPersona());
		log.info("strRazonSocial: "+getPerJuridica().getJuridica().getStrRazonSocial());
		log.info("id btnAgregarPerJuri: "+getStrIdBtnAgregarPerJuri());
		Persona beanPerJuri = new Persona();
		beanPerJuri.setIntIdPersona(getPerJuridica().getIntIdPersona());
		beanPerJuri.getJuridica().setStrRazonSocial(getPerJuridica().getJuridica().getStrRazonSocial());
		if(getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN1")){
			setBeanPerJuridicaN1(beanPerJuri);
		}else if (getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN2")){
			setBeanPerJuridicaN2(beanPerJuri);
		}else if (getStrIdBtnAgregarPerJuri().equals("btnAgregarPerJuriN3")){
			setBeanPerJuridicaN3(beanPerJuri);
		}
		//Limpiar bean perJuridica
		Persona juri = new Persona();
		setPerJuridica(juri);
		setStrTxtRuc(null);
		log.info("strRazonSocial N1: "+getBeanPerJuridicaN1().getJuridica().getStrRazonSocial());
		log.info("strRazonSocial N2: "+getBeanPerJuridicaN2().getJuridica().getStrRazonSocial());
	}
	
	public void setIdBtnAgregarPerJuri(ActionEvent event){//En el formulario de Estructura
		log.info("-------------------------------------Debugging setIdBtnAgregarPerJuri-------------------------------------");
		UIComponent uicomp = event.getComponent();
		String idcomp = uicomp.getId();
		log.info("uicomp.getId: "+uicomp.getId());
		setStrIdBtnAgregarPerJuri(idcomp);
		
		cleanPerJuridicaEstructura();
		
		if(idcomp.equals("btnAgregarPerJuriN2")){
			//Valida que se haya seleccionado una Institución para esta Unidad Ejecutora
			EstructuraOrganicaController estructura = (EstructuraOrganicaController)getSessionBean("estrucOrgController");
			if(estructura.getBeanUniEjecu().getIntIdCodigoRel()==null || estructura.getBeanUniEjecu().getIntIdCodigoRel().equals(0)){
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setWarningMessage("Primero debe seleccionar una Institución.");
				return;
			}
		}else if(idcomp.equals("btnAgregarPerJuriN3")){
			//Valida que se haya seleccionado una Institución para esta Unidad Ejecutora
			EstructuraOrganicaController estructura = (EstructuraOrganicaController)getSessionBean("estrucOrgController");
			if(estructura.getBeanDepen().getIntIdCodigoRel()==null || estructura.getBeanDepen().getIntIdCodigoRel().equals(0)){
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setWarningMessage("Primero debe seleccionar una Unidad Ejecutora.");
				return;
			}
		}
	}
	
	//-----------------------------------------------------------------------------------------------------------------------------------
	//Metodos para listar temporalmente
	//-----------------------------------------------------------------------------------------------------------------------------------
	public void verPerJuridicaSocio(ActionEvent event){
		log.info("-------------------------------------Debugging JuridicaController.verPerJuridica-------------------------------------");
		String rowKey = getRequestParameter("rowKeyEmpresaSocio");
		verPerJuridica(listPerJuridicaSocioNatu, rowKey);
	}
	
	public void verPerJuridica(List<Persona> listPerJuridica, String rowKey){
		log.info("-------------------------------------Debugging JuridicaController.verPerJuridica-------------------------------------");
	    Persona perJuridica = null;
		
	    if(listPerJuridica!=null){
	    	for(int i=0; i<listPerJuridica.size(); i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					perJuridica = listPerJuridica.get(i);
				}
			}
	    }
		
		setPerJuridica(perJuridica);
		
		//Obteniendo las Actividades Económicas
		ActividadEconomicaController actEconomicaController = (ActividadEconomicaController)getSessionBean("actEconomicaController");
		actEconomicaController.setListActiEcoSocioNatuEmp(perJuridica.getJuridica().getListaActividadEconomica());
		
		//Obteniendo los Tipos de Comprobante
		TipoComprobanteController tipoComprobanteController = (TipoComprobanteController)getSessionBean("tipoComprobanteController");
		tipoComprobanteController.setListTipoComprobSocioNatuEmp(perJuridica.getJuridica().getListaTipoComprobante());
		
		//Obteniendo las direcciones
		ArrayList<Domicilio> listDirecciones = new ArrayList<Domicilio>();
		for(int i=0; i<perJuridica.getListaDomicilio().size(); i++){
			Domicilio domi = (Domicilio) perJuridica.getListaDomicilio().get(i);
			listDirecciones.add(domi);
		}
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		domicilioController.setListDirecSocioNatuEmp(listDirecciones);
		
		//Obteniendo las comunicaciones
		ArrayList<Comunicacion> listComunica = new ArrayList<Comunicacion>();
		//Obteniendo Comunicaciones
		for(int i=0; i<perJuridica.getListaComunicacion().size(); i++){
			Comunicacion comunica = (Comunicacion) perJuridica.getListaComunicacion().get(i);
			comunica.setStrComunicacion("Tipo Línea: "+ (comunica.getIntTipoComunicacionCod() == 1 ? "Claro" : 
										comunica.getIntTipoComunicacionCod() == 2 ? "Fijo" : 
										comunica.getIntTipoComunicacionCod() == 3 ? "Movistar" :
										comunica.getIntTipoComunicacionCod() == 4 ? "Nextel" :"") + 
										" - Nro.: " + comunica.getIntNumero());
			listComunica.add(comunica);
		}
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		comunicacionController.setListComuniSocioNatuEmp(listComunica);
		
		//Obteniendo las Cuentas Bancarias
		CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		ctaBancariaController.setListCtaBanSocioNatuEmp(perJuridica.getListaCuentaBancaria());
	}
	
	public void quitarPerJuridicaSocio(ActionEvent event){
		log.info("-------------------------------------Debugging quitarPerJuridicaSocio-------------------------------------");
		String rowKey = getRequestParameter("rowKeyEmpresaSocio");
		
		quitarPerJuridica(listPerJuridicaSocioNatu, rowKey);
		log.info("listComuniSocioNatu.size: "+listPerJuridicaSocioNatu.size());
	}
	
	public void quitarPerJuridica(List<Persona> listPerJuridica, String rowKey){
		log.info("-------------------------------------Debugging quitarPerJuridica-------------------------------------");
		
		Persona perTmp = null;
		if(listPerJuridica!=null){
			for(int i=0; i<listPerJuridica.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Persona perJuridica = listPerJuridica.get(i);
					log.info("perJuridica.intIdPersona: "+perJuridica.getIntIdPersona());
					if(perJuridica.getIntIdPersona()!=null && perJuridica.getIntIdPersona()!=null){
						perTmp = listPerJuridica.get(i);
						perTmp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					listPerJuridica.remove(i);
					break;
				}
			}
			if(perTmp!=null && perTmp.getIntIdPersona()!=null){
				listPerJuridica.add(perTmp);
			}
		}
	}
	
	//Metodos Utilitarios
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		return sesion.getAttribute(beanName);
	}
	
	protected void setMessageError(String clientId, String error) {
		FacesContext.getCurrentInstance().addMessage(clientId,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, error, error));
	}

	protected void setMessageError(Exception e) {
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e
						.getLocalizedMessage()));
	}

	protected void setMessageError(String error) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, error, error));
	}
	
	protected void setMessageSuccess(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(msg));
	}
	protected void setMessageSuccess(String zone, String msg) {
		FacesContext.getCurrentInstance().addMessage(zone,
				new FacesMessage(msg));
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
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
	public Persona getPerJuridica() {
		return perJuridica;
	}
	public void setPerJuridica(Persona perJuridica) {
		this.perJuridica = perJuridica;
	}
	public ActividadEconomica getActEconomica() {
		return actEconomica;
	}
	public void setActEconomica(ActividadEconomica actEconomica) {
		this.actEconomica = actEconomica;
	}
	public String getStrTxtRuc() {
		return strTxtRuc;
	}
	public void setStrTxtRuc(String strTxtRuc) {
		this.strTxtRuc = strTxtRuc;
	}
	public String getStrIdBtnAgregarPerJuri() {
		return strIdBtnAgregarPerJuri;
	}
	public void setStrIdBtnAgregarPerJuri(String strIdBtnAgregarPerJuri) {
		this.strIdBtnAgregarPerJuri = strIdBtnAgregarPerJuri;
	}
	public String getIdModalPanel() {
		return idModalPanel;
	}
	public void setIdModalPanel(String idModalPanel) {
		this.idModalPanel = idModalPanel;
	}
	public Persona getBeanPerJuridicaN1() {
		return beanPerJuridicaN1;
	}
	public void setBeanPerJuridicaN1(Persona beanPerJuridicaN1) {
		this.beanPerJuridicaN1 = beanPerJuridicaN1;
	}
	public Persona getBeanPerJuridicaN2() {
		return beanPerJuridicaN2;
	}
	public void setBeanPerJuridicaN2(Persona beanPerJuridicaN2) {
		this.beanPerJuridicaN2 = beanPerJuridicaN2;
	}
	public Persona getBeanPerJuridicaN3() {
		return beanPerJuridicaN3;
	}
	public void setBeanPerJuridicaN3(Persona beanPerJuridicaN3) {
		this.beanPerJuridicaN3 = beanPerJuridicaN3;
	}
	public String getStrRoles() {
		return strRoles;
	}
	public void setStrRoles(String strRoles) {
		this.strRoles = strRoles;
	}
	public Boolean getIsJuridicaSaved() {
		return isJuridicaSaved;
	}
	public void setIsJuridicaSaved(Boolean isJuridicaSaved) {
		this.isJuridicaSaved = isJuridicaSaved;
	}
	//Formulario EmpresaSocio
	public List<Persona> getListPerJuridicaSocioNatu() {
		return listPerJuridicaSocioNatu;
	}
	public void setListPerJuridicaSocioNatu(List<Persona> listPerJuridicaSocioNatu) {
		this.listPerJuridicaSocioNatu = listPerJuridicaSocioNatu;
	}
	public String getStrCallingFormId() {
		return strCallingFormId;
	}
	public void setStrCallingFormId(String strCallingFormId) {
		this.strCallingFormId = strCallingFormId;
	}
	public String getStrFormIdSocioNatu() {
		return strFormIdSocioNatu;
	}
	public void setStrFormIdSocioNatu(String strFormIdSocioNatu) {
		this.strFormIdSocioNatu = strFormIdSocioNatu;
	}
}
