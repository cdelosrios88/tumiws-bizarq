package pe.com.tumi.reporte.operativo.credito.servicios.controller;

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
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Prevision;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Servicio;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.PrevisionFacadeLocal;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.ServicioFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class PrimerDescuentoController {
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
	private Integer intErrorFiltros;
	
	private List<Sucursal> listJuridicaSucursal;
	private List<Subsucursal> listJuridicaSubsucursal;
	private List<Tabla> listaTipoPrestamo;
	private List<Tabla> listaTipoCreditoEmpresa;
	private Integer intParaTipoSucursal;
	private Integer intIdSucursal;
	private Integer intIdSubSucursal;
	private Integer intTipoServicio;
	private Integer intTipoCreditoEmpresa;
	private Integer intIdTipoDiferencia;
	private Integer intIdTipoFecha;
	private Integer intIdMes;
	private Integer intIdAnio;
	private BigDecimal bdMontoMin;
	private BigDecimal bdMontoMax;
	private Boolean blnReprestamo;
	
	private BigDecimal bdTotMontoSolicitud = null;
	private BigDecimal bdTotDiferenciaPlla = null;
	private BigDecimal bdTotEnviado = null;
	private BigDecimal bdTotEfectuado = null;
	
	//CONTROLLER
	private List<SelectItem> listYears;
	private List<Servicio> listaPrimerDescuento;
	
	ServicioFacadeLocal servicioFacade;
	TablaFacadeRemote tablaFacade;
	
	public PrimerDescuentoController(){
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
		getListAnios();
		getListTipoPrestamo();
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
		} catch (Exception e) {
			log.error("Error en isValidFiltros ---> "+e);
		}
		return validFiltros;
	}
	
	public void consultarPrimerDscto() {
		List<Servicio> listaAnalisisPrimerDscto = null;
		
		if(intIdTipoFecha!= null && intIdTipoFecha== 2) intIdAnio = 0;
		
		if(intParaTipoSucursal==0)intParaTipoSucursal=null;
		if(intIdSucursal==0)intIdSucursal=null;
		if(intIdSubSucursal==0)intIdSubSucursal=null;
		if(intTipoServicio==0)intTipoServicio=null;
		if(intTipoCreditoEmpresa==0)intTipoCreditoEmpresa=null;
		if(intIdTipoDiferencia==0)intIdTipoDiferencia=null;
		if(intIdTipoFecha==0)intIdTipoFecha=null;
		if(intIdMes==0)intIdMes=null;
		if(intIdAnio==0)intIdAnio=null;
		if(bdMontoMin!=null && bdMontoMin.equals(BigDecimal.ZERO))bdMontoMin=null;
		if(bdMontoMax!=null && bdMontoMax.equals(BigDecimal.ZERO))bdMontoMax=null;
		String strReprestamo = blnReprestamo ?"1":null;
		
		bdTotMontoSolicitud = BigDecimal.ZERO;
		bdTotDiferenciaPlla = BigDecimal.ZERO;
		bdTotEnviado = BigDecimal.ZERO;
		bdTotEfectuado = BigDecimal.ZERO;
		
		try {
			if (!isValidFiltros()) {
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("Los filtros de Sucursal y Sub Sucursal no pueden estar vacíos. " +
						"Verifique.");
				return;
			}
			
			listaAnalisisPrimerDscto = servicioFacade.getListaPrimerDscto(
					intParaTipoSucursal, intIdSucursal, intIdSubSucursal, 
					intTipoServicio, intTipoCreditoEmpresa,
					intIdTipoFecha, intIdMes, intIdAnio, 
					bdMontoMin, bdMontoMax, strReprestamo, intIdTipoDiferencia);
			
			if(listaAnalisisPrimerDscto!=null && !listaAnalisisPrimerDscto.isEmpty()){
				for(Servicio servicio : listaAnalisisPrimerDscto){
					if(servicio.getBdMontoSolicitudPrimerDscto()!=null){
						bdTotMontoSolicitud = bdTotMontoSolicitud.add(servicio.getBdMontoSolicitudPrimerDscto());
					}
					if(servicio.getBdDiferenciaDscto()!=null){
						bdTotDiferenciaPlla = bdTotDiferenciaPlla.add(servicio.getBdDiferenciaDscto());
					}
					if(servicio.getBdDsctoEnviado()!=null){
						bdTotEnviado = bdTotEnviado.add(servicio.getBdDsctoEnviado());
					}
					if(servicio.getBdDsctoEfectuado()!=null){
						bdTotEfectuado = bdTotEfectuado.add(servicio.getBdDsctoEfectuado());
					}
				}
			}
			listaPrimerDescuento = listaAnalisisPrimerDscto;
			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	public void reloadCboTipoPrestamo(){
		Integer intTipoServicio = null;
		try {
			intTipoServicio = Integer.valueOf(getRequestParameter("pCboTipoPrestamo"));
			TablaFacadeRemote remote = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
		    listaTipoCreditoEmpresa = remote.getListaTablaPorAgrupamientoB(new Integer(Constante.PARAM_T_TIPOCREDITOEMPRESA), intTipoServicio);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}
	
	public String getLimpiarPrimerDescuento(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_PRIMERDESCUENTO);
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
			
			listaPrimerDescuento = new ArrayList<Servicio>();
			getListSucursales();
			getListAnios();
			limpiarMainForm();
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	private void cargarUsuario(){
		usuario = (Usuario)getRequest().getSession().getAttribute("usuario");		
		PERSONA_USUARIO = usuario.getIntPersPersonaPk();
		EMPRESA_USUARIO = usuario.getPerfil().getId().getIntPersEmpresaPk();
	}
	
	private void limpiarMainForm(){
		intParaTipoSucursal=null;
		intIdSucursal=0;
		intIdSubSucursal=0;
		intTipoServicio=null;
		intTipoCreditoEmpresa=null;
		intIdTipoDiferencia=0;
		intIdTipoFecha=null;
		intIdMes=null;
		intIdAnio=null;
		bdMontoMin=null;
		bdMontoMax=null;
	}
	
	public void imprimirPrimerDscto(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaTipoServicio = null;
		Tabla tablaTipoCreditoEmpresa = null;
		String strTipoCreditoEmpresa = "";
		String strTipoServicio = "";
		Tabla tablaTipoSucursal = null;
		String strTipoSucursal = "";
		String strTipoDiferencia = null;
		String strMes = null;
		String strFecha = null;
		Tabla tablaMes = null;
		Sucursal sucursalAtencion = null;
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			tablaTipoSucursal = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOSUCURSAL), intParaTipoSucursal);
			tablaTipoServicio = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPO_CREDITO), intTipoServicio);
			tablaTipoCreditoEmpresa = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_TIPOCREDITOEMPRESA), intTipoCreditoEmpresa);
			tablaMes = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MES_CALENDARIO), intIdMes);
			
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			if(tablaTipoSucursal!=null){
				strTipoSucursal = tablaTipoSucursal.getStrDescripcion();
			}
			if(tablaTipoServicio!=null){
				strTipoServicio = tablaTipoServicio.getStrDescripcion();
			}
			if(tablaTipoCreditoEmpresa!=null){
				strTipoCreditoEmpresa = tablaTipoCreditoEmpresa.getStrDescripcion();
			}
			if(tablaMes!=null){
				strMes = tablaMes.getStrDescripcion();
			}
			switch (intIdTipoDiferencia) {
			case 1:
				strTipoDiferencia = "Descuento Normal";
				break;
			case 2:
				strTipoDiferencia = "Diferencia de Menos";
				break;
			case 3:
				strTipoDiferencia = "Exceso de Descuento";
				break;
			case 4:
				strTipoDiferencia = "No descontados";
				break;
			default:
				strTipoDiferencia = "";
				break;
			}
			
			switch (intIdTipoFecha) {
			case 1:
				strFecha = "Solicitud del Préstamo";
				break;
			case 2:
				strFecha = "Primer Descuento";
				break;
			default:
				strFecha = "";
				break;
			}
			
			parametro.put("P_TIPOSUCURSAL", strTipoSucursal);
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_TIPOSERVICIO", strTipoServicio);
			parametro.put("P_TIPOCREDITOEMPRESA", strTipoCreditoEmpresa);
			parametro.put("P_TIPODIFERENCIA", strTipoDiferencia);
			parametro.put("P_FECHA", strFecha);
			parametro.put("P_MES", strMes);
			parametro.put("P_ANIO", "");
			parametro.put("P_MONTOMIN", bdMontoMin);
			parametro.put("P_MONTOMAX", bdMontoMax);
			parametro.put("P_REPRESTAMO", blnReprestamo==Boolean.TRUE?"Si":"No");
			
			strNombreReporte = "primerDescuento";
			
			for(Servicio servicio : listaPrimerDescuento){
				if(servicio.getIntIdSucursalSocio()!=null)sucursal = empresaFacade.getSucursalPorId(servicio.getIntIdSucursalSocio());
				if(servicio.getIntIdSucursalAtencion()!=null)sucursalAtencion = empresaFacade.getSucursalPorId(servicio.getIntIdSucursalAtencion());
				if(sucursal!=null)servicio.setStrSucursalSocio(sucursal.getJuridica().getStrRazonSocial());
				if(sucursalAtencion!=null)servicio.setStrSucursalAtencion(sucursalAtencion.getJuridica().getStrRazonSocial());
			}
			
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaPrimerDescuento), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			log.error("Error en imprimirReporteCaptaciones ---> "+e);
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
	
	public void getListTipoPrestamo() {
		try {
			if(listaTipoPrestamo == null){
				listaTipoPrestamo = tablaFacade.getListaTablaPorAgrupamientoA(Integer.valueOf(Constante.PARAM_T_TIPO_CREDITO), "A");
			}
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

	public List<Servicio> getListaPrimerDescuento() {
		return listaPrimerDescuento;
	}

	public void setListaPrimerDescuento(List<Servicio> listaPrimerDescuento) {
		this.listaPrimerDescuento = listaPrimerDescuento;
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

	public List<SelectItem> getListYears() {
		return listYears;
	}

	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		PrimerDescuentoController.log = log;
	}

	public Integer getIntParaTipoSucursal() {
		return intParaTipoSucursal;
	}

	public void setIntParaTipoSucursal(Integer intParaTipoSucursal) {
		this.intParaTipoSucursal = intParaTipoSucursal;
	}

	public Integer getIntIdTipoDiferencia() {
		return intIdTipoDiferencia;
	}

	public void setIntIdTipoDiferencia(Integer intIdTipoDiferencia) {
		this.intIdTipoDiferencia = intIdTipoDiferencia;
	}

	public Integer getIntIdTipoFecha() {
		return intIdTipoFecha;
	}

	public void setIntIdTipoFecha(Integer intIdTipoFecha) {
		this.intIdTipoFecha = intIdTipoFecha;
	}

	public Integer getIntIdMes() {
		return intIdMes;
	}

	public void setIntIdMes(Integer intIdMes) {
		this.intIdMes = intIdMes;
	}

	public Integer getIntIdAnio() {
		return intIdAnio;
	}

	public void setIntIdAnio(Integer intIdAnio) {
		this.intIdAnio = intIdAnio;
	}

	public BigDecimal getBdMontoMin() {
		return bdMontoMin;
	}

	public void setBdMontoMin(BigDecimal bdMontoMin) {
		this.bdMontoMin = bdMontoMin;
	}

	public BigDecimal getBdMontoMax() {
		return bdMontoMax;
	}

	public void setBdMontoMax(BigDecimal bdMontoMax) {
		this.bdMontoMax = bdMontoMax;
	}

	public Boolean getBlnReprestamo() {
		return blnReprestamo;
	}

	public void setBlnReprestamo(Boolean blnReprestamo) {
		this.blnReprestamo = blnReprestamo;
	}

	public ServicioFacadeLocal getServicioFacade() {
		return servicioFacade;
	}

	public void setServicioFacade(ServicioFacadeLocal servicioFacade) {
		this.servicioFacade = servicioFacade;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public BigDecimal getBdTotMontoSolicitud() {
		return bdTotMontoSolicitud;
	}

	public void setBdTotMontoSolicitud(BigDecimal bdTotMontoSolicitud) {
		this.bdTotMontoSolicitud = bdTotMontoSolicitud;
	}

	public BigDecimal getBdTotDiferenciaPlla() {
		return bdTotDiferenciaPlla;
	}

	public void setBdTotDiferenciaPlla(BigDecimal bdTotDiferenciaPlla) {
		this.bdTotDiferenciaPlla = bdTotDiferenciaPlla;
	}

	public List<Tabla> getListaTipoPrestamo() {
		return listaTipoPrestamo;
	}

	public void setListaTipoPrestamo(List<Tabla> listaTipoPrestamo) {
		this.listaTipoPrestamo = listaTipoPrestamo;
	}

	public BigDecimal getBdTotEnviado() {
		return bdTotEnviado;
	}

	public void setBdTotEnviado(BigDecimal bdTotEnviado) {
		this.bdTotEnviado = bdTotEnviado;
	}

	public BigDecimal getBdTotEfectuado() {
		return bdTotEfectuado;
	}

	public void setBdTotEfectuado(BigDecimal bdTotEfectuado) {
		this.bdTotEfectuado = bdTotEfectuado;
	}

	public boolean isPoseePermiso() {
		return poseePermiso;
	}

	public void setPoseePermiso(boolean poseePermiso) {
		this.poseePermiso = poseePermiso;
	}

	public Integer getIntErrorFiltros() {
		return intErrorFiltros;
	}

	public void setIntErrorFiltros(Integer intErrorFiltros) {
		this.intErrorFiltros = intErrorFiltros;
	}
}