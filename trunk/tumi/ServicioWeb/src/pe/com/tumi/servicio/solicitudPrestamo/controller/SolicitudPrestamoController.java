package pe.com.tumi.servicio.solicitudPrestamo.controller;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
//import java.text.NumberFormat;
//import java.text.NumberFormat;
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

//import com.ibm.gsk.ikeyman.gui.CenteredDialog;

import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.domain.EnviomontoId;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.DownloadFile;
import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.MyFile;
import pe.com.tumi.common.util.Constante;
//import pe.com.tumi.common.util.ConvertidorMontos;
import pe.com.tumi.common.util.ConvertirNumeroLetras;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeRemote;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.creditos.facade.CreditoGarantiaFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadronId;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.credito.socio.estructura.domain.DescuentoId;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.Terceros;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
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
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.domain.PersonaRolPK;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
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
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeNuevoLocal;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeRemote;

public class SolicitudPrestamoController {

	protected static Logger log = Logger.getLogger(SolicitudPrestamoController.class)	;
	
	private ExpedienteCredito beanExpedienteCredito;
	private SocioComp beanSocioCompGarante;
	private SocioComp beanSocioComp;
    //villarreal
//	private EstadoCredito beanEstadoCredito;
	private Credito beanCredito; 
	private Usuario usuario;
	private Integer intIdTipoPersona;
	private Integer intBusqSolicPtmo;
	private String strNroSolicitud;
	private Integer intTipoSucursalBusq;
	private Integer intTotalSucursales;
	private Integer intIdEstadoSolicitud;
	private Date dtFecInicio;
	private Date dtFecFin;
	private String strFechaRegistro;
	private Date dtFechaRegistro;
	private Integer intUltimoEstadoSolicitud;
	private String strUltimoEstadoSolicitud;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;

	private List<ExpedienteCreditoComp> listaExpedienteCreditoComp;
	private List<Tabla> listaSubOpePrestamos;
	private List<Tabla> listaRelacion;
	private List<Estructura> listEstructura;
	private List<Sucursal> listSucursal;
	private List<CapacidadCreditoComp> listaCapacidadCreditoComp;
	private List<CuentaConcepto> listaCuentaConcepto;
	private List<GarantiaCreditoComp> listaGarantiaCreditoComp;
	private List<CronogramaCreditoComp> listaCronogramaCreditoComp;
	private List<RequisitoCreditoComp> listaRequisitoCreditoComp;
	private List<Archivo> listaArchivo;
	private Archivo archivoAdjunto;
	private List<CreditoExcepcion> listaExcepciones;
	private Tabla tablaEstado;
	private Boolean blnModoVisualizacion; // false (nuevo)  true (editar/ver) -- disable(blnModoVisualizacion)
	private String strSolicitudPrestamo;
	private Boolean formSolicitudPrestamoRendered;
	private Boolean pgValidDatos;
	private Boolean blnDatosSocio;
	private Boolean blnEvaluacionCredito;
	private Integer intTipoRelacion;
	private Integer intTipoCredito;
	private BigDecimal bdMontoTotalSolicitado;
	private BigDecimal bdTotalDstos;
	private BigDecimal bdMontoPrestamo;
	private BigDecimal bdAportes;
	private BigDecimal bdPorcAporteMontoSolic;
	private BigDecimal bdCuotaMensual;
	private BigDecimal bdTotalCuotaMensual;
	private BigDecimal bdCapacidadPago;
	private BigDecimal bdMaxCapacidadPago;

	// Tabs de Solicitud de Prestamo
	private Boolean blnSolicitud;
	private Boolean blnAutorizacion;
	private Boolean blnGiro;
	private Boolean blnArchivamiento;
	private Boolean blnCreditosEspeciales;
	private Integer intNroCuotas;
	private Integer intCuotasMaximas;
	private String strPorcInteres;
	private String strPorcAportes;
	private BigDecimal bdPorcSeguroDesgravamen;
	private MyFile fileDocAdjunto;
	//rVillarreal 19.05.2014
	private boolean mostrarBoton;
	// Popup de Garantías
	private String strNroDocumento;
	private Boolean blnDatosGarante;
	private Boolean blnValidaDatosGarante;
	private Integer intItemCreditoGarantia;
	private Boolean validGaranteSolidario;
	private Integer intNroPersGarantizadas;

	private TablaFacadeRemote tablaFacade;
	private SocioFacadeRemote socioFacade = null;
	private SolicitudPrestamoFacadeLocal solicitudPrestamoFacade = null;
	private SolicitudPrestamoFacadeNuevoLocal solicitudPrestamoFacadeNuevo = null;
	private PersonaFacadeRemote personaFacade = null;
	private CreditoFacadeRemote creditoFacade = null;
	private EstructuraFacadeRemote estructuraFacade = null;
	private ConvenioFacadeRemote convenioFacade = null;
	private PlanillaFacadeRemote planillaFacade = null;
	private CaptacionFacadeRemote captacionFacade = null;
	private CreditoGarantiaFacadeRemote creditoGarantiaFacade = null;
	private GeneralFacadeRemote generalFacade = null;
	private ConceptoFacadeRemote conceptoFacade = null;
	private EmpresaFacadeRemote empresaFacade = null;
	private PermisoFacadeRemote permisoFacade = null;
	private CuentaFacadeRemote cuentaFacade = null;
	
	// Mensajes de error
	private String msgTxtTipoOperacion;
	private String msgTxtMontoSolicitado;
	private String msgTxtObservacion;
	private String msgTxtListaCapacidadPago;
	private String msgTxtNroCuotas;
	private String msgTxtErrores;
	private String msgTxtConvenioActivo;
	private String msgTxtEvaluacionFinal;


	private String msgTxtCreditoConvenio;
	private String msgTxtCreditoPk;
	private String msgTxtRolPersona;
	private String msgTxtEstructuraSocio;
	private String msgTxtCondicionLaboral;
	private String msgTxtInteresCredito;
	private String msgTxtExesoCuotasCronograma;
	private String msgTxtTipoCreditoEmpresa;
	private String msgTxtCondicionCredito;
	private String msgTxtCondicionHabil;
	private String msgTxtSubCondicionCuenta;
	private String msgTxtMontoExesivo;
	private String msgTxtEnvioPlla;
	private String msgTxtCuotaMensual;

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
	private String msgTxtPersonaGaranteNoExiste;
	private String msgTxtRolSocioGarante;
	private String msgTxtCondicionSocioNingunoGarante;
	private String msgTxtCondicionSocioGarante;
	private String strMsgErrorValidarDatos;
	private String strMsgErrorValidarMovimientos;

	// Mensajes de error de Estructura
	private String msgTxtEstructuraRepetida;
	private String msgTxtEstrucActivoRepetido;
	private String msgTxtEstrucCesanteRepetido;
	
	// booleanos para deshabilitar la edicion de campos del formulario de solicitud de credito
	private boolean blnBtnAddCapacidad;
	private boolean blnBtnEditCapacidad;
	private boolean blnBtnDelCapacidad;
	private boolean blnBtnEvaluarCredito;
	private boolean blnTextMontoPrestamo;
	private boolean blnCmbTipoOperacion;
	private boolean blnTextNroCuotas;
	private boolean blnBtnAddGarante;
	private boolean blnBtnDelGarante;
	private boolean blnTxtMotivoPrestamo;
	private boolean blnTxtObservacionesPrestamo;
	private boolean blnBtnAddRequisito;
	private boolean blnLnkDescarga;
	private boolean blnYaTieneCredito;
	private List<Tabla> listaTipoConsultaBusqueda;
	private Integer intTipoConsultaBusqueda; // Nombres DNI RUC Razón Social
	private String strConsultaBusqueda;
	private ExpedienteCreditoComp  registroSeleccionadoBusqueda;
	private Boolean blnMostrarEliminar;
	private Boolean blnMostrarModificar;
	private Boolean blnValidarGarantes;
	private String strDescripcionSubTipoOperacion;
	private boolean blnMostrarDescripciones ;
	
	// busqueda grilla
	private EstadoCredito estadoCreditoBusqueda;
	private Persona personaBusqueda;
	private EstructuraDetalle estructuraDetalleBusqueda;
	private List<Tabla> listaTablaDeSucursal;
	private List<Tabla> listaTablaCreditoEmpresa;
	private ExpedienteCredito expCreditoBusqueda;
	private String strNumeroSolicitudBusq;
	private Boolean blnTxtBusqueda;
	private List<ExpedienteComp> listExpedienteMovimientoComp;
	private List<Terceros> listTerceros = null;
	private List<Terceros> listColumnCpto = null;
	private Integer intFrecuenciaTerceros;
	private Boolean blnSeGeneroCronogramaCorrectamente;
	private String strErrorGrabar="";
	private Integer intTipoEvaluacion;
	private List<Tabla> listaCondicionSocio;  		// PARAM_T_CONDICIONSOCIO = "65"
	private String strCondicionSocio;
	private List<Tabla> listaTipoCondicionSocio;  	// PARAM_T_TIPO_CONDSOCIO = "90"
	private String strTipoCondicionSocio;
	private List<Tabla> listaCondicionLaboral;  	// PARAM_T_CONDICIONLABORAL = "72"
	private String strCondicionLaboral;
	private List<Tabla> listaSituacionLaboral;  	// PARAM_T_TIPOSOCIO = "28"
	private String strSituacionLaboral;
	private String msgTxtCondicionHabilGarante;
	private List<Tabla> listaEstadoPrestamo;
	private String strUltimoEstado;
	private List<Credito> listaCreditosValidados;
	private List<AutorizaCreditoComp> listaAutorizaCreditoComp;
	private Boolean blnBloquearXCuenta;
	private String strMensajeValidacionCuenta;
	private Integer intGarantesCorrectos;
	private CreditoGarantia garantiasPersonales;
	private String strMensajeGarantesObservacion;
	private String msgTxtYaExisteGaranteCOnfiguracionIgual;
	private String msgTxtDescuentoJudicial;
	
	// parametros de busqueda - cgd 17.10.2013
	private Integer intBusqTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;			  
	private Integer intBusqEstado;		    
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private	List<Sucursal> listaSucursal;
	private Integer intBusqTipoCreditoEmpresa;
	
	// CGD - 04.11.2013
	private CuentaConcepto ctaCtoAporte;
	private CuentaConcepto ctaCtoRetiro;
	private CuentaConcepto ctaCtoSepelio;
	private BigDecimal bdMontoADescontarRePrestamo;
	//private ExpedienteComp expedienteParaReprestamo;
	private List<ExpedienteComp> listaExpedientesParaReprestamo;
	private BigDecimal bdMontoSaldoAnterior = BigDecimal.ZERO;
	
	private BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
	//private BigDecimal bdMontoInteresAtrasado;
	
	//RVILLARREAL 23.04.2014
	private List<Tabla> lstParaQuePinteReporte;
	
	/**
	 * Constructor por defecto
	 */
	public SolicitudPrestamoController() {
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
		blnYaTieneCredito = Boolean.FALSE;
		beanCredito = new Credito();
		beanCredito.setId(new CreditoId());
		blnEvaluacionCredito = false;
		blnDatosGarante = false;
		blnValidaDatosGarante = true;
		beanExpedienteCredito = new ExpedienteCredito();
		beanExpedienteCredito.setId(new ExpedienteCreditoId());
		beanExpedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());
		beanExpedienteCredito.setListaCronogramaCredito(new ArrayList<CronogramaCredito>());
		beanExpedienteCredito.setListaGarantiaCredito(new ArrayList<GarantiaCredito>());
		beanExpedienteCredito.setListaCapacidadCreditoComp(new ArrayList<CapacidadCreditoComp>());
		beanExpedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
		listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
		bdMontoTotalSolicitado = new BigDecimal(0);
		bdTotalDstos = new BigDecimal(0);
		bdMontoPrestamo = new BigDecimal(0);
		bdMontoInteresAtrasado = new BigDecimal(0);
		bdAportes = new BigDecimal(0);
		bdCuotaMensual = new BigDecimal(0);
		bdTotalCuotaMensual = new BigDecimal(0);
		bdPorcAporteMontoSolic = new BigDecimal(0);
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		beanSocioCompGarante = new SocioComp();
		beanSocioCompGarante.setPersona(new Persona());
		validGaranteSolidario = true;
		listaGarantiaCreditoComp = new ArrayList<GarantiaCreditoComp>();
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		listaArchivo = new ArrayList<Archivo>();
		intCuotasMaximas = 0;
		intUltimoEstadoSolicitud = null;
		strUltimoEstadoSolicitud = "";
		blnModoVisualizacion = false;
		blnBtnAddCapacidad = false;
		blnBtnEditCapacidad = false;
		blnBtnDelCapacidad = false;
		blnBtnEvaluarCredito = false;
		blnTextMontoPrestamo = true;
		blnTextNroCuotas = false;
		blnCmbTipoOperacion = false;
		blnBtnAddGarante = false;
		blnBtnDelGarante = false;
		blnTxtMotivoPrestamo = false;
		blnTxtObservacionesPrestamo = false;
		blnBtnAddRequisito = false;
		blnLnkDescarga = false;
		estructuraDetalleBusqueda = new EstructuraDetalle(); // ***
		expCreditoBusqueda = new ExpedienteCredito();		//	***
		strNumeroSolicitudBusq = "";						//	***
		intTipoConsultaBusqueda = 0;
		strConsultaBusqueda = "";
		estadoCreditoBusqueda = new EstadoCredito();
		blnTxtBusqueda = true;
		listExpedienteMovimientoComp = new ArrayList<ExpedienteComp>();
		blnValidarGarantes = Boolean.FALSE;
		beanExpedienteCredito.setSocioComp(new SocioComp());
		beanExpedienteCredito.setListaEstructuraSocio(new ArrayList<Estructura>());
		strUltimoEstado = "";
		bdMontoADescontarRePrestamo = BigDecimal.ZERO;
		//RVILLARREAL 23.04.2014
		lstParaQuePinteReporte = new ArrayList<Tabla>();
		beanSocioComp = new SocioComp();
//		beanEstadoCredito = new EstadoCredito();

		try {
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			solicitudPrestamoFacadeNuevo = (SolicitudPrestamoFacadeNuevoLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeNuevoLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			convenioFacade = (ConvenioFacadeRemote) EJBFactory.getRemote(ConvenioFacadeRemote.class);
			planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			captacionFacade = (CaptacionFacadeRemote) EJBFactory.getRemote(CaptacionFacadeRemote.class);
			creditoGarantiaFacade = (CreditoGarantiaFacadeRemote) EJBFactory.getRemote(CreditoGarantiaFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			listaCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO)); 
			listaTipoCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CONDSOCIO));
			listaCondicionLaboral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONLABORAL));
			listaEstadoPrestamo = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO));
			listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			//Ordenamos por nombre
			Collections.sort(listaSucursal, new Comparator<Sucursal>(){
				public int compare(Sucursal sucUno, Sucursal sucDos) {
					return sucUno.getJuridica().getStrSiglas().compareTo(sucDos.getJuridica().getStrSiglas());
				}
			});
		} catch (EJBFactoryException e) {
			log.error("error: " + e.getMessage());
		} catch (NumberFormatException e) {
			log.error("error: " + e.getMessage());
			e.printStackTrace();
		} catch (BusinessException e) {
			log.error("error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			inicio(null);
		}
	}
	
	
	/**
	 * Metodo de limpieza asociado a h:outputText de nuevoCreditoBody.jsp
	 * Ayuda a controlar los tabs de acuerdo a perfiles de usuario logueado.
	 * @return
	 */
	public String  getLimpiarPrestamo(){
		cargarUsuarioPermiso();
		limpiarFormSolicitudPrestamo();
		limpiarFiltros(null);
		limpiarResultadoBusqueda();
		limpiarCronograma();
		pgValidDatos = false;
		blnDatosSocio = false;
		return "";
	}
	
	
	/**
	 * Metodo que realiza consulta para definir permisos de usuario logueado.
	 */
	public void cargarUsuarioPermiso() {
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		Integer MENU_SOLICITUDCREDITO = 60;
		Integer MENU_AUTORIZACION = 61;
		Integer MENU_GIRO = 62;
		Integer MENU_ARCHIVAMIENTO = 63;
		Integer MENU_ESPECIALES = 311;
		try {
			if (usuario != null) {
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_SOLICITUDCREDITO);
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
				
				id.setIntIdTransaccion(MENU_ESPECIALES);
				permiso = remotePermiso.getPermisoPerfilPorPk(id);
				blnCreditosEspeciales = (permiso == null) ? true : false;
				 
				setUsuario(usuario);
				intUltimoEstadoSolicitud = -1;
				blnYaTieneCredito = Boolean.FALSE;
				blnMostrarEliminar = Boolean.TRUE;
				blnMostrarModificar = Boolean.TRUE;
				blnMostrarDescripciones = Boolean.FALSE;
				strErrorGrabar="";
				blnValidarGarantes = Boolean.FALSE;
				
				cargarCombosBusqueda();
				limpiarFormSolicitudPrestamo();
				limpiarFiltros(null);
				limpiarResultadoBusqueda();
				limpiarCronograma();
			} else {
				blnSolicitud = false;
				blnAutorizacion = false;
				blnGiro = false;
				blnArchivamiento = false;
				blnCreditosEspeciales = false;
			}
		} catch (BusinessException e) {
			log.error(e);
		} catch (EJBFactoryException e) {
			log.error(e);
		}
	}
	
	/**
	 * Carga valores iniciales, permisos, etc.
	 * @param event
	 */
	public void inicio(ActionEvent event) {
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		Integer MENU_SOLICITUDCREDITO = 60;
		Integer MENU_AUTORIZACION = 61;
		Integer MENU_GIRO = 62;
		Integer MENU_ARCHIVAMIENTO = 63;
		try {
			if (usuario != null) {
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_SOLICITUDCREDITO);
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
				intUltimoEstadoSolicitud = -1;
				blnYaTieneCredito = Boolean.FALSE;
				blnMostrarEliminar = Boolean.TRUE;
				blnMostrarModificar = Boolean.TRUE;
				blnMostrarDescripciones = Boolean.FALSE;
				strErrorGrabar="";
				blnValidarGarantes = Boolean.FALSE;
				
				cargarCombosBusqueda();
				
				limpiarFormSolicitudPrestamo();
				limpiarFiltros(null);
				limpiarResultadoBusqueda();
				limpiarCronograma();
				
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
	 * Metodo que carga los combos de la grilla de busqueda
	 */
	public void cargarCombosBusqueda(){
			try{
				// combo 1
				listaTablaDeSucursal = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
				List<Sucursal> listaSucursal =  empresaFacade.getListaSucursalPorPkEmpresa(usuario.getPerfil().getId().getIntPersEmpresaPk());
				
				//Ordenamos por nombre
				Collections.sort(listaSucursal, new Comparator<Sucursal>(){
					public int compare(Sucursal sucUno, Sucursal sucDos) {
						return sucUno.getJuridica().getStrSiglas().compareTo(sucDos.getJuridica().getStrSiglas());
					}
				});
				
				Sucursal sucursal = null;
				Tabla tabla = null;
				for(int i=0;i<listaSucursal.size();i++){
					 sucursal = listaSucursal.get(i);
					 tabla = new Tabla();
					 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
					 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
					 listaTablaDeSucursal.add(tabla);
				}
				// combo 2
				//listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 1);
				listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), "B");

			} catch (BusinessException e) {
				log.error("Error en cargarCombosBusqueda ---> "+e);
				e.printStackTrace();
			} 
	}
	
	
	/**
	 * Metodo que se ejecuta al presionar el boton NUEVO.
	 * Despliega el formulario de Validacionde  Dstos
	 */
	public void habilitarGrabarSolicitud(ActionEvent event) {
		try {
			blnModoVisualizacion = false;
			setFormSolicitudPrestamoRendered(true);
			limpiarFormSolicitudPrestamo();
			strSolicitudPrestamo = Constante.MANTENIMIENTO_GRABAR;
			pgValidDatos = true;
			blnDatosSocio = false;
			blnEvaluacionCredito = false;
			strFechaRegistro = Constante.sdf.format(new Date());
			blnBtnAddCapacidad = false;
			blnBtnEditCapacidad = false;
			blnBtnDelCapacidad = false;
			blnBtnEvaluarCredito = false;
			blnTextMontoPrestamo = false;
			blnTextNroCuotas = false;
			blnCmbTipoOperacion = false;
			blnBtnAddGarante = false;
			blnBtnDelGarante = false;
			blnTxtMotivoPrestamo = false;
			blnTxtObservacionesPrestamo = false;
			blnBtnAddRequisito = false;
			blnLnkDescarga = false;	
		} catch (Exception e) {
			log.error("Error en habilitarGrabarSolicitud ---> "+e);
		}
		
	}
	
	
	/**
	 * Asocioado al boton CANCELAR. Limpia los objetos utilizados hasta el momento y 
	 * deja limpio el formulario.
	 * @param event
	 */
	public void cancelarGrabarSolicitudPrestamo(ActionEvent event) {
		
		try {
			blnModoVisualizacion = false;
			setFormSolicitudPrestamoRendered(false);
			limpiarFormSolicitudPrestamo();
			strSolicitudPrestamo = null;
			pgValidDatos = false;
			blnDatosSocio = false;
			blnEvaluacionCredito = false;
			blnBtnAddCapacidad = false;
			blnBtnEditCapacidad = false;
			blnBtnDelCapacidad = false;
			blnBtnEvaluarCredito = false;
			blnTextMontoPrestamo = false;
			blnTextNroCuotas = false;
			blnCmbTipoOperacion = false;
			blnBtnAddGarante = false;
			blnBtnDelGarante = false;
			blnTxtMotivoPrestamo = false;
			blnTxtObservacionesPrestamo = false;
			blnBtnAddRequisito = false;
			blnLnkDescarga = false;
			strMensajeGarantesObservacion = "";
		} catch (Exception e) {
			log.error("Error en cancelarGrabarSolicitudPrestamo ---> "+e);
		}
		
	}

	
	/**
	 * Limpia el Formulario de la Solicitud de Prestamo
	 */
	public void limpiarFormSolicitudPrestamo() {
		try {
			blnModoVisualizacion = false;
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
			beanCredito = new Credito();		
			beanCredito.setId(new CreditoId());
			personaBusqueda = new Persona();
			personaBusqueda.setDocumento(new Documento());
			beanExpedienteCredito = new ExpedienteCredito();
			beanExpedienteCredito.setId(new ExpedienteCreditoId());
			beanExpedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());
			beanExpedienteCredito.setListaCapacidadCreditoComp(new ArrayList<CapacidadCreditoComp>());
			beanExpedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
			listaGarantiaCreditoComp = new ArrayList<GarantiaCreditoComp>();
			listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
			listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
			bdTotalCuotaMensual = null;
			dtFechaRegistro = null;
			strFechaRegistro = null;
			bdPorcSeguroDesgravamen = null;
			bdTotalDstos = null;
			bdMontoPrestamo = null;
			bdMontoInteresAtrasado = null;
			bdAportes = new BigDecimal(0);
			intNroCuotas = null;
			strPorcInteres = null;
			strNroDocumento = null;
			msgTxtNroCuotas = null;
			msgTxtTipoOperacion = null;
			msgTxtMontoSolicitado = null;
			msgTxtListaCapacidadPago = null;
			msgTxtCreditoPk = null;
			msgTxtConvenioActivo = null;
			msgTxtEvaluacionFinal = "";
			msgTxtCreditoConvenio = null;
			msgTxtRolPersona = null;
			msgTxtEstructuraSocio = null;
			msgTxtCondicionLaboral = null;
			msgTxtInteresCredito = null;
			msgTxtExesoCuotasCronograma = null;
			msgTxtTipoCreditoEmpresa = null;
			msgTxtCondicionCredito = null;
			msgTxtCondicionHabil = null;
			msgTxtSubCondicionCuenta = null;
			msgTxtMontoExesivo = null;
			msgTxtEnvioPlla = null;
			msgTxtCuotaMensual = null;
			strMsgErrorValidarDatos = null;
			msgTxtEvaluacionFinal = "";
			msgTxtErrores = "";
			msgTxtEstructuraRepetida = null;
			msgTxtEstrucActivoRepetido = null;
			msgTxtEstrucCesanteRepetido = null;
			intUltimoEstadoSolicitud = -1;
			strUltimoEstadoSolicitud = "";
			blnYaTieneCredito = Boolean.FALSE;
			blnMostrarEliminar = Boolean.TRUE;
			blnMostrarModificar = Boolean.TRUE;
			blnMostrarDescripciones = Boolean.FALSE;
			blnTxtBusqueda = true;
			listExpedienteMovimientoComp = new ArrayList<ExpedienteComp>();
			strErrorGrabar="";
			blnValidarGarantes = Boolean.FALSE;
			beanExpedienteCredito.setSocioComp(new SocioComp());
			intTipoEvaluacion = 1;
			beanExpedienteCredito.setListaEstructuraSocio(new ArrayList<Estructura>());
			strUltimoEstado = "";
			strMsgErrorValidarMovimientos = "";
			strMensajeGarantesObservacion = "";
			
			bdMontoADescontarRePrestamo = BigDecimal.ZERO;
			listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
			bdMontoSaldoAnterior = BigDecimal.ZERO;
		} catch (Exception e) {
			log.error("Error en limpiarFormSolicitudPrestamo ---> "+e);
		}
		
	}

	
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
	public void listarSolicitudPrestamo(ActionEvent event) {
		SolicitudPrestamoFacadeLocal facade = null;
		cancelarGrabarSolicitudPrestamo(event);
		ExpedienteCreditoComp expedienteCompBusq = null;
		try {

			facade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
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

			listaExpedienteCreditoComp = facade.getListaBusqCreditoFiltros(expedienteCompBusq);

		} catch (BusinessException e) {
			log.error("Error BusinessException listarSolicitudPrestamo ---> "+e);
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException listarSolicitudPrestamo ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Limpia los filtros utilizados en la busqueda
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
	}
	
	
	/**
	 * Valida datos de Socio al registrar una Nueva Solicitud de Credito o Represtamo.
	 * @param event
	 */
	public void validarDatos(ActionEvent event) {
		SocioComp socioComp = null;
		CapacidadCreditoComp capacidadCreditoComp = null;
		Integer intTipoDoc = 0;
		String strNumIdentidad = "";
		CuentaComp cuentaComp = null;
		List<ExpedienteComp> lstExpedientesRecuperados = null;
		//List<Expediente> listaCreditosConSaldo = null;
		List<CuentaConcepto> lstCtaCto = null;
		Cuenta cuentaSocio = null;
		Boolean blnAplicaReprestamo = Boolean.FALSE;
		Boolean blnAplicaPrestamoNormal = Boolean.FALSE;
		Boolean blnTipoRelacion = false;
		try {
			log.info("numero de cuotas: "+intNroCuotas);
			cuentaComp = new CuentaComp();
			intTipoDoc = personaBusqueda.getDocumento().getIntTipoIdentidadCod();
			strNumIdentidad = personaBusqueda.getDocumento().getStrNumeroIdentidad();
			strNumIdentidad = strNumIdentidad.trim();
			
			limpiarListaCapacidades();
			
			strMsgErrorValidarMovimientos = "";
			if ((intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_SOCIO)&& intTipoCredito.equals(Constante.PARAM_T_TIPOSCREDITOPANTALLA_DECONSUMO))
				|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_USUARIO)&& intTipoCredito.equals(Constante.PARAM_T_TIPOSCREDITOPANTALLA_DECONSUMO)) {

				socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa( intTipoDoc, strNumIdentidad,Constante.PARAM_EMPRESASESION);
				
				if (socioComp != null && socioComp.getPersona()!= null) {					
					
				// 1. Validamos el estado de la persona (fallecido , activo)
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
									strMsgErrorValidarDatos = "";
									if (socioComp.getCuenta() != null) {
										
									// 2. Validamos la situacion de la cuenta - vigente
										if(socioComp.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)==0){
											
											//cargamos la lista de sociestructura
											if (socioComp.getSocio().getListSocioEstructura() != null && !socioComp.getSocio().getListSocioEstructura().isEmpty()) {
												for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
													if(socioEstructura.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
														capacidadCreditoComp = new CapacidadCreditoComp();
														capacidadCreditoComp.setSocioEstructura(socioEstructura);
														listaCapacidadCreditoComp.add(capacidadCreditoComp);
													}
												}
											}
											
											//Setemos el socioestructura.
											for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
												if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
													if (socioEstructura.getIntTipoEstructura().intValue() == Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.intValue()) {
														socioComp.getSocio().setSocioEstructura(socioEstructura);
														break;
													}
												}
											}
											
											// CGD - 04.11.2013
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
			
											cuentaSocio = cuentaFacade.getCuentaActualPorIdPersona(socioComp.getPersona().getIntIdPersona(), Constante.PARAM_EMPRESASESION);
			
											if(cuentaSocio != null ){
												cuentaComp.setCuenta(cuentaSocio);
												socioComp.setCuenta(cuentaSocio);
											}
											socioComp.setCuentaComp(cuentaComp);								
											beanSocioComp = socioComp;
											calcularTotalesConceptos(socioComp);
			
											Boolean blnTieneExpReq = false;
											Boolean blnTieneExpSol = false;
											Boolean blnTieneExpApro = false;
											//jchavez 26.06.2014
											Boolean blnTieneExpObs = false;
											// Validamos si poseee alguna solicitud en estado Requisito y/o Solicitud
											blnTieneExpReq = validarExistenciaPrestamosEstado_Req_Sol(beanSocioComp.getCuentaComp().getCuenta(), 
																										Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
											blnTieneExpSol = validarExistenciaPrestamosEstado_Req_Sol(beanSocioComp.getCuentaComp().getCuenta(), 
																										Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
											
											blnTieneExpApro = validarExistenciaPrestamosEstado_Req_Sol(beanSocioComp.getCuentaComp().getCuenta(), 
																										Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
											
											blnTieneExpObs = validarExistenciaPrestamosEstado_Req_Sol(beanSocioComp.getCuentaComp().getCuenta(), 
																										Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);
											
											blnAplicaPrestamoNormal = validaPrestamoNormal();
											
											// Sino tiene procede
											if(blnTieneExpReq == false && blnTieneExpSol == false && blnTieneExpApro == false && blnTieneExpObs == false){
											
												// Recuperamos los expedientes de movimineto para visualizarlos en la grilla de saldo de cuentas
												// Sino existen es porque el socio no posee deudas.
												lstExpedientesRecuperados = recuperarCreditosMovimiento();
												if(lstExpedientesRecuperados != null && !lstExpedientesRecuperados.isEmpty()){
													// Se valñidan los prestamos rcueprados desde movimiento.
													listaExpedientesParaReprestamo = validarExpedientesParaRePrestamo(lstExpedientesRecuperados);
													bdMontoSaldoAnterior = BigDecimal.ZERO;
													// 
													if(listaExpedientesParaReprestamo != null && !listaExpedientesParaReprestamo.isEmpty()){
														blnAplicaReprestamo = Boolean.TRUE;
														
														for (ExpedienteComp expComp : listaExpedientesParaReprestamo) {
															bdMontoSaldoAnterior = bdMontoSaldoAnterior.add(expComp.getBdMontoReprestamo());
														}
														
														strMsgErrorValidarDatos= "";
														beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(listaExpedientesParaReprestamo);
														CuentaComp miCuentaComp = beanSocioComp.getCuentaComp();
			
														List<CuentaComp> listaTemp = new ArrayList<CuentaComp>();
														listaTemp.add(miCuentaComp);
														socioComp.setListaCuentaComp(listaTemp);
														beanSocioComp.setListaCuentaComp(listaTemp);
														Integer intTam = listaExpedientesParaReprestamo.size();
														intTam++;
														beanSocioComp.getCuentaComp().setIntTamannoListaExp(intTam);
														beanExpedienteCredito.setIntParaSubTipoOperacionCod(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO);
			
													}else{
														blnAplicaReprestamo = Boolean.FALSE;
														if(!blnAplicaPrestamoNormal){
															strMsgErrorValidarDatos =  "El socio ingresado no es apto para un Crédito Nuevo o RePrestamo.";
															pgValidDatos = true;
															blnDatosSocio = false;
														}
													}
												}else{
													// Sino posee expedientes en movimeinto, se le otorga prestamo nuevo.
													beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(listaExpedientesParaReprestamo);
													beanSocioComp.getCuentaComp().setIntTamannoListaExp(1);
													beanExpedienteCredito.setIntParaSubTipoOperacionCod(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO);
												}
												
												Boolean blnExisteConfiguracion = false;
												blnExisteConfiguracion = validacionPreviaCreditos(socioComp);
			
												if(blnAplicaReprestamo == Boolean.TRUE || blnAplicaPrestamoNormal == Boolean.TRUE){
													if(blnExisteConfiguracion){
														pgValidDatos = false;
														blnDatosSocio = true;
													}else{
														pgValidDatos = true;
														blnDatosSocio = false;
														strMsgErrorValidarDatos = " * No se encontró Configuración de Crédito para el Socio: " +
														cargarDesripcionValidacionesGarantes(1,beanSocioComp.getCuenta().getIntParaCondicionCuentaCod())+ " - "+
														cargarDesripcionValidacionesGarantes(2,beanSocioComp.getCuenta().getIntParaSubCondicionCuentaCod())+" - "+
														cargarDesripcionValidacionesGarantes(3,beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral())+".";
													}
												}else{
													strMsgErrorValidarDatos =  "El socio ingresado no es apto para un Crédito Nuevo o RePrestamo.";
													pgValidDatos = true;
													blnDatosSocio = false;
													
												}
											}else{
												pgValidDatos = true;
												blnDatosSocio = false;
												strMsgErrorValidarDatos = "El Socio tiene Solicitudes en estado Requisito, Solicitud, Aprobado u Observado.";
											}
										}else{
											strMsgErrorValidarDatos = "El Cuenta del Socio no se encuentra en estado Vigente.";	
										}	
			
									}else{
										strMsgErrorValidarDatos = "El Socio no posee Cuenta registrada.";
									}
								}else{
									strMsgErrorValidarDatos = "La Relación NO SOCIO no cuenta con configuración de crédito.";
								}								
							}else{
								strMsgErrorValidarDatos = "La Relación no concuerda con la configuración de la cuenta del socio.";
							}
						}else{
							strMsgErrorValidarDatos = "El socio no cuenta con ningun rol asignado.";
						}
					}else {
						strMsgErrorValidarDatos = "El Socio se encuentra en estado Fallecido.";
					}
				}else{
					strMsgErrorValidarDatos = "No se encontró Socio.";
				}
			} else {
				pgValidDatos = true;
				blnDatosSocio = false;
			}
			log.info("numero de cuotas: "+intNroCuotas);
		} catch (BusinessException e) {
			log.error("Error BusinessException validarDatos ---> "+e);
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
	 * Calcula el montoa  descontar sobre el saldo del credito segun existencia de envio y efectuado.
	 * Ademas se calcula el interes atrasado x expediente.
	 * Ademas define si aplica o no el represtamo.
	 * Si devuelve TRUE aplica, caso contrario no.
	 * @return
	 */
	public List<ExpedienteComp> validarExpedientesParaRePrestamo(List<ExpedienteComp> lstExpedientesMovimientosRecuperados){
		Integer intEmpresa = 0;
		Integer intNroCta = 0;
		List<Envioconcepto> lstEnvioConcepto = null;
		Envioconcepto envioConcepto = null;
		List<Efectuado> lstEfectuado = null;
		bdMontoADescontarRePrestamo = BigDecimal.ZERO;
		BigDecimal bdMontoTotalPrestamo = BigDecimal.ZERO;
		BigDecimal bdMontoPagadoCaja = BigDecimal.ZERO;
		BigDecimal bdMontoPagadoPlanilla = BigDecimal.ZERO;
		BigDecimal bdPorcentajeDefinido = new BigDecimal(30);
		Boolean blnCumpleCaja = Boolean.FALSE;
		Boolean blnCumplePlanilla = Boolean.FALSE;
		List<ExpedienteComp> listaValidada = null;
		try {
			intEmpresa = beanSocioComp.getCuentaComp().getCuenta().getId().getIntPersEmpresaPk();
			intNroCta  = beanSocioComp.getCuentaComp().getCuenta().getId().getIntCuenta();
			
			if(lstExpedientesMovimientosRecuperados != null && !lstExpedientesMovimientosRecuperados.isEmpty()){
				
				//1. Recuperamos el ultimo ENVIADO
				lstEnvioConcepto = planillaFacade.getListaEnvioconceptoPorEmprNroCta(intEmpresa, intNroCta);
				if(lstEnvioConcepto != null && !lstEnvioConcepto.isEmpty()){
					envioConcepto = new Envioconcepto();
					envioConcepto = lstEnvioConcepto.get(0);

					//2. En base al envio se valida si existe EFECTUADO para ese periodo
					lstEfectuado = planillaFacade.getListaPorEmpCtaPeriodo(envioConcepto.getId().getIntEmpresacuentaPk(), 
													envioConcepto.getIntCuentaPk(), 
													envioConcepto.getIntPeriodoplanilla());
					
					// CASO 0: SI EXISTE EFECTUADO
//					if(lstEfectuado != null && !lstEfectuado.isEmpty()){
//						bdMontoADescontarRePrestamo = BigDecimal.ZERO;
//					}
//					// CASO 1: NO EXISTE EFECTUADO
//					else{
						listaValidada = new ArrayList<ExpedienteComp>();
						
						for (ExpedienteComp expedienteComp : lstExpedientesMovimientosRecuperados){							
							// bdMontoADescontarRePrestamo	
							bdMontoTotalPrestamo = expedienteComp.getExpediente().getBdMontoTotal();
							List<Envioconcepto> lstEnviosXExp = null;
							lstEnviosXExp = planillaFacade.getListaEnvioconceptoPorPkExpedienteCredito(expedienteComp.getExpediente().getId());
							if(lstEnviosXExp != null && !lstEnviosXExp.isEmpty()){
								List<Movimiento> lstMovimientos = null;
								// validamos el 30% sea por planilla o caja
								lstMovimientos = conceptoFacade.getListXCtaExpediente(expedienteComp.getExpediente().getId().getIntPersEmpresaPk(),
															expedienteComp.getExpediente().getId().getIntCuentaPk(), 
															expedienteComp.getExpediente().getId().getIntItemExpediente(), 
															expedienteComp.getExpediente().getId().getIntItemExpedienteDetalle());
								
								if(lstMovimientos != null && !lstMovimientos.isEmpty()){
									for (Movimiento movimiento : lstMovimientos) {
										if(movimiento.getIntParaTipoCargoAbono().compareTo(Constante.PARAM_T_CARGOABONO_ABONO)==0){
											if(movimiento.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_INGRESO_POR_CAJA)==0){
												bdMontoPagadoCaja = bdMontoPagadoCaja.add(movimiento.getBdMontoMovimiento());			
											}
											if(movimiento.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_POR_PLANILLA)==0){
												bdMontoPagadoPlanilla = bdMontoPagadoPlanilla.add(movimiento.getBdMontoMovimiento());
											}
										}
									}
									
									// validando que se haya pagado el 30% del prestamo.
									//bdMontoTotalPrestamo
									BigDecimal bdCalc1 = BigDecimal.ZERO;
									BigDecimal bdCalc2 = BigDecimal.ZERO;
									bdCalc1 = bdMontoPagadoCaja.multiply(new BigDecimal(100));
									if(bdCalc1.compareTo(BigDecimal.ZERO)>0){
										bdCalc2 = bdCalc1.divide(bdMontoTotalPrestamo, 2,RoundingMode.HALF_UP);
									}
									
									
									BigDecimal bdCalc3 = BigDecimal.ZERO;
									BigDecimal bdCalc4 = BigDecimal.ZERO;
									bdCalc3 = bdMontoPagadoPlanilla.multiply(new BigDecimal(100));
									if(bdCalc3.compareTo(BigDecimal.ZERO)>0){
										bdCalc4 = bdCalc3.divide(bdMontoTotalPrestamo, 2,RoundingMode.HALF_UP);
									}

									if(bdCalc2.compareTo(new BigDecimal(100))>=0){
										blnCumpleCaja = Boolean.TRUE;								
									}
									if(bdCalc4.compareTo(bdPorcentajeDefinido)>=0){
										blnCumplePlanilla = Boolean.TRUE;
									}
		
									// del monto pagado el 30% debe ser x planilla o el 100% x caja
									if(blnCumplePlanilla || blnCumpleCaja){
										expedienteComp.setChecked(true);
										listaValidada.add(expedienteComp);
										BigDecimal bdMontoPorDescontar = BigDecimal.ZERO;
										for (Envioconcepto envioExp : lstEnviosXExp) {
											if(envioExp.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0
												&& envioExp.getIntPeriodoplanilla().compareTo(envioConcepto.getIntPeriodoplanilla())==0
												&& envioExp.getIntTipoconceptogeneralCod().compareTo(Constante.PARAM_T_CONCEPTOGENERAL_AMORTIZACION_EXPEDIENTE)==0){
												bdMontoADescontarRePrestamo = envioExp.getBdMontoconcepto();
												bdMontoPorDescontar= bdMontoPorDescontar.add(envioExp.getBdMontoconcepto());
											}
										}
										if(lstEfectuado != null && !lstEfectuado.isEmpty()){
											bdMontoADescontarRePrestamo = BigDecimal.ZERO;
											bdMontoPorDescontar = BigDecimal.ZERO;
										}
										BigDecimal bdMontoPrestamo = BigDecimal.ZERO;
										BigDecimal bdMontoPrestamoDescontado = BigDecimal.ZERO;
										BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
										BigDecimal bdMontoTotalReprestamo = BigDecimal.ZERO;
										
										bdMontoPrestamo = expedienteComp.getBdSaldo();
										bdMontoPrestamoDescontado = bdMontoPrestamo.subtract(bdMontoPorDescontar);
										
										ExpedienteCreditoId expCredId = new ExpedienteCreditoId();
										expCredId.setIntCuentaPk(expedienteComp.getExpediente().getId().getIntCuentaPk());
										expCredId.setIntItemDetExpediente(expedienteComp.getExpediente().getId().getIntItemExpedienteDetalle());
										expCredId.setIntItemExpediente(expedienteComp.getExpediente().getId().getIntItemExpediente());
										expCredId.setIntPersEmpresaPk(expedienteComp.getExpediente().getId().getIntPersEmpresaPk());
										
										bdMontoInteresAtrasado = calcularInteresAtrasado(expCredId);
										if(bdMontoInteresAtrasado.compareTo(BigDecimal.ZERO)!= 0){
											beanExpedienteCredito.setBdMontoInteresAtrasado(bdMontoInteresAtrasado);
										}
										bdMontoTotalReprestamo = bdMontoPrestamoDescontado.add(bdMontoInteresAtrasado);
										expedienteComp.setBdMontoReprestamo(bdMontoTotalReprestamo);
										
										expedienteComp.setBdMontoSaldoReprestamo(bdMontoPrestamoDescontado);
										//agregado para su grabacion en monto cancelado
										
										//jchavez 21.06.2014 Se ingresa el monto sin el  interes atrasado para que cuadre el asiento
//										expedienteComp.setBdMontoReprestamo(bdMontoPrestamoDescontado);//bdMontoTotalReprestamo);
										
									}
								}
							}
						}		
					}	
				}
//			}	

		} catch (Exception e) {
			log.error("Error en validarSiAplicaRePrestamo ---> "+e);	
		}
		return listaValidada;

	}
	
	
	/**
	 * Carga la variable global: listaCuentaConcepto.
	 * @param socioComp
	 */
	public List<CuentaConcepto> recuperarCuentasConceptoSocio (SocioComp socioComp){
		List<CuentaConcepto> listaCtaCto = null;
		try {
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(socioComp.getCuenta().getId());
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
	 * Validacion previa de creditos. Revisa que para el socio, exista al menos un credito para visualizar el formulario de soliciutd.
	 * @return
	 */
	public boolean validacionPreviaCreditos(SocioComp socioComp){
		EstructuraDetalle estructuraDetalle = null;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetalle = null;
		Boolean blnContinuaValidacion = Boolean.FALSE;
		Boolean blnContinua;
		Date today = new Date();
		String strToday = "";

		Credito credito = null;
		try {
			strToday = Constante.sdf.format(today);
			Date dtToday = Constante.sdf.parse(strToday);

			for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
				if(socioEstructura.getIntEstadoCod().intValue() == Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO.intValue()){
					if (socioEstructura.getIntTipoEstructura().intValue() == Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN.intValue()) {
						socioComp.getSocio().setSocioEstructura(socioEstructura);
						break;
					}
				}
			}

			if (socioComp.getSocio().getSocioEstructura() != null) {
				estructuraDetalle = new EstructuraDetalle();	
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(socioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(socioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
				socioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
				socioComp.getSocio().getSocioEstructura().getIntModalidad());

				listaConvenioEstructuraDetalle = estructuraFacade.getListaConvenioEstructuraDetallePorEstructuraDetCompleto(estructuraDetalle.getId());

				if(listaConvenioEstructuraDetalle != null && !listaConvenioEstructuraDetalle.isEmpty()){

					for (ConvenioEstructuraDetalleComp convEstrDetComp : listaConvenioEstructuraDetalle) {
						Adenda adenda = new Adenda();
						adenda.setId(new AdendaId());
						adenda.getId().setIntConvenio(convEstrDetComp.getConvenioEstructuraDetalle().getId().getIntConvenio());
						adenda.getId().setIntItemConvenio(convEstrDetComp.getConvenioEstructuraDetalle().getId().getIntItemConvenio());
						adenda = convenioFacade.getConvenioPorIdConvenio(adenda.getId());

						blnContinua = true;
						// Validamos que la adenda este habil
						if(adenda.getDtCese() == null){
							blnContinua = true;
						}else{
							if(dtToday.before(adenda.getDtCese())){
								blnContinua = true;
							}else{	
								blnContinua = false;
							}
						}

						if(blnContinua){
							if(adenda.getIntParaEstadoHojaPlan() == null){
								blnContinua = true;
							}else{
								if(adenda.getIntParaEstadoHojaPlan().compareTo(Constante.PARAM_T_ESTADODOCUMENTO_CONCLUIDO)==0){
									blnContinua = true;
								}else{	
									blnContinua = false;
								}
							}

							if(blnContinua){
								if(adenda.getIntParaEstadoValidacion() == null){
									blnContinua = true;
								}else{
									if(adenda.getIntParaEstadoValidacion().compareTo(Constante.PARAM_T_ESTADODOCUMENTO_APROBADO)==0){
										blnContinua = true;
									}else{	
										blnContinua = false;
									}
								}
							}
						}

						if (((dtToday.after(adenda.getDtInicio()))
						&& adenda.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0
						&& adenda.getIntParaEstadoConvenioCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0)){
							if(blnContinua){
								if ((adenda.getListaAdendaCreditos() != null)&&(!adenda.getListaAdendaCreditos().isEmpty())) {
									for (int j = 0; j < adenda.getListaAdendaCreditos().size(); j++) {
										// Validamos que la configuracion del credito en la adenda este activo
										if(adenda.getListaAdendaCreditos().get(j).getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0){
											credito = new Credito();
											credito.setId(new CreditoId());
											credito.getId().setIntPersEmpresaPk(adenda.getListaAdendaCreditos().get(j).getId().getIntPersEmpresaPk());
											credito.getId().setIntParaTipoCreditoCod(adenda.getListaAdendaCreditos().get(j).getId().getIntParaTipoCreditoCod());
											credito.getId().setIntItemCredito(adenda.getListaAdendaCreditos().get(j).getId().getIntItemCredito());
											credito = creditoFacade.getCreditoPorIdCredito(credito.getId());
											blnYaTieneCredito = aplicaPromocional(beanSocioComp, credito);
											
											log.error("DESCRIPCION DE CREDITO ************* "+credito.getStrDescripcion());
											log.error("APLICA CREDITO ************* "+blnYaTieneCredito);
											// Si teiene creditosd anterioes no debe caer en promocionales
											if(blnYaTieneCredito){

													credito = null;
											}else{
												Boolean blnNoAplica = Boolean.FALSE;
												blnNoAplica = validarExcepcionDiasPromocional(credito, beanSocioComp);
												if(blnNoAplica){
													credito = null;
												}
											}
											// Validamos cada credito
											if (credito != null){
												if(credito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) ==  0) {
													if (credito.getIntParaRolPk().compareTo(intTipoRelacion)==0) {
														if (beanSocioComp.getSocio().getSocioEstructura() != null) {
															boolean boFlag = false;

															for (int k = 0; k < beanSocioComp.getPersona().getNatural().getListaPerLaboral().size(); k++) {
																if (credito.getIntParaCondicionLaboralCod() == 
																(beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(k).getIntCondicionLaboral())) {
																	boFlag = true;
																}
																if (credito.getIntParaCondicionLaboralCod().equals(
																(beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(k).getIntCondicionLaboral()))) {
																	boFlag = true;
																}
															}

															if ((credito.getIntParaCondicionLaboralCod().compareTo(-1)==0)|| (boFlag)) {
																// Verificar la condición de la cuenta
																if (credito.getListaCondicion() != null && !credito.getListaCondicion().isEmpty()) {
																	for (CondicionCredito condicionCredito : credito.getListaCondicion()) {
																		//if(!blnContinuaValidacion){
																		if (beanSocioComp.getCuenta().getIntParaCondicionCuentaCod().equals(condicionCredito.getId().getIntParaCondicionSocioCod())
																			&& condicionCredito.getIntValor() == 1) {
																			// Verificar si la cuenta está activa o inactiva
																			if (credito.getListaCondicionHabil() != null && !credito.getListaCondicionHabil().isEmpty()) {
																				// forCondicionHabil:
																				for (CondicionHabil condicionHabil : credito.getListaCondicionHabil()) {
																					// verificando condicion de regular
																					//if(!blnContinuaValidacion){
																					if (beanSocioComp.getCuenta().getIntParaSubCondicionCuentaCod().equals(condicionHabil.getId().getIntParaTipoHabilCod())
																						&& condicionHabil.getIntValor() == 1) {
																						blnContinuaValidacion = Boolean.TRUE;
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
		} catch (Exception e) {
			log.error("Error en validacionPreviaCreditos ---> "+e);
		}
		return blnContinuaValidacion;
	}	
	
	
	/**
	 * Valida la existencia de creditos previos a fin de determinar si el credito evaluado se debe considerar.


	 * TRUE - si credito no aplica.
	 * FALSE - si credito aplica.
	 * @return
	 */
	public boolean aplicaPromocional(SocioComp socioComp, Credito credito){
		
		Boolean blnTiene = Boolean.FALSE;
		Boolean blnAplica = Boolean.FALSE;
		List<Expediente>listaExpedientesMovimiento= null;
		
		try {
			//Valida la existencia de creditos previos a fin de definir si se le otorga PORMOCIONAL o no.
			listaExpedientesMovimiento = conceptoFacade.getListaExpedientePorEmpresaYCta(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk(), beanSocioComp.getCuenta().getId().getIntCuenta());
			if(listaExpedientesMovimiento != null && !listaExpedientesMovimiento.isEmpty()){
				
				forExpedientesEstados:
				for (Expediente expediente : listaExpedientesMovimiento) {
					List<EstadoCredito> lstEstados = null;
					ExpedienteCreditoId pId = new ExpedienteCreditoId();
					pId.setIntCuentaPk(expediente.getId().getIntCuentaPk());
					pId.setIntItemDetExpediente(expediente.getId().getIntItemExpedienteDetalle());
					pId.setIntItemExpediente(expediente.getId().getIntItemExpediente());
					pId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
					
					lstEstados = solicitudPrestamoFacade.getListaEstadosPorExpedienteCreditoId(pId);
					
					if(lstEstados != null && !lstEstados.isEmpty()){
						for (EstadoCredito estadoCredito : lstEstados) {
							if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0){

									blnTiene = Boolean.TRUE;
									break forExpedientesEstados;
							}
						}
					}
				}
			}
			
			// Si existen expediente y el credito evaluado es promocional --- no aplica
			if(blnTiene){
				if(credito.getIntParaTipoCreditoEmpresa().compareTo(Constante.PARAM_T_TIPOCREDITOEMPRESA_PROMOCIONAL)==0
						|| credito.getIntParaTipoCreditoEmpresa().compareTo(Constante.PARAM_T_TIPOCREDITOEMPRESA_ADMINISTRATIVO)==0){
					blnAplica = Boolean.TRUE;
				}else{
					blnAplica = Boolean.FALSE;
				}
			}else{
				if(credito.getIntParaTipoCreditoEmpresa().compareTo(Constante.PARAM_T_TIPOCREDITOEMPRESA_PROMOCIONAL)==0
						|| credito.getIntParaTipoCreditoEmpresa().compareTo(Constante.PARAM_T_TIPOCREDITOEMPRESA_ADMINISTRATIVO)==0){
					blnAplica = Boolean.FALSE;
				}else{
					blnAplica = Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Error en validarSiYaTienePrestamosPrevios ---> "+e);
		}
		return blnAplica;
	}
	
	/**
	 * Valida en base a 
	 * Ademas se valida contra las excepciones.
	 * TRUE - Si credito evaluado no aplica.
	 * FALSE - Si credito evaluado aplica.
	 * Sabemos que el credito validado es promocional
	 * @return
	 */
	public boolean validarExcepcionDiasPromocional(Credito credito, SocioComp socioComp){
		Boolean blnAplica = Boolean.FALSE;
		//List<Expediente>listaExpedientesMovimiento= null;
		try {			
			// Se valida la excepcion de tiempo del primer/segundo envio
			if(credito.getListaExcepcion() != null && !credito.getListaExcepcion().isEmpty()){

				List<Envioconcepto> lstEnvioConcepto = null;
				Envioconcepto primerEnvio = null;
				List<Enviomonto> lstEnvioMonto = null;
				Enviomonto envioMonto = null;

				for (CreditoExcepcion excepcion : credito.getListaExcepcion()) {
					if(excepcion.getIntPrimerCredito() != null 
					   && excepcion.getIntPrimerCreditoEnvioPlla() != null){
						if(excepcion.getIntPrimerCreditoEnvioPlla().compareTo(Constante.PARAM_PRIMER_ENVIO_PLANILLA)==0){
							Integer intEmpresa = socioComp.getCuenta().getId().getIntPersEmpresaPk();
							Integer intCuenta = socioComp.getCuenta().getId().getIntCuenta();
							
							lstEnvioConcepto = planillaFacade.getListaEnvioMinimoPorEmpCtaYEstado(intEmpresa, intCuenta);
							if(lstEnvioConcepto != null && !lstEnvioConcepto.isEmpty()){
								primerEnvio = new Envioconcepto();
								primerEnvio = lstEnvioConcepto.get(0);
								
								// formamos el EnvioMOnto de busqeda...
								Enviomonto envioMontoBusq = new Enviomonto();
								envioMontoBusq.setId(new EnviomontoId());

								envioMontoBusq.getId().setIntEmpresacuentaPk(primerEnvio.getId().getIntEmpresacuentaPk());
								envioMontoBusq.getId().setIntItemenvioconcepto(primerEnvio.getId().getIntItemenvioconcepto());
								envioMontoBusq.setIntTiposocioCod(socioComp.getSocio().getSocioEstructura().getIntTipoSocio());
								envioMontoBusq.setIntModalidadCod(socioComp.getSocio().getSocioEstructura().getIntModalidad());
								envioMontoBusq.setIntNivel(socioComp.getSocio().getSocioEstructura().getIntNivel());
								envioMontoBusq.setIntCodigo(socioComp.getSocio().getSocioEstructura().getIntCodigo());
								envioMontoBusq.setIntTipoestructuraCod(socioComp.getSocio().getSocioEstructura().getIntTipoEstructura());
								
								lstEnvioMonto = planillaFacade.getListaXItemEnvioCtoGral(envioMontoBusq);
								if(lstEnvioMonto != null && !lstEnvioMonto.isEmpty()){
									envioMonto = new Enviomonto();
									envioMonto= lstEnvioMonto.get(0);
									envioMonto.getTsFecharegistro();
									Date fechaEnvioMonto =  convertirTimestampToDate(envioMonto.getTsFecharegistro());
									Calendar fecHoy = Calendar.getInstance();
									Integer intDiasDiferencia = obtenerDiasEntreFechas(fechaEnvioMonto, fecHoy.getTime());
									if( intDiasDiferencia!= null){
										if(intDiasDiferencia.compareTo(excepcion.getIntPrimerCredito())>0){
											blnAplica = Boolean.TRUE;

											break;
										}
									}
								}
							}
						}else{
							// pendiente desarrollo, encomntarr segundo periodo de envio
						}
					}
				}
			}else{
				blnAplica = Boolean.FALSE;
			}
		
		} catch (Exception e) {
			log.error("Error en validarSiYaTienePrestamosPrevios ---> "+e);
		}
		
		return blnAplica;
		
	}

	
	/**
	 * Recupera todos los creditos (prestamos o refinanciamiento)que aun no se terminan de cancelar (Movimiento-estado vigente) del Socio validado.
	 * Para mostrarse en la grilla de Saldod e Cuentas.
	 * @see beanSocioComp.CuentaComp.ListExpedienteMovimientoComp();
	 * @return listExpedienteMovimientoComp
	 */
	public List<ExpedienteComp> recuperarCreditosMovimiento(){
		List<Expediente> listaCreditos = null;
		Integer intCuenta = beanSocioComp.getCuenta().getId().getIntCuenta();
		Integer intEmpresa = beanSocioComp.getCuenta().getId().getIntPersEmpresaPk();
		List<EstadoExpediente> listaEstadosExp= null;
		
		try {
			listaCreditos =  conceptoFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa,intCuenta);
			
			if(listaCreditos != null && !listaCreditos.isEmpty()){
				
				listExpedienteMovimientoComp = new ArrayList<ExpedienteComp>();
				
				for (Expediente expediente : listaCreditos) {
					// solo prestamos y refinanciamientos
					if(expediente.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)==0
						|| expediente.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)==0 ){
						String strDescripcionExpedienteCredito;
						ExpedienteComp expedienteComp = new ExpedienteComp();
	
						//Recuoperamos la descripciond el crdsiro
						CreditoId creditoId = new CreditoId();
						creditoId.setIntItemCredito(expediente.getIntItemCredito());
						creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
						creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
						
						Credito creditoRec = null;
						strDescripcionExpedienteCredito = "Desconocido";
						creditoRec = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
						if(creditoRec != null){
							List<Tabla> listaDescripcionExpedienteXredito= null;	
							listaDescripcionExpedienteXredito = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 
																													creditoRec.getId().getIntParaTipoCreditoCod().toString());
							if(!listaDescripcionExpedienteXredito.isEmpty()){
								for (Tabla tabla : listaDescripcionExpedienteXredito) {
									if(tabla.getIntIdDetalle().compareTo(creditoRec.getIntParaTipoCreditoEmpresa())==0){
										strDescripcionExpedienteCredito = tabla.getStrDescripcion();
										break;
									}
								}
							}
						}
						expedienteComp.setStrDescripcionTipoCreditoEmpresa(strDescripcionExpedienteCredito);
						expedienteComp.setStrDescripcionTipoCredito(strDescripcionExpedienteCredito);
						
						listaEstadosExp = conceptoFacade.getListaPorPkExpedienteCredito(expediente.getId());
						if(listaEstadosExp != null && !listaEstadosExp.isEmpty()){
							expediente.setListaEstadosExpediente(listaEstadosExp);
						}
						EstadoExpediente ultimoEstado = null;
						ultimoEstado = conceptoFacade.getMaxEstadoExpPorPkExpediente(expediente.getId());
						if(ultimoEstado != null){
							expedienteComp.setUltimoEstadoExpediente(ultimoEstado);
							if(ultimoEstado.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){	
								expedienteComp.setExpediente(expediente);
								listExpedienteMovimientoComp.add(expedienteComp);
							}
						}
					}
				}
				
				if(listExpedienteMovimientoComp != null && !listExpedienteMovimientoComp.isEmpty()){
					listExpedienteMovimientoComp = calcularCuotasPagadasVencidas(listExpedienteMovimientoComp);
				}
			}

		} catch (BusinessException e) {
			log.error("Error en recuperarCreditos --> "+e);
		}
		return listExpedienteMovimientoComp;
	}
	
	
	
	/**
	 * Recupera todos los creditos (prestamos o refinanciamiento)que aun no se terminan de cancelar (Movimiento-estado vigente) del Socio validado.
	 * Para mostrarse en la grilla de Saldod e Cuentas.
	 * @see beanSocioComp.CuentaComp.ListExpedienteMovimientoComp();
	 * @return listExpedienteMovimientoComp
	 */
	public Boolean validaPrestamoNormal(){
		List<Expediente> listaCreditos = null;
		Integer intCuenta = beanSocioComp.getCuenta().getId().getIntCuenta();
		Integer intEmpresa = beanSocioComp.getCuenta().getId().getIntPersEmpresaPk();
		Boolean blnAplica = Boolean.TRUE;
		try {
			// Creditos movimientos con saldo
			listaCreditos =  conceptoFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa,intCuenta);
			
			if(listaCreditos != null && !listaCreditos.isEmpty()){				
				for (Expediente expediente : listaCreditos) {
					// solo prestamos y refinanciamientos
					if(expediente.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)==0
						|| expediente.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)==0 ){

						EstadoExpediente ultimoEstado = null;
						ultimoEstado = conceptoFacade.getMaxEstadoExpPorPkExpediente(expediente.getId());
						if(ultimoEstado != null){
							if(ultimoEstado.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){	
								blnAplica = Boolean.FALSE;
							}
						}else{
							blnAplica = Boolean.FALSE;
						}
					}
				}
			}

		} catch (BusinessException e) {
			log.error("Error en validaPrestamoNormal --> "+e);
		}

		return blnAplica;
	}
	
	/**
	 * Calcula el nro de cuotas venciadas y pagadas en base al cronogrtma de cada expediente mov
	 * @param listExpedienteMovimientoComp
	 * @return
	 */
	public List<ExpedienteComp> calcularCuotasPagadasVencidas(List<ExpedienteComp>listExpedienteMovimientoComp){
		ExpedienteComp expedienteComp= null;
		List<Cronograma> lstCronograma = null;
		Integer intCuotasPagadas =0;
//		Integer intCuotasDefinidas = 0;
		Integer intCuotasAtrasadas =  0;
		//Integer intOperacion =  0;
		BigDecimal bdMontoCuotaFija = BigDecimal.ZERO;
		BigDecimal bdMontoSaldo =   BigDecimal.ZERO;
		List<ExpedienteComp> lstExpedienteResult = null;
		List<EstadoExpediente> lstEstadoExpediente = null;

		try {
			lstExpedienteResult = new ArrayList<ExpedienteComp>();
			
			for (int k = 0; k < listExpedienteMovimientoComp.size(); k++) {
				expedienteComp = listExpedienteMovimientoComp.get(k);
				lstCronograma = conceptoFacade.getListaCronogramaPorPkExpediente(expedienteComp.getExpediente().getId());
				intCuotasPagadas = new Integer(0);
				intCuotasAtrasadas = new Integer(0);
//				intCuotasDefinidas= new Integer(0);
				
				//Ordenamos los subtipos por int
				Collections.sort(lstCronograma, new Comparator<Cronograma>(){
					public int compare(Cronograma uno, Cronograma otro) {
						return uno.getId().getIntItemCronograma().compareTo(otro.getId().getIntItemCronograma());
					}
				});

				if(lstCronograma != null && !lstCronograma.isEmpty()){
					for(Cronograma cronograma: lstCronograma){

						// 1. calculando cuotas Pagadas
						if(cronograma.getBdSaldoDetalleCredito() != null){
							if((cronograma.getIntParaTipoConceptoCreditoCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION)==0)
								&&cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)==0){
								intCuotasPagadas ++;
							}
						}
						
						// 2. calculando atrasadas
						if(cronograma.getBdSaldoDetalleCredito() != null){
//							EstadoExpediente primerEstado = new EstadoExpediente();
							lstEstadoExpediente = new ArrayList<EstadoExpediente>();
							lstEstadoExpediente = expedienteComp.getExpediente().getListaEstadosExpediente();
							
							//Ordenamos los subtipos por int
							Collections.sort(lstEstadoExpediente, new Comparator<EstadoExpediente>(){
								public int compare(EstadoExpediente uno, EstadoExpediente otro) {
									return uno.getId().getIntItemEstado().compareTo(otro.getId().getIntItemEstado());
								}
							});
							
//							primerEstado = lstEstadoExpediente.get(0);
							expedienteComp.getExpediente().getListaEstadosExpediente();
							
							 if((cronograma.getIntParaTipoConceptoCreditoCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION)==0)
								&&(cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)!=0)
								&& (cronograma.getTsFechaVencimiento().before(new Timestamp(new Date().getTime())))){
								 intCuotasAtrasadas ++;
							}
						}
					}
					
					if(expedienteComp.getExpediente().getIntNumeroCuota() != null){
//						intCuotasDefinidas = expedienteComp.getExpediente().getIntNumeroCuota();
					}
					if(lstCronograma.get(0).getBdMontoConcepto() != null){
						bdMontoCuotaFija = lstCronograma.get(0).getBdMontoConcepto();
					}
					if(expedienteComp.getExpediente().getBdSaldoCredito() != null){
						bdMontoSaldo = expedienteComp.getExpediente().getBdSaldoCredito();
					}
					if(expedienteComp.getExpediente().getBdSaldoCredito() != null){
						bdMontoSaldo = expedienteComp.getExpediente().getBdSaldoCredito();
					}
					if(intCuotasPagadas.compareTo(0)!=0){
						expedienteComp.setIntNroCuotasPagadas(intCuotasPagadas);
					}else{
						expedienteComp.setIntNroCuotasPagadas(0);
					}
					
					if(intCuotasAtrasadas.compareTo(0)!=0){
						expedienteComp.setIntNroCuotasAtrasadas(intCuotasAtrasadas);
					}else{
						expedienteComp.setIntNroCuotasAtrasadas(0);
					}
				}
				expedienteComp.setIntNroCuotasDefinidas(expedienteComp.getExpediente().getIntNumeroCuota());
				expedienteComp.setBdCuotaFija(bdMontoCuotaFija);
				expedienteComp.setBdSaldo(bdMontoSaldo);
				lstExpedienteResult.add(expedienteComp);
			}
			
		} catch (BusinessException e) {
			strMsgErrorValidarDatos = strMsgErrorValidarDatos+" Se ha producido un error al Calcular Cuotas Pagadas y/o Vencidas.";
			log.error("Error en calcularCuotasPagadasVencidas"+e);
		}
		return lstExpedienteResult;	
	}
	
	
	/**
	 * Devuelve TRUE si existe algun expediente de credito  cuyo ultimo estado sea el deseado.
	 * @param cuenta
	 * @param intEstadoAValidar
	 * @return
	 */
	private boolean validarExistenciaPrestamosEstado_Req_Sol(Cuenta cuenta, Integer intEstadoAValidar){
		boolean blnExiste = false;
		List<ExpedienteCredito> lstExpedientes = null;
		
		try {
			lstExpedientes = solicitudPrestamoFacade.getListaExpedienteCreditoPorCuentaYUltimoEstado(cuenta, intEstadoAValidar);
			if(lstExpedientes != null && !lstExpedientes.isEmpty()){
				for (ExpedienteCredito expedienteCredito : lstExpedientes) {
					if(expedienteCredito.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ORDENDECREDITO)==0
						|| expedienteCredito.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)==0
						|| expedienteCredito.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_MULTAS)==0
						|| expedienteCredito.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)==0){
						blnExiste = Boolean.FALSE;
					}
					else{
						blnExiste = Boolean.TRUE;
						break;
					}		
				}
			}
			
		} catch (BusinessException e) {
			log.error("Error en validarExistenciaPrestamosEstado_Req_Sol ---> "+e);
			e.printStackTrace();
		}

		return blnExiste;
	}
	
	
	/**
	 * Valida la existencia de prestamos en movimiento con saldo y en estado vigente.
	 * Recupera False si Existe un credito con saldo y vigente.
	 * @param cuenta
	 * @return
	 */
//	private boolean validarExistenciaPrestamoEnMovimiento(Cuenta cuenta){
//		List<Expediente> listaExpedientesMovimeinto = null;
//		Boolean blnPasaValidacion = Boolean.TRUE;
//		strMsgErrorValidarMovimientos = "";
//		
//		try {
//			
//			listaExpedientesMovimeinto= conceptoFacade.getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(cuenta.getId().getIntPersEmpresaPk(), 
//																										cuenta.getId().getIntCuenta());
//			
//			if(listaExpedientesMovimeinto != null && !listaExpedientesMovimeinto.isEmpty()){
//				for (Expediente expediente : listaExpedientesMovimeinto) {
//					if(expediente.getBdSaldoCredito() != null){
//						if(expediente.getBdSaldoCredito().compareTo(BigDecimal.ZERO)>0){
//							if(expediente.getListaEstadosExpediente() != null && !expediente.getListaEstadosExpediente().isEmpty()){
//								for (EstadoExpediente estadoExp : expediente.getListaEstadosExpediente()) {
//									if(estadoExp.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){
//										blnPasaValidacion = Boolean.FALSE;
//										break;
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//
//		} catch (BusinessException e) {
//			log.error("Error en validarExistenciaPrestamoEnMovimiento ---> "+e);
//			e.printStackTrace();
//		}
//		return blnPasaValidacion;
//	}
	
	

	/**
	 * Valida los campos: Tipo de Operacion, Monto Solicitado, Observacion y
	 * Unidades Ejecutoras (se debe registrar al menos uno) del Expediente.
	 * Y ademas la suma de las cuotas fijas de las capacidades de credito
	 * @param beanExpedienteCredito
	 * @return
	 */
	private Boolean isValidoExpedienteCredito(ExpedienteCredito beanExpedienteCredito) {
		Boolean validExpedienteCredito = true;
		
		try {
			if(beanExpedienteCredito.getIntParaSubTipoOperacionCod() == null) {
				setMsgTxtTipoOperacion(" * El campo Tipo de Operación debe ser ingresado.");
				validExpedienteCredito = false;
			}else{
				setMsgTxtTipoOperacion("");
			}
			if(beanExpedienteCredito.getBdMontoSolicitado() == null) {
				setMsgTxtMontoSolicitado(" * El campo Monto Solicitado debe ser ingresado.");
				validExpedienteCredito = false;
			}else{
				setMsgTxtMontoSolicitado("");
			}
			if(beanExpedienteCredito.getStrObservacion() == null
					|| beanExpedienteCredito.getStrObservacion().equals("")) {
				setMsgTxtObservacion(" * El campo de Observación debe ser ingresado.");
				validExpedienteCredito = false;
			}else{
				setMsgTxtObservacion("");
			}
			if(listaCapacidadCreditoComp == null || listaCapacidadCreditoComp.isEmpty()) {
				setMsgTxtListaCapacidadPago(" * Deben agregarse Unidades Ejecutoras.");
				validExpedienteCredito = false;
			}else{
				validExpedienteCredito = validarMontosCapacidadesCredito(bdTotalCuotaMensual);
				if(validExpedienteCredito){
					setMsgTxtListaCapacidadPago("");
				}
			}
		
			Boolean blnCuenta = Boolean.TRUE;
			if(beanExpedienteCredito != null && beanExpedienteCredito.getId() != null && beanExpedienteCredito.getId().getIntItemExpediente() != null){
				blnCuenta = validarEstadoCuenta(beanExpedienteCredito.getId());
				if(blnCuenta){
					validExpedienteCredito = Boolean.FALSE;
				}
			}
		} catch (Exception e) {
			log.error("Error Exception en isValidoExpedienteCredito ---> "+e);
			e.printStackTrace();
		}
		return validExpedienteCredito;
	}


	
	/**
	 * Valida la Situacion de la cuenta del expediente seleccionado, a find e permitir solo los de Estado ACTIVO.
	 * Devuelve TRUE si el estadod e la cuenta NO ES ACTIVO.
	 * @return
	 */
	public Boolean validarEstadoCuenta(ExpedienteCreditoId expId){
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
						strMensajeValidacionCuenta = "La Solicitud de Ptmo pertenece a una Cuenta No Vigente. No se puede realizar alguna operación.";
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en validarEstadoCuenta ----> "+e);
		}
		return blnValido;
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
			if(listaCapacidadCreditoComp != null && listaCapacidadCreditoComp.size() <= 0) {
				cnt++;
			}
			if(listaCronogramaCreditoComp != null && listaCronogramaCreditoComp.size() <= 0) {
				cnt++;
			}
			if(listaGarantiaCreditoComp != null && listaGarantiaCreditoComp.size() <= 0 && !blnBtnAddGarante) {
				cnt++;
			}
			if(listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				for(RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {

					if(requisitoCreditoComp.getArchivoAdjunto() == null && requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1) {
						cnt++;
					}
				}
			}
		} catch (Exception e) {
			log.error("Error Exception en isValidTodaSolicitud ---> "+e);
			e.printStackTrace();
		}
		return cnt;
	}

	
	/**
	 * Guarda la Solicitud de Prestamo
	 * @param event
	 */
	public void grabarSolicitudPrestamo(ActionEvent event) {
		EstadoCredito estadoCredito = null;
		try {
			log.info(bdMontoInteresAtrasado);
			beanExpedienteCredito.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			beanExpedienteCredito.getId().setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());
			//Temporalmente
			beanExpedienteCredito.setIntPersEmpresaCreditoPk(beanCredito.getId().getIntPersEmpresaPk() == null ? 0 : beanCredito.getId().getIntPersEmpresaPk());
			beanExpedienteCredito.setIntParaTipoCreditoCod(beanCredito.getId().getIntParaTipoCreditoCod()== null ? 0 : beanCredito.getId().getIntParaTipoCreditoCod());
			beanExpedienteCredito.setIntItemCredito(beanCredito.getId().getIntItemCredito() == null ? 0 : beanCredito.getId().getIntItemCredito());
			beanExpedienteCredito.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
			
			// 18.09.2013 - CGD
			beanExpedienteCredito.setIntPersEmpresaSucAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntEmpresaSucAdministra());
			beanExpedienteCredito.setIntSucuIdSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalAdministra());
			beanExpedienteCredito.setIntSudeIdSubSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSubsucurAdministra());

			List<GarantiaCredito> listaGarantiaCredito = null;
	
			if(isValidoExpedienteCredito(beanExpedienteCredito) == false) {
				strErrorGrabar=" Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.";
				return;
			}

			if(listaCapacidadCreditoComp != null && listaCapacidadCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaCapacidadCreditoComp(listaCapacidadCreditoComp);
			}
	
			if(listaGarantiaCreditoComp != null && listaGarantiaCreditoComp.size() > 0) {
				listaGarantiaCredito = new ArrayList<GarantiaCredito>();
				for (GarantiaCreditoComp garantiaCreditoComp : listaGarantiaCreditoComp) {
					listaGarantiaCredito.add(garantiaCreditoComp.getGarantiaCredito());
				}
				beanExpedienteCredito.setListaGarantiaCredito(listaGarantiaCredito);
			}
	
			if(listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
			}

			//cgd-07.11.2013 - represtamo
			if(listaExpedientesParaReprestamo != null && !listaExpedientesParaReprestamo.isEmpty()){
				List<CancelacionCredito> lstCancelacionCreditos = new ArrayList<CancelacionCredito>();
				
				for (ExpedienteComp expRePrestamo : listaExpedientesParaReprestamo) {
					CancelacionCredito cancelacion = new CancelacionCredito();
					//Se modifica para que cuandren los montos.
//					cancelacion.setBdMontoCancelado(expRePrestamo.getBdMontoReprestamo());
//					cancelacion.setBdMontoCancelado(expRePrestamo.getBdMontoSaldoReprestamo());
					//jchavez 16.06.2014 se agrega monto saldo del prestamo cancelado
					
					cancelacion.setBdMontoCancelado(expRePrestamo.getBdMontoSaldoReprestamo());
					cancelacion.setIntItemExpediente(expRePrestamo.getExpediente().getId().getIntItemExpediente());
					cancelacion.setIntItemDetExpediente(expRePrestamo.getExpediente().getId().getIntItemExpedienteDetalle());
					lstCancelacionCreditos.add(cancelacion);
				}
				beanExpedienteCredito.getListaCancelacionCredito().addAll(lstCancelacionCreditos);
			}
			
		// Validamos que todos requisitos se cumplan
			if (isValidTodaSolicitud() == 0) {
				if (blnBtnAddGarante) {
					estadoCredito = new EstadoCredito();
					estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
					estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
					estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
					estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
					estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
					beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);

					if((beanExpedienteCredito.getId().getIntItemExpediente() != null) && (beanExpedienteCredito.getId().getIntItemDetExpediente() != null)){
						solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
						cancelarGrabarSolicitudPrestamo(event);
					}else{
						beanExpedienteCredito.setSocioComp(beanSocioComp);
						beanExpedienteCredito = solicitudPrestamoFacade.grabarExpedienteCredito(beanExpedienteCredito);
					}
								
					if (beanExpedienteCredito.getListaRequisitoCreditoComp() != null && beanExpedienteCredito.getListaRequisitoCreditoComp().size() > 0) {
						renombrarArchivo(beanExpedienteCredito.getListaRequisitoCreditoComp());
					}
					limpiarFormSolicitudPrestamo();
					listarSolicitudPrestamo(event);
				}
				if (!blnBtnAddGarante) {
					if(garantiasPersonales != null && garantiasPersonales.getIntNroGarantesConfigurados() != 0){
						if(listaGarantiaCreditoComp.isEmpty() || listaGarantiaCreditoComp == null) {
							setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago + "* Deben agregarse Garantes Solidarios. ");
							return;
						}else{
							calcularGarantesValidos();
							Boolean blnMarca = Boolean.TRUE;
							
							blnMarca = validarGrabarGarantes();
							if(!blnMarca){
								return;
							}else{
								estadoCredito = new EstadoCredito();
								estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
								estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
								estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
								estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
								estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
								estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
								beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);

								if((beanExpedienteCredito.getId().getIntItemExpediente() != null) && (beanExpedienteCredito.getId().getIntItemDetExpediente() != null)){
									solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
									cancelarGrabarSolicitudPrestamo(event);
								}else{
									beanExpedienteCredito.setSocioComp(beanSocioComp);
									beanExpedienteCredito = solicitudPrestamoFacade.grabarExpedienteCredito(beanExpedienteCredito);
								}
								
								if (beanExpedienteCredito.getListaRequisitoCreditoComp() != null && beanExpedienteCredito.getListaRequisitoCreditoComp().size() > 0) {
									renombrarArchivo(beanExpedienteCredito.getListaRequisitoCreditoComp());
								}
								limpiarFormSolicitudPrestamo();
								listarSolicitudPrestamo(event);
							}
						}
					}
				}
				
				
			}else{
				// Si no se graba en estado REQUISITO
				estadoCredito = new EstadoCredito();
				estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
				estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
				estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
				estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
				estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
				beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);

				if((beanExpedienteCredito.getId().getIntItemExpediente() != null) && (beanExpedienteCredito.getId().getIntItemDetExpediente() != null)){
					solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
					cancelarGrabarSolicitudPrestamo(event);
				}else{
					//10.05.2013 - CGD
					beanExpedienteCredito.setSocioComp(beanSocioComp);
					beanExpedienteCredito = solicitudPrestamoFacade.grabarExpedienteCredito(beanExpedienteCredito);
				}
				
				//10.05.2013 - cgd
				if (beanExpedienteCredito.getListaRequisitoCreditoComp() != null && beanExpedienteCredito.getListaRequisitoCreditoComp().size() > 0) {
					renombrarArchivo(beanExpedienteCredito.getListaRequisitoCreditoComp());
				}
				limpiarFormSolicitudPrestamo();
				listarSolicitudPrestamo(event);
			}

		} catch (BusinessException e) {
			log.error("Error BusinessException en grabarSolicitudPrestamo --->  "+e);
			e.printStackTrace();	
		}
	}

	
	
	/**
	 * Renombra los adjuntos de acuerdo a configuracion.
	 * Adjuntos mapeados son 20 (titular + garante):
	 * CARTADESCUENTO, AUTORIZACIONDSCTO, BOLETAPAGO, COPIADNI, PAGARE, AUTORIZACIONPORINCENTLABORALES, 
	 * CONTRATOLABORALVIGENTE, AUTORIZACIONVISADAPORBIENESTARDEPERSONAL, DECLARACIONJURADA, 
	 * CRONOGRAMADEPAGO, ESTADODECUENTA, RECIBODESERVICIOS, VERIFICACION_DOMICILIARIA, BOUCHER_PAGO, 
	 * CTS_SOCIO, CTS_GARANTE, ARTA_COMPROMISO, LETRA_DE_CAMBIO, FICHA_REGISTRAL, ADJUNTOS_DEL_CREDITO
	 * @param lista
	 * @throws BusinessException
	 */
	public void renombrarArchivo(List<RequisitoCreditoComp> lista) throws BusinessException {
		TipoArchivo tipoArchivo = null;
		Archivo archivo = null;
		try {
			for (RequisitoCreditoComp requisitoCreditoComp : lista) {
				if (requisitoCreditoComp.getArchivoAdjunto() != null) {
				
			// PRESTAMO_CARTADESCUENTO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTADESCUENTO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTADESCUENTO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
					
			// PRESTAMO_AUTORIZACIONDSCTO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONDSCTO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONDSCTO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			//--- PRESTAMO_BOLETAPAGO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOLETAPAGO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOLETAPAGO)
							&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
							&& requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1
							&& requisitoCreditoComp.getArchivoAdjunto() != null) {
						tipoArchivo = generalFacade.getTipoArchivoPorPk(requisitoCreditoComp.getArchivoAdjunto().getId().getIntParaTipoCod());
						log.info("requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo(): "+ requisitoCreditoComp.getArchivoAdjunto().getStrNombrearchivo());
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

			// PRESTAMO_COPIADNI
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_COPIADNI)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_COPIADNI)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			// PRESTAMO_PAGARE
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_PAGARE)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_PAGARE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			// PRESTAMO_AUTORIZACIONPORINCENTLABORALES
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONPORINCENTLABORALES)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONPORINCENTLABORALES)
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			// PRESTAMO_CONTRATOLABORALVIGENTE
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CONTRATOLABORALVIGENTE)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CONTRATOLABORALVIGENTE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			// PRESTAMO_AUTORIZACIONVISADAPORBIENESTARDEPERSONAL
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONVISADAPORBIENESTARDEPERSONAL)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONVISADAPORBIENESTARDEPERSONAL)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			// PRESTAMO_DECLARACIONJURADA
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_DECLARACIONJURADA)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_DECLARACIONJURADA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			// PRESTAMO_CRONOGRAMADEPAGO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CRONOGRAMADEPAGO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CRONOGRAMADEPAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			// PRESTAMO_ESTADODECUENTA
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ESTADODECUENTA)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ESTADODECUENTA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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

			// PRESTAMO_RECIBODESERVICIOS
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_RECIBODESERVICIOS)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_RECIBODESERVICIOS)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
					
			// PRESTAMO_VERIFICACION_DOMICILIARIA
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_VERIFICACION_DOMICILIARIA)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_VERIFICACION_DOMICILIARIA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
					
			// PRESTAMO_BOUCHER_PAGO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOUCHER_PAGO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOUCHER_PAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
			
			// PRESTAMO_CTS_SOCIO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_SOCIO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_SOCIO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
					
			// PRESTAMO_CTS_GARANTE
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_GARANTE)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_GARANTE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
					
					
			// PRESTAMO_CARTA_COMPROMISO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTA_COMPROMISO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTA_COMPROMISO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
		
			// PRESTAMO_LETRA_DE_CAMBIO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_LETRA_DE_CAMBIO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_LETRA_DE_CAMBIO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
					
	
			// PRESTAMO_FICHA_REGISTRAL
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_REGISTRAL)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_REGISTRAL)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
			
		
			// PRESTAMO_ADJUNTOS_DEL_CREDITO
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ADJUNTOS_DEL_CREDITO)
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
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ADJUNTOS_DEL_CREDITO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
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
		} catch (BusinessException e) {
			log.error("Error BusinessException en renombrarArchivo ---> "+e);
			throw e;
		}
	}

	
	/**
	 * Redirige la aplicaCion a Actualizar
	 * @param event
	 * @throws IOException 
	 * @throws EJBFactoryException 
	 */
	
	public void irModificarSolicitudPrestamo(ActionEvent event) throws EJBFactoryException, IOException {		
		limpiarFormSolicitudPrestamo();
		blnValidarGarantes = Boolean.TRUE;
		intTipoEvaluacion = 2 ;
		List<ExpedienteComp> lstExpedientesRecuperados = null;
		Cuenta cuentaExpedienteCredito = null;
		List<CuentaConcepto> lstCtaCto = null;
		Cuenta cuentaSocio = null;
		CuentaComp cuentaComp = null;
		
		try {
			
			limpiarResultadoBusqueda();
			blnModoVisualizacion = true;
			blnMostrarDescripciones = Boolean.TRUE;
			strSolicitudPrestamo = Constante.MANTENIMIENTO_MODIFICAR;
			SocioComp socioComp;
			Integer intIdPersona = null;
			Persona persona = null;

			ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
			expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
			expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
			expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
			expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());
			
			// devuelve el crongrama son id vacio.
			beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);
			log.info("monto total ---> "+beanExpedienteCredito.getBdMontoTotal());
			if (beanExpedienteCredito != null) {

				Credito creditoRecuperado = new Credito();
				CreditoId creditoRecuperadoId = new CreditoId();
				creditoRecuperadoId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
				creditoRecuperadoId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
				creditoRecuperadoId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());
				creditoRecuperado = creditoFacade.getCreditoPorIdCredito(creditoRecuperadoId);
				beanCredito = creditoRecuperado;
				
				
				// Recuperamos el id de persona del socio desde la capacidad.
				if (beanExpedienteCredito.getListaCapacidadCreditoComp() != null && beanExpedienteCredito.getListaCapacidadCreditoComp().size() > 0) {
					listaCapacidadCreditoComp = beanExpedienteCredito.getListaCapacidadCreditoComp();
					if (listaCapacidadCreditoComp != null && listaCapacidadCreditoComp.size() > 0) {
						for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
							if(capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk() != null){
								intIdPersona = capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk();
								break;
							}
						}
						// Persona de socio
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
							
							cuentaExpedienteCredito = new Cuenta();
							cuentaExpedienteCredito.setId(new CuentaId());
							cuentaExpedienteCredito.getId().setIntCuenta(beanExpedienteCredito.getId().getIntCuentaPk());
							cuentaExpedienteCredito.getId().setIntPersEmpresaPk(beanExpedienteCredito.getId().getIntPersEmpresaPk());
							
							socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
										new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
										persona.getDocumento().getStrNumeroIdentidad(),
										Constante.PARAM_EMPRESASESION, cuentaExpedienteCredito);

							for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
								if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
									socioComp.getSocio().setSocioEstructura(socioEstructura);
								}
							}
							
							lstCtaCto = recuperarCuentasConceptoSocio(socioComp);
							cuentaComp = new CuentaComp();
							
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

							cuentaSocio = cuentaFacade.getCuentaActualPorIdPersona(socioComp.getPersona().getIntIdPersona(), Constante.PARAM_EMPRESASESION);
							if(cuentaSocio != null ){
								cuentaComp.setCuenta(cuentaSocio);
								socioComp.setCuenta(cuentaSocio);
							}
							socioComp.setCuentaComp(cuentaComp);
							beanSocioComp = socioComp;
							
							if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
								for (CuentaConcepto ctaCto : listaCuentaConcepto) {
									if(ctaCto.getListaCuentaConceptoDetalle() != null && !ctaCto.getListaCuentaConceptoDetalle().isEmpty()){
										for (CuentaConceptoDetalle ctaCtoDet : ctaCto.getListaCuentaConceptoDetalle()) {
											Boolean blnContinua = Boolean.TRUE;
											
											// se valida el inicio
											if(ctaCtoDet.getTsInicio()!= null){
												if(ctaCtoDet.getTsInicio().before(new Timestamp(new Date().getTime()))){
													blnContinua = Boolean.TRUE;
												}else{
													blnContinua = Boolean.FALSE;
												}
												if(blnContinua){
													if(ctaCtoDet.getTsFin()!= null){
														if(ctaCtoDet.getTsFin().after(new Timestamp(new Date().getTime()))){
															blnContinua = Boolean.FALSE;	
														}
													}else{
														blnContinua = Boolean.TRUE;
													}
												}
											}else{
												blnContinua = Boolean.FALSE;
											}

											if(blnContinua){
												bdAportes = bdAportes.add(ctaCtoDet.getBdMontoConcepto());
											}
										}
									}
								}
							}

							
							bdMontoSaldoAnterior = BigDecimal.ZERO;
							// CGD-07.11.2013 - REPRETSAMO
							if(beanExpedienteCredito.getIntParaSubTipoOperacionCod().compareTo(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)==0){
								if(beanExpedienteCredito.getListaCancelacionCredito() != null && !beanExpedienteCredito.getListaCancelacionCredito().isEmpty()){
									for(CancelacionCredito cancelacion : beanExpedienteCredito.getListaCancelacionCredito()) {
										bdMontoSaldoAnterior=bdMontoSaldoAnterior.add(cancelacion.getBdMontoCancelado());
									}
									//JCHAVEZ 23.06.2014
									lstExpedientesRecuperados = recuperarCreditosMovimiento();
									listaExpedientesParaReprestamo = validarExpedientesParaRePrestamo(lstExpedientesRecuperados);
									
									if (listaExpedientesParaReprestamo!=null && !listaExpedientesParaReprestamo.isEmpty()) {
										ExpedienteCreditoId expCredId = null;
										for (ExpedienteComp expediente : listaExpedientesParaReprestamo) {
											expCredId = new ExpedienteCreditoId();
											expCredId.setIntCuentaPk(expediente.getExpediente().getId().getIntCuentaPk());
											expCredId.setIntItemDetExpediente(expediente.getExpediente().getId().getIntItemExpedienteDetalle());
											expCredId.setIntItemExpediente(expediente.getExpediente().getId().getIntItemExpediente());
											expCredId.setIntPersEmpresaPk(expediente.getExpediente().getId().getIntPersEmpresaPk());
											break;
										}
										bdMontoSaldoAnterior = bdMontoSaldoAnterior.add(bdMontoInteresAtrasado!=null?bdMontoInteresAtrasado:calcularInteresAtrasado(expCredId));
									}
									
									listExpedienteMovimientoComp =  recuperarCanceladoCreditoMovimiento(beanExpedienteCredito.getListaCancelacionCredito());
									//jchavez 24.06.2014
									recuperarCreditosMovimiento();
									beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(listExpedienteMovimientoComp);
									Integer intTamanno = listExpedienteMovimientoComp.size();
									intTamanno = intTamanno + 1;
									beanSocioComp.getCuentaComp().setIntTamannoListaExp(intTamanno);
								}
							}else{
								// Recuperamos los expedientes de movimineto Ppara visualizarlos en la grilla de saldo de cuentas
								lstExpedientesRecuperados = recuperarCreditosMovimiento();
								if(lstExpedientesRecuperados != null && !lstExpedientesRecuperados.isEmpty()){
									strMsgErrorValidarDatos= "";
									beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(lstExpedientesRecuperados);
									CuentaComp miCuentaComp = beanSocioComp.getCuentaComp();
									
									List<CuentaComp> listaTemp = new ArrayList<CuentaComp>();
									listaTemp.add(miCuentaComp);
									socioComp.setListaCuentaComp(listaTemp);
									beanSocioComp.setListaCuentaComp(listaTemp);
									Integer intTam = lstExpedientesRecuperados.size();
									intTam++;
									beanSocioComp.getCuentaComp().setIntTamannoListaExp(intTam);
									
								}else{
									beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(lstExpedientesRecuperados);
									beanSocioComp.getCuentaComp().setIntTamannoListaExp(1);
									strMsgErrorValidarDatos ="No se ha encontrado algún Crédito con deuda.";
								}
							}
							// Queda pendiente sumar si se eligiera cancelar un préstamo anterior
							bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoSolicitado();
							bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
						}
					}
				}
				// Se valida el estado del Expediente para ver si se puede o no
				// Eliminar o Modificar
				if (beanExpedienteCredito.getListaEstadoCredito() != null && !beanExpedienteCredito.getListaEstadoCredito().isEmpty()) {
					configurarCampos(beanExpedienteCredito);
					cargarDescripciones();
					
					mostrarlistaAutorizacionesPrevias(beanExpedienteCredito.getListaEstadoCredito());

					if(beanExpedienteCredito.getEstadoCreditoUltimo() != null){
						if(beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)==0
							||beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)==0 ){
							setStrSolicitudPrestamo(Constante.MANTENIMIENTO_ELIMINAR);
						}else if(beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
							||beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0 ){
							setStrSolicitudPrestamo(Constante.MANTENIMIENTO_MODIFICAR);
						}
						
					}
					if(beanExpedienteCredito.getEstadoCreditoPrimero() != null){
						if(beanExpedienteCredito.getEstadoCreditoPrimero().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
							strFechaRegistro = Constante.sdf.format(beanExpedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado());
							}
					}
				}
				
				if (beanExpedienteCredito.getListaEstadoCredito() != null && !beanExpedienteCredito.getListaEstadoCredito().isEmpty()) {
					int maximo = beanExpedienteCredito.getListaEstadoCredito().size();
					EstadoCredito estadoCreditoSolicitud = beanExpedienteCredito.getListaEstadoCredito().get(maximo - 1);
					intUltimoEstadoSolicitud = estadoCreditoSolicitud.getIntParaEstadoCreditoCod();
					tablaEstado = tablaFacade.getTablaPorIdMaestroYIdDetalle(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO),
																				estadoCreditoSolicitud.getIntParaEstadoCreditoCod());
					strUltimoEstadoSolicitud = tablaEstado.getStrDescripcion();
					strFechaRegistro = Constante.sdf.format(estadoCreditoSolicitud.getTsFechaEstado());
				}
				intNroCuotas = beanExpedienteCredito.getIntNumeroCuota();
				
				//CGD-12.11.2013
				evaluarPrestamoCronogramaView(event);
				blnEvaluacionCredito = true;
				formSolicitudPrestamoRendered = true;
				pgValidDatos = false;
				blnDatosSocio = true;
					
				if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null && !beanExpedienteCredito.getListaRequisitoCreditoComp().isEmpty()) {
					listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
				}

				if (beanExpedienteCredito.getListaGarantiaCredito() != null && beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
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
										 if(estructuraGarante.getListaEstructura() != null && !estructuraGarante.getListaEstructura().isEmpty()){
												int maximo = estructuraGarante.getListaEstructura().size();
												Juridica juridicaGarante = new Juridica();
												juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
												estructuraGarante.setJuridica(juridicaGarante);
											}
									}
								
									if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null && !socioCompGaranteEdit.getSocio().getListSocioEstructura().isEmpty()){
										int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
										socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
									}
									
									Integer nroMaxAsegurados = new Integer(-1);
									nroMaxAsegurados=solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGaranteEdit.getPersona().getIntIdPersona());
									
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

					if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()){
						calcularGarantesValidos();
					}
					garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
					log.info("garantias personales"+garantiasPersonales);
					log.info("paso por aca");
					if(garantiasPersonales != null){
						if(garantiasPersonales.getIntNroGarantesConfigurados().compareTo(0)==0){
							blnBtnAddGarante = Boolean.TRUE;
						}else{
							blnBtnAddGarante = Boolean.FALSE;
						}
					}else{
						blnBtnAddGarante = Boolean.TRUE;
					}
				}
			}
			
		} catch (BusinessException e) {
			log.error("Error BusinessException en  irModificarSolicitudPrestamo ---> " + e);
			e.printStackTrace();
		} finally {
			formSolicitudPrestamoRendered = true;
			pgValidDatos = false;
			blnDatosSocio = true;
		}
	}

	
	/**
	 * Recupera el cronograma y lo convierte en cronograma comp, ya no reecalcula nada solo se visualiza.
	 * @param event
	 */
	private void evaluarPrestamoCronogramaView(ActionEvent event) {
		CronogramaCreditoComp cronogramaComp = null;
		Integer intNroCuotas = beanExpedienteCredito.getIntNumeroCuota();
		BigDecimal bdTotalCuotaMensualCronograma = BigDecimal.ZERO;
		
		try {
			listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
			for (int i = 0, intNroCuota = 1 ; i < intNroCuotas; i++, intNroCuota++) {
				cronogramaComp = solicitudPrestamoFacade.recuperarCronogramaCompVistaPopUp(beanExpedienteCredito.getId(), intNroCuota);
				if(intNroCuota == 1){
					bdTotalCuotaMensualCronograma = cronogramaComp.getBdTotalCuotaMensual();
				}
				listaCronogramaCreditoComp.add(cronogramaComp);
			}
			strPorcInteres = "" + beanExpedienteCredito.getBdPorcentajeInteres().divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
			strPorcAportes = ""+beanExpedienteCredito.getBdPorcentajeAporte().divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
			bdMontoPrestamo = beanExpedienteCredito.getBdMontoTotal();
			bdPorcSeguroDesgravamen = beanExpedienteCredito.getBdMontoGravamen();
			bdTotalDstos = beanExpedienteCredito.getBdMontoAporte();
			bdTotalCuotaMensual = bdTotalCuotaMensualCronograma;

		} catch (Exception e) {
			log.error("Error en evaluarPrestamoCronogramaView ---> "+e);
		}
	}


	/**
	 * Convierte la lista CanceladoCredito a lista ExpedienetComp.
	 * Para el caso de edicion y ver represtamo.
	 * @param listaCancelacionCredito
	 * @return
	 */
	private List<ExpedienteComp> recuperarCanceladoCreditoMovimiento(List<CancelacionCredito> listaCancelacionCredito) {
		List<ExpedienteComp> listaExpedientesGrillaComp = null;
		Expediente expedienteMov = null;
		try {
			listaExpedientesGrillaComp = new ArrayList<ExpedienteComp>();
			if (listaCancelacionCredito!=null && !listaCancelacionCredito.isEmpty()) {
				for (CancelacionCredito cancelacion: listaCancelacionCredito) {
					log.info("Recorriendo lista de movimientos cancelados");
					ExpedienteId expId = new ExpedienteId();
					expId.setIntCuentaPk(cancelacion.getId().getIntCuentaPk());
					expId.setIntItemExpediente(cancelacion.getIntItemExpediente());
					expId.setIntItemExpedienteDetalle(cancelacion.getIntItemDetExpediente());
					expId.setIntPersEmpresaPk(cancelacion.getId().getIntPersEmpresaPk());
					expedienteMov =  conceptoFacade.getExpedientePorPK(expId);
					
					if(expedienteMov != null){
						String strDescripcionExpedienteCredito;
						ExpedienteComp expedienteComp = new ExpedienteComp();
						expedienteComp.setExpediente(expedienteMov);
						
						//Recuoperamos la descripciond el crdsiro
						CreditoId creditoId = new CreditoId();
						creditoId.setIntItemCredito(expedienteMov.getIntItemCredito());
						creditoId.setIntParaTipoCreditoCod(expedienteMov.getIntParaTipoCreditoCod());
						creditoId.setIntPersEmpresaPk(expedienteMov.getIntPersEmpresaCreditoPk());
						
						Credito creditoRec = null;
						strDescripcionExpedienteCredito = "Desconocido";
						creditoRec = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
						if(creditoRec != null){
							List<Tabla> listaDescripcionExpedienteXredito= null;	
							listaDescripcionExpedienteXredito = tablaFacade.getListaTablaPorIdMaestroYNotInIdDetalle(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 
																													creditoRec.getId().getIntParaTipoCreditoCod().toString());
							if(!listaDescripcionExpedienteXredito.isEmpty()){
								for (Tabla tabla : listaDescripcionExpedienteXredito) {
									if(tabla.getIntIdDetalle().compareTo(creditoRec.getIntParaTipoCreditoEmpresa())==0){
										strDescripcionExpedienteCredito = tabla.getStrDescripcion();
										break;
									}
								}
							}
						}
						expedienteComp.setStrDescripcionTipoCreditoEmpresa(strDescripcionExpedienteCredito);
						expedienteComp.setStrDescripcionTipoCredito(strDescripcionExpedienteCredito);
						expedienteComp.setChecked(true);
						listaExpedientesGrillaComp.add(expedienteComp);
					}
				}
			}			
		} catch (Exception e) {
			log.error("Error en recuperarCanceladoCreditoMovimiento ---> "+e);
		}
		return listaExpedientesGrillaComp;
	}
	
	
	/**
	 * Metodo que realiza la modificad¿cion de la soliciutd
	 * @param event
	 */
	public void modificarSolicitudPrestamo(ActionEvent event) {
		EstadoCredito estadoCredito = null;
		List<GarantiaCredito> listaGarantiaCredito = null;
		
		try {
			
			if (isValidoExpedienteCredito(beanExpedienteCredito) == false) {
				strErrorGrabar=" Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.";
				return;
			}
			log.info("Monto Total: "+beanExpedienteCredito.getBdMontoTotal());
			evaluarPrestamoModificar(event);
			
			if (isValidTodaSolicitud() == 0) {
				estadoCredito = new EstadoCredito();
				estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
				estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
				estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
				estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
				estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
				estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
				beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);
			}
			
//			if (isValidTodaSolicitud() != 0) {
//				if (blnBtnAddGarante) {
//					estadoCredito = new EstadoCredito();
//					estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
//					estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
//					estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
//					estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
//					estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
//					estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
//					beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);
//				}							
//			}
	
			if (listaCapacidadCreditoComp != null	&& listaCapacidadCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaCapacidadCreditoComp(listaCapacidadCreditoComp);
			}
	
			if (listaGarantiaCreditoComp != null && listaGarantiaCreditoComp.size() > 0) {
				listaGarantiaCredito = new ArrayList<GarantiaCredito>();
				for (GarantiaCreditoComp garantiaCreditoComp : listaGarantiaCreditoComp) {
					listaGarantiaCredito.add(garantiaCreditoComp.getGarantiaCredito());
				}
				beanExpedienteCredito.setListaGarantiaCredito(listaGarantiaCredito);
			}
			
			if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
			}

			//10.05.2013 - CGD
			beanExpedienteCredito.setSocioComp(beanSocioComp);			
			beanExpedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
			solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
			limpiarFormSolicitudPrestamo();
			limpiarResultadoBusqueda();
			cancelarGrabarSolicitudPrestamo(event);
			
		}  catch (BusinessException e) {
			log.error("Error BusinessException en modificarSolicitudPrestamo ---> "+e);
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Limpia la grilla de busqueda de solicitudes
	 */
	public void limpiarResultadoBusqueda(){
		try {
			if(listaExpedienteCreditoComp != null && !listaExpedienteCreditoComp.isEmpty()){
				listaExpedienteCreditoComp.clear();
			}
		} catch (Exception e) {
			log.error("Error en limpiarResultadoBusqueda ---> "+e);
		}
	}
	
	
	/**
	 * Setea el monto solicitado.
	 * Si es represtamo se agregara el moneto saldo del credito anterior.
	 * @param event
	 */
	public void setMontoSolicitado(ActionEvent event) {		
		try {
			String strMontoSolicitado = getRequestParameter("bdMontoSolicitado");
			if (strMontoSolicitado != null) {
				BigDecimal bdMontoSolicitado = new BigDecimal(strMontoSolicitado.equals("") ? "0.00": strMontoSolicitado);
				beanExpedienteCredito.setBdMontoSolicitado(bdMontoSolicitado.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP));
				log.info("beanExpedienteCredito.getBdMontoSolicitado(): "+ beanExpedienteCredito.getBdMontoSolicitado());
			}
		} catch (Exception e) {
			log.error("Error Exception en setMontoSolicitado ---> " + e);
		}
	}

	
	/**
	 * Valida el Monto ingresado y que se haya registrado Capacidad de Credito
	 * en el Expediente.
	 * @param beanExpedienteCredito
	 * @return True(Cumple con validaciones) , False (No cumple con
	 *         validaciones).
	 */
	private Boolean isValidoEvaluacionCredito(ExpedienteCredito beanExpedienteCredito) {
		Boolean validEvaluarCredito = true;
		try {
			if (beanExpedienteCredito.getBdMontoSolicitado() == null || beanExpedienteCredito.getBdMontoSolicitado().compareTo(BigDecimal.ZERO) == 0) {
				setMsgTxtMontoSolicitado("* El monto solicitado debe ser ingresado.");
				validEvaluarCredito = false;
			} else {
				setMsgTxtMontoSolicitado("");
			}
			if (listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()) {
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
		} catch (Exception e) {
			log.error("Error Exception en isValidoEvaluacionCredito ---> "+e);
			e.printStackTrace();
		}
		return validEvaluarCredito;
	}

	
	/**
	 * Realiza la 'Evaluacion' de la solicitud de Credito.
	 * 
	 * @param event
	 * @throws ParseException
	 */
	public void evaluarPrestamoModificar(ActionEvent event) throws ParseException {
		EstructuraDetalle estructuraDetalle = null;
		bdPorcSeguroDesgravamen = new BigDecimal(0);
		bdTotalDstos = new BigDecimal(0);
		bdMontoPrestamo = new BigDecimal(0);
		bdCuotaMensual = new BigDecimal(0);
		bdTotalCuotaMensual = new BigDecimal(0);
		listaCronogramaCreditoComp = null;
		blnSeGeneroCronogramaCorrectamente = true;

		try {
			listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
			bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoSolicitado();// bdMontoTotalSolicitado.add(bdMontoSaldoAnterior);
			if (isValidoEvaluacionCredito(beanExpedienteCredito) == false) {
				log.info("Datos de Evaluación no válidos. Se aborta el proceso de evaluación del Crédito.");
				return;
			}
			
			cargarDescripciones();
			
			if (beanSocioComp.getSocio().getSocioEstructura() != null) {
				estructuraDetalle = new EstructuraDetalle();
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																							beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());

				if(blnSeGeneroCronogramaCorrectamente){
					garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
					validarCreditoRecuperado(beanCredito,estructuraDetalle);
					// CGD-15.11.2013
					msgTxtCreditoConvenio = "";
					blnEvaluacionCredito = true;
				}
			}
		} catch (Exception e) {
			log.error("Error Exception en evaluarPrestamoModificar: " + e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Realiza la 'Evaluacion' de la solicitud de Credito.
	 * 
	 * @param event
	 * @throws ParseException
	 */
	public void evaluarPrestamoModificarRePrestamo(ActionEvent event) throws ParseException {
		EstructuraDetalle estructuraDetalle = null;
		bdPorcSeguroDesgravamen = new BigDecimal(0);
		bdTotalDstos = new BigDecimal(0);
		bdMontoPrestamo = new BigDecimal(0);
		bdCuotaMensual = new BigDecimal(0);
		bdTotalCuotaMensual = new BigDecimal(0);
		listaCronogramaCreditoComp = null;
		blnSeGeneroCronogramaCorrectamente = true;

		try {
				if (isValidoEvaluacionCredito(beanExpedienteCredito) == false) {
					log.info("Datos de Evaluación no válidos. Se aborta el proceso de evaluación del Crédito.");
					return;
				}
				cargarDescripciones();

				bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoSolicitado();
				listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();

				if (beanSocioComp.getSocio().getSocioEstructura() != null) {
					estructuraDetalle = new EstructuraDetalle();
					estructuraDetalle.setId(new EstructuraDetalleId());
					estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
					beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
					beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
					
					if(blnSeGeneroCronogramaCorrectamente){
						garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
						validarCreditoRecuperado(beanCredito,estructuraDetalle);
						mostrarArchivosAdjuntos(event);
						msgTxtCreditoConvenio = "";
						blnEvaluacionCredito = true;
					}
				}
		} catch (Exception e) {
			log.error("Error Exception en evaluarPrestamoModificar: " + e);
			e.printStackTrace();
		}
	}

	
	/**
	 * Redirige la evalucion, si es la primera vez o es una edicion.
	 * @param event
	 * @throws ParseException
	 */
	public void evaluarPrestamo(ActionEvent event) throws ParseException {
		try {
			if(intTipoEvaluacion.compareTo(new Integer(1))==0){
				evaluarPrestamoInicio(event);
			}else if(intTipoEvaluacion.compareTo(new Integer(2))==0){
				evaluarPrestamoModificar(event);
			}
		} catch (Exception e) {
			log.error("Error en evaluarPrestamo ---> "+e);
			e.printStackTrace();
		}
	}
	

	/**
	 * Evalua la solicitud pro priemera vez.
	 * @param event
	 * @throws ParseException
	 */
	public void evaluarPrestamoInicio(ActionEvent event) throws ParseException {
		EstructuraDetalle estructuraDetalle = null;
		Date today = new Date();
		String strToday = Constante.sdf.format(today);
		Date dtToday = Constante.sdf.parse(strToday);
		Credito credito;
		bdPorcSeguroDesgravamen = new BigDecimal(0);
		bdTotalDstos = new BigDecimal(0);
		bdMontoPrestamo = new BigDecimal(0);
		bdCuotaMensual = new BigDecimal(0);
		bdTotalCuotaMensual = new BigDecimal(0);
		listaCronogramaCreditoComp = null;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetalle = null;
		boolean blnContinua = true; 
		blnSeGeneroCronogramaCorrectamente = true;
		listaCreditosValidados = null;
		
		try {
		
			if (isValidoEvaluacionCredito(beanExpedienteCredito) == false) {
				log.info("Datos de Evaluación no válidos. Se aborta el proceso de evaluación del Crédito.");
				return;
			}
			
			listaCreditosValidados = new ArrayList<Credito>();
			listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
			bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoSolicitado();

			cargarDescripciones();
			
			if (beanSocioComp.getSocio().getSocioEstructura() != null) {
				estructuraDetalle = new EstructuraDetalle();	
				estructuraDetalle.setId(new EstructuraDetalleId());
				estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
				estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
				estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
				beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
				beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
				listaConvenioEstructuraDetalle = estructuraFacade.getListaConvenioEstructuraDetallePorEstructuraDetCompleto(estructuraDetalle.getId());
				
				if(listaConvenioEstructuraDetalle != null && !listaConvenioEstructuraDetalle.isEmpty()){
				
					for (ConvenioEstructuraDetalleComp convEstrDetComp : listaConvenioEstructuraDetalle) {
						Adenda adenda = new Adenda();
						adenda.setId(new AdendaId());
						adenda.getId().setIntConvenio(convEstrDetComp.getConvenioEstructuraDetalle().getId().getIntConvenio());
						adenda.getId().setIntItemConvenio(convEstrDetComp.getConvenioEstructuraDetalle().getId().getIntItemConvenio());
						adenda = convenioFacade.getConvenioPorIdConvenio(adenda.getId());
						
						blnContinua = true;
						if(adenda.getDtCese() == null){
							blnContinua = true;
						}else{
							if(dtToday.before(adenda.getDtCese())){
								blnContinua = true;
							}else{	
								blnContinua = false;
							}
						}
						
						if(blnContinua){
							if(adenda.getIntParaEstadoHojaPlan() == null){
								blnContinua = true;
							}else{
								if(adenda.getIntParaEstadoHojaPlan().compareTo(Constante.PARAM_T_ESTADODOCUMENTO_CONCLUIDO)==0){
									blnContinua = true;
								}else{	
									blnContinua = false;
								}
							}
							
							if(blnContinua){
								if(adenda.getIntParaEstadoValidacion() == null){
									blnContinua = true;
								}else{
									if(adenda.getIntParaEstadoValidacion().compareTo(Constante.PARAM_T_ESTADODOCUMENTO_APROBADO)==0){
										blnContinua = true;
									}else{	
										blnContinua = false;
									}
								}
							}
						}
						
						if(blnContinua){
							if(adenda.getIntParaEstadoConvenioCod() == null){
								blnContinua = true;
							}else{
								if(adenda.getIntParaEstadoConvenioCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
									blnContinua = true;
								}else{	
									blnContinua = false;
								}
							}
						}
						
						if (((dtToday.after(adenda.getDtInicio()))
							&& adenda.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0)){

							if(blnContinua){
								if ((adenda.getListaAdendaCreditos() != null)) {
									for (int j = 0; j < adenda.getListaAdendaCreditos().size(); j++) {
										if(adenda.getListaAdendaCreditos().get(j).getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0){
											credito = new Credito();
											credito.setId(new CreditoId());
											credito.getId().setIntPersEmpresaPk(adenda.getListaAdendaCreditos().get(j).getId().getIntPersEmpresaPk());
											credito.getId().setIntParaTipoCreditoCod(adenda.getListaAdendaCreditos().get(j).getId().getIntParaTipoCreditoCod());
											credito.getId().setIntItemCredito(adenda.getListaAdendaCreditos().get(j).getId().getIntItemCredito());
											credito = creditoFacade.getCreditoPorIdCredito(credito.getId());
											
											blnYaTieneCredito = aplicaPromocional(beanSocioComp,credito);
											log.error("DESCRIPCION DE CREDITO ************* "+credito.getStrDescripcion());
											log.error("APLICA CREDITO ************* "+blnYaTieneCredito);
											
											// Si teiene creditosd anterioes no debe caer en promocionale											
											if(blnYaTieneCredito){
												credito = null;
											}else{
												Boolean blnNoAplica = Boolean.FALSE;
												blnNoAplica = validarExcepcionDiasPromocional(credito, beanSocioComp);
												if(blnNoAplica){
													credito = null;
												}
											}

											if (credito != null){
												if(credito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) ==  0) {
													if (credito.getIntParaRolPk().compareTo(intTipoRelacion)==0) {
														listaCreditosValidados.add(credito);
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

			if(listaCreditosValidados != null && !listaCreditosValidados.isEmpty()){
				List<CreditoComp> listaCreditosValidadosComp = new ArrayList<CreditoComp>();
				
				for (Credito creditoVal : listaCreditosValidados) {
					CreditoComp creditoComp = new CreditoComp();
					creditoComp.setCredito(creditoVal);
					creditoComp.setChkCredito(Boolean.FALSE);
					listaCreditosValidadosComp.add(creditoComp);
				}
				
				evaluarCreditosComp(listaCreditosValidadosComp, event);
				garantiasPersonales = recuperarConfiguracionGarantiaTipoPersonal();
				if(garantiasPersonales != null){
					if(garantiasPersonales.getIntNroGarantesConfigurados().compareTo(0)==0){
						blnBtnAddGarante = Boolean.TRUE;
					}else{
						blnBtnAddGarante = Boolean.FALSE;
					}
				}else{
					blnBtnAddGarante = Boolean.TRUE;
				}
				calcularGarantesValidos();
			}

		} catch (Exception e) {
			log.error("Error Exception en evaluarPrestamo ---> " + e);
		} 
	}
	
	
	/**
	 * ReEvalua solicitud que cubre margen de error sobre rangos de escala de tipo de credito.
	 * @param listaCreditosComp
	 * @param event
	 */
	public void evaluarCreditosComp (List<CreditoComp> listaCreditosComp, ActionEvent event){
		EstructuraDetalle estructuraDetalle = null;
		try {
		
			if(listaCreditosComp != null && !listaCreditosComp.isEmpty()){	
				if (beanSocioComp.getSocio().getSocioEstructura() != null) {
					estructuraDetalle = new EstructuraDetalle();	
					estructuraDetalle.setId(new EstructuraDetalleId());
					estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
					estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
					estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
					beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
					beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
				}
				
				for (CreditoComp creditoComp : listaCreditosComp) {
					if(creditoComp.getChkCredito().compareTo(Boolean.FALSE)==0){
						if(blnSeGeneroCronogramaCorrectamente){
							creditoComp.setChkCredito(Boolean.TRUE);
							// Validamos que la configuracion del credito en la adenda este activo
							if(creditoComp.getCredito().getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0){
								Credito credito = creditoFacade.getCreditoPorIdCredito(creditoComp.getCredito().getId());
								
								blnYaTieneCredito = aplicaPromocional(beanSocioComp, credito);
								//log.error("DESCRIPCION DE CREDITO ************* "+credito.getStrDescripcion());
								//log.error("APLICA CREDITO ************* "+blnYaTieneCredito);
								
								// Si teiene creditosd anterioes no debe caer en promocionales
								if(blnYaTieneCredito){
									//if(credito.getIntParaTipoCreditoEmpresa().compareTo(Constante.PARAM_T_TIPOCREDITOEMPRESA_PROMOCIONAL)==0){
										credito = null;
									//}
								}else{
									Boolean blnNoAplica = Boolean.FALSE;
									blnNoAplica = validarExcepcionDiasPromocional(credito, beanSocioComp);
									if(blnNoAplica){
										credito = null;
									}
								}
								
								// Validamos cada credito
								if (credito != null){
									if(credito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) ==  0) {
										if (credito.getIntParaRolPk().compareTo(intTipoRelacion)==0) {
											if (beanSocioComp.getSocio().getSocioEstructura() != null) {
												boolean boFlag = false;
												for (int k = 0; k < beanSocioComp.getPersona().getNatural().getListaPerLaboral().size(); k++) {
													if (credito.getIntParaCondicionLaboralCod() == 
													(beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(k).getIntCondicionLaboral())) {
														boFlag = true;
													}
													if (credito.getIntParaCondicionLaboralCod().equals(
													(beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(k).getIntCondicionLaboral()))) {
														boFlag = true;
													}
												}
	
												if ((credito.getIntParaCondicionLaboralCod().compareTo(-1)==0)|| (boFlag)) {
													// Verificar la condición de la cuenta
													if (credito.getListaCondicion() != null) {
														for (CondicionCredito condicionCredito : credito.getListaCondicion()) {
															if(blnSeGeneroCronogramaCorrectamente){
																if (beanSocioComp.getCuenta().getIntParaCondicionCuentaCod().equals(
																condicionCredito.getId().getIntParaCondicionSocioCod())
																&& condicionCredito.getIntValor() == 1) {
																	// Verificar si la cuenta está activa o inactiva
																	if (credito.getListaCondicionHabil() != null) {
																		// forCondicionHabil:
																		for (CondicionHabil condicionHabil : credito.getListaCondicionHabil()) {
																			if(blnSeGeneroCronogramaCorrectamente){
																				// verificando condicion de regular
																				if (beanSocioComp.getCuenta().getIntParaSubCondicionCuentaCod().equals(condicionHabil.getId().getIntParaTipoHabilCod())
																				&& condicionHabil.getIntValor() == 1) {
			
																					Credito creditoRecuperado = null;
																					creditoRecuperado = validarConfiguracionCredito(beanSocioComp,credito);
																					if(blnSeGeneroCronogramaCorrectamente){
																						if(creditoRecuperado != null){
																							beanCredito = creditoRecuperado;
																							List<Credito> listaCreditosRecuperados = new ArrayList<Credito>();
																							listaCreditosRecuperados.add(creditoRecuperado);
			
																							boolean blnLast = calcularMontosCreditoValidado(listaCreditosRecuperados, estructuraDetalle);
																							if(blnLast){
																								generarCronograma(creditoRecuperado, estructuraDetalle);
																								msgTxtErrores = "";
																								blnEvaluacionCredito = true;
																								blnMostrarDescripciones = true;
																								mostrarArchivosAdjuntos(event);
																								blnSeGeneroCronogramaCorrectamente = false;
																							}else{
																								blnSeGeneroCronogramaCorrectamente = true;
																								blnEvaluacionCredito = false;
																								blnMostrarDescripciones = false;
																								intNroCuotas = 0;
																								evaluarCreditosComp(listaCreditosComp, event);
																							}
																						}else{
																							beanCredito = null;
																							if(msgTxtEvaluacionFinal.length() > 0){
																								msgTxtErrores = "";
																							}else{
																								msgTxtErrores = "No existen Creditos configurados o ninguno pasó las Validaciones de Monto, %Aporte, Excepción, etc.";
																							}
																							blnEvaluacionCredito = false;
																						}
																					}
																					msgTxtCondicionHabil ="";
																				} else{
																					msgTxtCondicionHabil = "No se superó la validación de Condición Hábil.";
																				}
																			}
																		}
																		msgTxtCondicionHabil = "";
																	} else {
																		msgTxtCondicionHabil = "No se superó la validación de Condición Hábil. ";
																	}
																}
															}
														}
														msgTxtCondicionLaboral = "";
													}else{msgTxtCondicionLaboral = "No se superó la validación de Condición Laboral.";}
												} else { msgTxtCondicionLaboral = "No se superó la validación de Condición Laboral.";}
											}
	
										}
									}
								}
							}
						}
					}
					msgTxtCreditoConvenio = "";
				}
			}
		} catch (Exception e) {
			log.error("Error en evaluarCreditosComp ---> "+e);
		}
		
	}

	
	/**
	 * Calcula los montos de:
	 * Monto solicitado (M)
	 *	% Gravamen (pG)
	 *	Gravamen (G)
	 *	% Aporte
	 *	Aporte(A)
	 *	Monto del Préstamo (MP).
	 * Ademas establece una ultima validacion de montos que podria dar pie a una reevaluacion.
	 * @param listaCreditosRecuperados
	 * @param estructuraDetalle
	 */
	private Boolean calcularMontosCreditoValidado(List<Credito> listaCreditosRecuperados, EstructuraDetalle estructuraDetalle) {
		Calendar dtToday = Calendar.getInstance();
		Boolean blnPasaUltimaValidacion = Boolean.TRUE;
		List<CreditoDescuento> listaDescuentos = null;
		Boolean blnContinuaDescuento = Boolean.TRUE;
		Credito credito = null;
		
		try {
			if (listaCreditosRecuperados.get(0) != null && !listaCreditosRecuperados.isEmpty()) {
				credito = listaCreditosRecuperados.get(0);

				if (credito.getListaDescuento() != null && ! credito.getListaDescuento().isEmpty()) {
					listaDescuentos = new ArrayList<CreditoDescuento>();
					listaDescuentos = credito.getListaDescuento();
					
					// 1. Recuperamos y calculamos los Descuentos:
					for (CreditoDescuento creditoDescuento : listaDescuentos) {
						if(blnContinuaDescuento){
							if ((dtToday.getTime().compareTo(creditoDescuento.getDtFechaIni()) > 0 
								&& creditoDescuento.getDtFechaFin() == null) 
								||(dtToday.getTime().compareTo(creditoDescuento.getDtFechaIni()) > 0 
								&& dtToday.getTime().compareTo(creditoDescuento.getDtFechaFin()) < 0)) {
								
								// Si existe un porcentaje o monto en el dscto
								if (creditoDescuento.getBdPorcentaje() != null) {
									blnContinuaDescuento = Boolean.FALSE;
									BigDecimal bdSolicitadoMasAnterior= BigDecimal.ZERO;
									bdSolicitadoMasAnterior = beanExpedienteCredito.getBdMontoSolicitado().add(bdMontoSaldoAnterior);
									bdTotalDstos = bdSolicitadoMasAnterior.multiply(creditoDescuento.getBdPorcentaje()).divide(new BigDecimal(100));
									bdTotalDstos = bdTotalDstos.divide(BigDecimal.ONE,2, RoundingMode.HALF_UP);
									//cgd-07.11.2013-represtamo
									bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoSolicitado().add(bdTotalDstos).add(bdMontoSaldoAnterior);
									strPorcAportes = ""+ creditoDescuento.getBdPorcentaje().divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
									bdMontoPrestamo = bdMontoTotalSolicitado;
									break;
								}else{
									bdTotalDstos = BigDecimal.ZERO;
									bdTotalDstos = bdTotalDstos.divide(BigDecimal.ONE,2, RoundingMode.HALF_UP);
									//cgd-07.11.2013-represtamo
									bdMontoTotalSolicitado = bdMontoTotalSolicitado.add(creditoDescuento.getBdMonto()).add(bdMontoSaldoAnterior);
									bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
									strPorcAportes = "0.00";
									bdMontoPrestamo = bdMontoTotalSolicitado;
								}
							}
						}
					}
				}else{
					bdTotalDstos = BigDecimal.ZERO;
					bdTotalDstos = bdTotalDstos.divide(BigDecimal.ONE,2, RoundingMode.HALF_UP);
					strPorcAportes = "0.00";
					//cgd-07.11.2013-represtamo
					bdMontoPrestamo = beanExpedienteCredito.getBdMontoSolicitado().add(bdTotalDstos.add(bdPorcSeguroDesgravamen)).add(bdMontoSaldoAnterior);
				}

				// 2. Recuperamos y calculamos la tasa de desgravamen:
				BigDecimal bdTasaSeguroDesgravamen;
				if (credito.getBdTasaSeguroDesgravamen() != null) {
					bdTasaSeguroDesgravamen = BigDecimal.ZERO;
					bdTasaSeguroDesgravamen = listaCreditosRecuperados.get(0).getBdTasaSeguroDesgravamen();
					
					listaCreditosRecuperados.get(0).setBdTasaSeguroDesgravamen(bdTasaSeguroDesgravamen.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP));
					BigDecimal bdSolicitadoMasAnterior= BigDecimal.ZERO;
					bdSolicitadoMasAnterior = beanExpedienteCredito.getBdMontoSolicitado().add(bdMontoSaldoAnterior);
					bdPorcSeguroDesgravamen = bdSolicitadoMasAnterior.multiply(bdTasaSeguroDesgravamen).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
					bdPorcSeguroDesgravamen = bdPorcSeguroDesgravamen.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
					bdMontoTotalSolicitado = bdMontoTotalSolicitado.add(bdPorcSeguroDesgravamen);
					bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
					//cgd-07.11.2013-represtamo
					bdMontoPrestamo = beanExpedienteCredito.getBdMontoSolicitado().add(bdTotalDstos.add(bdPorcSeguroDesgravamen)).add(bdMontoSaldoAnterior);

					beanCredito = listaCreditosRecuperados.get(0);
				}else{
					bdTasaSeguroDesgravamen = BigDecimal.ZERO;
					bdTasaSeguroDesgravamen = bdTasaSeguroDesgravamen.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
					bdPorcSeguroDesgravamen = BigDecimal.ZERO;
					bdPorcSeguroDesgravamen = bdPorcSeguroDesgravamen.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
					bdMontoTotalSolicitado = bdMontoTotalSolicitado.add(bdPorcSeguroDesgravamen);
					bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
					//cgd-07.11.2013-represtamo
					bdMontoPrestamo = beanExpedienteCredito.getBdMontoSolicitado().add(bdTotalDstos.add(bdPorcSeguroDesgravamen)).add(bdMontoSaldoAnterior);
					beanCredito = listaCreditosRecuperados.get(0);
					beanCredito.setBdTasaSeguroDesgravamen(BigDecimal.ZERO);
				}
				// 3. Se valida rangos de montos finales.
				if(bdMontoTotalSolicitado.compareTo(recuperarMinimo(credito)) >= 0
					&& bdMontoTotalSolicitado.compareTo(recuperarMaximo(credito)) <= 0){
						blnPasaUltimaValidacion = Boolean.TRUE;
				}else{
						blnPasaUltimaValidacion = Boolean.FALSE;
					}
			}
		} catch (Exception e) {
			blnEvaluacionCredito = false;
			log.error("Error Exception en validarRecuperados ---> " + e);
			e.printStackTrace();
		}
		return blnPasaUltimaValidacion;
	}
	
	
	/**
	 * Recupera el monto minimo que debe aplicarse a un credito
	 * @return
	 */
	public BigDecimal recuperarMinimo(Credito credito){
		BigDecimal bdMontoMinimo = BigDecimal.ZERO;
		try {
			if (credito.getBdMontoMinimo() != null) {
				bdMontoMinimo = credito.getBdMontoMinimo();
			}else{
				if(credito.getBdPorcMinimo() != null){
					bdMontoMinimo = (beanSocioComp.getCuentaComp().getBdTotalAporte().multiply(credito.getBdPorcMinimo())).divide(new BigDecimal(100));
				}	
			}
		} catch (Exception e) {
			log.error("Error en recuperarMinimo --> "+e);
		}
		return bdMontoMinimo;
	}
	
	
	/**
	 * Recupera el monto maximno que debe aplicarse a un credito
	 * @return
	 */
	public BigDecimal recuperarMaximo(Credito credito){
		BigDecimal bdMontoMaximo = BigDecimal.ZERO;
		
		try {
			if (credito.getBdMontoMaximo() != null) {
				bdMontoMaximo = credito.getBdMontoMaximo();
			}else{
				if(credito.getBdPorcMaximo() != null){
					bdMontoMaximo = (beanSocioComp.getCuentaComp().getBdTotalAporte().multiply(credito.getBdPorcMaximo())).divide(new BigDecimal(100));
				}	
			}
		} catch (Exception e) {
			log.error("Error en recuperarMinimo --> "+e);
		}
		return bdMontoMaximo;
	}
	
	
	/**
	 * Funciona en el modificar. LLama al generarCronograma()
	 * @param creditosRecuperado
	 * @param estructuraDetalle
	 */
	private void validarCreditoRecuperado(Credito creditosRecuperado, EstructuraDetalle estructuraDetalle) {
		
		try {
			Calendar dtToday = Calendar.getInstance();
			// Recoremos la lista de los creditos r4ecuperados
			if (creditosRecuperado.getListaDescuento() != null && creditosRecuperado.getListaDescuento().size() > 0) {
				for (CreditoDescuento creditoDescuento : creditosRecuperado.getListaDescuento()) {
					// El dscto sólo aplicará a los que estén vigentes
					if ((dtToday.getTime().compareTo(creditoDescuento.getDtFechaIni()) > 0 && creditoDescuento.getDtFechaFin() == null) 
						||(dtToday.getTime().compareTo(creditoDescuento.getDtFechaIni()) > 0 
						&& dtToday.getTime().compareTo(creditoDescuento.getDtFechaFin()) < 0)) {
						
						// Si existe un porcentaje o monto en el dscto
						if (creditoDescuento.getBdPorcentaje() != null) {
							BigDecimal bdMontoSolicitadoMasSaldoAnterior = BigDecimal.ZERO;
							bdMontoSolicitadoMasSaldoAnterior = bdMontoTotalSolicitado.add(bdMontoSaldoAnterior);
							bdTotalDstos = bdMontoSolicitadoMasSaldoAnterior.multiply(creditoDescuento.getBdPorcentaje()).divide(new BigDecimal(100));
							bdTotalDstos = bdTotalDstos.divide(BigDecimal.ONE,2, RoundingMode.HALF_UP);
							//bdMontoTotalSolicitado = bdMontoTotalSolicitado.add(bdTotalDstos);
							bdMontoTotalSolicitado = bdMontoSolicitadoMasSaldoAnterior.add(bdTotalDstos);
							strPorcAportes = ""+ creditoDescuento.getBdPorcentaje().divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
							break;
						} else {
							bdMontoTotalSolicitado = bdMontoTotalSolicitado.add(creditoDescuento.getBdMonto());
							bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
						}
					} else {
						System.out.println("Descuento : "+ creditoDescuento.getStrConcepto()+ "  esta fuera de rango "
								+ creditoDescuento.getStrDtFechaIni()
								+ creditoDescuento.getStrDtFechaFin());
					}
				}

			} else {
				// msgTxtErrores=" Los datos ingresados no cumplen con alguna configuración de Credito. ";
			}

			// Recuperamos la tasa de Desgravamen
			if (creditosRecuperado.getBdTasaSeguroDesgravamen() != null) {
				creditosRecuperado.setBdTasaSeguroDesgravamen(creditosRecuperado.getBdTasaSeguroDesgravamen().divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP));
				BigDecimal bdMontoSolicitadoMasAnterior = BigDecimal.ZERO;
				bdMontoSolicitadoMasAnterior= beanExpedienteCredito.getBdMontoSolicitado().add(bdMontoSaldoAnterior);
				bdPorcSeguroDesgravamen = bdMontoSolicitadoMasAnterior.multiply(creditosRecuperado.getBdTasaSeguroDesgravamen()).divide(new BigDecimal(100), 4, RoundingMode.HALF_UP);
				bdPorcSeguroDesgravamen = bdPorcSeguroDesgravamen.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
				bdMontoTotalSolicitado = bdMontoTotalSolicitado.add(bdPorcSeguroDesgravamen);
				bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
				bdMontoPrestamo = beanExpedienteCredito.getBdMontoSolicitado().add(bdTotalDstos.add(bdPorcSeguroDesgravamen).add(bdMontoSaldoAnterior));
			} else {
				// No se encontro tasa de desgravamen
			}

			// El interes se calcula en la creacion del crongrama;
			if (creditosRecuperado.getListaCreditoInteres() != null) {
				msgTxtInteresCredito = "";
			} else {
				msgTxtInteresCredito = "No existe Interés configurado en la configuración del Crédito. ";
			}

			bdMaxCapacidadPago = new BigDecimal(0);
			bdCapacidadPago = new BigDecimal(0);

			if (listaCapacidadCreditoComp != null || !listaCapacidadCreditoComp.isEmpty()) {
				for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
					if (capacidadCreditoComp.getCapacidadCredito() != null) {
						bdCapacidadPago = bdCapacidadPago.add(capacidadCreditoComp.getCapacidadCredito().getBdCapacidadPago());
						bdMaxCapacidadPago = bdCapacidadPago.multiply(new BigDecimal(0.9));
						bdMaxCapacidadPago = bdMaxCapacidadPago.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
					}
				}
			}
			beanCredito = creditosRecuperado;
			generarCronograma(beanCredito,estructuraDetalle);
		
		} catch (Exception e) {
			log.error("Error Exception en validarCreditoRecuperado --->  " + e);
		} finally {
			blnEvaluacionCredito = true;
		}
	}

	
	
	/**
	 * Genera el cronograma del credito validado.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public void generarCronograma(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle) {
		Calendar miCal = Calendar.getInstance();
		CronogramaCredito cronogramaCredito = null;
		CronogramaCreditoComp cronogramaCreditoComp = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		Integer intNroCuotasOld = 0;
		BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
		Integer intCuotaNegativa;
		try {
			// Se trabajara sobre la fecha de registro
			log.info(bdMontoInteresAtrasado);
			log.info("listaCronogramaCreditoComp: "+listaCronogramaCreditoComp);
			miCal.clear();
			miCal.setTime(Constante.sdf.parse(strFechaRegistro));
			cronogramaCredito = new CronogramaCredito();
			cronogramaCreditoComp = new CronogramaCreditoComp();
			listaCronogramaCredito = new ArrayList<CronogramaCredito>();
			if (creditoSeleccionado.getListaCreditoInteres() != null) {
				for (CreditoInteres creditoInteres : creditoSeleccionado.getListaCreditoInteres()){
					if(blnSeGeneroCronogramaCorrectamente){
						limpiarCronograma();
						if(creditoInteres.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							if(creditoInteres.getIntMesMaximo() != null){
								if(creditoInteres.getBdTasaInteres() == null || creditoInteres.getBdTasaInteres().compareTo(BigDecimal.ZERO)==0){
									bdPorcentajeInteres = BigDecimal.ZERO;
								}else{
									bdPorcentajeInteres = creditoInteres.getBdTasaInteres();
								}
								// Se valida si el socio es Activo o cesante
								if(creditoInteres.getIntParaTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0){
									if(blnSeGeneroCronogramaCorrectamente){
										intCuotasMaximas = creditoInteres.getIntMesMaximo();
										if (intNroCuotas == null || intNroCuotas == 0 || (intNroCuotas < 0)){
											intNroCuotas = creditoInteres.getIntMesMaximo();
											msgTxtExesoCuotasCronograma = "";
										}else{
											intNroCuotasOld = intNroCuotas;
											msgTxtExesoCuotasCronograma = "";
											if(intCuotasMaximas.compareTo(intNroCuotas)<0){
												intNroCuotas = creditoInteres.getIntMesMaximo();
												msgTxtExesoCuotasCronograma = "El número de Cuotas ingresada("+ intNroCuotasOld+") " + "excede al número configurado para este Crédito ("+
																			  creditoInteres.getIntMesMaximo()+ "). Se colocará el nro por defecto.";
											}
										}
										Integer intNroCuotasTemp = intNroCuotas;
										for (int j = intNroCuotas; j > 0; j--) {
											intCuotaNegativa = 0;
											// CGD-21.01.2014-YT
											List<String> listaDiasEnvio = new ArrayList<String>();
											List<String> listaDiasVencimiento = new ArrayList<String>();
											int vencMes, vencAnno; //vencDia,
											int envAnno; //envDia, envMes, 
											//Partimos a fecha registro en dia, mes y año
//											envDia = new Integer(strFechaRegistro.substring(0,2));
//											envMes = new Integer(strFechaRegistro.substring(3,5));
											envAnno = new Integer(strFechaRegistro.substring(6,10));
											//Calculo de la 1era Fecha de Vencimiento
											String strVencimiento = calcular1raFechaVencimiento(estructuraDetalle);
//											vencDia = new Integer(strVencimiento.substring(0,2));
											vencMes = new Integer(strVencimiento.substring(3,5));
											vencAnno = new Integer(strVencimiento.substring(6,10));
											
											// SE GENENERA LA LISTA DE LOS DIAS DE VENCIMIENTO listaDiasVencimiento
											for (int i = 0; i < intNroCuotasTemp; i++) {
												Calendar nuevoDia = Calendar.getInstance();
												if(i==0){
													listaDiasEnvio.add(i, strFechaRegistro);
													listaDiasVencimiento.add(i,strVencimiento);
												}else{										
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

											BigDecimal bdCuotaFinal = new BigDecimal(0);

											BigDecimal bdTea = BigDecimal.ZERO;
											if(bdPorcentajeInteres.compareTo(BigDecimal.ZERO)==0){
												bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres)).pow(12)).subtract(BigDecimal.ONE).setScale(4,
												RoundingMode.HALF_UP));
											}else{
												bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres.divide(new BigDecimal(100)))).pow(12)).subtract(BigDecimal.ONE).setScale(4,
												RoundingMode.HALF_UP));
											}

											// SE GENENERA LA LISTA DE LA DIEFERENICA DE DIAS diferenciaEntreDias
											List<Integer> listaDias = new ArrayList<Integer>(); // Lista que guarda la
											List<Integer> listaSumaDias = new ArrayList<Integer>(); // Lista que guarda la
											// sumatoria de dias.
											int diferenciaEntreDias = 0;
											int sumaDias = 0;

											for (int i = 0; i < intNroCuotasTemp; i++) {
												// calculando diferencia entre el 1er vencimeitno y la fecha de hoy
												if (i == 0) {
													diferenciaEntreDias = fechasDiferenciaEnDias((StringToCalendar(strFechaRegistro)).getTime(), 
																								 (StringToCalendar(strVencimiento)).getTime());
													listaDias.add(i, diferenciaEntreDias);
												} else {
													Calendar calendario = Calendar.getInstance();
													@SuppressWarnings("deprecation")
													Calendar calend = new GregorianCalendar(
													(getPrimerDiaDelMes(StringToCalendar(listaDiasEnvio.get(i).toString()))).getYear(),
													getPrimerDiaDelMes(StringToCalendar(listaDiasEnvio.get(i).toString())).getMonth(), 1);

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

											BigDecimal bdSumatoria = new BigDecimal(0);
											System.out.println("TEA" + bdTea);

											for (int i = 0; i < intNroCuotasTemp; i++) {
												BigDecimal bdCalculo1 = new BigDecimal(0);
												BigDecimal bdCalculo2 = new BigDecimal(0);
												BigDecimal bdCalculo3 = new BigDecimal(0);
												BigDecimal bdCalculo4 = new BigDecimal(0);
												BigDecimal bdResultado = new BigDecimal(0);
												BigDecimal bdUno = BigDecimal.ONE;
												double dbResultDenom = 0;
												bdCalculo1 = new BigDecimal(listaSumaDias.get(i).toString()); // suma de dias
												bdCalculo2 = new BigDecimal(360); // 360
												bdCalculo3 = bdTea.add(bdUno); // tea + 1
												bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
												dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
												bdResultado = bdUno.divide(new BigDecimal(dbResultDenom),4, RoundingMode.HALF_UP);
												bdSumatoria = bdSumatoria.add(bdResultado);
											}

											bdCuotaFinal = bdMontoTotalSolicitado.divide(bdSumatoria, 4,RoundingMode.HALF_UP);
											System.out.println("CUOTA FINAL " + bdCuotaFinal);

											// Calculando Interes, Amortizacion, Saldo y la cuota mensual total y se conforma el cronograma
											BigDecimal bdAmortizacion = new BigDecimal(0);
											BigDecimal bdSaldo = new BigDecimal(0);
											BigDecimal bdSaldoMontoCapital = new BigDecimal(0);
											// BigDecimal bdMontoCocepto = new BigDecimal(0);
											BigDecimal bdSumaAmortizacion = new BigDecimal(0);
											bdSaldo = bdMontoTotalSolicitado; // se inicializa el saldo con
											// el monto solicitado
											BigDecimal bdSaldoTemp = new BigDecimal(0);
											List<BigDecimal> listaSaldos = new ArrayList<BigDecimal>();

											//SE FORMA LA LISTA DEL CRONOGRAMA
											for (int i = 0; i < intNroCuotasTemp; i++) {
												BigDecimal bdCalculo1 = new BigDecimal(0);
												BigDecimal bdCalculo2 = new BigDecimal(0);
												BigDecimal bdCalculo3 = new BigDecimal(0);
												BigDecimal bdCalculo4 = new BigDecimal(0);
												BigDecimal bdCalculo5 = new BigDecimal(0);
												BigDecimal bdInteresCuota = new BigDecimal(0);
												BigDecimal bdUno = BigDecimal.ONE;
												double dbResultDenom = 0;
												bdCalculo1 = new BigDecimal(listaDias.get(i).toString()); // suma de dias
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

												if(bdSaldo.compareTo(bdAmortizacion)<0){
													bdSaldo = BigDecimal.ZERO;
												}else{
													bdSaldo = bdSaldo.subtract(bdAmortizacion);
													bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
												}

												listaSaldos.add(i, bdSaldo);
												System.out.println("SALDO   " + bdSaldo);

												if (i + 1 == intNroCuotasTemp) {
													bdSaldo = BigDecimal.ZERO;
													BigDecimal bdSaldoRed = new BigDecimal(0);

													if (intNroCuotasTemp == 1) {
														bdAmortizacion = bdMontoTotalSolicitado;
													} else {
														bdSaldoRed = new BigDecimal(listaSaldos.get(i - 1).toString());
														bdAmortizacion = bdSaldoRed;
													}
												} else {
													bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
													bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE,4, RoundingMode.HALF_UP);
												}
												//Validacion para evitar que la primera cuota sea negativa
												if (bdAmortizacion.compareTo(BigDecimal.ZERO)==-1) {
													intCuotaNegativa = 1;
													intNroCuotasTemp--;
													break;
												}
												bdCuotaMensual = bdAmortizacion.add(bdInteresCuota);
												bdAportes = bdAportes.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
												
							// Formando el cronograma comp que es lo que se visualiza en popup
												cronogramaCreditoComp = new CronogramaCreditoComp();
												cronogramaCreditoComp.setStrFechaEnvio(Constante.sdf.format((StringToCalendar(listaDiasEnvio.get(i)	.toString())).getTime()));
												cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
												cronogramaCreditoComp.setIntDiasTranscurridos(new Integer(listaDias.get(i).toString()));
												cronogramaCreditoComp.setBdSaldoCapital(bdSaldo);
												cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
												cronogramaCreditoComp.setBdInteres(bdInteresCuota);
												cronogramaCreditoComp.setBdCuotaMensual(bdCuotaMensual);
												cronogramaCreditoComp.setBdAportes(bdAportes);
												cronogramaCreditoComp.setBdTotalCuotaMensual(bdCuotaMensual.add(bdAportes));
												listaCronogramaCreditoComp.add(cronogramaCreditoComp);

												
							// Agregando el tipo de Concepto - "Amortización"
												cronogramaCredito = new CronogramaCredito();
												cronogramaCredito.setId(new CronogramaCreditoId());
												cronogramaCredito.setIntNroCuota(i + 1);
												cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
												cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
												cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
												if (i == 0) {
													bdSaldoMontoCapital = bdMontoTotalSolicitado;
												} else {
													bdSaldoMontoCapital = bdSaldoTemp;
												}
												bdSaldoTemp = bdSaldo;
												cronogramaCredito.setBdMontoCapital(bdSaldoMontoCapital);
												if (i + 1 == intNroCuotasTemp) {
													cronogramaCredito.setBdMontoConcepto(bdSaldoMontoCapital);
												} else {
													cronogramaCredito.setBdMontoConcepto(i + 1 == intNroCuotasTemp ? 
													bdAmortizacion.add(bdMontoTotalSolicitado.subtract(bdAmortizacion)): bdAmortizacion);
												}
												Date fechaVenc = new Date();
												fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();
												cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
												cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
												cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
												// CGD-12.11.2013 - nuevos atributos
												cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
												cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
												cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
												cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
												Calendar clEnvio= Calendar.getInstance();
												clEnvio = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
												cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio.getTime().getTime()));
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
												// CGD-12.11.2013 - nuevos atributos
												cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
												cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
												cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
												cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
												Calendar clEnvio2= Calendar.getInstance();
												clEnvio2 = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
												cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio2.getTime().getTime()));
												listaCronogramaCredito.add(cronogramaCredito);
											}
											//cortamos el for si hay cuotas negativas
											if (intCuotaNegativa.compareTo(1)!=0) {
												bdTotalCuotaMensual = bdCuotaFinal.add(bdAportes);
//												Boolean bln90Por = Boolean.FALSE;

												if (listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()) {
													if (listaCapacidadCreditoComp.size() == 1) {
														for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
														capacidadCreditoComp.getCapacidadCredito().setBdCuotaFija(bdTotalCuotaMensual);
														}
													}else{
														// en el caso que existan mas capacidades
													}
												}

												// Se se calcula y definen los valores de: strPorcInteres ,  PorcentajeGravamen, strPorcAportes, 
												strPorcInteres = "" + bdPorcentajeInteres.divide(	BigDecimal.ONE, 2,RoundingMode.HALF_UP);
												// seteamos ultimos valores
												beanExpedienteCredito.setBdPorcentajeInteres(new BigDecimal(strPorcInteres));
												beanExpedienteCredito.setBdPorcentajeGravamen(beanCredito.getBdTasaSeguroDesgravamen());
												beanExpedienteCredito.setBdPorcentajeAporte(new BigDecimal(strPorcAportes));
												beanExpedienteCredito.setBdMontoTotal(bdMontoPrestamo.equals(BigDecimal.ZERO)?beanExpedienteCredito.getBdMontoSolicitado().add(bdTotalDstos.add(bdPorcSeguroDesgravamen).add(bdMontoSaldoAnterior)):bdMontoPrestamo);
												beanExpedienteCredito.setBdMontoGravamen(bdPorcSeguroDesgravamen);
												beanExpedienteCredito.setBdMontoAporte(bdTotalDstos); // % de monto solicitado
												beanExpedienteCredito.setIntNumeroCuota(intNroCuotasTemp);
												
												blnSeGeneroCronogramaCorrectamente = false;
												Boolean blnValidaMontosCap = Boolean.FALSE;
												blnValidaMontosCap = validarMontosCapacidadesCredito(bdTotalCuotaMensual);

												if(blnValidaMontosCap){
													msgTxtCuotaMensual = "";
													beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);
													blnEvaluacionCredito = true;
												}else{
													blnEvaluacionCredito = true;
													listaCronogramaCreditoComp.clear();
													listaCronogramaCredito.clear();
												}
												intNroCuotas = intNroCuotasTemp;
												break;
											}
										}
									}else{
										blnSeGeneroCronogramaCorrectamente = true;
										log.error("El creditoInteres.getIntParaTipoSocio() ---> "+creditoInteres.getIntParaTipoSocio());
										strPorcInteres = "0.00";
									}
								}
							}else{
								log.error("creditoInteres.getIntMesMaximo() es nulo --> "+ creditoInteres.getIntMesMaximo());
							}
						}else{
							log.error("NO HAY NADA EN creditoSeleccionado.getListaCreditoInteres() "+ creditoSeleccionado.getListaCreditoInteres().size());
						}
					}
				}
			} else {
				strPorcInteres = "0.00";
				log.error("NO HAY NADA EN creditoSeleccionado.getListaCreditoInteres() "+ creditoSeleccionado.getListaCreditoInteres().size());
			}
		} catch (Exception e) {
			log.error("Error BusinessException en generarCronograma ---> "+e);
			e.printStackTrace();
		}
	}

	public void chekeandoListaCronograma(){
		log.info("lista cache: "+listaCronogramaCreditoComp);
	}
	/**
	 * Recalcula el monto de las cuotas fijas de cada capacidad 
	 * En el caso que exista n-1 capacidades con Cuota Fija ingresada.
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
	 * Valida la suma de las cuotas fijas de las capacidadaes sean menor al 90/100
	 * Y la suma de estas se igual a la cuotra mensual calculada.
	 * Recalcula los montos de las cuotas fijas.
	 * Retorna TRUE cuando pasa y FALSE cuando incumple.
	 * @param listaCapacidadComp
	 * @param bdMontoCuotaFija
	 * @return
	 */
	public Boolean validarMontosCapacidadesCredito(BigDecimal bdCuotaMensualCalc){
		Boolean blnContinua = Boolean.TRUE;
//		Boolean blnEsElUltimo = Boolean.FALSE;
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

						if(!bdSumaCuotasFijas.equals(bdCuotaMensualCalc)){
							msgTxtEstrucActivoRepetido = "La Sumatoria de las Cuotas Fijas (S/. "+ bdSumaCuotasFijas + " ) debe ser igual al Valor de la Cuota Mensual Calculada (S/. "+bdCuotaMensualCalc+"). ";
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
	 * Muestra dinamicamente los ventanas para adjuntar documentos de Solicitud
	 * de Credito. En base a la cofiguracion del credito.
	 * @param event
	 */
	public void mostrarArchivosAdjuntos(ActionEvent event) {
		ConfServSolicitud confServSolicitud = null;
//		String strToday = Constante.sdf.format(new Date());
//		Date dtToday = null;
		List<ConfServSolicitud> listaDocAdjuntos = null;
		EstructuraDetalle estructuraDet = null;
		List<EstructuraDetalle> listaEstructuraDet = null;
		listaRequisitoCreditoComp = null;
		RequisitoCreditoComp requisitoCreditoComp;
		
		ConfSolicitudFacadeRemote facade = null;
		TablaFacadeRemote tablaFacade = null;
		EstructuraFacadeRemote estructuraFacade = null;
		
		try {
//			dtToday = Constante.sdf.parse(strToday);
			
			listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
			listaEstructuraDet = new ArrayList<EstructuraDetalle>();
			listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		
			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			
			confServSolicitud = new ConfServSolicitud();
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);
			confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_PRESTAMO);
			confServSolicitud.setIntParaSubtipoOperacionCod(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO);
			listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, Constante.PARAM_T_TIPOREQAUT_REQUISITO, beanCredito);
			
			if (listaDocAdjuntos != null && !listaDocAdjuntos.isEmpty()) {
				
				forSolicitud: 
				for (ConfServSolicitud solicitud : listaDocAdjuntos) {
					if (solicitud.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)) {
						if (solicitud.getIntParaSubtipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO)) {
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
														/*System.out.println("req aut -----> "+solicitud.getId().getIntItemSolicitud());
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
															&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())){
															//&& estructuraDetalle.getIntTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0){
															
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
																		listaRequisitoCreditoCompTemp.add(requisitoCreditoComp);
																	}
																}
																													
																List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();
																
																// validamos que solo se muestre las de agrupamioento A.
																listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO), "A");
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
//		} catch (ParseException e1) {
//			log.error("Error ParseException en mostrarArchivosAdjuntos ---> "+e1);
//			e1.printStackTrace();
		} catch (BusinessException e2) {
			log.error("Error BusinessException en mostrarArchivosAdjuntos ---> "+e2);
			e2.printStackTrace();
		} catch (EJBFactoryException e3) {
			log.error("Error EJBFactoryException en mostrarArchivosAdjuntos ---> "+e3);
			e3.printStackTrace();
		}
	}

	
	/**
	 * Relacionado al link para descargar la imagen de cada archivo adjunto de la solicitud.
	 * @param event
	 */
	public void descargarDocumento(ActionEvent event) {
		TipoArchivo tipoArchivo = null;
		String strParaTipoCod = getRequestParameter("intParaTipoCod");
		Integer intParaTipoCod = new Integer(strParaTipoCod);
		String strNombreArchivo = getRequestParameter("strNombreArchivo");
		log.info("strTipoArchivo: " + intParaTipoCod);
		log.info("strNombreArchivo: " + strNombreArchivo);
		try {
			tipoArchivo = generalFacade.getTipoArchivoPorPk(intParaTipoCod);
			if (tipoArchivo != null) {
				DownloadFile.downloadFile(tipoArchivo.getStrRuta() + "\\"+ strNombreArchivo);
			}
		} catch (Exception e) {
			log.error("Error Exception en descargarDocumento ---> "+e);
			e.printStackTrace();
		}
	}

	
	/**
	 * Pinta la imagen miniatura de cada adjunto para la solicitud.
	 * Adjuntos mapeados (21):
	 * CARTADESCUENTO, AUTORIZACIONDSCTO, BOLETAPAGO, COPIADNI, PAGARE, AUTORIZACIONPORINCENTLABORALES, CONTRATOLABORALVIGENTE, 
	 * AUTORIZACIONVISADAPORBIENESTARDEPERSONAL, DECLARACIONJURADA, CRONOGRAMADEPAGO, ESTADODECUENTA, RESTAMO_RECIBODESERVICIOS, 
	 * RECIBOSSERVICIOS, VERIFICACION_DOMICILIARIA, BOUCHER_PAGO, CTS_SOCIO, CTS_GARANTE, CARTA_COMPROMISO, LETRA_DE_CAMBIO, 
	 * FICHA_REGISTRAL, ADJUNTOS_CREDITOS.
	 * @param event
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 * @throws IOException
	 */
	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException {
		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {

		// PRESTAMO_CARTADESCUENTO
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTADESCUENTO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}

				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTADESCUENTO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}

		// PRESTAMO_AUTORIZACIONDSCTO
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONDSCTO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONDSCTO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}

		// BOLETAPAGO
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOLETAPAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOLETAPAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}
				
		// PRESTAMO_COPIADNI
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_COPIADNI)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_COPIADNI)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}

				}
				
		// PRESTAMO_PAGARE
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_PAGARE)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_PAGARE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}
				
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_PAGARE)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_PAGARE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}

		// AUTORIZACIONPORINCENTLABORALES	
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTINCENTIVOLABORAL)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONPORINCENTLABORALES)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTINCENTIVOLABORAL)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONPORINCENTLABORALES)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}

		// PRESTAMO_CONTRATOLABORALVIGENTE			
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONTRATOLABORAL)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CONTRATOLABORALVIGENTE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONTRATOLABORAL)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CONTRATOLABORALVIGENTE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
							break;
					}
				}

		// PRESTAMO_AUTORIZACIONVISADAPORBIENESTARDEPERSONAL
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTVISADABIENESTARSOCIAL)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONVISADAPORBIENESTARDEPERSONAL)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())	
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTVISADABIENESTARSOCIAL)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONVISADAPORBIENESTARDEPERSONAL)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}

		// PRESTAMO_DECLARACIONJURADA
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DECLARACIONJURADA)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_DECLARACIONJURADA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_DECLARACIONJURADA)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_DECLARACIONJURADA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}

		// PRESTAMO_CRONOGRAMADEPAGO
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CRONOGRAMA)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CRONOGRAMADEPAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CRONOGRAMA)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CRONOGRAMADEPAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				
		// PRESTAMO_ESTADODECUENTA
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ESTADOCUENTA)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ESTADODECUENTA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ESTADOCUENTA)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ESTADODECUENTA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}

		// RESTAMO_RECIBODESERVICIOS
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_RECIBODESERVICIOS)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_RECIBODESERVICIOS)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}

		// RECIBOSSERVICIOS
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_RECIBODESERVICIOS)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_RECIBODESERVICIOS)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}

		// PRESTAMO_VERIFICACION_DOMICILIARIA
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_VERIFICAION_DOMICILIO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_VERIFICACION_DOMICILIARIA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_VERIFICAION_DOMICILIO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_VERIFICACION_DOMICILIARIA)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				
		// PRESTAMO_BOUCHER_PAGO
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOUCHER_PAGO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOUCHER_PAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOUCHER_PAGO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOUCHER_PAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}

		// PRESTAMO_CTS_SOCIO
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CTS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_SOCIO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CTS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_SOCIO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				
				
		// PRESTAMO_CTS_GARANTE
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CTS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_GARANTE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CTS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_GARANTE)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				
				
		// PRESTAMO_CARTA_COMPROMISO
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTACOMROMISO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTA_COMPROMISO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTACOMROMISO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTA_COMPROMISO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				
		// PRESTAMO_LETRA_DE_CAMBIO
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_LETRA_CAMBIO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_LETRA_DE_CAMBIO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_LETRA_CAMBIO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_LETRA_DE_CAMBIO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
						
		// PRESTAMO_FICHA_REGISTRAL
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FICHA_REGISTRAL)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_REGISTRAL)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_FICHA_REGISTRAL)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_REGISTRAL)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				
				
		// ADJUNTOS_CREDITOS
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADJUNTOS_CREDITOS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ADJUNTOS_DEL_CREDITO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
					}
				}
				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADJUNTOS_CREDITOS)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ADJUNTOS_DEL_CREDITO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
							file.setData(byteImg);
							requisitoCreditoComp.setFileDocAdjunto(file);
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
	public void paintImage(OutputStream stream, Object object)throws IOException {
		stream.write(((MyFile) object).getData());
	}

	
	/**
	 * Asociado al boton Adjuntoar para subir documento adjunto.
	 * @param event
	 */
	public void adjuntarDocumento(ActionEvent event) {
		String strParaTipoDescripcion = getRequestParameter("intParaTipoDescripcion");
		String strParaTipoOperacionPersona = getRequestParameter("intParaTipoOperacionPersona");
		Integer intParaTipoDescripcion = new Integer(strParaTipoDescripcion);
		Integer intParaTipoOperacionPersona = new Integer(strParaTipoOperacionPersona);
		this.intParaTipoDescripcion = intParaTipoDescripcion;
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;

		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		fileupload.setStrDescripcion("Seleccione el archivo que desea adjuntar.");
		fileupload.setFileType(FileUtil.imageTypes);
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;

		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {
				
			// PRESTAMO_CARTADESCUENTO
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTADESCUENTO)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO);
				}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTADESCUENTO)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_SUSTENTOPRESTAMO);
				}
			// PRESTAMO_AUTORIZACIONDSCTO
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONDSCTO)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo, intItemHistorico, Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO);
				}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONDSCTO)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTORIZACIONDSCTO);
				}

			// PRESTAMO_BOLETAPAGO
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOLETAPAGO)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO);
				}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOLETAPAGO)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO);
				}
				
			// PRESTAMO_COPIADNI
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_COPIADNI)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,	Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
				}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_COPIADNI)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
				}

			// PRESTAMO_PAGARE
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_PAGARE)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_PAGARE);
				}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_PAGARE)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_PAGARE);
				}
				
			// AUTORIZACIONPORINCENTLABORALES
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONPORINCENTLABORALES)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTINCENTIVOLABORAL);
				}
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONPORINCENTLABORALES)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTINCENTIVOLABORAL);
				}

		//---PRESTAMO_CONTRATOLABORALVIGENTE		
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CONTRATOLABORALVIGENTE)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONTRATOLABORAL);
				}
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CONTRATOLABORALVIGENTE)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONTRATOLABORAL);
				}

		//---	AUTORIZACIONVISADAPORBIENESTARDEPERSONAL	
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONVISADAPORBIENESTARDEPERSONAL)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTVISADABIENESTARSOCIAL);
				}
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_AUTORIZACIONVISADAPORBIENESTARDEPERSONAL)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_AUTVISADABIENESTARSOCIAL);
				}
				
		//--- PRESTAMO_DECLARACIONJURADA
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_DECLARACIONJURADA)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DECLARACIONJURADA);
				}
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_DECLARACIONJURADA)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DECLARACIONJURADA);
				}
				
		//---PRESTAMO_CRONOGRAMADEPAGO
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CRONOGRAMADEPAGO)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,	Constante.PARAM_T_TIPOARCHIVOADJUNTO_CRONOGRAMA);
				}
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CRONOGRAMADEPAGO)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CRONOGRAMA);
				}

		//--- PRESTAMO_ESTADODECUENTA
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ESTADODECUENTA)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_ESTADOCUENTA);
				}
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ESTADODECUENTA)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_ESTADOCUENTA);
				}

		//--- PRESTAMO_RECIBODESERVICIOS
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_RECIBODESERVICIOS)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS);
				}
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_RECIBODESERVICIOS)
					&& intParaTipoOperacionPersona.equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_RECIBOSSERVICIOS);
				}
				

				// CGD-06.01.2013
		//--- PRESTAMO_VERIFICACION_DOMICILIARIA - 15
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_VERIFICACION_DOMICILIARIA)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {	
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_VERIFICAION_DOMICILIO);
					}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_VERIFICACION_DOMICILIARIA)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_VERIFICAION_DOMICILIO);
				}
				
		//--- PRESTAMO_BOUCHER_PAGO - 16
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOUCHER_PAGO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
						
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOUCHER_PAGO);
					}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOUCHER_PAGO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOUCHER_PAGO);
				}
				
		//--- PRESTAMO_CTS_SOCIO - 17
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_SOCIO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
						
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CTS);
					}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_SOCIO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CTS);
				}
				
		//--- PRESTAMO_CTS_GARANTE - 18
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_GARANTE)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CTS);
					}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CTS_GARANTE)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CTS);
				}
				
				
		//--- PRESTAMO_CARTA_COMPROMISO - 19
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTA_COMPROMISO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTACOMROMISO);
					}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_CARTA_COMPROMISO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_CARTACOMROMISO);
				}
				
		//--- PRESTAMO_LETRA_DE_CAMBIO - 20
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_LETRA_DE_CAMBIO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_LETRA_CAMBIO);
					}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_LETRA_DE_CAMBIO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_LETRA_CAMBIO);
				}
				
		//--- PRESTAMO_FICHA_REGISTRAL - 21
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_REGISTRAL)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_FICHA_REGISTRAL);
					}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_REGISTRAL)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_FICHA_REGISTRAL);
				}
				
		//--- PRESTAMO_ADJUNTOS_DEL_CREDITO - 22
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ADJUNTOS_DEL_CREDITO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADJUNTOS_CREDITOS);
					}
				if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp	.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_ADJUNTOS_DEL_CREDITO)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_GARANTE)) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_ADJUNTOS_CREDITOS);
				}
			}
		}
		fileupload.setStrJsFunction("putFileDocAdjunto()");
	}


	/**
	 * Valida condicion, habil de garantes.
	 * @param event
	 */
	public void validarGarante(ActionEvent event) {
		SocioComp socioCompGarante = null;
		Integer nroMaxAsegurados = null;
		
		try {
			socioCompGarante = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
																		strNroDocumento, usuario.getEmpresa().getIntIdEmpresa());
			
			msgTxtPersonaGaranteNoExiste = "";
			msgTxtCuentaGarante = "";
			msgTxtRolSocioGarante = "";
			msgTxtSocioEstructuraGarante ="";
			msgTxtSocioEstructuraOrigenGarante = "";
			msgTxtTipoGarantiaPersonal = "";
			msgTxtYaExisteGaranteCOnfiguracionIgual = "";
			msgTxtCondicionSocioNingunoGarante = "";
			msgTxtCondicionSocioGarante = "";
			msgTxtCondicionHabilGarante = "";
			msgTxtCondicionLaboralGaranteNinguno = "";
			msgTxtCondicionLaboralGarante = "";
			msgTxtSituacionLaboralGaranteNinguno = "";
			msgTxtSituacionLaboralGarante = "";
			msgTxtMaxNroGarantes = "";
			msgTxtDescuentoJudicial = "";
			
			if (socioCompGarante != null && beanCredito != null) {
				msgTxtPersonaGaranteNoExiste = "";
				if (socioCompGarante.getCuenta() != null) {
					msgTxtCuentaGarante = "";
					if (socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol() != null 
						&& !socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol().isEmpty()) {
						msgTxtRolSocioGarante = "";
						
						for (PersonaRol personaRol : socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol()) {
							if (personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
								msgTxtRolSocioGarante = "";
								if (socioCompGarante.getSocio().getListSocioEstructura() != null
									&& !socioCompGarante.getSocio().getListSocioEstructura().isEmpty()) {
										msgTxtSocioEstructuraGarante = "";
									
										forEstructuraGarante: 
										for (SocioEstructura socioEstructura : socioCompGarante.getSocio().getListSocioEstructura()) {
											if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
												msgTxtSocioEstructuraOrigenGarante = "";
												socioCompGarante.getSocio().setSocioEstructura(socioEstructura);
												
												//if(garantiasPersonales != null){
													//if (beanCredito.getListaGarantiaPersonal() != null
												//	&& !beanCredito.getListaGarantiaPersonal().isEmpty()) {
												//	for (CreditoGarantia garantiaPersonal : beanCredito.getListaGarantiaPersonal()) {
														//if (garantiaPersonal.getId().getIntParaTipoGarantiaCod().equals(Constante.CREDITOS_GARANTIA_PERSONAL)) {
													//		msgTxtTipoGarantiaPersonal = ""; 
															//intItemCreditoGarantia = garantiasPersonales.getId().getIntItemCreditoGarantia();
															//garantiaPersonal = creditoGarantiaFacade.getCreditoGarantiaPorIdCreditoGarantia(garantiaPersonal.getId());
															
															if (garantiasPersonales != null) {
																msgTxtTipoGarantiaPersonal = "";
																if (garantiasPersonales.getListaTipoGarantia() != null && !garantiasPersonales.getListaTipoGarantia().isEmpty()) {
																	for (CreditoTipoGarantia creditoTipoGarantia : garantiasPersonales.getListaTipoGarantia()) {
																		
																		// Validamos la preexistencia del credito tiopi garantia en los garantes ya registrados.
																		Boolean blnYaSeEncuentraRegistrado = Boolean.TRUE;
																		if(listaGarantiaCreditoComp != null && !listaGarantiaCreditoComp.isEmpty()) {
																			blnYaSeEncuentraRegistrado = validarPreExistencia(creditoTipoGarantia);	
																		}
																		
																		if(blnYaSeEncuentraRegistrado){
																			msgTxtYaExisteGaranteCOnfiguracionIgual = "";
																			msgTxtCondicionSocioNingunoGarante = "";
																			if (creditoTipoGarantia.getListaCondicionSocio() != null) {
																				msgTxtCondicionSocioNingunoGarante = "";
																				
																				for (CondicionSocioTipoGarantia condicionSocio : creditoTipoGarantia.getListaCondicionSocio()) {
																					if (socioCompGarante.getCuenta().getIntParaCondicionCuentaCod().equals(
																						condicionSocio.getId().getIntParaCondicionSocioCod())&& condicionSocio.getIntValor() == 1) {
																						msgTxtYaExisteGaranteCOnfiguracionIgual = "";
																						msgTxtCondicionSocioNingunoGarante = "";
																						msgTxtCondicionSocioGarante = "";
																						if (creditoTipoGarantia.getListaTipoCondicion() != null) {
																							msgTxtCondicionHabilGarante = "";
																							
																							for (CondicionHabilTipoGarantia condicionHabil : creditoTipoGarantia.getListaTipoCondicion()) {
																								if ((socioCompGarante.getCuenta().getIntParaSubCondicionCuentaCod().compareTo(condicionHabil.getId().getIntParaTipoHabilCod())==0)
																									&& condicionHabil.getIntValor() == 1) {
																									msgTxtCondicionHabilGarante = "";
																									if (creditoTipoGarantia.getListaCondicionLaboral() != null) {
																										msgTxtCondicionLaboralGaranteNinguno = "";
																										
																										for (CondicionLaboralTipoGarantia condicionLaboral : creditoTipoGarantia.getListaCondicionLaboral()) {
																											if (socioCompGarante.getPersona().getNatural().getPerLaboral() != null) {
																												msgTxtCondicionLaboralGarante = "";
																												if ((socioCompGarante.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(
																													condicionLaboral.getId().getIntParaCondicionLaboralCod())
																													||socioCompGarante.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(-1))
																													&& condicionLaboral.getIntValor() == 1) {
																													msgTxtCondicionLaboralGarante = "";

																													if (creditoTipoGarantia.getListaSituacionLaboral() != null) {
																														msgTxtSituacionLaboralGaranteNinguno = "";
//																														List<SituacionLaboralTipoGarantia> situacionLaboral2 = creditoTipoGarantia.getListaSituacionLaboral();
																														
																														for (SituacionLaboralTipoGarantia situacionLaboral : creditoTipoGarantia.getListaSituacionLaboral()) {
																															if (socioEstructura.getIntTipoSocio().equals(situacionLaboral.getId().getIntParaSituacionLaboralCod())
																																&& situacionLaboral.getIntValor() == 1) {
																																msgTxtSituacionLaboralGarante = "";
																																Boolean blnPasoNroMaximo = Boolean.FALSE;
																																
																																// Se valida Nro de asegurados...
																																nroMaxAsegurados = solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGarante.getPersona().getIntIdPersona());
																																if (nroMaxAsegurados != null) {
																																	intNroPersGarantizadas = nroMaxAsegurados;
																																	if (creditoTipoGarantia.getIntNumeroMaximoGarantia() != null) {
																																		if (nroMaxAsegurados.compareTo(creditoTipoGarantia.getIntNumeroMaximoGarantia()) <= 0) {
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
																																	if(creditoTipoGarantia.getIntParaTipoDsctoJudicialCod() != null){
																																		msgTxtDescuentoJudicial = "";
//																																		Boolean blnAplicaDesceuntoJudicial = Boolean.FALSE;
																																		if(creditoTipoGarantia.getIntParaTipoDsctoJudicialCod().compareTo(Constante.PARAM_T_DESCUENTO_JUDICIAL_GARANTE_SIN_DESCUENTO)==0){															
																																			blnDescuento = validarDescuentoJudicial(socioCompGarante);
																																			if(blnDescuento){
																																				socioCompGarante.setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																				socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia);
																																				
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
																																			socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia);
																																			beanSocioCompGarante = socioCompGarante;
																																			blnDatosGarante = true;
																																			blnValidaDatosGarante = true;
																																			break forEstructuraGarante;
																																		}
																																	}else{
																																		msgTxtDescuentoJudicial = "";
																																		socioCompGarante.setCreditoTipoGarantiaAsignado(new CreditoTipoGarantia());
																																		socioCompGarante.setCreditoTipoGarantiaAsignado(creditoTipoGarantia);
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
																									msgTxtCondicionHabilGarante = "Ninguna Condición Hábil coincide con la condición establecida en la cuenta.";
																								}
																							}
																						} else {
																							msgTxtCondicionHabilGarante = "No se ha configurado una Condición Hábil en esta garantía.";
																						}
																					} else {
																						msgTxtCondicionSocioNingunoGarante = "Ninguna Condición de Socio coincide con la condición establecida en la cuenta.";
																					}
																				}
																			} else {
																				msgTxtCondicionSocioNingunoGarante = "No se ha configurado la Condición de Socio en esta garantía.";
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
															
														//} else {
														//	msgTxtTipoGarantiaPersonal = "El crédito no tiene configurado un tipo de Garantía Personal.";
														//}
													//}
												//}
												//}else{
												//	msgTxtTipoGarantiaPersonal = "El crédito no tiene configurado un tipo de Garantía Personal.";
												//}
												
											} else {
												msgTxtSocioEstructuraOrigenGarante = "La Estructura del Socio no tiene un tipo Origen predefinido.";
											}
										}
								} else {
									msgTxtSocioEstructuraGarante = "La persona elegida no tiene una Estructura.";
								}
							} else {
								msgTxtRolSocioGarante = "El Garante solicitado no tiene un Rol Socio";
							}
						}
					} else {
						msgTxtRolSocioGarante = "La persona no posee ningún Rol de Usuario.";
					}
				} else {
					msgTxtCuentaGarante = "La Persona no tiene una Cuenta creada.";
				}
				
			} else {
				msgTxtPersonaGaranteNoExiste = "El DNI ingresado no existe en la BD.";
				blnDatosGarante = false;
			}
		} catch (Exception e) {
			log.error("Error en validarGarante ---> "+e);
		}
	}

	
	/**
	 * Devuelve FALSE si ya existe una garantia en la grilla. 
	 * Ya que no deberia duplicarse.
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
							
							if(adminPadronresult.getId() != null){
								DescuentoId descuentoId = new DescuentoId();
								descuentoId.setIntCodigo(adminPadronresult.getId().getIntCodigo());
								descuentoId.setIntItemAdministraPadron(adminPadronresult.getId().getIntItemAdministraPadron());
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
	 * Cancela la operacion de agregar garante.
	 * @param event
	 */
	public void cancelarGarante(ActionEvent event) {
		blnValidaDatosGarante = true;
		strNroDocumento = "";
		beanSocioCompGarante = new SocioComp();
		beanSocioCompGarante.setPersona(new Persona());
		msgTxtPersonaNoExiste = null;
		msgTxtRolSocio = null;
		msgTxtTipoGarantiaPersonal = null;
		msgTxtCondicionSocio = null;
		msgTxtCondicionSocioNinguno = null;
		msgTxtSubCondicionSocio = null;
		msgTxtSubCondicionSocioNinguno = null;
		msgTxtCondicionLaboralGarante = null;
		msgTxtCondicionLaboralGaranteNinguno = null;
		msgTxtSituacionLaboralGarante = null;
		msgTxtSituacionLaboralGaranteNinguno = null;
		msgTxtMaximaGarantia = null;
		msgTxtSocioEstructuraGarante = null;
		msgTxtSocioEstructuraOrigenGarante = null;
		msgTxtCuentaGarante = null;
		msgTxtMaxNroGarantes = null;
		msgTxtGarante = "";
		
	}

	
	/**
	 * Se recupera las garantias de credito del tipo personal
	 * @return
	 */
	public CreditoGarantia recuperarConfiguracionGarantiaTipoPersonal(){
		CreditoGarantia garantiasPersonales = null;
		try {
			garantiasPersonales = creditoFacade.recuperarGarantiasPersonales(beanCredito);
		} catch (Exception e) {
			log.error("Error en recuperarConfiguracionGarantiaTipoPersonal ---> "+e);
		}
		return garantiasPersonales;
	}
	

	/**
	 * Registra Garante Voluntario a la Solicitud de Prestamo.
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
				log.error("Error Exception en grabarGarante ---> " + e);
				e.printStackTrace();
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
	 * Calcula el nro de garantes correctamente registrados
	 */
	public void calcularGarantesValidos() {
		System.out.println("ListaGarantia " +listaGarantiaCreditoComp);
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
					blnPasa = Boolean.FALSE;
					break;
				}
			}
			
			if(garantiasPersonales.getIntNroGarantesConfigurados().compareTo(intGarantesCorrectos)!=0){
				strMensajeGarantesObservacion = "";
				strMensajeGarantesObservacion = strMensajeGarantesObservacion + "Nro. de Garantes insuficientes. Configurados: " +garantiasPersonales.getIntNroGarantesConfigurados()+
												" / Registrados: "+intGarantesCorrectos;
				blnPasa = Boolean.FALSE;
			}else{
				strMensajeGarantesObservacion = strMensajeGarantesObservacion + "";
			}
		} catch (Exception e) {
			log.error("Error en validarGrabargarantes ---* "+e);	
		}
		return blnPasa;
	}
	
	/**
	 * Remueve un registro de la grilla de U. Ejecutora.
	 * @param event
	 */
	public void removeSocioEstructura(ActionEvent event) {
		String rowKey = getRequestParameter("rowKeySocioEstructura");
		CapacidadCreditoComp capacidadCreditoCompTmp = null;
		setMsgTxtEstrucActivoRepetido("");
		try {
			if (listaCapacidadCreditoComp != null) {
				for (int i = 0; i < listaCapacidadCreditoComp.size(); i++) {
					if (Integer.parseInt(rowKey) == i) {
						CapacidadCreditoComp capacidadCreditoComp = listaCapacidadCreditoComp.get(i);
						if(capacidadCreditoComp.getSocioEstructura().getIntTipoEstructura().compareTo(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)!=0){
							
							if (capacidadCreditoComp.getSocioEstructura().getId() != null) {
								capacidadCreditoCompTmp = listaCapacidadCreditoComp.get(i);
								capacidadCreditoCompTmp.getSocioEstructura().setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
							}
							listaCapacidadCreditoComp.remove(i);
							break;
						}else{
							setMsgTxtEstrucActivoRepetido("No puede eliminarse la U. Ejecutora de Origen.");
							
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error Exception en removeSocioEstructura ---> : " + e);
		}
	}

	
	/**
	 * Remueve un registro de la grilla de Garantes Solidarios
	 * @param event
	 */
	public void removeGaranteSolidario(ActionEvent event) {
		String rowKey = getRequestParameter("rowKeyGaranteSolidario");
		GarantiaCreditoComp garantiaCreditoCompTmp = null;
		try {
			if (listaGarantiaCreditoComp != null) {
				for (int i = 0; i < listaGarantiaCreditoComp.size(); i++) {
					if (Integer.parseInt(rowKey) == i) {
						GarantiaCreditoComp garantiaCreditoComp = listaGarantiaCreditoComp.get(i);
						if (garantiaCreditoComp.getGarantiaCredito().getId() != null) {
							garantiaCreditoCompTmp = listaGarantiaCreditoComp.get(i);
							garantiaCreditoCompTmp.getGarantiaCredito().setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
						}
						listaGarantiaCreditoComp.remove(i);
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("Error Exception en removeGaranteSolidario ---> " + e);
		}
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
		double dias = new Double(0);
		
		try {
			fechaInicial = df.parse(fechaInicioString);
			String fechaFinalString = df.format(fechaFinal);
			fechaFinal = df.parse(fechaFinalString);
			long fechaInicialMs = fechaInicial.getTime();
			long fechaFinalMs = fechaFinal.getTime();
			long diferencia = fechaFinalMs - fechaInicialMs;
			dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
		
		} catch (ParseException ex) {
			log.error("Error ParseException en fechasDiferenciaEnDias ---> " + ex);
		}
		return ((int) dias);
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
	 * Recupera los Creditos que cumplen con las validaciones configuradas y
	 * continua la Evaluación
	 * @param beanSocioComp
	 * @param credito
	 * @return
	 */
	public Credito validarConfiguracionCredito(SocioComp beanSocioComp, Credito credito) {
		Boolean hasMontMin = false;
		Boolean hasMontMax = false;
		Boolean hasPorcMin = false;
		Boolean hasPorcMax = false;
//		Boolean hasDiasMax = false;
		Integer nroValidaciones = new Integer(0);
		Integer contAprob = new Integer(0);
		BigDecimal bdMontoPorcMinimo = null;
		BigDecimal bdMontoPorcMaximo = null;
		Boolean boAprueba = new Boolean(false);
//		ExpedienteComp expSelect = null;
		
		// De acuerdo a la configuracion del credito, se establece que se
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
		//solo aplica en caso de un represtamo
		//14.04.2014 Se cancela modificacion. MOTIVO: si se hace un represtamo, este puede caer en cualquier tipo de prestamo, no necesariamente.
		//en el mismo tipo del prestamo original (segun explicacion de javier)
//		if (beanExpedienteCredito.getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)) {
//			for (ExpedienteComp x : beanSocioComp.getCuentaComp().getListExpedienteMovimientoComp()) {
//				if (x.getChecked()) {
//					expSelect = x;
//					break;
//				}
//			}
//			if (credito.getId().getIntParaTipoCreditoCod().compareTo(expSelect.getExpediente().getIntParaTipoCreditoCod())!=0 
//					|| credito.getId().getIntItemCredito().compareTo(expSelect.getExpediente().getIntItemCredito())!=0) {
//				nroValidaciones--;
//			}
//		}	
		
		if (beanExpedienteCredito.getListaEstadoCredito().size()>0) {
			
			
		}else{
			//  Excepciones: Dias de gracia
			/*  if(credito.getListaExcepcion().size()>0){ 
				  listaExcepciones = credito.getListaExcepcion(); 
				  for(int i=0; i< credito.getListaExcepcion().size(); i++){
					  if(credito.getListaExcepcion().get(i).getIntPrimerCredito() != null){
						  hasDiasMax = true; 
						  nroValidaciones++; 
					  }
					  System.out.println("  PRIMER CREDITO ???   "  +i+" ---- "+credito.getListaExcepcion().get(i).getIntPrimerCredito());
				  }
			  }
			*/
		}

		// Validando las restricciones
		if (hasMontMin	&& (bdMontoTotalSolicitado.compareTo(credito.getBdMontoMinimo()) >= 0))
				contAprob++;
		if (hasMontMax&& (bdMontoTotalSolicitado.compareTo(credito.getBdMontoMaximo()) <= 0))
				contAprob++;
		if (hasPorcMin && (bdMontoTotalSolicitado.compareTo(bdMontoPorcMinimo) >= 0))
				contAprob++;
		if (hasPorcMax	&& (bdMontoTotalSolicitado.compareTo(bdMontoPorcMaximo) <= 0))
				contAprob++;

		// Validando Excepciones
		
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
		//System.out.println(" NRO DE VALIDACIONES EXISTENTES " + nroValidaciones);
		//System.out.println(" NRO DE VALIDACIONES APROBADAS " + contAprob);

		if (nroValidaciones == contAprob)
			boAprueba = true;
		if (boAprueba) {
			//System.out.println("***********************************************************************");
			//System.out.println("ITEM CREDITO ---> "+credito.getId().getIntItemCredito());
			//System.out.println("TIPO CREDITO ---> "+credito.getId().getIntParaTipoCreditoCod());
			//System.out.println("ITEM CREDITO ---> "+credito.getId().getIntItemCredito());
			//System.out.println("DESC CREDITO ---> "+credito.getStrDescripcion());
			//System.out.println("PERSEMPRESA ---> "+credito.getId().getIntPersEmpresaPk());
			//System.out.println("SIZE - INTERES ---> "+credito.getListaCreditoInteres().size());
			//System.out.println("***********************************************************************");
			if(!credito.getListaCreditoInteres().isEmpty()){
//				List<CreditoInteres> lstInteres = null;
//				lstInteres =credito.getListaCreditoInteres();
				/*for (CreditoInteres creditoInteres : lstInteres) {
					System.out.println("TIPO INTERES ACT / CES ---> "+creditoInteres.getIntParaTipoSocio());
					System.out.println("MONTO INTERES ---> "+creditoInteres.getBdTasaInteres());
				}*/
			}			
			return credito;
		} else {
			return null;
		}
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
			int intFechRegDia = new Integer(strFechaRegistro.substring(0,2));
			int intFechRegMes = new Integer(strFechaRegistro.substring(3,5))-1;
			int intFechRegAno = new Integer(strFechaRegistro.substring(6,10));
			/**
			 * FATALITY
			 */
			// En la clase calendar enero es 0
//			if(intFechRegMes == 1){
//				intFechRegMes = 0;
//			}

			miFecha.set(intFechRegAno, intFechRegMes, intFechRegDia);
			//Integer intPeridoEnvio = 0;		
			capacidadMaximoEnvio = recuperarPerdiodoUltimoEnvio(beanSocioComp);			
			// SI NO HAY ENVIOS para todas las u.e. del soccio
			if(capacidadMaximoEnvio == null){
				// vencimiento
				//Date dtFechaVenc = new Date();
				Calendar clFechaVencTemp = Calendar.getInstance();
				clFechaVencTemp.setTime(Constante.sdf.parse(strFechaRegistro));
				clFechaVencTemp = getUltimoDiaDelMesCal(clFechaVencTemp);
				fecVenc1.clear();
				fecVenc1= clFechaVencTemp;
				
				if ((new Integer(strFechaRegistro.substring(0,2))) > estructuraDetalle.getIntDiaEnviado()) {
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
	 * Asociado al boton Aceptar del popup de adjuntar documentos.
	 */
	public void aceptarAdjuntarArchivo() {
		try {
			FileUploadControllerServicio fileUploadControllerServicio = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
			archivoAdjunto = fileUploadControllerServicio.getObjArchivo();
			listaArchivo.add(archivoAdjunto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	
	/**
	 * Asociado al link de descarga de adjunto.
	 * Recupera el adjunto fisico y dispara la descarga.
	 * @param event
	 */
	public void descargaArchivo(ActionEvent event) {
		//HttpServletResponse objResponse;
		//FileInputStream objFileInputStream;
//		String strNombreCompletoArchivo="";
		//byte[] arrDatosArchivo;
		
		try {
			String strRutaActual = getRequestParameter("strRutaActual");
			String strNombreArchivo = getRequestParameter("strNombreArchivo");
//			strNombreCompletoArchivo = strRutaActual + "\\" + strNombreArchivo;
			log.info("strRutaActual: " + strRutaActual);
			log.info("strNombreArchivov: " + strNombreArchivo);
			
			DownloadFile.downloadFile(strRutaActual + "\\" + strNombreArchivo);

		} catch (Exception e) {
			log.error("Error Exception en descargaArchivo ---> "+e);
		}
	}

	
	/**
	 * 
	 * @author USER
	 *
	 */
	public class UConstante {
		public static final int BUFFER_SIZE = 2048;
	}
	
	
	
	/**
	 * Asociado al link de descarga de adjunto.
	 * Recupera el adjunto fisico y dispara la descarga.
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
			log.error("Error Exception en descargaArchivoUltimo ---> "+exc);
		} 
	
	}

	
	/**
	 * Cambia el estado a Anulada a la solcitud seleccionada. Solo cambia si esta en estado requiisito.
	 */
	public void irEliminarSolicitudPrestamo(ActionEvent event) {
//		SocioComp socioComp;
//		Integer intIdPersona = null;
//		Persona persona = null;
		EstadoCredito ultimoEstado = null;

		try {
			
			limpiarFormSolicitudPrestamo();
			limpiarResultadoBusqueda();
			
			ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
			expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
			expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
			expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
			expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());
			
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
								//Se graba el nuevo estado
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
								}
								solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
							}else{
								strMsgErrorValidarDatos = "Solo se puede Eliminar una Solicitud en estado REQUISITO. El estado actual es "+strUltimoEstado.toUpperCase();
								return;
							}
					}
				}
			}
			cancelarGrabarSolicitudPrestamo(event);
			limpiarFormSolicitudPrestamo();
		} catch (BusinessException e) {
			log.error("Error en irEliminarSolicitudPrestamo ---> : " + e);
			e.printStackTrace();
		} 	
	}
	
	
	/**
	 * Devuelve la descripcion de las condiciones, situaciones del garante.
	 * @param intTipo
	 * @param intValor
	 * @return
	 */
	public String cargarDesripcionValidacionesGarantes(Integer intTipo, Integer intValor){
		String strDescripcion="";
		
		try {
			strDescripcion="Descripción Desconocida";
	
	        switch (intTipo) {
	            case 1: //La condición de la cuenta del garante - PARAM_T_TIPO_CONDSOCIO
		            	for (Tabla tablaDescripcion : listaCondicionSocio) {
		            		if(intValor.compareTo(tablaDescripcion.getIntIdDetalle())==0){
		            			strDescripcion = tablaDescripcion.getStrDescripcion();
		            			 break;
		            		}
						}
		                break;
 
	            case 2: //La sub condición de la cuenta
		            	for (Tabla tablaDescripcion : listaTipoCondicionSocio) {
		            		if(intValor.compareTo(tablaDescripcion.getIntIdDetalle())==0){
		            			strDescripcion = tablaDescripcion.getStrDescripcion();
		            			 break;
		            		}
						}
		            	break;
	            case 3:  //La Condición Laboral
		            	for (Tabla tablaDescripcion : listaCondicionLaboral) {
		            		if(intValor.compareTo(tablaDescripcion.getIntIdDetalle())==0){
		            			strDescripcion = tablaDescripcion.getStrDescripcion();
		            			 break;
		            		}
						}
		                break;
	            case 4:  //La Situación Laboral
		            	for (Tabla tablaDescripcion : listaSituacionLaboral) {
		            		if(intValor.compareTo(tablaDescripcion.getIntIdDetalle())==0){
		            			strDescripcion = tablaDescripcion.getStrDescripcion();
		            			 break;
		            		}	
						}
		                break;
	        }
		} catch (Exception e) {
			log.error("Error en cargarDesripcionValidacionesGarantes --->  "+e);
		}
			return strDescripcion;
	}
	
	
	/**
	 * Actualiza el estado a ANULADO
	 */
	public void cambioEstadoEliminadoSolicitudPrestamo(ActionEvent event) {
		EstadoCredito estadoCredito = null;
		List<GarantiaCredito> listaGarantiaCredito = null;
		
		try {
			beanExpedienteCredito.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			beanExpedienteCredito.getId().setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());
			beanExpedienteCredito.setIntPersEmpresaCreditoPk(beanCredito.getId().getIntPersEmpresaPk() == null ? 0 : beanCredito.getId().getIntPersEmpresaPk());
			beanExpedienteCredito.setIntParaTipoCreditoCod(beanCredito.getIntParaTipoCreditoEmpresa() == null ? 0 : beanCredito.getIntParaTipoCreditoEmpresa());
			beanExpedienteCredito.setIntItemCredito(beanCredito.getId().getIntItemCredito() == null ? 0 : beanCredito.getId().getIntItemCredito());
			beanExpedienteCredito.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
			
			if (isValidoExpedienteCredito(beanExpedienteCredito) == false) {
				strErrorGrabar=" Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.";
				return;
			}
	
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
			}

			if (listaCapacidadCreditoComp != null && listaCapacidadCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaCapacidadCreditoComp(listaCapacidadCreditoComp);
			}
	
			if (listaGarantiaCreditoComp != null && listaGarantiaCreditoComp.size() > 0) {
				listaGarantiaCredito = new ArrayList<GarantiaCredito>();
				for (GarantiaCreditoComp garantiaCreditoComp : listaGarantiaCreditoComp) {
					listaGarantiaCredito.add(garantiaCreditoComp.getGarantiaCredito());
				}
				beanExpedienteCredito.setListaGarantiaCredito(listaGarantiaCredito);
			}
	
			if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
			}
		
			if((beanExpedienteCredito.getId().getIntItemExpediente() != null) 
				&& (beanExpedienteCredito.getId().getIntItemDetExpediente() != null)){
				
				solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
				cancelarGrabarSolicitudPrestamo(event);
				
			}else{
				solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
			}

			if (beanExpedienteCredito.getListaRequisitoCreditoComp() != null
				&& beanExpedienteCredito.getListaRequisitoCreditoComp().size() > 0) {
				renombrarArchivo(beanExpedienteCredito.getListaRequisitoCreditoComp());
			}
			
			cancelarGrabarSolicitudPrestamo(event);
			limpiarFormSolicitudPrestamo();
			listarSolicitudPrestamo(event);
		} catch (BusinessException e) {
			log.error("Error BusinessException en cambioEstadoEliminadoSolicitudPrestamo ---> "+e);
		}
	}
	


	/**
	 * 
	 * @param event
	 * @throws EJBFactoryException
	 * @throws IOException
	 */
	public void irVerSolicitudPrestamo(ActionEvent event) throws EJBFactoryException, IOException {		
		blnValidarGarantes = Boolean.TRUE;
		intTipoEvaluacion = 2 ;
		List<ExpedienteComp> lstExpedientesRecuperados = null;
		Cuenta cuentaExpedienteCredito = null;
		List<CuentaConcepto> lstCtaCto = null;
		Cuenta cuentaSocio = null;
		CuentaComp cuentaComp = null;
		
		try {
			log.info("listaCronogramaCreditoComp :"+listaCronogramaCreditoComp);
			limpiarFormSolicitudPrestamo();
//			limpiarResultadoBusqueda();
			
			blnModoVisualizacion = true;
			blnMostrarDescripciones = Boolean.TRUE;
			SocioComp socioComp;
			Integer intIdPersona = null;
			Persona persona = null;

			ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
			expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
			expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
			expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
			expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());
			
			// devuelve el crongrama son id vacio.
			beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);
			
			if (beanExpedienteCredito != null) {
				Credito creditoRecuperado = new Credito();
				CreditoId creditoRecuperadoId = new CreditoId();
				creditoRecuperadoId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
				creditoRecuperadoId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
				creditoRecuperadoId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());
				creditoRecuperado = creditoFacade.getCreditoPorIdCredito(creditoRecuperadoId);
				beanCredito = creditoRecuperado;

				// Recuperamos el id de persona del socio desde la capacidad.
				if (beanExpedienteCredito.getListaCapacidadCreditoComp() != null && beanExpedienteCredito.getListaCapacidadCreditoComp().size() > 0) {
					listaCapacidadCreditoComp = beanExpedienteCredito.getListaCapacidadCreditoComp();
					
					if (listaCapacidadCreditoComp != null && listaCapacidadCreditoComp.size() > 0) {
						for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
							if(capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk() != null){
								intIdPersona = capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk();
								break;
							}
						}
						
						// Persona de socio
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
							
							cuentaExpedienteCredito = new Cuenta();
							cuentaExpedienteCredito.setId(new CuentaId());
							cuentaExpedienteCredito.getId().setIntCuenta(beanExpedienteCredito.getId().getIntCuentaPk());
							cuentaExpedienteCredito.getId().setIntPersEmpresaPk(beanExpedienteCredito.getId().getIntPersEmpresaPk());
							
							socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
										new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
										persona.getDocumento().getStrNumeroIdentidad(),
										Constante.PARAM_EMPRESASESION, cuentaExpedienteCredito);

							for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
								if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
									socioComp.getSocio().setSocioEstructura(socioEstructura);
								}
							}

							lstCtaCto = recuperarCuentasConceptoSocio(socioComp);
							cuentaComp = new CuentaComp();
							
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

							cuentaSocio = cuentaFacade.getCuentaActualPorIdPersona(socioComp.getPersona().getIntIdPersona(), Constante.PARAM_EMPRESASESION);
							if(cuentaSocio != null ){
								cuentaComp.setCuenta(cuentaSocio);
								socioComp.setCuenta(cuentaSocio);
							}
							socioComp.setCuentaComp(cuentaComp);
							beanSocioComp = socioComp;

							if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
								for (CuentaConcepto ctaCto : listaCuentaConcepto) {
									if(ctaCto.getListaCuentaConceptoDetalle() != null && !ctaCto.getListaCuentaConceptoDetalle().isEmpty()){
										for (CuentaConceptoDetalle ctaCtoDet : ctaCto.getListaCuentaConceptoDetalle()) {
											Boolean blnContinua = Boolean.TRUE;
											
											// se valida el inicio
											if(ctaCtoDet.getTsInicio()!= null){
												if(ctaCtoDet.getTsInicio().before(new Timestamp(new Date().getTime()))){
													blnContinua = Boolean.TRUE;
												}else{
													blnContinua = Boolean.FALSE;
												}
												if(blnContinua){
													if(ctaCtoDet.getTsFin()!= null){
														if(ctaCtoDet.getTsFin().after(new Timestamp(new Date().getTime()))){
															blnContinua = Boolean.FALSE;	
														}
													}else{
														blnContinua = Boolean.TRUE;
													}
												}
											}else{
												blnContinua = Boolean.FALSE;
											}

											if(blnContinua){
												bdAportes = bdAportes.add(ctaCtoDet.getBdMontoConcepto());
											}
										}
									}
								}
							}
							
							bdMontoSaldoAnterior = BigDecimal.ZERO;
							// CGD-07.11.2013 - REPRETSAMO
							if(beanExpedienteCredito.getIntParaSubTipoOperacionCod().compareTo(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)==0){
								if(beanExpedienteCredito.getListaCancelacionCredito() != null && !beanExpedienteCredito.getListaCancelacionCredito().isEmpty()){
									for(CancelacionCredito cancelacion : beanExpedienteCredito.getListaCancelacionCredito()) {
										bdMontoSaldoAnterior=bdMontoSaldoAnterior.add(cancelacion.getBdMontoCancelado());
									}
									//JCHAVEZ 23.06.2014
									lstExpedientesRecuperados = recuperarCreditosMovimiento();
									listaExpedientesParaReprestamo = validarExpedientesParaRePrestamo(lstExpedientesRecuperados);
									
									if (listaExpedientesParaReprestamo!=null && !listaExpedientesParaReprestamo.isEmpty()) {
										ExpedienteCreditoId expCredId = null;
										for (ExpedienteComp expediente : listaExpedientesParaReprestamo) {
											expCredId = new ExpedienteCreditoId();
											expCredId.setIntCuentaPk(expediente.getExpediente().getId().getIntCuentaPk());
											expCredId.setIntItemDetExpediente(expediente.getExpediente().getId().getIntItemExpedienteDetalle());
											expCredId.setIntItemExpediente(expediente.getExpediente().getId().getIntItemExpediente());
											expCredId.setIntPersEmpresaPk(expediente.getExpediente().getId().getIntPersEmpresaPk());
											break;
										}
										
//										bdMontoSaldoAnterior = bdMontoSaldoAnterior.add(bdMontoInteresAtrasado!=null?bdMontoInteresAtrasado:calcularInteresAtrasado(expCredId));
									}
									//JCHAVEZ 25.06.2014 no se recalcula, se debe obtener el monto interes ya calculado en la evaluacion previa
									if (bdMontoInteresAtrasado!=null && bdMontoInteresAtrasado.equals(BigDecimal.ZERO)) {
										bdMontoSaldoAnterior = bdMontoSaldoAnterior.add(bdMontoInteresAtrasado);
									}else {
										bdMontoSaldoAnterior = bdMontoSaldoAnterior.add(registroSeleccionadoBusqueda.getExpedienteCredito().getBdMontoInteresAtrasado());
//										calcularInteresAtrasado(expCredId)
									}
									
									listExpedienteMovimientoComp =  recuperarCanceladoCreditoMovimiento(beanExpedienteCredito.getListaCancelacionCredito());
									recuperarCreditosMovimiento();
									beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(listExpedienteMovimientoComp);
									Integer intTamanno = listExpedienteMovimientoComp.size();
									intTamanno = intTamanno + 1;
									beanSocioComp.getCuentaComp().setIntTamannoListaExp(intTamanno);
								}
							}else{
								// Recuperamos los expedientes de movimineto Ppara visualizarlos en la grilla de saldo de cuentas
								lstExpedientesRecuperados = recuperarCreditosMovimiento();
								if(lstExpedientesRecuperados != null && !lstExpedientesRecuperados.isEmpty()){
									strMsgErrorValidarDatos= "";
									beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(lstExpedientesRecuperados);
									CuentaComp miCuentaComp = beanSocioComp.getCuentaComp();
									
									List<CuentaComp> listaTemp = new ArrayList<CuentaComp>();
									listaTemp.add(miCuentaComp);
									socioComp.setListaCuentaComp(listaTemp);
									beanSocioComp.setListaCuentaComp(listaTemp);
									Integer intTam = lstExpedientesRecuperados.size();
									intTam++;
									beanSocioComp.getCuentaComp().setIntTamannoListaExp(intTam);
									
								}else{
									beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(lstExpedientesRecuperados);
									beanSocioComp.getCuentaComp().setIntTamannoListaExp(1);
									strMsgErrorValidarDatos ="No se ha encontrado algún Crédito con deuda.";
								}
							}
							// Queda pendiente sumar si se eligiera cancelar un préstamo anterior
							bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoSolicitado();
							bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
						}
					}
				}
				// Se valida el estado del Expediente para ver si se puede o no Eliminar o Modificar
				if (beanExpedienteCredito.getListaEstadoCredito() != null && !beanExpedienteCredito.getListaEstadoCredito().isEmpty()) {
					configurarCampos(beanExpedienteCredito);
					cargarDescripciones();
					mostrarlistaAutorizacionesPrevias(beanExpedienteCredito.getListaEstadoCredito());
					
					if(beanExpedienteCredito.getEstadoCreditoPrimero() != null){
						if(beanExpedienteCredito.getEstadoCreditoPrimero().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
							strFechaRegistro = Constante.sdf.format(beanExpedienteCredito.getEstadoCreditoPrimero().getTsFechaEstado());
						}
					}
				}
				
				if (beanExpedienteCredito.getListaEstadoCredito() != null && !beanExpedienteCredito.getListaEstadoCredito().isEmpty()) {
					int maximo = beanExpedienteCredito.getListaEstadoCredito().size();
					EstadoCredito estadoCreditoSolicitud = beanExpedienteCredito.getListaEstadoCredito().get(maximo - 1);
					intUltimoEstadoSolicitud = estadoCreditoSolicitud.getIntParaEstadoCreditoCod();
					tablaEstado = tablaFacade.getTablaPorIdMaestroYIdDetalle(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO),estadoCreditoSolicitud.getIntParaEstadoCreditoCod());
					strUltimoEstadoSolicitud = tablaEstado.getStrDescripcion();
					strFechaRegistro = Constante.sdf.format(estadoCreditoSolicitud.getTsFechaEstado());
				}
				
				intNroCuotas = beanExpedienteCredito.getIntNumeroCuota();

				//CGD-12.11.2013
				evaluarPrestamoCronogramaView(event);
				blnEvaluacionCredito = true;
				formSolicitudPrestamoRendered = true;
				pgValidDatos = false;
				blnDatosSocio = true;
					
				calcularGarantesValidos();
				
				if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null && !beanExpedienteCredito.getListaRequisitoCreditoComp().isEmpty()) {
					listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
				}

				if (beanExpedienteCredito.getListaGarantiaCredito() != null && beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
					
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
										 if(estructuraGarante.getListaEstructura() != null && !estructuraGarante.getListaEstructura().isEmpty()){
												int maximo = estructuraGarante.getListaEstructura().size();
												Juridica juridicaGarante = new Juridica();
												juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
												estructuraGarante.setJuridica(juridicaGarante);
											}
									}
								
									if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null && !socioCompGaranteEdit.getSocio().getListSocioEstructura().isEmpty()){
										int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
										socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
									}
									
									Integer nroMaxAsegurados = new Integer(-1);
									nroMaxAsegurados=solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGaranteEdit.getPersona().getIntIdPersona());
									
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
			}
			log.info("lista: "+listaExpedienteCreditoComp);
		} catch (BusinessException e) {
			log.error("Error BusinessException en  irModificarSolicitudPrestamo ---> " + e);
			e.printStackTrace();
		}finally {
			formSolicitudPrestamoRendered = true;
			pgValidDatos = false;
			blnDatosSocio = true;
			strSolicitudPrestamo = Constante.MANTENIMIENTO_NINGUNO;
		}
	}

	
	/**
	 * Determina si un campo se habilita o deshabilita dependiendo 
	 * del estado de la Solicitud.
	 */
	public void configurarCampos(ExpedienteCredito beanExpedienteCredito){
		try {
			if( beanExpedienteCredito.getListaEstadoCredito()!= null){
				int tamano = beanExpedienteCredito.getListaEstadoCredito().size();
				EstadoCredito estadoCreditoSolicitud = beanExpedienteCredito.getListaEstadoCredito().get(tamano-1);
				int intStatus = estadoCreditoSolicitud.getIntParaEstadoCreditoCod();
				
				if(intStatus == Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO){
					blnBtnAddCapacidad = false;
					blnBtnEditCapacidad = false;
					blnBtnDelCapacidad = false;
					blnBtnEvaluarCredito = false;
					blnTextMontoPrestamo = true;
					blnTextNroCuotas = false;
					blnCmbTipoOperacion = false;
					blnBtnAddGarante = false;
					blnBtnDelGarante = false;
					blnTxtMotivoPrestamo = false;
					blnTxtObservacionesPrestamo = false;
					blnBtnAddRequisito = false;
					blnLnkDescarga = false;
				} else if((intStatus == Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)
							||(intStatus == Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO) ){
							blnBtnAddCapacidad = true;
							blnBtnEditCapacidad = true;
							blnBtnDelCapacidad = true;
							blnBtnEvaluarCredito = true;
							blnTextMontoPrestamo = true;
							blnTextNroCuotas = true;
							blnCmbTipoOperacion = true;
							blnBtnAddGarante = true;
							blnBtnDelGarante = true;
							blnTxtMotivoPrestamo = true;
							blnTxtObservacionesPrestamo = true;
							blnBtnAddRequisito = true;
							blnLnkDescarga = false;
						} else if ((intStatus == Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)){
									blnBtnAddCapacidad = false;
									blnBtnEditCapacidad = false;
									blnBtnDelCapacidad = false;
									blnBtnEvaluarCredito = false;
									blnTextMontoPrestamo = true;
									blnTextNroCuotas = true;
									blnCmbTipoOperacion = false;
									blnBtnAddGarante = false;
									blnBtnDelGarante = false;
									blnTxtMotivoPrestamo = false;
									blnTxtObservacionesPrestamo = false;
									blnBtnAddRequisito = false;
									blnLnkDescarga = false;
						}else{
							blnBtnAddCapacidad = true;
							blnBtnEditCapacidad = true;
							blnBtnDelCapacidad = true;
							blnBtnEvaluarCredito = true;
							blnTextMontoPrestamo = true;
							blnTextNroCuotas = true;
							blnCmbTipoOperacion = false;
							blnBtnAddGarante = true;
							blnBtnDelGarante = true;
							blnTxtMotivoPrestamo = true;
							blnTxtObservacionesPrestamo = true;
							blnBtnAddRequisito = true;
							blnLnkDescarga = true;	
						}
			} else{
					blnBtnAddCapacidad = false;
					blnBtnEditCapacidad = false;
					blnBtnDelCapacidad = false;
					blnBtnEvaluarCredito = false;
					blnTextMontoPrestamo = false;
					blnTextNroCuotas = false;
					blnCmbTipoOperacion = false;
					blnBtnAddGarante = false;
					blnBtnDelGarante = false;
					blnTxtMotivoPrestamo = false;
					blnTxtObservacionesPrestamo = false;
					blnBtnAddRequisito = false;
					blnLnkDescarga = false;
			}
		} catch (Exception e) {
			log.error("Error en configurarCampos ---> "+e);
		}
	}

	
	
	/**
	 * Calcula totales de Aporte, Retiro y SEpelio y los setea en cuentaComp.
	 * Ademas Define el valor de la variablke global "bdAportes", utilizado en los procesos de evaluacion, cronograma, etc etc
	 * @param pSocioComp
	 * @return SocioComp
	 */
	public SocioComp calcularTotalesConceptos (SocioComp pSocioComp){
		
		try {
			//beanSocioComp --> pSocioComp
			if (pSocioComp.getCuenta().getListaConcepto() != null) {
				List<CuentaConcepto> listaConceptos = new ArrayList<CuentaConcepto>();
				listaConceptos = pSocioComp.getCuenta().getListaConcepto();
				BigDecimal totalAporte = new BigDecimal(0);
				BigDecimal totalSepelio = new BigDecimal(0);
				BigDecimal totalRetiro = new BigDecimal(0);

				for (int i = 0; i < listaConceptos.size(); i++) {
					// Fondo Aportes
					if (listaConceptos.get(i).getId().getIntItemCuentaConcepto() == 1) { // temporalmente
						totalAporte = totalAporte.add(listaConceptos.get(i).getBdSaldo());
					}
					// Fondo Retiro
					if (listaConceptos.get(i).getId().getIntItemCuentaConcepto() == 2) {// temporalmente
						totalRetiro = totalRetiro.add(listaConceptos.get(i).getBdSaldo());
					}
					// Fondo Sepelio
					if (listaConceptos.get(i).getId().getIntItemCuentaConcepto() == 3) {// temporalmente
						totalSepelio = totalSepelio.add(listaConceptos.get(i).getBdSaldo());
					}

//					List<CuentaConceptoDetalle> listaConceptosDetalle = new ArrayList<CuentaConceptoDetalle>();
//					listaConceptosDetalle = pSocioComp.getCuenta().getListaConcepto().get(i).getListaCuentaConceptoDetalle();

				}
				
				CuentaComp cuentaComp = new CuentaComp();
				cuentaComp.setCuenta(pSocioComp.getCuenta());
				cuentaComp.setBdTotalAporte(totalAporte);
				cuentaComp.setBdTotalRetiro(totalRetiro);
				cuentaComp.setBdTotalSepelio(totalSepelio);
				pSocioComp.setCuentaComp(cuentaComp);
			}

			if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
				for (CuentaConcepto ctaCto : listaCuentaConcepto) {
					if(ctaCto.getListaCuentaConceptoDetalle() != null && !ctaCto.getListaCuentaConceptoDetalle().isEmpty()){
						for (CuentaConceptoDetalle ctaCtoDet : ctaCto.getListaCuentaConceptoDetalle()) {
							Boolean blnContinua = Boolean.TRUE;
							// se valida el inicio
							if(ctaCtoDet.getTsInicio()!= null){
								if(ctaCtoDet.getTsInicio().before(new Timestamp(new Date().getTime()))){
									blnContinua = Boolean.TRUE;
								}else{
									blnContinua = Boolean.FALSE;
								}
								if(blnContinua){
									if(ctaCtoDet.getTsFin()!= null){
										if(ctaCtoDet.getTsFin().after(new Timestamp(new Date().getTime()))){
											blnContinua = Boolean.FALSE;	
										}
									}else{
										blnContinua = Boolean.TRUE;
									}
								}
							}else{
								blnContinua = Boolean.FALSE;
							}
							if(blnContinua){
								bdAportes = bdAportes.add(ctaCtoDet.getBdMontoConcepto());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en calcularTotalesConceptos ---> "+e);
		}
		return  pSocioComp;
	}

	
	/**
	 * Limpia el cronograma entre evaluacion y evaluacion
	 */
	public void limpiarCronograma(){
		try {
			if(listaCronogramaCreditoComp != null && !listaCronogramaCreditoComp.isEmpty()){
				listaCronogramaCreditoComp.clear();
			}
			if(beanExpedienteCredito.getListaCronogramaCredito() != null && !beanExpedienteCredito.getListaCronogramaCredito().isEmpty()){
				beanExpedienteCredito.getListaCronogramaCredito().clear();
			}
		} catch (Exception e) {
			log.error("Error en limpiarCronograma ---> "+e);
		}
	}
	
	
	/**
	 * Metodo que se ejecuta al seleccionar un registro de la grilla de busqueda
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		log.info("esta entrando en el procedimiento");
		ExpedienteCreditoId expedienteCreditoId = null;
		try{
			registroSeleccionadoBusqueda = (ExpedienteCreditoComp)event.getComponent().getAttributes().get("itemExpCredito");
			validarOperacionEliminar();
			validarOperacionModificar();
			log.info("Es modificar? "+blnMostrarModificar);
			if(registroSeleccionadoBusqueda != null){
				expedienteCreditoId = new ExpedienteCreditoId();
				expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
				expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
				validarEstadoCuenta(expedienteCreditoId);
				log.info("Es modificar? "+blnMostrarModificar);
				log.info("paso esta validacion");
//				irVerSolicitudPrestamo(null);
				log.info("Es modificar? "+blnMostrarModificar);
				
//				if(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio()==2)
//					mostrarBoton = Boolean.TRUE;
//				else
//					mostrarBoton = Boolean.FALSE;
			}
			log.info("Es modificar? "+blnMostrarModificar);
		}catch (Exception e) {
			log.error("Error Exception en seleccionarRegistro ---> "+e);
		}
	}
	
	
	/**
	 * Valida si la operacion ELIMINAR se visualiza o no en el popup de acciones.
	 * Solo se podra eliminar si es Requisito.
	 */
	public void validarOperacionEliminar(){
		try {
			if(registroSeleccionadoBusqueda != null){
				if(registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
					blnMostrarEliminar = Boolean.TRUE;
				}else{
					blnMostrarEliminar = Boolean.FALSE;
				}
			}
		} catch (Exception e) {
			log.error("Error en validarOperacionEliminar ---> "+e);
		}
	}
	
	
	/**
	 * Valida si la operacion MODIFICAR se visualiza o no en el popup de acciones.
	 * Solo se podra modificar si es Requiisito u Observado.
	 */
	public void validarOperacionModificar(){
		try {
			if(registroSeleccionadoBusqueda != null){				
				if(registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
					|| registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
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
	 * Valida que el credito al encontarse en esatdo Observado, no sea pposible editar el nrod e cuotas
	 */
	public void validarEstadoObservado(){
		try {
			if(registroSeleccionadoBusqueda != null){
				if(registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
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
	 * Valida si la operacion MODIFICAR se visualiza o no en el popup de acciones
	 */
	public void validarGarantesSolidarios(){
		try {
			if(registroSeleccionadoBusqueda != null){				
				if(registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)!=0){
					blnValidarGarantes = Boolean.TRUE;
				}else{
					blnValidarGarantes = Boolean.FALSE;
				}
			}	
		} catch (Exception e) {
			log.error("Error en validarOperacionModificar ---> "+e);
		}
	}
	
	/**
	 * 
	 */
	public void cargarDescripciones(){
		try {
			listaSubOpePrestamos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_SUBOPERACIONPRESTAMO), "A");
			
			if(listaSubOpePrestamos != null && !listaSubOpePrestamos.isEmpty()){
				for (Tabla tabDesc1 : listaSubOpePrestamos) {
					if(tabDesc1.getIntIdDetalle().compareTo(beanExpedienteCredito.getIntParaSubTipoOperacionCod())==0){
						strDescripcionSubTipoOperacion= tabDesc1.getStrDescripcion();
						break;
					}
				}	
			}
		} catch (Exception e) {
			log.error("Error en cargarDescripciones ---> "+e);
		}
	}
	
	
	
	/**
	 * Carga el 2do combo de Tipo de actividad segun 1er combo seleccionado
	 * @param event
	 */
	public void renderizarTextBusqueda(ActionEvent event)  {
		String strTipoConsultaBusqueda = "";
		try {			
			strTipoConsultaBusqueda = getRequestParameter("pIntTipoConsultaBusqueda");
			if(!strTipoConsultaBusqueda.equals("0")){
				blnTxtBusqueda = false;
			}
		} catch (Exception e) {
			log.error("Error en renderizarTextBusqueda ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * Invocado desde la solicitud de prestamo
	 */
	public void listarTercerosSolicitudPrestamo(ActionEvent event){
		List<Terceros> listaFilas = null;
		List<Terceros> listaColumnas = null;
		Persona persona = null;
		
		try {
			if(beanSocioComp !=null && beanSocioComp.getPersona()!=null){
				persona = beanSocioComp.getPersona();				
				listaFilas = estructuraFacade.getListaFilaTercerosPorDNI(persona.getDocumento().getStrNumeroIdentidad());
				listaColumnas = estructuraFacade.getListaColumnaTercerosPorDNI(persona.getDocumento().getStrNumeroIdentidad());
				
				if(listaFilas!=null && listaColumnas!=null){					
					listColumnCpto = new ArrayList<Terceros>();
					listaFilas = filtrarPorPeriodoYMesSolicitudPrestamo(listaFilas);
					
					for(Terceros tercero : listaColumnas){
						int conta = 0;
						for(Terceros columna : listColumnCpto){
							if(columna.getStrCpto().equals(tercero.getStrCpto())){
								conta++;
								break;
							}
						}
						if(conta==0)listColumnCpto.add(tercero);
					}
					
					for(Terceros columna : listaColumnas){
						for(Terceros fila : listaFilas){
							if(fila.getId().getIntPeriodo().equals(columna.getId().getIntPeriodo()) &&
							fila.getId().getIntMes().equals(columna.getId().getIntMes())){
								if(fila.getLsMontos()==null){
									fila.setLsMontos(new String[1]);
								}else{
									String values[] = new String[fila.getLsMontos().length+1];
									for(int i=0; i<fila.getLsMontos().length; i++){
										values[i] = fila.getLsMontos()[i];
									}
									fila.setLsMontos(values);
								}

								// cgd - 15.05.2013
								// FORMATEANDO LAS COLUMNAS DINAMICAS
								Integer intSizeCadena =columna.getIntMonto().toString().length();
								String intDecimal = (columna.getIntMonto().toString().substring(intSizeCadena - 2));
								String intEntero =(columna.getIntMonto().toString().substring(0,intSizeCadena - 2));
								String strMontoCorregido = intEntero +"."+intDecimal;

								BigDecimal aDecimal = new BigDecimal(strMontoCorregido);  
								BigDecimal bdMonto = aDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);  
								String strMonto = bdMonto.toString();
								fila.getLsMontos()[fila.getLsMontos().length-1]=""+strMonto;
							}
							// FORMATEANDO EL TOTAL
							Integer intSize =fila.getIntMonto().toString().length();
							String intDec = (fila.getIntMonto().toString().substring(intSize - 2));
							String intEnt =(fila.getIntMonto().toString().substring(0,intSize - 2));
							String strMontoCorr = intEnt +"."+intDec;

							BigDecimal aDecimalTot = new BigDecimal(strMontoCorr);  
							BigDecimal bdMontoTot = aDecimalTot.setScale(2, BigDecimal.ROUND_HALF_UP);  

							fila.setBdMontoTotal(bdMontoTot);
						}
					}
					setListTerceros(listaFilas);
				}
			}
		} catch (BusinessException e) {
			log.error("Error en listarTercerosSolicitudPrestamo ---> "+ e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param listaFilas
	 * @return
	 */
	public List<Terceros> filtrarPorPeriodoYMesSolicitudPrestamo(List<Terceros> listaFilas){
		GregorianCalendar gcFechaReferencia = new GregorianCalendar();
		// Se pide por defecto los ultimos 6 meses...
		intFrecuenciaTerceros = Constante.PARAM_T_FREQTERCEROS_SEISMESES;
		
		if(intFrecuenciaTerceros.equals(0)){
			return listaFilas;
		}else if(intFrecuenciaTerceros.equals(Constante.PARAM_T_FREQTERCEROS_TRESMESES)){
			gcFechaReferencia.add(Calendar.MONTH, -3);
		}else if(intFrecuenciaTerceros.equals(Constante.PARAM_T_FREQTERCEROS_SEISMESES)){
			gcFechaReferencia.add(Calendar.MONTH, -7);
		}else if(intFrecuenciaTerceros.equals(Constante.PARAM_T_FREQTERCEROS_UNANIO)){
			gcFechaReferencia.add(Calendar.MONTH, -12);
		}else if(intFrecuenciaTerceros.equals(Constante.PARAM_T_FREQTERCEROS_DOSANIOS)){
			gcFechaReferencia.add(Calendar.MONTH, -24);
		}
		
		List<Terceros> listFilas = new ArrayList<Terceros>();
		for(Terceros fila : listaFilas){
			if(Integer.valueOf(fila.getId().getIntPeriodo())>gcFechaReferencia.get(Calendar.YEAR)){
				listFilas.add(fila);
			}else{
				if(Integer.valueOf(fila.getId().getIntMes())>gcFechaReferencia.get(Calendar.MONTH)){
					listFilas.add(fila);
				}
			}
		}
		return listFilas;
	}
	
	/**
	 * Relacionado con la clase autorizacionPrestamoCOntroller
	 * @param event
	 */
	public void irModificarSolicitudPrestamoAutoriza(ActionEvent event){	
		AutorizacionPrestamoController autorizacionPrestamo = (AutorizacionPrestamoController) getSessionBean("autorizacionPrestamoController");
		autorizacionPrestamo.irModificarSolicitudPrestamoAutoriza(event);	
	}
	
	
	/**
	 * Redirige la aplicaCion a Actualizar
	 * @param event
	 */
	
	public void irModificarSolicitudPrestamoAutoriza2(ActionEvent event, ExpedienteCreditoComp expedienteCreditoComp) {		
		blnValidarGarantes = Boolean.TRUE;
		intTipoEvaluacion = 2 ;
		List<ExpedienteComp> lstExpedientesRecuperados = null;
		Cuenta cuentaExpedienteCredito = null;
		
		try {
			
			blnModoVisualizacion = true;
			blnMostrarDescripciones = Boolean.TRUE;
			SocioComp socioComp;
			Integer intIdPersona = null;
			Persona persona = null;
			ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
			expedienteCreditoId.setIntPersEmpresaPk(expedienteCreditoComp.getExpedienteCredito().getId().getIntPersEmpresaPk());
			expedienteCreditoId.setIntCuentaPk(expedienteCreditoComp.getExpedienteCredito().getId().getIntCuentaPk());
			expedienteCreditoId.setIntItemExpediente(expedienteCreditoComp.getExpedienteCredito().getId().getIntItemExpediente());
			expedienteCreditoId.setIntItemDetExpediente(expedienteCreditoComp.getExpedienteCredito().getId().getIntItemDetExpediente());
			
			// devuelve el crongrama son id vacio.
			beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);
			if (beanExpedienteCredito != null) {
				Credito creditoRecuperado = new Credito();
				CreditoId creditoRecuperadoId = new CreditoId();
				
				creditoRecuperadoId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
				creditoRecuperadoId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
				creditoRecuperadoId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());
				creditoRecuperado = creditoFacade.getCreditoPorIdCredito(creditoRecuperadoId);
				beanCredito = creditoRecuperado;
		
				if (beanExpedienteCredito.getListaCapacidadCreditoComp() != null
					&& beanExpedienteCredito.getListaCapacidadCreditoComp().size() > 0) {
					
					listaCapacidadCreditoComp = beanExpedienteCredito.getListaCapacidadCreditoComp();
					if (listaCapacidadCreditoComp != null && listaCapacidadCreditoComp.size() > 0) {
						
						for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
							intIdPersona = capacidadCreditoComp.getCapacidadCredito().getIntPersPersonaPk();
						}
						persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
						if (persona != null) {
							if (persona.getListaDocumento() != null && persona.getListaDocumento().size() > 0) {
								for (Documento documento : persona.getListaDocumento()) {
									if (documento.getIntTipoIdentidadCod().equals(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTO_DNI))) {
										persona.setDocumento(documento);
										break;
									}
								}
							}

							cuentaExpedienteCredito = new Cuenta();
							cuentaExpedienteCredito.setId(new CuentaId());
							cuentaExpedienteCredito.getId().setIntCuenta(beanExpedienteCredito.getId().getIntCuentaPk());
							cuentaExpedienteCredito.getId().setIntPersEmpresaPk(beanExpedienteCredito.getId().getIntPersEmpresaPk());
							
							socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresaYCuenta(
										new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
										persona.getDocumento().getStrNumeroIdentidad(),
										Constante.PARAM_EMPRESASESION, cuentaExpedienteCredito);

							for (SocioEstructura socioEstructura : socioComp.getSocio().getListSocioEstructura()) {
								if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
									socioComp.getSocio().setSocioEstructura(socioEstructura);
								}
							}
							beanSocioComp = socioComp;
							beanSocioComp= calcularTotalesConceptos(beanSocioComp);

							// Queda pendiente sumar si se eligiera cancelar un préstamo anterior
							bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoSolicitado().divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);

							// Recuperamos los expedientes de movimineto Ppara visualizarlos en la grilla de saldo de cuentas
							lstExpedientesRecuperados = recuperarCreditosMovimiento();
							if(lstExpedientesRecuperados != null && !lstExpedientesRecuperados.isEmpty()){
								strMsgErrorValidarDatos= "";
								beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(lstExpedientesRecuperados);
								CuentaComp miCuentaComp = beanSocioComp.getCuentaComp();
								
								List<CuentaComp> listaTemp = new ArrayList<CuentaComp>();
								listaTemp.add(miCuentaComp);
								socioComp.setListaCuentaComp(listaTemp);
								beanSocioComp.setListaCuentaComp(listaTemp);
								Integer intTam = lstExpedientesRecuperados.size();
								intTam++;
								beanSocioComp.getCuentaComp().setIntTamannoListaExp(intTam);								
							}else{
								beanSocioComp.getCuentaComp().setListExpedienteMovimientoComp(lstExpedientesRecuperados);
								beanSocioComp.getCuentaComp().setIntTamannoListaExp(1);
								strMsgErrorValidarDatos ="No se ha encontrado algún Crédito con deuda.";
							}
						}
					}
				}
				// Se valida el estado del Expediente para ver si se puede o no
				// Eliminar o Modificar
				if (beanExpedienteCredito.getListaEstadoCredito() != null && !beanExpedienteCredito.getListaEstadoCredito().isEmpty()) {
					configurarCampos(beanExpedienteCredito);
					cargarDescripciones();
					
					for (EstadoCredito estadoCredito : beanExpedienteCredito.getListaEstadoCredito()) {
						if (estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)
							|| estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)) {
							setStrSolicitudPrestamo(Constante.MANTENIMIENTO_ELIMINAR);
							break;
						} else {
							if (estadoCredito.getIntParaEstadoCreditoCod().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)) {
								strFechaRegistro = Constante.sdf.format(estadoCredito.getTsFechaEstado());
							}
							setStrSolicitudPrestamo(Constante.MANTENIMIENTO_MODIFICAR);
						}
					}
				}
				if (beanExpedienteCredito.getListaEstadoCredito() != null && !beanExpedienteCredito.getListaEstadoCredito().isEmpty()) {
					int maximo = beanExpedienteCredito.getListaEstadoCredito().size();
					EstadoCredito estadoCreditoSolicitud = beanExpedienteCredito.getListaEstadoCredito().get(maximo - 1);
					intUltimoEstadoSolicitud = estadoCreditoSolicitud.getIntParaEstadoCreditoCod();
					tablaEstado = tablaFacade.getTablaPorIdMaestroYIdDetalle(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO),
																				estadoCreditoSolicitud.getIntParaEstadoCreditoCod());
					strUltimoEstadoSolicitud = tablaEstado.getStrDescripcion();
					strFechaRegistro = Constante.sdf.format(estadoCreditoSolicitud.getTsFechaEstado());
				}
				intNroCuotas = beanExpedienteCredito.getIntNumeroCuota();
				evaluarPrestamoModificar(event);
					
				if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null && !beanExpedienteCredito.getListaRequisitoCreditoComp().isEmpty()) {
					listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
				}

				if (beanExpedienteCredito.getListaGarantiaCredito() != null && beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
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
										 if(estructuraGarante.getListaEstructura() != null && !estructuraGarante.getListaEstructura().isEmpty()){
												int maximo = estructuraGarante.getListaEstructura().size();
												Juridica juridicaGarante = new Juridica();
												juridicaGarante = estructuraGarante.getListaEstructura().get(maximo-1).getJuridica();
												estructuraGarante.setJuridica(juridicaGarante);
											}
									}
									// VERIFICAR CAIDA
									if(socioCompGaranteEdit.getSocio().getListSocioEstructura() != null && !socioCompGaranteEdit.getSocio().getListSocioEstructura().isEmpty()){
										int maximo = socioCompGaranteEdit.getSocio().getListSocioEstructura().size();
										socioCompGaranteEdit.getSocio().setSocioEstructura(socioCompGaranteEdit.getSocio().getListSocioEstructura().get(maximo-1));
									}
					
									Integer nroMaxAsegurados = new Integer(-1);
									nroMaxAsegurados=solicitudPrestamoFacade.getCantidadPersonasAseguradasPorPkPersona(socioCompGaranteEdit.getPersona().getIntIdPersona());
									
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
			}
		} catch (BusinessException e) {
			log.error("Error BusinessException en  irModificarSolicitudPrestamo ---> " + e);
			e.printStackTrace();
		} catch (ParseException e) {
			log.error("Error ParseException en  irModificarSolicitudPrestamo ---> " + e);
			e.printStackTrace();
		} finally {
			formSolicitudPrestamoRendered = true;
			pgValidDatos = false;
			blnDatosSocio = true;
		}
	}

	
	/**
	 * Muestra las personas que observaron la solicitud de credito previamente, durante
	 * el proceso de autorizacion de prestamo.
	 * @return
	 */
	public boolean mostrarlistaAutorizacionesPrevias(List<EstadoCredito> listaEstados) {
		boolean isValidEncaragadoAutorizar = true;
		List<AutorizaCredito> listaAutorizaCredito = null;
		listaAutorizaCreditoComp = null;
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
		
			listaAutorizaCredito = new ArrayList<AutorizaCredito>();
			listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
			// buscar todos sus estados y ver si aen alguno existe un observado
			if(listaEstados!= null && !listaEstados.isEmpty()){
				Boolean blnContinua = Boolean.FALSE;
				
				for (EstadoCredito estados : listaEstados) {
					if(estados.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0){
						blnContinua = Boolean.TRUE;
						break;
					}
				}

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
			log.error("Error en mostrarlistaAutorizacionesPrevias ---< "+e);
			e.printStackTrace();
		}
		return isValidEncaragadoAutorizar;
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
	
	
	/**
	 * Convierte un String a Timestamp
	 * @param str_date
	 * @return
	 */
	public static final Timestamp  convierteStringATimestamp(String str_date){
		DateFormat formatter ; 
		Date date ; 
		java.sql.Timestamp timeStampDate = null;
		formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
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
	 * Calcaula el interes atrazado del credito a represtar
	 * @param expCreditoAntiguoId
	 * @return
	 */
	public BigDecimal calcularInteresAtrasado(ExpedienteCreditoId expCreditoAntiguoId){
//		BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
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
				ultimoInteresCancelado = conceptoFacade.getMaxInteresCanceladoPorExpediente(expedienteAntiguo.getId());
					// si existe ... dias = hoy - fecha de registro o mov
				if(ultimoInteresCancelado != null && ultimoInteresCancelado.getTsFechaMovimiento() != null){
//					Date dtFechaMovimiento = convertirTimestampToDate(ultimoInteresCancelado.getTsFechaMovimiento());
//					intNumeroDias = obtenerDiasEntreFechas(dtFechaMovimiento, dtHoy);
					//jchavez 26.06.2014 Si el ultimo interes cancelado, su monto interes es cero, tomar como
					//fecha de movimiento la fecha de inicio
					if (ultimoInteresCancelado.getBdMontoInteres().equals(BigDecimal.ZERO)) {
						Date dtFechaMovimiento = convertirTimestampToDate(ultimoInteresCancelado.getTsFechaInicio());
						intNumeroDias = obtenerDiasEntreFechas(dtFechaMovimiento, dtHoy);
					}else{
						Date dtFechaMovimiento = convertirTimestampToDate(ultimoInteresCancelado.getTsFechaMovimiento());
						intNumeroDias = obtenerDiasEntreFechas(dtFechaMovimiento, dtHoy);
					}
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
				expedienteAntiguo = conceptoFacade.getExpedientePorPK(expedienteAntiguo.getId());
				bdMontoSaldoCredito = expedienteAntiguo.getBdSaldoCredito();
				// 5. xxxxxxxxxxxx
				BigDecimal bdPorcentajeInteres =  BigDecimal.ONE;
				bdPorcentajeInteres = expedienteAntiguo.getBdPorcentajeInteres();

				// 6. Calculo: dias * interes * saldo / 30 * 100
				if(intNumeroDias.compareTo(0)!=0){
					bdNumeroDias = new BigDecimal(intNumeroDias);
					bdMult01 = bdNumeroDias.multiply(bdPorcentajeInteres);
					bdMult02 = bdMult01.multiply(bdMontoSaldoCredito);
					bdMult03 = bdMult100.multiply(bdMult30);

					bdMontoInteresAtrasado = bdMult02.divide(bdMult03, 2,RoundingMode.HALF_UP);
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
     * @param timestamp
     * @return
     */
    private static Date convertirTimestampToDate(Timestamp timestamp) {
        return new Date(timestamp.getTime());
    }
    
    /**
     * 
     * @param 23.04.2014
     * @param 24.04.2014
     * @return Imprime el formulario de prestamos
     * @throws Exception
     */

    public void imprimirSolicitudPrestamo(){
    	String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla prestamo = new Tabla();
    	Tabla tablaEstadoCivil = new Tabla();
    	Tabla tablaCargoLaboral = null;
    	Tabla tablaMotivo = null;
    	//String strCargoLaboral = "";
    	List<Movimiento> lstMovSaldos = new ArrayList<Movimiento>();
    	EstadoExpediente ultimoEstExp = new EstadoExpediente();
    	ExpedienteId expMovId = new ExpedienteId();
    	List<CuentaConcepto> lstCtaCpto = new ArrayList<CuentaConcepto>();
    	List<EstadoCredito> lstEstadoCredito = new ArrayList<EstadoCredito>();
    	List<CapacidadCredito> lstCapCred = new ArrayList<CapacidadCredito>();
		Tabla nombreBanco = new Tabla();
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(',');
		NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);
		
		try {
			ConceptoFacadeRemote conceptoFacade =(ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			ContactoFacadeRemote contactoFacade =(ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
					
			irVerSolicitudPrestamo(null);
						
			expMovId.setIntPersEmpresaPk(beanExpedienteCredito.getId().getIntPersEmpresaPk());
			expMovId.setIntCuentaPk(beanExpedienteCredito.getId().getIntCuentaPk());
			expMovId.setIntItemExpediente(beanExpedienteCredito.getId().getIntItemExpediente());
			expMovId.setIntItemExpedienteDetalle(beanExpedienteCredito.getId().getIntItemDetExpediente());
			ultimoEstExp = conceptoFacade.getMaxEstadoExpPorPkExpediente(expMovId);
			
			BigDecimal bdMontoSaldo = BigDecimal.ZERO;
			if (ultimoEstExp!=null && ultimoEstExp.getIntCuenta()!=null) {
				if (ultimoEstExp.getIntParaEstadoExpediente().equals(new Integer(1))) {
					lstMovSaldos = conceptoFacade.getListXCtaExpediente(beanExpedienteCredito.getId().getIntPersEmpresaPk(),
							beanExpedienteCredito.getId().getIntCuentaPk(),beanExpedienteCredito.getId().getIntItemExpediente(),
							beanExpedienteCredito.getId().getIntItemDetExpediente());
					if (lstMovSaldos!=null && !lstMovSaldos.isEmpty()) {
						for (Movimiento movimiento : lstMovSaldos) {
							if (movimiento.getBdMontoSaldo()!=null && movimiento.getBdMontoSaldo().compareTo(BigDecimal.ZERO)==1) {
								bdMontoSaldo = bdMontoSaldo.add(movimiento.getBdMontoSaldo());
							}
						}
					}
				}
			}

			BigDecimal bdAporte = BigDecimal.ZERO;
			lstCtaCpto = conceptoFacade.getListaCuentaConceptoPorEmpresaYCuenta(beanExpedienteCredito.getId().getIntPersEmpresaPk(),beanExpedienteCredito.getId().getIntCuentaPk());
			if (lstCtaCpto!=null && !lstCtaCpto.isEmpty()) {
				for (CuentaConcepto cuentaConcepto : lstCtaCpto) {
					if (cuentaConcepto.getBdSaldo()!=null && cuentaConcepto.getId().getIntItemCuentaConcepto().equals(new Integer(1))) {
						bdAporte = bdAporte.add(cuentaConcepto.getBdSaldo());
					}
				}
			}

			BigDecimal bdCtaFija = BigDecimal.ZERO;
			lstEstadoCredito = solicitudPrestamoFacadeNuevo.getListaPorExpedienteCreditoPkYEstadoCredito(beanExpedienteCredito.getId(),new Integer(2));
			if (lstEstadoCredito!=null && !lstEstadoCredito.isEmpty()) {
				lstCapCred = solicitudPrestamoFacade.getListaPorPkExpedienteCredito(beanExpedienteCredito.getId());
				if (lstCapCred!=null && !lstCapCred.isEmpty()) {
					for (CapacidadCredito capacidadCredito : lstCapCred) {
						bdCtaFija = capacidadCredito.getBdCuotaFija();
						break;
					}
				}
			}
			//fechaEsta = solicitudPrestamoFacadeNuevo.getListaPorExpedienteCreditoPkYEfechaEstado(beanExpedienteCredito.getId(), tsFechaEstado);		
			tablaEstadoCivil = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOCIVIL), 
    				beanSocioComp.getPersona().getNatural().getIntEstadoCivilCod());
			
			tablaCargoLaboral = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_CARGOPERSONAL), 
					beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCargo());
			
			tablaMotivo = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_FINALIDAD_CREDITO), 
					(beanExpedienteCredito.getIntParaFinalidadCod()!=null?beanExpedienteCredito.getIntParaFinalidadCod():1));
			
			parametro.put("P_APORTE", formato.format(bdAporte));
			parametro.put("P_CUOTAFIJA", bdCtaFija);	
			parametro.put("P_MOTIVO", ":" +tablaMotivo.getStrDescripcion()!=null?tablaMotivo.getStrDescripcion():" ");
			parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
			parametro.put("P_APELLIDOPATERNO", beanSocioComp.getPersona().getNatural().getStrApellidoPaterno()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoPaterno():" ");
			parametro.put("P_APELLIDOMATERNO", beanSocioComp.getPersona().getNatural().getStrApellidoMaterno()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoMaterno():" ");
			parametro.put("P_NOMCOMPLETO", beanSocioComp.getPersona().getNatural().getStrNombres()!=null?beanSocioComp.getPersona().getNatural().getStrNombres():" ");
			if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			}
			parametro.put("P_CARGOlABORAL", tablaCargoLaboral.getStrDescripcion());
			
			if (lstEstadoCredito!=null && !lstEstadoCredito.isEmpty()) {
				Timestamp fecha = lstEstadoCredito.get(0).getTsFechaEstado();
				String fecha1[] = fecha.toString().split("-");
			parametro.put("P_EXPEDIENTE", fecha1[0]+"-"+beanExpedienteCredito.getId().getIntItemExpediente()+"-"+beanExpedienteCredito.getId().getIntItemDetExpediente());
			}else{
				parametro.put("P_EXPEDIENTE", " ");	
			}
			BigDecimal montoSolicitado = beanExpedienteCredito.getBdMontoTotal();
			
			parametro.put("P_MONTOTOTAL1", formato.format(montoSolicitado));

	        parametro.put("P_NUMCUOTA", beanExpedienteCredito.getIntNumeroCuota());
	        //trae el domicilio
	        List<Domicilio> lstDomicilio = contactoFacade.getListaDomicilio(beanSocioComp.getPersona().getIntIdPersona());
			Domicilio dom = new Domicilio();
			if(lstDomicilio != null && lstDomicilio.size()>=1){
				dom = lstDomicilio.get(0);
			}
			parametro.put("P_DIRECCION", (dom.getStrNombreVia()!=null?dom.getStrNombreVia(): " ") + " " + (dom.getIntNumeroVia()!=null?dom.getIntNumeroVia(): " ") + " " + (dom.getIntInterior()!=null?dom.getIntInterior(): " "));
			parametro.put("P_DIRREFERENCIA", dom.getStrReferencia()!=null?dom.getStrReferencia():" ");
			parametro.put("P_DISTRITO", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPk())).get(0).getStrDescripcion()!=null?(facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPk())).get(0).getStrDescripcion():" ");
			parametro.put("P_PROVINCIA", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkProvincia())).get(0).getStrDescripcion()!=null?(facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkProvincia())).get(0).getStrDescripcion():" ");
	        parametro.put("P_DEPARTAMENTO", (facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkDpto())).get(0).getStrDescripcion()!=null?(facade.getListaPorIdUbigeo(dom.getIntParaUbigeoPkDpto())).get(0).getStrDescripcion():" ");
			//Trae la lista de comunicaciones telefono celular y correo electronico.
			List<Comunicacion> lstComunicacion = beanSocioComp.getPersona().getListaComunicacion();
            String telefMovil = " ";
            String correoElectronico =" ";
	        	if(lstComunicacion != null && !lstComunicacion.isEmpty()){
	        	for (Comunicacion com : lstComunicacion) {
					if(com.getIntTipoComunicacionCod()!= null){
						if (com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO) && com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION) &&
                    			(com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_CLARO) ||
                    					com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_MOVISTAR) ||
                    							com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_NEXTEL)) &&
                    							com.getIntSubTipoComunicacionCod().equals(Constante.PARAM_T_COMUNICACIONPERSONAL)) {
                    		telefMovil = String.valueOf(com.getIntNumero())!=null?String.valueOf(com.getIntNumero()): " ";
                    		
                    	}else if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_CORREO) && com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION)){
                    		
                    		correoElectronico = com.getStrDato()!=null?com.getStrDato(): " ";
                    	}
					}
				}
	        }
	        parametro.put("P_COMUMOVIL", telefMovil);
	        parametro.put("P_CORREO", correoElectronico);
	        Integer telefFijo=null;
	           if(lstComunicacion!=null && !lstComunicacion.isEmpty()){
	        	   int prioridad = 99;
	        	   for(Comunicacion comFijo : lstComunicacion){
	           		   if(comFijo.getIntSubTipoComunicacionCod()==4 && comFijo.getIntTipoComunicacionCod()==1 && prioridad>5 && comFijo.getIntTipoLineaCod()==1){
	        			   prioridad = 5;
	        			   telefFijo =comFijo.getIntNumero();
	        	       }else if(comFijo.getIntSubTipoComunicacionCod()==3 && comFijo.getIntTipoComunicacionCod()==1 && prioridad>4 && comFijo.getIntTipoLineaCod()==1){
	        	    	   prioridad = 4;
	        	    	   telefFijo =comFijo.getIntNumero();
	        		   }else if(comFijo.getIntSubTipoComunicacionCod()==2 && comFijo.getIntTipoComunicacionCod()==1 && prioridad>3 && comFijo.getIntTipoLineaCod()==1){
	        	    	   prioridad = 3;
	        	    	   telefFijo =comFijo.getIntNumero();
	        		   }else if(comFijo.getIntSubTipoComunicacionCod()==1 && comFijo.getIntTipoComunicacionCod()==1 && prioridad>2 && comFijo.getIntTipoLineaCod()==1){
	        	    	   prioridad = 2;
	        	    	   telefFijo =comFijo.getIntNumero();
	        		   }else if(comFijo.getIntSubTipoComunicacionCod()==5 && comFijo.getIntTipoComunicacionCod()==1 && prioridad>1 && comFijo.getIntTipoLineaCod()==1){
	        	    	   prioridad = 1;
	        	    	   telefFijo =comFijo.getIntNumero();
	        		   }
	        	   	}
	           	}
	           parametro.put("P_COMUCASA", telefFijo);
	        //CONTRATO DE PRESTAMO
	        String apellidoPaterno = beanSocioComp.getPersona().getNatural().getStrApellidoPaterno().toUpperCase();
	        String apellidoMaterno = beanSocioComp.getPersona().getNatural().getStrApellidoMaterno().toUpperCase();
	        String nombres = beanSocioComp.getPersona().getNatural().getStrNombres().toUpperCase();
	        parametro.put("P_NOMYAPELLIDO", apellidoPaterno + " " + apellidoMaterno + ", " + nombres);
	        List<CuentaBancaria> lstCuentaBancaria = beanSocioComp.getPersona().getListaCuentaBancaria();
	        CuentaBancaria cuentaBan = new CuentaBancaria();
			if(lstCuentaBancaria != null && lstCuentaBancaria.size()>=1 && !lstCuentaBancaria.isEmpty()){
				cuentaBan = lstCuentaBancaria.get(0);
				if(cuentaBan.getIntEstadoCod()==1 && cuentaBan.getIntMarcaAbono()==1)
					parametro.put("P_CUENTABANCARIA", cuentaBan.getStrNroCuentaBancaria());
				else
					parametro.put("P_CUENTABANCARIA", ".....................................");
			}else{
				parametro.put("P_CUENTABANCARIA", ".....................................");
			}
			
			BigDecimal interes = beanExpedienteCredito.getBdPorcentajeInteres();
			parametro.put("P_INTERES", formato.format(interes));
	        parametro.put("P_GRABAMEN", beanExpedienteCredito.getBdPorcentajeInteres());
	        parametro.put("P_ESTADOCIVIL", tablaEstadoCivil.getStrDescripcion());
	        /*De aqui en adelante estan los parametros para las siguientes dos hojas*/
	        lstEstadoCredito = solicitudPrestamoFacadeNuevo.getListaPorExpedienteCreditoPkYEstadoCredito(beanExpedienteCredito.getId(),new Integer(6));
			if (lstEstadoCredito!=null && !lstEstadoCredito.isEmpty()) {
				if(lstEstadoCredito.get(0).getTsFechaEstado()!=null){
					Timestamp fecha = lstEstadoCredito.get(0).getTsFechaEstado();
					String dia = Constante.sdfDia.format(fecha);
					String mes = Constante.sdfMes.format(fecha);
					String anio = Constante.sdfAnno.format(fecha);
					int numero = 0;
					numero = Integer.parseInt(mes);
					switch(numero){
					case 1:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de ENERO de "+ anio);
						break;
					}
					case 2:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de FEBRERO de "+ anio);
						break;
					}
					case 3:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de MARZO de "+ anio);
						break;
					}
					case 4:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de ABRIL de "+ anio);
						break;
					}
					case 5:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de MAYO de "+ anio);
						break;
					}
					case 6:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de JUNIO de "+ anio);
						break;
					}
					case 7:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de JULIO del "+ anio);
						break;
					}
					case 8:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de AGOSTO de "+ anio);
						break;
					}
					case 9:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de SEPTIEMBRE de "+ anio);
						break;
					}
					case 10:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de OCTUBRE de "+ anio);
						break;
					}
					case 11:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de NOVIEMBRE de "+ anio);
						break;
					}
					case 12:
					{
						parametro.put("P_FECHAESTADO", dia +" días del mes de DICIEMBRE de "+ anio);
						break;
					}
				}
					}else{
					parametro.put("P_FECHAESTADO", "...........");
				}
			}else{
				parametro.put("P_FECHAESTADO", "...........");
			}
			tablaEstadoCivil = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_ESTADOCIVIL), 
    				beanSocioComp.getPersona().getNatural().getIntEstadoCivilCod());

			parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
			if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			}

			BigDecimal montoSolicitado1 = beanExpedienteCredito.getBdMontoTotal();
			String letras=ConvertirNumeroLetras.convertNumberToLetter(formato.format(montoSolicitado1).toString());
	        parametro.put("P_MONTOENLETRAS", letras);
	        
			parametro.put("P_NUMCUOTA", beanExpedienteCredito.getIntNumeroCuota());
        
	        List<Domicilio> lstDomicilio1 = contactoFacade.getListaDomicilio(beanSocioComp.getPersona().getIntIdPersona());
			Domicilio dom1 = new Domicilio();
			if(lstDomicilio1 != null && lstDomicilio1.size()>=1){
				dom1 = lstDomicilio1.get(0);
			}
			 String strVia=null;
	            Tabla tablaVia = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), dom.getIntTipoViaCod());
	            if(tablaVia!=null){
	            	strVia = tablaVia.getStrDescripcion();
	            }
			parametro.put("P_DIRECCION", strVia + " " + (dom1.getStrNombreVia()!=null?dom1.getStrNombreVia(): " ") + " " + (dom1.getIntNumeroVia()!=null?dom1.getIntNumeroVia(): " ") + " " + (dom1.getIntInterior()!=null?dom1.getIntInterior(): " "));

	        //CONTRATO DE PRESTAMO
	        if(beanExpedienteCredito.getBdMontoMoraAtrasada()!=null){
	        	parametro.put("P_GRABAMEN", beanExpedienteCredito.getBdMontoMoraAtrasada());
	        }else{
	        	parametro.put("P_GRABAMEN", "");
	        }
	        parametro.put("P_ESTADOCIVIL", tablaEstadoCivil.getStrDescripcion());
	        if(!beanSocioComp.getPersona().getListaCuentaBancaria().isEmpty() && beanSocioComp.getPersona().getListaCuentaBancaria()!=null){
				nombreBanco = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.NOMBRE_BANCO), beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntBancoCod());
					if(nombreBanco!=null && beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntEstadoCod()==1 
							&& beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntMarcaAbono()==1)
						parametro.put("P_NOMBREBANCO", nombreBanco.getStrDescripcion());
					else
						parametro.put("P_NOMBREBANCO", ".....................................");
			}else{
			parametro.put("P_NOMBREBANCO", ".....................................");
				}
	        /**/
	        calcularGarantesValidos();
			
			if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null && !beanExpedienteCredito.getListaRequisitoCreditoComp().isEmpty()) {
				listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
			}

			if (beanExpedienteCredito.getListaGarantiaCredito() != null && beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
				for (int k=0; k<beanExpedienteCredito.getListaGarantiaCredito().size(); k++) { //
					switch(k){
					case 0:
					{
						Persona personaGarante = new Persona();
						personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
						
						parametro.put("P_IDGARANTE", personaGarante.getIntIdPersona());
						parametro.put("P_IDGARANTE1", "");
						parametro.put("P_IDGARANTE2", "");
						parametro.put("P_IDGARANTE3", "");
						for(Documento listDoc : personaGarante.getListaDocumento()){
						if(listDoc.getIntEstadoCod()==1 && listDoc.getIntTipoIdentidadCod()==1){
							String dniGarante = listDoc.getStrNumeroIdentidad();
							parametro.put("P_DNIGARANTE", dniGarante);
							parametro.put("P_DNIGARANTE1", "");
							parametro.put("P_DNIGARANTE2", "");
							parametro.put("P_DNIGARANTE3", "");
						}
					}
						parametro.put("P_CENTROTRABAJOGARANTE", personaGarante.getNatural().getPerLaboral().getStrCentroTrabajo());
						parametro.put("P_CENTROTRABAJOGARANTE1", "");
						parametro.put("P_CENTROTRABAJOGARANTE2", "");
						parametro.put("P_CENTROTRABAJOGARANTE3", "");
						for(Comunicacion listaComunicacion : personaGarante.getListaComunicacion()) {
							if(listaComunicacion.getIntTipoComunicacionCod()==1 && 
									listaComunicacion.getIntEstadoCod()==1 &&
									listaComunicacion.getIntTipoLineaCod()==1 && listaComunicacion.getIntNumero()!=null){
								parametro.put("P_TELEFONOGARANTE", listaComunicacion.getIntNumero());
							}else{
									parametro.put("P_TELEFONOGARANTE", "");
							break;
							}
						}
						parametro.put("P_TELEFONOGARANTE1", "");
						parametro.put("P_TELEFONOGARANTE2", "");
						parametro.put("P_TELEFONOGARANTE3", "");
						String strVia1=null;
			            Tabla tablaVia1 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), personaGarante.getListaDomicilio().get(0).getIntTipoViaCod());
			            if(tablaVia1!=null){
			            	strVia1 = tablaVia1.getStrDescripcion();
			            }
						parametro.put("P_DIRECCIONGARANTE", strVia1+" "+(personaGarante.getListaDomicilio().get(0).getStrNombreVia()!=null?personaGarante.getListaDomicilio().get(0).getStrNombreVia():" ")
															+ " " + (personaGarante.getListaDomicilio().get(0).getIntNumeroVia()!=null?personaGarante.getListaDomicilio().get(0).getIntNumeroVia():" ")
															+ " " +(personaGarante.getListaDomicilio().get(0).getStrInterior()!=null?personaGarante.getListaDomicilio().get(0).getStrInterior():" "));
						parametro.put("P_DIRECCIONGARANTE1", "");
						parametro.put("P_DIRECCIONGARANTE2", "");
						parametro.put("P_DIRECCIONGARANTE3", "");
						parametro.put("P_NOMBREGARANTE", personaGarante.getNatural().getStrApellidoPaterno().toUpperCase() 
										+ " " + personaGarante.getNatural().getStrApellidoMaterno().toUpperCase() 
										+ ", " + personaGarante.getNatural().getStrNombres().toUpperCase());
						parametro.put("P_NOMBREGARANTE1", "");
						parametro.put("P_NOMBREGARANTE2", "");
						parametro.put("P_NOMBREGARANTE3", "");
						break;
					}
					case 1:
					{
						Persona personaGarante = new Persona();
						personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
						
						parametro.put("P_IDGARANTE1", personaGarante.getIntIdPersona());
						parametro.put("P_IDGARANTE2", "");
						parametro.put("P_IDGARANTE3", "");
						for(Documento listDoc : personaGarante.getListaDocumento()){
							if(listDoc.getIntEstadoCod()==1 && listDoc.getIntTipoIdentidadCod()==1){
								String dniGarante = listDoc.getStrNumeroIdentidad();
							parametro.put("P_DNIGARANTE1", dniGarante);
							parametro.put("P_DNIGARANTE2", "");
							parametro.put("P_DNIGARANTE3", "");

					}
				}
						parametro.put("P_CENTROTRABAJOGARANTE1", personaGarante.getNatural().getPerLaboral().getStrCentroTrabajo());
						parametro.put("P_CENTROTRABAJOGARANTE2", "");
						parametro.put("P_CENTROTRABAJOGARANTE3", "");
						for(Comunicacion listaComunicacion : personaGarante.getListaComunicacion()) {
							if(listaComunicacion.getIntTipoComunicacionCod()==1 && 
									listaComunicacion.getIntEstadoCod()==1 &&
									listaComunicacion.getIntTipoLineaCod()==1 && listaComunicacion.getIntNumero()!=null){
								parametro.put("P_TELEFONOGARANTE1", listaComunicacion.getIntNumero());
							}else{
									parametro.put("P_TELEFONOGARANTE1", "");	
								break;
								}
						}
						parametro.put("P_TELEFONOGARANTE2", "");
						parametro.put("P_TELEFONOGARANTE3", "");
						String strVia1=null;
			            Tabla tablaVia1 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), personaGarante.getListaDomicilio().get(0).getIntTipoViaCod());
			            if(tablaVia1!=null){
			            	strVia1 = tablaVia1.getStrDescripcion();
			            }
						parametro.put("P_DIRECCIONGARANTE1", strVia1+" "+(personaGarante.getListaDomicilio().get(0).getStrNombreVia()!=null?personaGarante.getListaDomicilio().get(0).getStrNombreVia():" ")
															+ " " + (personaGarante.getListaDomicilio().get(0).getIntNumeroVia()!=null?personaGarante.getListaDomicilio().get(0).getIntNumeroVia():" ")
															+ " " +(personaGarante.getListaDomicilio().get(0).getStrInterior()!=null?personaGarante.getListaDomicilio().get(0).getStrInterior():" "));
						parametro.put("P_DIRECCIONGARANTE2", "");
						parametro.put("P_DIRECCIONGARANTE3", "");
						parametro.put("P_NOMBREGARANTE1", personaGarante.getNatural().getStrApellidoPaterno().toUpperCase() 
														+ " " + personaGarante.getNatural().getStrApellidoMaterno().toUpperCase() 
														+ ", " + personaGarante.getNatural().getStrNombres().toUpperCase());
						parametro.put("P_NOMBREGARANTE2", "");
						parametro.put("P_NOMBREGARANTE3", "");
						break;
					}
					case 2:
					{
						Persona personaGarante = new Persona();
						personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
						
						parametro.put("P_IDGARANTE2", personaGarante.getIntIdPersona());
						parametro.put("P_IDGARANTE3", "");
						for(Documento listDoc : personaGarante.getListaDocumento()){
							if(listDoc.getIntEstadoCod()==1 && listDoc.getIntTipoIdentidadCod()==1){
								String dniGarante = listDoc.getStrNumeroIdentidad();
							parametro.put("P_DNIGARANTE2", dniGarante);
							parametro.put("P_DNIGARANTE3", "");
						}
					}
						parametro.put("P_CENTROTRABAJOGARANTE2", personaGarante.getNatural().getPerLaboral().getStrCentroTrabajo());
						parametro.put("P_CENTROTRABAJOGARANTE3", "");
						for(Comunicacion listaComunicacion : personaGarante.getListaComunicacion()) {
							if(listaComunicacion.getIntTipoComunicacionCod()==1 && 
									listaComunicacion.getIntEstadoCod()==1 &&
									listaComunicacion.getIntTipoLineaCod()==1 && listaComunicacion.getIntNumero()!=null){
								parametro.put("P_TELEFONOGARANTE2", listaComunicacion.getIntNumero());
							}else{
								parametro.put("P_TELEFONOGARANTE2", "");
							break;
							}
						}
						parametro.put("P_TELEFONOGARANTE3", "");
						String strVia1=null;
			            Tabla tablaVia1 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), personaGarante.getListaDomicilio().get(0).getIntTipoViaCod());
			            if(tablaVia1!=null){
			            	strVia1 = tablaVia1.getStrDescripcion();
			            }
						parametro.put("P_DIRECCIONGARANTE2", strVia1+" "+(personaGarante.getListaDomicilio().get(0).getStrNombreVia()!=null?personaGarante.getListaDomicilio().get(0).getStrNombreVia():" ")
															+ " " + (personaGarante.getListaDomicilio().get(0).getIntNumeroVia()!=null?personaGarante.getListaDomicilio().get(0).getIntNumeroVia():" ")
															+ " " +(personaGarante.getListaDomicilio().get(0).getStrInterior()!=null?personaGarante.getListaDomicilio().get(0).getStrInterior():" "));
						parametro.put("P_DIRECCIONGARANTE3", "");
						parametro.put("P_NOMBREGARANTE2", personaGarante.getNatural().getStrApellidoPaterno().toUpperCase() 
															+ " " + personaGarante.getNatural().getStrApellidoMaterno().toUpperCase() 
															+ ", " + personaGarante.getNatural().getStrNombres().toUpperCase());
						parametro.put("P_NOMBREGARANTE3", "");
						break;
					}
					case 3:
					{
						Persona personaGarante = new Persona();
						personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
						
						parametro.put("P_IDGARANTE3", personaGarante.getIntIdPersona());
						for(Documento listDoc : personaGarante.getListaDocumento()){
							if(listDoc.getIntEstadoCod()==1 && listDoc.getIntTipoIdentidadCod()==1){
								String dniGarante = listDoc.getStrNumeroIdentidad();
						parametro.put("P_DNIGARANTE3", dniGarante);
						}
					}
						parametro.put("P_CENTROTRABAJOGARANTE3", personaGarante.getNatural().getPerLaboral().getStrCentroTrabajo());
						for(Comunicacion listaComunicacion : personaGarante.getListaComunicacion()) {
							if(listaComunicacion.getIntTipoComunicacionCod()==1 && 
									listaComunicacion.getIntEstadoCod()==1 &&
									listaComunicacion.getIntTipoLineaCod()==1 && listaComunicacion.getIntNumero()!=null){
								parametro.put("P_TELEFONOGARANTE3", listaComunicacion.getIntNumero());
							}else{
								parametro.put("P_TELEFONOGARANTE3", "");
								
							break;
							}
							String strVia1=null;
				            Tabla tablaVia1 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), personaGarante.getListaDomicilio().get(0).getIntTipoViaCod());
				            if(tablaVia1!=null){
				            	strVia1 = tablaVia1.getStrDescripcion();
				            }
						parametro.put("P_DIRECCIONGARANTE3", strVia1+" "+(personaGarante.getListaDomicilio().get(0).getStrNombreVia()!=null?personaGarante.getListaDomicilio().get(0).getStrNombreVia():" ")
															+ " " + (personaGarante.getListaDomicilio().get(0).getIntNumeroVia()!=null?personaGarante.getListaDomicilio().get(0).getIntNumeroVia():" ")
															+ " " +(personaGarante.getListaDomicilio().get(0).getStrInterior()!=null?personaGarante.getListaDomicilio().get(0).getStrInterior():" "));
						parametro.put("P_NOMBREGARANTE3", personaGarante.getNatural().getStrApellidoPaterno().toUpperCase() 
															+ " " + personaGarante.getNatural().getStrApellidoMaterno().toUpperCase() 
															+ ", " + personaGarante.getNatural().getStrNombres().toUpperCase());
						
						break;
					}
				}
			}
		}
	}else{
		parametro.put("P_NOMBREGARANTE", "");
		parametro.put("P_NOMBREGARANTE1", "");
		parametro.put("P_NOMBREGARANTE2", "");
		parametro.put("P_NOMBREGARANTE3", "");
		parametro.put("P_DNIGARANTE", "");
		parametro.put("P_DNIGARANTE1", "");
		parametro.put("P_DNIGARANTE2", "");
		parametro.put("P_DNIGARANTE3", "");
		parametro.put("P_DIRECCIONGARANTE", "");
		parametro.put("P_DIRECCIONGARANTE1", "");
		parametro.put("P_DIRECCIONGARANTE2", "");
		parametro.put("P_DIRECCIONGARANTE3", "");
	}
			
	        /*Aqui Finaliza los parametros de las siguientes 2 hojas*/
	        System.out.println("Parametro " + parametro);
			prestamo.setStrAbreviatura(" ");
			lstParaQuePinteReporte.add(prestamo);
			
			strNombreReporte = "solicitudDePrestamo";
			//strNombreReporte = "jasperPadre";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteReporte), Constante.PARAM_T_TIPOREPORTE_PDF);
					
		} catch (Exception e) {
			log.error("Error en imprimirSolicitudPrestamo ---> "+e);
		}   	
    }
    
    /**
     * 
     * @param Rvillarral
     * @param dtFechaFin
     * @return
     * @throws Exception
     */
    
    public void imprimirAutorizacionDescuento(){
    	String strNombreReporte = " ";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	lstParaQuePinteReporte.clear();
//    	Tabla cargoLaboral = new Tabla();
    	Tabla cargoLaboral = null;
    	List<EstadoCredito> lstEstadoCredito = new ArrayList<EstadoCredito>();
		try {
			ContactoFacadeRemote contactoFacade =(ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			EstructuraFacadeRemote facade1 = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
	        autorizacion.setStrAbreviatura(" ");
			lstParaQuePinteReporte.add(autorizacion);
			
			irVerSolicitudPrestamo(null);
			
			cargoLaboral = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_CARGOPERSONAL), 
					beanSocioComp.getPersona().getNatural().getPerLaboral().getIntCargo());
					
			if(cargoLaboral!=null){
				parametro.put("P_CARGOlABORAL", cargoLaboral.getStrDescripcion());
			}else{
				parametro.put("P_CARGOlABORAL", ".......................................................");
			}
			parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
			String nombre = beanSocioComp.getPersona().getNatural().getStrNombres()!=null?beanSocioComp.getPersona().getNatural().getStrNombres(): " ";
			String apellidoPaterno = beanSocioComp.getPersona().getNatural().getStrApellidoPaterno()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoPaterno():" ";
			String apellidoMaterno = beanSocioComp.getPersona().getNatural().getStrApellidoMaterno()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoMaterno(): " ";
	        parametro.put("P_NOMYAPELLIDO", apellidoPaterno.toUpperCase() +" "+ apellidoMaterno.toUpperCase()+ ", " + nombre.toUpperCase());
			if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			}
			List<Domicilio> lstDomicilio = contactoFacade.getListaDomicilio(beanSocioComp.getPersona().getIntIdPersona());
			Domicilio dom = new Domicilio();
			if(lstDomicilio != null && lstDomicilio.size()>=1){
				dom = lstDomicilio.get(0);
			}
			 String strVia=null;
	            Tabla tablaVia = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), dom.getIntTipoViaCod());
	            System.out.println("Nombre via " + strVia);
	            if(tablaVia!=null){
	            	strVia = tablaVia.getStrDescripcion();
	            }
			parametro.put("P_DIRECCION", strVia + " " + (dom.getStrNombreVia()!=null?dom.getStrNombreVia(): " ") + " " + (dom.getIntNumeroVia()!=null?dom.getIntNumeroVia(): " ") + " " + (dom.getIntInterior()!=null?dom.getIntInterior(): " "));
			
			listEstructura = facade1.getListaEstructuraPorNivelCodigo(beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntNivel(),
																	  beanSocioComp.getSocio().getListSocioEstructura().get(0).getIntCodigo());
			parametro.put("P_DEPENDENCIA", listEstructura.get(0).getJuridica().getStrRazonSocial()!=null?listEstructura.get(0).getJuridica().getStrRazonSocial():"");
			lstEstadoCredito = solicitudPrestamoFacadeNuevo.getListaPorExpedienteCreditoPkYEstadoCredito(beanExpedienteCredito.getId(),new Integer(2));
				
			if (lstEstadoCredito!=null && !lstEstadoCredito.isEmpty()) {
				String dia = Constante.sdfDia.format(lstEstadoCredito.get(0).getTsFechaEstado());
				String mes = Constante.sdfMes.format(lstEstadoCredito.get(0).getTsFechaEstado());
				String anio = Constante.sdfAnno.format(lstEstadoCredito.get(0).getTsFechaEstado());	
				int numero = 0;
				numero = Integer.parseInt(mes);
				
				switch(numero){
				case 1:
				{
					parametro.put("P_FECHAESTADO", dia +" de Enero del "+ anio);
					break;
				}
				case 2:
				{
					parametro.put("P_FECHAESTADO", dia +" de Febrero del "+ anio);
					break;
				}
				case 3:
				{
					parametro.put("P_FECHAESTADO", dia +" de Marzo del "+ anio);
					break;
				}
				case 4:
				{
					parametro.put("P_FECHAESTADO", dia +" de Abril del "+ anio);
					break;
				}
				case 5:
				{
					parametro.put("P_FECHAESTADO", dia +" de Mayo del "+ anio);
					break;
				}
				case 6:
				{
					parametro.put("P_FECHAESTADO", dia +" de Junio del "+ anio);
					break;
				}
				case 7:
				{
					parametro.put("P_FECHAESTADO", dia +" de Julio del "+ anio);
					break;
				}
				case 8:
				{
					parametro.put("P_FECHAESTADO", dia +" de Agosto del "+ anio);
					break;
				}
				case 9:
				{
					parametro.put("P_FECHAESTADO", dia +" de Septiembre del "+ anio);
					break;
				}
				case 10:
				{
					parametro.put("P_FECHAESTADO", dia +" de Octubre del "+ anio);
					break;
				}
				case 11:
				{
					parametro.put("P_FECHAESTADO", dia +" de Noviembre del "+ anio);
					break;
				}
				case 12:
				{
					parametro.put("P_FECHAESTADO", dia +" de Diciembre del "+ anio);
					break;
				}
				
			}
		}
			strNombreReporte = "autorizacionDeDescuento";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteReporte), Constante.PARAM_T_TIPOREPORTE_PDF);
					
		} catch (Exception e) {
			log.error("Error en imprimirAutorizacionDescuento ---> "+e);
		}   	
    }      
    
    public void imprimirAutorizacionDescuentoCesantes(){
    	String strNombreReporte = " ";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	Tabla regimenLaboral = new Tabla();
    	lstParaQuePinteReporte.clear();
		try {
			//mostrarMensaje = Boolean.TRUE;
			irVerSolicitudPrestamo(null);
			autorizacion.setStrAbreviatura(" ");
			lstParaQuePinteReporte.add(autorizacion);
			
			regimenLaboral = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_REGIMENLABORAL), 
					beanSocioComp.getPersona().getNatural().getPerLaboral().getIntRegimenLaboral());
			if(regimenLaboral!=null){
				parametro.put("P_REGIMENLAB", regimenLaboral.getStrDescripcion());
			}else{
				parametro.put("P_REGIMENLAB", " ");
			}
			//irVerSolicitudPrestamo(null);
			parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
	        parametro.put("P_NOMYAPELLIDO", beanSocioComp.getPersona().getNatural().getStrApellidoPaterno() +" "+ beanSocioComp.getPersona().getNatural().getStrApellidoMaterno()+", "+beanSocioComp.getPersona().getNatural().getStrNombres());
			if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			}
			System.out.println("Parametro " + parametro);
	       	strNombreReporte = "autorizacionDeDescuentoCesantes";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteReporte), Constante.PARAM_T_TIPOREPORTE_PDF);
		
		} catch (Exception e) {
			log.error("Error en imprimirPlanillaPrestamo ---> "+e);
		}   	
    }      
    
    public void imprimirautorizacionDscto100(){
    	String strNombreReporte = " ";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	lstParaQuePinteReporte.clear();

		try {
			autorizacion.setStrAbreviatura(" ");
			lstParaQuePinteReporte.add(autorizacion);
		
			irVerSolicitudPrestamo(null);
			parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
	        parametro.put("P_NOMYAPELLIDO", beanSocioComp.getPersona().getNatural().getStrApellidoPaterno() +" "+ beanSocioComp.getPersona().getNatural().getStrApellidoMaterno() +", "+ beanSocioComp.getPersona().getNatural().getStrNombres());
			if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			}
			parametro.put("P_DEPENDENCIA", beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(0).getStrCentroTrabajo());

			strNombreReporte = "autorizacionDscto100";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteReporte), Constante.PARAM_T_TIPOREPORTE_PDF);
					
		} catch (Exception e) {
			log.error("Error en imprimirPlanillaPrestamo ---> "+e);
		}   	
    }
    /**
     * 
     * @param 02/05/2014
     * @param rvillarreal
     * @return
     * @throws Imprime el formato de Declaración Jurada
     */
    public void imprimirDeclaraciónJurada(){
    	String strNombreReporte = " ";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	lstParaQuePinteReporte.clear();
    	Tabla nombreBanco = new Tabla();
    	List<Movimiento> lstMovimientoCtaCte= null;
    	Movimiento movCtaCte = null;
    	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);
		try {
			EgresoFacadeRemote egresoFacade = (EgresoFacadeRemote)EJBFactory.getRemote(EgresoFacadeRemote.class);
			//CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			autorizacion.setStrAbreviatura(" ");
			lstParaQuePinteReporte.add(autorizacion);
			irVerSolicitudPrestamo(null);
			
//			parametro.put("P_NUMEROCHEQUE", beanExpedienteCredito.getEgreso().getIntNumeroCheque()); 
			lstMovimientoCtaCte = conceptoFacade.getListXCtaExpediente(beanExpedienteCredito.getId().getIntPersEmpresaPk(), beanExpedienteCredito.getId().getIntCuentaPk()
												, beanExpedienteCredito.getId().getIntItemExpediente(), beanExpedienteCredito.getId().getIntItemDetExpediente());
			
			if (lstMovimientoCtaCte!=null && !lstMovimientoCtaCte.isEmpty()) {
				movCtaCte = lstMovimientoCtaCte.get(0);
				if(movCtaCte!=null){
				if (movCtaCte.getIntItemEgresoGeneral()!=null) {
					Egreso egreso = null;
					EgresoId gId = new EgresoId();
					gId.setIntPersEmpresaEgreso(movCtaCte.getIntPersEmpresaEgreso());
					gId.setIntItemEgresoGeneral(movCtaCte.getIntItemEgresoGeneral());
					egreso = egresoFacade.getEgresoPorId(gId);
					if(egreso!=null){
						parametro.put("P_NUMEROCHEQUE", egreso.getIntNumeroCheque());
					}else{
						parametro.put("P_NUMEROCHEQUE", " ");
					}
				}else{
					parametro.put("P_NUMEROCHEQUE", " ");
				}
			}else{
				parametro.put("P_NUMEROCHEQUE", " ");
			}
		}else{
			parametro.put("P_NUMEROCHEQUE", " ");
		}
			if(!beanSocioComp.getPersona().getListaCuentaBancaria().isEmpty()){
				nombreBanco = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.NOMBRE_BANCO), beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntBancoCod());
					if(beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntEstadoCod()==1 && beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntMarcaAbono()==1)
						parametro.put("P_NOMBREBANCO", nombreBanco.getStrDescripcion());
					else
						parametro.put("P_NOMBREBANCO", " ");
		}else{
			parametro.put("P_NOMBREBANCO", " ");
				}
			//listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), "B");
			Credito creditoRecuperado = new Credito();
			CreditoId creditoRecuperadoId = new CreditoId();
			creditoRecuperadoId.setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
			creditoRecuperadoId.setIntParaTipoCreditoCod(beanExpedienteCredito.getIntParaTipoCreditoCod());
			creditoRecuperadoId.setIntPersEmpresaPk(beanExpedienteCredito.getIntPersEmpresaCreditoPk());
			creditoRecuperado = creditoFacade.getCreditoPorIdCredito(creditoRecuperadoId);
			beanCredito = creditoRecuperado;
			
			parametro.put("P_TIPOPRESTAMO", creditoRecuperado.getStrDescripcion());
			parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
			String nombre = beanSocioComp.getPersona().getNatural().getStrNombres()!=null?beanSocioComp.getPersona().getNatural().getStrNombres(): " ";
			String apellidoPaterno = beanSocioComp.getPersona().getNatural().getStrApellidoPaterno()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoPaterno():" ";
			String apellidoMaterno = beanSocioComp.getPersona().getNatural().getStrApellidoMaterno()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoMaterno(): " ";
	        parametro.put("P_NOMYAPELLIDO", apellidoPaterno.toUpperCase() +" "+ apellidoMaterno.toUpperCase() + ", " + nombre.toUpperCase());
			if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			}
			List<Comunicacion> lstComunicacion = beanSocioComp.getPersona().getListaComunicacion();
	        String telefFijo = " ";
            String telefMovil = " ";
	        	if(lstComunicacion != null && !lstComunicacion.isEmpty()){
	        	for (Comunicacion com : lstComunicacion) {
					if(com.getIntTipoComunicacionCod()!= null){
						if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO) &&
                    			com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_FIJO) && com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION)){
							
							telefFijo = String.valueOf(com.getIntNumero())!=null?String.valueOf(com.getIntNumero()): " ";
						}
						else if (com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO) && com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION) &&
                    			(com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_CLARO) ||
                    					com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_MOVISTAR) ||
                    							com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_NEXTEL))) {
                    		telefMovil = String.valueOf(com.getIntNumero())!=null?String.valueOf(com.getIntNumero()): " ";
                    		
                    	}
					}
				}
	        	
	        }
	        parametro.put("P_COMUCASA", telefFijo);
	        parametro.put("P_COMUMOVIL", telefMovil);
	        List<CuentaBancaria> lstCuentaBancaria = beanSocioComp.getPersona().getListaCuentaBancaria();
	        CuentaBancaria cuentaBan = new CuentaBancaria();
			if(lstCuentaBancaria != null && lstCuentaBancaria.size()>=1 && !lstCuentaBancaria.isEmpty()){
				cuentaBan = lstCuentaBancaria.get(0);
				if(cuentaBan.getIntEstadoCod()==1 && cuentaBan.getIntMarcaAbono()==1)
					parametro.put("P_CUENTABANCARIA", cuentaBan.getStrNroCuentaBancaria());
			}else{
				parametro.put("P_CUENTABANCARIA", " ");
			}
	        BigDecimal montoSolicitado = beanExpedienteCredito.getBdMontoTotal();
	        parametro.put("P_MONTOTOTAL", formato.format(montoSolicitado));
	        String letras=ConvertirNumeroLetras.convertNumberToLetter(formato.format(montoSolicitado).toString());
	        parametro.put("P_MONTOENLETRAS", letras);
	        listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
	        parametro.put("P_AGENCIA", listaSucursal.get(0).getJuridica().getStrSiglas());
	        //se obtiene la fecha actual
			Date hoy = new Date();
			String dia = Constante.sdfDia.format(hoy);
			String mes = Constante.sdfMes.format(hoy);
			String anio = Constante.sdfAnno.format(hoy);
			//parametro.put("P_FECHAHOY", hoy);
			int numero = 0;
			numero = Integer.parseInt(mes);
			switch(numero){
			case 1:
			{
				parametro.put("P_FECHAHOY", dia +" de Enero del "+ anio);
				break;
			}
			case 2:
			{
				parametro.put("P_FECHAHOY", dia +" de Febrero del "+ anio);
				break;
			}
			case 3:
			{
				parametro.put("P_FECHAHOY", dia +" de Marzo del "+ anio);
				break;
			}
			case 4:
			{
				parametro.put("P_FECHAHOY", dia +" de Abril del "+ anio);
				break;
			}
			case 5:
			{
				parametro.put("P_FECHAHOY", dia +" de Mayo del "+ anio);
				break;
			}
			case 6:
			{
				parametro.put("P_FECHAHOY", dia +" de Junio del "+ anio);
				break;
			}
			case 7:
			{
				parametro.put("P_FECHAHOY", dia +" de Julio del "+ anio);
				break;
			}
			case 8:
			{
				parametro.put("P_FECHAHOY", dia +" de Agosto del "+ anio);
				break;
			}
			case 9:
			{
				parametro.put("P_FECHAHOY", dia +" de Septiembre del "+ anio);
				break;
			}
			case 10:
			{
				parametro.put("P_FECHAHOY", dia +" de Octubre del "+ anio);
				break;
			}
			case 11:
			{
				parametro.put("P_FECHAHOY", dia +" de Noviembre del "+ anio);
				break;
			}
			case 12:
			{
				parametro.put("P_FECHAHOY", dia +" de Diciembre del "+ anio);
				break;
			}
			
		}
			System.out.println("Parametro "+ parametro);
			strNombreReporte = "declaracionJurada";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteReporte), Constante.PARAM_T_TIPOREPORTE_PDF);
					
		} catch (Exception e) {
			log.error("Error en imprimirDeclaraciónJurada ---> "+e);
		}   	
    } 
    
    /**
     * 
     * @param 07/05/2014
     * @param RVILLARREAL
     * @return
     * @throws Exception
     */
    
    public void imprimirPagare(){
    	String strNombreReporte = " ";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	lstParaQuePinteReporte.clear();
    	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);

		try {
			ContactoFacadeRemote contactoFacade =(ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			autorizacion.setStrAbreviatura(" ");
			lstParaQuePinteReporte.add(autorizacion);
			irVerSolicitudPrestamo(null);
			
			parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
			String nombre = beanSocioComp.getPersona().getNatural().getStrNombres()!=null?beanSocioComp.getPersona().getNatural().getStrNombres(): " ";
			String apellidoPaterno = beanSocioComp.getPersona().getNatural().getStrApellidoPaterno()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoPaterno():" ";
			String apellidoMaterno = beanSocioComp.getPersona().getNatural().getStrApellidoMaterno()!=null?beanSocioComp.getPersona().getNatural().getStrApellidoMaterno(): " ";
	        parametro.put("P_NOMYAPELLIDO", apellidoPaterno.toUpperCase() +" "+ apellidoMaterno.toUpperCase()+ ", " + nombre.toUpperCase());
			if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
				String docIdentidad = beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad();
				String uno = docIdentidad.substring(0, 1);
				String dos = docIdentidad.substring(1, 2);
				String tres = docIdentidad.substring(2, 3);
				String cuatro = docIdentidad.substring(3, 4);
				String cinco = docIdentidad.substring(4, 5);
				String seis = docIdentidad.substring(5, 6);
				String siete = docIdentidad.substring(6, 7);
				String ocho = docIdentidad.substring(7, 8);

				parametro.put("P1",  uno);
				parametro.put("P2",  dos);
				parametro.put("P3",  tres);
				parametro.put("P4",  cuatro);
				parametro.put("P5",  cinco);
				parametro.put("P6",  seis);
				parametro.put("P7",  siete);
				parametro.put("P8",  ocho);
			}
//			List<Comunicacion> lstComunicacion = beanSocioComp.getPersona().getListaComunicacion();
//	        String telefFijo = " ";
//            String telefMovil = " ";
//	        	if(lstComunicacion != null && !lstComunicacion.isEmpty()){
//	        	for (Comunicacion com : lstComunicacion) {
//					if(com.getIntTipoComunicacionCod()!= null){
//						if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO) &&
//                    			com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_FIJO) && com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION)){
//							
//							telefFijo = String.valueOf(com.getIntNumero())!=null?String.valueOf(com.getIntNumero()): " ";
//						}
//						else if (com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO) && com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION) &&
//                    			(com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_CLARO) ||
//                    					com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_MOVISTAR) ||
//                    							com.getIntTipoLineaCod().equals(Constante.PARAM_T_TIPOLINEATELEF_NEXTEL)) &&
//                    							com.getIntSubTipoComunicacionCod().equals(Constante.PARAM_T_COMUNICACIONPERSONAL)) {
//                    		telefMovil = String.valueOf(com.getIntNumero())!=null?String.valueOf(com.getIntNumero()): " ";
//                     	}
//					}
//				}
//	        }
//	        //if((telefFijo!=null && !telefFijo.isEmpty()) || (telefMovil!=null && !telefMovil.isEmpty())){
//	        if(telefFijo!=null && !telefFijo.trim().isEmpty())
//	        	parametro.put("P_COMUCASA", telefFijo);
//	        	
//	        else if(telefMovil!=null && !telefMovil.trim().isEmpty())
//	        	parametro.put("P_COMUCASA", telefMovil);
//	        else
//	        	parametro.put("P_COMUCASA", " ");
			List<Comunicacion> lstComunicacion = beanSocioComp.getPersona().getListaComunicacion();
	           
	           Integer numero1=null;
	           if(lstComunicacion!=null && !lstComunicacion.isEmpty()){
	        	   int prioridad = 99;
	        	   for(Comunicacion com : lstComunicacion){
	           		   if(com.getIntSubTipoComunicacionCod()==4 && com.getIntTipoComunicacionCod()==1 && prioridad>5){
	        			   prioridad = 5;
	        			   numero1 =com.getIntNumero();
	        	       }else if(com.getIntSubTipoComunicacionCod()==3 && com.getIntTipoComunicacionCod()==1 && prioridad>4){
	        	    	   prioridad = 4;
	        	    	   numero1 =com.getIntNumero();
	        		   }else if(com.getIntSubTipoComunicacionCod()==2 && com.getIntTipoComunicacionCod()==1 && prioridad>3){
	        	    	   prioridad = 3;
	        	    	   numero1 =com.getIntNumero();
	        		   }else if(com.getIntSubTipoComunicacionCod()==1 && com.getIntTipoComunicacionCod()==1 && prioridad>2){
	        	    	   prioridad = 2;
	        	    	   numero1 =com.getIntNumero();
	        		   }else if(com.getIntSubTipoComunicacionCod()==5 && com.getIntTipoComunicacionCod()==1 && prioridad>1){
	        	    	   prioridad = 1;
	        	    	   numero1 =com.getIntNumero();
	        		   }
	        	   	}
	           	}
	           parametro.put("P_COMUCASA", numero1);
	        List<Domicilio> lstDomicilio = contactoFacade.getListaDomicilio(beanSocioComp.getPersona().getIntIdPersona());
			Domicilio dom = new Domicilio();
			if(lstDomicilio != null && lstDomicilio.size()>=1){
				dom = lstDomicilio.get(0);
			}
			  String strVia=null;
	            Tabla tablaVia = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), dom.getIntTipoViaCod());
	            System.out.println("Nombre via " + strVia);
	            if(tablaVia!=null){
	            	strVia = tablaVia.getStrDescripcion();
	            }
			parametro.put("P_DIRECCION", strVia+ " " +(dom.getStrNombreVia()!=null?dom.getStrNombreVia(): " ") + " " + (dom.getIntNumeroVia()!=null?dom.getIntNumeroVia(): " ") + " " + (dom.getIntInterior()!=null?dom.getIntInterior(): " "));
			parametro.put("P_DEPENDENCIA", beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(0).getStrCentroTrabajo());
			if(beanCredito.getBdTasaMoratoriaAnual()!=null){
				parametro.put("P_TASAANUAL", beanCredito.getBdTasaMoratoriaAnual());
			}else{
				parametro.put("P_TASAANUAL", " ");
			}
			parametro.put("P_TASAMENSUAL", formato.format(beanCredito.getBdTasaMoratoriaMensual()));
			BigDecimal montoSolicitado2 = beanExpedienteCredito.getBdMontoTotal();
			parametro.put("P_MONTOTOTAL", formato.format(montoSolicitado2));
	        String letras=ConvertirNumeroLetras.convertNumberToLetter(formato.format(montoSolicitado2).toString());
	        parametro.put("P_MONTOENLETRAS", letras);
			/**/
			calcularGarantesValidos();
			
			if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null && !beanExpedienteCredito.getListaRequisitoCreditoComp().isEmpty()) {
				listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
			}

			if (beanExpedienteCredito.getListaGarantiaCredito() != null && beanExpedienteCredito.getListaGarantiaCredito().size() > 0) {
				for (int k=0; k<beanExpedienteCredito.getListaGarantiaCredito().size(); k++) { //
					switch(k){
					case 0:
					{
						Persona personaGarante = new Persona();
						personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
						
						parametro.put("P_IDGARANTE", personaGarante.getIntIdPersona());
						parametro.put("P_IDGARANTE1", " ");
						parametro.put("P_IDGARANTE2", " ");
						parametro.put("P_IDGARANTE3", " ");
					for(Documento documentoGar : personaGarante.getListaDocumento()){
						if(documentoGar.getIntEstadoCod()==1 && documentoGar.getIntTipoIdentidadCod()==1){
							String dniGarante = documentoGar.getStrNumeroIdentidad();
							String unoGar = dniGarante.substring(0, 1);
							String dosGar = dniGarante.substring(1, 2);
							String tresGar = dniGarante.substring(2, 3);
							String cuatroGar = dniGarante.substring(3, 4);
							String cincoGar = dniGarante.substring(4, 5);
							String seisGar = dniGarante.substring(5, 6);
							String sieteGar = dniGarante.substring(6, 7);
							String ochoGar = dniGarante.substring(7, 8);

							parametro.put("PG1",  unoGar);
							parametro.put("PG11",  " ");
							parametro.put("PG12",  " ");
							parametro.put("PG13",  " ");
							parametro.put("PG2",  dosGar);
							parametro.put("PG21",  " ");
							parametro.put("PG22",  " ");
							parametro.put("PG23",  " ");
							parametro.put("PG3",  tresGar);
							parametro.put("PG31",  " ");
							parametro.put("PG32",  " ");
							parametro.put("PG33",  " ");
							parametro.put("PG4",  cuatroGar);
							parametro.put("PG41",  " ");
							parametro.put("PG42",  " ");
							parametro.put("PG43",  " ");
							parametro.put("PG5",  cincoGar);
							parametro.put("PG51",  " ");
							parametro.put("PG52",  " ");
							parametro.put("PG53",  " ");
							parametro.put("PG6",  seisGar);
							parametro.put("PG61",  " ");
							parametro.put("PG62",  " ");
							parametro.put("PG63",  " ");
							parametro.put("PG7",  sieteGar);
							parametro.put("PG71",  " ");
							parametro.put("PG72",  " ");
							parametro.put("PG73",  " ");
							parametro.put("PG8",  ochoGar);
							parametro.put("PG81",  " ");
							parametro.put("PG82",  " ");
							parametro.put("PG83",  " ");
						}
					}
						parametro.put("P_CENTROTRABAJOGARANTE", personaGarante.getNatural().getPerLaboral().getStrCentroTrabajo());
						parametro.put("P_CENTROTRABAJOGARANTE1", " ");
						parametro.put("P_CENTROTRABAJOGARANTE2", " ");
						parametro.put("P_CENTROTRABAJOGARANTE3", " ");
						Integer numero=null;
			        	   int prioridad = 99;
			        	   for(Comunicacion listaComunicacion : personaGarante.getListaComunicacion()){
			           		   if(listaComunicacion.getIntSubTipoComunicacionCod()==4 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>5){
			        			   prioridad = 5;
			        			   numero = listaComunicacion.getIntNumero();
			        	       }else if(listaComunicacion.getIntSubTipoComunicacionCod()==3 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>4){
			        	    	   prioridad = 4;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==2 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>3){
			        	    	   prioridad = 3;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==1 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>2){
			        	    	   prioridad = 2;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==5 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>1){
			        	    	   prioridad = 1;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }
			        	   	}
			           parametro.put("P_TELEFONOGARANTE", numero);
						parametro.put("P_TELEFONOGARANTE1", " ");
						parametro.put("P_TELEFONOGARANTE2", " ");
						parametro.put("P_TELEFONOGARANTE3", " ");
						String strVia1=null;
			            Tabla tablaVia1 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), personaGarante.getListaDomicilio().get(0).getIntTipoViaCod());
			            if(tablaVia1!=null){
			            	strVia1 = tablaVia1.getStrDescripcion();
			            }
						parametro.put("P_DIRECCIONGARANTE", strVia1 +" "+(personaGarante.getListaDomicilio().get(0).getStrNombreVia()!=null?personaGarante.getListaDomicilio().get(0).getStrNombreVia():" ")
															+ " " + (personaGarante.getListaDomicilio().get(0).getIntNumeroVia()!=null?personaGarante.getListaDomicilio().get(0).getIntNumeroVia():" ")
															+ " " +(personaGarante.getListaDomicilio().get(0).getStrInterior()!=null?personaGarante.getListaDomicilio().get(0).getStrInterior():" "));
						parametro.put("P_DIRECCIONGARANTE1", " ");
						parametro.put("P_DIRECCIONGARANTE2", " ");
						parametro.put("P_DIRECCIONGARANTE3", " ");
						parametro.put("P_NOMBREGARANTE", personaGarante.getNatural().getStrApellidoPaterno().toUpperCase() 
														+ " " + personaGarante.getNatural().getStrApellidoMaterno().toUpperCase()
														+ " " + personaGarante.getNatural().getStrNombres().toUpperCase());
						parametro.put("P_NOMBREGARANTE1", " ");
						parametro.put("P_NOMBREGARANTE2", " ");
						parametro.put("P_NOMBREGARANTE3", " ");
						break;
					}
					case 1:
					{
						Persona personaGarante = new Persona();
						personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
						
						parametro.put("P_IDGARANTE1", personaGarante.getIntIdPersona());
						parametro.put("P_IDGARANTE2", " ");
						parametro.put("P_IDGARANTE3", " ");
						for(Documento documentoGar : personaGarante.getListaDocumento()){
							if(documentoGar.getIntEstadoCod()==1 && documentoGar.getIntTipoIdentidadCod()==1){
								String dniGarante = documentoGar.getStrNumeroIdentidad();
							String unoGar = dniGarante.substring(0, 1);
							String dosGar = dniGarante.substring(1, 2);
							String tresGar = dniGarante.substring(2, 3);
							String cuatroGar = dniGarante.substring(3, 4);
							String cincoGar = dniGarante.substring(4, 5);
							String seisGar = dniGarante.substring(5, 6);
							String sieteGar = dniGarante.substring(6, 7);
							String ochoGar = dniGarante.substring(7, 8);

							parametro.put("PG11",  unoGar);
							parametro.put("PG12",  " ");
							parametro.put("PG13",  " ");
							parametro.put("PG21",  dosGar);
							parametro.put("PG22",  " ");
							parametro.put("PG23",  " ");
							parametro.put("PG31",  tresGar);
							parametro.put("PG32",  " ");
							parametro.put("PG33",  " ");
							parametro.put("PG41",  cuatroGar);
							parametro.put("PG42",  " ");
							parametro.put("PG43",  " ");
							parametro.put("PG51",  cincoGar);
							parametro.put("PG52",  " ");
							parametro.put("PG53",  " ");
							parametro.put("PG61",  seisGar);
							parametro.put("PG62",  " ");
							parametro.put("PG63",  " ");
							parametro.put("PG71",  sieteGar);
							parametro.put("PG72",  " ");
							parametro.put("PG73",  " ");
							parametro.put("PG81",  ochoGar);
							parametro.put("PG82",  " ");
							parametro.put("PG83",  " ");
						}
					}
						parametro.put("P_CENTROTRABAJOGARANTE1", personaGarante.getNatural().getPerLaboral().getStrCentroTrabajo());
						parametro.put("P_CENTROTRABAJOGARANTE2", " ");
						parametro.put("P_CENTROTRABAJOGARANTE3", " ");
				           Integer numero=null;
				        	   int prioridad = 99;
				        	   for(Comunicacion listaComunicacion : personaGarante.getListaComunicacion()){
				           		   if(listaComunicacion.getIntSubTipoComunicacionCod()==4 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>5){
				        			   prioridad = 5;
				        			   numero = listaComunicacion.getIntNumero();
				        	       }else if(listaComunicacion.getIntSubTipoComunicacionCod()==3 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>4){
				        	    	   prioridad = 4;
				        			   numero =listaComunicacion.getIntNumero();
				        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==2 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>3){
				        	    	   prioridad = 3;
				        			   numero =listaComunicacion.getIntNumero();
				        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==1 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>2){
				        	    	   prioridad = 2;
				        			   numero =listaComunicacion.getIntNumero();
				        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==5 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>1){
				        	    	   prioridad = 1;
				        			   numero =listaComunicacion.getIntNumero();
				        		   }
				        	   	}
				           parametro.put("P_TELEFONOGARANTE1", numero);
				           
						parametro.put("P_TELEFONOGARANTE2", " ");
						parametro.put("P_TELEFONOGARANTE3", " ");
						String strVia1=null;
			            Tabla tablaVia1 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), personaGarante.getListaDomicilio().get(0).getIntTipoViaCod());
			            if(tablaVia1!=null){
			            	strVia1 = tablaVia1.getStrDescripcion();
			            }
						parametro.put("P_DIRECCIONGARANTE1", strVia1+" "+(personaGarante.getListaDomicilio().get(0).getStrNombreVia()!=null?personaGarante.getListaDomicilio().get(0).getStrNombreVia():"")
																+ " " + (personaGarante.getListaDomicilio().get(0).getIntNumeroVia()!=null?personaGarante.getListaDomicilio().get(0).getIntNumeroVia():"")
																+ " " +(personaGarante.getListaDomicilio().get(0).getStrInterior()!=null?personaGarante.getListaDomicilio().get(0).getStrInterior():""));
						parametro.put("P_DIRECCIONGARANTE2", "");
						parametro.put("P_DIRECCIONGARANTE3", "");
						parametro.put("P_NOMBREGARANTE1", personaGarante.getNatural().getStrApellidoPaterno().toUpperCase() 
														+ " " + personaGarante.getNatural().getStrApellidoMaterno().toUpperCase()
														+ " " + personaGarante.getNatural().getStrNombres().toUpperCase()); 
						parametro.put("P_NOMBREGARANTE2", "");
						parametro.put("P_NOMBREGARANTE3", "");
						break;
					}
					case 2:
					{
						Persona personaGarante = new Persona();
						personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
						
						parametro.put("P_IDGARANTE2", personaGarante.getIntIdPersona());
						parametro.put("P_IDGARANTE3", " ");
						for(Documento documentoGar : personaGarante.getListaDocumento()){
							if(documentoGar.getIntEstadoCod()==1 && documentoGar.getIntTipoIdentidadCod()==1){
								String dniGarante = documentoGar.getStrNumeroIdentidad();
							String unoGar = dniGarante.substring(0, 1);
							String dosGar = dniGarante.substring(1, 2);
							String tresGar = dniGarante.substring(2, 3);
							String cuatroGar = dniGarante.substring(3, 4);
							String cincoGar = dniGarante.substring(4, 5);
							String seisGar = dniGarante.substring(5, 6);
							String sieteGar = dniGarante.substring(6, 7);
							String ochoGar = dniGarante.substring(7, 8);

							parametro.put("PG12",  unoGar);
							parametro.put("PG13",  " ");
							parametro.put("PG22",  dosGar);
							parametro.put("PG23",  " ");
							parametro.put("PG32",  tresGar);
							parametro.put("PG33",  " ");
							parametro.put("PG42",  cuatroGar);
							parametro.put("PG43",  " ");
							parametro.put("PG52",  cincoGar);
							parametro.put("PG53",  " ");
							parametro.put("PG62",  seisGar);
							parametro.put("PG63",  " ");
							parametro.put("PG72",  sieteGar);
							parametro.put("PG73",  " ");
							parametro.put("PG82",  ochoGar);
							parametro.put("PG83",  " ");
						}
					}
						parametro.put("P_CENTROTRABAJOGARANTE2", personaGarante.getNatural().getPerLaboral().getStrCentroTrabajo());
						parametro.put("P_CENTROTRABAJOGARANTE3", " ");
						Integer numero=null;
			        	   int prioridad = 99;
			        	   for(Comunicacion listaComunicacion : personaGarante.getListaComunicacion()){
			           		   if(listaComunicacion.getIntSubTipoComunicacionCod()==4 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>5){
			        			   prioridad = 5;
			        			   numero = listaComunicacion.getIntNumero();
			        	       }else if(listaComunicacion.getIntSubTipoComunicacionCod()==3 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>4){
			        	    	   prioridad = 4;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==2 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>3){
			        	    	   prioridad = 3;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==1 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>2){
			        	    	   prioridad = 2;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==5 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>1){
			        	    	   prioridad = 1;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }
			        	   	}
			           parametro.put("P_TELEFONOGARANTE2", numero);
						parametro.put("P_TELEFONOGARANTE3", " ");
						String strVia1=null;
			            Tabla tablaVia1 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), personaGarante.getListaDomicilio().get(0).getIntTipoViaCod());
			            if(tablaVia1!=null){
			            	strVia1 = tablaVia1.getStrDescripcion();
			            }
						parametro.put("P_DIRECCIONGARANTE2", strVia1+" "+(personaGarante.getListaDomicilio().get(0).getStrNombreVia()!=null?personaGarante.getListaDomicilio().get(0).getStrNombreVia():" ")
															+ " " + (personaGarante.getListaDomicilio().get(0).getIntNumeroVia()!=null?personaGarante.getListaDomicilio().get(0).getIntNumeroVia():" ")
															+ " " +(personaGarante.getListaDomicilio().get(0).getStrInterior()!=null?personaGarante.getListaDomicilio().get(0).getStrInterior():" "));
						parametro.put("P_DIRECCIONGARANTE3", " ");
						parametro.put("P_NOMBREGARANTE2", personaGarante.getNatural().getStrApellidoPaterno().toUpperCase() 
														+ " " + personaGarante.getNatural().getStrApellidoMaterno().toUpperCase()
														+ " " + personaGarante.getNatural().getStrNombres().toUpperCase());
						parametro.put("P_NOMBREGARANTE3", " ");
						break;
					}
					case 3:
					{
						Persona personaGarante = new Persona();
						personaGarante = personaFacade.getPersonaNaturalPorIdPersona(beanExpedienteCredito.getListaGarantiaCredito().get(k).getIntPersPersonaGarantePk());
						
						parametro.put("P_IDGARANTE3", personaGarante.getIntIdPersona());
						for(Documento documentoGar : personaGarante.getListaDocumento()){
							if(documentoGar.getIntEstadoCod()==1 && documentoGar.getIntTipoIdentidadCod()==1){
								String dniGarante = documentoGar.getStrNumeroIdentidad();
							String unoGar = dniGarante.substring(0, 1);
							String dosGar = dniGarante.substring(1, 2);
							String tresGar = dniGarante.substring(2, 3);
							String cuatroGar = dniGarante.substring(3, 4);
							String cincoGar = dniGarante.substring(4, 5);
							String seisGar = dniGarante.substring(5, 6);
							String sieteGar = dniGarante.substring(6, 7);
							String ochoGar = dniGarante.substring(7, 8);

							parametro.put("PG13",  unoGar);
							parametro.put("PG23",  dosGar);
							parametro.put("PG33",  tresGar);
							parametro.put("PG43",  cuatroGar);
							parametro.put("PG53",  cincoGar);
							parametro.put("PG63",  seisGar);
							parametro.put("PG73",  sieteGar);
							parametro.put("PG83",  ochoGar);
						}
					}
						parametro.put("P_CENTROTRABAJOGARANTE3", personaGarante.getNatural().getPerLaboral().getStrCentroTrabajo());
						Integer numero=null;
			        	   int prioridad = 99;
			        	   for(Comunicacion listaComunicacion : personaGarante.getListaComunicacion()){
			           		   if(listaComunicacion.getIntSubTipoComunicacionCod()==4 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>5){
			        			   prioridad = 5;
			        			   numero = listaComunicacion.getIntNumero();
			        	       }else if(listaComunicacion.getIntSubTipoComunicacionCod()==3 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>4){
			        	    	   prioridad = 4;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==2 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>3){
			        	    	   prioridad = 3;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==1 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>2){
			        	    	   prioridad = 2;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }else if(listaComunicacion.getIntSubTipoComunicacionCod()==5 && listaComunicacion.getIntTipoComunicacionCod()==1 && prioridad>1){
			        	    	   prioridad = 1;
			        			   numero =listaComunicacion.getIntNumero();
			        		   }
			        	   	}
			           parametro.put("P_TELEFONOGARANTE3", numero);
						String strVia1=null;
			            Tabla tablaVia1 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOVIA), personaGarante.getListaDomicilio().get(0).getIntTipoViaCod());
			            if(tablaVia1!=null){
			            	strVia1 = tablaVia1.getStrDescripcion();
			            }
						parametro.put("P_DIRECCIONGARANTE3", strVia1+" "+(personaGarante.getListaDomicilio().get(0).getStrNombreVia()!=null?personaGarante.getListaDomicilio().get(0).getStrNombreVia():" ")
															+ " " + (personaGarante.getListaDomicilio().get(0).getIntNumeroVia()!=null?personaGarante.getListaDomicilio().get(0).getIntNumeroVia():" ")
															+ " " +(personaGarante.getListaDomicilio().get(0).getStrInterior()!=null?personaGarante.getListaDomicilio().get(0).getStrInterior():" "));
						parametro.put("P_NOMBREGARANTE3", personaGarante.getNatural().getStrApellidoPaterno().toUpperCase() 
															+ " " + personaGarante.getNatural().getStrApellidoMaterno().toUpperCase()
															+ " " + personaGarante.getNatural().getStrNombres().toUpperCase());
						
						break;
					}
				}
			}
		}
			/**/
			System.out.println("parametro " + parametro);
			strNombreReporte = "pagare";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteReporte), Constante.PARAM_T_TIPOREPORTE_PDF);
					
		} catch (Exception e) {
			log.error("Error en imprimirDeclaraciónJurada ---> "+e);
		}   	
    } 
    
    public static Integer obtenerDiasEntreFechas(Date dtFechaInicio, Date dtFechaFin)throws Exception{
		return (int)( (dtFechaFin.getTime() - dtFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
	}   
	public Boolean getBlnMostrarEliminar() {
		return blnMostrarEliminar;
	}
	public void setBlnMostrarEliminar(Boolean blnMostrarEliminar) {
		this.blnMostrarEliminar = blnMostrarEliminar;
	}
	public Boolean getBlnMostrarModificar() {
		return blnMostrarModificar;
	}
	public void setBlnMostrarModificar(Boolean blnMostrarModificar) {
		this.blnMostrarModificar = blnMostrarModificar;
	}
	public ExpedienteCreditoComp getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}
	public void setRegistroSeleccionadoBusqueda(
			ExpedienteCreditoComp registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}
	public Integer getIntTipoConsultaBusqueda() {
		return intTipoConsultaBusqueda;
	}

	public void setIntTipoConsultaBusqueda(Integer intTipoConsultaBusqueda) {
		this.intTipoConsultaBusqueda = intTipoConsultaBusqueda;
	}
	public boolean isBlnBtnAddCapacidad() {
		return blnBtnAddCapacidad;
	}
	public void setBlnBtnAddCapacidad(boolean blnBtnAddCapacidad) {
		this.blnBtnAddCapacidad = blnBtnAddCapacidad;
	}

	public boolean isBlnBtnEditCapacidad() {
		return blnBtnEditCapacidad;
	}
	public void setBlnBtnEditCapacidad(boolean blnBtnEditCapacidad) {
		this.blnBtnEditCapacidad = blnBtnEditCapacidad;
	}
	public boolean isBlnBtnDelCapacidad() {
		return blnBtnDelCapacidad;
	}
	public void setBlnBtnDelCapacidad(boolean blnBtnDelCapacidad) {
		this.blnBtnDelCapacidad = blnBtnDelCapacidad;
	}
	public boolean isBlnBtnEvaluarCredito() {
		return blnBtnEvaluarCredito;
	}
	public void setBlnBtnEvaluarCredito(boolean blnBtnEvaluarCredito) {
		this.blnBtnEvaluarCredito = blnBtnEvaluarCredito;
	}
	public boolean isBlnTextNroCuotas() {
		return blnTextNroCuotas;
	}
	public void setBlnTextNroCuotas(boolean blnTextNroCuotas) {
		this.blnTextNroCuotas = blnTextNroCuotas;
	}
	public boolean isBlnBtnAddGarante() {
		return blnBtnAddGarante;
	}
	public void setBlnBtnAddGarante(boolean blnBtnAddGarante) {
		this.blnBtnAddGarante = blnBtnAddGarante;
	}
	public boolean isBlnBtnDelGarante() {
		return blnBtnDelGarante;
	}
	public void setBlnBtnDelGarante(boolean blnBtnDelGarante) {
		this.blnBtnDelGarante = blnBtnDelGarante;
	}
	public boolean isBlnTxtMotivoPrestamo() {
		return blnTxtMotivoPrestamo;
	}
	public void setBlnTxtMotivoPrestamo(boolean blnTxtMotivoPrestamo) {
		this.blnTxtMotivoPrestamo = blnTxtMotivoPrestamo;
	}
	public boolean isBlnTxtObservacionesPrestamo() {
		return blnTxtObservacionesPrestamo;
	}
	public void setBlnTxtObservacionesPrestamo(boolean blnTxtObservacionesPrestamo) {
		this.blnTxtObservacionesPrestamo = blnTxtObservacionesPrestamo;
	}
	public boolean isBlnBtnAddRequisito() {
		return blnBtnAddRequisito;
	}
	public void setBlnBtnAddRequisito(boolean blnBtnAddRequisito) {
		this.blnBtnAddRequisito = blnBtnAddRequisito;
	}
	public boolean isBlnLnkDescarga() {
		return blnLnkDescarga;
	}
	public void setBlnLnkDescarga(boolean blnLnkDescarga) {
		this.blnLnkDescarga = blnLnkDescarga;
	}
	public boolean isBlnCmbTipoOperacion() {
		return blnCmbTipoOperacion;
	}
	public void setBlnCmbTipoOperacion(boolean blnCmbTipoOperacion) {
		this.blnCmbTipoOperacion = blnCmbTipoOperacion;
	}
	public boolean isBlnTextMontoPrestamo() {
		return blnTextMontoPrestamo;
	}
	public void setBlnTextMontoPrestamo(boolean blnTextMontoPrestamo) {
		this.blnTextMontoPrestamo = blnTextMontoPrestamo;
	}
	public List<CreditoExcepcion> getListaExcepciones() {
		return listaExcepciones;
	}
	public void setListaExcepciones(List<CreditoExcepcion> listaExcepciones) {
		this.listaExcepciones = listaExcepciones;
	}
	public Integer getIntCuotasMaximas() {
		return intCuotasMaximas;
	}
	public void setIntCuotasMaximas(Integer intCuotasMaximas) {
		this.intCuotasMaximas = intCuotasMaximas;
	}
	public String getStrUltimoEstadoSolicitud() {
		return strUltimoEstadoSolicitud;
	}
	public void setStrUltimoEstadoSolicitud(String strUltimoEstadoSolicitud) {
		this.strUltimoEstadoSolicitud = strUltimoEstadoSolicitud;
	}
	public Integer getIntUltimoEstadoSolicitud() {
		return intUltimoEstadoSolicitud;
	}
	public void setIntUltimoEstadoSolicitud(Integer intUltimoEstadoSolicitud) {
		this.intUltimoEstadoSolicitud = intUltimoEstadoSolicitud;
	}
	public Tabla getTablaEstado() {
		return tablaEstado;
	}
	public void setTablaEstado(Tabla tablaEstado) {
		this.tablaEstado = tablaEstado;
	}	
	public Boolean getBlnModoVisualizacion() {
		return blnModoVisualizacion;
	}
	public void setBlnModoVisualizacion(Boolean blnModoVisualizacion) {
		this.blnModoVisualizacion = blnModoVisualizacion;
	}
	public ExpedienteCredito getBeanExpedienteCredito() {
		return beanExpedienteCredito;
	}
	public void setBeanExpedienteCredito(ExpedienteCredito beanExpedienteCredito) {
		this.beanExpedienteCredito = beanExpedienteCredito;
	}
	public Persona getPersonaBusqueda() {
		return personaBusqueda;
	}
	public void setPersonaBusqueda(Persona personaBusqueda) {
		this.personaBusqueda = personaBusqueda;
	}
	public SocioComp getBeanSocioCompGarante() {
		return beanSocioCompGarante;
	}
	public void setBeanSocioCompGarante(SocioComp beanSocioCompGarante) {
		this.beanSocioCompGarante = beanSocioCompGarante;
	}
	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}
	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}
	public Credito getBeanCredito() {
		return beanCredito;
	}
	public void setBeanCredito(Credito beanCredito) {
		this.beanCredito = beanCredito;
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
	public Integer getIntBusqSolicPtmo() {
		return intBusqSolicPtmo;
	}
	public void setIntBusqSolicPtmo(Integer intBusqSolicPtmo) {
		this.intBusqSolicPtmo = intBusqSolicPtmo;
	}
	public String getStrNroSolicitud() {
		return strNroSolicitud;
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
	public Integer getIntTotalSucursales() {
		return intTotalSucursales;
	}
	public void setIntTotalSucursales(Integer intTotalSucursales) {
		this.intTotalSucursales = intTotalSucursales;
	}
	public Integer getIntIdEstadoSolicitud() {
		return intIdEstadoSolicitud;
	}
	public void setIntIdEstadoSolicitud(Integer intIdEstadoSolicitud) {
		this.intIdEstadoSolicitud = intIdEstadoSolicitud;
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
	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}
	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}
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
	public Integer getIntParaTipoOperacionPersona() {
		return intParaTipoOperacionPersona;
	}
	public void setIntParaTipoOperacionPersona(
			Integer intParaTipoOperacionPersona) {
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;
	}
	public List<ExpedienteCreditoComp> getListaExpedienteCreditoComp() {
		return listaExpedienteCreditoComp;
	}
	public void setListaExpedienteCreditoComp(
			List<ExpedienteCreditoComp> listaExpedienteCreditoComp) {
		this.listaExpedienteCreditoComp = listaExpedienteCreditoComp;
	}
	public List<Tabla> getListaSubOpePrestamos() {
		try {
			listaSubOpePrestamos = tablaFacade.getListaTablaPorAgrupamientoA(
					new Integer(Constante.PARAM_T_SUBOPERACIONPRESTAMO), "A");
		} catch (NumberFormatException e) {
			log.error("error: " + e.getMessage());
		} catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
		return listaSubOpePrestamos;
	}
	public void setListaSubOpePrestamos(List<Tabla> listaSubOpePrestamos) {
		this.listaSubOpePrestamos = listaSubOpePrestamos;
	}
	public List<Tabla> getListaRelacion() {
		try {
			listaRelacion = tablaFacade.getListaTablaPorAgrupamientoA(
					new Integer(Constante.PARAM_T_TIPOROL), "E");
		} catch (NumberFormatException e) {
			log.error("error: " + e.getMessage());
		} catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		}
		return listaRelacion;
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
	public List<Tabla> getListaTipoConsultaBusqueda() {
		try {
			listaTipoConsultaBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOBUSQUEDA_COMBO_DRNRZ));		
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		return listaTipoConsultaBusqueda;
	}

	public void setListaTipoConsultaBusqueda(List<Tabla> listaTipoConsultaBusqueda) {
		this.listaTipoConsultaBusqueda = listaTipoConsultaBusqueda;
	}
	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
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
	public List<CapacidadCreditoComp> getListaCapacidadCreditoComp() {
		return listaCapacidadCreditoComp;
	}
	public void setListaCapacidadCreditoComp(
			List<CapacidadCreditoComp> listaCapacidadCreditoComp) {
		this.listaCapacidadCreditoComp = listaCapacidadCreditoComp;
	}
	public List<CuentaConcepto> getListaCuentaConcepto() {
		return listaCuentaConcepto;
	}
	public void setListaCuentaConcepto(List<CuentaConcepto> listaCuentaConcepto) {
		this.listaCuentaConcepto = listaCuentaConcepto;
	}
	public List<GarantiaCreditoComp> getListaGarantiaCreditoComp() {
		return listaGarantiaCreditoComp;
	}
	public void setListaGarantiaCreditoComp(
			List<GarantiaCreditoComp> listaGarantiaCreditoComp) {
		this.listaGarantiaCreditoComp = listaGarantiaCreditoComp;
	}
	public List<CronogramaCreditoComp> getListaCronogramaCreditoComp() {
		return listaCronogramaCreditoComp;
	}
	public void setListaCronogramaCreditoComp(
			List<CronogramaCreditoComp> listaCronogramaCreditoComp) {
		this.listaCronogramaCreditoComp = listaCronogramaCreditoComp;
	}
	public List<RequisitoCreditoComp> getListaRequisitoCreditoComp() {
		return listaRequisitoCreditoComp;
	}
	public void setListaRequisitoCreditoComp(List<RequisitoCreditoComp> listaRequisitoCreditoComp) {
		this.listaRequisitoCreditoComp = listaRequisitoCreditoComp;
	}
	public List<Archivo> getListaArchivo() {
		return listaArchivo;
	}
	public void setListaArchivo(List<Archivo> listaArchivo) {
		this.listaArchivo = listaArchivo;
	}
	public String getStrSolicitudPrestamo() {
		return strSolicitudPrestamo;
	}
	public void setStrSolicitudPrestamo(String strSolicitudPrestamo) {
		this.strSolicitudPrestamo = strSolicitudPrestamo;
	}
	public Boolean getFormSolicitudPrestamoRendered() {
		return formSolicitudPrestamoRendered;
	}
	public void setFormSolicitudPrestamoRendered(Boolean formSolicitudPrestamoRendered) {
		this.formSolicitudPrestamoRendered = formSolicitudPrestamoRendered;
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
	public Boolean getBlnEvaluacionCredito() {
		return blnEvaluacionCredito;
	}
	public void setBlnEvaluacionCredito(Boolean blnEvaluacionCredito) {
		this.blnEvaluacionCredito = blnEvaluacionCredito;
	}
	public Integer getIntTipoRelacion() {
		return intTipoRelacion;
	}
	public void setIntTipoRelacion(Integer intTipoRelacion) {
		this.intTipoRelacion = intTipoRelacion;
	}
	public Integer getIntTipoCredito() {
		return intTipoCredito;
	}
	public void setIntTipoCredito(Integer intTipoCredito) {
		this.intTipoCredito = intTipoCredito;
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
	public Boolean getBlnGiro() {
		return blnGiro;
	}
	public void setBlnGiro(Boolean blnGiro) {
		this.blnGiro = blnGiro;
	}
	public Boolean getBlnArchivamiento() {
		return blnArchivamiento;
	}
	public void setBlnArchivamiento(Boolean blnArchivamiento) {
		this.blnArchivamiento = blnArchivamiento;
	}
	public Integer getIntNroCuotas() {
		return intNroCuotas;
	}
	public void setIntNroCuotas(Integer intNroCuotas) {
		this.intNroCuotas = intNroCuotas;
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
	public MyFile getFileDocAdjunto() {
		return fileDocAdjunto;
	}
	public void setFileDocAdjunto(MyFile fileDocAdjunto) {
		this.fileDocAdjunto = fileDocAdjunto;
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
	public void setListaRelacion(List<Tabla> listaRelacion) {
		this.listaRelacion = listaRelacion;
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
	public SolicitudPrestamoFacadeLocal getSolicitudPrestamoFacade() {
		return solicitudPrestamoFacade;
	}
	public void setSolicitudPrestamoFacade(SolicitudPrestamoFacadeLocal solicitudPrestamoFacade) {
		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
	}
	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}
	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
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
	public PlanillaFacadeRemote getPlanillaFacade() {
		return planillaFacade;
	}
	public void setPlanillaFacade(PlanillaFacadeRemote planillaFacade) {
		this.planillaFacade = planillaFacade;
	}
	public CaptacionFacadeRemote getCaptacionFacade() {
		return captacionFacade;
	}
	public void setCaptacionFacade(CaptacionFacadeRemote captacionFacade) {
		this.captacionFacade = captacionFacade;
	}
	public CreditoGarantiaFacadeRemote getCreditoGarantiaFacade() {
		return creditoGarantiaFacade;
	}
	public void setCreditoGarantiaFacade(CreditoGarantiaFacadeRemote creditoGarantiaFacade) {
		this.creditoGarantiaFacade = creditoGarantiaFacade;
	}
	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}
	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
	}
	public String getMsgTxtTipoOperacion() {
		return msgTxtTipoOperacion;
	}
	public void setMsgTxtTipoOperacion(String msgTxtTipoOperacion) {
		this.msgTxtTipoOperacion = msgTxtTipoOperacion;
	}
	public String getMsgTxtMontoSolicitado() {
		return msgTxtMontoSolicitado;
	}
	public void setMsgTxtMontoSolicitado(String msgTxtMontoSolicitado) {
		this.msgTxtMontoSolicitado = msgTxtMontoSolicitado;
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
	public String getMsgTxtNroCuotas() {
		return msgTxtNroCuotas;
	}
	public void setMsgTxtNroCuotas(String msgTxtNroCuotas) {
		this.msgTxtNroCuotas = msgTxtNroCuotas;
	}
	public String getMsgTxtConvenioActivo() {
		return msgTxtConvenioActivo;
	}
	public void setMsgTxtConvenioActivo(String msgTxtConvenioActivo) {
		this.msgTxtConvenioActivo = msgTxtConvenioActivo;
	}
	public String getMsgTxtCreditoConvenio() {
		return msgTxtCreditoConvenio;
	}
	public void setMsgTxtCreditoConvenio(String msgTxtCreditoConvenio) {
		this.msgTxtCreditoConvenio = msgTxtCreditoConvenio;
	}
	public String getMsgTxtCreditoPk() {
		return msgTxtCreditoPk;
	}
	public void setMsgTxtCreditoPk(String msgTxtCreditoPk) {
		this.msgTxtCreditoPk = msgTxtCreditoPk;
	}
	public String getMsgTxtRolPersona() {
		return msgTxtRolPersona;
	}
	public void setMsgTxtRolPersona(String msgTxtRolPersona) {
		this.msgTxtRolPersona = msgTxtRolPersona;
	}
	public String getMsgTxtEstructuraSocio() {
		return msgTxtEstructuraSocio;
	}
	public void setMsgTxtEstructuraSocio(String msgTxtEstructuraSocio) {
		this.msgTxtEstructuraSocio = msgTxtEstructuraSocio;
	}
	public String getMsgTxtCondicionLaboral() {
		return msgTxtCondicionLaboral;
	}
	public void setMsgTxtCondicionLaboral(String msgTxtCondicionLaboral) {
		this.msgTxtCondicionLaboral = msgTxtCondicionLaboral;
	}
	public String getMsgTxtInteresCredito() {
		return msgTxtInteresCredito;
	}
	public void setMsgTxtInteresCredito(String msgTxtInteresCredito) {
		this.msgTxtInteresCredito = msgTxtInteresCredito;
	}
	public String getMsgTxtExesoCuotasCronograma() {
		return msgTxtExesoCuotasCronograma;
	}
	public void setMsgTxtExesoCuotasCronograma(
			String msgTxtExesoCuotasCronograma) {
		this.msgTxtExesoCuotasCronograma = msgTxtExesoCuotasCronograma;
	}
	public String getMsgTxtTipoCreditoEmpresa() {
		return msgTxtTipoCreditoEmpresa;
	}
	public void setMsgTxtTipoCreditoEmpresa(String msgTxtTipoCreditoEmpresa) {
		this.msgTxtTipoCreditoEmpresa = msgTxtTipoCreditoEmpresa;
	}
	public String getMsgTxtCondicionCredito() {
		return msgTxtCondicionCredito;
	}
	public void setMsgTxtCondicionCredito(String msgTxtCondicionCredito) {
		this.msgTxtCondicionCredito = msgTxtCondicionCredito;
	}
	public String getMsgTxtCondicionHabil() {
		return msgTxtCondicionHabil;
	}
	public void setMsgTxtCondicionHabil(String msgTxtCondicionHabil) {
		this.msgTxtCondicionHabil = msgTxtCondicionHabil;
	}
	public String getMsgTxtSubCondicionCuenta() {
		return msgTxtSubCondicionCuenta;
	}
	public void setMsgTxtSubCondicionCuenta(String msgTxtSubCondicionCuenta) {
		this.msgTxtSubCondicionCuenta = msgTxtSubCondicionCuenta;
	}
	public String getMsgTxtMontoExesivo() {
		return msgTxtMontoExesivo;
	}
	public void setMsgTxtMontoExesivo(String msgTxtMontoExesivo) {
		this.msgTxtMontoExesivo = msgTxtMontoExesivo;
	}
	public String getMsgTxtEnvioPlla() {
		return msgTxtEnvioPlla;
	}
	public void setMsgTxtEnvioPlla(String msgTxtEnvioPlla) {
		this.msgTxtEnvioPlla = msgTxtEnvioPlla;
	}
	public String getMsgTxtCuotaMensual() {
		return msgTxtCuotaMensual;
	}
	public void setMsgTxtCuotaMensual(String msgTxtCuotaMensual) {
		this.msgTxtCuotaMensual = msgTxtCuotaMensual;
	}
	public String getMsgTxtPersonaNoExiste() {
		return msgTxtPersonaNoExiste;
	}
	public void setMsgTxtPersonaNoExiste(String msgTxtPersonaNoExiste) {
		this.msgTxtPersonaNoExiste = msgTxtPersonaNoExiste;
	}
	public String getMsgTxtErrores() {
		return msgTxtErrores;
	}
	public void setMsgTxtErrores(String msgTxtErrores) {
		this.msgTxtErrores = msgTxtErrores;
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
	public String getMsgTxtSubCondicionSocio() {
		return msgTxtSubCondicionSocio;
	}
	public void setMsgTxtSubCondicionSocio(String msgTxtSubCondicionSocio) {
		this.msgTxtSubCondicionSocio = msgTxtSubCondicionSocio;
	}
	public String getMsgTxtCondicionLaboralGarante() {
		return msgTxtCondicionLaboralGarante;
	}
	public void setMsgTxtCondicionLaboralGarante(String msgTxtCondicionLaboralGarante) {
		this.msgTxtCondicionLaboralGarante = msgTxtCondicionLaboralGarante;
	}
	public String getMsgTxtSituacionLaboralGarante() {
		return msgTxtSituacionLaboralGarante;
	}
	public void setMsgTxtSituacionLaboralGarante(String msgTxtSituacionLaboralGarante) {
		this.msgTxtSituacionLaboralGarante = msgTxtSituacionLaboralGarante;
	}
	public String getMsgTxtCondicionSocioNinguno() {
		return msgTxtCondicionSocioNinguno;
	}
	public void setMsgTxtCondicionSocioNinguno(String msgTxtCondicionSocioNinguno) {
		this.msgTxtCondicionSocioNinguno = msgTxtCondicionSocioNinguno;
	}
	public String getMsgTxtSubCondicionSocioNinguno() {
		return msgTxtSubCondicionSocioNinguno;
	}
	public void setMsgTxtSubCondicionSocioNinguno(String msgTxtSubCondicionSocioNinguno) {
		this.msgTxtSubCondicionSocioNinguno = msgTxtSubCondicionSocioNinguno;
	}
	public String getMsgTxtCondicionLaboralGaranteNinguno() {
		return msgTxtCondicionLaboralGaranteNinguno;
	}
	public void setMsgTxtCondicionLaboralGaranteNinguno(String msgTxtCondicionLaboralGaranteNinguno) {
		this.msgTxtCondicionLaboralGaranteNinguno = msgTxtCondicionLaboralGaranteNinguno;
	}
	public String getMsgTxtSituacionLaboralGaranteNinguno() {
		return msgTxtSituacionLaboralGaranteNinguno;
	}
	public void setMsgTxtSituacionLaboralGaranteNinguno(String msgTxtSituacionLaboralGaranteNinguno) {
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
	public void setMsgTxtSocioEstructuraGarante(
			String msgTxtSocioEstructuraGarante) {
		this.msgTxtSocioEstructuraGarante = msgTxtSocioEstructuraGarante;
	}
	public String getMsgTxtSocioEstructuraOrigenGarante() {
		return msgTxtSocioEstructuraOrigenGarante;
	}
	public void setMsgTxtSocioEstructuraOrigenGarante(String msgTxtSocioEstructuraOrigenGarante) {
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
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		SolicitudPrestamoController.log = log;
	}
	public Archivo getArchivoAdjunto() {
		return archivoAdjunto;
	}
	public void setArchivoAdjunto(Archivo archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}
	public BigDecimal getBdPorcAporteMontoSolic() {
		return bdPorcAporteMontoSolic;
	}
	public void setBdPorcAporteMontoSolic(BigDecimal bdPorcAporteMontoSolic) {
		this.bdPorcAporteMontoSolic = bdPorcAporteMontoSolic;
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
	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}
	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}
	public String getStrMsgErrorValidarDatos() {
		return strMsgErrorValidarDatos;
	}
	public void setStrMsgErrorValidarDatos(String strMsgErrorValidarDatos) {
		this.strMsgErrorValidarDatos = strMsgErrorValidarDatos;
	}
	public boolean isBlnYaTieneCredito() {
		return blnYaTieneCredito;
	}
	public void setBlnYaTieneCredito(boolean blnYaTieneCredito) {
		this.blnYaTieneCredito = blnYaTieneCredito;
	}
	public String getStrConsultaBusqueda() {
		return strConsultaBusqueda;
	}
	public void setStrConsultaBusqueda(String strConsultaBusqueda) {
		this.strConsultaBusqueda = strConsultaBusqueda;
	}
	public String getStrDescripcionSubTipoOperacion() {
		return strDescripcionSubTipoOperacion;
	}
	public void setStrDescripcionSubTipoOperacion(String strDescripcionSubTipoOperacion) {
		this.strDescripcionSubTipoOperacion = strDescripcionSubTipoOperacion;
	}
	public boolean isBlnMostrarDescripciones() {
		return blnMostrarDescripciones;
	}
	public void setBlnMostrarDescripciones(boolean blnMostrarDescripciones) {
		this.blnMostrarDescripciones = blnMostrarDescripciones;
	}
	public EstadoCredito getEstadoCreditoBusqueda() {
		return estadoCreditoBusqueda;
	}
	public void setEstadoCreditoBusqueda(EstadoCredito estadoCreditoBusqueda) {
		this.estadoCreditoBusqueda = estadoCreditoBusqueda;
	}
	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}
	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}
	public EstructuraDetalle getEstructuraDetalleBusqueda() {
		return estructuraDetalleBusqueda;
	}
	public void setEstructuraDetalleBusqueda(EstructuraDetalle estructuraDetalleBusqueda) {
		this.estructuraDetalleBusqueda = estructuraDetalleBusqueda;
	}
	public List<Tabla> getListaTablaDeSucursal() {
		return listaTablaDeSucursal;
	}
	public void setListaTablaDeSucursal(List<Tabla> listaTablaDeSucursal) {
		this.listaTablaDeSucursal = listaTablaDeSucursal;
	}
	public List<Tabla> getListaTablaCreditoEmpresa() {
		return listaTablaCreditoEmpresa;
	}
	public void setListaTablaCreditoEmpresa(List<Tabla> listaTablaCreditoEmpresa) {
		this.listaTablaCreditoEmpresa = listaTablaCreditoEmpresa;
	}
	public ExpedienteCredito getExpCreditoBusqueda() {
		return expCreditoBusqueda;
	}
	public void setExpCreditoBusqueda(ExpedienteCredito expCreditoBusqueda) {
		this.expCreditoBusqueda = expCreditoBusqueda;
	}
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
	public Boolean getBlnValidarGarantes() {
		return blnValidarGarantes;
	}
	public void setBlnValidarGarantes(Boolean blnValidarGarantes) {
		this.blnValidarGarantes = blnValidarGarantes;
	}
	public List<ExpedienteComp> getListExpedienteMovimientoComp() {
		return listExpedienteMovimientoComp;
	}
	public void setListExpedienteMovimientoComp(List<ExpedienteComp> listExpedienteMovimientoComp) {
		this.listExpedienteMovimientoComp = listExpedienteMovimientoComp;
	}
	public List<Terceros> getListTerceros() {
		return listTerceros;
	}
	public void setListTerceros(List<Terceros> listTerceros) {
		this.listTerceros = listTerceros;
	}
	public List<Terceros> getListColumnCpto() {
		return listColumnCpto;
	}
	public void setListColumnCpto(List<Terceros> listColumnCpto) {
		this.listColumnCpto = listColumnCpto;
	}
	public Integer getIntFrecuenciaTerceros() {
		return intFrecuenciaTerceros;
	}
	public void setIntFrecuenciaTerceros(Integer intFrecuenciaTerceros) {
		this.intFrecuenciaTerceros = intFrecuenciaTerceros;
	}
	public Boolean getBlnSeGeneroCronogramaCorrectamente() {
		return blnSeGeneroCronogramaCorrectamente;
	}
	public void setBlnSeGeneroCronogramaCorrectamente(Boolean blnSeGeneroCronogramaCorrectamente) {
		this.blnSeGeneroCronogramaCorrectamente = blnSeGeneroCronogramaCorrectamente;
	}
	public String getStrErrorGrabar() {
		return strErrorGrabar;
	}
	public void setStrErrorGrabar(String strErrorGrabar) {
		this.strErrorGrabar = strErrorGrabar;
	}
	public Integer getIntTipoEvaluacion() {
		return intTipoEvaluacion;
	}
	public void setIntTipoEvaluacion(Integer intTipoEvaluacion) {
		this.intTipoEvaluacion = intTipoEvaluacion;
	}
	public List<Tabla> getListaCondicionSocio() {
		return listaCondicionSocio;
	}
	public void setListaCondicionSocio(List<Tabla> listaCondicionSocio) {
		this.listaCondicionSocio = listaCondicionSocio;
	}
	public String getStrCondicionSocio() {
		return strCondicionSocio;
	}
	public void setStrCondicionSocio(String strCondicionSocio) {
		this.strCondicionSocio = strCondicionSocio;
	}
	public List<Tabla> getListaTipoCondicionSocio() {
		return listaTipoCondicionSocio;
	}
	public void setListaTipoCondicionSocio(List<Tabla> listaTipoCondicionSocio) {
		this.listaTipoCondicionSocio = listaTipoCondicionSocio;
	}
	public String getStrTipoCondicionSocio() {
		return strTipoCondicionSocio;
	}
	public void setStrTipoCondicionSocio(String strTipoCondicionSocio) {
		this.strTipoCondicionSocio = strTipoCondicionSocio;
	}
	public List<Tabla> getListaCondicionLaboral() {
		return listaCondicionLaboral;
	}
	public void setListaCondicionLaboral(List<Tabla> listaCondicionLaboral) {
		this.listaCondicionLaboral = listaCondicionLaboral;
	}
	public String getStrCondicionLaboral() {
		return strCondicionLaboral;
	}
	public void setStrCondicionLaboral(String strCondicionLaboral) {
		this.strCondicionLaboral = strCondicionLaboral;
	}
	public List<Tabla> getListaSituacionLaboral() {
		return listaSituacionLaboral;
	}
	public void setListaSituacionLaboral(List<Tabla> listaSituacionLaboral) {
		this.listaSituacionLaboral = listaSituacionLaboral;
	}
	public String getStrSituacionLaboral() {
		return strSituacionLaboral;
	}
	public void setStrSituacionLaboral(String strSituacionLaboral) {
		this.strSituacionLaboral = strSituacionLaboral;
	}
	public String getMsgTxtCondicionHabilGarante() {
		return msgTxtCondicionHabilGarante;
	}
	public void setMsgTxtCondicionHabilGarante(String msgTxtCondicionHabilGarante) {
		this.msgTxtCondicionHabilGarante = msgTxtCondicionHabilGarante;
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
	public String getMsgTxtEvaluacionFinal() {
		return msgTxtEvaluacionFinal;
	}
	public void setMsgTxtEvaluacionFinal(String msgTxtEvaluacionFinal) {
		this.msgTxtEvaluacionFinal = msgTxtEvaluacionFinal;
	}
	public List<Credito> getListaCreditosValidados() {
		return listaCreditosValidados;
	}
	public void setListaCreditosValidados(List<Credito> listaCreditosValidados) {
		this.listaCreditosValidados = listaCreditosValidados;
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
	public void setListaAutorizaCreditoComp(List<AutorizaCreditoComp> listaAutorizaCreditoComp) {
		this.listaAutorizaCreditoComp = listaAutorizaCreditoComp;
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
	public String getStrMsgErrorValidarMovimientos() {
		return strMsgErrorValidarMovimientos;
	}
	public void setStrMsgErrorValidarMovimientos(String strMsgErrorValidarMovimientos) {
		this.strMsgErrorValidarMovimientos = strMsgErrorValidarMovimientos;
	}
	public Integer getIntGarantesCorrectos() {
		return intGarantesCorrectos;
	}
	public void setIntGarantesCorrectos(Integer intGarantesCorrectos) {
		this.intGarantesCorrectos = intGarantesCorrectos;
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
	public void setStrMensajeGarantesObservacion(String strMensajeGarantesObservacion) {
		this.strMensajeGarantesObservacion = strMensajeGarantesObservacion;
	}
	public String getMsgTxtYaExisteGaranteCOnfiguracionIgual() {
		return msgTxtYaExisteGaranteCOnfiguracionIgual;
	}
	public void setMsgTxtYaExisteGaranteCOnfiguracionIgual(String msgTxtYaExisteGaranteCOnfiguracionIgual) {
		this.msgTxtYaExisteGaranteCOnfiguracionIgual = msgTxtYaExisteGaranteCOnfiguracionIgual;
	}
	public String getMsgTxtDescuentoJudicial() {
		return msgTxtDescuentoJudicial;
	}
	public void setMsgTxtDescuentoJudicial(String msgTxtDescuentoJudicial) {
		this.msgTxtDescuentoJudicial = msgTxtDescuentoJudicial;
	}
	public String getMsgTxtPersonaGaranteNoExiste() {
		return msgTxtPersonaGaranteNoExiste;
	}
	public void setMsgTxtPersonaGaranteNoExiste(String msgTxtPersonaGaranteNoExiste) {
		this.msgTxtPersonaGaranteNoExiste = msgTxtPersonaGaranteNoExiste;
	}
	public String getMsgTxtRolSocioGarante() {
		return msgTxtRolSocioGarante;
	}
	public void setMsgTxtRolSocioGarante(String msgTxtRolSocioGarante) {
		this.msgTxtRolSocioGarante = msgTxtRolSocioGarante;
	}
	public String getMsgTxtCondicionSocioNingunoGarante() {
		return msgTxtCondicionSocioNingunoGarante;
	}
	public void setMsgTxtCondicionSocioNingunoGarante(String msgTxtCondicionSocioNingunoGarante) {
		this.msgTxtCondicionSocioNingunoGarante = msgTxtCondicionSocioNingunoGarante;
	}
	public String getMsgTxtCondicionSocioGarante() {
		return msgTxtCondicionSocioGarante;
	}
	public void setMsgTxtCondicionSocioGarante(String msgTxtCondicionSocioGarante) {
		this.msgTxtCondicionSocioGarante = msgTxtCondicionSocioGarante;
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
	public CuentaFacadeRemote getCuentaFacade() {
		return cuentaFacade;
	}
	public void setCuentaFacade(CuentaFacadeRemote cuentaFacade) {
		this.cuentaFacade = cuentaFacade;
	}
	public BigDecimal getBdMontoADescontarRePrestamo() {
		return bdMontoADescontarRePrestamo;
	}
	public void setBdMontoADescontarRePrestamo(BigDecimal bdMontoADescontarRePrestamo) {
		this.bdMontoADescontarRePrestamo = bdMontoADescontarRePrestamo;
	}
	public List<ExpedienteComp> getListaExpedientesParaReprestamo() {
		return listaExpedientesParaReprestamo;
	}
	public void setListaExpedientesParaReprestamo(List<ExpedienteComp> listaExpedientesParaReprestamo) {
		this.listaExpedientesParaReprestamo = listaExpedientesParaReprestamo;
	}
	public BigDecimal getBdMontoSaldoAnterior() {
		return bdMontoSaldoAnterior;
	}
	public void setBdMontoSaldoAnterior(BigDecimal bdMontoSaldoAnterior) {
		this.bdMontoSaldoAnterior = bdMontoSaldoAnterior;
	}
	public Boolean getBlnCreditosEspeciales() {
		return blnCreditosEspeciales;
	}
	public void setBlnCreditosEspeciales(Boolean blnCreditosEspeciales) {
		this.blnCreditosEspeciales = blnCreditosEspeciales;
	}


	public boolean isMostrarBoton() {
		return mostrarBoton;
	}


	public void setMostrarBoton(boolean mostrarBoton) {
		this.mostrarBoton = mostrarBoton;
	}
	
}