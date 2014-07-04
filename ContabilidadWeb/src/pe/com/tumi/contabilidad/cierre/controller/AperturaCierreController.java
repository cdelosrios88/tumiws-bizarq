package pe.com.tumi.contabilidad.cierre.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


import pe.com.tumi.common.util.Constante;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.facade.CierreFacadeLocal;
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.login.facade.LoginFacadeRemote;

public class AperturaCierreController {

	protected static Logger log = Logger.getLogger(AperturaCierreController.class);	
	
	PlanCuentaFacadeLocal planCuentaFacade;
	CierreFacadeLocal cierreFacade;
	LoginFacadeRemote loginFacade;
	
	private List listaAnios;
	private List listaCuentaCierre;
	private List listaPlanCuenta;
	private List listaCuentaCierreDetalle;
	
	private CuentaCierre cuentaCierreFiltro;
	private CuentaCierre cuentaCierreNuevo;
	private PlanCuenta planCuentaFiltro;
	private CuentaCierre registroSeleccionado;
	
	private Usuario usuario;
	private String 	mensajeOperacion;
	private String strCuentaContableFiltro;
	private Integer intFiltroPlanCuenta;
	private final int cantidadAñosLista = 4;
	
	//popup plan de cuenta
	private Integer intTipoBusquedaPlanCuenta;
	private Integer intTipoAgregarPlanCuenta;
	
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	private boolean habilitarCerrarOperaciones;
	private boolean habilitarAperturarOperaciones;
	private boolean habilitarTextoPlanCuenta;
	
	
	
	public AperturaCierreController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}
	}	
	
	
	public void cargarValoresIniciales(){
		try{
			cuentaCierreFiltro = new CuentaCierre();
			cuentaCierreNuevo  = new CuentaCierre();
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaCuentaCierre = new ArrayList<CuentaCierre>();
			listaPlanCuenta = new ArrayList<PlanCuenta>();
			listaCuentaCierreDetalle = new ArrayList<CuentaCierreDetalle>();
			strCuentaContableFiltro = "";
			
			planCuentaFacade = (PlanCuentaFacadeLocal) EJBFactory.getLocal(PlanCuentaFacadeLocal.class);
			cierreFacade = (CierreFacadeLocal) EJBFactory.getLocal(CierreFacadeLocal.class);
			loginFacade = (LoginFacadeRemote) EJBFactory.getRemote(LoginFacadeRemote.class);
			
			cargarListaAnios();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	private void cargarListaAnios(){
		listaAnios = new ArrayList<Tabla>();
		Calendar cal=Calendar.getInstance();
		Tabla tabla = null;
		for(int i=0;i<cantidadAñosLista;i++){
			tabla = new Tabla();
			int year = cal.get(Calendar.YEAR);
			cal.add(Calendar.YEAR, 1);
			tabla.setIntIdDetalle(year);
			tabla.setStrDescripcion(""+year);
			listaAnios.add(tabla);
		}		
	}
	
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			cuentaCierreNuevo = new CuentaCierre();
			cuentaCierreNuevo.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			listaCuentaCierreDetalle.clear();
			habilitarGrabar = Boolean.TRUE;
			
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;			
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
		habilitarGrabar = Boolean.FALSE;
	}
	
	
	public void buscar(){
		try{
						
			if(strCuentaContableFiltro!=null && strCuentaContableFiltro.isEmpty()){
				strCuentaContableFiltro = null;
			}
			
			if(intFiltroPlanCuenta.equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){
				cuentaCierreFiltro.setStrDescripcion(strCuentaContableFiltro);
				cuentaCierreFiltro.setStrContNumeroCuenta(null);
			}else if(intFiltroPlanCuenta.equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){
				cuentaCierreFiltro.setStrDescripcion(null);
				cuentaCierreFiltro.setStrContNumeroCuenta(strCuentaContableFiltro);
			}
			//cuentaCierreFiltro.setStrDescripcion(null);
			//cuentaCierreFiltro.setStrContNumeroCuenta(null);
			if(cuentaCierreFiltro.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
				cuentaCierreFiltro.setIntParaEstado(null);
			}
			log.info(cuentaCierreFiltro);
			
			listaCuentaCierre = cierreFacade.getListaCuentaCierrePorBusqueda(cuentaCierreFiltro);
			for(Object o : listaCuentaCierre){
				CuentaCierre cuentaCierre = (CuentaCierre)o;
				cuentaCierre.setPersona((loginFacade.getUsuarioPersonaPorIdPersona(cuentaCierre.getIntPersPersonaUsuario())).getPersona());
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try{
			registroSeleccionado = (CuentaCierre)event.getComponent().getAttributes().get("item");
			if(registroSeleccionado.getIntParaEstado().equals(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO)){
				cargarRegistro();
				mostrarBtnEliminar= Boolean.FALSE;
				habilitarGrabar = Boolean.FALSE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;
				mostrarMensajeExito = Boolean.FALSE;
				mostrarMensajeError = Boolean.FALSE;
				deshabilitarNuevo = Boolean.TRUE;
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
			}
			log.info("reg selec:"+registroSeleccionado);						
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarRegistro() throws BusinessException{
		log.info("cargarRegistro");
		cuentaCierreNuevo = registroSeleccionado;
		List<CuentaCierreDetalle> lista = cierreFacade.getListaCuentaCierreDetallePorCuentaCierre(cuentaCierreNuevo);
		PlanCuenta planCuenta = null;
		PlanCuentaId planCuentaId = new PlanCuentaId();
		for(CuentaCierreDetalle cuentaCierreDetalle : lista){
			planCuentaId.setIntEmpresaCuentaPk(cuentaCierreDetalle.getIntPersEmpresaCuenta());
			planCuentaId.setIntPeriodoCuenta(cuentaCierreDetalle.getIntContPeriodoCuenta());
			planCuentaId.setStrNumeroCuenta(cuentaCierreDetalle.getStrContNumeroCuenta());
			planCuenta = planCuentaFacade.getPlanCuentaPorPk(planCuentaId);
			cuentaCierreDetalle.setStrDescripcion(planCuenta.getStrDescripcion());
			log.info(cuentaCierreDetalle);
		}
		cuentaCierreNuevo.setListaCuentaCierreDetalle(lista);
		listaCuentaCierreDetalle = lista;
	}
	
	public void buscarPlanCuenta(){
		try{
			if(planCuentaFiltro.getId().getIntPeriodoCuenta().equals(Constante.PARAM_T_ESTADOUNIVERSAL_TODOS)){
				planCuentaFiltro.getId().setIntPeriodoCuenta(null);
			}
			if(intTipoBusquedaPlanCuenta==0){
				
			}else if(intTipoBusquedaPlanCuenta.equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){
				log.info("Tipo:Descripcion");
				planCuentaFiltro.getId().setStrNumeroCuenta(null);
				planCuentaFiltro.setStrDescripcion(planCuentaFiltro.getStrComentario());
				listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);
			}else if(intTipoBusquedaPlanCuenta.equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){
				log.info("Tipo:NumeroCuenta");
				planCuentaFiltro.getId().setStrNumeroCuenta(planCuentaFiltro.getStrComentario());
				planCuentaFiltro.setStrDescripcion(null);
				listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);
			}
			for(Object o : listaPlanCuenta){
				log.info((PlanCuenta)o);
			}
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	public void grabar(){
		log.info("--grabar");
		Boolean exito = Boolean.FALSE;
		String mensaje = null;
		try{
			
			if(cuentaCierreNuevo.getStrDescripcion()==null || cuentaCierreNuevo.getStrDescripcion().isEmpty()){
				mensaje = "Hubo un error durante el registro del cierre de cuenta. Debe ingresar la Descripción.";
				return;
			}
			if(cuentaCierreNuevo.getStrContNumeroCuenta()==null || cuentaCierreNuevo.getStrContNumeroCuenta().isEmpty()){
				mensaje = "Hubo un error durante el registro del cierre de cuenta. Debe seleccionar al menos una cuenta en Agregar Cuenta.";
				return;
			}			
			if(listaCuentaCierreDetalle.isEmpty()){
				mensaje = "Hubo un error durante el registro del cierre de cuenta. Debe seleccionar al menos una cuenta para Detalle de Operación.";
				return;
			}
			cuentaCierreNuevo.setListaCuentaCierreDetalle(listaCuentaCierreDetalle);
			cuentaCierreNuevo.getId().setIntPersEmpresaCierre(usuario.getPerfil().getId().getIntPersEmpresaPk());
			cuentaCierreNuevo.setIntPersEmpresaUsuario(usuario.getPerfil().getId().getIntPersEmpresaPk());
			cuentaCierreNuevo.setIntPersPersonaUsuario(usuario.getIntPersPersonaPk());
			
			if(registrarNuevo){
				log.info("--registrar");
				cuentaCierreNuevo.setTsFechaRegistro(new Timestamp(new Date().getTime()));
				cuentaCierreNuevo = cierreFacade.grabarCuentaCierre(cuentaCierreNuevo);
				mensaje = "Se registró correctamente el libro mayor.";
			}else{
				log.info("--modificar");
				cuentaCierreNuevo = cierreFacade.modificarCuentaCierre(cuentaCierreNuevo);
				mensaje = "Se modificó correctamente el libro mayor.";				
			}
			buscar();
			exito = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
			deshabilitarNuevo = Boolean.TRUE;
		}catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de registro del cierre de cuenta.";
			log.error(e.getMessage(),e);
		}finally{
			mostrarMensaje(exito,mensaje);
		}
	}
	
	
	private void mostrarMensaje(boolean exito, String mensaje){
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
	
	
	public void abrirPopUpPlanCuenta(ActionEvent event){
		try{
			intTipoBusquedaPlanCuenta = 0;
			planCuentaFiltro = new PlanCuenta();
			planCuentaFiltro.setId(new PlanCuentaId());
			listaPlanCuenta = planCuentaFacade.getListaPlanCuentaBusqueda(planCuentaFiltro);
			habilitarTextoPlanCuenta = Boolean.FALSE;
			planCuentaFiltro.getId().setIntPeriodoCuenta(-1);
			intTipoAgregarPlanCuenta = (Integer)event.getComponent().getAttributes().get(Constante.TIPOAGREGARPLANCUENTA);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void seleccionarTipoBusquedaPlanCuenta(){
		if(intTipoBusquedaPlanCuenta!=0){
			habilitarTextoPlanCuenta = Boolean.TRUE;
		}else{
			habilitarTextoPlanCuenta = Boolean.FALSE;
		}
	}
	
	public boolean validarDuplicidadPlanCuenta(PlanCuenta planCuenta){
		boolean noExisteDuplicidad = Boolean.FALSE;
		if(	cuentaCierreNuevo.getStrContNumeroCuenta()!=null && 
			!cuentaCierreNuevo.getStrContNumeroCuenta().isEmpty() &&
			cuentaCierreNuevo.getStrContNumeroCuenta().equalsIgnoreCase(planCuenta.getId().getStrNumeroCuenta()) &&
			cuentaCierreNuevo.getIntContPeriodoCuenta()!=null &&
			cuentaCierreNuevo.getIntContPeriodoCuenta().equals(planCuenta.getId().getIntPeriodoCuenta()) &&
			cuentaCierreNuevo.getIntPersEmpresaCuenta()!=null &&
			cuentaCierreNuevo.getIntPersEmpresaCuenta().equals(planCuenta.getId().getIntEmpresaCuentaPk())){
			return noExisteDuplicidad;//false
		}
		CuentaCierreDetalle ccd = null;
		for(Object o : listaCuentaCierreDetalle){
			ccd = (CuentaCierreDetalle)o;
			if( ccd.getStrContNumeroCuenta().equals(planCuenta.getId().getStrNumeroCuenta()) &&
				ccd.getIntContPeriodoCuenta().equals(planCuenta.getId().getIntPeriodoCuenta()) &&
				ccd.getIntPersEmpresaCuenta().equals(planCuenta.getId().getIntEmpresaCuentaPk())){
				return noExisteDuplicidad;//false
			}
		}
		noExisteDuplicidad = Boolean.TRUE;
		return noExisteDuplicidad;//true
	}
	
	public void seleccionarPlanCuenta(ActionEvent event){
		try{
			PlanCuenta planCuenta = (PlanCuenta)event.getComponent().getAttributes().get("item");
			if(validarDuplicidadPlanCuenta(planCuenta)){
				if(intTipoAgregarPlanCuenta.equals(Constante.TIPOAGREGARPLANCUENTA_CIERRECUENTA)){
					cuentaCierreNuevo.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
					cuentaCierreNuevo.setIntContPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
					cuentaCierreNuevo.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());	
					
				}else if(intTipoAgregarPlanCuenta.equals(Constante.TIPOAGREGARPLANCUENTA_CIERRECUENTADETALLE)){
					CuentaCierreDetalle cuentaCierreDetalle = new CuentaCierreDetalle();
					cuentaCierreDetalle.setIntPersEmpresaCuenta(planCuenta.getId().getIntEmpresaCuentaPk());
					cuentaCierreDetalle.setIntContPeriodoCuenta(planCuenta.getId().getIntPeriodoCuenta());
					cuentaCierreDetalle.setStrContNumeroCuenta(planCuenta.getId().getStrNumeroCuenta());
					cuentaCierreDetalle.setStrDescripcion(planCuenta.getStrDescripcion());
					listaCuentaCierreDetalle.add(cuentaCierreDetalle);
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void eliminarCuentaCierreDetalle(ActionEvent event){
		try{
			CuentaCierreDetalle cuentaCierreDetalle = (CuentaCierreDetalle)event.getComponent().getAttributes().get("item");
			listaCuentaCierreDetalle.remove(cuentaCierreDetalle);
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	public void modificarRegistro(){
		try{
			cargarRegistro();
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.FALSE;
			mostrarPanelInferior = Boolean.TRUE;
			mostrarMensajeExito = Boolean.FALSE;
			mostrarMensajeError = Boolean.FALSE;
			deshabilitarNuevo = Boolean.FALSE;
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	public void eliminarRegistro(){
		try{
			listaCuentaCierre.remove(registroSeleccionado);
			registroSeleccionado.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			registroSeleccionado.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
			cierreFacade.eliminarCuentaCierre(registroSeleccionado);
			
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
	}
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
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
	public boolean isHabilitarCerrarOperaciones() {
		return habilitarCerrarOperaciones;
	}
	public void setHabilitarCerrarOperaciones(boolean habilitarCerrarOperaciones) {
		this.habilitarCerrarOperaciones = habilitarCerrarOperaciones;
	}
	public boolean isHabilitarAperturarOperaciones() {
		return habilitarAperturarOperaciones;
	}
	public void setHabilitarAperturarOperaciones(
			boolean habilitarAperturarOperaciones) {
		this.habilitarAperturarOperaciones = habilitarAperturarOperaciones;
	}
	public List getListaAnios() {
		return listaAnios;
	}
	public void setListaAnios(List listaAnios) {
		this.listaAnios = listaAnios;
	}
	public CuentaCierre getCuentaCierreFiltro() {
		return cuentaCierreFiltro;
	}
	public void setCuentaCierreFiltro(CuentaCierre cuentaCierreFiltro) {
		this.cuentaCierreFiltro = cuentaCierreFiltro;
	}
	public List getListaCuentaCierre() {
		return listaCuentaCierre;
	}
	public void setListaCuentaCierre(List listaCuentaCierre) {
		this.listaCuentaCierre = listaCuentaCierre;
	}
	public Integer getIntFiltroPlanCuenta() {
		return intFiltroPlanCuenta;
	}
	public void setIntFiltroPlanCuenta(Integer intFiltroPlanCuenta) {
		this.intFiltroPlanCuenta = intFiltroPlanCuenta;
	}
	public CuentaCierre getCuentaCierreNuevo() {
		return cuentaCierreNuevo;
	}
	public void setCuentaCierreNuevo(CuentaCierre cuentaCierreNuevo) {
		this.cuentaCierreNuevo = cuentaCierreNuevo;
	}
	public PlanCuenta getPlanCuentaFiltro() {
		return planCuentaFiltro;
	}
	public void setPlanCuentaFiltro(PlanCuenta planCuentaFiltro) {
		this.planCuentaFiltro = planCuentaFiltro;
	}
	public List getListaPlanCuenta() {
		return listaPlanCuenta;
	}
	public void setListaPlanCuenta(List listaPlanCuenta) {
		this.listaPlanCuenta = listaPlanCuenta;
	}
	public boolean isHabilitarTextoPlanCuenta() {
		return habilitarTextoPlanCuenta;
	}
	public void setHabilitarTextoPlanCuenta(boolean habilitarTextoPlanCuenta) {
		this.habilitarTextoPlanCuenta = habilitarTextoPlanCuenta;
	}
	public List getListaCuentaCierreDetalle() {
		return listaCuentaCierreDetalle;
	}
	public void setListaCuentaCierreDetalle(List listaCuentaCierreDetalle) {
		this.listaCuentaCierreDetalle = listaCuentaCierreDetalle;
	}
	public Integer getIntTipoAgregarPlanCuenta() {
		return intTipoAgregarPlanCuenta;
	}
	public void setIntTipoAgregarPlanCuenta(Integer intTipoAgregarPlanCuenta) {
		this.intTipoAgregarPlanCuenta = intTipoAgregarPlanCuenta;
	}
	public Integer getIntTipoBusquedaPlanCuenta() {
		return intTipoBusquedaPlanCuenta;
	}
	public void setIntTipoBusquedaPlanCuenta(Integer intTipoBusquedaPlanCuenta) {
		this.intTipoBusquedaPlanCuenta = intTipoBusquedaPlanCuenta;
	}
	public String getStrCuentaContableFiltro() {
		return strCuentaContableFiltro;
	}
	public void setStrCuentaContableFiltro(String strCuentaContableFiltro) {
		this.strCuentaContableFiltro = strCuentaContableFiltro;
	}
	public CuentaCierre getRegistroSeleccionado() {
		return registroSeleccionado;
	}
	public void setRegistroSeleccionado(CuentaCierre registroSeleccionado) {
		this.registroSeleccionado = registroSeleccionado;
	}
}