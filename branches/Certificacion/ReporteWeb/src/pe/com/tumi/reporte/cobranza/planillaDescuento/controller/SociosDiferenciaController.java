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

public class SociosDiferenciaController {
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
	//private EstructuraFacadeRemote estructuraFacade;
	
	//Filtros de Búsqueda
	private Integer intErrorFiltros;
	
	private Integer intIdSucursal;
	private List<Sucursal> listJuridicaSucursal;
	//private List<Estructura> listUnidadEjecutora;
	private List<PlanillaDescuento> listaUnidadEjecutora;
	private Integer intIdSubSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	private Integer intIdUnidadEjecutora;
	private Integer intMes;
	private Integer intAnioBusqueda;
	private Integer intIdTipoDiferencia;
	private Integer intParaTipoSocio;
	private Integer intParaModalidad;
	
	private List<SelectItem> listYears;
	private List<PlanillaDescuento> listaSocioDiferencia;
	
	private BigDecimal bdSumPllaEnviada;
	private BigDecimal bdSumPllaEfectuada;
	private BigDecimal bdSumPllaDiferencia;
	private BigDecimal bdSumIngresoCaja;
	private BigDecimal bdSumIngresoCajaDiferencia;
	private BigDecimal bdSumTransferenciaIngreso;
	private BigDecimal bdSumTransferenciaIngresoDiferencia;
	
	public SociosDiferenciaController(){
		log = Logger.getLogger(this.getClass());
		//INICIO DE SESION
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		try {
			planillaDescuentoFacade =(PlanillaDescuentoFacadeLocal)EJBFactory.getLocal(PlanillaDescuentoFacadeLocal.class);
			//estructuraFacade =(EstructuraFacadeRemote)EJBFactory.getRemote(EstructuraFacadeRemote.class);
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
		
		listaSocioDiferencia = new ArrayList<PlanillaDescuento>();
		getListSucursales();
		getListAnios();
	}
	
	public void getListSubSucursal() {
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = Integer.valueOf(getRequestParameter("pCboSucursalSD"));
			
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
	
	public void consultarSocioDiferencia() {
		Integer intPeriodo = null;
		try {
			if (!isValidFiltros()) {
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("Los filtros de Sucursal, Sub Sucursal, Año y mes no pueden estar vacíos. " +
						"Verifique.");
				return;
			}
			if(intIdTipoDiferencia == 0) intIdTipoDiferencia = null;
			if(intParaTipoSocio == 0) intParaTipoSocio = null;
			if(intParaModalidad == 0) intParaModalidad = null;
			
			intPeriodo = concatPeriodo(intAnioBusqueda, intMes);
			listaSocioDiferencia = planillaDescuentoFacade.getListaSocioDiferencia(intIdSucursal, intIdSubSucursal, intIdUnidadEjecutora, intPeriodo, intIdTipoDiferencia, intParaTipoSocio, intParaModalidad);
			
			if(listaSocioDiferencia!=null && !listaSocioDiferencia.isEmpty()){
				bdSumPllaEnviada = BigDecimal.ZERO;
				bdSumPllaEfectuada = BigDecimal.ZERO;
				bdSumPllaDiferencia = BigDecimal.ZERO;
				bdSumIngresoCaja = BigDecimal.ZERO;
				bdSumIngresoCajaDiferencia = BigDecimal.ZERO;
				bdSumTransferenciaIngreso = BigDecimal.ZERO;
				bdSumTransferenciaIngresoDiferencia = BigDecimal.ZERO;
				for(PlanillaDescuento socioDiferencia: listaSocioDiferencia){
					if(socioDiferencia.getBdEnviadoSD()!=null)
						bdSumPllaEnviada = bdSumPllaEnviada.add(socioDiferencia.getBdEnviadoSD());
					if(socioDiferencia.getBdEfectuadoSD()!=null)
						bdSumPllaEfectuada = bdSumPllaEfectuada.add(socioDiferencia.getBdEfectuadoSD());
					if(socioDiferencia.getBdDiferenciaPllaSD()!=null)
						bdSumPllaDiferencia = bdSumPllaDiferencia.add(socioDiferencia.getBdDiferenciaPllaSD());
					if(socioDiferencia.getBdIngresoCajaSD()!=null)
						bdSumIngresoCaja = bdSumIngresoCaja.add(socioDiferencia.getBdIngresoCajaSD());
					if(socioDiferencia.getBdDiferenciaCajaSD()!=null)
						bdSumIngresoCajaDiferencia = bdSumIngresoCajaDiferencia.add(socioDiferencia.getBdDiferenciaCajaSD());
					if(socioDiferencia.getBdIngresoTransferenciaSD()!=null)
						bdSumTransferenciaIngreso = bdSumTransferenciaIngreso.add(socioDiferencia.getBdIngresoTransferenciaSD());
					if(socioDiferencia.getBdDiferenciaTransferenciaSD()!=null)
						bdSumTransferenciaIngresoDiferencia = bdSumTransferenciaIngresoDiferencia.add(socioDiferencia.getBdDiferenciaTransferenciaSD());
				}
			}
		} catch (Exception e) {
			log.error("Error en consultarSocioDiferencia ---> "+e);
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
			
		} catch (Exception e) {
			log.error("Error en isValidFiltros ---> "+e);
		}
		return validFiltros;
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
	
	public void getListUnidadEjecutora() {
		log.info("carga lista entidad");
		Integer intIdSubSucursal = null;
		try {
			intIdSubSucursal = Integer.valueOf(getRequestParameter("pCboSubSucursalSD"));
			log.info("intIdSucursal: " + intIdSucursal);
			log.info("intIdSubSucursal: " + intIdSubSucursal);
			listaUnidadEjecutora = planillaDescuentoFacade.getListaEntidad(intIdSubSucursal);
			log.info("listaUnidadEjecutora: " + listaUnidadEjecutora.size());
		} catch (BusinessException e) {
			e.printStackTrace();
		}
	}
	
	public String getLimpiarSociosDiferencia(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_SOCIOS_DIFERENCIA);
		log.info("POSEE PERMISO" + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			inicio();
			limpiarTabSocioDiferencia();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		return "";
	}
	
	public void limpiarTabSocioDiferencia(){
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		intAnioBusqueda = 0;
		intMes = 0;
		intParaTipoSocio = 0;
		intParaModalidad = 0;
		listaSocioDiferencia = new ArrayList<PlanillaDescuento>();
		bdSumPllaEnviada = null;
		bdSumPllaEfectuada = null;
		bdSumPllaDiferencia = null;
		bdSumIngresoCaja = null;
		bdSumIngresoCajaDiferencia = null;
		bdSumTransferenciaIngreso = null;
		bdSumTransferenciaIngresoDiferencia = null;
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
	
	public void imprimirSocioDiferencia(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaMes = null;
		Tabla tablaTipoSocio = null;
		Tabla tablaModalidad = null;
		String strMes = "";
		String strTipoDiferencia = null;
		String strTipoSocio = null;
		String strModalidad = null;
		String strUnidadEjecutora = null;
		List<PlanillaDescuento> lstUnidadEjec = null;
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaMes = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MES_CALENDARIO), intMes);
			tablaTipoSocio = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOSOCIO), intParaTipoSocio);
			tablaModalidad = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MODALIDADPLANILLA), intParaModalidad);
			
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			
			if(intIdTipoDiferencia.equals(1))
				strTipoDiferencia = "Descuento Normal";
			else if(intIdTipoDiferencia.equals(2))
				strTipoDiferencia = "Diferencia de Menos";
			else if(intIdTipoDiferencia.equals(3))
				strTipoDiferencia = "Exceso de Descuento";
			else if(intIdTipoDiferencia.equals(4))
				strTipoDiferencia = "No descontados";
			
			if(tablaMes!=null){
				strMes = tablaMes.getStrDescripcion();
			}
			if(tablaTipoSocio!=null){
				strTipoSocio = tablaTipoSocio.getStrDescripcion();
			}
			if(tablaModalidad!=null){
				strModalidad = tablaModalidad.getStrDescripcion();
			}
			
			if(intIdUnidadEjecutora!=null && intIdUnidadEjecutora != 0){
				lstUnidadEjec = planillaDescuentoFacade.getListaEntidad(intIdSubSucursal);
				if(lstUnidadEjec!=null && !lstUnidadEjec.isEmpty()){
					for (PlanillaDescuento planillaDescuento : lstUnidadEjec) {
						if(planillaDescuento.getIntIdUnidadEjecutora().equals(intIdUnidadEjecutora)){
							strUnidadEjecutora = planillaDescuento.getStrDescUnidadEjecutora();
							break;
						}
					}
				}
			}
			
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_ENTIDAD", strUnidadEjecutora);
			parametro.put("P_ANIO", intAnioBusqueda);
			parametro.put("P_MES", strMes);
			parametro.put("P_TIPODIFERENCIA", strTipoDiferencia);
			parametro.put("P_TIPOSOCIO", strTipoSocio);
			parametro.put("P_MODALIDAD", strModalidad);
			
			strNombreReporte = "socioDescuento";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaSocioDiferencia), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error en imprimirSocioDiferencia ---> "+e);
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

	public List<PlanillaDescuento> getListaSocioDiferencia() {
		return listaSocioDiferencia;
	}

	public void setListaSocioDiferencia(List<PlanillaDescuento> listaSocioDiferencia) {
		this.listaSocioDiferencia = listaSocioDiferencia;
	}

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public Integer getIntIdUnidadEjecutora() {
		return intIdUnidadEjecutora;
	}

	public void setIntIdUnidadEjecutora(Integer intIdUnidadEjecutora) {
		this.intIdUnidadEjecutora = intIdUnidadEjecutora;
	}

	public Integer getIntIdTipoDiferencia() {
		return intIdTipoDiferencia;
	}

	public void setIntIdTipoDiferencia(Integer intIdTipoDiferencia) {
		this.intIdTipoDiferencia = intIdTipoDiferencia;
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

	public BigDecimal getBdSumPllaEnviada() {
		return bdSumPllaEnviada;
	}

	public void setBdSumPllaEnviada(BigDecimal bdSumPllaEnviada) {
		this.bdSumPllaEnviada = bdSumPllaEnviada;
	}

	public BigDecimal getBdSumPllaEfectuada() {
		return bdSumPllaEfectuada;
	}

	public void setBdSumPllaEfectuada(BigDecimal bdSumPllaEfectuada) {
		this.bdSumPllaEfectuada = bdSumPllaEfectuada;
	}

	public BigDecimal getBdSumPllaDiferencia() {
		return bdSumPllaDiferencia;
	}

	public void setBdSumPllaDiferencia(BigDecimal bdSumPllaDiferencia) {
		this.bdSumPllaDiferencia = bdSumPllaDiferencia;
	}

	public BigDecimal getBdSumIngresoCaja() {
		return bdSumIngresoCaja;
	}

	public void setBdSumIngresoCaja(BigDecimal bdSumIngresoCaja) {
		this.bdSumIngresoCaja = bdSumIngresoCaja;
	}

	public BigDecimal getBdSumIngresoCajaDiferencia() {
		return bdSumIngresoCajaDiferencia;
	}

	public void setBdSumIngresoCajaDiferencia(BigDecimal bdSumIngresoCajaDiferencia) {
		this.bdSumIngresoCajaDiferencia = bdSumIngresoCajaDiferencia;
	}

	public BigDecimal getBdSumTransferenciaIngreso() {
		return bdSumTransferenciaIngreso;
	}

	public void setBdSumTransferenciaIngreso(BigDecimal bdSumTransferenciaIngreso) {
		this.bdSumTransferenciaIngreso = bdSumTransferenciaIngreso;
	}

	public BigDecimal getBdSumTransferenciaIngresoDiferencia() {
		return bdSumTransferenciaIngresoDiferencia;
	}

	public void setBdSumTransferenciaIngresoDiferencia(
			BigDecimal bdSumTransferenciaIngresoDiferencia) {
		this.bdSumTransferenciaIngresoDiferencia = bdSumTransferenciaIngresoDiferencia;
	}

	public List<PlanillaDescuento> getListaUnidadEjecutora() {
		return listaUnidadEjecutora;
	}

	public void setListaUnidadEjecutora(List<PlanillaDescuento> listaUnidadEjecutora) {
		this.listaUnidadEjecutora = listaUnidadEjecutora;
	}
	
}