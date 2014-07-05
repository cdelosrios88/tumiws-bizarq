package pe.com.tumi.credito.socio.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import java.sql.Timestamp;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.DownloadFile;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.common.util.MyFile;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.credito.popup.ComunicacionController;
import pe.com.tumi.credito.popup.CuentaBancariaController;
import pe.com.tumi.credito.popup.DomicilioController;
import pe.com.tumi.credito.popup.JuridicaController;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeLocal;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeLocal;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.message.MessageController;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;


/************************************************************************/
/* Nombre de la clase: HojaPlaneamientoController */
/* Funcionalidad : Clase que que tiene los parametros de busqueda */
/* Ref. : */
/* Autor : Jhonathan Morán */
/* Versión : 0.1 */
/* Fecha creación : 28/12/2011 */
/* ********************************************************************* */

public class SocioController {
	
	protected   static Logger 			log = Logger.getLogger(SocioController.class);
	private 	SocioComp 				socioBusqueda;
	private 	SocioComp				beanSocioComp;
	private 	SocioComp				socioNatuSelected;
	private 	List<SocioComp> 		listaSocioBusqueda;
	private 	Persona 				personaBusqueda;
	private 	SocioEstructura 		socioEstructura;
	/* Inicio - Persona antes de la modificación - GTorresBrousset 24.abr.2014 */
	private		Persona					personaOld;
	/* Fin - Persona antes de la modificación - GTorresBrousset 24.abr.2014 */
	
	//combo de TipoDocumento
	private 	List<Tabla>				listDocumentoBusq;
	private 	List<Tabla>				listDocumento;
	
	//lista de Roles
	private		List<PersonaRol>		listRoles;
	private		List<PersonaRol>		arrayRoles;
	
	//combos de Sucursal
	private 	List<Sucursal> 			listJuridicaSucursal;
	private 	List<Subsucursal> 		listJuridicaSubsucursal;
	//combo de Detalle de Condición Laboral
	private 	List<Tabla> 			listDetCondicionLaboral;
	
	private 	List<Tabla> 			listTipoRelacion;
	
	protected 	static SimpleDateFormat	sdfshort = new SimpleDateFormat("dd/MM/yyyy");
	
	private 	Boolean 				blnShowFiltroPersonaNatu;
	private 	Boolean					blnShowDivFormSocio;
	private 	Boolean					blnShowDivFormSocioNatu;
	private 	Boolean					blnShowDivFormAperturaCuenta;
	private 	Boolean					blnShowDivFormSocioJuridica;
	private 	Boolean					blnShowDivPanelControlsSocio;
	
	private 	Integer 				intDocIdentidadMaxLength;
	private 	Boolean 				blnFechaContratoDisabled;
	private 	Boolean					blnIsDatoPadron;
	
	private 	List<Ubigeo>			listaUbigeoDepartamento = new ArrayList<Ubigeo>();
	private 	List<Ubigeo>			listaUbigeoProvincia = new ArrayList<Ubigeo>();
	private 	List<Ubigeo>			listaUbigeoDistrito = new ArrayList<Ubigeo>();
	
	private 	MyFile					fileFotoSocio;
	private 	MyFile					fileFirmaSocio;
	
	private 	Boolean 				isValidSocioNatural;
	private 	Boolean 				isContratado;
	private 	Boolean 				isValidSocioEstructura;
	
	private 	Archivo 				fileContrato;
	//Agregado 19/01/2013
	private		Boolean					isDatosView;
	/////////////////////
	
	//atributos de sesión
	private 	Integer 				SESION_IDEMPRESA;
	private 	Integer 				SESION_IDUSUARIO;
	private 	Integer 				SESION_IDSUCURSAL;
	private 	Integer 				SESION_IDSUBSUCURSAL;
	
	private		Boolean					blnSocioCliente;
	private		Boolean					blnModificacion;
	private		Boolean					blnArchivamiento;
	//rVillarreal 19.05.2014
	private boolean mostrarBoton;
	
	private 	AperturaCuentaController 	aperturaCuentaController = (AperturaCuentaController)getSessionBean("aperturaCuentaController");
	
	//Agregado 05/03/2013
	private 	PersonaFacadeRemote 	personaFacade = null;
	private		EstructuraFacadeLocal 	estructuraFacade = null;
	/* Inicio - AuditoriaFacade - GTorresBrousset 24.abr.2014 */
	private AuditoriaFacadeRemote 		auditoriaFacade;
	/* Fin - AuditoriaFacade - GTorresBrousset 24.abr.2014 */
	
	//Agregado 10/03/2013
	private		Boolean					blnMsgFecCumple;
	//Agregado 13/03/2013
	private		Boolean					blnRegimenLaboral;
	private		Boolean					blnRemunNeta;
	private		Boolean					blnCondLaboral;
	
	//Agregado 18/03/2013
	private		String					msgTxtDsctoJudicialYPerfil;
	private		String					msgTxtDsctoJudicial;
	private		String					msgTxtLicencPermiso;
	private		String					msgTxtLicencSubvencPermiso;
	//Agregado 19/03/2013
	private		String					msgTxtRolPersona;
	//Agregado 09/04/2013
	private		String					msgTxtPermisoSucursal;
	
	// CGD - 25.10.2013
	private Integer intBusquedaTipo; 	
	private String strBusqCadena;		    
	private Date dtBusqFechaRegDesde;  
	private Date dtBusqFechaRegHasta;
	private Integer intBusqSituacionCta;
	
	//JCHAVEZ 25.03.2014
	private String strRoldePersona;
	
	//RVILLARREAL 15.04.2014
//	private List<Socio> listaSocioCliente;
	private TablaFacadeRemote tablaFacade;
	private Date dtFechaNacimiento;
	//private 	Domicilio beanDomicilio = null;
	
	private List<Tabla> lstReporteVacia;
	private List<Estructura> listEstructura;
	//------------------------------------------------------------------------------------------------------------
	//Mantenimiento de Estructura Organica
	//------------------------------------------------------------------------------------------------------------
	public SocioController(){
		//beanDomicio autor rvillarreal
		//beanDomicilio = new Domicilio();
		//atributos de sesión
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		
		//bean composite de busqueda
		socioBusqueda = new SocioComp();
		socioBusqueda.setPersona(new Persona());
		socioBusqueda.getPersona().setDocumento(new Documento());
		socioBusqueda.setPersonaRol(new PersonaRol());
		socioBusqueda.getPersonaRol().setId(new PersonaRolPK());
		socioBusqueda.setSocio(new Socio());
		socioBusqueda.getSocio().setId(new SocioPK());
		socioBusqueda.getSocio().getId().setIntIdEmpresa(SESION_IDEMPRESA);
		socioBusqueda.getSocio().setSocioEstructura(new SocioEstructura());
		
		socioBusqueda.getPersona().setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
		
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		
		//bean composite de registro
		cleanBeanSocioComp();
		
		//bean de SocioEstructura
		socioEstructura = new SocioEstructura();
		
		//atributos para mostrar u ocultar
		blnShowDivFormSocio = false;
		blnShowDivFormSocioNatu = false;
		blnShowDivFormAperturaCuenta = false;
		blnShowDivFormSocioJuridica = false;
		blnShowDivPanelControlsSocio = true;
		blnIsDatoPadron = false;
		
		//Longitud de caracteres de los documentos
		intDocIdentidadMaxLength = 0;
		
		/*Agregado 19/01/2013*/
		socioNatuSelected = new SocioComp();
		socioNatuSelected.setPersona(new Persona());
		isDatosView = false;
		
		//Agregado 10/03/2013
		blnMsgFecCumple = false;
		//Agregado 13/03/2013
		blnRegimenLaboral = false;
		blnRemunNeta = false;
		blnCondLaboral = false;
		
		//Agregado 18/03/2013
		msgTxtDsctoJudicialYPerfil = "";
		msgTxtDsctoJudicial = "";
		msgTxtLicencPermiso = "";
		msgTxtLicencSubvencPermiso = "";
		msgTxtRolPersona = "";
		msgTxtPermisoSucursal = "";
		//Fin
		mostrarBoton = false;
		strRoldePersona = "";
		lstReporteVacia = new ArrayList<Tabla>();
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			/* Inicio - Inicializar AuditoriaFacade - GTorresBrousset 24.abr.2014*/
			auditoriaFacade = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
			/* Fin - Inicializar AuditoriaFacade - GTorresBrousset 24.abr.2014*/
		} catch (EJBFactoryException e) {
			log.error("error: " + e);
		}
		
		inicio();
	}
	
	public void inicio(){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		//Integer MENU_SOCIO_CLIENTE = 44;
		Integer MENU_SOCIO_CLIENTE = 284;
		Integer MENU_MODIFICACION_DATOS = 45;
		Integer MENU_ARCHIVAMIENTO = 46;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_SOCIO_CLIENTE);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				PermisoFacadeRemote localPermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				blnSocioCliente = (permiso == null)?true:false;
				id.setIntIdTransaccion(MENU_MODIFICACION_DATOS);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				blnModificacion = (permiso == null)?true:false;
				id.setIntIdTransaccion(MENU_ARCHIVAMIENTO);
				permiso = localPermiso.getPermisoPerfilPorPk(id);
				blnArchivamiento = (permiso == null)?true:false;
			}else{
				  blnSocioCliente = false;
				  blnModificacion= false;
				  blnArchivamiento= false;
			}
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}		
	}
	
	public void cleanBeanSocioComp(){
		//bean composite de registro
		this.beanSocioComp = new SocioComp();
		this.beanSocioComp.setPersona(new Persona());
		this.beanSocioComp.getPersona().setNatural(new Natural());
		this.beanSocioComp.getPersona().getNatural().setPerLaboral(new PerLaboral());
		this.beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
		this.beanSocioComp.getPersona().setDocumento(new Documento());
		this.beanSocioComp.setPersonaRol(new PersonaRol());
		this.beanSocioComp.getPersonaRol().setId(new PersonaRolPK());
		this.beanSocioComp.setSocio(new Socio());
		this.beanSocioComp.getSocio().setId(new SocioPK());
		this.beanSocioComp.getSocio().setSocioEstructura(new SocioEstructura());
		
		//atributos por default
		this.beanSocioComp.getPersona().setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
		this.beanSocioComp.getSocio().getId().setIntIdEmpresa(SESION_IDEMPRESA);
		
		//Agregados 18/11/2012
		this.beanSocioComp.getSocio().getSocioEstructura().setIntIdSubSucursalUsuario(SESION_IDSUBSUCURSAL);
		this.beanSocioComp.getSocio().getSocioEstructura().setIntIdSubsucurAdministra(SESION_IDSUBSUCURSAL);
		//beanSocioComp.getSocio().getSocioEstructura().setIntNivel(1);
		
		//limpiar Roles
		arrayRoles = new ArrayList<PersonaRol>();
		listRoles = new ArrayList<PersonaRol>();
		this.beanSocioComp.setStrRoles("");
		
		//Limpiando Firma y Foto
		setFileFirmaSocio(null);
		setFileFotoSocio(null);
		
		//Habilita los campos que podrian ser de Padron
		setBlnIsDatoPadron(false);
		
		//Limpiando list de Ctas Bancarias de Persona Natural
		CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		if(ctaBancariaController!=null)
		ctaBancariaController.setListCtaBancariaSocioNatu(new ArrayList<CuentaBancaria>());
		
		//Limpiando list de Direcciones de Persona Natura
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		if(domicilioController!=null)
		domicilioController.setBeanListDirecSocioNatu(new ArrayList<Domicilio>());
		
		//Limpiando list de Comunicaciones de Persona Natural
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		if(comunicacionController!=null)
		comunicacionController.setListComuniSocioNatu(new ArrayList<Comunicacion>());
		
		//Limpiando list de Empresas de Persona Natura
		JuridicaController juridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		if(juridicaController!=null)
		juridicaController.setListPerJuridicaSocioNatu(new ArrayList<Persona>());
		
		//Agregado 10/03/2013
		blnMsgFecCumple = false;
		//Agregado 13/03/2013
		blnRegimenLaboral = false;
		blnRemunNeta = false;
		blnCondLaboral = false;
		
		//Agregado 18/03/2013
		cleanMsgError();
	}
	
	public void buscarSocio(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.buscarSocio-------------------------------------");
		//socioBusqueda.setIntCtasActivInactiv(socioBusqueda.getBlnCtasActivasInact()==null?0:);
		socioBusqueda.getSocio().getSocioEstructura().setIntTipoSocio(Constante.PARAM_T_TIPOSOCIO_TODOS);
		List<SocioComp> listSocioBusqueda = null;
		SocioFacadeLocal facade;
		try {
			facade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			listSocioBusqueda = facade.getListaSocioComp(socioBusqueda);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if(listSocioBusqueda!=null && listSocioBusqueda.size()>0){
			log.info("listSocioBusqueda.size(): " + listSocioBusqueda.size());
			setListaSocioBusqueda(listSocioBusqueda);
			//log.info("listSocioBusqueda.get(0).getStrTipoSocio(): "+listSocioBusqueda.get(0).getStrTipoSocio());
			//log.info("listSocioBusqueda.get(0).getModalidad(): "+listSocioBusqueda.get(0).getStrModalidad());
		}else{
			if(getListaSocioBusqueda()!=null)getListaSocioBusqueda().clear();
		}
	}
	
	/**
	 * Asociado al boton Buscar, recupera los socios segun filtros
	 * @param event
	 */
	public void buscarSocioFiltros(ActionEvent event){
		//List<SocioComp> listSocioBusqueda = null;
		SocioFacadeLocal facade;
		SocioComp socioBusq = null;
		try {
			facade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			
			socioBusq = new SocioComp();
			socioBusq.setIntBusquedaTipo(intBusquedaTipo);
			socioBusq.setStrBusqCadena(strBusqCadena.trim());
			socioBusq.setIntBusqSituacionCta(intBusqSituacionCta);
			socioBusq.setDtBusqFechaRegDesde(dtBusqFechaRegDesde);
			socioBusq.setDtBusqFechaRegHasta(dtBusqFechaRegHasta);
			listaSocioBusqueda = facade.getListaSocioPorFiltrosBusq(socioBusq);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Limpia campos de filtros de busqueda
	 * @param event
	 */
	public void limpiarFiltros(ActionEvent event){
		intBusquedaTipo = 0;
		strBusqCadena= "";
		intBusqSituacionCta=0;
		dtBusqFechaRegDesde= null;
		dtBusqFechaRegHasta= null;
	}
	
	/*public List<Socio> listaSocios(){
		
		return null;
	}*/
	
	public void showNewSocioNatu(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.setNewSocioNatu-------------------------------------");
		//limpiando bean de busqueda
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		System.out.println("Se ha limpiado personaBusqueda...");
		AperturaCuentaController aperturaCuentaController = (AperturaCuentaController)getSessionBean("aperturaCuentaController");
		cleanBeanSocioComp();
		setBlnShowDivFormSocio(true);
		setBlnShowFiltroPersonaNatu(true);
		setBlnShowDivFormSocioNatu(false);
		//Agregado 19/01/2013
		isDatosView = false;
		//Agregado 18/02/2013
		aperturaCuentaController.setBlnShowDivFormAperturaCuenta(false);
		aperturaCuentaController.setBlnShowDivPanelControlsAperturaCuenta(false);
		
		//Agregado 09/04/2013
		personaBusqueda.setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
		loadCboDni(event);
		personaBusqueda.getDocumento().setIntTipoIdentidadCod(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI));
	}
	
	public void hideFormSocioNatu(ActionEvent event){
		//limpiando bean de busqueda
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		AperturaCuentaController aperturaCuentaController = (AperturaCuentaController)getSessionBean("aperturaCuentaController");
		
		cleanBeanSocioComp();
		setBlnShowDivFormSocio(false);
		setBlnShowDivFormSocioNatu(false);
		//Agregado 19/01/2013
		isDatosView = false;
		//Agregado 18/02/2013
		aperturaCuentaController.setBlnShowDivFormAperturaCuenta(false);
		aperturaCuentaController.setBlnShowDivPanelControlsAperturaCuenta(false);
		//Agregado 11/03/2013
		//Agregado cdelosrios, 31/10/2013
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if(getSessionBean("aperturaCuentaController")!=null){
			sesion.removeAttribute("aperturaCuentaController");
		}
		if(getSessionBean("socioController")!=null){
			sesion.removeAttribute("socioController");
		}
		if(getSessionBean("beneficiarioController")!=null){
			sesion.removeAttribute("beneficiarioController");
		}
		//Fin agregado cdelosrios, 31/10/2013
	}
	
	public void grabarSocioNatural(ActionEvent event){
		//Limpieza de variables para la grabación.
//		beanSocioComp.getSocio().setId(null);
//		beanSocioComp.getPersona().setIntIdPersona(null);

		log.info("-------------------------------------Debugging socioController.grabarSocioNatural-------------------------------------");
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(!validarSocioNatural()){
			MessageController message = (MessageController)FacesContextUtil.getSessionBean("messageController");
			message.setWarningMessage(FacesContextUtil.MESSAGE_WARNING_ONVALIDATE);
			message.setBlnAccepted(true);
			return;
		}
		//agregando el DNI a los documentos de identidad, verificando que no exista
		Documento dni = null;
		if(getBeanSocioComp().getPersona().getListaDocumento()!=null){
			for(int i=0; i<getBeanSocioComp().getPersona().getListaDocumento().size(); i++){
				if(getBeanSocioComp().getPersona().getListaDocumento().get(i).getIntTipoIdentidadCod().toString().equals(Constante.PARAM_T_TIPODOCUMENTO_DNI)){
					dni = getBeanSocioComp().getPersona().getListaDocumento().get(i);
				}
			}
		}
		if(dni==null){
			dni = new Documento();
			dni.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			dni.setIntTipoIdentidadCod(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI));
			dni.setStrNumeroIdentidad(getBeanSocioComp().getPersona().getDocumento().getStrNumeroIdentidad());
			if(getBeanSocioComp().getPersona().getListaDocumento()==null){
				getBeanSocioComp().getPersona().setListaDocumento(new ArrayList<Documento>());
			}
			getBeanSocioComp().getPersona().getListaDocumento().add(dni);
		}
		
		//Seteando list de Ctas Bancarias de Persona Natural
		CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		List<CuentaBancaria> arrayCtas = ctaBancariaController.getListCtaBancariaSocioNatu();
		if(arrayCtas!=null)log.info("arrayCtas.size: "+arrayCtas.size());
		getBeanSocioComp().getPersona().setListaCuentaBancaria(arrayCtas);
		
		//Seteando list de Direcciones de Persona Natura
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		List<Domicilio> arrayDomi = domicilioController.getBeanListDirecSocioNatu();
		if(arrayDomi!=null)log.info("arrayDomi.size: "+arrayDomi.size());
		getBeanSocioComp().getPersona().setListaDomicilio(arrayDomi);
		
		//Seteando list de Comunicaciones de Persona Natural
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		List<Comunicacion> arrayComunica = comunicacionController.getListComuniSocioNatu();
		if(arrayComunica!=null)log.info("arrayComunica.size: "+arrayComunica.size());
		getBeanSocioComp().getPersona().setListaComunicacion(arrayComunica);
		
		//Seteando list de Empresas de Persona Natura
		JuridicaController juridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		List<Persona> arrayPerJuri = juridicaController.getListPerJuridicaSocioNatu();
		if(arrayPerJuri!=null)log.info("arrayPerJuri.size: "+arrayPerJuri.size());
		getBeanSocioComp().getPersona().setListaPersona(arrayPerJuri);
		
		//Seteando lista de PerLaboral
		getBeanSocioComp().getPersona().getNatural().setListaPerLaboral(new ArrayList<PerLaboral>());
		getBeanSocioComp().getPersona().getNatural().getListaPerLaboral().add(getBeanSocioComp().getPersona().getNatural().getPerLaboral());
		
		//Agregado 18/11/2012 - COMENTADO 27.02.2014 JCHAVEZ
//		if(beanSocioComp.getSocio().getListSocioEstructura()!=null){
//			for(SocioEstructura socioEstructura : beanSocioComp.getSocio().getListSocioEstructura()){
//				socioEstructura.setIntNivel(2);
//			}
//		}
		
		//Seteando valores 
		SocioFacadeLocal facade = null;
		try {
			facade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			
			//Agregado 27.02.2014 JCHAVEZ
			if(beanSocioComp.getSocio().getListSocioEstructura()!=null){
				EstructuraDetalle estDet = null;
				for(SocioEstructura socioEstructura : beanSocioComp.getSocio().getListSocioEstructura()){
					estDet = estructuraFacade.getEstructuraDetallePorSucuSubsucuYCodigo(socioEstructura);
					socioEstructura.setIntNivel(estDet.getId().getIntNivel());
				}
			}
			log.info("beanSocioComp.persona.intIdPersona: "+getBeanSocioComp().getPersona().getIntIdPersona());
			if(getBeanSocioComp().getSocio().getId()==null || getBeanSocioComp().getSocio().getId().getIntIdPersona()==null){
				log.info("beanSocioComp.getSocio(): "+beanSocioComp.getSocio());
				
				/////////// beanSocioComp.getSocio().getId().getIntIdEmpresa()
				if(beanSocioComp.getSocio().getId()== null){
					beanSocioComp.getSocio().setId(new SocioPK());
					beanSocioComp.getSocio().getId().setIntIdEmpresa(usuario.getEmpresa().getIntIdEmpresa());
					beanSocioComp.getSocio().getId().setIntIdPersona(beanSocioComp.getPersona().getIntIdPersona());
				}

				/* Inicio - Auditoria - GTorresBrousset 24.abr.2014 */
				List<Auditoria> listaAuditoriaPersona = new ArrayList<Auditoria>();
				List<Auditoria> listaAuditoriaSocio = new ArrayList<Auditoria>();
				if(beanSocioComp.getPersona().getIntIdPersona() == null) {
					log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
					beanSocioComp = facade.grabarSocioNatural(beanSocioComp, arrayRoles);
					listaAuditoriaPersona = generarAuditoriaPersona(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, beanSocioComp.getPersona());
					listaAuditoriaSocio = generarAuditoriaSocio(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, beanSocioComp.getSocio());
				} else {
					log.info("Tipo: PARAM_T_AUDITORIA_TIPO_UPDATE");
					beanSocioComp = facade.grabarSocioNatural(beanSocioComp, arrayRoles);
					listaAuditoriaPersona = generarAuditoriaPersona(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE, beanSocioComp.getPersona());
					listaAuditoriaSocio = generarAuditoriaSocio(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE, beanSocioComp.getSocio());
				}
				for(Auditoria auditoriaPersona : listaAuditoriaPersona) {
					grabarAuditoria(auditoriaPersona);
				}
				for(Auditoria auditoriaSocio : listaAuditoriaSocio) {
					grabarAuditoria(auditoriaSocio);
				}
				PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
				//personaFacade.grabarListaDinamicaPersonaRol(listaRol,socioComp.getSocio().getId().getIntIdPersona());
				if(arrayRoles!=null && arrayRoles.size()>0){
					for(PersonaRol rol : arrayRoles){
						rol.getId().setIntIdPersona(beanSocioComp.getSocio().getId().getIntIdPersona());
						personaFacade.grabarPersonaRol(rol);
					}
				}
			}else{
				beanSocioComp = facade.modificarSocioNatural(getBeanSocioComp(), arrayRoles);
				//Update auditoria
				List<Auditoria> listaAuditoriaPersona = new ArrayList<Auditoria>();
				List<Auditoria> listaAuditoriaDomicilio = new ArrayList<Auditoria>();
				List<Auditoria> listaAuditoriaCuentaBancaria = new ArrayList<Auditoria>();
				List<Auditoria> listaAuditoriaComunicaciones = new ArrayList<Auditoria>();
				List<Auditoria> listaAuditoriaDocumento = new ArrayList<Auditoria>();
				listaAuditoriaPersona = generarAuditoriaPersona(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE, beanSocioComp.getPersona());
				listaAuditoriaDomicilio = generarAuditoriaDomicilio(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, beanSocioComp.getPersona());
				listaAuditoriaCuentaBancaria = generarAuditoriaCuentaBancaria(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, beanSocioComp.getPersona());
				listaAuditoriaComunicaciones = generarAuditoriaComunicaciones(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, beanSocioComp.getPersona());
				listaAuditoriaDocumento = generarAuditoriaDocumento(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, beanSocioComp.getPersona());
				for(Auditoria auditoriaPersona : listaAuditoriaPersona) {
					grabarAuditoria(auditoriaPersona);
				}
				for(Auditoria auditoriaDomicilio : listaAuditoriaDomicilio) {
					grabarAuditoria(auditoriaDomicilio);
				}
				for(Auditoria auditoriaCuentaBancaria : listaAuditoriaCuentaBancaria) {
					grabarAuditoria(auditoriaCuentaBancaria);
				}
				for(Auditoria auditoriaComunicaciones : listaAuditoriaComunicaciones) {
					grabarAuditoria(auditoriaComunicaciones);
				}
				for(Auditoria auditoriaDocumento : listaAuditoriaDocumento) {
					grabarAuditoria(auditoriaDocumento);
				}
			}
			FacesContextUtil.setMessageSuccess(FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
			hideFormSocioNatu(null);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 	
	 * @return
	 */
	public Boolean validarSocioNatural(){
		log.info("-------------------------------------Debugging socioController.validarSocioNatural-------------------------------------");
		strRoldePersona = "";
		isValidSocioNatural = true;
		Persona persona = null;
		PersonaRol rol = null;
		log.info("listRoles.size: "+listRoles.size());
		
		if(beanSocioComp!=null){			
			for(int i=0; i<listRoles.size(); i++){
				rol = listRoles.get(i);
				log.info("rol.isChecked: "+rol.getIsChecked());
				if(rol.getIsChecked()!=null && rol.getIsChecked()){
					if (!(rol.getTabla().getIntIdDetalle().equals(Constante.PARAM_T_TIPOROL_NOSOCIO))) {
						DomicilioController domicilioController = (DomicilioController)FacesContextUtil.getSessionBean("domicilioController");
						if(domicilioController==null || domicilioController.getBeanListDirecSocioNatu()==null || domicilioController.getBeanListDirecSocioNatu().size()==0){
							FacesContextUtil.setMessageError("Debe ingresar almenos una dirección.");
							isValidSocioNatural = false;
						}
						
						ComunicacionController comunicacionController = (ComunicacionController)FacesContextUtil.getSessionBean("comunicacionController");
						if(comunicacionController==null || comunicacionController.getListComuniSocioNatu()==null || comunicacionController.getListComuniSocioNatu().size()==0){
							FacesContextUtil.setMessageError("Debe registrar almenos un medio de comunicación con el socio.");
							isValidSocioNatural = false;
						}
						
						if(beanSocioComp.getPersona().getNatural().getPerLaboral()==null ||
								beanSocioComp.getPersona().getNatural().getPerLaboral().getStrCentroTrabajo()==null || beanSocioComp.getPersona().getNatural().getPerLaboral().getStrCentroTrabajo().equals("") ||
								beanSocioComp.getPersona().getNatural().getPerLaboral().getDtInicioServicio()==null ||
								beanSocioComp.getPersona().getNatural().getPerLaboral().getBdRemuneracion()==null ||
								beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral()==null || beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(0) || 
								beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCondicionLaboralDet()==null || beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCondicionLaboralDet().equals(0) ||
								beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCargo()==null ||
								beanSocioComp.getPersona().getNatural().getPerLaboral().getIntRegimenLaboral()==null){
							FacesContextUtil.setMessageError("Complete todos los Datos Laborales. Solicitud ONP es opcional.");
							isValidSocioNatural = false;
						}
					}
				}
			}
			
			
			if(beanSocioComp.getSocio().getListSocioEstructura()==null || beanSocioComp.getSocio().getListSocioEstructura().size()==0){
				FacesContextUtil.setMessageError("Debe ingresar almenos un registro de Unidad Ejecutora de tipo Origen.");
				isValidSocioNatural = false;
			}else{
				int countOrigen = 0; //Debe haber sólo un registro de tipo Origen en Unidad Ejecutora
				for(SocioEstructura socioEstructura : beanSocioComp.getSocio().getListSocioEstructura()){
					if(socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
						countOrigen ++;
					}
				}
				//if(countOrigen!=1){
				if(countOrigen>1){
					FacesContextUtil.setMessageError("Debe haber sólo un único registro de Unidad Ejecutora de tipo Origen.");
					isValidSocioNatural = false;
				}
			}
			
			//Req. 1
//			SocioComp socioComp = null;
			try {				
				// CGD - 03.01.2014
				persona = personaFacade.getPersonaPorPK(beanSocioComp.getPersona().getIntIdPersona());

			} catch (BusinessException e) {
				log.error(e.getMessage(), e);
			}

			if(persona != null){
				if(persona.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)!=0){
					FacesContextUtil.setMessageError("La Persona consultada se encuentra registrada como fallecida/o");
					isValidSocioNatural = false;
				}
			}
		}
		return isValidSocioNatural;
	}
	
	public void grabarContratoDePerLaboral(List<PerLaboral> listaPerlaboral){
		Archivo archivo = null;
		for(PerLaboral perlaboral : listaPerlaboral){
			try {
				GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
				archivo = generalFacade.grabarArchivo(perlaboral.getContrato());
				
				if(archivo!=null){
					perlaboral.setIntTipoArchivoContrato(archivo.getId().getIntParaTipoCod());
					perlaboral.setIntItemArchivoContrato(archivo.getId().getIntItemArchivo());
					perlaboral.setIntItemHistoricoContrato(archivo.getId().getIntItemHistorico());
					
					FileUtil.renombrarArchivo(archivo.getTipoarchivo().getStrRuta(), archivo.getTipoarchivo().getStrRuta()+"\\"+archivo.getStrNombrearchivo());
				}
			} catch (EJBFactoryException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addListEntidadSocio(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.addListEntidadSocio-------------------------------------");
		if(!validarSocioEstructura()){
			return;
		}
		log.info("socioEstructura.intIdSucursalUsuario: "+getSocioEstructura().getIntIdSucursalUsuario());
		log.info("socioEstructura.idSubSucursalUsuario: "+getSocioEstructura().getIntIdSubSucursalUsuario());
		log.info("socioEstructura.intIdSucursalAdministra: "+getSocioEstructura().getIntIdSucursalAdministra());
		log.info("socioEstructura.intIdSubsucurAdministra: "+getSocioEstructura().getIntIdSubsucurAdministra());
		log.info("socioEstructura.getIntModalidad: "+getSocioEstructura().getIntModalidad());
		log.info("socioEstructura.intCodigo: "+getSocioEstructura().getIntCodigo());
		log.info("socioEstructura.strCodigoPlanilla: "+getSocioEstructura().getStrCodigoPlanilla());
		log.info("socioEstructura.intTipoEstructura: "+getSocioEstructura().getIntTipoEstructura());
		getSocioEstructura().setDtFechaRegistro(new Date());
		
		SocioEstructura socioEstruc = new SocioEstructura();
		socioEstruc.setIntEmpresaSucUsuario(getSocioEstructura().getIntEmpresaSucUsuario());
		socioEstruc.setIntIdSucursalUsuario(getSocioEstructura().getIntIdSucursalUsuario());
		socioEstruc.setIntIdSubSucursalUsuario(getSocioEstructura().getIntIdSubSucursalUsuario());
		socioEstruc.setIntEmpresaSucAdministra(getSocioEstructura().getIntEmpresaSucAdministra());
		socioEstruc.setIntIdSucursalAdministra(getSocioEstructura().getIntIdSucursalAdministra());
		socioEstruc.setIntIdSubsucurAdministra(getSocioEstructura().getIntIdSubsucurAdministra());
		socioEstruc.setIntTipoSocio(getSocioEstructura().getIntTipoSocio());
		socioEstruc.setIntModalidad(getSocioEstructura().getIntModalidad());
		socioEstruc.setIntCodigo(getSocioEstructura().getIntCodigo());
		socioEstruc.setStrCodigoPlanilla(getSocioEstructura().getStrCodigoPlanilla());
		socioEstruc.setIntTipoEstructura(getSocioEstructura().getIntTipoEstructura());
		socioEstruc.setDtFechaRegistro(getSocioEstructura().getDtFechaRegistro());
		socioEstruc.setStrFechaRegistro(sdfshort.format(getSocioEstructura().getDtFechaRegistro()));
		socioEstruc.setIntEstadoCod(getSocioEstructura().getIntEstadoCod());
		socioEstruc.setIntEmpresaUsuario(getSocioEstructura().getIntEmpresaUsuario());
		socioEstruc.setIntPersonaUsuario(getSocioEstructura().getIntPersonaUsuario());
		
		if(getBeanSocioComp().getSocio().getListSocioEstructura()==null){
			getBeanSocioComp().getSocio().setListSocioEstructura(new ArrayList<SocioEstructura>());
		}
		getBeanSocioComp().getSocio().getListSocioEstructura().add(socioEstruc);
		
		//Limpiar
		setSocioEstructura(new SocioEstructura());
		//grabar los insert para la auditoria autor rVillarreal 21/06/2014
		SocioFacadeLocal facade = null;
		try {
			facade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
		List<Auditoria> listaAuditoriaSocio = new ArrayList<Auditoria>();
		if(beanSocioComp.getPersona().getIntIdPersona() != null) {
			log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
			listaAuditoriaSocio = generarAuditoriaSocio(Constante.PARAM_T_AUDITORIA_TIPO_INSERT, beanSocioComp.getSocio());
		} 
		for(Auditoria auditoriaSocio : listaAuditoriaSocio) {
			grabarAuditoria(auditoriaSocio);
		}
		
	}catch (EJBFactoryException e) {
		e.printStackTrace();
	}catch (BusinessException e) {
		e.printStackTrace();
	}	
} 
	
	public Boolean validarSocioEstructura(){
		log.info("-------------------------------------Debugging SocioController.validarSocioEstructura-------------------------------------");
		isValidSocioEstructura = true;
		
		//Verificar que se haya buscado y seleccionado una Unidad Ejecutora
		log.info("getSocioEstructura.intIdSucursalAdministra: "+getSocioEstructura().getIntIdSucursalAdministra());
		if(getSocioEstructura().getIntIdSucursalAdministra()==null){
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setWarningMessage("Primero de Buscar y seleccionar una Entidad.");
			message.setBlnAccepted(true);
			isValidSocioEstructura = false;
			return isValidSocioEstructura;
		}
		//Verificar que sólo haya una Unidad Ejecutora de tipo Origen
		if(getSocioEstructura().getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
			if(getBeanSocioComp().getSocio().getListSocioEstructura()!=null){
				for(SocioEstructura est : getBeanSocioComp().getSocio().getListSocioEstructura()){
					if(est.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
						MessageController message = (MessageController)getSessionBean("messageController");
						message.setWarningMessage("Sólo puede haber una Unidad Ejecutora de tipo Origen por cada Socio.");
						message.setBlnAccepted(true);
						isValidSocioEstructura = false;
						return isValidSocioEstructura;
					}
				}
			}
		}
		//Varificar que en caso haya una Estructura con Modalidad CAS no se permita estructuras con
		//modalidad Haberes o Incentivos y viceversa
		if(getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
			if(getBeanSocioComp().getSocio().getListSocioEstructura()!=null && getBeanSocioComp().getSocio().getListSocioEstructura().size()>0){
				for(SocioEstructura est : getBeanSocioComp().getSocio().getListSocioEstructura()){
					if(est.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
							|| est.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
						MessageController message = (MessageController)getSessionBean("messageController");
						message.setWarningMessage("No se permite registros de modalidad CAS junto con modalidades Haberes o Incentivos y viceversa.");
						message.setBlnAccepted(true);
						isValidSocioEstructura = false;
						return isValidSocioEstructura;
					}
				}
			}
		}else if(getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)
				|| getSocioEstructura().getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)){
			if(getBeanSocioComp().getSocio().getListSocioEstructura()!=null && getBeanSocioComp().getSocio().getListSocioEstructura().size()>0){
				for(SocioEstructura est : getBeanSocioComp().getSocio().getListSocioEstructura()){
					if(est.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						MessageController message = (MessageController)getSessionBean("messageController");
						message.setWarningMessage("No se permite registros de modalidad CAS junto con modalidades Haberes o Incentivos y viceversa.");
						message.setBlnAccepted(true);
						isValidSocioEstructura = false;
						return isValidSocioEstructura;
					}
				}
			}
		}
		//Verificar que no hayan registros con la misma Unidad Ejecutora, Tipo de Socio y Modalidad
		if(getBeanSocioComp().getSocio().getListSocioEstructura()!=null && getBeanSocioComp().getSocio().getListSocioEstructura().size()>0){
			for(SocioEstructura est : getBeanSocioComp().getSocio().getListSocioEstructura()){
				if(est.getIntCodigo().equals(getSocioEstructura().getIntCodigo())
						&& est.getIntTipoSocio().equals(getSocioEstructura().getIntTipoSocio())
						&& est.getIntModalidad().equals(getSocioEstructura().getIntModalidad())){
					MessageController message = (MessageController)getSessionBean("messageController");
					message.setWarningMessage("No se permite más de un registro con la misma Unidad Ejecutora, Tipo de Socio y Modalidad.");
					message.setBlnAccepted(true);
					isValidSocioEstructura = false;
					return isValidSocioEstructura;
				}
			}
		}
		return isValidSocioEstructura;
	}
	
	public void validarSocioNatural(ActionEvent event){
		log.info("-------------------------------------Debugging socioController.validarSocioNatural-------------------------------------");
//		Persona persona = null;
		if(personaBusqueda.getIntTipoPersonaCod().equals(0)){
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setWarningMessage("Debe seleccionar el Tipo de Persona.");
			message.setBlnAccepted(true);
			return;
		}else if(personaBusqueda.getIntTipoPersonaCod().toString().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			return;
		}
		
		Integer intTipoDoc = getPersonaBusqueda().getDocumento().getIntTipoIdentidadCod();
		String strNumIdentidad = getPersonaBusqueda().getDocumento().getStrNumeroIdentidad();
		
		SocioComp socioComp = null;
		SocioFacadeLocal facade;
		try {
			facade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			socioComp = facade.getSocioNatuPorDocIdentidadYIdEmpresaYTipoVinculo(intTipoDoc,strNumIdentidad,SESION_IDEMPRESA,Constante.PARAM_T_TIPOVINCULO_TIENEEMPRESA);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(socioComp!=null && socioComp.getPersona()!=null){ //Entonces ya ha sido agregado al sistema
			/**
			 * @author Christian De los Ríos - 04/04/2013
			 * Validar que si el DNI de la persona consultada existe, entonces en el caso de los Administradores
			 * no permita continuar con el grabado.
			 * 
			 * */
			Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
			if( (intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_CENTRAL) || 
					intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_FILIAL) || 
					intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_LIMA)) ){
				MessageController message = (MessageController)getSessionBean("messageController");
				message.clean();
				message.setWarningMessage("Su Perfil no está habilitado para poder registrar un Socio que está registrado en el Padrón.");
				message.setBlnAccepted(false);
				return;
			}
			
			setBeanSocioComp(socioComp);
			log.info("getBeanSocioComp().getPersona(): "+getBeanSocioComp().getPersona());
			setBlnShowFiltroPersonaNatu(false);
			setBlnShowDivFormSocioNatu(true);
			
			//Seteando roles
			if(socioComp.getPersona().getPersonaEmpresa()!=null &&
					socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()!=null){
				log.info("socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size(): "+socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size());
				for(PersonaRol rol : socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()){
					socioComp.setStrRoles(rol.getTabla().getStrDescripcion()+((socioComp.getStrRoles()==null)?"":","+socioComp.getStrRoles()));
				}
			}
			log.info("getBeanSocioComp().getStrRoles(): "+getBeanSocioComp().getStrRoles());
			strRoldePersona = beanSocioComp.getStrRoles();
		    beanSocioComp.setStrRoles("");
		      
			//Seteando list de Ctas Bancarias de Persona Natural
			if(socioComp.getPersona().getListaCuentaBancaria()!=null){
				CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
				ctaBancariaController.setListCtaBancariaSocioNatu(socioComp.getPersona().getListaCuentaBancaria());
			}
			
			//Seteando list de Direcciones de Persona Natura
			if(socioComp.getPersona().getListaDomicilio()!=null){
				DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
				domicilioController.setBeanListDirecSocioNatu(socioComp.getPersona().getListaDomicilio());
			}
			
			//Seteando list de Comunicaciones de Persona Natural
			if(socioComp.getPersona().getListaComunicacion()!=null){
				ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
				comunicacionController.setListComuniSocioNatu(socioComp.getPersona().getListaComunicacion());
			}
			
			//Seteando list de Empresas de Persona Natural
			if(socioComp.getPersona().getListaPersona()!=null){
				log.info("socioComp.persona.listaPersona.size: "+socioComp.getPersona().getListaPersona().size());
				JuridicaController juridicaController = (JuridicaController)getSessionBean("perJuridicaController");
				juridicaController.setListPerJuridicaSocioNatu(socioComp.getPersona().getListaPersona());
			}
		}else{ //No está en el sistema y se tiene que buscar en Padron
			try {
				EstructuraFacadeLocal estructuraFacade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
				socioComp = estructuraFacade.getSocioNatuPorLibElectoral(getPersonaBusqueda().getDocumento().getStrNumeroIdentidad());
				if(socioComp!=null && socioComp.getPadron()!=null){
					setBeanSocioComp(socioComp);
				}
			} catch (EJBFactoryException e) {
				e.printStackTrace();
			} catch (BusinessException e) {
				e.printStackTrace();
			}
		}
		setBeanSocioComp(socioComp);
		
		if(socioComp!=null && socioComp.getPadron()!=null){
			MessageController message = (MessageController)getSessionBean("messageController");
			message.setStrFunctionValidator("validarSocioPadron()");
			message.setWarningMessage("La persona aún no se encuentra registrada en el sistema, " +
					"sin embargo se haya en los datos de MINSA. ¿Desea registrarla ahora?");
			message.setBlnAccepted(true);
			return;
		}
		
		if(socioComp==null){ //No se encontro en el sistema ni en el Padron de MINSA
			//Req 4. 14/03/2013
			Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
			MessageController message = (MessageController)getSessionBean("messageController");
			
			if( (intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_CENTRAL) || 
					intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_FILIAL) || 
					intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_LIMA)) ){
				message.clean();
				message.setWarningMessage("Su Perfil no está habilitado para poder registrar un Socio " +
						"que está registrado en el Padrón.");
				message.setBlnAccepted(false);
				return;
			}
			//Fin Req.
			message.clean();
			message.setWarningMessage("La persona buscada no se encuentra registrada. " +
					"Puede presionar Aceptar para hacer un nuevo registro.");
			message.setBlnAccepted(true);
			return;
		}
		isDatosView = false;
	}
	
	public void addOtroDocumento(ActionEvent event){
		log.info("-------------------------------------Debugging socioController.addOtroDocumento-------------------------------------");
		log.info("socioComp.intTipoDocIdentidad: "+getBeanSocioComp().getIntTipoDocIdentidad());
		log.info("socioComp.strDocIdentidad: "+getBeanSocioComp().getStrDocIdentidad());
		/*
		MessageController message = (MessageController)getSessionBean("messageController");
		if(getBeanSocioComp().getStrDocIdentidad()==null || getBeanSocioComp().getStrDocIdentidad().equals("")){
			message.setWarningMessage("No ha ingresado ningún valor del documento de identidad.");
			return;
		}*/
		
		Documento documento = new Documento();
		documento.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
		documento.setIntTipoIdentidadCod(getBeanSocioComp().getIntTipoDocIdentidad());
		documento.setStrNumeroIdentidad(getBeanSocioComp().getStrDocIdentidad());
		
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
		
		if(getBeanSocioComp().getPersona().getListaDocumento()!=null){
			for(int i=0; i<getBeanSocioComp().getPersona().getListaDocumento().size(); i++){
				Documento doc = getBeanSocioComp().getPersona().getListaDocumento().get(i);
				if(documento.getIntTipoIdentidadCod().equals(doc.getIntTipoIdentidadCod())){
					//message.setWarningMessage("Ya existe registrado un documento de tipo: "+documento.getTabla().getStrDescripcion()+".");
					return;
				}
			}
		}
		
		if(getBeanSocioComp().getPersona().getListaDocumento()==null){
			getBeanSocioComp().getPersona().setListaDocumento(new ArrayList<Documento>());
		}
		getBeanSocioComp().getPersona().getListaDocumento().add(documento);
		
		//Limpiar
		getBeanSocioComp().setIntTipoDocIdentidad(0); //Opción Seleccione
		getBeanSocioComp().setStrDocIdentidad("");
}
	
	public void quitarOtrosDocumentos(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.quitarOtrosDocumentos-------------------------------------");
		String rowKey = getRequestParameter("rowKeyOtrosDocumentos");
		Documento docTmp = null;
		if(getBeanSocioComp().getPersona().getListaDocumento()!=null){
			for(int i=0; i<getBeanSocioComp().getPersona().getListaDocumento().size(); i++){
				if(Integer.parseInt(rowKey)==i){
					Documento documento = getBeanSocioComp().getPersona().getListaDocumento().get(i);
					log.info("documento.id: "+documento.getId());
					if(documento.getId()!=null && documento.getId().getIntItemDocumento()!=null){
						docTmp = getBeanSocioComp().getPersona().getListaDocumento().get(i);
						docTmp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
					}
					getBeanSocioComp().getPersona().getListaDocumento().remove(i);
					break;
				}
			}
			if(docTmp!=null){
				getBeanSocioComp().getPersona().getListaDocumento().add(docTmp);
			}
		}
		log.info("beanSocioComp.persona.listaDocumento.size: "+getBeanSocioComp().getPersona().getListaDocumento().size());
	}
	
	public void obtenerSocioEstructura(ActionEvent event) throws IOException{
		log.info("-------------------------------------Debugging SocioController.obtenerSocioEstructura-------------------------------------");
		SocioComp socioComp = null;
		fileFirmaSocio = null;
		fileFotoSocio = null;
		fileContrato = null;
		try {
			SocioFacadeLocal socioFacade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			socioComp = socioFacade.getSocioNatural(socioNatuSelected.getSocio().getId());
			if(socioComp==null)return;
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("socioComp.persona.natural.strNombres: "+socioComp.getPersona().getNatural().getStrNombres());
		
		//Lista de Roles
		if(socioComp.getPersona().getPersonaEmpresa()!=null && 
				socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()!=null){
			//log.info("socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size(): "+socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().size());
			arrayRoles = new ArrayList<PersonaRol>();
			listRoles = new ArrayList<PersonaRol>();
			for(PersonaRol rol : socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()){
				socioComp.setStrRoles(rol.getTabla().getStrDescripcion()+((socioComp.getStrRoles()==null)?"":","+socioComp.getStrRoles()));
				arrayRoles.add(rol);
				listRoles.add(rol);
			}
		}
		
		//Mostrar la Firma del Socio
		if(socioComp.getPersona().getNatural().getFirma()!=null){
			Archivo firma = socioComp.getPersona().getNatural().getFirma();
			log.info("Path Firma: "+firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
			File fileTemp = new File(firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
			if(fileTemp.exists()){
				byte[] byteImg = FileUtil.getDataImage(firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
				MyFile file = new MyFile();
		        file.setLength(byteImg.length);
		        file.setName(firma.getStrNombrearchivo());
		        file.setData(byteImg);
		        setFileFirmaSocio(file);
			}
		}
		
		//Mostrar la Foto del Socio
		if(socioComp.getPersona().getNatural().getFoto()!=null){
			Archivo foto = socioComp.getPersona().getNatural().getFoto();
			log.info("Path Foto: "+foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
			File fileTemp = new File(foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
			if(fileTemp.exists()){
				byte[] byteImg = FileUtil.getDataImage(foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
				MyFile file = new MyFile();
		        file.setLength(byteImg.length);
		        file.setName(foto.getStrNombrearchivo());
		        file.setData(byteImg);
		        setFileFotoSocio(file);
			}
		}
		
		//Mostrar
		setBlnShowDivFormSocio(true);
		setBlnShowFiltroPersonaNatu(false);
		setBlnShowDivFormSocioNatu(true);
		
		setBeanSocioComp(socioComp);
		
		//Seteando list de Ctas Bancarias de Persona Natural
		CuentaBancariaController ctaBancariaController = (CuentaBancariaController)getSessionBean("ctaBancariaController");
		ctaBancariaController.setListCtaBancariaSocioNatu(socioComp.getPersona().getListaCuentaBancaria());
		
		//Seteando list de Direcciones de Persona Natura
		DomicilioController domicilioController = (DomicilioController)getSessionBean("domicilioController");
		domicilioController.setBeanListDirecSocioNatu(socioComp.getPersona().getListaDomicilio());
		
		//Seteando list de Comunicaciones de Persona Natural
		ComunicacionController comunicacionController = (ComunicacionController)getSessionBean("comunicacionController");
		comunicacionController.setListComuniSocioNatu(socioComp.getPersona().getListaComunicacion());
		
		//Seteando list de Empresas de Persona Natural
		if(socioComp.getPersona().getListaPersona()!=null)log.info("socioComp.persona.listaPersona.size: "+socioComp.getPersona().getListaPersona().size());
		JuridicaController juridicaController = (JuridicaController)getSessionBean("perJuridicaController");
		juridicaController.setListPerJuridicaSocioNatu(socioComp.getPersona().getListaPersona());
		
		//Seteando lista de PerLaboral
		if(socioComp.getPersona().getNatural().getPerLaboral()!=null){
			getBeanSocioComp().getPersona().getNatural().setPerLaboral(socioComp.getPersona().getNatural().getPerLaboral());
			loadListDetCondicionLaboral(socioComp.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral());
		}
		
		//Inicializar PerLaboral y Contrato si son null
		if(socioComp.getPersona().getNatural().getPerLaboral()==null)socioComp.getPersona().getNatural().setPerLaboral(new PerLaboral());
		if(socioComp.getPersona().getNatural().getPerLaboral().getContrato()==null)socioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
		setBlnShowDivPanelControlsSocio(true);
		
		//Agregado 19/01/2013
		//blnShowDivFormAperturaCuenta = false;
		isDatosView = true;
	}
	
	public void setSelectedSocioNatu(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.setSelectedSocioNatu-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowSocioNatu"));
		String selectedRow = getRequestParameter("rowSocioNatu");
		SocioComp socioComp = null;
		for(int i=0; i<listaSocioBusqueda.size(); i++){
			socioComp = listaSocioBusqueda.get(i);
			if(i == Integer.parseInt(selectedRow)){
				setSocioNatuSelected(socioComp);
				break;
			}
		}
		log.info("ed.id.intCodigo: "+socioComp.getPersona().getNatural().getIntIdPersona());
	}
	
	public void validarMensaje(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.validarMensaje-------------------------------------");
		MessageController message = (MessageController)getSessionBean("messageController");
		log.info("message: "+message);
		log.info("message.getBlnAccepted(): "+message.getBlnAccepted());
		if(message.getBlnAccepted()){
			setBlnShowFiltroPersonaNatu(false);
			setBlnShowDivFormSocioNatu(true);
			cleanBeanSocioComp();
			getBeanSocioComp().getPersona().getDocumento().setStrNumeroIdentidad(getPersonaBusqueda().getDocumento().getStrNumeroIdentidad()); //Tipo de Persona
			getBeanSocioComp().getPersona().setIntTipoPersonaCod(getPersonaBusqueda().getIntTipoPersonaCod()); //Tipo de Persona
		}else{
			setBlnShowDivFormSocioNatu(false);
		}
	}
	
	public void cleanMsgError(){
		msgTxtDsctoJudicialYPerfil = "";
		msgTxtDsctoJudicial = "";
		msgTxtLicencPermiso = "";
		msgTxtLicencSubvencPermiso = "";
		msgTxtRolPersona = "";
		msgTxtPermisoSucursal = "";
	}
	
	private boolean isValidarPadron(Padron padron){
		log.info("-------------------------------------Debugging SocioController.isValidarPadron-------------------------------------");
		boolean isValidPadron = true;
		/**
		 * @author Christian De los Ríos
		 * 10-03-2013
		 * 
		 * */
		Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		Integer intIdPerfil = usuario.getPerfil().getId().getIntIdPerfil();
		
		//Agregado 19/03/2013; Comentado 27.02.2014
//		List<PersonaEmpresa> listaPersonaEmpresa = new ArrayList<PersonaEmpresa>();
//		List<PersonaRol> listaRol = new ArrayList<PersonaRol>();
		
		//Req 6.
		if(padron.getBdPorJud().compareTo(BigDecimal.ZERO)>0){
			if(!intIdPerfil.equals(11)){ //Asociativo
				msgTxtDsctoJudicialYPerfil = "Dado que el socio tiene un Descuento Judicial de " + padron.getBdPorJud() +"% " +
						"solo un usuario con perfil Asociativo puede realizar el registro.";
				//FacesContextUtil.setMessageError("Dado que el socio tiene un Descuento Judicial de " + padron.getBdPorJud() +"% " +
						//"solo un usuario con perfil Asociativo puede realizar el registro.");
				isValidPadron = false;
			}else{
				msgTxtDsctoJudicialYPerfil = "";
			}
		}
		
		/**
		 * @author Christian De los Ríos - 09/04/2013
		 * Validando que la sucursal sea la misma para el Socio en consulta.
		 * 
		 * */
		//SocioComp socioComp = null;
		List<EstructuraDetalle> listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();
		EstructuraDetalle estructuraDetalle = null;
		
		if(padron.getStrLibEle()!=null && padron.getStrPro()!=null){
			try {
				listaEstructuraDetalle = estructuraFacade.getListaEstructuraDetallePorCodExterno(padron.getStrPro());
			} catch (BusinessException e) {
				e.printStackTrace();
			}
			
			if(intIdPerfil.equals(11)){//Asociativo
				msgTxtPermisoSucursal = "";
			}else{
				if(listaEstructuraDetalle!=null && listaEstructuraDetalle.size()>0){
					for(EstructuraDetalle estrucDetalle : listaEstructuraDetalle){
						try {
							estructuraDetalle = estructuraFacade.getEstructuraDetallePorCodSocioYTipoSocYModalidad(estrucDetalle.getId().getIntCodigo(), estrucDetalle.getIntParaTipoSocioCod(), estrucDetalle.getIntParaModalidadCod());
						} catch (BusinessException e) {
							log.error(e.getMessage(), e);
						}
						if(estructuraDetalle!=null){
							if(!estructuraDetalle.getIntSeguSucursalPk().equals(SESION_IDSUCURSAL) && 
								!estructuraDetalle.getIntSeguSubSucursalPk().equals(SESION_IDSUBSUCURSAL)){
								FacesContextUtil.setMessageError("Ud. no está autorizado a realizar acciones sobre esta jurisdicción.");
								msgTxtPermisoSucursal = "Ud. no está autorizado a realizar acciones sobre esta jurisdicción.";
								isValidPadron = false;
								break;
							} else if((estructuraDetalle.getIntSeguSucursalPk().equals(SESION_IDSUCURSAL) 
									&& estructuraDetalle.getIntSeguSubSucursalPk().equals(SESION_IDSUBSUCURSAL)) 
									&&
									( !((""+Constante.ADMINISTRADOR_ZONAL_CENTRAL +
										Constante.ADMINISTRADOR_ZONAL_FILIAL +
										Constante.ADMINISTRADOR_ZONAL_LIMA)
										).contains(intIdPerfil.toString()) )&&
									/*(!intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_CENTRAL) || 
									!intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_FILIAL) || 
									!intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_LIMA)) &&*/
									(padron != null)){
								FacesContextUtil.setMessageError("Su Perfil no está habilitado para poder registrar un Socio que está registrado en el Padrón.");
								isValidPadron = false;
								break;
							} else {
								msgTxtPermisoSucursal = "";
							}
						}
					}
				}
			}
		}
		
		//Fin Validación
		/*if( ((intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_CENTRAL) || 
				intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_FILIAL) || 
				intIdPerfil.equals(Constante.ADMINISTRADOR_ZONAL_LIMA)) &&
				(padron != null))){
			FacesContextUtil.setMessageError("Su Perfil no está habilitado para poder registrar un Socio que está registrado en el Padrón.");
			isValidPadron = false;
		}*/
		
		return isValidPadron;
	}
	
	public void validarSocioPadron(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.validarSocioPadron-------------------------------------");
		Padron padron = this.beanSocioComp.getPadron();
		List<EstructuraDetalle> listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();
		cleanBeanSocioComp();
		this.beanSocioComp.setPadron(padron);
		this.beanSocioComp.getPersona().getDocumento().setStrNumeroIdentidad(padron.getStrLibEle());
		this.beanSocioComp.getPersona().getNatural().setDtFechaNacimiento(padron.getDtFecNac());
		this.beanSocioComp.getPersona().setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
		
		/**
		 * @author Christian De los Ríos
		 * Agregado 05-03-2013
		 * Req: Datos a migrar de padrón
		 * 
		 * */
		
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		Integer intIdPerfil = usuarioSesion.getPerfil().getId().getIntIdPerfil();
		
		if(isValidarPadron(padron)==false){
			return;
		}
		
		//3.d
		EstructuraComp estrucComp = new EstructuraComp();
		estrucComp.setEstructuraDetalle(new EstructuraDetalle());
		estrucComp.getEstructuraDetalle().setIntSeguSucursalPk(SESION_IDSUCURSAL);
		estrucComp.getEstructuraDetalle().setIntSeguSubSucursalPk(SESION_IDSUBSUCURSAL);
		
		try {
			EstructuraFacadeLocal facade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			listaEstructuraDetalle = facade.getConveEstrucDetPlanilla(estrucComp);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		
		if(listaEstructuraDetalle!=null && listaEstructuraDetalle.size()>0){
			int cntExist = 0;
			for(EstructuraDetalle ed : listaEstructuraDetalle){
				
				if(ed.getIntSeguSucursalPk().equals(SESION_IDSUCURSAL) &&
						ed.getIntSeguSubSucursalPk().equals(SESION_IDSUBSUCURSAL)){
					String strTipoPla = padron.getStrTipoPla();
					if(strTipoPla.equals("1") || strTipoPla.equals("2") || strTipoPla.equals("3") || 
							strTipoPla.equals("4") || strTipoPla.equals("9")){
						ed.setIntParaTipoSocioCod(Constante.PARAM_T_TIPOSOCIO_ACTIVO);
					}else{
						ed.setIntParaTipoSocioCod(Constante.PARAM_T_TIPOSOCIO_CESANTE);
					}
					
					//if((padron.getStrPro()+padron.getId().getIntParaModalidadCod()+padron.getStrTipoPla()).
					if((padron.getStrPro()+padron.getId().getIntParaModalidadCod()+ed.getIntParaTipoSocioCod()).
							equals(ed.getStrCodigoExterno()+ed.getIntParaModalidadCod()+ed.getIntParaTipoSocioCod())){
						cntExist++;
						this.socioEstructura.setIntEmpresaSucUsuario(SESION_IDEMPRESA);
						this.socioEstructura.setIntIdSucursalUsuario(SESION_IDSUCURSAL);
						this.socioEstructura.setIntIdSubSucursalUsuario(SESION_IDSUBSUCURSAL);
						this.socioEstructura.setIntEmpresaSucAdministra(ed.getIntPersEmpresaPk());
						this.socioEstructura.setIntIdSucursalAdministra(ed.getIntSeguSucursalPk());
						this.socioEstructura.setIntIdSubsucurAdministra(ed.getIntSeguSubSucursalPk());
						this.socioEstructura.setIntTipoSocio(ed.getIntParaTipoSocioCod());
						this.socioEstructura.setIntModalidad(ed.getIntParaModalidadCod());
						this.socioEstructura.setIntNivel(ed.getId().getIntNivel());
						this.socioEstructura.setIntCodigo(ed.getId().getIntCodigo());
						this.socioEstructura.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						this.socioEstructura.setIntEmpresaUsuario(SESION_IDEMPRESA);
						this.socioEstructura.setIntPersonaUsuario(SESION_IDUSUARIO);
						this.socioEstructura.setIntTipoEstructura(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN);
						this.socioEstructura.setStrCodigoPlanilla(padron.getStrPlaza());
						
						addListEntidadSocio(event);
						break;
					}
				} else {
					MessageController message = (MessageController)getSessionBean("messageController");
					message.setWarningMessage("Ud. no tiene autorización para realizar cambios sobre esta jurisdicción.");
					message.setBlnAccepted(true);
					return;
				}
			}
			
			if(cntExist==0){
				FacesContextUtil.setMessageError("La persona consultada en el padrón no tiene coincidencia " +
						"con ninguna Estructura.");
			}
			
		} else { //3.e
			if(!intIdPerfil.equals(11)){
				FacesContextUtil.setMessageError("Este socio no pertenece a su jurisdicción de administración");
				return;
			}
		}
		
		//3.a
		this.beanSocioComp.getPersona().getNatural().getPerLaboral().setBdRemuneracion(padron.getBdLiquid());
		blnRemunNeta = true;
		
		//3.c
		log.info("padron.getStrRegim(): " + padron.getStrRegim());
		String strRegimen = padron.getStrRegim()==null?"":padron.getStrRegim();
		Pattern p = Pattern.compile("[a-zA-Z]");
		Matcher m = p.matcher(strRegimen);
		if(m.find()){
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntRegimenLaboral(Constante.PARAM_T_REGIMENLABORAL_AFP);
			blnRegimenLaboral = true;
		}else{
			if(strRegimen.equals("1")){
				this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntRegimenLaboral(Constante.PARAM_T_REGIMENLABORAL_ONP_19990);
				blnRegimenLaboral = true;
			}else if(strRegimen.equals("2")) {
				this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntRegimenLaboral(Constante.PARAM_T_REGIMENLABORAL_ONP_20530);
				blnRegimenLaboral = true;
			}else{
				//this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntRegimenLaboral(new Integer(null));
				blnRegimenLaboral = false;
			}
		}
		
		//3.d
		//this.getSocioEstructura().setStrCodigoPlanilla(padron.getStrPlaza());
		
		//3.f
		blnCondLaboral = true;
		if(padron.getStrTipoPla().equals("1")){
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboral(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO);
			loadListDetCondicionLaboralByIntIdCondLaboral(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO);
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboralDet(Constante.PARAM_T_DETALLECONDICIONLABORAL_NOMBRADOPROFESIONAL);
		}else if(padron.getStrTipoPla().equals("2")){
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboral(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO);
			loadListDetCondicionLaboralByIntIdCondLaboral(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO);
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboralDet(Constante.PARAM_T_DETALLECONDICIONLABORAL_NOMBRADOADMINISTRATIVO);
		}else if(padron.getStrTipoPla().equals("3")){
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboral(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO);
			loadListDetCondicionLaboralByIntIdCondLaboral(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO);
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboralDet(Constante.PARAM_T_DETALLECONDICIONLABORAL_CONTRATADOPROFESIONAL);
		}else if(padron.getStrTipoPla().equals("4")){
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboral(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO);
			loadListDetCondicionLaboralByIntIdCondLaboral(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO);
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboralDet(Constante.PARAM_T_DETALLECONDICIONLABORAL_CONTRATADOADMINISTRATIVO);
		}else if(padron.getStrTipoPla().equals("5")){
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboral(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO);
			loadListDetCondicionLaboralByIntIdCondLaboral(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO);
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboralDet(Constante.PARAM_T_DETALLECONDICIONLABORAL_CESANTE);
		}else if(padron.getStrTipoPla().equals("6")){
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboral(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO);
			loadListDetCondicionLaboralByIntIdCondLaboral(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO);
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboralDet(Constante.PARAM_T_DETALLECONDICIONLABORAL_SOBREVIVIENTE);
		}else if(padron.getStrTipoPla().equals("9")){
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboral(Constante.PARAM_T_CONDICIONLABORAL_SERUM);
			loadListDetCondicionLaboralByIntIdCondLaboral(Constante.PARAM_T_CONDICIONLABORAL_SERUM);
			this.beanSocioComp.getPersona().getNatural().getPerLaboral().setIntCondicionLaboralDet(Constante.PARAM_T_DETALLECONDICIONLABORAL_SERUM);
		}else{
			blnCondLaboral = false;
		}
		
		//Req 6.
		if(padron.getBdPorJud().compareTo(BigDecimal.ZERO)>0){
			//FacesContextUtil.setMessageError("Socio tiene descuento judicial igual al " + padron.getBdPorJud() + "%");
			msgTxtDsctoJudicial = "Socio tiene descuento judicial igual al " + padron.getBdPorJud() + "%";
		}else{
			msgTxtDsctoJudicial = "";
		}
		//Req 7.
		if(padron.getIntLicenp()>0){
			//FacesContextUtil.setMessageError("El/La Socio(a) tiene una Licencia de permiso igual a " + padron.getIntLicenp() + " día(s)");
			msgTxtLicencPermiso = "El/La Socio(a) tiene una Licencia de permiso igual a " + padron.getIntLicenp() + " día(s)";
		}else{
			msgTxtLicencPermiso = "";
		}
		//Req 8.
		if(padron.getIntLicSub()>0){
			//FacesContextUtil.setMessageError("El/La Socio(a) tiene una Licencia de subsidio igual a " + padron.getIntLicSub() + " día(s)");
			msgTxtLicencSubvencPermiso = "El/La Socio(a) tiene una Licencia de subsidio igual a " + padron.getIntLicSub() + " día(s)";
		}else {
			msgTxtLicencSubvencPermiso = "";
		}
		
		//Req 10.
		Date today = new Date();
		String strToday = Constante.sdfDiaMes.format(today);
		Date fecNacSocioPadron = padron.getDtFecNac();
		String strFecNacSocioPadron = Constante.sdfDiaMes.format(fecNacSocioPadron);
		
		if(strToday.equals(strFecNacSocioPadron)){
			blnMsgFecCumple = true;
		}else{
			blnMsgFecCumple = false;
		}
		
		//Fin Requerimiento
		
		setBlnShowFiltroPersonaNatu(false);
		setBlnShowDivFormSocioNatu(true);
		setBlnIsDatoPadron(true);
	}
	
	public void completeNombreReniec(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.completeNombreReniec-------------------------------------");
		beanSocioComp.getPersona().getNatural().setStrApellidoPaterno(beanSocioComp.getSocio().getStrApePatSoc());
		beanSocioComp.getPersona().getNatural().setStrApellidoMaterno(beanSocioComp.getSocio().getStrApeMatSoc());
		beanSocioComp.getPersona().getNatural().setStrNombres(beanSocioComp.getSocio().getStrNombreSoc());
	}
	
	//Recarga de combos
	public void getListSubsucursalDeSucursal(ValueChangeEvent event) {
		log.info("-------------------------------------Debugging SocioController.getListSubsucursalDeSucursal-------------------------------------");
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = (Integer)event.getNewValue();
			if(intIdSucursal!=0){
				facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursal);
				log.info("listaSubsucursal.size: "+listaSubsucursal.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("event.getComponent.getId(): "+event.getComponent().getId());
		setListJuridicaSubsucursal(listaSubsucursal);
	}
	
	public void loadListDetCondicionLaboral(ValueChangeEvent event) {
		log.info("-------------------------------------Debugging SocioController.loadListDetCondicionLaboral-------------------------------------");
		TablaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Tabla> listaDetCondiLaboral = null;
		try {
			intIdSucursal = (Integer)event.getNewValue();
			if(intIdSucursal!=null && intIdSucursal!=0){
				facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaDetCondiLaboral = facade.getListaTablaPorAgrupamientoB(Integer.parseInt(Constante.PARAM_T_DETALLECONDICIONLABORAL), Integer.parseInt(event.getNewValue().toString()));
				log.info("listaDetCondiLaboral.size: "+listaDetCondiLaboral.size());
			}
			
			if(event.getNewValue()!=null){
				if(Integer.valueOf(event.getNewValue().toString()).equals(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO)){
					System.out.println("Mostrando campos del contrato...");
					setIsContratado(true);
				}else{
					System.out.println("Ocultando campos del contrato...");
					beanSocioComp.getPersona().getNatural().getPerLaboral().setIntTipoContrato(null);
					beanSocioComp.getPersona().getNatural().getPerLaboral().setDtInicioContrato(null);
					beanSocioComp.getPersona().getNatural().getPerLaboral().setDtFinContrato(null);
					beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
					beanSocioComp.getPersona().getNatural().getPerLaboral().getContrato().setStrNombrearchivo(null);
					setIsContratado(false);
				}
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListDetCondicionLaboral(listaDetCondiLaboral);
	}
	
	public void loadListDetCondicionLaboralByIntIdCondLaboral(Integer intIdCondLaboral) {
		log.info("-------------------------------------Debugging SocioController.loadListDetCondicionLaboral-------------------------------------");
		TablaFacadeRemote facade = null;
		List<Tabla> listaDetCondiLaboral = null;
		try {
			if(intIdCondLaboral!=null && intIdCondLaboral!=0){
				facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaDetCondiLaboral = facade.getListaTablaPorAgrupamientoB(Integer.parseInt(Constante.PARAM_T_DETALLECONDICIONLABORAL), Integer.parseInt(intIdCondLaboral.toString()));
				log.info("listaDetCondiLaboral.size: "+listaDetCondiLaboral.size());
			}
			
			if(Integer.valueOf(intIdCondLaboral.toString()).equals(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO)){
				System.out.println("Mostrando campos del contrato...");
				setIsContratado(true);
			}else{
				System.out.println("Ocultando campos del contrato...");
				beanSocioComp.getPersona().getNatural().getPerLaboral().setIntTipoContrato(null);
				beanSocioComp.getPersona().getNatural().getPerLaboral().setDtInicioContrato(null);
				beanSocioComp.getPersona().getNatural().getPerLaboral().setDtFinContrato(null);
				beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
				beanSocioComp.getPersona().getNatural().getPerLaboral().getContrato().setStrNombrearchivo(null);
				setIsContratado(false);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListDetCondicionLaboral(listaDetCondiLaboral);
	}
	
	public void loadListDetCondicionLaboral(Integer intCondicionLaboral) {
		log.info("-------------------------------------Debugging SocioController.loadListDetCondicionLaboral-------------------------------------");
		TablaFacadeRemote facade = null;
		List<Tabla> listaDetCondiLaboral = null;
		try {
			if(intCondicionLaboral!=null && intCondicionLaboral!=0){
				facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaDetCondiLaboral = facade.getListaTablaPorAgrupamientoB(Integer.parseInt(Constante.PARAM_T_DETALLECONDICIONLABORAL), intCondicionLaboral);
				log.info("listaDetCondiLaboral.size: "+listaDetCondiLaboral.size());
			}
			
			if(intCondicionLaboral.equals(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO)){
				System.out.println("Mostrando campos del contrato...");
				setIsContratado(true);
			}else{
				System.out.println("Ocultando campos del contrato...");
				beanSocioComp.getPersona().getNatural().getPerLaboral().setIntTipoContrato(null);
				beanSocioComp.getPersona().getNatural().getPerLaboral().setDtInicioContrato(null);
				beanSocioComp.getPersona().getNatural().getPerLaboral().setDtFinContrato(null);
				beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
				beanSocioComp.getPersona().getNatural().getPerLaboral().getContrato().setStrNombrearchivo(null);
				setIsContratado(false);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListDetCondicionLaboral(listaDetCondiLaboral);
	}
	
	public void loadListDocumentoBusq(ActionEvent event) {
		log.info("-------------------------------------Debugging SocioController.loadListDocumento-------------------------------------");
		log.info("pIntTipoPersona: "+getRequestParameter("pIntTipoPersona"));
		TablaFacadeRemote facade = null;
		String strIdTipoPersona = null;
		List<Tabla> listaDocumento = null;
		try {
			strIdTipoPersona = getRequestParameter("pIntTipoPersona");
			if(!strIdTipoPersona.equals("0")){
				facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				if(strIdTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA.toString())){
					listaDocumento = facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO), Constante.VISTA_TIPOPERSONA_JURIDICA);
				}else{
					listaDocumento = facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO));
				}
				log.info("listaDocumento.size: "+listaDocumento.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("event.getComponent.getId(): "+event.getComponent().getId());
		setListDocumentoBusq(listaDocumento);
	}
	
	//Agregado 09/04/2013
	public void loadCboDni(ActionEvent event) {
		log.info("-------------------------------------Debugging SocioController.loadListDocumento-------------------------------------");
		log.info("pIntTipoPersona: "+getRequestParameter("pIntTipoPersona"));
		TablaFacadeRemote facade = null;
		String strIdTipoPersona = null;
		List<Tabla> listaDocumento = null;
		try {
			strIdTipoPersona = ""+Constante.PARAM_T_TIPOPERSONA_NATURAL;
			if(!strIdTipoPersona.equals("0")){
				facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaDocumento = facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO));
				log.info("listaDocumento.size: "+listaDocumento.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("event.getComponent.getId(): "+event.getComponent().getId());
		setListDocumentoBusq(listaDocumento);
	}
	//Fin
	
	public void verContratoPerNatu(ActionEvent event) throws IOException{
		log.info("-------------------------------------Debugging SocioController.verContratoPerNatu-------------------------------------");
		//Mostrar la Foto del Socio
		if(getBeanSocioComp().getPersona().getNatural().getPerLaboral()!=null &&
				getBeanSocioComp().getPersona().getNatural().getPerLaboral().getContrato()!=null){
			Archivo contrato = getBeanSocioComp().getPersona().getNatural().getPerLaboral().getContrato();
			log.info("Path Contrato: "+contrato.getTipoarchivo().getStrRuta()+"\\"+contrato.getStrNombrearchivo());
			File file = new File(contrato.getTipoarchivo().getStrRuta()+"\\"+contrato.getStrNombrearchivo());
			log.info("file.exists(): "+file.exists());
			
	        byte[] bytes = getBytesFromFile(file);
	        
	        FacesContext faces = FacesContext.getCurrentInstance();
	        HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
	        getResponse().setContentType("application/pdf");
	        getResponse().setContentLength(bytes.length);
	        getResponse().setHeader( "Content-disposition", "inline; filename=\""+file.getName()+"\"");
	        System.out.println("file.getName(): "+file.getName());
	        try {
		        ServletOutputStream out;
		        out = response.getOutputStream();
		        out.write(bytes);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        faces.responseComplete();
		}
	}
	
	public void adjuntarContrato(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.adjuntarContrato-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		fileupload.setStrDescripcion("Seleccione el contrato correspondiente al socio.");
		fileupload.setFileType(FileUtil.allDocumentTypes);
		
		//Si ya ha grabado el contrato(Archivo) anteriormente
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		if(getBeanSocioComp().getPersona().getNatural().getPerLaboral().getContrato()!=null &&
				getBeanSocioComp().getPersona().getNatural().getPerLaboral().getContrato().getId()!=null){
			intItemArchivo = getBeanSocioComp().getPersona().getNatural().getPerLaboral().getContrato().getId().getIntItemArchivo();
			intItemHistorico = getBeanSocioComp().getPersona().getNatural().getPerLaboral().getContrato().getId().getIntItemHistorico();
		}
		fileupload.setParamArchivo(intItemArchivo, intItemHistorico, Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONTRATOLABPERSONA);
	}
	
	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException{
		log.info("-------------------------------------Debugging SocioController.putFile-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		
		if(fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONTRATOLABPERSONA)){
			//setear contrato a PerLaboral 
			log.info("fileupload.getObjArchivo().getStrNombrearchivo(): "+ fileupload.getObjArchivo().getStrNombrearchivo());
			
			fileContrato = fileupload.getObjArchivo();
			log.info("fileContrato.getStrNombrearchivo(): " + fileContrato.getStrNombrearchivo());
			getBeanSocioComp().getPersona().getNatural().getPerLaboral().setContrato(fileupload.getObjArchivo());
		}else if(fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FIRMAPERSONAS)){
			//setear Firma 
			getBeanSocioComp().getPersona().getNatural().setFirma(fileupload.getObjArchivo());
			
			log.info("byteImg.length: "+fileupload.getDataImage().length);
			byte[] byteImg = fileupload.getDataImage();
			MyFile file = new MyFile();
	        file.setLength(byteImg.length);
	        file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
	        file.setData(byteImg);
	        setFileFirmaSocio(file);
		}else if(fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FOTOPERSONAS)){
			//setear Foto
			getBeanSocioComp().getPersona().getNatural().setFoto(fileupload.getObjArchivo());
			
			log.info("byteImg.length: "+fileupload.getDataImage().length);
			byte[] byteImg = fileupload.getDataImage();
			MyFile file = new MyFile();
	        file.setLength(byteImg.length);
	        file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
	        file.setData(byteImg);
	        setFileFotoSocio(file);
		}
	}
	
	public void paintImage(OutputStream stream, Object object) throws IOException {
        stream.write(((MyFile)object).getData());
    }
	
	public void adjuntarFirma(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.adjuntarFirma-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		fileupload.setStrDescripcion("Seleccione el archivo que contiene la firma del socio.");
		fileupload.setFileType(FileUtil.imageTypes);
		
		//Si ya ha grabado el contrato(Archivo) anteriormente
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		if(getBeanSocioComp().getPersona().getNatural().getFirma()!=null &&
				getBeanSocioComp().getPersona().getNatural().getFirma().getId()!=null){
			intItemArchivo = getBeanSocioComp().getPersona().getNatural().getFirma().getId().getIntItemArchivo();
			intItemHistorico = getBeanSocioComp().getPersona().getNatural().getFirma().getId().getIntItemHistorico();
		}
		fileupload.setParamArchivo(intItemArchivo, intItemHistorico, Constante.PARAM_T_TIPOARCHIVOADJUNTO_FIRMAPERSONAS);
		fileupload.setStrJsFunction("putFile()");
	}
	
	public void adjuntarFoto(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.adjuntarFoto-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		fileupload.setStrDescripcion("Seleccione el archivo que contiene la foto del socio.");
		fileupload.setFileType(FileUtil.imageTypes);
		
		//Si ya ha grabado el contrato(Archivo) anteriormente
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		if(getBeanSocioComp().getPersona().getNatural().getFoto()!=null &&
				getBeanSocioComp().getPersona().getNatural().getFoto().getId()!=null){
			intItemArchivo = getBeanSocioComp().getPersona().getNatural().getFoto().getId().getIntItemArchivo();
			intItemHistorico = getBeanSocioComp().getPersona().getNatural().getFoto().getId().getIntItemHistorico();
		}
		fileupload.setParamArchivo(intItemArchivo, intItemHistorico, Constante.PARAM_T_TIPOARCHIVOADJUNTO_FOTOPERSONAS);
		fileupload.setStrJsFunction("putFile()");
	}
	
	public void removeContrato(){
		try{
			/*if (getBeanSolCtaCte().getId().getIntCcobItemsolctacte() != null && archivoDocSolicitudTemp == null){
			    archivoDocSolicitudTemp = archivoDocSolicitud;
			}*/
			fileContrato.setStrNombrearchivo("");
			fileContrato = null;
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			fileUploadController.setObjArchivo(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void resetDocIdentidadMaxLength(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.resetDocIdentidadMaxLength-------------------------------------");
		log.info("pIntTipoDocIdentidad: "+getRequestParameter("pIntTipoDocIdentidad"));
		String strTipoDoc = getRequestParameter("pIntTipoDocIdentidad");
		
		if(strTipoDoc.equals(Constante.PARAM_T_TIPODOCUMENTO_PASAPORTE)){
			setIntDocIdentidadMaxLength(11);
		}else if(strTipoDoc.equals(Constante.PARAM_T_TIPODOCUMENTO_CARNEEXTRANJERIA)){
			setIntDocIdentidadMaxLength(8);
		}else if(strTipoDoc.equals(Constante.PARAM_T_TIPODOCUMENTO_RUC)){
			setIntDocIdentidadMaxLength(11);
		}
		getBeanSocioComp().setStrDocIdentidad(null);
	}
	
	public void resetFechasContrato(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.resetFechasContrato-------------------------------------");
		log.info("pIntTipoContrato: "+getRequestParameter("pIntTipoContrato"));
		String strTipoContrato = getRequestParameter("pIntTipoContrato");
		
		try{
			if(Integer.valueOf(strTipoContrato).equals(Constante.PARAM_T_TIPOCONTRATO_PLAZOFIJO)){
				setBlnFechaContratoDisabled(false);
			}else if(Integer.valueOf(strTipoContrato).equals(Constante.PARAM_T_TIPOCONTRATO_INDETERMINADO) ||
					Integer.valueOf(strTipoContrato).equals(0)){
				setBlnFechaContratoDisabled(true);
			}
			getBeanSocioComp().getPersona().getNatural().getPerLaboral().setDtInicioContrato(null);
			getBeanSocioComp().getPersona().getNatural().getPerLaboral().setDtFinContrato(null);
		}catch (Exception e){
			log.error(e.getMessage());
		}
	}
	
	//Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile(File file) throws IOException {        
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }
    
    public void showRoles(ActionEvent event){
		log.info("--------------------Debugging SocioController.showRoles-------------------");
		listarRoles(listRoles);
		log.info("listRoles.size: "+listRoles.size());
	}
    
    public void listarRoles(List<PersonaRol> listaRol){
		log.info("-----------------------Debugging SocioController.listarRoles-----------------------------");
		if(listRoles!=null)log.info("listRoles.size: "+listRoles.size());
		
	    List<Tabla> listTabla = getListaRolesParam();
		
		//Limpiar y volver a llenar arrayTipoComprobante desde Parametros
		setListRoles(new ArrayList<PersonaRol>());
		PersonaRol rol = null;
		//Tabla tabla = null;
		for(int i=0; i<listTabla.size(); i++){
			rol = new PersonaRol();
			rol.setTabla(listTabla.get(i));
			rol.setId(new PersonaRolPK());
			rol.getId().setIntIdEmpresa(SESION_IDEMPRESA);
			rol.getId().setIntParaRolPk(listTabla.get(i).getIntIdDetalle());
			listRoles.add(rol);
		}
		
		PersonaRol pRol = null;
		//marcar con check aquellas que estan en arrayRoles
		if(arrayRoles!=null && arrayRoles.size()>0){
			for(int i=0; i<arrayRoles.size(); i++){
				pRol = arrayRoles.get(i);
				
				for(int j=0; j<listRoles.size(); j++){
					rol = listRoles.get(j);
					if(pRol.getTabla().getIntIdDetalle().equals(rol.getTabla().getIntIdDetalle())){
						rol.setIsChecked(true);
					}
				}
			}
		}
		
		listaRol = listRoles;
	}
    
    public List<Tabla> getListaRolesParam(){
		log.info("-----------------------Debugging SocioController.getListaRolesParam-----------------------------");
		
	    List<Tabla> listTabla = null;
		try {
			TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listTabla = facade.getListaTablaPorAgrupamientoA(61, "D");
    		log.info("listTabla.size: "+listTabla.size());
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listTabla;
	}
    
    public void seleccionarRoles(ActionEvent event){
		log.info("--------------------Debugging SocioController.seleccionarRoles-------------------");
		selectCheckedRol(listRoles);
	}
    
    public void selectCheckedRol(List<PersonaRol> listaRol){
		if(listRoles==null){
			return;
		}
		
		if(arrayRoles==null){
			arrayRoles = new ArrayList<PersonaRol>();
		}
		beanSocioComp.setStrRoles("");
		
		PersonaRol rol = null;
		log.info("listRoles.size: "+listRoles.size());
		for(int i=0; i<listRoles.size(); i++){
			rol = listRoles.get(i);
			log.info("rol.isChecked: "+rol.getIsChecked());
			if(rol.getIsChecked()!=null && rol.getIsChecked()){
				if(!existeRol(rol)){
					arrayRoles.add(rol);
				}
				beanSocioComp.setStrRoles(beanSocioComp.getStrRoles() + ", " + rol.getTabla().getStrDescripcion());
			}else if(rol.getIsChecked()!=null && !rol.getIsChecked() && existeRol(rol)){
				anularRol(rol);
			}
		}
		if(beanSocioComp.getStrRoles().length()>2)beanSocioComp.setStrRoles(beanSocioComp.getStrRoles().substring(2));
	}
    
    public Boolean existeRol(PersonaRol rol){
    	for(PersonaRol pRol : arrayRoles){
    		if(rol.getId().getIntParaRolPk().equals(pRol.getId().getIntParaRolPk())){
    			return true;
    		}
    	}
    	return false;
    }
    
    public void anularRol(PersonaRol rol){
    	for(int i=0; i<arrayRoles.size(); i++){
    		PersonaRol pRol = arrayRoles.get(i);
    		if(rol.getId().getIntParaRolPk().equals(pRol.getId().getIntParaRolPk())){
    			if(pRol.getIntEstadoCod()!=null && pRol.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
    				pRol.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
    			}else if(pRol.getIntEstadoCod()==null){
    				arrayRoles.remove(i);
    			}
    		}
    	}
    }
    
    public void descargarCroquis(){
		log.info("-----------------DomicilioController.descargarArchivo---------------");
		String rutaActual = getRequestParameter("strRutaActual");
		log.info("rutaActual: "+ rutaActual);
		try {
			DownloadFile.downloadFile("C:\\tumi\\ArchivosAdjuntos\\Imagenes\\Personas\\Croquis\\"+rutaActual);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

    
    
    /**
     * Carga datos del socio comp al seleccionar registro de la grilla de busqueda.
     * Metodo que reeemplaza a :
     * setSocioNatuSelected(socioComp); y
     * aperturaCuentaController.setSocioNatuSelected(socioComp);
     * @param event
     */
    public void selectSocioBusqueda(ActionEvent event){
    	SocioComp socioTemp = null;
    	SocioComp socioComp = null;
    	SocioFacadeLocal socioFacade = null;
    	List<CuentaConceptoDetalle> lstCuentaConceptoDetalle = null;
    	CuentaConceptoDetalle ctaCptoDet = new CuentaConceptoDetalle();
    	ctaCptoDet.setId(new CuentaConceptoDetalleId());
    	mostrarBoton = false;
    	try {
    		socioFacade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
    		socioTemp = (SocioComp)event.getComponent().getAttributes().get("itemSocioBusq");
    		ConceptoFacadeRemote conceptoFacade =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
    		socioComp = socioFacade.completarSocioCompBusqueda(socioTemp.getSocio());
    		if(socioComp != null){
    			setSocioNatuSelected(socioComp);
    			if(aperturaCuentaController == null){
    				aperturaCuentaController = (AperturaCuentaController)getSessionBean("aperturaCuentaController");
    				aperturaCuentaController.setSocioNatuSelected(socioComp);
    			}	else{
    				aperturaCuentaController.setSocioNatuSelected(socioComp);
    			}
    		}
    		/* Inicio - Obtener Persona Seleccionada - GTorresBrousset 24.abr.2014 */
    		setPersonaOld(socioComp.getPersona());
    		/* Fin - Obtener Persona Seleccionada - GTorresBrousset 24.abr.2014 */
    		//OCULTA LOS BOTONES DE FONRED
    		obtenerSocioEstructura(null);
			if (socioNatuSelected.getCuenta()!=null) {
	            ctaCptoDet.getId().setIntPersEmpresaPk(SESION_IDEMPRESA);
	            ctaCptoDet.getId().setIntCuentaPk(socioNatuSelected.getCuenta().getId().getIntCuenta());
	            ctaCptoDet.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTO);
	            lstCuentaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ctaCptoDet);
	            if (lstCuentaConceptoDetalle!=null && !lstCuentaConceptoDetalle.isEmpty()) {
					for (CuentaConceptoDetalle lista : lstCuentaConceptoDetalle) {
						if((lista.getIntParaTipoConceptoCod()==3 && lista.getIntItemConcepto()==15) ||
								(lista.getIntParaTipoConceptoCod()==3 && lista.getIntItemConcepto()==17) || 
								(lista.getIntParaTipoConceptoCod()==3 && lista.getIntItemConcepto()==18)){
							mostrarBoton = Boolean.TRUE;
							break;
						}else{
							mostrarBoton = Boolean.FALSE;
						}
					}
	            }//
			}
    		//log.error("Error en selectSocioBusqueda --> "+ socioNatuSelected);
		} catch (Exception e) {
			log.error("Error en selectSocioBusqueda --> "+e);
		}
    }
    /**
     * AUTOR--> RVILLARREAL
     * ESTE METODO IMPRIME EL FORMULARIO REGISTRO DE SOCIO / CLIENTE
     */
    @SuppressWarnings("deprecation")
	public void imprimirSolicitudIngreso(){
    	String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla tablaEstadoCivil = new Tabla();
    	Tabla tablaCargoLaboral = new Tabla();
    	Tabla x = new Tabla();
    	List<Vinculo> lstPersVinculo = new ArrayList<Vinculo>();
    	
    	PersonaEmpresaPK pK = new PersonaEmpresaPK();
    	Natural naturalVinculado = new Natural();
    	//@SuppressWarnings("unused")
		BigDecimal bdMontoSaldo = BigDecimal.ZERO;

    	List<CuentaConceptoDetalle> lstConceptoDetalle = null;
    	CuentaConceptoDetalle ctaCptoDet = new CuentaConceptoDetalle();
    	ctaCptoDet.setId(new CuentaConceptoDetalleId());
    	
    	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);
    	    	
		try {
			SocioFacadeLocal socioFacade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			ConceptoFacadeRemote conceptoFacade =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			EstructuraFacadeLocal facade1 = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			beanSocioComp = socioFacade.getSocioNatural(socioNatuSelected.getSocio().getId());
			//Artificio para que se visualice el reporte
			x.setStrAbreviatura("");
			lstReporteVacia.add(x);
			
			//Aqui me trae el nombre del conyugue
			pK.setIntIdEmpresa(SESION_IDEMPRESA);
			pK.setIntIdPersona(beanSocioComp.getPersona().getIntIdPersona());
			lstPersVinculo = personaFacade.getVinculoPorPk(pK);
			if (lstPersVinculo!=null && !lstPersVinculo.isEmpty()) {
				for (Vinculo vinculo : lstPersVinculo) {
					if (vinculo.getIntTipoVinculoCod().equals(Constante.PARAM_T_TIPOVINCULO_CONYUGE)) {//TIPO VINCULO (1)
						naturalVinculado = personaFacade.getNaturalPorPK(vinculo.getIntPersonaVinculo());
						break;
					}
				}				
			}

			if(naturalVinculado.getStrNombres()!=null && naturalVinculado.getStrApellidoPaterno()!=null && naturalVinculado.getStrApellidoMaterno()!=null){
				parametro.put("P_CONYUGUE", naturalVinculado.getStrNombres().toUpperCase() + " " + naturalVinculado.getStrApellidoPaterno().toUpperCase() + " " + naturalVinculado.getStrApellidoMaterno().toUpperCase());
			}else{
				parametro.put("P_CONYUGUE", "");
			}

			for(SocioEstructura lstSocioEstructura : beanSocioComp.getSocio().getListSocioEstructura()){
				if (lstSocioEstructura != null && lstSocioEstructura.getIntEstadoCod()==1 && lstSocioEstructura.getIntTipoEstructura()==1){ 
					parametro.put("P_CODPLANILLA", lstSocioEstructura.getStrCodigoPlanilla());
					break;
				}else{
					parametro.put("P_CODPLANILLA", "");
				}
			}
			String fechaNacimiento = Constante.sdf.format(socioNatuSelected.getPersona().getNatural().getDtFechaNacimiento());
			Date tiempoServicio = beanSocioComp.getPersona().getNatural().getPerLaboral().getDtInicioServicio();
			
    		tablaEstadoCivil = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOCIVIL), 
    				beanSocioComp.getPersona().getNatural().getIntEstadoCivilCod());
    		
    		tablaCargoLaboral =tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_CARGOPERSONAL),
    				beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCargo());

    		parametro.put("P_IDCODIGOPERSONA", socioNatuSelected.getPersona().getIntIdPersona()!=null?socioNatuSelected.getPersona().getIntIdPersona():" ");
			parametro.put("P_APELLIDOPATERNO", socioNatuSelected.getPersona().getNatural().getStrApellidoPaterno().toUpperCase()!=null?socioNatuSelected.getPersona().getNatural().getStrApellidoPaterno().toUpperCase():" ");
			parametro.put("P_APELLIDOMATERNO", socioNatuSelected.getPersona().getNatural().getStrApellidoMaterno().toUpperCase()!=null?socioNatuSelected.getPersona().getNatural().getStrApellidoMaterno().toUpperCase():" ");
			parametro.put("P_NOMBRECOMPLETO", socioNatuSelected.getPersona().getNatural().getStrNombres().toUpperCase()!=null?socioNatuSelected.getPersona().getNatural().getStrNombres().toUpperCase():" ");
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			parametro.put("P_ECIVIL", tablaEstadoCivil.getStrDescripcion()!=null?tablaEstadoCivil.getStrDescripcion():"");
			if(tablaCargoLaboral!=null)
				parametro.put("P_CARGOlABORAL", tablaCargoLaboral.getStrDescripcion());
			else
				parametro.put("P_CARGOlABORAL", "");
			if(socioNatuSelected.getPersona().getIntTipoPersonaCod()==1){
				for(Documento lstDocumento : beanSocioComp.getPersona().getListaDocumento()){
					if(lstDocumento.getIntTipoIdentidadCod()==4 && lstDocumento.getIntEstadoCod()==1){
						parametro.put("P_RUC", lstDocumento.getStrNumeroIdentidad());
						break;
						}else{
							parametro.put("P_RUC", " ");
							}
						}
			}
            parametro.put("P_FECHA_NACIMIENTO",fechaNacimiento!=null?fechaNacimiento:" ");
            parametro.put("P_TIEMPOSERVICO", beanSocioComp.getPersona().getNatural().getPerLaboral().getDtInicioServicio()!=null?beanSocioComp.getPersona().getNatural().getPerLaboral().getDtInicioServicio():" ");
            parametro.put("P_REMUNERACION", beanSocioComp.getPersona().getNatural().getPerLaboral().getBdRemuneracion()!=null?formato.format(beanSocioComp.getPersona().getNatural().getPerLaboral().getBdRemuneracion()):" ");
            parametro.put("P_TIEMPOSERVICIO", obtenerPeriodo(tiempoServicio, new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH))!=null?obtenerPeriodo(tiempoServicio, new Date(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH)): " ");
            
            
            List<Comunicacion> lstComunicacion = beanSocioComp.getPersona().getListaComunicacion();
           
           String numero="";
           if(lstComunicacion!=null && !lstComunicacion.isEmpty()){
        	   int prioridad = 99;
        	   for(Comunicacion com : lstComunicacion){
           		   if(com.getIntSubTipoComunicacionCod()==4 && com.getIntTipoComunicacionCod()==1 && prioridad>5){
        			   prioridad = 5;
        			   numero = "Vecino"+ " -> " + com.getIntNumero();
        	       }else if(com.getIntSubTipoComunicacionCod()==3 && com.getIntTipoComunicacionCod()==1 && prioridad>4){
        	    	   prioridad = 4;
        			   numero = "Familiar"+ " -> " + com.getIntNumero();
        		   }else if(com.getIntSubTipoComunicacionCod()==2 && com.getIntTipoComunicacionCod()==1 && prioridad>3){
        	    	   prioridad = 3;
        			   numero = "Trabajo"+ " -> " + com.getIntNumero();
        		   }else if(com.getIntSubTipoComunicacionCod()==1 && com.getIntTipoComunicacionCod()==1 && prioridad>2){
        	    	   prioridad = 2;
        			   numero = "Casa"+ " -> " + com.getIntNumero();
        		   }else if(com.getIntSubTipoComunicacionCod()==5 && com.getIntTipoComunicacionCod()==1 && prioridad>1){
        	    	   prioridad = 1;
        			   numero = "Personal"+ " -> " + com.getIntNumero();
        		   }
        	   	}
           	}
           parametro.put("P_COMUCASA", numero);
           parametro.put("P_COMUMOVIL", "");
//           System.out.println("Descripcion " + tipocomunicacion.getStrDescripcion());
//            if (lstComunicacion!=null && !lstComunicacion.isEmpty()) {
//            	for (Comunicacion com : lstComunicacion) {
//            		if (com.getIntTipoComunicacionCod()!=null && com.getIntTipoLineaCod()!=null) {
//            			System.out.println("TipoComunicacion :" +com.getIntSubTipoComunicacionCod());
//            			if( com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO) &&
//            					com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION) &&
//                    			com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_FIJO) &&
//                    			com.getIntSubTipoComunicacionCod().equals(Constante.PARAM_T_COMUNICACIONCASA)){
//                    		telefFijo = String.valueOf(com.getIntNumero());
//                    	} else if (com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO) &&
//                    			com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION) &&
//                    			(com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_CLARO) ||
//                    					com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_MOVISTAR) ||
//                    							com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_NEXTEL)) && 
//                    							com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_COMUNICACIONTRABAJO)) {
//                    		telefMovil = String.valueOf(com.getIntNumero())!=null?String.valueOf(com.getIntNumero()): " ";
//                    		
//                    	}
//					}
//                	
//                }
//			}
//            if(telefFijo!=null && !telefFijo.isEmpty())
//            	parametro.put("P_COMUCASA", "CASA ->"+ telefFijo);
//        	else
//            	parametro.put("P_COMUCASA", "");
//           
//            if(telefMovil!=null && !telefMovil.isEmpty())
//            	parametro.put("P_COMUMOVIL", "TRABAJO ->" +telefMovil);
//            else
//            	parametro.put("P_COMUMOVIL", "");
            parametro.put("P_NUMCUENTA", socioNatuSelected.getCuenta().getStrNumeroCuenta());
            parametro.put("P_CANTIDADHIJO", beanSocioComp.getIntCantidadHijos()!=null?beanSocioComp.getIntCantidadHijos():" ");
            parametro.put("P_FCHAINGRESO", beanSocioComp.getDtFechaIngreso()!=null?Constante.sdf.format(beanSocioComp.getDtFechaIngreso()):" ");
            
            List<Domicilio> lstDomicilio = beanSocioComp.getPersona().getListaDomicilio();
            Domicilio dom = new Domicilio();
            
            if (lstDomicilio != null && lstDomicilio.size() >= 1) {
            	dom = lstDomicilio.get(0);
            }
            
            String strVia=null;
            Tabla tablaVia = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), dom.getIntTipoViaCod());
            System.out.println("Nombre via " + strVia);
            if(tablaVia!=null){
            	strVia = tablaVia.getStrDescripcion();
            }
            parametro.put("P_REFDOMICILIARIA", dom.getStrReferencia());
			parametro.put("P_JIRONVIA",  strVia + " " + dom.getStrNombreVia() + " " +dom.getIntNumeroVia());
			if(dom.getIntParaUbigeoPkDistrito()!=null)
				parametro.put("P_DISTRITO", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkDistrito())).get(0).getStrDescripcion());
			else
				parametro.put("P_DISTRITO", "");
			if(dom.getIntParaUbigeoPkProvincia()!=null)
				parametro.put("P_PROVINCIA", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkProvincia())).get(0).getStrDescripcion());
			else
				parametro.put("P_PROVINCIA", "");
			if(dom.getIntParaUbigeoPkDpto()!=null)
				parametro.put("P_DEPARTAMENTO", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkDpto())).get(0).getStrDescripcion());
			else
				parametro.put("P_DEPARTAMENTO", "");
            //Aporte Mensual
			if (socioNatuSelected.getCuenta()!=null) {
				log.info("numero de cuenta: "+socioNatuSelected.getCuenta().getId().getIntCuenta());
	            ctaCptoDet.getId().setIntPersEmpresaPk(SESION_IDEMPRESA);
	            ctaCptoDet.getId().setIntCuentaPk(socioNatuSelected.getCuenta().getId().getIntCuenta());
	            ctaCptoDet.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCUENTA_APORTES); 
	            lstConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ctaCptoDet);
	            if (lstConceptoDetalle!=null && !lstConceptoDetalle.isEmpty()) {
					for (CuentaConceptoDetalle lista : lstConceptoDetalle) {
						bdMontoSaldo = lista.getBdMontoConcepto()!=null?lista.getBdMontoConcepto():BigDecimal.ZERO;
						parametro.put("P_APORMENSUAL", formato.format(bdMontoSaldo));
						break;
					}
				}else{
					parametro.put("P_APORMENSUAL", "");
				}
			}else {
				parametro.put("P_APORMENSUAL", "");
			}
			if(beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntEstadoCod()==1){
			listEstructura = facade1.getListaEstructuraPorNivelCodigo(beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntNivel(),
																	  beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntCodigo());
			parametro.put("P_DEPENDENCIA", listEstructura.get(0).getJuridica().getStrRazonSocial());
			}else{
				parametro.put("P_DEPENDENCIA", "");
			}
			System.out.println("Parametro " +parametro);
			strNombreReporte = "solicitudIngreso";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstReporteVacia), Constante.PARAM_T_TIPOREPORTE_PDF);
			
		} catch (Exception e) {
			log.error("Error en imprimirPlanillaDescuento ---> "+e);
		}   	
    }
    
    private String obtenerPeriodo(Date oldDate, Date newDate) {
    	Interval interval = new Interval(oldDate.getTime(), Calendar.getInstance().getTimeInMillis());
        Period period = interval.toPeriod().normalizedStandard(PeriodType.yearMonthDay());
        
        String days = period.getDays() +" Dia(s) ";
        String month = period.getMonths() +" Mes(es) ";
        String year = period.getYears() + " Año(s)";
        
        return year+" "+month+" "+days;
    }
    /**
     * IMPRIME SOLICITUD DE FONRET
     * @param intTipoCambio
     * @param socio
     * @return
     * @throws BusinessException
     */
    public void imprimirSolicitudDeAfiliacion(){
    	String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla x = new Tabla();
    	lstReporteVacia.clear();
    	List<CuentaConceptoDetalle> lstCuentaConceptoDetalle = null;
    	CuentaConceptoDetalle ctaCptoDet = new CuentaConceptoDetalle();
    	ctaCptoDet.setId(new CuentaConceptoDetalleId());
		DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
		try {
			GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			EstructuraFacadeLocal facade1 = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			x.setStrAbreviatura("");
			lstReporteVacia.add(x);
			obtenerSocioEstructura(null);
			
			if (socioNatuSelected.getCuenta()!=null) {
	            ctaCptoDet.getId().setIntPersEmpresaPk(SESION_IDEMPRESA);
	            ctaCptoDet.getId().setIntCuentaPk(socioNatuSelected.getCuenta().getId().getIntCuenta());
	            ctaCptoDet.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTO);
	            lstCuentaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ctaCptoDet);
	            if (lstCuentaConceptoDetalle!=null && !lstCuentaConceptoDetalle.isEmpty()) {
					for (CuentaConceptoDetalle lista : lstCuentaConceptoDetalle) {
						if(lista.getIntParaTipoConceptoCod()==3 && lista.getIntItemConcepto()==15)
							parametro.put("P1", "X");
						else
							parametro.put("P1", "");
						if(lista.getIntParaTipoConceptoCod()==3 && lista.getIntItemConcepto()==17)
							parametro.put("P2", "X");
						else 
							parametro.put("P2", "");
						if(lista.getIntParaTipoConceptoCod()==3 && lista.getIntItemConcepto()==18)
							parametro.put("P3", "X");
						else
							parametro.put("P3", "");
						 Date date = new Date();

						if(lista.getTsFin()!=null){
						Timestamp fechaFin = lista.getTsFin();
						Date dateFin = new Date(fechaFin.getTime()); 
						if(lista.getIntParaTipoConceptoCod()==3 && date.before(dateFin) && dateFin!=null){

						Timestamp fechaIni = lista.getTsInicio();
						Date dateInicio = new Date(fechaIni.getTime());
						
						String fecha = df4.format(dateInicio);
						String dia = fecha.substring(0, 1).toUpperCase();
						String fechafinal = df4.format(dateInicio);
						String todo = dia + fechafinal.substring(1, fechafinal.length());
						parametro.put("P_FECHAESTADO", todo);
						}else{
							parametro.put("P_FECHAESTADO", "");
						}
					}else{
//						DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
						Timestamp fechaIni = lista.getTsInicio();
						Date dateInicio = new Date(fechaIni.getTime());
						
						String fecha = df4.format(dateInicio);
						String dia = fecha.substring(0, 1).toUpperCase();
						String fechafinal = df4.format(dateInicio);
						String todo = dia + "" + fechafinal.substring(1, fechafinal.length());
						parametro.put("P_FECHAESTADO", todo);

					}
				}
			}
	            else {
				parametro.put("P1", "");
				parametro.put("P2", "");
				parametro.put("P3", "");
				parametro.put("P_FECHAESTADO", "");
			}
		} else {
			parametro.put("P1", "");
			parametro.put("P2", "");
			parametro.put("P3", "");
			parametro.put("P_FECHAESTADO", "");
		}
			parametro.put("P_IDCODIGOPERSONA", socioNatuSelected.getPersona().getIntIdPersona()!=null?socioNatuSelected.getPersona().getIntIdPersona():" ");
			String apellidoPaterno = (socioNatuSelected.getPersona().getNatural().getStrApellidoPaterno().toUpperCase()!=null?socioNatuSelected.getPersona().getNatural().getStrApellidoPaterno().toUpperCase():" ");
			String apellidoMaterno = (socioNatuSelected.getPersona().getNatural().getStrApellidoMaterno().toUpperCase()!=null?socioNatuSelected.getPersona().getNatural().getStrApellidoMaterno().toUpperCase():" ");
			String Nombre = (socioNatuSelected.getPersona().getNatural().getStrNombres().toUpperCase()!=null?socioNatuSelected.getPersona().getNatural().getStrNombres().toUpperCase():" "); 
			parametro.put("P_NOMBRECOMPLETO", apellidoPaterno +" " + apellidoMaterno + ", " + Nombre);
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			if(beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntEstadoCod()==1){
				listEstructura = facade1.getListaEstructuraPorNivelCodigo(beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntNivel(),
																		  beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntCodigo());
				parametro.put("P_DEPENDENCIA", listEstructura.get(0).getJuridica().getStrRazonSocial());
				}else{
					parametro.put("P_DEPENDENCIA", "");
				}
	        List<Domicilio> lstDomicilio = beanSocioComp.getPersona().getListaDomicilio();
            Domicilio dom = new Domicilio();
            
            if (lstDomicilio != null && lstDomicilio.size() >= 1) {
            	dom = lstDomicilio.get(0);
            }
			parametro.put("P_DEPARTAMENTO", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkDpto())).get(0).getStrDescripcion()!=null?(facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkDpto())).get(0).getStrDescripcion():"");
   			
			System.out.println("Parametro : " + parametro);
			strNombreReporte = "solicitudFonret";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstReporteVacia), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirSolicitudDeAfiliacion ---> "+e);
		}   	
    }

    /*Fin impresion de reporte*/
    
    /* Inicio - Auditoria - GTorresBrousset 24.abr.2014 */

	public List<Auditoria> generarAuditoriaSocio(Integer intTipoCambio, Socio socio) throws BusinessException{
		log.info("Inicio");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			// Inserción de Nuevos Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				
				/* Inicio tabla CSO_SOCIO */
				// PERS_PERSONA_N_PK
//				if(socio.getId().getIntIdPersona() != null) {
//					List<String> listaLlaves = new ArrayList<String>();
//					listaLlaves.add("" + socio.getId().getIntIdPersona());
//					listaLlaves.add("" + socio.getId().getIntIdEmpresa());
//					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIO);
//					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIO_PERS_PERSONA_N_PK);
//					auditoria.setStrValoranterior(null);
//					auditoria.setStrValornuevo("" + socio.getId().getIntIdPersona());
//					
//					lista.add(auditoria);
//				}

				// PERS_EMPRESA_N_PK
//				if(socio.getId().getIntIdEmpresa() != null) {
//					List<String> listaLlaves = new ArrayList<String>();
//					listaLlaves.add("" + socio.getId().getIntIdPersona());
//					listaLlaves.add("" + socio.getId().getIntIdEmpresa());
//					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIO);
//					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIO_PERS_EMPRESA_N_PK);
//					auditoria.setStrValoranterior(null);
//					auditoria.setStrValornuevo("" + socio.getId().getIntIdEmpresa());
//					
//					lista.add(auditoria);
//				}

				// SOCI_APEPLLIDOPATERNO_V
//				if(socio.getStrApePatSoc() != null) {
//					List<String> listaLlaves = new ArrayList<String>();
//					listaLlaves.add("" + socio.getId().getIntIdPersona());
//					listaLlaves.add("" + socio.getId().getIntIdEmpresa());
//					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIO);
//					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIO_SOCI_APEPLLIDOPATERNO_V);
//					auditoria.setStrValoranterior(null);
//					auditoria.setStrValornuevo("" + socio.getStrApePatSoc());
//					
//					lista.add(auditoria);
//				}

				// SOCI_APELLIDOMATERNO_V
//				if(socio.getStrApeMatSoc() != null) {
//					List<String> listaLlaves = new ArrayList<String>();
//					listaLlaves.add("" + socio.getId().getIntIdPersona());
//					listaLlaves.add("" + socio.getId().getIntIdEmpresa());
//					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIO);
//					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIO_SOCI_APELLIDOMATERNO_V);
//					auditoria.setStrValoranterior(null);
//					auditoria.setStrValornuevo("" + socio.getStrApeMatSoc());
//					
//					lista.add(auditoria);
//				}

				// SOCI_NOMBRES_V
//				if(socio.getStrNombreSoc() != null) {
//					List<String> listaLlaves = new ArrayList<String>();
//					listaLlaves.add("" + socio.getId().getIntIdPersona());
//					listaLlaves.add("" + socio.getId().getIntIdEmpresa());
//					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIO);
//					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIO_SOCI_NOMBRES_V);
//					auditoria.setStrValoranterior(null);
//					auditoria.setStrValornuevo("" + socio.getStrNombreSoc());
//					
//					lista.add(auditoria);
//				}
					
				/* Inicio Tabla CSO_SOCIOESTRUCTURA */
				if (socio.getListSocioEstructura() != null && socio.getListSocioEstructura().size() > 0)
					for(SocioEstructura socioEstructura : socio.getListSocioEstructura()) {
						// PERS_PERSONA_N_PK
//						if(socioEstructura.getId().getIntIdPersona() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PERS_PERSONA_N_PK);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getId().getIntIdPersona());
//
//							lista.add(auditoria);
//						}
						
						// CSOC_ITEMSOCIOESTRUCTURA_N
//						if(socioEstructura.getId().getIntItemSocioEstructura() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_CSOC_ITEMSOCIOESTRUCTURA_N);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getId().getIntItemSocioEstructura());
//
//							lista.add(auditoria);
//						}
						
						// PERS_EMPRESA_N_PK
//						if(socioEstructura.getId().getIntIdEmpresa() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PERS_EMPRESA_N_PK);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getId().getIntIdEmpresa());
//
//							lista.add(auditoria);
//						}
						
						// PERS_EMPRESASUCUSUARIO_N_PK
//						if(socioEstructura.getIntEmpresaSucUsuario() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PERS_EMPRESASUCUSU_N_PK);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getIntEmpresaSucUsuario());
//
//							lista.add(auditoria);
//						}
						
						// SUCU_IDSUCURSALUSUARIO_N_PK
						if(socioEstructura.getIntIdSucursalUsuario() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_SUCU_IDSUCURSALUSU_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntIdSucursalUsuario());

							lista.add(auditoria);
						}
						
						// SUDE_IDSUBSUCURSALUSUARIO_N_PK
						if(socioEstructura.getIntIdSubSucursalUsuario() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_SUDE_IDSUBSUCURSALUSU_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntIdSubSucursalUsuario());

							lista.add(auditoria);
						}
						
						// PERS_EMPRESASUCADMINISTRA_N_PK
//						if(socioEstructura.getIntEmpresaSucUsuario() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PERS_EMPRESASUCADMI_N_PK);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getIntEmpresaSucAdministra());
//
//							lista.add(auditoria);
//						}
						
						// SUCU_IDSUCURSALADMINISTRA_N_PK
						if(socioEstructura.getIntIdSucursalAdministra() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_SUCU_IDSUCURSALADMI_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntIdSucursalAdministra());

							lista.add(auditoria);
						}
						
						// SUDE_IDSUBSUCURSALADMINISTRA_N
						if(socioEstructura.getIntIdSubsucurAdministra() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_SUDE_IDSUBSUCURSALADMINI_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntIdSubsucurAdministra());

							lista.add(auditoria);
						}
						
						// PARA_TIPOSOCIO_N_COD
						if(socioEstructura.getIntTipoSocio() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PARA_TIPOSOCIO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntTipoSocio());

							lista.add(auditoria);
						}
						
						// PARA_MODALIDAD_N_COD
						if(socioEstructura.getIntModalidad() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PARA_MODALIDAD_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntModalidad());

							lista.add(auditoria);
						}
						
						// CSOC_NIVEL_N
						if(socioEstructura.getIntNivel() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_CSOC_NIVEL_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntNivel());

							lista.add(auditoria);
						}
						
						// CSOC_CODIGO_N
						if(socioEstructura.getIntCodigo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_CSOC_CODIGO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntCodigo());

							lista.add(auditoria);
						}
						
						// SOES_CODIGOPLANILLA_V
						if(socioEstructura.getStrCodigoPlanilla() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_SOES_CODIGOPLANILLA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getStrCodigoPlanilla());

							lista.add(auditoria);
						}
						
						// PARA_TIPOESTRUCTURA_N_COD
						if(socioEstructura.getIntTipoEstructura() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PARA_TIPOESTRUCTURA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + socioEstructura.getIntTipoEstructura());

							lista.add(auditoria);
						}
						
						// SOES_FECHAREGISTRO_D
//						if(socioEstructura.getDtFechaRegistro() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_SOES_FECHAREGISTRO_D);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getDtFechaRegistro());
//
//							lista.add(auditoria);
//						}
						
						// PARA_ESTADO_N_COD
//						if(socioEstructura.getIntEstadoCod() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PARA_ESTADO_N_COD);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getIntEstadoCod());
//
//							lista.add(auditoria);
//						}
						
						// PERS_EMPRESAUSUARIO_N_PK
//						if(socioEstructura.getIntEmpresaUsuario() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PERS_EMPRESAUSUARIO_N_PK);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getIntEmpresaUsuario());
//
//							lista.add(auditoria);
//						}
						
						// PERS_PERSONAUSUARIO_N_PK
//						if(socioEstructura.getIntPersonaUsuario() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PERS_PERSONAUSUARIO_N_PK);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getIntPersonaUsuario());
//
//							lista.add(auditoria);
//						}
						
						// SOES_FECHAELIMINACION_D
//						if(socioEstructura.getTsFechaEliminacion() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_SOES_FECHAELIMINACION_D);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getTsFechaEliminacion());
//
//							lista.add(auditoria);
//						}
						
						// PERS_EMPRESAELIMINAR_N_PK
//						if(socioEstructura.getIntEmpresaEliminar() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PERS_EMPRESAELIMINAR_N_PK);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getIntEmpresaEliminar());
//
//							lista.add(auditoria);
//						}
						
						// PERS_PERSONAELIMINAR_N_PK
//						if(socioEstructura.getIntPersonaEliminar() != null) {
//							List<String> listaLlaves = new ArrayList<String>();
//							listaLlaves.add("" + socioEstructura.getId().getIntIdPersona());
//							listaLlaves.add("" + socioEstructura.getId().getIntItemSocioEstructura());
//							listaLlaves.add("" + socioEstructura.getId().getIntIdEmpresa());
//							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUCTURA);
//							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSO_SOCIOESTRUC_PERS_PERSONAELIMINAR_N_PK);
//							auditoria.setStrValoranterior(null);
//							auditoria.setStrValornuevo("" + socioEstructura.getIntPersonaEliminar());
//
//							lista.add(auditoria);
//						}
					}
			}
		} catch (Exception e) {
			log.error("Error en generarAuditoriaSocio --> " + e);
		}
		log.info("Fin");
		return lista;
	}
    /* Se implementa la Auditoría para los Nuevos Registros y las Modificaciones */
	public List<Auditoria> generarAuditoriaPersona(Integer intTipoCambio, Persona persona) throws BusinessException{
		log.info("Inicio");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			// Inserción de Nuevos Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				
				/* Inicio tabla PER_PERSONA */
				// PERS_PERSONA_N
				if(persona.getIntIdPersona() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_PERSONA_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + persona.getIntIdPersona());
					
					lista.add(auditoria);
				}
				
				// TIPOPERSONA_N_COD
				if(persona.getIntTipoPersonaCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_TIPOPERSONA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + persona.getIntTipoPersonaCod());

					lista.add(auditoria);
				}
				
				// PERS_RUC_V
				if(persona.getStrRuc() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_RUC_V);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + persona.getStrRuc());

					lista.add(auditoria);
				}
				
				// PERS_ESTADOPERSONA_N_COD
				if(persona.getIntEstadoCod() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_ESTADOPERSONA_N_COD);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + persona.getIntEstadoCod());
					
					lista.add(auditoria);
				}
				
				// PERS_FECHABAJARUC_D
				if(persona.getStrFechaBajaRuc() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_FECHABAJARUC_D);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + persona.getStrFechaBajaRuc());

					lista.add(auditoria);
				}
				
				/* Inicio tabla PER_JURIDICA */
				// PERS_PERSONA_N
				if(persona.getJuridica() != null) {
					if(persona.getJuridica().getIntIdPersona() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_PERS_PERSONA_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntIdPersona());
	
						lista.add(auditoria);
					}
	
					// JURI_RAZONSOCIAL_V
					if(persona.getJuridica().getStrRazonSocial() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_RAZONSOCIAL_V);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getStrRazonSocial());
	
						lista.add(auditoria);
					}
	
					// JURI_NOMBRECOMERCIAL_V
					if(persona.getJuridica().getStrNombreComercial() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_NOMBRECOMERCIAL_V);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getStrNombreComercial());
	
						lista.add(auditoria);
					}
	
					// JURI_SIGLAS_V
					if(persona.getJuridica().getStrSiglas() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_SIGLAS_V);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getStrSiglas());
	
						lista.add(auditoria);
					}
	
					// JURI_FECHAINSCRIPCION_D
					if(persona.getJuridica().getDtFechaInscripcion() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_FECHAINSCRIPCION_D);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getDtFechaInscripcion());
	
						lista.add(auditoria);
					}
	
					// JURI_NROTRABAJADORES_N
					if(persona.getJuridica().getIntNroTrabajadores() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_NROTRABAJADORES_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntNroTrabajadores());
	
						lista.add(auditoria);
					}
	
					// JURI_TIPOEMPRESA_N_COD
					if(persona.getJuridica().getIntTipoEmpresaCod() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_TIPOEMPRESA_N_COD);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntTipoEmpresaCod());
	
						lista.add(auditoria);
					}
	
					// JURI_TIPOCONTRIBUYENTE_N_COD
					if(persona.getJuridica().getIntTipoContribuyenteCod() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_TIPOCONTRIBUYENTE_N_COD);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntTipoContribuyenteCod());
	
						lista.add(auditoria);
					}
	
					// JURI_CONDCONTRIBUYENTE_N
					if(persona.getJuridica().getIntCondContribuyente() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_CONDCONTRIBUYENTE_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntCondContribuyente());
	
						lista.add(auditoria);
					}
	
					// JURI_ESTCONTRIBUYENTE_N_COD
					if(persona.getJuridica().getIntEstadoContribuyenteCod() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_ESTCONTRIBUYENTE_N_COD);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntEstadoContribuyenteCod());
	
						lista.add(auditoria);
					}
	
					// JURI_EMISIONCOMPROBANTE_N
					if(persona.getJuridica().getIntEmisionComprobante() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_EMISIONCOMPROBANTE_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntEmisionComprobante());
	
						lista.add(auditoria);
					}
	
					// JURI_SISTEMACONTABLE_N
					if(persona.getJuridica().getIntSistemaContable() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_SISTEMACONTABLE_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntSistemaContable());
	
						lista.add(auditoria);
					}
	
					// JURI_COMERCIOEXTERIOR_N
					if(persona.getJuridica().getIntComercioExterior() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_COMERCIOEXTERIOR_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getIntComercioExterior());
	
						lista.add(auditoria);
					}
	
					// JURI_FECHAINICIOACT_D
					if(persona.getJuridica().getDtFechaInicioAct() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_FECHAINICIOACT_D);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getDtFechaInicioAct());
	
						lista.add(auditoria);
					}
	
					// JURI_FICHAREGPUBLICO_V
					if(persona.getJuridica().getStrFichaRegPublico() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getJuridica().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_JURIDICA);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_JURIDICA_JURI_FICHAREGPUBLICO_V);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getJuridica().getStrFichaRegPublico());
	
						lista.add(auditoria);
					}
				}
				
				/* Inicio tabla PER_NATURAL */
				// PERS_PERSONA_N
				if(persona.getNatural() != null) {
					if(persona.getNatural().getIntIdPersona() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_PERS_PERSONA_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntIdPersona());
	
						lista.add(auditoria);
					}
					
					// NATU_APELLIDOPATERNO_V
					if(persona.getNatural().getStrApellidoPaterno() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_NATU_APELLIDOPATERNO_V);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getStrApellidoPaterno());
	
						lista.add(auditoria);
					}
					
					// NATU_APELLIDOMATERNO_V
					if(persona.getNatural().getStrApellidoMaterno() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_NATU_APELLIDOMATERNO_V);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getStrApellidoMaterno());
	
						lista.add(auditoria);
					}
					
					// NATU_NOMBRES_V
					if(persona.getNatural().getStrNombres() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_NATU_NOMBRES_V);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getStrNombres());
	
						lista.add(auditoria);
					}
					
					// NATU_FECHANACIMIENTO_D
					if(persona.getNatural().getStrFechaNacimiento() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_NATU_FECHANACIMIENTO_D);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getStrFechaNacimiento());
	
						lista.add(auditoria);
					}
					
					// NATU_SEXO_N_COD
					if(persona.getNatural().getIntSexoCod() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_NATU_SEXO_N_COD);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntSexoCod());
	
						lista.add(auditoria);
					}
					
					// NATU_ESTADOCIVIL_N_COD
					if(persona.getNatural().getIntEstadoCivilCod() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_NATU_ESTADOCIVIL_N_COD);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntEstadoCivilCod());
	
						lista.add(auditoria);
					}
					
					// NATU_TIENEEMPRESA_N
					if(persona.getNatural().getIntTieneEmpresa() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_NATU_TIENEEMPRESA_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntTieneEmpresa());
	
						lista.add(auditoria);
					}
					
					// PARA_TIPOARCHIVOFOTO_N_COD
					if(persona.getNatural().getIntTipoArchivoFoto() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_PARA_TIPOARCHIVOFOTO_N_COD);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntTipoArchivoFoto());
	
						lista.add(auditoria);
					}
					
					// PARA_ITEMARCHIVOFOTO_N
					if(persona.getNatural().getIntItemArchivoFoto() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_PARA_ITEMARCHIVOFOTO_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntItemArchivoFoto());
	
						lista.add(auditoria);
					}
					
					// PARA_ITEMHISTORICOFOTO_N
					if(persona.getNatural().getIntItemHistoricoFoto() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_PARA_ITEMHISTORICOFOTO_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntItemHistoricoFoto());
	
						lista.add(auditoria);
					}
					
					// PARA_TIPOARCHIVOFIRMA_N_COD
					if(persona.getNatural().getIntTipoArchivoFirma() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_PARA_TIPOARCHIVOFIRMA_N_COD);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntTipoArchivoFirma());
	
						lista.add(auditoria);
					}
					
					// PARA_ITEMARCHIVOFIRMA_N
					if(persona.getNatural().getIntItemArchivoFirma() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_PARA_ITEMARCHIVOFIRMA_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntItemArchivoFirma());
	
						lista.add(auditoria);
					}
					
					// PARA_ITEMHISTORICOFIRMA_N
					if(persona.getNatural().getIntItemHistoricoFirma() != null) {
						List<String> listaLlaves = new ArrayList<String>();
						listaLlaves.add("" + persona.getNatural().getIntIdPersona());
						auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
						auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_PARA_ITEMHISTORICOFIRMA_N);
						auditoria.setStrValoranterior(null);
						auditoria.setStrValornuevo("" + persona.getNatural().getIntItemHistoricoFirma());
	
						lista.add(auditoria);
					}
					
					/* Inicio Tabla PER_LABORAL */
					// PERS_PERSONA_N
					if(persona.getNatural().getPerLaboral() != null) {
						if(persona.getNatural().getPerLaboral().getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
		
							lista.add(auditoria);
						}
		
						// PERS_ITEMLABORAL_N
						if(persona.getNatural().getPerLaboral().getId().getIntItemLaboral() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PERS_ITEMLABORAL_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
		
							lista.add(auditoria);
						}
		
						// PARA_CONDICIONLABORAL_N_COD
						if(persona.getNatural().getPerLaboral().getIntCondicionLaboral() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_CONDICIONLABORAL_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getIntCondicionLaboral());
		
							lista.add(auditoria);
						}
		
						// PARA_CONDICIONLABORALDET_N_COD
						if(persona.getNatural().getPerLaboral().getIntCondicionLaboralDet() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_CONDICIONLABORALDET_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getIntCondicionLaboralDet());
		
							lista.add(auditoria);
						}
		
						// PARA_TIPOCONTRATO_N_COD
						if(persona.getNatural().getPerLaboral().getIntTipoContrato() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_TIPOCONTRATO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getIntTipoContrato());
		
							lista.add(auditoria);
						}
		
						// LABO_CONTRATODESDE_D
						if(persona.getNatural().getPerLaboral().getDtInicioContrato() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_CONTRATODESDE_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getDtInicioContrato());
		
							lista.add(auditoria);
						}
		
						// LABO_CONTRATOHASTA_D
						if(persona.getNatural().getPerLaboral().getDtFinContrato() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_CONTRATOHASTA_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getDtFinContrato());
		
							lista.add(auditoria);
						}
		
						// LABO_INICIOSERVICIO_D
						if(persona.getNatural().getPerLaboral().getDtInicioServicio() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_INICIOSERVICIO_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getDtInicioServicio());
		
							lista.add(auditoria);
						}
		
						// LABO_REMUNERACION_N
						if(persona.getNatural().getPerLaboral().getBdRemuneracion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_REMUNERACION_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getBdRemuneracion());
		
							lista.add(auditoria);
						}
		
						// PARA_REGIMENLABORAL_N_COD
						if(persona.getNatural().getPerLaboral().getIntRegimenLaboral() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_REGIMENLABORAL_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getIntRegimenLaboral());
		
							lista.add(auditoria);
						}
		
						// LABO_SOLICITUDONP_V
						if(persona.getNatural().getPerLaboral().getStrSolicitudONP() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_SOLICITUDONP_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getStrSolicitudONP());
		
							lista.add(auditoria);
						}
		
						// PARA_TIPOARCHIVO_N_COD
						if(persona.getNatural().getPerLaboral().getIntTipoArchivoContrato() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_TIPOARCHIVO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getIntTipoArchivoContrato());
		
							lista.add(auditoria);
						}
		
						// PARA_ITEMARCHIVO_N
						if(persona.getNatural().getPerLaboral().getIntItemArchivoContrato() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_ITEMARCHIVO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getIntItemArchivoContrato());
		
							lista.add(auditoria);
						}
		
						// PARA_ITEMHISTORICO_N
						if(persona.getNatural().getPerLaboral().getIntItemHistoricoContrato() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_ITEMHISTORICO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getIntItemHistoricoContrato());
		
							lista.add(auditoria);
						}
		
						// LABO_CENTROTRABAJO_V
						if(persona.getNatural().getPerLaboral().getStrCentroTrabajo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
							listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_CENTROTRABAJO_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getNatural().getPerLaboral().getStrCentroTrabajo());
		
							lista.add(auditoria);
						}
					}
				}
				
				/* Inicio tabla PER_PERSONAEMPRESA */
				// PERS_EMPRESA_N
				if(persona.getPersonaEmpresa().getId().getIntIdEmpresa() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getPersonaEmpresa().getId().getIntIdEmpresa());
					listaLlaves.add("" + persona.getPersonaEmpresa().getId().getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONAEMPRESA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONAEMPRESA_PERS_EMPRESA_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + persona.getPersonaEmpresa().getId().getIntIdEmpresa());

					lista.add(auditoria);
				}
				
				// PERS_PERSONA_N
				if(persona.getPersonaEmpresa().getId().getIntIdPersona() != null) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getPersonaEmpresa().getId().getIntIdEmpresa());
					listaLlaves.add("" + persona.getPersonaEmpresa().getId().getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONAEMPRESA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONAEMPRESA_PERS_PERSONA_N);
					auditoria.setStrValoranterior(null);
					auditoria.setStrValornuevo("" + persona.getPersonaEmpresa().getId().getIntIdPersona());

					lista.add(auditoria);
				}
					
				/* Inicio Tabla PER_VINCULOS */
				if (persona.getPersonaEmpresa().getListaVinculo() != null && persona.getPersonaEmpresa().getListaVinculo().size() > 0)
					for(Vinculo vinculo : persona.getPersonaEmpresa().getListaVinculo()) {
						// VINC_ITEMVINCULO_N
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getIntItemVinculo());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_VINC_ITEMVINCULO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getIntItemVinculo());

							lista.add(auditoria);
						}
						
						// PERS_EMPRESA_N
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getIntIdEmpresa());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_PERS_EMPRESA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getIntIdEmpresa());

							lista.add(auditoria);
						}
						
						// PERS_PERSONA_N
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getIntIdPersona());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getIntIdPersona());

							lista.add(auditoria);
						}
						
						// PERS_TIPOVINCULO_N_COD
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getIntTipoVinculoCod());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_PERS_TIPOVINCULO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getIntTipoVinculoCod());

							lista.add(auditoria);
						}
						
						// PERS_EMPRESAVINC_N
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getIntEmpresaVinculo());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_PERS_EMPRESAVINC_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getIntEmpresaVinculo());

							lista.add(auditoria);
						}
						
						// PERS_PERSONAVINC_N
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getIntPersonaVinculo());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_PERS_PERSONAVINC_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getIntPersonaVinculo());

							lista.add(auditoria);
						}
						
						// VINC_CARGOVINCULO_N_COD
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getIntCargoVinculoCod());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_VINC_CARGOVINCULO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getIntCargoVinculoCod());

							lista.add(auditoria);
						}
						
						// VINC_INICIOVINCULO_D
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getDtInicioCargo());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_VINC_INICIOVINCULO_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getDtInicioCargo());

							lista.add(auditoria);
						}
						
						// VINC_ESTADO_N_COD
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getIntEstadoCod());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_VINC_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getIntEstadoCod());

							lista.add(auditoria);
						}
						
						// VINC_FECHAELIMINACION_D
						if(vinculo.getIntItemVinculo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + vinculo.getTsFechaEliminacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_VINCULOS);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_VINCULOS_VINC_FECHAELIMINACION_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + vinculo.getTsFechaEliminacion());

							lista.add(auditoria);
						}
					}
		
				/* Inicio Tabla PER_PERSONAROL */
				if (persona.getPersonaEmpresa().getListaPersonaRol() != null && persona.getPersonaEmpresa().getListaPersonaRol().size() > 0)
					for(PersonaRol personaRol : persona.getPersonaEmpresa().getListaPersonaRol()) {
						// PERS_EMPRESA_N
						if(personaRol.getId().getIntIdEmpresa() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + personaRol.getId().getIntIdEmpresa());
							listaLlaves.add("" + personaRol.getId().getIntIdPersona());
							listaLlaves.add("" + personaRol.getId().getIntParaRolPk());
							listaLlaves.add("" + personaRol.getId().getDtFechaInicio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONAROL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONAROL_PERS_EMPRESA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + personaRol.getId().getIntIdEmpresa());

							lista.add(auditoria);
						}

						// PERS_PERSONA_N
						if(personaRol.getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + personaRol.getId().getIntIdEmpresa());
							listaLlaves.add("" + personaRol.getId().getIntIdPersona());
							listaLlaves.add("" + personaRol.getId().getIntParaRolPk());
							listaLlaves.add("" + personaRol.getId().getDtFechaInicio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONAROL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONAROL_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + personaRol.getId().getIntIdPersona());

							lista.add(auditoria);
						}

						// PARA_ROL_N_PK
						if(personaRol.getId().getIntParaRolPk() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + personaRol.getId().getIntIdEmpresa());
							listaLlaves.add("" + personaRol.getId().getIntIdPersona());
							listaLlaves.add("" + personaRol.getId().getIntParaRolPk());
							listaLlaves.add("" + personaRol.getId().getDtFechaInicio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONAROL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONAROL_PARA_ROL_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + personaRol.getId().getIntParaRolPk());

							lista.add(auditoria);
						}

						// PERS_FECHAINICIO_D
						if(personaRol.getId().getDtFechaInicio() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + personaRol.getId().getIntIdEmpresa());
							listaLlaves.add("" + personaRol.getId().getIntIdPersona());
							listaLlaves.add("" + personaRol.getId().getIntParaRolPk());
							listaLlaves.add("" + personaRol.getId().getDtFechaInicio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONAROL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONAROL_PERS_FECHAINICIO_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + personaRol.getId().getDtFechaInicio());

							lista.add(auditoria);
						}

						// PERO_FECHAFIN_D
						if(personaRol.getDtFechaFin() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + personaRol.getId().getIntIdEmpresa());
							listaLlaves.add("" + personaRol.getId().getIntIdPersona());
							listaLlaves.add("" + personaRol.getId().getIntParaRolPk());
							listaLlaves.add("" + personaRol.getId().getDtFechaInicio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONAROL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONAROL_PERO_FECHAFIN_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + personaRol.getDtFechaFin());

							lista.add(auditoria);
						}

						// PERO_ESTADO_N_COD
						if(personaRol.getIntEstadoCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + personaRol.getId().getIntIdEmpresa());
							listaLlaves.add("" + personaRol.getId().getIntIdPersona());
							listaLlaves.add("" + personaRol.getId().getIntParaRolPk());
							listaLlaves.add("" + personaRol.getId().getDtFechaInicio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONAROL);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONAROL_PERO_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + personaRol.getIntEstadoCod());

							lista.add(auditoria);
						}
					}
				
				
				/* Inicio Tabla PER_DOCUMENTO */
				if (persona.getListaDocumento() != null && persona.getListaDocumento().size() > 0)
					for(Documento documento : persona.getListaDocumento()) {
						// PERS_PERSONA_N
						if(documento.getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + documento.getId().getIntIdPersona());
							listaLlaves.add("" + documento.getId().getIntItemDocumento());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + documento.getId().getIntIdPersona());

							lista.add(auditoria);
						}

						// PERS_ITEMDOCUMENTO_N
						if(documento.getId().getIntItemDocumento() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + documento.getId().getIntIdPersona());
							listaLlaves.add("" + documento.getId().getIntItemDocumento());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_PERS_ITEMDOCUMENTO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + documento.getId().getIntItemDocumento());

							lista.add(auditoria);
						}

						// PARA_TIPOIDENTIDAD_N_COD
						if(documento.getIntTipoIdentidadCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + documento.getId().getIntIdPersona());
							listaLlaves.add("" + documento.getId().getIntItemDocumento());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_PARA_TIPOIDENTIDAD_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + documento.getIntTipoIdentidadCod());

							lista.add(auditoria);
						}

						// DOCU_NUMEROIDENTIDAD_V
						if(documento.getStrNumeroIdentidad() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + documento.getId().getIntIdPersona());
							listaLlaves.add("" + documento.getId().getIntItemDocumento());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_DOCU_NUMEROIDENTIDAD_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + documento.getStrNumeroIdentidad());

							lista.add(auditoria);
						}

						// PARA_ESTADO_N_COD
						if(documento.getIntEstadoCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + documento.getId().getIntIdPersona());
							listaLlaves.add("" + documento.getId().getIntItemDocumento());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_PARA_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + documento.getIntEstadoCod());

							lista.add(auditoria);
						}
					}
				
				/* Inicio Tabla PER_COMUNICACION */
				if (persona.getListaComunicacion() != null && persona.getListaComunicacion().size() > 0)
					for(Comunicacion comunicacion : persona.getListaComunicacion()) {
						// PERS_PERSONA_N
						if(comunicacion.getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getId().getIntIdPersona());

							lista.add(auditoria);
						}

						// PERS_COMUNICACION_N
						if(comunicacion.getId().getIntIdComunicacion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_PERS_COMUNICACION_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getId().getIntIdComunicacion());

							lista.add(auditoria);
						}

						// COMU_TIPOCOMUNICACION_N_COD
						if(comunicacion.getIntTipoComunicacionCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_TIPOCOMUNICACION_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getIntTipoComunicacionCod());

							lista.add(auditoria);
						}

						// COMU_SUBTIPOCOMUNICACION_N_COD
						if(comunicacion.getIntSubTipoComunicacionCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_SUBTIPOCOMUNICAC_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getIntSubTipoComunicacionCod());

							lista.add(auditoria);
						}

						// COMU_DATO_V
						if(comunicacion.getStrDato() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_DATO_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getStrDato());

							lista.add(auditoria);
						}

						// COMU_TIPOLINEA_N_COD
						if(comunicacion.getIntTipoLineaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_TIPOLINEA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getIntTipoLineaCod());

							lista.add(auditoria);
						}

						// COMU_NUMERO_N
						if(comunicacion.getIntNumero() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_NUMERO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getIntNumero());

							lista.add(auditoria);
						}

						// COMU_ANEXO_N
						if(comunicacion.getIntAnexo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_ANEXO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getIntAnexo());

							lista.add(auditoria);
						}

						// COMU_DESCRIPCION_V
						if(comunicacion.getStrDescripcion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_DESCRIPCION_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getStrDescripcion());

							lista.add(auditoria);
						}

						// COMU_CASOEMERGENCIA_N
						if(comunicacion.getIntCasoEmergencia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_CASOEMERGENCIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getIntCasoEmergencia());

							lista.add(auditoria);
						}

						// COMU_ESTADO_N_COD
						if(comunicacion.getIntEstadoCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getIntEstadoCod());

							lista.add(auditoria);
						}

						// COMU_FECHAELIMINACION_D
						if(comunicacion.getTsFechaEliminacion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + comunicacion.getId().getIntIdPersona());
							listaLlaves.add("" + comunicacion.getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_FECHAELIMINACION_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + comunicacion.getTsFechaEliminacion());

							lista.add(auditoria);
						}
					}
				
				/* Inicio Tabla PER_DOMICILIO */
				if (persona.getListaDomicilio() != null && persona.getListaDomicilio().size() > 0)
					for(Domicilio domicilio : persona.getListaDomicilio()) {
						// PERS_PERSONA_N
						if(domicilio.getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getId().getIntIdPersona());

							lista.add(auditoria);
						}

						// PERS_DOMICILIO_N
						if(domicilio.getId().getIntIdDomicilio() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PERS_DOMICILIO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getId().getIntIdDomicilio());

							lista.add(auditoria);
						}

						// DOMI_TIPODOMICILIO_N_COD
						if(domicilio.getIntTipoDomicilioCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPODOMICILIO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntTipoDomicilioCod());

							lista.add(auditoria);
						}

						// DOMI_TIPODIRECCION_N_COD
						if(domicilio.getIntTipoDireccionCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPODIRECCION_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntTipoDireccionCod());

							lista.add(auditoria);
						}

						// DOMI_TIPOVIVIENDA_N_COD
						if(domicilio.getIntTipoViviendaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOVIVIENDA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntTipoViviendaCod());

							lista.add(auditoria);
						}

						// PARA_UBIGEO_N_PK
						if(domicilio.getIntParaUbigeoPkDistrito() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_UBIGEO_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntParaUbigeoPkDistrito());

							lista.add(auditoria);
						}

						// DOMI_TIPOVIA_N_COD
						if(domicilio.getIntTipoViaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOVIA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntTipoViaCod());

							lista.add(auditoria);
						}

						// DOMI_NOMBREVIA_V
						if(domicilio.getStrNombreVia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NOMBREVIA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getStrNombreVia());

							lista.add(auditoria);
						}

						// DOMI_NUMEROVIA_N
						if(domicilio.getIntNumeroVia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NUMEROVIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntNumeroVia());

							lista.add(auditoria);
						}

						// DOMI_INTERIOR_V
						if(domicilio.getStrInterior() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_INTERIOR_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getStrInterior());

							lista.add(auditoria);
						}

						// DOMI_TIPOZONA_N_COD
						if(domicilio.getIntTipoZonaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOZONA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntTipoZonaCod());

							lista.add(auditoria);
						}

						// DOMI_REFERENCIA_V
						if(domicilio.getStrReferencia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_REFERENCIA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getStrReferencia());

							lista.add(auditoria);
						}

						// DOMI_ENVIACORRESPONDENCIA_N
						if(domicilio.getIntEnviaCorrespondencia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_ENVIACORRESPONDENCIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntEnviaCorrespondencia());

							lista.add(auditoria);
						}

						// DOMI_OBSERVACION_V
						if(domicilio.getStrObservacion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_OBSERVACION_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getStrObservacion());

							lista.add(auditoria);
						}

						// DOMI_ESTADO_N_COD
						if(domicilio.getIntEstadoCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntEstadoCod());

							lista.add(auditoria);
						}

						// DOMI_NOMBREZONA_V
						if(domicilio.getStrNombreZona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NOMBREZONA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getStrNombreZona());

							lista.add(auditoria);
						}

						// DOMI_FECHAELIMINACION_D
						if(domicilio.getTsFechaEliminacion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_FECHAELIMINACION_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getTsFechaEliminacion());

							lista.add(auditoria);
						}

						// PARA_TIPOARCHIVO_N_COD
						if(domicilio.getIntParaTipoArchivo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_TIPOARCHIVO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntParaTipoArchivo());

							lista.add(auditoria);
						}

						// PARA_ITEMARCHIVO_N
						if(domicilio.getIntParaItemArchivo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_ITEMARCHIVO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntParaItemArchivo());

							lista.add(auditoria);
						}

						// PARA_ITEMHISTORICO_N
						if(domicilio.getIntParaItemHistorico() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + domicilio.getId().getIntIdPersona());
							listaLlaves.add("" + domicilio.getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_ITEMHISTORICO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + domicilio.getIntParaItemHistorico());

							lista.add(auditoria);
						}
					}
				
				/* Inicio Tabla PER_CUENTABANCARIA */
				if (persona.getListaCuentaBancaria() != null && persona.getListaCuentaBancaria().size() > 0)
					for(CuentaBancaria cuentaBancaria : persona.getListaCuentaBancaria()) {
						// PERS_PERSONA_N
						if(cuentaBancaria.getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getId().getIntIdPersona());

							lista.add(auditoria);
						}

						// PERS_CUENTABANCARIA_N
						if(cuentaBancaria.getId().getIntIdCuentaBancaria() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_PERS_CUENTABANCARIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getId().getIntIdCuentaBancaria());

							lista.add(auditoria);
						}

						// CUBA_BANCO_N_COD
						if(cuentaBancaria.getIntBancoCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_BANCO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getIntBancoCod());

							lista.add(auditoria);
						}

						// CUBA_TIPOCUENTA_N_COD
						if(cuentaBancaria.getIntTipoCuentaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_TIPOCUENTA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getIntTipoCuentaCod());

							lista.add(auditoria);
						}

						// CUBA_NROCUENTABANCARIA_V
						if(cuentaBancaria.getStrNroCuentaBancaria() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_NROCUENTABANCARIA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getStrNroCuentaBancaria());

							lista.add(auditoria);
						}

						// CUBA_MONEDA_N_COD
						if(cuentaBancaria.getIntMonedaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_MONEDA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getIntMonedaCod());

							lista.add(auditoria);
						}

						// CUBA_DEPOSITA_CTS_N
						if(cuentaBancaria.getIntDepositaCts() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_DEPOSITA_CTS_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getIntDepositaCts());

							lista.add(auditoria);
						}

						// CUBA_MARCAABONOS_N
						if(cuentaBancaria.getIntMarcaAbono() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_MARCAABONOS_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getIntMarcaAbono());

							lista.add(auditoria);
						}

						// CUBA_MARCACARGOS_N
						if(cuentaBancaria.getIntMarcaCargo() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_MARCACARGOS_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getIntMarcaCargo());

							lista.add(auditoria);
						}

						// CUBA_OBSERVACION_V
						if(cuentaBancaria.getStrObservacion() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_OBSERVACION_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getStrObservacion());

							lista.add(auditoria);
						}

						// CUBA_ESTADO_N_COD
						if(cuentaBancaria.getIntEstadoCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getIntEstadoCod());

							lista.add(auditoria);
						}

						// CUBA_CODINTERBANCO_N
						if(cuentaBancaria.getStrCodigoInterbancario() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdPersona());
							listaLlaves.add("" + cuentaBancaria.getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_CODINTERBANCO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + cuentaBancaria.getStrCodigoInterbancario());

							lista.add(auditoria);
						}
						
						/* Inicio Tabla PER_CUENTABANCARIAFIN */
						if (cuentaBancaria.getListaCuentaBancariaFin() != null && cuentaBancaria.getListaCuentaBancariaFin().size() > 0)
							for(CuentaBancariaFin cuentaBancariaFin : cuentaBancaria.getListaCuentaBancariaFin()) {
								// PERS_PERSONA_N
								if(cuentaBancariaFin.getId().getIntIdPersona() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdPersona());
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdCuentaBancaria());
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntParaTipoFinCuenta());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIAFIN);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABANFIN_PERS_PERSONA_N);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaBancariaFin.getId().getIntIdPersona());

									lista.add(auditoria);
								}

								// PERS_CUENTABANCARIA_N
								if(cuentaBancariaFin.getId().getIntIdCuentaBancaria() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdPersona());
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdCuentaBancaria());
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntParaTipoFinCuenta());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIAFIN);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABANFIN_PERS_CUENTABANCARIA_N);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaBancariaFin.getId().getIntIdCuentaBancaria());

									lista.add(auditoria);
								}

								// PARA_TIPOFINCUENTA_N_COD
								if(cuentaBancariaFin.getId().getIntParaTipoFinCuenta() != null) {
									List<String> listaLlaves = new ArrayList<String>();
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdPersona());
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdCuentaBancaria());
									listaLlaves.add("" + cuentaBancariaFin.getId().getIntParaTipoFinCuenta());
									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIAFIN);
									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABANFIN_PARA_TIPOFINCUENTA_N_COD);
									auditoria.setStrValoranterior(null);
									auditoria.setStrValornuevo("" + cuentaBancariaFin.getId().getIntParaTipoFinCuenta());

									lista.add(auditoria);
								}
							}
					}
			}
			// Modificación de Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_UPDATE)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_UPDATE");
				// PERS_PERSONA_N
				if(!personaOld.getIntIdPersona().equals(persona.getIntIdPersona())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_PERSONA_N);
					auditoria.setStrValoranterior(personaOld.getIntIdPersona() != null ? "" + personaOld.getIntIdPersona() : null);
					auditoria.setStrValornuevo(persona.getIntIdPersona() != null ? "" + persona.getIntIdPersona() : null);
					
					lista.add(auditoria);
				}
				
				// TIPOPERSONA_N_COD
				if(!personaOld.getIntTipoPersonaCod().equals(persona.getIntTipoPersonaCod())){
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_TIPOPERSONA_N_COD);
					auditoria.setStrValoranterior(personaOld.getIntTipoPersonaCod() != null ? "" + personaOld.getIntTipoPersonaCod() : null);
					auditoria.setStrValornuevo(persona.getIntTipoPersonaCod() != null ? "" + persona.getIntTipoPersonaCod() : null);

					lista.add(auditoria);
				}
				
				// PERS_RUC_V
//				if(!personaOld.getStrRuc().equals(persona.getStrRuc())) {
//					List<String> listaLlaves = new ArrayList<String>();
//					listaLlaves.add("" + persona.getIntIdPersona());
//					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
//					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_RUC_V);
//					auditoria.setStrValoranterior(personaOld.getStrRuc() != null ? "" + personaOld.getStrRuc() : null);
//					auditoria.setStrValornuevo(persona.getStrRuc() != null ? "" + persona.getStrRuc() : null);
//
//					lista.add(auditoria);
//				}
				
				// PERS_ESTADOPERSONA_N_COD
				if(!personaOld.getIntEstadoCod().equals(persona.getIntEstadoCod())){
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_ESTADOPERSONA_N_COD);
					auditoria.setStrValoranterior(personaOld.getIntEstadoCod() != null ? "" + personaOld.getIntEstadoCod() : null);
					auditoria.setStrValornuevo(persona.getIntEstadoCod() != null ? "" + persona.getIntEstadoCod() : null);
					
					lista.add(auditoria);
				}
				
				// PERS_FECHABAJARUC_D
//				if(!personaOld.getStrFechaBajaRuc().equals(persona.getStrFechaBajaRuc())) {
//					List<String> listaLlaves = new ArrayList<String>();
//					listaLlaves.add("" + persona.getIntIdPersona());
//					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_PERSONA);
//					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_PERSONA_PERS_FECHABAJARUC_D);
//					auditoria.setStrValoranterior(personaOld.getStrFechaBajaRuc() != null ? "" + personaOld.getStrFechaBajaRuc() : null);
//					auditoria.setStrValornuevo(persona.getStrFechaBajaRuc() != null ? "" + persona.getStrFechaBajaRuc() : null);
//
//					lista.add(auditoria);
//				}
				
				// PER_LABORAL //--> LABO_CENTROTRABAJO_V///
				if(!personaOld.getNatural().getPerLaboral().getStrCentroTrabajo().equals(persona.getNatural().getPerLaboral().getStrCentroTrabajo())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntIdPersona());
					listaLlaves.add("" + persona.getNatural().getPerLaboral().getId().getIntItemLaboral());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_CENTROTRABAJO_V);
					auditoria.setStrValoranterior(personaOld.getNatural().getPerLaboral().getStrCentroTrabajo() != null ? "" + personaOld.getNatural().getPerLaboral().getStrCentroTrabajo() : null);
					auditoria.setStrValornuevo(persona.getNatural().getPerLaboral().getStrCentroTrabajo() != null ? "" + persona.getNatural().getPerLaboral().getStrCentroTrabajo() : null);

					lista.add(auditoria);
				}
				// PER_NATURAL //--> PARA_CARGO_N_COD
				if(!personaOld.getNatural().getPerLaboral().getIntCargo().equals(persona.getNatural().getPerLaboral().getIntCargo())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_CARGO_N_COD);
					auditoria.setStrValoranterior(personaOld.getNatural().getPerLaboral().getIntCargo() != null ? "" + personaOld.getNatural().getPerLaboral().getIntCargo() : null);
					auditoria.setStrValornuevo(persona.getNatural().getPerLaboral().getIntCargo() != null ? "" + persona.getNatural().getPerLaboral().getIntCargo() : null);

					lista.add(auditoria);
				}
				// PER_NATURAL //--> LABO_INICIOSERVICIO_D
				if(!personaOld.getNatural().getPerLaboral().getDtInicioServicio().equals(persona.getNatural().getPerLaboral().getDtInicioServicio())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_INICIOSERVICIO_D);
					auditoria.setStrValoranterior(personaOld.getNatural().getPerLaboral().getDtInicioServicio() != null ? "" + personaOld.getNatural().getPerLaboral().getDtInicioServicio() : null);
					auditoria.setStrValornuevo(persona.getNatural().getPerLaboral().getDtInicioServicio() != null ? "" + persona.getNatural().getPerLaboral().getDtInicioServicio() : null);

					lista.add(auditoria);
				}
				// PER_NATURAL //--> LABO_REMUNERACION_N
				if(!personaOld.getNatural().getPerLaboral().getBdRemuneracion().equals(persona.getNatural().getPerLaboral().getBdRemuneracion())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_REMUNERACION_N);
					auditoria.setStrValoranterior(personaOld.getNatural().getPerLaboral().getBdRemuneracion() != null ? "" + personaOld.getNatural().getPerLaboral().getBdRemuneracion() : null);
					auditoria.setStrValornuevo(persona.getNatural().getPerLaboral().getBdRemuneracion() != null ? "" + persona.getNatural().getPerLaboral().getBdRemuneracion() : null);

					lista.add(auditoria);
				}
				// PER_NATURAL //--> LABO_REMUNERACION_N
				if(!personaOld.getNatural().getPerLaboral().getIntRegimenLaboral().equals(persona.getNatural().getPerLaboral().getIntRegimenLaboral())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_REGIMENLABORAL_N_COD);
					auditoria.setStrValoranterior(personaOld.getNatural().getPerLaboral().getIntRegimenLaboral() != null ? "" + personaOld.getNatural().getPerLaboral().getIntRegimenLaboral() : null);
					auditoria.setStrValornuevo(persona.getNatural().getPerLaboral().getIntRegimenLaboral() != null ? "" + persona.getNatural().getPerLaboral().getIntRegimenLaboral() : null);

					lista.add(auditoria);
				}
				// PER_NATURAL //--> LABO_SOLICITUDONP_V
				if(!personaOld.getNatural().getPerLaboral().getStrSolicitudONP().equals(persona.getNatural().getPerLaboral().getStrSolicitudONP())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_LABO_SOLICITUDONP_V);
					auditoria.setStrValoranterior(personaOld.getNatural().getPerLaboral().getStrSolicitudONP() != null ? "" + personaOld.getNatural().getPerLaboral().getStrSolicitudONP() : null);
					auditoria.setStrValornuevo(persona.getNatural().getPerLaboral().getStrSolicitudONP() != null ? "" + persona.getNatural().getPerLaboral().getStrSolicitudONP() : null);

					lista.add(auditoria);
				}
				
				// PER_NATURAL //--> PARA_CONDICIONLABORAL_N_COD
				if(!personaOld.getNatural().getPerLaboral().getIntCondicionLaboral().equals(persona.getNatural().getPerLaboral().getIntCondicionLaboral())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_CONDICIONLABORAL_N_COD);
					auditoria.setStrValoranterior(personaOld.getNatural().getPerLaboral().getIntCondicionLaboral() != null ? "" + personaOld.getNatural().getPerLaboral().getIntCondicionLaboral() : null);
					auditoria.setStrValornuevo(persona.getNatural().getPerLaboral().getIntCondicionLaboral() != null ? "" + persona.getNatural().getPerLaboral().getIntCondicionLaboral() : null);

					lista.add(auditoria);
				}
				// PER_NATURAL //--> PARA_CONDICIONLABORAL_N_COD
				if(!personaOld.getNatural().getPerLaboral().getIntCondicionLaboralDet().equals(persona.getNatural().getPerLaboral().getIntCondicionLaboralDet())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_LABORAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_LABORAL_PARA_CONDICIONLABORALDET_N_COD);
					auditoria.setStrValoranterior(personaOld.getNatural().getPerLaboral().getIntCondicionLaboralDet() != null ? "" + personaOld.getNatural().getPerLaboral().getIntCondicionLaboralDet() : null);
					auditoria.setStrValornuevo(persona.getNatural().getPerLaboral().getIntCondicionLaboralDet() != null ? "" + persona.getNatural().getPerLaboral().getIntCondicionLaboralDet() : null);

					lista.add(auditoria);
				}
				// PER_NATURAL //--> NATU_ESTADOCIVIL_N_COD
				if(!personaOld.getNatural().getIntEstadoCivilCod().equals(persona.getNatural().getIntEstadoCivilCod())) {
					List<String> listaLlaves = new ArrayList<String>();
					listaLlaves.add("" + persona.getIntIdPersona());
					auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_NATURAL);
					auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_NATURAL_NATU_ESTADOCIVIL_N_COD);
					auditoria.setStrValoranterior(personaOld.getNatural().getIntEstadoCivilCod() != null ? "" + personaOld.getNatural().getIntEstadoCivilCod() : null);
					auditoria.setStrValornuevo(persona.getNatural().getIntEstadoCivilCod() != null ? "" + persona.getNatural().getIntEstadoCivilCod() : null);

					lista.add(auditoria);
				}
			}
		} catch (Exception e) {
			log.error("Error en generarAuditoriaPersona --> " + e);
		}
		log.info("Fin");
		return lista;
	}

	/**
	 * 
	 * @param autor rVillarreal
	 * @param fecha 23/06/2014
	 * @param metodo que inserta los documentos
	 * @return
	 */
	
	public List<Auditoria> generarAuditoriaDocumento(Integer intTipoCambio, Persona persona) throws BusinessException{

		log.info("Inicio");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			// Inserción de Nuevos Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
						
				/* Inicio Tabla PER_DOCUMENTO */
				if (persona.getListaDocumento() != null && persona.getListaDocumento().size() > 0)
					for (int i = 0; i < persona.getListaDocumento().size(); i++) {
						
						if(i>=personaOld.getListaDocumento().size()){
							// PERS_PERSONA_N
							if(persona.getListaDocumento().get(i).getId().getIntIdPersona()!=null) {
								List<String> listaLlaves = new ArrayList<String>();
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntIdPersona());
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntItemDocumento());
								auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
								auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_PERS_PERSONA_N);
								auditoria.setStrValoranterior(null);
								auditoria.setStrValornuevo("" + persona.getListaDocumento().get(i).getId().getIntIdPersona());
								
								lista.add(auditoria);
							}
							//PERS_ITEMDOCUMENTO_N
							if(persona.getListaDocumento().get(i).getId().getIntItemDocumento()!=null) {
								List<String> listaLlaves = new ArrayList<String>();
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntIdPersona());
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntItemDocumento());
								auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
								auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_PERS_ITEMDOCUMENTO_N);
								auditoria.setStrValoranterior(null);
								auditoria.setStrValornuevo("" + persona.getListaDocumento().get(i).getId().getIntItemDocumento());
								
								lista.add(auditoria);
							}
							// PARA_TIPOIDENTIDAD_N_COD
							if(persona.getListaDocumento().get(i).getIntTipoIdentidadCod()!=null) {
								List<String> listaLlaves = new ArrayList<String>();
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntIdPersona());
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntItemDocumento());
								auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
								auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_PARA_TIPOIDENTIDAD_N_COD);
								auditoria.setStrValoranterior(null);
								auditoria.setStrValornuevo("" + persona.getListaDocumento().get(i).getIntTipoIdentidadCod());

								lista.add(auditoria);
							}
							// DOCU_NUMEROIDENTIDAD_V
							if(persona.getListaDocumento().get(i).getStrNumeroIdentidad() != null) {
								List<String> listaLlaves = new ArrayList<String>();
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntIdPersona());
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntItemDocumento());
								auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
								auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_DOCU_NUMEROIDENTIDAD_V);
								auditoria.setStrValoranterior(null);
								auditoria.setStrValornuevo("" + persona.getListaDocumento().get(i).getStrNumeroIdentidad());
								lista.add(auditoria);
							}
							// PARA_ESTADO_N_COD
							if(persona.getListaDocumento().get(i).getIntEstadoCod() != null) {
								List<String> listaLlaves = new ArrayList<String>();
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntIdPersona());
								listaLlaves.add("" + persona.getListaDocumento().get(i).getId().getIntItemDocumento());
								auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO);
								auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOCUMENTO_PARA_ESTADO_N_COD);
								auditoria.setStrValoranterior(null);
								auditoria.setStrValornuevo("" + persona.getListaDocumento().get(i).getIntEstadoCod());

								lista.add(auditoria);
							}
							
						}
					}
			}
		} catch (Exception e) {
			log.error("Error en generarAuditoriaPersona --> " + e);
		}
		log.info("Fin");
		return lista;
	}
	
	//inicio
	public List<Auditoria> generarAuditoriaDomicilio(Integer intTipoCambio, Persona persona) throws BusinessException{
		log.info("Inicio");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			// Inserción de Nuevos Registros
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				/* Inicio Tabla PER_DOMICILIO */
				if (persona.getListaDomicilio() != null && persona.getListaDomicilio().size() > 0)
						
					for (int i = 0; i < persona.getListaDomicilio().size(); i++) {
						
						if(i>=personaOld.getListaDomicilio().size()){
					
						// PERS_PERSONA_N
						if(persona.getListaDomicilio().get(i).getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());

							lista.add(auditoria);
						}

						// PERS_DOMICILIO_N
						if(persona.getListaDomicilio().get(i).getId().getIntIdDomicilio() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PERS_DOMICILIO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());

							lista.add(auditoria);
						}

						// DOMI_TIPODOMICILIO_N_COD
						if(persona.getListaDomicilio().get(i).getIntTipoDomicilioCod() != null){
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPODOMICILIO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntTipoDomicilioCod());

							lista.add(auditoria);
						}

						// DOMI_TIPODIRECCION_N_COD
						if(persona.getListaDomicilio().get(i).getIntTipoDireccionCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPODIRECCION_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntTipoDireccionCod());

							lista.add(auditoria);
						}

						// DOMI_TIPOVIVIENDA_N_COD
						if(persona.getListaDomicilio().get(i).getIntTipoViviendaCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOVIVIENDA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntTipoViviendaCod());

							lista.add(auditoria);
						}

						// PARA_UBIGEO_N_PK
						if(persona.getListaDomicilio().get(i).getIntParaUbigeoPkDistrito() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_UBIGEO_N_PK);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntParaUbigeoPkDistrito());

							lista.add(auditoria);
						}

						// DOMI_TIPOVIA_N_COD
						if(persona.getListaDomicilio().get(i).getIntTipoViaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOVIA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntTipoViaCod());

							lista.add(auditoria);
						}

						// DOMI_NOMBREVIA_V
						if(persona.getListaDomicilio().get(i).getStrNombreVia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NOMBREVIA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getStrNombreVia());

							lista.add(auditoria);
						}

						// DOMI_NUMEROVIA_N
						if(persona.getListaDomicilio().get(i).getIntNumeroVia() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NUMEROVIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntNumeroVia());

							lista.add(auditoria);
						}

						// DOMI_INTERIOR_V
						if(persona.getListaDomicilio().get(i).getStrInterior() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_INTERIOR_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getStrInterior());

							lista.add(auditoria);
						}

						// DOMI_TIPOZONA_N_COD
						if(persona.getListaDomicilio().get(i).getIntTipoZonaCod() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_TIPOZONA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntTipoZonaCod());

							lista.add(auditoria);
						}

						// DOMI_REFERENCIA_V
						if(persona.getListaDomicilio().get(i).getStrReferencia() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_REFERENCIA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getStrReferencia());

							lista.add(auditoria);
						}

						// DOMI_ENVIACORRESPONDENCIA_N
						if(persona.getListaDomicilio().get(i).getIntEnviaCorrespondencia() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_ENVIACORRESPONDENCIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntEnviaCorrespondencia());

							lista.add(auditoria);
						}

						// DOMI_OBSERVACION_V
						if(persona.getListaDomicilio().get(i).getStrObservacion() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_OBSERVACION_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getStrObservacion());

							lista.add(auditoria);
						}

						// DOMI_ESTADO_N_COD
						if(persona.getListaDomicilio().get(i).getIntEstadoCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntEstadoCod());

							lista.add(auditoria);
						}

						// DOMI_NOMBREZONA_V
						if(persona.getListaDomicilio().get(i).getStrNombreZona() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_NOMBREZONA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getStrNombreZona());

							lista.add(auditoria);
						}

						// DOMI_FECHAELIMINACION_D
						if(persona.getListaDomicilio().get(i).getTsFechaEliminacion() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_DOMI_FECHAELIMINACION_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getTsFechaEliminacion());

							lista.add(auditoria);
						}

						// PARA_TIPOARCHIVO_N_COD
						if(persona.getListaDomicilio().get(i).getIntParaTipoArchivo() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_TIPOARCHIVO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntParaTipoArchivo());

							lista.add(auditoria);
						}

						// PARA_ITEMARCHIVO_N
						if(persona.getListaDomicilio().get(i).getIntParaItemArchivo() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_ITEMARCHIVO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntParaItemArchivo());

							lista.add(auditoria);
						}

						// PARA_ITEMHISTORICO_N
						if(persona.getListaDomicilio().get(i).getIntParaItemHistorico() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaDomicilio().get(i).getId().getIntIdDomicilio());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_DOMICILIO);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_DOMICILIO_PARA_ITEMHISTORICO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaDomicilio().get(i).getIntParaItemHistorico());

							lista.add(auditoria);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en generarAuditoriaPersona --> " + e);
		}
		log.info("Fin");
		return lista;
	}
	
	//cuenta bancaria
	
	public List<Auditoria> generarAuditoriaCuentaBancaria(Integer intTipoCambio, Persona persona) throws BusinessException{
		log.info("Inicio");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
						
		if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
								/* Inicio Tabla PER_CUENTABANCARIA */
			if (persona.getListaCuentaBancaria() != null && persona.getListaCuentaBancaria().size() > 0)
				for (int i = 0; i < persona.getListaCuentaBancaria().size(); i++) {
								
					if(i>=personaOld.getListaCuentaBancaria().size()){
						// PERS_PERSONA_N
						if(persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona() != null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());

							lista.add(auditoria);
						}

						// PERS_CUENTABANCARIA_N
						if(persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_PERS_CUENTABANCARIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());

							lista.add(auditoria);
						}

						// CUBA_BANCO_N_COD
						if(persona.getListaCuentaBancaria().get(i).getIntBancoCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_BANCO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getIntBancoCod());

							lista.add(auditoria);
						}

						// CUBA_TIPOCUENTA_N_COD
						if(persona.getListaCuentaBancaria().get(i).getIntTipoCuentaCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_TIPOCUENTA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getIntTipoCuentaCod());

							lista.add(auditoria);
						}

						// CUBA_NROCUENTABANCARIA_V
						if(persona.getListaCuentaBancaria().get(i).getStrNroCuentaBancaria() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_NROCUENTABANCARIA_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getStrNroCuentaBancaria());

							lista.add(auditoria);
						}

						// CUBA_MONEDA_N_COD
						if(persona.getListaCuentaBancaria().get(i).getIntMonedaCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_MONEDA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getIntMonedaCod());

							lista.add(auditoria);
						}

						// CUBA_DEPOSITA_CTS_N
						if(persona.getListaCuentaBancaria().get(i).getIntDepositaCts() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_DEPOSITA_CTS_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getIntDepositaCts());

							lista.add(auditoria);
						}

						// CUBA_MARCAABONOS_N
						if(persona.getListaCuentaBancaria().get(i).getIntMarcaAbono() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_MARCAABONOS_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getIntMarcaAbono());

							lista.add(auditoria);
						}

						// CUBA_MARCACARGOS_N
						if(persona.getListaCuentaBancaria().get(i).getIntMarcaCargo() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_MARCACARGOS_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getIntMarcaCargo());

							lista.add(auditoria);
						}

						// CUBA_OBSERVACION_V
						if(persona.getListaCuentaBancaria().get(i).getStrObservacion()!=null){
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_OBSERVACION_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getStrObservacion());

							lista.add(auditoria);
						}else{
							System.out.println("Tiene Datos null " + persona.getListaCuentaBancaria().get(i).getStrObservacion());
						}

						// CUBA_ESTADO_N_COD
						if(persona.getListaCuentaBancaria().get(i).getIntEstadoCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getIntEstadoCod());

							lista.add(auditoria);
						}

						// CUBA_CODINTERBANCO_N
						if(persona.getListaCuentaBancaria().get(i).getStrCodigoInterbancario()!=null){
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaCuentaBancaria().get(i).getId().getIntIdCuentaBancaria());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIA);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABAN_CUBA_CODINTERBANCO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaCuentaBancaria().get(i).getStrCodigoInterbancario());

							lista.add(auditoria);
					}else{
						System.out.println("tine null: " +persona.getListaCuentaBancaria().get(i).getStrCodigoInterbancario());
					}
						
						/* Inicio Tabla PER_CUENTABANCARIAFIN */
						
//						if (cuentaBancaria.getListaCuentaBancariaFin() != null && cuentaBancaria.getListaCuentaBancariaFin().size() > 0)
//							for(CuentaBancariaFin cuentaBancariaFin : cuentaBancaria.getListaCuentaBancariaFin()) {
//								// PERS_PERSONA_N
//								if(cuentaBancariaFin.getId().getIntIdPersona() != null) {
//									List<String> listaLlaves = new ArrayList<String>();
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdPersona());
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdCuentaBancaria());
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntParaTipoFinCuenta());
//									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIAFIN);
//									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABANFIN_PERS_PERSONA_N);
//									auditoria.setStrValoranterior(null);
//									auditoria.setStrValornuevo("" + cuentaBancariaFin.getId().getIntIdPersona());
//
//									lista.add(auditoria);
//								}

								// PERS_CUENTABANCARIA_N
//								if(cuentaBancariaFin.getId().getIntIdCuentaBancaria() != null) {
//									List<String> listaLlaves = new ArrayList<String>();
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdPersona());
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdCuentaBancaria());
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntParaTipoFinCuenta());
//									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIAFIN);
//									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABANFIN_PERS_CUENTABANCARIA_N);
//									auditoria.setStrValoranterior(null);
//									auditoria.setStrValornuevo("" + cuentaBancariaFin.getId().getIntIdCuentaBancaria());
//
//									lista.add(auditoria);
//								}

								// PARA_TIPOFINCUENTA_N_COD
//								if(cuentaBancariaFin.getId().getIntParaTipoFinCuenta() != null) {
//									List<String> listaLlaves = new ArrayList<String>();
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdPersona());
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntIdCuentaBancaria());
//									listaLlaves.add("" + cuentaBancariaFin.getId().getIntParaTipoFinCuenta());
//									auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_CUENTABANCARIAFIN);
//									auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_CUENTABANFIN_PARA_TIPOFINCUENTA_N_COD);
//									auditoria.setStrValoranterior(null);
//									auditoria.setStrValornuevo("" + cuentaBancariaFin.getId().getIntParaTipoFinCuenta());
//
//									lista.add(auditoria);
//								}
								
//							}
						}
					}
				}
			} catch (Exception e) {
					log.error("Error en generarAuditoriaCuentaBancaria --> " + e);
				}
				log.info("Fin");
				return lista;
			}
	//Comunicaciones
	
public List<Auditoria> generarAuditoriaComunicaciones(Integer intTipoCambio, Persona persona) throws BusinessException{
		log.info("Inicio");
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
	try {
			// Inserción de Nuevos Registros
		if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPO_INSERT)) {
				log.info("Tipo: PARAM_T_AUDITORIA_TIPO_INSERT");
				
				/* Inicio Tabla PER_COMUNICACION */
			if (persona.getListaComunicacion() != null && persona.getListaComunicacion().size() > 0)

				for (int i = 0; i < persona.getListaComunicacion().size(); i++) {
						
					if(i>=personaOld.getListaComunicacion().size()){
						// PERS_PERSONA_N
						if(persona.getListaComunicacion().get(i).getId().getIntIdPersona() !=null){
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_PERS_PERSONA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());

							lista.add(auditoria);
						}

						// PERS_COMUNICACION_N
						if(persona.getListaComunicacion().get(i).getId().getIntIdComunicacion() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_PERS_COMUNICACION_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());

							lista.add(auditoria);
						}

						// COMU_TIPOCOMUNICACION_N_COD
						if(persona.getListaComunicacion().get(i).getIntTipoComunicacionCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_TIPOCOMUNICACION_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getIntTipoComunicacionCod());

							lista.add(auditoria);
						}

						// COMU_SUBTIPOCOMUNICACION_N_COD
						if(persona.getListaComunicacion().get(i).getIntSubTipoComunicacionCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_SUBTIPOCOMUNICAC_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getIntSubTipoComunicacionCod());

							lista.add(auditoria);
						}

						// COMU_DATO_V
						if(persona.getListaComunicacion().get(i).getStrDato() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_DATO_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getStrDato());

							lista.add(auditoria);
						}

						// COMU_TIPOLINEA_N_COD
						if(persona.getListaComunicacion().get(i).getIntTipoLineaCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_TIPOLINEA_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getIntTipoLineaCod());

							lista.add(auditoria);
						}

						// COMU_NUMERO_N
						if(persona.getListaComunicacion().get(i).getIntNumero() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_NUMERO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getIntNumero());

							lista.add(auditoria);
						}

						// COMU_ANEXO_N
						if(persona.getListaComunicacion().get(i).getIntAnexo() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_ANEXO_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getIntAnexo());

							lista.add(auditoria);
						}

						// COMU_DESCRIPCION_V
						if(persona.getListaComunicacion().get(i).getStrDescripcion() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_DESCRIPCION_V);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getStrDescripcion());

							lista.add(auditoria);
						}

						// COMU_CASOEMERGENCIA_N
						if(persona.getListaComunicacion().get(i).getIntCasoEmergencia() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_CASOEMERGENCIA_N);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getIntCasoEmergencia());

							lista.add(auditoria);
						}

						// COMU_ESTADO_N_COD
						if(persona.getListaComunicacion().get(i).getIntEstadoCod() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_ESTADO_N_COD);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getIntEstadoCod());

							lista.add(auditoria);
						}

						// COMU_FECHAELIMINACION_D
						if(persona.getListaComunicacion().get(i).getTsFechaEliminacion() !=null) {
							List<String> listaLlaves = new ArrayList<String>();
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdPersona());
							listaLlaves.add("" + persona.getListaComunicacion().get(i).getId().getIntIdComunicacion());
							auditoria = beanAuditoria(intTipoCambio, listaLlaves, Constante.PARAM_T_AUDITORIA_PER_COMUNICACION);
							auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PER_COMUNICAC_COMU_FECHAELIMINACION_D);
							auditoria.setStrValoranterior(null);
							auditoria.setStrValornuevo("" + persona.getListaComunicacion().get(i).getTsFechaEliminacion());

							lista.add(auditoria);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en generarAuditoriaPersona --> " + e);
		}
		log.info("Fin");
		return lista;
	}
	
	/* Método que carga los datos comunes de la estructura Auditoria */
	public Auditoria beanAuditoria(Integer intTipoCambio, List<String> llaves, String tabla) {
		log.info("Inicio");
		Auditoria auditoria = new Auditoria();
		try {
			auditoria = auditoriaFacade.beanAuditoria(SESION_IDEMPRESA, SESION_IDUSUARIO, intTipoCambio, llaves, tabla);
		} catch (Exception e) {
			log.error("Error al grabar en Auditoria: " + e);
		}
		log.info("Fin");
		return auditoria;
	}
	
	/* Método que graba en la tabla Auditoria */
	public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException {
		log.info("Inicio");
		try {
			auditoriaFacade.grabarAuditoria(auditoria);
		} catch (Exception e) {
			log.error("Error al grabar en Auditoria: " + e);
		}
		log.info("Fin");
		return auditoria;
	}
    /* Fin - Auditoria - GTorresBrousset 24.abr.2014 */
    
	//Metodos Utilitarios
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	protected HttpServletResponse getResponse() {
		return (HttpServletResponse) FacesContext.getCurrentInstance()
				.getExternalContext().getResponse();
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
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

	//Getters & Setters
	public SocioComp getSocioBusqueda() {
		return socioBusqueda;
	}
	public void setSocioBusqueda(SocioComp socioBusqueda) {
		this.socioBusqueda = socioBusqueda;
	}
	public List<SocioComp> getListaSocioBusqueda() {
		return listaSocioBusqueda;
	}
	public void setListaSocioBusqueda(List<SocioComp> listaSocioBusqueda) {
		this.listaSocioBusqueda = listaSocioBusqueda;
	}
	public List<Sucursal> getListJuridicaSucursal() {
		log.info("-------------------------------------Debugging SocioController.getListJuridicaSucursal-------------------------------------");
		try {
			if(listJuridicaSucursal == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("listJuridicaSucursal.size: "+listJuridicaSucursal.size());
		return this.listJuridicaSucursal;
	}
	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}
	public List<Subsucursal> getListJuridicaSubsucursal() {
		return listJuridicaSubsucursal;
	}
	public void setListJuridicaSubsucursal(List<Subsucursal> listJuridicaSubsucursal) {
		this.listJuridicaSubsucursal = listJuridicaSubsucursal;
	}
	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}
	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}
	public List<Tabla> getListTipoRelacion() {
		log.info("-------------------------------------Debugging SocioController.getListJuridicaSucursal-------------------------------------");
		try {
			if(listTipoRelacion == null){
				TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				this.listTipoRelacion = facade.getListaTablaPorAgrupamientoA(61, "D");
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("listTipoRelacion.size: "+listTipoRelacion.size());
		return listTipoRelacion;
	}
	public void setListTipoRelacion(List<Tabla> listTipoRelacion) {
		this.listTipoRelacion = listTipoRelacion;
	}
	public Persona getPersonaBusqueda() {
		return personaBusqueda;
	}
	public void setPersonaBusqueda(Persona personaBusqueda) {
		this.personaBusqueda = personaBusqueda;
	}
	public SocioEstructura getSocioEstructura() {
		return socioEstructura;
	}
	public void setSocioEstructura(SocioEstructura socioEstructura) {
		this.socioEstructura = socioEstructura;
	}
	public List<Tabla> getListDetCondicionLaboral() {
		return listDetCondicionLaboral;
	}
	public void setListDetCondicionLaboral(List<Tabla> listDetCondicionLaboral) {
		this.listDetCondicionLaboral = listDetCondicionLaboral;
	}
	public SocioComp getSocioNatuSelected() {
		return socioNatuSelected;
	}
	public void setSocioNatuSelected(SocioComp socioNatuSelected) {
		this.socioNatuSelected = socioNatuSelected;
	}
	public Boolean getBlnShowDivFormSocioNatu() {
		return blnShowDivFormSocioNatu;
	}
	public void setBlnShowDivFormSocioNatu(Boolean blnShowDivFormSocioNatu) {
		this.blnShowDivFormSocioNatu = blnShowDivFormSocioNatu;
	}
	public Boolean getBlnShowDivFormAperturaCuenta() {
		return blnShowDivFormAperturaCuenta;
	}
	public void setBlnShowDivFormAperturaCuenta(Boolean blnShowDivFormAperturaCuenta) {
		this.blnShowDivFormAperturaCuenta = blnShowDivFormAperturaCuenta;
	}
	public Boolean getBlnShowDivFormSocio() {
		return blnShowDivFormSocio;
	}
	public void setBlnShowDivFormSocio(Boolean blnShowDivFormSocio) {
		this.blnShowDivFormSocio = blnShowDivFormSocio;
	}
	public Boolean getBlnShowDivFormSocioJuridica() {
		return blnShowDivFormSocioJuridica;
	}
	public void setBlnShowDivFormSocioJuridica(Boolean blnShowDivFormSocioJuridica) {
		this.blnShowDivFormSocioJuridica = blnShowDivFormSocioJuridica;
	}
	public Boolean getBlnShowDivPanelControlsSocio() {
		return blnShowDivPanelControlsSocio;
	}
	public void setBlnShowDivPanelControlsSocio(Boolean blnShowDivPanelControlsSocio) {
		this.blnShowDivPanelControlsSocio = blnShowDivPanelControlsSocio;
	}
	public Boolean getBlnShowFiltroPersonaNatu() {
		return blnShowFiltroPersonaNatu;
	}
	public void setBlnShowFiltroPersonaNatu(Boolean blnShowFiltroPersonaNatu) {
		this.blnShowFiltroPersonaNatu = blnShowFiltroPersonaNatu;
	}
	public List<Tabla> getListDocumentoBusq() {
		return listDocumentoBusq;
	}
	public void setListDocumentoBusq(List<Tabla> listDocumentoBusq) {
		this.listDocumentoBusq = listDocumentoBusq;
	}
	public List<Tabla> getListDocumento() {
		log.info("-------------------------------------Debugging SocioController.getListDocumento-------------------------------------");
		log.info("personaBusqueda.intTipoPersonaCod: "+getPersonaBusqueda().getIntTipoPersonaCod());
		TablaFacadeRemote facade = null;
		Integer intIdTipoPersona = null;
		List<Tabla> listaDocumento = null;
		try {
			intIdTipoPersona = (getPersonaBusqueda().getIntTipoPersonaCod()==null?socioNatuSelected.getPersona().getIntTipoPersonaCod():getPersonaBusqueda().getIntTipoPersonaCod());
			if(intIdTipoPersona!=null && intIdTipoPersona!=0){
				facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				if(intIdTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
					listaDocumento = facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO), Constante.VISTA_TIPOPERSONA_JURIDICA);
				}else{
					listaDocumento = facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO), Constante.VISTA_TIPOPERSONA_NATURAL);
				}
				log.info("listaDocumento.size: "+listaDocumento.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		this.listDocumento = listaDocumento;
		return this.listDocumento;
	}
	

	public void setListDocumento(List<Tabla> listDocumento) {
		this.listDocumento = listDocumento;
	}
	public Integer getIntDocIdentidadMaxLength() {
		return intDocIdentidadMaxLength;
	}
	public void setIntDocIdentidadMaxLength(Integer intDocIdentidadMaxLength) {
		this.intDocIdentidadMaxLength = intDocIdentidadMaxLength;
	}
	public Boolean getBlnFechaContratoDisabled() {
		return blnFechaContratoDisabled;
	}
	public void setBlnFechaContratoDisabled(Boolean blnFechaContratoDisabled) {
		this.blnFechaContratoDisabled = blnFechaContratoDisabled;
	}
	public List<Ubigeo> getListaUbigeoDepartamento() throws EJBFactoryException, BusinessException {
		GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	    listaUbigeoDepartamento = facade.getListaUbigeoDeDepartamento();
		return listaUbigeoDepartamento;
	}
	public void setListaUbigeoDepartamento(List<Ubigeo> listaUbigeoDepartamento) {
		this.listaUbigeoDepartamento = listaUbigeoDepartamento;
	}
	public List<Ubigeo> getListaUbigeoProvincia() {
		return listaUbigeoProvincia;
	}
	public void setListaUbigeoProvincia(List<Ubigeo> listaUbigeoProvincia) {
		this.listaUbigeoProvincia = listaUbigeoProvincia;
	}
	public List<Ubigeo> getListaUbigeoDistrito() {
		return listaUbigeoDistrito;
	}
	public void setListaUbigeoDistrito(List<Ubigeo> listaUbigeoDistrito) {
		this.listaUbigeoDistrito = listaUbigeoDistrito;
	}
	public MyFile getFileFotoSocio() {
		return fileFotoSocio;
	}
	public void setFileFotoSocio(MyFile fileFotoSocio) {
		this.fileFotoSocio = fileFotoSocio;
	}
	public MyFile getFileFirmaSocio() {
		return fileFirmaSocio;
	}
	public void setFileFirmaSocio(MyFile fileFirmaSocio) {
		this.fileFirmaSocio = fileFirmaSocio;
	}
	public Boolean getBlnIsDatoPadron() {
		return blnIsDatoPadron;
	}
	public void setBlnIsDatoPadron(Boolean blnIsDatoPadron) {
		this.blnIsDatoPadron = blnIsDatoPadron;
	}
	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}
	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}
	public Boolean getIsValidSocioNatural() {
		return isValidSocioNatural;
	}
	public void setIsValidSocioNatural(Boolean isValidSocioNatural) {
		this.isValidSocioNatural = isValidSocioNatural;
	}
	public Boolean getIsContratado() {
		return isContratado;
	}
	public void setIsContratado(Boolean isContratado) {
		this.isContratado = isContratado;
	}
	public List<PersonaRol> getListRoles() {
		return listRoles;
	}
	public void setListRoles(List<PersonaRol> listRoles) {
		this.listRoles = listRoles;
	}
	public List<PersonaRol> getArrayRoles() {
		return arrayRoles;
	}
	public void setArrayRoles(List<PersonaRol> arrayRoles) {
		this.arrayRoles = arrayRoles;
	}
	public Boolean getIsValidSocioEstructura() {
		return isValidSocioEstructura;
	}
	public void setIsValidSocioEstructura(Boolean isValidSocioEstructura) {
		this.isValidSocioEstructura = isValidSocioEstructura;
	}
	public Archivo getFileContrato() {
		return fileContrato;
	}
	public void setFileContrato(Archivo fileContrato) {
		this.fileContrato = fileContrato;
	}
	public Boolean getIsDatosView() {
		return isDatosView;
	}
	public void setIsDatosView(Boolean isDatosView) {
		this.isDatosView = isDatosView;
	}
	public Boolean getBlnSocioCliente() {
		return blnSocioCliente;
	}
	public void setBlnSocioCliente(Boolean blnSocioCliente) {
		this.blnSocioCliente = blnSocioCliente;
	}
	public Boolean getBlnModificacion() {
		return blnModificacion;
	}
	public void setBlnModificacion(Boolean blnModificacion) {
		this.blnModificacion = blnModificacion;
	}
	public Boolean getBlnArchivamiento() {
		return blnArchivamiento;
	}
	public void setBlnArchivamiento(Boolean blnArchivamiento) {
		this.blnArchivamiento = blnArchivamiento;
	}
	public AperturaCuentaController getAperturaCuentaController() {
		return aperturaCuentaController;
	}
	public void setAperturaCuentaController(
			AperturaCuentaController aperturaCuentaController) {
		this.aperturaCuentaController = aperturaCuentaController;
	}
	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}
	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}
	public Boolean getBlnMsgFecCumple() {
		return blnMsgFecCumple;
	}
	public void setBlnMsgFecCumple(Boolean blnMsgFecCumple) {
		this.blnMsgFecCumple = blnMsgFecCumple;
	}
	public Boolean getBlnRegimenLaboral() {
		return blnRegimenLaboral;
	}
	public void setBlnRegimenLaboral(Boolean blnRegimenLaboral) {
		this.blnRegimenLaboral = blnRegimenLaboral;
	}
	public Boolean getBlnRemunNeta() {
		return blnRemunNeta;
	}
	public void setBlnRemunNeta(Boolean blnRemunNeta) {
		this.blnRemunNeta = blnRemunNeta;
	}
	public Boolean getBlnCondLaboral() {
		return blnCondLaboral;
	}
	public void setBlnCondLaboral(Boolean blnCondLaboral) {
		this.blnCondLaboral = blnCondLaboral;
	}
	public String getMsgTxtDsctoJudicialYPerfil() {
		return msgTxtDsctoJudicialYPerfil;
	}
	public void setMsgTxtDsctoJudicialYPerfil(String msgTxtDsctoJudicialYPerfil) {
		this.msgTxtDsctoJudicialYPerfil = msgTxtDsctoJudicialYPerfil;
	}
	public String getMsgTxtDsctoJudicial() {
		return msgTxtDsctoJudicial;
	}
	public void setMsgTxtDsctoJudicial(String msgTxtDsctoJudicial) {
		this.msgTxtDsctoJudicial = msgTxtDsctoJudicial;
	}
	public String getMsgTxtLicencPermiso() {
		return msgTxtLicencPermiso;
	}
	public void setMsgTxtLicencPermiso(String msgTxtLicencPermiso) {
		this.msgTxtLicencPermiso = msgTxtLicencPermiso;
	}
	public String getMsgTxtLicencSubvencPermiso() {
		return msgTxtLicencSubvencPermiso;
	}
	public void setMsgTxtLicencSubvencPermiso(String msgTxtLicencSubvencPermiso) {
		this.msgTxtLicencSubvencPermiso = msgTxtLicencSubvencPermiso;
	}
	public String getMsgTxtRolPersona() {
		return msgTxtRolPersona;
	}
	public void setMsgTxtRolPersona(String msgTxtRolPersona) {
		this.msgTxtRolPersona = msgTxtRolPersona;
	}
	public String getMsgTxtPermisoSucursal() {
		return msgTxtPermisoSucursal;
	}
	public void setMsgTxtPermisoSucursal(String msgTxtPermisoSucursal) {
		this.msgTxtPermisoSucursal = msgTxtPermisoSucursal;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		SocioController.log = log;
	}

	public static SimpleDateFormat getSdfshort() {
		return sdfshort;
	}

	public static void setSdfshort(SimpleDateFormat sdfshort) {
		SocioController.sdfshort = sdfshort;
	}

	public Integer getSESION_IDUSUARIO() {
		return SESION_IDUSUARIO;
	}

	public void setSESION_IDUSUARIO(Integer sESION_IDUSUARIO) {
		SESION_IDUSUARIO = sESION_IDUSUARIO;
	}

	public Integer getSESION_IDSUCURSAL() {
		return SESION_IDSUCURSAL;
	}

	public void setSESION_IDSUCURSAL(Integer sESION_IDSUCURSAL) {
		SESION_IDSUCURSAL = sESION_IDSUCURSAL;
	}

	public Integer getSESION_IDSUBSUCURSAL() {
		return SESION_IDSUBSUCURSAL;
	}

	public void setSESION_IDSUBSUCURSAL(Integer sESION_IDSUBSUCURSAL) {
		SESION_IDSUBSUCURSAL = sESION_IDSUBSUCURSAL;
	}

	public EstructuraFacadeLocal getEstructuraFacade() {
		return estructuraFacade;
	}

	public void setEstructuraFacade(EstructuraFacadeLocal estructuraFacade) {
		this.estructuraFacade = estructuraFacade;
	}

	public Integer getIntBusquedaTipo() {
		return intBusquedaTipo;
	}

	public void setIntBusquedaTipo(Integer intBusquedaTipo) {
		this.intBusquedaTipo = intBusquedaTipo;
	}

	public String getStrBusqCadena() {
		return strBusqCadena;
	}

	public void setStrBusqCadena(String strBusqCadena) {
		this.strBusqCadena = strBusqCadena;
	}

	public Date getDtBusqFechaRegDesde() {
		return dtBusqFechaRegDesde;
	}

	public void setDtBusqFechaRegDesde(Date dtBusqFechaRegDesde) {
		this.dtBusqFechaRegDesde = dtBusqFechaRegDesde;
	}

	public Date getDtBusqFechaRegHasta() {
		return dtBusqFechaRegHasta;
	}

	public void setDtBusqFechaRegHasta(Date dtBusqFechaRegHasta) {
		this.dtBusqFechaRegHasta = dtBusqFechaRegHasta;
	}

	public Integer getIntBusqSituacionCta() {
		return intBusqSituacionCta;
	}

	public void setIntBusqSituacionCta(Integer intBusqSituacionCta) {
		this.intBusqSituacionCta = intBusqSituacionCta;
	}

	public String getStrRoldePersona() {
		return strRoldePersona;
	}

	public void setStrRoldePersona(String strRoldePersona) {
		this.strRoldePersona = strRoldePersona;
	}
	
	//Codigo agregado por rvillarreal
	public Date getDtFechaNacimiento() {
		return dtFechaNacimiento;
	}

	public void setDtFechaNacimiento(Date dtFechaNacimiento) {
		this.dtFechaNacimiento = dtFechaNacimiento;
	}	
	//-----

	public void setPersonaOld(Persona personaOld) {
		this.personaOld = personaOld;
	}

	public Persona getPersonaOld() {
		return personaOld;
	}

	public List<Estructura> getListEstructura() {
		return listEstructura;
	}

	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}

	public boolean isMostrarBoton() {
		return mostrarBoton;
	}

	public void setMostrarBoton(boolean mostrarBoton) {
		this.mostrarBoton = mostrarBoton;
	}
}