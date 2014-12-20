package pe.com.tumi.reporte.operativo.credito.tesoreria.controller;
/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			15/12/2014     Christian De los Ríos        Creaciòn de componente         
*/

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.CommonUtils;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MyUtil;
import pe.com.tumi.common.util.PermisoUtil;
import pe.com.tumi.common.util.UtilManagerReport;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;
import pe.com.tumi.reporte.operativo.tesoreria.facade.IngresoCajaFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

/**
 * Clase Controller para el formulario de Ingreso Caja.
 * 
 * @author Bizarq
 */
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
	private List<SelectItem> listYears;
	private IngresoCaja ingresoCajaFiltro;
	private BigDecimal bdTotIngresoCaja;
	private BigDecimal bdTotDepositoBanco;
	private BigDecimal bdTotDifMontoTotal;
	
	private boolean mostrarMensajeExito;
	private boolean mostrarMensajeError;
	private String 	mensajeOperacion;
	private Integer intTipoIndFecha;
	private boolean mostrarRanFecha;
	private boolean mostrarPeriodoMensual;
	
	//CONTROLLER
	private Integer intErrorFiltros;
	private List<IngresoCaja> listaIngresosCaja;
	private List<IngresoCaja> listaDepositosCaja;
	
	IngresoCajaFacadeLocal ingresoCajaFacade;
	TablaFacadeRemote tablaFacade;
	PersonaFacadeRemote personaFacade;
	
	/**
	 * Constructor de la clase.
	 * 
	 */
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
	
	/**
	 * Metodo que inicializa el formulario.
	 * 
	 */
	public void inicio() {
		//LLENANDO COMBOS DEL FORMULARIO
		listJuridicaSubsucursal = new ArrayList<Subsucursal>();
		getListSucursales();
	}
	
	/**
	 * Metodo utilizado para buscar los ingresos con los filtros ingresados.
	 * 
	 */
	public void buscarIngresos() {
		bdTotIngresoCaja = BigDecimal.ZERO;
		bdTotDepositoBanco = BigDecimal.ZERO;
		try {
			if(ingresoCajaFiltro!=null){
				if(ingresoCajaFiltro.getIntIdSucursal()!=null && ingresoCajaFiltro.getIntIdSucursal()==0){
					mostrarMensaje(Boolean.FALSE, "Por favor, elija una Sucursal.");
					return;
				}else mostrarMensaje(Boolean.TRUE, "");
				if(intTipoIndFecha==null){
					mostrarMensaje(Boolean.FALSE, "Para continuar con la búsqueda, debe elegir un Rango de Fechas o un periodo mensual.");
					return;
				}else if(intTipoIndFecha!=null && intTipoIndFecha==1){
					if(ingresoCajaFiltro.getDtFecIni()==null || ingresoCajaFiltro.getDtFecFin()==null){
						mostrarMensaje(Boolean.FALSE, "Por favor, elija un rango de fechas para realizar la búsqueda.");
						return;
					}else mostrarMensaje(Boolean.TRUE, "");
				}else if(intTipoIndFecha!=null && intTipoIndFecha==2){
					if(ingresoCajaFiltro.getIntAnioIngreso()==null || ingresoCajaFiltro.getIntMesIngreso()==null){
						mostrarMensaje(Boolean.FALSE, "Para continuar con la búsqueda, debe elegir un mes u año");
						return;
					}else mostrarMensaje(Boolean.TRUE, "");
				}
				else {
					mostrarMensaje(Boolean.TRUE, "");
				}
			}
			
			ingresoCajaFiltro.setIntParaTipoDocGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_INGRESOCAJA);
			listaIngresosCaja = ingresoCajaFacade.getListaIngresosByTipoIngreso(ingresoCajaFiltro);
			ingresoCajaFiltro.setIntParaTipoDocGeneral(Constante.PARAM_T_DOCUMENTOGENERAL_DEPOSITOBANCO);
			listaDepositosCaja = ingresoCajaFacade.getListaIngresosByTipoIngreso(ingresoCajaFiltro);
			
			if(listaIngresosCaja!=null && !listaIngresosCaja.isEmpty()){
				for(IngresoCaja ingresoCaja : listaIngresosCaja){
					bdTotIngresoCaja = bdTotIngresoCaja.add(ingresoCaja.getBdMontoTotal());
				}
			}
			if(listaDepositosCaja!=null && !listaDepositosCaja.isEmpty()){
				for(IngresoCaja deposito : listaDepositosCaja){
					bdTotDepositoBanco = bdTotDepositoBanco.add(deposito.getBdMontoTotal());
				}
			}
			bdTotDifMontoTotal = bdTotIngresoCaja.subtract(bdTotDepositoBanco);
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * Metodo para visualizar los mensajes en el formulario.
	 * 
	 * @param exito, Indicador booleano.
	 * @param mensaje, Cadena a visualizar en el mensaje o alerta.
	 */
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
	
	/**
	 * Metodo para visualizar las fechas del formulario.
	 * 
	 */
	public void showDatesByIndicator(){
		if(intTipoIndFecha!=null && intTipoIndFecha==1){
			mostrarRanFecha = Boolean.FALSE;
			mostrarPeriodoMensual = Boolean.TRUE;
			ingresoCajaFiltro.setDtFecIni(null);
			ingresoCajaFiltro.setDtFecFin(null);
			ingresoCajaFiltro.setIntMesIngreso(null);
			ingresoCajaFiltro.setIntAnioIngreso(null);
		}
		
		if(intTipoIndFecha!=null && intTipoIndFecha==2){
			mostrarPeriodoMensual = Boolean.FALSE;
			mostrarRanFecha = Boolean.TRUE;
			ingresoCajaFiltro.setDtFecIni(null);
			ingresoCajaFiltro.setDtFecFin(null);
			ingresoCajaFiltro.setIntMesIngreso(null);
			ingresoCajaFiltro.setIntAnioIngreso(null);
		}
	}
	
	/**
	 * Metodo para limpiar los datos del formulario de consulta.
	 * 
	 */
	public String getLimpiarIngreso(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_INGRESOS);
		log.info("POSEE PERMISO" + poseePermiso);
		//poseePermiso = Boolean.TRUE;
		if(usuario!=null && poseePermiso){
			cargarValoresIniciales();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}
		return "";
	}
	
	/**
	 * Metodo para cargar los valores iniciales del formulario
	 * al iniciar por primera vez.
	 * 
	 */
	public void cargarValoresIniciales(){
		try{
			tablaFacade =  (TablaFacadeRemote) EJBFactory.getRemote(TablaFacadeRemote.class);
			ingresoCajaFacade =(IngresoCajaFacadeLocal)EJBFactory.getLocal(IngresoCajaFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote) EJBFactory.getRemote(PersonaFacadeRemote.class);
			listaIngresosCaja = new ArrayList<IngresoCaja>();
			listaDepositosCaja = new ArrayList<IngresoCaja>();
			ingresoCajaFiltro = new IngresoCaja();
			bdTotIngresoCaja = BigDecimal.ZERO;
			bdTotDepositoBanco = BigDecimal.ZERO;
			bdTotDifMontoTotal = BigDecimal.ZERO;
			mostrarRanFecha = Boolean.TRUE;
			mostrarPeriodoMensual = Boolean.TRUE;
			intTipoIndFecha = null;
			getListSucursales();
			listYears = CommonUtils.getListAnios(Constante.INT_INI_YEAR);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * Metodo para cargar los usuarios del sistema.
	 * 
	 */
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
	}
	
	/**
	 * Metodo que invoca la funcionalidad o llamada al reporte jasper del sistema.
	 * 
	 */
	public void printReport(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaTipoDocGeneral = null;
		String strDocGeneralTipoIngreso = "";
		Date dtFecIni = null;
		Date dtFecFin = null;
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
			
			if(intTipoIndFecha!=null && intTipoIndFecha==1){
				dtFecIni = ingresoCajaFiltro.getDtFecIni();
				dtFecFin = ingresoCajaFiltro.getDtFecFin();
			} else if(intTipoIndFecha!=null && intTipoIndFecha==2) {
				dtFecIni = MyUtil.getFirstDayOfMonth(ingresoCajaFiltro.getIntMesIngreso(), ingresoCajaFiltro.getIntAnioIngreso());
				dtFecFin = MyUtil.getLastDayOfMonth(ingresoCajaFiltro.getIntMesIngreso(), ingresoCajaFiltro.getIntAnioIngreso());
			} else {
				dtFecIni = null;
				dtFecFin = null;
			}
			
			parametro.put("P_TIPOINGRESODEPBANCO", strDocGeneralTipoIngreso);
			parametro.put("P_FECINI", Constante.sdf.format(dtFecIni));
			parametro.put("P_FECFIN", Constante.sdf.format(dtFecFin));
			parametro.put("P_LIST_DEPOSITOCAJA", listaDepositosCaja);
			
			strNombreReporte = "ingresosCaja";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaIngresosCaja), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirReporteIngresos ---> "+e);
		}
	}
	
	/**
	 * Metodo que recupera la lista de sucursales del sistema (para la empresa).
	 * 
	 */
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

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public List<IngresoCaja> getListaDepositosCaja() {
		return listaDepositosCaja;
	}

	public void setListaDepositosCaja(List<IngresoCaja> listaDepositosCaja) {
		this.listaDepositosCaja = listaDepositosCaja;
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

	public BigDecimal getBdTotIngresoCaja() {
		return bdTotIngresoCaja;
	}

	public void setBdTotIngresoCaja(BigDecimal bdTotIngresoCaja) {
		this.bdTotIngresoCaja = bdTotIngresoCaja;
	}

	public BigDecimal getBdTotDepositoBanco() {
		return bdTotDepositoBanco;
	}

	public void setBdTotDepositoBanco(BigDecimal bdTotDepositoBanco) {
		this.bdTotDepositoBanco = bdTotDepositoBanco;
	}

	public BigDecimal getBdTotDifMontoTotal() {
		return bdTotDifMontoTotal;
	}

	public void setBdTotDifMontoTotal(BigDecimal bdTotDifMontoTotal) {
		this.bdTotDifMontoTotal = bdTotDifMontoTotal;
	}

	public Integer getIntTipoIndFecha() {
		return intTipoIndFecha;
	}

	public void setIntTipoIndFecha(Integer intTipoIndFecha) {
		this.intTipoIndFecha = intTipoIndFecha;
	}

	public boolean isMostrarRanFecha() {
		return mostrarRanFecha;
	}

	public void setMostrarRanFecha(boolean mostrarRanFecha) {
		this.mostrarRanFecha = mostrarRanFecha;
	}

	public boolean isMostrarPeriodoMensual() {
		return mostrarPeriodoMensual;
	}

	public void setMostrarPeriodoMensual(boolean mostrarPeriodoMensual) {
		this.mostrarPeriodoMensual = mostrarPeriodoMensual;
	}
}