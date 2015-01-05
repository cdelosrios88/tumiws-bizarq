package pe.com.tumi.servicio.actividad.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
//import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;
//import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.prevision.controller.AutorizacionPrevisionController;
import pe.com.tumi.servicio.prevision.facade.AutorizacionPrevisionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.AutorizaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.service.SolicitudPrestamoService;

public class AutorizacionActividadController {
	protected static Logger log = Logger.getLogger(AutorizacionPrevisionController.class);
	
	private Usuario usuario;
	
	// Tabs de Solicitud de Actividad
	private Boolean blnSolicitud;
	private Boolean blnAutorizacion;

	private AutorizaCredito beanAutoriza;
	private Integer intParaEstadoAutorizar;
	private Integer intParaTipoAureobCod;
	private String strObservacion;
	
	//private AutorizaVerificacion beanAutorizaVerificacion;
	private ExpedienteCreditoComp expedienteCreditoCompSelected;
	
	private Boolean formAutorizacionActividadRendered;
	private Boolean formAutorizacionActividadViewRendered;
	private String strTxtMsgPerfil;
	private String strTxtMsgUsuario;
	private String strTxtMsgVigencia;
	private String strTxtMsgError;
	private String strTxtMsgValidacion;
	
	private List<RequisitoCreditoComp> listaRequisitoCreditoComp;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	private List<Tabla> listaTipoOperacion;
	private boolean showAutorizacionActividad;
	private List<AutorizaCreditoComp> listaAutorizaActividadComp;
	private List<ConfServSolicitud> listaAutorizacionConfigurada;

	// Busqueda
	private Integer intBusqTipoSolicitud;
	private List<Tabla> listaValidarDatosTipoSolicitud;
	private Integer intBusqTipoActividad;
	private List<Tabla> listaTiposActividad;
	private Integer intBusqTipoSubActividad;
	private List<Credito> listaBusqTiposSubActividad;
	private List<ExpedienteCreditoComp> listaAutorizacionCreditoComp ;
	private List<Sucursal> listSucursal;
	private List<Tabla> listaOperacionAutorizacion;  
	private List<Tabla> listaTipoOperacionAut;  
	
	//facades
	private ConfSolicitudFacadeLocal solicitudFacade;
	private GeneralFacadeRemote generalFacade;	
	private SocioFacadeRemote socioFacade;
	private TablaFacadeRemote tablaFacade;
	private PrevisionFacadeRemote previsionFacade;
	private PersonaFacadeRemote personaFacade;
	private PermisoFacadeRemote permisoFacade;
	private EmpresaFacadeRemote empresaFacade;
	private AutorizacionPrevisionFacadeRemote autorizacionFacade;
	private SolicitudPrestamoService solicitudPrestamoService;
	private SolicitudPrestamoFacadeRemote solicitudPrestamoFacade;
	private LibroDiarioFacadeRemote libroDiarioFacade;
	private CreditoFacadeRemote creditoFacade;
//	private ConfSolicitudFacadeRemote confSol;
	
	private String strConfiguracionesEvaluadas;
	private ConfServSolicitud autorizacionConfiguradaFinal = null;
	
	private boolean blnMostrarVer;
	private boolean blnMostrarAutorizar;
	private String strSolicitudAutorizacion;
	
	// BUSQUEDA - FILTROS - CGD
	private Integer intBusqTipo;
	private String strBusqCadena;
	private String strBusqNroSol;
	private Integer intBusqSucursal;
	private Integer intBusqEstado;
	private Date dtBusqFechaEstadoDesde;
	private Date dtBusqFechaEstadoHasta;
	private	List<Sucursal> listaSucursal;
	private Integer intBusqTipoCreditoEmpresa;
	private List<Tabla> listaTablaCreditoEmpresa;
	private List<Tabla> listaTablaDescConfCredito;
	private Integer intBusqItemCredito;
	
	private Date dtHoy = null;
	private Calendar fecHoy = Calendar.getInstance();
	
	//Autor: jchavez / Tarea: Modificación / Fecha: 01.09.2014 / 
	private List<Tabla> listaEstadoSolicitud;
	
	/**
	 * Constructor por defecto.
	 * @use cargarValoresIniciales();
	 */
	public AutorizacionActividadController() {
		beanAutoriza = new AutorizaCredito();
		//beanAutorizaVerificacion = new AutorizaVerificacion();
		formAutorizacionActividadRendered = false;
		formAutorizacionActividadViewRendered = false;
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		listaAutorizacionCreditoComp = new ArrayList<ExpedienteCreditoComp>();
		cargarValoresIniciales();
	}

	
	
	/**
	 * Carga al usuario logueado y renderiza el formulario de autorizacion a FALSE.
	 * @use_1 limpiarFormAutorizacionActividad();
	 * @use_2 cargarFacadesServicesListas();
	 */
	public void cargarValoresIniciales(){
		usuario = new Usuario();
		// pe.com.tumi.seguridad.login.domain.Usuario
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		limpiarFormAutorizacionActividad();
		strTxtMsgError = "";
		strTxtMsgPerfil = "";
		strTxtMsgValidacion = "";
		strTxtMsgVigencia = "";
		formAutorizacionActividadRendered = false;
		formAutorizacionActividadViewRendered = false;
		intParaEstadoAutorizar = 0;
		intParaTipoAureobCod = 0;
		cargarFacadesServicesListas();
		//Autor: jchavez / Tarea: Modificación / Fecha: 01.09.2014 / 
		try {
			
			listaEstadoSolicitud = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSOLICPRESTAMO),"D");
		} catch (Exception e) {
			log.error("error: " + e.getMessage());
		}
		//Fin jchavez - 01.09.2014
		dtHoy = fecHoy.getTime();
		
	}
	
	

	/**
	 * 
	 * @return
	 */
	public String getLimpiarAutorizacion(){
		cargarValoresIniciales();
		listaAutorizacionCreditoComp= new ArrayList<ExpedienteCreditoComp>();
		beanAutoriza = new AutorizaCredito();
		return "";
	}
	
	
	/**
	 * valida que el usuario logueo acceda a los menues(Transacciones) de acuerdo a configuracion.
	 */
		public void cargarPermisos() {
			PermisoPerfil permiso = null;
			PermisoPerfilId id = null;
			Integer MENU_SOLICITUD_ACTIVIDAD 	= 294;
			Integer MENU_AUTORIZACION_ACTIVIDAD = 295;
			try {
				if (usuario != null) {
					id = new PermisoPerfilId();
					id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
					id.setIntIdTransaccion(MENU_SOLICITUD_ACTIVIDAD);
					id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
					PermisoFacadeRemote remotePermiso = (PermisoFacadeRemote) EJBFactory.getRemote(PermisoFacadeRemote.class);
					permiso = remotePermiso.getPermisoPerfilPorPk(id);
					blnSolicitud = (permiso == null) ? true : false;
					id.setIntIdTransaccion(MENU_AUTORIZACION_ACTIVIDAD);
					permiso = remotePermiso.getPermisoPerfilPorPk(id);
					blnAutorizacion = (permiso == null) ? true : false;
					

					setUsuario(usuario);
				} else {
					blnSolicitud = false;
					blnAutorizacion = false;

				}
			} catch (BusinessException e) {
				log.error(e);
			} catch (EJBFactoryException e) {
				log.error(e);
			}
		}
		
		
		
		/**
		 * Valida si la operacion ELIMINAR se visualiza o no en el popup de acciones
		 */
		public void validarOperacionVer(){
			Integer intUltimoEstado = 0;
			
			try {
				
				if(expedienteCreditoCompSelected != null){
					//intUltimoEstado = expedienteCreditoCompSelected.getEstadoCredito().getIntParaEstadoCreditoCod();
					intUltimoEstado = expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo();
					
					if(intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)!=0){
						blnMostrarVer = Boolean.TRUE;
					}else{
						blnMostrarVer = Boolean.FALSE;
						
					}	
				}
		
			} catch (Exception e) {
				log.error("Error en validarOperacionEliminar ---> "+e);
			}

		}


	/**
		 * Valida si la operacion MODIFICAR se visualiza o no en el popup de acciones
		 */
		public void validarOperacionAutorizar(){
			Integer intUltimoEstado = 0;
			try {
				
				if(expedienteCreditoCompSelected != null){
					//intUltimoEstado = expedienteCreditoCompSelected.getEstadoCredito().getIntParaEstadoCreditoCod();
					intUltimoEstado = expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo();;
					if(intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0){
						blnMostrarAutorizar = Boolean.TRUE;
					}else{
						blnMostrarAutorizar = Boolean.FALSE;
					}
				}	
			} catch (Exception e) {
				log.error("Error en validarOperacionModificar ---> "+e);
			}

		}
		
		
	/**
	 * Se cargan los facades y listas para combos, descripciones, etc.
	 */
	public void cargarFacadesServicesListas(){
		
		try {
			solicitudFacade 			= (ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			generalFacade 				= (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			socioFacade					= (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class); 
			tablaFacade 				= (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);  
			previsionFacade 			= (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);  
			personaFacade 				= (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);  
			permisoFacade 				= (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);  
			empresaFacade 				= (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);  
			autorizacionFacade 			= (AutorizacionPrevisionFacadeRemote)EJBFactory.getRemote(AutorizacionPrevisionFacadeRemote.class);  
			solicitudPrestamoService 	= (SolicitudPrestamoService)TumiFactory.get(SolicitudPrestamoService.class);
			solicitudPrestamoFacade		= (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			libroDiarioFacade 			= (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			creditoFacade				= (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
//			confSol 					= (ConfSolicitudFacadeRemote)EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);

			listSucursal 				= empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			ordenarAlfabeticamenteSuc();
			listaOperacionAutorizacion 	= tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ESTADOAUTORIZACIONPRESTAMO));
			listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 5);
			listaTiposActividad  = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 3);
			listaTiposActividad  = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 3);

			cargarDescripcionConfCreditos();
			

		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException en cargarFacadesServicesListas() ---> " + e);
			e.printStackTrace();
		} catch (BusinessException e) {
			log.error("Error BusinessException en cargarFacadesServicesListas() ---> " + e);
			e.printStackTrace();
		}
			
	}

	/**
	 * 
	 * @return
	 */
	public List<Sucursal> ordenarAlfabeticamenteSuc(){
		if(listaSucursal != null && !listaSucursal.isEmpty()){
			//Ordenamos por nombre
			Collections.sort(listaSucursal, new Comparator<Sucursal>(){
				public int compare(Sucursal uno, Sucursal otro) {
					return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
				}
			});	
		}
			return listaSucursal;
	}
	
	/**
	 * 
	 */
	public void cargarDescripcionConfCreditos(){
		List<Credito> lstCreditos=null;
		try {
			lstCreditos = creditoFacade.getlistaCofCredito();
			listaTablaDescConfCredito = new ArrayList<Tabla>();
			if(lstCreditos != null && !lstCreditos.isEmpty()){
				for (Credito credito : lstCreditos) {
					Tabla tbl = new Tabla();
					tbl.setIntIdDetalle(credito.getId().getIntItemCredito());
					tbl.setStrDescripcion(credito.getStrDescripcion());
					listaTablaDescConfCredito.add(tbl);				
				}
			}
			
		} catch (Exception e) {
			log.error("Error en cargarDescripcionConfCreditos ---> "+e);
		}
		
	}
	
	
	/**
	 * Carga el 2do combo de Tipo de actividad segun 1er combo seleccionado
	 * @param event
	 */
	public void cargarComboSubActividadBusqueda(ActionEvent event)  {
		log.info("pIntTipoActividadBusq: "+getRequestParameter("pIntBusqTipoActividad"));
		//TablaFacadeRemote facade = null;
		String strIntTipoActividadBusq = null;
		//List<Tabla> listaTabla = null;
		List<Credito> lstCredito = null;
		List<Credito> lstCreditoTemporal = null;
		try {
			
			listaBusqTiposSubActividad = new ArrayList<Credito>();
			
			strIntTipoActividadBusq = getRequestParameter("pIntBusqTipoActividad");
			if(!strIntTipoActividadBusq.equals("0")){
				Credito creditoAct = new Credito();
				CreditoId id = new CreditoId();
				creditoAct.setId(id);
				creditoAct.setIntParaTipoCreditoEmpresa(new Integer(strIntTipoActividadBusq));
				
				lstCredito = creditoFacade.getlistaCreditoPorCredito(creditoAct);
				
				// VALIDAR FECHAS
				if(lstCredito != null && !lstCredito.isEmpty()){
					for (Credito credito : lstCredito) {
						lstCreditoTemporal = new ArrayList<Credito>();
						if((dtHoy.after(new Date(credito.getStrDtFechaIni()))) && (dtHoy.before(new Date(credito.getStrDtFechaFin())))){
							lstCreditoTemporal.add(credito);
						}
					}

					if(!lstCreditoTemporal.isEmpty()){
						setListaBusqTiposSubActividad(lstCreditoTemporal);
					}else{
						setListaBusqTiposSubActividad(null);
					}
				}

			}
		} catch (BusinessException e) {
			log.error("Error en cargarComboSubActividadBusqueda ---> "+e);
		}
		//setListaTiposSubActividad(lstCredito);
	}
	
	
	public void limpiarFiltros(ActionEvent event){
		intBusqTipo=0; 	
		strBusqCadena="";		    
		strBusqNroSol="";		   
		intBusqSucursal=0;			  
		intBusqEstado=0;		    
		dtBusqFechaEstadoDesde=null;  
		dtBusqFechaEstadoHasta=null;
		intBusqTipoCreditoEmpresa=0;
		intBusqItemCredito =0;
		intBusqSucursal=0;
		intBusqTipo=0;
		intBusqTipoActividad=0;
		intBusqTipoSolicitud=0;
		intBusqTipoSubActividad =0;
	}
	/**
	 * Devuelve un bean en sesion en base a su nombre.
	 * @param beanName
	 * @return sesion.getAttribute(beanName)
	 */
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	
	/**
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	
	/**
	 * Limpia el formulario de la autorizacion.
	 */
	public void limpiarFormAutorizacionActividad() {
		beanAutoriza = new AutorizaCredito();
		//beanAutorizaVerificacion = new AutorizaVerificacion();
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		// listaSolicitudAutorizadaFinal = new ArrayList<ConfServSolicitud>();
		strTxtMsgError = "";
		strTxtMsgPerfil = "";
		strTxtMsgUsuario="";
		strTxtMsgValidacion = "";
		strTxtMsgVigencia = "";
		formAutorizacionActividadRendered = false;
		formAutorizacionActividadViewRendered = false;
		intParaEstadoAutorizar = 0;
		intParaTipoAureobCod = 0;
		strSolicitudAutorizacion = "";
		if(listaAutorizaActividadComp != null && !listaAutorizaActividadComp.isEmpty()){
			listaAutorizaActividadComp.clear();
		}else{
			listaAutorizaActividadComp = new ArrayList<AutorizaCreditoComp>();
		}
		strObservacion = "";
	}
	
	
	/**
	 * Asociado al boton Cancelar. Renderiza el formulario a FALSE.
	 * @use limpiarFormAutorizacionActividad();
	 * @param event
	 */
	public void cancelarGrabarAutorizacionPrestamo(ActionEvent event) {
		limpiarFormAutorizacionActividad();
		formAutorizacionActividadRendered = false;
		formAutorizacionActividadViewRendered = false;
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void buscarSolicitudesPorAutorizar(ActionEvent event){
		SolicitudPrestamoFacadeLocal facade = null;
		cancelarGrabarAutorizacionPrestamo(event);
		ExpedienteCreditoComp expedienteCompBusq = null;
		try {
			facade = (SolicitudPrestamoFacadeLocal)EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			
			listaAutorizacionCreditoComp= new ArrayList<ExpedienteCreditoComp>();
			
			expedienteCompBusq = new ExpedienteCreditoComp();
			expedienteCompBusq.setIntBusqTipo(intBusqTipo);
			expedienteCompBusq.setStrBusqCadena(strBusqCadena.trim());
			expedienteCompBusq.setStrBusqNroSol(strBusqNroSol);
			expedienteCompBusq.setIntBusqSucursal(intBusqSucursal);
			expedienteCompBusq.setIntBusqEstado(intBusqEstado);
			expedienteCompBusq.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);
			expedienteCompBusq.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);
			expedienteCompBusq.setIntBusqTipoCreditoEmpresa(intBusqTipoCreditoEmpresa);
			expedienteCompBusq.setIntBusqItemCredito(intBusqItemCredito);

			listaAutorizacionCreditoComp = facade.getListaBusqActAutFiltros(expedienteCompBusq);
			
		} catch (BusinessException e) {
			log.error("Error BusinessException en buscarSolicitudesPorAutorizar ---> "+e);
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException en buscarSolicitudesPorAutorizar ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Metodo que se ejecuta al seleccionar un registro de la grilla de busqueda
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		try{
			expedienteCreditoCompSelected = (ExpedienteCreditoComp)event.getComponent().getAttributes().get("itemExpActividadPorAut");
			validarOperacionVer();
			validarOperacionAutorizar();
		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * Carga el 2do combo de Tipo de actividad segun 1er combo seleccionado
	 * @param event
	 */
	public void cargarComboTipoOperacionAut(ActionEvent event)  {
		String strIntTipoOper = null;
		List<Tabla> listaTabla = null;
		try {
			System.out.println("intParaEstadoAutorizarintParaEstadoAutorizar---> "+intParaEstadoAutorizar);
			strIntTipoOper = getRequestParameter("pIntParaEstadoAutorizar");
			if(!strIntTipoOper.equals("0")){
				//public static final String  = "213";
				
				//listaTabla = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO) , "B");
				//listaTabla = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO), strIntTipoOper);
				listaTabla = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO) , new Integer(strIntTipoOper));

				// VALIDAR FECHAS
				if(listaTabla != null && !listaTabla.isEmpty()){
					listaTipoOperacionAut = new ArrayList<Tabla>();
					//tListaTipoOperacionAut(listaTabla);
					listaTipoOperacionAut = listaTabla;
					//listaTipoOperacionAut.get(0).getIntIdDetalle();
					
				}
				System.out.println("listaTipoOperacionAut ---> "+listaTipoOperacionAut);
			}
		} catch (BusinessException e) {
			log.error("Error en cargarComboTipoOperacionAut ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Despliega el formulario de la autorizacion, si existen usuarios y/o perfiles configurados
	 * para la autorizacion de actividad.
	 * @param event
	 */
	public void showAutorizacionActividad(ActionEvent event) {
		List<ConfServSolicitud> listaSolicitudAutorizada = null;
		ConfServSolicitud confServSolicitud = null;
		autorizacionConfiguradaFinal = new ConfServSolicitud();
		listaAutorizacionConfigurada = new ArrayList<ConfServSolicitud>();
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
//		Calendar clToday = Calendar.getInstance();
//		String strConfiguracionesEvaluadas = "";
		Boolean blnContinuaValidacion = Boolean.TRUE;

		try {
			limpiarFormAutorizacionActividad();
			
			strSolicitudAutorizacion = Constante.MANTENIMIENTO_MODIFICAR;
			formAutorizacionActividadViewRendered = false;
			

				confServSolicitud = new ConfServSolicitud();
				confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD);
				confServSolicitud.setIntParaSubtipoOperacionCod(expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod());
				confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION);
				listaSolicitudAutorizada = solicitudFacade.buscarConfSolicitudAutorizacion(confServSolicitud, null,	null, null);
			
				if (listaSolicitudAutorizada != null && !listaSolicitudAutorizada.isEmpty()) {
					for (ConfServSolicitud solicitud : listaSolicitudAutorizada) {
						if(solicitud.getIntParaTipoRequertoAutorizaCod().compareTo(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION)==0){
							solicitud = validarConfiguracion(solicitud);
							if (solicitud != null) {
								listaAutorizacionConfigurada.add(solicitud);
								//break;
							}
						}
						
					}
				
					if (listaAutorizacionConfigurada != null && listaAutorizacionConfigurada.size() > 0) {
						for (ConfServSolicitud solicitud : listaAutorizacionConfigurada) {

							// 1. validando lista de perfiles
							if (solicitud.getListaPerfil() != null
									&& solicitud.getListaPerfil().size() > 0) {
								for (ConfServPerfil perfil : solicitud.getListaPerfil()) {
									if(blnContinuaValidacion){
									log.info("perfil.getIntIdPerfilPk(): "+ perfil.getIntIdPerfilPk());
									if (usuario.getPerfil().getId().getIntIdPerfil().equals(perfil.getIntIdPerfilPk())) {
										System.out.println("PERFIL APROBADO --> "+ perfil.getIntIdPerfilPk());
										blnContinuaValidacion = Boolean.FALSE;
										formAutorizacionActividadRendered = true;
										setStrTxtMsgPerfil("");
										listarEncargadosAutorizar();
										break;
									} else {
										formAutorizacionActividadRendered = false;
										setStrTxtMsgPerfil("El Perfil no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
									}
								}
								}
							}
							// 2. validando lista de usuarios
							if (solicitud.getListaUsuario() != null	&& solicitud.getListaUsuario().size() > 0) {
								for (ConfServUsuario $usuario : solicitud.getListaUsuario()) {
									if(blnContinuaValidacion){
									log.info("$usuario.getIntPersUsuarioPk(): "+ $usuario.getIntPersUsuarioPk());
									if (usuario.getIntPersPersonaPk().equals($usuario.getIntPersUsuarioPk())) {
										System.out.println("USUARIO APROBADO --> "	+ usuario.getStrUsuario());
										blnContinuaValidacion = Boolean.FALSE;
										formAutorizacionActividadRendered = true;
										setStrTxtMsgUsuario("");
										if (listarEncargadosAutorizar() == true) {

										}
										break;
									} else {
										formAutorizacionActividadRendered = false;
										setStrTxtMsgUsuario("El Usuario no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
									}
								}
								}
							}

						}
					} else{
						setStrTxtMsgPerfil("No existen Perfiles o usuarios configurados.");
					}
				
				} else{
					setStrTxtMsgPerfil("No se recuperaron Perfiles o usuarios configurados.");
				}
				
				// colocado por pruebas...
				//formAutorizacionActividadRendered = false;
			} catch (BusinessException e) {
				System.out.println("BusinessException --> "+e);
				e.printStackTrace();
			}
			System.out.println("formAutorizacionActividadRendered --> "+formAutorizacionActividadRendered);
	}
	
	
	
	public void showViewAutorizacionActividad(ActionEvent event) {
		List<ConfServSolicitud> listaSolicitudAutorizada = null;
		ConfServSolicitud confServSolicitud = null;
		autorizacionConfiguradaFinal = new ConfServSolicitud();
		listaAutorizacionConfigurada = new ArrayList<ConfServSolicitud>();
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
//		Calendar clToday = Calendar.getInstance();
//		String strConfiguracionesEvaluadas = "";
		Boolean blnContinuaValidacion = Boolean.TRUE;

		try {
			
			strSolicitudAutorizacion = Constante.MANTENIMIENTO_NINGUNO;
			formAutorizacionActividadRendered = false;
			
				confServSolicitud = new ConfServSolicitud();
				confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD);
				confServSolicitud.setIntParaSubtipoOperacionCod(expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod());
				confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION);
				listaSolicitudAutorizada = solicitudFacade.buscarConfSolicitudAutorizacion(confServSolicitud, null,	null, null);
			
				if (listaSolicitudAutorizada != null && !listaSolicitudAutorizada.isEmpty()) {
					for (ConfServSolicitud solicitud : listaSolicitudAutorizada) {
						if(solicitud.getIntParaTipoRequertoAutorizaCod().compareTo(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION)==0){
							solicitud = validarConfiguracion(solicitud);
							if (solicitud != null) {
								listaAutorizacionConfigurada.add(solicitud);
							}
						}
						
					}
				
					if (listaAutorizacionConfigurada != null && !listaAutorizacionConfigurada.isEmpty()) {
						for (ConfServSolicitud solicitud : listaAutorizacionConfigurada) {
					
							// 1. validando lista de perfiles
							if (solicitud.getListaPerfil() != null
									&& solicitud.getListaPerfil().size() > 0) {
								for (ConfServPerfil perfil : solicitud.getListaPerfil()) {
									if(blnContinuaValidacion){
									log.info("perfil.getIntIdPerfilPk(): "+ perfil.getIntIdPerfilPk());
									if (usuario.getPerfil().getId().getIntIdPerfil().equals(perfil.getIntIdPerfilPk())) {
										System.out.println("PERFIL APROBADO --> "+ perfil.getIntIdPerfilPk());
										blnContinuaValidacion = Boolean.FALSE;
										autorizacionConfiguradaFinal = solicitud;
										formAutorizacionActividadViewRendered = true;
										setStrTxtMsgPerfil("");
										listarEncargadosAutorizar();
										break;
									} else {
										formAutorizacionActividadViewRendered = false;
										setStrTxtMsgPerfil("El Perfil no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
									}
								}
								}
							}
							// 2. validando lista de usuarios
							if (solicitud.getListaUsuario() != null	&& solicitud.getListaUsuario().size() > 0) {
								for (ConfServUsuario $usuario : solicitud.getListaUsuario()) {
									if(blnContinuaValidacion){
									log.info("$usuario.getIntPersUsuarioPk(): "+ $usuario.getIntPersUsuarioPk());
									if (usuario.getIntPersPersonaPk().equals($usuario.getIntPersUsuarioPk())) {
										System.out.println("USUARIO APROBADO --> "	+ usuario.getStrUsuario());
										autorizacionConfiguradaFinal = solicitud;
										blnContinuaValidacion = Boolean.FALSE;
										autorizacionConfiguradaFinal = solicitud;
										formAutorizacionActividadViewRendered = true;
										setStrTxtMsgUsuario("");
										if (listarEncargadosAutorizar() == true) {

										}
										break;
									} else {
										formAutorizacionActividadViewRendered = false;
										setStrTxtMsgUsuario("El Usuario no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
									}
								}
							}
							}
					
						}
					} else{
						setStrTxtMsgPerfil("No existen Perfiles o usuarios configurados.");
					}
				
				} else{
					setStrTxtMsgPerfil("No se recuperaron Perfiles o usuarios configurados.");
				}
				
				// colocado por pruebas...
				//formAutorizacionActividadRendered = false;
			} catch (BusinessException e) {
				System.out.println("BusinessException --> "+e);
				e.printStackTrace();
			}
	}
	
	
	/**
	 * Valida que la configuracion recuperada sea de autorizacion (2) y no requisito (1). Ademas Rangos de montos.
	 * @param confServSolicitud
	 * @return
	 */
public ConfServSolicitud validarConfiguracion(ConfServSolicitud confServSolicitud) {
	Boolean hasMontDesde = false;
	Boolean hasMontHasta = false;
//	Boolean hasCuotaDesde = false;
//	Boolean hasCuotaHasta = false;
	Integer nroValidaciones = null;
	Integer contAprob = new Integer(0);
	Boolean boAprueba = new Boolean(false);
	ConfServSolicitud solicitud = null;
	Calendar clToday = Calendar.getInstance();
	String strResumen = "";
	solicitud = confServSolicitud;
	strResumen = solicitud.getId().getIntItemSolicitud()+"- No pasa las validaciones de: ";
	nroValidaciones = new Integer(3); // Se inicializa en 3 xq se toma en cuenta (1)Vigencia, (2) Estado, (3)Tipo y subtipo.

	if (solicitud.getBdMontoDesde() != null) {
		hasMontDesde = true;
		nroValidaciones++;
	}
	if (solicitud.getBdMontoHasta() != null) {
		hasMontHasta = true;
		nroValidaciones++;
	}

	// 1. Validando la Vigencia
	if ((clToday.getTime().compareTo(solicitud.getDtDesde()) > 0 && solicitud.getDtHasta() == null)
			|| (clToday.getTime().compareTo(solicitud.getDtDesde()) > 0 && clToday.getTime().compareTo(solicitud.getDtHasta()) < 0)) {
		contAprob++;
	}else{
		strResumen = strResumen + " 1. Validando la Vigencia. ";
	}
	// 2. Validando el estado
	if (solicitud.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0) {
		contAprob++;
	}else{
		strResumen = strResumen + " 2. Validando el estado. ";
	}
	
	
	Integer intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD;


	
	// 3. Validando el tipo y subtipo
	if (expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod().compareTo(solicitud.getIntParaSubtipoOperacionCod())==0
		&& (solicitud.getIntParaTipoOperacionCod().compareTo(intTipoOperacion))==0) {
		contAprob++;
		/*public static final Integer PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA = 1;
		public static final Integer PARAM_T_TIPOOPERACION_PRESTAMO = 2;
		public static final Integer PARAM_T_TIPOOPERACION_ORDENCREDITO = 3;
		public static final Integer PARAM_T_TIPOOPERACION_ACTIVIDAD = 4;
		public static final Integer PARAM_T_TIPOOPERACION_FONDOSEPELIO = 5;
		public static final Integer PARAM_T_TIPOOPERACION_FONDORETIRO = 6;
		public static final Integer PARAM_T_TIPOOPERACION_AES = 7;*/
		
	}else{
		strResumen = strResumen + " 3. Validando el tipo y subtipo. ";
	}
	// 4. Validando Monto Desde
	if (hasMontDesde){
			if (expedienteCreditoCompSelected.getExpedienteCredito().getBdMontoSolicitado().compareTo(solicitud.getBdMontoDesde()) >= 0){
				contAprob++;
			}else{
				strResumen = strResumen + " 4. Validando Monto Desde. ";
			}
	}
	// 5. Validando Monto Hasta
	if (hasMontHasta){
		if (expedienteCreditoCompSelected.getExpedienteCredito().getBdMontoSolicitado().compareTo(solicitud.getBdMontoHasta()) <= 0){
			contAprob++;
		}else{
			strResumen = strResumen + " 5. Validando Monto Hasta. ";
		}
	}
	// 6. Validando Nro Cuotas Desde
			
	// 7. Validando Nro Cuotas Hasta
			
	// 8. Validando que exista al menos uno
	/*if ((solicitud.getBdMontoDesde() != null)
			|| (solicitud.getBdMontoHasta() != null)
			|| (solicitud.getBdCuotaDesde() != null)
			|| (solicitud.getBdCuotaHasta() != null)) {
		contAprob++;
	}*/
	// ------------------
	// }
	System.out.println("===========================================================");
	System.out.println(" NRO DE REQAUT " + solicitud.getId().getIntItemSolicitud());
	System.out.println(" NRO DE VALIDACIONES EXISTENTES " + nroValidaciones);
	System.out.println(" NRO DE VALIDACIONES APROBADAS " + contAprob);
	//System.out.println(" hasCuotaDesde --->  " + hasCuotaDesde);
	//System.out.println(" hasCuotaHasta --->  " + hasCuotaHasta);
	
	System.out.println(" OBSERVACION " + strResumen);
	System.out.println("===========================================================");

	if (nroValidaciones.compareTo(contAprob)==0) boAprueba = true;
	if (boAprueba) {
		return solicitud;
	} else {
		return null;
	}
}
	/**
	 * Recupera una lista con las personas que ya han autorizado, rechazado u observado la solicitud.
	 * @return
	 */
	public boolean listarEncargadosAutorizar() {
		boolean isValidEncaragadoAutorizar = true;
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		listaAutorizaActividadComp = new ArrayList<AutorizaCreditoComp>();
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
			listaAutorizaCredito = solicitudPrestamoFacade.getListaAutorizaCreditoPorPkExpediente(expedienteCreditoCompSelected.getExpedienteCredito().getId());
			if (listaAutorizaCredito != null && !listaAutorizaCredito.isEmpty()) {
				for (AutorizaCredito autorizaCredito : listaAutorizaCredito) {
					if(autorizaCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						autorizaCreditoComp = new AutorizaCreditoComp();
						autorizaCreditoComp.setAutorizaCredito(autorizaCredito);
						persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaCredito.getIntPersUsuarioAutoriza());
						for (int k = 0; k < persona.getListaDocumento().size(); k++) {
							if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
								persona.setDocumento(persona.getListaDocumento().get(k));
								break;
							}
						}

						// recuperando el perfil del usuario
						Perfil perfil = null;
						PerfilId perfilId = null;
						autorizaCreditoComp.setPersona(persona);
						
						perfilId = new PerfilId();
						perfilId.setIntPersEmpresaPk(autorizaCreditoComp.getAutorizaCredito().getIntPersEmpresaAutoriza());
						perfilId.setIntIdPerfil(autorizaCreditoComp.getAutorizaCredito().getIntIdPerfilAutoriza());
						// recuperando el perfil del usuario
						perfil = permisoFacade.getPerfilYListaPermisoPerfilPorPkPerfil(perfilId);
						if(perfil != null){
							autorizaCreditoComp.setStrPerfil(perfil.getStrDescripcion());
						}
						listaAutorizaActividadComp.add(autorizaCreditoComp);
					}
					
				}
			}


		} catch (BusinessException e) {
			log.error("Error en listarEncargadosAutorizar ---> "+e);
			e.printStackTrace();
		}

		return isValidEncaragadoAutorizar;
	}
	
	
	/**
	 * Metodo que indica si todos los usuarios o perfiles de la autorizacion estan completos.
	 * En ese caso, ya se podria modificar.
	 */
	public void validarSiEstaCerradaLaAutorizacion(){
		
		try {
			refrescarEncargadosAutorizar();
			
			
		} catch (Exception e) {
			log.error("Error en validarSiEstaCerradaLaAutorizacion --->"+e);
		}
		
		
	}
	
	
	/**
	 * Graba la autorizacion completa.
	 * @param event
	 * @throws Exception
	 */
	public void grabarAutorizacionActividad(ActionEvent event) throws Exception {
		ExpedienteCredito expedienteActividad = null;
		List<AutorizaCredito> listaAutorizaCredito = null;
		Integer intNroPerfiles = null;
		Integer intNroUsuarios = null;
		boolean blnValidarUsuario = false;
		boolean blnValidarPerfil = false;
		Integer intTipoValidacion = new Integer(0); // o-usuario 1-perfil
		boolean blnNoExiste = true;
		boolean blnSeRegistaronTodos = false;
		LibroDiario diario = null;
//		CreditoId creditoId = null;
//		Credito beanCredito = null;

		//List<AutorizaCreditoComp> autorizaCreditoCompRecuperados = null;
		libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);

		try {	

				listaAutorizaCredito = new ArrayList<AutorizaCredito>();
				
				refrescarEncargadosAutorizar();
				
				if (isValidoAutorizacion() == false) {
					strTxtMsgValidacion = strTxtMsgValidacion + "No se puede continuar con el proceso de grabación. ";
					return;
				}
				
				//nrolista = listaAutorizacionConfigurada.size(); // numero de autorizaciones configuradas
				intNroUsuarios = listaAutorizacionConfigurada.get(0).getListaUsuario().size(); // numero de usuarios q deben autorizar
				intNroPerfiles = listaAutorizacionConfigurada.get(0).getListaPerfil().size(); // numero de perfiless que deben autorizar
	
				if (intNroUsuarios.intValue() > 0) {
					blnValidarUsuario = true;
					// intTipoValidacion = 0 ;
				}
				if (intNroPerfiles.intValue() > 0) {
					blnValidarPerfil = true;
					intTipoValidacion = new Integer(1);
				}
				beanAutoriza.setIntParaEstadoAutorizar(intParaEstadoAutorizar);
				beanAutoriza.setIntParaTipoAureobCod(intParaTipoAureobCod);
				beanAutoriza.setIntPersEmpresaAutoriza(usuario.getEmpresa().getIntIdEmpresa());
				beanAutoriza.setIntPersUsuarioAutoriza(usuario.getIntPersPersonaPk());
				beanAutoriza.setIntIdPerfilAutoriza(usuario.getPerfil().getId().getIntIdPerfil());
				beanAutoriza.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				beanAutoriza.setStrObservacion(strObservacion);
				//beanAutorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

				expedienteActividad = expedienteCreditoCompSelected.getExpedienteCredito();
				listaAutorizaCredito.add(beanAutoriza);
				//listaAutorizaVerificacion.add(beanAutorizaVerificacion);
				expedienteActividad.setListaAutorizaCredito(listaAutorizaCredito);
				//expedienteActividad.setListaAutorizaVerificacion(listaAutorizaVerificacion);
				
				
			// Validamos la Operacion - AUTORIZAR
			// =================================================================================================================================
				if (intParaEstadoAutorizar.compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO)==0) {
					if (expedienteCreditoCompSelected.getEstadoCredito().getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
						// Validamos Usuario
						if (blnValidarUsuario) {
							blnNoExiste = existeRegistro(intTipoValidacion);
							if (blnNoExiste) {
								blnSeRegistaronTodos = faltaSoloUno(intTipoValidacion, listaAutorizacionConfigurada.get(0));
								//blnSeRegistaronTodos = faltaSoloUno(intTipoValidacion, listaAutorizacionConfigurada);
								solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteActividad);
	
							} else {
								strTxtMsgPerfil = strTxtMsgUsuario + "Ya existe una Autorización registrada con el usuario: "
																   + usuario.getStrUsuario() + ". No se puede continuar con el grabado.";
							}
						}
						// Validamos Perfil
						else if (blnValidarPerfil) {
							blnNoExiste = existeRegistro(intTipoValidacion);
							if (blnNoExiste) {
								//blnSeRegistaronTodos = seRegistraronTodosLosUsuarios(intTipoValidacion,listaAutorizacionConfigurada);
								blnSeRegistaronTodos = faltaSoloUno(intTipoValidacion, listaAutorizacionConfigurada.get(0));
								if (blnSeRegistaronTodos) {
									/*diario  = autorizacionFacade.aprobarLiquidacionCuentas(beanSocioComp, obtenerPeriodoActual(), requisitoLiq, 
											registroSeleccionadoBusqueda,usuario,
											expedienteLiquidacion,Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO,
											intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia,auditoria);	
									*/
									diario = solicitudPrestamoFacade.generarProcesosAutorizacionActividad(expedienteActividad , usuario);
									if(diario != null){
										expedienteActividad = solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteActividad);
										cambioEstadoActividad(diario, expedienteActividad,Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
										cancelarGrabarAutorizacionPrestamo(event);
									}
								}
							} else {
								strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización con el Perfil "
																+ usuario.getPerfil().getStrDescripcion()
																+ " id: " + usuario.getPerfil().getId().getIntIdPerfil()
																+ ". No se puede continuar con el grabado.";
							}
						}
	
					} else {
						strTxtMsgError = "La Solicitud solo puede Autorizarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
						// cancelarGrabarAutorizacionPrestamo(event);
						return;
					}
	
			// Validamos la Operacion - OBSERVAR
			// ==================================================================================================================================
				} else if (intParaEstadoAutorizar.compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_PRESTAMO)==0) {
	
					if (expedienteCreditoCompSelected.getEstadoCredito().getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
	
						// Validamos Usuario
						if (blnValidarUsuario) {
							blnNoExiste = existeRegistro(intTipoValidacion);
							if (blnNoExiste) {
								solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteActividad);
								solicitudPrestamoFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedienteActividad, 
																											Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD , 
																											expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod());

								cambioEstadoCredito(expedienteActividad,Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);
	
							} else {
								strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización registrada con el usuario: "
																  + usuario.getStrUsuario()+ ". No se puede continuar con el grabado.";
							}
	
						}
						// Validamos Perfil
						else if (blnValidarPerfil) {
							blnNoExiste = existeRegistro(intTipoValidacion);
							if (blnNoExiste) {
								solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteActividad);
								solicitudPrestamoFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedienteActividad, 
										Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD , 
										expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod());

								cambioEstadoCredito(expedienteActividad,Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);
	
							} else {
								strTxtMsgPerfil = strTxtMsgPerfil+ "Ya existe una Autorización con el Perfil "
																+ usuario.getPerfil().getStrDescripcion()+ " id: "
																+ usuario.getPerfil().getId().getIntIdPerfil()+ ". No se puede continuar con el grabado.";
							}
	
						}
	
					} else {
						strTxtMsgError = "La Solicitud solo puede Observarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
						// cancelarGrabarAutorizacionPrestamo(event);
						return;
					}
	
					// Validamos la Operacion - RECHAZAR
					// ==================================================================================================================================
				} else if (intParaEstadoAutorizar.compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO)==0) {
					if (expedienteCreditoCompSelected.getEstadoCredito().getIntParaEstadoCreditoCod().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD) {
						// Validamos Usuario
						if (blnValidarUsuario) {
							blnNoExiste = existeRegistro(intTipoValidacion);
							if (blnNoExiste) {
								solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteActividad);
								// blnSeRegistaronTodos =
								// seRegistraronTodosLosUsuarios(strTipoValidacion,listaAutorizacionConfigurada
								// );
								cambioEstadoCredito(expedienteActividad,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);
								//irGenerarLibro(expedienteActividad,	Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO);
	
							} else {
								strTxtMsgPerfil = strTxtMsgPerfil+ "Ya existe una Autorización registarda con el usuario: "
																+ usuario.getStrUsuario()+ ". No se puede continuar con el grabado.";
							}
						}
						// Validamos Perfil
						else if (blnValidarPerfil) {
							blnNoExiste = existeRegistro(intTipoValidacion);
							if (blnNoExiste) {
								solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteActividad);
	
								cambioEstadoCredito(expedienteActividad,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);
								//irGenerarLibro(expedienteActividad,Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO);
	
							} else {
								strTxtMsgPerfil = strTxtMsgPerfil+ "Ya existe una una Autorización con el Perfil "
																+ usuario.getPerfil().getStrDescripcion()+ " id: "
																+ usuario.getPerfil().getId().getIntIdPerfil()+ ". No se puede continuar con el grabado.";
							}
						}
	
					} else {
						strTxtMsgError = "La Solicitud solo puede Aprobarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
					}
	
				}
	
				limpiarFormAutorizacionActividad();
				cancelarGrabarAutorizacionPrestamo(event);

		} catch (BusinessException e) {
			log.info("Error al grabarAutorizacionPrestamo --> " + e);
		}
	}
	
	
	/**
	 * Valida que si con el registro de la presente autorizacion se inica los registros de liquidacion y cambio de estado.
	 * @param intTipoValidacion
	 * @param listaAutorizacionConfigurada
	 * @return
	 */
	/*private boolean faltaSoloUno(Integer intTipoValidacion, List<ConfServSolicitud> listaAutorizacionConfigurada) {
		Integer intNroUsuariosConf = null;
		Integer intNroPerfilesConf = null;
		BigDecimal bdNroUsuariosConf = null;
		BigDecimal bdNroPerfilesConf = null;
		
		boolean blnEsElUltimo = false;
		Integer intRecuperados = 0;
	
		if (listaAutorizacionConfigurada != null) {
	
			bdNroUsuariosConf = BigDecimal.ZERO;
			bdNroPerfilesConf = BigDecimal.ZERO;
			
			for (ConfServSolicitud confServSolicitud : listaAutorizacionConfigurada) {
				if(confServSolicitud.getListaPerfil() != null && !confServSolicitud.getListaPerfil().isEmpty()){
					bdNroPerfilesConf = bdNroPerfilesConf.add(new BigDecimal(confServSolicitud.getListaPerfil().size()));	
				}
				
				if(confServSolicitud.getListaUsuario() != null && !confServSolicitud.getListaUsuario().isEmpty()){
					bdNroUsuariosConf = bdNroUsuariosConf.add(new BigDecimal(confServSolicitud.getListaUsuario().size()));	
				}	
			}
			
			//intNroUsuariosConf = listaAutorizacionConfigurada.get(0).getListaUsuario().size(); // numero de usuarios q deben autorizar
			//intNroPerfilesConf = listaAutorizacionConfigurada.get(0).getListaPerfil().size(); // numero de perfiless que deben autorizar
	
			refrescarEncargadosAutorizar();
			intRecuperados = listaAutorizaActividadComp.size();
	
			if (intTipoValidacion.compareTo(1)==0) { // perfil
				if (bdNroPerfilesConf.compareTo(new BigDecimal(intRecuperados)) == 1) {
					blnEsElUltimo = true;
				}
	
			} else if (intTipoValidacion.compareTo(0)== 0) { // usuario
				if (bdNroUsuariosConf.compareTo(new BigDecimal(intRecuperados)) == 1) {
					blnEsElUltimo = true;
				}
			}
	
		}
	
		return blnEsElUltimo;
	}*/
	
	
	/**
	 * 
	 */
	public void refrescarEncargadosAutorizar() {
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		listaAutorizaActividadComp = new ArrayList<AutorizaCreditoComp>();
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
			listaAutorizaCredito = solicitudPrestamoFacade.getListaAutorizaCreditoPorPkExpediente(expedienteCreditoCompSelected.getExpedienteCredito().getId());
			if (listaAutorizaCredito != null && listaAutorizaCredito.size() > 0) {
				for (AutorizaCredito autorizaCredito : listaAutorizaCredito) {
					if(autorizaCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						autorizaCreditoComp = new AutorizaCreditoComp();
						autorizaCreditoComp.setAutorizaCredito(autorizaCredito);
						persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaCredito.getIntPersUsuarioAutoriza());
						for (int k = 0; k < persona.getListaDocumento().size(); k++) {
							if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
								persona.setDocumento(persona.getListaDocumento().get(k));
								break;
							}
						}
						autorizaCreditoComp.setPersona(persona);
						listaAutorizaActividadComp.add(autorizaCreditoComp);
					}
				}
			}
		} catch (BusinessException e) {
			log.error("Error en refrescarEncargadosAutorizar ---> "+e);
			e.printStackTrace();
		}

	}

	
	/**
	 * Valida que se seleccionen los combos Operacion y tipo de Operacion.
	 * @param autorizaActividad
	 * @return
	 */
	private Boolean isValidoAutorizacion() {
		Boolean validAutorizaPrevision = true;
		strTxtMsgValidacion = "";
		
		if ( intParaEstadoAutorizar.compareTo(new Integer(0)) == 0) {
			strTxtMsgValidacion = strTxtMsgValidacion + "* Seleccionar Operación. ";
			validAutorizaPrevision = false;
		}
		
		if ( intParaTipoAureobCod.compareTo(new Integer(0)) == 0) {
			strTxtMsgValidacion = strTxtMsgValidacion + "* Seleccionar Tipo de Operación. ";
			validAutorizaPrevision = false;
		}
		return validAutorizaPrevision;
	}
	
	
	/**
	 * Compara el nro de autorizaciones existentes con los usuarios configurados.
	 * Si son iguales devuelve TRUE.
	 * COMENTADO XQ NO SE UTILIZA 17.03.2014
	 * @param intTipoValidacion
	 * @param listaAutorizacionConfigurada
	 * @return blnTodos
	 */
//	private boolean seRegistraronTodosLosUsuarios(Integer intTipoValidacion,
//		List<ConfServSolicitud> listaAutorizacionConfigurada) {
//		Integer intNroUsuariosConf = null;
//		Integer intNroPerfilesConf = null;
//		boolean blnTodos = false;
//		Integer intRecuperados = 0;
//
//		if (listaAutorizacionConfigurada != null && !listaAutorizacionConfigurada.isEmpty()) {
//			intNroUsuariosConf = listaAutorizacionConfigurada.get(0).getListaUsuario().size(); // numero de usuarios q deben autorizar
//			intNroPerfilesConf = listaAutorizacionConfigurada.get(0).getListaPerfil().size(); // numero de perfiless que deben autorizar
//
//			refrescarEncargadosAutorizar();
//			intRecuperados = listaAutorizaActividadComp.size();
//
//			if (intTipoValidacion.intValue() == 1) { // perfil
//				if (intNroPerfilesConf.intValue() == (intRecuperados)) {
//					blnTodos = true;
//				}
//
//			} else if (intTipoValidacion.intValue() == 0) { // usuario
//				if (intNroUsuariosConf.intValue() == (intRecuperados)) {
//					blnTodos = true;
//				}
//			}
//
//		}
//		return blnTodos;
//	}
	
	
	/**
	 * Indica si el usuario ya realizo una autorizacion.
	 * Si aun no, devuelve blnNoExiste = true.
	 * @return boolean blnNoExiste
	 */
	public boolean existeRegistro(Integer intTipoValidacion) {
		boolean blnNoExiste = true;

		if (!listaAutorizaActividadComp.isEmpty() && listaAutorizaActividadComp != null) {
			for (int k = 0; k < listaAutorizaActividadComp.size(); k++) {
				AutorizaCredito autorizaCredito = new AutorizaCredito();
				autorizaCredito = listaAutorizaActividadComp.get(k).getAutorizaCredito();

				if (intTipoValidacion.intValue() == 0) {// usuario
					if (autorizaCredito.getIntPersUsuarioAutoriza().intValue() == (usuario.getPerfil().getId().getIntIdPerfil().intValue())
						&& autorizaCredito.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						blnNoExiste = false;
						break;
					}

				} else if (intTipoValidacion.intValue() == 1) { // perfil
					if (autorizaCredito.getIntIdPerfilAutoriza().intValue() == (usuario.getPerfil().getId().getIntIdPerfil().intValue())
						&& autorizaCredito.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						blnNoExiste = false;
						break;
					}

				}

			}
		}

		return blnNoExiste;
	}
	
	
	/**
	 * Cambia de estado de la actividad, agregando un registro de Estado.
	 * 
	 * @param expedienteCredito
	 * @throws Exception
	 */
	private void cambioEstadoActividad(LibroDiario libroDiario, ExpedienteCredito expedienteCredito,
		Integer intParaEstadoCreditoCod) throws Exception {
		EstadoCredito estadoCredito = null;
		
		try {
			estadoCredito = new EstadoCredito();
			estadoCredito.setId(new EstadoCreditoId());
			estadoCredito.getId().setIntCuentaPk(expedienteCredito.getId().getIntCuentaPk());
			estadoCredito.getId().setIntItemDetExpediente(expedienteCredito.getId().getIntItemDetExpediente());
			estadoCredito.getId().setIntItemExpediente(expedienteCredito.getId().getIntItemExpediente());
			estadoCredito.getId().setIntPersEmpresaPk(expedienteCredito.getId().getIntPersEmpresaPk());
			estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
			estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
			estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
			estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
			estadoCredito.setIntParaEstadoCreditoCod(intParaEstadoCreditoCod);

			estadoCredito.setIntCodigoLibro(libroDiario.getId().getIntContCodigoLibro());
			estadoCredito.setIntPeriodoLibro(libroDiario.getId().getIntContPeriodoLibro());
			estadoCredito.setIntPersEmpresaLibro(libroDiario.getId().getIntPersEmpresaLibro());

			//expedienteCredito.getListaEstadoCredito().add(estadoCredito);
			solicitudPrestamoFacade.grabarEstadoAutorizadoRefinan(estadoCredito);
			//solicitudPrestamoFacade.modificarExpedienteCredito(expedienteCredito);
		} catch (Exception e) {
			log.error("Error en cambioEstadoCredito ---> "+e);
			e.printStackTrace();
		}
	}

	
	/**
	 * Cambia de estado al expediente, agregando un registro de Estado.
	 * 
	 * @param expedienteCredito
	 * @throws Exception
	 */
	private void cambioEstadoCredito(ExpedienteCredito expedienteCredito,
		Integer intParaEstadoCreditoCod) throws Exception {
		EstadoCredito estadoCredito = new EstadoCredito();
		EstadoCreditoId estadoCreditoId = null;

		estadoCredito.setId(estadoCreditoId);
		
		estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
		estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
		estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
		estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
		estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
		estadoCredito.setIntParaEstadoCreditoCod(intParaEstadoCreditoCod);

		//LibroDiario ultimoLibro = new LibroDiario();
		//LibroDiarioId libroDiarioId = new LibroDiarioId();
		//libroDiarioId.setIntContCodigoLibro(null);
		//libroDiarioId.setIntContPeriodoLibro(obtenerPeriodoActual());
		//libroDiarioId.setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);

		// recuperamos el ultimo registro del libro diario en el presente
		// periodo. Por el codigo de libro.
		//ultimoLibro = libroDiarioFacade.getLibroDiarioUltimoPorPk(libroDiarioId);

		//int intCodigoLibroDiario = -1;
		// recuperar el ultimo libro
		//if (ultimoLibro == null) {
		//	intCodigoLibroDiario = 1;
		//} else {
		//	intCodigoLibroDiario = ultimoLibro.getId().getIntContCodigoLibro()
		//			.intValue() + 1;
		//}

		// estadoCredito.setIntCodigoLibro(14);
		//estadoCredito.setIntCodigoLibro(intCodigoLibroDiario);
		//estadoCredito.setIntPeriodoLibro(obtenerPeriodoActual());
		//estadoCredito.setIntPersEmpresaLibro(Constante.PARAM_EMPRESASESION);
		
		if (expedienteCredito.getListaEstadoCredito()==null) {
			expedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());
		}
		expedienteCredito.getListaEstadoCredito().add(estadoCredito);
		solicitudPrestamoFacade.modificarExpedienteCredito(expedienteCredito);
	}
	
	/**
	 * Genera el asiento contable.
	 * @param expedienteCredito
	 * @param operacion
	 */
//	private void irGenerarLibro(ExpedienteCredito expedienteCredito,
//			Integer operacion) {
//		LibroDiario libroDiarioProvision = null;
//		try {
//
//			if (operacion.intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO) {
//				libroDiarioProvision = solicitudPrestamoFacade.generarLibroDiarioPrestamo(expedienteCredito);
//
//			} else if (operacion.intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO) {
//				libroDiarioProvision = solicitudPrestamoFacade.generarLibroDiarioAnulacion(expedienteCredito);
//				for (LibroDiarioDetalle libroDiarioDetalle : libroDiarioProvision.getListaLibroDiarioDetalle()) {
//					log.info(libroDiarioDetalle);
//				}
//			}
//
//			libroDiarioFacade.grabarLibroDiario(libroDiarioProvision);
//
//		} catch (BusinessException e) {
//			log.info("Error al irGenerarLibro --> " + e);
//		}
//
//	}
	
	
	/**
	 * Obtiene la fecha actual en formato AAAAMM para el libro diario.
	 * @return
	 * @throws Exception
	 */
	public Integer obtenerPeriodoActual() throws Exception {
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1;
		if (mes < 10) {
			strPeriodo = año + "0" + mes;
		} else {
			strPeriodo = año + "" + mes;
		}
		return Integer.parseInt(strPeriodo);
	}
	
	
	
	
	
	public void irModificarSolicitudActividadAutoriza(ActionEvent event){
		System.out.println("registroSeleccionadoregistroSeleccionadoregistroSeleccionado --> "+ expedienteCreditoCompSelected);
		SolicitudActividadController solicitudActividadController = (SolicitudActividadController) getSessionBean("solicitudActividadController");
		solicitudActividadController.irModificarSolicitudActividadAutoriza2(event, expedienteCreditoCompSelected);	
	}
	
	
	
	/**
	 * Valida que si con el registro de la presente autorizacion se inica los registros de liquidacion y cambio de estado.
	 * @param intTipoValidacion
	 * @param listaAutorizacionConfigurada
	 * @return
	 */
	private boolean faltaSoloUno(Integer intTipoValidacion, ConfServSolicitud autorizacionConfigurada) {
		BigDecimal bdNroUsuariosConf = null;
		BigDecimal bdNroPerfilesConf = null;
		boolean blnEsElUltimo = false;
		Integer intRecuperados = 0;
		
		try {			
			bdNroUsuariosConf = BigDecimal.ZERO;
			bdNroPerfilesConf = BigDecimal.ZERO;
			
			/*if(autorizacionConfigurada.getListaPerfil() == null || autorizacionConfigurada.getListaPerfil().isEmpty()){
				List<ConfServPerfil> lstPerfiles = null;
				lstPerfiles = confSol.getListaConfServPerfilPorCabecera(autorizacionConfigurada);
				if(lstPerfiles != null && !lstPerfiles.isEmpty()){
					autorizacionConfigurada.setListaPerfil(lstPerfiles);
				}
			}
			if(autorizacionConfigurada.getListaUsuario() == null || autorizacionConfigurada.getListaUsuario().isEmpty()){
				List<ConfServUsuario> lstUsuario = null;
				lstUsuario = confSol.getListaConfServUsuarioPorCabecera(autorizacionConfigurada);
				if(lstUsuario != null && !lstUsuario.isEmpty()){
					autorizacionConfigurada.setListaUsuario(lstUsuario);
				}
			}*/
			
			if(autorizacionConfigurada.getListaPerfil() != null && !autorizacionConfigurada.getListaPerfil().isEmpty()){
				bdNroPerfilesConf = bdNroPerfilesConf.add(new BigDecimal(autorizacionConfigurada.getListaPerfil().size()));	
			}
			
			if(autorizacionConfigurada.getListaUsuario() != null && !autorizacionConfigurada.getListaUsuario().isEmpty()){
				bdNroUsuariosConf = bdNroUsuariosConf.add(new BigDecimal(autorizacionConfigurada.getListaUsuario().size()));	
			}
			
			refrescarEncargadosAutorizar();
			intRecuperados = listaAutorizaActividadComp.size();
	
			if (intTipoValidacion.compareTo(1)==0) { // perfil
				if (bdNroPerfilesConf.compareTo(new BigDecimal(intRecuperados)) == 1) {
					blnEsElUltimo = true;
				}
			} else if (intTipoValidacion.compareTo(0)== 0) { // usuario
				if (bdNroUsuariosConf.compareTo(new BigDecimal(intRecuperados)) == 1) {
					blnEsElUltimo = true;
				}
			}
		} catch (Exception e) {
			log.error("Error en faltaSoloUno ---> "+e);
		} 
		return blnEsElUltimo;
	}
	
	/**
	 * ********************************************************** GETTERS Y SETTERS ********************************************************************
	 */
	
	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		AutorizacionActividadController.log = log;
	}

	public Integer getIntBusqTipoSolicitud() {
		return intBusqTipoSolicitud;
	}

	public void setIntBusqTipoSolicitud(Integer intBusqTipoSolicitud) {
		this.intBusqTipoSolicitud = intBusqTipoSolicitud;
	}

	public List<Tabla> getListaValidarDatosTipoSolicitud() {
		return listaValidarDatosTipoSolicitud;
	}

	public void setListaValidarDatosTipoSolicitud(
			List<Tabla> listaValidarDatosTipoSolicitud) {
		this.listaValidarDatosTipoSolicitud = listaValidarDatosTipoSolicitud;
	}

	public Integer getIntBusqTipoActividad() {
		return intBusqTipoActividad;
	}

	public void setIntBusqTipoActividad(Integer intBusqTipoActividad) {
		this.intBusqTipoActividad = intBusqTipoActividad;
	}

	public List<Tabla> getListaTiposActividad() {
		return listaTiposActividad;
	}

	public void setListaTiposActividad(List<Tabla> listaTiposActividad) {
		this.listaTiposActividad = listaTiposActividad;
	}

	public Integer getIntBusqTipoSubActividad() {
		return intBusqTipoSubActividad;
	}

	public void setIntBusqTipoSubActividad(Integer intBusqTipoSubActividad) {
		this.intBusqTipoSubActividad = intBusqTipoSubActividad;
	}

	public List<Credito> getListaBusqTiposSubActividad() {
		return listaBusqTiposSubActividad;
	}

	public void setListaBusqTiposSubActividad(
			List<Credito> listaBusqTiposSubActividad) {
		this.listaBusqTiposSubActividad = listaBusqTiposSubActividad;
	}

	public List<ExpedienteCreditoComp> getListaAutorizacionCreditoComp() {
		return listaAutorizacionCreditoComp;
	}

	public void setListaAutorizacionCreditoComp(
			List<ExpedienteCreditoComp> listaAutorizacionCreditoComp) {
		this.listaAutorizacionCreditoComp = listaAutorizacionCreditoComp;
	}

	public List<Sucursal> getListSucursal() {
		return listSucursal;
	}

	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}

	public ConfSolicitudFacadeLocal getSolicitudFacade() {
		return solicitudFacade;
	}

	public void setSolicitudFacade(ConfSolicitudFacadeLocal solicitudFacade) {
		this.solicitudFacade = solicitudFacade;
	}

	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}

	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
	}

	public SocioFacadeRemote getSocioFacade() {
		return socioFacade;
	}

	public void setSocioFacade(SocioFacadeRemote socioFacade) {
		this.socioFacade = socioFacade;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public PrevisionFacadeRemote getPrevisionFacade() {
		return previsionFacade;
	}

	public void setPrevisionFacade(PrevisionFacadeRemote previsionFacade) {
		this.previsionFacade = previsionFacade;
	}

	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}

	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}

	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}

	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}

	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}

	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}

	public AutorizacionPrevisionFacadeRemote getAutorizacionFacade() {
		return autorizacionFacade;
	}

	public void setAutorizacionFacade(
			AutorizacionPrevisionFacadeRemote autorizacionFacade) {
		this.autorizacionFacade = autorizacionFacade;
	}
	
	public List<ConfServSolicitud> getListaAutorizacionConfigurada() {
		return listaAutorizacionConfigurada;
	}



	public void setListaAutorizacionConfigurada(
			List<ConfServSolicitud> listaAutorizacionConfigurada) {
		this.listaAutorizacionConfigurada = listaAutorizacionConfigurada;
	}



	public SolicitudPrestamoService getSolicitudPrestamoService() {
		return solicitudPrestamoService;
	}



	public void setSolicitudPrestamoService(
			SolicitudPrestamoService solicitudPrestamoService) {
		this.solicitudPrestamoService = solicitudPrestamoService;
	}



	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/*
	public AutorizaVerificacion getBeanAutorizaVerificacion() {
		return beanAutorizaVerificacion;
	}

	public void setBeanAutorizaVerificacion(
			AutorizaVerificacion beanAutorizaVerificacion) {
		this.beanAutorizaVerificacion = beanAutorizaVerificacion;
	}
	*/
	public ExpedienteCreditoComp getExpedienteCreditoCompSelected() {
		return expedienteCreditoCompSelected;
	}

	public void setExpedienteCreditoCompSelected(
			ExpedienteCreditoComp expedienteCreditoCompSelected) {
		this.expedienteCreditoCompSelected = expedienteCreditoCompSelected;
	}

	public Boolean getFormAutorizacionActividadRendered() {
		return formAutorizacionActividadRendered;
	}

	public void setFormAutorizacionActividadRendered(
			Boolean formAutorizacionActividadRendered) {
		this.formAutorizacionActividadRendered = formAutorizacionActividadRendered;
	}

	public String getStrTxtMsgPerfil() {
		return strTxtMsgPerfil;
	}

	public void setStrTxtMsgPerfil(String strTxtMsgPerfil) {
		this.strTxtMsgPerfil = strTxtMsgPerfil;
	}

	public String getStrTxtMsgUsuario() {
		return strTxtMsgUsuario;
	}

	public void setStrTxtMsgUsuario(String strTxtMsgUsuario) {
		this.strTxtMsgUsuario = strTxtMsgUsuario;
	}

	public String getStrTxtMsgVigencia() {
		return strTxtMsgVigencia;
	}

	public void setStrTxtMsgVigencia(String strTxtMsgVigencia) {
		this.strTxtMsgVigencia = strTxtMsgVigencia;
	}

	public String getStrTxtMsgError() {
		return strTxtMsgError;
	}

	public void setStrTxtMsgError(String strTxtMsgError) {
		this.strTxtMsgError = strTxtMsgError;
	}

	public String getStrTxtMsgValidacion() {
		return strTxtMsgValidacion;
	}

	public void setStrTxtMsgValidacion(String strTxtMsgValidacion) {
		this.strTxtMsgValidacion = strTxtMsgValidacion;
	}

	public List<RequisitoCreditoComp> getListaRequisitoCreditoComp() {
		return listaRequisitoCreditoComp;
	}

	public void setListaRequisitoCreditoComp(
			List<RequisitoCreditoComp> listaRequisitoCreditoComp) {
		this.listaRequisitoCreditoComp = listaRequisitoCreditoComp;
	}

	public Integer getIntParaTipoDescripcion() {
		return intParaTipoDescripcion;
	}

	public void setIntParaTipoDescripcion(Integer intParaTipoDescripcion) {
		this.intParaTipoDescripcion = intParaTipoDescripcion;
	}

	public Integer getIntParaTipoOperacionPersona() {
		return intParaTipoOperacionPersona;
	}

	public void setIntParaTipoOperacionPersona(Integer intParaTipoOperacionPersona) {
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;
	}

	public List<Tabla> getListaTipoOperacion() {
		return listaTipoOperacion;
	}

	public void setListaTipoOperacion(List<Tabla> listaTipoOperacion) {
		this.listaTipoOperacion = listaTipoOperacion;
	}



	public SolicitudPrestamoFacadeRemote getSolicitudPrestamoFacade() {
		return solicitudPrestamoFacade;
	}



	public void setSolicitudPrestamoFacade(
			SolicitudPrestamoFacadeRemote solicitudPrestamoFacade) {
		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
	}



	public boolean isShowAutorizacionActividad() {
		return showAutorizacionActividad;
	}



	public void setShowAutorizacionActividad(boolean showAutorizacionActividad) {
		this.showAutorizacionActividad = showAutorizacionActividad;
	}
	
	public List<AutorizaCreditoComp> getListaAutorizaActividadComp() {
		return listaAutorizaActividadComp;
	}



	public void setListaAutorizaActividadComp(
			List<AutorizaCreditoComp> listaAutorizaActividadComp) {
		this.listaAutorizaActividadComp = listaAutorizaActividadComp;
	}



	public List<Tabla> getListaOperacionAutorizacion() {
		return listaOperacionAutorizacion;
	}



	public void setListaOperacionAutorizacion(List<Tabla> listaOperacionAutorizacion) {
		this.listaOperacionAutorizacion = listaOperacionAutorizacion;
	}



	public List<Tabla> getListaTipoOperacionAut() {
		return listaTipoOperacionAut;
	}



	public void setListaTipoOperacionAut(List<Tabla> listaTipoOperacionAut) {
		this.listaTipoOperacionAut = listaTipoOperacionAut;
	}



	public Boolean getBlnSolicitud() {
		return blnSolicitud;
	}



	public void setBlnSolicitud(Boolean blnSolicitud) {
		this.blnSolicitud = blnSolicitud;
	}



	public Boolean getBlnAutorizacion() {
		return blnAutorizacion;
	}



	public void setBlnAutorizacion(Boolean blnAutorizacion) {
		this.blnAutorizacion = blnAutorizacion;
	}



	public LibroDiarioFacadeRemote getLibroDiarioFacade() {
		return libroDiarioFacade;
	}



	public void setLibroDiarioFacade(LibroDiarioFacadeRemote libroDiarioFacade) {
		this.libroDiarioFacade = libroDiarioFacade;
	}



	public Integer getIntParaEstadoAutorizar() {
		return intParaEstadoAutorizar;
	}



	public void setIntParaEstadoAutorizar(Integer intParaEstadoAutorizar) {
		this.intParaEstadoAutorizar = intParaEstadoAutorizar;
	}



	public Integer getIntParaTipoAureobCod() {
		return intParaTipoAureobCod;
	}



	public void setIntParaTipoAureobCod(Integer intParaTipoAureobCod) {
		this.intParaTipoAureobCod = intParaTipoAureobCod;
	}



	public AutorizaCredito getBeanAutoriza() {
		return beanAutoriza;
	}



	public void setBeanAutoriza(AutorizaCredito beanAutoriza) {
		this.beanAutoriza = beanAutoriza;
	}



	public String getStrObservacion() {
		return strObservacion;
	}



	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}



	public CreditoFacadeRemote getCreditoFacade() {
		return creditoFacade;
	}



	public void setCreditoFacade(CreditoFacadeRemote creditoFacade) {
		this.creditoFacade = creditoFacade;
	}



	public String getStrConfiguracionesEvaluadas() {
		return strConfiguracionesEvaluadas;
	}



	public void setStrConfiguracionesEvaluadas(String strConfiguracionesEvaluadas) {
		this.strConfiguracionesEvaluadas = strConfiguracionesEvaluadas;
	}



	public ConfServSolicitud getAutorizacionConfiguradaFinal() {
		return autorizacionConfiguradaFinal;
	}



	public void setAutorizacionConfiguradaFinal(
			ConfServSolicitud autorizacionConfiguradaFinal) {
		this.autorizacionConfiguradaFinal = autorizacionConfiguradaFinal;
	}



	public Boolean getFormAutorizacionActividadViewRendered() {
		return formAutorizacionActividadViewRendered;
	}



	public void setFormAutorizacionActividadViewRendered(
			Boolean formAutorizacionActividadViewRendered) {
		this.formAutorizacionActividadViewRendered = formAutorizacionActividadViewRendered;
	}



	public boolean isBlnMostrarVer() {
		return blnMostrarVer;
	}



	public void setBlnMostrarVer(boolean blnMostrarVer) {
		this.blnMostrarVer = blnMostrarVer;
	}



	public boolean isBlnMostrarAutorizar() {
		return blnMostrarAutorizar;
	}



	public void setBlnMostrarAutorizar(boolean blnMostrarAutorizar) {
		this.blnMostrarAutorizar = blnMostrarAutorizar;
	}



	public String getStrSolicitudAutorizacion() {
		return strSolicitudAutorizacion;
	}



	public void setStrSolicitudAutorizacion(String strSolicitudAutorizacion) {
		this.strSolicitudAutorizacion = strSolicitudAutorizacion;
	}



	public Integer getIntBusqTipo() {
		return intBusqTipo;
	}



	public void setIntBusqTipo(Integer intBusqTipo) {
		this.intBusqTipo = intBusqTipo;
	}



	public String getStrBusqCadena() {
		return strBusqCadena;
	}



	public void setStrBusqCadena(String strBusqCadena) {
		this.strBusqCadena = strBusqCadena;
	}



	public String getStrBusqNroSol() {
		return strBusqNroSol;
	}



	public void setStrBusqNroSol(String strBusqNroSol) {
		this.strBusqNroSol = strBusqNroSol;
	}



	public Integer getIntBusqSucursal() {
		return intBusqSucursal;
	}



	public void setIntBusqSucursal(Integer intBusqSucursal) {
		this.intBusqSucursal = intBusqSucursal;
	}



	public Integer getIntBusqEstado() {
		return intBusqEstado;
	}



	public void setIntBusqEstado(Integer intBusqEstado) {
		this.intBusqEstado = intBusqEstado;
	}



	public Date getDtBusqFechaEstadoDesde() {
		return dtBusqFechaEstadoDesde;
	}



	public void setDtBusqFechaEstadoDesde(Date dtBusqFechaEstadoDesde) {
		this.dtBusqFechaEstadoDesde = dtBusqFechaEstadoDesde;
	}



	public Date getDtBusqFechaEstadoHasta() {
		return dtBusqFechaEstadoHasta;
	}



	public void setDtBusqFechaEstadoHasta(Date dtBusqFechaEstadoHasta) {
		this.dtBusqFechaEstadoHasta = dtBusqFechaEstadoHasta;
	}



	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}



	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}



	public Integer getIntBusqTipoCreditoEmpresa() {
		return intBusqTipoCreditoEmpresa;
	}



	public void setIntBusqTipoCreditoEmpresa(Integer intBusqTipoCreditoEmpresa) {
		this.intBusqTipoCreditoEmpresa = intBusqTipoCreditoEmpresa;
	}



	public List<Tabla> getListaTablaCreditoEmpresa() {
		return listaTablaCreditoEmpresa;
	}



	public void setListaTablaCreditoEmpresa(List<Tabla> listaTablaCreditoEmpresa) {
		this.listaTablaCreditoEmpresa = listaTablaCreditoEmpresa;
	}



	public List<Tabla> getListaTablaDescConfCredito() {
		return listaTablaDescConfCredito;
	}



	public void setListaTablaDescConfCredito(List<Tabla> listaTablaDescConfCredito) {
		this.listaTablaDescConfCredito = listaTablaDescConfCredito;
	}



	public Integer getIntBusqItemCredito() {
		return intBusqItemCredito;
	}



	public void setIntBusqItemCredito(Integer intBusqItemCredito) {
		this.intBusqItemCredito = intBusqItemCredito;
	}



	public List<Tabla> getListaEstadoSolicitud() {
		return listaEstadoSolicitud;
	}



	public void setListaEstadoSolicitud(List<Tabla> listaEstadoSolicitud) {
		this.listaEstadoSolicitud = listaEstadoSolicitud;
	}

	
}
