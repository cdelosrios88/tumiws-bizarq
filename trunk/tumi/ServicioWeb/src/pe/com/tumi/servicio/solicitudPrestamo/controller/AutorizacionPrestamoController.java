package pe.com.tumi.servicio.solicitudPrestamo.controller;

import java.io.IOException;
import java.io.OutputStream;
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
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.FileUtil;
import pe.com.tumi.common.MyFile;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.facade.LibroDiarioFacadeRemote;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalleId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.fileupload.FileUploadControllerServicio;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoEmpresa;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeLocal;
import pe.com.tumi.servicio.configuracion.facade.ConfSolicitudFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacion;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.AutorizaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeLocal;

public class AutorizacionPrestamoController {
	protected static Logger log = Logger.getLogger(AutorizacionPrestamoController.class);
	private AutorizaCredito beanAutorizaCredito;
	private AutorizaVerificacion beanAutorizaVerificacion;
	private ExpedienteCreditoComp expedienteCreditoCompSelected;

	private Integer intIdTipoPersona;
	private Integer intBusqSolicPtmo;
	private String strNroSolicitud;
	private Integer intTipoSucursalBusq;
	private Integer intTotalSucursales;
	private Integer intIdDependencia;
	private Integer intIdTipoPrestamo;
	private Integer intIdEstadoPrestamo;
	private Date dtFecInicio;
	private Date dtFecFin;

	private Boolean formAutorizacionPrestamoRendered;
	private String strTxtMsgPerfil;
	private String strTxtMsgUsuario;
	private String strTxtMsgVigencia;
	private String strTxtMsgError;
	private String strTxtMsgValidacion;
	private List<RequisitoCreditoComp> listaRequisitoCreditoComp;
	private Integer intParaTipoDescripcion;
	private Integer intParaTipoOperacionPersona;
	private Archivo archivoInfoCorp;
	private Archivo archivoReniec;

	private List<AutorizaCreditoComp> listaAutorizaCreditoComp;
	private List<ExpedienteCreditoComp> listaAutorizacionCreditoComp = new ArrayList<ExpedienteCreditoComp>();
	private List<AutorizaVerificacion> listaAutorizaVerificacionAdjuntos;
	private List<ConfServSolicitud> listaAutorizacionConfigurada = null;
	private ConfServSolicitud autorizacionConfigurada = null;
	private List<Tabla> listaTipoOperacion;
	private List<Sucursal> listSucursal;

	private ConfSolicitudFacadeLocal solicitudFacade = null;
	private GeneralFacadeRemote generalFacade = null;
	private EmpresaFacadeRemote empresaFacade = null;
	private ConceptoFacadeRemote conceptoFacade = null;

	private SocioFacadeRemote socioFacade = null;
	private TablaFacadeRemote tablaFacade = null;
	private SolicitudPrestamoFacadeLocal solicitudPrestamoFacade = null;
	private PersonaFacadeRemote personaFacade = null;
	private LibroDiarioFacadeRemote libroDiarioFacade = null;
	private PermisoFacadeRemote permisoFacade = null;
	private CreditoFacadeRemote creditoFacade = null;
	
	//BUSQUEDA - 22.05.2013 - CGD
	// sesion
	private Usuario 	usuario;
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	private Integer		SUCURSAL_USUARIO_ID;
	private Integer		SUBSUCURSAL_USUARIO_ID;

	private Integer 		intTipoConsultaBusqueda; // Nombres DNI RUC Razón Social
	private EstadoCredito 	estadoAutorizacionBusqueda;
	private List<Tabla> 	listaTipoConsultaBusqueda; 
	private String 			strConsultaBusqueda;
	private Boolean 		blnTxtBusqueda;
	private String 			strNumeroSolicitudBusq; 
	private Boolean 		blnBusquedaCondicion;
	private EstadoCredito	estadoCondicionFiltro;
	private	List 			listaTablaEstadoPago;
	private Integer			intTipoCreditoFiltro;
	private List			listaTablaTipoCredito;
	private Boolean 		blnBusquedaFechas;
	private EstadoCredito 	estadoAutorizacionFechas;
	private EstadoCredito 	estadoAutorizacionSuc;
	private List<Tabla> 	listaTablaDeSucursal;
	private Integer			intIdSubsucursalFiltro;
	private List			listaSubsucursalBusq;
	private List<Tabla> 	listaTablaCreditoEmpresa;
	private EstructuraDetalle estructuraDetalleBusqueda;
	private List			listaTablaSucursal;

	private List<ExpedienteCreditoComp> listaExpedienteCreditoComp;
	//private ExpedienteCreditoComp  registroSeleccionadoBusqueda;
	//private Boolean blnMostrarEliminar;
	private Boolean blnMostrarAutorizar;
	
	private Boolean blnBloquearCampo;
	private Boolean formAutorizacionViewPrestamoRendered;
	private String strSolicitudPrestamo;
	
	private Boolean blnBloquearXCuenta;
	private String strMensajeValidacionCuenta;
	private String strMsgErrorValidarMovimientos;
	
	// parametros de busqueda - cgd 18.10.2013
	private Integer intBusqTipo; 	
	private String strBusqCadena;		    
	private String strBusqNroSol;		   
	private Integer intBusqSucursal;			  
	private Integer intBusqEstado;		    
	private Date dtBusqFechaEstadoDesde;  
	private Date dtBusqFechaEstadoHasta;
	private	List<Sucursal> listaSucursal;
	private Integer intBusqTipoCreditoEmpresa;
	private ExpedienteCreditoComp expedienteCompBusq;
	
	

	/**
	 * 
	 * @throws NumberFormatException
	 */
	public AutorizacionPrestamoController() throws NumberFormatException {
		beanAutorizaCredito = new AutorizaCredito();
		beanAutorizaVerificacion = new AutorizaVerificacion();
		formAutorizacionPrestamoRendered = false;
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		formAutorizacionViewPrestamoRendered = false;
		strSolicitudPrestamo = Constante.MANTENIMIENTO_GRABAR;
		

		try {
			solicitudFacade = (ConfSolicitudFacadeLocal) EJBFactory.getLocal(ConfSolicitudFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);			
			permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			creditoFacade = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);

			listaTablaEstadoPago = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_ESTADOSOLICPRESTAMO), "C");
			listaTablaTipoCredito = tablaFacade.getListaTablaPorAgrupamientoB(Integer.parseInt(Constante.PARAM_T_TIPOCREDITOEMPRESA), 1);
			listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
			//Ordenamos por nombre
			Collections.sort(listaSucursal, new Comparator<Sucursal>(){
				public int compare(Sucursal sucUno, Sucursal sucDos) {
					return sucUno.getJuridica().getStrSiglas().compareTo(sucDos.getJuridica().getStrSiglas());
				}
			});
			
			//IdMaestro(new Integer());
			limpiarResultadoBusqueda();
			cargarUsuario();
			
		} catch (EJBFactoryException e) {
			log.error("error: " + e.getMessage());
		} catch (BusinessException e) {
			log.error("error: " + e.getMessage());
		} finally {
			inicio();
		}
	}
	
	
	/**
	 * 
	 */
	public void inicio() {
		try {
			cargarUsuario();
			limpiarFormAutorizacionPrestamo();
			strTxtMsgError = "";
			strTxtMsgPerfil = "";
			strTxtMsgValidacion = "";
			strTxtMsgVigencia = "";
			formAutorizacionPrestamoRendered = false;
			formAutorizacionViewPrestamoRendered = false;
			
			intTipoConsultaBusqueda = 0;
			strConsultaBusqueda = "";
			blnTxtBusqueda = true;
			blnBusquedaCondicion = false;
			strNumeroSolicitudBusq = "";
			estadoCondicionFiltro = new EstadoCredito();
			estadoCondicionFiltro.getId().setIntPersEmpresaPk(EMPRESA_USUARIO);
			estadoCondicionFiltro.setIntParaEstadoCreditoCod(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD);
			intTipoCreditoFiltro = 0;
			blnBusquedaFechas = true;
			estadoAutorizacionFechas = new EstadoCredito();
			estadoAutorizacionFechas.getId().setIntPersEmpresaPk(EMPRESA_USUARIO);
			estadoAutorizacionFechas.setIntParaEstadoCreditoCod(0);
			estadoAutorizacionSuc = new EstadoCredito();
			intIdSubsucursalFiltro = 0;
			
			cargarListaTablaSucursal();
			cargarCombosBusqueda();
			blnMostrarAutorizar = Boolean.TRUE;
			
			estructuraDetalleBusqueda = new EstructuraDetalle(); // ***
			autorizacionConfigurada = new ConfServSolicitud();
		} catch (Exception e) {
			log.error("Error en inicio ---> "+e);
			e.printStackTrace();
		}
	}

	
	
	/**
	 * Metodo de limpieza asociado a h:outputText de nuevoCreditoBody.jsp
	 * Ayuda a controlar los tabs de acuerdo a perfiles de usuario logueado.
	 * @return
	 */
	public String  getLimpiarAutorizacion(){
		cargarUsuario();
		limpiarFormAutorizacionPrestamo();
		limpiarFiltros(null);
		limpiarResultadoBusqueda();
		formAutorizacionPrestamoRendered = false;
		setStrTxtMsgUsuario("");
		setStrTxtMsgPerfil("");
		return "";
	}
	

	/**
	 * 
	 */
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
		SUCURSAL_USUARIO_ID = usuario.getSucursal().getId().getIntIdSucursal();
		SUBSUCURSAL_USUARIO_ID = usuario.getSubSucursal().getId().getIntIdSubSucursal();		
	}
	
	/**
	 * Carga la lista de sucursales
	 * @throws Exception
	 */
	private void cargarListaTablaSucursal() throws Exception{
		List<Sucursal>listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
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
	 * Cancela la grabacion dela autorizacion.
	 * Oculta formulario.
	 * @param event
	 */
	public void cancelarGrabarAutorizacionPrestamo(ActionEvent event) {
		limpiarFormAutorizacionPrestamo();
		formAutorizacionPrestamoRendered = false;
		formAutorizacionViewPrestamoRendered = false;
	}

	
	/**
	 * Carga los combos utilizados en la grilla de busqueda.
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
	 * Carga el combo de sub sucursal en la grilla de busqueda.
	 */
	public void seleccionarSucursalBusq(){
		try{
			if(estadoAutorizacionSuc.getIntIdUsuSucursalPk().intValue()>0){
				listaSubsucursalBusq = empresaFacade.getListaSubSucursalPorIdSucursal(estadoAutorizacionSuc.getIntIdUsuSucursalPk());
			}else{
				listaSubsucursalBusq = new ArrayList<Subsucursal>();
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	/**
	 * Limpia los filtors utilizados en la busqueda
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
	 * Carga el 2do combo de Tipo de actividad segun 1er combo seleccionado
	 * @param event
	 */
	public void renderizarTextBusqueda(ActionEvent event)  {
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
	 * Metodo que se ejecuta al seleccionar un registro de la grilla de busqueda
	 * @param event
	 */
	public void seleccionarRegistro(ActionEvent event){
		ExpedienteCreditoId expedienteCreditoId = null;
		try{
			expedienteCreditoCompSelected = (ExpedienteCreditoComp)event.getComponent().getAttributes().get("itemExpCreditoAut");
			if(expedienteCreditoCompSelected != null){
				expedienteCreditoId = new ExpedienteCreditoId();
				expedienteCreditoId.setIntCuentaPk(expedienteCreditoCompSelected.getExpedienteCredito().getId().getIntCuentaPk());
				expedienteCreditoId.setIntPersEmpresaPk(expedienteCreditoCompSelected.getExpedienteCredito().getId().getIntPersEmpresaPk());
				validarOperacionAutorizar();
				validarEstadoCuenta(expedienteCreditoId);
			}
		}catch (Exception e) {
			log.error("Error Exception en seleccionarRegistro ---> "+e);
		}
	}
	

	
	
	/**
	 * Valida si la operacion Autorizar se visualiza o no en el popup de acciones.
	 * Solo se podra Autorizar si el expedientee esta en estado Solicitud
	 */
	public void validarOperacionAutorizar(){
		try {
			if(expedienteCreditoCompSelected != null){				
				if(expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0){
					blnMostrarAutorizar = Boolean.TRUE;
				}else{
					blnMostrarAutorizar = Boolean.FALSE;
				}
			}	
		} catch (Exception e) {
			log.error("Error en validarOperacionAutorizar ---> "+e);
		}
	}

	
	
	/**
	 * 
	 */
	public void bloquearCamposSegunEstado(){
		try {
			if(expedienteCreditoCompSelected != null){				
				if(expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO)==0){
					blnBloquearCampo = Boolean.TRUE;
				}else{
					blnBloquearCampo = Boolean.FALSE;
				}
			}	
		} catch (Exception e) {
			log.error("Error en bloquearCamposSegunEstado ---> "+e);
		}
	}
	
	
	/**
	 * Realiza la Busqueda de las Solictudes a Autorizar.
	 * @param event
	 */
	public void listarAutorizacionPrestamo(ActionEvent event) {
		SolicitudPrestamoFacadeLocal facade = null;
		try {
			listaAutorizacionCreditoComp.clear();
			limpiarFormAutorizacionPrestamo();
			facade = (SolicitudPrestamoFacadeLocal) EJBFactory.getLocal(SolicitudPrestamoFacadeLocal.class);
			
			listaExpedienteCreditoComp = new ArrayList<ExpedienteCreditoComp>();
			expedienteCompBusq = new ExpedienteCreditoComp();
			expedienteCompBusq.setIntBusqTipo(intBusqTipo);
			expedienteCompBusq.setStrBusqCadena(strBusqCadena.trim());
			expedienteCompBusq.setStrBusqNroSol(strBusqNroSol);
			expedienteCompBusq.setIntBusqSucursal(intBusqSucursal);
			expedienteCompBusq.setIntBusqEstado(intBusqEstado);
			expedienteCompBusq.setDtBusqFechaEstadoDesde(dtBusqFechaEstadoDesde);
			expedienteCompBusq.setDtBusqFechaEstadoHasta(dtBusqFechaEstadoHasta);
			expedienteCompBusq.setIntBusqTipoCreditoEmpresa(intBusqTipoCreditoEmpresa);
			
			listaExpedienteCreditoComp = facade.getListaBusqCreditosAutFiltros(expedienteCompBusq);
		} catch (BusinessException e) {
			log.error("Error BusinessException en listarAutorizacionPrestamo ---> "+e);
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			log.error("Error EJBFactoryException en listarAutorizacionPrestamo ---> "+e);
			e.printStackTrace();
		}
	}

	
	/**
	 * 
	 * @param event
	 */
	public void setSelectedExpedienteCredito(ActionEvent event) {
		try {
			String selectedRow = getRequestParameter("rowExpedienteCredito");
			ExpedienteCreditoComp expedienteCreditoComp = null;
			limpiarFormAutorizacionPrestamo();
			inicio();
			// cancelarGrabarAutorizacionPrestamo(event);
			for (int i = 0; i < listaAutorizacionCreditoComp.size(); i++) {
				expedienteCreditoComp = listaAutorizacionCreditoComp.get(i);
				if (i == Integer.parseInt(selectedRow)) {
					setExpedienteCreditoCompSelected(expedienteCreditoComp);
					try {
						recuperarAdjuntosAutorizacion(expedienteCreditoComp);
					} catch (BusinessException e) {
						log.info("Error en setSelectedExpedienteCredito --> " + e);
					}
					break;
				}
			}
		} catch (Exception e) {
			log.error("Error en setSelectedExpedienteCredito ---> "+e);
		}
	}

	/**
	 * 
	 * @param expedienteSeleccionado
	 * @throws BusinessException
	 */
	private void recuperarAdjuntosAutorizacion(ExpedienteCreditoComp expedienteSeleccionado) throws BusinessException {
		List<AutorizaVerificacion> listaVerificacionBD = null;
		
		try {
			listaVerificacionBD = solicitudPrestamoFacade.getListaVerifificacionesCreditoPorPkExpediente(expedienteCreditoCompSelected.getExpedienteCredito().getId());

			if(listaVerificacionBD != null && !listaVerificacionBD.isEmpty()){
					for (AutorizaVerificacion autorizaVerificacion : listaVerificacionBD) {
						if(autorizaVerificacion.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
							beanAutorizaVerificacion = autorizaVerificacion;
							if (autorizaVerificacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
								&& autorizaVerificacion.getIntItemArchivoRen() != null
								&& autorizaVerificacion.getIntItemHistoricoRen() != null
								&& autorizaVerificacion.getIntParaTipoArchivoRenCod() != null) {

									Archivo archivoR = new Archivo();
									ArchivoId archivoIdR = new ArchivoId();
			
									archivoIdR.setIntItemArchivo(autorizaVerificacion.getIntItemArchivoRen());
									archivoIdR.setIntItemHistorico(autorizaVerificacion.getIntItemHistoricoRen());
									archivoIdR.setIntParaTipoCod(autorizaVerificacion.getIntParaTipoArchivoRenCod());
			
									archivoR = generalFacade.getArchivoPorPK(archivoIdR);
									if (archivoR.getId().getIntParaTipoCod() != null
										&& archivoR.getId().getIntItemArchivo() != null
										&& archivoR.getId().getIntItemHistorico() != null) {
											archivoR.setRutaActual(archivoR.getTipoarchivo().getStrRuta());
											archivoR.setStrNombrearchivo(archivoR.getStrNombrearchivo());
											archivoReniec = archivoR;
									}
							}  else {archivoReniec = null;}

							if (autorizaVerificacion.getIntParaEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)
								&& autorizaVerificacion.getIntItemArchivoInfo() != null
								&& autorizaVerificacion.getIntItemHistoricoInfo() != null
								&& autorizaVerificacion.getIntParaTipoArchivoInfoCod() != null) {

								Archivo archivoI = new Archivo();
								ArchivoId archivoIdI = new ArchivoId();

								archivoIdI.setIntItemArchivo(autorizaVerificacion.getIntItemArchivoInfo());
								archivoIdI.setIntItemHistorico(autorizaVerificacion.getIntItemHistoricoInfo());
								archivoIdI.setIntParaTipoCod(autorizaVerificacion.getIntParaTipoArchivoInfoCod());

								archivoI = generalFacade.getArchivoPorPK(archivoIdI);
								if (archivoI.getId().getIntParaTipoCod() != null
									&& archivoI.getId().getIntItemArchivo() != null
									&& archivoI.getId().getIntItemHistorico() != null) {
									archivoI.setRutaActual(archivoI.getTipoarchivo().getStrRuta());
									archivoI.setStrNombrearchivo(archivoI.getStrNombrearchivo());
									archivoInfoCorp = archivoI;
								}

							} else {archivoInfoCorp = null;}
								break;
						}else {
							beanAutorizaVerificacion = new AutorizaVerificacion();
							archivoInfoCorp = null;
							archivoReniec = null;
						}
					}
				} else {
						beanAutorizaVerificacion = new AutorizaVerificacion();
						archivoInfoCorp = null;
						archivoReniec = null;
			}

		} catch (BusinessException e) {
			log.info("Error en recuperarAdjuntosAutorizacion --> "+ e);
		}
	}

	
	/**
	 * 
	 */
	public void limpiarFormAutorizacionPrestamo() {
		try {
			beanAutorizaCredito = new AutorizaCredito();
			beanAutorizaVerificacion = new AutorizaVerificacion();
			listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
			blnMostrarAutorizar = Boolean.TRUE;
			strTxtMsgError = "";
			autorizacionConfigurada = new ConfServSolicitud();
			beanAutorizaCredito = new AutorizaCredito();
			beanAutorizaCredito.setIntParaTipoAureobCod(0);
			beanAutorizaCredito.setIntParaEstadoAutorizar(0);
			if(listaAutorizaCreditoComp != null && !listaAutorizaCreditoComp.isEmpty()){
				listaAutorizaCreditoComp.clear();
			}
		} catch (Exception e) {
			log.error("Error en limpiarFormAutorizacionPrestamo ---> "+e);
		}
	}

	
	/**
	 * 
	 * @param event
	 */
	public void reloadCboTipoMotivoEstado(ValueChangeEvent event) {
		Integer intIdOperacion;
		try {
			intIdOperacion = Integer.parseInt("" + event.getNewValue());
			listaTipoOperacion = tablaFacade.getListaTablaPorAgrupamientoB( new Integer(Constante.PARAM_T_TIPOMOTIVOESTADOAUTPTMO), intIdOperacion);
		} catch (BusinessException e) {
			log.error("Error  en reloadCboTipoMotivoEstado ---> " + e.getMessage());
		}
	}

	
	/**
	 * Valida la Situacion de la cuenta del expediente seleccionado, a find e permitir solo los de Estado ACTIVO
	 * @return
	 */
	public Boolean validarEstadoCuenta(ExpedienteCreditoId expId){
		Boolean blnValido= null;
		CuentaId ctaIdExp = null;
		Cuenta ctaExpediente = null;
		strMensajeValidacionCuenta = "";
		CuentaFacadeRemote cuentaFacade = null;
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
						strMensajeValidacionCuenta = "No se puede Autorizar. Solicitud de Ptmo pertenece a una Cuenta No Vigente.";
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en validarEstadoCuenta ----> "+e);
		}
		return blnValido;
	}
	
	
	
	/**
	 * Metodo asociado al Boton AUTORIZAR de popup de acciones de autorizacion de credito.
	 * @param event
	 */
	public void showAutorizacionPrestamo(ActionEvent event) {
		blnBloquearCampo = false;
		List<ConfServSolicitud> listaReqAutInicial = null;
		ConfServSolicitud confServSolicitud = null;
		listaAutorizacionConfigurada = null;
		ConfServSolicitud reaAutFinal = null;
		/* 
		1.Recuperar los req auts segun expediente y se validan, a fin de 
		reducirlos:
				LISTA_REQ_AUT_VALIDADA 
		2.Recuperamos las lista de ya autorizaciones registardas:
				LISTA_AUTORIZACIONES_YA_REGISTRADAS
			si =! 0 --- ya  registro el zonal.
				verificar si req aut es perfil o usuario. Solo aplica uno.
				validar al usuario logueado, si existe en el reqaut
				que ya posee un registro.
				si existe --> se le permite continuar
				no existe --> mensaje error.

			si = 0 -- aun no zonal.
				bloquear, mostrar mensaje de error.
		 */
		
		try {
				cargarUsuario();
				listaAutorizacionConfigurada = new ArrayList<ConfServSolicitud>();
				strTxtMsgPerfil = "";
				strTxtMsgUsuario = "";
				strTxtMsgValidacion = "";
				strTxtMsgError = "";
				strSolicitudPrestamo = Constante.MANTENIMIENTO_GRABAR;

				if(listaAutorizaCreditoComp != null && !listaAutorizaCreditoComp.isEmpty()){
					listaAutorizaCreditoComp.clear();
				}

				cargarUsuario();
				recuperarAdjuntosAutorizacion(expedienteCreditoCompSelected);
				// Recuperar Credito en base al expediente:
				CreditoId creditoId = new CreditoId();
				creditoId.setIntItemCredito(expedienteCreditoCompSelected.getExpedienteCredito().getIntItemCredito());
				creditoId.setIntParaTipoCreditoCod(expedienteCreditoCompSelected.getExpedienteCredito().getIntParaTipoCreditoCod());
				creditoId.setIntPersEmpresaPk(expedienteCreditoCompSelected.getExpedienteCredito().getIntPersEmpresaCreditoPk());

				Credito creditoRec = null;
				creditoRec = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
				confServSolicitud = new ConfServSolicitud();
				confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_PRESTAMO);
				listaReqAutInicial = solicitudFacade.buscarConfSolicitudAutorizacion (confServSolicitud, null, null, null);

				if(listaReqAutInicial != null && !listaReqAutInicial.isEmpty()){
					for (ConfServSolicitud reqAut : listaReqAutInicial) {
						if(reqAut.getIntParaTipoRequertoAutorizaCod().compareTo(Constante.PARAM_T_TIPOREQAUT_AUTORIZACION)==0){
							if (reqAut.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0) {
								// Se valida Vigencia, Estado, Montos, Tipos, SubTipos, etc.
								reqAut = validarConfiguracion(reqAut, creditoRec);
								if (reqAut != null) {
									// lista de req auts filtrada.
									listaAutorizacionConfigurada.add(reqAut);
								}
							}
						}
					}
					
					//1. Lista de los req auts configurados y validados
					if(listaAutorizacionConfigurada != null && !listaAutorizacionConfigurada.isEmpty()){
						// Recuperamos las autorizaciones ya registradas para el expediente. 
						// Se carga la variable global => listaAutorizaCreditoComp.
						listarEncargadosAutorizar();
						
						// ya existen autorizaciones, x lo tanto el Jefe Zonal ya autorizo...
						if(listaAutorizaCreditoComp != null && !listaAutorizaCreditoComp.isEmpty()){
							//Boolean blnExisteZonal = Boolean.FALSE;		// Debe de existir
							Boolean blnExisteLogueado = Boolean.FALSE;	// No deberia de existir
							
							
							forTotal:
							for (ConfServSolicitud reqAutRecuperado : listaAutorizacionConfigurada) {
								for (AutorizaCreditoComp autorizacionRegistrada : listaAutorizaCreditoComp) {
									
									if(reqAutRecuperado.getListaPerfil() != null && !reqAutRecuperado.getListaPerfil().isEmpty()){
										for (ConfServPerfil perfil : reqAutRecuperado.getListaPerfil()) {
											if(autorizacionRegistrada.getAutorizaCredito().getIntIdPerfilAutoriza().compareTo(perfil.getIntIdPerfilPk())==0
												&& autorizacionRegistrada.getAutorizaCredito().getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0
												&& autorizacionRegistrada.getAutorizaCredito().getIntIdPerfilAutoriza().compareTo(usuario.getPerfil().getId().getIntIdPerfil())==0){
														blnExisteLogueado = Boolean.TRUE;
														break forTotal;
												}
										}
									}
									
									if(reqAutRecuperado.getListaUsuario() != null && !reqAutRecuperado.getListaUsuario().isEmpty()){
										for (ConfServUsuario usuario : reqAutRecuperado.getListaUsuario()) {
											if(autorizacionRegistrada.getAutorizaCredito().getIntPersUsuarioAutoriza().compareTo(usuario.getIntPersUsuarioPk())==0
												&& autorizacionRegistrada.getAutorizaCredito().getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0
												&& autorizacionRegistrada.getAutorizaCredito().getIntPersUsuarioAutoriza().compareTo(usuario.getIntPersUsuarioPk())==0){
														blnExisteLogueado = Boolean.TRUE;
														break forTotal;
												}
										}
									}
								}
							}
						
							if(blnExisteLogueado){
							// usario logueado ya autorizo - mensaje de error
								formAutorizacionPrestamoRendered = false;
								strTxtMsgError = "Usuario logueado ya Autorizó o no esta configurado.";

							}else{
								
								// Validar que el usuario/perfil logueado exista en la configuracion	
								for (ConfServSolicitud reqAutConfigurado : listaAutorizacionConfigurada) {
									Boolean blnAplicaPerfil = Boolean.FALSE;
									Boolean blnAplicaUsuario = Boolean.FALSE;
									Boolean blnProcede = Boolean.FALSE;

									// Es x perfil
									if(reqAutConfigurado.getListaPerfil() != null 
									&& !reqAutConfigurado.getListaPerfil().isEmpty()){
										blnAplicaPerfil = Boolean.TRUE;	
									}

									// Es x Usuario
									if(reqAutConfigurado.getListaUsuario() != null 
									&& !reqAutConfigurado.getListaUsuario().isEmpty()){
										blnAplicaUsuario = Boolean.TRUE;		
									}

									if(blnAplicaPerfil){
										for (ConfServPerfil perfilReqAut : reqAutConfigurado.getListaPerfil()) {
											if(perfilReqAut.getIntIdPerfilPk().compareTo(usuario.getPerfil().getId().getIntIdPerfil())==0){
												blnProcede = Boolean.TRUE;
												break;
											}
										}
									}else if(blnAplicaUsuario){
										for (ConfServUsuario usuarioReqAut : reqAutConfigurado.getListaUsuario()) {
											if(usuarioReqAut.getIntPersUsuarioPk().compareTo(usuario.getIntPersPersonaPk())==0){
												blnProcede = Boolean.TRUE;
												break;
											}
										}
									}	

									if(blnProcede){
										reaAutFinal = new ConfServSolicitud();
										reaAutFinal = reqAutConfigurado;

										if(reaAutFinal != null){
											listaAutorizacionConfigurada.clear();
											autorizacionConfigurada = reaAutFinal;
											formAutorizacionPrestamoRendered = true;
											setStrTxtMsgUsuario("");
											setStrTxtMsgPerfil("");
										}
									}else{
										// No existe configuracio para el usuario o perfil logueado
										formAutorizacionPrestamoRendered = false;
										setStrTxtMsgUsuario("El Usuario/Perfil: "+ usuario.getStrUsuario() + "/"+usuario.getPerfil().getStrDescripcion()+" no concuerda con configuración existente.");							
									}
								}
							}
						}else{

							// Validar si el que esta logueado es un Adm Zonal.
							if(usuario.getPerfil().getId().getIntIdPerfil().compareTo(Constante.PARAM_PERFIL_ADM_ZONAL_CENTRAL)==0
								|| usuario.getPerfil().getId().getIntIdPerfil().compareTo(Constante.PARAM_PERFIL_ADM_ZONAL_FILIAL)==0
								|| usuario.getPerfil().getId().getIntIdPerfil().compareTo(Constante.PARAM_PERFIL_ADM_ZONAL_LIMA)==0){
								
								ConfServSolicitud reqAutUtilizado = null;
								// validamos que el logueado este en alguna configuracion de req aut.
								Boolean blnExiste = Boolean.FALSE;
								for (ConfServSolicitud reqAut : listaAutorizacionConfigurada) {
									// perfil
									if(reqAut.getListaPerfil() != null && !reqAut.getListaPerfil().isEmpty()){
										for (ConfServPerfil perfilReqAut : reqAut.getListaPerfil()) {
											if(perfilReqAut.getIntIdPerfilPk().compareTo(usuario.getPerfil().getId().getIntIdPerfil())==0){
												blnExiste = Boolean.TRUE;
												reqAutUtilizado = new ConfServSolicitud();
												reqAutUtilizado = reqAut;
												break;
											}
										}
									}
									
									// usuario
									if(reqAut.getListaUsuario() != null && !reqAut.getListaUsuario().isEmpty()){
										for (ConfServUsuario usuarioReqAut : reqAut.getListaUsuario()) {
											if(usuarioReqAut.getIntPersUsuarioPk().compareTo(usuario.getIntPersPersonaPk())==0){
												blnExiste = Boolean.TRUE;
												reqAutUtilizado = new ConfServSolicitud();
												reqAutUtilizado = reqAut;
												break;
											}
										}
									}	
								}
								
								if(blnExiste){
									// se le permite autoruizar
									reaAutFinal = new ConfServSolicitud();
									reaAutFinal = reqAutUtilizado;

									if(reaAutFinal != null){
										listaAutorizacionConfigurada.clear();
										autorizacionConfigurada = reaAutFinal;
										formAutorizacionPrestamoRendered = true;
										setStrTxtMsgUsuario("");
										setStrTxtMsgPerfil("");
									}
								}else{
									// se restringe
									formAutorizacionPrestamoRendered = false;	
									strTxtMsgError = "Aún no autoriza el jefe zonal." + reqAutUtilizado;
								}
							}else{
								// Estan vacias las autorizaciondel expediente.
								// Aun no autoriza el jefe zonal...
								formAutorizacionPrestamoRendered = false;
								System.out.println("Aun no autoriza el jefe zonal");
								strTxtMsgError = "Aun no autoriza el jefe zonal..";
							}
						}
					}else{	
						strTxtMsgError = "No se recupero configuraciones validadas.";
					}
				}else{
					// No se recupero configuraciones iniciales...
					formAutorizacionPrestamoRendered = false;
					strTxtMsgError = "No se recupero configuraciones iniciales.";
				}
			} catch (Exception e) {
				log.error("Error en showAutorizacionPrestamo ---> "+e);
			}

		}
		
	
	/**
	 * Metodo asociado al Boton VER de popup de acciones de autorizacion de credito.
	 * @param event
	 */
	public void showAutorizacionPrestamoView(ActionEvent event) {
		
		try {
			limpiarFiltros(event);
			limpiarResultadoBusqueda();
			
			blnBloquearCampo = true;
			strSolicitudPrestamo = Constante.MANTENIMIENTO_NINGUNO;
			formAutorizacionViewPrestamoRendered = true;
			formAutorizacionPrestamoRendered = true;
			if(listaAutorizaCreditoComp != null && !listaAutorizaCreditoComp.isEmpty()){
				listaAutorizaCreditoComp.clear();
			}
			listarEncargadosAutorizar();

		} catch (Exception e) {
			log.error("Error en showAutorizacionPrestamoView ---> "+e);
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Carga ==> listaAutorizaCreditoComp, con las personas QUE YA han autorizado el expediente de credito.
	 * @return
	 */
	public List<AutorizaCreditoComp> listarEncargadosAutorizar() {
		List<AutorizaCredito> listaAutorizaCredito = null;
		listaAutorizaCreditoComp = null;
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
			listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
			listaAutorizaCredito = new ArrayList<AutorizaCredito>();
			
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
							//autorizaCreditoComp.getAutorizaCredito().getIntIdPerfilAutoriza();
						}
						listaAutorizaCreditoComp.add(autorizaCreditoComp);
					}
				}
			}
		} catch (BusinessException e) {
			log.error("Error en listarEncargadosAutorizar ---> "+e);
			e.printStackTrace();
		}
		return listaAutorizaCreditoComp;
	}

	
	/**
	 * Graba la autorizacion de credito, aedemas dispara procesos de la autorizacion (cobranza, contabilidad, movimiento, servicio, etc)
	 * @param event
	 * @throws Exception
	 */
	public void grabarAutorizacionPrestamo(ActionEvent event) throws Exception {
		ExpedienteCredito expedienteCredito = null;
		List<AutorizaCredito> listaAutorizaCredito = null;
		List<AutorizaVerificacion> listaAutorizaVerificacion = null;
		Integer intNroPerfiles = 0;
		Integer intNroUsuarios = 0;
		boolean blnValidarUsuario = false;
		boolean blnValidarPerfil = false;
		Integer intTipoValidacion = new Integer(0); // o-usuario 1-perfil
		boolean blnNoExiste = true;
		boolean blnSeRegistaronTodos = false;
		//List<AutorizaCreditoComp> autorizaCreditoCompRecuperados = null;
		try {
			cargarUsuario();
			
			libroDiarioFacade = (LibroDiarioFacadeRemote) EJBFactory.getRemote(LibroDiarioFacadeRemote.class);
			listaAutorizaCredito = new ArrayList<AutorizaCredito>();
			listaAutorizaVerificacion = new ArrayList<AutorizaVerificacion>();
			
			refrescarEncargadosAutorizar();
			if (isValidoAutorizacion(beanAutorizaCredito) == false) {
				strTxtMsgValidacion = strTxtMsgValidacion+ "No se puede continuar con el proceso de grabación. ";
				return;
			}
			if(autorizacionConfigurada.getListaUsuario()!= null && !autorizacionConfigurada.getListaUsuario().isEmpty()){
				intNroUsuarios = autorizacionConfigurada.getListaUsuario().size(); // numero de usuarios q deben autorizar	
			}
			if(autorizacionConfigurada.getListaPerfil()!= null && !autorizacionConfigurada.getListaPerfil().isEmpty()){
				intNroPerfiles = autorizacionConfigurada.getListaPerfil().size(); // numero de perfiless que deben autorizar	
			}
			if (intNroUsuarios.intValue() > 0) {
				blnValidarUsuario = true;
			}
			if (intNroPerfiles.intValue() > 0) {
				blnValidarPerfil = true;
				intTipoValidacion = new Integer(1);
			}

			beanAutorizaCredito.setIntPersEmpresaAutoriza(usuario.getEmpresa().getIntIdEmpresa());
			beanAutorizaCredito.setIntPersUsuarioAutoriza(usuario.getIntPersPersonaPk());
			beanAutorizaCredito.setIntIdPerfilAutoriza(usuario.getPerfil().getId().getIntIdPerfil());
			beanAutorizaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			beanAutorizaCredito.setTsFechaAutorizacion((new Timestamp(new Date().getTime())));
			beanAutorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

			if (archivoInfoCorp != null) {
				beanAutorizaVerificacion.setIntItemArchivoInfo(archivoInfoCorp.getId().getIntItemArchivo());
				beanAutorizaVerificacion.setIntItemHistoricoInfo(archivoInfoCorp.getId().getIntItemHistorico());
				beanAutorizaVerificacion.setIntParaTipoArchivoInfoCod(archivoInfoCorp.getId().getIntParaTipoCod());
			}
			if (archivoReniec != null) {
				beanAutorizaVerificacion.setIntItemArchivoRen(archivoReniec.getId().getIntItemArchivo());
				beanAutorizaVerificacion.setIntItemHistoricoRen(archivoReniec.getId().getIntItemHistorico());
				beanAutorizaVerificacion.setIntParaTipoArchivoRenCod(archivoReniec.getId().getIntParaTipoCod());
			}

			expedienteCredito = expedienteCreditoCompSelected.getExpedienteCredito();

			listaAutorizaCredito.add(beanAutorizaCredito);
			listaAutorizaVerificacion.add(beanAutorizaVerificacion);
			expedienteCredito.setListaAutorizaCredito(listaAutorizaCredito);
			expedienteCredito.setListaAutorizaVerificacion(listaAutorizaVerificacion);

			if (beanAutorizaCredito.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO)==0) {
				if (expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {
					// Validamos Usuario
					if (blnValidarUsuario) {
						if (blnNoExiste) {

							Boolean blnExisteMovimiento = Boolean.TRUE;
							
							Cuenta cuenta = new Cuenta();
							cuenta.setId(new CuentaId());
							cuenta.getId().setIntCuenta(expedienteCreditoCompSelected.getExpedienteCredito().getId().getIntCuentaPk());
							cuenta.getId().setIntPersEmpresaPk(expedienteCreditoCompSelected.getExpedienteCredito().getId().getIntPersEmpresaPk());

							//CGD-25.11.2013
							blnSeRegistaronTodos = faltaSoloUno(intTipoValidacion,autorizacionConfigurada);
							//
							
							// validar si es represtamo
							//CGD-25.11.2013
							if(expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod().compareTo(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)!=0){
								blnExisteMovimiento = validarExistenciaPrestamoEnMovimiento(cuenta);	
							}
							
							if(blnExisteMovimiento){
								solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
								strMsgErrorValidarMovimientos = "";
								if(blnSeRegistaronTodos){
									LibroDiario diarioProvision = null;
									
									diarioProvision = irGenerarLibro(expedienteCredito,Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO);
									if(diarioProvision != null){
										diarioProvision= libroDiarioFacade.grabarLibroDiario(diarioProvision);
										solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
										cambioEstadoCredito(expedienteCredito, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO, diarioProvision);
									}
								}else{
									solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
								}
							}else{
								strMsgErrorValidarMovimientos = "El Socio posee Expediente de Credito en estado Vigente. No procede Autorización.";
								return;
							}
						} else {
							strTxtMsgPerfil = strTxtMsgUsuario + "Ya existe una Autorización registrada con el usuario: "
									+ usuario.getStrUsuario();
						}
					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							Boolean blnExisteMovimiento = Boolean.TRUE;
							
							Cuenta cuenta = new Cuenta();
							cuenta.setId(new CuentaId());
							cuenta.getId().setIntCuenta(expedienteCreditoCompSelected.getExpedienteCredito().getId().getIntCuentaPk());
							cuenta.getId().setIntPersEmpresaPk(expedienteCreditoCompSelected.getExpedienteCredito().getId().getIntPersEmpresaPk());
							
							//CGD-25.11.2013
							blnSeRegistaronTodos = faltaSoloUno(intTipoValidacion,autorizacionConfigurada);
							
							//CGD-25.11.2013
							if(expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod().compareTo(Constante.PARAM_T_SUBOPERACIONPRESTAMO_REPRESTAMO)!=0){
								blnExisteMovimiento = validarExistenciaPrestamoEnMovimiento(cuenta);	
							}
							if(blnExisteMovimiento){
								strMsgErrorValidarMovimientos = "";
								if(blnSeRegistaronTodos){
									LibroDiario diarioProvision = null;
									
									diarioProvision = irGenerarLibro(expedienteCredito,Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO);
									if(diarioProvision != null){
										solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
										cambioEstadoCredito(expedienteCredito, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO, diarioProvision);
									}
								}else{
									solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
								}
							}else{
								strMsgErrorValidarMovimientos = "El Socio posee Expediente de Credito en estado Vigente. No procede Autorización.";
								return;
							}
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización con el Perfil "
									+ usuario.getPerfil().getStrDescripcion() + " ("+ usuario.getPerfil().getId().getIntIdPerfil()+ "). ";
						}
					}
				} else {
					strTxtMsgError = "La Solicitud solo puede Autorizarse si se encuentra en estado SOLICITUD.";
					return;
				}

				// Validamos la Operacion - OBSERVAR
				// ==================================================================================================================================
			} else if (beanAutorizaCredito.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_OBSERVAR_PRESTAMO)==0) {

				if (expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {
					//comentario ÑÑññóóóá
					// Validamos Usuario
					if (blnValidarUsuario) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
							solicitudPrestamoFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedienteCredito, 
									Constante.PARAM_T_TIPOOPERACION_PRESTAMO , 
									Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO);


							cambioEstadoCredito(expedienteCredito,Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO, null);
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización registrada con el usuario: "
									+ usuario.getStrUsuario();
						}
					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
							solicitudPrestamoFacade.eliminarVerificaAutorizacionAdjuntosPorObservacion(expedienteCredito, 
																									Constante.PARAM_T_TIPOOPERACION_PRESTAMO , 
																									Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO);

							cambioEstadoCredito(expedienteCredito,Constante.PARAM_T_ESTADOSOLICPRESTAMO_OBSERVADO, null);

						} else {
							strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización con el Perfil "
							+ usuario.getPerfil().getStrDescripcion() + " ("+ usuario.getPerfil().getId().getIntIdPerfil()+ "). ";
						}
					}
				} else {
					strTxtMsgError = "La Solicitud solo puede Observarse si se encuentra en estado SOLICITUD.";
					return;
				}

				// Validamos la Operacion - RECHAZAR
				// ==================================================================================================================================
			} else if (beanAutorizaCredito.getIntParaEstadoAutorizar().compareTo(Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_RECHAZAR_PRESTAMO)==0) {
				if (expedienteCreditoCompSelected.getExpedienteCredito().getIntEstadoCreditoUltimo().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_SOLICITUD)==0) {
					// Validamos Usuario
					if (blnValidarUsuario) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
							cambioEstadoCredito(expedienteCredito,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO, null);
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil
									+ "Ya existe una Autorización registrada con el usuario: "+ usuario.getStrUsuario()+".";
						}
					}
					// Validamos Perfil
					else if (blnValidarPerfil) {
						blnNoExiste = existeRegistro(intTipoValidacion);
						if (blnNoExiste) {
							solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
							cambioEstadoCredito(expedienteCredito,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO, null);
						} else {
							strTxtMsgPerfil = strTxtMsgPerfil + "Ya existe una Autorización con el Perfil "
							+ usuario.getPerfil().getStrDescripcion() + " ("+ usuario.getPerfil().getId().getIntIdPerfil()+ "). ";
						}
					}
				} else {
					strTxtMsgError = "La Solicitud solo puede Aprobarse si se encuentra en estado SOLICITUD. Se anula el grabado. ";
				}
			}

			limpiarFormAutorizacionPrestamo();
			SolicitudPrestamoController solicitudPrestamoController = (SolicitudPrestamoController) getSessionBean("solicitudPrestamoController");

			solicitudPrestamoController.cancelarGrabarSolicitudPrestamo(event);	
			cancelarGrabarAutorizacionPrestamo(event);
			limpiarFiltros(event);

		} catch (BusinessException e) {
			log.info("Error en grabarAutorizacionPrestamo --> " + e);
		}
	}

	
	/**
	 * Valida que exista movimiento segun cuenta y empresa.
	 * @param cuenta
	 * @return
	 */
	private boolean validarExistenciaPrestamoEnMovimiento(Cuenta cuenta){
		boolean blnExiste = false;
		List<ExpedienteCredito> lstExpedientes = null;
		List<Expediente> listaExpedientesMovimeinto = null;
		Boolean blnPasaValidacion = Boolean.TRUE;
		strMsgErrorValidarMovimientos = "";
		
		try {
			listaExpedientesMovimeinto= conceptoFacade.getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(cuenta.getId().getIntPersEmpresaPk(), cuenta.getId().getIntCuenta());
			
			if(listaExpedientesMovimeinto != null && !listaExpedientesMovimeinto.isEmpty()){
				for (Expediente expediente : listaExpedientesMovimeinto) {
					if(expediente.getBdSaldoCredito().compareTo(BigDecimal.ZERO)>0){
						for (EstadoExpediente estadoExp : expediente.getListaEstadosExpediente()) {
							if(estadoExp.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){
								blnPasaValidacion = Boolean.FALSE;
								break;
							}
						}
					}
				}
			}
		} catch (BusinessException e) {
			log.error("Error en validarExistenciaPrestamoEnMovimiento ---> "+e);
			e.printStackTrace();
		}
		return blnPasaValidacion;
	}
	
	
	/**
	 * Valida que si con el registro de la presente autorizacion se inica los registros de liquidacion y cambio de estado.
	 * Retorna TRUE si falta solo uno y x lo tanto es el ultimo e inicia procesos de autorizacion.
	 * Si es FALSE solo graba la autorizacion.
	 * @param intTipoValidacion
	 * @param listaAutorizacionConfigurada
	 * @return
	 */
	private boolean faltaSoloUno(Integer intTipoValidacion, ConfServSolicitud autorizacionConfigurada) {
		BigDecimal bdNroUsuariosConf = null;
		BigDecimal bdNroPerfilesConf = null;
		boolean blnEsElUltimo = false;
		Integer intRecuperados = 0;
		BigDecimal bdDiferencia = BigDecimal.ZERO;
		try {
			bdNroUsuariosConf = BigDecimal.ZERO;
			bdNroPerfilesConf = BigDecimal.ZERO;
			
			if(autorizacionConfigurada.getListaPerfil() != null && !autorizacionConfigurada.getListaPerfil().isEmpty()){
				bdNroPerfilesConf = bdNroPerfilesConf.add(new BigDecimal(autorizacionConfigurada.getListaPerfil().size()));	
			}
			if(autorizacionConfigurada.getListaUsuario() != null && !autorizacionConfigurada.getListaUsuario().isEmpty()){
				bdNroUsuariosConf = bdNroUsuariosConf.add(new BigDecimal(autorizacionConfigurada.getListaUsuario().size()));	
			}	
			refrescarEncargadosAutorizar();
			intRecuperados = listaAutorizaCreditoComp.size(); 
			
			
			if (intTipoValidacion.compareTo(1)==0) { // perfil
				bdDiferencia = bdNroPerfilesConf.subtract(new BigDecimal(intRecuperados));
				Integer intDif = bdDiferencia.intValue();
				if (intDif == 1) {
					blnEsElUltimo = true;
				}
	
			} else if (intTipoValidacion.compareTo(0)== 0) { // usuario
				bdDiferencia = bdNroUsuariosConf.subtract(new BigDecimal(intRecuperados));
				Integer intDif = bdDiferencia.intValue();
				if (intDif == 1) {
					blnEsElUltimo = true;
				}
			}
		} catch (Exception e) {
			log.error("Error en faltaSoloUno ---> "+e);
		}
		return blnEsElUltimo;
	}
	
	/**
	 * 
	 * @param cuenta
	 * @return
	 */
	private boolean strMsgErrorValidarMovimientos(Cuenta cuenta){
		boolean blnExiste = false;
		List<ExpedienteCredito> lstExpedientes = null;
		List<Expediente> listaExpedientesMovimeinto = null;
		Boolean blnPasaValidacion = Boolean.TRUE;
		strMsgErrorValidarMovimientos = "";
		
		try {
			listaExpedientesMovimeinto= conceptoFacade.getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(cuenta.getId().getIntPersEmpresaPk(), cuenta.getId().getIntCuenta());
			
			if(listaExpedientesMovimeinto != null && !listaExpedientesMovimeinto.isEmpty()){
				for (Expediente expediente : listaExpedientesMovimeinto) {
					if(expediente.getBdSaldoCredito().compareTo(BigDecimal.ZERO)>0){
						for (EstadoExpediente estadoExp : expediente.getListaEstadosExpediente()) {
							if(estadoExp.getIntParaEstadoExpediente().compareTo(Constante.PARAM_T_ESTADOS_EXPEDIENTE_MOVIMIENTO_VIGENTE)==0){
								blnPasaValidacion = Boolean.FALSE;
							}
						}
					}
				}
			}

		} catch (BusinessException e) {
			log.error("Error en strMsgErrorValidarMovimientos ---> "+e);
			e.printStackTrace();
		}
		return blnExiste;
	}
	
	
	
	/**
	 * Cambia de estado al expediente, agregando un registro de Estado.
	 * @param expedienteCredito
	 * @throws Exception
	 */
	private void cambioEstadoCredito(ExpedienteCredito expedienteCredito,
		Integer intParaEstadoCreditoCod, LibroDiario diarioProvision) throws Exception {
		EstadoCredito estadoCredito = null;
		EstadoCreditoId estadoCreditoId = null;

		try {
			estadoCredito = new EstadoCredito();
			cargarUsuario();
			
			estadoCredito.setId(estadoCreditoId);
			estadoCredito.setTsFechaEstado(new Timestamp(new Date().getTime()));
			estadoCredito.setIntPersEmpresaEstadoCod(usuario.getEmpresa().getIntIdEmpresa());
			estadoCredito.setIntIdUsuSucursalPk(usuario.getSucursal().getId().getIntIdSucursal());
			estadoCredito.setIntIdUsuSubSucursalPk(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			estadoCredito.setIntPersUsuarioEstadoPk(usuario.getIntPersPersonaPk());
			estadoCredito.setIntParaEstadoCreditoCod(intParaEstadoCreditoCod);

			// estadoCredito.setIntCodigoLibro(14);
			if(diarioProvision != null && diarioProvision.getId() != null){
				estadoCredito.setIntCodigoLibro(diarioProvision.getId().getIntContCodigoLibro());
				estadoCredito.setIntPeriodoLibro(diarioProvision.getId().getIntContPeriodoLibro());
				estadoCredito.setIntPersEmpresaLibro(diarioProvision.getId().getIntPersEmpresaLibro());
			}
			if(expedienteCredito.getListaEstadoCredito() != null && !expedienteCredito.getListaEstadoCredito().isEmpty()){
				expedienteCredito.getListaEstadoCredito().add(estadoCredito);
			}else{
				expedienteCredito.setListaEstadoCredito(new ArrayList<EstadoCredito>());
				expedienteCredito.getListaEstadoCredito().add(estadoCredito);
			}
			solicitudPrestamoFacade.modificarExpedienteCredito(expedienteCredito);
		} catch (Exception e) {
			log.error("Error en cambioEstadoCredito ---> "+e);
		}
	}

	
	
	/**
	 * 
	 * @param expedienteCredito
	 * @param operacion
	 */
	private LibroDiario irGenerarLibro(ExpedienteCredito expedienteCredito,Integer operacion) {
		LibroDiario libroDiarioProvision = null;
		try {
			if (operacion.intValue() == Constante.PARAM_T_TIPOOPERACION_AUTORIZACION_AUTORIZAR_PRESTAMO) {
				libroDiarioProvision = solicitudPrestamoFacade.generarLibroDiarioPrestamo(expedienteCredito);
				if(libroDiarioProvision != null){
					libroDiarioProvision = libroDiarioFacade.grabarLibroDiario(libroDiarioProvision);
				}else{
					libroDiarioProvision= null;
				}
			}
		} catch (BusinessException e) {
			log.info("Error al irGenerarLibro --> " + e);
		}
		return libroDiarioProvision;
	}

	
	/**
	 * 
	 * @param event
	 */
	/*public void grabarAutorizacionPrestamoold(ActionEvent event) {
		ExpedienteCredito expedienteCredito = null;
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		List<AutorizaVerificacion> listaAutorizaVerificacion = new ArrayList<AutorizaVerificacion>();
		

		try {
			cargarUsuario();
			
			beanAutorizaCredito.setIntPersEmpresaAutoriza(usuario.getEmpresa().getIntIdEmpresa());
			beanAutorizaCredito.setIntPersUsuarioAutoriza(usuario.getIntPersPersonaPk());
			beanAutorizaCredito.setIntIdPerfilAutoriza(usuario.getPerfil().getId().getIntIdPerfil());
			beanAutorizaCredito.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			beanAutorizaVerificacion.setIntParaEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);

			expedienteCredito = expedienteCreditoCompSelected.getExpedienteCredito();
			listaAutorizaCredito.add(beanAutorizaCredito);
			listaAutorizaVerificacion.add(beanAutorizaVerificacion);
			expedienteCredito.setListaAutorizaCredito(listaAutorizaCredito);
			expedienteCredito.setListaAutorizaVerificacion(listaAutorizaVerificacion);

			solicitudPrestamoFacade.grabarAutorizacionPrestamo(expedienteCredito);
			limpiarFormAutorizacionPrestamo();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * 
	 * @param event
	 */
	public void adjuntarDocumento(ActionEvent event) {
		String strParaTipoDescripcion = "";
		String strParaTipoOperacionPersona = "";
		Integer intParaTipoDescripcion = 0;
		Integer intParaTipoOperacionPersona = 0;
		try {
			strParaTipoDescripcion = getRequestParameter("intParaTipoDescripcion");
			strParaTipoOperacionPersona = getRequestParameter("intParaTipoOperacionPersona");
			intParaTipoDescripcion = new Integer(strParaTipoDescripcion);
			intParaTipoOperacionPersona = new Integer( strParaTipoOperacionPersona);

			this.intParaTipoDescripcion = intParaTipoDescripcion;
			this.intParaTipoOperacionPersona = intParaTipoOperacionPersona;

			FileUploadController fileupload = (FileUploadController) getSessionBean("fileUploadController");
			// FileUploadController fileupload = new FileUploadController();
			fileupload.setStrDescripcion("Seleccione el archivo que desea adjuntar.");
			fileupload.setFileType(FileUtil.imageTypes);
			Integer intItemArchivo = null;
			Integer intItemHistorico = null;

			if (listaRequisitoCreditoComp != null) {
				for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {

					if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_INFOCORP)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_INFOCORP);
					}

					if (intParaTipoDescripcion.equals(requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion())
						&& requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_RENIEC)
						&& intParaTipoOperacionPersona.equals(requisitoCreditoComp.getDetalle().getIntParaTipoPersonaOperacionCod())
					) {
						if (requisitoCreditoComp.getRequisitoCredito() != null) {
							intItemArchivo = requisitoCreditoComp.getRequisitoCredito().getIntParaItemArchivo();
							intItemHistorico = requisitoCreditoComp.getRequisitoCredito().getIntParaItemHistorico();
						}
						fileupload.setParamArchivo(intItemArchivo,intItemHistorico,Constante.PARAM_T_TIPOARCHIVOADJUNTO_RENIEC);
					}
				}
			}
			fileupload.setStrJsFunction("putFileDocAdjunto()");
		} catch (Exception e) {
			log.error("Error en adjuntarDocumento ---> "+e);
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
		try {
			FileUploadController fileupload = (FileUploadController) getSessionBean("fileUploadController");
			if (listaRequisitoCreditoComp != null) {
				for (RequisitoCreditoComp requisitoCreditoComp : listaRequisitoCreditoComp) {
					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_INFOCORP)
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

					if (requisitoCreditoComp.getDetalle().getIntParaTipoDescripcion().equals(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO_FICHA_RENIEC)
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
			}
		} catch (Exception e) {
			log.error("Error en putFile ---> "+e);
		}
	}

	
	/**
	 * @param stream
	 * @param object
	 * @throws IOException
	 */
	public void paintImage(OutputStream stream, Object object)throws IOException {
		stream.write(((MyFile) object).getData());
	}

	
	/**
	 * Valida las configuraciones de solicitud (ReqAut's). Valida: Vigencia, tipo/subtipo,
	 * Monto Desde/Hasta, Cuota Desde/Hasta. Devuelve las que pasan estas
	 * validaciones.
	 * @param listaSolicitudAutorizada
	 * @return
	 */
	public ConfServSolicitud validarConfiguracion(ConfServSolicitud confServSolicitud, Credito creditoConf) {
		Boolean hasMontDesde = false;
		Boolean hasMontHasta = false;
		Boolean hasCuotaDesde = false;
		Boolean hasCuotaHasta = false;
		Integer nroValidaciones = null;
		Integer contAprob = new Integer(0);
		Boolean boAprueba = new Boolean(false);
		ConfServSolicitud solicitud = null;
		ConfServSolicitud solicitudFinal = null;
		Calendar clToday = Calendar.getInstance();
		List<ConfServCreditoEmpresa> listaConfServCreditoEmpresa = null;
		String strObservaciones ="";
		Integer intSubtipoOperacionAux = null;
		try {
			strObservaciones = "No paso : ";

			nroValidaciones = new Integer(4); // Se inicializa en 3 xq se toma en  cuenta (1)Vigencia, (2) Estado,  (3)TipoSubtipo y (8) Al menos uno exista.
											  // se agrego la lista de creditos empresa. 27.05.2013
			
			solicitud = confServSolicitud;
			if (solicitud.getBdMontoDesde() != null) {
				hasMontDesde = true;
				nroValidaciones++;
			}
			if (solicitud.getBdMontoHasta() != null) {
				hasMontHasta = true;
				nroValidaciones++;
			}
			if (solicitud.getBdCuotaDesde() != null) {
				hasCuotaDesde = true;
				nroValidaciones++;
			}
			if (solicitud.getBdCuotaHasta() != null) {
				hasCuotaHasta = true;
				nroValidaciones++;
			}

			// 1. Validando la Vigencia
			if ((clToday.getTime().compareTo(solicitud.getDtDesde()) > 0 && solicitud.getDtHasta() == null)
					|| (clToday.getTime().compareTo(solicitud.getDtDesde()) > 0 && clToday.getTime().compareTo(solicitud.getDtHasta()) < 0)) {
				contAprob++;
			}else{
				strObservaciones = strObservaciones +" 1. Validando la Vigencia";
			}
			// 2. Validando el estado
			if (solicitud.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO) == 0) {
				contAprob++;
			}else{
				strObservaciones = strObservaciones +" 2. Validando el estado";
			}
			// 3. Validando el tipo y subtipo
			//Se agraga logica, el represtamo no tiene configuracion, coje la del prestamo que se esta cancelando.
			intSubtipoOperacionAux = expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod().equals(Constante.PARAM_T_SUBTIPO_OPERACION_REPRESTAMO)
						?Constante.PARAM_T_SUBTIPO_OPERACION_NUEVO_PRESTAMO
						:expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod()
			;
			
			//if ( expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod().equals(solicitud.getIntParaSubtipoOperacionCod())
			if ( intSubtipoOperacionAux.equals(solicitud.getIntParaSubtipoOperacionCod())
				&& (solicitud.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO))) {
				contAprob++;
			}else{
				strObservaciones = strObservaciones +" 3. Validando el tipo y subtipo";
			}
			// 4. Validando Monto Desde
			if (hasMontDesde){
				
				if(expedienteCreditoCompSelected.getExpedienteCredito().getBdMontoTotal().compareTo(solicitud.getBdMontoDesde()) >= 0){
					contAprob++;
				}else{
					strObservaciones = strObservaciones +" 4. Validando Monto Desde";
				}
			}
			// 5. Validando Monto Hasta
			if (hasMontHasta){
				if(expedienteCreditoCompSelected.getExpedienteCredito().getBdMontoTotal().compareTo(solicitud.getBdMontoHasta()) <= 0){
					contAprob++;
				}else{
					strObservaciones = strObservaciones +" 5. Validando Monto Hasta";
				}	
			}
			// 6. Validando Nro Cuotas Desde
			if(hasCuotaDesde){
				if (expedienteCreditoCompSelected.getExpedienteCredito().getIntNumeroCuota().compareTo(solicitud.getBdCuotaDesde().intValue()) >= 0){
					contAprob++;
				}else{
					strObservaciones = strObservaciones +" 6. Validando Nro Cuotas Desde";
				}
			}
			// 7. Validando Nro Cuotas Hasta
			if (hasCuotaHasta){
				if (expedienteCreditoCompSelected.getExpedienteCredito().getIntNumeroCuota().compareTo(solicitud.getBdCuotaHasta().intValue()) <= 0){
					contAprob++;
				}else{
					strObservaciones = strObservaciones +" 7. Validando Nro Cuotas Hasta";
				}
			}
			// 9. validando la configuracion de credito: 
			listaConfServCreditoEmpresa = solicitud.getListaCreditoEmpresa();
			if(listaConfServCreditoEmpresa != null && !listaConfServCreditoEmpresa.isEmpty()){
				if(creditoConf != null){
					for (ConfServCreditoEmpresa confCreditoEmpresa : listaConfServCreditoEmpresa) {
						if(confCreditoEmpresa.getId().getIntParaTipoCreditoEmpresaCod().compareTo(creditoConf.getIntParaTipoCreditoEmpresa())==0
						   && confCreditoEmpresa.getIntValor().compareTo(new Integer(1))==0){
							contAprob++;
							break;
						}	
					}	
				}
			}
			log.error("solicitud.getId().getIntItemSolicitud() -----> "+solicitud.getId().getIntItemSolicitud());
			log.error("expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod() -----> "+expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod());
			log.error("solicitud.getIntParaSubtipoOperacionCod() -----> "+solicitud.getIntParaSubtipoOperacionCod());
			System.out.println("----------------------------------------------------------------------------------------");
			log.error("expedienteCreditoCompSelected.getExpedienteCredito().getBdMontoTotal() -----> "+expedienteCreditoCompSelected.getExpedienteCredito().getBdMontoTotal());
			if(hasMontDesde){
				log.error("solicitud.getBdMontoDesde() -----> "+solicitud.getBdMontoDesde());
			}else{
				log.error("No tiene solicitud.getBdMontoDesde(). ");
			}
			
			if(hasMontHasta){
				log.error("solicitud.getBdMontoDesde() -----> "+solicitud.getBdMontoHasta());		
			}else{
				log.error("No tiene solicitud.getBdMontoHasta(). ");
			}
			
			log.error("expedienteCreditoCompSelected.getExpedienteCredito().getIntNumeroCuota() -----> "+expedienteCreditoCompSelected.getExpedienteCredito().getIntNumeroCuota());
			if(hasCuotaDesde){
				log.error("solicitud.getBdCuotaDesde()----> "+solicitud.getBdCuotaDesde());
			}else{
				log.error("No tiene solicitud.getBdCuotaDesde(). "); 
			}
			if(hasCuotaHasta){
				log.error("solicitud.getBdCuotaHasta()----> "+solicitud.getBdCuotaHasta());
			}else{
				log.error("No tiene solicitud.getBdCuotaHasta(). "); 
			}
			
			log.error("solicitud.getId().getIntItemSolicitud() -----> "+solicitud.getId().getIntItemSolicitud());

			System.out.println(" NRO DE VALIDACIONES EXISTENTES " + nroValidaciones);
			System.out.println(" NRO DE VALIDACIONES APROBADAS " + contAprob);
			System.out.println("OBSERVACIONES ---> " +strObservaciones);
			log.error("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");

			if (contAprob.compareTo(nroValidaciones) >= 0)
				boAprueba = true;
			if (boAprueba) {
				solicitudFinal = new ConfServSolicitud();
				solicitudFinal = solicitud;
			} 
		} catch (Exception e) {
			log.error("Error en validarConfiguracion ---> "+e);
		}
		return solicitudFinal;
	}
	
	
	/**
	 * Validamos que el usuario logueado se encuentre en alguna de las listas de usuarios y/o perfiles de las
	 * configuraciones validadas.
	 * Usuario tiene prioridad sobre perfil.
	 * @param confServSolicitud
	 * @param listaReqAuts
	 * @return
	 */
	
	public ConfServSolicitud validarExtraConfiguracion(List<ConfServSolicitud> listaReqAuts) {
		ConfServSolicitud reqAutFinal = null;
		List<ConfServSolicitud> listaConfiguracionAutorizacion = null;
		//List<ConfServCreditoEmpresa> listaConfCreditoEmpresa = null;
		//List<ConfServPerfil> listaConfPerfil = null;
		//List<ConfServUsuario> listaConfUsuario = null;
		//Boolean blnPasoValidaciones = Boolean.FALSE;
		
		try {
			
			cargarUsuario();
			if(listaReqAuts != null && !listaReqAuts.isEmpty()){
				System.out.println("PERFIL DE USUARIO LOGUEADO ---> "+usuario.getPerfil().getId().getIntIdPerfil());
				System.out.println("PERSONA DE USUARIO LOGUEADO ---> "+usuario.getIntPersPersonaPk());
				
				listaConfiguracionAutorizacion = new ArrayList<ConfServSolicitud>();
				
				for (ConfServSolicitud reqAut : listaReqAuts) {
					reqAut.setBlnPerfil(Boolean.FALSE);
					reqAut.setBlnUsuario(Boolean.FALSE);
					reqAut.setBlnConfigurado(Boolean.FALSE);
					
					if(reqAut.getListaUsuario() != null && !reqAut.getListaUsuario().isEmpty()){
						for (ConfServUsuario confServUsuario : reqAut.getListaUsuario()) {
							if(confServUsuario.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								if(confServUsuario.getIntPersUsuarioPk().compareTo(usuario.getIntPersPersonaPk())==0){
									reqAut.setBlnUsuario(Boolean.TRUE);
									break;
								}
							}	
						}
					}
					
					if(reqAut.getListaPerfil() != null && !reqAut.getListaPerfil().isEmpty()){
						for (ConfServPerfil confServPerfil : reqAut.getListaPerfil()) {
							if(confServPerfil.getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
								if(confServPerfil.getIntIdPerfilPk().compareTo(usuario.getPerfil().getId().getIntIdPerfil())==0){
									reqAut.setBlnPerfil(Boolean.TRUE);
									break;
								}
							}	
						}	
					}
					
					// Al menos esta en usuario o perfil:
					if(reqAut.getBlnPerfil() || reqAut.getBlnUsuario()){
						reqAut.setBlnConfigurado(Boolean.TRUE);
						listaConfiguracionAutorizacion.add(reqAut);
					}
				}

				// 2. Se validan las listas de usuarios y perfiles, 
				//    dandole prioridad al reqAut donde existe el usuario.
				
				if(listaConfiguracionAutorizacion != null && !listaConfiguracionAutorizacion.isEmpty()){
					Integer intTam = 0;
					Boolean blnExisteAlgunUSuario= Boolean.FALSE;
					//Boolean blnExisteAlgunPerfil= Boolean.FALSE;
					
					intTam = listaConfiguracionAutorizacion.size();
					if(intTam.compareTo(1)==0){
						reqAutFinal = listaConfiguracionAutorizacion.get(0);
						
					}else{
						for (ConfServSolicitud confServSol : listaConfiguracionAutorizacion) {
								if(confServSol.getBlnUsuario().compareTo(Boolean.TRUE)==0){
									blnExisteAlgunUSuario = Boolean.TRUE;
									reqAutFinal = confServSol;
									break;
								}
						}

						if(!blnExisteAlgunUSuario){
							reqAutFinal =  listaConfiguracionAutorizacion.get(0);
						}	
					}
				}

				
			}else{
				reqAutFinal = null;
			}
			
		} catch (Exception e) {
			log.error(""+e);
		}
		return reqAutFinal;
		
	}
	
	
	/**
	 * 
	 * @param autorizaCredito
	 * @return
	 */
	private Boolean isValidoAutorizacion(AutorizaCredito autorizaCredito) {
		Boolean validAutorizaPrestamo = true;
		strTxtMsgValidacion = "";
		if (autorizaCredito.getIntParaEstadoAutorizar() == 0) {
			strTxtMsgValidacion = strTxtMsgValidacion
					+ "* Seleccionar Operación. ";
			validAutorizaPrestamo = false;
		}
		if (autorizaCredito.getIntParaTipoAureobCod() == 0) {
			strTxtMsgValidacion = strTxtMsgValidacion
					+ "* Seleccionar Tipo de Operación. ";
			validAutorizaPrestamo = false;
		}

		// -------< validacion agregada 08112012 ----->
		/*
		 * if (listaGarantiaCreditoComp == null) {
		 * setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago +
		 * "* Deben Garantes Solidarios."); validExpedienteCredito = false; }
		 * else { setMsgTxtListaCapacidadPago(msgTxtListaCapacidadPago); }
		 */

		return validAutorizaPrestamo;
	}

	/**
	 * 
	 * @param expedienteCreditoComp
	 * @return
	 */
	public List<AutorizaCreditoComp> recuperarEncargadosAutorizarXXXX(
			ExpedienteCreditoComp expedienteCreditoComp) {
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		List<AutorizaCreditoComp> listaAutorizaCreditoComp = null;
		AutorizaCreditoComp autorizaCreditoComp = null;
		Persona persona = null;
		try {
			listaAutorizaCredito = solicitudPrestamoFacade.getListaAutorizaCreditoPorPkExpediente(expedienteCreditoComp.getExpedienteCredito().getId());
			if (listaAutorizaCredito != null && listaAutorizaCredito.size() > 0) {
				listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
				for (AutorizaCredito autorizaCredito : listaAutorizaCredito) {
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
					listaAutorizaCreditoComp.add(autorizaCreditoComp);
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return listaAutorizaCreditoComp;
	}

	
	/**
	 * 
	 */
	public void refrescarEncargadosAutorizar() {
		List<AutorizaCredito> listaAutorizaCredito = new ArrayList<AutorizaCredito>();
		listaAutorizaCreditoComp = new ArrayList<AutorizaCreditoComp>();
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
						listaAutorizaCreditoComp.add(autorizaCreditoComp);
					}
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}

	}

	/*
	 * public void mostrarArchivosAdjuntos() { log.info(
	 * "-----------------------Debugging SolicitudPrestamoController.mostrarArchivosAdjuntos-----------------------------"
	 * ); ConfSolicitudFacadeRemote facade = null; EstructuraFacadeRemote
	 * estructuraFacade = null; ConfServSolicitud confServSolicitud = null;
	 * String strToday = Constante.sdf.format(new Date()); Date dtToday = null;
	 * List<ConfServSolicitud> listaDocAdjuntos = new
	 * ArrayList<ConfServSolicitud>(); EstructuraDetalle estructuraDet = null;
	 * List<EstructuraDetalle> listaEstructuraDet = new
	 * ArrayList<EstructuraDetalle>(); listaRequisitoCreditoComp = new
	 * ArrayList<RequisitoCreditoComp>(); RequisitoCreditoComp
	 * requisitoCreditoComp; SocioComp beanSocioComp = new SocioComp();
	 * SocioComp socioComp = null; try { dtToday =
	 * Constante.sdf.parse(strToday); } catch (ParseException e1) {
	 * e1.printStackTrace(); } try { beanSocioComp =
	 * expedienteCreditoCompSelected.getSocioComp(); socioComp =
	 * socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(
	 * beanSocioComp.getPersona().getDocumento().getIntTipoIdentidadCod(),
	 * beanSocioComp.getPersona().getDocumento().getStrNumeroIdentidad(),
	 * Constante.PARAM_EMPRESASESION);
	 * 
	 * if (socioComp != null) { if (socioComp.getCuenta() != null) { for
	 * (SocioEstructura socioEstructura :
	 * socioComp.getSocio().getListSocioEstructura()) { if
	 * (socioEstructura.getIntTipoEstructura().equals(
	 * Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
	 * socioComp.getSocio().setSocioEstructura(socioEstructura); } }
	 * beanSocioComp = socioComp; } }
	 * 
	 * 
	 * facade = (ConfSolicitudFacadeRemote)
	 * EJBFactory.getRemote(ConfSolicitudFacadeRemote.class); estructuraFacade =
	 * (EstructuraFacadeRemote)
	 * EJBFactory.getRemote(EstructuraFacadeRemote.class); confServSolicitud =
	 * new ConfServSolicitud(); listaDocAdjuntos =
	 * facade.buscarConfSolicitudRequisito(confServSolicitud, null, dtToday, 1);
	 * if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
	 * forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) { if
	 * (solicitud
	 * .getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO
	 * )) { if (solicitud.getIntParaSubtipoOperacionCod().equals(Constante.
	 * PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO)) {
	 * 
	 * if (solicitud.getListaEstructuraDetalle() != null) { for
	 * (ConfServEstructuraDetalle estructuraDetalle :
	 * solicitud.getListaEstructuraDetalle()) { estructuraDet = new
	 * EstructuraDetalle(); estructuraDet.setId(new EstructuraDetalleId());
	 * //expedienteCreditoCompSelected listaEstructuraDet = estructuraFacade.
	 * getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
	 * estructuraDet.getId(),
	 * beanSocioComp.getSocio().getSocioEstructura().getIntTipoSocio(),
	 * //beanSocioComp
	 * .getSocio().getListSocioEstructura().get(0).getIntTipoSocio(),
	 * beanSocioComp.getSocio().getSocioEstructura().getIntModalidad());
	 * //beanSocioComp
	 * .getSocio().getListSocioEstructura().get(0).getIntModalidad()); if
	 * (listaEstructuraDet != null && listaEstructuraDet.size() > 0) { for
	 * (EstructuraDetalle estructDetalle : listaEstructuraDet) { if
	 * (estructuraDetalle
	 * .getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura
	 * ().getIntCodigo()) &&
	 * estructuraDetalle.getIntNivelPk().equals(beanSocioComp
	 * .getSocio().getSocioEstructura().getIntNivel()) //if
	 * (estructuraDetalle.getIntCodigoPk
	 * ().equals(beanSocioComp.getSocio().getListSocioEstructura
	 * ().get(0).getIntCodigo()) // &&
	 * estructuraDetalle.getIntNivelPk().equals(beanSocioComp
	 * .getSocio().getListSocioEstructura().get(0).getIntNivel()) &&
	 * estructuraDetalle
	 * .getIntCaso().equals(estructDetalle.getId().getIntCaso()) &&
	 * estructuraDetalle
	 * .getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())) { if
	 * (solicitud.getListaDetalle() != null &&
	 * solicitud.getListaDetalle().size() > 0) { for (ConfServDetalle detalle :
	 * solicitud.getListaDetalle()) {
	 * 
	 * System.out.println("detalle.getId().getIntItemDetalle()"+
	 * detalle.getId().getIntItemDetalle());
	 * System.out.println("detalle.getId().getIntItemSolicitud()"+
	 * detalle.getId().getIntItemSolicitud());
	 * System.out.println("detalle.getId().getIntPersEmpresaPk()"+
	 * detalle.getId().getIntPersEmpresaPk());
	 * 
	 * if
	 * (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId()
	 * .getIntPersEmpresaPk()) &&
	 * detalle.getId().getIntItemSolicitud().equals(estructuraDetalle
	 * .getId().getIntItemSolicitud())) { requisitoCreditoComp = new
	 * RequisitoCreditoComp();
	 * 
	 * requisitoCreditoComp.setDetalle(detalle);
	 * listaRequisitoCreditoComp.add(requisitoCreditoComp);
	 * 
	 * } } break forSolicitud; } } } } } } } } } }
	 * 
	 * } catch (BusinessException e) { e.printStackTrace(); } catch
	 * (EJBFactoryException e) { e.printStackTrace(); } }
	 */

	
	/**
	 * 
	 */
	public void mostrarArchivosAdjuntos() {
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
		listaRequisitoCreditoComp = new ArrayList<RequisitoCreditoComp>();
		RequisitoCreditoComp requisitoCreditoComp;
		try {
			dtToday = Constante.sdf.parse(strToday);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		try {
			SocioComp beanSocioComp = new SocioComp();
			beanSocioComp = expedienteCreditoCompSelected.getSocioComp();

			facade = (ConfSolicitudFacadeRemote) EJBFactory.getRemote(ConfSolicitudFacadeRemote.class);
			estructuraFacade = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			/*confServSolicitud = new ConfServSolicitud();
			listaDocAdjuntos = facade.buscarConfSolicitudRequisito(confServSolicitud, null, dtToday, 1);
			
			*/
			confServSolicitud = new ConfServSolicitud();
			confServSolicitud.setIntParaTipoRequertoAutorizaCod(Constante.PARAM_T_TIPOREQAUT_REQUISITO);
			confServSolicitud.setIntParaTipoOperacionCod(Constante.PARAM_T_TIPOOPERACION_PRESTAMO);
			confServSolicitud.setIntParaSubtipoOperacionCod(expedienteCreditoCompSelected.getExpedienteCredito().getIntParaSubTipoOperacionCod());
			listaDocAdjuntos = facade.buscarConfSolicitudRequisitoOptimizado(confServSolicitud, Constante.PARAM_T_TIPOREQAUT_REQUISITO, null);
			
			if (listaDocAdjuntos != null && listaDocAdjuntos.size() > 0) {
				forSolicitud: for (ConfServSolicitud solicitud : listaDocAdjuntos) {
					if (solicitud.getIntParaTipoOperacionCod().equals(Constante.PARAM_T_TIPOOPERACION_PRESTAMO)) {
						//if (solicitud.getIntParaSubtipoOperacionCod().equals(Constante.PARAM_T_SUBOPERACIONPRESTAMO_NUEVO_PRESTAMO)) {
							if (solicitud.getListaEstructuraDetalle() != null) {
								for (ConfServEstructuraDetalle estructuraDetalle : solicitud.getListaEstructuraDetalle()) {
									estructuraDet = new EstructuraDetalle();
									estructuraDet.setId(new EstructuraDetalleId());
									// getSocioEstructuraDeOrigenPorPkSocio
									// **********************************************************************
									SocioEstructura socioEsctructura = new SocioEstructura();
									socioEsctructura = socioFacade.getSocioEstructuraDeOrigenPorPkSocio(beanSocioComp.getSocio().getId());

									// **********************************************************************
									/*
									 * estructuraDet.getId().setIntNivel(
									 * beanSocioComp
									 * .getSocio().getSocioEstructura
									 * ().getIntNivel());
									 * estructuraDet.getId().setIntCodigo
									 * (beanSocioComp
									 * .getSocio().getSocioEstructura
									 * ().getIntCodigo());
									 */
									estructuraDet.getId().setIntNivel(socioEsctructura.getIntNivel());
									estructuraDet.getId().setIntCodigo(socioEsctructura.getIntCodigo());
									listaEstructuraDet = estructuraFacade.getListaEstructuraDetallePorEstructuraYTipoSocioYTipoModalidad(
													estructuraDet.getId(),socioEsctructura.getIntTipoSocio(),socioEsctructura.getIntModalidad());

									if (listaEstructuraDet != null && listaEstructuraDet.size() > 0) {
										for (EstructuraDetalle estructDetalle : listaEstructuraDet) {
											// if
											// (estructuraDetalle.getIntCodigoPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntCodigo())
											// &&
											// estructuraDetalle.getIntNivelPk().equals(beanSocioComp.getSocio().getSocioEstructura().getIntNivel())
											if (estructuraDetalle.getIntCodigoPk().equals(socioEsctructura.getIntCodigo())
												&& estructuraDetalle.getIntNivelPk().equals(socioEsctructura.getIntNivel())
												&& estructuraDetalle.getIntCaso().equals(estructDetalle.getId().getIntCaso())
												&& estructuraDetalle.getIntItemCaso().equals(estructDetalle.getId().getIntItemCaso())) {
												
												if (solicitud.getListaDetalle() != null && solicitud.getListaDetalle().size() > 0) {

													List<RequisitoCreditoComp> listaRequisitoCreditoCompTemp = new ArrayList<RequisitoCreditoComp>();
													for (ConfServDetalle detalle : solicitud.getListaDetalle()) {
													
														if (detalle.getId().getIntPersEmpresaPk().equals(estructuraDetalle.getId().getIntPersEmpresaPk())
															&& detalle.getId().getIntItemSolicitud().equals(estructuraDetalle.getId().getIntItemSolicitud())) {

															requisitoCreditoComp = new RequisitoCreditoComp();
															requisitoCreditoComp.setDetalle(detalle);
															// listaRequisitoCreditoComp.add(requisitoCreditoComp);
															listaRequisitoCreditoCompTemp.add(requisitoCreditoComp);
														}
													}

													List<Tabla> listaTablaRequisitos = new ArrayList<Tabla>();

													// validamos que solo se muestre las de agrupamioento A.
													listaTablaRequisitos = tablaFacade.getListaTablaPorAgrupamientoA(
																	new Integer(Constante.PARAM_T_REQUISITOSDESCRIPCION_PRESTAMO),"B");
													
													for (int i = 0; i < listaTablaRequisitos.size(); i++) {
														for (int j = 0; j < listaRequisitoCreditoCompTemp.size(); j++) {
															if ((listaRequisitoCreditoCompTemp.get(j).getDetalle().getIntParaTipoDescripcion().intValue()) 
																== (listaTablaRequisitos.get(i).getIntIdDetalle().intValue())) {
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
						//}
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
	 * 
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
	 * 
	 * @return
	 */
	public Timestamp obtenerFechaActual() {
		return new Timestamp(new Date().getTime());
	}

	
	/**
	 * 
	 * @return
	 */
	public String getTipoValidacion() {
		Integer nrolista = null;
		Integer intNroPerfiles = null;
		Integer intNroUsuarios = null;
		String strTipoValidacion = null;

		nrolista = listaAutorizacionConfigurada.size();
		intNroUsuarios = listaAutorizacionConfigurada.get(0).getListaUsuario().size();
		intNroPerfiles = listaAutorizacionConfigurada.get(0).getListaPerfil().size();

		if (intNroUsuarios.compareTo(0) > 0) {
			strTipoValidacion = "U";
		}
		if (intNroPerfiles.compareTo(0) > 0) {
			strTipoValidacion = "P";
		}

		return strTipoValidacion;
	}

	
	/**
	 * Indica si el usuario ya realizo una autorizacion.
	 * 
	 * @return boolean blnNoExiste
	 */
	/*
	public boolean existeRegistro(Integer intTipoValidacion) {
		boolean blnNoExiste = true;

		if (listaAutorizaCreditoComp.size() > 0 && listaAutorizaCreditoComp != null) {
			for (int k = 0; k < listaAutorizaCreditoComp.size(); k++) {
				AutorizaCredito autorizaCredito = new AutorizaCredito();
				autorizaCredito = listaAutorizaCreditoComp.get(k)
						.getAutorizaCredito();

				if (intTipoValidacion.intValue() == 0) {// usuario
					if (autorizaCredito.getIntPersUsuarioAutoriza().intValue() == (usuario
							.getPerfil().getId().getIntIdPerfil().intValue())) {
						blnNoExiste = false;
						break;
					}

				} else if (intTipoValidacion.intValue() == 1) { // perfil
					if (autorizaCredito.getIntIdPerfilAutoriza().intValue() == (usuario
							.getPerfil().getId().getIntIdPerfil().intValue())) {
						blnNoExiste = false;
						break;
					}

				}

			}
		}

		return blnNoExiste;
	}
	*/
	
	
	/**
	 * 
	 */
	public boolean existeRegistro(Integer intTipoValidacion) {
		boolean blnNoExiste = true;

		if (listaAutorizaCreditoComp != null && !listaAutorizaCreditoComp.isEmpty()) {
			for(AutorizaCreditoComp autorizaCreditoComp : listaAutorizaCreditoComp){
				if(autorizaCreditoComp.getAutorizaCredito().getIntParaEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0){
				
					if (intTipoValidacion.intValue() == 0) {// usuario
						if (autorizaCreditoComp.getAutorizaCredito().getIntPersUsuarioAutoriza().
								compareTo(usuario.getPerfil().getId().getIntIdPerfil())==0) {
							blnNoExiste = false;
							break;
						}

					} else if (intTipoValidacion.intValue() == 1) { // perfil
						if (autorizaCreditoComp.getAutorizaCredito().getIntIdPerfilAutoriza().
								compareTo(usuario.getPerfil().getId().getIntIdPerfil())==0) {
							blnNoExiste = false;
							break;
						}

					}

				}

			}

		}

		return blnNoExiste;
	}
	
	
	
	/**
	 * 
	 */
	public void aceptarAdjuntarInfoCorp() {
		//System.out.println("GDF??? WTF???--------->");
		FileUploadControllerServicio fileUploadController = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");

		try {
			archivoInfoCorp = fileUploadController.getArchivoInfoCorp();
			fileUploadController = new FileUploadControllerServicio();
			// recuperarAdjuntosAutorizacion(expedienteCreditoCompSelected);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 
	 */
	public void aceptarAdjuntarReniec() {
		FileUploadControllerServicio fileUploadController = (FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio");
		try {
			// FileUploadControllerServicio fileUploadController =
			// (FileUploadControllerServicio)getSessionBean("fileUploadControllerServicio");
			archivoReniec = fileUploadController.getArchivoReniec();
			fileUploadController = new FileUploadControllerServicio();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	
	/**
	 * 
	 */
	public void quitarInfoCorp() {
		try {
			archivoInfoCorp = null;
			((FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio")).setArchivoInfoCorp(null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 
	 */
	public void quitarReniec() {
		try {
			archivoReniec = null;
			((FileUploadControllerServicio) getSessionBean("fileUploadControllerServicio")).setArchivoReniec(null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 
	 * @param event
	 */
	public void irModificarSolicitudPrestamoAutoriza(ActionEvent event){
		SolicitudPrestamoController solicitudPrestamoController = (SolicitudPrestamoController) getSessionBean("solicitudPrestamoController");
		// x terminar xxx
		solicitudPrestamoController.irModificarSolicitudPrestamoAutoriza2(event, expedienteCreditoCompSelected);	
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
	
	
	
	
	
	// Getters y Setters
	public AutorizaCredito getBeanAutorizaCredito() {
		return beanAutorizaCredito;
	}

	public void setBeanAutorizaCredito(AutorizaCredito beanAutorizaCredito) {
		this.beanAutorizaCredito = beanAutorizaCredito;
	}

	public AutorizaVerificacion getBeanAutorizaVerificacion() {
		return beanAutorizaVerificacion;
	}

	public void setBeanAutorizaVerificacion(
			AutorizaVerificacion beanAutorizaVerificacion) {
		this.beanAutorizaVerificacion = beanAutorizaVerificacion;
	}

	public ExpedienteCreditoComp getExpedienteCreditoCompSelected() {
		return expedienteCreditoCompSelected;
	}

	public void setExpedienteCreditoCompSelected(
			ExpedienteCreditoComp expedienteCreditoCompSelected) {
		this.expedienteCreditoCompSelected = expedienteCreditoCompSelected;
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

	public Integer getIntIdDependencia() {
		return intIdDependencia;
	}

	public void setIntIdDependencia(Integer intIdDependencia) {
		this.intIdDependencia = intIdDependencia;
	}

	public Integer getIntIdTipoPrestamo() {
		return intIdTipoPrestamo;
	}

	public void setIntIdTipoPrestamo(Integer intIdTipoPrestamo) {
		this.intIdTipoPrestamo = intIdTipoPrestamo;
	}

	public Integer getIntIdEstadoPrestamo() {
		return intIdEstadoPrestamo;
	}

	public void setIntIdEstadoPrestamo(Integer intIdEstadoPrestamo) {
		this.intIdEstadoPrestamo = intIdEstadoPrestamo;
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

	public Boolean getFormAutorizacionPrestamoRendered() {
		return formAutorizacionPrestamoRendered;
	}

	public void setFormAutorizacionPrestamoRendered(
			Boolean formAutorizacionPrestamoRendered) {
		this.formAutorizacionPrestamoRendered = formAutorizacionPrestamoRendered;
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

	public ConfSolicitudFacadeLocal getSolicitudFacade() {
		return solicitudFacade;
	}

	public void setSolicitudFacade(ConfSolicitudFacadeLocal solicitudFacade) {
		this.solicitudFacade = solicitudFacade;
	}

	public List<AutorizaCreditoComp> getListaAutorizaCreditoComp() {
		return listaAutorizaCreditoComp;
	}

	public void setListaAutorizaCreditoComp(
			List<AutorizaCreditoComp> listaAutorizaCreditoComp) {
		this.listaAutorizaCreditoComp = listaAutorizaCreditoComp;
	}

	public List<ExpedienteCreditoComp> getListaAutorizacionCreditoComp() {
		return listaAutorizacionCreditoComp;
	}

	public void setListaAutorizacionCreditoComp(
			List<ExpedienteCreditoComp> listaAutorizacionCreditoComp) {
		this.listaAutorizacionCreditoComp = listaAutorizacionCreditoComp;
	}

	public List<Tabla> getListaTipoOperacion() {
		return listaTipoOperacion;
	}

	public void setListaTipoOperacion(List<Tabla> listaTipoOperacion) {
		this.listaTipoOperacion = listaTipoOperacion;
	}

	public List<Sucursal> getListSucursal() {
		try {
			if (listSucursal == null) {
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote) EJBFactory
						.getRemote(EmpresaFacadeRemote.class);
				this.listSucursal = facade
						.getListaSucursalPorPkEmpresa(Constante.PARAM_EMPRESASESION);
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

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
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
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getStrTxtMsgError() {
		return strTxtMsgError;
	}

	public void setStrTxtMsgError(String strTxtMsgError) {
		this.strTxtMsgError = strTxtMsgError;
	}
	public Archivo getArchivoReniec() {
		return archivoReniec;
	}

	public void setArchivoReniec(Archivo archivoReniec) {
		this.archivoReniec = archivoReniec;
	}

	public Archivo getArchivoInfoCorp() {
		return archivoInfoCorp;
	}

	public void setArchivoInfoCorp(Archivo archivoInfoCorp) {
		this.archivoInfoCorp = archivoInfoCorp;
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

	public List<RequisitoCreditoComp> getListaRequisitoCreditoComp() {
		return listaRequisitoCreditoComp;
	}

	public void setListaRequisitoCreditoComp(
			List<RequisitoCreditoComp> listaRequisitoCreditoComp) {
		this.listaRequisitoCreditoComp = listaRequisitoCreditoComp;
	}

	public String getStrTxtMsgValidacion() {
		return strTxtMsgValidacion;
	}

	public void setStrTxtMsgValidacion(String strTxtMsgValidacion) {
		this.strTxtMsgValidacion = strTxtMsgValidacion;
	}

	public String getStrTxtMsgVigencia() {
		return strTxtMsgVigencia;
	}

	public void setStrTxtMsgVigencia(String strTxtMsgVigencia) {
		this.strTxtMsgVigencia = strTxtMsgVigencia;
	}

	public List<AutorizaVerificacion> getListaAutorizaVerificacionAdjuntos() {
		return listaAutorizaVerificacionAdjuntos;
	}

	public void setListaAutorizaVerificacionAdjuntos(
			List<AutorizaVerificacion> listaAutorizaVerificacionAdjuntos) {
		this.listaAutorizaVerificacionAdjuntos = listaAutorizaVerificacionAdjuntos;
	}

	public List<ConfServSolicitud> getListaAutorizacionConfigurada() {
		return listaAutorizacionConfigurada;
	}

	public void setListaAutorizacionConfigurada(
			List<ConfServSolicitud> listaAutorizacionConfigurada) {
		this.listaAutorizacionConfigurada = listaAutorizacionConfigurada;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		AutorizacionPrestamoController.log = log;
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

	public LibroDiarioFacadeRemote getLibroDiarioFacade() {
		return libroDiarioFacade;
	}

	public void setLibroDiarioFacade(LibroDiarioFacadeRemote libroDiarioFacade) {
		this.libroDiarioFacade = libroDiarioFacade;
	}

	public PermisoFacadeRemote getPermisoFacade() {
		return permisoFacade;
	}

	public void setPermisoFacade(PermisoFacadeRemote permisoFacade) {
		this.permisoFacade = permisoFacade;
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

	public Boolean getBlnTxtBusqueda() {
		return blnTxtBusqueda;
	}

	public void setBlnTxtBusqueda(Boolean blnTxtBusqueda) {
		this.blnTxtBusqueda = blnTxtBusqueda;
	}

	public String getStrNumeroSolicitudBusq() {
		return strNumeroSolicitudBusq;
	}

	public void setStrNumeroSolicitudBusq(String strNumeroSolicitudBusq) {
		this.strNumeroSolicitudBusq = strNumeroSolicitudBusq;
	}

	public Boolean getBlnBusquedaCondicion() {
		return blnBusquedaCondicion;
	}

	public void setBlnBusquedaCondicion(Boolean blnBusquedaCondicion) {
		this.blnBusquedaCondicion = blnBusquedaCondicion;
	}

	public EstadoCredito getEstadoCondicionFiltro() {
		return estadoCondicionFiltro;
	}

	public void setEstadoCondicionFiltro(EstadoCredito estadoCondicionFiltro) {
		this.estadoCondicionFiltro = estadoCondicionFiltro;
	}

	public List getListaTablaEstadoPago() {
		return listaTablaEstadoPago;
	}

	public void setListaTablaEstadoPago(List listaTablaEstadoPago) {
		this.listaTablaEstadoPago = listaTablaEstadoPago;
	}

	public Integer getIntTipoCreditoFiltro() {
		return intTipoCreditoFiltro;
	}

	public void setIntTipoCreditoFiltro(Integer intTipoCreditoFiltro) {
		this.intTipoCreditoFiltro = intTipoCreditoFiltro;
	}

	public Boolean getBlnBusquedaFechas() {
		return blnBusquedaFechas;
	}

	public void setBlnBusquedaFechas(Boolean blnBusquedaFechas) {
		this.blnBusquedaFechas = blnBusquedaFechas;
	}

	public EstadoCredito getEstadoAutorizacionFechas() {
		return estadoAutorizacionFechas;
	}

	public void setEstadoAutorizacionFechas(EstadoCredito estadoAutorizacionFechas) {
		this.estadoAutorizacionFechas = estadoAutorizacionFechas;
	}

	public EstadoCredito getEstadoAutorizacionSuc() {
		return estadoAutorizacionSuc;
	}

	public void setEstadoAutorizacionSuc(EstadoCredito estadoAutorizacionSuc) {
		this.estadoAutorizacionSuc = estadoAutorizacionSuc;
	}

	public List<Tabla> getListaTablaDeSucursal() {
		return listaTablaDeSucursal;
	}

	public void setListaTablaDeSucursal(List<Tabla> listaTablaDeSucursal) {
		this.listaTablaDeSucursal = listaTablaDeSucursal;
	}

	public Integer getIntIdSubsucursalFiltro() {
		return intIdSubsucursalFiltro;
	}

	public void setIntIdSubsucursalFiltro(Integer intIdSubsucursalFiltro) {
		this.intIdSubsucursalFiltro = intIdSubsucursalFiltro;
	}

	public List getListaSubsucursalBusq() {
		return listaSubsucursalBusq;
	}

	public void setListaSubsucursalBusq(List listaSubsucursalBusq) {
		this.listaSubsucursalBusq = listaSubsucursalBusq;
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


	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}


	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
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


	public List<Tabla> getListaTablaCreditoEmpresa() {
		return listaTablaCreditoEmpresa;
	}


	public void setListaTablaCreditoEmpresa(List<Tabla> listaTablaCreditoEmpresa) {
		this.listaTablaCreditoEmpresa = listaTablaCreditoEmpresa;
	}


	public EstructuraDetalle getEstructuraDetalleBusqueda() {
		return estructuraDetalleBusqueda;
	}


	public void setEstructuraDetalleBusqueda(
			EstructuraDetalle estructuraDetalleBusqueda) {
		this.estructuraDetalleBusqueda = estructuraDetalleBusqueda;
	}


	public List getListaTablaSucursal() {
		return listaTablaSucursal;
	}


	public void setListaTablaSucursal(List listaTablaSucursal) {
		this.listaTablaSucursal = listaTablaSucursal;
	}


	public EstadoCredito getEstadoAutorizacionBusqueda() {
		return estadoAutorizacionBusqueda;
	}


	public void setEstadoAutorizacionBusqueda(
			EstadoCredito estadoAutorizacionBusqueda) {
		this.estadoAutorizacionBusqueda = estadoAutorizacionBusqueda;
	}


	public List<ExpedienteCreditoComp> getListaExpedienteCreditoComp() {
		return listaExpedienteCreditoComp;
	}


	public void setListaExpedienteCreditoComp(
			List<ExpedienteCreditoComp> listaExpedienteCreditoComp) {
		this.listaExpedienteCreditoComp = listaExpedienteCreditoComp;
	}


	public List getListaTablaTipoCredito() {
		return listaTablaTipoCredito;
	}


	public void setListaTablaTipoCredito(List listaTablaTipoCredito) {
		this.listaTablaTipoCredito = listaTablaTipoCredito;
	}


	public Boolean getBlnMostrarAutorizar() {
		return blnMostrarAutorizar;
	}


	public void setBlnMostrarAutorizar(Boolean blnMostrarAutorizar) {
		this.blnMostrarAutorizar = blnMostrarAutorizar;
	}


	public ConfServSolicitud getAutorizacionConfigurada() {
		return autorizacionConfigurada;
	}


	public void setAutorizacionConfigurada(ConfServSolicitud autorizacionConfigurada) {
		this.autorizacionConfigurada = autorizacionConfigurada;
	}


	public CreditoFacadeRemote getCreditoFacade() {
		return creditoFacade;
	}


	public void setCreditoFacade(CreditoFacadeRemote creditoFacade) {
		this.creditoFacade = creditoFacade;
	}


	public Boolean getBlnBloquearCampo() {
		return blnBloquearCampo;
	}


	public void setBlnBloquearCampo(Boolean blnBloquearCampo) {
		this.blnBloquearCampo = blnBloquearCampo;
	}


	public Boolean getFormAutorizacionViewPrestamoRendered() {
		return formAutorizacionViewPrestamoRendered;
	}


	public void setFormAutorizacionViewPrestamoRendered(
			Boolean formAutorizacionViewPrestamoRendered) {
		this.formAutorizacionViewPrestamoRendered = formAutorizacionViewPrestamoRendered;
	}


	public String getStrSolicitudPrestamo() {
		return strSolicitudPrestamo;
	}


	public void setStrSolicitudPrestamo(String strSolicitudPrestamo) {
		this.strSolicitudPrestamo = strSolicitudPrestamo;
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


	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}


	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}


	public String getStrMsgErrorValidarMovimientos() {
		return strMsgErrorValidarMovimientos;
	}


	public void setStrMsgErrorValidarMovimientos(
			String strMsgErrorValidarMovimientos) {
		this.strMsgErrorValidarMovimientos = strMsgErrorValidarMovimientos;
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


	public ExpedienteCreditoComp getExpedienteCompBusq() {
		return expedienteCompBusq;
	}


	public void setExpedienteCompBusq(ExpedienteCreditoComp expedienteCompBusq) {
		this.expedienteCompBusq = expedienteCompBusq;
	}

	

}