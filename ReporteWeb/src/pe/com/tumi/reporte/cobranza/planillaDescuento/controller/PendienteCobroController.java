package pe.com.tumi.reporte.cobranza.planillaDescuento.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import pe.com.tumi.mensaje.MessageController;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.PlanillaDescuento;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.PlanillaDescuentoFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class PendienteCobroController {
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
	private Integer intModalidad;
	private Date dtFechaConsulta;
	
	private List<PlanillaDescuento> listaPendienteCobroActivos;
	private List<PlanillaDescuento> listaPendienteCobroCesantes;
	
	private BigDecimal bdTotEfectuadoActivo;
	private BigDecimal bdTotPagadoActivo;
	private BigDecimal bdTotSaldoActivo;
	
	private BigDecimal bdTotEfectuadoCesante;
	private BigDecimal bdTotPagadoCesante;
	private BigDecimal bdTotSaldoCesante;
	
	private BigDecimal bdTotEfectuado;
	private BigDecimal bdTotPagado;
	private BigDecimal bdTotSaldo;
	
	public PendienteCobroController(){
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
		listaPendienteCobroActivos = new ArrayList<PlanillaDescuento>();
		listaPendienteCobroCesantes = new ArrayList<PlanillaDescuento>();
		
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		intModalidad = 0;
		
		getListSucursales();
	}
	
	public void getListSubSucursal() {
		log.info("-------------------------------------Debugging AsociativoController.getListSubSucursal-------------------------------------");
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = Integer.valueOf(getRequestParameter("pCboSucursalPC"));
			
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
	
	public void consultarPendienteCobro() {
		log.info("intIdSucursal: " + intIdSucursal);
		log.info("intIdSubSucursal: " + intIdSubSucursal);
		try {
			if (!isValidFiltros()) {
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("Los filtros de Sucursal, Sub Sucursal, Año y mes no pueden estar vacíos. " +
						"Verifique.");
				return;
			}
			if(intModalidad==0)intModalidad=null;
			
			listaPendienteCobroActivos = planillaDescuentoFacade.getListaPendienteCobro(intIdSucursal, intIdSubSucursal, Constante.PARAM_T_TIPOSOCIO_ACTIVO, intModalidad, dtFechaConsulta);
			listaPendienteCobroCesantes = planillaDescuentoFacade.getListaPendienteCobro(intIdSucursal, intIdSubSucursal, Constante.PARAM_T_TIPOSOCIO_CESANTE, intModalidad, dtFechaConsulta);
			
			if(listaPendienteCobroActivos!=null && !listaPendienteCobroActivos.isEmpty()){
				bdTotEfectuadoActivo = BigDecimal.ZERO;
				bdTotPagadoActivo = BigDecimal.ZERO;
				bdTotSaldoActivo = BigDecimal.ZERO;
				
				for (PlanillaDescuento pendienteCobroActivo : listaPendienteCobroActivos) {
					if(pendienteCobroActivo.getBdEfectuadoPC()!=null){
						bdTotEfectuadoActivo = bdTotEfectuadoActivo.add(pendienteCobroActivo.getBdEfectuadoPC());
					}
					if(pendienteCobroActivo.getBdPagadoPC()!=null){
						bdTotPagadoActivo = bdTotPagadoActivo.add(pendienteCobroActivo.getBdPagadoPC());
					}
					if(pendienteCobroActivo.getBdSaldoPC()!=null){
						bdTotSaldoActivo = bdTotSaldoActivo.add(pendienteCobroActivo.getBdSaldoPC());
					}
				}
			}
			
			if(listaPendienteCobroCesantes!=null && !listaPendienteCobroCesantes.isEmpty()){
				bdTotEfectuadoCesante = BigDecimal.ZERO;
				bdTotPagadoCesante = BigDecimal.ZERO;
				bdTotSaldoCesante = BigDecimal.ZERO;
				
				for (PlanillaDescuento pendienteCobroActivo : listaPendienteCobroCesantes) {
					if(pendienteCobroActivo.getBdEfectuadoPC()!=null){
						bdTotEfectuadoCesante = bdTotEfectuadoCesante.add(pendienteCobroActivo.getBdEfectuadoPC());
					}
					if(pendienteCobroActivo.getBdPagadoPC()!=null){
						bdTotPagadoCesante = bdTotPagadoCesante.add(pendienteCobroActivo.getBdPagadoPC());
					}
					if(pendienteCobroActivo.getBdSaldoPC()!=null){
						bdTotSaldoCesante = bdTotSaldoCesante.add(pendienteCobroActivo.getBdSaldoPC());
					}
				}
			}
			
			bdTotEfectuado = BigDecimal.ZERO;
			bdTotPagado = BigDecimal.ZERO;
			bdTotSaldo = BigDecimal.ZERO;
			log.info("bdTotEfectuadoActivo: " + bdTotEfectuadoActivo);
			log.info("bdTotEfectuadoCesante: " + bdTotEfectuadoCesante);
			if(bdTotEfectuadoActivo!=null && bdTotEfectuadoCesante!=null){
				bdTotEfectuado = bdTotEfectuadoActivo.add(bdTotEfectuadoCesante);
			}
			log.info("bdTotEfectuado: " + bdTotEfectuado);
			if(bdTotPagadoActivo!=null && bdTotPagadoCesante!=null){
				bdTotPagado = bdTotPagadoActivo.add(bdTotPagadoCesante);
			}
			if(bdTotSaldoActivo!=null && bdTotSaldoCesante!=null){
				bdTotSaldo = bdTotSaldoActivo.add(bdTotSaldoCesante);
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
			if (dtFechaConsulta==null) {
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
	
	public void imprimirPendienteCobro(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		Sucursal sucursal = null;
		Subsucursal subSucursal = null;
		EmpresaFacadeRemote empresaFacade = null;
		Tabla tablaModalidad = null;
		String strModalidad = "";
		try {
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			//tablaModalidad = tablaFacade.getTablaPorIdMaestroYIdDetalle(Integer.valueOf(Constante.PARAM_T_MODALIDADPLANILLA), intModalidad);
			
			sucursal = empresaFacade.getSucursalPorId(intIdSucursal);
			subSucursal = empresaFacade.getSubSucursalPorIdSubSucursal(intIdSubSucursal);
			
			/*if(tablaModalidad!=null){
				strModalidad = tablaModalidad.getStrDescripcion();
			}*/
			
			parametro.put("P_SUCURSAL", (sucursal!=null?sucursal.getJuridica().getStrRazonSocial():""));
			parametro.put("P_SUBSUCURSAL", subSucursal!=null?subSucursal.getStrDescripcion():"");
			parametro.put("P_MODALIDAD", strModalidad);
			parametro.put("P_FECCONSULTA", dtFechaConsulta);
			parametro.put("P_LIST_CESANTES", listaPendienteCobroCesantes);
			parametro.put("P_TOT_EFECTUADO", bdTotEfectuado);
			parametro.put("P_TOT_PAGADO", bdTotPagado);
			parametro.put("P_TOT_SALDO", bdTotSaldo);
			
			strNombreReporte = "pendienteCobro";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaPendienteCobroActivos), Constante.PARAM_T_TIPOREPORTE_PDF);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error en imprimirPlanillaDescuento ---> "+e);
		}
	}
	
	public String getLimpiarPendienteCobro(){
		cargarUsuario();
		poseePermiso = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_REPORTE_PENDIENTE_COBRO);
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

	public List<PlanillaDescuento> getListaPendienteCobroActivos() {
		return listaPendienteCobroActivos;
	}

	public void setListaPendienteCobroActivos(
			List<PlanillaDescuento> listaPendienteCobroActivos) {
		this.listaPendienteCobroActivos = listaPendienteCobroActivos;
	}

	public List<PlanillaDescuento> getListaPendienteCobroCesantes() {
		return listaPendienteCobroCesantes;
	}

	public void setListaPendienteCobroCesantes(
			List<PlanillaDescuento> listaPendienteCobroCesantes) {
		this.listaPendienteCobroCesantes = listaPendienteCobroCesantes;
	}

	public Integer getIntModalidad() {
		return intModalidad;
	}

	public void setIntModalidad(Integer intModalidad) {
		this.intModalidad = intModalidad;
	}

	public Date getDtFechaConsulta() {
		return dtFechaConsulta;
	}

	public void setDtFechaConsulta(Date dtFechaConsulta) {
		this.dtFechaConsulta = dtFechaConsulta;
	}

	public BigDecimal getBdTotEfectuadoActivo() {
		return bdTotEfectuadoActivo;
	}

	public void setBdTotEfectuadoActivo(BigDecimal bdTotEfectuadoActivo) {
		this.bdTotEfectuadoActivo = bdTotEfectuadoActivo;
	}

	public BigDecimal getBdTotPagadoActivo() {
		return bdTotPagadoActivo;
	}

	public void setBdTotPagadoActivo(BigDecimal bdTotPagadoActivo) {
		this.bdTotPagadoActivo = bdTotPagadoActivo;
	}

	public BigDecimal getBdTotSaldoActivo() {
		return bdTotSaldoActivo;
	}

	public void setBdTotSaldoActivo(BigDecimal bdTotSaldoActivo) {
		this.bdTotSaldoActivo = bdTotSaldoActivo;
	}

	public BigDecimal getBdTotEfectuadoCesante() {
		return bdTotEfectuadoCesante;
	}

	public void setBdTotEfectuadoCesante(BigDecimal bdTotEfectuadoCesante) {
		this.bdTotEfectuadoCesante = bdTotEfectuadoCesante;
	}

	public BigDecimal getBdTotPagadoCesante() {
		return bdTotPagadoCesante;
	}

	public void setBdTotPagadoCesante(BigDecimal bdTotPagadoCesante) {
		this.bdTotPagadoCesante = bdTotPagadoCesante;
	}

	public BigDecimal getBdTotSaldoCesante() {
		return bdTotSaldoCesante;
	}

	public void setBdTotSaldoCesante(BigDecimal bdTotSaldoCesante) {
		this.bdTotSaldoCesante = bdTotSaldoCesante;
	}

	public BigDecimal getBdTotEfectuado() {
		return bdTotEfectuado;
	}

	public void setBdTotEfectuado(BigDecimal bdTotEfectuado) {
		this.bdTotEfectuado = bdTotEfectuado;
	}

	public BigDecimal getBdTotPagado() {
		return bdTotPagado;
	}

	public void setBdTotPagado(BigDecimal bdTotPagado) {
		this.bdTotPagado = bdTotPagado;
	}

	public BigDecimal getBdTotSaldo() {
		return bdTotSaldo;
	}

	public void setBdTotSaldo(BigDecimal bdTotSaldo) {
		this.bdTotSaldo = bdTotSaldo;
	}
}