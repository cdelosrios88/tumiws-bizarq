package pe.com.tumi.credito.popup;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
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
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class NaturalController {
	
	protected   static Logger 		    log = Logger.getLogger(ComunicacionController.class);
	private 	Persona					beanPerNatural = null;
	private 	List<Persona> 			beanListRepLegal = null; //List de Representante Legal
	private 	List<Persona>			beanListContactoNatu = null; //List de Contacto Natural
	private 	Integer 				intVinculoContactoNatu; //Contacto Natural
	private 	Integer 				intCboTipoPersona; //Persona Natural
	private 	Integer 				intCboTipoDoc;
	private 	String 					strNroDocIdentidad;
	private 	Integer 				intIdOtroDocumento;
	private 	String 					strDocIdentidad;
	private 	String 					strRoles;
	
	private 	Integer 				SESION_IDEMPRESA;
	
	
	public NaturalController(){
		intVinculoContactoNatu = Constante.PARAM_T_TIPOVINCULO_CONTACTO; //Representante Legal
		intCboTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL; //Persona Natural
		
		cleanRepLegal();
		
		beanListRepLegal = new ArrayList<Persona>(); 
		
		//atributos de sesión
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
	}
	
	public void validarRepLegal(ActionEvent event) throws ParseException{
		log.info("-------------------------------------Debugging validarRepLegal-------------------------------------");
		Persona replegal = null;
		replegal = validarPerNatural(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL);
		
		if(replegal!=null){
			setBeanPerNatural(replegal);
			getDetalleRepLegal(replegal);
		}
	}
	
	public void getDetalleRepLegal(Persona replegal){
		if(replegal.getListaComunicacion()!=null){
			//Obteniendo Comunicaciones de Representante Legal
			log.info("persona.listaComunicacion.size: "+replegal.getListaComunicacion().size());
			ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
			comunicacionController.setBeanListComuniRepLegal(replegal.getListaComunicacion());
		}
		
		if(replegal.getListaDomicilio()!=null){
			//Obteniendo Direcciones de Representante Legal
			log.info("persona.listaDomicilio.size: "+replegal.getListaDomicilio().size());
			DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
			domicilioController.setBeanListDirecRepLegal(replegal.getListaDomicilio());
		}
	}
	
	public void validarContacto(ActionEvent event) throws ParseException{
		log.info("-------------------------------------Debugging validarContacto-------------------------------------");
		Persona contacto = null;
		contacto = validarPerNatural(Constante.PARAM_T_TIPOVINCULO_CONTACTO);
		
		if(contacto!=null){
			setBeanPerNatural(contacto);
			getDetalleContacto(contacto);
		}
	}
	
	public void getDetalleContacto(Persona contacto){
		if(contacto.getListaComunicacion()!=null){
			//Obteniendo Comunicaciones de Representante Legal
			log.info("persona.listaComunicacion.size: "+contacto.getListaComunicacion().size());
			for(Comunicacion com : contacto.getListaComunicacion()){
				log.info("com.getId().getIntIdComunicacion(): "+com.getId().getIntIdComunicacion());
				log.info("com.getStrComunicacion(): "+com.getStrComunicacion());
				log.info("com.getStrDato(): "+com.getStrDato());
			}
			ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
			comunicacionController.setListComuniContactoNatu(contacto.getListaComunicacion());
		}
	}
	
	public Persona validarPerNatural(Integer pIntTipoVinculo) throws ParseException{
		log.info("-------------------------------------Debugging validarPerNatural-------------------------------------");
		
		log.info("IntCboTipoPersona: "+getIntCboTipoPersona());
		log.info("IntCboTipoDoc: "+getIntCboTipoDoc());
		log.info("strNroDocIdentidad: "+getStrNroDocIdentidad());
		
		Persona persona = null;
		
		try {
    		PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
    		persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(getIntCboTipoDoc(),getStrNroDocIdentidad(),SESION_IDEMPRESA);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if(persona!=null){
			logRepLegalVal(persona); //debug de las propiedades de la persona natural
		}else{
			setMessageError("No existe persona natural con documento: "+getStrNroDocIdentidad());
			return persona;
		}
		
		if(persona.getListaDocumento()!=null){
			//Obteniendo Otros Documentos de Representante Legal
			log.info("persona.listaDocumento.size: "+persona.getListaDocumento().size());
			
			//Separando el dni
			for(int i=0; i<persona.getListaDocumento().size(); i++){
				if(persona.getListaDocumento().get(i).getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
					persona.setDocumento(persona.getListaDocumento().get(i));
					log.info("DNI:"+persona.getListaDocumento().get(i).getStrNumeroIdentidad());
					persona.getListaDocumento().remove(i);
					break;
				}
			}
		}
		
		if(persona.getPersonaEmpresa()!=null && persona.getPersonaEmpresa().getListaPersonaRol()!=null){
			//Obteniedo Roles de Persona Juridica
			strRoles = "";
			log.info("persona.personaEmpresa.listaPersonaRol.size: "+persona.getPersonaEmpresa().getListaPersonaRol().size());
			List<PersonaRol> listPersonaRol = persona.getPersonaEmpresa().getListaPersonaRol();
			PersonaRol personaRol = null;
			for(int i=0; i<listPersonaRol.size(); i++){
				personaRol = listPersonaRol.get(i);
				log.info("personaRol.tabla.strDescripcion: "+personaRol.getTabla().getStrDescripcion());
				strRoles = strRoles+personaRol.getTabla().getStrDescripcion()+",";
			}
			if(strRoles!=null && strRoles.length()>0){
				strRoles=strRoles.substring(0,strRoles.length()-1);
			}
			log.info("strRoles: "+strRoles);
		}
		
		if(persona.getPersonaEmpresa()!=null){
			if(persona.getPersonaEmpresa().getVinculo()==null){
				persona.getPersonaEmpresa().setVinculo(new Vinculo());
			}
			//por default el vinculo es Representante Legal
			persona.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(pIntTipoVinculo);
		}
		
		return persona;
	}
	
	public void logRepLegalVal(Persona pernatu){
		log.info("-------------------------------------Debugging logRepLegalVal-------------------------------------");
		log.info("intIdPersona: "+pernatu.getIntIdPersona());
		log.info("strApePat: "+pernatu.getNatural().getStrApellidoPaterno());
		log.info("strApeMat: "+pernatu.getNatural().getStrApellidoMaterno());
		log.info("strNombres: "+pernatu.getNatural().getStrNombres());
		log.info("strFechaNac: "+pernatu.getNatural().getDtFechaNacimiento());
		log.info("intSexo: "+pernatu.getNatural().getIntSexoCod());
		log.info("intEstadoCivil: "+pernatu.getNatural().getIntEstadoCivilCod());
		log.info("intTipoPersona: "+pernatu.getIntTipoPersonaCod());
//		log.info("strPathFoto: "+pernatu.getNatural().getStrFoto());
//		log.info("strPathFirma: "+pernatu.getNatural().getStrFirma());
		log.info("intEstado: "+pernatu.getIntEstadoCod());
	}
	
	public void addRepLegal(ActionEvent event){
		log.info("-------------------------------------Debugging addRepLegal-------------------------------------");
		Persona replegal = null;
		
		replegal = getPerNatural(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL);
		
		//Obteniendo lista de direcciones para este Representante Legal
		DomicilioController domicilio = (DomicilioController) getSessionBean("domicilioController");
		ArrayList arrayDomi = (ArrayList) domicilio.getBeanListDirecRepLegal();
		ArrayList listDomi = new ArrayList();
		for(int i=0; i<arrayDomi.size(); i++){
			Domicilio domi = (Domicilio) arrayDomi.get(i);
			listDomi.add(domi);
		}
		log.info("beanListDirecRepLegal.size(): "+listDomi.size());
		replegal.setListaDomicilio(listDomi);
		domicilio.getBeanListDirecRepLegal().clear();
		
		//Obteniendo lista de comunicaciones para este Representante Legal
		ComunicacionController comunicacion = (ComunicacionController) getSessionBean("comunicacionController");
		ArrayList arrayComunicacion = (ArrayList) comunicacion.getBeanListComuniRepLegal();
		ArrayList listComunicacion = new ArrayList();
		for(int i=0; i<arrayComunicacion.size(); i++){
			Comunicacion comunica = (Comunicacion) arrayComunicacion.get(i);
			listComunicacion.add(comunica);
		}
		log.info("beanListComuniRepLegal.size(): "+listComunicacion.size());
		replegal.setListaComunicacion(listComunicacion);
		comunicacion.getBeanListComuniRepLegal().clear();
		
		if(beanListRepLegal==null){
			setBeanListRepLegal(new ArrayList<Persona>());
		}
		
		getBeanListRepLegal().add(replegal);
	}
	
	public void addContactoNatu(ActionEvent event){
		log.info("-------------------------------------Debugging addContactoNatu-------------------------------------");
		Persona contacto = null;
		
		contacto = getPerNatural(intVinculoContactoNatu);
		
		//Obteniendo lista de comunicaciones para este Representante Legal
		ComunicacionController comunicacion = (ComunicacionController) getSessionBean("comunicacionController");
		ArrayList arrayComunicacion = (ArrayList) comunicacion.getListComuniContactoNatu();
		ArrayList listComunicacion = new ArrayList();
		for(int i=0; i<arrayComunicacion.size(); i++){
			Comunicacion comunica = (Comunicacion) arrayComunicacion.get(i);
			listComunicacion.add(comunica);
		}
		log.info("beanListComuniRepLegal.size(): "+listComunicacion.size());
		contacto.setListaComunicacion(listComunicacion);
		comunicacion.getBeanListComuniRepLegal().clear();
		
		if(beanListContactoNatu==null){
			setBeanListContactoNatu(new ArrayList<Persona>());
		}
		getBeanListContactoNatu().add(contacto);
	}
	
	public Persona getPerNatural(Integer pIntTipoVinculo){
		log.info("-------------------------------------Debugging addPerNatural-------------------------------------");
		
		log.info("beanPerNatural: "+getBeanPerNatural());
		log.info("beanPerNatural.natural: "+getBeanPerNatural().getNatural());
		log.info("intIdPersona: "+getBeanPerNatural().getIntIdPersona());
		log.info("strApellidoPaterno: "+getBeanPerNatural().getNatural().getStrApellidoPaterno());
		log.info("strApellidoMaterno: "+getBeanPerNatural().getNatural().getStrApellidoMaterno());
		log.info("strNombres: "+getBeanPerNatural().getNatural().getStrNombres());
		log.info("dtFechaNacimiento: "+getBeanPerNatural().getNatural().getDtFechaNacimiento());
		log.info("strNroIdentidad: "+getBeanPerNatural().getDocumento().getStrNumeroIdentidad());
		log.info("intSexoCod: "+getBeanPerNatural().getNatural().getIntSexoCod());
		log.info("intEstadoCivilCod: "+getBeanPerNatural().getNatural().getIntEstadoCivilCod());
		log.info("intTipoPersonaCod: "+getBeanPerNatural().getIntTipoPersonaCod());
//		log.info("strFirma: "+getBeanRepLegal().getNatural().getStrFirma());
//		log.info("strFoto: "+getBeanRepLegal().getNatural().getStrFoto());
		log.info("intEstadoCod: "+getBeanPerNatural().getIntEstadoCod());
		
		//Seteando datos al bean
		Persona replegal = new Persona();
		replegal.setNatural(new Natural());
		replegal.setPersonaEmpresa(new PersonaEmpresa());
		replegal.getPersonaEmpresa().setId(new PersonaEmpresaPK());
		replegal.getPersonaEmpresa().setVinculo(new Vinculo());
		replegal.setIntIdPersona(getBeanPerNatural().getIntIdPersona());
		replegal.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO); //Activo por default
		replegal.setIntTipoPersonaCod(intCboTipoPersona);
		
		replegal.getNatural().setStrApellidoPaterno(getBeanPerNatural().getNatural().getStrApellidoPaterno());
		replegal.getNatural().setStrApellidoMaterno(getBeanPerNatural().getNatural().getStrApellidoMaterno());
		replegal.getNatural().setStrNombres(getBeanPerNatural().getNatural().getStrNombres());
		replegal.getNatural().setDtFechaNacimiento(getBeanPerNatural().getNatural().getDtFechaNacimiento());
		replegal.getNatural().setIntSexoCod(getBeanPerNatural().getNatural().getIntSexoCod());
		replegal.getNatural().setIntEstadoCivilCod(getBeanPerNatural().getNatural().getIntEstadoCivilCod());
//		replegal.getNatural().setStrFirma(getBeanRepLegal().getNatural().getStrFirma());
//		replegal.getNatural().setStrFoto(getBeanRepLegal().getNatural().getStrFoto());
		//Nombre Completo
		replegal.getNatural().setStrNombreCompleto(replegal.getNatural().getStrNombres()+" "+replegal.getNatural().getStrApellidoPaterno()+" "+replegal.getNatural().getStrApellidoMaterno());
		//Valores por default
		replegal.getPersonaEmpresa().getId().setIntIdEmpresa(SESION_IDEMPRESA);
		replegal.getPersonaEmpresa().getVinculo().setIntIdEmpresa(SESION_IDEMPRESA);
		replegal.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(pIntTipoVinculo);
		replegal.getPersonaEmpresa().getVinculo().setIntEmpresaVinculo(SESION_IDEMPRESA);
		
		
		
		//Obteniendo lista de Otros Documentos para el Representante Legal
		log.info("beanPerNatural.listaDocumento.size: "+getBeanPerNatural().getListaDocumento().size());
		replegal.setListaDocumento(getBeanPerNatural().getListaDocumento());
		
		//agregando el DNI a los documentos de identidad
		if(getBeanPerNatural().getDocumento()!=null && getBeanPerNatural().getDocumento().getId()!=null){
			replegal.getListaDocumento().add(getBeanPerNatural().getDocumento());
		}else{
			Documento dni = new Documento();
			dni.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			dni.setIntTipoIdentidadCod(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI));
			dni.setStrNumeroIdentidad(getBeanPerNatural().getDocumento().getStrNumeroIdentidad());
			replegal.getListaDocumento().add(dni);
		}
		
		return replegal;
	} 
	
	public void nuevoContactoNatu(ActionEvent event){
		log.info("-----------------------------------Debugging nuevoContactoNatu-----------------------------------");
		cleanRepLegal();
		setIntCboTipoDoc(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
		log.info("intCboTipoDoc: "+getIntCboTipoDoc());
		beanPerNatural.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(Constante.PARAM_T_TIPOVINCULO_CONTACTO);
	}
	
	public void nuevoRepLegal(ActionEvent event){
		log.info("-----------------------------------Debugging nuevoRepLegal-----------------------------------");
		cleanRepLegal();
		setIntCboTipoDoc(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
		beanPerNatural.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL);
	}
	
	public void cleanRepLegal(){
		log.info("-----------------------------------Debugging cleanRepLegal-----------------------------------");
		//Limpiar el BeanRepLegal
		Persona cleanRep = new Persona();
		cleanRep.setNatural(new Natural());
		cleanRep.getNatural().setPerLaboral(new PerLaboral());
		cleanRep.setDocumento(new Documento());
		cleanRep.setPersonaEmpresa(new PersonaEmpresa());
		cleanRep.getPersonaEmpresa().setVinculo(new Vinculo());
		cleanRep.setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
		cleanRep.setListaDocumento(new ArrayList<Documento>());
		setBeanPerNatural(cleanRep);
		
		log.info("beanRepLegal: "+getBeanPerNatural());
		log.info("beanRepLegal.natural: "+getBeanPerNatural().getNatural());
		
		//Lmpiando Comunicaciones de Representante Legal
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		if(comunicacionController!=null && comunicacionController.getBeanListComuniRepLegal()!=null){
			comunicacionController.getBeanListComuniRepLegal().clear();
		}
		
		//Limpiando Direcciones de Representante Legal
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		if(domicilioController!=null && domicilioController.getBeanListDirecRepLegal()!=null){
			domicilioController.getBeanListDirecRepLegal().clear();
		}
		
		strRoles = "";
	}
	
	/*
	public void grabarRepLegal(ActionEvent event) {
		log.info("-------------------------------------Debugging grabarRepLegal-------------------------------------");
		
		Natural repLegal = new Natural();
		repLegal = getBeanRepLegal();
		log.info("dtFechaNac: "+repLegal.getDtFechaNacimiento());
		//log.info("strRoles: "+repLegal.getStrRoles());
		
		try {
    		PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
    		repLegal = personaFacade.grabarPersonaNatural(repLegal);
    		log.info("repLegal.getIntIdPersona: "+repLegal.getIntIdPersona());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}*/
	
	public void verRepLegal(ActionEvent event) {
		log.info("-------------------------------------Debugging verRepLegal-------------------------------------");
		log.info("rowKeyRepLegal: "+getRequestParameter("rowKeyRepLegal"));
		String rowKey = getRequestParameter("rowKeyRepLegal");
		
		Persona replegal = new Persona();
		if(beanListRepLegal!=null){
			for(int i=0; i<beanListRepLegal.size();i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					replegal = (Persona) beanListRepLegal.get(i);
				}
			}
		}
		
		logRepLegalVal(replegal); //valores de las propiedades de Representante Legal
		//Separando el dni
		for(int i=0; i<replegal.getListaDocumento().size(); i++){
			if(replegal.getListaDocumento().get(i).getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
				replegal.setDocumento(replegal.getListaDocumento().get(i));
				replegal.getListaDocumento().remove(i);
				break;
			}
		}
		setBeanPerNatural(replegal);
		
		showDetallePerNatural(replegal);
	}
	
	public void verContactoNatu(ActionEvent event) {
		log.info("-------------------------------------Debugging verContactoNatu-------------------------------------");
		log.info("rowKeyContactoNatu: "+getRequestParameter("rowKeyContactoNatu"));
		String rowKey = getRequestParameter("rowKeyContactoNatu");
		
		Persona contacto = new Persona();
		if(beanListContactoNatu!=null){
			for(int i=0; i<beanListContactoNatu.size();i++){
				if(rowKey!=null && Integer.parseInt(rowKey)==i){
					contacto = (Persona) beanListContactoNatu.get(i);
				}
			}
		}
		
		logRepLegalVal(contacto); //valores de las propiedades de Representante Legal
		//Separando el dni
		for(int i=0; i<contacto.getListaDocumento().size(); i++){
			if(contacto.getListaDocumento().get(i).getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
				contacto.setDocumento(contacto.getListaDocumento().get(i));
				contacto.getListaDocumento().remove(i);
				break;
			}
		}
		setBeanPerNatural(contacto);
		
		showDetallePerNatural(contacto);
	}
	
	public void showDetallePerNatural(Persona perNatural){
		log.info("-------------------------------------Debugging showDetallePerNatural-------------------------------------");
		
		//Obteniendo las direcciones
		List<Domicilio> arrayDirecciones = perNatural.getListaDomicilio();
		ArrayList listDirecciones = new ArrayList();
		log.info("arrayDirecciones.size(): "+arrayDirecciones.size());
		for(int i=0; i<arrayDirecciones.size(); i++){
			Domicilio domi = (Domicilio) arrayDirecciones.get(i);
			listDirecciones.add(domi);
		}
		log.info("Representante Legal listDirecciones.size(): "+listDirecciones.size());
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		domicilioController.setBeanListDirecRepLegal(listDirecciones);
		
		//Obteniendo las comunicaciones
		List<Comunicacion> arrayComunica = perNatural.getListaComunicacion();
		ArrayList listComunica = new ArrayList();
		//Obteniendo Comunicaciones
		log.info("arrayComunica.size(): "+arrayComunica.size());
		for(int i=0; i<arrayComunica.size(); i++){
			Comunicacion comunica = (Comunicacion) arrayComunica.get(i);
			comunica.setStrComunicacion("Tipo Línea: "+ (comunica.getIntTipoComunicacionCod() == 1 ? "Claro" : 
										comunica.getIntTipoComunicacionCod() == 2 ? "Fijo" : 
										comunica.getIntTipoComunicacionCod() == 3 ? "Movistar" :
										comunica.getIntTipoComunicacionCod() == 4 ? "Nextel" :"") + 
										" - Nro.: " + comunica.getIntNumero());
			listComunica.add(comunica);
		}
		log.info("Representante Legal listComunica.size(): "+listComunica.size());
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		comunicacionController.setBeanListComuniRepLegal(listComunica);
	}
	
	public void quitarRepLegal(ActionEvent event){
		log.info("-------------------------------------Debugging quitarRepLegal-------------------------------------");
		String rowKey = getRequestParameter("rowKeyRepLegal");
		Persona perTmp = null;
		if(beanListRepLegal!=null){
			for(int i=0; i<beanListRepLegal.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Persona persona = beanListRepLegal.get(i);
					log.info("persona.getIntIdPersona: "+persona.getIntIdPersona());
					if(persona.getIntIdPersona()!=null){
						perTmp = beanListRepLegal.get(i);
						perTmp.getPersonaEmpresa().getVinculo().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					beanListRepLegal.remove(i);
					break;
				}
			}
			if(perTmp!=null){
				beanListRepLegal.add(perTmp);
			}
		}
	}
	
	public void quitarContactoNatu(ActionEvent event){
		log.info("-------------------------------------Debugging quitarContactoNatu-------------------------------------");
		String rowKey = getRequestParameter("rowKeyContactoNatu");
		Persona perTmp = null;
		if(beanListContactoNatu!=null){
			for(int i=0; i<beanListContactoNatu.size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Persona persona = beanListContactoNatu.get(i);
					if(persona.getIntIdPersona()!=null){
						perTmp = beanListContactoNatu.get(i);
						perTmp.getPersonaEmpresa().getVinculo().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					beanListContactoNatu.remove(i);
					break;
				}
			}
			if(perTmp!=null){
				beanListContactoNatu.add(perTmp);
			}
		}
	}
	
	public void addOtroDocumento(ActionEvent event){
		log.info("-------------------------------------Debugging addOtroDocumento-------------------------------------");
		log.info("intIdOtroDocumento: "+getIntIdOtroDocumento());
		log.info("StrDocIdentidad: "+getStrDocIdentidad());
		
		Documento documento = new Documento();
		documento.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		documento.setIntTipoIdentidadCod(getIntIdOtroDocumento());
		documento.setStrNumeroIdentidad(getStrDocIdentidad());
		
		List<Tabla> listTabla = null;
		try {
			TablaFacadeRemote tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO));
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		for(int i=0; i<listTabla.size(); i++){
			log.info("tabla.intIdDetalle: "+listTabla.get(i).getIntIdDetalle());
			log.info("documento.intTipoIdentidadCod: "+documento.getIntTipoIdentidadCod());
			if(listTabla.get(i).getIntIdDetalle().equals(documento.getIntTipoIdentidadCod())){
				log.info("tabla.strDescripcion: "+listTabla.get(i).getStrDescripcion());
				documento.setTabla(listTabla.get(i));
				break;
			}
		}
		
		if(beanPerNatural.getListaDocumento()==null){
			beanPerNatural.setListaDocumento(new ArrayList<Documento>());
		}
		beanPerNatural.getListaDocumento().add(documento);
	}
	
	public void quitarOtrosDocumentos(ActionEvent event){
		log.info("-------------------------------------Debugging quitarOtrosDocumentos-------------------------------------");
		String rowKey = getRequestParameter("rowKeyOtrosDocumentos");
		Documento docTmp = null;
		if(beanPerNatural.getListaDocumento()!=null){
			for(int i=0; i<beanPerNatural.getListaDocumento().size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Documento documento = beanPerNatural.getListaDocumento().get(i);
					log.info("documento.id: "+documento.getId());
					if(documento.getId()!=null && documento.getId().getIntItemDocumento()!=null){
						docTmp = beanPerNatural.getListaDocumento().get(i);
						docTmp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					beanPerNatural.getListaDocumento().remove(i);
					break;
				}
			}
			if(docTmp!=null){
				beanPerNatural.getListaDocumento().add(docTmp);
			}
		}
		log.info("beanPerNatural.listaDocumento.size: "+beanPerNatural.getListaDocumento().size());
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
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	//Getters y Setters
	public Integer getIntVinculoContactoNatu() {
		return intVinculoContactoNatu;
	}
	public void setIntVinculoContactoNatu(Integer intVinculoContactoNatu) {
		this.intVinculoContactoNatu = intVinculoContactoNatu;
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
	/*public List getBeanListRepLegal() {
		return beanListRepLegal;
	}
	public void setBeanListRepLegal(List beanListRepLegal) {
		this.beanListRepLegal = beanListRepLegal;
	}*/
	public String getStrDocIdentidad() {
		return strDocIdentidad;
	}
	public void setStrDocIdentidad(String strDocIdentidad) {
		this.strDocIdentidad = strDocIdentidad;
	}
	public Integer getIntIdOtroDocumento() {
		return intIdOtroDocumento;
	}
	public void setIntIdOtroDocumento(Integer intIdOtroDocumento) {
		this.intIdOtroDocumento = intIdOtroDocumento;
	}
	public String getStrRoles() {
		return strRoles;
	}
	public void setStrRoles(String strRoles) {
		this.strRoles = strRoles;
	}
	public Persona getBeanPerNatural() {
		return beanPerNatural;
	}
	public void setBeanPerNatural(Persona beanPerNatural) {
		this.beanPerNatural = beanPerNatural;
	}
	public List<Persona> getBeanListRepLegal() {
		return beanListRepLegal;
	}
	public void setBeanListRepLegal(List<Persona> beanListRepLegal) {
		this.beanListRepLegal = beanListRepLegal;
	}
	public List<Persona> getBeanListContactoNatu() {
		return beanListContactoNatu;
	}
	public void setBeanListContactoNatu(List<Persona> beanListContactoNatu) {
		this.beanListContactoNatu = beanListContactoNatu;
	}
	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}
	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}
	
}
