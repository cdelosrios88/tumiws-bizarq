package pe.com.tumi.reporte.operativo.credito.servicios.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Servicio;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.ServicioFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class PrestamoController {
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
	private ServicioFacadeLocal servicioFacade;
	private List<Tabla> listaDescripcionMes;
	private List<Tabla> listaDescripcionTipoSocio;
	private List<Tabla> listaDescripcionModalidad;
	
	//Show Panels
	private Boolean blnShowPanelTabCaptaciones;
	private Boolean blnShowPanelTabRenuncias;
	private Boolean blnShowPanelTabPadronTrabajadores;
	private Boolean blnShowPanelTabConvenios;
	
	//Filtros de Búsqueda
	private Integer intErrorFiltros;
	
	private Integer intIdSucursal;
	private List<Sucursal> listJuridicaSucursal;
	private Integer intIdSubSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	
	private String rbPeriodo;
	private String rbPeriodoOpcA;
	private String rbPeriodoOpcB;
	
	private Integer intMesDesde;
	private Integer intMesHasta;
	private List<SelectItem> listYears;
	private Integer intAnioBusqueda;
	private Integer intRangoPeriodo;
	
	private Boolean blnDisabledMesDesde;
	private Boolean blnDisabledMesHasta;
	private Boolean blnDisabledAnioBusqueda;
	private Boolean blnDisabledRangoPeriodo;
	
	private List<Servicio> listaCaptaciones;
	
	private Servicio beanServicio;	
	//Totales
	private BigDecimal bdSumatoriaProyectado;
	private BigDecimal bdSumatoriaEjecutado;
	private BigDecimal bdSumatoriaDiferencia;
	private Integer intSumatoriaAltas;
	private Integer intSumatoriaBajas;
	private Integer intSumatoriaTotal;
	
	//Detalle Captaciones/Renuncias Ejecutadas
	private String strFmtPeriodo;
	private String strSucursal;
	private String strTipoSocio;
	private String strModalidad;
	private List<Servicio> listaDetallePrestamos;
	
	private Formatter fmt = new Formatter();
	
	private Integer intDepartamento;
	private Boolean blnDisabledDepartamento;
	private Boolean blnDisabledSucursal;
	
	private Boolean blnRefinanciados;
	
	public PrestamoController(){
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
		beanServicio = new Servicio();
		
		listaCaptaciones = new ArrayList<Servicio>();
		listaDetallePrestamos = new ArrayList<Servicio>();
		
		listJuridicaSubsucursal = new ArrayList<Subsucursal>();
		getListAnios();
		getListSucursales();		
	}
	
	public void disabledCboPeriodo() {
		log.info("-------------------------------------Debugging AsociativoController.disabledCboPeriodo-------------------------------------");
		log.info("pRbPeriodo: "+getRequestParameter("pRbPeriodo"));
		String pFiltroRadio = getRequestParameter("pRbPeriodo");
		if (pFiltroRadio.equals("1")) {
			rbPeriodoOpcA = "1";
			rbPeriodoOpcB = null;
			intMesDesde = 0;
			intMesHasta = 0;
			intAnioBusqueda = 0;
			intRangoPeriodo = 0;
			blnDisabledMesDesde = Boolean.FALSE;
			blnDisabledMesHasta = Boolean.FALSE;
			blnDisabledAnioBusqueda = Boolean.FALSE;
			blnDisabledRangoPeriodo = Boolean.TRUE;
		}
		if (pFiltroRadio.equals("2")) {			
			rbPeriodoOpcA = null;
			rbPeriodoOpcB = "2";
			intMesDesde = 0;
			intMesHasta = 0;
			intAnioBusqueda = 0;
			intRangoPeriodo = 0;
			blnDisabledMesDesde = Boolean.TRUE;
			blnDisabledMesHasta = Boolean.TRUE;
			blnDisabledAnioBusqueda = Boolean.TRUE;
			blnDisabledRangoPeriodo = Boolean.FALSE;
		}
	}
	
	public void disabledCboBusqPadrones() {
		log.info("-------------------------------------Debugging AsociativoController.disabledCboBusqPadrones-------------------------------------");
		log.info("pRbBusqueda: "+getRequestParameter("pRbBusqueda"));
		String pFiltroRadio = getRequestParameter("pRbBusqueda");
		if (pFiltroRadio.equals("1")) {
			rbPeriodoOpcA = "1";
			rbPeriodoOpcB = null;
			intIdSucursal = 0;
			intDepartamento = 0;
			blnDisabledSucursal = Boolean.FALSE;
			blnDisabledDepartamento = Boolean.TRUE;
		}
		if (pFiltroRadio.equals("2")) {			
			rbPeriodoOpcA = null;
			rbPeriodoOpcB = "2";
			intIdSucursal = 0;
			intDepartamento = 0;
			blnDisabledSucursal = Boolean.TRUE;
			blnDisabledDepartamento = Boolean.FALSE;
		}
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
	
	public void consultarPrestamo() {
		List<Servicio> listaCantProy = null;
		List<Servicio> listaCantEjec = null;
		
		listaCaptaciones.clear();
		bdSumatoriaProyectado = BigDecimal.ZERO;
		bdSumatoriaEjecutado = BigDecimal.ZERO;
		bdSumatoriaDiferencia = BigDecimal.ZERO;
		
		Integer periodoDesde = 0;
		Integer periodoHasta = 0;
		String strRefinanciados = "";
		
		try {
			if (isValidFiltros() == false) {
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("Los filtros de Sucursal, Sub Sucursal y Periodo no pueden estar vacíos. " +
						"Verifique.");
				return;
			}
			
			if (rbPeriodoOpcA!=null && rbPeriodoOpcA.equals("1")) {
				periodoDesde = concatPeriodo(intAnioBusqueda, intMesDesde);
				periodoHasta = concatPeriodo(intAnioBusqueda, intMesHasta);
			}
			if (rbPeriodoOpcB!=null && rbPeriodoOpcB.equals("2")) {
				periodoDesde = filtrarPorPeriodoYMes();
				periodoHasta = concatPeriodo(Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)+1);
			}
			
			strRefinanciados = (blnRefinanciados?"1,5":"1");
			log.info("strRefinanciados: " + strRefinanciados);
			
			listaCantProy = servicioFacade.getListaCantidadProyectadas(SESION_IDEMPRESA, intIdSucursal, intIdSubSucursal, Constante.PARAM_T_TIPOINDICADOR_COLOCACIONES, periodoDesde, periodoHasta);
			listaCantEjec = servicioFacade.getListaCantidadCaptacionesEjecuadas(SESION_IDEMPRESA, intIdSucursal, intIdSubSucursal, periodoDesde, periodoHasta, strRefinanciados);
			
			if (listaCantProy != null && !listaCantProy.isEmpty()) {
				for (Servicio proyectado : listaCantProy) {
					//Generales...
					beanServicio.setIntPersEmpresaPk(SESION_IDEMPRESA);
					beanServicio.setIntIdSucursal(intIdSucursal);
					beanServicio.setIntIdSubSucursal(intIdSubSucursal);
					
					beanServicio.setIntPeriodo(proyectado.getIntPeriodo());
					beanServicio.setStrFmtPeriodo(formatoFecha(Integer.valueOf(proyectado.getIntPeriodo().toString().substring(0, 4)),Integer.valueOf(proyectado.getIntPeriodo().toString().substring(4, 6))));
					beanServicio.setBdProyectado(proyectado.getBdProyectado());
					//
					if (listaCantEjec!=null && !listaCantEjec.isEmpty()) {
						for (Servicio ejecutado : listaCantEjec) {
							if (ejecutado.getIntPeriodo().equals(proyectado.getIntPeriodo())) {
								beanServicio.setBdMontoEjecutado(ejecutado.getBdMontoEjecutado());
								break;
							}
						}
					}
					beanServicio.setBdDiferencia((beanServicio.getBdMontoEjecutado()!=null?beanServicio.getBdMontoEjecutado():BigDecimal.ZERO).subtract(beanServicio.getBdProyectado()));
					//
					listaCaptaciones.add(beanServicio);
					//
					bdSumatoriaProyectado = bdSumatoriaProyectado.add(beanServicio.getBdProyectado());
					bdSumatoriaEjecutado = bdSumatoriaEjecutado.add((beanServicio.getBdMontoEjecutado()!=null?beanServicio.getBdMontoEjecutado():BigDecimal.ZERO));
					bdSumatoriaDiferencia = bdSumatoriaDiferencia.add(beanServicio.getBdDiferencia());
					beanServicio = new Servicio();
				}
			}
		} catch (Exception e) {
			log.error("Error en consultarCaptaciones ---> "+e);
		}
	}
	
	public Integer filtrarPorPeriodoYMes(){
		GregorianCalendar gcFechaReferencia = new GregorianCalendar();
		
		if(intRangoPeriodo.equals(0)){
//				return listaFilas;
		}else if(intRangoPeriodo.equals(Constante.PARAM_T_FREQCONSULTA_TRESMESES)){
			gcFechaReferencia.add(Calendar.MONTH, -2);
		}else if(intRangoPeriodo.equals(Constante.PARAM_T_FREQCONSULTA_SEISMESES)){
			gcFechaReferencia.add(Calendar.MONTH, -5);
		}else if(intRangoPeriodo.equals(Constante.PARAM_T_FREQCONSULTA_UNANIO)){
			gcFechaReferencia.add(Calendar.MONTH, -11);
		}else if(intRangoPeriodo.equals(Constante.PARAM_T_FREQCONSULTA_DOSANIOS)){
			gcFechaReferencia.add(Calendar.MONTH, -23);
		}
		
		Integer periodoDesde = concatPeriodo(gcFechaReferencia.get(Calendar.YEAR),gcFechaReferencia.get(Calendar.MONTH));

		return periodoDesde;
	}
	
	public String formatoFecha(Integer anio, Integer mes) {
		String strFch="";
		for (Tabla descMes : listaDescripcionMes) {
			if(descMes.getIntIdDetalle().equals(mes)){
				strFch = descMes.getStrDescripcion()+" - "+anio;
				break;
			}
		}
		return strFch;
	}
	
	public void limpiarTabCaptaciones() {
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		
		rbPeriodoOpcA = "1";
		rbPeriodoOpcB = null;
		blnDisabledMesDesde = Boolean.FALSE;
		blnDisabledMesHasta = Boolean.FALSE;
		blnDisabledAnioBusqueda = Boolean.FALSE;
		blnDisabledRangoPeriodo = Boolean.TRUE;
		
		intMesDesde = 0;
		intMesHasta = 0;
		intAnioBusqueda = 0;
		intRangoPeriodo = 0;
		listaCaptaciones.clear();
		listaDetallePrestamos.clear();
	}
	
	public void limpiarTabRenuncias() {
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		
		rbPeriodoOpcA = "1";
		rbPeriodoOpcB = null;
		blnDisabledMesDesde = Boolean.FALSE;
		blnDisabledMesHasta = Boolean.FALSE;
		blnDisabledAnioBusqueda = Boolean.FALSE;
		blnDisabledRangoPeriodo = Boolean.TRUE;
		
		intMesDesde = 0;
		intMesHasta = 0;
		intAnioBusqueda = 0;
		intRangoPeriodo = 0;
	}
	
	public void limpiarTabPadrones() {
		intIdSucursal = 0;
		intDepartamento = 0;
		
		rbPeriodoOpcA = "1";
		rbPeriodoOpcB = null;
		
		blnDisabledSucursal = Boolean.FALSE;
		blnDisabledDepartamento = Boolean.TRUE;
	}
	
	public void limpiarTabConvenios() {
		intIdSucursal = 0;
	}
	
	public void limpiarFormulario() {
		listJuridicaSubsucursal.clear();
		
		limpiarTabCaptaciones();
		limpiarTabRenuncias();
		limpiarTabPadrones();
		limpiarTabConvenios();
	}
	
	public void imprimirPrestamo(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		String strPeriodo = "";
		Tabla tablaMesDesde = null;
		Tabla tablaMesHasta = null;
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			if (rbPeriodoOpcA!=null && rbPeriodoOpcA.equals("1")) {
				tablaMesDesde = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MES_CALENDARIO), intMesDesde);
				tablaMesHasta = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MES_CALENDARIO), intMesHasta);
				strPeriodo = tablaMesDesde.getStrDescripcion() + " - " + tablaMesHasta.getStrDescripcion() + " / " + intAnioBusqueda;
			}
			if (rbPeriodoOpcB!=null && rbPeriodoOpcB.equals("2")) {
				if(intRangoPeriodo.equals(Constante.PARAM_T_FREQCONSULTA_TRESMESES)){
					strPeriodo = "3 meses";
				}else if(intRangoPeriodo.equals(Constante.PARAM_T_FREQCONSULTA_SEISMESES)){
					strPeriodo = "6 meses";
				}else if(intRangoPeriodo.equals(Constante.PARAM_T_FREQCONSULTA_UNANIO)){
					strPeriodo = "1 año";
				}else if(intRangoPeriodo.equals(Constante.PARAM_T_FREQCONSULTA_DOSANIOS)){
					strPeriodo = "2 años";
				}else {
					strPeriodo = "";
				} 
			}
			
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_PERIODO", strPeriodo);
			parametro.put("P_REFINANCIADO", (blnRefinanciados?"SI":"NO"));
			
			strNombreReporte = "prestamo";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaCaptaciones), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirReporteCaptaciones ---> "+e);
		}
	}
	
	public Boolean isValidFiltros() {
		Boolean validFiltros = true;
		intErrorFiltros = 0;
		try {
			//1. Validación de Sucursal
			if (intIdSucursal.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			//2. Validación de Sub Sucursal
			if (intIdSubSucursal.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			//3. Validación de Periodo
			
			//3.1 Por mes desde - hasta y año
			if (rbPeriodoOpcA!=null && rbPeriodoOpcA.equals("1") && (intMesDesde.equals(0) || intMesHasta.equals(0) || intAnioBusqueda.equals(0))) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			//3.1.1 Verificar mes desde - hasta
			if (rbPeriodoOpcA!=null && rbPeriodoOpcA.equals("1") && (intMesDesde >= intMesHasta)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			
			//3.2 Por frequencia de meses
			if (rbPeriodoOpcB!=null && rbPeriodoOpcB.equals("2") && intRangoPeriodo.equals(0)) {
				validFiltros = false;
				intErrorFiltros = 1;
			}
			//4. Elección de uno de los tipo de busqueda por perodo
			if (rbPeriodoOpcA==null && rbPeriodoOpcB==null) {
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
	
	public void detalleCaptacionesEjecutadas(ActionEvent event) throws BusinessException{
		Servicio ejecutado = null;
		listaDetallePrestamos.clear();

		try {
			ejecutado = (Servicio)event.getComponent().getAttributes().get("itemGrilla");
			//Cabecera
			strFmtPeriodo = ejecutado.getStrFmtPeriodo();
			for (Sucursal descSuc : listJuridicaSucursal) {
				if(descSuc.getId().getIntIdSucursal().equals(ejecutado.getIntIdSucursal())){
					strSucursal = descSuc.getJuridica().getStrRazonSocial();
					break;
				}
			}
			listaDetallePrestamos = servicioFacade.getCaptacionesEjecutadasDetalle(ejecutado.getIntPersEmpresaPk(),ejecutado.getIntIdSucursal(),
																						ejecutado.getIntIdSubSucursal(),ejecutado.getIntPeriodo(),
																						null,null);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public String getLimpiarPrestamo(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_PRESTAMOS);
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
			
			listaCaptaciones = new ArrayList<Servicio>();
			getListSucursales();
			getListAnios();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
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
		limpiarFormulario();
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

	public Boolean getBlnShowPanelTabCaptaciones() {
		return blnShowPanelTabCaptaciones;
	}

	public void setBlnShowPanelTabCaptaciones(Boolean blnShowPanelTabCaptaciones) {
		this.blnShowPanelTabCaptaciones = blnShowPanelTabCaptaciones;
	}

	public Boolean getBlnShowPanelTabRenuncias() {
		return blnShowPanelTabRenuncias;
	}

	public void setBlnShowPanelTabRenuncias(Boolean blnShowPanelTabRenuncias) {
		this.blnShowPanelTabRenuncias = blnShowPanelTabRenuncias;
	}

	public Boolean getBlnShowPanelTabPadronTrabajadores() {
		return blnShowPanelTabPadronTrabajadores;
	}

	public void setBlnShowPanelTabPadronTrabajadores(
			Boolean blnShowPanelTabPadronTrabajadores) {
		this.blnShowPanelTabPadronTrabajadores = blnShowPanelTabPadronTrabajadores;
	}

	public Boolean getBlnShowPanelTabConvenios() {
		return blnShowPanelTabConvenios;
	}

	public void setBlnShowPanelTabConvenios(Boolean blnShowPanelTabConvenios) {
		this.blnShowPanelTabConvenios = blnShowPanelTabConvenios;
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

	public String getRbPeriodo() {
		return rbPeriodo;
	}

	public void setRbPeriodo(String rbPeriodo) {
		this.rbPeriodo = rbPeriodo;
	}

	public Integer getIntMesDesde() {
		return intMesDesde;
	}

	public void setIntMesDesde(Integer intMesDesde) {
		this.intMesDesde = intMesDesde;
	}

	public Integer getIntMesHasta() {
		return intMesHasta;
	}

	public void setIntMesHasta(Integer intMesHasta) {
		this.intMesHasta = intMesHasta;
	}

	public Integer getIntAnioBusqueda() {
		return intAnioBusqueda;
	}

	public void setIntAnioBusqueda(Integer intAnioBusqueda) {
		this.intAnioBusqueda = intAnioBusqueda;
	}

	public Boolean getBlnDisabledMesDesde() {
		return blnDisabledMesDesde;
	}

	public void setBlnDisabledMesDesde(Boolean blnDisabledMesDesde) {
		this.blnDisabledMesDesde = blnDisabledMesDesde;
	}

	public Boolean getBlnDisabledMesHasta() {
		return blnDisabledMesHasta;
	}

	public void setBlnDisabledMesHasta(Boolean blnDisabledMesHasta) {
		this.blnDisabledMesHasta = blnDisabledMesHasta;
	}

	public Boolean getBlnDisabledAnioBusqueda() {
		return blnDisabledAnioBusqueda;
	}

	public void setBlnDisabledAnioBusqueda(Boolean blnDisabledAnioBusqueda) {
		this.blnDisabledAnioBusqueda = blnDisabledAnioBusqueda;
	}

	public Boolean getBlnDisabledRangoPeriodo() {
		return blnDisabledRangoPeriodo;
	}

	public void setBlnDisabledRangoPeriodo(Boolean blnDisabledRangoPeriodo) {
		this.blnDisabledRangoPeriodo = blnDisabledRangoPeriodo;
	}

	public Integer getIntRangoPeriodo() {
		return intRangoPeriodo;
	}

	public void setIntRangoPeriodo(Integer intRangoPeriodo) {
		this.intRangoPeriodo = intRangoPeriodo;
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

	public ServicioFacadeLocal getServicioFacade() {
		return servicioFacade;
	}

	public void setServicioFacade(ServicioFacadeLocal servicioFacade) {
		this.servicioFacade = servicioFacade;
	}

	public List<Tabla> getListaDescripcionMes() {
		return listaDescripcionMes;
	}

	public void setListaDescripcionMes(List<Tabla> listaDescripcionMes) {
		this.listaDescripcionMes = listaDescripcionMes;
	}

	public Servicio getBeanServicio() {
		return beanServicio;
	}

	public void setBeanServicio(Servicio beanServicio) {
		this.beanServicio = beanServicio;
	}

	public List<Servicio> getListaDetallePrestamos() {
		return listaDetallePrestamos;
	}

	public void setListaDetallePrestamos(List<Servicio> listaDetallePrestamos) {
		this.listaDetallePrestamos = listaDetallePrestamos;
	}

	public BigDecimal getBdSumatoriaProyectado() {
		return bdSumatoriaProyectado;
	}

	public void setBdSumatoriaProyectado(BigDecimal bdSumatoriaProyectado) {
		this.bdSumatoriaProyectado = bdSumatoriaProyectado;
	}

	public BigDecimal getBdSumatoriaEjecutado() {
		return bdSumatoriaEjecutado;
	}

	public void setBdSumatoriaEjecutado(BigDecimal bdSumatoriaEjecutado) {
		this.bdSumatoriaEjecutado = bdSumatoriaEjecutado;
	}

	public BigDecimal getBdSumatoriaDiferencia() {
		return bdSumatoriaDiferencia;
	}

	public void setBdSumatoriaDiferencia(BigDecimal bdSumatoriaDiferencia) {
		this.bdSumatoriaDiferencia = bdSumatoriaDiferencia;
	}

	public Integer getIntSumatoriaAltas() {
		return intSumatoriaAltas;
	}

	public void setIntSumatoriaAltas(Integer intSumatoriaAltas) {
		this.intSumatoriaAltas = intSumatoriaAltas;
	}

	public Integer getIntSumatoriaTotal() {
		return intSumatoriaTotal;
	}

	public void setIntSumatoriaTotal(Integer intSumatoriaTotal) {
		this.intSumatoriaTotal = intSumatoriaTotal;
	}

	public String getRbPeriodoOpcA() {
		return rbPeriodoOpcA;
	}

	public void setRbPeriodoOpcA(String rbPeriodoOpcA) {
		this.rbPeriodoOpcA = rbPeriodoOpcA;
	}

	public String getRbPeriodoOpcB() {
		return rbPeriodoOpcB;
	}

	public void setRbPeriodoOpcB(String rbPeriodoOpcB) {
		this.rbPeriodoOpcB = rbPeriodoOpcB;
	}

	public String getStrFmtPeriodo() {
		return strFmtPeriodo;
	}

	public void setStrFmtPeriodo(String strFmtPeriodo) {
		this.strFmtPeriodo = strFmtPeriodo;
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

	public String getStrSucursal() {
		return strSucursal;
	}

	public void setStrSucursal(String strSucursal) {
		this.strSucursal = strSucursal;
	}

	public String getStrTipoSocio() {
		return strTipoSocio;
	}

	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}

	public String getStrModalidad() {
		return strModalidad;
	}

	public void setStrModalidad(String strModalidad) {
		this.strModalidad = strModalidad;
	}

	public List<Servicio> getListaCaptaciones() {
		return listaCaptaciones;
	}

	public void setListaCaptaciones(List<Servicio> listaCaptaciones) {
		this.listaCaptaciones = listaCaptaciones;
	}

	public Integer getIntSumatoriaBajas() {
		return intSumatoriaBajas;
	}

	public void setIntSumatoriaBajas(Integer intSumatoriaBajas) {
		this.intSumatoriaBajas = intSumatoriaBajas;
	}

	public Formatter getFmt() {
		return fmt;
	}

	public void setFmt(Formatter fmt) {
		this.fmt = fmt;
	}

	public Integer getIntDepartamento() {
		return intDepartamento;
	}

	public void setIntDepartamento(Integer intDepartamento) {
		this.intDepartamento = intDepartamento;
	}

	public Boolean getBlnDisabledDepartamento() {
		return blnDisabledDepartamento;
	}

	public void setBlnDisabledDepartamento(Boolean blnDisabledDepartamento) {
		this.blnDisabledDepartamento = blnDisabledDepartamento;
	}

	public Boolean getBlnDisabledSucursal() {
		return blnDisabledSucursal;
	}

	public void setBlnDisabledSucursal(Boolean blnDisabledSucursal) {
		this.blnDisabledSucursal = blnDisabledSucursal;
	}

	public Boolean getBlnRefinanciados() {
		return blnRefinanciados;
	}

	public void setBlnRefinanciados(Boolean blnRefinanciados) {
		this.blnRefinanciados = blnRefinanciados;
	}

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}
}
