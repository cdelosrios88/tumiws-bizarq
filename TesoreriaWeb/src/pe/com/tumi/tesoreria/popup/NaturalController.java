package pe.com.tumi.tesoreria.popup;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.common.util.MyFile;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
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
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;

public class NaturalController {
	
	protected   static Logger 		    log = Logger.getLogger(NaturalController.class);
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
	private 	MyFile					fileFoto;
	private 	MyFile					fileFirma;
	private		boolean					datosValidados;
	private		boolean					mostrarMensaje;
	private		String					mensaje;
	private		TablaFacadeRemote		tablaFacade;
	private		PersonaFacadeRemote 	personaFacade;
	private		GeneralFacadeRemote 	generalFacade;
	private		boolean					habilitarEditar;
	
	public NaturalController(){
		try{
		intVinculoContactoNatu = Constante.PARAM_T_TIPOVINCULO_CONTACTO;//Representante Legal
		intCboTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL; 		//Persona Natural
		intCboTipoDoc = Constante.PARAM_T_INT_TIPODOCUMENTO_DNI;		//Tipo de documento
		
		cleanRepLegal();
		
		beanListRepLegal = new ArrayList<Persona>(); 
		
		//atributos de sesión
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		
		tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
		generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
		
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	private boolean validarDuplicidadPersona(Persona persona, List<Persona> listaPersona) throws Exception{
		boolean seEncuentraAgregado = Boolean.FALSE;
		//String dniPersona = persona.getDocumento().getStrNumeroIdentidad();
		String dniPersona = obtenerDNI(persona);
		log.info("pers:"+persona.getIntIdPersona()+" dni:"+dniPersona);
		
		for(Persona personaTemp : listaPersona){
			seEncuentraAgregado = Boolean.FALSE;
			if(personaTemp.getIntIdPersona()!=null){
				log.info("personaTemp:"+personaTemp.getIntIdPersona());
				if(personaTemp.getIntIdPersona().equals(persona.getIntIdPersona())){
					seEncuentraAgregado = Boolean.TRUE;
					break;
				}
			}else{
				if(obtenerDNI(personaTemp).equals(dniPersona)){
					seEncuentraAgregado = Boolean.TRUE;
					break;
				}
			}			
		}
		log.info("seEncuentraAgregado:"+seEncuentraAgregado);
		if(!seEncuentraAgregado){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	
	private Persona obtenerDuplicidadPersonaPorDNI(String strDNIBuscar, List<Persona> listaPersona) throws Exception{
	
		for(Persona personaTemp : listaPersona){			
			if(obtenerDNI(personaTemp)!=null && obtenerDNI(personaTemp).equals(strDNIBuscar)){
				return personaTemp;
			}						
		}
		
		return null;
	}
	
	private void logPersona(Persona persona) throws Exception{
		log.info(persona);
		log.info(persona.getNatural());
		log.info(persona.getPersonaEmpresa());
		if(persona.getPersonaEmpresa()!=null){
			if(persona.getPersonaEmpresa().getListaPersonaRol()!=null){
				for(PersonaRol personaRol : persona.getPersonaEmpresa().getListaPersonaRol()){
					log.info(personaRol);
				}
			}else{
				log.info("listaPersonaRol:null");
			}
			
		}
		for(Documento documento : persona.getListaDocumento()){
			log.info(documento);
		}
	}
	
	private List<Persona> reemplazarDuplicidadPersonaPorDNI(Persona persona, List<Persona> listaPersona) throws Exception{
		String strDNIBuscar = obtenerDNI(persona);
		log.info("strDNIBuscar:"+strDNIBuscar);
		logPersona(persona);
		for(int i=0;i<listaPersona.size();i++){
			Persona personaTemp = listaPersona.get(i);
			if(obtenerDNI(personaTemp)!=null && obtenerDNI(personaTemp).equals(strDNIBuscar)){
				log.info("match!");
				logPersona(personaTemp);
				persona.setChecked(Boolean.TRUE);
				listaPersona.set(i, persona);				
			}else{
				personaTemp.setChecked(Boolean.FALSE);
			}			
		}
		for(Persona personaTemp : listaPersona){
			if(personaTemp.getChecked()){
				log.info("checked");
				logPersona(personaTemp);
			}
		}
		return listaPersona;
	}
	
	private Persona obtenerDuplicidadPersona(Persona persona, List<Persona> listaPersona) throws Exception{
		Persona personaRetornar = null;
		//String dniPersona = persona.getDocumento().getStrNumeroIdentidad();
		String dniPersona = obtenerDNI(persona);
		log.info("pers:"+persona.getIntIdPersona()+" dni:"+dniPersona);
		
		for(Persona personaTemp : listaPersona){
			if(personaTemp.getIntIdPersona()!=null){
				log.info("personaTemp:"+personaTemp.getIntIdPersona());
				if(personaTemp.getIntIdPersona().equals(persona.getIntIdPersona())){
					personaRetornar = personaTemp;
					return personaRetornar;
				}
			}else{
				if(obtenerDNI(personaTemp).equals(dniPersona)){
					personaRetornar = personaTemp;
					return personaRetornar;
				}
			}			
		}
		return personaRetornar;
	}
	
	
	public void validarRepLegal(ActionEvent event){
		try{
			mostrarMensaje = Boolean.TRUE;
			//valida que strNroDocIdentidad posea la longitud correcta
			if(strNroDocIdentidad==null && strNroDocIdentidad.isEmpty() || !(strNroDocIdentidad.length()==Constante.LONGITUD_DNI)){
				mensaje = "Debe ingresar un DNI con una longitud de 8 caracteres.";
				return;
			}
			
			Persona replegal = validarPerNatural(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL);
			
			if(replegal!=null){
				if(!validarDuplicidadPersona(replegal, beanListRepLegal)){
					mensaje = "Ya se encuentra agregada esa persona como representante legal.";
					return;
				}else{
					Persona personaDuplicada = obtenerDuplicidadPersona(replegal, beanListContactoNatu);
					if(personaDuplicada != null){
						mensaje = "Esta persona ya se encuentra agregada como Contacto.";
						personaDuplicada = prepararPersona(personaDuplicada,Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL);
						setBeanPerNatural(personaDuplicada);
						getDetalleContacto(personaDuplicada);
					}else{
						setBeanPerNatural(replegal);
						getDetalleContacto(replegal);
						mostrarMensaje = Boolean.FALSE;
					}
				}
			}else{
				Persona personaDuplicada = obtenerDuplicidadPersonaPorDNI(strNroDocIdentidad, beanListContactoNatu);
				if(personaDuplicada != null){
					mensaje = "Esta persona ya se encuentra agregada como Contacto.";
					personaDuplicada = prepararPersona(personaDuplicada,Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL);
					setBeanPerNatural(personaDuplicada);
					getDetalleContacto(personaDuplicada);
				}else{
					mensaje = "No existe una persona con ese DNI. Se procederá a registrar una nueva persona.";
					//beanPerNatural.getDocumento().setStrNumeroIdentidad(strNroDocIdentidad);
					/**Parche para añadir dni de nueva persona en lista de docs**/
					intIdOtroDocumento = Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI);
					strDocIdentidad = strNroDocIdentidad;
					addOtroDocumento(null);
					strDocIdentidad = "";
					/**Fin parche**/
				}
			}
			
			datosValidados = Boolean.TRUE;
			
		}catch(Exception e){
			mensaje = "Hubo un error durante la validación.";
			mostrarMensaje = Boolean.TRUE;
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	public void validarContacto(ActionEvent event) throws Exception{
		try{
			mostrarMensaje = Boolean.TRUE;
			//valida que strNroDocIdentidad posea la longitud correcta
			if(strNroDocIdentidad==null && strNroDocIdentidad.isEmpty() || !(strNroDocIdentidad.length()==Constante.LONGITUD_DNI)){
				mensaje = "Debe ingresar un DNI con una longitud de 8 caracteres.";
				return;
			}
			
			Persona contacto = validarPerNatural(Constante.PARAM_T_TIPOVINCULO_CONTACTO);
			
			if(contacto!=null){
				if(!validarDuplicidadPersona(contacto, beanListContactoNatu)){
					mensaje = "Ya se encuentra agregada esa persona como Contacto.";
					return;
				}else{
					Persona personaDuplicada = obtenerDuplicidadPersona(contacto, beanListRepLegal);
					if(personaDuplicada!=null){
						mensaje = "Esta persona ya se encuentra agregada como Representante Legal.";
						personaDuplicada = prepararPersona(personaDuplicada,Constante.PARAM_T_TIPOVINCULO_CONTACTO);
						setBeanPerNatural(personaDuplicada);
						getDetalleContacto(personaDuplicada);
					}else{
						setBeanPerNatural(contacto);
						getDetalleContacto(contacto);
						mostrarMensaje = Boolean.FALSE;
					}
				}
			}else{
				Persona personaDuplicada = obtenerDuplicidadPersonaPorDNI(strNroDocIdentidad, beanListRepLegal);
				if(personaDuplicada != null){
					mensaje = "Esta persona ya se encuentra agregada como Representante Legal.";
					personaDuplicada = prepararPersona(personaDuplicada,Constante.PARAM_T_TIPOVINCULO_CONTACTO);
					setBeanPerNatural(personaDuplicada);
					getDetalleContacto(personaDuplicada);
				}else{
					mensaje = "No existe una persona con ese DNI. Se procederá a registrar una nueva persona.";
					//beanPerNatural.getDocumento().setStrNumeroIdentidad(strNroDocIdentidad);
					/**Parche para añadir dni de nueva persona en lista de docs**/
					intIdOtroDocumento = Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI);
					strDocIdentidad = strNroDocIdentidad;
					addOtroDocumento(null);
					strDocIdentidad = "";
					/**Fin parche**/
				}
				
			}
			
			datosValidados = Boolean.TRUE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
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
	

	
	public void getDetalleContacto(Persona contacto){
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		
		if(contacto.getListaComunicacion()!=null){
			//Obteniendo Comunicaciones de Representante Legal
			/*log.info("persona.listaComunicacion.size: "+contacto.getListaComunicacion().size());
			for(Comunicacion com : contacto.getListaComunicacion()){
				log.info("com.getId().getIntIdComunicacion(): "+com.getId().getIntIdComunicacion());
				log.info("com.getStrComunicacion(): "+com.getStrComunicacion());
				log.info("com.getStrDato(): "+com.getStrDato());
			}*/			
			comunicacionController.setListComuniContactoNatu(contacto.getListaComunicacion());
			comunicacionController.setBeanListComuniRepLegal(contacto.getListaComunicacion());
		}else{
			comunicacionController.setListComuniContactoNatu(new ArrayList<Comunicacion>());
			comunicacionController.setBeanListComuniRepLegal(new ArrayList<Comunicacion>());
		}
		
		if(contacto.getListaDomicilio()!=null){
			domicilioController.setBeanListDirecRepLegal(contacto.getListaDomicilio());			
		}else{
			domicilioController.setBeanListDirecRepLegal(new ArrayList<Domicilio>());
		}
	}
	
	public Persona validarPerNatural(Integer pIntTipoVinculo){		
		Persona persona = null;
		try{
			log.info("-------------------------------------Debugging validarPerNatural-------------------------------------");			
			log.info("IntCboTipoPersona: "+getIntCboTipoPersona());
			log.info("IntCboTipoDoc: "+getIntCboTipoDoc());
			log.info("strNroDocIdentidad: "+getStrNroDocIdentidad());			
	    		
	    	persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(getIntCboTipoDoc(),getStrNroDocIdentidad(),SESION_IDEMPRESA);
			
			if(persona!=null){
				logRepLegalVal(persona); //debug de las propiedades de la persona natural
			}else{
				//setMessageError("No existe persona natural con documento: "+getStrNroDocIdentidad());
				return persona;
			}
			
			prepararPersona(persona,pIntTipoVinculo);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return persona;
	}
	
	private Persona prepararPersona(Persona persona, Integer pIntTipoVinculo) throws Exception{
		if(persona.getListaDocumento()!=null){
			//Obteniendo Otros Documentos de Representante Legal
			log.info("persona.listaDocumento.size: "+persona.getListaDocumento().size());
			
			//Separando el dni
			/*for(int i=0; i<persona.getListaDocumento().size(); i++){
				if(persona.getListaDocumento().get(i).getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
					persona.setDocumento(persona.getListaDocumento().get(i));
					log.info("DNI:"+persona.getListaDocumento().get(i).getStrNumeroIdentidad());
					persona.getListaDocumento().remove(i);
					break;
				}
			}*/
		}
		
		if(persona.getPersonaEmpresa()!=null && persona.getPersonaEmpresa().getListaPersonaRol()!=null){
			//Obteniedo Roles de Persona Juridica
			strRoles = "";
			log.info("persona.personaEmpresa.listaPersonaRol.size: "+persona.getPersonaEmpresa().getListaPersonaRol().size());
			List<PersonaRol> listPersonaRol = persona.getPersonaEmpresa().getListaPersonaRol();
			for(PersonaRol personaRol : listPersonaRol){
				personaRol = cargarTablaRol(personaRol);
				log.info("personaRol.tabla.strDescripcion: "+personaRol.getTabla().getStrDescripcion());
				strRoles = strRoles+personaRol.getTabla().getStrDescripcion()+",";
			}
			if(strRoles!=null && !strRoles.isEmpty()){
				strRoles=strRoles.substring(0,strRoles.length()-1);
			}
			log.info("strRoles: "+strRoles);
		}
		
		if(persona.getPersonaEmpresa()!=null){
			if(persona.getPersonaEmpresa().getVinculo()==null){
				persona.getPersonaEmpresa().setVinculo(new Vinculo());
			}
			//por default el vinculo es Representante Legal o Contacto Natural
			persona.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(pIntTipoVinculo);
		}
		return persona;
	}
	
	public PersonaRol cargarTablaRol(PersonaRol personaRol) throws Exception{
		List<Tabla> listaTablaRol = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOROL));
		for(Tabla tabla : listaTablaRol){
			if(tabla.getIntIdDetalle().equals(personaRol.getId().getIntParaRolPk())){
				personaRol.setTabla(tabla);
			}
		}
		return personaRol;
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
		try{
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
			
			
			
			replegal = addPersonaManejoArchivo(replegal);
		    
			if(beanListRepLegal==null){
				setBeanListRepLegal(new ArrayList<Persona>());
			}
			
			/**Parche para identificar que es un nuevo registro de persona**/
			replegal.setChecked(Boolean.FALSE);
			/**Fin de parche**/
			
			/**Parche para eliminar la duplicidad al momento de editar un nuevo registro**/
			manejarDuplicidadPersona(replegal, beanListRepLegal);			
			/**Fin parche**/
			
			/**Parche para comprobar la duplicidad en contacto**/
			beanListContactoNatu = reemplazarDuplicidadPersonaPorDNI(replegal,beanListContactoNatu);		
			/**Fin parche**/
			
			getBeanListRepLegal().add(replegal);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	private String obtenerDNI(Persona persona)throws Exception{
		String strDNI = null;
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				strDNI = documento.getStrNumeroIdentidad();
				break;
			}
		}
		log.info("pers:"+persona.getIntIdPersona()+" dni:"+strDNI);
		return strDNI;
	}
	
	private void manejarDuplicidadPersona(Persona persona, List<Persona> listaPersona)throws Exception{
		String strDNIBuscar = obtenerDNI(persona);
		Persona personaPersiste = null;
		
		for(Persona personaTemp : listaPersona){
			if(personaTemp.getIntIdPersona()==null && strDNIBuscar.equals(obtenerDNI(personaTemp))){
				personaPersiste = personaTemp;
				break;
			}
		}
		
		if(personaPersiste != null){
			listaPersona.remove(personaPersiste);
		}		
		
	}
	
	private Persona addPersonaManejoArchivo(Persona persona){
		
		//**Inicio Manejo de archivos adjuntos**///
	    FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
	    Archivo archivoFirma = fileUploadController.getArchivoFirma(); 
	    log.info("archivoFirma:"+archivoFirma);
	    if(archivoFirma!=null && archivoFirma.getId().getIntItemArchivo()!=null){
	    	  log.info("archivo tipo:"+archivoFirma.getId().getIntParaTipoCod());
	    	  log.info("archivo item:"+archivoFirma.getId().getIntItemArchivo());
	    	  log.info("archivo nomb:"+archivoFirma.getStrNombrearchivo());
	    	  persona.getNatural().setIntTipoArchivoFirma(archivoFirma.getId().getIntParaTipoCod());
	    	  persona.getNatural().setIntItemArchivoFirma(archivoFirma.getId().getIntItemArchivo());
	    	  persona.getNatural().setIntItemHistoricoFirma(archivoFirma.getId().getIntItemHistorico());
	    }
	    
	    Archivo archivoFoto = fileUploadController.getArchivoFoto(); 
	    log.info("archivoFoto:"+archivoFoto);
	    if(archivoFoto!=null && archivoFoto.getId().getIntItemArchivo()!=null){
	    	  log.info("archivo tipo:"+archivoFoto.getId().getIntParaTipoCod());
	    	  log.info("archivo item:"+archivoFoto.getId().getIntItemArchivo());
	    	  log.info("archivo nomb:"+archivoFoto.getStrNombrearchivo());
	    	  persona.getNatural().setIntTipoArchivoFoto(archivoFoto.getId().getIntParaTipoCod());
	    	  persona.getNatural().setIntItemArchivoFoto(archivoFoto.getId().getIntItemArchivo());
	    	  persona.getNatural().setIntItemHistoricoFoto(archivoFoto.getId().getIntItemHistorico());
	    }
	    
	    return persona;
	  //**Fin Manejo de archivos adjuntos**///
	}
	
	
	public void addContactoNatu(ActionEvent event){
		try{
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
			
			//**Inicio Manejo de archivos adjuntos**///
			contacto = addPersonaManejoArchivo(contacto);
			//**Fin Manejo de archivos adjuntos**///		    
		    
			if(beanListContactoNatu==null){
				setBeanListContactoNatu(new ArrayList<Persona>());
			}
			
			/**Parche para identificar que es un nuevo registro de persona**/
			contacto.setChecked(Boolean.FALSE);
			/**Fin de parche**/
			
			/**Parche para eliminar la duplicidad al momento de editar un nuevo registro**/
			manejarDuplicidadPersona(contacto, beanListContactoNatu);
			/**Fin parche**/
			
			/**Parche para comprobar la duplicidad en representante legal**/
			beanListRepLegal = reemplazarDuplicidadPersonaPorDNI(contacto,beanListRepLegal);		
			/**Fin parche**/
			
			getBeanListContactoNatu().add(contacto);		
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	private Persona manejarVinculoPersona(Persona persona, Integer intTipoVinculo) throws Exception{
		boolean poseeTipoDeVinculo = Boolean.FALSE;
		persona.getPersonaEmpresa().setListaVinculo(new ArrayList<Vinculo>());
		
		if(beanPerNatural.getPersonaEmpresa().getListaVinculo()!=null){
			log.info("beanPerNatural listaVinculo:"+beanPerNatural.getPersonaEmpresa().getListaVinculo().size());
			persona.getPersonaEmpresa().getListaVinculo().addAll(beanPerNatural.getPersonaEmpresa().getListaVinculo());
			for(Vinculo vinculo : beanPerNatural.getPersonaEmpresa().getListaVinculo()){
				if(vinculo.getIntTipoVinculoCod().equals(intTipoVinculo)){
					poseeTipoDeVinculo = Boolean.TRUE;
					break;
				}
			}
		}else{
			log.info("beanPerNatural listaVinculo:null");						
		}
		
		log.info("poseeTipoDeVinculo:"+poseeTipoDeVinculo);
		if(!poseeTipoDeVinculo){
			Vinculo vinculo = new Vinculo();
			vinculo.setIntIdEmpresa(SESION_IDEMPRESA);
			vinculo.setIntTipoVinculoCod(intTipoVinculo);
			vinculo.setIntEmpresaVinculo(SESION_IDEMPRESA);
			persona.getPersonaEmpresa().getListaVinculo().add(vinculo);
		}
		
		return persona;
	}
	
	public Persona getPerNatural(Integer pIntTipoVinculo) throws Exception{
		log.info("-------------------------------------Debugging addPerNatural-------------------------------------");
/*		
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
		log.info("strFirma: "+getBeanRepLegal().getNatural().getStrFirma());
		log.info("strFoto: "+getBeanRepLegal().getNatural().getStrFoto());
		log.info("intEstadoCod: "+getBeanPerNatural().getIntEstadoCod());
*/		
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
		
		/*
		replegal.getPersonaEmpresa().getVinculo().setIntIdEmpresa(SESION_IDEMPRESA);
		replegal.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(pIntTipoVinculo);
		replegal.getPersonaEmpresa().getVinculo().setIntEmpresaVinculo(SESION_IDEMPRESA);
		*/
		
		manejarVinculoPersona(replegal, pIntTipoVinculo);
		
		
		
		if(beanPerNatural.getPersonaEmpresa().getListaPersonaRol()!=null){
			replegal.getPersonaEmpresa().setListaPersonaRol(beanPerNatural.getPersonaEmpresa().getListaPersonaRol());
		}else{
			replegal.getPersonaEmpresa().setListaPersonaRol(new ArrayList<PersonaRol>());
		}
		
		if(pIntTipoVinculo.equals(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL)){
			if(beanPerNatural.getIntIdPersona()!=null){
				if(!poseeRolRepresentanteLegal(beanPerNatural)){
					replegal = agregarRolRepresentanteLegal(replegal);
				}
			}else{
				replegal = agregarRolRepresentanteLegal(replegal);
			}
		}
		
		
		//Obteniendo lista de Otros Documentos para el Representante Legal
		log.info("beanPerNatural.listaDocumento.size: "+getBeanPerNatural().getListaDocumento().size());
		replegal.setListaDocumento(getBeanPerNatural().getListaDocumento());
		//representante legal PRU = persona.getListaPersona
		
		
		//agregando el DNI a los documentos de identidad
		if(getBeanPerNatural().getDocumento()!=null && getBeanPerNatural().getDocumento().getId()!=null){
			//replegal.getListaDocumento().add(getBeanPerNatural().getDocumento());
		}else{
			/*if(beanPerNatural.getListaDocumento().isEmpty()){
				Documento dni = new Documento();
				dni.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				dni.setIntTipoIdentidadCod(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI));
				dni.setStrNumeroIdentidad(getBeanPerNatural().getDocumento().getStrNumeroIdentidad());
				replegal.getListaDocumento().add(dni);
			}*/
		}
		
		return replegal;
	} 
	
	private Persona agregarRolRepresentanteLegal(Persona persona){
		PersonaRol personaRol = new PersonaRol();
		personaRol.setId(new PersonaRolPK());
		personaRol.getId().setIntIdEmpresa(SESION_IDEMPRESA);
		personaRol.getId().setIntParaRolPk(Constante.PARAM_T_TIPOROL_REPLEGAL);
		personaRol.getId().setDtFechaInicio(new Date());
		personaRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		
		persona.getPersonaEmpresa().setListaPersonaRol(new ArrayList<PersonaRol>());
		persona.getPersonaEmpresa().getListaPersonaRol().add(personaRol);
		
		return persona;
	}
	
	private boolean poseeRolRepresentanteLegal(Persona persona) throws Exception{
		Persona personaBus = personaFacade.getPersonaJuridicaPorIdPersonaYIdEmpresa(persona.getIntIdPersona(), SESION_IDEMPRESA);
		
		boolean poseeRolReplegal = Boolean.FALSE;
		for(PersonaRol personaRol : personaBus.getPersonaEmpresa().getListaPersonaRol()){
			if(personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_REPLEGAL) 
				&& personaRol.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				poseeRolReplegal = Boolean.TRUE;
				break;
			}
		}
		return poseeRolReplegal;
	}
	
	public void nuevoContactoNatu(ActionEvent event){
		log.info("-----------------------------------Debugging nuevoContactoNatu-----------------------------------");
		cleanRepLegal();
		setIntCboTipoDoc(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
		log.info("intCboTipoDoc: "+getIntCboTipoDoc());
		beanPerNatural.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(Constante.PARAM_T_TIPOVINCULO_CONTACTO);
		
		FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
	    fileUploadController.setArchivoFirma(null);
	    fileUploadController.setArchivoFoto(null);
	    fileFirma = null;
	    fileFoto = null;
	    

	    strNroDocIdentidad = "";
	    datosValidados = Boolean.FALSE;
	    mostrarMensaje = Boolean.FALSE;
	    habilitarEditar = Boolean.TRUE;
	}
	
	public void nuevoRepLegal(ActionEvent event){
		//log.info("-----------------------------------Debugging nuevoRepLegal-----------------------------------");
		cleanRepLegal();
		setIntCboTipoDoc(Integer.valueOf(Constante.PARAM_T_TIPODOCUMENTO_DNI));
		beanPerNatural.getPersonaEmpresa().getVinculo().setIntTipoVinculoCod(Constante.PARAM_T_TIPOVINCULO_REPRESENTANTELEGAL);
		
		FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
	    fileUploadController.setArchivoFirma(null);
	    fileUploadController.setArchivoFoto(null);
	    fileFirma = null;
	    fileFoto = null;
	    
	    strNroDocIdentidad = "";
	    datosValidados = Boolean.FALSE;
	    mostrarMensaje = Boolean.FALSE;
	    habilitarEditar = Boolean.TRUE;
	}
		
	public void cleanRepLegal(){
		//log.info("-----------------------------------Debugging cleanRepLegal-----------------------------------");
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
		
		//log.info("beanRepLegal: "+getBeanPerNatural());
		//log.info("beanRepLegal.natural: "+getBeanPerNatural().getNatural());
		
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
		try{
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
			
			if(replegal.getChecked()){
				habilitarEditar = Boolean.FALSE;
			}else{
				habilitarEditar = Boolean.TRUE;
			}
			
			logRepLegalVal(replegal); //valores de las propiedades de Representante Legal
			//Separando el dni
			/*for(int i=0; i<replegal.getListaDocumento().size(); i++){
				if(replegal.getListaDocumento().get(i).getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
					replegal.setDocumento(replegal.getListaDocumento().get(i));
					replegal.getListaDocumento().remove(i);
					break;
				}
			}*/
			
			verPersonaManejoArchivo(replegal.getNatural());
		    
			setBeanPerNatural(replegal);
			
			showDetallePerNatural(replegal);
			
			generarStrRoles(replegal);
			
			getDetalleContacto(replegal);
			
			datosValidados = Boolean.TRUE;
			mostrarMensaje = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**Parche para poder ver los roles de las personas**/
	private void generarStrRoles(Persona persona) throws Exception{		
			
			PersonaEmpresaPK personaEmpresaId = new PersonaEmpresaPK();
			personaEmpresaId.setIntIdEmpresa(SESION_IDEMPRESA);
			personaEmpresaId.setIntIdPersona(persona.getIntIdPersona());
			
			//Obteniedo Roles de Persona Juridica
			strRoles = "";			
			List<PersonaRol> listPersonaRol = personaFacade.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresaId);
			for(PersonaRol personaRol : listPersonaRol){
				strRoles = strRoles+personaRol.getTabla().getStrDescripcion()+",";
			}
			if(strRoles!=null && strRoles.length()>0){
				strRoles=strRoles.substring(0,strRoles.length()-1);
			}		
	}
	/**Fin parche**/
	
	/**Parche para cargar archivos para "Ver" persona**/
	private void verPersonaManejoArchivo(Natural natural) throws Exception{
		
		
		if(natural.getIntItemArchivoFoto()!=null){
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntItemArchivo(natural.getIntItemArchivoFoto());
			archivoId.setIntItemHistorico(natural.getIntItemHistoricoFoto());
			archivoId.setIntParaTipoCod(natural.getIntTipoArchivoFoto());
			
			Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
			TipoArchivo tipoArchivo = generalFacade.getTipoArchivoPorPk(archivo.getId().getIntParaTipoCod());
			natural.setStrPathFoto(tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
			log.info("foto path:"+natural.getStrPathFoto());
			
	    	byte[] byteImg = getDataImage(natural.getStrPathFoto());
			MyFile file = new MyFile();
	        file.setLength(byteImg.length);
	        file.setName(archivo.getStrNombrearchivo());
	        file.setData(byteImg);
	        fileFoto = file;
		}else{
			fileFoto = null;
		}
		
		if(natural.getIntItemArchivoFirma()!=null){
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntItemArchivo(natural.getIntItemArchivoFirma());
			archivoId.setIntItemHistorico(natural.getIntItemHistoricoFirma());
			archivoId.setIntParaTipoCod(natural.getIntTipoArchivoFirma());
			
			Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
			TipoArchivo tipoArchivo = generalFacade.getTipoArchivoPorPk(archivo.getId().getIntParaTipoCod());
			natural.setStrParhFirma(tipoArchivo.getStrRuta()+"\\"+archivo.getStrNombrearchivo());
			log.info("firma path:"+natural.getStrParhFirma());
			
	        byte[] byteImg = getDataImage(natural.getStrParhFirma());
			MyFile file = new MyFile();
	        file.setLength(byteImg.length);
	        file.setName(archivo.getStrNombrearchivo());
	        file.setData(byteImg);
	        fileFirma = file;
		}else{
			fileFirma = null;
		}
		
		FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
	    fileUploadController.setArchivoFirma(null);
	    fileUploadController.setArchivoFoto(null);	    
	}
	/**Fin parche**/
	
	public void aceptarFoto(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
		    if(fileUploadController.getArchivoFoto()!=null){
		    	Archivo archivo = fileUploadController.getArchivoFoto();				
		    	String path = archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo(); 
		    	log.info("path:"+path);
		    	byte[] byteImg = getDataImage(path);
				MyFile file = new MyFile();
		        file.setLength(byteImg.length);
		        file.setName(archivo.getStrNombrearchivo());
		        file.setData(byteImg);
		        fileFoto = file;
		    	//fileFoto = obtenerMyFile(path,archivo.getStrNombrearchivo());
		    }else{
		    	fileFoto = null;
		    }
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarFirma(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
		    if(fileUploadController.getArchivoFirma()!=null){
		    	Archivo archivo = fileUploadController.getArchivoFirma();				
		    	String path = archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo(); 
		    	log.info("path:"+path);
		    	byte[] byteImg = getDataImage(path);
				MyFile file = new MyFile();
		        file.setLength(byteImg.length);
		        file.setName(archivo.getStrNombrearchivo());
		        file.setData(byteImg);
		        fileFirma = file;
		    	//fileFirma = obtenerMyFile(path,archivo.getStrNombrearchivo());
		    }else{
		    	fileFoto = null;
		    }
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public MyFile obtenerMyFile(String ruta, String nombre) throws Exception{
    	byte[] byteImg = getDataImage(ruta);
		MyFile file = new MyFile();
        file.setLength(byteImg.length);
        file.setName(nombre);
        file.setData(byteImg);
        fileFoto = file;
        return file;
	}
	
	public byte[] getDataImage(String strPath) throws IOException{
		String strExtension = strPath.substring(strPath.lastIndexOf(".")+1);
		java.io.File iofile = new java.io.File(strPath);
		BufferedImage image = ImageIO.read(iofile);
		// O P E N
		ByteArrayOutputStream baos = new ByteArrayOutputStream((int)iofile.length());
		// W R I T E
		ImageIO.write(image,strExtension.trim(),baos);
		// C L O S E
		baos.flush();
		byte[] byteImg = baos.toByteArray();
		baos.close();
		return byteImg;
	}
	
	public void verContactoNatu(ActionEvent event) {
		try{
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
			
			if(contacto.getChecked()){
				habilitarEditar = Boolean.FALSE;
			}else{
				habilitarEditar = Boolean.TRUE;
			}
			
			logRepLegalVal(contacto); //valores de las propiedades de Representante Legal
			//Separando el dni
			/*for(int i=0; i<contacto.getListaDocumento().size(); i++){
				if(contacto.getListaDocumento().get(i).getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
					contacto.setDocumento(contacto.getListaDocumento().get(i));
					contacto.getListaDocumento().remove(i);
					break;
				}
			}*/
			
			verPersonaManejoArchivo(contacto.getNatural());
			
			setBeanPerNatural(contacto);
			
			showDetallePerNatural(contacto);
			
			generarStrRoles(contacto);
			
			getDetalleContacto(contacto);
		
			datosValidados = Boolean.TRUE;
			mostrarMensaje = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
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
		//comunicacionController.setBean
	}
	
	public void quitarRepLegal(ActionEvent event){
		log.info("-------------------------------------Debugging quitarRepLegal-------------------------------------");
		int rowKey = Integer.parseInt(getRequestParameter("rowKeyRepLegal"));
		Persona perTmp = null;
		if(beanListRepLegal!=null){
			/*for(int i=0; i<beanListRepLegal.size(); i++){
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
			}*/
			
			for(int i=0; i<beanListRepLegal.size(); i++){
				if(rowKey == i){
					Persona persona = beanListRepLegal.get(i);
					if(persona.getIntIdPersona()!=null){
						beanListRepLegal.get(i).getPersonaEmpresa().getVinculo().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}else{
						beanListRepLegal.remove(persona);
					}					
				}
			}
		}
	}
	
	public void quitarContactoNatu(ActionEvent event){
		log.info("-------------------------------------Debugging quitarContactoNatu-------------------------------------");
		int rowKey = Integer.parseInt(getRequestParameter("rowKeyContactoNatu"));
		Persona perTmp = null;
		if(beanListContactoNatu!=null){
			/*for(int i=0; i<beanListContactoNatu.size(); i++){
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
			}*/
			
			for(int i=0;i<beanListContactoNatu.size();i++){
				if(rowKey == i){
					Persona persona = beanListContactoNatu.get(i);
					if(persona.getIntIdPersona()!=null){
						beanListContactoNatu.get(i).getPersonaEmpresa().getVinculo().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}else{
						beanListContactoNatu.remove(persona);
					}					
				}
			}
		}
	}
	
	public void addOtroDocumento(ActionEvent event){
		try {
			log.info("-------------------------------------Debugging addOtroDocumento-------------------------------------");
			log.info("intIdOtroDocumento: "+getIntIdOtroDocumento());
			log.info("StrDocIdentidad: "+getStrDocIdentidad());
			
			Documento documento = new Documento();
			documento.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			documento.setIntTipoIdentidadCod(getIntIdOtroDocumento());
			documento.setStrNumeroIdentidad(getStrDocIdentidad());
			
			List<Tabla> listTabla = null;
			
			listTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO));
	
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
		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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
	
	public void paintImage(OutputStream stream, Object object) throws IOException {
        MyFile myFile = (MyFile)object;
		log.info("myFile:"+myFile.getName());
        stream.write(myFile.getData());
    }
	public void paintImageFoto(OutputStream stream, Object object) throws IOException {
        MyFile myFile = (MyFile)object;
		log.info("Foto myFile:"+myFile.getName());
        stream.write(myFile.getData());
    }
	public void paintImageFirma(OutputStream stream, Object object) throws IOException {
        MyFile myFile = (MyFile)object;
		log.info("Firma myFile:"+myFile.getName());
        stream.write(myFile.getData());
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
	public MyFile getFileFoto() {
		return fileFoto;
	}
	public void setFileFoto(MyFile fileFoto) {
		this.fileFoto = fileFoto;
	}
	public MyFile getFileFirma() {
		return fileFirma;
	}
	public void setFileFirma(MyFile fileFirma) {
		this.fileFirma = fileFirma;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isMostrarMensaje() {
		return mostrarMensaje;
	}
	public void setMostrarMensaje(boolean mostrarMensaje) {
		this.mostrarMensaje = mostrarMensaje;
	}
	public boolean isHabilitarEditar() {
		return habilitarEditar;
	}
	public void setHabilitarEditar(boolean habilitarEditar) {
		this.habilitarEditar = habilitarEditar;
	}
}
