package pe.com.tumi.tesoreria.fondosFijos.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.domain.GestorCobranza;
import pe.com.tumi.cobranza.gestion.facade.GestionCobranzaFacadeRemote;
import pe.com.tumi.cobranza.planilla.domain.CobroPlanillas;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumenId;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.util.AjusteMonto;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConvertirLetras;
import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.common.util.MyUtilFormatoFecha;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.ModeloFacadeRemote;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPagoId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.movimiento.cuentaCteAhorro.domain.CuentaCteAhorro;
import pe.com.tumi.movimiento.cuentaCteAhorro.facade.CuentaCteAhorroFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.CuentaBancariaPK;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresa;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;
import pe.com.tumi.tesoreria.banco.domain.BancocuentaId;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeRemote;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngreso;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleInterfaz;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManual;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;
import pe.com.tumi.tesoreria.ingreso.facade.IngresoFacadeLocal;

public class CajaController {

	protected static Logger log = Logger.getLogger(CajaController.class);
	
	private	PermisoFacadeRemote 			permisoFacade;
	private	PersonaFacadeRemote 			personaFacade;
	private	TablaFacadeRemote 				tablaFacade;
	private	GestionCobranzaFacadeRemote 	gestionCobranzaFacade;
	private	IngresoFacadeLocal 				ingresoFacade;
	private PlanillaFacadeRemote			planillaFacadeRemote;
	private BancoFacadeLocal				bancoFacade;
	private PlanCuentaFacadeRemote			planCuentaFacade;
	private EmpresaFacadeRemote				empresaFacade;
	private CierreDiarioArqueoFacadeRemote	cierreDiarioArqueoFacade;
	private EstructuraFacadeRemote			estructuraFacade;
	private	SocioFacadeRemote 				socioFacade;
	private	ConceptoFacadeRemote 			conceptoFacade;
	private SolicitudPrestamoFacadeRemote 	solicitudPrestamoFacade;
	private	CuentaCteAhorroFacadeRemote 	ctaCteAhorroFacade;
	private	List<Ingreso>				listaIngreso;
	private List<Persona>				listaPersona;
	private List<DocumentoGeneral>		listaDocumentoPorAgregar;
	private List<DocumentoGeneral>		listaDocumentoAgregados;
	private List<IngresoDetalleInterfaz>listaIngresoDetalleInterfaz;
	private List<Tabla>					listaTablaDocumentoGeneral;
	private List<Persona>	 			listaPersonaFiltro;
	private List<Bancocuenta>			listaBancoCuenta;
	private	List<Ingreso>				listaIngresoDepositar;
	private List<Tabla>					listaTablaSucursal;
	private List<Bancofondo> 			listaBancoFondo;
	private List<Subsucursal>			listaSubsucursal;
	private List<Tabla>					lstPersonaRol;
	private List<Tabla>					listaTablaTipoConceptoGeneral;
	
	private Usuario			usuarioSesion;
	private Sucursal		sucursalUsuario;
	private Subsucursal		subsucursalUsuario;
	private NumberFormat 	formato;
	private Persona			personaSeleccionada;
	private	DocumentoGeneral	documentoGeneralSeleccionado;
	private	GestorCobranza	gestorCobranzaSeleccionado;
	private	GestorCobranza	gestorCobranzaTemp;
	private	ReciboManual	reciboManual;
	private Bancofondo		bancoFondoIngresar;
	private Ingreso			ingresoFiltro;
	private LibroDiario		libroDiario;
	private Modelo			modeloPlanillaEfectuada;
	private Archivo			archivoVoucher;
	private Sucursal		sucursalIngreso;
	private Subsucursal		subsucursalIngreso;
	
	private Integer		intTipoDocumentoValidar;
	private Integer		intFormaPagoValidar;
	private Integer		intMonedaValidar;
	private	BigDecimal	bdMontoIngresar;
	private	BigDecimal	bdMontoIngresarTotal;
	private	String		strMontoIngresarTotalDescripcion;
	private	String		strObservacion;
	private Integer 	intTipoPersona;
	private Integer 	intTipoBuscarPersona;
	private	String		strFiltroTextoPersona;
	private	Date		dtFechaActual;
	private	String		strListaPersonaRolUsar;
	private Integer		intTipoDocumentoAgregar;
	private	String		strNumeroCheque;
	private Integer		intTipoPersonaFiltro;
	private Integer		intTipoBusquedaPersonaFiltro;
	private String		strTextoPersonaFiltro;
	private Integer		intBancoSeleccionado;
	private Integer		intBancoCuentaSeleccionado;
	private BigDecimal	bdMontoDepositarTotal;
	private String		strMontoDepositarTotalDescripcion;
	private BigDecimal	bdMontoAjuste;
	private String		strMontoAjusteDescripcion;
	private BigDecimal	bdOtrosIngresos;
	private	String		strNumeroOperacion;
	
	private String		mensajeOperacion;
	private	Integer		SESION_IDEMPRESA;
//	private	Integer		SESION_IDUSUARIO;
	private	Integer		SESION_IDSUCURSAL;
	private	Integer		SESION_IDSUBSUCURSAL;
	private final Integer	BUSCAR_PERSONA = 1;
	private final Integer 	BUSCAR_GESTOR = 2;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean datosValidados;
	private boolean permiso;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 13.06.2014 / 
	private String strMontoSaldoCaja;
	private Integer intTipoPersonaC;
	private Integer intPersonaRolC;
	private Boolean blnEsNaturalSocio;
	private Boolean deshabilitarTipoDocumento;
	private List<Tabla>	listaTablaModalidad;
	private List<Tabla>	listaTablaTipoSocio;
	private List<EstructuraDetalle>	listaEstructuraDetalle;
	private EstructuraComp entidadSeleccionada;
	private Boolean isDisabledRegistroDocumento;
	private Integer planillaEfectSelec;
	private List<Persona> listaGestorIngreso;
	//Autor: jchavez / Tarea: Creación / Fecha: 01.07.2014 / 
	private List<GestorCobranza> listaGestorCobranza;
	private	String		strFiltroTextoPersonaDocumento;
	private	String		strFiltroTextoPersonaNombres;
	private List<Tabla> listaTablaTipoBusqueda;
	private Integer 	intTipoBusqDocuRazonSocial;
	private BigDecimal 	bdMontoIngresadoTotal;
	private List<EfectuadoResumen> listaDetallePlanillaEfectuada;
	private Boolean blnMontoIngresadoOK;
	private String strMsgErrorMontoIngresado;
	//Autor: jchavez / Tarea: Creación / Fecha: 08.07.2014 / 
	private Archivo	archivoAdjuntoCheque;
	private boolean mostrarPanelAdjuntoCheque;
	//
	private List<Tabla> listaTablaOpcionBusqueda;
	private Integer intOpcionBusquedaC;
	private List<SocioComp> listaSocioIngresoCaja;
	private Integer intCuentaSocioC;
	private Integer intModalidadC;
	private Boolean blnDeshabilitarBusqSocioEntidadC;
	private SocioComp socioSeleccionado;
	private List<SocioComp> listaSocioCuentaIngresoCaja;
	private List<SocioComp> listaPersonaIngresoCaja;
	private String strMsgErrorSocioSeleccionado;
	private Boolean blnSocioSeleccionadoOk;
	private String strMsgSubCondicionCuenta;
	private List<Tabla> listaTablaSubCondicionCuenta;
	private List<ExpedienteComp> listaMovimientoSocio;
	private BigDecimal bdSumatoriaMontosTotalesAPagar;
	private String strDescPeriodo;
	private List<ExpedienteComp> listaIngresoSocio;
	private InteresCancelado interesCancelado;
	private Date dtFechaInicioInteresCancelado;
	private Integer intDiasEntreFechasInteresCancelado;
	private BigDecimal bdSaldoAnteriorAlPago;
	private Boolean blnValidacionesOK;
	private BigDecimal bdMontoIngresadoTotalSimulacion;
	private Ingreso ingresoGeneradoTrasGrabacion;
	//Autor: jchavez / Tarea: Creación / Fecha: 12.08.2014 / 
	private List<Tabla> listaTablaModalidadSocio;
	//Autor: jchavez / Tarea: Creación / Fecha: 14.08.2014 / 
	private BigDecimal bdMontoDepositadoTotal;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 09.09.2014
	private List<Tabla> lstTablaTipoCuenta;
	private List<Tabla> lstTablaModalidadPago;
	private Boolean blnIngresoCajaView;
		/* Variables para las vistas*/
	private String strIngCajaViewDescTipoCta;
	private String strIngCajaViewNroCta;
	private String strIngCajaViewDescModalidadPago;
	private String strIngCajaViewDescPersona;
	
	private Ingreso depositoGeneradoTrasGrabacion;
	private Boolean blnExisteRedondeo;
	private Boolean blnDepositoCajaView;
	//Autor: Rodolfo Villarreal Acuña / Tarea: Creación / Fecha: 27.11.2014
	private List<Tabla> lstReporteIngreso;
	
	//
	private String strMensajeErrorGestor;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 09.12.2014
	private Integer intTipoCuentaC;
	private Boolean blnDocumentoAgregado;
	
	public CajaController(){
		cargarUsuario();
		if(usuarioSesion!=null && poseePermiso()){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public String getInicioPage() {
		cargarUsuario();		
		if(usuarioSesion!=null){
			limpiarFormulario();
			deshabilitarPanelInferior();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}		
		return "";
	}
	
	
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		
//		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getPerfil().getId().getIntPersEmpresaPk();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		
		sucursalUsuario = usuarioSesion.getSucursal();
		subsucursalUsuario = usuarioSesion.getSubSucursal();	
		listaDetallePlanillaEfectuada = new ArrayList<EfectuadoResumen>();
	}
	
	private boolean poseePermiso(){
		permiso = Boolean.FALSE;
		try{			
			PermisoPerfilId permisoPerfilid = null;
			permisoPerfilid = new PermisoPerfilId();
			permisoPerfilid.setIntPersEmpresaPk(SESION_IDEMPRESA);
			permisoPerfilid.setIntIdTransaccion(Constante.TRANSACCION_FONDOSFIJOS_CAJA);
			permisoPerfilid.setIntIdPerfil(usuarioSesion.getPerfil().getId().getIntIdPerfil());
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			
			PermisoPerfil permisoPerfil = permisoFacade.getPermisoPerfilPorPk(permisoPerfilid);
			
			if(permisoPerfil != null && permisoPerfil.getId()!=null){
				permiso= Boolean.TRUE;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}		
		return permiso;
	}
	
	private void cargarValoresIniciales(){
		try{
			//FACADE
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			gestionCobranzaFacade = (GestionCobranzaFacadeRemote) EJBFactory.getRemote(GestionCobranzaFacadeRemote.class);
			ingresoFacade = (IngresoFacadeLocal) EJBFactory.getLocal(IngresoFacadeLocal.class);
			planillaFacadeRemote = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			cierreDiarioArqueoFacade = (CierreDiarioArqueoFacadeRemote) EJBFactory.getRemote(CierreDiarioArqueoFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			conceptoFacade  = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
			ctaCteAhorroFacade = (CuentaCteAhorroFacadeRemote) EJBFactory.getRemote(CuentaCteAhorroFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeRemote) EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			
			listaIngreso = new ArrayList<Ingreso>();
			ingresoFiltro = new Ingreso();
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			blnEsNaturalSocio = true;
			deshabilitarTipoDocumento = false;
			listaTablaModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
			
			listaTablaTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			listaTablaSubCondicionCuenta = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CONDSOCIO));
			//Autor: jchavez / Tarea: Creación / Fecha: 01.07.2014 /
			listaTablaTipoBusqueda = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOBUSQUEDA_PERSONA), "A");
			//
			listaTablaTipoConceptoGeneral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL));
			//Autor: jchavez / Tarea: Creación / Fecha: 12.08.2014 /
			listaTablaModalidadSocio = new ArrayList<Tabla>();
			//Autor: jchavez / Tarea: Creación / Fecha: 09.09.2014 /
			lstTablaTipoCuenta = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCUENTASOCIO));
			lstTablaModalidadPago = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOMODALIDADINGRESO));
			//FIN jchavez - 09.09.2014
			listaTablaOpcionBusqueda = new ArrayList<Tabla>();
			listaEstructuraDetalle = new ArrayList<EstructuraDetalle>();
			listaGestorCobranza = new ArrayList<GestorCobranza>();
			blnMontoIngresadoOK = true;
			listaSocioIngresoCaja = new ArrayList<SocioComp>();
			blnDeshabilitarBusqSocioEntidadC = true;
			listaSocioCuentaIngresoCaja = new ArrayList<SocioComp>();
			blnSocioSeleccionadoOk = true;
			listaPersonaIngresoCaja = new ArrayList<SocioComp>();			
			listaMovimientoSocio = new ArrayList<ExpedienteComp>();			
			listaDetallePlanillaEfectuada = new ArrayList<EfectuadoResumen>();
			listaDocumentoPorAgregar = new ArrayList<DocumentoGeneral>();
			listaIngresoSocio = new ArrayList<ExpedienteComp>();
			ingresoGeneradoTrasGrabacion = null;
			//Autor: Rodolfo Villarreal Acuña / Tarea: Creación / Fecha: 27.11.2014
			lstReporteIngreso = new ArrayList<Tabla>();

			blnValidacionesOK = true;
			strMsgErrorMontoIngresado = "";
			
			blnIngresoCajaView = false;
			blnDepositoCajaView = false;
			blnExisteRedondeo = false;
			strMensajeErrorGestor = "";
			blnDocumentoAgregado = true;
			
			cargarUsuario();
			cargarListaTablaSucursal();
			//Formatear montos
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#,###.00",otherSymbols);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	public void habilitarPanelInferior(){
		try{
			datosValidados = Boolean.FALSE;
			intTipoDocumentoValidar = null;
			intFormaPagoValidar = null;
//			cargarUsuario();
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			listaGestorCobranza.clear();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
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
	
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		List<Ingreso> listaIngresoDepositarAux = new ArrayList<Ingreso>();
		Boolean blnSeIngresoAlMenosUno = false;
		try {			
			
			//validaciones
			//valida si la caja asociada a la sucursal se encuentra cerrada
			log.info(sucursalIngreso);
			log.info(subsucursalIngreso);
			// 1. Valida Cierre Diario Arqueo, si existe un cierre diario para la sucursal que hace el giro, se procede a la siguiente validación
			if(cierreDiarioArqueoFacade.existeCierreDiarioArqueo(SESION_IDEMPRESA, SESION_IDSUCURSAL, SESION_IDSUBSUCURSAL)){
				// 1.1. Valida si la caja asociada a la sucursal se encuentra cerrada el dia anterior
				if(!cierreDiarioArqueoFacade.existeCierreDiaAnterior(SESION_IDEMPRESA, sucursalIngreso.getId().getIntIdSucursal(), subsucursalIngreso.getId().getIntIdSubSucursal())){
					mensaje = "La caja de la sucursal no se ha cerrado el dia anterior.";
					return;
				}
				// 1.2. Valida si la caja asociada a la sucursal se encuentra cerrada a la fecha actual
				if(cierreDiarioArqueoFacade.existeCierreDiaActual(SESION_IDEMPRESA, sucursalIngreso.getId().getIntIdSucursal(), subsucursalIngreso.getId().getIntIdSubSucursal())){
					mensaje = "La caja de la sucursal ya se ha cerrado para el dia de hoy.";
					return;
				}
			}
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)){
				if(strObservacion==null || strObservacion.isEmpty()){
					mensaje = "Debe ingresar una observación.";
					return;
				}
				if(intFormaPagoValidar.equals(Constante.PARAM_T_PAGOINGRESO_CHEQUE) && (strNumeroCheque==null || strNumeroCheque.isEmpty())){
					mensaje = "Debe ingresar un número de cheque.";
					return;
				}
				if (intFormaPagoValidar.equals(Constante.PARAM_T_PAGOINGRESO_CHEQUE)) {
					if (archivoAdjuntoCheque==null) {
						mensaje = "Debe ingresar un adjunto del cheque ingresado.";
						return;
					}
				}
				if (intPersonaRolC.equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
					if (intCuentaSocioC.equals(0)) {
						mensaje = "Debe seleccionar una cuenta del socio.";
						return;
					}
					if (intModalidadC.equals(0)) {
						mensaje = "Debe seleccionar una modalidad.";
						return;
					}
					if (listaIngresoSocio==null || listaIngresoSocio.isEmpty()) {
						mensaje = "No se ha seleccionado ningún concepto a pagar, verifique con botón Detalle.";
						return;
					}
				}else{
					if(listaDocumentoAgregados==null || listaDocumentoAgregados.isEmpty()){
						mensaje = "Debe agregar al menos un documento para el ingreso a caja.";
						return;
					}
				}
				
				if(bdMontoIngresadoTotal==null || bdMontoIngresadoTotal.signum()<=0){
					mensaje = "El monto total ingresado posee problemas, debe ser mayor a 0.";
					return;
				}
				
				//fin validaciones
				if (intPersonaRolC.equals(Constante.PARAM_T_TIPOROL_ENTIDAD)) {
					listaDocumentoAgregados = agregarDatosComplementarios(listaDocumentoAgregados);
					grabarGiro(listaDocumentoAgregados, bancoFondoIngresar, usuarioSesion);				
					
				}else if (intPersonaRolC.equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
					documentoGeneralSeleccionado = agregarDatosComplementariosSocio();
					grabarIngresoSocio(listaIngresoSocio, documentoGeneralSeleccionado, bancoFondoIngresar, usuarioSesion);
				}
				
				mensaje = "Se registró correctamente el ingreso de caja.";
			
			}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){
				
				if(archivoVoucher==null){
					mensaje = "Debe de adjuntar un voucher.";
					return;
				}
//				if(listaIngresoDepositar==null || listaIngresoDepositar.isEmpty()){
//					mensaje = "Debe de agregar al menos un ingreso para depositar.";
//					return;
//				}
				if(strObservacion==null || strObservacion.isEmpty()){
					mensaje = "Debe ingresar una observación.";
					return;
				}
				if(intBancoSeleccionado.equals(new Integer(0))){
					mensaje = "Debe seleccionar un banco valido.";
					return;
				}
				if(intBancoCuentaSeleccionado.equals(new Integer(0))){
					mensaje = "Debe seleccionar una cuenta bancaria.";
					return;
				}
				if(strNumeroOperacion==null || strNumeroOperacion.isEmpty()){
					mensaje = "Debe ingresar un numero de operacion.";
					return;
				}
				
				blnSeIngresoAlMenosUno = false;
				for(Ingreso ingresoDepositar : listaIngresoDepositar){
					if(ingresoDepositar.getBdMontoDepositar()!=null){
						listaIngresoDepositarAux.add(ingresoDepositar);
						blnSeIngresoAlMenosUno = true;		
						if(ingresoDepositar.getBdMontoDepositar().compareTo(ingresoDepositar.getBdMontoDepositable())>0){
							mensaje = "El monto de deposito es mayor que el deposito de ingreso para "+ingresoDepositar.getStrNumeroIngreso();
							return;
						}else if (ingresoDepositar.getBdMontoDepositar().compareTo(BigDecimal.ZERO)<0) {
							mensaje = "El monto de deposito no puede ser negativo para "+ingresoDepositar.getStrNumeroIngreso();
							return;
						}
					}
				}
				if (!blnSeIngresoAlMenosUno) {
					mensaje = "Debe de agregar al menos un ingreso para depositar.";
					return;
				}
				
				log.info("lista a depositar: "+listaIngresoDepositarAux);
				Bancofondo bancoFondoDepositar = obtenerBancoFondoSeleccionado();
				
				Ingreso deposito = ingresoFacade.grabarDeposito(listaIngresoDepositarAux, usuarioSesion, bancoFondoDepositar, strObservacion, 
										archivoVoucher, bdOtrosIngresos, strNumeroOperacion, bancoFondoIngresar);
				
				depositoGeneradoTrasGrabacion = deposito;
				procesarItems(depositoGeneradoTrasGrabacion);
				
				if (bdMontoAjuste!=null) {
					if (bdMontoAjuste.compareTo(BigDecimal.ZERO)!=0) {
						blnExisteRedondeo = true;
					}else {
						blnExisteRedondeo = false;
					}
				}else {
					blnExisteRedondeo = false;
				}
				
				listaIngresoDepositar.clear();
				listaIngresoDepositar.addAll(listaIngresoDepositarAux);
				calcularTotalDepositar();
				blnDepositoCajaView = true;
				mensaje = "Se registró correctamente el depósito en caja.";
			}
			
			
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
//			buscar();
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el registro.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	private List<DocumentoGeneral> agregarDatosComplementarios(List<DocumentoGeneral> listaDocumentoGeneral) throws Exception{
		for(DocumentoGeneral documentoGeneral : listaDocumentoGeneral){
			if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
				documentoGeneral.getEfectuadoResumen().setStrObservacionIngreso(strObservacion);
				documentoGeneral.getEfectuadoResumen().setStrNumeroCheque(strNumeroCheque);
				documentoGeneral.getEfectuadoResumen().setGestorCobranza(gestorCobranzaSeleccionado);
				documentoGeneral.getEfectuadoResumen().setEntidadIngreso(entidadSeleccionada);
				documentoGeneral.getEfectuadoResumen().setIntParaFormaPago(intFormaPagoValidar);
				documentoGeneral.getEfectuadoResumen().setBdMontIngresar(bdMontoIngresadoTotal);
				documentoGeneral.getEfectuadoResumen().setReciboManualIngreso(reciboManual);
				documentoGeneral.getEfectuadoResumen().setArchivoCheque(archivoAdjuntoCheque);
			}
		}
		return listaDocumentoGeneral;
	}
	
	private DocumentoGeneral agregarDatosComplementariosSocio() throws Exception{
		documentoGeneralSeleccionado.setStrObservacionIngreso(strObservacion);
		documentoGeneralSeleccionado.setStrNumeroCheque(strNumeroCheque);
		documentoGeneralSeleccionado.setGestorCobranza(gestorCobranzaSeleccionado);
		documentoGeneralSeleccionado.setSocioIngreso(new SocioComp());
		documentoGeneralSeleccionado.getSocioIngreso().setIntIngCajaIdPersona(socioSeleccionado.getIntIngCajaIdPersona());
		documentoGeneralSeleccionado.getSocioIngreso().setIntIngCajaIdCta(intCuentaSocioC);
		documentoGeneralSeleccionado.setIntParaFormaPago(intFormaPagoValidar);
		documentoGeneralSeleccionado.setBdMontoAIngresar(bdMontoIngresadoTotal);
		documentoGeneralSeleccionado.setReciboManualIngreso(reciboManual);
		documentoGeneralSeleccionado.setArchivoCheque(archivoAdjuntoCheque);
		documentoGeneralSeleccionado.setIntParaModalidadPago(intModalidadC);
		documentoGeneralSeleccionado.setIntTipoCuentaSocio(intTipoCuentaC);
		return documentoGeneralSeleccionado;
	}
	
	public void grabarGiro(List<DocumentoGeneral> listaDocumentoGeneral, Bancofondo bancoFondo, Usuario usuario) throws Exception {
		Integer intTipoDocumentoGeneral = listaDocumentoGeneral.get(0).getIntTipoDocumento();
		//Solo para el caso de documentos de planilla efectuada, todos los documentoGeneral se giraran en un solo egreso
		if(intTipoDocumentoGeneral.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
			if (listaDocumentoAgregados!=null && !listaDocumentoAgregados.isEmpty()) {
				List<EfectuadoResumen> listaEfectuadoResumen = new ArrayList<EfectuadoResumen>();
				listaEfectuadoResumen.add(listaDocumentoAgregados.get(0).getEfectuadoResumen());
				listaEfectuadoResumen.set(0, ingresoFacade.generarIngresoEfectuadoResumen(listaEfectuadoResumen, bancoFondo, usuario));
				Ingreso ingreso = ingresoFacade.grabarIngresoEfectuadoResumen(listaEfectuadoResumen.get(0).getIngresoPllaEfect(), listaEfectuadoResumen);
				log.info(ingreso);
				ingresoGeneradoTrasGrabacion = ingreso;
				procesarItems(ingresoGeneradoTrasGrabacion);
			}
		}		
	}

	public void grabarIngresoSocio(List<ExpedienteComp> listaIngresosSocio, DocumentoGeneral documentoGeneral, Bancofondo bancoFondo, Usuario usuario) throws Exception {
		if (listaIngresosSocio!=null && !listaIngresosSocio.isEmpty()) {
			documentoGeneral = ingresoFacade.generarIngresoSocio(listaIngresosSocio, documentoGeneral, bancoFondo, usuario, intModalidadC, intPersonaRolC);
			log.info(documentoGeneral);
		Ingreso ingreso = ingresoFacade.grabarIngresoSocio(listaIngresosSocio, documentoGeneral, usuario, intModalidadC);
			log.info(ingreso);
			ingresoGeneradoTrasGrabacion = ingreso;
			procesarItems(ingresoGeneradoTrasGrabacion);
		}
	}
	private void buscarPersonaFiltro(){
		try{
			listaPersonaFiltro = new ArrayList<Persona>();
			Persona persona = null;
			strTextoPersonaFiltro = strTextoPersonaFiltro.trim();
			
			if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
					Natural natural = new Natural();
					natural.setStrNombres(strTextoPersonaFiltro);
					List<Natural> listaNatural = personaFacade.getListaNaturalPorBusqueda(natural);
					for(Natural natu : listaNatural){
						listaPersonaFiltro.add(personaFacade.getPersonaPorPK(natu.getIntIdPersona()));
					}
				}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)){
					persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
							Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
							strTextoPersonaFiltro, 
							SESION_IDEMPRESA);
					if(persona!=null)listaPersonaFiltro.add(persona);
				}
				
			}else if(intTipoPersonaFiltro.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE)){
					List<Juridica>listaJuridica = personaFacade.getListaJuridicaPorRazonSocial(strTextoPersonaFiltro);
					for(Juridica juridica : listaJuridica){
						listaPersonaFiltro.add(personaFacade.getPersonaPorPK(juridica.getIntIdPersona()));
					}
					
				}else if(intTipoBusquedaPersonaFiltro.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)){
					persona = personaFacade.getPersonaPorRuc(strTextoPersonaFiltro);
					if(persona!=null)listaPersonaFiltro.add(persona);
				}
			}
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void buscar(){
		log.info("--buscar");
		try{
			ingresoFiltro.getId().setIntIdEmpresa(SESION_IDEMPRESA);
			
			listaPersonaFiltro = new ArrayList<Persona>();
			if(strTextoPersonaFiltro!=null && !strTextoPersonaFiltro.isEmpty()){
				buscarPersonaFiltro();
			}
			if(ingresoFiltro.getIntSucuIdSucursal().equals(new Integer(0))){
				ingresoFiltro.setIntSucuIdSucursal(null);
			}
			if(ingresoFiltro.getIntSudeIdSubsucursal().equals(new Integer(0))){
				ingresoFiltro.setIntSudeIdSubsucursal(null);
			}
			if(ingresoFiltro.getStrNumeroOperacion()!= null && ingresoFiltro.getStrNumeroOperacion().isEmpty()){
				ingresoFiltro.setStrNumeroOperacion(null);
			}
			log.info("nro op:"+ingresoFiltro.getStrNumeroOperacion());
			listaIngreso = ingresoFacade.buscarIngresoParaCaja(ingresoFiltro, listaPersonaFiltro);
			
			for(Ingreso ingreso : listaIngreso){
				if(ingreso.getIntPersPersonaGirado()!=null)
					ingreso.setPersona(devolverPersonaCargada(ingreso.getIntPersPersonaGirado()));
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(ActionEvent event){
		try{
			limpiarFormulario();
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			datosValidados = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			
			Ingreso ingreso = (Ingreso)event.getComponent().getAttributes().get("item");
			intTipoDocumentoValidar = ingreso.getIntParaDocumentoGeneral();
			intMonedaValidar = ingreso.getIntParaTipoMoneda();
			dtFechaActual = ingreso.getDtFechaIngreso();
			
			
			if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)){
				blnIngresoCajaView = true;
				cargarIngreso(ingreso);			
			}else if(ingreso.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){
				blnDepositoCajaView = true;
				cargarDeposito(ingreso);
			}
			
			Sucursal sucursal = new Sucursal();
			sucursal.getId().setIntPersEmpresaPk(ingreso.getId().getIntIdEmpresa());
			sucursal.getId().setIntIdSucursal(ingreso.getIntSucuIdSucursal());
			sucursalIngreso = empresaFacade.getSucursalPorPK(sucursal);
			
			subsucursalIngreso = empresaFacade.getSubSucursalPorIdSubSucursal(ingreso.getIntSudeIdSubsucursal());

			ocultarMensaje();

		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Ocurrio un error en la selección del ingreso.");
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaTablaSucursal() throws Exception{
		List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
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
	
	public void seleccionarSucursal(){
		try{
			if(ingresoFiltro.getIntSucuIdSucursal().intValue()>0){
				listaSubsucursal = empresaFacade.getListaSubSucursalPorIdSucursal(ingresoFiltro.getIntSucuIdSucursal());
			}else{
				listaSubsucursal = new ArrayList<Subsucursal>();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarDeposito(Ingreso ingreso)throws Exception{
		listaIngresoDepositar = new ArrayList<Ingreso>();
		blnExisteRedondeo = false;
		strMontoAjusteDescripcion = "";
		for(DepositoIngreso depositoIngreso : ingreso.getListaDepositoIngreso()){
			listaIngresoDepositar.add(depositoIngreso.getIngreso());
		}
		
		// Autor: jchavez / Tarea: Creación / Fecha: 11.09.2014
		depositoGeneradoTrasGrabacion = new Ingreso();
		// Autor: rVillarreal / Tarea: Modificación / Fecha: 29.11.2014
		depositoGeneradoTrasGrabacion = ingreso;
//		depositoGeneradoTrasGrabacion.setIntItemPeriodoIngreso(ingreso.getIntItemPeriodoIngreso());
//		depositoGeneradoTrasGrabacion.setIntItemIngreso(ingreso.getIntItemIngreso());
//		depositoGeneradoTrasGrabacion.setIntContPeriodoLibro(ingreso.getIntContPeriodoLibro());
//		depositoGeneradoTrasGrabacion.setIntContCodigoLibro(ingreso.getIntContCodigoLibro());
		procesarItems(depositoGeneradoTrasGrabacion);
		
		//Mostramos el ajuste
		for (IngresoDetalle ingDet : ingreso.getListaIngresoDetalle()) {
			if (ingDet.getBdAjusteDeposito()!=null) {
				if (ingDet.getBdAjusteDeposito().compareTo(BigDecimal.ZERO)!=0) {
					blnExisteRedondeo = true;
					bdMontoAjuste = ingDet.getBdAjusteDeposito();
					strMontoAjusteDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoAjuste.abs(), intMonedaValidar);
				}
			}			
		}		
		//Fin jchavez - 11.09.2014
		
		strObservacion = ingreso.getStrObservacion();
		archivoVoucher = ingreso.getArchivoVoucher();
		strNumeroOperacion = ingreso.getStrNumeroOperacion();
		calcularOtrosIngresos(ingreso);
		cargarNuevoDeposito(listaIngresoDepositar);
		intBancoSeleccionado = ingreso.getIntItemBancoFondo();
		seleccionarBanco();
		intBancoCuentaSeleccionado = ingreso.getIntItemBancoCuenta(); //jchavez - Observación: Si no pintara es xq los registros no tienen la configuración de banco actual.
		intFormaPagoValidar = ingreso.getIntParaFormaPago();
//		bdMontoDepositadoTotal = ingreso.getBdMontoTotal();
		

//		String str = bdMontoDepositar;
		// Autor: jchavez / Tarea: Creación / Fecha: 11.09.2014
		if (intFormaPagoValidar.equals(Constante.PARAM_T_PAGOINGRESO_CHEQUE)) {
			bdMontoDepositarTotal = ingreso.getBdMontoTotal();
		}else {
			bdMontoDepositarTotal = AjusteMonto.obtenerMontoAjustado(ingreso.getBdMontoTotal());
		}
		strMontoDepositarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoDepositarTotal, intMonedaValidar);
		//Fin jchavez - 11.09.2014
//		calcularTotalDepositar();	
		bdMontoDepositadoTotal = BigDecimal.ZERO;
		if (listaIngresoDepositar!=null && !listaIngresoDepositar.isEmpty()) {
			for (Ingreso x : listaIngresoDepositar) {
				bdMontoDepositadoTotal = bdMontoDepositadoTotal.add(x.getBdMontoDepositar());
			}
		}
	}
	
	private void calcularOtrosIngresos(Ingreso ingreso)throws Exception{
		//Segun DepositoService.generarIngresoDetalle el ingresoDetalle de bdOtrosIngresos posee esa condicion
		for(IngresoDetalle ingresoDetalle : ingreso.getListaIngresoDetalle()){
			if(ingresoDetalle.getIntPersPersonaGirado().equals(ingreso.getBancoFondo().getIntPersonabancoPk())
			&& ingresoDetalle.getBdAjusteDeposito()==null){
				bdOtrosIngresos = ingresoDetalle.getBdMontoAbono();
			}
		}
	}
	
	private void cargarIngreso(Ingreso ingreso)throws Exception{
		IngresoDetalle ingresoDetalle = ingreso.getListaIngresoDetalle().get(0);
		
		libroDiario = ingresoFacade.obtenerLibroDiarioPorIngreso(ingreso);
		
		PersonaEmpresa personaEmpresa = new PersonaEmpresa();
		personaEmpresa.setId(new PersonaEmpresaPK());
		personaEmpresa.getId().setIntIdEmpresa(ingreso.getIntPersEmpresaGirado());
		personaEmpresa.getId().setIntIdPersona(ingreso.getIntPersPersonaGirado());
		personaEmpresa.setListaPersonaRol(personaFacade.getListaPersonaRolPorPKPersonaEmpresa(personaEmpresa.getId()));
		ingreso.getPersona().setPersonaEmpresa(personaEmpresa);
		personaSeleccionada = ingreso.getPersona();
		filtrarListaTablaDocumentoGeneral();
		
		
		if(ingreso.getStrNumeroCheque()==null  || ingreso.getStrNumeroCheque().isEmpty()){
			intFormaPagoValidar = Constante.PARAM_T_PAGOINGRESO_EFECTIVO;
			strNumeroCheque = "";
			mostrarPanelAdjuntoCheque = false;
		}else{
			intFormaPagoValidar = Constante.PARAM_T_PAGOINGRESO_CHEQUE;
			strNumeroCheque = ingreso.getStrNumeroCheque();
			archivoAdjuntoCheque = ingresoFacade.getArchivoPorIngreso(ingreso);
			mostrarPanelAdjuntoCheque = true;
		}
			
		
		intTipoDocumentoAgregar = ingresoDetalle.getIntParaDocumentoGeneral();
		strObservacion = ingreso.getStrObservacion();
		bancoFondoIngresar = bancoFacade.obtenerBancoFondoParaIngreso(usuarioSesion, intMonedaValidar);

		//Autor: jchavez / Tarea: Creación / Fecha: 08.09.2014
		Cuenta cuentaSocio = null;
		CuentaId pPK = new CuentaId();
		documentoGeneralSeleccionado = new DocumentoGeneral();
		try {
			CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote) EJBFactory.getRemote(CuentaFacadeRemote.class);
			
			ingresoGeneradoTrasGrabacion = new Ingreso();	
			//Autor: rvillarreal / Tarea: Modificación / Fecha: 27.11.2014
			ingresoGeneradoTrasGrabacion = ingreso;			
//			ingresoGeneradoTrasGrabacion.setIntItemPeriodoIngreso(ingreso.getIntItemPeriodoIngreso());
//			ingresoGeneradoTrasGrabacion.setIntItemIngreso(ingreso.getIntItemIngreso());			
//			ingresoGeneradoTrasGrabacion.setIntContPeriodoLibro(ingreso.getIntContPeriodoLibro());
//			ingresoGeneradoTrasGrabacion.setIntContCodigoLibro(ingreso.getIntContCodigoLibro());
			
			procesarItems(ingresoGeneradoTrasGrabacion);
			
			//Obtenemos la persona
			if (ingreso.getPersona().getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)) {
				strIngCajaViewDescPersona = "Jurídica - RUC: "+ingreso.getPersona().getStrRuc()+" - "+ingreso.getPersona().getJuridica().getStrRazonSocial();
			}else if (ingreso.getPersona().getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)) {
				strIngCajaViewDescPersona = "Natural - DNI: "+ingreso.getPersona().getDocumento().getStrNumeroIdentidad()+" - "+ingreso.getPersona().getNatural().getStrNombreCompleto();
			}
			
			bdMontoIngresadoTotal = ingreso.getBdMontoTotal();
			strMontoIngresarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoIngresadoTotal, bancoFondoIngresar.getIntMonedaCod());
						
			//Obtenemos datos de la entidad
			Estructura estructura = estructuraFacade.getEstructuraPorIdEmpresaYIdPersona(SESION_IDEMPRESA,ingreso.getIntPersPersonaGirado());
			String strEtiqueta = "";
			if (estructura!=null) {
				blnEsNaturalSocio = false;
				List<CobroPlanillas> lstCobPll = planillaFacadeRemote.getListaPorIngresoCaja(ingreso);
				if (lstCobPll!=null && !lstCobPll.isEmpty()) {
					CobroPlanillas cobPll = lstCobPll.get(0);
					EfectuadoResumenId pId = new EfectuadoResumenId();
					pId.setIntEmpresa(cobPll.getId().getIntEmpresa());
					pId.setIntItemEfectuadoResumen(cobPll.getId().getIntItemEfectuadoResumen());
					EfectuadoResumen efectResumen = planillaFacadeRemote.getEfectuadoResumenPorId(pId);
					
//					strSucursalConcatenado
					entidadSeleccionada = new EstructuraComp();
					//Descripcion tipo socio
					for (Tabla tipoSocio : listaTablaTipoSocio) {
						if (efectResumen.getIntTiposocioCod().equals(tipoSocio.getIntIdDetalle())) {
							entidadSeleccionada.setStrTipoSocioConcatenado(tipoSocio.getStrDescripcion());
							break;
						}
					}
					//Descripcion modalidad
					for (Tabla modalidad : listaTablaModalidad) {
						if (efectResumen.getIntModalidadCod().equals(modalidad.getIntIdDetalle())) {
							entidadSeleccionada.setStrModalidadConcatenado(modalidad.getStrDescripcion());
							break;
						}
					}
					strIngCajaViewDescPersona = "Jurídica - RUC: "+ingreso.getPersona().getStrRuc()+
												" - "+ingreso.getPersona().getJuridica().getStrRazonSocial()+
												" - Tipo Socio: "+entidadSeleccionada.getStrTipoSocioConcatenado()+
												" - Modalidad: "+entidadSeleccionada.getStrModalidadConcatenado();
				}
				
				if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
					modeloPlanillaEfectuada = obtenerModeloPlanillaEfectuada();
					List<EfectuadoResumen> listaEfectuadoResumen = ingresoFacade.obtenerListaEfectuadoResumenPorIngreso(ingreso);
					for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
						log.info(efectuadoResumen);
						documentoGeneralSeleccionado = new DocumentoGeneral();
						documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
						documentoGeneralSeleccionado.setStrNroDocumento(""+efectuadoResumen.getId().getIntItemEfectuadoResumen());
						documentoGeneralSeleccionado.setEfectuadoResumen(efectuadoResumen);
						documentoGeneralSeleccionado.setBdMonto(efectuadoResumen.getBdMontIngresar());
						documentoGeneralSeleccionado.setBdSubTotal(documentoGeneralSeleccionado.getBdMonto());
						documentoGeneralSeleccionado.setIntPeriodoPlanilla(efectuadoResumen.getIntPeriodoPlanilla());
						bdMontoIngresar = efectuadoResumen.getBdMontIngresar();
//						agregarDocumento();
						strEtiqueta = "Periodo: "+documentoGeneralSeleccionado.getIntPeriodoPlanilla()+" - Monto: "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" "+formato.format(documentoGeneralSeleccionado.getBdMonto())
										+" - Monto Pagado: "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" "+formato.format(ingreso.getBdMontoTotal());
						documentoGeneralSeleccionado.setStrEtiqueta(strEtiqueta);
					}			
				//soporte temporal para cierre de fondos
				}
			}else {
				blnEsNaturalSocio = true;
				//Obtenemos datos del Socio
				pPK.setIntPersEmpresaPk(ingreso.getId().getIntIdEmpresa());
				pPK.setIntCuenta(ingreso.getIntCuentaGirado());
				cuentaSocio = cuentaFacade.getCuentaPorId(pPK);
				for (Tabla x : lstTablaTipoCuenta) {
					if (x.getIntIdDetalle().equals(cuentaSocio.getIntParaTipoCuentaCod())) {
						strIngCajaViewDescTipoCta = x.getStrDescripcion();
						strIngCajaViewNroCta = cuentaSocio.getStrNumeroCuenta();
						break;
					}
				}
				
				if (ingreso.getIntParaModalidadPago() != null) {
					for (Tabla modalidad : lstTablaModalidadPago) {
						if (modalidad.getIntIdDetalle().equals(ingreso.getIntParaModalidadPago())) {
							strIngCajaViewDescModalidadPago = modalidad.getStrDescripcion();
							break;
						}
					}				
					if (ingreso.getIntParaModalidadPago().equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION) || ingreso.getIntParaModalidadPago().equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE) || ingreso.getIntParaModalidadPago().equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA)) {
						documentoGeneralSeleccionado.setStrEtiqueta("Periodo: "+ingreso.getIntItemPeriodoIngreso()
									+" - Monto Total: "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" "+formato.format(ingreso.getBdMontoTotal()));
					}else if(ingreso.getIntParaModalidadPago().equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES) || ingreso.getIntParaModalidadPago().equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_ADELANTO_CANCELACION)){
						documentoGeneralSeleccionado.setStrEtiqueta("Aporte Acumulado: "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" "+formato.format(ingreso.getBdMontoTotal()));
					}
				}else {
					strIngCajaViewDescModalidadPago = " ";
					documentoGeneralSeleccionado.setStrEtiqueta(" ");
				}
				
				Sucursal sucursal = new Sucursal();
				listaIngresoDetalleInterfaz = new ArrayList<IngresoDetalleInterfaz>();
				
				sucursal.getId().setIntPersEmpresaPk(ingreso.getId().getIntIdEmpresa());
				sucursal.getId().setIntIdSucursal(ingreso.getIntSucuIdSucursal());
				EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
				sucursal = empresaFacade.getSucursalPorPK(sucursal);
				Subsucursal subsucursal = empresaFacade.getSubSucursalPorIdSubSucursal(ingreso.getIntSudeIdSubsucursal());

				for (IngresoDetalle ingDet : ingreso.getListaIngresoDetalle()) {
					IngresoDetalleInterfaz ingresoDetalleInterfaz = new IngresoDetalleInterfaz();
					ingresoDetalleInterfaz.setIntDocumentoGeneral(ingDet.getIntParaDocumentoGeneral());
					ingresoDetalleInterfaz.setStrNroDocumento(ingreso.getIntItemPeriodoIngreso()+"-"+ingreso.getId().getIntIdIngresoGeneral());
					ingresoDetalleInterfaz.setPersona(ingreso.getPersona());
					ingresoDetalleInterfaz.setSucursal(sucursal);
					ingresoDetalleInterfaz.setSubsucursal(subsucursal);
					ingresoDetalleInterfaz.setStrDescripcion(ingDet.getStrDescripcionIngreso());
					ingresoDetalleInterfaz.setBdMonto(ingDet.getBdMontoAbono());
					ingresoDetalleInterfaz.setLibroDiario(libroDiario);
					listaIngresoDetalleInterfaz.add(ingresoDetalleInterfaz);
				}
			}
			
			reciboManual = ingresoFacade.obtenerReciboManualPorIngreso(ingreso);
			if(reciboManual!=null){
				ReciboManualDetalle reciboManualDetalle = reciboManual.getListaReciboManualDetalle().get(0);
				reciboManual.setReciboManualDetalleUltimo(reciboManualDetalle);
				gestorCobranzaSeleccionado = new GestorCobranza();
				gestorCobranzaSeleccionado.setPersona(devolverPersonaCargada(reciboManualDetalle.getIntPersPersonaGestor()));
			}
			
			
		} catch (Exception e) {
			log.info("Error en cargarIngreso() ---> "+e.getMessage());
		}		
		//Fin jchavez 08.09.2014
		
		
//		else{
////			bdMontoIngresarTotal = ingreso.getBdMontoTotal();
//			bdMontoIngresadoTotal = ingreso.getBdMontoTotal();
//			strMontoIngresarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoIngresadoTotal, bancoFondoIngresar.getIntMonedaCod());
//			
//			Sucursal sucursal = new Sucursal();
//			listaIngresoDetalleInterfaz = new ArrayList<IngresoDetalleInterfaz>();
//			
//			sucursal.getId().setIntPersEmpresaPk(ingreso.getId().getIntIdEmpresa());
//			sucursal.getId().setIntIdSucursal(ingreso.getIntSucuIdSucursal());
//			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
//			sucursal = empresaFacade.getSucursalPorPK(sucursal);
//			Subsucursal subsucursal = empresaFacade.getSubSucursalPorIdSubSucursal(ingreso.getIntSudeIdSubsucursal());
//
//			for (IngresoDetalle ingDet : ingreso.getListaIngresoDetalle()) {
//				IngresoDetalleInterfaz ingresoDetalleInterfaz = new IngresoDetalleInterfaz();
//				ingresoDetalleInterfaz.setIntDocumentoGeneral(ingDet.getIntParaDocumentoGeneral());
//				ingresoDetalleInterfaz.setStrNroDocumento(ingreso.getIntItemPeriodoIngreso()+"-"+ingreso.getId().getIntIdIngresoGeneral());
//				ingresoDetalleInterfaz.setPersona(ingreso.getPersona());
//				ingresoDetalleInterfaz.setSucursal(sucursal);
//				ingresoDetalleInterfaz.setSubsucursal(subsucursal);
//				ingresoDetalleInterfaz.setStrDescripcion(ingDet.getStrDescripcionIngreso());
//				ingresoDetalleInterfaz.setBdMonto(ingDet.getBdMontoAbono());
//				ingresoDetalleInterfaz.setLibroDiario(libroDiario);
//				listaIngresoDetalleInterfaz.add(ingresoDetalleInterfaz);
//			}
//		}
	}
	
	public void procesarItems(Ingreso ingreso){ 
		ingreso.setStrNumeroIngreso(
				obtenerPeriodoItem(	ingreso.getIntItemPeriodoIngreso(), 
									ingreso.getIntItemIngreso(), 
									"000000"));
			
		if(ingreso.getIntContPeriodoLibro()!=null){
			ingreso.setStrNumeroLibro(
					obtenerPeriodoItem(	ingreso.getIntContPeriodoLibro(),
										ingreso.getIntContCodigoLibro(), 
										"000000"));
		}		
	}
	
	private String obtenerPeriodoItem(Integer intPeriodo, Integer item, String patron){
		try{
			DecimalFormat formato = new DecimalFormat(patron);
			return intPeriodo+"-"+formato.format(Double.parseDouble(""+item));
		}catch (Exception e) {
			log.error(intPeriodo+" "+item+" "+patron+e.getMessage());
			return intPeriodo+"-"+item;
		}
	}
	
	//Autor: jchavez / Tarea: Modificación / Fecha:18.06.2014 
	private Modelo obtenerModeloPlanillaEfectuada()throws Exception{
		List<Modelo> listaModelo = null;
		Modelo rtnModelo = null;
		try {
			ModeloFacadeRemote modeloFacade = (ModeloFacadeRemote) EJBFactory.getRemote(ModeloFacadeRemote.class);
			listaModelo = modeloFacade.obtenerTipoModeloActual(Constante.PARAM_T_TIPOMODELOCONTABLE_CANCELACIONPLANILLAEFECTUADA, 
					SESION_IDEMPRESA);
			if(listaModelo!=null && !listaModelo.isEmpty()){
				rtnModelo = listaModelo.get(0);
			}else{
				throw new Exception("No existe modelo contable para el periodo actual.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		
		return rtnModelo;
	}
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}

	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	private void limpiarFormulario(){
		strObservacion = "";
		bdMontoIngresar = null;
		bdMontoIngresarTotal = null;
		strMontoIngresarTotalDescripcion = "";
		gestorCobranzaSeleccionado = null;
		personaSeleccionada = null;
		strListaPersonaRolUsar = "";
		listaIngresoDetalleInterfaz = new ArrayList<IngresoDetalleInterfaz>();
		listaDocumentoAgregados = new ArrayList<DocumentoGeneral>();
		strNumeroCheque = "";
		libroDiario = null;
		archivoVoucher = null;
		archivoAdjuntoCheque = null;
		listaIngresoDepositar = new ArrayList<Ingreso>();
		listaBancoFondo = new ArrayList<Bancofondo>();
		bdMontoDepositarTotal = null;
		strMontoDepositarTotalDescripcion = "";
		bdMontoAjuste = null;
		strMontoAjusteDescripcion = "";
		bdOtrosIngresos = null;
		strNumeroOperacion = "";
		listaBancoFondo = new ArrayList<Bancofondo>();
		listaBancoCuenta = new ArrayList<Bancocuenta>();
		intBancoSeleccionado = 0;
		intBancoCuentaSeleccionado = 0;
		entidadSeleccionada = null;
		listaEstructuraDetalle.clear();
		intTipoPersonaC = 0;
		intPersonaRolC = 0;
		intTipoDocumentoAgregar = 0;
		listaGestorCobranza.clear();
		documentoGeneralSeleccionado = null;
		bdMontoIngresadoTotal = null;
		bdMontoDepositadoTotal = null;
		listaPersonaIngresoCaja.clear();
		listaSocioCuentaIngresoCaja.clear();
		listaSocioIngresoCaja.clear();
		socioSeleccionado = null;
		listaDetallePlanillaEfectuada.clear();
		listaDocumentoPorAgregar.clear();
		listaIngresoSocio.clear();
		//
		blnValidacionesOK = true;
		intModalidadC = 0;
		intCuentaSocioC = 0;
		reciboManual = null;
		ingresoGeneradoTrasGrabacion = null;
		blnIngresoCajaView = false;
		blnDepositoCajaView = false;
		blnExisteRedondeo = false;
		strMensajeErrorGestor = "";
		intTipoCuentaC = 0;
		blnDocumentoAgregado = false;
		listaTablaModalidadSocio.clear();
	}
	
	private void calcularTotalDepositar()throws Exception{
		bdMontoDepositarTotal = new BigDecimal(0);
		bdMontoAjuste = new BigDecimal(0);
		if(listaIngresoDepositar==null)
			return;
		
		//Autor: jchavez / Tarea: Modificación / Fecha: 08.08.2014 / 
		if (intFormaPagoValidar.equals(Constante.PARAM_T_PAGOINGRESO_CHEQUE)) {
			bdMontoDepositarTotal = bdMontoDepositadoTotal;
		}else {
			bdMontoDepositarTotal = AjusteMonto.obtenerMontoAjustado(bdMontoDepositadoTotal);
		}
		bdMontoAjuste = bdMontoDepositadoTotal.subtract(bdMontoDepositarTotal);
		//Fin jchavez - 14.08.2014
		
		//Autor: jchavez / Tarea: Modificación / Fecha: 08.08.2014 / No va, segun REUNION MOD TESORERIA 13.08.2014
//		if(bdOtrosIngresos!=null){
//			bdMontoDepositarTotal = bdMontoDepositarTotal.add(bdOtrosIngresos);
//		}

		//BigDecimal bdMontoDepositarTotalAjustado = AjusteMonto.obtenerMontoAjustado(bdMontoDepositarTotal);

//		bdMontoDepositarTotal = bdMontoDepositarTotalAjustado;
		strMontoDepositarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoDepositarTotal, intMonedaValidar);
		strMontoAjusteDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoAjuste.abs(), intMonedaValidar);
	}	
	
	public void validarDatos(){
		try{
			datosValidados = Boolean.FALSE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelAdjuntoCheque = false;
			strMsgSubCondicionCuenta = "";
			limpiarFormulario();
			
			if(intMonedaValidar.equals(Constante.PARAM_T_TIPOMONEDA_DOLARES)){
				mostrarMensaje(Boolean.FALSE, "Actualmente no hay soporte para fondos de caja en dolares.");
				return;
			}
			
			bancoFondoIngresar = bancoFacade.obtenerBancoFondoParaIngreso(usuarioSesion, intMonedaValidar);
			if(bancoFondoIngresar==null || bancoFondoIngresar.getFondoDetalleUsar()==null){
				mostrarMensaje(Boolean.FALSE, "No existe un fondo de caja creado para "+
						usuarioSesion.getSucursal().getJuridica().getStrRazonSocial()+"-"+usuarioSesion.getSubSucursal().getStrDescripcion());
				return;
			}
			
			if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESODECAJA)){				
//				reciboManual = ingresoFacade.getReciboManualPorSubsucursal(usuarioSesion.getSubSucursal());			
				modeloPlanillaEfectuada = obtenerModeloPlanillaEfectuada();
				if (intFormaPagoValidar.equals(Constante.PARAM_T_PAGOINGRESO_CHEQUE)) {
					mostrarPanelAdjuntoCheque = true;
				}
				
				
			}else if(intTipoDocumentoValidar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITODEBANCO)){				
				listaIngresoDepositar = ingresoFacade.obtenerListaIngresoParaDepositar(SESION_IDEMPRESA, intMonedaValidar, intFormaPagoValidar, usuarioSesion);				
				cargarNuevoDeposito(listaIngresoDepositar);				
			}			
			
			sucursalIngreso = usuarioSesion.getSucursal();
			subsucursalIngreso = usuarioSesion.getSubSucursal();
			
			dtFechaActual = obtenerFechaActual();
			datosValidados = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
			
			ocultarMensaje();
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la validación.");
			log.error(e.getMessage(),e);
		}
	}


	private void cargarNuevoDeposito(List<Ingreso> listaIngresoDepositar) throws Exception{
		
		if(listaIngresoDepositar != null && !listaIngresoDepositar.isEmpty()){
			for(Ingreso ingreso : listaIngresoDepositar){
				ingreso.setPersona(devolverPersonaCargada(ingreso.getIntPersPersonaGirado()));
			}
			
			//Ordenamos por IntItemIngreso
			Collections.sort(listaIngresoDepositar, new Comparator<Ingreso>(){
				public int compare(Ingreso uno, Ingreso otro) {
					return uno.getIntItemIngreso().compareTo(otro.getIntItemIngreso());
				}
			});
			

			Bancofondo bancoFondoTemp = new Bancofondo();
			bancoFondoTemp.getId().setIntEmpresaPk(SESION_IDEMPRESA);
			bancoFondoTemp.setIntTipoBancoFondoFiltro(Constante.PARAM_T_BANCOFONDOFIJO_BANCO);
			bancoFondoTemp.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			bancoFondoTemp.setIntMonedaCod(intMonedaValidar);					
			listaBancoFondo = bancoFacade.buscarBancoFondoParaDeposito(bancoFondoTemp, usuarioSesion);
		}
	}
	
	public void abrirPopUpBuscarPersona(){
		try{
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = "";
//			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			intTipoBuscarPersona = BUSCAR_PERSONA;
//			intOpcionBusquedaC = 1;
			socioSeleccionado = new SocioComp();
			listaSocioCuentaIngresoCaja.clear();
			listaPersonaIngresoCaja.clear();
			strMsgErrorSocioSeleccionado = "";
			
			if (intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)) {
				listaTablaOpcionBusqueda = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_OPCIONPERSONABUSQUEDA), "A");
				intOpcionBusquedaC = Constante.PARAM_T_OPCIONPERSONABUSQ_DNI;
			}else if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				listaTablaOpcionBusqueda = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_OPCIONPERSONABUSQUEDA), "B");
				intOpcionBusquedaC = Constante.PARAM_T_OPCIONPERSONABUSQ_RUC;
			}			
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void abrirPopUpBuscarEntidad(){
		try{
//			listaDetallePlanillaEfectuada.clear();
//			listaDocumentoPorAgregar.clear();
			listaEstructuraDetalle.clear();
			strFiltroTextoPersona = "";
//			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
//			intTipoBuscarPersona = BUSCAR_PERSONA;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void abrirPopUpBuscarGestor(){
		try{
			listaPersona = new ArrayList<Persona>();
			intTipoBusqDocuRazonSocial = 0;
			strFiltroTextoPersonaDocumento = "";
			strFiltroTextoPersonaNombres = "";
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			intTipoBuscarPersona = BUSCAR_GESTOR;
			gestorCobranzaTemp = null;
			listaGestorCobranza.clear();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void quitarPersonaSeleccionada(){
		try{
			limpiarFormulario();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void quitarGestorSeleccionado(){
		try{
			gestorCobranzaSeleccionado = null;
			reciboManual = null;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void quitarAdjuntoCheque(){
		try{
			archivoAdjuntoCheque = null;
			((FileUploadController)getSessionBean("fileUploadController")).setArchivoCheque(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	/*
	 * Autor: jchavez / Tarea: Creación / Fecha: 18.06.2014 / 
	 * Funcionalidad: Buscar entidades para el caso de Ingreso Caja - Juridica - Entidad Descuento
	 */
	public void buscarEntidad(){
		listaEstructuraDetalle.clear();
		Persona persona = null;
		List<EstructuraDetalle> lstEstructuraDetalle = null;
		try {
			strFiltroTextoPersona = strFiltroTextoPersona.trim();
			if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				if (intPersonaRolC.equals(Constante.PARAM_T_TIPOROL_ENTIDAD)) {
					lstEstructuraDetalle = estructuraFacade.getListaEstructuraDetalleIngresos(SESION_IDSUCURSAL,SESION_IDSUBSUCURSAL);
					if (lstEstructuraDetalle!=null && !lstEstructuraDetalle.isEmpty()) {
						for (EstructuraDetalle estructuraDetalle : lstEstructuraDetalle) {
							persona = personaFacade.getPersonaPorPK(estructuraDetalle.getEstructura().getJuridica().getIntIdPersona());
							if (persona.getStrRuc().trim().equals(strFiltroTextoPersona)) {
								estructuraDetalle.getEstructura().getJuridica().setPersona(persona);
								listaEstructuraDetalle.add(estructuraDetalle);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/*
	 * Autor: jchavez / Tarea: Creación / Fecha: 01.07.2014 / 
	 * Funcionalidad: Buscar gestor para su selección en el popup.
	 */
	public void buscarGestor(){
		listaGestorCobranza.clear();
		try {
			listaGestorCobranza = gestionCobranzaFacade.getListaGestorSucursal(SESION_IDEMPRESA,SESION_IDSUCURSAL,strFiltroTextoPersonaDocumento.trim()!=""?strFiltroTextoPersonaDocumento.trim():null,strFiltroTextoPersonaNombres.trim()!=null?strFiltroTextoPersonaNombres.trim():null, SESION_IDSUBSUCURSAL);
			if (listaGestorCobranza!=null && !listaGestorCobranza.isEmpty()) {
//				mostrarMensaje(Boolean.FALSE, "La cantidad de numeros de recibos configurados pasó el límite.");
//				habilitarGrabar = Boolean.FALSE;
//				return;
			}	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	public void buscarPersona(){
		/*
		 * PARAM_T_OPCIONPERSONABUSQ_NOMBRE = 1;
		 * PARAM_T_OPCIONPERSONABUSQ_DNI = 2;
		 * PARAM_T_OPCIONPERSONABUSQ_RUC = 3;
	 	 * PARAM_T_OPCIONPERSONABUSQ_RAZONSOCIAL = 4;
		 */
		listaSocioIngresoCaja.clear();
//		List<SocioComp> listaSocioComp = null;
		SocioComp persona = null;
		listaPersonaIngresoCaja.clear();
		strMsgErrorSocioSeleccionado = "";
		try{
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = strFiltroTextoPersona.trim();
//			Persona persona = new Persona();
			if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				if (intOpcionBusquedaC.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_NOMBRE) || intOpcionBusquedaC.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_DNI)) {
					listaSocioIngresoCaja = socioFacade.getListaSocioIngresoCaja(intOpcionBusquedaC, strFiltroTextoPersona, SESION_IDEMPRESA);
					if (listaSocioIngresoCaja!=null && !listaSocioIngresoCaja.isEmpty()) {
						persona = listaSocioIngresoCaja.get(0);
						if (persona.getIntIngCajaEstadoPersona().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)) {
							listaPersonaIngresoCaja.add(persona);
							for (SocioComp socioComp : listaSocioIngresoCaja) {
								if (!socioComp.getIntIngCajaIdPersona().equals(persona.getIntIngCajaIdPersona())) {
									listaPersonaIngresoCaja.add(socioComp);
//									persona = new SocioComp();
									persona = socioComp;
								}
							}
						}else if (persona.getIntIngCajaEstadoPersona().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO)) {
							strMsgErrorSocioSeleccionado = "La persona se encuentra fallecida.";
						}
					}
				}

//				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
//						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
//						strFiltroTextoPersona, 
//						SESION_IDEMPRESA);
			
			}else if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				if (intOpcionBusquedaC.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RAZONSOCIAL) || intOpcionBusquedaC.equals(Constante.PARAM_T_OPCIONPERSONABUSQ_RUC)) {
					listaSocioIngresoCaja = socioFacade.getListaSocioIngresoCaja(intOpcionBusquedaC, strFiltroTextoPersona, SESION_IDEMPRESA);
					if (listaSocioIngresoCaja!=null && !listaSocioIngresoCaja.isEmpty()) {
						persona = listaSocioIngresoCaja.get(0);
						if (persona.getIntIngCajaEstadoPersona().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)) {
							listaPersonaIngresoCaja.add(persona);
							for (SocioComp socioComp : listaSocioIngresoCaja) {
								if (!socioComp.getIntIngCajaIdPersona().equals(persona.getIntIngCajaIdPersona())) {
									listaPersonaIngresoCaja.add(socioComp);
//									persona = new SocioComp();
									persona = socioComp;
								}
							}
						}else if (persona.getIntIngCajaEstadoPersona().equals(Constante.PARAM_T_ESTADOUNIVERSAL_INACTIVO)) {
							strMsgErrorSocioSeleccionado = "La persona jurídica está de baja..";
						}
						
					}
				}
//				persona = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(strFiltroTextoPersona,SESION_IDEMPRESA);
			}				
			
//			if(intTipoBuscarPersona.equals(BUSCAR_PERSONA)){
//				if(persona!=null){
//					listaPersona.add(persona);
//				}				
//			}
//			else if(intTipoBuscarPersona.equals(BUSCAR_GESTOR)){
//				if(persona!=null) gestorCobranzaTemp = gestionCobranzaFacade.getGestorCobranzaPorPersona(persona, SESION_IDEMPRESA);
//				if(gestorCobranzaTemp!=null) listaPersona.add(gestorCobranzaTemp.getPersona());
//			}
			log.info("mensaje estado persona: "+strMsgErrorSocioSeleccionado);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	private void agregarNombreCompleto(Persona persona){
		persona.getNatural().setStrNombreCompleto(
				persona.getNatural().getStrNombres()+" "+
				persona.getNatural().getStrApellidoPaterno()+" "+
				persona.getNatural().getStrApellidoMaterno());
	}
	
	private void agregarDocumentoDNI(Persona persona){
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				persona.setDocumento(documento);
			}
		}
	}
	
	public void cargarListaPersonaRol(){
		log.info("-------------------------------------Debugging CajaController.getListaPersonaRol-------------------------------------");
//		Integer intTipoPersona = null;
		List<Tabla> listPersonaRol = null;
		blnEsNaturalSocio = true;
		//Autor: jchavez / Tarea: Modificación / Fecha: 22.12.2014
		blnIngresoCajaView = false;
		strMsgSubCondicionCuenta = "";
		listaSocioCuentaIngresoCaja.clear();
		listaTablaModalidadSocio.clear();		
		
		try {
			socioSeleccionado = null;
			intTipoPersonaC = Integer.valueOf(getRequestParameter("pCboTipoPersonaC"));
			
			if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				listPersonaRol = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "H");
			}else if(intTipoPersonaC.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				listPersonaRol = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "I");
//				blnEsNaturalSocio = false;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setLstPersonaRol(listPersonaRol);
	}
	
	public void cargarListaTipoDocumento(){
		log.info("-------------------------------------Debugging CajaController.getListaPersonaRol-------------------------------------");

//		Integer intPersonaRol = null;
//		Integer intTipoPersona = null;
		List<Tabla> listTipoDocumentoTemp = null;
		List<Tabla> listTipoDocumento = new ArrayList<Tabla>();
		blnEsNaturalSocio = false;
		deshabilitarTipoDocumento = false;
		blnDeshabilitarBusqSocioEntidadC = true;
		try {
			intPersonaRolC = Integer.valueOf(getRequestParameter("pCboPersonaRolC"));
			
			if(intPersonaRolC.equals(Constante.PARAM_T_TIPOROL_ENTIDAD)){
				listTipoDocumentoTemp = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODOCUMENTOINGRESO),"A");
				if (listTipoDocumentoTemp!=null && !listTipoDocumentoTemp.isEmpty()) {
					for (Tabla tipoDoc : listTipoDocumentoTemp) {
						if (tipoDoc.getIntIdDetalle().equals(Constante.PARAM_T_TIPODOCUMENTOINGRESO_PLANILLAEFECTUADA)) {
							listTipoDocumento.add(tipoDoc);
							intTipoDocumentoAgregar = tipoDoc.getIntIdDetalle();
							deshabilitarTipoDocumento = true;
							break;
						}
					}
				}
			}else if (intPersonaRolC.equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
				blnEsNaturalSocio = true;
			}
			if (intPersonaRolC!=0 && intTipoPersonaC!=0) {
				blnDeshabilitarBusqSocioEntidadC = false;
			}
			log.info("se desabilitan?? "+(intPersonaRolC==0?true:false)); 
			log.info("se desabilitan?? "+(intTipoPersonaC==0?true:false));
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListaTablaDocumentoGeneral(listTipoDocumentoTemp);
	}
	
	public void habilitarFiltroBusqueda() {
		intOpcionBusquedaC = Integer.valueOf(getRequestParameter("pCboFiltroBusqC"));
		log.info("intOpcionBusquedaC"+intOpcionBusquedaC);
	}
	public void seleccionarEntidad(ActionEvent event){
		
		try {
			entidadSeleccionada = new EstructuraComp();
			entidadSeleccionada.setEstructuraDetalle((EstructuraDetalle)event.getComponent().getAttributes().get("item"));
			//Descripcion tipo socio
			for (Tabla tipoSocio : listaTablaTipoSocio) {
				if (entidadSeleccionada.getEstructuraDetalle().getIntParaTipoSocioCod().equals(tipoSocio.getIntIdDetalle())) {
					entidadSeleccionada.setStrTipoSocioConcatenado(tipoSocio.getStrDescripcion());
					break;
				}
			}
			//Descripcion modalidad
			for (Tabla modalidad : listaTablaModalidad) {
				if (entidadSeleccionada.getEstructuraDetalle().getIntParaModalidadCod().equals(modalidad.getIntIdDetalle())) {
					entidadSeleccionada.setStrModalidadConcatenado(modalidad.getStrDescripcion());
					break;
				}
			}
			//Razon social de la entidad
			entidadSeleccionada.setStrSucursalConcatenado(entidadSeleccionada.getEstructuraDetalle().getEstructura().getJuridica().getPersona().getStrRuc()+" - "+entidadSeleccionada.getEstructuraDetalle().getEstructura().getJuridica().getStrRazonSocial());
			log.info("entidad seleccionada:"+entidadSeleccionada.getStrSucursalConcatenado()+","+entidadSeleccionada.getStrTipoSocioConcatenado()+","+entidadSeleccionada.getStrModalidadConcatenado());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
//	public void seleccionarPersona(ActionEvent event){
//		try{
//			if(intTipoBuscarPersona.equals(BUSCAR_PERSONA)){
//				personaSeleccionada = (Persona)event.getComponent().getAttributes().get("item");
//				if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
//					agregarNombreCompleto(personaSeleccionada);
//					agregarDocumentoDNI(personaSeleccionada);
//				}
//				filtrarListaTablaDocumentoGeneral();
//			
//			}else if(intTipoBuscarPersona.equals(BUSCAR_GESTOR)){
//				if(reciboManual==null){
//					mostrarMensaje(Boolean.FALSE, "No existe un recibo manual configurado para la sucursal.");
//					habilitarGrabar = Boolean.FALSE;
//					return;
//				}
//				gestorCobranzaSeleccionado = gestorCobranzaTemp;
//				agregarNombreCompleto(gestorCobranzaSeleccionado.getPersona());
//				agregarDocumentoDNI(gestorCobranzaSeleccionado.getPersona());
//				if(reciboManual.getIntNumeroActual().compareTo(reciboManual.getIntNumeroFinal())>0){
//					mostrarMensaje(Boolean.FALSE, "La cantidad de numeros de recibos configurados pasó el límite.");
//					habilitarGrabar = Boolean.FALSE;
//					return;
//				}
//			}
//		}catch(Exception e){
//			log.error(e.getMessage(), e);
//		}
//	}
	
	public void seleccionarSocio(ActionEvent event){
		listaSocioCuentaIngresoCaja.clear();
		strMsgErrorSocioSeleccionado = "";
		blnSocioSeleccionadoOk = true;
		try{
			socioSeleccionado = new SocioComp();
			socioSeleccionado = (SocioComp)event.getComponent().getAttributes().get("item");
			if (socioSeleccionado.getIntIngCajaEstadoPersona().equals(Constante.PARAM_T_ESTADOPERSONA_FALLECIDO)) {
				strMsgErrorSocioSeleccionado = "Socio se encuentra FALLECIDO, no procede operación.";
				socioSeleccionado = null;
				blnSocioSeleccionadoOk = false;
				return;
			}
			if (!socioSeleccionado.getIntIngCajaIdSituacionCta().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)) {
				strMsgErrorSocioSeleccionado = "Socio tiene cuenta en estado: "+socioSeleccionado.getStrIngCajaDescSituacionCta()+", no procede operación.";
				socioSeleccionado = null;
				blnSocioSeleccionadoOk = false;
				return;
			}
					
			if (listaSocioIngresoCaja!=null && !listaSocioIngresoCaja.isEmpty()) {
				for (SocioComp socioCuenta : listaSocioIngresoCaja) {
					if (socioCuenta.getIntIngCajaIdPersona().equals(socioSeleccionado.getIntIngCajaIdPersona()) && socioCuenta.getIntIngCajaIdSituacionCta().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)) {
						listaSocioCuentaIngresoCaja.add(socioCuenta);
					}
				}
			}
//			if(intTipoBuscarPersona.equals(BUSCAR_PERSONA)){
//				personaSeleccionada = (Persona)event.getComponent().getAttributes().get("item");
//				if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
//					agregarNombreCompleto(personaSeleccionada);
//					agregarDocumentoDNI(personaSeleccionada);
//				}
//				filtrarListaTablaDocumentoGeneral();
//			
//			}else if(intTipoBuscarPersona.equals(BUSCAR_GESTOR)){
//				if(reciboManual==null){
//					mostrarMensaje(Boolean.FALSE, "No existe un recibo manual configurado para la sucursal.");
//					habilitarGrabar = Boolean.FALSE;
//					return;
//				}
//				gestorCobranzaSeleccionado = gestorCobranzaTemp;
//				agregarNombreCompleto(gestorCobranzaSeleccionado.getPersona());
//				agregarDocumentoDNI(gestorCobranzaSeleccionado.getPersona());
//				if(reciboManual.getIntNumeroActual().compareTo(reciboManual.getIntNumeroFinal())>0){
//					mostrarMensaje(Boolean.FALSE, "La cantidad de numeros de recibos configurados pasó el límite.");
//					habilitarGrabar = Boolean.FALSE;
//					return;
//				}
//			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarModalidad(ActionEvent event){
		intModalidadC = 0;
		strMsgSubCondicionCuenta = "";
		String strDescripcion=null;
		blnValidacionesOK = true;
		//Autor: jchavez / Tarea: Creación / Fecha: 13.08.2014 / 
		SocioPK pkSocio = new SocioPK();
		SocioEstructura socioEstructura = null;
		try {
			intModalidadC = Integer.valueOf(getRequestParameter("pCboModalidadC"));
			if (intModalidadC.equals(0)) {
				strMsgSubCondicionCuenta = "Debe de seleccionar una modalidad";
				blnValidacionesOK = false;
				return;
			}else{
				/*
				 * Autor: jchavez / Tarea: Creación / Fecha: 13.08.2014 /
				 * Funcionalidad: Se agrega validacion socio estructura.  
				 */				
				pkSocio.setIntIdPersona(socioSeleccionado.getIntIngCajaIdPersona());
				pkSocio.setIntIdEmpresa(SESION_IDEMPRESA);
				socioEstructura = socioFacade.getSocioEstructuraDeOrigenPorPkSocio(pkSocio);
				Sucursal sucursal = null;
				
				if (socioEstructura!=null) {
					sucursal = empresaFacade.getSucursalPorId(socioEstructura.getIntIdSucursalAdministra());
					if (sucursal==null) {
						strMsgSubCondicionCuenta = "Ocurrio un error al obtener la Sucursal Administra del socio.";
						blnValidacionesOK = false;
						return;
					}
					
				}
						
				if (listaSocioCuentaIngresoCaja!=null && !listaSocioCuentaIngresoCaja.isEmpty()) {
					for (SocioComp cuenta : listaSocioCuentaIngresoCaja) {
						if (cuenta.getIntIngCajaIdCta().equals(intCuentaSocioC)) {
							if ((intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_ADELANTO_CANCELACION) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA))  && cuenta.getIntIngCajaIdCta().equals(intCuentaSocioC) && !cuenta.getIntIngCajaParaSubCondicionCuenta().equals(Constante.PARAM_T_TIPO_CONDSOCIO_VIGENTE)) {
								for (Tabla subCondCta : listaTablaSubCondicionCuenta) {
									if (subCondCta.getIntIdDetalle().equals(cuenta.getIntIngCajaParaSubCondicionCuenta())) {
										strDescripcion=subCondCta.getStrDescripcion();
										strMsgSubCondicionCuenta = "Socio tiene subcondicion cuenta "+strDescripcion.toUpperCase()+", no procede ingreso por esta modalidad";
										blnValidacionesOK = false;
										break;
									}							
								}
							}
							if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION) && cuenta.getIntIngCajaIdCta().equals(intCuentaSocioC) && !cuenta.getIntIngCajaParaSubCondicionCuenta().equals(Constante.PARAM_T_TIPO_CONDSOCIO_IRREGULAR)) {
								for (Tabla subCondCta : listaTablaSubCondicionCuenta) {
									if (subCondCta.getIntIdDetalle().equals(cuenta.getIntIngCajaParaSubCondicionCuenta())) {
										strDescripcion=subCondCta.getStrDescripcion();
										strMsgSubCondicionCuenta = "Socio tiene subcondicion cuenta "+strDescripcion.toUpperCase()+", no procede ingreso por esta modalidad";
										blnValidacionesOK = false;
										break;
									}							
								}
							}
						}
					}
				}
			}

			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarCuenta(ActionEvent event){
		strMsgSubCondicionCuenta = "";
		blnValidacionesOK = true;
		listaTablaModalidadSocio.clear();
		intModalidadC = 0;
		intTipoCuentaC = 0;
		try {
			intCuentaSocioC = Integer.valueOf(getRequestParameter("pCboCuentaC"));
			if (intCuentaSocioC.equals(0)) {
				strMsgSubCondicionCuenta = "Debe de seleccionar una cuenta";
				blnValidacionesOK = false;
				return;
			}
			//Autor: jchavez / Tarea: Creación / Fecha: 12.08.2014 / 
			for (SocioComp o : listaSocioCuentaIngresoCaja) {
				if (o.getIntIngCajaIdCta().compareTo(intCuentaSocioC)==0 && o.getIntIngCajaIdTipoCta().compareTo(Constante.PARAM_T_TIPOCUENTASOCIO_AHORRO)==0) {
					intTipoCuentaC = Constante.PARAM_T_TIPOCUENTASOCIO_AHORRO;
					List<Tabla> listaTablaModalidadSocioTemp =  tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOMODALIDADINGRESO));
					if (listaTablaModalidadSocioTemp!=null && !listaTablaModalidadSocioTemp.isEmpty()) {
						for (Tabla tabla : listaTablaModalidadSocioTemp) {
							if (tabla.getIntIdDetalle().compareTo(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES)==0) {
								listaTablaModalidadSocio.add(tabla);
								break;
							}
						}
					}					
				}else if (o.getIntIngCajaIdCta().compareTo(intCuentaSocioC)==0 && o.getIntIngCajaIdTipoCta().compareTo(Constante.PARAM_T_TIPOCUENTASOCIO_SOCIO)==0) {
					intTipoCuentaC = Constante.PARAM_T_TIPOCUENTASOCIO_SOCIO;
					listaTablaModalidadSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOMODALIDADINGRESO));
					break;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void seleccionarGestorIngreso(ActionEvent event){
		reciboManual = null;
//		habilitarGrabar = Boolean.TRUE;
		try {
			gestorCobranzaSeleccionado = (GestorCobranza)event.getComponent().getAttributes().get("item");
			if (gestorCobranzaSeleccionado!=null) {
				reciboManual = ingresoFacade.getReciboPorGestorYSucursal(SESION_IDEMPRESA,gestorCobranzaSeleccionado.getId().getIntPersPersonaGestorPk(),SESION_IDSUCURSAL,SESION_IDSUBSUCURSAL);
				if(reciboManual==null || reciboManual.getReciboManualDetalleUltimo().getIntNumeroRecibo()==null){
					strMensajeErrorGestor = "No existe un recibo manual configurado para la sucursal.";
//					mostrarMensaje(Boolean.FALSE, "No existe un recibo manual configurado para la sucursal.");
					gestorCobranzaSeleccionado = null;
//					habilitarGrabar = Boolean.FALSE;
					return;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}	
	
	private void filtrarListaTablaDocumentoGeneral() throws Exception{
		listaTablaDocumentoGeneral = new ArrayList<Tabla>();
		List<PersonaRol> listaPersonaRolUsar = new ArrayList<PersonaRol>();
		strListaPersonaRolUsar = "";
		List<Tabla> listaTablaRolPermitidos = new ArrayList<Tabla>();
		
		if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			listaTablaRolPermitidos = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "F");
			
		}else if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			listaTablaRolPermitidos = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "G");
		}
		
		for(PersonaRol personaRol : personaSeleccionada.getPersonaEmpresa().getListaPersonaRol()){
			for(Tabla tablaRolPermitido : listaTablaRolPermitidos){
				if(personaRol.getId().getIntParaRolPk().equals(tablaRolPermitido.getIntIdDetalle())){
					listaPersonaRolUsar.add(personaRol);
					strListaPersonaRolUsar = strListaPersonaRolUsar + "/" + tablaRolPermitido.getStrDescripcion();
					break;
				}
			}
		}
		
		//quitamos el 1er caracter de la cadena "/"
		if(!strListaPersonaRolUsar.isEmpty()) strListaPersonaRolUsar = strListaPersonaRolUsar.substring(1);		
		
		for(PersonaRol personaRolUsar : listaPersonaRolUsar){
			listaTablaDocumentoGeneral.addAll(obtenerListaTipoDocumentoGeneral(personaRolUsar.getId().getIntParaRolPk()));
		}
	}
	
	private List<Tabla> obtenerListaTipoDocumentoGeneral(Integer intTipoRol){
		List<Tabla> listaTablaTipoDocumento = new ArrayList<Tabla>();
		try{			
			if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_PERSONAL)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "P");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_SOCIO)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "S");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_USUARIO)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "U");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_ENTIDADREGULADORA)){
				listaTablaTipoDocumento = new ArrayList<Tabla>();
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_ENTIDADDESCUENTOS)){
				listaTablaTipoDocumento = new ArrayList<Tabla>();
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_UNIDADEJECUTORA)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "E");
				
			}else if(intTipoRol.equals(Constante.PARAM_T_TIPOROL_PROVEEDOR)){
				listaTablaTipoDocumento  = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "V");
				
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		return listaTablaTipoDocumento;
	}
	
	@SuppressWarnings("deprecation")
	public void abrirPopUpBuscarDocumento(){
		documentoGeneralSeleccionado = new DocumentoGeneral();
		bdMontoIngresadoTotal = null;
		bdMontoIngresadoTotalSimulacion = null;
		strMontoIngresarTotalDescripcion = "";
		listaDetallePlanillaEfectuada.clear();
		Integer pos=0;
		String strPeriodoSgte = "";
		String strPeriodoActual = "";
		Calendar cal = Calendar.getInstance();
		List<ExpedienteComp> lstExpedienteComp = new ArrayList<ExpedienteComp>();
		List<ExpedienteComp> lstExpedienteCompRegularizacion = null;
		List<ExpedienteComp> lstExpedienteCompAportaciones = null;

		listaMovimientoSocio.clear();
		bdSumatoriaMontosTotalesAPagar = BigDecimal.ZERO;
		
		strMsgErrorMontoIngresado = "";
		try{
//			socioSeleccionado.setIntIngCajaIdCta(intCuentaSocioC);
			listaDocumentoPorAgregar = new ArrayList<DocumentoGeneral>();
			log.info("intTipoDocumentoAgregar:"+intTipoDocumentoAgregar);
			
			if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_TIPODOCUMENTOINGRESO_PLANILLAEFECTUADA)){
				List<EfectuadoResumen> listaEfectuadoResumen = planillaFacadeRemote.getListaEfectuadoResumenParaIngreso2(entidadSeleccionada, usuarioSesion);
				int i = 0;
				for(EfectuadoResumen efectuadoResumen : listaEfectuadoResumen){
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setStrDescripcion(entidadSeleccionada.getEstructuraDetalle().getEstructura().getJuridica().getStrRazonSocial());
					documentoGeneral.setIntTipoDocumento(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA);
					documentoGeneral.setStrNroDocumento(""+efectuadoResumen.getId().getIntItemEfectuadoResumen());
					documentoGeneral.setBdMonto(efectuadoResumen.getBdMontoTotalAcumulado());
					documentoGeneral.setBdMontoPagado(efectuadoResumen.getBdMontIngresar());
					documentoGeneral.setIntPeriodoPlanilla(efectuadoResumen.getIntPeriodoPlanilla());
					documentoGeneral.setIntPlanillaEfectSelec(i++);
					//Se asumen que todos los efectuadoResumen estan en soles
					documentoGeneral.setIntMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
					documentoGeneral.setEfectuadoResumen(efectuadoResumen);
					log.info(efectuadoResumen);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
			}
			//Procedimiento para obtener el periodo de envio concepto (caso socio), envio planilla (caso entidad) o dia y salto de mes (caso configuracion entidad)
			String strUltPeriodo = planillaFacadeRemote.getMaxPeriodoIngCaja(SESION_IDEMPRESA,intCuentaSocioC,socioSeleccionado.getIntIngCajaIdPersona()); //socioSeleccionado.getIntIngCajaIdCta
			pos = strUltPeriodo.indexOf('-');

			if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE)) {
				
				strMsgErrorMontoIngresado = "";
				//Cálculo del periodo mes siguiente.
				if (pos.equals(-1)) {
					Integer intAnio = Integer.valueOf(strUltPeriodo.substring(0, 4));
					log.info("AÑO :"+intAnio);
					Integer intMes = Integer.valueOf(strUltPeriodo.substring(4, 6));
					log.info("AÑO :"+intMes);
					if (intMes.equals(12)) {
						strPeriodoSgte = (intAnio+1)+"01";
					}else{
						strPeriodoSgte = intAnio+""+(((intMes+1)+"").length()==1?"0"+(intMes+1):(intMes+1));
					}
				}else{
					Integer intDiaEnviado = Integer.valueOf(strUltPeriodo.substring(0, pos));
					log.info("DIA ENVIO:"+intDiaEnviado);
					Integer intSaltoEnviado = Integer.valueOf(strUltPeriodo.substring(pos+1, strUltPeriodo.length()));
					log.info("SALTO ENVIO:"+intSaltoEnviado);
					
					Integer d = cal.get(Calendar.DAY_OF_MONTH);
					Integer m = cal.get(Calendar.MONTH)+1;
					Integer y = cal.get(Calendar.YEAR);
					if (intDiaEnviado.compareTo(d)==-1) {
						if (intSaltoEnviado.equals(Constante.PARAM_T_FECHAENVIOCOBRO_ESTEMES)) {
							if (m.equals(12)) {
								strPeriodoSgte = (y+1)+"01";
							}else{
								strPeriodoSgte = y+""+(((m+1)+"").length()==1?"0"+(m+1):(m+1));
							}							
						}else if (intSaltoEnviado.equals(Constante.PARAM_T_FECHAENVIOCOBRO_MESSGTE)) {
							if (m.equals(12)) {
								strPeriodoSgte = (y+1)+"02";
							}else{
								strPeriodoSgte = y+""+(((m+2)+"").length()==1?"0"+(m+2):(m+2));
							}
						}
					}else if (intDiaEnviado.compareTo(d)==1 || intDiaEnviado.compareTo(d)==0) {
						if (intSaltoEnviado.equals(Constante.PARAM_T_FECHAENVIOCOBRO_ESTEMES)) {
							strPeriodoSgte = y+""+((m.toString().length()==1?"0"+m:m));
						}else if (intSaltoEnviado.equals(Constante.PARAM_T_FECHAENVIOCOBRO_MESSGTE)) {
							if (m.equals(12)) {
								strPeriodoSgte = (y+1)+"01";
							}else{
								strPeriodoSgte = y+""+(((m+1)+"").length()==1?"0"+(m+1):(m+1));
							}
						}
					}					
				}
				//Obtenemos los registros pago mes siguiente...
				log.info("periodo sgte: "+strPeriodoSgte);
				documentoGeneralSeleccionado.setIntPeriodoPlanilla(Integer.valueOf(strPeriodoSgte));
				strDescPeriodo = MyUtilFormatoFecha.mesesEnTexto(Integer.valueOf(strPeriodoSgte.substring(4,6)))+" del "+strPeriodoSgte.substring(0,4);
				lstExpedienteComp = conceptoFacade.getLstIngCajaPagoMesSgte(SESION_IDEMPRESA,intCuentaSocioC,Integer.valueOf(strPeriodoSgte)); //socioSeleccionado.getIntIngCajaIdCta()
				log.info(lstExpedienteComp);
				if (lstExpedienteComp!=null && !lstExpedienteComp.isEmpty()) {
					for (ExpedienteComp o : lstExpedienteComp) {
						if (o.getIntIngCajaOrdenAmortizacion()==null && o.getIntIngCajaOrdenInteres()==null) {
							strMsgErrorMontoIngresado = "Ocurrio un error al obtener la PRIORIDAD de Tipo "+o.getStrIngCajaDescTipo()+".";
							return;
						}
					}
				}				
			}else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION)) {
				Integer intDiaEnvio = estructuraFacade.getDiaEnvioPlanilla(SESION_IDEMPRESA, socioSeleccionado.getIntIngCajaIdPersona());
				log.info("Dia Enviado: "+intDiaEnvio);
				Integer m = cal.get(Calendar.MONTH)+1;
				Integer y = cal.get(Calendar.YEAR);
				//Periodo de la fecha actual
				strPeriodoActual = y+""+(((m)+"").length()==1?"0"+m:m);
				
				//Obtenemos los registros de regularizacion...
				log.info("periodo ACTUAL: "+strPeriodoActual);
				documentoGeneralSeleccionado.setIntPeriodoPlanilla(Integer.valueOf(strPeriodoActual));
				strDescPeriodo = MyUtilFormatoFecha.mesesEnTexto(Integer.valueOf(strPeriodoActual.substring(4,6)))+" del "+strPeriodoActual.substring(0,4);
				lstExpedienteCompRegularizacion = conceptoFacade.getLstIngCajaRegularizacion(SESION_IDEMPRESA,intCuentaSocioC,Integer.valueOf(strPeriodoActual)); //socioSeleccionado.getIntIngCajaIdCta()
				//Luego tendremos que ajustar la lista con todos los periodo a regularizar hasta el periodo actual.
				log.info("Lista obtenida: "+lstExpedienteCompRegularizacion);
				List<ExpedienteComp> lstCuentaConceptoDetalle = new ArrayList<ExpedienteComp>();
				List<ExpedienteComp> lstCuentaConceptoDetalleFinal = new ArrayList<ExpedienteComp>();
				if (lstExpedienteCompRegularizacion!=null && !lstExpedienteCompRegularizacion.isEmpty()) {
					for (ExpedienteComp x : lstExpedienteCompRegularizacion) {
						if (x.getStrIngCajaNroSolicitud()==null) {
							lstCuentaConceptoDetalle.add(x);
						}else{
							lstExpedienteComp.add(x);
						}
					}
					
					if (lstCuentaConceptoDetalle!=null && !lstCuentaConceptoDetalle.isEmpty()) {
						//Ordenamos la lista por cuenta concepto (id1) y cuenta concepto detalle (id2).
						Collections.sort(lstCuentaConceptoDetalle, new Comparator<ExpedienteComp>() {
				            public int compare(ExpedienteComp o1, ExpedienteComp o2) {
				            	ExpedienteComp e1 = (ExpedienteComp) o1;
				            	ExpedienteComp e2 = (ExpedienteComp) o2;
				                return e1.getIntIngCajaId2().compareTo(e2.getIntIngCajaId1());
				            }
				        });
						//recorremos la lista para armar concepto pago y concepto pago detalle
						List<String> listaPeriodoEntreFechas = null;
						List<String> listaPeriodoEntreFechasFiltradaPorDiaEnvio = new ArrayList<String>();
						
						for (ExpedienteComp x : lstCuentaConceptoDetalle) {
							listaPeriodoEntreFechasFiltradaPorDiaEnvio.clear();
							//recuperamos los datos de cuenta concepto detalle
							CuentaConceptoDetalleId pId = new CuentaConceptoDetalleId();
							pId.setIntPersEmpresaPk(x.getIntIngCajaIdEmpresa());
							pId.setIntCuentaPk(x.getIntIngCajaIdCuenta());
							pId.setIntItemCuentaConcepto(x.getIntIngCajaId1());
							pId.setIntItemCtaCptoDet(x.getIntIngCajaId2());
							CuentaConceptoDetalle ctaCptoDet = conceptoFacade.getCuentaConceptoDetallePorPK(pId);
							ctaCptoDet.setListaConceptoPago(new ArrayList<ConceptoPago>());
							//recupero los periodos entre las fechas inicio y fin de la cuenta concepto detalle
							Integer intPeriodoInicio = 0;
							Integer intPeriodoFin = 0;
							if (ctaCptoDet.getTsFin()!=null) {
								listaPeriodoEntreFechas = MyUtilFormatoFecha.obtenerPeriodosEntreFechas(ctaCptoDet.getTsInicio(), ctaCptoDet.getTsFin());
							} else if (ctaCptoDet.getTsFin()==null) {
								listaPeriodoEntreFechas = MyUtilFormatoFecha.obtenerPeriodosEntreFechas(ctaCptoDet.getTsInicio(), MyUtilFormatoFecha.obtenerFechaActual());
							}
							//filtro los periodo a usar por el dia de envio concepto ya calculado (intDiaEnvio)
							Integer intDiaInicio = ctaCptoDet.getTsInicio().getDate();
							Integer intDiaFin = ctaCptoDet.getTsFin()!=null?ctaCptoDet.getTsFin().getDate():(MyUtilFormatoFecha.obtenerFechaActual().getDate());
							//obtenemos el periodo inicial
							intPeriodoInicio = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(ctaCptoDet.getTsInicio()));
							if (intDiaInicio.compareTo(intDiaEnvio)==1) {
								intPeriodoInicio = MyUtilFormatoFecha.obtenerPeriodoSiguiente(intPeriodoInicio.toString());
							}
							//obtenemos el periodo final
							intPeriodoFin = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(ctaCptoDet.getTsFin()!=null?ctaCptoDet.getTsFin():MyUtilFormatoFecha.obtenerFechaActual()));
							if (intDiaFin.compareTo(1)>=0 && intDiaFin.compareTo(intDiaEnvio)<=0) {
								intPeriodoFin = MyUtilFormatoFecha.obtenerPeriodoAnterior(intPeriodoFin.toString());
							}
							for (String pdoFch : listaPeriodoEntreFechas) {
								Integer periodo = Integer.parseInt(pdoFch);
								if (periodo.compareTo(intPeriodoInicio)>=0 && periodo.compareTo(intPeriodoFin)<=0) {
									listaPeriodoEntreFechasFiltradaPorDiaEnvio.add(pdoFch);
								}
							}
							if (listaPeriodoEntreFechasFiltradaPorDiaEnvio!=null && !listaPeriodoEntreFechasFiltradaPorDiaEnvio.isEmpty()) {
								for (String string : listaPeriodoEntreFechasFiltradaPorDiaEnvio) {
									log.info("periodo:"+string);
								}
							}						
							//Recuperamos la lista de conceptos pago existentes por cuenta concepto detalle.
							List<ConceptoPago> lstCptoPgo = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ctaCptoDet.getId());
							
							BigDecimal bdMontoRegularizar = BigDecimal.ZERO;
							//1° CASO: validamos si EXISTE un registro en concepto pago
							Integer intUltimoPeriodoPagado = 0;
							if (lstCptoPgo!=null && !lstCptoPgo.isEmpty()) {
								List<ConceptoPago> lstPendientePago = new ArrayList<ConceptoPago>();
								for (ConceptoPago o : lstCptoPgo) {
									/*Recorremos la lista de conceptos hallados para filtrar los q tengan saldo 
									  diferente a 0, y hasta encontrar un registro cuyo saldo sea 0*/
									if (o.getBdMontoSaldo().compareTo(BigDecimal.ZERO)!=0) {
										lstPendientePago.add(o);
									}else if (o.getBdMontoSaldo().compareTo(BigDecimal.ZERO)==0) {
										intUltimoPeriodoPagado = o.getIntPeriodo();
										break;
									}
								}
								log.info("Ultimo periodo registrado en concepto pago sin saldo: "+intUltimoPeriodoPagado);
								/* Una vez hecho esto, recorremos la lista de coneptos pago filtrados, y la comparamos con la lista de periodos
								   que deberian estar registrados (listaPeriodoEntreFechas), para asi obtener los periodos que se deben regularizar*/
								
								if (lstPendientePago!=null && !lstPendientePago.isEmpty()) {
									if (listaPeriodoEntreFechasFiltradaPorDiaEnvio!=null && !listaPeriodoEntreFechasFiltradaPorDiaEnvio.isEmpty()) {
										Collections.sort(listaPeriodoEntreFechasFiltradaPorDiaEnvio, new Comparator<String>() {
								            public int compare(String o1, String o2) {
								            	String e1 = (String) o1;
								            	String e2 = (String) o2;
								                return e1.compareTo(e2);
								            }
								        });
										
										for (String periodo : listaPeriodoEntreFechasFiltradaPorDiaEnvio) {
											log.info("Periodo a comparar: "+periodo);
											Integer intPeriodo = Integer.parseInt(periodo);
											Boolean blnExisteCptoPago = false;
											if (intPeriodo.compareTo(intUltimoPeriodoPagado)==1) {
												//si ya tiene un saldo en concepto pago...
												for (ConceptoPago pndtePago : lstPendientePago) {
													if (intPeriodo.compareTo(pndtePago.getIntPeriodo())==0) {
														bdMontoRegularizar = bdMontoRegularizar.add(pndtePago.getBdMontoSaldo());
														ctaCptoDet.getListaConceptoPago().add(pndtePago);
														blnExisteCptoPago = true;
														
													}
												}
												//si no existe, se toma el monto de cuenta concepto detalle
												if (!blnExisteCptoPago) {
													ConceptoPago cP = new ConceptoPago();
													cP.setId(new ConceptoPagoId());
													cP.getId().setIntPersEmpresaPk(ctaCptoDet.getId().getIntPersEmpresaPk());
													cP.getId().setIntCuentaPk(ctaCptoDet.getId().getIntCuentaPk());
													cP.getId().setIntItemCuentaConcepto(ctaCptoDet.getId().getIntItemCuentaConcepto());
													cP.getId().setIntItemCtaCptoDet(ctaCptoDet.getId().getIntItemCtaCptoDet());
													cP.setIntPeriodo(intPeriodo);
													cP.setBdMontoPago(BigDecimal.ZERO);
													cP.setBdMontoSaldo(ctaCptoDet.getBdMontoConcepto());
													ctaCptoDet.getListaConceptoPago().add(cP);
													bdMontoRegularizar = bdMontoRegularizar.add(ctaCptoDet.getBdMontoConcepto());
												}
											}											
										}										
									}									
								}
								x.setBdIngCajaAmortizacion(bdMontoRegularizar);
								x.setIngCajaCuentaConceptoDetalle(ctaCptoDet);
								
							//2° CASO: validamos si NO EXISTE un registro en concepto pago
							} else{
								if (listaPeriodoEntreFechasFiltradaPorDiaEnvio!=null && !listaPeriodoEntreFechasFiltradaPorDiaEnvio.isEmpty()) {
									Collections.sort(listaPeriodoEntreFechasFiltradaPorDiaEnvio, new Comparator<String>() {
							            public int compare(String o1, String o2) {
							            	String e1 = (String) o1;
							            	String e2 = (String) o2;
							                return e1.compareTo(e2);
							            }
							        });
									for (String periodo : listaPeriodoEntreFechasFiltradaPorDiaEnvio) {
										log.info("Periodo a ingresar: "+periodo);
										Integer intPeriodo = Integer.parseInt(periodo);
										ConceptoPago cP = new ConceptoPago();
										cP.setId(new ConceptoPagoId());
										cP.getId().setIntPersEmpresaPk(ctaCptoDet.getId().getIntPersEmpresaPk());
										cP.getId().setIntCuentaPk(ctaCptoDet.getId().getIntCuentaPk());
										cP.getId().setIntItemCuentaConcepto(ctaCptoDet.getId().getIntItemCuentaConcepto());
										cP.getId().setIntItemCtaCptoDet(ctaCptoDet.getId().getIntItemCtaCptoDet());
										cP.setIntPeriodo(intPeriodo);
										cP.setBdMontoPago(BigDecimal.ZERO);
										cP.setBdMontoSaldo(ctaCptoDet.getBdMontoConcepto());
										ctaCptoDet.getListaConceptoPago().add(cP);
										bdMontoRegularizar = bdMontoRegularizar.add(ctaCptoDet.getBdMontoConcepto());
									}										
								}
								x.setBdIngCajaAmortizacion(bdMontoRegularizar);
								x.setIngCajaCuentaConceptoDetalle(ctaCptoDet);
							}
						}
						
						//filtramos los conceptos para q no se muestren repetidos...
						int cont = 1;
						Integer intIdCuentaConcepto = 0;
						ExpedienteComp expCompAnterior = null;
						for (ExpedienteComp x : lstCuentaConceptoDetalle) {
							if (cont==1) {	
								expCompAnterior = x;
								intIdCuentaConcepto = expCompAnterior.getIntIngCajaId1();
								expCompAnterior.setLstCajaCuentaConceptoDetalle(new ArrayList<CuentaConceptoDetalle>());
								expCompAnterior.getLstCajaCuentaConceptoDetalle().add(x.getIngCajaCuentaConceptoDetalle());
								if (cont==lstCuentaConceptoDetalle.size()) {
									lstCuentaConceptoDetalleFinal.add(expCompAnterior);
									break;
								}								
								cont++;
							} else if (cont!=1) {
								if (x.getIntIngCajaId1().compareTo(intIdCuentaConcepto)==0) {
									expCompAnterior.setLstCajaCuentaConceptoDetalle(new ArrayList<CuentaConceptoDetalle>());
									expCompAnterior.getLstCajaCuentaConceptoDetalle().add(x.getIngCajaCuentaConceptoDetalle());
									if (cont==lstCuentaConceptoDetalle.size()) {
										expCompAnterior.getLstCajaCuentaConceptoDetalle().add(expCompAnterior.getIngCajaCuentaConceptoDetalle());
										lstCuentaConceptoDetalleFinal.add(expCompAnterior);
										break;
									}	
									cont++;
								}else {
									intIdCuentaConcepto = x.getIntIngCajaId1();
									lstCuentaConceptoDetalleFinal.add(expCompAnterior);
									expCompAnterior = x;
									if (cont==lstCuentaConceptoDetalle.size()) {
										x.setLstCajaCuentaConceptoDetalle(new ArrayList<CuentaConceptoDetalle>());
										x.getLstCajaCuentaConceptoDetalle().add(x.getIngCajaCuentaConceptoDetalle());
										lstCuentaConceptoDetalleFinal.add(x);
										break;
									}
									cont++;
								}
							}
						}
					}
				}
				if (lstCuentaConceptoDetalleFinal!=null && !lstCuentaConceptoDetalleFinal.isEmpty()) {
					for (ExpedienteComp o : lstCuentaConceptoDetalleFinal) {
						BigDecimal bdMontoAmortizar = BigDecimal.ZERO;
						if (o.getLstCajaCuentaConceptoDetalle()!=null && !o.getLstCajaCuentaConceptoDetalle().isEmpty()) {
							for (CuentaConceptoDetalle lstCtaCptodet : o.getLstCajaCuentaConceptoDetalle()) {
								if (lstCtaCptodet.getListaConceptoPago()!=null && !lstCtaCptodet.getListaConceptoPago().isEmpty()) {
									for (ConceptoPago cptopgo : lstCtaCptodet.getListaConceptoPago()) {
										bdMontoAmortizar = bdMontoAmortizar.add(cptopgo.getBdMontoSaldo());
									}									
								}
							}
						}
						o.setBdIngCajaAmortizacion(bdMontoAmortizar);
						lstExpedienteComp.add(o);
					}
				}
			}else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES)) {
				//Autor: jchavez / Tarea: Creación / Fecha: 12.08.2014 / 
				lstExpedienteCompAportaciones = conceptoFacade.getLstIngCajaAportaciones(SESION_IDEMPRESA,intCuentaSocioC, intTipoCuentaC);//socioSeleccionado.getIntIngCajaIdCta()
				
				if (lstExpedienteCompAportaciones!=null && !lstExpedienteCompAportaciones.isEmpty()) {
					
					for (ExpedienteComp x : lstExpedienteCompAportaciones) {
						if (intTipoCuentaC.equals(Constante.PARAM_T_TIPOCUENTASOCIO_SOCIO)) {
							x.setLstCajaCuentaConceptoDetalle(new ArrayList<CuentaConceptoDetalle>());
							//Recuperamos los datos de la cuenta concepto detalle
							CuentaConceptoDetalle ctaCptoDet = new CuentaConceptoDetalle();
							ctaCptoDet.setId(new CuentaConceptoDetalleId());						
							ctaCptoDet.getId().setIntPersEmpresaPk(x.getIntIngCajaIdEmpresa());
							ctaCptoDet.getId().setIntCuentaPk(x.getIntIngCajaIdCuenta());
							ctaCptoDet.getId().setIntItemCuentaConcepto(x.getIntIngCajaId1());
							ctaCptoDet.getId().setIntItemCtaCptoDet(x.getIntIngCajaId2());
							ctaCptoDet = conceptoFacade.getCuentaConceptoDetallePorPK(ctaCptoDet.getId());
							x.getLstCajaCuentaConceptoDetalle().add(ctaCptoDet);

							if (x.getIntIngCajaParaTipoX().equals(Constante.CAPTACION_FDO_RETIRO)) {
								x = calcularInteresAportacion(x);
							}
						}//Autor: jchavez / Tarea: Modificación / Fecha: 09.12.2014 / Se agrega lógica de ahorros
						else if (intTipoCuentaC.equals(Constante.PARAM_T_TIPOCUENTASOCIO_AHORRO)) {
							CuentaCteAhorro ctaCteAhorro = new CuentaCteAhorro();
							//Recuperamos los datos de la cuenta corriente ahorro.
							ctaCteAhorro.getId().setIntPersEmpresaPk(x.getIntIngCajaIdEmpresa());
							ctaCteAhorro.getId().setIntCsocCuentaPk(x.getIntIngCajaIdCuenta());
							ctaCteAhorro.getId().setIntItemCuentaConcepto(x.getIntIngCajaId1());
							ctaCteAhorro.getId().setIntItemCtaCteAhorro(x.getIntIngCajaId2());
							
							ctaCteAhorro = ctaCteAhorroFacade.getCuentaCteAhorroPorPk(ctaCteAhorro);
							x.getLstCajaCuentaCteAhorro().add(ctaCteAhorro);
							x.setBdIngCajaInteres(ctaCteAhorroFacade.getCalculoInteresPasivoAhorro(ctaCteAhorro));
						}							
					}
					lstExpedienteComp.addAll(lstExpedienteCompAportaciones);
				}
			}else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_ADELANTO_CANCELACION)) {
				lstExpedienteComp = conceptoFacade.getLstIngCajaAdelantoCanc(SESION_IDEMPRESA,intCuentaSocioC); //socioSeleccionado.getIntIngCajaIdCta()
				log.info(lstExpedienteComp);
				if (lstExpedienteComp!=null && !lstExpedienteComp.isEmpty()) {
					for (ExpedienteComp o : lstExpedienteComp) {
						if (o.getIntIngCajaOrdenAmortizacion()==null && o.getIntIngCajaOrdenInteres()==null) {
							strMsgErrorMontoIngresado = "Ocurrio un error al obtener la PRIORIDAD de Tipo "+o.getStrIngCajaDescTipo()+".";
							return;
						}
					}
				}	
			}else if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA)) {
				lstExpedienteComp = conceptoFacade.getLstIngCajaPlanillaEnviada(SESION_IDEMPRESA,intCuentaSocioC); //socioSeleccionado.getIntIngCajaIdCta()
				log.info(lstExpedienteComp);
				if (lstExpedienteComp!=null && !lstExpedienteComp.isEmpty()) {
					Integer intPeriodo = lstExpedienteComp.get(0).getIntIngCajaPeriodo();
					documentoGeneralSeleccionado.setIntPeriodoPlanilla(intPeriodo);
					strDescPeriodo = MyUtilFormatoFecha.mesesEnTexto(Integer.valueOf(intPeriodo.toString().substring(4,6)))+" del "+intPeriodo.toString().substring(0,4);
					for (ExpedienteComp o : lstExpedienteComp) {
						if (o.getIntIngCajaOrdenAmortizacion()==null && o.getIntIngCajaOrdenInteres()==null) {
							strMsgErrorMontoIngresado = "Ocurrio un error al obtener la PRIORIDAD de Tipo "+o.getStrIngCajaDescTipo()+".";
							return;
						}
					}
				}else {
					strMsgErrorMontoIngresado = "No existe periodo planilla enviada.";
					return;
				}
			}
			List<ExpedienteComp> lstExpedienteComp2 = new ArrayList<ExpedienteComp>();
			if (lstExpedienteComp!=null && !lstExpedienteComp.isEmpty()) {
				for (ExpedienteComp expedienteComp : lstExpedienteComp) {
					if (expedienteComp.getIntIngCajaOrdenInteres()!=null) {
						expedienteComp.setIntIngCajaFlagAmortizacionInteres(1);
						interesCancelado = null;
						dtFechaInicioInteresCancelado = null;
						intDiasEntreFechasInteresCancelado = null;
						ExpedienteComp o = new ExpedienteComp();
						o.setIntIngCajaIdEmpresa(expedienteComp.getIntIngCajaIdEmpresa());
						o.setIntIngCajaIdCuenta(expedienteComp.getIntIngCajaIdCuenta());
						o.setIntIngCajaId1(expedienteComp.getIntIngCajaId1());			
						o.setIntIngCajaId2(expedienteComp.getIntIngCajaId2());			
						o.setIntIngCajaParaTipoX(expedienteComp.getIntIngCajaParaTipoX());	
						o.setIntIngCajaItemX(expedienteComp.getIntIngCajaItemX());		
						o.setStrIngCajaDescTipo(expedienteComp.getStrIngCajaDescTipo());
						o.setStrIngCajaDescTipoCredito(expedienteComp.getStrIngCajaDescTipoCredito());
						o.setStrIngCajaNroSolicitud(expedienteComp.getStrIngCajaNroSolicitud());
						o.setBdIngCajaMontoTotal(expedienteComp.getBdIngCajaMontoTotal());
						o.setStrIngCajaCuotas(null);
						o.setBdIngCajaSaldoCredito(null);
						o.setBdIngCajaAmortizacion(null);
						o.setBdIngCajaInteres(calcularInteresAtrasado(expedienteComp));
						o.setUltimoInteresCancelado(interesCancelado);
						o.setDtFechaInicioInteresCancelado(dtFechaInicioInteresCancelado);
						o.setIntDiasEntreFechasInteresCancelado(intDiasEntreFechasInteresCancelado);
						o.setBdIngCajaSaldoAnteriorAlPago(bdSaldoAnteriorAlPago);
						o.setBdIngCajaPorcentajeInteres(expedienteComp.getBdIngCajaPorcentajeInteres());
						o.setIntIngCajaOrdenAmortizacion(expedienteComp.getIntIngCajaOrdenInteres());
						o.setIntIngCajaOrdenInteres(null);
						o.setIntIngCajaFlagAmortizacionInteres(2);
						if (o.getBdIngCajaInteres().compareTo(BigDecimal.ZERO)!=0) {
							lstExpedienteComp2.add(o);
						}						
					}else {
						if (expedienteComp.getStrIngCajaNroSolicitud()!=null) {
							expedienteComp.setIntIngCajaFlagAmortizacionInteres(1);
						}							
					}							
				}
			}
			if (lstExpedienteComp2!=null && !lstExpedienteComp2.isEmpty()) {
				lstExpedienteComp.addAll(lstExpedienteComp2);
			}
			
			if (!intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES)) {
				//Ordenamos primero los servicios y luego los beneficios...
				Collections.sort(lstExpedienteComp, new Comparator<ExpedienteComp>() {
		            public int compare(ExpedienteComp o1, ExpedienteComp o2) {
		            	ExpedienteComp e1 = (ExpedienteComp) o1;
		            	ExpedienteComp e2 = (ExpedienteComp) o2;
		                return e1.getIntIngCajaOrdenAmortizacion().compareTo(e2.getIntIngCajaOrdenAmortizacion());
		            }
		        });
			}
			
			//Suma total ingresada
			listaMovimientoSocio.addAll(lstExpedienteComp);
			for (ExpedienteComp x : listaMovimientoSocio) {
				x.setBdIngCajaSumCapitalInteres((x.getBdIngCajaAmortizacion()!=null?x.getBdIngCajaAmortizacion():BigDecimal.ZERO).add(x.getBdIngCajaInteres()!=null?x.getBdIngCajaInteres():BigDecimal.ZERO));
				bdSumatoriaMontosTotalesAPagar = bdSumatoriaMontosTotalesAPagar.add(x.getBdIngCajaSumCapitalInteres());
			}
			
			documentoGeneralSeleccionado.setBdMonto(bdSumatoriaMontosTotalesAPagar);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void getBaseCalculo(ActionEvent event){
		bdMontoIngresadoTotal = BigDecimal.ZERO;
		try{
			for (ExpedienteComp x : listaMovimientoSocio) {
				bdMontoIngresadoTotal = bdMontoIngresadoTotal.add(x.getBdIngCajaMontoPagado()!=null?x.getBdIngCajaMontoPagado():BigDecimal.ZERO);
			}
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	public void getCalculoTotalDepositado(ActionEvent event){
		bdMontoDepositadoTotal = BigDecimal.ZERO;
		try{
			for (Ingreso x : listaIngresoDepositar) {
				bdMontoDepositadoTotal = bdMontoDepositadoTotal.add(x.getBdMontoDepositar()!=null?x.getBdMontoDepositar():BigDecimal.ZERO);
				
			}
		}catch(Exception e){
			log.error("error: " + e);
		}
	}
	
	
	public void simulacionIngresoMonto(){
		strMsgErrorMontoIngresado = "";
		bdMontoIngresadoTotalSimulacion = null;
		try {
			if (bdMontoIngresadoTotal!=null) {
				if (bdMontoIngresadoTotal.compareTo(bdSumatoriaMontosTotalesAPagar)==1) {
					bdMontoIngresadoTotal = null;
					//LIMPIAMOS LOS INGRESOS PREVIOS CALCULADOS
					for (ExpedienteComp x : listaMovimientoSocio) {
						x.setBdIngCajaMontoPagado(null);
					}
					strMsgErrorMontoIngresado = "El monto ingresado NO puede superar el monto total a pagar del mes";
					return;
				}else{
					BigDecimal bdMontoRestante = bdMontoIngresadoTotal;
					//LIMPIAMOS LOS INGRESOS PREVIOS CALCULADOS
					for (ExpedienteComp x : listaMovimientoSocio) {
						x.setBdIngCajaMontoPagado(null);
					}
					//GENERAMOS LOS NUEVOS INGRESOS
					for (ExpedienteComp x : listaMovimientoSocio) {
						if (bdMontoRestante.compareTo(BigDecimal.ZERO)==1) {
							if (x.getBdIngCajaSumCapitalInteres().compareTo(bdMontoRestante)==-1 || x.getBdIngCajaSumCapitalInteres().compareTo(bdMontoRestante)==0) {
								x.setBdIngCajaMontoPagado(x.getBdIngCajaSumCapitalInteres());
								bdMontoRestante = bdMontoRestante.subtract(x.getBdIngCajaSumCapitalInteres());
							}else if (x.getBdIngCajaSumCapitalInteres().compareTo(bdMontoRestante)==1) {
								x.setBdIngCajaMontoPagado(bdMontoRestante);
								bdMontoRestante = BigDecimal.ZERO;
							}
						}					
					}
					bdMontoIngresadoTotalSimulacion = bdMontoIngresadoTotal;
				}				
			}else{
				bdMontoIngresadoTotal = null;
				//LIMPIAMOS LOS INGRESOS PREVIOS CALCULADOS
				for (ExpedienteComp x : listaMovimientoSocio) {
					x.setBdIngCajaMontoPagado(null);
				}
				strMsgErrorMontoIngresado = "Debe ingresar un monto para realizar la simulación";
				return;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}				
	}
	
	public void agregarIngresoSocio(){
		SocioPK pkSocio = new SocioPK();
		SocioEstructura socioEstructura = null;
		listaIngresoSocio.clear();
		String strSucursal = "";
		strMsgErrorMontoIngresado = "";

		try {
			if (bdMontoIngresadoTotal==null || bdMontoIngresadoTotal.compareTo(BigDecimal.ZERO)==0) {
				strMsgErrorMontoIngresado = "El monto a ingresar debe ser mayor a 0";
				return;
			}
			//validaciones			
			if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_ADELANTO_CANCELACION) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA)) {
				if (bdMontoIngresadoTotalSimulacion==null || bdMontoIngresadoTotal.compareTo(bdMontoIngresadoTotalSimulacion)!=0) {
					strMsgErrorMontoIngresado = "Debe dar click en el botón Aplicar para distribuir el monto ingresado.";
					return;
				}
			}
			
			//agregamos las etiquetas...
			String strEtiqueta = "";

			if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA)) {
				strEtiqueta = "Periodo: "+documentoGeneralSeleccionado.getIntPeriodoPlanilla()
							+" - Monto Total: "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" "+formato.format(documentoGeneralSeleccionado.getBdMonto());
				documentoGeneralSeleccionado.setStrEtiqueta(strEtiqueta);
			}else if(intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_ADELANTO_CANCELACION)){
				strEtiqueta = "Aporte Acumulado: "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" "+formato.format(documentoGeneralSeleccionado.getBdMonto());
				documentoGeneralSeleccionado.setStrEtiqueta(strEtiqueta);
			}
							
			strMontoIngresarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoIngresadoTotal, Constante.PARAM_T_TIPOMONEDA_SOLES);
			//Obtenemos el socio estructura origen par aobtener la sucursal origen
			pkSocio.setIntIdPersona(socioSeleccionado.getIntIngCajaIdPersona());
			pkSocio.setIntIdEmpresa(SESION_IDEMPRESA);
			socioEstructura = socioFacade.getSocioEstructuraDeOrigenPorPkSocio(pkSocio);
			Sucursal sucursal = null;
			
			if (socioEstructura!=null) {
				String strDescSubSucursal = "";
				sucursal = empresaFacade.getSucursalPorId(socioEstructura.getIntIdSucursalAdministra());
				if (sucursal!=null && sucursal.getListaSubSucursal()!=null && !sucursal.getListaSubSucursal().isEmpty()) {
					for (Subsucursal subSucursal : sucursal.getListaSubSucursal()) {
						if (subSucursal.getId().getIntIdSubSucursal().equals(socioEstructura.getIntIdSubsucurAdministra())) {
							strDescSubSucursal = subSucursal.getStrDescripcion();
						}
					}
				}
				strSucursal = sucursal.getJuridica().getStrSiglas()+" - "+strDescSubSucursal;

				//jchavez 04.08.2014 Debe existir Socio Estructura ORIGEN
				//AGREGAMOS A LA LISTA SOLO LOS QUE SE HA INGRESADO MONTOS
				if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PAGOMESSGTE) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_ADELANTO_CANCELACION) || intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_PLANILLA_ENVIADA)) {
					for (ExpedienteComp x : listaMovimientoSocio) {
						if (x.getBdIngCajaMontoPagado()!=null) {
							if ((x.getBdIngCajaSumCapitalInteres().compareTo(x.getBdIngCajaMontoPagado())==0)) {
								x.setIntIngCajaParaTipoPagoCuenta(Constante.PARAM_T_TIPOPAGOCUENTA_CANCELACION);
							}else {
								x.setIntIngCajaParaTipoPagoCuenta(Constante.PARAM_T_TIPOPAGOCUENTA_ACUENTA);
							}
							x.setIntIngCajaIdSucursalAdministra(socioEstructura.getIntIdSucursalAdministra());
							x.setIntIngCajaIdSubSucursalAdministra(socioEstructura.getIntIdSubsucurAdministra());
							listaIngresoSocio.add(x);
						}
					}
					if(bdSumatoriaMontosTotalesAPagar.compareTo(bdMontoIngresadoTotalSimulacion)==0) {
						documentoGeneralSeleccionado.setIntCorrespodeCambioCondicion(Constante.CORRESPONDE_CAMBIO_CONDICION);
					}else {
						documentoGeneralSeleccionado.setIntCorrespodeCambioCondicion(Constante.NO_CORRESPONDE_CAMBIO_CONDICION);
					}
				}else if(intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_APORTACIONES)){
					if (intTipoCuentaC.equals(Constante.PARAM_T_TIPOCUENTASOCIO_SOCIO)) {
						for (ExpedienteComp x : listaMovimientoSocio) {
							if (x.getBdIngCajaMontoPagado()!=null && x.getBdIngCajaMontoPagado().compareTo(BigDecimal.ZERO)!=0) {						
								CuentaConceptoDetalle ctaCptoDet = null;
								for (CuentaConceptoDetalle o : x.getLstCajaCuentaConceptoDetalle()) {
									if (o.getTsFin()==null) {
										o.setListaConceptoPago(new ArrayList<ConceptoPago>());
										//Recuperamos los datos de la cuenta concepto detalle
										ctaCptoDet = conceptoFacade.getCuentaConceptoDetallePorPK(o.getId());
										//Recuperamos la lista de conceptos pago existentes por cuenta concepto detalle.
										List<ConceptoPago> lstCptoPgo = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ctaCptoDet.getId());
										//Obtenemos el ultimo periodo pagado
										Integer intUltimoPeriodoPagado = 0;
										if (lstCptoPgo!=null && !lstCptoPgo.isEmpty()) {
											List<ConceptoPago> lstPendientePago = new ArrayList<ConceptoPago>();
											for (ConceptoPago cp : lstCptoPgo) {
												/*Recorremos la lista de conceptos hallados para filtrar los q tengan saldo 
												  diferente a 0, y hasta encontrar un registro cuyo saldo sea 0*/
												if (cp.getBdMontoSaldo().compareTo(BigDecimal.ZERO)!=0) {
													lstPendientePago.add(cp);
												}else if (cp.getBdMontoSaldo().compareTo(BigDecimal.ZERO)==0) {
													intUltimoPeriodoPagado = cp.getIntPeriodo();
													break;
												}
											}
										}
										if (intUltimoPeriodoPagado.equals(0)) {
											intUltimoPeriodoPagado = Integer.parseInt(MyUtilFormatoFecha.convertirTimestampAStringPeriodo(MyUtilFormatoFecha.obtenerFechaActual()));
										}
										// Autor: jchavez / Tarea: Modificación / Fecha: 09.12.2014
										BigDecimal bdNroCptoPago = (x.getBdIngCajaMontoPagado().divide(ctaCptoDet.getBdMontoConcepto(), 2, RoundingMode.CEILING));
										Integer intNroCptoPagoTemp = (x.getBdIngCajaMontoPagado().divide(ctaCptoDet.getBdMontoConcepto(),0,RoundingMode.DOWN)).intValueExact();
										//Si la división da parte decimal, al numero de conceptos se le suma 1 ()
										Integer intNroCptoPago =  ((bdNroCptoPago.subtract(new BigDecimal(intNroCptoPagoTemp))).compareTo(BigDecimal.ZERO)==1)?(intNroCptoPagoTemp+1):intNroCptoPagoTemp;
										//Fin jchavez - 09.12.2014
										BigDecimal bdMto1 = x.getBdIngCajaMontoPagado();
										Integer periodo = intUltimoPeriodoPagado;
										for (int i = 0; i < (intNroCptoPago); i++) {
											List<ConceptoPago> lstCptoPgoAport = conceptoFacade.getListaConceptoPagoPorCtaCptoDetYPeriodo(ctaCptoDet.getId(), periodo, null);
											if (lstCptoPgoAport!=null && !lstCptoPgoAport.isEmpty()) {
												for (ConceptoPago conceptoPago : lstCptoPgoAport) {
													if (conceptoPago.getBdMontoSaldo().compareTo(bdMto1)==-1) {
														bdMto1 = bdMto1.subtract(conceptoPago.getBdMontoSaldo());
														conceptoPago.setBdMontoPago(conceptoPago.getBdMontoPago().add(conceptoPago.getBdMontoSaldo()));
														conceptoPago.setBdMontoSaldo(conceptoPago.getBdMontoSaldo().subtract(conceptoPago.getBdMontoSaldo()));
													}else {
														conceptoPago.setBdMontoPago(conceptoPago.getBdMontoPago().add(bdMto1));
														conceptoPago.setBdMontoSaldo(conceptoPago.getBdMontoSaldo().subtract(bdMto1));
														bdMto1 = BigDecimal.ZERO;
													}
													o.getListaConceptoPago().add(conceptoPago);
												}
											}else{
												//Registrar Concepto Pago 
												ConceptoPago cptoPgo = new ConceptoPago();
												cptoPgo.setId(new ConceptoPagoId());
												cptoPgo.getId().setIntPersEmpresaPk(o.getId().getIntPersEmpresaPk());
												cptoPgo.getId().setIntCuentaPk(o.getId().getIntCuentaPk());
												cptoPgo.getId().setIntItemCuentaConcepto(o.getId().getIntItemCuentaConcepto());
												cptoPgo.getId().setIntItemCtaCptoDet(o.getId().getIntItemCtaCptoDet());
												cptoPgo.setIntPeriodo(periodo);
												cptoPgo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
												if (o.getBdMontoConcepto().compareTo(bdMto1)==-1) {
													cptoPgo.setBdMontoPago(o.getBdMontoConcepto());
													cptoPgo.setBdMontoSaldo(BigDecimal.ZERO);
													bdMto1 = bdMto1.subtract(o.getBdMontoConcepto());
												}else {
													cptoPgo.setBdMontoPago(bdMto1);
													cptoPgo.setBdMontoSaldo(o.getBdMontoConcepto().subtract(bdMto1));
													bdMto1 = BigDecimal.ZERO;
												}
												o.getListaConceptoPago().add(cptoPgo);
											}
											periodo = MyUtilFormatoFecha.obtenerPeriodoSiguiente(periodo.toString());
										}
										break;
									}
								}					
								x.setIntIngCajaParaTipoPagoCuenta(0);
								x.setIntIngCajaIdSucursalAdministra(socioEstructura.getIntIdSucursalAdministra());
								x.setIntIngCajaIdSubSucursalAdministra(socioEstructura.getIntIdSubsucurAdministra());
								listaIngresoSocio.add(x);
							}					
						}
					}
					//Autor: jchavez / Tarea: Creación / Fecha: 10.12.2014
					if (intTipoCuentaC.equals(Constante.PARAM_T_TIPOCUENTASOCIO_AHORRO)) {
						for (ExpedienteComp x : listaMovimientoSocio) {
							if (x.getBdIngCajaMontoPagado()!=null && x.getBdIngCajaMontoPagado().compareTo(BigDecimal.ZERO)!=0) {	
								x.setIntIngCajaParaTipoPagoCuenta(0);
								x.setIntIngCajaIdSucursalAdministra(socioEstructura.getIntIdSucursalAdministra());
								x.setIntIngCajaIdSubSucursalAdministra(socioEstructura.getIntIdSubsucurAdministra());
								listaIngresoSocio.add(x);
							}
						}
					}
					//Fin jchavez - 10.12.2014
				}
				
				if (!listaIngresoSocio.isEmpty()) {
					BigDecimal bdMonto = BigDecimal.ZERO;
					Collections.sort(listaIngresoSocio, new Comparator<ExpedienteComp>() {
			            public int compare(ExpedienteComp o1, ExpedienteComp o2) {
			            	ExpedienteComp e1 = (ExpedienteComp) o1;
			            	ExpedienteComp e2 = (ExpedienteComp) o2;
			                return e2.getIntIngCajaId1().compareTo(e1.getIntIngCajaId1());
			            }
			        });
					for (ExpedienteComp y : listaIngresoSocio) {
						if (y.getStrIngCajaNroSolicitud()!=null) {
							bdMonto = bdMonto.add(y.getBdIngCajaMontoPagado());
							for (Tabla o : listaTablaTipoConceptoGeneral) {
								if (o.getIntIdDetalle().equals(y.getIntIngCajaFlagAmortizacionInteres())) {
									y.setStrIngCajaDescTipoConceptoGeneral(o.getStrDescripcion().substring(0, o.getStrDescripcion().indexOf("-")));
								}
							}
						}else y.setStrIngCajaDescTipoConceptoGeneral(y.getStrIngCajaDescTipoCredito());
							
						y.setStrIngCajaSocioSucursalAdministra(strSucursal);
					}
				}
				
				//caso Regularizacion:
				if (intModalidadC.equals(Constante.PARAM_T_TIPOMODALIDADINGRESO_REGULARIZACION)) {
					if (listaIngresoSocio!=null && !listaIngresoSocio.isEmpty()) {
						BigDecimal bdMontopagado = BigDecimal.ZERO;
						for (ExpedienteComp x : listaIngresoSocio) {
							bdMontopagado = x.getBdIngCajaMontoPagado();
							
							if (x.getLstCajaCuentaConceptoDetalle()!=null && !x.getLstCajaCuentaConceptoDetalle().isEmpty()) {
								//Recorremos la lista de ccd para setear el monto que se está regularizando de las mismas 
								for (CuentaConceptoDetalle ccd : x.getLstCajaCuentaConceptoDetalle()) {
									BigDecimal bdMontopagadoPorCCD = BigDecimal.ZERO;
									if (ccd.getListaConceptoPago()!=null && !ccd.getListaConceptoPago().isEmpty()) {
										for (ConceptoPago cp : ccd.getListaConceptoPago()) {
											if (cp.getBdMontoPago().compareTo(BigDecimal.ZERO)==0){
												if (cp.getBdMontoSaldo().compareTo(bdMontopagado)<0) {
													cp.setBdMontoPago(ccd.getBdMontoConcepto());
													cp.setBdMontoSaldo(BigDecimal.ZERO);
													bdMontopagado = bdMontopagado.subtract(cp.getBdMontoPago());
													bdMontopagadoPorCCD = bdMontopagadoPorCCD.add(cp.getBdMontoPago());
												}else if (cp.getBdMontoSaldo().compareTo(bdMontopagado)>=0) {
													cp.setBdMontoPago(bdMontopagado);
													cp.setBdMontoSaldo(cp.getBdMontoSaldo().subtract(bdMontopagado));
													bdMontopagadoPorCCD = bdMontopagadoPorCCD.add(bdMontopagado);
													bdMontopagado = BigDecimal.ZERO;
												}
											}else if (cp.getBdMontoPago().compareTo(BigDecimal.ZERO)!=0) {
												if (cp.getBdMontoSaldo().compareTo(bdMontopagado)<0) {
													bdMontopagado = bdMontopagado.subtract(cp.getBdMontoSaldo());
													bdMontopagadoPorCCD = bdMontopagadoPorCCD.add(cp.getBdMontoSaldo());
													cp.setBdMontoPago(cp.getBdMontoPago().add(cp.getBdMontoSaldo()));
													cp.setBdMontoSaldo(BigDecimal.ZERO);												
												}else if (cp.getBdMontoSaldo().compareTo(bdMontopagado)>=0) {
													cp.setBdMontoPago(cp.getBdMontoPago().add(bdMontopagado));
													cp.setBdMontoSaldo(cp.getBdMontoSaldo().subtract(bdMontopagado));
													bdMontopagadoPorCCD = bdMontopagadoPorCCD.add(bdMontopagado);
													bdMontopagado = BigDecimal.ZERO;
												}
											}
										}
										ccd.setBdIngCajaMontoRegularizarCtaCptoDet(bdMontopagadoPorCCD);
									}
								}
							}
						}
					}
				}
				blnDocumentoAgregado = true;
			}else {
				strMsgErrorMontoIngresado = "El socio no cuneta con Socio Estructura ORIGEN";
				return;
			}		
			
			log.info(listaIngresoSocio);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * jchavez 24.07.2014 
	 * Método que calcula el interes que genera una aportacion, en este caso el interes generado por el 
	 * fondo de retiro.
	 * @param expediente
	 * @return
	 */
	public ExpedienteComp calcularInteresAportacion(ExpedienteComp expediente){
		BigDecimal bdMontoInteresAportacion = BigDecimal.ZERO;
		Date dtHoy = null;
		Calendar fecHoy = Calendar.getInstance();
		Movimiento ultimoMovInteresAportacion = null;
		Integer intNumeroDias = 0;
		BigDecimal bdMontoSaldoCCD = null;
		BigDecimal bdMult01 = BigDecimal.ONE;
		BigDecimal bdMult02 = BigDecimal.ONE;
		BigDecimal bdMult03 = BigDecimal.ONE;
		BigDecimal bdNumeroDias = BigDecimal.ZERO;
		BigDecimal bdMult100 = new BigDecimal(100);
		BigDecimal bdMult30 = new BigDecimal(30);
		try {
			dtHoy = fecHoy.getTime();
			// 1. reupero el ultimo movimiento interes correspondiente al concepto.
			ultimoMovInteresAportacion = conceptoFacade.getUltimoMovFdoRetiroInteres(SESION_IDEMPRESA, expediente.getIntIngCajaIdCuenta());
			if (ultimoMovInteresAportacion!=null) {
				Date dtFechaMovimiento = MyUtilFormatoFecha.convertirTimestampToDate(ultimoMovInteresAportacion.getTsFechaMovimiento());
				// 2. Se calcula la cantidad de dias de diferencia entre fechas.
				intNumeroDias = MyUtilFormatoFecha.obtenerDiasEntreFechas(dtFechaMovimiento, dtHoy);
			}else{
				Date dtFechaApertura= new Date();
				CuentaConceptoDetalle ccd = new CuentaConceptoDetalle();
				ccd.setId(new CuentaConceptoDetalleId());
				ccd.getId().setIntPersEmpresaPk(expediente.getIntIngCajaIdEmpresa());
				ccd.getId().setIntCuentaPk(expediente.getIntIngCajaIdCuenta());
				ccd.getId().setIntItemCuentaConcepto(expediente.getIntIngCajaId1());
				ccd.getId().setIntItemCtaCptoDet(expediente.getIntIngCajaId2());
				ccd = conceptoFacade.getCuentaConceptoDetallePorPK(ccd.getId());
				dtFechaApertura = MyUtilFormatoFecha.convertirTimestampToDate(ccd.getTsInicio());
				// 2. Se calcula la cantidad de dias de diferencia entre fechas.
				intNumeroDias = MyUtilFormatoFecha.obtenerDiasEntreFechas(dtFechaApertura, dtHoy);
			}
			// 3. recupero el saldo del prestamo
			bdMontoSaldoCCD = expediente.getBdIngCajaAmortizacion();
			// 4. recupero el porcentaje de interes
			BigDecimal bdPorcentajeInteres = expediente.getBdIngCajaPorcentajeInteres()!=null?expediente.getBdIngCajaPorcentajeInteres():BigDecimal.ZERO;
			// 5. Calculo: dias * interes * saldo / 30 * 100
			if(intNumeroDias.compareTo(0)!=0){
				bdNumeroDias = new BigDecimal(intNumeroDias);
				bdMult01 = bdNumeroDias.multiply(bdPorcentajeInteres);
				bdMult02 = bdMult01.multiply(bdMontoSaldoCCD.add(ultimoMovInteresAportacion!=null?ultimoMovInteresAportacion.getBdMontoSaldo():BigDecimal.ZERO));
				bdMult03 = bdMult100.multiply(bdMult30);

				bdMontoInteresAportacion = bdMult02.divide(bdMult03, 2,RoundingMode.HALF_UP);
				bdMontoInteresAportacion = bdMontoInteresAportacion.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
			}
			expediente.setBdIngCajaInteres(ultimoMovInteresAportacion!=null?ultimoMovInteresAportacion.getBdMontoSaldo():BigDecimal.ZERO.add(bdMontoInteresAportacion));
		} catch (Exception e) {
			log.error("Error en calcularInteresAportacion ---> "+e);
		}
		return expediente;
	}
	
	public BigDecimal calcularInteresAtrasado(ExpedienteComp expediente){
		InteresCancelado ultimoInteresCancelado = null;
		Date dtHoy = null;
		Calendar fecHoy = Calendar.getInstance();
		Integer intNumeroDias = 0;
		List<EstadoCredito> lstEstados = null;
		BigDecimal bdMontoSaldoCredito = BigDecimal.ZERO;
		BigDecimal bdMult01 = BigDecimal.ONE;
		BigDecimal bdMult02 = BigDecimal.ONE;
		BigDecimal bdMult03 = BigDecimal.ONE;
		BigDecimal bdNumeroDias = BigDecimal.ZERO;
		BigDecimal bdMult100 = new BigDecimal(100);
		BigDecimal bdMult30 = new BigDecimal(30);
		BigDecimal bdMontoInteresAtrasado = BigDecimal.ZERO;
				
		try {
			dtHoy = fecHoy.getTime();

			ExpedienteId expedienteId = new ExpedienteId();
			expedienteId.setIntPersEmpresaPk(expediente.getIntIngCajaIdEmpresa());
			expedienteId.setIntCuentaPk(expediente.getIntIngCajaIdCuenta());
			expedienteId.setIntItemExpediente(expediente.getIntIngCajaId1());
			expedienteId.setIntItemExpedienteDetalle(expediente.getIntIngCajaId2());

			// 1. Recuperamos el interes (%)

			// 2. Interes Calculado - tabla. llave y max item movctacte
			ultimoInteresCancelado = conceptoFacade.getMaxInteresCanceladoPorExpediente(expedienteId);
			interesCancelado = ultimoInteresCancelado;
			// si existe ... dias = hoy - fecha de registro o mov
			if(ultimoInteresCancelado != null && ultimoInteresCancelado.getTsFechaMovimiento() != null){
				//jchavez 26.06.2014 Si el ultimo interes cancelado, su monto interes es cero, tomar como
				//fecha de movimiento la fecha de inicio
				if (ultimoInteresCancelado.getBdMontoInteres().compareTo(BigDecimal.ZERO)==0) {
					Date dtFechaMovimiento = MyUtilFormatoFecha.convertirTimestampToDate(ultimoInteresCancelado.getTsFechaInicio());
					dtFechaInicioInteresCancelado = dtFechaMovimiento;
					intNumeroDias = MyUtilFormatoFecha.obtenerDiasEntreFechas(dtFechaMovimiento, dtHoy);
					intDiasEntreFechasInteresCancelado = intNumeroDias;
				}else{
					Date dtFechaMovimiento = MyUtilFormatoFecha.convertirTimestampToDate(ultimoInteresCancelado.getTsFechaMovimiento());
					dtFechaInicioInteresCancelado = dtFechaMovimiento;
					intNumeroDias = MyUtilFormatoFecha.obtenerDiasEntreFechas(dtFechaMovimiento, dtHoy);
					intDiasEntreFechasInteresCancelado = intNumeroDias;
				}
			}else{
				Date dtFechaEstadGirado= new Date();
				// si no existe... t vas estado de credito y ver estado GIRO - 6
				ExpedienteCreditoId expCreditoId = new ExpedienteCreditoId();
				expCreditoId.setIntPersEmpresaPk(expediente.getIntIngCajaIdEmpresa());
				expCreditoId.setIntCuentaPk(expediente.getIntIngCajaIdCuenta());
				expCreditoId.setIntItemExpediente(expediente.getIntIngCajaId1());
				expCreditoId.setIntItemDetExpediente(expediente.getIntIngCajaId2());
				lstEstados  = solicitudPrestamoFacade.getListaEstadosPorExpedienteCreditoId(expCreditoId);
				if(lstEstados != null && !lstEstados.isEmpty()){
					for (EstadoCredito estadoCredito : lstEstados) {
						if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0
							&& estadoCredito.getTsFechaEstado() != null){
							dtFechaEstadGirado = MyUtilFormatoFecha.convertirTimestampToDate(estadoCredito.getTsFechaEstado());
							dtFechaInicioInteresCancelado = dtFechaEstadGirado;
							break;
						}
					}
					intNumeroDias = MyUtilFormatoFecha.obtenerDiasEntreFechas(dtFechaEstadGirado, dtHoy);
					intDiasEntreFechasInteresCancelado = intNumeroDias;
				}	
			}

			// 4. recupero el saldo del prestamo
			bdMontoSaldoCredito = expediente.getBdIngCajaSaldoCredito();
			bdSaldoAnteriorAlPago = bdMontoSaldoCredito;
			// 5. recupero el porcentaje de interes
			BigDecimal bdPorcentajeInteres = expediente.getBdIngCajaPorcentajeInteres()!=null?expediente.getBdIngCajaPorcentajeInteres():BigDecimal.ZERO;

			// 6. Calculo: dias * interes * saldo / 30 * 100
			if(intNumeroDias.compareTo(0)!=0){
				bdNumeroDias = new BigDecimal(intNumeroDias);
				bdMult01 = bdNumeroDias.multiply(bdPorcentajeInteres);
				bdMult02 = bdMult01.multiply(bdMontoSaldoCredito);
				bdMult03 = bdMult100.multiply(bdMult30);

				bdMontoInteresAtrasado = bdMult02.divide(bdMult03, 2,RoundingMode.HALF_UP);
				bdMontoInteresAtrasado = bdMontoInteresAtrasado.divide(BigDecimal.ONE, 2,RoundingMode.HALF_UP);
			}
		} catch (Exception e) {
			log.error("Error en calcularInteresAtrasado ---> "+e);
		}
		return bdMontoInteresAtrasado;
	}
	
	private String obtenerEtiquetaTipoDocumentoGeneral(Integer intTipoDocumento){
		for(Tabla tabla : listaTablaDocumentoGeneral){
			if(tabla.getIntIdDetalle().equals(intTipoDocumento)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	public void disableRegistroDocumento(ActionEvent event){
		log.info("-------------------------------------Debugging ModeloController.disableTipoRegistro-------------------------------------");
		log.info("pRdoRegistroDocumento: "+getRequestParameter("pRdoRegistroDocumento"));
		String pRegistroDocumento = getRequestParameter("pRdoRegistroDocumento");
		int i = 0;
		int pos = 0;
		for (DocumentoGeneral docAgregado : listaDocumentoPorAgregar) {
			if (docAgregado.getIntPlanillaEfectSelec().equals(new Integer(pRegistroDocumento))) {
				docAgregado.setRbPlanillaEfectSelec(i);				
				docAgregado.setIsDisabledDocPlanillaEfect(false);
				pos = i;
				break;
			}
//			else {
//				docAgregado.setRbPlanillaEfectSelec(pos);
////				planillaEfectSelec = 0;
//				docAgregado.setIsDisabledDocPlanillaEfect(true);
//			}
			i++;
		}
		//deseleccionar otros registros
		for (DocumentoGeneral docAgregado : listaDocumentoPorAgregar) {
			if (!docAgregado.getIntPlanillaEfectSelec().equals(new Integer(pRegistroDocumento))) {
				docAgregado.setRbPlanillaEfectSelec(pos);
				docAgregado.setIsDisabledDocPlanillaEfect(true);
			}
		}

//		if(Integer.valueOf(pRegistroDocumento).equals(Constante.PARAM_T_TIPOREGISTROMODELO_VALORFIJO)){
//			setIsDisabledRegistroDocumento(true);
//		}else if(Integer.valueOf(pRegistroDocumento).equals(Constante.PARAM_T_TIPOREGISTROMODELO_TABLA)){
//			setIsDisabledRegistroDocumento(false);
//		}
	}
	public void seleccionarPlanillaEfectuada(ActionEvent event){
		blnMontoIngresadoOK = true;
		try{
			documentoGeneralSeleccionado = (DocumentoGeneral)event.getComponent().getAttributes().get("item");
			if (documentoGeneralSeleccionado.getBdMontoAIngresar().compareTo(BigDecimal.ZERO)==-1
					|| documentoGeneralSeleccionado.getBdMontoAIngresar().compareTo(documentoGeneralSeleccionado.getBdMonto().subtract(documentoGeneralSeleccionado.getBdMontoPagado()))==1) {
				strMsgErrorMontoIngresado = "El monto ingresado NO es correcto. Verifique...";
				blnMontoIngresadoOK = false;
				return;
			}
			String strEtiqueta = "";
			strEtiqueta = "Periodo: "+documentoGeneralSeleccionado.getIntPeriodoPlanilla()+" - "+documentoGeneralSeleccionado.getStrDescripcion()
							+" - Monto: "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" "+formato.format(documentoGeneralSeleccionado.getBdMonto())
							+" - Monto Pagado: "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" "+formato.format(documentoGeneralSeleccionado.getBdMontoPagado());
			documentoGeneralSeleccionado.setStrEtiqueta(strEtiqueta);
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	private String obtenerEtiquetaTipoMoneda(Integer intTipoMoneda)throws Exception{
		Tabla tablaMoneda = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA), intTipoMoneda);
		if(tablaMoneda==null) return "";
		return tablaMoneda.getStrDescripcion();
	}
	
	public void seleccionarDocumento(ActionEvent event){
		try{
			documentoGeneralSeleccionado = (DocumentoGeneral)event.getComponent().getAttributes().get("item");			
			String strEtiqueta = "";
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
				strEtiqueta = obtenerEtiquetaTipoDocumentoGeneral(documentoGeneralSeleccionado.getIntTipoDocumento()) 
					+ " - "+ documentoGeneralSeleccionado.getStrNroDocumento() + " - ";			
				strEtiqueta = strEtiqueta + " disponible : "
					+ formato.format(documentoGeneralSeleccionado.getEfectuadoResumen().getBdMontoDisponibelIngresar())
					+ " "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
			}else{
				strEtiqueta = obtenerEtiquetaTipoDocumentoGeneral(documentoGeneralSeleccionado.getIntTipoDocumento()) 
					+ " - "+ documentoGeneralSeleccionado.getStrNroDocumento() + " - ";			
				strEtiqueta = strEtiqueta + formato.format(documentoGeneralSeleccionado.getBdMonto())
					+ " "+obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES);
			}
						
			documentoGeneralSeleccionado.setStrEtiqueta(strEtiqueta);
			
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	private Persona devolverPersonaCargada(Integer intIdPersona) throws Exception{
		//log.info(intIdPersona);
		Persona persona = personaFacade.getPersonaPorPK(intIdPersona);
		//Autor: jchavez / Tarea: Modificación / Fecha: 08.09.2014
		if (persona != null) {
			if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				persona = personaFacade.getPersonaNaturalPorIdPersona(persona.getIntIdPersona());			
				agregarDocumentoDNI(persona);
				agregarNombreCompleto(persona);			
			
			}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				persona.setJuridica(personaFacade.getJuridicaPorPK(persona.getIntIdPersona()));	
			}
		}		
		
		return persona;
	}


	public void agregarDocumento(){
		listaDetallePlanillaEfectuada.clear();
		listaDocumentoAgregados.clear();
		try{
			
			PlanillaFacadeRemote planillaFacade = (PlanillaFacadeRemote) EJBFactory.getRemote(PlanillaFacadeRemote.class);
			
			if(documentoGeneralSeleccionado.getBdMonto()==null){
				mostrarMensaje(Boolean.FALSE,"El documento general seleccionado no posee un monto.");
				return;
			}
//			if(bdMontoIngresar==null || bdMontoIngresar.compareTo(new BigDecimal(0))<=0){
//				mostrarMensaje(Boolean.FALSE,"Debe de ingresar un monto adecuado.");
//				return;
//			}

			if (documentoGeneralSeleccionado.getEfectuadoResumen().getListaDetallePlanillaEfectuada()!=null && !documentoGeneralSeleccionado.getEfectuadoResumen().getListaDetallePlanillaEfectuada().isEmpty()) {
				listaDetallePlanillaEfectuada.addAll(documentoGeneralSeleccionado.getEfectuadoResumen().getListaDetallePlanillaEfectuada());
			}

			bdMontoIngresadoTotal = documentoGeneralSeleccionado.getBdMontoAIngresar();
			//03.07.2014 jchavez
			Sucursal sucursal = null;
			String strDescSubSucursal = "";
			for (EfectuadoResumen efectResDescSucuSubsucu : listaDetallePlanillaEfectuada) {
				sucursal = empresaFacade.getSucursalPorId(efectResDescSucuSubsucu.getIntIdsucursaladministraPk());
				efectResDescSucuSubsucu.setJuridicaSucursal(new Juridica());
				if (sucursal.getListaSubSucursal()!=null && !sucursal.getListaSubSucursal().isEmpty()) {
					for (Subsucursal subSucursal : sucursal.getListaSubSucursal()) {
						if (subSucursal.getId().getIntIdSubSucursal().equals(efectResDescSucuSubsucu.getIntIdsubsucursaladministra())) {
							strDescSubSucursal = subSucursal.getStrDescripcion();
						}
					}
				}
				efectResDescSucuSubsucu.getJuridicaSucursal().setStrRazonSocial(sucursal.getJuridica().getStrSiglas()+" - "+strDescSubSucursal);
			}
			
			BigDecimal bdMontoIngresado = bdMontoIngresadoTotal;
			for (EfectuadoResumen detPllaEfect : listaDetallePlanillaEfectuada) {
				BigDecimal bdMontoPagado = BigDecimal.ZERO;
				List<CobroPlanillas> lstCobroPlanillas = planillaFacade.getPorEfectuadoResumen(detPllaEfect);
				if (lstCobroPlanillas!=null && !lstCobroPlanillas.isEmpty()) {
					for (CobroPlanillas cobroPlanillas : lstCobroPlanillas) {
						bdMontoPagado = bdMontoPagado.add(cobroPlanillas.getBdMontoPago());
					}
				}
				
				if ((detPllaEfect.getBdMontoTotal().subtract(bdMontoPagado)).compareTo(bdMontoIngresado)==1) {
					detPllaEfect.setBdIngCajaDetalleMontoIngresado(bdMontoIngresado);
					break;
				}else if ((detPllaEfect.getBdMontoTotal().subtract(bdMontoPagado)).compareTo(bdMontoIngresado)==-1) {
					bdMontoIngresado = bdMontoIngresado.subtract(detPllaEfect.getBdMontoTotal());
					detPllaEfect.setBdIngCajaDetalleMontoIngresado(detPllaEfect.getBdMontoTotal());
				}else if ((detPllaEfect.getBdMontoTotal().subtract(bdMontoPagado)).compareTo(bdMontoIngresado)==0) {
					detPllaEfect.setBdIngCajaDetalleMontoIngresado(bdMontoIngresado);
					break;
				}
			}
			

//			int intOrden = 1;
//			if(bdMontoIngresarTotal!=null && bdMontoIngresarTotal.signum()>0){
//				IngresoDetalleInterfaz ultimoIDI = (IngresoDetalleInterfaz)
//					(listaIngresoDetalleInterfaz.get(listaIngresoDetalleInterfaz.size()-1));
//				intOrden = ultimoIDI.getIntOrden() + 1;
//			}else{
//				bdMontoIngresarTotal = new BigDecimal(0);
//			}
			
//			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAEFECTUADA)){
//				EfectuadoResumen efectuadoResumen = documentoGeneralSeleccionado.getEfectuadoResumen();
//				//la validacion no se realiza cuando el metodo se usa para ver un ingreso ya registrado
//				if(!deshabilitarNuevo &&(bdMontoIngresar.compareTo(efectuadoResumen.getBdMontoDisponibelIngresar())>0)){
//					mostrarMensaje(Boolean.FALSE,"El monto ingresado es mayor al monto que esta disponible a ingresar.");
//					return;
//				}
//				IngresoDetalleInterfaz ingresoDetalleInterfaz = new IngresoDetalleInterfaz();
//				ingresoDetalleInterfaz.setIntDocumentoGeneral(documentoGeneralSeleccionado.getIntTipoDocumento());
//				ingresoDetalleInterfaz.setStrNroDocumento(documentoGeneralSeleccionado.getStrNroDocumento());
//				ingresoDetalleInterfaz.setPersona(personaSeleccionada);
//				ingresoDetalleInterfaz.setSucursal(usuarioSesion.getSucursal());
//				ingresoDetalleInterfaz.setSubsucursal(usuarioSesion.getSubSucursal());
//				ingresoDetalleInterfaz.setStrDescripcion(modeloPlanillaEfectuada.getListModeloDetalle().get(0).getPlanCuenta().getStrDescripcion());
//				ingresoDetalleInterfaz.setBdSubtotal(bdMontoIngresar);
//				ingresoDetalleInterfaz.setBdMonto(bdMontoIngresar);
//				ingresoDetalleInterfaz.setLibroDiario(libroDiario);
//				efectuadoResumen.setBdMontIngresar(bdMontoIngresar);
//				
//				listaIngresoDetalleInterfaz.add(ingresoDetalleInterfaz);
//			}			
			
			
//			for(Object o : listaIngresoDetalleInterfaz){
//				IngresoDetalleInterfaz ingresoDetalleInterfaz = (IngresoDetalleInterfaz)o;
//				if(ingresoDetalleInterfaz.getIntOrden()==null){
//					ingresoDetalleInterfaz.setIntOrden(intOrden);
//					bdMontoIngresarTotal = bdMontoIngresarTotal.add(ingresoDetalleInterfaz.getBdSubtotal());
//					intOrden++;
//				}
//			}
//			strMontoIngresarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoIngresarTotal, bancoFondoIngresar.getIntMonedaCod());
			//debe de funcar
			strMontoIngresarTotalDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoIngresadoTotal, Constante.PARAM_T_TIPOMONEDA_SOLES);
			listaDocumentoAgregados.add(documentoGeneralSeleccionado);
			blnDocumentoAgregado = true;
//			bdMontoIngresar = null;
			//Ordenamos por intOrden
//			Collections.sort(listaIngresoDetalleInterfaz, new Comparator<IngresoDetalleInterfaz>(){
//				public int compare(IngresoDetalleInterfaz uno, IngresoDetalleInterfaz otro) {
//					return uno.getIntOrden().compareTo(otro.getIntOrden());
//				}
//			});
//			documentoGeneralSeleccionado = null;
			ocultarMensaje();
		}catch(Exception e){
			mostrarMensaje(Boolean.FALSE,"Ocurrió un error al agregar el documento.");
			log.error(e.getMessage(), e);
		}
	}	

	public void seleccionarBanco() throws Exception{
		try{
			listaBancoCuenta = new ArrayList<Bancocuenta>();
			if(intBancoSeleccionado.equals(new Integer(0))){
				return;
			}

			String strEtiqueta = "";
			for(Bancofondo bancoFondo : listaBancoFondo){
				if(bancoFondo.getId().getIntItembancofondo().equals(intBancoSeleccionado)){
					for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
						if(intMonedaValidar.equals(bancoCuenta.getCuentaBancaria().getIntMonedaCod())){
							strEtiqueta = bancoCuenta.getStrNombrecuenta()+" - "
											+bancoCuenta.getCuentaBancaria().getStrNroCuentaBancaria()+" - "
											+obtenerEtiquetaTipoMoneda(bancoCuenta.getCuentaBancaria().getIntMonedaCod());
							bancoCuenta.setStrEtiqueta(strEtiqueta);
							listaBancoCuenta.add(bancoCuenta);
						}
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public Bancofondo obtenerBancoFondoSeleccionado() throws Exception{
		Bancofondo bancoFondoSeleccionado = null;
		log.info("intBancoSeleccionado:"+intBancoSeleccionado);
		log.info("intBancoCuentaSeleccionado:"+intBancoCuentaSeleccionado);
		for(Bancofondo bancoFondo : listaBancoFondo){
			if(intBancoSeleccionado.equals(bancoFondo.getId().getIntItembancofondo())){
				bancoFondoSeleccionado = bancoFondo;
				for(Bancocuenta bancoCuenta : bancoFondo.getListaBancocuenta()){
					if(intBancoCuentaSeleccionado.equals((bancoCuenta.getId().getIntItembancocuenta()))){
						bancoFondoSeleccionado.setBancoCuentaSeleccionada(bancoCuenta);
						break;
					}
				}
				break;
			}
		}
		
		PlanCuentaId planCuentaId = new PlanCuentaId();
		planCuentaId.setIntEmpresaCuentaPk(bancoFondoSeleccionado.getBancoCuentaSeleccionada().getIntEmpresacuentaPk());
		planCuentaId.setIntPeriodoCuenta(bancoFondoSeleccionado.getBancoCuentaSeleccionada().getIntPeriodocuenta());
		planCuentaId.setStrNumeroCuenta(bancoFondoSeleccionado.getBancoCuentaSeleccionada().getStrNumerocuenta());
		
		bancoFondoSeleccionado.getBancoCuentaSeleccionada().setPlanCuenta(planCuentaFacade.getPlanCuentaPorPk(planCuentaId));
		return bancoFondoSeleccionado;
	}
	
	public void quitarIngresoDeposito(ActionEvent event){
		try{
			Ingreso ingreso = (Ingreso)event.getComponent().getAttributes().get("item");			
			listaIngresoDepositar.remove(ingreso);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void aceptarAdjuntarVoucher(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			archivoVoucher = fileUploadController.getArchivoVoucher();
			fileUploadController.setArchivoVoucher(null);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarCartaPoder(){
		try{
			archivoVoucher = null;
			((FileUploadController)getSessionBean("fileUploadController")).setArchivoVoucher(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarAdjuntarCheque(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			archivoAdjuntoCheque = fileUploadController.getArchivoCheque();
			log.info("Nombre del Archivo recuperado: "+archivoAdjuntoCheque.getStrNombrearchivo());
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	//se inicia la imprecion de reporte de ingreso de caja
	public void imprimirIngresoCaja(){
    	String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	Tabla formPago = new Tabla();
    	Tabla tipoMoneda = new Tabla();
    	Tabla tipoComprobante = new Tabla();
    	TablaFacadeRemote tablaFacade = null;
    	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);
		PersonaFacadeRemote facadePersona = null;
		Natural usuario = new Natural();
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			formPago = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_PAGOINGRESO), 
																	ingresoGeneradoTrasGrabacion.getIntParaFormaPago());
			
			tipoMoneda = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOMONEDA_INT), 
																	ingresoGeneradoTrasGrabacion.getIntParaTipoMoneda()); 
			
			parametro.put("P_NUMINGRESO", ingresoGeneradoTrasGrabacion.getIntItemPeriodoIngreso() +" - "+ ingresoGeneradoTrasGrabacion.getIntItemIngreso());
			parametro.put("P_SUCURSAL", sucursalIngreso.getJuridica().getStrSiglas() +" - "+subsucursalIngreso.getStrDescripcion());
			parametro.put("P_FECHAINGRESO", Constante.sdf.format(ingresoGeneradoTrasGrabacion.getDtFechaIngreso()));
			parametro.put("P_FORMPAGO", formPago.getStrDescripcion());
			parametro.put("P_MONEDA", tipoMoneda.getStrDescripcion());
			parametro.put("P_TIPOCAMBIO", ingresoGeneradoTrasGrabacion.getBdTipoCambio());
			parametro.put("P_MONTO", formato.format(ingresoGeneradoTrasGrabacion.getBdMontoTotal()));
			parametro.put("P_DESCRIPCIONMONTO", getStrMontoIngresarTotalDescripcion());
//			if(ingresoGeneradoTrasGrabacion.getPersona().getIntTipoPersonaCod()==1){
//				parametro.put("P_NOMBRECOMPLETO", ingresoGeneradoTrasGrabacion.getIntPersEmpresaGirado()+"-"+
//												  ingresoGeneradoTrasGrabacion.getIntPersPersonaGirado()+" - DNI : "+ 
//												  ingresoGeneradoTrasGrabacion.getPersona().getDocumento().getStrNumeroIdentidad()+" - "+
//												  ingresoGeneradoTrasGrabacion.getPersona().getNatural().getStrApellidoPaterno()+" "+
//												  ingresoGeneradoTrasGrabacion.getPersona().getNatural().getStrApellidoMaterno()+", "+
//												  ingresoGeneradoTrasGrabacion.getPersona().getNatural().getStrNombres());
//			}else{
//				parametro.put("P_NOMBRECOMPLETO", ingresoGeneradoTrasGrabacion.getIntPersEmpresaGirado()+"-"+
//												  ingresoGeneradoTrasGrabacion.getIntPersPersonaGirado()+"-"+
//												  ingresoGeneradoTrasGrabacion.getIntCuentaGirado()+" - RUC : "+
//												  ingresoGeneradoTrasGrabacion.getPersona().getStrRuc()+" - "+
//												  ingresoGeneradoTrasGrabacion.getPersona().getJuridica().getStrRazonSocial());
//			}
			parametro.put("P_NROASIENTO", ingresoGeneradoTrasGrabacion.getStrNumeroLibro());
			parametro.put("P_PORCONCEPTODE", getStrObservacion());
			parametro.put("P_PERIODO", ingresoGeneradoTrasGrabacion.getIntPeriodoSocio());
			parametro.put("P_NUMCHEQUE", ingresoGeneradoTrasGrabacion.getStrNumeroCheque());
			parametro.put("P_MODALIDADPAGO", getStrIngCajaViewDescModalidadPago());
			
//			if(ingresoGeneradoTrasGrabacion.getPersona().getIntTipoPersonaCod()==1){
//				parametro.put("P_NOMBREFIRMA", ingresoGeneradoTrasGrabacion.getPersona().getNatural().getStrApellidoPaterno()+" "+
//						  ingresoGeneradoTrasGrabacion.getPersona().getNatural().getStrApellidoMaterno()+", "+
//						  ingresoGeneradoTrasGrabacion.getPersona().getNatural().getStrNombres());
//				parametro.put("P_DNI", "D.N.I: "+ingresoGeneradoTrasGrabacion.getPersona().getDocumento().getStrNumeroIdentidad());
//			}else{
//				parametro.put("P_NOMBREFIRMA",  ingresoGeneradoTrasGrabacion.getPersona().getJuridica().getStrRazonSocial());
//				parametro.put("P_DNI", "RUC: "+ingresoGeneradoTrasGrabacion.getPersona().getStrRuc());
//			}
			//21.12.2014 para salir del paso - jchavez
			parametro.put("P_NOMBREFIRMA",  "");
			parametro.put("P_DNI", "");
			
			usuario = facadePersona.getNaturalPorPK(ingresoGeneradoTrasGrabacion.getIntPersPersonaUsuario());
			SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
			parametro.put("P_USUARIO", usuario.getStrApellidoPaterno()+" "+usuario.getStrApellidoMaterno()+", "+usuario.getStrNombres());
			parametro.put("P_FECHAHORA", Constante.sdf.format(ingresoGeneradoTrasGrabacion.getDtFechaIngreso()) +" "+formateador.format(ingresoGeneradoTrasGrabacion.getDtFechaIngreso()));
			Date fecha = new Date();
			parametro.put("P_SYSDATE", Constante.sdf.format(fecha));
			parametro.put("P_HORADIA", formateador.format(fecha));
			
			List<IngresoDetalle> listIngreso = new ArrayList<IngresoDetalle>();
			Sucursal sucursal = new Sucursal();
			for (IngresoDetalle ingresoReporte : ingresoGeneradoTrasGrabacion.getListaIngresoDetalle()) {
				IngresoDetalle ingresoTemp = new IngresoDetalle();
				sucursal.getId().setIntPersEmpresaPk(ingresoGeneradoTrasGrabacion.getId().getIntIdEmpresa());
				sucursal.getId().setIntIdSucursal(ingresoGeneradoTrasGrabacion.getIntSucuIdSucursal());
				EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
				sucursal = empresaFacade.getSucursalPorPK(sucursal);
				Subsucursal subsucursal = empresaFacade.getSubSucursalPorIdSubSucursal(ingresoGeneradoTrasGrabacion.getIntSudeIdSubsucursal());
				
				tipoComprobante = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOCOMPROBANTE), 
						ingresoReporte.getIntParaTipoComprobante());
				if(tipoComprobante!=null){
					ingresoTemp.setStrDesTipoComprobante(tipoComprobante.getStrDescripcion());
				}else{
					ingresoTemp.setStrDesTipoComprobante("");
				}
				ingresoTemp.setStrDescripcionAgencia(sucursal.getJuridica().getStrRazonSocial() +"-"+subsucursal.getStrDescripcion());
				ingresoTemp.setStrDescripcionIngreso(ingresoReporte.getStrDescripcionIngreso());
				if(ingresoReporte.getBdMontoAbono()!=null){
					ingresoTemp.setStrMontoCargoReport(formato.format(ingresoReporte.getBdMontoAbono()));
				}
				if(ingresoReporte.getBdMontoCargo()!=null){
					ingresoTemp.setStrMontoCargoReport("-"+formato.format(ingresoReporte.getBdMontoCargo()));
				}
				ingresoTemp.setStrNumeroDocumento(ingresoReporte.getStrNumeroDocumento());
				listIngreso.add(ingresoTemp);
			}
			System.out.println("Parametro " +parametro);
			strNombreReporte = "ingresoCaja";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(listIngreso), Constante.PARAM_T_TIPOREPORTE_PDF);
			
		} catch (Exception e) {
			log.error("Error en imprimirPlanillaDescuento ---> "+e);
		}
    }
	
	//Se inicia la imprecion del deposito de banco
	public void imprimirDepositoBanco(){
		String strNombreReporte = "";
    	HashMap<String,Object> parametro = new HashMap<String,Object>();
    	SimpleDateFormat formateador = new SimpleDateFormat("hh:mm:ss");
    	Date fecha = new Date();
    	DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		NumberFormat formato = new DecimalFormat("#,###.00",otherSymbols);
		TablaFacadeRemote tablaFacade = null;
		Tabla formaPago = new Tabla();
		Tabla tipoMoneda = new Tabla();
		Tabla tipoPersona = new Tabla();
		Tabla tipoMonList = new Tabla();
		Natural usuario = new Natural();
		PersonaFacadeRemote facadePersona = null;
		BancoFacadeLocal facadeBancoCuenta = null;
		BancocuentaId cuentapk = null;
		Bancocuenta banCuenta = null ;
		PersonaFacadeRemote personaFacade = null;
		Juridica juri =null;
		CuentaBancaria cuentaBancaria = null;
		CuentaBancariaPK cueBancariaPk = null;
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			facadePersona = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			facadeBancoCuenta = (BancoFacadeLocal)EJBFactory.getLocal(BancoFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			
			formaPago = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_PAGOINGRESO), 
																	getIntFormaPagoValidar());
			
			tipoMoneda = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOMONEDA_INT), 
																	depositoGeneradoTrasGrabacion.getIntParaTipoMoneda()); 
			
			parametro.put("P_NUMEROINGRESO", depositoGeneradoTrasGrabacion.getIntItemPeriodoIngreso()+"-"+depositoGeneradoTrasGrabacion.getIntItemIngreso());
			parametro.put("P_SUCURSAL", sucursalIngreso.getJuridica().getStrSiglas() +" - "+subsucursalIngreso.getStrDescripcion());
			parametro.put("P_FECHAINGRESO", Constante.sdf.format(depositoGeneradoTrasGrabacion.getDtFechaIngreso()));
			parametro.put("P_SYSDATE", Constante.sdf.format(fecha));
			parametro.put("P_HORADIA", formateador.format(fecha));
			parametro.put("P_FORMPAGO", formaPago.getStrDescripcion());
			parametro.put("P_MONEDA", tipoMoneda.getStrDescripcion());
			parametro.put("P_TIPOCAMBIO", depositoGeneradoTrasGrabacion.getBdTipoCambio());
			parametro.put("P_MONTO", formato.format(getBdMontoDepositarTotal()));
			parametro.put("P_DESCRIPCIONMONTO", getStrMontoDepositarTotalDescripcion());
			parametro.put("P_ASIENTO", depositoGeneradoTrasGrabacion.getIntContPeriodoLibro()+"-"+depositoGeneradoTrasGrabacion.getIntContCodigoLibro());
			parametro.put("P_NUMOPERACION", depositoGeneradoTrasGrabacion.getStrNumeroOperacion());
			parametro.put("P_PORCOMCEPTO", depositoGeneradoTrasGrabacion.getStrObservacion());
			if(getBdMontoAjuste()!=null){
				parametro.put("P_REDONDEO", formato.format(getBdMontoAjuste()));
			}
			parametro.put("P_DESCRIPCIONREDONDEO", getStrMontoAjusteDescripcion());
			usuario = facadePersona.getNaturalPorPK(depositoGeneradoTrasGrabacion.getIntPersPersonaUsuario());
			parametro.put("P_USUARIO", usuario.getStrApellidoPaterno()+" "+usuario.getStrApellidoMaterno()+", "+usuario.getStrNombres());
			parametro.put("P_FECHAHORA", Constante.sdf.format(depositoGeneradoTrasGrabacion.getDtFechaIngreso()) +" "+formateador.format(depositoGeneradoTrasGrabacion.getDtFechaIngreso()));
			parametro.put("P_TOTALDEPOSITO", getBdMontoDepositadoTotal());
			
			banCuenta =new Bancocuenta();
			cuentapk = new BancocuentaId();
			cuentapk.setIntEmpresaPk(depositoGeneradoTrasGrabacion.getId().getIntIdEmpresa());
			cuentapk.setIntItembancofondo(depositoGeneradoTrasGrabacion.getIntItemBancoFondo());
			cuentapk.setIntItembancocuenta(depositoGeneradoTrasGrabacion.getIntItemBancoCuenta());
			
			banCuenta = facadeBancoCuenta.getBancoCuentaPorPk(cuentapk);
			juri = new Juridica();
			juri = personaFacade.getJuridicaPorPK(banCuenta.getIntPersona());
			if(juri!=null){
				parametro.put("P_BANCO", juri.getStrRazonSocial());
			}
			cuentaBancaria = new CuentaBancaria();
			cueBancariaPk = new CuentaBancariaPK();
			
			cueBancariaPk.setIntIdPersona(banCuenta.getId().getIntEmpresaPk());
			cueBancariaPk.setIntIdCuentaBancaria(banCuenta.getIntCuentabancaria());
			
			cuentaBancaria = personaFacade.getCuentaBancariaPorPK(cueBancariaPk);
			
			if(cuentaBancaria!=null){
				parametro.put("P_CTACTE", cuentaBancaria.getStrNroCuentaBancaria());
			}
			
			List<Ingreso> listDeposito = new ArrayList<Ingreso>();
			for (Ingreso ingreso : listaIngresoDepositar) {
				Ingreso depo = new Ingreso();
				depo.setStrFechaIngreso(Constante.sdf.format(ingreso.getDtFechaIngreso()));
				tipoPersona = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOPERSONA), 
																		 ingreso.getPersona().getIntTipoPersonaCod());
				depo.setStrDescripcionPersona(tipoPersona.getStrDescripcion());
				if(ingreso.getPersona().getIntTipoPersonaCod()==1){
					depo.setStrNombrePersona(ingreso.getPersona().getNatural().getStrNombreCompleto());
				}else if(ingreso.getPersona().getIntTipoPersonaCod()==2){
					depo.setStrNombrePersona(ingreso.getPersona().getJuridica().getStrRazonSocial());
				}
				tipoMonList = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOMONEDA), 
																		 ingreso.getIntParaTipoMoneda());
				depo.setStrDescripcionMoneda(tipoMonList.getStrDescripcion());
				depo.setBdMontoDepositable(ingreso.getBdMontoDepositable());
				depo.setStrMontoDepositar(formato.format(ingreso.getBdMontoDepositar()));
				depo.setStrNumeroIngreso(ingreso.getStrNumeroIngreso());
				listDeposito.add(depo);
			}
			strNombreReporte = "depositoBanco";
			UtilManagerReport.generateReport(strNombreReporte, parametro, new ArrayList<Object>(listDeposito), Constante.PARAM_T_TIPOREPORTE_PDF);
			
		} catch (Exception e) {
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
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
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}
	public boolean isMostrarPanelInferior() {
		return mostrarPanelInferior;
	}
	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public boolean isRegistrarNuevo() {
		return registrarNuevo;
	}
	public void setRegistrarNuevo(boolean registrarNuevo) {
		this.registrarNuevo = registrarNuevo;
	}
	public boolean isHabilitarGrabar() {
		return habilitarGrabar;
	}
	public void setHabilitarGrabar(boolean habilitarGrabar) {
		this.habilitarGrabar = habilitarGrabar;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
	public Sucursal getSucursalUsuario() {
		return sucursalUsuario;
	}
	public void setSucursalUsuario(Sucursal sucursalUsuario) {
		this.sucursalUsuario = sucursalUsuario;
	}
	public Subsucursal getSubsucursalUsuario() {
		return subsucursalUsuario;
	}
	public void setSubsucursalUsuario(Subsucursal subsucursalUsuario) {
		this.subsucursalUsuario = subsucursalUsuario;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public String getStrFiltroTextoPersona() {
		return strFiltroTextoPersona;
	}
	public void setStrFiltroTextoPersona(String strFiltroTextoPersona) {
		this.strFiltroTextoPersona = strFiltroTextoPersona;
	}
	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}
	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}
	public String getStrMontoIngresarTotalDescripcion() {
		return strMontoIngresarTotalDescripcion;
	}
	public void setStrMontoIngresarTotalDescripcion(String strMontoIngresarTotalDescripcion) {
		this.strMontoIngresarTotalDescripcion = strMontoIngresarTotalDescripcion;
	}
	public boolean isPermiso() {
		return permiso;
	}
	public void setPermiso(boolean permiso) {
		this.permiso = permiso;
	}
	public Integer getIntTipoBuscarPersona() {
		return intTipoBuscarPersona;
	}
	public void setIntTipoBuscarPersona(Integer intTipoBuscarPersona) {
		this.intTipoBuscarPersona = intTipoBuscarPersona;
	}
	public Integer getIntTipoDocumentoValidar() {
		return intTipoDocumentoValidar;
	}
	public void setIntTipoDocumentoValidar(Integer intTipoDocumentoValidar) {
		this.intTipoDocumentoValidar = intTipoDocumentoValidar;
	}
	public Integer getIntFormaPagoValidar() {
		return intFormaPagoValidar;
	}
	public void setIntFormaPagoValidar(Integer intFormaPagoValidar) {
		this.intFormaPagoValidar = intFormaPagoValidar;
	}
	public List<Ingreso> getListaIngreso() {
		return listaIngreso;
	}
	public void setListaIngreso(List<Ingreso> listaIngreso) {
		this.listaIngreso = listaIngreso;
	}
	public Date getDtFechaActual() {
		return dtFechaActual;
	}
	public void setDtFechaActual(Date dtFechaActual) {
		this.dtFechaActual = dtFechaActual;
	}
	public String getStrListaPersonaRolUsar() {
		return strListaPersonaRolUsar;
	}
	public void setStrListaPersonaRolUsar(String strListaPersonaRolUsar) {
		this.strListaPersonaRolUsar = strListaPersonaRolUsar;
	}
	public Integer getIntTipoDocumentoAgregar() {
		return intTipoDocumentoAgregar;
	}
	public void setIntTipoDocumentoAgregar(Integer intTipoDocumentoAgregar) {
		this.intTipoDocumentoAgregar = intTipoDocumentoAgregar;
	}
	public DocumentoGeneral getDocumentoGeneralSeleccionado() {
		return documentoGeneralSeleccionado;
	}
	public void setDocumentoGeneralSeleccionado(DocumentoGeneral documentoGeneralSeleccionado) {
		this.documentoGeneralSeleccionado = documentoGeneralSeleccionado;
	}
	public GestorCobranza getGestorCobranzaSeleccionado() {
		return gestorCobranzaSeleccionado;
	}
	public void setGestorCobranzaSeleccionado(GestorCobranza gestorCobranzaSeleccionado) {
		this.gestorCobranzaSeleccionado = gestorCobranzaSeleccionado;
	}
	public BigDecimal getBdMontoIngresar() {
		return bdMontoIngresar;
	}
	public void setBdMontoIngresar(BigDecimal bdMontoIngresar) {
		this.bdMontoIngresar = bdMontoIngresar;
	}
	public BigDecimal getBdMontoIngresarTotal() {
		return bdMontoIngresarTotal;
	}
	public void setBdMontoIngresarTotal(BigDecimal bdMontoIngresarTotal) {
		this.bdMontoIngresarTotal = bdMontoIngresarTotal;
	}
	public ReciboManual getReciboManual() {
		return reciboManual;
	}
	public void setReciboManual(ReciboManual reciboManual) {
		this.reciboManual = reciboManual;
	}
	public List<Tabla> getListaTablaDocumentoGeneral() {
		return listaTablaDocumentoGeneral;
	}
	public void setListaTablaDocumentoGeneral(List<Tabla> listaTablaDocumentoGeneral) {
		this.listaTablaDocumentoGeneral = listaTablaDocumentoGeneral;
	}
	public List<IngresoDetalleInterfaz> getListaIngresoDetalleInterfaz() {
		return listaIngresoDetalleInterfaz;
	}
	public void setListaIngresoDetalleInterfaz(List<IngresoDetalleInterfaz> listaIngresoDetalleInterfaz) {
		this.listaIngresoDetalleInterfaz = listaIngresoDetalleInterfaz;
	}
	public List<DocumentoGeneral> getListaDocumentoPorAgregar() {
		return listaDocumentoPorAgregar;
	}
	public void setListaDocumentoPorAgregar(List<DocumentoGeneral> listaDocumentoPorAgregar) {
		this.listaDocumentoPorAgregar = listaDocumentoPorAgregar;
	}
	public Integer getIntMonedaValidar() {
		return intMonedaValidar;
	}
	public void setIntMonedaValidar(Integer intMonedaValidar) {
		this.intMonedaValidar = intMonedaValidar;
	}
	public Bancofondo getBancoFondoIngresar() {
		return bancoFondoIngresar;
	}
	public void setBancoFondoIngresar(Bancofondo bancoFondoIngresar) {
		this.bancoFondoIngresar = bancoFondoIngresar;
	}
	public String getStrNumeroCheque() {
		return strNumeroCheque;
	}
	public void setStrNumeroCheque(String strNumeroCheque) {
		this.strNumeroCheque = strNumeroCheque;
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
	public Ingreso getIngresoFiltro() {
		return ingresoFiltro;
	}
	public void setIngresoFiltro(Ingreso ingresoFiltro) {
		this.ingresoFiltro = ingresoFiltro;
	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public Archivo getArchivoVoucher() {
		return archivoVoucher;
	}
	public void setArchivoVoucher(Archivo archivoVoucher) {
		this.archivoVoucher = archivoVoucher;
	}
	public Integer getIntBancoSeleccionado() {
		return intBancoSeleccionado;
	}
	public void setIntBancoSeleccionado(Integer intBancoSeleccionado) {
		this.intBancoSeleccionado = intBancoSeleccionado;
	}
	public List<Bancocuenta> getListaBancoCuenta() {
		return listaBancoCuenta;
	}
	public void setListaBancoCuenta(List<Bancocuenta> listaBancoCuenta) {
		this.listaBancoCuenta = listaBancoCuenta;
	}
	public Integer getIntBancoCuentaSeleccionado() {
		return intBancoCuentaSeleccionado;
	}
	public void setIntBancoCuentaSeleccionado(Integer intBancoCuentaSeleccionado) {
		this.intBancoCuentaSeleccionado = intBancoCuentaSeleccionado;
	}
	public List<Ingreso> getListaIngresoDepositar() {
		return listaIngresoDepositar;
	}
	public void setListaIngresoDepositar(List<Ingreso> listaIngresoDepositar) {
		this.listaIngresoDepositar = listaIngresoDepositar;
	}
	public BigDecimal getBdMontoDepositarTotal() {
		return bdMontoDepositarTotal;
	}
	public void setBdMontoDepositarTotal(BigDecimal bdMontoDepositarTotal) {
		this.bdMontoDepositarTotal = bdMontoDepositarTotal;
	}
	public String getStrMontoDepositarTotalDescripcion() {
		return strMontoDepositarTotalDescripcion;
	}
	public void setStrMontoDepositarTotalDescripcion(String strMontoDepositarTotalDescripcion) {
		this.strMontoDepositarTotalDescripcion = strMontoDepositarTotalDescripcion;
	}
	public BigDecimal getBdMontoAjuste() {
		return bdMontoAjuste;
	}
	public void setBdMontoAjuste(BigDecimal bdMontoAjuste) {
		this.bdMontoAjuste = bdMontoAjuste;
	}
	public String getStrMontoAjusteDescripcion() {
		return strMontoAjusteDescripcion;
	}
	public void setStrMontoAjusteDescripcion(String strMontoAjusteDescripcion) {
		this.strMontoAjusteDescripcion = strMontoAjusteDescripcion;
	}
	public BigDecimal getBdOtrosIngresos() {
		return bdOtrosIngresos;
	}
	public void setBdOtrosIngresos(BigDecimal bdOtrosIngresos) {
		this.bdOtrosIngresos = bdOtrosIngresos;
	}
	public String getStrNumeroOperacion() {
		return strNumeroOperacion;
	}
	public void setStrNumeroOperacion(String strNumeroOperacion) {
		this.strNumeroOperacion = strNumeroOperacion;
	}	
	public Sucursal getSucursalIngreso() {
		return sucursalIngreso;
	}
	public void setSucursalIngreso(Sucursal sucursalIngreso) {
		this.sucursalIngreso = sucursalIngreso;
	}
	public Subsucursal getSubsucursalIngreso() {
		return subsucursalIngreso;
	}
	public void setSubsucursalIngreso(Subsucursal subsucursalIngreso) {
		this.subsucursalIngreso = subsucursalIngreso;
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
	public List<Bancofondo> getListaBancoFondo() {
		return listaBancoFondo;
	}
	public void setListaBancoFondo(List<Bancofondo> listaBancoFondo) {
		this.listaBancoFondo = listaBancoFondo;
	}
	public String getStrMontoSaldoCaja() {
		return strMontoSaldoCaja;
	}
	public void setStrMontoSaldoCaja(String strMontoSaldoCaja) {
		this.strMontoSaldoCaja = strMontoSaldoCaja;
	}
	public List<Tabla> getLstPersonaRol() {
		return lstPersonaRol;
	}
	public void setLstPersonaRol(List<Tabla> lstPersonaRol) {
		this.lstPersonaRol = lstPersonaRol;
	}
	public Integer getIntTipoPersonaC() {
		return intTipoPersonaC;
	}
	public void setIntTipoPersonaC(Integer intTipoPersonaC) {
		this.intTipoPersonaC = intTipoPersonaC;
	}
	public Integer getIntPersonaRolC() {
		return intPersonaRolC;
	}
	public void setIntPersonaRolC(Integer intPersonaRolC) {
		this.intPersonaRolC = intPersonaRolC;
	}
	public Boolean getBlnEsNaturalSocio() {
		return blnEsNaturalSocio;
	}
	public void setBlnEsNaturalSocio(Boolean blnEsNaturalSocio) {
		this.blnEsNaturalSocio = blnEsNaturalSocio;
	}
	public Boolean getDeshabilitarTipoDocumento() {
		return deshabilitarTipoDocumento;
	}
	public void setDeshabilitarTipoDocumento(Boolean deshabilitarTipoDocumento) {
		this.deshabilitarTipoDocumento = deshabilitarTipoDocumento;
	}
	public List<Tabla> getListaTablaModalidad() {
		return listaTablaModalidad;
	}
	public void setListaTablaModalidad(List<Tabla> listaTablaModalidad) {
		this.listaTablaModalidad = listaTablaModalidad;
	}
	public List<Tabla> getListaTablaTipoSocio() {
		return listaTablaTipoSocio;
	}
	public void setListaTablaTipoSocio(List<Tabla> listaTablaTipoSocio) {
		this.listaTablaTipoSocio = listaTablaTipoSocio;
	}
	public List<EstructuraDetalle> getListaEstructuraDetalle() {
		return listaEstructuraDetalle;
	}
	public void setListaEstructuraDetalle(
			List<EstructuraDetalle> listaEstructuraDetalle) {
		this.listaEstructuraDetalle = listaEstructuraDetalle;
	}
	public EstructuraComp getEntidadSeleccionada() {
		return entidadSeleccionada;
	}
	public void setEntidadSeleccionada(EstructuraComp entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}
	public Boolean getIsDisabledRegistroDocumento() {
		return isDisabledRegistroDocumento;
	}
	public void setIsDisabledRegistroDocumento(Boolean isDisabledRegistroDocumento) {
		this.isDisabledRegistroDocumento = isDisabledRegistroDocumento;
	}
	public Integer getPlanillaEfectSelec() {
		return planillaEfectSelec;
	}
	public void setPlanillaEfectSelec(Integer planillaEfectSelec) {
		this.planillaEfectSelec = planillaEfectSelec;
	}
	public List<Persona> getListaGestorIngreso() {
		return listaGestorIngreso;
	}
	public void setListaGestorIngreso(List<Persona> listaGestorIngreso) {
		this.listaGestorIngreso = listaGestorIngreso;
	}
	public List<GestorCobranza> getListaGestorCobranza() {
		return listaGestorCobranza;
	}
	public void setListaGestorCobranza(List<GestorCobranza> listaGestorCobranza) {
		this.listaGestorCobranza = listaGestorCobranza;
	}
	public String getStrFiltroTextoPersonaDocumento() {
		return strFiltroTextoPersonaDocumento;
	}
	public void setStrFiltroTextoPersonaDocumento(
			String strFiltroTextoPersonaDocumento) {
		this.strFiltroTextoPersonaDocumento = strFiltroTextoPersonaDocumento;
	}
	public String getStrFiltroTextoPersonaNombres() {
		return strFiltroTextoPersonaNombres;
	}
	public void setStrFiltroTextoPersonaNombres(String strFiltroTextoPersonaNombres) {
		this.strFiltroTextoPersonaNombres = strFiltroTextoPersonaNombres;
	}
	public List<Tabla> getListaTablaTipoBusqueda() {
		return listaTablaTipoBusqueda;
	}
	public void setListaTablaTipoBusqueda(List<Tabla> listaTablaTipoBusqueda) {
		this.listaTablaTipoBusqueda = listaTablaTipoBusqueda;
	}
	public Integer getIntTipoBusqDocuRazonSocial() {
		return intTipoBusqDocuRazonSocial;
	}
	public void setIntTipoBusqDocuRazonSocial(Integer intTipoBusqDocuRazonSocial) {
		this.intTipoBusqDocuRazonSocial = intTipoBusqDocuRazonSocial;
	}
	public BigDecimal getBdMontoIngresadoTotal() {
		return bdMontoIngresadoTotal;
	}
	public void setBdMontoIngresadoTotal(BigDecimal bdMontoIngresadoTotal) {
		this.bdMontoIngresadoTotal = bdMontoIngresadoTotal;
	}
	public List<EfectuadoResumen> getListaDetallePlanillaEfectuada() {
		return listaDetallePlanillaEfectuada;
	}
	public void setListaDetallePlanillaEfectuada(
			List<EfectuadoResumen> listaDetallePlanillaEfectuada) {
		this.listaDetallePlanillaEfectuada = listaDetallePlanillaEfectuada;
	}
	public Boolean getBlnMontoIngresadoOK() {
		return blnMontoIngresadoOK;
	}
	public void setBlnMontoIngresadoOK(Boolean blnMontoIngresadoOK) {
		this.blnMontoIngresadoOK = blnMontoIngresadoOK;
	}
	public String getStrMsgErrorMontoIngresado() {
		return strMsgErrorMontoIngresado;
	}
	public void setStrMsgErrorMontoIngresado(String strMsgErrorMontoIngresado) {
		this.strMsgErrorMontoIngresado = strMsgErrorMontoIngresado;
	}
	public Archivo getArchivoAdjuntoCheque() {
		return archivoAdjuntoCheque;
	}
	public void setArchivoAdjuntoCheque(Archivo archivoAdjuntoCheque) {
		this.archivoAdjuntoCheque = archivoAdjuntoCheque;
	}
	public boolean isMostrarPanelAdjuntoCheque() {
		return mostrarPanelAdjuntoCheque;
	}
	public void setMostrarPanelAdjuntoCheque(boolean mostrarPanelAdjuntoCheque) {
		this.mostrarPanelAdjuntoCheque = mostrarPanelAdjuntoCheque;
	}
	public Integer getIntOpcionBusquedaC() {
		return intOpcionBusquedaC;
	}
	public void setIntOpcionBusquedaC(Integer intOpcionBusquedaC) {
		this.intOpcionBusquedaC = intOpcionBusquedaC;
	}
	public List<SocioComp> getListaSocioIngresoCaja() {
		return listaSocioIngresoCaja;
	}
	public void setListaSocioIngresoCaja(List<SocioComp> listaSocioIngresoCaja) {
		this.listaSocioIngresoCaja = listaSocioIngresoCaja;
	}
	public List<Tabla> getListaTablaOpcionBusqueda() {
		return listaTablaOpcionBusqueda;
	}
	public void setListaTablaOpcionBusqueda(List<Tabla> listaTablaOpcionBusqueda) {
		this.listaTablaOpcionBusqueda = listaTablaOpcionBusqueda;
	}
	public Integer getIntCuentaSocioC() {
		return intCuentaSocioC;
	}
	public void setIntCuentaSocioC(Integer intCuentaSocioC) {
		this.intCuentaSocioC = intCuentaSocioC;
	}
	public Integer getIntModalidadC() {
		return intModalidadC;
	}
	public void setIntModalidadC(Integer intModalidadC) {
		this.intModalidadC = intModalidadC;
	}
	public Boolean getBlnDeshabilitarBusqSocioEntidadC() {
		return blnDeshabilitarBusqSocioEntidadC;
	}
	public void setBlnDeshabilitarBusqSocioEntidadC(
			Boolean blnDeshabilitarBusqSocioEntidadC) {
		this.blnDeshabilitarBusqSocioEntidadC = blnDeshabilitarBusqSocioEntidadC;
	}
	public SocioComp getSocioSeleccionado() {
		return socioSeleccionado;
	}
	public void setSocioSeleccionado(SocioComp socioSeleccionado) {
		this.socioSeleccionado = socioSeleccionado;
	}
	public String getStrMsgErrorSocioSeleccionado() {
		return strMsgErrorSocioSeleccionado;
	}
	public void setStrMsgErrorSocioSeleccionado(String strMsgErrorSocioSeleccionado) {
		this.strMsgErrorSocioSeleccionado = strMsgErrorSocioSeleccionado;
	}
	public Boolean getBlnSocioSeleccionadoOk() {
		return blnSocioSeleccionadoOk;
	}
	public void setBlnSocioSeleccionadoOk(Boolean blnSocioSeleccionadoOk) {
		this.blnSocioSeleccionadoOk = blnSocioSeleccionadoOk;
	}
	public List<SocioComp> getListaSocioCuentaIngresoCaja() {
		return listaSocioCuentaIngresoCaja;
	}
	public void setListaSocioCuentaIngresoCaja(
			List<SocioComp> listaSocioCuentaIngresoCaja) {
		this.listaSocioCuentaIngresoCaja = listaSocioCuentaIngresoCaja;
	}
	public List<SocioComp> getListaPersonaIngresoCaja() {
		return listaPersonaIngresoCaja;
	}
	public void setListaPersonaIngresoCaja(List<SocioComp> listaPersonaIngresoCaja) {
		this.listaPersonaIngresoCaja = listaPersonaIngresoCaja;
	}
	public String getStrMsgSubCondicionCuenta() {
		return strMsgSubCondicionCuenta;
	}
	public void setStrMsgSubCondicionCuenta(String strMsgSubCondicionCuenta) {
		this.strMsgSubCondicionCuenta = strMsgSubCondicionCuenta;
	}
	public List<ExpedienteComp> getListaMovimientoSocio() {
		return listaMovimientoSocio;
	}
	public void setListaMovimientoSocio(List<ExpedienteComp> listaMovimientoSocio) {
		this.listaMovimientoSocio = listaMovimientoSocio;
	}
	public BigDecimal getBdSumatoriaMontosTotalesAPagar() {
		return bdSumatoriaMontosTotalesAPagar;
	}
	public void setBdSumatoriaMontosTotalesAPagar(
			BigDecimal bdSumatoriaMontosTotalesAPagar) {
		this.bdSumatoriaMontosTotalesAPagar = bdSumatoriaMontosTotalesAPagar;
	}
	public String getStrDescPeriodo() {
		return strDescPeriodo;
	}
	public void setStrDescPeriodo(String strDescPeriodo) {
		this.strDescPeriodo = strDescPeriodo;
	}
	public List<ExpedienteComp> getListaIngresoSocio() {
		return listaIngresoSocio;
	}
	public void setListaIngresoSocio(List<ExpedienteComp> listaIngresoSocio) {
		this.listaIngresoSocio = listaIngresoSocio;
	}
	public GestorCobranza getGestorCobranzaTemp() {
		return gestorCobranzaTemp;
	}
	public void setGestorCobranzaTemp(GestorCobranza gestorCobranzaTemp) {
		this.gestorCobranzaTemp = gestorCobranzaTemp;
	}
	public Modelo getModeloPlanillaEfectuada() {
		return modeloPlanillaEfectuada;
	}
	public void setModeloPlanillaEfectuada(Modelo modeloPlanillaEfectuada) {
		this.modeloPlanillaEfectuada = modeloPlanillaEfectuada;
	}
	public Boolean getBlnValidacionesOK() {
		return blnValidacionesOK;
	}
	public void setBlnValidacionesOK(Boolean blnValidacionesOK) {
		this.blnValidacionesOK = blnValidacionesOK;
	}
	public BigDecimal getBdMontoIngresadoTotalSimulacion() {
		return bdMontoIngresadoTotalSimulacion;
	}
	public void setBdMontoIngresadoTotalSimulacion(
			BigDecimal bdMontoIngresadoTotalSimulacion) {
		this.bdMontoIngresadoTotalSimulacion = bdMontoIngresadoTotalSimulacion;
	}
	public Ingreso getIngresoGeneradoTrasGrabacion() {
		return ingresoGeneradoTrasGrabacion;
	}
	public void setIngresoGeneradoTrasGrabacion(Ingreso ingresoGeneradoTrasGrabacion) {
		this.ingresoGeneradoTrasGrabacion = ingresoGeneradoTrasGrabacion;
	}
	
	public List<Tabla> getListaTablaModalidadSocio() {
		return listaTablaModalidadSocio;
	}
	public void setListaTablaModalidadSocio(List<Tabla> listaTablaModalidadSocio) {
		this.listaTablaModalidadSocio = listaTablaModalidadSocio;
	}
	public BigDecimal getBdMontoDepositadoTotal() {
		return bdMontoDepositadoTotal;
	}
	public void setBdMontoDepositadoTotal(BigDecimal bdMontoDepositadoTotal) {
		this.bdMontoDepositadoTotal = bdMontoDepositadoTotal;
	}
	public String getStrIngCajaViewDescTipoCta() {
		return strIngCajaViewDescTipoCta;
	}
	public void setStrIngCajaViewDescTipoCta(String strIngCajaViewDescTipoCta) {
		this.strIngCajaViewDescTipoCta = strIngCajaViewDescTipoCta;
	}
	public String getStrIngCajaViewNroCta() {
		return strIngCajaViewNroCta;
	}
	public void setStrIngCajaViewNroCta(String strIngCajaViewNroCta) {
		this.strIngCajaViewNroCta = strIngCajaViewNroCta;
	}
	public Boolean getBlnIngresoCajaView() {
		return blnIngresoCajaView;
	}
	public void setBlnIngresoCajaView(Boolean blnIngresoCajaView) {
		this.blnIngresoCajaView = blnIngresoCajaView;
	}
	public String getStrIngCajaViewDescModalidadPago() {
		return strIngCajaViewDescModalidadPago;
	}
	public void setStrIngCajaViewDescModalidadPago(
			String strIngCajaViewDescModalidadPago) {
		this.strIngCajaViewDescModalidadPago = strIngCajaViewDescModalidadPago;
	}
	public String getStrIngCajaViewDescPersona() {
		return strIngCajaViewDescPersona;
	}
	public void setStrIngCajaViewDescPersona(String strIngCajaViewDescPersona) {
		this.strIngCajaViewDescPersona = strIngCajaViewDescPersona;
	}
	public Ingreso getDepositoGeneradoTrasGrabacion() {
		return depositoGeneradoTrasGrabacion;
	}
	public void setDepositoGeneradoTrasGrabacion(
			Ingreso depositoGeneradoTrasGrabacion) {
		this.depositoGeneradoTrasGrabacion = depositoGeneradoTrasGrabacion;
	}
	public Boolean getBlnExisteRedondeo() {
		return blnExisteRedondeo;
	}
	public void setBlnExisteRedondeo(Boolean blnExisteRedondeo) {
		this.blnExisteRedondeo = blnExisteRedondeo;
	}
	public Boolean getBlnDepositoCajaView() {
		return blnDepositoCajaView;
	}
	public void setBlnDepositoCajaView(Boolean blnDepositoCajaView) {
		this.blnDepositoCajaView = blnDepositoCajaView;
	}
	public String getStrMensajeErrorGestor() {
		return strMensajeErrorGestor;
	}
	public void setStrMensajeErrorGestor(String strMensajeErrorGestor) {
		this.strMensajeErrorGestor = strMensajeErrorGestor;
	}

	public List<Tabla> getLstReporteIngreso() {
		return lstReporteIngreso;
	}
	public void setLstReporteIngreso(List<Tabla> lstReporteIngreso) {
		this.lstReporteIngreso = lstReporteIngreso;
	}
	public Integer getIntTipoCuentaC() {
		return intTipoCuentaC;
	}
	public void setIntTipoCuentaC(Integer intTipoCuentaC) {
		this.intTipoCuentaC = intTipoCuentaC;
	}
	public Boolean getBlnDocumentoAgregado() {
		return blnDocumentoAgregado;
	}
	public void setBlnDocumentoAgregado(Boolean blnDocumentoAgregado) {
		this.blnDocumentoAgregado = blnDocumentoAgregado;
	}	
}