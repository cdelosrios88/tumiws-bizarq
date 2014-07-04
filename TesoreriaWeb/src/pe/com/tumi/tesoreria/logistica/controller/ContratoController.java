package pe.com.tumi.tesoreria.logistica.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.contacto.domain.DomicilioPK;
import pe.com.tumi.persona.contacto.facade.ContactoFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.domain.Contrato;
import pe.com.tumi.tesoreria.logistica.domain.ContratoId;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionId;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;


public class ContratoController {

	protected static Logger log = Logger.getLogger(ContratoController.class);
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	LogisticaFacadeLocal	logisticaFacade;
	GeneralFacadeRemote		generalFacade;
	ContactoFacadeRemote	contactoFacade;
	TablaFacadeRemote		tablaFacade;
	
	private	Contrato			contratoNuevo;
	private Contrato			contratoFiltro;
	private Contrato			registroSeleccionado;
	private Domicilio			domicilioNuevo;
	private List<Sucursal>		listaSucursal;
	private List<Contrato>		listaContrato;
	private List<Proveedor>		listaProveedor;
	private List<Requisicion>	listaRequisicion;
	private List<Contrato>		listaAnteriorContrato;
	private List<Ubigeo>		listaUbigeoDepartamento;
	private List<Ubigeo>		listaUbigeoProvincia;
	private List<Ubigeo>		listaUbigeoDistrito;
	private List<Tabla>			listaTablaProveedor;
	private List<Tabla>			listaTablaTipoVia;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private Integer		intTipoPersona;
	private String		strFiltroTextoPersona;
	private Date		dtFiltroDesde;
	private Date 		dtFiltroHasta;
	private Integer		intTipoFiltroFecha;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	private final Integer 	FILTROFECHA_INICIO = 1;
	private final Integer 	FILTROFECHA_FIN = 2;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean poseePermiso;
	
	
	public ContratoController(){
		cargarUsuario();
		//poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_CONTRATO);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	public String getLimpiarContrato(){
		cargarUsuario();
		//poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_CONTRATO);
		log.info("POSEE PERMISO" + poseePermiso);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
			//Agregado por cdelosrios, 25/11/2013
			deshabilitarPanelInferior();
			//Agregado por cdelosrios, 25/11/2013
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		
		return "";
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
			
			listaTablaProveedor = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOPROVEEDOR));
			listaTablaTipoVia = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOVIA));
			contratoFiltro = new Contrato();
			listaContrato = new ArrayList<Contrato>();
			listaAnteriorContrato = new ArrayList<Contrato>();
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			intTipoFiltroFecha = FILTROFECHA_INICIO;
			
			listaUbigeoDepartamento = generalFacade.getListaUbigeoDeDepartamento();
			cargarListaSucursal();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}	
	
	
	public void grabar(){
		log.info("--grabar");
		try {

			if(contratoNuevo.getRequisicion()==null){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar una requisición."); return;
			}
			if(contratoNuevo.getEmpresaServicio()==null){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar una empresa de servicio."); return;
			}
			if(contratoNuevo.getDomicilioEmpresaServicio()==null){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar un domicilio para la empresa de servicio."); return;
			}
			if(contratoNuevo.getIntIdAreaSolicitante()==null || contratoNuevo.getIntIdAreaSolicitante().equals(new Integer(0))){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar un área."); return;
			}
			if(contratoNuevo.getDtFechaInicio()==null){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar una fecha de inicio."); return;
			}
			if(contratoNuevo.getDtFechaFin()==null){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar una fecha de fin."); return;
			}
			if(contratoNuevo.getDtFechaInicio().compareTo(contratoNuevo.getDtFechaFin())>=0){
				mostrarMensaje(Boolean.FALSE, "La fecha de inicio no puede ser mayor o igual a la fecha de fin."); return;
			}
			if(contratoNuevo.getBdMontoContrato()==null || contratoNuevo.getBdMontoContrato().signum()<=0){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar un monto de contrato válido."); return;
			}
			/*if(contratoNuevo.getBdMontoGarantia()==null || contratoNuevo.getBdMontoGarantia().signum()<=0){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar un monto de garantía válido."); return;
			}*/
			if(contratoNuevo.getArchivoDocumento()==null){
				mostrarMensaje(Boolean.FALSE, "Debe adjuntar un documento."); return;
			}
			if(contratoNuevo.getStrObservacion()==null || contratoNuevo.getStrObservacion().isEmpty()){
				mostrarMensaje(Boolean.FALSE, "Debe ingresar una observación."); return;
			}
			
			
			contratoNuevo = logisticaFacade.grabarContrato(contratoNuevo);
			contratoNuevo.setStrCantidadDias(MyUtil.obtenerDescripcionDiferenciaEntreFechas(contratoNuevo.getDtFechaInicio(), contratoNuevo.getDtFechaFin()));
			buscar();
			
			mostrarMensaje(Boolean.TRUE, "Se registró correctamente el Contrato.");			
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro del Informe de Gerencia.");
			log.error(e.getMessage(),e);
		}
	}
	
	
	
	public void buscar(){
		try{
			contratoFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			
			contratoFiltro.setDtFiltroInicioDesde(null);
			contratoFiltro.setDtFiltroInicioHasta(null);
			contratoFiltro.setDtFiltroFinDesde(null);
			contratoFiltro.setDtFiltroFinHasta(null);
			
			if(contratoFiltro.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
				contratoFiltro.setIntParaEstado(null);
			}
			
			if(intTipoFiltroFecha.equals(FILTROFECHA_INICIO)){
				if(dtFiltroDesde == null)
					contratoFiltro.setDtFiltroInicioDesde(null);
				else
					contratoFiltro.setDtFiltroInicioDesde(dtFiltroDesde);
				
				if(dtFiltroHasta == null)
					contratoFiltro.setDtFiltroInicioHasta(null);
				else
					contratoFiltro.setDtFiltroInicioHasta(dtFiltroHasta);
			
			}else if(intTipoFiltroFecha.equals(FILTROFECHA_FIN)){
				if(dtFiltroDesde == null)
					contratoFiltro.setDtFiltroFinDesde(null);
				else
					contratoFiltro.setDtFiltroFinDesde(dtFiltroDesde);
				
				if(dtFiltroHasta == null)
					contratoFiltro.setDtFiltroFinHasta(null);
				else
					contratoFiltro.setDtFiltroFinHasta(dtFiltroHasta);
			}
			
			if(contratoFiltro.getIntSucuIdSucursal().equals(new Integer(0))){
				contratoFiltro.setIntSucuIdSucursal(null);
			}		
			
			
			listaContrato = logisticaFacade.buscarContrato(contratoFiltro);
			
			for(Contrato contrato : listaContrato){				
				Persona empresaServicio = personaFacade.getPersonaPorPK(contrato.getIntPersPersonaServicio());
				
				if(empresaServicio.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
					empresaServicio = personaFacade.getPersonaNaturalPorIdPersona(contrato.getIntPersPersonaServicio());					
					agregarNombreCompleto(empresaServicio);
					agregarDocumentoDNI(empresaServicio);					
					empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona()+"-"+empresaServicio.getNatural().getStrNombreCompleto()
							+"-DNI:"+empresaServicio.getDocumento().getStrNumeroIdentidad());					
				
				}else if(empresaServicio.getIntTipoPersonaCod().equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){					
					empresaServicio = personaFacade.getPersonaJuridicaPorIdPersona(contrato.getIntPersPersonaServicio());
					empresaServicio.setStrEtiqueta(empresaServicio.getIntIdPersona()+"-"+empresaServicio.getJuridica().getStrRazonSocial()
							+"-RUC:"+empresaServicio.getStrRuc());
				}				
				contrato.setEmpresaServicio(empresaServicio);
				
				for(Sucursal sucursal : listaSucursal){
					if(sucursal.getId().getIntIdSucursal().equals(contrato.getIntSucuIdSucursal()))
						contrato.setSucursal(sucursal);
				}
				
				contrato.setOrdenCompra(logisticaFacade.obtenerOrdenCompraPorContrato(contrato));
			}
			
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			registroSeleccionado = (Contrato)event.getComponent().getAttributes().get("item");
			log.info(registroSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(){
		try{
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;			
			mostrarPanelInferior = Boolean.TRUE;
			
			contratoNuevo = registroSeleccionado;
			
			RequisicionId requisicionId = new RequisicionId();
			requisicionId.setIntPersEmpresa(contratoNuevo.getIntPersEmpresaRequisicion());
			requisicionId.setIntItemRequisicion(contratoNuevo.getIntItemRequisicion());
			Requisicion requisicion = logisticaFacade.obtenerRequisicionPorId(requisicionId);
			añadirEtiquetaRequisicion(requisicion);
			contratoNuevo.setRequisicion(requisicion);
			
			Domicilio domicilioEmpresaServicio = null;
			for(Domicilio domicilio : contratoNuevo.getEmpresaServicio().getListaDomicilio()){
				if(domicilio.getId().getIntIdDomicilio().equals(contratoNuevo.getIntDomicilio())){
					domicilioEmpresaServicio = domicilio;
					break;
				}					
			}
			añadirDescripcionUbigeo(domicilioEmpresaServicio);
			domicilioEmpresaServicio.setStrEtiqueta(obtenerEtiquetaTipoVia(domicilioEmpresaServicio.getIntTipoViaCod())+" "
					+domicilioEmpresaServicio.getStrNombreVia()+" "+domicilioEmpresaServicio.getIntNumeroVia()
					+" "+domicilioEmpresaServicio.getStrUbigeoDepartamento()+"-"+domicilioEmpresaServicio.getStrUbigeoProvincia()
					+"-"+domicilioEmpresaServicio.getStrUbigeoDistrito());
			contratoNuevo.setDomicilioEmpresaServicio(domicilioEmpresaServicio);
			
			
			Persona empresaSolicita = personaFacade.getPersonaJuridicaPorIdPersona(contratoNuevo.getIntPersPersonaSolicita());
			contratoNuevo.setEmpresaSolicita(empresaSolicita);
			
			seleccionarSucursal();
			
			ArchivoId archivoId = new ArchivoId();
			archivoId.setIntParaTipoCod(contratoNuevo.getIntParaTipo());
			archivoId.setIntItemArchivo(contratoNuevo.getIntItemArchivo());
			archivoId.setIntItemHistorico(contratoNuevo.getIntItemHistorico());			
			contratoNuevo.setArchivoDocumento(generalFacade.getArchivoPorPK(archivoId));		
			
			if(contratoNuevo.getIntItemContratoAnterior()!=null){
				ContratoId contratoAnteriorId = new ContratoId();
				contratoAnteriorId.setIntPersEmpresa(contratoNuevo.getIntPersEmpresaAnterior());
				contratoAnteriorId.setIntItemContrato(contratoNuevo.getIntItemContratoAnterior());
				Contrato contratoAnterior = logisticaFacade.obtenerContratoPorId(contratoAnteriorId);
				añadirEtiquetaContrato(contratoAnterior);
				contratoNuevo.setContratoAnterior(contratoAnterior);
			}			
			
			contratoNuevo.setSeleccionaPagoUnico(Boolean.FALSE);
			contratoNuevo.setSeleccionaRenovacion(Boolean.FALSE);
			
			if(contratoNuevo.getIntPagoUnico().equals(new Integer(1)))
				contratoNuevo.setSeleccionaPagoUnico(Boolean.TRUE);
			if(contratoNuevo.getIntRenovacion().equals(new Integer(1)))
				contratoNuevo.setSeleccionaRenovacion(Boolean.TRUE);
			
			contratoNuevo.setStrCantidadDias(MyUtil.obtenerDescripcionDiferenciaEntreFechas(contratoNuevo.getDtFechaInicio(), contratoNuevo.getDtFechaFin()));
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
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

	public void abrirPopUpBuscarRequisicion(){
		try{
			listaRequisicion = logisticaFacade.obtenerListaRequisicionReferencia(EMPRESA_USUARIO, Constante.PARAM_T_APROBACION_CONTRATO);			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpBuscarDomicilio(){
		try{
			List<Domicilio> listaDomicilioBD = contactoFacade.getListaDomicilio(contratoNuevo.getIntPersPersonaServicio());
			
			if(listaDomicilioBD!=null && !listaDomicilioBD.isEmpty()){
				List<Domicilio> listaDomicilioActivo = new ArrayList<Domicilio>();
				for(Domicilio domicilioBD : listaDomicilioBD){
					if(domicilioBD.getIntEstadoCod().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)){
						añadirDescripcionUbigeo(domicilioBD);
						listaDomicilioActivo.add(domicilioBD);
					}
						
				}
				listaDomicilioBD = listaDomicilioActivo;
			}
			
			contratoNuevo.getEmpresaServicio().setListaDomicilio(listaDomicilioBD);
		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	

	private void añadirDescripcionUbigeo(Domicilio domicilio)throws Exception{
		listaUbigeoProvincia = generalFacade.getListaUbigeoDeProvinciaPorIdUbigeo(domicilio.getIntParaUbigeoPkDpto());
		listaUbigeoDistrito = generalFacade.getListaUbigeoDeDistritoPorIdUbigeo(domicilio.getIntParaUbigeoPkProvincia());
		
		for(Ubigeo ubigeoDepartamento : listaUbigeoDepartamento){
			if(domicilio.getIntParaUbigeoPkDpto()!=null 
			&& domicilio.getIntParaUbigeoPkDpto().equals(ubigeoDepartamento.getIntIdUbigeo())){
				domicilio.setStrUbigeoDepartamento(ubigeoDepartamento.getStrDescripcion());break;
			}
		}
		for(Ubigeo ubigeoProvincia : listaUbigeoProvincia){
			if(domicilio.getIntParaUbigeoPkProvincia()!=null 
			&& domicilio.getIntParaUbigeoPkProvincia().equals(ubigeoProvincia.getIntIdUbigeo())){
				domicilio.setStrUbigeoProvincia(ubigeoProvincia.getStrDescripcion());break;
			}
		}
		for(Ubigeo ubigeoDistrito : listaUbigeoDistrito){
			if(domicilio.getIntParaUbigeoPkDistrito()!=null
			&& domicilio.getIntParaUbigeoPkDistrito().equals(ubigeoDistrito.getIntIdUbigeo())){
				domicilio.setStrUbigeoDistrito(ubigeoDistrito.getStrDescripcion());break;
			}
		}
	}
	
	public void abrirPopUpBuscarAnteriorContrato(){
		try{
			Contrato contratoAnteriorFiltro = new Contrato();
			contratoAnteriorFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			contratoAnteriorFiltro.setIntPersPersonaServicio(contratoNuevo.getIntPersPersonaServicio());
			contratoAnteriorFiltro.setIntPersPersonaSolicita(contratoNuevo.getIntPersPersonaSolicita());
			contratoAnteriorFiltro.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			
			listaAnteriorContrato = logisticaFacade.buscarContrato(contratoAnteriorFiltro);			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRequisicion(ActionEvent event){
		try{
			Requisicion requisicionAgregar = (Requisicion)event.getComponent().getAttributes().get("item");
			
			añadirEtiquetaRequisicion(requisicionAgregar);
			contratoNuevo.setIntPersEmpresaRequisicion(requisicionAgregar.getId().getIntPersEmpresa());
			contratoNuevo.setIntItemRequisicion(requisicionAgregar.getId().getIntItemRequisicion());
			contratoNuevo.setRequisicion(requisicionAgregar);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarAnteriorContrato(ActionEvent event){
		try{
			Contrato contratoAnteriorAgregar = (Contrato)event.getComponent().getAttributes().get("item");
			
			añadirEtiquetaContrato(contratoAnteriorAgregar);
			contratoNuevo.setIntPersEmpresaAnterior(contratoAnteriorAgregar.getId().getIntPersEmpresa());
			contratoNuevo.setIntItemContratoAnterior(contratoAnteriorAgregar.getId().getIntItemContrato());
			contratoNuevo.setContratoAnterior(contratoAnteriorAgregar);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarDomicilio(ActionEvent event){
		try{
			Domicilio domicilioEmpresaServicio = (Domicilio)event.getComponent().getAttributes().get("item");
			domicilioEmpresaServicio.setStrEtiqueta(obtenerEtiquetaTipoVia(domicilioEmpresaServicio.getIntTipoViaCod())+" "
					+domicilioEmpresaServicio.getStrNombreVia()+" "+domicilioEmpresaServicio.getIntNumeroVia()
					+" "+domicilioEmpresaServicio.getStrUbigeoDepartamento()+"-"+domicilioEmpresaServicio.getStrUbigeoProvincia()
					+"-"+domicilioEmpresaServicio.getStrUbigeoDistrito());
			
			contratoNuevo.setIntDomicilio(domicilioEmpresaServicio.getId().getIntIdDomicilio());
			contratoNuevo.setDomicilioEmpresaServicio(domicilioEmpresaServicio);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	private void añadirEtiquetaRequisicion(Requisicion requisicionAgregar)throws Exception{
		TablaFacadeRemote tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
		Tabla tablaRequisicion = tablaFacade.getTablaPorIdMaestroYIdDetalle(
				Integer.parseInt(Constante.PARAM_T_REQUISICION), requisicionAgregar.getIntParaTipoRequisicion());
		Tabla tablaAprobacion = tablaFacade.getTablaPorIdMaestroYIdDetalle(
				Integer.parseInt(Constante.PARAM_T_APROBACION), requisicionAgregar.getIntParaTipoAprobacion());
		String strEtiqueta = requisicionAgregar.getId().getIntItemRequisicion() + " "+tablaRequisicion.getStrDescripcion()
			+" "+tablaAprobacion.getStrDescripcion();
		
		requisicionAgregar.setStrEtiqueta(strEtiqueta);
	}
	
	private void añadirEtiquetaContrato(Contrato contratoAnteriorAgregar)throws Exception{
		TablaFacadeRemote tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
		Tabla tablaTipoContrato = tablaFacade.getTablaPorIdMaestroYIdDetalle(
				Integer.parseInt(Constante.PARAM_T_COMPRACONTRATO), contratoAnteriorAgregar.getIntParaTipoContrato());
		String strEtiqueta = contratoAnteriorAgregar.getId().getIntItemContrato() + " "+tablaTipoContrato.getStrDescripcion();
		
		contratoAnteriorAgregar.setStrEtiqueta(strEtiqueta);
	}
	
	public void abrirPopUpBuscarEmpresaServicio(){
		try{
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			strFiltroTextoPersona = "";
			listaProveedor = new ArrayList<Proveedor>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpAgregarDomicilio(){
		try{
			domicilioNuevo = new Domicilio();
			domicilioNuevo.setId(new DomicilioPK());
			
			listaUbigeoProvincia = new ArrayList<Ubigeo>();
			listaUbigeoDistrito = new ArrayList<Ubigeo>();			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarAgregarDomicilio(){
		try{
			domicilioNuevo.getId().setIntIdPersona(contratoNuevo.getIntPersPersonaServicio());
			domicilioNuevo.setIntEstadoCod(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			Archivo archivoCroquis = fileUploadController.getObjArchivo(); 
			log.info(archivoCroquis);
		    if(archivoCroquis!=null && archivoCroquis.getId().getIntItemArchivo()!=null){
		    	domicilioNuevo.setIntParaTipoArchivo(Constante.PARAM_T_TIPOARCHIVOADJUNTO_CROQUIS);
		    	domicilioNuevo.setIntParaItemArchivo(archivoCroquis.getId().getIntItemArchivo());
		    	domicilioNuevo.setIntParaItemHistorico(archivoCroquis.getId().getIntItemHistorico());
		    }
			
			contactoFacade.grabarDomicilio(domicilioNuevo);
			
			añadirDescripcionUbigeo(domicilioNuevo);
			domicilioNuevo.setStrEtiqueta(obtenerEtiquetaTipoVia(domicilioNuevo.getIntTipoViaCod())+" "
					+domicilioNuevo.getStrNombreVia()+" "+domicilioNuevo.getIntNumeroVia()
					+" "+domicilioNuevo.getStrUbigeoDepartamento()+"-"+domicilioNuevo.getStrUbigeoProvincia()
					+"-"+domicilioNuevo.getStrUbigeoDistrito());
			
			contratoNuevo.setIntDomicilio(domicilioNuevo.getId().getIntIdDomicilio());
			contratoNuevo.setDomicilioEmpresaServicio(domicilioNuevo);			
			
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error grabando el domicilio.");
			log.error(e.getMessage(),e);
		}
	}
	

	
	public void aceptarAdjuntarDocumento(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			Archivo archivoDocumento = fileUploadController.getArchivoDocumento();
			
			contratoNuevo.setIntParaTipo(archivoDocumento.getId().getIntParaTipoCod());
			contratoNuevo.setIntItemArchivo(archivoDocumento.getId().getIntItemArchivo());
			contratoNuevo.setIntItemHistorico(archivoDocumento.getId().getIntItemHistorico());
			contratoNuevo.setArchivoDocumento(archivoDocumento);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarPersona(){
		try{
			log.info("--buscarPersona");
			Persona persona = null;
			boolean proveedorValido = Boolean.TRUE;
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						strFiltroTextoPersona, 
						EMPRESA_USUARIO);
				
				proveedorValido = MyUtil.poseeRol(persona,Constante.PARAM_T_TIPOROL_PROVEEDOR);
				
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				persona = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(strFiltroTextoPersona,EMPRESA_USUARIO);				
				proveedorValido = MyUtil.poseeRol(persona,Constante.PARAM_T_TIPOROL_PROVEEDOR) 
					&& (persona.getJuridica().getIntCondContribuyente()!=null 
						&& !persona.getJuridica().getIntCondContribuyente().equals(Constante.PARAM_T_TIPOCONDCONTRIBUYENTE_NOHABIDO))
					&& (persona.getJuridica().getIntEstadoContribuyenteCod() !=null 
						&& !persona.getJuridica().getIntEstadoContribuyenteCod().equals(Constante.PARAM_T_TIPOESTADOCONTRIB_INACTIVO));			
			}
			
			//proveedorValido = Boolean.TRUE;
			log.info(persona);
			if(proveedorValido){
				ProveedorId proveedorId = new ProveedorId();
				proveedorId.setIntPersEmpresa(EMPRESA_USUARIO);
				proveedorId.setIntPersPersona(persona.getIntIdPersona());
				Proveedor proveedor = logisticaFacade.getProveedorPorPK(proveedorId);
				String strListaProveedorDetalle = "";
				for(ProveedorDetalle proveedorDetalle : proveedor.getListaProveedorDetalle()){
					strListaProveedorDetalle = strListaProveedorDetalle + " / " +obtenerEtiquetaTipoProveedor(proveedorDetalle.getIntParaTipoProveedor());
				}
				proveedor.setStrListaProveedorDetalle(strListaProveedorDetalle);
				proveedor.setPersona(persona);
				listaProveedor.add(proveedor);
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}
	
	private String obtenerEtiquetaTipoProveedor(Integer intTipoProveedor){
		for(Tabla tabla : listaTablaProveedor){
			if(tabla.getIntIdDetalle().equals(intTipoProveedor)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
	}
	
	private String obtenerEtiquetaTipoVia(Integer intTipoVia){
		for(Tabla tabla : listaTablaTipoVia){
			if(tabla.getIntIdDetalle().equals(intTipoVia)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
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
	
	public void seleccionarPersona(ActionEvent event){
		try{
			Persona personaSeleccionada = (Persona)event.getComponent().getAttributes().get("item");
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				agregarNombreCompleto(personaSeleccionada);
				agregarDocumentoDNI(personaSeleccionada);				
			
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){				
				
			}

			contratoNuevo.setIntPersPersonaServicio(personaSeleccionada.getIntIdPersona());				
			contratoNuevo.setEmpresaServicio(personaSeleccionada);
			
			contratoNuevo.setDomicilioEmpresaServicio(null);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void eliminarRegistro(){
		try{
			Contrato contratoEliminar = registroSeleccionado;
			
			if(contratoEliminar.getOrdenCompra() != null){
				mostrarMensaje(Boolean.FALSE, "No se puede eliminar el contrato, esta siendo referenciado por una Orden de Compra.");
				return;
			}
			
			cargarUsuario();
			contratoEliminar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			contratoEliminar.setTsFechaAnula(obtenerFechaActual());
			contratoEliminar.setIntPersEmpresaAnula(EMPRESA_USUARIO);
			contratoEliminar.setIntPersPersonaAnula(PERSONA_USUARIO);
			
			logisticaFacade.modificarContratoDirecto(contratoEliminar);
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se eliminó correctamente el informe de gerencia.");
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la eliminación de la requisición.");
			log.error(e.getMessage(),e);
		}
	}

	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	public void habilitarPanelInferior(){
		try{
			cargarUsuario();
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			
			contratoNuevo = new Contrato();
			
			contratoNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			contratoNuevo.setTsFechaContrato(obtenerFechaActual());
			contratoNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			contratoNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
			contratoNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			contratoNuevo.setIntPersEmpresaSucursal(EMPRESA_USUARIO);
			
			//añadimos la empresa solicita
			contratoNuevo.setIntPersPersonaSolicita(EMPRESA_USUARIO);
			Persona empresaSolicita = personaFacade.getPersonaJuridicaPorIdPersona(EMPRESA_USUARIO);
			contratoNuevo.setEmpresaSolicita(empresaSolicita);
			
			//añadimos a informeGerenciaNuevo la sucursal del usuario cargada con listaArea
			contratoNuevo.setIntSucuIdSucursal(usuario.getSucursal().getId().getIntIdSucursal());
			seleccionarSucursal();
						
			contratoNuevo.setSeleccionaPagoUnico(Boolean.FALSE);
			contratoNuevo.setSeleccionaRenovacion(Boolean.FALSE);
			
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarSucursal(){
		try{
			for(Sucursal sucursal : listaSucursal){
				if(sucursal.getId().getIntIdSucursal().equals(contratoNuevo.getIntSucuIdSucursal())){
					contratoNuevo.setSucursal(sucursal);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaSucursal() throws Exception{
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(EMPRESA_USUARIO);
		for(Sucursal sucursal : listaSucursal){
			sucursal.setListaArea(empresaFacade.getListaAreaPorSucursal(sucursal));			
		}
		//Ordenamos por nombre
		Collections.sort(listaSucursal, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});		
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
	
	public void seleccionarDepartamento(){
		try{
			listaUbigeoProvincia = generalFacade.getListaUbigeoDeProvinciaPorIdUbigeo(domicilioNuevo.getIntParaUbigeoPkDpto());
			listaUbigeoDistrito = new ArrayList<Ubigeo>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarProvincia(){
		try{
			listaUbigeoDistrito = generalFacade.getListaUbigeoDeDistritoPorIdUbigeo(domicilioNuevo.getIntParaUbigeoPkProvincia());
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
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
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
	public List<Requisicion> getListaRequisicion() {
		return listaRequisicion;
	}
	public void setListaRequisicion(List<Requisicion> listaRequisicion) {
		this.listaRequisicion = listaRequisicion;
	}
	public Contrato getContratoNuevo() {
		return contratoNuevo;
	}
	public void setContratoNuevo(Contrato contratoNuevo) {
		this.contratoNuevo = contratoNuevo;
	}
	public Contrato getContratoFiltro() {
		return contratoFiltro;
	}
	public void setContratoFiltro(Contrato contratoFiltro) {
		this.contratoFiltro = contratoFiltro;
	}
	public Contrato getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(Contrato registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public List<Contrato> getListaContrato() {
		return listaContrato;
	}
	public void setListaContrato(List<Contrato> listaContrato) {
		this.listaContrato = listaContrato;
	}
	public List<Contrato> getListaAnteriorContrato() {
		return listaAnteriorContrato;
	}
	public void setListaAnteriorContrato(List<Contrato> listaAnteriorContrato) {
		this.listaAnteriorContrato = listaAnteriorContrato;
	}
	public Date getDtFiltroDesde() {
		return dtFiltroDesde;
	}
	public void setDtFiltroDesde(Date dtFiltroDesde) {
		this.dtFiltroDesde = dtFiltroDesde;
	}
	public Date getDtFiltroHasta() {
		return dtFiltroHasta;
	}
	public void setDtFiltroHasta(Date dtFiltroHasta) {
		this.dtFiltroHasta = dtFiltroHasta;
	}
	public Integer getIntTipoFiltroFecha() {
		return intTipoFiltroFecha;
	}
	public void setIntTipoFiltroFecha(Integer intTipoFiltroFecha) {
		this.intTipoFiltroFecha = intTipoFiltroFecha;
	}
	public Domicilio getDomicilioNuevo() {
		return domicilioNuevo;
	}
	public void setDomicilioNuevo(Domicilio domicilioNuevo) {
		this.domicilioNuevo = domicilioNuevo;
	}
	public List<Ubigeo> getListaUbigeoDepartamento() {
		return listaUbigeoDepartamento;
	}
	public void setListaUbigeoDepartamento(List<Ubigeo> listaUbigeoDepartamento) {
		this.listaUbigeoDepartamento = listaUbigeoDepartamento;
	}
	public List<Ubigeo> getListaUbigeoProvincia() {
		return listaUbigeoProvincia;
	}
	public void setListaUbigeoProvincia(List<Ubigeo> listaUbigeoProvincia) {
		this.listaUbigeoProvincia = listaUbigeoProvincia;
	}
	public List<Ubigeo> getListaUbigeoDistrito() {
		return listaUbigeoDistrito;
	}
	public void setListaUbigeoDistrito(List<Ubigeo> listaUbigeoDistrito) {
		this.listaUbigeoDistrito = listaUbigeoDistrito;
	}
	public List<Proveedor> getListaProveedor() {
		return listaProveedor;
	}
	public void setListaProveedor(List<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}
}