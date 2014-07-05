package pe.com.tumi.servicio.prevision.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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
//import org.apache.openjpa.jdbc.kernel.exps.Substring;

import pe.com.tumi.cobranza.cuentacte.facade.CuentacteFacadeRemote;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
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
import pe.com.tumi.credito.socio.captacion.domain.Concepto;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeRemote;
import pe.com.tumi.credito.socio.captacion.facade.CondicionFacadeRemote;
import pe.com.tumi.credito.socio.captacion.facade.VinculoFacadeRemote;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeRemote;
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
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
//import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficioId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.CuentaConceptoComp;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
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
//import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevisionId;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevisionId;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;
import pe.com.tumi.servicio.prevision.domain.composite.AutorizaPrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;
import pe.com.tumi.servicio.prevision.facade.LiquidacionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeLocal;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;


/**
 * @author USER
 *19210376
 */
public class SolicitudPrevisionController {
	protected static Logger log = Logger.getLogger(SolicitudPrevisionController.class);
	private ExpedientePrevision beanExpedientePrevision;
	private ExpedientePrevisionComp registroSeleccionado;
	//private ExpedientePrevision registroSeleccionadoBusqueda;
	private BeneficiarioPrevision  beneficiarioSeleccionado;
	private FallecidoPrevision  fallecidoSeleccionado;
	private Persona personaFallecida;
	private Persona personaBusqueda;
	private Integer intTipoPersona;
	private SocioComp beanSocioComp;
	private Usuario usuario;
	private Integer intIdTipoPersona;
	private String strNroSolicitud;
	private Integer intTipoSucursalBusq;
	private Integer intIdEstadoSolicitud;
	private Date dtFecInicio;
	private Date dtFecFin;
	private Date dtFechaRegistro;
	private Date dtFechaFallecimiento;
	private Date dtFechaSustento;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	private Estructura beanEstructuraSocioComp;
	
	// Mensaje de error para validarExpediente
	private String strMsgTxtMonto;
	private String strMsgTxtObservacion;
	private String strMsgTxtTipoSolicitud;
	private String strMsgTxtSubOperacion;
	private String strMsgTxtEvaluacion;
	private String strMsgTxtFechaSepelio;
	private String strMsgTxtGrabar;
	private String strMsgTxtTieneBeneficiarios;
	private String strMsgTxtTieneFallecidos;
	// Mensaje de error al agregar beneficiario
	private String strMsgTxtAgregarBeneficiario;
	private String strUnidadesEjecutorasConcatenadas = "";
	
	// Mensaje de error evaluar solicitud
	private String strMsgTxtAESPeriocidad;
	private String strMsgTxtAESCuotasSepelio;
	private String strMsgTxtAESDeudaExistente;
	private String strMsgTxtAESFechaSustentacion;
	private String strMsgTxtSepelioFechaPresentacion;
	private String strMsgTxtRetiroValorCuotaMensual;
	private String strMsgTxtSepelioTitularNoDebeDeudaExistente;
	private String strMsgTxtSumaPorcentaje;
	private String strFrecuenciaPresentacionSolicitud;
	private Integer intValorComparacion;
	private Integer intTiempoSustentoTabla;
	private String strMinimoMaximo;
	private String strMsgTxtFechaFallecimiento;
	private String strMsgTxtCuotasRetiro;
	
	// Mensaje de evaluar la captacion recuperada
	private String strMsgTxtCaptacionConcepto;
	private String strMsgTxtCaptacionCondicion;
	private String strMsgTxtCaptacionSepelio;
	private Integer intNroCuotasPagadasTotal;
	private List<ExpedientePrevisionComp> listaExpedientePrevisionBusq;
	private List<RequisitoPrevision> listaRequisitoPrevision;
	private List<BeneficiarioPrevision> listaBeneficiarioPrevisionBusq;
	private List<FallecidoPrevision> listaFallecidosPrevisionBusq;
	//private List<BeneficiarioPrevision> listaBeneficiarioPrevisionSol;
	// 03.07.2013
	//private List<BeneficiarioPrevision> listaBeneficiarioPrevisionBusqTitular;
	//private List<FallecidoPrevision> listaFallecidosPrevisionBusqTitular;
	//private List<Persona> listaBeneficiarios;
	private Persona nuevoBeneficiario;
	private List<Tabla> listaTipoRelacion;	
	private List<Tabla> listaSubTipoSolicitud;
	private List<Tabla> listaSubTipoSolicitudBusqueda;
	private List<Tabla> listaTablaDescripcionAdjuntos;
	private Integer intTipoSolicitud;
	private Integer intTipoSolicitudBusq;
	private List<Tabla> listaTipoSolicitud;
	private List<Estructura> listEstructura;
	private List<RequisitoPrevisionComp> listRequisitoPrevision;
	private List<Sucursal> listSucursal;
	private List<Subsucursal>listSubSucursal;
	private List<Archivo> listaArchivo;
	private Archivo archivoAdjunto;
	private Tabla tablaEstado;
	private Integer intSolicitudRegularidad;
	private Integer intSolicitudPeriodicidad;
	private Integer intNroCuotasDefinidasSepelio;
	private Integer intNroCuotasDefinidasRetiro;
	private Integer intTiempoAportacion;
	private Integer intTiempoPresentacion;
	private BigDecimal bdAnnosAportacionCalculado;
	
	/**
	 * Indica la accion que se va a realizar con la Solicitud: 
	 * Constante.MANTENIMIENTO_GRABAR - Constante.MANTENIMIENTO_ELIMINAR - Constante.MANTENIMIENTO_MODIFICAR
	 */
	private String strSolicitudPrevision;
	/**
	 * Por definir
	 */
	private Boolean formSolicitudPrevisionRendered;
	private Boolean pgValidDatos;
	private Boolean blnDatosSocio;
	private Boolean blnCaptacion;
	//private Boolean blnEvaluacionPrevision;
	private Integer intTipoRelacion;
	private String strMsgErrorValidarDatos;
	private Integer intTipoPrevision;
	private Boolean blnTieneAdjuntosConfigurados;
	private Boolean blnBotonActulizar;
	private Boolean blnBotonVer;
	private Boolean blnBotonEliminar;
	//rVillarreal 12.06.2014
	private boolean mostrarBoton;
	private boolean mostrarBotonUno;
	private boolean mostrarBotonDos;
	/**
	 * Muestra la opcion para busqueda y validar datos de Socio.
	 */
	private Boolean blnShowValidarDatos;
	//private Boolean blnShowEvaluacion;
	/**
	 * Muestra el formulario de Solicitud Prevision: solicitudPrevisionBody.jsp
	 */
	private Boolean blnShowDivFormSolicitudPrevision;
	/**
	 * Oculta el boton de Agregar Beneficairio si ya existe uno registrado.
	 */
	private Boolean blnExisteBeneficiario;

	// Tabs de Solicitud de Prestamo
	private Boolean blnSolicitud;
	private Boolean blnAutorizacion;
	private Boolean blnGiro;
	private Boolean blnIsSepelio;
	private Boolean blnIsAES;
	private Boolean blnIsRetiro;
	
	/**
	 * Booleano que deshabilita los combos de tipo y sub tipo de solicitud luego de evaluar la solicitud.
	 */
	private Boolean blnDeshabilitar;
	/**
	 * Booleano que indica si se muestra o no un campo de acuerdo al tipo de solicitud (AES/Sepelio)
	 */
	//private Boolean blnCampoHabil;
	/**
	 * Booleano que indica si se muestra o no Los Beneficiarios de SEPELIO acuerdo al tipo de solicitud.
	 */
	private Boolean blnBeneficiarioSepelio;
	/**
	 * 
	 * Booleano que indica si se muestra o no Los Beneficiarios de AES acuerdo al tipo de solicitud.
	 */

	private Boolean blnBeneficiarioNormal;
	/**
	 * 
	 * Booleano que indica si se muestra o no Los Beneficiarios de RETIRO acuerdo al tipo de solicitud.
	 */
	private Boolean blnBeneficiarioRetiro;
	
	private Boolean blnArchivamiento;
	
	/**
	 * Muestra el resto del formulario de Solicitud, luego de la Evaluacion.
	 */
	private Boolean blnPostEvaluacion;
	
	private 	Integer	intCboTipoRelPerJuri;// = 1;
	private 	Integer intCboTiposPersona = 2;
	private 	Integer intCboTiposDocumento = 4;

	private List<Tabla>		listaTablaSucursal;
	private List<Subsucursal> listaSubsucursal;
	private String strSubsucursalSocio;
	
	private TablaFacadeRemote tablaFacade;
	private SocioFacadeRemote socioFacade = null;
	private GeneralFacadeRemote generalFacade = null;
	private CuentaFacadeRemote cuentaFacade = null; 
	private PrevisionFacadeRemote previsionFacade = null; 
	private EstructuraFacadeRemote estructuraFacade = null;
	private ConvenioFacadeRemote convenioFacade =null;
	private CaptacionFacadeRemote captacionFacade = null;
	private CondicionFacadeRemote condicionFacade = null;
	private ConceptoFacadeRemote conceptoFacade = null;
	private PersonaFacadeRemote personaFacade = null;
	private EmpresaFacadeRemote empresaFacade= null;
	private VinculoFacadeRemote vinculoFacade = null;
	private PlanillaFacadeRemote planillaFacade = null;
	private CreditoFacadeRemote creditoFacade = null;
	private CuentacteFacadeRemote cuentaCteFacade = null;
	private PermisoFacadeRemote permisoFacade = null;
	private ConceptoFacadeRemote conceptoFacadeRemote = null;
	private LiquidacionFacadeRemote liquidacionFacade = null;
	private List<RequisitoPrevisionComp>  listaRequisitoPrevisionComp = null;
	private List<RequisitoPrevisionComp> listaCondicionesPrevisionComp = null;
	private Boolean blnHayExcepciones = null;
	private Boolean blnCondicionPeridoMeses = null;
	private Boolean blnCondicionCuotaSepelio = null;
	private Boolean blnCondicionDebeTenerDeuda = null;
	private Boolean blnCondicionPresentacionSol = null;
	private Boolean blnCondicionNODebeTenerDeuda = null;
	private Boolean chkCondicionPeridoMeses;
	private Boolean chkCondicionCuotaSepelio;
	private Boolean chkCondicionDebeTenerDeuda;
	private Boolean chkCondicionPresentacionSol;
	private Boolean blnFechaSustentoMedico = null;
	private Boolean blnFechafallecimiento = null;
	private Boolean blnValidacionCaptacionRecuperada=null;
	private Boolean blnSumaPorcentajes = null;
	private Boolean chkCondicionCuotaRetiro;

	//combo de TipoDocumento
	private 	List<Tabla>				listDocumentoBusq;
	private 	List<Tabla>				listDocumento;
	private Integer intUltimoEstadoPrevision;
	private String strUltimoEstadoPrevision;
	private Integer intSubTipoSolicitud;
	//private String 	strFechaFallecimiento;
	private Boolean blnBenefTieneRelFam;
	//private String 	strFechaSustentoMedico;
	private String 	strObservacion;
	private BigDecimal bdMontoSolicitado; 
	private BigDecimal bdMontoTotalAportadoSepelio;
	private Integer intParaTipoCaptacion;
	private BigDecimal bdPorcGastosAdministrativo; 
	
	// Campos para la busqueda
	private Integer		intTipoCreditoFiltro;
	private Integer		intSubTipoCreditoFiltro;
	private Integer		intTipoPersonaFiltro;
	private Integer		intTipoBusquedaPersonaFiltro;
	private String		strTextoPersonaFiltro;
	private Integer		intItemExpedienteFiltro;
	private EstadoPrevision		estadoPrevisionFiltro;
	private List<Tabla>		listaTablaEstadoPago;
	//private Integer		intTipoCreditoFiltro;
	private List<Tabla>		listaTablaTipoDocumento;
	private Integer		intTipoBusquedaFechaFiltro;
	private Integer		intTipoBusquedaSucursal;
	private List<Tabla>		listaTipoBusquedaSucursal;
	private Integer		intIdSubsucursalFiltro;
	private Integer		intIdSucursalFiltro;
//	private List		listaExpedientePrevisionX;
	private List<ExpedientePrevision> listaExpedientePrevision;
	private List<Tabla> listaTipoVinculo;
	private List<ExpedientePrevision> listaExpedientePrevisionSocio;
	//rVillarreal 26.05.2014
	private List<BloqueoCuenta> listaBloqueoCuenta;
	
	private pe.com.tumi.credito.socio.captacion.domain.Vinculo  vinculoRetiroSeleccionado = null;
	private String campoBuscarBeneficiario;
	private String campoBuscarFallecido;
	private BigDecimal bdValorNumeroCuotas;
	private BigDecimal bdValorAnnosEntero;
	private Captacion beanCaptacion;
	private BigDecimal bdMontoTotalAportadoRetiro;
	private Boolean blnCondicionCuotaRetiro = null;

	// cgd
	private List<Tabla> listaDescripcionModalidad;
	private List<Tabla> listaDescripcionTipoSocio;
	private String strDescripcionTipoPrevision;
	private String strDescripcionPrevision;
	private Boolean blnMostrarDescripcionTipoPrevision;
	private List<Tabla> listaSubOperacion;
	private String strFechaRegistro;
	private List<CuentaConcepto> listaCuentaConcepto;
	private Boolean blnCheckSinRelacion;
	private Boolean blnIsSepelioTitular;
	private BigDecimal bdMontoCalculadoBeneficio;
	private List<AutorizaPrevisionComp> listaAutorizaPrevisionComp;
	
	private Boolean blnSol;
	private Boolean blnAut;
	private Boolean blnArch;
	private Boolean blnGir;
	
	private Boolean blnBloquearXCuenta;
	private String strMensajeValidacionCuenta;
	
	// parametros de busqueda - cgd 01.10.2013
	private Integer intBusqTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;
	private Integer intBusqEstado;	
	private Integer intBusqTipoPrevision;
	private Integer intBusqSubTipoPrevision;
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private	List<Sucursal> listaSucursalBusqueda;
	private List<Subsucursal> listaSubSucursaBusqueda;
	
	//RVILLARREAL 20.05.2014
	private List<Tabla> lstParaQuePinteSepelio;
	private 	Integer 				SESION_IDEMPRESA;
	private Boolean blnVerRegistroSolExpPrevision;
	
	
	public SolicitudPrevisionController() {
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
		beanExpedientePrevision = new ExpedientePrevision();
		beanExpedientePrevision.setId(new ExpedientePrevisionId());
		beanExpedientePrevision.setListaEstadoPrevision(new ArrayList<EstadoPrevision>());
		beanExpedientePrevision.setListaBeneficiarioPrevision(new ArrayList<BeneficiarioPrevision>());
		beanExpedientePrevision.setListaRequisitoPrevisionComp(new ArrayList<RequisitoPrevisionComp>());
		beanExpedientePrevision.setListaFallecidoPrevision(new ArrayList<FallecidoPrevision>());
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		
		listaArchivo = new ArrayList<Archivo>();
		beanCaptacion = new Captacion();			
		blnVerRegistroSolExpPrevision = false;		
		blnShowDivFormSolicitudPrevision = false;
		blnShowValidarDatos = false ;
		blnPostEvaluacion = false;
		intUltimoEstadoPrevision = null;
		strUltimoEstadoPrevision = "";
		campoBuscarFallecido="";
		campoBuscarBeneficiario="";
		blnTieneAdjuntosConfigurados = false;
		blnCheckSinRelacion = false;
		limpiarMensajesIsValidoExpediente();
		strSolicitudPrevision = "";
		//RVILLARREAL 23.04.2014
		lstParaQuePinteSepelio = new ArrayList<Tabla>();
		//RVILLARREAL 12.06.2014
		mostrarBoton = false;
		mostrarBotonUno = false;
		mostrarBotonDos = false;
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		try {
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			previsionFacade = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			convenioFacade = (ConvenioFacadeRemote)EJBFactory.getRemote(ConvenioFacadeRemote.class);
			captacionFacade = (CaptacionFacadeRemote)EJBFactory.getRemote(CaptacionFacadeRemote.class);
			condicionFacade = (CondicionFacadeRemote)EJBFactory.getRemote(CondicionFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			vinculoFacade = (VinculoFacadeRemote)EJBFactory.getRemote(VinculoFacadeRemote.class);
			planillaFacade = (PlanillaFacadeRemote)EJBFactory.getRemote(PlanillaFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			cuentaCteFacade = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			conceptoFacadeRemote = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			liquidacionFacade = (LiquidacionFacadeRemote) EJBFactory.getRemote(LiquidacionFacadeRemote.class);

			
			listaTipoSolicitud = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA), "F");
			//JCHAVEZ 23.04.2014
			listaTipoRelacion =tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "E");
//			listaTipoRelacion =tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "D");
			listaSubTipoSolicitud = null;

			listaTablaEstadoPago = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSOLICPRESTAMO), "A");
			listaTipoBusquedaSucursal = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA), "A");
			listaTablaTipoDocumento = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "F");
			listaTipoVinculo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOVINCULO));
			
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			estadoPrevisionFiltro = new EstadoPrevision();
			estadoPrevisionFiltro.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
			
			listaExpedientePrevision = new ArrayList<ExpedientePrevision>();
			
			cargarListaTablaSucursal();
			
			listaDescripcionModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
			listaDescripcionTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			
			listaSucursalBusqueda = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			ordenarAlfabeticamenteSuc();

		} catch (EJBFactoryException e) {
			log.error("error: " + e.getMessage());
		} catch (NumberFormatException e1) {
			log.info(e1);
			e1.printStackTrace();
		} catch (BusinessException e2) {
			e2.printStackTrace();
			log.info(e2);
		} catch (Exception e3) {
			log.error(e3);
		}

		finally {
			inicio(null);
		}
	}
	
	
	public List<Sucursal> ordenarAlfabeticamenteSuc(){
		if(listaSucursalBusqueda != null && !listaSucursalBusqueda.isEmpty()){
			//Ordenamos por nombre
			Collections.sort(listaSucursalBusqueda, new Comparator<Sucursal>(){
				public int compare(Sucursal uno, Sucursal otro) {
					return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
				}
			});	
		}
			return listaSucursalBusqueda;
	}
	
	/**
	 * Carga los valores iniciales
	 * @param event
	 */
	public void inicio(ActionEvent event) {
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		limpiarMensajesIsValidoExpediente();
		limpiarFormSolicitudPrevision();
		blnCaptacion = false;
			if (usuario != null) {
				setUsuario(usuario);
				cargarUsuarioPermiso();
				} else {
				
				blnSolicitud = false;
				blnAutorizacion = false;
				blnGiro = false;
				blnArchivamiento = false;

			}
	}
	
	
	




	
	
	/**
	 * Metodo que realiza consulta para definir permisos de usuario logueado.
	 */
	public void cargarUsuarioPermiso() {
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		Integer MENU_SOLICITUDPREVISION = 259;
		Integer MENU_AUTORIZACION = 260;
		Integer MENU_GIRO = 261;
		Integer MENU_ARCHIVAMIENTO = 263;
		try {
			if (usuario != null) {
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_SOLICITUDPREVISION);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				PermisoFacadeRemote remotePermiso = (PermisoFacadeRemote) EJBFactory.getRemote(PermisoFacadeRemote.class);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnSolicitud = (permiso == null) ? true : false;
				id.setIntIdTransaccion(MENU_AUTORIZACION);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnAutorizacion = (permiso == null) ? true : false;
				id.setIntIdTransaccion(MENU_GIRO);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnGiro = (permiso == null) ? true : false;
				id.setIntIdTransaccion(MENU_ARCHIVAMIENTO);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnArchivamiento = (permiso == null) ? true : false;
				setUsuario(usuario);
			} else {
				blnSolicitud = false;
				blnAutorizacion = false;
				blnGiro = false;
				blnArchivamiento = false;
			}
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}
	
	


	/**
	 * Permite Bloquear los tabs de acuerdo al perfil logueado.
	 */
	public void cargarPermisos(){
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		Integer MENU_SOLICITUD = 259;
		Integer MENU_AUTORIZACION = 260;
		Integer MENU_GIRO = 261;
		Integer MENU_ARCHIVAMIENTO = 262;
		PermisoFacadeRemote remotePermiso = null;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_SOLICITUD);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				remotePermiso = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnSol = (permiso == null);
				
				id.setIntIdTransaccion(MENU_AUTORIZACION);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnAut = (permiso == null);
				
				id.setIntIdTransaccion(MENU_GIRO);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnGir = (permiso == null);
				
				id.setIntIdTransaccion(MENU_ARCHIVAMIENTO);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnArch = (permiso == null);
				
			}else{
				blnSol = false;
				blnAut= false;
				blnGir= false;
				blnArch= false;
			}
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}		
	}
	
	
	
	
	/**
	 * Metodo de limpieza asociado a h:outputText de nuevoCreditoBody.jsp
	 * Ayuda a controlar los tabs de acuerdo a perfiles de usuario logueado.
	 * @return
	 */
	public String getLimpiarPrevision(){
		cargarPermisos();
		limpiarFormSolicitudPrevision();
		limpiarBeneficiarios(null);
		limpiarCampos(null);
		limpiarFallecidos(null);
		limpiarFiltrosGrilla(null);
		limpiarGrillasSepelio(null);
		limpiarMensajesIsValidoExpediente();
		
		listaExpedientePrevisionBusq = new ArrayList<ExpedientePrevisionComp>();
		
		pgValidDatos = false;
		blnDatosSocio = false;
		return "";
	}
	
	
	/**
	 * Metodo que ejecuta al presionar el boton NUEVO.
	 * Limpia lel formulario de solicitud
	 * @param event
	 */
	public void nuevaSolicitudPrevision(ActionEvent event) {
		limpiarFormSolicitudPrevision();
		limpiarMensajesIsValidoExpediente();
		
		strSolicitudPrevision = Constante.MANTENIMIENTO_GRABAR;
		blnShowValidarDatos = true;
		dtFechaRegistro = Calendar.getInstance().getTime();
		blnShowDivFormSolicitudPrevision = false;
		listaBeneficiarioPrevisionBusq = new ArrayList<BeneficiarioPrevision>();
		listaFallecidosPrevisionBusq = new ArrayList<FallecidoPrevision>();
	}
	
	/**
	 * 
	 * @param event
	 */
	/*public void buscarSolicitudPrevision2(ActionEvent event){
		log.info("<---------------- SolicitudPrevisionController.buscarSolicitudPrevision2 ------------------------>");	
		PrevisionFacadeLocal previsionFacade= null;
		try {
			previsionFacade = (PrevisionFacadeLocal) EJBFactory.getLocal(PrevisionFacadeLocal.class);
			ExpedientePrevision o = new ExpedientePrevision();
			ExpedientePrevisionComp expedientePrevisionComp = new ExpedientePrevisionComp();
			listaExpedientePrevisionBusq = previsionFacade.getListaExpedienteCreditoCompDeBusqueda(expedientePrevisionComp);
		} catch (BusinessException e) {
			e.printStackTrace();
			log.error(e);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
			log.error(e);
		}	
	
	}*/
	
	/**
	 * Valida los datos ingresados y define si procede o no la evaluacion.
	 * 
	 * 
	 * 
	 * 
	 * @param beanExpedientePrevision
	 * @return
	 */
	private Boolean procedeEvaluacion(ExpedientePrevision beanExpedientePrevision) {
		Boolean validProcedeEvaluacion = true;

	// Valida la seleccion de tipo de solicitud
		if (intTipoSolicitud == null || intTipoSolicitud == 0) {
			setStrMsgTxtTipoSolicitud("El campo Tipo de Solicitud debe ser ingresado.");
			validProcedeEvaluacion = false;
		} else {
			setStrMsgTxtTipoSolicitud("");
		}

	// Valida la seleccion de sub tipo de solicitud
		if (intSubTipoSolicitud == null || intSubTipoSolicitud.intValue()== 0) {
				setStrMsgTxtSubOperacion("El campo Sub Tipo de Solicitud debe ser ingresado.");
				validProcedeEvaluacion = false;
		} else{
			setStrMsgTxtSubOperacion("");
		}
		
	// SI ES SEPELIO	
		if(intTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))){
			if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null 
				|| !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
					setStrMsgTxtTieneBeneficiarios("");
					validProcedeEvaluacion = true;
			}else{
				setStrMsgTxtTieneBeneficiarios("* Se debe de registrar al menos un Beneficiario. ");
				validProcedeEvaluacion = false;
			}
			
			Integer intNrofallecidos = beanExpedientePrevision.getListaFallecidoPrevision().size();

			if(!(intSubTipoSolicitud.intValue() == Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)){
				if(intNrofallecidos >0){
					setStrMsgTxtTieneFallecidos("");
					validProcedeEvaluacion = true;
				}else{
					setStrMsgTxtTieneFallecidos("* Se debe de registrar al menos un Fallecido. ");
					validProcedeEvaluacion = false;
					}		
			}
			
			// Si es sepelio debe ingresar la feaha de fallecimineto.
			// De esa fecha dependen muchos calculos.
			if(beanExpedientePrevision.getDtFechaFallecimiento() == null){
				setStrMsgTxtFechaSepelio("El campo Fecha de Fallecimiento debe ser ingresado.");
				validProcedeEvaluacion = false;
			}else{
				setStrMsgTxtFechaSepelio("");
				// agregar validacion de fecha de hoy
			}
	
	// SI ES AES
			// Se valida la existencia de solicitudes aes en estado requisito. No aplica para excepciones.
		} else if(intTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))){
					
			if (intSubTipoSolicitud == null
				|| intSubTipoSolicitud.intValue()== 0) {
					setStrMsgTxtSubOperacion("El campo Sub Tipo de Solicitud debe ser ingresado.");
					validProcedeEvaluacion = false;
				} else {
						setStrMsgTxtSubOperacion("");
						if((intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_AES_EXCEPCIONALJEFE)==0)
							||(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_AES_EXCEPCIONALGERENCIA)==0)){
								validProcedeEvaluacion = true;
								setStrMsgTxtGrabar("");
						}else{
							if(beanExpedientePrevision.getId().getIntItemExpediente() == null){
								if(existeSolicitudAES()){
									setStrMsgTxtGrabar("Ya existe una Solicitud AES en estado REQUISITO. No procede la Evaluación.");
									validProcedeEvaluacion = false;
								}else{
									setStrMsgTxtGrabar("");
									validProcedeEvaluacion = true;
								}
							}else{
								setStrMsgTxtGrabar("");
								validProcedeEvaluacion = true;
							}
						}	
				}
		} else if(intTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))){
			 		CuentaConcepto cuentaConceptoRetiroVal = null;
			// Validar que existe cta cto retiro con saldo <> cero
 			 		cuentaConceptoRetiroVal = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
			 		if(cuentaConceptoRetiroVal != null){
			 			if(cuentaConceptoRetiroVal.getBdSaldo().compareTo(BigDecimal.ZERO)>= 0 ){
			 				setStrMsgTxtGrabar("");
							validProcedeEvaluacion = true;
			 			}else{
							setStrMsgTxtGrabar("No existe Cuenta Concepto de Retiro y/o no posee saldo.");
							validProcedeEvaluacion = false;
			 			}
			 		}else{
			 			setStrMsgTxtGrabar("No existe Cuenta Concepto de Retiro para el socio evaluado.");
						validProcedeEvaluacion = false;	
			 		}
		}
		if(!validacionFinalOtorgamientoPrevision()){
			validProcedeEvaluacion = false;
		}
		return validProcedeEvaluacion;
	}
	
	
	/**
	 * Realiza las validaciones segun tipo de prevision
	 * @param beanExpedientePrevision
	 * @return
	 */
	private Boolean isValidoExpedientePrevision(ExpedientePrevision beanExpedientePrevision) {
		Boolean validExpedientePrevision = true;

		try {
			// 0. Valida las excepciones a reliar segun subtipo de AES excepcional.
			existeExcepcion();

			// 1. Validacion de tipo de prevision
			if (intTipoSolicitud == null || intTipoSolicitud == 0) {
				setStrMsgTxtTipoSolicitud("El campo Tipo de Solicitud debe ser ingresado.");
				validExpedientePrevision = false;
			} else {
				setStrMsgTxtTipoSolicitud("");
			}

			// 2. validamos el subtipo de prevision
			if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
				if (intSubTipoSolicitud == null
					|| intSubTipoSolicitud.intValue()== 0) {
						setStrMsgTxtSubOperacion("El campo Sub Tipo de Solicitud debe ser ingresado.");
						validExpedientePrevision = false;
					} else {
						setStrMsgTxtSubOperacion("");
					}	
			} 
			
			// 3. validamos el monto del beneficio
			if (beanExpedientePrevision.getBdMontoBrutoBeneficio() == null) {
					setStrMsgTxtMonto("El campo Monto Total debe ser ingresado.");
					validExpedientePrevision = false;
			} else {
				setStrMsgTxtMonto("");
			}
			
			// 4. validamos el campo observacion
			if (beanExpedientePrevision.getStrObservacion() == null
				|| beanExpedientePrevision.getStrObservacion().equals("")) {
					setStrMsgTxtObservacion("El campo de Observación debe ser ingresado.");
					validExpedientePrevision = false;
			} else {
				setStrMsgTxtObservacion("");
			}
			
			// 5. Existencia de la captacion
			if (blnCaptacion == false) {
				setStrMsgTxtEvaluacion("Aún no se Evalua la solicitud. Por favor presionar el botón EVALUAR. ");
				validExpedientePrevision = false;
			} else {
				setStrMsgTxtEvaluacion("");
				validExpedientePrevision = true;
			}
			
			// 6. EXCEPCIONES - valida las consiciones de la solciitud
			if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				if(!blnHayExcepciones){
					if(blnCondicionCuotaSepelio && blnCondicionPeridoMeses && blnCondicionDebeTenerDeuda ){
						setStrMsgTxtGrabar("");
						} else {
							validExpedientePrevision = false;
							setStrMsgTxtGrabar("La solicitud NO CUMPLE con las Condiciones requeridas. No procede el registro de la misma. ");
						}
				}
			} else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
				porcetanjesSuman100();
				if(validarFechaFallecimiento()){
					validExpedientePrevision = validarFechaFallecimiento();
					setStrMsgTxtGrabar("La solicitud presenta incoherencias. No procede el registro de la solicitud. ");
				}
				if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
					// CGD-07.08.2013
					validExpedientePrevision = isValidCondicionNoDebeTenerDeudaSepelioTitular();
					setStrMsgTxtGrabar("La solicitud NO CUMPLE con las Condiciones requeridas. No procede el registro de la solicitud. ");
				}else{
						if(!(blnCondicionCuotaSepelio && blnCondicionPresentacionSol)){
								validExpedientePrevision = false;
								setStrMsgTxtGrabar("La solicitud NO CUMPLE con las Condiciones requeridas. No procede el registro de la solicitud. ");
						} else {
								setStrMsgTxtGrabar("");
						}
				}
				if(!blnSumaPorcentajes){
					validExpedientePrevision = false;
				}
			} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
				porcetanjesSuman100();
				//if(!blnCondicionCuotaRetiro){
					//	validExpedientePrevision = false;
					//	setStrMsgTxtGrabar("La solicitud NO CUMPLE con las Condiciones requeridas. No procede el registro de la solicitud. ");
					//} else {
						setStrMsgTxtGrabar("");
					//}
			}

			
			Boolean blnValidCaptacion = Boolean.TRUE;
			blnValidCaptacion = validarCaptacionRecuperada();
			if(validExpedientePrevision && blnValidCaptacion){
				validExpedientePrevision = Boolean.TRUE;
			}else{
				validExpedientePrevision = Boolean.FALSE;
			}
			// 7. Valida la existencia del beanCaptacion
			//if(!blnValidacionCaptacionRecuperada){
			//	validExpedientePrevision = false;
			//}
			
			//8. SE VALIDAN LOS MONTOS DE LA CAPTACION VS LOS DE LA SOLICUT
			
			
			// 8. Validar Monto de la solicitud configurado para aes...
			/*if(beanCaptacion.getListaConcepto() != null && !beanCaptacion.getListaConcepto().isEmpty()){
				for (Concepto concepto : beanCaptacion.getListaConcepto()) {
					System.out.println(""+concepto.getId().getIntItem());
					System.out.println(""+concepto.getId().getIntParaTipoCaptacionCod());
					System.out.println(""+concepto.getId().getIntParaTipoConcepto());
					System.out.println(""+concepto.getIntDia());
					System.out.println(""+concepto.getIntMonto());
					System.out.println(""+concepto.get);
				}
				
			}*/
			
			Boolean blnCuenta = Boolean.TRUE;
			if(beanExpedientePrevision != null && beanExpedientePrevision.getId() != null && beanExpedientePrevision.getId().getIntItemExpediente() != null){
				
				blnCuenta = validarEstadoCuenta(beanExpedientePrevision.getId());
				
				if(blnCuenta){
					validExpedientePrevision = Boolean.FALSE;
				}
			}
			
		} catch (Exception e) {
			log.error("Error en isValidoExpedientePrevision ---> "+e);
		}
		return validExpedientePrevision;
	}
	
	
	/**
	 * Limpía los mensajes de Validacion
	 */
	public void limpiarMensajesIsValidoExpediente() {
		setStrMsgTxtTipoSolicitud("");
		setStrMsgTxtSubOperacion("");
		setStrMsgTxtMonto("");
		setStrMsgTxtObservacion("");
		setStrMsgTxtAESCuotasSepelio("");
		setStrMsgTxtCuotasRetiro("");
		setStrMsgTxtAESDeudaExistente("");
		setStrMsgTxtAESPeriocidad("");
		setStrMsgTxtEvaluacion("");
		setStrMsgTxtGrabar("");
		setStrMsgTxtFechaSepelio("");
		setStrMsgErrorValidarDatos("");
		dtFechaRegistro = Calendar.getInstance().getTime();
		setStrMsgTxtCaptacionConcepto("");
		setStrMsgTxtCaptacionCondicion("");
		setStrMsgTxtAgregarBeneficiario("");
		setStrMsgTxtCaptacionSepelio("");
		
		setStrFrecuenciaPresentacionSolicitud("");
		setStrMsgTxtAESFechaSustentacion("");
		setStrMsgTxtSepelioFechaPresentacion("");
		setStrMsgTxtSepelioFechaPresentacion("");
		setStrNroSolicitud("");
		setStrObservacion("");
		setStrSolicitudPrevision("");
		setStrUltimoEstadoPrevision("");
		setStrMsgTxtSumaPorcentaje("");
		setChkCondicionCuotaSepelio(false);
		setChkCondicionCuotaRetiro(false);
		setChkCondicionDebeTenerDeuda(false);
		setChkCondicionPeridoMeses(false);
		strMsgTxtSepelioTitularNoDebeDeudaExistente = "";
		strMsgTxtFechaFallecimiento = "";

		blnDeshabilitar = false;
		
		campoBuscarFallecido="";
		campoBuscarBeneficiario="";
	}
	
	
	/**
	 * Valida la solicitud y define si pasa al estado REQUISITO o PRevision.
	 * Si pasa a Solicitud devuelve CERO, caso contarrio UNO.
	 * @return
	 */
	private int isValidAdjuntosSolicitudCambioEstado() {
		int cnt = 0;
		Integer intTotal = 0;
		Integer intExistenetes = 0;
		
		if(blnTieneAdjuntosConfigurados){
			if(beanExpedientePrevision.getListaRequisitoPrevisionComp()== null || beanExpedientePrevision.getListaRequisitoPrevisionComp().isEmpty()){
				cnt = 1;
			}else{
				for (RequisitoPrevisionComp requisitoComp : beanExpedientePrevision.getListaRequisitoPrevisionComp()) {
					if(requisitoComp.getDetalle().getIntOpcionAdjunta().compareTo(new Integer(1))==0){
						intTotal++;
					}
						
				}
				// contamios cuienatps estar registrados
					for (RequisitoPrevisionComp requisitoComp : beanExpedientePrevision.getListaRequisitoPrevisionComp()) {
						if(requisitoComp.getDetalle().getIntOpcionAdjunta().compareTo(new Integer(1))==0
							&& requisitoComp.getFileDocAdjunto() != null){
							intExistenetes++;
						}
					}
					if(intTotal.compareTo(intExistenetes)==0){
						cnt = 0;		
					}else{
						cnt = 1;
					}
			}
		}
		return cnt;
	}

	
	/**
	 * Metodo invocado al presionar GUARDAR o GRABAR.
	 * Redirecciona ala funcion grabarSolicitudPrevision() o modificarSolicitudPrevision()segun sea el caso.
	 * @param event
	 */
	public  void grabarSolicitud(ActionEvent event){
		
		try {
			if(beanExpedientePrevision.getId().getIntItemExpediente() == null ){
				grabarSolicitudPrevision(event);
			}else{
				modificarSolicitudPrevision(event);
			}
		} catch (Exception e) {
			log.error("Error en grabarSolicitud ---> "+e);
		}
	}
	
	/**
	 * En el caso de AES no se registra el valor Mionto Neto, necesario para la autorizacion.
	 * Por lo tanto se setea el valor del Monto bruto en el neto.
	 *	
	 */
	public void setearMontoNetoAES(){
		try {
			beanExpedientePrevision.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			beanExpedientePrevision.getId().setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());

			// Seteamos el documento general de acuerdo al tipo de solicitud de prevision
			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0
				&& beanExpedientePrevision.getBdMontoBrutoBeneficio() != null){
				beanExpedientePrevision.setBdMontoNetoBeneficio(beanExpedientePrevision.getBdMontoBrutoBeneficio());	
			}
		} catch (Exception e) {
			log.error("Error en setearMontoNetoAES ---> "+e);
		}
		
	}
	
	/**
	 * Metodo que graba por primera vez la solicitud de prevision.
	 * @param event
	 * @throws ParseException
	 */
	public void grabarSolicitudPrevision(ActionEvent event) throws ParseException {
		Integer intValidacion = new Integer(0);
		try{
			setearMontoNetoAES();
			if (isValidoExpedientePrevision(beanExpedientePrevision) == false) {
				log.info("Datos de Previsión no válidos. Se aborta el proceso de grabación de Solicitud de Previsión.");
				return;
			}

			// Formando el expediente
			beanExpedientePrevision.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			beanExpedientePrevision.getId().setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());

			// 18.09.2013 - CGD
			beanExpedientePrevision.setIntPersEmpresaSucAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntEmpresaSucAdministra());
			beanExpedientePrevision.setIntSucuIdSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
			beanExpedientePrevision.setIntSudeIdSubSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSubsucurAdministra());

			// Seteamos el documento general de acuerdo al tipo de solicitud de prevision
			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				beanExpedientePrevision.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_AES);
			} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
				beanExpedientePrevision.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO);
			} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
				beanExpedientePrevision.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO);	
			}

			//captacion
			beanExpedientePrevision.setIntPersEmpresa(beanCaptacion.getId().getIntPersEmpresaPk());
			beanExpedientePrevision.setIntParaTipoCaptacion(beanCaptacion.getId().getIntParaTipoCaptacionCod());
			beanExpedientePrevision.setIntItem(beanCaptacion.getId().getIntItem());
			beanExpedientePrevision.setIntParaSubTipoOperacion(intSubTipoSolicitud); 

			if(blnBenefTieneRelFam != null ){
				beanExpedientePrevision.setIntBeneficiarioRel(blnBenefTieneRelFam==true?1:0);
			}

			beanExpedientePrevision.setIntSucuIdSucursal(beanSocioComp.getCuenta().getIntIdUsuSucursal());
			beanExpedientePrevision.setIntSudeIdsubsucursal(beanSocioComp.getCuenta().getIntIdUsuSubSucursal());

			// Validamos que todos requisitos se cumplan
			EstadoPrevision estadoPrevision = null;
			estadoPrevision = new EstadoPrevision();
			estadoPrevision.setTsFechaEstado(new Timestamp(new Date().getTime()));
			estadoPrevision.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
			estadoPrevision.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			estadoPrevision.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			estadoPrevision.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
			
			if (listaRequisitoPrevisionComp != null && listaRequisitoPrevisionComp.size() > 0) {
				beanExpedientePrevision.setListaRequisitoPrevisionComp(listaRequisitoPrevisionComp);
			}
			
			
			intValidacion = isValidAdjuntosSolicitudCambioEstado();
			if (intValidacion.compareTo(new Integer(0)) == 0) {
				estadoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
				beanExpedientePrevision.getListaEstadoPrevision().add(estadoPrevision);
			} else {
				// Si no se graba en estado REQUISITO
				estadoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
				beanExpedientePrevision.getListaEstadoPrevision().add(estadoPrevision);
			}	

			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				if (beanExpedientePrevision.getListaBeneficiarioPrevision() == null 
				||beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){		
					BeneficiarioPrevision beneficiarioDefault = new BeneficiarioPrevision();
					BeneficiarioPrevisionId beneficiarioDefaultId = new BeneficiarioPrevisionId();
					beneficiarioDefault.setId(beneficiarioDefaultId);
					beneficiarioDefault.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
					beneficiarioDefault.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
					beneficiarioDefault.setIntItemViculo(intTipoRelacion);
					beneficiarioDefault.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
					beneficiarioDefault.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
					List<BeneficiarioPrevision> lstBenefPrev = new ArrayList<BeneficiarioPrevision>();
					lstBenefPrev.add(beneficiarioDefault);
					beanExpedientePrevision.setListaBeneficiarioPrevision(lstBenefPrev);
				}
			} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
						if(intSubTipoSolicitud.intValue() == Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR){
							FallecidoPrevision fallecidoDefault = new FallecidoPrevision();
							FallecidoPrevisionId fallecidoDefaultId = new FallecidoPrevisionId();
							fallecidoDefault.setId(fallecidoDefaultId);
							fallecidoDefault.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
							fallecidoDefault.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
							fallecidoDefault.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
							fallecidoDefault.setIntPersPersonaFallecido(beanSocioComp.getPersona().getIntIdPersona());
							List<FallecidoPrevision> lstFallecPrev = new ArrayList<FallecidoPrevision>();
							lstFallecPrev.add(fallecidoDefault);
							beanExpedientePrevision.setListaFallecidoPrevision(lstFallecPrev);
						}
			}else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
						if(intSubTipoSolicitud.intValue() == Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR){
							FallecidoPrevision fallecidoDefault = new FallecidoPrevision();
							FallecidoPrevisionId fallecidoDefaultId = new FallecidoPrevisionId();
							fallecidoDefault.setId(fallecidoDefaultId);
							fallecidoDefault.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
							fallecidoDefault.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
							fallecidoDefault.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
							fallecidoDefault.setIntPersPersonaFallecido(beanSocioComp.getPersona().getIntIdPersona());
							List<FallecidoPrevision> lstFallecPrev = new ArrayList<FallecidoPrevision>();
							beanExpedientePrevision.setListaFallecidoPrevision(lstFallecPrev);
						}
			}
			// parche 09.07.2013 - beneficiarios x fallecidos
			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				if(beanExpedientePrevision.getListaBeneficiarioPrevision()!= null
					&& !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
					List<FallecidoPrevision> listaFallecidosAES = null;
					
					listaFallecidosAES = generarPersonaMotivoParaAES(beanExpedientePrevision.getListaBeneficiarioPrevision());
					if(listaFallecidosAES != null & !listaFallecidosAES.isEmpty()){
						beanExpedientePrevision.setListaFallecidoPrevision(listaFallecidosAES);
						beanExpedientePrevision.getListaBeneficiarioPrevision().clear();	
					}
				}	
			}else {
						modificarTodosLosBeneficiarios(event);	
				}
			if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
				recalcularMontosRetiro(event);	
			}else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
				recalcularMontosSepelio(event);	
			}
			setearMontoNetoAES();
			if(bdValorNumeroCuotas != null){
				beanExpedientePrevision.setIntNumeroCuotaFondo(bdValorNumeroCuotas.intValue());
			}
			beanExpedientePrevision = previsionFacade.grabarExpedientePrevision(beanExpedientePrevision);
			
			//25.06.2013 - cgd
			if (beanExpedientePrevision.getListaRequisitoPrevisionComp() != null
				&& beanExpedientePrevision.getListaRequisitoPrevisionComp().size() > 0) {
					renombrarArchivo(beanExpedientePrevision.getListaRequisitoPrevisionComp());
			}
			
			//validdamos que se haya generado itemexpediente...
			if((beanExpedientePrevision.getId().getIntItemExpediente() != null)){
				cancelarGrabarSolicitud(event);
			}
		} 
		catch (BusinessException e) {
			log.error("Error en grabarSolicitud --> "+e);
		}
	}
	
		
	/**
	 * Convierte una lista del tipo Beneficiaro al tipo Fallecidos.
	 * Solo aplica en el caso de AES
	 * @param lstBeneficiariosAES
	 * @return
	 */
	public List<FallecidoPrevision> generarPersonaMotivoParaAES(List<BeneficiarioPrevision> lstBeneficiariosAES){
		List<FallecidoPrevision> lstFallecidosAES = null;
		
		try {
			if(lstBeneficiariosAES != null && !lstBeneficiariosAES.isEmpty()){
				lstFallecidosAES = new ArrayList<FallecidoPrevision>();
				
				for (BeneficiarioPrevision beneficiarioAES : lstBeneficiariosAES) {
					FallecidoPrevision fallecidoAES = new FallecidoPrevision();
					FallecidoPrevisionId fallecidoAESId = new FallecidoPrevisionId();
					fallecidoAESId.setIntCuenta(beneficiarioAES.getId().getIntCuenta());
					fallecidoAESId.setIntPersEmpresaPrevision(beneficiarioAES.getId().getIntPersEmpresaPrevision());
					
					if(beneficiarioAES.getId().getIntItemBeneficiario() != null){
						fallecidoAESId.setIntItemFallecido(beneficiarioAES.getId().getIntItemExpediente());
					}
					if(beneficiarioAES.getId().getIntItemBeneficiario() != null){
						fallecidoAESId.setIntItemExpediente(beneficiarioAES.getId().getIntItemBeneficiario());
					}

					fallecidoAES.setId(fallecidoAESId);
					
					if(beneficiarioAES.getIntItemViculo() != null){
						fallecidoAES.setIntItemViculo(beneficiarioAES.getIntItemViculo());
					}
					if(beneficiarioAES.getIntPersPersonaBeneficiario() != null){
						fallecidoAES.setIntPersPersonaFallecido(beneficiarioAES.getIntPersPersonaBeneficiario());	
					}
					if(beneficiarioAES.getIntParaEstado() != null){
						fallecidoAES.setIntParaEstado(beneficiarioAES.getIntParaEstado());
					}
					lstFallecidosAES.add(fallecidoAES);
				}	
			}

		} catch (Exception e) {
			log.error("Error en generarPersonaMotivoParaAES ---> "+e);
		}
		return lstFallecidosAES;
	}
	
	
	/**
	 * Convierte una lista del tipo fallecido al tipo Beneficiario.
	 * Solo aplica en el caso de AES. AL momento de recuperar el expediente para la edicion.
	 * @param lstFallecidosAES
	 * @return
	 */
	public List<BeneficiarioPrevision> regenerarBeneficiariosXPersonaMotivoAES(List<FallecidoPrevision> lstFallecidosAES){
		List<BeneficiarioPrevision> lstBeneficiariosAES = null;
		
		try {
			if(lstFallecidosAES != null && !lstFallecidosAES.isEmpty()){
				lstBeneficiariosAES = new ArrayList<BeneficiarioPrevision>();
				
				for (FallecidoPrevision fallecidosAes : lstFallecidosAES) {
					BeneficiarioPrevision beneficiarioAES = new BeneficiarioPrevision();
					BeneficiarioPrevisionId beneficiarioAESId = new BeneficiarioPrevisionId();
					beneficiarioAESId.setIntCuenta(fallecidosAes.getId().getIntCuenta());
					beneficiarioAESId.setIntPersEmpresaPrevision(fallecidosAes.getId().getIntPersEmpresaPrevision());
					beneficiarioAESId.setIntItemBeneficiario(fallecidosAes.getId().getIntItemExpediente());
					beneficiarioAESId.setIntItemExpediente(fallecidosAes.getId().getIntItemFallecido());
					beneficiarioAES.setId(beneficiarioAESId);
					beneficiarioAES.setIntItemViculo(fallecidosAes.getIntItemViculo());
					beneficiarioAES.setIntPersPersonaBeneficiario(fallecidosAes.getIntPersPersonaFallecido());	
					beneficiarioAES.setIntParaEstado(fallecidosAes.getIntParaEstado());
					lstBeneficiariosAES.add(beneficiarioAES);
				}	
			}
		} catch (Exception e) {
			log.error("Error en regenerarBeneficiariosXPersonaMotivoAES ---> "+e);
		}
		return lstBeneficiariosAES;
	}
	
	
	/**
	 * 
	 * @param event
	 * @throws ParseException
	 */
	public void modificarSolicitudPrevision(ActionEvent event) throws ParseException {

		try{
			setearMontoNetoAES();
			if (isValidoExpedientePrevision(beanExpedientePrevision) == false) {
				log.info("Datos de Previsión no válidos. Se aborta el proceso de grabación de Solicitud de Previsión.");
				return;
			}
			if (listaRequisitoPrevisionComp != null && listaRequisitoPrevisionComp.size() > 0) {
				beanExpedientePrevision.setListaRequisitoPrevisionComp(listaRequisitoPrevisionComp);
			}
			if (isValidAdjuntosSolicitudCambioEstado() == 0) {
				// Validamos que todos requisitos se cumplan
				EstadoPrevision estadoPrevision = null;
				estadoPrevision = new EstadoPrevision();
				estadoPrevision.setTsFechaEstado(new Timestamp(new Date().getTime()));
				estadoPrevision.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
				estadoPrevision.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
				estadoPrevision.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				estadoPrevision.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
				estadoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
				beanExpedientePrevision.getListaEstadoPrevision().add(estadoPrevision);
			} 
			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				if (beanExpedientePrevision.getListaBeneficiarioPrevision() == null 
				||beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){		
					BeneficiarioPrevision beneficiarioDefault = new BeneficiarioPrevision();
					BeneficiarioPrevisionId beneficiarioDefaultId = new BeneficiarioPrevisionId();
					beneficiarioDefault.setId(beneficiarioDefaultId);
					beneficiarioDefault.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
					beneficiarioDefault.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
					beneficiarioDefault.setIntItemViculo(intTipoRelacion);
					beneficiarioDefault.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
					beneficiarioDefault.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
					List<BeneficiarioPrevision> lstBenefPrev = new ArrayList<BeneficiarioPrevision>();
					lstBenefPrev.add(beneficiarioDefault);
					beanExpedientePrevision.setListaBeneficiarioPrevision(lstBenefPrev);
				}
			} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
						if(intSubTipoSolicitud.intValue() == Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR){
							FallecidoPrevision fallecidoDefault = new FallecidoPrevision();
							FallecidoPrevisionId fallecidoDefaultId = new FallecidoPrevisionId();
							fallecidoDefault.setId(fallecidoDefaultId);
							fallecidoDefault.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
							fallecidoDefault.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
							fallecidoDefault.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
							fallecidoDefault.setIntPersPersonaFallecido(beanSocioComp.getPersona().getIntIdPersona());
							List<FallecidoPrevision> lstFallecPrev = new ArrayList<FallecidoPrevision>();
							lstFallecPrev.add(fallecidoDefault);
							beanExpedientePrevision.setListaFallecidoPrevision(lstFallecPrev);
						}

			}else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
						if(intSubTipoSolicitud.intValue() == Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR){
							FallecidoPrevision fallecidoDefault = new FallecidoPrevision();
							FallecidoPrevisionId fallecidoDefaultId = new FallecidoPrevisionId();
							fallecidoDefault.setId(fallecidoDefaultId);
							fallecidoDefault.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
							fallecidoDefault.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
							fallecidoDefault.setIntParaEstado(beanSocioComp.getPersona().getIntEstadoCod());
							fallecidoDefault.setIntPersPersonaFallecido(beanSocioComp.getPersona().getIntIdPersona());
							List<FallecidoPrevision> lstFallecPrev = new ArrayList<FallecidoPrevision>();
							beanExpedientePrevision.setListaFallecidoPrevision(lstFallecPrev);
						}
			}

			setearMontoNetoAES();
			// parche 09.07.2013 - beneficiarios x fallecidos
			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				if(beanExpedientePrevision.getListaBeneficiarioPrevision()!= null
					&& !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
					List<FallecidoPrevision> listaFallecidosAES = null;
					listaFallecidosAES = generarPersonaMotivoParaAES(beanExpedientePrevision.getListaBeneficiarioPrevision());
					if(listaFallecidosAES != null & !listaFallecidosAES.isEmpty()){
						beanExpedientePrevision.setListaFallecidoPrevision(listaFallecidosAES);
						beanExpedientePrevision.getListaBeneficiarioPrevision().clear();	
					}
				}	
			}else{
				modificarTodosLosBeneficiarios(event);
			}
				
			beanExpedientePrevision = previsionFacade.modificarExpedientePrevision(beanExpedientePrevision);
			
			//25.06.2013 - cgd
			/*if (beanExpedientePrevision.getListaRequisitoPrevisionComp() != null
				&& beanExpedientePrevision.getListaRequisitoPrevisionComp().size() > 0) {
					renombrarArchivo(beanExpedientePrevision.getListaRequisitoPrevisionComp());
			}*/
			
			//validdamos que se haya generado itemexpediente...
			if((beanExpedientePrevision.getId().getIntItemExpediente() != null)){
				cancelarGrabarSolicitud(event);
			}
		} 
		catch (Exception e) {
			log.error("Error en grabarSolicitud --> "+e);
		}
	}
	
		
	/**
	 * Asociado al boton CANCELAR
	 * @param event
	 */
	public void cancelarGrabarSolicitud(ActionEvent event) {
		listaBeneficiarioPrevisionBusq = null;
		listaFallecidosPrevisionBusq = null;
		try {
			limpiarFormSolicitudPrevision();
			limpiarMensajesIsValidoExpediente();
			blnShowValidarDatos = false;
			dtFechaRegistro = Calendar.getInstance().getTime();
			blnShowDivFormSolicitudPrevision = false;
			listaBeneficiarioPrevisionBusq = new ArrayList<BeneficiarioPrevision>();
			listaFallecidosPrevisionBusq = new ArrayList<FallecidoPrevision>();
			blnVerRegistroSolExpPrevision = false;
		} catch (Exception e) {
			log.error("Error en cancelarGrabarSolicitud ---> "+e);
		}
			
	}
	
	
	/**
	 * Limpia las variable iniciales y deja listoe l formulario para una nueva operacion.
	 */
	public void limpiarFormSolicitudPrevision() {
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

		beanCaptacion= new Captacion();
		beanCaptacion.setId(new CaptacionId());
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());

		beanExpedientePrevision = new ExpedientePrevision();
		beanExpedientePrevision.setId(new ExpedientePrevisionId());
		beanExpedientePrevision.setListaEstadoPrevision(new ArrayList<EstadoPrevision>());
		beanExpedientePrevision.setListaBeneficiarioPrevision(new ArrayList<BeneficiarioPrevision>());
		beanExpedientePrevision.setListaFallecidoPrevision(new ArrayList<FallecidoPrevision>());
		beanExpedientePrevision.setListaRequisitoPrevisionComp(new ArrayList<RequisitoPrevisionComp>());
		listaRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
		bdMontoSolicitado = null;
		dtFechaRegistro = null;
		dtFechaFallecimiento= null;
		dtFechaSustento = null;
		dtFechaRegistro = Calendar.getInstance().getTime();

		blnCondicionCuotaSepelio = false;
		blnCondicionDebeTenerDeuda = false;
		blnCondicionNODebeTenerDeuda = false;
		blnCondicionPeridoMeses = false;
		blnValidacionCaptacionRecuperada = false;
		blnExisteBeneficiario = true;
		
		bdMontoTotalAportadoSepelio = BigDecimal.ZERO;
		intSolicitudRegularidad = null;
		intSolicitudPeriodicidad = null;
		
		blnIsSepelio = false;
		blnIsAES = false;
		blnIsRetiro = false;
		
		intNroCuotasDefinidasSepelio = null;
		intTiempoAportacion= null;
		intTiempoPresentacion= null;
		
		blnDeshabilitar = false;
		blnPostEvaluacion = false;
		intSubTipoSolicitud   = 0;
		intTipoSolicitud 	  = 0;
		blnBeneficiarioSepelio = false;
		blnBeneficiarioNormal = false;
		
		campoBuscarFallecido="";
		campoBuscarBeneficiario="";
		
		blnTieneAdjuntosConfigurados = false;
		blnMostrarDescripcionTipoPrevision = false;

		strFechaRegistro = Constante.sdf.format(new Date());
		
		estadoPrevisionFiltro = new EstadoPrevision();
		estadoPrevisionFiltro.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
		blnBenefTieneRelFam = Boolean.FALSE;
		blnIsSepelioTitular = false;
		
		blnVerRegistroSolExpPrevision = false;
	}
	
	
	/**
	 * Valida al socio que se quiere dar el beneficio.
	 * ASociado al boton VALIDAR DATOS
	 * @param event
	 */
	public void validarDatos(ActionEvent event) {
		SocioComp socioComp = null;
		Integer intTipoDoc = 0;
		String strNumIdentidad = "";
		strMsgErrorValidarDatos="";
		Boolean blnTipoRelacion = false;
		
		try {
			
			intTipoDoc = personaBusqueda.getDocumento().getIntTipoIdentidadCod();
			strNumIdentidad = personaBusqueda.getDocumento().getStrNumeroIdentidad();
			strNumIdentidad = strNumIdentidad.trim();
			if ((intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_SOCIO)
			|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_USUARIO)
			|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_CLIENTE)
			|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_NO_SOCIO))){

				socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(intTipoDoc, strNumIdentidad, Constante.PARAM_EMPRESASESION);

				// solo situacion cta activa
				if (socioComp != null) {
					if (socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()!=null && !socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol().isEmpty()) {
						for (PersonaRol x : socioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()) {
							if (x.getId().getIntParaRolPk().equals(intTipoRelacion)) {
								blnTipoRelacion = true;
								break;
							}								
						}
						if (blnTipoRelacion) {
							if (!(intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_NO_SOCIO))) {
								//28.08.2013 - CGD
								//if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								if (socioComp.getCuenta() != null) {
									if(socioComp.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
										//if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){

										strMsgErrorValidarDatos = "";

										for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
											if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
												socioComp.getSocio().setSocioEstructura(socioEstructura);

												//if(!(socioComp.getCuenta().getIntParaSubTipoCuentaCod().intValue() 
												//== Constante.PARAM_SUBCONDICION_CUENTASOCIO_REGULAR.intValue())){
													pgValidDatos = false;
													blnDatosSocio = true;
													beanSocioComp = socioComp;
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

													blnShowDivFormSolicitudPrevision = true;
													blnShowValidarDatos = false;
													// cargando sucursal y subsucursal del socio
													cargarListaTablaSucursal();
													seleccionarSucursal();
													strMsgErrorValidarDatos="";
													strSolicitudPrevision = Constante.MANTENIMIENTO_MODIFICAR;
													recuperarCuentasConceptoSocio(beanSocioComp);
											}
										}
										cargarDescripcionUEjecutorasConcatenadas(socioComp);
									}else{
										strMsgErrorValidarDatos = "La Situación de la Cuenta del Socio NO ES ACTIVA. ";
									}
								}else{
									strMsgErrorValidarDatos = "El Socio no posee Cuenta.";
								}
							}else{
								strMsgErrorValidarDatos = "La Relación NO SOCIO no cuenta con configuración de crédito.";
							}							
						}else{
							strMsgErrorValidarDatos = "La Relación no concuerda con la configuración de la cuenta del socio.";
						}
					}
				}
			} else {
				pgValidDatos = true;
				blnDatosSocio = false;
				strMsgErrorValidarDatos = "Rol de Socio incorrecto. ";
			}
		} catch (BusinessException e) {
			log.error("error: " + e);
		} catch (Exception e1) {
			log.error(e1);
		}

	}


	/**
	 * Carga la variable global: listaCuentaConcepto.
	 * @param socioComp
	 */
	public void recuperarCuentasConceptoSocio (SocioComp socioComp){
		
		try {
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());

		} catch (Exception e) {
			log.error("Error en recuperarCuentasConceptoSocio ---> "+e);
		}
	}
	
	
	
	/**
	 * Recupera la cta concepto segun intTipoCuentaConcepto
	 */
	public CuentaConcepto recuperarCuentaConceptoBase(Integer intTipoCuentaConcepto){
		CuentaConcepto cuentaConceptoResult = null;
		try {
			if(intTipoCuentaConcepto != null){
				if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){	
					for (CuentaConcepto ctaCpto : listaCuentaConcepto) {
						CuentaConceptoDetalle detalle = ctaCpto.getListaCuentaConceptoDetalle().get(0);
						if(detalle.getIntParaTipoConceptoCod().compareTo(intTipoCuentaConcepto)==0){
							cuentaConceptoResult = new CuentaConcepto();
							cuentaConceptoResult = ctaCpto;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en generarCuentasConceptoBase ---> "+e);
		}
		return cuentaConceptoResult; 
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
	 * 
	 */
	public void loadListDocumentoBusq(ActionEvent event) {
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
		setListDocumentoBusq(listaDocumento);
	}
	

	/**
	 * 
	 * @return
	 */
	public void cargarDescripcionTipoPrevision(){
		strDescripcionTipoPrevision= "";
		//blnMostrarDescripcionTipoPrevision = Boolean.TRUE;
		try {
			
			if(beanExpedientePrevision.getIntParaSubTipoOperacion() != null 
				&& beanExpedientePrevision.getIntParaSubTipoOperacion().compareTo(0)!=0
				&& intTipoSolicitud != null
				&& intTipoSolicitud.compareTo(0)!= 0){
				// cargamos la lista de suboperacines en base al tipod e prevision seleccionada

				/*//
				public static final Integer CAPTACION_APORTACIONES = 1;
				public static final Integer CAPTACION_FDO_SEPELIO = 2;
				public static final Integer CAPTACION_FDO_RETIRO = 3;
				public static final Integer CAPTACION_AHORROS = 4;
				public static final Integer CAPTACION_DEPOSITO = 5;
				public static final Integer CAPTACION_CUENTACORRIENTE = 6;
				public static final Integer CAPTACION_CREDITO = 7;
				public static final Integer CAPTACION_INDEMNIZACIONPORSEPELIO = 8;
				public static final Integer CAPTACION_AUXILIOFUNERARIO = 9;
				public static final Integer CAPTACION_MANT_CUENTA = 10;
				public static final Integer CAPTACION_AES = 11;
				//*/
				
					if(intTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))){
						listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));

					} else if(intTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))){
								//listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES)); 
						listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));  
								
					} else if(intTipoSolicitud.equals(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)){
								listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));  	
					} 

					for (Tabla tablaDesc : listaTipoSolicitud) {
						if(intTipoSolicitud.compareTo(tablaDesc.getIntIdDetalle())==0){
							strDescripcionPrevision = tablaDesc.getStrDescripcion();
							blnMostrarDescripcionTipoPrevision = Boolean.TRUE;
							break;
						}
					}

					for (Tabla tablaDesc : listaSubTipoSolicitud) {
						if(beanExpedientePrevision.getIntParaSubTipoOperacion().compareTo(tablaDesc.getIntIdDetalle())==0){
							strDescripcionTipoPrevision = tablaDesc.getStrDescripcion();
							blnMostrarDescripcionTipoPrevision = Boolean.TRUE;
							break;
						}
					}
			}
		} catch (Exception e) {
			log.error("Error en cargarDescripcionTipoLiquidacion ---> "+e);
		}
	}
	

	/**
	 * Recupera u muestra los docuemntos adjuntos configurados
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
		listaRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
		RequisitoPrevisionComp requisitoPrevisionComp;
		//Integer intTipoOperacion = 0;
		Integer intReqDesc = new Integer(0);
		Integer intTIpoOperacion=new Integer(0);
		try {
//			dtToday = Constante.sdf.parse(strToday);

			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			confServSolicitud = new ConfServSolicitud();

			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				intTIpoOperacion = Constante.PARAM_T_TIPOOPERACION_AES;
				intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_AES);	
			}else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
				intTIpoOperacion = Constante.PARAM_T_TIPOOPERACION_FONDORETIRO;
				intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO);	
			}else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
				intTIpoOperacion = Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO;
				intReqDesc = new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO);	
			}
			
			confServSolicitud.setIntParaTipoOperacionCod(intTIpoOperacion);
			confServSolicitud.setIntParaSubtipoOperacionCod(intSubTipoSolicitud);
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);

			listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, Constante.PARAM_T_TIPOREQAUT_REQUISITO, null);
			
			if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
				forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {
					if(solicitud.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						if (solicitud.getIntParaTipoOperacionCod().compareTo(intTIpoOperacion)==0) {
							if (solicitud.getIntParaSubtipoOperacionCod().equals(intSubTipoSolicitud)) {
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
												if(estructDetalle.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
													if (estructuraDetalle.getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())
														&& estructuraDetalle.getIntNivelPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())
														&& estructuraDetalle.getIntCaso().equals(estructDetalle.getId().getIntCaso())
														&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())) {
														
															if (solicitud.getListaDetalle() != null && solicitud.getListaDetalle().size() > 0) {
																List<RequisitoPrevisionComp> listaRequisitoPrevisionCompTemp = new ArrayList<RequisitoPrevisionComp>();
																for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
																	if(detalle.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
																		if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId().getIntPersEmpresaPk())
																				&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalle.getId().getIntItemSolicitud())) {
																				
																				requisitoPrevisionComp = new RequisitoPrevisionComp();
																				requisitoPrevisionComp.setDetalle(detalle);
																				listaRequisitoPrevisionCompTemp.add(requisitoPrevisionComp);
																			}
																	}
																}
																													
																List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();

																	// validamos que solo se muestre las de agrupamioento A.
																	listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(intReqDesc,"A");
																		for(int i=0;i<listaTablaRequisitos.size();i++){	
																			for(int j=0 ; j<listaRequisitoPrevisionCompTemp.size();j++){
																 				if((listaRequisitoPrevisionCompTemp.get(j).getDetalle().getIntParaTipoDescripcion().intValue()) ==
																					(listaTablaRequisitos.get(i).getIntIdDetalle().intValue())){
																					listaRequisitoPrevisionComp.add(listaRequisitoPrevisionCompTemp.get(j));
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
			}

		} catch (BusinessException e) {
			log.error("MOSTRAR ADJUNTOS - BusinessException ---> "+e);
		} catch (EJBFactoryException e1) {
			log.error("MOSTRAR ADJUNTOS - EJBFactoryException ---> "+e1);
		}
//		catch (ParseException e2) {
//			log.error("MOSTRAR ADJUNTOS - ParseException ---> "+e2);
//		}
	}
	
	
	/**
	 * 
	 * @param lista
	 * @throws BusinessException
	 */
	public void renombrarArchivo(List<RequisitoPrevisionComp> lista) throws BusinessException {
		TipoArchivo tipoArchivo = null;
		Archivo archivo = null;
		
		try {
			for (RequisitoPrevisionComp requisitoPrevisionComp : lista) {
				if (requisitoPrevisionComp.getArchivoAdjunto() != null) {
				// -------------------   A E S --------------------------->
					
					// 1. PREVISION_AES_SOLICITUD_BENEFICIO
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_SOLICITUD_BENEFICIO)
							//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 2. PREVISION_AES_BOLETAPAGOHABERES_CAS
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_BOLETAPAGOHABERES_CAS)
							//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 3. PREVISION_AES_RECETA_MEDICA
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_RECETA_MEDICA)
							//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 4. PREVISION_AES_EXAMENES_CLINICOS
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_EXAMENES_CLINICOS)
							//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 5. PREVISION_AES_CONSTANCIA_HOSPITALIZACION
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_CONSTANCIA_HOSPITALIZACION)
							//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 6. PREVISION_AES_INFORME_ADMINISTRADOR
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_INFORME_ADMINISTRADOR)
							//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 7. PREVISION_AES_INFORME_JEFATURA
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_INFORME_JEFATURA)
							//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					
				// -------------------   S E P E L I O --------------------------->		
					
					// 1. PREVISION_SEPELIO_SOLICITUD_BENEFICIO
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SOLICITUD_BENEFICIO)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 2. PREVISION_SEPELIO_INFORME_ADMINISTRADOR
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_INFORME_ADMINISTRADOR)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 3. PREVISION_SEPELIO_COPIA_DNI_TITULAR
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_COPIA_DNI_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 4. PREVISION_SEPELIO_COPIA_DNI_BENEFICIARIO
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_COPIA_DNI_BENEFICIARIO)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 5. PREVISION_SEPELIO_SUCESION_INTESTADA
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SUCESION_INTESTADA)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 6. PREVISION_SEPELIO_DECLARATORIA_HEREDEROS
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_DECLARATORIA_HEREDEROS)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 7. PREVISION_SEPELIO_PARTDIDA_DEFUNCION_ORIFGINAL
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_DEFUNCION_ORIFGINAL)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 8. PREVISION_SEPELIO_PARTDIDA_NACIMIENTO_ORIFGINAL
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_NACIMIENTO_ORIFGINAL)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 9. PREVISION_SEPELIO_PARTDIDA_MATRIMONIO_ORIFGINAL
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_MATRIMONIO_ORIFGINAL)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 10. PREVISION_SEPELIO_SUSTENTO_GASTOS_SEPELIO
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SUSTENTO_GASTOS_SEPELIO)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 11. PREVISION_SEPELIO_FOTO_LUGAR_SEPULTURA
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_FOTO_LUGAR_SEPULTURA)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
				
				// -------------------   R E T I R O --------------------------->		
					
					// 1. PREVISION_RETIRO_BOLETA_DE_PAGO
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_BOLETA_DE_PAGO)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 2. PREVISION_RETIRO_SOLICITUD_BENEFICIO
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_SOLICITUD_BENEFICIO)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 3. PREVISION_RETIRO_COPIA_DNI_SOCIO
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_COPIA_DNI_SOCIO)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 4. PREVISION_RETIRO_INFORME_ADMINISTRADOR
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_INFORME_ADMINISTRADOR)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 5. PREVISION_RETIRO_COPIA_DNI_BENEFICIARIO
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_COPIA_DNI_BENEFICIARIO)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 6. PREVISION_RETIRO_CARTA_DECLARATORIA_BENEFICIARIO_SUCESION_INTESTADA
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_CARTA_DECLARATORIA_BENEFICIARIO_SUCESION_INTESTADA)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 7. PREVISION_RETIRO_ACTA_PAGO_BENEFICIARIOS
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_ACTA_PAGO_BENEFICIARIOS)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 8. PREVISION_RETIRO_PARTIDA_DEFUNCION_ORIGINAL_LEGALIZADA
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_DEFUNCION_ORIGINAL_LEGALIZADA)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 9. PREVISION_RETIRO_PARTIDA_NACIMIENTO_ORIGINAL_LEGALIZADA
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_NACIMIENTO_ORIGINAL_LEGALIZADA)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
					
					// 10. PREVISION_RETIRO_PARTIDA_MATRIMONIO_ORIGINAL_LEGALIZADA
					if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_MATRIMONIO_ORIGINAL_LEGALIZADA)
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
							&& requisitoPrevisionComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoPrevisionComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoPrevisionComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						if(tipoArchivo != null){
							archivo = new Archivo();
							archivo.setId(new ArchivoId());
							
							String ruta = tipoArchivo.getStrRuta()+ "\\"+ requisitoPrevisionComp.getArchivoAdjunto().getStrNombrearchivo();
							archivo.getId().setIntParaTipoCod(requisitoPrevisionComp.getRequisitoPrevision().getIntParaTipoArchivo());
							archivo.getId().setIntItemArchivo(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo());
							archivo.getId().setIntItemHistorico(requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico());
							archivo = generalFacade.getArchivoPorPK(archivo.getId());
							FileUtil.renombrarArchivo(ruta,tipoArchivo.getStrRuta() + "\\"+ archivo.getStrNombrearchivo());
						}
					}
				}
				 
			}
		} catch (BusinessException e) {
			log.error("Error BusinessException en renombrarArchivo ---> "+e);
			throw e;
		}
	}
	
	
	/**
	 * Al seleccionar el combo de Tipo de Prevision, carga parametros de renderer y carga lista de subtipo de prevision.
	 * @param event
	 */
	public void loadSubTipoSolicitud(ActionEvent event) {
		//log.info("-------------------------------------Debugging solicitudPrevisionController.loadListDocumento-------------------------------------");
		//TablaFacadeRemote facade = null;
		String strIdTipoSolicitud = null;
		Integer intIdTipoSolicitud = null;
		//List<Tabla> listaDocumento = null;
		//Integer pIntIdMaestro = null;
		intSubTipoSolicitud = 0;
		//limpiarMensajesIsValidoExpediente();
		try {
			strIdTipoSolicitud = getRequestParameter("pIntTipoSolicitud");
			intIdTipoSolicitud = new Integer(strIdTipoSolicitud);
			
			intTipoSolicitud = intIdTipoSolicitud;
			
			limpiarBeneficiarios(event);
			limpiarFallecidos(event);
			blnCheckSinRelacion = false;
			if(!intIdTipoSolicitud.equals("0")){

				/* //
				public static final Integer CAPTACION_APORTACIONES = 1;
				public static final Integer CAPTACION_FDO_SEPELIO = 2;
				public static final Integer CAPTACION_FDO_RETIRO = 3;
				public static final Integer CAPTACION_AHORROS = 4;
				public static final Integer CAPTACION_DEPOSITO = 5;
				public static final Integer CAPTACION_CUENTACORRIENTE = 6;
				public static final Integer CAPTACION_CREDITO = 7;
				public static final Integer CAPTACION_INDEMNIZACIONPORSEPELIO = 8;
				public static final Integer CAPTACION_AUXILIOFUNERARIO = 9;
				public static final Integer CAPTACION_MANT_CUENTA = 10;
				public static final Integer CAPTACION_AES = 11;
				// */
				if(intIdTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))){
					blnIsAES = false;
					blnIsSepelio = true;
					blnIsRetiro = false;
					blnBeneficiarioNormal = false;
					blnBeneficiarioSepelio = true;
					blnBeneficiarioRetiro = false;

					listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));

				} else if(intIdTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))){
							blnIsAES = true;
							blnIsSepelio = false;
							blnIsRetiro = false;
							blnBeneficiarioNormal = true;
							blnBeneficiarioSepelio = false;
							blnBeneficiarioRetiro = false;
							
							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));  
							
				} else if(intIdTipoSolicitud.equals(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)){
							blnIsAES = false;
							blnIsSepelio = false;
							blnIsRetiro = true;
							blnBeneficiarioNormal = false;
							blnBeneficiarioSepelio = false;
							blnBeneficiarioRetiro = true;

							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));  	
				} 
				
				//Ordenamos los subtipos por int
				Collections.sort(listaSubTipoSolicitud, new Comparator<Tabla>(){
					public int compare(Tabla uno, Tabla otro) {
						return uno.getIntOrden().compareTo(otro.getIntOrden());
					}
				});
				
				for(int k=0; k<listaSubTipoSolicitud.size();k++){
					System.out.println("LIST SUBTIPO ---> "+listaSubTipoSolicitud.get(k).getIntOrden());	
				}

			}
			
		} catch (NumberFormatException e1) {
			log.error("Error loadSubTipoSolicitud 1-->"+e1);
		} catch (BusinessException e2) {
			log.error("Error loadSubTipoSolicitud 2-->"+e2);} 


	} 
	
	/**
	 * 
	 * @param event
	 */
	public void loadSubTipoSolicitudMod(ActionEvent event) {
		//log.info("-------------------------------------Debugging solicitudPrevisionController.loadListDocumento-------------------------------------");
		intSubTipoSolicitud = 0;
		try {

				if(beanExpedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
					blnIsAES = false;
					blnIsSepelio = true;
					blnIsRetiro = false;
					blnBeneficiarioNormal = false;
					blnBeneficiarioSepelio = true;
					blnBeneficiarioRetiro = false;

					listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));

				} else if(beanExpedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)){
							blnIsAES = true;
							blnIsSepelio = false;
							blnIsRetiro = false;
							blnBeneficiarioNormal = true;
							blnBeneficiarioSepelio = false;
							blnBeneficiarioRetiro = false;
							
							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));  
							
				} else if(beanExpedientePrevision.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
							blnIsAES = false;
							blnIsSepelio = false;
							blnIsRetiro = true;
							blnBeneficiarioNormal = false;
							blnBeneficiarioSepelio = false;
							blnBeneficiarioRetiro = true;

							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));  	
		} 
				
				//Ordenamos los subtipos por int
				Collections.sort(listaSubTipoSolicitud, new Comparator<Tabla>(){
					public int compare(Tabla uno, Tabla otro) {
						return uno.getIntOrden().compareTo(otro.getIntOrden());
					}
				});
				
				for(int k=0; k<listaSubTipoSolicitud.size();k++){
					System.out.println("LIST SUBTIPO ---> "+listaSubTipoSolicitud.get(k).getIntOrden());	
				}

			
		} catch (NumberFormatException e1) {
			log.error("Error loadSubTipoSolicitud 1-->"+e1);
		} catch (BusinessException e2) {
			log.error("Error loadSubTipoSolicitud 2-->"+e2);} 

	} 
	
	/**
	 * 
	 * @param event
	 */
	public void loadSubTipoSolicitudBusqueda(ActionEvent event) {
		//log.info("-------------------------------------Debugging solicitudPrevisionController.loadListDocumento-------------------------------------");
		String strIdTipoSolicitudBusq = null;
		Integer intIdTipoSolicitudBusq = null;
		
		try {
			strIdTipoSolicitudBusq = getRequestParameter("pIntTipoSolicitudBusqueda");
			intIdTipoSolicitudBusq = new Integer(strIdTipoSolicitudBusq);
			System.out.println("TIPO DE SOLICITUD busqueda ---> "+strIdTipoSolicitudBusq);

			if(!intIdTipoSolicitudBusq.equals("0")){

				if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
						listaSubTipoSolicitudBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));

				} else if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)){
					listaSubTipoSolicitudBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));  
							
				} else if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
					listaSubTipoSolicitudBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO)); 
				}
				
				//Ordenamos los subtipos por int
				Collections.sort(listaSubTipoSolicitudBusqueda, new Comparator<Tabla>(){
					public int compare(Tabla uno, Tabla otro) {
						return uno.getIntOrden().compareTo(otro.getIntOrden());
					}
				});
			}
			
		} catch (NumberFormatException e1) {
			log.error("Error loadSubTipoSolicitudBusqueda 1-->"+e1);
		} catch (BusinessException e2) {
			log.error("Error loadSubTipoSolicitudBusqueda 2-->"+e2);} 


	} 
	
	/**
	 * 
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
	 * @return
	 */
	public List<Subsucursal> getListSubSucursal() {
		try {
			if (listSubSucursal == null) {
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listSubSucursal = facade.getListaSubSucursalPorIdSucursal(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				// beanSocioComp.getSocio().getSocioEstructura().getIntIdSubsucurAdministra()
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listSubSucursal;
	}
	
	
	/**
	 * Craga lista de subcursales de acuerdo a sucursal seleccionada.
	 */
	public void cargarListaSubSucursal() {
		try {
			EmpresaFacadeRemote facade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			listaSubSucursaBusqueda = new ArrayList<Subsucursal>();
			listaSubSucursaBusqueda = facade.getListaSubSucursalPorIdSucursal(intBusqSucursal);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 
	 */
	public void addBeneficiario(ActionEvent event){
		//log.info("-------------------------------------Debugging buscarBeneficiario-------------------------------------");
		listaBeneficiarioPrevisionBusq.clear();
		listaBeneficiarioPrevisionBusq = new ArrayList<BeneficiarioPrevision>();
		listarBeneficiariosDeSocio(beanSocioComp);
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void addFallecido(ActionEvent event){
		//log.info("-------------------------------------Debugging buscarBeneficiario-------------------------------------");
		listaFallecidosPrevisionBusq.clear();
		listaFallecidosPrevisionBusq = new ArrayList<FallecidoPrevision>();
		listarFallecidosDeSocio(beanSocioComp);
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void buscarFallecido(ActionEvent event){
		//log.info("-------------------------------------Debugging buscarFallecido-------------------------------------");
		listaFallecidosPrevisionBusq.clear();
		listaFallecidosPrevisionBusq = new ArrayList<FallecidoPrevision>();
		listarFallecidosDeSocio(beanSocioComp);
	}
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void buscarBeneficiario(ActionEvent event){
		//log.info("-------------------------------------Debugging buscarBeneficiario-------------------------------------");
		listaBeneficiarioPrevisionBusq.clear();
		listaBeneficiarioPrevisionBusq = new ArrayList<BeneficiarioPrevision>();
		listarBeneficiariosDeSocio(beanSocioComp);
	}
	
	
	/**
	 * 
	 */
	public void buscarFallecidoFiltro(){
		//log.info("-------------------------------------Debugging buscarFallecido-------------------------------------");
		listaFallecidosPrevisionBusq.clear();
		listaFallecidosPrevisionBusq = new ArrayList<FallecidoPrevision>();
		listarFallecidosDeSocioFiltro(beanSocioComp);
	}
	
	
	/**
	 * 
	 */
	public void buscarBeneficiarioFiltro(){
		//log.info("-------------------------------------Debugging buscarBeneficiario-------------------------------------");
		listaBeneficiarioPrevisionBusq.clear();
		listaBeneficiarioPrevisionBusq = new ArrayList<BeneficiarioPrevision>();
		listarBeneficiariosDeSocioFiltro(beanSocioComp);
	}
	
	
	/**
	 * Carga la lista de
	 * @param socioComp
	 */
	public void listarBeneficiariosDeSocioFiltro(SocioComp socioComp){
		log.info("-------------------------------------Debugging listarBeneficiariosDeSocio-------------------------------------");
		//PersonaEmpresaPK personaEmpresaPk = null;
		PersonaFacadeRemote personaFacade = null;
//		VinculoFacadeRemote vinculoFacade = null;
		List<Vinculo> vinculos = null;
		try {
			
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			if(socioComp != null){
				//----------------------------------------->
				if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
					if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
						
						Integer intIdPersona = socioComp.getSocio().getSocioEstructura().getId().getIntIdPersona();
						Integer intIdEmpresa = socioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa();
						PersonaEmpresaPK personaPk = new PersonaEmpresaPK();
						personaPk.setIntIdEmpresa(intIdEmpresa);
						personaPk.setIntIdPersona(intIdPersona);
						
						vinculos = personaFacade.getVinculoPorPk(personaPk);
						
						if(vinculos != null && !vinculos.isEmpty() ){
							
							for(int k=0; k<vinculos.size();k++){
								Boolean blnContinuaProceso = Boolean.FALSE; 
								if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
									blnContinuaProceso = Boolean.TRUE; 
								}else{
									blnContinuaProceso = validarBeneficiarioSegunTipoPrevision(vinculos.get(k));
								}
								// cgd - 14.08.2013
								// validamos que el vinculo exista para el tipo de prevision seleccionado
								if(blnContinuaProceso){
								
								// cgd - 14.08.2013
								// validamos que el vinculo este configurado para el tipode prevision seleccionado

									Integer intIdPersonaVinc = vinculos.get(k).getIntPersonaVinculo();
									BeneficiarioPrevision beneficiarioPrevision = new BeneficiarioPrevision();
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
													beneficiarioPrevision.setPersona(persona);
													beneficiarioPrevision.setIntItemViculo(vinculos.get(k).getIntItemVinculo());
													beneficiarioPrevision.setIntTipoViculo(vinculos.get(k).getIntTipoVinculoCod());
													//beneficiarioPrevision.setIntItemViculo(vinculos.get(k).getIntTipoVinculoCod());
													//beneficiarioPrevision.setIntPersPersonaBeneficiario(persona.getIntIdPersona());
													beneficiarioPrevision.setIntPersPersonaBeneficiario(vinculos.get(k).getIntPersonaVinculo());
													listaBeneficiarioPrevisionBusq.add(beneficiarioPrevision);	
											}
										}
									}
								}
							}
						}
					} else {
						if(beanSocioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							BeneficiarioPrevision beneficiarioPrevision = new BeneficiarioPrevision();
							beneficiarioPrevision.setPersona(beanSocioComp.getPersona());
							beneficiarioPrevision.setIntItemViculo(null);
							beneficiarioPrevision.setIntTipoViculo(null);
							beneficiarioPrevision.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
							listaBeneficiarioPrevisionBusq.add(beneficiarioPrevision);	
						}
						
					}
				}

				System.out.println("TAMAÑO DE BENEFICIARIOS ---> "+ listaBeneficiarioPrevisionBusq.size());
			}

			
		} catch (EJBFactoryException e) {
			log.info("Err--> "+e);
			
		} catch (BusinessException e1) {
			log.info("Err--> "+e1);
		}
	}
	
	
	/**
	 * Carga los beneficiarios en la grilla de busqueda, segun el tipo de prevision.
	 * Si es Fallecimiento - Titular solo se deberia cargar con los beneficiarios del tipo OTROS.
	 * 
	 * @param socioComp
	 */
		public void listarBeneficiariosDeSocio(SocioComp socioComp){
		log.info("-------------------------------------Debugging listarBeneficiariosDeSocio-------------------------------------");
		PersonaFacadeRemote personaFacade = null;
		//VinculoFacadeRemote vinculoFacade = null;
		List<Vinculo> vinculos = null;
		try {
			if(socioComp != null){
				// si es aes o sepelio-titular o retiro titular, se cargan los beneficiarios...
				if((intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0)
						
					||(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
						&& intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0)

					||(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0
						&& intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_FALLECIMIENTO_TITULAR)==0)){
				
					personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
					Integer intIdPersona = socioComp.getSocio().getSocioEstructura().getId().getIntIdPersona();
					Integer intIdEmpresa = socioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa();
					PersonaEmpresaPK personaPk = new PersonaEmpresaPK();
					personaPk.setIntIdEmpresa(intIdEmpresa);
					personaPk.setIntIdPersona(intIdPersona);
					
					vinculos = personaFacade.getVinculoPorPk(personaPk);
					
					if(!vinculos.isEmpty()  || vinculos != null ){
						
						for (Vinculo vinculo : vinculos) {
							Boolean blnContinuaProceso = Boolean.FALSE; 
							if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
								blnContinuaProceso = Boolean.TRUE; 
							}else{
								blnContinuaProceso = validarBeneficiarioSegunTipoPrevision(vinculo);
							}
							// cgd - 14.08.2013
							// validamos que el vinculo exista para el tipo de prevision seleccionado
							if(blnContinuaProceso){
								if(blnBenefTieneRelFam == true){
									if(vinculo.getIntTipoVinculoCod().compareTo(Constante.PARAM_RELACION_OTROS)==0){
										Integer intIdPersonaVinc = vinculo.getIntPersonaVinculo();
										BeneficiarioPrevision beneficiarioPrevision = new BeneficiarioPrevision();
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
												beneficiarioPrevision.setPersona(persona);
												beneficiarioPrevision.setIntItemViculo(vinculo.getIntItemVinculo());
												beneficiarioPrevision.setIntTipoViculo(vinculo.getIntTipoVinculoCod());
												//beneficiarioPrevision.setIntItemViculo(vinculos.get(k).getIntTipoVinculoCod());
												//beneficiarioPrevision.setIntPersPersonaBeneficiario(persona.getIntIdPersona());
												beneficiarioPrevision.setIntPersPersonaBeneficiario(vinculo.getIntPersonaVinculo());
												listaBeneficiarioPrevisionBusq.add(beneficiarioPrevision);
											}
											
											
										}	
									}
									
								} else{
									if(vinculo.getIntTipoVinculoCod().compareTo(Constante.PARAM_RELACION_OTROS)!=0){
										Integer intIdPersonaVinc = vinculo.getIntPersonaVinculo();
										BeneficiarioPrevision beneficiarioPrevision = new BeneficiarioPrevision();
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
												beneficiarioPrevision.setPersona(persona);
												beneficiarioPrevision.setIntItemViculo(vinculo.getIntItemVinculo());
												beneficiarioPrevision.setIntTipoViculo(vinculo.getIntTipoVinculoCod());
												//beneficiarioPrevision.setIntItemViculo(vinculos.get(k).getIntTipoVinculoCod());
												//beneficiarioPrevision.setIntPersPersonaBeneficiario(persona.getIntIdPersona());
												beneficiarioPrevision.setIntPersPersonaBeneficiario(vinculo.getIntPersonaVinculo());
												listaBeneficiarioPrevisionBusq.add(beneficiarioPrevision);	
											}
											
										}
									}
								}
							}
						}
					}
				}else{
					// sino se carga al mismo socio
					if(beanSocioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						BeneficiarioPrevision beneficiarioPrevision = new BeneficiarioPrevision();
						beneficiarioPrevision.setPersona(beanSocioComp.getPersona());
						beneficiarioPrevision.setIntItemViculo(null);
						beneficiarioPrevision.setIntTipoViculo(null);
						beneficiarioPrevision.setIntPersPersonaBeneficiario(beanSocioComp.getPersona().getIntIdPersona());
						listaBeneficiarioPrevisionBusq.add(beneficiarioPrevision);	
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
		 * Verifica si el beneficiario x agregar esta configurado para recibir el beneficio de prevision de acuerdo al tipo.
		 * @param beneficiarioPrevision
		 * @return
		 */
		public Boolean validarBeneficiarioSegunTipoPrevision(Vinculo vinculo){
			
			CuentaConcepto cuentaConceptoUtil = null;
			List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficioTemp = null;
//			CuentaDetalleBeneficio beneficio = null;
			Boolean blnProcede = Boolean.FALSE;
			CuentaConceptoDetalle ctaCtoDetalle = null;
			try {
				
				if(intTipoSolicitud.compareTo(new  Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
					cuentaConceptoUtil = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
					
				}else if(intTipoSolicitud.compareTo(new  Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
					cuentaConceptoUtil = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
				}
				
				if(cuentaConceptoUtil != null && cuentaConceptoUtil.getListaCuentaDetalleBeneficio() != null 
					&& !cuentaConceptoUtil.getListaCuentaConceptoDetalle().isEmpty()){
					ctaCtoDetalle = cuentaConceptoUtil.getListaCuentaConceptoDetalle().get(0);
				}

				// if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
					//if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_FALLECIMIENTO_TITULAR)==0){
						// 100% titular
						// recuperamos la lista con los % activos para cada beneficiario
						listaCuentaDetalleBeneficioTemp =  conceptoFacade.getListaCuentaDetalleBeneficioPorPkConcepto(cuentaConceptoUtil.getId());
						if(listaCuentaDetalleBeneficioTemp != null && !listaCuentaDetalleBeneficioTemp.isEmpty()){
							for (CuentaDetalleBeneficio cuentaDetalleBeneficio : listaCuentaDetalleBeneficioTemp) {
								if(cuentaDetalleBeneficio.getIntItemVinculo().compareTo(vinculo.getIntItemVinculo())==0
									&& ctaCtoDetalle != null 
									&& cuentaDetalleBeneficio.getIntParaTipoConceptoCod() != null
									&& cuentaDetalleBeneficio.getIntItemConcepto() != null){
									
									if(ctaCtoDetalle.getIntParaTipoConceptoCod().compareTo(cuentaDetalleBeneficio.getIntParaTipoConceptoCod())==0
										&& ctaCtoDetalle.getIntItemConcepto().compareTo(cuentaDetalleBeneficio.getIntItemConcepto())==0){
											blnProcede = Boolean.TRUE;
											break;
									
									}
									
								}
							}
						}	
					//}
				//}
						
			} catch (Exception e) {
				log.error("Error en validarBeneficiarioSegunTipoPrevision --->"+e);
			}
			
			return blnProcede;
		
		}
		
		
		/**
		 * Carga la lista de los fallecidos segun tipo de sepelio.
		 * Si es Titular se visualizara al propio socio, d elo contrario seran los beneficiarios registrados.
		 * @param socioComp
		 */
		public void listarFallecidosDeSocio(SocioComp socioComp){
			log.info("-------------------------------------Debugging listarBeneficiariosDeSocio-------------------------------------");
			//PersonaEmpresaPK personaEmpresaPk = null;
			PersonaFacadeRemote personaFacade = null;
//			VinculoFacadeRemote vinculoFacade =null;
			List<Vinculo> vinculos =  new ArrayList<Vinculo>();
			List<Vinculo> vinculosFiltrada = new ArrayList<Vinculo>();
			List<FallecidoPrevision> lstFallecidosTemp = null;
			lstFallecidosTemp = new ArrayList<FallecidoPrevision>();
			Integer intValorRelacion = new Integer(0);
			try {

				if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_CONYUGE)==0){
					intValorRelacion = Constante.PARAM_RELACION_CONYUGUE;
				}else if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_PADRES)==0){
					intValorRelacion = Constante.PARAM_RELACION_PADRE;
				}else if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_HIJOS)==0){
					intValorRelacion = Constante.PARAM_RELACION_HIJO;
				}else{
					intValorRelacion = Constante.PARAM_RELACION_OTROS;
				}

				if(socioComp != null){

					// si es aes o sepelio-titular o retiro titular, se cargan los beneficiarios...
					
					if((intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
							&& intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0)
						|| (intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0
							&& intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_FALLECIMIENTO_TITULAR)==0)){

								FallecidoPrevision fallecidoPrevision = new FallecidoPrevision();
								fallecidoPrevision.setPersona(beanSocioComp.getPersona());
								fallecidoPrevision.setIntItemViculo(null);
								fallecidoPrevision.setIntTipoViculo(null);
								fallecidoPrevision.setIntPersPersonaFallecido(beanSocioComp.getPersona().getIntIdPersona());
								listaFallecidosPrevisionBusq.add(fallecidoPrevision);		

					}else{
						
						personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
						//personaEmpresaPk = new PersonaEmpresaPK();
						Integer intIdPersona = socioComp.getSocio().getSocioEstructura().getId().getIntIdPersona();
						Integer intIdEmpresa = socioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa();
						
						/*if(listaBeneficiarios != null && listaBeneficiarios.size()>0){
							listaBeneficiarios.clear();
						}*/
						
						PersonaEmpresaPK personaPk = new PersonaEmpresaPK();
						personaPk.setIntIdEmpresa(intIdEmpresa);
						personaPk.setIntIdPersona(intIdPersona);
						
						vinculosFiltrada = personaFacade.getVinculoPorPk(personaPk);
						//vinculos = personaFacade.getVinculoPorPk(personaPk);
						
						if(vinculosFiltrada.isEmpty() || vinculosFiltrada != null){
							for(int n=0; n<vinculosFiltrada.size();n++){
								if(vinculosFiltrada.get(n).getIntTipoVinculoCod().compareTo(intValorRelacion)==0)
									vinculos.add(vinculosFiltrada.get(n));
							}
						}
						
						if(!vinculos.isEmpty()  || vinculos != null ){
							for(int k=0; k<vinculos.size();k++){
								Integer intIdPersonaVinc = vinculos.get(k).getIntPersonaVinculo();
								FallecidoPrevision fallecidoPrevision = new FallecidoPrevision();
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

										fallecidoPrevision.setPersona(persona);
										fallecidoPrevision.setIntItemViculo(vinculos.get(k).getIntItemVinculo());
										fallecidoPrevision.setIntPersPersonaFallecido(vinculos.get(k).getIntPersonaVinculo());
										fallecidoPrevision.setIntTipoViculo(vinculos.get(k).getIntTipoVinculoCod());
										fallecidoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
										lstFallecidosTemp.add(fallecidoPrevision);
									}	
								}
							}
						}
						listaFallecidosPrevisionBusq = lstFallecidosTemp;
					}
		
		}
				
				
			} catch (EJBFactoryException e) {
				log.info("Err--> "+e);
				
			} catch (BusinessException e1) {
				log.info("Err--> "+e1);
			}
		}
			
		
		
		
		/**
		 * 
		 * @param socioComp
		 */
		public void listarFallecidosDeSocioFiltro(SocioComp socioComp){
			log.info("-------------------------------------Debugging listarBeneficiariosDeSocio-------------------------------------");
			//PersonaEmpresaPK personaEmpresaPk = null;
			PersonaFacadeRemote personaFacade = null;
//			VinculoFacadeRemote vinculoFacade =null;
			List<Vinculo> vinculos =  new ArrayList<Vinculo>();
			List<Vinculo> vinculosFiltrada = new ArrayList<Vinculo>();
			List<FallecidoPrevision> lstFallecidosTemp = null;
			lstFallecidosTemp = new ArrayList<FallecidoPrevision>();
			Integer intValorRelacion = new Integer(0);
			try {

			    
				if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_CONYUGE)==0){
					intValorRelacion = Constante.PARAM_RELACION_CONYUGUE;
				}else if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_PADRES)==0){
					intValorRelacion = Constante.PARAM_RELACION_PADRE;
				}else if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_HIJOS)==0){
					intValorRelacion = Constante.PARAM_RELACION_HIJO;
				}else{
					intValorRelacion = Constante.PARAM_RELACION_OTROS;
				}
				
				
				
				if(socioComp != null){
					personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
					//personaEmpresaPk = new PersonaEmpresaPK();
					Integer intIdPersona = socioComp.getSocio().getSocioEstructura().getId().getIntIdPersona();
					Integer intIdEmpresa = socioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa();
					
					PersonaEmpresaPK personaPk = new PersonaEmpresaPK();
					personaPk.setIntIdEmpresa(intIdEmpresa);
					personaPk.setIntIdPersona(intIdPersona);
					
					vinculosFiltrada = personaFacade.getVinculoPorPk(personaPk);
					//vinculos = personaFacade.getVinculoPorPk(personaPk);
					
					if(vinculosFiltrada.isEmpty() || vinculosFiltrada != null){
						for(int n=0; n<vinculosFiltrada.size();n++){
							if(vinculosFiltrada.get(n).getIntTipoVinculoCod().compareTo(intValorRelacion)==0)
								vinculos.add(vinculosFiltrada.get(n));
						}
					}
					
					if(!vinculos.isEmpty()  || vinculos != null ){
						for(int k=0; k<vinculos.size();k++){
							Integer intIdPersonaVinc = vinculos.get(k).getIntPersonaVinculo();
							FallecidoPrevision fallecidoPrevision = new FallecidoPrevision();
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
									
									if((persona.getNatural().getStrApellidoMaterno().toUpperCase().contains(campoBuscarFallecido.toUpperCase())) 
											||(persona.getNatural().getStrApellidoPaterno().toUpperCase().contains(campoBuscarFallecido.toUpperCase()))
											||(persona.getNatural().getStrNombres().toUpperCase().contains(campoBuscarFallecido.toUpperCase()))
											||(persona.getDocumento().getStrNumeroIdentidad().toUpperCase().contains(campoBuscarFallecido.toUpperCase()))
									){
										fallecidoPrevision.setPersona(persona);
										fallecidoPrevision.setIntItemViculo(vinculos.get(k).getIntItemVinculo());
										fallecidoPrevision.setIntPersPersonaFallecido(vinculos.get(k).getIntPersonaVinculo());
										fallecidoPrevision.setIntTipoViculo(vinculos.get(k).getIntTipoVinculoCod());
										fallecidoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
										lstFallecidosTemp.add(fallecidoPrevision);
									}
								}	
							}
						}
					}
					listaFallecidosPrevisionBusq = lstFallecidosTemp;
				}
				
				
			} catch (EJBFactoryException e) {
				log.info("Err--> "+e);
				
			} catch (BusinessException e1) {
				log.info("Err--> "+e1);
			}
		}

		
		
		/**
		 * Define la carpeta donde  se registrara el archivo adjunto.
		 * @param event
		 */
		public void adjuntarDocumento(ActionEvent event) {
			log.info("-------------------------------------Debugging adjuntarDocumento -------------------------------------");
			String strParaTipoDescripcion = getRequestParameter("intParaTipoDescripcion");
			String strParaTipoOperacionPersona = getRequestParameter("intParaTipoOperacionPersona");
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

			if (listaRequisitoPrevisionComp != null) {
				for (RequisitoPrevisionComp requisitoPrevisionComp : listaRequisitoPrevisionComp) {
					
				//------------------------------- AES -------------------------------------------------------------------------------->
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_SOLICITUD_BENEFICIO)
						&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
						
						if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
							intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
							intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES);
					}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_BOLETAPAGOHABERES_CAS)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO);
						}
					
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_INFORME_JEFATURA)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES);
						}
					
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_INFORME_ADMINISTRADOR)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES);
						}
					
					
					
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_RECETA_MEDICA)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_EXAMENES_CLINICOS)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_CONSTANCIA_HOSPITALIZACION)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES);
						}

					
					
				//------------------------------- SEPELIO -------------------------------------------------------------------------------->
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SOLICITUD_BENEFICIO)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES);
						}
					

					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_INFORME_ADMINISTRADOR)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS );
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_COPIA_DNI_TITULAR)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_COPIA_DNI_BENEFICIARIO)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
						}

					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SUCESION_INTESTADA)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_DECLARATORIA_HEREDEROS)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS );
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_DEFUNCION_ORIFGINAL)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS );
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_NACIMIENTO_ORIFGINAL)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS );
						}
					
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_MATRIMONIO_ORIFGINAL)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS );
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SUSTENTO_GASTOS_SEPELIO)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_FOTO_LUGAR_SEPULTURA)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS);
						}
					
					
				//------------------------------- RETIRO -------------------------------------------------------------------------------->
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_BOLETA_DE_PAGO)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_SOLICITUD_BENEFICIO)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_COPIA_DNI_SOCIO)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_INFORME_ADMINISTRADOR)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_COPIA_DNI_BENEFICIARIO)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_CARTA_DECLARATORIA_BENEFICIARIO_SUCESION_INTESTADA)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_ACTA_PAGO_BENEFICIARIOS)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_DEFUNCION_ORIGINAL_LEGALIZADA)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_NACIMIENTO_ORIGINAL_LEGALIZADA)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES);
						}
					
					if (intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
							&& requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_MATRIMONIO_ORIGINAL_LEGALIZADA)
							&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							
							if (requisitoPrevisionComp.getRequisitoPrevision()!= null) {
								intItemArchivo = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemArchivo();
								intItemHistorico = requisitoPrevisionComp.getRequisitoPrevision().getIntParaItemHistorico();
							}
							fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES);
						}
				}
			}
			// fileupload.setStrJsFunction("putFile");
			fileupload.setStrJsFunction("putFileDocAdjunto()");
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
	// fileupload.getObjArchivo();
	if (listaRequisitoPrevisionComp != null) {
		for (RequisitoPrevisionComp requisitoPrevisionComp : listaRequisitoPrevisionComp) {

			
			//---------------------------------------------------------------AES-------------------------------------------------------------------->
			
			//-------------- BOLETAPAGOHABERES_CAS ------------------------
			if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES)) {
				if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_SOLICITUD_BENEFICIO)
					//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
					&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
					&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
					
					requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
					log.info("byteImg.length: "	+ fileupload.getDataImage().length);
					byte[] byteImg = fileupload.getDataImage();
					MyFile file = new MyFile();
					file.setLength(byteImg.length);
					file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
					file.setData(byteImg);
					requisitoPrevisionComp.setFileDocAdjunto(file);
					break;
				}
			}

			//-------------- BOLETAPAGOHABERES_CAS ------------------------
			if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO)) {
				if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_BOLETAPAGOHABERES_CAS)
					//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
					&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
					&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
					
					requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
					log.info("byteImg.length: "	+ fileupload.getDataImage().length);
					byte[] byteImg = fileupload.getDataImage();
					MyFile file = new MyFile();
					file.setLength(byteImg.length);
					file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
					file.setData(byteImg);
					requisitoPrevisionComp.setFileDocAdjunto(file);
					break;
				}
			}

		
		//-------------- RECETA_MEDICA ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_RECETA_MEDICA)
				//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		//-------------- EXAMENES_CLINICOS ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_EXAMENES_CLINICOS)
				//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		//-------------- CONSTANCIA_HOSPITALIZACION ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_CONSTANCIA_HOSPITALIZACION)
				//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		//-------------- INFORME_ADMINISTRADOR ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_INFORME_ADMINISTRADOR)
				//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- INFORME_JEFATURA ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_AES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_AES_INFORME_JEFATURA)
				//&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}
		
		
		
		//-------------------------------------------------------SEPELIO-------------------------------------------------------------------->
		
		//-------------- SOLICITUD_BENEFICIO ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SOLICITUD_BENEFICIO)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		//-------------- INFORME_ADMINISTRADOR ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_INFORME_ADMINISTRADOR)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		//-------------- COPIA_DNI_TITULAR ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_COPIA_DNI_TITULAR)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- COPIA_DNI_BENEFICIARIO ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_COPIA_DNI_BENEFICIARIO)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		//-------------- SUCESION_INTESTADA ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SUCESION_INTESTADA)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- DECLARATORIA_HEREDEROS ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_DECLARATORIA_HEREDEROS)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- PARTDIDA_DEFUNCION_ORIFGINAL ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_DEFUNCION_ORIFGINAL)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- PARTDIDA_NACIMIENTO_ORIFGINAL ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_NACIMIENTO_ORIFGINAL)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		//-------------- PARTDIDA_MATRIMONIO_ORIFGINAL ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_PARTDIDA_MATRIMONIO_ORIFGINAL)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- SUSTENTO_GASTOS_SEPELIO ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_SUSTENTO_GASTOS_SEPELIO)
				&& requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		//-------------- FOTO_LUGAR_SEPULTURA ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_SEPELIO_FOTO_LUGAR_SEPULTURA)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}
		
		//------------------------------------- RETIRO ------------------------------------------------------------------------------------------------------->
		
		
		//-------------- BOLETA_DE_PAGO ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_BOLETA_DE_PAGO)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}
		
		//-------------- SOLICITUD_BENEFICIO ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_SUSTENTOS)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_SOLICITUD_BENEFICIO)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- RETIRO_COPIA_DNI_SOCIO ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_COPIA_DNI_SOCIO)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- INFORME_ADMINISTRADOR ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_INFORME_ADMINISTRADOR)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- COPIA_DNI_BENEFICIARIO ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_COPIA_DNI_BENEFICIARIO)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- CARTA_DECLARATORIA_BENEFICIARIO_SUCESION_INTESTADA ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_CARTA_DECLARATORIA_BENEFICIARIO_SUCESION_INTESTADA)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		//-------------- RETIRO_ACTA_PAGO_BENEFICIARIOS ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_ACTA_PAGO_BENEFICIARIOS)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- PARTIDA_DEFUNCION_ORIGINAL_LEGALIZADA ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_DEFUNCION_ORIGINAL_LEGALIZADA)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- PARTIDA_NACIMIENTO_ORIGINAL_LEGALIZADA ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_NACIMIENTO_ORIGINAL_LEGALIZADA)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}

		
		
		//-------------- PARTIDA_MATRIMONIO_ORIGINAL_LEGALIZADA ------------------------
		if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SOLICITUD_PREVISION_INFORMES)) {
			if (requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PREVISION_RETIRO_PARTIDA_MATRIMONIO_ORIGINAL_LEGALIZADA)
				//&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
				&& intParaTipoDescripcion.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoDescripcion())
				&& intParaTipoOperacionPersona.equals(requisitoPrevisionComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
				
				requisitoPrevisionComp.setArchivoAdjunto(fileupload.getObjArchivo());
				log.info("byteImg.length: "	+ fileupload.getDataImage().length);
				byte[] byteImg = fileupload.getDataImage();
				MyFile file = new MyFile();
				file.setLength(byteImg.length);
				file.setName(fileupload.getObjArchivo()
						.getStrNombrearchivo());
				file.setData(byteImg);
				requisitoPrevisionComp.setFileDocAdjunto(file);
				break;
			}
		}


		}
	}
}
		

		/**
		 * Convierte un string en calendar
		 * @param fecha
		 * @return
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
		 * Recuperta las captaciones para validarlas.
		 * @return
		 */
		public List<Captacion> recuperarCaptaciones() {
			log.info("------------------------Debugging SolicitudPrestamoController.evaluarPrestamo------------------------");
			EstructuraDetalle estructuraDetalle = null;
//			Date today = new Date();
			//String strToday = Constante.sdf.format(today);
			//Date dtToday = Constante.sdf.parse(strToday);
			//Adenda adenda = null;
//			Captacion captacion = null;
			CaptacionId captacionId = null;
			List<Captacion> listaCaptaciones = null;
//			Calendar cal = Calendar.getInstance();
//			Calendar miCal = Calendar.getInstance();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			List<Captacion> listaCaptacionesCargadas = null;
			try {
				
				if (beanSocioComp.getSocio().getSocioEstructura() != null) {
					estructuraDetalle = new EstructuraDetalle();
					estructuraDetalle.setId(new EstructuraDetalleId());
					estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					
						estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																									beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																									beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
					
						//convenioEstructuraDetalle = estructuraFacade.getConvenioEstructuraDetallePorPkEstructuraDetalle(estructuraDetalle.getId());
						
						captacionId = new CaptacionId();
						captacionId.setIntPersEmpresaPk(beanSocioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa());	
						captacionId.setIntParaTipoCaptacionCod(intTipoSolicitud);
						// Diferenciamos el tipo de captacion
						/*if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
							captacionId.setIntParaTipoCaptacionCod(Integer.valueOf(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)); 
						}else if(intTipoSolicitud.intValue() == Constante.PARAM_T_TIPO_PREVISION_SEPELIO){
							captacionId.setIntParaTipoCaptacionCod(Integer.valueOf(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)); 
						}
						else if(intTipoSolicitud.intValue() == Constante.PARAM_T_TIPO_PREVISION_RETIRO){
							captacionId.setIntParaTipoCaptacionCod(Integer.valueOf(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO));
						
						} else {setStrMsgTxtEvaluacion("No se encontró Configuración de Captación.");}
						*/
						//----------
						/*public static final String PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO = "2";
						public static final String PARAM_T_TIPOSOLICITUDPREVISION_AES = "11";
						
						public static final Integer PARAM_T_TIPO_PREVISION_AES = 101;
						public static final Integer PARAM_T_TIPO_PREVISION_SEPELIO = 102;
						public static final Integer PARAM_T_TIPO_PREVISION_RETIRO = 103;
						*/
						//----------
						
						listaCaptaciones = captacionFacade.getCaptacionPorPKOpcional(captacionId);
						
						if(listaCaptaciones!=null && !listaCaptaciones.isEmpty()){
							listaCaptacionesCargadas = new ArrayList<Captacion>();
							
							for (Captacion captacionRec : listaCaptaciones) {
								if(captacionRec.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
									if(captacionRec.getDtFin() == null){
										//if(captacionRec.getIntParaEstadoSolicitudCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){

											List<Concepto> listaConceptos = null;
											List<Condicion> listaCondiciones = null;
											List<pe.com.tumi.credito.socio.captacion.domain.Vinculo> listaVinculo = null;
											
											CaptacionId id = new CaptacionId();
											id = captacionRec.getId();
											listaConceptos=captacionFacade.getListaConceptoPorPKCaptacion(id);
											listaCondiciones = condicionFacade.listarCondicion(id);
											listaVinculo = vinculoFacade.listarVinculoPorPKCaptacion(beanCaptacion.getId());
												//captacionFacade.getListaPorPKCaptacion(listaCaptacion.get(k).getId());
											//(arg0)   condicionFacade.listarCondicion(listaCaptacion.get(k).getId());
											
											if(listaConceptos != null && !listaConceptos.isEmpty()){
												captacionRec.setListaConcepto(listaConceptos);
											}
											if(listaCondiciones != null && !listaCondiciones.isEmpty()){
												captacionRec.setListaCondicion(listaCondiciones);
											}
											if(listaVinculo != null && !listaVinculo.isEmpty()){
												captacionRec.setListaVinculo(listaVinculo);
											}
											
											listaCaptacionesCargadas.add(captacionRec);
										
									}
	
								}
	
							}

						}else{
							setStrMsgTxtEvaluacion("No se encontró Configuración de Captación.");
						}
				}
			}
			catch (BusinessException e) {
					log.info("Error en recuperarCaptacion --> "+e);
				}

			return listaCaptacionesCargadas;		
		}
		

	
		
		/**
		 * Se recupera la captacion para la solciitud de prevision. AES, retiro y sepelio.
		 * En el caso de Retiro y sepelio, se recupera el id de la captracion desde la ctactodetalle.
		 * Para el caso del aes, se recupera todas las posiblers captacioens y se evalua hasta encontar la adecuada.
		 * @return
		 */
		public List<Captacion> recuperarCaptacionesPorPk() {
			log.info("------------------------Debugging SolicitudPrestamoController.evaluarPrestamo------------------------");
			//EstructuraDetalle estructuraDetalle = null;
			//Date today = new Date();
			//Captacion captacion = null;
			CaptacionId captacionId = null;
			List<Captacion> listaCaptaciones = null;
			//Calendar cal = Calendar.getInstance();
			//Calendar miCal = Calendar.getInstance();
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			List<Captacion> listaCaptacionesCargadas = null;
			CuentaConcepto cuentaConceptoEvaluada = null;
			try {
				
				if (beanSocioComp.getSocio().getSocioEstructura() != null) {
					
					// public static final String PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO = "2";
					// public static final String PARAM_T_TIPOSOLICITUDPREVISION_AES = "11";
					// public static final String PARAM_T_TIPOSOLICITUDPREVISION_RETIRO = "3";
					if(intTipoSolicitud.compareTo( new  Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
						cuentaConceptoEvaluada = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
						
						if(cuentaConceptoEvaluada != null){
							captacionId = new CaptacionId();
							
							if(cuentaConceptoEvaluada.getListaCuentaConceptoDetalle() != null && !cuentaConceptoEvaluada.getListaCuentaConceptoDetalle().isEmpty()){
								CuentaConceptoDetalle detalle =  cuentaConceptoEvaluada.getListaCuentaConceptoDetalle().get(0);
									if(detalle != null){
										captacionId.setIntPersEmpresaPk(detalle.getId().getIntPersEmpresaPk());	
										captacionId.setIntParaTipoCaptacionCod(detalle.getIntParaTipoConceptoCod());
										captacionId.setIntItem(detalle.getIntItemConcepto());	
									}
							}
						}
	
					}else if(intTipoSolicitud.compareTo( new  Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
								cuentaConceptoEvaluada = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
								if(cuentaConceptoEvaluada != null){
									captacionId = new CaptacionId();
									List<CuentaConceptoDetalle> listaCtaCtoDet = new ArrayList<CuentaConceptoDetalle>();
									
									listaCtaCtoDet = cuentaConceptoEvaluada.getListaCuentaConceptoDetalle();
									
									if(listaCtaCtoDet != null && !listaCtaCtoDet.isEmpty()){
										
										Collections.sort(listaCtaCtoDet, new Comparator<CuentaConceptoDetalle>(){
											public int compare(CuentaConceptoDetalle uno, CuentaConceptoDetalle otro) {
												return uno.getId().getIntItemCtaCptoDet().compareTo(otro.getId().getIntItemCtaCptoDet());
											}
										});
										
										for (CuentaConceptoDetalle ctaCtoDetPeriodo : listaCtaCtoDet) {
											System.out.println("getIntItemCtaCptoDet ---> "+ctaCtoDetPeriodo.getId().getIntItemCtaCptoDet());
											System.out.println("getTsInicio ---> "+ctaCtoDetPeriodo.getTsInicio());
											System.out.println("getTsFin ---> "+ctaCtoDetPeriodo.getTsFin());
										}
										
										Integer tamanno = listaCtaCtoDet.size();
										
										CuentaConceptoDetalle detalle =  cuentaConceptoEvaluada.getListaCuentaConceptoDetalle().get(tamanno-1);
											if(detalle != null){
												captacionId.setIntPersEmpresaPk(detalle.getId().getIntPersEmpresaPk());	
												captacionId.setIntParaTipoCaptacionCod(detalle.getIntParaTipoConceptoCod());
												captacionId.setIntItem(detalle.getIntItemConcepto());	
											}
									}
								}
						}else if(intTipoSolicitud.compareTo( new  Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){

									captacionId = new CaptacionId();
									captacionId.setIntPersEmpresaPk(beanSocioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa());	
									captacionId.setIntParaTipoCaptacionCod(intTipoSolicitud);

						}
					

						//listaCaptaciones = captacionFacade.getCaptacionPorPKOpcional(captacionId);
						listaCaptaciones = captacionFacade.getCaptacionPorPKOpcional(captacionId);
						if(listaCaptaciones!=null && !listaCaptaciones.isEmpty()){
							listaCaptacionesCargadas = new ArrayList<Captacion>();
							
							for (Captacion captacionRec : listaCaptaciones) {
								if(captacionRec.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
									if(captacionRec.getDtFin() == null){
										//if(captacionRec.getIntParaEstadoSolicitudCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){

											List<Concepto> listaConceptos = null;
											List<Condicion> listaCondiciones = null;
											List<pe.com.tumi.credito.socio.captacion.domain.Vinculo> listaVinculo = null;
											
											CaptacionId id = new CaptacionId();
											id = captacionRec.getId();
											listaConceptos=captacionFacade.getListaConceptoPorPKCaptacion(id);
											listaCondiciones = condicionFacade.listarCondicion(id);
											listaVinculo = vinculoFacade.listarVinculoPorPKCaptacion(beanCaptacion.getId());
												//captacionFacade.getListaPorPKCaptacion(listaCaptacion.get(k).getId());
											//(arg0)   condicionFacade.listarCondicion(listaCaptacion.get(k).getId());
											
											if(listaConceptos != null && !listaConceptos.isEmpty()){
												captacionRec.setListaConcepto(listaConceptos);
											}
											if(listaCondiciones != null && !listaCondiciones.isEmpty()){
												captacionRec.setListaCondicion(listaCondiciones);
											}
											if(listaVinculo != null && !listaVinculo.isEmpty()){
												captacionRec.setListaVinculo(listaVinculo);
											}
											
											listaCaptacionesCargadas.add(captacionRec);
										
									}
	
								}
	
							}

						}else{
							setStrMsgTxtEvaluacion("No se encontró Configuración de Captación.");
						}
				}
			}
			catch (BusinessException e) {
					log.info("Error en recuperarCaptacion --> "+e);
					}

			return listaCaptacionesCargadas;		
		}
		
		
		/**
		 * 
		 */
		public void evaluarPrevisionMod(ActionEvent event) throws ParseException {
			log.info("------------------------Debugging evaluarPrevisionMod------------------------");
			EstructuraDetalle estructuraDetalle = null;
			//ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
//			Date today = new Date();
//			String strToday = Constante.sdf.format(today);
//			Date dtToday = Constante.sdf.parse(strToday);
			intNroCuotasPagadasTotal=0;
			//beanExpedientePrevision.setIntParaTipoCaptacion(intTipoSolicitud);
			//beanExpedientePrevision.setIntParaSubTipoOperacion(intSubTipoSolicitud);

			try {
				
				if (procedeEvaluacion(beanExpedientePrevision) == false) {
					log.info("Datos de Previsión no válidos. Se aborta el proceso de Evaluacion de Solicitud.");
					return;
				}
				
				intTipoSolicitud = beanExpedientePrevision.getIntParaTipoCaptacion();
				intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion() ;
				
				
				if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
					listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_AES));
				} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
					listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO));					
				} else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
					listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO));				
				}

				
				if (beanSocioComp.getSocio().getSocioEstructura() != null) {
					estructuraDetalle = new EstructuraDetalle();
					estructuraDetalle.setId(new EstructuraDetalleId());
					estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																								beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																								beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
					
					CaptacionId captacionId = new CaptacionId();
					captacionId .setIntItem(beanExpedientePrevision.getIntItem());
					captacionId.setIntParaTipoCaptacionCod(beanExpedientePrevision.getIntParaTipoCaptacion());
					captacionId.setIntPersEmpresaPk(beanExpedientePrevision.getIntPersEmpresa());

					beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
					
					if(beanCaptacion != null){
						blnPostEvaluacion=Boolean.TRUE;
						blnCaptacion = Boolean.TRUE;
						blnDeshabilitar = Boolean.TRUE;
					}

					if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES) )==0){
						isValidCondicionCuotasSepelio();  //blnCondicionCuotaSepelio
						isValidCondicionSolicitudesAESPorPeriodos(); // blnCondicionPeridoMeses
						isValidCondicionDebeTenerDeuda();  // blnAESDebeTenerDeuda
						
						
					} if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
						cargarValorGastosAdministrativosSepelio();
						
						getCalculoMontoGAyNeto();
						calcularMontosPorcentajeBeneficiariosSepelio(event);
						
						isValidCondicionCuotasSepelio();
						isValidCondicionPresentacionSolicitud();
						

					} else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
						isValidCondicionCuotasRetiro();
						recuperarMontosRetiro();
						if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null
							&& !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
							
							calcularMontosPorcentajeBeneficiariosRetiro();
							
						}
					}
					
					blnPostEvaluacion=Boolean.TRUE;
					blnCaptacion = Boolean.TRUE;
					blnDeshabilitar = Boolean.TRUE;
					
				}

		} catch (Exception e) {
					log.error("Error en evaluarPrevisionMod ---> " + e);
						e.printStackTrace();
				} 

		}

		
		public void evaluarPrevisionModSinValidaciones(ActionEvent event) throws ParseException {
			log.info("------------------------Debugging evaluarPrevisionMod------------------------");
			EstructuraDetalle estructuraDetalle = null;
			//ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
//			Date today = new Date();
//			String strToday = Constante.sdf.format(today);
//			Date dtToday = Constante.sdf.parse(strToday);
			intNroCuotasPagadasTotal=0;
			//beanExpedientePrevision.setIntParaTipoCaptacion(intTipoSolicitud);
			//beanExpedientePrevision.setIntParaSubTipoOperacion(intSubTipoSolicitud);

			try {
				
//				if (procedeEvaluacion(beanExpedientePrevision) == false) {
//					log.info("Datos de Previsión no válidos. Se aborta el proceso de Evaluacion de Solicitud.");
//					return;
//				}
				
				intTipoSolicitud = beanExpedientePrevision.getIntParaTipoCaptacion();
				intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion() ;
				
				
				if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
					listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_AES));
				} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
					listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO));					
				} else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
					listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO));				
				}

				
				if (beanSocioComp.getSocio().getSocioEstructura() != null) {
					estructuraDetalle = new EstructuraDetalle();
					estructuraDetalle.setId(new EstructuraDetalleId());
					estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																								beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																								beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
					
					CaptacionId captacionId = new CaptacionId();
					captacionId .setIntItem(beanExpedientePrevision.getIntItem());
					captacionId.setIntParaTipoCaptacionCod(beanExpedientePrevision.getIntParaTipoCaptacion());
					captacionId.setIntPersEmpresaPk(beanExpedientePrevision.getIntPersEmpresa());

					beanCaptacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
					
					if(beanCaptacion != null){
						blnPostEvaluacion=Boolean.TRUE;
						blnCaptacion = Boolean.TRUE;
						blnDeshabilitar = Boolean.TRUE;
					}

					if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES) )==0){
						isValidCondicionCuotasSepelio();  //blnCondicionCuotaSepelio
						isValidCondicionSolicitudesAESPorPeriodos(); // blnCondicionPeridoMeses
						isValidCondicionDebeTenerDeuda();  // blnAESDebeTenerDeuda
						
						
					} if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
						cargarValorGastosAdministrativosSepelio();
						
						getCalculoMontoGAyNeto();
						calcularMontosPorcentajeBeneficiariosSepelio(event);
						
						isValidCondicionCuotasSepelio();
						isValidCondicionPresentacionSolicitud();
						

					} else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
						isValidCondicionCuotasRetiro();
						recuperarMontosRetiro();
						if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null
							&& !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
							
							calcularMontosPorcentajeBeneficiariosRetiro();
							
						}
					}
					
					blnPostEvaluacion=Boolean.TRUE;
					blnCaptacion = Boolean.TRUE;
					blnDeshabilitar = Boolean.TRUE;
					
				}

		} catch (Exception e) {
					log.error("Error en evaluarPrevisionMod ---> " + e);
						e.printStackTrace();
				} 

		}
		
		/**
		 * Realiza la evaluaciond e ela solicitud, recupera las captaciones las valida y recupera una final, sobre la cual se opera.
		 * @param event
		 * @throws ParseException
		 */
		public void evaluarPrevision(ActionEvent event) throws ParseException {
			log.info("------------------------Debugging evaluarPrevision------------------------");
			EstructuraDetalle estructuraDetalle = null;
			//ConvenioEstructuraDetalle convenioEstructuraDetalle = null;
			Date today = new Date();
			String strToday = Constante.sdf.format(today);
			Date dtToday = Constante.sdf.parse(strToday);
			intNroCuotasPagadasTotal=0;
			beanExpedientePrevision.setIntParaTipoCaptacion(intTipoSolicitud);
			beanExpedientePrevision.setIntParaSubTipoOperacion(intSubTipoSolicitud);
			
			try {
				// COMENTADO POR PRUEBAS
				if (procedeEvaluacion(beanExpedientePrevision) == false) {
					log.info("Datos de Previsión no válidos. Se aborta el proceso de Evaluacion de Solicitud.");
					return;
				}

				if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
					listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_AES));
				} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
						listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO));					
						
				} else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
						listaTablaDescripcionAdjuntos = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO));				
						
				}
				
				cargarDescripcionTipoPrevision();
				List <Captacion> listaCaptacionesRecuperadas = null;
				listaCaptacionesRecuperadas = recuperarCaptacionesPorPk();
				
				if (beanSocioComp.getSocio().getSocioEstructura() != null) {
					
					estructuraDetalle = new EstructuraDetalle();
					estructuraDetalle.setId(new EstructuraDetalleId());
					estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																								beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																								beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
			

					// recorremos todas las captaciones recuperradas
					Boolean blnContinua = Boolean.TRUE;
					if(listaCaptacionesRecuperadas != null && !listaCaptacionesRecuperadas.isEmpty()){
						
						for (Captacion captacionEvaluada : listaCaptacionesRecuperadas) {

							if(blnContinua){
							//Captacion captacionEvaluada = new Captacion();
							//captacionEvaluada = listaCaptacionesRecuperadas.get(k);
							Calendar fechaInicio = null;
							Calendar fechaFin = null;
							Boolean blnFecIni = Boolean.FALSE;
							Boolean blnFecFin = Boolean.FALSE;

							// 1. Validando estado de captacion
							if (captacionEvaluada.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								
								// 2. Validando vigencia. Fecha de inicio y fin de captacion
								if(captacionEvaluada.getStrDtFechaIni() != null){
									fechaInicio=Calendar.getInstance();
									fechaInicio.clear();
									fechaInicio = StringToCalendar(captacionEvaluada.getStrDtFechaIni());
									if(dtToday.after(fechaInicio.getTime())){
										blnFecIni = Boolean.TRUE;
									}
								}
								if(captacionEvaluada.getStrDtFechaFin() != null){
									fechaFin = Calendar.getInstance();
									fechaFin.clear();
									fechaFin = StringToCalendar(captacionEvaluada.getStrDtFechaFin());
									if(dtToday.before(fechaFin.getTime())){
										blnFecFin = Boolean.TRUE;
									}
								}else{
									blnFecFin = Boolean.TRUE;
								}

								if(blnFecIni && blnFecFin){
									
									// 3. Validando Tipo de Persona
									if(captacionEvaluada.getIntParaTipopersonaCod().compareTo(beanSocioComp.getPersona().getIntTipoPersonaCod())==0){
										
										// 4. Validando la edad del socio si existiera. En el caso de aes.
										Boolean blnEdad = Boolean.FALSE;
										
										if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))!=0){
											
											blnEdad = Boolean.TRUE;

										}else{
											
											if(captacionEvaluada.getIntEdadLimite() !=null ){
												int edad = 0;
												edad=fechasDiferenciaEnAnnos(beanSocioComp.getPersona().getNatural().getDtFechaNacimiento(), dtToday);
		
												if(edad <= captacionEvaluada.getIntEdadLimite().intValue()){
													blnEdad = Boolean.TRUE;
												}
											}else{
												blnEdad = Boolean.TRUE;
											}	
										}
										
										
										if(blnEdad){
											// 6. Validando condicion del socio vs lista condicion
											Boolean blnFlagCondicion = false;
											List<Condicion> lstCondicion = null;
											
											lstCondicion = captacionEvaluada.getListaCondicion();
											if(lstCondicion != null && !lstCondicion.isEmpty()){
												for (Condicion condicion : lstCondicion) {
													if(condicion.getId().getIntParaCondicionSocioCod().compareTo(-1)==0
														&& condicion.getIntValor().compareTo(1) == 0){
															blnFlagCondicion = true;
															break;
														}else{
															if (beanSocioComp.getCuenta().getIntParaCondicionCuentaCod().compareTo(condicion.getId().getIntParaCondicionSocioCod())==0
																&& condicion.getIntValor().compareTo(1) == 0 ){
																	blnFlagCondicion = true;
																	break;
															}else {
																	blnFlagCondicion = false;
															}
														}	
												}
											}else{
												blnFlagCondicion = Boolean.TRUE;	
											}
											
											if(blnFlagCondicion){
												
												/*// 7. Validando Tipo de relación  vs lista condicion
												List<PersonaRol> listaPersonaRoles = null;
												Boolean blnRoles = Boolean.FALSE;
												PersonaEmpresaPK personaEmpresaPk = new PersonaEmpresaPK();
												personaEmpresaPk.setIntIdEmpresa(beanSocioComp.getSocio().getId().getIntIdEmpresa());
												personaEmpresaPk.setIntIdPersona(beanSocioComp.getPersona().getIntIdPersona());
												
												listaPersonaRoles = personaFacade.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresaPk);
												
												if(listaPersonaRoles != null && !listaPersonaRoles.isEmpty()){
													
												}else{
													blnRoles 
												}
												beanSocioComp.getPersona().getPersonaEmpresa().getListaPersonaRol()*/
												
												//if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
												//	Boolean blnProcede = Boolean.FALSE;
													
													
													//if(blnProcede){
														
														blnPostEvaluacion = Boolean.TRUE;
														blnContinua = Boolean.FALSE;
														beanCaptacion = captacionEvaluada;
														blnCaptacion = true;
														blnDeshabilitar = true;
														
														/*cargarValorGastosAdministrativos();
														getCalculoMontoGAyNeto();
														calcularMontosPorcentajeBeneficiariosSepelio(event);
														
														isValidCondicionCuotasSepelio();
														isValidCondicionPresentacionSolicitud();*/
													//}
													
												/*}else{
													blnPostEvaluacion = Boolean.TRUE;
													blnContinua = Boolean.FALSE;
													beanCaptacion = captacionEvaluada;
													blnCaptacion = true;
													blnDeshabilitar = true;
													
												}*/
											}	
										}
									}	
								}	
							}
						}
					}

						if(blnPostEvaluacion.compareTo(Boolean.TRUE)==0){
							setStrMsgTxtCaptacionSepelio("");
							if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES) )==0){
								isValidCondicionCuotasSepelio();  //blnCondicionCuotaSepelio
								isValidCondicionSolicitudesAESPorPeriodos(); // blnCondicionPeridoMeses
								isValidCondicionDebeTenerDeuda();  // blnAESDebeTenerDeuda
								//isValidCondicionCuotasAES();
								
								
							}else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
								//Boolean blnProcede = Boolean.FALSE;
								
								cargarValorGastosAdministrativosSepelio();
								
								getCalculoMontoGAyNeto();
								calcularMontosPorcentajeBeneficiariosSepelio(event);
								
								isValidCondicionCuotasSepelio();
								isValidCondicionPresentacionSolicitud();
								/*if(blnProcede){
									getCalculoMontoGAyNeto();
									calcularMontosPorcentajeBeneficiariosSepelio(event);
									
									isValidCondicionCuotasSepelio();
									isValidCondicionPresentacionSolicitud();
								}else{
									setStrMsgTxtCaptacionSepelio("No se recueperaron los datos de Gastos Administrativos. ");
								}*/
								
								
								/*isValidCondicionCuotasSepelio();
								isValidCondicionPresentacionSolicitud();
								cargarValorGastosAdministrativos();
								calcularMontosPorcentajeBeneficiarios();*/
								
							} else if(intTipoSolicitud.compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0){
								// validaciones de cuotas de sepelio
								
								isValidCondicionCuotasRetiro();
								recuperarMontosRetiro();
								//isValidCondicionCuotasRetiro();
								if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null
									&& !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
									
									calcularMontosPorcentajeBeneficiariosRetiro();
									
								}
								
								/*isValidCondicionCuotasRetiro();
								recuperarMontosRetiro();
								calcularMontosPorcentajeBeneficiariosRetiro();	*/
							}
							mostrarArchivosAdjuntos(event);
							
						}else{
							setStrMsgTxtCaptacionSepelio("Ninguna de las configuraciones registradas para Captación coinciden con las características del Socio. ");
						}	
				}else{
					setStrMsgTxtGrabar("No se recuperaron Captaciones.");
				}
			}
		} catch (Exception e) {
				log.error("Error en evaluarPrevision ---> " + e);
			} 
		}
		
	/**
	 * 	Devuelve en años (int) la diferencia entre 2 fechas date
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
	 * Devuelve en meses (int) la diferencia entre 2 fechas date
	 * @param fechaInicial
	 * @param fechaFinal
	 * @return
	 */
	public int fechasDiferenciaEnMeses(Date fechaInicial, Date fechaFinal) {
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
		double meses = Math.floor(diferencia / (1000 * 60 * 60 * 24));
				meses = Math.floor(meses/365);
				meses = Math.floor(meses*12);
		return ((int) meses);
	}
	
	
	
	
	/**
	 * Aplica a AES.
	 * Valida de acuerdo a la regularidad y periocidad si una solicitud de prevision puede generarse.
	 * @return boolean
	 */
	public boolean isValidCondicionSolicitudesAESPorPeriodos (){
		blnCondicionPeridoMeses = true;
		Integer intRegularidad = null;
		Integer intPeriodicidad = null;
		ExpedientePrevisionId expedienteId = null; 
		List<ExpedientePrevisionComp> listaExpedientesValidacion = null; // Expedientes de prevision existentes
		ExpedientePrevisionComp expedienteValidacion = null;
		strMsgTxtAESPeriocidad = "";
		List<Tabla> listaPeriocidadAes = null; 
		String strToday = null;
		Date dtToday = null;	

		try{
			
			strToday = Constante.sdf.format(new Date());
			
			if(beanCaptacion.getIntRegularidad() != null && beanCaptacion.getIntPeriodicidad() != null){
				intRegularidad = beanCaptacion.getIntRegularidad();  // 1
				intPeriodicidad = beanCaptacion.getIntPeriodicidad(); // 9
				
				// CONVERTIR INTPERIOCIDAD EN BASE A PARAMETRO 264
				listaPeriocidadAes = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_PERIOCIDAD_AES);
				
				if(listaPeriocidadAes != null){
					for(int p=0; p<listaPeriocidadAes.size();p++){
						if(listaPeriocidadAes.get(p).getIntIdDetalle().compareTo(intPeriodicidad)==0){
							intPeriodicidad = new Integer(listaPeriocidadAes.get(p).getStrDescripcion());
							break;
						}
					}	
				}
				
				if(intRegularidad != null){
					intSolicitudRegularidad = intRegularidad;
				}
				if(intPeriodicidad != null){
					intSolicitudPeriodicidad = intPeriodicidad;
				}

				dtToday = Constante.sdf.parse(strToday);
				
				expedienteId = new ExpedientePrevisionId();
				expedienteId.setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());
				expedienteId.setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());	
				
				// Recuperamos los expedientes 
				listaExpedientesValidacion = previsionFacade.getListaExpedienteCreditoPorEmpresaYCuenta(expedienteId);
				
				int diferenciaEnDias = (int)Math.floor(intPeriodicidad*30);
				long tiempoActual = dtToday.getTime();
				long sustraendo = diferenciaEnDias * 24 * 60 * 60 * 1000;
				
				// Restandole tantos meses como Periocidad a la fecha de hoy
				Date fechaInicio = new Date(tiempoActual - sustraendo);
				Integer intContador = 0;
				
				if(listaExpedientesValidacion != null){
					for(int k=0; k<listaExpedientesValidacion.size();k++){
						expedienteValidacion =listaExpedientesValidacion.get(k);
						if(expedienteValidacion.getIntTipoSolicitud() != null 
						&& expedienteValidacion.getEstadoPrevision() != null){
							if(expedienteValidacion.getIntTipoSolicitud().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
								if(expedienteValidacion.getEstadoPrevision() != null ){
									if(expedienteValidacion.getEstadoPrevision().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0){
										if(expedienteValidacion.getEstadoPrevision().getTsFechaEstado().after(fechaInicio)  ){
											intContador++;	
										}
									}	
								}
								
							}
						}
						
					}
				}
				
	
				//if(20 >= intRegularidad){
				if(intContador >= intRegularidad){	
					blnCondicionPeridoMeses = false;
						strMsgTxtAESPeriocidad = "Observación: Existe(n) "+ intRegularidad+" Solicitud(es) Girada(s) en los últimos "+ intPeriodicidad + "meses.";
						setChkCondicionPeridoMeses(false);
				}else{
					setChkCondicionPeridoMeses(true);
					strMsgTxtAESPeriocidad="";
				}
				
			}
					
		}catch (Exception e) {
			log.error("ERROR VALIDACION MESES - PERIOCIDAD ---> "+e);
		}
		return blnCondicionPeridoMeses;

	}

	
	
	/**
	 * Valida la fecha de fallecimiento contra la de sustento segun configuracion
	 * @return
	 */
	public boolean isValidCondicionPresentacionSolicitud(){
		blnCondicionPresentacionSol = true;
		List<Tabla> listaTiempoSustento = null; // nro
		List<Tabla> listaFrecuenciaSustento = null; // unidades del tiempo d sustento --> dias, meses, semanas
		strMinimoMaximo = null;
		Integer nroDias = null;
		Integer semanas = null;
		Integer meses = null;
		Integer bimestre = null;
		Integer semestre = null;
		Integer anual = null;
		
		try {
			listaTiempoSustento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TCUOTASMINFONRET));
			
			// obtenemos el tiempod me sustento
			if(listaTiempoSustento != null){
				for(int k=0;k<listaTiempoSustento.size();k++){
					if(listaTiempoSustento.get(k).getIntIdDetalle().intValue() == beanCaptacion.getIntTiempoSustento().intValue()){
						intTiempoSustentoTabla = new Integer(listaTiempoSustento.get(k).getStrDescripcion());
						break;
					}
				}	
			}

			listaFrecuenciaSustento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_FRECUENCPAGOINT));
			if(listaFrecuenciaSustento != null){
				//Siempre toma el primer valor, asi que esta demas el for !!!. jchavez 08.04.2014
//				for(int f=0; f < listaFrecuenciaSustento.size(); f++){
//					strFrecuenciaPresentacionSolicitud = listaFrecuenciaSustento.get(f).getStrDescripcion();
//					break;
//				}
				strFrecuenciaPresentacionSolicitud = listaFrecuenciaSustento.get(0).getStrDescripcion();
			}

			nroDias = fechasDiferenciaEnDias(beanExpedientePrevision.getDtFechaFallecimiento() ,dtFechaRegistro);
			nroDias = Math.abs(nroDias);
			//nroDias = Math.abs(nroDias);
			//if(Abs( nroDias) > -1){
				semanas = (int)Math.floor(nroDias / 7);
				meses = (int)Math.floor(semanas / 4);
				bimestre = (int)Math.floor(meses / 2);
				semestre = (int)Math.floor(bimestre / 3);
				anual = (int)Math.floor(semestre / 2);
				
				Integer intValorComparacion = 0;
				if(beanCaptacion.getIntPeriodicSustento() != null){
					intTiempoPresentacion = beanCaptacion.getIntPeriodicSustento();	
				}
				
				
				if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_DIARIO.intValue()){
					intValorComparacion = nroDias;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_SEMANAL.intValue()){
							intValorComparacion = semanas;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_MENSUAL.intValue()){
							intValorComparacion = meses;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_BIMENSUAL.intValue()){
							intValorComparacion = bimestre;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_SEMESTRAL.intValue()){
							intValorComparacion = semestre;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_ANUAL.intValue()){
							intValorComparacion = anual;
				}

				
				if(beanCaptacion.getIntParaTipoMaxMinSustCod().intValue() == Constante.PARAM_T_MINIMO.intValue()){
					strMinimoMaximo = "Mínimo";
					if(intTiempoSustentoTabla < intValorComparacion.intValue()){
						//blnFechafallecimiento = false;
						setStrMsgTxtSepelioFechaPresentacion("La fecha ingresada del Fallecimiento Sustentación es de "+ intValorComparacion +" "+ strFrecuenciaPresentacionSolicitud );
						blnCondicionPresentacionSol=false;
						setChkCondicionPresentacionSol(false);
					}else{	//blnFechafallecimiento = true;
							setStrMsgTxtSepelioFechaPresentacion("");
							blnCondicionPresentacionSol=true ;
							setChkCondicionPresentacionSol(true);	
					}
					
				}else if(beanCaptacion.getIntParaTipoMaxMinSustCod().intValue() == Constante.PARAM_T_MAXIMO.intValue()){
						strMinimoMaximo = "Máximo";
						if(intTiempoSustentoTabla < intValorComparacion.intValue()){
							//blnFechafallecimiento = false;
							setStrMsgTxtSepelioFechaPresentacion("La fecha ingresada del Fallecimiento Sustentación es de "+ intValorComparacion +" "+ strFrecuenciaPresentacionSolicitud);
							blnCondicionPresentacionSol = false;
							setChkCondicionPresentacionSol(false);
						}else{ 	//blnFechafallecimiento = true;
								setStrMsgTxtSepelioFechaPresentacion("");
								blnCondicionPresentacionSol = true;
								setChkCondicionPresentacionSol(true);
						}
				}



			
		} catch (Exception e) {
			log.info("Error en isValidFechaDeSustentoMedico --> "+e);
		}
		return blnCondicionPresentacionSol;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isValidFechaDeSustentoMedico(){
		blnFechaSustentoMedico = true;
		List<Tabla> listaTiempoSustento = null; // nro
		List<Tabla> listaFrecuenciaSustento = null; // unidades del tiempo d sustento --> dias, meses, semanas
		Integer intTiempoSustentoTabla = null;
		String strFrecuencia = null;
		String strMinimoMaximo = null;
		Integer nroDias = null;
		Integer semanas = null;
		Integer meses = null;
		Integer bimestre = null;
		Integer semestre = null;
		Integer anual = null;
		
		/*
		beanCaptacion.getIntParaTipoMaxMinSustCod();
		beanCaptacion.getIntTiempoSustento();
		beanCaptacion.getIntPeriodicSustento();*/
		
		try {
			listaTiempoSustento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TCUOTASMINFONRET));
			
			// obtenemos el tiempod me sustento
			if(listaTiempoSustento != null){
				for(int k=0;k<listaTiempoSustento.size();k++){
					if(listaTiempoSustento.get(k).getIntIdDetalle().intValue() == beanCaptacion.getIntTiempoSustento().intValue()){
						intTiempoSustentoTabla = new Integer(listaTiempoSustento.get(k).getStrDescripcion());
						break;
					}
				}	
			}
			
			/*
			public static final Integer FRECUENCIA_DIARIO 	 = 1;
			public static final Integer FRECUENCIA_SEMANAL 	 = 2;
			public static final Integer FRECUENCIA_MENSUAL 	 = 3;
			public static final Integer FRECUENCIA_BIMENSUAL = 4;
			public static final Integer FRECUENCIA_SEMESTRAL = 5;
			public static final Integer FRECUENCIA_ANUAL 	 = 6;
			*/
			listaFrecuenciaSustento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_FRECUENCPAGOINT));
			if(listaFrecuenciaSustento != null){
				//Siempre toma el primer valor, asi que esta demas el for !!!. jchavez 08.04.2014
//				for(int f=0; f < listaFrecuenciaSustento.size(); f++){
//					strFrecuencia = listaFrecuenciaSustento.get(f).getStrDescripcion();
//					break;
//				}
				strFrecuencia = listaFrecuenciaSustento.get(0).getStrDescripcion();
			}

			nroDias = fechasDiferenciaEnDias(dtFechaRegistro, beanExpedientePrevision.getDtFechaFallecimiento());
			if(nroDias > -1){
				semanas = (int)Math.floor(nroDias / 7);
				meses = (int)Math.floor(semanas / 4);
				bimestre = (int)Math.floor(meses / 2);
				semestre = (int)Math.floor(bimestre / 3);
				anual = (int)Math.floor(semestre / 2);
				
				Integer intValorComparacion = 0;
				if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_DIARIO.intValue()){
					intValorComparacion = nroDias;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_SEMANAL.intValue()){
							intValorComparacion = semanas;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_MENSUAL.intValue()){
							intValorComparacion = meses;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_BIMENSUAL.intValue()){
							intValorComparacion = bimestre;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_SEMESTRAL.intValue()){
							intValorComparacion = semestre;
				}else if(beanCaptacion.getIntPeriodicSustento().intValue() ==  Constante.FRECUENCIA_ANUAL.intValue()){
							intValorComparacion = anual;
				}

				
				if(beanCaptacion.getIntParaTipoMaxMinSustCod().intValue() == Constante.PARAM_T_MINIMO.intValue()){
					strMinimoMaximo = "Mínimo";
					if(intTiempoSustentoTabla > intValorComparacion.intValue()){
						blnFechaSustentoMedico = false;
						setStrMsgTxtAESFechaSustentacion("La fecha de Sustentación esta fuera de rango: "+ strMinimoMaximo + " "+ intValorComparacion +" "+strFrecuencia);
					}else{	blnFechaSustentoMedico = true;
							setStrMsgTxtAESFechaSustentacion("");
					}
					
				}else if(beanCaptacion.getIntParaTipoMaxMinSustCod().intValue() == Constante.PARAM_T_MAXIMO.intValue()){
						strMinimoMaximo = "Máximo";
						if(intTiempoSustentoTabla > intValorComparacion.intValue()){
							blnFechaSustentoMedico = false;
							setStrMsgTxtAESFechaSustentacion("La fecha de Sustentación esta fuera de rango: "+ strMinimoMaximo + " "+ intValorComparacion +" "+strFrecuencia);
						}else{ 	blnFechaSustentoMedico = true;
								setStrMsgTxtAESFechaSustentacion("");
						}
					}
			}


			
		} catch (Exception e) {
			log.info("Error en isValidFechaDeSustentoMedico --> "+e);
		}
		return blnFechaSustentoMedico;
	}
	
	
/*
	 * Aplica a AES. Valida 
	
	public boolean isValidCondicionCuotasAESnousado(){
		//CuentaId cuentaId = null;
		//List<CuentaConcepto> listaCuentaConcepto=null;
		List<Movimiento> listaMovientosTotal=null;
		List<Movimiento> listaMovimientoFiltrada=new ArrayList<Movimiento>();
		Movimiento movimiento = null;
		//List<CuentaConceptoDetalle> listaCuentaConceptoDet = null;
		blnCondicionCuotaSepelio = true;
		//blnAESCuotaSepelio = true;
		BigDecimal bdValorCuota 		  = BigDecimal.ZERO; 	// Valor de cada cuota de sepelio (S/. 12) (A)
		//Integer   
		intNroCuotasDefinidasSepelio = null; 				// Nro de cuotas que debe aportar el socio (B)
		BigDecimal bdTotalAportesDefinido = BigDecimal.ZERO; 	// producto de (A)*(B)
		BigDecimal bdTotalAporteSocio     = BigDecimal.ZERO;	//  total de aportes del socio en sepelio 
		List<Tabla> listaDescCuotasSepelio 	  = null;
		Integer intNroCuotasPagadas 	  = null;						// Nro de cuotas que cancelo el socio.
		Integer intNroCuotasDistintas 	  = null;
		Captacion captacionSepelioTemp = null;
		List<Captacion> listaCaptacionSepelio = new ArrayList<Captacion>();
		List<Captacion> listaCaptacionSepelioFinal = new ArrayList<Captacion>();
		List<BigDecimal> listaMontos = null;
		List<Integer> listaNroCuotas =  null;
		List<Integer> listaCodItem =  null;

		
		try {
			
			listaDescCuotasSepelio  = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TCUOTASMINFONRET));

			// 1. Validamos el nro de cuotas configuradas VS las pagadas...
			if(listaDescCuotasSepelio != null){
				
				// Obtenemos el nro de cuotas de sepelio necesarias desde la tabla parametros y el campo cuconumerocuotas...
				if(listaDescCuotasSepelio != null && !listaDescCuotasSepelio.isEmpty()){
					for (Tabla strDescCuotas : listaDescCuotasSepelio) {
						if(strDescCuotas.getIntIdDetalle().intValue() == beanCaptacion.getIntNumeroCuota().intValue()){
							intNroCuotasDefinidasSepelio = new Integer(strDescCuotas.getStrDescripcion());	
							break;
						}else{
							intNroCuotasDefinidasSepelio = new Integer(0);
						}
					}	
				}

			}
			
			
			// revcueperando el nro de cuotas a partir de la fecha de fallecimiento hacia atras...
			listaMovientosTotal = conceptoFacade.getListaMovimientoPorCuentaEmpresaConcepto(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), 
																							beanSocioComp.getCuenta().getId().getIntCuenta(), 
																							Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
			
			if(listaMovientosTotal!= null){
				
				intNroCuotasPagadasTotal = listaMovientosTotal.size();
				
				for(int m=0; m<listaMovientosTotal.size();m++){
					movimiento = listaMovientosTotal.get(m);
					// MOCC_FECHAMOVIMIENTO_D
					
					if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)){
						if(!movimiento.getTsFechaMovimiento().after(beanExpedientePrevision.getDtFechaFallecimiento())){
							listaMovimientoFiltrada.add(movimiento);
						}
					}else  if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
						Calendar fecHoy = Calendar.getInstance();
								if(!movimiento.getTsFechaMovimiento().after(fecHoy.getTime())){
									listaMovimientoFiltrada.add(movimiento);
								}
					}
					
	
				}
					intNroCuotasPagadas = listaMovimientoFiltrada.size();
			}else{
				intNroCuotasPagadas = 0;	
				intNroCuotasPagadasTotal = 0;
			}
			 
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMM");
			//Calendar calFallecimiento = Calendar.getInstance();
			//calFallecimiento.setTime(beanExpedientePrevision.getDtFechaFallecimiento());
			String strFechaFallec = null;
			if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
				Calendar hoy = Calendar.getInstance();
				 strFechaFallec = sdf3.format(hoy.getTime());
			}else if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)){
				 strFechaFallec = sdf3.format(beanExpedientePrevision.getDtFechaFallecimiento());
			}

			int intMes = Integer.parseInt(strFechaFallec.substring(4));
			int intAnno = Integer.parseInt(strFechaFallec.substring(0,4));
			
			List<String> listaPeriodos = new ArrayList<String>();
			
			for(int c =0; c<intNroCuotasDefinidasSepelio.intValue();c++){
				String strPeriodo = "";
				intMes--;
				if (intMes == 0) {intAnno = intAnno - 1;
					intMes = 12;
				}
				if(intMes <10){
					strPeriodo = intAnno+"0"+intMes;
				}else{
					strPeriodo = ""+intAnno+intMes;
				}
				
				listaPeriodos.add(strPeriodo);
			}
			
			// recorremos los perisdos q  deben estar pagado...
			// SI ES AES SE TOMA all... SIE  SSEPELIO SOLO TOAM LAS ULTIMAS DEFINIDAS
			if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)){
				intNroCuotasPagadas =0;
				for(int a=0;a<listaMovimientoFiltrada.size();a++){
					for(int b=0; b<listaPeriodos.size();b++){
						if(listaMovimientoFiltrada.get(a).getIntPeriodoPlanilla().toString().equals(listaPeriodos.get(b).toString())){
							intNroCuotasPagadas++;	
						}
					}
				}
			}else  if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
				intNroCuotasPagadas =0;
				for(int a=0;a<listaMovimientoFiltrada.size();a++){
					for(int b=0; b<listaPeriodos.size();b++){
						//if(listaMovimientoFiltrada.get(a).getIntPeriodoPlanilla().toString().equals(listaPeriodos.get(b).toString())){
							intNroCuotasPagadas++;	
						//}
					}
				}
			}
			


			if(intNroCuotasDefinidasSepelio.compareTo(intNroCuotasPagadas)>0){
				// strMsgTxtAESCuotasSepelio
				setStrMsgTxtAESCuotasSepelio("Solo tiene "+intNroCuotasPagadas + " cuota(s) cancelada(s) de Sepelio. ");
				setChkCondicionCuotaSepelio(false);
			}else{
				setStrMsgTxtAESCuotasSepelio("");
				setChkCondicionCuotaSepelio(true);
			}

			// 2. 
			
			
						//}else{setStrMsgTxtAESCuotasSepelio("El campo NumeroCuota de la Captación es nulo. No se puede ejecutar la validación de Nro de cuotas de Sepelio. ");}	
					//}else{setStrMsgTxtAESCuotasSepelio(strMsgTxtCaptacionSepelio + "El campo ValorConfiguracion de la Captación es nulo. No se puede ejecutar la validación de Nro de cuotas de Sepelio. ");}

		} catch (Exception e) {
			log.error("Error isValidCuotaSepelio12 -->"+e);
		}
		
		
		return blnCondicionCuotaSepelio;
	}*/
	
	
	/**
	 * Aplica a AES y SEPELIO
	 * Valida las cuotas para SEPELIO segun campos cuco_cuotas y para_tipomaxmincuotas de la captacion.
	 * @return
	 */
	public boolean isValidCondicionCuotasSepelio(){
//		CuentaId cuentaId = null;
		//List<CuentaConcepto> listaCuentaConcepto=null;
		//List<Movimiento> listaMovientosTotal=null;
		//List<Movimiento> listaMovimientoFiltrada=new ArrayList<Movimiento>();
		List<CuentaConceptoDetalle> listaCuentaConceptoDet = null;
		blnCondicionCuotaSepelio = true;
		//BigDecimal bdValorCuota  = BigDecimal.ZERO; 	// Valor de cada cuota de sepelio (S/. 12) (A)
		intNroCuotasDefinidasSepelio = 0; //Constante.PARAM_NUMERO_CUOTAS_SEPELIO; 	// Nro de cuotas que debe aportar el socio (B)
		//BigDecimal bdTotalAporteSocio     = BigDecimal.ZERO;	//  total de aportes del socio en sepelio 
		List<Tabla> listaCuotasSepelio 	  = null;
//		Integer intNroDetalleDiferente 	  = new Integer(0);						// Nro de cuotas que cancelo el socio.
		List<String> listaPeriodos = null;
		CuentaConceptoComp cuentaConceptoCompUtil = null;
		Integer intNroCuotasTotalmentePagadas = new Integer(0);
		BigDecimal bdMontoConfiguradoCuota = BigDecimal.ZERO;
		BigDecimal bdMontoConfiguradoAPagar = BigDecimal.ZERO;
		BigDecimal bdMontoTotalPagado = BigDecimal.ZERO;

		try {

			//PARAM_T_TCUOTASMINFONRET
			listaCuotasSepelio  = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TCUOTASMINFONRET));//PARAM_T_TCUOTASMINFONRET
			
			// Obtenemos el nro de cuotas definidas para sepelio
			//  SI ES AES, SE RECUPERA DE CONFIGURACION
			if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				if(listaCuotasSepelio != null){
					for (Tabla cuotaSepelio : listaCuotasSepelio) {
						if(cuotaSepelio.getIntIdDetalle().compareTo(beanCaptacion.getIntCuota())==0){  //  getIntTiempoSustento() / getIntNumeroCuota
							intNroCuotasDefinidasSepelio = new Integer(cuotaSepelio.getStrDescripcion());
							break;
						}
					}
				}
			
			// SI ES SEPELIO SE SETEA 12 CUOTAS
			}else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
				intNroCuotasDefinidasSepelio = Constante.PARAM_NUMERO_CUOTAS_SEPELIO;
			}
			

			// CORREGIR COMO IDENTIFDICAR EL TIPO DE CUENTAC CONCEPTO SEPELIO.
			// 1. Definimos si existe mas de un tipo de cuenta concepto
			//cuentaConceptoCompUtil = recuperarCuentaConceptoPorTipo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
			cuentaConceptoCompUtil = recuperarCuentaConceptoPorTipo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO); // PARAM_T_CUENTACONCEPTO_RETIRO
			if(cuentaConceptoCompUtil != null){
				listaCuentaConceptoDet = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaConceptoCompUtil.getCuentaConcepto().getId());
					// si existe uno se realizara CASO 1 de lo contrario CASO 2.
					//if(listaCuentaConceptoDet != null){
				//intNroDetalleDiferente = listaCuentaConceptoDet.size();
					//}else{
					//	intNroDetalleDiferente = 0;	
					//}
				
				if(listaCuentaConceptoDet != null && !listaCuentaConceptoDet.isEmpty()){
					// 1.1 Calculamos el monto que debio pagarse hasta el periodo anterior...
					bdMontoConfiguradoCuota = listaCuentaConceptoDet.get(0).getBdMontoConcepto();
					bdMontoConfiguradoAPagar =  bdMontoConfiguradoCuota.multiply(new BigDecimal(intNroCuotasDefinidasSepelio-1));
		
				// 2. Generamos los periodos que deben estar pagados en base a la fecha de falleciemito o  fecha de registro
					SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMM");
					String strFechaFallec = null;
					int intMes = 0;
					int intAnno = 0;
					
					if(intTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))){
						//Calendar hoy = Calendar.getInstance();
						// strFechaFallec = sdf3.format(hoy.getTime());
						 strFechaFallec = strFechaRegistro;
						 intMes = Integer.parseInt(strFechaFallec.substring(3,5));
						 intAnno = Integer.parseInt(strFechaFallec.substring(6));
					}else if(intTipoSolicitud.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))){
						 strFechaFallec = sdf3.format(beanExpedientePrevision.getDtFechaFallecimiento());
						 intMes = Integer.parseInt(strFechaFallec.substring(4));
						 intAnno = Integer.parseInt(strFechaFallec.substring(0,4));
					}
					
					

					// Generamos la lista de los periodos que deberia haber cancelado el socio
					listaPeriodos = new ArrayList<String>();
					for(int c =0; c<intNroCuotasDefinidasSepelio.intValue();c++){
						String strPeriodo = "";
						intMes--;
						if (intMes == 0) {intAnno = intAnno - 1;
							intMes = 12;
						}
						if(intMes <10){
							strPeriodo = intAnno+"0"+intMes;
						}else{
							strPeriodo = ""+intAnno+intMes;
						}
						// se inica con el mas reciente
						listaPeriodos.add(c, strPeriodo);
					}
					
					for (String per : listaPeriodos) {
						log.error("PERIODOS GENERADOS ---> "+per.toString());
					}
					
					// Verificamos en cada peridodo calculado que la suma de los montos en Concepto PAgo
					// y alli se disgregarn los pagos por periodos. Y estos tendrian que ser iguales al monto de la ctacotdetalle correspondianete.
					
					if(listaPeriodos != null && !listaPeriodos.isEmpty()){
						if(cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle() != null
							&& !cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle().isEmpty()){
								
							List<CuentaConceptoDetalle> listCtaCtoDetalle = new ArrayList<CuentaConceptoDetalle>();
							listCtaCtoDetalle = cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle();
							listCtaCtoDetalle = recuperarListaCuentaConceptoDetallePeriodoVigencia(listCtaCtoDetalle);
							
							
								for (CuentaConceptoDetalle ctaCtoDet : listCtaCtoDetalle) {
									List<ConceptoPago> listaConceptoPago = null;
									listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ctaCtoDet.getId());
									if(listaConceptoPago != null && !listaConceptoPago.isEmpty()){
										
										for (ConceptoPago lstCtoPago : listaConceptoPago) {
											log.error("PERIDOS-CTAPAGO"+lstCtoPago.getIntPeriodo().toString());
										}
										
										for (String perido : listaPeriodos) {
											BigDecimal bdSumaPorPeriodo = BigDecimal.ZERO;
											
											for (ConceptoPago conceptoPago : listaConceptoPago) {
												if(conceptoPago.getIntPeriodo().compareTo(Integer.parseInt(perido))==0){
													bdSumaPorPeriodo = bdSumaPorPeriodo.add(conceptoPago.getBdMontoPago());	
													bdMontoTotalPagado = bdMontoTotalPagado.add(conceptoPago.getBdMontoPago());		
												}
											}
											
											if(bdSumaPorPeriodo.compareTo(ctaCtoDet.getBdMontoConcepto())==0){
												intNroCuotasTotalmentePagadas ++;	
											}
										}
									}else{
										strMsgTxtAESCuotasSepelio = "El Socio no posee Concepto Pago para Sepelio.";
										setChkCondicionCuotaSepelio(Boolean.FALSE);	
										
									}
								}
							}
					}

					
					System.out.println("bdMontoTotalPagado ---> "+bdMontoTotalPagado);
					System.out.println("bdMontoConfiguradoAPagar ---> "+bdMontoConfiguradoAPagar);
					
					if(bdMontoTotalPagado.compareTo(bdMontoConfiguradoAPagar)>=0){
						setStrMsgTxtAESCuotasSepelio("");
						setChkCondicionCuotaSepelio(true);
					}else{
						setChkCondicionCuotaSepelio(false);
						setStrMsgTxtAESCuotasSepelio("Solo tiene "+intNroCuotasTotalmentePagadas +" Cuota(s) cancelada(s) de Sepelio. ");
					}
	
				}else{
					strMsgTxtAESCuotasSepelio = "El Socio no posee Cuenta Concepto Detalle para Sepelio.";
					setChkCondicionCuotaSepelio(Boolean.FALSE);	
				}
				
			}else{
				strMsgTxtAESCuotasSepelio = "El Socio no posee Cuenta Concepto Sepelio.";
				setChkCondicionCuotaSepelio(Boolean.FALSE);
	
			}

		} catch (Exception e) {
			log.error("Error isValidCuotaSepelio12 -->"+e);
		}

		return blnCondicionCuotaSepelio;
	}
	
	
	
	/**
	 * Genrea las fechas reales de viegncia de las cuenta concepto detalle.
	 * @param listaCtaCtoDet
	 * @return
	 */
	public List<CuentaConceptoDetalle> recuperarListaCuentaConceptoDetallePeriodoVigencia (List<CuentaConceptoDetalle> listaCtaCtoDet){
		List<CuentaConceptoDetalle>  listaCtaCtoDetPeriodo = null;
		
		try {
			
			if(listaCtaCtoDet != null && !listaCtaCtoDet.isEmpty()){
				//Ordenamos los subtipos por int
				Collections.sort(listaCtaCtoDet, new Comparator<CuentaConceptoDetalle>(){
					public int compare(CuentaConceptoDetalle uno, CuentaConceptoDetalle otro) {
						return uno.getId().getIntItemCtaCptoDet().compareTo(otro.getId().getIntItemCtaCptoDet());
					}
				});
				
				for (CuentaConceptoDetalle ctaCtoDetPeriodo : listaCtaCtoDet) {
					System.out.println("getIntItemCtaCptoDet ---> "+ctaCtoDetPeriodo.getId().getIntItemCtaCptoDet());
					System.out.println("getTsInicio ---> "+ctaCtoDetPeriodo.getTsInicio());
					System.out.println("getTsFin ---> "+ctaCtoDetPeriodo.getTsFin());
				}
				

				listaCtaCtoDetPeriodo = new ArrayList<CuentaConceptoDetalle>();
				
				// Recorremos la lista de ctactodetalle para calcular su inicio y fin alternativos
				for (int i = 0; i < listaCtaCtoDet.size(); i++) {
					CuentaConceptoDetalle ctaCtoDetPeriodo = new CuentaConceptoDetalle();
					ctaCtoDetPeriodo = listaCtaCtoDet.get(i);
					Calendar calendarInicio = null;
					Calendar calendarFin= null;
					
					if(i == 0){
						if(ctaCtoDetPeriodo.getTsInicio() != null){
							calendarInicio = Calendar.getInstance();
							calendarInicio.setTimeInMillis(ctaCtoDetPeriodo.getTsInicio().getTime());
							ctaCtoDetPeriodo.setTsInicioVigenciaTemp(new Timestamp(getPrimerDiaDelMes(calendarInicio).getTime()));	
						}

						if(ctaCtoDetPeriodo.getTsFin() != null){
							calendarFin = Calendar.getInstance();
							calendarFin.setTimeInMillis(ctaCtoDetPeriodo.getTsFin().getTime());
							ctaCtoDetPeriodo.setTsFinVigenciaTemp(new Timestamp(getUltimoDiaDelMes(calendarFin).getTime()));
						}
						
						//listaCtaCtoDetPeriodo.add(ctaCtoDetPeriodo);	
					
					} else{
						
						if(ctaCtoDetPeriodo.getTsInicio() != null){
							calendarInicio = Calendar.getInstance();
							calendarInicio.setTimeInMillis(ctaCtoDetPeriodo.getTsInicio().getTime());

							Integer dia = calendarInicio.get(Calendar.DATE);
							if(dia.compareTo(1)==0){
								ctaCtoDetPeriodo.setTsInicioVigenciaTemp(new Timestamp(getPrimerDiaDelMes(calendarInicio).getTime()));
							}else{
								calendarInicio.add(java.util.Calendar.MONTH, 1);
								ctaCtoDetPeriodo.setTsInicioVigenciaTemp(new Timestamp(getPrimerDiaDelMes(calendarInicio).getTime()));		
							}
						}

						if(ctaCtoDetPeriodo.getTsFin() != null){
							calendarFin = Calendar.getInstance();
							calendarFin.setTimeInMillis(ctaCtoDetPeriodo.getTsFin().getTime());
							ctaCtoDetPeriodo.setTsFinVigenciaTemp(new Timestamp(getUltimoDiaDelMes(calendarFin).getTime()));
						}
						//listaCtaCtoDetPeriodo.add(ctaCtoDetPeriodo);	
					}
					listaCtaCtoDetPeriodo.add(ctaCtoDetPeriodo);
				}	
			}

		} catch (Exception e) {
			log.error("Error en recuperarListaCuentaConceptoDetallePeriodoVigencia ---> ");
		}
		return listaCtaCtoDetPeriodo;

	}
	
	
	
	/**
	 * Genera una lista string con los periodos a validarse, segun numero de cuotas a validar.
	 */
	public List<String> generarListaPeridos(Integer intTipoPrevision, Integer intNroCuotas, String strFecha){
		SimpleDateFormat sdf3 = null;
		String strFechaFallec = null;
		List<String> listaPeriodos = null;
		
		try {
			
			sdf3 = new SimpleDateFormat("yyyyMM");
			if(intTipoPrevision.equals(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))){
				//Calendar hoy = Calendar.getInstance();
				// strFechaFallec = sdf3.format(hoy.getTime());
				 strFechaFallec = strFecha;
			}else if(intTipoPrevision.equals(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO)){
				 strFechaFallec = sdf3.format(beanExpedientePrevision.getDtFechaFallecimiento());
			}
			
			int intMes = Integer.parseInt(strFechaFallec.substring(3,5));
			int intAnno = Integer.parseInt(strFechaFallec.substring(6));

			listaPeriodos = new ArrayList<String>();
			// Generamos la lista de los periodos que deberia haber cancelado el socio
			for(int c =0; c<intNroCuotas.intValue();c++){
				String strPeriodo = "";
				intMes--;
				if (intMes == 0) {intAnno = intAnno - 1;
					intMes = 12;
				}
				if(intMes <10){
					strPeriodo = intAnno+"0"+intMes;
				}else{
					strPeriodo = ""+intAnno+intMes;
				}
				// se inica con el mas reciente
				listaPeriodos.add(c, strPeriodo);
			}
			
			
		} catch (Exception e) {
			log.error("Error en generarListaPeridos ---> "+e);
		}
		return listaPeriodos;	
	}
	
	
	/**
	 * Recupera la cuenta concepto del socio. 
	 * @param intCuentaConcepto
	 */
	public CuentaConceptoComp recuperarCuentaConceptoPorTipo(Integer intCuentaConcepto){
//		Boolean blnContinuaAporte = Boolean.TRUE;
//		Boolean blnContinuaRetiro = Boolean.TRUE;
		List<CuentaConcepto> listaCuentaConcepto= null;
//		List<CuentaConceptoComp> listaCuentaConceptoComp = null;
		CuentaConceptoComp cuentaConceptoComp = null;
		
		try {

			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(beanSocioComp.getCuenta().getId());

			// Solo se deben visualizar 4 cuentas: Aporte, Retiro, Ahoroo y Depaosito
//			listaCuentaConceptoComp = new ArrayList<CuentaConceptoComp>();

			for (CuentaConcepto cuentaConcepto : listaCuentaConcepto) {
				
				CuentaConceptoDetalle detalle = null;
				
				if(cuentaConcepto.getListaCuentaConceptoDetalle() != null 
				&& !cuentaConcepto.getListaCuentaConceptoDetalle().isEmpty()){
					detalle = new CuentaConceptoDetalle();
					detalle = cuentaConcepto.getListaCuentaConceptoDetalle().get(0);

					if(detalle.getIntParaTipoConceptoCod().compareTo(intCuentaConcepto)==0){
						cuentaConceptoComp = new CuentaConceptoComp();
						cuentaConceptoComp.setCuentaConcepto(cuentaConcepto);
						break;
					}
				}	
			}
		}
			 catch (Exception e) {
			log.error("Error en recuperarCuentaConceptoPorTipo ---> "+e);
		}	
		return cuentaConceptoComp;
	}
	
	
	
	/**
	 * SOLO ES INFORMATIVO !!!!!!!
	 * VAlida el numero de cuotas para RETIRO. 
	 * Valida en PAaagoCtaConcepto q el saldo sea 0.00 y q el monto copincida con el de su detalle.
	 * @return
	 */
	
	public boolean isValidCondicionCuotasRetiro(){
		//CuentaId cuentaId = null;
//		List<CuentaConceptoDetalle> listaCuentaConceptoDet = null;
		blnCondicionCuotaRetiro = true;
		intNroCuotasDefinidasRetiro = 0; //Constante.PARAM_NUMERO_CUOTAS_SEPELIO; 	// Nro de cuotas que debe aportar el socio (B)
		List<Tabla> listaCuotasRetiro 	  = null;
		//Integer intNroDetalleDiferente 	  = new Integer(0);						// Nro de cuotas que cancelo el socio.
		//List<String> listaPeriodos = null;
		CuentaConceptoComp cuentaConceptoCompUtil = null;
		Integer intNroCuotasTotalmentePagadas = new Integer(0);
		//BigDecimal bdMontoConfiguradoCuota = BigDecimal.ZERO;
		//BigDecimal bdMontoConfiguradoAPagar = BigDecimal.ZERO;
		//BigDecimal bdMontoTotalPagado = BigDecimal.ZERO;

		try {

			//PARAM_T_TCUOTASMINFONRET
			listaCuotasRetiro  = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TCUOTASMINFONRET));//PARAM_T_TCUOTASMINFONRET
			
			// Obtenemos el nro de cuotas definidas para retiro
			if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
				if(listaCuotasRetiro != null){
					for (Tabla cuotaRetiro : listaCuotasRetiro) {
						if(cuotaRetiro.getIntIdDetalle().compareTo(beanCaptacion.getIntNumeroCuota())==0){ 
						//if(cuotaRetiro.getIntIdDetalle().compareTo(beanCaptacion.getIntCuota())==0){  //  getIntTiempoSustento() / getIntNumeroCuota
							intNroCuotasDefinidasRetiro = new Integer(cuotaRetiro.getStrDescripcion());
							break;
						}
						
					}
				}

			// 1. Definimos si existe mas de un tipo de cuenta concepto
			cuentaConceptoCompUtil = recuperarCuentaConceptoPorTipo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
			if(cuentaConceptoCompUtil != null){
//					listaCuentaConceptoDet = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaConceptoCompUtil.getCuentaConcepto().getId());
					
					if(cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle() != null
						&& !cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle().isEmpty()){
							
						List<CuentaConceptoDetalle> listCtaCtoDetalle = new ArrayList<CuentaConceptoDetalle>();
						listCtaCtoDetalle = cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle();
						
						// cargamos las fechas temporales para el funcionamiento de la validacion.
						listCtaCtoDetalle = recuperarListaCuentaConceptoDetallePeriodoVigencia(listCtaCtoDetalle);

							for (CuentaConceptoDetalle ctaCtoDet : listCtaCtoDetalle) {
								List<ConceptoPago> listaConceptoPago = null;
								listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ctaCtoDet.getId());
								if(listaConceptoPago != null && !listaConceptoPago.isEmpty()){

										for (ConceptoPago conceptoPago : listaConceptoPago) {
											// Se valida que el saldo sea cero y ademas q el monto pagado sea el mismo
											// que el monto concepto del detalle
											if(conceptoPago.getBdMontoSaldo().compareTo(BigDecimal.ZERO)==0 
												&& conceptoPago.getBdMontoPago().compareTo(ctaCtoDet.getBdMontoConcepto())==0){
												intNroCuotasTotalmentePagadas ++;		
											}
										}

								}
							}
						}

					intNroCuotasPagadasTotal = new Integer(0);
					intNroCuotasPagadasTotal = intNroCuotasTotalmentePagadas;
					/*System.out.println("bdMontoTotalPagado ---> "+bdMontoTotalPagado);
				System.out.println("bdMontoConfiguradoAPagar ---> "+bdMontoConfiguradoAPagar);*/
				//setStrMsgTxtAESCuotasSepelio("");
				setChkCondicionCuotaRetiro(true);
				blnCondicionCuotaRetiro = true;
				
				//setStrMsgTxtCuotasRetiro("El socio tiene "+intNroCuotasTotalmentePagadas +" Cuota(s) cancelada(s) de Retiro.");
				//setStrMsgTxtCuotasRetiro("Solo tiene "+bdValorNumeroCuotas.intValue() +" Cuota(s) cancelada(s) de Retiro.");
					
			}else{
					strMsgTxtCuotasRetiro = "El Socio no posee Cuenta Concepto Retiro.";
					setChkCondicionCuotaRetiro(Boolean.FALSE);
					blnCondicionCuotaRetiro = false;
				}
			}

		} catch (Exception e) {
			log.error("Error isValidCondicionCuotasRetiro -->"+e);
		}

		return blnCondicionCuotaRetiro;
	}
	
	
	
	/**
	 * Evalua las Condiciones y Conceptos de la Captacion recuperada.
	 * @return
	 */
	public boolean validarCaptacionRecuperada(){
		
		//beanCaptacion.getListaConcepto();
		//beanCaptacion.getListaCondicion();
		// intSubTipoSolicitud;
		Boolean blnContinua = Boolean.TRUE;
		try {
			blnValidacionCaptacionRecuperada = true;
			if(beanCaptacion != null){
				
				// Validamos los conceptos
				if(beanCaptacion.getListaConcepto() != null && !beanCaptacion.getListaConcepto().isEmpty()){
					for (Concepto conceptoCaptacion : beanCaptacion.getListaConcepto()) {
						
						if(conceptoCaptacion.getId().getIntParaTipoConcepto().compareTo(intSubTipoSolicitud)==0){
							
							if(blnContinua){
								blnContinua = Boolean.FALSE;
								BigDecimal bdMontoConfSol = new BigDecimal(conceptoCaptacion.getIntMonto());
								BigDecimal bdDiasHosp = BigDecimal.ZERO;
								BigDecimal bdMontoTotal = BigDecimal.ZERO;

								if(conceptoCaptacion.getIntDia() != null && conceptoCaptacion.getIntDia().compareTo(0)!=0){
									bdDiasHosp = new BigDecimal(conceptoCaptacion.getIntDia());	
								}

								System.out.println("intTipoSolicitud ---> "+ intTipoSolicitud);
								System.out.println("intSubTipoSolicitud ---> "+ intSubTipoSolicitud);
								System.out.println("-----------------------------------------------------------------");
								
							// Si es AES, se valida montos teniendo en cuenta tiempo de ehospotalizacion.
							// Solo en el caso de AES-Hospitalizacion. Se multiplica el monto x dias
								if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
									if (intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_AES_HOSPITALIZACION)==0){
										bdMontoTotal = bdDiasHosp.multiply(bdMontoConfSol);
										bdMontoTotal = bdMontoTotal.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
										
										//------------------------------------------------------------------------------------------------->
										if(conceptoCaptacion.getIntTipoMaxMinCod().compareTo(Constante.PARAM_T_MINIMO)==0){										
											if(bdMontoTotal.compareTo(beanExpedientePrevision.getBdMontoBrutoBeneficio())<= 0){
												setStrMsgTxtCaptacionConcepto("");
											}else{
												blnValidacionCaptacionRecuperada = false;
												setStrMsgTxtCaptacionConcepto("El Monto solicitado es Menor al Monto configurado. Monto Configurado: S/. "+bdMontoTotal);
											}
											
										}else if(conceptoCaptacion.getIntTipoMaxMinCod().compareTo(Constante.PARAM_T_MAXIMO)==0){
													if(bdMontoTotal.compareTo(beanExpedientePrevision.getBdMontoBrutoBeneficio())>=0){
														setStrMsgTxtCaptacionConcepto("");
													}else{
														blnValidacionCaptacionRecuperada = false;
														setStrMsgTxtCaptacionConcepto("El Monto solicitado es Mayor al Monto configurado. Monto Configurado: S/. "+bdMontoTotal);
													}
											}
										//------------------------------------------------------------------------------------------------->
									}else{
										if(conceptoCaptacion.getIntTipoMaxMinCod().compareTo(Constante.PARAM_T_MINIMO)==0){
											if(bdMontoConfSol.compareTo(beanExpedientePrevision.getBdMontoBrutoBeneficio())<= 0){
												setStrMsgTxtCaptacionConcepto("");
											}else{ blnValidacionCaptacionRecuperada = false;
													setStrMsgTxtCaptacionConcepto("El Monto solicitado es Menor al Monto configurado. Monto Configurado: S/. "+bdMontoConfSol);
											}
										}else if(conceptoCaptacion.getIntTipoMaxMinCod().compareTo(Constante.PARAM_T_MAXIMO)==0){
													if(bdMontoConfSol.compareTo(beanExpedientePrevision.getBdMontoBrutoBeneficio())>=0){
														setStrMsgTxtCaptacionConcepto("");
													}else{ blnValidacionCaptacionRecuperada = false;
															setStrMsgTxtCaptacionConcepto("El Monto solicitado es Mayor al Monto configurado. Monto Configurado: S/. "+bdMontoConfSol);
													}
										}
									}
								
								// Si es no es AES, se valida montos
								}else{
										if(conceptoCaptacion.getIntTipoMaxMinCod().compareTo(Constante.PARAM_T_MINIMO)==0){
											if(bdMontoConfSol.compareTo(beanExpedientePrevision.getBdMontoBrutoBeneficio())<= 0){
												setStrMsgTxtCaptacionConcepto("");
											}else{ blnValidacionCaptacionRecuperada = false;
													setStrMsgTxtCaptacionConcepto("El Monto solicitado es Menor al Monto configurado. Monto Configurado: S/. "+bdMontoConfSol);
											}
										}else if(conceptoCaptacion.getIntTipoMaxMinCod().compareTo(Constante.PARAM_T_MAXIMO)==0){
													if(bdMontoConfSol.compareTo(beanExpedientePrevision.getBdMontoBrutoBeneficio())>=0){
														setStrMsgTxtCaptacionConcepto("");
													}else{ blnValidacionCaptacionRecuperada = false;
															setStrMsgTxtCaptacionConcepto("El Monto solicitado es Mayor al Monto configurado. Monto Configurado: S/. "+bdMontoConfSol);
													}
											}
								}
							}
							
						}
						
					}
				}
				
				// Validamos la condicion 
				if(beanCaptacion.getListaCondicion() != null){
					// Validamos los conceptos
					List<Condicion> listaCondicion = null;
					listaCondicion = beanCaptacion.getListaCondicion();
					Boolean blnCOntinuaCond = Boolean.TRUE;
					for(int j=0; j<listaCondicion.size();j++){
						
						if(blnCOntinuaCond){
							
							Condicion condicion = null;
							condicion = listaCondicion.get(j);
							
							if(condicion != null){
								Integer intValor = null;
								if(condicion.getId().getIntParaCondicionSocioCod().compareTo(beanSocioComp.getCuenta().getIntParaCondicionCuentaCod())==0){
									intValor = condicion.getIntValor();
									if(intValor == 0){
										setStrMsgTxtCaptacionCondicion("La condición del socio no cumple con la configuración registrada.");
										blnValidacionCaptacionRecuperada = false;
										blnCOntinuaCond = Boolean.FALSE;
										break;
									}else{
										setStrMsgTxtCaptacionCondicion("");
									}
								}
							}	
						}
					}	
				}	else{
					blnValidacionCaptacionRecuperada = false;
					setStrMsgTxtCaptacionCondicion("La captacion "+ beanCaptacion.getId().getIntItem() + ". No posee COndiciones." );
				}

			}else{
				blnValidacionCaptacionRecuperada = false;
			}
			
		} catch (Exception e) {
			log.error("Error en validarCaptacionRecuperada ---> "+e);
		}
	
		return blnValidacionCaptacionRecuperada;
	}
	
	
	/**
	 * Aplica a AES. 
	 * Valida que el socio tenga una deuda existente. 
	 * Recupera los tipos de credito  (1)Prestamo (2)Orden de credito y (5)refinanciamiento.
	 * @return
	 */
	public boolean isValidCondicionDebeTenerDeuda(){
		List<Expediente> listaExpedientesMovimiento = null;
		//boolean blnAESDebeTenerDeuda = true;
		blnCondicionDebeTenerDeuda = true;
		strMsgTxtAESDeudaExistente = "";
		try {
			
			//regenerarCombos();
			listaExpedientesMovimiento = conceptoFacade.getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), 
																									beanSocioComp.getCuenta().getId().getIntCuenta());
			
			if(listaExpedientesMovimiento == null || listaExpedientesMovimiento.size()==0){
				//blnAESDebeTenerDeuda=false;
				blnCondicionDebeTenerDeuda = false;
				strMsgTxtAESDeudaExistente = "El Socio No tiene ninguna deuda. ";
				setChkCondicionDebeTenerDeuda(false);
			}else{
				setChkCondicionDebeTenerDeuda(true);
				strMsgTxtAESDeudaExistente = "";
				
			}

		} catch (BusinessException e) {
			log.error("Error en isValidCondicionDebeTenerDeuda --->"+e);
		}
		return blnCondicionDebeTenerDeuda;
	}
	
	

	/**
	 * 
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		mostrarBoton = false;
		mostrarBotonUno = false;
		mostrarBotonDos = false;
		try{
			registroSeleccionado = (ExpedientePrevisionComp)event.getComponent().getAttributes().get("itemExpedientes");

			validarOperacionActualizar();
			validarOperacionEliminar();
			//log.info("reg selec:"+registroSeleccionadoBusqueda);	
			//Integer intEstado = registroSeleccionadoBusqueda.getEstadoPrevisionUltimo().getIntParaEstado();
			/*public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO = 1;
			public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD = 2;
			public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO = 3;
			public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_APROBADO = 4;
			public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO = 5;
			public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_GIRADO = 6;
			public static final Integer PARAM_T_ESTADOSOLICPRESTAMO_ANULADO = 7;
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
				blnBotonActulizar = true;
				blnBotonVer = true;
				blnBotonEliminar = true;
			}
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0){
				blnBotonActulizar = true;
				blnBotonVer = true;
				blnBotonEliminar = false;
			}
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)==0){
				blnBotonActulizar = false;
				blnBotonVer = true;
				blnBotonEliminar = false;
			}*/
			//888

			Integer numero = registroSeleccionado.getExpedientePrevision().getIntParaTipoCaptacion();
			switch(numero){
			case 11:
			{
				if(registroSeleccionado.getExpedientePrevision().getIntParaTipoCaptacion()==11){
					mostrarBoton = Boolean.TRUE;
				}else{
					mostrarBoton = Boolean.FALSE;
				}
				break;
			}
			case 3:
			{
				if(registroSeleccionado.getExpedientePrevision().getIntParaTipoCaptacion()==3){
					mostrarBotonDos = Boolean.TRUE;
				}else{
					mostrarBotonDos = Boolean.FALSE;
				}
				break;
			}
			case 2:
			{
				if(registroSeleccionado.getExpedientePrevision().getIntParaTipoCaptacion()==2){
					mostrarBotonUno = Boolean.TRUE;
				}else{
					mostrarBotonUno = Boolean.FALSE;
				}
				break;
			}
		}
			validarEstadoCuenta(registroSeleccionado.getExpedientePrevision().getId());
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * Valida si la operacion ELIMINAR se visualiza o no en el popup de acciones.
	 * Solo se podra eliminar si es Requisito.
	 */
	public void validarOperacionEliminar(){
		Integer intUltimoEstado = 0;
		
		try {
			if(registroSeleccionado != null){
				//intUltimoEstado = registroSeleccionadoBusqueda.getEstadoPrevisionUltimo().getIntParaEstado();
				registroSeleccionado.getExpedientePrevision().getIntEstadoCreditoUltimo();
				
				if(intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
					blnBotonEliminar = Boolean.TRUE;
				}else{
					blnBotonEliminar = Boolean.FALSE;
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
			if(registroSeleccionado != null){
				//intUltimoEstado = registroSeleccionadoBusqueda.getEstadoPrevisionUltimo().getIntParaEstado();
				intUltimoEstado = registroSeleccionado.getExpedientePrevision().getIntEstadoCreditoUltimo();
				if(intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
						|| intUltimoEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0 ){
					blnBotonActulizar = Boolean.TRUE;
				}else{
					blnBotonActulizar = Boolean.FALSE;
				}	
			}
		} catch (Exception e) {
			log.error("Error en validarOperacionEliminar ---> "+e);
		}

	}
	
	
	
	/**
	 * Valida la Situacion de la cuenta del expediente seleccionado, a find e permitir solo los de Estado ACTIVO.
	 * Devuelve TRUE si el estadod e la cuenta NO ES ACTIVO.
	 * @return
	 */
	public Boolean validarEstadoCuenta(ExpedientePrevisionId expId){
		Boolean blnValido= null;
		CuentaFacadeRemote cuentaFacade = null;
		CuentaId ctaIdExp = null;
		Cuenta ctaExpediente = null;
		strMensajeValidacionCuenta = "";
		
		try {
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			if(expId != null){
				ctaIdExp = new CuentaId();
				ctaIdExp.setIntCuenta(expId.getIntCuentaPk());
				ctaIdExp.setIntPersEmpresaPk(expId.getIntPersEmpresaPk());
				
				ctaExpediente = cuentaFacade.getListaCuentaPorPkTodoEstado(ctaIdExp);
				if(ctaExpediente != null){
					if(ctaExpediente.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						blnValido = Boolean.FALSE;
						blnBloquearXCuenta =  Boolean.FALSE;
						strMensajeValidacionCuenta = "";
					}else{
						blnBloquearXCuenta =  Boolean.TRUE;
						blnValido = Boolean.TRUE;
						strMensajeValidacionCuenta = "La Solicitud de Previsión pertenece a una Cuenta No Vigente. No se puede realizar alguna operación.";
					}
				}

			}
			
			
		} catch (Exception e) {
			log.error("Error en validarEstadoCuenta ----> "+e);
		}
		return blnValido;
	}
	
	/**
	 * 
	 * @param event
	 */
	public void seleccionarBeneficiario(ActionEvent event){
		try{
			campoBuscarBeneficiario ="";
			//intTipoSolicitud = intTipoSolicitudExtra;
			//intSubTipoSolicitud = intSubTipoSolicitudExtra;
			beneficiarioSeleccionado = (BeneficiarioPrevision)event.getComponent().getAttributes().get("itemBeneficiariosPopup");
			setStrMsgTxtAgregarBeneficiario("");
			//procedeEvaluacion(beanExpedientePrevision);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void seleccionarFallecido(ActionEvent event){
		try{
			campoBuscarFallecido = "";
			fallecidoSeleccionado = (FallecidoPrevision)event.getComponent().getAttributes().get("itemFallecidoPopup");
			//intTipoSolicitud = intTipoSolicitudExtra;
			//intSubTipoSolicitud = intSubTipoSolicitudExtra;
			setStrMsgTxtAgregarBeneficiario("");
			//procedeEvaluacion(beanExpedientePrevision);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			}
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void irModificarSolicitudPrevisionAutoriza(ActionEvent event){	
		//cancelarGrabarSolicitud(event);
		AutorizacionPrevisionController autorizacionPrevision = (AutorizacionPrevisionController) getSessionBean("autorizacionPrevisionController");
		autorizacionPrevision.irModificarSolicitudPrevisionAutoriza(event);
		
	}
	
	/**
	 * 
	 * @param event
	 * @param registroSeleccionadoAut
	 */
	
	public void irVerSolicitudPrevision(ActionEvent event){	
		cancelarGrabarSolicitud(event);
		strSolicitudPrevision = Constante.MANTENIMIENTO_NINGUNO;
		blnMostrarDescripcionTipoPrevision = Boolean.TRUE;
		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
		List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;
		ExpedientePrevisionId expedientePrevisionId = null;
		Cuenta cuentaExpediente = null;
		
		try {
			expedientePrevisionId = new ExpedientePrevisionId();
			expedientePrevisionId.setIntPersEmpresaPk(registroSeleccionado.getExpedientePrevision().getId().getIntPersEmpresaPk());
			expedientePrevisionId.setIntCuentaPk(registroSeleccionado.getExpedientePrevision().getId().getIntCuentaPk());
			expedientePrevisionId.setIntItemExpediente(registroSeleccionado.getExpedientePrevision().getId().getIntItemExpediente());
			// devuelve el crongrama son id vacio.
			beanExpedientePrevision = previsionFacade.getExpedientePrevisionCompletoPorIdExpedientePrevision(expedientePrevisionId);
			
			if (beanExpedientePrevision != null) {
				// Seteamos los valores en combos de tipo y subtipo
				intTipoSolicitud = beanExpedientePrevision.getIntParaTipoCaptacion();
				intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
				cargarDescripcionTipoPrevision();
					// Recuperamos al Socio 
						if(beanExpedientePrevision.getId() != null){
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = new Cuenta();
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
							cuentaSocio.setId(cuentaIdSocio);
							cuentaSocio = cuentaFacade.getCuentaPorIdCuenta(cuentaSocio);
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
										cuentaExpediente.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
										cuentaExpediente.getId().setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
										
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
										for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
											if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
												socioComp.getSocio().setSocioEstructura(socioEstructura);
											}
										}
										beanSocioComp = socioComp;
										recuperarCuentasConceptoSocio(beanSocioComp);
										mostrarlistaAutorizacionesPrevias(beanExpedientePrevision.getListaEstadoPrevision());
									}
								}	
							//}	
						}
					
						// colocar las UNIDADES EJECUTORAS correlativas...
						//---------------------------------------------------------------------------------------------------->
						cargarDescripcionUEjecutorasConcatenadas(beanSocioComp);

					// recuperamos estados
					if (beanExpedientePrevision.getEstadoPrevisionPrimero() != null) {
						long lnFechaEstadoUno = beanExpedientePrevision.getEstadoPrevisionPrimero().getTsFechaEstado().getTime();
						//configurarCampos(beanExpedienteCredito);
						dtFechaRegistro = new Date(lnFechaEstadoUno);
						strFechaRegistro = Constante.sdf.format(dtFechaRegistro);

					}
					
				if (beanExpedientePrevision.getListaRequisitoPrevisionComp()!= null) {
					
					listaRequisitoPrevisionComp = beanExpedientePrevision.getListaRequisitoPrevisionComp();
				}

				if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0 ){
						//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_AES;
						//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
						listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));
						blnIsAES = true;
						blnIsSepelio = false;
						blnIsRetiro = false;
						blnBeneficiarioNormal = true;
						blnBeneficiarioSepelio = false;
						blnBeneficiarioRetiro = false;
	
				} else if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO)==0 ){
							//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_SEPELIO;
							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));
							//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
							blnIsAES = false;
							blnIsSepelio = true;
							blnIsRetiro = false;
							blnBeneficiarioNormal = false;
							blnBeneficiarioSepelio = true;
							blnBeneficiarioRetiro = false;
							
							if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
								blnIsSepelioTitular = true;
							}
							
				} else if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
							//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_RETIRO;	
							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));
							//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
							blnIsAES = false;
							blnIsSepelio = false;
							blnIsRetiro = true; 
							blnBeneficiarioNormal = false;
							blnBeneficiarioSepelio = false;
							blnBeneficiarioRetiro = true;
				}
				Collections.sort(listaSubTipoSolicitud, new Comparator<Tabla>(){
					public int compare(Tabla uno, Tabla otro) {
						return uno.getIntOrden().compareTo(otro.getIntOrden());
					}
				});
				blnDeshabilitar = true;


				CaptacionId captacionId = new CaptacionId();
				captacionId.setIntPersEmpresaPk(beanExpedientePrevision.getIntPersEmpresa());
				captacionId.setIntParaTipoCaptacionCod(beanExpedientePrevision.getIntParaTipoCaptacion());
				captacionId.setIntItem(beanExpedientePrevision.getIntItem());

							listaCaptacion = captacionFacade.getCaptacionPorPKOpcional(captacionId);
							if(listaCaptacion!=null){		

								for(int k=0; k<listaCaptacion.size();k++){
									if((listaCaptacion.get(k).getId().getIntItem().compareTo(beanExpedientePrevision.getIntItem())==0)
										&&	(listaCaptacion.get(k).getId().getIntPersEmpresaPk().compareTo(beanExpedientePrevision.getIntPersEmpresa())==0)
										&&	(listaCaptacion.get(k).getId().getIntParaTipoCaptacionCod().compareTo(beanExpedientePrevision.getIntParaTipoCaptacion())==0)
										){
											beanCaptacion = listaCaptacion.get(k);
											blnCaptacion = true;
										}
								
								}
							}
							
							if(beanCaptacion!=null){
								//for(int k=0; k<listaCaptacion.size();k++){
									List<Concepto> listaConceptos = null;
									List<Condicion> listaCondiciones = null;
									CaptacionId id = new CaptacionId();
									id = beanCaptacion.getId();
									listaConceptos=captacionFacade.getListaConceptoPorPKCaptacion(id);
									listaCondiciones = condicionFacade.listarCondicion(id);
										//captacionFacade.getListaPorPKCaptacion(listaCaptacion.get(k).getId());
									//(arg0)   condicionFacade.listarCondicion(listaCaptacion.get(k).getId());
									
									if(listaConceptos != null){
										beanCaptacion.setListaConcepto(listaConceptos);
									}
									if(listaCondiciones != null){
										beanCaptacion.setListaCondicion(listaCondiciones);
									}
								
								//}
							}
							
				
							
				// parche Convertir Fallecidos a beneficiarios (solo visual)
				if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
					if(beanExpedientePrevision.getListaFallecidoPrevision()!= null
						&& !beanExpedientePrevision.getListaFallecidoPrevision().isEmpty()){
						List<BeneficiarioPrevision> listaBeneficiariosAES = null;
						
						listaBeneficiariosAES = regenerarBeneficiariosXPersonaMotivoAES(beanExpedientePrevision.getListaFallecidoPrevision());
						if(listaBeneficiariosAES != null & !listaBeneficiariosAES.isEmpty()){
							beanExpedientePrevision.setListaBeneficiarioPrevision(listaBeneficiariosAES);
							beanExpedientePrevision.getListaFallecidoPrevision().clear();	
						}
						
					}	
				}		
							
			
				// Recuperando los beneficiarios
				if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null){
					
					for(int k=0; k<beanExpedientePrevision.getListaBeneficiarioPrevision().size();k++){
						
						intIdPersona = beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntPersPersonaBeneficiario();
						
						persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
						// Recuperamos su dni
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
							
							
							// Recuperamos su tipo de relacion, en base a la lista de vinculos del socio.
							PersonaEmpresaPK personaEmpresaId = new PersonaEmpresaPK();
							personaEmpresaId.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
							personaEmpresaId.setIntIdPersona(socioComp.getPersona().getIntIdPersona());
							List<Vinculo> listaVinculos = null;
							listaVinculos = personaFacade.getVinculoPorPk(personaEmpresaId);
							// cgd - 12.012014
							if(!blnIsSepelioTitular){
								// solo recupera un beneficiario (el titular), x lo tanto nos e valida y setea.
								Vinculo vinculoTitular = new Vinculo();
								vinculoTitular = listaVinculos.get(0);
								beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setIntTipoViculo(vinculoTitular.getIntTipoVinculoCod());
							}else{
								for (Vinculo vinculo2 : listaVinculos) {
								if(beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntItemViculo().compareTo(vinculo2.getIntItemVinculo())==0){
									beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setIntTipoViculo(vinculo2.getIntTipoVinculoCod());
									break;
								}
								}
							}
							/*if(listaVinculos != null && !listaVinculos.isEmpty()){
								
								for (Vinculo vinculo2 : listaVinculos) {
									if(beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntItemViculo().compareTo(vinculo2.getIntItemVinculo())==0){
										beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setIntTipoViculo(vinculo2.getIntTipoVinculoCod());
										break;
									}
								}
							}*/
							beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setPersona(persona);

						}
					}	
				}	
				
				
			
				if(beanExpedientePrevision.getListaFallecidoPrevision() != null){
					//	|| !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
				//if(!beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
											//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
					for(int k=0; k<beanExpedientePrevision.getListaFallecidoPrevision().size();k++){
						
						intIdPersona = beanExpedientePrevision.getListaFallecidoPrevision().get(k).getIntPersPersonaFallecido();
						
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
							
							
							
							// Recuperamos su tipo de relacion, en base a la lista de vinculos del socio.
							PersonaEmpresaPK personaEmpresaId = new PersonaEmpresaPK();
							personaEmpresaId.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
							personaEmpresaId.setIntIdPersona(socioComp.getPersona().getIntIdPersona());
							List<Vinculo> listaVinculos = null;
							listaVinculos = personaFacade.getVinculoPorPk(personaEmpresaId);
							if(listaVinculos != null && !listaVinculos.isEmpty()){
								
								for (Vinculo vinculo2 : listaVinculos) {
									if(beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntItemViculo().compareTo(vinculo2.getIntItemVinculo())==0){
										beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setIntTipoViculo(vinculo2.getIntTipoVinculoCod());
										break;
									}
								}
							}
							beanExpedientePrevision.getListaFallecidoPrevision().get(k).setPersona(persona);
						}
					}	
				}

				cargarListaTablaSucursal();
				seleccionarSucursal();
				evaluarPrevisionMod(event);
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

		blnShowDivFormSolicitudPrevision = true;
		pgValidDatos = false;
		blnDatosSocio = true;
}


/*	public void irModificarSolicitudPrevisionAutoriza2(ActionEvent event,ExpedientePrevision registroSeleccionadoAut){	
		//cancelarGrabarSolicitud(event);
		
		//autorizacionPrevision.irModificarSolicitudPrevisionAutoriza(event);
		
		strSolicitudPrevision = Constante.MANTENIMIENTO_MODIFICAR;
		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
		List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;

		ExpedientePrevisionId expedientePrevisionId = new ExpedientePrevisionId();
		
		System.out.println("registroSeleccionadoBusquedav --> "+registroSeleccionadoAut);
		expedientePrevisionId.setIntPersEmpresaPk(registroSeleccionadoAut.getId().getIntPersEmpresaPk());
		expedientePrevisionId.setIntCuentaPk(registroSeleccionadoAut.getId().getIntCuentaPk());
		expedientePrevisionId.setIntItemExpediente(registroSeleccionadoAut.getId().getIntItemExpediente());
		//ExpedientePrevisionId.setIntItemDetExpediente(strItemDetExpediente == null ? new Integer(strIntItemDetExpediente) : new Integer(	strItemDetExpediente));
		
		try {
			// devuelve el crongrama son id vacio.
			//beanExpedientePrevision 
			beanExpedientePrevision = previsionFacade.getExpedientePrevisionCompletoPorIdExpedientePrevision(expedientePrevisionId);
			
			if (beanExpedientePrevision != null) {
					//listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				
					// Recuperamos al Socio 
						if(beanExpedientePrevision.getId() != null){
							
							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = null;
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());

							//intTipoSolicitudExtra = beanExpedientePrevision.getIntParaTipoCaptacion();
							//intSubTipoSolicitudExtra = beanExpedientePrevision.getIntParaSubTipoOperacion();
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
						
						String strUnidadesEjecutoras = "";
						SocioEstructura socioEstrConcat = null;
						getListEstructura();
						for ( int e=0; e<socioComp.getSocio().getListSocioEstructura().size();e++) {
							socioEstrConcat = beanSocioComp.getSocio().getListSocioEstructura().get(e);
							
							for(int s=0; s<listEstructura.size();s++){
								if(listEstructura.get(s).getId().getIntCodigo().compareTo(socioEstrConcat.getIntCodigo())==0){
									strUnidadesEjecutoras = listEstructura.get(s).getJuridica().getStrRazonSocial();
									strUnidadesEjecutorasConcatenadas = strUnidadesEjecutoras+  ", " + strUnidadesEjecutorasConcatenadas;	
								}
								
							}
						}
						//---------------------------------------------------------------------------------------------------->
						
						
					// recuperamos estados
					if (beanExpedientePrevision.getListaEstadoPrevision() != null) {
						//configurarCampos(beanExpedienteCredito);
						
						//for (EstadoPrevision estadoPrevision : beanExpedientePrevision.getListaEstadoPrevision()) {
							if (beanExpedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)
								|| beanExpedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)) {
								setStrSolicitudPrevision(Constante.MANTENIMIENTO_ELIMINAR);
							} else {
								setStrSolicitudPrevision(Constante.MANTENIMIENTO_MODIFICAR);
							}
						//}
						
					}

					if (beanExpedientePrevision.getEstadoPrevisionPrimero() != null) {
						long lnFechaEstadoUno = beanExpedientePrevision.getEstadoPrevisionPrimero().getTsFechaEstado().getTime();
						//configurarCampos(beanExpedienteCredito);
						dtFechaRegistro = new Date(lnFechaEstadoUno);

					}
					
				if (beanExpedientePrevision.getListaRequisitoPrevisionComp()!= null) {
					listaRequisitoPrevisionComp = beanExpedientePrevision.getListaRequisitoPrevisionComp();
				}
				
				
				intSubTipoSolicitud = 0;
				if(beanExpedientePrevision.getIntParaTipoCaptacion().intValue() == Constante.CAPTACION_AES ){
						intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_AES;
						intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
						listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));
						blnIsAES = true;
						blnIsSepelio = false;
						blnIsRetiro = false;
						blnBeneficiarioNormal = true;
						blnBeneficiarioSepelio = false;
						blnBeneficiarioRetiro = false;
	
				} else if(beanExpedientePrevision.getIntParaTipoCaptacion().intValue() == Constante.CAPTACION_FDO_SEPELIO){
							intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_SEPELIO;
							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));
							intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
							blnIsAES = false;
							blnIsSepelio = true;
							blnIsRetiro = false;
							blnBeneficiarioNormal = false;
							blnBeneficiarioSepelio = true;
							blnBeneficiarioRetiro = false;
							
				} else if(beanExpedientePrevision.getIntParaTipoCaptacion().intValue() == Constante.CAPTACION_FDO_RETIRO){
							intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_RETIRO;	
							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));
							intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
							blnIsAES = false;
							blnIsSepelio = false;
							blnIsRetiro = true; 
							blnBeneficiarioNormal = false;
							blnBeneficiarioSepelio = false;
							blnBeneficiarioRetiro = true;
				}
				Collections.sort(listaSubTipoSolicitud, new Comparator<Tabla>(){
					public int compare(Tabla uno, Tabla otro) {
						return uno.getIntOrden().compareTo(otro.getIntOrden());
					}
				});
				
				blnDeshabilitar = true;
				// recuperamos captacion>
				//Captacion captacion = null;
				CaptacionId captacionId = new CaptacionId();
				captacionId.setIntPersEmpresaPk(beanExpedientePrevision.getIntPersEmpresa());
				captacionId.setIntParaTipoCaptacionCod(beanExpedientePrevision.getIntParaTipoCaptacion());
				captacionId.setIntItem(beanExpedientePrevision.getIntItem());
				try {
					//captacion = captacionFacade.getCaptacionPorIdCaptacion(captacionId);
					//------------------------------------------------------------------------------------>
							captacionId = new CaptacionId();
							captacionId.setIntPersEmpresaPk(beanSocioComp.getSocio().getSocioEstructura().getId().getIntIdEmpresa());	
							
							if(intTipoSolicitud.intValue() == Constante.PARAM_T_TIPO_PREVISION_AES){
								captacionId.setIntParaTipoCaptacionCod(Integer.valueOf(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)); 
							}else if(intTipoSolicitud.intValue() == Constante.PARAM_T_TIPO_PREVISION_SEPELIO){
								captacionId.setIntParaTipoCaptacionCod(Integer.valueOf(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)); 
							}
							else if(intTipoSolicitud.intValue() == Constante.PARAM_T_TIPO_PREVISION_RETIRO){
								captacionId.setIntParaTipoCaptacionCod(Integer.valueOf(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO));
							
							} else {setStrMsgTxtEvaluacion("No se encontró Configuración de Captación.");}

							listaCaptacion = captacionFacade.getCaptacionPorPKOpcional(captacionId);
							if(listaCaptacion!=null){		

								for(int k=0; k<listaCaptacion.size();k++){
									if((listaCaptacion.get(k).getId().getIntItem().compareTo(beanExpedientePrevision.getIntItem())==0)
										&&	(listaCaptacion.get(k).getId().getIntPersEmpresaPk().compareTo(beanExpedientePrevision.getIntPersEmpresa())==0)
										&&	(listaCaptacion.get(k).getId().getIntParaTipoCaptacionCod().compareTo(beanExpedientePrevision.getIntParaTipoCaptacion())==0)
										){
											beanCaptacion = listaCaptacion.get(k);
											blnCaptacion = true;
										}
								
								}
							}
							
							if(beanCaptacion!=null){
								//for(int k=0; k<listaCaptacion.size();k++){
									List<Concepto> listaConceptos = null;
									List<Condicion> listaCondiciones = null;
									CaptacionId id = new CaptacionId();
									id = beanCaptacion.getId();
									listaConceptos=captacionFacade.getListaConceptoPorPKCaptacion(id);
									listaCondiciones = condicionFacade.listarCondicion(id);
										//captacionFacade.getListaPorPKCaptacion(listaCaptacion.get(k).getId());
									//(arg0)   condicionFacade.listarCondicion(listaCaptacion.get(k).getId());
									
									if(listaConceptos != null){
										beanCaptacion.setListaConcepto(listaConceptos);
									}
									if(listaCondiciones != null){
										beanCaptacion.setListaCondicion(listaCondiciones);
									}
								
								//}
							}
							
							
					//-------------------------------------------------------------------------------------->
				
				} catch (Exception e) {
					log.info("Recuperando la captacion ---> "+e);
					blnCaptacion = false;
					beanCaptacion = null;					
				}
				
				// Revuperando los beneficiarios
				if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null){
					//	|| !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
				//if(!beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
											//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
					for(int k=0; k<beanExpedientePrevision.getListaBeneficiarioPrevision().size();k++){
						
						intIdPersona = beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntPersPersonaBeneficiario();
						
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
							//beanExpedientePrevision.getListaBeneficiarioPrevision().clear();
							beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setPersona(persona);
						}
					}	
				}	
				
				
				// Revuperando los fallecidos
				if(beanExpedientePrevision.getListaFallecidoPrevision() != null){
					//	|| !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
				//if(!beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
											//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
					for(int k=0; k<beanExpedientePrevision.getListaFallecidoPrevision().size();k++){
						
						intIdPersona = beanExpedientePrevision.getListaFallecidoPrevision().get(k).getIntPersPersonaFallecido();
						
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
							//beanExpedientePrevision.getListaBeneficiarioPrevision().clear();
							beanExpedientePrevision.getListaFallecidoPrevision().get(k).setPersona(persona);
						}
					}	
				}

				cargarListaTablaSucursal();
				seleccionarSucursal();
				evaluarPrevisionMod(event);
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
	}*/
		
		
		/**
		 * 
		 * @param event
		 */
		public void irModificarSolicitudPrevisionAutoriza2(ActionEvent event,ExpedientePrevision registroSeleccionadoAut){	
			cancelarGrabarSolicitud(event);
			strSolicitudPrevision = Constante.MANTENIMIENTO_NINGUNO;
			blnMostrarDescripcionTipoPrevision = Boolean.TRUE;
			SocioComp socioComp = null;
			Integer intIdPersona = null;
			Persona persona = null;
			List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;
			ExpedientePrevisionId expedientePrevisionId = null;
			Cuenta cuentaExpediente = null;
			
			try {
				expedientePrevisionId = new ExpedientePrevisionId();
				expedientePrevisionId.setIntPersEmpresaPk(registroSeleccionadoAut.getId().getIntPersEmpresaPk());
				expedientePrevisionId.setIntCuentaPk(registroSeleccionadoAut.getId().getIntCuentaPk());
				expedientePrevisionId.setIntItemExpediente(registroSeleccionadoAut.getId().getIntItemExpediente());
				// devuelve el crongrama son id vacio.
				beanExpedientePrevision = previsionFacade.getExpedientePrevisionCompletoPorIdExpedientePrevision(expedientePrevisionId);
				
				if (beanExpedientePrevision != null) {
					// Seteamos los valores en combos de tipo y subtipo
					intTipoSolicitud = beanExpedientePrevision.getIntParaTipoCaptacion();
					intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
					cargarDescripcionTipoPrevision();
						// Recuperamos al Socio 
							if(beanExpedientePrevision.getId() != null){
								
								CuentaId cuentaIdSocio = new CuentaId();
								Cuenta cuentaSocio = new Cuenta();
								List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
								cuentaIdSocio.setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
								cuentaIdSocio.setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
								cuentaSocio.setId(cuentaIdSocio);
								cuentaSocio = cuentaFacade.getCuentaPorIdCuenta(cuentaSocio);
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
											cuentaExpediente.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
											cuentaExpediente.getId().setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
											
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
											for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
												if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
													socioComp.getSocio().setSocioEstructura(socioEstructura);
												}
											}
											beanSocioComp = socioComp;
											recuperarCuentasConceptoSocio(beanSocioComp);
											mostrarlistaAutorizacionesPrevias(beanExpedientePrevision.getListaEstadoPrevision());
										}
									}	
								//}	
							}
						
							// colocar las UNIDADES EJECUTORAS correlativas...
							//---------------------------------------------------------------------------------------------------->
							cargarDescripcionUEjecutorasConcatenadas(beanSocioComp);

						// recuperamos estados
						if (beanExpedientePrevision.getEstadoPrevisionPrimero() != null) {
							long lnFechaEstadoUno = beanExpedientePrevision.getEstadoPrevisionPrimero().getTsFechaEstado().getTime();
							//configurarCampos(beanExpedienteCredito);
							dtFechaRegistro = new Date(lnFechaEstadoUno);
							strFechaRegistro = Constante.sdf.format(dtFechaRegistro);

						}
						
					if (beanExpedientePrevision.getListaRequisitoPrevisionComp()!= null) {
						
						listaRequisitoPrevisionComp = beanExpedientePrevision.getListaRequisitoPrevisionComp();
					}

					if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0 ){
							//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_AES;
							//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));
							blnIsAES = true;
							blnIsSepelio = false;
							blnIsRetiro = false;
							blnBeneficiarioNormal = true;
							blnBeneficiarioSepelio = false;
							blnBeneficiarioRetiro = false;
		
					} else if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO)==0 ){
								//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_SEPELIO;
								listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));
								//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
								blnIsAES = false;
								blnIsSepelio = true;
								blnIsRetiro = false;
								blnBeneficiarioNormal = false;
								blnBeneficiarioSepelio = true;
								blnBeneficiarioRetiro = false;
								
								if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
									blnIsSepelioTitular = true;
								}
								
					} else if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
								//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_RETIRO;	
								listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));
								//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
								blnIsAES = false;
								blnIsSepelio = false;
								blnIsRetiro = true; 
								blnBeneficiarioNormal = false;
								blnBeneficiarioSepelio = false;
								blnBeneficiarioRetiro = true;
					}
					Collections.sort(listaSubTipoSolicitud, new Comparator<Tabla>(){
						public int compare(Tabla uno, Tabla otro) {
							return uno.getIntOrden().compareTo(otro.getIntOrden());
						}
					});
					blnDeshabilitar = true;


					CaptacionId captacionId = new CaptacionId();
					captacionId.setIntPersEmpresaPk(beanExpedientePrevision.getIntPersEmpresa());
					captacionId.setIntParaTipoCaptacionCod(beanExpedientePrevision.getIntParaTipoCaptacion());
					captacionId.setIntItem(beanExpedientePrevision.getIntItem());

								listaCaptacion = captacionFacade.getCaptacionPorPKOpcional(captacionId);
								if(listaCaptacion!=null){		

									for(int k=0; k<listaCaptacion.size();k++){
										if((listaCaptacion.get(k).getId().getIntItem().compareTo(beanExpedientePrevision.getIntItem())==0)
											&&	(listaCaptacion.get(k).getId().getIntPersEmpresaPk().compareTo(beanExpedientePrevision.getIntPersEmpresa())==0)
											&&	(listaCaptacion.get(k).getId().getIntParaTipoCaptacionCod().compareTo(beanExpedientePrevision.getIntParaTipoCaptacion())==0)
											){
												beanCaptacion = listaCaptacion.get(k);
												blnCaptacion = true;
											}
									
									}
								}
								
								if(beanCaptacion!=null){
									//for(int k=0; k<listaCaptacion.size();k++){
										List<Concepto> listaConceptos = null;
										List<Condicion> listaCondiciones = null;
										CaptacionId id = new CaptacionId();
										id = beanCaptacion.getId();
										listaConceptos=captacionFacade.getListaConceptoPorPKCaptacion(id);
										listaCondiciones = condicionFacade.listarCondicion(id);
											//captacionFacade.getListaPorPKCaptacion(listaCaptacion.get(k).getId());
										//(arg0)   condicionFacade.listarCondicion(listaCaptacion.get(k).getId());
										
										if(listaConceptos != null){
											beanCaptacion.setListaConcepto(listaConceptos);
										}
										if(listaCondiciones != null){
											beanCaptacion.setListaCondicion(listaCondiciones);
										}
									
									//}
								}
								
					
								
					// parche Convertir Fallecidos a beneficiarios (solo visual)
					if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
						if(beanExpedientePrevision.getListaFallecidoPrevision()!= null
							&& !beanExpedientePrevision.getListaFallecidoPrevision().isEmpty()){
							List<BeneficiarioPrevision> listaBeneficiariosAES = null;
							
							listaBeneficiariosAES = regenerarBeneficiariosXPersonaMotivoAES(beanExpedientePrevision.getListaFallecidoPrevision());
							if(listaBeneficiariosAES != null & !listaBeneficiariosAES.isEmpty()){
								beanExpedientePrevision.setListaBeneficiarioPrevision(listaBeneficiariosAES);
								beanExpedientePrevision.getListaFallecidoPrevision().clear();	
							}
							
						}	
					}		
								
				
					// Revuperando los beneficiarios
					if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null){
						
						for(int k=0; k<beanExpedientePrevision.getListaBeneficiarioPrevision().size();k++){
							
							intIdPersona = beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntPersPersonaBeneficiario();
							
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
								beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setPersona(persona);

							}
						}	
					}	
					
					
					// Revuperando los fallecidos
					if(beanExpedientePrevision.getListaFallecidoPrevision() != null){
						//	|| !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
					//if(!beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
												//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
						for(int k=0; k<beanExpedientePrevision.getListaFallecidoPrevision().size();k++){
							
							intIdPersona = beanExpedientePrevision.getListaFallecidoPrevision().get(k).getIntPersPersonaFallecido();
							
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
								//beanExpedientePrevision.getListaBeneficiarioPrevision().clear();
								beanExpedientePrevision.getListaFallecidoPrevision().get(k).setPersona(persona);
							}
						}	
					}

					cargarListaTablaSucursal();
					seleccionarSucursal();
					evaluarPrevisionMod(event);
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

			blnShowDivFormSolicitudPrevision = true;
			pgValidDatos = false;
			blnDatosSocio = true;
	}
	
		public void verSolicitudPrevision(ActionEvent event){
			blnVerRegistroSolExpPrevision = false;
			cancelarGrabarSolicitud(event);
			strSolicitudPrevision = Constante.MANTENIMIENTO_NINGUNO;
			blnMostrarDescripcionTipoPrevision = Boolean.TRUE;
			SocioComp socioComp = null;
			Integer intIdPersona = null;
			Persona persona = null;
			List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;
			ExpedientePrevisionId expedientePrevisionId = null;
			Cuenta cuentaExpediente = null;
			
			try {
				expedientePrevisionId = new ExpedientePrevisionId();
				expedientePrevisionId.setIntPersEmpresaPk(registroSeleccionado.getExpedientePrevision().getId().getIntPersEmpresaPk());
				expedientePrevisionId.setIntCuentaPk(registroSeleccionado.getExpedientePrevision().getId().getIntCuentaPk());
				expedientePrevisionId.setIntItemExpediente(registroSeleccionado.getExpedientePrevision().getId().getIntItemExpediente());
				// devuelve el crongrama son id vacio.
				beanExpedientePrevision = previsionFacade.getExpedientePrevisionCompletoPorIdExpedientePrevision(expedientePrevisionId);
				
				if (beanExpedientePrevision != null) {
					// Seteamos los valores en combos de tipo y subtipo
					intTipoSolicitud = beanExpedientePrevision.getIntParaTipoCaptacion();
					intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
					cargarDescripcionTipoPrevision();
						// Recuperamos al Socio 
							if(beanExpedientePrevision.getId() != null){
								
								CuentaId cuentaIdSocio = new CuentaId();
								Cuenta cuentaSocio = new Cuenta();
								List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
								cuentaIdSocio.setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
								cuentaIdSocio.setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
								cuentaSocio.setId(cuentaIdSocio);
								cuentaSocio = cuentaFacade.getCuentaPorIdCuenta(cuentaSocio);
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
											cuentaExpediente.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
											cuentaExpediente.getId().setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
											
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
											for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
												if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
													socioComp.getSocio().setSocioEstructura(socioEstructura);
												}
											}
											beanSocioComp = socioComp;
											recuperarCuentasConceptoSocio(beanSocioComp);
											mostrarlistaAutorizacionesPrevias(beanExpedientePrevision.getListaEstadoPrevision());
										}
									}	
								//}	
							}
						
							// colocar las UNIDADES EJECUTORAS correlativas...
							//---------------------------------------------------------------------------------------------------->
							cargarDescripcionUEjecutorasConcatenadas(beanSocioComp);

						// recuperamos estados
						if (beanExpedientePrevision.getEstadoPrevisionPrimero() != null) {
							long lnFechaEstadoUno = beanExpedientePrevision.getEstadoPrevisionPrimero().getTsFechaEstado().getTime();
							//configurarCampos(beanExpedienteCredito);
							dtFechaRegistro = new Date(lnFechaEstadoUno);
							strFechaRegistro = Constante.sdf.format(dtFechaRegistro);

						}
						
					if (beanExpedientePrevision.getListaRequisitoPrevisionComp()!= null) {
						
						listaRequisitoPrevisionComp = beanExpedientePrevision.getListaRequisitoPrevisionComp();
					}

					if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0 ){
							//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_AES;
							//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
							listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));
							blnIsAES = true;
							blnIsSepelio = false;
							blnIsRetiro = false;
							blnBeneficiarioNormal = true;
							blnBeneficiarioSepelio = false;
							blnBeneficiarioRetiro = false;
		
					} else if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO)==0 ){
								//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_SEPELIO;
								listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));
								//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
								blnIsAES = false;
								blnIsSepelio = true;
								blnIsRetiro = false;
								blnBeneficiarioNormal = false;
								blnBeneficiarioSepelio = true;
								blnBeneficiarioRetiro = false;
								
								if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
									blnIsSepelioTitular = true;
								}
								
					} else if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
								//intTipoSolicitud = Constante.PARAM_T_TIPO_PREVISION_RETIRO;	
								listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));
								//intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
								blnIsAES = false;
								blnIsSepelio = false;
								blnIsRetiro = true; 
								blnBeneficiarioNormal = false;
								blnBeneficiarioSepelio = false;
								blnBeneficiarioRetiro = true;
					}
					Collections.sort(listaSubTipoSolicitud, new Comparator<Tabla>(){
						public int compare(Tabla uno, Tabla otro) {
							return uno.getIntOrden().compareTo(otro.getIntOrden());
						}
					});
					blnDeshabilitar = true;


					CaptacionId captacionId = new CaptacionId();
					captacionId.setIntPersEmpresaPk(beanExpedientePrevision.getIntPersEmpresa());
					captacionId.setIntParaTipoCaptacionCod(beanExpedientePrevision.getIntParaTipoCaptacion());
					captacionId.setIntItem(beanExpedientePrevision.getIntItem());

								listaCaptacion = captacionFacade.getCaptacionPorPKOpcional(captacionId);
								if(listaCaptacion!=null){		

									for(int k=0; k<listaCaptacion.size();k++){
										if((listaCaptacion.get(k).getId().getIntItem().compareTo(beanExpedientePrevision.getIntItem())==0)
											&&	(listaCaptacion.get(k).getId().getIntPersEmpresaPk().compareTo(beanExpedientePrevision.getIntPersEmpresa())==0)
											&&	(listaCaptacion.get(k).getId().getIntParaTipoCaptacionCod().compareTo(beanExpedientePrevision.getIntParaTipoCaptacion())==0)
											){
												beanCaptacion = listaCaptacion.get(k);
												blnCaptacion = true;
											}
									
									}
								}
								
								if(beanCaptacion!=null){
									//for(int k=0; k<listaCaptacion.size();k++){
										List<Concepto> listaConceptos = null;
										List<Condicion> listaCondiciones = null;
										CaptacionId id = new CaptacionId();
										id = beanCaptacion.getId();
										listaConceptos=captacionFacade.getListaConceptoPorPKCaptacion(id);
										listaCondiciones = condicionFacade.listarCondicion(id);
											//captacionFacade.getListaPorPKCaptacion(listaCaptacion.get(k).getId());
										//(arg0)   condicionFacade.listarCondicion(listaCaptacion.get(k).getId());
										
										if(listaConceptos != null){
											beanCaptacion.setListaConcepto(listaConceptos);
										}
										if(listaCondiciones != null){
											beanCaptacion.setListaCondicion(listaCondiciones);
										}
									
									//}
								}
								
					
								
					// parche Convertir Fallecidos a beneficiarios (solo visual)
					if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
						if(beanExpedientePrevision.getListaFallecidoPrevision()!= null
							&& !beanExpedientePrevision.getListaFallecidoPrevision().isEmpty()){
							List<BeneficiarioPrevision> listaBeneficiariosAES = null;
							
							listaBeneficiariosAES = regenerarBeneficiariosXPersonaMotivoAES(beanExpedientePrevision.getListaFallecidoPrevision());
							if(listaBeneficiariosAES != null & !listaBeneficiariosAES.isEmpty()){
								beanExpedientePrevision.setListaBeneficiarioPrevision(listaBeneficiariosAES);
								beanExpedientePrevision.getListaFallecidoPrevision().clear();	
							}
							
						}	
					}		
								
				
					// Revuperando los beneficiarios
					if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null){
						
						for(int k=0; k<beanExpedientePrevision.getListaBeneficiarioPrevision().size();k++){
							
							intIdPersona = beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntPersPersonaBeneficiario();
							
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
								beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setPersona(persona);

							}
						}	
					}	
					
					
					// Revuperando los fallecidos
					if(beanExpedientePrevision.getListaFallecidoPrevision() != null){
						//	|| !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
					//if(!beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
												//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
						for(int k=0; k<beanExpedientePrevision.getListaFallecidoPrevision().size();k++){
							
							intIdPersona = beanExpedientePrevision.getListaFallecidoPrevision().get(k).getIntPersPersonaFallecido();
							
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
								//beanExpedientePrevision.getListaBeneficiarioPrevision().clear();
								beanExpedientePrevision.getListaFallecidoPrevision().get(k).setPersona(persona);
							}
						}	
					}

					cargarListaTablaSucursal();
					seleccionarSucursal();
					evaluarPrevisionModSinValidaciones(event);
				}
				
				blnShowDivFormSolicitudPrevision = true;
				pgValidDatos = false;
				blnDatosSocio = true;
				blnVerRegistroSolExpPrevision = true;
			} catch (BusinessException e) {
				log.error("Error BusinessException en verSolicitudPrevision ---> " + e);
	
				e.printStackTrace();
			} 
			catch (ParseException e1) {
					log.error("Error ParseException en verSolicitudPrevision ---> " + e1);
	
			} catch (Exception e2) {
					log.error("Error Exception en verSolicitudPrevision ---> " + e2);
			}	
		}

		/**
		 * 
		 * @param event
		 */
		
		public void irModificarSolicitudPrevision(ActionEvent event){
			cancelarGrabarSolicitud(event);
			strSolicitudPrevision = Constante.MANTENIMIENTO_MODIFICAR;
			blnMostrarDescripcionTipoPrevision = Boolean.TRUE;
			SocioComp socioComp = null;
			Integer intIdPersona = null;
			Persona persona = null;
			List<Captacion> listaCaptacion = null;		//blnDeshabilitar = true;
			ExpedientePrevisionId expedientePrevisionId = null;
			Cuenta cuentaExpediente = null;


			try {
					expedientePrevisionId = new ExpedientePrevisionId();

					expedientePrevisionId.setIntPersEmpresaPk(registroSeleccionado.getExpedientePrevision().getId().getIntPersEmpresaPk());
					expedientePrevisionId.setIntCuentaPk(registroSeleccionado.getExpedientePrevision().getId().getIntCuentaPk());
					expedientePrevisionId.setIntItemExpediente(registroSeleccionado.getExpedientePrevision().getId().getIntItemExpediente());
					// devuelve el crongrama son id vacio.
					beanExpedientePrevision = previsionFacade.getExpedientePrevisionCompletoPorIdExpedientePrevision(expedientePrevisionId);


					if (beanExpedientePrevision != null) {
						// Seteamos los valores en combos de tipo y subtipo
						intTipoSolicitud = beanExpedientePrevision.getIntParaTipoCaptacion();
						intSubTipoSolicitud = beanExpedientePrevision.getIntParaSubTipoOperacion();
						cargarDescripcionTipoPrevision();
						// Recuperamos al Socio 
						if(beanExpedientePrevision.getId() != null){

							CuentaId cuentaIdSocio = new CuentaId();
							Cuenta cuentaSocio = new Cuenta();
							List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
							cuentaIdSocio.setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
							cuentaIdSocio.setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
							cuentaSocio.setId(cuentaIdSocio);
							cuentaSocio = cuentaFacade.getCuentaPorIdCuenta(cuentaSocio);
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

									cuentaExpediente = new Cuenta();
									cuentaExpediente.setId(new CuentaId());
									cuentaExpediente.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
									cuentaExpediente.getId().setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());

									socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
																										persona.getDocumento().getStrNumeroIdentidad(),
																										Constante.PARAM_EMPRESASESION, cuentaExpediente);
									
									for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											socioComp.getSocio().setSocioEstructura(socioEstructura);
										}
									}
									beanSocioComp = socioComp;
									recuperarCuentasConceptoSocio(beanSocioComp);
									mostrarlistaAutorizacionesPrevias(beanExpedientePrevision.getListaEstadoPrevision());
								}
							}

							cargarDescripcionUEjecutorasConcatenadas(beanSocioComp);

							// recuperamos estados
							if (beanExpedientePrevision.getListaEstadoPrevision() != null) {
								if (beanExpedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)
								|| beanExpedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)){
									setStrSolicitudPrevision(Constante.MANTENIMIENTO_ELIMINAR);
								} else {
									setStrSolicitudPrevision(Constante.MANTENIMIENTO_MODIFICAR);
								}
							}else{
								log.error("No se recuepraron estados en beanExpedientePrevision.getListaEstadoPrevision() - ir modificar.");
							}

							if (beanExpedientePrevision.getEstadoPrevisionPrimero() != null) {
								long lnFechaEstadoUno = beanExpedientePrevision.getEstadoPrevisionPrimero().getTsFechaEstado().getTime();
								dtFechaRegistro = new Date(lnFechaEstadoUno);
								strFechaRegistro = Constante.sdf.format(dtFechaRegistro);
							}else{
								log.error("No existe ultimo estado en beanExpedientePrevision.getEstadoPrevisionPrimero() . ES NULO");
							}

							if (beanExpedientePrevision.getListaRequisitoPrevisionComp()!= null) {

								listaRequisitoPrevisionComp = beanExpedientePrevision.getListaRequisitoPrevisionComp();

								if(listaRequisitoPrevisionComp != null && !listaRequisitoPrevisionComp.isEmpty()){
									for (RequisitoPrevisionComp requisitoComp : listaRequisitoPrevisionComp) {
										if(requisitoComp.getDetalle().getIntOpcionAdjunta().compareTo(1)==0){
											blnTieneAdjuntosConfigurados = Boolean.TRUE;
											break;
										}
									}
								}
							}

							if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0 ){
								listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));
								blnIsAES = true;
								blnIsSepelio = false;

								blnIsRetiro = false;
								blnBeneficiarioNormal = true;
								blnBeneficiarioSepelio = false;

								blnBeneficiarioRetiro = false;

							} else if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO)==0 ){
										listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));
										blnIsAES = false;
										blnIsSepelio = true;
										blnIsRetiro = false;
										blnBeneficiarioNormal = false;
										blnBeneficiarioSepelio = true;
										blnBeneficiarioRetiro = false;

										if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
											blnIsSepelioTitular = true;
										}
								
							} else if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO)==0 ){
										listaSubTipoSolicitud = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_RETIRO));
										blnIsAES = false;
										blnIsSepelio = false;
										blnIsRetiro = true; 
										blnBeneficiarioNormal = false;
										blnBeneficiarioSepelio = false;
										blnBeneficiarioRetiro = true;
							}
							
							Collections.sort(listaSubTipoSolicitud, new Comparator<Tabla>(){
								public int compare(Tabla uno, Tabla otro) {
								return uno.getIntOrden().compareTo(otro.getIntOrden());


								}
							});
							blnDeshabilitar = true;

							CaptacionId captacionId = new CaptacionId();
							captacionId.setIntPersEmpresaPk(beanExpedientePrevision.getIntPersEmpresa());
							captacionId.setIntParaTipoCaptacionCod(beanExpedientePrevision.getIntParaTipoCaptacion());
							captacionId.setIntItem(beanExpedientePrevision.getIntItem());

							listaCaptacion = captacionFacade.getCaptacionPorPKOpcional(captacionId);
							if(listaCaptacion!=null){

								for(int k=0; k<listaCaptacion.size();k++){
									if((listaCaptacion.get(k).getId().getIntItem().compareTo(beanExpedientePrevision.getIntItem())==0)

									&&	(listaCaptacion.get(k).getId().getIntPersEmpresaPk().compareTo(beanExpedientePrevision.getIntPersEmpresa())==0)
									&&	(listaCaptacion.get(k).getId().getIntParaTipoCaptacionCod().compareTo(beanExpedientePrevision.getIntParaTipoCaptacion())==0)){
										beanCaptacion = listaCaptacion.get(k);
										blnCaptacion = true;
									}
								}
							}


							if(beanCaptacion!=null){
								//for(int k=0; k<listaCaptacion.size();k++){
								List<Concepto> listaConceptos = null;
								List<Condicion> listaCondiciones = null;
								CaptacionId id = new CaptacionId();
								id = beanCaptacion.getId();
								listaConceptos=captacionFacade.getListaConceptoPorPKCaptacion(id);
								listaCondiciones = condicionFacade.listarCondicion(id);

								if(listaConceptos != null && !listaCondiciones.isEmpty()){
									beanCaptacion.setListaConcepto(listaConceptos);
								}else{
									log.error("No hat listaconceptos");

								}
								if(listaCondiciones != null && !listaCondiciones.isEmpty()){
									beanCaptacion.setListaCondicion(listaCondiciones);
								}else{
									log.error("No hat listaCOndiciones");

								}
							}else{
								log.error("El bean beanCaptacion es Nulo." +beanCaptacion + "---"+ beanCaptacion);
							}

							// parche Convertir Fallecidos a beneficiarios (solo visual)
							if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){

								if(beanExpedientePrevision.getListaFallecidoPrevision()!= null
								&& !beanExpedientePrevision.getListaFallecidoPrevision().isEmpty()){
									List<BeneficiarioPrevision> listaBeneficiariosAES = null;

									listaBeneficiariosAES = regenerarBeneficiariosXPersonaMotivoAES(beanExpedientePrevision.getListaFallecidoPrevision());
									if(listaBeneficiariosAES != null & !listaBeneficiariosAES.isEmpty()){
										beanExpedientePrevision.setListaBeneficiarioPrevision(listaBeneficiariosAES);
										if(beanExpedientePrevision.getListaFallecidoPrevision()!= null
										&& !beanExpedientePrevision.getListaFallecidoPrevision().isEmpty()){
											beanExpedientePrevision.getListaFallecidoPrevision().clear();
										}
									}
								}	
							}		

							// Revuperando los beneficiarios
							if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null && !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){

								for(int k=0; k<beanExpedientePrevision.getListaBeneficiarioPrevision().size();k++){

									intIdPersona = beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntPersPersonaBeneficiario();
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

										PersonaEmpresaPK personaEmpresaId = new PersonaEmpresaPK();
										personaEmpresaId.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
										personaEmpresaId.setIntIdPersona(socioComp.getPersona().getIntIdPersona());
										List<Vinculo> listaVinculos = null;


										//vinculo = personaFacade.getVinculoPorPk(personaEmpresaId).get(0);
										listaVinculos = personaFacade.getVinculoPorPk(personaEmpresaId);
										if(listaVinculos != null && !listaVinculos.isEmpty()){
											// cgd - 12.012014
											if(!blnIsSepelioTitular){
												// solo recupera un beneficiario (el titular), x lo tanto nos e valida y setea.
												Vinculo vinculoTitular = new Vinculo();
												vinculoTitular = listaVinculos.get(0);
												beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setIntTipoViculo(vinculoTitular.getIntTipoVinculoCod());
											}else{
												for (Vinculo vinculo2 : listaVinculos) {
												if(beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).getIntItemViculo().compareTo(vinculo2.getIntItemVinculo())==0){
													beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setIntTipoViculo(vinculo2.getIntTipoVinculoCod());
													break;
												}
												}
											}
										}
										beanExpedientePrevision.getListaBeneficiarioPrevision().get(k).setPersona(persona);

									}
								}	
							}

								
							// Revuperando los fallecidos
							if(beanExpedientePrevision.getListaFallecidoPrevision() != null && !beanExpedientePrevision.getListaFallecidoPrevision().isEmpty()){
								//	|| !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
								//if(!beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
								//intIdPersona = beanExpedientePrevision.getIntPersEmpresa();
								for(int k=0; k<beanExpedientePrevision.getListaFallecidoPrevision().size();k++){
									intIdPersona = beanExpedientePrevision.getListaFallecidoPrevision().get(k).getIntPersPersonaFallecido();

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

											// Recuperamos su tipo de relacion, en base a la lista de vinculos del socio.
											PersonaEmpresaPK personaEmpresaId = new PersonaEmpresaPK();
											personaEmpresaId.setIntIdEmpresa(Constante.PARAM_EMPRESASESION);
											personaEmpresaId.setIntIdPersona(socioComp.getPersona().getIntIdPersona());
											List<Vinculo> listaVinculos = null;
											listaVinculos = personaFacade.getVinculoPorPk(personaEmpresaId);
											if(listaVinculos != null && !listaVinculos.isEmpty()){

												for (Vinculo vinculo2 : listaVinculos) {
													if(beanExpedientePrevision.getListaFallecidoPrevision().get(k).getIntItemViculo() == null 
													||vinculo2.getIntItemVinculo() == null 
													|| vinculo2.getIntTipoVinculoCod() ==null){

													
													}else{


														if(beanExpedientePrevision.getListaFallecidoPrevision().get(k).getIntItemViculo().compareTo(vinculo2.getIntItemVinculo())==0){
															beanExpedientePrevision.getListaFallecidoPrevision().get(k).setIntTipoViculo(vinculo2.getIntTipoVinculoCod());
															break;
														}
													}
												}

											}
											//beanExpedientePrevision.getListaBeneficiarioPrevision().clear();
											beanExpedientePrevision.getListaFallecidoPrevision().get(k).setPersona(persona);


									}
								}	
							}
							cargarListaTablaSucursal();
							seleccionarSucursal();
							//CGD - PRUEBAS - 28.11.2013
							evaluarPrevisionMod(event);
							
							System.out.println("mdts:->>"+beanSocioComp.getPersona().getNatural().getStrApellidoPaterno());
						}else{
							System.out.println("beanExpedientePrevision.getId() es nulo.");
						}

					}else{
						System.out.println("No se recupera beanExpedientePrevision...");

					}

					strSolicitudPrevision = Constante.MANTENIMIENTO_MODIFICAR;
					blnShowDivFormSolicitudPrevision = true;
					pgValidDatos = false;
					blnDatosSocio = true;
					blnVerRegistroSolExpPrevision = false;

			} catch (BusinessException e) {
				log.error("Error BusinessException en irModificarSolicitudPrevision ---> " + e);

				e.printStackTrace();
			} 
			catch (ParseException e1) {
					log.error("Error ParseException en irModificarSolicitudPrevision ---> " + e1);

			} catch (Exception e2) {
					log.error("Error Exception en irModificarSolicitudPrevision ---> " + e2);
			}

			//blnPostEvaluacion = true;
		}



	
	/**
	 * 
	 * @param event
	 */
	public void irEliminarSolicitudPrevision(ActionEvent event){
		ExpedientePrevisionId expedientePrevisionId = null;
//		ExpedientePrevision expedientePrevision = null;
		SocioComp socioComp = null;
		Integer intIdPersona = null;
		Persona persona = null;
		//listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		blnDeshabilitar = true;
		
		try {
			
			if(registroSeleccionado != null){
				expedientePrevisionId = new ExpedientePrevisionId();
				
				expedientePrevisionId.setIntPersEmpresaPk(registroSeleccionado.getExpedientePrevision().getId().getIntPersEmpresaPk());
				expedientePrevisionId.setIntCuentaPk(registroSeleccionado.getExpedientePrevision().getId().getIntCuentaPk());
				expedientePrevisionId.setIntItemExpediente(registroSeleccionado.getExpedientePrevision().getId().getIntItemExpediente());
				
				// 1.  recuperamos el expediente
				beanExpedientePrevision = previsionFacade.getExpedientePrevisionCompletoPorIdExpedientePrevision(expedientePrevisionId);
				if(beanExpedientePrevision.getId() != null){
					
					// 2. Recuperamos al Socio 
					if(beanExpedientePrevision.getId() != null){
						
						CuentaId cuentaIdSocio = new CuentaId();
						Cuenta cuentaSocio = new Cuenta();
						List<CuentaIntegrante> listaCuentaIntegranteSocio = null;
						cuentaIdSocio.setIntPersEmpresaPk(beanExpedientePrevision.getId().getIntPersEmpresaPk());
						cuentaIdSocio.setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
						
						//intTipoSolicitudExtra = beanExpedientePrevision.getIntParaTipoCaptacion();
						//intSubTipoSolicitudExtra = beanExpedientePrevision.getIntParaSubTipoOperacion();
						cuentaSocio.setId(cuentaIdSocio);
						//cuentaSocio = cuentaFacade.getCuentaPorId(cuentaIdSocio);
						cuentaSocio = cuentaFacade.getCuentaPorIdCuenta(cuentaSocio);
						//if(cuentaSocio != null){
							
							try {
								listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());

							} catch (Exception e) {
								log.info("error eliminar expediente--> "+e);
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
					}
					
					//3. recueramos los estados
					
					EstadoPrevision estadoPrevision = null;
					// Temporalmente
					if (beanExpedientePrevision.getListaEstadoPrevision() != null) {
							if (beanExpedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)) {
								
								setStrSolicitudPrevision(Constante.MANTENIMIENTO_ELIMINAR);
								estadoPrevision = new EstadoPrevision();
								estadoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO);
								estadoPrevision.setTsFechaEstado(new Timestamp(new Date().getTime()));
								estadoPrevision.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
								estadoPrevision.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
								estadoPrevision.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
								estadoPrevision.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
								beanExpedientePrevision.getListaEstadoPrevision().add(estadoPrevision);
							}
					}

					// 4.  reuperamos los requiisitos
					if (beanExpedientePrevision.getListaRequisitoPrevisionComp()!= null) {
						listaRequisitoPrevisionComp = beanExpedientePrevision.getListaRequisitoPrevisionComp();
					}
					
					previsionFacade.modificarExpedientePrevision(beanExpedientePrevision);
					
					limpiarCampos(event);
					limpiarFormSolicitudPrevision();
					limpiarMensajesIsValidoExpediente();
					cancelarGrabarSolicitud(event);
					//buscarSolicitudPrevision(event);
				}
			}
			
		}catch (Exception e) {
			System.out.println("Error al realizar eliminacion  de solicitud --> "+e);
		}		
	}

	
		

	/**
	 * Asociado al link de descarga de los docuemntos adjuntos.
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
	 * Valida la existencia del beneficairio en la lista de beneficiarios.
	 * Si ya existe devuelve TRUE.
	 * @return
	 */
	public Boolean existeBeneficiario (){
		
		Boolean blnExiste = Boolean.FALSE;
		List<BeneficiarioPrevision> lstBeneficiarios = null;
		try {
			lstBeneficiarios = new ArrayList<BeneficiarioPrevision>();
			lstBeneficiarios = beanExpedientePrevision.getListaBeneficiarioPrevision();
			
			for (BeneficiarioPrevision beneficiario : lstBeneficiarios) {
				if(beneficiario.getIntPersPersonaBeneficiario().compareTo(beneficiarioSeleccionado.getIntPersPersonaBeneficiario())==0){
					blnExiste = Boolean.TRUE;
					break;
				}	
			}

		} catch (Exception e) {
			log.error("Error en existeBeneficiario ---> "+e);
		}
		
		return blnExiste;
	}
	
	/**
	 * Se utliza para agregar el beneficiario seleccionado a la tabla del formulario principal
	 * @param event
	 */
	public void agregarBeneficiarioSocio(ActionEvent event) {
			BeneficiarioPrevision beneficiario = null;
			BeneficiarioPrevisionId beneficiarioId = null;
			CuentaDetalleBeneficio ctaCptoDetBeneficio = null;
		
		try {
			beneficiario = new BeneficiarioPrevision();
			beneficiarioId = new BeneficiarioPrevisionId();
			
			setStrMsgTxtAgregarBeneficiario("");
			
			if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null &&
				!beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
				
				if(!existeBeneficiario()){
					beneficiario.setId(beneficiarioId);
					beneficiario.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
					beneficiario.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
					beneficiario.getId().setIntItemExpediente(beanExpedientePrevision.getId().getIntItemExpediente());
					beneficiario.setIntItemViculo(beneficiarioSeleccionado.getIntItemViculo());
					beneficiario.setIntTipoViculo(beneficiarioSeleccionado.getIntTipoViculo());
					beneficiario.setIntParaEstado(beneficiarioSeleccionado.getPersona().getIntEstadoCod());
					beneficiario.setIntPersPersonaBeneficiario(beneficiarioSeleccionado.getIntPersPersonaBeneficiario());
					beneficiario.setPersona(beneficiarioSeleccionado.getPersona());
					if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))!=0){
						ctaCptoDetBeneficio = recuperarPorcentajeBeneficioPorBeneficiarioRetiroSepelio(beneficiario);	
					}
					if(ctaCptoDetBeneficio != null){
						beneficiario.setCuentaDetalleBeneficio(ctaCptoDetBeneficio);
						beneficiario.setBdPorcentajeBeneficio(ctaCptoDetBeneficio.getBdPorcentaje());
					}

					beanExpedientePrevision.getListaBeneficiarioPrevision().add(beneficiario);//ct)add(beneficiarioSeleccionado);
					setStrMsgTxtAgregarBeneficiario("");
					calcularPorcentajesBeneficiariosSegunTipoSolicitud(event);
				}else{
					setStrMsgTxtAgregarBeneficiario("Beneficiario ya se encuentra ingresado.");
				}

			}else{
					beanExpedientePrevision.setListaBeneficiarioPrevision(new ArrayList<BeneficiarioPrevision>());
					
					beneficiario.setId(beneficiarioId);
					beneficiario.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
					beneficiario.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
					beneficiario.getId().setIntItemExpediente(beanExpedientePrevision.getId().getIntItemExpediente());
					beneficiario.setIntItemViculo(beneficiarioSeleccionado.getIntItemViculo());
					beneficiario.setIntTipoViculo(beneficiarioSeleccionado.getIntTipoViculo());
					beneficiario.setIntParaEstado(beneficiarioSeleccionado.getPersona().getIntEstadoCod());
					beneficiario.setIntPersPersonaBeneficiario(beneficiarioSeleccionado.getIntPersPersonaBeneficiario());
					beneficiario.setPersona(beneficiarioSeleccionado.getPersona());
					ctaCptoDetBeneficio = recuperarPorcentajeBeneficioPorBeneficiarioRetiroSepelio(beneficiario);
					if(ctaCptoDetBeneficio != null){
						beneficiario.setCuentaDetalleBeneficio(ctaCptoDetBeneficio);
						beneficiario.setBdPorcentajeBeneficio(ctaCptoDetBeneficio.getBdPorcentaje());
					}
					
					beanExpedientePrevision.getListaBeneficiarioPrevision().add(beneficiario);//ct)add(beneficiarioSeleccionado);
					setStrMsgTxtAgregarBeneficiario("");
					calcularPorcentajesBeneficiariosSegunTipoSolicitud(event);

			}

			} catch (Exception e) {
				log.error("Error en agregarBeneficiarioSocio ---> " + e);
				e.printStackTrace();
			} 
	}
	
	/**
	 * Redirige la ejecucion del metodo calcularMontosPorcentajeBeneficiarios(SEP) o calcularMontosPorcentajeBeneficiariosRetiro(RET).
	 * Segun el tipo de solcitud.
	 */
	public void calcularPorcentajesBeneficiariosSegunTipoSolicitud(ActionEvent event){
		if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
			calcularMontosPorcentajeBeneficiariosSepelio(event);
		}else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
			calcularMontosPorcentajeBeneficiariosRetiro();
		}	
	}
	
	/**
	 * Se utliza para agregar el fallecido seleccionado a la tabla del formulario principal
	 * @param event
	 */
	public void agregarFallecidoSocio(ActionEvent event) {
			try {
				if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)){
					
					
				}
				else if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
	
				}else{
					// falta el caso en que no sea ni AES o SEPELIO
				}

				//if(beneficiarioSeleccionado != null && blnExisteBeneficiario == false){
				if(fallecidoSeleccionado != null){	
					if(!beanExpedientePrevision.getListaFallecidoPrevision().isEmpty()){
						for(int f=0; f<beanExpedientePrevision.getListaFallecidoPrevision().size();f++){
							if(beanExpedientePrevision.getListaFallecidoPrevision().get(f).getIntPersPersonaFallecido().
								compareTo(fallecidoSeleccionado.getIntPersPersonaFallecido()) != 0){
								
								FallecidoPrevision fallecido = new FallecidoPrevision();
								FallecidoPrevisionId fallecidoId = new FallecidoPrevisionId();
								
								fallecido.setId(fallecidoId);
								fallecido.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
								fallecido.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
								fallecido.getId().setIntItemExpediente(beanExpedientePrevision.getId().getIntItemExpediente());
								fallecido.setIntItemViculo(fallecidoSeleccionado.getIntItemViculo());
								fallecido.setIntTipoViculo(fallecidoSeleccionado.getIntTipoViculo());
								fallecido.setIntParaEstado(fallecidoSeleccionado.getPersona().getIntEstadoCod());
								fallecido.setIntPersPersonaFallecido(fallecidoSeleccionado.getIntPersPersonaFallecido());
								fallecido.setPersona(fallecidoSeleccionado.getPersona());

								//beanExpedientePrevision.setListaBeneficiarioPrevision(new ArrayList<BeneficiarioPrevision>());
								beanExpedientePrevision.getListaFallecidoPrevision().add(fallecido);
								setStrMsgTxtAgregarBeneficiario("");

							}
						}
	
					} else{
						
						FallecidoPrevision fallecido = new FallecidoPrevision();
						FallecidoPrevisionId fallecidoId = new FallecidoPrevisionId();
						
						fallecido.setId(fallecidoId);
						fallecido.getId().setIntPersEmpresaPrevision(beanExpedientePrevision.getId().getIntPersEmpresaPk());
						fallecido.getId().setIntCuenta(beanExpedientePrevision.getId().getIntCuentaPk());
						fallecido.getId().setIntItemExpediente(beanExpedientePrevision.getId().getIntItemExpediente());
						fallecido.setIntItemViculo(fallecidoSeleccionado.getIntItemViculo());
						fallecido.setIntTipoViculo(fallecidoSeleccionado.getIntTipoViculo());
						fallecido.setIntParaEstado(fallecidoSeleccionado.getPersona().getIntEstadoCod());
						fallecido.setIntPersPersonaFallecido(fallecidoSeleccionado.getIntPersPersonaFallecido());
						fallecido.setPersona(fallecidoSeleccionado.getPersona());

						//beanExpedientePrevision.setListaBeneficiarioPrevision(new ArrayList<BeneficiarioPrevision>());
						beanExpedientePrevision.getListaFallecidoPrevision().add(fallecido);
						setStrMsgTxtAgregarBeneficiario("");
						
						
					}
				} 
				
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error: " + e);
			}

	}
	

	/**
	 * Realiza la modificacion de del porcentaje del beneficario.
	 * En caso no existe en la tabla ctadetallebeneficio, se genera un nuevo registro.
	 * @param event
	 */
	
	public void modificarBeneficiario(ActionEvent event) {
		String rowKey = getRequestParameter("rowKeyBeneficiario");
		BeneficiarioPrevision nuevoBeneficiarioPrevision = null;
		List<BeneficiarioPrevision> listaBeneficiarioPrevisionSol = null;
		CuentaDetalleBeneficio ctaDetBeneficio = null;
		CuentaDetalleBeneficioId ctaDetBeneficioId = null;
		CuentaConcepto cuentaConceptoGral = null;
		try {
			if (beanExpedientePrevision.getListaBeneficiarioPrevision() != null) {
				listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				for (int i = 0; i < listaBeneficiarioPrevisionSol.size(); i++) {
					if (Integer.parseInt(rowKey) == i) {
						BeneficiarioPrevision beneficiarioPrevision = listaBeneficiarioPrevisionSol.get(i);
						
						if(beneficiarioPrevision.getCuentaDetalleBeneficio() != null){
							// hacemos la consulta si existe en ctadetbeneficio si esiste el beneficiario a modificar.
							
							ctaDetBeneficio = conceptoFacade.getCuentaConceptoDetallePorPk(beneficiarioPrevision.getCuentaDetalleBeneficio());
							
							if(ctaDetBeneficio != null){
								
								ctaDetBeneficio = new CuentaDetalleBeneficio();
								
								ctaDetBeneficio=beneficiarioPrevision.getCuentaDetalleBeneficio();
								ctaDetBeneficio.setBdPorcentaje(beneficiarioPrevision.getBdPorcentajeBeneficio());
								ctaDetBeneficio=conceptoFacade.modificarCuentaDetalleBeneficio(ctaDetBeneficio);
								
								nuevoBeneficiarioPrevision = new BeneficiarioPrevision();
								nuevoBeneficiarioPrevision = listaBeneficiarioPrevisionSol.get(i);
								nuevoBeneficiarioPrevision.setBdPorcentajeBeneficio(ctaDetBeneficio.getBdPorcentaje());
								nuevoBeneficiarioPrevision.setCuentaDetalleBeneficio(ctaDetBeneficio);
								
								listaBeneficiarioPrevisionSol.remove(i);
								listaBeneficiarioPrevisionSol.add(i, nuevoBeneficiarioPrevision);

								break;

							}

						}else{	
							/*
								public static final String PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO = "2";
								public static final String PARAM_T_TIPOSOLICITUDPREVISION_AES = "11";
								public static final String PARAM_T_TIPOSOLICITUDPREVISION_RETIRO = "3";
								
								public static final Integer PARAM_T_CUENTACONCEPTO_APORTES = 1;
								public static final Integer PARAM_T_CUENTACONCEPTO_SEPELIO = 2;
								public static final Integer PARAM_T_CUENTACONCEPTO_RETIRO = 3;
	
							*/
								if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
									cuentaConceptoGral = recuperarCuentaConceptoBase(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO));
									
								}else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
										cuentaConceptoGral = recuperarCuentaConceptoBase(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO));
									
								}else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
										cuentaConceptoGral = recuperarCuentaConceptoBase(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES));	
								}

								if(cuentaConceptoGral != null){
									ctaDetBeneficio = new CuentaDetalleBeneficio();
									ctaDetBeneficioId = new CuentaDetalleBeneficioId();
									ctaDetBeneficioId.setIntCuentaPk(cuentaConceptoGral.getId().getIntCuentaPk());
									ctaDetBeneficioId.setIntItemCuentaConcepto(cuentaConceptoGral.getId().getIntItemCuentaConcepto());
									ctaDetBeneficioId.setIntPersEmpresaPk(cuentaConceptoGral.getId().getIntPersEmpresaPk());
									ctaDetBeneficio.setId(ctaDetBeneficioId);
									ctaDetBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
									ctaDetBeneficio.setPersPersonausuarioPk(usuario.getPersona().getIntIdPersona());
									ctaDetBeneficio.setTsFecharegistro(new Timestamp(new Date().getTime()));
									ctaDetBeneficio.setIntItemVinculo(beneficiarioPrevision.getIntItemViculo());
									ctaDetBeneficio.setBdPorcentaje(beneficiarioPrevision.getBdPorcentajeBeneficio());
									
									// CONSULTAR CON JUDITH
									//ctaDetBeneficio.setIntParaTipoConceptoCod(intParaTipoConceptoCod);
									//ctaDetBeneficio.setIntItemConcepto(intItemConcepto);

									ctaDetBeneficio=conceptoFacade.grabarCuentaDetalleBeneficio(ctaDetBeneficio);
									
									nuevoBeneficiarioPrevision = new BeneficiarioPrevision();
									nuevoBeneficiarioPrevision = listaBeneficiarioPrevisionSol.get(i);
									nuevoBeneficiarioPrevision.setBdPorcentajeBeneficio(ctaDetBeneficio.getBdPorcentaje());
									nuevoBeneficiarioPrevision.setCuentaDetalleBeneficio(ctaDetBeneficio);
									
									listaBeneficiarioPrevisionSol.remove(i);
									listaBeneficiarioPrevisionSol.add(i, nuevoBeneficiarioPrevision);
		
									break;
								}
						}
	
					}
				}
				calcularMontosPorcentajeBeneficiariosSepelio(event);
				if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
					recalcularMontosRetiro(event);	
				}else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
					recalcularMontosSepelio(event);	
				}
			}
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	
	/**
	 * Previo al grabado se recalcula los montos de los beneficiarios
	 * @param event
	 */
	public void modificarTodosLosBeneficiarios(ActionEvent event) {
		//String rowKey = getRequestParameter("rowKeyBeneficiario");
		BeneficiarioPrevision nuevoBeneficiarioPrevision = null;
		List<BeneficiarioPrevision> listaBeneficiarioPrevisionSol = null;
		CuentaDetalleBeneficio ctaDetBeneficio = null;
		CuentaDetalleBeneficioId ctaDetBeneficioId = null;
		CuentaConcepto cuentaConceptoGral = null;
		try {
			if (beanExpedientePrevision.getListaBeneficiarioPrevision() != null) {
				listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				for (int i = 0; i < listaBeneficiarioPrevisionSol.size(); i++) {
					//if (Integer.parseInt(rowKey) == i) {
						BeneficiarioPrevision beneficiarioPrevision = listaBeneficiarioPrevisionSol.get(i);
						
						if(beneficiarioPrevision.getCuentaDetalleBeneficio() != null){
							// hacemos la consulta si existe en ctadetbeneficio si esiste el beneficiario a modificar.
							
							ctaDetBeneficio = conceptoFacade.getCuentaConceptoDetallePorPk(beneficiarioPrevision.getCuentaDetalleBeneficio());
							
							if(ctaDetBeneficio != null){
								
								ctaDetBeneficio = new CuentaDetalleBeneficio();
								
								ctaDetBeneficio=beneficiarioPrevision.getCuentaDetalleBeneficio();
								ctaDetBeneficio.setBdPorcentaje(beneficiarioPrevision.getBdPorcentajeBeneficio());
								ctaDetBeneficio=conceptoFacade.modificarCuentaDetalleBeneficio(ctaDetBeneficio);
								
								nuevoBeneficiarioPrevision = new BeneficiarioPrevision();
								nuevoBeneficiarioPrevision = listaBeneficiarioPrevisionSol.get(i);
								nuevoBeneficiarioPrevision.setBdPorcentajeBeneficio(ctaDetBeneficio.getBdPorcentaje());
								nuevoBeneficiarioPrevision.setCuentaDetalleBeneficio(ctaDetBeneficio);
								
								listaBeneficiarioPrevisionSol.remove(i);
								listaBeneficiarioPrevisionSol.add(i, nuevoBeneficiarioPrevision);

								//break;

							}

						}else{	
							/*
								public static final String PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO = "2";
								public static final String PARAM_T_TIPOSOLICITUDPREVISION_AES = "11";
								public static final String PARAM_T_TIPOSOLICITUDPREVISION_RETIRO = "3";
								
								public static final Integer PARAM_T_CUENTACONCEPTO_APORTES = 1;
								public static final Integer PARAM_T_CUENTACONCEPTO_SEPELIO = 2;
								public static final Integer PARAM_T_CUENTACONCEPTO_RETIRO = 3;
	
							*/
								if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
									cuentaConceptoGral = recuperarCuentaConceptoBase(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO));
									
								}else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
										cuentaConceptoGral = recuperarCuentaConceptoBase(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO));
									
								}else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
										cuentaConceptoGral = recuperarCuentaConceptoBase(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES));	
								}

								if(cuentaConceptoGral != null){
									ctaDetBeneficio = new CuentaDetalleBeneficio();
									ctaDetBeneficioId = new CuentaDetalleBeneficioId();
									ctaDetBeneficioId.setIntCuentaPk(cuentaConceptoGral.getId().getIntCuentaPk());
									ctaDetBeneficioId.setIntItemCuentaConcepto(cuentaConceptoGral.getId().getIntItemCuentaConcepto());
									ctaDetBeneficioId.setIntPersEmpresaPk(cuentaConceptoGral.getId().getIntPersEmpresaPk());
									ctaDetBeneficio.setId(ctaDetBeneficioId);
									ctaDetBeneficio.setParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
									ctaDetBeneficio.setPersPersonausuarioPk(usuario.getPersona().getIntIdPersona());
									ctaDetBeneficio.setTsFecharegistro(new Timestamp(new Date().getTime()));
									ctaDetBeneficio.setIntItemVinculo(beneficiarioPrevision.getIntItemViculo());
									ctaDetBeneficio.setBdPorcentaje(beneficiarioPrevision.getBdPorcentajeBeneficio());
									
									// CONSULTAR CON JUDITH
									//ctaDetBeneficio.setIntParaTipoConceptoCod(intParaTipoConceptoCod);
									//ctaDetBeneficio.setIntItemConcepto(intItemConcepto);

									ctaDetBeneficio=conceptoFacade.grabarCuentaDetalleBeneficio(ctaDetBeneficio);
									
									nuevoBeneficiarioPrevision = new BeneficiarioPrevision();
									nuevoBeneficiarioPrevision = listaBeneficiarioPrevisionSol.get(i);
									nuevoBeneficiarioPrevision.setBdPorcentajeBeneficio(ctaDetBeneficio.getBdPorcentaje());
									nuevoBeneficiarioPrevision.setCuentaDetalleBeneficio(ctaDetBeneficio);
									
									listaBeneficiarioPrevisionSol.remove(i);
									listaBeneficiarioPrevisionSol.add(i, nuevoBeneficiarioPrevision);
		
									//break;
								}
						}
	
				//	}
				}
				calcularMontosPorcentajeBeneficiariosSepelio(event);
				if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
					recalcularMontosRetiro(event);	
				}else if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
					recalcularMontosSepelio(event);
				}	
			}
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}

	
	/**
	 * 
	 * @param event
	 */
	public void removeBeneficiario(ActionEvent event) {
		String rowKey = getRequestParameter("rowKeyBeneficiario");
//		BeneficiarioPrevision beneficiarioTmp = null;
		List<BeneficiarioPrevision> listaBeneficiarioPrevisionSol = null;
		try {
			
			//intTipoSolicitud = intTipoSolicitudExtra;
			//intSubTipoSolicitud = intSubTipoSolicitudExtra;
			//regenerarCombos();
			
			if (beanExpedientePrevision.getListaBeneficiarioPrevision() != null) {
				listaBeneficiarioPrevisionSol = beanExpedientePrevision.getListaBeneficiarioPrevision();
				for (int i = 0; i < listaBeneficiarioPrevisionSol.size(); i++) {
					if (Integer.parseInt(rowKey) == i) {
						BeneficiarioPrevision beneficiarioPrevision = listaBeneficiarioPrevisionSol.get(i);
						if (beneficiarioPrevision.getId() != null) {
							beneficiarioPrevision = listaBeneficiarioPrevisionSol.get(i);
							beneficiarioPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						}
						listaBeneficiarioPrevisionSol.remove(i);
						break;
					}
				}
//				if (beneficiarioTmp != null) {
//					listaBeneficiarioPrevisionSol.add(beneficiarioTmp);
//				}
				porcetanjesSuman100();
				
			}
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void removeFallecido(ActionEvent event) {
		String rowKey = getRequestParameter("rowKeyFallecido");
//		FallecidoPrevision fallecidoTemp = null;
		//BeneficiarioPrevision beneficiarioTmp = null;
		//List<BeneficiarioPrevision> listaBeneficiarioPrevisionSol = null;
		List<FallecidoPrevision> listFallecidoPrevisionSol = null;
		try {
			if (beanExpedientePrevision.getListaBeneficiarioPrevision() != null) {
				listFallecidoPrevisionSol = beanExpedientePrevision.getListaFallecidoPrevision();
				for (int i = 0; i < listFallecidoPrevisionSol.size(); i++) {
					if (Integer.parseInt(rowKey) == i) {
						FallecidoPrevision fallecidoPrevision = listFallecidoPrevisionSol.get(i);
						if (fallecidoPrevision.getId() != null) {
							fallecidoPrevision = listFallecidoPrevisionSol.get(i);
							fallecidoPrevision.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						}
						listFallecidoPrevisionSol.remove(i);
						break;
					}
				}
//				if (fallecidoTemp != null) {
//					listFallecidoPrevisionSol.add(fallecidoTemp);
//				}
				//blnExisteBeneficiario=true;
				//porcetanjesSuman100();
			}
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	
	/**
	 * Se ejecuta cuando se modifica la seleccion de tipo de fallecimiento.
	 * Ademas muetsra el check de No Relacion de fallecimiento.
	 * @param event
	 */
	public void limpiarFallecidos(ActionEvent event) {

		try {
			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
				if (beanExpedientePrevision.getListaFallecidoPrevision() != null) {
					beanExpedientePrevision.getListaFallecidoPrevision().clear();
				}else{
					beanExpedientePrevision.setListaFallecidoPrevision(new ArrayList<FallecidoPrevision>());
				}	
			}

			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
				&& intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
				   blnIsSepelioTitular = 	Boolean.TRUE;
			}else{
				 blnIsSepelioTitular = 	Boolean.FALSE;
			}
			

		} catch (Exception e) {
			log.error("Error en limpiarFallecidos ---> " + e);
		}
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void limpiarGrillasSepelio(ActionEvent event) {
		try {
			limpiarFallecidos(event);
			limpiarBeneficiarios(event);
		} catch (Exception e) {
			log.error("Error en limpiarFallecidos ---> " + e);
		}
	}
	
	
	/**
	 * Se ejecuta cuando se modifica la seleccion de tipo de fallecimiento.
	 * Ademas muetsra el check de No Relacion de fallecimiento.
	 * @param event
	 */
	public void limpiarBeneficiarios(ActionEvent event) {

		try {
				if (beanExpedientePrevision.getListaBeneficiarioPrevision() != null
						&& !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()) {
					beanExpedientePrevision.getListaBeneficiarioPrevision().clear();
				}else{
					beanExpedientePrevision.setListaBeneficiarioPrevision(new ArrayList<BeneficiarioPrevision>());
				}	

		} catch (Exception e) {
			log.error("Error en limpiarFallecidos ---> " + e);
		}
	}
	/*public void setMontoSolicitado(ActionEvent event) {
		log.info("----------setMontoSolicitado----------");
		String strMontoSolicitado = getRequestParameter("bdMontoSolicitado");
		try {
			if (strMontoSolicitado != null) {
				BigDecimal bdMontoSolicitado = new BigDecimal(
						strMontoSolicitado.equals("") ? "0.00"
								: strMontoSolicitado);
				beanExpedientePrevision.setBdMontoBrutoBeneficio(bdMontoSolicitado
						.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP));
			}
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}*/
	
	
	/**
	 * 
	 */
	private void cargarListaTablaSucursal() throws Exception{
		List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
		//Ordena la sucursal alafabeticamente
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
		
		listaTablaSucursal = new ArrayList<Tabla>();
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
	
	/**
	 * 
	 */
	public void seleccionarSucursalBusqueda(){
		try{
			if(intIdSucursalFiltro.intValue()>0){
				listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(intIdSucursalFiltro);
			}else{
				listaSubsucursal = new ArrayList<Subsucursal>();
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
				listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
				strSubsucursalSocio = listaSubsucursal.get(0).getStrDescripcion();
				
				System.out.println("SUBSUCURSAL ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
				System.out.println("SUCURSAL ---> "+beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
			}else{
				listaSubsucursal = new ArrayList<Subsucursal>();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
		
	/*public void reconfigurarCampos(){
		
		blnDeshabilitar = true;
		
		if(intTipoSolicitud.intValue() == Constante.PARAM_T_TIPO_PREVISION_AES){
			//blnCampoHabil = false;
			blnBeneficiarioNormal = true;
			blnBeneficiarioSepelio = false;

		} else if(intTipoSolicitud.intValue() == Constante.PARAM_T_TIPO_PREVISION_SEPELIO){
					//blnCampoHabil = true;
					blnBeneficiarioNormal = false;
					blnBeneficiarioSepelio = true;

		} else if(intTipoSolicitud.intValue() == Constante.PARAM_T_TIPO_PREVISION_RETIRO){
					//blnCampoHabil = false;
					blnBeneficiarioNormal = false;
					blnBeneficiarioSepelio = false;
			
			
		}
	}*/	
		
	
	
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
	 * Calcula segun Escala el valor  de solicitud y % de Gastos Administrativos para la previsiond e sepelio
	 */
	public void cargarValorGastosAdministrativosSepelio(){
		List<Requisito>listaConfCapRequisitoTemp = null;
		List<Requisito> listaConfCapRequisito = null;
		Integer intNroCuotasPagadasTotal = 0;
		CuentaConcepto ctaCtoRecuperada = null;
		BigDecimal bdMontoCuota = BigDecimal.ZERO;
		BigDecimal bdNroCuotasPagadas = BigDecimal.ZERO;
		BigDecimal bdMontoTotalPagado = BigDecimal.ZERO;
		//Requisito confCapRequisito = null;
		Integer intPeriodoFallecimiento = 0;
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		String strPeriodoFallecimiento = "";	
		//Boolean blnProcede = Boolean.FALSE;
		try {

			strPeriodoFallecimiento = sdfPeriodo.format(beanExpedientePrevision.getDtFechaFallecimiento());
			intPeriodoFallecimiento = Integer.parseInt(strPeriodoFallecimiento);
			listaConfCapRequisitoTemp = captacionFacade.getListaRequisitoPorPKCaptacion(beanCaptacion.getId());

			if(listaConfCapRequisitoTemp != null && !listaConfCapRequisitoTemp.isEmpty()){
				
				
				// recuperamos la cta cto sepelio  ---------- A
				ctaCtoRecuperada = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
				if(ctaCtoRecuperada != null){
					if(ctaCtoRecuperada.getListaCuentaConceptoDetalle() != null && !ctaCtoRecuperada.getListaCuentaConceptoDetalle().isEmpty()){
						CuentaConceptoDetalle ctaCptoDetalle = new CuentaConceptoDetalle();
						ctaCptoDetalle = ctaCtoRecuperada.getListaCuentaConceptoDetalle().get(0);
						
						bdMontoCuota  = ctaCptoDetalle.getBdMontoConcepto();
						
						// recuperamos todas los pagoconcepto del tipo de sepelio  ----- B
							List<ConceptoPago> listaConceptoPago = null;
							listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ctaCptoDetalle.getId());
							if(listaConceptoPago != null && !listaConceptoPago.isEmpty()){
								for (ConceptoPago conceptoPago : listaConceptoPago) {
									if(conceptoPago.getIntPeriodo().compareTo(intPeriodoFallecimiento)<=0){
										bdMontoTotalPagado = bdMontoTotalPagado.add(conceptoPago.getBdMontoPago());	
									}
									
										
								}
							}
					}
					
				}

				// dividimos... B / A para hallar el nro aprooximadas de cuotas pagadas 
				if(bdMontoCuota.compareTo(BigDecimal.ZERO)!=0 && bdMontoTotalPagado.compareTo(BigDecimal.ZERO)!=0){
					bdNroCuotasPagadas = bdMontoTotalPagado.divide(bdMontoCuota, 2,RoundingMode.HALF_UP);
				}else{
					bdNroCuotasPagadas = BigDecimal.ZERO;
				}
				
				
				intNroCuotasPagadasTotal = bdNroCuotasPagadas.intValue();

				listaConfCapRequisito = new ArrayList<Requisito>();
				
				Integer intEquivalencia = recuperarEquivalenciaSubTipoPrevision(intSubTipoSolicitud);
				
				
				for (Requisito requisito : listaConfCapRequisitoTemp) {
					System.out.println("itemrequisito: "+ requisito.getId().getIntItemRequisito() + " / estado: "+requisito.getIntParaEstadoCod()+
							"requisito.getId().getIntParaTipoCaptacionCod()"+requisito.getId().getIntParaTipoCaptacionCod());
					if(requisito.getId().getIntParaTipoCaptacionCod().compareTo(beanCaptacion.getId().getIntParaTipoCaptacionCod())==0){
						if(requisito.getId().getIntParaTipoRequisitoBenef().compareTo(intEquivalencia)==0){
							if(requisito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								listaConfCapRequisito.add(requisito);
							}
						}	
					}
					
				}

				// Diferencamos el manejo por nro de registros de la lista.
				// EN TEORIA, SE SUPONE....
				// Solo Titular tendria mas de un registro y se manejaria por rangos (Caso 1)
				// EN caso contrario se respetaria el Tipod e CUota. <> <= >= etc	 (Caso 2)

				// Recuperar el monto de la solicitud.
				if(listaConfCapRequisito != null && !listaConfCapRequisito.isEmpty()){
					Integer intTamanno = listaConfCapRequisito.size();
					
					// CASO 1 :
					if(intTamanno.compareTo(1)!=0){
						if((intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0)
							&& (intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0)){
								
								//Ordenamos los requisiitos por nrodecuota
								Collections.sort(listaConfCapRequisito, new Comparator<Requisito>(){
									public int compare(Requisito uno, Requisito otro) {
										return otro.getIntNumeroCuota().compareTo(uno.getIntNumeroCuota());
									}
								});
									
								List<Requisito> lstConfCapRequisitoDesc = new ArrayList<Requisito>();
								int intTamanolista = listaConfCapRequisito.size();
								intTamanolista = intTamanolista -1;
								for(int w=0; w<listaConfCapRequisito.size();w++){	
									lstConfCapRequisitoDesc.add(w, listaConfCapRequisito.get(intTamanolista));
									intTamanolista-- ;
								}


								List<Tabla> lstEscalaModidifcada = null;
								lstEscalaModidifcada = generarListaRangosTabla(lstConfCapRequisitoDesc);
								
								if(lstEscalaModidifcada != null && !lstEscalaModidifcada.isEmpty()){
									Collections.sort(listaConfCapRequisito, new Comparator<Requisito>(){
										public int compare(Requisito uno, Requisito otro) {
											return otro.getIntNumeroCuota().compareTo(uno.getIntNumeroCuota());
										}
									});
									
									// Para pruebas xxx
									//intNroCuotasPagadasTotal = 52;
									for (Tabla tabla : lstEscalaModidifcada) {
										// * En StrAbreviaturase guardara el valor	---> monto / 
										// * En StrIdAgrupamientoA guardara el valor 	---> minimo / 
										// * En StrIdAgrupamientoB guardara el valor 	---> maximo / 
										// * En StrDescripcion guardara el valor 		---> porcentaje / 
										// * En setIntIdMaestro guardara el valor		---> nro cuotas.
										Integer intMinimo = new Integer(tabla.getStrIdAgrupamientoA());
										Integer intMaximo = new Integer(tabla.getStrIdAgrupamientoB());
										
										if(intNroCuotasPagadasTotal.compareTo(intMinimo)>=0
											&& intNroCuotasPagadasTotal.compareTo(intMaximo)<=0){
											bdMontoCalculadoBeneficio = BigDecimal.ZERO;
											bdMontoCalculadoBeneficio = new BigDecimal(tabla.getStrAbreviatura());
											beanExpedientePrevision.setBdMontoBrutoBeneficio(new BigDecimal(tabla.getStrAbreviatura()));
											bdPorcGastosAdministrativo = new BigDecimal(tabla.getStrDescripcion());
											//blnProcede = Boolean.TRUE;
											break;
										}
										
									}
									
								}
								}

					}else{
	
						if((intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
							&& intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_CONYUGE)==0)
							|| (intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
							&& intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_HIJOS)==0)
							|| (intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
							&& intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_PADRES)==0)){

							// public static final Integer PARAM_MAYOR_IGUAL= 1;
							// public static final Integer PARAM_MENOR_IGUAL= 2;
							// public static final Integer PARAM_IGUAL= 3;
							
							if(listaConfCapRequisito != null && !listaConfCapRequisito.isEmpty()){
								Requisito requisito = listaConfCapRequisito.get(0);
									if(requisito.getIntParaTipoMaxMinCod().compareTo(Constante.PARAM_MAYOR_IGUAL)==0){
										if(intNroCuotasPagadasTotal.compareTo(requisito.getIntNumeroCuota())>=0){
											bdMontoCalculadoBeneficio = BigDecimal.ZERO;
											bdMontoCalculadoBeneficio = requisito.getBdBeneficio();
											beanExpedientePrevision.setBdMontoBrutoBeneficio(bdMontoCalculadoBeneficio);
											bdPorcGastosAdministrativo = requisito.getBdGastoAdministrativo();	
										}
										
									}else if(requisito.getIntParaTipoMaxMinCod().compareTo(Constante.PARAM_MENOR_IGUAL)==0){
											if(intNroCuotasPagadasTotal.compareTo(requisito.getIntNumeroCuota())<=0){
												bdMontoCalculadoBeneficio = BigDecimal.ZERO;
												bdMontoCalculadoBeneficio = requisito.getBdBeneficio();
												beanExpedientePrevision.setBdMontoBrutoBeneficio(bdMontoCalculadoBeneficio);
												bdPorcGastosAdministrativo = requisito.getBdGastoAdministrativo();	
											}
										
									}else if(requisito.getIntParaTipoMaxMinCod().compareTo(Constante.PARAM_IGUAL)==0){
										if(intNroCuotasPagadasTotal.compareTo(requisito.getIntNumeroCuota())==0){
											bdMontoCalculadoBeneficio = BigDecimal.ZERO;
											bdMontoCalculadoBeneficio = requisito.getBdBeneficio();
											beanExpedientePrevision.setBdMontoBrutoBeneficio(bdMontoCalculadoBeneficio);
											bdPorcGastosAdministrativo = requisito.getBdGastoAdministrativo();	
										}
									}
							}
						}
					}	
				}
	
			}
			/*if(){
				
			}*/
			
		} catch (BusinessException e) {
			log.error("Error cargarValorGastosAdministrativos --> "+e);
		}
		//return blnProcede;
		
	}
	
	
	/**
	 * Devuelve la equivalnecia entre parametros 154 Y 49 para sepelio
	 * @param intSubTipoSol
	 * @return
	 */
	public Integer recuperarEquivalenciaSubTipoPrevision(Integer intSubTipoSol){
		Integer intEquivalencia = new Integer(0);
		try {
			
			if(intSubTipoSol != null){
				
				if(intSubTipoSol.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
					intEquivalencia = Constante.PARAM_T_EQUIVALENCIA_PREVISION_SEPELIO_FALLECIMIENTO_TITULAR;
				}else if(intSubTipoSol.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_CONYUGE)==0){
					intEquivalencia = Constante.PARAM_T_EQUIVALENCIA_PREVISION_SEPELIO_FALLECIMIENTO_CONYUGE;
				}else if(intSubTipoSol.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_PADRES)==0){
					intEquivalencia = Constante.PARAM_T_EQUIVALENCIA_PREVISION_SEPELIO_FALLECIMIENTO_PADRES;
				}else if(intSubTipoSol.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_HIJOS)==0){
					intEquivalencia = Constante.PARAM_T_EQUIVALENCIA_PREVISION_SEPELIO_FALLECIMIENTO_HIJOS;
				}else{
					intEquivalencia = -1;
				}
			}
			
		} catch (Exception e) {
			log.error("Error en recuperarEquivalenciaSubTipoPrevision ---> "+e);
		}
		return intEquivalencia;
	}
	
	
	/**
	 * Convierte la lista de requisiitos recuperada (Escala) en una lista Tabla con los rangos a  manejar.
	 * Solo para propositos de calculo de Monto y porcentaje de Gastos Administrativos
	 * Se utilizo como dummy la clase Tabla:
	 * En StrAbreviaturase guardara el valor	---> monto / 
	 * En StrIdAgrupamientoA guardara el valor 	---> minimo / 
	 * En StrIdAgrupamientoB guardara el valor 	---> maximo / 
	 * En StrDescripcion guardara el valor 		---> porcentaje / 
	 * En setIntIdMaestro guardara el valor		---> nro cuotas.
	 * @param listaRequisito
	 * @return
	 */
	public List<Tabla> generarListaRangosTabla (List<Requisito> listaRequisito){
		List<Tabla> lstTablaRangos = null;
		
		// Se utilizo como dummy la clase tabla:
		// =======================================================
		// En StrIdAgrupamientoA guardara el valor 	--- minimo / 
		// En StrIdAgrupamientoB guardara el valor 	--- maximo / 
		// En StrAbreviaturase guardara el valor	--- monto / 
		// En StrDescripcion guardara el valor 		--- porcentaje / 
		Integer intTamanno = 0;
		
		try {
			
			if(listaRequisito != null && !listaRequisito.isEmpty()){
				
			intTamanno = listaRequisito.size();
			
			//Ordenamos los subtipos por int
			Collections.sort(listaRequisito, new Comparator<Requisito>(){
				public int compare(Requisito uno, Requisito otro) {
					return uno.getIntNumeroCuota().compareTo(otro.getIntNumeroCuota());
				}
			});
			
			for (Requisito req : listaRequisito) {
				System.out.println("CUOTA ********* "+req.getIntNumeroCuota());
			}

			lstTablaRangos = new ArrayList<Tabla>();
			
				for(int i=0; i<listaRequisito.size(); i++){
					Requisito requisito = new Requisito();
					requisito = listaRequisito.get(i);

					Tabla requisitoRango = null;
					
					// Si es el primer rango
					if(i==0){
						requisitoRango = new Tabla();
						requisitoRango.setStrIdAgrupamientoA("0");
						requisitoRango.setStrIdAgrupamientoB(""+(requisito.getIntNumeroCuota()-1));
						requisitoRango.setStrAbreviatura("0.00");
						requisitoRango.setStrDescripcion("0.00");

					}else{
							// Son rangos intermedios
							requisitoRango = new Tabla();
							requisitoRango.setStrIdAgrupamientoA(""+listaRequisito.get(i-1).getIntNumeroCuota());
							requisitoRango.setStrIdAgrupamientoB(""+(requisito.getIntNumeroCuota()-1));
							requisitoRango.setStrAbreviatura(""+listaRequisito.get(i-1).getBdBeneficio());
							requisitoRango.setStrDescripcion(""+listaRequisito.get(i-1).getBdGastoAdministrativo());
					}
					lstTablaRangos.add(requisitoRango);
	
				}
				
				// Recuperamos el ultimo registro
				// Formamos el ultimo rango y lo agregamos a al lista
				Tabla requsitoTablaFinal = new Tabla();
				
				requsitoTablaFinal.setStrIdAgrupamientoA(""+listaRequisito.get(intTamanno -1).getIntNumeroCuota());
				requsitoTablaFinal.setStrIdAgrupamientoB("9999");
				requsitoTablaFinal.setStrAbreviatura(""+listaRequisito.get(intTamanno -1).getBdBeneficio());
				requsitoTablaFinal.setStrDescripcion(""+listaRequisito.get(intTamanno -1).getBdGastoAdministrativo());
				
				lstTablaRangos.add(requsitoTablaFinal);
				
				System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
				for (Tabla tabla : lstTablaRangos) {
					System.out.println("INICIO ---> "+tabla.getStrIdAgrupamientoA());
					System.out.println("FINAL ---> "+tabla.getStrIdAgrupamientoB());
					System.out.println("MONTO ---> "+tabla.getStrAbreviatura());
					System.out.println("PORC ---> "+tabla.getStrDescripcion());
				}
				System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{");
			}

		} catch (Exception e) {
			log.error("Error generarListaRangosTabla ---> "+e);
		}
		return lstTablaRangos;
	}

	
	/**
	 * Asociado al boton RECALCULAR de solicitud de sepelio
	 */
	public void recalcularMontosSepelio(ActionEvent event){  
		try {
			calcularMontosPorcentajeBeneficiariosSepelio(event);
			porcetanjesSuman100();
			log.info("mensaje: "+strMsgTxtSumaPorcentaje);
		} catch (Exception e) {
			log.error("Error en recalcularMontosSepelio ---> "+e);
		}
	}
	
	
	/**
	 * Asociado al boton RECALCULAR de solicitud de retiro
	 */
	public void recalcularMontosRetiro(ActionEvent event){  
		try {
			calcularMontosPorcentajeBeneficiariosRetiro();
			porcetanjesSuman100();
		} catch (Exception e) {
			log.error("Error en recalcularMontosRetiro ---> "+e);
		}
	}
	
	/**
	 * Realiza caclculo del Monto del beneficio, segun escala o rango. 
	 * @param event
	 */
	public void calcularMontosPorcentajeBeneficiariosSepelio(ActionEvent event){  
//		CuentaConcepto cuentaConceptoSepelio = null;
		//List<CuentaConcepto> listaCuentaConcepto = null;
		//List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficioTemp = null;
		//CuentaId cuentaId = null;
//		BigDecimal porTotalBeneficiario= BigDecimal.ZERO ;
		BigDecimal bdTotal = BigDecimal.ZERO;
		BigDecimal bdGastosAdm = BigDecimal.ZERO;
		BigDecimal bdNeto = BigDecimal.ZERO;
		BigDecimal bdPorcentaje = BigDecimal.ZERO;
		
//		List<BeneficiarioPrevision> listaBeneficiarios = null;
		
		try {
			
//			cuentaConceptoSepelio = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
			
			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
				if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
					// 100% titular
					// recuperamos la lista con los % activos para cada beneficiario... aca se esta cayendo... no se xq...
					//listaCuentaDetalleBeneficioTemp =  conceptoFacade.getListaCuentaDetalleBeneficioPorPkConcepto(cuentaConceptoSepelio.getId());
					//if(listaCuentaDetalleBeneficioTemp != null){
						//recorremos la lista de detalles para vlaidarla contra el vinculo del beneficiario y obetener su % .
						if(beanExpedientePrevision.getListaBeneficiarioPrevision() != null
							&& !beanExpedientePrevision.getListaBeneficiarioPrevision().isEmpty()){
							
//							listaBeneficiarios = new ArrayList<BeneficiarioPrevision>();
//							listaBeneficiarios =  beanExpedientePrevision.getListaBeneficiarioPrevision();

						}
							
							for(int y=0;y<beanExpedientePrevision.getListaBeneficiarioPrevision().size();y++){
								
								bdPorcentaje=beanExpedientePrevision.getListaBeneficiarioPrevision().get(y).getBdPorcentajeBeneficio();
								if(bdPorcentaje != null){
									bdTotal = beanExpedientePrevision.getBdMontoBrutoBeneficio().multiply(bdPorcentaje).divide(new BigDecimal(100))   ;
									if(bdPorcGastosAdministrativo.compareTo(BigDecimal.ZERO)==0){
										bdGastosAdm = BigDecimal.ZERO;
									}else{
										bdGastosAdm = bdTotal.multiply(bdPorcGastosAdministrativo.divide(new BigDecimal(100)));
									}
									bdNeto = bdTotal.subtract(bdGastosAdm);
									
									beanExpedientePrevision.getListaBeneficiarioPrevision().get(y).setBdMontoTotal(bdTotal);
									beanExpedientePrevision.getListaBeneficiarioPrevision().get(y).setBdGastosAdministrativos(bdGastosAdm);
									beanExpedientePrevision.getListaBeneficiarioPrevision().get(y).setBdMontoNeto(bdNeto);
									
								}else{
									bdTotal =BigDecimal.ZERO;
									bdGastosAdm=BigDecimal.ZERO;
									bdNeto = BigDecimal.ZERO;
									beanExpedientePrevision.getListaBeneficiarioPrevision().get(y).setBdMontoTotal(bdTotal);
									beanExpedientePrevision.getListaBeneficiarioPrevision().get(y).setBdGastosAdministrativos(bdGastosAdm);
									beanExpedientePrevision.getListaBeneficiarioPrevision().get(y).setBdMontoNeto(bdNeto);
								}
							}
						porcetanjesSuman100();
					//} 

					
				}else{
						bdPorcentaje=new BigDecimal(100);
						if(bdPorcentaje != null){
							bdTotal = beanExpedientePrevision.getBdMontoBrutoBeneficio().multiply(bdPorcentaje).divide(new BigDecimal(100))   ;
							if(bdPorcGastosAdministrativo.compareTo(BigDecimal.ZERO)==0){
								bdGastosAdm = BigDecimal.ZERO;
							}else{
								bdGastosAdm = bdTotal.multiply(bdPorcGastosAdministrativo.divide(new BigDecimal(100)));
							}
							bdNeto = bdTotal.subtract(bdGastosAdm);
							
							beanExpedientePrevision.getListaBeneficiarioPrevision().get(0).setBdMontoTotal(bdTotal);
							beanExpedientePrevision.getListaBeneficiarioPrevision().get(0).setBdGastosAdministrativos(bdGastosAdm);
							beanExpedientePrevision.getListaBeneficiarioPrevision().get(0).setBdMontoNeto(bdNeto);
							beanExpedientePrevision.getListaBeneficiarioPrevision().get(0).setBdPorcentajeBeneficio(new BigDecimal(100));
						}
				}
			}
			//------------------------------------>
		} catch (Exception e) {
			log.error("Error calcularMontosPorcentajeBeneficiarios --> "+e);
		}
	}
	
	
	/**
	 * Recupera la cuenta detalle beneficio desde movimeinto en base al beneficiario por agregar.
	 * Aplica para Sepelio y Retiro.
	 * @param beneficiarioPrevision
	 * @return
	 */
	public CuentaDetalleBeneficio recuperarPorcentajeBeneficioPorBeneficiarioRetiroSepelio(BeneficiarioPrevision beneficiarioPrevision){
		
		CuentaConcepto cuentaConceptoUtil = null;
		List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficioTemp = null;
		CuentaDetalleBeneficio beneficio = null;
		try {
			
			if(intTipoSolicitud.compareTo(new  Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
				cuentaConceptoUtil = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
				
			}else if(intTipoSolicitud.compareTo(new  Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
				cuentaConceptoUtil = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
			}
			
			// recuperamos la lista con los % activos para cada beneficiario
			listaCuentaDetalleBeneficioTemp =  conceptoFacade.getListaCuentaDetalleBeneficioPorPkConcepto(cuentaConceptoUtil.getId());
			if(listaCuentaDetalleBeneficioTemp != null && !listaCuentaDetalleBeneficioTemp.isEmpty()){
				for (CuentaDetalleBeneficio cuentaDetalleBeneficio : listaCuentaDetalleBeneficioTemp) {
					if(cuentaDetalleBeneficio.getIntItemVinculo().compareTo(beneficiarioPrevision.getIntItemViculo())==0){
						beneficio = new CuentaDetalleBeneficio();
						beneficio = cuentaDetalleBeneficio;
						break;
					}
				}
			}	
					
		} catch (Exception e) {
			log.error("Error en recuperarPorcentajeBeneficio --->"+e);
		}
		
		return beneficio;
	
	}
	
	
	/**
	 * Calcula los montos neto de cada beneficiario, para mostralos en 
	 * la grilla de beneficiarios de la solicitud de retiro.
	 */
	public void calcularMontosPorcentajeBeneficiariosRetiro(){
//		CuentaConcepto cuentaConceptoRetiro = null;
		//List<CuentaConcepto> listaCuentaConcepto = null;
//		List<CuentaDetalleBeneficio> listaCuentaDetalleBeneficioTemp = null;
		CuentaId cuentaId = null;
//		BigDecimal porTotalBeneficiario= BigDecimal.ZERO ;
//		BigDecimal bdTotal = BigDecimal.ZERO;
//		BigDecimal bdGastosAdm = BigDecimal.ZERO;
		BigDecimal bdNeto = BigDecimal.ZERO;
		BigDecimal bdPorcentaje = BigDecimal.ZERO;
		
		try {
			cuentaId = new CuentaId();
			cuentaId.setIntCuenta(beanSocioComp.getCuenta().getId().getIntCuenta());
			cuentaId.setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaId);
//			cuentaConceptoRetiro = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
			
			if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
						for (BeneficiarioPrevision beneficiarioPrevision : beanExpedientePrevision.getListaBeneficiarioPrevision()) {
							bdPorcentaje=beneficiarioPrevision.getBdPorcentajeBeneficio();
							if(bdPorcentaje != null){
								bdNeto = beanExpedientePrevision.getBdMontoNetoBeneficio().multiply(bdPorcentaje).divide(new BigDecimal(100))   ;					
								beneficiarioPrevision.setBdMontoNeto(bdNeto);
							}else{
								bdNeto = BigDecimal.ZERO;
								beneficiarioPrevision.setBdMontoNeto(bdNeto);
							}
						}
						
						porcetanjesSuman100();
			}
		} catch (Exception e) {
			log.error("Error calcularMontosPorcentajeBeneficiariosRetiro --> "+e);
		}
	}

	
	/**
	 * Modificado 22.04.2014
	 * Calcula el interes generado por el fondo de retiro, mas el interes ya generado. Para la solicitud de prevision Retiro.
	 * @return
	 */
	public BigDecimal calcularInteresRetiro(){
		List<Movimiento> listaMovimiento = null; 
		CuentaConcepto cuentaConceptoRetiro = null;
		Movimiento movimientoInteresUltimo = null;
//		BigDecimal bdMontoTotal = BigDecimal.ZERO;
//		Integer intNroDias = 0;
//		BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;	
		BigDecimal bdInteresCalculado = BigDecimal.ZERO;
		BigDecimal bdInteresAcumulado = BigDecimal.ZERO;
		CuentaConceptoDetalle cuentaConceptoDetalleRetiro = new CuentaConceptoDetalle();
		List<CuentaConceptoDetalle> lstCtaCtpoDet = null;
		try {
			
			if(beanCaptacion != null){
				
				// 1. Se recupera el porcentaje del ineters a aplicar.
				//beanCaptacion.getIntTasaInteres();
				
				// 2. Recuperamos las llaves de la cuenta cto retiro, para obetener los movimientos de interes de retiro.
				cuentaConceptoRetiro = recuperarCuentaConceptoBase(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
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
						// Agregado 23.05.2014 jchavez. ademas del utimo interes se debe de recuperar de movimiento la ultima 
						// amortizacion de capital de fdo. retiro.
						CuentaConceptoDetalle ccd = new CuentaConceptoDetalle();
//						Integer intItemCtaCpto = null;
						ccd.setId(new CuentaConceptoDetalleId());
						ccd.getId().setIntPersEmpresaPk(cuentaConceptoRetiro.getId().getIntPersEmpresaPk());
						ccd.getId().setIntCuentaPk(cuentaConceptoRetiro.getId().getIntCuentaPk());
						ccd.setIntParaTipoConceptoCod(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
						lstCtaCtpoDet = conceptoFacadeRemote.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ccd);
						List<Movimiento> lstMovCapital = null;
						if (lstCtaCtpoDet!=null && !lstCtaCtpoDet.isEmpty()) {
							for (CuentaConceptoDetalle listCCD : lstCtaCtpoDet) {
//								intItemCtaCpto = listCCD.getId().getIntItemCuentaConcepto();
								lstMovCapital = conceptoFacadeRemote.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(listCCD.getId().getIntPersEmpresaPk(),
																	listCCD.getId().getIntCuentaPk(),listCCD.getId().getIntItemCuentaConcepto(),
																	Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
								break;
							}
						}
						if (lstMovCapital!=null && !lstMovCapital.isEmpty()) {
							bdUltimoCapRetiro = lstMovCapital.get(0).getBdMontoSaldo();
						}
						
						// b. Recuperamos nro de dias entres fecha de movimeitno y hoy.
						Date dtFechaUltimoInteres = new Date();
						String strFechaUltimoInteres = Constante.sdf.format(movimientoInteresUltimo.getTsFechaMovimiento());
						dtFechaUltimoInteres = Constante.sdf.parse(strFechaUltimoInteres);
						
						Date dtHoy = new Date();

						Integer nroDias =  obtenerDiasEntreFechas(dtFechaUltimoInteres,dtHoy);
						nroDias = Math.abs(nroDias);
						
						
						if(nroDias.compareTo(0)!= 0){
							// c. Se aplica formula simple de interes --> Monto*Porc Interes
							/*
							 * Modificacion 22.04.2014 jchavez
							 * Porc Interes beanCaptacion.getBdTem(). 
							 * Se agrega a la formula el *(nro dias)/30
							 */
							bdInteresCalculado =  (bdTotalBaseCtaMasInt.add(bdUltimoCapRetiro)).multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
							bdInteresAcumulado = bdInteresCalculado.add(bdTotalBaseCtaMasInt);
						}else{
							bdInteresCalculado = BigDecimal.ZERO;
							//Se agrega el interes anterior calculado a la fecha (30.05.2014 - jchavez)
							bdInteresAcumulado= BigDecimal.ZERO.add(bdTotalBaseCtaMasInt);
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
						cuentaConceptoDetalleRetiro = conceptoFacade.getCuentaConceptoDetallePorPkYTipoConcepto(cuentaConceptoDetalleRetiro);
						
						Date dtHoy = new Date();
						Integer nroDias =  obtenerDiasEntreFechas(convertirTimestampToDate(cuentaConceptoDetalleRetiro.getTsInicio()),dtHoy);
						
						bdInteresCalculado =  cuentaConceptoRetiro.getBdSaldo().multiply(new BigDecimal(nroDias).multiply(beanCaptacion.getBdTem())).divide(new BigDecimal(3000),2,RoundingMode.HALF_UP);
						bdInteresAcumulado = bdInteresCalculado.add(BigDecimal.ZERO);
					}
				}
			}
			
		} catch (Exception e) {
			log.error("Error en calcularInteresRetiro ---> "+e);
		}
		
		
		
		return bdInteresAcumulado;
	}
	
    private static Date convertirTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
	
	public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
	}
	
	/**
	 * Calcula los montos de Gastos administratyivos y Monto neto del formulario en base al Monto Total y el % de GA
	 * @param event
	 */
	public void getCalculoMontoGAyNeto(ActionEvent event){
		String strMontoBruto = getRequestParameter("bdMontoBruto");
		BigDecimal bdMontoGA= null;
		BigDecimal bdMontoNeto= null;
		try{
			if(strMontoBruto!=null){
				BigDecimal bdTotalIngreso = new BigDecimal(strMontoBruto.equals("")?"0":strMontoBruto);
				bdMontoGA = bdTotalIngreso.multiply(bdPorcGastosAdministrativo.divide(new BigDecimal(100)));
				bdMontoNeto = bdTotalIngreso.subtract(bdMontoGA);
				beanExpedientePrevision.setBdMontoGastosADM(bdMontoGA);
				beanExpedientePrevision.setBdMontoNetoBeneficio(bdMontoNeto);
			}
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	/**
	 * Metodo que calcula los montos: neto, gastos adm y neto del formulario.
	 */
	public void getCalculoMontoGAyNeto(){
		BigDecimal bdMontoGA= null;
		BigDecimal bdMontoNeto= null;
		try{
			//if(strMontoBruto!=null){
				BigDecimal bdTotalIngreso = beanExpedientePrevision.getBdMontoBrutoBeneficio();
				bdMontoGA = bdTotalIngreso.multiply(bdPorcGastosAdministrativo.divide(new BigDecimal(100)));
				bdMontoNeto = bdTotalIngreso.subtract(bdMontoGA);
				beanExpedientePrevision.setBdMontoGastosADM(bdMontoGA);
				beanExpedientePrevision.setBdMontoNetoBeneficio(bdMontoNeto);
			//}
			/*if(beanExpedientePrevision.getBdTotalIngresos()!=null && beanExpedientePrevision.getBdIndiceDescuento()!=null){
				beanExpedientePrevision.setBdBaseCalculo(beanExpedientePrevision.getBdTotalIngresos().multiply(beanExpedientePrevision.getBdIndiceDescuento()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
			}*/
		}catch(Exception e){
			log.error("Error en getCalculoMontoGAyNeto --> " + e);
		}
	}

	
	/**
	 * Valida la sumatoria de los porcentajes de los beneficiarios
	 * @return
	 */
	public boolean porcetanjesSuman100(){
		BigDecimal bdSumaPorc = null;
		List<BeneficiarioPrevision> listaBeneficiario= null;
		
		try {
			blnSumaPorcentajes = true;
			bdSumaPorc = BigDecimal.ZERO;
			setStrMsgTxtSumaPorcentaje("");
			
			listaBeneficiario=	beanExpedientePrevision.getListaBeneficiarioPrevision();
			if(listaBeneficiario != null && !listaBeneficiario.isEmpty()){
				for (BeneficiarioPrevision beneficiarioPrevision : listaBeneficiario) {
					bdSumaPorc = bdSumaPorc.add(beneficiarioPrevision.getBdPorcentajeBeneficio()) ;
				}
				
				if(bdSumaPorc.compareTo(new BigDecimal(100))!= 0){
					setStrMsgTxtSumaPorcentaje("La suma de los % de Beneficio debe de ser 100%. Modificar porcentajes.");
					blnSumaPorcentajes = false;
				}else{
					setStrMsgTxtSumaPorcentaje("");
					blnSumaPorcentajes = true;
				}
	
			}

		} catch (Exception e) {
			log.error("Error en porcetanjesSuman100 ---> "+e);
		}

		return blnSumaPorcentajes;
	}

	
	
	/**
	 * Carga el flag blnHayExcepciones con TRUE si es AES - subtipo Excepcional. 
	 * @return
	 */
	public void existeExcepcion(){
		//List<ExpedientePrevision> lstExpedientePrevision = null;
		blnHayExcepciones = false;
		
		try {
			if(intTipoSolicitud.compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
				 if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_AES_EXCEPCIONALGERENCIA)==0
					|| intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_AES_EXCEPCIONALJEFE)==0){
					 blnHayExcepciones = true;		 
				 }			
			}
		} catch (Exception e) {
			log.error("Error en existeExcepcion ---> "+e);
		}
		
		//return blnHayExcepciones;
	}
	
	
	
	
	/**
	 * 
	 * @param event
	 */
	public void buscarSolicitudPrevision(ActionEvent event){
		ExpedientePrevisionComp expedienteCompBusq = null;
		try{
			
			listaExpedientePrevisionBusq = new ArrayList<ExpedientePrevisionComp>();
			listaExpedientePrevision = new ArrayList<ExpedientePrevision>();
			
			expedienteCompBusq = new ExpedientePrevisionComp();
			expedienteCompBusq.setIntBusquedaTipo(intBusqTipo);
			expedienteCompBusq.setStrBusqCadena(strBusqCadena.trim());
			expedienteCompBusq.setStrBusqNroSol("");
			expedienteCompBusq.setIntBusqSucursal(intBusqSucursal);
			expedienteCompBusq.setIntBusqEstado(intBusqEstado);
			expedienteCompBusq.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);
			expedienteCompBusq.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);
			expedienteCompBusq.setIntBusqTipoPrevision(intBusqTipoPrevision);
			expedienteCompBusq.setIntBusqSubTipoPrevision(intBusqSubTipoPrevision);
			
			listaExpedientePrevisionBusq = previsionFacade.getListaBusqExpPrevFiltros(expedienteCompBusq);
			
			if(listaExpedientePrevisionBusq != null && !listaExpedientePrevisionBusq.isEmpty()){
				for (ExpedientePrevisionComp expedienteprevisioncomp : listaExpedientePrevisionBusq) {
					listaExpedientePrevision.add(expedienteprevisioncomp.getExpedientePrevision());
					
				}
				
			}
			//listaExpedientePrevision = previsionFacade.buscarExpedientePrevisionFiltro(listaPersonaFiltro, intTipoCreditoFiltro, intSubTipoCreditoFiltro,
			//		estadoPrevisionFiltro, intItemExpedienteFiltro, intTipoBusquedaSucursal, intIdSucursalFiltro, intIdSubsucursalFiltro);

			//Se añade persona administra la cuenta del expediente
			/*for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
				for(CuentaIntegrante cuentaIntegrante : expedientePrevision.getCuenta().getListaIntegrante()){
					if(cuentaIntegrante.getIntParaTipoIntegranteCod().equals(Constante.TIPOINTEGRANTE_ADMINISTRADOR)){
						expedientePrevision.setPersonaAdministra(devolverPersonaCargada(cuentaIntegrante.getId().getIntPersonaIntegrante()));
					}
				}
					expedientePrevision.getEstadoPrevisionUltimo().setPersona(devolverPersonaCargada(expedientePrevision.getEstadoPrevisionUltimo().getIntPersUsuarioEstado()));
					expedientePrevision.getEstadoPrevisionPrimero().setPersona(devolverPersonaCargada(expedientePrevision.getEstadoPrevisionPrimero().getIntPersUsuarioEstado()));
			}*/


		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 
	 */
	public void limpiarFiltrosGrilla(ActionEvent event){
		try {
			intBusqTipo = 0;
			strBusqCadena = "";
			strBusqNroSol = "";
			intBusqSucursal = 0;
			intBusqEstado = 0;
			dtBusqFechaEstadoDesde = null;
			dtBusqFechaEstadoHasta = null;
			intBusqTipoPrevision = 0;
			intBusqSubTipoPrevision = 0;
		} catch (Exception e) {
			log.error("Errro en limpiarFiltrosGrilla --> "+e);
		}
	}
	
	/**
	 * 
	 * @param intIdPersona
	 * @return
	 * @throws Exception
	 */
//	private Persona devolverPersonaCargada(Integer intIdPersona) throws Exception{
//		log.info(intIdPersona);
//		Persona persona = null;
//			
//		persona = personaFacade.getPersonaPorPK(intIdPersona);
//		
//		if(persona != null){
//			if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
//				persona = personaFacade.getPersonaNaturalPorIdPersona(persona.getIntIdPersona());			
//				agregarDocumentoDNI(persona);
//				agregarNombreCompleto(persona);			
//			
//			}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
//				persona.setJuridica(personaFacade.getJuridicaPorPK(persona.getIntIdPersona()));			
//			}
//		}
//
//		return persona;
//	}
	
	/**
	 * 
	 * @param persona
	 */
//	private void agregarDocumentoDNI(Persona persona){
//		for(Documento documento : persona.getListaDocumento()){
//			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
//				persona.setDocumento(documento);
//			}
//		}
//	}
	
	/**
	 * 
	 * @param persona
	 */
	
//	private void agregarNombreCompleto(Persona persona){
//		persona.getNatural().setStrNombreCompleto(
//				persona.getNatural().getStrNombres()+" "+
//				persona.getNatural().getStrApellidoPaterno()+" "+
//				persona.getNatural().getStrApellidoMaterno());
//	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
//	private List<Persona> buscarPersona() throws Exception{
//		List<Persona> listaPersona = new ArrayList<Persona>();
//		
//		//log.info("intTipoBusquedaPersonaFiltro:"+intTipoBusquedaPersonaFiltro);
//		
//		if(strTextoPersonaFiltro==null || strTextoPersonaFiltro.isEmpty()){
//			//Si strTextoPersonaFiltro se deja vacio, entonces buscaremos todos los registros
//			return null;
//		}
//		
//		if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_CODIGOPERSONA)){	
//			Persona persona = personaFacade.getPersonaPorPK(Integer.parseInt(strTextoPersonaFiltro));
//			if(persona!=null)listaPersona.add(persona);
//		
//		}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_APELLIDOSNOMBRES)){
//			Natural natural = new Natural();
//			natural.setStrNombres(strTextoPersonaFiltro);
//			List<Natural> listaNatural = personaFacade.getListaNaturalPorBusqueda(natural);
//			if(listaNatural!=null && !listaNatural.isEmpty()){
//				for(Natural naturalTemp : listaNatural){
//					Persona persona = new Persona();
//					persona.setIntIdPersona(naturalTemp.getIntIdPersona());
//					listaPersona.add(persona);
//				}
//			}
//			
//		}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_BUSQSOLICPTMO_DOCUMENTO)){
//			if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
//				Persona persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
//						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
//						strTextoPersonaFiltro,
//						Constante.PARAM_EMPRESASESION);
//				if(persona!=null) listaPersona.add(persona);
//			}else if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
//				Persona persona = personaFacade.getPersonaPorRuc(strTextoPersonaFiltro);
//				if(persona!=null) listaPersona.add(persona);
//			}			
//		}
//		
//		return listaPersona;
//	}
	
	
	/**
	 * 
	 */
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

	
	/*public void marcarSubtipoSolicitud(){
		//intSubTipoSolicitudExtra = intSubTipoSolicitud;
		//asdasdasd
		
		
	}*/
	
	
	/**
	 * 
	 */
	public void limpiarCampos(ActionEvent event){
		intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
		estadoPrevisionFiltro = new EstadoPrevision();
		estadoPrevisionFiltro.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
		strTextoPersonaFiltro = null;
		intItemExpedienteFiltro = null;
		intTipoCreditoFiltro = 0; 
		intSubTipoCreditoFiltro=0;
		intTipoBusquedaSucursal =0;
		intIdSucursalFiltro = 0;
		intIdSubsucursalFiltro = 0;
		listaExpedientePrevision = new ArrayList<ExpedientePrevision>();

		beanExpedientePrevision.getBdMontoInteresBeneficio();
		beanExpedientePrevision.getIntNumeroCuotaFondo();
		beanExpedientePrevision.getBdMontoCuotasFondo();
		beanExpedientePrevision.getBdMontoBrutoBeneficio();
		beanExpedientePrevision.getBdMontoInteresBeneficio();
		beanExpedientePrevision.getBdMontoNetoBeneficio();
				
		
	}
	
	
	/**
	 * Valida que no exista previamiente una solicitud aes en estado requisito
	 * @return
	 */
	public boolean existeSolicitudAES(){
		log.info("<---------------- existeSolicitudAES ------------------------>");	
		PrevisionFacadeLocal previsionFacade= null;
		List<ExpedientePrevision> lstExpedientesSocio = null;
		EstadoPrevision ultEstadoPrevision = null;
		boolean existe = false;
		try {
			previsionFacade = (PrevisionFacadeLocal) EJBFactory.getLocal(PrevisionFacadeLocal.class);
			lstExpedientesSocio = previsionFacade.getListaExpedientePrevisionPorCuenta(beanSocioComp.getCuenta());
			if(lstExpedientesSocio != null){
				for(int f=0;f<lstExpedientesSocio.size();f++){
					if(lstExpedientesSocio.get(f).getIntParaTipoCaptacion().intValue() == new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
						ultEstadoPrevision = previsionFacade.getUltimoEstadoExpedientePrevision(lstExpedientesSocio.get(f));
						if(ultEstadoPrevision != null){
							if(ultEstadoPrevision.getIntParaEstado().intValue() == Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO){
								existe = Boolean.TRUE;
							}
						}
					}
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
			log.error(e);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
			log.error(e);
		}	

		return existe;
	}
	

	
	/**
	 * 
	 * @param lstFallecido
	 */
	public void cambioEstadoPersonaFallecida(List<FallecidoPrevision> lstFallecido){
		Persona persona = null;
		try {
			if(lstFallecido != null && !lstFallecido.isEmpty()){
				for(int k=0;k<lstFallecido.size();k++){
					persona = new Persona();
					persona = lstFallecido.get(k).getPersona();	
				}
				persona.setIntEstadoCod(Constante.PARAM_PERSONA_ESTADO_FALLECIDO);
				personaFacade.modificarPersona(persona);
			}
			
		} catch (Exception e) {
			System.out.println("Error en cambioEstadoPersonaFallecida --> "+e);
		}

	}
	
	//--------------------------------------------------------------------------------------------------------------------------------------------->
	
	/**
	 * 
	 */
	public boolean isValidCondicionTiempoAportacionConsiderado(){
		CuentaId cuentaId = null;
//		List<CuentaConcepto> listaCuentaConcepto=null;
		List<Movimiento> listaMovientosTotal=null;
		List<Movimiento> listaMovimientoFiltrada=new ArrayList<Movimiento>();
		Movimiento movimiento = null;
//		List<CuentaConceptoDetalle> listaCuentaConceptoDet = null;
		blnCondicionCuotaSepelio = true;
		//blnAESCuotaSepelio = true;
//		BigDecimal bdValorCuota 		  = BigDecimal.ZERO; 	// Valor de cada cuota de sepelio (S/. 12) (A)
		//Integer   
		intNroCuotasDefinidasSepelio = null; 				// Nro de cuotas que debe aportar el socio (B)
//		BigDecimal bdTotalAportesDefinido = BigDecimal.ZERO; 	// producto de (A)*(B)
//		BigDecimal bdTotalAporteSocio     = BigDecimal.ZERO;	//  total de aportes del socio en sepelio 
		List<Tabla> listaCuotasSepelio 	  = null;
		Integer intNroCuotasPagadas 	  = null;						// Nro de cuotas que cancelo el socio.
//		Captacion captacionSepelioTemp = null;
//		List<Captacion> listaCaptacionSepelio = new ArrayList<Captacion>();
//		List<Captacion> listaCaptacionSepelioFinal = new ArrayList<Captacion>();
//		List<BigDecimal> listaMontos = null;
//		List<Integer> listaNroCuotas =  null;
//		List<Integer> listaCodItem =  null;
		//List<String[]> listaMontoCuotaItem = new ArrayList<String[]>();

		//listaTotal.add(0, xxx);
		//////
		
		try {
			cuentaId = new CuentaId();
			cuentaId.setIntCuenta(beanSocioComp.getCuenta().getId().getIntCuenta());
			cuentaId.setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaId);
			listaCuotasSepelio  = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TCUOTASMINFONRET));

			// 1. Validamos el nro de cuotas configuradas VS las pagadas...
			if(listaCuotasSepelio != null){
				
				// Obtenemos el nro de cuotas de sepelio necesarias desde la tabla parametros y el campo cuconumerocuotas...
				if(listaCuotasSepelio != null){
					for(int k=0; k<listaCuotasSepelio.size(); k++){
						if(listaCuotasSepelio.get(k).getIntIdDetalle().intValue() == beanCaptacion.getIntNumeroCuota().intValue()){
							intNroCuotasDefinidasSepelio = new Integer(listaCuotasSepelio.get(k).getStrDescripcion());	
							break;
						}else{
							intNroCuotasDefinidasSepelio = new Integer(0);
						}	
					}
				}

			}
			// revcueperando el nro de cuotas a partir de la fecha de fallecimiento hacia atras...
			listaMovientosTotal = conceptoFacade.getListaMovimientoPorCuentaEmpresaConcepto(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), 
																							beanSocioComp.getCuenta().getId().getIntCuenta(), 
																							Constante.PARAM_T_CUENTACONCEPTO_SEPELIO);
			
			if(listaMovientosTotal!= null){
				
				intNroCuotasPagadasTotal = listaMovientosTotal.size();
				
				for(int m=0; m<listaMovientosTotal.size();m++){
					movimiento = listaMovientosTotal.get(m);
					// MOCC_FECHAMOVIMIENTO_D
					
					if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)){
						if(!movimiento.getTsFechaMovimiento().after(beanExpedientePrevision.getDtFechaFallecimiento())){
							listaMovimientoFiltrada.add(movimiento);
						}
					}else  if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
						Calendar fecHoy = Calendar.getInstance();
								if(!movimiento.getTsFechaMovimiento().after(fecHoy.getTime())){
									listaMovimientoFiltrada.add(movimiento);
								}
					}
					
	
				}
					intNroCuotasPagadas = listaMovimientoFiltrada.size();
			}else{
				intNroCuotasPagadas = 0;	
				intNroCuotasPagadasTotal = 0;
			}
			 
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMM");
			//Calendar calFallecimiento = Calendar.getInstance();
			//calFallecimiento.setTime(beanExpedientePrevision.getDtFechaFallecimiento());
			String strFechaFallec = null;
			if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
				Calendar hoy = Calendar.getInstance();
				 strFechaFallec = sdf3.format(hoy.getTime());
			}else if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)){
				 strFechaFallec = sdf3.format(beanExpedientePrevision.getDtFechaFallecimiento());
			}

			int intMes = Integer.parseInt(strFechaFallec.substring(4));
			int intAnno = Integer.parseInt(strFechaFallec.substring(0,4));
			
			List<String> listaPeriodos = new ArrayList<String>();
			
			for(int c =0; c<intNroCuotasDefinidasSepelio.intValue();c++){
				String strPeriodo = "";
				intMes--;
				if (intMes == 0) {intAnno = intAnno - 1;
					intMes = 12;
				}
				if(intMes <10){
					strPeriodo = intAnno+"0"+intMes;
				}else{
					strPeriodo = ""+intAnno+intMes;
				}
				
				listaPeriodos.add(strPeriodo);
			}
			
			// recorremos los periodos q  deben estar pagado...
			// SI ES AES SE TOMA all... SIE  SSEPELIO SOLO TOAM LAS ULTIMAS DEFINIDAS
			if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO)){
				intNroCuotasPagadas =0;
				for(int a=0;a<listaMovimientoFiltrada.size();a++){
					for(int b=0; b<listaPeriodos.size();b++){
						if(listaMovimientoFiltrada.get(a).getIntPeriodoPlanilla().toString().equals(listaPeriodos.get(b).toString())){
							intNroCuotasPagadas++;	
						}
					}
				}
			}else  if(intTipoSolicitud.equals(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES)){
				intNroCuotasPagadas =0;
				for(int a=0;a<listaMovimientoFiltrada.size();a++){
					for(int b=0; b<listaPeriodos.size();b++){
						//if(listaMovimientoFiltrada.get(a).getIntPeriodoPlanilla().toString().equals(listaPeriodos.get(b).toString())){
							intNroCuotasPagadas++;	
						//}
					}
				}
			}

			if(intNroCuotasDefinidasSepelio.compareTo(intNroCuotasPagadas)>0){
				// strMsgTxtAESCuotasSepelio
				setStrMsgTxtAESCuotasSepelio("Solo tiene "+intNroCuotasPagadas + " cuota(s) cancelada(s) de Sepelio.");
				setChkCondicionCuotaSepelio(false);
			}else{
				setStrMsgTxtAESCuotasSepelio("");
				setChkCondicionCuotaSepelio(true);
			}

		} catch (Exception e) {
			log.error("Error isValidCuotaSepelio12 -->"+e);
		}
		
		
		return blnCondicionCuotaSepelio;
	}
	
	
	/**
	 * Recupera para la Prevision Retiro los valores de:
	 *  Cuota Mensual, Nro de Cuotas , Años de aportacion Calculado, 
	 *  Monto aporte toptal , Interes ganado en base a escala y monto neto
	 */
	public void recuperarMontosRetiro(){
		//List<Movimiento>  listaMovientosRetiro =null;
		//List<Movimiento>  listaMovientosRetiroAscendente =null;
		//List<CuentaConcepto>  listaCuentaConcepto =null;
		//BigDecimal bdValorCuotaMensual = BigDecimal.ZERO;
		//BigDecimal bdValorAporteTotal = BigDecimal.ZERO;
		bdValorNumeroCuotas = BigDecimal.ZERO;
		bdValorAnnosEntero = BigDecimal.ZERO;
		//List<pe.com.tumi.credito.socio.captacion.domain.Vinculo> listaVinculosCaptacion = null;
		//List<Movimiento>  listaMovimientoRetiroFiltrada =null; 
		//Integer intNumeroCuotasRetiroTotal = new Integer(0);
		//pe.com.tumi.credito.socio.captacion.domain.Vinculo  vinculoRetiroCap = null;
		//BigDecimal bdValorNeto = BigDecimal.ZERO;
		//BigDecimal bdValorAportacion = BigDecimal.ZERO;
		//BigDecimal bdValorInteres = BigDecimal.ZERO;
		//BigDecimal bdValorPenalidad = BigDecimal.ZERO;
		//BigDecimal bdMontoInteresesAcumulado = BigDecimal.ZERO;
		
		CuentaConceptoComp cuentaConceptoCompUtil= null;
//		List<CuentaConceptoDetalle> listaCuentaConceptoDet = null;

		List<pe.com.tumi.credito.socio.captacion.domain.Vinculo> listaVinculo = null;

		 try {
			 // 0. recuperarmos la cta cto de retiro y sus detalles.
			 vinculoRetiroSeleccionado = null;
			 
			 if(vinculoRetiroSeleccionado == null){
				// 1. Definimos si existe mas de un tipo de cuenta concepto
				 
					cuentaConceptoCompUtil = recuperarCuentaConceptoPorTipo(Constante.PARAM_T_CUENTACONCEPTO_RETIRO);
					if(cuentaConceptoCompUtil != null){
//							listaCuentaConceptoDet = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaConceptoCompUtil.getCuentaConcepto().getId());
							
							if(cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle() != null
								&& !cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle().isEmpty()){
									
								List<CuentaConceptoDetalle> listCtaCtoDetalle = new ArrayList<CuentaConceptoDetalle>();
								listCtaCtoDetalle = cuentaConceptoCompUtil.getCuentaConcepto().getListaCuentaConceptoDetalle();
								
								if(listCtaCtoDetalle != null && !listCtaCtoDetalle.isEmpty()){
									
									Integer tamanno = new Integer(0);
									tamanno = listCtaCtoDetalle.size();
									
									CuentaConceptoDetalle detalle = null;
									
								// 1. recuperamos el ultimo valor de CUOTA MENSUAL
									if(tamanno.compareTo(1)==0){
										detalle = listCtaCtoDetalle.get(0);
										beanExpedientePrevision.setBdMontoCuotasFondo(detalle.getBdMontoConcepto());
									}else{
										
										//Ordenamos los subtipos por int
										Collections.sort(listCtaCtoDetalle, new Comparator<CuentaConceptoDetalle>(){
											public int compare(CuentaConceptoDetalle uno, CuentaConceptoDetalle otro) {
												return uno.getId().getIntItemCtaCptoDet().compareTo(otro.getId().getIntItemCtaCptoDet());
											}
										});
										
										for(int k=0; k<listCtaCtoDetalle.size();k++){
											System.out.println("LIST xxxxxxxxx ---> "+listCtaCtoDetalle.get(k).getId().getIntItemCtaCptoDet());	
										}
										detalle = listCtaCtoDetalle.get(tamanno-1);
										beanExpedientePrevision.setBdMontoCuotasFondo(detalle.getBdMontoConcepto());
									}
									
									strMsgTxtRetiroValorCuotaMensual =" S/. "+beanExpedientePrevision.getBdMontoCuotasFondo();
								}

							}

							
							//2. validamos la escala
							listaVinculo = vinculoFacade.listarVinculoPorPKCaptacion(beanCaptacion.getId());
							
							// PARAM_T_TIPO_RANGOCUOTAS
							/*if(beanCaptacion.getListaVinculo() != null && !beanCaptacion.getListaVinculo().isEmpty()){
								Boolean blnContinua = Boolean.TRUE;

								for (pe.com.tumi.credito.socio.captacion.domain.Vinculo vinculoCaptacion : beanCaptacion.getListaVinculo()) {*/
							if(listaVinculo != null && !listaVinculo.isEmpty()){
								Boolean blnContinua = Boolean.TRUE;

								for (pe.com.tumi.credito.socio.captacion.domain.Vinculo vinculoCaptacion : listaVinculo) {
									if(blnContinua){

										if(vinculoCaptacion.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
											if(intSubTipoSolicitud.compareTo(vinculoCaptacion.getIntParaMotivo())==0){
												//intNroCuotasPagadasTotal = 1;
												
												switch (vinculoCaptacion.getIntParaTipoCuotaCod()){
												
												case 1:	//Mayor igual
														if(intNroCuotasPagadasTotal.compareTo(vinculoCaptacion.getIntCuotaCancelada())>=0){
															blnContinua = Boolean.FALSE;
															vinculoRetiroSeleccionado = new pe.com.tumi.credito.socio.captacion.domain.Vinculo();
															vinculoRetiroSeleccionado = vinculoCaptacion;
														}
														break;
													
												case 2:	//Menor igual
													if(intNroCuotasPagadasTotal.compareTo(vinculoCaptacion.getIntCuotaCancelada())<=0){
															blnContinua = Boolean.FALSE;
															vinculoRetiroSeleccionado = new pe.com.tumi.credito.socio.captacion.domain.Vinculo();
															vinculoRetiroSeleccionado = vinculoCaptacion;
														}							
														break;
														
												case 3:	// Igual a
													if(intNroCuotasPagadasTotal.compareTo(vinculoCaptacion.getIntCuotaCancelada())==0){
															blnContinua = Boolean.FALSE;
															vinculoRetiroSeleccionado = new pe.com.tumi.credito.socio.captacion.domain.Vinculo();
															vinculoRetiroSeleccionado = vinculoCaptacion;
														}
														break;
														
												case 4:	//  Menor
														if(intNroCuotasPagadasTotal.compareTo(vinculoCaptacion.getIntCuotaCancelada())< 0){
															blnContinua = Boolean.FALSE;
															vinculoRetiroSeleccionado = new pe.com.tumi.credito.socio.captacion.domain.Vinculo();
															vinculoRetiroSeleccionado = vinculoCaptacion;
														}
														break;
														
												case 5:	//	Mayor
														if(intNroCuotasPagadasTotal.compareTo(vinculoCaptacion.getIntCuotaCancelada())>0){
															blnContinua = Boolean.FALSE;
															vinculoRetiroSeleccionado = new pe.com.tumi.credito.socio.captacion.domain.Vinculo();
															vinculoRetiroSeleccionado = vinculoCaptacion;
														}
														break;
														
												}
												
												
				
											}
										}
									}	
								}
								
								
								BigDecimal bdMontoNeto = BigDecimal.ZERO;
								BigDecimal bdMontAporte = BigDecimal.ZERO;
								BigDecimal bdMontoInteres = BigDecimal.ZERO;
								
								
								// valor cuota
								// beanExpedientePrevision.setBdMontoCuotasFondo

								if(vinculoRetiroSeleccionado != null){
									
									if(vinculoRetiroSeleccionado.getIntAportacion().compareTo(1)==0){
										// 2.  monto aporte total
										bdMontAporte = bdMontoNeto.add(cuentaConceptoCompUtil.getCuentaConcepto().getBdSaldo());
										beanExpedientePrevision.setBdMontoBrutoBeneficio(bdMontAporte);
										
									}
									if(vinculoRetiroSeleccionado.getIntInteres().compareTo(1)==0){
										// 3.  monto de ineteres
										//BigDecimal bdInteres = BigDecimal.ZERO;
										
										//bdMontoInteres = new BigDecimal("60");
										bdMontoInteres =calcularInteresRetiro();
										
										bdMontoNeto = bdMontAporte.add(bdMontoInteres);
										beanExpedientePrevision.setBdMontoInteresBeneficio(bdMontoInteres);
										
									}
									
									
									bdValorNumeroCuotas = cuentaConceptoCompUtil.getCuentaConcepto().getBdSaldo().divide(beanExpedientePrevision.getBdMontoCuotasFondo(),RoundingMode.DOWN);
									setStrMsgTxtCuotasRetiro("El socio tiene "+bdValorNumeroCuotas.intValue() +" Cuota(s) cancelada(s) de Retiro.");
									
									//bdAnnosAportacionCalculado = bdValorNumeroCuotas.divide(new BigDecimal(12));
									bdAnnosAportacionCalculado = bdValorNumeroCuotas.divide(new BigDecimal("12"), 2,RoundingMode.HALF_UP);
									// bdValorNumeroCuotas = monto aporte / valor cuota

									//beanExpedientePrevision.setBdMontoNetoBeneficio(bdMontoInteres.add(bdMontoNeto));
									beanExpedientePrevision.setBdMontoNetoBeneficio(bdMontoNeto);
									
								}
		
							}
		
						} 

			 }				

		} catch (BusinessException e) {
			System.out.println("Error en recuperarMontosRetiro --> "+e);
		}
		
	}
	
	
	/**
	 * Convierte los date en strings dd-MM-yyyy y los compara
	 * @param dateUno
	 * @param dateDos
	 * @param intTipoValidacion
	 * @return
	 */
    public  Boolean comparaDatesDDMMYYYY(Date dateUno, Date dateDos, Integer intTipoValidacion) {
	   	 DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
	   	 Boolean blnValido = Boolean.FALSE;
	   	 try {
	   		intTipoValidacion = 1;
	   		
	   		 switch (intTipoValidacion) {
			// valida que el date 2 puede ser solo mayor o igual al date 1
	   		 case 1:	
		   			 if (df.format(dateUno).equals(df.format(dateDos))){
		   				blnValido = Boolean.TRUE;
		   			 }
		   			 break;
				
	   		 }
	   		 
		} catch (Exception e) {
			log.error("Error en comparaDatesDDMMYYYY ---> "+e);
		}
		return blnValido; 
   }
    
    
	/**
	 * Valida la fecha de sustento medico vs la fecha de registro de solicitud, segun configuracion de captacion.
	 * @param event
	 */
	public void validarFechaSustentoMedico(){
		
		List<Tabla> listaTiempoSustento = null;
		Integer intTiempoPresentacionSustento = 0;
		Integer nroDias = 0;
		try {
			
			setStrMsgTxtAESFechaSustentacion("");
			// Recuperamos el nro de dias y la periocidad segun beanCaptacion 
			if(beanExpedientePrevision.getDtFechaSustentoMedico() != null){
				
				if(beanExpedientePrevision.getDtFechaSustentoMedico().after(dtFechaRegistro)
					&& (!comparaDatesDDMMYYYY(beanExpedientePrevision.getDtFechaSustentoMedico(), dtFechaRegistro, null))){
					setStrMsgTxtAESFechaSustentacion("La Fecha de Sustento Médico no puede ser posterior a la Fecha de Solicitud");
					
				}else{
					setStrMsgTxtAESFechaSustentacion("");
					
					listaTiempoSustento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TCUOTASMINFONRET));
					// obtenemos el tiempod me sustento
					if(listaTiempoSustento != null){
						for(int k=0;k<listaTiempoSustento.size();k++){
							if(listaTiempoSustento.get(k).getIntIdDetalle().intValue() == beanCaptacion.getIntTiempoSustento().intValue()){
								intTiempoPresentacionSustento = new Integer(listaTiempoSustento.get(k).getStrDescripcion());
								break;
							}
						}	
					}

					nroDias = fechasDiferenciaEnDias(beanExpedientePrevision.getDtFechaSustentoMedico() ,dtFechaRegistro);
					nroDias = Math.abs(nroDias);
					
					if(nroDias > -1){
						// x dias minimo?
						if(beanCaptacion.getIntParaTipoMaxMinSustCod().compareTo(Constante.PARAM_T_MINIMO)==0){
							if(intTiempoPresentacionSustento > nroDias.intValue()){
								//blnFechafallecimiento = false;
								setStrMsgTxtAESFechaSustentacion("La Fecha de Sustento Médico se encuentra fuera del rango configurado ( "+
																intTiempoPresentacionSustento+" dias Mínimo).");
							}else{	//blnFechafallecimiento = true;
								setStrMsgTxtAESFechaSustentacion("");
							}
							
						}else if(beanCaptacion.getIntParaTipoMaxMinSustCod().compareTo(Constante.PARAM_T_MAXIMO)==0){
							// x dias maximo?
							if(intTiempoPresentacionSustento < nroDias.intValue()){
								//blnFechafallecimiento = false;
								setStrMsgTxtAESFechaSustentacion("La Fecha de Sustento Médico se encuentra fuera del rango configurado ( "+
																intTiempoPresentacionSustento+ " dias Máximo).");
							}else{	//blnFechafallecimiento = true;
								setStrMsgTxtAESFechaSustentacion("");
							}
							
						}
					}
				}
				
			}
			
			
		} catch (Exception e) {
			log.error("Error en validarFechaSustentoMedico ---> "+e);
		}
	}
	
	
	/**
	 * Valida la fecha de fallecimiento. Que no sea mayor a la fecha de hoy.
	 * @param event
	 */
	public Boolean validarFechaFallecimiento(){
		Boolean blnOk = Boolean.TRUE;
		
		try {
			
			setStrMsgTxtFechaFallecimiento("");
			// Recuperamos el nro de dias y la periocidad segun beanCaptacion 
			if(beanExpedientePrevision.getDtFechaFallecimiento() != null){
				
				if(beanExpedientePrevision.getDtFechaFallecimiento().after(dtFechaRegistro)){

					setStrMsgTxtFechaFallecimiento("La Fecha de Fallecimiento no puede ser mayor que la Fecha de Solicitud");	
					blnOk = Boolean.FALSE;
				}else{
					setStrMsgTxtFechaFallecimiento("");

				}
				
			}
			
			
		} catch (Exception e) {
			log.error("Error en validarFechaSustentoMedico ---> "+e);
		}
		return blnOk;
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
	 * 
	 * @param event
	 */
	public void onclickCheckSinRelacion(ActionEvent  event) {
		
		try {
			limpiarBeneficiarios(event);
			//limpiarFallecidos(event);
			
			if(blnBenefTieneRelFam == true ){
				aplicarDescuento50Porciento();

			}else{
				restaurarDescuento50Porciento();
			}
			
			System.out.println("blnBenefTieneRelFam ---> "+ blnBenefTieneRelFam);
			
		} catch (Exception e) {
			log.error("Error en onclickCheckSinRelacion ---> "+e);
		}
	}
	
	/**
	 * Aplica un desceunto del 50% sobre el monto calculado del beneficio.
	 * Se aplica cuendo se marca el check de Beneficiario NO tiene relacion.
	 */
	private void aplicarDescuento50Porciento() {
		try {
			beanExpedientePrevision.setBdMontoBrutoBeneficio(bdMontoCalculadoBeneficio.multiply(new BigDecimal(Constante.PARAM_T_DESCUENTO_PREVISION_OTROS)));
		} catch (Exception e) {
			log.error("Error en aplicarDescuento50Porciento ---> "+e);
		}
	}
	
	/**
	 * Aplica un desceunto del 50% sobre el monto calculado del beneficio.
	 * Se aplica cuendo se DESmarca el check de Beneficiario NO tiene relacion.
	 */
	private void restaurarDescuento50Porciento() {
		try {
			beanExpedientePrevision.setBdMontoBrutoBeneficio(bdMontoCalculadoBeneficio);
		} catch (Exception e) {
			log.error("Error en aplicarDescuento50Porciento ---> "+e);
		}
	}

	
	/**
	 * Valida que en el caso se sepelio - titular, no exista deuda (creditos, actividades, indebidos)
	 * @return
	 */
	public boolean isValidCondicionNoDebeTenerDeudaSepelioTitular(){
		Boolean blnNoTieneDeuda = Boolean.TRUE;
		List<Expediente> listaExpedientesMovimiento = null;
		blnCondicionNODebeTenerDeuda = true;
		strMsgTxtSepelioTitularNoDebeDeudaExistente = "";
		try {
			
			// 1. recuperamos los creditos y las actividades de movimeinto.
			listaExpedientesMovimiento = conceptoFacade.getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), 
					beanSocioComp.getCuenta().getId().getIntCuenta());

			if(listaExpedientesMovimiento != null && !listaExpedientesMovimiento.isEmpty()){
				//blnAESDebeTenerDeuda=false;
				String strListadoDeudas = "";
				blnCondicionNODebeTenerDeuda = false;
				blnNoTieneDeuda = false;
				
				for (Expediente expediente : listaExpedientesMovimiento) {
					CreditoId creditoId = new CreditoId();
					Credito creditoRec = null;
					
					creditoId.setIntItemCredito(expediente.getIntItemCredito());
					creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					creditoId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
					
					creditoRec = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
					if(creditoRec != null){
						strListadoDeudas = creditoRec.getStrDescripcion()+ " "+strListadoDeudas + ". ";	
					}

				}
				
				
				// 2. recuperamos los cobros indebidos:
				List<DescuentoIndebido> listaDescuentosIndebidos = null;
				listaDescuentosIndebidos = cuentaCteFacade.getListaDesceuntoIndebidoXEmpYCta(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), 
																beanSocioComp.getCuenta().getId().getIntCuenta());
				
				if(listaDescuentosIndebidos != null && !listaDescuentosIndebidos.isEmpty()){
					
					for (DescuentoIndebido descuentoIndebido : listaDescuentosIndebidos) {
						
						if(descuentoIndebido.getIntParaEstadocod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0
							&& descuentoIndebido.getIntParaEstadoPagadocod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0
							&& descuentoIndebido.getBdDeinMonto().compareTo(BigDecimal.ZERO)>0){
							
							strListadoDeudas = strListadoDeudas + " Descuentos Indebidos.";
							blnCondicionNODebeTenerDeuda = false;
							blnNoTieneDeuda = false;
							break;
						}
						
					}
					
				}

				strMsgTxtSepelioTitularNoDebeDeudaExistente = "El Socio presenta deuda: "+ strListadoDeudas;
				//setChkCondicionDebeTenerDeuda(false);
			}else{
				//setChkCondicionDebeTenerDeuda(true);
				strMsgTxtAESDeudaExistente = "";
			}

		} catch (Exception e) {
			log.error(""+e);
		}
		
		return blnNoTieneDeuda;
	}
	
	
	
	
	
	
	/**
	 * Muestra las personas que observaron la solicitud de credito previamente.
	 * @return
	 */
	public boolean mostrarlistaAutorizacionesPrevias(List<EstadoPrevision> listaEstados) {
		boolean isValidEncaragadoAutorizar = true;
		List<AutorizaPrevision> listaAutorizaPrevision = new ArrayList<AutorizaPrevision>();
		listaAutorizaPrevisionComp = new ArrayList<AutorizaPrevisionComp>();
		AutorizaPrevisionComp autorizaPrevisionComp = null;
		Persona persona = null;
		try {
			// buscar todos sus estados y ver si aen alguno existe un observado
			
			if(listaEstados!= null && !listaEstados.isEmpty()){
				Boolean blnContinua = Boolean.FALSE;
				
				for (EstadoPrevision estados : listaEstados) {
					if(estados.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
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
					listaAutorizaPrevision = previsionFacade.getListaVerificaPrevisionPorPkExpediente(registroSeleccionado.getExpedientePrevision().getId());
					if (listaAutorizaPrevision != null && listaAutorizaPrevision.size() > 0) {
						for (AutorizaPrevision autorizaPrevision : listaAutorizaPrevision) {
							if(autorizaPrevision.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_PRESTAMO)==0
							   || autorizaPrevision.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO)==0 ){
								autorizaPrevisionComp = new AutorizaPrevisionComp();
								autorizaPrevisionComp.setAutorizaPrevision(autorizaPrevision);
								persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaPrevision.getIntPersUsuarioAutoriza());
								for (int k = 0; k < persona.getListaDocumento().size(); k++) {
									if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
										persona.setDocumento(persona.getListaDocumento().get(k));
										break;
									}

								}

								// recuperando el perfil del usuario
								Perfil perfil = null;
								PerfilId perfilId = null;
								autorizaPrevisionComp.setPersona(persona);
								
								perfilId = new PerfilId();
								perfilId.setIntPersEmpresaPk(autorizaPrevisionComp.getAutorizaPrevision().getIntPersEmpresaAutoriza());
								perfilId.setIntIdPerfil(autorizaPrevisionComp.getAutorizaPrevision().getIntIdPerfilAutoriza());
								// recuperando el perfil del usuario
								perfil = permisoFacade.getPerfilYListaPermisoPerfilPorPkPerfil(perfilId);
								
								if(perfil != null){
									autorizaPrevisionComp.setStrPerfil(perfil.getStrDescripcion());
								}
								listaAutorizaPrevisionComp.add(autorizaPrevisionComp);
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
	 * Validacion Final para el otorgamiento de prevision vs. existencia de previsiones anteriores y/o liquidaciones
	 * @return
	 */
	public Boolean validacionFinalOtorgamientoPrevision(){
		Boolean blnPasaPrevision = Boolean.TRUE;
		Boolean blnPasaLiquidacion = Boolean.TRUE;
		Boolean blnPasa = null;
		//String strValidacionFinal = "";
		List<ExpedientePrevision> listaExpPrevision= null;
		List<ExpedienteLiquidacion> listaExpLiquidacion= null;

		try {
			
			strMsgTxtEvaluacion = "";
			
			// 1. Recuperamos las previones
			listaExpPrevision = previsionFacade.getListaExpedientePrevisionPorCuenta(beanSocioComp.getCuenta());
			if(listaExpPrevision != null && !listaExpPrevision.isEmpty()){
				for (ExpedientePrevision expedientePrevision : listaExpPrevision) {
					//if(!(beanExpedientePrevision.getId().getIntCuentaPk().compareTo(expedientePrevision.getId().getIntCuentaPk())== 0
						//&& beanExpedientePrevision.getId().getIntItemExpediente().compareTo(expedientePrevision.getId().getIntItemExpediente())== 0
						//&& beanExpedientePrevision.getId().getIntPersEmpresaPk().compareTo(expedientePrevision.getId().getIntPersEmpresaPk())==0)){
						// AES
						if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
							if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
							&& expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0
							&& expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){

							blnPasaPrevision = Boolean.FALSE;
							strMsgTxtEvaluacion = strMsgTxtEvaluacion +" El socio presenta una Solicitud de Previsión Sepelio Titular en estado APROBADO.";
							break; 
							}
						// SEPELIO		
						} else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){	

									if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
										if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
										&& expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){

											if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0
											|| expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
											|| expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0
											|| expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
												blnPasaPrevision = Boolean.FALSE;
												strMsgTxtEvaluacion = strMsgTxtEvaluacion +" El socio presenta una Solicitud de Previsión Sepelio Titular.";
												break; 
											}
										}		
									}else{
										if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0
										&& expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_RETIRO_FALLECIMIENTO_TITULAR)==0){
											if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
												blnPasaPrevision = Boolean.FALSE;
												strMsgTxtEvaluacion = strMsgTxtEvaluacion + " El socio presenta una Solicitud de Previsión Retiro Fallecimiento Titular en estado Aporbado.";
												break; 
											}
										}	
									}

						// RETIRO	
						}else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
								if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)!=0){
									if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0
										&& expedientePrevision.getIntParaSubTipoOperacion().compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)==0){
										if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
											blnPasaPrevision = Boolean.FALSE;
											strMsgTxtEvaluacion = strMsgTxtEvaluacion + " El socio presenta una Solicitud de Previsión Sepelio Titular en estado Aporbado.";
											break; 
										}
									}	
								}

								if(expedientePrevision.getIntParaTipoCaptacion().compareTo(Integer.parseInt(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
									if(expedientePrevision.getEstadoPrevisionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){
										blnPasaPrevision = Boolean.FALSE;
										strMsgTxtEvaluacion = strMsgTxtEvaluacion + " El socio presenta una Solicitud de Previsión Retiro en estado Aporbado.";
										break; 
									}
								}
							}
						//}
					}
			}

			// 2.  Recuperamos las liquidaciones
			// Solo puede tener una liquidacion
			// recuperar liquidaciones x cuenta  personay empresa...
			// liquidacionFacade.getlistaex

			listaExpLiquidacion =  liquidacionFacade.getListaExpedienteLiquidacionYEstados(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), beanSocioComp.getCuenta().getId().getIntCuenta());

			if(listaExpLiquidacion != null && !listaExpLiquidacion.isEmpty()){
				for (ExpedienteLiquidacion expedienteLiquidacion : listaExpLiquidacion) {					
				
					if(expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
					|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0
					|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0
					|| expedienteLiquidacion.getEstadoLiquidacionUltimo().getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0){

						if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0){
							blnPasaLiquidacion = Boolean.FALSE;
							strMsgTxtEvaluacion = strMsgTxtEvaluacion +" El socio ya posee una Solicitud de Liquidación.";
							break; 

						}else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_RETIRO))==0){
									blnPasaLiquidacion = Boolean.FALSE;
									strMsgTxtEvaluacion = strMsgTxtEvaluacion +" El socio ya posee una Solicitud de Liquidación.";
									break; 

						}else if(intTipoSolicitud.compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_SEPELIO))==0){
								if(intSubTipoSolicitud.compareTo(Constante.PARAM_SUBTIPO_OPERACION_SEPELIO_TITULAR)!=0){
									blnPasaLiquidacion = Boolean.FALSE;
									strMsgTxtEvaluacion = strMsgTxtEvaluacion +" El socio ya posee una Solicitud de Liquidación.";
									break; 
								}

						}
					}
				
				}

			}

		} catch (Exception e) {
			log.error("Error en validacionFinalOtorgamientoPrevision ---> "+e);
		}
		
		if(blnPasaPrevision && blnPasaLiquidacion){
			blnPasa = Boolean.TRUE;
		}else{
			blnPasa = Boolean.FALSE;
		}
		return blnPasa;
		
	}
	/**
	 * 
	 * @return
	 */
	
	/**
	 * Se Imprime los Formularios de los sepelios
	 * Autor: rVillarreal
	 */
	public void imprimirSepelio(){
		 String strNombreReporte = " ";
		 HashMap<String,Object> parametro = new HashMap<String,Object>();
		 Tabla autorizacion = new Tabla();
		 DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(',');
			NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);
	    	CuentaConceptoDetalle ctaCptoDet = new CuentaConceptoDetalle();
	    	ctaCptoDet.setId(new CuentaConceptoDetalleId());
	    	List<BloqueoCuenta> lstBloqueoCuenta = new ArrayList<BloqueoCuenta>();
	    	ConfServDetalle detalle = null;
	    	Movimiento movi =  new Movimiento();
	    	List<Movimiento> lstmovi = new ArrayList<Movimiento>();
	    	List<CuentaConceptoDetalle> lstCuentaConceptoDetalle = null;
	    	List<ConceptoPago> lstConceptoPago = new ArrayList<ConceptoPago>();
	    	CuentaConceptoDetalleId ccd = new CuentaConceptoDetalleId();
			lstParaQuePinteSepelio.clear();
		 try {
			 EstructuraFacadeRemote facade1 = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			 ConceptoFacadeRemote conceptoFacade =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			 ConfSolicitudFacadeLocal solicitudFacade =(ConfSolicitudFacadeLocal)EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			 PrevisionFacadeLocal beneficiario =(PrevisionFacadeLocal)EJBFactory.getLocal(PrevisionFacadeLocal.class);
			 GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			 verSolicitudPrevision(null);
			 movi = new Movimiento();
			 movi.setIntPersPersonaIntegrante(beanSocioComp.getPersona().getIntIdPersona());

			 lstmovi = conceptoFacade.getMaxMovCtaCteXFechaMaxima(movi.getIntPersPersonaIntegrante());		 
			 if(lstmovi.get(0).getTsFechaMovimientoMax()!=null){
				 String fechaMaxima = Constante.sdf.format(lstmovi.get(0).getTsFechaMovimientoMax());
				 	parametro.put("R1", fechaMaxima.split("/")[0]);
			        parametro.put("R2", fechaMaxima.split("/")[1]);
			        parametro.put("R3", fechaMaxima.split("/")[2]);
			 }else{
				 parametro.put("R1", "");
				 parametro.put("R2", "");
				 parametro.put("R3", "");
			 }

			 List<RequisitoPrevisionComp> lstListaRequisitoPrevisionComp = beanExpedientePrevision.getListaRequisitoPrevisionComp();
			 if(lstListaRequisitoPrevisionComp!=null && !lstListaRequisitoPrevisionComp.isEmpty()){
			 for (RequisitoPrevisionComp lstListaRequisitoPrevisionComp1 : lstListaRequisitoPrevisionComp){
				 	if(lstListaRequisitoPrevisionComp1!=null){
				 		detalle = new ConfServDetalle();
				 		detalle.setId(new ConfServDetalleId());
				 		detalle.getId().setIntPersEmpresaPk(lstListaRequisitoPrevisionComp1.getRequisitoPrevision().getIntPersEmpresaPk());
				 		detalle.getId().setIntItemSolicitud(lstListaRequisitoPrevisionComp1.getRequisitoPrevision().getIntItemReqAut());
				 		detalle.getId().setIntItemDetalle(lstListaRequisitoPrevisionComp1.getRequisitoPrevision().getIntItemReqAutEstDetalle());
						detalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());
			 
						ConfServDetalle lstConfServDetalle = solicitudFacade.getConfServDetallePorPk(detalle.getId());

						Tabla tipoDocumento = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO),
								 lstConfServDetalle.getIntParaTipoDescripcion());
						if(tipoDocumento!=null){
									tipoDocumento.setStrAbreviatura(tipoDocumento.getStrDescripcion());
									lstParaQuePinteSepelio.add(tipoDocumento);
						}else{
							 autorizacion= new Tabla();
							 autorizacion.setStrAbreviatura("");
							 lstParaQuePinteSepelio.add(autorizacion);
								}
							}
						}
						 autorizacion= new Tabla();
						 autorizacion.setStrAbreviatura("");
						 lstParaQuePinteSepelio.add(autorizacion);
					}else{
						 autorizacion= new Tabla();
						 autorizacion.setStrAbreviatura("");
						 lstParaQuePinteSepelio.add(autorizacion);
					}

			 String codigo = Integer.toString(beanSocioComp.getPersona().getIntIdPersona());
			  String resultado = String.format("%09d", Integer.parseInt(codigo));
			 for(int i = 0; i < 9; i++){
	            	parametro.put("C"+i, "");
	            }
			 
			 for(int i = 0; i < resultado.length(); i++){
	            	parametro.put("C"+i, resultado.substring(i, i+1));
	            }
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
	            //
	            List<Comunicacion> lstComunicacion = beanSocioComp.getPersona().getListaComunicacion();
	            
	            String numero="";
	            if(lstComunicacion!=null && !lstComunicacion.isEmpty()){
	         	   int prioridad = 99;
	         	   for(Comunicacion com : lstComunicacion){
	            		   if(com.getIntSubTipoComunicacionCod()==4 && com.getIntTipoComunicacionCod()==1 && prioridad>5){
	         			   prioridad = 5;
	         			   numero = Integer.toString(com.getIntNumero());
	         	       }else if(com.getIntSubTipoComunicacionCod()==3 && com.getIntTipoComunicacionCod()==1 && prioridad>4){
	         	    	   prioridad = 4;
	         			   numero = Integer.toString(com.getIntNumero());
	         		   }else if(com.getIntSubTipoComunicacionCod()==2 && com.getIntTipoComunicacionCod()==1 && prioridad>3){
	         	    	   prioridad = 3;
	         			   numero = Integer.toString(com.getIntNumero());
	         		   }else if(com.getIntSubTipoComunicacionCod()==1 && com.getIntTipoComunicacionCod()==1 && prioridad>2){
	         	    	   prioridad = 2;
	         			   numero = Integer.toString(com.getIntNumero());
	         		   }else if(com.getIntSubTipoComunicacionCod()==5 && com.getIntTipoComunicacionCod()==1 && prioridad>1){
	         	    	   prioridad = 1;
	         			   numero = Integer.toString(com.getIntNumero());
	         		   }
	         	   	}
	            }
	            for(int i = 0; i < 9; i++){
	            	parametro.put("T"+i, "");
	            }
	            for(int i = 0; i < numero.length(); i++){
	            	parametro.put("T"+i, numero.substring(i, i+1));
	            }
	            
	            String numDni = "";
	            if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && 
	            		beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
	             numDni = beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad();
	            	String d0 = numDni.substring(0, 1);
	            	String d1 = numDni.substring(1, 2);
	            	String d2 = numDni.substring(2, 3);
	            	String d3 = numDni.substring(3, 4);
	            	String d4 = numDni.substring(4, 5);
	            	String d5 = numDni.substring(5, 6);
	            	String d6 = numDni.substring(6, 7);
	            	String d7 = numDni.substring(7, 8);
	            	parametro.put("D0", d0);
	            	parametro.put("D1", d1);
	            	parametro.put("D2", d2);
	            	parametro.put("D3", d3);
	            	parametro.put("D4", d4);
	            	parametro.put("D5", d5);
	            	parametro.put("D6", d6);
	            	parametro.put("D7", d7);
	            }
	            if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && 
	            		beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
	             parametro.put("P_DNIFIRMA", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad());
	            }
	            for(EstadoPrevision lstEstadoPrevision : beanExpedientePrevision.getListaEstadoPrevision()){
	              if(lstEstadoPrevision.getIntParaEstado()==2){
	            	  String fechaEstado = Constante.sdf.format(lstEstadoPrevision.getTsFechaEstado());
	            	parametro.put("P_FECHA", fechaEstado);
	              	break;
	              }else{
	            	  parametro.put("P_FECHA", "");
	              }
	            }
	            System.out.println("Nivel " +beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntNivel());
	            System.out.println("Codigo " +beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntCodigo());
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
	            listaSucursalBusqueda = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
		        parametro.put("P_AGENCIA", listaSucursalBusqueda.get(0).getJuridica().getStrSiglas());
		        String fechaIngreso = Constante.sdf.format(beanSocioComp.getCuenta().getListaIntegrante().get(0).getTsFechaIngreso());

		        parametro.put("P1", fechaIngreso.split("/")[0]);
		        parametro.put("P2", fechaIngreso.split("/")[1]);
		        parametro.put("P3", fechaIngreso.split("/")[2]);
		        
		        ctaCptoDet.getId().setIntPersEmpresaPk(SESION_IDEMPRESA);
	            ctaCptoDet.getId().setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());
	            ctaCptoDet.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTO);
	            lstCuentaConceptoDetalle = conceptoFacade.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(ctaCptoDet);
	            
	            lstBloqueoCuenta = conceptoFacade.getListaFondoSepelioMonto(beanExpedientePrevision.getId().getIntPersEmpresaPk(), beanExpedientePrevision.getId().getIntCuentaPk());
	            //
	            if(lstBloqueoCuenta !=null && !lstBloqueoCuenta.isEmpty()){
	            	int suma=0;
		            for(int k=0; k<lstBloqueoCuenta.size();k++){
		            	suma=suma+lstBloqueoCuenta.get(k).getMontoFdoSepelio();
		            }
		            parametro.put("P_APORTE", formato.format(suma));
	            }else{
	            	parametro.put("P_APORTE", "");
	            }
	            for(CuentaConceptoDetalle lstCuenta : lstCuentaConceptoDetalle){
	            	ccd = new CuentaConceptoDetalleId();
					ccd.setIntPersEmpresaPk(lstCuenta.getId().getIntPersEmpresaPk());
					ccd.setIntCuentaPk(lstCuenta.getId().getIntCuentaPk());
					ccd.setIntItemCuentaConcepto(lstCuenta.getId().getIntItemCuentaConcepto());
					ccd.setIntItemCtaCptoDet(lstCuenta.getId().getIntItemCtaCptoDet());
					
					lstConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ccd);
					Integer menor=999999;
					for(ConceptoPago lstCocnepto : lstConceptoPago){
						 if(lstCocnepto.getIntPeriodo()<menor){
							 menor=lstCocnepto.getIntPeriodo();
						 }
					int añomenor=Integer.parseInt(menor.toString().substring(0,4));
					Date d = new Date();
					String añoActual = Constante.sdf.format(d);
					String añoActua = añoActual.split("/")[2];
					parametro.put("P_APO", Integer.parseInt(añoActua)-añomenor);
					}
	            }

              Tabla tablaCondicion = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_CONDICIONSOCIO), 
						 							beanSocioComp.getCuenta().getIntParaCondicionCuentaCod());
              if(beanExpedientePrevision.getIntParaTipoCaptacion()==2)
              	parametro.put("P_CONDICION", tablaCondicion.getStrDescripcion());
              else
              	 parametro.put("P_CONDICION", "");
				 
				 if(beanExpedientePrevision.getIntParaSubTipoOperacion()!=null && beanExpedientePrevision.getIntParaTipoCaptacion()==2){
					 	 Tabla tablaTipo = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO),
					 	  				 beanExpedientePrevision.getIntParaSubTipoOperacion());
					 	 if(tablaTipo!=null)
					 		 parametro.put("P_TIPO", tablaTipo.getStrDescripcion());
					 	 else
					 		parametro.put("P_TIPO", "");
				 }else{
					 parametro.put("P_TIPO", "");
				 }
				 if(beanExpedientePrevision.getDtFechaFallecimiento()!=null){
					String fechaFallecimiento = Constante.sdf.format(beanExpedientePrevision.getDtFechaFallecimiento());
			        parametro.put("N1", fechaFallecimiento.split("/")[0]);
			        parametro.put("N2", fechaFallecimiento.split("/")[1]);
			        parametro.put("N3", fechaFallecimiento.split("/")[2]);
				 }else{
					 	parametro.put("N1", "");
				        parametro.put("N2", "");
				        parametro.put("N3", "");
				 }
				 if(beanExpedientePrevision.getListaFallecidoPrevision()!=null){
					 for(int k=0; k<beanExpedientePrevision.getListaFallecidoPrevision().size();k++){
						 String apellidoPaterno = beanExpedientePrevision.getListaFallecidoPrevision().get(k).getPersona().getNatural().getStrApellidoPaterno();
						 String apellidoMaterno = beanExpedientePrevision.getListaFallecidoPrevision().get(k).getPersona().getNatural().getStrApellidoMaterno();
						 String nombre = beanExpedientePrevision.getListaFallecidoPrevision().get(k).getPersona().getNatural().getStrNombres();
						 parametro.put("P_NOMBREFALLECIDO", (apellidoPaterno.toUpperCase()!=null?apellidoPaterno.toUpperCase():"") +" "+ (apellidoMaterno.toUpperCase()!=null?apellidoMaterno.toUpperCase():"") + "," + (nombre.toUpperCase()!=null?nombre.toUpperCase():""));
					 }
				 }else{
					 parametro.put("P_NOMBREFALLECIDO", "");
				 }
	 
				 if(beanExpedientePrevision.getIntParaTipoCaptacion()==2){
					 	List<Tabla> lstDescVinculo = new ArrayList<Tabla>();
					 	TablaFacadeRemote decripcionVinculo = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
						List<BeneficiarioPrevision> lstBeneficiarioDescripcion = beneficiario.getListaVinculo(beanExpedientePrevision.getId().getIntPersEmpresaPk(), 
																		beanExpedientePrevision.getId().getIntCuentaPk(),
																		beanExpedientePrevision.getId().getIntItemExpediente());
						if(lstBeneficiarioDescripcion==null || lstBeneficiarioDescripcion.isEmpty() ){
							Tabla decripcionVinculo1 = decripcionVinculo.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVINCULO), 8);
							lstDescVinculo.add(decripcionVinculo1);
							parametro.put("P_LIST_TIPODESC", lstDescVinculo);
						}else{
						for(BeneficiarioPrevision listDescrip : lstBeneficiarioDescripcion){
							Tabla decripcionVinculo1 = decripcionVinculo.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVINCULO), listDescrip.getIntTipoVinculoCod());
							lstDescVinculo.add(decripcionVinculo1);
							parametro.put("P_LIST_TIPODESC", lstDescVinculo);
						}
					}
				 }		
				List<BeneficiarioPrevision> listBeni = new ArrayList<BeneficiarioPrevision>();
				if(beanExpedientePrevision.getIntParaTipoCaptacion()==2){
					
					List<BeneficiarioPrevision> lstBeneficiario = beneficiario.getListaNombreCompletoBeneficiario(beanExpedientePrevision.getId().getIntPersEmpresaPk(), 
																	beanExpedientePrevision.getId().getIntCuentaPk(),
																	beanExpedientePrevision.getId().getIntItemExpediente());
					
					if (lstBeneficiario!=null && !lstBeneficiario.isEmpty()) {
						for(BeneficiarioPrevision beneficiarioPrevision : lstBeneficiario){
							beneficiarioPrevision.setStrNomApeBeneficiario(beneficiarioPrevision.getStrApePaterno().toUpperCase()+" "+beneficiarioPrevision.getStrApeMaterno().toUpperCase()+", "+beneficiarioPrevision.getStrNombre().toUpperCase());
							listBeni.add(beneficiarioPrevision);
							parametro.put("P_LIST_TIPOBENI", listBeni);
						}
					}else{
						if (lstBeneficiario==null || lstBeneficiario.isEmpty()) {
							lstBeneficiario = new ArrayList<BeneficiarioPrevision>();
							BeneficiarioPrevision x = new BeneficiarioPrevision();
							x.setStrNomApeBeneficiario("");
							lstBeneficiario.add(x);
							parametro.put("P_LIST_TIPOBENI", listBeni);
						}
					}
				}else{
					List<BeneficiarioPrevision> lstBeneficiario= null;
					if (lstBeneficiario==null || lstBeneficiario.isEmpty()) {
						lstBeneficiario = new ArrayList<BeneficiarioPrevision>();
						BeneficiarioPrevision x = new BeneficiarioPrevision();
						x.setStrNomApeBeneficiario("");
						lstBeneficiario.add(x);
						parametro.put("P_LIST_TIPOBENI", listBeni);
					}
					
				}

					BigDecimal money = BigDecimal.ZERO;
					parametro.put("P_SEPELIO", formato.format(beanExpedientePrevision.getBdMontoBrutoBeneficio()));
					if(beanExpedientePrevision.getBdMontoGastosADM()!=null){
						parametro.put("P_GASTOADM", formato.format(beanExpedientePrevision.getBdMontoGastosADM()));
					parametro.put("P_NETOPAGAR", formato.format(beanExpedientePrevision.getBdMontoBrutoBeneficio().subtract(beanExpedientePrevision.getBdMontoGastosADM())));
					}else{
						parametro.put("P_GASTOADM", formato.format(money));
						parametro.put("P_NETOPAGAR", beanExpedientePrevision.getBdMontoBrutoBeneficio().subtract(money));
					}
					if(beanExpedientePrevision.getIntParaTipoCaptacion()==2)
						parametro.put("P_EXPEDIENTE", beanExpedientePrevision.getId().getIntItemExpediente());
					else
						parametro.put("P_EXPEDIENTE", "");
		        System.out.println("PARAMETRO "+ parametro);
	            
	           	strNombreReporte = "sepelio";
				UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteSepelio), Constante.PARAM_T_TIPOREPORTE_PDF);
			 
		} catch (Exception e) {
			log.error("Error en imprimirSepelio ---> "+e);
		}
	 }
	
	//solicitud AEs
	 public void imprimirSepelioAes(){
		 String strNombreReporte = " ";
		 HashMap<String,Object> parametro = new HashMap<String,Object>();
		 Tabla autorizacion = new Tabla();
		 DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(',');
			NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);
	    	CuentaConceptoDetalle ctaCptoDet = new CuentaConceptoDetalle();
	    	ctaCptoDet.setId(new CuentaConceptoDetalleId());
	    	List<Expediente> lstExpediente = new ArrayList<Expediente>();
		 lstParaQuePinteSepelio.clear();
		 try {
			 EstructuraFacadeRemote facade1 = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			 verSolicitudPrevision(null);
			 autorizacion= new Tabla();
			 autorizacion.setStrAbreviatura("");
			 lstParaQuePinteSepelio.add(autorizacion);
			 String descripcion ="";
			 if(beanExpedientePrevision.getIntParaTipoCaptacion().compareTo(new Integer(Constante.PARAM_T_TIPOSOLICITUDPREVISION_AES))==0 ){
						if(beanExpedientePrevision.getIntParaSubTipoOperacion()==1){
							 descripcion = "Costo medicinas";
						}else if(beanExpedientePrevision.getIntParaSubTipoOperacion()==2){
							 descripcion = "Hospitalización";
						}else if(beanExpedientePrevision.getIntParaSubTipoOperacion()==3){
							 descripcion = "Exámen clínico";
						}else if(beanExpedientePrevision.getIntParaSubTipoOperacion()==4){
							 descripcion = "Excepción gerencia";
						}else if(beanExpedientePrevision.getIntParaSubTipoOperacion()==5){
							 descripcion = "Excepción jefatura";
						}
			 		}
			 
			 parametro.put("P_TIPOSOLICITUD", descripcion);
			 //
			 lstParaQuePinteSepelio.add(autorizacion);
			 GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
			 PrevisionFacadeLocal beneficiario =(PrevisionFacadeLocal)EJBFactory.getLocal(PrevisionFacadeLocal.class);
			 
			 lstExpediente = conceptoFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(beanExpedientePrevision.getId().getIntPersEmpresaPk(), 
					 beanExpedientePrevision.getId().getIntCuentaPk());
			 
			 if(lstExpediente !=null && !lstExpediente.isEmpty()){
				  BigDecimal suma = BigDecimal.ZERO;
				  BigDecimal saldo = BigDecimal.ZERO;
				 for(int k=0; k<lstExpediente.size();k++){
					 saldo = suma.add(lstExpediente.get(k).getBdSaldoCredito());
				 }
				 parametro.put("P_SALDO", formato.format(saldo));
			 }else{
	            	parametro.put("P_SALDO", "");
	      }
			 
			 parametro.put("P_NUMCUENTA",beanSocioComp.getCuenta().getStrNumeroCuenta());
			 if(beanExpedientePrevision.getIntParaTipoCaptacion()==11)
				 parametro.put("P_EXPEDIENTE", beanExpedientePrevision.getId().getIntItemExpediente());
			 else
				 parametro.put("P_EXPEDIENTE", "");
			 for(EstadoPrevision lstEstadoPrevision : beanExpedientePrevision.getListaEstadoPrevision()){
	              if(lstEstadoPrevision.getIntParaEstado()==2){
	            	  String fechaEstado = Constante.sdf.format(lstEstadoPrevision.getTsFechaEstado());
	            	parametro.put("P_FECHA", fechaEstado);
	              	break;
	              }else{
	            	  parametro.put("P_FECHA", "");
	              }
	            }
			 
			 parametro.put("P_NUMCUENTA",beanSocioComp.getCuenta().getStrNumeroCuenta());
	        
			 String codigo = Integer.toString(beanSocioComp.getPersona().getIntIdPersona());
			 
			 for(int i = 0; i < 9; i++){
	            	parametro.put("C"+i, "0");
	            }

			 for(int i = 0; i < codigo.length(); i++){
	            	parametro.put("C"+i, codigo.substring(i, i+1));

	            }
			 if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && 
	            		beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1)
				 parametro.put("P_DNI", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad());
			 else
				 parametro.put("P_DNI", "");
			 
			 parametro.put("P_MONTOPRESTAMO", formato.format(beanExpedientePrevision.getBdMontoBrutoBeneficio()));
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
	            	 if(dom.getStrReferencia()!=null)
	            		 parametro.put("P_REFERENCIA", dom.getStrReferencia());
	            	 else
	            		 parametro.put("P_REFERENCIA", "");
	            //
	            List<Comunicacion> lstComunicacion = beanSocioComp.getPersona().getListaComunicacion();
	            
	            String numero="";
	            if(lstComunicacion!=null && !lstComunicacion.isEmpty()){
	         	   int prioridad = 99;
	         	   for(Comunicacion com : lstComunicacion){
	            		   if(com.getIntSubTipoComunicacionCod()==4 && com.getIntTipoComunicacionCod()==1 && prioridad>5){
	         			   prioridad = 5;
	         			   numero = Integer.toString(com.getIntNumero());
	         	       }else if(com.getIntSubTipoComunicacionCod()==3 && com.getIntTipoComunicacionCod()==1 && prioridad>4){
	         	    	   prioridad = 4;
	         			   numero = Integer.toString(com.getIntNumero());
	         		   }else if(com.getIntSubTipoComunicacionCod()==2 && com.getIntTipoComunicacionCod()==1 && prioridad>3){
	         	    	   prioridad = 3;
	         			   numero = Integer.toString(com.getIntNumero());
	         		   }else if(com.getIntSubTipoComunicacionCod()==1 && com.getIntTipoComunicacionCod()==1 && prioridad>2){
	         	    	   prioridad = 2;
	         			   numero = Integer.toString(com.getIntNumero());
	         		   }else if(com.getIntSubTipoComunicacionCod()==5 && com.getIntTipoComunicacionCod()==1 && prioridad>1){
	         	    	   prioridad = 1;
	         			   numero = Integer.toString(com.getIntNumero());
	         		   }
	         	   	}
	            }
	            for(int i = 0; i < 9; i++){
	            	parametro.put("T"+i, "");
	            }
	            for(int i = 0; i < numero.length(); i++){
	            	parametro.put("T"+i, numero.substring(i, i+1));
	            }

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
	            listaSucursalBusqueda = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
		        parametro.put("P_AGENCIA", listaSucursalBusqueda.get(0).getJuridica().getStrSiglas());
		        
		        List<FallecidoPrevision> listRodo = new ArrayList<FallecidoPrevision>();
		        if(beanExpedientePrevision.getIntParaTipoCaptacion()==11){
				 List<FallecidoPrevision> lstBeneficiario = beneficiario.getListaNombreCompletoAes(beanExpedientePrevision.getId().getIntPersEmpresaPk(), 
						 beanExpedientePrevision.getId().getIntCuentaPk(), beanExpedientePrevision.getId().getIntItemExpediente());
				 	if (lstBeneficiario!=null && !lstBeneficiario.isEmpty()) {
						for (FallecidoPrevision fallecidoPrevision : lstBeneficiario) {
							fallecidoPrevision.setStrNomApeBeneficiario(fallecidoPrevision.getStrApePaterno().toUpperCase()+" "+fallecidoPrevision.getStrApeMaterno().toUpperCase()+", "+fallecidoPrevision.getStrNombre().toUpperCase());
							listRodo.add(fallecidoPrevision);
							parametro.put("P_LIST_TIPOBENI", listRodo);
						}
					}else{
						if (lstBeneficiario==null || lstBeneficiario.isEmpty()) {
							lstBeneficiario = new ArrayList<FallecidoPrevision>();
							FallecidoPrevision x = new FallecidoPrevision();
							x.setStrNomApeBeneficiario("");
							lstBeneficiario.add(x);
							parametro.put("P_LIST_TIPOBENI", listRodo);
						}
					}
		        }else{
		        	List<BeneficiarioPrevision> lstBeneficiario=null;
		        	if (lstBeneficiario==null || lstBeneficiario.isEmpty()) {
						lstBeneficiario = new ArrayList<BeneficiarioPrevision>();
						BeneficiarioPrevision x = new BeneficiarioPrevision();
						x.setStrNomApeBeneficiario("");
						lstBeneficiario.add(x);
						parametro.put("P_LIST_TIPOBENI", listRodo);
		        	}
		        }

		        if(beanExpedientePrevision.getIntParaTipoCaptacion()==11){
				 	List<Tabla> lstDescVinculo = new ArrayList<Tabla>();
				 	TablaFacadeRemote decripcionVinculo = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
					List<FallecidoPrevision> lstBeneficiarioDescripcion = beneficiario.getListaVinculoAes(beanExpedientePrevision.getId().getIntPersEmpresaPk(), 
																	beanExpedientePrevision.getId().getIntCuentaPk(),
																	beanExpedientePrevision.getId().getIntItemExpediente());
					if(lstBeneficiarioDescripcion==null || lstBeneficiarioDescripcion.isEmpty() ){
						lstDescVinculo = new ArrayList<Tabla>();
						Tabla t = new Tabla();
						t.setStrDescripcion("");
						lstDescVinculo.add(t);
						parametro.put("P_LIST_TIPODESC", lstDescVinculo);
					}else{
					for(FallecidoPrevision listDescrip : lstBeneficiarioDescripcion){
						Tabla decripcionVinculo1 = decripcionVinculo.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVINCULO), listDescrip.getIntTipoVinculoCod());
						lstDescVinculo.add(decripcionVinculo1);
						parametro.put("P_LIST_TIPODESC", lstDescVinculo);
					}
				}
			 }	
		        System.out.println("PARAMETRO "+ parametro);
	            
	           	strNombreReporte = "solicitudAes";
				UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteSepelio), Constante.PARAM_T_TIPOREPORTE_PDF);
			 
		} catch (Exception e) {
			log.error("Error en imprimirSepelioAes ---> "+e);
		}
	 }
	 /**
	  * Se Imprime la solicitud de Renuncia FONRET
	  * @return
	  */
	 
	    public void renunciaFonret(){
	    	String strNombreReporte = "";
	    	HashMap<String,Object> parametro = new HashMap<String,Object>();
	    	Tabla x = new Tabla();
	    	lstParaQuePinteSepelio.clear();
			DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
			try {
				x.setStrAbreviatura("");
				lstParaQuePinteSepelio.add(x);
				verSolicitudPrevision(null);
				parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
				String apellidoPaterno = (beanSocioComp.getPersona().getNatural().getStrApellidoPaterno().toUpperCase()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoPaterno().toUpperCase():" ");
				String apellidoMaterno = (beanSocioComp.getPersona().getNatural().getStrApellidoMaterno().toUpperCase()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoMaterno().toUpperCase():" ");
				String Nombre = (beanSocioComp.getPersona().getNatural().getStrNombres().toUpperCase()!=null?beanSocioComp.getPersona().getNatural().getStrNombres().toUpperCase():" "); 
				parametro.put("P_NOMBRECOMPLETO", apellidoPaterno +" " + apellidoMaterno + ", " + Nombre);
				parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
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
	            
				parametro.put("P_DIRECCION",  strVia + " " + dom.getStrNombreVia() + " " +dom.getIntNumeroVia());
	            
	            Date fechaAct = new Date();
	            
	            String fecha = df4.format(fechaAct);
				String dia = fecha.substring(0, 1).toUpperCase();
				String fechafinal = df4.format(fechaAct);
				String todo = dia + "" + fechafinal.substring(1, fechafinal.length());
				parametro.put("P_FECHAESTADO", todo);
				
				System.out.println("Parametro : " + parametro);
				strNombreReporte = "renunciaFonret";
				UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteSepelio), Constante.PARAM_T_TIPOREPORTE_PDF);
			} catch (Exception e) {
				log.error("Error en imprimirSolicitudDeAfiliacion ---> "+e);
			}   	
	    }

	 public Boolean getBlnBotonActulizar() {
		return blnBotonActulizar;
	}

	public void setBlnBotonActulizar(Boolean blnBotonActulizar) {
		this.blnBotonActulizar = blnBotonActulizar;
	}

	public Boolean getBlnBotonVer() {
		return blnBotonVer;
	}

	public void setBlnBotonVer(Boolean blnBotonVer) {
		this.blnBotonVer = blnBotonVer;
	}

	public Boolean getBlnBotonEliminar() {
		return blnBotonEliminar;
	}

	public void setBlnBotonEliminar(Boolean blnBotonEliminar) {
		this.blnBotonEliminar = blnBotonEliminar;
	}

	public Boolean getBlnTieneAdjuntosConfigurados() {
		return blnTieneAdjuntosConfigurados;
	}

	public void setBlnTieneAdjuntosConfigurados(Boolean blnTieneAdjuntosConfigurados) {
		this.blnTieneAdjuntosConfigurados = blnTieneAdjuntosConfigurados;
	}

	public Boolean getBlnCondicionCuotaRetiro() {
		return blnCondicionCuotaRetiro;
	}

	public String getStrUnidadesEjecutorasConcatenadas() {
		return strUnidadesEjecutorasConcatenadas;
	}

	public void setStrUnidadesEjecutorasConcatenadas(
			String strUnidadesEjecutorasConcatenadas) {
		this.strUnidadesEjecutorasConcatenadas = strUnidadesEjecutorasConcatenadas;
	}

	public void setBlnCondicionCuotaRetiro(Boolean blnCondicionCuotaRetiro) {
		this.blnCondicionCuotaRetiro = blnCondicionCuotaRetiro;
	}

	public BigDecimal getBdMontoTotalAportadoRetiro() {
		return bdMontoTotalAportadoRetiro;
	}

	public void setBdMontoTotalAportadoRetiro(BigDecimal bdMontoTotalAportadoRetiro) {
		this.bdMontoTotalAportadoRetiro = bdMontoTotalAportadoRetiro;
	}

	public Integer getIntNroCuotasDefinidasRetiro() {
		return intNroCuotasDefinidasRetiro;
	}

	public void setIntNroCuotasDefinidasRetiro(Integer intNroCuotasDefinidasRetiro) {
		this.intNroCuotasDefinidasRetiro = intNroCuotasDefinidasRetiro;
	}

	public pe.com.tumi.credito.socio.captacion.domain.Vinculo getVinculoRetiroSeleccionado() {
		return vinculoRetiroSeleccionado;
	}

	public void setVinculoRetiroSeleccionado(
			pe.com.tumi.credito.socio.captacion.domain.Vinculo vinculoRetiroSeleccionado) {
		this.vinculoRetiroSeleccionado = vinculoRetiroSeleccionado;
	}

	public BigDecimal getBdValorAnnosEntero() {
		return bdValorAnnosEntero;
	}

	public void setBdValorAnnosEntero(BigDecimal bdValorAnnosEntero) {
		this.bdValorAnnosEntero = bdValorAnnosEntero;
	}

	public String getStrMsgTxtRetiroValorCuotaMensual() {
		return strMsgTxtRetiroValorCuotaMensual;
	}

	public void setStrMsgTxtRetiroValorCuotaMensual(
			String strMsgTxtRetiroValorCuotaMensual) {
		this.strMsgTxtRetiroValorCuotaMensual = strMsgTxtRetiroValorCuotaMensual;
	}

	public BigDecimal getBdValorNumeroCuotas() {
		return bdValorNumeroCuotas;
	}

	public void setBdValorNumeroCuotas(BigDecimal bdValorNumeroCuotas) {
		this.bdValorNumeroCuotas = bdValorNumeroCuotas;
	}

	public BigDecimal getBdAnnosAportacionCalculado() {
		return bdAnnosAportacionCalculado;
	}

	public void setBdAnnosAportacionCalculado(BigDecimal bdAnnosAportacionCalculado) {
		this.bdAnnosAportacionCalculado = bdAnnosAportacionCalculado;
	}

	public Boolean getBlnBeneficiarioRetiro() {
		return blnBeneficiarioRetiro;
	}

	public void setBlnBeneficiarioRetiro(Boolean blnBeneficiarioRetiro) {
		this.blnBeneficiarioRetiro = blnBeneficiarioRetiro;
	}

	public Boolean getBlnIsRetiro() {
		return blnIsRetiro;
	}

	public void setBlnIsRetiro(Boolean blnIsRetiro) {
		this.blnIsRetiro = blnIsRetiro;
	}

	public String getCampoBuscarBeneficiario() {
		return campoBuscarBeneficiario;
	}

	public void setCampoBuscarBeneficiario(String campoBuscarBeneficiario) {
		this.campoBuscarBeneficiario = campoBuscarBeneficiario;
	}

	public String getCampoBuscarFallecido() {
		return campoBuscarFallecido;
	}

	public void setCampoBuscarFallecido(String campoBuscarFallecido) {
		this.campoBuscarFallecido = campoBuscarFallecido;
	}

	/*public List<BeneficiarioPrevision> getListaBeneficiarioPrevisionBusqTitular() {
		return listaBeneficiarioPrevisionBusqTitular;
	}

	public void setListaBeneficiarioPrevisionBusqTitular(
			List<BeneficiarioPrevision> listaBeneficiarioPrevisionBusqTitular) {
		this.listaBeneficiarioPrevisionBusqTitular = listaBeneficiarioPrevisionBusqTitular;
	}

	public List<FallecidoPrevision> getListaFallecidosPrevisionBusqTitular() {
		return listaFallecidosPrevisionBusqTitular;
	}

	public void setListaFallecidosPrevisionBusqTitular(
			List<FallecidoPrevision> listaFallecidosPrevisionBusqTitular) {
		this.listaFallecidosPrevisionBusqTitular = listaFallecidosPrevisionBusqTitular;
	}*/

	public String getStrMsgTxtTieneFallecidos(){
		return strMsgTxtTieneFallecidos;
	}

	public void setStrMsgTxtTieneFallecidos(String strMsgTxtTieneFallecidos) {
		this.strMsgTxtTieneFallecidos = strMsgTxtTieneFallecidos;
	}

	public List<FallecidoPrevision> getListaFallecidosPrevisionBusq() {
		return listaFallecidosPrevisionBusq;
	}

	public void setListaFallecidosPrevisionBusq(
			List<FallecidoPrevision> listaFallecidosPrevisionBusq) {
		this.listaFallecidosPrevisionBusq = listaFallecidosPrevisionBusq;
	}

	public FallecidoPrevision getFallecidoSeleccionado() {
		return fallecidoSeleccionado;
	}

	public void setFallecidoSeleccionado(FallecidoPrevision fallecidoSeleccionado) {
		this.fallecidoSeleccionado = fallecidoSeleccionado;
	}

	public Persona getPersonaFallecida() {
		return personaFallecida;
	}

	public void setPersonaFallecida(Persona personaFallecida) {
		this.personaFallecida = personaFallecida;
	}

	public List<ExpedientePrevision> getListaExpedientePrevisionSocio() {
		return listaExpedientePrevisionSocio;
	}

	public void setListaExpedientePrevisionSocio(
			List<ExpedientePrevision> listaExpedientePrevisionSocio) {
		this.listaExpedientePrevisionSocio = listaExpedientePrevisionSocio;
	}

	/*public Integer getIntSubTipoSolicitudExtra() {
		return intSubTipoSolicitudExtra;
	}

	public void setIntSubTipoSolicitudExtra(Integer intSubTipoSolicitudExtra) {
		this.intSubTipoSolicitudExtra = intSubTipoSolicitudExtra;
	}*/

	/*public ExpedientePrevision getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}

	public void setRegistroSeleccionadoBusqueda(
			ExpedientePrevision registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}*/

	public void setListaExpedientePrevision(
			List<ExpedientePrevision> listaExpedientePrevision) {
		this.listaExpedientePrevision = listaExpedientePrevision;
	}

	public List<Tabla> getListaSubTipoSolicitudBusqueda() {
		return listaSubTipoSolicitudBusqueda;
	}

	public List<Tabla> getListaTablaDescripcionAdjuntos() {
		return listaTablaDescripcionAdjuntos;
	}

	public void setListaTablaDescripcionAdjuntos(
			List<Tabla> listaTablaDescripcionAdjuntos) {
		this.listaTablaDescripcionAdjuntos = listaTablaDescripcionAdjuntos;
	}

	public void setListaSubTipoSolicitudBusqueda(
			List<Tabla> listaSubTipoSolicitudBusqueda) {
		this.listaSubTipoSolicitudBusqueda = listaSubTipoSolicitudBusqueda;
	}

	public Integer getIntSubTipoCreditoFiltro() {
		return intSubTipoCreditoFiltro;
	}

	public void setIntSubTipoCreditoFiltro(Integer intSubTipoCreditoFiltro) {
		this.intSubTipoCreditoFiltro = intSubTipoCreditoFiltro;
	}

	public List<Tabla> getListaTipoVinculo() {
		return listaTipoVinculo;
	}

	public void setListaTipoVinculo(List<Tabla> listaTipoVinculo) {
		this.listaTipoVinculo = listaTipoVinculo;
	}

	public List<ExpedientePrevision> getListaExpedientePrevision() {
		return listaExpedientePrevision;
	}
/*
	public void setListaExpedientePrevision(List listaExpedientePrevision) {
		this.listaExpedientePrevision = listaExpedientePrevision;
	}*/

	public Integer getIntTipoCreditoFiltro() {
		return intTipoCreditoFiltro;
	}

	public void setIntTipoCreditoFiltro(Integer intTipoCreditoFiltro) {
		this.intTipoCreditoFiltro = intTipoCreditoFiltro;
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

	public EstadoPrevision getEstadoPrevisionFiltro() {
		return estadoPrevisionFiltro;
	}

	public void setEstadoPrevisionFiltro(EstadoPrevision estadoPrevisionFiltro) {
		this.estadoPrevisionFiltro = estadoPrevisionFiltro;
	}

	public List<Tabla> getListaTablaEstadoPago() {
		return listaTablaEstadoPago;
	}

	public void setListaTablaEstadoPago(List<Tabla> listaTablaEstadoPago) {
		this.listaTablaEstadoPago = listaTablaEstadoPago;
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

	public Integer getIntTipoBusquedaSucursal() {
		return intTipoBusquedaSucursal;
	}

	public void setIntTipoBusquedaSucursal(Integer intTipoBusquedaSucursal) {
		this.intTipoBusquedaSucursal = intTipoBusquedaSucursal;
	}

	public List<Tabla> getListaTipoBusquedaSucursal() {
		return listaTipoBusquedaSucursal;
	}

	public void setListaTipoBusquedaSucursal(List<Tabla> listaTipoBusquedaSucursal) {
		this.listaTipoBusquedaSucursal = listaTipoBusquedaSucursal;
	}

	public Integer getIntIdSubsucursalFiltro() {
		return intIdSubsucursalFiltro;
	}

	public void setIntIdSubsucursalFiltro(Integer intIdSubsucursalFiltro) {
		this.intIdSubsucursalFiltro = intIdSubsucursalFiltro;
	}

	public Integer getIntIdSucursalFiltro() {
		return intIdSucursalFiltro;
	}

	public void setIntIdSucursalFiltro(Integer intIdSucursalFiltro) {
		this.intIdSucursalFiltro = intIdSucursalFiltro;
	}

	/*public Integer getIntTipoSolicitudExtra() {
		return intTipoSolicitudExtra;
	}

	public void setIntTipoSolicitudExtra(Integer intTipoSolicitudExtra) {
		this.intTipoSolicitudExtra = intTipoSolicitudExtra;
	}*/

	public Boolean getBlnHayExcepciones() {
		return blnHayExcepciones;
	}

	public void setBlnHayExcepciones(Boolean blnHayExcepciones) {
		this.blnHayExcepciones = blnHayExcepciones;
	}

	public Boolean getBlnCondicionPresentacionSol() {
		return blnCondicionPresentacionSol;
	}

	public void setBlnCondicionPresentacionSol(Boolean blnCondicionPresentacionSol) {
		this.blnCondicionPresentacionSol = blnCondicionPresentacionSol;
	}

	public Boolean getChkCondicionPresentacionSol() {
		return chkCondicionPresentacionSol;
	}

	public void setChkCondicionPresentacionSol(Boolean chkCondicionPresentacionSol) {
		this.chkCondicionPresentacionSol = chkCondicionPresentacionSol;
	}

	public Boolean getBlnCondicionPeridoMeses() {
		return blnCondicionPeridoMeses;
	}

	public void setBlnCondicionPeridoMeses(Boolean blnCondicionPeridoMeses) {
		this.blnCondicionPeridoMeses = blnCondicionPeridoMeses;
	}

	public Boolean getBlnCondicionCuotaSepelio() {
		return blnCondicionCuotaSepelio;
	}

	public void setBlnCondicionCuotaSepelio(Boolean blnCondicionCuotaSepelio) {
		this.blnCondicionCuotaSepelio = blnCondicionCuotaSepelio;
	}

	public Boolean getBlnCondicionDebeTenerDeuda() {
		return blnCondicionDebeTenerDeuda;
	}

	public void setBlnCondicionDebeTenerDeuda(Boolean blnCondicionDebeTenerDeuda) {
		this.blnCondicionDebeTenerDeuda = blnCondicionDebeTenerDeuda;
	}

	public Boolean getChkCondicionPeridoMeses() {
		return chkCondicionPeridoMeses;
	}

	public void setChkCondicionPeridoMeses(Boolean chkCondicionPeridoMeses) {
		this.chkCondicionPeridoMeses = chkCondicionPeridoMeses;
	}

	public Boolean getChkCondicionCuotaSepelio() {
		return chkCondicionCuotaSepelio;
	}

	public void setChkCondicionCuotaSepelio(Boolean chkCondicionCuotaSepelio) {
		this.chkCondicionCuotaSepelio = chkCondicionCuotaSepelio;
	}

	public Boolean getChkCondicionDebeTenerDeuda() {
		return chkCondicionDebeTenerDeuda;
	}

	public void setChkCondicionDebeTenerDeuda(Boolean chkCondicionDebeTenerDeuda) {
		this.chkCondicionDebeTenerDeuda = chkCondicionDebeTenerDeuda;
	}

	public Boolean getBlnSumaPorcentajes() {
		return blnSumaPorcentajes;
	}

	public void setBlnSumaPorcentajes(Boolean blnSumaPorcentajes) {
		this.blnSumaPorcentajes = blnSumaPorcentajes;
	}

	public Integer getIntNroCuotasPagadasTotal() {
		return intNroCuotasPagadasTotal;
	}

	public void setIntNroCuotasPagadasTotal(Integer intNroCuotasPagadasTotal) {
		this.intNroCuotasPagadasTotal = intNroCuotasPagadasTotal;
	}

	public String getStrMsgTxtSumaPorcentaje() {
		return strMsgTxtSumaPorcentaje;
	}

	public void setStrMsgTxtSumaPorcentaje(String strMsgTxtSumaPorcentaje) {
		this.strMsgTxtSumaPorcentaje = strMsgTxtSumaPorcentaje;
	}

	public Boolean getBlnDeshabilitar() {
		return blnDeshabilitar;
	}

	public void setBlnDeshabilitar(Boolean blnDeshabilitar) {
		this.blnDeshabilitar = blnDeshabilitar;
	}

	public String getStrMinimoMaximo() {
		return strMinimoMaximo;
	}

	public void setStrMinimoMaximo(String strMinimoMaximo) {
		this.strMinimoMaximo = strMinimoMaximo;
	}

	public String getStrMsgTxtTieneBeneficiarios() {
		return strMsgTxtTieneBeneficiarios;
	}

	public void setStrMsgTxtTieneBeneficiarios(String strMsgTxtTieneBeneficiarios) {
		this.strMsgTxtTieneBeneficiarios = strMsgTxtTieneBeneficiarios;
	}

	public Integer getIntTiempoSustentoTabla() {
		return intTiempoSustentoTabla;
	}

	public void setIntTiempoSustentoTabla(Integer intTiempoSustentoTabla) {
		this.intTiempoSustentoTabla = intTiempoSustentoTabla;
	}

	public String getStrFrecuenciaPresentacionSolicitud() {
		return strFrecuenciaPresentacionSolicitud;
	}

	public void setStrFrecuenciaPresentacionSolicitud(
			String strFrecuenciaPresentacionSolicitud) {
		this.strFrecuenciaPresentacionSolicitud = strFrecuenciaPresentacionSolicitud;
	}

	public Integer getIntValorComparacion() {
		return intValorComparacion;
	}

	public void setIntValorComparacion(Integer intValorComparacion) {
		this.intValorComparacion = intValorComparacion;
	}

	public String getStrMsgTxtSepelioFechaPresentacion() {
		return strMsgTxtSepelioFechaPresentacion;
	}

	public void setStrMsgTxtSepelioFechaPresentacion(
			String strMsgTxtSepelioFechaPresentacion) {
		this.strMsgTxtSepelioFechaPresentacion = strMsgTxtSepelioFechaPresentacion;
	}

	public Boolean getBlnFechafallecimiento() {
		return blnFechafallecimiento;
	}

	public void setBlnFechafallecimiento(Boolean blnFechafallecimiento) {
		this.blnFechafallecimiento = blnFechafallecimiento;
	}

	public Integer getIntNroCuotasDefinidasSepelio() {
		return intNroCuotasDefinidasSepelio;
	}

	public void setIntNroCuotasDefinidasSepelio(Integer intNroCuotasDefinidasSepelio) {
		this.intNroCuotasDefinidasSepelio = intNroCuotasDefinidasSepelio;
	}

	public Integer getIntTiempoAportacion() {
		return intTiempoAportacion;
	}

	public void setIntTiempoAportacion(Integer intTiempoAportacion) {
		this.intTiempoAportacion = intTiempoAportacion;
	}

	public Integer getIntTiempoPresentacion() {
		return intTiempoPresentacion;
	}

	public void setIntTiempoPresentacion(Integer intTiempoPresentacion) {
		this.intTiempoPresentacion = intTiempoPresentacion;
	}

	public BigDecimal getBdPorcGastosAdministrativo() {
		return bdPorcGastosAdministrativo;
	}

	public void setBdPorcGastosAdministrativo(BigDecimal bdPorcGastosAdministrativo) {
		this.bdPorcGastosAdministrativo = bdPorcGastosAdministrativo;
	}

	public VinculoFacadeRemote getVinculoFacade() {
		return vinculoFacade;
	}

	public void setVinculoFacade(VinculoFacadeRemote vinculoFacade) {
		this.vinculoFacade = vinculoFacade;
	}

	public String getStrMsgTxtAESFechaSustentacion() {
		return strMsgTxtAESFechaSustentacion;
	}

	public void setStrMsgTxtAESFechaSustentacion(
			String strMsgTxtAESFechaSustentacion) {
		this.strMsgTxtAESFechaSustentacion = strMsgTxtAESFechaSustentacion;
	}

	public Boolean getBlnFechaSustentoMedico() {
		return blnFechaSustentoMedico;
	}

	public void setBlnFechaSustentoMedico(Boolean blnFechaSustentoMedico) {
		this.blnFechaSustentoMedico = blnFechaSustentoMedico;
	}

	public Boolean getBlnBeneficiarioSepelio() {
		return blnBeneficiarioSepelio;
	}

	public void setBlnBeneficiarioSepelio(Boolean blnBeneficiarioSepelio) {
		this.blnBeneficiarioSepelio = blnBeneficiarioSepelio;
	}

	public Boolean getBlnBeneficiarioNormal() {
		return blnBeneficiarioNormal;
	}

	public void setBlnBeneficiarioNormal(Boolean blnBeneficiarioNormal) {
		this.blnBeneficiarioNormal = blnBeneficiarioNormal;
	}
	/*
	public Boolean getBlnCampoHabil() {
		return blnCampoHabil;
	}

	public void setBlnCampoHabil(Boolean blnCampoHabil) {
		this.blnCampoHabil = blnCampoHabil;
	}
	*/
	public List<Tabla> getListaSubTipoSolicitud() {
		return listaSubTipoSolicitud;
	}

	public void setListaSubTipoSolicitud(List<Tabla> listaSubTipoSolicitud) {
		this.listaSubTipoSolicitud = listaSubTipoSolicitud;
	}

	public Integer getIntSolicitudRegularidad() {
		return intSolicitudRegularidad;
	}

	public void setIntSolicitudRegularidad(Integer intSolicitudRegularidad) {
		this.intSolicitudRegularidad = intSolicitudRegularidad;
	}

	public Integer getIntSolicitudPeriodicidad() {
		return intSolicitudPeriodicidad;
	}

	public void setIntSolicitudPeriodicidad(Integer intSolicitudPeriodicidad) {
		this.intSolicitudPeriodicidad = intSolicitudPeriodicidad;
	}

	public String getStrMsgTxtCaptacionSepelio() {
		return strMsgTxtCaptacionSepelio;
	}

	public void setStrMsgTxtCaptacionSepelio(String strMsgTxtCaptacionSepelio) {
		this.strMsgTxtCaptacionSepelio = strMsgTxtCaptacionSepelio;
	}

	public BigDecimal getBdMontoTotalAportadoSepelio() {
		return bdMontoTotalAportadoSepelio;
	}

	public void setBdMontoTotalAportadoSepelio(
			BigDecimal bdMontoTotalAportadoSepelio) {
		this.bdMontoTotalAportadoSepelio = bdMontoTotalAportadoSepelio;
	}

	public Boolean getBlnExisteBeneficiario() {
		return blnExisteBeneficiario;
	}

	public void setBlnExisteBeneficiario(Boolean blnExisteBeneficiario) {
		this.blnExisteBeneficiario = blnExisteBeneficiario;
	}

	public String getStrMsgTxtAgregarBeneficiario() {
		return strMsgTxtAgregarBeneficiario;
	}

	public void setStrMsgTxtAgregarBeneficiario(String strMsgTxtAgregarBeneficiario) {
		this.strMsgTxtAgregarBeneficiario = strMsgTxtAgregarBeneficiario;
	}

	public Boolean getBlnShowValidarDatos() {
		return blnShowValidarDatos;
	}

	public void setBlnShowValidarDatos(Boolean blnShowValidarDatos) {
		this.blnShowValidarDatos = blnShowValidarDatos;
	}

	public String getStrSubsucursalSocio() {
		return strSubsucursalSocio;
	}

	public void setStrSubsucursalSocio(String strSubsucursalSocio) {
		this.strSubsucursalSocio = strSubsucursalSocio;
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

	public String getStrMsgTxtCaptacionConcepto() {
		return strMsgTxtCaptacionConcepto;
	}

	public void setStrMsgTxtCaptacionConcepto(String strMsgTxtCaptacionConcepto) {
		this.strMsgTxtCaptacionConcepto = strMsgTxtCaptacionConcepto;
	}

	public String getStrMsgTxtCaptacionCondicion() {
		return strMsgTxtCaptacionCondicion;
	}

	public void setStrMsgTxtCaptacionCondicion(String strMsgTxtCaptacionCondicion) {
		this.strMsgTxtCaptacionCondicion = strMsgTxtCaptacionCondicion;
	}

	public Boolean getBlnValidacionCaptacionRecuperada() {
		return blnValidacionCaptacionRecuperada;
	}

	public void setBlnValidacionCaptacionRecuperada(
			Boolean blnValidacionCaptacionRecuperada) {
		this.blnValidacionCaptacionRecuperada = blnValidacionCaptacionRecuperada;
	}

	public String getStrMsgTxtGrabar() {
		return strMsgTxtGrabar;
	}

	public void setStrMsgTxtGrabar(String strMsgTxtGrabar) {
		this.strMsgTxtGrabar = strMsgTxtGrabar;
	}

	public Boolean getBlnPostEvaluacion() {
		return blnPostEvaluacion;
	}

	public void setBlnPostEvaluacion(Boolean blnPostEvaluacion) {
		this.blnPostEvaluacion = blnPostEvaluacion;
	}

	public BeneficiarioPrevision getBeneficiarioSeleccionado() {
		return beneficiarioSeleccionado;
	}

	public void setBeneficiarioSeleccionado(
			BeneficiarioPrevision beneficiarioSeleccionado) {
		this.beneficiarioSeleccionado = beneficiarioSeleccionado;
	}

	public ExpedientePrevisionComp getRegistroSeleccionado() {
		return registroSeleccionado;
	}

	public void setRegistroSeleccionado(ExpedientePrevisionComp registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}

	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}

	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}

	public Date getDtFechaFallecimiento() {
		return dtFechaFallecimiento;
	}

	public void setDtFechaFallecimiento(Date dtFechaFallecimiento) {
		this.dtFechaFallecimiento = dtFechaFallecimiento;
	}
/*
	public String getStrFechaSustento() {
		return strFechaSustento;
	}

	public void setStrFechaSustento(String strFechaSustento) {
		this.strFechaSustento = strFechaSustento;
	}*/

	public Date getDtFechaSustento() {
		return dtFechaSustento;
	}

	public void setDtFechaSustento(Date dtFechaSustento) {
		this.dtFechaSustento = dtFechaSustento;
	}

	public Integer getIntUltimoEstadoPrevision() {
		return intUltimoEstadoPrevision;
	}

	public void setIntUltimoEstadoPrevision(Integer intUltimoEstadoPrevision) {
		this.intUltimoEstadoPrevision = intUltimoEstadoPrevision;
	}

	public String getStrUltimoEstadoPrevision() {
		return strUltimoEstadoPrevision;
	}

	public void setStrUltimoEstadoPrevision(String strUltimoEstadoPrevision) {
		this.strUltimoEstadoPrevision = strUltimoEstadoPrevision;
	}

	public String getStrMsgTxtFechaSepelio() {
		return strMsgTxtFechaSepelio;
	}

	public void setStrMsgTxtFechaSepelio(String strMsgTxtFechaSepelio) {
		this.strMsgTxtFechaSepelio = strMsgTxtFechaSepelio;
	}
	/*
	public Boolean getBlnMostrarDatosSepelio() {
		return blnMostrarDatosSepelio;
	}

	public void setBlnMostrarDatosSepelio(Boolean blnMostrarDatosSepelio) {
		this.blnMostrarDatosSepelio = blnMostrarDatosSepelio;
	}

	public Boolean getBlnHabilitarComboSubTipo() {
		return blnHabilitarComboSubTipo;
	}

	public void setBlnHabilitarComboSubTipo(Boolean blnHabilitarComboSubTipo) {
		this.blnHabilitarComboSubTipo = blnHabilitarComboSubTipo;
	}*/

	public Estructura getBeanEstructuraSocioComp() {
		return beanEstructuraSocioComp;
	}

	public void setBeanEstructuraSocioComp(Estructura beanEstructuraSocioComp) {
		this.beanEstructuraSocioComp = beanEstructuraSocioComp;
	}

	public String getStrMsgTxtEvaluacion() {
		return strMsgTxtEvaluacion;
	}

	public void setStrMsgTxtEvaluacion(String strMsgTxtEvaluacion) {
		this.strMsgTxtEvaluacion = strMsgTxtEvaluacion;
	}

	public String getStrMsgTxtAESPeriocidad() {
		return strMsgTxtAESPeriocidad;
	}

	public void setStrMsgTxtAESPeriocidad(String strMsgTxtAESPeriocidad) {
		this.strMsgTxtAESPeriocidad = strMsgTxtAESPeriocidad;
	}

	public String getStrMsgTxtAESCuotasSepelio() {
		return strMsgTxtAESCuotasSepelio;
	}

	public void setStrMsgTxtAESCuotasSepelio(String strMsgTxtAESCuotasSepelio) {
		this.strMsgTxtAESCuotasSepelio = strMsgTxtAESCuotasSepelio;
	}

	public String getStrMsgTxtAESDeudaExistente() {
		return strMsgTxtAESDeudaExistente;
	}

	public void setStrMsgTxtAESDeudaExistente(String strMsgTxtAESDeudaExistente) {
		this.strMsgTxtAESDeudaExistente = strMsgTxtAESDeudaExistente;
	}

	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}

	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}

	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}

	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}

	public List<BeneficiarioPrevision> getListaBeneficiarioPrevisionBusq() {
		return listaBeneficiarioPrevisionBusq;
	}

	public void setListaBeneficiarioPrevisionBusq(
			List<BeneficiarioPrevision> listaBeneficiarioPrevisionBusq) {
		this.listaBeneficiarioPrevisionBusq = listaBeneficiarioPrevisionBusq;
	}

	public Boolean getBlnCaptacion() {
		return blnCaptacion;
	}

	public void setBlnCaptacion(Boolean blnCaptacion) {
		this.blnCaptacion = blnCaptacion;
	}

	public Captacion getBeanCaptacion() {
		return beanCaptacion;
	}

	public void setBeanCaptacion(Captacion beanCaptacion) {
		this.beanCaptacion = beanCaptacion;
	}

	public CondicionFacadeRemote getCondicionFacade() {
		return condicionFacade;
	}

	public void setCondicionFacade(CondicionFacadeRemote condicionFacade) {
		this.condicionFacade = condicionFacade;
	}

	public CaptacionFacadeRemote getCaptacionFacade() {
		return captacionFacade;
	}

	public void setCaptacionFacade(CaptacionFacadeRemote captacionFacade) {
		this.captacionFacade = captacionFacade;
	}

	public ConvenioFacadeRemote getConvenioFacade() {
		return convenioFacade;
	}

	public void setConvenioFacade(ConvenioFacadeRemote convenioFacade) {
		this.convenioFacade = convenioFacade;
	}

	public EstructuraFacadeRemote getEstructuraFacade() {
		return estructuraFacade;
	}

	public void setEstructuraFacade(EstructuraFacadeRemote estructuraFacade) {
		this.estructuraFacade = estructuraFacade;
	}

	public Integer getIntParaTipoCaptacion() {
		return intParaTipoCaptacion;
	}

	public void setIntParaTipoCaptacion(Integer intParaTipoCaptacion) {
		this.intParaTipoCaptacion = intParaTipoCaptacion;
	}

	public BigDecimal getBdMontoSolicitado() {
		return bdMontoSolicitado;
	}

	public void setBdMontoSolicitado(BigDecimal bdMontoSolicitado) {
		this.bdMontoSolicitado = bdMontoSolicitado;
	}

	public Integer getIntSubTipoSolicitud() {
		return intSubTipoSolicitud;
	}

	public void setIntSubTipoSolicitud(Integer intSubTipoSolicitud) {
		this.intSubTipoSolicitud = intSubTipoSolicitud;
	}

	/*public String getStrFechaFallecimiento() {
		return strFechaFallecimiento;
	}

	public void setStrFechaFallecimiento(String strFechaFallecimiento) {
		this.strFechaFallecimiento = strFechaFallecimiento;
	}*/

	public Boolean getBlnBenefTieneRelFam() {
		return blnBenefTieneRelFam;
	}

	public void setBlnBenefTieneRelFam(Boolean blnBenefTieneRelFam) {
		this.blnBenefTieneRelFam = blnBenefTieneRelFam;
	}

	public String getStrObservacion() {
		return strObservacion;
	}

	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}

	public String getStrMsgTxtMonto() {
		return strMsgTxtMonto;
	}

	public void setStrMsgTxtMonto(String strMsgTxtMonto) {
		this.strMsgTxtMonto = strMsgTxtMonto;
	}

	public String getStrMsgTxtObservacion() {
		return strMsgTxtObservacion;
	}

	public void setStrMsgTxtObservacion(String strMsgTxtObservacion) {
		this.strMsgTxtObservacion = strMsgTxtObservacion;
	}

	public String getStrMsgTxtTipoSolicitud() {
		return strMsgTxtTipoSolicitud;
	}

	public void setStrMsgTxtTipoSolicitud(String strMsgTxtTipoSolicitud) {
		this.strMsgTxtTipoSolicitud = strMsgTxtTipoSolicitud;
	}

	public String getStrMsgTxtSubOperacion() {
		return strMsgTxtSubOperacion;
	}

	public void setStrMsgTxtSubOperacion(String strMsgTxtSubOperacion) {
		this.strMsgTxtSubOperacion = strMsgTxtSubOperacion;
	}

	public List<Tabla> getListaTipoRelacion() {
		return listaTipoRelacion;
	}

	public void setListaTipoRelacion(List<Tabla> listaTipoRelacion) {
		this.listaTipoRelacion = listaTipoRelacion;
	}

	public List<Tabla> getListaTipoSolicitud() {
		return listaTipoSolicitud;
	}

	public void setListaTipoSolicitud(List<Tabla> listaTipoSolicitud) {
		this.listaTipoSolicitud = listaTipoSolicitud;
	}

	public String getStrMsgErrorValidarDatos() {
		return strMsgErrorValidarDatos;
	}

	public void setStrMsgErrorValidarDatos(String strMsgErrorValidarDatos) {
		this.strMsgErrorValidarDatos = strMsgErrorValidarDatos;
	}

	public Persona getNuevoBeneficiario() {
		return nuevoBeneficiario;
	}

	public void setNuevoBeneficiario(Persona nuevoBeneficiario) {
		this.nuevoBeneficiario = nuevoBeneficiario;
	}

	public Integer getIntParaTipoOperacionPersona() {
		return intParaTipoOperacionPersona;
	}

	public void setIntParaTipoOperacionPersona(Integer intParaTipoOperacionPersona) {
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;
	}

	public void setListSubSucursal(List<Subsucursal> listSubSucursal) {
		this.listSubSucursal = listSubSucursal;
	}


	public List<RequisitoPrevisionComp> getListaCondicionesPrevisionComp() {
		return listaCondicionesPrevisionComp;
	}

	public void setListaCondicionesPrevisionComp(
			List<RequisitoPrevisionComp> listaCondicionesPrevisionComp) {
		this.listaCondicionesPrevisionComp = listaCondicionesPrevisionComp;
	}


	public Boolean getBlnIsAES() {
		return blnIsAES;
	}


	public void setBlnIsAES(Boolean blnIsAES) {
		this.blnIsAES = blnIsAES;
	}


	public Boolean getBlnIsSepelio() {
		return blnIsSepelio;
	}


	public void setBlnIsSepelio(Boolean blnIsSepelio) {
		this.blnIsSepelio = blnIsSepelio;
	}


	public List<RequisitoPrevisionComp> getListaRequisitoPrevisionComp() {
		return listaRequisitoPrevisionComp;
	}


	public void setListaRequisitoPrevisionComp(
			List<RequisitoPrevisionComp> listaRequisitoPrevisionComp) {
		this.listaRequisitoPrevisionComp = listaRequisitoPrevisionComp;
	}


	public Integer getIntTipoSolicitud() {
		return intTipoSolicitud;
	}


	public void setIntTipoSolicitud(Integer intTipoSolicitud) {
		this.intTipoSolicitud = intTipoSolicitud;
	}


	public Integer getIntTipoSolicitudBusq() {
		return intTipoSolicitudBusq;
	}


	public void setIntTipoSolicitudBusq(Integer intTipoSolicitudBusq) {
		this.intTipoSolicitudBusq = intTipoSolicitudBusq;
	}


	public List<Tabla> getListDocumentoBusq() {
		return listDocumentoBusq;
	}


	public void setListDocumentoBusq(List<Tabla> listDocumentoBusq) {
		this.listDocumentoBusq = listDocumentoBusq;
	}


	public List<Tabla> getListDocumento() {
		return listDocumento;
	}

	
	public PrevisionFacadeRemote getPrevisionFacade() {
		return previsionFacade;
	}


	public void setPrevisionFacade(PrevisionFacadeRemote previsionFacade) {
		this.previsionFacade = previsionFacade;
	}


	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}


	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}


	public void setListDocumento(List<Tabla> listDocumento) {
		this.listDocumento = listDocumento;
	}


	public ExpedientePrevision getBeanExpedientePrevision() {
		return beanExpedientePrevision;
	}


	public void setBeanExpedientePrevision(
			ExpedientePrevision beanExpedientePrevision) {
		this.beanExpedientePrevision = beanExpedientePrevision;
	}


	public Persona getPersonaBusqueda() {
		return personaBusqueda;
	}


	public void setPersonaBusqueda(Persona personaBusqueda) {
		this.personaBusqueda = personaBusqueda;
	}


	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}


	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}


	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}


	public String getStrNroSolicitud() {
		return strNroSolicitud;
	}
	

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


	public void setStrNroSolicitud(String strNroSolicitud) {
		this.strNroSolicitud = strNroSolicitud;
	}


	public Integer getIntTipoSucursalBusq() {
		return intTipoSucursalBusq;
	}

	
	public void setIntTipoSucursalBusq(Integer intTipoSucursalBusq) {
		this.intTipoSucursalBusq = intTipoSucursalBusq;
	}


	public Integer getIntIdEstadoSolicitud() {
		return intIdEstadoSolicitud;
	}


	public void setIntIdEstadoSolicitud(Integer intIdEstadoSolicitud) {
		this.intIdEstadoSolicitud = intIdEstadoSolicitud;
	}

/*
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}


	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}*/


	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}


	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}


	public Integer getIntParaTipoDescripcion() {
		return intParaTipoDescripcion;
	}


	public void setIntParaTipoDescripcion(Integer intParaTipoDescripcion) {
		this.intParaTipoDescripcion = intParaTipoDescripcion;
	}


	public List<ExpedientePrevisionComp> getListaExpedientePrevisionBusq() {
		return listaExpedientePrevisionBusq;
	}


	public void setListaExpedientePrevisionBusq(
			List<ExpedientePrevisionComp> listaExpedientePrevisionBusq) {
		this.listaExpedientePrevisionBusq = listaExpedientePrevisionBusq;
	}


	public List<RequisitoPrevision> getListaRequisitoPrevision() {
		return listaRequisitoPrevision;
	}


	public void setListaRequisitoPrevision(
			List<RequisitoPrevision> listaRequisitoPrevision) {
		this.listaRequisitoPrevision = listaRequisitoPrevision;
	}

	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}


	public List<Sucursal> getListSucursal() {
		return listSucursal;
	}


	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}


	public List<Archivo> getListaArchivo() {
		return listaArchivo;
	}


	public void setListaArchivo(List<Archivo> listaArchivo) {
		this.listaArchivo = listaArchivo;
	}


	public Archivo getArchivoAdjunto() {
		return archivoAdjunto;
	}


	public void setArchivoAdjunto(Archivo archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}

	public Tabla getTablaEstado() {
		return tablaEstado;
	}
	
	public void setTablaEstado(Tabla tablaEstado) {
		this.tablaEstado = tablaEstado;
	}

	public String getStrSolicitudPrevision() {
		return strSolicitudPrevision;
	}

	public void setStrSolicitudPrevision(String strSolicitudPrevision) {
		this.strSolicitudPrevision = strSolicitudPrevision;
	}

	public Boolean getFormSolicitudPrevisionRendered() {
		return formSolicitudPrevisionRendered;
	}
	
	public Boolean getBlnShowDivFormSolicitudPrevision() {
		return blnShowDivFormSolicitudPrevision;
	}


	public void setBlnShowDivFormSolicitudPrevision(
			Boolean blnShowDivFormSolicitudPrevision) {
		this.blnShowDivFormSolicitudPrevision = blnShowDivFormSolicitudPrevision;
	}


	public void setFormSolicitudPrevisionRendered(
			Boolean formSolicitudPrevisionRendered) {
		this.formSolicitudPrevisionRendered = formSolicitudPrevisionRendered;
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

	public Integer getIntTipoRelacion() {
		return intTipoRelacion;
	}

	public void setIntTipoRelacion(Integer intTipoRelacion) {
		this.intTipoRelacion = intTipoRelacion;
	}
	
	public Integer getIntTipoPrevision() {
		return intTipoPrevision;
	}


	public void setIntTipoPrevision(Integer intTipoPrevision) {
		this.intTipoPrevision = intTipoPrevision;
	}
	
	public Boolean getBlnAutorizacion() {
		return blnAutorizacion;
	}

	public void setBlnAutorizacion(Boolean blnAutorizacion) {
		this.blnAutorizacion = blnAutorizacion;
	}

	public Boolean getBlnArchivamiento() {
		return blnArchivamiento;
	}

	public void setBlnArchivamiento(Boolean blnArchivamiento) {
		this.blnArchivamiento = blnArchivamiento;
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

	
	public CuentaFacadeRemote getCuentaFacade() {
		return cuentaFacade;
	}


	public void setCuentaFacade(CuentaFacadeRemote cuentaFacade) {
		this.cuentaFacade = cuentaFacade;
	}


	public void setSocioFacade(SocioFacadeRemote socioFacade) {
		this.socioFacade = socioFacade;
	}


	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}

	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}

	public Date getDtFecInicio() {
		return dtFecInicio;
	}

	public void setDtFecInicio(Date dtFecInicio) {
		this.dtFecInicio = dtFecInicio;
	}

	public Date getDtFecFin() {
		return dtFecFin;
	}

	public void setDtFecFin(Date dtFecFin) {
		this.dtFecFin = dtFecFin;
	}

	public Boolean getBlnSolicitud() {
		return blnSolicitud;
	}

	public void setBlnSolicitud(Boolean blnSolicitud) {
		this.blnSolicitud = blnSolicitud;
	}

	public Boolean getblnShowValidarDatos() {
		return blnShowValidarDatos;
	}


	public void setblnShowValidarDatos(Boolean blnShowValidarDatos) {
		this.blnShowValidarDatos = blnShowValidarDatos;
	}


	//------------ sesion ---------------------
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}


	public static Logger getLog() {
		return log;
	}


	public static void setLog(Logger log) {
		SolicitudPrevisionController.log = log;
	}


	public Boolean getBlnGiro() {
		return blnGiro;
	}


	public void setBlnGiro(Boolean blnGiro) {
		this.blnGiro = blnGiro;
	}


//	public List getListaExpedientePrevisionX() {
//		return listaExpedientePrevisionX;
//	}
//
//
//	public void setListaExpedientePrevisionX(List listaExpedientePrevisionX) {
//		this.listaExpedientePrevisionX = listaExpedientePrevisionX;
//	}


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


	public String getStrDescripcionTipoPrevision() {
		return strDescripcionTipoPrevision;
	}

	public void setStrDescripcionTipoPrevision(String strDescripcionTipoPrevision) {
		this.strDescripcionTipoPrevision = strDescripcionTipoPrevision;
	}

	public Boolean getBlnMostrarDescripcionTipoPrevision() {
		return blnMostrarDescripcionTipoPrevision;
	}

	public void setBlnMostrarDescripcionTipoPrevision(
			Boolean blnMostrarDescripcionTipoPrevision) {
		this.blnMostrarDescripcionTipoPrevision = blnMostrarDescripcionTipoPrevision;
	}

	public List<Tabla> getListaSubOperacion() {
		return listaSubOperacion;
	}

	public void setListaSubOperacion(List<Tabla> listaSubOperacion) {
		this.listaSubOperacion = listaSubOperacion;
	}

	public String getStrDescripcionPrevision() {
		return strDescripcionPrevision;
	}

	public void setStrDescripcionPrevision(String strDescripcionPrevision) {
		this.strDescripcionPrevision = strDescripcionPrevision;
	}

	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}

	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}


	public PlanillaFacadeRemote getPlanillaFacade() {
		return planillaFacade;
	}


	public void setPlanillaFacade(PlanillaFacadeRemote planillaFacade) {
		this.planillaFacade = planillaFacade;
	}


	public List<CuentaConcepto> getListaCuentaConcepto() {
		return listaCuentaConcepto;
	}


	public void setListaCuentaConcepto(List<CuentaConcepto> listaCuentaConcepto) {
		this.listaCuentaConcepto = listaCuentaConcepto;
	}


	public Boolean getBlnCheckSinRelacion() {
		return blnCheckSinRelacion;
	}


	public void setBlnCheckSinRelacion(Boolean blnCheckSinRelacion) {
		this.blnCheckSinRelacion = blnCheckSinRelacion;
	}


	public Boolean getBlnIsSepelioTitular() {
		return blnIsSepelioTitular;
	}

	public void setBlnIsSepelioTitular(Boolean blnIsSepelioTitular) {
		this.blnIsSepelioTitular = blnIsSepelioTitular;
	}


	public BigDecimal getBdMontoCalculadoBeneficio() {
		return bdMontoCalculadoBeneficio;
	}


	public void setBdMontoCalculadoBeneficio(BigDecimal bdMontoCalculadoBeneficio) {
		this.bdMontoCalculadoBeneficio = bdMontoCalculadoBeneficio;
	}


	public String getStrMsgTxtSepelioTitularNoDebeDeudaExistente() {
		return strMsgTxtSepelioTitularNoDebeDeudaExistente;
	}


	public void setStrMsgTxtSepelioTitularNoDebeDeudaExistente(
			String strMsgTxtSepelioTitularNoDebeDeudaExistente) {
		this.strMsgTxtSepelioTitularNoDebeDeudaExistente = strMsgTxtSepelioTitularNoDebeDeudaExistente;
	}


	public CreditoFacadeRemote getCreditoFacade() {
		return creditoFacade;
	}


	public void setCreditoFacade(CreditoFacadeRemote creditoFacade) {
		this.creditoFacade = creditoFacade;
	}


	public Boolean getBlnCondicionNODebeTenerDeuda() {
		return blnCondicionNODebeTenerDeuda;
	}


	public void setBlnCondicionNODebeTenerDeuda(Boolean blnCondicionNODebeTenerDeuda) {
		this.blnCondicionNODebeTenerDeuda = blnCondicionNODebeTenerDeuda;
	}


	public CuentacteFacadeRemote getCuentaCteFacade() {
		return cuentaCteFacade;
	}


	public void setCuentaCteFacade(CuentacteFacadeRemote cuentaCteFacade) {
		this.cuentaCteFacade = cuentaCteFacade;
	}


	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}


	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}


	public List<AutorizaPrevisionComp> getListaAutorizaPrevisionComp() {
		return listaAutorizaPrevisionComp;
	}


	public void setListaAutorizaPrevisionComp(
			List<AutorizaPrevisionComp> listaAutorizaPrevisionComp) {
		this.listaAutorizaPrevisionComp = listaAutorizaPrevisionComp;
	}


	public ConceptoFacadeRemote getConceptoFacadeRemote() {
		return conceptoFacadeRemote;
	}


	public void setConceptoFacadeRemote(ConceptoFacadeRemote conceptoFacadeRemote) {
		this.conceptoFacadeRemote = conceptoFacadeRemote;
	}


	public String getStrMsgTxtFechaFallecimiento() {
		return strMsgTxtFechaFallecimiento;
	}


	public void setStrMsgTxtFechaFallecimiento(String strMsgTxtFechaFallecimiento) {
		this.strMsgTxtFechaFallecimiento = strMsgTxtFechaFallecimiento;
	}


	public Boolean getChkCondicionCuotaRetiro() {
		return chkCondicionCuotaRetiro;
	}


	public void setChkCondicionCuotaRetiro(Boolean chkCondicionCuotaRetiro) {
		this.chkCondicionCuotaRetiro = chkCondicionCuotaRetiro;
	}


	public String getStrMsgTxtCuotasRetiro() {
		return strMsgTxtCuotasRetiro;
	}


	public void setStrMsgTxtCuotasRetiro(String strMsgTxtCuotasRetiro) {
		this.strMsgTxtCuotasRetiro = strMsgTxtCuotasRetiro;
	}


	public LiquidacionFacadeRemote getLiquidacionFacade() {
		return liquidacionFacade;
	}


	public void setLiquidacionFacade(LiquidacionFacadeRemote liquidacionFacade) {
		this.liquidacionFacade = liquidacionFacade;
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


	public List<Sucursal> getListaSucursalBusqueda() {
		return listaSucursalBusqueda;
	}


	public void setListaSucursalBusqueda(List<Sucursal> listaSucursalBusqueda) {
		this.listaSucursalBusqueda = listaSucursalBusqueda;
	}


	public List<Subsucursal> getListaSubSucursaBusqueda() {
		return listaSubSucursaBusqueda;
	}


	public void setListaSubSucursaBusqueda(List<Subsucursal> listaSubSucursaBusqueda) {
		this.listaSubSucursaBusqueda = listaSubSucursaBusqueda;
	}


	public Integer getIntBusqTipoPrevision() {
		return intBusqTipoPrevision;
	}


	public void setIntBusqTipoPrevision(Integer intBusqTipoPrevision) {
		this.intBusqTipoPrevision = intBusqTipoPrevision;
	}


	public Integer getIntBusqSubTipoPrevision() {
		return intBusqSubTipoPrevision;
	}


	public void setIntBusqSubTipoPrevision(Integer intBusqSubTipoPrevision) {
		this.intBusqSubTipoPrevision = intBusqSubTipoPrevision;
	}


	public List<RequisitoPrevisionComp> getListRequisitoPrevision() {
		return listRequisitoPrevision;
	}


	public void setListRequisitoPrevision(
			List<RequisitoPrevisionComp> listRequisitoPrevision) {
		this.listRequisitoPrevision = listRequisitoPrevision;
	}


	public List<BloqueoCuenta> getListaBloqueoCuenta() {
		return listaBloqueoCuenta;
	}


	public void setListaBloqueoCuenta(List<BloqueoCuenta> listaBloqueoCuenta) {
		this.listaBloqueoCuenta = listaBloqueoCuenta;
	}


	public Boolean getBlnVerRegistroSolExpPrevision() {
		return blnVerRegistroSolExpPrevision;
	}


	public void setBlnVerRegistroSolExpPrevision(
			Boolean blnVerRegistroSolExpPrevision) {
		this.blnVerRegistroSolExpPrevision = blnVerRegistroSolExpPrevision;
	}


	public boolean isMostrarBoton() {
		return mostrarBoton;
	}


	public void setMostrarBoton(boolean mostrarBoton) {
		this.mostrarBoton = mostrarBoton;
	}


	public List<Tabla> getLstParaQuePinteSepelio() {
		return lstParaQuePinteSepelio;
	}


	public void setLstParaQuePinteSepelio(List<Tabla> lstParaQuePinteSepelio) {
		this.lstParaQuePinteSepelio = lstParaQuePinteSepelio;
	}


	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}


	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}


	public boolean isMostrarBotonUno() {
		return mostrarBotonUno;
	}


	public void setMostrarBotonUno(boolean mostrarBotonUno) {
		this.mostrarBotonUno = mostrarBotonUno;
	}


	public boolean isMostrarBotonDos() {
		return mostrarBotonDos;
	}


	public void setMostrarBotonDos(boolean mostrarBotonDos) {
		this.mostrarBotonDos = mostrarBotonDos;
	}

}

