package pe.com.tumi.tesoreria.solicitudPersonal.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonal;
import pe.com.tumi.tesoreria.egreso.domain.SolicitudPersonalDetalle;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;


public class SolicitudPersonalController {

	protected static Logger log = Logger.getLogger(SolicitudPersonalController.class);
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	LogisticaFacadeLocal	logisticaFacade;
	GeneralFacadeRemote		generalFacade;
	ContactoFacadeRemote	contactoFacade;
	TablaFacadeRemote		tablaFacade;
	PlanCuentaFacadeRemote	planCuentaFacade;
	EgresoFacadeLocal		egresoFacade;
	
	private	SolicitudPersonal	solicitudPersonalNuevo;
	private SolicitudPersonal	solicitudPersonalFiltro;
	private SolicitudPersonal	registroSeleccionado;
	private	SolicitudPersonalDetalle	solicitudPersonalDetalleNuevo;
	private PlanCuenta			planCuentaFiltro;
	private Persona				personaFiltro;
	private Persona				personaFiltroBusqueda;
	
	private List<SolicitudPersonal>	listaSolicitudPersonal;
	private List<Sucursal>		listaSucursal;	
	private List<Persona>		listaPersonaBuscar;
	private List<Persona>		listaPersonaFiltroBuqueda;
	private List<PlanCuenta>	listaPlanCuenta;
	private List<Tabla>			listaAnios;
	private List<Tabla>			listaTablaDocumentoGeneral;
	private List<SolicitudPersonal>	listaSolicitudPersonalCarga;
	private List<SolicitudPersonalDetalle>	listaSolicitudPersonalDetalleCarga;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private Integer		intTipoBusquedaPlanCuenta;
	private String		strMensajeDetalle;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean poseePermiso;
	private boolean mostrarMensajeDetalle;
	private boolean mostrarImportarDatos;
	private boolean habilitarComboTipoPersona;
	
	public SolicitudPersonalController(){
		cargarUsuario();
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();	
	}
	
	public void cargarValoresIniciales(){
		try{
			personaFacade =  (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			empresaFacade =  (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			logisticaFacade =  (LogisticaFacadeLocal) EJBFactory.getLocal(LogisticaFacadeLocal.class);
			generalFacade =  (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			contactoFacade = (ContactoFacadeRemote) EJBFactory.getRemote(ContactoFacadeRemote.class);
			tablaFacade  = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			planCuentaFacade  = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			egresoFacade  = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			
			listaTablaDocumentoGeneral = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_DOCUMENTOGENERAL), "P");
			personaFiltroBusqueda = new Persona();
			solicitudPersonalFiltro = new SolicitudPersonal();
			solicitudPersonalFiltro.setIntAño(Constante.OPCION_SELECCIONAR);
			listaSucursal = MyUtil.cargarListaSucursalConSubsucursalArea(EMPRESA_USUARIO);
			listaAnios = MyUtil.obtenerListaAnios();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		mostrarImportarDatos = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}

	public void grabar(){
		log.info("--grabar");
		try {
			if(mostrarPanelInferior){
				if(solicitudPersonalNuevo.getBdMontoTotalSolicitud()==null || solicitudPersonalNuevo.getBdMontoTotalSolicitud().signum()<=0){
					mostrarMensaje(Boolean.FALSE, "Debe de haber un Monto Total válido."); return;
				}
				if(solicitudPersonalNuevo.getPersona()==null){
					mostrarMensaje(Boolean.FALSE, "Debe de seleccionar una Entidad de Pago."); return;
				}
				if(solicitudPersonalNuevo.getStrObservacion()==null || solicitudPersonalNuevo.getStrObservacion().isEmpty()){
					mostrarMensaje(Boolean.FALSE, "Debe de ingresar una observación."); return;
				}
				if(solicitudPersonalNuevo.getArchivo()==null){
					mostrarMensaje(Boolean.FALSE, "Debe de agregar un archivo de Sustento de Autorización."); return;
				}
				/*if(solicitudPersonalNuevo.getIntPeriodoPago()==null){
					mostrarMensaje(Boolean.FALSE, "Debe de ingresar un Periodo."); return;
				}*/
				if(solicitudPersonalNuevo.getListaSolicitudPersonalDetalle().isEmpty()){
					mostrarMensaje(Boolean.FALSE, "Debe de agregar al menos un Detalle de Solicitud."); return;
				}
				
				if(!solicitudPersonalNuevo.getIntParaDocumentoGeneral().equals(Constante.PARAM_T_DOCUMENTOGENERAL_REMUNERACION)){
					solicitudPersonalNuevo.setIntParaSubTipoDocumentoPlanilla(null);
				}
				if(solicitudPersonalNuevo.getIntParaAgrupacionPago().equals(Constante.PARAM_T_OPERACIONPAGOPLANILLA_INDIVIDUAL) 
				&& solicitudPersonalNuevo.getListaSolicitudPersonalDetalle().size()>1){
					mostrarMensaje(Boolean.FALSE, "Si el tipo de operación es Individual, solo puede tener 1 detalle asociado."); return;
				}
				
				if(solicitudPersonalNuevo.getId().getIntItemSolicitudPersonal()==null){
					egresoFacade.grabarSolicitudPersonal(solicitudPersonalNuevo);
					buscar();
					mostrarMensaje(Boolean.TRUE, "Se registró correctamente la Solicitud Personal.");
				}else{
					egresoFacade.modificarSolicitudPersonal(solicitudPersonalNuevo);
					buscar();
					mostrarMensaje(Boolean.TRUE, "Se modificó correctamente la Solicitud Personal.");
				}
			}
			
			if(mostrarImportarDatos){
				if(listaSolicitudPersonalCarga!=null && !listaSolicitudPersonalCarga.isEmpty()){
					for(SolicitudPersonal solicitudPersonal : listaSolicitudPersonalCarga){
						egresoFacade.grabarSolicitudPersonal(solicitudPersonal);
					}
					buscar();
					mostrarMensaje(Boolean.TRUE, "Se registraron correctamente las Solicitudes Personales cargadas.");
					((FileUploadController)getSessionBean("fileUploadController")).setListaSolicitudPersonal(null);
				}				
			}
			
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro de la Solicitud Personal.");
			log.error(e.getMessage(),e);
		}
	}	
	
	public void buscar(){
		try{
			cargarUsuario();
			solicitudPersonalFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			
			listaSolicitudPersonal = new ArrayList<SolicitudPersonal>();
			listaPersonaFiltroBuqueda = new ArrayList<Persona>();
			if(personaFiltroBusqueda.getStrEtiqueta()!=null && !personaFiltroBusqueda.getStrEtiqueta().isEmpty()){
				personaFiltroBusqueda.getStrEtiqueta().trim();
				listaPersonaFiltroBuqueda = 
					personaFacade.buscarListaPersonaParaFiltro(personaFiltroBusqueda.getIntEstadoCod(),	personaFiltroBusqueda.getStrEtiqueta());
				if(listaPersonaFiltroBuqueda.isEmpty())
					return;
			}	
			
			for(Persona persona : listaPersonaFiltroBuqueda){
				log.info(persona);
			}
			
			if(solicitudPersonalFiltro.getIntAño().equals(Constante.OPCION_SELECCIONAR)){
				solicitudPersonalFiltro.setIntAño(null);
				solicitudPersonalFiltro.setIntMes(null);
			}else{
				if(solicitudPersonalFiltro.getIntMes().intValue()<10)
					solicitudPersonalFiltro.setIntPeriodoPago(
							Integer.parseInt(solicitudPersonalFiltro.getIntAño()+"0"+solicitudPersonalFiltro.getIntMes()));
				else
					solicitudPersonalFiltro.setIntPeriodoPago(
							Integer.parseInt(solicitudPersonalFiltro.getIntAño()+""+solicitudPersonalFiltro.getIntMes()));
			}			
				
			
			if(solicitudPersonalFiltro.getIntParaDocumentoGeneral().equals(Constante.OPCION_SELECCIONAR))
				solicitudPersonalFiltro.setIntParaDocumentoGeneral(null);			
			
			if(solicitudPersonalFiltro.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS))
				solicitudPersonalFiltro.setIntParaEstado(null);
			
			if(solicitudPersonalFiltro.getIntParaEstadoPago().equals(Constante.OPCION_SELECCIONAR))
				solicitudPersonalFiltro.setIntParaEstadoPago(null);			
			
			listaSolicitudPersonal = egresoFacade.buscarSolicitudPersonal(solicitudPersonalFiltro, listaPersonaFiltroBuqueda);
			
			for(SolicitudPersonal solicitudPersonal : listaSolicitudPersonal){
				solicitudPersonal.setPersona(personaFacade.devolverPersonaCargada(solicitudPersonal.getIntPersPersonaGiro()));
				if(solicitudPersonal.getIntSucuIdSucursal()!=null)
					solicitudPersonal.setSucursal(MyUtil.obtenerSucursalDeLista(solicitudPersonal.getIntSucuIdSucursal(), listaSucursal));
				if(solicitudPersonal.getIntSudeIdSubsucursal()!=null)
					solicitudPersonal.setSubsucursal(MyUtil.obtenerSubsucursalDeLista(solicitudPersonal.getIntSudeIdSubsucursal(), 
						solicitudPersonal.getSucursal().getListaSubSucursal()));
			}
			
			Collections.sort(listaSolicitudPersonal, new Comparator<SolicitudPersonal>(){
				public int compare(SolicitudPersonal uno, SolicitudPersonal otro) {
					return uno.getId().getIntItemSolicitudPersonal().compareTo(otro.getId().getIntItemSolicitudPersonal());
				}
			});
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			registroSeleccionado = (SolicitudPersonal)event.getComponent().getAttributes().get("item");
			log.info(registroSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarSucursalDetalle(){
		try{
			solicitudPersonalDetalleNuevo.setSucursal(MyUtil.obtenerSucursalDeLista(
				solicitudPersonalDetalleNuevo.getIntSucuIdSucursal(), listaSucursal));
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(){
		try{
			if(registroSeleccionado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
				deshabilitarNuevo = Boolean.FALSE;
				habilitarGrabar = Boolean.TRUE;
			}else{
				deshabilitarNuevo = Boolean.TRUE;
				habilitarGrabar = Boolean.FALSE;
			}
			mostrarPanelInferior = Boolean.TRUE;
			
			solicitudPersonalNuevo = registroSeleccionado;
			
			if(solicitudPersonalNuevo.getIntItemArchivoSustento()!=null)
				solicitudPersonalNuevo.setArchivo(obtenerArchivo(solicitudPersonalNuevo));
			
			for(SolicitudPersonalDetalle solicitudPersonalDetalle : solicitudPersonalNuevo.getListaSolicitudPersonalDetalle()){
				solicitudPersonalDetalle.setPersona(personaFacade.devolverPersonaCargada(solicitudPersonalDetalle.getIntPersPersonaAbonado()));
				solicitudPersonalDetalle.setPlanCuenta(obtenerPlanCuenta(solicitudPersonalDetalle));
				if(solicitudPersonalDetalle.getIntSucuIdSucursal()!=null)
					solicitudPersonalDetalle.setSucursal(MyUtil.obtenerSucursalDeLista
						(solicitudPersonalDetalle.getIntSucuIdSucursal(), listaSucursal));
				if(solicitudPersonalDetalle.getIntSudeIdSubsucursal()!=null)
					solicitudPersonalDetalle.setSubsucursal(MyUtil.obtenerSubsucursalDeLista
						(solicitudPersonalDetalle.getIntSudeIdSubsucursal(), solicitudPersonalDetalle.getSucursal().getListaSubSucursal()));
				if(solicitudPersonalDetalle.getIntIdArea()!=null)
					solicitudPersonalDetalle.setArea(MyUtil.obtenerAreaDeLista
						(solicitudPersonalDetalle.getIntIdArea(), solicitudPersonalDetalle.getSucursal().getListaArea()));
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private Archivo obtenerArchivo(SolicitudPersonal solicitudPersonal)throws Exception{
		ArchivoId archivoId = new ArchivoId();
		archivoId.setIntParaTipoCod(solicitudPersonal.getIntParaTipoSustento());
		archivoId.setIntItemArchivo(solicitudPersonal.getIntItemArchivoSustento());
		archivoId.setIntItemHistorico(solicitudPersonal.getIntItemHistoricoSustento());
		
		return generalFacade.getArchivoPorPK(archivoId);
	}
	
	private PlanCuenta obtenerPlanCuenta(SolicitudPersonalDetalle solicitudPersonalDetalle)throws Exception{
		PlanCuentaId planCuentaId = new PlanCuentaId();
		planCuentaId.setIntEmpresaCuentaPk(solicitudPersonalDetalle.getIntPersEmpresaCuenta());
		planCuentaId.setIntPeriodoCuenta(solicitudPersonalDetalle.getIntContPeriodoCuenta());
		planCuentaId.setStrNumeroCuenta(solicitudPersonalDetalle.getStrContNumeroCuenta());
		
		return planCuentaFacade.getPlanCuentaPorPk(planCuentaId);
	}
	
	public void modificarRegistro(){
		try{
			log.info("--modificarRegistro");			
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;			

			
			ocultarMensaje();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		try{
			SolicitudPersonal solicitudPersonalEliminar = registroSeleccionado;
			solicitudPersonalEliminar.setListaSolicitudPersonalPago(egresoFacade.getListaSolicitudPersonalPago(solicitudPersonalEliminar));
			if(solicitudPersonalEliminar.getListaSolicitudPersonalPago()!=null 
			&& !solicitudPersonalEliminar.getListaSolicitudPersonalPago().isEmpty()){
				mostrarMensaje(Boolean.TRUE, "No se puede eliminar la Solicitud Personal, posee un egreso de Pago asociado.");
				return;
			}	
			
			solicitudPersonalEliminar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			solicitudPersonalEliminar.setIntPersEmpresaElimina(EMPRESA_USUARIO);
			solicitudPersonalEliminar.setIntPersPersonaElimina(PERSONA_USUARIO);
			solicitudPersonalEliminar.setTsFechaEliminacion(MyUtil.obtenerFechaActual());
			
			egresoFacade.modificarSolicitudPersonaDirecto(solicitudPersonalEliminar);
			
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se eliminó correctamente la Solicitud Personal.");
		}catch (Exception e){
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la eliminación de la Solicitud Personal.");
			log.error(e.getMessage(),e);
		}
	}
	
	public void mostrarListaSolicitudPersonalCarga(){
		try{
			listaSolicitudPersonalCarga = ((FileUploadController)getSessionBean("fileUploadController")).getListaSolicitudPersonal();
			listaSolicitudPersonalDetalleCarga = new ArrayList<SolicitudPersonalDetalle>();
			
			for(SolicitudPersonal solicitudPersonal : listaSolicitudPersonalCarga){
				solicitudPersonal.setPersona(personaFacade.devolverPersonaCargada(solicitudPersonal.getIntPersPersonaGiro()));
				for(SolicitudPersonalDetalle solicitudPersonalDetalle : solicitudPersonal.getListaSolicitudPersonalDetalle()){
					solicitudPersonalDetalle.setPersona(personaFacade.devolverPersonaCargada(solicitudPersonalDetalle.getIntPersPersonaAbonado()));
					solicitudPersonalDetalle.setSucursal(MyUtil.obtenerSucursalDeLista(
							solicitudPersonalDetalle.getIntSucuIdSucursal(), listaSucursal));
					solicitudPersonalDetalle.setSubsucursal(MyUtil.obtenerSubsucursalDeLista(
							solicitudPersonalDetalle.getIntSudeIdSubsucursal(), solicitudPersonalDetalle.getSucursal().getListaSubSucursal()));
					solicitudPersonalDetalle.setArea(MyUtil.obtenerAreaDeLista(
							solicitudPersonalDetalle.getIntIdArea(), solicitudPersonalDetalle.getSucursal().getListaArea()));
					listaSolicitudPersonalDetalleCarga.add(solicitudPersonalDetalle);
				}
			}
				
			
		}catch (Exception e){
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la muestra de carga de Solicitud Personal.");
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarImportarDatos(){
		try{
			cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			mostrarImportarDatos = Boolean.TRUE;
			listaSolicitudPersonalCarga = null;
			listaSolicitudPersonalDetalleCarga = null;
			
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void habilitarPanelInferior(){
		try{
			cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			mostrarImportarDatos = Boolean.FALSE;
			
			solicitudPersonalNuevo = new SolicitudPersonal();
			solicitudPersonalNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			solicitudPersonalNuevo.setTsFechaRegistro(MyUtil.obtenerFechaActual());
			solicitudPersonalNuevo.setSucursal(MyUtil.obtenerSucursalDeLista(usuario.getSucursal().getId().getIntIdSucursal(), listaSucursal));
			solicitudPersonalNuevo.setSubsucursal(MyUtil.obtenerSubsucursalDeLista(usuario.getSubSucursal().getId().getIntIdSubSucursal(), 
							solicitudPersonalNuevo.getSucursal().getListaSubSucursal()));
			solicitudPersonalNuevo.setBdMontoTotalSolicitud(new BigDecimal(0));
			solicitudPersonalNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			solicitudPersonalNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
			solicitudPersonalNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			solicitudPersonalNuevo.setIntParaEstadoPago(Constante.PARAM_T_ESTADOPAGO_PENDIENTE);
			solicitudPersonalNuevo.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			solicitudPersonalNuevo.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
			
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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
	
	public void ocultarMensaje(){
		mostrarMensajeExito = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;		
	}
	
	public void abrirPopUpBuscarPersona(){
		try{
			personaFiltro = new Persona();
			personaFiltro.setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_JURIDICA);
			listaPersonaBuscar = new ArrayList<Persona>();
			habilitarComboTipoPersona = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpBuscarPersonaDetalle(){
		try{
			personaFiltro = new Persona();
			personaFiltro.setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
			listaPersonaBuscar = new ArrayList<Persona>();
			habilitarComboTipoPersona = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarPersona(){
		try{
			if(personaFiltro.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				listaPersonaBuscar = personaFacade.buscarListaPersonaParaFiltro(
						Constante.PARAM_T_OPCIONPERSONABUSQ_RUC, personaFiltro.getStrRuc());
				List<Persona> listaTemp = new ArrayList<Persona>();
				for(Persona persona : listaPersonaBuscar){
					if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
						persona.setJuridica(personaFacade.getJuridicaPorPK(persona.getIntIdPersona()));
						listaTemp.add(persona);
					}else if(persona.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
						persona.setDocumento(contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(
								persona.getIntIdPersona(), Constante.PARAM_T_INT_TIPODOCUMENTO_DNI));
						persona.setNatural(personaFacade.getNaturalPorPK(persona.getIntIdPersona()));
						MyUtil.agregarNombreCompleto(persona);
						listaTemp.add(persona);
					}
				}
				listaPersonaBuscar = listaTemp;
			
			}else if(personaFiltro.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				listaPersonaBuscar = personaFacade.buscarListaPersonaParaFiltro(
						Constante.PARAM_T_OPCIONPERSONABUSQ_DNI, personaFiltro.getStrRuc());
				for(Persona persona : listaPersonaBuscar){
					persona.setDocumento(contactoFacade.getDocumentoPorIdPersonaYTipoIdentidad(
							persona.getIntIdPersona(), Constante.PARAM_T_INT_TIPODOCUMENTO_DNI));
					persona.setNatural(personaFacade.getNaturalPorPK(persona.getIntIdPersona()));
					MyUtil.agregarNombreCompleto(persona);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarPersona(ActionEvent event){
		try{
			Persona personaSeleccionada = (Persona)event.getComponent().getAttributes().get("item");
			//habilitarComboTipoPersona es True cuando se llama desde el formulario principal
			if(habilitarComboTipoPersona){
				solicitudPersonalNuevo.setIntPersEmpresaPersona(EMPRESA_USUARIO);
				solicitudPersonalNuevo.setIntPersPersonaGiro(personaSeleccionada.getIntIdPersona());				
				solicitudPersonalNuevo.setPersona(personaSeleccionada);
			}else{
				solicitudPersonalDetalleNuevo.setIntPersEmpresaAbonado(EMPRESA_USUARIO);
				solicitudPersonalDetalleNuevo.setIntPersPersonaAbonado(personaSeleccionada.getIntIdPersona());
				solicitudPersonalDetalleNuevo.setPersona(personaSeleccionada);
			}	
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarAdjuntarDocumento(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			Archivo archivoSustento = fileUploadController.getArchivoSustento();
			
			solicitudPersonalNuevo.setIntParaTipoSustento(archivoSustento.getId().getIntParaTipoCod());
			solicitudPersonalNuevo.setIntItemArchivoSustento(archivoSustento.getId().getIntItemArchivo());
			solicitudPersonalNuevo.setIntItemHistoricoSustento(archivoSustento.getId().getIntItemHistorico());
			solicitudPersonalNuevo.setArchivo(archivoSustento);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}	
	
	public void abrirPopUpAgregarDetalle(){
		try{
			strMensajeDetalle = "";
			mostrarMensajeDetalle = Boolean.FALSE;
			solicitudPersonalDetalleNuevo = new SolicitudPersonalDetalle();
			solicitudPersonalDetalleNuevo.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			seleccionarSucursalDetalle();
			solicitudPersonalDetalleNuevo.setIntSudeIdSubsucursal(usuario.getSubSucursal().getId().getIntIdSubSucursal());
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verSolicitudPersonalDetalle(ActionEvent event){
		try{
			strMensajeDetalle = "";
			mostrarMensajeDetalle = Boolean.FALSE;
			solicitudPersonalDetalleNuevo = (SolicitudPersonalDetalle)event.getComponent().getAttributes().get("item");
			seleccionarSucursalDetalle();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpBuscarPlanCuenta(){
		try{
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			planCuentaFiltro.getId().setIntPeriodoCuenta(MyUtil.obtenerAñoActual());
			planCuentaFiltro.setIntTipoBusqueda(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION);			
			//listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);			
			buscarPlanCuenta();
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarPlanCuenta(){
		try{
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro, usuario, Constante.PARAM_TRANSACCION_SOLICITUDPAGOPERSONAL);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarPlanCuenta(ActionEvent event){
		try{
			PlanCuenta planCuenta = (PlanCuenta)event.getComponent().getAttributes().get("item");
			solicitudPersonalDetalleNuevo.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
			solicitudPersonalDetalleNuevo.setIntContPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
			solicitudPersonalDetalleNuevo.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
			
			solicitudPersonalDetalleNuevo.setPlanCuenta(planCuenta);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}		
	
	private void calcularMonto()throws Exception{
		BigDecimal bdMontoTotal = new BigDecimal(0);
		for(SolicitudPersonalDetalle solicitudPersonalDetalle : solicitudPersonalNuevo.getListaSolicitudPersonalDetalle())
			bdMontoTotal = bdMontoTotal.add(solicitudPersonalDetalle.getBdMonto());
		solicitudPersonalNuevo.setBdMontoTotalSolicitud(bdMontoTotal);
		solicitudPersonalNuevo.setBdMontoSaldoSolicitud(bdMontoTotal);
	}
	
	public void agregarSolicitudPersonalDetalle(){
		try{
			strMensajeDetalle = "";
			mostrarMensajeDetalle = Boolean.TRUE;	
			if(solicitudPersonalDetalleNuevo.getPersona()==null){
				strMensajeDetalle = "Debe de agregar una persona."; return;
			}
			if(solicitudPersonalDetalleNuevo.getIntSudeIdSubsucursal()==null || Integer.signum(solicitudPersonalDetalleNuevo.getIntSudeIdSubsucursal())==0){
				strMensajeDetalle = "Debe de seleccionar una subsucursal valida."; return;
			}
			if(solicitudPersonalDetalleNuevo.getIntIdArea()==null || Integer.signum(solicitudPersonalDetalleNuevo.getIntIdArea())==0 ){
				strMensajeDetalle = "Debe de seleccionar un área valida."; return;
			}
			if(solicitudPersonalDetalleNuevo.getBdMonto()==null || solicitudPersonalDetalleNuevo.getBdMonto().signum()<=0){
				strMensajeDetalle = "Debe de ingresar un monto válido."; return;
			}
			if(solicitudPersonalDetalleNuevo.getPlanCuenta()==null){
				strMensajeDetalle = "Debe de agregar un plan de cuenta."; return;
			}
			
			solicitudPersonalDetalleNuevo.setSubsucursal(MyUtil.obtenerSubsucursalDeLista(
					solicitudPersonalDetalleNuevo.getIntSudeIdSubsucursal(), solicitudPersonalDetalleNuevo.getSucursal().getListaSubSucursal()));
			solicitudPersonalDetalleNuevo.setArea(MyUtil.obtenerAreaDeLista(solicitudPersonalDetalleNuevo.getIntIdArea(), 
					solicitudPersonalDetalleNuevo.getSucursal().getListaArea()));
			
			solicitudPersonalNuevo.getListaSolicitudPersonalDetalle().add(solicitudPersonalDetalleNuevo);			
			calcularMonto();
			mostrarMensajeDetalle = Boolean.FALSE;			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarSolicitudPersonalDetalle(ActionEvent event){
		try{
			SolicitudPersonalDetalle solicitudPersonalDetalleEliminar = (SolicitudPersonalDetalle)event.getComponent().getAttributes().get("item");			
			solicitudPersonalNuevo.getListaSolicitudPersonalDetalle().remove(solicitudPersonalDetalleEliminar);
			
			calcularMonto();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}	

	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);		
		return sesion.getAttribute(beanName);
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
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	public SolicitudPersonal getSolicitudPersonalNuevo() {
		return solicitudPersonalNuevo;
	}
	public void setSolicitudPersonalNuevo(SolicitudPersonal solicitudPersonalNuevo) {
		this.solicitudPersonalNuevo = solicitudPersonalNuevo;
	}
	public SolicitudPersonal getSolicitudPersonalFiltro() {
		return solicitudPersonalFiltro;
	}
	public void setSolicitudPersonalFiltro(SolicitudPersonal solicitudPersonalFiltro) {
		this.solicitudPersonalFiltro = solicitudPersonalFiltro;
	}
	public List<SolicitudPersonal> getListaSolicitudPersonal() {
		return listaSolicitudPersonal;
	}
	public void setListaSolicitudPersonal(List<SolicitudPersonal> listaSolicitudPersonal) {
		this.listaSolicitudPersonal = listaSolicitudPersonal;
	}
	public Persona getPersonaFiltro() {
		return personaFiltro;
	}
	public void setPersonaFiltro(Persona personaFiltro) {
		this.personaFiltro = personaFiltro;
	}
	public List<Persona> getListaPersonaBuscar() {
		return listaPersonaBuscar;
	}
	public void setListaPersonaBuscar(List<Persona> listaPersonaBuscar) {
		this.listaPersonaBuscar = listaPersonaBuscar;
	}
	public PlanCuenta getPlanCuentaFiltro() {
		return planCuentaFiltro;
	}
	public void setPlanCuentaFiltro(PlanCuenta planCuentaFiltro) {
		this.planCuentaFiltro = planCuentaFiltro;
	}
	public List<PlanCuenta> getListaPlanCuenta() {
		return listaPlanCuenta;
	}
	public void setListaPlanCuenta(List<PlanCuenta> listaPlanCuenta) {
		this.listaPlanCuenta = listaPlanCuenta;
	}
	public Integer getIntTipoBusquedaPlanCuenta() {
		return intTipoBusquedaPlanCuenta;
	}
	public void setIntTipoBusquedaPlanCuenta(Integer intTipoBusquedaPlanCuenta) {
		this.intTipoBusquedaPlanCuenta = intTipoBusquedaPlanCuenta;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
	public String getStrMensajeDetalle() {
		return strMensajeDetalle;
	}
	public void setStrMensajeDetalle(String strMensajeDetalle) {
		this.strMensajeDetalle = strMensajeDetalle;
	}
	public SolicitudPersonalDetalle getSolicitudPersonalDetalleNuevo() {
		return solicitudPersonalDetalleNuevo;
	}
	public void setSolicitudPersonalDetalleNuevo(SolicitudPersonalDetalle solicitudPersonalDetalleNuevo) {
		this.solicitudPersonalDetalleNuevo = solicitudPersonalDetalleNuevo;
	}
	public boolean isMostrarMensajeDetalle() {
		return mostrarMensajeDetalle;
	}
	public void setMostrarMensajeDetalle(boolean mostrarMensajeDetalle) {
		this.mostrarMensajeDetalle = mostrarMensajeDetalle;
	}
	public List<Tabla> getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List<Tabla> listaAnios) {
		this.listaAnios = listaAnios;
	}
	public Persona getPersonaFiltroBusqueda() {
		return personaFiltroBusqueda;
	}
	public void setPersonaFiltroBusqueda(Persona personaFiltroBusqueda) {
		this.personaFiltroBusqueda = personaFiltroBusqueda;
	}
	public SolicitudPersonal getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(SolicitudPersonal registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public List<Tabla> getListaTablaDocumentoGeneral() {
		return listaTablaDocumentoGeneral;
	}
	public void setListaTablaDocumentoGeneral(List<Tabla> listaTablaDocumentoGeneral) {
		this.listaTablaDocumentoGeneral = listaTablaDocumentoGeneral;
	}
	public boolean isMostrarImportarDatos(){
		return mostrarImportarDatos;
	}
	public void setMostrarImportarDatos(boolean mostrarImportarDatos){
		this.mostrarImportarDatos = mostrarImportarDatos;
	}
	public boolean isHabilitarComboTipoPersona() {
		return habilitarComboTipoPersona;
	}
	public void setHabilitarComboTipoPersona(boolean habilitarComboTipoPersona) {
		this.habilitarComboTipoPersona = habilitarComboTipoPersona;
	}
	public List<SolicitudPersonal> getListaSolicitudPersonalCarga() {
		return listaSolicitudPersonalCarga;
	}
	public void setListaSolicitudPersonalCarga(List<SolicitudPersonal> listaSolicitudPersonalCarga) {
		this.listaSolicitudPersonalCarga = listaSolicitudPersonalCarga;
	}
	public List<SolicitudPersonalDetalle> getListaSolicitudPersonalDetalleCarga() {
		return listaSolicitudPersonalDetalleCarga;
	}
	public void setListaSolicitudPersonalDetalleCarga(List<SolicitudPersonalDetalle> listaSolicitudPersonalDetalleCarga) {
		this.listaSolicitudPersonalDetalleCarga = listaSolicitudPersonalDetalleCarga;
	}
}