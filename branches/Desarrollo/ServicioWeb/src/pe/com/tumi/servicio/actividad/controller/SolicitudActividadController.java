package pe.com.tumi.servicio.actividad.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.MyFile;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
//import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.creditos.facade.CreditoGarantiaFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CronogramaId;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
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
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteActividad;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CronogramaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.PrestamoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;


public class SolicitudActividadController {
	
	protected static Logger log = Logger.getLogger(SolicitudActividadController.class);
	
	// sesion
	private Usuario 	usuario;
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	private Integer		SUCURSAL_USUARIO_ID;
	private Integer		SUBSUCURSAL_USUARIO_ID;
	
	private Date dtHoy = null;
	private Calendar fecHoy = Calendar.getInstance();
	private Date dtFechaRegistro;
	private Calendar calFechaRegistro = Calendar.getInstance();
	private ExpedienteCredito beanExpedienteCredito;
	private Credito beanCredito;
	
	private TablaFacadeRemote tablaFacade;
	private SocioFacadeRemote socioFacade;
	private CreditoFacadeRemote creditoFacade;
	private EstructuraFacadeRemote estructuraFacade;
	private ConvenioFacadeRemote convenioFacade;
	private SolicitudPrestamoFacadeRemote solicitudPrestamoFacade;
	private ConceptoFacadeRemote conceptofacade;
	private PersonaFacadeRemote personaFacade;
	private GeneralFacadeRemote generalFacade;
	private PlanillaFacadeRemote planillaFacade;
	private CreditoGarantiaFacadeRemote creditoGarantiaFacade;
	private CuentaFacadeRemote cuentaFacade;
	private PrestamoFacadeRemote prestamoFacade;
	
	
	private List<Tabla> listaTipoOperacion;
	private List<Tabla> listaTipoRelacion;
	private Integer intValidarDatosTipoSolicitud;
	private String strSubTipoOperacion;
	private Persona personaValida;
	private String strMsgErrorValidarDatos;
	private List<Tabla> listaEstadoPrestamo;
	private String strUltimoEstado;
	
	private List<Tabla> listaValidarDatosTipoSolicitud;
	
	private List<ExpedienteCreditoComp> listaExpedienteCreditoComp;
	private ExpedienteCreditoComp  registroSeleccionadoBusqueda;
	
	
	//Manejo de formularios
	private Boolean blnShowDivFormSolicitudActividad;
	private String strSolicitudActividad;
	private Boolean blnShowValidarDatos;
	
	
	// Validar datos
	private Integer intTipoRelacion; 
	private SocioComp beanSocioComp;
	private Boolean pgValidDatos;
	private Boolean blnDatosSocio;
	private Estructura beanEstructuraSocioComp;
	private List<Tabla>	listDocumentoBusq;
	private Integer intTipoDeSolicitud; 
	
	private boolean blnTipoDeSolicitud;
	// Tabs de Solicitud de Actividad
	private Boolean blnSolicitud;
	private Boolean blnAutorizacion;
	
	
	// Dependencias
	//private List<CapacidadCreditoComp> listaCapacidadCreditoComp;
	private List<Estructura> listEstructura;
	private List<Tabla> listaDescTipoCreditoEmpresa;
	private List<Sucursal> listSucursal;
	
	// eVALUACION
	private String strMsgErrorPreEvaluacion;
	boolean blnPostEvaluacion;	
	private String strMsgErrorPreEvaluacionCondicionLaboral;
	private String strMsgErrorPreEvaluacionCondicion;
	private String strMsgErrorPreEvaluacionCondicionHabil;
	private String strMsgErrorPreEvaluacionTipoSocio;
	
	
	private String msgTxtObservacion;
	private String strMsgCondicionSocioCapacidadPago;
	
	private Integer intMiOperacion;
	
	private List<Estructura> listaDependenciasSocio;
	private String strMsgCondicionCapacidadPagoSocio;
	
	private List<Tabla> listaTablaDescripcionAdjuntos;
	private List<RequisitoCreditoComp> listaRequisitoCreditoComp;
	private Integer intNroCuotas;
	

	private Integer intTipoActividad;
	/**
	 * relacionado con itemcredito de conf creditos (es unico).
	 */
	private Integer intTipoSubActividad;
	private List<Credito> listaTiposSubActividad;
	private List<Tabla> listaTiposActividad;
	private String strMontoACancelar;
	
	private List<Tabla> listaCondicionSocio;
	private List<Tabla> listaTipoCondicionSocio;
	private String strCondicionSocio;
	private boolean blnHayError;
	private List<CronogramaCreditoComp> listaCronogramaCreditoComp;
	private Boolean blnCronogramaNormal;
	
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	//private Integer intMiOperacion;
	private BigDecimal bdMontoDeCuotas;
	private Integer intTipoSolicitudSubActividad; // Subtipo de actidad
	private BigDecimal bdMontoCancelar;
	private Boolean blnCronograma;
	private Boolean blnMontoCancelar;
	private boolean blnMostrarDescripciones ;
	private String strDescripcionTipoActividad;
	private String strDescripcionTipoSubActividad;
	
	private Integer intBusqTipoSolicitud;
	//private Integer intBusqTipoActividad;
	//private Integer intBusqTipoSubActividad;
	
	private List<Credito> listaBusqTiposSubActividad;
	private List<Credito> listaGeneralSubtipoTipoActividad;
	
	private String strUnidadesEjecutorasConcatenadas;
	private boolean blnMostrarEliminar;
	private boolean blnMostrarModificar;
	
	private CreditoInteres creditoInteresExpediente;
	private List<Tabla> listaDescripcionModalidad;
	private List<Tabla> listaDescripcionTipoSocio;
	
	// BUSQUEDA - FILTROS - CGD
	private Integer intBusqTipo;
	private String strBusqCadena;
	private String strBusqNroSol;
	private Integer intBusqTipoActividad;
	private Integer intBusqTipoSubActividad;
	private Integer intBusqSucursal;
	private Integer intBusqEstado;
	private Date dtBusqFechaEstadoDesde;
	private Date dtBusqFechaEstadoHasta;
	private	List<Sucursal> listaSucursal;
	private Integer intBusqTipoCreditoEmpresa;
	private List<Tabla> listaTablaCreditoEmpresa;
	private List<Tabla> listaTablaDescConfCredito;
	private EmpresaFacadeRemote empresaFacade = null;
	private Integer intBusqItemCredito;


	public SolicitudActividadController() {
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario != null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	/**
	 * Carga datos del usuario logueado
	 */
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
		SUCURSAL_USUARIO_ID = usuario.getSucursal().getId().getIntIdSucursal();
		SUBSUCURSAL_USUARIO_ID = usuario.getSubSucursal().getId().getIntIdSubSucursal();		
	}
	
	/**
	 * Carga los valores iniciales, facades, listas y combos
	 */
	private void cargarValoresIniciales(){
		cargarUsuario();
		//CARGA DE PARAMETROS DE BUSQUEDA
		
		dtHoy = fecHoy.getTime();

		strSolicitudActividad = "";

		personaValida = new Persona();
		personaValida.setDocumento(new Documento());
		pgValidDatos = false;
		blnDatosSocio = false;
		
		beanSocioComp = new SocioComp();
		beanSocioComp.setPersona(new Persona());
		beanSocioComp.getPersona().setNatural(new Natural());
		beanSocioComp.getPersona().getNatural().setPerLaboral(new PerLaboral());
		beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
		beanSocioComp.getPersona().setDocumento(new Documento());
		beanSocioComp.getPersona().setPersonaEmpresa(new PersonaEmpresa());
		beanSocioComp.getPersona().getPersonaEmpresa().setVinculo(new Vinculo());
		beanSocioComp.setPersonaRol(new PersonaRol());
		beanSocioComp.getPersonaRol().setId(new PersonaRolPK());
		beanSocioComp.setSocio(new Socio());
		beanSocioComp.getSocio().setSocioEstructura(new SocioEstructura());
		beanSocioComp.setCuenta(new Cuenta());
		beanSocioComp.setCuentaComp(new CuentaComp());

		beanExpedienteCredito = new ExpedienteCredito();
		beanExpedienteCredito.setId(new ExpedienteCreditoId());
		beanExpedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());
		beanExpedienteCredito.setListaCronogramaCredito(new ArrayList<CronogramaCredito>());
		beanExpedienteCredito.setListaGarantiaCredito(new ArrayList<GarantiaCredito>());
		beanExpedienteCredito.setEstadoCreditoUltimo(new EstadoCredito());
		beanExpedienteCredito.setListaExpedienteActividad(new ArrayList<ExpedienteActividad>());
		
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		
		blnPostEvaluacion = false;
		
		strUltimoEstado = "";
		blnCronograma = Boolean.FALSE;
		
		limpiarMensajes();
		limpiarFormSolicitudActividad();
		try {
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			conceptofacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			convenioFacade = (ConvenioFacadeRemote)EJBFactory.getRemote(ConvenioFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			planillaFacade = (PlanillaFacadeRemote)EJBFactory.getRemote(PlanillaFacadeRemote.class);
			creditoGarantiaFacade = (CreditoGarantiaFacadeRemote)EJBFactory.getRemote(CreditoGarantiaFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			prestamoFacade = (PrestamoFacadeRemote)EJBFactory.getRemote(PrestamoFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);

			listaTipoRelacion =tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "E");
			//listaTipoOperacion =tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONPRESTAMO), "B");
			listaValidarDatosTipoSolicitud = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_SUBOPERACIONACTIVIDADES), "A");
			listaDescTipoCreditoEmpresa = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA));
			//listaMotivoPrestamo = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_FINALIDAD_CREDITO));
			listaEstadoPrestamo = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO));
			listDocumentoBusq = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO));
			listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDADES));
			listaTiposActividad  = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 3);
			
			listaCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
			listaTipoCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CONDSOCIO));
			//listaGeneralSubtipoTipoActividad = creditoFacade.getlistaCreditoPorCredito(new Credito());

			listaDescripcionModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
			listaDescripcionTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			ordenarAlfabeticamenteSuc();
			listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 5);

			cargarDescripcionConfCreditos();
			
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
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
			log.info(""+e.getMessage(),e);
		}
		
	}
	
	
	/**
	 * Metodo de limpieza asociado a h:outputText de nuevoCreditoBody.jsp
	 * Ayuda a controlar los tabs de acuerdo a perfiles de usuario logueado.
	 * @return
	 */
	public String  getLimpiarSolicitud(){
		cargarUsuario();
		cargarPermisos();
		limpiarFormSolicitudActividad();
		limpiarFiltros(null);
		//limpiarDatosBusq(null);
		limpiarCronograma();
		pgValidDatos = false;
		blnDatosSocio = false;
		listaExpedienteCreditoComp = new ArrayList<ExpedienteCreditoComp>();
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
    * 
	*/
	public void limpiarMensajes(){
		strMsgErrorPreEvaluacion = "";
		strMsgErrorPreEvaluacionCondicionLaboral="";
		strMsgErrorPreEvaluacionCondicion="";
		strMsgErrorPreEvaluacionCondicionHabil="";
		strMsgErrorPreEvaluacionTipoSocio="";
		strUltimoEstado = "";
	}
	
	/**
	 * Limpia los campos del formulario
	 */
	public void limpiarFormSolicitudActividad() {

		Calendar fecHoy = Calendar.getInstance();
		dtHoy = fecHoy.getTime();
		blnPostEvaluacion = false;
		strSolicitudActividad = "";

		personaValida = new Persona();
		personaValida.setDocumento(new Documento());
		pgValidDatos = false;
		blnDatosSocio = false;
		
		beanSocioComp = new SocioComp();
		beanSocioComp.setPersona(new Persona());
		beanSocioComp.getPersona().setNatural(new Natural());
		beanSocioComp.getPersona().getNatural().setPerLaboral(new PerLaboral());
		beanSocioComp.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
		beanSocioComp.getPersona().setDocumento(new Documento());
		beanSocioComp.getPersona().setPersonaEmpresa(new PersonaEmpresa());
		beanSocioComp.getPersona().getPersonaEmpresa().setVinculo(new Vinculo());
		beanSocioComp.setPersonaRol(new PersonaRol());
		beanSocioComp.getPersonaRol().setId(new PersonaRolPK());
		beanSocioComp.setSocio(new Socio());
		beanSocioComp.getSocio().setSocioEstructura(new SocioEstructura());
		beanSocioComp.setCuenta(new Cuenta());
		beanSocioComp.setCuentaComp(new CuentaComp());
		beanExpedienteCredito = new ExpedienteCredito();
		beanExpedienteCredito.setId(new ExpedienteCreditoId());
		beanExpedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());
		beanExpedienteCredito.setListaCronogramaCredito(new ArrayList<CronogramaCredito>());
		beanExpedienteCredito.setListaGarantiaCredito(new ArrayList<GarantiaCredito>());
		beanExpedienteCredito.setEstadoCreditoUltimo(new EstadoCredito());
		beanExpedienteCredito.setListaExpedienteActividad(new ArrayList<ExpedienteActividad>());
		
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		blnHayError = Boolean.FALSE;
		strUltimoEstado = "";
		intMiOperacion = 1; // 1: nuevo, 2: modifica.
		limpiarMensajes();
		blnCronograma = Boolean.FALSE;
		intValidarDatosTipoSolicitud = 0;
		strMontoACancelar ="0.00";
		
		intTipoActividad = 0;
		intTipoSubActividad = 0;
		listaTiposSubActividad=new ArrayList<Credito>();
		
		blnMontoCancelar = Boolean.FALSE;
		blnMostrarDescripciones = Boolean.FALSE;
		strUnidadesEjecutorasConcatenadas = "";
		blnMostrarEliminar = Boolean.TRUE;
		blnMostrarModificar = Boolean.TRUE;
	}
	
	/**
	 * Buscar los expedientes de actividades
	 * @param event
	 */
	public void buscarExpedientesActividad(ActionEvent event) {
		SolicitudPrestamoFacadeLocal facade = null;
		cancelarGrabarSolicitud(event);
		ExpedienteCreditoComp expedienteCompBusq = null;
		try {
			facade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			/*ExpedienteCreditoComp o = new ExpedienteCreditoComp();
			
			listaExpedienteCreditoComp = facade.getListaExpedienteActividadCompDeBusqueda(o);*/
			listaExpedienteCreditoComp= new ArrayList<ExpedienteCreditoComp>();
			
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

			listaExpedienteCreditoComp = facade.getListaBusqActividadFiltros(expedienteCompBusq);
			
		} catch (BusinessException e) {
			System.out.println("Error BusinessException ---> "+e);
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			System.out.println("Error EJBFactoryException ---> "+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param event
	 */
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
	 * Metodo que se ejecuta al presionar CANCELAR. Limpia todo.
	 * @param event
	 */
	public void cancelarGrabarSolicitud(ActionEvent event) {
		limpiarFormSolicitudActividad();
		limpiarMensajesIsValido();

		blnShowValidarDatos = false;
		dtFechaRegistro = Calendar.getInstance().getTime();		
		//strSolicitudRefinan = Constante.MANTENIMIENTO_MODIFICAR;
		blnShowDivFormSolicitudActividad = false;
		//limpiarFormSolicitudRefinan();
		limpiarMensajes();
		//limpiarMensajesIsValido();
		 strSolicitudActividad =  "";
		 blnCronogramaNormal =  Boolean.FALSE;
		/*listaBeneficiarioLiquidacionBusq = new ArrayList<BeneficiarioLiquidacion>();*/
}
	
	
	/**
	 * Metodo que se ejecuta al seleccionar un registro de la grilla de busqueda
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		try{
			
			registroSeleccionadoBusqueda = (ExpedienteCreditoComp)event.getComponent().getAttributes().get("itemExpAct");
			log.info("reg selec:"+registroSeleccionadoBusqueda);	
			validarOperacionEliminar();
			validarOperacionModificar();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * Valida si la operacion ELIMINAR se visualiza o no en el popup de acciones
	 */
	public void validarOperacionEliminar(){
		Integer intUltimoEstado = 0;
		
		try {
			
			if(registroSeleccionadoBusqueda != null){
				intUltimoEstado = registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo();
				if(intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)!=0){
					blnMostrarEliminar = Boolean.FALSE;
				}else{
					blnMostrarEliminar = Boolean.TRUE;
				}	
			}
	
		} catch (Exception e) {
			log.error("Error en validarOperacionEliminar ---> "+e);
		}

	}
	
	
	/**
	 * Valida si la operacion MODIFICAR se visualiza o no en el popup de acciones
	 */
	public void validarOperacionModificar(){
		Integer intUltimoEstado = 0;
		try {
			
			if(registroSeleccionadoBusqueda != null){
				intUltimoEstado = registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo();
				
				if(intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
						|| intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
					blnMostrarModificar = Boolean.TRUE;
				}else{
					blnMostrarModificar = Boolean.FALSE;
				}
			}	
		} catch (Exception e) {
			log.error("Error en validarOperacionModificar ---> "+e);
		}

	}
	
	
	
	/**
	 * Metodo que se ejecuta al presionar NUEVO. Limpia todo.
	 * @param event
	 */
	public void nuevaSolicitudActividad(ActionEvent event) {
		cancelarGrabarSolicitud(event);	
		strSolicitudActividad = Constante.MANTENIMIENTO_MODIFICAR;
		blnShowValidarDatos = true;
		dtFechaRegistro = Calendar.getInstance().getTime();
		blnShowDivFormSolicitudActividad = false;
		limpiarFormSolicitudActividad();
		limpiarMensajes();
		limpiarMensajesIsValido();
		intMiOperacion = 1;

	}
	
	
	/**
	 * Metodo que limpia todos campos de mensajes 
	 */
	public void limpiarMensajesIsValido() {
		strMsgCondicionSocioCapacidadPago="";
		strMsgErrorValidarDatos = "";
		strMsgCondicionCapacidadPagoSocio = "";
		strMsgErrorPreEvaluacion = "";
		strMsgErrorPreEvaluacionCondicionLaboral="";
		strMsgErrorPreEvaluacionCondicion="";
		strMsgErrorPreEvaluacionCondicionHabil="";
		strMsgErrorPreEvaluacionTipoSocio="";
	}
	
	
	/**
	 * Carga el 2do combo de Tipo de actividad segun 1er combo seleccionado
	 * @param event
	 */
	public void cargarComboSubActividad(ActionEvent event)  {
		String strIntTipoActividad = null;
		List<Credito> lstCredito = null;
		List<Credito> lstCreditoTemporal = null;
		Timestamp tsFechaInicio = new Timestamp(0);
		Timestamp tsFechaFin = new Timestamp(0);
		Timestamp tsHoy = new Timestamp(0);
		try {
			
			tsHoy = new Timestamp(new Date().getTime());
			listaTiposSubActividad = new ArrayList<Credito>();
			
			strIntTipoActividad = getRequestParameter("pIntTipoActividad");
			if(!strIntTipoActividad.equals("0")){
				Credito creditoAct = new Credito();
				CreditoId id = new CreditoId();
				creditoAct.setId(id);
				creditoAct.setIntParaTipoCreditoEmpresa(new Integer(strIntTipoActividad));
				
				lstCredito = creditoFacade.getlistaCreditoPorCredito(creditoAct);
				
				// VALIDAR FECHAS
				if(lstCredito != null && !lstCredito.isEmpty()){
					for (Credito credito : lstCredito) {
						Boolean blnAprobado = Boolean.FALSE;
						lstCreditoTemporal = new ArrayList<Credito>();
						
						tsFechaInicio = convierteStringATimestamp(credito.getStrDtFechaIni());
						
						if(tsHoy.after(tsFechaInicio)){
							blnAprobado = Boolean.TRUE;
							if(credito.getStrDtFechaFin() != null && credito.getStrDtFechaFin().length()>0){
								tsFechaFin = convierteStringATimestamp(credito.getStrDtFechaFin());
								if(tsHoy.before(tsFechaFin)){
									blnAprobado = Boolean.TRUE;
								}else{
									blnAprobado = Boolean.FALSE;
								}
							}
						}
						
						if(blnAprobado){
							lstCreditoTemporal.add(credito);
						}
					}

					if(!lstCreditoTemporal.isEmpty()){
						setListaTiposSubActividad(lstCreditoTemporal);
					}else{
						setListaTiposSubActividad(null);
					}
				}

			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Carga el 2do combo de Tipo de actividad segun 1er combo seleccionado
	 * @param event
	 */
	@SuppressWarnings("deprecation")
	public void cargarComboSubActividadBusqueda(ActionEvent event)  {
		log.info("pIntTipoActividadBusq: "+getRequestParameter("pIntBusqTipoActividad"));
		String strIntTipoActividadBusq = null;
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
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Se valida la actividad que se palica al socio
	 * @param credito
	 * @return
	 */

	public boolean validarMontoACancelarVSCredito(Credito actividad){
		boolean blnPuedeEvaluar = Boolean.TRUE;
		
		try {
			
			if(intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
				if(strMontoACancelar != null && !strMontoACancelar.equalsIgnoreCase("0.00") && strMontoACancelar.length()>0){
					Double dbMontoCancelar = new Double(strMontoACancelar);
					BigDecimal bdMontCanc = new BigDecimal(dbMontoCancelar);
					bdMontCanc = bdMontCanc.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
						if(creditoInteresExpediente.getBdMontoMaximo().compareTo(bdMontCanc)<0){
							blnPuedeEvaluar = Boolean.FALSE;
							beanCredito = null;
							strMsgErrorPreEvaluacion = "El Monto a Cancelar ( S/. "+ bdMontCanc + " )no puede exceder al Monto Máximo del Crédito ( S/. "+ creditoInteresExpediente.getBdMontoMaximo() +").";
						}else{
							beanCredito = actividad;
							strMsgErrorPreEvaluacion = "";
						}
				}else{
					strMsgErrorPreEvaluacion = "";
				}

			}

		} catch (Exception e) {
			log.error("Error en validarMontoACancelarVSCredito ---> "+e);
		}
		return blnPuedeEvaluar;
		
	}
	
	
	/**
	 * 
	 * @return
	 */
//	private Envioconcepto recuperarEnvios(Integer intEmpresa, Integer intCuenta, Integer intPerido){
//		Envioconcepto envio = null;
//		try {
//			envio = planillaFacade.getEnvioConceptoPorEmpPerCta(intEmpresa, intPerido, intCuenta);
//		} catch (Exception e) {
//			log.error("Error en recuperarEnvios ---> "+e);
//		}
//		return envio;
//	}
//	
	/**
	 * Restringe la visualizacion del formuulario de actividad.
	 * @param actividad
	 * @return
	 */
	private Boolean procedeEvaluacion(Credito actividad) {
		Boolean validProcedeEvaluacion = true;
		try {

			if(validarConfiguracionActividad(actividad)){
				validProcedeEvaluacion = validarMontoACancelarVSCredito(actividad);
			}else{	
				validProcedeEvaluacion = Boolean.FALSE;
			}

		} catch (Exception e) {
			log.error("Error en procedeEvaluacion ---> "+e);
		}
		return validProcedeEvaluacion;
		
		

	}
	
	
	/**
	 * Oculta o muestra texto de monto a cancelar
	 * Tambien se relaciona con el SUBTIPO DE ACTIVIDAD
	 * @param event
	 */
	public void definirTipoDeSolicitud (ActionEvent event){
		String strIntTipoSolicitud ;
		strIntTipoSolicitud = getRequestParameter("pIntValidarDatosTipoSolicitud");
		
		try {
			if(!strIntTipoSolicitud.equals("0")){
				intTipoSolicitudSubActividad = new Integer(strIntTipoSolicitud);

				// Es actividad?
				if(intTipoSolicitudSubActividad.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
					blnTipoDeSolicitud = Boolean.TRUE; // boolean q muestra u oculta campo de monto a cancelar
				}
				//Es contado?
				if(intTipoSolicitudSubActividad.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO)==0){
					blnTipoDeSolicitud = Boolean.FALSE;
					strMontoACancelar = "0.00";
				}
			}
		} catch (Exception e) {
			log.error("Error en definirTipoDeSolicitud"+e);
		}
	}
	
	/**
	 * Define si se visualiza o no el campo de Montoa  cancelar. En base al tipo. Si es Credito o Contado.
	 */
	public void definirTipoDeSolicitudMod (){
		try {

			if(intValidarDatosTipoSolicitud != null && intValidarDatosTipoSolicitud != 0){
				// Es actividad?
				if(intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
					blnTipoDeSolicitud = Boolean.TRUE;
				}
				//Es contado?
				if(intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO)==0){
					blnTipoDeSolicitud = Boolean.TRUE;//Boolean.FALSE;
					strMontoACancelar = "0.00";
				}	
			}

		} catch (Exception e) {
			log.error("Error en definirTipoDeSolicitud"+e);
		}

	}
	
	
	/**
	 * Valida y recupera los datos del socio evaluado.
	 * @param event
	 */
	public void validarDatos(ActionEvent event) {
		SocioComp socioComp = null;
		Integer intTipoDoc = personaValida.getDocumento().getIntTipoIdentidadCod();
		String strNumIdentidad = personaValida.getDocumento().getStrNumeroIdentidad();
		strNumIdentidad = strNumIdentidad.trim();
		CuentaComp cuentaComp = new CuentaComp();
		strMsgErrorValidarDatos = "";
//		String strUnidadesEjecutoras ="";
		strUnidadesEjecutorasConcatenadas = "";
		Boolean blnTipoRelacion = false;
		try {
			// De momento solo se van a definir 2 tipos: PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO = 2; PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO = 3;
			if(intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0
				//||intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_DONACION)==0 
				|| intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO)==0){
				
				if ((intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_SOCIO)
					|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_USUARIO)
					|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_CLIENTE))) {
				
					socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa( intTipoDoc, strNumIdentidad,
																						Constante.PARAM_EMPRESASESION);
				
					if (socioComp != null) {
						if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){
							if (socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()!=null && !socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().isEmpty()) {
								for (PersonaRol x : socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()) {
									if (x.getId().getIntParaRolPk().equals(intTipoRelacion)) {
										blnTipoRelacion = true;
										break;
									}								
								}
								if (blnTipoRelacion) {
									if (!(intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_NO_SOCIO))) {
										if (socioComp.getCuenta() != null) {
											
											for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
												if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
													if (socioEstructura.getIntTipoEstructura().intValue() == Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.intValue()) {
														socioComp.getSocio().setSocioEstructura(socioEstructura);
													}
												}	
											}
							
											pgValidDatos = false;
											blnDatosSocio = true;
											socioComp.setCuentaComp(cuentaComp);
											beanSocioComp = socioComp;
											
											blnShowDivFormSolicitudActividad = true;
											blnShowValidarDatos = false;
										
											strSolicitudActividad = Constante.MANTENIMIENTO_MODIFICAR;
											getListEstructura();
											cargarDescripcionUEjecutorasConcatenadas(beanSocioComp);
										} else{
											strMsgErrorValidarDatos = strMsgErrorValidarDatos + "Socio no posee Cuenta. ";
										}
									}else{
										strMsgErrorValidarDatos = "La Relación NO SOCIO no cuenta con configuración de crédito.";
									}							
								}else{
									strMsgErrorValidarDatos = "La Relación no concuerda con la configuración de la cuenta del socio.";
								}
							}
						}else {
							strMsgErrorValidarDatos = "El Socio se encuentra en estado Fallecido.";
						}
					} else {
						strMsgErrorValidarDatos = strMsgErrorValidarDatos + "Socio no ubicado. ";
					}		
				} else {
						strMsgErrorValidarDatos = strMsgErrorValidarDatos + "Tipo de relación incorrecta. ";
						pgValidDatos = true;
						blnDatosSocio = false;
				}	
			}else{
				strMsgErrorValidarDatos = strMsgErrorValidarDatos + "Tipo de solicitud incorrecta. ";
			}
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Redirecciona el tipo de grabacion a aplicar, si es Inicial(solicitud Nueva) o Modificar (solicitud ya existente).
	 * @param event
	 */
	public void grabarExpedienteActividad(ActionEvent event){
		
		try {
			
			if (isValidoExpedienteActividad(beanExpedienteCredito) == false) {
				log.info("Datos de Actividad no válidos. Se aborta el proceso de grabación de Actividad.");
				return;
			}
			
			if (intMiOperacion.compareTo(new Integer(1)) == 0) {
				grabarExpedienteActividadInicial(event);
			} else if (intMiOperacion.compareTo(new Integer(2)) == 0) {
				grabarExpedienteActividadModificar(event);
			}
			
		} catch (Exception e) {
			log.error("Error en grabarExpedienteRefinanciamiento --> "+e);
		}
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void grabarExpedienteActividadInicial(ActionEvent event){
		EstadoCredito estadoCredito = null;
		//EstadoCredito estadoRequisito = null;
		//EstadoCredito estadoSolicitud = null;
		//List<EstadoCredito> listaEstados = null;
		List<ExpedienteActividad> listaExpedienteActividad = null;
		
		try {
				beanExpedienteCredito.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
				beanExpedienteCredito.getId().setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());
				beanExpedienteCredito.setIntPersEmpresaCreditoPk(beanCredito.getId().getIntPersEmpresaPk() == null ? 0 : beanCredito.getId().getIntPersEmpresaPk());
				beanExpedienteCredito.setIntParaTipoCreditoCod(beanCredito.getId().getIntParaTipoCreditoCod()== null ? 0 : beanCredito.getId().getIntParaTipoCreditoCod());
				beanExpedienteCredito.setIntItemCredito(beanCredito.getId().getIntItemCredito() == null ? 0 : beanCredito.getId().getIntItemCredito());
				beanExpedienteCredito.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_ACTIVIDAD);
				beanExpedienteCredito.setIntParaSubTipoOperacionCod(intTipoSolicitudSubActividad);

				// 18.09.2013 - CGD
				beanExpedienteCredito.setIntPersEmpresaSucAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntEmpresaSucAdministra());
				beanExpedienteCredito.setIntSucuIdSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
				beanExpedienteCredito.setIntSudeIdSubSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSubsucurAdministra());
				
				if (isValidoExpedienteActividad(beanExpedienteCredito) == false) {
					log.info("Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.");
					return;
				}

				listaExpedienteActividad = new ArrayList<ExpedienteActividad>();
				ExpedienteActividad expAct = new ExpedienteActividad();	
				expAct.setIntParaTipoSolicitudCod(intValidarDatosTipoSolicitud);
				// de acuerdo altipo de solicitud generamos expedienteactividad...
				if (intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0) {

					if(strMontoACancelar != null && !strMontoACancelar.equalsIgnoreCase("0.00") && strMontoACancelar.length()>0){
						Double dbMontoCancelar = new Double(strMontoACancelar);
						BigDecimal bdMontCanc = new BigDecimal(dbMontoCancelar);
						expAct.setBdMontoCancelado(bdMontCanc);
					}else{
						expAct.setBdMontoCancelado(BigDecimal.ZERO);
					}
				}else{
					expAct.setBdMontoCancelado(BigDecimal.ZERO);
				}
				
				listaExpedienteActividad.add(expAct);
				beanExpedienteCredito.setListaExpedienteActividad(listaExpedienteActividad);
		
		// Validamos que todos requisitos se cumplan
			if (beanExpedienteCredito.getListaEstadoCredito() != null) {
	
				if (isValidTodaSolicitud() == 0) {
					estadoCredito = new EstadoCredito();
					estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
					estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
					estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
					estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
					estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
					beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);
	
				} else {
					// Si no se graba en estado REQUISITO
					estadoCredito = new EstadoCredito();
					estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
					estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
					estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
					estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
					estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
					beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);
				}
				// }
			}
		
			if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
			}		
			if((beanExpedienteCredito.getId().getIntItemExpediente() != null)
				&& (beanExpedienteCredito.getId().getIntItemDetExpediente() != null)){
				
				solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);				
			}else{
				beanExpedienteCredito = solicitudPrestamoFacade.grabarExpedienteCredito(beanExpedienteCredito);
			}

			if (beanExpedienteCredito.getListaRequisitoCreditoComp() != null
					&& beanExpedienteCredito.getListaRequisitoCreditoComp().size() > 0) {
				renombrarArchivo(beanExpedienteCredito.getListaRequisitoCreditoComp());
			}
			
			limpiarFormSolicitudActividad();
			
		} catch (BusinessException e) {
			log.error("Error en grabarExpedienteActividadInicial ---> "+e);
			e.printStackTrace();
		}
	}
		

	/**
	 * 
	 * @param event
	 */
	public void grabarExpedienteActividadModificar(ActionEvent event){
			EstadoCredito estadoCredito = null;
			//List<GarantiaCredito> listaGarantiaCredito = null;

			try {
				
				if (isValidTodaSolicitud() == 0) {
					EstadoCredito ultimo = null;
					
					ultimo = solicitudPrestamoFacade.ultimoEstadoCredito(beanExpedienteCredito);
					if(ultimo.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
							|| ultimo.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
						estadoCredito = new EstadoCredito();
						estadoCredito.setId(ultimo.getId());
						estadoCredito.getId().setIntItemEstado(null);
						estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
						estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
						estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
						estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
						estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
						beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);
					}
				}
			//}

			if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
			}

			solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
			limpiarFormSolicitudActividad();
			cancelarGrabarSolicitud(event);
			limpiarMensajes();
			limpiarMensajesIsValido();
			
			} catch (BusinessException e) {
				e.printStackTrace();
			}
	}

	
	/**
	 * 
	 * @param event
	 */
	public void irEliminarSolicitudActividad(ActionEvent event) {
		limpiarFormSolicitudActividad();
		//SocioComp socioComp;
		//Integer intIdPersona = null;
		//Persona persona = null;
		EstadoCredito ultimoEstado = null;
		ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
		expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
		expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
		expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
		expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());
		
		try {
			// devuelve el crongrama son id vacio.
			beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);
			if (beanExpedienteCredito != null) {
				if (beanExpedienteCredito.getListaEstadoCredito() != null) {
					ultimoEstado = solicitudPrestamoFacade.ultimoEstadoCredito(beanExpedienteCredito);
					if(ultimoEstado != null){
						beanExpedienteCredito.setEstadoCreditoUltimo(ultimoEstado);
						
						Integer intEstado = ultimoEstado.getIntParaEstadoCreditoCod();
							
							for(int k=0;k<listaEstadoPrestamo.size();k++){
								if(intEstado.compareTo(listaEstadoPrestamo.get(k).getIntIdDetalle())==0){
									strUltimoEstado = listaEstadoPrestamo.get(k).getStrDescripcion();
									break;
								}
							}

							if(ultimoEstado.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
								//-------------------- se graba el nuevo estado
								EstadoCredito estadoCredito = null;
								// Validamos que todos requisitos se cumplan
								if (beanExpedienteCredito.getListaEstadoCredito() != null) {	
									// Si no se graba en estado REQUISITO
									estadoCredito = new EstadoCredito();
									estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO);
									estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
									estadoCredito.setIntPersEmpresaEstadoCod(usuario.getSucursal().getIntIdEstado());				
									estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
									estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
									estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntIdEstado());
									beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);
									// }
								}

								solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
							}else{
								strMsgErrorPreEvaluacion = "Solo se puede Eliminar una Solicitud en estado REQUISITO. El estado actual es "+strUltimoEstado.toUpperCase();
								return;
							}
					}
					
				}

			}

			cancelarGrabarSolicitud(event);
			limpiarFormSolicitudActividad();
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
		} 	
	}

	
	/**
	 * Recupera los valores de los combos Actividad y SubActividad. Ademas beanCredito
	 */
	public void recuperarCredito(){
		ExpedienteCredito exp = null;
		CreditoId creditoId = null;
		List<Credito> lstCreditos= null;
		
		try {
			if(registroSeleccionadoBusqueda != null){
				exp = registroSeleccionadoBusqueda.getExpedienteCredito();
				creditoId = new CreditoId();
				creditoId.setIntItemCredito(exp.getIntItemCredito());
				creditoId.setIntParaTipoCreditoCod(exp.getIntParaTipoCreditoCod());
				creditoId.setIntPersEmpresaPk(exp.getIntPersEmpresaCreditoPk());

				beanCredito = creditoFacade.getCreditoPorIdCredito(creditoId);
				
				intTipoActividad = beanCredito.getId().getIntParaTipoCreditoCod();
				
				// CARGAR LISTA DE SUBACTIVIDAD
				lstCreditos = creditoFacade.getlistaCreditoPorCredito(beanCredito);
				if(lstCreditos != null && !lstCreditos.isEmpty()){
					setListaTiposSubActividad(lstCreditos);
				}
			}
		} catch (Exception e) {
			log.error("Error en recuperarCredito ---> "+e);
		}
		
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void irModificarSolicitudActividadAutoriza(ActionEvent event){	
		AutorizacionActividadController autorizacionActividad = (AutorizacionActividadController) getSessionBean("autorizacionActividadController");
		autorizacionActividad.irModificarSolicitudActividadAutoriza(event);
	}

	
	/**
	 * 
	 * @param event
	 * @param registroSeleccionadoAut
	 */
		public void irModificarSolicitudActividadAutoriza2(ActionEvent event, ExpedienteCreditoComp registroSeleccionadoAut){	
				cancelarGrabarSolicitud(event);
				blnMontoCancelar = Boolean.TRUE;
				intMiOperacion = 2;
				strSolicitudActividad = Constante.MANTENIMIENTO_MODIFICAR;
				blnMostrarDescripciones = Boolean.TRUE;
		
				SocioComp socioComp = null;
				Integer intIdPersona = null;
				Persona persona = null;
//				List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;
				List<ExpedienteActividad> listaExpActividad = null;
				ExpedienteActividad expActividad = null;
				CreditoId creditoId = new CreditoId();
//				Credito credito = new Credito();
		
				ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();							
				try {
					expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoAut.getExpedienteCredito().getId().getIntPersEmpresaPk());
					expedienteCreditoId.setIntCuentaPk(registroSeleccionadoAut.getExpedienteCredito().getId().getIntCuentaPk());
					expedienteCreditoId.setIntItemExpediente(registroSeleccionadoAut.getExpedienteCredito().getId().getIntItemExpediente());
					expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoAut.getExpedienteCredito().getId().getIntItemDetExpediente());
					
					// devuelve el crongrama son id vacio.
					beanExpedienteCredito =  solicitudPrestamoFacade.getExpedienteActividadCompletoPorIdExpedienteActividad(expedienteCreditoId);
					
					creditoId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());
					
					beanCredito = creditoFacade.getCreditoPorIdCredito(creditoId);
					intTipoActividad = beanCredito.getId().getIntParaTipoCreditoCod();
					intTipoSubActividad = beanCredito.getId().getIntItemCredito();
					intTipoSolicitudSubActividad = beanCredito.getId().getIntParaTipoCreditoCod();
					
					cargarDescripciones();
					listaTiposActividad  = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 3);
					
					if (beanExpedienteCredito != null) {						
							// Recuperamos al Socio 
								if(beanExpedienteCredito.getId() != null){
									
									CuentaId cuentaIdSocio = new CuentaId();
									Cuenta cuentaSocio = null;
									List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
									cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteCredito.getId().getIntPersEmpresaPk());
									cuentaIdSocio.setIntCuenta(beanExpedienteCredito.getId().getIntCuentaPk());
									cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
										
									listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());
										
										if(listaCuentaIntegranteSocio != null){
											intIdPersona = listaCuentaIntegranteSocio.get(0).getId().getIntPersonaIntegrante();
											persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
											if (persona != null) {
												if (persona.getListaDocumento() != null
														&& persona.getListaDocumento().size() > 0) {
													for (Documento documento : persona.getListaDocumento()) {
														if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
															persona.setDocumento(documento);
															break;
														}
													}
												}							
												
												socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
														new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
														persona.getDocumento().getStrNumeroIdentidad(),
														Constante.PARAM_EMPRESASESION,cuentaSocio);
		
												for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
													if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
														socioComp.getSocio().setSocioEstructura(socioEstructura);
													}
												}
												beanSocioComp = socioComp;
											}
										}	
								}

								SocioEstructura socioEstrConcat = null;
								getListEstructura();
								for ( int e=0; e<socioComp.getSocio().getListSocioEstructura().size();e++) {
									socioEstrConcat = beanSocioComp.getSocio().getListSocioEstructura().get(e);
									listaDependenciasSocio = new ArrayList<Estructura>();
									for(int s=0; s<listEstructura.size();s++){
										if(listEstructura.get(s).getId().getIntCodigo().compareTo(socioEstrConcat.getIntCodigo())==0){
											listaDependenciasSocio.add(listEstructura.get(s));
										}	
									}
								}
																
							//recuperamos los datos de expedienteactividad
								if (beanExpedienteCredito.getListaExpedienteActividad() != null 
									&& !beanExpedienteCredito.getListaExpedienteActividad().isEmpty() ) {
									BigDecimal bdMontoCancelar = BigDecimal.ZERO;
									
									listaExpActividad = beanExpedienteCredito.getListaExpedienteActividad();
									expActividad = listaExpActividad.get(0);
									intValidarDatosTipoSolicitud = expActividad.getIntParaTipoSolicitudCod();
									intTipoDeSolicitud = expActividad.getIntParaTipoSolicitudCod();
									bdMontoCancelar = expActividad.getBdMontoCancelado();
									if(intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
										bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
										strMontoACancelar = ""+bdMontoCancelar;
										intTipoSolicitudSubActividad =Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO;
										
									}else{
										strMontoACancelar = null;
										intTipoSolicitudSubActividad = Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO;
									}
								}	
								
								
							// recuperamos estados
							if (beanExpedienteCredito.getListaEstadoCredito() != null) {
									if (beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)
										|| beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)) {
										setStrSolicitudActividad(Constante.MANTENIMIENTO_ELIMINAR);
									} else {
										setStrSolicitudActividad(Constante.MANTENIMIENTO_MODIFICAR);
									}
							}
							
							if (beanExpedienteCredito.getEstadoCreditoPrimero() != null) {
								long lnFechaEstadoUno = beanExpedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado().getTime();
								lnFechaEstadoUno = beanExpedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado().getTime();
								dtFechaRegistro = new Date(lnFechaEstadoUno);
								calFechaRegistro.setTime(dtFechaRegistro);
							}

						if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null) {
							listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
						}

						recuperarCredito();
						definirTipoDeSolicitudMod();
						evaluarSolicitudMod(event, calFechaRegistro);

						blnShowDivFormSolicitudActividad = true;
						pgValidDatos = false;
						blnDatosSocio = true;	
	
					}

				} catch (BusinessException e) {
					log.error("Error BusinessException  generarCronograma e : " + e);
					e.printStackTrace();
				} 
		catch (Exception e2) {
			System.out.println("Error Exception generarCronograma e2 ---> "+e2);
					log.error(e2);
		}		
	}
	
		
		
		/**
		 * Metodo que se ejecuta al presionar MODIFICAR. 
		 * Recupera todos los datos del Expediente de Refinanciamiento.
		 * @param event
		 * @throws ParseException 
		 */
			public void irModificarSolicitudActividad(ActionEvent event){	
				cancelarGrabarSolicitud(event);
				blnMontoCancelar = Boolean.TRUE;
				intMiOperacion = 2;
				strSolicitudActividad = Constante.MANTENIMIENTO_MODIFICAR;
				blnMostrarDescripciones = Boolean.TRUE;
				SocioComp socioComp = null;
				Integer intIdPersona = null;
				Persona persona = null;
//				List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;
				List<ExpedienteActividad> listaExpActividad = null;
				ExpedienteActividad expActividad = null;
				ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
				strUnidadesEjecutorasConcatenadas = "";
						
				try {
					
						System.out.println("registroSeleccionadoBusquedav --> "+registroSeleccionadoBusqueda);
						expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
						expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
						expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
						expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());
						
						// devuelve el crongrama son id vacio.
						beanExpedienteCredito =  solicitudPrestamoFacade.getExpedienteActividadCompletoPorIdExpedienteActividad(expedienteCreditoId);
						
						if (beanExpedienteCredito != null) {
							
								// Recuperamos al Socio 
									if(beanExpedienteCredito.getId() != null){
										
										CuentaId cuentaIdSocio = new CuentaId();
										Cuenta cuentaSocio = null;
										List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
										cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteCredito.getId().getIntPersEmpresaPk());
										cuentaIdSocio.setIntCuenta(beanExpedienteCredito.getId().getIntCuentaPk());
			
										cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
										listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());
											if(listaCuentaIntegranteSocio != null && !listaCuentaIntegranteSocio.isEmpty()){
												intIdPersona = listaCuentaIntegranteSocio.get(0).getId().getIntPersonaIntegrante();
												persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
												if (persona != null) {
													if (persona.getListaDocumento() != null
															&& persona.getListaDocumento().size() > 0) {
														for (Documento documento : persona.getListaDocumento()) {
															if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
																persona.setDocumento(documento);
																break;
															}
														}
													}							
													
													socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
																new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
																persona.getDocumento().getStrNumeroIdentidad(),
																Constante.PARAM_EMPRESASESION,cuentaSocio);
			
													for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
														if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
															socioComp.getSocio().setSocioEstructura(socioEstructura);
														}
													}
													beanSocioComp = socioComp;
												}
											}	
										//}	
									}
								
									cargarDescripcionUEjecutorasConcatenadas(beanSocioComp);
									
									intTipoSubActividad = beanExpedienteCredito.getIntItemCredito();//   beanCredito.getId().getIntItemCredito();
									intTipoSolicitudSubActividad = beanExpedienteCredito.getIntParaTipoCreditoCod();//  beanCredito.getId().getIntParaTipoCreditoCod();
								//recuperamos los datos de expedienteactividad
									if (beanExpedienteCredito.getListaExpedienteActividad() != null 
										&& !beanExpedienteCredito.getListaExpedienteActividad().isEmpty() ) {
										BigDecimal bdMontoCancelar = BigDecimal.ZERO;
										
										listaExpActividad = beanExpedienteCredito.getListaExpedienteActividad();
										if(listaExpActividad != null && !listaExpActividad.isEmpty()){
											expActividad = listaExpActividad.get(0);
											intValidarDatosTipoSolicitud = expActividad.getIntParaTipoSolicitudCod();
											intTipoDeSolicitud = expActividad.getIntParaTipoSolicitudCod();
											bdMontoCancelar = expActividad.getBdMontoCancelado();
										}
										
										if(intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
											bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
											strMontoACancelar = ""+bdMontoCancelar;
											intTipoSolicitudSubActividad =Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO;
											
										}else{
											strMontoACancelar = null;
											intTipoSolicitudSubActividad = Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO;
										}
									}	

								// recuperamos estados
								if (beanExpedienteCredito.getListaEstadoCredito() != null) {
										if (beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)
											|| beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)) {
											setStrSolicitudActividad(Constante.MANTENIMIENTO_ELIMINAR);
										} else {
											setStrSolicitudActividad(Constante.MANTENIMIENTO_MODIFICAR);
										}
								}
								
								if (beanExpedienteCredito.getEstadoCreditoPrimero() != null) {
									long lnFechaEstadoUno = beanExpedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado().getTime();
									lnFechaEstadoUno = beanExpedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado().getTime();
									dtFechaRegistro = new Date(lnFechaEstadoUno);
									calFechaRegistro.setTime(dtFechaRegistro);
								}
								
								
							if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null) {
								listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
							}
							recuperarCredito();
							definirTipoDeSolicitudMod();
							evaluarSolicitudMod(event, calFechaRegistro);
						}

					} catch (BusinessException e) {
					log.error("Error BusinessException  irModificarSolicitudActividad e : " + e);
					e.printStackTrace();
				} 
		catch (Exception e2) {
			System.out.println("Error Exception irModificarSolicitudActividad e2 ---> "+e2);
					log.error(e2);
		}
		
				blnShowDivFormSolicitudActividad = true;
				pgValidDatos = false;
				blnDatosSocio = true;
		}
			
	/**
	 * 	
	 * @param event
	 */
	public void irVerSolicitudActividad(ActionEvent event){	
		cancelarGrabarSolicitud(event);
		blnMontoCancelar = Boolean.TRUE;
		intMiOperacion = 2;
		strSolicitudActividad = Constante.MANTENIMIENTO_NINGUNO;
		blnMostrarDescripciones = Boolean.TRUE;

		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
//		List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;
		List<ExpedienteActividad> listaExpActividad = null;
		ExpedienteActividad expActividad = null;

		ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
		strUnidadesEjecutorasConcatenadas = "";
	
		
		try {
			
			System.out.println("registroSeleccionadoBusquedav --> "+registroSeleccionadoBusqueda);
			expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
			expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
			expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
			expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());
			
			// devuelve el crongrama son id vacio.
			beanExpedienteCredito =  solicitudPrestamoFacade.getExpedienteActividadCompletoPorIdExpedienteActividad(expedienteCreditoId);
			
			if (beanExpedienteCredito != null) {
					//listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				
					// Recuperamos al Socio 
						if(beanExpedienteCredito.getId() != null){
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = null;
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteCredito.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedienteCredito.getId().getIntCuentaPk());
							cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
							listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());
								if(listaCuentaIntegranteSocio != null){
									//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
									intIdPersona = listaCuentaIntegranteSocio.get(0).getId().getIntPersonaIntegrante();
									persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
									if (persona != null) {
										if (persona.getListaDocumento() != null
												&& persona.getListaDocumento().size() > 0) {
											for (Documento documento : persona.getListaDocumento()) {
												if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
													persona.setDocumento(documento);
													break;
												}
											}
										}							
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
												new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
												persona.getDocumento().getStrNumeroIdentidad(),
												Constante.PARAM_EMPRESASESION,cuentaSocio);

										for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
											if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
												socioComp.getSocio().setSocioEstructura(socioEstructura);
											}
										}
										beanSocioComp = socioComp;
									}
								}	
							//}	
						}
					
						cargarDescripcionUEjecutorasConcatenadas(beanSocioComp);
						
						intTipoSubActividad = beanExpedienteCredito.getIntItemCredito();//   beanCredito.getId().getIntItemCredito();
						intTipoSolicitudSubActividad = beanExpedienteCredito.getIntParaTipoCreditoCod();//  beanCredito.getId().getIntParaTipoCreditoCod();
					//recuperamos los datos de expedienteactividad
						if (beanExpedienteCredito.getListaExpedienteActividad() != null 
							&& !beanExpedienteCredito.getListaExpedienteActividad().isEmpty() ) {
							BigDecimal bdMontoCancelar = BigDecimal.ZERO;
							
							listaExpActividad = beanExpedienteCredito.getListaExpedienteActividad();
							expActividad = listaExpActividad.get(0);
							intValidarDatosTipoSolicitud = expActividad.getIntParaTipoSolicitudCod();
							intTipoDeSolicitud = expActividad.getIntParaTipoSolicitudCod();
							bdMontoCancelar = expActividad.getBdMontoCancelado();

							if(intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
								bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
								strMontoACancelar = ""+bdMontoCancelar;
								intTipoSolicitudSubActividad =Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO;
								
							}else{
								//bdMontoCancelar = null;;
								strMontoACancelar = null;
								intTipoSolicitudSubActividad = Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CONTADO;
							}
						}	
					if (beanExpedienteCredito.getEstadoCreditoPrimero() != null) {
						long lnFechaEstadoUno = beanExpedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado().getTime();
						lnFechaEstadoUno = beanExpedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado().getTime();
						//configurarCampos(beanExpedienteCredito);
						dtFechaRegistro = new Date(lnFechaEstadoUno);
						Integer intAnno = Integer.parseInt(Constante.sdfAnno.format(dtFechaRegistro));
						Integer intMes = Integer.parseInt(Constante.sdfMes.format(dtFechaRegistro));
						Integer intDia = Integer.parseInt(Constante.sdfDia.format(dtFechaRegistro));
						calFechaRegistro.clear();
						calFechaRegistro.setTimeInMillis(lnFechaEstadoUno);

					}
					
					
				if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null) {
					listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
				}
				recuperarCredito();
				definirTipoDeSolicitudMod();
				evaluarSolicitudMod(event, calFechaRegistro);
			}
		} catch (BusinessException e) {
			log.error("Error BusinessException  irModificarSolicitudActividad e : " + e);
			e.printStackTrace();
		} 
		catch (Exception e2) {
			System.out.println("Error Exception irModificarSolicitudActividad e2 ---> "+e2);
					log.error(e2);
		}

		blnShowDivFormSolicitudActividad = true;
		pgValidDatos = false;
		blnDatosSocio = true;
	}	
	
	/**
	 * 
	 * @param lista
	 * @throws BusinessException
	 */
	public void renombrarArchivo(List<RequisitoCreditoComp> lista)
			throws BusinessException {
		TipoArchivo tipoArchivo = null;
		Archivo archivo = null;
		try {

			for (RequisitoCreditoComp requisitoCreditoComp : lista) {
				if (requisitoCreditoComp.getArchivoAdjunto() != null) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDAD_BOLETA_PAGO)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDAD_SOLICITUD)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
							archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
				}
			} 
		}catch (BusinessException e) {
			log.error(e);
			throw e;
		}
	}

	/**
	 * 
	 */
	public void cargarDescripciones(){
		Credito creditoAct = null;
		CreditoId id = null;
		List<Credito> lstCredito = null;
		
		try {
			
			if(listaTiposActividad != null && !listaTiposActividad.isEmpty() 
				&& intTipoActividad != null && intTipoActividad != 0){
				
				for (Tabla tabDesc1 : listaTiposActividad) {
					if(tabDesc1.getIntIdDetalle().compareTo(intTipoActividad)==0){
						strDescripcionTipoActividad = tabDesc1.getStrDescripcion();
					}
				}
				
				// cargamos el 2do combo
				creditoAct = new Credito();
				id = new CreditoId();
				creditoAct.setId(id);
				creditoAct.setIntParaTipoCreditoEmpresa(intTipoActividad);
				
				lstCredito = creditoFacade.getlistaCreditoPorCredito(creditoAct);
				if(lstCredito != null){
					for (Credito credito : lstCredito) {
						if(credito.getId().getIntItemCredito().compareTo(intTipoSubActividad)==0){
							strDescripcionTipoSubActividad = credito.getStrDescripcion();
						}
					}
				}
				
			}

			
		} catch (Exception e) {
			log.error("Error en cargarDescripciones ---> "+e);
		}
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void evaluarSolicitud(ActionEvent event) {
		blnMostrarDescripciones = Boolean.TRUE;
		
		try {
			if(intMiOperacion.compareTo(1)==0){
					evaluarSolicitudInicial(event);
			}else if(intMiOperacion.compareTo(2)==0){
				evaluarSolicitudMod(event,calFechaRegistro);
			}

		} catch (Exception e) {
			log.error("Error en evaluarSolicitud ---> "+e);
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void evaluarSolicitudInicial(ActionEvent event) {
		Credito credito = null;
		CreditoId creditoId = null;	
		List<Credito> lstCreditos = null;
//		List listaVencimientoUno = new ArrayList();
		Calendar cal = fecHoy.getInstance();
		Calendar calResult = fecHoy.getInstance();
//		String strNumeroCuotas = null;
		List<CreditoInteres> listaCreditoInteres = null;
		Integer intCuotaMaxima = new Integer(0);
		Credito actividadSelect = null;
			
		try {
			credito = new Credito();
			creditoId = new CreditoId();
			creditoId.setIntItemCredito(intTipoSubActividad);
			credito.setId(creditoId);
			
			cargarDescripciones();
			//beanCredito
			lstCreditos = creditoFacade.getlistaCreditoPorCredito(credito);
			if(lstCreditos != null && !lstCreditos.isEmpty() && lstCreditos.size() == 1){
				actividadSelect = lstCreditos.get(0);
				
				if(actividadSelect != null){
					if(!procedeEvaluacion(actividadSelect)){
						blnMostrarDescripciones = Boolean.FALSE;
						return;
					}else{
						EstructuraDetalle estructuraDetalle = null;
						
						if (beanSocioComp.getSocio().getSocioEstructura() != null) {
							estructuraDetalle = new EstructuraDetalle();	
							estructuraDetalle.setId(new EstructuraDetalleId());
							estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
							estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
							estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																										beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																										beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());

							// si es credito se calcula la fecha de 1er envio y nro de cuotas
							if(intTipoSolicitudSubActividad.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
								calResult.clear();
								calResult = xcalculoInicialFechasEnvio(estructuraDetalle,cal);
								String strFechaInicio = Constante.sdf.format(calResult.getTime());	
								String strFechaFinCredito = beanCredito.getStrDtFechaFin(); // 31/12/2013
								Integer intMeses= calcularNrodeMesesEntreDosFechasString(strFechaInicio, strFechaFinCredito);
								
								intNroCuotas = new Integer(intMeses);
								
								listaCreditoInteres = creditoFacade.getlistaCreditoInteresPorPKCreditoFiltradoPorCondicionSocio(beanCredito.getId(), beanSocioComp);
								if(listaCreditoInteres != null && !listaCreditoInteres.isEmpty()){
									for (CreditoInteres creditoInteres : listaCreditoInteres) {
										if(creditoInteres.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
											if(creditoInteres.getIntMesMaximo() != null){
												intCuotaMaxima = creditoInteres.getIntMesMaximo();
												
											}
											if(creditoInteres.getBdMontoMaximo() != null){
												beanExpedienteCredito.setBdMontoSolicitado(creditoInteres.getBdMontoMaximo());
												beanExpedienteCredito.setBdMontoTotal(creditoInteres.getBdMontoMaximo());												
											}
										}
									}
									 
									if(intCuotaMaxima.compareTo(0)!=0 && intCuotaMaxima.compareTo(intMeses)< 0){
										intNroCuotas = intCuotaMaxima;	
									}

								}else{
									intNroCuotas = new Integer(intMeses);
								}
								
							}else{
								intNroCuotas = new Integer(1);
							}
							
							// se valida la condicion de la capacidad del socio...
							isValidCondicionCapacidadPago();

							mostrarArchivosAdjuntos(event);
							limpiarCronograma();
							
							// sie s credito se genera un cronograma, caso contrario no existe
							if(intTipoSolicitudSubActividad.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
								recuperarTipoCronograma(strMontoACancelar);
								if(blnCronogramaNormal){
									//Integer intRecalculo = 0;
									generarCronogramaNormal(beanCredito, estructuraDetalle, intNroCuotas, cal);
									
								}else{
									bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
									// Puede darse el caso en que se cancele el integro en base a al adelanto...
									if(bdMontoCancelar.compareTo(creditoInteresExpediente.getBdMontoMaximo())==0){
										intNroCuotas = new Integer(1);
										generarCronogramaAdelantoTotal(beanCredito, estructuraDetalle,cal);
									
									}else{
										generarCronogramaAdelanto(beanCredito, estructuraDetalle, intNroCuotas,cal);
									}
								}
								
								recuperarSaldoYEstadoCronograma();
							}else{
								bdMontoDeCuotas = creditoInteresExpediente.getBdMontoMaximo();
								beanExpedienteCredito.setBdMontoSolicitado(bdMontoDeCuotas);
								generarCronogramaContado(beanCredito, estructuraDetalle,cal);
								blnCronograma = Boolean.FALSE;
								recuperarSaldoYEstadoCronograma();
							}
								
							blnPostEvaluacion = true;
						}
		
					}
					}else{
						
					}	
			}

		} catch (BusinessException e) {
			log.error("Error en evaluarSolicitudMod"+e);
		}

	}
	
	
	/**
	 * Valida la conficuraciond e la actividad seleccionada vs los datos del socio solicitante.
	 * @param actividad
	 */
	public Boolean validarConfiguracionActividad(Credito actividad){
		List<Credito> lstCreditos = null;
		Boolean blnPasa = Boolean.TRUE;
		Boolean blnCumpleCondicionLaboral = Boolean.TRUE;
		Boolean blnCumpleCondicion = Boolean.TRUE;
		Boolean blnCumpleCondicionHabil= Boolean.TRUE;
		Boolean blnCumpleTipoSocio= Boolean.TRUE;
		limpiarMensajesSolicitud();
		Credito actividadEvaluada = null;
		try {
			
			lstCreditos = creditoFacade.getlistaCreditoPorCredito(actividad);
			if(lstCreditos != null && !lstCreditos.isEmpty()){
				actividadEvaluada = lstCreditos.get(0);
				
				actividadEvaluada.getStrDtFechaIni();
				actividadEvaluada.getStrDtFechaFin();
				actividadEvaluada.getDtFechaIni();
				actividadEvaluada.getDtFechaFin();
				
			//1. Condicion Laboral  - persona, laboral, condicion lab.
				if(actividadEvaluada.getIntParaCondicionLaboralCod() != null){
					if(actividadEvaluada.getIntParaCondicionLaboralCod().compareTo(new Integer(-1))==0){
						blnCumpleCondicionLaboral = Boolean.TRUE;
					}else{
						for (int k = 0; k < beanSocioComp.getPersona().getNatural().getListaPerLaboral().size(); k++) {
							if (actividadEvaluada.getIntParaCondicionLaboralCod().compareTo(beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(k).getIntCondicionLaboral())==0) {
								blnCumpleCondicionLaboral = Boolean.TRUE;
								break;
							}else{
								blnCumpleCondicionLaboral = Boolean.FALSE;
								//break;
							}
						}
					}
				}
				
			//2. Credito COndicion - Condicion Cuenta
				if(actividadEvaluada.getListaCondicion() != null && !actividadEvaluada.getListaCondicion().isEmpty()){
					
					for (CondicionCredito condicion : actividadEvaluada.getListaCondicion()) {
						if (condicion.getId().getIntParaCondicionSocioCod().compareTo(-1)==0
							&& condicion.getIntValor().compareTo(1)==0){
							blnCumpleCondicion = Boolean.TRUE;
							break;
						}
					}
					if(blnCumpleCondicion){
						for (CondicionCredito condicion : actividadEvaluada.getListaCondicion()) {
							if (condicion.getId().getIntParaCondicionSocioCod().compareTo(beanSocioComp.getCuenta().getIntParaCondicionCuentaCod())==0
								&& condicion.getIntValor().compareTo(1)==0) {
								blnCumpleCondicion = Boolean.TRUE;
								break;
							}else{
								blnCumpleCondicion = Boolean.FALSE;
							}
						}
						
					}
						
				}


			//3. Credito COndicion HAbil - Suncondicion Cuenta				
				if(actividadEvaluada.getListaCondicionHabil() != null && !actividadEvaluada.getListaCondicionHabil().isEmpty()){
					
					for (CondicionHabil habil : actividadEvaluada.getListaCondicionHabil()) {
						if (habil.getId().getIntParaTipoHabilCod().compareTo(-1)==0
							&& habil.getIntValor().compareTo(1)==0){
							blnCumpleCondicionHabil = Boolean.TRUE;
							break;
						}
					}
					
					if(blnCumpleCondicionHabil){
						for (CondicionHabil habil : actividadEvaluada.getListaCondicionHabil()) {
							if (habil.getId().getIntParaTipoHabilCod().compareTo(beanSocioComp.getCuenta().getIntParaSubCondicionCuentaCod())==0
								&& habil.getIntValor().compareTo(1)==0) {
								blnCumpleCondicionHabil = Boolean.TRUE;
								break;
							}else{
								blnCumpleCondicionHabil = Boolean.FALSE;
								
							}
						}
					}
				}

				// 4. Validar tipo de socio - cesante / activo
					if(actividadEvaluada.getListaCreditoInteres() != null){
						if(beanSocioComp.getSocio().getSocioEstructura()!=null){
							
							for (CreditoInteres interes : actividadEvaluada.getListaCreditoInteres()) {
								if(interes.getIntParaTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0
										&& interes.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
									blnCumpleTipoSocio= Boolean.TRUE;
									creditoInteresExpediente = new CreditoInteres();
									creditoInteresExpediente = interes;
									break;
									
								}else {
									blnCumpleTipoSocio= Boolean.FALSE;
									creditoInteresExpediente = null;
								}
									
							}
						}
						
					}else{
						
						
						
					}
			}
			
			if(!blnCumpleCondicionLaboral){
				strMsgErrorPreEvaluacionCondicionLaboral=" *El Socio no cumple con la Condición Laboral de la Actividad. ";
			}
			if(!blnCumpleCondicion){
				strMsgErrorPreEvaluacionCondicion= " *La Situación de la Cuenta del Socio no cumple con la configuración establecida. ";
			}
			if(!blnCumpleCondicionHabil){
				strMsgErrorPreEvaluacionCondicionHabil= " *La Sub Condición de la Cuenta del Socio no cumple con la configuración establecida. ";
			}
			if(!blnCumpleTipoSocio){
				strMsgErrorPreEvaluacionTipoSocio= " *El tipo de Socio no cumple con la configuración establecida. ";
			}
			
			if(blnCumpleCondicionLaboral && blnCumpleCondicion && blnCumpleCondicionHabil && blnCumpleTipoSocio){
				blnPasa = Boolean.TRUE;
				beanCredito = actividadEvaluada;
			}else{
				blnPasa = Boolean.FALSE;
			}
			
			
		} catch (Exception e) {
			log.error("Error en validarConfiguracionActividad ---> "+e);
		}
		return blnPasa;
	}

	private void limpiarMensajesSolicitud(){
		strMsgErrorPreEvaluacionCondicionLaboral = "";
		strMsgErrorPreEvaluacionCondicion = "";
		strMsgErrorPreEvaluacionCondicionHabil = "";
		strMsgErrorPreEvaluacionTipoSocio = "";
	}
	
	public void evaluarSolicitudMod(ActionEvent event, Calendar calFechaRegistro) {
		Credito credito = null;
		CreditoId creditoId = null;	
		List<Credito> lstCreditos = null;
		List listaVencimientoUno = new ArrayList();
		Calendar cal = fecHoy.getInstance();
		Calendar calResult = fecHoy.getInstance();
		String strNumeroCuotas = null;
		List<CreditoInteres> listaCreditoInteres = null;
		Integer intCuotaMaxima = new Integer(0);
		CreditoInteres creditoInteresRecuperado = null;
			
		try {

			cargarDescripciones();
			int diaF = calFechaRegistro.get(Calendar.DATE);
			int mesF = calFechaRegistro.get(Calendar.MONTH);
			int annoF = calFechaRegistro.get(Calendar.YEAR);
			cal.clear();
			cal.set(annoF, mesF +1, diaF);			
			calResult.clear();
			calResult.set(annoF, mesF +1, diaF);			
				if(beanCredito != null){
					creditoInteresRecuperado= recuperarCreditoInteres(beanCredito);
					if(creditoInteresRecuperado != null){
						creditoInteresExpediente = new CreditoInteres();
						creditoInteresExpediente = creditoInteresRecuperado;
					}

					EstructuraDetalle estructuraDetalle = null;
					
					if (beanSocioComp.getSocio().getSocioEstructura() != null) {
						estructuraDetalle = new EstructuraDetalle();	
						estructuraDetalle.setId(new EstructuraDetalleId());
						estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
						estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
						estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																									beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																									beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());

						// si es credito se calcula la fecha de 1er envio y nro de cuotas
						if( intTipoDeSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
							calResult.clear();
							calResult = xcalculoInicialFechasEnvio(estructuraDetalle,calFechaRegistro);
							String strFechaInicio = Constante.sdf.format(calResult.getTime());	
							String strFechaFinCredito = beanCredito.getStrDtFechaFin(); // 31/12/2013
							Integer intMeses= calcularNrodeMesesEntreDosFechasString(strFechaInicio, strFechaFinCredito);
							
							intNroCuotas = new Integer(intMeses);
							
							
							
							if(creditoInteresRecuperado != null ){
									if(creditoInteresRecuperado.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
										if(creditoInteresRecuperado.getIntMesMaximo() != null){
											intCuotaMaxima = creditoInteresRecuperado.getIntMesMaximo();
											
										}
										if(creditoInteresRecuperado.getBdMontoMaximo() != null){
											beanExpedienteCredito.setBdMontoSolicitado(creditoInteresRecuperado.getBdMontoMaximo());
											beanExpedienteCredito.setBdMontoTotal(creditoInteresRecuperado.getBdMontoMaximo());												
										}
									}
								if(intCuotaMaxima.compareTo(0)!=0 && intCuotaMaxima.compareTo(intMeses)< 0){
									intNroCuotas = intCuotaMaxima;	
								}

							}else{
								intNroCuotas = new Integer(intMeses);
							}
							
						}else{
							intNroCuotas = new Integer(1);

						}

						// se valida la condicion de la capacidad del socio...
						isValidCondicionCapacidadPago();
						limpiarCronograma();
						
						// sie s credito se genera un cronograma, caso contrario no existe
						if(intTipoSolicitudSubActividad.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
							recuperarTipoCronograma(strMontoACancelar);
							if(blnCronogramaNormal){
								generarCronogramaNormal(beanCredito, estructuraDetalle, intNroCuotas, calFechaRegistro);
							}else{
								bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
								// Puede darse el caso en que se cancele el integro en base a al adelanto...
								if(bdMontoCancelar.compareTo(creditoInteresExpediente.getBdMontoMaximo())==0){
									intNroCuotas = new Integer(1);
									generarCronogramaAdelantoTotal(beanCredito, estructuraDetalle, calFechaRegistro);
								
								}else{
									generarCronogramaAdelanto(beanCredito, estructuraDetalle, intNroCuotas, calFechaRegistro);
								}
							}
							
							recuperarSaldoYEstadoCronograma();
						}else{
							bdMontoDeCuotas = creditoInteresExpediente.getBdMontoMaximo();
							beanExpedienteCredito.setBdMontoSolicitado(bdMontoDeCuotas);
							//recuperarSaldoYEstadoCronograma();
							generarCronogramaContado(beanCredito, estructuraDetalle, calFechaRegistro);
							blnCronograma = Boolean.FALSE;
							recuperarSaldoYEstadoCronograma();
						}
							
						blnPostEvaluacion = true;
					}
				}

		} catch (BusinessException e) {
			log.error("Error en evaluarSolicitud"+e);
		}

	}
	
	
	
	/**
	 * Recupera el ineters de la configuraciond e credito actividad, de acuerdo al tipo de socio.
	 * @param actividadRecuperada
	 * @return
	 */
	public CreditoInteres recuperarCreditoInteres(Credito actividadRecuperada){
		
		try {
			
			if(actividadRecuperada.getListaCreditoInteres() != null){
				if(beanSocioComp.getSocio().getSocioEstructura()!=null){
					
					for (CreditoInteres interes : actividadRecuperada.getListaCreditoInteres()) {
						if(interes.getIntParaTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0
								&& interes.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							creditoInteresExpediente = new CreditoInteres();
							creditoInteresExpediente = interes;
							break;
							
						}else {
							creditoInteresExpediente = null;
						}	
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarCreditoInteres --> "+e);
		}
		return creditoInteresExpediente;
		
	}

	public void recuperarMontoDeCuotasPromedio(ExpedienteCredito expCredito){
		List<CronogramaCredito> listaCronogramaCred = null;
		
		try {
			
			listaCronogramaCred = prestamoFacade.getListaCronogramaCreditoPorExpedienteCredito(expCredito);
			if(listaCronogramaCred != null && !listaCronogramaCred.isEmpty()){
				
				//ordenando la liosta de cronogramas...
				Collections.sort(listaCronogramaCred, new Comparator<CronogramaCredito>(){
					public int compare(CronogramaCredito uno, CronogramaCredito otro) {
						return uno.getIntPeriodoPlanilla().compareTo(otro.getIntPeriodoPlanilla());
					}
				});
				
				for(int k=0; k<listaCronogramaCred.size();k++){
					System.out.println("CRONOGRAMAS ---> "+listaCronogramaCred.get(k).getIntPeriodoPlanilla());
				}
				
				//strMontoACancelar =  ""+listaCronogramaCred.get(0).getBdMontoConcepto();
				bdMontoDeCuotas = listaCronogramaCred.get(1).getBdMontoConcepto();
				bdMontoDeCuotas = bdMontoDeCuotas.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
				
			}
			
		} catch (Exception e) {
			log.equals("Error en recuperarMontoACancelar ---> "+e);
		}
	}
	
	
	public void limpiarCronograma(){
		
		try {
			
			if(listaCronogramaCreditoComp != null){
				listaCronogramaCreditoComp.clear();	
			}
			
			if(beanExpedienteCredito.getListaCronogramaCredito() != null){
				beanExpedienteCredito.getListaCronogramaCredito().clear();
			}

		} catch (Exception e) {
			log.error("Error en limpiarCronograma() ---> "+e);
		}	
	}
	
	
	/**
	 * Define de acuerdo al valor de MONTO A CANCELAR si se aplica cronograma contado o crnograma adelanto.
	 * @param strMontoACancelar
	 * @return
	 */
	public boolean recuperarTipoCronograma(String strMontoACancelar){
		blnCronogramaNormal= Boolean.TRUE;
		Double dbMonto;
		try {
			
			if(strMontoACancelar != null && !strMontoACancelar.equalsIgnoreCase("0.00") && strMontoACancelar.length()>0){
				dbMonto = new Double(strMontoACancelar);
				bdMontoCancelar = new BigDecimal(dbMonto);
				blnCronogramaNormal = Boolean.FALSE;
			}
		} catch (Exception e) {
			log.error("Error en recuperarTipoCronograma --> "+e);
		}

		return blnCronogramaNormal;
	}
	
	
	/**
	 * Regenera el cronograma para mostrar su saldo, estado desde esquema de movimeinto
	 * @param lstCronogramaBruto
	 */
	public void recuperarSaldoYEstadoCronograma(){
		
		List<CronogramaCreditoComp> lstCronogramaCreditoCompTemp = new ArrayList<CronogramaCreditoComp>();
		List<CronogramaCreditoComp> lstCronograma = new ArrayList<CronogramaCreditoComp>();
		BigDecimal bdSaldo = BigDecimal.ZERO;
		try {
			
			if(!listaCronogramaCreditoComp.isEmpty()){
				
				lstCronogramaCreditoCompTemp = listaCronogramaCreditoComp;
				
				
				CronogramaId cronMovId = null;
				Cronograma cronMov = null;
				CronogramaCredito cronogramaCredito = null;
				for (CronogramaCreditoComp cronogramaComp : lstCronogramaCreditoCompTemp) {
						cronMovId = new CronogramaId();
						cronogramaCredito = new CronogramaCredito();
						cronogramaCredito = cronogramaComp.getCronogramaCredito();
						
						cronMovId.setIntCuentaPk(cronogramaCredito.getId().getIntCuentaPk());
						cronMovId.setIntItemCronograma(cronogramaCredito.getId().getIntItemCronograma());
						cronMovId.setIntItemExpediente(cronogramaCredito.getId().getIntItemExpediente());
						cronMovId.setIntPersEmpresaPk(cronogramaCredito.getId().getIntPersEmpresaPk());
						
						cronMov = conceptofacade.getCronogramaPorPK(cronMovId);
						
						if(cronMov != null){
							if(cronMov.getBdSaldoDetalleCredito() != null){
								
								bdSaldo = cronMov.getBdSaldoDetalleCredito();
								if(bdSaldo.compareTo(cronogramaComp.getBdAmortizacion())== 0){
									cronogramaComp.setBdSaldoCuota(BigDecimal.ZERO);
									cronogramaComp.setStrEstadoDescripcion("Cancelado");
								}else{
									cronogramaComp.setBdSaldoCuota(bdSaldo);
									cronogramaComp.setStrEstadoDescripcion("Pendiente");
								}	
							} else{
								cronogramaComp.setBdSaldoCuota(cronogramaComp.getBdAmortizacion());
								cronogramaComp.setStrEstadoDescripcion("Pendiente");
							}	
						}else{
							cronogramaComp.setBdSaldoCuota(cronogramaComp.getBdAmortizacion());
							cronogramaComp.setStrEstadoDescripcion("Pendiente");
						}
						
						lstCronograma.add(cronogramaComp);
						
					}

				listaCronogramaCreditoComp.clear();
				listaCronogramaCreditoComp = lstCronograma;
				
				}
			
		} catch (Exception e) {
			log.error("Error en recuperarSaldoYEstadoCronograma ---> "+e);
		}
	}
	
	
	
	
	
	/**
	 * Se genera el cronograma del tipo credito cuando no se ingresa adelanto.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public void generarCronogramaNormal(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle, Integer intNumeroDeCuotas, Calendar calFechaRegistro) {
		
		Envioconcepto envioConcepto = null;
		CronogramaCredito cronogramaCredito = new CronogramaCredito();
		CronogramaCreditoComp cronogramaCreditoComp = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
		BigDecimal bdMontoPrestamo = beanExpedienteCredito.getBdMontoSolicitado();
		BigDecimal bdMontoCuota = BigDecimal.ZERO;
		BigDecimal bdMontoUltimaCuota = BigDecimal.ZERO;
		Date fechaVenc = new Date();
//		BigDecimal bdMontoCancelarTx = BigDecimal.ZERO;
		MathContext mc1 = new MathContext(2, RoundingMode.HALF_UP);  
//		Integer intNroCuotasActual = 0;//  intNroCuotas
		Integer intNroRecalculado = -1;

		try {
			
			while (intNroRecalculado.compareTo(0)!=0){
				limpiarCronogramasGeneracion();
				listaCronogramaCredito = new ArrayList<CronogramaCredito>();
				cronogramaCreditoComp = new CronogramaCreditoComp();
			
				recuperarTipoCronograma(strMontoACancelar);
				bdMontoGenerarCronograma = beanExpedienteCredito.getBdMontoSolicitado();
				bdMontoCuota = bdMontoGenerarCronograma.divide(new BigDecimal(intNroCuotas),mc1);	

				// CGD-21.01.2014-YT
				List listaDiasEnvio = new ArrayList();
				List listaDiasVencimiento = new ArrayList();
				int vencDia, vencMes, vencAnno;
				int envDia, envMes, envAnno;

				envDia = new Integer(Constante.sdf.format(dtFechaRegistro).substring(0,2));
				envMes = new Integer(Constante.sdf.format(dtFechaRegistro).substring(3,5));
				envAnno = new Integer(Constante.sdf.format(dtFechaRegistro).substring(6,10));
				
				String strVencimiento = calcular1raFechaVencimiento(estructuraDetalle);
				vencDia = new Integer(strVencimiento.substring(0,2));
				vencMes = new Integer(strVencimiento.substring(3,5));
				vencAnno = new Integer(strVencimiento.substring(6,10));

				for (int i = 0; i < intNroCuotas; i++) {
					Calendar nuevoDia = Calendar.getInstance();
					if(i==0){
						listaDiasEnvio.add(i, Constante.sdf.format(dtFechaRegistro));
						listaDiasVencimiento.add(i,strVencimiento);
					}else{
						//JCHAVEZ 14.02.2014
						if (vencMes == 12) {
							listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
							nuevoDia.set(vencAnno, vencMes-1, 15);
							listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
							vencAnno = vencAnno + 1;
							vencMes = 0;
							envAnno ++;
						} else {
							listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
							nuevoDia.set(vencAnno, vencMes-1, 15);
							listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
						}
					}
					vencMes++;
				}

				BigDecimal bdAmortizacion = new BigDecimal(0);
				BigDecimal bdSumaAmortizacion = BigDecimal.ZERO;
				BigDecimal bdSumaFaltaCancelar = beanExpedienteCredito.getBdMontoSolicitado();

				for (int i = 0; i < intNroCuotas; i++) {
					bdAmortizacion = bdMontoCuota;
					
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
					
					// Si es la ultima cuota realizamos ajuste de centimos
					if(i + 1 == intNroCuotas){
						if(bdMontoPrestamo.compareTo(bdSumaAmortizacion)!=0){
							bdMontoUltimaCuota = bdMontoPrestamo.subtract(bdSumaAmortizacion.subtract(bdAmortizacion));
							//bdMontoUltimaCuota = bdMontoPrestamo.subtract(bdSumaAmortizacion);
							bdAmortizacion = bdMontoUltimaCuota;
							bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
							bdSumaFaltaCancelar = bdAmortizacion;
						}
					}

						// generando cronograma para guardar
						cronogramaCredito = new CronogramaCredito();
						cronogramaCredito.setId(new CronogramaCreditoId());
						cronogramaCredito.setIntNroCuota(i + 1);
						cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
						cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
						cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
						cronogramaCredito.setBdMontoConcepto(bdAmortizacion);
						cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);

						fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();

						cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
						cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
						cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						listaCronogramaCredito.add(cronogramaCredito);
						
						// Generando Cronograma para mostrar
						cronogramaCreditoComp = new CronogramaCreditoComp();
						cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
						cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
						cronogramaCreditoComp.setBdSaldoCapital(bdSumaFaltaCancelar);
						cronogramaCreditoComp.setBdCuotaMensual(bdAmortizacion);
						cronogramaCreditoComp.setBdTotalCuotaMensual(BigDecimal.ZERO); //  movimiento
						cronogramaCreditoComp.setIntParaTipoFormapago(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
						cronogramaCreditoComp.setCronogramaCredito(cronogramaCredito);
						listaCronogramaCreditoComp.add(cronogramaCreditoComp);
						
						bdSumaFaltaCancelar = bdSumaFaltaCancelar.subtract(bdAmortizacion);

					//}
					
				}
								
				beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);			
				beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
				// generamos el valor de la cuota a mostar
				if(listaCronogramaCreditoComp.size() == 1){
					setBdMontoDeCuotas(bdMontoGenerarCronograma);
				}else{
					setBdMontoDeCuotas(listaCronogramaCreditoComp.get(1).getBdAmortizacion()== null ? BigDecimal.ZERO : listaCronogramaCreditoComp.get(1).getBdAmortizacion());

				}
				
				intNroRecalculado = validarRecalculoCronograma(intNroCuotas, listaCronogramaCredito);
				if(intNroRecalculado.compareTo(0)!=0){
					intNroCuotas = intNroRecalculado - 1;
				}
			};
			
			
		} catch (Exception e) {
			log.error("Error al generarCronograma() ---> "+e);
			e.printStackTrace();
		}

}
	
	
	public void limpiarCronogramasGeneracion (){
		try {
			if(beanExpedienteCredito.getListaCronogramaCredito() != null
				&& !beanExpedienteCredito.getListaCronogramaCredito().isEmpty()){
				beanExpedienteCredito.getListaCronogramaCredito().clear();
			}else{
				beanExpedienteCredito.setListaCronogramaCredito(new ArrayList<CronogramaCredito>());
			}
			
			if(listaCronogramaCreditoComp != null
					&& !listaCronogramaCreditoComp.isEmpty()){
				listaCronogramaCreditoComp.clear();
			}else{
				listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
			}

			
		} catch (Exception e) {
			log.error("Error en limpiarCronogramas ---> "+e);
		}
	}

	/**
	 * Clacula cuotas dif entre fech hoy y fin credito
	 * @param credito
	 * @return
	 */

	
	public static final Timestamp  convierteStringATimestamp(String str_date){
		DateFormat formatter ; 
		Date date ; 
		java.sql.Timestamp timeStampDate = null;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		try{
		    date = (Date)formatter.parse(str_date); 
		    timeStampDate = new  Timestamp(date.getTime());
		    System.out.println("Today is " +timeStampDate);
		}
		catch (ParseException e){
			System.out.println("Exception :"+e);  
		}  
		 
		return timeStampDate;
	 }
	
	/**
	 * Valida sla necesidad de realizar un recalculo del nrod e cuotas.
	 * Estas no deben pasar la fecha de fin de vigenica de la configuraciond e la actividad
	 * @param intNroCuotas
	 * @param lstCronograma
	 */
	private Integer validarRecalculoCronograma(Integer intNroCuotas, List<CronogramaCredito> lstCronograma){
		Timestamp dtFin = new Timestamp(new Date().getTime());
		Date dtVencimiento = null;
		//Boolean blnPasa = Boolean.TRUE; 
		Integer intCuota = 0;
		Integer intNroMeses = 0;
		CronogramaCredito cronogramaCred = null;
		
		try {
		
			dtFin = convierteStringATimestamp(beanCredito.getStrDtFechaFin());

			// 1.  orednamos el cronograma x nro cuota, cogemos la ultima....
			Collections.sort(lstCronograma, new Comparator<CronogramaCredito>(){
				public int compare(CronogramaCredito uno, CronogramaCredito otro) {
					return uno.getIntPeriodoPlanilla().compareTo(otro.getIntPeriodoPlanilla());
				}
			});
			
			for(int k=0; k<lstCronograma.size();k++){
				System.out.println("CRONOGRAMAS ---> "+lstCronograma.get(k).getIntNroCuota());
			}
			
			
			// 2. No deberia sobrepadar el periodo final de la fecha de find el cfrredito
			for (CronogramaCredito cronogramaCredito : lstCronograma) {
				dtVencimiento = cronogramaCredito.getTsFechaVencimiento();
				if(dtVencimiento.after(dtFin)){
					intCuota = cronogramaCredito.getIntNroCuota();
					break;
					
				}
			}

		} catch (Exception e) {
			log.error("Error en reCalcularNroCuotasActividad ---> "+e);
		}
		return intCuota;

	}
	
	/**
	 * Genera el cronograma en el caso que sea Credito y exista un adelanto distinto a CERO y al Monto Total de la Actividad.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public void generarCronogramaAdelanto(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle, Integer intNumeroDeCuotas, Calendar calFechaRegistro) {
		Integer intNroCuotasTemp = intNroCuotas; 
		Calendar miCal = Calendar.getInstance();
		miCal.clear();
		miCal.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		CronogramaCredito cronogramaCredito = new CronogramaCredito();
		CronogramaCreditoComp cronogramaCreditoComp = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
		BigDecimal bdMontoPrestamo = beanExpedienteCredito.getBdMontoSolicitado();
		BigDecimal bdMontoCuota = BigDecimal.ZERO;
		BigDecimal bdMontoUltimaCuota = BigDecimal.ZERO;
		Date fechaVenc = new Date();
		BigDecimal bdMontoCancelarTx = BigDecimal.ZERO;
		MathContext mc1 = new MathContext(2, RoundingMode.HALF_UP);  
		Integer intNroCuotasActual = 0;//  intNroCuotas
		String strFechaMes="";
		String strFechaDia="";
		
		Integer intNroRecalculado = -1;

		try {
			while (intNroRecalculado.compareTo(0)!=0 ){//&& intNroCuotas.compareTo(0)!=0
			limpiarCronogramasGeneracion();
			listaCronogramaCredito = new ArrayList<CronogramaCredito>();
			cronogramaCreditoComp = new CronogramaCreditoComp();
			recuperarTipoCronograma(strMontoACancelar);

			// se agrega la primera cuota el monto q cancelo...
			bdMontoGenerarCronograma = beanExpedienteCredito.getBdMontoSolicitado().subtract(bdMontoCancelar);
			bdMontoCuota = bdMontoGenerarCronograma.divide(new BigDecimal(intNroCuotas.equals(0)?1:intNroCuotas),2,RoundingMode.HALF_UP);
			
			// CGD-21.01.2014-YT
			List listaDiasEnvio = new ArrayList();
			List listaDiasVencimiento = new ArrayList();
			int vencDia, vencMes, vencAnno;
			int envDia, envMes, envAnno;

			envDia = new Integer(Constante.sdf.format(dtFechaRegistro).substring(0,2));
			envMes = new Integer(Constante.sdf.format(dtFechaRegistro).substring(3,5));
			envAnno = new Integer(Constante.sdf.format(dtFechaRegistro).substring(6,10));
			
			String strVencimiento = calcular1raFechaVencimiento(estructuraDetalle);
			vencDia = new Integer(strVencimiento.substring(0,2));
			vencMes = new Integer(strVencimiento.substring(3,5));
			vencAnno = new Integer(strVencimiento.substring(6,10));
			
			// SE GENENERA LA LISTA DE LOS DIAS DE VENCIMIENTO listaDiasVencimiento
			for (int i = 0; i < intNroCuotas; i++) {
				Calendar nuevoDia = Calendar.getInstance();
				if(i==0){
					listaDiasEnvio.add(i, Constante.sdf.format(dtFechaRegistro));
					listaDiasVencimiento.add(i,strVencimiento);
				}else{		
					//JCHAVEZ 14.02.2014
					if (vencMes == 12) {
						listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
						nuevoDia.set(vencAnno, vencMes-1, 15);
						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
						vencAnno = vencAnno + 1;
						vencMes = 0;
						envAnno ++;
					} else {
						listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
						nuevoDia.set(vencAnno, vencMes-1, 15);
						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
					}
				}
				//
//					listaDiasEnvio.add(i, Constante.sdf.format(dtFechaRegistro));
//					listaDiasVencimiento.add(i,strVencimiento);
//				}else{
//					listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
//					nuevoDia.set(vencAnno, vencMes, 15);
//					if (vencMes == 12) {
//						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//						vencAnno = vencAnno + 1;
//						vencMes = 0;
//					} else {
//						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//					}
//				}
				vencMes++;
			}
			
			/**
			 * Valor de la cuota Mensual
			 */
			BigDecimal bdAmortizacion = new BigDecimal(0); //la cuota mensual a pagar
			BigDecimal bdSumaAmortizacion = BigDecimal.ZERO;	// loq ue se va pagando
			BigDecimal bdSumaFaltaCancelar = beanExpedienteCredito.getBdMontoSolicitado(); // se inicaliza con el valor total de actividad
			
			
			for (int i = 0; i < intNroCuotas; i++) {
				bdAmortizacion = bdMontoCuota;
				if(i==0){
					// Cuota CERO -  TRANSFERECNIA.
					bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);

					// generando cronograma para guardar
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.setIntNroCuota(i);
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_REGULARIZACION);
					cronogramaCredito.setIntParaFormaPagoCod(0);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
					cronogramaCredito.setBdMontoConcepto(bdMontoCancelar);
					cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);
					cronogramaCredito.setTsFechaVencimiento(new Timestamp(calFechaRegistro.getTime().getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(calFechaRegistro.getTime().getTime()).toString()));
					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaCronogramaCredito.add(cronogramaCredito);

					// Generando Cronograma para mostrar
					cronogramaCreditoComp = new CronogramaCreditoComp();
					cronogramaCreditoComp.setStrFechaVencimiento(Constante.sdf.format(calFechaRegistro.getTime().getTime()));
					cronogramaCreditoComp.setBdAmortizacion(bdMontoCancelar);
					cronogramaCreditoComp.setBdSaldoCapital(bdSumaFaltaCancelar);
					cronogramaCreditoComp.setCronogramaCredito(cronogramaCredito);
					listaCronogramaCreditoComp.add(cronogramaCreditoComp);
					
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdMontoCancelar);
					bdSumaFaltaCancelar = beanExpedienteCredito.getBdMontoSolicitado().subtract(bdSumaAmortizacion);
					bdSumaFaltaCancelar = bdSumaFaltaCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);

					//CUOTA  UNO:
					// generando cronograma para guardar
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.setIntNroCuota(i+1);
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
					cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
					cronogramaCredito.setBdMontoConcepto(bdAmortizacion);
					cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);

					fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();

					cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaCronogramaCredito.add(cronogramaCredito);
					
					// Generando Cronograma para mostrar
					cronogramaCreditoComp = new CronogramaCreditoComp();
					cronogramaCreditoComp.setStrFechaVencimiento(Constante.sdf.format(fechaVenc.getTime()).toString());
					cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
					cronogramaCreditoComp.setBdTotalCuotaMensual(BigDecimal.ZERO); //  movimiento
					cronogramaCreditoComp.setIntParaTipoFormapago(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					cronogramaCreditoComp.setBdSaldoCapital(bdSumaFaltaCancelar);
					cronogramaCreditoComp.setCronogramaCredito(cronogramaCredito);
					listaCronogramaCreditoComp.add(cronogramaCreditoComp);
					
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
					bdSumaFaltaCancelar = beanExpedienteCredito.getBdMontoSolicitado().subtract(bdSumaAmortizacion);
					bdSumaFaltaCancelar = bdSumaFaltaCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
					
				}else{
					// Si es la ultima cuota realizamos ajuste de centimos
					if(i + 1 == intNroCuotas){
						if(bdMontoPrestamo.compareTo(bdSumaAmortizacion)!=0){
							bdMontoUltimaCuota = bdMontoPrestamo.subtract(bdSumaAmortizacion);
							bdMontoUltimaCuota = bdMontoUltimaCuota.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
							//bdSumaFaltaCancelar = BigDecimal.ZERO;
							bdSumaFaltaCancelar = bdMontoUltimaCuota;
							bdAmortizacion = bdSumaFaltaCancelar;

						}
					}

					// generando cronograma para guardar
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.setIntNroCuota(i + 1);
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
					cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
					cronogramaCredito.setBdMontoConcepto(bdAmortizacion);
					cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);

					fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();

					cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaCronogramaCredito.add(cronogramaCredito);
					
					// Generando Cronograma para mostrar
					cronogramaCreditoComp = new CronogramaCreditoComp();
					cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
					cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
					cronogramaCreditoComp.setBdSaldoCapital(bdSumaFaltaCancelar);

					cronogramaCreditoComp.setIntParaTipoFormapago(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					cronogramaCreditoComp.setCronogramaCredito(cronogramaCredito);
					listaCronogramaCreditoComp.add(cronogramaCreditoComp);
					
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
					bdSumaFaltaCancelar = beanExpedienteCredito.getBdMontoSolicitado().subtract(bdSumaAmortizacion);
					bdSumaFaltaCancelar = bdSumaFaltaCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);

				}
			}
			beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);			
			beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
			// generamos el valor de la cuota a mostar
			if(listaCronogramaCreditoComp.size() == 1){
				setBdMontoDeCuotas(bdMontoGenerarCronograma);
			}else{
				setBdMontoDeCuotas(listaCronogramaCreditoComp.get(1).getBdAmortizacion()== null ? BigDecimal.ZERO : listaCronogramaCreditoComp.get(1).getBdAmortizacion());
			}
			intNroRecalculado = validarRecalculoCronograma(intNroCuotas, listaCronogramaCredito);
			if(intNroRecalculado.compareTo(0)!=0){
				intNroCuotas = intNroRecalculado - 1;
			}
		};
//		intNroCuotas = intNroCuotasTemp;
		} catch (Exception e) {
			log.error("Error al generarCronograma() ---> "+e);
			e.printStackTrace();
		}
}
	
	
	
	/**
	 * En el caso que el adelanto sea del mismo valor que la configuracion del monto.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public void generarCronogramaAdelantoTotal(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle, Calendar calFechaRegistro) {
		CronogramaCredito cronogramaCredito = new CronogramaCredito();
		CronogramaCreditoComp cronogramaCreditoComp = new CronogramaCreditoComp();
		List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();

		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		
		BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
		BigDecimal bdMontoPrestamo = beanExpedienteCredito.getBdMontoSolicitado();
		BigDecimal bdMontoCuota = BigDecimal.ZERO;
		MathContext mc1 = new MathContext(2, RoundingMode.HALF_UP);  
		String strFechaMes="";
		String strFechaDia="";
		
		try {

			recuperarTipoCronograma(strMontoACancelar);

				// se agrega la primera cuota el monto q cancelo...
				bdMontoGenerarCronograma = new BigDecimal(strMontoACancelar);
				//bdMontoCuota = bdMontoGenerarCronograma.divide(new BigDecimal(intNroCuotas - 1),2,RoundingMode.HALF_UP);

				bdMontoCuota = bdMontoGenerarCronograma;
				bdMontoGenerarCronograma = bdMontoCuota;
				intNroCuotas = new Integer(1);

			// y se conforma el cronograma
			//BigDecimal bdAmortizacion = bdMontoGenerarCronograma;
			BigDecimal bdSumaAmortizacion = BigDecimal.ZERO;
			BigDecimal bdSumaFaltaCancelar = bdMontoPrestamo;

				// Cuota CERO -  TRANSFERECNIA.
				bdMontoCancelar = bdMontoCancelar.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
				bdSumaAmortizacion = bdSumaAmortizacion.add(bdMontoCancelar);
				
				// generando cronograma para guardar
				cronogramaCredito = new CronogramaCredito();
				cronogramaCredito.setId(new CronogramaCreditoId());
				cronogramaCredito.setIntNroCuota(0);
				cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_REGULARIZACION);
				cronogramaCredito.setIntParaFormaPagoCod(0);
				cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
				cronogramaCredito.setBdMontoConcepto(bdMontoCancelar);
				cronogramaCredito.setBdMontoCapital(bdSumaFaltaCancelar);
				//cronogramaCredito.setTsFechaVencimiento(new Timestamp(new Date().getTime()));
				//cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(new Date().getTime()).toString()));
				cronogramaCredito.setTsFechaVencimiento(new Timestamp(calFechaRegistro.getTime().getTime()));
				cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(calFechaRegistro.getTime().getTime()).toString()));
				cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				listaCronogramaCredito.add(cronogramaCredito);

				// Generando Cronograma para mostrar
				cronogramaCreditoComp = new CronogramaCreditoComp();
				//cronogramaCreditoComp.setStrFechaVencimiento(Constante.sdf.format(new Date().getTime()).toString());
				cronogramaCreditoComp.setStrFechaVencimiento(Constante.sdf.format(calFechaRegistro.getTime().getTime()).toString());
				cronogramaCreditoComp.setBdAmortizacion(bdMontoCancelar);
				cronogramaCreditoComp.setBdSaldoCapital(bdSumaFaltaCancelar);
				//cronogramaCreditoComp.setIntParaTipoFormapago(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
				cronogramaCreditoComp.setCronogramaCredito(cronogramaCredito);
				listaCronogramaCreditoComp.add(cronogramaCreditoComp);
			
			beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);			
			beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
			bdMontoDeCuotas = bdMontoGenerarCronograma;


		} catch (Exception e) {
			log.error("Error al generarCronogramaAdelantoTotal() ---> "+e);
			e.printStackTrace();
		}

}
	
	/**
	 * Genera el cronogramo en el caso que se seleccione el tipo contado.
	 * Genera una sola cuota
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public void generarCronogramaContado(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle, Calendar calFechaRegistro ) {
		
		CronogramaCredito cronogramaCredito = null;
		CronogramaCreditoComp cronogramaCreditoComp = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		SimpleDateFormat sdfPeriodo = null;
		BigDecimal bdMontoGenerarCronograma = BigDecimal.ZERO;
		
		try {
			 cronogramaCredito = new CronogramaCredito();
			 cronogramaCreditoComp = new CronogramaCreditoComp();
			 listaCronogramaCredito = new ArrayList<CronogramaCredito>();
			 sdfPeriodo = new SimpleDateFormat("yyyyMM");
			 
				// se agrega la primera cuota el monto q cancelo.
				bdMontoGenerarCronograma = creditoInteresExpediente.getBdMontoMaximo();
				intNroCuotas = new Integer(1);
				bdMontoGenerarCronograma = bdMontoGenerarCronograma.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
				//Cronograma para guardar
				cronogramaCredito = new CronogramaCredito();
				cronogramaCredito.setId(new CronogramaCreditoId());
				cronogramaCredito.setIntNroCuota(0);
				cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_CONTADO);
				cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_CAJA);
				cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
				cronogramaCredito.setBdMontoConcepto(bdMontoGenerarCronograma);
				cronogramaCredito.setBdMontoCapital(bdMontoGenerarCronograma);
				cronogramaCredito.setTsFechaVencimiento(new Timestamp(calFechaRegistro.getTime().getTime()));
				cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(calFechaRegistro.getTime().getTime()).toString()));
				cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				listaCronogramaCredito.add(cronogramaCredito);
				//Cronograma para mostrar
				cronogramaCreditoComp = new CronogramaCreditoComp();
				cronogramaCreditoComp.setStrFechaVencimiento(Constante.sdf.format(calFechaRegistro.getTime().getTime()).toString());
				cronogramaCreditoComp.setBdAmortizacion(bdMontoGenerarCronograma);
				cronogramaCreditoComp.setBdSaldoCapital(bdMontoGenerarCronograma);
				cronogramaCreditoComp.setCronogramaCredito(cronogramaCredito);
				listaCronogramaCreditoComp.add(cronogramaCreditoComp);
				beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);			
				beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
				bdMontoDeCuotas = bdMontoGenerarCronograma;
				beanExpedienteCredito.setBdMontoTotal(bdMontoDeCuotas);
		} catch (Exception e) {
			log.error("Error al generarCronogramaAdelantoTotal() ---> "+e);
			e.printStackTrace();
		}
}	

	/**
	 * 
	 * @param strInicio
	 * @param strFin
	 * @return
	 */
	public Integer calcularNrodeMesesEntreDosFechasString(String strInicio, String strFin){
		Integer intNroDiferenciaMeses = 0;
		
		String strMesI=strInicio.substring(3, 5);
		String strAnnoI=strInicio.substring(6);
		String strMesF=strFin.substring(3, 5);
		String strAnnoF=strFin.substring(6);
		
		try {
			Integer intMesInicio = Integer.parseInt(strInicio.substring(3,5)); // dd/mm/yyyyy
			Integer intAnnoInicio = Integer.parseInt(strInicio.substring(6));
			Integer intMesFin = Integer.parseInt(strFin.substring(3,5));
			Integer intAnnoFin = Integer.parseInt(strFin.substring(6));
			
			
			// calculando nro de meses entre dos fechas
			if(intAnnoInicio.compareTo(intAnnoFin)==0){
				intNroDiferenciaMeses = intMesFin - (intMesInicio -1);
			}else{
				intNroDiferenciaMeses = 12 - (intMesInicio - 1) + (intMesFin);
			}
		} catch (NumberFormatException e) {
			log.error("Error en calcularNrodeMesesEntreDosFechasString ---> "+e);
		}
		return intNroDiferenciaMeses;
	}
	
	
	
	/**
	 * Calcula la fecha de Envio Inicial
	 * @param estructuraDetalle
	 * @return
	 */
	public Calendar xcalculoInicialFechasEnvio(EstructuraDetalle estructuraDetalle, Calendar calFechaRegistro){
		Envioconcepto envioConcepto = null;
		Calendar miCal = Calendar.getInstance();
		Calendar fecHoy = Calendar.getInstance();
		Calendar fec1erEnvio = Calendar.getInstance();
		Calendar envio = Calendar.getInstance();
		
		miCal.clear();
		fecHoy.clear();
		fec1erEnvio.clear();
		envio.clear();
		
		miCal.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		fecHoy.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		fec1erEnvio.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
		envio.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));

		String strPeriodoPlla = "";
		//Envioconcepto envioConcepto = null;
		
		try{
			envioConcepto = new Envioconcepto();
			envioConcepto.setId(new EnvioconceptoId());
			envioConcepto.getId().setIntEmpresacuentaPk(0);
			envioConcepto.getId().setIntItemenvioconcepto(0);
			envioConcepto.getId().setIntItemenvioconceptoDet(0);

			envioConcepto = planillaFacade.getEnvioconceptoPorPkMaxPeriodo(envioConcepto.getId());

			// no se realiza calculo de fechas de envio y vencimiento...
			if (envioConcepto != null) {
				strPeriodoPlla = "" + envioConcepto.getIntPeriodoplanilla();

				// substring x---o x--->
				Calendar calendarTemp = Calendar.getInstance();
				calendarTemp.clear();
				calendarTemp.set(calFechaRegistro.get(Calendar.YEAR), calFechaRegistro.get(Calendar.MONTH), calFechaRegistro.get(Calendar.DATE));
				
				int intUltimoMesPlla = Integer.parseInt(strPeriodoPlla.substring(0, 2));
				int intUltimoAnnoPlla = Integer.parseInt(strPeriodoPlla.substring(2));
				int intPrimerDiaPlla = Integer.parseInt("01");

				if (intUltimoMesPlla == 12) {
					intUltimoAnnoPlla = intUltimoAnnoPlla + 1;
					intUltimoMesPlla = 0;
				}

				// Definiendo fecha de 1er envio y 1er vencimiento
				fec1erEnvio.clear();
				fec1erEnvio.set(intUltimoAnnoPlla, intUltimoMesPlla + 1,intPrimerDiaPlla);

				calendarTemp.set(fec1erEnvio.get(Calendar.YEAR),
				fec1erEnvio.get(Calendar.MONTH),
				fec1erEnvio.get(Calendar.DATE));

			} else {
					int dia = miCal.get(Calendar.DATE);
					int mes = miCal.get(Calendar.MONTH);
					int anno = miCal.get(Calendar.YEAR);
					// fecHoy.clear();
					fecHoy.set(anno, mes, dia);
					List listaEnvioVencimiento = new ArrayList();

				listaEnvioVencimiento.addAll(xcalcular1raFechaEnvioVencimiento(estructuraDetalle, fecHoy));
				fec1erEnvio = (Calendar) listaEnvioVencimiento.get(0);
			}

			// Calculamos el resto de dias de envio y vencimiento en base a
			envio.set(fec1erEnvio.get(Calendar.YEAR),
			fec1erEnvio.get(Calendar.MONTH),
			fec1erEnvio.get(Calendar.DATE));
		
			List listaDiasEnvio = new ArrayList();
			int envDia, envMes, envAnno;

			envDia = envio.get(Calendar.DATE);
			envMes = envio.get(Calendar.MONTH);
			envAnno = envio.get(Calendar.YEAR);
		}catch(Exception e){
			log.error("Error en"+e);
		}
		
		return fec1erEnvio;
	}

	
	/**
	 * Calcula la fecha del ler Envio y 1er Vencimiento.
	 * Toma en cuenta la existencia de salto al siguiente mes.
	 * @param estructuraDetalle
	 * @param fecHoy
	 * @return listaEnvioVencimiento (0, fec1erEnvio)(1, fec1erVenc);
	 */
	public String calcular1raFechaVencimiento(EstructuraDetalle estructuraDetalle) {
//		Calendar fecEnvioTemp = Calendar.getInstance();
//		String miFechaPrimerVenc = null;
//		Calendar fec1erVenc  = Calendar.getInstance();
		Calendar fecVenc1 	 = Calendar.getInstance();
//		Calendar fecVenc2 	 = Calendar.getInstance();
//		Calendar fecVenc3 	 = Calendar.getInstance();
		//Calendar fec1erEnvio = Calendar.getInstance();
//		List listaEnvioVencimiento = new ArrayList();

		Calendar miFecha = Calendar.getInstance();
		CapacidadCreditoComp capacidadMaximoEnvio =null;
		String strFecha1erVencimiento = "";
		try {
			miFecha.clear();
			int intFechRegDia = new Integer(Constante.sdf.format(dtFechaRegistro).substring(0,2));
			int intFechRegMes = new Integer(Constante.sdf.format(dtFechaRegistro).substring(3,5))-1;
			int intFechRegAno = new Integer(Constante.sdf.format(dtFechaRegistro).substring(6,10));
			
			// En la clase calendar enero es 0
			if(intFechRegMes == 1){
				intFechRegMes = 0;
			}
			
			miFecha.set(intFechRegAno, intFechRegMes, intFechRegDia);
			//Integer intPeridoEnvio = 0;		
			capacidadMaximoEnvio = recuperarPerdiodoUltimoEnvio(beanSocioComp);			
			// SI NO HAY ENVIOS para todas las u.e. del soccio
			if(capacidadMaximoEnvio == null){
				// vencimiento
				//Date dtFechaVenc = new Date();
				Calendar clFechaVencTemp = Calendar.getInstance();
				clFechaVencTemp.setTime(dtFechaRegistro);
				clFechaVencTemp = getUltimoDiaDelMesCal(clFechaVencTemp);
				fecVenc1.clear();
				fecVenc1= clFechaVencTemp;
				
				if ((new Integer(Constante.sdf.format(dtFechaRegistro).substring(0,2))) > estructuraDetalle.getIntDiaEnviado()) {
					fecVenc1.add(Calendar.MONTH, 1);
					fecVenc1 = getUltimoDiaDelMesCal(fecVenc1);
				}
				if(estructuraDetalle.getIntSaltoEnviado().compareTo(2)==0){
					fecVenc1.add(Calendar.MONTH, 1);
					fecVenc1 = getUltimoDiaDelMesCal(fecVenc1);
				}
			}else{	
				// existe envios
				// vencimiento
				int intAnno = Integer.parseInt(capacidadMaximoEnvio.getSocioEstructura().getIntUltimoPeriodo().toString().substring(0,4));
				int intMes = Integer.parseInt(capacidadMaximoEnvio.getSocioEstructura().getIntUltimoPeriodo().toString().substring(4,6));
				int intDia = 15;
				String strFechaPeriodo = "";
				strFechaPeriodo = intDia+"/"+intMes+"/"+intAnno;
				
				Calendar clFechaVencTemp = Calendar.getInstance();
				clFechaVencTemp.setTime(Constante.sdf.parse(strFechaPeriodo));
				clFechaVencTemp.add(Calendar.MONTH, 1);
				
				clFechaVencTemp = getUltimoDiaDelMesCal(clFechaVencTemp);
				fecVenc1.clear();
				fecVenc1= clFechaVencTemp;
			}
			strFecha1erVencimiento = Constante.sdf.format(fecVenc1.getTime());
		} catch (NumberFormatException e) {
			log.error("Error NumberFormatException en calcular1raFechaEnvioVencimiento ---> "+e);
			e.printStackTrace();
		} catch (ParseException e) {
			log.error("Error ParseException en calcular1raFechaEnvioVencimiento---> "+e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Error general en calcular1raFechaEnvioVencimiento---> "+e);
		}
		
		return strFecha1erVencimiento;
	}

	
	
	/**
	 * Retorna la fecha del ultimo dia del mes
	 * @param Calendar
	 * @return Date
	 */
	public Calendar getUltimoDiaDelMesCal(Calendar fecha) {
		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMaximum(Calendar.DAY_OF_MONTH),
				fecha.getMaximum(Calendar.HOUR_OF_DAY),
				fecha.getMaximum(Calendar.MINUTE),
				fecha.getMaximum(Calendar.SECOND));
		return fecha;
	}
	
	
	/**
	 * Recupera el maximo periodo de los envios realizados por la unidades ejecutoras existenrtes en la solicitud.
	 * @return
	 */
	public CapacidadCreditoComp recuperarPerdiodoUltimoEnvio(SocioComp socioComp){
		CapacidadCreditoComp capacidadMaximoEnvio = null;
		List<CapacidadCreditoComp> lstCapacidadesComp = null;
		List<SocioEstructura> listSocioEstructura = null;
		try {
			
			listSocioEstructura  = socioComp.getSocio().getListSocioEstructura();
			
			if(listSocioEstructura != null && !listSocioEstructura.isEmpty()){
				lstCapacidadesComp = new ArrayList<CapacidadCreditoComp>();
				for (SocioEstructura socioEstructura : listSocioEstructura) {
					CapacidadCreditoComp capacidadCreditoComp= new CapacidadCreditoComp();
					capacidadCreditoComp.setSocioEstructura(socioEstructura);
					
					EstructuraId estructuraId = new EstructuraId();
					Integer intPeriodoRecuperado= 0;
					Integer intTipoSocio = 0;
					Integer intModalidad = 0;
					Integer intEmpresa = 0;
					
					intEmpresa = socioEstructura.getIntEmpresaUsuario();
					intTipoSocio = socioComp.getSocio().getSocioEstructura().getIntTipoSocio();
					intModalidad = socioComp.getSocio().getSocioEstructura().getIntModalidad();
					estructuraId.setIntCodigo(socioEstructura.getIntCodigo());
					estructuraId.setIntNivel(socioEstructura.getIntNivel());
					
					intPeriodoRecuperado = planillaFacade.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(intEmpresa, estructuraId, intTipoSocio, intModalidad);
					if(intPeriodoRecuperado != null){
						socioEstructura.setIntUltimoPeriodo(intPeriodoRecuperado);
						capacidadCreditoComp.setSocioEstructura(socioEstructura);
						lstCapacidadesComp.add(capacidadCreditoComp);
					}
				}
				
				if(lstCapacidadesComp != null && !lstCapacidadesComp.isEmpty()){
					int intTam = lstCapacidadesComp.size();
					capacidadMaximoEnvio = new CapacidadCreditoComp();
					capacidadMaximoEnvio = lstCapacidadesComp.get(0);
					for (int j = 0; j < intTam; j++) {
						if (lstCapacidadesComp.get(j).getSocioEstructura().getIntUltimoPeriodo() > capacidadMaximoEnvio.getSocioEstructura().getIntUltimoPeriodo()) {
							capacidadMaximoEnvio = lstCapacidadesComp.get(j);
						}						
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en recuperarPerdiodoUltimoEnvio ---> "+e);
		}
		return capacidadMaximoEnvio;
		
	}
	
	/**
	 * Para el calculo de los numeros de cuotas
	 * @param estructuraDetalle
	 * @param fecHoy
	 * @return
	 */
	public List xcalcular1raFechaEnvioVencimiento(EstructuraDetalle estructuraDetalle, Calendar fecHoy) {
			Calendar fecEnvioTemp = Calendar.getInstance();
			String miFechaPrimerVenc = null;
			Calendar fecVenc1 	 = Calendar.getInstance();
			Calendar fecVenc2 	 = Calendar.getInstance();
			Calendar fecVenc3 	 = Calendar.getInstance();
			Calendar fec1erEnvio = Calendar.getInstance();
			List listaEnvioVencimiento = new ArrayList();

			if ((fecHoy.get(Calendar.DATE)) < estructuraDetalle.getIntDiaEnviado()) {
				fecEnvioTemp.clear();
				fecEnvioTemp.set(fecHoy.get(Calendar.YEAR),
								fecHoy.get(Calendar.MONTH),
								estructuraDetalle.getIntDiaEnviado());

			} else { // Salta al mes siguiente
				fecEnvioTemp.clear();
				fecEnvioTemp.set(fecHoy.get(Calendar.YEAR),
						fecHoy.get(Calendar.MONTH) + 1,
						estructuraDetalle.getIntDiaEnviado());
			}
			fec1erEnvio = fecEnvioTemp;
			fec1erEnvio.set(fec1erEnvio.get(Calendar.YEAR),	fec1erEnvio.get(Calendar.MONTH),estructuraDetalle.getIntDiaEnviado());
			listaEnvioVencimiento.add(0, fec1erEnvio);

			return listaEnvioVencimiento;
		}
	
	/**
	 * Clacula cuotas dif entre fech hoy y fin credito
	 * @param credito
	 * @return
	 */
	private String calcularNroCuotasActividad(Credito credito){
		//String strNroCuotas = null;
		Date dtInicio = dtHoy;
		Date dtFin = new Date(credito.getStrDtFechaFin());
		Integer intNroMeses = 0;
		
		intNroMeses = diferenciaEntreDosDateEnMeses(dtInicio, dtFin);

		
		
		return ""+intNroMeses;
	}
	
	/**
	 * Devuelve el nro de dias entres 2 fechas date:
	 * @param fInicial
	 * @param fFinal
	 * @return
	 */
	 private int diferenciaEntreDosDateEnMeses(Date fInicial, Date fFinal)
	    {
		 
		 	System.out.println("fInicial ----> "+ fInicial);
		 	System.out.println("fFinal ----> "+ fFinal);
	        Calendar ci = Calendar.getInstance();
	        ci.setTime(fInicial);

	        Calendar cf = Calendar.getInstance();
	        cf.setTime(fFinal);

	        long ntime = cf.getTimeInMillis() - ci.getTimeInMillis();

	        return (int)Math.ceil((double)ntime / 1000 / 3600 / 24 / 30);
	    }


		/**
		 * Calcula el nro de dias entre 2 fechas Calendar
		 * @param Date fechaInicial
		 * @param Date fechaFinal
		 * @return int dias
		 */
		public int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) {

			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			String fechaInicioString = df.format(fechaInicial);
			try {
				fechaInicial = df.parse(fechaInicioString);
			} catch (ParseException ex) {
				log.error("error: " + ex);
			}

			String fechaFinalString = df.format(fechaFinal);
			try {
				fechaFinal = df.parse(fechaFinalString);
			} catch (ParseException ex) {
				log.error("error: " + ex);
			}

			long fechaInicialMs = fechaInicial.getTime();
			long fechaFinalMs = fechaFinal.getTime();
			long diferencia = fechaFinalMs - fechaInicialMs;
			double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));

			return ((int) dias);
		}

		/**
		 * Retorna la fecha del ultimo dia del mes
		 * @param Calendar
		 * @return Date
		 */
		public Date getUltimoDiaDelMes(Calendar fecha) {
			Calendar calReturn = Calendar.getInstance();
			try {
				calReturn.clear();
				calReturn.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
							fecha.getActualMaximum(Calendar.DAY_OF_MONTH),
							fecha.getMaximum(Calendar.HOUR_OF_DAY),
							fecha.getMaximum(Calendar.MINUTE),
							fecha.getMaximum(Calendar.SECOND));

				} catch (Exception e) {
				log.error("Error getUltimoDiaDelMes --->  "+e);
			}
			
			return calReturn.getTime();
		}

		/**
		 * Retorna el primer dia del mes
		 * @param Calendar
		 * @return Date
		 */
		public Date getPrimerDiaDelMes(Calendar fecha) {

			fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
					fecha.getActualMinimum(Calendar.DAY_OF_MONTH),
					fecha.getMinimum(Calendar.HOUR_OF_DAY),
					fecha.getMinimum(Calendar.MINUTE),
					fecha.getMinimum(Calendar.SECOND));

			return fecha.getTime();
		}

		/**
		 * Convierte una cadena a Calendar
		 * @param fecha
		 * @return Calendar cal
		 */
		public Calendar StringToCalendar(String fecha) {
			DateFormat formatter;
			Date date;
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();

			try {
				date = (Date) formatter.parse(fecha);
				cal.setTime(date);
			} catch (ParseException e) {
				System.out.println("Exception :" + e);
			}

			return cal;
		}
		
	/**
	 * Valida los campos: Tipo de Operacion, Monto Solicitado, Observacion y
	 * Unidades Ejecutoras (se debe registrar al menos uno) del Expediente.
	 * 
	 * @param beanExpedienteCredito
	 * @return
	 */
	private Boolean isValidoExpedienteActividad(ExpedienteCredito beanExpedienteCredito) {
		Boolean validExpedienteLiquidacion = true;

		try {
			if (beanExpedienteCredito.getStrObservacion() == null || beanExpedienteCredito.getStrObservacion().equals("")) {
				setMsgTxtObservacion("* El campo de Observación debe ser ingresado.");
				validExpedienteLiquidacion = false;
			} else {
				setMsgTxtObservacion("");
			}

		} catch (Exception e) {
			log.error("error en isValidoExpedienteRefinan --> "+e);
		}
		return validExpedienteLiquidacion;
	}
	
	/**
	 * Valida que exista Capacidad de Credito, Cronograma, Garantias y los
	 * Requisitos del Credito (Adjuntos) de acuerdo a lo configurado.
	 * 
	 * @return
	 */
	private int isValidTodaSolicitud() {
		int cnt = 0;
		
		if(intValidarDatosTipoSolicitud.compareTo(Constante.PARAM_T_TIPO_SOLICITUD_ACTIVIDAD_CREDITO)==0){
			if (listaCronogramaCreditoComp != null && listaCronogramaCreditoComp.size() <= 0) {
				cnt++;
			}
		}

		if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {

				if (requisitoCreditoComp.getArchivoAdjunto() == null && requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1) {
					cnt++;
				}
			}
		}
		return cnt;
	}
	

	
	public String recuperarCondicionSubCondicionSocio (Cuenta cuentaSocio){
		String condicion = "";
		
		
		if(cuentaSocio != null){
			// Recuperndo condicion
			for (Tabla tablaCondicion : listaCondicionSocio) {
				if(cuentaSocio.getIntParaCondicionCuentaCod().compareTo(tablaCondicion.getIntIdDetalle())==0){
					condicion = condicion + tablaCondicion.getStrDescripcion();
					break;
				}
			}
			// Recuperando tipo condicion
			for (Tabla tablaTipoCondicion : listaTipoCondicionSocio) {
				if(cuentaSocio.getIntParaSubCondicionCuentaCod().compareTo(tablaTipoCondicion.getIntIdDetalle())==0){
					condicion = condicion + " - "+tablaTipoCondicion.getStrDescripcion();
					break;
				}
			}
		}

		return condicion ;
	}
	
	public void isValidCondicionCapacidadPago(){
		blnHayError = Boolean.FALSE;
		try {
			beanCredito   = creditoFacade.getCreditoPorIdCredito(beanCredito.getId());
			strMsgCondicionCapacidadPagoSocio = "";
			strCondicionSocio = recuperarCondicionSubCondicionSocio(beanSocioComp.getCuenta());
						
			if (beanCredito != null){
				if(beanCredito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) ==  0) {

				//if(credito.getIntParaEstadoCod() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO){
					if (beanCredito.getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
						if (beanSocioComp.getSocio().getSocioEstructura() != null) {
							boolean boFlag = false;
							
							for (int k = 0; k < beanSocioComp.getPersona().getNatural().getListaPerLaboral().size(); k++) {
								if (beanCredito.getIntParaCondicionLaboralCod() == 
								(beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(k).getIntCondicionLaboral())) {
									boFlag = true;
								}
								if (beanCredito.getIntParaCondicionLaboralCod().equals(
								(beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(k).getIntCondicionLaboral()))) {
									boFlag = true;
								}
							}

							if ((beanCredito.getIntParaCondicionLaboralCod().compareTo(-1)==0)|| (boFlag)) {
								// Verificar la condición de la cuenta
								if (beanCredito.getListaCondicion() != null && !beanCredito.getListaCondicion().isEmpty()) {
									forCondiciom:
									for (CondicionCredito condicionCredito : beanCredito.getListaCondicion()) {
										if (beanSocioComp.getCuenta().getIntParaCondicionCuentaCod().equals(
											condicionCredito.getId().getIntParaCondicionSocioCod())
											&& condicionCredito.getIntValor() == 1) {
											
											// Verificar si la cuenta está activa o inactiva
											if (beanCredito.getListaCondicionHabil() != null && !beanCredito.getListaCondicionHabil().isEmpty()) {
												// forCondicionHabil:
												for (CondicionHabil condicionHabil : beanCredito.getListaCondicionHabil()) {
													
													// verificando condicion de regular
													if (beanSocioComp.getCuenta().getIntParaSubCondicionCuentaCod().equals(condicionHabil.getId().getIntParaTipoHabilCod())
														&& condicionHabil.getIntValor().compareTo(new Integer(1)) == 0) {
														
															break forCondiciom;
														//msgTxtSubCondicionCuenta ="";
													} else{
														blnHayError = Boolean.TRUE;
														strCondicionSocio = strCondicionSocio +" / No se superó la validación de Sub Condición de la Cuenta Habil.";
														//msgTxtSubCondicionCuenta = "No se superó la validación de Sub Condición de la Cuenta Habil.";
														}

												}

												//msgTxtCondicionHabil = "";
											} else {
												blnHayError = Boolean.TRUE;
												strCondicionSocio = strCondicionSocio +" / No existen Condiciones Hábiles asociadas al credtio configurado.";
												//msgTxtCondicionHabil = "No existen Condiciones Hábiles asociadas al credtio configurado. ";
											}
											
											//msgTxtCondicionSocio = "";
										}else{
											blnHayError = Boolean.TRUE;
											strCondicionSocio = strCondicionSocio +" / No se supero la validación de Condición de Cuenta de Socio.";
											//msgTxtCondicionSocio = "No se supero la validación de Condición de Cuenta de Socio.";
										}
									}
									//msgTxtCondicionLaboral = "";
								}else{
									blnHayError = Boolean.TRUE;
									strCondicionSocio = strCondicionSocio +" / No existen Condiciones asociadas al credito configurado.";
									//msgTxtCondicionLaboral = "No existen Condiciones asociadas al credito configurado.";
									}
								
							}  else { 
								blnHayError = Boolean.TRUE;
								strCondicionSocio = strCondicionSocio +" / No se supero la validación de Condición Laboral.";
								//msgTxtCondicionLaboral = "No se supero la validación de Condición Laboral.";}
							}
						}

					}

				}
				
				
				
				if(blnHayError){
					strMsgCondicionCapacidadPagoSocio =  strCondicionSocio;
				}else{
					strMsgCondicionCapacidadPagoSocio = strCondicionSocio;
				}
			}
		} catch (BusinessException e) {
			log.error("Error en isValidCondicionCapacidadPago ---> "+e);
			e.printStackTrace();
		}
		
		
	}
	
	
	
	/**
	 * Muestra dinamicamente los ventanas para adjuntar documentos de Solicitud
	 * de Credito. En base a la cofiguracion del credito.
	 * 
	 * @param event
	 */
	public void mostrarArchivosAdjuntos(ActionEvent event) {
		ConfSolicitudFacadeRemote facade = null;
		TablaFacadeRemote tablaFacade = null;
		EstructuraFacadeRemote estructuraFacade = null;
		ConfServSolicitud confServSolicitud = null;
//		String strToday = Constante.sdf.format(new Date());
//		Date dtToday = null;
		List<ConfServSolicitud> listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
		EstructuraDetalle estructuraDet = null;
		List<EstructuraDetalle> listaEstructuraDet = new ArrayList<EstructuraDetalle>();
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		RequisitoCreditoComp requisitoCreditoComp;
//		try {
//			dtToday = Constante.sdf.parse(strToday);
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
		try {
			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			confServSolicitud = new ConfServSolicitud();
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);
			confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD);
			confServSolicitud.setIntParaSubtipoOperacionCod(intTipoSolicitudSubActividad);// 
			listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, 1, beanCredito);

			if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
				forSolicitud: 
				for (ConfServSolicitud solicitud : listaDocAdjuntos) {
					if (solicitud.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_ACTIVIDAD)) {
						if (solicitud.getIntParaSubtipoOperacionCod().equals(intTipoSolicitudSubActividad)) {
							if(solicitud.getListaCredito() != null){
								for (ConfServCredito ConfServCredito : solicitud.getListaCredito()) {
									
									if((ConfServCredito.getIntParaTipocreditoCod().compareTo(beanCredito.getId().getIntParaTipoCreditoCod())==0) 
											&&(ConfServCredito.getIntSocioItemCredito().compareTo(beanCredito.getId().getIntItemCredito())==0)){
										if (solicitud.getListaEstructuraDetalle() != null) {
											
											for (ConfServEstructuraDetalle estructuraDetalle : solicitud.getListaEstructuraDetalle()) {
												estructuraDet = new EstructuraDetalle();
												estructuraDet.setId(new EstructuraDetalleId());
												estructuraDet.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
												estructuraDet.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
												listaEstructuraDet = estructuraFacade.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
																estructuraDet.getId(),beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(),
																beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());

												if (listaEstructuraDet != null && listaEstructuraDet.size() > 0) {
													for (EstructuraDetalle estructDetalle : listaEstructuraDet) {
														if (estructuraDetalle.getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())
															&& estructuraDetalle.getIntNivelPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())
															&& estructuraDetalle.getIntCaso().equals(estructDetalle.getId().getIntCaso())
															&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())) {
															if (solicitud.getListaDetalle() != null	&& solicitud.getListaDetalle().size() > 0) {
																
																List<RequisitoCreditoComp> listaRequisitoCreditoCompTemp = new ArrayList<RequisitoCreditoComp>();
																for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
																	System.out.println("detalle.getId().getIntItemDetalle()"+ detalle.getId().getIntItemDetalle());
																	System.out.println("detalle.getId().getIntItemSolicitud()"+ detalle.getId().getIntItemSolicitud());
																	System.out.println("detalle.getId().getIntPersEmpresaPk()"+ detalle.getId().getIntPersEmpresaPk());
																	
																	if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId().getIntPersEmpresaPk())
																		&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalle.getId().getIntItemSolicitud())) {
																		
																		requisitoCreditoComp = new RequisitoCreditoComp();
																		requisitoCreditoComp.setDetalle(detalle);
																		//listaRequisitoCreditoComp.add(requisitoCreditoComp);
																		listaRequisitoCreditoCompTemp.add(requisitoCreditoComp);
																	}
																}
																													
																List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();
																
																// validamos que solo se muestre las de agrupamioento A.
																listaTablaRequisitos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDADES));
																	for(int i=0;i<listaTablaRequisitos.size();i++){	
																		for(int j=0 ; j<listaRequisitoCreditoCompTemp.size();j++){
																			if((listaRequisitoCreditoCompTemp.get(j).getDetalle().getIntParaTipoDescripcion().intValue()) ==
																				(listaTablaRequisitos.get(i).getIntIdDetalle().intValue())){
																				listaRequisitoCreditoComp.add(listaRequisitoCreditoCompTemp.get(j));
																			}
																		}
																	}	
																break forSolicitud;
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Permite descargar el requisito de un Expediente
	 */
	public void descargaArchivoUltimo() {
		TipoArchivo tipoArchivo = null;
		String strNombreArchivo = null;
		String strParaTipoCod = null;
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.setContentType("application/force-download");
		
		strNombreArchivo = getRequestParameter("strNombreArchivo");
		strParaTipoCod = getRequestParameter("intParaTipoCod");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + strNombreArchivo + "\"");
		
		byte[] buf = new byte[1024];
		try{
			tipoArchivo = new TipoArchivo();
			tipoArchivo = generalFacade.getTipoArchivoPorPk(new Integer(strParaTipoCod));
			String ruta = tipoArchivo.getStrRuta()+ "\\"+ strNombreArchivo;
			String realPath = ruta;
			File file = new File(realPath);
			long length = file.length();
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			ServletOutputStream out = response.getOutputStream();
			response.setContentLength((int)length);
			while ((in != null) && ((length = in.read(buf)) != -1)) {
				out.write(buf, 0, (int)length);
			}
			in.close();
			out.flush();
			out.close();
			
			FacesContext.getCurrentInstance().responseComplete();
		}catch (Exception exc){
			exc.printStackTrace();
		} 
	
	}
	

	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException {

		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDAD_BOLETA_PAGO)
							//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
							&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
						// log.info("byteImg.length: "+fileupload.getDataImage().length);
						byte[] byteImg = fileupload.getDataImage();
						MyFile file = new MyFile();
						file.setLength(byteImg.length);
						file.setName(fileupload.getObjArchivo()
								.getStrNombrearchivo());
						file.setData(byteImg);
						requisitoCreditoComp.setFileDocAdjunto(file);
						break;
					}
				}
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDAD_SOLICITUD)
							//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
							&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
						// log.info("byteImg.length: "+fileupload.getDataImage().length);
						byte[] byteImg = fileupload.getDataImage();
						MyFile file = new MyFile();
						file.setLength(byteImg.length);
						file.setName(fileupload.getObjArchivo()
								.getStrNombrearchivo());
						file.setData(byteImg);
						requisitoCreditoComp.setFileDocAdjunto(file);
						break;
					}
				}

			}
		}
	}

	
	
	/**
	 * Metodo q adjunta documento
	 * @param event
	 */
	public void adjuntarDocumento(ActionEvent event) {
		String strParaTipoDescripcion = getRequestParameter("intParaTipoDescripcion");
		String strParaTipoOperacionPersona = getRequestParameter("intParaTipoOperacionPersona");
		Integer intParaTipoDescripcion = new Integer(strParaTipoDescripcion);
		Integer intParaTipoOperacionPersona = new Integer( strParaTipoOperacionPersona);

		this.intParaTipoDescripcion = intParaTipoDescripcion;
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;

		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		fileupload.setStrDescripcion("Seleccione el archivo que desea adjuntar.");
		fileupload.setFileType(FileUtil.imageTypes);
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		
		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {
				
				//
				// PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDAD_BOLETA_PAGO
				
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDAD_BOLETA_PAGO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO);
				}
				
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_ACTIVIDAD_SOLICITUD)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO);
				}
			}
		}
		 //fileupload.setStrJsFunction("putFile");
		 fileupload.setStrJsFunction("putFileDocAdjunto()");
	}
	
	
	/**
	 * Devuelve las U. Ejecutoras del socio validado. 
	 * Concatenadas y cuyo estado sea activo.
	 */
	public void cargarDescripcionUEjecutorasConcatenadas(SocioComp socioComp){
		String strUnidadesEjecutoras = "";
		strUnidadesEjecutorasConcatenadas = "";
		List<SocioEstructura> listSocioEstructura = null;
		String strModalidad = "";
		String strTipoSocio = "";
		
		try {
			if(socioComp != null){
				getListEstructura();
				listSocioEstructura  = socioComp.getSocio().getListSocioEstructura();
				if(listSocioEstructura != null && !listSocioEstructura.isEmpty()){
					for (SocioEstructura socioEstructura : listSocioEstructura) {
						//socioEstrConcat = beanSocioComp.getSocio().getListSocioEstructura().get(e);
						for (Estructura estructura : listEstructura) {
							if(estructura.getId().getIntCodigo().compareTo(socioEstructura.getIntCodigo())==0
							&& socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){

								for (Tabla modalidad : listaDescripcionModalidad) {
									strModalidad="";
									if(modalidad.getIntIdDetalle().compareTo(socioEstructura.getIntModalidad())==0){
										strModalidad = modalidad.getStrDescripcion();
										break;
									}
								}
								
								for (Tabla tipoSocio : listaDescripcionTipoSocio) {
									strTipoSocio="";
									if(tipoSocio.getIntIdDetalle().compareTo(socioEstructura.getIntTipoSocio())==0){
										strTipoSocio = tipoSocio.getStrDescripcion();
										break;
									}
								}

								strUnidadesEjecutoras = estructura.getJuridica().getStrRazonSocial() +" ("+strModalidad+"-"+strTipoSocio+")";
								strUnidadesEjecutorasConcatenadas = strUnidadesEjecutoras+  ". " + strUnidadesEjecutorasConcatenadas;
							}
							
						}			
					}

				}else{
					strUnidadesEjecutorasConcatenadas = "";
				}

			}else{
				strUnidadesEjecutorasConcatenadas = "";
			}
			
		} catch (Exception e) {
			log.error("Error en cargarDescripcionUEjecutorasConcatenadas --> "+e);
		}

	}
	
	
	
	/**
	 * Obtiene el periodo YYYYMM de la fecha actual.
	 * @return
	 * @throws Exception
	 */
	public Integer	obtenerPeriodoActual() throws Exception{
		String strPeriodo = "";
		Calendar cal = Calendar.getInstance();
		int año = cal.get(Calendar.YEAR);
		int mes = cal.get(Calendar.MONTH);
		mes = mes + 1; 
		if(mes<10){
			strPeriodo = año + "0" + mes;
		}else{
			strPeriodo  = año + "" + mes;
		}		
		return Integer.parseInt(strPeriodo);		
	}
	
//------------------------------- GETERS Y SETERS ---------------------------------------->
	

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		return sesion.getAttribute(beanName);
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		SolicitudActividadController.log = log;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getEMPRESA_USUARIO() {
		return EMPRESA_USUARIO;
	}

	public void setEMPRESA_USUARIO(Integer eMPRESA_USUARIO) {
		EMPRESA_USUARIO = eMPRESA_USUARIO;
	}

	public Integer getPERSONA_USUARIO() {
		return PERSONA_USUARIO;
	}

	public void setPERSONA_USUARIO(Integer pERSONA_USUARIO) {
		PERSONA_USUARIO = pERSONA_USUARIO;
	}

	public Integer getSUCURSAL_USUARIO_ID() {
		return SUCURSAL_USUARIO_ID;
	}

	public void setSUCURSAL_USUARIO_ID(Integer sUCURSAL_USUARIO_ID) {
		SUCURSAL_USUARIO_ID = sUCURSAL_USUARIO_ID;
	}

	public Integer getSUBSUCURSAL_USUARIO_ID() {
		return SUBSUCURSAL_USUARIO_ID;
	}

	public void setSUBSUCURSAL_USUARIO_ID(Integer sUBSUCURSAL_USUARIO_ID) {
		SUBSUCURSAL_USUARIO_ID = sUBSUCURSAL_USUARIO_ID;
	}

	public Date getDtHoy() {
		return dtHoy;
	}

	public void setDtHoy(Date dtHoy) {
		this.dtHoy = dtHoy;
	}

	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}

	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}






	public ExpedienteCredito getBeanExpedienteCredito() {
		return beanExpedienteCredito;
	}






	public void setBeanExpedienteCredito(ExpedienteCredito beanExpedienteCredito) {
		this.beanExpedienteCredito = beanExpedienteCredito;
	}






	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}






	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}






	public SocioFacadeRemote getSocioFacade() {
		return socioFacade;
	}






	public void setSocioFacade(SocioFacadeRemote socioFacade) {
		this.socioFacade = socioFacade;
	}






	public CreditoFacadeRemote getCreditoFacade() {
		return creditoFacade;
	}






	public void setCreditoFacade(CreditoFacadeRemote creditoFacade) {
		this.creditoFacade = creditoFacade;
	}






	public EstructuraFacadeRemote getEstructuraFacade() {
		return estructuraFacade;
	}






	public void setEstructuraFacade(EstructuraFacadeRemote estructuraFacade) {
		this.estructuraFacade = estructuraFacade;
	}






	public ConvenioFacadeRemote getConvenioFacade() {
		return convenioFacade;
	}






	public void setConvenioFacade(ConvenioFacadeRemote convenioFacade) {
		this.convenioFacade = convenioFacade;
	}






	public SolicitudPrestamoFacadeRemote getSolicitudPrestamoFacade() {
		return solicitudPrestamoFacade;
	}






	public void setSolicitudPrestamoFacade(
			SolicitudPrestamoFacadeRemote solicitudPrestamoFacade) {
		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
	}






	public ConceptoFacadeRemote getConceptofacade() {
		return conceptofacade;
	}






	public void setConceptofacade(ConceptoFacadeRemote conceptofacade) {
		this.conceptofacade = conceptofacade;
	}






	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}






	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}






	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}






	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
	}






	public PlanillaFacadeRemote getPlanillaFacade() {
		return planillaFacade;
	}






	public void setPlanillaFacade(PlanillaFacadeRemote planillaFacade) {
		this.planillaFacade = planillaFacade;
	}






	public CreditoGarantiaFacadeRemote getCreditoGarantiaFacade() {
		return creditoGarantiaFacade;
	}






	public void setCreditoGarantiaFacade(
			CreditoGarantiaFacadeRemote creditoGarantiaFacade) {
		this.creditoGarantiaFacade = creditoGarantiaFacade;
	}






	public List<Tabla> getListaTipoOperacion() {
		return listaTipoOperacion;
	}






	public void setListaTipoOperacion(List<Tabla> listaTipoOperacion) {
		this.listaTipoOperacion = listaTipoOperacion;
	}






	public List<Tabla> getListaTipoRelacion() {
		return listaTipoRelacion;
	}






	public void setListaTipoRelacion(List<Tabla> listaTipoRelacion) {
		this.listaTipoRelacion = listaTipoRelacion;
	}


	public CuentaFacadeRemote getCuentaFacade() {
		return cuentaFacade;
	}

	public void setCuentaFacade(CuentaFacadeRemote cuentaFacade) {
		this.cuentaFacade = cuentaFacade;
	}

	public Integer getIntValidarDatosTipoSolicitud() {
		return intValidarDatosTipoSolicitud;
	}

	public void setIntValidarDatosTipoSolicitud(Integer intValidarDatosTipoSolicitud) {
		this.intValidarDatosTipoSolicitud = intValidarDatosTipoSolicitud;
	}

	public String getStrSubTipoOperacion() {
		return strSubTipoOperacion;
	}

	public void setStrSubTipoOperacion(String strSubTipoOperacion) {
		this.strSubTipoOperacion = strSubTipoOperacion;
	}

	public Persona getPersonaValida() {
		return personaValida;
	}

	public void setPersonaValida(Persona personaValida) {
		this.personaValida = personaValida;
	}

	public String getStrMsgErrorValidarDatos() {
		return strMsgErrorValidarDatos;
	}

	public void setStrMsgErrorValidarDatos(String strMsgErrorValidarDatos) {
		this.strMsgErrorValidarDatos = strMsgErrorValidarDatos;
	}

	public List<Tabla> getListaEstadoPrestamo() {
		return listaEstadoPrestamo;
	}

	public void setListaEstadoPrestamo(List<Tabla> listaEstadoPrestamo) {
		this.listaEstadoPrestamo = listaEstadoPrestamo;
	}

	public String getStrUltimoEstado() {
		return strUltimoEstado;
	}

	public void setStrUltimoEstado(String strUltimoEstado) {
		this.strUltimoEstado = strUltimoEstado;
	}

	public List<ExpedienteCreditoComp> getListaExpedienteCreditoComp() {
		return listaExpedienteCreditoComp;
	}

	public void setListaExpedienteCreditoComp(
			List<ExpedienteCreditoComp> listaExpedienteCreditoComp) {
		this.listaExpedienteCreditoComp = listaExpedienteCreditoComp;
	}

	public ExpedienteCreditoComp getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}

	public void setRegistroSeleccionadoBusqueda(
			ExpedienteCreditoComp registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}
	
	public Boolean getBlnShowDivFormSolicitudActividad() {
		return blnShowDivFormSolicitudActividad;
	}

	public void setBlnShowDivFormSolicitudActividad(
			Boolean blnShowDivFormSolicitudActividad) {
		this.blnShowDivFormSolicitudActividad = blnShowDivFormSolicitudActividad;
	}

	public Integer getIntMiOperacion() {
		return intMiOperacion;
	}

	public void setIntMiOperacion(Integer intMiOperacion) {
		this.intMiOperacion = intMiOperacion;
	}

	public String getStrSolicitudActividad() {
		return strSolicitudActividad;
	}

	public void setStrSolicitudActividad(String strSolicitudActividad) {
		this.strSolicitudActividad = strSolicitudActividad;
	}

	public Boolean getBlnShowValidarDatos() {
		return blnShowValidarDatos;
	}

	public void setBlnShowValidarDatos(Boolean blnShowValidarDatos) {
		this.blnShowValidarDatos = blnShowValidarDatos;
	}

	public Integer getIntTipoRelacion() {
		return intTipoRelacion;
	}

	public void setIntTipoRelacion(Integer intTipoRelacion) {
		this.intTipoRelacion = intTipoRelacion;
	}

	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}

	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}

	public Boolean getPgValidDatos() {
		return pgValidDatos;
	}

	public void setPgValidDatos(Boolean pgValidDatos) {
		this.pgValidDatos = pgValidDatos;
	}

	public Boolean getBlnDatosSocio() {
		return blnDatosSocio;
	}

	public void setBlnDatosSocio(Boolean blnDatosSocio) {
		this.blnDatosSocio = blnDatosSocio;
	}

	public Estructura getBeanEstructuraSocioComp() {
		return beanEstructuraSocioComp;
	}

	public void setBeanEstructuraSocioComp(Estructura beanEstructuraSocioComp) {
		this.beanEstructuraSocioComp = beanEstructuraSocioComp;
	}

	public List<Tabla> getListDocumentoBusq() {
		return listDocumentoBusq;
	}

	public void setListDocumentoBusq(List<Tabla> listDocumentoBusq) {
		this.listDocumentoBusq = listDocumentoBusq;
	}

	public List<Estructura> getListEstructura() {
		try {
			if (listEstructura == null) {
				EstructuraFacadeRemote facade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
				this.listEstructura = facade.getListaEstructuraPorNivelYCodigoRel(null, null);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listEstructura;
	}

	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}

	public List<Tabla> getListaDescTipoCreditoEmpresa() {
		return listaDescTipoCreditoEmpresa;
	}

	public void setListaDescTipoCreditoEmpresa(
			List<Tabla> listaDescTipoCreditoEmpresa) {
		this.listaDescTipoCreditoEmpresa = listaDescTipoCreditoEmpresa;
	}

	public List<Sucursal> getListSucursal() {
		return listSucursal;
	}

	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}

	public String getStrMsgErrorPreEvaluacion() {
		return strMsgErrorPreEvaluacion;
	}

	public void setStrMsgErrorPreEvaluacion(String strMsgErrorPreEvaluacion) {
		this.strMsgErrorPreEvaluacion = strMsgErrorPreEvaluacion;
	}

	public boolean isBlnPostEvaluacion() {
		return blnPostEvaluacion;
	}

	public void setBlnPostEvaluacion(boolean blnPostEvaluacion) {
		this.blnPostEvaluacion = blnPostEvaluacion;
	}

	public String getMsgTxtObservacion() {
		return msgTxtObservacion;
	}

	public void setMsgTxtObservacion(String msgTxtObservacion) {
		this.msgTxtObservacion = msgTxtObservacion;
	}

	public String getStrMsgCondicionSocioCapacidadPago() {
		return strMsgCondicionSocioCapacidadPago;
	}

	public void setStrMsgCondicionSocioCapacidadPago(
			String strMsgCondicionSocioCapacidadPago) {
		this.strMsgCondicionSocioCapacidadPago = strMsgCondicionSocioCapacidadPago;
	}

	public List<Tabla> getListaValidarDatosTipoSolicitud() {
		return listaValidarDatosTipoSolicitud;
	}

	public void setListaValidarDatosTipoSolicitud(
			List<Tabla> listaValidarDatosTipoSolicitud) {
		this.listaValidarDatosTipoSolicitud = listaValidarDatosTipoSolicitud;
	}

	public List<Estructura> getListaDependenciasSocio() {
		return listaDependenciasSocio;
	}

	public void setListaDependenciasSocio(List<Estructura> listaDependenciasSocio) {
		this.listaDependenciasSocio = listaDependenciasSocio;
	}

	public String getStrMsgCondicionCapacidadPagoSocio() {
		return strMsgCondicionCapacidadPagoSocio;
	}

	public void setStrMsgCondicionCapacidadPagoSocio(
			String strMsgCondicionCapacidadPagoSocio) {
		this.strMsgCondicionCapacidadPagoSocio = strMsgCondicionCapacidadPagoSocio;
	}

	public List<Tabla> getListaTablaDescripcionAdjuntos() {
		return listaTablaDescripcionAdjuntos;
	}

	public void setListaTablaDescripcionAdjuntos(
			List<Tabla> listaTablaDescripcionAdjuntos) {
		this.listaTablaDescripcionAdjuntos = listaTablaDescripcionAdjuntos;
	}

	public List<RequisitoCreditoComp> getListaRequisitoCreditoComp() {
		return listaRequisitoCreditoComp;
	}

	public void setListaRequisitoCreditoComp(
			List<RequisitoCreditoComp> listaRequisitoCreditoComp) {
		this.listaRequisitoCreditoComp = listaRequisitoCreditoComp;
	}

	public Integer getIntNroCuotas() {
		return intNroCuotas;
	}

	public void setIntNroCuotas(Integer intNroCuotas) {
		this.intNroCuotas = intNroCuotas;
	}

	public Integer getIntTipoActividad() {
		return intTipoActividad;
	}

	public void setIntTipoActividad(Integer intTipoActividad) {
		this.intTipoActividad = intTipoActividad;
	}

	public List<Tabla> getListaTiposActividad() {
		return listaTiposActividad;
	}

	public void setListaTiposActividad(List<Tabla> listaTiposActividad) {
		this.listaTiposActividad = listaTiposActividad;
	}

	public List<Credito> getListaTiposSubActividad() {
		return listaTiposSubActividad;
	}

	public void setListaTiposSubActividad(List<Credito> listaTiposSubActividad) {
		this.listaTiposSubActividad = listaTiposSubActividad;
	}

	public Calendar getFecHoy() {
		return fecHoy;
	}

	public void setFecHoy(Calendar fecHoy) {
		this.fecHoy = fecHoy;
	}

	public String getStrMontoACancelar() {
		return strMontoACancelar;
	}

	public void setStrMontoACancelar(String strMontoACancelar) {
		this.strMontoACancelar = strMontoACancelar;
	}

	public Integer getIntTipoSubActividad() {
		return intTipoSubActividad;
	}

	public void setIntTipoSubActividad(Integer intTipoSubActividad) {
		this.intTipoSubActividad = intTipoSubActividad;
	}

	public Credito getBeanCredito() {
		return beanCredito;
	}

	public void setBeanCredito(Credito beanCredito) {
		this.beanCredito = beanCredito;
	}

	public List<Tabla> getListaCondicionSocio() {
		return listaCondicionSocio;
	}

	public void setListaCondicionSocio(List<Tabla> listaCondicionSocio) {
		this.listaCondicionSocio = listaCondicionSocio;
	}

	public List<Tabla> getListaTipoCondicionSocio() {
		return listaTipoCondicionSocio;
	}

	public void setListaTipoCondicionSocio(List<Tabla> listaTipoCondicionSocio) {
		this.listaTipoCondicionSocio = listaTipoCondicionSocio;
	}

	public String getStrCondicionSocio() {
		return strCondicionSocio;
	}

	public void setStrCondicionSocio(String strCondicionSocio) {
		this.strCondicionSocio = strCondicionSocio;
	}

	public boolean isBlnHayError() {
		return blnHayError;
	}

	public void setBlnHayError(boolean blnHayError) {
		this.blnHayError = blnHayError;
	}

	public List<CronogramaCreditoComp> getListaCronogramaCreditoComp() {
		return listaCronogramaCreditoComp;
	}

	public void setListaCronogramaCreditoComp(
			List<CronogramaCreditoComp> listaCronogramaCreditoComp) {
		this.listaCronogramaCreditoComp = listaCronogramaCreditoComp;
	}

	public Boolean getBlnCronogramaNormal() {
		return blnCronogramaNormal;
	}

	public void setBlnCronogramaNormal(Boolean blnCronogramaNormal) {
		this.blnCronogramaNormal = blnCronogramaNormal;
	}

	public Integer getIntTipoDeSolicitud() {
		return intTipoDeSolicitud;
	}

	public void setIntTipoDeSolicitud(Integer intTipoDeSolicitud) {
		this.intTipoDeSolicitud = intTipoDeSolicitud;
	}

	public boolean isBlnTipoDeSolicitud() {
		return blnTipoDeSolicitud;
	}

	public void setBlnTipoDeSolicitud(boolean blnTipoDeSolicitud) {
		this.blnTipoDeSolicitud = blnTipoDeSolicitud;
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

	public BigDecimal getBdMontoDeCuotas() {
		return bdMontoDeCuotas;
	}

	public void setBdMontoDeCuotas(BigDecimal bdMontoDeCuotas) {
		this.bdMontoDeCuotas = bdMontoDeCuotas;
	}

	public Integer getIntTipoSolicitudSubActividad() {
		return intTipoSolicitudSubActividad;
	}

	public void setIntTipoSolicitudSubActividad(Integer intTipoSolicitudSubActividad) {
		this.intTipoSolicitudSubActividad = intTipoSolicitudSubActividad;
	}

	public BigDecimal getBdMontoCancelar() {
		return bdMontoCancelar;
	}

	public void setBdMontoCancelar(BigDecimal bdMontoCancelar) {
		this.bdMontoCancelar = bdMontoCancelar;
	}

	public PrestamoFacadeRemote getPrestamoFacade() {
		return prestamoFacade;
	}

	public void setPrestamoFacade(PrestamoFacadeRemote prestamoFacade) {
		this.prestamoFacade = prestamoFacade;
	}

	public Boolean getBlnCronograma() {
		return blnCronograma;
	}

	public void setBlnCronograma(Boolean blnCronograma) {
		this.blnCronograma = blnCronograma;
	}

	public Boolean getBlnMontoCancelar() {
		return blnMontoCancelar;
	}

	public void setBlnMontoCancelar(Boolean blnMontoCancelar) {
		this.blnMontoCancelar = blnMontoCancelar;
	}

	public boolean isBlnMostrarDescripciones() {
		return blnMostrarDescripciones;
	}

	public void setBlnMostrarDescripciones(boolean blnMostrarDescripciones) {
		this.blnMostrarDescripciones = blnMostrarDescripciones;
	}

	public String getStrDescripcionTipoActividad() {
		return strDescripcionTipoActividad;
	}

	public void setStrDescripcionTipoActividad(String strDescripcionTipoActividad) {
		this.strDescripcionTipoActividad = strDescripcionTipoActividad;
	}

	public String getStrDescripcionTipoSubActividad() {
		return strDescripcionTipoSubActividad;
	}

	public void setStrDescripcionTipoSubActividad(
			String strDescripcionTipoSubActividad) {
		this.strDescripcionTipoSubActividad = strDescripcionTipoSubActividad;
	}

	public Integer getIntBusqTipoSolicitud() {
		return intBusqTipoSolicitud;
	}

	public void setIntBusqTipoSolicitud(Integer intBusqTipoSolicitud) {
		this.intBusqTipoSolicitud = intBusqTipoSolicitud;
	}

	public Integer getIntBusqTipoActividad() {
		return intBusqTipoActividad;
	}

	public void setIntBusqTipoActividad(Integer intBusqTipoActividad) {
		this.intBusqTipoActividad = intBusqTipoActividad;
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

	public List<Credito> getListaGeneralSubtipoTipoActividad() {
		return listaGeneralSubtipoTipoActividad;
	}

	public void setListaGeneralSubtipoTipoActividad(
			List<Credito> listaGeneralSubtipoTipoActividad) {
		this.listaGeneralSubtipoTipoActividad = listaGeneralSubtipoTipoActividad;
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

	public String getStrUnidadesEjecutorasConcatenadas() {
		return strUnidadesEjecutorasConcatenadas;
	}

	public void setStrUnidadesEjecutorasConcatenadas(
			String strUnidadesEjecutorasConcatenadas) {
		this.strUnidadesEjecutorasConcatenadas = strUnidadesEjecutorasConcatenadas;
	}

	public Calendar getCalFechaRegistro() {
		return calFechaRegistro;
	}

	public void setCalFechaRegistro(Calendar calFechaRegistro) {
		this.calFechaRegistro = calFechaRegistro;
	}

	public boolean isBlnMostrarEliminar() {
		return blnMostrarEliminar;
	}

	public void setBlnMostrarEliminar(boolean blnMostrarEliminar) {
		this.blnMostrarEliminar = blnMostrarEliminar;
	}

	public boolean isBlnMostrarModificar() {
		return blnMostrarModificar;
	}

	public void setBlnMostrarModificar(boolean blnMostrarModificar) {
		this.blnMostrarModificar = blnMostrarModificar;
	}

	public String getStrMsgErrorPreEvaluacionCondicionLaboral() {
		return strMsgErrorPreEvaluacionCondicionLaboral;
	}

	public void setStrMsgErrorPreEvaluacionCondicionLaboral(
			String strMsgErrorPreEvaluacionCondicionLaboral) {
		this.strMsgErrorPreEvaluacionCondicionLaboral = strMsgErrorPreEvaluacionCondicionLaboral;
	}

	public String getStrMsgErrorPreEvaluacionCondicion() {
		return strMsgErrorPreEvaluacionCondicion;
	}

	public void setStrMsgErrorPreEvaluacionCondicion(
			String strMsgErrorPreEvaluacionCondicion) {
		this.strMsgErrorPreEvaluacionCondicion = strMsgErrorPreEvaluacionCondicion;
	}

	public String getStrMsgErrorPreEvaluacionCondicionHabil() {
		return strMsgErrorPreEvaluacionCondicionHabil;
	}

	public void setStrMsgErrorPreEvaluacionCondicionHabil(
			String strMsgErrorPreEvaluacionCondicionHabil) {
		this.strMsgErrorPreEvaluacionCondicionHabil = strMsgErrorPreEvaluacionCondicionHabil;
	}

	public String getStrMsgErrorPreEvaluacionTipoSocio() {
		return strMsgErrorPreEvaluacionTipoSocio;
	}

	public void setStrMsgErrorPreEvaluacionTipoSocio(
			String strMsgErrorPreEvaluacionTipoSocio) {
		this.strMsgErrorPreEvaluacionTipoSocio = strMsgErrorPreEvaluacionTipoSocio;
	}

	public CreditoInteres getCreditoInteresExpediente() {
		return creditoInteresExpediente;
	}

	public void setCreditoInteresExpediente(CreditoInteres creditoInteresExpediente) {
		this.creditoInteresExpediente = creditoInteresExpediente;
	}

	public List<Tabla> getListaDescripcionModalidad() {
		return listaDescripcionModalidad;
	}

	public void setListaDescripcionModalidad(List<Tabla> listaDescripcionModalidad) {
		this.listaDescripcionModalidad = listaDescripcionModalidad;
	}

	public List<Tabla> getListaDescripcionTipoSocio() {
		return listaDescripcionTipoSocio;
	}

	public void setListaDescripcionTipoSocio(List<Tabla> listaDescripcionTipoSocio) {
		this.listaDescripcionTipoSocio = listaDescripcionTipoSocio;
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

	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}

	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}

	public Integer getIntBusqItemCredito() {
		return intBusqItemCredito;
	}

	public void setIntBusqItemCredito(Integer intBusqItemCredito) {
		this.intBusqItemCredito = intBusqItemCredito;
	}

	public List<Tabla> getListaTablaDescConfCredito() {
		return listaTablaDescConfCredito;
	}

	public void setListaTablaDescConfCredito(List<Tabla> listaTablaDescConfCredito) {
		this.listaTablaDescConfCredito = listaTablaDescConfCredito;
	}
	
	
	
}
