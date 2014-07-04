package pe.com.tumi.tesoreria.logistica.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.fileupload.FileUploadController;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedorDetalle;
import pe.com.tumi.tesoreria.logistica.domain.Proveedor;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorId;
import pe.com.tumi.tesoreria.logistica.domain.Requisicion;
import pe.com.tumi.tesoreria.logistica.facade.LogisticaFacadeLocal;


public class CuadroController {

	protected static Logger log = Logger.getLogger(CuadroController.class);
	
	PersonaFacadeRemote 	personaFacade;
	EmpresaFacadeRemote		empresaFacade;
	LogisticaFacadeLocal	logisticaFacade;
	GeneralFacadeRemote		generalFacade;
	TablaFacadeRemote		tablaFacade;
	
	private	CuadroComparativo	cuadroComparativoNuevo;
	private CuadroComparativo	cuadroComparativoFiltro;
	private CuadroComparativo	registroSeleccionado;
	private CuadroComparativoProveedor	cuadroComparativoProveedorNuevo;
	private CuadroComparativoProveedorDetalle	cuadroComparativoProveedorDetalleNuevo;
	private NumberFormat 		formato;
	
	private List<CuadroComparativo>	listaCuadroComparativo;
	private List<Persona>		listaPersona;
	private List<Requisicion>	listaRequisicion;
	private List<Sucursal>		listaSucursal;
	private List<Tabla>			listaMoneda;
	private List<Proveedor>		listaProveedor;
	private List<Tabla>			listaTablaProveedor;
	
	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private Integer		intTipoPersona;
	private String		strFiltroTextoPersona;
	private String		strMensajeProveedorDetalle;
	private String		strMensajeProveedor;
	private Integer		intItemProveedorSeleccionado;
	private Integer		intTipoAccion;
	
	private	Integer		EMPRESA_USUARIO;
	private	Integer		PERSONA_USUARIO;
	private	Integer		ACCION_REGISTRAR = 1;
	private	Integer		ACCION_APROBAR = 2;
	private	Integer		ACCION_VER = 3;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean poseePermiso;
	private boolean mostrarMensajeProveedor;
	private boolean mostrarMensajeProveedorDetalle;
	
	public CuadroController(){
		cargarUsuario();
		//poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_CUADROCOMPARATIVO);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
	}
	
	public String getLimpiarCuadro(){
		cargarUsuario();
		//poseePermiso = Boolean.TRUE;
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_CUADROCOMPARATIVO);
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
			//Agregado por cdelosrios, 25/11/2013
			deshabilitarPanelInferior();
			//Fin agregado por cdelosrios, 25/11/2013
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
			generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class); 
			
			listaMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
			listaTablaProveedor = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOPROVEEDOR));
			cuadroComparativoFiltro = new CuadroComparativo();
			listaCuadroComparativo = new ArrayList<CuadroComparativo>();
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			listaSucursal = MyUtil.cargarListaSucursal(EMPRESA_USUARIO);
			
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#,###.00",otherSymbols);
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
			if(cuadroComparativoNuevo.getRequisicion()==null){
				mostrarMensaje(Boolean.FALSE, "Debe agregar una requisicón."); return;
			}
			if(cuadroComparativoNuevo.getListaCuadroComparativoProveedor().isEmpty()){
				mostrarMensaje(Boolean.FALSE, "Debe agregar al menos un proveedor."); return;
			}
			
			if(cuadroComparativoNuevo.getId().getIntItemCuadroComparativo()==null){
				logisticaFacade.grabarCuadroComparativo(cuadroComparativoNuevo);
				buscar();
				mostrarMensaje(Boolean.TRUE, "Se registró correctamente el Cuadro Comparativo.");
				
			}else{
				if(cuadroComparativoNuevo.getPersonaAprueba()==null){
					mostrarMensaje(Boolean.FALSE, "Debe seleccionar a la persona que aprueba."); return;
				}
				if(cuadroComparativoNuevo.getStrObservacionAutoriza()==null || cuadroComparativoNuevo.getStrObservacionAutoriza().isEmpty()){
					mostrarMensaje(Boolean.FALSE, "Debe ingresar la observacion de autorizacion."); return;
				}
				cuadroComparativoNuevo.getProveedorAprobado().setIntParaEstadoSeleccion(Constante.PARAM_T_SELECCIONPROVEEDOR_SELECCIONADO);
				cuadroComparativoNuevo.setIntParaEstadoAprobacion(Constante.PARAM_T_ESTADOAPROBACIONCUADRO_APROBADO);
				//Agregado por cdelosrios, 08/10/2013
				cuadroComparativoNuevo.setIntItemCuadroComparativoPropuestaAutoriza(intItemProveedorSeleccionado);
				cuadroComparativoNuevo.setTsFechaAutoriza(new Timestamp(new Date().getTime()));
				//Fin agregado por cdelosrios, 08/10/2013
				
				logisticaFacade.modificarCuadroComparativo(cuadroComparativoNuevo);
				buscar();
				mostrarMensaje(Boolean.TRUE, "Se aprobó correctamente el Cuadro Comparativo.");
			}
			
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
			intTipoAccion = ACCION_VER;
		} catch (Exception e) {
			mostrarMensaje(Boolean.FALSE,"Ocurrio un error durante el proceso de registro del Cuadro Comparativo.");
			log.error(e.getMessage(),e);
		}
	}	
	
	public void buscar(){
		try{
			cargarUsuario();
			
			cuadroComparativoFiltro.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			
			if(cuadroComparativoFiltro.getIntSucuIdSucursalFiltro().equals(new Integer(0)))
				cuadroComparativoFiltro.setIntSucuIdSucursalFiltro(null);
				
			if(cuadroComparativoFiltro.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS))
				cuadroComparativoFiltro.setIntParaEstado(null);			
			
			listaCuadroComparativo = logisticaFacade.buscarCuadroComparativo(cuadroComparativoFiltro);
			
			for(CuadroComparativo cuadroComparativo : listaCuadroComparativo){
				for(Sucursal sucursal : listaSucursal){
					if(sucursal.getId().getIntIdSucursal().equals(cuadroComparativo.getRequisicion().getIntSucuIdSucursal()))
						cuadroComparativo.getRequisicion().setSucursalSolicitante(sucursal);
				}
				cuadroComparativo.setOrdenCompra(logisticaFacade.obtenerOrdenCompraPorCuadroComparativo(cuadroComparativo));
			}
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			cargarUsuario();
			registroSeleccionado = (CuadroComparativo)event.getComponent().getAttributes().get("item");
			log.info(registroSeleccionado);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistroAprobar(){
		try{
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			intTipoAccion = ACCION_APROBAR;
			
			cuadroComparativoNuevo = registroSeleccionado;
			
			añadirEtiquetaRequisicion(cuadroComparativoNuevo.getRequisicion());			
			
			for(CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativoNuevo.getListaCuadroComparativoProveedor()){
				Persona persona = personaFacade.getPersonaJuridicaPorIdPersona(cuadroComparativoProveedor.getIntPersPersonaProveedor());
				cuadroComparativoProveedor.setPersona(persona);
				
				ArchivoId archivoId = new ArchivoId();
				archivoId.setIntParaTipoCod(cuadroComparativoProveedor.getIntParaTipo());
				archivoId.setIntItemArchivo(cuadroComparativoProveedor.getIntItemArchivo());
				archivoId.setIntItemHistorico(cuadroComparativoProveedor.getIntItemHistorico());			
				cuadroComparativoProveedor.setArchivoDocumento(generalFacade.getArchivoPorPK(archivoId));
				
				String strEtiqueta = "Propuesta "+cuadroComparativoProveedor.getId().getIntItemCuadroComparativoProveedor()+" : ";
				strEtiqueta = strEtiqueta + cuadroComparativoProveedor.getPersona().getJuridica().getStrRazonSocial()+" ";
				strEtiqueta = strEtiqueta + "Monto : "+formato.format(cuadroComparativoProveedor.getBdPrecioTotal())+" ";
				strEtiqueta = strEtiqueta + obtenerEtiquetaTipoMoneda(cuadroComparativoProveedor.getIntParaTipoMoneda());
				cuadroComparativoProveedor.setStrEtiqueta(strEtiqueta);
			}
			
			CuadroComparativoProveedor cuadroComparativoProveedor = cuadroComparativoNuevo.getListaCuadroComparativoProveedor().get(0);
			intItemProveedorSeleccionado = cuadroComparativoProveedor.getId().getIntItemCuadroComparativoProveedor();
			cuadroComparativoNuevo.setProveedorAprobado(cuadroComparativoProveedor);
			
			
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void verRegistro(){
		try{
			deshabilitarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			intTipoAccion = ACCION_VER;
			
			cuadroComparativoNuevo = registroSeleccionado;
			
			añadirEtiquetaRequisicion(cuadroComparativoNuevo.getRequisicion());			
			
			for(CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativoNuevo.getListaCuadroComparativoProveedor()){
				Persona persona = personaFacade.getPersonaJuridicaPorIdPersona(cuadroComparativoProveedor.getIntPersPersonaProveedor());
				cuadroComparativoProveedor.setPersona(persona);
				
				ArchivoId archivoId = new ArchivoId();
				archivoId.setIntParaTipoCod(cuadroComparativoProveedor.getIntParaTipo());
				archivoId.setIntItemArchivo(cuadroComparativoProveedor.getIntItemArchivo());
				archivoId.setIntItemHistorico(cuadroComparativoProveedor.getIntItemHistorico());			
				cuadroComparativoProveedor.setArchivoDocumento(generalFacade.getArchivoPorPK(archivoId));
				
				String strEtiqueta = "Propuesta "+cuadroComparativoProveedor.getId().getIntItemCuadroComparativoProveedor()+" : ";
				strEtiqueta = strEtiqueta + cuadroComparativoProveedor.getPersona().getJuridica().getStrRazonSocial()+" ";
				strEtiqueta = strEtiqueta + "Monto : "+formato.format(cuadroComparativoProveedor.getBdPrecioTotal())+" ";
				strEtiqueta = strEtiqueta + obtenerEtiquetaTipoMoneda(cuadroComparativoProveedor.getIntParaTipoMoneda());
				cuadroComparativoProveedor.setStrEtiqueta(strEtiqueta);
			}
			
			intItemProveedorSeleccionado = cuadroComparativoNuevo.getProveedorAprobado().getId().getIntItemCuadroComparativoProveedor();			
			
			Persona personaAprueba = personaFacade.getPersonaNaturalPorIdPersona(cuadroComparativoNuevo.getIntPersPersonaAutoriza());
			agregarDocumentoDNI(personaAprueba);
			agregarNombreCompleto(personaAprueba);			
			cuadroComparativoNuevo.setPersonaAprueba(personaAprueba);
			
			
			habilitarGrabar = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	private String obtenerEtiquetaTipoMoneda(Integer intTipoMoneda){
		for(Tabla tabla : listaMoneda){
			if(tabla.getIntIdDetalle().equals(intTipoMoneda)){
				return tabla.getStrDescripcion();
			}
		}
		return "";
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
			listaRequisicion = logisticaFacade.obtenerListaRequisicionReferencia(EMPRESA_USUARIO, Constante.PARAM_T_APROBACION_EVALUACIONPROVEEDORES);			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	
	public void seleccionarRequisicion(ActionEvent event){
		try{
			Requisicion requisicionAgregar = (Requisicion)event.getComponent().getAttributes().get("item");
			
			añadirEtiquetaRequisicion(requisicionAgregar);
			cuadroComparativoNuevo.setIntPersEmpresaRequisicion(requisicionAgregar.getId().getIntPersEmpresa());
			cuadroComparativoNuevo.setIntItemRequisicion(requisicionAgregar.getId().getIntItemRequisicion());
			cuadroComparativoNuevo.setRequisicion(requisicionAgregar);
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
			+" - "+tablaAprobacion.getStrDescripcion();
		
		List<Tabla> listaTablaMoneda = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TIPOMONEDA));
		strEtiqueta = strEtiqueta 
		    + " - "+ MyUtil.convertirMonto(requisicionAgregar.getBdMontoLogistica()) 
			+ " "  + MyUtil.obtenerEtiquetaTabla(requisicionAgregar.getIntParaTipoMoneda(), listaTablaMoneda)
			+ " - " + MyUtil.convertirFecha(requisicionAgregar.getTsFechaRequisicion());
		
		requisicionAgregar.setStrEtiqueta(strEtiqueta);
	}
	
	public void abrirPopUpBuscarPersonaJuridica(){
		try{
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_JURIDICA;
			strFiltroTextoPersona = "";
			listaProveedor = new ArrayList<Proveedor>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpBuscarPersonaNatural(){
		try{
			intTipoPersona = Constante.PARAM_T_TIPOPERSONA_NATURAL;
			strFiltroTextoPersona = "";
			listaPersona = new ArrayList<Persona>();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpAgregarProveedor(){
		try{
			strMensajeProveedor = "";
			mostrarMensajeProveedor = Boolean.FALSE;
			cuadroComparativoProveedorNuevo = new CuadroComparativoProveedor();		
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void abrirPopUpAgregarProveedorDetalle(){
		try{
			strMensajeProveedorDetalle = "";
			mostrarMensajeProveedorDetalle = Boolean.FALSE;
			cuadroComparativoProveedorDetalleNuevo = new CuadroComparativoProveedorDetalle();
			if(!cuadroComparativoProveedorNuevo.getListaCuadroComparativoProveedorDetalle().isEmpty()){
				CuadroComparativoProveedorDetalle cuadroComparativoProveedorDetalle = 
					cuadroComparativoProveedorNuevo.getListaCuadroComparativoProveedorDetalle().get(0); 
				cuadroComparativoProveedorDetalleNuevo.setIntParaTipoMoneda(cuadroComparativoProveedorDetalle.getIntParaTipoMoneda());
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void aceptarAdjuntarDocumento(){
		try{
			FileUploadController fileUploadController = (FileUploadController)getSessionBean("fileUploadController");
			Archivo archivoDocumento = fileUploadController.getArchivoDocumento();
			
			cuadroComparativoProveedorNuevo.setIntParaTipo(archivoDocumento.getId().getIntParaTipoCod());
			cuadroComparativoProveedorNuevo.setIntItemArchivo(archivoDocumento.getId().getIntItemArchivo());
			cuadroComparativoProveedorNuevo.setIntItemHistorico(archivoDocumento.getId().getIntItemHistorico());
			cuadroComparativoProveedorNuevo.setArchivoDocumento(archivoDocumento);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscarPersona(){
		try{
			listaProveedor = new ArrayList<Proveedor>();
			listaPersona = new ArrayList<Persona>();
			log.info("--buscarPersona");
			Persona persona = null;
			boolean proveedorValido = Boolean.TRUE;
			if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_NATURAL)){
				persona = personaFacade.getPersonaNaturalPorDocIdentidadYIdEmpresa(
						Constante.PARAM_T_INT_TIPODOCUMENTO_DNI, 
						strFiltroTextoPersona, 
						EMPRESA_USUARIO);
				proveedorValido = MyUtil.poseeRol(persona,Constante.PARAM_T_TIPOROL_PERSONAL);
				if(proveedorValido)
					listaPersona.add(persona);
				
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){
				persona = personaFacade.getPersonaJuridicaYListaPersonaPorRucYIdEmpresa2(strFiltroTextoPersona,EMPRESA_USUARIO);				
				proveedorValido = MyUtil.poseeRol(persona,Constante.PARAM_T_TIPOROL_PROVEEDOR) 
				&& (persona.getJuridica().getIntCondContribuyente()!=null 
					&& !persona.getJuridica().getIntCondContribuyente().equals(Constante.PARAM_T_TIPOCONDCONTRIBUYENTE_NOHABIDO))
				&& (persona.getJuridica().getIntEstadoContribuyenteCod() !=null 
					&& !persona.getJuridica().getIntEstadoContribuyenteCod().equals(Constante.PARAM_T_TIPOESTADOCONTRIB_INACTIVO));
				
				for(CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativoNuevo.getListaCuadroComparativoProveedor()){
					//Una persona solo puede estar 1 vez como proveedor a ser evaluado en el cuadro
					if(cuadroComparativoProveedor.getPersona().getIntIdPersona().equals(persona.getIntIdPersona()))
						return;				
				}
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
			}
			
			log.info(persona);
			
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
			
				cuadroComparativoNuevo.setIntPersEmpresaAutoriza(EMPRESA_USUARIO);
				cuadroComparativoNuevo.setIntPersPersonaAutoriza(personaSeleccionada.getIntIdPersona());				
				cuadroComparativoNuevo.setPersonaAprueba(personaSeleccionada);
				
			}else if(intTipoPersona.equals(Constante.PARAM_T_TIPOPERSONA_JURIDICA)){			
				
				cuadroComparativoProveedorNuevo.setIntPersEmpresaProveedor(EMPRESA_USUARIO);
				cuadroComparativoProveedorNuevo.setIntPersPersonaProveedor(personaSeleccionada.getIntIdPersona());				
				cuadroComparativoProveedorNuevo.setPersona(personaSeleccionada);
			}
			
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarProveedor(){
		try{
			for(CuadroComparativoProveedor cuadroComparativoProveedor : cuadroComparativoNuevo.getListaCuadroComparativoProveedor()){
				if(cuadroComparativoProveedor.getId().getIntItemCuadroComparativoProveedor().equals(intItemProveedorSeleccionado)){
					cuadroComparativoNuevo.setProveedorAprobado(cuadroComparativoProveedor);
					break;
				}
			}			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}	
	
	public void eliminarRegistro(){
		try{
			CuadroComparativo cuadroComparativoEliminar = registroSeleccionado;
			
			if(cuadroComparativoEliminar.getOrdenCompra()!=null){
				mostrarMensaje(Boolean.TRUE, "No se puede eliminar el Cuadro Comparativo porque esta asociado a una Orden de Compra.");
			}
			
			cargarUsuario();
			cuadroComparativoEliminar.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			cuadroComparativoEliminar.setTsFechaAnula(obtenerFechaActual());
			cuadroComparativoEliminar.setIntPersEmpresaAnula(EMPRESA_USUARIO);
			cuadroComparativoEliminar.setIntPersPersonaAnula(PERSONA_USUARIO);
			
			logisticaFacade.modificarCuadroComparativoDirecto(cuadroComparativoEliminar);
			buscar();
			mostrarMensaje(Boolean.TRUE, "Se eliminó correctamente el cuadro comparativo.");
		}catch (Exception e) {
			mostrarMensaje(Boolean.FALSE, "Hubo un error durante la eliminación de el cuadro comparativo.");
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
			intTipoAccion = ACCION_REGISTRAR;
			
			cuadroComparativoNuevo = new CuadroComparativo();
			
			cuadroComparativoNuevo.getId().setIntPersEmpresa(EMPRESA_USUARIO);
			cuadroComparativoNuevo.setTsFechaRegistro(obtenerFechaActual());
			cuadroComparativoNuevo.setIntPersEmpresaUsuario(EMPRESA_USUARIO);
			cuadroComparativoNuevo.setIntPersPersonaUsuario(PERSONA_USUARIO);
			cuadroComparativoNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);			
			cuadroComparativoNuevo.setIntParaEstadoAprobacion(Constante.PARAM_T_ESTADOAPROBACIONCUADRO_PENDIENTE);
			
			
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
	
	public void agregarProveedor(){
		try{
			strMensajeProveedor = "";
			mostrarMensajeProveedor = Boolean.TRUE;
			
			if(cuadroComparativoProveedorNuevo.getPersona()==null){
				strMensajeProveedor = "Debe agregar una persona juridica."; return;
			}
			if(cuadroComparativoProveedorNuevo.getTsPlazoEntrega()==null){
				strMensajeProveedor = "Debe agregar un plazo de entrega."; return;
			}
			if(cuadroComparativoProveedorNuevo.getStrLugarEntrega()==null || cuadroComparativoProveedorNuevo.getStrLugarEntrega().isEmpty()){
				strMensajeProveedor = "Debe agregar un lugar de entrega."; return;
			}
			if(cuadroComparativoProveedorNuevo.getStrGarantia()==null || cuadroComparativoProveedorNuevo.getStrGarantia().isEmpty()){
				strMensajeProveedor = "Debe agregar una garantía."; return;
			}
			if(cuadroComparativoProveedorNuevo.getStrCondicionPago()==null || cuadroComparativoProveedorNuevo.getStrCondicionPago().isEmpty()){
				strMensajeProveedor = "Debe agregar una condición de pago."; return;
			}
			if(cuadroComparativoProveedorNuevo.getStrDescuento()==null || cuadroComparativoProveedorNuevo.getStrDescuento().isEmpty()){
				strMensajeProveedor = "Debe agregar un descuento."; return;
			}
			if(cuadroComparativoProveedorNuevo.getListaCuadroComparativoProveedorDetalle().isEmpty()){
				strMensajeProveedor = "Debe agregar al menos un producto/servicio."; return;
			}
			if(cuadroComparativoProveedorNuevo.getArchivoDocumento()==null){
				strMensajeProveedor = "Debe adjuntar una propuesta."; return;
			}
			if(cuadroComparativoProveedorNuevo.getStrObservacion()==null || cuadroComparativoProveedorNuevo.getStrObservacion().isEmpty()){
				strMensajeProveedor = "Debe agregar una observación."; return;
			}
			
			mostrarMensajeProveedor = Boolean.FALSE;
			
			//cuadroComparativoProveedorNuevo.setIntParaEstadoSeleccion(Constante);
			BigDecimal bdPrecioTotal = new BigDecimal(0);
			Integer intParaMoneda = null;
			for(CuadroComparativoProveedorDetalle proveedorDetalle : cuadroComparativoProveedorNuevo.getListaCuadroComparativoProveedorDetalle()){
				bdPrecioTotal = bdPrecioTotal.add(proveedorDetalle.getBdPrecioUnitario().multiply(proveedorDetalle.getBdCantidad()));
				intParaMoneda = proveedorDetalle.getIntParaTipoMoneda();
			}
			cuadroComparativoProveedorNuevo.setBdPrecioTotal(bdPrecioTotal);
			cuadroComparativoProveedorNuevo.setIntParaTipoMoneda(intParaMoneda);
			cuadroComparativoProveedorNuevo.setIntParaEstadoSeleccion(Constante.PARAM_T_SELECCIONPROVEEDOR_NOSELECCIONADO);
			
			cuadroComparativoNuevo.getListaCuadroComparativoProveedor().add(cuadroComparativoProveedorNuevo);			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void agregarProveedorDetalle(){
		try{
			strMensajeProveedorDetalle = "";
			mostrarMensajeProveedorDetalle = Boolean.TRUE;
			
			if(cuadroComparativoProveedorDetalleNuevo.getStrDetalle()==null  || cuadroComparativoProveedorDetalleNuevo.getStrDetalle().isEmpty()){
				strMensajeProveedorDetalle = "Debe agregar una descripción."; return;
			}
			if(cuadroComparativoProveedorDetalleNuevo.getStrMarca()==null || cuadroComparativoProveedorDetalleNuevo.getStrMarca().isEmpty()){
				strMensajeProveedorDetalle = "Debe agregar una marca."; return;
			}
			if(cuadroComparativoProveedorDetalleNuevo.getBdCantidad()==null || cuadroComparativoProveedorDetalleNuevo.getBdCantidad().signum()<=0){
				strMensajeProveedorDetalle = "Debe agregar una cantidad válida."; return;
			}
			if(cuadroComparativoProveedorDetalleNuevo.getBdPrecioUnitario()==null || cuadroComparativoProveedorDetalleNuevo.getBdPrecioUnitario().signum()<=0){
				strMensajeProveedorDetalle = "Debe agregar un precio unitario válido."; return;
			}
			
			mostrarMensajeProveedorDetalle = Boolean.FALSE;
			
			cuadroComparativoProveedorDetalleNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			cuadroComparativoProveedorNuevo.getListaCuadroComparativoProveedorDetalle().add(cuadroComparativoProveedorDetalleNuevo);			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	public void quitarProveedorDetalle(ActionEvent event){
		try{
			CuadroComparativoProveedorDetalle proveedorDetalleQuitar = 
				(CuadroComparativoProveedorDetalle)event.getComponent().getAttributes().get("item");
			cuadroComparativoProveedorNuevo.getListaCuadroComparativoProveedorDetalle().remove(proveedorDetalleQuitar);
			
		}catch(Exception e){
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
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public List<Requisicion> getListaRequisicion() {
		return listaRequisicion;
	}
	public void setListaRequisicion(List<Requisicion> listaRequisicion) {
		this.listaRequisicion = listaRequisicion;
	}
	public CuadroComparativo getCuadroComparativoNuevo() {
		return cuadroComparativoNuevo;
	}
	public void setCuadroComparativoNuevo(CuadroComparativo cuadroComparativoNuevo) {
		this.cuadroComparativoNuevo = cuadroComparativoNuevo;
	}
	public CuadroComparativo getCuadroComparativoFiltro() {
		return cuadroComparativoFiltro;
	}
	public void setCuadroComparativoFiltro(CuadroComparativo cuadroComparativoFiltro) {
		this.cuadroComparativoFiltro = cuadroComparativoFiltro;
	}
	public CuadroComparativo getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(CuadroComparativo registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
	public List<CuadroComparativo> getListaCuadroComparativo() {
		return listaCuadroComparativo;
	}
	public void setListaCuadroComparativo(List<CuadroComparativo> listaCuadroComparativo) {
		this.listaCuadroComparativo = listaCuadroComparativo;
	}
	public CuadroComparativoProveedor getCuadroComparativoProveedorNuevo() {
		return cuadroComparativoProveedorNuevo;
	}
	public void setCuadroComparativoProveedorNuevo(CuadroComparativoProveedor cuadroComparativoProveedorNuevo) {
		this.cuadroComparativoProveedorNuevo = cuadroComparativoProveedorNuevo;
	}
	public CuadroComparativoProveedorDetalle getCuadroComparativoProveedorDetalleNuevo() {
		return cuadroComparativoProveedorDetalleNuevo;
	}
	public void setCuadroComparativoProveedorDetalleNuevo(CuadroComparativoProveedorDetalle cuadroComparativoProveedorDetalleNuevo) {
		this.cuadroComparativoProveedorDetalleNuevo = cuadroComparativoProveedorDetalleNuevo;
	}
	public String getStrMensajeProveedorDetalle() {
		return strMensajeProveedorDetalle;
	}
	public void setStrMensajeProveedorDetalle(String strMensajeProveedorDetalle) {
		this.strMensajeProveedorDetalle = strMensajeProveedorDetalle;
	}
	public String getStrMensajeProveedor() {
		return strMensajeProveedor;
	}
	public void setStrMensajeProveedor(String strMensajeProveedor) {
		this.strMensajeProveedor = strMensajeProveedor;
	}
	public boolean isMostrarMensajeProveedor() {
		return mostrarMensajeProveedor;
	}
	public void setMostrarMensajeProveedor(boolean mostrarMensajeProveedor) {
		this.mostrarMensajeProveedor = mostrarMensajeProveedor;
	}
	public boolean isMostrarMensajeProveedorDetalle() {
		return mostrarMensajeProveedorDetalle;
	}
	public void setMostrarMensajeProveedorDetalle(boolean mostrarMensajeProveedorDetalle) {
		this.mostrarMensajeProveedorDetalle = mostrarMensajeProveedorDetalle;
	}
	public Integer getIntItemProveedorSeleccionado() {
		return intItemProveedorSeleccionado;
	}
	public void setIntItemProveedorSeleccionado(Integer intItemProveedorSeleccionado) {
		this.intItemProveedorSeleccionado = intItemProveedorSeleccionado;
	}
	public Integer getIntTipoAccion() {
		return intTipoAccion;
	}
	public void setIntTipoAccion(Integer intTipoAccion) {
		this.intTipoAccion = intTipoAccion;
	}
	public List<Proveedor> getListaProveedor() {
		return listaProveedor;
	}
	public void setListaProveedor(List<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}
	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}
	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}
}