package pe.com.tumi.reporte.cobranza.centralriesgo.controller;

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
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.domain.CarteraCredito;
import pe.com.tumi.reporte.operativo.cobranza.centralriesgo.facade.CarteraCreditoFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class CarteraCreditoController {
	protected 	static Logger 	log;
	
	private Integer SESION_IDEMPRESA;
	private Usuario usuario;
	private	Integer	EMPRESA_USUARIO;
	private	Integer	PERSONA_USUARIO;
	private boolean poseePermiso;
	
	private Integer intIdSucursal;
	private List<Sucursal> listJuridicaSucursal;
	private Integer intIdSubSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	private Integer intParaTipoSocio;
	private Integer intParaModalidad;
	private Integer intParaTipoCredito;
	private Integer intParaSubTipoCredito;
	private Integer intParaClasificacionDeudor;
	private Integer intParaMes;
	private Integer intParaAnio;
	private List<Tabla> lstTipoCredito;
	private List<Tabla> lstSubTipoCredito;
	private List<SelectItem> listYears;
	private Integer intErrorFiltros;
	
	private List<CarteraCredito> listaCarteraCredito;
	
	//Facades
	private TablaFacadeRemote tablaFacade;
	private GeneralFacadeRemote generalFacade;
	CarteraCreditoFacadeLocal carteraCreditoFacade;
	
	//Totalizadores
	private Integer intTotNroSolicitud;
	private BigDecimal bdTotMontoOtorgado;
	private BigDecimal bdTotSaldoCapitalDeuda;
	private BigDecimal bdTotProvision;
	private BigDecimal bdTotClasifNormal;
	private BigDecimal bdTotClasifCPP;
	private BigDecimal bdTotClasifDeficiente;
	private BigDecimal bdTotClasifDudoso;
	private BigDecimal bdTotClasifPerdida;
	
	public CarteraCreditoController() {
		log = Logger.getLogger(this.getClass());
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		
		try {
			carteraCreditoFacade = (CarteraCreditoFacadeLocal)EJBFactory.getLocal(CarteraCreditoFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		inicio();
	}
	
	public void inicio() {
		//LLENANDO COMBOS DEL FORMULARIO
		listJuridicaSubsucursal = new ArrayList<Subsucursal>();
		
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		
		//listaSocioDiferencia = new ArrayList<PlanillaDescuento>();
		getListSucursales();
		getListTipoCredito();
		getListAnios();
	}
	
	public void consultarCarteraCredito() {
		Integer intPeriodo = null;
		try {
			if (!isValidFiltros()) {
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("Los filtros de Sucursal, Sub Sucursal, Año y mes no pueden estar vacíos. " +
						"Verifique.");
				return;
			}
			if(intParaTipoSocio == 0) intParaTipoSocio = null;
			if(intParaModalidad == 0) intParaModalidad = null;
			if(intParaTipoCredito == 0) intParaTipoCredito = null;
			if(intParaSubTipoCredito == 0) intParaSubTipoCredito = null;
			if(intParaClasificacionDeudor == 0) intParaClasificacionDeudor = null;
			//if(intParaAnio == 0) intParaAnio = null;
			//if(intParaMes == 0) intParaMes = null;
			
			intPeriodo = concatPeriodo(intParaAnio, intParaMes);
			listaCarteraCredito = carteraCreditoFacade.getListaCarteraCredito(intIdSucursal, intIdSubSucursal, intParaTipoSocio, intParaModalidad, intParaTipoCredito, intParaSubTipoCredito, intParaClasificacionDeudor, intPeriodo);
			
			if(listaCarteraCredito!=null && !listaCarteraCredito.isEmpty()){
				intTotNroSolicitud = 0;
				bdTotMontoOtorgado = BigDecimal.ZERO;
				bdTotSaldoCapitalDeuda = BigDecimal.ZERO;
				bdTotProvision = BigDecimal.ZERO;
				bdTotClasifNormal = BigDecimal.ZERO;
				bdTotClasifCPP = BigDecimal.ZERO;
				bdTotClasifDeficiente = BigDecimal.ZERO;
				bdTotClasifDudoso = BigDecimal.ZERO;
				bdTotClasifPerdida = BigDecimal.ZERO;
				
				for(CarteraCredito carteraCredito : listaCarteraCredito){
					intTotNroSolicitud = listaCarteraCredito.size();
					if(carteraCredito.getBdMontoOtorgado()!=null)
						bdTotMontoOtorgado = bdTotMontoOtorgado.add(carteraCredito.getBdMontoOtorgado());
					if(carteraCredito.getBdSaldoCapitalDeuda()!=null)
						bdTotSaldoCapitalDeuda = bdTotSaldoCapitalDeuda.add(carteraCredito.getBdSaldoCapitalDeuda());
					if(carteraCredito.getBdConstituida()!=null)
						bdTotProvision = bdTotProvision.add(carteraCredito.getBdConstituida());
					if(carteraCredito.getIntClasificacionDeudor()!=null && carteraCredito.getBdRequerida()!=null){
						switch (carteraCredito.getIntClasificacionDeudor()) {
						case 1:
							bdTotClasifNormal = bdTotClasifNormal.add(carteraCredito.getBdRequerida());
							break;
						case 2:
							bdTotClasifCPP = bdTotClasifCPP.add(carteraCredito.getBdRequerida());
							break;
						case 3:
							bdTotClasifDeficiente = bdTotClasifDeficiente.add(carteraCredito.getBdRequerida());
							break;
						case 4:
							bdTotClasifDudoso = bdTotClasifDudoso.add(carteraCredito.getBdRequerida());
							break;
						case 5:
							bdTotClasifPerdida = bdTotClasifPerdida.add(carteraCredito.getBdRequerida());
							break;
						default:
							break;
						}
					}
				}
			} else {
				intTotNroSolicitud = 0;
				bdTotMontoOtorgado = BigDecimal.ZERO;
				bdTotSaldoCapitalDeuda = BigDecimal.ZERO;
				bdTotProvision = BigDecimal.ZERO;
				bdTotClasifNormal = BigDecimal.ZERO;
				bdTotClasifCPP = BigDecimal.ZERO;
				bdTotClasifDeficiente = BigDecimal.ZERO;
				bdTotClasifDudoso = BigDecimal.ZERO;
				bdTotClasifPerdida = BigDecimal.ZERO;
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("No existe información con los filtros seleccionados." +
						"Verifique.");
				return;
			}
		} catch (Exception e) {
			log.error("Error en consultarSocioDiferencia ---> "+e);
		}
	}
	
	public void imprimirCarteraCredito(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaMes = null;
		Tabla tablaTipoSocio = null;
		Tabla tablaModalidad = null;
		Tabla tablaTipoCredito = null;
		Tabla tablaSubTipoCredito = null;
		Tabla tablaClasificacionDeudor = null;
		
		String strMes = "";
		String strTipoSocio = null;
		String strModalidad = null;
		String strTipoCredito = null;
		String strSubTipoCredito = null;
		String strClasificacionDeudor = null;
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaMes = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MES_CALENDARIO), intParaMes);
			tablaTipoSocio = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOSOCIO), intParaTipoSocio);
			tablaModalidad = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MODALIDADPLANILLA), intParaModalidad);
			tablaTipoCredito = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPO_CREDITO), intParaTipoCredito);
			tablaSubTipoCredito = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOCREDITOEMPRESA), intParaSubTipoCredito);
			tablaClasificacionDeudor = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOCATEGORIADERIESGO), intParaClasificacionDeudor);
			
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			
			if(tablaMes!=null){
				strMes = tablaMes.getStrDescripcion();
			}
			if(tablaTipoSocio!=null){
				strTipoSocio = tablaTipoSocio.getStrDescripcion();
			}
			if(tablaModalidad!=null){
				strModalidad = tablaModalidad.getStrDescripcion();
			}
			if(tablaTipoCredito!=null){
				strTipoCredito = tablaTipoCredito.getStrDescripcion();
			}
			if(tablaSubTipoCredito!=null){
				strSubTipoCredito = tablaSubTipoCredito.getStrDescripcion();
			}
			if(tablaClasificacionDeudor!=null){
				strClasificacionDeudor = tablaClasificacionDeudor.getStrDescripcion();
			}
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_ANIO", intParaAnio);
			parametro.put("P_MES", strMes);
			parametro.put("P_TIPOSOCIO", strTipoSocio);
			parametro.put("P_MODALIDAD", strModalidad);
			parametro.put("P_TIPOCREDITO", strTipoCredito);
			parametro.put("P_SUBTIPOCREDITO", strSubTipoCredito);
			parametro.put("P_CLASIFDEUDOR", strClasificacionDeudor);
			parametro.put("P_CLASIF_NORMAL", bdTotClasifNormal);
			parametro.put("P_CLASIF_CPP", bdTotClasifCPP);
			parametro.put("P_CLASIF_DEFICIENTE", bdTotClasifDeficiente);
			parametro.put("P_CLASIF_DUDOSO", bdTotClasifDudoso);
			parametro.put("P_CLASIF_PERDIDA", bdTotClasifPerdida);
			
			strNombreReporte = "CarteraCredito";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaCarteraCredito), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error en imprimirSocioDiferencia ---> "+e);
		}
	}
	
	public String getLimpiarCarteraCredito(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_CARTERA_CREDITO);
		log.info("POSEE PERMISO" + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			inicio();
			limpiarTabCarteraCredito();
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
	
	public void limpiarTabCarteraCredito(){
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		intParaAnio = 0;
		intParaMes = 0;
		intParaTipoSocio = 0;
		intParaModalidad = 0;
		listaCarteraCredito = new ArrayList<CarteraCredito>();
		intTotNroSolicitud = null;
		bdTotMontoOtorgado = null;
		bdTotSaldoCapitalDeuda = null;
		bdTotProvision = null;
		bdTotClasifNormal = null;
		bdTotClasifCPP = null;
		bdTotClasifDeficiente = null;
		bdTotClasifDudoso = null;
		bdTotClasifPerdida = null;
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
			
			if (intParaAnio==null || intParaAnio.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			
			if (intParaMes==null || intParaMes.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			
		} catch (Exception e) {
			log.error("Error en isValidFiltros ---> "+e);
		}
		return validFiltros;
	}
	
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
	
	public void getListSucursales() {
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
	
	public void getListTipoCredito() {
		try {
			if(lstTipoCredito == null){
				lstTipoCredito = tablaFacade.getListaTablaPorAgrupamientoA(Integer.valueOf(Constante.PARAM_T_TIPO_CREDITO), Constante.PARAM_STR_VALUE_A);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public void getListAnios() {
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
	
	public void getListSubSucursal() {
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = Integer.valueOf(getRequestParameter("pCboSucursal"));
			
			if(intIdSucursal!=0){
				facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
				listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursal);
			}
		} catch (EJBFactoryException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setListJuridicaSubsucursal(listaSubsucursal);
	}
	
	public void getListSubTipoCredito() {
		Integer intIdTipoCredito = null;
		List<Tabla> listaSubTipoCredito = null;
		try {
			intIdTipoCredito = Integer.valueOf(getRequestParameter("pCboTipoCredito"));
			
			if(intIdTipoCredito!=0){
				listaSubTipoCredito = tablaFacade.getListaTablaPorAgrupamientoB(Integer.valueOf(Constante.PARAM_T_TIPOCREDITOEMPRESA), intIdTipoCredito);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		setLstSubTipoCredito(listaSubTipoCredito);
	}

	public List<Sucursal> getListJuridicaSucursal() {
		return listJuridicaSucursal;
	}

	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
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

	public List<Subsucursal> getListJuridicaSubsucursal() {
		return listJuridicaSubsucursal;
	}

	public void setListJuridicaSubsucursal(List<Subsucursal> listJuridicaSubsucursal) {
		this.listJuridicaSubsucursal = listJuridicaSubsucursal;
	}

	public Integer getIntParaTipoSocio() {
		return intParaTipoSocio;
	}

	public void setIntParaTipoSocio(Integer intParaTipoSocio) {
		this.intParaTipoSocio = intParaTipoSocio;
	}

	public Integer getIntParaModalidad() {
		return intParaModalidad;
	}

	public void setIntParaModalidad(Integer intParaModalidad) {
		this.intParaModalidad = intParaModalidad;
	}

	public Integer getIntParaTipoCredito() {
		return intParaTipoCredito;
	}

	public void setIntParaTipoCredito(Integer intParaTipoCredito) {
		this.intParaTipoCredito = intParaTipoCredito;
	}

	public Integer getIntParaSubTipoCredito() {
		return intParaSubTipoCredito;
	}

	public void setIntParaSubTipoCredito(Integer intParaSubTipoCredito) {
		this.intParaSubTipoCredito = intParaSubTipoCredito;
	}

	public Integer getIntParaClasificacionDeudor() {
		return intParaClasificacionDeudor;
	}

	public void setIntParaClasificacionDeudor(Integer intParaClasificacionDeudor) {
		this.intParaClasificacionDeudor = intParaClasificacionDeudor;
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

	public List<Tabla> getLstTipoCredito() {
		return lstTipoCredito;
	}

	public void setLstTipoCredito(List<Tabla> lstTipoCredito) {
		this.lstTipoCredito = lstTipoCredito;
	}

	public List<Tabla> getLstSubTipoCredito() {
		return lstSubTipoCredito;
	}

	public void setLstSubTipoCredito(List<Tabla> lstSubTipoCredito) {
		this.lstSubTipoCredito = lstSubTipoCredito;
	}

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public Integer getIntErrorFiltros() {
		return intErrorFiltros;
	}

	public void setIntErrorFiltros(Integer intErrorFiltros) {
		this.intErrorFiltros = intErrorFiltros;
	}

	public List<CarteraCredito> getListaCarteraCredito() {
		return listaCarteraCredito;
	}

	public void setListaCarteraCredito(List<CarteraCredito> listaCarteraCredito) {
		this.listaCarteraCredito = listaCarteraCredito;
	}

	public Integer getIntTotNroSolicitud() {
		return intTotNroSolicitud;
	}

	public void setIntTotNroSolicitud(Integer intTotNroSolicitud) {
		this.intTotNroSolicitud = intTotNroSolicitud;
	}

	public BigDecimal getBdTotMontoOtorgado() {
		return bdTotMontoOtorgado;
	}

	public void setBdTotMontoOtorgado(BigDecimal bdTotMontoOtorgado) {
		this.bdTotMontoOtorgado = bdTotMontoOtorgado;
	}

	public BigDecimal getBdTotSaldoCapitalDeuda() {
		return bdTotSaldoCapitalDeuda;
	}

	public void setBdTotSaldoCapitalDeuda(BigDecimal bdTotSaldoCapitalDeuda) {
		this.bdTotSaldoCapitalDeuda = bdTotSaldoCapitalDeuda;
	}

	public BigDecimal getBdTotProvision() {
		return bdTotProvision;
	}

	public void setBdTotProvision(BigDecimal bdTotProvision) {
		this.bdTotProvision = bdTotProvision;
	}

	public BigDecimal getBdTotClasifNormal() {
		return bdTotClasifNormal;
	}

	public void setBdTotClasifNormal(BigDecimal bdTotClasifNormal) {
		this.bdTotClasifNormal = bdTotClasifNormal;
	}

	public BigDecimal getBdTotClasifCPP() {
		return bdTotClasifCPP;
	}

	public void setBdTotClasifCPP(BigDecimal bdTotClasifCPP) {
		this.bdTotClasifCPP = bdTotClasifCPP;
	}

	public BigDecimal getBdTotClasifDeficiente() {
		return bdTotClasifDeficiente;
	}

	public void setBdTotClasifDeficiente(BigDecimal bdTotClasifDeficiente) {
		this.bdTotClasifDeficiente = bdTotClasifDeficiente;
	}

	public BigDecimal getBdTotClasifDudoso() {
		return bdTotClasifDudoso;
	}

	public void setBdTotClasifDudoso(BigDecimal bdTotClasifDudoso) {
		this.bdTotClasifDudoso = bdTotClasifDudoso;
	}

	public BigDecimal getBdTotClasifPerdida() {
		return bdTotClasifPerdida;
	}

	public void setBdTotClasifPerdida(BigDecimal bdTotClasifPerdida) {
		this.bdTotClasifPerdida = bdTotClasifPerdida;
	}	
}