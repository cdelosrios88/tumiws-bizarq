package pe.com.tumi.servicio.liquidacion.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.MyFile;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.CuentaConceptoComp;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
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
import pe.com.tumi.servicio.liquidacion.service.SolicitudLiquidacionService;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.composite.AutorizaLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
import pe.com.tumi.servicio.prevision.facade.AutorizacionLiquidacionFacadeLocal;
import pe.com.tumi.servicio.prevision.facade.LiquidacionFacadeLocal;
import pe.com.tumi.servicio.prevision.facade.LiquidacionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeLocal;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;

public class SolicitudLiquidacionController {
	protected static Logger log = Logger.getLogger(SolicitudLiquidacionController.class);
	
	// facades
	private PersonaFacadeRemote 					personaFacade;
	private TablaFacadeRemote 						tablaFacade;
	private EmpresaFacadeRemote 					empresaFacade;
	private LiquidacionFacadeLocal 					liquidacionFacade;
	private SocioFacadeRemote 						socioFacade;
	private EstructuraFacadeRemote 					estructuraFacade;
	private GeneralFacadeRemote 					generalFacade;
	private ConceptoFacadeRemote 					conceptoFacade;
	private CuentaFacadeRemote 						cuentaFacade; 
	private CreditoFacadeRemote 					creditoFacade;
	private SolicitudPrestamoFacadeLocal 			solicitudPrestamoFacade;
	private PrevisionFacadeLocal 					previsionFacade;
	private SolicitudLiquidacionService 			solicitudLiquidacionService;
	private AutorizacionLiquidacionFacadeLocal		autorizaLiquidacionFacade;
	private PermisoFacadeRemote 					permisoFacade;
	
	// sesion
	private Usuario 	usuario;
	private	Integer		SESION_IDEMPRESA;
	private	Integer		SESION_IDUSUARIO;
	private Integer		SESION_IDSUCURSAL;
	private Integer		SESION_IDSUBSUCURSAL;
	private Integer		SESION_IDPERFIL;
	
	// tablas en controlador
	private List<Tabla> 	listaSubOperacion;
	private List<Tabla> 	listaDescCondicionSocio;
	private List<Tabla> 	listaDescTipoCondicionSocio;
	private List<Tabla> 	listaDescripcionTipoCuenta;
	private List<Tabla> 	listaDescripcionCuentaConcepto;	
	private List<Tabla> 	listaDescTipoCredito;
	private List<Tabla> 	listaDescTipoCreditoEmpresa;
	private List<Tabla> 	lstMsgCondicionSinGarantesDeudores;
	private List<Tabla> 	listaDescripcionTipoSocio;
	private List<Tabla> 	listaDescripcionModalidad;
	
	// tablas en formulario
	private List<Tabla> 	listaTipoRelacion;
	private List<Tabla> 	listaMotivoRenuncia;
	private List<Tabla>		listDocumentoBusq;
	private List<Tabla>		listaTablaTipoDocumento;
	private List<Tabla>		listaTablaSucursal;
	
//	private List<Tabla> 	listaTipoVinculo;	
//	private List<Tabla> 	listaTablaDeSucursal;
//	private List<Tabla> 	listaTablaCreditoEmpresa;	
//	private	List<Tabla>		listaTablaEstadoPago;
//	private List<Tabla>		listaTipoBusquedaSucursal;
//	private List<Tabla> 	listaTablaTipoRenuncia;
//	private List<Tabla> 	listaTablaTipoSolicitud;
	
	private List<Tabla> lstReporteVacia;
	// busqueda
	private Integer		intTipoPersonaFiltro;
	private Integer		intTipoBusquedaPersonaFiltro;
	private String		strTextoPersonaFiltro;
	private Integer		intItemExpedienteFiltro;
	private Integer		intTipoCreditoFiltro;
	
	private Integer		intTipoBusquedaFechaFiltro;
	
	private EstadoLiquidacion		estadoCondicionFiltro;
	private Integer		intTipoBusquedaSucursal;
	
	private Integer		intIdSucursalFiltro;
	
	private List<ExpedienteLiquidacionComp> listaExpedienteLiquidacion;
	private Integer		intIdSubsucursalFiltro;
	private ExpedienteLiquidacion registroSeleccionadoBusqueda;
	private ExpedienteLiquidacionComp registroSeleccionadoBusquedaComp;
	
	// MENSAJES
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private String 	mensajeOperacion;
	
	private String strMsgTxtSubOperacion;
	private String strMsgTxtTieneFallecidos;
	private String strMsgTxtFechaRenuncia;
	private String strMsgTxtFechaRecepcion;
	private String strMsgTxtFechaProgramacion;
	private String strMsgTxtMontoBrutoLiquidacion;
	private String strMsgTxttPeriodoUltimoDescuento;
	private String strMsgTxtParaMotivoRenuncia;
	private String strMsgTxtTieneBeneficiarios;
	private String StrMsgTxtGrabar;
	private String StrMsgTxtSumaPorcentaje;
	private boolean blnSumaPorcentajes;
	private boolean blnTieneAdjuntosConfigurados;
	private String StrMsgTxtObservacion;
	



	// CONDICIONES - VALIDACIONES
	private Boolean blnCondicionSocioSinDeudaPendiente;
	private Boolean blnCondicionSinGarantesDeudores;
	private Boolean blnCondicionBeneficioFondoSepelio;
	
	private String strMsgCondicionSocioSinDeudaPendiente;
	private String strMsgCondicionSinGarantesDeudores;
	private String strMsgCondicionBeneficioFondoSepelio;
	
	private Boolean chkCondicionSocioSinDeudaPendiente;
	private Boolean chkCondicionSinGarantesDeudores;
	private Boolean chkCondicionBeneficioFondoSepelio;

	private String strSolicitudLiquidacion;
	private Boolean blnShowValidarDatos;
	private Date dtFechaRegistro;
	private Boolean blnShowDivFormSolicitudLiquidacion;

	// validar datos
	private SocioComp beanSocioComp;
	private Persona personaValida;
	private Integer intTipoRelacion;
	private Estructura beanEstructuraSocioComp;
	private String strSubsucursalSocio;
	private List<Estructura> listEstructura;
	private String strUnidadesEjecutorasConcatenadas;
	private List<Subsucursal> listaSubsucursalSocio;
	private Boolean pgValidDatos;
	private Boolean blnDatosSocio;
	private String strMsgErrorValidarDatos;
	
	
//	private Date dtHoy = null;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	
	private ExpedienteLiquidacion beanExpedienteLiquidacion;

	private boolean blnPostEvaluacion;
	private List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp;
	private List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionBusq;
	private List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionVista;
	
	private List<ExpedienteComp> listExpedienteMovimientoComp;
	
	private List<CuentaComp> listaCuentaSocio;
	/**
	 * Lista con las ctas conceptos del socio recuperardas en validar datos.
	 */
	private List<CuentaConcepto> listaCuentaConcepto;
	/**
	 * Lista con las ccuentas concepto del socio, se le agrega la descripcion de las ctas cto - validar datos.
	 */
	private List<CuentaConceptoComp> listaCuentaConceptoComp;
	/**
	 * Por defecto es del tipo LIQUIDACION ( 1 )
	 */
	private Integer intTipoOperacion;
	/**
	 * Es el subtipo de operacion
	 */
	private Integer intSubTipoOperacion;
	private String campoBuscarBeneficiario;
	private BeneficiarioLiquidacion beneficiarioSeleccionado;
	private boolean blnIsRenuncia;
	private boolean blnIsFallecimiento;
	private String strMsgObservacion;
	private List<Tabla> lstDeudaPendiente;

	private String strFechaRenuncia;
	private String strFechaRecepcionRenuncia;
	private String strFechaProgramacionPago;
	
	
	
	// Grilla de busqueda
	// 16.05.2013 - cgd
	private List<ExpedienteLiquidacionComp> listaExpedienteLiquidacionComp;
	private Integer intTipoConsultaBusqueda; // Nombres DNI RUC Razón Social
	private String strConsultaBusqueda;
	private EstadoLiquidacion estadoLiquidacionBusqueda;
	private Persona personaBusqueda;
	private EstructuraDetalle estructuraDetalleBusqueda;
	
	
	private String strNumeroSolicitudBusq;
	private Boolean blnTxtBusqueda;
	private Boolean blnBusquedaFechas;
	private Boolean blnBusquedaCondicion;
//	private List<Tabla> listaTipoConsultaBusqueda;
	private List<Subsucursal>		listaSubsucursal;
	private List<Subsucursal>		listaSubsucursalBusq;
	private EstadoLiquidacion estadoLiquidacionFechas;
	private EstadoLiquidacion estadoLiquidacionSuc;
	private List<Sucursal> listSucursal;
		
	private String strDescripcionTipoLiquidacion;
	private Boolean blnMostrarDescripcionTipoLiquidacion;
	private String strMsgTxtAsterisco;
	
	
	private String strMsgTxtCondiciones;

	
	private List<BeneficiarioLiquidacion> listaBeneficiariosVista;
	private BeneficiarioLiquidacion beneficiarioTotales;
	private CuentaConceptoComp beanCuentaConceptoAportes;
	private CuentaConceptoComp beanCuentaConceptoRetiro;
	private String strMsgTxtValidacionbeneficiarios;
	private Boolean blnMostrarObjeto;
	
	private Boolean blnMonstrarEliminar;
	private Boolean blnMonstrarActualizar;
	
	private String  strMsgTxtProcedeEvaluacion;
	private String  strMsgTxtProcedeEvaluacion1;
	private Boolean blnSol;
	private Boolean blnAut;
	private Boolean blnArch;
	private Boolean blnGir;
	
	private Boolean blnBloquearXCuenta;
	private String strMensajeValidacionCuenta;
	
	
	// CGD -24.102.2013
	
	private Integer intBusquedaTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;
	private Integer intBusqEstado;	
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private Integer intBusqTipoLiquidacion;
	
	private List<Sucursal> listaSucursal;
	private List<Sucursal> listaSucursalBusqueda;
	
	private Boolean blnVerSolExpLiquidacion;
	
	private BigDecimal bdMontoInteresFdoRetiro;
	
	private Boolean blnViewSolicitudLiquidacion;
	
	private List<AutorizaLiquidacionComp> 	listaAutorizaLiquidacionComp;
	private BigDecimal bdMondoFondoRetiroTotal;
	
	
	//
	private Boolean blnCorrespondeLiquidacion;
	
	public SolicitudLiquidacionController() {	
		log = Logger.getLogger(this.getClass());		
		cargarUsuario();
		if(usuario != null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}		
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuario.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuario.getEmpresa().getIntIdEmpresa();
		SESION_IDPERFIL = usuario.getPerfil().getId().getIntIdPerfil();
		SESION_IDSUCURSAL = usuario.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuario.getSubSucursal().getId().getIntIdSubSucursal();		
	}
	
	public void cargarPermisos() throws Exception{
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;

		Integer MENU_SOLICITUD = 232;
		Integer MENU_AUTORIZACION = 233;
		Integer MENU_GIRO = 234;
		Integer MENU_ARCHIVAMIENTO = 235;

		try{
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(SESION_IDEMPRESA);
				id.setIntIdPerfil(SESION_IDPERFIL);
				
				//Solicitud de Liquidación.
				id.setIntIdTransaccion(MENU_SOLICITUD);				
				permiso = permisoFacade.getPermisoPerfilPorPk(id);
				blnSol = (permiso == null);
				
				//Autorización de Liquidación.
				id.setIntIdTransaccion(MENU_AUTORIZACION);
				permiso = permisoFacade.getPermisoPerfilPorPk(id);
				blnAut = (permiso == null);
				
				//Giro de Liquidación.
				id.setIntIdTransaccion(MENU_GIRO);
				permiso = permisoFacade.getPermisoPerfilPorPk(id);
				blnGir = (permiso == null);
				
				//Archivamiento de Liquidación.
				id.setIntIdTransaccion(MENU_ARCHIVAMIENTO);
				permiso = permisoFacade.getPermisoPerfilPorPk(id);
				blnArch = (permiso == null);
			}else{
				blnSol = false;
				blnAut= false;
				blnGir= false;
				blnArch= false;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}	
	}
	
	public String getInicioPage() {
		cargarUsuario();		
		if(usuario!=null){
			limpiarFiltros();
			limpiarFormSolicitudLiquidacion();
			limpiarBeneficiarios();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}		
		return "";
	}
	
	private void cargarValoresIniciales(){
		try{
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			liquidacionFacade = (LiquidacionFacadeLocal) EJBFactory.getLocal(LiquidacionFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			previsionFacade = (PrevisionFacadeLocal)EJBFactory.getLocal(PrevisionFacadeLocal.class);
			solicitudLiquidacionService = (SolicitudLiquidacionService)TumiFactory.get(SolicitudLiquidacionService.class);
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			autorizaLiquidacionFacade = (AutorizacionLiquidacionFacadeLocal)EJBFactory.getLocal(AutorizacionLiquidacionFacadeLocal.class);
			
			cargarPermisos();
			
			listaSubOperacion = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_TIPOS_LIQUIDACION);
			listaDescCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_CONDICIONSOCIO));
			listaDescTipoCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPO_CONDSOCIO));
			listaDescripcionTipoCuenta = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTAREQUISITOS));
			listaDescripcionCuentaConcepto = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCUENTA));
			listaDescTipoCredito = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CREDITO));
			listaDescTipoCreditoEmpresa = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA));
			listaDescripcionTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			listaDescripcionModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
			listaTipoRelacion =tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "D");
			listaMotivoRenuncia =tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_MOTIVO_DE_RENUNCIA); 
			listaTablaTipoDocumento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSUBOPERACION));

			listSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			
//			ordenarAlfabeticamenteSuc();
			lstReporteVacia = new ArrayList<Tabla>();
			lstMsgCondicionSinGarantesDeudores = new ArrayList<Tabla>();
			listDocumentoBusq = new ArrayList<Tabla>();
			listaTablaSucursal = new ArrayList<Tabla>();
			cargarListaTablaSucursal();
			lstDeudaPendiente = new ArrayList<Tabla>();
			
			listaCuentaSocio = new ArrayList<CuentaComp>();
			listaCuentaConcepto=new ArrayList<CuentaConcepto>();
			listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
			listaBeneficiarioLiquidacionBusq = new ArrayList<BeneficiarioLiquidacion>();
			listaExpedienteLiquidacion =  new ArrayList<ExpedienteLiquidacionComp>();
			listExpedienteMovimientoComp =new ArrayList<ExpedienteComp>();
			listaExpedienteLiquidacionComp= new ArrayList<ExpedienteLiquidacionComp>();
			listaBeneficiarioLiquidacionVista = new ArrayList<BeneficiarioLiquidacion>();
			
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
			
			beanExpedienteLiquidacion = new ExpedienteLiquidacion();
			beanExpedienteLiquidacion.setId(new ExpedienteLiquidacionId());
			beanExpedienteLiquidacion.setListaEstadoLiquidacion(new ArrayList<EstadoLiquidacion>());
			beanExpedienteLiquidacion.setListaExpedienteLiquidacionDetalle(new ArrayList<ExpedienteLiquidacionDetalle>());
			
			estadoCondicionFiltro = new EstadoLiquidacion();
			estadoCondicionFiltro.getId().setIntPersEmpresaPk(SESION_IDEMPRESA);
			
			estadoLiquidacionSuc = new EstadoLiquidacion();
			estadoLiquidacionBusqueda = new EstadoLiquidacion();
			estadoLiquidacionFechas = new EstadoLiquidacion();
			estadoLiquidacionFechas.getId().setIntPersEmpresaPk(SESION_IDEMPRESA);
			
			personaValida = new Persona();
			personaValida.setDocumento(new Documento());
			
			estructuraDetalleBusqueda = new EstructuraDetalle(); 
			
			intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA;
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			intTipoConsultaBusqueda = 0;
			
			strNumeroSolicitudBusq = "";			
			strConsultaBusqueda = "";
			strDescripcionTipoLiquidacion = "";
			strUnidadesEjecutorasConcatenadas = "";
			strSolicitudLiquidacion = "";
			
			bdMontoInteresFdoRetiro = BigDecimal.ZERO;
			
			blnShowDivFormSolicitudLiquidacion = false;
			blnShowValidarDatos = false ;
			blnIsRenuncia = false;
			blnTxtBusqueda = true;
			blnBusquedaFechas = false;
			blnBusquedaCondicion = false;
			blnMostrarDescripcionTipoLiquidacion = true;
			blnMostrarObjeto = true;
			blnMonstrarEliminar= true;
			blnMonstrarActualizar= true;
			blnVerSolExpLiquidacion = false;

			limpiarFiltros();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	

//	public List<Sucursal> ordenarAlfabeticamenteSuc(){
//		if(listaSucursal != null && !listaSucursal.isEmpty()){
//			//Ordenamos por nombre
//			Collections.sort(listaSucursal, new Comparator<Sucursal>(){
//				public int compare(Sucursal uno, Sucursal otro) {
//					return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
//				}
//			});	
//		}
//		return listaSucursal;
//	}
	


	
	/**
	 * Limpia los filtros de la busqueda
	 */
	public void limpiarFiltros(){
		intBusquedaTipo = 0;
		strBusqCadena = "";
		strBusqNroSol ="";
		intBusqSucursal = 0;
		intBusqTipoLiquidacion = 0;
		intBusqEstado =0;
		dtBusqFechaEstadoDesde = null;
		dtBusqFechaEstadoHasta = null;
	}
	
	/**
	 * Coloca en cero la linea de totales de la grilla beneficiarios
	 */
	public void limpiarBeneficiarioTotales(){
		beneficiarioTotales = new BeneficiarioLiquidacion();
		beneficiarioTotales.setBdPorcentajeBeneficioApo(BigDecimal.ZERO);
		beneficiarioTotales.setBdMontoAporte(BigDecimal.ZERO);
		beneficiarioTotales.setBdPorcentajeBeneficioRet(BigDecimal.ZERO);
		beneficiarioTotales.setBdMontoRetiro(BigDecimal.ZERO);
		beneficiarioTotales.setBdMontoTotal(BigDecimal.ZERO);
	}
	
	
	/**
	 * Calcula los montos a mostrarse en la secciones de totales de la grilla de beneficiarios.
	 */
	public void calcularBeneficiarioTotales(){
		BigDecimal bdPorcentajeTotalAportes= BigDecimal.ZERO;
		BigDecimal bdMontoTotalAportes= BigDecimal.ZERO;
		BigDecimal bdPorcentajeTotalRetiro= BigDecimal.ZERO;
		BigDecimal bdMontoTotalRetiro= BigDecimal.ZERO;
		BigDecimal bdMontoTotalBeneficiarios= BigDecimal.ZERO;
		
		try {
			for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
				if(beneficiarioVista.getBdPorcentajeBeneficioApo()==null){
					bdPorcentajeTotalAportes = bdPorcentajeTotalAportes.add(BigDecimal.ZERO);
				}else{
					bdPorcentajeTotalAportes = bdPorcentajeTotalAportes.add(beneficiarioVista.getBdPorcentajeBeneficioApo());
				}
				
				if(beneficiarioVista.getBdMontoAporte()==null){
					bdMontoTotalAportes = bdMontoTotalAportes.add(BigDecimal.ZERO);
				}else{
					bdMontoTotalAportes = bdMontoTotalAportes.add(beneficiarioVista.getBdMontoAporte());
				}
				
				if(beneficiarioVista.getBdPorcentajeBeneficioRet()==null){
					bdPorcentajeTotalRetiro = bdPorcentajeTotalRetiro.add(BigDecimal.ZERO);
				}else{
					bdPorcentajeTotalRetiro = bdPorcentajeTotalRetiro.add(beneficiarioVista.getBdPorcentajeBeneficioRet());
				}
				
				if(beneficiarioVista.getBdMontoRetiro()==null){
					bdMontoTotalRetiro = bdMontoTotalRetiro.add(BigDecimal.ZERO);
				}else{
					//03.06.2014 jchavez se adiciona interes calculado de fdo de retiro
					bdMontoTotalRetiro = bdMontoTotalRetiro.add(beneficiarioVista.getBdMontoRetiro());
				}
				
				if(beneficiarioVista.getBdMontoTotal()==null){
					bdMontoTotalBeneficiarios = bdMontoTotalBeneficiarios.add(BigDecimal.ZERO);
				}else{
					bdMontoTotalBeneficiarios = bdMontoTotalBeneficiarios.add(beneficiarioVista.getBdMontoTotal());
				}

			}
			
			if(beneficiarioTotales == null){
				beneficiarioTotales = new BeneficiarioLiquidacion();	
			}
			beneficiarioTotales.setBdPorcentajeBeneficioApo(bdPorcentajeTotalAportes);
			beneficiarioTotales.setBdMontoAporte(bdMontoTotalAportes);
			beneficiarioTotales.setBdPorcentajeBeneficioRet(bdPorcentajeTotalRetiro);
			beneficiarioTotales.setBdMontoRetiro(bdMontoTotalRetiro);
			beneficiarioTotales.setBdMontoTotal(bdMontoTotalBeneficiarios);

		} catch (Exception e) {
			log.error("Error en calcularBeneficiarioTotales ---> "+e);
			e.printStackTrace();
		}
		
	}
	
	private void cargarListaTablaSucursal() throws Exception{
		listaTablaSucursal.clear();
		
		List<Sucursal> listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
		//Ordena la sucursal alafabeticamente
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
		
		listaTablaSucursal = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
		Sucursal sucursal;
		Tabla tabla;
		for(Object o : listaSucursal){
			 sucursal = (Sucursal)o;
			 tabla = new Tabla();
			 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
			 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
			 listaTablaSucursal.add(tabla);
		}
	}

	public void seleccionarTipoBusquedaSucursal(){
		try{
			if(intTipoBusquedaSucursal.intValue()>0){
				cargarListaTablaSucursal();
				listaSubsucursal = new ArrayList<Subsucursal>();
			}else{
				listaTablaSucursal  = new ArrayList<Tabla>();
				listaSubsucursal = new ArrayList<Subsucursal>();
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 */
	/*public void buscar2(){
		try {
			listaExpedienteLiquidacion = liquidacionFacade.getListaExpedienteLiquidacionCompBusqueda();
		} catch (BusinessException e) {
			System.out.println("Error en buscar2 --> "+ e);
		}
	}*/
	
	
	/**
	 * Carga el 2do combo de Tipo de actividad segun 1er combo seleccionado
	 * @param event
	 */
	public void renderizarTextBusqueda(ActionEvent event)  {
		log.info("pIntTipoConsultaBusqueda: "+getRequestParameter("pIntTipoConsultaBusqueda"));
		String strTipoConsultaBusqueda = "";
		try {
						
			strTipoConsultaBusqueda = getRequestParameter("pIntTipoConsultaBusqueda");
			if(!strTipoConsultaBusqueda.equals("0")){
				blnTxtBusqueda = false;
			}else{
				blnTxtBusqueda = true;
			}
		} catch (Exception e) {
			log.error("Error en renderizarTextBusqueda ---> "+e);
			e.printStackTrace();
		}
	}

	
	/**
	 * Se se selecciona busqueda x fechas se bloque a la busqueda por condicion
	 * @param event
	 */
	public void renderizarBusquedaFechas(ActionEvent event)  {
		log.info("pIntParaEstado: "+getRequestParameter("pIntParaEstado"));
		String strBusquedaCondicion = "";
		try {			
			strBusquedaCondicion = getRequestParameter("pIntParaEstado");
			if(!strBusquedaCondicion.equals("0")){
				blnBusquedaFechas = true;
			}else{
				blnBusquedaFechas = false;
			}
		} catch (Exception e) {
			log.error("Error en renderizarBusquedaFechas ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * Se se selecciona busqueda x fechas se bloque a la busqueda por condicion
	 * @param event
	 */
	public void renderizarBusquedaCondicion(ActionEvent event)  {
		log.info("pIntParaEstadoCondicion: "+getRequestParameter("pIntParaEstadoCondicion"));
		String strBusquedaCondicion = "";
		try {			
			strBusquedaCondicion = getRequestParameter("pIntParaEstadoCondicion");
			if(!strBusquedaCondicion.equals("0")){
				blnBusquedaCondicion = true;
			}else{
				blnBusquedaCondicion = false;
			}
		} catch (Exception e) {
			log.error("Error en renderizarBusquedaCondicion ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 */
//	public void cargarCombosBusqueda(){
//		
//		try{
//			
//			// combo 1
//			listaTablaDeSucursal = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
//			List<Sucursal> listaSucursal =  empresaFacade.getListaSucursalPorPkEmpresa(usuario.getPerfil().getId().getIntPersEmpresaPk());
//			
//			//Ordenamos por nombre
//			Collections.sort(listaSucursal, new Comparator<Sucursal>(){
//				public int compare(Sucursal sucUno, Sucursal sucDos) {
//					return sucUno.getJuridica().getStrSiglas().compareTo(sucDos.getJuridica().getStrSiglas());
//				}
//			});
//			
//			Sucursal sucursal = null;
//			Tabla tabla = null;
//			for(int i=0;i<listaSucursal.size();i++){
//				 sucursal = listaSucursal.get(i);
//				 tabla = new Tabla();
//				 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
//				 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
//				 listaTablaDeSucursal.add(tabla);
//			}
//			
//			
//			// combo 2
//			listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 1);
//			
//			
//		} catch (BusinessException e) {
//			log.error("Error en cargarCombosBusqueda ---> "+e);
//			e.printStackTrace();
//		} 
//}
	
	
	/**
	 * Recupera la lista de Solicitudes de Credito. {Busqueda -
	 * SolicitudCreditoMain.jsp}
	 * @param event
	 * 	Tipo de Busqueda :  intTipoConsultaBusqueda
						   	listaTipoConsultaBusqueda 
						   	strConsultaBusqueda
		Nro. Solicitud	 : 	strNumeroSolicitudBusq
		Sucursal 		 :	estadoCreditoBusqueda.intIdUsuSucursalPk
							listaTablaDeSucursal
		Estado 			 : 	estadoCreditoBusqueda.intParaEstadoCreditoCod
		Tipo Préstamo 	 : 	expCreditoBusqueda.intPersEmpresaCreditoPk
							listaTablaCreditoEmpresa
	 */
	public void listarSolicitudLiquidacion(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.listarSolicitudPrestamo -----------------------------");
		cancelarGrabarSolicitud();
		ExpedienteLiquidacionComp expLiqComp= null;
		try {
			
			//liquidacionFacade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			/*ExpedienteCreditoComp o = new ExpedienteCreditoComp();
			
			listaExpedienteCreditoComp = facade.getListaExpedienteActividadCompDeBusqueda(o);*/
			//listaExpedienteCreditoComp= new ArrayList<ExpedienteCreditoComp>();
			
			//
			
			expLiqComp = new ExpedienteLiquidacionComp();
			expLiqComp.setIntBusquedaTipo(intBusquedaTipo);
			expLiqComp.setStrBusqCadena(strBusqCadena.trim());
			expLiqComp.setStrBusqNroSol(strBusqNroSol);
			//expLiqComp.setIntBusqTipoLiquidacion(intTipoCreditoFiltro);
			expLiqComp.setIntBusqSucursal(intBusqSucursal);
			expLiqComp.setIntBusqEstado(intBusqEstado);
			expLiqComp.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);
			expLiqComp.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);
			expLiqComp.setIntBusqTipoLiquidacion(intBusqTipoLiquidacion);
			
		 		
			listaExpedienteLiquidacionComp = liquidacionFacade.getListaBusqExpLiqFiltros(expLiqComp);
			//limpiarFiltros(event);
			
			
		} catch (BusinessException e) {
			log.error("Error BusinessException listarSolicitudLiquidacion ---> "+e);
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 
	 * @param exito
	 * @param mensaje
	 */
	public void mostrarMensaje(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
		}else{
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}
	
	
	

	
	/**
	 * 
	 * @param event
	 */
	public void nuevaSolicitudLiquidacion(ActionEvent event) {
		cancelarGrabarSolicitud();
		//limpiarMensajesIsValidoExpediente();
		
		strSolicitudLiquidacion = Constante.MANTENIMIENTO_MODIFICAR;
		//strSolicitudLiquidacion = Constante.MANTENIMIENTO_GRABAR;
		blnShowValidarDatos = true;
		dtFechaRegistro = Calendar.getInstance().getTime();

		
		blnShowDivFormSolicitudLiquidacion = false;
		//listaBeneficiarioPrevisionBusq = new ArrayList<BeneficiarioPrevision>();
		//listaFallecidosPrevisionBusq = new ArrayList<FallecidoPrevision>();
		
	}
	
	
	/**
	 * 
	 */
	public void limpiarFormSolicitudLiquidacion() {
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

		personaValida = new Persona();
		personaValida.setDocumento(new Documento());

		beanExpedienteLiquidacion = new ExpedienteLiquidacion();
		beanExpedienteLiquidacion.setId(new ExpedienteLiquidacionId());
		beanExpedienteLiquidacion.setListaEstadoLiquidacion(new ArrayList<EstadoLiquidacion>());
		//beanExpedienteLiquidacion.setListaBeneficiarioLiquidacion(new ArrayList<BeneficiarioLiquidacion>());
		beanExpedienteLiquidacion.setListaExpedienteLiquidacionDetalle(new ArrayList<ExpedienteLiquidacionDetalle>());
		dtFechaRegistro = null;
		dtFechaRegistro = Calendar.getInstance().getTime();
		
//		listaCuentaSocio = new ArrayList<CuentaComp>();
		listaCuentaConcepto = new ArrayList<CuentaConcepto>();
		listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
		listExpedienteMovimientoComp =new ArrayList<ExpedienteComp>();
		
		chkCondicionBeneficioFondoSepelio = true;
		chkCondicionSinGarantesDeudores = true;
		chkCondicionSocioSinDeudaPendiente = true;
		
		blnCondicionBeneficioFondoSepelio = false;
		blnCondicionSinGarantesDeudores = false;
		blnCondicionSocioSinDeudaPendiente = false;

		blnIsFallecimiento = false;
		blnIsRenuncia = false;
		blnPostEvaluacion = false;

		campoBuscarBeneficiario="";
		blnTieneAdjuntosConfigurados = false;

		strMsgObservacion = "";
		
		strFechaRenuncia = Constante.sdf.format(Calendar.getInstance().getTime());

		strFechaRecepcionRenuncia = Constante.sdf.format(new Date());
		strFechaProgramacionPago = "";//Constante.sdf.format(addDaysToDate(new Date(), 60));
		blnTxtBusqueda = true;
		blnBusquedaFechas = false;
		blnBusquedaCondicion = false;

		blnMostrarDescripcionTipoLiquidacion = Boolean.TRUE;
		strDescripcionTipoLiquidacion = "";
		strMsgCondicionSocioSinDeudaPendiente = "";
		strMsgCondicionSinGarantesDeudores = "";
		
//		limpiarListaBeneficiarios();
//		limpiarBeneficiarioTotales();
		limpiarBeneficiarios();
		
		blnMostrarObjeto = true;
		blnMonstrarEliminar= true;
		blnMonstrarActualizar= true;
		
		strMsgTxtProcedeEvaluacion = "";
		strMsgTxtProcedeEvaluacion1="";
		blnVerSolExpLiquidacion = false;
		
		//Autor: jchavez / Tarea: Modificación / Fecha: 03.12.2014
		limpiarBeneficiarios();
		
		listaCuentaSocio.clear();
		
		blnShowValidarDatos = false;
		blnShowDivFormSolicitudLiquidacion = false;
		
		strSolicitudLiquidacion = Constante.MANTENIMIENTO_MODIFICAR;
	}
	
	
	/**
	 * Valida si la operacion ELIMINAR se visualiza o no en el popup de acciones.
	 * Solo se podra eliminar si es Requisito.
	 */
	public void validarOperacionEliminar(){
		Integer intUltimoEstado = 0;
		
		try {
			if(registroSeleccionadoBusquedaComp != null){
				intUltimoEstado = registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntEstadoCreditoUltimo();
				
				if(intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
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
	 * Valida si la operacion ELIMINAR se visualiza o no en el popup de acciones.
	 * Solo se podra eliminar si es Requisito.
	 */
	public void validarOperacionActualizar(){
		Integer intUltimoEstado = 0;
		
		try {
			if(registroSeleccionadoBusqueda != null){
				intUltimoEstado = registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntEstadoCreditoUltimo();
				
				if(intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
					|| intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
					blnMonstrarActualizar = Boolean.TRUE;
				}else{
					blnMonstrarActualizar = Boolean.FALSE;
				}	
			}
		} catch (Exception e) {
			log.error("Error en validarOperacionEliminar ---> "+e);
		}

	}
	
	public void limpiarBeneficiarios(){
		listaBeneficiarioLiquidacionBusq.clear();
		listaBeneficiarioLiquidacionVista.clear();
		
		beneficiarioTotales = new BeneficiarioLiquidacion();
		beneficiarioTotales.setBdPorcentajeBeneficioApo(BigDecimal.ZERO);
		beneficiarioTotales.setBdMontoAporte(BigDecimal.ZERO);
		beneficiarioTotales.setBdPorcentajeBeneficioRet(BigDecimal.ZERO);
		beneficiarioTotales.setBdMontoRetiro(BigDecimal.ZERO);
		beneficiarioTotales.setBdMontoTotal(BigDecimal.ZERO);
	}
	
	/**
	 * 
	 */
	public void limpiarListaBeneficiarios(){
		try {
			if(listaBeneficiarioLiquidacionVista != null && !listaBeneficiarioLiquidacionVista.isEmpty()){
				listaBeneficiarioLiquidacionVista.clear();
			}else{
				listaBeneficiarioLiquidacionVista = new ArrayList<BeneficiarioLiquidacion>();
			}
			
			
			if(listaBeneficiarioLiquidacionBusq != null && !listaBeneficiarioLiquidacionBusq.isEmpty()){
				listaBeneficiarioLiquidacionBusq.clear();
			}else{
				listaBeneficiarioLiquidacionBusq = new ArrayList<BeneficiarioLiquidacion>();
			}
			log.info("blnShowDivFormSolicitudLiquidacion: "+blnShowDivFormSolicitudLiquidacion);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Si las liquidaciones previas estan en estado Requsiito, Solicitud o Aprobado. NO procede una nueva solicitud.
	 * Devuleve true si existen.
	 * @param cuentaId
	 * @return
	 */
	public Boolean existeLiquidacionPrevia (CuentaId cuentaId){
		List<ExpedienteLiquidacion> listaLiquidaciones = null;
		Boolean blnExisteLiquidacionPrevia = Boolean.FALSE;
		
		try {
			
			listaLiquidaciones = solicitudLiquidacionService.getListaExpedienteLiquidacionMasEstadoPorCuenta(cuentaId);
			if(listaLiquidaciones != null && !listaLiquidaciones.isEmpty()){
				for (ExpedienteLiquidacion expedienteLiquidacion : listaLiquidaciones) {
					if(expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
					|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0
					|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
					|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
						blnExisteLiquidacionPrevia = Boolean.TRUE;
						break;
					}
				}
				
			}

		} catch (Exception e) {
			log.error("Error en existeLiquidacionPrevia ---> "+e);
		}

		return blnExisteLiquidacionPrevia;
	}
	
	/**
	 * Validacion inicial del socio. Se valida estado de persona, estados de cuenta, etc.
	 * Valida la existencia de liquidaciones en estado Requisito, Solicitud o Aprobado. 
	 * Validamos que no tenga solicitudes de prevision Previas en estado Aprobado.
	 * @param event
	 */
	public void validarDatos() {
		SocioComp socioComp = null;
		Integer intTipoDoc = personaValida.getDocumento().getIntTipoIdentidadCod();
		String strNumIdentidad = personaValida.getDocumento().getStrNumeroIdentidad().trim();
		BigDecimal bdMontoSolicitudLiquidacion = BigDecimal.ZERO;
		
		Boolean blnContinuaBarrido = Boolean.TRUE;
		Boolean blnExistenPrevias = Boolean.FALSE;
		strMsgErrorValidarDatos = "";
		
		bdMontoInteresFdoRetiro = BigDecimal.ZERO;

		//Autor: jchavez / Tarea: Modificación / Fecha: 03.12.2014
		listaCuentaSocio.clear();
		try {
			if ((intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_SOCIO)
			|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_USUARIO)
			|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_CLIENTE)
			|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_NO_SOCIO))){

				socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(intTipoDoc,strNumIdentidad, usuario.getEmpresa().getIntIdEmpresa());
				if (socioComp != null) {
					if (socioComp.getCuenta() != null) {
						if(socioComp.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							// Valida la existencia de liquidaciones en estado Requsiito, Solicitud, Aprobado u Observado. 
							blnExistenPrevias = existeLiquidacionPrevia(socioComp.getCuenta().getId());

							if(!blnExistenPrevias){
								if(socioComp.getPersona() != null){
									// • Estado de persona = 1 activa 
									//28.08.2013
									//if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){

										for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
											if(blnContinuaBarrido){
												if(socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
													if (socioEstructura.getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)==0) {
														strMsgErrorValidarDatos = "";
														socioComp.getSocio().setSocioEstructura(socioEstructura);

														//if(!(socioComp.getCuenta().getIntParaSubTipoCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0)){
														//•	Cuenta situación = 1 activa
														if(socioComp.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0){
															
															// agregar validacion: no debe existir expedientes de prevision.
															pgValidDatos = false;
															blnDatosSocio = true;
															beanSocioComp = socioComp;
															blnContinuaBarrido = Boolean.FALSE;

															// strDescripcionTipoCuenta
//																	listaCuentaSocio = new ArrayList<CuentaComp>();
															listaCuentaSocio.clear();
															CuentaComp cuentaCompSocio = new CuentaComp();
															cuentaCompSocio.setCuenta(beanSocioComp.getCuenta());

															// 1. Secarga la descripcion del Tipo de Cuenta - lista 1
															for(int t=0; t<listaDescripcionTipoCuenta.size();t++){
																if(listaDescripcionTipoCuenta.get(t).getIntIdDetalle().compareTo(beanSocioComp.getCuenta().getIntParaTipoCuentaCod())==0){
																	cuentaCompSocio.setStrDescripcionTipoCuenta(listaDescripcionTipoCuenta.get(t).getStrDescripcion());
																	break;
																}
															}

															// 2. De momento solo hay una cuenta x socio
															listaCuentaSocio.add(cuentaCompSocio);
															String strDescCuenta = listaCuentaSocio.get(0).getStrDescripcionTipoCuenta();

															//List<CuentaConcepto> lstCtaCto = null;
															listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());

															// Solo se deben visualizar 4 cuentas: Aporte, Retiro, Ahoroo y Depaosito
															listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();

															CuentaConceptoComp cuentaConceptoComp = null;
															CuentaConcepto cuentaConcepto = null;
															for(int k=0; k<listaCuentaConcepto.size();k++){
																cuentaConcepto = listaCuentaConcepto.get(k);
																cuentaConceptoComp = new CuentaConceptoComp();
																// cargando la descripcion del tipo de cuenta
																cuentaConceptoComp.setStrDescripcionCuenta(strDescCuenta); 
													
																// cargando la descripcion de cada cuenta concepto
																for (Tabla descripcion : listaDescripcionCuentaConcepto) {
																	CuentaConceptoDetalle detalle = null;
																	if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
																	&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
																		detalle = new CuentaConceptoDetalle();
																		detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);

																		if(descripcion.getIntIdDetalle().compareTo(detalle.getIntParaTipoConceptoCod())==0){
																			//Autor: jchavez / Tarea: Modificación / Fecha: 29.08.2014 /
																			cuentaConceptoComp.setIntParaTipoConceptoCod(detalle.getIntParaTipoConceptoCod());
																			//FIN jchavez - 29.08.2014
																			cuentaConceptoComp.setStrDescripcionConcepto(descripcion.getStrDescripcion());
																			cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNumeroCuenta());
																			cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
																			if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0
																			 ||detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
																				cuentaConceptoComp.getCuentaConcepto().setChecked(true);
																				cuentaConceptoComp.setLstCuentaConceptoDetalle(new ArrayList<CuentaConceptoDetalle>());
																				cuentaConceptoComp.getLstCuentaConceptoDetalle().add(detalle);
																				//02.06.2014 jchavez - Se agrega calculo del interes ganado caso liquidacion fdo. de retiro.
																				BigDecimal bdMontoInteresCalculado = BigDecimal.ZERO;
																				if (detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ) {
																					bdMontoInteresCalculado = calcularInteresRetiroAcumulado(cuentaConceptoComp);
																					bdMontoInteresFdoRetiro = bdMontoInteresCalculado;
																				}
																				
																				bdMontoSolicitudLiquidacion = bdMontoSolicitudLiquidacion.add(cuentaConcepto.getBdSaldo()).add(bdMontoInteresCalculado);
																			}
																			
																				break;
																			}
																		}	
																	}

																listaCuentaConceptoComp.add(cuentaConceptoComp);
																}
															
															beanExpedienteLiquidacion.setBdMontoBrutoLiquidacion(bdMontoSolicitudLiquidacion);
															
															EstructuraId estructuraId = new EstructuraId();
															Estructura estructura = null;
															estructuraId.setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
															estructuraId.setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
															estructura = estructuraFacade.getEstructuraPorPk(estructuraId);

															beanEstructuraSocioComp = estructura;
															estructura.getListaEstructuraDetalle();

															if(estructura.getListaEstructuraDetalle() != null){
																for(int k=0; k<estructura.getListaEstructuraDetalle().size();k++){
																	estructura.getListaEstructuraDetalle().get(k).getListaSubsucursal();
																}
															}

															blnShowDivFormSolicitudLiquidacion = true;
															blnShowValidarDatos = false;

															// cargando sucursal y subsucursal del socio
															cargarListaTablaSucursal();
															seleccionarSucursal();

														} else {
															strMsgErrorValidarDatos = strMsgErrorValidarDatos +"La sub condición de la Cuenta no es Regular. ";
														}

													}else{
														strMsgErrorValidarDatos = strMsgErrorValidarDatos+ "El socio no posee una estructura de origen.";
													}
												}
											}
										}

										if(!blnContinuaBarrido){
											cargarDescripcionUEjecutorasConcatenadas(socioComp);
										}
									}else{
										strMsgErrorValidarDatos = "No se recuperó Persona.";
									}	
									
								
								}else{
										strMsgErrorValidarDatos = "El Socio posee Solicitudes de Prevision en estado APROBADO.";
								}
							
							/*}else{
								strMsgErrorValidarDatos = "El Socio posee Solicitudes de Liquidación en estado Requisito, Solicitud o Aprobada.";
							}*/
						}else{
							strMsgErrorValidarDatos = "El Socio no posee Cuenta asociada.";
						}


					} else {
						pgValidDatos = true;
						blnDatosSocio = false;
						strMsgErrorValidarDatos = "La Situación de la Cuenta del Socio no se encuentra en estado ACTIVO. ";
					} 
				}else {
					pgValidDatos = true;
					blnDatosSocio = false;
					strMsgErrorValidarDatos = "Rol de Socio incorrecto. ";
				}
			}			
		} catch (BusinessException e) {
			log.error("error: " + e);
		} catch (Exception e1) {
			log.error(e1);
		} finally{
			generarCuentasConceptoBase();
		}
	}
	/**
	 * JCHAVEZ 02.06.2014
	 * Formula General del cálculo del interes acumulado
	 * @param cuentaConceptoComp
	 * @return
	 */
	public BigDecimal calcularInteresRetiroAcumulado(CuentaConceptoComp cuentaConceptoComp){
		List<Movimiento> listaMovimiento = null; 
		Movimiento movimientoInteresUltimo = null;
		BigDecimal bdInteresCalculado = BigDecimal.ZERO;
		Captacion beanCaptacion = null;
		CaptacionFacadeRemote captacionFacade = null;
		ConceptoFacadeRemote conceptoFacadeRemote= null;
		BigDecimal bdMontoInteresMasSaldoInteres = BigDecimal.ZERO;
		CuentaConcepto cuentaConceptoRetiro = new CuentaConcepto();
		CuentaConceptoDetalle cuentaConceptoDetalleRetiro = new CuentaConceptoDetalle();

		try {
			cuentaConceptoRetiro = cuentaConceptoComp.getCuentaConcepto();
			cuentaConceptoDetalleRetiro = cuentaConceptoComp.getLstCuentaConceptoDetalle().get(0);
			captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
			conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			CaptacionId captacionId = new CaptacionId();
			captacionId.setIntPersEmpresaPk(cuentaConceptoDetalleRetiro.getId().getIntPersEmpresaPk());
			captacionId.setIntParaTipoCaptacionCod(cuentaConceptoDetalleRetiro.getIntParaTipoConceptoCod());
			captacionId.setIntItem(cuentaConceptoDetalleRetiro.getIntItemConcepto());

			beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
						
						
			if(beanCaptacion != null){
				// 1. Recuperamos las llaves de la cuenta cto retiro, para obetener los movimientos de interes de retiro.
				if(cuentaConceptoRetiro != null){
					listaMovimiento = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(), 
							cuentaConceptoRetiro.getId().getIntCuentaPk(), 
							cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(), 
							Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
					
					if(listaMovimiento != null && !listaMovimiento.isEmpty()){
						//Ordenamos los subtipos por int
						Collections.sort(listaMovimiento, new Comparator<Movimiento>(){
							public int compare(Movimiento uno, Movimiento otro) {
								return uno.getIntItemMovimiento().compareTo(otro.getIntItemMovimiento());
							}
						});
						
						Integer tamanno = 0;
						tamanno = listaMovimiento.size();
						tamanno = tamanno -1;
						// recuperamos el ultimo registro y en base a le se realiza calculo...
						movimientoInteresUltimo = new Movimiento();
						movimientoInteresUltimo = listaMovimiento.get(tamanno);
						
						BigDecimal bdTotalBaseCtaMasInt = BigDecimal.ZERO;
						BigDecimal bdUltimoCapRetiro = BigDecimal.ZERO;
						
						// a. 	Calculamos el monto base para el calculo de interes
						bdTotalBaseCtaMasInt = movimientoInteresUltimo.getBdMontoSaldo();
						// Agregado 26.05.2014 jchavez. ademas del utimo interes se debe de recuperar de movimiento la ultima 
						// amortizacion de capital de fdo. retiro.
						List<Movimiento> lstMovCapital = null;

						lstMovCapital = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(cuentaConceptoRetiro.getId().getIntPersEmpresaPk(),
								cuentaConceptoRetiro.getId().getIntCuentaPk(),cuentaConceptoRetiro.getId().getIntItemCuentaConcepto(),
								Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
						
						
						if (lstMovCapital!=null && !lstMovCapital.isEmpty()) {
							bdUltimoCapRetiro = lstMovCapital.get(0).getBdMontoSaldo();
						}
						// b. Recuperamos nro de dias entres fecha de movimeitno y hoy.
						Date dtFechaUltimoInteres = new Date();
						String strFechaUltimoInteres = Constante.sdf.format(movimientoInteresUltimo.getTsFechaMovimiento());
						dtFechaUltimoInteres = Constante.sdf.parse(strFechaUltimoInteres);
						
						Date dtHoy = new Date();

						Integer nroDias =  obtenerDiasEntreFechas(dtHoy, dtFechaUltimoInteres);
						nroDias = Math.abs(nroDias);
						
						
						if(nroDias.compareTo(0)!= 0){
							// c. Se aplica formula simple de interes --> Monto*Porc Interes*(dias/30)
							/*
							 * Modificacion 26.04.2014 jchavez
							 * Porc Interes beanCaptacion.getBdTem(). 
							 * Se agrega a la formula el *(nro dias)/30
							 */
							bdInteresCalculado =  (bdTotalBaseCtaMasInt.add(bdUltimoCapRetiro)).multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
							bdMontoInteresMasSaldoInteres = bdInteresCalculado.add(bdTotalBaseCtaMasInt);
						}else{
							bdInteresCalculado = BigDecimal.ZERO;
							bdMontoInteresMasSaldoInteres = BigDecimal.ZERO.add(bdTotalBaseCtaMasInt);
						}
					}
					//Agregado 22.04.2014 jchavez
					//En caso no exista movimiento anterior, usar las tablas cuenta concepto y cuenta concepto detalle
					else{
						cuentaConceptoDetalleRetiro.setId(new CuentaConceptoDetalleId());
						cuentaConceptoDetalleRetiro.getId().setIntPersEmpresaPk(cuentaConceptoRetiro.getId().getIntPersEmpresaPk());
						cuentaConceptoDetalleRetiro.getId().setIntCuentaPk(cuentaConceptoRetiro.getId().getIntCuentaPk());
						cuentaConceptoDetalleRetiro.getId().setIntItemCuentaConcepto(cuentaConceptoRetiro.getId().getIntItemCuentaConcepto());
						cuentaConceptoDetalleRetiro.setIntParaTipoConceptoCod(Constante.CAPTACION_FDO_RETIRO);
						cuentaConceptoDetalleRetiro = conceptoFacadeRemote.getCuentaConceptoDetallePorPkYTipoConcepto(cuentaConceptoDetalleRetiro);
						
						Date dtHoy = new Date();
						Integer nroDias =  obtenerDiasEntreFechas(convertirTimestampToDate(cuentaConceptoDetalleRetiro.getTsInicio()),dtHoy);
						
						bdInteresCalculado =  cuentaConceptoRetiro.getBdSaldo().multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
						bdMontoInteresMasSaldoInteres = bdInteresCalculado.add(BigDecimal.ZERO);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error en calcularInteresRetiro ---> "+e);
		}
		return bdMontoInteresMasSaldoInteres;
	}
	
    private static Date convertirTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }	
    
//	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
//		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
//	}
	
	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		SimpleDateFormat strEnlace = new SimpleDateFormat("dd/MM/yyyy");
		Date dtFecIni = strEnlace.parse(strEnlace.format(dtFechaInicio));
		Date dtFecFin = strEnlace.parse(strEnlace.format(dtFechaFin));
		return (int)( (dtFecFin.getTime() - dtFecIni.getTime()) / (1000 * 60 * 60 * 24) );
	} 
	
	/**
	 * VAlida la existencia de previsiones previas.
	 * Si es Liquidacion Fallecimeinto. Exige existencia de prevision fallec titular.
	 * Si otro tipo valida q no exista sol prevision en estado APROBADO.
	 * @return
	 */
	public Boolean validarExistencaPrevisionesPreviasPorTipo(){
		Boolean blnPasa = Boolean.TRUE;
		List<ExpedientePrevision> listaExpPrevision = null;
		//String strMsgTxtValidacionPrevision = "";
		try {
			strMsgTxtProcedeEvaluacion1 ="";
			listaExpPrevision = previsionFacade.getListaExpedientePrevisionPorCuenta(beanSocioComp.getCuenta());
				
				if(intTipoOperacion.compareTo(Constante.PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA)==0
					&& beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
					Boolean blnTiene = Boolean.FALSE;
					if(listaExpPrevision != null && !listaExpPrevision.isEmpty()){
						for (ExpedientePrevision expedientePrevision : listaExpPrevision) {
							blnTiene = Boolean.FALSE;
							if((expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
								|| expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0 )
								&& expedientePrevision.getIntParaTipoCaptacion().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
								&& expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
								blnTiene = Boolean.TRUE;
								//blnPasa = Boolean.TRUE;
								strMsgTxtProcedeEvaluacion1 = strMsgTxtProcedeEvaluacion1+"";
								break;
							}
						}
						if(blnTiene){
							blnPasa = Boolean.TRUE;
							strMsgTxtProcedeEvaluacion1 = strMsgTxtProcedeEvaluacion1 +"";
						}else{
							blnPasa = Boolean.FALSE;
							strMsgTxtProcedeEvaluacion1 = strMsgTxtProcedeEvaluacion1 +" El Socio no posee Solicitud de Previsión Sepelio Titular. No procede la Evaluación.";
						}
					}else{
						blnPasa = Boolean.FALSE;
						strMsgTxtProcedeEvaluacion1 =strMsgTxtProcedeEvaluacion1+  " El Socio no posee Solicitudes de Previsión. No procede la Evaluación.";
					}

				}else{
					
					if(listaExpPrevision != null && !listaExpPrevision.isEmpty()){
						for (ExpedientePrevision expedientePrevision : listaExpPrevision) {
							if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
								blnPasa = Boolean.FALSE;
								strMsgTxtProcedeEvaluacion1 = strMsgTxtProcedeEvaluacion1+ " El Socio posee alguna Solicitud de Previsión en estado Aprobado. No procede la Evaluación.";
								break;
							}
						}
					}else{
						blnPasa = Boolean.TRUE;
						strMsgTxtProcedeEvaluacion1 = strMsgTxtProcedeEvaluacion1+"";
					}
				}

		} catch (Exception e) {
			log.error("Erroe n validarExistencaPrevisionesPreviasPorTipo ---> "+e);
		}
		
		return blnPasa;
		
	}
	
	
	/**
	 * Genera los beans de ctacto aporte y/o retiro. En base a 'listaCuentaConceptoComp' de Validar datos.
	 */
	public void generarCuentasConceptoBase(){
		
		try {
			
			if(listaCuentaConceptoComp != null && !listaCuentaConceptoComp.isEmpty()){
				for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptoComp) {
					if (cuentaConceptoComp.getCuentaConcepto()!=null) {
						if(cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle()!= null
								&& !cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().isEmpty()){
								
							CuentaConceptoDetalle detalle = cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().get(0);
							if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
								beanCuentaConceptoAportes = new CuentaConceptoComp();
								beanCuentaConceptoAportes = cuentaConceptoComp;
								
							}
						}
						
						if(cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle()!= null
							&& !cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().isEmpty()){
								
							CuentaConceptoDetalle detalle = cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().get(0);
							if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
								beanCuentaConceptoRetiro = new CuentaConceptoComp();
								beanCuentaConceptoRetiro = cuentaConceptoComp;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en generarCuentasConceptoBase ---> "+e);
		}
		
		
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
	 * Carga el combo de sub sucursal en la grilla de busqueda.
	 */
	public void seleccionarSucursalBusq(){
		try{
			if(estadoLiquidacionSuc.getIntSucuIdSucursal().intValue()>0){
				listaSubsucursalBusq = empresaFacade.getListaSubSucursalPorIdSucursal(estadoLiquidacionSuc.getIntSucuIdSucursal());
			}else{
				listaSubsucursalBusq = new ArrayList<Subsucursal>();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 */
	public void seleccionarSucursal(){
		try{
			
			// socio.socioEstructura.intIdSubsucurAdministra
			if(beanSocioComp.getSocio().getSocioEstructura().getIntEmpresaSucAdministra()!= null){
				listaSubsucursalSocio = empresaFacade.getListaSubSucursalPorIdSucursal(beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
				strSubsucursalSocio = listaSubsucursalSocio.get(0).getStrDescripcion();
				
				System.out.println("SUBSUCURSAL ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
				System.out.println("SUCURSAL ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
			}else{
				listaSubsucursalSocio = new ArrayList<Subsucursal>();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
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
	
	
	/**
	 * 
	 * @param event
	 */
	public void loadListDocumentoBusq(ActionEvent event) {
		log.info("-------------------------------------Debugging .loadListDocumento-------------------------------------");
		log.info("pIntTipoPersona: "+getRequestParameter("pIntTipoPersonaLiquidacion")); //pIntTipoPersonaLiquidacion
		String strIdTipoPersona = null;
		List<Tabla> listaDocumento = null;
		try {
			strIdTipoPersona = getRequestParameter("pIntTipoPersonaLiquidacion");
			if(!strIdTipoPersona.equals("0")){
				if(strIdTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA.toString())){
					listaDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO), Constante.VISTA_TIPOPERSONA_JURIDICA);
				}else{
					listaDocumento = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO));
				}
				log.info("listaDocumento.size: "+listaDocumento.size());
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListDocumentoBusq(listaDocumento);
	}
	
	
	/**
	 * 
	 */
	
	public void descargaArchivoUltimo2() {
		log.info("LALALALA");
	}
	
	public void descargaArchivoUltimo() {
		TipoArchivo tipoArchivo = null;
		String strNombreArchivo = null;
		String strParaTipoCod = null;
		
		//ServletContext context = (ServletContext) getExternalContext().getContext();
		//ExternalContext con = FacesContext.getCurrentInstance().getExternalContext();
	    //ServletContext sCon = (ServletContext) con.getContext();
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

			//String realPath = sCon.getRealPath(strRutaActual+"/" + strNombreArchivo);
			//String realPath = "C:\\Tumi\\ArchivosAdjuntos\\Documentos\\ExpedientePrestamos\\CopiaDNI"+"\\" + strNombreArchivo;
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
			log.error("Error al descargar archivo ---> "+exc);
		} 
	
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void addBeneficiario(ActionEvent event){
		log.info("-------------------------------------Debugging buscarBeneficiario-------------------------------------");
		listaBeneficiarioLiquidacionBusq.clear();
		listaBeneficiarioLiquidacionBusq = new ArrayList<BeneficiarioLiquidacion>();
		listarBeneficiariosDeSocio(beanSocioComp);
	}
	
	
	/**
	 * Al momento de seleccionar la grilla de busqueda y seleccionar un beneficiario
	 * @param event
	 */
	public void seleccionarBeneficiario(ActionEvent event){
		try{
			campoBuscarBeneficiario ="";
			beneficiarioSeleccionado = (BeneficiarioLiquidacion)event.getComponent().getAttributes().get("itemBeneficiariosPopup");
		}catch (Exception e) {
			log.error("Error en seleccionarBeneficiario ---> "+e);
		}
	}
	
	/**
	 * 
	 */
	public void limpiarObservaciones (){
		try {
			if(lstDeudaPendiente != null && !lstDeudaPendiente.isEmpty()) lstDeudaPendiente.clear();
			setChkCondicionSocioSinDeudaPendiente(Boolean.TRUE);
			lstDeudaPendiente.clear();
			strMsgCondicionSinGarantesDeudores = "";
			setChkCondicionSinGarantesDeudores(Boolean.TRUE);
			setChkCondicionBeneficioFondoSepelio(Boolean.TRUE);
			
			System.out.println("listExpedienteMovimientoComp----< "+listExpedienteMovimientoComp);
			//if(listExpedienteMovimientoComp != null && !listExpedienteMovimientoComp.isEmpty()) listExpedienteMovimientoComp.clear();
			
			
		} catch (Exception e) {
			log.error("Error en limpiarObservaciones ---> "+e);
		}
		
	}
	
	/**
	 * Autor: jchavez / Tarea: Modificación / Fecha: 13.12.2014
	 * Validacion Nro 1.
	 * recuperamos todos los expediente que tengan saldo distinto de 0.00.
	 * Solo del tipo prestamo/credito.
	 */
	public void isValidCondicionSocioSinDeudaPendiente(){
		List<Expediente> lstExpedienteCreditoMovimiento = null;
		Expediente expedienteMov = null;
		blnCondicionSocioSinDeudaPendiente = Boolean.TRUE;
		lstDeudaPendiente.clear();
		listExpedienteMovimientoComp.clear();
		Integer intContTipoCreditompresa = 0;
		try {
			Integer intCuenta = beanSocioComp.getCuenta().getId().getIntCuenta();
			Integer intEmpresa = beanSocioComp.getCuenta().getId().getIntPersEmpresaPk();
			lstExpedienteCreditoMovimiento = conceptoFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa,intCuenta);
		   
//			listExpedienteMovimientoComp = new ArrayList<ExpedienteComp>();
			
			if( lstExpedienteCreditoMovimiento != null && !lstExpedienteCreditoMovimiento.isEmpty()){
				
				for(int k=0; k<lstExpedienteCreditoMovimiento.size();k++) {
					List<EstadoExpediente> listaEstados = null;
					listaEstados = lstExpedienteCreditoMovimiento.get(k).getListaEstadosExpediente();
					
					//PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE = 1;
					//PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_CANCELADO = 2;
					
					if(listaEstados != null && !listaEstados.isEmpty()){
						for (EstadoExpediente estadoExpediente : listaEstados) {
							if(estadoExpediente.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){
								blnCondicionSocioSinDeudaPendiente = Boolean.FALSE;
								
								expedienteMov = new Expediente();
								expedienteMov = lstExpedienteCreditoMovimiento.get(k); 
								ExpedienteComp expedienteComp = new ExpedienteComp();
								expedienteComp.setExpediente(expedienteMov);
								listExpedienteMovimientoComp.add(k,expedienteComp);
								
								for(int d=0; d<listaDescTipoCredito.size();d++){
									if(lstExpedienteCreditoMovimiento.get(k).getIntParaTipoCreditoCod().compareTo(listaDescTipoCredito.get(d).getIntIdDetalle())==0){
										listExpedienteMovimientoComp.get(k).setStrDescripcionTipoCredito(
												listaDescTipoCredito.get(d).getStrDescripcion());
									}
								}
								
								// cargando el tipo credito empresa
								CreditoId creditoId = null;
								Credito credito = null;
								if(lstExpedienteCreditoMovimiento.get(k).getIntParaTipoCreditoCod().compareTo( Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)==0){
									creditoId = new CreditoId();
									creditoId.setIntPersEmpresaPk(lstExpedienteCreditoMovimiento.get(k).getIntPersEmpresaCreditoPk());
									creditoId.setIntItemCredito(lstExpedienteCreditoMovimiento.get(k).getIntItemCredito());
									creditoId.setIntParaTipoCreditoCod(lstExpedienteCreditoMovimiento.get(k).getIntParaTipoCreditoCod());
				
									// buscamos el tipocreditoempresa de confcreditos
									credito = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
									if(credito != null){
										for(int e=0;e<listaDescTipoCreditoEmpresa.size();e++){
											intContTipoCreditompresa ++;
											if(listaDescTipoCreditoEmpresa.get(e).getIntIdDetalle().compareTo(credito.getIntParaTipoCreditoEmpresa())==0){
												listExpedienteMovimientoComp.get(k).setStrDescripcionTipoCreditoEmpresa(
														listaDescTipoCreditoEmpresa.get(e).getStrDescripcion());
											}
										}
									}
								}
							}
						}
					}	
				}
				
				String strError= "";
				// formando mensaje de error
//				lstDeudaPendiente = new ArrayList<Tabla>();
				for(int m=0;m<listExpedienteMovimientoComp.size();m++){
					Tabla tablaErr = new Tabla();
					if(listExpedienteMovimientoComp.get(m).getExpediente().getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)==0){
						strError = strError + listExpedienteMovimientoComp.get(m).getStrDescripcionTipoCredito()+" : " + listExpedienteMovimientoComp.get(m).getStrDescripcionTipoCreditoEmpresa()+
								" : " + " S/."+ listExpedienteMovimientoComp.get(m).getExpediente().getBdSaldoCredito()+" \n";
						
						tablaErr.setStrDescripcion(listExpedienteMovimientoComp.get(m).getStrDescripcionTipoCredito()+" : " + listExpedienteMovimientoComp.get(m).getStrDescripcionTipoCreditoEmpresa()+
								" : " + " S/."+ listExpedienteMovimientoComp.get(m).getExpediente().getBdSaldoCredito()+" \n");
						lstDeudaPendiente.add(tablaErr);
					}else{
						strError = strError + listExpedienteMovimientoComp.get(m).getStrDescripcionTipoCredito()+" : "+" S/."+ listExpedienteMovimientoComp.get(m).getExpediente().getBdSaldoCredito()+" \n";
						tablaErr.setStrDescripcion(listExpedienteMovimientoComp.get(m).getStrDescripcionTipoCredito()+" : "+" S/."+ listExpedienteMovimientoComp.get(m).getExpediente().getBdSaldoCredito());
						lstDeudaPendiente.add(tablaErr);
					}
				}
				
				strMsgObservacion = ""; 
				blnCondicionSocioSinDeudaPendiente=Boolean.FALSE;
				chkCondicionSocioSinDeudaPendiente = Boolean.FALSE;
				strMsgCondicionSocioSinDeudaPendiente = "Aún tiene saldos pendientes: \n" + strError;
	
			}else{
				strMsgCondicionSocioSinDeudaPendiente = "";
				blnCondicionSocioSinDeudaPendiente=Boolean.TRUE;
				chkCondicionSocioSinDeudaPendiente = Boolean.TRUE;
			}
			log.info("entra al for tipocreditoempresa: "+intContTipoCreditompresa);
		} catch (BusinessException e) {
			System.out.println("isValidCondicionSocioSinDeudaPendiente ---> "+e);
		}

	}

	
	
	/**
	 * Validacion Nro 2
	 * Valida que el socio no sea garante activo de un credito.Movimiento(Vigente)
	 */
	public void isValidCondicionSinGarantesDeudores(){
		blnCondicionSinGarantesDeudores = true;
		List<GarantiaCredito> listaGarantiaCredito = null;
		Expediente expedienteCreditoMov = null;
		List<CuentaIntegrante> listaCuentaIntegranteSocio= null;
		String strError = "";
		
		List<EstadoExpediente> listaEstados = null;
//		Boolean blnContinua = Boolean.TRUE;
	
		//05.05.2014 jchavez
		lstMsgCondicionSinGarantesDeudores.clear();
		
		try {
			
			strMsgCondicionSinGarantesDeudores = strError;
			setBlnCondicionSinGarantesDeudores(Boolean.TRUE);
			setChkCondicionSinGarantesDeudores(Boolean.TRUE);

				listaGarantiaCredito = solicitudPrestamoFacade.getListaGarantiasPorPkPersona(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), 
																							 beanSocioComp.getPersona().getIntIdPersona());
				if(listaGarantiaCredito != null && !listaGarantiaCredito.isEmpty()){
					for (GarantiaCredito garantiaCredito : listaGarantiaCredito) {

						ExpedienteId expedienteId = new ExpedienteId();
						expedienteId.setIntCuentaPk(garantiaCredito.getId().getIntCuentaPk());
						expedienteId.setIntItemExpediente(garantiaCredito.getId().getIntItemExpediente());
						expedienteId.setIntItemExpedienteDetalle(garantiaCredito.getId().getIntItemDetExpediente());
						expedienteId.setIntPersEmpresaPk(garantiaCredito.getId().getIntPersEmpresaPk());
						
						expedienteCreditoMov = conceptoFacade.getExpedientePorPK(expedienteId);
						
						if(expedienteCreditoMov != null){
							listaEstados = conceptoFacade.getListaPorPkExpedienteCredito(expedienteCreditoMov.getId());
							// PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE = 1;
							// PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_CANCELADO = 2;
							
							if(listaEstados != null && !listaEstados.isEmpty()){
								for (EstadoExpediente estadoExpediente : listaEstados) {
									if(estadoExpediente.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){
										//if(expedienteCreditoMov.getBdSaldoCredito().compareTo(BigDecimal.ZERO)!= 0){
											//--------------------------------------
											CuentaId cuentaIdSocio = new CuentaId();
											Cuenta cuentaSocio = null;
											cuentaIdSocio.setIntPersEmpresaPk(expedienteCreditoMov.getId().getIntPersEmpresaPk());
											cuentaIdSocio.setIntCuenta(expedienteCreditoMov.getId().getIntCuentaPk());
					
											cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
											
											listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());
											Integer intIdPersona = 0;
											Persona persona = null;
											SocioComp socioCompTitPrest = null;
											
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
													socioCompTitPrest = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
															new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
															persona.getDocumento().getStrNumeroIdentidad(),
															Constante.PARAM_EMPRESASESION);
					
													for (SocioEstructura socioEstructura : socioCompTitPrest.getSocio().getListSocioEstructura()) {
														if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
															socioCompTitPrest.getSocio().setSocioEstructura(socioEstructura);
														}
													}
												}
											}
					
											String strCondicion = "";
											String strTipoCondicion = "";
											for(int c=0; c<listaDescCondicionSocio.size();c++){
												if(listaDescCondicionSocio.get(c).getIntIdDetalle().compareTo(socioCompTitPrest.getCuenta().getIntParaCondicionCuentaCod())==0){
													strCondicion = listaDescCondicionSocio.get(c).getStrDescripcion();
												}
											}
											
											for(int c=0; c<listaDescTipoCondicionSocio.size();c++){
												if(listaDescTipoCondicionSocio.get(c).getIntIdDetalle().compareTo(socioCompTitPrest.getCuenta().getIntParaSubCondicionCuentaCod())==0){
													strTipoCondicion = listaDescTipoCondicionSocio.get(c).getStrDescripcion();
												}
											}
											strError =	socioCompTitPrest.getPersona().getIntIdPersona()+" - "+ 
														socioCompTitPrest.getPersona().getNatural().getStrNombres()+" "+
														socioCompTitPrest.getPersona().getNatural().getStrApellidoPaterno()+" "+
														socioCompTitPrest.getPersona().getNatural().getStrApellidoMaterno()+" : "+
														" S/. "+ expedienteCreditoMov.getBdSaldoCredito()+ " - "+ "Condición : "+
														strCondicion +" - "+ strTipoCondicion+".";
											
											Tabla tabla = new Tabla();
											tabla.setStrDescripcion(strError);
											lstMsgCondicionSinGarantesDeudores.add(tabla);
											setBlnCondicionSinGarantesDeudores(Boolean.FALSE);
											setChkCondicionSinGarantesDeudores(Boolean.FALSE);
											//strMsgCondicionSinGarantesDeudores = "";
											
											//break;
										//}
									}
								}

							}
						}
					}

				}else{
					strMsgCondicionSinGarantesDeudores = strError;
					setBlnCondicionSinGarantesDeudores(Boolean.TRUE);
					setChkCondicionSinGarantesDeudores(Boolean.TRUE);
				}
		} catch (BusinessException e) {
			log.error("Error en isValidCondicionSinGarantesDeudores ---> "+e);
			e.printStackTrace();
		}
		strMsgCondicionSinGarantesDeudores = strError;

	}

	
	
	/**
	 * Validacion Nro 3
	 */
	public void isValidCondicionbeneficioDeFondoSepelio(){
		blnCondicionBeneficioFondoSepelio = true;
//		List<ExpedientePrevision> listaExpedientePrevision = null;
//		EstadoPrevision ultimoEstado = null;
//		boolean blnSiTiene = false;
		try {
//			listaExpedientePrevision = previsionFacade.getListaExpedientePrevisionPorCuenta(beanSocioComp.getCuenta());
			blnCondicionBeneficioFondoSepelio = true;
			strMsgCondicionBeneficioFondoSepelio = "";
			chkCondicionBeneficioFondoSepelio = true;
//			blnSiTiene = true;
			
			// reponer
			/*if(listaExpedientePrevision != null && !listaExpedientePrevision.isEmpty()){
				for (ExpedientePrevision expedientePrevision : listaExpedientePrevision) {

					if((expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0)
							//&&(	expedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.CAPTACION_FDO_SEPELIO)==0 ) PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO
							&&(	expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0)){
							ultimoEstado =  previsionFacade.getUltimoEstadoExpedientePrevision(expedientePrevision);
		
							if((ultimoEstado.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0)
								||(ultimoEstado.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0) ){
								blnCondicionBeneficioFondoSepelio = true;
								strMsgCondicionBeneficioFondoSepelio = "";
								chkCondicionBeneficioFondoSepelio = true;
								blnSiTiene = true;
								break;
							}	

						}
				}

				if(!blnSiTiene){
					blnCondicionBeneficioFondoSepelio = false;
					strMsgCondicionBeneficioFondoSepelio = "Observación: El socio no tiene Solicitud de Beneficio de Sepelio por fallecimiento del titular. ";
					chkCondicionBeneficioFondoSepelio = false;
				}
				
			}else{
				blnCondicionBeneficioFondoSepelio = false;
				strMsgCondicionBeneficioFondoSepelio = "Observación: El socio no tiene Solicitud de Beneficio de Sepelio por fallecimiento del titular. ";
				chkCondicionBeneficioFondoSepelio = false;
			}*/
			
			
			
		} catch (Exception e) {
			log.error("Error en isValidCondicionbeneficioDeFondoSepelio ---> "+e);
		}
	}

	
	/**
	 * 
	 * @param event
	 */
	public void mostrarArchivosAdjuntos(ActionEvent event) {
		log.info("----------------------- mostrarArchivosAdjuntos -----------------------------");
		ConfSolicitudFacadeRemote facade = null;
		TablaFacadeRemote tablaFacade = null;
		EstructuraFacadeRemote estructuraFacade = null;
		ConfServSolicitud confServSolicitud = null;
//		String strToday = Constante.sdf.format(new Date());
//		Date dtToday = null;
		List<ConfServSolicitud> listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
		EstructuraDetalle estructuraDet = null;
		List<EstructuraDetalle> listaEstructuraDet = new ArrayList<EstructuraDetalle>();
		listaRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
		RequisitoLiquidacionComp requisitoLiquidacionComp;
		Integer intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_LIQUIDACION;
		//RequisitoCreditoComp requisitoCreditoComp;
//		try {
////			dtToday = Constante.sdf.parse(strToday);
//		} catch (ParseException e1) {
//			e1.printStackTrace();
//		}
		try {
			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			confServSolicitud = new ConfServSolicitud();
			
			confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_LIQUIDACIONDECUENTA);
			confServSolicitud.setIntParaSubtipoOperacionCod(beanExpedienteLiquidacion.getIntParaSubTipoOperacion());
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);

			//listaDocAdjuntos = facade.buscarConfSolicitudRequisito(confServSolicitud, null, dtToday, 1);

			listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, Constante.PARAM_T_TIPOREQAUT_REQUISITO, null);
			if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
				forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {

					// COMENTADO PARA PRUEBAS 
					if (solicitud.getIntParaTipoOperacionCod().equals(intTipoOperacion)) {
							if (solicitud.getIntParaSubtipoOperacionCod().equals(beanExpedienteLiquidacion.getIntParaSubTipoOperacion())) {
								if (solicitud.getListaEstructuraDetalle() != null) {
									
									for (ConfServEstructuraDetalle estructuraDetalle : solicitud.getListaEstructuraDetalle()) {
										estructuraDet = new EstructuraDetalle();
										estructuraDet.setId(new EstructuraDetalleId());
										estructuraDet.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
										estructuraDet.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
										listaEstructuraDet = estructuraFacade.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
														estructuraDet.getId(),beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(),
														beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());										
										
										for (EstructuraDetalle estructuraDetallexxx : listaEstructuraDet) {
											System.out.println("CODIGO"+estructuraDetallexxx.getId().getIntCodigo());
											System.out.println("NIVEL"+estructuraDetallexxx.getId().getIntNivel());
											System.out.println("CASO"+estructuraDetallexxx.getId().getIntCaso());
											System.out.println("ITEM CASO"+estructuraDetallexxx.getId().getIntItemCaso());
										}
										
										if (listaEstructuraDet != null && listaEstructuraDet.size() > 0) {
											for (EstructuraDetalle estructDetalle : listaEstructuraDet) {
												/*
												System.out.println("req aut -----> "+solicitud.getId().getIntItemSolicitud());
												System.out.println("estructuraDetalle.getIntCodigoPk() ---> "+estructuraDetalle.getIntCodigoPk());
												System.out.println("beanSocioComp.getSocio().getSocioEstructura().getIntCodigo() ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
												System.out.println("----------------------------------------------------------------");
												System.out.println("estructuraDetalle.getIntNivelPk() ---> "+estructuraDetalle.getIntNivelPk());
												System.out.println("beanSocioComp.getSocio().getSocioEstructura().getIntNivel() ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
												System.out.println("----------------------------------------------------------------");
												System.out.println("estructuraDetalle.getIntCaso() ---> "+estructuraDetalle.getIntCaso());
												System.out.println("estructDetalle.getId().getIntCaso() ---> "+estructDetalle.getId().getIntCaso());
												System.out.println("----------------------------------------------------------------");
												System.out.println("estructuraDetalle.getIntItemCaso() ---> "+estructuraDetalle.getIntItemCaso());
												System.out.println("estructDetalle.getId().getIntItemCaso() ---> "+estructDetalle.getId().getIntItemCaso());
												System.out.println("----------------------------------------------------------------");
												System.out.println("estructuraDetalle.getIntTipoSocio() ---> "+estructuraDetalle.getIntTipoSocio());
												System.out.println("beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio() ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio());
												System.out.println("----------------------------------------------------------------");
												*/
												if (estructuraDetalle.getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())
													&& estructuraDetalle.getIntNivelPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())
													&& estructuraDetalle.getIntCaso().equals(estructDetalle.getId().getIntCaso())
													&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())) {
													
													System.out.println(" -------------- pasa -----------------  ");
													if (solicitud.getListaDetalle() != null
															&& solicitud.getListaDetalle().size() > 0) {
														
														List<RequisitoLiquidacionComp> listaRequisitoLiquidacionCompTemp = new ArrayList<RequisitoLiquidacionComp>();
														for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
															
															if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId().getIntPersEmpresaPk())
																&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalle.getId().getIntItemSolicitud())) {
																
																requisitoLiquidacionComp = new RequisitoLiquidacionComp();
																requisitoLiquidacionComp.setDetalle(detalle);
																listaRequisitoLiquidacionComp.add(requisitoLiquidacionComp);
																listaRequisitoLiquidacionCompTemp.add(requisitoLiquidacionComp);
															}
														}
														
														
														if(listaRequisitoLiquidacionComp != null && !listaRequisitoLiquidacionComp.isEmpty()){
															List<Tabla> listaTablaDescripcionRequisitos = new ArrayList<Tabla>();															
															listaRequisitoLiquidacionComp.clear();
															
															listaTablaDescripcionRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION),"A");
															for (Tabla tabla : listaTablaDescripcionRequisitos) {
																for (RequisitoLiquidacionComp requisitoComp : listaRequisitoLiquidacionCompTemp) {
																	if(requisitoComp.getDetalle().getIntParaTipoDescripcion().compareTo(tabla.getIntIdDetalle())==0){
																		listaRequisitoLiquidacionComp.add(requisitoComp);
																		blnTieneAdjuntosConfigurados = true;
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
			log.info("PASO EVALUACION: "+blnPostEvaluacion);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 
	 * @param beanExpedienteLiquidacion
	 * @return
	 */
	private Boolean procedeEvaluacion(ExpedienteLiquidacion beanExpedienteLiquidacion) {
		Boolean validProcedeEvaluacion = true;
//		Boolean validPrevisiones = true;
		//intTipoSolicitud = intTipoSolicitudExtra;
		//intSubTipoSolicitud = intSubTipoSolicitudExtra;
		
		if (beanExpedienteLiquidacion.getIntParaSubTipoOperacion() == null || beanExpedienteLiquidacion.getIntParaSubTipoOperacion() == 0) {
			setStrMsgTxtSubOperacion("El campo Tipo de Liquidación debe ser ingresado. ");
			validProcedeEvaluacion = false;
		} else {
			setStrMsgTxtSubOperacion("");
			// Si es fallecimiento titualr y el esatdo del apersona es 2 ().. pasa. Solo este caso
			if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)!=0
				&& beanSocioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_FALLECIDO)==0){
				strMsgTxtProcedeEvaluacion =strMsgTxtProcedeEvaluacion+" El Socio se encuentra en estado DIFUNTO. ";
				validProcedeEvaluacion = false;
			}
	
		}
		
		
		if(!validarExistencaPrevisionesPreviasPorTipo()){
			validProcedeEvaluacion = false;
		}
		
		if(validProcedeEvaluacion){
			if(!validacionFinalOtorgamientoLiquidacion()){
				validProcedeEvaluacion = false;
			}
		}

		return validProcedeEvaluacion;
	}
	
	private Boolean procedeEvaluacion_mod(ExpedienteLiquidacion beanExpedienteLiquidacion) {
		Boolean validProcedeEvaluacion = true;
//		Boolean validPrevisiones = true;
		//intTipoSolicitud = intTipoSolicitudExtra;
		//intSubTipoSolicitud = intSubTipoSolicitudExtra;
		
		if (beanExpedienteLiquidacion.getIntParaSubTipoOperacion() == null || beanExpedienteLiquidacion.getIntParaSubTipoOperacion() == 0) {
			setStrMsgTxtSubOperacion("El campo Tipo de Liquidación debe ser ingresado. ");
			validProcedeEvaluacion = false;
		} else {
			setStrMsgTxtSubOperacion("");
			// Si es fallecimiento titualr y el esatdo del apersona es 2 ().. pasa. Solo este caso
			if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)!=0
				&& beanSocioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_FALLECIDO)==0){
				strMsgTxtProcedeEvaluacion =strMsgTxtProcedeEvaluacion+" El Socio se encuentra en estado DIFUNTO. ";
				validProcedeEvaluacion = false;
			}
	
		}
		
		
		if(!validarExistencaPrevisionesPreviasPorTipo()){
			validProcedeEvaluacion = false;
		}
		
		if(validProcedeEvaluacion){
			if(!validacionFinalOtorgamientoLiquidacion2()){
				validProcedeEvaluacion = false;
			}
		}

		return validProcedeEvaluacion;
	}
	/**
	 * 
	 * @param socioComp
	 */
	public void listarBeneficiariosDeSocio(SocioComp socioComp){
		log.info("-------------------------------------Debugging listarBeneficiariosDeSocio-------------------------------------");
		//PersonaEmpresaPK personaEmpresaPk = null;
		PersonaFacadeRemote personaFacade = null;
		//VinculoFacadeRemote vinculoFacade = null;
		List<Vinculo> vinculos = null;
		try {
			if(socioComp != null){
				// si es fallecimiento se agregan todos los beneficiarios
				
				intSubTipoOperacion = beanExpedienteLiquidacion.getIntParaSubTipoOperacion();
				if(intSubTipoOperacion.compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
				
					personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
					Integer intIdPersona = socioComp.getSocio().getSocioEstructura().getId().getIntIdPersona();
					Integer intIdEmpresa = socioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa();
					PersonaEmpresaPK personaPk = new PersonaEmpresaPK();
					personaPk.setIntIdEmpresa(intIdEmpresa);
					personaPk.setIntIdPersona(intIdPersona);
					
					vinculos = personaFacade.getVinculoPorPk(personaPk);
					
					if(!vinculos.isEmpty()  || vinculos != null ){
						
						for(int k=0; k<vinculos.size();k++){
							Integer intIdPersonaVinc = vinculos.get(k).getIntPersonaVinculo();
							BeneficiarioLiquidacion beneficiarioLiquidacion = new BeneficiarioLiquidacion();
							Persona persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersonaVinc);
							if (persona != null) {
								if(persona.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
									if (persona.getListaDocumento() != null
											&& persona.getListaDocumento().size() > 0) {
										for (Documento documento : persona.getListaDocumento()) {
											if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
												persona.setDocumento(documento);
												break;
											}
										}
									}
									beneficiarioLiquidacion.setPersona(persona);
									beneficiarioLiquidacion.setIntItemViculo(vinculos.get(k).getIntItemVinculo());
									beneficiarioLiquidacion.setIntTipoViculo(vinculos.get(k).getIntTipoVinculoCod());
									//beneficiarioPrevision.setIntItemViculo(vinculos.get(k).getIntTipoVinculoCod());
									//beneficiarioPrevision.setIntPersPersonaBeneficiario(persona.getIntIdPersona());
									beneficiarioLiquidacion.setIntPersPersonaBeneficiario(vinculos.get(k).getIntPersonaVinculo());
									listaBeneficiarioLiquidacionBusq.add(beneficiarioLiquidacion);	
								}

							}
						}
					}
				}else{
					// sino se carga al mismo socio
					if(beanSocioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						BeneficiarioLiquidacion beneficiarioLiquidacion = new BeneficiarioLiquidacion();
						beneficiarioLiquidacion.setPersona(beanSocioComp.getPersona());
						beneficiarioLiquidacion.setIntItemViculo(null);
						beneficiarioLiquidacion.setIntTipoViculo(null);
						beneficiarioLiquidacion.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
						beneficiarioLiquidacion.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
						listaBeneficiarioLiquidacionBusq.add(beneficiarioLiquidacion);	
					}
					
				}
			}

		} catch (EJBFactoryException e) {
			log.info("Err--> "+e);
			
		} catch (BusinessException e1) {
			log.info("Err--> "+e1);
		}
	}
	
	
	
	/**
	 * Recupera los datos de PERSONA de beneficiario, nombres, documento y relacion.
	 * @param beneficiarioVista
	 * @param vinculos
	 * @return
	 */
	public BeneficiarioLiquidacion recuperarBeneficiarioPorVinculo(BeneficiarioLiquidacion beneficiarioVista, List<Vinculo> vinculos){
		BeneficiarioLiquidacion beneficiarioLiquidacion = null;
		try {
			
			if(beanSocioComp != null){
					if(!vinculos.isEmpty()  || vinculos != null ){
						
						for(int k=0; k<vinculos.size();k++){
							Integer intIdPersonaVinc = vinculos.get(k).getIntPersonaVinculo();
							if(beneficiarioVista.getIntItemViculo().compareTo(vinculos.get(k).getIntItemVinculo())==0){
								beneficiarioLiquidacion = beneficiarioVista;
								Persona persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersonaVinc);
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
									beneficiarioLiquidacion.setPersona(persona);
									beneficiarioLiquidacion.setIntItemViculo(vinculos.get(k).getIntItemVinculo());
									beneficiarioLiquidacion.setIntTipoViculo(vinculos.get(k).getIntTipoVinculoCod());
									//beneficiarioPrevision.setIntItemViculo(vinculos.get(k).getIntTipoVinculoCod());
									//beneficiarioPrevision.setIntPersPersonaBeneficiario(persona.getIntIdPersona());
									beneficiarioLiquidacion.setIntPersPersonaBeneficiario(vinculos.get(k).getIntPersonaVinculo());
									break;	
								}	
							}
						}
					}
			}
			
			
		} catch (Exception e) {
			log.error("Error en recuperarBeneficiarioPorVinculo --> "+e);
		}
		 return beneficiarioLiquidacion;
		
		
	}
	
	/**
	 * 
	 * @param beneficiarioVista
	 * @return
	 */
	public BeneficiarioLiquidacion recuperarBeneficiarioPorVinculo_Titular(BeneficiarioLiquidacion beneficiarioVista){
		BeneficiarioLiquidacion beneficiarioLiquidacion = null;
		try {
			
			if(beanSocioComp != null){
				beneficiarioLiquidacion = beneficiarioVista;
				beneficiarioLiquidacion.setPersona(beanSocioComp.getPersona());
				beneficiarioLiquidacion.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
					}

		} catch (Exception e) {
			log.error("Error en recuperarBeneficiarioPorVinculo --> "+e);
		}
		 return beneficiarioLiquidacion;
		
		
	}
	
	/**
	 * 
	 * @param event
	 */
	public void loadMotivoRenuncia(ActionEvent event) {
//		TablaFacadeRemote facade = null;
		String strpIntSubTipoOperacion = null;
		Integer intpIntSubTipoOperacion = null;
//		List<Tabla> listaDocumento = null;
		
		Integer intCorrespondePrevision;
		blnCorrespondeLiquidacion = true;
		strMsgTxtParaMotivoRenuncia = "";
		
		strpIntSubTipoOperacion = getRequestParameter("pIntSubTipoOperacion");
		intpIntSubTipoOperacion = new Integer(strpIntSubTipoOperacion);

		try {
			if (!intpIntSubTipoOperacion.equals("0")) {

				if (intpIntSubTipoOperacion.compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA) == 0) {
					blnIsRenuncia = true;
				} else {
					blnIsRenuncia = false;
				}
				//Autor: jchavez / Tarea: Nueva validación / Fecha: 09.12.2014
				if (intpIntSubTipoOperacion.compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO) == 0) {
					intCorrespondePrevision = liquidacionFacade.getCorrespondePrevision(SESION_IDEMPRESA, beanSocioComp.getCuenta().getId().getIntCuenta());
					//Si le corresponde prevision, no procede la liquidación
					if (intCorrespondePrevision == 1) {
						blnCorrespondeLiquidacion = false;
						strMsgTxtParaMotivoRenuncia = "NO se puede liquidar dado que al socio le corresponde Previsión.";
					}
					blnIsFallecimiento = true;
				} else {
					blnIsFallecimiento = false;
				}
				//Fin jchavez - 10.12.2014
			}
		} catch (Exception e) {
			log.error("Error en loadMotivoRenuncia --> "+e);
		}
	} 
	
	/**
	 * 
	 */
	public void loadFechaRenuncia() {
		log.info("-------------------------------------Debugging loadFechaRenuncia-------------------------------------");
			//System.out.println(getRequestParameter("pdtFechaRenuncia"));
			Date dtFechaRecpecion;
			int days = 180;
				//strFechaRenuncia
				DateFormat formatter;
				Date dateRenuncia;
				formatter = new SimpleDateFormat("dd/MM/yyyy");

				try {

					if(beanExpedienteLiquidacion.getDtFechaRecepcion()	 == null){
						setStrMsgTxtFechaRecepcion("El campo Fecha de Recepción no puede ser nulo. ");
					}else{
						setStrMsgTxtFechaRecepcion("");
						dateRenuncia = (Date) formatter.parse(strFechaRenuncia);

						dtFechaRecpecion= beanExpedienteLiquidacion.getDtFechaRecepcion();
						if(dtFechaRecpecion.before(dateRenuncia)){
							strMsgTxtFechaRenuncia = "La fecha de Recepción no puede ser anterior a la Renuncia(*).";
							strMsgTxtAsterisco = "(*)";
						}else{
							strMsgTxtFechaRenuncia = "";
							strMsgTxtAsterisco = "";
						}
						
						beanExpedienteLiquidacion.setDtFechaProgramacion(addDaysToDate(dtFechaRecpecion, days));
						strFechaProgramacionPago =  Constante.sdf.format(addDaysToDate(dtFechaRecpecion, days));
					}

			} catch (Exception e) {
				log.error("Error en loadFechaRenuncia --> "+e);
			}
	}
	
	
	
	/**
	 * 
	 */
	public void buscarBeneficiarioFiltro(){
		log.info("-------------------------------------Debugging buscarBeneficiario-------------------------------------");
		listaBeneficiarioLiquidacionBusq.clear();
		listaBeneficiarioLiquidacionBusq = new ArrayList<BeneficiarioLiquidacion>();
		listarBeneficiariosDeSocioFiltro(beanSocioComp);
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void buscarBeneficiario(ActionEvent event){
		log.info("-------------------------------------Debugging buscarBeneficiario-------------------------------------");
		listaBeneficiarioLiquidacionBusq.clear();
		listaBeneficiarioLiquidacionBusq = new ArrayList<BeneficiarioLiquidacion>();
		listarBeneficiariosDeSocio(beanSocioComp);
	}
	
	
	/**
	 * 
	 * @param socioComp
	 */
	public void listarBeneficiariosDeSocioFiltro(SocioComp socioComp){
		log.info("-------------------------------------Debugging listarBeneficiariosDeSocio-------------------------------------");
		//PersonaEmpresaPK personaEmpresaPk = null;
		PersonaFacadeRemote personaFacade = null;
//		VinculoFacadeRemote vinculoFacade = null;
		List<Vinculo> vinculos = null;
		try {
			if(socioComp != null){
				//----------------------------------------->
					if(intSubTipoOperacion.compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
						personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
						Integer intIdPersona = socioComp.getSocio().getSocioEstructura().getId().getIntIdPersona();
						Integer intIdEmpresa = socioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa();
						PersonaEmpresaPK personaPk = new PersonaEmpresaPK();
						personaPk.setIntIdEmpresa(intIdEmpresa);
						personaPk.setIntIdPersona(intIdPersona);
						
						vinculos = personaFacade.getVinculoPorPk(personaPk);
						
						if(!vinculos.isEmpty()  || vinculos != null ){
							
							for(int k=0; k<vinculos.size();k++){
								Integer intIdPersonaVinc = vinculos.get(k).getIntPersonaVinculo();
								BeneficiarioLiquidacion beneficiarioLiquidacion = new BeneficiarioLiquidacion();
								Persona persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersonaVinc);
								if (persona != null) {
									if(persona.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
										if (persona.getListaDocumento() != null
												&& persona.getListaDocumento().size() > 0) {
											for (Documento documento : persona.getListaDocumento()) {
												if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
													persona.setDocumento(documento);
													break;
												}
											}
										}

										if((persona.getNatural().getStrApellidoMaterno().toUpperCase().contains(campoBuscarBeneficiario.toUpperCase())) 
												||(persona.getNatural().getStrApellidoPaterno().toUpperCase().contains(campoBuscarBeneficiario.toUpperCase()))
												||(persona.getNatural().getStrNombres().toUpperCase().contains(campoBuscarBeneficiario.toUpperCase()))
												||(persona.getDocumento().getStrNumeroIdentidad().toUpperCase().contains(campoBuscarBeneficiario.toUpperCase()))
										){
												///modificacion rara
											beneficiarioLiquidacion.setPersona(persona);
											beneficiarioLiquidacion.setIntItemViculo(vinculos.get(k).getIntItemVinculo());
											beneficiarioLiquidacion.setIntTipoViculo(vinculos.get(k).getIntTipoVinculoCod());
											//beneficiarioPrevision.setIntItemViculo(vinculos.get(k).getIntTipoVinculoCod());
											//beneficiarioPrevision.setIntPersPersonaBeneficiario(persona.getIntIdPersona());
											beneficiarioLiquidacion.setIntPersPersonaBeneficiario(vinculos.get(k).getIntPersonaVinculo());
											listaBeneficiarioLiquidacionBusq.add(beneficiarioLiquidacion);	
										}
									}	
								}
							}
						}
					} else {
						if(beanSocioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							BeneficiarioLiquidacion beneficiarioLiquidacion = new BeneficiarioLiquidacion();
							beneficiarioLiquidacion.setPersona(beanSocioComp.getPersona());
							beneficiarioLiquidacion.setIntItemViculo(null);
							beneficiarioLiquidacion.setIntTipoViculo(null);
							beneficiarioLiquidacion.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
							listaBeneficiarioLiquidacionBusq.add(beneficiarioLiquidacion);	
						}
					}

				System.out.println("TAMAÑO DE BENEFICIARIOS ---> "+ listaBeneficiarioLiquidacionBusq.size());
			}

			
		} catch (EJBFactoryException e) {
			log.info("Error en listarBeneficiariosDeSocioFiltro 1 --> "+e);
			
		} catch (BusinessException e1) {
			log.info("Error en listarBeneficiariosDeSocioFiltro 2 --> "+e1);
		}
	}
	
	

	
	/**
	 * Agrega lista de beneficiarios a cada detalle segun grilla de beneficiarios visual, en donde recupera % de los beneficiaios
	 * @param event
	 */
	public List<ExpedienteLiquidacionDetalle> agregarBeneficiarioSocio (){
		
		List<ExpedienteLiquidacionDetalle> listaDetalles = null;
		List<ExpedienteLiquidacionDetalle> ListaDetallesCargados = null;
		try {
			
			
			if(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle() != null
				&& !beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()){
				listaDetalles = new ArrayList<ExpedienteLiquidacionDetalle>();
				listaDetalles = beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle();
				ListaDetallesCargados = new ArrayList<ExpedienteLiquidacionDetalle>();
				//ListaDetallesCargados = new ArrayList<ExpedienteLiquidacionDetalle>();
				ExpedienteLiquidacionDetalle detalleCargadoAporte =null;
				ExpedienteLiquidacionDetalle detalleCargadoRetiro =null;
				
				for (ExpedienteLiquidacionDetalle liquidacionDetalle : listaDetalles) {
					
					
					// si es nulo es xq es una edicion.
					// Lo recuperamos de los bean aporte o retiro
					if(liquidacionDetalle.getCuentaConcepto() == null){
						if(liquidacionDetalle.getId().getIntCuenta().compareTo(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntCuentaPk())==0
						&&	liquidacionDetalle.getId().getIntPersEmpresa().compareTo(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntPersEmpresaPk())==0
						&&liquidacionDetalle.getId().getIntItemCuentaConcepto().compareTo(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntItemCuentaConcepto())==0
						){
							liquidacionDetalle.setCuentaConcepto(beanCuentaConceptoAportes.getCuentaConcepto());
						}
						
						if(liquidacionDetalle.getId().getIntCuenta().compareTo(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntCuentaPk())==0
							&&	liquidacionDetalle.getId().getIntPersEmpresa().compareTo(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntPersEmpresaPk())==0
							&&liquidacionDetalle.getId().getIntItemCuentaConcepto().compareTo(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntItemCuentaConcepto())==0
							){
								liquidacionDetalle.setCuentaConcepto(beanCuentaConceptoRetiro.getCuentaConcepto());
							}
						
						
					}
					
					CuentaConcepto cuentaCto = null;
					cuentaCto = liquidacionDetalle.getCuentaConcepto();
					
					if(cuentaCto.getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0
						&& beanCuentaConceptoAportes != null){
						detalleCargadoAporte = new ExpedienteLiquidacionDetalle();
						detalleCargadoAporte = liquidacionDetalle;
						
						List<BeneficiarioLiquidacion> listaBeneficiariosAportes = new ArrayList<BeneficiarioLiquidacion>();
						
						for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
							
							BeneficiarioLiquidacion beneficiarioAporte = new BeneficiarioLiquidacion();
							beneficiarioAporte.setIntItemViculo(beneficiarioVista.getIntItemViculo());
							beneficiarioAporte.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							beneficiarioAporte.setBdPorcentajeBeneficioApo(beneficiarioVista.getBdPorcentajeBeneficioApo());
							beneficiarioAporte.setBdPorcentajeBeneficio(beneficiarioVista.getBdPorcentajeBeneficioApo());
							beneficiarioAporte.setIntPersPersonaBeneficiario(beneficiarioVista.getIntPersPersonaBeneficiario());
							
							
							/*if(beneficiarioVista.getId() != null) {
								beneficiarioAporte.setId(new BeneficiarioLiquidacionId());
								beneficiarioAporte.getId().setIntCuenta(beneficiarioVista.getId().getIntCuenta());
								beneficiarioAporte.getId().setIntItemBeneficiario(beneficiarioVista.getId().getIntItemBeneficiario());
								beneficiarioAporte.getId().setIntItemCuentaConcepto(beneficiarioVista.getId().getIntItemCuentaConcepto());
								beneficiarioAporte.getId().setIntItemExpediente(beneficiarioVista.getId().getIntItemExpediente());
								beneficiarioAporte.getId().setIntPersEmpresa(beneficiarioVista.getId().getIntPersEmpresa());
								beneficiarioAporte.getId().setIntPersEmpresaLiquidacion(beneficiarioVista.getId().getIntPersEmpresaLiquidacion());
							}*/
							listaBeneficiariosAportes.add(beneficiarioAporte);
						}
						detalleCargadoAporte.setListaBeneficiarioLiquidacion(listaBeneficiariosAportes);
						ListaDetallesCargados.add(detalleCargadoAporte);
					}
					
					if(cuentaCto.getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0
						&& beanCuentaConceptoRetiro != null){
							
						detalleCargadoRetiro = new ExpedienteLiquidacionDetalle();
						detalleCargadoRetiro = liquidacionDetalle;
						List<BeneficiarioLiquidacion> listaBeneficiariosRetiro = new ArrayList<BeneficiarioLiquidacion>();
							
							for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
								BeneficiarioLiquidacion beneficiarioRetiro = new BeneficiarioLiquidacion();
								beneficiarioRetiro.setIntItemViculo(beneficiarioVista.getIntItemViculo());
								beneficiarioRetiro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								beneficiarioRetiro.setBdPorcentajeBeneficioRet(beneficiarioVista.getBdPorcentajeBeneficioRet());
								beneficiarioRetiro.setBdPorcentajeBeneficio(beneficiarioVista.getBdPorcentajeBeneficioRet());
								beneficiarioRetiro.setIntPersPersonaBeneficiario(beneficiarioVista.getIntPersPersonaBeneficiario());
								
								/*if(beneficiarioVista.getId() != null) {
									beneficiarioRetiro.setId(new BeneficiarioLiquidacionId());
									beneficiarioRetiro.getId().setIntCuenta(beneficiarioVista.getId().getIntCuenta());
									beneficiarioRetiro.getId().setIntItemBeneficiario(beneficiarioVista.getId().getIntItemBeneficiario());
									beneficiarioRetiro.getId().setIntItemCuentaConcepto(beneficiarioVista.getId().getIntItemCuentaConcepto());
									beneficiarioRetiro.getId().setIntItemExpediente(beneficiarioVista.getId().getIntItemExpediente());
									beneficiarioRetiro.getId().setIntPersEmpresa(beneficiarioVista.getId().getIntPersEmpresa());
									beneficiarioRetiro.getId().setIntPersEmpresaLiquidacion(beneficiarioVista.getId().getIntPersEmpresaLiquidacion());
								}*/
								
								listaBeneficiariosRetiro.add(beneficiarioRetiro);
							}
							detalleCargadoRetiro.setListaBeneficiarioLiquidacion(listaBeneficiariosRetiro);
							ListaDetallesCargados.add(detalleCargadoRetiro);
					}
	
				}
	
			}

			
		} catch (Exception e) {
			log.error("Error en agregarBeneficiarioSocio ---> "+e);
		}
		
		return ListaDetallesCargados;
		
	}
	
public List<ExpedienteLiquidacionDetalle> agregarBeneficiarioSocio_Titular (){
		
		List<ExpedienteLiquidacionDetalle> listaDetalles = null;
		List<ExpedienteLiquidacionDetalle> ListaDetallesCargados = null;
		try {

			if(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle() != null
				&& !beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()){
				listaDetalles = new ArrayList<ExpedienteLiquidacionDetalle>();
				listaDetalles = beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle();
				ListaDetallesCargados = new ArrayList<ExpedienteLiquidacionDetalle>();
				//ListaDetallesCargados = new ArrayList<ExpedienteLiquidacionDetalle>();
				ExpedienteLiquidacionDetalle detalleCargadoAporte =null;
				ExpedienteLiquidacionDetalle detalleCargadoRetiro =null;
				
				for (ExpedienteLiquidacionDetalle liquidacionDetalle : listaDetalles) {
					
					
					// si es nulo es xq es una edicion.
					// Lo recuperamos de los bean aporte o retiro
					if(liquidacionDetalle.getCuentaConcepto() == null){
						if(liquidacionDetalle.getId().getIntCuenta().compareTo(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntCuentaPk())==0
						&&	liquidacionDetalle.getId().getIntPersEmpresa().compareTo(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntPersEmpresaPk())==0
						&&liquidacionDetalle.getId().getIntItemCuentaConcepto().compareTo(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntItemCuentaConcepto())==0
						){
							liquidacionDetalle.setCuentaConcepto(beanCuentaConceptoAportes.getCuentaConcepto());
						}
						
						if(liquidacionDetalle.getId().getIntCuenta().compareTo(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntCuentaPk())==0
							&&	liquidacionDetalle.getId().getIntPersEmpresa().compareTo(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntPersEmpresaPk())==0
							&&liquidacionDetalle.getId().getIntItemCuentaConcepto().compareTo(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntItemCuentaConcepto())==0
							){
								liquidacionDetalle.setCuentaConcepto(beanCuentaConceptoRetiro.getCuentaConcepto());
							}
						
						
					}
					
					CuentaConcepto cuentaCto = null;
					cuentaCto = liquidacionDetalle.getCuentaConcepto();
					
					if(cuentaCto.getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0
						&& beanCuentaConceptoAportes != null){
						detalleCargadoAporte = new ExpedienteLiquidacionDetalle();
						detalleCargadoAporte = liquidacionDetalle;
						
						List<BeneficiarioLiquidacion> listaBeneficiariosAportes = new ArrayList<BeneficiarioLiquidacion>();
						
						for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
							
							BeneficiarioLiquidacion beneficiarioAporte = new BeneficiarioLiquidacion();
							//beneficiarioAporte.setIntItemViculo(beneficiarioVista.getIntItemViculo());
							beneficiarioAporte.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
							beneficiarioAporte.setBdPorcentajeBeneficioApo(beneficiarioVista.getBdPorcentajeBeneficioApo());
							beneficiarioAporte.setBdPorcentajeBeneficio(beneficiarioVista.getBdPorcentajeBeneficioApo());
							beneficiarioAporte.setIntPersPersonaBeneficiario(beneficiarioVista.getIntPersPersonaBeneficiario());
							
							listaBeneficiariosAportes.add(beneficiarioAporte);
						}
						detalleCargadoAporte.setListaBeneficiarioLiquidacion(listaBeneficiariosAportes);
						ListaDetallesCargados.add(detalleCargadoAporte);
					}
					
					if(cuentaCto.getId().getIntItemCuentaConcepto().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0
						&& beanCuentaConceptoRetiro != null){
							
						detalleCargadoRetiro = new ExpedienteLiquidacionDetalle();
						detalleCargadoRetiro = liquidacionDetalle;
						List<BeneficiarioLiquidacion> listaBeneficiariosRetiro = new ArrayList<BeneficiarioLiquidacion>();
							
							for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
								BeneficiarioLiquidacion beneficiarioRetiro = new BeneficiarioLiquidacion();
								//beneficiarioRetiro.setIntItemViculo(beneficiarioVista.getIntItemViculo());
								beneficiarioRetiro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
								beneficiarioRetiro.setBdPorcentajeBeneficioRet(beneficiarioVista.getBdPorcentajeBeneficioRet());
								beneficiarioRetiro.setBdPorcentajeBeneficio(beneficiarioVista.getBdPorcentajeBeneficioRet());
								beneficiarioRetiro.setIntPersPersonaBeneficiario(beneficiarioVista.getIntPersPersonaBeneficiario());
																
								listaBeneficiariosRetiro.add(beneficiarioRetiro);
							}
							detalleCargadoRetiro.setListaBeneficiarioLiquidacion(listaBeneficiariosRetiro);
							ListaDetallesCargados.add(detalleCargadoRetiro);
					}
	
				}
	
			}

			
		} catch (Exception e) {
			log.error("Error en agregarBeneficiarioSocio ---> "+e);
		}
		
		return ListaDetallesCargados;
		
	}
	
	
	
	
	/**
	 * Se agrega el beneficiario a la grilla de beneficiarios (Solo modo visual).
	 * @param event
	 */
	public void agregarBeneficiarioSocioVisual_Titular(ActionEvent event) {
		Boolean blnSeAgregaraBenefiaciario = Boolean.TRUE;
		List<ExpedienteLiquidacionDetalle> listaDetalles = null;
		try {
			if(beneficiarioSeleccionado != null){	
				if(listaBeneficiarioLiquidacionVista == null || listaBeneficiarioLiquidacionVista.isEmpty()){
					
					blnSeAgregaraBenefiaciario = existeBeneficiario();

					if(blnSeAgregaraBenefiaciario){
						BeneficiarioLiquidacion beneficiario = new BeneficiarioLiquidacion();
						BeneficiarioLiquidacionId beneficiarioId = new BeneficiarioLiquidacionId();
						
						beneficiario.setId(beneficiarioId);
						beneficiario.getId().setIntPersEmpresaLiquidacion(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
						beneficiario.getId().setIntItemExpediente(beanExpedienteLiquidacion.getId().getIntItemExpediente());
						//beneficiario.setIntItemViculo(beneficiarioSeleccionado.getIntItemViculo());
						beneficiario.setIntTipoViculo(beneficiarioSeleccionado.getIntTipoViculo());
						beneficiario.setIntParaEstado(beneficiarioSeleccionado.getPersona().getIntEstadoCod());
						beneficiario.setIntPersPersonaBeneficiario(beneficiarioSeleccionado.getIntPersPersonaBeneficiario());
						beneficiario.setPersona(beneficiarioSeleccionado.getPersona());
						listaBeneficiarioLiquidacionVista.add(beneficiario);	
					}
					
					
				}
				
				listaBeneficiarioLiquidacionVista = calcularMontosPorcentajeBeneficiarios_Titular();

				calcularBeneficiarioTotales();
				validacionSuman100();
				
				
				listaDetalles = agregarBeneficiarioSocio_Titular();
				if(listaDetalles != null && !listaDetalles.isEmpty()){
					beanExpedienteLiquidacion.setListaExpedienteLiquidacionDetalle(listaDetalles);
				}
			}
			
		} catch (Exception e) {
			log.error("Error en agregarBeneficiarioSocioVisual ---> " + e);
			e.printStackTrace();
			
		} 
	}
	
	
	
	public void agregarBeneficiarioSocioVisual(ActionEvent event) {
		try {
			
			//PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA = 1;
			//public static final Integer PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_EXPULSION = 2;
			//public static final Integer PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO = 3;
			
			
			if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0) {
				agregarBeneficiarioSocioVisual_Fallec(event);
			}else {
				agregarBeneficiarioSocioVisual_Titular(event);
			}
		} catch (Exception e) {
			log.error("Error en agregarBeneficiarioSocioVisual ---> "+e);
		}
	}
	
	/**
	 * Se agrega el beneficiario a la grilla de beneficiarios (Solo modo visual).
	 * @param event
	 */
	public void agregarBeneficiarioSocioVisual_Fallec(ActionEvent event) {
		Boolean blnSeAgregaraBenefiaciario = Boolean.TRUE;
		List<ExpedienteLiquidacionDetalle> listaDetalles = null;
		try {
			if(beneficiarioSeleccionado != null){	
				if(listaBeneficiarioLiquidacionVista != null && !listaBeneficiarioLiquidacionVista.isEmpty()){
					
					blnSeAgregaraBenefiaciario = existeBeneficiario();

					if(blnSeAgregaraBenefiaciario){
						BeneficiarioLiquidacion beneficiario = new BeneficiarioLiquidacion();
						BeneficiarioLiquidacionId beneficiarioId = new BeneficiarioLiquidacionId();
						
						beneficiario.setId(beneficiarioId);
						beneficiario.getId().setIntPersEmpresaLiquidacion(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
						beneficiario.getId().setIntItemExpediente(beanExpedienteLiquidacion.getId().getIntItemExpediente());
						beneficiario.setIntItemViculo(beneficiarioSeleccionado.getIntItemViculo());
						beneficiario.setIntTipoViculo(beneficiarioSeleccionado.getIntTipoViculo());
						beneficiario.setIntParaEstado(beneficiarioSeleccionado.getPersona().getIntEstadoCod());
						beneficiario.setIntPersPersonaBeneficiario(beneficiarioSeleccionado.getIntPersPersonaBeneficiario());
						beneficiario.setPersona(beneficiarioSeleccionado.getPersona());
						listaBeneficiarioLiquidacionVista.add(beneficiario);	
					}
					
					
				} else{
					listaBeneficiarioLiquidacionVista = new ArrayList<BeneficiarioLiquidacion>();
						BeneficiarioLiquidacion beneficiario = new BeneficiarioLiquidacion();
						BeneficiarioLiquidacionId beneficiarioId = new BeneficiarioLiquidacionId();
						beneficiario.setId(beneficiarioId);
						beneficiario.getId().setIntPersEmpresaLiquidacion(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
						beneficiario.getId().setIntItemExpediente(beanExpedienteLiquidacion.getId().getIntItemExpediente());
						beneficiario.setIntItemViculo(beneficiarioSeleccionado.getIntItemViculo());
						beneficiario.setIntTipoViculo(beneficiarioSeleccionado.getIntTipoViculo());
						beneficiario.setIntParaEstado(beneficiarioSeleccionado.getPersona().getIntEstadoCod());
						beneficiario.setIntPersPersonaBeneficiario(beneficiarioSeleccionado.getIntPersPersonaBeneficiario());
						beneficiario.setPersona(beneficiarioSeleccionado.getPersona());
						listaBeneficiarioLiquidacionVista.add(beneficiario);
					
				}
				
				listaBeneficiarioLiquidacionVista = calcularMontosPorcentajeBeneficiarios();
				calcularBeneficiarioTotales();
				validacionSuman100();

				listaDetalles = agregarBeneficiarioSocio();
				if(listaDetalles != null && !listaDetalles.isEmpty()){
					beanExpedienteLiquidacion.setListaExpedienteLiquidacionDetalle(listaDetalles);
				}
			}
			
		} catch (Exception e) {
			log.error("Error en agregarBeneficiarioSocioVisual ---> " + e);
			e.printStackTrace();
			
		} 
	}
	
	
	
	/**
	 * Remueve un registro de la grilla de beneficiarios
	 * @param event
	 */
	public void removerBeneficiarioLiquidacion(ActionEvent event) {
		String rowKey = getRequestParameter("rowKeyBeneficiarioLiquidacion");
		try {
			if (listaBeneficiarioLiquidacionVista != null && !listaBeneficiarioLiquidacionVista.isEmpty()) {
				for (int i = 0; i < listaBeneficiarioLiquidacionVista.size(); i++) {
					if (Integer.parseInt(rowKey) == i) {
						BeneficiarioLiquidacion beneficiarioTemp = listaBeneficiarioLiquidacionVista.get(i);
						beneficiarioTemp = listaBeneficiarioLiquidacionVista.get(i);
						beneficiarioTemp.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						
						listaBeneficiarioLiquidacionVista.remove(i);
						break;
					}
				}
			}
			// recalcumos los valores de la grilla de totales
			//reCalcularMontosGrillaBeneficiarios(event);
			calcularBeneficiarioTotales();
			validacionSuman100();
			
		} catch (Exception e) {
			log.error("Error Exception en removerBeneficiarioLiquidacion ---> " + e);
		}
	}
	
	/**
	 * Valida la existencia del beneficiario seleccionado en la grilla de beneficairios.
	 * Devuleve TRUE si no existe. FALSE si ya existe y no se puede agregar.
	 * @return
	 */
	public Boolean existeBeneficiario(){
		Boolean blnAgregar = Boolean.TRUE;
		if(beneficiarioSeleccionado != null){

			for (BeneficiarioLiquidacion beneficiarioLiq : listaBeneficiarioLiquidacionVista) {
				if(beneficiarioLiq.getIntPersPersonaBeneficiario().compareTo(beneficiarioSeleccionado.getIntPersPersonaBeneficiario())==0){
					blnAgregar = Boolean.FALSE;
					break;
				}
			}
		}
		
		return blnAgregar;
	}
	
	
	
	/**
	 * 
	 * @param beanExpedienteLiquidacion
	 * @return
	 */
	private Boolean isValidoExpedienteLiquidacion(ExpedienteLiquidacion beanExpedienteLiquidacion) {
		Boolean validExpedienteLiquidacion = true;

		// Evaluando campos de solicitud
		//---------------------------------
		/*if(beanExpedienteLiquidacion.getDtFechaRenuncia() == null){
			validExpedienteLiquidacion = false;
			setStrMsgTxtFechaRenuncia("El campo Fecha de Renuncia no puede ser nulo. ");
		}else{
			validExpedienteLiquidacion = true;
			setStrMsgTxtFechaRenuncia("");
		}*/
		
		if(beanExpedienteLiquidacion.getDtFechaRecepcion()	 == null){
			validExpedienteLiquidacion = false;	
			setStrMsgTxtFechaRecepcion("El campo Fecha de Recepción no puede ser nulo. ");
		}else{
			validExpedienteLiquidacion = true;	
			setStrMsgTxtFechaRecepcion("");
		}
		
		if(beanExpedienteLiquidacion.getDtFechaProgramacion() == null || strFechaProgramacionPago.isEmpty()){
			validExpedienteLiquidacion = false;
			setStrMsgTxtFechaProgramacion("El campo Fecha de Programación no puede ser nulo. Ingresar Fecha de Recepción. ");
		}else{
			validExpedienteLiquidacion = true;
			setStrMsgTxtFechaProgramacion("");
		}
		
		if(beanExpedienteLiquidacion.getBdMontoBrutoLiquidacion() == null){
			validExpedienteLiquidacion = false;
			setStrMsgTxtMontoBrutoLiquidacion("El campo Monto Total no puede ser nulo. ");
		}else{
			validExpedienteLiquidacion = true;
			setStrMsgTxtMontoBrutoLiquidacion("");
		}
		/*if(beanExpedienteLiquidacion.getIntPeriodoUltimoDescuento() == null){
			validExpedienteLiquidacion = false;
			setStrMsgTxttPeriodoUltimoDescuento("El campo de Periodo de descuento no puede ser nulo. ");
		}else{
			validExpedienteLiquidacion = true;
			setStrMsgTxttPeriodoUltimoDescuento("");
		}*/
		
		if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA)==0){
			if(beanExpedienteLiquidacion.getIntParaMotivoRenuncia()== null || beanExpedienteLiquidacion.getIntParaMotivoRenuncia()==0){
				validExpedienteLiquidacion = false;
				setStrMsgTxtParaMotivoRenuncia("El campo Motivo de Renuncia debe ser ingresado.");
			}else{
				validExpedienteLiquidacion = true;
				setStrMsgTxtParaMotivoRenuncia("");
			}
		}
		
		if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
			Integer intNrofallecidos = listaBeneficiarioLiquidacionVista.size();
			if(intNrofallecidos >0){
				setStrMsgTxtTieneBeneficiarios("");
				validExpedienteLiquidacion = true;	
			}else{
				setStrMsgTxtTieneBeneficiarios("Se debe de registrar al menos un Beneficairio. ");
				validExpedienteLiquidacion = false;
			}
		}
		
		
		
		
		// Validacion Renuncia
		if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA)==0){
			if(chkCondicionSocioSinDeudaPendiente.compareTo(Boolean.TRUE)==0 && chkCondicionSinGarantesDeudores.compareTo(Boolean.TRUE)==0){
				strMsgTxtCondiciones="";
				
				} else {
					validExpedienteLiquidacion = false;
					strMsgTxtCondiciones="La solicitud NO CUMPLE con las Condiciones (Liq. Renuncia) requeridas. No procede el registro de la solicitud. ";
				}

		} 
		
		
		// Validacion Expulsion
		if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_EXPULSION)==0){
			if(chkCondicionSocioSinDeudaPendiente.compareTo(Boolean.TRUE)==0 && chkCondicionSinGarantesDeudores.compareTo(Boolean.TRUE)==0){
				strMsgTxtCondiciones="";
				} else {
					validExpedienteLiquidacion = false;
					strMsgTxtCondiciones="La solicitud NO CUMPLE con las Condiciones(Liq. Expulsión) requeridas. No procede el registro de la solicitud. ";
				}

		} 
		
		// Validacion Fallecimiento
		if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
			if(chkCondicionSocioSinDeudaPendiente.compareTo(Boolean.TRUE)==0 && chkCondicionSinGarantesDeudores.compareTo(Boolean.TRUE)==0
					&& chkCondicionBeneficioFondoSepelio.compareTo(Boolean.TRUE)==0){
				strMsgTxtCondiciones="";
				//validExpedienteLiquidacion = true;
				} else {
					validExpedienteLiquidacion = false;
					strMsgTxtCondiciones="La solicitud NO CUMPLE con las Condiciones (Liq Fallecimiento) requeridas. No procede el registro de la solicitud. ";
				}

		} 
		
		/*if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
				if(!(blnCondicionBeneficioFondoSepelio || blnCondicionSinGarantesDeudores || blnCondicionSocioSinDeudaPendiente )){
					validExpedienteLiquidacion = false;
						setStrMsgTxtGrabar("La solicitud NO CUMPLE con las Condiciones requeridas. No procede el registro de la solicitud. ");
					} else {
						setStrMsgTxtGrabar("");
					}

		} else {
			if(!(blnCondicionSinGarantesDeudores || blnCondicionSocioSinDeudaPendiente )){
				validExpedienteLiquidacion = false;
					setStrMsgTxtGrabar("La solicitud NO CUMPLE con las Condiciones requeridas. No procede el registro de la solicitud. ");
				} else {
					setStrMsgTxtGrabar("");
				}
		}*/
		
		if(listaBeneficiarioLiquidacionVista != null && !listaBeneficiarioLiquidacionVista.isEmpty()){
			//validExpedienteLiquidacion = true;
			strMsgTxtTieneBeneficiarios = "";
			//strMsgTxtCondiciones="";
		}else{
			validExpedienteLiquidacion = false;
			strMsgTxtTieneBeneficiarios ="La solicitud no presenta beneficiarios. Agregar Beneficiarios.";
		}
		
		
		validacionSuman100();
		if(!blnSumaPorcentajes){
			validExpedienteLiquidacion = false;
		}
		
		if (beanExpedienteLiquidacion.getStrObservacion() == null
				|| beanExpedienteLiquidacion.getStrObservacion().equals("")) {
					setStrMsgTxtObservacion("El campo de Observación debe ser ingresado.");
					validExpedienteLiquidacion = false;
			} else {
				setStrMsgTxtObservacion("");
			}
		
		
		// validacion agregada para validar las fechas. No nulas ni incoherencias
			if(beanExpedienteLiquidacion.getDtFechaRecepcion()	 == null){
				setStrMsgTxtFechaRecepcion("El campo Fecha de Recepción no puede ser nulo. ");
				validExpedienteLiquidacion = false;
			}else{
				
				Calendar cal = Calendar.getInstance();
				if(strFechaRenuncia != null){
					cal = stringToCalendar(strFechaRenuncia);
					beanExpedienteLiquidacion.setDtFechaRenuncia(cal.getTime());
				}
				
				strMsgTxtFechaRecepcion = "";
				if(beanExpedienteLiquidacion.getDtFechaRecepcion().before(cal.getTime())){
					strMsgTxtFechaRenuncia = "La fecha de Recepción no puede ser anterior a la Renuncia(*).";
					strMsgTxtAsterisco = "(*)";
					validExpedienteLiquidacion = false;
				}
			}
			
			
			Boolean blnCuenta = Boolean.TRUE;
			if(beanExpedienteLiquidacion != null && beanExpedienteLiquidacion.getId() != null && beanExpedienteLiquidacion.getId().getIntItemExpediente() != null){
				
				blnCuenta = validarEstadoCuenta(beanExpedienteLiquidacion);
				
				if(blnCuenta){
					validExpedienteLiquidacion = Boolean.FALSE;
				}
			}

		return validExpedienteLiquidacion;
	}
	
	
	/**
	 * Valida la existencia de adjuntos
	 * @return
	 */
	private int isValidRequisitosSolicitud() {
		int cnt = 0;
		
		//if(blnTieneAdjuntosConfigurados){
			
			if (listaRequisitoLiquidacionComp != null  && !listaRequisitoLiquidacionComp.isEmpty()) {
				
				for (RequisitoLiquidacionComp requisitoLiqComp :listaRequisitoLiquidacionComp) {

					if (requisitoLiqComp.getArchivoAdjunto() == null && requisitoLiqComp.getDetalle().getIntOpcionAdjunta() == 1) {
						cnt++;

					}
				}
			}
		//}

		return cnt;
	}
	
	
	
	public  void grabarSolicitud(ActionEvent event){
		
		try {
			if(beanExpedienteLiquidacion.getId().getIntItemExpediente() == null ){
				grabarSolicitudLiquidacion(event);
			}else{
				ModificarSolicitudLiquidacion(event);
			}
	
		} catch (Exception e) {
			log.error("Error en grabarSolicitud ---> "+e);
		}
		
	}
	
	
	/*
	if (isValidTodaSolicitud() == 0) {
		estadoCredito = new EstadoCredito();
		estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
		estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
		//estadoCredito.setIntPersEmpresaEstadoCod(usuario.getSucursal().getIntIdEstado());
		estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
		estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
		estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
		//estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntIdEstado());
		estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
		beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);
					
	}
	*/
	
	/**
	 * 
	 * @param event
	 * @throws ParseException
	 */
	public void grabarSolicitudLiquidacion(ActionEvent event) throws ParseException {
		//evaluarPrevision(event);
//		List<CuentaConcepto> lstCuentaConcepto = null;
//		CuentaConcepto cuentaCtoAportes = null;
		
		try{
			if (isValidoExpedienteLiquidacion(beanExpedienteLiquidacion) == false) {
				log.info("Datos de Previsión no válidos. Se aborta el proceso de grabación de Solicitud de Previsión.");
				return;
			}

			if(strFechaRenuncia != null){
				Calendar cal = Calendar.getInstance();
				
				//cal = stringToCalendar(strFechaRenuncia);
				beanExpedienteLiquidacion.setDtFechaRenuncia(cal.getTime());
			}
			
			beanExpedienteLiquidacion.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			// Seteamos el documento general de acuerdo al tipo de solicitud de prevision
			beanExpedienteLiquidacion.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
			beanExpedienteLiquidacion.setIntSucuIdSucursal(beanSocioComp.getCuenta().getIntIdUsuSucursal());
			beanExpedienteLiquidacion.setIntSudeIdsubsucursal(beanSocioComp.getCuenta().getIntIdUsuSubSucursal());
			
			// 18.09.2013 - CGD
			beanExpedienteLiquidacion.setIntPersEmpresaSucAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntEmpresaSucAdministra());
			beanExpedienteLiquidacion.setIntSucuIdSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
			beanExpedienteLiquidacion.setIntSudeIdSubSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSubsucurAdministra());

			reCalcularMontosGrillaBeneficiarios();
			
			if (isValidRequisitosSolicitud() == 0) {
			//if (true) {
				EstadoLiquidacion estadoLiquidacion = null;
				
				estadoLiquidacion = new EstadoLiquidacion();	
				estadoLiquidacion.setTsFechaEstado(new Timestamp(new Date().getTime()));
				estadoLiquidacion.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
				estadoLiquidacion.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				estadoLiquidacion.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				estadoLiquidacion.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
				estadoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
				beanExpedienteLiquidacion.getListaEstadoLiquidacion().add(estadoLiquidacion);
				//beanExpedientePrevision.setEstadoPrevisionUltimo(estadoPrevision);
			} else {
					// Si no se graba en estado REQUISITO
				EstadoLiquidacion estadoLiquidacion = null;
				
				estadoLiquidacion = new EstadoLiquidacion();	
				estadoLiquidacion.setTsFechaEstado(new Timestamp(new Date().getTime()));
				estadoLiquidacion.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
				estadoLiquidacion.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				estadoLiquidacion.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				estadoLiquidacion.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
				estadoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
				beanExpedienteLiquidacion.getListaEstadoLiquidacion().add(estadoLiquidacion);
			}	

			if( beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA)==0
				||beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_EXPULSION)==0){	
				if (listaBeneficiarioLiquidacionVista == null || listaBeneficiarioLiquidacionVista.isEmpty()){		
					
					BeneficiarioLiquidacion beneficiarioDefault = new BeneficiarioLiquidacion();
					BeneficiarioLiquidacionId beneficiarioDefaultId = new BeneficiarioLiquidacionId();
					
					beneficiarioDefault.setId(beneficiarioDefaultId);
					beneficiarioDefault.getId().setIntPersEmpresaLiquidacion(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
					beneficiarioDefault.setIntItemViculo(intTipoRelacion);
					beneficiarioDefault.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
					beneficiarioDefault.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
					//listaBeneficiarioPrevisionSol.add(beneficiarioDefault);
					
					List<BeneficiarioLiquidacion> lstBenefPrev = new ArrayList<BeneficiarioLiquidacion>();
					lstBenefPrev.add(beneficiarioDefault);
					setListaBeneficiarioLiquidacionVista(lstBenefPrev);
				}
			}
	
			if (listaRequisitoLiquidacionComp != null && listaRequisitoLiquidacionComp.size() > 0) {
				beanExpedienteLiquidacion.setListaRequisitoLiquidacionComp(listaRequisitoLiquidacionComp);
			}
			
			//if((beanExpedienteLiquidacion.getId().getIntItemExpediente() != null)){
				//beanExpedienteLiquidacion = liquidacionFacade.modificarExpedienteLiquidacion(beanExpedienteLiquidacion,cuentaCtoAportes);
				// comentado x pruebas -------------------------------------------------------------------------------------------------->
			//	beanExpedienteLiquidacion = liquidacionFacade.modificarExpedienteLiquidacion(beanExpedienteLiquidacion);			
			//}else{
				
				// comentado x pruebas -------------------------------------------------------------------------------------------------->
				 beanExpedienteLiquidacion = liquidacionFacade.grabarExpedienteLiquidacion(beanExpedienteLiquidacion);
			//}
			
				//25.08.2013 - cgd
			if (beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp() != null
				&& beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp().size() > 0) {
					renombrarArchivo(beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp());
			}
			
			//validdamos que se haya generado itemexpediente...
			if((beanExpedienteLiquidacion.getId().getIntItemExpediente() != null)){
				cancelarGrabarSolicitud();
								
			}
			
			//buscarSolicitudPrevision2(event);
		} 
		catch (BusinessException e) {
			//e.printStackTrace();
			log.error("Error en grabarSolicitud --> "+e);
		}
	}
	
	
	public void ModificarSolicitudLiquidacion(ActionEvent event) throws ParseException {
		//evaluarPrevision(event);
//		List<CuentaConcepto> lstCuentaConcepto = null;
//		CuentaConcepto cuentaCtoAportes = null;
		
		try{
			
			if (isValidoExpedienteLiquidacion(beanExpedienteLiquidacion) == false) {
				log.info("Datos de Previsión no válidos. Se aborta el proceso de grabación de Solicitud de Previsión.");
				return;
			}
			
			/*
			if(strFechaRenuncia != null){
				beanExpedienteLiquidacion.setDtFechaRenuncia(new Date(strFechaRenuncia));
			}
			*/
			
			beanExpedienteLiquidacion.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			// Seteamos el documento general de acuerdo al tipo de solicitud de prevision
			beanExpedienteLiquidacion.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACION_CUENTA);
			beanExpedienteLiquidacion.setIntSucuIdSucursal(beanSocioComp.getCuenta().getIntIdUsuSucursal());
			beanExpedienteLiquidacion.setIntSudeIdsubsucursal(beanSocioComp.getCuenta().getIntIdUsuSubSucursal());
			
			reCalcularMontosGrillaBeneficiarios();
			
			if (isValidRequisitosSolicitud() == 0) {
				EstadoLiquidacion estadoLiquidacion = new EstadoLiquidacion();
				estadoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
				estadoLiquidacion.setTsFechaEstado(new Timestamp(new Date().getTime()));
				//estadoCredito.setIntPersEmpresaEstadoCod(usuario.getSucursal().getIntIdEstado());
				estadoLiquidacion.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
				estadoLiquidacion.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				estadoLiquidacion.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				//estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntIdEstado());
				estadoLiquidacion.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
				beanExpedienteLiquidacion.getListaEstadoLiquidacion().add(estadoLiquidacion);
							
			}	

			if( beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA)==0
				||beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_EXPULSION)==0){	
				if (listaBeneficiarioLiquidacionVista == null || listaBeneficiarioLiquidacionVista.isEmpty()){		
					
					BeneficiarioLiquidacion beneficiarioDefault = new BeneficiarioLiquidacion();
					BeneficiarioLiquidacionId beneficiarioDefaultId = new BeneficiarioLiquidacionId();
					
					beneficiarioDefault.setId(beneficiarioDefaultId);
					beneficiarioDefault.getId().setIntPersEmpresaLiquidacion(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
					beneficiarioDefault.setIntItemViculo(intTipoRelacion);
					beneficiarioDefault.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
					beneficiarioDefault.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
					//listaBeneficiarioPrevisionSol.add(beneficiarioDefault);
					
					List<BeneficiarioLiquidacion> lstBenefPrev = new ArrayList<BeneficiarioLiquidacion>();
					lstBenefPrev.add(beneficiarioDefault);
					setListaBeneficiarioLiquidacionVista(lstBenefPrev);
				}
			}
	
			if (listaRequisitoLiquidacionComp != null && listaRequisitoLiquidacionComp.size() > 0) {
				beanExpedienteLiquidacion.setListaRequisitoLiquidacionComp(listaRequisitoLiquidacionComp);
			}
			
			//if((beanExpedienteLiquidacion.getId().getIntItemExpediente() != null)){
				//beanExpedienteLiquidacion = liquidacionFacade.modificarExpedienteLiquidacion(beanExpedienteLiquidacion,cuentaCtoAportes);
				// comentado x pruebas -------------------------------------------------------------------------------------------------->
				beanExpedienteLiquidacion = liquidacionFacade.modificarExpedienteLiquidacion(beanExpedienteLiquidacion);			
			//}else{
				
				// comentado x pruebas -------------------------------------------------------------------------------------------------->
			//	 beanExpedienteLiquidacion = liquidacionFacade.grabarExpedienteLiquidacion(beanExpedienteLiquidacion);
			//}
			
			//validdamos que se haya generado itemexpediente...
			if((beanExpedienteLiquidacion.getId().getIntItemExpediente() != null)){
				cancelarGrabarSolicitud();
								
			}
			
			//buscarSolicitudPrevision2(event);
		} 
		catch (BusinessException e) {
			//e.printStackTrace();
			log.error("Error en grabarSolicitud --> "+e);
		}
	}
	
	
	/**
	 * 
	 */
	public void limpiarMensajesIsValidoExpediente() {
		setStrMsgTxtSubOperacion("");
		setStrMsgCondicionBeneficioFondoSepelio("");
		setStrMsgCondicionSinGarantesDeudores("");
		setStrMsgCondicionSocioSinDeudaPendiente("");
		setStrMsgErrorValidarDatos("");
		setStrMsgTxtFechaProgramacion("");
		setStrMsgTxtFechaRecepcion("");
		setStrMsgTxtFechaRenuncia("");
		setStrMsgTxtMontoBrutoLiquidacion("");
		setStrMsgTxtObservacion("");
		setStrMsgTxtParaMotivoRenuncia("");
		dtFechaRegistro = Calendar.getInstance().getTime();
		setStrMsgTxttPeriodoUltimoDescuento("");
		setStrMsgTxtTieneFallecidos("");
		setStrMsgTxtTieneBeneficiarios("");
		setStrMsgTxtGrabar("");
		setStrMsgTxtAsterisco("");
		
		//setStrUnidadesEjecutorasConcatenadas("");
		setStrMsgTxtSumaPorcentaje("");

		setChkCondicionBeneficioFondoSepelio(false);
		setChkCondicionSinGarantesDeudores(false);
		setChkCondicionSocioSinDeudaPendiente(false);

		campoBuscarBeneficiario="";
		strMsgTxtCondiciones="";
		strMsgTxtValidacionbeneficiarios = "";
		strMsgTxtProcedeEvaluacion = "";
		strMsgTxtProcedeEvaluacion1 = "";
		strMsgTxtProcedeEvaluacion = "";
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void cancelarGrabarSolicitud() {
		strSolicitudLiquidacion = "";
		limpiarFormSolicitudLiquidacion();
		limpiarMensajesIsValidoExpediente();
		blnShowValidarDatos = false;
		dtFechaRegistro = Calendar.getInstance().getTime();
		blnShowDivFormSolicitudLiquidacion = false;
		blnVerSolExpLiquidacion = false;
		limpiarBeneficiarios();
	}
	

	
	/**
	 * 
	 */
	public void irModificarSolicitudLiquidacionAutoriza(ActionEvent event){	
		//cancelarGrabarSolicitud(event);
		AutorizacionLiquidacionController autorizacionLiquidacion = (AutorizacionLiquidacionController) getSessionBean("autorizacionLiquidacionController");
		autorizacionLiquidacion.irModificarSolicitudLiquidacionAutoriza(event);
		
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void irModificarSolicitudLiquidacion(ActionEvent event){	
		cancelarGrabarSolicitud();
		strSolicitudLiquidacion = Constante.MANTENIMIENTO_MODIFICAR;
		blnViewSolicitudLiquidacion = false;
		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
		//List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;

		ExpedienteLiquidacionId expedienteLiquidacionId = new ExpedienteLiquidacionId();
		
		expedienteLiquidacionId.setIntPersEmpresaPk(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntPersEmpresaPk());
		expedienteLiquidacionId.setIntItemExpediente(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntItemExpediente());
		//expedienteLiquidacionId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getId().get == null ? new Integer(strIntItemDetExpediente) : new Integer(	strItemDetExpediente));
		Boolean blnContinuaRecorrido = true;
		Cuenta cuentaExpediente = null;
		
		try {
			blnMostrarDescripcionTipoLiquidacion = Boolean.FALSE;
			// devuelve el crongrama son id vacio.
			//beanExpedientePrevision 
			beanExpedienteLiquidacion = liquidacionFacade.getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(expedienteLiquidacionId);
			
			
			if (beanExpedienteLiquidacion != null) {
					//listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				
					// Recuperamos al Socio 
						if(beanExpedienteLiquidacion.getId() != null){
							cargarDescripcionTipoLiquidacion();
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = null;
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta());

							// cgd - 30.08.2013
							//cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
							cuentaSocio = cuentaFacade.getListaCuentaPorPkTodoEstado(cuentaIdSocio);
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
										
										
										cuentaExpediente = new Cuenta();
										cuentaExpediente.setId(new CuentaId());
										Integer intCuenta = 0;
										if(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle() != null
												&& !beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()){
											intCuenta = beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta();
											
										}
										cuentaExpediente.getId().setIntCuenta(intCuenta);
										cuentaExpediente.getId().setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
										
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION, cuentaExpediente);
										
										/*
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION);
										
										*/
										//-------------------agregado 03062013----------------->
										if (socioComp != null) {
											if (socioComp.getCuenta() != null) {
														if(blnContinuaRecorrido){
															if(socioComp.getPersona() != null){
																// •	Estado de persona = 1 activa 
//																if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){
						
																	for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
																		if(true){
																			if(socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																				if (socioEstructura.getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)==0) {
																					strMsgErrorValidarDatos = "";
																					socioComp.getSocio().setSocioEstructura(socioEstructura);
						
																					//if(!(socioComp.getCuenta().getIntParaSubTipoCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0)){
																					//•	Cuenta situación = 1 activa
																					if(socioComp.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0){
																						
																						// agregar validacion: no debe existir expedientes de prevision.
																						pgValidDatos = false;
																						blnDatosSocio = true;
																						beanSocioComp = socioComp;
						
																						// strDescripcionTipoCuenta
//																						listaCuentaSocio = new ArrayList<CuentaComp>();
																						listaCuentaSocio.clear();
																						CuentaComp cuentaCompSocio = new CuentaComp();
																						cuentaCompSocio.setCuenta(beanSocioComp.getCuenta());
						
																						// 1. Secarga la descripcion del Tipo de Cuenta - lista 1
																						for(int t=0; t<listaDescripcionTipoCuenta.size();t++){
																							if(listaDescripcionTipoCuenta.get(t).getIntIdDetalle().compareTo(beanSocioComp.getCuenta().getIntParaTipoCuentaCod())==0){
																								cuentaCompSocio.setStrDescripcionTipoCuenta(listaDescripcionTipoCuenta.get(t).getStrDescripcion());
																								break;
																							}
																						}
						
																						// 2. De momento solo hay una cuenta x socio
																						listaCuentaSocio.add(cuentaCompSocio);
																						String strDescCuenta = listaCuentaSocio.get(0).getStrDescripcionTipoCuenta();
						
																						//List<CuentaConcepto> lstCtaCto = null;
																						listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());
						
																						// Solo se deben visualizar 4 cuentas: Aporte, Retiro, Ahoroo y Depaosito
																						listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
						
																						CuentaConceptoComp cuentaConceptoComp = null;
																						CuentaConcepto cuentaConcepto = null;
																						for(int k=0; k<listaCuentaConcepto.size();k++){
																							cuentaConcepto = listaCuentaConcepto.get(k);
																							cuentaConceptoComp = new CuentaConceptoComp();
																							// cargando la descripcion del tipo de cuenta
																							cuentaConceptoComp.setStrDescripcionCuenta(strDescCuenta); 
						
																							// cargando la descripcion de cada cuenta concepto
																							for (Tabla descripcion : listaDescripcionCuentaConcepto) {
																								CuentaConceptoDetalle detalle = null;
																								if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
																								&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
																									detalle = new CuentaConceptoDetalle();
																									detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
						
																									if(descripcion.getIntIdDetalle().compareTo(detalle.getIntParaTipoConceptoCod())==0){
																										cuentaConceptoComp.setStrDescripcionConcepto(descripcion.getStrDescripcion());
																										cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNumeroCuenta());
																										cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
																										if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0
																										 ||detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
																											cuentaConceptoComp.getCuentaConcepto().setChecked(true);
																										}
																										
																										break;
																									}
																								}	
																							}
						
																							listaCuentaConceptoComp.add(cuentaConceptoComp);
																						}
																																												
																						EstructuraId estructuraId = new EstructuraId();
																						Estructura estructura = null;
																						estructuraId.setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
																						estructuraId.setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
																						estructura = estructuraFacade.getEstructuraPorPk(estructuraId);
						
																						beanEstructuraSocioComp = estructura;
																						//estructura.getListaEstructuraDetalle();
						
																						if(estructura.getListaEstructuraDetalle() != null){
																							for(int k=0; k<estructura.getListaEstructuraDetalle().size();k++){
																								estructura.getListaEstructuraDetalle().get(k).getListaSubsucursal();
																							}
																						}
						
																						blnShowDivFormSolicitudLiquidacion = true;
																						blnShowValidarDatos = false;
						
																						// cargando sucursal y subsucursal del socio
																						cargarListaTablaSucursal();
																						seleccionarSucursal();
						
																					} else {
																						strMsgErrorValidarDatos = strMsgErrorValidarDatos +"La sub condición de la Cuenta no es Regular. ";
																					}
						
																				}else{
																					strMsgErrorValidarDatos = strMsgErrorValidarDatos+ "El socio no posee una estructura de origen.";
																				}
																			}
																		}
																	}
						
																	blnContinuaRecorrido = false;
																	cargarDescripcionUEjecutorasConcatenadas(socioComp);
																	
						
//																}
	
											} 
										}
										
										//--------------------------------------------->
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
					
					regenerarCuentasConceptoBase();
					if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
						regenerarBenficiariosVista_m1();
					}else{
						regenerarBenficiariosVista_m1_Titular();
					}
					
									
					// cargando fechas de solicitud...
					strFechaRenuncia = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaRenuncia());
					strFechaProgramacionPago = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaProgramacion());
					strFechaRecepcionRenuncia = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaRecepcion());
					System.out.println("getDtFechaRecepcion()---->  "+beanExpedienteLiquidacion.getDtFechaRecepcion());
					
				if (beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp()!= null) {
					listaRequisitoLiquidacionComp = beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp();
				}
				
				//jchavez 09.06.2014
				mostrarlistaAutorizacionesPrevias(beanExpedienteLiquidacion.getListaEstadoLiquidacion());
				//
//				List<ExpedienteLiquidacionDetalle> listaDetalleLiquidacion = null;

				cargarListaTablaSucursal();
				seleccionarSucursal();
				evaluarSolicitudMod(event);
			}
			
						}}
		} catch (BusinessException e) {
			log.error("Error -BusinessException - en irModificarSolicitudLiquidacion --->  " + e);
			e.printStackTrace();
		} 
		catch (ParseException e1) {
			log.error("Error -ParseException - en irModificarSolicitudLiquidacion --->  " + e1);
			log.error(e1);
		} catch (Exception e2) {
			log.error("Error -Exception - en irModificarSolicitudLiquidacion --->  " + e2);
			log.error(e2);
		}

		blnShowDivFormSolicitudLiquidacion = true;
		blnPostEvaluacion = true;
		pgValidDatos = false;
		blnDatosSocio = true;
	}
	
	
	
	/**
	 * Regenera los beanCuentasdCOncepto en base a los detalles recuperados
	 */
	public void regenerarCuentasConceptoBase(){
		Boolean blnContinuaAporte = Boolean.TRUE;
		Boolean blnContinuaRetiro = Boolean.TRUE;
		
		try {
			System.out.println("wtf???...");
			if(listaCuentaConceptoComp != null && !listaCuentaConceptoComp.isEmpty()
				&& beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle() != null
				&& !beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()){
				
				// Aportes ------------>
				for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptoComp) {
					if(blnContinuaAporte){
						for (ExpedienteLiquidacionDetalle detalleExpediente : beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
							if(blnContinuaAporte){
								if(cuentaConceptoComp.getCuentaConcepto().getId().getIntItemCuentaConcepto().
									compareTo(detalleExpediente.getId().getIntItemCuentaConcepto())==0){
									
									CuentaConceptoDetalle detalle = cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().get(0);
									if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
										beanCuentaConceptoAportes = new CuentaConceptoComp();
										beanCuentaConceptoAportes = cuentaConceptoComp;
										blnContinuaAporte = Boolean.FALSE;
									}
								}
							}
						}	
					}
					
				}
				
				// Fondo de Retiro ------------>
				for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptoComp) {
					if(blnContinuaRetiro){
						for (ExpedienteLiquidacionDetalle detalleExpediente : beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
							if(blnContinuaRetiro){
								if(cuentaConceptoComp.getCuentaConcepto().getId().getIntItemCuentaConcepto().
									compareTo(detalleExpediente.getId().getIntItemCuentaConcepto())==0){
									
									CuentaConceptoDetalle detalle = cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().get(0);
									if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
										beanCuentaConceptoRetiro = new CuentaConceptoComp();
										beanCuentaConceptoRetiro = cuentaConceptoComp;
										blnContinuaRetiro = Boolean.FALSE;
									}
								}
							}
						}	
					}
					
				}
			}	

		} catch (Exception e) {
			log.error("Error en regenerarBeneficiariosVista ---> "+e);
		}	
	}
	
	
	
	/**
	 * 1. Regenera los beneficiarios Vista (grilla)  apartir de los detalles del expediente
	 */
	public void regenerarBenficiariosVista_m1(){
		List<BeneficiarioLiquidacion> lstBeneficiariosTotal = null;
		List<BeneficiarioLiquidacion> lstBeneficiariosFiltrado = null;
		List<Vinculo> vinculos = null;
		try {
			
			lstBeneficiariosTotal = liquidacionFacade.getListaBeneficiariosPorExpedienteLiquidacion(beanExpedienteLiquidacion);
			lstBeneficiariosFiltrado = new ArrayList<BeneficiarioLiquidacion>();
			for(int k=0; k<lstBeneficiariosTotal.size(); k++){
				if(k==0){
					lstBeneficiariosFiltrado.add(lstBeneficiariosTotal.get(0));
				}
				
				else{
					boolean blnPasa = Boolean.TRUE;
						for(int u=0; u<lstBeneficiariosFiltrado.size();u++){
							blnPasa = true;
							if(lstBeneficiariosTotal.get(k).getIntPersPersonaBeneficiario().intValue() ==(lstBeneficiariosFiltrado.get(u).getIntPersPersonaBeneficiario().intValue())){
								blnPasa = false;
								break;
							}else{
								
							}
						}
						
						if(blnPasa){
							lstBeneficiariosFiltrado.add(lstBeneficiariosTotal.get(k));
							
						}	
				}

			}
			
			// Recuperamos los vinculos del socio
			
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			Integer intIdPersona = beanSocioComp.getSocio().getSocioEstructura().getId().getIntIdPersona();
			Integer intIdEmpresa = beanSocioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa();
			PersonaEmpresaPK personaPk = new PersonaEmpresaPK();
			personaPk.setIntIdEmpresa(intIdEmpresa);
			personaPk.setIntIdPersona(intIdPersona);
			
			vinculos = personaFacade.getVinculoPorPk(personaPk);
			
			if(vinculos != null && !vinculos.isEmpty()
				&& lstBeneficiariosFiltrado != null && !lstBeneficiariosFiltrado.isEmpty()){
				
				for( BeneficiarioLiquidacion beneficiarioLiq:   lstBeneficiariosFiltrado){
					BeneficiarioLiquidacion benef = null;
					benef = recuperarBeneficiarioPorVinculo(beneficiarioLiq, vinculos);
					if(benef != null){
						listaBeneficiarioLiquidacionVista.add(benef);	
					}
					
				}
				
				
			}
				
			listaBeneficiarioLiquidacionVista = reCalcularMontosYPorcentajeBeneficiariosParaEdicion_m2();
			List<ExpedienteLiquidacionDetalle> listaDetalles = null;
			listaDetalles = agregarBeneficiarioSocio();
			if(listaDetalles != null && !listaDetalles.isEmpty()){
				beanExpedienteLiquidacion.setListaExpedienteLiquidacionDetalle(listaDetalles);
			}
			calcularBeneficiarioTotales();
			
		} catch (Exception e) {
			log.error("Error en regenerarBenficiariosVista ---> "+e);
		}

	}
	
	/**
	 * 
	 */
	public void regenerarBenficiariosVista_m1_Titular(){
		List<BeneficiarioLiquidacion> lstBeneficiariosTotal = null;
		List<BeneficiarioLiquidacion> lstBeneficiariosFiltrado = null;
//		List<Vinculo> vinculos = null;
		try {
			
			lstBeneficiariosTotal = liquidacionFacade.getListaBeneficiariosPorExpedienteLiquidacion(beanExpedienteLiquidacion);
			lstBeneficiariosFiltrado = new ArrayList<BeneficiarioLiquidacion>();
			for(int k=0; k<lstBeneficiariosTotal.size(); k++){
				if(k==0){
					lstBeneficiariosFiltrado.add(lstBeneficiariosTotal.get(0));
				}
				
				else{
					boolean blnPasa = Boolean.TRUE;
						for(int u=0; u<lstBeneficiariosFiltrado.size();u++){
							blnPasa = true;
							if(lstBeneficiariosTotal.get(k).getIntPersPersonaBeneficiario().intValue() ==(lstBeneficiariosFiltrado.get(u).getIntPersPersonaBeneficiario().intValue())){
								blnPasa = false;
								break;
							}else{
								
							}
						}
						
						if(blnPasa){
							lstBeneficiariosFiltrado.add(lstBeneficiariosTotal.get(k));
							
						}	
				}

			}
			
			// Recuperamos los vinculos del socio
			
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			Integer intIdPersona = beanSocioComp.getSocio().getSocioEstructura().getId().getIntIdPersona();
			Integer intIdEmpresa = beanSocioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa();
			PersonaEmpresaPK personaPk = new PersonaEmpresaPK();
			personaPk.setIntIdEmpresa(intIdEmpresa);
			personaPk.setIntIdPersona(intIdPersona);
			
			//vinculos = personaFacade.getVinculoPorPk(personaPk);
			
			//if(vinculos != null && !vinculos.isEmpty()
			//	&& lstBeneficiariosFiltrado != null && !lstBeneficiariosFiltrado.isEmpty()){
				
				for( BeneficiarioLiquidacion beneficiarioLiq:   lstBeneficiariosFiltrado){
					BeneficiarioLiquidacion benef = null;
					benef = recuperarBeneficiarioPorVinculo_Titular(beneficiarioLiq);
					if(benef != null){
						listaBeneficiarioLiquidacionVista.add(benef);	
					}
					
				}
				
				
			//}
				
			listaBeneficiarioLiquidacionVista = reCalcularMontosYPorcentajeBeneficiariosParaEdicion_m2();
			List<ExpedienteLiquidacionDetalle> listaDetalles = null;
			listaDetalles = agregarBeneficiarioSocio_Titular();
			if(listaDetalles != null && !listaDetalles.isEmpty()){
				beanExpedienteLiquidacion.setListaExpedienteLiquidacionDetalle(listaDetalles);
			}
			calcularBeneficiarioTotales();
			
		} catch (Exception e) {
			log.error("Error en regenerarBenficiariosVista ---> "+e);
		}

	}
	/**
	 * 
	 * @param event
	 * @param registroSeleccionadoAut
	 */
	
	public void irModificarSolicitudLiquidacionAutoriza2(ActionEvent event, ExpedienteLiquidacion expedienteLiquidacion){	
		cancelarGrabarSolicitud();
		strSolicitudLiquidacion = Constante.MANTENIMIENTO_MODIFICAR;
		
		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
//		List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;

		ExpedienteLiquidacionId expedienteLiquidacionId = new ExpedienteLiquidacionId();
		
		expedienteLiquidacionId.setIntPersEmpresaPk(expedienteLiquidacion.getId().getIntPersEmpresaPk());
		expedienteLiquidacionId.setIntItemExpediente(expedienteLiquidacion.getId().getIntItemExpediente());
		//expedienteLiquidacionId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getId().get == null ? new Integer(strIntItemDetExpediente) : new Integer(	strItemDetExpediente));
		Boolean blnContinuaRecorrido = true;
		Cuenta cuentaExpediente = null;
		
		try {
			blnMostrarDescripcionTipoLiquidacion = Boolean.FALSE;
			// devuelve el crongrama son id vacio.
			//beanExpedientePrevision 
			beanExpedienteLiquidacion = liquidacionFacade.getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(expedienteLiquidacionId);
			
			
			if (beanExpedienteLiquidacion != null) {
					//listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				
					// Recuperamos al Socio 
						if(beanExpedienteLiquidacion.getId() != null){
							cargarDescripcionTipoLiquidacion();
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = null;
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta());

							
							// cgd - 30.08.2013
							cuentaSocio = cuentaFacade.getListaCuentaPorPkTodoEstado(cuentaIdSocio);
							//cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
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
										
										
										cuentaExpediente = new Cuenta();
										cuentaExpediente.setId(new CuentaId());
										Integer intCuenta = 0;
										if(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle() != null
												&& !beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()){
											intCuenta = beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta();
											
										}
										cuentaExpediente.getId().setIntCuenta(intCuenta);
										cuentaExpediente.getId().setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
										
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION, cuentaExpediente);
										/*
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION);
										*/
										//-------------------agregado 03062013----------------->
										if (socioComp != null) {
											if (socioComp.getCuenta() != null) {
														if(blnContinuaRecorrido){
															if(socioComp.getPersona() != null){
																// •	Estado de persona = 1 activa 
																if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){
						
																	for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
																		if(true){
																			if(socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																				if (socioEstructura.getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)==0) {
																					strMsgErrorValidarDatos = "";
																					socioComp.getSocio().setSocioEstructura(socioEstructura);
						
																					//if(!(socioComp.getCuenta().getIntParaSubTipoCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0)){
																					//•	Cuenta situación = 1 activa
																					if(socioComp.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0){
																						
																						// agregar validacion: no debe existir expedientes de prevision.
																						pgValidDatos = false;
																						blnDatosSocio = true;
																						beanSocioComp = socioComp;
						
																						// strDescripcionTipoCuenta
//																						listaCuentaSocio = new ArrayList<CuentaComp>();
																						listaCuentaSocio.clear();
																						CuentaComp cuentaCompSocio = new CuentaComp();
																						cuentaCompSocio.setCuenta(beanSocioComp.getCuenta());
						
																						// 1. Secarga la descripcion del Tipo de Cuenta - lista 1
																						for(int t=0; t<listaDescripcionTipoCuenta.size();t++){
																							if(listaDescripcionTipoCuenta.get(t).getIntIdDetalle().compareTo(beanSocioComp.getCuenta().getIntParaTipoCuentaCod())==0){
																								cuentaCompSocio.setStrDescripcionTipoCuenta(listaDescripcionTipoCuenta.get(t).getStrDescripcion());
																								break;
																							}
																						}
						
																						// 2. De momento solo hay una cuenta x socio
																						listaCuentaSocio.add(cuentaCompSocio);
																						String strDescCuenta = listaCuentaSocio.get(0).getStrDescripcionTipoCuenta();
						
																						//List<CuentaConcepto> lstCtaCto = null;
																						listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());
						
																						// Solo se deben visualizar 4 cuentas: Aporte, Retiro, Ahoroo y Depaosito
																						listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
						
																						CuentaConceptoComp cuentaConceptoComp = null;
																						CuentaConcepto cuentaConcepto = null;
																						for(int k=0; k<listaCuentaConcepto.size();k++){
																							cuentaConcepto = listaCuentaConcepto.get(k);
																							cuentaConceptoComp = new CuentaConceptoComp();
																							// cargando la descripcion del tipo de cuenta
																							cuentaConceptoComp.setStrDescripcionCuenta(strDescCuenta); 
						
																							// cargando la descripcion de cada cuenta concepto
																							for (Tabla descripcion : listaDescripcionCuentaConcepto) {
																								CuentaConceptoDetalle detalle = null;
																								if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
																								&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
																									detalle = new CuentaConceptoDetalle();
																									detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
						
																									if(descripcion.getIntIdDetalle().compareTo(detalle.getIntParaTipoConceptoCod())==0){
																										cuentaConceptoComp.setStrDescripcionConcepto(descripcion.getStrDescripcion());
																										cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNumeroCuenta());
																										cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
																										if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0
																										 ||detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
																											cuentaConceptoComp.getCuentaConcepto().setChecked(true);
																										}
																										
																										break;
																									}
																								}	
																							}
						
																							listaCuentaConceptoComp.add(cuentaConceptoComp);
																						}
																																												
																						EstructuraId estructuraId = new EstructuraId();
																						Estructura estructura = null;
																						estructuraId.setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
																						estructuraId.setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
																						estructura = estructuraFacade.getEstructuraPorPk(estructuraId);
						
																						beanEstructuraSocioComp = estructura;
																						estructura.getListaEstructuraDetalle();
						
																						if(estructura.getListaEstructuraDetalle() != null){
																							for(int k=0; k<estructura.getListaEstructuraDetalle().size();k++){
																								estructura.getListaEstructuraDetalle().get(k).getListaSubsucursal();
																							}
																						}
						
																						blnShowDivFormSolicitudLiquidacion = true;
																						blnShowValidarDatos = false;
						
																						// cargando sucursal y subsucursal del socio
																						cargarListaTablaSucursal();
																						seleccionarSucursal();
						
																					} else {
																						strMsgErrorValidarDatos = strMsgErrorValidarDatos +"La sub condición de la Cuenta no es Regular. ";
																					}
						
																				}else{
																					strMsgErrorValidarDatos = strMsgErrorValidarDatos+ "El socio no posee una estructura de origen.";
																				}
																			}
																		}
																	}
						
																	blnContinuaRecorrido = false;
																	cargarDescripcionUEjecutorasConcatenadas(socioComp);
																	
						
																}
	
											} 
										}
										
										//--------------------------------------------->
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
					
					regenerarCuentasConceptoBase();
					if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
						regenerarBenficiariosVista_m1();
					}else{
						regenerarBenficiariosVista_m1_Titular();
					}
					
									
					// cargando fechas de solicitud...
					strFechaRenuncia = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaRenuncia());
					strFechaProgramacionPago = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaProgramacion());
					strFechaRecepcionRenuncia = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaRecepcion());
					System.out.println("getDtFechaRecepcion()---->  "+beanExpedienteLiquidacion.getDtFechaRecepcion());
					
				if (beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp()!= null) {
					listaRequisitoLiquidacionComp = beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp();
				}
				
				
//				List<ExpedienteLiquidacionDetalle> listaDetalleLiquidacion = null;

				cargarListaTablaSucursal();
				seleccionarSucursal();
				evaluarSolicitudMod(event);
			}
			
						}}
		} catch (BusinessException e) {
			log.error("Error -BusinessException - en irModificarSolicitudLiquidacion --->  " + e);
			e.printStackTrace();
		} 
		catch (ParseException e1) {
			log.error("Error -ParseException - en irModificarSolicitudLiquidacion --->  " + e1);
			log.error(e1);
		} catch (Exception e2) {
			log.error("Error -Exception - en irModificarSolicitudLiquidacion --->  " + e2);
			log.error(e2);
		}

		blnShowDivFormSolicitudLiquidacion = true;
		blnPostEvaluacion = true;
		pgValidDatos = false;
		blnDatosSocio = true;
	}
	
	
	/*public void irModificarSolicitudLiquidacionAutoriza2(ActionEvent event,ExpedienteLiquidacion registroSeleccionadoAut){	
		cancelarGrabarSolicitud(event);
		//strSolicitudLiquidacion = Constante.MANTENIMIENTO_;

		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
		List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;

		ExpedienteLiquidacionId expedienteLiquidacionId = new ExpedienteLiquidacionId();
		
		expedienteLiquidacionId.setIntPersEmpresaPk(registroSeleccionadoAut.getId().getIntPersEmpresaPk());
		expedienteLiquidacionId.setIntItemExpediente(registroSeleccionadoAut.getId().getIntItemExpediente());
		//ExpedientePrevisionId.setIntItemDetExpediente(strItemDetExpediente == null ? new Integer(strIntItemDetExpediente) : new Integer(	strItemDetExpediente));
		
		try {
			// devuelve el crongrama son id vacio.
			//beanExpedientePrevision 
			beanExpedienteLiquidacion = liquidacionFacade.getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(expedienteLiquidacionId);
			
			if (beanExpedienteLiquidacion != null) {
					//listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				
					// Recuperamos al Socio 
						if(beanExpedienteLiquidacion.getId() != null){
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = null;
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta());

							cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
							//if(cuentaSocio != null){
								
								try {
									listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());

								} catch (Exception e) {
									log.info("listaCuentaIntegranteSociolistaCuentaIntegranteSocio---> "+e);
								}
								//listaCuentaIntegranteSocio = ;
								
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
										
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION);

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
					
						// colocar las UNIDADES EJECUTORAS correlativas...
						//---------------------------------------------------------------------------------------------------->
												
							// strDescripcionTipoCuenta
							listaCuentaSocio = new ArrayList<CuentaComp>();
							
							CuentaComp cuentaCompSocio = new CuentaComp();
							cuentaCompSocio.setCuenta(beanSocioComp.getCuenta());
							
							// 1. Secarga la descripcion del Tipo de Cuenta - lista 1
							for(int t=0; t<listaDescripcionTipoCuenta.size();t++){
								if(listaDescripcionTipoCuenta.get(t).getIntIdDetalle().
										compareTo(beanSocioComp.getCuenta().getIntParaTipoCuentaCod())==0){
									cuentaCompSocio.setStrDescripcionTipoCuenta(listaDescripcionTipoCuenta.get(t).getStrDescripcion());
									break;
								}
							}
							
							// 2. 
							// de momento solo hay una cuenta x socio
							listaCuentaSocio.add(cuentaCompSocio);
							String strDescCuenta = listaCuentaSocio.get(0).getStrDescripcionTipoCuenta();
	
							listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());
							listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
							
							CuentaConceptoComp cuentaConceptoComp = null;
							CuentaConcepto cuentaConcepto = null;

							for(int k=0; k<listaCuentaConcepto.size();k++){
								cuentaConcepto = listaCuentaConcepto.get(k);
								cuentaConceptoComp = new CuentaConceptoComp();
								cuentaConceptoComp.setStrDescripcionCuenta(strDescCuenta); 
								//cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNombreCuenta());
								
								// cargando la descripcion de la cuenta concepto
								for(int w=0; w<listaDescripcionCuentaConcepto.size();w++){
									if(listaDescripcionCuentaConcepto.get(w).getIntIdDetalle().
											compareTo(cuentaConcepto.getId().getIntItemCuentaConcepto())==0){
										cuentaConceptoComp.setStrDescripcionConcepto(listaDescripcionCuentaConcepto.get(w).getStrDescripcion());
										cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNumeroCuenta());
										cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
										
										break;
									}
								}

								listaCuentaConceptoComp.add(cuentaConceptoComp);
							}
							
							EstructuraId estructuraId = new EstructuraId();
							Estructura estructura = null;
							estructuraId.setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
							estructuraId.setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
							estructura = estructuraFacade.getEstructuraPorPk(estructuraId);
							
							beanEstructuraSocioComp = estructura;
							estructura.getListaEstructuraDetalle();
							if(estructura.getListaEstructuraDetalle() != null){
								for(int k=0; k<estructura.getListaEstructuraDetalle().size();k++){
									estructura.getListaEstructuraDetalle().get(k).getListaSubsucursal();
								}
							}
	
							blnShowDivFormSolicitudLiquidacion = true;
							blnShowValidarDatos = false;
							// cargando sucursal y subsucursal del socio
							cargarListaTablaSucursal();
							seleccionarSucursal();

						
						String strUnidadesEjecutoras = "";
						SocioEstructura socioEstrConcat = null;
						getListEstructura();
						for ( int e=0; e<socioComp.getSocio().getListSocioEstructura().size();e++) {
							socioEstrConcat = beanSocioComp.getSocio().getListSocioEstructura().get(e);
							
							for(int s=0; s<listEstructura.size();s++){
								if(listEstructura.get(s).getId().getIntCodigo().compareTo(socioEstrConcat.getIntCodigo())==0){
									strUnidadesEjecutoras = listEstructura.get(s).getJuridica().getStrRazonSocial();
									strUnidadesEjecutorasConcatenadas = strUnidadesEjecutoras+  ". " + strUnidadesEjecutorasConcatenadas;	
								}
								
							}
						}
						//---------------------------------------------------------------------------------------------------->
						
					
					
				if (beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp()!= null) {
					listaRequisitoLiquidacionComp = beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp();
				}
				
				List<BeneficiarioLiquidacion> lstbeneficiariosTemp = null;
				if(listaBeneficiarioLiquidacionVista != null){
					lstbeneficiariosTemp = listaBeneficiarioLiquidacionVista;
				//	listaBeneficiariosVista.clear();
					
				//	for(int t=0; t<lstbeneficiariosTemp.size();t++){
					
						
						
						
					//}
					
					
					for(int k=0; k<listaBeneficiarioLiquidacionVista.size();k++){
						
						intIdPersona = listaBeneficiarioLiquidacionVista.get(k).getIntPersPersonaBeneficiario();
						
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
							listaBeneficiarioLiquidacionVista.get(k).setPersona(persona);
						}
					}	
					
					// calcularMontosPorcentajeBeneficiarios();
				}	


				cargarListaTablaSucursal();
				seleccionarSucursal();
				evaluarSolicitudMod(event);
			}
			
			
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
		} 
		catch (ParseException e1) {
			log.error(e1);
		} catch (Exception e2) {
			log.error(e2);
		}

		blnShowDivFormSolicitudLiquidacion = true;
		pgValidDatos = false;
		blnDatosSocio = true;
	}
	*/
	
	/**
	 * 
	 * @param event
	 */
	/*public void irVerSolicitudLiquidacion(ActionEvent event){	
		cancelarGrabarSolicitud(event);
		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
		List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;

		ExpedienteLiquidacionId expedienteLiquidacionId = new ExpedienteLiquidacionId();
		
		expedienteLiquidacionId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getId().getIntPersEmpresaPk());
		expedienteLiquidacionId.setIntItemExpediente(registroSeleccionadoBusqueda.getId().getIntItemExpediente());
		//ExpedientePrevisionId.setIntItemDetExpediente(strItemDetExpediente == null ? new Integer(strIntItemDetExpediente) : new Integer(	strItemDetExpediente));
		
		try {
			// devuelve el crongrama son id vacio.
			//beanExpedientePrevision 
			beanExpedienteLiquidacion = liquidacionFacade.getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(expedienteLiquidacionId);
			
			if (beanExpedienteLiquidacion != null) {
					//listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				
					// Recuperamos al Socio 
						if(beanExpedienteLiquidacion.getId() != null){
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = null;
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta());

							cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
							//if(cuentaSocio != null){
								
								try {
									listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());

								} catch (Exception e) {
									log.info("listaCuentaIntegranteSociolistaCuentaIntegranteSocio---> "+e);
								}
								//listaCuentaIntegranteSocio = ;
								
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
										
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION);

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
					
						// colocar las UNIDADES EJECUTORAS correlativas...
						//---------------------------------------------------------------------------------------------------->
												
							// strDescripcionTipoCuenta
							listaCuentaSocio = new ArrayList<CuentaComp>();
							
							CuentaComp cuentaCompSocio = new CuentaComp();
							cuentaCompSocio.setCuenta(beanSocioComp.getCuenta());
							
							// 1. Secarga la descripcion del Tipo de Cuenta - lista 1
							for(int t=0; t<listaDescripcionTipoCuenta.size();t++){
								if(listaDescripcionTipoCuenta.get(t).getIntIdDetalle().
										compareTo(beanSocioComp.getCuenta().getIntParaTipoCuentaCod())==0){
									cuentaCompSocio.setStrDescripcionTipoCuenta(listaDescripcionTipoCuenta.get(t).getStrDescripcion());
									break;
								}
							}
							
							// 2. 
							// de momento solo hay una cuenta x socio
							listaCuentaSocio.add(cuentaCompSocio);
							String strDescCuenta = listaCuentaSocio.get(0).getStrDescripcionTipoCuenta();
	
							listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());
							listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
							
							CuentaConceptoComp cuentaConceptoComp = null;
							CuentaConcepto cuentaConcepto = null;

							for(int k=0; k<listaCuentaConcepto.size();k++){
								cuentaConcepto = listaCuentaConcepto.get(k);
								cuentaConceptoComp = new CuentaConceptoComp();
								cuentaConceptoComp.setStrDescripcionCuenta(strDescCuenta); 
								//cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNombreCuenta());
								
								// cargando la descripcion de la cuenta concepto
								for(int w=0; w<listaDescripcionCuentaConcepto.size();w++){
									if(listaDescripcionCuentaConcepto.get(w).getIntIdDetalle().
											compareTo(cuentaConcepto.getId().getIntItemCuentaConcepto())==0){
										cuentaConceptoComp.setStrDescripcionConcepto(listaDescripcionCuentaConcepto.get(w).getStrDescripcion());
										cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNumeroCuenta());
										cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
										
										break;
									}
								}

								listaCuentaConceptoComp.add(cuentaConceptoComp);
							}
							
							EstructuraId estructuraId = new EstructuraId();
							Estructura estructura = null;
							estructuraId.setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
							estructuraId.setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
							estructura = estructuraFacade.getEstructuraPorPk(estructuraId);
							
							beanEstructuraSocioComp = estructura;
							estructura.getListaEstructuraDetalle();
							if(estructura.getListaEstructuraDetalle() != null){
								for(int k=0; k<estructura.getListaEstructuraDetalle().size();k++){
									estructura.getListaEstructuraDetalle().get(k).getListaSubsucursal();
								}
							}
	
							blnShowDivFormSolicitudLiquidacion = true;
							blnShowValidarDatos = false;
							// cargando sucursal y subsucursal del socio
							cargarListaTablaSucursal();
							seleccionarSucursal();

						
						String strUnidadesEjecutoras = "";
						SocioEstructura socioEstrConcat = null;
						getListEstructura();
						for ( int e=0; e<socioComp.getSocio().getListSocioEstructura().size();e++) {
							socioEstrConcat = beanSocioComp.getSocio().getListSocioEstructura().get(e);
							
							for(int s=0; s<listEstructura.size();s++){
								if(listEstructura.get(s).getId().getIntCodigo().compareTo(socioEstrConcat.getIntCodigo())==0){
									strUnidadesEjecutoras = listEstructura.get(s).getJuridica().getStrRazonSocial();
									strUnidadesEjecutorasConcatenadas = strUnidadesEjecutoras+  ". " + strUnidadesEjecutorasConcatenadas;	
								}
								
							}
						}
						//---------------------------------------------------------------------------------------------------->

					
					
					setStrSolicitudLiquidacion(Constante.MANTENIMIENTO_ELIMINAR);
					
				if (beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp()!= null) {
					listaRequisitoLiquidacionComp = beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp();
				}
				
				List<BeneficiarioLiquidacion> lstbeneficiariosTemp = null;
				if(listaBeneficiarioLiquidacionVista != null){
					lstbeneficiariosTemp = listaBeneficiarioLiquidacionVista;

					for(int k=0; k<listaBeneficiarioLiquidacionVista.size();k++){
						
						intIdPersona = listaBeneficiarioLiquidacionVista.get(k).getIntPersPersonaBeneficiario();
						
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
							listaBeneficiarioLiquidacionVista.get(k).setPersona(persona);
						}
					}	
				}	


				cargarListaTablaSucursal();
				seleccionarSucursal();
				evaluarSolicitudMod(event);
			}
			
			
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
		} 
		catch (ParseException e1) {
			log.error(e1);
		} catch (Exception e2) {
			log.error(e2);
		}

		blnShowDivFormSolicitudLiquidacion = true;
		pgValidDatos = false;
		blnDatosSocio = true;
	}
	*/
	/**
	 * Modificado 09.05.2014 jchavez
	 * Se soluciona problemas en la visualizacion de la solicitud.
	 */
	public void irVerSolicitudLiquidacion(ActionEvent event){	
		cancelarGrabarSolicitud();
		strSolicitudLiquidacion = Constante.MANTENIMIENTO_ELIMINAR;
		
		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
//		List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;

		ExpedienteLiquidacionId expedienteLiquidacionId = new ExpedienteLiquidacionId();
		
		expedienteLiquidacionId.setIntPersEmpresaPk(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntPersEmpresaPk());
		expedienteLiquidacionId.setIntItemExpediente(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntItemExpediente());
		//expedienteLiquidacionId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getId().get == null ? new Integer(strIntItemDetExpediente) : new Integer(	strItemDetExpediente));
		Boolean blnContinuaRecorrido = true;
		Cuenta cuentaExpediente = null;
		
		try {
			blnMostrarDescripcionTipoLiquidacion = Boolean.FALSE;
			// devuelve el crongrama son id vacio.
			//beanExpedientePrevision 
			beanExpedienteLiquidacion = liquidacionFacade.getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(expedienteLiquidacionId);
			
			
			if (beanExpedienteLiquidacion != null) {
					//listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				
					// Recuperamos al Socio 
						if(beanExpedienteLiquidacion.getId() != null){
							cargarDescripcionTipoLiquidacion();
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = null;
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta());

							// cgd - 30.08.2013
							cuentaSocio = cuentaFacade.getListaCuentaPorPkTodoEstado(cuentaIdSocio);
							//cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
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
										
										
										cuentaExpediente = new Cuenta();
										cuentaExpediente.setId(new CuentaId());
										Integer intCuenta = 0;
										if(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle() != null
												&& !beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()){
											intCuenta = beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta();
											
										}
										cuentaExpediente.getId().setIntCuenta(intCuenta);
										cuentaExpediente.getId().setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
										
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION, cuentaExpediente);
										/*
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION);
										
										*/
										//-------------------agregado 03062013----------------->
										if (socioComp != null) {
											if (socioComp.getCuenta() != null) {
														if(blnContinuaRecorrido){
															if(socioComp.getPersona() != null){
																// •	Estado de persona = 1 activa 
																if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){
						
																	for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
																		if(true){
																			if(socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																				if (socioEstructura.getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)==0) {
																					strMsgErrorValidarDatos = "";
																					socioComp.getSocio().setSocioEstructura(socioEstructura);
						
																					//if(!(socioComp.getCuenta().getIntParaSubTipoCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0)){
																					//•	Cuenta situación = 1 activa
																					if(socioComp.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0){
																						
																						// agregar validacion: no debe existir expedientes de prevision.
																						pgValidDatos = false;
																						blnDatosSocio = true;
																						beanSocioComp = socioComp;
						
																						// strDescripcionTipoCuenta
//																						listaCuentaSocio = new ArrayList<CuentaComp>();
																						listaCuentaSocio.clear();
																						CuentaComp cuentaCompSocio = new CuentaComp();
																						cuentaCompSocio.setCuenta(beanSocioComp.getCuenta());
						
																						// 1. Secarga la descripcion del Tipo de Cuenta - lista 1
																						for(int t=0; t<listaDescripcionTipoCuenta.size();t++){
																							if(listaDescripcionTipoCuenta.get(t).getIntIdDetalle().compareTo(beanSocioComp.getCuenta().getIntParaTipoCuentaCod())==0){
																								cuentaCompSocio.setStrDescripcionTipoCuenta(listaDescripcionTipoCuenta.get(t).getStrDescripcion());
																								break;
																							}
																						}
						
																						// 2. De momento solo hay una cuenta x socio
																						listaCuentaSocio.add(cuentaCompSocio);
																						String strDescCuenta = listaCuentaSocio.get(0).getStrDescripcionTipoCuenta();
						
																						//List<CuentaConcepto> lstCtaCto = null;
																						listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());
						
																						// Solo se deben visualizar 4 cuentas: Aporte, Retiro, Ahoroo y Depaosito
																						listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
						
																						CuentaConceptoComp cuentaConceptoComp = null;
																						CuentaConcepto cuentaConcepto = null;
																						for(int k=0; k<listaCuentaConcepto.size();k++){
																							cuentaConcepto = listaCuentaConcepto.get(k);
																							cuentaConceptoComp = new CuentaConceptoComp();
																							// cargando la descripcion del tipo de cuenta
																							cuentaConceptoComp.setStrDescripcionCuenta(strDescCuenta); 
						
																							// cargando la descripcion de cada cuenta concepto
																							for (Tabla descripcion : listaDescripcionCuentaConcepto) {
																								CuentaConceptoDetalle detalle = null;
																								if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
																								&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
																									detalle = new CuentaConceptoDetalle();
																									detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
						
																									if(descripcion.getIntIdDetalle().compareTo(detalle.getIntParaTipoConceptoCod())==0){
																										cuentaConceptoComp.setStrDescripcionConcepto(descripcion.getStrDescripcion());
																										cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNumeroCuenta());
																										cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
																										if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0
																										 ||detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
																											cuentaConceptoComp.getCuentaConcepto().setChecked(true);
																										}
																										
																										break;
																									}
																								}	
																							}
						
																							listaCuentaConceptoComp.add(cuentaConceptoComp);
																						}
																																												
																						EstructuraId estructuraId = new EstructuraId();
																						Estructura estructura = null;
																						estructuraId.setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
																						estructuraId.setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
																						estructura = estructuraFacade.getEstructuraPorPk(estructuraId);
						
																						beanEstructuraSocioComp = estructura;
																						estructura.getListaEstructuraDetalle();
						
																						if(estructura.getListaEstructuraDetalle() != null){
																							for(int k=0; k<estructura.getListaEstructuraDetalle().size();k++){
																								estructura.getListaEstructuraDetalle().get(k).getListaSubsucursal();
																							}
																						}
						
																						blnShowDivFormSolicitudLiquidacion = true;
																						blnShowValidarDatos = false;
						
																						// cargando sucursal y subsucursal del socio
																						cargarListaTablaSucursal();
																						seleccionarSucursal();
						
																					} else {
																						strMsgErrorValidarDatos = strMsgErrorValidarDatos +"La sub condición de la Cuenta no es Regular. ";
																					}
						
																				}else{
																					strMsgErrorValidarDatos = strMsgErrorValidarDatos+ "El socio no posee una estructura de origen.";
																				}
																			}
																		}
																	}
						
																	blnContinuaRecorrido = false;
																	cargarDescripcionUEjecutorasConcatenadas(socioComp);
																	
						
																}
	
											} 
										}
										
										//--------------------------------------------->
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
					
					regenerarCuentasConceptoBase();
					if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
						regenerarBenficiariosVista_m1();
					}else{
						regenerarBenficiariosVista_m1_Titular();
					}
					
									
					// cargando fechas de solicitud...
					strFechaRenuncia = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaRenuncia());
					strFechaProgramacionPago = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaProgramacion());
					strFechaRecepcionRenuncia = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaRecepcion());
					System.out.println("getDtFechaRecepcion()---->  "+beanExpedienteLiquidacion.getDtFechaRecepcion());
					
				if (beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp()!= null) {
					listaRequisitoLiquidacionComp = beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp();
				}
				
				//jchavez 09.06.2014
				mostrarlistaAutorizacionesPrevias(beanExpedienteLiquidacion.getListaEstadoLiquidacion());
//				List<ExpedienteLiquidacionDetalle> listaDetalleLiquidacion = null;

				cargarListaTablaSucursal();
				seleccionarSucursal();
				evaluarSolicitudMod(event);
			}
			
						}}
		} catch (BusinessException e) {
			log.error("Error -BusinessException - en irModificarSolicitudLiquidacion --->  " + e);
			e.printStackTrace();
		} 
		catch (ParseException e1) {
			log.error("Error -ParseException - en irModificarSolicitudLiquidacion --->  " + e1);
			log.error(e1);
		} catch (Exception e2) {
			log.error("Error -Exception - en irModificarSolicitudLiquidacion --->  " + e2);
			log.error(e2);
		}

		blnShowDivFormSolicitudLiquidacion = true;
		blnPostEvaluacion = true;
		pgValidDatos = false;
		blnDatosSocio = true;
	}
	
	public void verSolicitudLiquidacion(ActionEvent event){	
		bdMondoFondoRetiroTotal = BigDecimal.ZERO;
		cancelarGrabarSolicitud();
		strSolicitudLiquidacion = Constante.MANTENIMIENTO_ELIMINAR;
		blnViewSolicitudLiquidacion = true;
		
		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
//		List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;

		ExpedienteLiquidacionId expedienteLiquidacionId = new ExpedienteLiquidacionId();
		
		expedienteLiquidacionId.setIntPersEmpresaPk(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntPersEmpresaPk());
		expedienteLiquidacionId.setIntItemExpediente(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntItemExpediente());
		//expedienteLiquidacionId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getId().get == null ? new Integer(strIntItemDetExpediente) : new Integer(	strItemDetExpediente));
		Boolean blnContinuaRecorrido = true;
		Cuenta cuentaExpediente = null;
		
		try {
			blnMostrarDescripcionTipoLiquidacion = Boolean.FALSE;
			// devuelve el crongrama son id vacio.
			//beanExpedientePrevision 
			beanExpedienteLiquidacion = liquidacionFacade.getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(expedienteLiquidacionId);
			
			
			if (beanExpedienteLiquidacion != null) {
					//listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				
					// Recuperamos al Socio 
						if(beanExpedienteLiquidacion.getId() != null){
							cargarDescripcionTipoLiquidacion();
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = null;
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta());

							// cgd - 30.08.2013
							cuentaSocio = cuentaFacade.getListaCuentaPorPkTodoEstado(cuentaIdSocio);
							//cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
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
										
										
										cuentaExpediente = new Cuenta();
										cuentaExpediente.setId(new CuentaId());
										Integer intCuenta = 0;
										if(beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle() != null
												&& !beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().isEmpty()){
											intCuenta = beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta();
											
										}
										cuentaExpediente.getId().setIntCuenta(intCuenta);
										cuentaExpediente.getId().setIntPersEmpresaPk(beanExpedienteLiquidacion.getId().getIntPersEmpresaPk());
										
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION, cuentaExpediente);
										/*
										socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
													new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
													persona.getDocumento().getStrNumeroIdentidad(),
													Constante.PARAM_EMPRESASESION);
										
										*/
										//-------------------agregado 03062013----------------->
										if (socioComp != null) {
											if (socioComp.getCuenta() != null) {
														if(blnContinuaRecorrido){
															if(socioComp.getPersona() != null){
																// •	Estado de persona = 1 activa 
																//jchavez 03.06.2014
																//no tomar en cuenta el estado al ver la solicitud
//																if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){
						
																	for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
																		if(true){
																			if(socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																				if (socioEstructura.getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)==0) {
																					strMsgErrorValidarDatos = "";
																					socioComp.getSocio().setSocioEstructura(socioEstructura);
						
																					//if(!(socioComp.getCuenta().getIntParaSubTipoCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0)){
																					//•	Cuenta situación = 1 activa
																					if(socioComp.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR)==0){
																						
																						// agregar validacion: no debe existir expedientes de prevision.
																						pgValidDatos = false;
																						blnDatosSocio = true;
																						beanSocioComp = socioComp;
						
																						// strDescripcionTipoCuenta
//																						listaCuentaSocio = new ArrayList<CuentaComp>();
																						listaCuentaSocio.clear();
																						CuentaComp cuentaCompSocio = new CuentaComp();
																						cuentaCompSocio.setCuenta(beanSocioComp.getCuenta());
						
																						// 1. Secarga la descripcion del Tipo de Cuenta - lista 1
																						for(int t=0; t<listaDescripcionTipoCuenta.size();t++){
																							if(listaDescripcionTipoCuenta.get(t).getIntIdDetalle().compareTo(beanSocioComp.getCuenta().getIntParaTipoCuentaCod())==0){
																								cuentaCompSocio.setStrDescripcionTipoCuenta(listaDescripcionTipoCuenta.get(t).getStrDescripcion());
																								break;
																							}
																						}
						
																						// 2. De momento solo hay una cuenta x socio
																						listaCuentaSocio.add(cuentaCompSocio);
																						String strDescCuenta = listaCuentaSocio.get(0).getStrDescripcionTipoCuenta();
						
																						//List<CuentaConcepto> lstCtaCto = null;
																						listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());
						
																						// Solo se deben visualizar 4 cuentas: Aporte, Retiro, Ahoroo y Depaosito
																						listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();
						
																						CuentaConceptoComp cuentaConceptoComp = null;
																						CuentaConcepto cuentaConcepto = null;
																						for(int k=0; k<listaCuentaConcepto.size();k++){
																							cuentaConcepto = listaCuentaConcepto.get(k);
																							cuentaConceptoComp = new CuentaConceptoComp();
																							// cargando la descripcion del tipo de cuenta
																							cuentaConceptoComp.setStrDescripcionCuenta(strDescCuenta); 
						
																							// cargando la descripcion de cada cuenta concepto
																							for (Tabla descripcion : listaDescripcionCuentaConcepto) {
																								CuentaConceptoDetalle detalle = null;
																								if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
																								&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
																									detalle = new CuentaConceptoDetalle();
																									detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);
						
																									if(descripcion.getIntIdDetalle().compareTo(detalle.getIntParaTipoConceptoCod())==0){
																										//Autor: jchavez / Tarea: Modificación / Fecha: 29.08.2014 /
																										cuentaConceptoComp.setIntParaTipoConceptoCod(detalle.getIntParaTipoConceptoCod());
																										//FIN jchavez - 29.08.2014
																										cuentaConceptoComp.setStrDescripcionConcepto(descripcion.getStrDescripcion());
																										cuentaConceptoComp.setStrNumeroCuenta(beanSocioComp.getCuenta().getStrNumeroCuenta());
																										cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
																										if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0
																										 ||detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
																											cuentaConceptoComp.getCuentaConcepto().setChecked(true);
																										}
																										BigDecimal bdMontoInteresCalculado = BigDecimal.ZERO;
																										if (detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ) {
																											bdMontoInteresCalculado = calcularInteresRetiroAcumulado(cuentaConceptoComp);
																											bdMontoInteresFdoRetiro = bdMontoInteresCalculado;
																										}
																										break;
																									}
																								}	
																							}
						
																							listaCuentaConceptoComp.add(cuentaConceptoComp);
																						}
																																												
																						EstructuraId estructuraId = new EstructuraId();
																						Estructura estructura = null;
																						estructuraId.setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
																						estructuraId.setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
																						estructura = estructuraFacade.getEstructuraPorPk(estructuraId);
						
																						beanEstructuraSocioComp = estructura;
																						estructura.getListaEstructuraDetalle();
						
																						if(estructura.getListaEstructuraDetalle() != null){
																							for(int k=0; k<estructura.getListaEstructuraDetalle().size();k++){
																								estructura.getListaEstructuraDetalle().get(k).getListaSubsucursal();
																							}
																						}
						
																						blnShowDivFormSolicitudLiquidacion = true;
																						blnShowValidarDatos = false;
						
																						// cargando sucursal y subsucursal del socio
																						cargarListaTablaSucursal();
																						seleccionarSucursal();
						
																					} else {
																						strMsgErrorValidarDatos = strMsgErrorValidarDatos +"La sub condición de la Cuenta no es Regular. ";
																					}
						
																				}else{
																					strMsgErrorValidarDatos = strMsgErrorValidarDatos+ "El socio no posee una estructura de origen.";
																				}
																			}
																		}
//																	}
						
																	blnContinuaRecorrido = false;
																	cargarDescripcionUEjecutorasConcatenadas(socioComp);
																	
						
																}
	
											} 
										}
										
										//--------------------------------------------->
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
									
					for (CuentaConceptoComp cuentaConceptoComp : listaCuentaConceptoComp) {
						for (ExpedienteLiquidacionDetalle detalleExpediente : beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle()) {
							//Autor: jchavez / Tarea: modificacion para salir del paso. REVISAR / Fecha: 19.12.2014
							if (cuentaConceptoComp.getCuentaConcepto()!=null) {
								if(cuentaConceptoComp.getCuentaConcepto().getId().getIntItemCuentaConcepto().
									compareTo(detalleExpediente.getId().getIntItemCuentaConcepto())==0){
									
									CuentaConceptoDetalle detalle = cuentaConceptoComp.getCuentaConcepto().getListaCuentaConceptoDetalle().get(0);
									if(detalle.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
										cuentaConceptoComp.setLstCuentaConceptoDetalle(new ArrayList<CuentaConceptoDetalle>());
										cuentaConceptoComp.getLstCuentaConceptoDetalle().add(detalle);
										bdMontoInteresFdoRetiro = calcularInteresRetiroAcumulado(cuentaConceptoComp);
										break;
									}
								}
							}							
						}
					}
					
					regenerarCuentasConceptoBase();
					if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
						regenerarBenficiariosVista_m1();
					}else{
						regenerarBenficiariosVista_m1_Titular();
					}
					
									
					// cargando fechas de solicitud...
					strFechaRenuncia = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaRenuncia());
					strFechaProgramacionPago = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaProgramacion());
					strFechaRecepcionRenuncia = Constante.sdf.format(beanExpedienteLiquidacion.getDtFechaRecepcion());
					System.out.println("getDtFechaRecepcion()---->  "+beanExpedienteLiquidacion.getDtFechaRecepcion());
					
				if (beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp()!= null) {
					listaRequisitoLiquidacionComp = beanExpedienteLiquidacion.getListaRequisitoLiquidacionComp();
				}
				
				
//				List<ExpedienteLiquidacionDetalle> listaDetalleLiquidacion = null;
				//jchavez 09.06.2014
				mostrarlistaAutorizacionesPrevias(beanExpedienteLiquidacion.getListaEstadoLiquidacion());
				//
				cargarListaTablaSucursal();
				seleccionarSucursal();
				evaluarSolicitudModSinValidaciones(event);
				
				//Autor: jchavez / Tarea: Modificación / Fecha: 29.08.2014
				for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
					bdMondoFondoRetiroTotal = bdMondoFondoRetiroTotal.add(beneficiarioVista.getBdMontoRetiro());
				}
				//FIN jchavez - 29.08.2014 	
			}
			
						}}
		} catch (BusinessException e) {
			log.error("Error -BusinessException - en irModificarSolicitudLiquidacion --->  " + e);
			e.printStackTrace();
		} 
		catch (ParseException e1) {
			log.error("Error -ParseException - en irModificarSolicitudLiquidacion --->  " + e1);
			log.error(e1);
		} catch (Exception e2) {
			log.error("Error -Exception - en irModificarSolicitudLiquidacion --->  " + e2);
			log.error(e2);
		}

		blnShowDivFormSolicitudLiquidacion = true;
		blnPostEvaluacion = true;
		pgValidDatos = false;
		blnDatosSocio = true;
		blnVerSolExpLiquidacion = true;
	}
	
	//-------------------------------------------------------------------------------------------------------------->
	
	/**
	 * 
	 */
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionadoBusquedaComp = (ExpedienteLiquidacionComp)event.getComponent().getAttributes().get("item");
			
			if(registroSeleccionadoBusquedaComp != null){
				registroSeleccionadoBusqueda = registroSeleccionadoBusquedaComp.getExpedienteLiquidacion();
				
				validarOperacionActualizar();
				validarOperacionEliminar();
				validarEstadoCuenta(registroSeleccionadoBusqueda);
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	/**
	 * Valida la Situacion de la cuenta del expediente seleccionado, a find e permitir solo los de Estado ACTIVO.
	 * Devuelve TRUE si el estadod e la cuenta NO ES ACTIVO.
	 * @return
	 */
	public Boolean validarEstadoCuenta(ExpedienteLiquidacion expediente){
		Boolean blnValido= null;
		CuentaFacadeRemote cuentaFacade = null;
		//CuentaId ctaIdExp = null;
		Cuenta ctaExpediente = null;
		strMensajeValidacionCuenta = "";
		List<ExpedienteLiquidacionDetalle> lstDetalle = null;
		
		try {
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			lstDetalle = liquidacionFacade.getListaExpedienteLiquidacionDetallePorExpediente(expediente);
			
			if(lstDetalle != null && !lstDetalle.isEmpty()){
				CuentaId cuentaId = new CuentaId();
				//cuenta.setId(new CuentaId());
				
				cuentaId.setIntCuenta(lstDetalle.get(0).getId().getIntCuenta());
				cuentaId.setIntPersEmpresaPk(lstDetalle.get(0).getId().getIntPersEmpresa());

				ctaExpediente = cuentaFacade.getListaCuentaPorPkTodoEstado(cuentaId);
				if(ctaExpediente != null){
					if(ctaExpediente.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						blnValido = Boolean.FALSE;
						blnBloquearXCuenta =  Boolean.FALSE;
						strMensajeValidacionCuenta = "";
					}else{
						blnBloquearXCuenta =  Boolean.TRUE;
						blnValido = Boolean.TRUE;
						strMensajeValidacionCuenta = "La Solicitud de Liquidación pertenece a una Cuenta No Vigente. No se puede realizar alguna operación.";
					}
				}

			}
			
		} catch (Exception e) {
			log.error("Error en validarEstadoCuenta ----> "+e);
		}
		return blnValido;
	}
	
	/**
	 * Calcula el monto de los aportes, retiro, ahorro, deposito, total y monto de la tabla de beneficiarios.
	 * Se recupera unalista de beneficairios con porcentajes y montos calculados.
	 */
	public List<BeneficiarioLiquidacion> calcularMontosPorcentajeBeneficiarios(){
		List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		try {
			if(listaBeneficiarioLiquidacionVista != null && !listaBeneficiarioLiquidacionVista.isEmpty()){
				 
				listaBeneficiarios = new ArrayList<BeneficiarioLiquidacion>();

				for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
					BigDecimal bdMontoTotalBeneficio = BigDecimal.ZERO;
					
					// Aportes ------------------------------->
					if(beanCuentaConceptoAportes != null && beanCuentaConceptoAportes.getCuentaConcepto().getListaCuentaDetalleBeneficio() != null
						&& !beanCuentaConceptoAportes.getCuentaConcepto().getListaCuentaDetalleBeneficio().isEmpty()){
						List<CuentaDetalleBeneficio> listaDetalleBeneficio = null;
						
						listaDetalleBeneficio = beanCuentaConceptoAportes.getCuentaConcepto().getListaCuentaDetalleBeneficio();
						
						if(listaDetalleBeneficio != null && !listaDetalleBeneficio.isEmpty()){
							
							for (CuentaDetalleBeneficio cuentaDetalleBeneficio : listaDetalleBeneficio) {								
									if(cuentaDetalleBeneficio.getIntItemVinculo().compareTo(beneficiarioVista.getIntItemViculo())==0){
										beneficiarioVista.setBdPorcentajeBeneficioApo(cuentaDetalleBeneficio.getBdPorcentaje()== null ? BigDecimal.ZERO :cuentaDetalleBeneficio.getBdPorcentaje() );
										
										if(beneficiarioVista.getBdPorcentajeBeneficioApo() != null && beneficiarioVista.getBdPorcentajeBeneficioApo().compareTo(BigDecimal.ZERO)!=0){
											beneficiarioVista.setBdMontoAporte(beanCuentaConceptoAportes.getCuentaConcepto().getBdSaldo().multiply(beneficiarioVista.getBdPorcentajeBeneficioApo()).divide(new BigDecimal(100)));
										}else{
											beneficiarioVista.setBdMontoAporte(BigDecimal.ZERO);
										}
										break;
									}
							}
	
						}
						
						bdMontoTotalBeneficio = bdMontoTotalBeneficio.add(beneficiarioVista.getBdMontoAporte());
					}
					
					// Retiro -------------------------------------->
					if(beanCuentaConceptoRetiro != null && beanCuentaConceptoRetiro.getCuentaConcepto().getListaCuentaDetalleBeneficio() != null
							&& !beanCuentaConceptoRetiro.getCuentaConcepto().getListaCuentaDetalleBeneficio().isEmpty()){
							List<CuentaDetalleBeneficio> listaDetalleBeneficio = null;
							
							listaDetalleBeneficio = beanCuentaConceptoRetiro.getCuentaConcepto().getListaCuentaDetalleBeneficio();
							
							if(listaDetalleBeneficio != null && !listaDetalleBeneficio.isEmpty()){
								
								for (CuentaDetalleBeneficio cuentaDetalleBeneficio : listaDetalleBeneficio) {									
										if(cuentaDetalleBeneficio.getIntItemVinculo().compareTo(beneficiarioVista.getIntItemViculo())==0){
											beneficiarioVista.setBdPorcentajeBeneficioRet(cuentaDetalleBeneficio.getBdPorcentaje()== null ? BigDecimal.ZERO :cuentaDetalleBeneficio.getBdPorcentaje() );
											
											if(beneficiarioVista.getBdPorcentajeBeneficioRet() != null && beneficiarioVista.getBdPorcentajeBeneficioRet().compareTo(BigDecimal.ZERO)!=0){
												//jchavez 03.06.2014 se agraga interes de fdo de retiro 
												beneficiarioVista.setBdMontoRetiro((beanCuentaConceptoRetiro.getCuentaConcepto().getBdSaldo().add(bdMontoInteresFdoRetiro)).multiply(beneficiarioVista.getBdPorcentajeBeneficioRet()).divide(new BigDecimal(100)));
											}else{
												beneficiarioVista.setBdMontoRetiro(BigDecimal.ZERO);
											}
											break;
										}
								}
	
							}
							bdMontoTotalBeneficio = bdMontoTotalBeneficio.add(beneficiarioVista.getBdMontoRetiro());
						}
					
					
					// Agremos el beneficiario con todo calculado a la lista.
					beneficiarioVista.setBdMontoTotal(bdMontoTotalBeneficio);
					listaBeneficiarios.add(beneficiarioVista);

				}	
			}

		} catch (Exception e) {
			log.error("Error calcularMontosPorcentajeBeneficiarios --> "+e);
		}
		
		return listaBeneficiarios ;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public List<BeneficiarioLiquidacion> calcularMontosPorcentajeBeneficiarios_Titular(){
		List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		try {
			if(listaBeneficiarioLiquidacionVista != null && !listaBeneficiarioLiquidacionVista.isEmpty()){
				 
				listaBeneficiarios = new ArrayList<BeneficiarioLiquidacion>();

				for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
					BigDecimal bdMontoTotalBeneficio = BigDecimal.ZERO;
					
					// Aportes ------------------------------->
					if(beanCuentaConceptoAportes != null){
						beneficiarioVista.setBdPorcentajeBeneficioApo(new BigDecimal(100) );		
						if(beneficiarioVista.getBdPorcentajeBeneficioApo() != null && beneficiarioVista.getBdPorcentajeBeneficioApo().compareTo(BigDecimal.ZERO)!=0){
							beneficiarioVista.setBdMontoAporte(beanCuentaConceptoAportes.getCuentaConcepto().getBdSaldo());
						}

						bdMontoTotalBeneficio = bdMontoTotalBeneficio.add(beneficiarioVista.getBdMontoAporte());
					}
					
					// Retiro -------------------------------------->
					if(beanCuentaConceptoRetiro != null){
							
							beneficiarioVista.setBdPorcentajeBeneficioRet(new BigDecimal(100));
							if(beneficiarioVista.getBdPorcentajeBeneficioRet() != null && beneficiarioVista.getBdPorcentajeBeneficioRet().compareTo(BigDecimal.ZERO)!=0){
								//JCHAVEZ 03.06.2014 se agrega interes ganado al fondo de retiro
								beneficiarioVista.setBdMontoRetiro(beanCuentaConceptoRetiro.getCuentaConcepto().getBdSaldo().add(bdMontoInteresFdoRetiro));
							}
										
							bdMontoTotalBeneficio = bdMontoTotalBeneficio.add(beneficiarioVista.getBdMontoRetiro());
						}
					
					
					// Agremos el beneficiario con todo calculado a la lista.
					beneficiarioVista.setBdMontoTotal(bdMontoTotalBeneficio);
					listaBeneficiarios.add(beneficiarioVista);

				}	
			}

		} catch (Exception e) {
			log.error("Error calcularMontosPorcentajeBeneficiarios_Titular --> "+e);
		}
		
		return listaBeneficiarios ;
	}
	
	
	
	/**
	 * 
	 * @param beneficiarioVistaAporte
	 * @return
	 */
	public BeneficiarioLiquidacion reCalcularMontosYPorcentajeAportes( BeneficiarioLiquidacion beneficiarioVistaAporte ){
		//List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		List<BeneficiarioLiquidacion> lstBeneficiariosTotal = null;
		try {	
			
				// cuando el beneficiario es el propio titular.
				if(beneficiarioVistaAporte.getIntPersPersonaBeneficiario().compareTo(beanSocioComp.getPersona().getIntIdPersona())==0){
					
					beneficiarioVistaAporte.setBdPorcentajeBeneficioApo(new BigDecimal(100));

					if(beneficiarioVistaAporte.getBdPorcentajeBeneficioApo() != null && beneficiarioVistaAporte.getBdPorcentajeBeneficioApo().compareTo(BigDecimal.ZERO)!=0){
						beneficiarioVistaAporte.setBdMontoAporte(beanCuentaConceptoAportes.getCuentaConcepto().getBdSaldo().multiply(beneficiarioVistaAporte.getBdPorcentajeBeneficioApo()).divide(new BigDecimal(100)));
					}else{
						beneficiarioVistaAporte.setBdMontoAporte(BigDecimal.ZERO);
					}
					
					
					
				}else{

						lstBeneficiariosTotal = liquidacionFacade.getListaBeneficiariosPorExpedienteLiquidacion(beanExpedienteLiquidacion);
	
						Boolean blnAporteRegistrado = false;
					
						for (BeneficiarioLiquidacion beneficiarioLstTotal : lstBeneficiariosTotal) {
						//Boolean blnContinue = true;
						//Buscamos coincidenecioa entre el beneficiario devista y el total
						if(beneficiarioVistaAporte.getIntPersPersonaBeneficiario().compareTo(beneficiarioLstTotal.getIntPersPersonaBeneficiario())==0){
	
							//Estabelecemos si el benficiariolsttoal es aporte o retiro
							if(!blnAporteRegistrado) {
								if(beanCuentaConceptoAportes != null && beanCuentaConceptoAportes.getCuentaConcepto().getListaCuentaDetalleBeneficio() != null
									&& !beanCuentaConceptoAportes.getCuentaConcepto().getListaCuentaDetalleBeneficio().isEmpty()){
									List<CuentaConceptoDetalle> listaDetalleCtaCtoApo = null;
										
										listaDetalleCtaCtoApo = beanCuentaConceptoAportes.getCuentaConcepto().getListaCuentaConceptoDetalle();
										
										if(listaDetalleCtaCtoApo != null && !listaDetalleCtaCtoApo.isEmpty()){
											System.out.println("**********************************************************************************");
											for (CuentaConceptoDetalle cuentaDetalleApo : listaDetalleCtaCtoApo) {
	
													if(cuentaDetalleApo.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0
														&& cuentaDetalleApo.getId().getIntItemCuentaConcepto().compareTo(beneficiarioLstTotal.getId().getIntItemCuentaConcepto())==0)
														{
														beneficiarioVistaAporte.setBdPorcentajeBeneficioApo(beneficiarioLstTotal.getBdPorcentajeBeneficio());
														
														
														if(beneficiarioVistaAporte.getBdPorcentajeBeneficioApo() != null && beneficiarioVistaAporte.getBdPorcentajeBeneficioApo().compareTo(BigDecimal.ZERO)!=0){
															beneficiarioVistaAporte.setBdMontoAporte(beanCuentaConceptoAportes.getCuentaConcepto().getBdSaldo().multiply(beneficiarioVistaAporte.getBdPorcentajeBeneficioApo()).divide(new BigDecimal(100)));
														}else{
															beneficiarioVistaAporte.setBdMontoAporte(BigDecimal.ZERO);
														}
														blnAporteRegistrado=true;
														
														beneficiarioVistaAporte.setBdPorcentajeBeneficioRet(BigDecimal.ZERO);
														beneficiarioVistaAporte.setBdMontoRetiro(BigDecimal.ZERO);
														break;
													}
											}
					
										}
								}
							}
						}
	
					}
				}

		} catch (Exception e) {
			log.error("Error reCalcularMontosYPorcentajeAportes --> "+e);
		}
		
		return beneficiarioVistaAporte ;
	}
	
	
	/**
	 * 
	 * @param beneficiarioVistaRetiro
	 * @return
	 */
	public BeneficiarioLiquidacion reCalcularMontosYPorcentajeRetiro( BeneficiarioLiquidacion beneficiarioVistaRetiro ){
		//List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		List<BeneficiarioLiquidacion> lstBeneficiariosTotal = null;
			try {

				// cuando el beneficiario es el propio titular.
				if(beneficiarioVistaRetiro.getIntPersPersonaBeneficiario().compareTo(beanSocioComp.getPersona().getIntIdPersona())==0){
					
					beneficiarioVistaRetiro.setBdPorcentajeBeneficioRet(new BigDecimal(100));

					if(beneficiarioVistaRetiro.getBdPorcentajeBeneficioRet() != null && beneficiarioVistaRetiro.getBdPorcentajeBeneficioRet().compareTo(BigDecimal.ZERO)!=0){
						beneficiarioVistaRetiro.setBdMontoRetiro((beanCuentaConceptoRetiro.getCuentaConcepto().getBdSaldo().add(bdMontoInteresFdoRetiro)).multiply(beneficiarioVistaRetiro.getBdPorcentajeBeneficioRet()).divide(new BigDecimal(100)));
					}else{
						beneficiarioVistaRetiro.setBdMontoRetiro(BigDecimal.ZERO);
					}
	
				}else{

					lstBeneficiariosTotal = liquidacionFacade.getListaBeneficiariosPorExpedienteLiquidacion(beanExpedienteLiquidacion);

						Boolean blnRetiroRegistrado = false;
						
						for (BeneficiarioLiquidacion beneficiarioLstTotal : lstBeneficiariosTotal) {
							//Boolean blnContinue = true;
							//Buscamos coincidenecioa entre el beneficiario devista y el total
							if(beneficiarioVistaRetiro.getIntPersPersonaBeneficiario().compareTo(beneficiarioLstTotal.getIntPersPersonaBeneficiario())==0){

								//Estabelecemos si el benficiariolsttoal es aporte o retiro
								if(!blnRetiroRegistrado) {
									if(beanCuentaConceptoRetiro != null && beanCuentaConceptoRetiro.getCuentaConcepto().getListaCuentaDetalleBeneficio() != null
										&& !beanCuentaConceptoRetiro.getCuentaConcepto().getListaCuentaDetalleBeneficio().isEmpty()){
										List<CuentaConceptoDetalle> listaDetalleCtaCtoRet = null;
											
											listaDetalleCtaCtoRet = beanCuentaConceptoRetiro.getCuentaConcepto().getListaCuentaConceptoDetalle();
											
											if(listaDetalleCtaCtoRet != null && !listaDetalleCtaCtoRet.isEmpty()){
												System.out.println("**********************************************************************************");
												for (CuentaConceptoDetalle cuentaDetalleRet : listaDetalleCtaCtoRet) {
		
														if(cuentaDetalleRet.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0
															&& cuentaDetalleRet.getId().getIntItemCuentaConcepto().compareTo(beneficiarioLstTotal.getId().getIntItemCuentaConcepto())==0)
															{
															beneficiarioVistaRetiro.setBdPorcentajeBeneficioRet(beneficiarioLstTotal.getBdPorcentajeBeneficio());
															
															
															if(beneficiarioVistaRetiro.getBdPorcentajeBeneficioRet() != null && beneficiarioVistaRetiro.getBdPorcentajeBeneficioRet().compareTo(BigDecimal.ZERO)!=0){
																beneficiarioVistaRetiro.setBdMontoRetiro((beanCuentaConceptoRetiro.getCuentaConcepto().getBdSaldo().add(bdMontoInteresFdoRetiro)).multiply(beneficiarioVistaRetiro.getBdPorcentajeBeneficioRet()).divide(new BigDecimal(100)));
															}else{
																beneficiarioVistaRetiro.setBdMontoRetiro(BigDecimal.ZERO);
															}
															blnRetiroRegistrado=true;
															break;
														}
												}
						
											}
									}
								}
							}

						}
						
				}
			} catch (Exception e) {
				log.error("Error reCalcularMontosYPorcentajeRetiro --> "+e);
			}
			
			return beneficiarioVistaRetiro ;
		}
	
	
	/**
	 * Muestra los % y montos de aportes  y/o retiro para los beneficaios vista recuperados.
	 * En base a los datos ya guradados en bd.
	 * @return
	 */
	public List<BeneficiarioLiquidacion> reCalcularMontosYPorcentajeBeneficiariosParaEdicion_m2(){
		List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		List<BeneficiarioLiquidacion> lstBeneficiariosCargado = null;
		try {
			if(listaBeneficiarioLiquidacionVista != null && !listaBeneficiarioLiquidacionVista.isEmpty()){
				listaBeneficiarios = new ArrayList<BeneficiarioLiquidacion>();
				listaBeneficiarios = listaBeneficiarioLiquidacionVista;
				lstBeneficiariosCargado =  new ArrayList<BeneficiarioLiquidacion>();

				
				// validar retiro
					
					for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarios) {
						BigDecimal bdMontoTotalBenef = BigDecimal.ZERO;
						
						
						beneficiarioVista = reCalcularMontosYPorcentajeAportes(beneficiarioVista);
						
						Integer nroDet = 0;
						nroDet= beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().size();
						if(nroDet > 1) {
							beneficiarioVista = reCalcularMontosYPorcentajeRetiro(beneficiarioVista);
						}
						
						bdMontoTotalBenef = beneficiarioVista.getBdMontoAporte().add(beneficiarioVista.getBdMontoRetiro());
						beneficiarioVista.setBdMontoTotal(bdMontoTotalBenef);
						lstBeneficiariosCargado.add(beneficiarioVista);
						
						}

			}
				
					
			//}

		} catch (Exception e) {
			log.error("Error reCalcularMontosYPorcentajeBeneficiariosParaEdicion_m2 --> "+e);
		}
		
		return lstBeneficiariosCargado ;
	}
	

	/**
	 * 
	 * @param event
	 * @throws ParseException
	 */
	public void evaluarSolicitudMod(ActionEvent event) throws ParseException {
		log.info("------------------------Debugging evaluarPrevision------------------------");
		EstructuraDetalle estructuraDetalle = null;
		///ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
//		Date today = new Date();
//		String strToday = Constante.sdf.format(today);
//		Date dtToday = Constante.sdf.parse(strToday);
		limpiarMensajesIsValidoExpediente();
		if (procedeEvaluacion_mod(beanExpedienteLiquidacion) == false) {
			log.info("Datos de Previsión no válidos. Se aborta el proceso de Evaluacion de Solicitud.");
			return;
		}

		
		try {
			
			if (beanSocioComp.getSocio().getSocioEstructura() != null) {
				estructuraDetalle = new EstructuraDetalle();
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());

				
				recuperarPeriodoUltimoDescuento();
				
				if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
					isValidCondicionSocioSinDeudaPendiente();
					isValidCondicionSinGarantesDeudores();
					isValidCondicionbeneficioDeFondoSepelio();
					blnMostrarObjeto= true;
				}else {
					isValidCondicionSocioSinDeudaPendiente();
					isValidCondicionSinGarantesDeudores();
					blnMostrarObjeto= false;
				}
				
				blnPostEvaluacion=true;
				//mostrarArchivosAdjuntos(event);
				
				
				//--------------------------------->
				
				if (beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA) == 0) {
					blnIsRenuncia = true;
				} else {
					blnIsRenuncia = false;
				}
				
				if (beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO) == 0) {
					blnIsFallecimiento = true;
				} else {
					blnIsFallecimiento = false;
				}
				
				//---------------------------------------------------->
						
				
			}
			log.info("message"+strSolicitudLiquidacion);
			blnShowDivFormSolicitudLiquidacion = true;

	} catch (Exception e) {
				log.error("error en evaluarSolicitud --> " + e);
					e.printStackTrace();
		}/* finally{
			mostrarArchivosAdjuntos(event);
		}*/

	}
	
	public void evaluarSolicitudModSinValidaciones(ActionEvent event) throws ParseException {
		log.info("------------------------Debugging evaluarPrevision------------------------");
		EstructuraDetalle estructuraDetalle = null;
		///ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
//		Date today = new Date();
//		String strToday = Constante.sdf.format(today);
//		Date dtToday = Constante.sdf.parse(strToday);
		limpiarMensajesIsValidoExpediente();
//		if (procedeEvaluacion(beanExpedienteLiquidacion) == false) {
//			log.info("Datos de Previsión no válidos. Se aborta el proceso de Evaluacion de Solicitud.");
//			return;
//		}

		
		try {
			
			if (beanSocioComp.getSocio().getSocioEstructura() != null) {
				estructuraDetalle = new EstructuraDetalle();
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());

				
				recuperarPeriodoUltimoDescuento();
				
				if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
					isValidCondicionSocioSinDeudaPendiente();
					isValidCondicionSinGarantesDeudores();
					isValidCondicionbeneficioDeFondoSepelio();
					blnMostrarObjeto= true;
				}else {
					isValidCondicionSocioSinDeudaPendiente();
					isValidCondicionSinGarantesDeudores();
					blnMostrarObjeto= false;
				}
				
				blnPostEvaluacion=true;
				//mostrarArchivosAdjuntos(event);
				
				
				//--------------------------------->
				
				if (beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA) == 0) {
					blnIsRenuncia = true;
				} else {
					blnIsRenuncia = false;
				}
				
				if (beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO) == 0) {
					blnIsFallecimiento = true;
				} else {
					blnIsFallecimiento = false;
				}
				
				//---------------------------------------------------->
						
					
			}

	} catch (Exception e) {
				log.error("error en evaluarSolicitud --> " + e);
					e.printStackTrace();
		}/* finally{
			mostrarArchivosAdjuntos(event);
		}*/

	}
	
	
	/**
	 * 
	 * @param eventO
	 * @throws ParseException
	 */
	public void evaluarSolicitud(ActionEvent event) throws ParseException {
		log.info("------------------------Debugging evaluarPrevision------------------------");
		EstructuraDetalle estructuraDetalle = null;
		///ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
//		Date today = new Date();
//		String strToday = Constante.sdf.format(today);
//		Date dtToday = Constante.sdf.parse(strToday);
		limpiarMensajesIsValidoExpediente();
		limpiarObservaciones();
		if (procedeEvaluacion(beanExpedienteLiquidacion) == false) {
			log.info("Datos de Previsión no válidos. Se aborta el proceso de Evaluacion de Solicitud.");
			return;
		}

		
		try {
			
			if (beanSocioComp.getSocio().getSocioEstructura() != null) {
				//Autor: jchavez / Tarea: Modificacion / Fecha: 13.12.2014 / Se comenta dado que no se usa.
				estructuraDetalle = new EstructuraDetalle();
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
				//Fin jchavez - 13.12.2014
				
				recuperarPeriodoUltimoDescuento();
				cargarDescripcionTipoLiquidacion();
				
				if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_FALLECIMIENTO)==0){
					isValidCondicionSocioSinDeudaPendiente();
					isValidCondicionSinGarantesDeudores();
					isValidCondicionbeneficioDeFondoSepelio();
					blnMostrarObjeto= true;
					
				}else {
					isValidCondicionSocioSinDeudaPendiente();
					isValidCondicionSinGarantesDeudores();
					blnMostrarObjeto= false;
				}
				
				blnPostEvaluacion=true;
				generarDetallesExpediente();
				mostrarArchivosAdjuntos(event);
						
					
			}
			log.info("PASO EVALUACION: "+blnPostEvaluacion);
	} catch (Exception e) {
				log.error("error en evaluarSolicitud --> " + e);
					e.printStackTrace();
			} 

	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public void cargarDescripcionTipoLiquidacion(){
		strDescripcionTipoLiquidacion = "";
		blnMostrarDescripcionTipoLiquidacion = Boolean.TRUE;
		try {
			if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion() != null 
				&& beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(0)!=0){
				
				for (Tabla tablaDesc : listaSubOperacion) {
					if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(tablaDesc.getIntIdDetalle())==0){
						strDescripcionTipoLiquidacion = tablaDesc.getStrDescripcion();
						blnMostrarDescripcionTipoLiquidacion = Boolean.FALSE;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en cargarDescripcionTipoLiquidacion ---> "+e);
		}
	}
	
	
	/**
	 * Recupera el periodo del ultimo perido efectuado. 
	 * En base a la socioestructura de origen.
	 */
	public void recuperarPeriodoUltimoDescuento(){	
//		Integer ultimoMovimiento = new Integer(0);
		SocioEstructura socioEstructuraOrigen = null;
//		Integer intEmpresa = new Integer(0);
		EstructuraId pk = null;
//		Integer intTipoSocio = new Integer(0);
//		Integer intModalidad = new Integer(0);

		try {
			socioEstructuraOrigen = beanSocioComp.getSocio().getSocioEstructura();
			if(socioEstructuraOrigen != null){
//				intEmpresa = socioEstructuraOrigen.getId().getIntIdEmpresa();
				pk = new EstructuraId();
				pk.setIntCodigo(socioEstructuraOrigen.getIntCodigo());
				pk.setIntNivel(socioEstructuraOrigen.getIntNivel());
//				intTipoSocio = socioEstructuraOrigen.getIntTipoSocio();
//				intModalidad = socioEstructuraOrigen.getIntModalidad();

				
				//public Integer getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidad(Integer intEmpresa,EstructuraId pk,Integer intTipoSocio,Integer intModalidad) throws BusinessException{

				//ultimoMovimiento =  planillaFacade.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidad(intEmpresa, pk, intTipoSocio, intModalidad);
//				 ultimoMov = null;
				
				//ultimoMov =	conceptoFacade.getListPeriodoMaxCuentaEmpresa(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), beanSocioComp.getCuenta().getId().getIntCuenta());
				
				 List<Movimiento> ultimoMov = conceptoFacade.getMaxMovXCtaEmpresaTipoMov(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), beanSocioComp.getCuenta().getId().getIntCuenta(), Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_POR_PLANILLA);
				if(ultimoMov != null && !ultimoMov.isEmpty()) {
					
					beanExpedienteLiquidacion.setIntPeriodoUltimoDescuento(ultimoMov.get(0).getIntPeriodoPlanilla());
					//beanExpedienteLiquidacion.setIntPeriodoUltimoDescuento(ultimoMovimiento);
				}	
			}
			
		} catch (Exception e) {
			System.out.println("Error en recuperarDatosSolicitud --> "+e);
		}

	}
	
	
	/**
	 * 
	 * @return
	 */
	public boolean validacionSuman100(){
		blnSumaPorcentajes = true;
//		BigDecimal bdSumaPorc = BigDecimal.ZERO;
		strMsgTxtValidacionbeneficiarios = "";
		//List<BeneficiarioLiquidacion> listaBeneficiario=listaBeneficiarioLiquidacionVista;
		
		if(beneficiarioTotales != null){
			if(beanCuentaConceptoAportes != null && beanCuentaConceptoRetiro != null){
				if(beneficiarioTotales.getBdPorcentajeBeneficioApo().compareTo(new BigDecimal(100))!= 0
						|| beneficiarioTotales.getBdPorcentajeBeneficioRet().compareTo(new BigDecimal(100))!= 0){
						strMsgTxtValidacionbeneficiarios = "Los Porcentajes de Aportes y F. Retiro deberían sumar 100%. Corregir y Recalcular.";
						blnSumaPorcentajes = Boolean.FALSE;
					}
			}else if(beanCuentaConceptoRetiro == null){
				if(beneficiarioTotales.getBdPorcentajeBeneficioApo().compareTo(new BigDecimal(100))!= 0){
						strMsgTxtValidacionbeneficiarios = "Los Porcentajes de Aportes deberían sumar 100%. Corregir y Recalcular.";
						blnSumaPorcentajes = Boolean.FALSE;
					}
			}
		}
		
		
		return blnSumaPorcentajes;
	}
	
	
	/**
	 * 
	 * @param stream
	 * @param object
	 * @throws IOException
	 */
	public void paintImage(OutputStream stream, Object object)throws IOException {
		stream.write(((MyFile) object).getData());
	}
	
	
	/**
	 * 
	 * @param event
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 * @throws IOException
	 */
	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException {
		log.info("-------------------------------------Debugging SocioController.putFile-------------------------------------");
		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		
		 System.out.println("fileupload.getObjArchivo().getId().getIntParaTipoCod() ----  "+fileupload.getObjArchivo().getId().getIntParaTipoCod());
		if (listaRequisitoLiquidacionComp != null) {
			
			
			
			for (RequisitoLiquidacionComp requisitoLiquidacionComp : listaRequisitoLiquidacionComp) {
			//-------------- SOLICITUD_RENUNCIA_SOCIO ------------------------
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_RENUNCIA_SOCIO)) {
					if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_SOLICITUD_RENUNCIA_SOCIO)
						//&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						requisitoLiquidacionComp.setArchivoAdjunto(fileupload.getObjArchivo());
						log.info("byteImg.length: "	+ fileupload.getDataImage().length);
						byte[] byteImg = fileupload.getDataImage();
						MyFile file = new MyFile();
						file.setLength(byteImg.length);
						file.setName(fileupload.getObjArchivo()
								.getStrNombrearchivo());
						file.setData(byteImg);
						requisitoLiquidacionComp.setFileDocAdjunto(file);
						break;
					}
				}

				//-------------- SOLICITUD_FICHA_EMITIDA_SISTEMA ------------------------
					if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FICHASOCIO_SISTEMA)) {
						if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_SOLICITUD_FICHA_EMITIDA_SISTEMA)
							//&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							requisitoLiquidacionComp.setArchivoAdjunto(fileupload.getObjArchivo());
							log.info("byteImg.length: "	+ fileupload.getDataImage().length);
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo()
									.getStrNombrearchivo());
							file.setData(byteImg);
							requisitoLiquidacionComp.setFileDocAdjunto(file);
							break;
						}
					}

				//-------------- SUCESION_INTESTADA ------------------------
					if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO)) {
						if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_SUCESION_INTESTADA)
							//&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							requisitoLiquidacionComp.setArchivoAdjunto(fileupload.getObjArchivo());
							log.info("byteImg.length: "	+ fileupload.getDataImage().length);
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo()
									.getStrNombrearchivo());
							file.setData(byteImg);
							requisitoLiquidacionComp.setFileDocAdjunto(file);
							break;
						}
					}

				//-------------- PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_COPIA_DNI_FAMILIAR ------------------------
					if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
						if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_COPIA_DNI_FAMILIAR)
							//&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							requisitoLiquidacionComp.setArchivoAdjunto(fileupload.getObjArchivo());
							log.info("byteImg.length: "	+ fileupload.getDataImage().length);
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo()
									.getStrNombrearchivo());
							file.setData(byteImg);
							requisitoLiquidacionComp.setFileDocAdjunto(file);
							break;
						}
					}

				//-------------- PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_COPIA_DNI_SOCIO ------------------------
					if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
						if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_COPIA_DNI_SOCIO)
							//&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							requisitoLiquidacionComp.setArchivoAdjunto(fileupload.getObjArchivo());
							log.info("byteImg.length: "	+ fileupload.getDataImage().length);
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo()
									.getStrNombrearchivo());
							file.setData(byteImg);
							requisitoLiquidacionComp.setFileDocAdjunto(file);
							break;
						}
					}

				//-------------- PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_PARTIDA_DEFUNCION ------------------------
					if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO)) {
						if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_PARTIDA_DEFUNCION)
							//&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							requisitoLiquidacionComp.setArchivoAdjunto(fileupload.getObjArchivo());
							log.info("byteImg.length: "	+ fileupload.getDataImage().length);
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo()
									.getStrNombrearchivo());
							file.setData(byteImg);
							requisitoLiquidacionComp.setFileDocAdjunto(file);
							break;
						}
					}

			}

		}
	}
	
	
	/**
	 * ReCUPERA LA RUTRA DONDE SE GRABAN LOS ADJUNTOS
	 * @param lista
	 * @throws BusinessException
	 */
	public void renombrarArchivo(List<RequisitoLiquidacionComp> lista) throws BusinessException {
		TipoArchivo tipoArchivo = null;
		Archivo archivo = null;
		
		try {
			for (RequisitoLiquidacionComp requisitoLiquidacionComp : lista) {
				//tipoArchivo = new TipoArchivo();
				//archivo = new Archivo();
				//archivo.setId(new ArchivoId());
				
				// -------------------------  LIQUIDACION_SOLICITUD_RENUNCIA_SOCIO  -----------------------------------------------------
				if (requisitoLiquidacionComp.getArchivoAdjunto() != null) {
					if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_RENUNCIA_SOCIO)
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoLiquidacionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoLiquidacionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoLiquidacionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
						break;
						
					}
					// -------------------------  LIQUIDACION_SOLICITUD_FICHA_EMITIDA_SISTEMA  -----------------------------------------------------
					if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FICHASOCIO_SISTEMA)
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoLiquidacionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoLiquidacionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoLiquidacionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
						break;
					}

					// -------------------------  LIQUIDACION_SUCESION_INTESTADA  -----------------------------------------------------
					
					if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO)
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoLiquidacionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoLiquidacionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());

						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoLiquidacionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
						break;
					}
					
					// -------------------------  LIQUIDACION_COPIA_DNI_FAMILIAR  -----------------------------------------------------
					if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoLiquidacionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoLiquidacionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());

						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoLiquidacionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
						break;
					}
					
					// ------------------------- LIQUIDACION_COPIA_DNI_SOCIO  -----------------------------------------------------

					if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoLiquidacionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoLiquidacionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());

						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoLiquidacionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
						break;
					}
					
					
					// -------------------------  LIQUIDACION_PARTIDA_DEFUNCION  -----------------------------------------------------
					if (requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO)
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoLiquidacionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoLiquidacionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoLiquidacionComp.getArchivoAdjunto().getId().getIntParaTipoCod());

						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoLiquidacionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
						break;
					}
				}

			}
		} catch (BusinessException e) {
			log.error("Error BusinessException en renombrarArchivo ---> "+e);
			throw e;
		}
	}

	
	
	/**
	 * Asosciado al boton Recalcular del formulario de liquidacion.
	 * 
	 */
	public void reCalcularMontosGrillaBeneficiarios (){
		List<BeneficiarioLiquidacion> listaBeneficiarios = null;
		
		try {
			
			if(listaBeneficiarioLiquidacionVista != null && !listaBeneficiarioLiquidacionVista.isEmpty()){
				listaBeneficiarios = new ArrayList<BeneficiarioLiquidacion>();
				for (BeneficiarioLiquidacion beneficiarioVista : listaBeneficiarioLiquidacionVista) {
					
					BigDecimal bdPorcApoEdit = BigDecimal.ZERO;
					BigDecimal bdMontoApoEdit = BigDecimal.ZERO;
					BigDecimal bdPorcRetEdit = BigDecimal.ZERO;
					BigDecimal bdMontoRetEdit = BigDecimal.ZERO;
					BigDecimal bdMontoTotalBeneficio = BigDecimal.ZERO;
					
					if(beanCuentaConceptoAportes != null){
						// Aportes ------------------------------->							
						if(beneficiarioVista.getBdPorcentajeBeneficioApo() != null){
							bdPorcApoEdit = beneficiarioVista.getBdPorcentajeBeneficioApo();
						}
						
						beneficiarioVista.setBdPorcentajeBeneficioApo(bdPorcApoEdit);
						bdMontoApoEdit = beanCuentaConceptoAportes.getCuentaConcepto().getBdSaldo().multiply(bdPorcApoEdit).divide(new BigDecimal(100), 2,RoundingMode.HALF_UP);
						beneficiarioVista.setBdMontoAporte(bdMontoApoEdit);
						
						bdMontoTotalBeneficio = bdMontoTotalBeneficio.add(beneficiarioVista.getBdMontoAporte());
	
					}
					
					if(beanCuentaConceptoRetiro != null){
						// Retiro -------------------------------------->		
						if(beneficiarioVista.getBdPorcentajeBeneficioRet() != null){
							bdPorcRetEdit = beneficiarioVista.getBdPorcentajeBeneficioRet();
						}
						
						beneficiarioVista.setBdPorcentajeBeneficioRet(bdPorcRetEdit);
						bdMontoRetEdit = (beanCuentaConceptoRetiro.getCuentaConcepto().getBdSaldo().add(bdMontoInteresFdoRetiro)).multiply(bdPorcRetEdit).divide(new BigDecimal(100), 2,RoundingMode.HALF_UP);
						beneficiarioVista.setBdMontoRetiro(bdMontoRetEdit);
						
						bdMontoTotalBeneficio = bdMontoTotalBeneficio.add(beneficiarioVista.getBdMontoRetiro());
	
					}

					
					// Agremos el beneficiario con todo calculado a la lista.
					beneficiarioVista.setBdMontoTotal(bdMontoTotalBeneficio);
					listaBeneficiarios.add(beneficiarioVista);

					}
				
				
			}
			listaBeneficiarioLiquidacionVista = listaBeneficiarios;
			calcularBeneficiarioTotales();
			validacionSuman100();
			if(agregarBeneficiarioSocio()!= null && !agregarBeneficiarioSocio().isEmpty()){
				beanExpedienteLiquidacion.setListaExpedienteLiquidacionDetalle(agregarBeneficiarioSocio());
			}
			

		} catch (Exception e) {
			log.error("Error en reCalcularMontosGrillaBeneficiarios ---> "+e);
		}
		
		
	}
	
	
	/**
	 * Definimos la descripcion del adjunto y steamos al fileupload
	 * loa parametros del archivo mediante:
	 * setParamArchivo(Integer pIntItemArchivo, Integer pIntItemHistorico, Integer pIntTipoArchivo)

	 * @param event
	 */
	public void adjuntarDocumento(ActionEvent event) {
		log.info("-------------------------------------Debugging adjuntarDocumento -------------------------------------");
		String strParaTipoDescripcion = getRequestParameter("intParaTipoDescripcion");
		String strParaTipoOperacionPersona = getRequestParameter("intParaTipoOperacionPersona");
		log.info("strParaTipoDescripcion: " + strParaTipoDescripcion);
		log.info("strParaTipoOperacionPersona: " + strParaTipoOperacionPersona);
		Integer intParaTipoDescripcion = new Integer(strParaTipoDescripcion);
		Integer intParaTipoOperacionPersona = new Integer(
				strParaTipoOperacionPersona);
		FileUploadControllerServicio fileupload = null;
		

		try {
			
			this.intParaTipoDescripcion = intParaTipoDescripcion;
			this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;
			
			fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
			
			/*if(fileupload == null){
				fileupload = new FileUploadControllerServicio();	
			}*/
			fileupload.setStrDescripcion("Seleccione el archivo que desea adjuntar.");
			fileupload.setFileType(FileUtil.imageTypes);
			Integer intItemArchivo = null;
			Integer intItemHistorico = null;

			if (listaRequisitoLiquidacionComp != null) {
				for (RequisitoLiquidacionComp requisitoLiquidacionComp : listaRequisitoLiquidacionComp) {
					
					// -------------------------  LIQUIDACION_SOLICITUD_RENUNCIA_SOCIO  -----------------------------------------------------
					if (intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_SOLICITUD_RENUNCIA_SOCIO)
						&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoLiquidacionComp.getRequisitoLiquidacion()!= null) {
							intItemArchivo = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo();
							intItemHistorico = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_RENUNCIA_SOCIO);
						break;
					}
					
					// -------------------------  LIQUIDACION_SOLICITUD_FICHA_EMITIDA_SISTEMA  -----------------------------------------------------
					if (intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_SOLICITUD_FICHA_EMITIDA_SISTEMA)
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoLiquidacionComp.getRequisitoLiquidacion()!= null) {
								intItemArchivo = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo();
								intItemHistorico = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_FICHASOCIO_SISTEMA);
							break;
					}
					
					// -------------------------  LIQUIDACION_SUCESION_INTESTADA  -----------------------------------------------------
					if (intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_SUCESION_INTESTADA)
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoLiquidacionComp.getRequisitoLiquidacion()!= null) {
								intItemArchivo = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo();
								intItemHistorico = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO);
							break;
					}
					
					// -------------------------  LIQUIDACION_COPIA_DNI_FAMILIAR  -----------------------------------------------------
					if (intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_COPIA_DNI_FAMILIAR)
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoLiquidacionComp.getRequisitoLiquidacion()!= null) {
								intItemArchivo = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo();
								intItemHistorico = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
							break;
					}
					
					// ------------------------- LIQUIDACION_COPIA_DNI_SOCIO  -----------------------------------------------------
					if (intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_COPIA_DNI_SOCIO)
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoLiquidacionComp.getRequisitoLiquidacion()!= null) {
								intItemArchivo = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo();
								intItemHistorico = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
							break;
					}
					
					// -------------------------  LIQUIDACION_PARTIDA_DEFUNCION  -----------------------------------------------------
					if (intParaTipoDescripcion.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoLiquidacionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_LIQUIDACION_PARTIDA_DEFUNCION)
							&& intParaTipoOperacionPersona.equals(requisitoLiquidacionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoLiquidacionComp.getRequisitoLiquidacion()!= null) {
								intItemArchivo = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemArchivo();
								intItemHistorico = requisitoLiquidacionComp.getRequisitoLiquidacion().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO);
							break;
					}
					

				}
			}
			// fileupload.setStrJsFunction("putFile");
			fileupload.setStrJsFunction("putFileDocAdjunto()");
		} catch (Exception e) {
			log.error("Error en adjuntarDocumento ---> "+e);
			e.printStackTrace();
		}
	}

	
	/**
	 * Calcula el nro de dias entre 2 fechas Calendar
	 * @param Date fechaInicial
	 * @param Date fechaFinal
	 * @return int dias
	 */
/*	public int fechasDiferenciaEnDias(Date fechaInicial, Date fechaFinal) {

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
	
	public Date sumarDiasAFecha(Date fechaInicial, int nroDias) {
		//------------date a calendar--------

		//----------------------->
		//int diferenciaEnDias = 60;

		long tiempoActual = fechaInicial.getTime();
		long dias = nroDias * 24 * 60 * 60 * 1000;
		Date dtFinal = new Date(tiempoActual + dias);
		System.out.println("fechaInicial ---> "+fechaInicial);
		System.out.println("dtFinal --------> "+dtFinal);
		//----------------------->
		 	long fechaInicialMs = fechaInicial.getTime();
		 	long dias  = (nroDias * 1000 * 60 * 60 * 24);
			
		 	long fechaFinalMs = fechaInicialMs + dias ;
		 //----------------------->
		 return dtFinal;
	}*/
	
	
	
	/**
	 * Genera los Expedientes Detalle de acuerdoa a la existencia de los beans de aportes y/o retiro.
	 * Seteamos los Saldos, CUneta,ItemCOnceptoi,Beneficiarios por default
	 */
	public void generarDetallesExpediente(){
		ExpedienteLiquidacionDetalle detalleExpediente = null;
		
		try {
			
			if(beanCuentaConceptoAportes!= null){
				detalleExpediente = new ExpedienteLiquidacionDetalle();
				detalleExpediente.setBdSaldo(beanCuentaConceptoAportes.getCuentaConcepto().getBdSaldo());
				detalleExpediente.setCuentaConcepto(beanCuentaConceptoAportes.getCuentaConcepto());
				detalleExpediente.getId().setIntCuenta(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntCuentaPk());
				detalleExpediente.getId().setIntItemCuentaConcepto(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntItemCuentaConcepto());
				detalleExpediente.getId().setIntPersEmpresa(beanCuentaConceptoAportes.getCuentaConcepto().getId().getIntPersEmpresaPk());
				detalleExpediente.setListaBeneficiarioLiquidacion(new ArrayList<BeneficiarioLiquidacion>());
				beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().add(detalleExpediente);
			}
			if(beanCuentaConceptoRetiro != null){
				detalleExpediente = new ExpedienteLiquidacionDetalle();
				detalleExpediente.setBdSaldo(beanCuentaConceptoRetiro.getCuentaConcepto().getBdSaldo());
				detalleExpediente.setCuentaConcepto(beanCuentaConceptoRetiro.getCuentaConcepto());
				detalleExpediente.getId().setIntCuenta(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntCuentaPk());
				detalleExpediente.getId().setIntItemCuentaConcepto(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntItemCuentaConcepto());
				detalleExpediente.getId().setIntPersEmpresa(beanCuentaConceptoRetiro.getCuentaConcepto().getId().getIntPersEmpresaPk());
				detalleExpediente.setListaBeneficiarioLiquidacion(new ArrayList<BeneficiarioLiquidacion>());
				beanExpedienteLiquidacion.getListaExpedienteLiquidacionDetalle().add(detalleExpediente);
				
			}

		} catch (Exception e) {
			log.error("Error generarDetallesExpediente --> "+e);
		}
	}
	

	
	/**
	 * Agrega 
	 * @param date
	 * @param minutes
	 * @return
	 */
	 public static Date addMinutesToDate(Date date, int minutes) { 
	       Calendar calendarDate = Calendar.getInstance();  
	       calendarDate.setTime(date);  
	       calendarDate.add(Calendar.MINUTE, minutes); 
	       return calendarDate.getTime();  
	   }  
	 
	 
	 /**
	  * Agrega dias a una fecha Date
	  * @param date
	  * @param days
	  * @return
	  */
	 public static Date addDaysToDate(Date date, int days) {
	       return addMinutesToDate(date, 60 * 24 * days);  
	   } 
	
	
	/**
	 * Convierte una cadena a Calendar
	 * @param fecha
	 * @return Calendar cal
	 */
	public Calendar stringToCalendar(String fecha) {
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
	 * Cambia el estado a Anulada a la solcitud seleccionada. Solo cambia si esta en estado requiisito.
	 */
	public void irEliminarSolicitudLiquidacion(ActionEvent event) {
		limpiarFormSolicitudLiquidacion();

		//blnModoVisualizacion = true;
//		SocioComp socioComp;
//		Integer intIdPersona = null;
//		Persona persona = null;
		//listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		ExpedienteLiquidacionId expedienteLiquidacionId = new ExpedienteLiquidacionId();
		
		expedienteLiquidacionId.setIntPersEmpresaPk(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntPersEmpresaPk());
		expedienteLiquidacionId.setIntItemExpediente(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntItemExpediente());
		//expedienteLiquidacionId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getId().get == null ? new Integer(strIntItemDetExpediente) : new Integer(	strItemDetExpediente));
		//Boolean blnContinuaRecorrido = true;
		
		try {
			// devuelve el crongrama son id vacio.
			beanExpedienteLiquidacion = liquidacionFacade.getExpedientePrevisionCompletoPorIdExpedienteLiquidacion(expedienteLiquidacionId);
			
			if (beanExpedienteLiquidacion != null) {
				if (beanExpedienteLiquidacion.getEstadoLiquidacionUltimo() != null) {
					EstadoLiquidacion ultimoEstado = new EstadoLiquidacion();
					ultimoEstado = beanExpedienteLiquidacion.getEstadoLiquidacionUltimo();

							if(ultimoEstado.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
								//-------------------- se graba el nuevo estado
								EstadoLiquidacion estadoLiquidacion = null;
								// Validamos que todos requisitos se cumplan
								if (beanExpedienteLiquidacion.getListaEstadoLiquidacion() != null) {	
									// Si no se graba en estado REQUISITO
									estadoLiquidacion = new EstadoLiquidacion();
									estadoLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO);
									estadoLiquidacion.setTsFechaEstado(new Timestamp(new Date().getTime()));
									estadoLiquidacion.setIntPersEmpresaEstado(usuario.getSucursal().getIntIdEstado());				
									estadoLiquidacion.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
									estadoLiquidacion.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
									estadoLiquidacion.setIntPersUsuarioEstado(usuario.getIntIdEstado());
									beanExpedienteLiquidacion.getListaEstadoLiquidacion().add(estadoLiquidacion);
									// }
								}

								liquidacionFacade.modificarExpedienteLiquidacion(beanExpedienteLiquidacion);
							}else{
								strMsgErrorValidarDatos = "Solo se puede Eliminar una Solicitud en estado REQUISITO.";
								return;
							}
					//}
					
				}

			}

			cancelarGrabarSolicitud();
			limpiarFormSolicitudLiquidacion();
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
		} 	
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
	 * Validacion Final para el otorgamiento de liquidacion vs. existencia de previsiones anteriores 
	 * @return
	 */
	public Boolean validacionFinalOtorgamientoLiquidacion(){
		Boolean blnPasaPrevision = Boolean.TRUE;
		Boolean blnPasaLiquidacion = Boolean.TRUE;
		Boolean blnPasa = null;
		//String strValidacionFinal = "";
		List<ExpedientePrevision> listaExpPrevision= null;
		List<ExpedienteLiquidacion> listaExpLiquidacion= null;
		//ExpedienteLiquidacionBO expedienteBo = null;
		 
		
		try {
			strMsgTxtProcedeEvaluacion = "";
			//expedienteBo = (ExpedienteLiquidacionBO)TumiFactory.get(ExpedienteLiquidacionBO.class);
			
			// 1. Recuperamos las previones
			listaExpPrevision = previsionFacade.getListaExpedientePrevisionPorCuenta(beanSocioComp.getCuenta());
			if(listaExpPrevision != null && !listaExpPrevision.isEmpty()){
				for (ExpedientePrevision expedientePrevision : listaExpPrevision) {
					
					// AES no tiene restriccion. Por lo tanto no se valida.
					
					//SEPELIO
					if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
						
						//Titular
						/*if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
							if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)!=0){
								if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
									|| expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0 ){
									blnPasaPrevision = Boolean.FALSE;
									strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Titular en estado APROBADO.";
									break;
									
								}
							}
							
							
						
						// Conyuge
						}else */
						if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_CONYUGE)==0){
							if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){	
								blnPasaPrevision = Boolean.FALSE;
								strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Conyuge en estado APROBADO.";
								break;
									
								}
							
							
						// Padres
						}else if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_PADRES)==0){
							if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
								blnPasaPrevision = Boolean.FALSE;
								strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Padres en estado APROBADO.";
								break;
								
								}
							
							
						// Hijos
						}else if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_HIJOS)==0){
							if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
								blnPasaPrevision = Boolean.FALSE;
								strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Hijos en estado APROBADO.";
								break;

								}
						}
						
					// RETIRO	
					}else if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
						
							// fallecim titular
							 if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_FALLECIMIENTO_TITULAR)==0){
								if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
									&& beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)!=0){
									blnPasaPrevision = Boolean.FALSE;
									strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Retiro Fallecimiento Titular en estado APROBADO.";
									break;
										
										
									}
							}	
						
						// Cese
					 else if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_CESE_LABORAL)==0){
						 	// No importa el estado . Si se le da.
							
							// Retiro	/ renuncia
							}else if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_RETIRO_VOLUNTARIO)==0){
								// No importa el estado . Si se le da.	
								
							}
					}
				}
			}
			// 2.  Recuperamos las liquidaciones
			// Solo puede tener una liquidacion
			// recuperar liquidaciones x cuenta  personay empresa...
			// liquidacionFacade.getlistaex
			
			listaExpLiquidacion =  liquidacionFacade.getListaExpedienteLiquidacionYEstados(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), beanSocioComp.getCuenta().getId().getIntCuenta());
			
			if(listaExpLiquidacion != null && !listaExpLiquidacion.isEmpty()){
				for (ExpedienteLiquidacion expedienteLiquidacion : listaExpLiquidacion) {
					if(expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
						||expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
						|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0
						|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
						blnPasaLiquidacion = Boolean.FALSE;
						strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Liquidacion previa.";
						break;
					}
					
				}
				
			}
			
			
			if(blnPasaPrevision && blnPasaLiquidacion){
				blnPasa = Boolean.TRUE;
			}else{
				blnPasa = Boolean.FALSE;
			}
			
			
			
		} catch (Exception e) {
			log.error("Error en validacionFinalOtorgamientoLiquidacion ---> "+e);
		}
		return blnPasa;
	}
	/*IMPRIME EL REPORTE DE LA SOLICITUD DE RENUNCIA DE SEPELIO
     * 
     * AUTOR: Rodolfo Villarreal Acuña
     */
    
	//Solicitud de renuncia
	//solicitud AEs
	 public void imprimirSepelioRenuncia(){
		 String strNombreReporte = " ";
		 HashMap<String,Object> parametro = new HashMap<String,Object>();
		 Tabla autorizacion = new Tabla();
		 DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(',');
	    	CuentaConceptoDetalle ctaCptoDet = new CuentaConceptoDetalle();
	    	ctaCptoDet.setId(new CuentaConceptoDetalleId());
	    	List<ExpedienteLiquidacion> expeLiqui = new ArrayList<ExpedienteLiquidacion>();
	    	lstReporteVacia.clear();
		 try {
			 EstructuraFacadeRemote facade1 = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			 EmpresaFacadeRemote Empresafacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			 LiquidacionFacadeRemote liquidacion = (LiquidacionFacadeRemote)EJBFactory.getRemote(LiquidacionFacadeRemote.class);
			 
			 autorizacion.setStrAbreviatura(" ");
			 lstReporteVacia.add(autorizacion);
			 GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			 irVerSolicitudLiquidacion(null);
			 
			String fechaPago = Constante.sdf.format( beanExpedienteLiquidacion.getDtFechaProgramacion());
			if(fechaPago!=null && !fechaPago.isEmpty()){
		        parametro.put("P1", fechaPago.split("/")[0]);
		        parametro.put("P2", fechaPago.split("/")[1]);
		        parametro.put("P3", fechaPago.split("/")[2]);
			 }else{
				 	parametro.put("P1", "");
			        parametro.put("P2", "");
			        parametro.put("P3", "");
			 }
			parametro.put("P_EXPEDIENTE", beanExpedienteLiquidacion.getId().getIntItemExpediente());
			
			beanExpedienteLiquidacion.getListaEstadoLiquidacion().get(0).getTsFechaEstado();
			
			for(EstadoLiquidacion lstEstadoPrevision : beanExpedienteLiquidacion.getListaEstadoLiquidacion()){
	              if(lstEstadoPrevision.getIntParaEstado()==2){
	            	  String fechaEstado = Constante.sdf.format(lstEstadoPrevision.getTsFechaEstado());
	            	parametro.put("P_FECHA", fechaEstado);
	              	break;
	              }else{
	            	  parametro.put("P_FECHA", "");
	              }
	            }
			
			expeLiqui = liquidacion.getListaExpedienteLiquidacionYEstados(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(),
					 													   beanSocioComp.getCuenta().getId().getIntCuenta());
			 int contDoc=0;
			 if(expeLiqui!=null && !expeLiqui.isEmpty()){
				 for(ExpedienteLiquidacion lstExp : expeLiqui){
					 if(lstExp!=null){
					 Tabla tipoMotivo = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_MOTIVORENUNCIA_N_COD),
							 lstExp.getIntParaMotivoRenuncia());
					 //String docMotivo = tipoMotivo.getStrDescripcion();
					 if(tipoMotivo!=null){
							parametro.put("P_RENUNCIA"+contDoc, "- " +tipoMotivo.getStrDescripcion());
						}else{
							parametro.put("P_RENUNCIA"+contDoc, "");
					}
				 }else{
					 parametro.put("P_RENUNCIA", "");
				 	}
					 contDoc++;
				 }
			 }
			 
			 for(ExpedienteLiquidacion observa : expeLiqui){
				 if(observa!=null){
					 parametro.put("P_OBSERVACION", observa.getStrObservacion());
				 }else{
					 parametro.put("P_OBSERVACION", "");
				 }
			 }
			 
			 if(contDoc<6){
				 for(int i=contDoc;i<7;i++){
					 parametro.put("P_RENUNCIA"+i, "");
				 }
			 }
			 if(expeLiqui != null && !expeLiqui.isEmpty())
				 parametro.put("P_FECHAPAGO", expeLiqui.get(0).getDtFechaProgramacion());
			 else
				 parametro.put("P_FECHAPAGO", "");
			 
			 if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && 
	            		beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			 parametro.put("P_DNI", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad());
			 }
			 if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && 
	            		beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			 String dni = beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad();
			 for(int i = 0; i < 9; i++){
	            	parametro.put("D"+i, "");
	            }
			 
			 for(int i = 0; i < dni.length(); i++){
	            	parametro.put("D"+i, dni.substring(i, i+1));
	            }
			 }
			 parametro.put("P_NUMEROCUENTA", beanSocioComp.getCuenta().getStrNumeroCuenta());
			 parametro.put("P_APELLIDOPATERNO", beanSocioComp.getPersona().getNatural().getStrApellidoPaterno());
			 parametro.put("P_APELLIDOMATERNO", beanSocioComp.getPersona().getNatural().getStrApellidoMaterno());
			 parametro.put("P_NOMBRE", beanSocioComp.getPersona().getNatural().getStrNombres());
			 
			 List<Domicilio> lstDomicilio = beanSocioComp.getPersona().getListaDomicilio();
			 	Domicilio dom = new Domicilio();
			 
			 	if (lstDomicilio != null && lstDomicilio.size() >= 1) {
	            	dom = lstDomicilio.get(0);
	            }
	            
	            String strVia=null;
	            Tabla tablaVia = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), dom.getIntTipoViaCod());
	            if(tablaVia!=null){
	            	strVia = tablaVia.getStrDescripcion();
	            }
	            parametro.put("P_DIRECCION", strVia + " " + dom.getStrNombreVia() + " " +dom.getIntNumeroVia());
	            if(dom.getIntParaUbigeoPkDistrito()!=null)
	            	 parametro.put("P_DISTRITO", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkDistrito())).get(0).getStrDescripcion());
	            else
	            	 parametro.put("P_DISTRITO", "");
	            	parametro.put("P_PROVINCIA", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkProvincia())).get(0).getStrDescripcion());
	            	parametro.put("P_DEPARTAMENTO", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkDpto())).get(0).getStrDescripcion());
	            	parametro.put("P_REFERENCIA", dom.getStrReferencia());

	            for(SocioEstructura lstEstructura : beanSocioComp.getSocio().getListSocioEstructura()){
	            if(lstEstructura.getIntEstadoCod()==1){
	    			listEstructura = facade1.getListaEstructuraPorNivelCodigo(beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntNivel(),
	    																	  beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntCodigo());
	    			parametro.put("P_DEPENDENCIA", listEstructura.get(0).getJuridica().getStrRazonSocial());
	    			break;
	    			}else{
	    				parametro.put("P_DEPENDENCIA", "");
	    			}
	            }
	            listSucursal = Empresafacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
		        parametro.put("P_AGENCIA", listSucursal.get(0).getJuridica().getStrSiglas());
		        
		        

		        System.out.println("PARAMETRO "+ parametro);
	            
	           	strNombreReporte = "solicitudRenuncia";
				UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstReporteVacia), Constante.PARAM_T_TIPOREPORTE_PDF);
			 
		} catch (Exception e) {
			log.error("Error en imprimirSepelio ---> "+e);
		}
	 }
	 
	 /*Inicio formatos en blanco autor 21/07/2014*/
	 public void imprimirSepelioRenunciaEnBlanco(){
		 String strNombreReporte = " ";
		 HashMap<String,Object> parametro = new HashMap<String,Object>();
		 Tabla autorizacion = new Tabla();

		 try {
			 			 
			 autorizacion.setStrAbreviatura(" ");
			 lstReporteVacia.add(autorizacion);
			
		         	parametro.put("P1", "");
			        parametro.put("P2", "");
			        parametro.put("P3", "");
					parametro.put("P_EXPEDIENTE", "");
			     	parametro.put("P_FECHA", "");
				    parametro.put("P_RENUNCIA", "");
				 	parametro.put("P_OBSERVACION", "");
				 	int contDoc=0;
				 	if(contDoc<6){
						 for(int i=contDoc;i<7;i++){
							 parametro.put("P_RENUNCIA"+i, "");
						 }
					 }
				    parametro.put("P_RENUNCIA", "");
				    parametro.put("P_FECHAPAGO", "");
			 		parametro.put("P_DNI", "");
				    for(int i = 0; i < 9; i++){
	            	parametro.put("D"+i, "");
					}
					parametro.put("P_NUMEROCUENTA", "");
					parametro.put("P_APELLIDOPATERNO", "");
					parametro.put("P_APELLIDOMATERNO", "");
					parametro.put("P_NOMBRE", "");
			        parametro.put("P_DIRECCION", "");
	               	parametro.put("P_DISTRITO", "");
	                parametro.put("P_PROVINCIA", "");
	            	parametro.put("P_DEPARTAMENTO", "");
	            	parametro.put("P_REFERENCIA", "");
	    			parametro.put("P_DEPENDENCIA", "");
	    			parametro.put("P_AGENCIA", "");
		         
		        System.out.println("PARAMETRO "+ parametro);
	            
	           	strNombreReporte = "solicitudRenuncia";
				UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstReporteVacia), Constante.PARAM_T_TIPOREPORTE_PDF);
			 
		} catch (Exception e) {
			log.error("Error en imprimirSepelio ---> "+e);
		}
	 }

	 /*fin del formato en blanco*/

	
	/**
	 * Validacion Final para el otorgamiento de liquidacion vs. existencia de previsiones anteriores 
	 * @return
	 */
	public Boolean validacionFinalOtorgamientoLiquidacion2(){
		Boolean blnPasaPrevision = Boolean.TRUE;
		Boolean blnPasaLiquidacion = Boolean.TRUE;
		Boolean blnPasa = null;
		//String strValidacionFinal = "";
		List<ExpedientePrevision> listaExpPrevision= null;
//		List<ExpedienteLiquidacion> listaExpLiquidacion= null;
		//ExpedienteLiquidacionBO expedienteBo = null;
		 
		
		try {
			strMsgTxtProcedeEvaluacion = "";
			//expedienteBo = (ExpedienteLiquidacionBO)TumiFactory.get(ExpedienteLiquidacionBO.class);
			
			// 1. Recuperamos las previones
			listaExpPrevision = previsionFacade.getListaExpedientePrevisionPorCuenta(beanSocioComp.getCuenta());
			if(listaExpPrevision != null && !listaExpPrevision.isEmpty()){
				for (ExpedientePrevision expedientePrevision : listaExpPrevision) {
					
					// AES no tiene restriccion. Por lo tanto no se valida.
					
					//SEPELIO
					if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
						
						//Titular
						/*if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
							if(beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)!=0){
								if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
									|| expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0 ){
									blnPasaPrevision = Boolean.FALSE;
									strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Titular en estado APROBADO.";
									break;
									
								}
							}
							
							
						
						// Conyuge
						}else */
						if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_CONYUGE)==0){
							if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){	
								blnPasaPrevision = Boolean.FALSE;
								strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Conyuge en estado APROBADO.";
								break;
									
								}
							
							
						// Padres
						}else if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_PADRES)==0){
							if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
								blnPasaPrevision = Boolean.FALSE;
								strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Padres en estado APROBADO.";
								break;
								
								}
							
							
						// Hijos
						}else if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_HIJOS)==0){
							if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
								blnPasaPrevision = Boolean.FALSE;
								strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Hijos en estado APROBADO.";
								break;

								}
						}
						
					// RETIRO	
					}else if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
						
							// fallecim titular
							 if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_FALLECIMIENTO_TITULAR)==0){
								if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
									&& beanExpedienteLiquidacion.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)!=0){
									blnPasaPrevision = Boolean.FALSE;
									strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Previsión Retiro Fallecimiento Titular en estado APROBADO.";
									break;
										
										
									}
							}	
						
						// Cese
					 else if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_CESE_LABORAL)==0){
						 	// No importa el estado . Si se le da.
							
							// Retiro	/ renuncia
							}else if(expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_RETIRO_VOLUNTARIO)==0){
								// No importa el estado . Si se le da.	
								
							}
					}
				}
			}
			// 2.  Recuperamos las liquidaciones
			// Solo puede tener una liquidacion
			// recuperar liquidaciones x cuenta  personay empresa...
			// liquidacionFacade.getlistaex
			
//			listaExpLiquidacion =  liquidacionFacade.getListaExpedienteLiquidacionYEstados(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), beanSocioComp.getCuenta().getId().getIntCuenta());
			
//			if(listaExpLiquidacion != null && !listaExpLiquidacion.isEmpty()){
//				for (ExpedienteLiquidacion expedienteLiquidacion : listaExpLiquidacion) {
//					if(expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
//						||expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
//						|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0
//						|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
//						blnPasaLiquidacion = Boolean.FALSE;
//						strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion + " El socio presenta una Solicitud de Liquidacion previa.";
//						break;
//					}
//					
//				}
//				
//			}
			
			
			if(blnPasaPrevision && blnPasaLiquidacion){
				blnPasa = Boolean.TRUE;
			}else{
				blnPasa = Boolean.FALSE;
			}
			
			
			
		} catch (Exception e) {
			log.error("Error en validacionFinalOtorgamientoLiquidacion2 ---> "+e);
		}
		return blnPasa;
	}
	
	
	/**
	 * jchavez 09.06.2014 Reutilizado de solicitud prestamo y adaptado para su uso en sol. liquidacion
	 * Muestra las personas que observaron la solicitud de liquidacion previamente, durante
	 * el proceso de autorizacion de liquidacion.
	 * @return
	 */
	public boolean mostrarlistaAutorizacionesPrevias(List<EstadoLiquidacion> listaEstados) {
		boolean isValidEncaragadoAutorizar = true;
		List<AutorizaLiquidacion> listaAutorizaLiquidacion = null;
		listaAutorizaLiquidacionComp = null;
		AutorizaLiquidacionComp autorizaLiquidacionComp = null;
		Persona persona = null;
		try {
		
			listaAutorizaLiquidacion = new ArrayList<AutorizaLiquidacion>();
			listaAutorizaLiquidacionComp = new ArrayList<AutorizaLiquidacionComp>();
			// buscar todos sus estados y ver si aen alguno existe un observado
			if(listaEstados!= null && !listaEstados.isEmpty()){
				Boolean blnContinua = Boolean.FALSE;
				
				for (EstadoLiquidacion estados : listaEstados) {
					if(estados.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
						blnContinua = Boolean.TRUE;
						break;
					}
				}

				if(blnContinua){
					listaAutorizaLiquidacion = autorizaLiquidacionFacade.getListaAutorizaLiquidacionPorPkExpediente(registroSeleccionadoBusqueda.getId());
					if (listaAutorizaLiquidacion != null && listaAutorizaLiquidacion.size() > 0) {
						for (AutorizaLiquidacion autorizaLiquidacion : listaAutorizaLiquidacion) {
							if(autorizaLiquidacion.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_LIQUIDACION)==0
							   || autorizaLiquidacion.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_LIQUIDACION)==0 ){
								autorizaLiquidacionComp = new AutorizaLiquidacionComp();
								autorizaLiquidacionComp.setAutorizaLiquidacion(autorizaLiquidacion);
								persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaLiquidacion.getIntPersUsuarioAutoriza());
								for (int k = 0; k < persona.getListaDocumento().size(); k++) {
									if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
										persona.setDocumento(persona.getListaDocumento().get(k));
										break;
									}
								}

								// recuperando el perfil del usuario
								Perfil perfil = null;
								PerfilId perfilId = null;
								autorizaLiquidacionComp.setPersona(persona);
								
								perfilId = new PerfilId();
								perfilId.setIntPersEmpresaPk(autorizaLiquidacionComp.getAutorizaLiquidacion().getIntPersEmpresaAutoriza());
								perfilId.setIntIdPerfil(autorizaLiquidacionComp.getAutorizaLiquidacion().getIntIdPerfilAutoriza());
								// recuperando el perfil del usuario
								perfil = permisoFacade.getPerfilYListaPermisoPerfilPorPkPerfil(perfilId);
								if(perfil != null){
									autorizaLiquidacionComp.setStrPerfil(perfil.getStrDescripcion());
								}
								listaAutorizaLiquidacionComp.add(autorizaLiquidacionComp);
							}
						}
					}	
				}
			}
		} catch (BusinessException e) {
			log.error("Error en mostrarlistaAutorizacionesPrevias ---< "+e);
			e.printStackTrace();
		}
		return isValidEncaragadoAutorizar;
	}
	
	
	
	
	
	/**
	 * 
	 * 
	*/
	
	
	public String getStrUnidadesEjecutorasConcatenadas() {
		return strUnidadesEjecutorasConcatenadas;
	}
	/*public ExpedienteLiquidacion getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}


	public void setRegistroSeleccionadoBusqueda(
			ExpedienteLiquidacion registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}*/


	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}


	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
	}


//	public List<Tabla> getListaTablaTipoRenuncia() {
//		return listaTablaTipoRenuncia;
//	}
//
//
//	public void setListaTablaTipoRenuncia(List<Tabla> listaTablaTipoRenuncia) {
//		this.listaTablaTipoRenuncia = listaTablaTipoRenuncia;
//	}

//
//	public List<Tabla> getListaTablaTipoSolicitud() {
//		return listaTablaTipoSolicitud;
//	}
//
//
//	public void setListaTablaTipoSolicitud(List<Tabla> listaTablaTipoSolicitud) {
//		this.listaTablaTipoSolicitud = listaTablaTipoSolicitud;
//	}


	public boolean isBlnPostEvaluacion() {
		return blnPostEvaluacion;
	}


	public void setBlnPostEvaluacion(boolean blnPostEvaluacion) {
		this.blnPostEvaluacion = blnPostEvaluacion;
	}

	public List<RequisitoLiquidacionComp> getListaRequisitoLiquidacionComp() {
		return listaRequisitoLiquidacionComp;
	}


	public void setListaRequisitoLiquidacionComp(
			List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp) {
		this.listaRequisitoLiquidacionComp = listaRequisitoLiquidacionComp;
	}


	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacionBusq() {
		return listaBeneficiarioLiquidacionBusq;
	}


	public void setListaBeneficiarioLiquidacionBusq(
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionBusq) {
		this.listaBeneficiarioLiquidacionBusq = listaBeneficiarioLiquidacionBusq;
	}



	public List<Tabla> getListaSubOperacion() {
		return listaSubOperacion;
	}


	public void setListaSubOperacion(List<Tabla> listaSubOperacion) {
		this.listaSubOperacion = listaSubOperacion;
	}


	public List<Tabla> getListaMotivoRenuncia() {
		return listaMotivoRenuncia;
	}


	public void setListaMotivoRenuncia(List<Tabla> listaMotivoRenuncia) {
		this.listaMotivoRenuncia = listaMotivoRenuncia;
	}


	public Integer getIntTipoOperacion() {
		return intTipoOperacion;
	}


	public void setIntTipoOperacion(Integer intTipoOperacion) {
		this.intTipoOperacion = intTipoOperacion;
	}


	public Integer getIntSubTipoOperacion() {
		return intSubTipoOperacion;
	}


	public void setIntSubTipoOperacion(Integer intSubTipoOperacion) {
		this.intSubTipoOperacion = intSubTipoOperacion;
	}


	public String getCampoBuscarBeneficiario() {
		return campoBuscarBeneficiario;
	}


	public void setCampoBuscarBeneficiario(String campoBuscarBeneficiario) {
		this.campoBuscarBeneficiario = campoBuscarBeneficiario;
	}


	public BeneficiarioLiquidacion getBeneficiarioSeleccionado() {
		return beneficiarioSeleccionado;
	}


	public void setBeneficiarioSeleccionado(
			BeneficiarioLiquidacion beneficiarioSeleccionado) {
		this.beneficiarioSeleccionado = beneficiarioSeleccionado;
	}


	public void setStrUnidadesEjecutorasConcatenadas(
			String strUnidadesEjecutorasConcatenadas) {
		this.strUnidadesEjecutorasConcatenadas = strUnidadesEjecutorasConcatenadas;
	}
	public List<Subsucursal> getListaSubsucursalSocio() {
		return listaSubsucursalSocio;
	}
	public void setListaSubsucursalSocio(List<Subsucursal> listaSubsucursalSocio) {
		this.listaSubsucursalSocio = listaSubsucursalSocio;
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
	public String getStrMsgErrorValidarDatos() {
		return strMsgErrorValidarDatos;
	}
	public void setStrMsgErrorValidarDatos(String strMsgErrorValidarDatos) {
		this.strMsgErrorValidarDatos = strMsgErrorValidarDatos;
	}
	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}
	public SocioFacadeRemote getSocioFacade() {
		return socioFacade;
	}
	public void setSocioFacade(SocioFacadeRemote socioFacade) {
		this.socioFacade = socioFacade;
	}
	public EstructuraFacadeRemote getEstructuraFacade() {
		return estructuraFacade;
	}
	public void setEstructuraFacade(EstructuraFacadeRemote estructuraFacade) {
		this.estructuraFacade = estructuraFacade;
	}
	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}
	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
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
	public Estructura getBeanEstructuraSocioComp() {
		return beanEstructuraSocioComp;
	}
	public void setBeanEstructuraSocioComp(Estructura beanEstructuraSocioComp) {
		this.beanEstructuraSocioComp = beanEstructuraSocioComp;
	}
	public String getStrSubsucursalSocio() {
		return strSubsucursalSocio;
	}
	public void setStrSubsucursalSocio(String strSubsucursalSocio) {
		this.strSubsucursalSocio = strSubsucursalSocio;
	}
	public ExpedienteLiquidacion getBeanExpedienteLiquidacion() {
		return beanExpedienteLiquidacion;
	}
	public void setBeanExpedienteLiquidacion(
			ExpedienteLiquidacion beanExpedienteLiquidacion) {
		this.beanExpedienteLiquidacion = beanExpedienteLiquidacion;
	}

	public Boolean getBlnShowValidarDatos() {
		return blnShowValidarDatos;
	}
	public void setBlnShowValidarDatos(Boolean blnShowValidarDatos) {
		this.blnShowValidarDatos = blnShowValidarDatos;
	}
	
	public SolicitudLiquidacionService getSolicitudLiquidacionService() {
		return solicitudLiquidacionService;
	}


	public void setSolicitudLiquidacionService(
			SolicitudLiquidacionService solicitudLiquidacionService) {
		this.solicitudLiquidacionService = solicitudLiquidacionService;
	}


	public String getStrSolicitudLiquidacion() {
		return strSolicitudLiquidacion;
	}


	public void setStrSolicitudLiquidacion(String strSolicitudLiquidacion) {
		this.strSolicitudLiquidacion = strSolicitudLiquidacion;
	}


	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}
	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}


	public Boolean getBlnShowDivFormSolicitudLiquidacion() {
		return blnShowDivFormSolicitudLiquidacion;
	}


	public void setBlnShowDivFormSolicitudLiquidacion(
			Boolean blnShowDivFormSolicitudLiquidacion) {
		this.blnShowDivFormSolicitudLiquidacion = blnShowDivFormSolicitudLiquidacion;
	}


	public boolean isMostrarBtnEliminar() {
		return mostrarBtnEliminar;
	}
	public void setMostrarBtnEliminar(boolean mostrarBtnEliminar) {
		this.mostrarBtnEliminar = mostrarBtnEliminar;
	}
	public boolean isMostrarMensajeExito() {
		return mostrarMensajeExito;
	}
	public void setMostrarMensajeExito(boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}
	public boolean isMostrarMensajeError() {
		return mostrarMensajeError;
	}
	public void setMostrarMensajeError(boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public Integer getIntIdSubsucursalFiltro() {
		return intIdSubsucursalFiltro;
	}
	public void setIntIdSubsucursalFiltro(Integer intIdSubsucursalFiltro) {
		this.intIdSubsucursalFiltro = intIdSubsucursalFiltro;
	}
	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		SolicitudLiquidacionController.log = log;
	}
	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}
	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}
	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}
	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}
	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}
	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}
//	public EgresoFacadeRemote getEgresoFacade() {
//		return egresoFacade;
//	}
//	public void setEgresoFacade(EgresoFacadeRemote egresoFacade) {
//		this.egresoFacade = egresoFacade;
//	}
//	public CierreDiarioArqueoFacadeRemote getCierreDiarioArqueoFacade() {
//		return cierreDiarioArqueoFacade;
//	}
//	public void setCierreDiarioArqueoFacade(
//			CierreDiarioArqueoFacadeRemote cierreDiarioArqueoFacade) {
//		this.cierreDiarioArqueoFacade = cierreDiarioArqueoFacade;
//	}
//	public BancoFacadeRemote getBancoFacade() {
//		return bancoFacade;
//	}
//	public void setBancoFacade(BancoFacadeRemote bancoFacade) {
//		this.bancoFacade = bancoFacade;
//	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Integer getEMPRESA_USUARIO() {
		return SESION_IDEMPRESA;
	}
	public void setEMPRESA_USUARIO(Integer eMPRESA_USUARIO) {
		SESION_IDEMPRESA = eMPRESA_USUARIO;
	}
	public Integer getPERSONA_USUARIO() {
		return SESION_IDUSUARIO;
	}
	public void setPERSONA_USUARIO(Integer pERSONA_USUARIO) {
		SESION_IDUSUARIO = pERSONA_USUARIO;
	}
	public Integer getSUCURSAL_USUARIO_ID() {
		return SESION_IDSUCURSAL;
	}
	public void setSUCURSAL_USUARIO_ID(Integer sUCURSAL_USUARIO_ID) {
		SESION_IDSUCURSAL = sUCURSAL_USUARIO_ID;
	}
	public Integer getSUBSUCURSAL_USUARIO_ID() {
		return SESION_IDSUBSUCURSAL;
	}
	public void setSUBSUCURSAL_USUARIO_ID(Integer sUBSUCURSAL_USUARIO_ID) {
		SESION_IDSUBSUCURSAL = sUBSUCURSAL_USUARIO_ID;
	}
	public Integer getIntTipoPersonaFiltro() {
		return intTipoPersonaFiltro;
	}
	public void setIntTipoPersonaFiltro(Integer intTipoPersonaFiltro) {
		this.intTipoPersonaFiltro = intTipoPersonaFiltro;
	}
	public Integer getIntTipoBusquedaPersonaFiltro() {
		return intTipoBusquedaPersonaFiltro;
	}
	public void setIntTipoBusquedaPersonaFiltro(Integer intTipoBusquedaPersonaFiltro) {
		this.intTipoBusquedaPersonaFiltro = intTipoBusquedaPersonaFiltro;
	}
	public String getStrTextoPersonaFiltro() {
		return strTextoPersonaFiltro;
	}
	public void setStrTextoPersonaFiltro(String strTextoPersonaFiltro) {
		this.strTextoPersonaFiltro = strTextoPersonaFiltro;
	}
	public Integer getIntItemExpedienteFiltro() {
		return intItemExpedienteFiltro;
	}
	public void setIntItemExpedienteFiltro(Integer intItemExpedienteFiltro) {
		this.intItemExpedienteFiltro = intItemExpedienteFiltro;
	}
	public Integer getIntTipoCreditoFiltro() {
		return intTipoCreditoFiltro;
	}
	public void setIntTipoCreditoFiltro(Integer intTipoCreditoFiltro) {
		this.intTipoCreditoFiltro = intTipoCreditoFiltro;
	}
	public List<Tabla> getListaTablaTipoDocumento() {
		return listaTablaTipoDocumento;
	}
	public void setListaTablaTipoDocumento(List<Tabla> listaTablaTipoDocumento) {
		this.listaTablaTipoDocumento = listaTablaTipoDocumento;
	}
	public Integer getIntTipoBusquedaFechaFiltro() {
		return intTipoBusquedaFechaFiltro;
	}
	public void setIntTipoBusquedaFechaFiltro(Integer intTipoBusquedaFechaFiltro) {
		this.intTipoBusquedaFechaFiltro = intTipoBusquedaFechaFiltro;
	}
//	public List<Tabla> getListaTablaEstadoPago() {
//		return listaTablaEstadoPago;
//	}
//	public void setListaTablaEstadoPago(List<Tabla> listaTablaEstadoPago) {
//		this.listaTablaEstadoPago = listaTablaEstadoPago;
//	}
	public Integer getIntTipoBusquedaSucursal() {
		return intTipoBusquedaSucursal;
	}
	public void setIntTipoBusquedaSucursal(Integer intTipoBusquedaSucursal) {
		this.intTipoBusquedaSucursal = intTipoBusquedaSucursal;
	}
//	public List<Tabla> getListaTipoBusquedaSucursal() {
//		return listaTipoBusquedaSucursal;
//	}
//	public void setListaTipoBusquedaSucursal(List<Tabla> listaTipoBusquedaSucursal) {
//		this.listaTipoBusquedaSucursal = listaTipoBusquedaSucursal;
//	}
	public Integer getIntIdSucursalFiltro() {
		return intIdSucursalFiltro;
	}
	public void setIntIdSucursalFiltro(Integer intIdSucursalFiltro) {
		this.intIdSucursalFiltro = intIdSucursalFiltro;
	}
	public List<Tabla> getListaTablaSucursal() {
		return listaTablaSucursal;
	}
	public void setListaTablaSucursal(List<Tabla> listaTablaSucursal) {
		this.listaTablaSucursal = listaTablaSucursal;
	}
	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}
	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}

//	public List<Tabla> getListaTipoVinculo() {
//		return listaTipoVinculo;
//	}
//	public void setListaTipoVinculo(List<Tabla> listaTipoVinculo) {
//		this.listaTipoVinculo = listaTipoVinculo;
//	}
	
	public List<Tabla> getListaDescripcionTipoCuenta() {
		return listaDescripcionTipoCuenta;
	}
	public void setListaDescripcionTipoCuenta(List<Tabla> listaDescripcionTipoCuenta) {
		this.listaDescripcionTipoCuenta = listaDescripcionTipoCuenta;
	}


	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		
		return sesion.getAttribute(beanName);
	}


	public List<Tabla> getListaTipoRelacion() {
		return listaTipoRelacion;
	}


	public void setListaTipoRelacion(List<Tabla> listaTipoRelacion) {
		this.listaTipoRelacion = listaTipoRelacion;
	}
	
	
	
	public List<Tabla> getListDocumentoBusq() {
		return listDocumentoBusq;
	}


	public void setListDocumentoBusq(List<Tabla> listDocumentoBusq) {
		this.listDocumentoBusq = listDocumentoBusq;
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}


	public boolean isBlnIsRenuncia() {
		return blnIsRenuncia;
	}


	public void setBlnIsRenuncia(boolean blnIsRenuncia) {
		this.blnIsRenuncia = blnIsRenuncia;
	}

	public List<CuentaComp> getListaCuentaSocio() {
		return listaCuentaSocio;
	}


	public void setListaCuentaSocio(List<CuentaComp> listaCuentaSocio) {
		this.listaCuentaSocio = listaCuentaSocio;
	}


	public List<CuentaConcepto> getListaCuentaConcepto() {
		return listaCuentaConcepto;
	}


	public void setListaCuentaConcepto(List<CuentaConcepto> listaCuentaConcepto) {
		this.listaCuentaConcepto = listaCuentaConcepto;
	}


	public List<CuentaConceptoComp> getListaCuentaConceptoComp() {
		return listaCuentaConceptoComp;
	}


	public void setListaCuentaConceptoComp(
			List<CuentaConceptoComp> listaCuentaConceptoComp) {
		this.listaCuentaConceptoComp = listaCuentaConceptoComp;
	}


	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}


	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}


	public List<Tabla> getListaDescripcionCuentaConcepto() {
		return listaDescripcionCuentaConcepto;
	}


	public void setListaDescripcionCuentaConcepto(
			List<Tabla> listaDescripcionCuentaConcepto) {
		this.listaDescripcionCuentaConcepto = listaDescripcionCuentaConcepto;
	}


	public void setLiquidacionFacade(LiquidacionFacadeLocal liquidacionFacade) {
		this.liquidacionFacade = liquidacionFacade;
	}

//	public LiquidacionFacadeRemote getLiquidacionFacade() {
//		return liquidacionFacade;
//	}
//
//
//	public void setLiquidacionFacade(LiquidacionFacadeRemote liquidacionFacade) {
//		this.liquidacionFacade = liquidacionFacade;
//	}


	public CuentaFacadeRemote getCuentaFacade() {
		return cuentaFacade;
	}


	public void setCuentaFacade(CuentaFacadeRemote cuentaFacade) {
		this.cuentaFacade = cuentaFacade;
	}


//	public ConvenioFacadeRemote getConvenioFacade() {
//		return convenioFacade;
//	}
//
//
//	public void setConvenioFacade(ConvenioFacadeRemote convenioFacade) {
//		this.convenioFacade = convenioFacade;
//	}


	public CreditoFacadeRemote getCreditoFacade() {
		return creditoFacade;
	}


	public void setCreditoFacade(CreditoFacadeRemote creditoFacade) {
		this.creditoFacade = creditoFacade;
	}


//	public SolicitudPrestamoFacadeRemote getSolicitudPrestamoFacade() {
//		return solicitudPrestamoFacade;
//	}
//
//
//	public void setSolicitudPrestamoFacade(
//			SolicitudPrestamoFacadeRemote solicitudPrestamoFacade) {
//		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
//	}


	public SolicitudPrestamoFacadeLocal getSolicitudPrestamoFacade() {
		return solicitudPrestamoFacade;
	}

	public void setSolicitudPrestamoFacade(
			SolicitudPrestamoFacadeLocal solicitudPrestamoFacade) {
		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
	}

	public List<Tabla> getListaDescTipoCredito() {
		return listaDescTipoCredito;
	}


	public void setListaDescTipoCredito(List<Tabla> listaDescTipoCredito) {
		this.listaDescTipoCredito = listaDescTipoCredito;
	}


	public List<Tabla> getListaDescTipoCreditoEmpresa() {
		return listaDescTipoCreditoEmpresa;
	}


	public void setListaDescTipoCreditoEmpresa(
			List<Tabla> listaDescTipoCreditoEmpresa) {
		this.listaDescTipoCreditoEmpresa = listaDescTipoCreditoEmpresa;
	}


	public Boolean getBlnCondicionSocioSinDeudaPendiente() {
		return blnCondicionSocioSinDeudaPendiente;
	}


	public void setBlnCondicionSocioSinDeudaPendiente(
			Boolean blnCondicionSocioSinDeudaPendiente) {
		this.blnCondicionSocioSinDeudaPendiente = blnCondicionSocioSinDeudaPendiente;
	}


	public Boolean getBlnCondicionSinGarantesDeudores() {
		return blnCondicionSinGarantesDeudores;
	}


	public void setBlnCondicionSinGarantesDeudores(
			Boolean blnCondicionSinGarantesDeudores) {
		this.blnCondicionSinGarantesDeudores = blnCondicionSinGarantesDeudores;
	}


	public Boolean getBlnCondicionBeneficioFondoSepelio() {
		return blnCondicionBeneficioFondoSepelio;
	}


	public void setBlnCondicionBeneficioFondoSepelio(
			Boolean blnCondicionBeneficioFondoSepelio) {
		this.blnCondicionBeneficioFondoSepelio = blnCondicionBeneficioFondoSepelio;
	}


//	public Date getDtHoy() {
//		return dtHoy;
//	}
//
//
//	public void setDtHoy(Date dtHoy) {
//		this.dtHoy = dtHoy;
//	}


	public List<ExpedienteComp> getListExpedienteMovimientoComp() {
		return listExpedienteMovimientoComp;
	}


	public void setListExpedienteMovimientoComp(
			List<ExpedienteComp> listExpedienteMovimientoComp) {
		this.listExpedienteMovimientoComp = listExpedienteMovimientoComp;
	}


	public String getStrMsgCondicionSocioSinDeudaPendiente() {
		return strMsgCondicionSocioSinDeudaPendiente;
	}


	public void setStrMsgCondicionSocioSinDeudaPendiente(
			String strMsgCondicionSocioSinDeudaPendiente) {
		this.strMsgCondicionSocioSinDeudaPendiente = strMsgCondicionSocioSinDeudaPendiente;
	}


	public String getStrMsgCondicionSinGarantesDeudores() {
		return strMsgCondicionSinGarantesDeudores;
	}


	public void setStrMsgCondicionSinGarantesDeudores(
			String strMsgCondicionSinGarantesDeudores) {
		this.strMsgCondicionSinGarantesDeudores = strMsgCondicionSinGarantesDeudores;
	}


	public String getStrMsgCondicionBeneficioFondoSepelio() {
		return strMsgCondicionBeneficioFondoSepelio;
	}


	public void setStrMsgCondicionBeneficioFondoSepelio(
			String strMsgCondicionBeneficioFondoSepelio) {
		this.strMsgCondicionBeneficioFondoSepelio = strMsgCondicionBeneficioFondoSepelio;
	}

	public List<Tabla> getListaDescCondicionSocio() {
		return listaDescCondicionSocio;
	}


	public void setListaDescCondicionSocio(List<Tabla> listaDescCondicionSocio) {
		this.listaDescCondicionSocio = listaDescCondicionSocio;
	}


	public List<Tabla> getListaDescTipoCondicionSocio() {
		return listaDescTipoCondicionSocio;
	}


	public void setListaDescTipoCondicionSocio(
			List<Tabla> listaDescTipoCondicionSocio) {
		this.listaDescTipoCondicionSocio = listaDescTipoCondicionSocio;
	}


//	public PrevisionFacadeRemote getPrevisionFacade() {
//		return previsionFacade;
//	}
//
//
//	public void setPrevisionFacade(PrevisionFacadeRemote previsionFacade) {
//		this.previsionFacade = previsionFacade;
//	}


	public void setPrevisionFacade(PrevisionFacadeLocal previsionFacade) {
		this.previsionFacade = previsionFacade;
	}

	public String getStrMsgTxtSubOperacion() {
		return strMsgTxtSubOperacion;
	}


	public void setStrMsgTxtSubOperacion(String strMsgTxtSubOperacion) {
		this.strMsgTxtSubOperacion = strMsgTxtSubOperacion;
	}


	public String getStrMsgTxtTieneFallecidos() {
		return strMsgTxtTieneFallecidos;
	}


	public void setStrMsgTxtTieneFallecidos(String strMsgTxtTieneFallecidos) {
		this.strMsgTxtTieneFallecidos = strMsgTxtTieneFallecidos;
	}


	public String getStrMsgTxtFechaRenuncia() {
		return strMsgTxtFechaRenuncia;
	}


	public void setStrMsgTxtFechaRenuncia(String strMsgTxtFechaRenuncia) {
		this.strMsgTxtFechaRenuncia = strMsgTxtFechaRenuncia;
	}


	public String getStrMsgTxtFechaRecepcion() {
		return strMsgTxtFechaRecepcion;
	}


	public void setStrMsgTxtFechaRecepcion(String strMsgTxtFechaRecepcion) {
		this.strMsgTxtFechaRecepcion = strMsgTxtFechaRecepcion;
	}


	public String getStrMsgTxtFechaProgramacion() {
		return strMsgTxtFechaProgramacion;
	}


	public void setStrMsgTxtFechaProgramacion(String strMsgTxtFechaProgramacion) {
		this.strMsgTxtFechaProgramacion = strMsgTxtFechaProgramacion;
	}


	public String getStrMsgTxtMontoBrutoLiquidacion() {
		return strMsgTxtMontoBrutoLiquidacion;
	}


	public void setStrMsgTxtMontoBrutoLiquidacion(
			String strMsgTxtMontoBrutoLiquidacion) {
		this.strMsgTxtMontoBrutoLiquidacion = strMsgTxtMontoBrutoLiquidacion;
	}


	public String getStrMsgTxttPeriodoUltimoDescuento() {
		return strMsgTxttPeriodoUltimoDescuento;
	}


	public void setStrMsgTxttPeriodoUltimoDescuento(
			String strMsgTxttPeriodoUltimoDescuento) {
		this.strMsgTxttPeriodoUltimoDescuento = strMsgTxttPeriodoUltimoDescuento;
	}


	public String getStrMsgTxtParaMotivoRenuncia() {
		return strMsgTxtParaMotivoRenuncia;
	}


	public void setStrMsgTxtParaMotivoRenuncia(String strMsgTxtParaMotivoRenuncia) {
		this.strMsgTxtParaMotivoRenuncia = strMsgTxtParaMotivoRenuncia;
	}


	public String getStrMsgTxtTieneBeneficiarios() {
		return strMsgTxtTieneBeneficiarios;
	}


	public void setStrMsgTxtTieneBeneficiarios(String strMsgTxtTieneBeneficiarios) {
		this.strMsgTxtTieneBeneficiarios = strMsgTxtTieneBeneficiarios;
	}


	public String getStrMsgTxtGrabar() {
		return StrMsgTxtGrabar;
	}


	public void setStrMsgTxtGrabar(String strMsgTxtGrabar) {
		StrMsgTxtGrabar = strMsgTxtGrabar;
	}


	public String getStrMsgTxtSumaPorcentaje() {
		return StrMsgTxtSumaPorcentaje;
	}


	public void setStrMsgTxtSumaPorcentaje(String strMsgTxtSumaPorcentaje) {
		StrMsgTxtSumaPorcentaje = strMsgTxtSumaPorcentaje;
	}


	public boolean isBlnSumaPorcentajes() {
		return blnSumaPorcentajes;
	}


	public void setBlnSumaPorcentajes(boolean blnSumaPorcentajes) {
		this.blnSumaPorcentajes = blnSumaPorcentajes;
	}


	public boolean isBlnTieneAdjuntosConfigurados() {
		return blnTieneAdjuntosConfigurados;
	}


	public void setBlnTieneAdjuntosConfigurados(boolean blnTieneAdjuntosConfigurados) {
		this.blnTieneAdjuntosConfigurados = blnTieneAdjuntosConfigurados;
	}


	public boolean isBlnIsFallecimiento() {
		return blnIsFallecimiento;
	}


	public void setBlnIsFallecimiento(boolean blnIsFallecimiento) {
		this.blnIsFallecimiento = blnIsFallecimiento;
	}


	public String getStrMsgTxtObservacion() {
		return StrMsgTxtObservacion;
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


	public void setStrMsgTxtObservacion(String strMsgTxtObservacion) {
		StrMsgTxtObservacion = strMsgTxtObservacion;
	}


	public List<ExpedienteLiquidacionComp> getListaExpedienteLiquidacion() {
		return listaExpedienteLiquidacion;
	}


	public void setListaExpedienteLiquidacion(List<ExpedienteLiquidacionComp> listaExpedienteLiquidacion) {
		this.listaExpedienteLiquidacion = listaExpedienteLiquidacion;
	}


	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacionVista() {
		return listaBeneficiarioLiquidacionVista;
	}


	public void setListaBeneficiarioLiquidacionVista(
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionVista) {
		this.listaBeneficiarioLiquidacionVista = listaBeneficiarioLiquidacionVista;
	}


	public String getStrMsgObservacion() {
		return strMsgObservacion;
	}


	public void setStrMsgObservacion(String strMsgObservacion) {
		this.strMsgObservacion = strMsgObservacion;
	}


	public List<Tabla> getLstDeudaPendiente() {
		return lstDeudaPendiente;
	}


	public void setLstDeudaPendiente(List<Tabla> lstDeudaPendiente) {
		this.lstDeudaPendiente = lstDeudaPendiente;
	}


	public Boolean getChkCondicionSocioSinDeudaPendiente() {
		return chkCondicionSocioSinDeudaPendiente;
	}


	public void setChkCondicionSocioSinDeudaPendiente(
			Boolean chkCondicionSocioSinDeudaPendiente) {
		this.chkCondicionSocioSinDeudaPendiente = chkCondicionSocioSinDeudaPendiente;
	}


	public Boolean getChkCondicionSinGarantesDeudores() {
		return chkCondicionSinGarantesDeudores;
	}


	public void setChkCondicionSinGarantesDeudores(
			Boolean chkCondicionSinGarantesDeudores) {
		this.chkCondicionSinGarantesDeudores = chkCondicionSinGarantesDeudores;
	}


	public Boolean getChkCondicionBeneficioFondoSepelio() {
		return chkCondicionBeneficioFondoSepelio;
	}


	public void setChkCondicionBeneficioFondoSepelio(
			Boolean chkCondicionBeneficioFondoSepelio) {
		this.chkCondicionBeneficioFondoSepelio = chkCondicionBeneficioFondoSepelio;
	}


	public String getStrFechaRenuncia() {
		return strFechaRenuncia;
	}


	public void setStrFechaRenuncia(String strFechaRenuncia) {
		this.strFechaRenuncia = strFechaRenuncia;
	}


	public String getStrFechaRecepcionRenuncia() {
		return strFechaRecepcionRenuncia;
	}


	public void setStrFechaRecepcionRenuncia(String strFechaRecepcionRenuncia) {
		this.strFechaRecepcionRenuncia = strFechaRecepcionRenuncia;
	}


	public String getStrFechaProgramacionPago() {
		return strFechaProgramacionPago;
	}


	public void setStrFechaProgramacionPago(String strFechaProgramacionPago) {
		this.strFechaProgramacionPago = strFechaProgramacionPago;
	}


	public List<ExpedienteLiquidacionComp> getListaExpedienteLiquidacionComp() {
		return listaExpedienteLiquidacionComp;
	}


	public void setListaExpedienteLiquidacionComp(
			List<ExpedienteLiquidacionComp> listaExpedienteLiquidacionComp) {
		this.listaExpedienteLiquidacionComp = listaExpedienteLiquidacionComp;
	}


	public Integer getIntTipoConsultaBusqueda() {
		return intTipoConsultaBusqueda;
	}


	public void setIntTipoConsultaBusqueda(Integer intTipoConsultaBusqueda) {
		this.intTipoConsultaBusqueda = intTipoConsultaBusqueda;
	}


	public String getStrConsultaBusqueda() {
		return strConsultaBusqueda;
	}


	public void setStrConsultaBusqueda(String strConsultaBusqueda) {
		this.strConsultaBusqueda = strConsultaBusqueda;
	}


	public EstadoLiquidacion getEstadoLiquidacionBusqueda() {
		return estadoLiquidacionBusqueda;
	}


	public void setEstadoLiquidacionBusqueda(
			EstadoLiquidacion estadoLiquidacionBusqueda) {
		this.estadoLiquidacionBusqueda = estadoLiquidacionBusqueda;
	}


	public Persona getPersonaBusqueda() {
		return personaBusqueda;
	}


	public void setPersonaBusqueda(Persona personaBusqueda) {
		this.personaBusqueda = personaBusqueda;
	}


	public EstructuraDetalle getEstructuraDetalleBusqueda() {
		return estructuraDetalleBusqueda;
	}


	public void setEstructuraDetalleBusqueda(
			EstructuraDetalle estructuraDetalleBusqueda) {
		this.estructuraDetalleBusqueda = estructuraDetalleBusqueda;
	}


//	public List<Tabla> getListaTablaDeSucursal() {
//		return listaTablaDeSucursal;
//	}
//
//
//	public void setListaTablaDeSucursal(List<Tabla> listaTablaDeSucursal) {
//		this.listaTablaDeSucursal = listaTablaDeSucursal;
//	}


//	public List<Tabla> getListaTablaCreditoEmpresa() {
//		return listaTablaCreditoEmpresa;
//	}
//
//
//	public void setListaTablaCreditoEmpresa(List<Tabla> listaTablaCreditoEmpresa) {
//		this.listaTablaCreditoEmpresa = listaTablaCreditoEmpresa;
//	}

	public String getStrNumeroSolicitudBusq() {
		return strNumeroSolicitudBusq;
	}


	public void setStrNumeroSolicitudBusq(String strNumeroSolicitudBusq) {
		this.strNumeroSolicitudBusq = strNumeroSolicitudBusq;
	}


	public Boolean getBlnTxtBusqueda() {
		return blnTxtBusqueda;
	}


	public void setBlnTxtBusqueda(Boolean blnTxtBusqueda) {
		this.blnTxtBusqueda = blnTxtBusqueda;
	}


//	public List<Tabla> getListaTipoConsultaBusqueda() {
//		try {
//			listaTipoConsultaBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOBUSQUEDA_COMBO_DRNRZ));		
//		} catch (BusinessException e) {
//			e.printStackTrace();
//		}
//
//		return listaTipoConsultaBusqueda;
//	}
//
//
//	public void setListaTipoConsultaBusqueda(List<Tabla> listaTipoConsultaBusqueda) {
//		this.listaTipoConsultaBusqueda = listaTipoConsultaBusqueda;
//	}


	public List<Subsucursal> getListaSubsucursalBusq() {
		return listaSubsucursalBusq;
	}


	public void setListaSubsucursalBusq(List<Subsucursal> listaSubsucursalBusq) {
		this.listaSubsucursalBusq = listaSubsucursalBusq;
	}


	public EstadoLiquidacion getEstadoCondicionFiltro() {
		return estadoCondicionFiltro;
	}


	public void setEstadoCondicionFiltro(EstadoLiquidacion estadoCondicionFiltro) {
		this.estadoCondicionFiltro = estadoCondicionFiltro;
	}


	public EstadoLiquidacion getEstadoLiquidacionFechas() {
		return estadoLiquidacionFechas;
	}


	public void setEstadoLiquidacionFechas(EstadoLiquidacion estadoLiquidacionFechas) {
		this.estadoLiquidacionFechas = estadoLiquidacionFechas;
	}


	public EstadoLiquidacion getEstadoLiquidacionSuc() {
		return estadoLiquidacionSuc;
	}


	public void setEstadoLiquidacionSuc(EstadoLiquidacion estadoLiquidacionSuc) {
		this.estadoLiquidacionSuc = estadoLiquidacionSuc;
	}



	public ExpedienteLiquidacionComp getRegistroSeleccionadoBusquedaComp() {
		return registroSeleccionadoBusquedaComp;
	}


	public void setRegistroSeleccionadoBusquedaComp(
			ExpedienteLiquidacionComp registroSeleccionadoBusquedaComp) {
		this.registroSeleccionadoBusquedaComp = registroSeleccionadoBusquedaComp;
	}


	public Boolean getBlnBusquedaFechas() {
		return blnBusquedaFechas;
	}


	public void setBlnBusquedaFechas(Boolean blnBusquedaFechas) {
		this.blnBusquedaFechas = blnBusquedaFechas;
	}


	public Boolean getBlnBusquedaCondicion() {
		return blnBusquedaCondicion;
	}


	public void setBlnBusquedaCondicion(Boolean blnBusquedaCondicion) {
		this.blnBusquedaCondicion = blnBusquedaCondicion;
	}


	public String getStrDescripcionTipoLiquidacion() {
		return strDescripcionTipoLiquidacion;
	}


	public void setStrDescripcionTipoLiquidacion(
			String strDescripcionTipoLiquidacion) {
		this.strDescripcionTipoLiquidacion = strDescripcionTipoLiquidacion;
	}


	public Boolean getBlnMostrarDescripcionTipoLiquidacion() {
		return blnMostrarDescripcionTipoLiquidacion;
	}


	public void setBlnMostrarDescripcionTipoLiquidacion(
			Boolean blnMostrarDescripcionTipoLiquidacion) {
		this.blnMostrarDescripcionTipoLiquidacion = blnMostrarDescripcionTipoLiquidacion;
	}


	public String getStrMsgTxtAsterisco() {
		return strMsgTxtAsterisco;
	}


	public void setStrMsgTxtAsterisco(String strMsgTxtAsterisco) {
		this.strMsgTxtAsterisco = strMsgTxtAsterisco;
	}


//	public PlanillaFacadeRemote getPlanillaFacade() {
//		return planillaFacade;
//	}
//
//
//	public void setPlanillaFacade(PlanillaFacadeRemote planillaFacade) {
//		this.planillaFacade = planillaFacade;
//	}


	public List<Tabla> getListaDescripcionTipoSocio() {
		return listaDescripcionTipoSocio;
	}


	public void setListaDescripcionTipoSocio(List<Tabla> listaDescripcionTipoSocio) {
		this.listaDescripcionTipoSocio = listaDescripcionTipoSocio;
	}


	public List<Tabla> getListaDescripcionModalidad() {
		return listaDescripcionModalidad;
	}


	public void setListaDescripcionModalidad(List<Tabla> listaDescripcionModalidad) {
		this.listaDescripcionModalidad = listaDescripcionModalidad;
	}


	public String getStrMsgTxtCondiciones() {
		return strMsgTxtCondiciones;
	}


	public void setStrMsgTxtCondiciones(String strMsgTxtCondiciones) {
		this.strMsgTxtCondiciones = strMsgTxtCondiciones;
	}


	public List<BeneficiarioLiquidacion> getListaBeneficiariosVista() {
		return listaBeneficiariosVista;
	}


	public void setListaBeneficiariosVista(
			List<BeneficiarioLiquidacion> listaBeneficiariosVista) {
		this.listaBeneficiariosVista = listaBeneficiariosVista;
	}


	public BeneficiarioLiquidacion getBeneficiarioTotales() {
		return beneficiarioTotales;
	}


	public void setBeneficiarioTotales(BeneficiarioLiquidacion beneficiarioTotales) {
		this.beneficiarioTotales = beneficiarioTotales;
	}


	public CuentaConceptoComp getBeanCuentaConceptoAportes() {
		return beanCuentaConceptoAportes;
	}


	public void setBeanCuentaConceptoAportes(
			CuentaConceptoComp beanCuentaConceptoAportes) {
		this.beanCuentaConceptoAportes = beanCuentaConceptoAportes;
	}


	public CuentaConceptoComp getBeanCuentaConceptoRetiro() {
		return beanCuentaConceptoRetiro;
	}


	public void setBeanCuentaConceptoRetiro(
			CuentaConceptoComp beanCuentaConceptoRetiro) {
		this.beanCuentaConceptoRetiro = beanCuentaConceptoRetiro;
	}


	public String getStrMsgTxtValidacionbeneficiarios() {
		return strMsgTxtValidacionbeneficiarios;
	}


	public void setStrMsgTxtValidacionbeneficiarios(
			String strMsgTxtValidacionbeneficiarios) {
		this.strMsgTxtValidacionbeneficiarios = strMsgTxtValidacionbeneficiarios;
	}


	public Boolean getBlnMostrarObjeto() {
		return blnMostrarObjeto;
	}


	public void setBlnMostrarObjeto(Boolean blnMostrarObjeto) {
		this.blnMostrarObjeto = blnMostrarObjeto;
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


	public String getStrMsgTxtProcedeEvaluacion() {
		return strMsgTxtProcedeEvaluacion;
	}


	public void setStrMsgTxtProcedeEvaluacion(String strMsgTxtProcedeEvaluacion) {
		this.strMsgTxtProcedeEvaluacion = strMsgTxtProcedeEvaluacion;
	}


	public Boolean getBlnSol() {
		return blnSol;
	}


	public void setBlnSol(Boolean blnSol) {
		this.blnSol = blnSol;
	}


	public Boolean getBlnAut() {
		return blnAut;
	}


	public void setBlnAut(Boolean blnAut) {
		this.blnAut = blnAut;
	}


	public Boolean getBlnArch() {
		return blnArch;
	}


	public void setBlnArch(Boolean blnArch) {
		this.blnArch = blnArch;
	}


	public Boolean getBlnGir() {
		return blnGir;
	}


	public void setBlnGir(Boolean blnGir) {
		this.blnGir = blnGir;
	}


	public Boolean getBlnBloquearXCuenta() {
		return blnBloquearXCuenta;
	}


	public void setBlnBloquearXCuenta(Boolean blnBloquearXCuenta) {
		this.blnBloquearXCuenta = blnBloquearXCuenta;
	}


	public String getStrMensajeValidacionCuenta() {
		return strMensajeValidacionCuenta;
	}


	public void setStrMensajeValidacionCuenta(String strMensajeValidacionCuenta) {
		this.strMensajeValidacionCuenta = strMensajeValidacionCuenta;
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


	public Integer getIntBusqTipoLiquidacion() {
		return intBusqTipoLiquidacion;
	}


	public void setIntBusqTipoLiquidacion(Integer intBusqTipoLiquidacion) {
		this.intBusqTipoLiquidacion = intBusqTipoLiquidacion;
	}


	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}


	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}


	public List<Sucursal> getListSucursal() {
		return listSucursal;
	}


	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}


	public List<Sucursal> getListaSucursalBusqueda() {
		return listaSucursalBusqueda;
	}


	public void setListaSucursalBusqueda(List<Sucursal> listaSucursalBusqueda) {
		this.listaSucursalBusqueda = listaSucursalBusqueda;
	}





	public ExpedienteLiquidacion getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}





	public void setRegistroSeleccionadoBusqueda(
			ExpedienteLiquidacion registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}





	public String getStrMsgTxtProcedeEvaluacion1() {
		return strMsgTxtProcedeEvaluacion1;
	}





	public void setStrMsgTxtProcedeEvaluacion1(String strMsgTxtProcedeEvaluacion1) {
		this.strMsgTxtProcedeEvaluacion1 = strMsgTxtProcedeEvaluacion1;
	}





	public List<Tabla> getLstMsgCondicionSinGarantesDeudores() {
		return lstMsgCondicionSinGarantesDeudores;
	}





	public void setLstMsgCondicionSinGarantesDeudores(
			List<Tabla> lstMsgCondicionSinGarantesDeudores) {
		this.lstMsgCondicionSinGarantesDeudores = lstMsgCondicionSinGarantesDeudores;
	}





	public Boolean getBlnVerSolExpLiquidacion() {
		return blnVerSolExpLiquidacion;
	}





	public void setBlnVerSolExpLiquidacion(Boolean blnVerSolExpLiquidacion) {
		this.blnVerSolExpLiquidacion = blnVerSolExpLiquidacion;
	}





	public Boolean getBlnViewSolicitudLiquidacion() {
		return blnViewSolicitudLiquidacion;
	}





	public void setBlnViewSolicitudLiquidacion(Boolean blnViewSolicitudLiquidacion) {
		this.blnViewSolicitudLiquidacion = blnViewSolicitudLiquidacion;
	}





	public AutorizacionLiquidacionFacadeLocal getAutorizaLiquidacionFacade() {
		return autorizaLiquidacionFacade;
	}





	public void setAutorizaLiquidacionFacade(
			AutorizacionLiquidacionFacadeLocal autorizaLiquidacionFacade) {
		this.autorizaLiquidacionFacade = autorizaLiquidacionFacade;
	}





	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}





	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}





	public BigDecimal getBdMontoInteresFdoRetiro() {
		return bdMontoInteresFdoRetiro;
	}





	public void setBdMontoInteresFdoRetiro(BigDecimal bdMontoInteresFdoRetiro) {
		this.bdMontoInteresFdoRetiro = bdMontoInteresFdoRetiro;
	}





	public List<AutorizaLiquidacionComp> getListaAutorizaLiquidacionComp() {
		return listaAutorizaLiquidacionComp;
	}





	public void setListaAutorizaLiquidacionComp(
			List<AutorizaLiquidacionComp> listaAutorizaLiquidacionComp) {
		this.listaAutorizaLiquidacionComp = listaAutorizaLiquidacionComp;
	}





	public BigDecimal getBdMondoFondoRetiroTotal() {
		return bdMondoFondoRetiroTotal;
	}





	public void setBdMondoFondoRetiroTotal(BigDecimal bdMondoFondoRetiroTotal) {
		this.bdMondoFondoRetiroTotal = bdMondoFondoRetiroTotal;
	}

	public Boolean getBlnCorrespondeLiquidacion() {
		return blnCorrespondeLiquidacion;
	}

	public void setBlnCorrespondeLiquidacion(Boolean blnCorrespondeLiquidacion) {
		this.blnCorrespondeLiquidacion = blnCorrespondeLiquidacion;
	}
	
	
}
