package pe.com.tumi.seguridad.permiso.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.composite.AreaComp;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeLocal;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.Computadora;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraAcceso;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeLocal;

public class ComputadoraController {

	protected static Logger log = Logger.getLogger(ComputadoraController.class);	
	
	private TablaFacadeRemote tablaFacade;
	private PersonaFacadeRemote personaFacade;
	private EmpresaFacadeLocal empresaFacade;
	private PermisoFacadeLocal permisoFacade;
	
	private List listaComputadoras;
	private List listaAccesosSelec;
	private List listaEmpresas;
	private List listaSucursales;
	private List listaAreas;
	private List listaSucursalesNuevo;
	private List listaAreasNuevo;
	private List<ComputadoraAcceso> listaAccesoSelec;
	private List<ComputadoraAcceso> listaAccesoNoSelec;
	private List<Tabla> listaAccesoTabla;
	
	private Tabla accesoASelec;
	private Computadora computadoraFiltro;
	private Computadora computadoraNuevo;
	private Computadora registroSeleccionado;
	private Usuario usuario;
	
	private String mensajeOperacion;
	private boolean mostrarBtnEliminar;
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private boolean deshabilitarNuevo;
	private boolean mostrarPanelInferior;
	private boolean registrarNuevo;
	private boolean habilitarGrabar;
	
	public ComputadoraController(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
		if(usuario!=null){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL.");
		}		
	}
	
	private void cargarValoresIniciales(){
		try{
			usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			if(usuario!=null){
				habilitarGrabar = Boolean.TRUE;
				registrarNuevo = Boolean.TRUE;
				listaAccesoSelec = new ArrayList<ComputadoraAcceso>();
				listaAccesoNoSelec = new ArrayList<ComputadoraAcceso>();
				accesoASelec = new Tabla();
				computadoraFiltro = new Computadora();
				computadoraNuevo = new Computadora();
				listaComputadoras = new ArrayList<Computadora>();
				mostrarPanelInferior = Boolean.FALSE;
				deshabilitarNuevo = Boolean.FALSE;
				tablaFacade = (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
				personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
				empresaFacade = (EmpresaFacadeLocal)EJBFactory.getLocal(EmpresaFacadeLocal.class);
				permisoFacade = (PermisoFacadeLocal)EJBFactory.getLocal(PermisoFacadeLocal.class);
				listaEmpresas = personaFacade.getListaJuridicaDeEmpresa();
				listaAccesoTabla = tablaFacade.getListaTablaPorIdMaestro(Integer.valueOf(Constante.PARAM_T_TIPOSWTERCERO));
			}else{
				log.error("--Usuario obtenido es NULL.");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buscar(){
		try {
			//Si no se selecciono una empresa
			if(computadoraFiltro.getId().getIntPersEmpresaPk()==0){
				computadoraFiltro.getId().setIntPersEmpresaPk(null);
			}
			//Si no se selecciono una sucursal
			if(computadoraFiltro.getId().getIntIdSucursal()==0){
				computadoraFiltro.getId().setIntIdSucursal(null);
			}
			//Si no se selecciono un area
			if(computadoraFiltro.getId().getIntIdArea()==0){
				computadoraFiltro.getId().setIntIdArea(null);
			}
			//Si se eligio buscar todos los estados
			if(computadoraFiltro.getIntIdEstado().intValue()==-1){
				computadoraFiltro.setIntIdEstado(null);
			}
			log.info(computadoraFiltro);
			listaComputadoras = permisoFacade.buscarComputadora(computadoraFiltro);
			
		} catch (BusinessException e) {			
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void agregarAcceso(ActionEvent event){
		int idAccesoSelec = accesoASelec.getIntIdDetalle();
		//Encontramos el ComputadoraAcceso seleccionado
		ComputadoraAcceso compAccesoAux = null;
		for(ComputadoraAcceso compAcceso : listaAccesoNoSelec){
			if(compAcceso.getIntIdTipoAcceso().intValue() == idAccesoSelec){
				compAccesoAux = compAcceso;
			}
		}
		//Lo retiramos de la lista listaAccesoNoSelec
		listaAccesoNoSelec.remove(compAccesoAux);
		//Lo añadimos a la lista listaAccesoSelec
		listaAccesoSelec.add(compAccesoAux);
	}
	
	public void eliminarAcceso(ActionEvent event){
		//Encontramos cuales han sido seleccionados
		List<ComputadoraAcceso> listaAux = new ArrayList<ComputadoraAcceso>();
		for(ComputadoraAcceso compAcceso : listaAccesoSelec){
			if(compAcceso.getChecked()){
				listaAux.add(compAcceso);				
			}
		}
		//Los retiramos de la lista de listaAccesoSelec y los añadimos a listaAccesoNoSelec
		for(ComputadoraAcceso compAcceso : listaAux){
			listaAccesoSelec.remove(compAcceso);
			listaAccesoNoSelec.add(compAcceso);
		}
		listaAux.clear();
	}
	
	private void cargarListaAccesoNoSelec() throws NumberFormatException, BusinessException{
		listaAccesoNoSelec.clear();
		for(Tabla tabla : listaAccesoTabla){
			ComputadoraAcceso compAcceso = new ComputadoraAcceso();
			compAcceso.setIntIdTipoAcceso(tabla.getIntIdDetalle());
			compAcceso.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			compAcceso.setAcceso(tabla);
			listaAccesoNoSelec.add(compAcceso);
		}
	}
	
	public void habilitarPanelInferior(ActionEvent event){
		try{
			habilitarGrabar = Boolean.TRUE;
			registrarNuevo = Boolean.TRUE;
			mostrarPanelInferior = Boolean.TRUE;
			deshabilitarNuevo = Boolean.FALSE;
			computadoraNuevo = new Computadora();
			computadoraNuevo.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			accesoASelec = new Tabla();
			listaAccesoNoSelec.clear();
			listaAccesoSelec.clear();
			listaAreasNuevo = new ArrayList<AreaComp>();
			listaSucursalesNuevo = new ArrayList<Sucursal>();
			mostrarMensajeError = Boolean.FALSE;
			mostrarMensajeExito = Boolean.FALSE;
			cargarListaAccesoNoSelec();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deshabilitarPanelInferior(ActionEvent event){
		registrarNuevo = Boolean.FALSE; 
		mostrarPanelInferior = Boolean.FALSE;
		mostrarMensajeError = Boolean.FALSE;
		mostrarMensajeExito = Boolean.FALSE;
	}
	
	public void grabar(){
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {
			if(computadoraNuevo.getStrIdentificador()== null || !(computadoraNuevo.getStrIdentificador().length()>0)){
				mensaje = "Ocurrio un error durante el proceso. Debe ingresar un número de indentificación.";
				return;
			}
			if(!(listaAccesoSelec.size()>0)){
				mensaje = "Ocurrio un error durante el proceso. Debe elegir al menos un tipo de acceso.";
				return;
			}
			//Se actualizan los datos de los accesos
			int i = 0;
			while(i<listaAccesoSelec.size()){
				listaAccesoSelec.get(i).setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
				listaAccesoSelec.get(i).getId().setIntIdArea(computadoraNuevo.getId().getIntIdArea());
				listaAccesoSelec.get(i).getId().setIntIdSucursal(computadoraNuevo.getId().getIntIdSucursal());
				listaAccesoSelec.get(i).getId().setIntPersEmpresaPk(computadoraNuevo.getId().getIntPersEmpresaPk());
				i++;
			}
			if(registrarNuevo){
				//Si se trata de una nueva insercion
				computadoraNuevo.setDtFechaActivacion(new Date());
				//Procede a grabar
				permisoFacade.grabarComputadorayAccesos(computadoraNuevo, listaAccesoSelec);
				mensaje = "Se registró correctamente la computadora.";
				exito = Boolean.TRUE;
			}else{
				//Si se trata de una modificación
				permisoFacade.modificarComputadorayAccesos(computadoraNuevo, listaAccesoSelec);
				mensaje = "Se modificó correctamente la computadora.";
				exito = Boolean.TRUE;
			}
			deshabilitarNuevo = Boolean.TRUE;
			habilitarGrabar = Boolean.FALSE;
		} catch (BusinessException e) {
			mensaje = "Ocurrio un error durante el proceso.";
			e.printStackTrace();
		} catch (Exception e) {
			mensaje = "Ocurrio un error durante el proceso.";
			e.printStackTrace();
		} finally{
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

	public void seleccionarEmpresa(){		
		try {
			//Carga la lista de Sucursales dependiendo de la empresa seleccionada
			listaSucursales = empresaFacade.getListaSucursalPorPkEmpresa(computadoraFiltro.getId().getIntPersEmpresaPk());
			if(listaSucursales!=null && listaSucursales.size()>0){
				//Carga la lista de Areas con el primer item de la lista de sucursales				
				Area area = new Area();
				area.getId().setIntPersEmpresaPk(computadoraFiltro.getId().getIntPersEmpresaPk());
				area.getId().setIntIdSucursalPk(((Sucursal)(listaSucursales.get(0))).getId().getIntIdSucursal());
				listaAreas = empresaFacade.getListaArea(area);
			}						
		} catch (BusinessException e) {			
			e.printStackTrace();
		}
	}
	
	public void seleccionarSucursal(){		
		try {
			//Carga la lista de Sucursales dependiendo de la empresa y sucursal seleccionada
			Area area = new Area();
			area.getId().setIntPersEmpresaPk(computadoraFiltro.getId().getIntPersEmpresaPk());
			area.getId().setIntIdSucursalPk(computadoraFiltro.getId().getIntIdSucursal());
			listaAreas = empresaFacade.getListaArea(area);
		} catch (BusinessException e) {			
			e.printStackTrace();
		}
	}
	
	public void seleccionarEmpresaNuevo(){		
		try {
			log.info(computadoraNuevo);
			listaSucursalesNuevo = empresaFacade.getListaSucursalPorPkEmpresa(computadoraNuevo.getId().getIntPersEmpresaPk());
			if(listaSucursalesNuevo!=null && listaSucursalesNuevo.size()>0){
				//Carga la lista de Areas con el primer item de la lista de sucursales				
				Area area = new Area();
				area.getId().setIntPersEmpresaPk(computadoraNuevo.getId().getIntPersEmpresaPk());
				area.getId().setIntIdSucursalPk(((Sucursal)(listaSucursalesNuevo.get(0))).getId().getIntIdSucursal());
				listaAreasNuevo = empresaFacade.getListaArea(area);
			}						
		} catch (BusinessException e) {			
			e.printStackTrace();
		}
	}
	
	public void seleccionarSucursalNuevo(){		
		try {
			Area area = new Area();
			area.getId().setIntPersEmpresaPk(computadoraNuevo.getId().getIntPersEmpresaPk());
			area.getId().setIntIdSucursalPk(computadoraNuevo.getId().getIntIdSucursal());
			listaAreasNuevo = empresaFacade.getListaArea(area);
		} catch (BusinessException e) {			
			e.printStackTrace();
		}
	}
	
	public void seleccionarRegistro(ActionEvent event){
		try {
			registroSeleccionado = (Computadora)event.getComponent().getAttributes().get("item");
			if(registroSeleccionado.getIntIdEstado().intValue()==Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO){
				mostrarBtnEliminar= Boolean.FALSE;
				computadoraNuevo = registroSeleccionado;
				deshabilitarNuevo = Boolean.TRUE;
				registrarNuevo = Boolean.FALSE;
				mostrarPanelInferior = Boolean.TRUE;
				habilitarGrabar = Boolean.FALSE;
				listaAccesoSelec = permisoFacade.getListaComputadoraAccesoPorCabecera(computadoraNuevo);
				listaSucursalesNuevo = empresaFacade.getListaSucursalPorPkEmpresa(computadoraNuevo.getId().getIntPersEmpresaPk());		
				Area area = new Area();
				area.getId().setIntPersEmpresaPk(computadoraNuevo.getId().getIntPersEmpresaPk());
				area.getId().setIntIdSucursalPk(computadoraNuevo.getId().getIntIdSucursal());
				listaAreasNuevo = empresaFacade.getListaArea(area);
				listaAccesoSelec = permisoFacade.getListaComputadoraAccesoPorCabecera(computadoraNuevo);
				
			}else{
				mostrarBtnEliminar = Boolean.TRUE;
				habilitarGrabar = Boolean.TRUE;
			}
			log.info("reg selec:"+registroSeleccionado);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void eliminarRegistro(){
		boolean exito = Boolean.FALSE;
		String mensaje = null;
		try {			
			listaComputadoras.remove(registroSeleccionado);
			registroSeleccionado.setTsFechaEliminacion(new Timestamp(new Date().getTime()));
			registroSeleccionado = permisoFacade.eliminarComputadora(registroSeleccionado);
			if(registroSeleccionado.getIntIdEstado().intValue()==Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO){
				exito = Boolean.TRUE;
				mensaje = "El proceso de eliminación se realizo correctamente";			
			}else{
				mensaje = "Ocurrio un error durante el proceso de eliminación";			
			}
		} catch (BusinessException e) {
			mensaje = "Ocurrio un error durante el proceso de eliminación";
			e.printStackTrace();
		} catch(Exception e){
			mensaje = "Ocurrio un error durante el proceso de eliminación";
			e.printStackTrace();
		} finally{
			mostrarMensaje(exito, mensaje);
			deshabilitarPanelInferior(null);	
		}		
	}	
	
	public void modificarRegistro(){
		registrarNuevo = Boolean.FALSE;
		mostrarPanelInferior = Boolean.TRUE;
		deshabilitarNuevo = Boolean.FALSE;
		computadoraNuevo = registroSeleccionado;
		try {
			List<ComputadoraAcceso> listaAux = new ArrayList<ComputadoraAcceso>();
			//Cargamos las listas de combos a mostrar en la interfaz de acuerdo al registro seleccionado.
			listaSucursalesNuevo = empresaFacade.getListaSucursalPorPkEmpresa(computadoraNuevo.getId().getIntPersEmpresaPk());		
			Area area = new Area();
			area.getId().setIntPersEmpresaPk(computadoraNuevo.getId().getIntPersEmpresaPk());
			area.getId().setIntIdSucursalPk(computadoraNuevo.getId().getIntIdSucursal());
			listaAreasNuevo = empresaFacade.getListaArea(area);
			listaAccesoSelec = permisoFacade.getListaComputadoraAccesoPorCabecera(computadoraNuevo);
			//Cargamos listaAccesoSelec con los accesos registrados en la BD
			for(ComputadoraAcceso c : listaAccesoSelec){
				if(c.getIntIdEstado().intValue()!=Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO){
					listaAux.add(c);
				}
			}
			listaAccesoSelec = listaAux;
			//Quitamos de la listaAccesoNoSelec los elementos que se encuetran en listaAccesoSelec
			List<ComputadoraAcceso> listaAux2 = new ArrayList<ComputadoraAcceso>();			
			cargarListaAccesoNoSelec();
			boolean esIgual;
			
			for(ComputadoraAcceso compAccesoNoSel : listaAccesoNoSelec){
				esIgual = Boolean.FALSE;
				for(ComputadoraAcceso compAccesoSel : listaAccesoSelec){
					if(compAccesoNoSel.getIntIdTipoAcceso().intValue() == compAccesoSel.getIntIdTipoAcceso().intValue()){
						esIgual = Boolean.TRUE;
					}
				}
				if(!esIgual){
					listaAux2.add(compAccesoNoSel);
				}
			}
			listaAccesoNoSelec = listaAux2;
			//Cargamos la lista de listaAccesoSelec con sus accesos para que se muestren en el combo de "colSelAcceso"
			int i = 0;
			while(i<listaAccesoSelec.size()){
				for(Tabla tabla : listaAccesoTabla){
					if(listaAccesoSelec.get(i).getIntIdTipoAcceso().intValue() == tabla.getIntIdDetalle()){
						listaAccesoSelec.get(i).setAcceso(tabla);
					}
				}
				i++;
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	public List getListaComputadoras() {
		return listaComputadoras;
	}
	public void setListaComputadoras(List listaComputadoras) {
		this.listaComputadoras = listaComputadoras;
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
	public String getMensajeOperacion() {
		return mensajeOperacion;
	}
	public void setMensajeOperacion(String mensajeOperacion) {
		this.mensajeOperacion = mensajeOperacion;
	}
	public boolean isDeshabilitarNuevo() {
		return deshabilitarNuevo;
	}
	public void setDeshabilitarNuevo(boolean deshabilitarNuevo) {
		this.deshabilitarNuevo = deshabilitarNuevo;
	}
	public List getListaAccesosSelec() {
		return listaAccesosSelec;
	}
	public void setListaAccesosSelec(List listaAccesosSelec) {
		this.listaAccesosSelec = listaAccesosSelec;
	}
	public boolean isMostrarPanelInferior() {
		return mostrarPanelInferior;
	}
	public void setMostrarPanelInferior(boolean mostrarPanelInferior) {
		this.mostrarPanelInferior = mostrarPanelInferior;
	}
	public List getListaEmpresas() {
		return listaEmpresas;
	}
	public void setListaEmpresas(List listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
	}
	public List getListaSucursales() {
		return listaSucursales;
	}
	public void setListaSucursales(List listaSucursales) {
		this.listaSucursales = listaSucursales;
	}
	public List getListaAreas() {
		return listaAreas;
	}
	public void setListaAreas(List listaAreas) {
		this.listaAreas = listaAreas;
	}
	public Computadora getComputadoraFiltro() {
		return computadoraFiltro;
	}
	public void setComputadoraFiltro(Computadora computadoraFiltro) {
		this.computadoraFiltro = computadoraFiltro;
	}
	public Computadora getComputadoraNuevo() {
		return computadoraNuevo;
	}
	public void setComputadoraNuevo(Computadora computadoraNuevo) {
		this.computadoraNuevo = computadoraNuevo;
	}
	public List getListaSucursalesNuevo() {
		return listaSucursalesNuevo;
	}
	public void setListaSucursalesNuevo(List listaSucursalesNuevo) {
		this.listaSucursalesNuevo = listaSucursalesNuevo;
	}
	public List getListaAreasNuevo() {
		return listaAreasNuevo;
	}
	public void setListaAreasNuevo(List listaAreasNuevo) {
		this.listaAreasNuevo = listaAreasNuevo;
	}
	public Tabla getAccesoASelec() {
		return accesoASelec;
	}
	public void setAccesoASelec(Tabla accesoASelec) {
		this.accesoASelec = accesoASelec;
	}
	public List<ComputadoraAcceso> getListaAccesoSelec() {
		return listaAccesoSelec;
	}
	public void setListaAccesoSelec(List<ComputadoraAcceso> listaAccesoSelec) {
		this.listaAccesoSelec = listaAccesoSelec;
	}
	public List<ComputadoraAcceso> getListaAccesoNoSelec() {
		return listaAccesoNoSelec;
	}
	public void setListaAccesoNoSelec(List<ComputadoraAcceso> listaAccesoNoSelec) {
		this.listaAccesoNoSelec = listaAccesoNoSelec;
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

}