package pe.com.tumi.servicio.refinanciamiento.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
//import pe.com.tumi.cobranza.planilla.domain.EnvioconceptoId;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.MyFile;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoTipoGarantiaComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.facade.CreditoExcepcionFacadeRemote;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.creditos.facade.CreditoGarantiaFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadronId;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.credito.socio.estructura.domain.DescuentoId;
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
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
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
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.report.engine.Reporter;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.AutorizaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CronogramaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;

public class SolicitudRefinanControllerV2 {
	protected static Logger log = Logger.getLogger(SolicitudRefinanController.class);
	
	// sesion
	private Usuario 	usuario;
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	private Integer		SUCURSAL_USUARIO_ID;
	private Integer		SUBSUCURSAL_USUARIO_ID;
	
	
	private Date dtHoy = null;
	private Date dtFechaRegistro;
	private ExpedienteCredito beanExpedienteCredito;
	private ExpedienteCredito beanExpedienteCreditoAnterior;
	
	private ExpedienteCreditoComp beanExpedienteCreditoComp;

	
	
	// carga inicial
	
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
	private CreditoExcepcionFacadeRemote creditoExcepcionFacade;
	private PermisoFacadeRemote permisoFacade = null;
	
	private List<Tabla> listaTipoOperacion;
	private List<Tabla> listaTipoRelacion;
	private Integer intSubTipoOperacion;
	private String strSubTipoOperacion;
	private Persona personaValida;
	private String strMsgErrorValidarDatos;
	private List<Tabla> listaEstadoPrestamo;
	private String strUltimoEstado;
	
	//busqueda
	private List<ExpedienteCreditoComp> listaExpedienteCreditoComp;
	private ExpedienteCreditoComp  registroSeleccionadoBusqueda;
	private List<ExpedienteComp> listaDetalleRefinanciamientoLink;
	
	
	//Manejo de formularios
	private Boolean blnShowDivFormSolicitudRefinan;
	private String strSolicitudRefinan;
	private Boolean blnShowValidarDatos;
	
	
	// Validar datos
	private Integer intTipoRelacion; 
	private SocioComp beanSocioComp;
	private Boolean pgValidDatos;
	private Boolean blnDatosSocio;
	private Estructura beanEstructuraSocioComp;
	private List<Tabla>	listDocumentoBusq;
	
	
	// Dependencias
	private List<CapacidadCreditoComp> listaCapacidadCreditoComp;
	private List<Estructura> listEstructura;
	private List<Tabla> listaDescTipoCreditoEmpresa;
	private List<Sucursal> listSucursal;
	
	// GRILLA CREDITOS
	private List<ExpedienteComp> listExpedienteMovimientoComp;
	ExpedienteComp registroSeleccionado;
	private Credito beanCreditoAnterior;
	private Credito beanCreditoNuevo;
	private List<Tabla> listaMotivoPrestamo ;
	private Integer intMotivoRefinanciamiento;
	private List<GarantiaCreditoComp> listaGarantiaCreditoComp;
	private List<RequisitoCreditoComp> listaRequisitoCreditoComp;
	
	
	
	// eVALUACION
	private String strMsgErrorPreEvaluacion;
	private String strMsgMasDeUnoSeleecionado;
	boolean blnPostEvaluacion;
	private String strMsgErrorConfiguracionRefinan;
	/**
	 * Expediente Moviento seleccionado de la grilla con los Expedientes Movimiento del socio
	 */
	private ExpedienteComp beanExpedienteMovARefinanciar;
	private String strMsgCondicionTiempoCeseTrabajador;
	private String strMsgCondicionPlazoMaximoPrestamo;
	private String strMsgCondicionCancelarPorPlanilla;
	private List<CronogramaCreditoComp> listaCronogramaCreditoComp;
	private String strPorcentajeInteres;
	private BigDecimal bdPorcentajeInteres;
	private String strNroCuotas;
	
	private String strMsgErrorExisteRefinanciamiento;
	private List<Tabla> listaTablaDescripcionAdjuntos;
	
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	private Integer intMiOperacion;
	
	
	//-REEMPLZAR
	private BigDecimal bdMontoTotalSolicitado;
	private BigDecimal bdTotalDstos;
	private BigDecimal bdMontoPrestamo;
	private BigDecimal bdAportes;
	private BigDecimal bdPorcAporteMontoSolic;
	private BigDecimal bdCuotaMensual;
	private BigDecimal bdTotalCuotaMensual;
	private BigDecimal bdCapacidadPago;
	private BigDecimal bdMaxCapacidadPago;
	
	private Integer intNroCuotas;
	private Integer intCuotasMaximas;
	private String strPorcInteres;
	private String strPorcAportes;
	private BigDecimal bdPorcSeguroDesgravamen;
	
	private List<CreditoExcepcion> listaExcepciones;
	
	private String strInteresAtrasadoDelPrestamoRefinanciado;
	private String strMoraAtrasadoDelPrestamoRefinanciado;
	private String strMontoTotalDelPrestamoRefinanciado;
	private String strMontoDelPrestamoRefinanciado;
	
	private Integer intNroCuotasCalculado; 
	private boolean blnMarca;
	private String msgTxtCuotaMensual;
	
	private CreditoInteres creditoInteres;
	
	// garantes
	private SocioComp beanSocioCompGarante;
	
	
	// Popup de Garantías
	private String strNroDocumento;
	private Boolean blnDatosGarante;
	private Boolean blnValidaDatosGarante;
	private Integer intItemCreditoGarantia;
	private Boolean validGaranteSolidario;
	private Integer intNroPersGarantizadas;
	
	// Mensajes de error de Garantes
	private String msgTxtPersonaNoExiste;
	private String msgTxtRolSocio;
	private String msgTxtTipoGarantiaPersonal;
	private String msgTxtCondicionSocio;
	private String msgTxtCondicionSocioNinguno;
	private String msgTxtSubCondicionSocio;
	private String msgTxtSubCondicionSocioNinguno;
	private String msgTxtCondicionLaboralGarante;
	private String msgTxtCondicionLaboralGaranteNinguno;
	private String msgTxtSituacionLaboralGarante;
	private String msgTxtSituacionLaboralGaranteNinguno;
	private String msgTxtMaximaGarantia;
	private String msgTxtSocioEstructuraGarante;
	private String msgTxtSocioEstructuraOrigenGarante;
	private String msgTxtCuentaGarante;
	private String msgTxtMaxNroGarantes;
	private String msgTxtGarante;
	private String msgTxtCondicionHabil;
	
	// Mensajes de error de Estructura
	private String msgTxtEstructuraRepetida;
	private String msgTxtEstrucActivoRepetido;
	private String msgTxtEstrucCesanteRepetido;
	
	
	private String msgTxtObservacion;
	private String msgTxtListaCapacidadPago;
	
	private List<CuentaConcepto> listaCuentaConcepto;
	
	private CuentaConcepto ctaCtoAporte;
	private CuentaConcepto ctaCtoRetiro;
	private CuentaConcepto ctaCtoSepelio;
	
	private Boolean blnBloquearcheck;
	private Boolean blnChckCondicionEdadSocio;
	private CreditoGarantia garantiasPersonales; 
	private String strMensajeGarantesObservacion;
	private String strMensajeEdadSocio;
	private Integer intGarantesCorrectos;
	private String msgTxtYaExisteGaranteCOnfiguracionIgual;
	private String msgTxtDescuentoJudicial;
	private List<AutorizaCreditoComp> listaAutorizaCreditoComp;

	private Boolean blnMonstrarEliminar;
	private Boolean blnMonstrarActualizar;
	
	// parametros de busqueda - cgd 30.09.2013
	private Integer intBusqTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;			  
	private Integer intBusqEstado;		    
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private EmpresaFacadeRemote empresaFacade = null;
	private	List<Sucursal> listaSucursal;
	private List<ExpedienteCreditoComp> listaDetalleRefinanciamiento;
	private Integer intBusqTipoCreditoEmpresa;
	private List<Tabla> listaTablaCreditoEmpresa;
	
	private Boolean blnSolicitud;
	private Boolean blnAutorizacion;

	//
	private ExpedienteCreditoComp  registroSeleccionadoImpresion;
	
	//------------------------------------------------------------------------------------>
	
	/**
	 * Clase Controlador
	 */
	public SolicitudRefinanControllerV2() {
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario != null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	
	
	/**
	 * valida que el usuario logueo acceda a los menues(Transacciones) de acuerdo a configuracion.
	 */
		public void cargarPermisos() {
			PermisoPerfil permiso = null;
			PermisoPerfilId id = null;
			Integer MENU_SOLICITUD_REFINANCAMIENTO 	= 212;
			Integer MENU_AUTORIZACION_REFINANCIAMIENTO = 213		;
			try {
				if (usuario != null) {
					id = new PermisoPerfilId();
					id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
					id.setIntIdTransaccion(MENU_SOLICITUD_REFINANCAMIENTO);
					id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
					PermisoFacadeRemote remotePermiso = (PermisoFacadeRemote) EJBFactory.getRemote(PermisoFacadeRemote.class);
					permiso = remotePermiso.getPermisoPerfilPorPk(id);
					blnSolicitud = (permiso == null) ? true : false;
					id.setIntIdTransaccion(MENU_AUTORIZACION_REFINANCIAMIENTO);
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
	 * Metodo de limpieza asociado a h:outputText de nuevoCreditoBody.jsp
	 * Ayuda a controlar los tabs de acuerdo a perfiles de usuario logueado.
	 * @return
	 */
	public String  getLimpiarRefinanciamiento(){
		cargarUsuario();
		cargarValoresIniciales();
		cargarPermisos();
		limpiarFormSolicitudRefinan();
		limpiarFiltros(null);
		limpiarGrillaBusqueda();
		limpiarMensajes();
		limpiarMensajesIsValido();
		pgValidDatos = false;
		blnDatosSocio = false;
		return "";
	}
	
	/**
	 * Carga los valores iniciales, facades, listas y combos
	 */
	private void cargarValoresIniciales(){
		cargarUsuario();
		cargarPermisos();
		//CARGA DE PARAMETROS DE BUSQUEDA
		limpiarFormSolicitudRefinan();
		strSolicitudRefinan = "";
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
		beanExpedienteCreditoAnterior = new ExpedienteCredito();
		beanExpedienteCreditoAnterior.setId(new ExpedienteCreditoId());
		beanExpedienteCreditoAnterior.setListaEstadoCredito(new ArrayList<EstadoCredito>());
		beanExpedienteCreditoAnterior.setListaCronogramaCredito(new ArrayList<CronogramaCredito>());
		beanExpedienteCreditoAnterior.setListaGarantiaCredito(new ArrayList<GarantiaCredito>());
		beanExpedienteCreditoAnterior.setEstadoCreditoUltimo(new EstadoCredito());
		listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
		listaGarantiaCreditoComp = new ArrayList<GarantiaCreditoComp>();
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		beanCreditoAnterior = new Credito();
		beanCreditoAnterior.setId(new CreditoId());
		beanCreditoNuevo = new Credito();
		beanCreditoNuevo.setId(new CreditoId());
		beanExpedienteMovARefinanciar = new ExpedienteComp();
		beanExpedienteMovARefinanciar.setExpediente(new Expediente());
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		beanSocioCompGarante = new SocioComp();
		beanSocioCompGarante.setPersona(new Persona());
		validGaranteSolidario = true;
		blnValidaDatosGarante = true;	
		blnPostEvaluacion = false;
		strUltimoEstado = "";
		blnMonstrarEliminar= true;
		blnMonstrarActualizar= true;		
		limpiarMensajes();
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
			creditoExcepcionFacade = (CreditoExcepcionFacadeRemote)EJBFactory.getRemote(CreditoExcepcionFacadeRemote.class);
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			
			listaTipoRelacion =tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "E");
			//listaTipoOperacion =tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_SUBOPERACIONPRESTAMO), "B");
			listaTipoOperacion =tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_SUBTIPO_OPERACION_REFINANCIAMIENTO);
			listaDescTipoCreditoEmpresa = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA));
			listaMotivoPrestamo = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_FINALIDAD_CREDITO));
			listaEstadoPrestamo = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO));
			listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			ordenarAlfabeticamenteSuc();
			listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 5);
			
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
	 * Limpia los campos del formulario
	 */
	public void limpiarFormSolicitudRefinan() {

		Calendar fecHoy = Calendar.getInstance();
		dtHoy = fecHoy.getTime();
		blnPostEvaluacion = false;
		strSolicitudRefinan = "";

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
		beanExpedienteCredito.setListaCapacidadCreditoComp(new ArrayList<CapacidadCreditoComp>());
		beanExpedienteCreditoAnterior = new ExpedienteCredito();
		beanExpedienteCreditoAnterior.setId(new ExpedienteCreditoId());
		beanExpedienteCreditoAnterior.setListaEstadoCredito(new ArrayList<EstadoCredito>());
		beanExpedienteCreditoAnterior.setListaCronogramaCredito(new ArrayList<CronogramaCredito>());
		beanExpedienteCreditoAnterior.setListaGarantiaCredito(new ArrayList<GarantiaCredito>());
		beanExpedienteCreditoAnterior.setEstadoCreditoUltimo(new EstadoCredito());
		
		listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
		listaGarantiaCreditoComp = new ArrayList<GarantiaCreditoComp>();
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		
		beanCreditoAnterior = new Credito();
		beanCreditoAnterior.setId(new CreditoId());
		beanCreditoNuevo = new Credito();
		beanCreditoNuevo.setId(new CreditoId());
		
		beanExpedienteMovARefinanciar = new ExpedienteComp();
		beanExpedienteMovARefinanciar.setExpediente(new Expediente());
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		
		listExpedienteMovimientoComp = null; 
		
		bdAportes = new BigDecimal(0);
		intNroCuotasCalculado = new Integer(0);
		blnMarca = true;
		strUltimoEstado = "";
		
		intMiOperacion = 1; // 1: nuevo, 2: modifica.
		limpiarMensajes();
		intGarantesCorrectos = 0;
		blnMonstrarEliminar= true;
		blnMonstrarActualizar= true;

	}
	
	public void limpiarMensajes(){
		strMsgErrorPreEvaluacion = "";
		msgTxtCuotaMensual ="";
		strMsgErrorExisteRefinanciamiento = "";
		strUltimoEstado = "";
		strMensajeGarantesObservacion = "";
		strMensajeEdadSocio = "";
		strMsgErrorConfiguracionRefinan = "";
		msgTxtDescuentoJudicial = "";
	}
	
	/**
	 * Metodo que busca las solicitudes de refinanciamiento
	 */
	public void buscar2(){
		
	}
	
	/**
	 * Metodo que se ejecuta al seleccionar un registro de la grilla de busqueda
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		try{
			
			registroSeleccionadoBusqueda = (ExpedienteCreditoComp)event.getComponent().getAttributes().get("itemExpRef");
			registroSeleccionadoImpresion = (ExpedienteCreditoComp)event.getComponent().getAttributes().get("itemExpRef");
			if(registroSeleccionadoBusqueda != null && registroSeleccionadoBusqueda.getExpedienteCredito() != null){
				validarOperacionActualizar();
				validarOperacionEliminar();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

		/**
		 * Valida si la operacion ELIMINAR se visualiza o no en el popup de acciones.
		 * Solo se podra eliminar si es Requisito.
		 */
		public void validarOperacionActualizar(){			
			try {
				if(registroSeleccionadoBusqueda != null){					
					if(registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
						|| registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
						blnMonstrarActualizar = Boolean.TRUE;
					}else{
						blnMonstrarActualizar = Boolean.FALSE;
					}	
				}
			} catch (Exception e) {
				log.error("Error en validarOperacionEliminar ---> "+e);
			}
		}

		
		/**
		 * 
		 */
		public void validarOperacionEliminar(){			
			try {
				if(registroSeleccionadoBusqueda != null){
					if(registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
						blnMonstrarEliminar = Boolean.TRUE;
					}else{
						blnMonstrarEliminar = Boolean.FALSE;
					}	
				}
			} catch (Exception e) {
				log.error("Error en validarOperacionEliminar ---> "+e);
			}
		}

		
	/**
	 * Valida que no exista mas de un Refinanciamiento para un prestamo.
	 * @return
	 */
	public boolean validarExistencia(){
		Boolean blnTieneOtroRefinanciamiento = false;
		List<ExpedienteCredito> lstExpedientes = null;
		
		try {
			lstExpedientes = solicitudPrestamoFacade.getListaPorExpediente(beanExpedienteCreditoAnterior);
			if(lstExpedientes != null){
				for(int k=0; k<lstExpedientes.size();k++){
					ExpedienteCredito expediente = new ExpedienteCredito();
					expediente = lstExpedientes.get(k);

					if(beanExpedienteCreditoAnterior.getId().getIntItemDetExpediente().compareTo(expediente.getId().getIntItemDetExpediente())<0){
						blnTieneOtroRefinanciamiento = Boolean.TRUE;
						break;	
					}
				}
			}
		} catch (BusinessException e) {
			log.error("Error en validarExistencia ---> "+e);
		}
		
		
		
		return blnTieneOtroRefinanciamiento;
	}
	
	
	
	/**
	 * Metodo que se ejecuta al presionar NUEVO. Limpia todo.
	 * @param event
	 */
	public void nuevaSolicitudRefinan(ActionEvent event) {
		try {
			strSolicitudRefinan = Constante.MANTENIMIENTO_MODIFICAR;
			blnShowValidarDatos = true;
			dtFechaRegistro = Calendar.getInstance().getTime();
			blnShowDivFormSolicitudRefinan = false;
			limpiarFormSolicitudRefinan();
			limpiarMensajes();
			limpiarMensajesIsValido();
			intMiOperacion = 1;
			blnBloquearcheck = Boolean.FALSE;
			intGarantesCorrectos = new Integer(0);

		} catch (Exception e) {
			log.error("Error en nuevaSolicitudRefinan ---> "+e);
		}
	}
	
	/**
	 * Metodo que se ejecuta al presionar CANCELAR. Limpia todo.
	 * @param event
	 */
	public void cancelarGrabarSolicitud(ActionEvent event) {
		limpiarFormSolicitudRefinan();
		limpiarMensajesIsValido();

		blnShowValidarDatos = false;
		dtFechaRegistro = Calendar.getInstance().getTime();		
		blnShowDivFormSolicitudRefinan = false;
		limpiarMensajes();
		blnShowValidarDatos = false;
		strSolicitudRefinan =  "";
}
	
	/**
	 * Metodo que limpia todos campos de mensajes 
	 */
	public void limpiarMensajesIsValido() {
		strMsgCondicionTiempoCeseTrabajador="";
		strMsgCondicionPlazoMaximoPrestamo="";
		strMsgCondicionCancelarPorPlanilla="";
		strMensajeGarantesObservacion = "";
		strMensajeEdadSocio = "";
		strMsgErrorConfiguracionRefinan= "";
		msgTxtDescuentoJudicial= "";
	}
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void cargarTipoOperacion(ActionEvent event) {
		String strIdTipoOperacion = null;
		try {
			strIdTipoOperacion = getRequestParameter("pIntTipoOperacion");
			
			if(strIdTipoOperacion == null || strIdTipoOperacion.equalsIgnoreCase("")){
				Integer intTipoOperacion = beanExpedienteCredito.getIntParaSubTipoOperacionCod();
				
				for(int k=0;k<listaTipoOperacion.size();k++){
					if(intTipoOperacion.compareTo(listaTipoOperacion.get(k).getIntIdDetalle())==0){
						strSubTipoOperacion = listaTipoOperacion.get(k).getStrDescripcion();
						break;
					}
				}
				
			}else{
				Integer intTipoOperacion = new Integer(strIdTipoOperacion);
				
				for(int k=0;k<listaTipoOperacion.size();k++){
					if(intTipoOperacion.compareTo(listaTipoOperacion.get(k).getIntIdDetalle())==0){
						strSubTipoOperacion = listaTipoOperacion.get(k).getStrDescripcion();
						break;
					}
				}
			}


		} catch (Exception e) {
			log.error("Error en cargarTipoOperacion ---> "+e);
		} 
	}
	
	
	public void cargarTipoOperacionModificar(ActionEvent event) {
		try {
				Integer intTipoOperacion = beanExpedienteCredito.getIntParaSubTipoOperacionCod();
				intSubTipoOperacion = beanExpedienteCredito.getIntParaSubTipoOperacionCod();
				for(int k=0;k<listaTipoOperacion.size();k++){
					if(intTipoOperacion.compareTo(listaTipoOperacion.get(k).getIntIdDetalle())==0){
						strSubTipoOperacion = listaTipoOperacion.get(k).getStrDescripcion();
						break;
					}
				}
		} catch (Exception e) {
			log.error("Error en cargarTipoOperacion ---> "+e);
		} 
	}
	
	/**
	 * Carga el combo de Tipo de Documento, dependiendo del Tipo de persona seleccionado.
	 * @param event
	 */
	public void cargarComboDocumento(ActionEvent event) {
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
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("event.getComponent.getId(): "+event.getComponent().getId());
		setListDocumentoBusq(listaDocumento);
	}

	/**
	 * Valida y recupera los datos del socio evaluado.
	 * @param event
	 */
	public void validarDatos2(ActionEvent event) {
		SocioComp socioComp = null;
		CapacidadCreditoComp capacidadCreditoComp = null;
		Integer intTipoDoc = 0;
		String strNumIdentidad = "";
		
		CuentaComp cuentaComp = new CuentaComp();
		bdAportes = BigDecimal.ZERO;
		
		List<CuentaConcepto> lstCtaCto = null;
		try {
			
			limpiarListaCapacidades();
			
			intTipoDoc = personaValida.getDocumento().getIntTipoIdentidadCod();
			strNumIdentidad = personaValida.getDocumento().getStrNumeroIdentidad();
			strNumIdentidad = strNumIdentidad.trim();
			
			if ((intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_SOCIO)
				|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_USUARIO)
				|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_CLIENTE))) {

				socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(intTipoDoc, strNumIdentidad,
																			Constante.PARAM_EMPRESASESION);
				if (socioComp != null) {
					if (socioComp.getCuenta() != null) {
						if(socioComp.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							if (socioComp.getSocio().getListSocioEstructura() != null) {
								for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
									if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
										capacidadCreditoComp = new CapacidadCreditoComp();
										capacidadCreditoComp.setSocioEstructura(socioEstructura);
										listaCapacidadCreditoComp.add(capacidadCreditoComp);
									}
								}
							}
							
							for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
								if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
									if (socioEstructura.getIntTipoEstructura().intValue() == Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.intValue()) {
										socioComp.getSocio().setSocioEstructura(socioEstructura);
									}
								}	
							}
							

							// 31.08.2013 - CGD
							lstCtaCto = recuperarCuentasConceptoSocio(socioComp);
							if(lstCtaCto != null && !lstCtaCto.isEmpty()){
								
								ctaCtoAporte = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_APORTES, lstCtaCto);
								ctaCtoRetiro = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO, lstCtaCto);
								ctaCtoSepelio = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO, lstCtaCto);
								
								if(ctaCtoAporte != null){
									cuentaComp.setBdTotalAporte(ctaCtoAporte.getBdSaldo());
									cuentaComp.setCuentaConceptoAportes(ctaCtoAporte);
								}
								if(ctaCtoRetiro != null){
									cuentaComp.setBdTotalRetiro(ctaCtoRetiro.getBdSaldo());
									cuentaComp.setCuentaConceptoRetiro(ctaCtoRetiro);
								}
								if(ctaCtoSepelio != null){
									cuentaComp.setBdTotalSepelio(ctaCtoSepelio.getBdSaldo());
									cuentaComp.setCuentaConceptoSepelio(ctaCtoSepelio);
								}
							}
							
							
							pgValidDatos = false;
							blnDatosSocio = true;
							socioComp.setCuentaComp(cuentaComp);
							beanSocioComp = socioComp;
							
							blnShowDivFormSolicitudRefinan = true;
							blnShowValidarDatos = false;
							List<ExpedienteComp> lstExpedientesRecuperados = null;
							// recuperamos los creditos desde movimiento
							lstExpedientesRecuperados = recuperarCreditosMovimiento();
							if(lstExpedientesRecuperados != null && !lstExpedientesRecuperados.isEmpty()){
								Integer intTam = lstExpedientesRecuperados.size();
								intTam++;
								//beanSocioComp.getCuentaComp().setIntTamannoListaExp(intTam);
								cuentaComp.setIntTamannoListaExp(intTam); 
								
								strMsgErrorValidarDatos= "";
								blnShowValidarDatos = false;
								beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(lstExpedientesRecuperados);
								strSolicitudRefinan = Constante.MANTENIMIENTO_MODIFICAR;
							}else{
								blnShowValidarDatos = true;
								strMsgErrorValidarDatos ="El Socio no presenta algún Crédito con deuda.";
								blnDatosSocio = false;							
								blnShowDivFormSolicitudRefinan = false;
								strSolicitudRefinan = "";
							}
						}
					}
				}
			} else {
				pgValidDatos = true;
				blnDatosSocio = false;
			}
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
			
		}
	}

	/**
	 * 
	 */
	public void limpiarListaCapacidades(){
		try {
			if(listaCapacidadCreditoComp != null){
				listaCapacidadCreditoComp.clear();
			}else{
				listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
			}
		} catch (Exception e) {
			log.error("Error en limpiarListaCapacidades ---> "+e);
		}
	}
	
	/**
	 * Recupera Cuenta COncepto segun parametro.
	 * @param intTipoCuentaConcepto
	 * @param listaCuentaConcepto
	 * @return
	 * @throws BusinessException
	 */
	public CuentaConcepto recuperarCuentasConceptoXCuentaYTipo(Integer intTipoCuentaConcepto, List<CuentaConcepto> listaCuentaConcepto)throws BusinessException{
		CuentaConcepto cuentaConceptoReturn = null;
		
		try {
			if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){	
					for (CuentaConcepto cuentaConcepto : listaCuentaConcepto) {
						CuentaConceptoDetalle detalle = null;
						
						if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
						&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
							detalle = new CuentaConceptoDetalle();
							detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
			
							if(detalle.getIntParaTipoConceptoCod().compareTo(intTipoCuentaConcepto)==0){
								cuentaConceptoReturn = new CuentaConcepto();
								cuentaConceptoReturn = cuentaConcepto ;
								break;
							}
						}		
					}
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoXCuentaYTipo ---> "+e);
		}
		
		return cuentaConceptoReturn;
	}
	
	
	/**
	 * Valida y recupera los datos del socio evaluado.
	 * @param event
	 */
	public void validarDatos2Modificado(ActionEvent event) {
		SocioComp socioComp = null;
		CapacidadCreditoComp capacidadCreditoComp = null;
		Integer intTipoDoc = personaValida.getDocumento().getIntTipoIdentidadCod();
		String strNumIdentidad = personaValida.getDocumento().getStrNumeroIdentidad();
		CuentaComp cuentaComp = new CuentaComp();
		bdAportes = BigDecimal.ZERO;
		List<BloqueoCuenta> lstBloqueos = null;
		List<Integer> lstCuentasConceptoBloqueada = null;

		try {
			if ((intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_SOCIO)
					|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_USUARIO)
					|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_CLIENTE))) {

				socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
						intTipoDoc, strNumIdentidad,
						Constante.PARAM_EMPRESASESION);

				if (socioComp != null) {
					if (socioComp.getCuenta() != null) {
						if (socioComp.getSocio().getListSocioEstructura() != null) {
							for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
								if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
									capacidadCreditoComp = new CapacidadCreditoComp();
									capacidadCreditoComp.setSocioEstructura(socioEstructura);
									//capacidadCreditoComp.getCapacidadCredito().getId().setIntItemCapacidad(null);
									listaCapacidadCreditoComp.add(capacidadCreditoComp);
								}
							}
						}

						for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
							
							if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
								if (socioEstructura.getIntTipoEstructura().intValue() == Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.intValue()) {
									socioComp.getSocio().setSocioEstructura(socioEstructura);
								}
							}	
						}
						
						// verificar los bloqueos
						//Integer intPersEmpresaPk,Integer intCuentaPk
						lstBloqueos = conceptofacade.getListaBloqueoCuentaPorNroCuenta(socioComp.getCuenta().getId().getIntPersEmpresaPk(), socioComp.getCuenta().getId().getIntCuenta());
						
						if(lstBloqueos != null){
							lstCuentasConceptoBloqueada = new ArrayList<Integer>();
							for (BloqueoCuenta bloqueo : lstBloqueos) {
								Date dtInicio = null; 
								Date dtFin = null; 
								if(bloqueo.getIntItemCuentaConcepto() != null){
									if(bloqueo.getTsFechaInicio() != null){
										dtInicio = new java.sql.Date(bloqueo.getTsFechaInicio().getTime());	
									}
									if(bloqueo.getTsFechaFin()!= null){
										dtFin = new java.sql.Date(bloqueo.getTsFechaFin().getTime());
									}
									// Validamos 
									if (((dtHoy.after(dtInicio)&& (dtFin == null)) || (dtHoy.after(dtInicio)&& (dtHoy.before(dtFin))))
											&& bloqueo.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
											lstCuentasConceptoBloqueada.add(bloqueo.getIntItemCuentaConcepto());
									}		
								} 
							}
							
							if (socioComp.getCuenta().getListaConcepto() != null) {
								List<CuentaConcepto> listaConceptos = new ArrayList<CuentaConcepto>();
								listaConceptos = socioComp.getCuenta().getListaConcepto();
								
								boolean blnSiExiste = true;
								for (int j = 0; j < listaConceptos.size(); j++) {
									for (Integer conceptoBloqueado : lstCuentasConceptoBloqueada) {
										if(listaConceptos.get(j).getId().getIntItemCuentaConcepto().compareTo(conceptoBloqueado)==0){
											blnSiExiste = false;
											break;
										}
									}
									if(blnSiExiste)	bdAportes = bdAportes.add(listaConceptos.get(j).getListaCuentaConceptoDetalle().get(0).getBdMontoConcepto());	
								}
							}
							
						} else{
							
							if (socioComp.getCuenta().getListaConcepto() != null) {
								List<CuentaConcepto> listaConceptos = new ArrayList<CuentaConcepto>();
								listaConceptos = socioComp.getCuenta().getListaConcepto();
								for (int j = 0; j < listaConceptos.size(); j++) {
										bdAportes = bdAportes.add(listaConceptos.get(j).getListaCuentaConceptoDetalle().get(0).getBdMontoConcepto());
								}
							}

						}
						
						if (socioComp.getCuenta().getListaConcepto() != null) {
							List<CuentaConcepto> listaConceptos = new ArrayList<CuentaConcepto>();
							listaConceptos = socioComp.getCuenta().getListaConcepto();
							BigDecimal totalAporte = new BigDecimal(0);
							BigDecimal totalSepelio = new BigDecimal(0);
							BigDecimal totalRetiro = new BigDecimal(0);

							for (int j = 0; j < listaConceptos.size(); j++) {
								// Fondo Aportes
								if (listaConceptos.get(j).getId().getIntItemCuentaConcepto() == 1) {// temporalmente
									totalAporte = totalAporte.add(listaConceptos.get(j).getBdSaldo());
								}
								// Fondo Retiro
								if (listaConceptos.get(j).getId().getIntItemCuentaConcepto() == 2) {// temporalmente
									totalRetiro = totalRetiro.add(listaConceptos.get(j).getBdSaldo());
								}
								// Fondo Sepelio
								if (listaConceptos.get(j).getId().getIntItemCuentaConcepto() == 3) {// temporalmente
									totalSepelio = totalSepelio.add(listaConceptos.get(j).getBdSaldo());
								}

							}
							cuentaComp.setCuenta(socioComp.getCuenta());
							cuentaComp.setBdTotalAporte(totalAporte);
							cuentaComp.setBdTotalRetiro(totalRetiro);
							cuentaComp.setBdTotalSepelio(totalSepelio);

						}
					}

						pgValidDatos = false;
						blnDatosSocio = true;
						socioComp.setCuentaComp(cuentaComp);
						beanSocioComp = socioComp;
						
						blnShowDivFormSolicitudRefinan = true;
						blnShowValidarDatos = false;
						
						// recuperamos los creditos desde movimiento
						beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(recuperarCreditosMovimiento());
						strSolicitudRefinan = Constante.MANTENIMIENTO_MODIFICAR;
					}

			} else {
				pgValidDatos = true;
				blnDatosSocio = false;
			}
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
			
		}
	}
	
	
	
	/**
	 * Carga la variable global: listaCuentaConcepto.
	 * @param socioComp
	 */
	public List<CuentaConcepto> recuperarCuentasConceptoSocio (SocioComp socioComp){
		List<CuentaConcepto> listaCtaCto = null;
		try {
			listaCuentaConcepto = conceptofacade.getListaCuentaConceptoPorPkCuenta(socioComp.getCuenta().getId());
			if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
				listaCtaCto = new ArrayList<CuentaConcepto>();
				listaCtaCto = listaCuentaConcepto ;
			}
		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoSocio ---> "+e);
		}
		return listaCtaCto;
	}
	
	
	
	/**
	 * Recupera bloqueos del socio y calcula los aportes mensuales que debera pagar el socio.
	 * @see Relacionado con la generacion del cronograma.
	 */
	public void  validarBloqueos (){
		bdAportes = BigDecimal.ZERO;
		List<BloqueoCuenta> lstBloqueos = null;
		List<Integer> lstCuentasConceptoBloqueada = new ArrayList<Integer>();;
		List<CuentaConcepto> lstCuentaConceptoTotal = null;
		try {
			
			lstBloqueos = conceptofacade.getListaBloqueoCuentaPorNroCuenta(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(),
																			beanSocioComp.getCuenta().getId().getIntCuenta());

			// recuperamos las cuentas concepto y sus reepsectivos detalles
			lstCuentaConceptoTotal = conceptofacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());
			// Si es una solicitud Nueva...
			if (intMiOperacion.compareTo(1)==0) {
				if (lstBloqueos != null) {
					// recuperamos todos los blokeos existentes ys e validan sobre la fecha de hoy.
					for (BloqueoCuenta bloqueo : lstBloqueos) {
						Date dtInicio = null;
						Date dtFin = null;
						if (bloqueo.getIntItemCuentaConcepto() != null) {
							if (bloqueo.getTsFechaInicio() != null) {
								dtInicio = new java.sql.Date(bloqueo.getTsFechaInicio().getTime());
							}
							if (bloqueo.getTsFechaFin() != null) {
								dtFin = new java.sql.Date(bloqueo.getTsFechaFin().getTime());
							}
							
							// Validamos 

							if (((dtHoy.after(dtInicio) && (dtFin == null)) || (dtHoy.after(dtInicio) && (dtHoy.before(dtFin))))
									&& bloqueo.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0) {
									
								lstCuentasConceptoBloqueada.add(bloqueo.getIntItemCuentaConcepto());
							}
						}
					}

					if (lstCuentaConceptoTotal != null) {
						for (int j = 0; j < lstCuentaConceptoTotal.size(); j++) {
							boolean blnSiExiste = true;
							
							for (Integer conceptoBloqueado : lstCuentasConceptoBloqueada) {
								if (lstCuentaConceptoTotal.get(j).getId().getIntItemCuentaConcepto().compareTo(conceptoBloqueado) == 0) {
									blnSiExiste = false;
									break;
								}
							}
							if (blnSiExiste){
								//Ordenamos los subtipos por int
								Collections.sort(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle(), new Comparator<CuentaConceptoDetalle>(){
									public int compare(CuentaConceptoDetalle uno, CuentaConceptoDetalle otro) {
										return uno.getId().getIntItemCtaCptoDet().compareTo(otro.getId().getIntItemCtaCptoDet());
									}
								});
							
								for(int c=0; c<lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().size();c++){
									System.out.println("ORDER---XXX --->"+j+lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().get(c).getId().getIntItemCtaCptoDet());	
								}
								BigDecimal bdTamanno =  new BigDecimal(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().size());
								bdTamanno = bdTamanno.subtract(BigDecimal.ONE);
								Integer intUltimo = new Integer(""+bdTamanno);
								bdAportes = bdAportes.add(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().get(intUltimo).getBdMontoConcepto());
						
							}
						}
					}

				}else{
					// Si aun no existen bloqueos,  BDAportes se suman todos los montos...
					for (int j = 0; j < lstCuentaConceptoTotal.size(); j++) {
							//Ordenamos los subtipos por int
							Collections.sort(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle(), new Comparator<CuentaConceptoDetalle>(){
								public int compare(CuentaConceptoDetalle uno, CuentaConceptoDetalle otro) {
									return uno.getId().getIntItemCtaCptoDet().compareTo(otro.getId().getIntItemCtaCptoDet());
								}
							});

							BigDecimal bdTamanno =  new BigDecimal(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().size());
							bdTamanno = bdTamanno.subtract(BigDecimal.ONE);
							Integer intUltimo = new Integer(""+bdTamanno);
							bdAportes = bdAportes.add(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().get(intUltimo).getBdMontoConcepto());
					}
				}
				
				
				
			} else if(intMiOperacion.compareTo(2)==0){
				if (lstBloqueos != null) {
					
					// recuperamos todos los blokeos existentes ys e validan sobre la fecha de registro de la solicitud.
					for (BloqueoCuenta bloqueo : lstBloqueos) {
						Date dtInicio = null;
						Date dtFin = null;
						if (bloqueo.getIntItemCuentaConcepto() != null) {
							if (bloqueo.getTsFechaInicio() != null) {
								dtInicio = new java.sql.Date(bloqueo.getTsFechaInicio().getTime());
							}
							if (bloqueo.getTsFechaFin() != null) {
								dtFin = new java.sql.Date(bloqueo.getTsFechaFin().getTime());
							}
							
							// Validamos 
							if (((dtFechaRegistro.after(dtInicio) && (dtFin == null)) || (dtFechaRegistro.after(dtInicio) && (dtFechaRegistro.before(dtFin))))
									&& bloqueo.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0) {	
									lstCuentasConceptoBloqueada.add(bloqueo.getIntItemCuentaConcepto());
							}
							
						}
					}
					
					if (lstCuentaConceptoTotal != null) {
						for (int j = 0; j < lstCuentaConceptoTotal.size(); j++) {
							boolean blnSiExiste = true;
							
							for (Integer conceptoBloqueado : lstCuentasConceptoBloqueada) {
								if (lstCuentaConceptoTotal.get(j).getId().getIntItemCuentaConcepto().compareTo(conceptoBloqueado) == 0) {
									blnSiExiste = false;
									break;
								}
							}
							if (blnSiExiste){
								//Ordenamos los subtipos por int
								Collections.sort(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle(), new Comparator<CuentaConceptoDetalle>(){
									public int compare(CuentaConceptoDetalle uno, CuentaConceptoDetalle otro) {
										return uno.getId().getIntItemCtaCptoDet().compareTo(otro.getId().getIntItemCtaCptoDet());
									}
								});
							
								for(int c=0; c<lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().size();c++){
									System.out.println("ORDER---XXX --->"+j+lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().get(c).getId().getIntItemCtaCptoDet());	
								}
								BigDecimal bdTamanno =  new BigDecimal(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().size());
								bdTamanno = bdTamanno.subtract(BigDecimal.ONE);
								Integer intUltimo = new Integer(""+bdTamanno);
								bdAportes = bdAportes.add(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().get(intUltimo).getBdMontoConcepto());
						
							}
						}
						
					}
				}else{
					// Si aun no existen bloqueos,  BDAportes se suman todos los montos...
					for (int j = 0; j < lstCuentaConceptoTotal.size(); j++) {
							//Ordenamos los subtipos por int
							Collections.sort(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle(), new Comparator<CuentaConceptoDetalle>(){
								public int compare(CuentaConceptoDetalle uno, CuentaConceptoDetalle otro) {
									return uno.getId().getIntItemCtaCptoDet().compareTo(otro.getId().getIntItemCtaCptoDet());
								}
							});

							BigDecimal bdTamanno =  new BigDecimal(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().size());
							bdTamanno = bdTamanno.subtract(BigDecimal.ONE);
							Integer intUltimo = new Integer(""+bdTamanno);
							bdAportes = bdAportes.add(lstCuentaConceptoTotal.get(j).getListaCuentaConceptoDetalle().get(intUltimo).getBdMontoConcepto());
					}
				}
	
				
			} 

		} catch (Exception e) {
			log.error("Error en validarBloqueos --> "+e);
		}
		
	}
	
	
	/**
	 * Metodo que valida si se ejecuta la EVALUACION
	 * @return
	 */
//	private Boolean procedeEvaluacion() {
//		return null;
//	}
	
	/**
	 * Descarga el Archivo adjunto
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

	/**
	 * Muestra los archivos adjuntos configurados
	 * @param event
	 */
	public void mostrarArchivosAdjuntos(ActionEvent event) {
		ConfSolicitudFacadeRemote facade = null;
		TablaFacadeRemote tablaFacade = null;
		EstructuraFacadeRemote estructuraFacade = null;
		ConfServSolicitud confServSolicitud = null;
		List<ConfServSolicitud> listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
		EstructuraDetalle estructuraDet = null;
		List<EstructuraDetalle> listaEstructuraDet = new ArrayList<EstructuraDetalle>();
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		RequisitoCreditoComp requisitoCreditoComp;

		try {
			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);			
			
			confServSolicitud = new ConfServSolicitud();
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);
			confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO);
			confServSolicitud.setIntParaSubtipoOperacionCod(Constante.SUBTIPO_OPERACION_REFINANCIAMIENTO);
			listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, Constante.PARAM_T_TIPOREQAUT_REQUISITO, null);

			if (listaDocAdjuntos != null && !listaDocAdjuntos.isEmpty()) {
				
				forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {
					
					if(solicitud.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						if (solicitud.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_REFINANCIAMIENTO)) {
							// dividir de acuerdo a la sub operacion refinanciamiento / reprogramacion
							// PARAM_T_SUBOPERACIONPRESTAMO_REFINANCIAMIENTO
							// PARAM_T_SUBOPERACIONPRESTAMO_REPROGRAMACION
							if (solicitud.getIntParaSubtipoOperacionCod().equals(Constante.SUBTIPO_OPERACION_REFINANCIAMIENTO)) {
								if (solicitud.getListaEstructuraDetalle() != null && !solicitud.getListaEstructuraDetalle().isEmpty()) {
									
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
												if(estructDetalle.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
													if (estructuraDetalle.getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())
															&& estructuraDetalle.getIntNivelPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())
															&& estructuraDetalle.getIntCaso().equals(estructDetalle.getId().getIntCaso())
															&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())) {
															
														if (solicitud.getListaDetalle() != null && solicitud.getListaDetalle().size() > 0) {
																
																List<RequisitoCreditoComp> listaRequisitoCreditoCompTemp = new ArrayList<RequisitoCreditoComp>();
																for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
																	
																	if(detalle.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																		if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId().getIntPersEmpresaPk())
																				&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalle.getId().getIntItemSolicitud())) {
																				
																				requisitoCreditoComp = new RequisitoCreditoComp();
																				requisitoCreditoComp.setDetalle(detalle);
																				//listaRequisitoCreditoComp.add(requisitoCreditoComp);
																				listaRequisitoCreditoCompTemp.add(requisitoCreditoComp);
																			}
																	}
																	
																}
																													
																List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();
																
																cargarDescripcionAdjuntos();
																
																// validamos que solo se muestre las de agrupamioento A.
																listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO), "A");
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

		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Valida los campos: Tipo de Operacion, Monto Solicitado, Observacion y
	 * Unidades Ejecutoras (se debe registrar al menos uno) del Expediente.
	 * 
	 * @param beanExpedienteCredito
	 * @return
	 */
	private Boolean isValidoExpedienteRefinan(ExpedienteCredito beanExpedienteCredito) {
		Boolean validExpedienteRefinan = true;
		strMensajeGarantesObservacion = "";
		strMensajeEdadSocio = "";
		try {
			if (beanExpedienteCredito.getStrObservacion() == null || beanExpedienteCredito.getStrObservacion().equals("")) {
				setMsgTxtObservacion("* El campo de Observación debe ser ingresado.");
				validExpedienteRefinan = false;
			} else {
				setMsgTxtObservacion("");
			}
			if (listaCapacidadCreditoComp == null) {
				setMsgTxtListaCapacidadPago("* Deben agregarse Unidades Ejecutoras.");
				validExpedienteRefinan = false;
			} else {
				setMsgTxtListaCapacidadPago("");
			}
			
			
			if(garantiasPersonales != null && garantiasPersonales.getIntNroGarantesConfigurados() != 0){
				if (listaGarantiaCreditoComp.isEmpty() || listaGarantiaCreditoComp == null) {
					setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago + "* Deben agregarse Garantes Solidarios. ");
					validExpedienteRefinan = false;
				} else {
					calcularGarantesValidos();
					Boolean blnMarca = Boolean.TRUE;
					
					setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago);
					blnMarca = validarGrabarGarantes();
					if(!blnMarca){
						validExpedienteRefinan = false;
					}
				}
				
			}
			
			if (listaCapacidadCreditoComp == null || listaCapacidadCreditoComp.isEmpty()) {
				setMsgTxtListaCapacidadPago(" * Deben agregarse Unidades Ejecutoras.");
				validExpedienteRefinan = false;
			}
			Boolean blnEdad = Boolean.FALSE;
			blnEdad = isValidCondicionTiempoCeseTrabajador(beanSocioComp);
			if(blnEdad){
				validExpedienteRefinan = false;
				strMensajeEdadSocio= "El Socio Excede la edad configurada para el beneficio. ";
			}
			
		} catch (Exception e) {
			log.error("error en isValidoExpedienteRefinan --> "+e);
		}
		return validExpedienteRefinan;
	}
	
	
	
	
	
	/**
	 * Valida la suma de las cuotas fijas de las capacidadaes sean menor al 90/100
	 * Y la suma de estas se igual a la cuotra mensual calculada.
	 * 
	 * Recalcula los montos de las cuotas fijas.
	 * 
	 * Retorna TRUE cuando pasa y FALSE cuando incumple.
	 * @param listaCapacidadComp
	 * @param bdMontoCuotaFija
	 * @return
	 */
	public Boolean validarMontosCapacidadesCredito(BigDecimal bdCuotaMensualCalc){
		
		Boolean blnContinua = Boolean.TRUE;
		//Boolean blnEsElUltimo = Boolean.FALSE;
		Integer intTamannoLista = 0;
		Integer intContador = 0;
		Integer intRegistrosOk = 0; // nro de capapcidades con cuota fija ingresada
		Integer intValor = 0;
		
		try {
			if( listaCapacidadCreditoComp != null && ! listaCapacidadCreditoComp.isEmpty() && bdCuotaMensualCalc   != null && bdCuotaMensualCalc   != BigDecimal.ZERO ){
				msgTxtEstrucActivoRepetido = "";
				
				for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
					if(capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija() != null  
						&& capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija().compareTo(BigDecimal.ZERO)!=0){
						intRegistrosOk ++;
					}
				}

				intTamannoLista = listaCapacidadCreditoComp.size();
				intValor = intTamannoLista - intRegistrosOk;
	
				// 1. Validamos que no existan nulos ni ceros
				if(intValor.compareTo(new Integer(1))==0){
					recalculoCuotasFijasCapacidades();
				}else{
					// base
					for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
						if(capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija() == null  
							|| capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija().compareTo(BigDecimal.ZERO)==0){
							msgTxtEstrucActivoRepetido = "Registrar valor de Cuota Fija para cada Capacidad de Crédito. ";
								//blnAprueba = Boolean.FALSE;
								blnContinua = Boolean.FALSE;
								intContador++;
								break;	
						}
					}
						
				}
				

				// 2. Validamos que cada cuota fija sea menor al 0.9 de capacidad 
				if(blnContinua){
					for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
						BigDecimal bdCapacidad		 = BigDecimal.ZERO;
						BigDecimal bdCapacidad90Porc = BigDecimal.ZERO;
						BigDecimal bdCapacidadCF = BigDecimal.ZERO;
						
						bdCapacidad = capacidadCreditoComp.getCapacidadCredito().getBdCapacidadPago();
						bdCapacidad90Porc = bdCapacidad.multiply(new BigDecimal(0.9));
						bdCapacidad90Porc = bdCapacidad90Porc.divide(BigDecimal.ONE,2, RoundingMode.HALF_UP);
						bdCapacidadCF = capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija();
						
						if(bdCapacidad90Porc.compareTo(bdCapacidadCF)<0){
							msgTxtEstrucActivoRepetido = "La cuota Fija (S/. "+ bdCapacidadCF+"  ) excede al 90% de la Capacidad (S/. "+ bdCapacidad90Porc +" ). ";
							//blnAprueba = Boolean.FALSE;
							blnContinua = Boolean.FALSE;
							break;
						}

					}
					
					// 3. Validamos la suma de las Cuotas Fijas vs la cuota mensual calculada
					if(blnContinua){
						BigDecimal bdSumaCuotasFijas = BigDecimal.ZERO;
						
						for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
							bdSumaCuotasFijas  = bdSumaCuotasFijas.add(capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija());
							bdSumaCuotasFijas = bdSumaCuotasFijas.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
						}

						//if(bdSumaCuotasFijas.compareTo(bdCuotaMensualCalc )!= 0){
						if(!bdSumaCuotasFijas.equals(bdCuotaMensualCalc)){
							msgTxtEstrucActivoRepetido = "La Sumatoria de las Cuotas Fijas (S/. "+ bdSumaCuotasFijas
							+ " ) debe ser igual al Valor de la Cuota Mensual Calculada (S/. "+bdCuotaMensualCalc+"). ";
							blnContinua = Boolean.FALSE;
						}
						
					}	
				}
			}
			
		} catch (Exception e) {
			log.error("Error envalidarCuotaFijaVSCapacidad --->  "+e);
		}
		return blnContinua;
	}
	
	
	/**
	 * Recalcula el monto de las cuotas fijas de cada capacidad 
	 * En el caso que exista n-1 capacidades con CF ingresada.
	 */
	public void recalculoCuotasFijasCapacidades(){
		BigDecimal bdMontoCuotaFinal = BigDecimal.ZERO;
		BigDecimal bdSumatoriaCuotasFijas = BigDecimal.ZERO;
		List<CapacidadCreditoComp> listaTempCapComp = new ArrayList<CapacidadCreditoComp>();
		List<CapacidadCreditoComp> listaTempCapFinal = new ArrayList<CapacidadCreditoComp>();
		try {
			
			if(listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()){
				for (CapacidadCreditoComp capacidadComp : listaCapacidadCreditoComp) {
					if(capacidadComp.getCapacidadCredito().getBdCuotaFija() != null
						&& capacidadComp.getCapacidadCredito().getBdCuotaFija().compareTo(BigDecimal.ZERO)!=0){
						bdSumatoriaCuotasFijas = bdSumatoriaCuotasFijas.add(capacidadComp.getCapacidadCredito().getBdCuotaFija());
					}	
				}
				
				bdMontoCuotaFinal = bdTotalCuotaMensual.subtract(bdSumatoriaCuotasFijas);
				bdMontoCuotaFinal = bdMontoCuotaFinal.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
				
				listaTempCapComp = listaCapacidadCreditoComp;
				
				for (CapacidadCreditoComp capacidadComp : listaTempCapComp) {
					if(capacidadComp.getCapacidadCredito().getBdCuotaFija() == null
						|| capacidadComp.getCapacidadCredito().getBdCuotaFija().compareTo(BigDecimal.ZERO)==0){
						
						capacidadComp.getCapacidadCredito().setBdCuotaFija(bdMontoCuotaFinal);
						bdSumatoriaCuotasFijas = bdSumatoriaCuotasFijas.add(capacidadComp.getCapacidadCredito().getBdCuotaFija());
						listaTempCapFinal.add(capacidadComp);
						
					}else{	
						BigDecimal bdMontoFormateado = BigDecimal.ZERO;
						bdMontoFormateado = capacidadComp.getCapacidadCredito().getBdCuotaFija();
						bdMontoFormateado = bdMontoFormateado.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
						capacidadComp.getCapacidadCredito().setBdCuotaFija(bdMontoFormateado);
						
						listaTempCapFinal.add(capacidadComp);
					}
				}
				
				listaCapacidadCreditoComp.clear();
				listaCapacidadCreditoComp = listaTempCapFinal;
			}

		} catch (Exception e) {
			log.error("Error en recalculoCuotasFijasCapacidades "+e);
		}
		
		
		
	}
	
	/**
	 * Recorre garantes si alguno muestra error, no graba.
	 * Si devuelve FALSE -  existe error.
	 * @return
	 */
	public Boolean validarGrabarGarantes(){
		Boolean blnPasa = Boolean.TRUE;
		strMensajeGarantesObservacion = "";
		try {
			for (GarantiaCreditoComp garantiaComp : listaGarantiaCreditoComp) {
				if(garantiaComp.getSocioComp().getCreditoTipoGarantiaAsignado() == null){
					strMensajeGarantesObservacion = strMensajeGarantesObservacion + "Existen Garantes observados. ";
					blnPasa = Boolean.FALSE;
					break;
				}
			}
			
			if(garantiasPersonales.getIntNroGarantesConfigurados().compareTo(intGarantesCorrectos)!=0){
				strMensajeGarantesObservacion = strMensajeGarantesObservacion + "Nro. de Garantes insuficientes. Configurados: " +garantiasPersonales.getIntNroGarantesConfigurados()+
				" / Registrados: "+intGarantesCorrectos;
				blnPasa = Boolean.FALSE;
			}
			
			
		} catch (Exception e) {
			log.error("Error en validarGrabargarantes ---* "+e);
			
		}
		return blnPasa;
		
	}

	/**
	 * Valida que exista Capacidad de Credito, Cronograma, Garantias y los
	 * Requisitos del Credito (Adjuntos) de acuerdo a lo configurado.
	 * 
	 * @return
	 */
	private int isValidTodaSolicitud() {
		int cnt = 0;
		
		try {
			if (listaCapacidadCreditoComp != null
					&& listaCapacidadCreditoComp.size() <= 0) {
				cnt++;
			}
			if (listaCronogramaCreditoComp != null
					&& listaCronogramaCreditoComp.size() <= 0) {
				cnt++;
			}
			if (listaGarantiaCreditoComp != null
					&& listaGarantiaCreditoComp.size() <= 0) {
				cnt++;
			}
			if (listaRequisitoCreditoComp != null
					&& listaRequisitoCreditoComp.size() > 0) {
				for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {

					if (requisitoCreditoComp.getArchivoAdjunto() == null
						&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1) {
							cnt++;

					}
				}
			}
		} catch (Exception e) {
			log.error("Error en isValidTodaSolicitud ---> "+e);
		}
		return cnt;
	}

	/**
	 * Valida el Monto ingresado y que se haya registrado Capacidad de Credito
	 * en el Expediente.
	 * 
	 * @param beanExpedienteCredito
	 * @return True(Cumple con validaciones) , False (No cumple con
	 *         validaciones).
	 */
	private Boolean isValidoEvaluacionRefinanciamiento(ExpedienteCredito beanExpedienteCredito) {
		Boolean validEvaluarCredito = true;
		Integer intContador = new Integer(0);
		try {
			strMsgMasDeUnoSeleecionado = "";
			msgTxtListaCapacidadPago = "";
			
			if (listaCapacidadCreditoComp != null) {
				for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
					if (capacidadCreditoComp.getCapacidadCredito() == null) {
						setMsgTxtListaCapacidadPago("* La Capacidad de Pago debe ingresarse en la estructura del Socio.");
						validEvaluarCredito = false;
						break;
					} else {
						setMsgTxtListaCapacidadPago("");
					}
				}
			}
			
			if(beanSocioComp.getCuentaComp().getListExpedienteMovimientoComp() != null 
			&& !beanSocioComp.getCuentaComp().getListExpedienteMovimientoComp().isEmpty()){
				
				for (ExpedienteComp expedientesGrilla : beanSocioComp.getCuentaComp().getListExpedienteMovimientoComp()) {
					if(expedientesGrilla.getChecked().compareTo(true)==0){
						intContador++;
					}
				}
				
				if(intContador.compareTo(new Integer(1))>0){
					strMsgMasDeUnoSeleecionado = "Seleccionar SOLO UN préstamo.";
					validEvaluarCredito = false;
				}
				
			}

		} catch (Exception e) {
			log.error("Error en isValidoEvaluacionRefinanciamiento --> "+e);
		}
		return validEvaluarCredito;
	}

	/**
	 * Metodo que se ejecuta al presionar MODIFICAR. 
	 * Recupera todos los datos del Expediente de Refinanciamiento.
	 * @param event
	 * @throws ParseException 
	 */
	public void irModificarSolicitudRefinan(ActionEvent event) throws ParseException{
			EstadoCredito ultimoEstado = null;
			limpiarFormSolicitudRefinan();
			Credito creditoConf = null;
			CreditoId creditoConfId = null;
			List<CuentaConcepto> lstCtaCto = null;
			
			
			intMiOperacion = 2;
			blnBloquearcheck = Boolean.TRUE;
			intGarantesCorrectos = new Integer(0);
			
			SocioComp socioComp;
			Integer intIdPersona = null;
			Persona persona = null;
			CuentaComp cuentaComp = new CuentaComp();
			//listaCronogramaCreditoComp = new ArrayLisR<CronogramaCreditoComp>();

			ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
			expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
			expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
			expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
			expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());


			try {
				
				beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);
				Calendar calFchaReg = StringToCalendar(registroSeleccionadoBusqueda.getStrFechaRequisito());
				dtFechaRegistro = calFchaReg.getTime();
				
				// recuperamos las configuraciones del credito...
				creditoConfId = new CreditoId();
				creditoConfId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
				creditoConfId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
				creditoConfId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());
				///creditoConf.setId(creditoConfId);

				creditoConf = creditoFacade.getCreditoPorIdCredito(creditoConfId);
				if(creditoConf != null){
					beanCreditoNuevo = creditoConf;
				}else{
					beanCreditoNuevo = new Credito();
				}

				// Validamos que se enciuentre en estado Requisiito. Solo asis e puede editar.
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

						cargarDescripcionAdjuntos();
						
						// devuelve el crongrama son id vacio.
						if (beanExpedienteCredito != null) {	
							cargarTipoOperacionModificar(event);
							intNroCuotas =  beanExpedienteCredito.getIntNumeroCuota();
							strPorcentajeInteres = ""+ beanExpedienteCredito.getBdPorcentajeInteres();
							//strInteresAtrasadoDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoInteresAtrasado().toString();
							if(beanExpedienteCredito.getBdMontoInteresAtrasado() == null 
								|| beanExpedienteCredito.getBdMontoInteresAtrasado().compareTo(BigDecimal.ZERO)==0){
									strInteresAtrasadoDelPrestamoRefinanciado = BigDecimal.ZERO.toString();
							}else{
								strInteresAtrasadoDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoInteresAtrasado().toString();
							}
								
							ExpedienteCreditoId viejoCreditoId 	= new ExpedienteCreditoId();
							
							viejoCreditoId.setIntCuentaPk(beanExpedienteCredito.getIntCuentaRefPk());
							viejoCreditoId.setIntItemDetExpediente(beanExpedienteCredito.getIntItemDetExpedienteRef());
							viejoCreditoId.setIntItemExpediente(beanExpedienteCredito.getIntItemExpedienteRef());
							viejoCreditoId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaRefPk());

							beanExpedienteCreditoAnterior = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(viejoCreditoId);
							bdPorcentajeInteres = beanExpedienteCredito.getBdPorcentajeInteres();
							if (beanExpedienteCredito.getListaCapacidadCreditoComp() != null
								&& beanExpedienteCredito.getListaCapacidadCreditoComp().size() > 0) {
								listaCapacidadCreditoComp = beanExpedienteCredito.getListaCapacidadCreditoComp();
								
								if (listaCapacidadCreditoComp != null
										&& listaCapacidadCreditoComp.size() > 0) {
									for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
										intIdPersona = capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk();
									}
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
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION);

										for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
											if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
												socioComp.getSocio().setSocioEstructura(socioEstructura);
											}
										}
										
										// 31.08.2013 - CGD
										lstCtaCto = recuperarCuentasConceptoSocio(socioComp);
										if(lstCtaCto != null && !lstCtaCto.isEmpty()){
											
											ctaCtoAporte = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_APORTES, lstCtaCto);
											ctaCtoRetiro = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO, lstCtaCto);
											ctaCtoSepelio = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO, lstCtaCto);
											
											if(ctaCtoAporte != null){
												cuentaComp.setBdTotalAporte(ctaCtoAporte.getBdSaldo());
												cuentaComp.setCuentaConceptoAportes(ctaCtoAporte);
											}
											if(ctaCtoRetiro != null){
												cuentaComp.setBdTotalRetiro(ctaCtoRetiro.getBdSaldo());
												cuentaComp.setCuentaConceptoRetiro(ctaCtoRetiro);
											}
											if(ctaCtoSepelio != null){
												cuentaComp.setBdTotalSepelio(ctaCtoSepelio.getBdSaldo());
												cuentaComp.setCuentaConceptoSepelio(ctaCtoSepelio);
											}
										}
										socioComp.setCuentaComp(cuentaComp);
										beanSocioComp = socioComp;
									}
								}
							}
							// Se valida el estado del Expediente para ver si se puede o no
							// Eliminar o Modificar
							if (beanExpedienteCredito.getListaEstadoCredito() != null) {
								//configurarCampos(beanExpedienteCredito);
								
								for (EstadoCredito estadoCredito : beanExpedienteCredito.getListaEstadoCredito()) {
									if (estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)
										|| estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)) {
										//setStrSolicitudPrestamo(Constante.MANTENIMIENTO_ELIMINAR);
										break;
									} else {
										if (estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)) {
											//strFechaRegistro = Constante.sdf.format(estadoCredito.getTsFechaEstado());
										}
										//setStrSolicitudPrestamo(Constante.MANTENIMIENTO_MODIFICAR);
									}
								}
							}
							if (beanExpedienteCredito.getListaEstadoCredito() != null) {
								
								mostrarlistaAutorizacionesPrevias(beanExpedienteCredito.getListaEstadoCredito());
								
//								int maximo = beanExpedienteCredito.getListaEstadoCredito().size();
//								EstadoCredito estadoCreditoSolicitud = beanExpedienteCredito.getListaEstadoCredito().get(maximo - 1);
							}
							
							if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null) {
								listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
							}

							if (beanExpedienteCredito.getListaGarantiaCredito() != null
								&& beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
								
								for (int k=0; k<beanExpedienteCredito.getListaGarantiaCredito().size(); k++) {
									Estructura estructuraGarante = new Estructura();
									EstructuraId estructuraIdGarante= new EstructuraId();
									// SocioComp socioCompGarante = new SocioComp();
									GarantiaCreditoComp garantiaComps = new GarantiaCreditoComp();
									SocioComp socioCompGaranteEdit = new SocioComp();
									
									Persona personaGarante = new Persona();
									personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
									
									if(personaGarante!=null){
										if(personaGarante.getListaDocumento()!=null && personaGarante.getListaDocumento().size()>0){
											for (Documento documento : personaGarante.getListaDocumento()) {
												if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
													personaGarante.setDocumento(documento);
													break;
												}
											}
										}
										socioCompGaranteEdit = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), personaGarante.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);
										
										
										if(socioCompGaranteEdit!=null){
											if(socioCompGaranteEdit.getSocio().getListSocioEstructura() !=null && socioCompGaranteEdit.getSocio().getListSocioEstructura().size()>0){
												int ultimo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
												estructuraIdGarante.setIntNivel(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntNivel());
												estructuraIdGarante.setIntCodigo(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntCodigo());
												estructuraGarante.setId(estructuraIdGarante);
												
												estructuraGarante = estructuraFacade.getEstructuraPorPk(estructuraIdGarante);
											
												if(estructuraGarante != null){
												 
													 if(estructuraGarante.getListaEstructura() != null){
															int maximo = estructuraGarante.getListaEstructura().size();
															Juridica juridicaGarante = new Juridica();
															juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
															estructuraGarante.setJuridica(juridicaGarante);
														}
												}
											
												if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null){
													int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
													socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
												}
												
												Integer nroMaxAsegurados = new Integer(-1);
												nroMaxAsegurados=solicitudPrestamoFacade.getNumeroCreditosGarantizadosPorPersona(
														socioCompGaranteEdit.getSocio().getId().getIntIdEmpresa(), 
														socioCompGaranteEdit.getPersona().getIntIdPersona());
												
												if (nroMaxAsegurados != null) {
													garantiaComps.setIntNroGarantizados(nroMaxAsegurados);
												}
												// agregamos garantiaCredito, Estructura, nroGarantizados y socioComp
												garantiaComps.setGarantiaCredito(beanExpedienteCredito.getListaGarantiaCredito().get(k));
												garantiaComps.setEstructura(estructuraGarante);
												garantiaComps.setSocioComp(socioCompGaranteEdit);									
												
												listaGarantiaCreditoComp.add(garantiaComps);
				
											}
										}
									}
								}
							}
							
							
							//--------------- VALIDAR GARANTES
							List<GarantiaCreditoComp>listaGarantiaCompValidadas = null;
							if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
								listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
								
								garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
								if(garantiasPersonales != null && garantiasPersonales.getId() != null){
									beanCreditoNuevo.setCreditoGarantiaPersonal(garantiasPersonales);
									listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
									for (GarantiaCreditoComp garantiaComp : listaGarantiaCreditoComp) {
										garantiaComp = validarGaranteParaRefinanciamiento(garantiaComp);
										if(garantiaComp != null){
											listaGarantiaCompValidadas.add(garantiaComp);
										}
									}
									
									if(listaGarantiaCompValidadas != null && !listaGarantiaCompValidadas.isEmpty()){
										
										listaGarantiaCreditoComp.clear();
										listaGarantiaCreditoComp = listaGarantiaCompValidadas;
									}
								}
							}
							
							calcularGarantesValidos();
							setStrSolicitudRefinan(Constante.MANTENIMIENTO_MODIFICAR);
							beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(recuperarCreditosMovimiento());							
							BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
							bdMontoInteresAtrasado = calcularInteresAtrasado(beanExpedienteCreditoAnterior.getId());
							strInteresAtrasadoDelPrestamoRefinanciado = ""+bdMontoInteresAtrasado; 
							recalcularMontos();
							evaluarRefinanciamientoModificar2(event);
						}
				}

			} catch (BusinessException e) {
				log.error("error: " + e);
				e.printStackTrace();
			}finally {		
				pgValidDatos = false;
				blnDatosSocio = true;
			}
		}

	//}
	
	
	
	/**
	 * Recuepera expediente recalculado con interes actualizado
	 */
	public ExpedienteCredito irModificarSolicitudPrestamoRecalculado(ExpedienteCreditoComp expedienteCredComp, ActionEvent event){
		ExpedienteCredito expedienteRecalculado = new ExpedienteCredito();
		registroSeleccionadoBusqueda = new ExpedienteCreditoComp();
		registroSeleccionadoBusqueda = expedienteCredComp;
		
		EstadoCredito ultimoEstado = null;
		limpiarFormSolicitudRefinan();
		Credito creditoConf = null;
		CreditoId creditoConfId = null;
		List<CuentaConcepto> lstCtaCto = null;
	
		intMiOperacion = 2;
		blnBloquearcheck = Boolean.TRUE;
		intGarantesCorrectos = new Integer(0);
		
		SocioComp socioComp;
		Integer intIdPersona = null;
		Persona persona = null;
		CuentaComp cuentaComp = new CuentaComp();

		ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
		expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
		expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
		expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
		expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());

		try {
			
			beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);
			if (registroSeleccionadoBusqueda.getStrFechaRequisito()==null) {
				int dia = new Integer(Constante.sdf.format(new Date().getTime()).substring(0,2));
				log.info("día: "+dia);
				int mes = new Integer(Constante.sdf.format(new Date().getTime()).substring(3,5));
				log.info("mes: "+mes);
				int anno = new Integer(Constante.sdf.format(new Date().getTime()).substring(6,10));
				log.info("año: "+anno);
				registroSeleccionadoBusqueda.setStrFechaRequisito(dia+"/"+mes+"/"+anno);
			}
			Calendar calFchaReg = StringToCalendar(registroSeleccionadoBusqueda.getStrFechaRequisito());
			dtFechaRegistro = calFchaReg.getTime();
			
			// recuperamos las configuraciones del credito...
			creditoConfId = new CreditoId();
			creditoConfId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
			creditoConfId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
			creditoConfId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());
			///creditoConf.setId(creditoConfId);

			creditoConf = creditoFacade.getCreditoPorIdCredito(creditoConfId);
			if(creditoConf != null){
				beanCreditoNuevo = creditoConf;
			}else{
				beanCreditoNuevo = new Credito();
			}
			// Validamos que se enciuentre en estado Requisiito. Solo asis e puede editar.
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
					cargarDescripcionAdjuntos();
					// devuelve el crongrama son id vacio.

					if (beanExpedienteCredito != null) {	
						cargarTipoOperacionModificar(event);
						intNroCuotas =  beanExpedienteCredito.getIntNumeroCuota();
						strPorcentajeInteres = ""+ beanExpedienteCredito.getBdPorcentajeInteres();
						//strInteresAtrasadoDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoInteresAtrasado().toString();
						if(beanExpedienteCredito.getBdMontoInteresAtrasado() == null 
								|| beanExpedienteCredito.getBdMontoInteresAtrasado().compareTo(BigDecimal.ZERO)==0){
								strInteresAtrasadoDelPrestamoRefinanciado = BigDecimal.ZERO.toString();
							}else{
								strInteresAtrasadoDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoInteresAtrasado().toString();
							}
						
						if (beanExpedienteCredito.getListaCapacidadCreditoComp() != null
							&& beanExpedienteCredito.getListaCapacidadCreditoComp().size() > 0) {
							listaCapacidadCreditoComp = beanExpedienteCredito.getListaCapacidadCreditoComp();
							
							if (listaCapacidadCreditoComp != null
									&& listaCapacidadCreditoComp.size() > 0) {
								for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
									intIdPersona = capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk();
								}
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
									socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
												new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
												persona.getDocumento().getStrNumeroIdentidad(),
												Constante.PARAM_EMPRESASESION);

									for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											socioComp.getSocio().setSocioEstructura(socioEstructura);
										}
									}
									
									lstCtaCto = recuperarCuentasConceptoSocio(socioComp);
									if(lstCtaCto != null && !lstCtaCto.isEmpty()){
										
										ctaCtoAporte = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_APORTES, lstCtaCto);
										ctaCtoRetiro = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO, lstCtaCto);
										ctaCtoSepelio = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO, lstCtaCto);
										
										if(ctaCtoAporte != null){
											cuentaComp.setBdTotalAporte(ctaCtoAporte.getBdSaldo());
											cuentaComp.setCuentaConceptoAportes(ctaCtoAporte);
										}
										if(ctaCtoRetiro != null){
											cuentaComp.setBdTotalRetiro(ctaCtoRetiro.getBdSaldo());
											cuentaComp.setCuentaConceptoRetiro(ctaCtoRetiro);
										}
										if(ctaCtoSepelio != null){
											cuentaComp.setBdTotalSepelio(ctaCtoSepelio.getBdSaldo());
											cuentaComp.setCuentaConceptoSepelio(ctaCtoSepelio);
										}	
									}
									
									socioComp.setCuentaComp(cuentaComp);
									
									beanSocioComp = socioComp;

								}
							}
						}
						// Se valida el estado del Expediente para ver si se puede o no
						// Eliminar o Modificar
						if (beanExpedienteCredito.getListaEstadoCredito() != null) {
							//configurarCampos(beanExpedienteCredito);
							
							for (EstadoCredito estadoCredito : beanExpedienteCredito.getListaEstadoCredito()) {
								if (estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)
									|| estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)) {
									
									break;
								} else {
									if (estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)) {
									}
								}
							}
						}
						if (beanExpedienteCredito.getListaEstadoCredito() != null) {
							int maximo = beanExpedienteCredito.getListaEstadoCredito().size();
							EstadoCredito estadoCreditoSolicitud = beanExpedienteCredito.getListaEstadoCredito().get(maximo - 1);
							
						}
							
						if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null) {
							listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
						}

						///if (beanExpedienteCredito.getListaGarantiaCredito() != null
						//		&& beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
						if (beanExpedienteCredito.getListaGarantiaCredito() != null
								&& beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
							
							for (int k=0; k<beanExpedienteCredito.getListaGarantiaCredito().size(); k++) {
								Estructura estructuraGarante = new Estructura();
								EstructuraId estructuraIdGarante= new EstructuraId();
								// SocioComp socioCompGarante = new SocioComp();
								GarantiaCreditoComp garantiaComps = new GarantiaCreditoComp();
								SocioComp socioCompGaranteEdit = new SocioComp();
								
								Persona personaGarante = new Persona();
								personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
								
								if(personaGarante!=null){
									if(personaGarante.getListaDocumento()!=null && personaGarante.getListaDocumento().size()>0){
										for (Documento documento : personaGarante.getListaDocumento()) {
											if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
												personaGarante.setDocumento(documento);
												break;
											}
										}
									}
									socioCompGaranteEdit = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), personaGarante.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);
									
									
									if(socioCompGaranteEdit!=null){
										if(socioCompGaranteEdit.getSocio().getListSocioEstructura() !=null && socioCompGaranteEdit.getSocio().getListSocioEstructura().size()>0){
											int ultimo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
											estructuraIdGarante.setIntNivel(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntNivel());
											estructuraIdGarante.setIntCodigo(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntCodigo());
											estructuraGarante.setId(estructuraIdGarante);
											
											estructuraGarante = estructuraFacade.getEstructuraPorPk(estructuraIdGarante);
										
											if(estructuraGarante != null){
											 
												 if(estructuraGarante.getListaEstructura() != null){
														int maximo = estructuraGarante.getListaEstructura().size();
														Juridica juridicaGarante = new Juridica();
														juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
														estructuraGarante.setJuridica(juridicaGarante);
													}
											}
										
											// VERIFICAR CAIDA
											if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null){
												int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
												//int sucursal = socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1).getIntIdSubsucurAdministra();
												socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
											}
											
											Integer nroMaxAsegurados = new Integer(-1);
											nroMaxAsegurados=solicitudPrestamoFacade.getNumeroCreditosGarantizadosPorPersona(
													socioCompGaranteEdit.getSocio().getId().getIntIdEmpresa(), 
													socioCompGaranteEdit.getPersona().getIntIdPersona());
											// Antiguo metodo deprecado
											//nroMaxAsegurados=solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGaranteEdit.getPersona().getIntIdPersona());
											
											if (nroMaxAsegurados != null) {
												garantiaComps.setIntNroGarantizados(nroMaxAsegurados);
											}
											// agregamos garantiaCredito, Estructura, nroGarantizados y socioComp
											garantiaComps.setGarantiaCredito(beanExpedienteCredito.getListaGarantiaCredito().get(k));
											garantiaComps.setEstructura(estructuraGarante);
											garantiaComps.setSocioComp(socioCompGaranteEdit);									
											
											listaGarantiaCreditoComp.add(garantiaComps);
			
										}
									}
			
								}
							}
						}
						
						//--------------- VALIDAR GARANTES
						List<GarantiaCreditoComp>listaGarantiaCompValidadas = null;
						if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
							listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
							
							garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
							if(garantiasPersonales != null && garantiasPersonales.getId() != null){
								beanCreditoNuevo.setCreditoGarantiaPersonal(garantiasPersonales);
								listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
								for (GarantiaCreditoComp garantiaComp : listaGarantiaCreditoComp) {
									garantiaComp = validarGaranteParaRefinanciamiento(garantiaComp);
									if(garantiaComp != null){
										listaGarantiaCompValidadas.add(garantiaComp);
									}
								}
								
								if(listaGarantiaCompValidadas != null && !listaGarantiaCompValidadas.isEmpty()){
									
									listaGarantiaCreditoComp.clear();
									listaGarantiaCreditoComp = listaGarantiaCompValidadas;
								}
							}
						}
						
						calcularGarantesValidos();
						setStrSolicitudRefinan(Constante.MANTENIMIENTO_MODIFICAR);						
						BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
						ExpedienteCreditoId viejoCreditoId 	= new ExpedienteCreditoId();
						
						ExpedienteCredito viejoCredito 	= new ExpedienteCredito();
						viejoCreditoId.setIntCuentaPk(expedienteCredComp.getExpedienteCredito().getIntCuentaRefPk());
						viejoCreditoId.setIntItemDetExpediente(expedienteCredComp.getExpedienteCredito().getIntItemDetExpedienteRef());
						viejoCreditoId.setIntItemExpediente(expedienteCredComp.getExpedienteCredito().getIntItemExpedienteRef());
						viejoCreditoId.setIntPersEmpresaPk(expedienteCredComp.getExpedienteCredito().getIntPersEmpresaRefPk());
						viejoCredito.setId(viejoCreditoId);

						beanExpedienteCreditoAnterior = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(viejoCreditoId);
						bdPorcentajeInteres = beanExpedienteCredito.getBdPorcentajeInteres();
						
						bdMontoInteresAtrasado = calcularInteresAtrasado(beanExpedienteCreditoAnterior.getId());
						strInteresAtrasadoDelPrestamoRefinanciado = ""+bdMontoInteresAtrasado;
						beanExpedienteCredito.setBdMontoInteresAtrasado(bdMontoInteresAtrasado);
						recalcularMontos();
						beanExpedienteCredito.setBdMontoInteresAtrasado(bdMontoInteresAtrasado);
						beanExpedienteCredito.setBdMontoTotal(new BigDecimal(strMontoTotalDelPrestamoRefinanciado));							
						//strMontoTotalDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoTotal().toString();
						evaluarRefinanciamientoModificar2(event);
					}
			}
			
			expedienteRecalculado = beanExpedienteCredito;
		} catch (BusinessException e) {
			log.error("error BusinessException: " + e);
			e.printStackTrace();
		} catch (ParseException e) {
			log.error("error ParseException: " + e);
			e.printStackTrace();
		}
		return expedienteRecalculado;
	}
	
	/**
	 * Recupera lista de garantes y los valida a fin de visualizar cuales ya no son aptos como garantes
	 * para e refinanciamiento.
	 *
	 */
	public List<GarantiaCreditoComp> validarListaGarantes(List<GarantiaCredito> lstGarantiasOriginales){
			
		List<GarantiaCreditoComp> listaGarantiaCreditoComps= null;
		
		try {
			for (GarantiaCredito garantiaCreditoOriginal : lstGarantiasOriginales) {
				Estructura estructuraGarante = new Estructura();
				EstructuraId estructuraIdGarante= new EstructuraId();
				GarantiaCreditoComp garantiaComps = new GarantiaCreditoComp();
				SocioComp socioCompGaranteEdit = new SocioComp();
				
				Persona personaGarante = new Persona();
				personaGarante = personaFacade.getPersonaNaturalPorIdPersona(garantiaCreditoOriginal.getIntPersPersonaGarantePk());
				
				if(personaGarante!=null){
					if(personaGarante.getListaDocumento()!=null && personaGarante.getListaDocumento().size()>0){
						for (Documento documento : personaGarante.getListaDocumento()) {
							if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
								personaGarante.setDocumento(documento);
								break;
							}
						}
					}
					socioCompGaranteEdit = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), personaGarante.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);
					
					
					if(socioCompGaranteEdit!=null){
						if(socioCompGaranteEdit.getSocio().getListSocioEstructura() !=null && socioCompGaranteEdit.getSocio().getListSocioEstructura().size()>0){
							int ultimo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
							estructuraIdGarante.setIntNivel(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntNivel());
							estructuraIdGarante.setIntCodigo(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntCodigo());
							estructuraGarante.setId(estructuraIdGarante);
							
							estructuraGarante = estructuraFacade.getEstructuraPorPk(estructuraIdGarante);
						
							if(estructuraGarante != null){
							 
								 if(estructuraGarante.getListaEstructura() != null){
										int maximo = estructuraGarante.getListaEstructura().size();
										Juridica juridicaGarante = new Juridica();
										juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
										estructuraGarante.setJuridica(juridicaGarante);
									}
							}
						
							if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null){
								int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
								socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
							}
							
							Integer nroMaxAsegurados = new Integer(-1);
							nroMaxAsegurados=solicitudPrestamoFacade.getNumeroCreditosGarantizadosPorPersona(
									socioCompGaranteEdit.getSocio().getId().getIntIdEmpresa(), 
									socioCompGaranteEdit.getPersona().getIntIdPersona());
							
							if (nroMaxAsegurados != null) {
								garantiaComps.setIntNroGarantizados(nroMaxAsegurados);
							}
							// agregamos garantiaCredito, Estructura, nroGarantizados y socioComp
							garantiaComps.setGarantiaCredito(garantiaCreditoOriginal);
							garantiaComps.setEstructura(estructuraGarante);
							garantiaComps.setSocioComp(socioCompGaranteEdit);									
							
							listaGarantiaCreditoComp.add(garantiaComps);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en validarListaGarantes ---> "+e);
		}
		return listaGarantiaCreditoComps;
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void irModificarSolicitudRefinanAutoriza(ActionEvent event){	
		AutorizacionRefinanController autorizacionPrevision = (AutorizacionRefinanController) getSessionBean("autorizacionRefinanController");
		autorizacionPrevision.irModificarSolicitudRefinanAutoriza(event);
	}
	
	/**
	 * 
	 * @param event
	 * @param expedienteCreditoCompSelected
	 * @throws ParseException
	 */
	public void irModificarSolicitudRefinanAutoriza2(ActionEvent event, ExpedienteCreditoComp expedienteCreditoCompSelected) throws ParseException{
		EstadoCredito ultimoEstado = null;
		limpiarFormSolicitudRefinan();
		Credito creditoConf = null;
		CreditoId creditoConfId = null;
		List<CuentaConcepto> lstCtaCto = null;
		intMiOperacion = 2;
		strSolicitudRefinan = Constante.MANTENIMIENTO_NINGUNO;
		blnBloquearcheck = Boolean.TRUE;
		intGarantesCorrectos = new Integer(0);
		
		SocioComp socioComp;
		Integer intIdPersona = null;
		Persona persona = null;
		CuentaComp cuentaComp = new CuentaComp();
		
		registroSeleccionadoBusqueda = expedienteCreditoCompSelected;
		Calendar calFchaReg = StringToCalendar(registroSeleccionadoBusqueda.getStrFechaRequisito());
		dtFechaRegistro = calFchaReg.getTime();
		
		ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
		expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
		expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
		expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
		expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());

		try {
			beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);

			// recuperamos las configuraciones del credito...
			creditoConfId = new CreditoId();
			creditoConfId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
			creditoConfId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
			creditoConfId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());

			creditoConf = creditoFacade.getCreditoPorIdCredito(creditoConfId);
			if(creditoConf != null){
				beanCreditoNuevo = creditoConf;
			}else{
				beanCreditoNuevo = new Credito();
			}
			// Validamos que se enciuentre en estado Requisiito. Solo asis e puede editar.
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

					cargarDescripcionAdjuntos();

					if (beanExpedienteCredito != null) {	
						cargarTipoOperacionModificar(event);
						intNroCuotas =  beanExpedienteCredito.getIntNumeroCuota();
						strPorcentajeInteres = ""+ beanExpedienteCredito.getBdPorcentajeInteres();
						if(beanExpedienteCredito.getBdMontoInteresAtrasado() == null 
							|| beanExpedienteCredito.getBdMontoInteresAtrasado().compareTo(BigDecimal.ZERO)==0){
							strInteresAtrasadoDelPrestamoRefinanciado = BigDecimal.ZERO.toString();
						}else{
							strInteresAtrasadoDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoInteresAtrasado().toString();
						}
	
						ExpedienteCreditoId viejoCreditoId 	= new ExpedienteCreditoId();
						
						viejoCreditoId.setIntCuentaPk(beanExpedienteCredito.getIntCuentaRefPk());
						viejoCreditoId.setIntItemDetExpediente(beanExpedienteCredito.getIntItemDetExpedienteRef());
						viejoCreditoId.setIntItemExpediente(beanExpedienteCredito.getIntItemExpedienteRef());
						viejoCreditoId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaRefPk());

						beanExpedienteCreditoAnterior = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(viejoCreditoId);
						bdPorcentajeInteres = beanExpedienteCredito.getBdPorcentajeInteres();
						if (beanExpedienteCredito.getListaCapacidadCreditoComp() != null
							&& beanExpedienteCredito.getListaCapacidadCreditoComp().size() > 0) {
							listaCapacidadCreditoComp = beanExpedienteCredito.getListaCapacidadCreditoComp();
							
							if (listaCapacidadCreditoComp != null
									&& listaCapacidadCreditoComp.size() > 0) {
								for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
									intIdPersona = capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk();
								}
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
									socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
												new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
												persona.getDocumento().getStrNumeroIdentidad(),
												Constante.PARAM_EMPRESASESION);

									for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											socioComp.getSocio().setSocioEstructura(socioEstructura);
										}
									}
									
									// 31.08.2013 - CGD
									lstCtaCto = recuperarCuentasConceptoSocio(socioComp);
									if(lstCtaCto != null && !lstCtaCto.isEmpty()){
										
										ctaCtoAporte = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_APORTES, lstCtaCto);
										ctaCtoRetiro = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO, lstCtaCto);
										ctaCtoSepelio = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO, lstCtaCto);
										
										if(ctaCtoAporte != null){
											cuentaComp.setBdTotalAporte(ctaCtoAporte.getBdSaldo());
											cuentaComp.setCuentaConceptoAportes(ctaCtoAporte);
										}
										if(ctaCtoRetiro != null){
											cuentaComp.setBdTotalRetiro(ctaCtoRetiro.getBdSaldo());
											cuentaComp.setCuentaConceptoRetiro(ctaCtoRetiro);
										}
										if(ctaCtoSepelio != null){
											cuentaComp.setBdTotalSepelio(ctaCtoSepelio.getBdSaldo());
											cuentaComp.setCuentaConceptoSepelio(ctaCtoSepelio);
										}
										
								 }
									socioComp.setCuentaComp(cuentaComp);
									beanSocioComp = socioComp;

								}
							}
						}
						
						if (beanExpedienteCredito.getListaEstadoCredito() != null) {
							mostrarlistaAutorizacionesPrevias(beanExpedienteCredito.getListaEstadoCredito());
							int maximo = beanExpedienteCredito.getListaEstadoCredito().size();
							EstadoCredito estadoCreditoSolicitud = beanExpedienteCredito.getListaEstadoCredito().get(maximo - 1);
						}
					
						if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null) {
							listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
						}

						if (beanExpedienteCredito.getListaGarantiaCredito() != null
							&& beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
							

							for (int k=0; k<beanExpedienteCredito.getListaGarantiaCredito().size(); k++) {
								Estructura estructuraGarante = new Estructura();
								EstructuraId estructuraIdGarante= new EstructuraId();
								GarantiaCreditoComp garantiaComps = new GarantiaCreditoComp();
								SocioComp socioCompGaranteEdit = new SocioComp();
								
								Persona personaGarante = new Persona();
								personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
								
								if(personaGarante!=null){
									if(personaGarante.getListaDocumento()!=null && personaGarante.getListaDocumento().size()>0){
										for (Documento documento : personaGarante.getListaDocumento()) {
											if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
												personaGarante.setDocumento(documento);
												break;
											}
										}
									}
									socioCompGaranteEdit = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), personaGarante.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);

									if(socioCompGaranteEdit!=null){
										if(socioCompGaranteEdit.getSocio().getListSocioEstructura() !=null && socioCompGaranteEdit.getSocio().getListSocioEstructura().size()>0){
											int ultimo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
											estructuraIdGarante.setIntNivel(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntNivel());
											estructuraIdGarante.setIntCodigo(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntCodigo());
											estructuraGarante.setId(estructuraIdGarante);
											
											estructuraGarante = estructuraFacade.getEstructuraPorPk(estructuraIdGarante);
										
											if(estructuraGarante != null){
											 
												 if(estructuraGarante.getListaEstructura() != null){
														int maximo = estructuraGarante.getListaEstructura().size();
														Juridica juridicaGarante = new Juridica();
														juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
														estructuraGarante.setJuridica(juridicaGarante);
													}
											}
										
											// VERIFICAR CAIDA
											if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null){
												int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
												//int sucursal = socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1).getIntIdSubsucurAdministra();
												socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
											}
											
											Integer nroMaxAsegurados = new Integer(-1);
											nroMaxAsegurados=solicitudPrestamoFacade.getNumeroCreditosGarantizadosPorPersona(
													socioCompGaranteEdit.getSocio().getId().getIntIdEmpresa(), 
													socioCompGaranteEdit.getPersona().getIntIdPersona());
											// Antiguo metodo deprecado
											//nroMaxAsegurados=solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGaranteEdit.getPersona().getIntIdPersona());
											
											if (nroMaxAsegurados != null) {
												garantiaComps.setIntNroGarantizados(nroMaxAsegurados);
											}
											// agregamos garantiaCredito, Estructura, nroGarantizados y socioComp
											garantiaComps.setGarantiaCredito(beanExpedienteCredito.getListaGarantiaCredito().get(k));
											garantiaComps.setEstructura(estructuraGarante);
											garantiaComps.setSocioComp(socioCompGaranteEdit);									
											
											listaGarantiaCreditoComp.add(garantiaComps);
			
										}
									}
			
								}

							} 
						}
						
						//--------------- VALIDAR GARANTES
						List<GarantiaCreditoComp>listaGarantiaCompValidadas = null;
						if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
							listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
							
							garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
							if(garantiasPersonales != null && garantiasPersonales.getId() != null){
								beanCreditoNuevo.setCreditoGarantiaPersonal(garantiasPersonales);
								listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
								for (GarantiaCreditoComp garantiaComp : listaGarantiaCreditoComp) {
									garantiaComp = validarGaranteParaRefinanciamiento(garantiaComp);
									if(garantiaComp != null){
										listaGarantiaCompValidadas.add(garantiaComp);
									}
								}
								
								if(listaGarantiaCompValidadas != null && !listaGarantiaCompValidadas.isEmpty()){
									listaGarantiaCreditoComp.clear();
									listaGarantiaCreditoComp = listaGarantiaCompValidadas;
								}
							}
						}

						calcularGarantesValidos();
						if(beanSocioComp.getCuentaComp().getListExpedienteMovimientoComp() == null
								|| beanSocioComp.getCuentaComp().getListExpedienteMovimientoComp().isEmpty()){
							beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(new ArrayList<ExpedienteComp>());
							beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(recuperarCreditosMovimiento());
						}
						BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
						bdMontoInteresAtrasado = calcularInteresAtrasado(beanExpedienteCreditoAnterior.getId());
						strInteresAtrasadoDelPrestamoRefinanciado = ""+bdMontoInteresAtrasado; 
						recalcularMontos();
						
						evaluarRefinanciamientoModificar2(event);

					}
			}

		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();

		}finally {		
			pgValidDatos = false;
			blnDatosSocio = true;
		}
	}

	
	/**
	 * Metodo que se ejecuta al presionar MODIFICAR. 
	 * Recupera todos los datos del Expediente de Refinanciamiento.
	 * @param event
	 * @throws ParseException 
	 */
	public void irVerSolicitudRefinan(ActionEvent event) throws ParseException{
		EstadoCredito ultimoEstado = null;
		limpiarFormSolicitudRefinan();
		Credito creditoConf = null;
		CreditoId creditoConfId = null;
		List<CuentaConcepto> lstCtaCto = null;
		intMiOperacion = 2;
		strSolicitudRefinan = Constante.MANTENIMIENTO_NINGUNO;
		blnBloquearcheck = Boolean.TRUE;
		intGarantesCorrectos = new Integer(0);
		
		SocioComp socioComp;
		Integer intIdPersona = null;
		Persona persona = null;
		CuentaComp cuentaComp = new CuentaComp();

		ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
		expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
		expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
		expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
		expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());

		try {
			beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);

			// recuperamos las configuraciones del credito...
			creditoConfId = new CreditoId();
			creditoConfId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
			creditoConfId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
			creditoConfId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());

			creditoConf = creditoFacade.getCreditoPorIdCredito(creditoConfId);
			if(creditoConf != null){
				beanCreditoNuevo = creditoConf;
			}else{
				beanCreditoNuevo = new Credito();
			}
			// Validamos que se enciuentre en estado Requisiito. Solo asis e puede editar.
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

					cargarDescripcionAdjuntos();

					if (beanExpedienteCredito != null) {	
						cargarTipoOperacionModificar(event);
						intNroCuotas =  beanExpedienteCredito.getIntNumeroCuota();
						strPorcentajeInteres = ""+ beanExpedienteCredito.getBdPorcentajeInteres();
						if(beanExpedienteCredito.getBdMontoInteresAtrasado() == null 
							|| beanExpedienteCredito.getBdMontoInteresAtrasado().compareTo(BigDecimal.ZERO)==0){
							strInteresAtrasadoDelPrestamoRefinanciado = BigDecimal.ZERO.toString();
						}else{
							strInteresAtrasadoDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoInteresAtrasado().toString();
						}
					
						ExpedienteCreditoId viejoCreditoId 	= new ExpedienteCreditoId();
						
						viejoCreditoId.setIntCuentaPk(beanExpedienteCredito.getIntCuentaRefPk());
						viejoCreditoId.setIntItemDetExpediente(beanExpedienteCredito.getIntItemDetExpedienteRef());
						viejoCreditoId.setIntItemExpediente(beanExpedienteCredito.getIntItemExpedienteRef());
						viejoCreditoId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaRefPk());

						beanExpedienteCreditoAnterior = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(viejoCreditoId);
						bdPorcentajeInteres = beanExpedienteCredito.getBdPorcentajeInteres();
						if (beanExpedienteCredito.getListaCapacidadCreditoComp() != null
							&& beanExpedienteCredito.getListaCapacidadCreditoComp().size() > 0) {
							listaCapacidadCreditoComp = beanExpedienteCredito.getListaCapacidadCreditoComp();
							
							if (listaCapacidadCreditoComp != null
									&& listaCapacidadCreditoComp.size() > 0) {
								for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
									intIdPersona = capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk();
								}
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
									socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
												new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
												persona.getDocumento().getStrNumeroIdentidad(),
												Constante.PARAM_EMPRESASESION);

									for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											socioComp.getSocio().setSocioEstructura(socioEstructura);
										}
									}
									
									// 31.08.2013 - CGD
									lstCtaCto = recuperarCuentasConceptoSocio(socioComp);
									if(lstCtaCto != null && !lstCtaCto.isEmpty()){
										
										ctaCtoAporte = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_APORTES, lstCtaCto);
										ctaCtoRetiro = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO, lstCtaCto);
										ctaCtoSepelio = recuperarCuentasConceptoXCuentaYTipo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO, lstCtaCto);
										
										if(ctaCtoAporte != null){
											cuentaComp.setBdTotalAporte(ctaCtoAporte.getBdSaldo());
											cuentaComp.setCuentaConceptoAportes(ctaCtoAporte);
										}
										if(ctaCtoRetiro != null){
											cuentaComp.setBdTotalRetiro(ctaCtoRetiro.getBdSaldo());
											cuentaComp.setCuentaConceptoRetiro(ctaCtoRetiro);
										}
										if(ctaCtoSepelio != null){
											cuentaComp.setBdTotalSepelio(ctaCtoSepelio.getBdSaldo());
											cuentaComp.setCuentaConceptoSepelio(ctaCtoSepelio);
										}
										
								 }
									
									socioComp.setCuentaComp(cuentaComp);
									
									beanSocioComp = socioComp;

								}
							}
						}
						
						if (beanExpedienteCredito.getListaEstadoCredito() != null) {
							mostrarlistaAutorizacionesPrevias(beanExpedienteCredito.getListaEstadoCredito());
							int maximo = beanExpedienteCredito.getListaEstadoCredito().size();
							EstadoCredito estadoCreditoSolicitud = beanExpedienteCredito.getListaEstadoCredito().get(maximo - 1);
						}
					
						if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null) {
							listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
						}

						if (beanExpedienteCredito.getListaGarantiaCredito() != null
							&& beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
							

							for (int k=0; k<beanExpedienteCredito.getListaGarantiaCredito().size(); k++) {
								Estructura estructuraGarante = new Estructura();
								EstructuraId estructuraIdGarante= new EstructuraId();
								GarantiaCreditoComp garantiaComps = new GarantiaCreditoComp();
								SocioComp socioCompGaranteEdit = new SocioComp();
								
								Persona personaGarante = new Persona();
								personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
								
								if(personaGarante!=null){
									if(personaGarante.getListaDocumento()!=null && personaGarante.getListaDocumento().size()>0){
										for (Documento documento : personaGarante.getListaDocumento()) {
											if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
												personaGarante.setDocumento(documento);
												break;
											}
										}
									}
									socioCompGaranteEdit = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), personaGarante.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);

									if(socioCompGaranteEdit!=null){
										if(socioCompGaranteEdit.getSocio().getListSocioEstructura() !=null && socioCompGaranteEdit.getSocio().getListSocioEstructura().size()>0){
											int ultimo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
											estructuraIdGarante.setIntNivel(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntNivel());
											estructuraIdGarante.setIntCodigo(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntCodigo());
											estructuraGarante.setId(estructuraIdGarante);
											
											estructuraGarante = estructuraFacade.getEstructuraPorPk(estructuraIdGarante);
										
											if(estructuraGarante != null){
											 
												 if(estructuraGarante.getListaEstructura() != null){
														int maximo = estructuraGarante.getListaEstructura().size();
														Juridica juridicaGarante = new Juridica();
														juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
														estructuraGarante.setJuridica(juridicaGarante);
													}
											}
										
											if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null){
												int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
												socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
											}
											
											Integer nroMaxAsegurados = new Integer(-1);
											nroMaxAsegurados=solicitudPrestamoFacade.getNumeroCreditosGarantizadosPorPersona(
													socioCompGaranteEdit.getSocio().getId().getIntIdEmpresa(), 
													socioCompGaranteEdit.getPersona().getIntIdPersona());
											
											if (nroMaxAsegurados != null) {
												garantiaComps.setIntNroGarantizados(nroMaxAsegurados);
											}
											// agregamos garantiaCredito, Estructura, nroGarantizados y socioComp
											garantiaComps.setGarantiaCredito(beanExpedienteCredito.getListaGarantiaCredito().get(k));
											garantiaComps.setEstructura(estructuraGarante);
											garantiaComps.setSocioComp(socioCompGaranteEdit);									
											
											listaGarantiaCreditoComp.add(garantiaComps);
			
										}
									}
			
								}

							} 
						}
						
						//--------------- VALIDAR GARANTES
						List<GarantiaCreditoComp>listaGarantiaCompValidadas = null;
						if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
							listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
							
							garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
							if(garantiasPersonales != null && garantiasPersonales.getId() != null){
								beanCreditoNuevo.setCreditoGarantiaPersonal(garantiasPersonales);
								listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
								for (GarantiaCreditoComp garantiaComp : listaGarantiaCreditoComp) {
									garantiaComp = validarGaranteParaRefinanciamiento(garantiaComp);
									if(garantiaComp != null){
										listaGarantiaCompValidadas.add(garantiaComp);
									}
								}
								
								if(listaGarantiaCompValidadas != null && !listaGarantiaCompValidadas.isEmpty()){
									
									listaGarantiaCreditoComp.clear();
									listaGarantiaCreditoComp = listaGarantiaCompValidadas;
								}
							}
						}

						calcularGarantesValidos();
						beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(recuperarCreditosMovimiento());
						BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
						bdMontoInteresAtrasado = calcularInteresAtrasado(beanExpedienteCreditoAnterior.getId());
						strInteresAtrasadoDelPrestamoRefinanciado = ""+bdMontoInteresAtrasado; 
						recalcularMontos();
						evaluarRefinanciamientoModificar2(event);

					}
			}

		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();



		}finally {		

			pgValidDatos = false;
			blnDatosSocio = true;
		}
	}


	/**
	 * Permite Eliminar una solicitud de refinanciamiento SOLO en estado Requisito.
	 */
	public void irEliminarSolicitudRefinan(ActionEvent event) {
		limpiarFormSolicitudRefinan();
		//blnModoVisualizacion = true;
		//SocioComp socioComp;
		//Integer intIdPersona = null;
		//Persona persona = null;
		//listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
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
								strMsgErrorExisteRefinanciamiento = "Solo se puede Eliminar una Solicitud en estado REQUISTO. El estado actual es "+strUltimoEstado.toUpperCase();
								return;
							}
					}
					
				}

			}

			cancelarGrabarSolicitud(event);
			limpiarFormSolicitudRefinan();
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
		} 	
	}

	
	/**
	 * Redirecciona el tipo de evaluacion a aplicar.
	 * @param event
	 */
	public void evaluarRefinanciamiento(ActionEvent event){
		
		try {

			if (isValidoEvaluacionRefinanciamiento(beanExpedienteCredito) == false) {
				log.info("Datos para Evaluación no válidos. Se aborta el proceso de evaluación del Crédito.");
				return;
			}
			
			if (intMiOperacion.compareTo(new Integer(1)) == 0) {
				evaluarRefinanciamientoInicial(event);
			} else {
				evaluarRefinanciamientoModificar2(event);
			}

		} catch (Exception e) {
			log.error("Error en evaluarRefinanciamiento --> "+e);
		}
	}

	public void evaluarRefinanciamientoModificar2(ActionEvent event) throws ParseException { 
		
		EstructuraDetalle estructuraDetalle = null;
		ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
		Date today = new Date();
		String strToday = Constante.sdf.format(today);
		Date dtToday = Constante.sdf.parse(strToday);
		Adenda adenda = null;
		Credito credito = null;
		CreditoId creditoId = null;
		Calendar cal = Calendar.getInstance();
		Calendar miCal = Calendar.getInstance();
		CronogramaCredito cronogramaCredito = null;
		List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Envioconcepto envioConcepto = null;
		Captacion captacion = null;
		BigDecimal bdMontoTotalSolicitado = null;
		//--------------->
		bdPorcSeguroDesgravamen = new BigDecimal(0);
		bdTotalDstos = new BigDecimal(0);
		bdMontoPrestamo = new BigDecimal(0);
		String strPeriodoPlla = null;
		bdCuotaMensual = new BigDecimal(0);
		bdTotalCuotaMensual = new BigDecimal(0);
		CronogramaCreditoComp cronogramaCreditoComp = null;
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		List<ExpedienteCreditoComp> listaAutorizacionCreditoComp;
		boolean boAprobValidacion = false;
		boolean existeConfiguracion = false;
		
		ExpedienteCreditoId viejoCreditoId = null;
		ExpedienteCredito viejoCredito = null;
		
		List<Credito> lstConfCreditos = null;
		boolean boFlag = false;
		List<Tabla> lstDetalleCondLab = null;
		Tabla detalleCondLab = null;
		
		try {
			
			if (beanSocioComp.getSocio().getSocioEstructura() != null) {
				estructuraDetalle = new EstructuraDetalle();
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
				
				if(intMiOperacion.compareTo(1)== 0) beanExpedienteMovARefinanciar =  recuperarPrestamoSeleccionado();
				if(intMiOperacion.compareTo(2)== 0) beanExpedienteMovARefinanciar =  recuperarPrestamoSeleccionadoModificar();
				viejoCreditoId 	= new ExpedienteCreditoId();
				viejoCredito 	= new ExpedienteCredito();
				viejoCreditoId.setIntCuentaPk(beanExpedienteMovARefinanciar.getExpediente().getId().getIntCuentaPk());
				viejoCreditoId.setIntItemDetExpediente(beanExpedienteMovARefinanciar.getExpediente().getId().getIntItemExpedienteDetalle());
				viejoCreditoId.setIntItemExpediente(beanExpedienteMovARefinanciar.getExpediente().getId().getIntItemExpediente());
				viejoCreditoId.setIntPersEmpresaPk(beanExpedienteMovARefinanciar.getExpediente().getId().getIntPersEmpresaPk());
				viejoCredito.setId(viejoCreditoId);

				beanExpedienteCreditoAnterior = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(viejoCreditoId);
				beanExpedienteCredito= solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(registroSeleccionadoBusqueda.getExpedienteCredito().getId());
					
				strPorcentajeInteres = ""+beanExpedienteCredito.getBdPorcentajeInteres();
				
				strInteresAtrasadoDelPrestamoRefinanciado =beanExpedienteCredito.getBdMontoInteresAtrasado().toString();
				strMontoDelPrestamoRefinanciado = "";
				strMontoDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoSolicitado().toString();
				strMoraAtrasadoDelPrestamoRefinanciado    = "0.00";
				strMontoTotalDelPrestamoRefinanciado = beanExpedienteCredito.getBdMontoTotal().toString();

				strMsgErrorExisteRefinanciamiento="";
				blnPostEvaluacion = true;

				// condiciones de la solicitud
				isValidCondicionTiempoCeseTrabajador(beanSocioComp);

				// recuperamos las configuraciones de credito del antiguo prestamo...
				lstConfCreditos = recuperarConfCreditoRefinanciado(beanExpedienteCreditoAnterior);


				// Recuperando el INTERES dependiendo el tipo de socio y el monto
				bdMaxCapacidadPago = new BigDecimal(0);
				bdCapacidadPago = new BigDecimal(0);

				if (listaCapacidadCreditoComp != null) {
					for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
						if (capacidadCreditoComp.getCapacidadCredito() != null) {
							bdCapacidadPago = bdCapacidadPago.add(capacidadCreditoComp.getCapacidadCredito().getBdCapacidadPago());
							bdMaxCapacidadPago = bdCapacidadPago.multiply(new BigDecimal(0.9));
							bdMaxCapacidadPago = bdMaxCapacidadPago.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
						}
					}
				}
				validarBloqueos();
				generarCronograma(/*beanCreditoAnterior,*/ estructuraDetalle,strPorcentajeInteres);
				intMiOperacion = 2;
			}	
		} catch (Exception e) {
			log.error("Error en evaluarRefinanciamiento"+e);
		}
		
	}
	
	/**
	 * Recupera las conficugacion de creditos en base al credito anterior. Y ademas 'beanCreditoAnterior'
	 * @param expedienteCredito
	 * @return
	 */
	private List<Credito> recuperarConfCreditoRefinanciado(ExpedienteCredito expedienteCredito){
		
		Credito confCreditoAntiguo = null;
		CreditoId confCreditoAntiguoId = null;
		List<Credito> lstConfCreditosNuevo = null;
		List <Tabla> lstTipoNuevo = null;
		Credito credito = null;
		List<Credito> lstConfCreditosCompleto = null;
		try {
			
			confCreditoAntiguoId = new CreditoId();
			confCreditoAntiguoId.setIntItemCredito(expedienteCredito.getIntItemCredito());
			confCreditoAntiguoId.setIntParaTipoCreditoCod(expedienteCredito.getIntParaTipoCreditoCod());
			confCreditoAntiguoId.setIntPersEmpresaPk(expedienteCredito.getIntPersEmpresaCreditoPk());
			
			confCreditoAntiguo = creditoFacade.getCreditoPorIdCreditoDirecto(confCreditoAntiguoId);
			
			
			if(confCreditoAntiguo != null){
				Integer intTipoCreditoEmpresa = 0;
				
				beanCreditoAnterior = confCreditoAntiguo;
				
				if(confCreditoAntiguo.getIntParaTipoCreditoEmpresa() != null){
					intTipoCreditoEmpresa = confCreditoAntiguo.getIntParaTipoCreditoEmpresa();
					lstTipoNuevo = tablaFacade.getListaTablaPorAgrupamientoB(Constante.PARAM_T_RELACION_REFINANCIAMIENTO, intTipoCreditoEmpresa);
					
					if(lstTipoNuevo != null){
						credito = new Credito();
						credito.setId(new CreditoId());
						credito.setIntParaTipoCreditoEmpresa(lstTipoNuevo.get(0).getIntIdDetalle());
						lstConfCreditosNuevo = creditoFacade.getlistaCreditoPorCredito(credito);
						
						if (lstConfCreditosNuevo != null){
							lstConfCreditosCompleto = new ArrayList<Credito>();
							for (Credito creditoNuevo : lstConfCreditosNuevo) {
								 creditoNuevo = creditoFacade.getCreditoPorIdCredito(creditoNuevo.getId());
								 lstConfCreditosCompleto.add(creditoNuevo);
							}
						}
					}
				}	
			}

		} catch (Exception e) {
			log.error("Error en  recuperarConfCreditoRefinanciado --> "+e);
			
		}
		
		return lstConfCreditosCompleto;
	}

	
	/**
	 * Es la evaluacion que se realiza por 1ra vez a una solicitud nueva.
	 * @param event
	 * @throws ParseException
	 */
	
	
	public void evaluarRefinanciamientoInicial(ActionEvent event) throws ParseException {
		
		EstructuraDetalle estructuraDetalle = null;
		ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
		Date today = new Date();
		String strToday = Constante.sdf.format(today);
		Date dtToday = Constante.sdf.parse(strToday);
		Adenda adenda = null;
		Credito credito = null;
		CreditoId creditoId = null;
		Calendar cal = Calendar.getInstance();
		Calendar miCal = Calendar.getInstance();
		CronogramaCredito cronogramaCredito = null;
		List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Envioconcepto envioConcepto = null;
		Captacion captacion = null;
		BigDecimal bdMontoTotalSolicitado = null;
		bdPorcSeguroDesgravamen = new BigDecimal(0);
		bdTotalDstos = new BigDecimal(0);
		bdMontoPrestamo = new BigDecimal(0);
		String strPeriodoPlla = null;
		bdCuotaMensual = new BigDecimal(0);
		bdTotalCuotaMensual = new BigDecimal(0);
		CronogramaCreditoComp cronogramaCreditoComp = null;
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		List<ExpedienteCreditoComp> listaAutorizacionCreditoComp;
		boolean boAprobValidacion = false;
		boolean existeConfiguracion = false;
		ExpedienteCreditoId viejoCreditoId = null;
		ExpedienteCredito viejoCredito = null;
		List<Credito> lstConfCreditos = null;
		boolean boFlag = false;
		List<Tabla> lstDetalleCondLab = null;
		Tabla detalleCondLab = null;
		Boolean blnSeValidoCofiguracion = Boolean.FALSE;
		
		try {
			
			if (beanSocioComp.getSocio().getSocioEstructura() != null) {
				estructuraDetalle = new EstructuraDetalle();
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
				
				beanExpedienteMovARefinanciar =  recuperarPrestamoSeleccionado();
	
				viejoCreditoId 	= new ExpedienteCreditoId();
				viejoCredito 	= new ExpedienteCredito();
				viejoCreditoId.setIntCuentaPk(beanExpedienteMovARefinanciar.getExpediente().getId().getIntCuentaPk());
				viejoCreditoId.setIntItemDetExpediente(beanExpedienteMovARefinanciar.getExpediente().getId().getIntItemExpedienteDetalle());
				viejoCreditoId.setIntItemExpediente(beanExpedienteMovARefinanciar.getExpediente().getId().getIntItemExpediente());
				viejoCreditoId.setIntPersEmpresaPk(beanExpedienteMovARefinanciar.getExpediente().getId().getIntPersEmpresaPk());
				viejoCredito.setId(viejoCreditoId);

				if(validarExistencia()){
					strMsgErrorExisteRefinanciamiento ="El registro seleccionado ya tiene un Refinanciamiento en proceso.";
					return;
				}else{
						strMsgErrorExisteRefinanciamiento="";
						beanExpedienteCreditoAnterior = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(viejoCreditoId);
						beanExpedienteCredito= convertirExpedienteMovimientoAExpedienteCreditoMasMoraInteresTotal(beanExpedienteMovARefinanciar.getExpediente(),beanExpedienteCreditoAnterior);
						
						
						// recuperamos las configuraciones de credito del antiguo prestamo...
						lstConfCreditos = recuperarConfCreditoRefinanciado(beanExpedienteCreditoAnterior);
						
						if(lstConfCreditos != null && !lstConfCreditos.isEmpty()){
							strMsgErrorConfiguracionRefinan = "";
							Integer intTamanno = lstConfCreditos.size();
						
							if(intTamanno.compareTo(1)==0){								
								beanCreditoNuevo = lstConfCreditos.get(0);
							}else{
								//validar de acuerdoa  montos si trae mas de una configuracion...
								for (Credito credito2 : lstConfCreditos) {
									
									if((beanExpedienteCredito.getBdMontoSolicitado().compareTo(credito2.getBdMontoMinimo())>=0)
										&& (beanExpedienteCredito.getBdMontoSolicitado().compareTo(credito2.getBdMontoMaximo())<=0)){
											credito2 = creditoFacade.getCreditoPorIdCredito(credito2.getId());
											beanCreditoNuevo = credito2;
											break;
									}
								}	
							}	
						}else{
							blnPostEvaluacion = false;
							strMsgErrorConfiguracionRefinan = "No se recuperó Configuración para el Crédito seleccionado.";
							return;
						}

						// Recuperando el INTERES dependiendo el tipo de socio y el monto
						if(beanSocioComp != null){	
							if(beanCreditoNuevo != null){
								blnSeValidoCofiguracion= validarConfiguracionDeRefinanciamiento(beanCreditoNuevo, event);
								if(blnSeValidoCofiguracion){
									if (beanCreditoNuevo.getListaCreditoInteres() != null) {
										
										Integer intTipoSocio = beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio();
										Integer intModalidad = beanSocioComp.getSocio().getSocioEstructura().getIntModalidad();
										Integer intCondicionLaboral = 0;
										List<PerLaboral> lstPerLaboral = null;
										
										lstPerLaboral =  beanSocioComp.getPersona().getNatural().getListaPerLaboral();
										intCondicionLaboral = lstPerLaboral.get(0).getIntCondicionLaboral();
										 
										 if(intCondicionLaboral.compareTo(0) != 0){
											 lstDetalleCondLab = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_DETALLECONDICIONLABORAL));
											 											 
											 if(lstDetalleCondLab != null){
												 for (Tabla tabla : lstDetalleCondLab) {
													 if(tabla.getIntIdDetalle().compareTo(intCondicionLaboral)==0){
														 detalleCondLab = tabla;
														 break; 
													 }
												}

												 if(detalleCondLab.getStrIdAgrupamientoA().equalsIgnoreCase("A")){
														for (CreditoInteres interes : beanCreditoNuevo.getListaCreditoInteres()) {
																if(interes.getIntParaTipoSocio().compareTo(1)==0){
																	creditoInteres = interes;
																	strPorcentajeInteres = ""+creditoInteres.getBdTasaInteres();
																	bdPorcentajeInteres = creditoInteres.getBdTasaInteres();
																	break;
																}
														}

												 }else if (detalleCondLab.getStrIdAgrupamientoA().equalsIgnoreCase("C")){
													 for (CreditoInteres interes : beanCreditoNuevo.getListaCreditoInteres()) {
														 if(interes.getIntParaTipoSocio().compareTo(2)==0){
															 creditoInteres = interes;
															 strPorcentajeInteres = ""+creditoInteres.getBdTasaInteres();
															 bdPorcentajeInteres = creditoInteres.getBdTasaInteres();
															 break;
														 }
													}
													 
												 }
											 } 
										 }
									}
									
									bdMaxCapacidadPago = new BigDecimal(0);
									bdCapacidadPago = new BigDecimal(0);
									// condiciones de la solicitud
									isValidCondicionTiempoCeseTrabajador(beanSocioComp);
									validarBloqueos();
									
									//--------------- VALIDAR GARANTES
									List<GarantiaCreditoComp>listaGarantiaCompValidadas = null;
									if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
										listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
										
										garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
										if(garantiasPersonales != null && garantiasPersonales.getId() != null){
											beanCreditoNuevo.setCreditoGarantiaPersonal(garantiasPersonales);
											listaGarantiaCompValidadas = new ArrayList<GarantiaCreditoComp>();
											for (GarantiaCreditoComp garantiaComp : listaGarantiaCreditoComp) {
												garantiaComp = validarGaranteParaRefinanciamiento(garantiaComp);
												if(garantiaComp != null){
													listaGarantiaCompValidadas.add(garantiaComp);
												}
											}
											
											if(listaGarantiaCompValidadas != null && !listaGarantiaCompValidadas.isEmpty()){
												listaGarantiaCreditoComp.clear();
												listaGarantiaCreditoComp = listaGarantiaCompValidadas;
											}
										}
									}
									
									calcularGarantesValidos();
									
									if (listaCapacidadCreditoComp != null) {
										for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
											if (capacidadCreditoComp.getCapacidadCredito() != null) {
												// bdCuotaFija =
												// bdCuotaFija.add(capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija());
												bdCapacidadPago = bdCapacidadPago.add(capacidadCreditoComp.getCapacidadCredito().getBdCapacidadPago());
												bdMaxCapacidadPago = bdCapacidadPago.multiply(new BigDecimal(0.9));
												bdMaxCapacidadPago = bdMaxCapacidadPago.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
											}
										}
									}
									BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
									bdMontoInteresAtrasado = calcularInteresAtrasado(beanExpedienteCreditoAnterior.getId());
									strInteresAtrasadoDelPrestamoRefinanciado = ""+bdMontoInteresAtrasado; 
									recalcularMontos();
									generarCronograma(estructuraDetalle, strPorcentajeInteres);
									
									mostrarArchivosAdjuntos(event);
									blnPostEvaluacion = true;
									blnBloquearcheck = Boolean.TRUE;
								}else{
									blnPostEvaluacion = false;
									strMsgErrorConfiguracionRefinan = "El Socio no cumple con la Configuración del Refinanciamiento.";
								}	
							}
						}
					}
				}	
		} catch (Exception e) {
			log.error("Error en evaluarRefinanciamiento"+e);
		}
	}
	
	
	
	/**
	 * 
	 * @param confRefinan
	 * @param event
	 */
	public Boolean validarConfiguracionDeRefinanciamiento (Credito confRefinan, ActionEvent event){
		EstructuraDetalle estructuraDetalle = null;
		Date today = new Date();
		String strToday = Constante.sdf.format(today);
		Boolean blnSeAprueba = Boolean.FALSE;
		
		
		try {
			Date dtToday = Constante.sdf.parse(strToday);
			
			if(confRefinan != null){
				
				if (beanSocioComp.getSocio().getSocioEstructura() != null) {
					estructuraDetalle = new EstructuraDetalle();	
					estructuraDetalle.setId(new EstructuraDetalleId());
					estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
					beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
					beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
				}
				
				
				Calendar fechaInicio = null;
				Calendar fechaFin = null;
				Boolean blnFecIni = Boolean.FALSE;
				Boolean blnFecFin = Boolean.FALSE;
				Boolean blnRol = Boolean.FALSE;

				// 1. Validando estado
				if (confRefinan.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
					
					// 2. Validando vigencia. Fecha de inicio y fin
					//if(confRefinan.getStrDtFechaIni() != null){
					if(confRefinan.getStrDtFechaIni() != null && !confRefinan.getStrDtFechaIni().isEmpty()){
						fechaInicio=Calendar.getInstance();
						fechaInicio.clear();
						fechaInicio = StringToCalendar(confRefinan.getStrDtFechaIni());
						if(dtToday.after(fechaInicio.getTime())){
							blnFecIni = Boolean.TRUE;
						}
					}
					if(confRefinan.getStrDtFechaFin() != null && !confRefinan.getStrDtFechaFin().isEmpty()){
						fechaFin = Calendar.getInstance();
						fechaFin.clear();
						fechaFin = StringToCalendar(confRefinan.getStrDtFechaFin());
						if(dtToday.before(fechaFin.getTime())){
							blnFecFin = Boolean.TRUE;
						}
					}else{
						blnFecFin = Boolean.TRUE;
					}

					if(blnFecIni && blnFecFin){
						if (beanSocioComp.getPersona().getPersonaEmpresa().getListaPersonaRol() != null) {
							// 3. Validamos rol
							for (PersonaRol personaRol : beanSocioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()) {
								if(personaRol.getId().getIntParaRolPk().compareTo(confRefinan.getIntParaRolPk())==0){
									blnRol = Boolean.TRUE;
									break;
								}
							}
							if(blnRol){
							
								//4. Validamos la Condicion Laboral
								boolean boFlagCondLab = false;
								for (int k = 0; k < beanSocioComp.getPersona().getNatural().getListaPerLaboral().size(); k++) {
									if (confRefinan.getIntParaCondicionLaboralCod().compareTo(beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(k).getIntCondicionLaboral())==0) {
										boFlagCondLab = true;
										break;
									}
								}
								if((confRefinan.getIntParaCondicionLaboralCod().compareTo(-1)==0)|| (boFlagCondLab)) {
									
									// 5. Validando condicion de cuenta
									if (confRefinan.getListaCondicion() != null) {
										for (CondicionCredito condicionCuenta : confRefinan.getListaCondicion()) {
											if (beanSocioComp.getCuenta().getIntParaCondicionCuentaCod().compareTo(condicionCuenta.getId().getIntParaCondicionSocioCod())==0
												&&condicionCuenta.getIntValor().compareTo(new Integer(1))==0 ){
												
												// 6. Validar condicion habil
												if (confRefinan.getListaCondicionHabil() != null) {
													for (CondicionHabil condicionHabil : confRefinan.getListaCondicionHabil()) {
														// verificando condicion de regular
														if (beanSocioComp.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(condicionHabil.getId().getIntParaTipoHabilCod())==0
															&& condicionHabil.getIntValor() == 1) {
															blnSeAprueba = Boolean.TRUE;
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

		} catch (Exception e) {
			log.error("Error en validarConfiguracionDeRefinanciamiento ---> "+e);
		}
		return blnSeAprueba;	
	}
	
	
	public void evaluarRefinanciamientoModificar(ActionEvent event) throws ParseException {
			
		EstructuraDetalle estructuraDetalle = null;
		ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
		Date today = new Date();
		String strToday = Constante.sdf.format(today);
		Date dtToday = Constante.sdf.parse(strToday);
		Adenda adenda = null;
		Credito credito = null;
		CreditoId creditoId = null;
		Calendar cal = Calendar.getInstance();
		Calendar miCal = Calendar.getInstance();
		CronogramaCredito cronogramaCredito = null;
		List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Envioconcepto envioConcepto = null;
		Captacion captacion = null;
		BigDecimal bdMontoTotalSolicitado = null;
		//--------------->
		bdPorcSeguroDesgravamen = new BigDecimal(0);
		bdTotalDstos = new BigDecimal(0);
		bdMontoPrestamo = new BigDecimal(0);
		String strPeriodoPlla = null;
		bdCuotaMensual = new BigDecimal(0);
		bdTotalCuotaMensual = new BigDecimal(0);
		CronogramaCreditoComp cronogramaCreditoComp = null;
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		List<ExpedienteCreditoComp> listaAutorizacionCreditoComp;
		boolean boAprobValidacion = false;
		boolean existeConfiguracion = false;
		
		try {
			
			if (beanSocioComp.getSocio().getSocioEstructura() != null) {
				estructuraDetalle = new EstructuraDetalle();
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
				
				
						strMsgErrorExisteRefinanciamiento="";
						blnPostEvaluacion = true;
		
						// condiciones de la solicitud
						isValidCondicionTiempoCeseTrabajador(beanSocioComp);
						
						// recuperamos los la configuracion de credito del  antiguo prestamo...
						credito = new Credito();
						creditoId = new CreditoId();
						
						creditoId.setIntPersEmpresaPk(beanExpedienteMovARefinanciar.getExpediente().getIntPersEmpresaCreditoPk());
						creditoId.setIntParaTipoCreditoCod(beanExpedienteMovARefinanciar.getExpediente().getIntParaTipoCreditoCod());
						creditoId.setIntItemCredito(beanExpedienteMovARefinanciar.getExpediente().getIntItemCredito());
						
						credito.setId(creditoId);
						credito = creditoFacade.getCreditoPorIdCredito(creditoId);
						
						beanCreditoAnterior = credito;
						beanExpedienteCreditoAnterior.getBdPorcentajeAporte();
						beanExpedienteCreditoAnterior.getBdPorcentajeInteres();
						beanCreditoAnterior.getListaDescuento();
						beanCreditoAnterior.getBdTasaSeguroDesgravamen();
						beanCreditoAnterior.getIntDescuento();
						beanCreditoAnterior.getListaCreditoInteres();
						beanCreditoAnterior.getListaRangoMontoMax();
						beanCreditoAnterior.getListaRangoMontoMin();
						beanCreditoAnterior.getStrDescripcion();
	
						bdMaxCapacidadPago = new BigDecimal(0);
						bdCapacidadPago = new BigDecimal(0);
	
						if (listaCapacidadCreditoComp != null) {
							for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
								if (capacidadCreditoComp.getCapacidadCredito() != null) {
									// bdCuotaFija =
									// bdCuotaFija.add(capacidadCreditoComp.getCapacidadCredito().getBdCuotaFija());
									bdCapacidadPago = bdCapacidadPago.add(capacidadCreditoComp.getCapacidadCredito().getBdCapacidadPago());
									bdMaxCapacidadPago = bdCapacidadPago.multiply(new BigDecimal(0.9));
									bdMaxCapacidadPago = bdMaxCapacidadPago.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
								}
							}
						}
						generarCronograma(estructuraDetalle,strPorcentajeInteres);
						cargarDescripcionAdjuntos();
			}	
		} catch (Exception e) {
			log.error("Error en evaluarRefinanciamiento"+e);
		}
			
	}


	/**
	 * 
	 */
	public BigDecimal calcularInteresAtrasado(ExpedienteCreditoId expCreditoAntiguoId){
		BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
		Expediente expedienteAntiguo = null;
		InteresCancelado ultimoInteresCancelado = null;
		Date dtHoy = null;
		Calendar fecHoy = Calendar.getInstance();
		Integer intNumeroDias = 0;
		List<EstadoCredito> lstEstados = null;
		BigDecimal bdMontoSaldoCredito = BigDecimal.ZERO;
		BigDecimal bdMult01 = BigDecimal.ONE;
		BigDecimal bdMult02 = BigDecimal.ONE;
		BigDecimal bdMult03 = BigDecimal.ONE;
		//BigDecimal bdMult04 = BigDecimal.ONE;
		BigDecimal bdNumeroDias = BigDecimal.ZERO;
		BigDecimal bdMult100 = new BigDecimal(100);
		BigDecimal bdMult30 = new BigDecimal(30);
						
		try {
			dtHoy = fecHoy.getTime();
			
			if(expCreditoAntiguoId != null){
				expedienteAntiguo = new Expediente();
				expedienteAntiguo.setId(new ExpedienteId());
				expedienteAntiguo.getId().setIntCuentaPk(expCreditoAntiguoId.getIntCuentaPk());
				expedienteAntiguo.getId().setIntItemExpediente(expCreditoAntiguoId.getIntItemExpediente());
				expedienteAntiguo.getId().setIntItemExpedienteDetalle(expCreditoAntiguoId.getIntItemDetExpediente());
				expedienteAntiguo.getId().setIntPersEmpresaPk(expCreditoAntiguoId.getIntPersEmpresaPk());
				
			
				// 1. Recuperamos el interes (%)

				// 2. Interes Calculado - tabla. llave y max item movctacte
				ultimoInteresCancelado = conceptofacade.getMaxInteresCanceladoPorExpediente(expedienteAntiguo.getId());
					// si existe ... dias = hoy - fecha de registro o mov
				if(ultimoInteresCancelado != null && ultimoInteresCancelado.getTsFechaMovimiento() != null){
					Date dtFechaMovimiento = convertirTimestampToDate(ultimoInteresCancelado.getTsFechaMovimiento());
					intNumeroDias = obtenerDiasEntreFechas(dtFechaMovimiento, dtHoy);

				}else{
					Date dtFechaEstadGirado= new Date();
					// si no existe... t vas estado de credito y ver estado GIRO - 6
					lstEstados  = solicitudPrestamoFacade.getListaEstadosPorExpedienteCreditoId(expCreditoAntiguoId);
					if(lstEstados != null && !lstEstados.isEmpty()){
						for (EstadoCredito estadoCredito : lstEstados) {
							if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0
								&& estadoCredito.getTsFechaEstado() != null){
								dtFechaEstadGirado = convertirTimestampToDate(estadoCredito.getTsFechaEstado());
								break;
							}
						}
						intNumeroDias = obtenerDiasEntreFechas(dtFechaEstadGirado, dtHoy);
					}	
				}

				// 4. recupero el saldo del prestamo viejo
				expedienteAntiguo = conceptofacade.getExpedientePorPK(expedienteAntiguo.getId());
				bdMontoSaldoCredito = expedienteAntiguo.getBdSaldoCredito();				
				
				
				// 6. Calculo: dias * interes * saldo / 30 * 100
				if(intNumeroDias.compareTo(0)!=0){
					bdNumeroDias = new BigDecimal(intNumeroDias);
					bdMult01 = bdNumeroDias.multiply(bdPorcentajeInteres);
					bdMult02 = bdMult01.multiply(bdMontoSaldoCredito);
					bdMult03 = bdMult100.multiply(bdMult30);
					bdMontoInteresAtrasado = bdMult02.divide(bdMult03,2, BigDecimal.ROUND_HALF_UP);
					bdMontoInteresAtrasado = bdMontoInteresAtrasado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);					
				}
			}
		} catch (Exception e) {
			log.error("Error en calcularInteresAtrasado ---> "+e);
		}
		return bdMontoInteresAtrasado;
	}



	
	/**
	 * 
	 * @return
	 */
	public static final Timestamp obtieneFechaActualEnTimesTamp(){
		
	    java.util.Date utilDate = new java.util.Date(System.currentTimeMillis());
		java.sql.Date sqlDate1 = new java.sql.Date(utilDate.getTime());
		utilDate = new java.util.Date(System.currentTimeMillis());
		java.sql.Date sqlDate2 = new java.sql.Date(utilDate.getTime());
		java.sql.Timestamp ts = new java.sql.Timestamp(sqlDate1.getTime());
		System.out.println(ts);
		ts = new java.sql.Timestamp(sqlDate2.getTime());
	
	 return ts;
	}
	
	/**
	 * 
	 * @param fecha
	 * @return
	 */
    public static Timestamp convertirDateToTimeStamp(Date fecha){
        return new Timestamp(fecha.getTime());
    }


    /**
     * 
     * @param timestamp
     * @return
     */
    private static Date convertirTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
    

    /**
     * 
     * @param dtFechaInicio
     * @param dtFechaFin
     * @return
     * @throws Exception
     */
//    public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
//		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
//	}
    
    public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		SimpleDateFormat strEnlace = new SimpleDateFormat("dd/MM/yyyy");
		Date dtFecIni = strEnlace.parse(strEnlace.format(dtFechaInicio));
		Date dtFecFin = strEnlace.parse(strEnlace.format(dtFechaFin));
		return (int)( (dtFecFin.getTime() - dtFecIni.getTime()) / (1000 * 60 * 60 * 24) );
	} 
    
/**
 * 
 * @param creditoSeleccionado
 * @param estructuraDetalle
 */
 
public void generarCronograma(EstructuraDetalle estructuraDetalle, String strPorcentajeInteres) {
	CronogramaCredito cronogramaCredito = new CronogramaCredito();
	CronogramaCreditoComp cronogramaCreditoComp = new CronogramaCreditoComp();
	List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();
	Integer intNroCuotasTemporalMaximo = 120; // 10 años (exagereando)
	
			if (intNroCuotas == null || intNroCuotas == 0 || (intNroCuotas < 0)) {
				intNroCuotas = intNroCuotasTemporalMaximo;
				blnMarca = true;
			}else{
				blnMarca = false;
			}

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

			// CGD - 27.12.2013
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
//					if (envMes == 12) {
//						listaDiasEnvio.add(i, "01" + "/" + envMes + "/"+ envAnno);
//						envAnno = envAnno + 1;
//						envMes = 0;
//					} else {
//						listaDiasEnvio.add(i, "01" + "/" + envMes + "/"+ envAnno);
//					}
//					
//					nuevoDia.set(vencAnno, vencMes, 15);
//					if (vencMes == 12) {
//						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//						vencAnno = vencAnno + 1;
//						vencMes = 0;
//					} else {
//						listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//					}
				}
				vencMes++;
			}
			BigDecimal bdCuotaFinal = new BigDecimal(0);
			BigDecimal bdTea = (((BigDecimal.ONE.add(new BigDecimal(strPorcentajeInteres).divide(new BigDecimal(100)))).pow(12)).subtract(BigDecimal.ONE).setScale(4,RoundingMode.HALF_UP));

			// Calculamos el nro de dias entre las fechas de envio y vencimiento
			List listaDias = new ArrayList();
			List listaSumaDias = new ArrayList();
			
			int diferenciaEntreDias = 0;
			int sumaDias = 0;

			for (int i = 0; i <intNroCuotas; i++) {
				if (i == 0) {
					diferenciaEntreDias = fechasDiferenciaEnDias(dtFechaRegistro,(StringToCalendar(strVencimiento)).getTime());
					listaDias.add(i, diferenciaEntreDias);
				} else {
					Calendar calendario = Calendar.getInstance();
					Calendar calend = new GregorianCalendar(
							getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getYear(),
							getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getMonth(), 1);

					calendario.set(calend.get(Calendar.YEAR),calend.get(Calendar.MONTH),calend.get(Calendar.DATE));
					diferenciaEntreDias = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
					listaDias.add(i, diferenciaEntreDias);
				}
				sumaDias = sumaDias + diferenciaEntreDias;
				listaSumaDias.add(i, sumaDias);
			}

			// Calculamos el valor de la cuota en base a la formula:
			// ----------------------------------------------------------//
			// monto //
			// cuota = _____________________________________ //
			// //
			// 1/ (1+tea)^(sumdias/360) + ... n //
			// ----------------------------------------------------------//
			// SUMATORIA DEBE SER EL MAXIMO PREVIO A LA CUOTA FIJA DE CAPACIDADES
			BigDecimal bdSumatoria = new BigDecimal(0);
			System.out.println("TEA     " + bdTea);
			
			Integer z=0;
			BigDecimal bdAcumulado = BigDecimal.ZERO;
			
			if(blnMarca){
				do {
					try {
						BigDecimal bdCalculo1 = new BigDecimal(0);
						BigDecimal bdCalculo2 = new BigDecimal(0);
						BigDecimal bdCalculo3 = new BigDecimal(0);
						BigDecimal bdCalculo4 = new BigDecimal(0);
						BigDecimal bdResultado = new BigDecimal(0);
						BigDecimal bdUno = BigDecimal.ONE;
						double dbResultDenom = 0;
						bdCalculo1 = new BigDecimal(listaSumaDias.get(z).toString()); // suma de dias
						bdCalculo2 = new BigDecimal(360); // 360
						bdCalculo3 = bdTea.add(bdUno); // tea + 1
						bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
						dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
						bdResultado = bdUno.divide(new BigDecimal(dbResultDenom),4, RoundingMode.HALF_UP);
						bdSumatoria = bdSumatoria.add(bdResultado);
						z++;
						intNroCuotasCalculado = z;
						//bdAcumulado = beanExpedienteCredito.getBdMontoTotal().divide(bdSumatoria, RoundingMode.HALF_UP);
						bdAcumulado = new BigDecimal(strMontoTotalDelPrestamoRefinanciado).divide(bdSumatoria, RoundingMode.HALF_UP);
						bdAcumulado = bdAcumulado.add(bdAportes);
						System.out.println("APORTES   ---> " + bdAportes);
						System.out.println("ACUMULADO ---> " + bdAcumulado);
						System.out.println("NUMERO DE CUOTAS CALCULADAS ---> "+ intNroCuotasCalculado);
					} catch (ArithmeticException e) {
							log.equals("Error aritmetico ---> "+e);
					}
					
				} while (bdAcumulado.compareTo(bdMaxCapacidadPago.subtract(bdAportes))>= 0);
				
				intNroCuotas = intNroCuotasCalculado;
				
			}else{
				if( intNroCuotas.compareTo(intNroCuotasCalculado)< 0){
					msgTxtCuotaMensual = "El número de cuotas ingresadas: " + intNroCuotas + ", es inferior al mínimo calculado:  "+ intNroCuotasCalculado+".";
					return;
				}else{
					msgTxtCuotaMensual="";
				}
				
				for (int i = 0; i < intNroCuotas; i++) {
					BigDecimal bdCalculo1 = new BigDecimal(0);
					BigDecimal bdCalculo2 = new BigDecimal(0);
					BigDecimal bdCalculo3 = new BigDecimal(0);
					BigDecimal bdCalculo4 = new BigDecimal(0);
					BigDecimal bdResultado = new BigDecimal(0);
					BigDecimal bdUno = BigDecimal.ONE;
					double dbResultDenom = 0;
					bdCalculo1 = new BigDecimal(listaSumaDias.get(i).toString());
					bdCalculo2 = new BigDecimal(360); // 360
					bdCalculo3 = bdTea.add(bdUno); // tea + 1
					bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
					dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
					bdResultado = bdUno.divide(new BigDecimal(dbResultDenom),4, RoundingMode.HALF_UP);
					bdSumatoria = bdSumatoria.add(bdResultado);

				}	
			}

			bdCuotaFinal = new BigDecimal(strMontoTotalDelPrestamoRefinanciado).divide(bdSumatoria, 4,RoundingMode.HALF_UP);
			System.out.println("CUOTA FINAL " + bdCuotaFinal);

			// Calculando Interes, Amortizacion, Saldo y la cuota mensual
			// total
			// y se conforma el cronograma

			BigDecimal bdAmortizacion = new BigDecimal(0);
			BigDecimal bdSaldo = new BigDecimal(0);
			BigDecimal bdSaldoMontoCapital = new BigDecimal(0);
			// BigDecimal bdMontoCocepto = new BigDecimal(0);
			BigDecimal bdSumaAmortizacion = new BigDecimal(0);
			bdSaldo =  new BigDecimal(strMontoTotalDelPrestamoRefinanciado);
			//beanExpedienteCredito.getBdMontoTotal(); // se inicializa el saldo con el monto solicitado
			BigDecimal bdSaldoTemp = new BigDecimal(0);
			List listaSaldos = new ArrayList();

			for (int i = 0; i < intNroCuotas; i++) {
				BigDecimal bdCalculo1 = new BigDecimal(0);
				BigDecimal bdCalculo2 = new BigDecimal(0);
				BigDecimal bdCalculo3 = new BigDecimal(0);
				BigDecimal bdCalculo4 = new BigDecimal(0);
				BigDecimal bdCalculo5 = new BigDecimal(0);
				BigDecimal bdInteresCuota = new BigDecimal(0);
				BigDecimal bdUno = BigDecimal.ONE;
				double dbResultDenom = 0;
				
				try {
					bdCalculo1 = new BigDecimal(listaDias.get(i).toString());
					bdCalculo2 = new BigDecimal(360); // 360
					bdCalculo3 = bdTea.add(bdUno); // tea + 1
					bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
					dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
					bdCalculo5 = new BigDecimal(dbResultDenom).subtract(bdUno);
					// modificar el bdSaldoCapital para que vaya reduciendose
					bdInteresCuota = bdSaldo.multiply(bdCalculo5);
					bdInteresCuota = bdInteresCuota.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
					bdInteresCuota.setScale(4, RoundingMode.HALF_UP);// setScale(4,
					System.out.println("======================= CUOTA NRO " + i	+ " ========================");
					System.out.println("INTERES CUOTA " + bdInteresCuota);
					bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
					bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
					bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
					System.out.println("AMORTIZACION  " + bdAmortizacion);
					bdSaldo = bdSaldo.subtract(bdAmortizacion);
					bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,	RoundingMode.HALF_UP);
					listaSaldos.add(i, bdSaldo);
					System.out.println("SALDO   " + bdSaldo);
					
					if (i + 1 == intNroCuotas) {
						bdSaldo = BigDecimal.ZERO;
						BigDecimal bdSaldoRed = new BigDecimal(0);

						if (intNroCuotas == 1) {
							bdAmortizacion = beanExpedienteCredito.getBdMontoTotal();
						} else {
							bdSaldoRed = new BigDecimal(listaSaldos.get(i - 1).toString());
							bdAmortizacion = bdSaldoRed;
						}
					} else {
						bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
						bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE,4, RoundingMode.HALF_UP);
					}
					bdCuotaMensual = bdAmortizacion.add(bdInteresCuota);
					System.out.println("APORTES - EXISTENTES ---> "+bdAportes);
					
					bdAportes = bdAportes.divide(BigDecimal.ONE, 2,	RoundingMode.HALF_UP);
					// agregndo datos a cronograma
					cronogramaCreditoComp = new CronogramaCreditoComp();
					cronogramaCreditoComp.setStrFechaEnvio(Constante.sdf.format((StringToCalendar(listaDiasEnvio.get(i).toString())).getTime()));
					cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
					cronogramaCreditoComp.setIntDiasTranscurridos(new Integer(listaDias.get(i).toString()));
					cronogramaCreditoComp.setBdSaldoCapital(bdSaldo);
					cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
					cronogramaCreditoComp.setBdInteres(bdInteresCuota);
					cronogramaCreditoComp.setBdCuotaMensual(bdCuotaMensual);
					cronogramaCreditoComp.setBdAportes(bdAportes);
					cronogramaCreditoComp.setBdTotalCuotaMensual(bdCuotaMensual.add(bdAportes));
					listaCronogramaCreditoComp.add(cronogramaCreditoComp);
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.setIntNroCuota(i + 1);
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
					cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
					if (i == 0) {
						bdSaldoMontoCapital = beanExpedienteCredito.getBdMontoTotal();

					} else {
						bdSaldoMontoCapital = bdSaldoTemp;
					}
					bdSaldoTemp = bdSaldo;
					cronogramaCredito.setBdMontoCapital(bdSaldoMontoCapital);
					if (i + 1 == intNroCuotas) {
						cronogramaCredito.setBdMontoConcepto(bdSaldoMontoCapital);
					} else {
						cronogramaCredito.setBdMontoConcepto(i + 1 == intNroCuotas ? bdAmortizacion.add(bdMontoTotalSolicitado.subtract(bdAmortizacion))
										: bdAmortizacion);
					}
					// ------------------------>
					Date fechaVenc = new Date();
					fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();
					SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
					cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaCronogramaCredito.add(cronogramaCredito);
					// Agregando el tipo de Concepto - "Interés"
					cronogramaCredito = new CronogramaCredito();
					cronogramaCredito.setId(new CronogramaCreditoId());
					cronogramaCredito.setIntNroCuota(i + 1);
					cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
					cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
					cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_INTERES);
					cronogramaCredito.setBdMontoConcepto(bdInteresCuota);
					cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
					cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime())));
					cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					listaCronogramaCredito.add(cronogramaCredito);
				} catch (Exception e) {
					log.error("ererererererer ---> "+e);
				}
			}
			bdTotalCuotaMensual = bdCuotaFinal.add(bdAportes);
			
			// Se le agrega a la capacidad la cuota mensual
			//----------------------------------------------->
			if (listaCronogramaCreditoComp != null && listaCronogramaCreditoComp.size() > 0) {
				BigDecimal bdSumaAportCuota = new BigDecimal(0);
				bdSumaAportCuota = bdAportes.add(bdCuotaMensual);

				if (listaCapacidadCreditoComp != null) {
					if (listaCapacidadCreditoComp.size() == 1) {
						for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
							capacidadCreditoComp.getCapacidadCredito().setBdCuotaFija(bdTotalCuotaMensual);
						}
					}else{
						Boolean blnValidaMontosCap = Boolean.FALSE;
						blnValidaMontosCap = validarMontosCapacidadesCredito(bdTotalCuotaMensual);
					}
				}
				
				// ALMOST
				/*
				if (bdSumaAportCuota.compareTo(bdCapacidadPago.multiply(new BigDecimal(0.9))) > 0) {
					msgTxtCuotaMensual = "Crédito no permitido. La cuota mensual ( "+ bdSumaAportCuota+ " ) excede el 90% de la Capacidad de Pago del Socio ( "
							+ bdMaxCapacidadPago + " ).";
					listaCronogramaCreditoComp.clear();
					listaCronogramaCredito.clear();

				} else {
					msgTxtCuotaMensual = "";
				}
				*/
			}		
				// seteamos ultimos valores
				if(!listaCronogramaCredito.isEmpty()) beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);
				beanExpedienteCredito.setBdPorcentajeInteres(new BigDecimal(strPorcentajeInteres));
				beanExpedienteCredito.setBdPorcentajeGravamen(null);
				beanExpedienteCredito.setBdMontoGravamen(null);
				beanExpedienteCredito.setBdMontoAporte(bdTotalDstos); // % de monto solicitad
				beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);

	}


		/**
		 * Calcula la fecha del ler Envio y 1er Vencimiento.
		 * Toma en cuenta la existencia de salto al siguiente mes.
		 * @param estructuraDetalle
		 * @param fecHoy
		 * @return listaEnvioVencimiento (0, fec1erEnvio)(1, fec1erVenc);
		 */
		public String calcular1raFechaVencimiento(EstructuraDetalle estructuraDetalle) {
//			Calendar fecEnvioTemp = Calendar.getInstance();
//			String miFechaPrimerVenc = null;
//			Calendar fec1erVenc  = Calendar.getInstance();
			Calendar fecVenc1 	 = Calendar.getInstance();
//			Calendar fecVenc2 	 = Calendar.getInstance();
//			Calendar fecVenc3 	 = Calendar.getInstance();
			//Calendar fec1erEnvio = Calendar.getInstance();
//			List listaEnvioVencimiento = new ArrayList();
			Calendar miFecha = Calendar.getInstance();
			CapacidadCreditoComp capacidadMaximoEnvio =null;

			String strFecha1erVencimiento = "";
			try {
				miFecha.clear();
				int intFechRegDia = new Integer(Constante.sdf.format(dtFechaRegistro).substring(0,2));
				int intFechRegMes = new Integer(Constante.sdf.format(dtFechaRegistro).substring(3,5))-1;
				int intFechRegAno = new Integer(Constante.sdf.format(dtFechaRegistro).substring(6,10));
				
				// En la clase calendar enero es 0
//				if(intFechRegMes == 1){
//					intFechRegMes = 0;
//				}
				
				miFecha.set(intFechRegAno, intFechRegMes, intFechRegDia);
				//Integer intPeridoEnvio = 0;		
				capacidadMaximoEnvio = recuperarPerdiodoUltimoEnvio(beanSocioComp);			
				// SI NO HAY ENVIOS para todas las u.e. del soccio
				if(capacidadMaximoEnvio == null){
					// vencimiento
					Calendar clFechaVencTemp = Calendar.getInstance();
					clFechaVencTemp.setTime(dtFechaRegistro);
					clFechaVencTemp = getUltimoDiaDelMesCal(clFechaVencTemp);
					fecVenc1.clear();
					fecVenc1= clFechaVencTemp;
					
					if (dtFechaRegistro.getDate() > estructuraDetalle.getIntDiaEnviado()) {
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
		 * Recupera el maximo periodo de los envios realizados por la unidades ejecutoras existenrtes en la solicitud.
		 * @return
		 */
		public CapacidadCreditoComp recuperarPerdiodoUltimoEnvio(SocioComp socioComp){
			CapacidadCreditoComp capacidadMaximoEnvio = null;
			//List<Integer> lstUltimosPeridos=null;
			List<CapacidadCreditoComp> lstCapacidadesComp = null;
			try {
				if(listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()){
					lstCapacidadesComp = new ArrayList<CapacidadCreditoComp>();
					for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
						EstructuraId estructuraId = new EstructuraId();
						Integer intPeriodoRecuperado= 0;
						Integer intTipoSocio = 0;
						Integer intModalidad = 0;
						Integer intEmpresa = 0;
						
						intEmpresa = capacidadCreditoComp.getSocioEstructura().getIntEmpresaUsuario();
						intTipoSocio = socioComp.getSocio().getSocioEstructura().getIntTipoSocio();
						intModalidad = socioComp.getSocio().getSocioEstructura().getIntModalidad();
						estructuraId.setIntCodigo(capacidadCreditoComp.getSocioEstructura().getIntCodigo());
						estructuraId.setIntNivel(capacidadCreditoComp.getSocioEstructura().getIntNivel());
						
						intPeriodoRecuperado = planillaFacade.getMaxPeriodoEnviadoPorEmpresaYEstructuraYTipoSocioM(intEmpresa, estructuraId, intTipoSocio, intModalidad);
						if(intPeriodoRecuperado != null){
							capacidadCreditoComp.getSocioEstructura().setIntUltimoPeriodo(intPeriodoRecuperado);
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
 * Calcula la fecha del ler Envio y 1er Vencimiento
 * @param estructuraDetalle
 * @param fecHoy
 * @return listaEnvioVencimiento (0, fec1erEnvio)(1, fec1erVenc);
 */
	public List calcular1raFechaEnvioVencimientoxxxxxxxxxxx(EstructuraDetalle estructuraDetalle, Calendar fecHoy) {
		Calendar fecEnvioTemp = Calendar.getInstance();
		String miFechaPrimerVenc = null;
		Calendar fec1erVenc  = Calendar.getInstance();
		Calendar fecVenc1 	 = Calendar.getInstance();
		Calendar fecVenc2 	 = Calendar.getInstance();
		Calendar fecVenc3 	 = Calendar.getInstance();
		Calendar fec1erEnvio = Calendar.getInstance();
		List listaEnvioVencimiento = new ArrayList();
	
		try {
			if ((fecHoy.get(Calendar.DATE)) < estructuraDetalle.getIntDiaEnviado()) {
				fecVenc1.clear();
				fecVenc1.set(fecHoy.get(Calendar.YEAR), 
							fecHoy.get(Calendar.MONTH),
							fecHoy.get(Calendar.DATE));
				fecEnvioTemp.clear();
				fecEnvioTemp.set(fecHoy.get(Calendar.YEAR),
								fecHoy.get(Calendar.MONTH),
								estructuraDetalle.getIntDiaEnviado());
	
			} else { // Salta al mes siguiente
				fecVenc1.clear();
				fecVenc1.set(fecHoy.get(Calendar.YEAR),
						fecHoy.get(Calendar.MONTH) + 1, fecHoy.get(Calendar.DATE));
				fecEnvioTemp.clear();
				fecEnvioTemp.set(fecHoy.get(Calendar.YEAR),
						fecHoy.get(Calendar.MONTH) + 1,
						estructuraDetalle.getIntDiaEnviado());
			}
			fec1erEnvio = fecEnvioTemp;
	
			// fecEnvioTemp;
			// Se recalcula fecha de 1er Vencimiento - Se agrega un mes si intSalto
			// != 1
			if (estructuraDetalle.getIntSaltoEnviado() == 1) {
				miFechaPrimerVenc = Constante.sdf.format(getUltimoDiaDelMes(
						fecVenc1).getTime());
				fecVenc3 = StringToCalendar(miFechaPrimerVenc);
			} else {
				int dd = fecVenc1.get(Calendar.DATE); // captura día actual
				int mm = fecVenc1.get(Calendar.MONTH); // captura mes actual
				int aa = fecVenc1.get(Calendar.YEAR); // captura año actual
	
				fecVenc2.set(aa, mm + 1, dd);
				miFechaPrimerVenc = Constante.sdf.format(getUltimoDiaDelMes(
						fecVenc2).getTime());
				fecVenc3 = StringToCalendar(miFechaPrimerVenc);
			}
			fec1erVenc.clear();
			fec1erVenc = fecVenc3;
			fec1erEnvio.set(fec1erEnvio.get(Calendar.YEAR),	fec1erEnvio.get(Calendar.MONTH),estructuraDetalle.getIntDiaEnviado());
	
			listaEnvioVencimiento.add(0, fec1erEnvio);
			listaEnvioVencimiento.add(1, fec1erVenc);
		} catch (Exception e) {
			log.error("calcular1raFechaEnvioVencimiento ---> "+e);
		}
	
		return listaEnvioVencimiento;
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
 * Retorna la fecha del ultimo dia del mes
 * @param Calendar
 * @return Date
 */
public Date getUltimoDiaDelMes(Calendar fecha) {
	fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
			fecha.getActualMaximum(Calendar.DAY_OF_MONTH),
			fecha.getMaximum(Calendar.HOUR_OF_DAY),
			fecha.getMaximum(Calendar.MINUTE),
			fecha.getMaximum(Calendar.SECOND));

	return fecha.getTime();
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
 * Recupera los Creditos que cumplen con las validaciones configuradas y
 * continua la Evaluación
 * 
 * @param beanSocioComp
 * @param credito
 * @return
 */
public Credito validarConfiguracionCredito(SocioComp beanSocioComp,
	Credito credito) {
	Boolean hasMontMin = false;
	Boolean hasMontMax = false;
	Boolean hasPorcMin = false;
	Boolean hasPorcMax = false;
	Boolean hasDiasMax = false;
	Integer nroValidaciones = new Integer(0);
	Integer contAprob = new Integer(0);
	BigDecimal bdMontoPorcMinimo = null;
	BigDecimal bdMontoPorcMaximo = null;
	Boolean boAprueba = new Boolean(false);

	// System.out.println("CREDITO EVALUADO:"+credito.getStrDescripcion());
	// System.out.println("--------------------------------------------");

	// ---- De acuerdo a la configuracion del credito, se establece que se
	// valida...
	if (credito.getBdMontoMinimo() != null) {
		hasMontMin = true;
		nroValidaciones++;
	}
	if (credito.getBdMontoMaximo() != null) {
		hasMontMax = true;
		nroValidaciones++;
	}
	if (credito.getBdPorcMinimo() != null) {
		hasPorcMin = true;
		nroValidaciones++;
		bdMontoPorcMinimo = (beanSocioComp.getCuentaComp().getBdTotalAporte().multiply(credito.getBdPorcMinimo())).divide(new BigDecimal(100));
	}
	if (credito.getBdPorcMaximo() != null) {
		hasPorcMax = true;
		nroValidaciones++;
		bdMontoPorcMaximo = (beanSocioComp.getCuentaComp().getBdTotalAporte().multiply(credito.getBdPorcMaximo())).divide(new BigDecimal(100));
	}

	if (beanExpedienteCredito.getListaEstadoCredito().size()>0) {
		
		
	}else{
		// -------------------- Excepciones: Dias de gracia
		 if(credito.getListaExcepcion().size()>0){ 
			  listaExcepciones = credito.getListaExcepcion(); 
			  for(int i=0; i< credito.getListaExcepcion().size(); i++){
				  if(credito.getListaExcepcion().get(i).getIntPrimerCredito() != null){
					  hasDiasMax = true; 
					  nroValidaciones++; 
				  }
				  System.out.println("  PRIMER CREDITO ???   "  +i+" ---- "+credito.getListaExcepcion().get(i).getIntPrimerCredito());
			  }
		  }
		
		
	}

	// ----- Validando las restricciones
	if (hasMontMin	&& (bdMontoTotalSolicitado.compareTo(credito.getBdMontoMinimo()) >= 0))
			contAprob++;
	if (hasMontMax&& (bdMontoTotalSolicitado.compareTo(credito.getBdMontoMaximo()) <= 0))
			contAprob++;
	if (hasPorcMin && (bdMontoTotalSolicitado.compareTo(bdMontoPorcMinimo) >= 0))
			contAprob++;
	if (hasPorcMax	&& (bdMontoTotalSolicitado.compareTo(bdMontoPorcMaximo) <= 0))
			contAprob++;

	// -------------------- Validando Excepciones ------------------------//
	
	if (beanExpedienteCredito.getListaEstadoCredito().size()>0) {
		
		
	} else {
		/*for(int i=0; i< credito.getListaExcepcion().size(); i++){
			  if(credito.getListaExcepcion().get(i).getIntPrimerCredito() != null){
				  Integer diasExtr = new Integer(0); 
				  diasExtr = credito.getListaExcepcion().get(i).getIntPrimerCredito();
				  
				  int diferenciaEnDias = 0; 
				  Date fechaActual = Calendar.getInstance().getTime(); 
				  // HOY 
				  Date fechaApertura = new Date(beanSocioComp.getCuenta().getTsCuentFecRegistro().getTime());
				  
				  diferenciaEnDias= fechasDiferenciaEnDias(fechaApertura, fechaActual);
				  System.out.println("APERTURA" + Constante.sdf.format(fechaApertura));
				  System.out.println("FECHA ACTUAL" +
				  Constante.sdf.format(fechaActual));
				  System.out.println("NRO DE DIAS	gracia "+ diasExtr);
				  System.out.println("DIAS DE DIFERENCIA ENTRE"+ diferenciaEnDias);
				  
				  if(hasDiasMax && (diferenciaEnDias<diasExtr)){ 
					  contAprob++;
				  }
			  
			  }
		  }*/
		
	}

	System.out.println(" NRO DE VALIDACIONES EXISTENTES " + nroValidaciones);
	System.out.println(" NRO DE VALIDACIONES APROBADAS " + contAprob);

	if (nroValidaciones == contAprob)
		boAprueba = true;
	if (boAprueba) {
		return credito;
	} else {
		return null;
	}
}


	/**
	 * Redirecciona el tipo de grabacion a aplicar, si es Inicial(solicitud Nueva) o Modificar (solicitud ya existente).
	 * @param event
	 */
	public void grabarExpedienteRefinanciamiento(ActionEvent event){
		
		try {
			
			if (isValidoExpedienteRefinan(beanExpedienteCredito) == false) {
				log.info("Datos de Refinanciamiento no válidos. Se aborta el proceso de grabación de Refinanciamineto.");
				return;
			}else{
				if (intMiOperacion.compareTo(new Integer(1)) == 0) {
					grabarExpedienteRefinanciamientoInicial(event);
				} else {
					grabarExpedienteRefinanciamientoModificar(event);
				}
			}
			
			
			
		} catch (Exception e) {
			log.error("Error en grabarExpedienteRefinanciamiento --> "+e);
		}
	}


	/**
	 * Graba el refinancimaiento la primera ocasion
	 * @param event
	 */
	public void grabarExpedienteRefinanciamientoInicial ( ActionEvent event){
//			EstadoCredito estadoCredito = null;
//			EstadoCredito estadoRequisito = null;
//			EstadoCredito estadoSolicitud = null;
//			List<EstadoCredito> listaEstados = null;
			Integer intDetalleExpediente = 0;
			try {
				
			if (intMiOperacion.compareTo(new Integer(1)) == 0) {
				beanExpedienteCredito.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
				beanExpedienteCredito.getId().setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());
				beanExpedienteCredito.getId().setIntItemExpediente(beanExpedienteMovARefinanciar.getExpediente().getId().getIntItemExpediente());
				
				//Integer intDetalleExpediente = beanExpedienteMovARefinanciar.getExpediente().getId().getIntItemExpedienteDetalle();
				//intDetalleExpediente++;
				//--------------- cgd - 27.12.2013 -------------------------->
				// buscamos los expedientes con emp, cta e itemexp.. para recuepora
				List<ExpedienteCredito> lstExpedienteCred = null;
				lstExpedienteCred = solicitudPrestamoFacade.getMaxExpedienteRefinan(beanExpedienteCredito.getId().getIntPersEmpresaPk(), beanExpedienteCredito.getId().getIntCuentaPk(), beanExpedienteCredito.getId().getIntItemExpediente());
				if(lstExpedienteCred != null && !lstExpedienteCred.isEmpty()){
					ExpedienteCredito expCredito = new ExpedienteCredito();
					expCredito = lstExpedienteCred.get(0);
					
					intDetalleExpediente= expCredito.getId().getIntItemDetExpediente();
					intDetalleExpediente = intDetalleExpediente + 1;
					beanExpedienteCredito.getId().setIntItemDetExpediente(intDetalleExpediente);
					
				}else{
					return;
				}

				beanExpedienteCredito.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
				beanExpedienteCredito.setIntPersEmpresaCreditoPk(beanCreditoNuevo.getId().getIntPersEmpresaPk() == null ? 0 : beanCreditoNuevo.getId().getIntPersEmpresaPk());
				beanExpedienteCredito.setIntParaTipoCreditoCod(beanCreditoNuevo.getId().getIntParaTipoCreditoCod()== null ? 0 : beanCreditoNuevo.getId().getIntParaTipoCreditoCod());
				beanExpedienteCredito.setIntItemCredito(beanCreditoNuevo.getId().getIntItemCredito() == null ? 0 : beanCreditoNuevo.getId().getIntItemCredito());
				beanExpedienteCredito.setIntParaSubTipoOperacionCod(intSubTipoOperacion);
				beanExpedienteCredito.setIntParaFinalidadCod(intMotivoRefinanciamiento);
				beanExpedienteCredito.setBdMontoTotal(new BigDecimal(strMontoTotalDelPrestamoRefinanciado));
				beanExpedienteCredito.setBdMontoSolicitado(new BigDecimal(strMontoDelPrestamoRefinanciado));
				beanExpedienteCredito.setBdMontoInteresAtrasado(new BigDecimal(strInteresAtrasadoDelPrestamoRefinanciado));
				
				beanExpedienteCredito.setIntCuentaRefPk(beanExpedienteMovARefinanciar.getExpediente().getId().getIntCuentaPk());
				beanExpedienteCredito.setIntItemDetExpedienteRef(beanExpedienteMovARefinanciar.getExpediente().getId().getIntItemExpedienteDetalle());
				beanExpedienteCredito.setIntItemExpedienteRef(beanExpedienteMovARefinanciar.getExpediente().getId().getIntItemExpediente());
				beanExpedienteCredito.setIntPersEmpresaRefPk(beanExpedienteMovARefinanciar.getExpediente().getId().getIntPersEmpresaPk());
				
				// 18.09.2013 - CGD
				beanExpedienteCredito.setIntPersEmpresaSucAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntEmpresaSucAdministra());
				beanExpedienteCredito.setIntSucuIdSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
				beanExpedienteCredito.setIntSudeIdSubSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSubsucurAdministra());

				// Capacidades
				if (listaCapacidadCreditoComp != null && listaCapacidadCreditoComp.size() > 0) {
					beanExpedienteCredito.setListaCapacidadCreditoComp(listaCapacidadCreditoComp);
				}
				
				//Garantias
				List<GarantiaCredito> listaGarantiaCredito = null;
				if (listaGarantiaCreditoComp != null) {
					listaGarantiaCredito = new ArrayList<GarantiaCredito>();
					for (GarantiaCreditoComp garantiaCreditoComp : listaGarantiaCreditoComp) {
						garantiaCreditoComp.getGarantiaCredito().getId().setIntItemDetExpediente(intDetalleExpediente);
						listaGarantiaCredito.add(garantiaCreditoComp.getGarantiaCredito());
					}
					beanExpedienteCredito.setListaGarantiaCredito(listaGarantiaCredito);
				}
				
				// Requisitos
				if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
					beanExpedienteCredito.setListaRequisitoCreditoComp(new ArrayList<RequisitoCreditoComp>());
					beanExpedienteCredito.getListaRequisitoCreditoComp().addAll(listaRequisitoCreditoComp);
				}

				// dieferenciuar si es nuevo o modifica
			} 
			
			beanExpedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());

			EstadoCredito estadoRefinan = null;
				if (isValidTodaSolicitud() == 0) {
				//if (true) {

					estadoRefinan = new EstadoCredito();	
					estadoRefinan.setTsFechaEstado(new Timestamp(new Date().getTime()));
					estadoRefinan.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
					estadoRefinan.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
					estadoRefinan.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					estadoRefinan.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
					estadoRefinan.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
					beanExpedienteCredito.getListaEstadoCredito().add(estadoRefinan);
					//beanExpedientePrevision.setEstadoPrevisionUltimo(estadoPrevision);
				} else {
						// Si no se graba en estado REQUISITO
					estadoRefinan = new EstadoCredito();	
					estadoRefinan.setTsFechaEstado(new Timestamp(new Date().getTime()));
					estadoRefinan.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
					estadoRefinan.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
					estadoRefinan.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					estadoRefinan.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
					estadoRefinan.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
					beanExpedienteCredito.getListaEstadoCredito().add(estadoRefinan);
				}
			

				beanExpedienteCredito = solicitudPrestamoFacade.grabarExpedienteRefinanciamiento(beanExpedienteCredito);
				if (beanExpedienteCredito.getListaRequisitoCreditoComp() != null && beanExpedienteCredito.getListaRequisitoCreditoComp().size() > 0) {
					renombrarArchivo(beanExpedienteCredito.getListaRequisitoCreditoComp());
				}
				cancelarGrabarSolicitud(event);
				limpiarFormSolicitudRefinan();
				
			} catch (Exception e) {
				System.out.println("Error grabarExpedienteRefinanciamiento --> "+e);
				e.printStackTrace();
			} finally {
				cancelarGrabarSolicitud(event);
			}
		}

	
	/**
	 * 
	 * @param event
	 */
	public void grabarExpedienteRefinanciamientoModificar(ActionEvent event) {
		EstadoCredito estadoCredito = null;
		List<GarantiaCredito> listaGarantiaCredito = null;
		
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
					//estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
					estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
					estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
					estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					//estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
					estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
					beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);
					beanExpedienteCredito.setBdMontoTotal(new BigDecimal(strMontoTotalDelPrestamoRefinanciado));
					beanExpedienteCredito.setBdMontoSolicitado(new BigDecimal(strMontoDelPrestamoRefinanciado));	
				}
			}

		if (listaCapacidadCreditoComp != null	&& listaCapacidadCreditoComp.size() > 0) {
			beanExpedienteCredito.setListaCapacidadCreditoComp(listaCapacidadCreditoComp);
		}

		if (listaGarantiaCreditoComp != null && listaGarantiaCreditoComp.size() > 0) {
			listaGarantiaCredito = new ArrayList<GarantiaCredito>();
			for (GarantiaCreditoComp garantiaCreditoComp : listaGarantiaCreditoComp) {
				listaGarantiaCredito.add(garantiaCreditoComp.getGarantiaCredito());
			}
			beanExpedienteCredito.setListaGarantiaCredito(listaGarantiaCredito);
			// grabarGarante(event);
		}

		if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
			beanExpedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
		}
		
			solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
			limpiarFormSolicitudRefinan();
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
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 * @throws IOException
	 */
	/**
	 * 
	 */
	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException {
		log.info("-------------------------------------Debugging SocioController.putFile-------------------------------------");

		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		// fileupload.getObjArchivo();
		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_COPIA_DNI)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
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
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_COPIA_DNI)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
				//----------------------
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_AUTORIZACION_DESCUENTO_POR_INCENTIVOS)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
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
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_BOLETAS_DE_PAGO)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
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
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_CARTA_AUTORIZACION_DESCUENTO_100)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
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
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_PAGARE)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_PAGARE_FIRMADO_POR_GARANTES)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
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
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_RECIBO_SERVICIOS)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
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
	 * 
	 * @param stream
	 * @param object
	 * @throws IOException
	 */
	public void paintImage(OutputStream stream, Object object)
			throws IOException {
		stream.write(((MyFile) object).getData());
	}
	 
	/**
	 * Metodo q adjunta documento
	 * @param event
	 */
	public void adjuntarDocumento(ActionEvent event) {
		log.info("-------------------------------------Debugging BeneficiarioController.adjuntarFirma-------------------------------------");
		String strParaTipoDescripcion = getRequestParameter("intParaTipoDescripcion");
		String strParaTipoOperacionPersona = getRequestParameter("intParaTipoOperacionPersona");
		log.info("strParaTipoDescripcion: " + strParaTipoDescripcion);
		log.info("strParaTipoOperacionPersona: " + strParaTipoOperacionPersona);
		Integer intParaTipoDescripcion = new Integer(strParaTipoDescripcion);
		Integer intParaTipoOperacionPersona = new Integer(
				strParaTipoOperacionPersona);

		this.intParaTipoDescripcion = intParaTipoDescripcion;
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;

		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		// FileUploadController fileupload = new FileUploadController();
		fileupload.setStrDescripcion("Seleccione el archivo que desea adjuntar.");
		fileupload.setFileType(FileUtil.imageTypes);
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		
		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {
				
				// dividir entre 
				/*
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO = 12;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO = 13;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO = 14;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_DNI = 15;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_PAGARE = 16;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_AUTINCENTIVOLABORAL = 17;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_CONTRATOLABORAL = 18;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_AUTVISADABIENESTARSOCIAL = 19;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_DECLARACIONJURADA = 20;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_CRONOGRAMA = 21;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_ESTADOCUENTA = 22;
				public static final Integer PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS = 23;
				*/
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_AUTORIZACION_DESCUENTO_POR_INCENTIVOS)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO);
				}
				
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_BOLETAS_DE_PAGO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO);
				}
				
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_CARTA_AUTORIZACION_DESCUENTO_100)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO);
				}
				
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_COPIA_DNI)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
				}
				
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_PAGARE_FIRMADO_POR_GARANTES)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
				}
				
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_RECIBO_SERVICIOS)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS);
				}
				
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_PAGARE_FIRMADO_POR_GARANTES)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoCreditoComp.getRequisitoCredito()!= null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_PAGARE);
				}
				
			}
		}
		 //fileupload.setStrJsFunction("putFile");
		 fileupload.setStrJsFunction("putFileDocAdjunto()");
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
				tipoArchivo = new TipoArchivo();
				archivo = new Archivo();
				archivo.setId(new ArchivoId());
				if (requisitoCreditoComp.getArchivoAdjunto() != null) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_AUTORIZACION_DESCUENTO_POR_INCENTIVOS)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						
						String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
						archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
					}
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_COPIA_DNI)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						
						String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
						archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
					}
					
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_COPIA_DNI)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						
						String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
						archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
					}

					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_BOLETAS_DE_PAGO)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());

						String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
						archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
					}
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_CARTA_AUTORIZACION_DESCUENTO_100)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());

						String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
						archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
					}

					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_PAGARE_FIRMADO_POR_GARANTES)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						log.info("requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo(): "+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo());
						// String ruta = tipoArchivo.getStrRuta() + "\\" +
						// strCopiaDniTitular;
						String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
						archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
					}
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO_RECIBO_SERVICIOS)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						log.info("requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo(): "+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo());
						// String ruta = tipoArchivo.getStrRuta() + "\\" +
						// strCopiaDniTitular;
						String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo();
						archivo.getId().setIntParaTipoCod(requisitoCreditoComp.getRequisitoCredito().getIntParaTipoArchivoCod());
						archivo.getId().setIntItemArchivo(requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo());
						archivo.getId().setIntItemHistorico(requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico());
						archivo = generalFacade.getArchivoPorPK(archivo.getId());
						FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
					}
					
				}
			}
		} catch (BusinessException e) {
			log.error(e);
			throw e;
		}
	}

	
	/**
	 * Recupera todos los creditos que aun no se terminan de cancelar (Moviemiento) del Socio validado.
	 * @see beanSocioComp.CuentaComp.ListExpedienteMovimientoComp();
	 * @return listExpedienteMovimientoComp
	 */
	public List<ExpedienteComp> recuperarCreditosMovimiento(){
		List<Expediente> listaCreditos = null;
		Integer intCuenta = beanSocioComp.getCuenta().getId().getIntCuenta();
		Integer intEmpresa = beanSocioComp.getCuenta().getId().getIntPersEmpresaPk();
		Expediente expedienteMov = null;
		//List<ExpedienteComp> listExpedienteMovimientoComp = null;
		try {
			// Creditos movimientos con saldo
			listaCreditos =  conceptofacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa,intCuenta);
			
			if(listaCreditos != null && !listaCreditos.isEmpty()){
				listExpedienteMovimientoComp = new ArrayList<ExpedienteComp>();
				for(int k=0; k<listaCreditos.size();k++) {
					// Solo si son prestamos o 	r3efinanciamientos				
					if(listaCreditos.get(k).getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)==0
					|| listaCreditos.get(k).getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)==0){
						expedienteMov = new Expediente();
						expedienteMov = listaCreditos.get(k); 
						ExpedienteComp expedienteComp = new ExpedienteComp();
						expedienteComp.setExpediente(expedienteMov);
						//listExpedienteMovimientoComp.add(expedienteComp)
						listExpedienteMovimientoComp.add(k,expedienteComp);

						// cargando el tipo credito empresa
						CreditoId creditoId = null;
						Credito credito = null;
						//if(listaCreditos.get(k).getIntParaTipoCreditoCod().compareTo( Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)==0){
						creditoId = new CreditoId();
						creditoId.setIntPersEmpresaPk(listaCreditos.get(k).getId().getIntPersEmpresaPk());
						creditoId.setIntItemCredito(listaCreditos.get(k).getIntItemCredito());
						creditoId.setIntParaTipoCreditoCod(listaCreditos.get(k).getIntParaTipoCreditoCod());
							
						// buscamos el tipocreditoempresa de confcreditos
						credito = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
						listExpedienteMovimientoComp.get(k).setStrDescripcionTipoCreditoEmpresa(credito.getStrDescripcion());
												
						if(credito != null){
							for(int e=0;e<listaDescTipoCreditoEmpresa.size();e++){
								if(listaDescTipoCreditoEmpresa.get(e).getIntIdDetalle().compareTo(credito.getIntParaTipoCreditoEmpresa())==0){
									listExpedienteMovimientoComp.get(k).setStrDescripcionTipoCredito(listaDescTipoCreditoEmpresa.get(e).getStrDescripcion());
									break;
								}
							}
						}
					}
				}
				listExpedienteMovimientoComp = calcularCuotasPagadasVencidas(listExpedienteMovimientoComp);
			}
		} catch (BusinessException e) {
			log.error("Error en recuperarCreditos --> "+e);
		}
		return listExpedienteMovimientoComp;
	}


	/**
	 * Recorre la lista de todos los Expedientes (Movimiento) y recupera el seleccionado con check. 
	 * @use recuperarGarantesPrestamo()
	 * @return expedienteCreditoComp
	 */
	public ExpedienteComp recuperarPrestamoSeleccionado(){
		ExpedienteComp expedienteCreditoComp = null;
		List<GarantiaCredito> listaGarantias = null;
		Integer intContador = new Integer(0);
		ExpedienteCreditoId expedienteCreditoId = null;
		try {
			strMsgErrorExisteRefinanciamiento="";
			
			for (ExpedienteComp expedienteComp : listExpedienteMovimientoComp) {
				if (expedienteComp.getChecked().equals(true)) {
					intContador++;
				}
			}
			if (intContador.compareTo(new Integer(1)) == 0 ) {
				strMsgErrorPreEvaluacion = "";

				for (ExpedienteComp expedienteComp : listExpedienteMovimientoComp) {
					if (expedienteComp.getChecked().equals(true)) {
						expedienteCreditoId = new ExpedienteCreditoId();
						expedienteCreditoId.setIntCuentaPk(expedienteComp.getExpediente().getId().getIntCuentaPk());
						expedienteCreditoId.setIntItemDetExpediente(expedienteComp.getExpediente().getId().getIntItemExpedienteDetalle());
						expedienteCreditoId.setIntItemExpediente(expedienteComp.getExpediente().getId().getIntItemExpediente());
						expedienteCreditoId.setIntPersEmpresaPk(expedienteComp.getExpediente().getId().getIntPersEmpresaPk());
						
						recuperarGarantesPrestamo(expedienteCreditoId);
						expedienteCreditoComp = expedienteComp;
						break;
					}
				}

			} else if (intContador.compareTo(new Integer(0)) == 0) {
				strMsgErrorPreEvaluacion = "Se debe seleccionar UN préstamo. ";
			} else {
				strMsgErrorPreEvaluacion = "Se debe seleccionar SOLO un préstamo. ";
			}
		} catch (Exception e) {
			log.error("Error en recuperarPrestamo ---> "+e);
		}
		
		return expedienteCreditoComp;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public ExpedienteComp recuperarPrestamoSeleccionadoModificar(){
		ExpedienteComp expedienteCreditoComp = null;
		List<GarantiaCredito> listaGarantias = null;
		Integer intContador = new Integer(0);
		ExpedienteCreditoId expedienteCreditoId = null;
		Expediente expedienteMovimientoAnterior = null;
		ExpedienteId expedienteId = null;
		Integer intItemExpedienteDetalle = null;
		try {
			strMsgErrorExisteRefinanciamiento="";
			
			
			//intItemExpedienteDetalle = beanExpedienteCredito.getId().getInt
			// veridficar error ----- xxx ----
			//intItemExpedienteDetalle--;
			expedienteId = new ExpedienteId();
			expedienteId.setIntCuentaPk(beanExpedienteCredito.getIntCuentaRefPk());
			expedienteId.setIntItemExpediente(beanExpedienteCredito.getIntItemExpedienteRef());
			expedienteId.setIntItemExpedienteDetalle(beanExpedienteCredito.getIntItemDetExpedienteRef());
			expedienteId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaRefPk());

			expedienteMovimientoAnterior  = conceptofacade.getExpedientePorPK(expedienteId);
			
			for (ExpedienteComp expedienteComp : beanSocioComp.getCuentaComp().getListExpedienteMovimientoComp()){
					if((expedienteMovimientoAnterior.getId().getIntCuentaPk().compareTo(expedienteComp.getExpediente().getId().getIntCuentaPk())==0)
						&& (expedienteMovimientoAnterior.getId().getIntItemExpediente().compareTo(expedienteComp.getExpediente().getId().getIntItemExpediente())==0)	
						&& (expedienteMovimientoAnterior.getId().getIntItemExpedienteDetalle().compareTo(expedienteComp.getExpediente().getId().getIntItemExpedienteDetalle())==0)
						&& (expedienteMovimientoAnterior.getId().getIntPersEmpresaPk().compareTo(expedienteComp.getExpediente().getId().getIntPersEmpresaPk())==0)
					){
						expedienteComp.setChecked(true);
						listExpedienteMovimientoComp.clear();
						listExpedienteMovimientoComp.add(expedienteComp);
					}
				//
			}
			
			// PARA DAR EFECTO DE SUBTABLA CON PRESTAMOS ANTERIROES... 06.09.2013
			beanSocioComp.getCuentaComp().setIntTamannoListaExp(2);
			//beanExpedienteCredito.getId()
			for (ExpedienteComp expedienteComp : listExpedienteMovimientoComp) {
				if (expedienteComp.getChecked().equals(true)) {
					intContador++;
				}
			}
			if (intContador.compareTo(new Integer(1)) == 0 ) {
				strMsgErrorPreEvaluacion = "";

				for (ExpedienteComp expedienteComp : listExpedienteMovimientoComp) {
					if (expedienteComp.getChecked().equals(true)) {
						expedienteCreditoId = new ExpedienteCreditoId();
						expedienteCreditoId.setIntCuentaPk(expedienteComp.getExpediente().getId().getIntCuentaPk());
						expedienteCreditoId.setIntItemDetExpediente(expedienteComp.getExpediente().getId().getIntItemExpedienteDetalle());
						expedienteCreditoId.setIntItemExpediente(expedienteComp.getExpediente().getId().getIntItemExpediente());
						expedienteCreditoId.setIntPersEmpresaPk(expedienteComp.getExpediente().getId().getIntPersEmpresaPk());
						
						//recuperarGarantesPrestamo(expedienteCreditoId);
						expedienteCreditoComp = expedienteComp;
						break;
					}
				}

			} else if (intContador.compareTo(new Integer(0)) == 0) {
				strMsgErrorPreEvaluacion = "Se debe seleccionar UN préstamo. ";
			} else {
				strMsgErrorPreEvaluacion = "Se debe seleccionar SOLO un préstamo. ";
			}
		} catch (Exception e) {
			log.error("Error en recuperarPrestamo ---> "+e);
		}
		
		return expedienteCreditoComp;
	}
	
	
	/**
	 * Devuelve un nuevo ExpedienteCredito en base al ExpedienteMovieminto y el ExpedienteCredito Antiguo.
	 * Tambien genera los datos strMoraAtrasado, strInteresAtrasado y strMontoTotalRefinanciamiento.
	 * @param expediente
	 * @return
	 */	
	public ExpedienteCredito convertirExpedienteMovimientoAExpedienteCreditoMasMoraInteresTotal(Expediente expedienteMovimiento , ExpedienteCredito expedienteCreditoAnt ){
		ExpedienteCredito nuevoCredito = null;
		ExpedienteCreditoId nuevoCreditoId = null;
		BigDecimal bdMontoTotalRefinanciamiento = BigDecimal.ZERO;
		BigDecimal bdMontoMoraAtrasado = BigDecimal.ZERO;
		BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
		
		try {

			if(expedienteMovimiento != null && expedienteCreditoAnt != null){
				nuevoCreditoId = new ExpedienteCreditoId();
				nuevoCredito = new ExpedienteCredito();
				
				nuevoCreditoId.setIntCuentaPk(expedienteMovimiento.getId().getIntCuentaPk());
				nuevoCreditoId.setIntItemDetExpediente(null);
				nuevoCreditoId.setIntItemExpediente(expedienteMovimiento.getId().getIntItemExpediente());
				nuevoCreditoId.setIntPersEmpresaPk(expedienteMovimiento.getId().getIntPersEmpresaPk());
				nuevoCredito.setId(nuevoCreditoId);
				nuevoCredito.setBdMontoSolicitado(expedienteMovimiento.getBdSaldoCredito());
				// == null ? 0 : 
				nuevoCredito.setBdPorcentajeInteres(expedienteCreditoAnt.getBdPorcentajeInteres());
				//strPorcentajeInteres = ""+expedienteCreditoAnt.getBdPorcentajeInteres();
								
				bdMontoTotalRefinanciamiento = bdMontoTotalRefinanciamiento.add(nuevoCredito.getBdMontoSolicitado());
				bdMontoTotalRefinanciamiento = bdMontoTotalRefinanciamiento.add(expedienteMovimiento.getBdMontoInteresAtrazado() == null ? BigDecimal.ZERO : expedienteMovimiento.getBdMontoInteresAtrazado());
				bdMontoTotalRefinanciamiento = bdMontoTotalRefinanciamiento.add(expedienteMovimiento.getBdMontoMoraAtrazado() == null ? BigDecimal.ZERO : expedienteMovimiento.getBdMontoMoraAtrazado());
				bdMontoTotalRefinanciamiento = bdMontoTotalRefinanciamiento.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
				nuevoCredito.setBdMontoTotal(bdMontoTotalRefinanciamiento);
				
				//***********************************************
				//bdMontoInteresAtrasado = calcularInteresAtrasado(expedienteCreditoAnt.getId());
				strInteresAtrasadoDelPrestamoRefinanciado = ""+bdMontoInteresAtrasado;
				strMontoDelPrestamoRefinanciado = beanExpedienteMovARefinanciar.getBdSaldo() == null ? "0.00" : ""+beanExpedienteMovARefinanciar.getBdSaldo();
				//strInteresAtrasadoDelPrestamoRefinanciado = expedienteMovimiento.getBdMontoInteresAtrazado() == null ? "0.00" : ""+expedienteMovimiento.getBdMontoInteresAtrazado();
				strMoraAtrasadoDelPrestamoRefinanciado    = expedienteMovimiento.getBdMontoMoraAtrazado() == null ? "0.00": ""+expedienteMovimiento.getBdMontoMoraAtrazado();
				strMontoTotalDelPrestamoRefinanciado = ""+bdMontoTotalRefinanciamiento;
			}
			
			
		} catch (Exception e) {
			log.error("Error en convertirExpMovimientoCredito ---> "+e);
		}
		
		
		return nuevoCredito;	
	}
	
	
	/**
	 * 
	 */
	public void recalcularMontos(){
		BigDecimal bdInteres = BigDecimal.ZERO;
		BigDecimal bdPrestamo = BigDecimal.ZERO;
		BigDecimal bdTotalPrestamo = BigDecimal.ZERO;
		//MathContext mc1 = new MathContext(2, RoundingMode.HALF_UP);

		try {
			bdInteres = new BigDecimal(strInteresAtrasadoDelPrestamoRefinanciado);
			bdPrestamo = new BigDecimal(beanExpedienteCredito.getBdMontoSolicitado().toString());
			bdTotalPrestamo = bdPrestamo.add(bdInteres);
			bdTotalPrestamo = bdTotalPrestamo.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
			strMontoTotalDelPrestamoRefinanciado = ""+bdTotalPrestamo;

		} catch (Exception e) {
			log.error("Error en recalcularMontos --> "+e);
		}
		
	}
	
	
	/**
	 * 
	 */
	public List<ExpedienteComp> calcularCuotasPagadasVencidas(List<ExpedienteComp>listExpedienteMovimientoComp){
		ExpedienteComp expedienteComp= null;
		List<Cronograma> lstCronograma = null;
		Integer intCuotasPagadas =null;
		Integer intCuotasDefinidas = null;
		Integer intCuotasAtrasadas =  null;
		Integer intOperacion =  null;
		BigDecimal bdMontoCuotaFija =  null;
		BigDecimal bdMontoSaldo =  null;
		List<ExpedienteComp> lstExpedienteResult = null;

		try {
			intCuotasPagadas = new Integer(0);
			intCuotasAtrasadas = new Integer(0);
			intCuotasDefinidas= new Integer(0);
			lstExpedienteResult = new ArrayList<ExpedienteComp>();
			
			for (int k = 0; k < listExpedienteMovimientoComp.size(); k++) {
				expedienteComp = listExpedienteMovimientoComp.get(k);
				lstCronograma = conceptofacade.getListaCronogramaPorPkExpediente(expedienteComp.getExpediente().getId());
				
				//Ordenamos los subtipos por int
				Collections.sort(lstCronograma, new Comparator<Cronograma>(){
					public int compare(Cronograma uno, Cronograma otro) {
						return uno.getId().getIntItemCronograma().compareTo(otro.getId().getIntItemCronograma());
					}
				});

					
					if(lstCronograma != null && !lstCronograma.isEmpty()){

						for(Cronograma cronograma: lstCronograma){
							// 1. calculando cuotas Pagadas
							if(cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)==0){
								intCuotasPagadas ++;
							}
							// 2. calculando atrasadas
							if((cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)!=0)
									&& (cronograma.getTsFechaVencimiento().before(dtFechaRegistro))){
								intCuotasAtrasadas ++;
							}
						}
						intCuotasDefinidas = expedienteComp.getExpediente().getIntNumeroCuota();
						bdMontoCuotaFija = lstCronograma.get(0).getBdMontoConcepto();
						bdMontoSaldo = expedienteComp.getExpediente().getBdSaldoCredito();
						
						expedienteComp.setIntNroCuotasPagadas(intCuotasPagadas/2);
						expedienteComp.setIntNroCuotasAtrasadas(intCuotasAtrasadas/2);
						expedienteComp.setBdCuotaFija(bdMontoCuotaFija);
						expedienteComp.setBdSaldo(bdMontoSaldo);
						expedienteComp.setIntNroCuotasDefinidas(intCuotasDefinidas);
						lstExpedienteResult.add(expedienteComp);

					}
			}
			
		} catch (BusinessException e) {
			strMsgErrorValidarDatos = strMsgErrorValidarDatos+" Se ha producido un error al Calcular Cuotas Pagadas y/o Vencidas.";
			log.error("Error en calcularCuotasPagadasVencidas"+e);
		}
		return lstExpedienteResult;	
	}
	
	/**
	 * Recupera los garantes del Credito(Servicio) seleccionado. Y los almacena en listaGarantiaCreditoComp().
	 * @param expedienteCreditoId
	 * @return
	 */
	public void recuperarGarantesPrestamo (ExpedienteCreditoId expedienteCreditoId){
		List<GarantiaCredito> listaGarantias = null;
		List<GarantiaCreditoComp> listaGarantiaCompValidadas = null;
		try {
			listaGarantias = solicitudPrestamoFacade.getListaGarantiaCreditoPorId(expedienteCreditoId);
			
			// Se limia la lista para evitar duplicidad.
			if(!listaGarantiaCreditoComp.isEmpty())listaGarantiaCreditoComp.clear();
			
			if (listaGarantias != null && listaGarantias.size() > 0) {
				
				for (int k=0; k<listaGarantias.size(); k++) {
					Estructura estructuraGarante = new Estructura();
					EstructuraId estructuraIdGarante= new EstructuraId();
					// SocioComp socioCompGarante = new SocioComp();
					GarantiaCreditoComp garantiaComps = new GarantiaCreditoComp();
					SocioComp socioCompGaranteEdit = new SocioComp();
					
					Persona personaGarante = new Persona();
					personaGarante = personaFacade.getPersonaNaturalPorIdPersona(listaGarantias.get(k).getIntPersPersonaGarantePk());
					
					if(personaGarante!=null){
						if(personaGarante.getListaDocumento()!=null && personaGarante.getListaDocumento().size()>0){
							for (Documento documento : personaGarante.getListaDocumento()) {
								if(documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))){
									personaGarante.setDocumento(documento);
									break;
								}
							}
						}
						
						//socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(intTipoDoc, strNumIdentidad,
						//		Constante.PARAM_EMPRESASESION);
						
						socioCompGaranteEdit = socioFacade.getSocioCompXDocEmpCuenta(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), 
								personaGarante.getDocumento().getStrNumeroIdentidad(), 
								Constante.PARAM_EMPRESASESION,
								listaGarantias.get(k).getIntPersCuentaGarantePk());
						//socioCompGaranteEdit = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI), personaGarante.getDocumento().getStrNumeroIdentidad(), Constante.PARAM_EMPRESASESION);
						
						
						if(socioCompGaranteEdit!=null){
							if(socioCompGaranteEdit.getSocio().getListSocioEstructura() !=null && socioCompGaranteEdit.getSocio().getListSocioEstructura().size()>0){
								int ultimo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
								estructuraIdGarante.setIntNivel(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntNivel());
								estructuraIdGarante.setIntCodigo(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(ultimo-1).getIntCodigo());
								estructuraGarante.setId(estructuraIdGarante);
								
								estructuraGarante = estructuraFacade.getEstructuraPorPk(estructuraIdGarante);
								
								if(estructuraGarante != null){
								 
									 if(estructuraGarante.getListaEstructura() != null){
											int maximo = estructuraGarante.getListaEstructura().size();
											Juridica juridicaGarante = new Juridica();
											juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
											estructuraGarante.setJuridica(juridicaGarante);
										}
								}
							
								if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null){
									int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
									socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
								}
								
								Integer nroMaxAsegurados = new Integer(-1);
								nroMaxAsegurados=solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGaranteEdit.getPersona().getIntIdPersona());
								
								if (nroMaxAsegurados != null) {
									garantiaComps.setIntNroGarantizados(nroMaxAsegurados);
								}
								// agregamos garantiaCredito, Estructura, nroGarantizados y socioComp
								garantiaComps.setGarantiaCredito(listaGarantias.get(k));
								garantiaComps.getGarantiaCredito().getId().setIntItemGarantia(null);
								garantiaComps.setEstructura(estructuraGarante);
								garantiaComps.setSocioComp(socioCompGaranteEdit);									
								listaGarantiaCreditoComp.add(garantiaComps);

							}
						}

					}

				}

			}
			
		} catch (BusinessException e) {
			log.error("Error en recuperarGarantesPrestamo ---> "+e);
		}
	}
		
	/**
	 * Se recupera las garantias de credito del tipo personal
	 * @return
	 */
	public CreditoGarantia recuperarConfiguracionGarantiaTipoPersonal(){
		CreditoGarantia garantiasPersonales = null;
		try {
			garantiasPersonales = creditoFacade.recuperarGarantiasPersonales(beanCreditoNuevo);

		} catch (Exception e) {
			log.error("Error en recuperarConfiguracionGarantiaTipoPersonal ---> "+e);
		}
		return garantiasPersonales;
		
	}
	
	public void isValidCondicionCancelarPorPlanilla(){
		
		// strMsgCondicionCancelarPorPlanilla
	}
	
	public void isValidCondicionPlazoMaximoPrestamo(){
		
		// strMsgCondicionPlazoMaximoPrestamo
	}

	
	/**
	 * Valida la edad del socio vs excepcion configurada, si existiese..
	 * Devuelve TRUE si excede edad.
	 */
	public Boolean isValidCondicionTiempoCeseTrabajador(SocioComp socioComp){
		Integer intEdadSocio = new Integer(0);
		BigDecimal bdEdadSocio = BigDecimal.ZERO;
		BigDecimal bdEdadSocioDefinida = BigDecimal.ZERO;
		BigDecimal bdCondideracion = new BigDecimal(5);
		BigDecimal bdEdadSocioNuevaDefinida = BigDecimal.ZERO;
		//Integer  intAnnosDiferencia = new Integer(0);
		BigDecimal bdAnnosDiferencia = BigDecimal.ZERO;
		CreditoExcepcion excepcion = null;
		List<CreditoExcepcion> lstExcepciones = null;
		Boolean blnExcede = Boolean.FALSE;
		try {
			//CreditoExcepcionBO boCreditoExcepcion = (CreditoExcepcionBO)TumiFactory.get(CreditoExcepcionBO.class);
			if(beanCreditoNuevo.getListaExcepcion() != null && !beanCreditoNuevo.getListaExcepcion().isEmpty()){
				excepcion = new CreditoExcepcion();
				excepcion= beanCreditoNuevo.getListaExcepcion().get(0);
				
			}else{
				lstExcepciones = creditoExcepcionFacade.getListaCreditoExcepcion(beanCreditoNuevo.getId());
				if(lstExcepciones != null && !lstExcepciones.isEmpty()){
					excepcion = new CreditoExcepcion();
					excepcion= lstExcepciones.get(0);
				}
			}
			
			if(excepcion != null){
				bdEdadSocioDefinida = new BigDecimal(excepcion.getIntEdadLimite());
				
				// fecha de nacimientno + hoy
				intEdadSocio = fechasDiferenciaEnAnnos(socioComp.getPersona().getNatural().getDtFechaNacimiento(), dtHoy);
				if(intEdadSocio != 0){
					//bdEdadSocio = new BigDecimal(80);
					bdEdadSocio = new BigDecimal(intEdadSocio);
						//new BigDecimal(Constante.PARAM_T_TIEMPO_CESE_TRABAJADOR);

					if(bdEdadSocio.compareTo(bdEdadSocioDefinida)>0){
						blnExcede = Boolean.TRUE;
						setStrMsgCondicionTiempoCeseTrabajador("El Socio excedió la edad de Cese."+"Edad socio: "+bdEdadSocio);
					}else{
						setStrMsgCondicionTiempoCeseTrabajador("");
					}
				
				}else{
					bdEdadSocioNuevaDefinida = bdEdadSocioDefinida.subtract(bdCondideracion);
					if(bdEdadSocio.compareTo(bdEdadSocioNuevaDefinida)>0){
						bdAnnosDiferencia = bdEdadSocioDefinida.subtract(bdEdadSocio);
						bdAnnosDiferencia.divide(BigDecimal.ONE, 0,RoundingMode.HALF_UP);

						setStrMsgCondicionTiempoCeseTrabajador("Faltan "+ bdAnnosDiferencia+" años para el cese del socio."+"Edad socio: "+bdEdadSocio);
					}else{
						setStrMsgCondicionTiempoCeseTrabajador("Edad socio: "+bdEdadSocio);
					}
				}
				
			}else{
				setStrMsgCondicionTiempoCeseTrabajador("");
			}

		} catch (Exception e) {
			log.error("isValidCondicionTiempoCeseTrabajador ---> "+e);
		}
		return blnExcede;
	}
	
	/**
	 *  Valida la edad del garante del refinanciamiento.
	 *  TRUE:excede la edad. FALSE: no excede.
	 * @param garanteComp
	 * @return
	 */
	public Boolean validarEdadGarante(SocioComp garanteComp){
		Integer intEdadSocio = new Integer(0);
		BigDecimal bdEdadSocio = BigDecimal.ZERO;
		BigDecimal bdEdadSocioDefinida = BigDecimal.ZERO;
		BigDecimal bdCondideracion = new BigDecimal(5);
		BigDecimal bdEdadSocioNuevaDefinida = BigDecimal.ZERO;
		//Integer  intAnnosDiferencia = new Integer(0);
		BigDecimal bdAnnosDiferencia = BigDecimal.ZERO;
		CreditoExcepcion excepcion = null;
		List<CreditoExcepcion> lstExcepciones = null;
		Boolean blnExcedioEdad = Boolean.FALSE;
		try {
			//CreditoExcepcionBO boCreditoExcepcion = (CreditoExcepcionBO)TumiFactory.get(CreditoExcepcionBO.class);
			if(beanCreditoNuevo.getListaExcepcion() != null && !beanCreditoNuevo.getListaExcepcion().isEmpty()){
				excepcion = new CreditoExcepcion();
				excepcion= beanCreditoNuevo.getListaExcepcion().get(0);
				
			}else{
				lstExcepciones = creditoExcepcionFacade.getListaCreditoExcepcion(beanCreditoNuevo.getId());
				if(lstExcepciones != null && !lstExcepciones.isEmpty()){
					excepcion = new CreditoExcepcion();
					excepcion= lstExcepciones.get(0);
				}
			}
			
			if(excepcion != null){
				bdEdadSocioDefinida = new BigDecimal(excepcion.getIntEdadLimite());
				
				// fecha de nacimientno + hoy
				intEdadSocio = fechasDiferenciaEnAnnos(garanteComp.getPersona().getNatural().getDtFechaNacimiento(), dtHoy);
				if(intEdadSocio != 0){
					// pruebas cgd - 11.09.2013
					//bdEdadSocio = new BigDecimal(intEdadSocio);
					bdEdadSocio = new BigDecimal(62);
					
						//new BigDecimal(Constante.PARAM_T_TIEMPO_CESE_TRABAJADOR);
					
					
					if(bdEdadSocio.compareTo(bdEdadSocioDefinida)>0){
						blnExcedioEdad = Boolean.TRUE;
						setStrMsgCondicionTiempoCeseTrabajador("El Garante excedió la edad de Cese."+"Edad socio: "+bdEdadSocio);
					}else{
						setStrMsgCondicionTiempoCeseTrabajador("");
					}
				
				}else{
					bdEdadSocioNuevaDefinida = bdEdadSocioDefinida.subtract(bdCondideracion);
					if(bdEdadSocio.compareTo(bdEdadSocioNuevaDefinida)>0){
						bdAnnosDiferencia = bdEdadSocioDefinida.subtract(bdEdadSocio);
						bdAnnosDiferencia.divide(BigDecimal.ONE, 0,RoundingMode.HALF_UP);

						setStrMsgCondicionTiempoCeseTrabajador("Faltan "+ bdAnnosDiferencia+" años para el cese del socio."+"Edad socio: "+bdEdadSocio);
					}else{
						setStrMsgCondicionTiempoCeseTrabajador("Edad socio: "+bdEdadSocio);
					}
				}
				
			}else{
				setStrMsgCondicionTiempoCeseTrabajador("");
			}

		} catch (Exception e) {
			log.error("Error en validarEdadGarante ---> "+e);
		}
		return blnExcedioEdad;
	}
	
	/**
	 * 
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return
	 */
	public int fechasDiferenciaEnAnnos(Date fechaInicial, Date fechaFinal) {
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
				dias = Math.floor(dias/365);
		return ((int) dias);
	}
	
	/**
	 * Recupera los expedientes de refinanciamienti segun filtros de grilla.
	 * @param event
	 */
	public void listarExpedientesRefinanciamiento(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.listarSolicitudPrestamo -----------------------------");
		SolicitudPrestamoFacadeLocal facade = null;
		ExpedienteCreditoComp expedienteCompBusq= null;

		try {
			facade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			cancelarGrabarSolicitud(event);
			limpiarGrillaBusqueda();
			
			
			expedienteCompBusq = new ExpedienteCreditoComp();
			expedienteCompBusq.setIntBusqTipo(intBusqTipo);
			expedienteCompBusq.setStrBusqCadena(strBusqCadena);
			expedienteCompBusq.setStrBusqNroSol(strBusqNroSol);
			expedienteCompBusq.setIntBusqSucursal(intBusqSucursal);
			expedienteCompBusq.setIntBusqEstado(intBusqEstado);
			expedienteCompBusq.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);
			expedienteCompBusq.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);
			expedienteCompBusq.setIntBusqTipoCreditoEmpresa(intBusqTipoCreditoEmpresa);
			
			listaExpedienteCreditoComp = facade.getListaBusqRefinanFiltros(expedienteCompBusq);
			
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Limpia los filtros de busqeda de refinanciamiento
	 * @param event
	 */
	public void limpiarFiltros (ActionEvent event){
	
		try {
			intBusqTipo = 0; 	
			strBusqCadena = "";		    
			strBusqNroSol = "";		   
			intBusqSucursal = 0;			  
			intBusqEstado =0;		    
			dtBusqFechaEstadoDesde = null;  
			dtBusqFechaEstadoHasta = null;
			//private	List<Sucursal> listaSucursal;

		} catch (Exception e) {
			log.error("Error en limpiarFiltros ---> "+e);
		}
		
	}
	
	
	/**
	 * Limpia la grilla de busqueda de refinanciamientos
	 */
	public void limpiarGrillaBusqueda(){

		try {
			if(listaExpedienteCreditoComp != null){
				listaExpedienteCreditoComp.clear();
			}else{
				listaExpedienteCreditoComp = new ArrayList<ExpedienteCreditoComp>();
			}

		} catch (Exception e) {
			log.error("Error en limpiarGrillaBusqueda ---> "+e);
		}
	}
	
	
	
	
	/**
	 * 
	 */
	public void cargarDescripcionAdjuntos(){
		
		try {
				listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_REFINANCIAMIENTO),"A");
		} catch (Exception e) {
			log.error("Error en cargarDescripcionAdjuntos --> "+e);
		}
	}

	
	
	/**
	 * Registra Garante Voluntario a la Solicitud de Prestamo.
	 * 
	 * @param event
	 */
	public void grabarGarante(ActionEvent event) {
		GarantiaCredito garantiaCredito = null;
		GarantiaCreditoComp garantiaCreditoComp = null;
		Estructura estructura = null;
		validGaranteSolidario = true;
		
		if (beanSocioCompGarante != null) {
			msgTxtGarante = "";
			validGaranteSolidario = true;
			
			try {
				
				/*if(beanSocioCompGarante.getPersona().getIntIdPersona().compareTo(beanSocioComp.getPersona().getIntIdPersona())!=0){
					estructura = new Estructura();
					estructura.setId(new EstructuraId());
					garantiaCreditoComp = new GarantiaCreditoComp();
					garantiaCredito = new GarantiaCredito();
					garantiaCredito.setId(new GarantiaCreditoId());
					garantiaCredito.setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_PERSONAL);
					garantiaCredito.setIntItemCreditoGarantia(intItemCreditoGarantia);
					garantiaCredito.setIntPersEmpresaGarantePk(beanSocioCompGarante.getCuenta().getId().getIntPersEmpresaPk());
					garantiaCredito.setIntPersCuentaGarantePk(beanSocioCompGarante.getCuenta().getId().getIntCuenta());
					garantiaCredito.setIntPersPersonaGarantePk(beanSocioCompGarante.getPersona().getIntIdPersona());
					garantiaCredito.setIntIdGaranteSucursalPk(beanSocioCompGarante.getCuenta().getIntIdUsuSucursal());
					garantiaCredito.setIntIdGaranteSubSucursalPk(beanSocioCompGarante.getCuenta().getIntIdUsuSubSucursal());
					garantiaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
					garantiaCredito.setTsFechaRegistro(new Timestamp(new Date().getTime()));
					garantiaCredito.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
					estructura.getId().setIntNivel(beanSocioCompGarante.getSocio().getSocioEstructura().getIntNivel());
					estructura.getId().setIntCodigo(beanSocioCompGarante.getSocio().getSocioEstructura().getIntCodigo());
					estructura = estructuraFacade.getEstructuraPorPk(estructura.getId());
					garantiaCreditoComp.setIntNroGarantizados(intNroPersGarantizadas);
					garantiaCreditoComp.setEstructura(estructura);
					garantiaCreditoComp.setGarantiaCredito(garantiaCredito);
					garantiaCreditoComp.setSocioComp(beanSocioCompGarante);
					listaGarantiaCreditoComp.add(garantiaCreditoComp);
					
					calcularGarantesValidos();
					validarGrabarGarantes();
					cancelarGarante(event);
					
				}else{
					msgTxtGarante= "El Socio NO PUEDE ser Garante.";
				}*/
				if(beanSocioCompGarante != null){
					//CGD-21.01.214-YT
					if(yaExisteGaranteRegistrado(beanSocioCompGarante)){
						if(beanSocioCompGarante.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							if(beanSocioCompGarante.getPersona().getIntIdPersona().compareTo(beanSocioComp.getPersona().getIntIdPersona())!=0){
								estructura = new Estructura();
								estructura.setId(new EstructuraId());
								garantiaCreditoComp = new GarantiaCreditoComp();
								garantiaCredito = new GarantiaCredito();
								garantiaCredito.setId(new GarantiaCreditoId());
								garantiaCredito.setIntParaTipoGarantiaCod(Constante.CREDITOS_GARANTIA_PERSONAL);
								garantiaCredito.setIntItemCreditoGarantia(intItemCreditoGarantia);
								garantiaCredito.setIntPersEmpresaGarantePk(beanSocioCompGarante.getCuenta().getId().getIntPersEmpresaPk());
								garantiaCredito.setIntPersCuentaGarantePk(beanSocioCompGarante.getCuenta().getId().getIntCuenta());
								garantiaCredito.setIntPersPersonaGarantePk(beanSocioCompGarante.getPersona().getIntIdPersona());
								garantiaCredito.setIntIdGaranteSucursalPk(beanSocioCompGarante.getCuenta().getIntIdUsuSucursal());
								garantiaCredito.setIntIdGaranteSubSucursalPk(beanSocioCompGarante.getCuenta().getIntIdUsuSubSucursal());
								garantiaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								garantiaCredito.setTsFechaRegistro(new Timestamp(new Date().getTime()));
								garantiaCredito.setIntPersPersonaUsuarioPk(usuario.getIntPersPersonaPk());
								estructura.getId().setIntNivel(beanSocioCompGarante.getSocio().getSocioEstructura().getIntNivel());
								estructura.getId().setIntCodigo(beanSocioCompGarante.getSocio().getSocioEstructura().getIntCodigo());
								estructura = estructuraFacade.getEstructuraPorPk(estructura.getId());
								garantiaCreditoComp.setIntNroGarantizados(intNroPersGarantizadas);
								garantiaCreditoComp.setEstructura(estructura);
								garantiaCreditoComp.setGarantiaCredito(garantiaCredito);
								garantiaCreditoComp.setSocioComp(beanSocioCompGarante);
								listaGarantiaCreditoComp.add(garantiaCreditoComp);

								calcularGarantesValidos();
								validarGrabarGarantes();
								cancelarGarante(event);
							}else{
								msgTxtGarante= "El Socio NO PUEDE ser Garante de sí mismo.";
							}
						}else{
							msgTxtGarante= "El Socio se encuentra en estado Fallecido.";
						}	
					}else{
						msgTxtGarante= "El Garante ya se encuentra registrado.";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error: " + e);
			}
		} else {
			validGaranteSolidario = false;
			msgTxtGarante = "No se puede agregar una garantía debido a que no existe ningún garante.";
		}
	}
	
	
	
	/**
	 * Valida si se esta agregando un garante ya registrado.
	 * DEvuelve FALSE si ya existe y no procede.
	 * @param garanteComp
	 * @return
	 */
	public Boolean yaExisteGaranteRegistrado(SocioComp garanteComp){
		Boolean blnProcede = Boolean.TRUE;
		try {
			if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
				for (GarantiaCreditoComp garantiaComp : listaGarantiaCreditoComp) {
					if(garantiaComp.getGarantiaCredito().getIntPersPersonaGarantePk().compareTo(garanteComp.getPersona().getIntIdPersona())==0){
						blnProcede = Boolean.FALSE;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en yaExisteGaranteRegistrado ---> "+e);
		}
		return blnProcede;
	}
	/**
	 * Caclula el nro de garantes correctamente registrados
	 */
	public void calcularGarantesValidos() {
		
		try {
		
			if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
				intGarantesCorrectos = 0;
				for (GarantiaCreditoComp garanteCreditoComp : listaGarantiaCreditoComp) {
					if(garanteCreditoComp.getSocioComp().getCreditoTipoGarantiaAsignado() != null){
						intGarantesCorrectos ++;
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error en calcularGarantesValidos --- "+e);
		}
		
		
	}
	
	
	
	
	/**
	 * Asociado al boton del popup de garantes
	 * @param event
	 */
	public void validarGarante(ActionEvent event) {
		SocioComp socioCompGarante = null;
		Integer nroMaxAsegurados = null;
		
		try {
			
			socioCompGarante = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
			strNroDocumento, usuario.getEmpresa().getIntIdEmpresa());
			
			if (socioCompGarante != null && beanCreditoNuevo != null) {
				msgTxtPersonaNoExiste = "";
				if (socioCompGarante.getCuenta() != null) {
					msgTxtCuentaGarante = "";
					if (socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol() != null) {
						msgTxtRolSocio = "";
						
						for (PersonaRol personaRol : socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol()) {
							if (personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
								msgTxtRolSocio = "";
								if (socioCompGarante.getSocio().getListSocioEstructura() != null) {
									msgTxtSocioEstructuraGarante = "";
									forEstructuraGarante: 
										for (SocioEstructura socioEstructura : socioCompGarante.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											msgTxtSocioEstructuraOrigenGarante = "";
											socioCompGarante.getSocio().setSocioEstructura(socioEstructura);
											if (beanCreditoNuevo.getListaGarantiaPersonal() != null
												&& !beanCreditoNuevo.getListaGarantiaPersonal().isEmpty()) {
												for (CreditoGarantia garantiaPersonal : beanCreditoNuevo.getListaGarantiaPersonal()) {
													if (garantiaPersonal.getId().getIntParaTipoGarantiaCod().equals(Constante.CREDITOS_GARANTIA_PERSONAL)) {
														msgTxtTipoGarantiaPersonal = ""; 
														intItemCreditoGarantia = garantiaPersonal.getId().getIntItemCreditoGarantia();
														garantiaPersonal = creditoGarantiaFacade.getCreditoGarantiaPorIdCreditoGarantia(garantiaPersonal.getId());
														
														if (garantiaPersonal != null) {
															msgTxtTipoGarantiaPersonal = "";
															if (garantiaPersonal.getListaTipoGarantiaComp() != null && !garantiaPersonal.getListaTipoGarantiaComp().isEmpty()) {
																for (CreditoTipoGarantiaComp creditoTipoGarantia : garantiaPersonal	.getListaTipoGarantiaComp()) {
																	
																	// Validamos la preexistencia del credito tiopi garantia en los garantes ya registrados.
																	Boolean blnYaSeEncuentraRegistrado = Boolean.TRUE;
																	if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()) {
																		blnYaSeEncuentraRegistrado = validarPreExistencia(creditoTipoGarantia.getCreditoTipoGarantia());	
																	}
																	
																	if(blnYaSeEncuentraRegistrado){
																		msgTxtYaExisteGaranteCOnfiguracionIgual = "";
																		if (creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionSocio() != null) {
																			msgTxtCondicionSocioNinguno = "";
																			for (CondicionSocioTipoGarantia condicionSocio : creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionSocio()) {
																				if (socioCompGarante.getCuenta().getIntParaCondicionCuentaCod().equals(
																					condicionSocio.getId().getIntParaCondicionSocioCod())&& condicionSocio.getIntValor() == 1) {
																					msgTxtCondicionSocio = "";
																					if (creditoTipoGarantia.getCreditoTipoGarantia().getListaTipoCondicion() != null) {
																						msgTxtCondicionHabil = "";
																						for (CondicionHabilTipoGarantia condicionHabil : creditoTipoGarantia.getCreditoTipoGarantia().getListaTipoCondicion()) {
																							if ((socioCompGarante.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(condicionHabil.getId().getIntParaTipoHabilCod())==0)
																								&& condicionHabil.getIntValor() == 1) {
																								msgTxtCondicionHabil = "";
																								if (creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionLaboral() != null) {
																									msgTxtCondicionLaboralGaranteNinguno = "";
																									for (CondicionLaboralTipoGarantia condicionLaboral : creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionLaboral()) {
																										if (socioCompGarante.getPersona().getNatural().getPerLaboral() != null) {
																											msgTxtCondicionLaboralGarante = "";
																											if ((socioCompGarante.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(
																												condicionLaboral.getId().getIntParaCondicionLaboralCod())
																												||socioCompGarante.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(-1))
																												&& condicionLaboral.getIntValor() == 1) {
																												msgTxtCondicionLaboralGarante = "";

																												if (creditoTipoGarantia.getCreditoTipoGarantia().getListaSituacionLaboral() != null) {
																													msgTxtSituacionLaboralGaranteNinguno = "";
																													List<SituacionLaboralTipoGarantia> situacionLaboral2 = creditoTipoGarantia.getCreditoTipoGarantia().getListaSituacionLaboral();
																													
																													for (SituacionLaboralTipoGarantia situacionLaboral : creditoTipoGarantia.getCreditoTipoGarantia().getListaSituacionLaboral()) {
																														if (socioEstructura.getIntTipoSocio().equals(situacionLaboral.getId().getIntParaSituacionLaboralCod())
																															&& situacionLaboral.getIntValor() == 1) {
																															msgTxtSituacionLaboralGarante = "";
																															Boolean blnPasoNroMaximo = Boolean.FALSE;
																															
																															// Se valida Nro de asegurados...
																															nroMaxAsegurados = solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGarante.getPersona().getIntIdPersona());
																															if (nroMaxAsegurados != null) {
																																intNroPersGarantizadas = nroMaxAsegurados;
																																if (creditoTipoGarantia.getCreditoTipoGarantia().getIntNumeroMaximoGarantia() != null) {
																																	if (nroMaxAsegurados.compareTo(creditoTipoGarantia.getCreditoTipoGarantia().getIntNumeroMaximoGarantia()) <= 0) {
																																		msgTxtMaxNroGarantes = "";
																																		blnPasoNroMaximo = Boolean.TRUE;
																																	} else {
																																		msgTxtMaxNroGarantes = "La Persona elegida ya tiene aseguradas a "
																																				+ nroMaxAsegurados
																																				+ " personas. No se puede asegurar a otra.";
																																	}
																																} else {
																																	msgTxtMaxNroGarantes = "";
																																	blnPasoNroMaximo = Boolean.TRUE;
																																}
																															}else{
																																blnPasoNroMaximo = Boolean.TRUE;
																															}
																															
																															
																															if(blnPasoNroMaximo){
																																msgTxtMaxNroGarantes = "";
																																
																																// Validamos DESCUENTO JUDICIAL.
																																Boolean blnDescuento = Boolean.TRUE;
																																if(creditoTipoGarantia.getCreditoTipoGarantia().getIntParaTipoDsctoJudicialCod() != null){
																																	msgTxtDescuentoJudicial = "";
																																	Boolean blnAplicaDesceuntoJudicial = Boolean.FALSE;
																																	if(creditoTipoGarantia.getCreditoTipoGarantia().getIntParaTipoDsctoJudicialCod().compareTo(Constante.PARAM_T_DESCUENTO_JUDICIAL_GARANTE_SIN_DESCUENTO)==0){															
																																		blnDescuento = validarDescuentoJudicial(socioCompGarante);
																																		if(blnDescuento){
																																			socioCompGarante.setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																			socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia.getCreditoTipoGarantia());
																																			
																																			//setIntItemCreditoGarantia(creditoTipoGarantia.getCreditoTipoGarantia().getId().getIntItemCreditoGarantia() | fghjm );
																																			beanSocioCompGarante = socioCompGarante;
																																			blnDatosGarante = true;
																																			blnValidaDatosGarante = true;
																																			break forEstructuraGarante;

																																		}else{
																																			msgTxtDescuentoJudicial = " El Garante posee Descuento Judicial. ";
																																			msgTxtMaxNroGarantes = msgTxtMaxNroGarantes + " El Garante posee Descuento Judicial. ";
																																		}
																																	}else{
																																		socioCompGarante.setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																		socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia.getCreditoTipoGarantia());
																																		beanSocioCompGarante = socioCompGarante;
																																		blnDatosGarante = true;
																																		blnValidaDatosGarante = true;
																																		break forEstructuraGarante;
																																	}
																																}else{
																																	msgTxtDescuentoJudicial = "";
																																	socioCompGarante.setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																	socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia.getCreditoTipoGarantia());
																																	beanSocioCompGarante = socioCompGarante;
																																	blnDatosGarante = true;
																																	blnValidaDatosGarante = true;
																																	break forEstructuraGarante;
																																}
																															}else{
																																msgTxtMaxNroGarantes = "El Garante ya ha realizado el nro maximo de garantias.";
																															}
																														} else {
																															msgTxtSituacionLaboralGarante = "Ninguna Situación Laboral coincide establecida en el crédito coincide con el de la persona.";
																														}
																													}
																												} else {
																													msgTxtSituacionLaboralGaranteNinguno = "La persona elegida no tiene asociada una Condición Laboral.";
																												}
																											} else {
																												msgTxtCondicionLaboralGarante = "Ninguna Condición Laboral coincide con la condición establecida en la garantía del crédito.";
																											}
																											
																										} else {
																											msgTxtCondicionLaboralGarante = "La Persona no tiene ninguna Condición Laboral.";
																										}
																									}
																									
																								} else {
																									msgTxtCondicionLaboralGaranteNinguno = "No se ha configurado una Condición Laboral en esta garantía.";
																								}
																							} else {
																								msgTxtCondicionHabil = "Ninguna Condición Hábil coincide con la condición establecida en la cuenta.";
																							}
																						}
																						
																					} else {
																						msgTxtCondicionHabil = "No se ha configurado una Condición Hábil en esta garantía.";
																					}
																				} else {
																					msgTxtCondicionSocio = "Ninguna Condición de Socio coincide con la condición establecida en la cuenta.";
																				}
																			}
																		} else {
																			msgTxtCondicionSocioNinguno = "No se ha configurado la Condición de Socio en esta garantía.";
																		}
																	//}	
																	}else {
																		msgTxtYaExisteGaranteCOnfiguracionIgual = "Ya existe un Garante registrado con caracteristicas similares.";
																		//msgTxtCondicionSocioNinguno = "No se ha configurado la Condición de Socio en esta garantía.";
																	}
																}
															}
														}else{
															msgTxtTipoGarantiaPersonal = "El crédito no tiene configurado un tipo de Garantía Personal.";
														}
														
													} else {
														msgTxtTipoGarantiaPersonal = "El crédito no tiene configurado un tipo de Garantía Personal.";
													}
												}
											}
											
										} else {
											msgTxtSocioEstructuraOrigenGarante = "La Estructura del Socio no tiene un tipo Origen predefinido.";
										}
									}
								} else {
									msgTxtSocioEstructuraGarante = "La persona elegida no tiene una Estructura.";
								}
								// 14.09.2013
								//break;
							} else {
								msgTxtRolSocio = "El Garante solicitado no tiene un Rol Socio";
							}
						}
					} else {
						msgTxtRolSocio = "La persona no posee ningún Rol de Usuario.";
					}
					
				} else {
					msgTxtCuentaGarante = "La Persona no tiene una Cuenta creada.";
				}
				
			} else {
				msgTxtPersonaNoExiste = "El DNI ingresado no existe en la BD.";
				blnDatosGarante = false;
			}
		} catch (Exception e) {
			log.error("Error en validarGarante ---> "+e);
		}
	}
	
	
	/**
	 * Devuelve FALSE si ya existe en la grilla. Por lo tanto no deberia registrase.
	 * @param creditoTipoGarantia
	 * @return
	 */
	private Boolean validarPreExistencia(CreditoTipoGarantia creditoTipoGarantia) {
		Boolean blnExiste = Boolean.TRUE;
		try {
			if(creditoTipoGarantia != null){
				if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
					for (GarantiaCreditoComp garantiaCreditoComp : listaGarantiaCreditoComp) {
						if(garantiaCreditoComp.getSocioComp().getCreditoTipoGarantiaAsignado() != null){
							
							CreditoTipoGarantia credTipGar = new CreditoTipoGarantia();
							credTipGar = garantiaCreditoComp.getSocioComp().getCreditoTipoGarantiaAsignado();
							if(credTipGar.getId().getIntItemCredito().compareTo(creditoTipoGarantia.getId().getIntItemCredito())==0
								&& credTipGar.getId().getIntItemCreditoGarantia().compareTo(creditoTipoGarantia.getId().getIntItemCreditoGarantia())==0
								&& credTipGar.getId().getIntItemGarantiaTipo().compareTo(creditoTipoGarantia.getId().getIntItemGarantiaTipo())==0
								&& credTipGar.getId().getIntParaTipoCreditoCod().compareTo(creditoTipoGarantia.getId().getIntParaTipoCreditoCod())==0
								&& credTipGar.getId().getIntParaTipoGarantiaCod().compareTo(creditoTipoGarantia.getId().getIntParaTipoGarantiaCod())==0
								&& credTipGar.getId().getIntPersEmpresaPk().compareTo(creditoTipoGarantia.getId().getIntPersEmpresaPk())==0){
								
								blnExiste = Boolean.FALSE;
								break;
							}
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			log.error("Error en validarPreExistencia ---* "+e);
		}
		return blnExiste;
	}

	/**
	 * Valida los garantes segun configuraciond e refinanciamiento y marca a los q ya no son aptos
	 * para q sean retirados antes de registrar el refinanciamiento.
	 * @param garanteComp
	 * @return
	 */
	public GarantiaCreditoComp validarGaranteParaRefinanciamiento(GarantiaCreditoComp garanteComp) {
		SocioComp socioCompGarante = null;
		Integer nroGarantizados = new Integer(0);
		//String strResultadoValidacion = "";
		String strResultadoValidacion = "";
		CreditoGarantia garantiaPersonalCurrent = null;
		
		try {
			socioCompGarante = new SocioComp();
			socioCompGarante  = garanteComp.getSocioComp();
			
			if (socioCompGarante != null && beanCreditoNuevo != null) {
				if (socioCompGarante.getCuenta() != null) {
					if (socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol() != null) {
						for (PersonaRol personaRol : socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol()) {
							if (personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
								if (socioCompGarante.getSocio().getListSocioEstructura() != null) {
									
									forEstructuraGarante: 
										for (SocioEstructura socioEstructura : socioCompGarante.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											socioCompGarante.getSocio().setSocioEstructura(socioEstructura);
											if (beanCreditoNuevo.getListaGarantiaPersonal() != null
													&& !beanCreditoNuevo.getListaGarantiaPersonal().isEmpty()) {
												for (CreditoGarantia garantiaPersonal : beanCreditoNuevo.getListaGarantiaPersonal()) {
													if (garantiaPersonal.getId().getIntParaTipoGarantiaCod().equals(Constante.CREDITOS_GARANTIA_PERSONAL)) {
														intItemCreditoGarantia = garantiaPersonal.getId().getIntItemCreditoGarantia();
														garantiaPersonal = creditoGarantiaFacade.getCreditoGarantiaPorIdCreditoGarantia(garantiaPersonal.getId());
														
														if (garantiaPersonal != null) {
															if (garantiaPersonal.getListaTipoGarantiaComp() != null) {
																for (CreditoTipoGarantiaComp creditoTipoGarantia : garantiaPersonal	.getListaTipoGarantiaComp()) {
																	
																	// Validamos la preexistencia del credito tiopi garantia en los garantes ya registrados.
																	Boolean blnYaSeEncuentraRegistrado = Boolean.FALSE;
																	
																	if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()) {
																		blnYaSeEncuentraRegistrado = validarPreExistencia(creditoTipoGarantia.getCreditoTipoGarantia());	
																	}
																	
																	if(blnYaSeEncuentraRegistrado){

																		if (creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionSocio() != null) {

																			for (CondicionSocioTipoGarantia condicionSocio : creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionSocio()) {
																				if (socioCompGarante.getCuenta().getIntParaCondicionCuentaCod().equals(
																						condicionSocio.getId().getIntParaCondicionSocioCod())
																						&& condicionSocio.getIntValor() == 1) {
																					msgTxtCondicionSocio = "";
																					if (creditoTipoGarantia.getCreditoTipoGarantia().getListaTipoCondicion() != null) {
																						/*List<CondicionHabilTipoGarantia> lstTipoCondicion = creditoTipoGarantia.getCreditoTipoGarantia().getListaTipoCondicion();
																						for (int i = 0; i < lstTipoCondicion.size(); i++) {
																							System.out.println("========================================================================");
																							System.out.println("TIPO GARATIA-VALOR deber ser =  a: 1 -->"+lstTipoCondicion.get(i).getIntValor());
																							System.out.println("INT CONDICION CUENTA GARANTE --> "+socioCompGarante.getCuenta().getIntParaSubCondicionCuentaCod());
																							System.out.println("TIPO GARATIA-VALOR ---> "+lstTipoCondicion.get(i).getId().getIntParaTipoHabilCod());
																							System.out.println("========================================================================");
																						}*/
																						
																						for (CondicionHabilTipoGarantia condicionHabil : creditoTipoGarantia.getCreditoTipoGarantia().getListaTipoCondicion()) {
																							if ((socioCompGarante.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(condicionHabil.getId().getIntParaTipoHabilCod())==0)
																									&& condicionHabil.getIntValor() == 1) {
																								msgTxtCondicionHabil = "";
																								if (creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionLaboral() != null) {
																									
																									/*List<CondicionLaboralTipoGarantia> condicionLaboral2 = creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionLaboral();
																									for (int i = 0; i < condicionLaboral2.size(); i++) {
																										System.out.println("========================================================================");
																										System.out.println("condicionLaboral deber ser =  a: 1 -->"+condicionLaboral2.get(i).getIntValor());
																										System.out.println("INT CONDICION LABORAL GARANTE --> "+socioCompGarante.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral());
																										System.out.println("getIntParaCondicionLaboralCod ---> "+condicionLaboral2.get(i).getId().getIntParaCondicionLaboralCod());
																										System.out.println("========================================================================");
																									}*/
																									
																									
																									for (CondicionLaboralTipoGarantia condicionLaboral : creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionLaboral()) {
																										if (socioCompGarante.getPersona().getNatural().getPerLaboral() != null) {																										
																											if ((socioCompGarante.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(
																													condicionLaboral.getId().getIntParaCondicionLaboralCod())
																													||socioCompGarante.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(-1))
																													&& condicionLaboral.getIntValor() == 1) {
																												msgTxtCondicionLaboralGarante = "";
																												// if(socioCompGarante.getSocio().getSocioEstructura()!=null){
																												if (creditoTipoGarantia.getCreditoTipoGarantia().getListaSituacionLaboral() != null) {
																													
																													List<SituacionLaboralTipoGarantia> situacionLaboral2 = creditoTipoGarantia.getCreditoTipoGarantia().getListaSituacionLaboral();
																													for (int i = 0; i < situacionLaboral2.size(); i++) {
																														System.out.println("========================================================================");
																														System.out.println("SituacionLaboral deber ser =  a: 1 -->"+situacionLaboral2.get(i).getIntValor());
																														System.out.println("INT Situacion LABORAL GARANTE - socioEstructura.getIntTipoSocio() --> "+socioEstructura.getIntTipoSocio());
																														System.out.println("getIntParaSituacionLaboralCod ---> "+situacionLaboral2.get(i).getId().getIntParaSituacionLaboralCod());
																														System.out.println("========================================================================");
																													}
																													
																													for (SituacionLaboralTipoGarantia situacionLaboral : creditoTipoGarantia.getCreditoTipoGarantia().getListaSituacionLaboral()) {
																														if (socioEstructura.getIntTipoSocio().equals(situacionLaboral.getId().getIntParaSituacionLaboralCod())
																																&& situacionLaboral.getIntValor() == 1) {
																															msgTxtSituacionLaboralGarante = "";
																															
																															
																															Boolean blnPasoNroMaximo = Boolean.FALSE;
																															Integer nroMaxAsegurados = 0;
																															// Se valida Nro de asegurados...
																															nroMaxAsegurados = solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGarante.getPersona().getIntIdPersona());
																															if (nroMaxAsegurados != null) {
																																intNroPersGarantizadas = nroMaxAsegurados;
																																if (creditoTipoGarantia.getCreditoTipoGarantia().getIntNumeroMaximoGarantia() != null) {
																																	if (nroMaxAsegurados.compareTo(creditoTipoGarantia.getCreditoTipoGarantia().getIntNumeroMaximoGarantia()) <= 0) {
																																		msgTxtMaxNroGarantes = "";
																																		//beanSocioCompGarante = socioCompGarante;
																																		//blnDatosGarante = true;
																																		//blnValidaDatosGarante = true;
																																		blnPasoNroMaximo = Boolean.TRUE;
																																		//break forEstructuraGarante;
																																	} else {
																																		msgTxtMaxNroGarantes = "La Persona elegida ya tiene aseguradas a "
																																				+ nroMaxAsegurados
																																				+ " personas. No se puede asegurar a otra.";
																																	}
																																} else {
																																	//beanSocioCompGarante = socioCompGarante;
																																	//blnDatosGarante = true;
																																	//blnValidaDatosGarante = false;
																																	msgTxtMaxNroGarantes = "";
																																	blnPasoNroMaximo = Boolean.TRUE;
																																	//break forEstructuraGarante;
																																}
																															}else{
																																blnPasoNroMaximo = Boolean.TRUE;
																															}
																															
																															
																															if(blnPasoNroMaximo){
																																// Validamos DESCUENTO JUDICIAL.
																																Boolean blnDescuento = Boolean.TRUE;
																																if(creditoTipoGarantia.getCreditoTipoGarantia().getIntParaTipoDsctoJudicialCod() != null){
																																	Boolean blnAplicaDesceuntoJudicial = Boolean.FALSE;
																																	//	public static final Integer PARAM_T_DESCUENTO_JUDICIAL_GARANTE_CON_DESCUENTO = 1;
																																	//  public static final Integer PARAM_T_DESCUENTO_JUDICIAL_GARANTE_SIN_DESCUENTO = 2;
																																	if(creditoTipoGarantia.getCreditoTipoGarantia().getIntParaTipoDsctoJudicialCod().compareTo(Constante.PARAM_T_DESCUENTO_JUDICIAL_GARANTE_SIN_DESCUENTO)==0){															
																																		blnDescuento = validarDescuentoJudicial(socioCompGarante);
																																		if(blnDescuento){
																																			//setIntItemCreditoGarantia(tipoGarantia.getId().getIntItemCreditoGarantia());
																																			
																																			//garanteComp.getSocioComp().setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																			//garanteComp.getSocioComp().setCreditoTipoGarantiaAsignado(tipoGarantia);
																																			
																																			socioCompGarante.setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																			socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia.getCreditoTipoGarantia());
																																			
																																			//setIntItemCreditoGarantia(creditoTipoGarantia.getCreditoTipoGarantia().getId().getIntItemCreditoGarantia() | fghjm );
																																			beanSocioCompGarante = socioCompGarante;
																																			blnDatosGarante = true;
																																			blnValidaDatosGarante = true;
																																			break forEstructuraGarante;

																																		}else{
																																			msgTxtMaxNroGarantes = msgTxtMaxNroGarantes + " El Garante posee Descuento Judicial. ";
																																		}
																																	}else{
																																		socioCompGarante.setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																		socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia.getCreditoTipoGarantia());
																																		beanSocioCompGarante = socioCompGarante;
																																		blnDatosGarante = true;
																																		blnValidaDatosGarante = true;
																																		break forEstructuraGarante;
																																	}
																																}else{
																																	socioCompGarante.setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																	socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia.getCreditoTipoGarantia());
																																	beanSocioCompGarante = socioCompGarante;
																																	blnDatosGarante = true;
																																	blnValidaDatosGarante = true;
																																	break forEstructuraGarante;
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
						}
					} 
				}
			}
			
			
			if(socioCompGarante.getCreditoTipoGarantiaAsignado()== null){
				garanteComp.setSocioComp(socioCompGarante);
				garanteComp.getSocioComp().setStrDescValidaRefinan("* El garante presenta observaciones.");				
			}else{
				garanteComp.getSocioComp().setStrDescValidaRefinan("");
			}
			
			
			
		} catch (Exception e) {
			log.error("Error en validarGaranteParaRefinanciamiento --->"+e);
			e.printStackTrace();
		}
		return garanteComp;
	}

	/**
	 * Si devuelve FALSE es que si posee desceunto
	 * @param socioCompGarante
	 * @return
	 */
	private Boolean validarDescuentoJudicial(SocioComp socioCompGarante) {
		Boolean blnPoseeDesc = Boolean.TRUE;
		List<SocioEstructura> listaSocioEstructura = null;
		List<SocioEstructura> listaSocioEstructuraFiltrada = null;
		String strDocumento = "";
		try {
				strDocumento = socioCompGarante.getPersona().getDocumento().getStrNumeroIdentidad();

			if(socioCompGarante.getSocio().getListSocioEstructura() != null
			   && !socioCompGarante.getSocio().getListSocioEstructura().isEmpty()){
				listaSocioEstructura = new ArrayList<SocioEstructura>();
				listaSocioEstructura = socioCompGarante.getSocio().getListSocioEstructura();
				
				if(listaSocioEstructura != null && !listaSocioEstructura.isEmpty()){
					listaSocioEstructuraFiltrada = new ArrayList<SocioEstructura>();
					for (SocioEstructura socioEstructuraB : listaSocioEstructura) {
						if(socioEstructuraB.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							listaSocioEstructuraFiltrada.add(socioEstructuraB);
						}
					}
					
				}
				
				if(listaSocioEstructuraFiltrada != null && !listaSocioEstructuraFiltrada.isEmpty()){
					for (SocioEstructura socioEstructura : listaSocioEstructuraFiltrada) {
						AdminPadron adminPadron = new AdminPadron();
						adminPadron.setId(new AdminPadronId());
						AdminPadron adminPadronresult = new AdminPadron();;
						
						if(socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							adminPadron.getId().setIntParaModalidadCod(socioEstructura.getIntModalidad());
							adminPadron.getId().setIntParaTipoSocioCod(socioEstructura.getIntTipoSocio());
							adminPadron.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							adminPadronresult = estructuraFacade.getMaximoAdminPadronPorAdminPadron(adminPadron);
							System.out.println("WWTF???-----> "+adminPadronresult.getId().getIntItemAdministraPadron());
							
							if(adminPadronresult.getId() != null){
								DescuentoId descuentoId = new DescuentoId();
								descuentoId.setIntCodigo(adminPadronresult.getId().getIntCodigo());
								descuentoId.setIntItemAdministraPadron(adminPadronresult.getId().getIntItemAdministraPadron());
								//descuentoId.setIntItemDescuento(adminPadronresult.getId());
								descuentoId.setIntMes(adminPadronresult.getId().getIntMes());
								descuentoId.setIntNivel(adminPadronresult.getId().getIntNivel());
								descuentoId.setIntParaModalidadCod(adminPadronresult.getId().getIntParaModalidadCod());
								descuentoId.setIntParaTipoArchivoPadronCod(adminPadronresult.getId().getIntParaTipoArchivoPadronCod());
								descuentoId.setIntParaTipoSocio(adminPadronresult.getId().getIntParaTipoSocioCod());
								descuentoId.setIntPeriodo(adminPadronresult.getId().getIntPeriodo());
								
								List<Descuento> lstDescuentos = null;
								lstDescuentos = estructuraFacade.getListaPorAdminPadron(adminPadronresult,strDocumento);
								if(lstDescuentos != null && ! lstDescuentos.isEmpty()){
									blnPoseeDesc = Boolean.FALSE;
									break;
								}
							}
						}
					}
				}	
			}
		} catch (Exception e) {
			log.error("Error en  validarDescuentoJudicial ---> "+e);	
		}
		return blnPoseeDesc;
	}

	
	/**
	 * @param event
	 */
	public void cancelarGarante(ActionEvent event) {
		blnValidaDatosGarante = true;
		// blnValidaDatosGarante = false;
		strNroDocumento = "";
		beanSocioCompGarante = new SocioComp();
		beanSocioCompGarante.setPersona(new Persona());
		msgTxtPersonaNoExiste = "";
		msgTxtRolSocio = "";
		msgTxtTipoGarantiaPersonal = "";
		msgTxtCondicionSocio = "";
		msgTxtCondicionSocioNinguno = "";
		msgTxtSubCondicionSocio ="";
		msgTxtSubCondicionSocioNinguno = "";
		msgTxtCondicionLaboralGarante = "";
		msgTxtCondicionLaboralGaranteNinguno = "";
		msgTxtSituacionLaboralGarante ="";
		msgTxtSituacionLaboralGaranteNinguno = "";
		msgTxtMaximaGarantia = "";
		msgTxtSocioEstructuraGarante = "";
		msgTxtSocioEstructuraOrigenGarante = "";
		msgTxtCuentaGarante = "";
		msgTxtMaxNroGarantes = "";
		msgTxtYaExisteGaranteCOnfiguracionIgual = "";
		msgTxtDescuentoJudicial = "";
	}
	
	
	/**
	 * Remueve un registro de la grilla de Garantes Solidarios
	 * @param event
	 */
	public void removeGaranteSolidario(ActionEvent event) {
		log.info("--------------------SolicitudPrestamoController.removeGarante--------------------");
		String rowKey = getRequestParameter("rowKeyGaranteSolidario");
		GarantiaCreditoComp garantiaCreditoCompTmp = null;
		try {
			if (listaGarantiaCreditoComp != null) {
				for (int i = 0; i < listaGarantiaCreditoComp.size(); i++) {
					if (Integer.parseInt(rowKey) == i) {
						GarantiaCreditoComp garantiaCreditoComp = listaGarantiaCreditoComp.get(i);
						if (garantiaCreditoComp.getGarantiaCredito().getId() != null) {
							garantiaCreditoComp = listaGarantiaCreditoComp.get(i);
							garantiaCreditoComp.getGarantiaCredito().setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						}
						listaGarantiaCreditoComp.remove(i);
						break;
					}
				}
				if (garantiaCreditoCompTmp != null) {
					listaGarantiaCreditoComp.add(garantiaCreditoCompTmp);
				}
			}
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	
	/**
	 * Muestra las personas que observaron la solicitud de credito previamente.
	 * @return
	 */
	public boolean mostrarlistaAutorizacionesPrevias(List<EstadoCredito> listaEstados) {
		boolean isValidEncaragadoAutorizar = true;
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
			// buscar todos sus estados y ver si aen alguno existe un observado
			
			if(listaEstados!= null && !listaEstados.isEmpty()){
				Boolean blnContinua = Boolean.FALSE;
				
				for (EstadoCredito estados : listaEstados) {
					if(estados.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
						blnContinua = Boolean.TRUE;
						break;
					}
				}
				
				/*
				PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO = 1;
				PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_PRESTAMO = 2;
				PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO = 3;
				 
				 */
				if(blnContinua){
					listaAutorizaCredito = solicitudPrestamoFacade.getListaAutorizaCreditoPorPkExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId());
					if (listaAutorizaCredito != null && listaAutorizaCredito.size() > 0) {
						for (AutorizaCredito autorizaCredito : listaAutorizaCredito) {
							if(autorizaCredito.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_PRESTAMO)==0
							   || autorizaCredito.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO)==0 ){
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
								listaAutorizaCreditoComp.add(autorizaCreditoComp);
							}
						}
					}	
				}
			}
		} catch (BusinessException e) {
			log.error("Error en "+e);
			e.printStackTrace();
		}

		return isValidEncaragadoAutorizar;
	}
	
	/**
	 * 
	 * @param event
	 */
	public void irDetalleRefinanciamientoLink(ActionEvent event){
		List<Expediente> lstExpedientesMov = null;
		List<ExpedienteComp> lstExpedientesCompMov = null;
		ExpedienteComp expedienteComp = null;
		try {
			listaDetalleRefinanciamientoLink = solicitudPrestamoFacade.recuperarDetallesCreditoRefinanciado(registroSeleccionadoBusqueda);
		} catch (Exception e) {
		}
	}
	
	
	/**
	 * 
	 */
	public void imprimirReporteConBD(){
		String strNombreReporte = "rptServicio_Persona";
		HashMap parameters = new HashMap();
		try {
			parameters.put("valorTabla", registroSeleccionadoImpresion.getDtFechaInicio());
			Reporter reporte = new Reporter();
			reporte.executeReport(strNombreReporte, parameters);

		} catch (Exception e) {
			log.error("Error en imprimirReporteConBD ---> "+e);
		}		 
	}
	
	/**
	 * 
	 */
	public void imprimirReporteConJavaBean(){
		String strNombreReporte = "";
		try {
			strNombreReporte = "test_Refinanciamientos";
			Reporter reporte = new Reporter();
			reporte.executeReport(strNombreReporte, null);

		} catch (Exception e) {
			log.error("Error en imprimirReporteConJavaBean ---> "+e);
		}		 
	}
//------------------------------- GETERS Y SETERS ---------------------------------------->
	
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
		SolicitudRefinanController.log = log;
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

	public String getStrSolicitudRefinan() {
		return strSolicitudRefinan;
	}

	public void setStrSolicitudRefinan(String strSolicitudRefinan) {
		this.strSolicitudRefinan = strSolicitudRefinan;
	}

	public Boolean getBlnShowValidarDatos() {
		return blnShowValidarDatos;
	}

	public void setBlnShowValidarDatos(Boolean blnShowValidarDatos) {
		this.blnShowValidarDatos = blnShowValidarDatos;
	}

	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}

	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}

	public Boolean getBlnShowDivFormSolicitudRefinan() {
		return blnShowDivFormSolicitudRefinan;
	}

	public void setBlnShowDivFormSolicitudRefinan(
			Boolean blnShowDivFormSolicitudRefinan) {
		this.blnShowDivFormSolicitudRefinan = blnShowDivFormSolicitudRefinan;
	}

	public List<ExpedienteCreditoComp> getListaExpedienteCreditoComp() {
		return listaExpedienteCreditoComp;
	}

	public void setListaExpedienteCreditoComp(
			List<ExpedienteCreditoComp> listaExpedienteCreditoComp) {
		this.listaExpedienteCreditoComp = listaExpedienteCreditoComp;
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

	public Integer getIntSubTipoOperacion() {
		return intSubTipoOperacion;
	}

	public void setIntSubTipoOperacion(Integer intSubTipoOperacion) {
		this.intSubTipoOperacion = intSubTipoOperacion;
	}

	public Persona getPersonaValida() {
		return personaValida;
	}

	public void setPersonaValida(Persona personaValida) {
		this.personaValida = personaValida;
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

	public EstructuraFacadeRemote getEstructuraFacade() {
		return estructuraFacade;
	}

	public void setEstructuraFacade(EstructuraFacadeRemote estructuraFacade) {
		this.estructuraFacade = estructuraFacade;
	}

	public Estructura getBeanEstructuraSocioComp() {
		return beanEstructuraSocioComp;
	}

	public void setBeanEstructuraSocioComp(Estructura beanEstructuraSocioComp) {
		this.beanEstructuraSocioComp = beanEstructuraSocioComp;
	}

	public ExpedienteCredito getBeanExpedienteCredito() {
		return beanExpedienteCredito;
	}

	public void setBeanExpedienteCredito(ExpedienteCredito beanExpedienteCredito) {
		this.beanExpedienteCredito = beanExpedienteCredito;
	}

	public ExpedienteCreditoComp getBeanExpedienteCreditoComp() {
		return beanExpedienteCreditoComp;
	}

	public void setBeanExpedienteCreditoComp(
			ExpedienteCreditoComp beanExpedienteCreditoComp) {
		this.beanExpedienteCreditoComp = beanExpedienteCreditoComp;
	}

	public String getStrMsgErrorValidarDatos() {
		return strMsgErrorValidarDatos;
	}

	public void setStrMsgErrorValidarDatos(String strMsgErrorValidarDatos) {
		this.strMsgErrorValidarDatos = strMsgErrorValidarDatos;
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

	public List<Tabla> getListDocumentoBusq() {
		return listDocumentoBusq;
	}

	public void setListDocumentoBusq(List<Tabla> listDocumentoBusq) {
		this.listDocumentoBusq = listDocumentoBusq;
	}

	public List<CapacidadCreditoComp> getListaCapacidadCreditoComp() {
		return listaCapacidadCreditoComp;
	}

	public void setListaCapacidadCreditoComp(
			List<CapacidadCreditoComp> listaCapacidadCreditoComp) {
		this.listaCapacidadCreditoComp = listaCapacidadCreditoComp;
	}
	
	public String getStrSubTipoOperacion() {
		return strSubTipoOperacion;
	}

	public void setStrSubTipoOperacion(String strSubTipoOperacion) {
		this.strSubTipoOperacion = strSubTipoOperacion;
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

	public CreditoFacadeRemote getCreditoFacade() {
		return creditoFacade;
	}

	public void setCreditoFacade(CreditoFacadeRemote creditoFacade) {
		this.creditoFacade = creditoFacade;
	}

	public ConceptoFacadeRemote getConceptofacade() {
		return conceptofacade;
	}

	public void setConceptofacade(ConceptoFacadeRemote conceptofacade) {
		this.conceptofacade = conceptofacade;
	}

	public List<Tabla> getListaDescTipoCreditoEmpresa() {
		return listaDescTipoCreditoEmpresa;
	}

	public void setListaDescTipoCreditoEmpresa(
			List<Tabla> listaDescTipoCreditoEmpresa) {
		this.listaDescTipoCreditoEmpresa = listaDescTipoCreditoEmpresa;
	}

	public List<ExpedienteComp> getListExpedienteMovimientoComp() {
		return listExpedienteMovimientoComp;
	}

	public void setListExpedienteMovimientoComp(
			List<ExpedienteComp> listExpedienteMovimientoComp) {
		this.listExpedienteMovimientoComp = listExpedienteMovimientoComp;
	}

	public ExpedienteComp getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(ExpedienteComp registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}

	public ConvenioFacadeRemote getConvenioFacade() {
		return convenioFacade;
	}

	public void setConvenioFacade(ConvenioFacadeRemote convenioFacade) {
		this.convenioFacade = convenioFacade;
	}

	public String getStrMsgErrorPreEvaluacion() {
		return strMsgErrorPreEvaluacion;
	}

	public void setStrMsgErrorPreEvaluacion(String strMsgErrorPreEvaluacion) {
		this.strMsgErrorPreEvaluacion = strMsgErrorPreEvaluacion;
	}


	/*public ExpedienteComp getBeanExpedienteMovCompSeleccionado() {
		return beanExpedienteMovCompSeleccionado;
	}

	public void setBeanExpedienteMovCompSeleccionado(
			ExpedienteComp beanExpedienteMovCompSeleccionado) {
		this.beanExpedienteMovCompSeleccionado = beanExpedienteMovCompSeleccionado;
	}*/

	public Integer getIntMiOperacion() {
		return intMiOperacion;
	}

	public void setIntMiOperacion(Integer intMiOperacion) {
		this.intMiOperacion = intMiOperacion;
	}

	public String getStrMsgCondicionTiempoCeseTrabajador() {
		return strMsgCondicionTiempoCeseTrabajador;
	}

	public void setStrMsgCondicionTiempoCeseTrabajador(
			String strMsgCondicionTiempoCeseTrabajador) {
		this.strMsgCondicionTiempoCeseTrabajador = strMsgCondicionTiempoCeseTrabajador;
	}

	public String getStrMsgCondicionPlazoMaximoPrestamo() {
		return strMsgCondicionPlazoMaximoPrestamo;
	}

	public void setStrMsgCondicionPlazoMaximoPrestamo(
			String strMsgCondicionPlazoMaximoPrestamo) {
		this.strMsgCondicionPlazoMaximoPrestamo = strMsgCondicionPlazoMaximoPrestamo;
	}

	public String getStrMsgCondicionCancelarPorPlanilla() {
		return strMsgCondicionCancelarPorPlanilla;
	}

	public void setStrMsgCondicionCancelarPorPlanilla(
			String strMsgCondicionCancelarPorPlanilla) {
		this.strMsgCondicionCancelarPorPlanilla = strMsgCondicionCancelarPorPlanilla;
	}

	public boolean isBlnPostEvaluacion() {
		return blnPostEvaluacion;
	}

	public void setBlnPostEvaluacion(boolean blnPostEvaluacion) {
		this.blnPostEvaluacion = blnPostEvaluacion;
	}

	public List<Tabla> getListaMotivoPrestamo() {
		return listaMotivoPrestamo;
	}

	public void setListaMotivoPrestamo(List<Tabla> listaMotivoPrestamo) {
		this.listaMotivoPrestamo = listaMotivoPrestamo;
	}

	public Integer getIntMotivoRefinanciamiento() {
		return intMotivoRefinanciamiento;
	}

	public void setIntMotivoRefinanciamiento(Integer intMotivoRefinanciamiento) {
		this.intMotivoRefinanciamiento = intMotivoRefinanciamiento;
	}

	public List<GarantiaCreditoComp> getListaGarantiaCreditoComp() {
		return listaGarantiaCreditoComp;
	}

	public void setListaGarantiaCreditoComp(
			List<GarantiaCreditoComp> listaGarantiaCreditoComp) {
		this.listaGarantiaCreditoComp = listaGarantiaCreditoComp;
	}

	public List<RequisitoCreditoComp> getListaRequisitoCreditoComp() {
		return listaRequisitoCreditoComp;
	}

	public void setListaRequisitoCreditoComp(
			List<RequisitoCreditoComp> listaRequisitoCreditoComp) {
		this.listaRequisitoCreditoComp = listaRequisitoCreditoComp;
	}

	public SolicitudPrestamoFacadeRemote getSolicitudPrestamoFacade() {
		return solicitudPrestamoFacade;
	}

	public void setSolicitudPrestamoFacade(
			SolicitudPrestamoFacadeRemote solicitudPrestamoFacade) {
		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
	}

	public List<CronogramaCreditoComp> getListaCronogramaCreditoComp() {
		return listaCronogramaCreditoComp;
	}

	public void setListaCronogramaCreditoComp(
			List<CronogramaCreditoComp> listaCronogramaCreditoComp) {
		this.listaCronogramaCreditoComp = listaCronogramaCreditoComp;
	}
	public List<Sucursal> getListSucursal() {
		try {
			if (listSucursal == null) {
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listSucursal = facade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listSucursal;
	}
	
	
	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}

	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}

	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}

	public ExpedienteCreditoComp getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}

	public void setRegistroSeleccionadoBusqueda(
			ExpedienteCreditoComp registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}

	public String getStrMsgErrorExisteRefinanciamiento() {
		return strMsgErrorExisteRefinanciamiento;
	}

	public void setStrMsgErrorExisteRefinanciamiento(
			String strMsgErrorExisteRefinanciamiento) {
		this.strMsgErrorExisteRefinanciamiento = strMsgErrorExisteRefinanciamiento;
	}

	public ExpedienteCredito getBeanExpedienteCreditoAnterior() {
		return beanExpedienteCreditoAnterior;
	}

	public void setBeanExpedienteCreditoAnterior(
			ExpedienteCredito beanExpedienteCreditoAnterior) {
		this.beanExpedienteCreditoAnterior = beanExpedienteCreditoAnterior;
	}

	public List<Tabla> getListaTablaDescripcionAdjuntos() {
		return listaTablaDescripcionAdjuntos;
	}

	public void setListaTablaDescripcionAdjuntos(
			List<Tabla> listaTablaDescripcionAdjuntos) {
		this.listaTablaDescripcionAdjuntos = listaTablaDescripcionAdjuntos;
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

	public BigDecimal getBdMontoTotalSolicitado() {
		return bdMontoTotalSolicitado;
	}

	public void setBdMontoTotalSolicitado(BigDecimal bdMontoTotalSolicitado) {
		this.bdMontoTotalSolicitado = bdMontoTotalSolicitado;
	}

	public BigDecimal getBdTotalDstos() {
		return bdTotalDstos;
	}

	public void setBdTotalDstos(BigDecimal bdTotalDstos) {
		this.bdTotalDstos = bdTotalDstos;
	}

	public BigDecimal getBdMontoPrestamo() {
		return bdMontoPrestamo;
	}

	public void setBdMontoPrestamo(BigDecimal bdMontoPrestamo) {
		this.bdMontoPrestamo = bdMontoPrestamo;
	}

	public BigDecimal getBdAportes() {
		return bdAportes;
	}

	public void setBdAportes(BigDecimal bdAportes) {
		this.bdAportes = bdAportes;
	}

	public BigDecimal getBdPorcAporteMontoSolic() {
		return bdPorcAporteMontoSolic;
	}

	public void setBdPorcAporteMontoSolic(BigDecimal bdPorcAporteMontoSolic) {
		this.bdPorcAporteMontoSolic = bdPorcAporteMontoSolic;
	}

	public BigDecimal getBdCuotaMensual() {
		return bdCuotaMensual;
	}

	public void setBdCuotaMensual(BigDecimal bdCuotaMensual) {
		this.bdCuotaMensual = bdCuotaMensual;
	}

	public BigDecimal getBdTotalCuotaMensual() {
		return bdTotalCuotaMensual;
	}

	public void setBdTotalCuotaMensual(BigDecimal bdTotalCuotaMensual) {
		this.bdTotalCuotaMensual = bdTotalCuotaMensual;
	}

	public BigDecimal getBdCapacidadPago() {
		return bdCapacidadPago;
	}

	public void setBdCapacidadPago(BigDecimal bdCapacidadPago) {
		this.bdCapacidadPago = bdCapacidadPago;
	}

	public BigDecimal getBdMaxCapacidadPago() {
		return bdMaxCapacidadPago;
	}

	public void setBdMaxCapacidadPago(BigDecimal bdMaxCapacidadPago) {
		this.bdMaxCapacidadPago = bdMaxCapacidadPago;
	}

	public Integer getIntNroCuotas() {
		return intNroCuotas;
	}

	public void setIntNroCuotas(Integer intNroCuotas) {
		this.intNroCuotas = intNroCuotas;
	}

	public Integer getIntCuotasMaximas() {
		return intCuotasMaximas;
	}

	public void setIntCuotasMaximas(Integer intCuotasMaximas) {
		this.intCuotasMaximas = intCuotasMaximas;
	}

	public String getStrPorcInteres() {
		return strPorcInteres;
	}

	public void setStrPorcInteres(String strPorcInteres) {
		this.strPorcInteres = strPorcInteres;
	}

	public String getStrPorcAportes() {
		return strPorcAportes;
	}

	public void setStrPorcAportes(String strPorcAportes) {
		this.strPorcAportes = strPorcAportes;
	}

	public BigDecimal getBdPorcSeguroDesgravamen() {
		return bdPorcSeguroDesgravamen;
	}

	public void setBdPorcSeguroDesgravamen(BigDecimal bdPorcSeguroDesgravamen) {
		this.bdPorcSeguroDesgravamen = bdPorcSeguroDesgravamen;
	}

	public List<CreditoExcepcion> getListaExcepciones() {
		return listaExcepciones;
	}

	public void setListaExcepciones(List<CreditoExcepcion> listaExcepciones) {
		this.listaExcepciones = listaExcepciones;
	}

	public String getStrPorcentajeInteres() {
		return strPorcentajeInteres;
	}

	public void setStrPorcentajeInteres(String strPorcentajeInteres) {
		this.strPorcentajeInteres = strPorcentajeInteres;
	}

	/*public String getStrInteresAtrasado() {
		return strInteresAtrasado;
	}

	public void setStrInteresAtrasado(String strInteresAtrasado) {
		this.strInteresAtrasado = strInteresAtrasado;
	}

	public String getStrMoraAtrasado() {
		return strMoraAtrasado;
	}

	public void setStrMoraAtrasado(String strMoraAtrasado) {
		this.strMoraAtrasado = strMoraAtrasado;
	}
*/
	public String getStrNroCuotas() {
		return strNroCuotas;
	}

	public void setStrNroCuotas(String strNroCuotas) {
		this.strNroCuotas = strNroCuotas;
	}

	public Integer getIntNroCuotasCalculado() {
		return intNroCuotasCalculado;
	}

	public void setIntNroCuotasCalculado(Integer intNroCuotasCalculado) {
		this.intNroCuotasCalculado = intNroCuotasCalculado;
	}

	public boolean isBlnMarca() {
		return blnMarca;
	}

	public void setBlnMarca(boolean blnMarca) {
		this.blnMarca = blnMarca;
	}

	public String getMsgTxtCuotaMensual() {
		return msgTxtCuotaMensual;
	}

	public void setMsgTxtCuotaMensual(String msgTxtCuotaMensual) {
		this.msgTxtCuotaMensual = msgTxtCuotaMensual;
	}

	public Credito getBeanCreditoAnterior() {
		return beanCreditoAnterior;
	}

	public void setBeanCreditoAnterior(Credito beanCreditoAnterior) {
		this.beanCreditoAnterior = beanCreditoAnterior;
	}

	public Credito getBeanCreditoNuevo() {
		return beanCreditoNuevo;
	}

	public void setBeanCreditoNuevo(Credito beanCreditoNuevo) {
		this.beanCreditoNuevo = beanCreditoNuevo;
	}

	public CreditoInteres getCreditoInteres() {
		return creditoInteres;
	}

	public void setCreditoInteres(CreditoInteres creditoInteres) {
		this.creditoInteres = creditoInteres;
	}
/*
	public String getStrMontoTotalRefinanciamiento() {
		return strMontoTotalRefinanciamiento;
	}

	public void setStrMontoTotalRefinanciamiento(
			String strMontoTotalRefinanciamiento) {
		this.strMontoTotalRefinanciamiento = strMontoTotalRefinanciamiento;
	}
*/
	public SocioComp getBeanSocioCompGarante() {
		return beanSocioCompGarante;
	}

	public void setBeanSocioCompGarante(SocioComp beanSocioCompGarante) {
		this.beanSocioCompGarante = beanSocioCompGarante;
	}

	public String getStrNroDocumento() {
		return strNroDocumento;
	}

	public void setStrNroDocumento(String strNroDocumento) {
		this.strNroDocumento = strNroDocumento;
	}

	public Boolean getBlnDatosGarante() {
		return blnDatosGarante;
	}

	public void setBlnDatosGarante(Boolean blnDatosGarante) {
		this.blnDatosGarante = blnDatosGarante;
	}

	public Boolean getBlnValidaDatosGarante() {
		return blnValidaDatosGarante;
	}

	public void setBlnValidaDatosGarante(Boolean blnValidaDatosGarante) {
		this.blnValidaDatosGarante = blnValidaDatosGarante;
	}

	public Integer getIntItemCreditoGarantia() {
		return intItemCreditoGarantia;
	}

	public void setIntItemCreditoGarantia(Integer intItemCreditoGarantia) {
		this.intItemCreditoGarantia = intItemCreditoGarantia;
	}

	public Boolean getValidGaranteSolidario() {
		return validGaranteSolidario;
	}

	public void setValidGaranteSolidario(Boolean validGaranteSolidario) {
		this.validGaranteSolidario = validGaranteSolidario;
	}

	public Integer getIntNroPersGarantizadas() {
		return intNroPersGarantizadas;
	}

	public void setIntNroPersGarantizadas(Integer intNroPersGarantizadas) {
		this.intNroPersGarantizadas = intNroPersGarantizadas;
	}

	public String getMsgTxtPersonaNoExiste() {
		return msgTxtPersonaNoExiste;
	}

	public void setMsgTxtPersonaNoExiste(String msgTxtPersonaNoExiste) {
		this.msgTxtPersonaNoExiste = msgTxtPersonaNoExiste;
	}

	public String getMsgTxtRolSocio() {
		return msgTxtRolSocio;
	}

	public void setMsgTxtRolSocio(String msgTxtRolSocio) {
		this.msgTxtRolSocio = msgTxtRolSocio;
	}

	public String getMsgTxtTipoGarantiaPersonal() {
		return msgTxtTipoGarantiaPersonal;
	}

	public void setMsgTxtTipoGarantiaPersonal(String msgTxtTipoGarantiaPersonal) {
		this.msgTxtTipoGarantiaPersonal = msgTxtTipoGarantiaPersonal;
	}

	public String getMsgTxtCondicionSocio() {
		return msgTxtCondicionSocio;
	}

	public void setMsgTxtCondicionSocio(String msgTxtCondicionSocio) {
		this.msgTxtCondicionSocio = msgTxtCondicionSocio;
	}

	public String getMsgTxtCondicionSocioNinguno() {
		return msgTxtCondicionSocioNinguno;
	}

	public void setMsgTxtCondicionSocioNinguno(String msgTxtCondicionSocioNinguno) {
		this.msgTxtCondicionSocioNinguno = msgTxtCondicionSocioNinguno;
	}

	public String getMsgTxtSubCondicionSocio() {
		return msgTxtSubCondicionSocio;
	}

	public void setMsgTxtSubCondicionSocio(String msgTxtSubCondicionSocio) {
		this.msgTxtSubCondicionSocio = msgTxtSubCondicionSocio;
	}

	public String getMsgTxtSubCondicionSocioNinguno() {
		return msgTxtSubCondicionSocioNinguno;
	}

	public void setMsgTxtSubCondicionSocioNinguno(
			String msgTxtSubCondicionSocioNinguno) {
		this.msgTxtSubCondicionSocioNinguno = msgTxtSubCondicionSocioNinguno;
	}

	public String getMsgTxtCondicionLaboralGarante() {
		return msgTxtCondicionLaboralGarante;
	}

	public void setMsgTxtCondicionLaboralGarante(
			String msgTxtCondicionLaboralGarante) {
		this.msgTxtCondicionLaboralGarante = msgTxtCondicionLaboralGarante;
	}

	public String getMsgTxtCondicionLaboralGaranteNinguno() {
		return msgTxtCondicionLaboralGaranteNinguno;
	}

	public void setMsgTxtCondicionLaboralGaranteNinguno(
			String msgTxtCondicionLaboralGaranteNinguno) {
		this.msgTxtCondicionLaboralGaranteNinguno = msgTxtCondicionLaboralGaranteNinguno;
	}

	public String getMsgTxtSituacionLaboralGarante() {
		return msgTxtSituacionLaboralGarante;
	}

	public void setMsgTxtSituacionLaboralGarante(
			String msgTxtSituacionLaboralGarante) {
		this.msgTxtSituacionLaboralGarante = msgTxtSituacionLaboralGarante;
	}

	public String getMsgTxtSituacionLaboralGaranteNinguno() {
		return msgTxtSituacionLaboralGaranteNinguno;
	}

	public void setMsgTxtSituacionLaboralGaranteNinguno(
			String msgTxtSituacionLaboralGaranteNinguno) {
		this.msgTxtSituacionLaboralGaranteNinguno = msgTxtSituacionLaboralGaranteNinguno;
	}

	public String getMsgTxtMaximaGarantia() {
		return msgTxtMaximaGarantia;
	}

	public void setMsgTxtMaximaGarantia(String msgTxtMaximaGarantia) {
		this.msgTxtMaximaGarantia = msgTxtMaximaGarantia;
	}

	public String getMsgTxtSocioEstructuraGarante() {
		return msgTxtSocioEstructuraGarante;
	}

	public void setMsgTxtSocioEstructuraGarante(String msgTxtSocioEstructuraGarante) {
		this.msgTxtSocioEstructuraGarante = msgTxtSocioEstructuraGarante;
	}

	public String getMsgTxtSocioEstructuraOrigenGarante() {
		return msgTxtSocioEstructuraOrigenGarante;
	}

	public void setMsgTxtSocioEstructuraOrigenGarante(
			String msgTxtSocioEstructuraOrigenGarante) {
		this.msgTxtSocioEstructuraOrigenGarante = msgTxtSocioEstructuraOrigenGarante;
	}

	public String getMsgTxtCuentaGarante() {
		return msgTxtCuentaGarante;
	}

	public void setMsgTxtCuentaGarante(String msgTxtCuentaGarante) {
		this.msgTxtCuentaGarante = msgTxtCuentaGarante;
	}

	public String getMsgTxtMaxNroGarantes() {
		return msgTxtMaxNroGarantes;
	}

	public void setMsgTxtMaxNroGarantes(String msgTxtMaxNroGarantes) {
		this.msgTxtMaxNroGarantes = msgTxtMaxNroGarantes;
	}

	public String getMsgTxtGarante() {
		return msgTxtGarante;
	}

	public void setMsgTxtGarante(String msgTxtGarante) {
		this.msgTxtGarante = msgTxtGarante;
	}

	public CreditoGarantiaFacadeRemote getCreditoGarantiaFacade() {
		return creditoGarantiaFacade;
	}

	public void setCreditoGarantiaFacade(
			CreditoGarantiaFacadeRemote creditoGarantiaFacade) {
		this.creditoGarantiaFacade = creditoGarantiaFacade;
	}

	public String getMsgTxtCondicionHabil() {
		return msgTxtCondicionHabil;
	}

	public void setMsgTxtCondicionHabil(String msgTxtCondicionHabil) {
		this.msgTxtCondicionHabil = msgTxtCondicionHabil;
	}

	public String getMsgTxtEstructuraRepetida() {
		return msgTxtEstructuraRepetida;
	}

	public void setMsgTxtEstructuraRepetida(String msgTxtEstructuraRepetida) {
		this.msgTxtEstructuraRepetida = msgTxtEstructuraRepetida;
	}

	public String getMsgTxtEstrucActivoRepetido() {
		return msgTxtEstrucActivoRepetido;
	}

	public void setMsgTxtEstrucActivoRepetido(String msgTxtEstrucActivoRepetido) {
		this.msgTxtEstrucActivoRepetido = msgTxtEstrucActivoRepetido;
	}

	public String getMsgTxtEstrucCesanteRepetido() {
		return msgTxtEstrucCesanteRepetido;
	}

	public void setMsgTxtEstrucCesanteRepetido(String msgTxtEstrucCesanteRepetido) {
		this.msgTxtEstrucCesanteRepetido = msgTxtEstrucCesanteRepetido;
	}

	public String getMsgTxtObservacion() {
		return msgTxtObservacion;
	}

	public void setMsgTxtObservacion(String msgTxtObservacion) {
		this.msgTxtObservacion = msgTxtObservacion;
	}

	public String getMsgTxtListaCapacidadPago() {
		return msgTxtListaCapacidadPago;
	}

	public void setMsgTxtListaCapacidadPago(String msgTxtListaCapacidadPago) {
		this.msgTxtListaCapacidadPago = msgTxtListaCapacidadPago;
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

	/*public String getStrMontoDelPrestamo() {
		return strMontoDelPrestamo;
	}

	public void setStrMontoDelPrestamo(String strMontoDelPrestamo) {
		this.strMontoDelPrestamo = strMontoDelPrestamo;
	}*/

	public List<CuentaConcepto> getListaCuentaConcepto() {
		return listaCuentaConcepto;
	}

	public void setListaCuentaConcepto(List<CuentaConcepto> listaCuentaConcepto) {
		this.listaCuentaConcepto = listaCuentaConcepto;
	}

	public CuentaConcepto getCtaCtoAporte() {
		return ctaCtoAporte;
	}

	public void setCtaCtoAporte(CuentaConcepto ctaCtoAporte) {
		this.ctaCtoAporte = ctaCtoAporte;
	}

	public CuentaConcepto getCtaCtoRetiro() {
		return ctaCtoRetiro;
	}

	public void setCtaCtoRetiro(CuentaConcepto ctaCtoRetiro) {
		this.ctaCtoRetiro = ctaCtoRetiro;
	}

	public CuentaConcepto getCtaCtoSepelio() {
		return ctaCtoSepelio;
	}

	public void setCtaCtoSepelio(CuentaConcepto ctaCtoSepelio) {
		this.ctaCtoSepelio = ctaCtoSepelio;
	}

	public String getStrMsgMasDeUnoSeleecionado() {
		return strMsgMasDeUnoSeleecionado;
	}

	public void setStrMsgMasDeUnoSeleecionado(String strMsgMasDeUnoSeleecionado) {
		this.strMsgMasDeUnoSeleecionado = strMsgMasDeUnoSeleecionado;
	}

	public ExpedienteComp getBeanExpedienteMovARefinanciar() {
		return beanExpedienteMovARefinanciar;
	}

	public void setBeanExpedienteMovARefinanciar(
			ExpedienteComp beanExpedienteMovARefinanciar) {
		this.beanExpedienteMovARefinanciar = beanExpedienteMovARefinanciar;
	}

	public String getStrMontoDelPrestamoRefinanciado() {
		return strMontoDelPrestamoRefinanciado;
	}

	public void setStrMontoDelPrestamoRefinanciado(
			String strMontoDelPrestamoRefinanciado) {
		this.strMontoDelPrestamoRefinanciado = strMontoDelPrestamoRefinanciado;
	}

	public String getStrInteresAtrasadoDelPrestamoRefinanciado() {
		return strInteresAtrasadoDelPrestamoRefinanciado;
	}

	public void setStrInteresAtrasadoDelPrestamoRefinanciado(
			String strInteresAtrasadoDelPrestamoRefinanciado) {
		this.strInteresAtrasadoDelPrestamoRefinanciado = strInteresAtrasadoDelPrestamoRefinanciado;
	}

	public String getStrMoraAtrasadoDelPrestamoRefinanciado() {
		return strMoraAtrasadoDelPrestamoRefinanciado;
	}

	public void setStrMoraAtrasadoDelPrestamoRefinanciado(
			String strMoraAtrasadoDelPrestamoRefinanciado) {
		this.strMoraAtrasadoDelPrestamoRefinanciado = strMoraAtrasadoDelPrestamoRefinanciado;
	}

	public String getStrMontoTotalDelPrestamoRefinanciado() {
		return strMontoTotalDelPrestamoRefinanciado;
	}

	public void setStrMontoTotalDelPrestamoRefinanciado(
			String strMontoTotalDelPrestamoRefinanciado) {
		this.strMontoTotalDelPrestamoRefinanciado = strMontoTotalDelPrestamoRefinanciado;
	}

	public BigDecimal getBdPorcentajeInteres() {
		return bdPorcentajeInteres;
	}

	public void setBdPorcentajeInteres(BigDecimal bdPorcentajeInteres) {
		this.bdPorcentajeInteres = bdPorcentajeInteres;
	}

	public Boolean getBlnBloquearcheck() {
		return blnBloquearcheck;
	}

	public void setBlnBloquearcheck(Boolean blnBloquearcheck) {
		this.blnBloquearcheck = blnBloquearcheck;
	}

	public String getStrMsgErrorConfiguracionRefinan() {
		return strMsgErrorConfiguracionRefinan;
	}

	public void setStrMsgErrorConfiguracionRefinan(
			String strMsgErrorConfiguracionRefinan) {
		this.strMsgErrorConfiguracionRefinan = strMsgErrorConfiguracionRefinan;
	}

	public Boolean getBlnChckCondicionEdadSocio() {
		return blnChckCondicionEdadSocio;
	}

	public void setBlnChckCondicionEdadSocio(Boolean blnChckCondicionEdadSocio) {
		this.blnChckCondicionEdadSocio = blnChckCondicionEdadSocio;
	}

	public CreditoExcepcionFacadeRemote getCreditoExcepcionFacade() {
		return creditoExcepcionFacade;
	}

	public void setCreditoExcepcionFacade(
			CreditoExcepcionFacadeRemote creditoExcepcionFacade) {
		this.creditoExcepcionFacade = creditoExcepcionFacade;
	}

	public CreditoGarantia getGarantiasPersonales() {
		return garantiasPersonales;
	}

	public void setGarantiasPersonales(CreditoGarantia garantiasPersonales) {
		this.garantiasPersonales = garantiasPersonales;
	}

	public String getStrMensajeGarantesObservacion() {
		return strMensajeGarantesObservacion;
	}

	public void setStrMensajeGarantesObservacion(
			String strMensajeGarantesObservacion) {
		this.strMensajeGarantesObservacion = strMensajeGarantesObservacion;
	}

	public String getStrMensajeEdadSocio() {
		return strMensajeEdadSocio;
	}

	public void setStrMensajeEdadSocio(String strMensajeEdadSocio) {
		this.strMensajeEdadSocio = strMensajeEdadSocio;
	}

	public Integer getIntGarantesCorrectos() {
		return intGarantesCorrectos;
	}

	public void setIntGarantesCorrectos(Integer intGarantesCorrectos) {
		this.intGarantesCorrectos = intGarantesCorrectos;
	}

	public String getMsgTxtYaExisteGaranteCOnfiguracionIgual() {
		return msgTxtYaExisteGaranteCOnfiguracionIgual;
	}

	public void setMsgTxtYaExisteGaranteCOnfiguracionIgual(
			String msgTxtYaExisteGaranteCOnfiguracionIgual) {
		this.msgTxtYaExisteGaranteCOnfiguracionIgual = msgTxtYaExisteGaranteCOnfiguracionIgual;
	}

	public String getMsgTxtDescuentoJudicial() {
		return msgTxtDescuentoJudicial;
	}

	public void setMsgTxtDescuentoJudicial(String msgTxtDescuentoJudicial) {
		this.msgTxtDescuentoJudicial = msgTxtDescuentoJudicial;
	}

	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}

	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}

	public List<AutorizaCreditoComp> getListaAutorizaCreditoComp() {
		return listaAutorizaCreditoComp;
	}

	public void setListaAutorizaCreditoComp(
			List<AutorizaCreditoComp> listaAutorizaCreditoComp) {
		this.listaAutorizaCreditoComp = listaAutorizaCreditoComp;
	}

	public Boolean getBlnMonstrarEliminar() {
		return blnMonstrarEliminar;
	}

	public void setBlnMonstrarEliminar(Boolean blnMonstrarEliminar) {
		this.blnMonstrarEliminar = blnMonstrarEliminar;
	}

	public Boolean getBlnMonstrarActualizar() {
		return blnMonstrarActualizar;
	}

	public void setBlnMonstrarActualizar(Boolean blnMonstrarActualizar) {
		this.blnMonstrarActualizar = blnMonstrarActualizar;
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

	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}

	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public List<ExpedienteCreditoComp> getListaDetalleRefinanciamiento() {
		return listaDetalleRefinanciamiento;
	}

	public void setListaDetalleRefinanciamiento(
			List<ExpedienteCreditoComp> listaDetalleRefinanciamiento) {
		this.listaDetalleRefinanciamiento = listaDetalleRefinanciamiento;
	}

	public List<ExpedienteComp> getListaDetalleRefinanciamientoLink() {
		return listaDetalleRefinanciamientoLink;
	}

	public void setListaDetalleRefinanciamientoLink(
			List<ExpedienteComp> listaDetalleRefinanciamientoLink) {
		this.listaDetalleRefinanciamientoLink = listaDetalleRefinanciamientoLink;
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



	public ExpedienteCreditoComp getRegistroSeleccionadoImpresion() {
		return registroSeleccionadoImpresion;
	}



	public void setRegistroSeleccionadoImpresion(
			ExpedienteCreditoComp registroSeleccionadoImpresion) {
		this.registroSeleccionadoImpresion = registroSeleccionadoImpresion;
	}
	
	
}
