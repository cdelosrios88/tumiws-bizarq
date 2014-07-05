package pe.com.tumi.servicio.solicitudEspecial.controller;

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

//import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.DownloadFile;
import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.MyFile;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConvertirNumeroLetras;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeRemote;
import pe.com.tumi.credito.socio.convenio.domain.Adenda;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCredito;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.credito.socio.convenio.facade.ConvenioFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.creditos.facade.CreditoGarantiaFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
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
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
//import pe.com.tumi.servicio.solicitudPrestamo.controller.Imprime;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CapacidadCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.AutorizaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CapacidadCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.CronogramaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
//import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeNuevoLocal;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoId;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeRemote;

public class SolicitudEspecialController {
	protected static Logger log = Logger.getLogger(SolicitudEspecialController.class);
	
	private ExpedienteCredito 	beanExpedienteCredito;
	private SocioComp 			beanSocioCompGarante;
	private SocioComp 			beanSocioComp;
	private Credito 			beanCredito; 
	private Usuario 			usuario;

	private String 				strSolicitudPrestamo;
	// PopUp de Acciones
	private Boolean 			blnMostrarModificar;
	private Boolean 			blnMostrarEliminar;
	// Grilla de Busqueda
	private Integer 			intBusqTipo; 	
	private String 				strBusqCadena;		    
	private String 				strBusqNroSol;		   
	private Integer 			intBusqSucursal;			  
	private Integer 			intBusqEstado;		    
	private Date 				dtBusqFechaEstadoDesde;  
	private Date 				dtBusqFechaEstadoHasta;
	private	List<Sucursal> 		listaSucursal;
	private Integer 			intBusqTipoCreditoEmpresa;
	
	private List<Tabla> 		listaTablaDeSucursal;
	private List<Tabla> 		listaCondicionSocio;  		// PARAM_T_CONDICIONSOCIO = "65"
	private List<Tabla> 		listaTipoCondicionSocio;  	// PARAM_T_TIPO_CONDSOCIO = "90"
	private List<Tabla> 		listaCondicionLaboral;  	// PARAM_T_CONDICIONLABORAL = "72"
	private List<Tabla> 		listaSituacionLaboral;  	// PARAM_T_TIPOSOCIO = "28"
	private List<Tabla> 		listaTablaCreditoEmpresa;
	private List<Tabla> 		listaEstadoPrestamo;
	
	// Lista resultado de busqueda
	private List<ExpedienteCreditoComp> listaExpedienteCreditoComp;
	// Booleanos que muestran u ocultan 3 areas: Validacion de Datos / Datos del Socio / Zona post Evaluacion. 
	private Boolean 			blnValidDatos;
	private Boolean				blnDatosSocio;
	private Boolean				blnEvaluacionCreditoEspecial;
	//rVillarreal 20.05.2014
	private boolean mostrarBoton;
	
	// Mensajes de validacion
	private String 				strMensajeGarantesObservacion;
	private String 				strErrorGrabar;
	
	// muetsra el edit
	private Boolean formSolicitudPrestamoRendered;
	// validar datos
	private Integer intTipoRelacion;
	private List<Tabla> listaRelacion;
	private Integer intTipoCredito;
	private Persona personaBusqueda;
	private String strMsgErrorValidarDatos;
	private String strMsgErrorValidarMovimientos;
	
	//Facades
	private TablaFacadeRemote 				tablaFacade;
	private SocioFacadeRemote 					socioFacade = null;
	private SolicitudPrestamoFacadeLocal 	solicitudPrestamoFacade = null;
	private SolicitudPrestamoFacadeNuevoLocal solicitudPrestamoFacadeNuevo = null;
	private PersonaFacadeRemote 			personaFacade = null;
	private CreditoFacadeRemote 			creditoFacade = null;
	private EstructuraFacadeRemote 			estructuraFacade = null;
	private ConvenioFacadeRemote 			convenioFacade = null;
	private PlanillaFacadeRemote 			planillaFacade = null;
	private CaptacionFacadeRemote 			captacionFacade = null;
	private CreditoGarantiaFacadeRemote 	creditoGarantiaFacade = null;
	private GeneralFacadeRemote 			generalFacade = null;
	private ConceptoFacadeRemote 			conceptoFacade = null;
	private EmpresaFacadeRemote 			empresaFacade = null;
	private PermisoFacadeRemote 			permisoFacade = null;
	private CuentaFacadeRemote 				cuentaFacade = null;
	
	
	private List<CapacidadCreditoComp> listaCapacidadCreditoComp;
	private CuentaConcepto ctaCtoAporte;
	private CuentaConcepto ctaCtoRetiro;
	private CuentaConcepto ctaCtoSepelio;
	private List<CuentaConcepto> listaCuentaConcepto;
	
	private BigDecimal bdAportes;
	
	private List<ExpedienteComp> listExpedienteMovimientoComp;
	private String strUltimoEstadoSolicitud;
	
	
	// Mensajes de error de Estructura
	
	private String strFechaRegistro;	
	private List<Estructura> listEstructura;
	private boolean blnBtnEditCapacidad;
	private boolean blnBtnAddCapacidad;
	private boolean blnBtnDelCapacidad;
	private boolean blnBtnEvaluarCredito;
	private boolean blnTextMontoPrestamo;
	private boolean blnCmbTipoOperacion;
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
	private Boolean blnValidarGarantes;
	private String strDescripcionPrestamoEspecial;
	private boolean blnMostrarDescripciones ;
	
	private List<Tabla> listaSubOpePrestamos;
	
	
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

	// Mensajes de error de Estructura
	private String msgTxtEstructuraRepetida;
	private String msgTxtEstrucActivoRepetido;
	private String msgTxtEstrucCesanteRepetido;
	
	private List<Sucursal> listSucursal;
	private List<Credito> lstCreditosOfrecidos;
	
	
	
	private String strPorcInteres;
	private String strPorcAportes;
	private Integer intNroCuotas;
	private boolean blnTextNroCuotas;
	//private BigDecimal bdMontoPrestamo;
	private BigDecimal bdMontoSeguroDesgravamen;
	private BigDecimal bdTotalDstosAporte;
	private BigDecimal bdTotalDescuentos;
	private BigDecimal bdTotalCuotaMensual;
	private Integer intTipoEvaluacion;
	
	private BigDecimal bdMontoTotalSolicitado;
	private BigDecimal bdMontoSolicitado;
	private BigDecimal bdCuotaMensual;
	private List<CronogramaCreditoComp> listaCronogramaCreditoComp;
	private List<RequisitoCreditoComp> listaRequisitoCreditoComp;
	
	private Boolean blnSeGeneroCronogramaCorrectamente;
	private Integer intCuotasMaximas;
	
	// adjuntos
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	
	private Date dtFechaRegistro;
	private BigDecimal bdMaxCapacidadPago;
	private BigDecimal bdCapacidadPago;
	//private BigDecimal bdMontoTotalDescuentos;
	private Boolean blnBloquearXCuenta;
	private String strMensajeValidacionCuenta;
	private Tabla tablaEstado;
	private Integer intUltimoEstadoSolicitud;
	private List<AutorizaCreditoComp> listaAutorizaCreditoComp;
	
	//RVILLARREAL 09.05.2014
	private List<Tabla> lstParaQuePinteReporte;
//	private List<GarantiaCreditoComp> listaGarantiaCreditoComp;
//	private Integer intGarantesCorrectos;
//	private String strNroDocumento;
	
	/**
	 * 
	 */
	public SolicitudEspecialController() {
		strFechaRegistro = Constante.sdf.format(new Date());
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
		beanCredito = new Credito();
		beanCredito.setId(new CreditoId());
		beanExpedienteCredito = new ExpedienteCredito();
		beanExpedienteCredito.setId(new ExpedienteCreditoId());
		beanExpedienteCredito.setIntParaSubTipoOperacionCod(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO);
		beanExpedienteCredito.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
		beanExpedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());
		beanExpedienteCredito.setListaCronogramaCredito(new ArrayList<CronogramaCredito>());
		beanExpedienteCredito.setListaGarantiaCredito(new ArrayList<GarantiaCredito>());
		beanExpedienteCredito.setListaCapacidadCreditoComp(new ArrayList<CapacidadCreditoComp>());
		beanExpedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
		beanSocioCompGarante = new SocioComp();
		beanSocioCompGarante.setPersona(new Persona());
		intCuotasMaximas = 0;
		blnTxtObservacionesPrestamo = false;
		beanExpedienteCredito.setSocioComp(new SocioComp());
		beanExpedienteCredito.setListaEstructuraSocio(new ArrayList<Estructura>());
		strPorcAportes = null;
		strPorcInteres = null;
		intTipoEvaluacion = 1;
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		blnMostrarDescripciones = Boolean.FALSE;
		blnTextMontoPrestamo = Boolean.FALSE;
		//RVILLARREAL 09.05.2014
		lstParaQuePinteReporte = new ArrayList<Tabla>();
//		listaGarantiaCreditoComp = new ArrayList<GarantiaCreditoComp>();

		// validar datos
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		
		bdAportes = BigDecimal.ZERO;
		lstCreditosOfrecidos = new ArrayList<Credito>();
		intUltimoEstadoSolicitud = -1;

		try {
			cargarFacades();
			cargarListas();
			cargarUsuario();
			cargarPermisos();
			
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 
	 */
	public void cargarFacades(){
		try {
			tablaFacade 			= (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			socioFacade 			= (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			solicitudPrestamoFacadeNuevo = (SolicitudPrestamoFacadeNuevoLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeNuevoLocal.class);
			personaFacade 			= (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			creditoFacade 			= (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
			estructuraFacade 		= (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			convenioFacade 			= (ConvenioFacadeRemote) EJBFactory.getRemote(ConvenioFacadeRemote.class);
			planillaFacade 			= (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			captacionFacade 		= (CaptacionFacadeRemote) EJBFactory.getRemote(CaptacionFacadeRemote.class);
			creditoGarantiaFacade 	= (CreditoGarantiaFacadeRemote) EJBFactory.getRemote(CreditoGarantiaFacadeRemote.class);
			generalFacade 			= (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			conceptoFacade 			= (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			empresaFacade 			= (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			permisoFacade 			= (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			cuentaFacade 			= (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			
		} catch (Exception e) {
			
		}
	}
	
	
	/**
	 * 
	 */
	public void cargarListas(){
		try {
			listaCondicionSocio 	= tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO)); 
			listaTipoCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CONDSOCIO));
			listaCondicionLaboral 	= tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONLABORAL));
			listaEstadoPrestamo 	= tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ESTADOSOLICPRESTAMO));
			listaSucursal 			= empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			listaRelacion 			= tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOROL), "E");
			listSucursal 			= empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			listEstructura 			= estructuraFacade.getListaEstructuraPorNivelYCodigoRel(null, null);
		
			//Ordenamos por nombre
			Collections.sort(listaSucursal, new Comparator<Sucursal>(){
				public int compare(Sucursal sucUno, Sucursal sucDos) {
					return sucUno.getJuridica().getStrSiglas().compareTo(sucDos.getJuridica().getStrSiglas());
				}
			});
			
			listaSubOpePrestamos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_SUBOPERACIONPRESTAMO), "A");
			
			if(listaSubOpePrestamos != null && !listaSubOpePrestamos.isEmpty()){
				for (Tabla tabDesc1 : listaSubOpePrestamos) {
					if(tabDesc1.getIntIdDetalle().compareTo(beanExpedienteCredito.getIntParaSubTipoOperacionCod())==0){
						strDescripcionPrestamoEspecial= tabDesc1.getStrDescripcion();
						break;
					}
				}	
			}
			// lista de grilla de busqueda
			listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), "A");
			//lstCreditosOfrecidos = 	cargarListaCreditos();

		} catch (Exception e) {
			log.error("Error en cargarListas --->"+e);
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
	public void buscarSolicitudPrestamoEspecial(ActionEvent event) {
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

			listaExpedienteCreditoComp = facade.getListaBusqCreditoEspecialFiltros(expedienteCompBusq);

		} catch (BusinessException e) {
			log.error("Error BusinessException buscarSolicitudPrestamoEspecial ---> "+e);
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException buscarSolicitudPrestamoEspecial ---> "+e);
			e.printStackTrace();
		}
	}
	/**
	 * Metodo que se ejecuta al seleccionar un registro de la grilla de busqueda
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		ExpedienteCreditoId expedienteCreditoId = null;
		try{
			registroSeleccionadoBusqueda = (ExpedienteCreditoComp)event.getComponent().getAttributes().get("itemExpCredito");
			log.info("reg selec:"+registroSeleccionadoBusqueda);	
			validarOperacionEliminar();
			validarOperacionModificar();
			
			if(registroSeleccionadoBusqueda != null){
				expedienteCreditoId = new ExpedienteCreditoId();
				expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
				expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
				validarEstadoCuenta(expedienteCreditoId);
				irVerSolicitudPrestamo(null);
				if(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio()==2)
					mostrarBoton = Boolean.TRUE;
				else
					mostrarBoton = Boolean.FALSE;
			}
			
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
			
			strSolicitudPrestamo =Constante.MANTENIMIENTO_MODIFICAR;
			blnMostrarDescripciones = Boolean.TRUE;
			blnTextMontoPrestamo = Boolean.TRUE;
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
				
				strDescripcionPrestamoEspecial = beanCredito.getStrDescripcion();
				
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
						
							// Queda pendiente sumar si se eligiera cancelar un préstamo anterior
							bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoTotal();
							bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
							bdMontoSolicitado = beanExpedienteCredito.getBdMontoSolicitado();
							bdMontoSolicitado = bdMontoSolicitado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
						}
					}
				}
				

				if (beanExpedienteCredito.getListaEstadoCredito() != null && !beanExpedienteCredito.getListaEstadoCredito().isEmpty()) {
					
					mostrarlistaAutorizacionesPrevias(beanExpedienteCredito.getListaEstadoCredito());

					if(beanExpedienteCredito.getEstadoCreditoUltimo() != null){
						if(beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)==0
							||beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)==0 ){
						}else if(beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
							||beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0 ){
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
					evaluarPrestamoModificar(event);
					evaluarPrestamoCronogramaView(event);
					blnEvaluacionCreditoEspecial = true;
					formSolicitudPrestamoRendered = true;
					blnValidDatos = false;
					blnDatosSocio = true;
					
				if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null && !beanExpedienteCredito.getListaRequisitoCreditoComp().isEmpty()) {
					listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
				}
			}
			
		} catch (Exception e) {
			log.error("Error BusinessException en  irModificarSolicitudPrestamo ---> " + e);
			e.printStackTrace();
		} finally {
			formSolicitudPrestamoRendered = true;
			blnValidDatos = false;
			blnDatosSocio = true;
		}
	}

	
	
	
	public void irVerSolicitudPrestamo(ActionEvent event) throws EJBFactoryException, IOException {		
		limpiarFormSolicitudPrestamo();
		blnValidarGarantes = Boolean.TRUE;
		intTipoEvaluacion = 2 ;
		List<ExpedienteComp> lstExpedientesRecuperados = null;
		Cuenta cuentaExpedienteCredito = null;
		List<CuentaConcepto> lstCtaCto = null;
		Cuenta cuentaSocio = null;
		CuentaComp cuentaComp = null;
		
		try {
			
			strSolicitudPrestamo = Constante.MANTENIMIENTO_NINGUNO;
			blnMostrarDescripciones = Boolean.TRUE;
			blnTextMontoPrestamo = Boolean.TRUE;
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
				
				strDescripcionPrestamoEspecial = beanCredito.getStrDescripcion();
				
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
						
							// Queda pendiente sumar si se eligiera cancelar un
							// préstamo anterior
							bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoTotal();
							bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
							bdMontoSolicitado = beanExpedienteCredito.getBdMontoSolicitado();
							bdMontoSolicitado = bdMontoSolicitado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
						}
					}
				}
				

				if (beanExpedienteCredito.getListaEstadoCredito() != null && !beanExpedienteCredito.getListaEstadoCredito().isEmpty()) {
					mostrarlistaAutorizacionesPrevias(beanExpedienteCredito.getListaEstadoCredito());

					if(beanExpedienteCredito.getEstadoCreditoUltimo() != null){
						if(beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)==0
							||beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)==0 ){
						}else if(beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0
							||beanExpedienteCredito.getEstadoCreditoUltimo().getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO)==0 ){
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
					evaluarPrestamoModificar(event);
					evaluarPrestamoCronogramaView(event);
					blnEvaluacionCreditoEspecial = true;
					formSolicitudPrestamoRendered = true;
					blnValidDatos = false;
					blnDatosSocio = true;
					
				if (beanExpedienteCredito.getListaRequisitoCreditoComp()!= null && !beanExpedienteCredito.getListaRequisitoCreditoComp().isEmpty()) {
					listaRequisitoCreditoComp = beanExpedienteCredito.getListaRequisitoCreditoComp();
				}
			}
			
		} catch (Exception e) {
			log.error("Error BusinessException en  irModificarSolicitudPrestamo ---> " + e);
			e.printStackTrace();
		} finally {
			formSolicitudPrestamoRendered = true;
			blnValidDatos = false;
			blnDatosSocio = true;
		}
	}

	
	
	
	public void irEliminarSolicitudPrestamo(ActionEvent event) {
		limpiarFormSolicitudPrestamo();
		EstadoCredito estadoCredito = null;
		//SocioComp socioComp;
		//Integer intIdPersona = null;
		//Persona persona = null;
		//EstadoCredito ultimoEstado = null;
		ExpedienteCreditoId expedienteCreditoId = new ExpedienteCreditoId();
		expedienteCreditoId.setIntPersEmpresaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntPersEmpresaPk());
		expedienteCreditoId.setIntCuentaPk(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntCuentaPk());
		expedienteCreditoId.setIntItemExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemExpediente());
		expedienteCreditoId.setIntItemDetExpediente(registroSeleccionadoBusqueda.getExpedienteCredito().getId().getIntItemDetExpediente());
		
		try {
			// devuelve el crongrama son id vacio.
			beanExpedienteCredito = solicitudPrestamoFacade.getSolicitudPrestamoPorIdExpedienteCredito(expedienteCreditoId);
			if (beanExpedienteCredito != null) {
				
				if(registroSeleccionadoBusqueda.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
					estadoCredito = new EstadoCredito();
					estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO);
					estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
					estadoCredito.setIntPersEmpresaEstadoCod(usuario.getSucursal().getIntIdEstado());				
					estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
					estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
					estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntIdEstado());
					beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);

				solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
				}else{
					strMsgErrorValidarDatos = "Solo se puede Eliminar una Solicitud en estado REQUISITO.";
					return;
				}
			}

			cancelarGrabarSolicitudPrestamo(event);
			limpiarFormSolicitudPrestamo();
		} catch (BusinessException e) {
			log.error("error: " + e);
			e.printStackTrace();
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
			bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoTotal();
			bdMontoSeguroDesgravamen = beanExpedienteCredito.getBdMontoGravamen();
			bdTotalDstosAporte = beanExpedienteCredito.getBdMontoAporte();
			bdTotalCuotaMensual = bdTotalCuotaMensualCronograma;

		} catch (Exception e) {
			log.error("Error en evaluarPrestamoCronogramaView ---> "+e);
		}
	}

	
	/**
	 * Valida si el socio puede acceder a un presta tumi.
	 * Se valida que no posea expedientes en servicio(req/sol/aprob).
	 * Se valida que no exista en movimiento otro prestatumi en estado vigente y con saldo.
	 * @param cuenta
	 * @return
	 */
	public Boolean validaConfPrestaTumi(Cuenta cuenta){
		Boolean blnPrestaTumi 	= Boolean.TRUE;
		Integer intCuenta 		= 0;
		Integer intEmpresa 		= 0;
		List<Expediente> listaCreditos = null;
		
		try {
			if(cuenta != null){
				intCuenta = cuenta.getId().getIntCuenta();
				intEmpresa = cuenta.getId().getIntPersEmpresaPk();
				
				Boolean blnTieneExpReq = false;
				Boolean blnTieneExpSol = false;	
				Boolean blnTieneExpAprob = false;	
				// Validamos si poseee alguna solicitud en estado Requisito y/o Solicitud
				blnTieneExpReq = validarExistenciaPrestamosEstado_Req_Sol(cuenta,Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
				blnTieneExpSol = validarExistenciaPrestamosEstado_Req_Sol(cuenta,Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
				blnTieneExpAprob = validarExistenciaPrestamosEstado_Req_Sol(cuenta,Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
				
				if(blnTieneExpReq || blnTieneExpSol || blnTieneExpAprob){
					blnPrestaTumi = Boolean.FALSE;	
					return blnPrestaTumi;
				}
				
				// Creditos movimientos con saldo
				listaCreditos =  conceptoFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa,intCuenta);
				if(listaCreditos != null && !listaCreditos.isEmpty()){
					for (Expediente expediente : listaCreditos) {
						EstadoExpediente ultimoEstado = null;
						ultimoEstado = conceptoFacade.getMaxEstadoExpPorPkExpediente(expediente.getId());
						if(ultimoEstado != null){
							if(ultimoEstado.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){
								Credito credito = null;
								CreditoId pk = new CreditoId();
								pk.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
								pk.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
								pk.setIntItemCredito(expediente.getIntItemCredito());
								
								credito = creditoFacade.getCreditoPorIdCreditoDirecto(pk);
								if(credito != null && credito.getIntParaTipoCreditoEmpresa().compareTo(Constante.PARAM_T_TIPOCREDITOEMPRESA_PRESTATUMI)==0){
									blnPrestaTumi = Boolean.FALSE;
									break;
								}
							}
						}
					}		
				}
			}
	
		} catch (Exception e) {
			log.error("Error en validaConfPrestaTumi ---> "+e);
		}
		return blnPrestaTumi;
	}
	
	
	/**
	 * Valida si el socio puede acceder a un credito solidario.
	 * Se valida que no posea expedientes en servicio(req/sol/aprob).
	 * Se valida que no exista en movimiento prestamo de tipo 1,2,5 en estado vigente y con saldo.
	 * Se valida que no tenga un credito solidario previo (x cuenta).
	 * @param cuenta
	 * @return
	 */
	public Boolean validaConfSolidario(Cuenta cuenta){
		Boolean blnSolidario 	= Boolean.TRUE;
		Integer intCuenta 		= 0;
		Integer intEmpresa 		= 0;
		List<Expediente> listaCreditosTotal = null;		
		try {
			intCuenta = cuenta.getId().getIntCuenta();
			intEmpresa = cuenta.getId().getIntPersEmpresaPk();
			
			Boolean blnTieneExpReq = false;
			Boolean blnTieneExpSol = false;	
			Boolean blnTieneExpAprob = false;	
			// Validamos si poseee alguna solicitud en estado Requisito y/o Solicitud
			blnTieneExpReq = validarExistenciaPrestamosEstado_Req_Sol(cuenta,Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
			blnTieneExpSol = validarExistenciaPrestamosEstado_Req_Sol(cuenta,Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
			blnTieneExpAprob = validarExistenciaPrestamosEstado_Req_Sol(cuenta,Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
			
			if(blnTieneExpReq || blnTieneExpSol || blnTieneExpAprob){
				blnSolidario = Boolean.FALSE;
				return blnSolidario;
			}

			listaCreditosTotal = conceptoFacade.getListaExpedientePorEmpresaYCta(intEmpresa,intCuenta);
			if(listaCreditosTotal != null && !listaCreditosTotal.isEmpty()){
				for (Expediente expedienteT : listaCreditosTotal) {
					if(expedienteT.getBdSaldoCredito().compareTo(BigDecimal.ZERO)!=0){
						if(expedienteT.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)==0
							|| expedienteT.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ORDENDECREDITO)==0
							|| expedienteT.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)==0){
								
								EstadoExpediente ultimoEstado = null;
								ultimoEstado = conceptoFacade.getMaxEstadoExpPorPkExpediente(expedienteT.getId());
								if(ultimoEstado != null){
									if(ultimoEstado.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){
										blnSolidario = Boolean.FALSE;
										return blnSolidario;
										//break;
									}
								}
							}
					}
				}
				
				for (Expediente expediente : listaCreditosTotal) {
					if(expediente.getBdSaldoCredito().compareTo(BigDecimal.ZERO)!=0){
								Credito credito = null;
								CreditoId pk = new CreditoId();
								pk.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
								pk.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
								pk.setIntItemCredito(expediente.getIntItemCredito());
								
								credito = creditoFacade.getCreditoPorIdCreditoDirecto(pk);
								if(credito != null && credito.getIntParaTipoCreditoEmpresa().compareTo(Constante.PARAM_T_TIPOCREDITOEMPRESA_SOLIDARIO)==0){
									blnSolidario = Boolean.FALSE;
									return blnSolidario;
							}
					}
				}
			}

		} catch (Exception e) {
			log.error("Error en validaConfSolidario ---> "+e);
		}
		return blnSolidario;
		
		
	}
	
	/**
	 * Recuperea los creditos del tipo Prestatumi y solidario
	 * @return
	 */
	public List<Credito> cargarListaCreditos(Boolean blnPrstTm, Boolean blnSldr){
		List<Credito> listaCreditosRecuperados = null;
		try {
			
			listaCreditosRecuperados = new ArrayList<Credito>();
			
			Credito pCredito = new Credito();
			pCredito.setId(new CreditoId());
			if(blnPrstTm){
				pCredito.setIntParaTipoCreditoEmpresa(Constante.PARAM_T_TIPOCREDITOEMPRESA_PRESTATUMI);
				List<Credito> listaCreditosTemp = null;
				
				listaCreditosTemp =  creditoFacade.getlistaCreditoPorCredito(pCredito);
				if(listaCreditosTemp != null && !listaCreditosTemp.isEmpty()){
					for (Credito credito : listaCreditosTemp) {
						if(credito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							listaCreditosRecuperados.add(credito);
						}
					}
				}
			}
			
			if(blnSldr){
				pCredito.setIntParaTipoCreditoEmpresa(Constante.PARAM_T_TIPOCREDITOEMPRESA_SOLIDARIO);
				List<Credito> listaCreditosTemp = null;
				listaCreditosTemp =  creditoFacade.getlistaCreditoPorCredito(pCredito);
				if(listaCreditosTemp != null && !listaCreditosTemp.isEmpty()){
					for (Credito credito : listaCreditosTemp) {
						if(credito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							listaCreditosRecuperados.add(credito);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en cargarListaCreditos ---> "+e);
		}
		return listaCreditosRecuperados;
	}
	
	

	/**
	 * 
	 */
	public void cargarValoresIniciales(){
			
	}
	
	/**
	 * 
	 */
	public void cargarPermisos(){
		
	}
	
	/**
	 * 
	 */
	public void cargarUsuario(){
		
	}
	
	
	
	/**
	 * Valida datos de Socio al registrar una Nueva Solicitud de Credito.
	 * @param event
	 */
	public void validarDatos(ActionEvent event) {
		
		SocioComp socioComp = null;
		CapacidadCreditoComp capacidadCreditoComp = null;
		Integer intTipoDoc = personaBusqueda.getDocumento().getIntTipoIdentidadCod();
		String strNumIdentidad = personaBusqueda.getDocumento().getStrNumeroIdentidad();
		strNumIdentidad = strNumIdentidad.trim();
		CuentaComp cuentaComp = new CuentaComp();
		List<ExpedienteComp> lstExpedientesRecuperados = null;
		List<CuentaConcepto> lstCtaCto = null;
		Cuenta cuentaSocio = null;

		try {
			
			limpiarListaCapacidades();
			
			strMsgErrorValidarMovimientos = "";
			if ((intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_SOCIO) && intTipoCredito.equals(Constante.PARAM_T_TIPOSCREDITOPANTALLA_DECONSUMO))
				|| intTipoRelacion.equals(Constante.PARAM_T_TIPOROL_USUARIO)&& intTipoCredito.equals(Constante.PARAM_T_TIPOSCREDITOPANTALLA_DECONSUMO)) {

				socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa( intTipoDoc, strNumIdentidad,Constante.PARAM_EMPRESASESION);
				
				if (socioComp != null && socioComp.getPersona()!= null) {					
					
				// 1. Validamos el estado de la persona (fallecido , activo)
					if(socioComp.getPersona().getIntEstadoCod().compareTo(Constante.PARAM_PERSONA_ESTADO_ACTIVO)==0){
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
											if(listaCapacidadCreditoComp == null){
												listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
											}
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
										
										
										//if(listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()){
											//for (CuentaConcepto ctaCto : listaCuentaConcepto) {
												if(ctaCtoAporte.getListaCuentaConceptoDetalle() != null && !ctaCtoAporte.getListaCuentaConceptoDetalle().isEmpty()){
													for (CuentaConceptoDetalle ctaCtoDet : ctaCtoAporte.getListaCuentaConceptoDetalle()) {
														if(ctaCtoDet.getIntParaTipoConceptoCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
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
																bdAportes = ctaCtoDet.getBdMontoConcepto();
															}
														}
														
													}
												}
											//}
										//}
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
				
								lstCreditosOfrecidos = cargarListaCreditos(validaConfPrestaTumi(beanSocioComp.getCuentaComp().getCuenta()), 
																			validaConfSolidario(beanSocioComp.getCuentaComp().getCuenta()));
								if(lstCreditosOfrecidos == null || lstCreditosOfrecidos.isEmpty()){
									blnValidDatos = true;
									blnDatosSocio = false;
									strMsgErrorValidarDatos = "No se encontraron creditos configurados.";
								}else{
									calcularTotalesConceptos(socioComp);
									
									// Sino tiene procede
									//if(blnTieneExpReq == false && blnTieneExpSol == false){
									
										// Recuperamos los expedientes de movimineto para visualizarlos en la grilla de saldo de cuentas
										// Sino existen es porque el socio no posee deudas.
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
												//strMsgErrorValidarDatos ="No se ha encontrado algún Crédito con deuda.";
											}
										blnValidDatos = false;
										blnDatosSocio = true;
										strMsgErrorValidarDatos = "";	
								}
								
							}else{
								strMsgErrorValidarDatos = "El Cuenta del Socio no se encuentra en estado Vigente.";	
							}	

						}else{
							strMsgErrorValidarDatos = "El Socio no posee Cuenta registrada.";
						}

					}else {
						strMsgErrorValidarDatos = "El Socio se encuentra en estado Fallecido.";
					}
				}else{
					strMsgErrorValidarDatos = "No se encontró Socio.";
				}
			} else {
				blnValidDatos = true;
				blnDatosSocio = false;
			}
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
	 * 
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
	 * Valida el Monto ingresado y que se haya registrado Capacidad de Credito
	 * en el Expediente.
	 * @param beanExpedienteCredito
	 * @return True(Cumple con validaciones) , False (No cumple con
	 *         validaciones).
	 */
	private Boolean isValidoEvaluacionCredito(ExpedienteCredito beanExpedienteCredito) {
		Boolean validEvaluarCredito = true;
		try {
			if (beanExpedienteCredito.getBdMontoSolicitado() == null
				|| beanExpedienteCredito.getBdMontoSolicitado().compareTo(BigDecimal.ZERO) == 0) {

				setMsgTxtMontoSolicitado("* El monto solicitado debe ser ingresado.");
				validEvaluarCredito = false;
			} else {
				setMsgTxtMontoSolicitado("");
			}
			
			// comentado por pruebas 
			
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
		}
		return validEvaluarCredito;
	}
	
	
	/**
	 * 
	 * @param event
	 * @throws ParseException
	 */
	public void evaluarPrestamoInicio(ActionEvent event) throws ParseException {
		EstructuraDetalle estructuraDetalle = null;
//		Date today = new Date();
//		String strToday = Constante.sdf.format(today);
//		Date dtToday = Constante.sdf.parse(strToday);
		Credito credito;
		bdMontoSolicitado = beanExpedienteCredito.getBdMontoSolicitado();
		bdMontoSeguroDesgravamen = new BigDecimal(0);
		bdTotalDstosAporte = new BigDecimal(0);
		bdTotalDescuentos = new BigDecimal(0);
		//bdMontoPrestamo = new BigDecimal(0);
		bdCuotaMensual = new BigDecimal(0);
		bdTotalCuotaMensual = new BigDecimal(0);
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
//		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetalle = null;
		boolean blnContinua = true; 
		blnSeGeneroCronogramaCorrectamente = true;
		
		try {
			if (isValidoEvaluacionCredito(beanExpedienteCredito) == false) {
				log.info("Datos de Evaluación no válidos. Se aborta el proceso de evaluación del Crédito.");
				return;
			}
			
			if(beanExpedienteCredito.getIntItemCredito() != null){
				credito = new Credito();
				credito.setId(new CreditoId());
				credito.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
				credito.getId().setIntParaTipoCreditoCod(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO);
				credito.getId().setIntItemCredito(beanExpedienteCredito.getIntItemCredito());
				credito = creditoFacade.getCreditoPorIdCredito(credito.getId());
				
				
				blnContinua = validacionCredito_1(credito);
				
				if(blnContinua){
					if(credito.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) ==  0) {
						if (credito.getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
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
											if (beanSocioComp.getCuenta().getIntParaCondicionCuentaCod().equals(
											condicionCredito.getId().getIntParaCondicionSocioCod())
											&& condicionCredito.getIntValor() == 1){
												// Verificar si la cuenta está activa o inactiva
												if (credito.getListaCondicionHabil() != null) {
													// forCondicionHabil:
													for (CondicionHabil condicionHabil : credito.getListaCondicionHabil()) {
														// verificando condicion de regular
														if (beanSocioComp.getCuenta().getIntParaSubCondicionCuentaCod().equals(condicionHabil.getId().getIntParaTipoHabilCod())
														&& condicionHabil.getIntValor() == 1) {

															blnContinua = validacionCredito_2(beanSocioComp,credito);																
																if(blnContinua){
																	msgTxtErrores = "";
																	beanCredito = credito;
																	if (beanSocioComp.getSocio().getSocioEstructura() != null) {
																		estructuraDetalle = new EstructuraDetalle();	
																		estructuraDetalle.setId(new EstructuraDetalleId());
																		estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
																		estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
																		estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																		beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																		beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
																		
																		calcularMontosCreditoRecuperado(credito, estructuraDetalle);
																		generarCronograma(credito, estructuraDetalle);
																		mostrarArchivosAdjuntos(event);
																		msgTxtErrores = "";
																		blnEvaluacionCreditoEspecial = Boolean.TRUE;
																	}

																}else{
																	blnEvaluacionCreditoEspecial= Boolean.FALSE;
																	beanCredito = null;
																	msgTxtErrores = "No se superaron las validaciones para el credito Seleccionado.";
																}
														} else{msgTxtCondicionHabil = "No se superó la validación de Condición Hábil.";}
													}
												} else {msgTxtCondicionHabil = "No existen condiciones habiles. ";}
											}else{ System.out.println("VERIRIFCAR CONDICIONDE  LA CUENTA.");}
										}
									}else{msgTxtCondicionLaboral = "No existe lista de Condición";}
								}  else { msgTxtCondicionLaboral = "No se superó la validación de Condición Laboral.";}
							}else{System.out.println("NO HAY SOCIO ESTRUCTURA");						}
						}else{System.out.println("rol de CREDITO NO SOCIO. ");}
					}else{System.out.println("CREDITO NO ACTIVO. ");}
				}else{
					msgTxtEvaluacionFinal = "Socio no supero validaciones del crédito seleccionado.";
				}
			}

		} catch (Exception e) {
			log.error("Error Exception en evaluarPrestamo ---> " + e);
		} 
	}
	
	
	
	/**
	 * Realiza la 'Evaluacion' de la solicitud de Credito.
	 * 
	 * @param event
	 * @throws ParseException
	 */
	public void evaluarPrestamoModificar(ActionEvent event) throws ParseException {
		EstructuraDetalle estructuraDetalle = null;
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		blnSeGeneroCronogramaCorrectamente = true;
		
		try {
			
			if (isValidoEvaluacionCredito(beanExpedienteCredito) == false) {
				log.info("Datos de Evaluación no válidos. Se aborta el proceso de evaluación del Crédito.");
				return;
			}
			
			if(beanExpedienteCredito.getIntItemCredito() != null){
					if (beanSocioComp.getSocio().getSocioEstructura() != null) {
						estructuraDetalle = new EstructuraDetalle();	
						estructuraDetalle.setId(new EstructuraDetalleId());
						estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
						estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
						estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																				beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																				beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());

						generarCronogramaEdit(beanCredito, estructuraDetalle);
						msgTxtErrores = "";
						blnEvaluacionCreditoEspecial = Boolean.TRUE;
					}

				}
			//}

		} catch (Exception e) {
			log.error("Error Exception en evaluarPrestamoModificar: " + e);
			e.printStackTrace();
		}
	}
	
	
	
	public void evaluarPrestamoModificarEdit(ActionEvent event) throws ParseException {
		EstructuraDetalle estructuraDetalle = null;
		listaCronogramaCreditoComp = new ArrayList<CronogramaCreditoComp>();
		blnSeGeneroCronogramaCorrectamente = true;
		
		try {
			
			//blnEvaluacionCreditoEspecial = Boolean.TRUE;
			if (isValidoEvaluacionCredito(beanExpedienteCredito) == false) {
				log.info("Datos de Evaluación no válidos. Se aborta el proceso de evaluación del Crédito.");
				return;
			}
			
			if(beanExpedienteCredito.getIntItemCredito() != null){
			
					if (beanSocioComp.getSocio().getSocioEstructura() != null) {
						estructuraDetalle = new EstructuraDetalle();	
						estructuraDetalle.setId(new EstructuraDetalleId());
						estructuraDetalle.getId().setIntNivel(beanSocioComp.getSocio().getSocioEstructura().getIntNivel());
						estructuraDetalle.getId().setIntCodigo(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo());
						estructuraDetalle = estructuraFacade.getEstructuraDetallePorPkEstructuraYTipoSocioYModalidad(estructuraDetalle.getId(), 
																				beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(), 
																				beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
						generarCronogramaEdit(beanCredito, estructuraDetalle);
						msgTxtErrores = "";
						blnEvaluacionCreditoEspecial = Boolean.TRUE;
					}
				}

		} catch (Exception e) {
			log.error("Error Exception en evaluarPrestamoModificar: " + e);
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
															&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso()))
															//&& estructuraDetalle.getIntTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0) 
															{
															
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
		} /*catch (ParseException e1) {
			log.error("Error ParseException en mostrarArchivosAdjuntos ---> "+e1);
			e1.printStackTrace();
		}*/ catch (BusinessException e2) {
			log.error("Error BusinessException en mostrarArchivosAdjuntos ---> "+e2);
			e2.printStackTrace();
		} catch (EJBFactoryException e3) {
			log.error("Error EJBFactoryException en mostrarArchivosAdjuntos ---> "+e3);
			e3.printStackTrace();
		}
	}
	
	
	
	/**
	 * Relacoinado al link para descargar el archivo adjunto de la solicitud.
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
	 * 
	 * @param event
	 * @throws BusinessException
	 * @throws EJBFactoryException
	 * @throws IOException
	 */
	public void putFile(ActionEvent event) throws BusinessException, EJBFactoryException, IOException {
		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		//System.out.println("fileupload.getStrJsFunction()----->  "+ fileupload.getStrJsFunction2());
		//System.out.println("fileupload.getObjArchivo()-------->  "+ fileupload.getObjArchivo());
		//System.out.println("fileupload.getStrJsFunction()----->  "+ fileupload.getStrJsFunction2());
		
		// fileupload.getObjArchivo();
		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {

				if (fileupload.getObjArchivo().getId().getIntParaTipoCod().equals(Constante.PARAM_T_TIPOARCHIVOADJUNTO_BOLETAPAGO)) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_BOLETAPAGO)
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							log.info("byteImg.length: "+ fileupload.getDataImage().length);
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
						&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)
						&& intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())) {
							requisitoCreditoComp.setArchivoAdjunto(fileupload.getObjArchivo());
							// log.info("byteImg.length: "+fileupload.getDataImage().length);
							byte[] byteImg = fileupload.getDataImage();
							MyFile file = new MyFile();
							file.setLength(byteImg.length);
							file.setName(fileupload.getObjArchivo().getStrNombrearchivo());
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
	public void paintImage(OutputStream stream, Object object)throws IOException {
		stream.write(((MyFile) object).getData());
	}

	
	/**
	 * Asociado al boton Adjuntoar para subir documento adjunto.
	 * 
	 * @param event
	 */
	public void adjuntarDocumento(ActionEvent event) {
		String strParaTipoDescripcion = getRequestParameter("intParaTipoDescripcion");
		String strParaTipoOperacionPersona = getRequestParameter("intParaTipoOperacionPersona");
		//log.info("strParaTipoDescripcion: " + strParaTipoDescripcion);
		//log.info("strParaTipoOperacionPersona: " + strParaTipoOperacionPersona);
		Integer intParaTipoDescripcion = new Integer(strParaTipoDescripcion);
		Integer intParaTipoOperacionPersona = new Integer(
				strParaTipoOperacionPersona);

		this.intParaTipoDescripcion = intParaTipoDescripcion;
		this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;

		FileUploadControllerServicio fileupload = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		fileupload.setStrDescripcion("Seleccione el archivo que desea adjuntar.");
		fileupload.setFileType(FileUtil.imageTypes);
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;

		if (listaRequisitoCreditoComp != null) {
			for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {


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
					&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_COPIADNI)
					&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					&& requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod().equals(Constante.PARAM_T_TIPOPERSONAREQUISITO_TITULAR)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_DNI);
				}
				
				
				//--------- agreghado para autorizacion ----------
				/*
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_INFOCORP)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_INFOCORP);
				}
				
				if (intParaTipoDescripcion.equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_RENIEC)) {
					if (requisitoCreditoComp.getRequisitoCredito() != null) {
						intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
						intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
					}
					fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_RENIEC);
				}
				*/
				//------------------------------------------------
			}
		}
		fileupload.setStrJsFunction2("putFileDocAdjuntoEspecial()");
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
			log.error("Error Exception en descargaArchivoUltimo ---> "+exc);
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
	 * Valida los campos: Tipo de Operacion, Monto Solicitado, Observacion y
	 * Unidades Ejecutoras (se debe registrar al menos uno) del Expediente.
	 * Y ademas la suma de las cuotas fijas de las capacidades de credito
	 * @param beanExpedienteCredito
	 * @return
	 */
	private Boolean isValidoExpedienteCredito(ExpedienteCredito beanExpedienteCredito) {
		Boolean validExpedienteCredito = true;
		
		try {
				if (beanExpedienteCredito.getIntParaSubTipoOperacionCod() == null) {
					setMsgTxtTipoOperacion(" * El campo Tipo de Operación debe ser ingresado.");
					validExpedienteCredito = false;
				} else {
					setMsgTxtTipoOperacion("");
				}
				if (beanExpedienteCredito.getBdMontoSolicitado() == null) {
					setMsgTxtMontoSolicitado(" * El campo Monto Solicitado debe ser ingresado.");
					validExpedienteCredito = false;
				} else {
					setMsgTxtMontoSolicitado("");
				}
				if (beanExpedienteCredito.getStrObservacion() == null
						|| beanExpedienteCredito.getStrObservacion().equals("")) {
					setMsgTxtObservacion(" * El campo de Observación debe ser ingresado.");
					validExpedienteCredito = false;
				} else {
					setMsgTxtObservacion("");
				}
				if (listaCapacidadCreditoComp == null || listaCapacidadCreditoComp.isEmpty()) {
					setMsgTxtListaCapacidadPago(" * Deben agregarse Unidades Ejecutoras.");
					validExpedienteCredito = false;
				} else {
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
	 * Guarda la Solicitud de Prestamo
	 * @param event
	 */
	public void grabarSolicitudPrestamo(ActionEvent event) {
		EstadoCredito estadoCredito = null;
		try {
			usuario = (Usuario) getRequest().getSession().getAttribute("usuario");			
			beanExpedienteCredito.getId().setIntPersEmpresaPk(beanSocioComp.getCuenta().getId().getIntPersEmpresaPk());
			beanExpedienteCredito.getId().setIntCuentaPk(beanSocioComp.getCuenta().getId().getIntCuenta());
			beanExpedienteCredito.setIntParaSubTipoOperacionCod(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO);
			beanExpedienteCredito.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
	
			//Temporalmente
			beanExpedienteCredito.setIntPersEmpresaCreditoPk(beanCredito.getId().getIntPersEmpresaPk() == null ? 0 : beanCredito.getId().getIntPersEmpresaPk());
			beanExpedienteCredito.setIntParaTipoCreditoCod(beanCredito.getId().getIntParaTipoCreditoCod()== null ? 0 : beanCredito.getId().getIntParaTipoCreditoCod());
			beanExpedienteCredito.setIntItemCredito(beanCredito.getId().getIntItemCredito() == null ? 0 : beanCredito.getId().getIntItemCredito());
			//beanExpedienteCredito.setIntParaDocumentoGeneralCod(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
			
			// 18.09.2013 - CGD
			beanExpedienteCredito.setIntPersEmpresaSucAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntEmpresaSucAdministra());
			beanExpedienteCredito.setIntSucuIdSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSucursalUsuario());
			beanExpedienteCredito.setIntSudeIdSubSucursalAdministra(beanSocioComp.getSocio().getSocioEstructura().getIntIdSubSucursalUsuario());

			beanExpedienteCredito.setBdMontoAporte(bdTotalDstosAporte);
			beanExpedienteCredito.setBdMontoGravamen(bdMontoSeguroDesgravamen);
			beanExpedienteCredito.setBdMontoTotal(bdMontoTotalSolicitado);
			beanExpedienteCredito.setBdPorcentajeAporte(new BigDecimal(strPorcAportes));
			beanExpedienteCredito.setBdPorcentajeGravamen(beanCredito.getBdTasaSeguroDesgravamen());
			beanExpedienteCredito.setBdPorcentajeInteres(new BigDecimal(strPorcInteres));
			beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
			
			if (isValidoExpedienteCredito(beanExpedienteCredito) == false) {
				strErrorGrabar=" Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.";
				return;
			}

			if (listaCapacidadCreditoComp != null
					&& listaCapacidadCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaCapacidadCreditoComp(listaCapacidadCreditoComp);
			}

	
			if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
			}
			
		// Validamos que todos requisitos se cumplan
				if (isValidTodaSolicitud() == 0) {
								estadoCredito = new EstadoCredito();
								estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
								estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
								estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
								estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
								estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
								estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
								beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);

								if((beanExpedienteCredito.getId().getIntItemExpediente() != null)
									&& (beanExpedienteCredito.getId().getIntItemDetExpediente() != null)){
									solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
									cancelarGrabarSolicitudPrestamo(event);
									
								}else{
									beanExpedienteCredito.setSocioComp(beanSocioComp);
									beanExpedienteCredito = solicitudPrestamoFacade.grabarExpedienteCredito(beanExpedienteCredito);
								}
								
								if (beanExpedienteCredito.getListaRequisitoCreditoComp() != null
									&& beanExpedienteCredito.getListaRequisitoCreditoComp().size() > 0) {
									renombrarArchivo(beanExpedienteCredito.getListaRequisitoCreditoComp());
								}
								limpiarFormSolicitudPrestamo();
								cancelarGrabarSolicitudPrestamo(event);
								//listarSolicitudPrestamo(event);
				

				} else {
					
						// Si no se graba en estado REQUISITO
						estadoCredito = new EstadoCredito();
						estadoCredito.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO);
						estadoCredito.setTsFechaEstado(obtenerFechaActual());
						estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
						estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
						estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
						estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
						beanExpedienteCredito.getListaEstadoCredito().add(estadoCredito);

						if((beanExpedienteCredito.getId().getIntItemExpediente() != null)
							&& (beanExpedienteCredito.getId().getIntItemDetExpediente() != null)){
							
							solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
							cancelarGrabarSolicitudPrestamo(event);
							
						}else{
							beanExpedienteCredito.setSocioComp(beanSocioComp);
							beanExpedienteCredito = solicitudPrestamoFacade.grabarExpedienteCredito(beanExpedienteCredito);
						}
						
						if (beanExpedienteCredito.getListaRequisitoCreditoComp() != null
								&& beanExpedienteCredito.getListaRequisitoCreditoComp().size() > 0) {
							renombrarArchivo(beanExpedienteCredito.getListaRequisitoCreditoComp());
						}
						limpiarFormSolicitudPrestamo();
						cancelarGrabarSolicitudPrestamo(event);
					
				}

		} catch (BusinessException e) {
			log.error("Error BusinessException en grabarSolicitudPrestamo --->  "+e);
			e.printStackTrace();	
		}
	}
	
	
	/**
	 * 
	 * @return
	 */
	public Timestamp obtenerFechaActual() {
		return new Timestamp(new Date().getTime());
	}
	
	/**
	 * Valida que exista Capacidad de Credito, Cronograma y los
	 * Requisitos del Credito (Adjuntos) de acuerdo a lo configurado.
	 * 
	 * @return
	 */
	private int isValidTodaSolicitud() {
		int cnt = 0;
		try {
			if (listaCapacidadCreditoComp != null && listaCapacidadCreditoComp.size() <= 0) {
				cnt++;
			}
			if (listaCronogramaCreditoComp != null && listaCronogramaCreditoComp.size() <= 0) {
				cnt++;
			}

			if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {

					if (requisitoCreditoComp.getArchivoAdjunto() == null && requisitoCreditoComp.getDetalle().getIntOpcionAdjunta() == 1) {
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
	 * Renombra los adjuntos.
	 * @param lista
	 * @throws BusinessException
	 */
	public void renombrarArchivo(List<RequisitoCreditoComp> lista) throws BusinessException {
		TipoArchivo tipoArchivo = null;
		Archivo archivo = null;
		
		try {
			for (RequisitoCreditoComp requisitoCreditoComp : lista) {
				if (requisitoCreditoComp.getArchivoAdjunto() != null) {
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
				} 
			}
		} catch (BusinessException e) {
			log.error("Error BusinessException en renombrarArchivo ---> "+e);
			throw e;
		}
	}

	/**
	 * Calcula la fecha del ler Envio y 1er Vencimiento.
	 * Toma en cuenta la existencia de salto al siguiente mes.
	 * @param estructuraDetalle
	 * @param fecHoy
	 * @return listaEnvioVencimiento (0, fec1erEnvio)(1, fec1erVenc);
	 */
	public List<Calendar> calcular1raFechaEnvioVencimiento(EstructuraDetalle estructuraDetalle, Calendar fecHoy) {
		Calendar fecEnvioTemp = Calendar.getInstance();
		String miFechaPrimerVenc = null;
		Calendar fec1erVenc  = Calendar.getInstance();
		Calendar fecVenc1 	 = Calendar.getInstance();
		Calendar fecVenc2 	 = Calendar.getInstance();
		Calendar fecVenc3 	 = Calendar.getInstance();
		Calendar fec1erEnvio = Calendar.getInstance();
		List<Calendar> listaEnvioVencimiento = new ArrayList<Calendar>();

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

		return listaEnvioVencimiento;
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
			int intFechRegMes = new Integer(strFechaRegistro.substring(3,5));
			int intFechRegAno = new Integer(strFechaRegistro.substring(6,10));
			
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

		//return listaEnvioVencimiento;
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
	 * Limpia el cronograma entre evaluacion y evaluacion
	 */
	public void limpiarCronograma(){
		if(listaCronogramaCreditoComp != null && !listaCronogramaCreditoComp.isEmpty()){
			listaCronogramaCreditoComp.clear();
		}
		if(beanExpedienteCredito.getListaCronogramaCredito() != null && !beanExpedienteCredito.getListaCronogramaCredito().isEmpty()){
			beanExpedienteCredito.getListaCronogramaCredito().clear();
		}
	}
	
	
	/**
	 * 
	 * Funciona en el modificar. LLama al generarCronograma()
	 * @param creditosRecuperado
	 * @param estructuraDetalle
	 */
	private void calcularMontosCreditoRecuperado(Credito creditosRecuperado, EstructuraDetalle estructuraDetalle) {
		Calendar dtToday = Calendar.getInstance();

		try {
			strPorcAportes = "0.00";
			bdTotalDstosAporte = BigDecimal.ZERO;
			
			if(creditosRecuperado.getBdTasaSeguroDesgravamen() != null || creditosRecuperado.getBdTasaSeguroDesgravamen().compareTo(BigDecimal.ZERO)!=0){
				//b = creditosRecuperado.getBdTasaSeguroDesgravamen();
				bdMontoSeguroDesgravamen = beanExpedienteCredito.getBdMontoSolicitado().multiply(creditosRecuperado.getBdTasaSeguroDesgravamen()).divide(new BigDecimal(100));
				bdMontoSeguroDesgravamen = bdMontoSeguroDesgravamen.divide(BigDecimal.ONE,2, RoundingMode.HALF_UP);
				if(bdMontoSeguroDesgravamen.compareTo(new BigDecimal(600))>0){
					bdMontoSeguroDesgravamen = new BigDecimal(600);
				}
				
			}else{
				bdMontoSeguroDesgravamen = BigDecimal.ZERO;
			}
			
			// Recoremos la lista de los creditos r4ecuperados
			if (creditosRecuperado.getListaDescuento() != null && creditosRecuperado.getListaDescuento().size() > 0) {
				Boolean blnContinua = Boolean.TRUE;
				
				for (CreditoDescuento creditoDescuento : creditosRecuperado.getListaDescuento()) {
					
					if(blnContinua){
						if(creditoDescuento.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							// El dscto sólo aplicará a los que estén vigentes
							
							
							//fecha fin
							if(creditoDescuento.getDtFechaFin() == null){
								blnContinua = true;
							}else{
								if(dtToday.before(creditoDescuento.getDtFechaFin())){
									blnContinua = true;
								}else{	
									blnContinua = false;
								}
							}
							// fecha inicio
							if(dtToday.getTime().after(creditoDescuento.getDtFechaIni())){
								blnContinua = true;
							}else{	
								blnContinua = false;
							}
							
							if(blnContinua){
								if(creditoDescuento.getListaDescuento() != null && !creditoDescuento.getListaDescuento().isEmpty()){
									
									for (CreditoDescuentoCaptacion creditoDescCapt : creditoDescuento.getListaDescuento()) {
										if(creditoDescCapt.getIntValor().compareTo(1)==0
											&&	creditoDescCapt.getId().getIntParaTipoCaptacionCod().compareTo(Constante.PARAM_T_CUENTACONCEPTO_APORTES)==0){
											if(blnContinua){
												if (creditoDescuento.getBdPorcentaje() != null) {
													strPorcAportes = ""+creditoDescuento.getBdPorcentaje().divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
													bdTotalDstosAporte = beanExpedienteCredito.getBdMontoSolicitado().multiply(creditoDescuento.getBdPorcentaje()).divide(new BigDecimal(100));
													
												}else{
													strPorcAportes = "---";
													bdTotalDstosAporte = creditoDescuento.getBdMonto();
												}
												blnContinua = Boolean.FALSE;
											}
										}
									}
								
							} else {
								System.out.println("Descuento : "+ creditoDescuento.getStrConcepto()+ "  esta fuera de rango "
										+ creditoDescuento.getStrDtFechaIni()
										+ creditoDescuento.getStrDtFechaFin());
							}

							}
								
						}
					}
				}
				
				

			} else {
				// msgTxtErrores=" Los datos ingresados no cumplen con alguna configuración de Credito. ";
				strPorcAportes = "0.00";
				bdTotalDstosAporte = BigDecimal.ZERO;
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

			bdMontoTotalSolicitado = beanExpedienteCredito.getBdMontoSolicitado().add(bdTotalDstosAporte).add(bdMontoSeguroDesgravamen);
			bdMontoTotalSolicitado = bdMontoTotalSolicitado.divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP);
		}
		catch (Exception e) {
			log.error("Error Exception en validarCreditoRecuperado --->  " + e);
		}

	}
	
	/**
	 * Genera el cronograma del credito validado.
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public void generarCronograma(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle) {
//		Envioconcepto envioConcepto = null; //comentado 14.05.2014 jchavez - variable no utilizada
//		Calendar miCal = Calendar.getInstance();
//		String strPeriodoPlla = ""; //comentado 14.05.2014 jchavez - variable no utilizada
		CronogramaCredito cronogramaCredito = new CronogramaCredito();
		CronogramaCreditoComp cronogramaCreditoComp = new CronogramaCreditoComp();
		List<CronogramaCredito> listaCronogramaCredito = new ArrayList<CronogramaCredito>();
		Integer intNroCuotasOld = 0;
		BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");

		try {
			if (creditoSeleccionado.getListaCreditoInteres() != null) {
				for (CreditoInteres creditoInteres : creditoSeleccionado.getListaCreditoInteres()){
					if(blnSeGeneroCronogramaCorrectamente){
						limpiarCronograma();
						if(creditoInteres.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							if(creditoInteres.getIntMesMaximo() != null){
								if(creditoInteres.getBdTasaInteres() == null || creditoInteres.getBdTasaInteres().compareTo(BigDecimal.ZERO)==0){
									bdPorcentajeInteres = BigDecimal.ZERO;
									strPorcInteres = bdPorcentajeInteres.toString();
								}else{
									bdPorcentajeInteres = creditoInteres.getBdTasaInteres();
									bdPorcentajeInteres = bdPorcentajeInteres.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
									strPorcInteres = bdPorcentajeInteres.toString();
								}
								// Se valida si el socio es Activo o cesante
								if(creditoInteres.getIntParaTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0){
									if(blnSeGeneroCronogramaCorrectamente){
										//comentado 14.05.2014 jchavez - variable no utilizada
//										envioConcepto = planillaFacade.getEnvioConceptoPorEmpPerCta(Constante.PARAM_EMPRESASESION, 
//																									obtenerPeriodoActual(),
//																									beanSocioComp.getCuenta().getId().getIntCuenta());

										intCuotasMaximas = creditoInteres.getIntMesMaximo();

										if (intNroCuotas == null || intNroCuotas == 0 || (intNroCuotas < 0)){
											intNroCuotas = creditoInteres.getIntMesMaximo();
											msgTxtExesoCuotasCronograma = "";
										}else{
											intNroCuotasOld = intNroCuotas;
											msgTxtExesoCuotasCronograma = "";
											if(intCuotasMaximas.compareTo(intNroCuotas)<0){
												intNroCuotas = creditoInteres.getIntMesMaximo();
												msgTxtExesoCuotasCronograma = "El número de Cuotas ingresada("+ intNroCuotasOld+") " +
																			  "excede al número configurado para este Crédito ("+ creditoInteres.getIntMesMaximo()+ 
																			  "). Se colocará el nro por defecto.";
											}
										}

										// detalle movimiento
										Calendar fec1erEnvio = Calendar.getInstance();
										Calendar envio = Calendar.getInstance();
//										int dia = miCal.get(Calendar.DATE);
//										int mes = miCal.get(Calendar.MONTH);
//										int anno = miCal.get(Calendar.YEAR);

//										Calendar fecHoy = Calendar.getInstance();
//										Calendar fecEnvioTemp = Calendar.getInstance();
//										String miFechaPrimerVenc = null;
//										Calendar fec1erVenc = Calendar.getInstance();
//										Calendar fecVenc1 = Calendar.getInstance();
//										Calendar fecVenc2 = Calendar.getInstance();
//										Calendar fecVenc3 = Calendar.getInstance();
										
										//-----------------------------------------------------------------
										// Calculamos el resto de dias de envio y vencimiento en base a
										// fec1erEnvio y fec1erVenc
										//-----------------------------------------------------------------
										envio.set(fec1erEnvio.get(Calendar.YEAR),
										fec1erEnvio.get(Calendar.MONTH),
										fec1erEnvio.get(Calendar.DATE));

										List<String> listaDiasEnvio = new ArrayList<String>();
										List<String> listaDiasVencimiento = new ArrayList<String>();
										int vencMes, vencAnno; //vencDia //comentado 14.05.2014 jchavez - variable no utilizada
										int envMes, envAnno; //envDia //comentado 14.05.2014 jchavez - variable no utilizada
										
//										envDia = new Integer(strFechaRegistro.substring(0,2));
										envMes = new Integer(strFechaRegistro.substring(3,5));
										envAnno = new Integer(strFechaRegistro.substring(6,10));
										
										String strVencimiento = calcular1raFechaVencimiento(estructuraDetalle);
										
//										vencDia = new Integer(strVencimiento.substring(0,2)); //comentado 14.05.2014 jchavez - variable no utilizada
										vencMes = new Integer(strVencimiento.substring(3,5));
										vencAnno = new Integer(strVencimiento.substring(6,10));

										// SE GENENERA LA LISTA DE LOS DIAS DE VENCIMIENTO listaDiasVencimiento
										for (int i = 0; i < intNroCuotas; i++) {
											Calendar nuevoDia = Calendar.getInstance();
											envMes++;
											if(i==0){
												listaDiasEnvio.add(i, strFechaRegistro);
												listaDiasVencimiento.add(i,strVencimiento);
											}else{
												listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
												nuevoDia.set(vencAnno, vencMes, 15);
												if (vencMes == 12) {
													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
													vencAnno = vencAnno + 1;
													vencMes = 0;
												} else {
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

										for (int i = 0; i < intNroCuotas; i++) {
											// calculando diferencia entre el 1er vencimeitno y la fecha de hoy
											if (i == 0) {
												diferenciaEntreDias = fechasDiferenciaEnDias((StringToCalendar(strFechaRegistro)).getTime(), 
														 (StringToCalendar(strVencimiento)).getTime());
												listaDias.add(i, diferenciaEntreDias);
											} else {
												Calendar calendario = Calendar.getInstance();
												@SuppressWarnings("deprecation")
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

										BigDecimal bdSumatoria = new BigDecimal(0);
										System.out.println("TEA" + bdTea);

										for (int i = 0; i < intNroCuotas; i++) {
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
										for (int i = 0; i < intNroCuotas; i++) {
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
											// BigDecimal.ROUND_HALF_UP);
											System.out.println("======================= CUOTA NRO " + i	+ " ========================");
											System.out.println("INTERES CUOTA " + bdInteresCuota);
											bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
											bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
											System.out.println("AMORTIZACION  " + bdAmortizacion);


											if(bdSaldo.compareTo(bdAmortizacion)<0){
												bdSaldo = BigDecimal.ZERO;
												//bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											}else{
												bdSaldo = bdSaldo.subtract(bdAmortizacion);
												bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											}

											listaSaldos.add(i, bdSaldo);
											System.out.println("SALDO   " + bdSaldo);

											if (i + 1 == intNroCuotas) {
												bdSaldo = BigDecimal.ZERO;
												BigDecimal bdSaldoRed = new BigDecimal(0);

												if (intNroCuotas == 1) {
													bdAmortizacion = bdMontoTotalSolicitado;
												} else {
													bdSaldoRed = new BigDecimal(listaSaldos.get(i - 1).toString());
													bdAmortizacion = bdSaldoRed;
												}
											} else {
												bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
												bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE,4, RoundingMode.HALF_UP);
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
											if (i + 1 == intNroCuotas) {
												cronogramaCredito.setBdMontoConcepto(bdSaldoMontoCapital);
											} else {
												cronogramaCredito.setBdMontoConcepto(i + 1 == intNroCuotas ? 
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

										bdTotalCuotaMensual = bdCuotaFinal.add(bdAportes);
//										Boolean bln90Por = Boolean.FALSE;

										if (listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()) {
											if (listaCapacidadCreditoComp.size() == 1) {
												for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
												capacidadCreditoComp.getCapacidadCredito().setBdCuotaFija(bdTotalCuotaMensual);
												}
											}else{
												// en el caso que existan mas capacidades
											}
										}

										beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
										blnSeGeneroCronogramaCorrectamente = false;
										Boolean blnValidaMontosCap = Boolean.FALSE;
										blnValidaMontosCap = validarMontosCapacidadesCredito(bdTotalCuotaMensual);

										if(blnValidaMontosCap){
											msgTxtCuotaMensual = "";
											beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);
										}else{
											listaCronogramaCreditoComp.clear();
											listaCronogramaCredito.clear();
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

	
//	/**
//	 * 
//	 * @param creditoSeleccionado
//	 * @param estructuraDetalle
//	 */
//	public void generarCronogramaEdit(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle) {
//		Envioconcepto envioConcepto = null;
//		Calendar miCal = Calendar.getInstance();
//		String strPeriodoPlla = "";
//		CronogramaCredito cronogramaCredito = null;
//		CronogramaCreditoComp cronogramaCreditoComp = null;
//		List<CronogramaCredito> listaCronogramaCredito = null;
//		Integer intNroCuotasOld = 0;
//		BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;
//		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");
//
//		try {
//			// Se trabajara sobre la fercha de registro
//			miCal.clear();
//			miCal.setTime(Constante.sdf.parse(strFechaRegistro));
//			log.error("X XX FECHA REGISTRO DEFA ULT ---> "+strFechaRegistro);
//			
//			cronogramaCredito = new CronogramaCredito();
//			cronogramaCreditoComp = new CronogramaCreditoComp();
//			listaCronogramaCredito = new ArrayList<CronogramaCredito>();
//			
//			if (creditoSeleccionado.getListaCreditoInteres() != null) {
//
//				for (CreditoInteres creditoInteres : creditoSeleccionado.getListaCreditoInteres()){
//					if(blnSeGeneroCronogramaCorrectamente){
//						limpiarCronograma();
//						
//						if(creditoInteres.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
//							if(creditoInteres.getIntMesMaximo() != null){
//								if(creditoInteres.getBdTasaInteres() == null || creditoInteres.getBdTasaInteres().compareTo(BigDecimal.ZERO)==0){
//									bdPorcentajeInteres = BigDecimal.ZERO;
//									strPorcInteres = bdPorcentajeInteres.toString();
//								}else{
//									bdPorcentajeInteres = creditoInteres.getBdTasaInteres();
//									bdPorcentajeInteres = bdPorcentajeInteres.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
//									strPorcInteres = bdPorcentajeInteres.toString();
//								}
//								// Se valida si el socio es Activo o cesante
//								if(creditoInteres.getIntParaTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0){
//									if(blnSeGeneroCronogramaCorrectamente){
//										envioConcepto = planillaFacade.getEnvioConceptoPorEmpPerCta(Constante.PARAM_EMPRESASESION, 
//																									obtenerPeriodoActual(),
//																									beanSocioComp.getCuenta().getId().getIntCuenta());
//
//										intCuotasMaximas = creditoInteres.getIntMesMaximo();
//
//										if (intNroCuotas == null || intNroCuotas == 0 || (intNroCuotas < 0)){
//											intNroCuotas = creditoInteres.getIntMesMaximo();
//											msgTxtExesoCuotasCronograma = "";
//										}else{
//											intNroCuotasOld = intNroCuotas;
//											msgTxtExesoCuotasCronograma = "";
//											if(intCuotasMaximas.compareTo(intNroCuotas)<0){
//												intNroCuotas = creditoInteres.getIntMesMaximo();
//												msgTxtExesoCuotasCronograma = "El número de Cuotas ingresada("+
//																			  intNroCuotasOld+") " +
//																			  "excede al número configurado para este Crédito ("+
//																			  creditoInteres.getIntMesMaximo()+
//																		      "). Se colocará el nro por defecto.";
//											}
//										}
//
//										// detalle movimiento
//										Calendar fec1erEnvio = Calendar.getInstance();
//										//Calendar envio = Calendar.getInstance();
//										int dia =  new Integer(Constante.sdfDia.format(miCal.getTime()));
//										int mes = new Integer(Constante.sdfMes.format(miCal.getTime())); 
//										int anno = new Integer(Constante.sdfAnno.format(miCal.getTime())); 
//
//										Calendar fec1erVenc = Calendar.getInstance();
//										Calendar fecVenc1 = Calendar.getInstance();
//										Calendar fecVenc2 = Calendar.getInstance();
//										Calendar fecVenc3 = Calendar.getInstance();
//
//										List listaDiasEnvio = new ArrayList();
//										List listaDiasVencimiento = new ArrayList();
//										int vencDia, vencMes, vencAnno;
//										int envDia, envMes, envAnno;
//
//										envDia = new Integer(strFechaRegistro.substring(0,2));
//										envMes = new Integer(strFechaRegistro.substring(3,5));
//										envAnno = new Integer(strFechaRegistro.substring(6,10));
//										
//										String strVencimiento = calcular1raFechaVencimiento(estructuraDetalle);
//
//										vencDia = new Integer(strVencimiento.substring(0,2));
//										vencMes = new Integer(strVencimiento.substring(3,5));
//										vencAnno = new Integer(strVencimiento.substring(6,10));
//
//										// SE GENENERA LA LISTA DE LOS DIAS DE VENCIMIENTO listaDiasVencimiento
//										for (int i = 0; i < intNroCuotas; i++) {
//											Calendar nuevoDia = Calendar.getInstance();
//											if(i==0){
//												listaDiasEnvio.add(i, strFechaRegistro);
//												listaDiasVencimiento.add(i,strVencimiento);
//											}else{
//												//Agregado 13.02.2014 JCHAVEZ
//												if (vencMes == 12) {
//													listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
//													nuevoDia.set(vencAnno, vencMes-1, 15);
//													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//													vencAnno = vencAnno + 1;
//													vencMes = 0;
//													envAnno ++;
//												} else {
//													listaDiasEnvio.add(i, "01" + "/" + vencMes + "/"+ envAnno);
//													nuevoDia.set(vencAnno, vencMes-1, 15);
//													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//												}
//												//Comentado 13.02.2014 JCHAVEZ
////												if (envMes == 12) {
////													listaDiasEnvio.add(i, "01" + "/" + envMes + "/"+ envAnno);
////													envAnno = envAnno + 1;
////													envMes = 0;
////												} else {
////													listaDiasEnvio.add(i, "01" + "/" + envMes + "/"+ envAnno);
////												}
////
////												nuevoDia.set(vencAnno, vencMes, 15);
////												if (vencMes == 12) {
////													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
////													vencAnno = vencAnno + 1;
////													vencMes = 0;
////												} else {
////													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
////												}
//											}
//											vencMes++;
//										}
//
//										BigDecimal bdCuotaFinal = new BigDecimal(0);
//
//										BigDecimal bdTea = BigDecimal.ZERO;
//										if(bdPorcentajeInteres.compareTo(BigDecimal.ZERO)==0){
//											bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres)).pow(12)).subtract(BigDecimal.ONE).setScale(4,
//											RoundingMode.HALF_UP));
//										}else{
//											bdTea = (((BigDecimal.ONE.add(bdPorcentajeInteres.divide(new BigDecimal(100)))).pow(12)).subtract(BigDecimal.ONE).setScale(4,
//											RoundingMode.HALF_UP));
//										}
//
//										// SE GENENERA LA LISTA DE LA DIEFERENICA DE DIAS diferenciaEntreDias
//										List listaDias = new ArrayList(); // Lista que guarda la
//										List listaSumaDias = new ArrayList(); // Lista que guarda la
//										// sumatoria de dias.
//										int diferenciaEntreDias = 0;
//										int sumaDias = 0;
//
//										for (int i = 0; i < intNroCuotas; i++) {
//											// calculando diferencia entre el 1er vencimeitno y la fecha de hoy
//											if (i == 0) {
//												diferenciaEntreDias = fechasDiferenciaEnDias((StringToCalendar(strFechaRegistro)).getTime(), 
//														 									(StringToCalendar(strVencimiento)).getTime());
//												listaDias.add(i, diferenciaEntreDias);
//											} else {
//												Calendar calendario = Calendar.getInstance();
//												Calendar calend = new GregorianCalendar(
//												getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getYear(),
//												getPrimerDiaDelMes(	StringToCalendar(listaDiasEnvio.get(i).toString())).getMonth(), 1);
//
//												calendario.set(calend.get(Calendar.YEAR),calend.get(Calendar.MONTH),calend.get(Calendar.DATE));
//												diferenciaEntreDias = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
//												listaDias.add(i, diferenciaEntreDias);
//											}
//											sumaDias = sumaDias + diferenciaEntreDias;
//											listaSumaDias.add(i, sumaDias);
//										}
//
//										// Calculamos el valor de la cuota en base a la formula:
//										// ----------------------------------------------------------//
//										// monto //
//										// cuota = _____________________________________ //
//										// //
//										// 1/ (1+tea)^(sumdias/360) + ... n //
//										// ----------------------------------------------------------//
//
//										BigDecimal bdSumatoria = new BigDecimal(0);
//										System.out.println("TEA" + bdTea);
//
//										for (int i = 0; i < intNroCuotas; i++) {
//											BigDecimal bdCalculo1 = new BigDecimal(0);
//											BigDecimal bdCalculo2 = new BigDecimal(0);
//											BigDecimal bdCalculo3 = new BigDecimal(0);
//											BigDecimal bdCalculo4 = new BigDecimal(0);
//											BigDecimal bdResultado = new BigDecimal(0);
//											BigDecimal bdUno = BigDecimal.ONE;
//											double dbResultDenom = 0;
//											bdCalculo1 = new BigDecimal(listaSumaDias.get(i).toString()); // suma de dias
//											bdCalculo2 = new BigDecimal(360); // 360
//											bdCalculo3 = bdTea.add(bdUno); // tea + 1
//											bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
//											dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
//											bdResultado = bdUno.divide(new BigDecimal(dbResultDenom),4, RoundingMode.HALF_UP);
//											bdSumatoria = bdSumatoria.add(bdResultado);
//										}
//
//										bdCuotaFinal = bdMontoTotalSolicitado.divide(bdSumatoria, 4,RoundingMode.HALF_UP);
//										System.out.println("CUOTA FINAL " + bdCuotaFinal);
//
//										// Calculando Interes, Amortizacion, Saldo y la cuota mensual total y se conforma el cronograma
//										BigDecimal bdAmortizacion = new BigDecimal(0);
//										BigDecimal bdSaldo = new BigDecimal(0);
//										BigDecimal bdSaldoMontoCapital = new BigDecimal(0);
//										// BigDecimal bdMontoCocepto = new BigDecimal(0);
//										BigDecimal bdSumaAmortizacion = new BigDecimal(0);
//										bdSaldo = bdMontoTotalSolicitado; // se inicializa el saldo con
//										// el monto solicitado
//										BigDecimal bdSaldoTemp = new BigDecimal(0);
//										List listaSaldos = new ArrayList();
//
//										//SE FORMA LA LISTA DEL CRONOGRAMA
//										for (int i = 0; i < intNroCuotas; i++) {
//											BigDecimal bdCalculo1 = new BigDecimal(0);
//											BigDecimal bdCalculo2 = new BigDecimal(0);
//											BigDecimal bdCalculo3 = new BigDecimal(0);
//											BigDecimal bdCalculo4 = new BigDecimal(0);
//											BigDecimal bdCalculo5 = new BigDecimal(0);
//											BigDecimal bdInteresCuota = new BigDecimal(0);
//											BigDecimal bdUno = BigDecimal.ONE;
//											double dbResultDenom = 0;
//											bdCalculo1 = new BigDecimal(listaDias.get(i).toString()); // suma de dias
//											bdCalculo2 = new BigDecimal(360); // 360
//											bdCalculo3 = bdTea.add(bdUno); // tea + 1
//
//											bdCalculo4 = bdCalculo1.divide(bdCalculo2, 4,RoundingMode.HALF_UP);
//											dbResultDenom = Math.pow(bdCalculo3.doubleValue(),bdCalculo4.doubleValue());
//											bdCalculo5 = new BigDecimal(dbResultDenom).subtract(bdUno);
//
//											// modificar el bdSaldoCapital para que vaya reduciendose
//											bdInteresCuota = bdSaldo.multiply(bdCalculo5);
//											bdInteresCuota = bdInteresCuota.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
//											bdInteresCuota.setScale(4, RoundingMode.HALF_UP);// setScale(4,
//											// BigDecimal.ROUND_HALF_UP);
//											System.out.println("======================= CUOTA NRO " + i	+ " ========================");
//											System.out.println("INTERES CUOTA " + bdInteresCuota);
//											bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
//											bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
//											bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
//											System.out.println("AMORTIZACION  " + bdAmortizacion);
//
//
//											if(bdSaldo.compareTo(bdAmortizacion)<0){
//												bdSaldo = BigDecimal.ZERO;
//												//bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
//											}else{
//												bdSaldo = bdSaldo.subtract(bdAmortizacion);
//												bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
//											}
//
//											listaSaldos.add(i, bdSaldo);
//											System.out.println("SALDO   " + bdSaldo);
//
//											if (i + 1 == intNroCuotas) {
//												bdSaldo = BigDecimal.ZERO;
//												BigDecimal bdSaldoRed = new BigDecimal(0);
//
//												if (intNroCuotas == 1) {
//													bdAmortizacion = bdMontoTotalSolicitado;
//												} else {
//													bdSaldoRed = new BigDecimal(listaSaldos.get(i - 1).toString());
//													bdAmortizacion = bdSaldoRed;
//												}
//											} else {
//												bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
//												bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE,4, RoundingMode.HALF_UP);
//											}
//
//											bdCuotaMensual = bdAmortizacion.add(bdInteresCuota);
//											bdAportes = bdAportes.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
//											
//						// Formando el cronograma comp que es lo que se visualiza en popup
//											cronogramaCreditoComp = new CronogramaCreditoComp();
//											cronogramaCreditoComp.setStrFechaEnvio(Constante.sdf.format((StringToCalendar(listaDiasEnvio.get(i)	.toString())).getTime()));
//											cronogramaCreditoComp.setStrFechaVencimiento(listaDiasVencimiento.get(i).toString());
//											cronogramaCreditoComp.setIntDiasTranscurridos(new Integer(listaDias.get(i).toString()));
//											cronogramaCreditoComp.setBdSaldoCapital(bdSaldo);
//											cronogramaCreditoComp.setBdAmortizacion(bdAmortizacion);
//											cronogramaCreditoComp.setBdInteres(bdInteresCuota);
//											cronogramaCreditoComp.setBdCuotaMensual(bdCuotaMensual);
//											cronogramaCreditoComp.setBdAportes(bdAportes);
//											cronogramaCreditoComp.setBdTotalCuotaMensual(bdCuotaMensual.add(bdAportes));
//											listaCronogramaCreditoComp.add(cronogramaCreditoComp);
//
//											
//						// Agregando el tipo de Concepto - "Amortización"
//											cronogramaCredito = new CronogramaCredito();
//											cronogramaCredito.setId(new CronogramaCreditoId());
//											cronogramaCredito.setIntNroCuota(i + 1);
//											cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
//											cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
//											cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION);
//											if (i == 0) {
//												bdSaldoMontoCapital = bdMontoTotalSolicitado;
//											} else {
//												bdSaldoMontoCapital = bdSaldoTemp;
//											}
//											bdSaldoTemp = bdSaldo;
//											cronogramaCredito.setBdMontoCapital(bdSaldoMontoCapital);
//											if (i + 1 == intNroCuotas) {
//												cronogramaCredito.setBdMontoConcepto(bdSaldoMontoCapital);
//											} else {
//												cronogramaCredito.setBdMontoConcepto(i + 1 == intNroCuotas ? 
//												bdAmortizacion.add(bdMontoTotalSolicitado.subtract(bdAmortizacion)): bdAmortizacion);
//											}
//											Date fechaVenc = new Date();
//											fechaVenc = (StringToCalendar(listaDiasVencimiento.get(i).toString())).getTime();
//											cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
//											cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime()).toString()));
//											cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//											// CGD-12.11.2013 - nuevos atributos
//											cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
//											cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
//											cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
//											cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
//											Calendar clEnvio= Calendar.getInstance();
//											clEnvio = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
//											cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio.getTime().getTime()));
//											listaCronogramaCredito.add(cronogramaCredito);
//
//						// Agregando el tipo de Concepto - "Interés"
//											cronogramaCredito = new CronogramaCredito();
//											cronogramaCredito.setId(new CronogramaCreditoId());
//											cronogramaCredito.setIntNroCuota(i + 1);
//											cronogramaCredito.setIntParaTipoCuotaCod(Constante.PARAM_T_TIPOCUOTACRONOGRAMA_NORMAL);
//											cronogramaCredito.setIntParaFormaPagoCod(Constante.PARAM_T_FORMADEPAGO_PLANILLA);
//											cronogramaCredito.setIntParaTipoConceptoCod(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_INTERES);
//											cronogramaCredito.setBdMontoConcepto(bdInteresCuota);
//											cronogramaCredito.setTsFechaVencimiento(new Timestamp(fechaVenc.getTime()));
//											cronogramaCredito.setIntPeriodoPlanilla(new Integer(sdfPeriodo.format(fechaVenc.getTime())));
//											cronogramaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
//											// CGD-12.11.2013 - nuevos atributos
//											cronogramaCredito.setBdAmortizacionView(cronogramaCreditoComp.getBdAmortizacion());
//											cronogramaCredito.setBdAportesView(cronogramaCreditoComp.getBdAportes());
//											cronogramaCredito.setBdInteresView(cronogramaCreditoComp.getBdInteres());
//											cronogramaCredito.setBdSaldoCapitalView(cronogramaCreditoComp.getBdSaldoCapital());
//											Calendar clEnvio2= Calendar.getInstance();
//											clEnvio2 = (StringToCalendar(cronogramaCreditoComp.getStrFechaEnvio()));
//											cronogramaCredito.setTsFechaEnvioView(new Timestamp(clEnvio2.getTime().getTime()));
//											listaCronogramaCredito.add(cronogramaCredito);
//										}
//
//										bdTotalCuotaMensual = bdCuotaFinal.add(bdAportes);
//										Boolean bln90Por = Boolean.FALSE;
//
//										if (listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()) {
//											if (listaCapacidadCreditoComp.size() == 1) {
//												for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
//												capacidadCreditoComp.getCapacidadCredito().setBdCuotaFija(bdTotalCuotaMensual);
//												}
//											}else{
//												// en el caso que existan mas capacidades
//											}
//										}
//
//										beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
//										blnSeGeneroCronogramaCorrectamente = false;
//										Boolean blnValidaMontosCap = Boolean.FALSE;
//										blnValidaMontosCap = validarMontosCapacidadesCredito(bdTotalCuotaMensual);
//
//										if(blnValidaMontosCap){
//											msgTxtCuotaMensual = "";
//											beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);
//										}else{
//											listaCronogramaCreditoComp.clear();
//											listaCronogramaCredito.clear();
//										}
//									}else{
//										blnSeGeneroCronogramaCorrectamente = true;
//										log.error("El creditoInteres.getIntParaTipoSocio() ---> "+creditoInteres.getIntParaTipoSocio());
//										strPorcInteres = "0.00";
//									}
//								}
//							}else{
//								log.error("creditoInteres.getIntMesMaximo() es nulo --> "+ creditoInteres.getIntMesMaximo());
//							}
//						}else{
//							log.error("NO HAY NADA EN creditoSeleccionado.getListaCreditoInteres() "+ creditoSeleccionado.getListaCreditoInteres().size());
//						}
//					}
//				}
//			} else {
//				strPorcInteres = "0.00";
//				log.error("NO HAY NADA EN creditoSeleccionado.getListaCreditoInteres() "+ creditoSeleccionado.getListaCreditoInteres().size());
//			}
//		} catch (Exception e) {
//			log.error("Error BusinessException en generarCronograma ---> "+e);
//			e.printStackTrace();
//		}
//	}

	/**
	 * Modificado 13.02.2014 JCHAVEZ
	 * @param creditoSeleccionado
	 * @param estructuraDetalle
	 */
	public void generarCronogramaEdit(Credito creditoSeleccionado,EstructuraDetalle estructuraDetalle) {
//		Envioconcepto envioConcepto = null; 
		Calendar miCal = Calendar.getInstance();
//		String strPeriodoPlla = ""; //comentado 14.05.2014 jchavez - variable no utilizada
		CronogramaCredito cronogramaCredito = null;
		CronogramaCreditoComp cronogramaCreditoComp = null;
		List<CronogramaCredito> listaCronogramaCredito = null;
		Integer intNroCuotasOld = 0;
		BigDecimal bdPorcentajeInteres = BigDecimal.ZERO;
		SimpleDateFormat sdfPeriodo = new SimpleDateFormat("yyyyMM");

		try {
			// Se trabajara sobre la fercha de registro
			miCal.clear();
			miCal.setTime(Constante.sdf.parse(strFechaRegistro));
			log.error("XXX FECHA REGISTRO DEFAULT ---> "+strFechaRegistro);
			
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
									strPorcInteres = bdPorcentajeInteres.toString();
								}else{
									bdPorcentajeInteres = creditoInteres.getBdTasaInteres();
									bdPorcentajeInteres = bdPorcentajeInteres.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
									strPorcInteres = bdPorcentajeInteres.toString();
								}
								// Se valida si el socio es Activo o cesante
								if(creditoInteres.getIntParaTipoSocio().compareTo(beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio())==0){
									if(blnSeGeneroCronogramaCorrectamente){
										//comentado 14.05.2014 jchavez - variable no utilizada
//										envioConcepto = planillaFacade.getEnvioConceptoPorEmpPerCta(Constante.PARAM_EMPRESASESION, 
//																									obtenerPeriodoActual(),
//																									beanSocioComp.getCuenta().getId().getIntCuenta());

										intCuotasMaximas = creditoInteres.getIntMesMaximo();

										if (intNroCuotas == null || intNroCuotas == 0 || (intNroCuotas < 0)){
											intNroCuotas = creditoInteres.getIntMesMaximo();
											msgTxtExesoCuotasCronograma = "";
										}else{
											intNroCuotasOld = intNroCuotas;
											msgTxtExesoCuotasCronograma = "";
											if(intCuotasMaximas.compareTo(intNroCuotas)<0){
												intNroCuotas = creditoInteres.getIntMesMaximo();
												msgTxtExesoCuotasCronograma = "El número de Cuotas ingresada("+
																			  intNroCuotasOld+") " +
																			  "excede al número configurado para este Crédito ("+
																			  creditoInteres.getIntMesMaximo()+
																		      "). Se colocará el nro por defecto.";
											}
										}

										// detalle movimiento
//										Calendar fec1erEnvio = Calendar.getInstance();
										//Calendar envio = Calendar.getInstance();
//										int dia =  new Integer(Constante.sdfDia.format(miCal.getTime()));
//										int mes = new Integer(Constante.sdfMes.format(miCal.getTime())); 
//										int anno = new Integer(Constante.sdfAnno.format(miCal.getTime())); 
//
//										Calendar fec1erVenc = Calendar.getInstance();
//										Calendar fecVenc1 = Calendar.getInstance();
//										Calendar fecVenc2 = Calendar.getInstance();
//										Calendar fecVenc3 = Calendar.getInstance();

										List<String> listaDiasEnvio = new ArrayList<String>();
										List<String> listaDiasVencimiento = new ArrayList<String>();
										int vencMes, vencAnno; //vencDia, 
										int envAnno; //envDia, envMes, 

//										envDia = new Integer(strFechaRegistro.substring(0,2));
//										envMes = new Integer(strFechaRegistro.substring(3,5));
										envAnno = new Integer(strFechaRegistro.substring(6,10));
										
										String strVencimiento = calcular1raFechaVencimiento(estructuraDetalle);

//										vencDia = new Integer(strVencimiento.substring(0,2));
										vencMes = new Integer(strVencimiento.substring(3,5));
										vencAnno = new Integer(strVencimiento.substring(6,10));

										// SE GENENERA LA LISTA DE LOS DIAS DE VENCIMIENTO listaDiasVencimiento
										for (int i = 0; i < intNroCuotas; i++) {
											Calendar nuevoDia = Calendar.getInstance();
											if(i==0){
												listaDiasEnvio.add(i, strFechaRegistro);
												listaDiasVencimiento.add(i,strVencimiento);
											}else{
												//Agregado 13.02.2014 JCHAVEZ
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
												//Comentado 13.02.2014 JCHAVEZ
//												if (envMes == 12) {
//													listaDiasEnvio.add(i, "01" + "/" + envMes + "/"+ envAnno);
//													envAnno = envAnno + 1;
//													envMes = 0;
//												} else {
//													listaDiasEnvio.add(i, "01" + "/" + envMes + "/"+ envAnno);
//												}
//
//												nuevoDia.set(vencAnno, vencMes, 15);
//												if (vencMes == 12) {
//													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//													vencAnno = vencAnno + 1;
//													vencMes = 0;
//												} else {
//													listaDiasVencimiento.add(i, Constante.sdf.format(getUltimoDiaDelMes(nuevoDia)));
//												}
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

										for (int i = 0; i < intNroCuotas; i++) {
											// calculando diferencia entre el 1er vencimeitno y la fecha de hoy
											if (i == 0) {
												diferenciaEntreDias = fechasDiferenciaEnDias((StringToCalendar(strFechaRegistro)).getTime(), 
														 									(StringToCalendar(strVencimiento)).getTime());
												listaDias.add(i, diferenciaEntreDias);
											} else {
												Calendar calendario = Calendar.getInstance();
												@SuppressWarnings("deprecation")
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

										BigDecimal bdSumatoria = new BigDecimal(0);
										System.out.println("TEA" + bdTea);

										for (int i = 0; i < intNroCuotas; i++) {
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
										for (int i = 0; i < intNroCuotas; i++) {
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
											// BigDecimal.ROUND_HALF_UP);
											System.out.println("======================= CUOTA NRO " + i	+ " ========================");
											System.out.println("INTERES CUOTA " + bdInteresCuota);
											bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
											bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											bdSumaAmortizacion = bdSumaAmortizacion.add(bdAmortizacion);
											System.out.println("AMORTIZACION  " + bdAmortizacion);


											if(bdSaldo.compareTo(bdAmortizacion)<0){
												bdSaldo = BigDecimal.ZERO;
												//bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											}else{
												bdSaldo = bdSaldo.subtract(bdAmortizacion);
												bdSaldo = bdSaldo.divide(BigDecimal.ONE, 4,RoundingMode.HALF_UP);
											}

											listaSaldos.add(i, bdSaldo);
											System.out.println("SALDO   " + bdSaldo);

											if (i + 1 == intNroCuotas) {
												bdSaldo = BigDecimal.ZERO;
												BigDecimal bdSaldoRed = new BigDecimal(0);

												if (intNroCuotas == 1) {
													bdAmortizacion = bdMontoTotalSolicitado;
												} else {
													bdSaldoRed = new BigDecimal(listaSaldos.get(i - 1).toString());
													bdAmortizacion = bdSaldoRed;
												}
											} else {
												bdAmortizacion = bdCuotaFinal.subtract(bdInteresCuota);
												bdAmortizacion = bdAmortizacion.divide(BigDecimal.ONE,4, RoundingMode.HALF_UP);
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
											if (i + 1 == intNroCuotas) {
												cronogramaCredito.setBdMontoConcepto(bdSaldoMontoCapital);
											} else {
												cronogramaCredito.setBdMontoConcepto(i + 1 == intNroCuotas ? 
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

										bdTotalCuotaMensual = bdCuotaFinal.add(bdAportes);
//										Boolean bln90Por = Boolean.FALSE; //comentado 14.05.2014 jchavez - variable no utilizada

										if (listaCapacidadCreditoComp != null && !listaCapacidadCreditoComp.isEmpty()) {
											if (listaCapacidadCreditoComp.size() == 1) {
												for (CapacidadCreditoComp capacidadCreditoComp : listaCapacidadCreditoComp) {
												capacidadCreditoComp.getCapacidadCredito().setBdCuotaFija(bdTotalCuotaMensual);
												}
											}else{
												// en el caso que existan mas capacidades
											}
										}

										beanExpedienteCredito.setIntNumeroCuota(intNroCuotas);
										blnSeGeneroCronogramaCorrectamente = false;
										Boolean blnValidaMontosCap = Boolean.FALSE;
										blnValidaMontosCap = validarMontosCapacidadesCredito(bdTotalCuotaMensual);

										if(blnValidaMontosCap){
											msgTxtCuotaMensual = "";
											beanExpedienteCredito.setListaCronogramaCredito(listaCronogramaCredito);
										}else{
											listaCronogramaCreditoComp.clear();
											listaCronogramaCredito.clear();
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
//		Boolean blnEsElUltimo = Boolean.FALSE; //comentado 14.05.2014 jchavez - variable no utilizada
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
						
					}	
				}
			}
			
		} catch (Exception e) {
			log.error("Error envalidarCuotaFijaVSCapacidad --->  "+e);
		}

		return blnContinua;
	}
	
	
	/**
	 * Valida que el credito cumla con configuracion de Monots, Porcentajes.
	 * 
	 * @param beanSocioComp
	 * @param credito
	 * @return
	 */
	public Boolean validacionCredito_2(SocioComp beanSocioComp, Credito credito) {
		Boolean hasMontMin = false;
		Boolean hasMontMax = false;
		Boolean hasPorcMin = false;
		Boolean hasPorcMax = false;
//		Boolean hasDiasMax = false; //comentado 14.05.2014 jchavez - variable no utilizada
		Integer nroValidaciones = new Integer(0);
		Integer contAprob = new Integer(0);
		BigDecimal bdMontoPorcMinimo = null;
		BigDecimal bdMontoPorcMaximo = null;
		Boolean boAprueba = Boolean.FALSE;

		// ---- De acuerdo a la configuracion del credito, se establece que se
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

		
		// ----- Validando las restricciones
		if (hasMontMin	&& (beanExpedienteCredito.getBdMontoSolicitado().compareTo(credito.getBdMontoMinimo()) >= 0))
				contAprob++;
		if (hasMontMax&& (beanExpedienteCredito.getBdMontoSolicitado().compareTo(credito.getBdMontoMaximo()) <= 0))
				contAprob++;
		if (hasPorcMin && (beanExpedienteCredito.getBdMontoSolicitado().compareTo(bdMontoPorcMinimo) >= 0))
				contAprob++;
		if (hasPorcMax	&& (beanExpedienteCredito.getBdMontoSolicitado().compareTo(bdMontoPorcMaximo) <= 0))
				contAprob++;

		System.out.println(" NRO DE VALIDACIONES EXISTENTES " + nroValidaciones);
		System.out.println(" NRO DE VALIDACIONES APROBADAS " + contAprob);

		if (nroValidaciones == contAprob) boAprueba = true;
		
		return boAprueba;
	}
	
	
	/**
	 * Valida que el credito seleccionado se encuentre entre los configurados por estructura, 
	 * convenio estrucutra, adenda, adendacredito.
	 * Si existe devuelve TRUE.
	 * @param credito
	 */
	public Boolean validacionCredito_1(Credito credito){
		EstructuraDetalle estructuraDetalle = null;
		List<ConvenioEstructuraDetalleComp> listaConvenioEstructuraDetalle = null;
		Boolean blnContinua = true;
		Date today = new Date();
		String strToday = ""; //Constante.sdf.format(today);
		Date dtToday = null; //Constante.sdf.parse(strToday);
		Boolean blnCreditoAprobado = Boolean.FALSE;
		
		try {
			strToday = Constante.sdf.format(today);
			dtToday = Constante.sdf.parse(strToday);
			
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
								if (adenda.getListaAdendaCreditos() != null) {
									for (AdendaCredito adendaCredito : adenda.getListaAdendaCreditos()) {
										
										if(adendaCredito.getId().getIntPersEmpresaPk().compareTo(credito.getId().getIntPersEmpresaPk())==0
											&& adendaCredito.getId().getIntParaTipoCreditoCod().compareTo(credito.getId().getIntParaTipoCreditoCod())==0
											&& adendaCredito.getId().getIntItemCredito().compareTo(credito.getId().getIntItemCredito())==0){
											
											blnCreditoAprobado = Boolean.TRUE;
											return blnCreditoAprobado;
											
										}
									}
								} 
							}		
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en validarCredito2 -->  "+e);
		}
		
		return blnCreditoAprobado;
		
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
			// Creditos movimientos con saldo
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
	 * Calcula el nro de cuotas venciadas y pagadas en base al cronogrtma de cada expediente mov
	 * @param listExpedienteMovimientoComp
	 * @return
	 */
	public List<ExpedienteComp> calcularCuotasPagadasVencidas(List<ExpedienteComp>listExpedienteMovimientoComp){
		ExpedienteComp expedienteComp= null;
		List<Cronograma> lstCronograma = null;
		Integer intCuotasPagadas =0;
//		Integer intCuotasDefinidas = 0; //comentado 14.05.2014 jchavez - variable no utilizada
		Integer intCuotasAtrasadas =  0;
//		Integer intOperacion =  0; //comentado 14.05.2014 jchavez - variable no utilizada
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
//				intCuotasDefinidas= new Integer(0); //comentado 14.05.2014 jchavez - variable no utilizada
				
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
//								EstadoExpediente primerEstado = new EstadoExpediente();
								lstEstadoExpediente = new ArrayList<EstadoExpediente>();
								lstEstadoExpediente = expedienteComp.getExpediente().getListaEstadosExpediente();
								
								//Ordenamos los subtipos por int
								Collections.sort(lstEstadoExpediente, new Comparator<EstadoExpediente>(){
									public int compare(EstadoExpediente uno, EstadoExpediente otro) {
										return uno.getId().getIntItemEstado().compareTo(otro.getId().getIntItemEstado());
									}
								});
								
//								primerEstado = lstEstadoExpediente.get(0);
								expedienteComp.getExpediente().getListaEstadosExpediente();
								
								 if((cronograma.getIntParaTipoConceptoCreditoCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOPRESTAMO_AMORTIZACION)==0)
									&&(cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)!=0)
									&& (cronograma.getTsFechaVencimiento().before(new Timestamp(new Date().getTime())))){
									 intCuotasAtrasadas ++;
								}
							}
						}
						
						if(expedienteComp.getExpediente().getIntNumeroCuota() != null){
//							intCuotasDefinidas = expedienteComp.getExpediente().getIntNumeroCuota(); //comentado 14.05.2014 jchavez - variable no utilizada
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
	 * Devuelve la descripcion de las condiciones, situaciones del garante.
	 * @param intTipo
	 * @param intValor
	 * @return
	 */
	public String cargarDesripcionValidacionesGarantes(Integer intTipo, Integer intValor){
		String strDescripcion="Descripción Desconocida";
		
		try {
                    
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
	 * CAlcula totales de Aporte, Retiro y SEpelio y los setea en cuentaComp.
	 * Ademas Define el valor de la variablke global "bdAportes", utilizado en los procesos de evaluacion, cronograma, etc etc
	 * @param pSocioComp
	 * @return SocioComp
	 */
	public SocioComp calcularTotalesConceptos (SocioComp pSocioComp){
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

//				List<CuentaConceptoDetalle> listaConceptosDetalle = new ArrayList<CuentaConceptoDetalle>();
//				listaConceptosDetalle = pSocioComp.getCuenta().getListaConcepto().get(i).getListaCuentaConceptoDetalle();
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
					}
				}
			}
		}
		
		return  pSocioComp;	
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
	 * 
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
				listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 1);

			} catch (BusinessException e) {
				log.error("Error en cargarCombosBusqueda ---> "+e);
				e.printStackTrace();
			} 
	}
	
	
	/**
	 * Metodo de limpieza asociado a h:outputText de nuevoCreditoBody.jsp
	 * Ayuda a controlar los tabs de acuerdo a perfiles de usuario logueado.
	 * @return
	 */
	public String  getLimpiarPrestamoEspecial(){
		limpiarFormSolicitudPrestamo();
		limpiarFiltros(null);
		limpiarResultadoBusqueda();
		limpiarCronograma();
		blnValidDatos= false;
		blnDatosSocio = false;
		return "";
	}
	
	
	
	/**
	 * 
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
	}
	/**
	 * Asociado al boton NUEVO.
	 */
	public void nuevoExpediente(ActionEvent event) {
		
		try {
			formSolicitudPrestamoRendered = true;
			strSolicitudPrestamo = Constante.MANTENIMIENTO_GRABAR;
			blnValidDatos = true;
			blnDatosSocio = false;
			blnEvaluacionCreditoEspecial = false;
			strFechaRegistro = Constante.sdf.format(new Date());
			intTipoEvaluacion = 1;
		} catch (Exception e) {
			log.error("Error en nuevoExpediente ---> "+e);
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
	public void modificarSolicitudPrestamo(ActionEvent event) {
		EstadoCredito estadoCredito = null;
		//List<GarantiaCredito> listaGarantiaCredito = null;
		
		try {
			
			
			usuario = (Usuario) getRequest().getSession().getAttribute("usuario");		
			
			if (isValidoExpedienteCredito(beanExpedienteCredito) == false) {
				strErrorGrabar=" Datos de Crédito no válidos. Se aborta el proceso de grabación de Crédito.";
				return;
			}
	
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
	
			if (listaCapacidadCreditoComp != null	&& listaCapacidadCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaCapacidadCreditoComp(listaCapacidadCreditoComp);
			}
	
			
			if (listaRequisitoCreditoComp != null && listaRequisitoCreditoComp.size() > 0) {
				beanExpedienteCredito.setListaRequisitoCreditoComp(listaRequisitoCreditoComp);
			}

			//10.05.2013 - CGD
			beanExpedienteCredito.setSocioComp(beanSocioComp);			
			beanExpedienteCredito.setListaCancelacionCredito(new ArrayList<CancelacionCredito>());
			solicitudPrestamoFacade.modificarExpedienteCredito(beanExpedienteCredito);
			limpiarFormSolicitudPrestamo();
			cancelarGrabarSolicitudPrestamo(event);
			
		}  catch (BusinessException e) {
			log.error("Error BusinessException en modificarSolicitudPrestamo ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	public void limpiarFormSolicitudPrestamo() {
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
		listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		bdTotalCuotaMensual = null;
		dtFechaRegistro = null;
		strFechaRegistro = Constante.sdf.format(new Date());
		strFechaRegistro = null;
		bdMontoSeguroDesgravamen = null;
		bdTotalDstosAporte = null;
		bdAportes = new BigDecimal(0);
		intNroCuotas = null;
		strPorcInteres = null;
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
		strUltimoEstadoSolicitud = "";
		blnYaTieneCredito = Boolean.FALSE;
		blnMostrarEliminar = Boolean.TRUE;
		blnMostrarModificar = Boolean.TRUE;
		blnMostrarDescripciones = Boolean.FALSE;
		listExpedienteMovimientoComp = new ArrayList<ExpedienteComp>();
		strErrorGrabar="";
		blnValidarGarantes = Boolean.FALSE;
		beanExpedienteCredito.setSocioComp(new SocioComp());
		intTipoEvaluacion = 1;
		beanExpedienteCredito.setListaEstructuraSocio(new ArrayList<Estructura>());
		strMsgErrorValidarMovimientos = "";
		strMensajeGarantesObservacion = "";
		listaCapacidadCreditoComp = new ArrayList<CapacidadCreditoComp>();
		blnTxtObservacionesPrestamo = false;
		listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
	}
	
	/**
	 * 
	 * @param event
	 */
	public void cancelarGrabarSolicitudPrestamo(ActionEvent event) {
		setFormSolicitudPrestamoRendered(false);
		limpiarFormSolicitudPrestamo();
		strSolicitudPrestamo = null;
		blnValidDatos = false;
		blnDatosSocio = false;
		blnEvaluacionCreditoEspecial = false;
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
	 * IMPRIMIR REPORTES SOLICITUD ESPECIAL
	 * RVILLARREAL 
	 * @param beanName
	 * @return
	 */
	public void imprimirAutorizacionDescuentoCesantes(){
    	String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	Tabla regimenLaboral = new Tabla();
    	lstParaQuePinteReporte.clear();
		try {
			autorizacion.setStrAbreviatura("");
			lstParaQuePinteReporte.add(autorizacion);
			
			regimenLaboral = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_REGIMENLABORAL), 
					beanSocioComp.getPersona().getNatural().getPerLaboral().getIntRegimenLaboral());
			if(regimenLaboral!=null){
				parametro.put("P_REGIMENLAB", regimenLaboral.getStrDescripcion());
			}else{
				parametro.put("P_REGIMENLAB", " ");
			}
			
			irVerSolicitudPrestamo(null);
			parametro.put("P_IDCODIGOPERSONA", beanSocioComp.getPersona().getIntIdPersona()!=null?beanSocioComp.getPersona().getIntIdPersona():" ");
			parametro.put("P_NOMYAPELLIDO", beanSocioComp.getPersona().getNatural().getStrApellidoPaterno() +" "+ beanSocioComp.getPersona().getNatural().getStrApellidoMaterno()+", "+beanSocioComp.getPersona().getNatural().getStrNombres());
			if(beanSocioComp.getPersona().getDocumento().getIntEstadoCod()==1 && beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod()==1){
			parametro.put("P_DOCUMENTO", beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad()!=null?beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad():" ");
			}
			
	       	strNombreReporte = "autorizacionDeDescuentoCesantes";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteReporte), Constante.PARAM_T_TIPOREPORTE_PDF);
					
		} catch (Exception e) {
			log.error("Error en imprimirPlanillaPrestamo ---> "+e);
		}   	
    }
	
    public void imprimirautorizacionDscto100(){
    	String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	lstParaQuePinteReporte.clear();

		try {
			autorizacion.setStrAbreviatura("");
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
    
    public void imprimirAutorizacionDescuento(){
    	String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	lstParaQuePinteReporte.clear();
//    	Tabla cargoLaboral = new Tabla();
    	Tabla cargoLaboral = null;
    	List<EstadoCredito> lstEstadoCredito = new ArrayList<EstadoCredito>();
		try {
			ContactoFacadeRemote contactoFacade =(ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);

	        autorizacion.setStrAbreviatura("");
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
			parametro.put("P_DIRECCION", (dom.getStrNombreVia()!=null?dom.getStrNombreVia(): " ") + " " + (dom.getIntNumeroVia()!=null?dom.getIntNumeroVia(): " ") + " " + (dom.getIntInterior()!=null?dom.getIntInterior(): " "));
			parametro.put("P_DEPENDENCIA", beanSocioComp.getPersona().getNatural().getListaPerLaboral().get(0).getStrCentroTrabajo());
			
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
			}//777
			if(tablaCargoLaboral!=null){
				parametro.put("P_CARGOlABORAL", tablaCargoLaboral.getStrDescripcion());
			}else
				parametro.put("P_CARGOlABORAL", " ");
			
			
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
	        String telefFijo = " ";
            String telefMovil = " ";
            String correoElectronico =" ";
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
                    		
                    	}else if(com.getIntTipoComunicacionCod().equals(Constante.PARAM_T_TIPOCOMUNICACION_CORREO) && com.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOCOMUNICACION)){
                    		
                    		correoElectronico = com.getStrDato()!=null?com.getStrDato(): " ";
                    	}
					}
				}
	        	
	        }
	        
	        parametro.put("P_COMUCASA", telefFijo);
	        parametro.put("P_COMUMOVIL", telefMovil);
	        parametro.put("P_CORREO", correoElectronico);
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
	        	parametro.put("P_GRABAMEN", " ");
	        }
	        parametro.put("P_ESTADOCIVIL", tablaEstadoCivil.getStrDescripcion());
	        if(!beanSocioComp.getPersona().getListaCuentaBancaria().isEmpty() && beanSocioComp.getPersona().getListaCuentaBancaria()!=null){
				nombreBanco = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.NOMBRE_BANCO), beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntBancoCod());
					if(nombreBanco!=null && beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntEstadoCod()==1 && beanSocioComp.getPersona().getListaCuentaBancaria().get(0).getIntMarcaAbono()==1)
						parametro.put("P_NOMBREBANCO", nombreBanco.getStrDescripcion());
					else
						parametro.put("P_NOMBREBANCO", ".....................................");
			}else{
			parametro.put("P_NOMBREBANCO", ".....................................");
				}
	        parametro.put("P_DNIGARANTE", "");
			parametro.put("P_DNIGARANTE1", "");
			parametro.put("P_DNIGARANTE2", "");
			parametro.put("P_DNIGARANTE3", "");
			parametro.put("P_DIRECCIONGARANTE", "");
			parametro.put("P_DIRECCIONGARANTE1", "");
			parametro.put("P_DIRECCIONGARANTE2", "");
			parametro.put("P_DIRECCIONGARANTE3", "");
			parametro.put("P_NOMBREGARANTE", "");
			parametro.put("P_NOMBREGARANTE1", "");
			parametro.put("P_NOMBREGARANTE2", "");
			parametro.put("P_NOMBREGARANTE3", "");
			/*Aqui Finaliza los parametros de las siguientes 2 hojas*/
	        System.out.println("Parametro " + parametro);
			prestamo.setStrAbreviatura("");
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
     * @param 13/05/2014
     * @param RVILLARREAL
     * @return
     * @throws Exception
     */
    
    public void imprimirPagare(){
    	String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla autorizacion = new Tabla();
    	lstParaQuePinteReporte.clear();
    	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);

		try {
			ContactoFacadeRemote contactoFacade =(ContactoFacadeRemote)EJBFactory.getRemote(ContactoFacadeRemote.class);
			autorizacion.setStrAbreviatura("");
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
			parametro.put("P_TASAMENSUAL", (beanCredito.getBdTasaMoratoriaMensual()!=null?beanCredito.getBdTasaMoratoriaMensual():""));
			BigDecimal montoSolicitado2 = beanExpedienteCredito.getBdMontoTotal();
			parametro.put("P_MONTOTOTAL", formato.format(montoSolicitado2));
	        String letras=ConvertirNumeroLetras.convertNumberToLetter(formato.format(montoSolicitado2).toString());
	        parametro.put("P_MONTOENLETRAS", letras);
	        
	        parametro.put("P_IDGARANTE", "");
			parametro.put("P_IDGARANTE1", "");
			parametro.put("P_IDGARANTE2", "");
			parametro.put("P_IDGARANTE3", "");
			parametro.put("PG1",  "");
			parametro.put("PG11",  "");
			parametro.put("PG12",  "");
			parametro.put("PG13",  "");
			parametro.put("PG2",  "");
			parametro.put("PG21",  "");
			parametro.put("PG22",  "");
			parametro.put("PG23",  "");
			parametro.put("PG3",  "");
			parametro.put("PG31",  "");
			parametro.put("PG32",  "");
			parametro.put("PG33",  "");
			parametro.put("PG4",  "");
			parametro.put("PG41",  "");
			parametro.put("PG42",  "");
			parametro.put("PG43",  "");
			parametro.put("PG5",  "");
			parametro.put("PG51",  "");
			parametro.put("PG52",  "");
			parametro.put("PG53",  "");
			parametro.put("PG6",  "");
			parametro.put("PG61",  "");
			parametro.put("PG62",  "");
			parametro.put("PG63",  "");
			parametro.put("PG7",  "");
			parametro.put("PG71",  "");
			parametro.put("PG72",  "");
			parametro.put("PG73",  "");
			parametro.put("PG8",  "");
			parametro.put("PG81",  "");
			parametro.put("PG82",  "");
			parametro.put("PG83",  "");
			parametro.put("P_CENTROTRABAJOGARANTE", "");
			parametro.put("P_CENTROTRABAJOGARANTE1", "");
			parametro.put("P_CENTROTRABAJOGARANTE2", "");
			parametro.put("P_CENTROTRABAJOGARANTE3", "");
			parametro.put("P_TELEFONOGARANTE", "");
			parametro.put("P_TELEFONOGARANTE1", "");
			parametro.put("P_TELEFONOGARANTE2", "");
			parametro.put("P_TELEFONOGARANTE3", "");
			parametro.put("P_DIRECCIONGARANTE", "");
			parametro.put("P_DIRECCIONGARANTE1", "");
			parametro.put("P_DIRECCIONGARANTE2", "");
			parametro.put("P_DIRECCIONGARANTE3", "");
			parametro.put("P_NOMBREGARANTE", "");
			parametro.put("P_NOMBREGARANTE1", "");
			parametro.put("P_NOMBREGARANTE2", "");
			parametro.put("P_NOMBREGARANTE3", "");

			strNombreReporte = "pagare";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(lstParaQuePinteReporte), Constante.PARAM_T_TIPOREPORTE_PDF);
					
		} catch (Exception e) {
			log.error("Error en imprimirPagare ---> "+e);
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
    	String strNombreReporte = "";
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
			autorizacion.setStrAbreviatura("");
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
						parametro.put("P_NUMEROCHEQUE", "");
					}
				}else{
					parametro.put("P_NUMEROCHEQUE", "");
				}
			}else{
				parametro.put("P_NUMEROCHEQUE", "");
			}
		}else{
			parametro.put("P_NUMEROCHEQUE", "");
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
	        parametro.put("P_NOMYAPELLIDO", apellidoPaterno.toUpperCase() +" "+ apellidoMaterno.toUpperCase()+", "+nombre.toUpperCase());
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
    
    
	
	// Geters & Seters
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
		SolicitudEspecialController.log = log;
	}

	public ExpedienteCredito getBeanExpedienteCredito() {
		return beanExpedienteCredito;
	}

	public void setBeanExpedienteCredito(ExpedienteCredito beanExpedienteCredito) {
		this.beanExpedienteCredito = beanExpedienteCredito;
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

	public Boolean getBlnValidDatos() {
		return blnValidDatos;
	}

	public void setBlnValidDatos(Boolean blnValidDatos) {
		this.blnValidDatos = blnValidDatos;
	}

	public Boolean getBlnDatosSocio() {
		return blnDatosSocio;
	}

	public void setBlnDatosSocio(Boolean blnDatosSocio) {
		this.blnDatosSocio = blnDatosSocio;
	}


	public Boolean getBlnEvaluacionCreditoEspecial() {
		return blnEvaluacionCreditoEspecial;
	}

	public void setBlnEvaluacionCreditoEspecial(Boolean blnEvaluacionCreditoEspecial) {
		this.blnEvaluacionCreditoEspecial = blnEvaluacionCreditoEspecial;
	}

	public List<ExpedienteCreditoComp> getListaExpedienteCreditoComp() {
		return listaExpedienteCreditoComp;
	}

	public void setListaExpedienteCreditoComp(
			List<ExpedienteCreditoComp> listaExpedienteCreditoComp) {
		this.listaExpedienteCreditoComp = listaExpedienteCreditoComp;
	}

	public Boolean getBlnMostrarModificar() {
		return blnMostrarModificar;
	}

	public void setBlnMostrarModificar(Boolean blnMostrarModificar) {
		this.blnMostrarModificar = blnMostrarModificar;
	}

	public Boolean getBlnMostrarEliminar() {
		return blnMostrarEliminar;
	}

	public void setBlnMostrarEliminar(Boolean blnMostrarEliminar) {
		this.blnMostrarEliminar = blnMostrarEliminar;
	}

	public List<Tabla> getListaTablaDeSucursal() {
		return listaTablaDeSucursal;
	}

	public void setListaTablaDeSucursal(List<Tabla> listaTablaDeSucursal) {
		this.listaTablaDeSucursal = listaTablaDeSucursal;
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

	public List<Tabla> getListaCondicionLaboral() {
		return listaCondicionLaboral;
	}

	public void setListaCondicionLaboral(List<Tabla> listaCondicionLaboral) {
		this.listaCondicionLaboral = listaCondicionLaboral;
	}

	public List<Tabla> getListaSituacionLaboral() {
		return listaSituacionLaboral;
	}

	public void setListaSituacionLaboral(List<Tabla> listaSituacionLaboral) {
		this.listaSituacionLaboral = listaSituacionLaboral;
	}

	public List<Tabla> getListaTablaCreditoEmpresa() {
		return listaTablaCreditoEmpresa;
	}

	public void setListaTablaCreditoEmpresa(List<Tabla> listaTablaCreditoEmpresa) {
		this.listaTablaCreditoEmpresa = listaTablaCreditoEmpresa;
	}

	public List<Tabla> getListaEstadoPrestamo() {
		return listaEstadoPrestamo;
	}

	public void setListaEstadoPrestamo(List<Tabla> listaEstadoPrestamo) {
		this.listaEstadoPrestamo = listaEstadoPrestamo;
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

	public void setSolicitudPrestamoFacade(
			SolicitudPrestamoFacadeLocal solicitudPrestamoFacade) {
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

	public void setCreditoGarantiaFacade(
			CreditoGarantiaFacadeRemote creditoGarantiaFacade) {
		this.creditoGarantiaFacade = creditoGarantiaFacade;
	}

	public GeneralFacadeRemote getGeneralFacade() {
		return generalFacade;
	}

	public void setGeneralFacade(GeneralFacadeRemote generalFacade) {
		this.generalFacade = generalFacade;
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

	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}

	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}

	public CuentaFacadeRemote getCuentaFacade() {
		return cuentaFacade;
	}

	public void setCuentaFacade(CuentaFacadeRemote cuentaFacade) {
		this.cuentaFacade = cuentaFacade;
	}

	public String getStrSolicitudPrestamo() {
		return strSolicitudPrestamo;
	}

	public void setStrSolicitudPrestamo(String strSolicitudPrestamo) {
		this.strSolicitudPrestamo = strSolicitudPrestamo;
	}

	public String getStrMensajeGarantesObservacion() {
		return strMensajeGarantesObservacion;
	}

	public void setStrMensajeGarantesObservacion(
			String strMensajeGarantesObservacion) {
		this.strMensajeGarantesObservacion = strMensajeGarantesObservacion;
	}

	public String getStrErrorGrabar() {
		return strErrorGrabar;
	}

	public void setStrErrorGrabar(String strErrorGrabar) {
		this.strErrorGrabar = strErrorGrabar;
	}

	public Boolean getFormSolicitudPrestamoRendered() {
		return formSolicitudPrestamoRendered;
	}

	public void setFormSolicitudPrestamoRendered(
			Boolean formSolicitudPrestamoRendered) {
		this.formSolicitudPrestamoRendered = formSolicitudPrestamoRendered;
	}

	public Integer getIntTipoRelacion() {
		return intTipoRelacion;
	}

	public void setIntTipoRelacion(Integer intTipoRelacion) {
		this.intTipoRelacion = intTipoRelacion;
	}

	public List<Tabla> getListaRelacion() {
		return listaRelacion;
	}

	public void setListaRelacion(List<Tabla> listaRelacion) {
		this.listaRelacion = listaRelacion;
	}

	public Integer getIntTipoCredito() {
		return intTipoCredito;
	}

	public void setIntTipoCredito(Integer intTipoCredito) {
		this.intTipoCredito = intTipoCredito;
	}

	public Persona getPersonaBusqueda() {
		return personaBusqueda;
	}

	public void setPersonaBusqueda(Persona personaBusqueda) {
		this.personaBusqueda = personaBusqueda;
	}

	public String getStrMsgErrorValidarDatos() {
		return strMsgErrorValidarDatos;
	}

	public void setStrMsgErrorValidarDatos(String strMsgErrorValidarDatos) {
		this.strMsgErrorValidarDatos = strMsgErrorValidarDatos;
	}

	public String getStrMsgErrorValidarMovimientos() {
		return strMsgErrorValidarMovimientos;
	}

	public void setStrMsgErrorValidarMovimientos(
			String strMsgErrorValidarMovimientos) {
		this.strMsgErrorValidarMovimientos = strMsgErrorValidarMovimientos;
	}

	public List<CapacidadCreditoComp> getListaCapacidadCreditoComp() {
		return listaCapacidadCreditoComp;
	}

	public void setListaCapacidadCreditoComp(
			List<CapacidadCreditoComp> listaCapacidadCreditoComp) {
		this.listaCapacidadCreditoComp = listaCapacidadCreditoComp;
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

	public List<CuentaConcepto> getListaCuentaConcepto() {
		return listaCuentaConcepto;
	}

	public void setListaCuentaConcepto(List<CuentaConcepto> listaCuentaConcepto) {
		this.listaCuentaConcepto = listaCuentaConcepto;
	}

	public BigDecimal getBdAportes() {
		return bdAportes;
	}

	public void setBdAportes(BigDecimal bdAportes) {
		this.bdAportes = bdAportes;
	}

	public List<ExpedienteComp> getListExpedienteMovimientoComp() {
		return listExpedienteMovimientoComp;
	}

	public void setListExpedienteMovimientoComp(
			List<ExpedienteComp> listExpedienteMovimientoComp) {
		this.listExpedienteMovimientoComp = listExpedienteMovimientoComp;
	}

	public String getStrUltimoEstadoSolicitud() {
		return strUltimoEstadoSolicitud;
	}

	public void setStrUltimoEstadoSolicitud(String strUltimoEstadoSolicitud) {
		this.strUltimoEstadoSolicitud = strUltimoEstadoSolicitud;
	}

	public boolean isBlnBtnAddCapacidad() {
		return blnBtnAddCapacidad;
	}

	public void setBlnBtnAddCapacidad(boolean blnBtnAddCapacidad) {
		this.blnBtnAddCapacidad = blnBtnAddCapacidad;
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

	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}

	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}

	/*public String getStrDescripcionSubTipoOperacion() {
		return strDescripcionSubTipoOperacion;
	}

	public void setStrDescripcionSubTipoOperacion(
			String strDescripcionSubTipoOperacion) {
		this.strDescripcionSubTipoOperacion = strDescripcionSubTipoOperacion;
	}*/

	public boolean isBlnMostrarDescripciones() {
		return blnMostrarDescripciones;
	}

	public void setBlnMostrarDescripciones(boolean blnMostrarDescripciones) {
		this.blnMostrarDescripciones = blnMostrarDescripciones;
	}

	public List<Estructura> getListEstructura() {
		return listEstructura;
	}

	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
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

	public boolean isBlnTextMontoPrestamo() {
		return blnTextMontoPrestamo;
	}

	public void setBlnTextMontoPrestamo(boolean blnTextMontoPrestamo) {
		this.blnTextMontoPrestamo = blnTextMontoPrestamo;
	}

	public boolean isBlnCmbTipoOperacion() {
		return blnCmbTipoOperacion;
	}

	public void setBlnCmbTipoOperacion(boolean blnCmbTipoOperacion) {
		this.blnCmbTipoOperacion = blnCmbTipoOperacion;
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

	public boolean isBlnYaTieneCredito() {
		return blnYaTieneCredito;
	}

	public void setBlnYaTieneCredito(boolean blnYaTieneCredito) {
		this.blnYaTieneCredito = blnYaTieneCredito;
	}

	public List<Tabla> getListaTipoConsultaBusqueda() {
		return listaTipoConsultaBusqueda;
	}

	public void setListaTipoConsultaBusqueda(List<Tabla> listaTipoConsultaBusqueda) {
		this.listaTipoConsultaBusqueda = listaTipoConsultaBusqueda;
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

	public ExpedienteCreditoComp getRegistroSeleccionadoBusqueda() {
		return registroSeleccionadoBusqueda;
	}

	public void setRegistroSeleccionadoBusqueda(
			ExpedienteCreditoComp registroSeleccionadoBusqueda) {
		this.registroSeleccionadoBusqueda = registroSeleccionadoBusqueda;
	}

	public Boolean getBlnValidarGarantes() {
		return blnValidarGarantes;
	}

	public void setBlnValidarGarantes(Boolean blnValidarGarantes) {
		this.blnValidarGarantes = blnValidarGarantes;
	}

	public List<Tabla> getListaSubOpePrestamos() {
		return listaSubOpePrestamos;
	}

	public void setListaSubOpePrestamos(List<Tabla> listaSubOpePrestamos) {
		this.listaSubOpePrestamos = listaSubOpePrestamos;
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

	public String getMsgTxtErrores() {
		return msgTxtErrores;
	}

	public void setMsgTxtErrores(String msgTxtErrores) {
		this.msgTxtErrores = msgTxtErrores;
	}

	public String getMsgTxtConvenioActivo() {
		return msgTxtConvenioActivo;
	}

	public void setMsgTxtConvenioActivo(String msgTxtConvenioActivo) {
		this.msgTxtConvenioActivo = msgTxtConvenioActivo;
	}

	public String getMsgTxtEvaluacionFinal() {
		return msgTxtEvaluacionFinal;
	}

	public void setMsgTxtEvaluacionFinal(String msgTxtEvaluacionFinal) {
		this.msgTxtEvaluacionFinal = msgTxtEvaluacionFinal;
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

	public void setMsgTxtExesoCuotasCronograma(String msgTxtExesoCuotasCronograma) {
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

	public void setMsgTxtCondicionSocioNingunoGarante(
			String msgTxtCondicionSocioNingunoGarante) {
		this.msgTxtCondicionSocioNingunoGarante = msgTxtCondicionSocioNingunoGarante;
	}

	public String getMsgTxtCondicionSocioGarante() {
		return msgTxtCondicionSocioGarante;
	}

	public void setMsgTxtCondicionSocioGarante(String msgTxtCondicionSocioGarante) {
		this.msgTxtCondicionSocioGarante = msgTxtCondicionSocioGarante;
	}

	public List<Sucursal> getListSucursal() {
		return listSucursal;
	}

	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}

	public List<Credito> getLstCreditosOfrecidos() {
		return lstCreditosOfrecidos;
	}

	public void setLstCreditosOfrecidos(List<Credito> lstCreditosOfrecidos) {
		this.lstCreditosOfrecidos = lstCreditosOfrecidos;
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

	public Integer getIntNroCuotas() {
		return intNroCuotas;
	}

	public void setIntNroCuotas(Integer intNroCuotas) {
		this.intNroCuotas = intNroCuotas;
	}

	/*public BigDecimal getBdMontoPrestamo() {
		return bdMontoPrestamo;
	}

	public void setBdMontoPrestamo(BigDecimal bdMontoPrestamo) {
		this.bdMontoPrestamo = bdMontoPrestamo;
	}*/

	public BigDecimal getBdMontoSeguroDesgravamen() {
		return bdMontoSeguroDesgravamen;
	}

	public void setBdMontoSeguroDesgravamen(BigDecimal bdMontoSeguroDesgravamen) {
		this.bdMontoSeguroDesgravamen = bdMontoSeguroDesgravamen;
	}

	public BigDecimal getBdTotalDstosAporte() {
		return bdTotalDstosAporte;
	}

	public void setBdTotalDstosAporte(BigDecimal bdTotalDstosAporte) {
		this.bdTotalDstosAporte = bdTotalDstosAporte;
	}

	public BigDecimal getBdTotalCuotaMensual() {
		return bdTotalCuotaMensual;
	}

	public void setBdTotalCuotaMensual(BigDecimal bdTotalCuotaMensual) {
		this.bdTotalCuotaMensual = bdTotalCuotaMensual;
	}

	public Integer getIntTipoEvaluacion() {
		return intTipoEvaluacion;
	}

	public void setIntTipoEvaluacion(Integer intTipoEvaluacion) {
		this.intTipoEvaluacion = intTipoEvaluacion;
	}

	public BigDecimal getBdMontoTotalSolicitado() {
		return bdMontoTotalSolicitado;
	}

	public void setBdMontoTotalSolicitado(BigDecimal bdMontoTotalSolicitado) {
		this.bdMontoTotalSolicitado = bdMontoTotalSolicitado;
	}

	public BigDecimal getBdCuotaMensual() {
		return bdCuotaMensual;
	}

	public void setBdCuotaMensual(BigDecimal bdCuotaMensual) {
		this.bdCuotaMensual = bdCuotaMensual;
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

	public void setListaRequisitoCreditoComp(
			List<RequisitoCreditoComp> listaRequisitoCreditoComp) {
		this.listaRequisitoCreditoComp = listaRequisitoCreditoComp;
	}

	public Boolean getBlnSeGeneroCronogramaCorrectamente() {
		return blnSeGeneroCronogramaCorrectamente;
	}

	public void setBlnSeGeneroCronogramaCorrectamente(
			Boolean blnSeGeneroCronogramaCorrectamente) {
		this.blnSeGeneroCronogramaCorrectamente = blnSeGeneroCronogramaCorrectamente;
	}

	public Integer getIntCuotasMaximas() {
		return intCuotasMaximas;
	}

	public void setIntCuotasMaximas(Integer intCuotasMaximas) {
		this.intCuotasMaximas = intCuotasMaximas;
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

	public Date getDtFechaRegistro() {
		return dtFechaRegistro;
	}

	public void setDtFechaRegistro(Date dtFechaRegistro) {
		this.dtFechaRegistro = dtFechaRegistro;
	}

	public BigDecimal getBdMaxCapacidadPago() {
		return bdMaxCapacidadPago;
	}

	public void setBdMaxCapacidadPago(BigDecimal bdMaxCapacidadPago) {
		this.bdMaxCapacidadPago = bdMaxCapacidadPago;
	}

	public BigDecimal getBdCapacidadPago() {
		return bdCapacidadPago;
	}

	public void setBdCapacidadPago(BigDecimal bdCapacidadPago) {
		this.bdCapacidadPago = bdCapacidadPago;
	}

	public BigDecimal getBdTotalDescuentos() {
		return bdTotalDescuentos;
	}

	public void setBdTotalDescuentos(BigDecimal bdTotalDescuentos) {
		this.bdTotalDescuentos = bdTotalDescuentos;
	}

	public BigDecimal getBdMontoSolicitado() {
		return bdMontoSolicitado;
	}

	public void setBdMontoSolicitado(BigDecimal bdMontoSolicitado) {
		this.bdMontoSolicitado = bdMontoSolicitado;
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

	public Tabla getTablaEstado() {
		return tablaEstado;
	}

	public void setTablaEstado(Tabla tablaEstado) {
		this.tablaEstado = tablaEstado;
	}

	public Integer getIntUltimoEstadoSolicitud() {
		return intUltimoEstadoSolicitud;
	}

	public void setIntUltimoEstadoSolicitud(Integer intUltimoEstadoSolicitud) {
		this.intUltimoEstadoSolicitud = intUltimoEstadoSolicitud;
	}

	public String getStrDescripcionPrestamoEspecial() {
		return strDescripcionPrestamoEspecial;
	}

	public void setStrDescripcionPrestamoEspecial(
			String strDescripcionPrestamoEspecial) {
		this.strDescripcionPrestamoEspecial = strDescripcionPrestamoEspecial;
	}

	public List<AutorizaCreditoComp> getListaAutorizaCreditoComp() {
		return listaAutorizaCreditoComp;
	}

	public void setListaAutorizaCreditoComp(
			List<AutorizaCreditoComp> listaAutorizaCreditoComp) {
		this.listaAutorizaCreditoComp = listaAutorizaCreditoComp;
	}

	public boolean isMostrarBoton() {
		return mostrarBoton;
	}

	public void setMostrarBoton(boolean mostrarBoton) {
		this.mostrarBoton = mostrarBoton;
	}
	
}
