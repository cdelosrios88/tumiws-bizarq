package pe.com.tumi.reporte.cobranza.centralriesgo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.CommonUtils;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.Fenacrep;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.facade.CarteraCreditoFacadeLocal;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class FenacrepController {
	protected 	static Logger 	log;
	
	private Usuario usuario;
	private	Integer	EMPRESA_USUARIO;
	private	Integer	PERSONA_USUARIO;
	private boolean poseePermiso;
	
	private Integer intParaMes;
	private Integer intParaAnio;
	private List<SelectItem> listYears;
	
	//Facades
	private TablaFacadeRemote tablaFacade;
	CarteraCreditoFacadeLocal carteraCreditoFacade;
	
	public FenacrepController() {
		log = Logger.getLogger(this.getClass());
		
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			carteraCreditoFacade = (CarteraCreditoFacadeLocal)EJBFactory.getLocal(CarteraCreditoFacadeLocal.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		inicio();
	}
	
	public void inicio() {
		//LLENANDO COMBOS DEL FORMULARIO
		getListAnios();
	}
	
	public List<Fenacrep> consultarFenacrep() {
		Integer intPeriodo = null;
		List<Fenacrep> lstFenacrep = null;
		try {
			intPeriodo = CommonUtils.concatPeriodo(intParaAnio, intParaMes);
			if(intPeriodo!=null){
				lstFenacrep = carteraCreditoFacade.getListaFenacrep(intPeriodo);
			}
		} catch (Exception e) {
			log.error("Error en consultarSocioDiferencia ---> "+e);
		}
		
		return lstFenacrep;
	}
	
	public void imprimirReporteFenacrep(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Tabla tablaMes = null;
		List<Fenacrep> lstFenacrep = null;
		
		String strMes = "";
		try {
			lstFenacrep = consultarFenacrep();
			if(lstFenacrep!=null){
				log.info("lstFenacrep.size(): " + lstFenacrep.size());
				tablaMes = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MES_CALENDARIO), intParaMes);
				
				if(tablaMes!=null){
					strMes = tablaMes.getStrDescripcion();
				}
				parametro.put("P_ANIO", intParaAnio);
				parametro.put("P_MES", strMes);
				
				strNombreReporte = "FENACREP";
				UtilManagerReport.generateReport(strNombreReporte, parametro, 
						new ArrayList<Object>(lstFenacrep), Constante.PARAM_T_TIPOREPORTE_EXCEL);
			}
		} catch (Exception e) {
			log.error("Error en imprimirReporteFenacrep ---> "+ e.getMessage() ,e);
		}
	}
	
	public String getLimpiarFenacrep(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_FENACREP);
		log.info("POSEE PERMISO: " + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			inicio();
			limpiarTabFenacrep();
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
	
	public void limpiarTabFenacrep(){
		intParaAnio = null;
		intParaMes = null;
	}
	
	public void getListAnios() {
		listYears = CommonUtils.getListAnios();
	}

	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		return sesion.getAttribute(beanName);
	}

	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}
	
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	public Integer getIntParaMes() {
		return intParaMes;
	}

	public void setIntParaMes(Integer intParaMes) {
		this.intParaMes = intParaMes;
	}

	public Integer getIntParaAnio() {
		return intParaAnio;
	}

	public void setIntParaAnio(Integer intParaAnio) {
		this.intParaAnio = intParaAnio;
	}

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}
}