package pe.com.tumi.tesoreria.popup;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Natural;
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
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.message.MessageController;
import pe.com.tumi.tesoreria.parametro.controller.BancoFondoController;

public class JuridicaController {

	protected   static Logger 		    	log = Logger.getLogger(JuridicaController.class);
	private 	Persona						perJuridica = null;
	private 	String						strTxtRuc;
	private 	String 						strRoles;
	private 	Boolean 					isJuridicaSaved;
	private 	String 						strCallingFormId;
	private 	String 						strFormIdSocioNatu;
	private 	String 						strFormIdBanco;
	private 	Integer						intCboTipoRelPerJuri;
	private 	Integer 					intCboTiposPersona;
	private 	Integer 					intCboTiposDocumento;
	private 	Integer						intCboEstados;
	private 	Integer 					intCboCondiContri;
	private 	Integer 					intCboTipoContri;
	private 	Integer 					intCboTipoContabilidad;
	private 	Integer 					intCboTipoEmisionComprobante;
	private 	Integer 					intCboComercioExterior;
	
	//Atributos de sesión
	private 	Integer 					SESION_IDEMPRESA;	
	
	private 	List<Ubigeo>				listaUbigeoDepartamento = new ArrayList<Ubigeo>();
	private 	List<Ubigeo>				listaUbigeoProvincia = new ArrayList<Ubigeo>();
	private 	List<Ubigeo>				listaUbigeoDistrito = new ArrayList<Ubigeo>();
	//
	private		List<Tabla>					listaTablaBancos;
	private		List<Tabla>					listaTablaTipoCtaBancaria;
	private		List<Tabla>					listaTablaTipoMoneda;
	
	public JuridicaController(){
		perJuridica = new Persona();
		perJuridica.setJuridica(new Juridica());
		
		intCboTiposPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;		
		intCboTipoRelPerJuri = 1;
		intCboTiposPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
		intCboTiposDocumento = 4;		
		isJuridicaSaved = false;
		strFormIdSocioNatu = "frmSocioNatural";
		strFormIdBanco = "frmBancoFondo";
		
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
	
	
	public Persona getDetallePerProveedor(Persona perjuri){
		
		//Seteando list de Ctas Bancarias de Persona Juridica
		CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		List<CuentaBancaria> arrayCtas = ctaBancariaController.getListCtaBancariaProveedor();
		if(arrayCtas!=null && arrayCtas.size()>0){
			//log.info("arrayCtas.size: "+arrayCtas.size());
			perjuri.setListaCuentaBancaria(new ArrayList<CuentaBancaria>());
			for(CuentaBancaria ctaBan : arrayCtas){
				perjuri.getListaCuentaBancaria().add(ctaBan);
			}
		}
		
		//Seteando list de Direcciones de Persona Juridica
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		List<Domicilio> arrayDomi = domicilioController.getBeanListDirecProveedor();
		if(arrayDomi!=null && arrayDomi.size()>0){
			//log.info("arrayDomi.size: "+arrayDomi.size());
			perjuri.setListaDomicilio(new ArrayList<Domicilio>());
			for(Domicilio domi : arrayDomi){
				perjuri.getListaDomicilio().add(domi);
			}
		}
		
		//Seteando list de Comunicaciones de Persona Juridica
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		List<Comunicacion> arrayComunica = comunicacionController.getBeanListComuniProveedor();
		if(arrayComunica!=null && arrayComunica.size()>0){
			//log.info("arrayComunica.size: "+arrayComunica.size());
			perjuri.setListaComunicacion(new ArrayList<Comunicacion>());
			for(Comunicacion comunica : arrayComunica){
				perjuri.getListaComunicacion().add(comunica);
			}
		}
		
		//Seteando lista de Actividades Económicas de Persona Juridica
		ActividadEconomicaController actEconomicaController = (ActividadEconomicaController)getSessionBean("actEconomicaController");
		List<ActividadEconomica> arrayActEconomica = actEconomicaController.getListActEconomicaProveedor();
		if(arrayActEconomica!=null && arrayActEconomica.size()>0){
			//log.info("arrayActEconomica.size: "+arrayActEconomica.size());
			perjuri.getJuridica().setListaActividadEconomica(new ArrayList<ActividadEconomica>());
			for(ActividadEconomica actEco : arrayActEconomica){
				perjuri.getJuridica().getListaActividadEconomica().add(actEco);
			}
		}
		
		//Seteando lista de Tipo de Comprobante de Persona Juridica
		TipoComprobanteController tipoComprobanteController = (TipoComprobanteController)getSessionBean("tipoComprobanteController");
		List<TipoComprobante> arrayComprobante = tipoComprobanteController.getListComprobanteProveedor();
		if(arrayComprobante!=null && arrayComprobante.size()>0){
			//log.info("arrayComprobante.size: "+arrayComprobante.size());
			perjuri.getJuridica().setListaTipoComprobante(new ArrayList<TipoComprobante>());
			for(TipoComprobante comprobante : arrayComprobante){
				perjuri.getJuridica().getListaTipoComprobante().add(comprobante);
			}
		}
		return perjuri;
	}
	
	private void logPersona(Persona persona) throws Exception{
		log.info(persona);
		log.info(persona.getNatural());
		log.info(persona.getPersonaEmpresa());
		TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		List<Tabla> lstRoles = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOROL));
		if(persona.getPersonaEmpresa()!=null){
			if(persona.getPersonaEmpresa().getListaPersonaRol()!=null){
				strRoles = "";
				for(PersonaRol personaRol : persona.getPersonaEmpresa().getListaPersonaRol()) {
					for (Tabla tabla : lstRoles) {
						if (tabla.getIntIdDetalle().equals(personaRol.getId().getIntParaRolPk())) {
							strRoles = (strRoles.trim().equalsIgnoreCase("")?(strRoles.trim()+" / "):"") + tabla.getStrDescripcion();
							log.info(personaRol);
						}
					}
				}
			}
			else
				log.info("listaPersonaRol:null");
		}
		for(Documento documento : persona.getListaDocumento())
			log.info(documento);		
	}
	
	private String obtenerDNI(Persona persona)throws Exception{
		String strDNI = null;
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				strDNI = documento.getStrNumeroIdentidad();
				break;
			}
		}
		//log.info("pers:"+persona.getIntIdPersona()+" dni:"+strDNI);
		return strDNI;
	}
	
	private List<Persona> manejarDuplicidadPersona(List<Persona> listaPersona) throws Exception{
		for(Persona persona : listaPersona)
			obtenerDNI(persona);
		
		List<Persona> listaRemover = new ArrayList<Persona>();
		
		for(int i=0;i<listaPersona.size();i++){
			Persona personaA = listaPersona.get(i);
			for(int j=i+1;j<listaPersona.size();j++){
				Persona personaB = listaPersona.get(j);
				if(i!=j && obtenerDNI(personaA).equals(obtenerDNI(personaB))){
					log.info("duplicidad:"+i+" "+j);
					logPersona(personaA);
					logPersona(personaB);
					//intDuplicidad = i;
					listaRemover.add(personaA);
				}
			}
		}
		if(!listaRemover.isEmpty()){
			for(Persona personaRemover : listaRemover){
				log.info(personaRemover);
				listaPersona.remove(personaRemover);
			}
		}
		
		for(Persona persona : listaPersona){
			obtenerDNI(persona);
		}
		return listaPersona;
	}
	
	public Persona savePerJuridicaProveedor() throws Exception {
		//log.info("-------------------------------------Debugging savePerJuridicaEstructura-------------------------------------");
		Persona perjuri = new Persona();
		perjuri.setJuridica(new Juridica());
		perjuri = getDetallePerProveedor(perjuri);
		//Seteando lista de Representantes Legales y Contactos de Persona Juridica
		NaturalController naturalController = (NaturalController)getSessionBean("naturalController");
		List<Persona> arrayRepLegal = naturalController.getBeanListRepLegal();
		List<Persona> arrayContacto = naturalController.getBeanListContactoNatu();
		
		ArrayList<Persona> arrayPersona = new ArrayList<Persona>();
		
		//log.info("SESION_IDEMPRESA:"+SESION_IDEMPRESA);
		if(arrayRepLegal!=null){
			for(Persona replegal : arrayRepLegal){
				//añadirRolRepresentanteLegal(replegal);
				logPersona(replegal);
				arrayPersona.add(replegal);
			}
		}
		if(arrayContacto!=null){
			for(Persona contacto : arrayContacto){
				logPersona(contacto);
				arrayPersona.add(contacto);
			}
		}
		perjuri.setListaPersona(arrayPersona);
		//ValidarDuplicidadPersona
		perjuri.setListaPersona(manejarDuplicidadPersona(perjuri.getListaPersona()));
		
		//Grabar la Persona Juridica
		perjuri = grabarPerJuridica(perjuri);
		
		//Agregar al bean de Persona Juridica
		if(strCallingFormId!=null){
			if(strCallingFormId.equals(strFormIdBanco)){
				BancoFondoController bancoFondoController = (BancoFondoController)getSessionBean("bancoFondoController");
				Bancofondo banco = bancoFondoController.getBancoNuevo();
				banco.setPersonaEmpresa(perjuri.getPersonaEmpresa());
				banco.getPersonaEmpresa().setPersona(perjuri);
				banco.setIntEmpresabancoPk(perjuri.getPersonaEmpresa().getId().getIntIdEmpresa());
				banco.setIntPersonabancoPk(perjuri.getIntIdPersona());
			}
		}
		
		//Limpiar list de Representantes Legales y Contactos
		//naturalController.setBeanListRepLegal(null);
		//naturalController.setBeanListContactoNatu(null);
		
		perJuridica.setIntIdPersona(perjuri.getIntIdPersona());
		
		return perJuridica;
	}
	
	public Persona grabarPerJuridica(Persona personaJuridica) throws BusinessException,Exception {
		
		Persona persona = new Persona();
		persona.setJuridica(new Juridica());
		persona.setPersonaEmpresa(new PersonaEmpresa());
		persona.getPersonaEmpresa().setId(new PersonaEmpresaPK());
		persona.getPersonaEmpresa().setVinculo(new Vinculo());
		
		//Parametros de Persona
		persona.setIntIdPersona(getPerJuridica().getIntIdPersona());
		persona.setStrRuc(getPerJuridica().getStrRuc());
		persona.setIntTipoPersonaCod(getPerJuridica().getIntTipoPersonaCod());
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
		if(personaJuridica.getPersonaEmpresa()==null || personaJuridica.getPersonaEmpresa().getId()==null
				|| personaJuridica.getPersonaEmpresa().getId().getIntIdEmpresa()==null){
			if(personaJuridica.getPersonaEmpresa()==null){
				personaJuridica.setPersonaEmpresa(new PersonaEmpresa());
			}
			if(personaJuridica.getPersonaEmpresa().getId()==null){
				personaJuridica.getPersonaEmpresa().setId(new PersonaEmpresaPK());
			}
			personaJuridica.getPersonaEmpresa().getId().setIntIdEmpresa(SESION_IDEMPRESA);
			persona.setPersonaEmpresa(personaJuridica.getPersonaEmpresa());
		}
		
		//Seteando su detalle
		persona.setListaCuentaBancaria(personaJuridica.getListaCuentaBancaria());
		persona.setListaDomicilio(personaJuridica.getListaDomicilio());
		persona.setListaComunicacion(personaJuridica.getListaComunicacion());
		persona.setListaPersona(personaJuridica.getListaPersona());
		persona.getJuridica().setListaActividadEconomica(personaJuridica.getJuridica().getListaActividadEconomica());
		persona.getJuridica().setListaTipoComprobante(personaJuridica.getJuridica().getListaTipoComprobante());
		
		
		for(Persona per : persona.getListaPersona()){
			obtenerDNI(per);
			if(per.getPersonaEmpresa()!=null){
				if(per.getPersonaEmpresa().getListaVinculo() !=null){
					log.info("listaVinculo:"+per.getPersonaEmpresa().getListaVinculo().size());
					for(Vinculo vinculo : per.getPersonaEmpresa().getListaVinculo()){
						log.info(vinculo);
					}
				}else{
					log.info("listaVinculo:null");
				}
			}else{
				log.info("personaEmpresa:null");
			}
		}
		
		try {
			//Mensaje de Error que sólo se mostrará si no se graba correctamente. 
			isJuridicaSaved = false;
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setErrorMessage("Ocurrió un error de validación al guardar Persona Jurídica. Verifique y corrija.");
			
			//Graba la persona jurídica
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			//log.info("persona.intIdPersona: "+persona.getIntIdPersona());
			if(persona.getIntIdPersona()==null){
				persona = personaFacade.grabarPersonaJuridicaTotal(persona);
			}else{
				persona = personaFacade.modificarPersonaJuridicaTotal(persona);
			}
			if(persona.getIntIdPersona()!=null){
    			isJuridicaSaved = true; //Ya no muestra el mensaje de Error
    			setMessageSuccess("Se grabó satisfactoriamente Persona Jurídica.");
    		}
    			
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}		
		return persona;
	}


	
	public Persona validarProveedor(Integer intTipoPersona, String strTextoFiltro) throws Exception {
		perJuridica = null;
		//
		String strDescDpto = "";
		String strDescProvincia = "";
		String strDescDistrito = "";
		String strDescTipoVia = "";
		String strDescTipoZona = "";
		List<Tabla> lstTablaTipoVia;
		List<Tabla> lstTablaTipoZona;
		TablaFacadeRemote tablaFacade;
		//
		String strDescBanco = "";
		String strDescTipoCuenta = "";
		String strDescTipoMoneda = "";
		//
		try{
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			lstTablaTipoVia = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOVIA));
			lstTablaTipoZona = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOZONA));
			listaTablaBancos = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_BANCOS));
			listaTablaTipoCtaBancaria = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTABANCARIA));
			listaTablaTipoMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			
			perJuridica = validarPersona(intTipoPersona, strTextoFiltro);
			//perjuri = validarPerJuridicayNatural(intTipoPersona, strTextoFiltro);
			
			ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
			if(perJuridica.getListaComunicacion()!=null){
				comunicacionController.setBeanListComuniProveedor(perJuridica.getListaComunicacion());
			}else{
				comunicacionController.setBeanListComuniProveedor(new ArrayList<Comunicacion>());
			}
			
			DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
			if(perJuridica.getListaDomicilio()!=null){
				domicilioController.setBeanListDirecProveedor(perJuridica.getListaDomicilio());
			}else{
				domicilioController.setBeanListDirecProveedor(new ArrayList<Domicilio>());
			}
			if (perJuridica.getListaDomicilio()!=null && !perJuridica.getListaDomicilio().isEmpty()) {
				for (Domicilio o : perJuridica.getListaDomicilio()) {
					GeneralFacadeRemote remote = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
				    listaUbigeoDepartamento = remote.getListaUbigeoDeDepartamento();
				    listaUbigeoProvincia = remote.getListaUbigeoDeProvinciaPorIdUbigeo(o.getIntParaUbigeoPkProvincia());
				    listaUbigeoDistrito = remote.getListaUbigeoDeDistritoPorIdUbigeo(o.getIntParaUbigeoPkDistrito());
				    
				      //Descripcion Tipo de Via
				      for (Tabla tabla : lstTablaTipoVia) {
				    	  if (tabla.getIntIdDetalle().equals(o.getIntTipoViaCod())) {
				    		  strDescTipoVia = tabla.getStrAbreviatura();
				    		  break;
				    	  }
				      }
				      //Descripcion Departamento
				      for (Ubigeo dpto : listaUbigeoDepartamento) {
				    	  if (dpto.getIntIdUbigeo().equals(o.getIntParaUbigeoPkDpto())) {
				    		  strDescDpto = dpto.getStrDescripcion();
				    		  break;
				    	  }
				      }
				      //Descripcion Provincia
				      for (Ubigeo prov : listaUbigeoProvincia) {
				    	  if (prov.getIntIdUbigeo().equals(o.getIntParaUbigeoPkProvincia())) {
				    		  strDescProvincia = prov.getStrDescripcion();
				    		  break;
				    	  }
				      }
				      //Descripcion Distrito
				      for (Ubigeo dist : listaUbigeoDistrito) {
				    	  if (dist.getIntIdUbigeo().equals(o.getIntParaUbigeoPkDistrito())) {
				    		  strDescDistrito = dist.getStrDescripcion();
				    		  break;
				    	  }
				      }
				      
				      //Descripcion Tipo de Zona
				      for (Tabla tabla : lstTablaTipoZona) {
				    	  if (tabla.getIntIdDetalle().equals(o.getIntTipoZonaCod())) {
				    		  strDescTipoZona = tabla.getStrAbreviatura();
				    		  break;
				    	  }
				      }
				      if(o.getStrInterior()==null) o.setStrInterior("");
				      String direccion = (strDescTipoVia + ". " + o.getStrNombreVia()+ " Nro. "+ o.getIntNumeroVia() + " "+ 
				    		  (o.getStrInterior().trim()!=""?(" - Int. "+o.getStrInterior()+" "):"") +
				    		  (strDescTipoZona!=null?strDescTipoZona:"") + ". "+(o.getStrNombreZona()!=null?o.getStrNombreZona():"")+
				    		  " - Referencia: "+ o.getStrReferencia())
				    		  			+" - "+ strDescDpto
				    		  			+" - "+ strDescProvincia
				    		  			+" - "+ strDescDistrito;
//				      String direccion = (dom.getStrNombreVia()+ " Nº "+ dom.getIntNumeroVia() + " "+ 
//				    		  			dom.getStrNombreZona()+" / Referencia: "+ dom.getStrReferencia());
				      log.info("dom.getIntParaUbigeoPk(): "+ o.getIntParaUbigeoPk());
				      log.info("dom.getIntParaUbigeoPkDpto(): "+ o.getIntParaUbigeoPkDpto());
				      log.info("dom.getIntParaUbigeoPkProvincia(): "+ o.getIntParaUbigeoPkProvincia());
				      log.info("dom.getIntParaUbigeoPkDistrito(): "+ o.getIntParaUbigeoPkDistrito());
				      o.setStrDireccion(direccion);
				}
			}
			
			CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
			if(perJuridica.getListaCuentaBancaria()!=null){
				ctaBancariaController.setListCtaBancariaProveedor(perJuridica.getListaCuentaBancaria());
			}else{
				ctaBancariaController.setListCtaBancariaProveedor(new ArrayList<CuentaBancaria>());
			}
			if (ctaBancariaController.getListCtaBancariaProveedor()!=null && !ctaBancariaController.getListCtaBancariaProveedor().isEmpty()) {
				for (CuentaBancaria o : ctaBancariaController.getListCtaBancariaProveedor()) {
					//Descripcion Banco
					for (Tabla tabla : listaTablaBancos) {
						if (tabla.getIntIdDetalle().equals(o.getIntBancoCod())) {
							strDescBanco = tabla.getStrDescripcion();
							break;
						}
				    }
					//Descripcion Tipo Cuenta
					for (Tabla tabla : listaTablaTipoCtaBancaria) {
						if (tabla.getIntIdDetalle().equals(o.getIntTipoCuentaCod())) {
							strDescTipoCuenta = tabla.getStrDescripcion();
							break;
						}
				    }
					//Descripcion Tipo Moneda
					for (Tabla tabla : listaTablaTipoMoneda) {
						if (tabla.getIntIdDetalle().equals(o.getIntMonedaCod())) {
							strDescTipoMoneda = tabla.getStrDescripcion();
							break;
						}
				    }
					//Concatenamos descripcion Cuenta Bancaria:
					//Nombre de Banco – Tipo de Cuenta – Moneda – Nro. de Cuenta
					o.setStrEtiqueta(strDescBanco +" - "+ strDescTipoCuenta +" - "+ strDescTipoMoneda +" - "+ o.getStrNroCuentaBancaria());
					//Fin jchavez - 01.10.2014
				}
			}
			
			NaturalController naturalController = (NaturalController)getSessionBean("naturalController");
			if(perJuridica.getListaPersona()!=null){
				//Obteniendo Representantes Legales
				ArrayList<Persona> arrayReplegal = new ArrayList<Persona>();
				ArrayList<Persona> arrayContacto = new ArrayList<Persona>();				
				for(Persona per : perJuridica.getListaPersona()){					
					per.setChecked(Boolean.TRUE);//para validar que provienen de bd					
					if(per.getPersonaEmpresa().getVinculo().getIntTipoVinculoCod().equals(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL))
						arrayReplegal.add(per);					
					if(per.getPersonaEmpresa().getVinculo().getIntTipoVinculoCod().equals(Constante.PARAM_T_TIPOVINCULO_CONTACTO))
						arrayContacto.add(per);					
				}
				naturalController.setBeanListRepLegal(arrayReplegal);
				naturalController.setBeanListContactoNatu(arrayContacto);
			}else{
				naturalController.setBeanListRepLegal(new ArrayList<Persona>());
				naturalController.setBeanListContactoNatu(new ArrayList<Persona>());
			}
			
			ActividadEconomicaController actEconomicaController = (ActividadEconomicaController)getSessionBean("actEconomicaController");
			if(perJuridica.getJuridica().getListaActividadEconomica()!=null){
				actEconomicaController.setListActEconomicaProveedor(perJuridica.getJuridica().getListaActividadEconomica());
			}else{
				actEconomicaController.setListActEconomicaProveedor(new ArrayList<ActividadEconomica>());
			}
			
			TipoComprobanteController tipoComprobanteController = (TipoComprobanteController)getSessionBean("tipoComprobanteController");
			if(perJuridica.getJuridica().getListaTipoComprobante()!=null){
				List<TipoComprobante> listaTipoComprobante = new ArrayList<TipoComprobante>();
				for(TipoComprobante tipoComprobante : perJuridica.getJuridica().getListaTipoComprobante()){
					if(tipoComprobante.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
						listaTipoComprobante.add(tipoComprobante);
					}
				}
				tipoComprobanteController.setListComprobanteProveedor(perJuridica.getJuridica().getListaTipoComprobante());
			}else{
				tipoComprobanteController.setListComprobanteProveedor(new ArrayList<TipoComprobante>());
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;			
		}
		return perJuridica;
	}
	
	public Persona validarPersona(Integer intTipoPersona, String strTextoFiltro) throws Exception {
		Persona persona = null;
		try {
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			Integer intTipoBusqueda = null;
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL))	intTipoBusqueda = Constante.PARAM_T_OPCIONPERSONABUSQ_DNI;
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA))	intTipoBusqueda = Constante.PARAM_T_OPCIONPERSONABUSQ_RUC;
			
			List<Persona> listaPersona = personaFacade.buscarListaPersonaParaFiltro(intTipoBusqueda, strTextoFiltro);
			log.info(listaPersona);
			if(listaPersona!=null && !listaPersona.isEmpty()){
				persona = listaPersona.get(0);
				log.info(persona);
				persona = personaFacade.getPersonaDetalladaPorIdPersonaYEmpresa(persona.getIntIdPersona(), SESION_IDEMPRESA);
				persona.setNatural(personaFacade.getNaturalDetalladaPorIdPersona(persona.getIntIdPersona()));
				persona.setJuridica(personaFacade.getJuridicaDetalladaPorIdPersona(persona.getIntIdPersona()));
				
				if(persona.getNatural()==null) persona.setNatural(new Natural());
				if(persona.getJuridica()==null)persona.setJuridica(new Juridica());
			}else{
				persona = new Persona();
				persona.setNatural(new Natural());
				persona.setJuridica(new Juridica());
				persona.setIntTipoPersonaCod(intTipoPersona);
				if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA))	persona.setStrRuc(strTextoFiltro);
			}
			strTxtRuc = strTextoFiltro;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw e;
		}
		return persona;
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
	public Integer getIntCboTiposPersona() {
		return intCboTiposPersona;
	}
	public void setIntCboTiposPersona(Integer intCboTiposPersona) {
		this.intCboTiposPersona = intCboTiposPersona;
	}
	public Persona getPerJuridica() {
		return perJuridica;
	}
	public void setPerJuridica(Persona perJuridica) {
		this.perJuridica = perJuridica;
	}
	public String getStrTxtRuc() {
		return strTxtRuc;
	}
	public void setStrTxtRuc(String strTxtRuc) {
		this.strTxtRuc = strTxtRuc;
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
	public String getStrFormIdBanco() {
		return strFormIdBanco;
	}
	public void setStrFormIdBanco(String strFormIdBanco) {
		this.strFormIdBanco = strFormIdBanco;
	}
	public Integer getIntCboTipoRelPerJuri() {
		return intCboTipoRelPerJuri;
	}
	public void setIntCboTipoRelPerJuri(Integer intCboTipoRelPerJuri) {
		this.intCboTipoRelPerJuri = intCboTipoRelPerJuri;
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
}