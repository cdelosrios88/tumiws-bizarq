package pe.com.tumi.servicio.liquidacion.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
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
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.domain.AuditoriaMotivo;
import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
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
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.liquidacion.service.SolicitudLiquidacionService;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.composite.AutorizaLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;
import pe.com.tumi.servicio.prevision.facade.AutorizacionLiquidacionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.LiquidacionFacadeRemote;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;

/*****************************************************************************
 * NOMBRE DE LA CLASE: AutorizacionLiquidacionController 
 * FUNCIONALIDAD : CLASE QUE TIENE LOS PARAMETROS DE BUSQUEDA Y VALIDACIONES 
 * REF. : 
 * AUTOR : 
 * MANTENIMIENTO: JUNIOR CHÁVEZ VALVERDE 
 * VERSIÓN : V2.0
 * FECHA CREACIÓN : 25/11/2013
 * FECHA MODIFICACION : 22.02.2014  
 *****************************************************************************/

public class AutorizacionLiquidacionController {
	protected static Logger log = Logger.getLogger(AutorizacionLiquidacionController.class);
	
	private AutorizaVerificaLiquidacion beanAutorizaVerificacion;
	private List<Tabla> 		listaSubTipoSolicitudBusqueda;
	private SocioComp 			beanSocioComp;       
	private Persona 			personaBusqueda;
	private Integer 			intTipoPersona;
	private Integer 			intIdTipoPersona;
	private String				strNroSolicitud;
	private Integer 			intTipoSucursalBusq;
	private Integer 			intIdEstadoSolicitud;
	private Date 				dtFecInicio;
	private Date 				dtFecFin;
	private List<Estructura> 	listEstructura;
	private List<Subsucursal>	listSubSucursal;
	private List<Archivo> 		listaArchivo;
	private Tabla 				tablaEstado;
	private String 				strTxtMsgPerfil;

	private String 				strTxtMsgUsuario;
	private String 				strTxtMsgValidacion;
	private String 				strTxtMsgError;
	private Boolean 			formAutorizacionLiquidacionRendered;
	private List<AutorizaLiquidacionComp> 	listaAutorizaLiquidacionComp;
	
	private List<Tabla> listaTipoCuenta;

	private AutorizaLiquidacion beanAutorizaLiquidacion;

	// Campos para la busqueda
	private Integer				intTipoCreditoFiltro;
	private Integer				intSubTipoCreditoFiltro;
	private Integer				intTipoPersonaFiltro;
	private Integer				intTipoBusquedaPersonaFiltro;
	private String				strTextoPersonaFiltro;
	private Integer				intItemExpedienteFiltro;
	private EstadoLiquidacion	estadoLiquidacionFiltro;
	private List<Tabla>			listaTablaEstadoPago;
	private List<Tabla>			listaTablaTipoDocumento;
	private Integer				intTipoBusquedaFechaFiltro;
	private Integer				intTipoBusquedaSucursal;
	private List<Tabla>			listaTipoBusquedaSucursal;
	private Integer				intIdSubsucursalFiltro;
	private Integer				intIdSucursalFiltro;
	private List<Tabla>			listaExpedienteLiquidacionX;
	private List<ExpedienteLiquidacion> listaExpedienteLiquidacion;
	private List<Tabla> 		listaTipoVinculo;
	private List<ExpedienteLiquidacion> listaExpedienteLiquidacionSocio;
	private List<Subsucursal> 	listaSubsucursal;
	private List<Tabla>			listaTablaSucursal;
	
	private List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	private Archivo archivoDeJu;
	private Archivo archivoReniec;
	
	private List<AutorizaVerificaLiquidacion> listaAutorizaVerificacionAdjuntos;
	private List<ConfServSolicitud> listaAutorizacionConfigurada = null;
	
	private List<Tabla> listaTipoOperacion;
	private List<Sucursal> listSucursal;
	private boolean blnIsRetiro;
	
	private Integer intTipoCambio;
	private Date dtNuevoFechaProgramacionPago;
	private String strMotivoCambio;
	private boolean blnCambioMotivoRenuncia;
	private boolean blnCambioFecha;
	private boolean blnCambiobeneficairio;
	private Integer intNuevoMotivoRenuncia;

	private List<Tabla> listaTipoCambio;
	private List<Tabla> listaMotivoRenuncia;

	private ConfSolicitudFacadeLocal solicitudFacade = null;
	private GeneralFacadeRemote generalFacade = null;	
	private SocioFacadeRemote socioFacade = null;
	private TablaFacadeRemote tablaFacade = null;
	private PrevisionFacadeRemote previsionFacade = null;
	private PersonaFacadeRemote personaFacade = null;
	private PermisoFacadeRemote permisoFacade = null;
	private EmpresaFacadeRemote empresaFacade= null;
	private AutorizacionLiquidacionFacadeRemote autorizacionFacade = null;
	private SolicitudLiquidacionService solicitudLiquidacionService = null;
	private CuentaFacadeRemote cuentaFacade = null;
	private ConceptoFacadeRemote conceptoFacade = null;
	private LibroDiarioFacadeRemote libroDiarioFacade = null;
	private AuditoriaFacadeRemote auditoriafacade = null;
	
	// ----------- Grilla de Busqueda - cgd - 11.06.2013 
	private LiquidacionFacadeRemote liquidacionFacade;
	private List<ExpedienteLiquidacionComp> listaExpedienteLiquidacionComp;
	private Integer intTipoConsultaBusqueda; // Nombres DNI RUC Razón Social
	private String strConsultaBusqueda;
	private EstadoLiquidacion estadoLiquidacionBusqueda;
	private EstructuraDetalle estructuraDetalleBusqueda;
	private List<Tabla> listaTablaDeSucursal;
	private List<Tabla> listaTablaCreditoEmpresa;
	private String strNumeroSolicitudBusq;
	private Boolean blnTxtBusqueda;
	private Boolean blnBusquedaFechas;
	private Boolean blnBusquedaCondicion;
	private List<Tabla> listaTipoConsultaBusqueda;
	//private List		listaSubsucursal;   * revisar
	private List<Subsucursal>	listaSubsucursalBusq;
	private EstadoLiquidacion 	estadoLiquidacionFechas;
	private EstadoLiquidacion estadoLiquidacionSuc;
	//private List<Sucursal> listSucursal;   * revisar
	private EstadoLiquidacion		estadoCondicionFiltro;
	private ExpedienteLiquidacionComp registroSeleccionadoBusquedaComp;

	// sesion
	private Usuario 	usuario;
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	private Integer		SUCURSAL_USUARIO_ID;
	private Integer		SUBSUCURSAL_USUARIO_ID;
	
	private Boolean blnBotonAutorizar;
	private Integer intNroTotalPersonarPerfilesAprobar;
	
	private Boolean blnBloquearXCuenta;
	private String strMensajeValidacionCuenta;
	
	// CGD -25.10.2013
	
	private Integer intBusquedaTipo; 	
	private String 	strBusqCadena;		    
	private String 	strBusqNroSol;		   
	private Integer intBusqSucursal;
	private Integer intBusqEstado;	
	private Date 	dtBusqFechaEstadoDesde;  
	private Date 	dtBusqFechaEstadoHasta;
	private Integer intBusqTipoLiquidacion;
	
	private List<Sucursal> listaSucursal;
	
	//JCHAVEZ 21.02.2014
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private String 	mensajeOperacion;
	
	public void cargarValoresIniciales(){
		beanAutorizaLiquidacion = new AutorizaLiquidacion();
		beanAutorizaVerificacion = new AutorizaVerificaLiquidacion();
		formAutorizacionLiquidacionRendered = false;
//		formAutorizacionLiquidacionRendered = true;
		listaRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
		personaBusqueda = new Persona();
		personaBusqueda.setDocumento(new Documento());
		
		listaArchivo = new ArrayList<Archivo>();

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
		
		estructuraDetalleBusqueda = new EstructuraDetalle(); 
		
		estadoCondicionFiltro = new EstadoLiquidacion();
		estadoCondicionFiltro.setId(new EstadoLiquidacionId());
		estadoCondicionFiltro.getId().setIntPersEmpresaPk(EMPRESA_USUARIO);

		estadoLiquidacionSuc = new EstadoLiquidacion();

		estadoLiquidacionFechas = new EstadoLiquidacion();
		estadoLiquidacionFechas.setId(new EstadoLiquidacionId());
		estadoLiquidacionFechas.getId().setIntPersEmpresaPk(EMPRESA_USUARIO);

		estadoLiquidacionFiltro = new EstadoLiquidacion();
		estadoLiquidacionFiltro.setId(new EstadoLiquidacionId());
		estadoLiquidacionFiltro.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);

		estadoLiquidacionBusqueda = new EstadoLiquidacion();

		intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
		intTipoConsultaBusqueda = 0;
		strNumeroSolicitudBusq = "";
		strConsultaBusqueda = "";

		blnTxtBusqueda = true;
		blnBusquedaFechas = false;
		blnBusquedaCondicion = false;		
		blnBotonAutorizar = false;
		blnIsRetiro = false;
		
		listaExpedienteLiquidacion = new ArrayList<ExpedienteLiquidacion>();
		listaAutorizaLiquidacionComp = new ArrayList<AutorizaLiquidacionComp>();
		
		try {
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			solicitudFacade = (ConfSolicitudFacadeLocal) EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			previsionFacade = (PrevisionFacadeRemote) EJBFactory.getRemote(PrevisionFacadeRemote.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			autorizacionFacade = (AutorizacionLiquidacionFacadeRemote)EJBFactory.getRemote(AutorizacionLiquidacionFacadeRemote.class);
			solicitudLiquidacionService = (SolicitudLiquidacionService)TumiFactory.get(SolicitudLiquidacionService.class);
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote)EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			auditoriafacade = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
			liquidacionFacade = (LiquidacionFacadeRemote)EJBFactory.getRemote(LiquidacionFacadeRemote.class);
			
			listaTablaTipoDocumento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSUBOPERACION));
			listaTablaEstadoPago = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSOLICPRESTAMO), "D");
			listaTipoBusquedaSucursal = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOSUCURSALBUSQUEDA), "A");
			listaTipoVinculo = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOVINCULO));
			listaTipoOperacion = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RETIRO);
			listaTipoCuenta = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOCUENTA));
			listaMotivoRenuncia =tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_MOTIVO_DE_RENUNCIA);

			cargarCombosBusqueda();
			cargarTipoConsultaBusqueda();
		}catch(Exception e) {
			log.error("Error en AutorizacionLiquidacionController --> "+e);
		}
	}
		
	public AutorizacionLiquidacionController() {
		log = Logger.getLogger(this.getClass());
		cargarUsuario();
		if (usuario!=null) {
			cargarValoresIniciales();
		}else log.error("--Usuario obtenido es NULL.");		
	}	
	/**
	 * 
	 * @return
	 */
	public String getLimpiarAutorizacion(){
		cargarUsuario();
		listaExpedienteLiquidacionComp = new ArrayList<ExpedienteLiquidacionComp>();
		beanAutorizaLiquidacion = new AutorizaLiquidacion();
		beanAutorizaVerificacion = new AutorizaVerificaLiquidacion();
		return "";
	}
	
//	public void inicio() {
//		usuario = new Usuario();
//		// pe.com.tumi.seguridad.login.domain.Usuario
//		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
//		limpiarFormAutorizacionLiquidacion();
//		formAutorizacionLiquidacionRendered = false;
//		
//		intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
//		estadoCondicionFiltro = new EstadoLiquidacion();
//		estadoLiquidacionSuc = new EstadoLiquidacion();
//		estadoCondicionFiltro.getId().setIntPersEmpresaPk(EMPRESA_USUARIO);
//		
//		estructuraDetalleBusqueda = new EstructuraDetalle(); // ***
//		strNumeroSolicitudBusq = "";						//	***
//		intTipoConsultaBusqueda = 0;
//		strConsultaBusqueda = "";
//		estadoLiquidacionBusqueda = new EstadoLiquidacion();
//		blnTxtBusqueda = true;
//		blnBusquedaFechas = false;
//		blnBusquedaCondicion = false;
//		estadoLiquidacionFechas = new EstadoLiquidacion();
//		estadoLiquidacionFechas.getId().setIntPersEmpresaPk(EMPRESA_USUARIO);
//		blnBotonAutorizar = Boolean.FALSE;
//		
//	}
	
	public void cargarTipoConsultaBusqueda(){
		try {
			listaTipoConsultaBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOBUSQUEDA_COMBO_DRNRZ));		
		} catch (Exception e) {
			log.info("Error en cargar listaTipoConsultaBusqueda ---> "+e.getMessage());
		}		
	}

	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
		SUCURSAL_USUARIO_ID = usuario.getSucursal().getId().getIntIdSucursal();
		SUBSUCURSAL_USUARIO_ID = usuario.getSubSucursal().getId().getIntIdSubSucursal();		
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
	
	public void limpiarFormAutorizacionLiquidacion() {
//		beanAutorizaLiquidacion = new AutorizaLiquidacion();
//		beanAutorizaVerificacion = new AutorizaVerificaLiquidacion();
//		listaRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
//		archivoDeJu = null;
//		archivoReniec = null;
//		formAutorizacionLiquidacionRendered = true;
//		blnCambiobeneficairio = false;
//		blnCambioFecha = false;
//		blnCambioMotivoRenuncia= false;
//		listaAutorizaLiquidacionComp = new ArrayList<AutorizaLiquidacionComp>();
//		blnBotonAutorizar = Boolean.FALSE;
//		//Limpieza de formulario de mensajes de error
//		mostrarMensajeError = Boolean.FALSE;
//		mostrarMensajeExito = Boolean.FALSE;
		
		beanAutorizaLiquidacion = new AutorizaLiquidacion();
		beanAutorizaVerificacion = new AutorizaVerificaLiquidacion();
		listaRequisitoLiquidacionComp.clear();
		archivoDeJu = null;
		archivoReniec = null;
		blnCambiobeneficairio = false;
		blnCambioFecha = false;
		blnCambioMotivoRenuncia= false;
		listaAutorizaLiquidacionComp.clear();
		blnBotonAutorizar = Boolean.FALSE;
		//Limpieza de formulario de mensajes de error
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
	}

	public void buscarSolicitudLiquidacion(ActionEvent event){
		ExpedienteLiquidacionComp expLiqBusq = null;
		try{
			expLiqBusq = new ExpedienteLiquidacionComp();
			expLiqBusq.setIntBusquedaTipo(intBusquedaTipo);
			expLiqBusq.setStrBusqCadena(strBusqCadena.trim());
			expLiqBusq.setStrBusqNroSol(strBusqNroSol);
			//expLiqComp.setIntBusqTipoLiquidacion(intTipoCreditoFiltro);
			expLiqBusq.setIntBusqSucursal(intBusqSucursal);
			expLiqBusq.setIntBusqEstado(intBusqEstado);
			expLiqBusq.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);
			expLiqBusq.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);
			expLiqBusq.setIntBusqTipoLiquidacion(intBusqTipoLiquidacion);
			
			listaExpedienteLiquidacionComp = liquidacionFacade.getListaBusqAutLiqFiltros(expLiqBusq);

		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public void limpiarFiltros(ActionEvent event){
		intTipoConsultaBusqueda = 0;
		strConsultaBusqueda = "";
		strNumeroSolicitudBusq = "";
		estructuraDetalleBusqueda = new EstructuraDetalle();
		estadoLiquidacionBusqueda = new EstadoLiquidacion();
		blnTxtBusqueda = true;
		estadoLiquidacionSuc = new EstadoLiquidacion();
		
		estadoCondicionFiltro = new EstadoLiquidacion();
		intTipoCreditoFiltro = 0;
		estadoLiquidacionFechas = new EstadoLiquidacion();
		intIdSubsucursalFiltro = 0;
		blnBusquedaFechas = false;
		blnBusquedaCondicion = false;
	}
	
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
	 * Metodo que se ejecuta al momentod e seleccionar una solicitud de liquidacion en la grilla de busqueda.
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionadoBusquedaComp= (ExpedienteLiquidacionComp)event.getComponent().getAttributes().get("item");
			Integer intEstado = registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntEstadoCreditoUltimo();
			
				// comentado para pruebas
				listaTipoCambio = tablaFacade.getListaTablaPorIdMaestro(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO);
				if(listaTipoCambio!= null && !listaTipoCambio.isEmpty()){
					 if(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntParaSubTipoOperacion().compareTo(Constante.PARAM_T_TIPO_SUBOPERACION_LIQUIDACION_RENUNCIA)!=0){
						 for(int k=0; k<listaTipoCambio.size(); k++){ 
							 if(listaTipoCambio.get(k).getIntIdDetalle().compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_MOTIVO_RENUNCIA)==0){
								 listaTipoCambio.remove(k);
							 }
						}
					 }

				}

			recuperarAdjuntosAutorizacion(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion());
			
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_REQUISITO)==0){
				/*blnBotonActulizar = true;
				blnBotonVer = true;
				blnBotonEliminar = true;*/
			}
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0){
				blnBotonAutorizar= Boolean.TRUE;
				/*blnBotonActulizar = true;
				blnBotonVer = true;
				blnBotonEliminar = false;*/
			}
			if(intEstado.compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_ANULADO)==0){
				/*blnBotonActulizar = false;
				blnBotonVer = true;
				blnBotonEliminar = false;*/

			}
			
			validarEstadoCuenta(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion());
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	/**
	 * Valida la Situacion de la cuenta del expediente seleccionado, a find e permitir solo los de Estado ACTIVO
	 * @return
	 */
	public Boolean validarEstadoCuenta(ExpedienteLiquidacion expediente){
		Boolean blnValido= null;
		CuentaFacadeRemote cuentaFacade = null;
		CuentaId ctaIdExp = null;
		Cuenta ctaExpediente = null;
		strMensajeValidacionCuenta = "";
		List<ExpedienteLiquidacionDetalle> listaDetalle = null;
		
		try {
			cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
			listaDetalle = liquidacionFacade.getListaExpedienteLiquidacionDetallePorExpediente(expediente);
			if(expediente != null && listaDetalle != null && !listaDetalle.isEmpty()){
					ctaIdExp = new CuentaId();
					ctaIdExp.setIntCuenta(listaDetalle.get(0).getId().getIntCuenta());
					ctaIdExp.setIntPersEmpresaPk(listaDetalle.get(0).getId().getIntPersEmpresa());
					
					ctaExpediente = cuentaFacade.getListaCuentaPorPkTodoEstado(ctaIdExp);
					if(ctaExpediente != null){
						if(ctaExpediente.getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							blnValido = Boolean.FALSE;
							blnBloquearXCuenta =  Boolean.FALSE;
							strMensajeValidacionCuenta = "";
						}else{
							blnBloquearXCuenta =  Boolean.TRUE;
							blnValido = Boolean.TRUE;
							strMensajeValidacionCuenta = "No se puede Autorizar. Solicitud de Liquidación pertenece a una Cuenta No Vigente.";
						}
					}
			}			
		} catch (Exception e) {
			log.error("Error en validarEstadoCuenta ----> "+e);
		}
		return blnValido;
	}
	
	public void limpiarCampos(ActionEvent event){
		intTipoPersonaFiltro = Constante.PARAM_T_TIPOPERSONA_NATURAL;
		estadoLiquidacionFiltro = new EstadoLiquidacion();
		estadoLiquidacionFiltro.getId().setIntPersEmpresaPk(Constante.PARAM_EMPRESASESION);
		strTextoPersonaFiltro = null;
		intItemExpedienteFiltro = null;
		intTipoCreditoFiltro = 0; 
		intSubTipoCreditoFiltro=0;
		intTipoBusquedaSucursal =0;
		intIdSucursalFiltro = 0;
		intIdSubsucursalFiltro = 0;
		listaExpedienteLiquidacion = new ArrayList<ExpedienteLiquidacion>();
		formAutorizacionLiquidacionRendered = true;
		intTipoCambio = 0;
		dtNuevoFechaProgramacionPago = null;
		intNuevoMotivoRenuncia = 0;
		strMotivoCambio = "";
		strTxtMsgError = "";
	}	
	
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
	
	public void loadSubTipoSolicitudBusqueda(ActionEvent event) {
		log.info("-------------------------------------Debugging solicitudPrevisionController.loadListDocumento-------------------------------------");
		String strIdTipoSolicitudBusq = null;
		Integer intIdTipoSolicitudBusq = null;
		
		try {
			strIdTipoSolicitudBusq = getRequestParameter("pIntTipoSolicitudBusquedaAut");
			intIdTipoSolicitudBusq = new Integer(strIdTipoSolicitudBusq);
			System.out.println("TIPO DE SOLICITUD busqueda ---> "+strIdTipoSolicitudBusq);

			if(!intIdTipoSolicitudBusq.equals("0")){

				if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDOSEPELIO)){
						listaSubTipoSolicitudBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_SEPELIO));

				} else if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_AES)){
					listaSubTipoSolicitudBusqueda = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTO_AES));  
							
				} else  if(intIdTipoSolicitudBusq.equals(Constante.PARAM_T_DOCUMENTOGENERAL_FONDORETIRO)){
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
	 * @param event
	 */
	public void showAutorizacionLiquidacion(ActionEvent event) {
		log.info("-----------------------Debugging CreditoController.showAutorizacionPrestamo-----------------------------");
	
		List<ConfServSolicitud> listaSolicitudAutorizada = null;
		ConfServSolicitud confServSolicitud = null;
		listaAutorizacionConfigurada = new ArrayList<ConfServSolicitud>();
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
//		Calendar clToday = Calendar.getInstance();
		Boolean blnContinuaValidacion = Boolean.TRUE;
		
		try {
			
			if(listaAutorizaLiquidacionComp != null && !listaAutorizaLiquidacionComp.isEmpty()){
				listaAutorizaLiquidacionComp.clear();
			}
			cargarUsuario();	
	
			confServSolicitud = new ConfServSolicitud();
			confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_LIQUIDACION);
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION);
			
			listaSolicitudAutorizada = solicitudFacade.buscarConfSolicitudAutorizacion (confServSolicitud, null, null, null);
		
			if (listaSolicitudAutorizada != null && listaSolicitudAutorizada.size() > 0) {
				for (ConfServSolicitud solicitud : listaSolicitudAutorizada) {
					if(solicitud.getIntParaTipoRequertoAutorizaCod().compareTo(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION)==0){
						solicitud = validarConfiguracion(solicitud);
						if (solicitud != null) {
							listaAutorizacionConfigurada.add(solicitud);
						}
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
								if (usuario.getPerfil().getId().getIntIdPerfil().equals(perfil.getIntIdPerfilPk())) {
									System.out.println("PERFIL APROBADO --> "+ perfil.getIntIdPerfilPk());
									blnContinuaValidacion = Boolean.FALSE;
									formAutorizacionLiquidacionRendered = true;
									setStrTxtMsgPerfil("");
									listarEncargadosAutorizar();
									break;
								} else {
									formAutorizacionLiquidacionRendered = false;
									setStrTxtMsgPerfil("El Perfil no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
								}
							}
						}
					}
					// 2. validando lista de usuarios
					if (solicitud.getListaUsuario() != null	&& solicitud.getListaUsuario().size() > 0) {
						for (ConfServUsuario $usuario : solicitud.getListaUsuario()) {
							if(blnContinuaValidacion){
								if (usuario.getIntPersPersonaPk().equals($usuario.getIntPersUsuarioPk())) {
									System.out.println("USUARIO APROBADO --> "	+ usuario.getStrUsuario());
									blnContinuaValidacion = Boolean.FALSE;
									formAutorizacionLiquidacionRendered = true;
									setStrTxtMsgUsuario("");
									if (listarEncargadosAutorizar() == true) {
											
									}
									break;
								} else {
									formAutorizacionLiquidacionRendered = false;
									setStrTxtMsgUsuario("El Usuario no concuerda con el que se ha configurado en la Autorización de la Solicitud.");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error en showAutorizacionLiquidacion--> "+e);
		}
	}	
	
	public void refrescarEncargadosAutorizar() {
		List<AutorizaLiquidacion> listaAutorizaLiquidacion = new ArrayList<AutorizaLiquidacion>();
		listaAutorizaLiquidacionComp = new ArrayList<AutorizaLiquidacionComp>();
		AutorizaLiquidacionComp autorizaLiquidacionComp = null;
		Persona persona = null;
		try {
			listaAutorizaLiquidacion = solicitudLiquidacionService.getListaAutorizaLiquidacionPorPkExpediente(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId());
	
			if (listaAutorizaLiquidacion != null && !listaAutorizaLiquidacion.isEmpty()) {
				for (AutorizaLiquidacion autorizaLiquidacion : listaAutorizaLiquidacion) {
					if(autorizaLiquidacion.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
						autorizaLiquidacionComp = new AutorizaLiquidacionComp();
						autorizaLiquidacionComp.setAutorizaLiquidacion(autorizaLiquidacion);
						persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaLiquidacion.getIntPersUsuarioAutoriza());
						for (int k = 0; k < persona.getListaDocumento().size(); k++) {
							if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
								persona.setDocumento(persona.getListaDocumento().get(k));
								break;
							}
	
						}
						autorizaLiquidacionComp.setPersona(persona);
						listaAutorizaLiquidacionComp.add(autorizaLiquidacionComp);
					}
				}
			}
		} catch (BusinessException e) {
			System.out.println("BusinessException --> "+e);
			log.info("Mensaje de Error: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private Boolean isValidoAutorizacion(AutorizaLiquidacion autorizaLiquidacion) {
		Boolean validAutorizaLiquidacion = true;
		strTxtMsgValidacion = "";
		if (autorizaLiquidacion.getIntParaEstadoAutorizar() == 0) {
			strTxtMsgValidacion = strTxtMsgValidacion + "* Seleccionar Operación. ";
			validAutorizaLiquidacion = false;
		}	
		
		if (intTipoCambio != 0) {
			if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_FECHA_PROGRAMACION_PAGO)==0){
				if(dtNuevoFechaProgramacionPago == null){
					strTxtMsgValidacion = strTxtMsgValidacion + "* Ingresar la nueva Fecha Nueva de programación. ";
					validAutorizaLiquidacion = false;
				}
		
			}else if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_MOTIVO_RENUNCIA)==0){						
				if(intNuevoMotivoRenuncia == 0){
					strTxtMsgValidacion = strTxtMsgValidacion + "* Ingresar la nueva Fecha Nueva de programación. ";
					validAutorizaLiquidacion = false;
				}
			}	
		}	
		return validAutorizaLiquidacion;
	}
	/**
	 * Valida que si con el registro de la presente autorizacion se inica los registros de liquidacion y cambio de estado.
	 * @param intTipoValidacion
	 * @param listaAutorizacionConfigurada
	 * @return
	 */
	private boolean faltaSoloUno(Integer intTipoValidacion, List<ConfServSolicitud> listaAutorizacionConfigurada) {
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

			refrescarEncargadosAutorizar();
			intRecuperados = listaAutorizaLiquidacionComp.size();
	
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
	}
	/**
	 * Indica si el usuario ya realizo una autorizacion.
	 * @return boolean blnNoExiste
	 */
	public boolean existeRegistro(Integer intTipoValidacion) {
		boolean blnNoExiste = true;
	
		if (listaAutorizaLiquidacionComp.size() > 0
				&& listaAutorizaLiquidacionComp != null) {
			for (int k = 0; k < listaAutorizaLiquidacionComp.size(); k++) {
				AutorizaLiquidacion autorizaLiquidacion = new AutorizaLiquidacion();
				autorizaLiquidacion = listaAutorizaLiquidacionComp.get(k).getAutorizaLiquidacion();
	
				if (intTipoValidacion.intValue() == 0) {// usuario
					if (autorizaLiquidacion.getIntPersUsuarioAutoriza().intValue() == (usuario.getPerfil().getId().getIntIdPerfil().intValue())
						&& autorizaLiquidacion.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0) {
						blnNoExiste = false;
						break;
					}
	
				} else if (intTipoValidacion.intValue() == 1) { // perfil
					if (autorizaLiquidacion.getIntIdPerfilAutoriza().intValue() == (usuario.getPerfil().getId().getIntIdPerfil().intValue())
						&& autorizaLiquidacion.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0) {
						blnNoExiste = false;
						break;
					}
				}
			}
		}
		return blnNoExiste;
	}
	/**
	 * Asociado al boton GRABAR de la autorizaciond e liquidacion.
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void grabarAutorizacionLiquidacion(ActionEvent event) throws Exception {
		log.info("-----------------------Debugging CreditoController.grabarAutorizacionPrestamo ----------------------------- 1");
		ExpedienteLiquidacion expedienteLiquidacion = null;
		List<AutorizaLiquidacion> listaAutorizaLiquidacion = new ArrayList<AutorizaLiquidacion>();
		List<AutorizaVerificaLiquidacion> listaAutorizaVerificacion = new ArrayList<AutorizaVerificaLiquidacion>();
		Integer nrolista = null;
		Integer intNroPerfiles = null;
		Integer intNroUsuarios = null;
		boolean blnValidarUsuario = false;
		boolean blnValidarPerfil = false;
		Integer intTipoValidacion = new Integer(0); // o-usuario 1-perfil
		boolean blnNoExiste = true;
		
		boolean blnEsElUltimo = false;
	
		usuario = (Usuario) getRequest().getSession().getAttribute("usuario");
		Auditoria auditoria = null;
		Boolean exito = Boolean.FALSE;
	
		try {
			refrescarEncargadosAutorizar();
			// recuperar la cuenta del socio
			recuperarCuentaCompSocio();
	
			registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().setSocioComp(beanSocioComp);
			
			if (isValidoAutorizacion(beanAutorizaLiquidacion) == false) {
				strTxtMsgValidacion = strTxtMsgValidacion + "No se puede continuar con el proceso de grabación. ";
				return;
			}
			nrolista = listaAutorizacionConfigurada.size(); // numero de autorizaciones configuradas
			log.info("Número de autorizaciones configuradas ---> "+nrolista);
			intNroUsuarios = listaAutorizacionConfigurada.get(0).getListaUsuario().size(); // numero de usuarios q deben autorizar
			intNroPerfiles = listaAutorizacionConfigurada.get(0).getListaPerfil().size(); // numero de perfiless que deben  autorizar
	
			if (intNroUsuarios.intValue() > 0) {
				blnValidarUsuario = true;
				// intTipoValidacion = 0 ;
			}
			if (intNroPerfiles.intValue() > 0) {
				blnValidarPerfil = true;
				intTipoValidacion = new Integer(1);
			}

			beanAutorizaLiquidacion.setIntPersEmpresaPk(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId().getIntPersEmpresaPk());
			beanAutorizaLiquidacion.setIntPersEmpresaAutoriza(usuario.getEmpresa().getIntIdEmpresa());
			beanAutorizaLiquidacion.setIntPersUsuarioAutoriza(usuario.getIntPersPersonaPk());
			beanAutorizaLiquidacion.setIntIdPerfilAutoriza(usuario.getPerfil().getId().getIntIdPerfil());
			beanAutorizaLiquidacion.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			beanAutorizaVerificacion.setId(null);
			beanAutorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
	
			if (archivoDeJu != null) {
				beanAutorizaVerificacion.setIntItemArchivoDeJu( archivoDeJu.getId().getIntItemArchivo());
				beanAutorizaVerificacion.setIntItemHistoricoDeJu(archivoDeJu.getId().getIntItemHistorico());
				beanAutorizaVerificacion.setIntParaTipoArchivoDeJu(archivoDeJu.getId().getIntParaTipoCod());
	
			}
			if (archivoReniec != null) {
				beanAutorizaVerificacion.setIntItemArchivoRen(archivoReniec.getId().getIntItemArchivo());
				beanAutorizaVerificacion.setIntItemHistoricoRen(archivoReniec.getId().getIntItemHistorico());
				beanAutorizaVerificacion.setIntParaTipoArchivoRen(archivoReniec.getId().getIntParaTipoCod());
			}
	
			expedienteLiquidacion = registroSeleccionadoBusquedaComp.getExpedienteLiquidacion();
			//Agregado 02.06.2014 - Se agrega lista de exp.liq.detalle
			expedienteLiquidacion.getListaExpedienteLiquidacionDetalle().addAll(liquidacionFacade.getListaExpedienteLiquidacionDetallePorExpediente(expedienteLiquidacion));
			
			listaAutorizaLiquidacion.add(beanAutorizaLiquidacion);
			listaAutorizaVerificacion.add(beanAutorizaVerificacion);
			expedienteLiquidacion.setListaAutorizaLiquidacion(listaAutorizaLiquidacion);
			expedienteLiquidacion.setListaAutorizaVerificaLiquidacion(listaAutorizaVerificacion);
	
			// Validamos la Operacion - AUTORIZAR
			// ==================================================================================================================================
			if (beanAutorizaLiquidacion.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO)==0) {
	
				if (registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {
					// Validamos Usuario
	
					if (blnValidarUsuario) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						// 19.06.2013 - cgd - pruebas - comentado
						if (blnNoExiste) {								
							blnEsElUltimo = faltaSoloUno(intTipoValidacion, listaAutorizacionConfigurada);
							
							if(blnEsElUltimo){
								//Boolean blnTodoOk = Boolean.FALSE;
								LibroDiario libroDiario = null;
								RequisitoLiquidacion requisitoLiq = new RequisitoLiquidacion();
								requisitoLiq.setId(new RequisitoLiquidacionId());
								RequisitoLiquidacionId requisitoLiqId = new RequisitoLiquidacionId();
								requisitoLiqId.setIntItemExpediente(0);
								requisitoLiqId.setIntItemRequisito(0);
								requisitoLiqId.setIntPersEmpresaLiquidacion(0);
								requisitoLiq.setId(requisitoLiqId);
								requisitoLiq.setIntParaTipoArchivo(0);
								requisitoLiq.setIntParaItemArchivo(0);
								requisitoLiq.setIntParaItemHistorico(0);
								requisitoLiq.setIntItemReqAut(0);
								requisitoLiq.setIntItemReqAutEstDetalle(0);
								requisitoLiq.setIntParaEstado(0);
								requisitoLiq.setIntPersEmpresaPk(0);
								requisitoLiq.setTsFechaRequisito(null);
								
								if(intTipoCambio.compareTo(0)!= 0){	
									auditoria = generarAuditoria(intTipoCambio, registroSeleccionadoBusquedaComp.getExpedienteLiquidacion());
									//liquidacionFacade.modificarExpedienteLiquidacionParaAuditoria(registroSeleccionadoBusqueda, intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia);
									//grabarAuditoria(auditoria);
								}
								
								libroDiario  = autorizacionFacade.aprobarLiquidacionCuentas(beanSocioComp, obtenerPeriodoActual(), requisitoLiq, 
																							registroSeleccionadoBusquedaComp.getExpedienteLiquidacion(),usuario,
																								expedienteLiquidacion,Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO,
																								intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia,auditoria);	
								log.info("aprobar liquidacion cuentas - libro diario ---> "+libroDiario);
								//}
							}else{
								solicitudLiquidacionService.grabarAutorizacionLiquidacion(expedienteLiquidacion);
								// Si selecciono alguna opcion de cambio
								if(intTipoCambio.compareTo(0)!= 0){
									//Auditoria auditoria = null;
									auditoria = generarAuditoria(intTipoCambio, registroSeleccionadoBusquedaComp.getExpedienteLiquidacion());
									liquidacionFacade.modificarExpedienteLiquidacionParaAuditoria(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion(), intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia);
									grabarAuditoria(auditoria);
								}								
							}							
						} else {
							strTxtMsgPerfil = strTxtMsgUsuario
							+ "Ya existe una Autorización registrada con el usuario: "
							+ usuario.getStrUsuario()
							+ ". No se puede continuar con el grabado.";
						}
	
					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						// 19.06.2013 - cgd - pruebas - comentado
						if (blnNoExiste) {
							//solicitudLiquidacionService.grabarAutorizacionLiquidacion(expedienteLiquidacion);
							//blnSeRegistaronTodos = seRegistraronTodosLosUsuarios(intTipoValidacion, listaAutorizacionConfigurada);
							
							blnEsElUltimo = faltaSoloUno(intTipoValidacion, listaAutorizacionConfigurada);
							
							if(blnEsElUltimo){
								LibroDiario libroDiario = null;
								RequisitoLiquidacion requisitoLiq = new RequisitoLiquidacion();
								requisitoLiq.setId(new RequisitoLiquidacionId());
								RequisitoLiquidacionId requisitoLiqId = new RequisitoLiquidacionId();
								requisitoLiqId.setIntItemExpediente(0);
								requisitoLiqId.setIntItemRequisito(0);
								requisitoLiqId.setIntPersEmpresaLiquidacion(0);
								requisitoLiq.setId(requisitoLiqId);
								requisitoLiq.setIntParaTipoArchivo(0);
								requisitoLiq.setIntParaItemArchivo(0);
								requisitoLiq.setIntParaItemHistorico(0);
								requisitoLiq.setIntItemReqAut(0);
								requisitoLiq.setIntItemReqAutEstDetalle(0);
								requisitoLiq.setIntParaEstado(0);
								requisitoLiq.setIntPersEmpresaPk(0);
								requisitoLiq.setTsFechaRequisito(null);

									if(intTipoCambio.compareTo(0)!= 0){	
										auditoria = generarAuditoria(intTipoCambio, registroSeleccionadoBusquedaComp.getExpedienteLiquidacion());
										//liquidacionFacade.modificarExpedienteLiquidacionParaAuditoria(registroSeleccionadoBusqueda, intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia);
										//grabarAuditoria(auditoria);
									}
									
									libroDiario  = autorizacionFacade.aprobarLiquidacionCuentas(beanSocioComp, obtenerPeriodoActual(), requisitoLiq, 
																								registroSeleccionadoBusquedaComp.getExpedienteLiquidacion(),usuario,
																									expedienteLiquidacion,Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO,
																									intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia,auditoria);	
									log.info("aprobar liquidacion cuentas - libro diario ---> "+libroDiario);
									// Si selecciono alguna opcion de cambio
									/*if(libroDiario != null){
										if(intTipoCambio.compareTo(0)!= 0){
											auditoria = generarAuditoria(intTipoCambio, registroSeleccionadoBusqueda);
											liquidacionFacade.modificarExpedienteLiquidacionParaAuditoria(registroSeleccionadoBusqueda, intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia);
											grabarAuditoria(auditoria);
										}	
									}*/
									
									
									/*if(libroDiario != null){
										solicitudLiquidacionService.grabarAutorizacionLiquidacion(expedienteLiquidacion);
										cambioEstadoLiquidacion(expedienteLiquidacion, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO,libroDiario);
									}*/	
								//}
							}else{
								
								solicitudLiquidacionService.grabarAutorizacionLiquidacion(expedienteLiquidacion);
								
								// Si selecciono alguna opcion de cambio
								if(intTipoCambio.compareTo(0)!= 0){
									//Auditoria auditoria = null;
									auditoria = generarAuditoria(intTipoCambio, registroSeleccionadoBusquedaComp.getExpedienteLiquidacion());
									liquidacionFacade.modificarExpedienteLiquidacionParaAuditoria(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion(), intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia);
									grabarAuditoria(auditoria);
								}

							}
							
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil
									+ "Ya existe una Autorización con el Perfil "
									+ usuario.getPerfil().getStrDescripcion()
									+ ". No se puede continuar con el grabado.";
						}

					}
					exito = true;
					strTxtMsgError = "La autorización se generó satisfactoriamente.";
				} else {
					strTxtMsgError = "La Solicitud solo puede Autorizarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
					// cancelarGrabarAutorizacionPrestamo(event);
					return;
				}
	
				// Validamos la Operacion - OBSERVAR
				// ==================================================================================================================================
			} else if (beanAutorizaLiquidacion.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_PRESTAMO)==0) {
	
				if (registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {
	
					// Validamos Usuario
					if (blnValidarUsuario) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudLiquidacionService.grabarAutorizacionLiquidacion(expedienteLiquidacion);
							liquidacionFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedienteLiquidacion);
							
							cambioEstadoLiquidacion(expedienteLiquidacion,Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);
	
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización registrada con el usuario: "
									+ usuario.getStrUsuario()+ ". No se puede continuar con el grabado.";
						}
	
					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudLiquidacionService.grabarAutorizacionLiquidacion(expedienteLiquidacion);
							liquidacionFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedienteLiquidacion);
							
							cambioEstadoLiquidacion(expedienteLiquidacion, Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO);
	
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización con el Perfil "
									+ usuario.getPerfil().getStrDescripcion()+ ". No se puede continuar con el grabado.";
						}
	
					}
	
				} else {
					strTxtMsgError = "La Solicitud solo puede Observarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
					// cancelarGrabarAutorizacionPrestamo(event);
					return;
				}
	
				// Validamos la Operacion - RECHAZAR
				// ==================================================================================================================================
			} else if (beanAutorizaLiquidacion.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO) ==0){
				
				if (registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {
					// Validamos Usuario
					if (blnValidarUsuario) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudLiquidacionService.grabarAutorizacionLiquidacion(expedienteLiquidacion);
							cambioEstadoLiquidacion(expedienteLiquidacion, Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);
	
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización registarda con el usuario: "+ usuario.getStrUsuario()+ ". No se puede continuar con el grabado.";
						}
						// COPIAR LO DE PERFIL
	
					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudLiquidacionService.grabarAutorizacionLiquidacion(expedienteLiquidacion);
							cambioEstadoLiquidacion(expedienteLiquidacion,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);
	
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil+ "Ya existe una una Autorización con el Perfil "+ usuario.getPerfil().getStrDescripcion()
									+ " id: "+ usuario.getPerfil().getId().getIntIdPerfil()+ ". No se puede continuar con el grabado.";
						}
						
					}
				} else {
					strTxtMsgError = "La Solicitud solo puede Rechazarse si se encuentra en estado APROBADO. Se anula el grabado. ";
				}
			}
			
			formAutorizacionLiquidacionRendered = false;
			
		} catch (BusinessException e) {
			throw new BusinessException("Error al grabarAutorizacionPrestamo --> "+e);
		}finally{
			mostrarMensaje(exito,strTxtMsgError);
//			cancelarGrabarAutorizacionLiquidacion(event);
		}
	}
	
	public void mostrarMensaje(boolean exito, String mensaje){
		if(exito){
			mostrarMensajeExito = Boolean.TRUE;
			mostrarMensajeError = Boolean.FALSE;
			mensajeOperacion = mensaje;
			strTxtMsgError = "";
		}else{
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.TRUE;
			mensajeOperacion = mensaje;
		}
	}
	/**
	 * Recupera el beanSocioComp
	 */
	private void recuperarCuentaCompSocio() {
		CuentaId cuentaId = null;
		Cuenta cuentaSocio = null;
		Integer intIdPersona = null;
		Persona persona = null;
		SocioComp socioComp = null;
		List<ExpedienteLiquidacionDetalle> listaDetalles = null;
		
		try {
			if(	registroSeleccionadoBusquedaComp.getExpedienteLiquidacion() != null){
				
				if(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getListaExpedienteLiquidacionDetalle()== null ||!registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getListaExpedienteLiquidacionDetalle().isEmpty()){
					listaDetalles = liquidacionFacade.getListaExpedienteLiquidacionDetallePorExpediente(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion());
					if(listaDetalles != null && !listaDetalles.isEmpty()){
						registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().setListaExpedienteLiquidacionDetalle(listaDetalles);
						registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().setListaExpedienteLiquidacionDetalle(listaDetalles);
					}
					
				}
				
				if(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getListaExpedienteLiquidacionDetalle() != null && !registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getListaExpedienteLiquidacionDetalle().isEmpty()){
					cuentaId =  new CuentaId();
					cuentaId.setIntCuenta(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getListaExpedienteLiquidacionDetalle().get(0).getId().getIntCuenta());
					cuentaId.setIntPersEmpresaPk(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getListaExpedienteLiquidacionDetalle().get(0).getId().getIntPersEmpresaLiquidacion());
	
					List<CuentaIntegrante> listaCuentaIntegranteSocio = null;	
					cuentaSocio = cuentaFacade.getCuentaPorId(cuentaId);
			
					listaCuentaIntegranteSocio = cuentaFacade.getListaCuentaIntegrantePorPKCuenta(cuentaSocio.getId());
			
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
								
								socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI),
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
				
			}
		} catch (NumberFormatException e) {
			System.out.println("NumberFormatExceptionNumberFormatException---> "+e);
			e.printStackTrace();
		} catch (BusinessException e) {
			System.out.println("BusinessExceptionBusinessException--> "+e);
			e.printStackTrace();
		}		
	}
	/**
	 * Cambia de estado al expediente, agregando un registro de Estado.
	 * 
	 * @param expedienteCredito
	 * @throws Exception
	 */	
	private void cambioEstadoLiquidacion(ExpedienteLiquidacion expedienteLiquidacion,Integer intParaEstadoLiquidacionCod) throws Exception {
		if (expedienteLiquidacion.getListaEstadoLiquidacion()==null) {
			expedienteLiquidacion.setListaEstadoLiquidacion(new ArrayList<EstadoLiquidacion>());
		}
		EstadoLiquidacion estadoLiquidacion = new EstadoLiquidacion();
		EstadoLiquidacionId estadoLiquidacionId = null;
		estadoLiquidacion.setId(estadoLiquidacionId);
		estadoLiquidacion.setTsFechaEstado(new Timestamp(new Date().getTime()));
		estadoLiquidacion.setIntPersEmpresaEstado(usuario.getEmpresa().getIntIdEmpresa());
		estadoLiquidacion.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
		estadoLiquidacion.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
		estadoLiquidacion.setIntPersUsuarioEstado(usuario.getIntPersPersonaPk());
		estadoLiquidacion.setIntParaEstado(intParaEstadoLiquidacionCod);

		expedienteLiquidacion.getListaEstadoLiquidacion().add(estadoLiquidacion);
		solicitudLiquidacionService.grabarListaDinamicaEstadoLiquidacion(expedienteLiquidacion.getListaEstadoLiquidacion(), expedienteLiquidacion.getId());
	
	}
	
	/**
	 * Obtiene el periodo actual en el formato YYYYMM
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
	/**
	 * Genera el registro de audityoria y motivo.
	 * @param pIntTipoCambio
	 * @param pExpedienteLiquidacion
	 * @return
	 * @throws BusinessException
	 */
	public Auditoria generarAuditoria(Integer pIntTipoCambio, ExpedienteLiquidacion pExpedienteLiquidacion)throws BusinessException {
		Calendar fecHoy = Calendar.getInstance();
		Auditoria auditoria = null;
		AuditoriaMotivo motivo =null;
		
		try {
			auditoria = new Auditoria();
			auditoria.setListaAuditoriaMotivo(new ArrayList<AuditoriaMotivo>());
			auditoria.setStrTabla(Constante.PARAM_T_AUDITORIA_CSE_EXPEDIENTELIQUIDACION);
			auditoria.setIntEmpresaPk(Constante.PARAM_EMPRESASESION);
			auditoria.setStrLlave1(""+pExpedienteLiquidacion.getId().getIntPersEmpresaPk());
			auditoria.setStrLlave2(""+pExpedienteLiquidacion.getId().getIntItemExpediente());
			auditoria.setIntTipo(intTipoCambio);
			auditoria.setTsFecharegistro(new Timestamp(fecHoy.getTimeInMillis()));
			auditoria.setIntPersonaPk(usuario.getIntPersPersonaPk());
			
			if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_FECHA_PROGRAMACION_PAGO)==0){
				auditoria.setStrValoranterior(pExpedienteLiquidacion.getDtFechaProgramacion().toString());
				auditoria.setStrValornuevo(dtNuevoFechaProgramacionPago.toString());
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSE_EXPEDIENTELIQUIDACION_EXLI_FECHAPROGRAMACION_D);
				pExpedienteLiquidacion.setDtFechaProgramacion(dtNuevoFechaProgramacionPago);
	
			}else if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_MOTIVO_RENUNCIA)==0){
				auditoria.setStrValoranterior(""+pExpedienteLiquidacion.getIntParaMotivoRenuncia());
				auditoria.setStrValornuevo(""+intNuevoMotivoRenuncia);
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_CSE_EXPEDIENTELIQUIDACION_PARA_MOTIVORENUNCIA_N_COD);
				pExpedienteLiquidacion.setIntParaMotivoRenuncia(intNuevoMotivoRenuncia);
			}
	
				motivo = new AuditoriaMotivo();
				motivo.setIntIdcodigo(auditoria.getIntIdcodigo());
				motivo.setIntAuditoriaMotivo(intTipoCambio);
				motivo.setStrMotivoCambio(strMotivoCambio);
				
				auditoria.getListaAuditoriaMotivo().add(motivo);
		} catch (Exception e) {
			log.error("Error en generarAuditoria ---> "+e);
		}
		
		return auditoria;
	
	}
	/**
	 * Genera el registro de audityoria y motivo.
	 * @param pIntTipoCambio
	 * @param pExpedienteLiquidacion
	 * @return
	 * @throws BusinessException
	 */
	public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException {
		
		try {
				generalFacade.grabarAuditoria(auditoria);
		} catch (Exception e) {
			log.error("Error en generarAuditoria ---> "+e);
		}
		
		return auditoria;
	
	}
	/**
	 * 
	 * @param expLiquidacion
	 * @return
	 */
	public ExpedienteLiquidacion modificarCamposExpedienteLiquidacion(ExpedienteLiquidacion expLiquidacion){
		
		try {
			if(expLiquidacion != null){
				
				if (intTipoCambio != 0) {
					if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_FECHA_PROGRAMACION_PAGO)==0){
						if(dtNuevoFechaProgramacionPago != null){
							expLiquidacion.setDtFechaProgramacion(dtNuevoFechaProgramacionPago);
						}
				
					}else if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_MOTIVO_RENUNCIA)==0){
								
								if(intNuevoMotivoRenuncia != 0){
									expLiquidacion.setIntParaMotivoRenuncia(intNuevoMotivoRenuncia);
								}
					}
					expLiquidacion = liquidacionFacade.modificarExpedienteLiquidacionParaAuditoria(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion(), intTipoCambio, dtNuevoFechaProgramacionPago, intNuevoMotivoRenuncia);
					//generarAuditoria(intTipoCambio, expLiquidacion);
				}
			}
	
		} catch (Exception e) {
			log.error("Error en modificarCamposExpedienteLiquidacion ---> "+e);
		}
	
		return expLiquidacion;
		
	}
	/**
	 * 
	 * @param confServSolicitud
	 * @return
	 */
	public ConfServSolicitud validarConfiguracion(ConfServSolicitud confServSolicitud) {
		Boolean hasMontDesde = false;
		Boolean hasMontHasta = false;
		Integer nroValidaciones = null;
		Integer contAprob = new Integer(0);
		Boolean boAprueba = new Boolean(false);
		ConfServSolicitud solicitud = null;
		Calendar clToday = Calendar.getInstance();
		String strResumen = "";

		solicitud = confServSolicitud;
		strResumen = solicitud.getId().getIntItemSolicitud()+"- No pasa las validaciones de: ";
		
	
		nroValidaciones = new Integer(3); // Se inicializa en 3 xq se toma en cuenta (1)Vigencia, (2) Estado, (3)tipo, TipoSubtipo.
		
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
		
		
		Integer intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_LIQUIDACION;
		// 3. Validando el tipo y subtipo
		if (registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntParaSubTipoOperacion().compareTo(solicitud.getIntParaSubtipoOperacionCod())==0
			&& (solicitud.getIntParaTipoOperacionCod().compareTo(intTipoOperacion))==0) {
			contAprob++;
		}else{
			strResumen = strResumen + " 3. Validando el tipo y subtipo. ";
		}
		
		// 4. Validando Monto Desde
		if (hasMontDesde){
				if (registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getBdMontoBrutoLiquidacion().compareTo(solicitud.getBdMontoDesde()) >= 0){
					contAprob++;
				}else{
					strResumen = strResumen + " 4. Validando Monto Desde. ";
				}
		}
		// 5. Validando Monto Hasta
		if (hasMontHasta){
			if (registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getBdMontoBrutoLiquidacion().compareTo(solicitud.getBdMontoHasta()) <= 0){
				contAprob++;
			}else{
				strResumen = strResumen + " 5. Validando Monto Hasta. ";
			}
		}

		// 8. Validando que exista al menos uno
		/*if ((solicitud.getBdMontoDesde() != null)
				|| (solicitud.getBdMontoHasta() != null)
				|| (solicitud.getBdCuotaDesde() != null)
				|| (solicitud.getBdCuotaHasta() != null)) {
			contAprob++;
		}*/

		System.out.println(" CONFIGURACION " + solicitud.getId().getIntItemSolicitud());
		System.out.println(" NRO DE VALIDACIONES EXISTENTES " + nroValidaciones);
		System.out.println(" NRO DE VALIDACIONES APROBADAS " + contAprob);
		System.out.println(" OBSERVACION " + strResumen);
		System.out.println("===========================================================");

		if (nroValidaciones.compareTo(contAprob)==0)
			boAprueba = true;
		if (boAprueba) {
			return solicitud;
		} else {
			return null;
		}
	}	
	/**
	 * 
	 * @return
	 */
	public boolean listarEncargadosAutorizar() {
		boolean isValidEncaragadoAutorizar = true;
		List<AutorizaLiquidacion> listaAutorizaLiquidacion = new ArrayList<AutorizaLiquidacion>();
		listaAutorizaLiquidacionComp = new ArrayList<AutorizaLiquidacionComp>();
		AutorizaLiquidacionComp autorizaLiquidacionComp = null;
		Persona persona = null;
		try {
			listaAutorizaLiquidacion =  solicitudLiquidacionService.getListaAutorizaLiquidacionPorPkExpediente(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId());
			if (listaAutorizaLiquidacion != null && listaAutorizaLiquidacion.size() > 0) {
				for (AutorizaLiquidacion autorizaLiquidacion : listaAutorizaLiquidacion) {
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
	
			//mostrarArchivosAdjuntosX();
	
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	
		return isValidEncaragadoAutorizar;
	}	

	public void mostrarArchivosAdjuntosX() {
		log.info("-----------------------Debugging SolicitudPrestamoController.mostrarArchivosAdjuntos-----------------------------");
		ConfSolicitudFacadeRemote facade = null;
		TablaFacadeRemote tablaFacade = null;
		EstructuraFacadeRemote estructuraFacade = null;
		ConfServSolicitud confServSolicitud = null;
		String strToday = Constante.sdf.format(new Date());
		Date dtToday = null;
		List<ConfServSolicitud> listaDocAdjuntos = new ArrayList<ConfServSolicitud>();
		EstructuraDetalle estructuraDet = null;
		List<EstructuraDetalle> listaEstructuraDet = new ArrayList<EstructuraDetalle>();
		listaRequisitoLiquidacionComp = new ArrayList<RequisitoLiquidacionComp>();
		RequisitoLiquidacionComp requisitoLiquidacionComp;
		try {
			dtToday = Constante.sdf.parse(strToday);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
					
			
			SocioComp beanSocioComp = new SocioComp();
	
			Integer intTipoOperacion = Constante.PARAM_T_TIPOOPERACION_LIQUIDACION;
			Integer intReqDesc = new Integer(0);
	
			//--------------------------------------------------------------------------------------------------------->
			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			confServSolicitud = new ConfServSolicitud();
			listaDocAdjuntos = facade.buscarConfSolicitudRequisito(	confServSolicitud, null, dtToday, 1);
			
			for(int i=0; i<listaDocAdjuntos.size();i++){
				System.out.println(""+listaDocAdjuntos.get(i).getIntParaTipoOperacionCod());
				System.out.println(""+listaDocAdjuntos.get(i).getIntParaSubtipoOperacionCod());
				System.out.println(""+listaDocAdjuntos.get(i).getId().getIntItemSolicitud());
				System.out.println(""+listaDocAdjuntos.get(i).getId().getIntPersEmpresaPk());
				System.out.println("-------------------------------------------------------");
			}
			
			if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
				forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {
					if (solicitud.getIntParaTipoOperacionCod().compareTo(intTipoOperacion)==0) {
						if (solicitud.getIntParaSubtipoOperacionCod().compareTo(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getIntParaSubTipoOperacion())==0) {
							if (solicitud.getListaEstructuraDetalle() != null) {
								for (ConfServEstructuraDetalle estructuraDetalle : solicitud.getListaEstructuraDetalle()) {
									estructuraDet = new EstructuraDetalle();
									estructuraDet.setId(new EstructuraDetalleId());
									// getSocioEstructuraDeOrigenPorPkSocio
									// **********************************************************************
									SocioEstructura socioEsctructura = new SocioEstructura();
									socioEsctructura = socioFacade.getSocioEstructuraDeOrigenPorPkSocio(beanSocioComp.getSocio().getId());
	
								
									estructuraDet.getId().setIntNivel(socioEsctructura.getIntNivel());
									estructuraDet.getId().setIntCodigo(	socioEsctructura.getIntCodigo());
									listaEstructuraDet = estructuraFacade.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
													estructuraDet.getId(),socioEsctructura.getIntTipoSocio(),
													socioEsctructura.getIntModalidad());
	
									if (listaEstructuraDet != null
											&& listaEstructuraDet.size() > 0) {
										for (EstructuraDetalle estructDetalle : listaEstructuraDet) {
											// if
											// (estructuraDetalle.getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())
											// &&
											// estructuraDetalle.getIntNivelPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())
											if (estructuraDetalle.getIntCodigoPk().compareTo(socioEsctructura.getIntCodigo())==0
													&& estructuraDetalle.getIntNivelPk().compareTo(socioEsctructura.getIntNivel())==0
													&& estructuraDetalle.getIntCaso().compareTo(estructDetalle.getId().getIntCaso())==0
													&& estructuraDetalle.getIntItemCaso().compareTo(estructDetalle.getId().getIntItemCaso())==0) {
												if (solicitud.getListaDetalle() != null	&& solicitud.getListaDetalle().size() > 0) {
	
													List<RequisitoLiquidacionComp> listaRequisitoLiquidacionCompTemp = new ArrayList<RequisitoLiquidacionComp>();
													for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
	
														if (detalle.getId().getIntPersEmpresaPk().compareTo(estructuraDetalle.getId().getIntPersEmpresaPk())==0
																&& detalle.getId().getIntItemSolicitud().compareTo(estructuraDetalle.getId().getIntItemSolicitud())==0) {
	
															requisitoLiquidacionComp = new RequisitoLiquidacionComp();
															requisitoLiquidacionComp.setDetalle(detalle);
															// listaRequisitoCreditoComp.add(requisitoCreditoComp);
															listaRequisitoLiquidacionCompTemp.add(requisitoLiquidacionComp);
														}
													}
	
													List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();
													/*
													 * 	public static final String PARAM_T_REQUISITOSDESCRIPCION_FONDOSEPELIO = "164";
														public static final String PARAM_T_REQUISITOSDESCRIPCION_FONDORETIRO = "165";
														public static final String PARAM_T_REQUISITOSDESCRIPCION_AES = "166";
													 */
													
													// validamos que solo se muestre las de agrupamioento A = b
													//listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO),"B");
	
													listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(intReqDesc,"B");
													for (int i = 0; i < listaTablaRequisitos.size(); i++) {
														for (int j = 0; j < listaRequisitoLiquidacionCompTemp.size(); j++) {
															if ((listaRequisitoLiquidacionCompTemp.get(j).getDetalle().getIntParaTipoDescripcion().intValue()) == 
																(listaTablaRequisitos.get(i).getIntIdDetalle().intValue())) {
																listaRequisitoLiquidacionComp.add(listaRequisitoLiquidacionCompTemp.get(j));
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
	
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
	}

	public void cancelarGrabarAutorizacionLiquidacion(ActionEvent event) {
		limpiarFormAutorizacionLiquidacion();
		limpiarCampos(event);
		formAutorizacionLiquidacionRendered = false;
	}

	public void aceptarAdjuntarDeJu() {
		FileUploadControllerServicio fileUploadController = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
	
		try {
			archivoDeJu = fileUploadController.getArchivoDeJu();
			fileUploadController = new FileUploadControllerServicio();
			// recuperarAdjuntosAutorizacion(expedienteCreditoCompSelected);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void aceptarAdjuntarReniecAut() {
		FileUploadControllerServicio fileUploadController = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		try {
			archivoReniec = fileUploadController.getArchivoReniec();
			fileUploadController = new FileUploadControllerServicio();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void quitarDeJu() {
		try {
			archivoDeJu = null;
			((FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio"))
					.setArchivoInfoCorp(null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void quitarReniecAut() {
		try {
			archivoReniec = null;
			((FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio"))
					.setArchivoReniec(null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void recuperarAdjuntosAutorizacion(	ExpedienteLiquidacion registroSeleccionadoBusqueda)
			throws BusinessException {
		List<AutorizaVerificaLiquidacion> listaVerificacionBD = null;
		// listaVerificacionBD = new ArrayList<AutorizaVerificacion>();
		try {
	
			listaVerificacionBD =  solicitudLiquidacionService.getListaVerificaLiquidacionPorPkExpediente(registroSeleccionadoBusqueda.getId());
				if (listaVerificacionBD.size() > 0) {
				beanAutorizaVerificacion = listaVerificacionBD.get(0);
					if (listaVerificacionBD.size() > 0) {
						// for(int k=0; k<listaVerificacionBD.size();k++){
						AutorizaVerificaLiquidacion verificacion = new AutorizaVerificaLiquidacion();
						verificacion = listaVerificacionBD.get(0);
		
						if (verificacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
							&& verificacion.getIntItemArchivoDeJu() != null
							&& verificacion.getIntItemHistoricoDeJu()!= null
							&& verificacion.getIntParaTipoArchivoDeJu() != null) {
	
								Archivo archivoDJ = new Archivo();
								ArchivoId archivoIdDJ = new ArchivoId();
		
								archivoIdDJ.setIntItemArchivo(verificacion.getIntItemArchivoDeJu());
								archivoIdDJ.setIntItemHistorico(verificacion.getIntItemHistoricoDeJu());
								archivoIdDJ.setIntParaTipoCod(verificacion.getIntParaTipoArchivoDeJu());
								// archivo.setId(new ArchivoId());
		
								archivoDJ = generalFacade.getArchivoPorPK(archivoIdDJ);
								if (archivoDJ.getId().getIntParaTipoCod() != null
									&& archivoDJ.getId().getIntItemArchivo() != null
									&& archivoDJ.getId().getIntItemHistorico() != null) {
										archivoDJ.setRutaActual(archivoDJ.getTipoarchivo().getStrRuta());
										archivoDJ.setStrNombrearchivo(archivoDJ.getStrNombrearchivo());
										archivoDeJu = archivoDJ;
								}
	
						} else {archivoDeJu = null;
					}	
					if (verificacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
						&& verificacion.getIntItemArchivoRen() != null
						&& verificacion.getIntItemHistoricoRen() != null
						&& verificacion.getIntParaTipoArchivoRen() != null) {
	
						Archivo archivoR = new Archivo();
						ArchivoId archivoIdR = new ArchivoId();
	
						archivoIdR.setIntItemArchivo(verificacion.getIntItemArchivoRen());
						archivoIdR.setIntItemHistorico(verificacion.getIntItemHistoricoRen());
						archivoIdR.setIntParaTipoCod(verificacion.getIntParaTipoArchivoRen());
						// archivo.setId(new ArchivoId());
	
						archivoR = generalFacade.getArchivoPorPK(archivoIdR);
						if (archivoR.getId().getIntParaTipoCod() != null
							&& archivoR.getId().getIntItemArchivo() != null
							&& archivoR.getId().getIntItemHistorico() != null) {
							archivoR.setRutaActual(archivoR.getTipoarchivo().getStrRuta());
							archivoR.setStrNombrearchivo(archivoR.getStrNombrearchivo());
							archivoReniec = archivoR;
						}
	
					} else {archivoReniec = null;}
				}
			} else {
				beanAutorizaVerificacion = new AutorizaVerificaLiquidacion();
				archivoDeJu = null;
				archivoReniec = null;
	
			}
	
		} catch (BusinessException e) {
			log.info("Error durante solicitudPrestamoFacade.getListaVerifificacionesCreditoPorPkExpediente --> "
					+ e);
		}
	}

	public List<AutorizaLiquidacionComp> recuperarEncargadosAutorizar(ExpedienteLiquidacion expedienteLiquidacion) {
		List<AutorizaLiquidacion> listaAutorizaLiquidacion = new ArrayList<AutorizaLiquidacion>();
		List<AutorizaLiquidacionComp> listaAutorizaLiquidacionComp = null;
		AutorizaLiquidacionComp autorizaLiquidacionComp = null;
		Persona persona = null;
		
		try {
			listaAutorizaLiquidacion = solicitudLiquidacionService.getListaAutorizaLiquidacionPorPkExpediente(registroSeleccionadoBusquedaComp.getExpedienteLiquidacion().getId());
				//autorizacionFacade.getListaAutorizaPrevisionPorPkExpediente(registroSeleccionadoBusqueda.getId());
			if (listaAutorizaLiquidacion != null && listaAutorizaLiquidacion.size() > 0) {
				listaAutorizaLiquidacionComp = new ArrayList<AutorizaLiquidacionComp>();
				for (AutorizaLiquidacion autorizaLiquidacion : listaAutorizaLiquidacion) {
					autorizaLiquidacionComp = new AutorizaLiquidacionComp();
					autorizaLiquidacionComp.setAutorizaLiquidacion(autorizaLiquidacion);
					
					persona = personaFacade.getPersonaNaturalPorIdPersona(autorizaLiquidacion.getIntPersUsuarioAutoriza());
					for (int k = 0; k < persona.getListaDocumento().size(); k++) {
						if (persona.getListaDocumento().get(k).getIntTipoIdentidadCod().compareTo(new Integer(Constante.PARAM_T_TIPODOCUMENTO_DNI)) == 0) {
							persona.setDocumento(persona.getListaDocumento().get(k));
							break;
						}
					}
					autorizaLiquidacionComp.setPersona(persona);
					listaAutorizaLiquidacionComp.add(autorizaLiquidacionComp);
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listaAutorizaLiquidacionComp;
	}

	public void cambioEstadoPersonaFallecida(List<FallecidoPrevision> lstFallecido){
		Persona persona = null;
		try {
			if(lstFallecido != null){
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
	/**
	 * Asociado al evento onchange del combo TIPO DE CAMBIO. Muestra el combo de Motivo de renunica o fecha de programacion 
	 * segun sea el caso.
	 * @param event
	 */
	public void loadCampoCambio(ActionEvent event) {
		//TablaFacadeRemote facade = null;
		String strIntTipoCambio = null;
		//List<Tabla> listaDocumento = null;
		Integer intTipoCambio = null;
		
		strIntTipoCambio = getRequestParameter("pIntTipoCambio");
		
		intTipoCambio = new Integer(strIntTipoCambio);
		log.info("event.getComponent.getId(): "+event.getComponent().getId());
	
		if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_FECHA_PROGRAMACION_PAGO)==0){
				blnCambiobeneficairio = false;
				blnCambioFecha = true;
				blnCambioMotivoRenuncia = false;
				intNuevoMotivoRenuncia = 0;
			
		}else if(intTipoCambio.compareTo(Constante.PARAM_T_AUTORIZACION_LIQUIDACION_TIPO_CAMBIO_MOTIVO_RENUNCIA)==0){
					blnCambiobeneficairio = false;
					blnCambioFecha = false;
					blnCambioMotivoRenuncia = true;
					dtNuevoFechaProgramacionPago = null;
			
		}else if(intTipoCambio.compareTo(0)==0){
				blnCambiobeneficairio = false;
				blnCambioFecha = false;
				blnCambioMotivoRenuncia = false;
				dtNuevoFechaProgramacionPago = null;
				intNuevoMotivoRenuncia = 0;
		}
	}
	
	public void irModificarSolicitudLiquidacionAutoriza(ActionEvent event){
		SolicitudLiquidacionController solicitudLiquidacionController = (SolicitudLiquidacionController) getSessionBean("solicitudLiquidacionController");
		solicitudLiquidacionController.irModificarSolicitudLiquidacionAutoriza2(event, registroSeleccionadoBusquedaComp.getExpedienteLiquidacion());	
	}	
	/**
	 * 
	 */
	public void cargarCombosBusqueda(){
		List<Sucursal> listaSucursal = null;
		try{
			// combo 1
			listaTablaDeSucursal = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
			listaSucursal =  empresaFacade.getListaSucursalPorPkEmpresa(usuario.getPerfil().getId().getIntPersEmpresaPk());
			
			if(listaSucursal != null && !listaSucursal.isEmpty()){
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
			}			
			// combo 2
			listaTablaCreditoEmpresa = tablaFacade.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), 1);
			
			
		} catch (BusinessException e) {
			log.error("Error en cargarCombosBusqueda ---> "+e);
			e.printStackTrace();
		} 
	}
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
	 * Se selecciona busqueda x fechas se bloque a la busqueda por condicion
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
	 * Se selecciona busqueda x fechas se bloque a la busqueda por condicion
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

	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		AutorizacionLiquidacionController.log = log;
	}
	public Usuario getUsuario() {
		return usuario;
	}
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
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public AutorizaVerificaLiquidacion getBeanAutorizaVerificacion() {
		return beanAutorizaVerificacion;
	}
	public void setBeanAutorizaVerificacion(
			AutorizaVerificaLiquidacion beanAutorizaVerificacion) {
		this.beanAutorizaVerificacion = beanAutorizaVerificacion;
	}
	public List<Tabla> getListaSubTipoSolicitudBusqueda() {
		return listaSubTipoSolicitudBusqueda;
	}
	public void setListaSubTipoSolicitudBusqueda(
			List<Tabla> listaSubTipoSolicitudBusqueda) {
		this.listaSubTipoSolicitudBusqueda = listaSubTipoSolicitudBusqueda;
	}
	public Persona getPersonaBusqueda() {
		return personaBusqueda;
	}
	public void setPersonaBusqueda(Persona personaBusqueda) {
		this.personaBusqueda = personaBusqueda;
	}
	public Integer getIntTipoPersona() {
		return intTipoPersona;
	}
	public void setIntTipoPersona(Integer intTipoPersona) {
		this.intTipoPersona = intTipoPersona;
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
	public List<Estructura> getListEstructura() {
		return listEstructura;
	}
	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}
	public List<Subsucursal> getListSubSucursal() {
		return listSubSucursal;
	}
	public void setListSubSucursal(List<Subsucursal> listSubSucursal) {
		this.listSubSucursal = listSubSucursal;
	}
	public List<Archivo> getListaArchivo() {
		return listaArchivo;
	}
	public void setListaArchivo(List<Archivo> listaArchivo) {
		this.listaArchivo = listaArchivo;
	}
	public Tabla getTablaEstado() {
		return tablaEstado;
	}
	public void setTablaEstado(Tabla tablaEstado) {
		this.tablaEstado = tablaEstado;
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
	public String getStrTxtMsgValidacion() {
		return strTxtMsgValidacion;
	}
	public void setStrTxtMsgValidacion(String strTxtMsgValidacion) {
		this.strTxtMsgValidacion = strTxtMsgValidacion;
	}
	public String getStrTxtMsgError() {
		return strTxtMsgError;
	}
	public void setStrTxtMsgError(String strTxtMsgError) {
		this.strTxtMsgError = strTxtMsgError;
	}
	public Boolean getFormAutorizacionLiquidacionRendered() {
		return formAutorizacionLiquidacionRendered;
	}
	public void setFormAutorizacionLiquidacionRendered(
			Boolean formAutorizacionLiquidacionRendered) {
		this.formAutorizacionLiquidacionRendered = formAutorizacionLiquidacionRendered;
	}
	public List<AutorizaLiquidacionComp> getListaAutorizaLiquidacionComp() {
		return listaAutorizaLiquidacionComp;
	}
	public void setListaAutorizaLiquidacionComp(
			List<AutorizaLiquidacionComp> listaAutorizaLiquidacionComp) {
		this.listaAutorizaLiquidacionComp = listaAutorizaLiquidacionComp;
	}
	public AutorizaLiquidacion getBeanAutorizaLiquidacion() {
		return beanAutorizaLiquidacion;
	}
	public void setBeanAutorizaLiquidacion(
			AutorizaLiquidacion beanAutorizaLiquidacion) {
		this.beanAutorizaLiquidacion = beanAutorizaLiquidacion;
	}
	public Integer getIntTipoCreditoFiltro() {
		return intTipoCreditoFiltro;
	}
	public void setIntTipoCreditoFiltro(Integer intTipoCreditoFiltro) {
		this.intTipoCreditoFiltro = intTipoCreditoFiltro;
	}
	public Integer getIntSubTipoCreditoFiltro() {
		return intSubTipoCreditoFiltro;
	}
	public void setIntSubTipoCreditoFiltro(Integer intSubTipoCreditoFiltro) {
		this.intSubTipoCreditoFiltro = intSubTipoCreditoFiltro;
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
	public EstadoLiquidacion getEstadoLiquidacionFiltro() {
		return estadoLiquidacionFiltro;
	}
	public void setEstadoLiquidacionFiltro(EstadoLiquidacion estadoLiquidacionFiltro) {
		this.estadoLiquidacionFiltro = estadoLiquidacionFiltro;
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
	public List<Tabla> getListaExpedienteLiquidacionX() {
		return listaExpedienteLiquidacionX;
	}
	public void setListaExpedienteLiquidacionX(List<Tabla> listaExpedienteLiquidacionX) {
		this.listaExpedienteLiquidacionX = listaExpedienteLiquidacionX;
	}
	public List<ExpedienteLiquidacion> getListaExpedienteLiquidacion() {
		return listaExpedienteLiquidacion;
	}
	public void setListaExpedienteLiquidacion(
			List<ExpedienteLiquidacion> listaExpedienteLiquidacion) {
		this.listaExpedienteLiquidacion = listaExpedienteLiquidacion;
	}
	public List<Tabla> getListaTipoVinculo() {
		return listaTipoVinculo;
	}
	public void setListaTipoVinculo(List<Tabla> listaTipoVinculo) {
		this.listaTipoVinculo = listaTipoVinculo;
	}
	public List<ExpedienteLiquidacion> getListaExpedienteLiquidacionSocio() {
		return listaExpedienteLiquidacionSocio;
	}
	public void setListaExpedienteLiquidacionSocio(
			List<ExpedienteLiquidacion> listaExpedienteLiquidacionSocio) {
		this.listaExpedienteLiquidacionSocio = listaExpedienteLiquidacionSocio;
	}
	public List<Subsucursal> getListaSubsucursal() {
		return listaSubsucursal;
	}
	public void setListaSubsucursal(List<Subsucursal> listaSubsucursal) {
		this.listaSubsucursal = listaSubsucursal;
	}
	public List<Tabla> getListaTablaSucursal() {
		return listaTablaSucursal;
	}
	public void setListaTablaSucursal(List<Tabla> listaTablaSucursal) {
		this.listaTablaSucursal = listaTablaSucursal;
	}
	public List<RequisitoLiquidacionComp> getListaRequisitoLiquidacionComp() {
		return listaRequisitoLiquidacionComp;
	}
	public void setListaRequisitoLiquidacionComp(
			List<RequisitoLiquidacionComp> listaRequisitoLiquidacionComp) {
		this.listaRequisitoLiquidacionComp = listaRequisitoLiquidacionComp;
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
	public Archivo getArchivoDeJu() {
		return archivoDeJu;
	}
	public void setArchivoDeJu(Archivo archivoDeJu) {
		this.archivoDeJu = archivoDeJu;
	}
	public Archivo getArchivoReniec() {
		return archivoReniec;
	}
	public void setArchivoReniec(Archivo archivoReniec) {
		this.archivoReniec = archivoReniec;
	}
	public List<AutorizaVerificaLiquidacion> getListaAutorizaVerificacionAdjuntos() {
		return listaAutorizaVerificacionAdjuntos;
	}
	public void setListaAutorizaVerificacionAdjuntos(
			List<AutorizaVerificaLiquidacion> listaAutorizaVerificacionAdjuntos) {
		this.listaAutorizaVerificacionAdjuntos = listaAutorizaVerificacionAdjuntos;
	}
	public List<ConfServSolicitud> getListaAutorizacionConfigurada() {
		return listaAutorizacionConfigurada;
	}
	public void setListaAutorizacionConfigurada(
			List<ConfServSolicitud> listaAutorizacionConfigurada) {
		this.listaAutorizacionConfigurada = listaAutorizacionConfigurada;
	}
	public List<Tabla> getListaTipoOperacion() {
		return listaTipoOperacion;
	}
	public void setListaTipoOperacion(List<Tabla> listaTipoOperacion) {
		this.listaTipoOperacion = listaTipoOperacion;
	}
	public boolean isBlnIsRetiro() {
		return blnIsRetiro;
	}
	public void setBlnIsRetiro(boolean blnIsRetiro) {
		this.blnIsRetiro = blnIsRetiro;
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
	public AutorizacionLiquidacionFacadeRemote getAutorizacionFacade() {
		return autorizacionFacade;
	}
	public void setAutorizacionFacade(
			AutorizacionLiquidacionFacadeRemote autorizacionFacade) {
		this.autorizacionFacade = autorizacionFacade;
	}
	public SolicitudLiquidacionService getSolicitudLiquidacionService() {
		return solicitudLiquidacionService;
	}
	public void setSolicitudLiquidacionService(
			SolicitudLiquidacionService solicitudLiquidacionService) {
		this.solicitudLiquidacionService = solicitudLiquidacionService;
	}
	public void setListSucursal(List<Sucursal> listSucursal) {
		this.listSucursal = listSucursal;
	}
	public List<Tabla> getListaTipoCuenta() {
		return listaTipoCuenta;
	}
	public void setListaTipoCuenta(List<Tabla> listaTipoCuenta) {
		this.listaTipoCuenta = listaTipoCuenta;
	}
	public Integer getIntTipoCambio() {
		return intTipoCambio;
	}
	public void setIntTipoCambio(Integer intTipoCambio) {
		this.intTipoCambio = intTipoCambio;
	}
	public Date getDtNuevoFechaProgramacionPago() {
		return dtNuevoFechaProgramacionPago;
	}
	public void setDtNuevoFechaProgramacionPago(Date dtNuevoFechaProgramacionPago) {
		this.dtNuevoFechaProgramacionPago = dtNuevoFechaProgramacionPago;
	}
	public Integer getIntNroTotalPersonarPerfilesAprobar() {
		return intNroTotalPersonarPerfilesAprobar;
	}
	public void setIntNroTotalPersonarPerfilesAprobar(
			Integer intNroTotalPersonarPerfilesAprobar) {
		this.intNroTotalPersonarPerfilesAprobar = intNroTotalPersonarPerfilesAprobar;
	}
	public String getStrMotivoCambio() {
		return strMotivoCambio;
	}
	public void setStrMotivoCambio(String strMotivoCambio) {
		this.strMotivoCambio = strMotivoCambio;
	}
	public boolean isBlnCambioMotivoRenuncia() {
		return blnCambioMotivoRenuncia;
	}
	public void setBlnCambioMotivoRenuncia(boolean blnCambioMotivoRenuncia) {
		this.blnCambioMotivoRenuncia = blnCambioMotivoRenuncia;
	}
	public boolean isBlnCambioFecha() {
		return blnCambioFecha;
	}
	public void setBlnCambioFecha(boolean blnCambioFecha) {
		this.blnCambioFecha = blnCambioFecha;
	}
	public boolean isBlnCambiobeneficairio() {
		return blnCambiobeneficairio;
	}
	public void setBlnCambiobeneficairio(boolean blnCambiobeneficairio) {
		this.blnCambiobeneficairio = blnCambiobeneficairio;
	}
	public List<Tabla> getListaTipoCambio() {
		return listaTipoCambio;
	}
	public void setListaTipoCambio(List<Tabla> listaTipoCambio) {
		this.listaTipoCambio = listaTipoCambio;
	}
	public List<Tabla> getListaMotivoRenuncia() {
		return listaMotivoRenuncia;
	}
	public void setListaMotivoRenuncia(List<Tabla> listaMotivoRenuncia) {
		this.listaMotivoRenuncia = listaMotivoRenuncia;
	}
	public Integer getIntNuevoMotivoRenuncia() {
		return intNuevoMotivoRenuncia;
	}
	public void setIntNuevoMotivoRenuncia(Integer intNuevoMotivoRenuncia) {
		this.intNuevoMotivoRenuncia = intNuevoMotivoRenuncia;
	}
	public SocioComp getBeanSocioComp() {
		return beanSocioComp;
	}
	public void setBeanSocioComp(SocioComp beanSocioComp) {
		this.beanSocioComp = beanSocioComp;
	}
	public CuentaFacadeRemote getCuentaFacade() {
		return cuentaFacade;
	}
	public void setCuentaFacade(CuentaFacadeRemote cuentaFacade) {
		this.cuentaFacade = cuentaFacade;
	}
	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}
	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}
	public LibroDiarioFacadeRemote getLibroDiarioFacade() {
		return libroDiarioFacade;
	}
	public void setLibroDiarioFacade(LibroDiarioFacadeRemote libroDiarioFacade) {
		this.libroDiarioFacade = libroDiarioFacade;
	}
	public AuditoriaFacadeRemote getAuditoriafacade() {
		return auditoriafacade;
	}
	public void setAuditoriafacade(AuditoriaFacadeRemote auditoriafacade) {
		this.auditoriafacade = auditoriafacade;
	}
	public LiquidacionFacadeRemote getLiquidacionFacade() {
		return liquidacionFacade;
	}
	public void setLiquidacionFacade(LiquidacionFacadeRemote liquidacionFacade) {
		this.liquidacionFacade = liquidacionFacade;
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
	public EstructuraDetalle getEstructuraDetalleBusqueda() {
		return estructuraDetalleBusqueda;
	}
	public void setEstructuraDetalleBusqueda(
			EstructuraDetalle estructuraDetalleBusqueda) {
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
	public List<Tabla> getListaTipoConsultaBusqueda() {
		return listaTipoConsultaBusqueda;
	}
	public void setListaTipoConsultaBusqueda(List<Tabla> listaTipoConsultaBusqueda) {
		this.listaTipoConsultaBusqueda = listaTipoConsultaBusqueda;
	}
	public List<Subsucursal> getListaSubsucursalBusq() {
		return listaSubsucursalBusq;
	}
	public void setListaSubsucursalBusq(List<Subsucursal> listaSubsucursalBusq) {
		this.listaSubsucursalBusq = listaSubsucursalBusq;
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
	public EstadoLiquidacion getEstadoCondicionFiltro() {
		return estadoCondicionFiltro;
	}
	public void setEstadoCondicionFiltro(EstadoLiquidacion estadoCondicionFiltro) {
		this.estadoCondicionFiltro = estadoCondicionFiltro;
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
	public ExpedienteLiquidacionComp getRegistroSeleccionadoBusquedaComp() {
		return registroSeleccionadoBusquedaComp;
	}
	public void setRegistroSeleccionadoBusquedaComp(
			ExpedienteLiquidacionComp registroSeleccionadoBusquedaComp) {
		this.registroSeleccionadoBusquedaComp = registroSeleccionadoBusquedaComp;
	}
	public Boolean getBlnBotonAutorizar() {
		return blnBotonAutorizar;
	}
	public void setBlnBotonAutorizar(Boolean blnBotonAutorizar) {
		this.blnBotonAutorizar = blnBotonAutorizar;
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
}
