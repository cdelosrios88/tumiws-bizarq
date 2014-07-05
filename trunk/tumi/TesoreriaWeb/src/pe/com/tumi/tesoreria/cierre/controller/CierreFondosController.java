package pe.com.tumi.tesoreria.cierre.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.ConvertirLetras;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.facade.BancoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;
import pe.com.tumi.tesoreria.egreso.facade.CierreFondoFijoFacadeLocal;
import pe.com.tumi.tesoreria.egreso.facade.EgresoFacadeLocal;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalleInterfaz;
import pe.com.tumi.tesoreria.ingreso.facade.IngresoFacadeLocal;


public class CierreFondosController {

	protected static Logger log = Logger.getLogger(CierreFondosController.class);
	
	private EmpresaFacadeRemote empresaFacade;
	private PersonaFacadeRemote personaFacade;
	private EgresoFacadeLocal 	egresoFacade;
	private TablaFacadeRemote 	tablaFacade;
	private BancoFacadeLocal 	bancoFacade;
	private IngresoFacadeLocal 	ingresoFacade;
	
	private List<ControlFondosFijos>	listaControlFondosFijos;
	private List<ControlFondosFijos>	listaControlFondosFijosBus;
	private List<ControlFondosFijos>	listaControlFondosFijosCerrar;
	private	List<Sucursal>		listaSucursal;
	private	List<Tabla>		listaSucursalBus;
	private List<IngresoDetalleInterfaz>listaIngresoDetalleInterfaz;
	
	private	ControlFondosFijos	controlFondosFijosFiltro;
	private	ControlFondosFijos 	controlFondosFijosCerrar;
	private Bancofondo			bancoFondoIngresar;
	

	
//	private Usuario 	usuario;
	private String 		mensajeOperacion;
	private Integer		intTipoCierre;
	private Integer		intAñoFiltro;
	private	Integer		intEstadoFiltro;
	private	Integer		intNumeroFiltro;
	private	Integer		intOperacion;
	private String		strMontoGirar;
	private String		strObservacion;
	
	private	final Integer	REGISTRAR_CIERRE = 1;
	private	final Integer	ANULAR_CIERRE = 2;

	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean datosValidados;
	private boolean poseePermiso;
	
	//Agregado 05.12.2013 JCHAVEZ
	//Inicio de Sesión
	private Usuario usuarioSesion;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
//	private Integer SESION_IDPERFIL;
	private Integer intIdSucursal;
	private Integer intAnioBusqueda;
	private Integer intEstadoFondo;
	private Integer intTipoFondoFijo;
	private Integer intParaTipoAnulaFondo;
	private Timestamp tsFechaCierreAnular;
	private boolean datosValidadosAnulacion;
	private boolean mostrarPanelInferiorAnulacion;
	private List<ControlFondosFijos> listaControlFondosFijosTemp;
	private List<SelectItem> listYears;
	private	List<Tabla>	listaTipoFondoFijo;
	private Boolean blnDisabledSucursal;
	private	List<ControlFondosFijos> listaControlFondosFijosAnular;
	private	ControlFondosFijos 	controlFondosFijosAnular;
	private CierreFondoFijoFacadeLocal cierreFdoFijoFacade;
	
	
	public CierreFondosController(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.TRANSACCION_CIERRE_CIERREFONDOS);
		if(usuarioSesion!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}
	
	public String getInicioPage() {
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.TRANSACCION_CIERRE_CIERREFONDOS);
		if(usuarioSesion!=null && poseePermiso){
			limpiarFormulario();
			deshabilitarPanelInferior();
			intIdSucursal = SESION_IDSUCURSAL;
			
			if (SESION_IDSUCURSAL.equals(59)) {
				blnDisabledSucursal = Boolean.FALSE;
			}else blnDisabledSucursal = Boolean.TRUE;
			
		}else log.error("--Usuario obtenido es NULL.");
		return "";
	}
	
	private void limpiarFormulario(){
		listaControlFondosFijosBus.clear();
		listaControlFondosFijos.clear();
		intAnioBusqueda = 0;
		intTipoFondoFijo = 0;
		deshabilitarPanelInferior();
	}
	private void cargarUsuario(){
//		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
//		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
//		SESION_IDEMPRESA = usuarioSesion.getPerfil().getId().getIntPersEmpresaPk();
		
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
//		SESION_IDPERFIL = usuarioSesion.getPerfil().getId().getIntIdPerfil();
	}
	
	public void cargarValoresIniciales(){
		cargarUsuario();
		listaControlFondosFijos = new ArrayList<ControlFondosFijos>();
		listaControlFondosFijosBus = new ArrayList<ControlFondosFijos>();
		listaControlFondosFijosAnular = new ArrayList<ControlFondosFijos>();
		listaControlFondosFijosTemp = new ArrayList<ControlFondosFijos>();
		controlFondosFijosFiltro = new ControlFondosFijos();
		controlFondosFijosFiltro.getId().setIntPersEmpresa(SESION_IDEMPRESA);
		
		try{			
			empresaFacade = (EmpresaFacadeRemote) EJBFactory.getRemote(EmpresaFacadeRemote.class);
			egresoFacade = (EgresoFacadeLocal) EJBFactory.getLocal(EgresoFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			bancoFacade = (BancoFacadeLocal) EJBFactory.getLocal(BancoFacadeLocal.class);
			ingresoFacade = (IngresoFacadeLocal) EJBFactory.getLocal(IngresoFacadeLocal.class);
			cierreFdoFijoFacade = (CierreFondoFijoFacadeLocal) EJBFactory.getLocal(CierreFondoFijoFacadeLocal.class);
			listaTipoFondoFijo = tablaFacade.getListaTablaPorAgrupamientoA(Integer.parseInt(Constante.PARAM_T_TIPOFONDOFIJO), "A");
			cargarListaSucursal();
			cargarListaSucursalBus();
			getListAnios();
			
			intIdSucursal = SESION_IDSUCURSAL;
			//Si la Sucursal es Sede Central(59), esta podra ver el combo de Sucursal habilitados, las otras 
			//sedes solo veran su sucursal con el combo bloqueado
			if (SESION_IDSUCURSAL.equals(59)) {
				blnDisabledSucursal = Boolean.FALSE;
			}else blnDisabledSucursal = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarListaSucursal() throws Exception{
		listaSucursal = empresaFacade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
		List<Sucursal> listaSucursalTemp = listaSucursal;
		//Ordenamos por nombre
		Collections.sort(listaSucursalTemp, new Comparator<Sucursal>(){
			public int compare(Sucursal uno, Sucursal otro) {
				return uno.getJuridica().getStrSiglas().compareTo(otro.getJuridica().getStrSiglas());
			}
		});
	}
	
	private void cargarListaSucursalBus() throws NumberFormatException, BusinessException{
		listaSucursalBus = new ArrayList<Tabla>();
//		listaSucursalBus = tablaFacade.getListaTablaPorIdMaestro(Integer.parseInt(Constante.PARAM_T_TOTALES_SUCURSALES));
		Sucursal sucursal;
		Tabla tabla;
		for(Object o : listaSucursal){
			 sucursal = (Sucursal)o;
			 tabla = new Tabla();
			 tabla.setIntIdDetalle(sucursal.getId().getIntIdSucursal());
			 tabla.setStrDescripcion(sucursal.getJuridica().getStrSiglas());
			 listaSucursalBus.add(tabla);
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
	
	public void deshabilitarPanelInferior(){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarPanelInferiorAnulacion = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	
	public void habilitarPanelInferior(){
		try{			
			datosValidados = Boolean.FALSE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void buscar(){
		listaControlFondosFijos.clear();
		listaControlFondosFijosTemp.clear();
		List<Subsucursal> listaSubsucursal = null;
		EmpresaFacadeRemote facade = null;
		try{			
			if(intIdSucursal.equals(new Integer(0))){
				mostrarMensaje(Boolean.FALSE, "Debe seleccionar una sucursal");
				return;
			}else{
				ocultarMensaje();
			}
			//Obtener las subsucursales de la Sucursal seleccionada			
			facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursal);
			log.info("listaSubsucursal.size: "+listaSubsucursal.size());			
			
			for (Subsucursal subsucursal : listaSubsucursal) {
				listaControlFondosFijosTemp = cierreFdoFijoFacade.getControlFondoFijo(SESION_IDEMPRESA, intTipoFondoFijo, intIdSucursal, subsucursal.getId().getIntIdSubSucursal());
				if (intEstadoFondo.equals(Constante.PARAM_T_ESTADOFONDO_CERRADO)) {
					for (ControlFondosFijos fdoFijo : listaControlFondosFijosTemp) {
						if (fdoFijo.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_CERRADO)) {
							for (ControlFondosFijos movAnterior : listaControlFondosFijosTemp) {
								if (movAnterior.getIntOrden().equals(fdoFijo.getIntOrden()+1) && movAnterior.getIntSudeIdSubsucursal().equals(fdoFijo.getIntSudeIdSubsucursal())) {
									fdoFijo.setStrMovimientoAnterior(movAnterior.getStrNumeroApertura()+"-"+movAnterior.getStrFechaRegistro()+"-S/."+movAnterior.getBdMontoSaldo());
									break;
								}
							}
							listaControlFondosFijos.add(fdoFijo);
							break;
						}
					}
				}
				
				if (intEstadoFondo.equals(Constante.PARAM_T_ESTADOFONDO_ABIERTO)) {
					for (ControlFondosFijos fdoFijo : listaControlFondosFijosTemp) {
						if (fdoFijo.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ABIERTO)) {
							for (ControlFondosFijos movAnterior : listaControlFondosFijosTemp) {
								if (movAnterior.getIntOrden().equals(fdoFijo.getIntOrden()+1) && movAnterior.getIntSudeIdSubsucursal().equals(fdoFijo.getIntSudeIdSubsucursal())) {
									fdoFijo.setStrMovimientoAnterior(movAnterior.getStrNumeroApertura()+"-"+movAnterior.getStrFechaRegistro()+"-S/."+movAnterior.getBdMontoSaldo());
									break;
								}
							}
							listaControlFondosFijos.add(fdoFijo);
							break;
						}
					}
				}
			}
			
			
			
//			List<ControlFondosFijos> listaTemp = null;
//			listaControlFondosFijos = new ArrayList<ControlFondosFijos>();
//			if(intIdSucursal.equals(new Integer(0))){				
//				log.info("No selecciono sucursal");
//				listaTemp = egresoFacade.buscarApertura(controlFondosFijosFiltro, listaSucursal);				
//			}else{
//				log.info("Si selecciono sucursal");
//				listaTemp = listaControlFondosFijosBus;
//			}
//			
//			for(ControlFondosFijos controlFondosFijos : listaTemp){
//				if(controlFondosFijos.getIntEstadoFondo().equals(controlFondosFijosFiltro.getIntEstadoFondo())){
//					if(controlFondosFijos.getAnterior()!=null){
//						obtenerDescripcionControlFondosFijos(controlFondosFijos.getAnterior());
//					}
//					listaControlFondosFijos.add(controlFondosFijos);
//				}
//			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
//	private String obtenerDescripcionControlFondosFijos(ControlFondosFijos controlFondosFijos){
//		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
//		Format formatter2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		String descripcion = "";
//		descripcion = controlFondosFijos.getStrNumeroApertura() + " "+ formatter.format(controlFondosFijos.getTsFechaRegistro())
//			+" - Saldo: " + controlFondosFijos.getBdMontoSaldo()
//			+" Cerrado: "+formatter2.format(controlFondosFijos.getTsFechaCierre());
//		controlFondosFijos.setStrDescripcion(descripcion);
//		return descripcion;
//	}
	
	public void cerrar(ActionEvent event){
		try{
			cargarUsuario();
			listaControlFondosFijosCerrar = new ArrayList<ControlFondosFijos>();
			
			controlFondosFijosCerrar = (ControlFondosFijos)event.getComponent().getAttributes().get("item");
			
			if(controlFondosFijosCerrar.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ABIERTO)){
				listaControlFondosFijosCerrar = new ArrayList<ControlFondosFijos>();			
				listaControlFondosFijosCerrar.add(controlFondosFijosCerrar);
				
				intOperacion = REGISTRAR_CIERRE;
				habilitarPanelInferior();
			}
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private String obtenerDescripcionPersona(Integer intIdPersona)throws Exception{
		Persona persona = personaFacade.getPersonaNaturalPorIdPersona(intIdPersona);
		String strDes = "";
		strDes = persona.getIntIdPersona() + " - " + persona.getNatural().getStrNombres() + " " +
			persona.getNatural().getStrApellidoPaterno() + " "+ persona.getNatural().getStrApellidoMaterno() +
			" - DNI : " + obtenerDNI(persona);
		return strDes;
	}
	
	private String obtenerDNI(Persona persona) throws Exception{
		for(Documento documento : persona.getListaDocumento()){
			if(documento.getIntTipoIdentidadCod().equals(Constante.PARAM_T_INT_TIPODOCUMENTO_DNI)){
				return documento.getStrNumeroIdentidad();
			}
		}
		return "";
	}
	
	public void validarDatos(){
		try{
			bancoFondoIngresar = null;
			//if(intTipoCierre.equals(Constante.PARAM_T_CIERREFONDOFIJO_RENOVACION)){
			controlFondosFijosCerrar.setTsFechaCierre(obtenerFechaActual());
			controlFondosFijosCerrar.setStrDescripcionPersona(obtenerDescripcionPersona(controlFondosFijosCerrar.getIntPersPersonaResponsable()));			
			//}
			
			if(intTipoCierre.equals(Constante.PARAM_T_CIERREFONDOFIJO_LIQUIDACION)){
				strObservacion = "";
				controlFondosFijosCerrar.setIntParaMoneda(egresoFacade.obtenerMonedaDeCFF(controlFondosFijosCerrar));
				strMontoGirar = ConvertirLetras.convertirMontoALetras(controlFondosFijosCerrar.getBdMontoSaldo(), controlFondosFijosCerrar.getIntParaMoneda());				
				controlFondosFijosCerrar.setEgreso(egresoFacade.getEgresoPorControlFondosFijos(controlFondosFijosCerrar));
				listaIngresoDetalleInterfaz = new ArrayList<IngresoDetalleInterfaz>();
				listaIngresoDetalleInterfaz.add(cargarIngresoDetalleInterfaz(controlFondosFijosCerrar));
				
				bancoFondoIngresar = bancoFacade.obtenerBancoFondoParaIngreso(usuarioSesion, controlFondosFijosCerrar.getIntParaMoneda());
				if(bancoFondoIngresar==null || bancoFondoIngresar.getFondoDetalleUsar()==null){
					mostrarMensaje(Boolean.FALSE, "No existe un fondo de caja creado para "+
							usuarioSesion.getSucursal().getJuridica().getStrRazonSocial()+"-"+usuarioSesion.getSubSucursal().getStrDescripcion());
					return;
				}				
			}
			
			datosValidados = Boolean.TRUE;
			habilitarGrabar = Boolean.TRUE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void validarDatosAnulacion(){
		Boolean valida = true;
		List<IngresoDetalle> listaIngresoDetalle = null;
		try{
			for (ControlFondosFijos anular : listaControlFondosFijosTemp) {
				if (anular.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_ABIERTO)) {
					mostrarMensaje(Boolean.FALSE, "Ya existe un registro Abierto");
					datosValidadosAnulacion = Boolean.FALSE;
					habilitarGrabar = Boolean.FALSE;
					valida = false;
					mostrarMensaje(Boolean.FALSE, "Ya existe un Fondo abierto para este registro");
					break;
				}				
			}
			if (!valida) {
				return;
			}else {
				listaIngresoDetalle = ingresoFacade.getPorControlFondosFijos(controlFondosFijosAnular.getId());
				if (listaIngresoDetalle!=null && !listaIngresoDetalle.isEmpty()) {
					valida = false;
					mostrarMensaje(Boolean.FALSE, "Solo se puede anular los cierres por Renovación de Fondos Fijos");
					return;
				}
			}
			
			if (valida) {
				datosValidadosAnulacion = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;			
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private IngresoDetalleInterfaz cargarIngresoDetalleInterfaz(ControlFondosFijos controlFondosFijos) throws Exception{		
		controlFondosFijos.setSubsucursal(empresaFacade.getSubSucursalPorIdSubSucursal(controlFondosFijos.getIntSudeIdSubsucursal()));
		
		IngresoDetalleInterfaz ingresoDetalleInterfaz = new IngresoDetalleInterfaz();
		ingresoDetalleInterfaz.setIntOrden(1);
		ingresoDetalleInterfaz.setIntDocumentoGeneral(controlFondosFijos.getId().getIntParaTipoFondoFijo());
		ingresoDetalleInterfaz.setStrNroDocumento(controlFondosFijos.getStrNumeroApertura());
		ingresoDetalleInterfaz.setPersona(devolverPersonaCargada(controlFondosFijos.getIntPersPersonaResponsable()));
		ingresoDetalleInterfaz.setSucursal(controlFondosFijos.getSucursal());
		ingresoDetalleInterfaz.setSubsucursal(controlFondosFijos.getSubsucursal());
		ingresoDetalleInterfaz.setStrDescripcion("Cierre Fondo");
		ingresoDetalleInterfaz.setBdMonto(controlFondosFijos.getBdMontoSaldo());
		ingresoDetalleInterfaz.setBdSubtotal(ingresoDetalleInterfaz.getBdMonto());
		ingresoDetalleInterfaz.setStrDescSucursal(controlFondosFijos.getStrDescSucursal());
		
		return ingresoDetalleInterfaz;
	}
	
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
	
	private Timestamp obtenerFechaActual(){
		return new Timestamp(new Date().getTime());
	}
	
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			//validaciones

			//fin validaciones
			
			if(registrarNuevo){
				if(intOperacion.equals(REGISTRAR_CIERRE)){
					controlFondosFijosCerrar.setIntPersEmpresaCierre(SESION_IDEMPRESA);
					controlFondosFijosCerrar.setIntPersPersonaCierre(SESION_IDUSUARIO);
					controlFondosFijosCerrar.setIntEstadoFondo(Constante.PARAM_T_ESTADOFONDO_CERRADO);
					
					if(intTipoCierre.equals(Constante.PARAM_T_CIERREFONDOFIJO_RENOVACION)){
						egresoFacade.modificarControlFondosFijos(controlFondosFijosCerrar);
						mensaje = "Se registró correctamente el Cierre de Fondos Fijos por renovación.";
					}else if(intTipoCierre.equals(Constante.PARAM_T_CIERREFONDOFIJO_LIQUIDACION)){
						controlFondosFijosCerrar.setBdMontoUtilizado(controlFondosFijosCerrar.getBdMontoApertura());
						controlFondosFijosCerrar.setBdMontoSaldo(BigDecimal.ZERO);
						if(strObservacion==null || strObservacion.isEmpty()){
							mensaje = "Debe de ingresar una observación.";return;
						}						
						ingresoFacade.grabarIngresoCierreFondo(controlFondosFijosCerrar, bancoFondoIngresar, usuarioSesion, strObservacion);
						mensaje = "Se registró correctamente el Cierre de Fondos Fijos por liquidación.";
					}
				}
				if (intOperacion.equals(ANULAR_CIERRE)) {					
					egresoFacade.grabarAnulaCierre(controlFondosFijosAnular, SESION_IDEMPRESA, SESION_IDUSUARIO, strObservacion, intParaTipoAnulaFondo);
					mensaje = "Se registró correctamente la Anulación de Cierre de Fondos Fijos";
				}
			}else{
				mensaje = "Se modificó correctamente el cierre de fondos.";
			}
			
//			seleccionarSucursal();
//			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el proceso de cierre de fondos.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
//	public void seleccionarSucursal(){
//		try{
//			if(intIdSucursal.equals(new Integer(0))){
//				return;
//			}
//			controlFondosFijosFiltro.getId().setIntItemPeriodoFondo(intAnioBusqueda);
//			controlFondosFijosFiltro.getId().setIntSucuIdSucursal(intIdSucursal);
//			controlFondosFijosFiltro.getId().setIntParaTipoFondoFijo(intTipoFondoFijo);
//			listaControlFondosFijosBus = egresoFacade.buscarApertura(controlFondosFijosFiltro, listaSucursal);
//
//		}catch (Exception e) {
//			log.error(e.getMessage(),e);
//		}
//	}
	
	/*Agregado 05.12.2013 JCHAVEZ*/
	public void getListAnios() {
		log.info("-------------------------------------Debugging AsociativoController.getListAnios-------------------------------------");
		listYears = new ArrayList<SelectItem>(); 
		try {
			int year=Calendar.getInstance().get(Calendar.YEAR)+5;
			int cont=0;

			for(int j=year; j>=year-6; j--){
				cont++;
			}			
			for(int i=0; i<cont; i++){
				listYears.add(i, new SelectItem(year));
				year--;
			}	
		} catch (Exception e) {
			log.error("Error en getListYears ---> "+e);
		}
	}
	
	public void anular(ActionEvent event){
		//El anular debe cambiar el estado del movimiento a "Abierto" y guardar la anulación 
		//en la tabla TESO_CONTROLFONDOFIJOANULA
		strObservacion = "";
		try {			
			
			controlFondosFijosAnular = (ControlFondosFijos)event.getComponent().getAttributes().get("item");
			controlFondosFijosAnular.setStrDescripcionPersona(obtenerDescripcionPersona(controlFondosFijosAnular.getIntPersPersonaResponsable()));
			tsFechaCierreAnular = controlFondosFijosAnular.getTsFechaCierre();
			
			if(controlFondosFijosAnular.getIntEstadoFondo().equals(Constante.PARAM_T_ESTADOFONDO_CERRADO)){
				listaControlFondosFijosAnular.clear();
				listaControlFondosFijosAnular.add(controlFondosFijosAnular);
				
				intOperacion = ANULAR_CIERRE;
				habilitarPanelInferiorAnular();
			}
		} catch (Exception e) {
			log.error("Error en anularCierre ---> "+e);
		}		
	}
	
	public void habilitarPanelInferiorAnular(){
		try{			
			datosValidadosAnulacion = Boolean.FALSE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferiorAnulacion = Boolean.TRUE;
//			deshabilitarNuevo = Boolean.FALSE;
			ocultarMensaje();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
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
	public ControlFondosFijos getControlFondosFijosFiltro() {
		return controlFondosFijosFiltro;
	}
	public void setControlFondosFijosFiltro(ControlFondosFijos controlFondosFijosFiltro) {
		this.controlFondosFijosFiltro = controlFondosFijosFiltro;
	}
	public Integer getIntTipoCierre() {
		return intTipoCierre;
	}
	public void setIntTipoCierre(Integer intTipoCierre) {
		this.intTipoCierre = intTipoCierre;
	}
	public Integer getIntAñoFiltro() {
		return intAñoFiltro;
	}
	public void setIntAñoFiltro(Integer intAñoFiltro) {
		this.intAñoFiltro = intAñoFiltro;
	}
	public Integer getIntEstadoFiltro() {
		return intEstadoFiltro;
	}
	public void setIntEstadoFiltro(Integer intEstadoFiltro) {
		this.intEstadoFiltro = intEstadoFiltro;
	}
	public Integer getIntNumeroFiltro() {
		return intNumeroFiltro;
	}
	public void setIntNumeroFiltro(Integer intNumeroFiltro) {
		this.intNumeroFiltro = intNumeroFiltro;
	}
	public boolean isDatosValidados() {
		return datosValidados;
	}
	public void setDatosValidados(boolean datosValidados) {
		this.datosValidados = datosValidados;
	}
	public ControlFondosFijos getControlFondosFijosCerrar() {
		return controlFondosFijosCerrar;
	}
	public void setControlFondosFijosCerrar(
			ControlFondosFijos controlFondosFijosCerrar) {
		this.controlFondosFijosCerrar = controlFondosFijosCerrar;
	}
	public List<Tabla> getListaSucursalBus() {
		return listaSucursalBus;
	}
	public Usuario getUsuario() {
		return usuarioSesion;
	}
	public void setUsuario(Usuario usuario) {
		this.usuarioSesion = usuario;
	}
	public String getStrMontoGirar() {
		return strMontoGirar;
	}
	public void setStrMontoGirar(String strMontoGirar) {
		this.strMontoGirar = strMontoGirar;
	}
	public String getStrObservacion() {
		return strObservacion;
	}
	public void setStrObservacion(String strObservacion) {
		this.strObservacion = strObservacion;
	}
	public List<ControlFondosFijos> getListaControlFondosFijos() {
		return listaControlFondosFijos;
	}
	public void setListaControlFondosFijos(List<ControlFondosFijos> listaControlFondosFijos) {
		this.listaControlFondosFijos = listaControlFondosFijos;
	}
	public List<ControlFondosFijos> getListaControlFondosFijosBus() {
		return listaControlFondosFijosBus;
	}
	public void setListaControlFondosFijosBus(List<ControlFondosFijos> listaControlFondosFijosBus) {
		this.listaControlFondosFijosBus = listaControlFondosFijosBus;
	}
	public List<ControlFondosFijos> getListaControlFondosFijosCerrar() {
		return listaControlFondosFijosCerrar;
	}
	public void setListaControlFondosFijosCerrar(List<ControlFondosFijos> listaControlFondosFijosCerrar) {
		this.listaControlFondosFijosCerrar = listaControlFondosFijosCerrar;
	}
	public List<IngresoDetalleInterfaz> getListaIngresoDetalleInterfaz() {
		return listaIngresoDetalleInterfaz;
	}
	public void setListaIngresoDetalleInterfaz(List<IngresoDetalleInterfaz> listaIngresoDetalleInterfaz) {
		this.listaIngresoDetalleInterfaz = listaIngresoDetalleInterfaz;
	}
	public Bancofondo getBancoFondoIngresar() {
		return bancoFondoIngresar;
	}
	public void setBancoFondoIngresar(Bancofondo bancoFondoIngresar) {
		this.bancoFondoIngresar = bancoFondoIngresar;
	}
	public boolean isPoseePermiso() {
		return poseePermiso;
	}
	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
	/* Agregado 05.12.2013 JCHAVEZ*/

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		CierreFondosController.log = log;
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

	public EgresoFacadeLocal getEgresoFacade() {
		return egresoFacade;
	}

	public void setEgresoFacade(EgresoFacadeLocal egresoFacade) {
		this.egresoFacade = egresoFacade;
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

	public IngresoFacadeLocal getIngresoFacade() {
		return ingresoFacade;
	}

	public void setIngresoFacade(IngresoFacadeLocal ingresoFacade) {
		this.ingresoFacade = ingresoFacade;
	}

	public Integer getIntOperacion() {
		return intOperacion;
	}

	public void setIntOperacion(Integer intOperacion) {
		this.intOperacion = intOperacion;
	}

	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
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

	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}

	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}

	public Boolean getBlnDisabledSucursal() {
		return blnDisabledSucursal;
	}

	public void setBlnDisabledSucursal(Boolean blnDisabledSucursal) {
		this.blnDisabledSucursal = blnDisabledSucursal;
	}

	public Integer getREGISTRAR_CIERRE() {
		return REGISTRAR_CIERRE;
	}

	public Integer getANULAR_CIERRE() {
		return ANULAR_CIERRE;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public void setListaSucursalBus(List<Tabla> listaSucursalBus) {
		this.listaSucursalBus = listaSucursalBus;
	}

	public Integer getIntAnioBusqueda() {
		return intAnioBusqueda;
	}

	public void setIntAnioBusqueda(Integer intAnioBusqueda) {
		this.intAnioBusqueda = intAnioBusqueda;
	}

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public Integer getIntTipoFondoFijo() {
		return intTipoFondoFijo;
	}

	public void setIntTipoFondoFijo(Integer intTipoFondoFijo) {
		this.intTipoFondoFijo = intTipoFondoFijo;
	}

	public Integer getIntParaTipoAnulaFondo() {
		return intParaTipoAnulaFondo;
	}

	public void setIntParaTipoAnulaFondo(Integer intParaTipoAnulaFondo) {
		this.intParaTipoAnulaFondo = intParaTipoAnulaFondo;
	}

	public ControlFondosFijos getControlFondosFijosAnular() {
		return controlFondosFijosAnular;
	}

	public void setControlFondosFijosAnular(
			ControlFondosFijos controlFondosFijosAnular) {
		this.controlFondosFijosAnular = controlFondosFijosAnular;
	}

	public List<Tabla> getListaTipoFondoFijo() {
		return listaTipoFondoFijo;
	}

	public void setListaTipoFondoFijo(List<Tabla> listaTipoFondoFijo) {
		this.listaTipoFondoFijo = listaTipoFondoFijo;
	}

	public Integer getIntEstadoFondo() {
		return intEstadoFondo;
	}

	public void setIntEstadoFondo(Integer intEstadoFondo) {
		this.intEstadoFondo = intEstadoFondo;
	}

	public CierreFondoFijoFacadeLocal getCierreFdoFijoFacade() {
		return cierreFdoFijoFacade;
	}

	public void setCierreFdoFijoFacade(
			CierreFondoFijoFacadeLocal cierreFdoFijoFacade) {
		this.cierreFdoFijoFacade = cierreFdoFijoFacade;
	}

	public Timestamp getTsFechaCierreAnular() {
		return tsFechaCierreAnular;
	}

	public void setTsFechaCierreAnular(Timestamp tsFechaCierreAnular) {
		this.tsFechaCierreAnular = tsFechaCierreAnular;
	}

	public List<ControlFondosFijos> getListaControlFondosFijosAnular() {
		return listaControlFondosFijosAnular;
	}

	public void setListaControlFondosFijosAnular(
			List<ControlFondosFijos> listaControlFondosFijosAnular) {
		this.listaControlFondosFijosAnular = listaControlFondosFijosAnular;
	}

	public boolean isDatosValidadosAnulacion() {
		return datosValidadosAnulacion;
	}

	public void setDatosValidadosAnulacion(boolean datosValidadosAnulacion) {
		this.datosValidadosAnulacion = datosValidadosAnulacion;
	}

	public boolean isMostrarPanelInferiorAnulacion() {
		return mostrarPanelInferiorAnulacion;
	}

	public void setMostrarPanelInferiorAnulacion(
			boolean mostrarPanelInferiorAnulacion) {
		this.mostrarPanelInferiorAnulacion = mostrarPanelInferiorAnulacion;
	}

	public List<ControlFondosFijos> getListaControlFondosFijosTemp() {
		return listaControlFondosFijosTemp;
	}

	public void setListaControlFondosFijosTemp(
			List<ControlFondosFijos> listaControlFondosFijosTemp) {
		this.listaControlFondosFijosTemp = listaControlFondosFijosTemp;
	}
}