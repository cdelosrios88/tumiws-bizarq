package pe.com.tumi.reporte.cobranza.planillaDescuento.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
import pe.com.tumi.mensaje.MessageController;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.PlanillaDescuento;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.PlanillaDescuentoFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class PlanillaDescuentoController {
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
	
	//CONTROLLER
	private TablaFacadeRemote tablaFacade;
	private PlanillaDescuentoFacadeLocal planillaDescuentoFacade;
	private List<Tabla> listaDescripcionMes;
	private List<Tabla> listaDescripcionTipoSocio;
	private List<Tabla> listaDescripcionModalidad;
	
	//Filtros de Búsqueda
	private Integer intErrorFiltros;
	
	private Integer intIdSucursal;
	private List<Sucursal> listJuridicaSucursal;
	private Integer intIdSubSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	private Integer intTipoSocio;
	private Integer intModalidad;
	private Integer intTipoSucursal;
	private Integer intAnioBusqueda;
	private Integer intMes;
	
	//Sumatorias Totales
	private BigDecimal bdMontoEnviadoTotal;
	private Integer intNroSociosEnviadoTotal;
	private BigDecimal bdMontoEfectuadoTotal;
	private Integer intNroSociosEfectuadoTotal;
	private BigDecimal bdMontoDiferenciaTotal;
	private Integer intNroSociosDiferenciaTotal;
	
	private List<SelectItem> listYears;
	private List<PlanillaDescuento> listaPlanillaDescuentoDesagregado;
	
	public PlanillaDescuentoController(){
		log = Logger.getLogger(this.getClass());
		//INICIO DE SESION
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		try {
			planillaDescuentoFacade =(PlanillaDescuentoFacadeLocal)EJBFactory.getLocal(PlanillaDescuentoFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaDescripcionMes = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MES_CALENDARIO));
			listaDescripcionTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			listaDescripcionModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
		} catch (Exception e) {
			log.error("Error en AsociativoController --> "+e);
		}
		intErrorFiltros = 0;
		inicio();
	}
	
	public void inicio() {
		//LLENANDO COMBOS DEL FORMULARIO
		listJuridicaSubsucursal = new ArrayList<Subsucursal>();
		listaPlanillaDescuentoDesagregado = new ArrayList<PlanillaDescuento>();
		
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		intTipoSocio = 0;
		intModalidad = 0;
		intTipoSucursal = 0;
		intAnioBusqueda = 0;
		intMes = 0;
		bdMontoEnviadoTotal = null;
		intNroSociosEnviadoTotal = null;
		bdMontoEfectuadoTotal = null;
		intNroSociosEfectuadoTotal = null;
		bdMontoDiferenciaTotal = null;
		intNroSociosDiferenciaTotal = null;
		
		getListAnios();
		getListSucursales();
	}
	
	public void getListSubSucursal() {
		log.info("-------------------------------------Debugging AsociativoController.getListSubSucursal-------------------------------------");
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
	
	public void consultarPlanillaDescuento() {
		Integer intPeriodo = null;
		try {
			if (!isValidFiltros()) {
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("Los filtros de Sucursal, Sub Sucursal, Año y mes no pueden estar vacíos. " +
						"Verifique.");
				return;
			}
			intPeriodo = concatPeriodo(intAnioBusqueda, intMes);
			/*if(intIdSucursal==0)intIdSucursal=null;
			if(intIdSubSucursal==0)intIdSubSucursal=null;*/
			if(intTipoSocio==0)intTipoSocio=null;
			if(intModalidad==0)intModalidad=null;
			if(intTipoSucursal==0)intTipoSucursal=null;
			
			listaPlanillaDescuentoDesagregado = planillaDescuentoFacade.getListaPlanillaDescuento(intIdSucursal, intIdSubSucursal, intTipoSocio, intModalidad,intTipoSucursal,intPeriodo);
			
			if(listaPlanillaDescuentoDesagregado!=null && !listaPlanillaDescuentoDesagregado.isEmpty()){
				bdMontoEnviadoTotal = BigDecimal.ZERO;
				intNroSociosEnviadoTotal = 0;
				bdMontoEfectuadoTotal = BigDecimal.ZERO;
				intNroSociosEfectuadoTotal = 0;
				bdMontoDiferenciaTotal = BigDecimal.ZERO;
				intNroSociosDiferenciaTotal = 0;
				
				for(PlanillaDescuento pllaDscto : listaPlanillaDescuentoDesagregado){
					if(pllaDscto.getBdMontoEnviado()!=null){
						bdMontoEnviadoTotal = bdMontoEnviadoTotal.add(pllaDscto.getBdMontoEnviado());
					}
					if(pllaDscto.getIntNroSociosEnviado()!=null){
						intNroSociosEnviadoTotal = intNroSociosEnviadoTotal + pllaDscto.getIntNroSociosEnviado();
					}
					if(pllaDscto.getBdMontoEfectuado()!=null){
						bdMontoEfectuadoTotal = bdMontoEfectuadoTotal.add(pllaDscto.getBdMontoEfectuado());
					}
					if(pllaDscto.getIntNroSociosEfectuado()!=null){
						intNroSociosEfectuadoTotal = intNroSociosEfectuadoTotal + pllaDscto.getIntNroSociosEfectuado();
					}
					if(pllaDscto.getBdMontoDiferencia()!=null){
						bdMontoDiferenciaTotal = bdMontoDiferenciaTotal.add(pllaDscto.getBdMontoDiferencia());
					}
					if(pllaDscto.getIntNroSociosDiferencia()!=null){
						intNroSociosDiferenciaTotal = intNroSociosDiferenciaTotal + pllaDscto.getIntNroSociosDiferencia();
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			log.error("Error en consultarPlanillaDescuento ---> "+e);
		}
	}
	
	public Boolean isValidFiltros() {
		Boolean validFiltros = true;
		intErrorFiltros = 0;
		try {
			//1. Validación de Sucursal
			if (intIdSucursal==null || intIdSucursal.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			//2. Validación de Sub Sucursal
			if (intIdSubSucursal==null || intIdSubSucursal.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			/*if (intTipoSocio==null || intTipoSocio.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			if (intModalidad==null || intModalidad.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}*/
			if (intAnioBusqueda==null || intAnioBusqueda.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			if (intMes==null || intMes.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
		} catch (Exception e) {
			log.error("Error en isValidFiltros ---> "+e);
		}
		return validFiltros;
	}
	
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
	
	//CONCATENA MES Y AÑO
	public Integer concatPeriodo(Integer anio, Integer mes){
		Integer intPeriodo = 0;
		String strMes="";
		if (mes.compareTo(10)<0) {
			strMes="0"+mes;
		}else{
			strMes=mes.toString();
		}
		intPeriodo = Integer.valueOf(anio+strMes);
		return intPeriodo;
	}
	
	public void imprimirPlanillaDescuento(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaTipoSocio = null;
		String strTipoSocio = "";
		Tabla tablaModalidad = null;
		String strModalidad = "";
		Tabla tablaMes = null;
		String strMes = "";
		String strTipoSucursal = null;
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaTipoSocio = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOSOCIO), intTipoSocio);
			tablaModalidad = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MODALIDADPLANILLA), intModalidad);
			tablaMes = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MES_CALENDARIO), intMes);
			
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			if(tablaTipoSocio!=null){
				strTipoSocio = tablaTipoSocio.getStrDescripcion();
			}
			if(tablaModalidad!=null){
				strModalidad = tablaModalidad.getStrDescripcion();
			}
			if(tablaMes!=null){
				strMes = tablaMes.getStrDescripcion();
			}
			if(intTipoSucursal!=0){
				strTipoSucursal = (intTipoSucursal==1?"Socio":"Planilla");
			}
			
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_TIPOSOCIO", strTipoSocio);
			parametro.put("P_MODALIDAD", strModalidad);
			parametro.put("P_TIPOSUCURSAL", strTipoSucursal);
			parametro.put("P_ANIO", intAnioBusqueda);
			parametro.put("P_MES", strMes);
			
			strNombreReporte = "PlanillaDescuento";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaPlanillaDescuentoDesagregado), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirPlanillaDescuento ---> "+e);
		}
	}
	
	public String getLimpiarPlanillaDescuento(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_PLANILLA_DESAGREGADO);
		log.info("POSEE PERMISO" + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			inicio();
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
	
	public List<Sucursal> getListJuridicaSucursal() {
		return listJuridicaSucursal;
	}
	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}

	public List<SelectItem> getListYears(){
		return listYears;
	}
	
	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
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

	public List<Subsucursal> getListJuridicaSubsucursal() {
		return listJuridicaSubsucursal;
	}

	public void setListJuridicaSubsucursal(List<Subsucursal> listJuridicaSubsucursal) {
		this.listJuridicaSubsucursal = listJuridicaSubsucursal;
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

	public Integer getIntErrorFiltros() {
		return intErrorFiltros;
	}

	public void setIntErrorFiltros(Integer intErrorFiltros) {
		this.intErrorFiltros = intErrorFiltros;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public List<Tabla> getListaDescripcionMes() {
		return listaDescripcionMes;
	}

	public void setListaDescripcionMes(List<Tabla> listaDescripcionMes) {
		this.listaDescripcionMes = listaDescripcionMes;
	}

	public List<Tabla> getListaDescripcionTipoSocio() {
		return listaDescripcionTipoSocio;
	}

	public void setListaDescripcionTipoSocio(List<Tabla> listaDescripcionTipoSocio) {
		this.listaDescripcionTipoSocio = listaDescripcionTipoSocio;
	}

	public List<Tabla> getListaDescripcionModalidad() {
		return listaDescripcionModalidad;
	}

	public void setListaDescripcionModalidad(List<Tabla> listaDescripcionModalidad) {
		this.listaDescripcionModalidad = listaDescripcionModalidad;
	}

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}

	public List<PlanillaDescuento> getListaPlanillaDescuentoDesagregado() {
		return listaPlanillaDescuentoDesagregado;
	}

	public void setListaPlanillaDescuentoDesagregado(
			List<PlanillaDescuento> listaPlanillaDescuentoDesagregado) {
		this.listaPlanillaDescuentoDesagregado = listaPlanillaDescuentoDesagregado;
	}

	public Integer getIntTipoSocio() {
		return intTipoSocio;
	}

	public void setIntTipoSocio(Integer intTipoSocio) {
		this.intTipoSocio = intTipoSocio;
	}

	public Integer getIntModalidad() {
		return intModalidad;
	}

	public void setIntModalidad(Integer intModalidad) {
		this.intModalidad = intModalidad;
	}

	public Integer getIntTipoSucursal() {
		return intTipoSucursal;
	}

	public void setIntTipoSucursal(Integer intTipoSucursal) {
		this.intTipoSucursal = intTipoSucursal;
	}

	public Integer getIntAnioBusqueda() {
		return intAnioBusqueda;
	}

	public void setIntAnioBusqueda(Integer intAnioBusqueda) {
		this.intAnioBusqueda = intAnioBusqueda;
	}

	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}

	public BigDecimal getBdMontoEnviadoTotal() {
		return bdMontoEnviadoTotal;
	}

	public void setBdMontoEnviadoTotal(BigDecimal bdMontoEnviadoTotal) {
		this.bdMontoEnviadoTotal = bdMontoEnviadoTotal;
	}

	public Integer getIntNroSociosEnviadoTotal() {
		return intNroSociosEnviadoTotal;
	}

	public void setIntNroSociosEnviadoTotal(Integer intNroSociosEnviadoTotal) {
		this.intNroSociosEnviadoTotal = intNroSociosEnviadoTotal;
	}

	public BigDecimal getBdMontoEfectuadoTotal() {
		return bdMontoEfectuadoTotal;
	}

	public void setBdMontoEfectuadoTotal(BigDecimal bdMontoEfectuadoTotal) {
		this.bdMontoEfectuadoTotal = bdMontoEfectuadoTotal;
	}

	public Integer getIntNroSociosEfectuadoTotal() {
		return intNroSociosEfectuadoTotal;
	}

	public void setIntNroSociosEfectuadoTotal(Integer intNroSociosEfectuadoTotal) {
		this.intNroSociosEfectuadoTotal = intNroSociosEfectuadoTotal;
	}

	public BigDecimal getBdMontoDiferenciaTotal() {
		return bdMontoDiferenciaTotal;
	}

	public void setBdMontoDiferenciaTotal(BigDecimal bdMontoDiferenciaTotal) {
		this.bdMontoDiferenciaTotal = bdMontoDiferenciaTotal;
	}

	public Integer getIntNroSociosDiferenciaTotal() {
		return intNroSociosDiferenciaTotal;
	}

	public void setIntNroSociosDiferenciaTotal(Integer intNroSociosDiferenciaTotal) {
		this.intNroSociosDiferenciaTotal = intNroSociosDiferenciaTotal;
	}
}