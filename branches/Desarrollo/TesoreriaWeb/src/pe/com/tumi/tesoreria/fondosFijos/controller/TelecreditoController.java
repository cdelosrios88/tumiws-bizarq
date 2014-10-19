package pe.com.tumi.tesoreria.fondosFijos.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConvertirLetras;
import pe.com.tumi.common.util.DocumentoGeneral;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.vinculo.domain.Vinculo;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;
import pe.com.tumi.seguridad.permiso.domain.TransaccionId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;
import pe.com.tumi.servicio.prevision.facade.LiquidacionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CancelacionCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EgresoDetalleInterfaz;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.PrestamoFacadeRemote;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijosId;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Movilidad;
import pe.com.tumi.tesoreria.egreso.facade.CierreDiarioArqueoFacadeRemote;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.domain.DocumentoSunat;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompra;
import pe.com.tumi.tesoreria.logistica.domain.OrdenCompraDetalle;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;

public class TelecreditoController {
	
	protected static Logger log = Logger.getLogger(CajaController.class);

	private Boolean		estadoTab;
	private EmpresaFacadeRemote 		empresaFacade;
	private PersonaFacadeRemote 		personaFacade;
	private TablaFacadeRemote 			tablaFacade;
	private BancoFacadeLocal 			bancoFacade;
	private EgresoFacadeLocal 			egresoFacade;
	private	PrevisionFacadeRemote 		previsionFacade;
	private	LiquidacionFacadeRemote 	liquidacionFacade;
	private	LibroDiarioFacadeRemote 	libroDiarioFacade;
	private	CierreDiarioArqueoFacadeRemote 	cierreDiarioArqueoFacade;
	private PrestamoFacadeRemote 		prestamoFacade;
	private LogisticaFacadeLocal		logisticaFacade;
	private PermisoFacadeRemote			permisoFacade;
	
	private	List<Egreso>	listaEgreso;
	private	List<Tabla>		listaTablaSucursal;
	private	List<Tabla>		listaMoneda;
	private	List<Tabla>		listaTablaDocumentoGeneral;
	private	List<Tabla>		listaSubTipoOperacion;
//	private	List<Tabla>	 		listaTablaTipoCredito;
	private	List<EgresoDetalleInterfaz>	listaEgresoDetalleInterfazAgregado;
	private List<ControlFondosFijos>	listaControlFondosFijosBusqueda; 
	/****/
	private	List<Tabla>	listaTablaTipoFondoFijo;
	private	List<ControlFondosFijos>		listaControlFondosFijos;
	private List<BeneficiarioPrevision>		listaBeneficiarioPrevision;
	private List<Persona>					listaBeneficiarioPersona;
	private	List<Persona>		listaPersona;
	private	List<Persona>		listaPersonaFiltro;
	private List<PersonaRol>	listaPersonaRolUsar;
	private List<DocumentoGeneral>	listaDocumentoAgregados;
	private List<DocumentoGeneral>	listaDocumentoPorAgregar;
	private List<Tabla> listaTipoVinculo;
	private List<Tabla> listaAño;
	private List<Tabla> listaTablaEstado;
	private List<Tabla> listaTablaTipoMovilidad;
	
	private Egreso		egresoNuevo;
	private Sucursal	sucursalUsuario;
	private	Subsucursal	subsucursalUsuario;
	private	Persona 	personaSeleccionada;
	private	CuentaBancaria	cuentaBancariaSeleccionada;
	private ControlFondosFijos controlFondosFijos;
	private NumberFormat formato;
	private	Persona		personaApoderado;
	private	Archivo		archivoCartaPoder;
	private Cuenta		cuentaActual;
	private LibroDiario libroDiario;
	
	private Usuario 	usuarioSesion;
	private String 		mensajeOperacion;
	private String 		mensajePopUp;
	private	BigDecimal	bdMontoGirar;
	private	BigDecimal	bdDiferencialGirar;
	private	String		strObservacion;
	private	String		strMontoGirarDescripcion;
	private	String		strDiferencialGirarDescripcion;
	private Integer		intTipoFondoFijoValidar;
	private Integer		intControlSeleccionado;
	private	String		strListaPersonaRolUsar;
	private Integer		intTipoBuscarPersona;
	private Integer		intBeneficiarioSeleccionar;
	/**Busqueda**/
	private Egreso		egresoFiltro;
	private	Integer		intAñoFiltro;
	private	Date		dtDesdeFiltro;
	private	Date		dtHastaFiltro;
	private	Integer		intTipoPersonaFiltro;
	private	Integer		intTipoBusquedaPersona;
	private	String		strTextoPersonaFiltro;
	private boolean		habilitarFiltroSucursal;
	private Integer		intItemControlFiltro;
	/****/
	private	Integer		intTipoPersona;
	private	String		strFiltroTextoPersona;
	private	Integer		intTipoDocumentoAgregar;
	private	DocumentoGeneral	documentoGeneralSeleccionado;
	
	private	Integer		SESION_IDEMPRESA;
	private	Integer		SESION_IDUSUARIO;
	private	Integer		SESION_IDSUCURSAL;
	private	Integer		SESION_IDSUBSUCURSAL;
	private	Integer		BUSCAR_PERSONA = 1;
	private	Integer		BUSCAR_APODERADO = 2;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
//	private boolean datosValidados;
	private boolean poseePermiso;
	
	//jchavez 29.04.2014
	private List<RequisitoCredito> lstRequisitoCredito;
	private List<RequisitoCreditoComp> lstRequisitoCreditoComp;
	private String strMensajeError;
	private Boolean blnNoExisteRequisito;
	private Boolean blnVerBotonApoderado;
	
	//jchavez 13.05.2014
	private List<RequisitoPrevision> lstRequisitoPrevision;
	private List<RequisitoPrevisionComp> lstRequisitoPrevisionComp;
	private BeneficiarioPrevision beneficiarioPrevisionSeleccionado;
	private String strMensajeErrorPorBeneficiario;
	private List<RequisitoLiquidacion> lstRequisitoLiquidacion;
	private List<RequisitoLiquidacionComp> lstRequisitoLiquidacionComp;
	private BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado;
	private Archivo archivoAdjuntoGiro;
	private Boolean blnBeneficiarioSinAutorizacion;
	private List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion;
	
	private String strObs;
	private Boolean blnHabilitarObservacion;
	private ConceptoFacadeRemote conceptoFacade;
	
	private List<Tabla> listaTablaTipoMoneda;
	private List<Tabla> listaTablaTipoCuentaBancaria;
	
	//Autor: jchavez / Tarea: Creación / Fecha: 11.09.2014 /
	private Egreso egresoGeneradoTrasGrabacion;
	private List<Tabla> listaTablaTipoDocumento;
	
		
	public TelecreditoController(){
		cargarUsuario();
		if(usuarioSesion!=null && poseePermiso() && estadoTab()){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public String getInicioPage() {
		cargarUsuario();	
		estadoTab();
		if(usuarioSesion!=null){
			limpiarFormulario();
			deshabilitarPanelInferior();
			cargarListaCCF();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}		
		return "";
	}
	
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getPerfil().getId().getIntPersEmpresaPk();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		
		setSucursalUsuario(usuarioSesion.getSucursal());
		setSubsucursalUsuario(usuarioSesion.getSubSucursal());	

	}
	
	private boolean poseePermiso(){
		poseePermiso = Boolean.FALSE;
		try{			
			PermisoPerfilId permisoPerfilid = null;
			permisoPerfilid = new PermisoPerfilId();
			permisoPerfilid.setIntPersEmpresaPk(SESION_IDEMPRESA);
			permisoPerfilid.setIntIdTransaccion(Constante.TRANSACCION_FONDOSFIJOS_CAJA);
			permisoPerfilid.setIntIdPerfil(usuarioSesion.getPerfil().getId().getIntIdPerfil());
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			
			PermisoPerfil permisoPerfil = permisoFacade.getPermisoPerfilPorPk(permisoPerfilid);
			
			if(permisoPerfil != null && permisoPerfil.getId()!=null){
				poseePermiso= Boolean.TRUE;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}		
		return poseePermiso;
	}
	
	private boolean estadoTab(){
		estadoTab = false;
		try {
			Transaccion transaccion = new Transaccion();
			transaccion.setId(new TransaccionId());
			transaccion.getId().setIntPersEmpresaPk(SESION_IDEMPRESA);
			transaccion.getId().setIntIdTransaccion(Constante.TRANSACCION_FONDOSFIJOS_TELECREDITO);
			
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			transaccion = permisoFacade.getTransaccionPorPk(transaccion.getId());
			if (transaccion!=null) {
				if (transaccion.getIntIdEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)) {
					estadoTab = true;
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return estadoTab;		
	}
	
	private void cargarValoresIniciales(){
		try {
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			previsionFacade = (PrevisionFacadeRemote) EJBFactory.getRemote(PrevisionFacadeRemote.class);
			liquidacionFacade = (LiquidacionFacadeRemote) EJBFactory.getRemote(LiquidacionFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			cierreDiarioArqueoFacade = (CierreDiarioArqueoFacadeRemote) EJBFactory.getRemote(CierreDiarioArqueoFacadeRemote.class);
			prestamoFacade = (PrestamoFacadeRemote) EJBFactory.getRemote(PrestamoFacadeRemote.class);
			logisticaFacade = (LogisticaFacadeLocal) EJBFactory.getLocal(LogisticaFacadeLocal.class);
			conceptoFacade = (ConceptoFacadeRemote) EJBFactory.getRemote(ConceptoFacadeRemote.class);
//			listaTablaTipoCredito = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPO_CREDITO));
			listaTablaDocumentoGeneral = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL));
			listaTablaTipoCuentaBancaria = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTABANCARIA));
			listaTablaTipoMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			listaTablaTipoDocumento = new ArrayList<Tabla>();
			
			listaMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			setListaSubTipoOperacion(tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPODESUBOPERACION), "A"));
			setListaTablaTipoFondoFijo(tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOFONDOFIJO),"A"));
			listaTipoVinculo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOVINCULO));
			listaTablaEstado = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOUNIVERSAL),"B");
			listaTablaTipoMovilidad = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_PLANILLAMOVILIDAD));
			intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			
			listaTablaSucursal = MyUtil.cargarListaTablaSucursal(listaTablaSucursal, SESION_IDEMPRESA);
			cargarUsuario();
			listaAño = MyUtil.obtenerListaAnios();
			egresoFiltro = new Egreso();
			egresoFiltro.setIntParaTipoFondoFijo(Constante.PARAM_T_TIPOFONDOFIJO_ENTREGARENDIR);
			egresoFiltro.setIntSucuIdSucursal(usuarioSesion.getSucursal().getId().getIntIdSucursal());
			intAñoFiltro = listaAño.get(0).getIntIdDetalle();
//			habilitarFiltroSucu();
//			obtenerListaNumeroApertura();
//			strMensajeError = "";
			lstRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
			lstRequisitoCredito = new ArrayList<RequisitoCredito>();
			blnVerBotonApoderado = true;
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#,###.00",otherSymbols);
			
			//
			lstRequisitoCredito = new ArrayList<RequisitoCredito>();
			lstRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
			lstRequisitoPrevision = new ArrayList<RequisitoPrevision>();
			lstRequisitoPrevisionComp = new ArrayList<RequisitoPrevisionComp>();
			lstRequisitoLiquidacion = new ArrayList<RequisitoLiquidacion>();
			lstRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
			listaEgresoDetalleInterfazAgregado = new ArrayList<EgresoDetalleInterfaz>();
			blnBeneficiarioSinAutorizacion = false;
			//
			listaEgreso = new ArrayList<Egreso>();
//			blnActivarNroApertura = true;
			intTipoFondoFijoValidar = Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO;
			deshabilitarPanelInferior();
			listaControlFondosFijosBusqueda = new ArrayList<ControlFondosFijos>();
			cargarListaCCF();
		} catch (Exception e) {
			log.info("Error en cargarValoresIniciales() ---> "+e.getMessage());
		}
	}
	
	//
	
	
	//Funcionalidad: Simula una lista de CFF, dado que en telecrédito el FF no existe en este punto. 
	public void cargarListaCCF(){
		ControlFondosFijos cff = new ControlFondosFijos();
		cff.setId(new ControlFondosFijosId());
		cff.getId().setIntPersEmpresa(SESION_IDEMPRESA);
		cff.getId().setIntParaTipoFondoFijo(intTipoFondoFijoValidar);
		cff.getId().setIntSucuIdSucursal(SESION_IDSUCURSAL);
		cff.setIntSudeIdSubsucursal(SESION_IDSUBSUCURSAL);
		listaControlFondosFijosBusqueda.add(cff);
		controlFondosFijos = cff;
	}
	
	/*******************************************************************************************************************************/
//
//	private void habilitarFiltroSucu(){
//		habilitarFiltroSucursal = Boolean.FALSE;
//		if(usuarioSesion.getSucursal().getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL))
//			habilitarFiltroSucursal = Boolean.TRUE;		
//		egresoFiltro.setIntSucuIdSucursal(usuarioSesion.getSucursal().getId().getIntIdSucursal());
//	}
	
	public void habilitarPanelInferior(){
		try{
//			datosValidados = Boolean.FALSE;
//			intTipoFondoFijoValidar = null;
			cargarUsuario();
			limpiarFormulario();
			listaControlFondosFijos = new ArrayList<ControlFondosFijos>();
			intControlSeleccionado = new Integer(0);
			
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = false;
			egresoNuevo = new Egreso();
			egresoNuevo.setDtFechaEgreso(MyUtil.obtenerFechaActual());
			cuentaBancariaSeleccionada = null;
//			blnActivarNroApertura = true;
			obtenerListaTipoDocumentoGeneral();
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
		log.info("Observacion: "+strObservacion);
		try {			
			// 1. Valida Cierre Diario Arqueo, si existe un cierre diario para la sucursal que hace el giro, se procede a la siguiente validación
			if(cierreDiarioArqueoFacade.existeCierreDiarioArqueo(SESION_IDEMPRESA, SESION_IDSUCURSAL, null)){
				log.info(controlFondosFijos);
				// 1.1. Valida si la caja asociada a la sucursal se encuentra cerrada el dia anterior
				if(!cierreDiarioArqueoFacade.existeCierreDiaAnterior(controlFondosFijos)){
					mensaje = "La caja de la sucursal no se ha cerrado el dia anterior."; return;
				}
				// 1.2. Valida si la caja asociada a la sucursal se encuentra cerrada a la fecha actual
				if(cierreDiarioArqueoFacade.existeCierreDiaActual(controlFondosFijos)){
					mensaje = "La caja de la sucursal ya se ha cerrado para el dia de hoy."; return;
				}
			}
			
			//validaciones
			if(intTipoDocumentoAgregar==null || intTipoDocumentoAgregar.equals(new Integer(0))){
				mensaje = "Debe seleccionar un tipo de documento a agregar."; return;
			}
			if(listaDocumentoAgregados==null || listaDocumentoAgregados.isEmpty()){
				mensaje = "Debe agregar al menos un documento."; return;
			}
			if(listaEgresoDetalleInterfazAgregado==null || listaEgresoDetalleInterfazAgregado.isEmpty()){
				mensaje = "Debe agregar al menos un documento."; return;
			}
			if(strObservacion==null || strObservacion.isEmpty()){
				mensaje = "Debe ingresar una observación.";	return;
			}
			if(bdMontoGirar==null ){
				mensaje = "El monto a girar no puede ser nulo.";	return;
			}
			if(cuentaBancariaSeleccionada==null ){
				mensaje = "Debe de seleccionar una cuenta bancaria.";	return;
			}
//			if(bdMontoGirar==null || bdMontoGirar.compareTo(controlFondosFijos.getBdMontoSaldo())>0){
//				mensaje = "No queda suficiente saldo en la apertura para realizar esa operación.";	return;
//			}
			//fin validaciones
			
			agregarStrGlosa(listaDocumentoAgregados);
			
			grabarGiro(listaDocumentoAgregados, controlFondosFijos, usuarioSesion, cuentaBancariaSeleccionada);
			
			mensaje = "Se registró correctamente el giro mediante la Planilla de Telecrédito.";
			
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			blnHabilitarObservacion = true;
			buscar();
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el giro mediante la Planilla de Telecrédito.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	/**
	 * Agregado 12.05.2014 jchavez
	 * evento al seleccionar un beneficiario.
	 */
	public void seleccionarBeneficiario(){
		strMensajeErrorPorBeneficiario = "";
		blnBeneficiarioSinAutorizacion = false;
		if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
			if (intBeneficiarioSeleccionar!=0) {
				if(validarNoExisteRequisitoAdjuntoGiroPrevision()){
					strMensajeErrorPorBeneficiario = "Este beneficiario no tiene adjunto de autorización de giro.";
					blnBeneficiarioSinAutorizacion = true;
				}
			}else {
				strMensajeErrorPorBeneficiario = "Debe de seleccionar un beneficiario.";
				blnBeneficiarioSinAutorizacion = true;
			}
		}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
			if (intBeneficiarioSeleccionar!=0) {
				if(validarNoExisteRequisitoAdjuntoGiroLiquidacion()){
					strMensajeErrorPorBeneficiario = "Este beneficiario no tiene adjunto de autorización de giro.";
					blnBeneficiarioSinAutorizacion = true;
				}
			}else {
				strMensajeErrorPorBeneficiario = "Debe de seleccionar un beneficiario.";
				blnBeneficiarioSinAutorizacion = true;
			}
		}
		log.info("documentoGeneralSeleccionado:"+documentoGeneralSeleccionado);
		log.info("deshabilitarNuevo:"+deshabilitarNuevo);
		log.info("blnBeneficiarioSinAutorizacion:"+blnBeneficiarioSinAutorizacion);
	}
	
	/**
	 * Agregado 10.05.2014 jchavez 
	 * validacion de existencia de adjunto de giro prevision 
	 */
	public boolean validarNoExisteRequisitoCredito(){
		log.info("--validarExisteRequisito()");
		blnNoExisteRequisito = true;
		deshabilitarNuevo = false;
		ExpedienteCredito expedienteCredito = null;
		lstRequisitoCredito.clear();
		lstRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		
		try {
//			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			//0. Recuperamos el expediente credito seleccionado...
			expedienteCredito = documentoGeneralSeleccionado.getExpedienteCredito();
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			lstRequisitoCreditoComp = prestamoFacade.getRequisitoGiroPrestamo(expedienteCredito);
			//2. validamos si existe registro en requisito credito
			if (lstRequisitoCreditoComp!=null && !lstRequisitoCreditoComp.isEmpty()) {
				Integer requisitoSize = lstRequisitoCreditoComp.size();

				for (RequisitoCreditoComp o : lstRequisitoCreditoComp) {
					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
					lstRequisitoCredito.addAll(prestamoFacade.getListaPorPkExpedienteCreditoYRequisitoDetalle(expedienteCredito.getId(), reqAutDetalleId));
				}
				if (lstRequisitoCredito!=null && !lstRequisitoCredito.isEmpty()) {
					if (requisitoSize.equals(lstRequisitoCredito.size())) {
						archivoAdjuntoGiro = prestamoFacade.getArchivoPorRequisitoCredito(lstRequisitoCredito.get(0));
						blnNoExisteRequisito = false;	
						deshabilitarNuevo = true;
					}
				}
			}
			
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return blnNoExisteRequisito;
	}
	/**
	 * Agregado 10.05.2014 jchavez 
	 * validacion de existencia de adjunto de giro prevision 
	 * @return
	 */
	public boolean validarNoExisteRequisitoAdjuntoGiroPrevision(){
		log.info("----- validarNoExisteRequisitoAdjuntoGiroPrevision()");
		blnNoExisteRequisito = true;
		deshabilitarNuevo = false;
		ExpedientePrevision expedientePrevision = null;
		lstRequisitoPrevision.clear();
		lstRequisitoPrevisionComp.clear();
		Integer intIdMaestro = null;
		Integer intParaTipoDescripcion = null;
		listaEgresoDetalleInterfazAgregado.clear();
		try {
			//0. Recuperamos el expediente prevision seleccionado...
			expedientePrevision = documentoGeneralSeleccionado.getExpedientePrevision();
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_SEPELIO)) {
				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_FONDOSEPELIO);
				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_SEPELIO;
				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_SEPELIO_DETALLE;
			}
			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_RETIRO)) {
				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_FONDORETIRO);
				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_RETIRO;
				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_RETIRO_DETALLE;
			}
			if (expedientePrevision.getIntParaTipoCaptacion().equals(Constante.CAPTACION_AES)) {
				expedientePrevision.setIntParaTipoOperacion(Constante.PARAM_T_TIPOOPERACION_AES);
				intIdMaestro = Constante.PARAM_T_ADJUNTOGIROPREVISION_AES;
				intParaTipoDescripcion = Constante.PARAM_T_ADJUNTOGIROPREVISION_AES_DETALLE;
			}
			
			lstRequisitoPrevisionComp = previsionFacade.getRequisitoGiroPrevision(expedientePrevision, intIdMaestro, intParaTipoDescripcion);
			//
			for(BeneficiarioPrevision beneficiarioPrevision : listaBeneficiarioPrevision){
				if(beneficiarioPrevision.getId().getIntItemBeneficiario().equals(intBeneficiarioSeleccionar)){
					beneficiarioPrevisionSeleccionado = beneficiarioPrevision;
//					listaEgresoDetalleInterfaz = previsionFacade.cargarListaEgresoDetalleInterfaz(expedientePrevisionGirar, beneficiarioSeleccionado);					
//					bdTotalEgresoDetalleInterfaz = ((EgresoDetalleInterfaz)(listaEgresoDetalleInterfaz.get(0))).getBdSubTotal();;
					break;
				}
			}
			
			//2. validamos si existe registro en requisito prevision
			if (lstRequisitoPrevisionComp!=null && !lstRequisitoPrevisionComp.isEmpty()) {
				Integer requisitoSize = lstRequisitoPrevisionComp.size();

				for (RequisitoPrevisionComp o : lstRequisitoPrevisionComp) {
					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
					lstRequisitoPrevision.addAll(previsionFacade.getListaPorPkExpedientePrevisionYRequisitoDetalle(expedientePrevision.getId(), reqAutDetalleId, beneficiarioPrevisionSeleccionado.getIntPersPersonaBeneficiario()));
				}
				if (lstRequisitoPrevision!=null && !lstRequisitoPrevision.isEmpty()) {
					if (requisitoSize.equals(lstRequisitoPrevision.size())) {
						archivoAdjuntoGiro = previsionFacade.getArchivoPorRequisitoPrevision(lstRequisitoPrevision.get(0));
						blnNoExisteRequisito = false;	
//						deshabilitarNuevo = true;
					}
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return blnNoExisteRequisito;
	}
	
	/**
	 * Agregado 12.05.2014 jchavez
	 * valida la existencia de requisito liquidacion en giro
	 * @return
	 */
	public boolean validarNoExisteRequisitoAdjuntoGiroLiquidacion(){
		log.info("----- validarNoExisteRequisitoAdjuntoGiroLiquidacion()");
		blnNoExisteRequisito = true;
		deshabilitarNuevo = false;
		ExpedienteLiquidacion expedienteLiquidacion = null;
		lstRequisitoLiquidacion.clear();
		lstRequisitoLiquidacionComp.clear();
		listaEgresoDetalleInterfazAgregado.clear();
		try {
			//0. Recuperamos el expediente prevision seleccionado...
			expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
			//1. Recuperamos la lista de requisitos necesarios para realizar e giro en Sede
			lstRequisitoLiquidacionComp = liquidacionFacade.getRequisitoGiroLiquidacionBanco(expedienteLiquidacion);
			//
			for(BeneficiarioLiquidacion beneficiarioLiquidacion : listaBeneficiarioLiquidacion){
				if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(intBeneficiarioSeleccionar)){
					beneficiarioLiquidacionSeleccionado = beneficiarioLiquidacion;
					break;
				}
			}			
			//2. validamos si existe registro en requisito credito
			if (lstRequisitoLiquidacionComp!=null && !lstRequisitoLiquidacionComp.isEmpty()) {
				Integer requisitoSize = lstRequisitoLiquidacionComp.size();
//				List<RequisitoLiquidacion> lstRequisitoLiquidacionTemp = null;
				for (RequisitoLiquidacionComp o : lstRequisitoLiquidacionComp) {
					ConfServDetalleId reqAutDetalleId = new ConfServDetalleId();
					reqAutDetalleId.setIntPersEmpresaPk(o.getIntEmpresa());
					reqAutDetalleId.setIntItemSolicitud(o.getIntItemRequisito());
					reqAutDetalleId.setIntItemDetalle(o.getIntItemRequisitoDetalle());
					//Se agrega campo del codigo de persona beneficiario. jchavez 09.05.2014
					lstRequisitoLiquidacion.addAll(liquidacionFacade.getListaPorPkExpedienteLiquidacionYRequisitoDetalle(expedienteLiquidacion.getId(), reqAutDetalleId, beneficiarioLiquidacionSeleccionado.getIntPersPersonaBeneficiario()));
				}
				if (lstRequisitoLiquidacion!=null && !lstRequisitoLiquidacion.isEmpty()) {
					if (requisitoSize.equals(lstRequisitoLiquidacion.size())) {
						archivoAdjuntoGiro = liquidacionFacade.getArchivoPorRequisitoLiquidacion(lstRequisitoLiquidacion.get(0));
						blnNoExisteRequisito = false;	
//						deshabilitarNuevo = true;
					}
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
		return blnNoExisteRequisito;
	}	
	
	public void grabarGiro(List<DocumentoGeneral> listaDocumentoGeneral, ControlFondosFijos controlFondosFijos, Usuario usuario, CuentaBancaria cuentaBancaria) 
	throws Exception {		
		controlFondosFijos.setCuentaBancaria(cuentaBancariaSeleccionada);
		Integer intTipoDocumentoGeneral = listaDocumentoGeneral.get(0).getIntTipoDocumento();
		//Solo pata el caso de documentos de movilidad, todos los documentoGeneral se giraran en un solo egreso
		if(intTipoDocumentoGeneral.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
			List<Movilidad> listaMovilidad = new ArrayList<Movilidad>();
			for(DocumentoGeneral documentoGeneral: listaDocumentoAgregados)
				listaMovilidad.add(documentoGeneral.getMovilidad());			
			Egreso egreso = egresoFacade.generarEgresoMovilidad(listaMovilidad, controlFondosFijos, usuario);
			egresoGeneradoTrasGrabacion = egresoFacade.grabarGiroMovilidad(egreso, listaMovilidad);
			procesarItems(egresoGeneradoTrasGrabacion);
		
		}else if(intTipoDocumentoGeneral.equals(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS)){
			List<DocumentoSunat> listaDocumentoSunat = new ArrayList<DocumentoSunat>();
			for(DocumentoGeneral documentoGeneral: listaDocumentoAgregados)
				listaDocumentoSunat.add(documentoGeneral.getDocumentoSunat());
			egresoGeneradoTrasGrabacion = egresoFacade.grabarGiroDocumentoSunat(listaEgresoDetalleInterfazAgregado, controlFondosFijos, usuario);
			procesarItems(egresoGeneradoTrasGrabacion);
		//Autor: jchavez / Tarea: Creacion / Fecha: 15.10.2014
		}else if (listaDocumentoGeneral.get(0).getOrdenCompra()!=null && 
				(listaDocumentoGeneral.get(0).getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)
						|| listaDocumentoGeneral.get(0).getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA))) {
			List<OrdenCompra> listaOrdenCompra = new ArrayList<OrdenCompra>();
			for(DocumentoGeneral documentoGeneral: listaDocumentoAgregados){
				listaOrdenCompra.add(documentoGeneral.getOrdenCompra());
			}
			egresoGeneradoTrasGrabacion = egresoFacade.grabarGiroOrdenCompraDocumento(listaEgresoDetalleInterfazAgregado, controlFondosFijos, usuario, intTipoDocumentoAgregar);
			procesarItems(egresoGeneradoTrasGrabacion);
		}//Fin jchavez - 15.10.2014
		
		for(DocumentoGeneral documentoGeneral : listaDocumentoGeneral){
			if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){				
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				ExpedienteCredito expedienteCredito = documentoGeneral.getExpedienteCredito();
				Egreso egreso = prestamoFacade.generarEgresoPrestamo(expedienteCredito, controlFondosFijos, usuario);
				//jchavez 24.06.2014
				expedienteCredito = egreso.getExpedienteCredito();
				expedienteCredito.setEgreso(egreso);
				expedienteCredito = prestamoFacade.grabarGiroPrestamoPorTesoreria(expedienteCredito);
				egresoGeneradoTrasGrabacion = expedienteCredito.getEgreso();
				procesarItems(egresoGeneradoTrasGrabacion);
					
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				ExpedientePrevision expedientePrevision = documentoGeneral.getExpedientePrevision();
				Egreso egreso = previsionFacade.generarEgresoGiroPrevision(expedientePrevision, controlFondosFijos, usuario);
				expedientePrevision.setEgreso(egreso);
				expedientePrevision = previsionFacade.grabarGiroPrevisionPorTesoreria(expedientePrevision);
				egresoGeneradoTrasGrabacion = expedientePrevision.getEgreso();
				procesarItems(egresoGeneradoTrasGrabacion);
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				ExpedienteLiquidacion expedienteLiquidacion = documentoGeneral.getExpedienteLiquidacion();
				log.info(expedienteLiquidacion);
				expedienteLiquidacion.setBeneficiarioLiquidacionGirar(new BeneficiarioLiquidacion());
				for (BeneficiarioLiquidacion x : listaBeneficiarioLiquidacion) {
					if (x.getIntPersPersonaBeneficiario().equals(intBeneficiarioSeleccionar)) {
						expedienteLiquidacion.setBeneficiarioLiquidacionGirar(x);		
						break;
					}
				}
				expedienteLiquidacion.getBeneficiarioLiquidacionGirar().setPersonaApoderado(personaApoderado);
				expedienteLiquidacion.getBeneficiarioLiquidacionGirar().setArchivoCartaPoder(archivoCartaPoder);
				Egreso egreso = liquidacionFacade.generarEgresoGiroLiquidacion(expedienteLiquidacion, controlFondosFijos, usuario);
				expedienteLiquidacion.setEgreso(egreso);
				expedienteLiquidacion = liquidacionFacade.grabarGiroLiquidacionPorTesoreria(expedienteLiquidacion);
				egresoGeneradoTrasGrabacion = expedienteLiquidacion.getEgreso();
				procesarItems(egresoGeneradoTrasGrabacion);
			}
		}
	}
	
	private List<DocumentoGeneral> agregarStrGlosa(List<DocumentoGeneral> listaDocumentoGeneral) throws Exception{
		for(DocumentoGeneral documentoGeneral : listaDocumentoGeneral){
			if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				Movilidad movilidad = documentoGeneral.getMovilidad();
				movilidad.setStrGlosaEgreso(strObservacion);
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				ExpedienteCredito expedienteCredito = documentoGeneral.getExpedienteCredito();
				expedienteCredito.setStrGlosaEgreso(strObservacion);
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
					|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					|| documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				ExpedientePrevision expedientePrevision = documentoGeneral.getExpedientePrevision();
				expedientePrevision.setStrGlosaEgreso(strObservacion);
				
			}else if(documentoGeneral.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				ExpedienteLiquidacion expedienteLiquidacion = documentoGeneral.getExpedienteLiquidacion();
				expedienteLiquidacion.setStrGlosaEgreso(strObservacion);
			//Autor: jchavez / Tarea: Creación / Fecha: 15.10.2014
			}else if(documentoGeneralSeleccionado.getOrdenCompra()!=null && 
					(documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)
							|| documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA))){
				OrdenCompra ordCmp = documentoGeneral.getOrdenCompra();
				ordCmp.setStrGlosaEgreso(strObservacion);
			}//fin jchavez - 15 .10.2014
		}
		return listaDocumentoGeneral;
	}
	
//	public void buscar(){
//		log.info("--buscar");
//		try{
//			List<ControlFondosFijos> listaControl = new ArrayList<ControlFondosFijos>();
//			if(intItemControlFiltro.equals(new Integer(0))){
//				for(ControlFondosFijos cff : listaControlFondosFijosBusqueda)
//					listaControl.add(cff);				
//			}else{
//				listaControl.add(obtenerCFFSeleccionado());			
//			}
//			listaPersonaFiltro = new ArrayList<Persona>();
//			if(strTextoPersonaFiltro!=null && !strTextoPersonaFiltro.isEmpty())
//				listaPersonaFiltro = personaFacade.buscarListaPersonaParaFiltro(intTipoBusquedaPersona, strTextoPersonaFiltro);							
//			
//			listaEgreso = egresoFacade.buscarEgresoParaFondosFijos(listaPersonaFiltro, egresoFiltro, listaControl, dtDesdeFiltro, dtHastaFiltro);
//			
//			for(Egreso egreso : listaEgreso)
//				egreso.setPersonaApoderado(personaFacade.devolverPersonaCargada(egreso.getIntPersPersonaGirado()));
//			
//			//ordenamos
//			Collections.sort(listaEgreso, new Comparator<Egreso>(){
//				public int compare(Egreso uno, Egreso otro) {
//					return otro.getStrNumeroEgreso().compareTo(uno.getStrNumeroEgreso());
//				}
//			});
//		}catch (Exception e) {
//			log.error(e.getMessage(),e);
//		}
//	}	
//	
	public void buscar(){
		log.info("--buscar");
		List<Persona> listaPersonaFiltro = null;
		listaEgreso.clear();
//		List<ControlFondosFijos> listaControl = new ArrayList<ControlFondosFijos>();
		ControlFondosFijos controlFondosFijosBusqueda = new ControlFondosFijos();
		
		try{			
			if(strTextoPersonaFiltro!=null && !strTextoPersonaFiltro.trim().isEmpty()){
				listaPersonaFiltro = personaFacade.buscarListaPersonaParaFiltro(intTipoBusquedaPersona, strTextoPersonaFiltro);
			}
			
			controlFondosFijosBusqueda.setId(new ControlFondosFijosId());
			controlFondosFijosBusqueda.getId().setIntPersEmpresa(SESION_IDEMPRESA);
			controlFondosFijosBusqueda.getId().setIntParaTipoFondoFijo(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO);
			controlFondosFijosBusqueda.getId().setIntItemPeriodoFondo(null);
			controlFondosFijosBusqueda.getId().setIntSucuIdSucursal(SESION_IDSUCURSAL);
			controlFondosFijosBusqueda.getId().setIntItemFondoFijo(null);
			
			listaEgreso = egresoFacade.buscarEgresoParaFondosFijos(listaPersonaFiltro, egresoFiltro, controlFondosFijosBusqueda, dtDesdeFiltro, dtHastaFiltro);
			
			if (!listaEgreso.isEmpty()) {
				for(Egreso egreso : listaEgreso){
					egreso.setPersonaApoderado(personaFacade.devolverPersonaCargada(egreso.getIntPersPersonaGirado()));
				}
			}
			
			//ordenamos
			Collections.sort(listaEgreso, new Comparator<Egreso>(){
				public int compare(Egreso uno, Egreso otro) {
					return otro.getStrNumeroEgreso().compareTo(uno.getStrNumeroEgreso());
				}
			});
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
//	private ControlFondosFijos obtenerCFFSeleccionado()throws Exception{
//		ControlFondosFijos cffSeleccionado = null;
//		for(ControlFondosFijos cff : listaControlFondosFijosBusqueda)
//			if(cff.getIntItemFiltro().equals(intItemControlFiltro))
//				cffSeleccionado = cff;
//		return cffSeleccionado;
//	}
	
//	public void obtenerListaNumeroApertura()throws Exception{
//		try{
//			listaControlFondosFijosBusqueda = egresoFacade.obtenerListaNumeroApertura(
//					egresoFiltro.getIntParaTipoFondoFijo(), intAñoFiltro, egresoFiltro.getIntSucuIdSucursal());
//			int intItemInterfaz = 0;
//			for(ControlFondosFijos cff : listaControlFondosFijosBusqueda){
//				intItemInterfaz ++;
//				cff.setIntItemFiltro(intItemInterfaz);
//			}
//			
//			//ordenamos
//			Collections.sort(listaControlFondosFijosBusqueda, new Comparator<ControlFondosFijos>(){
//				public int compare(ControlFondosFijos uno, ControlFondosFijos otro) {
//					return otro.getStrNumeroApertura().compareTo(uno.getStrNumeroApertura());
//				}
//			});
//		}catch (Exception e) {
//			log.error(e.getMessage(),e);
//		}			
//	}
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
		documentoGeneralSeleccionado = null;
	}
	
	private LibroDiario obtenerLibroDiario(Egreso egreso) throws Exception{
		LibroDiarioId libroDiarioId = new LibroDiarioId();
		libroDiarioId.setIntPersEmpresaLibro(egreso.getIntPersEmpresaLibro());
		libroDiarioId.setIntContPeriodoLibro(egreso.getIntContPeriodoLibro());
		libroDiarioId.setIntContCodigoLibro(egreso.getIntContCodigoLibro());
		return libroDiarioFacade.getLibroDiarioPorPk(libroDiarioId);		
	}
	
	private EgresoDetalle obtenerEgresoDetalle(Egreso egreso)throws Exception{
		//Algunos egresodetalles tienen en documentogeneral el tipo de controlfondosfijos
		for(EgresoDetalle egresoDetalle : egreso.getListaEgresoDetalle()){
			//Autor: jchavez / Tarea: Modificación / Fecha: 16.08.2014 / No debería existir esta validación
//			if(egresoDetalle.getIntParaDocumentoGeneral().compareTo(new Integer(300))>=0)
				return egresoDetalle;
		}
		return null;
	}
	
	public void verRegistro(ActionEvent event){
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			limpiarFormulario();
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
//			datosValidados = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			
			Egreso egreso = (Egreso)event.getComponent().getAttributes().get("item");
			EgresoDetalle egresoDetalle = obtenerEgresoDetalle(egreso);
			libroDiario = obtenerLibroDiario(egreso);
			
			//Autor: jchavez / Tarea: Modificación / Fecha: 11.09.2014
			egresoGeneradoTrasGrabacion = new Egreso();
			egresoGeneradoTrasGrabacion.setIntItemPeriodoEgreso(egreso.getIntItemPeriodoEgreso());
			egresoGeneradoTrasGrabacion.setIntItemEgreso(egreso.getIntItemEgreso());
			egresoGeneradoTrasGrabacion.setIntContPeriodoLibro(egreso.getIntContPeriodoLibro());
			egresoGeneradoTrasGrabacion.setIntContCodigoLibro(egreso.getIntContCodigoLibro());
			procesarItems(egresoGeneradoTrasGrabacion);
			
			
			
			
			//Fin jchavez - 11.09.2014
			log.info(egreso);
			egresoNuevo = egreso;
			/*bdMontoGirar = egreso.getBdMontoTotal();
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoDetalle.getIntParaTipoMoneda());
			*/
			strObservacion = egreso.getStrObservacion();
			controlFondosFijos = egreso.getControlFondosFijos();
//			intTipoFondoFijoValidar = controlFondosFijos.getId().getIntParaTipoFondoFijo();
			personaSeleccionada = egreso.getPersonaApoderado();
			intTipoDocumentoAgregar = egresoDetalle.getIntParaDocumentoGeneral();
			
			/**Para que se muestre correctamente el panel de la persona seleccionada**/
			intTipoPersona = personaSeleccionada.getIntTipoPersonaCod();
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL))
				strFiltroTextoPersona = personaSeleccionada.getDocumento().getStrNumeroIdentidad();
			else
				strFiltroTextoPersona = personaSeleccionada.getStrRuc();
			
			buscarPersona();
			personaSeleccionada = listaPersona.get(0);
			if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				agregarNombreCompleto(personaSeleccionada);
				agregarDocumentoDNI(personaSeleccionada);
			}			
//			filtrarListaTablaDocumentoGeneral();
			/* Autor: jchavez / Tarea: Creación / Fecha: 11.09.2014
			 * Se recupera cuenta bancaria del socio*/
			String strTipoCuenta = "";
			String strTipoMoneda = "";
			Persona pers = personaFacade.getPersonaDetalladaPorIdPersonaYEmpresa(personaSeleccionada.getNatural().getIntIdPersona(), SESION_IDEMPRESA);
			//Autor jchavez / Tarea: Se regresa al tipo de dato integer y se graba la llave de la cuenta / Fecha: 19.09.2014
			if (egreso.getIntPersCuentaBancariaGirado()!=null) {
				if (pers.getListaCuentaBancaria()!=null && !pers.getListaCuentaBancaria().isEmpty()) {
					for (CuentaBancaria cb : pers.getListaCuentaBancaria()) {
						if (cb.getId().getIntIdCuentaBancaria().equals(egreso.getIntPersCuentaBancariaGirado())) {
							//Fin jchavez - 19.09.2014
							cuentaBancariaSeleccionada = cb;
							break;
						}
					}
				}
				for (Tabla x : listaTablaTipoCuentaBancaria) {
					if (x.getIntIdDetalle().equals(cuentaBancariaSeleccionada.getIntTipoCuentaCod())) {
						strTipoCuenta = "Tipo Cuenta: "+x.getStrDescripcion();
						break;
					}
				}
				for (Tabla y : listaTablaTipoMoneda) {
					if (y.getIntIdDetalle().equals(cuentaBancariaSeleccionada.getIntMonedaCod())) {
						strTipoMoneda = " - Moneda: "+y.getStrDescripcion();
						break;
					}
				}
				cuentaBancariaSeleccionada.setStrEtiqueta(strTipoCuenta+strTipoMoneda+" - Nro. Cuenta: "+cuentaBancariaSeleccionada.getStrNroCuentaBancaria());
			}else {
				cuentaBancariaSeleccionada = new CuentaBancaria();
				cuentaBancariaSeleccionada.setStrEtiqueta("ERROR: No se grabó la cuenta bancaria del apoderado. Verifique.");
			}
			
			
			
			
			
			documentoGeneralSeleccionado = new DocumentoGeneral();
			documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
			documentoGeneralSeleccionado.setBdMonto(new BigDecimal(0));
			if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				List<Movilidad> listaMovilidad = egresoFacade.obtenerListaMovilidadPorEgreso(egreso);
				for(Movilidad movilidad : listaMovilidad){
					documentoGeneralSeleccionado = new DocumentoGeneral();
					documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
					documentoGeneralSeleccionado.setMovilidad(movilidad);
					documentoGeneralSeleccionado.setBdMonto(new BigDecimal(0));
					agregarDocumento();
				}
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				ExpedienteCredito expedienteCredito = prestamoFacade.obtenerExpedientePorEgreso(egreso);
				documentoGeneralSeleccionado.setExpedienteCredito(expedienteCredito);
				if(egreso.getIntPersPersonaApoderado()!=null){
					personaApoderado = personaFacade.devolverPersonaCargada(egreso.getIntPersPersonaApoderado());
					archivoCartaPoder = egresoFacade.getArchivoPorEgreso(egreso);
				}				
				validarNoExisteRequisitoCredito();
				agregarDocumento();			
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				
				BeneficiarioPrevision beneficiarioPrevision = previsionFacade.getBeneficiarioPrevisionPorEgreso(egreso);
				ExpedientePrevision expedientePrevision = previsionFacade.getExpedientePrevisionPorBeneficiarioPrevision(beneficiarioPrevision);
				documentoGeneralSeleccionado.setExpedientePrevision(expedientePrevision);
				intBeneficiarioSeleccionar = beneficiarioPrevision.getId().getIntItemBeneficiario();
				cargarListaBeneficiarioPrevision();
				if(egreso.getIntPersPersonaApoderado()!=null){
					personaApoderado = personaFacade.devolverPersonaCargada(egreso.getIntPersPersonaApoderado());
					archivoCartaPoder = egresoFacade.getArchivoPorEgreso(egreso);
				}
				validarNoExisteRequisitoAdjuntoGiroPrevision();
				agregarDocumento();
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				List<BeneficiarioLiquidacion> listaBeneficiarioliquidacion = liquidacionFacade.getListaBeneficiarioLiquidacionPorEgreso(egreso);
				BeneficiarioLiquidacion beneficiarioLiquidacion = listaBeneficiarioliquidacion.get(0);
				ExpedienteLiquidacion expedienteLiquidacion = liquidacionFacade.getExpedienteLiquidacionPorBeneficiario(beneficiarioLiquidacion);
				expedienteLiquidacion.setListaExpedienteLiquidacionDetalle(liquidacionFacade.getListaExpedienteLiquidacionDetallePorExpediente(expedienteLiquidacion));
				documentoGeneralSeleccionado.setExpedienteLiquidacion(expedienteLiquidacion);
				intBeneficiarioSeleccionar = beneficiarioLiquidacion.getIntPersPersonaBeneficiario();
				cargarListaBeneficiarioLiquidacion();
				if(egreso.getIntPersPersonaApoderado()!=null){
					personaApoderado = personaFacade.devolverPersonaCargada(egreso.getIntPersPersonaApoderado());
					archivoCartaPoder = egresoFacade.getArchivoPorEgreso(egreso);
				}
				validarNoExisteRequisitoAdjuntoGiroLiquidacion();
				agregarDocumento();
			
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS)){
				List<DocumentoSunat> listaDocumentoSunat = egresoFacade.obtenerListaDocumentoSunatPorEgreso(egreso);
				for(DocumentoSunat documentoSunat : listaDocumentoSunat){
					documentoSunat.setDtFechaDeGiro(egreso.getDtFechaEgreso());
					documentoGeneralSeleccionado = new DocumentoGeneral();
					documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
					documentoGeneralSeleccionado.setDocumentoSunat(documentoSunat);
					documentoGeneralSeleccionado.setBdMonto(new BigDecimal(0));
					agregarDocumento();
				}
			}
			
			//bdMontoGirar = egreso.getBdMontoTotal();
			//strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, egresoDetalle.getIntParaTipoMoneda());
			//strDiferencialGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdDiferencialGirar, controlFondosFijos.getIntParaMoneda());
			
			exito = Boolean.TRUE;
			
			documentoGeneralSeleccionado = new DocumentoGeneral();
			documentoGeneralSeleccionado.setIntTipoDocumento(intTipoDocumentoAgregar);
			
			log.info(strMontoGirarDescripcion);
			log.info(strDiferencialGirarDescripcion);
		}catch (Exception e) {
			mensaje = "Ocurrio un error en la selección de egreso.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
//	public void seleccionarTipoFondoFijo(){
////		blnActivarNroApertura = true;
//		try{			
//			//Autor: jchavez / Tarea: Modificación / Fecha: 19.08.2014 / 
//			if (intTipoFondoFijoValidar.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)) {
////				blnActivarNroApertura = false;
//			}else{
//				listaControlFondosFijos = new ArrayList<ControlFondosFijos>();
//				if(intTipoFondoFijoValidar.equals(new Integer(0)))
//					return;			
//				ControlFondosFijos controlFondosFijosValidar = new ControlFondosFijos();
//				controlFondosFijosValidar.getId().setIntPersEmpresa(SESION_IDEMPRESA);
//				controlFondosFijosValidar.getId().setIntParaTipoFondoFijo(intTipoFondoFijoValidar);
//				controlFondosFijosValidar.getId().setIntSucuIdSucursal(SESION_IDSUCURSAL);
//				
//				List<ControlFondosFijos> listaTemp1 = egresoFacade.buscarControlFondosFijos(controlFondosFijosValidar);
//				List<ControlFondosFijos> listaTemp = new ArrayList<ControlFondosFijos>();
//				for(ControlFondosFijos cFF : listaTemp1){
//					if(cFF.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)) listaTemp.add(cFF);				
//				}
//				
//				HashSet<Integer> hashTipoMonedas = new HashSet<Integer>();
//				for(ControlFondosFijos cFF : listaTemp){
//					Integer intTipoMoneda = egresoFacade.obtenerMonedaDeCFF(cFF);
//					hashTipoMonedas.add(intTipoMoneda);
//				}
//				
//				for(Integer intTipoMoneda : hashTipoMonedas){
//					List<ControlFondosFijos> listaCFF = new ArrayList<ControlFondosFijos>();
//					for(ControlFondosFijos cFF : listaTemp){
//						if(cFF.getIntParaMoneda().equals(intTipoMoneda))	listaCFF.add(cFF);					
//					}
//					listaControlFondosFijos.add(egresoFacade.obtenerControlFondosFijosUltimo(listaCFF));
//				}
//				
//				int intItemInterfaz = 0;
//				for(ControlFondosFijos cff : listaControlFondosFijos){
//					if(cff==null)	continue;				
//					intItemInterfaz = intItemInterfaz + 1 ;				
//					String strEtiqueta = cff.getStrNumeroApertura()+ " - " +
//					MyUtil.obtenerEtiquetaTabla(cff.getIntParaMoneda(), listaMoneda)+" Saldo : "+ cff.getBdMontoSaldo();
//					
//					cff.setStrDescripcion(strEtiqueta);
//					cff.setIntItemFiltro(intItemInterfaz);
//				}
//			}			
//			
//		}catch(Exception e){
//			log.error(e.getMessage(),e);
//		}
//	}
	
//	private ControlFondosFijos obtenerControlFondosFijosSeleccionado(){
//		for(ControlFondosFijos cff : listaControlFondosFijos){
//			log.info("cff filtro:"+cff.getIntItemFiltro());
//			if(cff.getIntItemFiltro().equals(intControlSeleccionado))	return cff;			
//		}
//		return null;
//	}	
	
//	private void cargarListaTablaDocumentoGeneral() throws Exception{
//		Bancofondo bancoFondo = controlFondosFijos.getBancoFondo();
//		List<Fondodetalle> listaFondoDetalleDocumento = new ArrayList<Fondodetalle>();
//		for(Fondodetalle fondoDetalle : bancoFondo.getListaFondodetalle()){
//			if(fondoDetalle.getIntCodigodetalle().equals(Constante.CODIGODETALLE_DOCUMENTO)){
//				if(fondoDetalle.getIntTotalsucursalCod()==null 
//				&& fondoDetalle.getIntIdsucursal().equals(sucursalUsuario.getId().getIntIdSucursal())
//				&& fondoDetalle.getIntIdsubsucursal().equals(subsucursalUsuario.getId().getIntIdSubSucursal())){
//					listaFondoDetalleDocumento.add(fondoDetalle);
//				}
//				if(fondoDetalle.getIntTotalsucursalCod()!=null 
//				&& empresaFacade.validarTotalSucursal(sucursalUsuario.getIntIdTipoSucursal(), fondoDetalle.getIntTotalsucursalCod())){
//				//&& validarTotalSucursal(fondoDetalle.getIntTotalsucursalCod())){
//					listaFondoDetalleDocumento.add(fondoDetalle);
//				}				
//			}
//		}
//		
//		HashSet<Integer> hashIdDocumentoGeneral = new HashSet<Integer>();
//		for(Fondodetalle fondoDetalleDocumento : listaFondoDetalleDocumento){
//			hashIdDocumentoGeneral.add(fondoDetalleDocumento.getIntDocumentogeneralCod());			
//		}
//		for(Integer intIdDocumentoGeneral : hashIdDocumentoGeneral){
//			Tabla tabla = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL),intIdDocumentoGeneral);			
//			if(!listaTablaDocumentoGeneral.contains(tabla))		listaTablaDocumentoGeneral.add(tabla);
//		}
//		//listaTablaDocumentoGeneral = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL));
//	}
	
	private void limpiarFormulario(){
//		listaTablaDocumentoGeneral = new ArrayList<Tabla>();
		personaSeleccionada = null;
		documentoGeneralSeleccionado = null;
		listaDocumentoAgregados = new ArrayList<DocumentoGeneral>();
		listaEgresoDetalleInterfazAgregado = new ArrayList<EgresoDetalleInterfaz>();
		listaBeneficiarioPrevision = null;
		personaApoderado = null;
		archivoCartaPoder = null;
		strListaPersonaRolUsar = "";
		strObservacion = "";
		intBeneficiarioSeleccionar = null;
		setCuentaActual(null);
		bdMontoGirar = null;
		strMontoGirarDescripcion = "";
		libroDiario = null;
		strDiferencialGirarDescripcion = "";
		bdDiferencialGirar = null;
		cuentaBancariaSeleccionada = null;
//		blnActivarNroApertura = true;
		listaTablaTipoDocumento.clear();
	}
	
//	public void validarDatos(){
//		String mensaje = "";
//		strMensajeErrorPorBeneficiario = "";
//		blnBeneficiarioSinAutorizacion = false;
//		try{
//			log.info("intTipoFondoFijoValidar:"+intTipoFondoFijoValidar);
////			datosValidados = Boolean.FALSE;
//			habilitarGrabar = Boolean.FALSE;
//			deshabilitarNuevo = Boolean.FALSE;
//			limpiarFormulario();
//			
//			if(intTipoFondoFijoValidar.equals(new Integer(0)))
//				return;			
//			//Autor: jchavez / Tarea: Modificación / Fecha: 19.08.2014 / 
//			if (intTipoFondoFijoValidar.equals(Constante.PARAM_T_TIPOFONDOFIJO_PLANILLATELECREDITO)) {
////				blnActivarNroApertura = false;
//				//pergunta: no se va a obtener el control de fondos fijos????
//			}else{
//				if(intControlSeleccionado.equals(new Integer(0)))
//					return;				
//				controlFondosFijos = obtenerControlFondosFijosSeleccionado();
//				log.info(controlFondosFijos);
//				controlFondosFijos.setEgreso(egresoFacade.getEgresoPorControlFondosFijos(controlFondosFijos));
//				controlFondosFijos.setBancoFondo(bancoFacade.obtenerBancoFondoPorControl(controlFondosFijos));
//				controlFondosFijos.setSucursal(usuarioSesion.getSucursal());
//				controlFondosFijos.setSubsucursal(usuarioSesion.getSubSucursal());
////				cargarListaTablaDocumentoGeneral();
//			}			
//			
//			egresoNuevo = new Egreso();
//			egresoNuevo.setDtFechaEgreso(MyUtil.obtenerFechaActual());
//			
////			datosValidados = Boolean.TRUE;
//			habilitarGrabar = Boolean.TRUE;
//		}catch (Exception e) {
//			log.error(e.getMessage(),e);
//		}finally{
////			if(datosValidados)	ocultarMensaje();
////			else	mostrarMensaje(datosValidados, mensaje);			
//		}
//	}
	/**Transferencia con Fondos Fijos**/
		
	public void abrirPopUpBuscarPersona(){
		try{
			intTipoBuscarPersona = BUSCAR_PERSONA;
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = "";
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void abrirPopUpBuscarApoderado(){
		try{
			intTipoBuscarPersona = BUSCAR_APODERADO;
			listaPersona = new ArrayList<Persona>();
			strFiltroTextoPersona = "";
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void quitarPersonaSeleccionada(){
		try{
			personaSeleccionada = null;
			documentoGeneralSeleccionado = null;
			personaApoderado = null;
			archivoCartaPoder = null;
			cuentaBancariaSeleccionada = null;
//			limpiarFormulario();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	public void buscarPersona(){
		try{
			listaPersona = new ArrayList<Persona>();
			Persona persona = null;
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						strFiltroTextoPersona, 
						SESION_IDEMPRESA);
			
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				persona = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(strFiltroTextoPersona,SESION_IDEMPRESA);
			}
			
			if(persona!=null)listaPersona.add(persona);
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
		for(Documento documento : persona.getListaDocumento())
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI))
				persona.setDocumento(documento);
	}
	
	public void quitarPersonaApoderado(){
		try{
			personaApoderado = null;
			archivoCartaPoder = null;
			((FileUploadController)getSessionBean("fileUploadController")).setArchivoCartaPoder(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarAdjuntarCartaPoder(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			archivoCartaPoder = fileUploadController.getArchivoCartaPoder();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void quitarCartaPoder(){
		try{
			archivoCartaPoder = null;
			((FileUploadController)getSessionBean("fileUploadController")).setArchivoCartaPoder(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
//	private void manejarSocioPersona(List<PersonaRol> listaRol)throws Exception{
//		try{
//			for(PersonaRol personaRol : listaRol)
//				if(personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_SOCIO))
//					cuentaActual = prestamoFacade.getCuentaActualPorIdPersona(personaSeleccionada.getIntIdPersona(), EMPRESA_USUARIO);
//		}catch(Exception e){
//			log.error(e.getMessage(),e);
//		}
//	}
	
	private void mostrarRolPersona() throws Exception{
		strListaPersonaRolUsar = "";
		List<Tabla> listaTablaRolPermitidos = new ArrayList<Tabla>();
		listaPersonaRolUsar = new ArrayList<PersonaRol>();
		try {
			if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				listaTablaRolPermitidos = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL), "F");
				
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
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

		} catch (Exception e) {
			log.info("Error en mostrarRolPersona() ---> "+e.getMessage());
		}
		
	}
	
	public void seleccionarPersona(ActionEvent event){
		String strTipoCuenta = "";
		String strTipoMoneda = "";
		try{
			log.info("intTipoBuscarPersona:"+intTipoBuscarPersona);
			if(intTipoBuscarPersona.equals(BUSCAR_PERSONA)){
				personaSeleccionada = (Persona)event.getComponent().getAttributes().get("item");
				if(personaSeleccionada.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
					agregarNombreCompleto(personaSeleccionada);
					agregarDocumentoDNI(personaSeleccionada);
				}				
			
			}else if(intTipoBuscarPersona.equals(BUSCAR_APODERADO)){
				personaApoderado = (Persona)event.getComponent().getAttributes().get("item");
				agregarNombreCompleto(personaApoderado);
				agregarDocumentoDNI(personaApoderado);
			}
			if (personaSeleccionada.getListaCuentaBancaria()!=null && !personaSeleccionada.getListaCuentaBancaria().isEmpty()) {
				List<CuentaBancaria> lstCtaBancaria = new ArrayList<CuentaBancaria>();
				for (CuentaBancaria x : personaSeleccionada.getListaCuentaBancaria()) {
					if (x.getIntBancoCod().equals(Constante.PARAM_T_BANCO_BANCODECREDITO)) {
						lstCtaBancaria.add(x);
						for (Tabla ctaBca : listaTablaTipoCuentaBancaria) {
							if (ctaBca.getIntIdDetalle().equals(x.getIntTipoCuentaCod())) {
								strTipoCuenta = "Tipo Cuenta: "+ctaBca.getStrDescripcion();
								break;
							}
						}
						for (Tabla tipMda : listaTablaTipoMoneda) {
							if (tipMda.getIntIdDetalle().equals(x.getIntMonedaCod())) {
								strTipoMoneda = " - Moneda: "+tipMda.getStrDescripcion();
								break;
							}
						}
						x.setStrEtiqueta(strTipoCuenta+strTipoMoneda+" - Nro. Cuenta: "+x.getStrNroCuentaBancaria());
					}
				}
				personaSeleccionada.getListaCuentaBancaria().clear();
				if (!lstCtaBancaria.isEmpty()) {
					personaSeleccionada.getListaCuentaBancaria().addAll(lstCtaBancaria);
				}
			}
			mostrarRolPersona();
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	

	public void abrirPopUpBuscarDocumento(){
		strMensajeError = "";
		try{
			listaDocumentoPorAgregar = new ArrayList<DocumentoGeneral>();
			Integer intIdPersona = personaSeleccionada.getIntIdPersona();
			
			log.info("intTipoDocumentoAgregar:"+intTipoDocumentoAgregar);
			
			if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				List<Movilidad> listaMovilidad = egresoFacade.buscarMovilidadParaGiroDesdeFondo(intIdPersona, SESION_IDEMPRESA);
				for(Movilidad movilidad : listaMovilidad){
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setIntTipoDocumento(intTipoDocumentoAgregar);
					documentoGeneral.setStrNroDocumento(""+movilidad.getId().getIntItemMovilidad());
					documentoGeneral.setSucursal(movilidad.getSucursal());
					documentoGeneral.setSubsucursal(movilidad.getSubsucursal());
					documentoGeneral.setBdMonto(movilidad.getBdMontoAcumulado());
					
					documentoGeneral.setMovilidad(movilidad);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
			
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				List<ExpedienteCredito> listaExpedienteCredito = prestamoFacade.obtenerExpedientePorIdPersonayIdEmpresa(intIdPersona, SESION_IDEMPRESA);
				for(ExpedienteCredito expedienteCredito : listaExpedienteCredito){
					EstadoCredito estadoCredito = expedienteCredito.getListaEstadoCredito().get(0);
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setIntTipoDocumento(intTipoDocumentoAgregar);
					documentoGeneral.setStrNroDocumento(expedienteCredito.getId().getIntItemExpediente()+"-"+expedienteCredito.getId().getIntItemDetExpediente());
					documentoGeneral.setSucursal(estadoCredito.getSucursal());
					documentoGeneral.setSubsucursal(estadoCredito.getSubsucursal());
					documentoGeneral.setBdMonto(expedienteCredito.getBdMontoTotal());
					
					documentoGeneral.setExpedienteCredito(expedienteCredito);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
				|| intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				List<ExpedientePrevision> listaExpedientePrevision = previsionFacade.buscarExpedienteParaGiroDesdeFondo(intIdPersona, 
						SESION_IDEMPRESA, intTipoDocumentoAgregar);
				for(ExpedientePrevision expedientePrevision : listaExpedientePrevision){
					EstadoPrevision estadoPrevision = expedientePrevision.getEstadoPrevisionUltimo();
					if (estadoPrevision.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICITUD_APROBADO)) {
						DocumentoGeneral documentoGeneral = new DocumentoGeneral();
						documentoGeneral.setIntTipoDocumento(expedientePrevision.getIntParaDocumentoGeneral());
						documentoGeneral.setStrNroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
						documentoGeneral.setSucursal(estadoPrevision.getSucursal());
						documentoGeneral.setSubsucursal(estadoPrevision.getSubsucursal());
						documentoGeneral.setBdMonto(expedientePrevision.getBdMontoBrutoBeneficio());
						
						documentoGeneral.setExpedientePrevision(expedientePrevision);
						listaDocumentoPorAgregar.add(documentoGeneral);
					}

				}
				
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				List<ExpedienteLiquidacion> listaExpedienteLiquidacion = liquidacionFacade.buscarExpedienteParaGiroDesdeFondo(intIdPersona, 
						SESION_IDEMPRESA);
				for(ExpedienteLiquidacion expedienteLiquidacion : listaExpedienteLiquidacion){
					EstadoLiquidacion estadoLiquidacion = expedienteLiquidacion.getEstadoLiquidacionUltimo();
					if (estadoLiquidacion.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICITUD_APROBADO)) {
						DocumentoGeneral documentoGeneral = new DocumentoGeneral();
						documentoGeneral.setIntTipoDocumento(expedienteLiquidacion.getIntParaDocumentoGeneral());
						documentoGeneral.setStrNroDocumento(""+expedienteLiquidacion.getId().getIntItemExpediente());
						documentoGeneral.setSucursal(estadoLiquidacion.getSucursal());
						documentoGeneral.setSubsucursal(estadoLiquidacion.getSubsucursal());
						documentoGeneral.setBdMonto(expedienteLiquidacion.getBdMontoBrutoLiquidacion());
						
						documentoGeneral.setExpedienteLiquidacion(expedienteLiquidacion);
						listaDocumentoPorAgregar.add(documentoGeneral);
					}					
				}
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS)){
				List<DocumentoSunat> listaDocumentoSunat = logisticaFacade.buscarDocumentoSunatParaGiroDesdeFondo(intIdPersona, SESION_IDEMPRESA);
				for(DocumentoSunat documentoSunat : listaDocumentoSunat){
					log.info(documentoSunat);
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setIntTipoDocumento(documentoSunat.getIntParaDocumentoGeneral());
					documentoGeneral.setStrNroDocumento(""+documentoSunat.getId().getIntItemDocumentoSunat());
					documentoGeneral.setBdMonto(documentoSunat.getDetalleTotalGeneral().getBdMontoTotal());
					documentoSunat.setProveedor(personaSeleccionada);
					documentoGeneral.setDocumentoSunat(documentoSunat);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
			}//Autor: jchavez / Tarea: Creación / Fecha: 11.10.2014
			else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)){
				List<OrdenCompra> listaOrdenCompra = logisticaFacade.buscarDocumentoAdelantoGarantiaParaGiroPorTesoreria(intIdPersona, SESION_IDEMPRESA, Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO);
				for (OrdenCompra ordenCompra : listaOrdenCompra) {
					log.info(ordenCompra);
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setIntTipoDocumento(ordenCompra.getIntParaTipoDocumentoGeneral());
					documentoGeneral.setStrNroDocumento(""+ordenCompra.getId().getIntItemOrdenCompra());
					BigDecimal bdMonto = BigDecimal.ZERO;
					for (OrdenCompraDetalle ordCmpDet : ordenCompra.getListaOrdenCompraDetalle()) {
						bdMonto = bdMonto.add(ordCmpDet.getBdPrecioTotal());
					}
					documentoGeneral.setBdMonto(bdMonto);
					documentoGeneral.setOrdenCompra(ordenCompra);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
			}else if(intTipoDocumentoAgregar.equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA)){
				List<OrdenCompra> listaOrdenCompra = logisticaFacade.buscarDocumentoAdelantoGarantiaParaGiroPorTesoreria(intIdPersona, SESION_IDEMPRESA, Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA);
				for (OrdenCompra ordenCompra : listaOrdenCompra) {
					log.info(ordenCompra);
					DocumentoGeneral documentoGeneral = new DocumentoGeneral();
					documentoGeneral.setIntTipoDocumento(ordenCompra.getIntParaTipoDocumentoGeneral());
					documentoGeneral.setStrNroDocumento(""+ordenCompra.getId().getIntItemOrdenCompra());
					BigDecimal bdMonto = BigDecimal.ZERO;
					for (OrdenCompraDetalle ordCmpDet : ordenCompra.getListaOrdenCompraDetalle()) {
						bdMonto = bdMonto.add(ordCmpDet.getBdPrecioTotal());
					}
					documentoGeneral.setBdMonto(bdMonto);
					documentoGeneral.setOrdenCompra(ordenCompra);
					listaDocumentoPorAgregar.add(documentoGeneral);
				}
			}
			//Fin jchavez - 11.10.2014
			
			listaDocumentoPorAgregar = egresoFacade.filtrarDuplicidadDocumentoGeneralParaEgreso(listaDocumentoPorAgregar, 
					intTipoDocumentoAgregar, listaDocumentoAgregados);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	private void cargarListaBeneficiarioPrevision()throws Exception{
		listaBeneficiarioPrevision = new ArrayList<BeneficiarioPrevision>();
		List<BeneficiarioPrevision> listaBeneficiarioPrevisionBD = previsionFacade.getListaBeneficiarioPrevision(
				documentoGeneralSeleccionado.getExpedientePrevision());
		
		for(BeneficiarioPrevision beneficiarioPrevision : listaBeneficiarioPrevisionBD){
			if(beneficiarioPrevision.getIntItemEgresoGeneral()!=null && deshabilitarNuevo==Boolean.FALSE)	continue;			
			
			BigDecimal bdPorcentaje = beneficiarioPrevision.getBdPorcentajeBeneficio().divide(new BigDecimal(100));			
			BigDecimal bdCapital = documentoGeneralSeleccionado.getExpedientePrevision().getBdMontoBrutoBeneficio().multiply(bdPorcentaje);			
			
			Persona persona = personaFacade.devolverPersonaCargada(beneficiarioPrevision.getIntPersPersonaBeneficiario());
			String strEtiqueta = ""+beneficiarioPrevision.getId().getIntItemBeneficiario();
			strEtiqueta = strEtiqueta + " - " + persona.getNatural().getStrNombreCompleto();
			strEtiqueta = strEtiqueta + " - DNI:" + persona.getDocumento().getStrNumeroIdentidad();
			if(beneficiarioPrevision.getIntItemViculo()!=null){
				strEtiqueta = strEtiqueta + " - VINCULO:"+MyUtil.obtenerEtiquetaTabla(beneficiarioPrevision.getIntItemViculo(),listaTipoVinculo);
			}
			strEtiqueta = strEtiqueta + " - " + beneficiarioPrevision.getBdPorcentajeBeneficio()+"%  " + formato.format(bdCapital);			
			
			beneficiarioPrevision.setStrEtiqueta(strEtiqueta);
			beneficiarioPrevision.setPersona(persona);
			
			listaBeneficiarioPrevision.add(beneficiarioPrevision);
		}
		documentoGeneralSeleccionado.getExpedientePrevision().setListaBeneficiarioPrevision(listaBeneficiarioPrevision);
	}
	
	private void cargarListaBeneficiarioLiquidacion()throws Exception{
		ExpedienteLiquidacion expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
		listaBeneficiarioPersona = new ArrayList<Persona>();
		listaBeneficiarioLiquidacion = new ArrayList<BeneficiarioLiquidacion>();
		
		List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionTemp = new ArrayList<BeneficiarioLiquidacion>();
		for(ExpedienteLiquidacionDetalle expedienteLiquidacionDetalle : expedienteLiquidacion.getListaExpedienteLiquidacionDetalle()){
			log.info(expedienteLiquidacionDetalle);
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacionTemp2 = liquidacionFacade.getListaBeneficiarioLiquidacionPorExpedienteLiquidacionDetalle(expedienteLiquidacionDetalle); 
			expedienteLiquidacionDetalle.setListaBeneficiarioLiquidacion(listaBeneficiarioLiquidacionTemp2);
			listaBeneficiarioLiquidacionTemp.addAll(listaBeneficiarioLiquidacionTemp2);
		}
		//Cargamos la lista de los beneficiarios
		listaBeneficiarioLiquidacion.addAll(listaBeneficiarioLiquidacionTemp);
		
		HashSet<Integer> hashIntIdPersona = new HashSet<Integer>();
		for(BeneficiarioLiquidacion beneficiarioLiquidacion : listaBeneficiarioLiquidacionTemp){
			log.info(beneficiarioLiquidacion);
			//Solo listaremos los beneficiarios que aun no hayan girado
			if(beneficiarioLiquidacion.getIntItemEgresoGeneral()==null || deshabilitarNuevo==Boolean.TRUE){
				hashIntIdPersona.add(beneficiarioLiquidacion.getIntPersPersonaBeneficiario());
			}
		}
		
		List<Persona> listaPersona = new ArrayList<Persona>();
		for(Integer intIdPersona : hashIntIdPersona){
			log.info(intIdPersona);
			Integer intItemVinculo = null;
			for(BeneficiarioLiquidacion beneficiarioLiquidacion : listaBeneficiarioLiquidacionTemp){
				log.info(beneficiarioLiquidacion);
				if(beneficiarioLiquidacion.getIntPersPersonaBeneficiario().equals(intIdPersona)){
					intItemVinculo = beneficiarioLiquidacion.getIntItemViculo();
					//en teoria aca tomo el valor del beneficiario seleccionado.
					beneficiarioLiquidacionSeleccionado= beneficiarioLiquidacion;
					break;
				}
			}
			String strEtiqueta = "";
			Persona persona = devolverPersonaCargada(intIdPersona);
			strEtiqueta = intItemVinculo + " - " + persona.getNatural().getStrNombreCompleto();
			strEtiqueta = strEtiqueta + " - DNI : " + persona.getDocumento().getStrNumeroIdentidad();
			strEtiqueta = strEtiqueta + " - VINCULO : "+obtenerEtiquetaVinculo(intItemVinculo);
			persona.setStrEtiqueta(strEtiqueta);
			
			listaPersona.add(persona);
		}
		expedienteLiquidacion.setListaPersona(listaPersona);
		listaBeneficiarioPersona.addAll(listaPersona);
	}
	
	//10.06.2014
	private Persona devolverPersonaCargada(Integer intIdPersona) throws Exception{
		//log.info(intIdPersona);
		Persona persona = personaFacade.getPersonaPorPK(intIdPersona);
		if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
			persona = personaFacade.getPersonaNaturalPorIdPersona(persona.getIntIdPersona());			
			agregarDocumentoDNI(persona);
			agregarNombreCompleto(persona);			
		
		}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
			persona.setJuridica(personaFacade.getJuridicaPorPK(persona.getIntIdPersona()));			
		}
		
		return persona;
	}
	
	//10.06.2014
	private String obtenerEtiquetaVinculo(Integer intItemVinculo) throws Exception{
		Vinculo vinculo = personaFacade.getVinculoPorId(intItemVinculo);
		for(Tabla tabla : listaTipoVinculo){
			if(tabla.getIntIdDetalle().equals(vinculo.getIntTipoVinculoCod())){
				return tabla.getStrDescripcion();
			}
		}
		return null;
	}
	
	public void seleccionarDocumento(ActionEvent event){
		blnBeneficiarioSinAutorizacion = false;
		documentoGeneralSeleccionado = null;
		blnNoExisteRequisito = false;
		try{
			documentoGeneralSeleccionado = (DocumentoGeneral)event.getComponent().getAttributes().get("item");
			
			String strEtiqueta = obtenerEtiquetaTipoDocumentoGeneral(documentoGeneralSeleccionado.getIntTipoDocumento()) 
				+ " - "+ documentoGeneralSeleccionado.getStrNroDocumento() + " - ";
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				strEtiqueta = strEtiqueta + obtenerEtiquetaTipoMoneda(Constante.PARAM_T_TIPOMONEDA_SOLES)+" ";
			}
			//Autor: jchavez / Tarea: Creacion / Fecha: 15.10.2014
			if(documentoGeneralSeleccionado.getOrdenCompra()!=null && 
					(documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)
							|| documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA))){
				strEtiqueta = strEtiqueta + obtenerEtiquetaTipoMoneda(documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaTipoMoneda())+" ";
			}//Fin jchavez - 15.10.2014
			strEtiqueta = strEtiqueta + formato.format(documentoGeneralSeleccionado.getBdMonto());
			
			documentoGeneralSeleccionado.setStrEtiqueta(strEtiqueta);
			//rqcuperamos el adjunto de giro para un prestamo
			if (documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)) {
				archivoAdjuntoGiro = documentoGeneralSeleccionado.getExpedienteCredito().getArchivoGiro();
			}
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
			|| documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
			|| documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				cargarListaBeneficiarioPrevision();
				//obtener el mov cta por pagar de prevision. jchavez 20.05.2014
				buscarMovCtaCtePorPagarPrevision(documentoGeneralSeleccionado.getExpedientePrevision());
				blnBeneficiarioSinAutorizacion = true;
			
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				cargarListaBeneficiarioLiquidacion();
				buscarMovCtaCtePorPagarLiquidacion(documentoGeneralSeleccionado.getExpedienteLiquidacion());
			}
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				if(validarNoExisteRequisitoCredito()){
					documentoGeneralSeleccionado = null;
					strMensajeError = "Préstamo no cuenta con documento de autorizacion de giro";
				}else{
					strMensajeError = "";
					deshabilitarNuevo = false;
					blnVerBotonApoderado = true;
				}
			}
			log.info("mensajito: "+strMensajeError);
			log.info("No Existe Requisito: "+blnNoExisteRequisito);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	private String obtenerEtiquetaTipoDocumentoGeneral(Integer intTipoDocumento){
		for(Tabla tabla : listaTablaDocumentoGeneral){
			if(tabla.getIntIdDetalle().equals(intTipoDocumento)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	private String obtenerEtiquetaTipoMoneda(Integer intTipoMoneda){
		for(Tabla tabla : listaMoneda){
			if(tabla.getIntIdDetalle().equals(intTipoMoneda)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	/**
	 * Metodo que recupera el movimiento generado en cuenta por pagar
	 */
	public void buscarMovCtaCtePorPagarPrevision(ExpedientePrevision expPrev) throws Exception {
		List<Movimiento> lstMov = null;
		Integer intParaTipoConcepto = null;

		try {
			switch (expPrev.getIntParaDocumentoGeneral()) {
			case (102): //Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO
				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_SEPELIO;
				break;
			case (103): //Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO
				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_RETIRO;
				break;
//			case Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO:
//				intParaTipoConcepto = Constante.PARAM_T_CUENTACONCEPTO_SEPELIO;
//				break;				
			default:
				break;
			}
			lstMov = conceptoFacade.getListaMovVtaCtePorPagar(expPrev.getId().getIntPersEmpresaPk(), expPrev.getId().getIntCuentaPk(), expPrev.getId().getIntItemExpediente(), intParaTipoConcepto,expPrev.getIntParaDocumentoGeneral());
			if (lstMov!=null && !lstMov.isEmpty()) {
				for (Movimiento movimiento : lstMov) {
					documentoGeneralSeleccionado.getExpedientePrevision().setMovimiento(movimiento);
					break;
				}
			}else documentoGeneralSeleccionado.getExpedientePrevision().setMovimiento(null);
		} catch (Exception e) {
			log.error("Error al buscarMovCtaCtePorPagar: "+e.getMessage(),e);
		}
		
	}
	
	public void buscarMovCtaCtePorPagarLiquidacion(ExpedienteLiquidacion expLiq) throws Exception {
		List<Movimiento> lstMov = null;

		try {
			lstMov = conceptoFacade.getListaMovCtaCtePorPagarLiq(expLiq.getId().getIntPersEmpresaPk(), expLiq.getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta(), expLiq.getId().getIntItemExpediente(), expLiq.getIntParaDocumentoGeneral());
			if (lstMov!=null && !lstMov.isEmpty()) {
				for (Movimiento movimiento : lstMov) {
					documentoGeneralSeleccionado.getExpedienteLiquidacion().setMovimiento(movimiento);
					break;
				}
			}else documentoGeneralSeleccionado.getExpedienteLiquidacion().setMovimiento(null);
		} catch (Exception e) {
			log.error("Error al buscarMovCtaCtePorPagar: "+e.getMessage(),e);
		}
		
	}
	
	private void manejarAgregarExpedientePrevision() throws Exception{
		ExpedientePrevision expedientePrevision = documentoGeneralSeleccionado.getExpedientePrevision();
		BeneficiarioPrevision beneficiarioPrevisionSeleccionado = null;
		for(BeneficiarioPrevision beneficiarioPrevision : listaBeneficiarioPrevision){
			if(listaBeneficiarioPrevision.size()==1){
				beneficiarioPrevision.setEsUltimoBeneficiarioAGirar(Boolean.TRUE);
			}else{
				beneficiarioPrevision.setEsUltimoBeneficiarioAGirar(Boolean.FALSE);
			}
			if(beneficiarioPrevision.getId().getIntItemBeneficiario().equals(intBeneficiarioSeleccionar)){
				beneficiarioPrevisionSeleccionado = beneficiarioPrevision;
				break;
			}
		}

		beneficiarioPrevisionSeleccionado.setPersonaApoderado(personaApoderado);
		beneficiarioPrevisionSeleccionado.setArchivoCartaPoder(archivoCartaPoder);
		expedientePrevision.setBeneficiarioPrevisionGirar(beneficiarioPrevisionSeleccionado);		
		
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = previsionFacade.
										cargarListaEgresoDetalleInterfaz(expedientePrevision, beneficiarioPrevisionSeleccionado);
		expedientePrevision.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
	}
	
	private void manejarAgregarExpedienteLiquidacion() throws Exception{
		ExpedienteLiquidacion expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
		
		if(listaBeneficiarioPersona.size()==1){
			expedienteLiquidacion.setEsUltimoBeneficiarioAGirar(Boolean.TRUE);
		}else{
			expedienteLiquidacion.setEsUltimoBeneficiarioAGirar(Boolean.FALSE);
		}
		
		expedienteLiquidacion.setIntIdPersonaBeneficiarioGirar(intBeneficiarioSeleccionar);
		
		List<EgresoDetalleInterfaz> listaEgresoDetalleInterfaz = liquidacionFacade.
					cargarListaEgresoDetalleInterfaz(expedienteLiquidacion.getListaExpedienteLiquidacionDetalle(), intBeneficiarioSeleccionar);
		expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEgresoDetalleInterfaz);
	}
	
	private SocioEstructura obtenerSocioEstructura(Persona persona, Integer intIdEmpresa) throws Exception{
		SocioFacadeRemote socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
		List<SocioEstructura> lista = socioFacade.getListaSocioEstrucuraPorIdPersona(persona.getIntIdPersona(), intIdEmpresa);		
		SocioEstructura socioEstructura = lista.get(0);
		
		Sucursal sucursal = new Sucursal();
		sucursal.getId().setIntPersEmpresaPk(intIdEmpresa);
		sucursal.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
		sucursal = empresaFacade.getSucursalPorPK(sucursal);
		
		Subsucursal subsucursal = empresaFacade.getSubSucursalPorIdSubSucursal(socioEstructura.getIntIdSubsucurAdministra());
		
		socioEstructura.setSucursal(sucursal);
		socioEstructura.setSubsucursal(subsucursal);
		
		return socioEstructura;
	}	
	
	private boolean validarMontosExpedienteCredito(ExpedienteCredito expedienteCredito) throws Exception{
		boolean exito = Boolean.FALSE;
		BigDecimal bdMontoCanceladoAcumulado = new BigDecimal(0);
		for(CancelacionCredito cancelacionCredito : expedienteCredito.getListaCancelacionCredito()){
			bdMontoCanceladoAcumulado = bdMontoCanceladoAcumulado.add(cancelacionCredito.getBdMontoCancelado());
		}
		
		if(expedienteCredito.getBdMontoTotal()==null){
			return exito;//FALSE
		}
		
		BigDecimal bdMontoValidar = new BigDecimal(0);
		bdMontoValidar = bdMontoValidar.add(bdMontoCanceladoAcumulado);
		if(expedienteCredito.getBdMontoGravamen()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoGravamen());
		}
		if(expedienteCredito.getBdMontoAporte()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoAporte());
		}
		if(expedienteCredito.getBdMontoInteresAtrasado()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoInteresAtrasado());
		}
		if(expedienteCredito.getBdMontoSolicitado()!=null){
			bdMontoValidar = bdMontoValidar.add(expedienteCredito.getBdMontoSolicitado());
		}else{
			return exito;//FALSE
		}
		log.info("total : "+expedienteCredito.getBdMontoTotal());
		log.info("valid : "+bdMontoValidar);
		if(!expedienteCredito.getBdMontoTotal().equals(bdMontoValidar)){
			return exito;//FALSE
		}
		exito = Boolean.TRUE;
		return exito;
	}
	
	public void agregarDocumento(){
		deshabilitarNuevo = false;
		try{
			if(documentoGeneralSeleccionado.getBdMonto()==null){
				return;
			}
			
			int intOrden = 1;			
			if(bdMontoGirar!=null && bdMontoGirar.signum()>0){
				EgresoDetalleInterfaz ultimoEDI = listaEgresoDetalleInterfazAgregado.get(listaEgresoDetalleInterfazAgregado.size()-1);
				intOrden = ultimoEDI.getIntOrden() + 1;
			}else{
				bdMontoGirar = new BigDecimal(0);
				bdDiferencialGirar = new BigDecimal(0);
			}
			
			if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD)){
				Movilidad movilidad = documentoGeneralSeleccionado.getMovilidad();
				List<EgresoDetalleInterfaz> listaEDI = egresoFacade.cargarListaEgresoDetalleInterfaz(movilidad);
				movilidad.setListaEgresoDetalleInterfaz(listaEDI);
				movilidad.setPersona(personaSeleccionada);
				for(EgresoDetalleInterfaz eDI : listaEDI){
					eDI.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PLANILLAMOVILIDAD);
					eDI.setStrNroDocumento(""+movilidad.getId().getIntItemMovilidad());
					eDI.setPersona(personaSeleccionada);
					eDI.setSucursal(movilidad.getSucursal());
					eDI.setSubsucursal(movilidad.getSubsucursal());
					eDI.setStrDescripcion(MyUtil.obtenerEtiquetaTabla(eDI.getIntParaConcepto(),listaTablaTipoMovilidad));
					eDI.setBdSubTotal(eDI.getBdMonto());
					eDI.setLibroDiario(libroDiario);
					
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
				
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS)){
				if(personaApoderado!=null && archivoCartaPoder==null){
					mostrarMensaje(Boolean.FALSE, "Debe adjuntar una carta de poder al apoderado.");
					return;
				}
				ExpedienteCredito expedienteCredito = documentoGeneralSeleccionado.getExpedienteCredito();
				log.info(personaSeleccionada);
				log.info(personaApoderado);
				log.info(archivoCartaPoder);
				expedienteCredito.setPersonaGirar(personaSeleccionada);
				expedienteCredito.setPersonaApoderado(personaApoderado);
				expedienteCredito.setArchivoCartaPoder(archivoCartaPoder);
				SocioEstructura socioEstructura = obtenerSocioEstructura(personaSeleccionada, SESION_IDEMPRESA);
				
				expedienteCredito.setListaCancelacionCredito(prestamoFacade.getListaCancelacionCreditoPorExpedienteCredito(expedienteCredito));
				List<EgresoDetalleInterfaz> listaEDI = prestamoFacade.cargarListaEgresoDetalleInterfaz(expedienteCredito);
				expedienteCredito.setListaCancelacionCredito(
						prestamoFacade.getListaCancelacionCreditoPorExpedienteCredito(expedienteCredito));
				if(!validarMontosExpedienteCredito(documentoGeneralSeleccionado.getExpedienteCredito())){
					mostrarMensaje(Boolean.FALSE, "Hay un error con los montos del expediente de crédito.");
					return;
				}
				for(EgresoDetalleInterfaz eDI : listaEDI){
					eDI.setIntParaDocumentoGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_PRESTAMOS);
					eDI.setStrNroDocumento(""+expedienteCredito.getId().getIntItemExpediente());
					eDI.setPersona(personaSeleccionada);
					eDI.setSucursal(socioEstructura.getSucursal());
					eDI.setSubsucursal(socioEstructura.getSubsucursal());
					eDI.setStrDescripcion(MyUtil.obtenerEtiquetaTabla(eDI.getIntParaDocumentoGeneral(), listaTablaDocumentoGeneral));
					eDI.setBdMonto(eDI.getBdSubTotal());
					eDI.setLibroDiario(libroDiario);
					eDI.setArchivoAdjuntoGiro(archivoAdjuntoGiro);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES) 
					|| documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)
					|| documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
				if(personaApoderado!=null && archivoCartaPoder==null){
					mostrarMensaje(Boolean.FALSE, "Debe adjuntar una carta de poder al apoderado.");
					return;
				}
				manejarAgregarExpedientePrevision();
				ExpedientePrevision expedientePrevision = documentoGeneralSeleccionado.getExpedientePrevision();
				SocioEstructura socioEstructura = obtenerSocioEstructura(personaSeleccionada, SESION_IDEMPRESA);				
				List<EgresoDetalleInterfaz> listaEDI = expedientePrevision.getListaEgresoDetalleInterfaz();
				expedientePrevision.setPersonaGirar(personaSeleccionada);
				expedientePrevision.setListaEgresoDetalleInterfaz(listaEDI);
				 
				for(EgresoDetalleInterfaz eDI : listaEDI){
					eDI.setIntParaDocumentoGeneral(expedientePrevision.getIntParaDocumentoGeneral());
					eDI.setStrNroDocumento(""+expedientePrevision.getId().getIntItemExpediente());
					eDI.setPersona(personaSeleccionada);
					eDI.setSucursal(socioEstructura.getSucursal());
					eDI.setSubsucursal(socioEstructura.getSubsucursal());
					eDI.setStrDescripcion(MyUtil.obtenerEtiquetaTabla(eDI.getIntParaDocumentoGeneral(), listaTablaDocumentoGeneral));
					eDI.setBdMonto(eDI.getBdSubTotal());
					eDI.setLibroDiario(libroDiario);
					eDI.setArchivoAdjuntoGiro(archivoAdjuntoGiro);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
				
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_LIQUIDACIONCUENTA)){
				manejarAgregarExpedienteLiquidacion();
				ExpedienteLiquidacion expedienteLiquidacion = documentoGeneralSeleccionado.getExpedienteLiquidacion();
				List<EgresoDetalleInterfaz> listaEDI = expedienteLiquidacion.getListaEgresoDetalleInterfaz(); 
				expedienteLiquidacion.setPersonaGirar(personaSeleccionada);
				expedienteLiquidacion.setListaEgresoDetalleInterfaz(listaEDI);
				
				SocioEstructura socioEstructura = obtenerSocioEstructura(personaSeleccionada, SESION_IDEMPRESA);
				
				for(EgresoDetalleInterfaz eDI : listaEDI){
					eDI.setIntParaDocumentoGeneral(expedienteLiquidacion.getIntParaDocumentoGeneral());
					eDI.setStrNroDocumento(""+expedienteLiquidacion.getId().getIntItemExpediente());
					eDI.setPersona(personaSeleccionada);
					eDI.setSucursal(socioEstructura.getSucursal());
					eDI.setSubsucursal(socioEstructura.getSubsucursal());
					eDI.setStrDescripcion(
							eDI.getExpedienteLiquidacionDetalle().getCuentaConcepto().getDetalle().getCaptacion().getStrDescripcion());
					eDI.setBdMonto(eDI.getBdSubTotal());
					eDI.setLibroDiario(libroDiario);
					eDI.setArchivoAdjuntoGiro(archivoAdjuntoGiro);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}else if(documentoGeneralSeleccionado.getIntTipoDocumento().equals(Constante.PARAM_T_DOCUMENTOGENERAL_COMPRAS)){
				DocumentoSunat documentoSunat = documentoGeneralSeleccionado.getDocumentoSunat();
				List<EgresoDetalleInterfaz> listaEDI = egresoFacade.cargarListaEgresoDetalleInterfaz(documentoSunat);
				for(EgresoDetalleInterfaz eDI : listaEDI){
					log.info(eDI.getIntParaDocumentoGeneral());
					eDI.setPersona(personaSeleccionada);
					eDI.setLibroDiario(libroDiario);
					eDI.setDocumentoSunat(documentoSunat);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
				documentoSunat.setPersonaGirar(personaSeleccionada);
			}//Autor: jchavez / Tarea: Creacion / Fecha: 15.10.2014
			else if(documentoGeneralSeleccionado.getOrdenCompra()!=null && documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_ADELANTO)){
				OrdenCompra ordenCompra = documentoGeneralSeleccionado.getOrdenCompra();
				List<EgresoDetalleInterfaz> listaEDI = egresoFacade.cargarListaEgresoDetalleInterfazOrdenCompra(ordenCompra, intTipoDocumentoAgregar);
				for(EgresoDetalleInterfaz eDI : listaEDI){
					log.info(eDI.getIntParaDocumentoGeneral());
					eDI.setStrDescripcion(MyUtil.obtenerEtiquetaTabla(eDI.getIntParaDocumentoGeneral(), listaTablaDocumentoGeneral));
					eDI.setPersona(personaSeleccionada);
					eDI.setLibroDiario(libroDiario);
					eDI.setOrdenCompra(ordenCompra);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}else if(documentoGeneralSeleccionado.getOrdenCompra()!=null && documentoGeneralSeleccionado.getOrdenCompra().getListaOrdenCompraDocumento().get(0).getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_GARANTIA)){
				OrdenCompra ordenCompra = documentoGeneralSeleccionado.getOrdenCompra();
				List<EgresoDetalleInterfaz> listaEDI = egresoFacade.cargarListaEgresoDetalleInterfazOrdenCompra(ordenCompra, intTipoDocumentoAgregar);
				for(EgresoDetalleInterfaz eDI : listaEDI){
					log.info(eDI.getIntParaDocumentoGeneral());
					eDI.setStrDescripcion(MyUtil.obtenerEtiquetaTabla(eDI.getIntParaDocumentoGeneral(), listaTablaDocumentoGeneral));
					eDI.setPersona(personaSeleccionada);
					eDI.setLibroDiario(libroDiario);
					eDI.setOrdenCompra(ordenCompra);
					listaEgresoDetalleInterfazAgregado.add(eDI);
				}
			}//Fin jchavez - 15.10.2014
			
			
			for(EgresoDetalleInterfaz egresoDetalleInterfaz : listaEgresoDetalleInterfazAgregado){
				if(egresoDetalleInterfaz.getIntOrden()==null){
					egresoDetalleInterfaz.setIntOrden(intOrden);
					if(egresoDetalleInterfaz.isEsDiferencial()==Boolean.FALSE)
						bdMontoGirar = bdMontoGirar.add(egresoDetalleInterfaz.getBdSubTotal());
					else
						bdDiferencialGirar = bdDiferencialGirar.add(egresoDetalleInterfaz.getBdSubTotal());
					intOrden++;
				}
			}
			
			strMontoGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdMontoGirar, Constante.PARAM_T_TIPOMONEDA_SOLES);
			strDiferencialGirarDescripcion = ConvertirLetras.convertirMontoALetras(bdDiferencialGirar, Constante.PARAM_T_TIPOMONEDA_SOLES);
		
			listaDocumentoAgregados.add(documentoGeneralSeleccionado);
			
			//Ordenamos por intOrden
			Collections.sort(listaEgresoDetalleInterfazAgregado, new Comparator<EgresoDetalleInterfaz>(){
				public int compare(EgresoDetalleInterfaz uno, EgresoDetalleInterfaz otro) {
					return uno.getIntOrden().compareTo(otro.getIntOrden());
				}
			});
			
			//CUSI CUSA PARA VER DOCUMENTO DE GIRO
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			Archivo archivoReqGiro = new Archivo();
			archivoReqGiro.setId(new ArchivoId());
			if (lstRequisitoCredito!=null && !lstRequisitoCredito.isEmpty()) {
				for (RequisitoCredito x : lstRequisitoCredito) {
					archivoReqGiro.getId().setIntItemArchivo(x.getIntParaItemArchivo());
					archivoReqGiro.getId().setIntItemHistorico(x.getIntParaItemHistorico());
					archivoReqGiro.getId().setIntParaTipoCod(x.getIntParaTipoArchivoCod());
					archivoReqGiro = generalFacade.getArchivoPorPK(archivoReqGiro.getId());
					listaEgresoDetalleInterfazAgregado.get(0).setArchivoAdjuntoGiro(archivoReqGiro);
				}
			}
			log.info("observacion: "+strObservacion);
			blnVerBotonApoderado = false;
			deshabilitarNuevo = true;
			blnHabilitarObservacion = false;
			ocultarMensaje();
		}catch(Exception e){
			mostrarMensaje(Boolean.FALSE,"Ocurrió un error al agregar el documento.");
			log.error(e.getMessage(), e);
		}
	}	
	
	public void seleccionarCuentaBancaria(ActionEvent event){
		String strTipoCuenta = "";
		String strTipoMoneda = "";
		try{
			cuentaBancariaSeleccionada = (CuentaBancaria)event.getComponent().getAttributes().get("item");
			for (Tabla x : listaTablaTipoCuentaBancaria) {
				if (x.getIntIdDetalle().equals(cuentaBancariaSeleccionada.getIntTipoCuentaCod())) {
					strTipoCuenta = "Tipo Cuenta: "+x.getStrDescripcion();
					break;
				}
			}
			for (Tabla y : listaTablaTipoMoneda) {
				if (y.getIntIdDetalle().equals(cuentaBancariaSeleccionada.getIntMonedaCod())) {
					strTipoMoneda = " - Moneda: "+y.getStrDescripcion();
					break;
				}
			}
			cuentaBancariaSeleccionada.setStrEtiqueta(strTipoCuenta+strTipoMoneda+" - Nro. Cuenta: "+cuentaBancariaSeleccionada.getStrNroCuentaBancaria());
			log.info(personaSeleccionada);
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}

	public void procesarItems(Egreso egreso){ 
		egreso.setStrNumeroEgreso(
				obtenerPeriodoItem(	egreso.getIntItemPeriodoEgreso(), 
									egreso.getIntItemEgreso(), 
									"000000"));
			
		if(egreso.getIntContPeriodoLibro()!=null){
			egreso.setStrNumeroLibro(
					obtenerPeriodoItem(	egreso.getIntContPeriodoLibro(),
										egreso.getIntContCodigoLibro(), 
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
	
	public void obtenerListaTipoDocumentoGeneral(){
		listaTablaTipoDocumento.clear();
		List<Fondodetalle> lstFdoDetalle = null;
		try {
			lstFdoDetalle = bancoFacade.getDocumentoPorFondoFijo(SESION_IDEMPRESA, intTipoFondoFijoValidar, (controlFondosFijos.getIntParaMoneda()!=null?controlFondosFijos.getIntParaMoneda():Constante.PARAM_T_TIPOMONEDA_SOLES));
			if (lstFdoDetalle!=null && !lstFdoDetalle.isEmpty()) {
				for (Fondodetalle fondodetalle : lstFdoDetalle) {
					if (fondodetalle.getIntTotalsucursalCod()!=null) {						
						if (fondodetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_SUCURSALES)) {
							for (Tabla o : listaTablaDocumentoGeneral) {
								if (o.getIntIdDetalle().equals(fondodetalle.getIntDocumentogeneralCod())) {
									listaTablaTipoDocumento.add(o);
									break;
								}
							}
						}else if (fondodetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_AGENCIAS)) {
							if (usuarioSesion.getSucursal().getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_AGENCIA)) {
								for (Tabla o : listaTablaDocumentoGeneral) {
									if (o.getIntIdDetalle().equals(fondodetalle.getIntDocumentogeneralCod())) {
										listaTablaTipoDocumento.add(o);
										break;
									}
								}
							}							
						}else if (fondodetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_FILIALES)) {
							if (usuarioSesion.getSucursal().getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_FILIAL)) {
								for (Tabla o : listaTablaDocumentoGeneral) {
									if (o.getIntIdDetalle().equals(fondodetalle.getIntDocumentogeneralCod())) {
										listaTablaTipoDocumento.add(o);
										break;
									}
								}
							}							
						}else if (fondodetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_OFICINAPRINCIPAL)) {
							if (usuarioSesion.getSucursal().getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_OFICINAPRINCIPAL)) {
								for (Tabla o : listaTablaDocumentoGeneral) {
									if (o.getIntIdDetalle().equals(fondodetalle.getIntDocumentogeneralCod())) {
										listaTablaTipoDocumento.add(o);
										break;
									}
								}
							}							
						}else if (fondodetalle.getIntTotalsucursalCod().equals(Constante.PARAM_T_TOTALESSUCURSALES_SEDE)) {
							if (usuarioSesion.getSucursal().getIntIdTipoSucursal().equals(Constante.PARAM_T_TIPOSUCURSAL_SEDECENTRAL)) {
								for (Tabla o : listaTablaDocumentoGeneral) {
									if (o.getIntIdDetalle().equals(fondodetalle.getIntDocumentogeneralCod())) {
										listaTablaTipoDocumento.add(o);
										break;
									}
								}
							}							
						}
					}else {
						if (fondodetalle.getIntIdsucursal().equals(sucursalUsuario.getId().getIntIdSucursal())
								&& fondodetalle.getIntIdsubsucursal().equals(subsucursalUsuario.getId().getIntIdSubSucursal())) {
							for (Tabla o : listaTablaDocumentoGeneral) {
								if (o.getIntIdDetalle().equals(fondodetalle.getIntDocumentogeneralCod())) {
									listaTablaTipoDocumento.add(o);
									break;
								}
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.info("Error en listaDocumentosConf() ---> "+e.getMessage());
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
	public Boolean getPermiso() {
		return poseePermiso;
	}
	public void setPermiso(Boolean permiso) {
		this.poseePermiso = permiso;
	}
	public Boolean getEstadoTab() {
		return estadoTab;
	}
	public void setEstadoTab(Boolean estadoTab) {
		this.estadoTab = estadoTab;
	}	
	public void setIntTipoPersonaFiltro(Integer intTipoPersonaFiltro) {
		this.intTipoPersonaFiltro = intTipoPersonaFiltro;
	}
	public Integer getIntTipoPersonaFiltro() {
		return intTipoPersonaFiltro;
	}
	public String getStrMensajeError() {
		return strMensajeError;
	}
	public void setStrMensajeError(String strMensajeError) {
		this.strMensajeError = strMensajeError;
	}
	public Egreso getEgresoFiltro() {
		return egresoFiltro;
	}
	public void setEgresoFiltro(Egreso egresoFiltro) {
		this.egresoFiltro = egresoFiltro;
	}
	public Date getDtDesdeFiltro() {
		return dtDesdeFiltro;
	}
	public void setDtDesdeFiltro(Date dtDesdeFiltro) {
		this.dtDesdeFiltro = dtDesdeFiltro;
	}
	public Date getDtHastaFiltro() {
		return dtHastaFiltro;
	}
	public void setDtHastaFiltro(Date dtHastaFiltro) {
		this.dtHastaFiltro = dtHastaFiltro;
	}
	public String getStrTextoPersonaFiltro() {
		return strTextoPersonaFiltro;
	}
	public void setStrTextoPersonaFiltro(String strTextoPersonaFiltro) {
		this.strTextoPersonaFiltro = strTextoPersonaFiltro;
	}
	public List<Egreso> getListaEgreso() {
		return listaEgreso;
	}
	public void setListaEgreso(List<Egreso> listaEgreso) {
		this.listaEgreso = listaEgreso;
	}	
	public List<Tabla> getListaTablaEstado() {
		return listaTablaEstado;
	}
	public void setListaTablaEstado(List<Tabla> listaTablaEstado) {
		this.listaTablaEstado = listaTablaEstado;
	}
	public void setMostrarMensajeExito(Boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}
	public Boolean getMostrarMensajeExito() {
		return mostrarMensajeExito;
	}
	public void setMostrarMensajeError(Boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}
	public Boolean getMostrarMensajeError() {
		return mostrarMensajeError;
	}
	public void setMostrarPanelInferior(Boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public Boolean getMostrarPanelInferior() {
		return mostrarPanelInferior;
	}
	public Integer getIntTipoBusquedaPersona() {
		return intTipoBusquedaPersona;
	}
	public void setIntTipoBusquedaPersona(Integer intTipoBusquedaPersona) {
		this.intTipoBusquedaPersona = intTipoBusquedaPersona;
	}
	public void setListaSubTipoOperacion(List<Tabla> listaSubTipoOperacion) {
		this.listaSubTipoOperacion = listaSubTipoOperacion;
	}
	public List<Tabla> getListaSubTipoOperacion() {
		return listaSubTipoOperacion;
	}
	public void setListaTablaTipoFondoFijo(List<Tabla> listaTablaTipoFondoFijo) {
		this.listaTablaTipoFondoFijo = listaTablaTipoFondoFijo;
	}
	public List<Tabla> getListaTablaTipoFondoFijo() {
		return listaTablaTipoFondoFijo;
	}
	public void setListaPersonaFiltro(List<Persona> listaPersonaFiltro) {
		this.listaPersonaFiltro = listaPersonaFiltro;
	}
	public List<Persona> getListaPersonaFiltro() {
		return listaPersonaFiltro;
	}
	public void setSucursalUsuario(Sucursal sucursalUsuario) {
		this.sucursalUsuario = sucursalUsuario;
	}
	public Sucursal getSucursalUsuario() {
		return sucursalUsuario;
	}
	public void setSubsucursalUsuario(Subsucursal subsucursalUsuario) {
		this.subsucursalUsuario = subsucursalUsuario;
	}
	public Subsucursal getSubsucursalUsuario() {
		return subsucursalUsuario;
	}
	public void setCuentaBancariaSeleccionada(CuentaBancaria cuentaBancariaSeleccionada) {
		this.cuentaBancariaSeleccionada = cuentaBancariaSeleccionada;
	}
	public CuentaBancaria getCuentaBancariaSeleccionada() {
		return cuentaBancariaSeleccionada;
	}
	public void setCuentaActual(Cuenta cuentaActual) {
		this.cuentaActual = cuentaActual;
	}
	public Cuenta getCuentaActual() {
		return cuentaActual;
	}
	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}
	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
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
	public BancoFacadeLocal getBancoFacade() {
		return bancoFacade;
	}
	public void setBancoFacade(BancoFacadeLocal bancoFacade) {
		this.bancoFacade = bancoFacade;
	}
	public EgresoFacadeLocal getEgresoFacade() {
		return egresoFacade;
	}
	public void setEgresoFacade(EgresoFacadeLocal egresoFacade) {
		this.egresoFacade = egresoFacade;
	}
	public PrevisionFacadeRemote getPrevisionFacade() {
		return previsionFacade;
	}
	public void setPrevisionFacade(PrevisionFacadeRemote previsionFacade) {
		this.previsionFacade = previsionFacade;
	}
	public LiquidacionFacadeRemote getLiquidacionFacade() {
		return liquidacionFacade;
	}
	public void setLiquidacionFacade(LiquidacionFacadeRemote liquidacionFacade) {
		this.liquidacionFacade = liquidacionFacade;
	}
	public LibroDiarioFacadeRemote getLibroDiarioFacade() {
		return libroDiarioFacade;
	}
	public void setLibroDiarioFacade(LibroDiarioFacadeRemote libroDiarioFacade) {
		this.libroDiarioFacade = libroDiarioFacade;
	}
	public CierreDiarioArqueoFacadeRemote getCierreDiarioArqueoFacade() {
		return cierreDiarioArqueoFacade;
	}
	public void setCierreDiarioArqueoFacade(
			CierreDiarioArqueoFacadeRemote cierreDiarioArqueoFacade) {
		this.cierreDiarioArqueoFacade = cierreDiarioArqueoFacade;
	}
	public PrestamoFacadeRemote getPrestamoFacade() {
		return prestamoFacade;
	}
	public void setPrestamoFacade(PrestamoFacadeRemote prestamoFacade) {
		this.prestamoFacade = prestamoFacade;
	}
	public LogisticaFacadeLocal getLogisticaFacade() {
		return logisticaFacade;
	}
	public void setLogisticaFacade(LogisticaFacadeLocal logisticaFacade) {
		this.logisticaFacade = logisticaFacade;
	}
	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}
	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
	}
	public List<Tabla> getListaTablaSucursal() {
		return listaTablaSucursal;
	}
	public void setListaTablaSucursal(List<Tabla> listaTablaSucursal) {
		this.listaTablaSucursal = listaTablaSucursal;
	}
	public List<Tabla> getListaMoneda() {
		return listaMoneda;
	}
	public void setListaMoneda(List<Tabla> listaMoneda) {
		this.listaMoneda = listaMoneda;
	}
	public List<Tabla> getListaTablaDocumentoGeneral() {
		return listaTablaDocumentoGeneral;
	}
	public void setListaTablaDocumentoGeneral(List<Tabla> listaTablaDocumentoGeneral) {
		this.listaTablaDocumentoGeneral = listaTablaDocumentoGeneral;
	}
	public List<EgresoDetalleInterfaz> getListaEgresoDetalleInterfazAgregado() {
		return listaEgresoDetalleInterfazAgregado;
	}
	public void setListaEgresoDetalleInterfazAgregado(
			List<EgresoDetalleInterfaz> listaEgresoDetalleInterfazAgregado) {
		this.listaEgresoDetalleInterfazAgregado = listaEgresoDetalleInterfazAgregado;
	}
	public List<ControlFondosFijos> getListaControlFondosFijosBusqueda() {
		return listaControlFondosFijosBusqueda;
	}
	public void setListaControlFondosFijosBusqueda(
			List<ControlFondosFijos> listaControlFondosFijosBusqueda) {
		this.listaControlFondosFijosBusqueda = listaControlFondosFijosBusqueda;
	}
	public List<ControlFondosFijos> getListaControlFondosFijos() {
		return listaControlFondosFijos;
	}
	public void setListaControlFondosFijos(
			List<ControlFondosFijos> listaControlFondosFijos) {
		this.listaControlFondosFijos = listaControlFondosFijos;
	}
	public List<BeneficiarioPrevision> getListaBeneficiarioPrevision() {
		return listaBeneficiarioPrevision;
	}
	public void setListaBeneficiarioPrevision(
			List<BeneficiarioPrevision> listaBeneficiarioPrevision) {
		this.listaBeneficiarioPrevision = listaBeneficiarioPrevision;
	}
	public List<Persona> getListaBeneficiarioPersona() {
		return listaBeneficiarioPersona;
	}
	public void setListaBeneficiarioPersona(List<Persona> listaBeneficiarioPersona) {
		this.listaBeneficiarioPersona = listaBeneficiarioPersona;
	}
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public List<PersonaRol> getListaPersonaRolUsar() {
		return listaPersonaRolUsar;
	}
	public void setListaPersonaRolUsar(List<PersonaRol> listaPersonaRolUsar) {
		this.listaPersonaRolUsar = listaPersonaRolUsar;
	}
	public List<DocumentoGeneral> getListaDocumentoAgregados() {
		return listaDocumentoAgregados;
	}
	public void setListaDocumentoAgregados(
			List<DocumentoGeneral> listaDocumentoAgregados) {
		this.listaDocumentoAgregados = listaDocumentoAgregados;
	}
	public List<DocumentoGeneral> getListaDocumentoPorAgregar() {
		return listaDocumentoPorAgregar;
	}
	public void setListaDocumentoPorAgregar(
			List<DocumentoGeneral> listaDocumentoPorAgregar) {
		this.listaDocumentoPorAgregar = listaDocumentoPorAgregar;
	}
	public List<Tabla> getListaTipoVinculo() {
		return listaTipoVinculo;
	}
	public void setListaTipoVinculo(List<Tabla> listaTipoVinculo) {
		this.listaTipoVinculo = listaTipoVinculo;
	}
	public List<Tabla> getListaAño() {
		return listaAño;
	}
	public void setListaAño(List<Tabla> listaAño) {
		this.listaAño = listaAño;
	}
	public List<Tabla> getListaTablaTipoMovilidad() {
		return listaTablaTipoMovilidad;
	}
	public void setListaTablaTipoMovilidad(List<Tabla> listaTablaTipoMovilidad) {
		this.listaTablaTipoMovilidad = listaTablaTipoMovilidad;
	}
	public Egreso getEgresoNuevo() {
		return egresoNuevo;
	}
	public void setEgresoNuevo(Egreso egresoNuevo) {
		this.egresoNuevo = egresoNuevo;
	}
	public Persona getPersonaSeleccionada() {
		return personaSeleccionada;
	}
	public void setPersonaSeleccionada(Persona personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}
	public ControlFondosFijos getControlFondosFijos() {
		return controlFondosFijos;
	}
	public void setControlFondosFijos(ControlFondosFijos controlFondosFijos) {
		this.controlFondosFijos = controlFondosFijos;
	}
	public NumberFormat getFormato() {
		return formato;
	}
	public void setFormato(NumberFormat formato) {
		this.formato = formato;
	}
	public Persona getPersonaApoderado() {
		return personaApoderado;
	}
	public void setPersonaApoderado(Persona personaApoderado) {
		this.personaApoderado = personaApoderado;
	}
	public Archivo getArchivoCartaPoder() {
		return archivoCartaPoder;
	}
	public void setArchivoCartaPoder(Archivo archivoCartaPoder) {
		this.archivoCartaPoder = archivoCartaPoder;
	}
	public LibroDiario getLibroDiario() {
		return libroDiario;
	}
	public void setLibroDiario(LibroDiario libroDiario) {
		this.libroDiario = libroDiario;
	}
	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}
	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public String getMensajePopUp() {
		return mensajePopUp;
	}
	public void setMensajePopUp(String mensajePopUp) {
		this.mensajePopUp = mensajePopUp;
	}
	public BigDecimal getBdMontoGirar() {
		return bdMontoGirar;
	}
	public void setBdMontoGirar(BigDecimal bdMontoGirar) {
		this.bdMontoGirar = bdMontoGirar;
	}
	public BigDecimal getBdDiferencialGirar() {
		return bdDiferencialGirar;
	}
	public void setBdDiferencialGirar(BigDecimal bdDiferencialGirar) {
		this.bdDiferencialGirar = bdDiferencialGirar;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public String getStrMontoGirarDescripcion() {
		return strMontoGirarDescripcion;
	}
	public void setStrMontoGirarDescripcion(String strMontoGirarDescripcion) {
		this.strMontoGirarDescripcion = strMontoGirarDescripcion;
	}
	public String getStrDiferencialGirarDescripcion() {
		return strDiferencialGirarDescripcion;
	}
	public void setStrDiferencialGirarDescripcion(
			String strDiferencialGirarDescripcion) {
		this.strDiferencialGirarDescripcion = strDiferencialGirarDescripcion;
	}
	public Integer getIntTipoFondoFijoValidar() {
		return intTipoFondoFijoValidar;
	}
	public void setIntTipoFondoFijoValidar(Integer intTipoFondoFijoValidar) {
		this.intTipoFondoFijoValidar = intTipoFondoFijoValidar;
	}
	public Integer getIntControlSeleccionado() {
		return intControlSeleccionado;
	}
	public void setIntControlSeleccionado(Integer intControlSeleccionado) {
		this.intControlSeleccionado = intControlSeleccionado;
	}
	public String getStrListaPersonaRolUsar() {
		return strListaPersonaRolUsar;
	}
	public void setStrListaPersonaRolUsar(String strListaPersonaRolUsar) {
		this.strListaPersonaRolUsar = strListaPersonaRolUsar;
	}
	public Integer getIntTipoBuscarPersona() {
		return intTipoBuscarPersona;
	}
	public void setIntTipoBuscarPersona(Integer intTipoBuscarPersona) {
		this.intTipoBuscarPersona = intTipoBuscarPersona;
	}
	public Integer getIntBeneficiarioSeleccionar() {
		return intBeneficiarioSeleccionar;
	}
	public void setIntBeneficiarioSeleccionar(Integer intBeneficiarioSeleccionar) {
		this.intBeneficiarioSeleccionar = intBeneficiarioSeleccionar;
	}
	public Integer getIntAñoFiltro() {
		return intAñoFiltro;
	}
	public void setIntAñoFiltro(Integer intAñoFiltro) {
		this.intAñoFiltro = intAñoFiltro;
	}
	public boolean isHabilitarFiltroSucursal() {
		return habilitarFiltroSucursal;
	}
	public void setHabilitarFiltroSucursal(boolean habilitarFiltroSucursal) {
		this.habilitarFiltroSucursal = habilitarFiltroSucursal;
	}
	public Integer getIntItemControlFiltro() {
		return intItemControlFiltro;
	}
	public void setIntItemControlFiltro(Integer intItemControlFiltro) {
		this.intItemControlFiltro = intItemControlFiltro;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
	}
	public String getStrFiltroTextoPersona() {
		return strFiltroTextoPersona;
	}
	public void setStrFiltroTextoPersona(String strFiltroTextoPersona) {
		this.strFiltroTextoPersona = strFiltroTextoPersona;
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
	public void setDocumentoGeneralSeleccionado(
			DocumentoGeneral documentoGeneralSeleccionado) {
		this.documentoGeneralSeleccionado = documentoGeneralSeleccionado;
	}
	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}
	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
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
	public Integer getBUSCAR_PERSONA() {
		return BUSCAR_PERSONA;
	}
	public void setBUSCAR_PERSONA(Integer bUSCAR_PERSONA) {
		BUSCAR_PERSONA = bUSCAR_PERSONA;
	}
	public Integer getBUSCAR_APODERADO() {
		return BUSCAR_APODERADO;
	}
	public void setBUSCAR_APODERADO(Integer bUSCAR_APODERADO) {
		BUSCAR_APODERADO = bUSCAR_APODERADO;
	}
	public boolean isMostrarBtnEliminar() {
		return mostrarBtnEliminar;
	}
	public void setMostrarBtnEliminar(boolean mostrarBtnEliminar) {
		this.mostrarBtnEliminar = mostrarBtnEliminar;
	}
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
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
//	public boolean isDatosValidados() {
//		return datosValidados;
//	}
//	public void setDatosValidados(boolean datosValidados) {
//		this.datosValidados = datosValidados;
//	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	public List<RequisitoCredito> getLstRequisitoCredito() {
		return lstRequisitoCredito;
	}
	public void setLstRequisitoCredito(List<RequisitoCredito> lstRequisitoCredito) {
		this.lstRequisitoCredito = lstRequisitoCredito;
	}
	public List<RequisitoCreditoComp> getLstRequisitoCreditoComp() {
		return lstRequisitoCreditoComp;
	}
	public void setLstRequisitoCreditoComp(
			List<RequisitoCreditoComp> lstRequisitoCreditoComp) {
		this.lstRequisitoCreditoComp = lstRequisitoCreditoComp;
	}
	public Boolean getBlnNoExisteRequisito() {
		return blnNoExisteRequisito;
	}
	public void setBlnNoExisteRequisito(Boolean blnNoExisteRequisito) {
		this.blnNoExisteRequisito = blnNoExisteRequisito;
	}
	public Boolean getBlnVerBotonApoderado() {
		return blnVerBotonApoderado;
	}
	public void setBlnVerBotonApoderado(Boolean blnVerBotonApoderado) {
		this.blnVerBotonApoderado = blnVerBotonApoderado;
	}
	public List<RequisitoPrevision> getLstRequisitoPrevision() {
		return lstRequisitoPrevision;
	}
	public void setLstRequisitoPrevision(
			List<RequisitoPrevision> lstRequisitoPrevision) {
		this.lstRequisitoPrevision = lstRequisitoPrevision;
	}
	public List<RequisitoPrevisionComp> getLstRequisitoPrevisionComp() {
		return lstRequisitoPrevisionComp;
	}
	public void setLstRequisitoPrevisionComp(
			List<RequisitoPrevisionComp> lstRequisitoPrevisionComp) {
		this.lstRequisitoPrevisionComp = lstRequisitoPrevisionComp;
	}
	public BeneficiarioPrevision getBeneficiarioPrevisionSeleccionado() {
		return beneficiarioPrevisionSeleccionado;
	}
	public void setBeneficiarioPrevisionSeleccionado(
			BeneficiarioPrevision beneficiarioPrevisionSeleccionado) {
		this.beneficiarioPrevisionSeleccionado = beneficiarioPrevisionSeleccionado;
	}
	public String getStrMensajeErrorPorBeneficiario() {
		return strMensajeErrorPorBeneficiario;
	}
	public void setStrMensajeErrorPorBeneficiario(
			String strMensajeErrorPorBeneficiario) {
		this.strMensajeErrorPorBeneficiario = strMensajeErrorPorBeneficiario;
	}
	public List<RequisitoLiquidacion> getLstRequisitoLiquidacion() {
		return lstRequisitoLiquidacion;
	}
	public void setLstRequisitoLiquidacion(
			List<RequisitoLiquidacion> lstRequisitoLiquidacion) {
		this.lstRequisitoLiquidacion = lstRequisitoLiquidacion;
	}
	public List<RequisitoLiquidacionComp> getLstRequisitoLiquidacionComp() {
		return lstRequisitoLiquidacionComp;
	}
	public void setLstRequisitoLiquidacionComp(
			List<RequisitoLiquidacionComp> lstRequisitoLiquidacionComp) {
		this.lstRequisitoLiquidacionComp = lstRequisitoLiquidacionComp;
	}
	public BeneficiarioLiquidacion getBeneficiarioLiquidacionSeleccionado() {
		return beneficiarioLiquidacionSeleccionado;
	}
	public void setBeneficiarioLiquidacionSeleccionado(
			BeneficiarioLiquidacion beneficiarioLiquidacionSeleccionado) {
		this.beneficiarioLiquidacionSeleccionado = beneficiarioLiquidacionSeleccionado;
	}
	public Archivo getArchivoAdjuntoGiro() {
		return archivoAdjuntoGiro;
	}
	public void setArchivoAdjuntoGiro(Archivo archivoAdjuntoGiro) {
		this.archivoAdjuntoGiro = archivoAdjuntoGiro;
	}
	public Boolean getBlnBeneficiarioSinAutorizacion() {
		return blnBeneficiarioSinAutorizacion;
	}
	public void setBlnBeneficiarioSinAutorizacion(
			Boolean blnBeneficiarioSinAutorizacion) {
		this.blnBeneficiarioSinAutorizacion = blnBeneficiarioSinAutorizacion;
	}
	public List<BeneficiarioLiquidacion> getListaBeneficiarioLiquidacion() {
		return listaBeneficiarioLiquidacion;
	}
	public void setListaBeneficiarioLiquidacion(
			List<BeneficiarioLiquidacion> listaBeneficiarioLiquidacion) {
		this.listaBeneficiarioLiquidacion = listaBeneficiarioLiquidacion;
	}
	public String getStrObs() {
		return strObs;
	}
	public void setStrObs(String strObs) {
		this.strObs = strObs;
	}
	public Boolean getBlnHabilitarObservacion() {
		return blnHabilitarObservacion;
	}
	public void setBlnHabilitarObservacion(Boolean blnHabilitarObservacion) {
		this.blnHabilitarObservacion = blnHabilitarObservacion;
	}
	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}
	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}
//	public Boolean getBlnActivarNroApertura() {
//		return blnActivarNroApertura;
//	}
//	public void setBlnActivarNroApertura(Boolean blnActivarNroApertura) {
//		this.blnActivarNroApertura = blnActivarNroApertura;
//	}
	public void setMostrarMensajeExito(boolean mostrarMensajeExito) {
		this.mostrarMensajeExito = mostrarMensajeExito;
	}
	public void setMostrarMensajeError(boolean mostrarMensajeError) {
		this.mostrarMensajeError = mostrarMensajeError;
	}
	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public Egreso getEgresoGeneradoTrasGrabacion() {
		return egresoGeneradoTrasGrabacion;
	}
	public void setEgresoGeneradoTrasGrabacion(Egreso egresoGeneradoTrasGrabacion) {
		this.egresoGeneradoTrasGrabacion = egresoGeneradoTrasGrabacion;
	}
	public List<Tabla> getListaTablaTipoDocumento() {
		return listaTablaTipoDocumento;
	}
	public void setListaTablaTipoDocumento(List<Tabla> listaTablaTipoDocumento) {
		this.listaTablaTipoDocumento = listaTablaTipoDocumento;
	}
}
