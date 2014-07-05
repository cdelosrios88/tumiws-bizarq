package pe.com.tumi.cobranza.solicitudcta.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.planilla.bo.EstadoSolicitudCtaCteBO;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCteId;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeLocal;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.common.PermisoUtil;
import pe.com.tumi.common.controller.GenericController;
import pe.com.tumi.common.controller.UtilCobranza;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FacesContextUtil;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.facade.CuentaFacadeRemote;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeRemote;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeRemote;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.fileupload.FileUploadController;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.PersonaEmpresaPK;
import pe.com.tumi.persona.core.domain.PersonaRol;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;


/************************************************************************/
/* Nombre de la clase: SolicitudCtaCteController                        */
/* Funcionalidad : Clase para realizar un solicitut xxx     		    */
/*                                                                      */
/* Ref. : 																*/
/* Autor : FRAMIREZ														*/
/* Versión : V1 														*/
/* Fecha creación : 01/01/2013 											*/
/* **********************************************************************/

public class SolicitudCtaCteController extends GenericController {
	
	//Variables locales beanSolCtaCte
	protected  static Logger 		      log = Logger.getLogger(SolicitudCtaCteController.class);
	private	  SolicitudCtaCte		  beanSolCtaCte;
	private   List<SolicitudCtaCte>   beanListSolCtaCte;
	private   SocioComp socioCom;
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
	private Archivo		archivoDocSolicitudTemp1;
	private Archivo		archivoDocSolicitudTemp2;
	
	
	
	private boolean     formBotonQuitarArchDisabled;
	private boolean     intTipoRolDisabled;
	
	private List<Tabla> listaTipoCondicion;
	private List<Tabla> listaCondicion;
	
	  
	// Parámetros de busqueda Enlace Recibo
	private Integer intCboSucursal;
	private Integer intCboTipoSolicitud;
	private Integer intCboEstadoSolicitud;
	private String  strInNroDniSocio;
	private Integer intCboParaTipo;
	//Propiedades de lista de combos.
	
	private 	List<Sucursal> 			listJuridicaSucursal;
	private 	List<Subsucursal> 		listJuridicaSubsucursal;
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
	
	// Inicio - GTorresBrousset 21.abr.2014
	private boolean btnVisible;
	// Fin - GTorresBrousset 21.abr.2014
	
	//Propiedades para renderizar
	private boolean solicitudCtaCteRendered;
	private boolean solicitudCtaCteFormRendered;
	
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
	
	//Auxiliar
	
	
	private String strFechSocioComp; 
	private String selAdjDocumento; 
	private List<Tabla> listaMeses;
	
	private Usuario usuario;
	private Integer PERSONA_USUARIO;
	private boolean poseePermiso;
	//-------------------------------------------------------------------------------------------------------------------------------------------
	//Métodos de GestionCobranzaController para Mantenimientos
	//-------------------------------------------------------------------------------------------------------------------------------------------
	public SolicitudCtaCteController() {
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_SOLICITUD);
		
		if(usuario != null && poseePermiso) {
			cargarValoresIniciales();
		} else {
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		//inicio(null);
	}
	
	private void cargarUsuario() {
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}
	
	public String getLimpiarSolicitud() {
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_SOLICITUD);
		if(usuario!= null && poseePermiso) {
			cargarValoresIniciales();
		} else {
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		return "";
	}
	
	public void cargarValoresIniciales() {
		try {
			inicializarFormSolicitudCtaCte();
			beanListSolCtaCte = null;
			btnGrabarDisabled = true;
			solicitudCtaCteRendered = false;
			solicitudCtaCteFormRendered = false;
			// Inicio - GTorresBrousset 21.abr.2014
			btnVisible = false;
			// Fin - GTorresBrousset 21.abr.2014
		} catch(Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	/******************************************************************/
	@SuppressWarnings("unused")
	public void inicio(ActionEvent event){
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
			} else {
				 bolEnlaceRecibo = false;
		    }
		} catch (Exception e) {
			log.error(e);
		}
		
		//inicializarFormSolicitudCtaCte();
		beanListSolCtaCte = null;
		btnGrabarDisabled = true;
		solicitudCtaCteRendered = false;
		solicitudCtaCteFormRendered = false;
		// Inicio - GTorresBrousset 21.abr.2014
		btnVisible = false;
		// Fin - GTorresBrousset 21.abr.2014
	}
	/**************************************************************/
	//Acciones
	public void buscar(ActionEvent envent){
	try {
		    EstadoSolicitudCtaCteBO boEstadoSolicitudCtaCte = (EstadoSolicitudCtaCteBO)TumiFactory.get(EstadoSolicitudCtaCteBO.class);
		
			PlanillaFacadeRemote SocioCompFacade = (PlanillaFacadeRemote)EJBFactory.getRemote(PlanillaFacadeRemote.class);
			
			Integer idSucursal         = getIntCboSucursal();
			Integer idTipoSolicitud    = getIntCboTipoSolicitud();
			String  nroDniSocio        = getStrInNroDniSocio();
			Integer idEstadoSolicitud  = getIntCboEstadoSolicitud();
			
			List<SolicitudCtaCte> listSolCta = null;
			listSolCta = SocioCompFacade.buscarSolicitudCtaCte(SESION_IDEMPRESA, idSucursal, idTipoSolicitud, idEstadoSolicitud, nroDniSocio);

			List<SolicitudCtaCte> listSolCtaTemp = new ArrayList<SolicitudCtaCte>();
			
			 for (SolicitudCtaCte solCtaCte: listSolCta) {
				
				
				List<EstadoSolicitudCtaCte> listaEstado =  boEstadoSolicitudCtaCte.getListaPorSolicitudCtacte(solCtaCte.getId().getIntEmpresasolctacte(), solCtaCte.getId().getIntCcobItemsolctacte());
				 
				if (listaEstado.size() == 1){
						listSolCtaTemp.add(solCtaCte);
					
				}
				
				if (listaEstado.size() == 2){
						if (solCtaCte.getEstSolCtaCte().getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO) || 
							solCtaCte.getEstSolCtaCte().getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ANULADO)){
							listSolCtaTemp.add(solCtaCte);
						}
					
				} 
				
				if (listaEstado.size() == 3){
						if (solCtaCte.getEstSolCtaCte().getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO)){
							listSolCtaTemp.add(solCtaCte);
						}
					
				} 
			 }
			 
			 beanListSolCtaCte = listSolCtaTemp;
			 
			 log.info("beanListSolCtaCte.size: "+beanListSolCtaCte.size());
			 
			 btnAnularDisabled = false;
		} catch (EJBFactoryException e) {
			e.printStackTrace();
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		} catch (BusinessException e) {
			e.printStackTrace();
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSEARCH);
		}
		// Inicio - GTorresBrousset 21.abr.2014
		cancelar(new ActionEvent(null));
		// Fin - GTorresBrousset 21.abr.2014
	}
	
	public void seleccionar(ActionEvent event){
		SolicitudCtaCte  solCtaCte =  (SolicitudCtaCte)event.getComponent().getAttributes().get("item");
		setBeanSolCtaCte(solCtaCte);
		// Inicio - GTorresBrousset 21.abr.2014
		btnVisible = false;
		// Fin - GTorresBrousset 21.abr.2014
		if (getBeanSolCtaCte().getEstSolCtaCte().getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ANULADO)){
			btnAnularDisabled = true;
		} else if (getBeanSolCtaCte().getEstSolCtaCte().getIntParaEstadoSolCtaCte().equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO)){
			btnAnularDisabled = true;
		} else {
			btnAnularDisabled = false;
			// Inicio - GTorresBrousset 21.abr.2014
			btnVisible = true;
			// Fin - GTorresBrousset 21.abr.2014
		}
	}
	
	public void nuevo(ActionEvent event){
		inicializarFormSolicitudCtaCte();
		solicitudCtaCteRendered = false;
		solicitudCtaCteFormRendered = true;
		intTipoRolDisabled = false;
		
	    btnQuitarArchivoDisabled = false;
		txtObservacionDisabled = false;
		cboTipoModalidadDisabled = false;
		cboMesDisabled = false;
		cboAnioDisabled = false;
		chkTipoSolicitudDisabled = false;
		// Inicio - GTorresBrousset 21.abr.2014
		btnVisible = true;
		// Fin - GTorresBrousset 21.abr.2014
	}
	
	public void grabar(ActionEvent event){
		log.info("grabar INICIO");
		PlanillaFacadeLocal planillaFacade = null;
		SolicitudCtaCte solCtaCte = null;
		boolean esValido = true;
		
		//Validar Datos
		esValido = validarForm();
		if (!esValido){
			return;
		}
		
	   //Grabar	
		try{
			/*******************************************************************************/
			inicio(event);
			/*******************************************************************************/
			 
			if (getBeanSolCtaCte().getId().getIntCcobItemsolctacte() == null &&
				getBeanSolCtaCte().getId().getIntEmpresasolctacte() == null){
				
				log.info("getIntCcobItemsolctacte()="+getBeanSolCtaCte().getId().getIntCcobItemsolctacte());
				planillaFacade = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
				getBeanSolCtaCte().getId().setIntEmpresasolctacte(SESION_IDEMPRESA);
				
				if (getStrCboAnio()!=null &&  getIntCboMes() != null){
					 String [] meses = {"","01","02","03","04","05","06","07","08","09","10","11","12"};
					 String mes = "";
					 for (int i = 0; i < meses.length; i++) {
						if (i == getIntCboMes()){
							mes =  meses[i];
						}
					 } 
					String periodo = Integer.parseInt(getStrCboAnio())+""+mes;
					log.info("periodo="+periodo);
					getBeanSolCtaCte().setIntPeriodo(new Integer(periodo));
				}
				
				if (archivoDocSolicitud != null){
					log.info("archivoDocSolicitud");
					Archivo archivo = grabarArchvioDocAdjunto(archivoDocSolicitud);
					getBeanSolCtaCte().setIntParaTipo(archivo.getId().getIntParaTipoCod());
					getBeanSolCtaCte().setIntMaeItemarchivo(archivo.getId().getIntItemArchivo());
					getBeanSolCtaCte().setIntMaeItemhistorico(archivo.getId().getIntItemHistorico());
				}
				
				if (archivoDocSolicitud1 != null){
					log.info("archivoDocSolicitud1");
					Archivo archivo = grabarArchvioDocAdjunto(archivoDocSolicitud1);
					getBeanSolCtaCte().setIntParaTipo1(archivo.getId().getIntParaTipoCod());
					getBeanSolCtaCte().setIntMaeItemarchivo1(archivo.getId().getIntItemArchivo());
					getBeanSolCtaCte().setIntMaeItemhistorico1(archivo.getId().getIntItemHistorico());
				}
				
				if (archivoDocSolicitud2 != null){
					log.info("archivoDocSolicitud2");
					Archivo archivo = grabarArchvioDocAdjunto(archivoDocSolicitud2);
					getBeanSolCtaCte().setIntParaTipo2(archivo.getId().getIntParaTipoCod());
					getBeanSolCtaCte().setIntMaeItemarchivo2(archivo.getId().getIntItemArchivo());
					getBeanSolCtaCte().setIntMaeItemhistorico2(archivo.getId().getIntItemHistorico());
				}
				//Datos Estado Cuenta
				EstadoSolicitudCtaCte estSolCtaCte = new EstadoSolicitudCtaCte();
				
				estSolCtaCte.setDtEsccFechaEstado(new Date());
				estSolCtaCte.setIntParaEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_PENDIENTE);
				estSolCtaCte.setIntSucuIduSusucursal(SESION_IDSUCURSAL);
				estSolCtaCte.setIntSudeIduSusubsucursal(SESION_IDSUBCURSAL);
				estSolCtaCte.setIntPersUsuarioEstado(SESION_IDUSUARIO);
				estSolCtaCte.setIntPersEmpresaEstado(SESION_IDEMPRESA);
				estSolCtaCte.setStrEsccObservacion(getStrObservacion());
				getBeanSolCtaCte().setEstSolCtaCte(estSolCtaCte);
				
				//Graba
				solCtaCte = planillaFacade.grabarSolicitudCtaCte(getBeanSolCtaCte());
				
			} else {
				log.info("getBeanSolCtaCte().getId() es null");
				planillaFacade = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
				
				if (getStrCboAnio()!=null &&  getIntCboMes() != null){
					
					String [] meses = {"","01","02","03","04","05","06","07","08","09","10","11","12"};
					 String mes = "";
					 for (int i = 0; i < meses.length; i++) {
						if (i == getIntCboMes()){
							mes =  meses[i];
						}
					 } 
					String periodo = Integer.parseInt(getStrCboAnio())+""+mes;
					getBeanSolCtaCte().setIntPeriodo(Integer.parseInt(periodo));
				}
				
				if (archivoDocSolicitud != null && archivoDocSolicitudTemp !=  null){
					
					archivoDocSolicitud.getId().setIntItemArchivo(archivoDocSolicitudTemp.getId().getIntItemArchivo());
					archivoDocSolicitud.getId().setIntItemHistorico(archivoDocSolicitudTemp.getId().getIntItemHistorico());
					archivoDocSolicitud.getId().setIntParaTipoCod(archivoDocSolicitudTemp.getId().getIntParaTipoCod());
					
					Archivo archivo = grabarArchvioDocAdjunto(archivoDocSolicitud);
					getBeanSolCtaCte().setIntParaTipo(archivo.getId().getIntParaTipoCod());
					getBeanSolCtaCte().setIntMaeItemarchivo(archivo.getId().getIntItemArchivo());
					getBeanSolCtaCte().setIntMaeItemhistorico(archivo.getId().getIntItemHistorico());
				}
				
				if (archivoDocSolicitud1 != null && archivoDocSolicitudTemp1 !=  null){
					archivoDocSolicitud1.getId().setIntItemArchivo(archivoDocSolicitudTemp1.getId().getIntItemArchivo());
					archivoDocSolicitud1.getId().setIntItemHistorico(archivoDocSolicitudTemp1.getId().getIntItemHistorico());
					archivoDocSolicitud1.getId().setIntParaTipoCod(archivoDocSolicitudTemp1.getId().getIntParaTipoCod());
					
					Archivo archivo = grabarArchvioDocAdjunto(archivoDocSolicitud1);
					getBeanSolCtaCte().setIntParaTipo1(archivo.getId().getIntParaTipoCod());
					getBeanSolCtaCte().setIntMaeItemarchivo1(archivo.getId().getIntItemArchivo());
					getBeanSolCtaCte().setIntMaeItemhistorico1(archivo.getId().getIntItemHistorico());
				}
				
				if (archivoDocSolicitud2 != null && archivoDocSolicitudTemp2 !=  null){
					
					archivoDocSolicitud2.getId().setIntItemArchivo(archivoDocSolicitudTemp2.getId().getIntItemArchivo());
					archivoDocSolicitud2.getId().setIntItemHistorico(archivoDocSolicitudTemp2.getId().getIntItemHistorico());
					archivoDocSolicitud2.getId().setIntParaTipoCod(archivoDocSolicitudTemp2.getId().getIntParaTipoCod());
					
					Archivo archivo = grabarArchvioDocAdjunto(archivoDocSolicitud2);
					getBeanSolCtaCte().setIntParaTipo2(archivo.getId().getIntParaTipoCod());
					getBeanSolCtaCte().setIntMaeItemarchivo2(archivo.getId().getIntItemArchivo());
					getBeanSolCtaCte().setIntMaeItemhistorico2(archivo.getId().getIntItemHistorico());
                }
				
				getBeanSolCtaCte().getEstSolCtaCte().setStrEsccObservacion(getStrObservacion());
				
			   	solCtaCte = planillaFacade.modificarSolicitudCtaCte(getBeanSolCtaCte());
				
			}
			log.info("grabar FIN");	
		} catch (BusinessException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
			log.error(e);
		} catch (EJBFactoryException e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
		    e.printStackTrace();
		    log.error(e);
		} catch (Exception e) {
			FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
		    e.printStackTrace();
		    log.error(e);
		}
		
		if (solCtaCte != null) {
			FacesContextUtil.setMessageSuccess(" " + FacesContextUtil.MESSAGE_SUCCESS_ONSAVE);
		}
		
		solicitudCtaCteFormRendered = false;
		solicitudCtaCteRendered = false;
		beanListSolCtaCte = null;
		btnGrabarDisabled = true;
		// Inicio - GTorresBrousset 21.abr.2014
		btnVisible = false;
		// Fin - GTorresBrousset 21.abr.2014
		buscar(event);
	}

	public void cancelar(ActionEvent event){
		solicitudCtaCteFormRendered = false;
		solicitudCtaCteRendered = false;
		btnGrabarDisabled = true;
		solicitudCtaCteRendered = false;
		intTipoRolDisabled = false;
		// Inicio - GTorresBrousset 21.abr.2014
		btnVisible = false;
		// Fin - GTorresBrousset 21.abr.2014
	}
	
	public void anular(ActionEvent event){
		
		//PlanillaFacadeLocal planillaFacade = null;
		//SolicitudCtaCte solCtaCte = null;
		EstadoSolicitudCtaCte estSolCtaCteResult = null;
		 try{
			 
			    EstadoSolicitudCtaCte estSolCtaCte = new EstadoSolicitudCtaCte();
				
			    EstadoSolicitudCtaCteId id = new EstadoSolicitudCtaCteId();
			    id.setIntPersEmpresaSolctacte(getBeanSolCtaCte().getId().getIntEmpresasolctacte());
			    id.setIntCcobItemSolCtaCte(getBeanSolCtaCte().getId().getIntCcobItemsolctacte());
			    estSolCtaCte.setId(id);
			    estSolCtaCte.setIntParaEstadoSolCtaCte(Constante.PARAM_T_TIPESTADOSOLCITUD_ANULADO);
			    estSolCtaCte.setDtEsccFechaEstado(new Date());
				estSolCtaCte.setIntSucuIduSusucursal(SESION_IDSUCURSAL);
				estSolCtaCte.setIntSudeIduSusubsucursal(SESION_IDSUBCURSAL);
				estSolCtaCte.setIntPersUsuarioEstado(SESION_IDUSUARIO);
				estSolCtaCte.setIntPersEmpresaEstado(SESION_IDEMPRESA);
				getBeanSolCtaCte().setEstSolCtaCte(estSolCtaCte);
		
			  //  planillaFacade = (PlanillaFacadeLocal)EJBFactory.getLocal(PlanillaFacadeLocal.class);
			  
				EstadoSolicitudCtaCteBO boEstadoSolicitudCtaCte = (EstadoSolicitudCtaCteBO)TumiFactory.get(EstadoSolicitudCtaCteBO.class);
				estSolCtaCteResult = boEstadoSolicitudCtaCte.grabarEstadoSolicitudCtaCte(estSolCtaCte);
				
			   //solCtaCte = planillaFacade.anularSolicitudCtaCte(getBeanSolCtaCte());
			 
					
			} catch (BusinessException e) {
				FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
				log.error(e);
				e.printStackTrace();
			}catch(Exception e){
				e.printStackTrace();
				log.error(e);
				FacesContextUtil.setMessageError(FacesContextUtil.MESSAGE_ERROR_ONSAVE);
		  }
			
			if (estSolCtaCteResult != null){
				FacesContextUtil.setMessageSuccess("La Anulación se realizo de manera Exitosa");
			}
			
			
			buscar(null);
			
			solicitudCtaCteRendered = false;
			solicitudCtaCteFormRendered = false;
			btnGrabarDisabled = true;
			// Inicio - GTorresBrousset 21.abr.2014
			btnVisible = false;
			// Fin - GTorresBrousset 21.abr.2014
			
	}
	
	
	public boolean validarForm(){
	 boolean esValido = true;	
	  
	      List<SolicitudCtaCteTipo> listTaTipoSol = getListaTipoSol();
	      getBeanSolCtaCte().setListaSolCtaCteTipo(listTaTipoSol);
		  if (listTaTipoSol== null || listTaTipoSol.size() == 0)
		  {	  
			esValido = false;
			FacesContextUtil.setMessageError("Debe seleccionar al menos un tipo de solicitud.");
		  }
		  
		  for (SolicitudCtaCteTipo solicitudCtaCteTipo : listTaTipoSol) {
			  if (solicitudCtaCteTipo.getStrScctObservacion() == null || solicitudCtaCteTipo.getStrScctObservacion().equals("")){
				  esValido = false;
				  FacesContextUtil.setMessageError("La observación de un los tipos de solicitud seleccionados esta vacio.");
			  }
		  }
		  
		  
		  if (getStrObservacion().equals("")){
			  FacesContextUtil.setMessageError("Debe Completar el campo Observación.");
			  esValido = false;
		  }
	       
		  if (archivoDocSolicitud == null){
			  FacesContextUtil.setMessageError("Debe adjuntar un archivo de sustento de solicitud.");
			  esValido = false;
		  }
		   
		  
	  
	   return esValido;
	}
	
	public void buscarSocio(ActionEvent event){
		log.info("---- buscarSocio()------");
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
		log.info("listSocioComp.size()="+listSocioComp.size());    
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
			
			
			if (socioEstructura.getIntTipoEstructura().equals(Constante.PARAM_T_TIPOESTRUCTURA_ORIGEN)){
				strDescEntidad = nivel +":"+desEntidad+"-"+desSucursal+"-"+tipoSocio+"-"+modalidad;
			}else{
				strDescEntidadPres = nivel +":"+desEntidad+"-"+desSucursal+"-"+tipoSocio+"-"+modalidad;
			}
			
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
		
		solicitudCtaCteRendered = true;
		intTipoRolDisabled = true;
		btnGrabarDisabled = false;
  }

   public void obtener(ActionEvent event){
	   log.info("obtener INICIO");
	    SolicitudCtaCte solCtaCte = null; 
	    archivoDocSolicitud  = null;
	    archivoDocSolicitud1 = null;
	    archivoDocSolicitud2 = null;
	    
	    Integer intEstadoSol =  getBeanSolCtaCte().getEstSolCtaCte().getIntParaEstadoSolCtaCte();
	   
	    
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
					strDescEntidad = nivel +":"+desEntidad+"-"+desSucursal+"-"+tipoSocio+"-"+modalidad;
				}else{
					strDescEntidadPres = nivel +":"+desEntidad+"-"+desSucursal+"-"+tipoSocio+"-"+modalidad;

				}
				
				
			}
	        
	        if (solCtaCte.getIntPeriodo() != null){
	        	   String periodo = solCtaCte.getIntPeriodo().toString();
	        	   log.info("periodo="+periodo);
	        	   strCboAnio = periodo.substring(0, 4);
	        	   intCboMes  = Integer.parseInt(periodo.substring(4,periodo.length()));
	        }
	     
	        GeneralFacadeRemote facade = (GeneralFacadeRemote)EJBFactory.getRemote(GeneralFacadeRemote.class);
	        
	        ArchivoId idArchivo = new ArchivoId();
	        idArchivo.setIntParaTipoCod(solCtaCte.getIntParaTipo());
	        idArchivo.setIntItemArchivo(solCtaCte.getIntMaeItemarchivo());
	        idArchivo.setIntItemHistorico(solCtaCte.getIntMaeItemhistorico());
	        
	        archivoDocSolicitud =  facade.getArchivoPorPK(idArchivo);
	        archivoDocSolicitudTemp = null;
	        
	        ArchivoId idArchivo1 = new ArchivoId();
	        idArchivo1.setIntParaTipoCod(solCtaCte.getIntParaTipo1());
	        idArchivo1.setIntItemArchivo(solCtaCte.getIntMaeItemarchivo1());
	        idArchivo1.setIntItemHistorico(solCtaCte.getIntMaeItemhistorico1());
	        
	        archivoDocSolicitud1 =  facade.getArchivoPorPK(idArchivo1);
	        archivoDocSolicitudTemp1 = null;
	        
	        
	        ArchivoId idArchivo2 = new ArchivoId();
	        idArchivo2.setIntParaTipoCod(solCtaCte.getIntParaTipo2());
	        idArchivo2.setIntItemArchivo(solCtaCte.getIntMaeItemarchivo2());
	        idArchivo2.setIntItemHistorico(solCtaCte.getIntMaeItemhistorico2());
	        
	        archivoDocSolicitud2 =  facade.getArchivoPorPK(idArchivo2);
	        archivoDocSolicitudTemp2 = null;
	        
	        setStrObservacion(solCtaCte.getEstSolCtaCte().getStrEsccObservacion());
	        setBeanSolCtaCte(solCtaCte);
	      
	        log.info("obtener FIN");   
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
			
		solicitudCtaCteFormRendered = true;
		solicitudCtaCteRendered = true;
		intTipoRolDisabled = true;
		btnGrabarDisabled = false;
		
		if (intEstadoSol.equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ANULADO) ||
			intEstadoSol.equals(Constante.PARAM_T_TIPESTADOSOLCITUD_ATENDIDO)){
			btnGrabarDisabled = true;
			btnQuitarArchivoDisabled = true;
			txtObservacionDisabled = true;
			cboTipoModalidadDisabled = true;
			cboMesDisabled = true;
			cboAnioDisabled = true;
			chkTipoSolicitudDisabled = true;
		}else{
			btnGrabarDisabled = false;
			btnQuitarArchivoDisabled = false;
			txtObservacionDisabled = false;
			cboTipoModalidadDisabled = false;
			cboMesDisabled = false;
			cboAnioDisabled = false;
			chkTipoSolicitudDisabled = false;
		}
   }
		
   public List<SolicitudCtaCteTipo> getListaTipoSol(){
	   
	   List<SolicitudCtaCteTipo> listaTipoSol = new ArrayList<SolicitudCtaCteTipo>();
	    if (listaTipoSolicitud != null)
		for (Tabla tipoSol:listaTipoSolicitud){
			if (tipoSol.getChecked() != null && tipoSol.getChecked()){
				SolicitudCtaCteTipo solCtaCteTipo = new SolicitudCtaCteTipo(); 
				solCtaCteTipo.getId().setIntTipoSolicitudctacte(tipoSol.getIntIdDetalle());
				solCtaCteTipo.setIntTaraEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				solCtaCteTipo.setStrScctObservacion(tipoSol.getStrAbreviatura());
				listaTipoSol.add(solCtaCteTipo);
			}
			
		}
		
	  return listaTipoSol;
   }
	
	public void manejarCheched(){
	    if (listaTipoSolicitud != null)
			for (Tabla tipoSol : listaTipoSolicitud){
				if (tipoSol.getChecked() == Boolean.TRUE){
					if(tipoSol.getIntIdDetalle() == 2 || tipoSol.getIntIdDetalle() == 3) {
						cboAnioDisabled = Boolean.FALSE;
						cboMesDisabled = Boolean.FALSE;
						cboTipoModalidadDisabled = Boolean.FALSE;
						break;
					} else {
						cboAnioDisabled = Boolean.TRUE;
						cboMesDisabled = Boolean.TRUE;
						cboTipoModalidadDisabled = Boolean.TRUE;
					}
				}
			}
	}
	
	
	public void inicializarFormSolicitudCtaCte(){
		log.info("-----------------------Debugging enlaceReciboController.inicializarFormEnlaceRecibo-----------------------------");
		
		 setBeanSolCtaCte(new SolicitudCtaCte());
		 strFechSocioComp ="";
		 strObservacion = "";
		 archivoDocSolicitud  = null;
		 archivoDocSolicitud1 = null;
		 archivoDocSolicitud2 = null;
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
	
	public void quitarDocumento1(){
		try{
		   	
			if (getBeanSolCtaCte().getId().getIntCcobItemsolctacte() != null && archivoDocSolicitudTemp1 == null){
			    archivoDocSolicitudTemp1 = archivoDocSolicitud1;
			}
			
			archivoDocSolicitud1 = null;
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			                     fileUploadController.setArchivoDocCobranza(null);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}
	
	public void quitarDocumento2(){
		try{
		   	
			if (getBeanSolCtaCte().getId().getIntCcobItemsolctacte() != null && archivoDocSolicitudTemp2 == null){
			    archivoDocSolicitudTemp2 = archivoDocSolicitud2;
			}
			
			archivoDocSolicitud2 = null;
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			                     fileUploadController.setArchivoDocCobranza(null);
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}

	}
	
	public void seleccionarDoc(ActionEvent event){
		String selecionarDoc = (String)event.getComponent().getAttributes().get("item");
		String uno = "1";
		String dos = "2";
		String tres = "3";
		if (selecionarDoc.equals(uno)) {
		     selAdjDocumento = uno;
		} else if (selecionarDoc.equals(dos)) {
			selAdjDocumento = dos;
		} else if (selecionarDoc.equals(tres)) {
			selAdjDocumento = tres;
		}
	}
	
	public void aceptarAdjuntarDocumento(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			log.info("selAdjDocumento: " + selAdjDocumento);
			String uno = "1";
			String dos = "2";
			String tres = "3";
			if (selAdjDocumento.equals(uno)) {
				archivoDocSolicitud = fileUploadController.getArchivoDocCobranza();
			} else if (selAdjDocumento.equals(dos)) {
			    archivoDocSolicitud1 = fileUploadController.getArchivoDocCobranza();
			} else if(selAdjDocumento.equals(tres)) {
				archivoDocSolicitud2 = fileUploadController.getArchivoDocCobranza();
			}
		} catch(Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	 
	 
	 public Archivo grabarArchvioDocAdjunto(Archivo archivo) throws BusinessException,EJBFactoryException{
			
			Archivo archivoResult= null;
				try {
					  InputStream in = new FileInputStream(archivo.getFile());
					  OutputStream out = new FileOutputStream(archivo.getRutaAntigua());
					try {
						// Transfer bytes from in to out
						byte[] buf = new byte[4096];//Máximo 50MB
						int len;
						while ((len = in.read(buf)) > 0) {
							out.write(buf, 0, len);
						}
					}
					catch(IOException e){
						e.printStackTrace();
						log.error(e.getMessage(),e);
						throw new BusinessException(e);
					}
					catch(Exception e){
						
						e.printStackTrace();
						log.error(e.getMessage(),e);
						throw new BusinessException(e);
					}finally {
						in.close();
						out.close();
					}
				 }catch(Exception e){
					e.printStackTrace();
					log.error(e.getMessage(),e);
					log.error(e.getMessage(),e);
					throw new BusinessException(e);
				 }
			 
			 
			 
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
					 }
					 catch(Exception e){
							System.out.println("El renombrado no se ha podido realizar: " + e);
							log.error(e.getMessage(),e);
							throw new BusinessException(e);
					}
					 
			 
			
				return archivoResult;
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
		
		log.info("-------------------------------------Debugging solicitudCtaCteController.getListJuridicaSucursal-------------------------------------");
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

	public boolean issolicitudCtaCteRendered() {
		return solicitudCtaCteRendered;
	}

	public void setsolicitudCtaCteRendered(boolean solicitudCtaCteRendered) {
		this.solicitudCtaCteRendered = solicitudCtaCteRendered;
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
	log.info("getListaTipoRol INICIO");
   try{		
	   TablaFacadeRemote facade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
	   listaTipoRol =	facade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOROL),"D");
	   if(listaTipoRol != null)
	   {
		   log.info("listaTipoRol="+listaTipoRol.size()); 
	   }else
	   {
		   log.info("listaTipoRol es null");
	   }
	   
   } catch (EJBFactoryException e) {
		e.printStackTrace();
	} catch (BusinessException e) {
		e.printStackTrace();
	}
	log.info("getListaTipoRol FIN");
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
			   listaTipoSolicitud =	facade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOSOLCITUD));
			  
			   
			    List<Tabla> lista = listaTipoSolicitud;
			    List<Tabla> listaTipo = new ArrayList<Tabla>();
			    if (getBeanSolCtaCte().getListaSolCtaCteTipo() != null)
			    {	
					for (Tabla tipoSol:lista){
						for (SolicitudCtaCteTipo solCtaCteTipoSel : getBeanSolCtaCte().getListaSolCtaCteTipo()) {
							if (solCtaCteTipoSel.getId().getIntTipoSolicitudctacte().equals(tipoSol.getIntIdDetalle())){
								tipoSol.setChecked(true);
								tipoSol.setStrAbreviatura(solCtaCteTipoSel.getStrScctObservacion());
								break;
							}
						}
						
						if (tipoSol.getChecked() != null && tipoSol.getChecked()){
						}else{
							tipoSol.setStrAbreviatura("");
						}
						
						listaTipo.add(tipoSol);
					}
				  listaTipoSolicitud = listaTipo;
			    }else{
			    	for (int i = 0; i < listaTipoSolicitud.size(); i++) {
			    		Tabla tables = (Tabla)listaTipoSolicitud.get(i);
			    		tables.setStrAbreviatura("");
			    		listaTipoSolicitud.set(i, tables);
					}
			    }
				
		} catch (EJBFactoryException e) {
				e.printStackTrace();
		} catch (BusinessException e) {
				e.printStackTrace();
		}
		
		if (listaTipoSolicitud != null)tamanioListaTipoSol = listaTipoSolicitud.size();
		return listaTipoSolicitud;
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

	public boolean isSolicitudCtaCteRendered() {
		return solicitudCtaCteRendered;
	}

	public void setSolicitudCtaCteRendered(boolean solicitudCtaCteRendered) {
		this.solicitudCtaCteRendered = solicitudCtaCteRendered;
	}

	public boolean isSolicitudCtaCteFormRendered() {
		return solicitudCtaCteFormRendered;
	}

	public void setSolicitudCtaCteFormRendered(boolean solicitudCtaCteFormRendered) {
		this.solicitudCtaCteFormRendered = solicitudCtaCteFormRendered;
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

	public Archivo getArchivoDocSolicitud1() {
		return archivoDocSolicitud1;
	}

	public void setArchivoDocSolicitud1(Archivo archivoDocSolicitud1) {
		this.archivoDocSolicitud1 = archivoDocSolicitud1;
	}

	public Archivo getArchivoDocSolicitudTemp1() {
		return archivoDocSolicitudTemp1;
	}

	public void setArchivoDocSolicitudTemp1(Archivo archivoDocSolicitudTemp1) {
		this.archivoDocSolicitudTemp1 = archivoDocSolicitudTemp1;
	}

	

	public String getSelAdjDocumento() {
		return selAdjDocumento;
	}

	public void setSelAdjDocumento(String selAdjDocumento) {
		this.selAdjDocumento = selAdjDocumento;
	}

	public Archivo getArchivoDocSolicitud2() {
		return archivoDocSolicitud2;
	}

	public void setArchivoDocSolicitud2(Archivo archivoDocSolicitud2) {
		this.archivoDocSolicitud2 = archivoDocSolicitud2;
	}

	public Archivo getArchivoDocSolicitudTemp2() {
		return archivoDocSolicitudTemp2;
	}

	public void setArchivoDocSolicitudTemp2(Archivo archivoDocSolicitudTemp2) {
		this.archivoDocSolicitudTemp2 = archivoDocSolicitudTemp2;
	}

	public String getStrDescEntidadPres() {
		return strDescEntidadPres;
	}

	public void setStrDescEntidadPres(String strDescEntidadPres) {
		this.strDescEntidadPres = strDescEntidadPres;
	}

	 public List<Tabla> getListaMeses(){
		 log.info("lista de meses inicio");
		 TablaFacadeRemote remoteTabla = null;
		 List<Tabla> listaMesesTemp = null;
		 listaMesesTemp = new ArrayList<Tabla>();
		 
		 try {
			remoteTabla = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			listaMeses = remoteTabla.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_MES_CALENDARIO)); 
			if(listaMeses != null && !listaMeses.isEmpty())
			{
				for(Tabla tabla: listaMeses)
				{
					if(tabla.getIntIdDetalle().compareTo(0)==1)
					{
						listaMesesTemp.add(tabla);
					}
				}
				listaMeses = listaMesesTemp;
			}
			else
			{
			log.info("listaMeses es null");	
			}
			
			log.info("lista de meses fin");	
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		}
		 
		return listaMeses;
	}
	 public void setListaMeses(List<Tabla> listaMeses) {
		this.listaMeses = listaMeses;
	}

	public Integer getSESION_IDEMPRESA() {
		return SESION_IDEMPRESA;
	}

	public void setSESION_IDEMPRESA(Integer sESION_IDEMPRESA) {
		SESION_IDEMPRESA = sESION_IDEMPRESA;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getPERSONA_USUARIO() {
		return PERSONA_USUARIO;
	}

	public void setPERSONA_USUARIO(Integer pERSONA_USUARIO) {
		PERSONA_USUARIO = pERSONA_USUARIO;
	}

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}

	public void setBtnVisible(boolean btnVisible) {
		this.btnVisible = btnVisible;
	}

	public boolean isBtnVisible() {
		return btnVisible;
	}
    
	

	
	
	
	
	
	
	
	
	
	
}