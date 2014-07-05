package pe.com.tumi.cobranza.movcuentacte.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.sql.Timestamp;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Months;

import pe.com.tumi.cobranza.cuentacte.facade.CuentacteFacadeLocal;
import pe.com.tumi.cobranza.cuentacte.facade.CuentacteFacadeRemote;
import pe.com.tumi.cobranza.gestion.controller.CobranzaController;
import pe.com.tumi.cobranza.planilla.bo.DescuentoIndebidoBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteBloqueoBO;
import pe.com.tumi.cobranza.planilla.bo.SolicitudCtaCteTipoBO;
import pe.com.tumi.cobranza.planilla.bo.TransferenciaBO;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteBloqueo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteId;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipoId;
import pe.com.tumi.cobranza.planilla.domain.Transferencia;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeLocal;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.cobranza.planilla.service.DescuentoIndebidoService;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.cobranza.prioridad.facade.PrioridadDescuentoFacadeRemote;
import pe.com.tumi.common.DownloadFile;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.controller.UtilCobranza;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioEstructuraPK;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoTipoGarantiaComp;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeRemote;
import pe.com.tumi.credito.socio.creditos.facade.CreditoGarantiaFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.domain.composite.EstructuraComp;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.GarantiaCreditoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeRemote;
import java.util.Collections;  
import java.util.Comparator; 

/************************************************************************/
/* Nombre de la clase: CuentaCteController                              */
/* Funcionalidad : Clase para la antencion de solicitudes.              */
/*                                                                      */
/* Ref. : 																*/
/* Autor : FRAMIREZ														*/
/* Versión : V1 																*/
/* Fecha creación : 01/01/2013 											*/
/* **********************************************************************/

public class CuentaCteController extends GenericController {
	
	//Variables locales beanSolCtaCte
	protected  static Logger 		      log = Logger.getLogger(CuentaCteController.class);
	private 	Estructura 					estrucBusq;
	private 	List<EstructuraDetalle> 	listaEstructuraDetalle;
	private	  SolicitudCtaCte		  beanSolCtaCte;
	private   List<SolicitudCtaCte>   beanListSolCtaCte;
	private   SocioComp socioCom;
	private   SocioComp socioComGarante;
	
	private  String strDescEntidad;
	private  String strDescEntidadPres;
	
	private  String strCboAnio;
	private  Integer intCboMes;
	private  String strObservacion;
	private  String strListaPersonaRol;
	
	private List<String> listaAnio;
	private Archivo		archivoDocSolicitud;
	private Archivo		archivoDocSolicitud1;
	private Archivo		archivoDocSolicitud2;
	
	private Archivo		archivoDocSolicitudTemp;
		
	private boolean     formBotonQuitarArchDisabled;
	private boolean     intTipoRolDisabled;
	private boolean		deshabilitaCondicionFinal;
	
	private List<Tabla> listaTipoCondicion;
	private List<Tabla> listaCondicion;
	
	  
	// Parámetros de busqueda Enlace Recibo
	private Integer intCboSucursal;
	private Integer intCboTipoSolicitud;
	private Integer intCboTipoSolicitudAux;
	
	private Integer intCboEstadoSolicitud;
	private String  strInNroDniSocio;
	private Integer intCboParaTipo;
	private Integer intCboParaTipoEstado;
	private Date    dtFechaInicio;
	private Date    dtFechaFin;
	
	//Propiedades de lista de combos.
	
	private 	List<UsuarioSubSucursal> 		listGestor;
	private     List<Tabla> listaTipoRol;
	private     Integer tamanioListaTipoSol;
	private    String descSocioComp;
	
   // Datos Generales del Usuario
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBCURSAL;
	
	
	private boolean bolEnlaceRecibo;
	
	//Proopiedades para deshabilitar.
	private boolean btnGrabarDisabled;
	private boolean cboSucursalDisabled;
	private boolean cboSubSucursalDisabled;
	private boolean texNumeroReciboDisabled;
	
	private boolean btnQuitarArchivoDisabled;
	private boolean txtObservacionDisabled;
	private boolean cboTipoModalidadDisabled;
	private boolean cboMesDisabled;
	private boolean cboAnioDisabled;
	private boolean chkTipoSolicitudDisabled;
	private boolean btnAnularDisabled;
	private boolean btnAtenderDisabled;
	private boolean btnAgregarDisabled;
	private boolean btnVerDisabled;
	
	
	
	//Propiedades para renderizar
	private boolean cuentaCteRendered;
	private boolean cuentaCteFormRendered;
	
	private boolean btnValidarRendered;
	private boolean btnAnularRendered;
	private boolean texGestorRedered;

	//Popup Busqueda Socio
	
	private String txtNombresSocio;
	private String txtDniSocio;
	private String cboTipo;
	
	
	private List<SocioComp> listSocioComp;
	
	private List<Integer> selectListaTipoSolicitud;  
	private List<Tabla> listaTipoSolicitud;
	private List<Tabla> listaTipoMovimiento;
	private List<Tabla> listaMotivoCambioGarante;
	private List<Tabla> listaMotivoCambioEntidad;
	
	private List<Tabla> listaMotivoCambioCondicionLaboral;
	
	private String strDesSucursal;
	private String strDesSucursalPres;
	
	private String strDesModaliadTipoSocio;
	private String strDesModaliadTipoSocioPres;
	
	
	
	
	//Auxiliar
	
	private String strFechSocioComp; 
	private Integer intCondicionLaboral;
	private Integer intCondicionLaboralDet;
	
	
	//Form Tipo de Movimientos
	
	private SolicitudCtaCteTipo solCtaCteTipo;
	private List<Tabla> listaMotivoLicencia;
	
	private SolicitudCtaCteTipo solicitudCtaCteTipoSel;
	
	private boolean esGrabadoOk;
	private String messageValidation;
	
	private boolean    cboAprobarRechazarDisabled;
	private	  SolicitudCtaCteTipo  beanSolCtaCteTipoSel;

    private Integer nroExpediente;	

	//Popup Garante
	private String txtDniGarante;
	private List<SocioComp> listGaranteComp;
	private boolean esValidoGarante;
	private List<GarantiaCreditoComp> listarGarantiaCreditoComp;
	private GarantiaCreditoComp beanGarantiaCreditoSel; 
	
	private boolean habilitarFormLicencia;
	private boolean habilitarFormCambioGarante;
	private boolean habilitarFormCambioEntidad;
	private boolean habilitarFormCambioCondicionLaboral;
	private boolean habilitarFormDescuentoIndebido;
	
	
	//Cambio Entidad
	
	private List<SocioEstructura> beanListaSocioEstructura;
	private 	EstructuraDetalle 			entidadSeleccionada;
	
	private 	List<Sucursal> 			listJuridicaSucursal;
	private 	List<Subsucursal> 		listJuridicaSubsucursal;
	
	private Integer intSucuIdSucursal;
	private Integer intSudeIdSubsucursal;
	
	private 	List<Estructura>			listEstructura;
	private Integer beanTipoEstructura;
	
	
	private String messagePlanillaOrigen;
	private String messagePlanillaOrigenNueva;
	
	private String messagePlanillaPrestamo;
	private String messagePlanillaPrestamoNueva;
	
	
	
	private String strNombreEntidad;
	private Boolean isContratado;
	private Boolean blnFechaContratoDisabled;
	
	//combo de Detalle de Condición Laboral
	private 	List<Tabla> 			listDetCondicionLaboral;
	
	private String strMessageAlertEnvioPlanilla;
	private String strPreguntaAlertaEnvioPlanilla;
	
	private String strMensajeCambioEntidad;
	private Boolean habilitarBotonAlertEnvioPlanilla;
	private Boolean habilitarFormAlerEnvioPlanilla;
	
	//Cambio Condicio de la cuenta
	
	private List<Tabla> listaMotivoCambioCondicion;
	private List<Tabla> listaCondicionFinal;
	
	private Boolean dtFechaFinDisbled;
	
	//Cambio condicion Laboral
	private Archivo fileContrato;
	
	private Boolean intCondicionLaboralDisabled;
	
	private Boolean habilitarDatosLaborales;
	private List<Tabla> listaMotivoDescuentoIndebido;
	
	
	//Descuento Indebido
	private DescuentoIndebidoService descuentoIndebidoService = (DescuentoIndebidoService)TumiFactory.get(DescuentoIndebidoService.class);
	
	private Integer intEstadoPago;
	
	//Transferencia
	
	private List<Tabla>	listaMotivoTransferencia;
	private Boolean habilitarFormTransferencia;
	private Boolean radioEntreCuentas;
	private Boolean radioEntreConceptos;
	private BigDecimal bgMontoTransferencia;
	private List<Expediente> beanListaExpediente;
	
	private BigDecimal bgMontoSaldoTotalAbono;
	private BigDecimal bgMontoSaldoAportaciones;
	
	private String strDescEntidadGarante;
	private String strDesSucursalGarante;
	private String strDesModaliadTipoSocioGarante;
	private Boolean esEditableMontoSaldoAbono;
	
	private String strPeriodoTrans;
	
	//-------------------------------------------------------------------------------------------------------------------------------------------
	//Métodos de GestionCobranzaController para Mantenimientos
	//-------------------------------------------------------------------------------------------------------------------------------------------
	public CuentaCteController() {
//		limpiarFormCobranza();
//		setFormCobranzaEnabled(true);
//		setCobranzaRendered(false);
		inicio(null);
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("CobranzaController");
	}
	
	public void inicio(ActionEvent event){
	    log.info("-----------iniciando caraxo---------------------------");
		
		PermisoPerfil permiso = null;
		PermisoPerfilId id = null;
		Usuario usuario = null;
		Integer MENU_GESTION_COBRANZA = 172;
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			SESION_IDUSUARIO = usuario.getIntPersPersonaPk();
			if(usuario != null){
				id = new PermisoPerfilId();
				id.setIntPersEmpresaPk(usuario.getPerfil().getId().getIntPersEmpresaPk());
				id.setIntIdTransaccion(MENU_GESTION_COBRANZA);
				id.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
				
				 SESION_IDEMPRESA = usuario.getEmpresa().getIntIdEmpresa();
				 SESION_IDSUCURSAL = usuario.getSucursal().getId().getIntIdSucursal();
				 SESION_IDSUBCURSAL = usuario.getSubSucursal().getId().getIntIdSubSucursal();
			}else{
				 bolEnlaceRecibo = false;
		    }
		} catch (Exception e) {
			log.error(e);
		}
		
		inicializarFormSolicitudCtaCte();
		beanListSolCtaCte = null;
		btnGrabarDisabled = true;
		cuentaCteRendered = false;
		cuentaCteFormRendered = false;
		
	}
	
	//Acciones
	public void buscar(ActionEvent envent){
	try {
		
		    log.info("-----------------------------Busqueda General----------------------------------------------------");
			PlanillaFacadeRemote SocioCompFacade = (PlanillaFacadeRemote)EJBFactory.getRemote(PlanillaFacadeRemote.class);
			
			Integer idSucursal            = getIntCboSucursal();
			Integer idTipoSolicitud       = getIntCboTipoSolicitud();
			String  nroDniSocio           = getStrInNroDniSocio();
			Integer idEstadoSolicitud     = getIntCboEstadoSolicitud();
			Integer intCboParaTipoEstado  = getIntCboParaTipoEstado();
			Date    dtFechaInicio         = getDtFechaInicio();
			Date    dtFechaFin            = getDtFechaFin();
			
			
			 List<SolicitudCtaCte> lista  = SocioCompFacade.buscarMovimientoCtaCte(SESION_IDEMPRESA, idSucursal, null, idEstadoSolicitud, nroDniSocio,intCboParaTipoEstado,dtFechaInicio,dtFechaFin);
			 
			
			 List<SolicitudCtaCte> listaTemp = null;
			 
			 if (idTipoSolicitud != 0){	
				 List<SolicitudCtaCte> listaSolCtaCte = new ArrayList<SolicitudCtaCte>();
				 for (SolicitudCtaCte solicitudCtaCte : lista) {
					 
					 List<EstadoSolicitudCtaCte> listaEtado =  solicitudCtaCte.getListaEstSolCtaCte();
					 Integer cantEstado = listaEtado.size();
					 
					 if (cantEstado == 1){
						 solicitudCtaCte.setIntEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE);
					 }else{
						 solicitudCtaCte.setIntEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO);
					 }
					
						 List<SolicitudCtaCteTipo> listaTipo =  solicitudCtaCte.getListaSolCtaCteTipo();
						 for (SolicitudCtaCteTipo solicitudCtaCteTipo : listaTipo) {
							 if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(idTipoSolicitud)){
								 listaSolCtaCte.add(solicitudCtaCte);
								 break;
							 }
						 }	
					 }	 
				 listaTemp = listaSolCtaCte;
				}
			    
			   if (listaTemp != null &&  listaTemp.size() > 0){
			    	lista.clear();
			    	lista = listaTemp;
			    	beanListSolCtaCte = listaTemp;
			  }
			
			  if (idEstadoSolicitud != 0) {
				  
					 List<SolicitudCtaCte> listaSolCtaCte2 = new ArrayList<SolicitudCtaCte>();
					 for (SolicitudCtaCte solicitudCtaCte : lista) {
						 List<EstadoSolicitudCtaCte> listaEtado =  solicitudCtaCte.getListaEstSolCtaCte();
						 Integer cantEstado = listaEtado.size();
						 
						 if (cantEstado == 1){
							 solicitudCtaCte.setIntEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE);
						 }else{
							 solicitudCtaCte.setIntEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO);
						 }
						 
						 if (solicitudCtaCte.getIntEstadoSolCtaCte().equals(idEstadoSolicitud)){
							 listaSolCtaCte2.add(solicitudCtaCte);
						 }
						 
				}
					 
				if (beanListSolCtaCte != null) {beanListSolCtaCte.clear();}
				    beanListSolCtaCte = listaSolCtaCte2;
			  }
			
			  if (idEstadoSolicitud == 0 && idTipoSolicitud == 0){
				  List<SolicitudCtaCte> listaSolCtaCte = new ArrayList<SolicitudCtaCte>();
				  for (SolicitudCtaCte solicitudCtaCte : lista) {
						 
						 List<EstadoSolicitudCtaCte> listaEtado =  solicitudCtaCte.getListaEstSolCtaCte();
						 Integer cantEstado = listaEtado.size();
						 
						 if (cantEstado == 1){
							 solicitudCtaCte.setIntEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE);
						 }else{
							 solicitudCtaCte.setIntEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO);
						 }
						 listaSolCtaCte.add(solicitudCtaCte);
							
						 }	 
				  beanListSolCtaCte = lista;

			  }
			  
			 
			 log.info("beanListSolCtaCte.size: "+beanListSolCtaCte.size());
			 
			 btnAnularDisabled = false;
			 btnAtenderDisabled = false;
		} catch (EJBFactoryException e) {
			e.printStackTrace();
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		} catch (BusinessException e) {
			e.printStackTrace();
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		}
	}
	public void seleccionar(ActionEvent event){
		SolicitudCtaCte  solCtaCte =  (SolicitudCtaCte)event.getComponent().getAttributes().get("item");
		setBeanSolCtaCte(solCtaCte);
		
		
     	btnAnularDisabled  = false;
     	btnAgregarDisabled = false;
     	
     	if (solCtaCte.getListaEstSolCtaCte().size() == 1){
     		 btnAtenderDisabled = false;
     		 btnVerDisabled = true;
     	}else{
     		 btnAtenderDisabled = true;
     		 btnVerDisabled = false;
     	}
	}
	
	public void nuevo(ActionEvent event){
		
		inicializarFormSolicitudCtaCte();
		cuentaCteRendered = false;
		cuentaCteFormRendered = true;
		intTipoRolDisabled = false;
		
	    btnQuitarArchivoDisabled = false;
		txtObservacionDisabled = false;
		cboTipoModalidadDisabled = false;
		cboMesDisabled = false;
		cboAnioDisabled = false;
		chkTipoSolicitudDisabled = false;
		
	}
	
	public void grabar(ActionEvent event){
		CuentacteFacadeLocal cuentacteFacade = null;
		SolicitudCtaCte solCtaCte = null;
		boolean esValido = true;
			
		//Validar Datos
		esValido = validarForm();
		if (!esValido){
			return;
		}		
		
	   //Grabar	
		try{
			
			cuentacteFacade = (CuentacteFacadeLocal)EJBFactory.getLocal(CuentacteFacadeLocal.class);
			EstadoSolicitudCtaCte estSolCtaCte = new EstadoSolicitudCtaCte();
						
			estSolCtaCte.setIntPersUsuarioEstado(SESION_IDUSUARIO);
			estSolCtaCte.setIntPersEmpresaEstado(SESION_IDEMPRESA);
			estSolCtaCte.setIntSucuIduSusucursal(SESION_IDSUCURSAL);
			estSolCtaCte.setIntSudeIduSusubsucursal(SESION_IDSUBCURSAL);

			if(getBeanSolCtaCte().getSolCtaCteTipo().getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICIONLABORAL)){
				if (fileContrato != null){
					socioCom.getPersona().getNatural().getPerLaboral().setContrato(null);
					Archivo archivo  = grabarArchvioDocAdjunto(fileContrato);
					socioCom.getPersona().getNatural().getPerLaboral().setContrato(archivo);
				}
			}
			
			getBeanSolCtaCte().setEstSolCtaCte(estSolCtaCte);
			solCtaCte = cuentacteFacade.grabarSolicitudCtaCteAntedido(getBeanSolCtaCte());
			
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
			e.printStackTrace();
			log.error(e);
		} catch (EJBFactoryException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
		    e.printStackTrace();
		    log.error(e);
		}catch (Exception e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
		    e.printStackTrace();
		    log.error(e);
		}
		
		
		if (solCtaCte != null){
			FacesContextUtil.setMessageSuccess(""+FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
		}
		
		cuentaCteFormRendered = false;
		cuentaCteRendered = false;
		beanListSolCtaCte = null;
		btnGrabarDisabled = true;
		buscar(event);
	}
	
	public void cancelar(ActionEvent event){
		cuentaCteFormRendered = false;
		cuentaCteRendered = false;
		btnGrabarDisabled = true;
		cuentaCteRendered = false;
		intTipoRolDisabled = false;
	}
	
	public void ver(ActionEvent event){
		    obtener(null);
		btnGrabarDisabled = true;
		cboAprobarRechazarDisabled = true;
		deshabilitaCondicionFinal = Boolean.TRUE;
	}
	
	
	public boolean validarForm(){
	 boolean esValido = true;	
	 
	   
	   List<SolicitudCtaCteTipo> listSolCtaTipo = getBeanSolCtaCte().getListaSolCtaCteTipo();
	 
	   for (SolicitudCtaCteTipo solCtaCteTipo : listSolCtaTipo) {
		
		   if (solCtaCteTipo.getIntParaEstadoanalisis().equals(1) && (solCtaCteTipo.getChecked() == null || solCtaCteTipo.getChecked() == false)){
			   esValido = false;
			   FacesContextUtil.setMessageError("Debe Aprobar/Rechazar todo los tipos de Solicitudes.");
		   }
	   }
	   
	   return esValido;
	}
	
	public void buscarSocio(ActionEvent event){
		
	try{	
		
		Integer intTipoRol = getIntCboParaTipo();// getBeanSolCtaCte().getIntParaTipo();
		
		String cboTipo = getCboTipo();
		String txtDni = "";
		String txtNombre = "";
		if (cboTipo != null)
		if (cboTipo.equals("1")){
			txtDni = getTxtDniSocio();
		 }else{
			txtNombre = getTxtDniSocio();
		 }
				
		SocioFacadeRemote socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
		listSocioComp =  socioFacade.getListaBuscarSocioConCuentaVigente(SESION_IDEMPRESA, intTipoRol, txtNombre,txtDni);
		    
		}catch (EJBFactoryException e) {
			 e.printStackTrace();
			 log.error(e);
			 FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		} catch (BusinessException e) {
			e.printStackTrace();
			 log.error(e);
			 FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}
	}
	
	
	public void seleccionarSocio(ValueChangeEvent event){
		log.info("---------------------------------Debugging enlaceReciboController.seleccionarSocioComp-----------------------------");
		
		String nroSocioComp = (String) event.getNewValue();
		log.info("nroSocioComp: "+nroSocioComp);
		SocioComp socioComp = (SocioComp)listSocioComp.get(Integer.valueOf(nroSocioComp));
		socioCom = socioComp;
		
		getBeanSolCtaCte().setIntCsocCuenta(socioCom.getCuenta().getId().getIntCuenta());
		List<SocioEstructura>  lista =  socioCom.getSocio().getListSocioEstructura();
		try{	
		   
		for (SocioEstructura socioEstructura : lista) {
			EstructuraFacadeRemote estructuraFacade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			TablaFacadeRemote   tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			
			
			EstructuraId id = new EstructuraId();
			id.setIntCodigo(socioEstructura.getIntCodigo());
			id.setIntNivel(socioEstructura.getIntNivel());
			
			Estructura entidad =   estructuraFacade.getEstructuraPorPk(id);
			Juridica  juridica = personaFacade.getJuridicaPorPK(entidad.getIntPersPersonaPk());
			
			Sucursal sucursall = new Sucursal();
			sucursall.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
			sucursall.getId().setIntPersEmpresaPk(socioEstructura.getIntEmpresaSucAdministra());
			
			Sucursal sucursal = empresaFacade.getSucursalPorPK(sucursall);
			Juridica suc = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
			
			Tabla tabla = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_NIVELENTIDAD), socioEstructura.getIntNivel());
			String nivel = tabla.getStrDescripcion();
			String desEntidad = juridica.getStrRazonSocial();
			String desSucursal = suc.getStrRazonSocial();
			
			Tabla tabla2 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_TIPOSOCIO), socioEstructura.getIntTipoSocio());
			String tipoSocio = tabla2.getStrDescripcion();
			
			Tabla tabla3 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_MODALIDADPLANILLA), socioEstructura.getIntModalidad());
			String modalidad = tabla3.getStrDescripcion();
			
			
			strDescEntidad = nivel +":"+desEntidad+"-"+desSucursal+"-"+tipoSocio+"-"+modalidad;
			
			getBeanSolCtaCte().setIntPersEmpresa(socioCom.getSocio().getId().getIntIdEmpresa());
			getBeanSolCtaCte().setIntPersPersona(socioCom.getSocio().getId().getIntIdPersona());
			getBeanSolCtaCte().setIntCsocCuenta(socioCom.getCuenta().getId().getIntCuenta());
			getBeanSolCtaCte().setIntSucuIdsucursalsocio(socioEstructura.getIntIdSucursalAdministra());
			getBeanSolCtaCte().setIntSudeIdsubsucursalsocio(socioEstructura.getIntIdSucursalAdministra());
			
			 
		     for (Tabla tipoRol : getListaTipoRol()) {
		       	if (tipoRol.getIntIdDetalle().equals(getIntCboParaTipo())){
		       			 strListaPersonaRol = tipoRol.getStrDescripcion();
		     	}
			 }
			
			break;
		}
		
		
		
	     }catch (EJBFactoryException e) {
	    	    e.printStackTrace();
				log.error(e);
				FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);		
		} catch (BusinessException e) {
			 e.printStackTrace();
			 log.error(e);
			 FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			
		}catch(Exception e){
			e.printStackTrace();e.printStackTrace();
			log.error(e);
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
	   }
		
		cuentaCteRendered = true;
		intTipoRolDisabled = true;
		btnGrabarDisabled = false;
  }

   public void obtener(ActionEvent event){
	   
	    SolicitudCtaCte solCtaCte = null; 
	   
	    
	    try{
	    	PlanillaFacadeLocal planillaFacade = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
		       
	    	solCtaCte =  planillaFacade.obtenerSolicitudCtaCte(getBeanSolCtaCte());
	    	
	        SocioFacadeRemote socioFacade = (SocioFacadeRemote)EJBFactory.getRemote(SocioFacadeRemote.class);
	        CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
	        PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
	        
	        SocioPK socioPk = new SocioPK();
	        socioPk.setIntIdEmpresa(solCtaCte.getIntPersEmpresa());
	        socioPk.setIntIdPersona(solCtaCte.getIntPersPersona());
	        
	      
	        
	        socioCom =  socioFacade.getSocioNatural(socioPk);
	        
	        PersonaEmpresaPK pk = new PersonaEmpresaPK();
	        pk.setIntIdEmpresa(solCtaCte.getIntPersEmpresa());
	        pk.setIntIdPersona(solCtaCte.getIntPersPersona());
	        
	        List<PersonaRol>  listaPersonaRol =   personaFacade.getListaPersonaRolPorPKPersonaEmpresa(pk);
	        
	        TablaFacadeRemote   tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
	        
	        strListaPersonaRol = " ";
	        for (PersonaRol personaRol : listaPersonaRol) {
	        	
	        	for (Tabla tabla : getListaTipoRol()) {
	        		
	        		if (tabla.getIntIdDetalle().equals(personaRol.getId().getIntParaRolPk())){
	        			strListaPersonaRol = tabla.getStrDescripcion()+strListaPersonaRol;
	        		}
				}
	        }
	        
	        
	        CuentaId cuentaId = new  CuentaId();
	        cuentaId.setIntCuenta(solCtaCte.getIntCsocCuenta());
	        cuentaId.setIntPersEmpresaPk(socioPk.getIntIdEmpresa());
	        
	        Cuenta cuenta =  cuentaFacade.getCuentaPorId(cuentaId);
	        socioCom.setCuenta(cuenta);
	        
	        List<SocioEstructura>  lista =  socioCom.getSocio().getListSocioEstructura();
	        for (SocioEstructura socioEstructura : lista) {
	        	
	        	EstructuraFacadeRemote estructuraFacade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
				EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				
				
				EstructuraId id = new EstructuraId();
				id.setIntCodigo(socioEstructura.getIntCodigo());
				id.setIntNivel(socioEstructura.getIntNivel());
				
				Estructura entidad =   estructuraFacade.getEstructuraPorPk(id);
				Juridica  juridica = personaFacade.getJuridicaPorPK(entidad.getIntPersPersonaPk());
				
				
				Sucursal sucursall = new Sucursal();
				sucursall.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
				sucursall.getId().setIntPersEmpresaPk(socioEstructura.getIntEmpresaSucAdministra());
				
				Sucursal sucursal = empresaFacade.getSucursalPorPK(sucursall);
				Juridica suc = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
				
				Tabla tabla = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_NIVELENTIDAD), socioEstructura.getIntNivel());
				String nivel = tabla.getStrDescripcion();
				String desEntidad = juridica.getStrRazonSocial();
				String desSucursal = suc.getStrRazonSocial();
				
				Tabla tabla2 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_TIPOSOCIO), socioEstructura.getIntTipoSocio());
				String tipoSocio = tabla2.getStrDescripcion();
				
				Tabla tabla3 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_MODALIDADPLANILLA), socioEstructura.getIntModalidad());
				String modalidad = tabla3.getStrDescripcion();
				
				if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
					strDescEntidad = nivel +":   "+desEntidad;
					strDesSucursal = desSucursal;
					strDesModaliadTipoSocio = tipoSocio+"-"+modalidad;
				}else{
					strDescEntidadPres = nivel +":   "+desEntidad;
					strDesSucursalPres = desSucursal;
					strDesModaliadTipoSocioPres = tipoSocio+"-"+modalidad;
				}
				
				
			}
	        
	        if (solCtaCte.getIntPeriodo() != null){
	        	   String periodo = solCtaCte.getIntPeriodo().toString();
	        	   strCboAnio = periodo.substring(0, 4);
//	        	   intCboMes  = Integer.parseInt(periodo.substring(4,periodo.length()));
	        }
	     
	        GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	        
	        ArchivoId idArchivo = new ArchivoId();
	        idArchivo.setIntParaTipoCod(solCtaCte.getIntParaTipo());
	        idArchivo.setIntItemArchivo(solCtaCte.getIntMaeItemarchivo());
	        idArchivo.setIntItemHistorico(solCtaCte.getIntMaeItemhistorico());
	        
	        archivoDocSolicitud =  facade.getArchivoPorPK(idArchivo);
	        
	        ArchivoId idArchivo1 = new ArchivoId();
	        idArchivo1.setIntParaTipoCod(solCtaCte.getIntParaTipo1());
	        idArchivo1.setIntItemArchivo(solCtaCte.getIntMaeItemarchivo1());
	        idArchivo1.setIntItemHistorico(solCtaCte.getIntMaeItemhistorico1());
	        
	        archivoDocSolicitud1 =  facade.getArchivoPorPK(idArchivo1);
	        
	        ArchivoId idArchivo2 = new ArchivoId();
	        idArchivo2.setIntParaTipoCod(solCtaCte.getIntParaTipo2());
	        idArchivo2.setIntItemArchivo(solCtaCte.getIntMaeItemarchivo2());
	        idArchivo2.setIntItemHistorico(solCtaCte.getIntMaeItemhistorico2());
	        
	        archivoDocSolicitud2 =  facade.getArchivoPorPK(idArchivo2);
	        
	        archivoDocSolicitudTemp = null;
	        
	        List<EstadoSolicitudCtaCte> listEstSol =  solCtaCte.getListaEstSolCtaCte();
	        
	        for (EstadoSolicitudCtaCte estadoSolicitudCtaCte : listEstSol) {
				
	        	if(estadoSolicitudCtaCte.getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE)){
	        		 setStrObservacion(estadoSolicitudCtaCte.getStrEsccObservacion());
	        		 break;
	        	}
			}
	       
	        
	        
	        
	        setBeanSolCtaCte(solCtaCte);
	        
	        
	        
	        //
	        
	        
	        
	        
		}catch (EJBFactoryException e) {
		  e.printStackTrace();
		  log.error(e);
		  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		} catch (BusinessException e) {
		  e.printStackTrace();
		  log.error(e);
		  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}catch(Exception e){
		  e.printStackTrace();
		  log.error(e);
		  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}
			
		cuentaCteFormRendered = true;
		cuentaCteRendered = true;
		intTipoRolDisabled = true;
		btnGrabarDisabled = false;
		
		
		
		
		if (getBeanSolCtaCte().getListaEstSolCtaCte().size() > 1){
			btnGrabarDisabled = true;
			btnQuitarArchivoDisabled = true;
			txtObservacionDisabled = true;
			cboTipoModalidadDisabled = true;
			cboMesDisabled = true;
			cboAnioDisabled = true;
			chkTipoSolicitudDisabled = true;
			cboAprobarRechazarDisabled = true;
		}else{
			btnGrabarDisabled = false;
			btnQuitarArchivoDisabled = false;
			txtObservacionDisabled = false;
			cboTipoModalidadDisabled = false;
			cboMesDisabled = false;
			cboAnioDisabled = false;
			chkTipoSolicitudDisabled = false;
			cboAprobarRechazarDisabled = false;
		}

		deshabilitaCondicionFinal = Boolean.FALSE;
		
   }
	
   
		
   public List<SolicitudCtaCteTipo> getListaTipoSol(){
	   
	   List<SolicitudCtaCteTipo> listaTipoSol = new ArrayList<SolicitudCtaCteTipo>();
	    if (listaTipoSolicitud != null)
		for (Tabla tipoSol:listaTipoSolicitud){
			if (tipoSol.getChecked() != null && tipoSol.getChecked()){
				SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo(); 
				solCtaCteTipo.getId().setIntTipoSolicitudctacte(tipoSol.getIntIdDetalle());
				solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				solCtaCteTipo.setStrScctObservacion(getStrObservacion());
				listaTipoSol.add(solCtaCteTipo);
			}
			
		}
		
	    
	  return listaTipoSol;
   }
	
	
	public void inicializarFormSolicitudCtaCte(){
		log.info("-----------------------Debugging enlaceReciboController.inicializarFormEnlaceRecibo-----------------------------");
		
		 setBeanSolCtaCte(new SolicitudCtaCte());
		 strFechSocioComp ="";
		 strObservacion = "";
		 archivoDocSolicitud = null;
	}
	
	
	public void quitarDocumento(){
		try{
		   	
			if (getBeanSolCtaCte().getId().getIntCcobItemsolctacte() != null && archivoDocSolicitudTemp == null){
			    archivoDocSolicitudTemp = archivoDocSolicitud;
			}
			
			archivoDocSolicitud = null;
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			                     fileUploadController.setArchivoDocCobranza(null);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}
	
	 public void aceptarAdjuntarDocumento(){
			try{
				FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
					archivoDocSolicitud = fileUploadController.getArchivoDocCobranza();
				fileContrato = archivoDocSolicitud;
				Archivo archivo = socioCom.getPersona().getNatural().getPerLaboral().getContrato();
				log.info("Archivo Contrato:"+archivo);
				grabarArchvioDirectorio(fileContrato); 
			}catch(Exception e){
				log.error(e.getMessage(),e);
			}
	 }
	 
	 public void grabarArchvioDirectorio(Archivo archivo) throws BusinessException{
		try {
	 		  InputStream in = new FileInputStream(archivo.getFile());
				  OutputStream out = new FileOutputStream(archivo.getRutaActual());
				try {
					// Transfer bytes from in to out
					byte[] buf = new byte[1024*1024*50];//Máximo 50MB
					int len;
					while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
					}
				}catch(Exception e){
					e.printStackTrace();
					log.error(e.getMessage(),e);
				}finally {
					in.close();
					out.close();
				}
		   }catch(Exception e){
				e.printStackTrace();
				log.error(e.getMessage(),e);
		   }
	}			 
	 
	public Archivo grabarArchvioDocAdjunto(Archivo archivo) throws BusinessException,EJBFactoryException{
			
		Archivo archivoResult= null;
		
		  		  try{
							   GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
							   archivoResult = facade.grabarArchivo(archivo);
						
						    } catch (BusinessException e) {
								System.out.println("error: "+ e);
								throw e;
							} catch (EJBFactoryException e) {
								System.out.println("error: "+ e);
								throw new BusinessException(e);
					 }
				
					 archivo.setRutaActual(archivo.getTipoarchivo().getStrRuta()+"\\"+archivoResult.getStrNombrearchivo());
				     String strRuta      = archivo.getRutaAntigua();
				     String nuevoNombre	 = archivo.getRutaActual();	  
					 try{
							java.io.File oldFile = new java.io.File(strRuta);
							java.io.File newFile = new java.io.File(nuevoNombre);
							oldFile.renameTo(newFile);
						}catch(Exception e){
							System.out.println("El renombrado no se ha podido realizar: " + e);
							log.error(e.getMessage(),e);
							throw new BusinessException(e);
					}
					 
			 
			
				return archivoResult;
   } 

	 
   //Form Tipo de Solicitudes
	 
	public void irSolicitudCtaCteTipo(ActionEvent event){
		
	  SolicitudCtaCteTipo tipoSolicitud  =  (SolicitudCtaCteTipo)event.getComponent().getAttributes().get("item");
	   if (tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_LICENCIA)){
		   nuevoLicencia(tipoSolicitud);
	   }
	   else
	   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOGARANTE)){
		   nuevoCambioGarante(tipoSolicitud);
	   }
	   else
	   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOENTIDAD)){
		   nuevoCambioEntidad(tipoSolicitud);
	   }
	   else
	   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICION)){
		   nuevoCambionCondicion(tipoSolicitud);
	   }   
	   else
	   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICIONLABORAL)){
		   nuevoCambioCondicionLaboral(tipoSolicitud);
	   }   
	   else
	   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_DESCUENTO_INDEBIDO)){
		   nuevoDescuentoIndebido(tipoSolicitud);
	   }  
	   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_TRANSFERENCIA)){
		   nuevoTransferencia(tipoSolicitud);
	   }  
	}
	
	
	public void verSolicitudCtaCteTipo(ActionEvent event){
		
		  SolicitudCtaCteTipo tipoSolicitud  =  (SolicitudCtaCteTipo)event.getComponent().getAttributes().get("item");
		
		   if (tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_LICENCIA)){
			   verLicencia(tipoSolicitud);
		   }
		   else
		   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOGARANTE)){
			   verCambioGarante(tipoSolicitud);
		   }
		   else
		   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOENTIDAD)){
			   verCambioEntidad(tipoSolicitud);
		   }
		   else
		   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICION)){
			   verCambioCondicion(tipoSolicitud);
		  }
		   else
		   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICIONLABORAL)){
			   verCambioCondicionLaboral(tipoSolicitud);
		  }
		   else
		   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_DESCUENTO_INDEBIDO)){
			   verDescuentoIndebido(tipoSolicitud);
		   }
		   else
		   if(tipoSolicitud.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_TRANSFERENCIA)){
			   verTransferencia(tipoSolicitud);
		   }
	}
	
	private void nuevoCambioGarante(SolicitudCtaCteTipo tipoSolicitud){
		habilitarFormCambioGarante = true;
		messageValidation = "";
		beanSolCtaCteTipoSel = tipoSolicitud;
		Date fechaActual = new  Date();
		beanSolCtaCteTipoSel.setDtFechaDocumento(fechaActual);
		getBeanSolCtaCte().setSolCtaCteTipo(beanSolCtaCteTipoSel);
	    listaGarantePorCuentaSocio();
	}
	
	private void nuevoCambioCondicionLaboral(SolicitudCtaCteTipo tipoSolicitud){
		log.info("---------------------------Metodo:nuevoCambioCondicionLaboral------------------"); 
		messageValidation = "";
		habilitarFormCambioCondicionLaboral = true;
		intCondicionLaboralDisabled = true;
		messagePlanillaOrigen = "";
		messagePlanillaPrestamo = "";
		intCondicionLaboralDisabled = false;
		try{
			
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			SocioFacadeRemote    socioFacade     = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			CuentaFacadeRemote cuentaFacade     = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
		    
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tipoSolicitud.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			solCtaCteTipo.setDtFechaDocumento(new Date());
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			SocioPK socioPk = new SocioPK();
	        socioPk.setIntIdEmpresa(beanSolCtaCte.getIntPersEmpresa());
	        socioPk.setIntIdPersona(beanSolCtaCte.getIntPersPersona());
	        
	        socioCom =  socioFacade.getSocioNatural(socioPk);
			
	        CuentaId cuentaId = new  CuentaId();
	        cuentaId.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
	        cuentaId.setIntPersEmpresaPk(socioPk.getIntIdEmpresa());
	        
	        Cuenta cuenta =  cuentaFacade.getCuentaPorId(cuentaId);
	        socioCom.setCuenta(cuenta);
			
			
			if(socioCom.getPersona().getNatural().getPerLaboral() != null){
				socioCom.getPersona().getNatural().setPerLaboral(socioCom.getPersona().getNatural().getPerLaboral());
				loadListDetCondicionLaboral(socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral());
				intCondicionLaboral = socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral();
				intCondicionLaboralDet = socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboralDet();
				
			}
			
			if (socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO)){
				intCondicionLaboralDisabled = true;
			}
			
			beanSolCtaCteTipoSel = getBeanSolCtaCte().getSolCtaCteTipo();
			
			habilitarDatosLaborales = false;
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
		
	}
	
	
	
	
	private void verCambioCondicionLaboral(SolicitudCtaCteTipo tipoSolicitud){
		log.info("---------------------------Metodo:verCambioCondicionLaboral------------------"); 
		messageValidation = "";
		habilitarFormCambioCondicionLaboral = false;
		messagePlanillaOrigen = "";
		messagePlanillaPrestamo = "";
		intCondicionLaboralDisabled = false;
		habilitarDatosLaborales = true;
		fileContrato = null;
		try{
			PersonaFacadeRemote  personaFacade   = (PersonaFacadeRemote)  EJBFactory.getRemote(PersonaFacadeRemote.class);
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tipoSolicitud.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			solCtaCteTipo.setDtFechaDocumento(new Date());
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
		
			pe.com.tumi.persona.core.domain.Persona persona = personaFacade.getPersonaNaturalPorIdPersona(socioCom.getPersona().getIntIdPersona());
			socioCom.setPersona(persona);
				
			if(socioCom.getPersona().getNatural().getPerLaboral() != null){
				socioCom.getPersona().getNatural().setPerLaboral(socioCom.getPersona().getNatural().getPerLaboral());
				loadListDetCondicionLaboral(socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral());
				intCondicionLaboral = socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral();
				intCondicionLaboralDet = socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboralDet();
		
			}
			
			if (socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO)){
				intCondicionLaboralDisabled = true;
			}else{
				 GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
				 ArchivoId   id = new ArchivoId();
							 id.setIntParaTipoCod(socioCom.getPersona().getNatural().getPerLaboral().getIntTipoArchivoContrato());
							 id.setIntItemArchivo(socioCom.getPersona().getNatural().getPerLaboral().getIntItemArchivoContrato());
							 id.setIntItemHistorico(socioCom.getPersona().getNatural().getPerLaboral().getIntItemHistoricoContrato());
				 Archivo  archivoResult = facade.getArchivoPorPK(id);
				 socioCom.getPersona().getNatural().getPerLaboral().setContrato(archivoResult);
			}
			
			
			beanSolCtaCteTipoSel = getBeanSolCtaCte().getSolCtaCteTipo();
			
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
	}
	
	
	private void nuevoDescuentoIndebido(SolicitudCtaCteTipo tipoSolicitud){
		 log.info("---------------------------Metodo:nuevoDescuentoIndebido------------------"); 
		 messageValidation = "";
		List<CuentaConcepto> listaConcepto = null;
		habilitarFormDescuentoIndebido = true;
		messagePlanillaOrigen = "";
		messagePlanillaPrestamo = "";
			
		try{
			
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			
			SocioFacadeRemote      socioFacade           = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			PersonaFacadeRemote    personaFacade         = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade      = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tipoSolicitud.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			log.info("---------------------------Metodo:nuevoLicencia------------------"); 
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			solCtaCteTipo.setDtFechaDocumento(new Date());
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			//Obtiene Lista CuentaConcepto  del expediente de credito
			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
			
			Integer idEmpresa = socioCom.getSocio().getId().getIntIdEmpresa();
			Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
			
			
			List<SocioEstructura> listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(idPersona, idEmpresa);
			
			if (listaSocioEstructura != null){
				log.info("Lista Entidad:"+listaSocioEstructura.size());
				
				for (int i = 0; i < listaSocioEstructura.size(); i++) {
					
					SocioEstructura socioEstructura = (SocioEstructura)listaSocioEstructura.get(i);
					
					
					EstructuraId id = new EstructuraId();
					id.setIntCodigo(socioEstructura.getIntCodigo());
					id.setIntNivel(socioEstructura.getIntNivel());
					
					Estructura estructura = estructuraFacade.getEstructuraPorPk(id);
					
					log.info("ID Entidad:"+estructura); 
					Juridica juridica = personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					
					log.info("Nombre Entidad:"+juridica);
					log.info("Modalidad:"+socioEstructura.getIntTipoEstructura());
					
					socioEstructura.setStrEntidad(juridica.getStrRazonSocial());
					
					if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
						 getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraOrigen(socioEstructura);
						 List<DescuentoIndebido> listaDesctoIndebidoOrigen =descuentoIndebidoService.getListaDescuentoIndebido(socioCom, socioEstructura);
						 getBeanSolCtaCte().getSolCtaCteTipo().setListaDesctoIndebidoOrigen(listaDesctoIndebidoOrigen);
					}else{
						getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraPrestamo(socioEstructura);
						 List<DescuentoIndebido> listaDesctoIndebidoPrestamo =descuentoIndebidoService.getListaDescuentoIndebido(socioCom, socioEstructura);
						 getBeanSolCtaCte().getSolCtaCteTipo().setListaDesctoIndebidoPrestamo(listaDesctoIndebidoPrestamo);
					}
				}
				
			}
			
			
			beanSolCtaCteTipoSel = getBeanSolCtaCte().getSolCtaCteTipo();
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
	   
	}
	
	private void verTransferencia(SolicitudCtaCteTipo tipoSolicitud){
		 log.info("---------------------------Metodo:verTransferencia------------------"); 
		 messageValidation = "";
		List<CuentaConcepto> listaConcepto = null;
		habilitarFormTransferencia = false;
		messagePlanillaOrigen = "";
		messagePlanillaPrestamo = "";
			
		try{
			
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			
			SocioFacadeRemote      socioFacade           = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			PersonaFacadeRemote    personaFacade         = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade      = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tipoSolicitud.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			log.info("---------------------------Metodo:verTransferencia------------------"); 
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			solCtaCteTipo.setDtFechaDocumento(new Date());
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			//Obtiene Lista CuentaConcepto  del expediente de credito
			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
			
			Integer idEmpresa = socioCom.getSocio().getId().getIntIdEmpresa();
			Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
			
			
			List<SocioEstructura> listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(idPersona, idEmpresa);
			
			if (listaSocioEstructura != null){
				log.info("Lista Entidad:"+listaSocioEstructura.size());
				
				for (int i = 0; i < listaSocioEstructura.size(); i++) {
					
					SocioEstructura socioEstructura = (SocioEstructura)listaSocioEstructura.get(i);
					
					
					EstructuraId id = new EstructuraId();
					id.setIntCodigo(socioEstructura.getIntCodigo());
					id.setIntNivel(socioEstructura.getIntNivel());
					
					Estructura estructura = estructuraFacade.getEstructuraPorPk(id);
					
					log.info("ID Entidad:"+estructura); 
					Juridica juridica = personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					
					log.info("Nombre Entidad:"+juridica);
					log.info("Modalidad:"+socioEstructura.getIntTipoEstructura());
					
					socioEstructura.setStrEntidad(juridica.getStrRazonSocial());
					
					if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
						 getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraOrigen(socioEstructura);
					}else{
						getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraPrestamo(socioEstructura);
					}
				}
				
			}
			
			if (solCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_FALLECIMIENTO_SOCIO)){
				 verCasoFallecimientoSocio();
			}
			else
			if (solCtaCteTipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_SOCIO_NO_LABORA)){	 
				 verCasoSocioNoLabora();
			}
			
			
			beanSolCtaCteTipoSel = getBeanSolCtaCte().getSolCtaCteTipo();
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
	   
	}
	private void nuevoTransferencia(SolicitudCtaCteTipo tipoSolicitud){
		 log.info("---------------------------CuentaCteController.nuevoTransferencia------------------"); 
			messageValidation = "";
			List<CuentaConcepto> listaConcepto = null;
			habilitarFormTransferencia = true;
			messagePlanillaOrigen = "";
			messagePlanillaPrestamo = "";
			beanListaExpediente = null;
			bgMontoTransferencia = null;
			bgMontoSaldoTotalAbono = null; 
			bgMontoSaldoAportaciones = null;
			
			radioEntreConceptos = true;
			socioComGarante = null;
			strDescEntidadGarante = "";
		    esEditableMontoSaldoAbono = true;
			
		try{
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			SocioFacadeRemote      socioFacade           = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			PersonaFacadeRemote    personaFacade         = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade      = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tipoSolicitud.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			log.info("---------------------------Metodo:nuevoLicencia------------------"); 
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			solCtaCteTipo.setDtFechaDocumento(new Date());
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
			getBeanSolCtaCte().getSolCtaCteTipo().setIntMotivoSolicitud(0);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			//Obtiene Lista CuentaConcepto  del expediente de credito
			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
			
			Integer idEmpresa = socioCom.getSocio().getId().getIntIdEmpresa();
			Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
			
			
			List<SocioEstructura> listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(idPersona, idEmpresa);
			
			if (listaSocioEstructura != null){
				log.info("Lista Entidad:"+listaSocioEstructura.size());
				
				for (int i = 0; i < listaSocioEstructura.size(); i++) {
					
					SocioEstructura socioEstructura = (SocioEstructura)listaSocioEstructura.get(i);
					
					
					EstructuraId id = new EstructuraId();
					id.setIntCodigo(socioEstructura.getIntCodigo());
					id.setIntNivel(socioEstructura.getIntNivel());
					
					Estructura estructura = estructuraFacade.getEstructuraPorPk(id);
					
					log.info("ID Entidad:"+estructura); 
					Juridica juridica = personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					
					log.info("Nombre Entidad:"+juridica);
					log.info("Modalidad:"+socioEstructura.getIntTipoEstructura());
					
					socioEstructura.setStrEntidad(juridica.getStrRazonSocial());
					
					if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
						 getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraOrigen(socioEstructura);
					}else{
						getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraPrestamo(socioEstructura);
					}
				}
				
			}
			
			
			beanSolCtaCteTipoSel = getBeanSolCtaCte().getSolCtaCteTipo();
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
	   
	}
	
	private void verDescuentoIndebido(SolicitudCtaCteTipo tipoSolicitud){
		 log.info("---------------------------Metodo:verDescuentoIndebido------------------"); 
		 messageValidation = "";
		List<CuentaConcepto> listaConcepto = null;
		habilitarFormDescuentoIndebido = false;
		messagePlanillaOrigen = "";
		messagePlanillaPrestamo = "";
			
		try{
			
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			
			SocioFacadeRemote      socioFacade           = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			PersonaFacadeRemote    personaFacade         = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade      = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tipoSolicitud.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			solCtaCteTipo.setDtFechaDocumento(new Date());
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			//Obtiene Lista CuentaConcepto  del expediente de credito
			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
			
			Integer idEmpresa = socioCom.getSocio().getId().getIntIdEmpresa();
			Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
			
			
			List<SocioEstructura> listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(idPersona, idEmpresa);
			
			if (listaSocioEstructura != null){
				log.info("Lista Entidad:"+listaSocioEstructura.size());
				
				for (int i = 0; i < listaSocioEstructura.size(); i++) {
					
					SocioEstructura socioEstructura = (SocioEstructura)listaSocioEstructura.get(i);
					
					
					EstructuraId id = new EstructuraId();
					id.setIntCodigo(socioEstructura.getIntCodigo());
					id.setIntNivel(socioEstructura.getIntNivel());
					
					Estructura estructura = estructuraFacade.getEstructuraPorPk(id);
					
					log.info("ID Entidad:"+estructura); 
					Juridica juridica = personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					
					log.info("Nombre Entidad:"+juridica);
					log.info("Modalidad:"+socioEstructura.getIntTipoEstructura());
					
					socioEstructura.setStrEntidad(juridica.getStrRazonSocial());
					
					if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
						 getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraOrigen(socioEstructura);
						 List<DescuentoIndebido> listaDesctoIndebidoOrigen =descuentoIndebidoService.getListaDescuentoIndebido(socioCom, socioEstructura);
						 getBeanSolCtaCte().getSolCtaCteTipo().setListaDesctoIndebidoOrigen(listaDesctoIndebidoOrigen);
					}else{
						getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraPrestamo(socioEstructura);
						 List<DescuentoIndebido> listaDesctoIndebidoPrestamo =descuentoIndebidoService.getListaDescuentoIndebido(socioCom, socioEstructura);
						 getBeanSolCtaCte().getSolCtaCteTipo().setListaDesctoIndebidoPrestamo(listaDesctoIndebidoPrestamo);
					}
				}
				
			}
			
			
			beanSolCtaCteTipoSel = getBeanSolCtaCte().getSolCtaCteTipo();
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
	   
	}
	
	
	private void nuevoCambioEntidad(SolicitudCtaCteTipo tipoSolicitud){
		 log.info("---------------------------Metodo:nuevoLicencia------------------"); 
		 messageValidation = "";
		List<CuentaConcepto> listaConcepto = null;
		habilitarFormCambioEntidad = true;
		messagePlanillaOrigen = "";
		messagePlanillaPrestamo = "";
			
		try{
			
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			
			SocioFacadeRemote      socioFacade           = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			PersonaFacadeRemote    personaFacade         = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade      = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			PlanillaFacadeLocal planillaFacade      = (PlanillaFacadeLocal) EJBFactory.getLocal(PlanillaFacadeLocal.class);
			
			
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tipoSolicitud.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			log.info("---------------------------Metodo:nuevoLicencia------------------"); 
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			solCtaCteTipo.setDtFechaDocumento(new Date());
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			//Obtiene Lista CuentaConcepto  del expediente de credito
			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
			
			Integer idEmpresa = socioCom.getSocio().getId().getIntIdEmpresa();
			Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
			
			
			List<SocioEstructura> listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(idPersona, idEmpresa);
			List<SocioEstructura> listaSocioEstructuraPrestamo = new ArrayList<SocioEstructura>();
			SocioEstructura socioEstructuraOrigen = new SocioEstructura();;
			
			if (listaSocioEstructura != null){
				log.info("Lista Entidad:"+listaSocioEstructura.size());
				
				for (int i = 0; i < listaSocioEstructura.size(); i++) {
					
					SocioEstructura socioEstructura = (SocioEstructura)listaSocioEstructura.get(i);
					
					
					EstructuraId id = new EstructuraId();
					id.setIntCodigo(socioEstructura.getIntCodigo());
					id.setIntNivel(socioEstructura.getIntNivel());
					
					Estructura estructura = estructuraFacade.getEstructuraPorPk(id);
					
					log.info("ID Entidad:"+estructura); 
					Juridica juridica = personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					
					log.info("Nombre Entidad:"+juridica);
					log.info("Modalidad:"+socioEstructura.getIntTipoEstructura());
					
					socioEstructura.setStrEntidad(juridica.getStrRazonSocial());
					
					if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
						socioEstructuraOrigen = socioEstructura;
						
						Integer intEmpresa = socioEstructura.getId().getIntIdEmpresa();
						EstructuraId pk = new EstructuraId();
						pk.setIntCodigo(socioEstructura.getIntCodigo());
						pk.setIntNivel(socioEstructura.getIntNivel());
						Integer intTipoSocio      = socioEstructura.getIntTipoSocio();
						Integer intModalidad      = socioEstructura.getIntModalidad();
						Integer intTipoEstructura =  socioEstructura.getIntTipoEstructura();
						
						Integer intPeriodo = planillaFacade.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
						Integer intPeriodoEfecuado = planillaFacade.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstr(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
						
						String messageEfecuado   = "Ultima planilla efectuada: ";
						String messageNoEfecuado = "Ultima planilla efectuada:no tiene.";
						String messageNoEnviado  = "Ultima planilla enviada:no tiene.";
						
						
						if (intPeriodo != null){
							messagePlanillaOrigen ="Ultima planilla enviada:"+intPeriodo+".";
							
						}
						
						if (intPeriodoEfecuado != null){
							if (intPeriodo > intPeriodoEfecuado){
								messagePlanillaOrigen = messagePlanillaOrigen+" "+messageNoEfecuado;
							}else{
								messagePlanillaOrigen = messagePlanillaOrigen+" "+messageEfecuado+intPeriodoEfecuado+".";
							}
						}else{
							    messagePlanillaOrigen = messagePlanillaOrigen+" "+messageNoEfecuado;
						}
						
						if(intPeriodo == null){
							    messagePlanillaOrigen = messageNoEnviado;
						}
						
						 getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraOrigen(socioEstructura);
					}else{
						
						Integer intEmpresa = socioEstructura.getId().getIntIdEmpresa();
						EstructuraId pk = new EstructuraId();
						pk.setIntCodigo(socioEstructura.getIntCodigo());
						pk.setIntNivel(socioEstructura.getIntNivel());
						Integer intTipoSocio      = socioEstructura.getIntTipoSocio();
						Integer intModalidad      = socioEstructura.getIntModalidad();
						Integer intTipoEstructura = socioEstructura.getIntTipoEstructura();
						
						Integer intPeriodo = planillaFacade.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
						Integer intPeriodoEfecuado = planillaFacade.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstr(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
						
						String messageEfecuado   = "Ultima planilla efectuada: ";
						String messageNoEfecuado = "Ultima planilla efectuada:no tiene.";
						String messageNoEnviado  = "Ultima planilla enviada:no tiene.";
						
						
						if (intPeriodo != null){
							messagePlanillaPrestamo ="Ultima planilla enviada:"+intPeriodo+".";
							
						}
						
						if (intPeriodoEfecuado != null){
							if (intPeriodo > intPeriodoEfecuado){
								messagePlanillaPrestamo = messagePlanillaPrestamo+" "+messageNoEfecuado;
							}else{
								messagePlanillaPrestamo = messagePlanillaPrestamo+" "+messageEfecuado+intPeriodoEfecuado+".";
							}
						}else{
							    messagePlanillaPrestamo = messagePlanillaPrestamo+" "+messageNoEfecuado;
						}
						
						if(intPeriodo == null){
							    messagePlanillaPrestamo = messageNoEnviado;
						}
						
						
						listaSocioEstructuraPrestamo.add(socioEstructura);
						getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraPrestamo(socioEstructura);
					}
				}
				//Seteando lista de PerLaboral
				if(socioCom.getPersona().getNatural().getPerLaboral() != null){
					socioCom.getPersona().getNatural().setPerLaboral(socioCom.getPersona().getNatural().getPerLaboral());
					loadListDetCondicionLaboral(socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral());
				}
				
				
			}
			
			
			beanSolCtaCteTipoSel = getBeanSolCtaCte().getSolCtaCteTipo();
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
	   
	}
	
	
	private void verCambioGarante(SolicitudCtaCteTipo tipoSolicitud){
		habilitarFormCambioGarante = false;
		messageValidation = "";
		beanSolCtaCteTipoSel = tipoSolicitud;
		Date fechaActual = new  Date();
		beanSolCtaCteTipoSel.setDtFechaDocumento(fechaActual);
		getBeanSolCtaCte().setSolCtaCteTipo(beanSolCtaCteTipoSel);
	    listaGarantePorCuentaSocio();
	   
	}
	
	private void verCambioEntidad(SolicitudCtaCteTipo tipoSolicitud){
		 log.info("---------------------------Metodo:nuevoLicencia------------------"); 
		 messageValidation = "";
		List<CuentaConcepto> listaConcepto = null;
		habilitarFormCambioEntidad = false;
		messagePlanillaOrigen = "";
		messagePlanillaPrestamo = "";
			
		try{
			
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			
			SocioFacadeRemote      socioFacade           = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			PersonaFacadeRemote    personaFacade         = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade      = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			PlanillaFacadeLocal planillaFacade      = (PlanillaFacadeLocal) EJBFactory.getLocal(PlanillaFacadeLocal.class);
			
			
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tipoSolicitud.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tipoSolicitud.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			solCtaCteTipo.setDtFechaDocumento(new Date());
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			//Obtiene Lista CuentaConcepto  del expediente de credito
			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
			
			Integer idEmpresa = socioCom.getSocio().getId().getIntIdEmpresa();
			Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
			
			
			List<SocioEstructura> listaSocioEstructura = socioFacade.getListaSocioEstrucuraPorIdPersona(idPersona, idEmpresa);
			List<SocioEstructura> listaSocioEstructuraPrestamo = new ArrayList<SocioEstructura>();
			SocioEstructura socioEstructuraOrigen = new SocioEstructura();;
			
			if (listaSocioEstructura != null){
				log.info("Lista Entidad:"+listaSocioEstructura.size());
				
				for (int i = 0; i < listaSocioEstructura.size(); i++) {
					
					SocioEstructura socioEstructura = (SocioEstructura)listaSocioEstructura.get(i);
					
					
					EstructuraId id = new EstructuraId();
					id.setIntCodigo(socioEstructura.getIntCodigo());
					id.setIntNivel(socioEstructura.getIntNivel());
					
					Estructura estructura = estructuraFacade.getEstructuraPorPk(id);
					
					log.info("ID Entidad:"+estructura); 
					Juridica juridica = personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
					
					log.info("Nombre Entidad:"+juridica);
					log.info("Modalidad:"+socioEstructura.getIntTipoEstructura());
					
					socioEstructura.setStrEntidad(juridica.getStrRazonSocial());
					
					if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
						socioEstructuraOrigen = socioEstructura;
						
						Integer intEmpresa = socioEstructura.getId().getIntIdEmpresa();
						EstructuraId pk = new EstructuraId();
						pk.setIntCodigo(socioEstructura.getIntCodigo());
						pk.setIntNivel(socioEstructura.getIntNivel());
						Integer intTipoSocio      = socioEstructura.getIntTipoSocio();
						Integer intModalidad      = socioEstructura.getIntModalidad();
						Integer intTipoEstructura =  socioEstructura.getIntTipoEstructura();
						
						Integer intPeriodo = planillaFacade.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
						Integer intPeriodoEfecuado = planillaFacade.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstr(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
						
						String messageEfecuado   = "Ultima planilla efectuada: ";
						String messageNoEfecuado = "Ultima planilla efectuada:no tiene.";
						String messageNoEnviado = "Ultima planilla enviada:no tiene.";
						
						
						if (intPeriodo != null){
							messagePlanillaOrigen ="Ultima planilla enviada:"+intPeriodo+".";
							
						}
						
						if (intPeriodoEfecuado != null){
							if (intPeriodo > intPeriodoEfecuado){
								messagePlanillaOrigen = messagePlanillaOrigen+" "+messageNoEfecuado;
							}else{
								messagePlanillaOrigen = messagePlanillaOrigen+" "+messageEfecuado+intPeriodoEfecuado+".";
							}
						}else{
							    messagePlanillaOrigen = messagePlanillaOrigen+" "+messageNoEfecuado;
						}
						
						if(intPeriodo == null){
							    messagePlanillaOrigen = messageNoEnviado;
						}
						
						 getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraOrigen(socioEstructura);
					}else{
						
						Integer intEmpresa = socioEstructura.getId().getIntIdEmpresa();
						EstructuraId pk = new EstructuraId();
						pk.setIntCodigo(socioEstructura.getIntCodigo());
						pk.setIntNivel(socioEstructura.getIntNivel());
						Integer intTipoSocio      = socioEstructura.getIntTipoSocio();
						Integer intModalidad      = socioEstructura.getIntModalidad();
						Integer intTipoEstructura = socioEstructura.getIntTipoEstructura();
						
						Integer intPeriodo = planillaFacade.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
						Integer intPeriodoEfecuado = planillaFacade.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstr(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
						
						String messageEfecuado   = "Ultima planilla efectuada: ";
						String messageNoEfecuado = "Ultima planilla efectuada:no tiene.";
						String messageNoEnviado  = "Ultima planilla enviada:no tiene.";
						
						
						if (intPeriodo != null){
							messagePlanillaPrestamo ="Ultima planilla enviada:"+intPeriodo+".";
							
						}
						
						if (intPeriodoEfecuado != null){
							if (intPeriodo > intPeriodoEfecuado){
								messagePlanillaPrestamo = messagePlanillaPrestamo+" "+messageNoEfecuado;
							}else{
								messagePlanillaPrestamo = messagePlanillaPrestamo+" "+messageEfecuado+intPeriodoEfecuado+".";
							}
						}else{
							    messagePlanillaPrestamo = messagePlanillaPrestamo+" "+messageNoEfecuado;
						}
						
						if(intPeriodo == null){
							    messagePlanillaPrestamo = messageNoEnviado;
						}
						
						
						listaSocioEstructuraPrestamo.add(socioEstructura);
						getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraPrestamo(socioEstructura);
					}
				}
				
			}
			
			
			beanSolCtaCteTipoSel = getBeanSolCtaCte().getSolCtaCteTipo();
			
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
		
	   log.info("Tamanio :"+listaConcepto.size());
	   
	}
	
	private void nuevoLicencia(SolicitudCtaCteTipo tabla){
		
		 log.info("---------------------------Metodo:nuevoLicencia------------------"); 
		 messageValidation = "";
	 	 List<CuentaConcepto> listaConcepto = null;
		 habilitarFormLicencia = true;
		
		try{
			
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tabla.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tabla.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tabla.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			log.info("---------------------------Metodo:nuevoLicencia------------------"); 
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			//Obtiene Lista CuentaConcepto  del expediente de credito
			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
			
			
			
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
		
	}
	
	private void nuevoCambionCondicion(SolicitudCtaCteTipo tabla){
		
		 log.info("---------------------------Metodo:nuevoCambionCondicion------------------"); 
		 messageValidation = "";
		habilitarFormLicencia = true;
		dtFechaFinDisbled = false;
		beanSolCtaCte.getSolCtaCteTipo().setListaExpediente(null);
		beanSolCtaCte.setCuenta(socioCom.getCuenta());
  	    beanSolCtaCte.getCuenta().setListaConcepto(null);
		
		try{
			
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tabla.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			
			SolicitudCtaCteTipo solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			
			if (solCtaCteTipo == null){
				SolicitudCtaCteTipo solCtaCteTipoTemp = new SolicitudCtaCteTipo();
				solCtaCteTipoTemp.getId().setIntTipoSolicitudctacte(tabla.getId().getIntTipoSolicitudctacte());
				solCtaCteTipoTemp.setIntParaEstadoanalisis(tabla.getIntParaEstadoanalisis());
				solCtaCteTipoTemp.setStrScctObservacion("Generado desde del Analista");
				solCtaCteTipoTemp.setIntTaraEstado(1);
				solCtaCteTipo = solCtaCteTipoTemp;
			}
			
			CuentaId idCuenta = new CuentaId();
			
			log.info("---------------------------Metodo:nuevoCambionCondicion------------------"); 
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
//			
//			//Obtiene Lista CuentaConcepto  por Cuenta
//			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
//			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
//			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
//			
//			//Obtiene Lista CuentaConcepto  del expediente de credito
//			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
//			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
//			
			
			
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
	}
	
	
	private void verLicencia(SolicitudCtaCteTipo tabla){
		
		 log.info("---------------------------Metodo:nuevoLicencia------------------"); 
		 messageValidation = "";
		 habilitarFormLicencia = false;
		List<CuentaConcepto> listaConcepto = null;
		
		
		try{
			SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tabla.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			CuentaId idCuenta = new CuentaId();
			
			log.info("---------------------------Metodo:verLicencia------------------"); 
			
			log.info("IntCuenta      ::"+beanSolCtaCte.getIntCsocCuenta()); 
			log.info("IntPersEmpresa ::"+beanSolCtaCte.getIntPersEmpresa()); 
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			
			
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			
			
			
			
		   Date fechaInicio = null;
		   Date fechaFin = null;
		   
		   //Obtiene listaCuenta Concepto Seleccionado por cuenta
		   
		    Integer idEmpresa        = beanSolCtaCte.getIntPersEmpresa();
		    Integer idCuentau        = beanSolCtaCte.getIntCsocCuenta();
			Integer idTipoSolicitud  = solCtaCteTipo.getIntMotivoSolicitud();
			List<BloqueoCuenta> listaBloqCuenta =  cuentaFacade.getListaPorNroCuentaYMotivo(idEmpresa, idCuentau,idTipoSolicitud);
			
			for (int i = 0; i < listaConcepto.size(); i++) {
				CuentaConcepto cuentaConcepto = (CuentaConcepto)listaConcepto.get(i);
					for (BloqueoCuenta bloqueoCuenta : listaBloqCuenta) {
						if (bloqueoCuenta.getIntItemCuentaConcepto() != null && bloqueoCuenta.getIntItemCuentaConcepto().equals(cuentaConcepto.getId().getIntItemCuentaConcepto())){
							cuentaConcepto.setChecked(true);
							listaConcepto.set(i,cuentaConcepto);
							fechaInicio = bloqueoCuenta.getTsFechaInicio();
							fechaFin = bloqueoCuenta.getTsFechaFin();
							
						}
					}
			}
			
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			
			//Obtiene Lista CuentaConcepto  del expediente de credito
			List<CuentaConcepto> listaCtaCptoExpCredito = listaCtaCptoPorExpeCredito(beanSolCtaCte.getIntPersEmpresa(),beanSolCtaCte.getIntCsocCuenta());
			
			
			Integer intEmpresa = beanSolCtaCte.getIntPersEmpresa();
			Integer intCuenta  = beanSolCtaCte.getIntCsocCuenta();
			List<Expediente> listaExp = cuentaFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa, intCuenta);
			
		
					for (int j = 0; j < listaBloqCuenta.size(); j++) {
						   BloqueoCuenta bloqueoCuenta = (BloqueoCuenta)listaBloqCuenta.get(j);
						   Integer intTipoCreditoTemp = 0;
					       Integer intTipoCredito = 0;
						
						
							 for (Expediente expediente : listaExp) {
								if (expediente.getId().getIntItemExpediente().equals(bloqueoCuenta.getIntItemExpediente())&&
								    expediente.getId().getIntItemExpedienteDetalle().equals(bloqueoCuenta.getIntItemExpedienteDetalle())){
									intTipoCredito = expediente.getIntParaTipoCreditoCod();
									fechaInicio = bloqueoCuenta.getTsFechaInicio();
									fechaFin    = bloqueoCuenta.getTsFechaFin();
									if (intTipoCreditoTemp != intTipoCredito){
										break;
									}
								}
							}
						 
							for (int i = 0; i < listaCtaCptoExpCredito.size(); i++) {
							       CuentaConcepto cuentaConcepto = (CuentaConcepto)listaCtaCptoExpCredito.get(i);
									if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(intTipoCredito)){
										cuentaConcepto.setChecked(true);
										listaCtaCptoExpCredito.set(i, cuentaConcepto);
										intTipoCreditoTemp = intTipoCredito;
									}
							}
					}
			
				
		    getBeanSolCtaCte().getSolCtaCteTipo().setMovimiento(new Movimiento());
		    getBeanSolCtaCte().getSolCtaCteTipo().getMovimiento().setDtFechaInicio(fechaInicio);
		    getBeanSolCtaCte().getSolCtaCteTipo().getMovimiento().setDtFechaFin(fechaFin);
		    
			getBeanSolCtaCte().getSolCtaCteTipo().setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
		
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
		
	}
	
	
	private void verCambioCondicion(SolicitudCtaCteTipo tabla){
		
		 log.info("---------------------------Metodo:verCambioCondicion------------------"); 
		 messageValidation = "";
		 habilitarFormLicencia = false;
		List<CuentaConcepto> listaConcepto = null;
		
		
		try{
			SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
			
			CuentacteFacadeLocal cuentacteFacade = (CuentacteFacadeLocal) EJBFactory.getLocal(CuentacteFacadeLocal.class);
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intPersEmpresasolctacte = getBeanSolCtaCte().getId().getIntEmpresasolctacte();
			Integer intCcobItemsolctacte = getBeanSolCtaCte().getId().getIntCcobItemsolctacte();
			SolicitudCtaCteTipoId pId = new SolicitudCtaCteTipoId();
			pId.setIntTipoSolicitudctacte(tabla.getId().getIntTipoSolicitudctacte());
			pId.setIntPersEmpresasolctacte(intPersEmpresasolctacte);
			pId.setIntCcobItemsolctacte(intCcobItemsolctacte);
			solCtaCteTipo = (cuentacteFacade.getSolicitudCtaCteTipoPorPk(pId));
			CuentaId idCuenta = new CuentaId();
			
			idCuenta.setIntCuenta(beanSolCtaCte.getIntCsocCuenta());
			idCuenta.setIntPersEmpresaPk(beanSolCtaCte.getIntPersEmpresa());
			
			getBeanSolCtaCte().setSolCtaCteTipo(solCtaCteTipo);
			
			//Obtiene Lista CuentaConcepto  por Cuenta
			listaConcepto = cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			getBeanSolCtaCte().setCuenta(socioCom.getCuenta());
			
		    Date fechaInicio = null;
		    Date fechaFin = null;
		   
		   //Obtiene listaCuenta Concepto Seleccionado por cuenta
		   
		    Integer idEmpresa        = beanSolCtaCte.getIntPersEmpresa();
		    Integer idCuentau        = beanSolCtaCte.getIntCsocCuenta();
			Integer idTipoSolicitud  = solCtaCteTipo.getIntMotivoSolicitud();
			List<BloqueoCuenta> listaBloqCuenta =  cuentaFacade.getListaPorNroCuentaYMotivo(idEmpresa, idCuentau,idTipoSolicitud);
			
			for (int i = 0; i < listaConcepto.size(); i++) {
				CuentaConcepto cuentaConcepto = (CuentaConcepto)listaConcepto.get(i);
					for (BloqueoCuenta bloqueoCuenta : listaBloqCuenta) {
						if (bloqueoCuenta.getIntItemCuentaConcepto() != null && bloqueoCuenta.getIntItemCuentaConcepto().equals(cuentaConcepto.getId().getIntItemCuentaConcepto())){
							cuentaConcepto.setChecked(true);
							listaConcepto.set(i,cuentaConcepto);
							fechaInicio = bloqueoCuenta.getTsFechaInicio();
							fechaFin = bloqueoCuenta.getTsFechaFin();
							
						}
					}
			}
			
			getBeanSolCtaCte().getCuenta().setListaConcepto(listaConcepto);
			
			
			Integer intEmpresa = beanSolCtaCte.getIntPersEmpresa();
			Integer intCuenta  = beanSolCtaCte.getIntCsocCuenta();
			List<Expediente> listaExp = cuentaFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa, intCuenta);
			
			List<Expediente> listaExpTemp = new ArrayList<Expediente>();
			
					for (int j = 0; j < listaBloqCuenta.size(); j++) {
						   BloqueoCuenta bloqueoCuenta = (BloqueoCuenta)listaBloqCuenta.get(j);
						   Integer intTipoCreditoTemp = 0;
					       Integer intTipoCredito = 0;
						
						
							 for (Expediente expediente : listaExp) {
								if (expediente.getId().getIntItemExpediente().equals(bloqueoCuenta.getIntItemExpediente())&&
								    expediente.getId().getIntItemExpedienteDetalle().equals(bloqueoCuenta.getIntItemExpedienteDetalle())){
									intTipoCredito = expediente.getIntParaTipoCreditoCod();
									fechaInicio = bloqueoCuenta.getTsFechaInicio();
									fechaFin    = bloqueoCuenta.getTsFechaFin();
									listaExpTemp.add(expediente);
									if (intTipoCreditoTemp != intTipoCredito){
										break;
									}
								}
							}
						 
					}
			
				
		    getBeanSolCtaCte().getSolCtaCteTipo().setMovimiento(new Movimiento());
		    getBeanSolCtaCte().getSolCtaCteTipo().getMovimiento().setDtFechaInicio(fechaInicio);
		    getBeanSolCtaCte().getSolCtaCteTipo().getMovimiento().setDtFechaFin(fechaFin);
		    getBeanSolCtaCte().getSolCtaCteTipo().setListaExpediente(listaExpTemp);
		
			
		} catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		} catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
			  messageValidation = FacesContextUtil.MESSAGE_ERROR_ONSEARCH;
		}
		
	}
	
	public List<CuentaConcepto> listaCtaCptoPorExpeCredito(Integer intEmpresa,Integer intCuenta) throws EJBFactoryException,BusinessException{
		
		List<CuentaConcepto> listaCtaCpto = new ArrayList<CuentaConcepto>();
		
		ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);

				List<Expediente> listaExp = cuentaFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa, intCuenta);
				
		//Agrupar por codTipoCredito
		Set<Integer> codTipoCredito = new HashSet<Integer>();
		for (Expediente expediente : listaExp) {
			codTipoCredito.add(expediente.getIntParaTipoCreditoCod());
		}
		
		for (Iterator<Integer> iterator = codTipoCredito.iterator(); iterator.hasNext();) {
			Integer codTipoCreditoU = (Integer) iterator.next();
			CuentaConcepto ctaCpto = new CuentaConcepto();
			ctaCpto.setId(new CuentaConceptoId());
			ctaCpto.getId().setIntCuentaPk(intCuenta);
			ctaCpto.getId().setIntPersEmpresaPk(intEmpresa);
			ctaCpto.getId().setIntItemCuentaConcepto(codTipoCreditoU);
			listaCtaCpto.add(ctaCpto);
		}
		
		return listaCtaCpto;
	}
	
	public void grabarLicencia(ActionEvent event){	 
	     esGrabadoOk = false;
	     messageValidation = "";
	     boolean esSeleccionado = false;
	     boolean esSelCtaCptoExpCre = false;
	     
		 //Validaciones  
		 boolean esValido = true;	
		  Movimiento mov =   getBeanSolCtaCte().getSolCtaCteTipo().getMovimiento();
		  if (getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud() == 0)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe seleccionar el Motivo de licencia. ";
		
		  }
		  
		  if (mov.getDtFechaInicio() == null || mov.getDtFechaFin() == null)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe completar la Fecha de Inicio y La fecha Fin. ";
		  }
		  

		  if (mov.getDtFechaInicio() != null &&   mov.getDtFechaFin() != null)
		  {	
			  if(mov.getDtFechaFin().before(mov.getDtFechaInicio())){
				  esValido = false;
				  messageValidation = messageValidation+ "La fecha de Fin es Mayor a la de Inicio. ";
			  }
		  }
		  
		  List<CuentaConcepto> lista = getBeanSolCtaCte().getCuenta().getListaConcepto();
		  List<CuentaConcepto> listaCtaCptoExpCred = getBeanSolCtaCte().getSolCtaCteTipo().getListaCtaCptoExpCredito();
		  
		  
		  if ((lista == null || lista.size() == 0) && (listaCtaCptoExpCred == null || listaCtaCptoExpCred.size() == 0))
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe considerar al menos un concepto de Bloqueo." ;
		  }
		  
			  for (CuentaConcepto cuentaConcepto : listaCtaCptoExpCred) {
				
				  if (cuentaConcepto.getChecked() != null && cuentaConcepto.getChecked()){
					   esSelCtaCptoExpCre = true;
				  }
			   }
			  
			  for (CuentaConcepto cuentaConcepto : lista) {
					
				  if (cuentaConcepto.getChecked() != null && cuentaConcepto.getChecked()){
					   esSeleccionado = true;
				  }
			   }
		  
		   if (!esSelCtaCptoExpCre &&  !esSeleccionado)
		   {	  
				esValido = false;
				messageValidation =messageValidation+ "Debe considerar al menos un concepto de Bloqueo." ;
		   }
		  
		  if (!esValido){
			  return;
		  }
		  
		  //Grabar 
		  List<SolicitudCtaCteTipo> listaCtaCteTipo =    getBeanSolCtaCte().getListaSolCtaCteTipo();
		  
		  SolicitudCtaCteTipo solCtaCteTipo=  getBeanSolCtaCte().getSolCtaCteTipo();
		  for (int i = 0; i < listaCtaCteTipo.size();i++) {
			  SolicitudCtaCteTipo solicitudCtaCteTipo = (SolicitudCtaCteTipo)listaCtaCteTipo.get(i);
			  if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(solCtaCteTipo.getId().getIntTipoSolicitudctacte())) {
				  solCtaCteTipo.setChecked(true);
				  solCtaCteTipo.setCuenta(getBeanSolCtaCte().getCuenta());
				  solCtaCteTipo.getCuenta().setListaConcepto(getBeanSolCtaCte().getCuenta().getListaConcepto());
				  List<CuentaConcepto>  listaCtaCptoExpCredito = solCtaCteTipo.getListaCtaCptoExpCredito();
				  solCtaCteTipo.setListaCtaCptoExpCredito(listaCtaCptoExpCredito);
				  
				  solCtaCteTipo.setIntPersUsuario(SESION_IDUSUARIO);
				  solCtaCteTipo.setIntEmpresa(SESION_IDEMPRESA);
				  
				  listaCtaCteTipo.set(i, solCtaCteTipo);
				  break;
			  }
		  }
		  
		  setBtnGrabarDisabled(false);	
		  esGrabadoOk = true;
		  getBeanSolCtaCte().getSolCtaCteTipo().setStrDescripcion("OK");
	}
	
	
	public void grabarCambioCondicion(ActionEvent event){	 
	     esGrabadoOk = false;
	     messageValidation = "";
	     
		 //Validaciones  
		 boolean esValido = true;	
		  Movimiento mov =   getBeanSolCtaCte().getSolCtaCteTipo().getMovimiento();
		  if (getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud() == 0)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe seleccionar el Motivo de Cambio Condición. ";
		
		  }
		  
		  if (getBeanSolCtaCte().getSolCtaCteTipo().getIntParaCondicionCuentaFinal() == 0)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe seleccionar la Condición Final. ";
		
		  }
		  
		  if (getBeanSolCtaCte().getSolCtaCteTipo().getIntParaCondicionCuentaFinal().equals(Constante.PARAM_T_CONDICIONSOCIO_INA)){
			  if (mov.getDtFechaInicio() == null)
			  {	  
				esValido = false;
				messageValidation =messageValidation+ "Debe completar la Fecha de Inicio. ";
			  }
		  }
		  else{
			  if (mov.getDtFechaInicio() == null)
			  {	  
				esValido = false;
				messageValidation =messageValidation+ "Debe completar la Fecha de Inicio. ";
			  }
			  
	
			  if (mov.getDtFechaInicio() != null &&   mov.getDtFechaFin() != null)
			  {	
				  if(mov.getDtFechaFin().before(mov.getDtFechaInicio())){
					  esValido = false;
					  messageValidation = messageValidation+ "La fecha de Fin es Mayor a la de Inicio. ";
				  }
			  }
		  }
		  
		  List<CuentaConcepto> lista = getBeanSolCtaCte().getCuenta().getListaConcepto();
		  List<Expediente> listaCtaCptoExpCred = getBeanSolCtaCte().getSolCtaCteTipo().getListaExpediente();
		  
		  
		  if ((lista == null || lista.size() == 0) && (listaCtaCptoExpCred == null || listaCtaCptoExpCred.size() == 0))
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "No tiene conceptos a bloquear.." ;
		  }
		  
		  
		  if (!esValido){
			  return;
		  }
		  
		  //Grabar 
		  List<SolicitudCtaCteTipo> listaCtaCteTipo =    getBeanSolCtaCte().getListaSolCtaCteTipo();
		  
		  SolicitudCtaCteTipo solCtaCteTipo=  getBeanSolCtaCte().getSolCtaCteTipo();
		  for (int i = 0; i < listaCtaCteTipo.size();i++) {
			  SolicitudCtaCteTipo solicitudCtaCteTipo = (SolicitudCtaCteTipo)listaCtaCteTipo.get(i);
			  if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(solCtaCteTipo.getId().getIntTipoSolicitudctacte())) {
				  solCtaCteTipo.setCuenta(getBeanSolCtaCte().getCuenta());
				  solCtaCteTipo.getCuenta().setListaConcepto(getBeanSolCtaCte().getCuenta().getListaConcepto());
				  solCtaCteTipo.setIntPersUsuario(SESION_IDUSUARIO);
				  solCtaCteTipo.setIntEmpresa(SESION_IDEMPRESA);
				  solCtaCteTipo.setStrDescripcion("OK");
				  solCtaCteTipo.setChecked(true);
				  listaCtaCteTipo.set(i, solCtaCteTipo);
				  break;
			  }
		  }
		  
		  setBtnGrabarDisabled(false);	
		  esGrabadoOk = true;
		  getBeanSolCtaCte().getSolCtaCteTipo().setStrDescripcion("OK");
	}
	
	public void buscarGarante(ActionEvent event) {
		log.info("------------------------Debugging SolicitudPrestamoController.validarGarante------------------------");
		// Persona persona = null;
		SocioComp socioCompGarante = null;
		Integer nroMaxAsegurados = null;
		Credito beanCredito = null;
		Integer intItemCreditoGarantia;
		String msgTxtCondicionSocio = "";
		String msgTxtCondicionHabil = "";
		String msgTxtCondicionLaboralGarante = "";
		String msgTxtSituacionLaboralGarante = "";
		String msgTxtMaxNroGarantes = "";
		String msgTxtSituacionLaboralGaranteNinguno = "";
		String msgTxtCondicionLaboralGaranteNinguno = "";
		String msgTxtCondicionSocioNinguno = "";
		String msgTxtTipoGarantiaPersonal = "";
		String msgTxtSocioEstructuraOrigenGarante = "";
		String msgTxtSocioEstructuraGarante = "";
		String msgTxtRolSocio = "";
		String msgTxtCuentaGarante = "";
		String msgTxtCuentaGaranteVigente = "";
		String msgTxtPersonaNoExiste = "";
		
		boolean blnDatosGarante = true;
		boolean blnValidaDatosGarante = true;
		
		SocioComp beanSocioCompGarante;
		
		
		esValidoGarante = true;
		Integer intNroPersGarantizadas ;
		
		messageValidation ="";
		//Parametro
		String strNroDocumento = getTxtDniGarante();
	
		try {
		
			CreditoFacadeRemote  creditoFacade = (CreditoFacadeRemote) EJBFactory.getRemote(CreditoFacadeRemote.class);
			CreditoGarantiaFacadeRemote creditoGarantiaFacade = (CreditoGarantiaFacadeRemote) EJBFactory.getRemote(CreditoGarantiaFacadeRemote.class);
			SolicitudPrestamoFacadeRemote solicitudPrestamoFacade = (SolicitudPrestamoFacadeRemote) EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			
			
			Credito credito = new Credito();
			credito.setId(new CreditoId());
			
			CondicionCredito condicionCredito = new CondicionCredito();
			condicionCredito.setId(new CondicionCreditoId());
			credito.setCondicionCredito(condicionCredito);
			
			
			GarantiaCredito garatiaCredito = beanGarantiaCreditoSel.getGarantiaCredito();
			
			List<CreditoComp> listaCrdito = creditoFacade.getListaCreditoCompDeBusquedaCredito(credito);
			
			List<CreditoGarantia> listaCreditoGarantia = new ArrayList<CreditoGarantia>();
			Credito beanCreditoAux = null;
			
			for (CreditoComp creditoComp : listaCrdito) {
				
				beanCredito = creditoFacade.getCreditoPorIdCredito(creditoComp.getCredito().getId());
				 
			   	if (beanCredito.getListaGarantiaPersonal()!= null && beanCredito.getListaGarantiaPersonal().size()> 0) {
			   		
			   		for (CreditoGarantia garantiaPersonal : beanCredito.getListaGarantiaPersonal()) {
						if (garantiaPersonal.getId().getIntItemCreditoGarantia().equals(garatiaCredito.getIntItemCreditoGarantia())&&
							garantiaPersonal.getId().getIntParaTipoGarantiaCod().equals(garatiaCredito.getIntParaTipoGarantiaCod())	) {
							listaCreditoGarantia =  beanCredito.getListaGarantiaPersonal();
							beanCreditoAux = beanCredito;
							beanCreditoAux.setListaGarantiaPersonal(listaCreditoGarantia);
							beanCredito.setListaGarantiaPersonal(listaCreditoGarantia);
							break;
						}
			   		}	
			   	}else{
			   		beanCredito = null;
			   	}
			   	    
				
			}
			
			beanCreditoAux.setListaGarantiaPersonal(listaCreditoGarantia);
			
			SocioFacadeRemote  socioFacade = (SocioFacadeRemote) EJBFactory.getRemote(SocioFacadeRemote.class);
			socioCompGarante = socioFacade
					.getSocioNatuPorDocIdentidadYIdEmpresa(new Integer(
							Constante.PARAM_T_TIPODOCUMENTO_DNI),
							strNroDocumento, SESION_IDEMPRESA);
			
			if (socioCompGarante != null && socioCompGarante.getSocio() != null && beanCreditoAux != null) {
				if (socioCompGarante.getCuenta() != null) {
				  if (socioCompGarante.getCuenta().getIntSecuenciaCuenta().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)){
					if (socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol() != null) {
						for (PersonaRol personaRol : socioCompGarante.getPersona().getPersonaEmpresa().getListaPersonaRol()) {
							if (personaRol.getId().getIntParaRolPk().equals(Constante.PARAM_T_TIPOROL_SOCIO)) {
								if (socioCompGarante.getSocio().getListSocioEstructura() != null) {
									forEstructuraGarante: 
										for (SocioEstructura socioEstructura : socioCompGarante.getSocio().getListSocioEstructura()) {
										if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)) {
											socioCompGarante.getSocio().setSocioEstructura(socioEstructura);
											if (beanCreditoAux.getListaGarantiaPersonal() != null) {
												for (CreditoGarantia garantiaPersonal : beanCreditoAux.getListaGarantiaPersonal()) {
													if (garantiaPersonal.getId().getIntParaTipoGarantiaCod().equals(4)) {
														intItemCreditoGarantia = garantiaPersonal.getId().getIntItemCreditoGarantia();
														garantiaPersonal = creditoGarantiaFacade.getCreditoGarantiaPorIdCreditoGarantia(garantiaPersonal.getId());
														if (garantiaPersonal != null) {
															if (garantiaPersonal.getListaTipoGarantiaComp() != null) {
																for (CreditoTipoGarantiaComp creditoTipoGarantia : garantiaPersonal	.getListaTipoGarantiaComp()) {
																	if (creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionSocio() != null) {
																		for (CondicionSocioTipoGarantia condicionSocio : creditoTipoGarantia.getCreditoTipoGarantia().getListaCondicionSocio()) {
																			if (socioCompGarante
																					.getCuenta()
																					.getIntParaCondicionCuentaCod()
																					.equals(condicionSocio
																							.getId()
																							.getIntParaCondicionSocioCod())
																					&& condicionSocio
																							.getIntValor() == 1) {
																				msgTxtCondicionSocio = "";
																				if (creditoTipoGarantia
																						.getCreditoTipoGarantia()
																						.getListaTipoCondicion() != null) {
																					for (CondicionHabilTipoGarantia condicionHabil : creditoTipoGarantia
																							.getCreditoTipoGarantia()
																							.getListaTipoCondicion()) {
																						if (socioCompGarante
																								.getCuenta()
																								.getIntParaSubCondicionCuentaCod()
																								.equals(condicionHabil
																										.getId()
																										.getIntParaTipoHabilCod())
																								&& condicionHabil
																										.getIntValor() == 1) {
																							msgTxtCondicionHabil = "";
																							if (creditoTipoGarantia
																									.getCreditoTipoGarantia()
																									.getListaCondicionLaboral() != null) {
																								for (CondicionLaboralTipoGarantia condicionLaboral : creditoTipoGarantia
																										.getCreditoTipoGarantia()
																										.getListaCondicionLaboral()) {
																									if (socioCompGarante
																											.getPersona()
																											.getNatural()
																											.getPerLaboral() != null) {
																										if (socioCompGarante
																												.getPersona()
																												.getNatural()
																												.getPerLaboral()
																												.getIntCondicionLaboral()
																												.equals(condicionLaboral
																														.getId()
																														.getIntParaCondicionLaboralCod())
																												&& condicionLaboral
																														.getIntValor() == 1) {
																											msgTxtCondicionLaboralGarante = "";
																											// if(socioCompGarante.getSocio().getSocioEstructura()!=null){
																											if (creditoTipoGarantia
																													.getCreditoTipoGarantia()
																													.getListaSituacionLaboral() != null) {
																												for (SituacionLaboralTipoGarantia situacionLaboral : creditoTipoGarantia
																														.getCreditoTipoGarantia()
																														.getListaSituacionLaboral()) {
																													if (socioEstructura
																															.getIntTipoSocio()
																															.equals(situacionLaboral
																																	.getId()
																																	.getIntParaSituacionLaboralCod())
																															&& situacionLaboral
																																	.getIntValor() == 1) {
																														msgTxtSituacionLaboralGarante = "";
																														// Validando
																														// el
																														// máximo
																														// número
																														// de
																														// personas
																														// garantizadas
																														// nro
																														// de
																														// asegurados
																														// hasta
																														// el
																														// momento
																														// ---
																														// nroMaxAsegurados
																														nroMaxAsegurados = solicitudPrestamoFacade
																																.getCantidadPersonasAseguradasPorPkPersona(socioCompGarante
																																		.getPersona()
																																		.getIntIdPersona());
																														if (nroMaxAsegurados != null) {
																															intNroPersGarantizadas = nroMaxAsegurados;
																															if (creditoTipoGarantia
																																	.getCreditoTipoGarantia()
																																	.getIntNumeroMaximoGarantia() != null) {
																																if (nroMaxAsegurados
																																		.compareTo(creditoTipoGarantia
																																				.getCreditoTipoGarantia()
																																				.getIntNumeroMaximoGarantia()) <= 0) {
																																	msgTxtMaxNroGarantes = "";
																																	beanSocioCompGarante = socioCompGarante;
																																	blnDatosGarante = true;
																																	blnValidaDatosGarante = true;
																																	break forEstructuraGarante;
																																} else {
																																	msgTxtMaxNroGarantes = "La Persona elegida ya tiene aseguradas a "
																																			+ nroMaxAsegurados
																																			+ " personas. No se puede asegurar a otra.";
																																}
																															} else {
																																beanSocioCompGarante = socioCompGarante;
																																blnDatosGarante = true;
																																blnValidaDatosGarante = false;
																																msgTxtSituacionLaboralGarante = "";
																																break forEstructuraGarante;
																															}
																														}
																													} else {
																														msgTxtSituacionLaboralGarante = "Ninguna Situación Laboral coincide establecida en el crédito coincide con el de la persona.";
																													}
																												}
																												msgTxtSituacionLaboralGaranteNinguno = "";
																											} else {
																												msgTxtSituacionLaboralGaranteNinguno = "La persona elegida no tiene asociada una Condición Laboral.";
																											}
																										} else {
																											msgTxtCondicionLaboralGarante = "Ninguna Condición Laboral coincide con la condición establecida en la garantía del crédito.";
																										}
																										msgTxtCondicionLaboralGarante = "";
																									} else {
																										msgTxtCondicionLaboralGarante = "La Persona no tiene ninguna Condición Laboral.";
																									}
																								}
																								msgTxtCondicionLaboralGaranteNinguno = "";
																							} else {
																								msgTxtCondicionLaboralGaranteNinguno = "No se ha configurado una Condición Laboral en esta garantía.";
																							}
																						} else {
																							msgTxtCondicionHabil = "Ninguna Condición Hábil coincide con la condición establecida en la cuenta.";
																						}
																					}
																					msgTxtCondicionHabil = "";
																				} else {
																					msgTxtCondicionHabil = "No se ha configurado una Condición Hábil en esta garantía.";
																				}
																			} else {
																				msgTxtCondicionSocio = "Ninguna Condición de Socio coincide con la condición establecida en la cuenta.";
																			}
																		}
																		msgTxtCondicionSocioNinguno = "";
																	} else {
																		msgTxtCondicionSocioNinguno = "No se ha configurado la Condición de Socio en esta garantía.";
																	}
																}
															}
														}
														msgTxtTipoGarantiaPersonal = "";
													} else {
														msgTxtTipoGarantiaPersonal = "El crédito no tiene configurado un tipo de Garantía Personal.";
													}
												}
											}
											msgTxtSocioEstructuraOrigenGarante = "";
										} else {
											msgTxtSocioEstructuraOrigenGarante = "La Estructura del Socio no tiene un tipo Origen predefinido.";
										}
									}
								} else {
									msgTxtSocioEstructuraGarante = "La persona elegida no tiene una Estructura.";
								}
								msgTxtRolSocio = "";
								break;
							} else {
								msgTxtRolSocio = "El Garante solicitado no tiene un Rol Socio";
							}
						}
						msgTxtRolSocio = "";
					} else {
						msgTxtRolSocio = "La persona no posee ningún Rol de Usuario.";
					}
				 } else {
					msgTxtCuentaGaranteVigente = "La Persona no tiene una Cuenta Vigente.";
				 }
			   } else {
					msgTxtCuentaGarante = "La Persona no tiene una Cuenta creada.";
			  }		  
				msgTxtPersonaNoExiste = "";
			} else {
				msgTxtPersonaNoExiste = "El DNI ingresado no existe en la BD.";
				blnDatosGarante = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// log.error(e);
		}
		
	 
		log.info("blnDatosGarante:::::::::::"+blnDatosGarante);
		log.info("blnValidaDatosGarante:::::"+blnValidaDatosGarante);
		
		
			if(msgTxtCondicionSocio.length() > 0) 				 { messageValidation = msgTxtCondicionSocio; esValidoGarante = false; }
			if(msgTxtCondicionLaboralGarante.length() > 0)  	 { messageValidation = msgTxtCondicionLaboralGarante; esValidoGarante = false; }
			if(msgTxtSituacionLaboralGarante.length() > 0) 		 { messageValidation = msgTxtSituacionLaboralGarante; esValidoGarante = false; }
			if(msgTxtMaxNroGarantes.length() > 0)				 { messageValidation = msgTxtMaxNroGarantes; esValidoGarante = false; }
			if(msgTxtSituacionLaboralGaranteNinguno.length() > 0){ messageValidation = msgTxtSituacionLaboralGaranteNinguno; esValidoGarante = false; }
			if(msgTxtCondicionLaboralGaranteNinguno.length() > 0){ messageValidation = msgTxtCondicionLaboralGaranteNinguno; esValidoGarante = false; }
			if(msgTxtCondicionSocioNinguno.length() > 0) 		 { messageValidation = msgTxtCondicionSocioNinguno; esValidoGarante = false; }
			if(msgTxtTipoGarantiaPersonal.length() > 0) 		 { messageValidation = msgTxtTipoGarantiaPersonal;  esValidoGarante = false; }
			if(msgTxtSocioEstructuraOrigenGarante.length() > 0)  { messageValidation = msgTxtSocioEstructuraOrigenGarante; esValidoGarante = false; }
			if(msgTxtSocioEstructuraGarante.length() > 0)        { messageValidation = msgTxtSocioEstructuraGarante; esValidoGarante = false; }
			if(msgTxtRolSocio.length() > 0)                      { messageValidation = msgTxtRolSocio; esValidoGarante = false; }
			if(msgTxtCuentaGarante.length() > 0)                 { messageValidation = msgTxtCuentaGarante; esValidoGarante = false; }
			if(msgTxtPersonaNoExiste.length() > 0)               { messageValidation = msgTxtPersonaNoExiste; esValidoGarante = false; }
			if(msgTxtCuentaGaranteVigente.length() > 0)          { messageValidation = msgTxtCuentaGaranteVigente; esValidoGarante = false; }
			
			
			
			
		listGaranteComp = new ArrayList<SocioComp>();
		if (esValidoGarante)
		    listGaranteComp.add(socioCompGarante);
		
	}

	
	public void seleccionarGarante(ValueChangeEvent event){
		log.info("---------------------------------Debugging cuentaCteController.seleccionarGarante-----------------------------");
		
		try{
			
		PersonaFacadeRemote 	personaFacade   = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
		TablaFacadeRemote   	tablaFacade 	= (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		ConceptoFacadeRemote    conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
	    
		messageValidation = "";
		boolean esGaranteValido = false;
		String nroSocioComp = (String) event.getNewValue();
		log.info("nroSocioComp: "+nroSocioComp);
		log.info("nroExpediente: "+nroExpediente);
		
		SocioComp garanteComp = (SocioComp)listGaranteComp.get(Integer.valueOf(nroSocioComp));
		socioComGarante = garanteComp;
		
		
		
		 List<SocioEstructura>  lista =  garanteComp.getSocio().getListSocioEstructura();
	        for (SocioEstructura socioEstructura : lista) {
	        	
	        	EstructuraFacadeRemote estructuraFacade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
				EmpresaFacadeRemote empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				
				
				EstructuraId id = new EstructuraId();
				id.setIntCodigo(socioEstructura.getIntCodigo());
				id.setIntNivel(socioEstructura.getIntNivel());
				
				Estructura entidad =   estructuraFacade.getEstructuraPorPk(id);
				Juridica  juridica =   personaFacade.getJuridicaPorPK(entidad.getIntPersPersonaPk());
				
				
				Sucursal sucursall = new Sucursal();
				sucursall.getId().setIntIdSucursal(socioEstructura.getIntIdSucursalAdministra());
				sucursall.getId().setIntPersEmpresaPk(socioEstructura.getIntEmpresaSucAdministra());
				
				Sucursal sucursal = empresaFacade.getSucursalPorPK(sucursall);
				Juridica suc = personaFacade.getJuridicaPorPK(sucursal.getIntPersPersonaPk());
				
				Tabla tabla = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_NIVELENTIDAD), socioEstructura.getIntNivel());
				String nivel = tabla.getStrDescripcion();
				String desEntidad = juridica.getStrRazonSocial();
				String desSucursal = suc.getStrRazonSocial();
				
				Tabla tabla2 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_TIPOSOCIO), socioEstructura.getIntTipoSocio());
				String tipoSocio = tabla2.getStrDescripcion();
				
				Tabla tabla3 = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.parseInt(Constante.PARAM_T_MODALIDADPLANILLA), socioEstructura.getIntModalidad());
				String modalidad = tabla3.getStrDescripcion();
				
				if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
					strDescEntidadGarante = nivel +":   "+desEntidad;
					strDesSucursalGarante = desSucursal;
					strDesModaliadTipoSocioGarante = tipoSocio+"-"+modalidad;
				}else{
					strDescEntidadPres = nivel +":   "+desEntidad;
					strDesSucursalPres = desSucursal;
					strDesModaliadTipoSocioPres = tipoSocio+"-"+modalidad;
				}
				
				
			}
		
	        //Obtener el aporte del Socio;
	    	List<CuentaConcepto>  listaCuentaCpto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(garanteComp.getCuenta().getId());
			BigDecimal     montoSaldoAportaciones = new BigDecimal(0);
	        for (CuentaConcepto cuentaConcepto : listaCuentaCpto) {
				if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_APORTACIONES)){
					//Aportaciones
					montoSaldoAportaciones = cuentaConcepto.getBdSaldo();
				}
			}
	        
	        boolean existeAporteInstanciado = false;
	        
	        if (beanListaExpediente != null){
	            for (int i = 0; i < beanListaExpediente.size(); i++) {
		        	Expediente expediente = (Expediente)beanListaExpediente.get(i);
		        	if (expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equals("Aporte")){
		        		existeAporteInstanciado = true;
		        		bgMontoSaldoAportaciones = montoSaldoAportaciones;
		        		beanListaExpediente.set(i, expediente);
		        	}	
				}
	        }
		if (listarGarantiaCreditoComp != null){
			GarantiaCreditoComp garantiaCreditoComp = (GarantiaCreditoComp)listarGarantiaCreditoComp.get(nroExpediente);
			
			socioComGarante = garanteComp;
			
			//Validando Garante.
			for (GarantiaCreditoComp gCreditoComp : listarGarantiaCreditoComp) {
				Integer intIdGarante      = gCreditoComp.getSocioComp().getSocio().getId().getIntIdPersona();
				Integer intIdGaranteNuevo = garanteComp.getSocio().getId().getIntIdPersona();
				
				if (intIdGarante.equals(intIdGaranteNuevo)){
					messageValidation = "Garante asignado. Eliga nuevo Garante.";
					esGaranteValido = true;
				}
			}
			
			if (!esGaranteValido) {
				beanGarantiaCreditoSel = garantiaCreditoComp;
				garantiaCreditoComp.setChecked(true);
				garantiaCreditoComp.setGaranteComp(garanteComp);
				listarGarantiaCreditoComp.set(nroExpediente, garantiaCreditoComp);
			}
		}
		 
		
	}catch (EJBFactoryException e) {
		  e.printStackTrace();
		  log.error(e);
		  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		} catch (BusinessException e) {
		  e.printStackTrace();
		  log.error(e);
		  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}catch(Exception e){
		  e.printStackTrace();
		  log.error(e);
		  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}
	}
	
	public void eliminarGarante(ActionEvent event){
		Integer nroExpedientes = (Integer) event.getComponent().getAttributes().get("nroItem");
		nroExpediente = nroExpedientes;
		GarantiaCreditoComp garantiaCreditoComp = (GarantiaCreditoComp)listarGarantiaCreditoComp.get(nroExpediente);
		garantiaCreditoComp.setSocioComp(null);
		garantiaCreditoComp.setGaranteComp(null);
		garantiaCreditoComp.setChecked(true);
		listarGarantiaCreditoComp.set(nroExpediente, garantiaCreditoComp);
	}
    
	public void irCambiarGarante(ActionEvent event){
		log.info("---------------------------------Debugging cuentaCteController.seleccionarGarantiaCredito-----------------------------");
		Integer nroExpedientes = (Integer) event.getComponent().getAttributes().get("nroItem");
		nroExpediente = nroExpedientes;
		log.info("--NroExpediente:"+nroExpedientes);
        GarantiaCreditoComp garantiaCreditoComp = (GarantiaCreditoComp)listarGarantiaCreditoComp.get(nroExpediente);
        
		beanGarantiaCreditoSel = garantiaCreditoComp;
		
		
		
		
		
	}
	

	
	public void grabarCambioGarante(ActionEvent event){
		
		log.info("---------------------------------Debugging cuentaCteController.grabarCambioGarante-----------------------------");
		
		esGrabadoOk = false;
	     messageValidation = " ";
		 //Validaciones  
		 boolean esValido = true;	
		 boolean existeGaranteCambiarEliminar = false;	
		 
		 if (getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud() == 0)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe seleccionar el Motivo de Cambio de Garantia. ";
		
		  }
		  
		  if (getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento() == null)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe completar la Fecha.";
		  }
		  
		  List<GarantiaCreditoComp> lista = listarGarantiaCreditoComp;
		  for (GarantiaCreditoComp garantiaCreditoComp : lista) {
			  
			  if (garantiaCreditoComp.getChecked() != null && garantiaCreditoComp.getChecked()){
				  existeGaranteCambiarEliminar = true;
			  }
			
		   }
		  
		  if (!existeGaranteCambiarEliminar)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "No hay garantes a cambiar o eliminar." ;
			
		  }
		  
		  if (!esValido){
			  return;
		  }
		  
		  if  (esValido)  {
			  //Grabar 
			  List<SolicitudCtaCteTipo> listaCtaCteTipo =    getBeanSolCtaCte().getListaSolCtaCteTipo();
			  SolicitudCtaCteTipo solCtaCteTipo=  getBeanSolCtaCte().getSolCtaCteTipo();
			  for (int i = 0; i < listaCtaCteTipo.size();i++) {
				  SolicitudCtaCteTipo solicitudCtaCteTipo = (SolicitudCtaCteTipo)listaCtaCteTipo.get(i);
				  if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(beanSolCtaCteTipoSel.getId().getIntTipoSolicitudctacte())) {
					  solCtaCteTipo.setChecked(true);
					  solCtaCteTipo.setListaGarantiaCreditoComp(listarGarantiaCreditoComp);
					  solCtaCteTipo.setIntPersUsuario(SESION_IDUSUARIO);
					  listaCtaCteTipo.set(i, solCtaCteTipo);
					  break;
				  }
			  }
			  
			  setBtnGrabarDisabled(false);	
			  esGrabadoOk = true;
			  getBeanSolCtaCte().getSolCtaCteTipo().setStrDescripcion("OK");
		  }
		  
		
	
	}
	
  public void grabarCambioEntidad(ActionEvent event){
		
		log.info("---------------------------------Debugging cuentaCteController.grabarCambioEntidad-----------------------------");
		
		 esGrabadoOk = false;
	     messageValidation = " ";
	     
		 //Validaciones  
		 boolean esValido = true;	
		 boolean existeDatosModificar = false;	
		 
		 
		 if (getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud() == 0)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe seleccionar el Motivo de Cambio de Garantia. ";
		  }
		  
		 SocioEstructura estructuraPrestamoNueva =  getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraPrestamoNueva();
		  
		 
		 if (estructuraPrestamoNueva != null){
			  existeDatosModificar = true;
		  }
		 
		
		  SocioEstructura estructuraOrigenNueva =  getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigenNueva();
		  
		  if (!existeDatosModificar){
			  if (estructuraOrigenNueva != null){
				  existeDatosModificar = true;
			  }
		  }
		  
		  if (!existeDatosModificar)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "No hay entidad para cambiar" ;
		  }
		  
		  if (estructuraOrigenNueva != null && estructuraOrigenNueva.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)){
			  
			 if (!socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral().equals(Constante.PARAM_T_CONDICIONLABORAL_NOMBRADO)){
				 
				 esValido = false;
				 messageValidation =messageValidation+ "Camie el Condicion Laboral a Nombrado." ;
			 }
		  }
		  
		  if (!esValido){
			  return;
		  }
		  
		  if  (esValido)  {
			  //Grabar 
			  List<SolicitudCtaCteTipo> listaCtaCteTipo =    getBeanSolCtaCte().getListaSolCtaCteTipo();
			  SolicitudCtaCteTipo solCtaCteTipo=  getBeanSolCtaCte().getSolCtaCteTipo();
			  for (int i = 0; i < listaCtaCteTipo.size();i++) {
				  SolicitudCtaCteTipo solicitudCtaCteTipo = (SolicitudCtaCteTipo)listaCtaCteTipo.get(i);
				  if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(beanSolCtaCteTipoSel.getId().getIntTipoSolicitudctacte())) {
					  solCtaCteTipo.setChecked(true);
					  solCtaCteTipo.setListaGarantiaCreditoComp(listarGarantiaCreditoComp);
					  solCtaCteTipo.setIntPersUsuario(SESION_IDUSUARIO);
					  solCtaCteTipo.setSocioComp(socioCom);
					  solCtaCteTipo.setStrDescripcion("OK");
					  listaCtaCteTipo.set(i, solCtaCteTipo);
					  break;
				  }
			  }
			  
			  setBtnGrabarDisabled(false);	
			  esGrabadoOk = true;
			  getBeanSolCtaCte().getSolCtaCteTipo().setStrDescripcion("OK");
		  }
		  
		
	
	}
  
  
  public void grabarDescuentoIndebido(ActionEvent event){
		
		log.info("---------------------------------Debugging cuentaCteController.grabarDescuentoIndebido-----------------------------");
		
		 esGrabadoOk = false;
	     messageValidation = " ";
	     
		 //Validaciones  
		 boolean esValido = true;	
		 boolean existeDatosModificar = false;	
		 
		 
		 if (getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud() == 0)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe seleccionar el Motivo de Descuento Indebido. ";
		
		  }
		  
		 List<DescuentoIndebido> listaUno =  getBeanSolCtaCte().getSolCtaCteTipo().getListaDesctoIndebidoOrigen();
		 List<DescuentoIndebido> listaDos =  getBeanSolCtaCte().getSolCtaCteTipo().getListaDesctoIndebidoPrestamo();
		 
		 if ((listaUno == null || listaUno.size() == 0) && (listaDos == null || listaDos.size() == 0)){
			 messageValidation = messageValidation+ "No hay descuento indebido a generar." ;
			
		 }else{
			 
			 if (listaUno != null) 
			 for (DescuentoIndebido descuentoIndebido : listaUno) {
				  if (descuentoIndebido.getChecked() != null && descuentoIndebido.getChecked()){
					  existeDatosModificar = false;
					  break;
				  }
				  
			  } 
			
			 if (listaDos != null) 
			 for (DescuentoIndebido descuentoIndebido : listaDos) {
				  if (descuentoIndebido.getChecked() != null && descuentoIndebido.getChecked()){
					  existeDatosModificar = false;
					  break;
				  }
				  
			  } 
		 }
		 
		 
		  if (existeDatosModificar)
		  {	  
			esValido = false;
			messageValidation = messageValidation+ "No hay descuento indebido a generar." ;
		  }
		  
		 
		  
		  if (!esValido){
			  return;
		  }
		  
		  if  (esValido)  {
			  //Grabar 
			  List<SolicitudCtaCteTipo> listaCtaCteTipo =    getBeanSolCtaCte().getListaSolCtaCteTipo();
			  SolicitudCtaCteTipo solCtaCteTipo=  getBeanSolCtaCte().getSolCtaCteTipo();
			  for (int i = 0; i < listaCtaCteTipo.size();i++) {
				  SolicitudCtaCteTipo solicitudCtaCteTipo = (SolicitudCtaCteTipo)listaCtaCteTipo.get(i);
				  if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(beanSolCtaCteTipoSel.getId().getIntTipoSolicitudctacte())) {
					  solCtaCteTipo.setChecked(true);
					  solCtaCteTipo.setListaGarantiaCreditoComp(listarGarantiaCreditoComp);
					  solCtaCteTipo.setIntPersUsuario(SESION_IDUSUARIO);
					  solCtaCteTipo.setSocioComp(socioCom);
					  solCtaCteTipo.setStrDescripcion("OK");
					  listaCtaCteTipo.set(i, solCtaCteTipo);
					  break;
				  }
			  }
			  
			  setBtnGrabarDisabled(false);	
			  esGrabadoOk = true;
			  getBeanSolCtaCte().getSolCtaCteTipo().setStrDescripcion("OK");
		  }
		
	
	}
  
  public void grabarTransferencia(ActionEvent event){
		
		log.info("---------------------------------Debugging cuentaCteController.grabarTransferencia-----------------------------");
		
		 esGrabadoOk = false;
	     messageValidation = " ";
	     
		 //Validaciones  
		 boolean esValido = true;	
		 boolean existeDatosModificar = false;	
		 
		 
		 if (getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud() == 0)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe seleccionar el Tipo de Transferencia.";
		
		  }
		  
		 
		 if (beanListaExpediente == null){
			 esValido = false;
				messageValidation =messageValidation+ "No hay expedientes para realizar la transferencia.";
			
		 }
		 if (getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA) || 
				 getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_SOCIO_NO_LABORA))
		 {
			 BigDecimal sumaMontoAbonoS = new BigDecimal(0);
			 for (Expediente expediente : beanListaExpediente) {
				  if (expediente.getBdMontoAbono() != null){
					  sumaMontoAbonoS = sumaMontoAbonoS.add(expediente.getBdMontoAbono());
				  }
			  }
			 
			 if (esValido && sumaMontoAbonoS.compareTo(new BigDecimal(0)) == 0){
				  esValido = false;
				  messageValidation ="El monto de transferencia esta en cero.";
		     }
		 
			  if (esEditableMontoSaldoAbono == false){
				  BigDecimal sumaMontoAbono = new BigDecimal(0);
				  BigDecimal mtoTotalSaldoCreidito = new BigDecimal(0);
				  
				  BigDecimal montoAporte = new BigDecimal(0);
				  
				  for (Expediente expediente : beanListaExpediente) {
					  if (expediente.getBdMontoAbono() != null){
						  if (expediente.getBdMontoAbono().compareTo(expediente.getBdSaldoCreditoSoles()) == 1){
							 esValido = false;
							 messageValidation ="El Monto Abono("+expediente.getBdMontoAbono()+") no debe ser superior al  Monto Saldo de Crédito("+expediente.getBdSaldoCreditoSoles()+").";
							 break;
						  }
						  sumaMontoAbono = sumaMontoAbono.add(expediente.getBdMontoAbono());
						  mtoTotalSaldoCreidito = mtoTotalSaldoCreidito.add(expediente.getBdSaldoCreditoSoles());
						  
					  }
					  
					  if (expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equalsIgnoreCase("Aporte")){
						  montoAporte = bgMontoSaldoAportaciones;
					  }
					  
				  }
				  
				  if (esValido &&(montoAporte == null || montoAporte.equals(new BigDecimal(0)))){
					  esValido = false;
					  messageValidation ="No hay Saldo en el Monto Aporte.";
				  }
				  else
				  if (esValido && sumaMontoAbono.compareTo(new BigDecimal(0)) == 0){
						  esValido = false;
						  messageValidation ="Debe ingresar el Monto Abono.";
				  }
				  else
				  if (esValido && sumaMontoAbono.compareTo(montoAporte) == 1){
					  esValido = false;
					  messageValidation ="El total del Monto Abono("+sumaMontoAbono+") no debe ser superior al Monto Saldo Aporte("+montoAporte+").";
				  }
				  
				 
			  }else{
				  
				  BigDecimal sumaMontoAbono = new BigDecimal(0);
				  BigDecimal mtoTotalSaldoCreidito = new BigDecimal(0);
				  
				  BigDecimal montoAporte = new BigDecimal(0);
				  
				  for (Expediente expediente : beanListaExpediente) {
					  if (expediente.getBdMontoAbono() != null){
						  sumaMontoAbono = sumaMontoAbono.add(expediente.getBdMontoAbono());
						  mtoTotalSaldoCreidito = mtoTotalSaldoCreidito.add(expediente.getBdSaldoCreditoSoles());
					  }
					  
					  if (expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equalsIgnoreCase("Aporte")){
						  montoAporte = bgMontoSaldoAportaciones;
					  }
				  }
				  
				  /*if (mtoTotalSaldoCreidito.compareTo(sumaMontoAbono) == 1 && montoAporte.compareTo(sumaMontoAbono) == 0){
					  
					  SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo(); 
			    	    solCtaCteTipo.setStrDescripcion("OK");
			    	    solCtaCteTipo.setChecked(true);
			    	if (!socioCom.getCuenta().getIntParaCondicionCuentaCod().equals(Constante.PARAM_T_CONDICIONSOCIO_CMO)){
				    	//Se genera el cambio de condicion cuenta a moroso.
				    	EstadoSolicitudCtaCte estSolCtaCte = new EstadoSolicitudCtaCte();
						estSolCtaCte.setDtEsccFechaEstado(new Date());
						estSolCtaCte.setIntParaEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE);
						estSolCtaCte.setIntSucuIduSusucursal(SESION_IDSUCURSAL);
						estSolCtaCte.setIntSudeIduSusubsucursal(SESION_IDSUBCURSAL);
						estSolCtaCte.setIntPersUsuarioEstado(SESION_IDUSUARIO);
						estSolCtaCte.setIntPersEmpresaEstado(SESION_IDEMPRESA);
						estSolCtaCte.setStrEsccObservacion("Cambio de condicion Socio.");
						
						beanSolCtaCte.setEstSolCtaCte(estSolCtaCte);
						
						solCtaCteTipo.getId().setIntTipoSolicitudctacte(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICION);
						solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
						solCtaCteTipo.setStrScctObservacion("Licencia");//cambiocobranzasolctactetiposaliasocionolabora10042013
						solCtaCteTipo.setDtFechaDocumento(new Date());//no pintaba1042013
						solCtaCteTipo.setCuenta(socioCom.getCuenta());
						List<CuentaConcepto> listaConcepto = new ArrayList();
						CuentaConcepto cuentaConcepto = new CuentaConcepto();
						
						CuentaConceptoId id = new CuentaConceptoId();
						id.setIntCuentaPk(socioCom.getCuenta().getId().getIntCuenta());
						id.setIntPersEmpresaPk(socioCom.getCuenta().getId().getIntPersEmpresaPk());
						id.setIntItemCuentaConcepto(Constante.CAPTACION_APORTACIONES);
						cuentaConcepto.setId(id);
						listaConcepto.add(cuentaConcepto);
						solCtaCteTipo.setIntParaCondicionCuentaFinal(Constante.PARAM_T_CONDICIONSOCIO_CMO);
						solCtaCteTipo.getCuenta().setListaConcepto(listaConcepto);
						Movimiento mov =   new Movimiento();
						mov.setIntPersEmpresa(SESION_IDEMPRESA);
						mov.setIntPersEmpresaUsuario(SESION_IDUSUARIO);
						mov.setIntPersEmpresa(socioCom.getCuenta().getId().getIntPersEmpresaPk());
						mov.setDtFechaInicio(UtilCobranza.obtieneFechaActualEnTimesTamp());
						mov.setDtFechaFin(null);
						solCtaCteTipo.setMovimiento(mov);
						solCtaCteTipo.setIntMotivoSolicitud(Constante.PARAM_T_TIPOMOTIVOSOLICITUD_CESELABORAL);
						solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOANALISIS_APROBAR);
						//beanSolCtaCte.getListaSolCtaCteTipo().clear();
						beanSolCtaCte.getListaSolCtaCteTipo().add(solCtaCteTipo);
						//cuentacteFacadeRemote.grabarSolicitudCtaCteAntedido(beanSolCtaCte);
						//beanSolCtaCte.getListaSolCtaCteTipo().clear();
						//getBeanSolCtaCte().getListaSolCtaCteTipo().add(beanSolCtaCte.getSolCtaCteTipo());

			    	}	
						
					  messageValidation = "El socio tiene aporte cero, pasara a la Condición Morosa.";
				  }*/
			  }
		  }
		 
		  if (!esValido){
			  return;
		  }
		  if  (esValido)  {
			  //Grabar 
			  List<SolicitudCtaCteTipo> listaCtaCteTipo =    getBeanSolCtaCte().getListaSolCtaCteTipo();
			  SolicitudCtaCteTipo solCtaCteTipo=  getBeanSolCtaCte().getSolCtaCteTipo();
			  for (int i = 0; i < listaCtaCteTipo.size();i++) {
				  SolicitudCtaCteTipo solicitudCtaCteTipo = (SolicitudCtaCteTipo)listaCtaCteTipo.get(i);
				  if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(beanSolCtaCteTipoSel.getId().getIntTipoSolicitudctacte())) {
					  solCtaCteTipo.setChecked(true);
					  solCtaCteTipo.setListaExpediente(beanListaExpediente);
					  solCtaCteTipo.setIntEmpresa(SESION_IDEMPRESA);
					  solCtaCteTipo.setIntPersUsuario(SESION_IDUSUARIO);
					  solCtaCteTipo.setIdSucursalUsuario(SESION_IDSUCURSAL);
					  solCtaCteTipo.setIdSubSucursalUsuario(SESION_IDSUBCURSAL);
					  solCtaCteTipo.setSocioComp(socioCom);
					  solCtaCteTipo.setSocioCompGarante(socioComGarante);
					  solCtaCteTipo.setStrDescripcion("OK");
					  listaCtaCteTipo.set(i, solCtaCteTipo);
				  }
				  else
				  if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICION)){
					  solCtaCteTipo = (SolicitudCtaCteTipo)listaCtaCteTipo.get(i);
					  solCtaCteTipo.setChecked(true);
					  solCtaCteTipo.setIntEmpresa(SESION_IDEMPRESA);
					  solCtaCteTipo.setIntPersUsuario(SESION_IDUSUARIO);
					  solCtaCteTipo.setIdSucursalUsuario(SESION_IDSUCURSAL);
					  solCtaCteTipo.setIdSubSucursalUsuario(SESION_IDSUBCURSAL);
					  solCtaCteTipo.setSocioComp(socioCom);
					  solCtaCteTipo.setSocioCompGarante(socioComGarante);
					  solCtaCteTipo.setStrDescripcion("OK");
					  listaCtaCteTipo.set(i, solCtaCteTipo);
				  }
				  
			  }
			  setBtnGrabarDisabled(false);	
			  esGrabadoOk = true;
			  getBeanSolCtaCte().getSolCtaCteTipo().setStrDescripcion("OK");
		  }
		  
		
	
	}

  
  public void grabarCambioCondicionLaboral(ActionEvent event){
		
		log.info("---------------------------------Debugging cuentaCteController.grabarCambioCondicionLaboral-----------------------------");
		
		 esGrabadoOk = false;
	     messageValidation = " ";
	     
		 //Validaciones  
		 boolean esValido = true;	
		 
		 
		 if (getBeanSolCtaCte().getSolCtaCteTipo().getIntMotivoSolicitud() == 0)
		  {	  
			esValido = false;
			messageValidation =messageValidation+ "Debe seleccionar el Motivo de Cambio de Condición Laboral. ";
		
		  }
		 
		 if (getIntCondicionLaboral().equals(socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral())&& 
		    		getIntCondicionLaboralDet().equals(socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboralDet())) {
		    		messageValidation = "Seleccione otra Condición Laboral";
		    		habilitarDatosLaborales = false;
		   }
		  
		 
		 
		  if (!esValido){
			  return;
		  }
		  
		  if  (esValido)  {
			  //Grabar 
			  List<SolicitudCtaCteTipo> listaCtaCteTipo =    getBeanSolCtaCte().getListaSolCtaCteTipo();
			  SolicitudCtaCteTipo solCtaCteTipo=  getBeanSolCtaCte().getSolCtaCteTipo();
			  for (int i = 0; i < listaCtaCteTipo.size();i++) {
				  SolicitudCtaCteTipo solicitudCtaCteTipo = (SolicitudCtaCteTipo)listaCtaCteTipo.get(i);
				  if (solicitudCtaCteTipo.getId().getIntTipoSolicitudctacte().equals(beanSolCtaCteTipoSel.getId().getIntTipoSolicitudctacte())) {
					  solCtaCteTipo.setChecked(true);
					  solCtaCteTipo.setIntPersUsuario(SESION_IDUSUARIO);
					  solCtaCteTipo.setSocioComp(socioCom);
					  solCtaCteTipo.setStrDescripcion("OK");
					  listaCtaCteTipo.set(i, solCtaCteTipo);
					  break;
				  }
			  }
			  
			  setBtnGrabarDisabled(false);	
			  esGrabadoOk = true;
			  getBeanSolCtaCte().getSolCtaCteTipo().setStrDescripcion("OK");
		  }
		  
		
	
	}
	
	public void agregarMovimiento(ActionEvent event){
		
		Integer intCboTipoSolicitudAux = getIntCboTipoSolicitudAux();
		
		SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo();
		solCtaCteTipo.getId().setIntTipoSolicitudctacte(intCboTipoSolicitudAux);
		solCtaCteTipo.setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOANALISIS_APROBAR);
	    getBeanSolCtaCte().getListaSolCtaCteTipo().add(solCtaCteTipo);
		
	}
	
 
	
    public void quitarMovimiento(ActionEvent event){
    	SolicitudCtaCteTipo tipoSolicitud  =  (SolicitudCtaCteTipo)event.getComponent().getAttributes().get("item");
    	getBeanSolCtaCte().getListaSolCtaCteTipo().remove(tipoSolicitud);
   	}
	
	public void verificarEnvioPlanilla(){
		habilitarFormAlerEnvioPlanilla = true;	 
		messagePlanillaOrigenNueva = "";
		messagePlanillaPrestamoNueva = "";
		
		SolicitudCtaCteTipo solCtaCteTipo = getBeanSolCtaCte().getSolCtaCteTipo();
		
		
		SocioEstructura socioEstOrigen       = null;
		SocioEstructura socioEstOrigenNueva  = null;
		
		
		try{	
			
			PlanillaFacadeLocal planillaFacade = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			
			if (getBeanTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
				socioEstOrigen      =  solCtaCteTipo.getSocioEstructuraOrigen();
				socioEstOrigenNueva =  solCtaCteTipo.getSocioEstructuraOrigenNueva();
			}else{
				socioEstOrigen      =  solCtaCteTipo.getSocioEstructuraPrestamo();
				socioEstOrigenNueva =  solCtaCteTipo.getSocioEstructuraPrestamoNueva();
			}
			
			
			if (socioEstOrigenNueva != null){
				
				 boolean existePlanillaEnviadaOrigen = false;
				 boolean existePlanillaEnviadaOrigenNueva = false;
				 
				 
				 EstructuraId pk = new EstructuraId();
				 Integer intEmpresa 		=  socioEstOrigen.getId().getIntIdEmpresa();
											   pk.setIntCodigo(socioEstOrigen.getIntCodigo());
										       pk.setIntNivel(socioEstOrigen.getIntNivel());
			     Integer intTipoSocio 		=  socioEstOrigen.getIntTipoSocio();
			     Integer intModalidad 		=  socioEstOrigen.getIntModalidad();
			     Integer intTipoEstructura  =  socioEstOrigen.getIntTipoEstructura();
			     
			     
			     Integer intPeriodoOrigen            =  planillaFacade.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
			     Integer intPeriodoOrigenEfectuado   =  planillaFacade.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstr(intEmpresa, pk, intTipoSocio, intModalidad, intTipoEstructura);
				 
			   //Verifica la existencia de planilla enviada actual.
			     if (intPeriodoOrigen == null)
			     {
			    	 existePlanillaEnviadaOrigen = false;
			     }else
			     if (intPeriodoOrigenEfectuado == null || intPeriodoOrigen > intPeriodoOrigenEfectuado){
			    	 existePlanillaEnviadaOrigen = true;
			     }
			     else{
			    	 existePlanillaEnviadaOrigen = false;
			     }
			    
			     EstructuraId pkNueva = new EstructuraId();
			     Integer intEmpresaNueva 		=  socioEstOrigenNueva.getId().getIntIdEmpresa();
				                                   pkNueva.setIntCodigo(socioEstOrigenNueva.getIntCodigo());
							                       pkNueva.setIntNivel(socioEstOrigenNueva.getIntNivel());
				Integer intTipoSocioNueva 		=  socioEstOrigenNueva.getIntTipoSocio();
				Integer intModalidadNueva 		=  socioEstOrigenNueva.getIntModalidad();
				Integer intTipoEstructuraNueva  =  socioEstOrigenNueva.getIntTipoEstructura();
				
				Integer intPeriodoOrigenNueva   =  planillaFacade.getMaxPeriodoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstru(intEmpresaNueva, pkNueva, intTipoSocioNueva, intModalidadNueva, intTipoEstructuraNueva);
				Integer intPeriodoOrigenEfectuadoNueva   =  planillaFacade.getMaxPeriodoEfectuadoPorEmpresaYEstructuraYTipoSocioYModalidadTipoEstr(intEmpresaNueva, pkNueva, intTipoSocioNueva, intModalidadNueva, intTipoEstructuraNueva);
				
				 
				 //Verifica la existencia de planilla enviada para la nueva entidad.
				 if (intPeriodoOrigenNueva == null){
					 existePlanillaEnviadaOrigenNueva = false;
				 }
				 else
			     if (intPeriodoOrigenEfectuadoNueva == null || intPeriodoOrigenNueva > intPeriodoOrigenEfectuadoNueva){
			    	 existePlanillaEnviadaOrigenNueva = true;
			     }
			     else{
			    	 existePlanillaEnviadaOrigenNueva = false;
			     }
			     
			     //Caso uno
			     if (existePlanillaEnviadaOrigen == true && existePlanillaEnviadaOrigenNueva == true){
			    	 strMessageAlertEnvioPlanilla     = "Existe la planilla enviada para la actual entidad y la nueva entidad.";
			    	 strPreguntaAlertaEnvioPlanilla   = "¿Desea cambiar al socio a la planilla enviada de la nueva entidad?";
			    	 strMensajeCambioEntidad          = "Cambio de entidad se realizó con éxito.";
			    	 habilitarBotonAlertEnvioPlanilla = true;
			    	 habilitarFormAlerEnvioPlanilla = true;
			    	 
			    	 if (getBeanTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
			    	     messagePlanillaOrigenNueva   = "Ultima Planilla Enviada:"+intPeriodoOrigenNueva+". Ultima planilla efectuada:No Tiene.";
			    	 }else{
			    	     messagePlanillaPrestamoNueva = "Ultima Planilla Enviada:"+intPeriodoOrigenNueva+". Ultima planilla efectuada:No Tiene.";
			    	 }
			    	 
			    	 getBeanSolCtaCte().getSolCtaCteTipo().setIntPeriodoEnvioPlanilla(intPeriodoOrigen);
			    	 getBeanSolCtaCte().getSolCtaCteTipo().setStrCasoEnvioPlanilla("CasoUno");
			     }
			     else
			     //Caso dos
			     if (existePlanillaEnviadaOrigen == false && existePlanillaEnviadaOrigenNueva == true){
			    	 strMessageAlertEnvioPlanilla     = "Planailla enviada para la actual entidad no existe.";
			    	 strPreguntaAlertaEnvioPlanilla   = "La planilla enviada se procederá a generar en la nueva entidad.";
			    	 strMensajeCambioEntidad          = "El envío de planilla se realizará en la nueva entidad.";
			    	 habilitarBotonAlertEnvioPlanilla = false;
			    	 habilitarFormAlerEnvioPlanilla = true;
			    	 if (getBeanTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
			    	    messagePlanillaOrigenNueva   = "Ultima Planilla Enviada:"+intPeriodoOrigenNueva+". Ultima planilla efectuada:No Tiene.";
			    	 }else{
			    	    messagePlanillaPrestamoNueva = "Ultima Planilla Enviada:"+intPeriodoOrigenNueva+". Ultima planilla efectuada:No Tiene.";
			    	 }  
			    	 getBeanSolCtaCte().getSolCtaCteTipo().setStrCasoEnvioPlanilla("CasoDos");
			    	 
			    	 
			     }
			     else
			     //Caso tres
			     if (existePlanillaEnviadaOrigen == true && existePlanillaEnviadaOrigenNueva == false){
			    	 strMessageAlertEnvioPlanilla     = "Existe la planilla enviada para la actual entidad.";
			    	 strPreguntaAlertaEnvioPlanilla   = "¿Desea cambiar la planilla enviada del socio a la nueva entidad?";
			    	 strMensajeCambioEntidad          = "Se mantiene envío de planilla o se elimina de la unidad actual.";
			    	 habilitarBotonAlertEnvioPlanilla = true;
			    	 habilitarFormAlerEnvioPlanilla = true;
			    	 if (getBeanTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
			    	     messagePlanillaOrigenNueva   = "Ultima Planilla Enviada:No tiene. Ultima planilla efectuada:No Tiene.";
			    	 }else{
			    		 messagePlanillaPrestamoNueva = "Ultima Planilla Enviada:No tiene. Ultima planilla efectuada:No Tiene."; 
			    	 }
			    	 getBeanSolCtaCte().getSolCtaCteTipo().setStrCasoEnvioPlanilla("CasoTres");
			     }
			     else
			     if(existePlanillaEnviadaOrigen == false && existePlanillaEnviadaOrigenNueva == false) {
			    	 if (getBeanTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
			    	    messagePlanillaOrigenNueva   = "Ultima Planilla Enviada:No tiene. Ultima planilla efectuada:No Tiene.";
			    	 }else{
			    		messagePlanillaPrestamoNueva = "Ultima Planilla Enviada:No tiene. Ultima planilla efectuada:No Tiene."; 
			    	 }
			        habilitarFormAlerEnvioPlanilla = false;	 
			     }
			     
			     
			     getBeanSolCtaCte().getSolCtaCteTipo().setIntPeriodoEnvioPlanilla(intPeriodoOrigen);
			}
			
			
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
			e.printStackTrace();
			log.error(e);
		} catch (EJBFactoryException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		    e.printStackTrace();
		    log.error(e);
		}catch (Exception e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		    e.printStackTrace();
		    log.error(e);
		}
	}
	
	
    public void cambiarEnvioPlanilla(ActionEvent event){
    	log.info("-------------------------------Metodo:cambiarEnvioPlanilla-----------------------");
    	String strSiNoEnvioPlanilla  =  (String)event.getComponent().getAttributes().get("item");
    	log.info("--strSiNoEnvioPlanilla:"+strSiNoEnvioPlanilla);
    	getBeanSolCtaCte().getSolCtaCteTipo().setStrSiNoEnvioPlanilla(strSiNoEnvioPlanilla);
   	}
	
    
    public void cambiarCondicionLaboral(ActionEvent event){
    	log.info("-------------------------------Metodo:cambiarCondicionLaboral-----------------------");
    	
    	messageValidation = "";
    	Integer intCondicionLaboral   =  (Integer)event.getComponent().getAttributes().get("pIntCondicionLaboral");//socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral();
    	Integer intCondicionLaboralDet = (Integer)event.getComponent().getAttributes().get("pIntCondicionLaboralDet");	//socioCom.getPersona().getNatural().getPerLaboral().getIntCondicionLaboralDet();
    	
    	if (getIntCondicionLaboral().equals(intCondicionLaboral)&& 
    		getIntCondicionLaboralDet().equals(intCondicionLaboralDet)) {
    		messageValidation = "Seleccione otra Condición Laboral";
    		habilitarDatosLaborales = false;
    	}
    	else{
    		
    		habilitarDatosLaborales = true;
    	}
    	
   	}
	
   public Object getSessionBean(String beanName) {
			HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
			return sesion.getAttribute(beanName);
		}

	public SolicitudCtaCte getBeanSolCtaCte() {
		return beanSolCtaCte;
	}

	public void setBeanSolCtaCte(SolicitudCtaCte beanSolCtaCte) {
		this.beanSolCtaCte = beanSolCtaCte;
	}

	public List<SolicitudCtaCte> getBeanListSolCtaCte() {
		return beanListSolCtaCte;
	}

	public void setBeanListSolCtaCte(List<SolicitudCtaCte> beanListSolCtaCte) {
		this.beanListSolCtaCte = beanListSolCtaCte;
	}

	public Integer getIntCboSucursal() {
		return intCboSucursal;
	}

	public void setIntCboSucursal(Integer intCboSucursal) {
		this.intCboSucursal = intCboSucursal;
	}

	
	public Integer getIntCboTipoSolicitud() {
		return intCboTipoSolicitud;
	}

	public void setIntCboTipoSolicitud(Integer intCboTipoSolicitud) {
		this.intCboTipoSolicitud = intCboTipoSolicitud;
	}

		public boolean isBolEnlaceRecibo() {
		return bolEnlaceRecibo;
	}

	public void setBolEnlaceRecibo(boolean bolEnlaceRecibo) {
		this.bolEnlaceRecibo = bolEnlaceRecibo;
	}

	public boolean isBtnGrabarDisabled() {
		return btnGrabarDisabled;
	}

	public void setBtnGrabarDisabled(boolean btnGrabarDisabled) {
		this.btnGrabarDisabled = btnGrabarDisabled;
	}

	public List<Sucursal> getListJuridicaSucursal() {
		
		log.info("-------------------------------------Debugging enlaceReciboController.getListJuridicaSucursal-------------------------------------");
		log.info("sesionIntIdEmpresa: "+SESION_IDEMPRESA);
		try {
			if(listJuridicaSucursal == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				this.listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		log.info("listJuridicaSucursal.size: "+listJuridicaSucursal.size());
		
		return listJuridicaSucursal;
	}

	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}

	public List<Subsucursal> getListJuridicaSubsucursal() {
		return listJuridicaSubsucursal;
	}

	public void setListJuridicaSubsucursal(List<Subsucursal> listJuridicaSubsucursal) {
		this.listJuridicaSubsucursal = listJuridicaSubsucursal;
	}

	public boolean iscuentaCteRendered() {
		return cuentaCteRendered;
	}

	public void setcuentaCteRendered(boolean cuentaCteRendered) {
		this.cuentaCteRendered = cuentaCteRendered;
	}

	public boolean isBtnValidarRendered() {
		return btnValidarRendered;
	}

	public void setBtnValidarRendered(boolean btnValidarRendered) {
		this.btnValidarRendered = btnValidarRendered;
	}

	public boolean isCboSucursalDisabled() {
		return cboSucursalDisabled;
	}

	public void setCboSucursalDisabled(boolean cboSucursalDisabled) {
		this.cboSucursalDisabled = cboSucursalDisabled;
	}

	public boolean isCboSubSucursalDisabled() {
		return cboSubSucursalDisabled;
	}

	public void setCboSubSucursalDisabled(boolean cboSubSucursalDisabled) {
		this.cboSubSucursalDisabled = cboSubSucursalDisabled;
	}

	public boolean isTexNumeroReciboDisabled() {
		return texNumeroReciboDisabled;
	}

	public void setTexNumeroReciboDisabled(boolean texNumeroReciboDisabled) {
		this.texNumeroReciboDisabled = texNumeroReciboDisabled;
	}

	public List<UsuarioSubSucursal> getListGestor() {
		return listGestor;
	}

	public void setListGestor(List<UsuarioSubSucursal> listGestor) {
		this.listGestor = listGestor;
	}

	public boolean isTexGestorRedered() {
		return texGestorRedered;
	}

	public void setTexGestorRedered(boolean texGestorRedered) {
		this.texGestorRedered = texGestorRedered;
	}

	public String getDescSocioComp() {
		return descSocioComp;
	}

	public void setDescSocioComp(String descSocioComp) {
		this.descSocioComp = descSocioComp;
	}


	public String getStrFechSocioComp() {
		return strFechSocioComp;
	}

	public Integer getIntCboEstadoSolicitud() {
		return intCboEstadoSolicitud;
	}

	public void setIntCboEstadoSolicitud(Integer intCboEstadoSolicitud) {
		this.intCboEstadoSolicitud = intCboEstadoSolicitud;
	}

	public String getStrInNroDniSocio() {
		return strInNroDniSocio;
	}

	public void setStrInNroDniSocio(String strInNroDniSocio) {
		this.strInNroDniSocio = strInNroDniSocio;
	}

	public List<Tabla> getListaTipoRol() {
   try{		
	   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
	   listaTipoRol =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL),"D");
	} catch (EJBFactoryException e) {
		e.printStackTrace();
	} catch (BusinessException e) {
		e.printStackTrace();
	}
		
		return listaTipoRol;
	}

	public void setListaTipoRol(List<Tabla> listaTipoRol) {
		this.listaTipoRol = listaTipoRol;
	}

	public String getTxtNombresSocio() {
		return txtNombresSocio;
	}

	public void setTxtNombresSocio(String txtNombresSocio) {
		this.txtNombresSocio = txtNombresSocio;
	}

	public String getTxtDniSocio() {
		return txtDniSocio;
	}

	public void setTxtDniSocio(String txtDniSocio) {
		this.txtDniSocio = txtDniSocio;
	}

	public String getCboTipo() {
		return cboTipo;
	}

	public void setCboTipo(String cboTipo) {
		this.cboTipo = cboTipo;
	}

	public List<SocioComp> getListSocioComp() {
		return listSocioComp;
	}

	public void setListSocioComp(List<SocioComp> listSocioComp) {
		this.listSocioComp = listSocioComp;
	}

	public SocioComp getSocioCom() {
		return socioCom;
	}

	public void setSocioCom(SocioComp socioCom) {
		this.socioCom = socioCom;
	}

	public String getStrDescEntidad() {
		return strDescEntidad;
	}

	public void setStrDescEntidad(String strDescEntidad) {
		this.strDescEntidad = strDescEntidad;
	}

	public List<Tabla> getListaTipoSolicitud() {
		 try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			    List<Tabla> lista = facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOSOLCITUD));
			    List<Tabla> listaTipo = new ArrayList<Tabla>();
			    if (getBeanSolCtaCte().getListaSolCtaCteTipo() != null)
			    {	
					for (Tabla tipoSol:lista){
						for (SolicitudCtaCteTipo solCtaCteTipoSel : getBeanSolCtaCte().getListaSolCtaCteTipo()) {
							if (solCtaCteTipoSel.getId().getIntTipoSolicitudctacte().equals(tipoSol.getIntIdDetalle())){
								tipoSol.setChecked(false);
								tipoSol.setStrAbreviatura(solCtaCteTipoSel.getStrScctObservacion());
								listaTipo.add(tipoSol);
							}
						}
					}
					
				  listaTipoSolicitud = listaTipo;
			    }	
				
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		
		if (listaTipoSolicitud != null)tamanioListaTipoSol = listaTipoSolicitud.size();
		return listaTipoSolicitud;
	}
	
	
	
	public List<Tabla> getListaTipoMovimiento() {
	    List<Tabla> listaTipoxx = new ArrayList<Tabla>();
		
		 try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   List<Tabla> listax =	facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOSOLCITUD));
			    
			    for (Tabla tipoSol:listax){
			    	boolean esSeleccionado = true;
				    	for (Tabla tabla : listaTipoSolicitud) {
					   				if (tabla.getIntIdDetalle().equals(tipoSol.getIntIdDetalle())){
											esSeleccionado = false;
										}
									
						}
			      
				  if (esSeleccionado)
			      listaTipoxx.add(tipoSol);
			       
			    }	
				
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		listaTipoMovimiento = listaTipoxx;
		return listaTipoMovimiento;
	}

	public void setListaTipoSolicitud(List<Tabla> listaTipoSolicitud) {
		this.listaTipoSolicitud = listaTipoSolicitud;
	}

	public List<Integer> getSelectListaTipoSolicitud() {
		return selectListaTipoSolicitud;
	}

	public void setSelectListaTipoSolicitud(List<Integer> selectListaTipoSolicitud) {
		this.selectListaTipoSolicitud = selectListaTipoSolicitud;
	}

	public String getStrCboAnio() {
		return strCboAnio;
	}

	public void setStrCboAnio(String strCboAnio) {
		this.strCboAnio = strCboAnio;
	}

	public Integer getIntCboMes() {
		return intCboMes;
	}

	public void setIntCboMes(Integer intCboMes) {
		this.intCboMes = intCboMes;
	}

	public List<String> getListaAnio() {
		java.util.Date date = new java.util.Date();
		String anioActual    = UtilCobranza.obtieneAnio(date);
		Integer anioSiguiente = Integer.parseInt(anioActual)+1;
		listaAnio = new ArrayList<String>();
		
		listaAnio.add(anioActual);
		listaAnio.add(anioSiguiente.toString());
		return listaAnio;
	}

	public void setListaAnio(List<String> listaAnio) {
		this.listaAnio = listaAnio;
	}

	public String getStrObservacion() {
		return strObservacion;
	}

	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}

	public Archivo getArchivoDocSolicitud() {
		return archivoDocSolicitud;
	}

	public void setArchivoDocSolicitud(Archivo archivoDocSolicitud) {
		this.archivoDocSolicitud = archivoDocSolicitud;
	}

	public Archivo getArchivoDocSolicitudTemp() {
		return archivoDocSolicitudTemp;
	}

	public void setArchivoDocSolicitudTemp(Archivo archivoDocSolicitudTemp) {
		this.archivoDocSolicitudTemp = archivoDocSolicitudTemp;
	}

	public boolean isFormBotonQuitarArchDisabled() {
		return formBotonQuitarArchDisabled;
	}

	public void setFormBotonQuitarArchDisabled(boolean formBotonQuitarArchDisabled) {
		this.formBotonQuitarArchDisabled = formBotonQuitarArchDisabled;
	}

	public boolean isCuentaCteRendered() {
		return cuentaCteRendered;
	}

	public void setCuentaCteRendered(boolean cuentaCteRendered) {
		this.cuentaCteRendered = cuentaCteRendered;
	}

	
	public void listarSocioEstructura(EstructuraComp estrucComp){
		log.info("-------------------------------------Debugging SocioEstructuraController.listarSocioEstructura-------------------------------------");
		if(estrucComp==null){
			estrucComp = new EstructuraComp();
		}
		
//		log.info("socioEstrucBusq.strEntidad: "+getEstrucBusq().getJuridica().getStrRazonSocial());

		estrucBusq = new Estructura();
		estrucBusq.setJuridica(new Juridica());
		estrucBusq.getJuridica().setStrRazonSocial(getStrNombreEntidad());
		estrucComp.setEstructura(estrucBusq);
		estrucComp.setEstructuraDetalle(new EstructuraDetalle());
		estrucComp.getEstructuraDetalle().setIntSeguSucursalPk(getIntSucuIdSucursal());
		estrucComp.getEstructuraDetalle().setIntSeguSubSucursalPk(getIntSudeIdSubsucursal());
		
		try {
			EstructuraFacadeRemote facade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
			this.listaEstructuraDetalle = facade.getConveEstrucDetAdministra(estrucComp);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}

	
	
	

	public boolean isCuentaCteFormRendered() {
		return cuentaCteFormRendered;
	}

	public void setCuentaCteFormRendered(boolean cuentaCteFormRendered) {
		this.cuentaCteFormRendered = cuentaCteFormRendered;
	}

	public List<Tabla> getListaTipoCondicion() {
		
		
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaTipoCondicion =	facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPO_CONDSOCIO));
			   
			  
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		
		return listaTipoCondicion;
	}

	public void setListaTipoCondicion(List<Tabla> listaTipoCondicion) {
		this.listaTipoCondicion = listaTipoCondicion;
	}

	public List<Tabla> getListaCondicion() {
		
		
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaCondicion =	facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_CONDICIONSOCIO));
			   
			  
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		return listaCondicion;
	}

	public void setListaCondicion(List<Tabla> listaCondicion) {
		this.listaCondicion = listaCondicion;
	}

	public boolean isIntTipoRolDisabled() {
		return intTipoRolDisabled;
	}

	public void setIntTipoRolDisabled(boolean intTipoRolDisabled) {
		this.intTipoRolDisabled = intTipoRolDisabled;
	}

	public String getStrListaPersonaRol() {
		return strListaPersonaRol;
	}

	public void setStrListaPersonaRol(String strListaPersonaRol) {
		this.strListaPersonaRol = strListaPersonaRol;
	}

	public boolean isBtnQuitarArchivoDisabled() {
		return btnQuitarArchivoDisabled;
	}

	public void setBtnQuitarArchivoDisabled(boolean btnQuitarArchivoDisabled) {
		this.btnQuitarArchivoDisabled = btnQuitarArchivoDisabled;
	}

	public boolean isCboTipoModalidadDisabled() {
		return cboTipoModalidadDisabled;
	}

	public void setCboTipoModalidadDisabled(boolean cboTipoModalidadDisabled) {
		this.cboTipoModalidadDisabled = cboTipoModalidadDisabled;
	}

	public boolean isCboMesDisabled() {
		return cboMesDisabled;
	}

	public void setCboMesDisabled(boolean cboMesDisabled) {
		this.cboMesDisabled = cboMesDisabled;
	}

	public boolean isCboAnioDisabled() {
		return cboAnioDisabled;
	}

	public void setCboAnioDisabled(boolean cboAnioDisabled) {
		this.cboAnioDisabled = cboAnioDisabled;
	}

	public boolean isChkTipoSolicitudDisabled() {
		return chkTipoSolicitudDisabled;
	}

	public void setChkTipoSolicitudDisabled(boolean chkTipoSolicitudDisabled) {
		this.chkTipoSolicitudDisabled = chkTipoSolicitudDisabled;
	}

	public boolean isTxtObservacionDisabled() {
		return txtObservacionDisabled;
	}

	public void setTxtObservacionDisabled(boolean txtObservacionDisabled) {
		this.txtObservacionDisabled = txtObservacionDisabled;
	}

	public boolean isBtnAnularRendered() {
		return btnAnularRendered;
	}

	public void setBtnAnularRendered(boolean btnAnularRendered) {
		this.btnAnularRendered = btnAnularRendered;
	}

	public boolean isBtnAnularDisabled() {
		return btnAnularDisabled;
	}

	public void setBtnAnularDisabled(boolean btnAnularDisabled) {
		this.btnAnularDisabled = btnAnularDisabled;
	}

	public Integer getIntCboParaTipo() {
		return intCboParaTipo;
	}

	public void setIntCboParaTipo(Integer intCboParaTipo) {
		this.intCboParaTipo = intCboParaTipo;
	}

	public Integer getTamanioListaTipoSol() {
		return tamanioListaTipoSol;
	}

	public void setTamanioListaTipoSol(Integer tamanioListaTipoSol) {
		this.tamanioListaTipoSol = tamanioListaTipoSol;
	}

	public Integer getIntCboParaTipoEstado() {
		return intCboParaTipoEstado;
	}

	public void setIntCboParaTipoEstado(Integer intCboParaTipoEstado) {
		this.intCboParaTipoEstado = intCboParaTipoEstado;
	}

	public Date getDtFechaInicio() {
		return dtFechaInicio;
	}

	public void setDtFechaInicio(Date dtFechaInicio) {
		this.dtFechaInicio = dtFechaInicio;
	}

	public Date getDtFechaFin() {
		return dtFechaFin;
	}

	public void setDtFechaFin(Date dtFechaFin) {
		this.dtFechaFin = dtFechaFin;
	}

	

	public void setListaTipoMovimiento(List<Tabla> listaTipoMovimiento) {
		this.listaTipoMovimiento = listaTipoMovimiento;
	}

	public SolicitudCtaCteTipo getSolCtaCteTipo() {
		return solCtaCteTipo;
	}

	public void setSolCtaCteTipo(SolicitudCtaCteTipo solCtaCteTipo) {
		this.solCtaCteTipo = solCtaCteTipo;
	}

	public List<Tabla> getListaMotivoLicencia() {
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaMotivoLicencia =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOMOTIVOSOLICITUD), "J");
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		return listaMotivoLicencia;
	}

	public void setListaMotivoLicencia(List<Tabla> listaMotivoLicencia) {
		this.listaMotivoLicencia = listaMotivoLicencia;
	}

	public SolicitudCtaCteTipo getSolicitudCtaCteTipoSel() {
		return solicitudCtaCteTipoSel;
	}

	public void setSolicitudCtaCteTipoSel(SolicitudCtaCteTipo solicitudCtaCteTipoSel) {
		this.solicitudCtaCteTipoSel = solicitudCtaCteTipoSel;
	}

	public boolean isEsGrabadoOk() {
		return esGrabadoOk;
	}

	public void setEsGrabadoOk(boolean esGrabadoOk) {
		this.esGrabadoOk = esGrabadoOk;
	}

	public String getMessageValidation() {
		return messageValidation;
	}

	public void setMessageValidation(String messageValidation) {
		this.messageValidation = messageValidation;
	}

	public boolean isCboAprobarRechazarDisabled() {
		return cboAprobarRechazarDisabled;
	}

	public void setCboAprobarRechazarDisabled(boolean cboAprobarRechazarDisabled) {
		this.cboAprobarRechazarDisabled = cboAprobarRechazarDisabled;
	}

	public List<Tabla> getListaMotivoCambioGarante() {
		
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaMotivoCambioGarante =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOMOTIVOSOLICITUD), "F");
			   
			  
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		
		return listaMotivoCambioGarante;
	}

	
   public List<Tabla> getListaMotivoCambioEntidad() {
		
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaMotivoCambioEntidad =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOMOTIVOSOLICITUD), "I");
			   
			  
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		
		return listaMotivoCambioEntidad;
	}
   
   public List<Tabla> getListaMotivoDescuentoIndebido() {
		
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaMotivoDescuentoIndebido =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOMOTIVOSOLICITUD), "D");
			   
			  
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		
		return listaMotivoDescuentoIndebido;
	}
   
   
   
   
	public void setListaMotivoDescuentoIndebido(
			List<Tabla> listaMotivoDescuentoIndebido) {
		this.listaMotivoDescuentoIndebido = listaMotivoDescuentoIndebido;
	}

	public void setListaMotivoCambioGarante(List<Tabla> listaMotivoCambioGarante) {
		this.listaMotivoCambioGarante = listaMotivoCambioGarante;
	}
	
	
	
	public void listaGarantePorCuentaSocio(){
		
		
		 List<GarantiaCreditoComp> listaGarantes = new ArrayList<GarantiaCreditoComp> ();
			
			try{
				
				ConceptoFacadeRemote   conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
				GarantiaCreditoFacadeRemote garantiaCreditoFacade = (GarantiaCreditoFacadeRemote)EJBFactory.getRemote(GarantiaCreditoFacadeRemote.class);
				
				Integer intEmpresa = socioCom.getCuenta().getId().getIntPersEmpresaPk();
				Integer intCuenta  = socioCom.getCuenta().getId().getIntCuenta();
				
				List<Expediente> listaExpediente = conceptoFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa, intCuenta);
				
				if (listaExpediente != null && listaExpediente.size() > 0)
				for (Expediente expediente : listaExpediente) {
					
					ExpedienteCreditoId expId = new ExpedienteCreditoId();
					expId.setIntCuentaPk(expediente.getId().getIntCuentaPk());
					expId.setIntItemExpediente(expediente.getId().getIntItemExpediente());
					expId.setIntItemDetExpediente(expediente.getId().getIntItemExpedienteDetalle());
					expId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
					//solicitudPrestamoFacade.getExpedienteCreditoPorId(expId);
					
					
					
					List<GarantiaCreditoComp> listaGaranCredComp =  garantiaCreditoFacade.getListaGarantiaCreditoCompPorExpediente(expId);
					
					for (GarantiaCreditoComp garantiaCreditoComp : listaGaranCredComp) {
						ExpedienteCredito expCredito = new ExpedienteCredito();
						expCredito.setId(new ExpedienteCreditoId());
						expCredito.getId().setIntItemExpediente(expId.getIntItemExpediente());
						expCredito.getId().setIntItemDetExpediente(expId.getIntItemDetExpediente());
						garantiaCreditoComp.setExpediente(expCredito);
						garantiaCreditoComp.getExpediente().setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
						garantiaCreditoComp.getExpediente().setBdMontoTotal(expediente.getBdMontoTotal());
					    listaGarantes.add(garantiaCreditoComp);
					}
				}
				
			} catch (EJBFactoryException e) {
					e.printStackTrace();
			} catch (BusinessException e) {
					e.printStackTrace();
			}
			
			messageValidation = "";
			if (listaGarantes == null || listaGarantes.size() == 0){
				messageValidation = "No existe expedientes";
			}
			
			listarGarantiaCreditoComp = listaGarantes;
			
	}

	
	public void seleccionarTipoEstructura(ActionEvent event){
		
		log.info("-------------------------------------Debugging cuentaCteController.seleccionarTipoEstructura-------------------------------");
		 Integer tipoEstructura  =  (Integer)event.getComponent().getAttributes().get("tipoEstructura");
		 log.info("tipoEstructura:"+tipoEstructura);
		 setBeanTipoEstructura(tipoEstructura);
		
	}
	
	public void buscarSocioEstructura(ActionEvent event){
		log.info("-------------------------------------Debugging cuentaCteController.buscarSocioEstructura-------------------------------------");
		
		 EstructuraComp estrucComp = new EstructuraComp();
		 listarSocioEstructura(estrucComp);
		
		if(listaEstructuraDetalle!=null){
			log.info("listaEstructuraDetalle.size: "+listaEstructuraDetalle.size());
		}
	}
	
	public void listarSubSucursalPorSuc(ValueChangeEvent event){
		log.info("-------------------------------------Debugging enlaceReciboController.listarSubSucursal-------------------------------------");
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = (Integer)event.getNewValue();
			if(intIdSucursal!=0){
				facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursal);
				log.info("listaSubsucursal.size: "+listaSubsucursal.size());
			}
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
			log.error(e);
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		setListJuridicaSubsucursal(listaSubsucursal);
	
	}
	
	public void seleccionarEntidad(ActionEvent event){
		log.info("-------------------------------------Debugging SocioEstructuraController.seleccionarEntidad-------------------------------------");
		messagePlanillaPrestamoNueva = null;
		habilitarFormAlerEnvioPlanilla = false;	 
//		if(getEntidadSeleccionada()==null  || getEntidadSeleccionada().getId()==null
//				|| getEntidadSeleccionada().getId().getIntCodigo()==null){
//			MessageController message = (MessageController)getSessionBean("messageController");
//			message.setWarningMessage("Debe seleccionar una Entidad. Haga click en la fila que desea seleccionar.");
//			return;
//		}
		try{
		
			
			 
			PersonaFacadeRemote    personaFacade         = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			EstructuraFacadeRemote estructuraFacade      = (EstructuraFacadeRemote) EJBFactory.getRemote(EstructuraFacadeRemote.class);
			
			SocioEstructura socioEstruc = new  SocioEstructura();
			
			EstructuraDetalle ed = getEntidadSeleccionada();
			
			log.info("ed.intSeguSucursalPk: "+ed.getIntSeguSucursalPk());
			log.info("ed.intParaModalidadCod: "+ed.getIntParaModalidadCod());
			log.info("ed.id.intCodigo: "+ed.getId().getIntCodigo());
			
			SocioEstructuraPK idSocEst = new SocioEstructuraPK();
			idSocEst.setIntIdEmpresa(socioCom.getSocio().getId().getIntIdEmpresa());
			idSocEst.setIntIdPersona(socioCom.getSocio().getId().getIntIdPersona());
			socioEstruc.setId(idSocEst);
			socioEstruc.setIntEmpresaSucUsuario(SESION_IDEMPRESA);
			socioEstruc.setIntIdSucursalUsuario(SESION_IDSUCURSAL);
			socioEstruc.setIntIdSubSucursalUsuario(SESION_IDSUBCURSAL);
			socioEstruc.setIntEmpresaSucAdministra(ed.getIntPersEmpresaPk());
			socioEstruc.setIntIdSucursalAdministra(ed.getIntSeguSucursalPk());
			socioEstruc.setIntIdSubsucurAdministra(ed.getIntSeguSubSucursalPk());
			socioEstruc.setIntTipoSocio(ed.getIntParaTipoSocioCod());
			socioEstruc.setIntModalidad(ed.getIntParaModalidadCod());
			socioEstruc.setIntNivel(ed.getId().getIntNivel());
			socioEstruc.setIntCodigo(ed.getId().getIntCodigo());
			socioEstruc.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			socioEstruc.setIntEmpresaUsuario(SESION_IDEMPRESA);
			socioEstruc.setIntPersonaUsuario(SESION_IDUSUARIO);
			socioEstruc.setIntTipoEstructura(getBeanTipoEstructura());
			
			EstructuraId id = new EstructuraId();
			id.setIntCodigo(socioEstruc.getIntCodigo());
			id.setIntNivel(socioEstruc.getIntNivel());
			
			Estructura estructura = estructuraFacade.getEstructuraPorPk(id);
			
			Juridica juridica = personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
			socioEstruc.setStrEntidad(juridica.getStrRazonSocial());
			log.info("tipo estructura Entidad:"+getBeanTipoEstructura()); 
			
			
				if (getBeanTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
					getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraOrigenNueva(socioEstruc);
				}else{
					getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraPrestamoNueva(socioEstruc);
				}
			
				if (!esValidoEntidadSeleccionado(socioEstruc))
				{
					if (getBeanTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
					    getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraOrigenNueva(null);
					}else{
						getBeanSolCtaCte().getSolCtaCteTipo().setSocioEstructuraPrestamoNueva(null);
					}
					
					
					
				}else{
					//Verificacion Planilla Enviada
					 verificarEnvioPlanilla();
				}
				
			
				log.info("DATOS DE LA ENTIDAD ACTUAL:"); 
				log.info("getIntCodigo:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigen().getIntCodigo()); 
				log.info("getIntNivel:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigen().getIntNivel()); 
				log.info("getIntModalidad:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigen().getIntModalidad()); 
				log.info("getIntTipoSocio:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigen().getIntTipoSocio()); 
				log.info("getIntTipoEstructura:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigen().getIntTipoEstructura()); 
				log.info("DATOS DE LA ENTIDAD NUEVA:"); 
				log.info("getIntCodigoNueva:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigenNueva().getIntCodigo()); 
				log.info("getIntNivelNueva:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigenNueva().getIntNivel()); 
				log.info("getIntModalidadNueva:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigenNueva().getIntModalidad()); 
				log.info("getIntTipoSocioNueva:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigenNueva().getIntTipoSocio()); 
				log.info("getIntTipoEstructuraNueva:"+getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigenNueva().getIntTipoEstructura()); 
			
			
			} catch (EJBFactoryException e) {
				log.error(e);
				e.printStackTrace();
			} catch (BusinessException e) {
				log.error(e);
				e.printStackTrace();
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
			}	

	}
	
	public boolean esValidoEntidadSeleccionado(SocioEstructura socioEstrucNueva){
		
	  log.info("--------------------------Metodo: esValidoEntidadSeleccionado---------");
		
	   messageValidation = ""; 
	   SocioEstructura  socioEstructuraOrigen         = getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigen();
	   SocioEstructura  socioEstructuraOrigenNueva    = getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraOrigenNueva();
	   
	   SocioEstructura  socioEstructuraPrestamo         = getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraPrestamo();
	   SocioEstructura  socioEstructuraPrestamoNueva    = getBeanSolCtaCte().getSolCtaCteTipo().getSocioEstructuraPrestamoNueva();
	   
	   //Caso Origen
	   if (socioEstrucNueva.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
		   
		   if (socioEstructuraOrigenNueva != null){
			   
			  if (socioEstructuraOrigenNueva.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)) {
				  
				    if (socioEstructuraOrigenNueva.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
						    if (socioEstructuraOrigen.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)){
						    	 messageValidation = "La asignación de la nueva Entidad de modalidad planilla no debe pasar de Haberes a CAS.";
						    	 return false;
						    }
					  }
				    
				    if (socioEstructuraOrigen.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_CESANTE)){
						  messageValidation = "No puede pasar un socio cesante a un activo.";
						  return false;
					 }
				    
			  }else{
				  if (socioEstructuraOrigen.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_CESANTE)){
					   if(socioEstructuraOrigenNueva.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)){

					   }
					   else{
						   messageValidation = "El el socio cesante debe tener asignado solamente la modalidad planilla Haberes.";
						   return false;
					   }
				  }
			  }
			   
		   }
	   }else{
		 //Caso Entidad Prestammo
		   if (socioEstructuraPrestamoNueva != null){
			   
				  if (socioEstructuraPrestamoNueva.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_ACTIVO)) {
					  
					    if (socioEstructuraPrestamoNueva.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)){
							    if (socioEstructuraPrestamo.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)){
							    	 messageValidation = "La asignación de la nueva Entidad de modalidad planilla no debe pasar de Haberes a CAS.";
							    	 return false;
							    }
						  }
					  	  
						 if (socioEstructuraPrestamo.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_CESANTE)){
								  messageValidation = "No puede pasar un socio cesante a activo.";
								  return false;
						 }
						 
				  }else{
					  if (socioEstructuraPrestamo.getIntTipoSocio().equals(Constante.PARAM_T_TIPOSOCIO_CESANTE)){
						   if(socioEstructuraPrestamoNueva.getIntModalidad().equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)){

						   }
						   else{
							   messageValidation = "El el socio cesante debe tener asignado solamente la modalidad planilla Haberes.";
							   return false;
						   }
					  }
				  }
				   
			   }
	   }
	   
	   return true;
		
	}
	
	public void loadListDetCondicionLaboral(Integer intCondicionLaboral) {
		log.info("-------------------------------------Debugging CuentaCteController.loadListDetCondicionLaboral-------------------------------------");
		TablaFacadeRemote facade = null;
		List<Tabla> listaDetCondiLaboral = null;
		try {
			if(intCondicionLaboral!=null && intCondicionLaboral!=0){
				facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaDetCondiLaboral = facade.getListaTablaPorAgrupamientoB(Integer.parseInt(Constante.PARAM_T_DETALLECONDICIONLABORAL), intCondicionLaboral);
				log.info("listaDetCondiLaboral.size: "+listaDetCondiLaboral.size());
			}
			
			if(intCondicionLaboral.equals(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO)){
				System.out.println("Mostrando campos del contrato...");
				setIsContratado(true);
			}else{
				System.out.println("Ocultando campos del contrato...");
				socioCom.getPersona().getNatural().getPerLaboral().setIntTipoContrato(null);
				socioCom.getPersona().getNatural().getPerLaboral().setDtInicioContrato(null);
				socioCom.getPersona().getNatural().getPerLaboral().setDtFinContrato(null);
				socioCom.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
				socioCom.getPersona().getNatural().getPerLaboral().getContrato().setStrNombrearchivo(null);
				setIsContratado(false);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListDetCondicionLaboral(listaDetCondiLaboral);
	}
	
	public void loadListDetCondicionLaboral(ValueChangeEvent event) {
		log.info("-------------------------------------Debugging CuentaCteController.loadListDetCondicionLaboral-------------------------------------");
		TablaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Tabla> listaDetCondiLaboral = null;
		try {
			intIdSucursal = (Integer)event.getNewValue();
			if(intIdSucursal!=null && intIdSucursal!=0){
				facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
				listaDetCondiLaboral = facade.getListaTablaPorAgrupamientoB(Integer.parseInt(Constante.PARAM_T_DETALLECONDICIONLABORAL), Integer.parseInt(event.getNewValue().toString()));
				log.info("listaDetCondiLaboral.size: "+listaDetCondiLaboral.size());
			}
			
			if(Integer.valueOf(event.getNewValue().toString()).equals(Constante.PARAM_T_CONDICIONLABORAL_CONTRATADO)){
				System.out.println("Mostrando campos del contrato...");
				setIsContratado(true);
			}else{
				System.out.println("Ocultando campos del contrato...");
				socioCom.getPersona().getNatural().getPerLaboral().setIntTipoContrato(null);
				socioCom.getPersona().getNatural().getPerLaboral().setDtInicioContrato(null);
				socioCom.getPersona().getNatural().getPerLaboral().setDtFinContrato(null);
				socioCom.getPersona().getNatural().getPerLaboral().setContrato(new Archivo());
				socioCom.getPersona().getNatural().getPerLaboral().getContrato().setStrNombrearchivo(null);
				setIsContratado(false);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListDetCondicionLaboral(listaDetCondiLaboral);
		habilitarDatosLaborales = false;
	}
	
	public void adjuntarContrato(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.adjuntarContrato-------------------------------------");
		FileUploadController fileupload = (FileUploadController)getSessionBean("fileUploadController");
		fileupload.setStrDescripcion("Seleccione el contrato correspondiente al socio.");
		fileupload.setFileType(pe.com.tumi.common.FileUtil.allDocumentTypes);
		
		//Si ya ha grabado el contrato(Archivo) anteriormente
		Integer intItemArchivo = null;
		Integer intItemHistorico = null;
		if(socioCom.getPersona().getNatural().getPerLaboral().getContrato()!=null &&
				socioCom.getPersona().getNatural().getPerLaboral().getContrato().getId()!=null){
			intItemArchivo   = socioCom.getPersona().getNatural().getPerLaboral().getContrato().getId().getIntItemArchivo();
			intItemHistorico = socioCom.getPersona().getNatural().getPerLaboral().getContrato().getId().getIntItemHistorico();
		}
		
		fileupload.setParamArchivo(intItemArchivo, intItemHistorico, Constante.PARAM_T_TIPOARCHIVOADJUNTO_CONTRATOLABPERSONA);
	}
	
	public void verContratoPerNatu(ActionEvent event) throws IOException{
		log.info("-------------------------------------Debugging CuentaCteController.verContratoPerNatu-------------------------------------");
		//Mostrar la Foto del Socio
		
		if(socioCom.getPersona().getNatural().getPerLaboral() != null){
			if (socioCom.getPersona().getNatural().getPerLaboral().getContrato() == null){
			    socioCom.getPersona().getNatural().getPerLaboral().setContrato(fileContrato);
			}
		}
		
		if(socioCom.getPersona().getNatural().getPerLaboral()!=null &&
				socioCom.getPersona().getNatural().getPerLaboral().getContrato()!=null){
			Archivo contrato = socioCom.getPersona().getNatural().getPerLaboral().getContrato();
			log.info("Path Contrato: "+contrato.getTipoarchivo().getStrRuta()+"\\"+contrato.getStrNombrearchivo());
			File file = new File(contrato.getTipoarchivo().getStrRuta()+"\\"+contrato.getStrNombrearchivo());
			log.info("file.exists(): "+file.exists());
			
	        byte[] bytes = getBytesFromFile(file);
	        
	        FacesContext faces = FacesContext.getCurrentInstance();
	        HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
	        getResponse().setContentType("application/pdf");
	        getResponse().setContentLength(bytes.length);
	        getResponse().setHeader( "Content-disposition", "inline; filename=\""+file.getName()+"\"");
	        System.out.println("file.getName(): "+file.getName());
	        try {
		        ServletOutputStream out;
		        out = response.getOutputStream();
		        out.write(bytes);
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        faces.responseComplete();
		}
	}
	
	//Returns the contents of the file in a byte array.
    public static byte[] getBytesFromFile(File file) throws IOException {        
        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
            throw new IOException("File is too large!");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;

        InputStream is = new FileInputStream(file);
        try {
            while (offset < bytes.length
                   && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
                offset += numRead;
            }
        } finally {
            is.close();
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
        return bytes;
    }
	
	public void setSelectedEntidad(ActionEvent event){
		log.info("-------------------------------------Debugging cuentaCteController.setSelectedEntidad-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowEntidad"));
		String selectedRow = getRequestParameter("rowEntidad");
		EstructuraDetalle ed = null;
		for(int i=0; i<listaEstructuraDetalle.size(); i++){
			ed = listaEstructuraDetalle.get(i);
			if(i == Integer.parseInt(selectedRow)){
				setEntidadSeleccionada(ed);
				break;
			}
		}
		log.info("ed.id.intCodigo: "+ed.getId().getIntCodigo());
	}
	
	
	public void resetFechasContrato(ActionEvent event){
		log.info("-------------------------------------Debugging SocioController.resetFechasContrato-------------------------------------");
		log.info("pIntTipoContrato: "+getRequestParameter("pIntTipoContrato"));
		String strTipoContrato = getRequestParameter("pIntTipoContrato");
		
		try{
			if(Integer.valueOf(strTipoContrato).equals(Constante.PARAM_T_TIPOCONTRATO_PLAZOFIJO)){
				setBlnFechaContratoDisabled(false);
			}else if(Integer.valueOf(strTipoContrato).equals(Constante.PARAM_T_TIPOCONTRATO_INDETERMINADO) ||
					Integer.valueOf(strTipoContrato).equals(0)){
				    setBlnFechaContratoDisabled(true);
			}
			socioCom.getPersona().getNatural().getPerLaboral().setDtInicioContrato(null);
			socioCom.getPersona().getNatural().getPerLaboral().setDtFinContrato(null);
		}catch (Exception e){
			log.error(e.getMessage());
		}
	}
	
	public List<GarantiaCreditoComp> getListarGarantiaCreditoComp() {
		return listarGarantiaCreditoComp;
	}

	public void setListarGarantiaCreditoComp(List<GarantiaCreditoComp> listarGarantiaCreditoComp) {
		this.listarGarantiaCreditoComp = listarGarantiaCreditoComp;
	}

	public String getTxtDniGarante() {
		return txtDniGarante;
	}

	public void setTxtDniGarante(String txtDniGarante) {
		this.txtDniGarante = txtDniGarante;
	}

	public List<SocioComp> getListGaranteComp() {
		return listGaranteComp;
	}

	public void setListGaranteComp(List<SocioComp> listGaranteComp) {
		this.listGaranteComp = listGaranteComp;
	}

	public Integer getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(Integer nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public SolicitudCtaCteTipo getBeanSolCtaCteTipoSel() {
		return beanSolCtaCteTipoSel;
	}

	public void setBeanSolCtaCteTipoSel(SolicitudCtaCteTipo beanSolCtaCteTipoSel) {
		this.beanSolCtaCteTipoSel = beanSolCtaCteTipoSel;
	}

	public boolean isEsValidoGarante() {
		return esValidoGarante;
	}

	public void setEsValidoGarante(boolean esValidoGarante) {
		this.esValidoGarante = esValidoGarante;
	}

	public GarantiaCreditoComp getBeanGarantiaCreditoSel() {
		return beanGarantiaCreditoSel;
	}

	public void setBeanGarantiaCreditoSel(GarantiaCreditoComp beanGarantiaCreditoSel) {
		this.beanGarantiaCreditoSel = beanGarantiaCreditoSel;
	}

	public boolean isHabilitarFormLicencia() {
		return habilitarFormLicencia;
	}

	public void setHabilitarFormLicencia(boolean habilitarFormLicencia) {
		this.habilitarFormLicencia = habilitarFormLicencia;
	}

	public boolean isHabilitarFormCambioGarante() {
		return habilitarFormCambioGarante;
	}

	public void setHabilitarFormCambioGarante(boolean habilitarFormCambioGarante) {
		this.habilitarFormCambioGarante = habilitarFormCambioGarante;
	}

	public boolean isBtnAtenderDisabled() {
		return btnAtenderDisabled;
	}

	public void setBtnAtenderDisabled(boolean btnAtenderDisabled) {
		this.btnAtenderDisabled = btnAtenderDisabled;
	}

	public boolean isBtnAgregarDisabled() {
		return btnAgregarDisabled;
	}

	public void setBtnAgregarDisabled(boolean btnAgregarDisabled) {
		this.btnAgregarDisabled = btnAgregarDisabled;
	}

	public Integer getIntCboTipoSolicitudAux() {
		return intCboTipoSolicitudAux;
	}

	public void setIntCboTipoSolicitudAux(Integer intCboTipoSolicitudAux) {
		this.intCboTipoSolicitudAux = intCboTipoSolicitudAux;
	}

	public boolean isBtnVerDisabled() {
		return btnVerDisabled;
	}

	public void setBtnVerDisabled(boolean btnVerDisabled) {
		this.btnVerDisabled = btnVerDisabled;
	}

	public boolean isHabilitarFormCambioEntidad() {
		return habilitarFormCambioEntidad;
	}

	public void setHabilitarFormCambioEntidad(boolean habilitarFormCambioEntidad) {
		this.habilitarFormCambioEntidad = habilitarFormCambioEntidad;
	}

	public void setListaMotivoCambioEntidad(List<Tabla> listaMotivoCambioEntidad) {
		this.listaMotivoCambioEntidad = listaMotivoCambioEntidad;
	}

	public Estructura getEstrucBusq() {
		return estrucBusq;
	}

	public void setEstrucBusq(Estructura estrucBusq) {
		this.estrucBusq = estrucBusq;
	}

	public List<SocioEstructura> getBeanListaSocioEstructura() {
		return beanListaSocioEstructura;
	}

	public void setBeanListaSocioEstructura(
			List<SocioEstructura> beanListaSocioEstructura) {
		this.beanListaSocioEstructura = beanListaSocioEstructura;
	}

	public List<EstructuraDetalle> getListaEstructuraDetalle() {
		return listaEstructuraDetalle;
	}

	public void setListaEstructuraDetalle(
			List<EstructuraDetalle> listaEstructuraDetalle) {
		this.listaEstructuraDetalle = listaEstructuraDetalle;
	}

	public EstructuraDetalle getEntidadSeleccionada() {
		return entidadSeleccionada;
	}

	public void setEntidadSeleccionada(EstructuraDetalle entidadSeleccionada) {
		this.entidadSeleccionada = entidadSeleccionada;
	}

	public Integer getIntSucuIdSucursal() {
		return intSucuIdSucursal;
	}

	public void setIntSucuIdSucursal(Integer intSucuIdSucursal) {
		this.intSucuIdSucursal = intSucuIdSucursal;
	}

	public Integer getIntSudeIdSubsucursal() {
		return intSudeIdSubsucursal;
	}

	public void setIntSudeIdSubsucursal(Integer intSudeIdSubsucursal) {
		this.intSudeIdSubsucursal = intSudeIdSubsucursal;
	}

	public List<Estructura> getListEstructura() {
		
		log.info("-------------------------------------Debugging CuentaCteController.getListEstructura-------------------------------------");
		try {
			if(listEstructura == null){
				EstructuraFacadeRemote facade = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
				this.listEstructura = facade.getListaEstructuraPorNivelYCodigoRel(null,null);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if(listEstructura!=null){
			log.info("beanListInstitucion.size: "+listEstructura.size());
		}
		return listEstructura;
	
	}

	public void setListEstructura(List<Estructura> listEstructura) {
		this.listEstructura = listEstructura;
	}

	public Integer getBeanTipoEstructura() {
		return beanTipoEstructura;
	}

	public void setBeanTipoEstructura(Integer beanTipoEstructura) {
		this.beanTipoEstructura = beanTipoEstructura;
	}

	public String getMessagePlanillaOrigen() {
		return messagePlanillaOrigen;
	}

	public void setMessagePlanillaOrigen(String messagePlanillaOrigen) {
		this.messagePlanillaOrigen = messagePlanillaOrigen;
	}

	public String getMessagePlanillaPrestamo() {
		return messagePlanillaPrestamo;
	}

	public void setMessagePlanillaPrestamo(String messagePlanillaPrestamo) {
		this.messagePlanillaPrestamo = messagePlanillaPrestamo;
	}

	public String getStrNombreEntidad() {
		return strNombreEntidad;
	}

	public void setStrNombreEntidad(String strNombreEntidad) {
		this.strNombreEntidad = strNombreEntidad;
	}

	public Boolean getIsContratado() {
		return isContratado;
	}

	public void setIsContratado(Boolean isContratado) {
		this.isContratado = isContratado;
	}

	public List<Tabla> getListDetCondicionLaboral() {
		return listDetCondicionLaboral;
	}

	public void setListDetCondicionLaboral(List<Tabla> listDetCondicionLaboral) {
		this.listDetCondicionLaboral = listDetCondicionLaboral;
	}

	public Boolean getBlnFechaContratoDisabled() {
		return blnFechaContratoDisabled;
	}

	public void setBlnFechaContratoDisabled(Boolean blnFechaContratoDisabled) {
		this.blnFechaContratoDisabled = blnFechaContratoDisabled;
	}

	public String getStrMessageAlertEnvioPlanilla() {
		return strMessageAlertEnvioPlanilla;
	}

	public void setStrMessageAlertEnvioPlanilla(String strMessageAlertEnvioPlanilla) {
		this.strMessageAlertEnvioPlanilla = strMessageAlertEnvioPlanilla;
	}

	public String getStrPreguntaAlertaEnvioPlanilla() {
		return strPreguntaAlertaEnvioPlanilla;
	}

	public void setStrPreguntaAlertaEnvioPlanilla(
			String strPreguntaAlertaEnvioPlanilla) {
		this.strPreguntaAlertaEnvioPlanilla = strPreguntaAlertaEnvioPlanilla;
	}

	public String getStrMensajeCambioEntidad() {
		return strMensajeCambioEntidad;
	}

	public void setStrMensajeCambioEntidad(String strMensajeCambioEntidad) {
		this.strMensajeCambioEntidad = strMensajeCambioEntidad;
	}

	public Boolean getHabilitarBotonAlertEnvioPlanilla() {
		return habilitarBotonAlertEnvioPlanilla;
	}

	public void setHabilitarBotonAlertEnvioPlanilla(
			Boolean habilitarBotonAlertEnvioPlanilla) {
		this.habilitarBotonAlertEnvioPlanilla = habilitarBotonAlertEnvioPlanilla;
	}

	public Boolean getHabilitarFormAlerEnvioPlanilla() {
		return habilitarFormAlerEnvioPlanilla;
	}

	public void setHabilitarFormAlerEnvioPlanilla(
			Boolean habilitarFormAlerEnvioPlanilla) {
		this.habilitarFormAlerEnvioPlanilla = habilitarFormAlerEnvioPlanilla;
	}

	public String getMessagePlanillaOrigenNueva() {
		return messagePlanillaOrigenNueva;
	}

	public void setMessagePlanillaOrigenNueva(String messagePlanillaOrigenNueva) {
		this.messagePlanillaOrigenNueva = messagePlanillaOrigenNueva;
	}

	public String getMessagePlanillaPrestamoNueva() {
		return messagePlanillaPrestamoNueva;
	}

	public void setMessagePlanillaPrestamoNueva(String messagePlanillaPrestamoNueva) {
		this.messagePlanillaPrestamoNueva = messagePlanillaPrestamoNueva;
	}

	public List<Tabla> getListaMotivoCambioCondicion() {
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaMotivoCambioCondicion =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOMOTIVOSOLICITUD), "G");
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		return listaMotivoCambioCondicion;
	}

	
	public void setListaMotivoCambioCondicion(List<Tabla> listaMotivoCambioCondicion) {
		this.listaMotivoCambioCondicion = listaMotivoCambioCondicion;
	}

	public List<Tabla> getListaCondicionFinal() {
		
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaCondicionFinal =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_CONDICIONSOCIO), "A");
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		return listaCondicionFinal;
	}

	public void setListaCondicionFinal(List<Tabla> listaCondicionFinal) {
		
		
		this.listaCondicionFinal = listaCondicionFinal;
	}

	
	public void mostrarConceptosAbloquear(ActionEvent event){
		log.info("-------------------------------------Debugging cuentaCtaController.mostrarConceptosAbloquear-------------------------------------");
		messageValidation = "";
		 dtFechaFinDisbled = false;
		Integer intCondicionCtaFinal  = null;
		Integer intCondicionCtaInicio = null;
		boolean esValidoCtaConcepto    = false;
		boolean esValidoCtaExpediente  = false;
		boolean flgAuxiliar  = false;
		
		beanSolCtaCte.getSolCtaCteTipo().setListaExpediente(null);
		beanSolCtaCte.setCuenta(socioCom.getCuenta());
  	    beanSolCtaCte.getCuenta().setListaConcepto(null);
		
		try {
			ConceptoFacadeRemote cuentaFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			 intCondicionCtaFinal = beanSolCtaCte.getSolCtaCteTipo().getIntParaCondicionCuentaFinal();
			 
			 if (intCondicionCtaFinal.equals(Constante.PARAM_T_CONDICIONSOCIO_HABIL)){
				  intCondicionCtaInicio = socioCom.getCuenta().getIntParaCondicionCuentaCod();
				  if (intCondicionCtaInicio.equals(Constante.PARAM_T_CONDICIONSOCIO_CAD) || 
				      intCondicionCtaInicio.equals(Constante.PARAM_T_CONDICIONSOCIO_CMO) ||
				      intCondicionCtaInicio.equals(Constante.PARAM_T_CONDICIONSOCIO_INA)){
					  messageValidation = "No puede cambiar de una condicion habil a una condición Administrativa,Moroza o Activa.";
					  getBeanSolCtaCte().getSolCtaCteTipo().setIntParaCondicionCuentaFinal(0);
				  }
			 }else
		     if (intCondicionCtaFinal.equals(Constante.PARAM_T_CONDICIONSOCIO_CAD)){
		    	 
			    	 CuentaId idCuenta = socioCom.getCuenta().getId();
			    	 beanSolCtaCte.setCuenta(socioCom.getCuenta());
			    	//Lista Cuenta Concepto
			    	List<CuentaConcepto> listaCtaConcepto =  cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			    	List<CuentaConcepto> listaCtaConceptoTemp =  new ArrayList<CuentaConcepto>();
			    	
			    	BigDecimal montoSaldoAportaciones = new BigDecimal(0);
			    	 for (CuentaConcepto cuentaConcepto : listaCtaConcepto) {
			    		
			    		 if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_APORTACIONES)){
			    			 //socio con saldo de aportes en S/.0
			    			     montoSaldoAportaciones = cuentaConcepto.getBdSaldo();
			    			 if (cuentaConcepto.getBdSaldo().equals(new BigDecimal(0))){
			    				 esValidoCtaConcepto = true;
			    			 }
			    			 listaCtaConceptoTemp.add(cuentaConcepto);
			    		 }
			    		 else
			    		 if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_SEPELIO) ||
			    					cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
			    			 listaCtaConceptoTemp.add(cuentaConcepto);
			    		 }
					  }
			    	 
			    	  //Liste Expediente credito.
			         Integer idEmpresa  = socioCom.getCuenta().getId().getIntPersEmpresaPk();
			         Integer intCuenta  = socioCom.getCuenta().getId().getIntCuenta();
			         
			         List<Expediente> listaExpediente     =  cuentaFacade.getListaExpedientePorEmpresaYCta(idEmpresa, intCuenta); 
			         List<Expediente> listaExpedienteTemp = new ArrayList<Expediente>(); 
			         if (listaExpediente == null || listaExpediente.size() == 0) flgAuxiliar = true; 
			         
			         BigDecimal sumSaldoCredito = new BigDecimal(0);
			         for (int i = 0; i < listaExpediente.size(); i++) {
			        	  Expediente expediente = listaExpediente.get(i);
			        	 if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)){
			        		 //socio con actividades anuladas
			        		 if (expediente.getBdSaldoCredito() == null) expediente.setBdSaldoCredito(new BigDecimal(0));
			        		 
			        		 sumSaldoCredito = sumSaldoCredito.add(expediente.getBdSaldoCredito());
			        		 flgAuxiliar = true;
			        		 listaExpedienteTemp.add(expediente);
			        		
			        	 }
					 }
			        
			         if (listaExpediente != null || listaExpediente.size() > 0){
			        	 if(!flgAuxiliar){
			        		 flgAuxiliar = true;
			        	 }
			         }
			        
			         if (flgAuxiliar && sumSaldoCredito.equals(new BigDecimal(0))){
			        	 esValidoCtaExpediente = true;
			         }
			         
			          
			         if (esValidoCtaConcepto && esValidoCtaExpediente){
			        	  beanSolCtaCte.getCuenta().setListaConcepto(listaCtaConceptoTemp);
			        	  beanSolCtaCte.getSolCtaCteTipo().setListaExpediente(listaExpedienteTemp);
			        	  dtFechaFinDisbled = false;
			         }else{
			        	     messageValidation ="No puede cambiar la condición: ";
			        	 if (montoSaldoAportaciones.compareTo(new BigDecimal(0)) == 1){
			        		 messageValidation = messageValidation+ "Socio con monto Aporte > 0. ";
			        	 }
			        	 else
			        	 if (sumSaldoCredito.compareTo(new BigDecimal(0)) == 1){
			        		 messageValidation = messageValidation+"Socio con monto saldo de Crédito Actividad > 0.";
			        	 }
			        	 
			        	  getBeanSolCtaCte().getSolCtaCteTipo().setIntParaCondicionCuentaFinal(0);
			        	  beanSolCtaCte.getSolCtaCteTipo().setListaExpediente(null);
			        	  beanSolCtaCte.getCuenta().setListaConcepto(null);
			         }
		         
		         
			 } if (intCondicionCtaFinal.equals(Constante.PARAM_T_CONDICIONSOCIO_CMO)){
				 
				 CuentaId idCuenta = socioCom.getCuenta().getId();
				 beanSolCtaCte.setCuenta(socioCom.getCuenta());
			    	//Lista Cuenta Concepto
			    	List<CuentaConcepto> listaCtaConcepto =  cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			    	List<CuentaConcepto> listaCtaConceptoTemp =  new ArrayList<CuentaConcepto>();
			    	BigDecimal montoSaldoAportaciones = new BigDecimal(0);
			    	 for (CuentaConcepto cuentaConcepto : listaCtaConcepto) {
			    		
			    		 if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_APORTACIONES)){
			    			 //socio con saldo de aportes en S/.0
			    			     montoSaldoAportaciones = cuentaConcepto.getBdSaldo(); 
			    			 if (cuentaConcepto.getBdSaldo().equals(new BigDecimal(0))){
			    				 esValidoCtaConcepto = true;
			    			 }
			    			 listaCtaConceptoTemp.add(cuentaConcepto);
			    		 }
			    		 else
			    		 if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_SEPELIO) ||
			    			 cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO) ||
			    			 cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_MANT_CUENTA)){
			    			 listaCtaConceptoTemp.add(cuentaConcepto);
			    		 }
			    		
					  }
			    	 
			    	  //Liste Expediente credito.
			         Integer idEmpresa = socioCom.getCuenta().getId().getIntPersEmpresaPk();
			         Integer intCuenta  = socioCom.getCuenta().getId().getIntCuenta();
			         
			         List<Expediente> listaExpediente     =  cuentaFacade.getListaExpedientePorEmpresaYCta(idEmpresa, intCuenta); 
			         List<Expediente> listaExpedienteTemp = new ArrayList<Expediente>(); 
			         
			         BigDecimal sumSaldoCredito = new BigDecimal(0);
			         BigDecimal sumSaldoPrestamoCredito = new BigDecimal(0);
			         BigDecimal sumSaldoPrestamoInteres = new BigDecimal(0);
			         
			         if (listaExpediente == null || listaExpediente.size() == 0) flgAuxiliar = true; 
			         for (int i = 0; i < listaExpediente.size(); i++) {
			        	  Expediente expediente = listaExpediente.get(i);
			        	 if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)){
			        		 //socio con actividades anuladas
			        		 if (expediente.getBdSaldoCredito() == null) expediente.setBdSaldoCredito(new BigDecimal(0));
			        		 
			        		 sumSaldoCredito = sumSaldoCredito.add(expediente.getBdSaldoCredito());
			        		 flgAuxiliar = true;
			        	 }
					 }
			         
			         for (int i = 0; i < listaExpediente.size(); i++) {
			        	  Expediente expediente = listaExpediente.get(i);
			        	 if (flgAuxiliar && expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)){
			        		     
			        		     sumSaldoPrestamoCredito = sumSaldoPrestamoCredito.add(expediente.getBdSaldoCredito()!=null?expediente.getBdSaldoCredito():new BigDecimal(0));
			        		     sumSaldoPrestamoInteres = sumSaldoPrestamoInteres.add(expediente.getBdSaldoInteres()!=null?expediente.getBdSaldoInteres():new BigDecimal(0));
			        		 if (expediente.getBdSaldoCredito() != null && expediente.getBdSaldoCredito().doubleValue() > 0 && expediente.getBdSaldoInteres().doubleValue() > 0){
			        			 expediente.setStrDescripcion("- Interes");
			        			 listaExpedienteTemp.add(expediente);
			        		 }
			        		 
			        	 }
					     
					 }
			         
			         if (listaExpediente != null || listaExpediente.size() > 0){
			        	 if(!flgAuxiliar){
			        		 flgAuxiliar = true;
			        	 }
			         }
			         
			         if (flgAuxiliar && sumSaldoCredito.equals(new BigDecimal(0))){
			        	 esValidoCtaExpediente = true;
			         }
			         
			          
			         if (esValidoCtaConcepto && esValidoCtaExpediente){
			        	  beanSolCtaCte.getSolCtaCteTipo().setListaExpediente(listaExpedienteTemp);
			        	  beanSolCtaCte.getCuenta().setListaConcepto(listaCtaConceptoTemp);
			        	  dtFechaFinDisbled = false;
			        	  beanSolCtaCte.getSolCtaCteTipo().getMovimiento().setDtFechaFin(null);
			         }else{
			        	  messageValidation ="No puede cambiar la condición: ";
			        	 if (montoSaldoAportaciones.compareTo(new BigDecimal(0)) == 1){
			        		 messageValidation =messageValidation+ "Socio con monto Aporte > 0.";
			        	 }
			        	 else
			        	 if (sumSaldoCredito.compareTo(new BigDecimal(0)) == 1){
			        		 messageValidation =messageValidation+ "Socio con monto saldo de Crédito Actividad > 0.";
			        	 }
			        	 else
			        	 if (sumSaldoPrestamoCredito.compareTo(new BigDecimal(0)) == 1){
			        		 messageValidation =messageValidation+ "Socio con monto saldo prestamo  > 0.";
			        	 }
			        	 else
			        	 if (sumSaldoPrestamoInteres.compareTo(new BigDecimal(0)) == 1){
			        		 messageValidation =messageValidation+ "Socio con monto saldo Interes > 0.";
			        	 }
			        	 
			        	 getBeanSolCtaCte().getSolCtaCteTipo().setIntParaCondicionCuentaFinal(0);
			        	 beanSolCtaCte.getSolCtaCteTipo().setListaExpediente(null);
			        	 beanSolCtaCte.getCuenta().setListaConcepto(null);
			         }
			         
			         
			 }
			 if (intCondicionCtaFinal.equals(Constante.PARAM_T_CONDICIONSOCIO_INA)){
				
				 CuentaId idCuenta = socioCom.getCuenta().getId();
				 beanSolCtaCte.setCuenta(socioCom.getCuenta());
			    	//Lista Cuenta Concepto
			    	List<CuentaConcepto> listaCtaConcepto =  cuentaFacade.getListaCuentaConceptoPorPkCuenta(idCuenta);
			    	
			    	 for (CuentaConcepto cuentaConcepto : listaCtaConcepto) {
			    		
			    		 if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
			    			 //socio con saldo de aportes en S/.0
			    			 
			    			 if (cuentaConcepto.getBdSaldo().equals(new BigDecimal(0))){
			    				 esValidoCtaConcepto = true;
			    			 }
			    		 }
			    		 
					  }
			    	 
			    	  //Liste Expediente credito.
			         Integer idEmpresa = socioCom.getCuenta().getId().getIntPersEmpresaPk();
			         Integer intCuenta  = socioCom.getCuenta().getId().getIntCuenta();
			         
			         List<Expediente> listaExpediente     =  cuentaFacade.getListaExpedientePorEmpresaYCta(idEmpresa, intCuenta); 
			         
			         if (listaExpediente == null || listaExpediente.size() == 0) flgAuxiliar = true; 
			         
			         List<Expediente> listaExpedienteTemp = new ArrayList<Expediente>(); 
			         
			         BigDecimal bdSaldoCreditoActividad    = new BigDecimal(0);
			         
			         
			         BigDecimal bdSaldoCreditoTotalPrestamo     = new BigDecimal(0);
			         BigDecimal bdSaldoCreditoPrestamo     		= new BigDecimal(0);
			         BigDecimal bdSaldoCreditoInteres      		= new BigDecimal(0);
			         BigDecimal bdSaldoCreditoMora        		= new BigDecimal(0);
			         
			         BigDecimal bdSaldoCreditoOrdenCredito = new BigDecimal(0);
			         
			         
			         
			         for (int i = 0; i < listaExpediente.size(); i++) {
			        	  Expediente expediente = listaExpediente.get(i);
			        	 if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)){
			        		 if (expediente.getBdSaldoCredito() == null) expediente.setBdSaldoCredito(new BigDecimal(0));
				        		
			        		 bdSaldoCreditoActividad     = bdSaldoCreditoActividad.add(expediente.getBdSaldoCredito());
			        		 flgAuxiliar = true;
			        	 }else 
			        	 if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)){
			        		 if (expediente.getBdSaldoCredito() == null) expediente.setBdSaldoCredito(new BigDecimal(0));
			        		 if (expediente.getBdSaldoInteres() == null) expediente.setBdSaldoInteres(new BigDecimal(0));
			        		 if (expediente.getBdSaldoMora() == null) expediente.setBdSaldoMora(new BigDecimal(0));
			        		 
				        	 flgAuxiliar = true;
			        		 bdSaldoCreditoPrestamo = bdSaldoCreditoPrestamo.add(expediente.getBdSaldoCredito());
			        		 bdSaldoCreditoInteres  = bdSaldoCreditoInteres.add(expediente.getBdSaldoInteres());
			        		 bdSaldoCreditoMora     = bdSaldoCreditoMora.add(expediente.getBdSaldoMora());
		        		     expediente.setStrDescripcion("- Interes");
			        	 }else
			        	  if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)){
			        		  if (expediente.getBdSaldoCredito() == null) expediente.setBdSaldoCredito(new BigDecimal(0));
			        		  flgAuxiliar = true;
			        		  bdSaldoCreditoOrdenCredito = bdSaldoCreditoOrdenCredito.add(expediente.getBdSaldoCredito());
			        	 }
			        	 
			        	 listaExpedienteTemp.add(expediente);
					 }
			         
			         bdSaldoCreditoTotalPrestamo =(bdSaldoCreditoPrestamo.add(bdSaldoCreditoPrestamo)).add(bdSaldoCreditoMora);
			         
			         if (flgAuxiliar && bdSaldoCreditoActividad.equals(new BigDecimal(0)) && bdSaldoCreditoTotalPrestamo.equals(new BigDecimal(0)) && bdSaldoCreditoOrdenCredito.equals(new BigDecimal(0))){
			        	 esValidoCtaExpediente = true;
			        	 listaExpedienteTemp = listaExpediente;
			         }
			         else{
			        	 listaExpedienteTemp = null;
			         }
			         
			          
			         if (esValidoCtaConcepto && esValidoCtaExpediente){
			        	  dtFechaFinDisbled = true;
			        	  beanSolCtaCte.getCuenta().setListaConcepto(listaCtaConcepto);
			        	  beanSolCtaCte.getSolCtaCteTipo().setListaExpediente(listaExpedienteTemp);
			         }else{
			        	      messageValidation ="No puede cambiar la condición: ";
			        	  if (bdSaldoCreditoActividad.compareTo(new BigDecimal(0)) == 1){
			        		  messageValidation = messageValidation+"Socio con monto saldo actividad > 0.";
			        	  }
			        	  else
			        	  if (bdSaldoCreditoPrestamo.compareTo(new BigDecimal(0)) == 1){
			        		  messageValidation = messageValidation+" Socio con monto saldo prestamo  > 0.";
			        		  
			        	  }
			        	  else
			        	  if (bdSaldoCreditoOrdenCredito.compareTo(new BigDecimal(0)) == 1){
			        		  messageValidation = messageValidation+ "Socio con monto orde credito > 0.";
			        	  }
			        	  
				      }
				 
			 }
			 
			 
			
		} catch (EJBFactoryException e) {
			log.error(e);
			e.printStackTrace();
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		} catch (BusinessException e) {
			log.error(e);
			e.printStackTrace();
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		}
		
	}

	public Boolean getDtFechaFinDisbled() {
		return dtFechaFinDisbled;
	}

	public void setDtFechaFinDisbled(Boolean dtFechaFinDisbled) {
		this.dtFechaFinDisbled = dtFechaFinDisbled;
	}
	
	public void limpiarConceptosCta(ValueChangeEvent event){
		
		Integer intCondicionFinal = (Integer)event.getNewValue();
		
		if (intCondicionFinal.equals(Constante.PARAM_T_CONDICIONSOCIO_INA)){
			beanSolCtaCte.getSolCtaCteTipo().getMovimiento().setDtFechaFin(null);
			dtFechaFinDisbled = true;
		}else{
			dtFechaFinDisbled = false;
		}
		
		beanSolCtaCte.getSolCtaCteTipo().setListaExpediente(null);
		beanSolCtaCte.setCuenta(socioCom.getCuenta());
  	    beanSolCtaCte.getCuenta().setListaConcepto(null);
		
	}

	public Archivo getArchivoDocSolicitud1() {
		return archivoDocSolicitud1;
	}

	public void setArchivoDocSolicitud1(Archivo archivoDocSolicitud1) {
		this.archivoDocSolicitud1 = archivoDocSolicitud1;
	}

	public Archivo getArchivoDocSolicitud2() {
		return archivoDocSolicitud2;
	}

	public void setArchivoDocSolicitud2(Archivo archivoDocSolicitud2) {
		this.archivoDocSolicitud2 = archivoDocSolicitud2;
	}

	public String getStrDesSucursal() {
		return strDesSucursal;
	}

	public void setStrDesSucursal(String strDesSucursal) {
		this.strDesSucursal = strDesSucursal;
	}

	public String getStrDesModaliadTipoSocio() {
		return strDesModaliadTipoSocio;
	}

	public void setStrDesModaliadTipoSocio(String strDesModaliadTipoSocio) {
		this.strDesModaliadTipoSocio = strDesModaliadTipoSocio;
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
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			
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
			exc.printStackTrace();
		} 
	
	}

	
	public void descargarArchivo(ActionEvent event)throws Exception{
		try{
			Archivo archivo = (Archivo)event.getComponent().getAttributes().get("archivo");
			descargarArchivo(archivo);
			
		}catch(Exception e){
			throw e;
		}
	}
	
	
	
	
	
	public void descargarArchivo(Archivo archivo)throws Exception{
		try{
			
			GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
		
			TipoArchivo tipoArchivo = generalFacade.getTipoArchivoPorPk(archivo.getId().getIntParaTipoCod());
			
			byte[] buf = new byte[1024];
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			response.setContentType("application/force-download");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + archivo.getStrNombrearchivo() + "\"");
			
			String ruta = tipoArchivo.getStrRuta()+ "\\"+ archivo.getStrNombrearchivo();
			
			DownloadFile.downloadFile(ruta);

//			log.info(ruta);
//			
//			//String realPath = sCon.getRealPath(strRutaActual+"/" + strNombreArchivo);
//			//String realPath = "C:\\Tumi\\ArchivosAdjuntos\\Documentos\\ExpedientePrestamos\\CopiaDNI"+"\\" + strNombreArchivo;
//			String realPath = ruta;
//			
//			
//			
//			File file = new File(realPath);
////			FacesContext.getCurrentInstance().responseComplete();
//			log.info(ruta);
//			long length = file.length();
//			
//			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
//			ServletOutputStream out = response.getOutputStream();
//			response.setContentLength((int)length);
//			while ((in != null) && ((length = in.read(buf)) != -1)) {
//				out.write(buf, 0, (int)length);
//			}
//			in.close();
//			out.flush();
//			out.close();
//			FacesContext.getCurrentInstance().responseComplete();
		}catch(Exception e){
			throw e;
		}
	}

	public String getStrDescEntidadPres() {
		return strDescEntidadPres;
	}

	public void setStrDescEntidadPres(String strDescEntidadPres) {
		this.strDescEntidadPres = strDescEntidadPres;
	}

	public String getStrDesSucursalPres() {
		return strDesSucursalPres;
	}

	public void setStrDesSucursalPres(String strDesSucursalPres) {
		this.strDesSucursalPres = strDesSucursalPres;
	}

	public String getStrDesModaliadTipoSocioPres() {
		return strDesModaliadTipoSocioPres;
	}

	public void setStrDesModaliadTipoSocioPres(String strDesModaliadTipoSocioPres) {
		this.strDesModaliadTipoSocioPres = strDesModaliadTipoSocioPres;
	}

	public List<Tabla> getListaMotivoCambioCondicionLaboral() {
		
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaMotivoCambioCondicionLaboral =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOMOTIVOSOLICITUD), "H");
			   
			  
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		
		return listaMotivoCambioCondicionLaboral;
	}

	public void setListaMotivoCambioCondicionLaboral(
			List<Tabla> listaMotivoCambioCondicionLaboral) {
		this.listaMotivoCambioCondicionLaboral = listaMotivoCambioCondicionLaboral;
	}

	public boolean isHabilitarFormCambioCondicionLaboral() {
		return habilitarFormCambioCondicionLaboral;
	}

	public void setHabilitarFormCambioCondicionLaboral(
			boolean habilitarFormCambioCondicionLaboral) {
		this.habilitarFormCambioCondicionLaboral = habilitarFormCambioCondicionLaboral;
	}

	public Boolean getIntCondicionLaboralDisabled() {
		return intCondicionLaboralDisabled;
	}

	public void setIntCondicionLaboralDisabled(Boolean intCondicionLaboralDisabled) {
		this.intCondicionLaboralDisabled = intCondicionLaboralDisabled;
	}
    
	public Archivo getFileContrato() {
		return fileContrato;
	}
	public void setFileContrato(Archivo fileContrato) {
		this.fileContrato = fileContrato;
	}
    
	public void removeContrato(){
		try{
			/*if (getBeanSolCtaCte().getId().getIntCcobItemsolctacte() != null && archivoDocSolicitudTemp == null){
			    archivoDocSolicitudTemp = archivoDocSolicitud;
			}*/
			fileContrato.setStrNombrearchivo("");
			fileContrato = null;
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			fileUploadController.setObjArchivo(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

		public Boolean getHabilitarDatosLaborales() {
		return habilitarDatosLaborales;
	}

	public void setHabilitarDatosLaborales(Boolean habilitarDatosLaborales) {
		this.habilitarDatosLaborales = habilitarDatosLaborales;
	}

	public Integer getIntCondicionLaboral() {
		return intCondicionLaboral;
	}

	public void setIntCondicionLaboral(Integer intCondicionLaboral) {
		this.intCondicionLaboral = intCondicionLaboral;
	}

	public Integer getIntCondicionLaboralDet() {
		return intCondicionLaboralDet;
	}

	public void setIntCondicionLaboralDet(Integer intCondicionLaboralDet) {
		this.intCondicionLaboralDet = intCondicionLaboralDet;
	}

	public DescuentoIndebidoService getDescuentoIndebidoService() {
		return descuentoIndebidoService;
	}

	public void setDescuentoIndebidoService(
			DescuentoIndebidoService descuentoIndebidoService) {
		this.descuentoIndebidoService = descuentoIndebidoService;
	}

	
 
	 
	public void generarDescuentoIndebido(ActionEvent event){
		
		DescuentoIndebido descIndebido  =  (DescuentoIndebido)event.getComponent().getAttributes().get("item");
		int indece = beanSolCtaCte.getSolCtaCteTipo().getListaDesctoIndebidoOrigen().indexOf(descIndebido);
	  	descIndebido.setIntParaEstadoPagadocod(Constante.PARAM_T_ESTADO_PAGO_PENDIENTE);
	  	descIndebido.setChecked(true);
	  	beanSolCtaCte.getSolCtaCteTipo().getListaDesctoIndebidoOrigen().set(indece, descIndebido);
		
		
	 
	
  }

	public boolean isHabilitarFormDescuentoIndebido() {
		return habilitarFormDescuentoIndebido;
	}

	public void setHabilitarFormDescuentoIndebido(
			boolean habilitarFormDescuentoIndebido) {
		this.habilitarFormDescuentoIndebido = habilitarFormDescuentoIndebido;
	}
	
	public void buscarDesctoIndebido(ActionEvent event){
		
		
		if (habilitarFormDescuentoIndebido){
			nuevoDescuentoIndebido(beanSolCtaCte.getSolCtaCteTipo());
		}else{
			verDescuentoIndebido(beanSolCtaCte.getSolCtaCteTipo());
		}
	
		
		List<DescuentoIndebido> listaOrigen   = new ArrayList<DescuentoIndebido>();
		List<DescuentoIndebido> listaPrestamo = new ArrayList<DescuentoIndebido>();
		
		
		 Integer  intEstadoPago = getIntEstadoPago();
		   List<DescuentoIndebido> listaUno=  beanSolCtaCte.getSolCtaCteTipo().getListaDesctoIndebidoOrigen();
		   if (listaUno != null)
		   for (DescuentoIndebido descuentoIndebido : listaUno) {
			   
			     if (descuentoIndebido.getIntParaEstadoPagadocod().equals(intEstadoPago)){
			    	 listaOrigen.add(descuentoIndebido);
			    	 
			     }
			     
			}
		   
		
		   
		 
		   beanSolCtaCte.getSolCtaCteTipo().setListaDesctoIndebidoOrigen(listaOrigen);
		   
		   List<DescuentoIndebido> listaDos=  beanSolCtaCte.getSolCtaCteTipo().getListaDesctoIndebidoPrestamo();
		   
		   
		   if (listaDos != null)
		   for (DescuentoIndebido descuentoIndebido : listaDos) {
			   
			     if (descuentoIndebido.getIntParaEstadoPagadocod().equals(intEstadoPago)){
			    	 listaPrestamo.add(descuentoIndebido);
			     }
			     
			}
		   
		   beanSolCtaCte.getSolCtaCteTipo().setListaDesctoIndebidoPrestamo(listaPrestamo);
			  
			  
	}

	public Integer getIntEstadoPago() {
		return intEstadoPago;
	}

	public void setIntEstadoPago(Integer intEstadoPago) {
		this.intEstadoPago = intEstadoPago;
	}
	
	
    public List<Tabla> getListaMotivoTransferencia() {
		
		try{		
			   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			   listaMotivoTransferencia =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOMOTIVOSOLICITUD), "A");
			   
			  
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		
		return listaMotivoTransferencia;
	}

	public Boolean getHabilitarFormTransferencia() {
		return habilitarFormTransferencia;
	}

	public void setHabilitarFormTransferencia(Boolean habilitarFormTransferencia) {
		this.habilitarFormTransferencia = habilitarFormTransferencia;
	}

	public Boolean getRadioEntreCuentas() {
		return radioEntreCuentas;
	}

	public void setRadioEntreCuentas(Boolean radioEntreCuentas) {
		this.radioEntreCuentas = radioEntreCuentas;
	}

	public Boolean getRadioEntreConceptos() {
		return radioEntreConceptos;
	}

	public void setRadioEntreConceptos(Boolean radioEntreConceptos) {
		this.radioEntreConceptos = radioEntreConceptos;
	}
	public void seleccionarTipoTransferencia(ValueChangeEvent event) {
		log.info("-------------------------------------Debugging CuentaCteController.seleccionarTipoTransferencia-------------------------------------");
		
		Integer intTipoTransferencia = null;
		List<Tabla> listaDetCondiLaboral = null;
		
		//Declaracion
		bgMontoSaldoTotalAbono = null;
		bgMontoSaldoAportaciones = null;
		bgMontoTransferencia = null;
		getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
		radioEntreConceptos = true;
		beanListaExpediente = null;
		messageValidation = "";
		socioComGarante = null;
		strDescEntidadGarante = "";
	  
		esEditableMontoSaldoAbono = true;
		
		try {
			intTipoTransferencia = (Integer)event.getNewValue();
			if(intTipoTransferencia != null && intTipoTransferencia != 0){
				if (intTipoTransferencia.equals(Constante.PARAM_T_TIPOTRANSFERENCIA_FALLECIMIENTO_SOCIO)){
					boolean result = validarCasoFallecimientoSocio();
					
				}
			}
		
			if(intTipoTransferencia != null && intTipoTransferencia != 0){
				if (intTipoTransferencia.equals(Constante.PARAM_T_TIPOTRANSFERENCIA_SOCIO_NO_LABORA)){
					boolean result = validarCasoSocioNoLabora();
					
					
				}
			}
		
			if(intTipoTransferencia != null && intTipoTransferencia != 0){
				if (intTipoTransferencia.equals(Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA)){
					boolean result = validarCasoLicencia();
					
				}
			}
			
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListDetCondicionLaboral(listaDetCondiLaboral);
		habilitarDatosLaborales = false;		
	}
	
	public boolean validarCasoLicencia() throws EJBFactoryException,BusinessException{
		
		log.info("-------------------------------ValidarCasoLicencia----------------------------------");
		ConceptoFacadeRemote   conceptoFacade             = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		CreditoFacadeRemote    creditoFacade              = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
		GeneralFacadeRemote    generalFacade              = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
		CuentacteFacadeRemote  cuentacteFacade            = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class); 
		SolicitudCtaCteBloqueoBO boSolicitudCtaCteBloqueo = (SolicitudCtaCteBloqueoBO)TumiFactory.get(SolicitudCtaCteBloqueoBO.class);
		SolicitudCtaCteTipoBO    boSolicitudCtaCteTipo    = (SolicitudCtaCteTipoBO)TumiFactory.get(SolicitudCtaCteTipoBO.class);
		TransferenciaBO          boTransferencia          = (TransferenciaBO)TumiFactory.get(TransferenciaBO.class);
		PersonaFacadeRemote      personaFacade            = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
		
		//Declaracion
		bgMontoSaldoTotalAbono = null;
		bgMontoSaldoAportaciones = null;
		bgMontoTransferencia = null;
		getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
		radioEntreConceptos = true;
		beanListaExpediente = null;
		messageValidation = "";
		socioComGarante = null;
		strDescEntidadGarante = "";
	  
		esEditableMontoSaldoAbono = true;
		BigDecimal montoSaldoPrestamo       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCredito        = new BigDecimal(0);
		BigDecimal montoSaldoActividad      	 = new BigDecimal(0);
		BigDecimal montoSaldoFondoRetiro    	 = new BigDecimal(0);
		BigDecimal montoSaldoAportaciones        = new BigDecimal(0); 
		BigDecimal montoSaldoPrestamoSoles       = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCreditoSoles   = new BigDecimal(0);
		
		BigDecimal montoTotalSaldoAbono       	 = new BigDecimal(0);
		boolean esValidoCaso = true;
		
		Cuenta cuenta = socioCom.getCuenta();
		Integer idEmpresa = cuenta.getId().getIntPersEmpresaPk();
		Integer idCuenta  = cuenta.getId().getIntCuenta();



		//Validaciones fechaDocumento es la fecha futura 
		if (!UtilCobranza.esMayorOIgualFechaActual(getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento())){
			messageValidation ="La fecha de transferencia debe ser mayor o igual a la fecha actual.";
			return false;
		}
		
		//Validacion si el socio ya ha hecho transferencia en este mes y si ya fallecio	
		
		Integer empresasolctacte                                  = beanSolCtaCte.getId().getIntEmpresasolctacte();
		Integer intCsocCuenta                                     = beanSolCtaCte.getIntCsocCuenta();	
		Integer perPersona                                        = beanSolCtaCte.getIntPersPersona();
		List<SolicitudCtaCteTipo> lstSolicitudesTipo              = null;		
		List<Transferencia> lstTransferencias         			  = null;
		List<SolicitudCtaCte> listasol 							  = null;
		String anioa          = UtilCobranza.obtieneAnio(getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());		 
		String  mesa          = UtilCobranza.obtieneMesCadena(getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());		
		Integer mesanioActual = new Integer(anioa+""+mesa);

		Persona persona = personaFacade.getPersonaPorPK(perPersona);
		if(persona.getIntEstadoCod()==2){
			esValidoCaso = false;			
		}
		if (!esValidoCaso){
			messageValidation ="El socio ya fallecio.";
			return false;
		}
		
		 
		listasol = cuentacteFacade.SolicitudesTipoTransf(empresasolctacte, intCsocCuenta);	
		 Integer cont = 0;		 
		 if (listasol != null && !listasol.isEmpty()){
			for(SolicitudCtaCte sol:listasol){
				lstSolicitudesTipo =sol.getListaSolCtaCteTipo();					
				for(SolicitudCtaCteTipo soltipo:lstSolicitudesTipo){	
					if(soltipo.getIntMotivoSolicitud() != null)
					if(soltipo.getIntMotivoSolicitud().equals(Constante.PARAM_T_TIPOTRANSFERENCIA_LICENCIA)){
						lstTransferencias = soltipo.getListaTransferencias();
						for(Transferencia transf:lstTransferencias){				
							if(transf.getIntTranPeriodo().equals(mesanioActual)){					
								cont++;
							}				
						}
					}
				}
				
			}
		 }
		
//		if(cont.compareTo(1)==1 || cont.compareTo(1)==0){
//			messageValidation ="Para este socio ya se hizo  transferencia en este mes.";
//			return false;
//		}		
		//Termino de validacion de si el socio ya hizo alguna transferencia este mes.
		
		
		
		List<BloqueoCuenta> listaBlqCta =  conceptoFacade.getListaBloqueoCuentaPorNroCuenta(idEmpresa, idCuenta);
		for (BloqueoCuenta bloqueoCuenta : listaBlqCta) {
			if (bloqueoCuenta.getTsFechaInicio() != null && bloqueoCuenta.getTsFechaFin() != null)
			if (UtilCobranza.esDentroRangoFechas(bloqueoCuenta.getTsFechaInicio(), bloqueoCuenta.getTsFechaFin(), getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento())){
				
				Integer intPersEmpresaPk = idEmpresa;
				Integer intTipoSolicitud = Constante.PARAM_T_TIPOSOLCITUD_LICENCIA;
				
				List<SolicitudCtaCteBloqueo> lista = boSolicitudCtaCteBloqueo.getListaPorTipoSolicitud(intPersEmpresaPk, intTipoSolicitud);
				for (SolicitudCtaCteBloqueo solicitudCtaCteBloqueo : lista) {
					 if(solicitudCtaCteBloqueo.getIntCmovItemblcu().equals(bloqueoCuenta.getIntItemBloqueoCuenta())){
						 esValidoCaso = true;
						 break;
					 }
				}
			}
			else{
				Timestamp fFin= null;
				fFin = bloqueoCuenta.getTsFechaFin();
				java.sql.Date date =  new java.sql.Date(fFin.getTime());
				String fechaFi = "";
				  fechaFi= UtilCobranza.convierteDateAString(date);				
				messageValidation ="La licencia de socio finalizó el  "+fechaFi;
				return false;
			}
			
		}
		
		if (!esValidoCaso){
			messageValidation ="El socio no tiene una licencia o suspencion activa.";
			return false;
		}
		else{
		
		//Lista Conceptos de cuenta.
//		List<Expediente>  listaExpediente =  conceptoFacade.getListaExpedienteConSaldoPorEmpresaYcuenta(idEmpresa, idCuenta);
		List<Expediente>  listaExpediente =  conceptoFacade.getListaExpedientePorEmpresaYCta(idEmpresa, idCuenta);
		List<Expediente>  listaExpedienteTemp =  new ArrayList<Expediente>();
		
		for (Expediente expediente : listaExpediente) {
			
			CreditoId creditoId = new CreditoId();
			creditoId.setIntItemCredito(expediente.getIntItemCredito());
			creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
			creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
			Credito credito = null;
			credito = creditoFacade.getCreditoPorIdCredito(creditoId);
			
			if (credito == null){
				 esValidoCaso = false;
				 messageValidation ="No existe la configuración de crédito.";
			    throw new BusinessException("No existe la configuración de crédito.");
			   
			}
			
			if (credito.getIntParaMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
				   expediente.setBdSaldoCreditoSoles(expediente.getBdSaldoCredito()) ;
				   expediente.setBdSaldoInteresSoles(expediente.getBdSaldoInteres() != null?expediente.getBdSaldoInteres():new BigDecimal(0));
				   expediente.setBdSaldoMoraSoles(expediente.getBdSaldoMora()!= null?expediente.getBdSaldoMora():new BigDecimal(0));
				   
			}else{
				   TipoCambio tipoCambio =   generalFacade.getTipoCambioActualPorClaseYMoneda(idEmpresa,Constante.PARAM_T_TIPOCAMBIO_CLASE_BANCARIA,credito.getIntParaMonedaCod());
				   BigDecimal bdSaldoCreditoSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoCredito());
				   expediente.setBdSaldoCreditoSoles(bdSaldoCreditoSoles) ;
				   BigDecimal bdSaldoInteresSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoInteresSoles());
				   expediente.setBdSaldoInteresSoles(bdSaldoInteresSoles);
				   BigDecimal bdSaldoMoraSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoMoraSoles());
				   expediente.setBdSaldoMoraSoles(bdSaldoMoraSoles);
			}
				  
			
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)){
				log.info("-------------------------------PARAM_T_TIPO_CREDITO_PRESTAMO----------------------------------");
				montoSaldoPrestamo = montoSaldoPrestamo.add(expediente.getBdSaldoCredito());
				 montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(expediente.getBdSaldoCreditoSoles().add(expediente.getBdSaldoInteresSoles()).add(expediente.getBdSaldoMoraSoles()));
				 expediente.setStrDescripcion("Amortización");
				 BigDecimal bdMontoSaldoDetalle = obtieneMtoSaldoDettalleCronPorCptoGenaralAlPeriodo(expediente, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
				 expediente.setBdMontoSaldoDetalle(bdMontoSaldoDetalle);
				 listaExpedienteTemp.add(expediente);
				 //interes
				 if (expediente.getBdSaldoInteres() != null && expediente.getBdSaldoInteres().compareTo(new BigDecimal(0)) == 1){
					 log.info("-------------------------------EXPEDIENTE INTERES ----------------------------------");
					 Expediente expedienteInteres = new Expediente();
					 expedienteInteres.setId(expediente.getId());
					 expedienteInteres.setIntPersEmpresaCreditoPk(expediente.getIntPersEmpresaCreditoPk());
					 expedienteInteres.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					 expedienteInteres.setIntItemCredito(expediente.getIntItemCredito());
					 expedienteInteres.setBdSaldoInteresSoles(expediente.getBdSaldoInteresSoles());
					 expedienteInteres.setBdSaldoInteres(expediente.getBdSaldoInteres());
					 expedienteInteres.setBdSaldoCreditoSoles(expediente.getBdSaldoInteresSoles());
					 expedienteInteres.setBdSaldoCredito(expediente.getBdSaldoInteresSoles());
					 expedienteInteres.setStrDescripcion("Interes");					
					 BigDecimal bdMontoSaldoDet = obtieneInteresDiario(expedienteInteres);
					 expedienteInteres.setBdMontoSaldoDetalle(bdMontoSaldoDet);
					 listaExpedienteTemp.add(expedienteInteres);
				 }
				//mora
				 if (expediente.getBdSaldoMora() != null && expediente.getBdSaldoMora().compareTo(new BigDecimal(0)) == 1){
					 log.info("-------------------------------EXPEDIENTE mora ----------------------------------");
					 Expediente expedienteMora = new Expediente();
					 expedienteMora.setId(expediente.getId());
					 expedienteMora.setIntPersEmpresaCreditoPk(expediente.getIntPersEmpresaCreditoPk());
					 expedienteMora.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					 expedienteMora.setIntItemCredito(expediente.getIntItemCredito());
					 expedienteMora.setBdSaldoMoraSoles(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setBdSaldoMora(expediente.getBdSaldoMora());
					 expedienteMora.setBdSaldoCreditoSoles(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setBdSaldoCredito(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setStrDescripcion("Mora");
					 BigDecimal bdMontoSaldoDet = obtieneMtoSaldoDettalleCronPorCptoGenaralAlPeriodo(expedienteMora, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
					 expedienteMora.setBdMontoSaldoDetalle(bdMontoSaldoDet);
					 listaExpedienteTemp.add(expedienteMora);
				 }
			}
			else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)){
				log.info("------------------------------- PARAM_T_TIPO_CREDITO_ORDENCREDITO ----------------------------------");
				montoSaldoOrdenCredito      = montoSaldoOrdenCredito.add(expediente.getBdSaldoCredito());
				montoSaldoOrdenCreditoSoles = montoSaldoOrdenCreditoSoles.add(expediente.getBdSaldoCreditoSoles().add(expediente.getBdSaldoInteresSoles()).add(expediente.getBdSaldoMoraSoles()));
				 expediente.setStrDescripcion("Amortización");
				 BigDecimal bdMontoSaldoDetalle = obtieneMtoSaldoDettalleCronPorCptoGenaralAlPeriodo(expediente, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
				 expediente.setBdMontoSaldoDetalle(bdMontoSaldoDetalle);
				
				 listaExpedienteTemp.add(expediente);
				 //Interes
				 if (expediente.getBdSaldoInteres() != null && expediente.getBdSaldoInteres().compareTo(new BigDecimal(0)) == 1){
					 log.info("------------------------------- PARAM_T_TIPO_CREDITO_ORDENCREDITO Interes----------------------------------");
					 Expediente expedienteInteres = new Expediente();
					 expedienteInteres.setId(expediente.getId());
					 expedienteInteres.setIntPersEmpresaCreditoPk(expediente.getIntPersEmpresaCreditoPk());
					 expedienteInteres.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					 expedienteInteres.setIntItemCredito(expediente.getIntItemCredito());
					 expedienteInteres.setBdSaldoInteresSoles(expediente.getBdSaldoInteresSoles());
					 expedienteInteres.setBdSaldoInteres(expediente.getBdSaldoInteres());
					 expedienteInteres.setBdSaldoCredito(expediente.getBdSaldoInteres());
					 expedienteInteres.setStrDescripcion("Interes");
//					 BigDecimal bdMontoSaldoDet = obtieneMtoSaldoDettalleCronPorCptoGenaralAlPeriodo(expedienteInteres, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
					 BigDecimal bdMontoSaldoDet = obtieneInteresDiario(expedienteInteres);
					 expedienteInteres.setBdMontoSaldoDetalle(bdMontoSaldoDet);
				     listaExpedienteTemp.add(expedienteInteres);
				 }    
				 //Mora
				 if (expediente.getBdSaldoMora() != null && expediente.getBdSaldoMora().compareTo(new BigDecimal(0)) == 1){
					 log.info("------------------------------- PARAM_T_TIPO_CREDITO_ORDENCREDITO Mora----------------------------------");
					 Expediente expedienteMora = new Expediente();
					 expedienteMora.setId(expediente.getId());
					 expedienteMora.setIntPersEmpresaCreditoPk(expediente.getIntPersEmpresaCreditoPk());
					 expedienteMora.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					 expedienteMora.setIntItemCredito(expediente.getIntItemCredito());
					 expedienteMora.setBdSaldoMoraSoles(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setBdSaldoMora(expediente.getBdSaldoMora());
					 expedienteMora.setBdSaldoCredito(expediente.getBdSaldoMora());
					 expedienteMora.setStrDescripcion("Mora");
					 BigDecimal bdMontoSaldoDet = obtieneMtoSaldoDettalleCronPorCptoGenaralAlPeriodo(expedienteMora, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
					 expedienteMora.setBdMontoSaldoDetalle(bdMontoSaldoDet);
					 listaExpedienteTemp.add(expedienteMora);
				 }
			}
			else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)){
				log.info("-------------------------------PARAM_T_TIPO_CREDITO_ACTIVIDAD----------------------------------");
				montoSaldoActividad = montoSaldoActividad.add(expediente.getBdSaldoCredito());
			}
		}
		
		List<CuentaConcepto>  listaCuentaCpto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuenta.getId());
		
		
		
		for (CuentaConcepto cuentaConcepto : listaCuentaCpto) {
			log.info("listaCuentaCpto----------------->"+listaCuentaCpto.size());
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
				log.info("-------------------------------CAPTACION_FDO_RETIRO----------------------------------");
				//FDO RETIRO
				 Expediente expedienteMant = new Expediente();
				 ExpedienteId id = new ExpedienteId();
				 id.setIntItemExpediente(cuentaConcepto.getId().getIntItemCuentaConcepto());
				 

				 expedienteMant.setId(id);
//				 BigDecimal montoSaldo= calcularMontoSaldoPorCptoGeneral(cuentaConcepto,Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO);
				 BigDecimal montoSaldo = cuentaConcepto.getBdSaldo();
				 expedienteMant.setBdSaldoCreditoSoles(montoSaldo);
				 expedienteMant.setBdSaldoCredito(montoSaldo);
				 expedienteMant.setBdSaldoMoraSoles(montoSaldo);
				 expedienteMant.setBdSaldoMora(montoSaldo);
				 expedienteMant.setStrDescripcion("Fondo de Retiro");
//				 BigDecimal bdMontoSaldoDetalle = calcularMontoSaldoDeCtaCptoPorCptoGeneralAlPeriodo(cuentaConcepto,Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
				 BigDecimal bdMontoSaldoDetalle = calcularDelConceptoPago(cuentaConcepto);
				 expedienteMant.setBdMontoSaldoDetalle(bdMontoSaldoDetalle);
				 
				 
				 if (montoSaldo.compareTo(new BigDecimal(0)) == 1){
					 listaExpedienteTemp.add(expedienteMant);
					 montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(montoSaldo);
				 }
			}
			else
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_MANT_CUENTA)){
				log.info("-------------------------------CAPTACION_MANT_CUENTA----------------------------------"); 
				//MANT CUENTA
				 Expediente expedienteMant = new Expediente();
				 ExpedienteId id = new ExpedienteId();
				 id.setIntItemExpediente(cuentaConcepto.getId().getIntItemCuentaConcepto());
				 expedienteMant.setId(id);
				 
//				 BigDecimal montoSaldoMant = calcularMontoSaldoPorCptoGeneral(cuentaConcepto,Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO);
				 BigDecimal montoSaldoMant = cuentaConcepto.getBdSaldo();
				 expedienteMant.setBdSaldoCreditoSoles(montoSaldoMant);
				 expedienteMant.setBdSaldoCredito(montoSaldoMant);
				 expedienteMant.setBdSaldoMoraSoles(montoSaldoMant);
				 expedienteMant.setBdSaldoMora(montoSaldoMant);
				 expedienteMant.setStrDescripcion("Mantenimiento Cuenta");
//				 BigDecimal bdMontoSaldoDetalle = calcularMontoSaldoDeCtaCptoPorCptoGeneralAlPeriodo(cuentaConcepto,Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
				 BigDecimal bdMontoSaldoDetalle = calcularDelConceptoPago(cuentaConcepto);
				 expedienteMant.setBdMontoSaldoDetalle(bdMontoSaldoDetalle);
				 
				 if (montoSaldoMant.compareTo(new BigDecimal(0)) == 1){
					 listaExpedienteTemp.add(expedienteMant);
					 montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(montoSaldoMant);
				 }
			}
			else
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_SEPELIO)){
					log.info("-------------------------------CAPTACION_FDO_SEPELIO----------------------------------"); 
					//FONDO SEPELIO
					 Expediente expedienteMant = new Expediente();
					 ExpedienteId id = new ExpedienteId();
					 id.setIntItemExpediente(cuentaConcepto.getId().getIntItemCuentaConcepto());
					 expedienteMant.setId(id);
					 
					 
					 BigDecimal montoSaldo = cuentaConcepto.getBdSaldo();					 
					 expedienteMant.setBdSaldoCreditoSoles(montoSaldo);
					 expedienteMant.setBdSaldoCredito(montoSaldo);
					 expedienteMant.setBdSaldoMoraSoles(montoSaldo);
					 expedienteMant.setBdSaldoMora(montoSaldo);
					 expedienteMant.setStrDescripcion("Fondo de Sepelio");
//					 BigDecimal bdMontoSaldoDetalle = calcularMontoSaldoDeCtaCptoPorCptoGeneralAlPeriodo(cuentaConcepto,Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
					 BigDecimal bdMontoSaldoDetalle = calcularDelConceptoPago(cuentaConcepto);
					 expedienteMant.setBdMontoSaldoDetalle(bdMontoSaldoDetalle);
					 
					 if (montoSaldo.compareTo(new BigDecimal(0)) == 1){
						 listaExpedienteTemp.add(expedienteMant);
						 montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(montoSaldo);
					 }
			}	
		
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_APORTACIONES)){
				//Aportaciones
				log.info("-------------------------------CAPTACION_APORTACIONES----------------------------------"); 
				montoSaldoAportaciones = cuentaConcepto.getBdSaldo();
				bgMontoSaldoAportaciones  = montoSaldoAportaciones;
				Expediente expedienteAporta = new Expediente();
				 ExpedienteId id = new ExpedienteId();
				 id.setIntItemExpediente(Constante.CAPTACION_APORTACIONES);
				 expedienteAporta.setChecked(true);
				 expedienteAporta.setId(id); 
				 expedienteAporta.setBdMontoSaldoDetalle(new BigDecimal(0));
				 expedienteAporta.setBdSaldoCreditoSoles(new BigDecimal(0));
				 expedienteAporta.setBdSaldoCredito(new BigDecimal(0));
				 expedienteAporta.setStrDescripcion("Aporte");
				 listaExpedienteTemp.add(expedienteAporta);
			}
				
		}
		if (listaExpedienteTemp == null && listaExpedienteTemp.size() == 0){
			messageValidation ="No hay montos a abonar.";
		}
		else{
				listaExpedienteTemp = asignarOrdenPrioridadPorConceptoGeneral(listaExpedienteTemp);
				//asignarMontoAbonoSegunMontoSaldoDetyOrdenPrioridad(listaExpedienteTemp,bgMontoTransferencia);
				
				for (Expediente exp : listaExpedienteTemp) {
					exp.setBdMontoAbono(new BigDecimal(0));
					int index = listaExpedienteTemp.indexOf(exp);
					listaExpedienteTemp.set(index, exp);
				} 
				
				bgMontoTransferencia = new BigDecimal(0);
				bgMontoSaldoTotalAbono = bgMontoTransferencia;
				
				//ordenamos la lista por orden de prioridad  
		        Collections.sort(listaExpedienteTemp, new Comparator() {  
		            public int compare(Object o1, Object o2) {  
		                Expediente e1 = (Expediente) o1;  
		                Expediente e2 = (Expediente) o2;  
		                if (e1.getIntOrdenPrioridad() == null)
		                {	
		                	e1.setIntOrdenPrioridad(88);
		                }
		                
		                if (e2.getIntOrdenPrioridad() == null)
		                {	
		                	e2.setIntOrdenPrioridad(88);
		                }
		                return e1.getIntOrdenPrioridad().compareTo(e2.getIntOrdenPrioridad());
		            }  
		        });  
			
			
			beanListaExpediente = listaExpedienteTemp;
			
			Date fechaTrans = beanSolCtaCte.getSolCtaCteTipo().getDtFechaDocumento();
			
			String mes  = UtilCobranza.obtieneMesCadena(fechaTrans);
			String anio = UtilCobranza.obtieneAnio(fechaTrans);
			
			int intMes  = Integer.parseInt(mes);
			
			String meses[] = {"","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Setiembre","Octubre","Noviembre","Diciembre"};
			
			    strPeriodoTrans = meses[intMes]+"  "+anio+" : "+anio+mes;
		 }
		
		}
		
		return esValidoCaso;
	}
	
	public void repartirMontoCargoAlMontoAbono(){
		log.info("----------cuentaCteController.repartirMontoCargoAlMontoAbono----------");
		
	 try{
		
		  BigDecimal montoCargo = new BigDecimal(0);
		  for (Expediente exp : beanListaExpediente) {
			if (exp.getChecked() != null && exp.getChecked()){
				montoCargo = exp.getBdSaldoCreditoSoles();
				break;
			}
		  }
		  
		  if (montoCargo.compareTo(bgMontoSaldoAportaciones) == 1){
			 messageValidation = "El Monto Cargo ("+montoCargo+") no debe ser mayor que el Monto Aporte.";
				 
			 for (Expediente exp : beanListaExpediente) {
				  exp.setBdMontoAbono(new BigDecimal(0));
				  int index = beanListaExpediente.indexOf(exp);
				  beanListaExpediente.set(index, exp);
			 }
			 
			 bgMontoTransferencia   = new BigDecimal(0);
			 bgMontoSaldoTotalAbono = bgMontoTransferencia;
				
		  }
		  else{
			  messageValidation  = "";
			  asignarMontoAbonoSegunMontoSaldoDetyOrdenPrioridad(beanListaExpediente, montoCargo);
			  bgMontoTransferencia = sumaMontoTotalAbono(beanListaExpediente);
			  bgMontoSaldoTotalAbono = bgMontoTransferencia;
			  
			  
			  for (Expediente exp : beanListaExpediente) {
					if (exp.getChecked() != null && exp.getChecked()){
						int index = beanListaExpediente.indexOf(exp);
						exp.setBdSaldoCreditoSoles(bgMontoTransferencia);
						beanListaExpediente.set(index, exp);
						break;
					}
			  }
		  }
	
		  
	    }catch (EJBFactoryException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}catch (BusinessException e) {
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}catch(Exception e){
			  e.printStackTrace();
			  log.error(e);
			  FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);	
		}
	}
	
	
	private BigDecimal sumaMontoTotalAbono(List<Expediente> lista){
		BigDecimal sumMontoTotalAbono = new BigDecimal(0);
		for (Expediente expediente : lista) {
			if (expediente.getBdMontoAbono() != null){
				
				sumMontoTotalAbono = sumMontoTotalAbono.add(expediente.getBdMontoAbono());
			}
		}
		return sumMontoTotalAbono;
	}
	
private boolean validarCasoSocioNoLabora() throws EJBFactoryException,BusinessException{
	log.info("----------cuentaCteController.validarCasoSocioNoLabora ----------");
		ConceptoFacadeRemote   conceptoFacade         = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		PrevisionFacadeRemote  previsionFacade        = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
		DescuentoIndebidoBO    boDescuentoIndebido    = (DescuentoIndebidoBO)TumiFactory.get(DescuentoIndebidoBO.class);
		CreditoFacadeRemote    creditoFacade          = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
		GeneralFacadeRemote    generalFacade          = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
		EstructuraFacadeRemote estructuraFacade       = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class); 
		   
		
		//Declaracion
		bgMontoSaldoTotalAbono = null;
		bgMontoSaldoAportaciones = null;
		bgMontoTransferencia = null;
		getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
		radioEntreConceptos = true;
		beanListaExpediente = null;
		messageValidation = "";
		socioComGarante = null;
		strDescEntidadGarante = "";
	  
		esEditableMontoSaldoAbono = true;
		BigDecimal montoSaldoPrestamo       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCredito        = new BigDecimal(0);
		BigDecimal montoSaldoActividad      	 = new BigDecimal(0);
		BigDecimal montoSaldoFondoRetiro    	 = new BigDecimal(0);
		//BigDecimal montoSaldoFondoSepelio   	 = new BigDecimal(0);
		BigDecimal montoSaldoAportaciones   	 = new BigDecimal(0);
		
		BigDecimal montoSaldoDescuentoIndebido   = new BigDecimal(0);
		
		BigDecimal montoSaldoPrestamoSoles       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCreditoSoles        = new BigDecimal(0);
		
		BigDecimal montoTotalSaldoAbono       	 = new BigDecimal(0);
		BigDecimal bdMontoAbonoInteres			 = new BigDecimal(0);
		boolean esValidoCaso = true;
		
		Cuenta cuenta = socioCom.getCuenta();
		Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
		Integer idEmpresa = cuenta.getId().getIntPersEmpresaPk();
		Integer idCuenta  = cuenta.getId().getIntCuenta();
		
		//Validaciones
		Documento documento = socioCom.getPersona().getDocumento();
		String strNroDocIdentidad = documento.getStrNumeroIdentidad();
		SocioComp socioComp = estructuraFacade.getSocioNatuPorLibElectoral(strNroDocIdentidad);
		if (socioComp != null && socioComp.getPadron() != null){
			 String [] meses = {"","01","02","03","04","05","06","07","08","09","10","11","12"};
			 String mes = "";
			 for (int i = 0; i < meses.length; i++) {
				if (i == socioComp.getPadron().getId().getIntMes()){
					mes =  meses[i];
				}
			 } 
			 messageValidation = "El socio se encuentra ubicado en el padrón de periodo ("+socioComp.getPadron().getId().getIntPeriodo()+""+mes+") no se puede realizar este tipo de transferencia.";
    		 esValidoCaso = false;
			 return false;
		}
		
		
		List<Expediente>  listaExpediente =  conceptoFacade.getListaExpedientePorEmpresaYCta(idEmpresa, idCuenta);
		List<Expediente>  listaExpedienteTemp =  new ArrayList<Expediente>();
		
		for (Expediente expediente : listaExpediente) {
			
			CreditoId creditoId = new CreditoId();
			creditoId.setIntItemCredito(expediente.getIntItemCredito());
			creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
			creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
			Credito credito = null;
			credito = creditoFacade.getCreditoPorIdCredito(creditoId);
			
			if (credito == null){
				 esValidoCaso = false;
				 messageValidation ="No existe la configuración de crédito.";
			    throw new BusinessException("No existe la configuración de crédito.");
			   
			}
			
			
			
			if (credito.getIntParaMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
				   expediente.setBdSaldoCreditoSoles(expediente.getBdSaldoCredito()) ;
				   expediente.setBdSaldoInteresSoles(expediente.getBdSaldoInteres() != null?expediente.getBdSaldoInteres():new BigDecimal(0));
				   expediente.setBdSaldoMoraSoles(expediente.getBdSaldoMora()!= null?expediente.getBdSaldoMora():new BigDecimal(0));
				   
			}else{
				   TipoCambio tipoCambio =   generalFacade.getTipoCambioActualPorClaseYMoneda(idEmpresa,Constante.PARAM_T_TIPOCAMBIO_CLASE_BANCARIA,credito.getIntParaMonedaCod());
				   BigDecimal bdSaldoCreditoSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoCredito());
				   expediente.setBdSaldoCreditoSoles(bdSaldoCreditoSoles) ;
				   BigDecimal bdSaldoInteresSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoInteresSoles());
				   expediente.setBdSaldoInteresSoles(bdSaldoInteresSoles);
				   BigDecimal bdSaldoMoraSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoMoraSoles());
				   expediente.setBdSaldoMoraSoles(bdSaldoMoraSoles);
			}
				  
			
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)){
				log.info("------------------------------PARAM_T_TIPO_CREDITO_PRESTAMO  ------------------------------------------------"); 
				montoSaldoPrestamo = montoSaldoPrestamo.add(expediente.getBdSaldoCredito());
								
				 expediente.setStrDescripcion("Amortización");
				 
				 BigDecimal bdMontoSaldoDetalle = obtieneMtoSaldoDettalleCronPorCptoGenaralAlPeriodo(expediente, getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento());
				 log.info("SaldoCreditoSoles de amortizacion Tamara------->"+bdMontoSaldoDetalle);
				 expediente.setBdSaldoCreditoSoles(expediente.getBdSaldoCreditoSoles());
				 expediente.setBdMontoAbono(bdMontoSaldoDetalle);
//				 expediente.setBdMontoSaldoDetalle(bdMontoSaldoDetalle);
//				 expediente.setBdSaldoCreditoSoles(bdMontoSaldoDetalle);
				 montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(bdMontoSaldoDetalle);
				 listaExpedienteTemp.add(expediente);
				 //interes
				 if (expediente.getBdSaldoInteres() != null && expediente.getBdSaldoInteres().compareTo(new BigDecimal(0)) == 1){
					 log.info("------------------------------Interes Interes Interes-------------------------------------------------------------");
					 log.info(expediente);
					 Expediente expedienteInteres = new Expediente();
					 expedienteInteres.setId(expediente.getId());
					 expedienteInteres.setIntPersEmpresaCreditoPk(expediente.getIntPersEmpresaCreditoPk());
					 expedienteInteres.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					 expedienteInteres.setIntItemCredito(expediente.getIntItemCredito());
					 expedienteInteres.setBdSaldoInteresSoles(expediente.getBdSaldoInteresSoles());
					 expedienteInteres.setBdSaldoInteres(expediente.getBdSaldoInteres());
					 expedienteInteres.setBdSaldoCreditoSoles(expediente.getBdSaldoInteresSoles());
					 expedienteInteres.setBdSaldoCredito(expediente.getBdSaldoInteresSoles());
					 bdMontoAbonoInteres = obtieneInteresDiario(expedienteInteres);
					 expedienteInteres.setBdMontoAbono(bdMontoAbonoInteres);	
//					 expediente.setBdMontoSaldoDetalle(bdMontoAbonoInteres);
					 expedienteInteres.setStrDescripcion("Interes");
					 if (bdMontoAbonoInteres.compareTo(new BigDecimal(0)) == 1){
						 listaExpedienteTemp.add(expedienteInteres);
					 }					 
					 log.info("bdMontoAbonoInteres------->"+bdMontoAbonoInteres);
					 montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(bdMontoAbonoInteres);					 
					 log.info("montoSaldoPrestamoSoles agregado interes------->"+montoSaldoPrestamoSoles);
					 
				 }
				//mora
				 if (expediente.getBdSaldoMora() != null && expediente.getBdSaldoMora().compareTo(new BigDecimal(0)) == 1){
					 log.info("------------------------------Mora Mora Mora-------------------------------------------------------------");
					log.info(expediente);
					 Expediente expedienteMora = new Expediente();
					 expedienteMora.setId(expediente.getId());
					 expedienteMora.setIntPersEmpresaCreditoPk(expediente.getIntPersEmpresaCreditoPk());
					 expedienteMora.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					 expedienteMora.setIntItemCredito(expediente.getIntItemCredito());
					 expedienteMora.setBdSaldoMoraSoles(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setBdSaldoMora(expediente.getBdSaldoMora());
					 expedienteMora.setBdSaldoCreditoSoles(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setBdSaldoCredito(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setBdMontoAbono(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setStrDescripcion("Mora");
					 listaExpedienteTemp.add(expedienteMora);
				 }
			}else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)){
				log.info("------------------------------PARAM_T_TIPO_CREDITO_ORDENCREDITO  -------------------------------------------------------------");
				montoSaldoOrdenCredito      = montoSaldoOrdenCredito.add(expediente.getBdSaldoCredito());
				montoSaldoOrdenCreditoSoles = montoSaldoOrdenCreditoSoles.add(expediente.getBdSaldoCreditoSoles().add(expediente.getBdSaldoInteresSoles()).add(expediente.getBdSaldoMoraSoles()));
				 expediente.setStrDescripcion("Amortización");
				 expediente.setBdMontoAbono(expediente.getBdSaldoCreditoSoles());
				 listaExpedienteTemp.add(expediente);
				 //Interes
				 if (expediente.getBdSaldoInteres() != null && expediente.getBdSaldoInteres().compareTo(new BigDecimal(0)) == 1){
					 log.info("------------------------------Interes Interes Interes-------------------------------------------------------------");
					log.info(expediente);
					 Expediente expedienteInteres = new Expediente();
					 expedienteInteres.setId(expediente.getId());
					 expedienteInteres.setIntPersEmpresaCreditoPk(expediente.getIntPersEmpresaCreditoPk());
					 expedienteInteres.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					 expedienteInteres.setIntItemCredito(expediente.getIntItemCredito());
					 expedienteInteres.setBdSaldoInteresSoles(expediente.getBdSaldoInteresSoles());
					 expedienteInteres.setBdSaldoInteres(expediente.getBdSaldoInteres());
					 expedienteInteres.setBdSaldoCredito(expediente.getBdSaldoInteres());
					 expedienteInteres.setBdMontoAbono(expediente.getBdSaldoInteresSoles());
					 expedienteInteres.setStrDescripcion("Interes");
				     listaExpedienteTemp.add(expedienteInteres);
				 }    
				 //Mora
				 if (expediente.getBdSaldoMora() != null && expediente.getBdSaldoMora().compareTo(new BigDecimal(0)) == 1){
					log.info("------------------------------Mora Mora Mora-------------------------------------------------------------");
					log.info(expediente);
					 Expediente expedienteMora = new Expediente();
					 expedienteMora.setId(expediente.getId());
					 expedienteMora.setIntPersEmpresaCreditoPk(expediente.getIntPersEmpresaCreditoPk());
					 expedienteMora.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
					 expedienteMora.setIntItemCredito(expediente.getIntItemCredito());
					 expedienteMora.setBdSaldoMoraSoles(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setBdSaldoMora(expediente.getBdSaldoMora());
					 expedienteMora.setBdSaldoCredito(expediente.getBdSaldoMora());
					 expedienteMora.setBdMontoAbono(expediente.getBdSaldoMoraSoles());
					 expedienteMora.setStrDescripcion("Mora");
					 listaExpedienteTemp.add(expedienteMora);
				 }
			}
			else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)){
				log.info("------------------------------PARAM_T_TIPO_CREDITO_ACTIVIDAD-------------------------------------------------------------");
				log.info(expediente);
				montoSaldoActividad = montoSaldoActividad.add(expediente.getBdSaldoCredito());
			}
			
		}
		
		List<CuentaConcepto>  listaCuentaCpto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuenta.getId());
		
		
		
		for (CuentaConcepto cuentaConcepto : listaCuentaCpto) {
			log.info("listacuentaconcepto cant----->"+listaCuentaCpto.size());
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
				log.info("------------------------------CAPTACION_FDO_RETIRO---------------------------");
				log.info(cuentaConcepto);
				montoSaldoFondoRetiro = montoSaldoFondoRetiro.add(cuentaConcepto.getBdSaldo());
			}
			else
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_MANT_CUENTA)){
				 //Mant Cuenta
				log.info("------------------------------CAPTACION_MANT_CUENTA---------------------------");
				log.info(cuentaConcepto); 
				Expediente expedienteMant = new Expediente();
				 ExpedienteId id = new ExpedienteId();
				 id.setIntItemExpediente(cuentaConcepto.getId().getIntItemCuentaConcepto());
				 expedienteMant.setId(id);
				 
//				 BigDecimal montoSaldoMant = calcularMontoSaldoPorCptoGeneral(cuentaConcepto,Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO);
				 BigDecimal montoSaldoMant = cuentaConcepto.getBdSaldo();
				 expedienteMant.setBdSaldoCreditoSoles(montoSaldoMant);
				 expedienteMant.setBdSaldoCredito(montoSaldoMant);
				 expedienteMant.setBdSaldoMoraSoles(montoSaldoMant);
				 expedienteMant.setBdSaldoMora(montoSaldoMant);
				 BigDecimal bdMontoAbono = calcularDelConceptoPago(cuentaConcepto);
				 expedienteMant.setBdMontoAbono(bdMontoAbono);
//				 expedienteMant.setBdMontoAbono(montoSaldoMant);
				 expedienteMant.setStrDescripcion("Mantenimiento Cuenta");
				 montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(bdMontoAbono);
				 log.info("bdMontoAbono de Mantenimiento Cuenta------->"+bdMontoAbono);
				 log.info("montoSaldoPrestamoSoles agregado Mantenimiento Cuenta------->"+montoSaldoPrestamoSoles);
				 if (bdMontoAbono.compareTo(new BigDecimal(0)) == 1){
					 listaExpedienteTemp.add(expedienteMant);					
					 
				 }
			}
		
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_APORTACIONES)){
				//Aportaciones
				log.info("------------------------------CAPTACION_APORTACIONES---------------------------");
				log.info("cuentaConcepto.getBdSaldo()"+cuentaConcepto.getBdSaldo());
				montoSaldoAportaciones = cuentaConcepto.getBdSaldo();
				
			}
				
		}
		
		
	    List<DescuentoIndebido>	 listaDesctoIndebido = boDescuentoIndebido.getListPorEmpYCta(idEmpresa, idCuenta);
	    
	    
	    if (montoSaldoAportaciones.compareTo(new BigDecimal(0)) == 1){
	    	log.info("-------------------------------- montoSaldoAportaciones ----------------------");
	    	getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
			radioEntreConceptos = true;
			
			Expediente expedienteAporta = new Expediente();
			 ExpedienteId id = new ExpedienteId();
			 id.setIntItemExpediente(Constante.CAPTACION_APORTACIONES);
			 expedienteAporta.setChecked(true);
			 expedienteAporta.setId(id);
			 
			 bgMontoSaldoAportaciones = montoSaldoAportaciones;
			 montoTotalSaldoAbono = montoSaldoPrestamoSoles.add(montoSaldoOrdenCreditoSoles);
			 
			 
			 log.info("montoSaldoOrdenCreditoSoles-------->"+montoSaldoOrdenCreditoSoles);
			 log.info("montoSaldoPrestamoSoles------------>"+montoSaldoPrestamoSoles);
			 
			
			 log.info("montoTotalSaldoAbono--------------->"+montoTotalSaldoAbono); 
			 
			 if (montoSaldoAportaciones.compareTo(montoTotalSaldoAbono) == 1 || montoSaldoAportaciones.compareTo(montoTotalSaldoAbono) == 0){
				
				 log.info("-------------------------------- montoSaldoAportaciones es mayor que   montoTotalSaldoAbono----------------------");
				 log.info("montoSaldoAportaciones" +montoSaldoAportaciones);
				 log.info("montoTotalSaldoAbono" +montoTotalSaldoAbono);
				 expedienteAporta.setBdSaldoCreditoSoles(montoTotalSaldoAbono);
				 expedienteAporta.setBdSaldoCredito(montoTotalSaldoAbono);
				 bgMontoTransferencia   = montoTotalSaldoAbono; 
				 bgMontoSaldoTotalAbono = montoTotalSaldoAbono;
				 expedienteAporta.setStrDescripcion("Aporte");	
			 }else{
				 log.info("-------------------------------- montoTotalSaldoAbono es mayor que montoSaldoAportaciones ----------------------");
				 log.info("montoTotalSaldoAbono" +montoTotalSaldoAbono);
				 log.info("montoSaldoAportaciones" +montoSaldoAportaciones);
				 bgMontoTransferencia   = montoSaldoAportaciones; 
				 expedienteAporta.setBdSaldoCreditoSoles(montoSaldoAportaciones);
				 expedienteAporta.setBdSaldoCredito(montoSaldoAportaciones);
				 expedienteAporta.setStrDescripcion("Aporte");
				 bgMontoSaldoTotalAbono = montoTotalSaldoAbono;
			 }
			 
			 listaExpedienteTemp.add(expedienteAporta);
	    }else{
	    	log.info("------------------------ montoSaldoAportaciones es menor que 0 o null----------------------");
	    	log.info(montoSaldoAportaciones);
	    	 Expediente expedienteAporta = new Expediente();
			 ExpedienteId ids = new ExpedienteId();
			 ids.setIntItemExpediente(Constante.CAPTACION_APORTACIONES);
			 expedienteAporta.setChecked(true);
			 expedienteAporta.setId(ids);
			 expedienteAporta.setStrDescripcion("Aporte");
	    	 listaExpedienteTemp.add(expedienteAporta);
	    	//Consideraciones Transferencias entre Cuentas.
	    	    //cboAprobarRechazarDisabled = true;
	    	 
	    	getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(0);
			radioEntreConceptos = true;
			
			listaExpedienteTemp = listarExpedienteDeTipoAmortizacion(listaExpedienteTemp);
			listaExpedienteTemp = asignarOrdenPrioridad(listaExpedienteTemp);
			
			esEditableMontoSaldoAbono = false;
	    }
	    
	    
	    
	    
	    List<Movimiento> listMovimiento =  conceptoFacade.getListaMovimientoPorCtaPersonaConceptoGeneral(idEmpresa, idCuenta, idPersona, Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
  	  
	    //Obtemos Unico Periodo
	    Set<Integer> periodos = new HashSet<Integer>();
	    for (Movimiento movimiento : listMovimiento) {
	    	periodos.add(movimiento.getIntPeriodoPlanilla());
		}
	   
	    for (Iterator iterator = periodos.iterator(); iterator.hasNext();) {
	    	Integer intPeriodos = (Integer) iterator.next();
	        BigDecimal saldoMonto = obtenerSaldoDstoIndebidoPorPeriodo(intPeriodos,listMovimiento);
	        montoSaldoDescuentoIndebido = montoSaldoDescuentoIndebido.add(saldoMonto);
	   }
	    for(Expediente expedientes : listaExpedienteTemp){
			log.info("expediente despues de la listamovimiento--->"+expedientes);
		}
		
		//
		if (montoSaldoPrestamo.compareTo(new BigDecimal(0)) == 1 ||  montoSaldoOrdenCredito.compareTo(new BigDecimal(0)) == 1){
			
			for (DescuentoIndebido descuentoIndebido : listaDesctoIndebido) {
		    	//3
		    	if (descuentoIndebido.getIntParaEstadoPagadocod().equals(Constante.PARAM_T_ESTADO_PAGO_PENDIENTE)){
		    		messageValidation = "El socio tienen pendientes solicitudes de descuento indebido pendiente de pago.";
		    		esValidoCaso = false;
					return false;
		    	}
			}
		    
			//2
			 if (montoSaldoActividad.compareTo(new BigDecimal(0)) == 1){
				 esValidoCaso = false;
				 messageValidation = "Debe anular las actividades pendientes antes de realizar este tipo de transferencia.";
				 return false;
			 }
			 ExpedientePrevisionId expPreId = new ExpedientePrevisionId();
			 expPreId.setIntPersEmpresaPk(idEmpresa);
			 expPreId.setIntCuentaPk(idCuenta);
			 
			 List<ExpedientePrevisionComp> listaExpPrevision = previsionFacade.getListaExpedienteCreditoPorEmpresaYCuenta(expPreId);
			 
			 if (listaExpPrevision != null && listaExpPrevision.size() > 0)
			 for (ExpedientePrevisionComp expedientePrevisionComp : listaExpPrevision) {
				EstadoPrevision estado = expedientePrevisionComp.getEstadoPrevision();
				//2
				if (expedientePrevisionComp.getExpedientePrevision().getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_RETIRO)){
					if (estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						 esValidoCaso = false;
						 messageValidation = "El socio tiene pendiente solicitudes de previsión social.";
						 return false;
					}
				}
			 }
			 
		     //3
			 if (montoSaldoFondoRetiro.compareTo(new BigDecimal(0)) == 1 &&  esValidoCaso){
				 esValidoCaso = false;
				 messageValidation = "Debe ingresar la solicitud aprobada de Fondo de retiro para la cuenta de socio.";
				 return false;
			 }
		   
			 
			 //4
			 if (montoSaldoDescuentoIndebido.compareTo(new BigDecimal(0)) == 1){
				 esValidoCaso = false;
				 messageValidation = "El socio tienen pendientes de transferencia montos en cuentas por pagar.";
				 return false;
			 }
			 
		}
		else{
			 messageValidation = "El socio no tiene saldo de deuda.";
			 esValidoCaso = false;
			 return false;
		}
		for(Expediente expediente : listaExpedienteTemp){
			log.info("expediente ANTES DE ENTRAR A ASIGNARMONTOABONO--->"+expediente);
		}
		if (bgMontoTransferencia != null && bgMontoTransferencia.compareTo(montoTotalSaldoAbono) == -1){
			listaExpedienteTemp = asignarOrdenPrioridad(listaExpedienteTemp);
			asignarMontoAbonoSegunOrdenPrioridad(listaExpedienteTemp,bgMontoTransferencia);
			bgMontoSaldoTotalAbono = bgMontoTransferencia;
		}
			
		beanListaExpediente = listaExpedienteTemp;
		for(Expediente expediente : listaExpedienteTemp){
			log.info("expediente FINAL--->"+expediente);
		}
		return esValidoCaso;
	}
	
//	private BigDecimal calcularMontoSaldoPorCptoGeneral(CuentaConcepto cuentaConcepto,Integer intConceptoGeneral) throws EJBFactoryException,BusinessException{
//		ConceptoFacadeRemote  conceptoFacade  = null;
//		                      conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
//		Integer intEmpresa = socioCom.getCuenta().getId().getIntPersEmpresaPk();
//  	    Integer intCuenta  = socioCom.getCuenta().getId().getIntCuenta();
//  	    Integer intPersona = socioCom.getSocio().getId().getIntIdPersona();
//  	    
//  	    BigDecimal mtoSaldoMantTotalPrimero = new BigDecimal(0);
//  	    BigDecimal mtoSaldoMantTotalSegundo = new BigDecimal(0);
//	    
//		boolean esCancelatorio = false;
//  	    
//  		List<CuentaConceptoDetalle> listaCtaCptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaConcepto.getId());
// 		for (CuentaConceptoDetalle cuentaConceptoDetalle : listaCtaCptoDetalle) {
//  			if (cuentaConceptoDetalle.getTsFin() == null){
//  				if (cuentaConceptoDetalle.getIntParaTipoDescuentoCod().equals(Constante.PARAM_T_TIPODESCUENTO_CANCELATORIO)){
//  					esCancelatorio = true;
//  					break;
//  				}
//  			}
//		}
// 		List<Movimiento> listMovimiento =  conceptoFacade.getListaMovimientoPorCtaPersonaConceptoGeneral(intEmpresa, intCuenta, intPersona, intConceptoGeneral);
//		
//		 if (esCancelatorio){
//			 Date date = getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento();
//			  String mesActual  = UtilCobranza.obtieneMesCadena(date);
//			  String anioActual = UtilCobranza.obtieneAnio(date);
//			  Integer mesAnioActual = new Integer(mesActual)+new Integer(anioActual);
//			 for (Movimiento movimiento : listMovimiento) {
//				  String mesMov  = UtilCobranza.obtieneMesCadena(movimiento.getTsFechaMovimiento());
//				  String anioMov = UtilCobranza.obtieneAnio(movimiento.getTsFechaMovimiento());
//				  Integer mesAnioMov = new Integer(mesMov)+new Integer(anioMov);
//				 if (mesAnioActual.equals(mesAnioMov)){
//					 if (movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)){
//						 mtoSaldoMantTotalPrimero = mtoSaldoMantTotalPrimero.subtract(movimiento.getBdMontoMovimiento());
//						
//					 }
//				 }
//			 }
//		 }else{
//			 BigDecimal mtoSaldoMant = new BigDecimal(0);;
//			 
//			 for (CuentaConceptoDetalle cuentaConceptoDetalle : listaCtaCptoDetalle) {
//				 mtoSaldoMant =cuentaConceptoDetalle.getBdMontoConcepto();
//				  if (cuentaConceptoDetalle.getTsFin() == null){
////						  Date date = new Date();
//					  Date date = getBeanSolCtaCte().getSolCtaCteTipo().getDtFechaDocumento();
//						   List<Integer> mesAnioPeriodo = listarPeriodoMesAnio(cuentaConceptoDetalle.getTsInicio(),date);
//						   mtoSaldoMantTotalPrimero = mtoSaldoMant.multiply(new BigDecimal(mesAnioPeriodo.size()));
//						   for (Integer i = 0;i<  mesAnioPeriodo.size();i++){//50 SOLES
//							  Integer vMesAnio = mesAnioPeriodo.get(i);
//							  
//							  for (Movimiento movimiento : listMovimiento) {
//								  String  mesMov   = UtilCobranza.obtieneMesCadena(movimiento.getTsFechaMovimiento());
//								  String  anioMov  = UtilCobranza.obtieneAnio(movimiento.getTsFechaMovimiento());
//								  Integer mesAnio = new Integer(anioMov+mesMov);
//								  if (vMesAnio.equals(mesAnio) && movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)){
//									  mtoSaldoMantTotalPrimero = mtoSaldoMantTotalPrimero.subtract(movimiento.getBdMontoMovimiento());
//									 
//								  }
//							  }	
//							 
//						  } 
//				  }else{
//					  //Mes Iinicial hasta el Mes FIN
//					 List<Integer> mesAnioPeriodo = listarPeriodoMesAnio(cuentaConceptoDetalle.getTsInicio(),cuentaConceptoDetalle.getTsFin());
//					 mtoSaldoMantTotalSegundo = mtoSaldoMant.multiply(new BigDecimal(mesAnioPeriodo.size()));
//					  for (Integer i = 0;i<  mesAnioPeriodo.size();i++){
//						  Integer vMesAnio = mesAnioPeriodo.get(i);
//						  for (Movimiento movimiento : listMovimiento) {
//							  String  mesMov   = UtilCobranza.obtieneMesCadena(movimiento.getTsFechaMovimiento());
//							  String  anioMov  = UtilCobranza.obtieneAnio(movimiento.getTsFechaMovimiento());
//							  Integer mesAnio = new Integer(anioMov+mesMov);
//							  
//							  if (vMesAnio.equals(mesAnio)){
//									 if (movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)){
//										 mtoSaldoMantTotalSegundo = mtoSaldoMantTotalSegundo.subtract(movimiento.getBdMontoMovimiento());
//									 }
//								 }
//						  }	  
//					  } 
//				  }
//		  	 }
//		 }
//		
//		 return mtoSaldoMantTotalPrimero.add(mtoSaldoMantTotalSegundo);
//	}
	
	
	private List<Integer> listarPeriodoMesAnio(Date fechaInicio, Date fechaFin){
		
		List<Integer> lista = new ArrayList<Integer>();
		
		String anioInicial = UtilCobranza.obtieneAnio(fechaInicio);
		String mesInicial = UtilCobranza.obtieneMesCadena(fechaInicio);
		
		String anioFin = UtilCobranza.obtieneAnio(fechaFin);
		String mesFin  = UtilCobranza.obtieneMesCadena(fechaFin);
		
		String [] meses = {"","01","02","03","04","05","06","07","08","09","10","11","12"};
			
		if (anioInicial.equals(anioFin)){
			for (Integer mes = new Integer(anioInicial); mes <= 12; mes++) {
				 String strMes = meses[mes];
				 lista.add(new Integer(anioInicial+strMes));
			}  
		}
		else{
			for (Integer i = new Integer(anioInicial); i <= new Integer(anioFin); i++) {
				if (i.equals(new Integer(anioInicial))){
					for (Integer mes = new Integer(mesInicial); mes <= new Integer(12); mes++) {
						String strMes = meses[mes];
						lista.add(new Integer(anioInicial+strMes));
					}  
				}
				else
				if (i.compareTo(new Integer(anioFin)) == -1){
					for (Integer mes= 1; mes <= new Integer(12); mes++) {
						String strMes = meses[mes];
						lista.add(new Integer(i+strMes));
					}  
				}
				else
				if (i.compareTo(new Integer(anioFin)) == 0){
					for (Integer mes = 1; mes <= new Integer(mesFin); mes++) {
						String strMes = meses[mes];
						lista.add(new Integer(anioFin+strMes));
					}  
				}
			}
		}
		
		return lista; 
	}
	
     private List<Expediente> asignarOrdenPrioridadPorConceptoGeneral(List<Expediente>  lista) throws EJBFactoryException,BusinessException{
    	
    	PrioridadDescuentoFacadeRemote   prioriadadFacade   = (PrioridadDescuentoFacadeRemote)EJBFactory.getRemote(PrioridadDescuentoFacadeRemote.class);
    	ConceptoFacadeRemote             conceptoFacade     = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
    		
        List<Expediente> listaTemp = new ArrayList<Expediente>();	
        
    	Cuenta cuenta = socioCom.getCuenta();
    	for (Expediente expediente : lista) {
    		 Integer intConceptoGeneral = 0;
    		 if (expediente.getStrDescripcion() == null || !expediente.getStrDescripcion().equals("Aporte")){
    		 	 if (expediente.getIntParaTipoCreditoCod() == null){
	    			 CuentaConceptoId ctaCptoId = new CuentaConceptoId();
	    			                  ctaCptoId.setIntCuentaPk(cuenta.getId().getIntCuenta());
	    			                  ctaCptoId.setIntItemCuentaConcepto(expediente.getId().getIntItemExpediente());
	    			                  ctaCptoId.setIntPersEmpresaPk(cuenta.getId().getIntPersEmpresaPk());
	    			                  
	    			 List<CuentaConceptoDetalle> listaCtaCptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(ctaCptoId);
	    			 for (CuentaConceptoDetalle ctaCptoDetalle : listaCtaCptoDetalle) {
	    				   if (ctaCptoDetalle.getTsFin() == null){
	    					   CaptacionId captacionId = new CaptacionId();
	    					   captacionId.setIntItem(ctaCptoDetalle.getIntItemConcepto());
	    					   captacionId.setIntParaTipoCaptacionCod(ctaCptoDetalle.getIntParaTipoConceptoCod());
	    					   captacionId.setIntPersEmpresaPk(ctaCptoDetalle.getId().getIntPersEmpresaPk());
	    					   
	    					   if (ctaCptoDetalle.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_MANT_CUENTA)){
	    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO;
	    					   }
	    					   else
	    					   if (ctaCptoDetalle.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
	    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO;	 
	    					   }
	    					   else
	    					   if (ctaCptoDetalle.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_SEPELIO)){
	    						   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO;	 
	    					   }
	    					   
	    					   log.info("Prioridad Descuento ::CptoGnral="+intConceptoGeneral+"Captaciopn="+captacionId.getIntParaTipoCaptacionCod()+"-"+captacionId.getIntItem());
	    					   PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento(ctaCptoDetalle.getId().getIntPersEmpresaPk(), intConceptoGeneral, captacionId, null);
	    					   if (prioriadDscto != null)
	    					   expediente.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad());
	    				   }
					 }
	    		 }else{
	    			    
	                     CreditoId creditoId = new CreditoId();
		     			 creditoId.setIntItemCredito(expediente.getIntItemCredito());
		     			 creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
		     			 creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
		     			 
		     			if (expediente.getStrDescripcion().equals("Interes")){
		     				intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES;
		     			}else
		     			if (expediente.getStrDescripcion().equals("Mora")){	
		     				intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MORA;
		     			}	 
		     			else{
		     				intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION;
		     			}
		     		    log.info("Prioridad Descuento ::CptoGnral="+intConceptoGeneral+"Credito="+creditoId.getIntParaTipoCreditoCod()+"-"+creditoId.getIntItemCredito());
	    				PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento(expediente.getIntPersEmpresaCreditoPk(), intConceptoGeneral, null, creditoId);
					    if (prioriadDscto != null)
					    expediente.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad());
					}
			   }
    		 listaTemp.add(expediente);
         	}
    	
    	return listaTemp;
    }
	
	private List<Expediente> asignarOrdenPrioridad(List<Expediente>  lista) throws EJBFactoryException,BusinessException{
    	
    	PrioridadDescuentoFacadeRemote   prioriadadFacade   = (PrioridadDescuentoFacadeRemote)EJBFactory.getRemote(PrioridadDescuentoFacadeRemote.class);
    	ConceptoFacadeRemote             conceptoFacade     = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
    		
        List<Expediente> listaTemp = new ArrayList<Expediente>();	
        
    	Cuenta cuenta = socioCom.getCuenta();
    	for (Expediente expediente : lista) {
    		 Integer intConceptoGeneral = 0;
    		 if (expediente.getStrDescripcion() == null || !expediente.getStrDescripcion().equals("Aporte")){
    		 	 if (expediente.getIntParaTipoCreditoCod() == null){
	    			 CuentaConceptoId ctaCptoId = new CuentaConceptoId();
	    			                  ctaCptoId.setIntCuentaPk(cuenta.getId().getIntCuenta());
	    			                  ctaCptoId.setIntItemCuentaConcepto(expediente.getId().getIntItemExpediente());
	    			                  ctaCptoId.setIntPersEmpresaPk(cuenta.getId().getIntPersEmpresaPk());
	    			                  
	    			 List<CuentaConceptoDetalle> listaCtaCptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(ctaCptoId);
	    			 for (CuentaConceptoDetalle ctaCptoDetalle : listaCtaCptoDetalle) {
	    				   if (ctaCptoDetalle.getTsFin() == null){
	    					   CaptacionId captacionId = new CaptacionId();
	    					   captacionId.setIntItem(ctaCptoDetalle.getIntItemConcepto());
	    					   captacionId.setIntParaTipoCaptacionCod(ctaCptoDetalle.getIntParaTipoConceptoCod());
	    					   captacionId.setIntPersEmpresaPk(ctaCptoDetalle.getId().getIntPersEmpresaPk());
	    					   intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO;
	    					   log.info("Prioridad Descuento ::CptoGnral="+intConceptoGeneral+"Captaciopn="+captacionId.getIntParaTipoCaptacionCod()+"-"+captacionId.getIntItem());
	    					   PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento(ctaCptoDetalle.getId().getIntPersEmpresaPk(), intConceptoGeneral, captacionId, null);
	    					   if (prioriadDscto != null)
	    					   expediente.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad());
	    				   }
					 }
	    		 }else{
	    			    
	                     CreditoId creditoId = new CreditoId();
		     			 creditoId.setIntItemCredito(expediente.getIntItemCredito());
		     			 creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
		     			 creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
		     			 
		     			if (expediente.getStrDescripcion().equals("Interes")){
		     				intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES;
		     			}else
		     			if (expediente.getStrDescripcion().equals("Mora")){	
		     				intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MORA;
		     			}	 
		     			else{
		     				intConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION;
		     			}
		     		    log.info("Prioridad Descuento ::CptoGnral="+intConceptoGeneral+"Credito="+creditoId.getIntParaTipoCreditoCod()+"-"+creditoId.getIntItemCredito());
	    				PrioridadDescuento prioriadDscto =  prioriadadFacade.obtenerOrdenPrioridadDescuento(expediente.getIntPersEmpresaCreditoPk(), intConceptoGeneral, null, creditoId);
					    if (prioriadDscto != null)
					    expediente.setIntOrdenPrioridad(prioriadDscto.getIntPrdeOrdenprioridad());
					}
			   }
    		 listaTemp.add(expediente);
         	}
    	
    	return listaTemp;
    }
	//Este metodo por lo de agregar las tablas conceptoPago se cambio.
	private BigDecimal calcularMontoSaldoDeCtaCptoPorCptoGeneralAlPeriodoOLD(CuentaConcepto cuentaConcepto,Integer intConceptoGeneral,Date fechaTran) throws EJBFactoryException,BusinessException{
			ConceptoFacadeRemote  conceptoFacade  = null;
			                      conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			Integer intEmpresa = socioCom.getCuenta().getId().getIntPersEmpresaPk();
	  	    Integer intCuenta  = socioCom.getCuenta().getId().getIntCuenta();
	  	    Integer intPersona = socioCom.getSocio().getId().getIntIdPersona();
	  	    
	  	    BigDecimal mtoSaldoMantTotalPrimero = new BigDecimal(0);
	  	    BigDecimal mtoSaldoMantTotalSegundo = new BigDecimal(0);
		    
			
	  	    boolean esCancelatorio = false;
	  	    
	  		List<CuentaConceptoDetalle> listaCtaCptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaConcepto.getId());
	 		for (CuentaConceptoDetalle cuentaConceptoDetalle : listaCtaCptoDetalle) {
	  			if (cuentaConceptoDetalle.getTsFin() == null){
	  				if (cuentaConceptoDetalle.getIntParaTipoDescuentoCod().equals(Constante.PARAM_T_TIPODESCUENTO_CANCELATORIO)){
	  					esCancelatorio = true;
	  					break;
	  				}
	  			}
			}
	 		
			List<Movimiento> listMovimiento =  conceptoFacade.getListaMovimientoPorCtaPersonaConceptoGeneral(intEmpresa, intCuenta, intPersona, intConceptoGeneral);
			
			 if (esCancelatorio){
				 Date date = new Date();
				  String mesActual  = UtilCobranza.obtieneMesCadena(date);
				  String anioActual = UtilCobranza.obtieneAnio(date);
				  Integer mesAnioActual = new Integer(mesActual)+new Integer(anioActual);
				 for (Movimiento movimiento : listMovimiento) {
					  String mesMov  = UtilCobranza.obtieneMesCadena(movimiento.getTsFechaMovimiento());
					  String anioMov = UtilCobranza.obtieneAnio(movimiento.getTsFechaMovimiento());
					  Integer mesAnioMov = new Integer(mesMov)+new Integer(anioMov);
					 if (mesAnioActual.equals(mesAnioMov)){
						 if (movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)){
							 mtoSaldoMantTotalPrimero = mtoSaldoMantTotalPrimero.subtract(movimiento.getBdMontoMovimiento());
						 }
					 }
				 }
			 }else{
				 BigDecimal mtoSaldoMant = new BigDecimal(0);;
				 
				 for (CuentaConceptoDetalle cuentaConceptoDetalle : listaCtaCptoDetalle) {
					 mtoSaldoMant =cuentaConceptoDetalle.getBdMontoConcepto();
					  if (cuentaConceptoDetalle.getTsFin() == null){
							   List<Integer> mesAnioPeriodo = listarPeriodoMesAnio(cuentaConceptoDetalle.getTsInicio(),fechaTran);
							       mtoSaldoMantTotalPrimero = mtoSaldoMant.multiply(new BigDecimal(mesAnioPeriodo.size()));
							   for (Integer i = 0;i<  mesAnioPeriodo.size();i++){
								  Integer vMesAnio = mesAnioPeriodo.get(i);
								  
								  for (Movimiento movimiento : listMovimiento) {
									  String mesMov   = UtilCobranza.obtieneMesCadena(movimiento.getTsFechaMovimiento());
									  String anioMov  = UtilCobranza.obtieneAnio(movimiento.getTsFechaMovimiento());
									  Integer mesAnio = new Integer(anioMov+mesMov);
									  if (vMesAnio.equals(mesAnio) && movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)){
										  mtoSaldoMantTotalPrimero = mtoSaldoMantTotalPrimero.subtract(movimiento.getBdMontoMovimiento());
									  }
								  }	  
							  } 
					  }else{
						 //Mes Iinicial hasta el Mes FIN
						  String strFechaTran = UtilCobranza.convierteDateAString(fechaTran);
						  String strTsfin     = UtilCobranza.convierteDateAString(cuentaConceptoDetalle.getTsFin());
						 if (fechaTran.before(cuentaConceptoDetalle.getTsFin()) || strFechaTran.equals(strTsfin)){
							 List<Integer> mesAnioPeriodo = listarPeriodoMesAnio(cuentaConceptoDetalle.getTsInicio(),fechaTran);
							 mtoSaldoMantTotalSegundo = mtoSaldoMant.multiply(new BigDecimal(mesAnioPeriodo.size()));
							  for (Integer i = 0;i<  mesAnioPeriodo.size();i++){
								  Integer vMesAnio = mesAnioPeriodo.get(i);
								  for (Movimiento movimiento : listMovimiento) {
									  String  mesMov  = UtilCobranza.obtieneMesCadena(movimiento.getTsFechaMovimiento());
									  String  anioMov = UtilCobranza.obtieneAnio(movimiento.getTsFechaMovimiento());
									  Integer mesAnio = new Integer(anioMov+mesMov);
									  if (vMesAnio.equals(mesAnio)){
											 if (movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)){
												 mtoSaldoMantTotalSegundo = mtoSaldoMantTotalSegundo.subtract(movimiento.getBdMontoMovimiento());
											 }
										 }
								  }	  
						     } 
						}	  
					  }
			  	 }
			 }
			
		 return mtoSaldoMantTotalPrimero.add(mtoSaldoMantTotalSegundo);
		
	}
	
	
	
    private void asignarMontoAbonoSegunOrdenPrioridad(List<Expediente>  lista,BigDecimal montoTransferencia) throws EJBFactoryException,BusinessException{
    	    		
    	BigDecimal montoTotalTransf = new BigDecimal(0);
    	montoTotalTransf = montoTransferencia;
    	 //Obtemos Unico Periodo
	    Set<Integer> numbers = new HashSet<Integer>();
	    for (Expediente expediente : lista) {
	    	if (expediente.getIntOrdenPrioridad() != null)
	    	numbers.add(expediente.getIntOrdenPrioridad());
		}
	    
	    List<Number> numeros = new ArrayList<Number>();
	    List list = new ArrayList();
	    for (Iterator iterator = numbers.iterator(); iterator.hasNext();) {
			Integer numero = (Integer) iterator.next();
			list.add(numero);
		}
      	Collections.sort(list);
      	
      	for (int i = 0; i < list.size(); i++) {
      		for (Expediente exp : lista) {
                 if (exp.getIntOrdenPrioridad() != null && exp.getIntOrdenPrioridad().equals(list.get(i))){
                	 if ((exp.getBdSaldoCreditoSoles().compareTo(new BigDecimal(0)) == 1) && montoTransferencia.compareTo(exp.getBdSaldoCreditoSoles()) == 1){
                		 log.info("exp.getBdSaldoCreditoSoles()>0 ---->"+exp.getBdSaldoCreditoSoles());
                		 log.info("montoTransferencia > BdSaldoCreditoSoles---->"+montoTransferencia);
                		 //                		 exp.setBdMontoAbono(exp.getBdSaldoCreditoSoles());
//                		 montoTransferencia = montoTransferencia.subtract(exp.getBdSaldoCreditoSoles());                		 
                		 montoTransferencia = montoTransferencia.subtract(exp.getBdMontoAbono());
                	 }
                	 else
                	 if ((exp.getBdSaldoCreditoSoles().compareTo(new BigDecimal(0)) == 1) && montoTransferencia.compareTo(exp.getBdSaldoCreditoSoles()) == -1){
                		 log.info("exp.getBdSaldoCreditoSoles()>0 ---->"+exp.getBdSaldoCreditoSoles());
                		 log.info("montoTransferencia < BdSaldoCreditoSoles---->"+montoTransferencia);
                		 exp.setBdMontoAbono(montoTransferencia);
//                		 montoTransferencia = new BigDecimal(0);
                		 exp.setBdMontoAbono(montoTransferencia);
                		 montoTransferencia = new BigDecimal(0);
                	 }
                	 else
                	 if ((exp.getBdSaldoCreditoSoles().compareTo(new BigDecimal(0)) == 1) && montoTransferencia.compareTo(exp.getBdSaldoCreditoSoles()) == 0){
                		 log.info("exp.getBdSaldoCreditoSoles()>0 ---->"+exp.getBdSaldoCreditoSoles());
                		 log.info("montoTransferencia = BdSaldoCreditoSoles---->"+montoTransferencia);
//                		 exp.setBdMontoAbono(exp.getBdSaldoCreditoSoles());
                		 montoTransferencia = montoTransferencia.subtract(exp.getBdSaldoCreditoSoles());
                	 }
                 }
			}
		}
        
    }
    
    
    private void asignarMontoAbonoSegunMontoSaldoDetyOrdenPrioridad(List<Expediente>  lista,BigDecimal montoTransferencia) throws EJBFactoryException,BusinessException{
    	List<Expediente> listaExp = new ArrayList<Expediente>();
    		
    	BigDecimal montoTotalTransf = new BigDecimal(0);
    	montoTotalTransf = montoTransferencia;
    	 //Obtemos Unico Periodo
	    Set<Integer> numbers = new HashSet<Integer>();
	    for (Expediente expediente : lista) {
	    	if (expediente.getIntOrdenPrioridad() != null)
	    	numbers.add(expediente.getIntOrdenPrioridad());
		}
	    
	    List<Number> numeros = new ArrayList<Number>();
	    List list = new ArrayList();
	    for (Iterator iterator = numbers.iterator(); iterator.hasNext();) {
			Integer numero = (Integer) iterator.next();
			list.add(numero);
		}
      	Collections.sort(list);
      	
      	for (int i = 0; i < list.size(); i++) {
      		for (Expediente exp : lista) {
      			 if (exp.getBdMontoSaldoDetalle() == null) {
      				exp.setBdMontoSaldoDetalle(new BigDecimal(0));
      			 }
      			 if (exp.getBdMontoAbono() == null){
      				exp.setBdMontoAbono(new BigDecimal(0));
      			 }	
      			 
                 if (exp.getIntOrdenPrioridad() != null && exp.getIntOrdenPrioridad().equals(list.get(i))){
                	 if ((exp.getBdMontoSaldoDetalle().compareTo(new BigDecimal(0)) == 1) && montoTransferencia.compareTo(exp.getBdMontoSaldoDetalle()) == 1){
                		 exp.setBdMontoAbono(exp.getBdMontoSaldoDetalle());
                		 montoTransferencia = montoTransferencia.subtract(exp.getBdMontoSaldoDetalle());
                	 }
                	 else
                	 if ((exp.getBdMontoSaldoDetalle().compareTo(new BigDecimal(0)) == 1) && montoTransferencia.compareTo(exp.getBdMontoSaldoDetalle()) == -1){
                		 exp.setBdMontoAbono(montoTransferencia);
                		 montoTransferencia = new BigDecimal(0);
                	 }
                	 else
                	 if ((exp.getBdMontoSaldoDetalle().compareTo(new BigDecimal(0)) == 1) && montoTransferencia.compareTo(exp.getBdMontoSaldoDetalle()) == 0){
                		 exp.setBdMontoAbono(exp.getBdMontoSaldoDetalle());
                		 montoTransferencia = montoTransferencia.subtract(exp.getBdMontoSaldoDetalle());
                	 }
                 }
			}
		}
        
    }
    
  private List<Expediente> listarExpedienteDeTipoAmortizacion(List<Expediente>  lista){
    	List<Expediente> listaTemp = new ArrayList<Expediente>();
    	for (Expediente expediente : lista) {
    		 if (expediente.getStrDescripcion() != null) {
    			if (expediente.getStrDescripcion().equalsIgnoreCase("Amortización")){
    				expediente.setBdMontoAbono(new BigDecimal(0));
    				listaTemp.add(expediente);
				}else
				if (expediente.getStrDescripcion().equalsIgnoreCase("Aporte")){
					listaTemp.add(expediente);
				}
		   	 }
	    }
        
    return listaTemp;
 }

    
private boolean verCasoSocioNoLabora() throws EJBFactoryException,BusinessException{
		
		ConceptoFacadeRemote   conceptoFacade         = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		PrevisionFacadeRemote  previsionFacade        = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
		DescuentoIndebidoBO    boDescuentoIndebido    = (DescuentoIndebidoBO)TumiFactory.get(DescuentoIndebidoBO.class);
		CreditoFacadeRemote    creditoFacade          = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
		GeneralFacadeRemote    generalFacade          = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
		EstructuraFacadeRemote estructuraFacade       = (EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class); 
		CuentacteFacadeRemote  cuentacteFacadeRemote  = (CuentacteFacadeRemote)EJBFactory.getRemote(CuentacteFacadeRemote.class);
		
		//Declaracion
		bgMontoSaldoTotalAbono = null; 
		bgMontoTransferencia = null;
		getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
		radioEntreConceptos = true;
		beanListaExpediente = null;
		messageValidation = "";
		BigDecimal montoSaldoPrestamo       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCredito        = new BigDecimal(0);
		BigDecimal montoSaldoActividad      	 = new BigDecimal(0);
		BigDecimal montoSaldoFondoRetiro    	 = new BigDecimal(0);
		//BigDecimal montoSaldoFondoSepelio   	 = new BigDecimal(0);
		BigDecimal montoSaldoAportaciones   	 = new BigDecimal(0);
		
		BigDecimal montoSaldoDescuentoIndebido   = new BigDecimal(0);
		
		BigDecimal montoSaldoPrestamoSoles       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCreditoSoles        = new BigDecimal(0);
		
		BigDecimal montoTotalSaldoAbono       	 = new BigDecimal(0);
		boolean esValidoCaso = true;
		
		Cuenta cuenta = socioCom.getCuenta();
		Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
		Integer idEmpresa = cuenta.getId().getIntPersEmpresaPk();
		Integer idCuenta  = cuenta.getId().getIntCuenta();
		
		//Validaciones
		
		//0
		Documento documento = socioCom.getPersona().getDocumento();
		String strNroDocIdentidad = documento.getStrNumeroIdentidad();
		SocioComp socioComp = estructuraFacade.getSocioNatuPorLibElectoral(strNroDocIdentidad);
		
		if (socioComp != null && socioComp.getPadron() != null){
			 String [] meses = {"01","02","03","04","05","06","07","08","09","10","11","12"};
			 String mes = "";
			 for (int i = 0; i < meses.length; i++) {
				if (i == socioComp.getPadron().getId().getIntMes()){
					mes =  meses[i];
				}
			 } 
			 messageValidation = "El socio se encuentra ubicado en el padrón de periodo ("+socioComp.getPadron().getId().getIntPeriodo()+""+mes+") no se puede realizar este tipo de transferencia.";
    		 esValidoCaso = false;
			 return false;
		}
		
		
		List<Expediente>  listaExpediente =  conceptoFacade.getListaExpedientePorEmpresaYCta(idEmpresa, idCuenta);
		List<Expediente>  listaExpedienteTemp =  new ArrayList<Expediente>();
		
		for (Expediente expediente : listaExpediente) {
			
			CreditoId creditoId = new CreditoId();
			creditoId.setIntItemCredito(expediente.getIntItemCredito());
			creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
			creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
			Credito credito = null;
			credito = creditoFacade.getCreditoPorIdCredito(creditoId);
			
			if (credito == null){
				 esValidoCaso = false;
				 messageValidation ="No existe la configuración de crédito.";
			    throw new BusinessException("No existe la configuración de crédito.");
			   
			}
			
			
			
			if (credito.getIntParaMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
				   expediente.setBdSaldoCreditoSoles(expediente.getBdSaldoCredito()) ;
				   expediente.setBdSaldoInteresSoles(expediente.getBdSaldoInteres() != null?expediente.getBdSaldoInteres():new BigDecimal(0));
				   expediente.setBdSaldoMoraSoles(expediente.getBdSaldoMora()!= null?expediente.getBdSaldoMora():new BigDecimal(0));
			}else{
				   TipoCambio tipoCambio =   generalFacade.getTipoCambioActualPorClaseYMoneda(idEmpresa,Constante.PARAM_T_TIPOCAMBIO_CLASE_BANCARIA,credito.getIntParaMonedaCod());
				   BigDecimal bdSaldoCreditoSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoCredito());
				   expediente.setBdSaldoCreditoSoles(bdSaldoCreditoSoles) ;
				   BigDecimal bdSaldoInteresSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoInteresSoles());
				   expediente.setBdSaldoInteresSoles(bdSaldoInteresSoles);
				   BigDecimal bdSaldoMoraSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoMoraSoles());
				   expediente.setBdSaldoMoraSoles(bdSaldoMoraSoles);
			}
				  
			
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)){
				 montoSaldoPrestamo = montoSaldoPrestamo.add(expediente.getBdSaldoCredito());
				 montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(expediente.getBdSaldoCreditoSoles().add(expediente.getBdSaldoInteresSoles()).add(expediente.getBdSaldoMoraSoles()));
				 expediente.setStrDescripcion("Amortización");
				 listaExpedienteTemp.add(expediente);
				 //interes
				 Expediente expedienteInteres = new Expediente();
				 expedienteInteres.setId(expediente.getId());
				 expedienteInteres.setChecked(true);
				 expedienteInteres.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
				 expedienteInteres.setBdSaldoInteresSoles(expediente.getBdSaldoInteresSoles());
				 expedienteInteres.setBdSaldoInteres(expediente.getBdSaldoInteres());
				 expedienteInteres.setBdSaldoCreditoSoles(expediente.getBdSaldoInteresSoles());
				 expedienteInteres.setBdSaldoCredito(expediente.getBdSaldoInteresSoles());
				 expedienteInteres.setStrDescripcion("Interes");
				 listaExpedienteTemp.add(expedienteInteres);
				//mora
				 Expediente expedienteMora = new Expediente();
				 expedienteMora.setChecked(true);
				 expedienteMora.setId(expediente.getId());
				 expedienteMora.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
				 expedienteMora.setBdSaldoMoraSoles(expediente.getBdSaldoMoraSoles());
				 expedienteMora.setBdSaldoMora(expediente.getBdSaldoMora());
				 expedienteMora.setBdSaldoCreditoSoles(expediente.getBdSaldoMoraSoles());
				 expedienteMora.setBdSaldoCredito(expediente.getBdSaldoMoraSoles());
				 expedienteMora.setStrDescripcion("Mora");
				 listaExpedienteTemp.add(expedienteMora);
				
			}else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)){
				montoSaldoOrdenCredito      = montoSaldoOrdenCredito.add(expediente.getBdSaldoCredito());
				montoSaldoOrdenCreditoSoles = montoSaldoOrdenCreditoSoles.add(expediente.getBdSaldoCreditoSoles().add(expediente.getBdSaldoInteresSoles()).add(expediente.getBdSaldoMoraSoles()));
				 expediente.setStrDescripcion("Amortización");
				 listaExpedienteTemp.add(expediente);
				 //Interes
				 Expediente expedienteInteres = new Expediente();
				 expedienteInteres.setId(expediente.getId());
				 expedienteInteres.setChecked(true);
				 expedienteInteres.setBdSaldoInteresSoles(expediente.getBdSaldoInteresSoles());
				 expedienteInteres.setBdSaldoInteres(expediente.getBdSaldoInteres());
				 expedienteInteres.setBdSaldoCredito(expediente.getBdSaldoInteres());
				 expedienteInteres.setStrDescripcion("Interes");
				 listaExpedienteTemp.add(expedienteInteres);
				 //Mora
				 Expediente expedienteMora = new Expediente();
				 expedienteMora.setChecked(true);
				 expedienteMora.setId(expediente.getId());
				 expedienteMora.setBdSaldoMoraSoles(expediente.getBdSaldoMoraSoles());
				 expedienteMora.setBdSaldoMora(expediente.getBdSaldoMora());
				 expedienteMora.setBdSaldoCredito(expediente.getBdSaldoMora());
				 expedienteMora.setStrDescripcion("Mora");
				 listaExpedienteTemp.add(expedienteMora);
			}
			else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)){
				montoSaldoActividad = montoSaldoActividad.add(expediente.getBdSaldoCredito());
			}
		}
		
		List<CuentaConcepto>  listaCuentaCpto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuenta.getId());
		
		
		
		for (CuentaConcepto cuentaConcepto : listaCuentaCpto) {
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
				montoSaldoFondoRetiro = montoSaldoFondoRetiro.add(cuentaConcepto.getBdSaldo());
			}
			else
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_MANT_CUENTA)){
				 //Mant Cuenta
				 Expediente expedienteMant = new Expediente();
				 ExpedienteId id = new ExpedienteId();
				 id.setIntItemExpediente(cuentaConcepto.getId().getIntItemCuentaConcepto());
				 expedienteMant.setChecked(true);
				 expedienteMant.setId(id);
				 expedienteMant.setBdSaldoCreditoSoles(cuentaConcepto.getBdSaldo());
				 expedienteMant.setBdSaldoMoraSoles(cuentaConcepto.getBdSaldo());
				 expedienteMant.setBdSaldoMora(cuentaConcepto.getBdSaldo());
				 expedienteMant.setStrDescripcion("Mantenimiento Cuenta");
				 listaExpedienteTemp.add(expedienteMant);
				 montoSaldoPrestamoSoles.add(cuentaConcepto.getBdSaldo());
				 
			}
		
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_APORTACIONES)){
				//Aportaciones
				montoSaldoAportaciones = cuentaConcepto.getBdSaldo();
			}
				
		}
		
		
	    List<DescuentoIndebido>	 listaDesctoIndebido = boDescuentoIndebido.getListPorEmpYCta(idEmpresa, idCuenta);
	    
	    
	    if (montoSaldoAportaciones.compareTo(new BigDecimal(0)) == 1){
	    	getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
			radioEntreConceptos = true;
			
			 Expediente expedienteAporta = new Expediente();
			 ExpedienteId id = new ExpedienteId();
			 id.setIntItemExpediente(Constante.CAPTACION_APORTACIONES);
			 expedienteAporta.setChecked(true);
			 expedienteAporta.setId(id);
			 expedienteAporta.setBdSaldoCredito(montoSaldoAportaciones);
			 montoTotalSaldoAbono = montoSaldoPrestamoSoles.add(montoSaldoOrdenCreditoSoles);
			 
			 if (montoSaldoAportaciones.compareTo(montoTotalSaldoAbono) == 1 || montoSaldoAportaciones.compareTo(montoTotalSaldoAbono) == 0){
				 expedienteAporta.setBdSaldoCreditoSoles(montoTotalSaldoAbono);
				 expedienteAporta.setBdSaldoCredito(montoTotalSaldoAbono);
				 bgMontoTransferencia = montoTotalSaldoAbono; 
				 bgMontoSaldoTotalAbono = montoTotalSaldoAbono;
				 expedienteAporta.setStrDescripcion("Aporte");
			 }else{
				 bgMontoTransferencia = montoSaldoAportaciones; 
				 expedienteAporta.setBdSaldoCreditoSoles(montoSaldoAportaciones);
				 expedienteAporta.setBdSaldoCredito(montoTotalSaldoAbono);
				 expedienteAporta.setStrDescripcion("Aporte");
				 bgMontoSaldoTotalAbono = montoTotalSaldoAbono;
			 }
			 
			 listaExpedienteTemp.add(expedienteAporta);
	    }else{
	    	
	    	//Se genera el cambio de condicion cuenta a moroso.
	    	EstadoSolicitudCtaCte estSolCtaCte = new EstadoSolicitudCtaCte();
			estSolCtaCte.setDtEsccFechaEstado(new Date());
			estSolCtaCte.setIntParaEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE);
			estSolCtaCte.setIntSucuIduSusucursal(SESION_IDSUCURSAL);
			estSolCtaCte.setIntSudeIduSusubsucursal(SESION_IDSUBCURSAL);
			estSolCtaCte.setIntPersUsuarioEstado(SESION_IDUSUARIO);
			estSolCtaCte.setIntPersEmpresaEstado(SESION_IDEMPRESA);
			estSolCtaCte.setStrEsccObservacion("Cambio de condicion Socio.");
			
			beanSolCtaCte.setEstSolCtaCte(estSolCtaCte);
			
			List<SolicitudCtaCteTipo> listTaTipoSol = new ArrayList<SolicitudCtaCteTipo>();
			SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo(); 
			solCtaCteTipo.getId().setIntTipoSolicitudctacte(Constante.PARAM_T_TIPOSOLCITUD_CAMBIOCONDICION);
			solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			solCtaCteTipo.setStrScctObservacion("Socio no labora");
			listTaTipoSol.add(solCtaCteTipo);
			solCtaCteTipo.setCuenta(socioCom.getCuenta());
			List<CuentaConcepto> listaConcepto = new ArrayList();
			CuentaConcepto cuentaConcepto = new CuentaConcepto();
			
			CuentaConceptoId id = new CuentaConceptoId();
			id.setIntCuentaPk(socioCom.getCuenta().getId().getIntCuenta());
			id.setIntPersEmpresaPk(socioCom.getCuenta().getId().getIntPersEmpresaPk());
			id.setIntItemCuentaConcepto(Constante.CAPTACION_APORTACIONES);
			cuentaConcepto.setId(id);
			listaConcepto.add(cuentaConcepto);
			
			solCtaCteTipo.getCuenta().setListaConcepto(listaConcepto);
			Movimiento mov =   new Movimiento();
			mov.setDtFechaInicio(UtilCobranza.obtieneFechaActualEnTimesTamp());
			mov.setDtFechaFin(null);
			
			SolicitudCtaCteTipo solCtaCteTipoTemp = beanSolCtaCte.getSolCtaCteTipo();
			//
			beanSolCtaCte.setSolCtaCteTipo(solCtaCteTipo);
			
			beanSolCtaCte.getSolCtaCteTipo().setMovimiento(mov);
			beanSolCtaCte.getSolCtaCteTipo().setIntParaCondicionCuentaFinal(Constante.PARAM_T_CONDICIONSOCIO_INA);
			beanSolCtaCte.getSolCtaCteTipo().setIntMotivoSolicitud(Constante.PARAM_T_TIPOMOTIVOSOLICITUD_CESELABORAL);
			beanSolCtaCte.getSolCtaCteTipo().setIntParaEstadoanalisis(Constante.PARAM_T_ESTADOANALISIS_APROBAR);
			beanSolCtaCte.getEstSolCtaCte().setIntParaEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO);
			beanSolCtaCte.setListaSolCtaCteTipo(listTaTipoSol);
			
			
			cuentacteFacadeRemote.grabarSolicitudCtaCteAntedido(beanSolCtaCte);
			
			beanSolCtaCte.setSolCtaCteTipo(solCtaCteTipoTemp);
			
			getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(0);
			radioEntreConceptos = true;
			messageValidation = "El socio ha pasado a Condición Moroso.";
	    }
	    
	    
	    
	    
	    List<Movimiento> listMovimiento =  conceptoFacade.getListaMovimientoPorCtaPersonaConceptoGeneral(idEmpresa, idCuenta, idPersona, Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
  	  
	    //Obtemos Unico Periodo
	    Set<Integer> periodos = new HashSet<Integer>();
	    for (Movimiento movimiento : listMovimiento) {
	    	periodos.add(movimiento.getIntPeriodoPlanilla());
		}
	   
	    for (Iterator iterator = periodos.iterator(); iterator.hasNext();) {
	    	Integer intPeriodos = (Integer) iterator.next();
	        BigDecimal saldoMonto = obtenerSaldoDstoIndebidoPorPeriodo(intPeriodos,listMovimiento);
	        montoSaldoDescuentoIndebido = montoSaldoDescuentoIndebido.add(saldoMonto);
	   }
	    
		
		//
		if (montoSaldoPrestamo.compareTo(new BigDecimal(0)) == 1 ||  montoSaldoOrdenCredito.compareTo(new BigDecimal(0)) == 1){
			
			for (DescuentoIndebido descuentoIndebido : listaDesctoIndebido) {
		    	//3
		    	if (descuentoIndebido.getIntParaEstadoPagadocod().equals(Constante.PARAM_T_ESTADO_PAGO_PENDIENTE)){
		    		messageValidation = "El socio tienen pendientes solicitudes de descuento indebido pendiente de pago.";
		    		esValidoCaso = false;
					return false;
		    	}
			}
		    
			//2
			 if (montoSaldoActividad.compareTo(new BigDecimal(0)) == 1){
				 esValidoCaso = false;
				 messageValidation = "Debe anular las actividades pendientes antes de realizar este tipo de transferencia.";
				 return false;
			 }
			 ExpedientePrevisionId expPreId = new ExpedientePrevisionId();
			 expPreId.setIntPersEmpresaPk(idEmpresa);
			 expPreId.setIntCuentaPk(idCuenta);
			 
			 List<ExpedientePrevisionComp> listaExpPrevision = previsionFacade.getListaExpedienteCreditoPorEmpresaYCuenta(expPreId);
			 
			 if (listaExpPrevision != null && listaExpPrevision.size() > 0)
			 for (ExpedientePrevisionComp expedientePrevisionComp : listaExpPrevision) {
				EstadoPrevision estado = expedientePrevisionComp.getEstadoPrevision();
				//2
				if (expedientePrevisionComp.getExpedientePrevision().getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_RETIRO)){
					if (estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						 esValidoCaso = false;
						 messageValidation = "El socio tiene pendiente solicitudes de previsión social.";
						 return false;
					}
				}
			 }
			 
		   //3
			 if (montoSaldoFondoRetiro.compareTo(new BigDecimal(0)) == 1 &&  esValidoCaso != false){
				 esValidoCaso = false;
				 messageValidation = "Debe ingresar la solicitud aprobada de Fondo de retiro para la cuenta de socio.";
				 return false;
			 }
		   
			 
			 //4
			 if (montoSaldoDescuentoIndebido.compareTo(new BigDecimal(0)) == 1){
				 esValidoCaso = false;
				 messageValidation = "El socio tienen pendientes de transferencia montos en cuentas por pagar.";
				 return false;
			 }
			
			
			 
		}
		else{
			 messageValidation = "El socio no tiene saldo de deuda.";
			 esValidoCaso = false;
			 return false;
		}
		
		
		beanListaExpediente = listaExpedienteTemp;
		return esValidoCaso;
	}
	
	private boolean validarCasoFallecimientoSocio() throws EJBFactoryException,BusinessException{
		
		ConceptoFacadeRemote   conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		PrevisionFacadeRemote  previsionFacade = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
		DescuentoIndebidoBO    boDescuentoIndebido = (DescuentoIndebidoBO)TumiFactory.get(DescuentoIndebidoBO.class);
		CreditoFacadeRemote    creditoFacade     = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
		GeneralFacadeRemote    generalFacade     = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
 	    //Declaracion
		getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
		radioEntreConceptos = true;
		beanListaExpediente = null;
		messageValidation = "";
		BigDecimal montoSaldoPrestamo       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCredito        = new BigDecimal(0);
		BigDecimal montoSaldoActividad      	 = new BigDecimal(0);
		BigDecimal montoSaldoFondoRetiro    	 = new BigDecimal(0);
		BigDecimal montoSaldoFondoSepelio   	 = new BigDecimal(0);
		BigDecimal montoSaldoDescuentoIndebido   = new BigDecimal(0);
		bgMontoSaldoTotalAbono =null;
		
		BigDecimal montoSaldoPrestamoSoles       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCreditoSoles       = new BigDecimal(0);
		
		boolean esValidoCaso = true;
		
		
		Cuenta cuenta = socioCom.getCuenta();
		Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
		Integer idEmpresa = cuenta.getId().getIntPersEmpresaPk();
		Integer idCuenta  = cuenta.getId().getIntCuenta();
		
		//Validaciones
		List<Expediente>  listaExpediente =  conceptoFacade.getListaExpedientePorEmpresaYCta(idEmpresa, idCuenta);
		List<Expediente>  listaExpedienteTemp =  new ArrayList<Expediente>();
		
		for (Expediente expediente : listaExpediente) {
			
			CreditoId creditoId = new CreditoId();
			creditoId.setIntItemCredito(expediente.getIntItemCredito());
			creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
			creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
			Credito credito = null;
			credito = creditoFacade.getCreditoPorIdCredito(creditoId);
			
			if (credito == null){
				 esValidoCaso = false;
				 messageValidation = "No existe la configuración de crédito del Expediente";
			    throw new BusinessException("No existe la configuración de crédito del Expediente.");
			   
			}
			
			if (credito.getIntParaMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
				   expediente.setBdSaldoCreditoSoles(expediente.getBdSaldoCredito()) ;
				   expediente.setBdMontoAbono(expediente.getBdSaldoCredito());
			}else{
				   TipoCambio tipoCambio =   generalFacade.getTipoCambioActualPorClaseYMoneda(idEmpresa,Constante.PARAM_T_TIPOCAMBIO_CLASE_BANCARIA,credito.getIntParaMonedaCod());
				   BigDecimal bdSaldoCreditoSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoCredito());
				   expediente.setBdSaldoCreditoSoles(bdSaldoCreditoSoles) ;
				   expediente.setBdMontoAbono(bdSaldoCreditoSoles);
				   
			}
				  
			
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)){
				montoSaldoPrestamo = montoSaldoPrestamo.add(expediente.getBdSaldoCredito());
				montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(expediente.getBdSaldoCreditoSoles());
				expediente.setStrDescripcion("Amortización");
				listaExpedienteTemp.add(expediente);
			}else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)){
				montoSaldoOrdenCredito      = montoSaldoOrdenCredito.add(expediente.getBdSaldoCredito());
				montoSaldoOrdenCreditoSoles = montoSaldoOrdenCreditoSoles.add(expediente.getBdSaldoCreditoSoles());
				expediente.setBdMontoAbono(montoSaldoPrestamoSoles);
				expediente.setStrDescripcion("Amortización");
				listaExpedienteTemp.add(expediente);
			}
			else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)){
				montoSaldoActividad = montoSaldoActividad.add(expediente.getBdSaldoCredito());
			}
		}
		
		List<CuentaConcepto>  listaCuentaCpto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuenta.getId());
		
		for (CuentaConcepto cuentaConcepto : listaCuentaCpto) {
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_RETIRO)){
				montoSaldoFondoRetiro = montoSaldoFondoRetiro.add(cuentaConcepto.getBdSaldo());
			}
			else
			if (cuentaConcepto.getId().getIntItemCuentaConcepto().equals(Constante.CAPTACION_FDO_SEPELIO)){
				montoSaldoFondoSepelio = montoSaldoFondoSepelio.add(cuentaConcepto.getBdSaldo());
			}
		}
		
	    List<DescuentoIndebido>	 listaDesctoIndebido = boDescuentoIndebido.getListPorEmpYCta(idEmpresa, idCuenta);
	    
	    
	    for (DescuentoIndebido descuentoIndebido : listaDesctoIndebido) {
	    	//4
	    	if (descuentoIndebido.getIntParaEstadoPagadocod().equals(Constante.PARAM_T_ESTADO_PAGO_PENDIENTE)){
	    		 messageValidation = "El socio tienen pendientes solicitudes de descuento indebido pendiente de pago.";
	    		 esValidoCaso = false;
				 return false;
	    	}
		}
	    
	    
	    List<Movimiento> listMovimiento =  conceptoFacade.getListaMovimientoPorCtaPersonaConceptoGeneral(idEmpresa, idCuenta, idPersona, Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR);
  	  
	    //Obtemos Unico Periodo
	    Set<Integer> periodos = new HashSet<Integer>();
	    for (Movimiento movimiento : listMovimiento) {
	    	periodos.add(movimiento.getIntPeriodoPlanilla());
		}
	   
	    for (Iterator iterator = periodos.iterator(); iterator.hasNext();) {
	    	Integer intPeriodos = (Integer) iterator.next();
	        BigDecimal saldoMonto = obtenerSaldoDstoIndebidoPorPeriodo(intPeriodos,listMovimiento);
	        montoSaldoDescuentoIndebido = montoSaldoDescuentoIndebido.add(saldoMonto);
	   }
	    
		
		//
		if (montoSaldoPrestamo.compareTo(new BigDecimal(0)) == 1 ||  montoSaldoOrdenCredito.compareTo(new BigDecimal(0)) == 1){
			
			 if (montoSaldoActividad.compareTo(new BigDecimal(0)) == 1){
				 esValidoCaso = false;
				 messageValidation = "Debe anular las actividades pendientes antes de realizar este tipo de transferencia.";
				 return false;
			 }
			 ExpedientePrevisionId expPreId = new ExpedientePrevisionId();
			 expPreId.setIntPersEmpresaPk(idEmpresa);
			 expPreId.setIntCuentaPk(idCuenta);
			 
			 List<ExpedientePrevisionComp> listaExpPrevision = previsionFacade.getListaExpedienteCreditoPorEmpresaYCuenta(expPreId);
			 
			 if (listaExpPrevision != null && listaExpPrevision.size() > 0)
			 for (ExpedientePrevisionComp expedientePrevisionComp : listaExpPrevision) {
				  
				EstadoPrevision estado = expedientePrevisionComp.getEstadoPrevision();
				//2
				if (expedientePrevisionComp.getExpedientePrevision().getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_SEPELIO)){
					if (estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						 esValidoCaso = false;
						 messageValidation = "El socio tiene pendiente solicitudes de previsión social.";
						 return false;
					}
				}
				else
				if (expedientePrevisionComp.getExpedientePrevision().getIntParaTipoCaptacion().equals(Constante.CAPTACION_FDO_RETIRO)){
					if (estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						 esValidoCaso = false;
						 messageValidation = "El socio tiene pendiente solicitudes de previsión social.";
						 return false;
					}
				}
				else
				if (expedientePrevisionComp.getExpedientePrevision().getIntParaTipoCaptacion().equals(Constante.CAPTACION_AES)){
					if (estado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)){
						 esValidoCaso = false;
						 messageValidation = "El socio tiene pendiente solicitudes de previsión social.";
						 return false;
					}
				}
						
			 }
			 
		   //3
			 if (montoSaldoFondoRetiro.compareTo(new BigDecimal(0)) == 1){
				 esValidoCaso = false;
				 messageValidation = "El socio tiene pendiente beneficio de fondo de retiro, debe ingresarlo antes de realizar este tipo de transferencia.";
				 return false;
			 }
		   //4 
			 if (montoSaldoFondoSepelio.compareTo(new BigDecimal(0)) == 1){
				 esValidoCaso = false;
				 messageValidation = "El socio tiene pendiente beneficio de fondo de sepelio, debe ingresarlo antes de realizar este tipo de transferencia.";
				 return false;
			 }
			 
			 //6
			 if (montoSaldoDescuentoIndebido.compareTo(new BigDecimal(0)) == 1){
				 esValidoCaso = false;
				 messageValidation = "El socio tienen pendientes de transferencia montos en cuentas por pagar.";
				 return false;
			 }
			
			if (esValidoCaso){
				
				 Expediente expediente = new Expediente();
				 expediente.setChecked(true);
				 bgMontoTransferencia = montoSaldoPrestamoSoles.add(montoSaldoOrdenCreditoSoles);
				 bgMontoSaldoTotalAbono = bgMontoTransferencia;
				 bgMontoSaldoAportaciones = null;;
				 expediente.setBdSaldoCreditoSoles(bgMontoTransferencia);
				 expediente.setBdSaldoCredito(bgMontoTransferencia);
				 expediente.setStrDescripcion("Seguro Desgravamen");
				 listaExpedienteTemp.add(expediente);
				 beanListaExpediente = listaExpedienteTemp;
			}
			 
		}
		else{
			 messageValidation = "El socio no tiene saldo de deuda.";
			 esValidoCaso = false;
			 return false;
		}
		
		return esValidoCaso;
	}
	
	
	private boolean verCasoFallecimientoSocio() throws EJBFactoryException,BusinessException{
		ConceptoFacadeRemote   conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		PrevisionFacadeRemote  previsionFacade = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
		DescuentoIndebidoBO    boDescuentoIndebido = (DescuentoIndebidoBO)TumiFactory.get(DescuentoIndebidoBO.class);
		CreditoFacadeRemote    creditoFacade     = (CreditoFacadeRemote)EJBFactory.getRemote(CreditoFacadeRemote.class);
		GeneralFacadeRemote    generalFacade     = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class); 
 	   
		//Declaracion
		bgMontoSaldoTotalAbono = null; 
		bgMontoTransferencia = null;
		getBeanSolCtaCte().getSolCtaCteTipo().setRadioOpcionTransferencias(1);
		radioEntreConceptos = true;
		beanListaExpediente = null;
		messageValidation = "";
		BigDecimal montoSaldoPrestamo       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCredito        = new BigDecimal(0);
		
		
		BigDecimal montoSaldoPrestamoSoles       	 = new BigDecimal(0);
		BigDecimal montoSaldoOrdenCreditoSoles        = new BigDecimal(0);
		
		
		
		Cuenta cuenta = socioCom.getCuenta();
		Integer idPersona = socioCom.getSocio().getId().getIntIdPersona();
		Integer idEmpresa = cuenta.getId().getIntPersEmpresaPk();
		Integer idCuenta  = cuenta.getId().getIntCuenta();
		
		//Validaciones
		List<Expediente>  listaExpediente =  conceptoFacade.getListaExpedientePorEmpresaYCta(idEmpresa, idCuenta);
		List<Expediente>  listaExpedienteTemp =  new ArrayList<Expediente>();
		
		for (Expediente expediente : listaExpediente) {
			
			CreditoId creditoId = new CreditoId();
			creditoId.setIntItemCredito(expediente.getIntItemCredito());
			creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
			creditoId.setIntPersEmpresaPk(expediente.getIntPersEmpresaCreditoPk());
			Credito credito = null;
			credito = creditoFacade.getCreditoPorIdCredito(creditoId);
			
			if (credito == null){
			    throw new BusinessException("No existe la configuración de crédito.");
			}
			
			if (credito.getIntParaMonedaCod().equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
				   expediente.setBdSaldoCreditoSoles(expediente.getBdSaldoCredito()) ;
			}else{
				   TipoCambio tipoCambio =   generalFacade.getTipoCambioActualPorClaseYMoneda(idEmpresa,Constante.PARAM_T_TIPOCAMBIO_CLASE_BANCARIA,credito.getIntParaMonedaCod());
				   BigDecimal bdSaldoCreditoSoles = tipoCambio.getBdPromedio().multiply(expediente.getBdSaldoCredito());
				   expediente.setBdSaldoCreditoSoles(bdSaldoCreditoSoles) ;
			}
				  
			
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)){
				montoSaldoPrestamo = montoSaldoPrestamo.add(expediente.getBdSaldoCredito());
				montoSaldoPrestamoSoles = montoSaldoPrestamoSoles.add(expediente.getBdSaldoCreditoSoles());
				
				listaExpedienteTemp.add(expediente);
			}else
			if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)){
				montoSaldoOrdenCredito      = montoSaldoOrdenCredito.add(expediente.getBdSaldoCredito());
				montoSaldoOrdenCreditoSoles = montoSaldoOrdenCreditoSoles.add(expediente.getBdSaldoCreditoSoles());
				listaExpedienteTemp.add(expediente);
			}
			
		}
		
		    //
			     Expediente expediente = new Expediente();
				 expediente.setChecked(true);
				 bgMontoTransferencia = montoSaldoPrestamoSoles.add(montoSaldoOrdenCreditoSoles);
				 expediente.setBdSaldoCreditoSoles(bgMontoTransferencia);
				 expediente.setBdSaldoCredito(bgMontoTransferencia);
				 listaExpedienteTemp.add(expediente);
				 beanListaExpediente = listaExpedienteTemp;
			
			 
		
		return true;
	}
	
   private BigDecimal obtenerSaldoDstoIndebidoPorPeriodo(Integer perdiodo, List<Movimiento> lista){
		
		BigDecimal montoSaldo  = new BigDecimal(0);
		BigDecimal montoDebe   = new BigDecimal(0);
		BigDecimal montoHaber  = new BigDecimal(0);
		for (Movimiento movimiento : lista) {
			
			if (perdiodo.equals(movimiento.getIntPeriodoPlanilla())){
			    if (movimiento.getIntParaTipoCargoAbono() != null && movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)){
				      if (movimiento.getBdMontoMovimiento() == null) movimiento.setBdMontoMovimiento(new BigDecimal(0));
					  montoHaber = montoHaber.add(movimiento.getBdMontoMovimiento());
				}
			   
				if (movimiento.getIntParaTipoCargoAbono() != null && movimiento.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)){
					  if (movimiento.getBdMontoMovimiento() == null) movimiento.setBdMontoMovimiento(new BigDecimal(0));
					  montoDebe  = montoHaber.add(movimiento.getBdMontoMovimiento());
				}
			}
		 }
				
		montoSaldo = montoDebe.subtract(montoHaber);
		
		return montoSaldo;
	}

public BigDecimal getBgMontoTransferencia() {
	return bgMontoTransferencia;
}

public void setBgMontoTransferencia(BigDecimal bgMontoTransferencia) {
	this.bgMontoTransferencia = bgMontoTransferencia;
}

public List<Expediente> getBeanListaExpediente() {
	return beanListaExpediente;
}

public void setBeanListaExpediente(List<Expediente> beanListaExpediente) {
	this.beanListaExpediente = beanListaExpediente;
}

public BigDecimal getBgMontoSaldoTotalAbono() {
	return bgMontoSaldoTotalAbono;
}

public void setBgMontoSaldoTotalAbono(BigDecimal bgMontoSaldoTotalAbono) {
	this.bgMontoSaldoTotalAbono = bgMontoSaldoTotalAbono;
}

public BigDecimal getBgMontoSaldoAportaciones() {
	return bgMontoSaldoAportaciones;
}

public void setBgMontoSaldoAportaciones(BigDecimal bgMontoSaldoAportaciones) {
	this.bgMontoSaldoAportaciones = bgMontoSaldoAportaciones;
}

public SocioComp getSocioComGarante() {
	return socioComGarante;
}

public void setSocioComGarante(SocioComp socioComGarante) {
	this.socioComGarante = socioComGarante;
}

public String getStrDesSucursalGarante() {
	return strDesSucursalGarante;
}

public void setStrDesSucursalGarante(String strDesSucursalGarante) {
	this.strDesSucursalGarante = strDesSucursalGarante;
}

public String getStrDesModaliadTipoSocioGarante() {
	return strDesModaliadTipoSocioGarante;
}

public void setStrDesModaliadTipoSocioGarante(
		String strDesModaliadTipoSocioGarante) {
	this.strDesModaliadTipoSocioGarante = strDesModaliadTipoSocioGarante;
}



public String getStrDescEntidadGarante() {
	return strDescEntidadGarante;
}

public void setStrDescEntidadGarante(String strDescEntidadGarante) {
	this.strDescEntidadGarante = strDescEntidadGarante;
}

public void setStrFechSocioComp(String strFechSocioComp) {
	this.strFechSocioComp = strFechSocioComp;
}
  
public void listarSociosGarantes(ActionEvent event){
	        List<GarantiaCreditoComp> listaGarantes = new ArrayList<GarantiaCreditoComp> ();
			
			try{
				
				GarantiaCreditoFacadeRemote garantiaCreditoFacade = (GarantiaCreditoFacadeRemote)EJBFactory.getRemote(GarantiaCreditoFacadeRemote.class);
				List<Expediente> listaExpediente = beanListaExpediente;
				
				if (listaExpediente != null && listaExpediente.size() > 0)
				for (Expediente expediente : listaExpediente) {
					    ExpedienteCreditoId expId = new ExpedienteCreditoId();
						expId.setIntCuentaPk(expediente.getId().getIntCuentaPk());
						expId.setIntItemExpediente(expediente.getId().getIntItemExpediente());
						expId.setIntItemDetExpediente(expediente.getId().getIntItemExpedienteDetalle());
						expId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
						//solicitudPrestamoFacade.getExpedienteCreditoPorId(expId);
						List<GarantiaCreditoComp> listaGaranCredComp =  garantiaCreditoFacade.getListaGarantiaCreditoCompPorExpediente(expId);
						
						for (GarantiaCreditoComp garantiaCreditoComp : listaGaranCredComp) {
							ExpedienteCredito expCredito = new ExpedienteCredito();
							expCredito.setId(new ExpedienteCreditoId());
							expCredito.getId().setIntItemExpediente(expId.getIntItemExpediente());
							expCredito.getId().setIntItemDetExpediente(expId.getIntItemDetExpediente());
							garantiaCreditoComp.setExpediente(expCredito);
							garantiaCreditoComp.getExpediente().setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
							garantiaCreditoComp.getExpediente().setBdMontoTotal(expediente.getBdMontoTotal());
						    listaGarantes.add(garantiaCreditoComp);
						}
				}
			listGaranteComp = listarGarantes(listaGarantes);
			
			} catch (EJBFactoryException e) {
					e.printStackTrace();
			} catch (BusinessException e) {
					e.printStackTrace();
			}
			
 }
   
	public List<SocioComp> listarGarantes(List<GarantiaCreditoComp> lista) throws EJBFactoryException,BusinessException{
	  CuentaFacadeRemote cuentaFacade = (CuentaFacadeRemote)EJBFactory.getRemote(CuentaFacadeRemote.class);
      List<SocioComp> listaSocioGarante = new ArrayList<SocioComp>();
		for (GarantiaCreditoComp garantiaCreditoCom : lista) {
		      listaSocioGarante.add(garantiaCreditoCom.getSocioComp());
		}
		return listaSocioGarante;
	}
	
	public void actualizarMontoTransferencia() {
		log.info("----------cuentaCteController.actualizarMontoTransferencia----------");
		try {
			  BigDecimal sumaMontoAbono = new BigDecimal(0);
			  for (Expediente expediente : beanListaExpediente) {
			  if (expediente.getBdMontoAbono() != null){
						  sumaMontoAbono = sumaMontoAbono.add(expediente.getBdMontoAbono());
					  }
			  }
			  for (int i = 0; i <  beanListaExpediente.size(); i++) {
				  Expediente expediente = (Expediente)beanListaExpediente.get(i);
				  if (expediente.getStrDescripcion() != null && expediente.getStrDescripcion().equals("Aporte")){
					  expediente.setBdSaldoCredito(sumaMontoAbono);
					  expediente.setBdSaldoCreditoSoles(sumaMontoAbono);
					  
					  beanListaExpediente.set(i, expediente);
						  break;
				  }
			  }
				  
			  bgMontoTransferencia   = sumaMontoAbono;
			  bgMontoSaldoTotalAbono = sumaMontoAbono;
		} catch (Exception e) {
			log.error("error: " + e);
		}
	}
	
	public BigDecimal obtieneInteresDiario(Expediente  exp) throws EJBFactoryException,BusinessException{
		 log.info("----------- obtieneInteresDiario iniciando obtieneInteresDiario---------------------------");
		Timestamp tsFechaInicio 				= null; 
		Timestamp tsFechaFinal 					= null; 		
		ConceptoFacadeRemote remoteConcepto 	= null;
		List<EstadoCredito>  listaEstadoCredito = null;
		List<InteresCancelado> listaInteresCancelado = null;
		SolicitudPrestamoFacadeRemote solicitudPrestamofacade = null;
		BigDecimal mtoSaldoDetalle				 = new BigDecimal(0);
		ConceptoFacadeRemote     conceptoFacade 	= null;
		Expediente expediente = null;
		try{
			remoteConcepto = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			solicitudPrestamofacade = (SolicitudPrestamoFacadeRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeRemote.class);
			conceptoFacade      = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			listaInteresCancelado =  remoteConcepto.getListaInteresCanceladoPorExpedienteCredito(exp.getId());
			if(listaInteresCancelado != null && !listaInteresCancelado.isEmpty()){
				log.info("----------- entro a interescancelado---------------------------");
				for(InteresCancelado interesCancelado: listaInteresCancelado){
					tsFechaInicio = interesCancelado.getTsFechaMovimiento();
					//sumar un dia
					tsFechaInicio = UtilCobranza.sumarUnDiaAFecha(tsFechaInicio);
					break;
				}
			}else{
				log.info("----------- entro a estadocredito---------------------------");
					ExpedienteCreditoId pId = new ExpedienteCreditoId();
					pId.setIntPersEmpresaPk(exp.getId().getIntPersEmpresaPk());
					pId.setIntCuentaPk(exp.getId().getIntCuentaPk());
					pId.setIntItemExpediente(exp.getId().getIntItemExpediente());
					pId.setIntItemDetExpediente(exp.getId().getIntItemExpedienteDetalle());
					listaEstadoCredito = solicitudPrestamofacade.getListaEstadosPorExpedienteCreditoId(pId);
					if(listaEstadoCredito != null && !listaEstadoCredito.isEmpty()){
						for(EstadoCredito estadoCredito:listaEstadoCredito){
							if(estadoCredito.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO) == 0){
								tsFechaInicio =  estadoCredito.getTsFechaEstado();
								break;
							}
						}
					}
				}
			tsFechaFinal =  UtilCobranza.obtieneFechaActualEnTimesTamp();
			java.sql.Date      dateFechaInicio      = new           java.sql.Date(tsFechaInicio.getTime());
			java.sql.Date      dateFechaFin         = new           java.sql.Date(tsFechaFinal.getTime());
			log.info("FECHA INICIO");
			log.info(dateFechaInicio);
			log.info("FECHA FIN");
			log.info(dateFechaFin);
			int intNumerosDias = (int)( (dateFechaFin.getTime() - dateFechaInicio.getTime()) / (1000 * 60 * 60 * 24) );
			expediente = conceptoFacade.getExpedientePorPK(exp.getId());
			mtoSaldoDetalle = expediente.getBdPorcentajeInteres().multiply(expediente.getBdSaldoCredito()).multiply(new BigDecimal(intNumerosDias));
			log.info(expediente.getBdPorcentajeInteres());
			log.info(expediente.getBdSaldoCredito());
			log.info(intNumerosDias);
			log.info(mtoSaldoDetalle);
			mtoSaldoDetalle = mtoSaldoDetalle.divide(new BigDecimal(3000),2,RoundingMode.HALF_UP); //a.divide(b, 2, RoundingMode.HALF_UP)	
			log.info(mtoSaldoDetalle);
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		return mtoSaldoDetalle;
	}
	
	public BigDecimal obtieneMtoSaldoDettalleCronPorCptoGenaralAlPeriodo(Expediente  exp,Date fechaTrans) throws EJBFactoryException,BusinessException{
		BigDecimal mtoSaldoDetalle = new BigDecimal(0);
		
		try{
		ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
		
				      if (exp.getStrDescripcion() != null){
				    	  
		 				   Integer tipConceptoGeneral = 0;
		 				   if (exp.getStrDescripcion().equalsIgnoreCase("Amortización")){
		 					   tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION;
		 				   }
//		 				   else
//		 				   if (exp.getStrDescripcion().equalsIgnoreCase("Interes")){
//		 					   tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES;   
//			    		   }  
		 				   else
		                   if (exp.getStrDescripcion().equalsIgnoreCase("Mora")){
		                 	   tipConceptoGeneral = Constante.PARAM_T_TIPOCONCEPTOGENERAL_MORA;
		 				   }
		                   
		                    String  mesTransf     = UtilCobranza.obtieneMesCadena(fechaTrans);
	                 	    String  anioTransf    = UtilCobranza.obtieneAnio(fechaTrans);
	                 	    Integer periodoTransf = new Integer(anioTransf+mesTransf);
		                    
		                   if (exp.getId().getIntItemExpediente() != null && exp.getId().getIntItemExpedienteDetalle() != null){
		                 	  List<Cronograma> lista = conceptoFacade.getListaCronogramaPorPkExpediente(exp.getId());
		                 	  for (Cronograma cronograma : lista) {
		                 		  String  mesCron         = UtilCobranza.obtieneMesCadena(cronograma.getTsFechaVencimiento());
		                 		  String  anioCron        = UtilCobranza.obtieneAnio(cronograma.getTsFechaVencimiento());
		                 		  Integer periodoCron  = new Integer(anioCron+mesCron);
		                 		  
		                 		  if (periodoCron.compareTo(periodoTransf) == -1 || periodoTransf.compareTo(periodoCron) == 0){
			                 		  if (cronograma.getIntParaTipoConceptoCreditoCod().equals(tipConceptoGeneral)){
			                 			  mtoSaldoDetalle = mtoSaldoDetalle.add(cronograma.getBdSaldoDetalleCredito());
			                 		  }
		                 		  }
							  }
		                   }
		 			   
		 		     }
		   
			} catch (EJBFactoryException e) {
					e.printStackTrace();
			} catch (BusinessException e) {
					e.printStackTrace();
			}
			
	     return mtoSaldoDetalle;
 	 }
	
    public void destribuirMontoCargo(){
    	
    	BigDecimal montoCargo = new BigDecimal(0);
    	for (Expediente expe : beanListaExpediente) {
			
    		if (expe.getChecked() != null && expe.getChecked()){
    			montoCargo = expe.getBdSaldoCreditoSoles();
    			break;
    		}
		} 
    	
       try{
    	
    	asignarMontoAbonoSegunMontoSaldoDetyOrdenPrioridad(beanListaExpediente,montoCargo);
    	bgMontoTransferencia   = montoCargo;
		bgMontoSaldoTotalAbono = bgMontoTransferencia;
		
	    } catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
			
    }
    
    private BigDecimal calcularDelConceptoPago(CuentaConcepto cuentaConcepto) throws EJBFactoryException,BusinessException{
    	log.info("-------   calcularDelConceptoPago()          ------------");
    	Timestamp tsFechaFinal 					= null; 
    	Timestamp tsFechaInicio					= null;
    	ConceptoFacadeRemote  conceptoFacade  = null;
        conceptoFacade  = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);      
    	BigDecimal monto = new BigDecimal(0); 
    	 boolean esCancelatorio = false;
    	 List<ConceptoPago> listaConceptoPago = null;
    	 CuentaConceptoDetalle lconceptoDetalle = null;
    	 try{
    	 List<CuentaConceptoDetalle> listaCtaCptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(cuentaConcepto.getId());
  		if(listaCtaCptoDetalle != null &&  !listaCtaCptoDetalle.isEmpty()){
  			for (CuentaConceptoDetalle cuentaConceptoDetalle : listaCtaCptoDetalle) {
  	   			if (cuentaConceptoDetalle.getTsFin() == null){
  	   				if (cuentaConceptoDetalle.getIntParaTipoDescuentoCod().equals(Constante.PARAM_T_TIPODESCUENTO_CANCELATORIO)){
  	   					esCancelatorio = true;
  	   					lconceptoDetalle = cuentaConceptoDetalle;
  	   					break;
  	   				}
  	   				lconceptoDetalle = cuentaConceptoDetalle;
  	   			}
  	 		}
  		}
    	 //cancelatorio = 2 acumulativo = 1
  		if (esCancelatorio){
  			//mandar su concepto nada mas
  			monto = lconceptoDetalle.getBdMontoConcepto();
  		}else{
  			log.info("-------  es acumulativo          ------------");
  			//si es acumulativo ir a conceptoPago traer los saldos y mandarle su concepto
  			listaConceptoPago =  conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(lconceptoDetalle.getId());
  			if(listaConceptoPago != null && !listaConceptoPago.isEmpty()){
  				log.info("-------  lista conceptoPago tiene data          ------------");
  				for(ConceptoPago conceptoPago:listaConceptoPago){
  					monto = monto.add(conceptoPago.getBdMontoSaldo());
  				}
  				monto = monto.add(lconceptoDetalle.getBdMontoConcepto());
  			}else{//de la tabla cuentaconceptoDetalle agarro la fechaInicio con la fecha actual veo el numero de dias multiplicado por su concepto
  				log.info("-------  lista conceptoPago vacia           ------------>");
  				tsFechaInicio = lconceptoDetalle.getTsInicio();
  				tsFechaFinal  =  UtilCobranza.obtieneFechaActualEnTimesTamp();
  				
  				java.sql.Date      dateFechaInicio      = new           java.sql.Date(tsFechaInicio.getTime());
  				java.sql.Date      dateFechaFin         = new           java.sql.Date(tsFechaFinal.getTime());
  				log.info("FECHA INICIO------------>"+dateFechaInicio);  				
  				
  				Date dateFI = new Date(dateFechaInicio.getTime());
				String mes = UtilCobranza.obtieneMesCadena(dateFI);
				String anio = UtilCobranza.obtieneAnioCadena(dateFI);
				String peridoFI = anio+mes;
				log.info("mes INICIO------------>"+mes);  
				log.info("anio INICIO------------>"+anio); 
  				log.info("peridoFI INICIO------------>"+peridoFI);  
				
				Date dateFF = new Date(dateFechaFin.getTime());
				String mesF = UtilCobranza.obtieneMesCadena(dateFF);
				String anioF = UtilCobranza.obtieneAnioCadena(dateFF);
				String peridoFF = anioF + mesF;
				log.info("FECHA FIN------------>"+dateFechaFin);  
				log.info("mes FIN------------>"+mesF);  
				log.info("anio FIN------------>"+anioF); 
  				log.info("peridoFI FIN------------>"+peridoFF); 
				SimpleDateFormat format = new SimpleDateFormat("yyyyMM");													
				Date d1 = null;
				Date d2 = null;
				d1 = format.parse(peridoFI);
				d2 = format.parse(peridoFF);
				
				DateTime dt1 = new DateTime(d1);
				DateTime dt2 = new DateTime(d2);
				Integer nromeses	= Months.monthsBetween(dt1, dt2).getMonths() + 1 ;  				
  				log.info(" nromeses------------>"+nromeses);  
  				monto =  lconceptoDetalle.getBdMontoConcepto().multiply(new BigDecimal(nromeses));  				
  				log.info(" monto------------>"+monto);
  			}
  		}
    	 }catch(Exception e){
 			throw new BusinessException(e);
 		} 
    	return	monto;
    }
    
	
    
	public Boolean getEsEditableMontoSaldoAbono() {
		return esEditableMontoSaldoAbono;
	}

	public void setEsEditableMontoSaldoAbono(Boolean esEditableMontoSaldoAbono) {
		this.esEditableMontoSaldoAbono = esEditableMontoSaldoAbono;
	}

	public String getStrPeriodoTrans() {
		return strPeriodoTrans;
	}

	public void setStrPeriodoTrans(String strPeriodoTrans) {
		this.strPeriodoTrans = strPeriodoTrans;
	}

	public void setDeshabilitaCondicionFinal(boolean deshabilitaCondicionFinal) {
		this.deshabilitaCondicionFinal = deshabilitaCondicionFinal;
	}

	public boolean isDeshabilitaCondicionFinal() {
		return deshabilitaCondicionFinal;
	}
	
	
	
	
}

