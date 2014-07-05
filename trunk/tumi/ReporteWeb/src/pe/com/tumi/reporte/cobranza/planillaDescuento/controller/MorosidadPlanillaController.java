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
import org.apache.openjpa.util.IntId;

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

public class MorosidadPlanillaController {
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
	
	//Filtros de Búsqueda
	private Integer intErrorFiltros;
	
	private Integer intIdSucursal;
	private List<Sucursal> listJuridicaSucursal;
	private Integer intIdSubSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	private Integer intMes;
	private Integer intAnioBusqueda;
	private Integer intTipoSucursal;
	
	private List<SelectItem> listYears;
	private List<PlanillaDescuento> listaMorosidadPlanilla;
	
	//SUMATORIAS
	private BigDecimal bdSumAdicReferencial;
	private BigDecimal bdSumPllaGenerada;
	private BigDecimal bdSumAdicEjecutada;
	private BigDecimal bdSumTotalEnviado;
	private BigDecimal bdSumPllaIngresada;
	private BigDecimal bdSumMontoAdicional;
	private BigDecimal bdSumTotalEfectuado;
	private BigDecimal bdSumMorPllaDiferencia;
	private BigDecimal bdSumMorPllaPorc;
	private BigDecimal bdSumMorCajaIngresos;
	private BigDecimal bdSumMorCajaDiferencia;
	private BigDecimal bdSumMorCajaPorc;
	
	public MorosidadPlanillaController(){
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
		} catch (Exception e) {
			log.error("Error en AsociativoController --> "+e);
		}
		intErrorFiltros = 0;
		inicio();
	}
	
	public void inicio() {
		//LLENANDO COMBOS DEL FORMULARIO
		listJuridicaSubsucursal = new ArrayList<Subsucursal>();
		
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		
		listaMorosidadPlanilla = new ArrayList<PlanillaDescuento>();
		getListSucursales();
		getListAnios();
	}
	
	public void getListSubSucursal() {
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = Integer.valueOf(getRequestParameter("pCboSucursalMP"));
			
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
	
	public void consultarMorosidadPlanilla() {
		log.info("intIdSucursal: " + intIdSucursal);
		log.info("intIdSubSucursal: " + intIdSubSucursal);
		Integer intPeriodo = null;
		try {
			if (!isValidFiltros()) {
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("Los filtros de Sucursal, Sub Sucursal, Año y mes no pueden estar vacíos. " +
						"Verifique.");
				return;
			}
			intPeriodo = concatPeriodo(intAnioBusqueda, intMes);
			log.info("intPeriodo: " + intPeriodo);
			listaMorosidadPlanilla = planillaDescuentoFacade.getListaMorosidadPlanilla(intIdSucursal, intIdSubSucursal, intPeriodo, intTipoSucursal);
			
			if(listaMorosidadPlanilla!=null && !listaMorosidadPlanilla.isEmpty()){
				bdSumAdicReferencial = BigDecimal.ZERO;
				bdSumPllaGenerada = BigDecimal.ZERO;
				bdSumAdicEjecutada = BigDecimal.ZERO;
				bdSumTotalEnviado = BigDecimal.ZERO;
				bdSumPllaIngresada = BigDecimal.ZERO;
				bdSumMontoAdicional = BigDecimal.ZERO;
				bdSumTotalEfectuado = BigDecimal.ZERO;
				bdSumMorPllaDiferencia = BigDecimal.ZERO;
				bdSumMorPllaPorc = BigDecimal.ZERO;
				bdSumMorCajaIngresos = BigDecimal.ZERO;
				bdSumMorCajaDiferencia = BigDecimal.ZERO;
				bdSumMorCajaPorc = BigDecimal.ZERO;
				for (PlanillaDescuento morosidadPlanilla : listaMorosidadPlanilla) {
					if(morosidadPlanilla.getBdAdicionReferencialMP()!=null){
						bdSumAdicReferencial = bdSumAdicReferencial.add(morosidadPlanilla.getBdAdicionReferencialMP());
					}
					if(morosidadPlanilla.getBdPlanillaGeneradaMP()!=null){
						bdSumPllaGenerada = bdSumPllaGenerada.add(morosidadPlanilla.getBdPlanillaGeneradaMP());
					}
					if(morosidadPlanilla.getBdAdicionEjecutadaMP()!=null){
						bdSumAdicEjecutada = bdSumAdicEjecutada.add(morosidadPlanilla.getBdAdicionEjecutadaMP());
					}
					if(morosidadPlanilla.getBdTotalEnviadaMP()!=null){
						bdSumTotalEnviado = bdSumTotalEnviado.add(morosidadPlanilla.getBdTotalEnviadaMP());
					}
					if(morosidadPlanilla.getBdPlanillaIngresadaMP()!=null){
						bdSumPllaIngresada = bdSumPllaIngresada.add(morosidadPlanilla.getBdPlanillaIngresadaMP());
					}
					if(morosidadPlanilla.getBdMontoAdicionalMP()!=null){
						bdSumMontoAdicional = bdSumMontoAdicional.add(morosidadPlanilla.getBdMontoAdicionalMP());
					}
					if(morosidadPlanilla.getBdTotalEfectuadaMP()!=null){
						bdSumTotalEfectuado = bdSumTotalEfectuado.add(morosidadPlanilla.getBdTotalEfectuadaMP());
					}
					if(morosidadPlanilla.getBdMorosidadDiferenciaMP()!=null){
						bdSumMorPllaDiferencia = bdSumMorPllaDiferencia.add(morosidadPlanilla.getBdMorosidadDiferenciaMP());
					}
					if(morosidadPlanilla.getBdMorosidadPorcMP()!=null){
						bdSumMorPllaPorc = bdSumMorPllaPorc.add(morosidadPlanilla.getBdMorosidadPorcMP());
					}
					if(morosidadPlanilla.getBdMorosidadIngresosCajaMP()!=null){
						bdSumMorCajaIngresos = bdSumMorCajaIngresos.add(morosidadPlanilla.getBdMorosidadIngresosCajaMP());
					}
					if(morosidadPlanilla.getBdMorosidadDiferenciaCajaMP()!=null){
						bdSumMorCajaDiferencia = bdSumMorCajaDiferencia.add(morosidadPlanilla.getBdMorosidadDiferenciaCajaMP());
					}
					if(morosidadPlanilla.getBdMorosidadPorcentajeCajaMP()!=null){
						bdSumMorCajaPorc = bdSumMorCajaPorc.add(morosidadPlanilla.getBdMorosidadPorcentajeCajaMP());
					}
				}
			}
		} catch (Exception e) {
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
			
			if (intAnioBusqueda==null || intAnioBusqueda.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			
			if (intMes==null || intMes.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			
			if (intTipoSucursal==null || intTipoSucursal.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
		} catch (Exception e) {
			log.error("Error en isValidFiltros ---> "+e);
		}
		return validFiltros;
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
	
	public String getLimpiarMorosidadPlanilla(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_MOROSIDAD_PLANILLA);
		log.info("POSEE PERMISO" + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			inicio();
			limpiarTabMorosidadPlanilla();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		return "";
	}
	
	public void limpiarTabMorosidadPlanilla(){
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		intAnioBusqueda = 0;
		intMes = 0;
		intTipoSucursal = 0;
		listaMorosidadPlanilla = new ArrayList<PlanillaDescuento>();
		bdSumAdicReferencial = null;
		bdSumPllaGenerada = null;
		bdSumAdicEjecutada = null;
		bdSumTotalEnviado = null;
		bdSumPllaIngresada = null;
		bdSumMontoAdicional = null;
		bdSumTotalEfectuado = null;
		bdSumMorPllaDiferencia = null;
		bdSumMorPllaPorc = null;
		bdSumMorCajaIngresos = null;
		bdSumMorCajaDiferencia = null;
		bdSumMorCajaPorc = null;
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
	
	public void imprimirMorosidadPlanilla(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaMes = null;
		String strMes = "";
		String strTipoSucursal = null;
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaMes = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MES_CALENDARIO), intMes);
			
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			
			if(tablaMes!=null){
				strMes = tablaMes.getStrDescripcion();
			}
			
			if(intTipoSucursal!=0){
				strTipoSucursal = (intTipoSucursal==1?"Socio":"Planilla");
			}
			
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_TIPOSUCURSAL", strTipoSucursal);
			parametro.put("P_ANIO", intAnioBusqueda);
			parametro.put("P_MES", strMes);
			
			strNombreReporte = "morosidadPlanilla";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaMorosidadPlanilla), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error en imprimirPlanillaDescuento ---> "+e);
		}
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

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}

	public Integer getIntMes() {
		return intMes;
	}

	public void setIntMes(Integer intMes) {
		this.intMes = intMes;
	}

	public Integer getIntAnioBusqueda() {
		return intAnioBusqueda;
	}

	public void setIntAnioBusqueda(Integer intAnioBusqueda) {
		this.intAnioBusqueda = intAnioBusqueda;
	}

	public Integer getIntTipoSucursal() {
		return intTipoSucursal;
	}

	public void setIntTipoSucursal(Integer intTipoSucursal) {
		this.intTipoSucursal = intTipoSucursal;
	}

	public List<PlanillaDescuento> getListaMorosidadPlanilla() {
		return listaMorosidadPlanilla;
	}

	public void setListaMorosidadPlanilla(
			List<PlanillaDescuento> listaMorosidadPlanilla) {
		this.listaMorosidadPlanilla = listaMorosidadPlanilla;
	}

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public BigDecimal getBdSumAdicReferencial() {
		return bdSumAdicReferencial;
	}

	public void setBdSumAdicReferencial(BigDecimal bdSumAdicReferencial) {
		this.bdSumAdicReferencial = bdSumAdicReferencial;
	}

	public BigDecimal getBdSumPllaGenerada() {
		return bdSumPllaGenerada;
	}

	public void setBdSumPllaGenerada(BigDecimal bdSumPllaGenerada) {
		this.bdSumPllaGenerada = bdSumPllaGenerada;
	}

	public BigDecimal getBdSumAdicEjecutada() {
		return bdSumAdicEjecutada;
	}

	public void setBdSumAdicEjecutada(BigDecimal bdSumAdicEjecutada) {
		this.bdSumAdicEjecutada = bdSumAdicEjecutada;
	}

	public BigDecimal getBdSumTotalEnviado() {
		return bdSumTotalEnviado;
	}

	public void setBdSumTotalEnviado(BigDecimal bdSumTotalEnviado) {
		this.bdSumTotalEnviado = bdSumTotalEnviado;
	}

	public BigDecimal getBdSumPllaIngresada() {
		return bdSumPllaIngresada;
	}

	public void setBdSumPllaIngresada(BigDecimal bdSumPllaIngresada) {
		this.bdSumPllaIngresada = bdSumPllaIngresada;
	}

	public BigDecimal getBdSumMontoAdicional() {
		return bdSumMontoAdicional;
	}

	public void setBdSumMontoAdicional(BigDecimal bdSumMontoAdicional) {
		this.bdSumMontoAdicional = bdSumMontoAdicional;
	}

	public BigDecimal getBdSumTotalEfectuado() {
		return bdSumTotalEfectuado;
	}

	public void setBdSumTotalEfectuado(BigDecimal bdSumTotalEfectuado) {
		this.bdSumTotalEfectuado = bdSumTotalEfectuado;
	}

	public BigDecimal getBdSumMorPllaDiferencia() {
		return bdSumMorPllaDiferencia;
	}

	public void setBdSumMorPllaDiferencia(BigDecimal bdSumMorPllaDiferencia) {
		this.bdSumMorPllaDiferencia = bdSumMorPllaDiferencia;
	}

	public BigDecimal getBdSumMorPllaPorc() {
		return bdSumMorPllaPorc;
	}

	public void setBdSumMorPllaPorc(BigDecimal bdSumMorPllaPorc) {
		this.bdSumMorPllaPorc = bdSumMorPllaPorc;
	}

	public BigDecimal getBdSumMorCajaIngresos() {
		return bdSumMorCajaIngresos;
	}

	public void setBdSumMorCajaIngresos(BigDecimal bdSumMorCajaIngresos) {
		this.bdSumMorCajaIngresos = bdSumMorCajaIngresos;
	}

	public BigDecimal getBdSumMorCajaDiferencia() {
		return bdSumMorCajaDiferencia;
	}

	public void setBdSumMorCajaDiferencia(BigDecimal bdSumMorCajaDiferencia) {
		this.bdSumMorCajaDiferencia = bdSumMorCajaDiferencia;
	}

	public BigDecimal getBdSumMorCajaPorc() {
		return bdSumMorCajaPorc;
	}

	public void setBdSumMorCajaPorc(BigDecimal bdSumMorCajaPorc) {
		this.bdSumMorCajaPorc = bdSumMorCajaPorc;
	}
}