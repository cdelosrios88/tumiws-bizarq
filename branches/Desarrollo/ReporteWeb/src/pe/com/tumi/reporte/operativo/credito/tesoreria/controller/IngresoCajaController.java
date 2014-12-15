package pe.com.tumi.reporte.operativo.credito.tesoreria.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.ServicioFacadeLocal;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;
import pe.com.tumi.reporte.operativo.tesoreria.facade.IngresoCajaFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class IngresoCajaController {
	protected 	static Logger 	log;
	//Inicio de Sesión
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	
	private	Integer	PERSONA_USUARIO;
	private Usuario usuario;
	private boolean poseePermiso;
	
	private List<Sucursal> listJuridicaSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	private List<Tabla> listaTipoCreditoEmpresa;
	private IngresoCaja ingresoCajaFiltro;
	
	//CONTROLLER
	private Integer intErrorFiltros;
	private List<IngresoCaja> listaIngresosCaja;
	
	IngresoCajaFacadeLocal ingresoCajaFacade;
	TablaFacadeRemote tablaFacade;
	
	public IngresoCajaController(){
		log = Logger.getLogger(this.getClass());
		//INICIO DE SESION
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		try {
			cargarValoresIniciales();
		} catch (Exception e) {
			log.error("Error en IngresoCajaController --> "+e);
		}
		intErrorFiltros = 0;
		inicio();
	}
	
	public void inicio() {
		//LLENANDO COMBOS DEL FORMULARIO
		
		listJuridicaSubsucursal = new ArrayList<Subsucursal>();
		getListSucursales();
	}
	
	public void buscarIngresos() {
		try {
			listaIngresosCaja = ingresoCajaFacade.getListaIngresosByTipoIngreso(ingresoCajaFiltro);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public String getLimpiarSaldoServicio(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_SALDOSERVICIOS);
		log.info("POSEE PERMISO" + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		
		return "";
	}
	
	public void cargarValoresIniciales(){
		try{
			tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			ingresoCajaFacade =(IngresoCajaFacadeLocal)EJBFactory.getLocal(IngresoCajaFacadeLocal.class);
			listaIngresosCaja = new ArrayList<IngresoCaja>();
			getListSucursales();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
	}
	
	public void printReport(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaTipoDocGeneral = null;
		String strDocGeneralTipoIngreso = "";
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaTipoDocGeneral = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL), Constante.PARAM_T_DOCUMENTOGENERAL_INGRESOCAJA);
			if(tablaTipoDocGeneral!=null){
				strDocGeneralTipoIngreso = tablaTipoDocGeneral.getStrDescripcion();
			}
			parametro.put("P_TIPOINGRESOCAJA", strDocGeneralTipoIngreso);
			
			sucursal = empresaFacade.getSucursalPorId(ingresoCajaFiltro.getIntIdSucursal());
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			
			tablaTipoDocGeneral = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_DOCUMENTOGENERAL), Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITOBANCO);
			if(tablaTipoDocGeneral!=null){
				strDocGeneralTipoIngreso = tablaTipoDocGeneral.getStrDescripcion();
			}
			parametro.put("P_TIPOINGRESODEPBANCO", strDocGeneralTipoIngreso);
			
			strNombreReporte = "saldoServicio";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaIngresosCaja), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirReporteCaptaciones ---> "+e);
		}
	}
	
	public void getListSucursales() {
		log.info("-------------------------------------Debugging AsociativoController.getListSucursales-------------------------------------");
		try {
			if(listJuridicaSucursal == null){
				EmpresaFacadeRemote facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listJuridicaSucursal = facade.getListaSucursalPorPkEmpresa(SESION_IDEMPRESA);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	//GETTERS Y SETTERS
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}
	
	public String getInicioPage() {		
		//limpiarFormulario();
		return "";
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

	public List<Sucursal> getListJuridicaSucursal() {
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

	public Integer getIntErrorFiltros() {
		return intErrorFiltros;
	}

	public void setIntErrorFiltros(Integer intErrorFiltros) {
		this.intErrorFiltros = intErrorFiltros;
	}

	public List<IngresoCaja> getListaIngresosCaja() {
		return listaIngresosCaja;
	}

	public void setListaIngresosCaja(List<IngresoCaja> listaIngresosCaja) {
		this.listaIngresosCaja = listaIngresosCaja;
	}

	public IngresoCaja getIngresoCajaFiltro() {
		return ingresoCajaFiltro;
	}

	public void setIngresoCajaFiltro(IngresoCaja ingresoCajaFiltro) {
		this.ingresoCajaFiltro = ingresoCajaFiltro;
	}

	public List<Tabla> getListaTipoCreditoEmpresa() {
		return listaTipoCreditoEmpresa;
	}

	public void setListaTipoCreditoEmpresa(List<Tabla> listaTipoCreditoEmpresa) {
		this.listaTipoCreditoEmpresa = listaTipoCreditoEmpresa;
	}

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
}
