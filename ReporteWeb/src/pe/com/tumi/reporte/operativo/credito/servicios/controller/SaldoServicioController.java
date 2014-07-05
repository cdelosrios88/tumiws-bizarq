package pe.com.tumi.reporte.operativo.credito.servicios.controller;

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
import pe.com.tumi.parametro.tabla.facade.TablaFacade;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Servicio;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.ServicioFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class SaldoServicioController {
	protected 	static Logger 	log;
	//Inicio de Sesión
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	
	private	Integer	EMPRESA_USUARIO;
	private	Integer	PERSONA_USUARIO;
	private Usuario usuario;
	private boolean poseePermiso;
	
	private List<Sucursal> listJuridicaSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	private List<Tabla> listaTipoCreditoEmpresa;
	private Integer intIdSucursal;
	private Integer intIdSubSucursal;
	private Integer intTipoServicio;
	private Integer intTipoCreditoEmpresa;
	
	//CONTROLLER
	private Integer intErrorFiltros;
	private List<Servicio> listaSaldoServicios;
	
	ServicioFacadeLocal servicioFacade;
	TablaFacadeRemote tablaFacade;
	
	public SaldoServicioController(){
		log = Logger.getLogger(this.getClass());
		//INICIO DE SESION
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		try {
			servicioFacade =(ServicioFacadeLocal)EJBFactory.getLocal(ServicioFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			//listaDescripcionMes = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MES_CALENDARIO));*/
		} catch (Exception e) {
			log.error("Error en AsociativoController --> "+e);
		}
		intErrorFiltros = 0;
		inicio();
	}
	
	public void inicio() {
		//LLENANDO COMBOS DEL FORMULARIO
		
		listJuridicaSubsucursal = new ArrayList<Subsucursal>();
		getListSucursales();		
	}
	
	public void getListSubSucursal() {
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = Integer.valueOf(getRequestParameter("pCboSucursal"));
			
			if(intIdSucursal!=0){
				facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursal);
				log.info("listaSubsucursal.size: "+listaSubsucursal.size());
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListJuridicaSubsucursal(listaSubsucursal);
	}
	
	public void consultarSaldos() {
		
		if(intIdSucursal==0)intIdSucursal=null;
		if(intIdSubSucursal==0)intIdSubSucursal=null;
		if(intTipoServicio==0)intTipoServicio=null;
		if(intTipoCreditoEmpresa==0)intTipoCreditoEmpresa=null;
		
		try {
			listaSaldoServicios = servicioFacade.getListaSaldoServicios(intIdSucursal, intIdSubSucursal, intTipoServicio, intTipoCreditoEmpresa);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	public void reloadCboTipoCreditoEmpresa(){
		Integer intTipoServicio = null;
		try {
			intTipoServicio = Integer.valueOf(getRequestParameter("pCboTipoServicio"));
			TablaFacadeRemote remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		    listaTipoCreditoEmpresa = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), intTipoServicio);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	public void limpiarTabCaptaciones() {
		
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
			
			listaSaldoServicios = new ArrayList<Servicio>();
			getListSucursales();
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}
	
	public void imprimirSaldoServicio(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaTipoServicio = null;
		String strTipoServicio = "";
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaTipoServicio = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPO_CREDITO), intTipoServicio);
			
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			if(tablaTipoServicio!=null){
				strTipoServicio = tablaTipoServicio.getStrDescripcion();
			}
			
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_TIPOSERVICIO", strTipoServicio);
			parametro.put("P_TIPOCREDITOEMPRESA", "");
			
			strNombreReporte = "saldoServicio";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaSaldoServicios), Constante.PARAM_T_TIPOREPORTE_PDF);
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

	public List<Servicio> getListaSaldoServicios() {
		return listaSaldoServicios;
	}

	public void setListaSaldoServicios(List<Servicio> listaSaldoServicios) {
		this.listaSaldoServicios = listaSaldoServicios;
	}

	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}

	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}

	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}

	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}

	public Integer getIntTipoServicio() {
		return intTipoServicio;
	}

	public void setIntTipoServicio(Integer intTipoServicio) {
		this.intTipoServicio = intTipoServicio;
	}

	public Integer getIntTipoCreditoEmpresa() {
		return intTipoCreditoEmpresa;
	}

	public void setIntTipoCreditoEmpresa(Integer intTipoCreditoEmpresa) {
		this.intTipoCreditoEmpresa = intTipoCreditoEmpresa;
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
