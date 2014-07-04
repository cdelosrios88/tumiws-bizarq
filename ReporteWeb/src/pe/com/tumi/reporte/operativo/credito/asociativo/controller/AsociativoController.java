package pe.com.tumi.reporte.operativo.credito.asociativo.controller;

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
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Asociativo;
import pe.com.tumi.reporte.operativo.credito.asociativo.facade.AsociativoFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

/*****************************************************************************
 * NOMBRE DE LA CLASE: AsociativoController
 * FUNCIONALIDAD : CLASE QUE TIENE LOS PARAMETROS DE BUSQUEDA Y VALIDACIONES 
 * REF. : 
 * AUTOR : JUNIOR CHÁVEZ VALVERDE 
 * VERSIÓN : V1 
 * FECHA CREACIÓN : 07.11.2013 
 *****************************************************************************/

public class AsociativoController {

	protected static Logger log;
	
	//Inicio de Sesión
	private Usuario usuarioSesion;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	
	//CONTROLLER
	private TablaFacadeRemote tablaFacade;
	private AsociativoFacadeLocal asociativoFacade;
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
	private Integer intTipoSocio;
	private Integer intModalidad;
	
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
	
	private List<Asociativo> listaCaptaciones;
	private List<Asociativo> listaRenuncias;
	
	private Asociativo beanAsociativo;	
	//Totales
	private Integer intSumatoriaProyectado;
	private Integer intSumatoriaEjecutado;
	private Integer intSumatoriaDiferencia;
	private Integer intSumatoriaAltas;
	private Integer intSumatoriaBajas;
	private Integer intSumatoriaTotal;
	
	//Detalle Captaciones/Renuncias Ejecutadas
	private String strFmtPeriodo;
	private String strSucursal;
	private String strTipoSocio;
	private String strModalidad;
	private List<Asociativo> listaDetalleCaptaciones;
	private List<Asociativo> listaDetalleRenuncias;
	
	private Formatter fmt = new Formatter();
	
	private Integer intDepartamento;
	private Boolean blnDisabledDepartamento;
	private Boolean blnDisabledSucursal;
	
	//Tab Convenios
	private List<Asociativo> listaConvenios;
	
	//Permisos
	private boolean poseePermisoCaptaciones;
	private boolean poseePermisoRenuncias;
	private boolean poseePermisoPadron;
	private boolean poseePermisoConvenios;
	
	public AsociativoController(){
		log = Logger.getLogger(this.getClass());	
		cargarUsuario();
		cargarPermisos();
		if (usuarioSesion!=null) {
			cargarValoresIniciales();
		}else log.error("--Usuario obtenido es NULL.");		
	}
	
	public String getInicioPage() {
		cargarUsuario();
		cargarPermisos();
		if (usuarioSesion!=null) {
			limpiarFormulario();			
		}else log.error("--Usuario obtenido es NULL.");						
		return "";
	}
	
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
	}
	
	private void cargarPermisos(){
		blnShowPanelTabCaptaciones = Boolean.FALSE;
		blnShowPanelTabRenuncias = Boolean.FALSE;
		blnShowPanelTabPadronTrabajadores = Boolean.FALSE;
		blnShowPanelTabConvenios = Boolean.FALSE;
		
		poseePermisoCaptaciones = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_CAPTACIONES);
		log.info("POSEE PERMISO CAPTACIONES: " + poseePermisoCaptaciones);
		if (poseePermisoCaptaciones == true) {
			blnShowPanelTabCaptaciones = Boolean.TRUE;
		}
		poseePermisoRenuncias = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_RENUNCIAS);
		log.info("POSEE PERMISO RENUNCIAS: " + poseePermisoRenuncias);
		if (poseePermisoRenuncias == true) {
			blnShowPanelTabRenuncias = Boolean.TRUE;
		}
		poseePermisoPadron = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_PADRON_TRABAJADORES);
		log.info("POSEE PERMISO PADRÓN: " + poseePermisoPadron);
		if (poseePermisoPadron == true) {
			blnShowPanelTabPadronTrabajadores = Boolean.TRUE;
		}
		poseePermisoConvenios = PermisoUtil.poseePermiso(Constante.PARAM_TRANSACCION_CONVENIOS);
		log.info("POSEE PERMISO CONVENIOS: " + poseePermisoConvenios);
		if (poseePermisoConvenios == true) {
			blnShowPanelTabConvenios = Boolean.TRUE;
		}
	}
	
	public void cargarValoresIniciales() {
		//LLENANDO COMBOS DEL FORMULARIO
		beanAsociativo = new Asociativo();
		listaCaptaciones = new ArrayList<Asociativo>();
		listaDetalleCaptaciones = new ArrayList<Asociativo>();
		listaRenuncias = new ArrayList<Asociativo>();
		listaDetalleRenuncias = new ArrayList<Asociativo>();
		listaConvenios = new ArrayList<Asociativo>();
		listJuridicaSubsucursal = new ArrayList<Subsucursal>();
		getListAnios();
		getListSucursales();		
		intErrorFiltros = 0;
		
		try {
			asociativoFacade =(AsociativoFacadeLocal)EJBFactory.getLocal(AsociativoFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaDescripcionMes = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MES_CALENDARIO));
			listaDescripcionTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			listaDescripcionModalidad = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
		} catch (Exception e) {
			log.error("Error en AsociativoController --> "+e);
		}
	}
	
	public void showPanelCaptaciones() {
//		rbPeriodoOpcA = "1";
//		rbPeriodoOpcB = null;
//		intMesDesde = 0;
//		intMesHasta = 0;
//		intAnioBusqueda = 0;
//		intRangoPeriodo = 0;
//		blnDisabledMesDesde = Boolean.FALSE;
//		blnDisabledMesHasta = Boolean.FALSE;
//		blnDisabledAnioBusqueda = Boolean.FALSE;
//		blnDisabledRangoPeriodo = Boolean.TRUE;
	}

	public void showPanelRenuncias() {
//		blnShowPanelTabCaptaciones = Boolean.FALSE;
//		blnShowPanelTabRenuncias = Boolean.TRUE;
//		blnShowPanelTabPadronTrabajadores = Boolean.FALSE;
//		blnShowPanelTabConvenios = Boolean.FALSE;
	}

	public void showPanelPadronTrabajadores() {
//		blnShowPanelTabCaptaciones = Boolean.FALSE;
//		blnShowPanelTabRenuncias = Boolean.FALSE;
//		blnShowPanelTabPadronTrabajadores = Boolean.TRUE;
//		blnShowPanelTabConvenios = Boolean.FALSE;
	}

	public void showPanelConvenios() {
//		blnShowPanelTabCaptaciones = Boolean.FALSE;
//		blnShowPanelTabRenuncias = Boolean.FALSE;
//		blnShowPanelTabPadronTrabajadores = Boolean.FALSE;
//		blnShowPanelTabConvenios = Boolean.TRUE;
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
	
	public void consultarCaptaciones() {
		List<Asociativo> listaCantProy = null;
		List<Asociativo> listaCantEjec = null;
		List<Asociativo> listaCantAltas = null;
		
		listaCaptaciones.clear();
		intSumatoriaProyectado = 0;
		intSumatoriaEjecutado = 0;
		intSumatoriaDiferencia = 0;
		intSumatoriaAltas = 0;
		intSumatoriaTotal = 0;
		
		Integer periodoDesde = 0;
		Integer periodoHasta = 0;
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
			
			listaCantProy = asociativoFacade.getListaCantidadProyectadas(SESION_IDEMPRESA, intIdSucursal, intIdSubSucursal, Constante.PARAM_T_TIPOINDICADOR_CAPTACION, periodoDesde, periodoHasta);
			listaCantEjec = asociativoFacade.getListaCantidadCaptacionesEjecuadas(SESION_IDEMPRESA, intIdSucursal, intIdSubSucursal, periodoDesde, periodoHasta);
			listaCantAltas = asociativoFacade.getListaCantidadCaptacionesAltas(Constante.PARAM_T_AUDITORIA_CUENTA, Constante.PARAM_T_AUDITORIA_CUENTA_COLUMNA_CONDICIONCUENTA,periodoDesde, periodoHasta);
			
			if (listaCantProy != null && !listaCantProy.isEmpty()) {
				for (Asociativo proyectado : listaCantProy) {
					//Generales...
					beanAsociativo.setIntPersEmpresaPk(SESION_IDEMPRESA);
					beanAsociativo.setIntIdSucursal(intIdSucursal);
					beanAsociativo.setIntIdSubSucursal(intIdSubSucursal);
					
					beanAsociativo.setIntPeriodo(proyectado.getIntPeriodo());
					beanAsociativo.setStrFmtPeriodo(formatoFecha(Integer.valueOf(proyectado.getIntPeriodo().toString().substring(0, 4)),Integer.valueOf(proyectado.getIntPeriodo().toString().substring(4, 6))));
					beanAsociativo.setIntCantProyectadas(proyectado.getIntCantProyectadas());
					//
					if (listaCantEjec!=null && !listaCantEjec.isEmpty()) {
						for (Asociativo ejecutado : listaCantEjec) {
							if (ejecutado.getIntPeriodo().equals(proyectado.getIntPeriodo())) {
								beanAsociativo.setIntCantEjecutadas(ejecutado.getIntCantEjecutadas());
								break;
							}
						}
					}					
					beanAsociativo.setIntDiferencia((beanAsociativo.getIntCantEjecutadas()!=null?beanAsociativo.getIntCantEjecutadas():0) - beanAsociativo.getIntCantProyectadas());
					//
					if (listaCantAltas!=null && !listaCantAltas.isEmpty()) {
						for (Asociativo altas : listaCantAltas) {
							if (altas.getIntPeriodo().equals(proyectado.getIntPeriodo())) {
								beanAsociativo.setIntCantCondAltas(altas.getIntCantCondAltas());
								break;
							}
						}
					}					
					beanAsociativo.setIntTotalCaptaciones(beanAsociativo.getIntDiferencia()+(beanAsociativo.getIntCantCondAltas()!=null?beanAsociativo.getIntCantCondAltas():0));
					//	
					listaCaptaciones.add(beanAsociativo);
					//
					intSumatoriaProyectado = intSumatoriaProyectado + beanAsociativo.getIntCantProyectadas();
					intSumatoriaEjecutado = intSumatoriaEjecutado + (beanAsociativo.getIntCantEjecutadas()!=null?beanAsociativo.getIntCantEjecutadas():0);
					intSumatoriaDiferencia = intSumatoriaDiferencia + beanAsociativo.getIntDiferencia();
					intSumatoriaAltas = intSumatoriaAltas + (beanAsociativo.getIntCantCondAltas()!=null?beanAsociativo.getIntCantCondAltas():0);
					intSumatoriaTotal = intSumatoriaTotal + beanAsociativo.getIntTotalCaptaciones();
					//
					beanAsociativo = new Asociativo();
				}
			}
		} catch (Exception e) {
			log.error("Error en consultarCaptaciones ---> "+e);
		}
	}
	
	public void consultarRenuncias() {
		List<Asociativo> listaCantProy = null;
		List<Asociativo> listaCantEjec = null;
		List<Asociativo> listaCantBajas = null;
		
		listaRenuncias.clear();
		intSumatoriaProyectado = 0;
		intSumatoriaEjecutado = 0;
		intSumatoriaDiferencia = 0;
		intSumatoriaBajas = 0;
		intSumatoriaTotal = 0;
		
		Integer periodoDesde = 0;
		Integer periodoHasta = 0;
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
			
			listaCantProy = asociativoFacade.getListaCantidadProyectadas(SESION_IDEMPRESA, intIdSucursal, intIdSubSucursal, Constante.PARAM_T_TIPOINDICADOR_RENUNCIA, periodoDesde, periodoHasta);
			
			listaCantEjec = asociativoFacade.getListaCantidadRenunciasEjecuadas(SESION_IDEMPRESA, intIdSucursal, intIdSubSucursal, periodoDesde, periodoHasta);
			listaCantBajas = asociativoFacade.getListaCantidadRenunciasBajas(Constante.PARAM_T_AUDITORIA_CUENTA,Constante.PARAM_T_AUDITORIA_CUENTA_COLUMNA_CONDICIONCUENTA,periodoDesde, periodoHasta);
			
			if (listaCantProy != null && !listaCantProy.isEmpty()) {
				for (Asociativo proyectado : listaCantProy) {
					//Generales...
					beanAsociativo.setIntPersEmpresaPk(SESION_IDEMPRESA);
					beanAsociativo.setIntIdSucursal(intIdSucursal);
					beanAsociativo.setIntIdSubSucursal(intIdSubSucursal);
					
					beanAsociativo.setIntPeriodo(proyectado.getIntPeriodo());
					beanAsociativo.setStrFmtPeriodo(formatoFecha(Integer.valueOf(proyectado.getIntPeriodo().toString().substring(0, 4)),Integer.valueOf(proyectado.getIntPeriodo().toString().substring(4, 6))));
					beanAsociativo.setIntCantProyectadas(proyectado.getIntCantProyectadas());
					//
					if (listaCantEjec!=null && !listaCantEjec.isEmpty()) {
						for (Asociativo ejecutado : listaCantEjec) {
							if (ejecutado.getIntPeriodo().equals(proyectado.getIntPeriodo())) {
								beanAsociativo.setIntCantEjecutadas(ejecutado.getIntCantEjecutadas());
								break;
							}
						}
					}					
					beanAsociativo.setIntDiferencia((beanAsociativo.getIntCantEjecutadas()!=null?beanAsociativo.getIntCantEjecutadas():0) - beanAsociativo.getIntCantProyectadas());
					//
					if (listaCantBajas!=null && !listaCantBajas.isEmpty()) {
						for (Asociativo altas : listaCantBajas) {
							if (altas.getIntPeriodo().equals(proyectado.getIntPeriodo())) {
								beanAsociativo.setIntCantCondBajas(altas.getIntCantCondBajas());
								break;
							}
						}
					}					
					beanAsociativo.setIntTotalRenuncias(beanAsociativo.getIntDiferencia()+(beanAsociativo.getIntCantCondBajas()!=null?beanAsociativo.getIntCantCondBajas():0));
					//	
					listaRenuncias.add(beanAsociativo);
					//
					intSumatoriaProyectado = intSumatoriaProyectado + beanAsociativo.getIntCantProyectadas();
					intSumatoriaEjecutado = intSumatoriaEjecutado + (beanAsociativo.getIntCantEjecutadas()!=null?beanAsociativo.getIntCantEjecutadas():0);
					intSumatoriaDiferencia = intSumatoriaDiferencia + beanAsociativo.getIntDiferencia();
					intSumatoriaBajas = intSumatoriaBajas + (beanAsociativo.getIntCantCondBajas()!=null?beanAsociativo.getIntCantCondBajas():0);
					intSumatoriaTotal = intSumatoriaTotal + beanAsociativo.getIntTotalRenuncias();
					//
					beanAsociativo = new Asociativo();
				}
			}
		} catch (Exception e) {
			log.error("Error en consultarRenuncias ---> "+e);
		}
	}
	
	public void consultarPadrones() {
		try {
			
		} catch (Exception e) {
			log.error("Error en consultarPadrones ---> "+e);
		}
	}
	
	public void consultarConvenios() {
		listaConvenios.clear();		
		try {
			listaConvenios = asociativoFacade.getListaConveniosPorSucursal(SESION_IDEMPRESA, intIdSucursal==0?null:intIdSucursal);
			if (listaConvenios!=null && !listaConvenios.isEmpty()) {
				String strEntidad = "";
				Integer cont = 1;
				for (Asociativo convenios : listaConvenios) {
					if (strEntidad!="") {
						if (strEntidad.equals(convenios.getStrEntidadDesc())) {
							strEntidad = convenios.getStrEntidadDesc();
							convenios.setStrEntidadDesc(null);
							convenios.setStrNroRuc(null);
						}else {
							strEntidad = convenios.getStrEntidadDesc();
							cont++;
						}
					}else {
						strEntidad = convenios.getStrEntidadDesc();
						cont++;
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en consultarConvenios ---> "+e);
		}
	}
	
	public Integer filtrarPorPeriodoYMes(){
		GregorianCalendar gcFechaReferencia = new GregorianCalendar();
		
		if(intRangoPeriodo.equals(0)){
//			return listaFilas;
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
		intTipoSocio = 0;
		intModalidad = 0;
		
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
		listaDetalleCaptaciones.clear();
	}
	
	public void limpiarTabRenuncias() {
		intIdSucursal = 0;
		intIdSubSucursal = 0;
		intTipoSocio = 0;
		intModalidad = 0;
		
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
		listaRenuncias.clear();
		listaDetalleRenuncias.clear();
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
		listaConvenios.clear();
	}
	
	public void limpiarFormulario() {
		
		listJuridicaSubsucursal.clear();
		
		limpiarTabCaptaciones();
		limpiarTabRenuncias();
		limpiarTabPadrones();
		limpiarTabConvenios();
	}
	
	public void imprimirReporteCaptaciones() {
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		String strSucursal = "";
		fmt = new Formatter();			
		Calendar cal = Calendar.getInstance();
		String strFechaYHoraActual = fmt.format("%1$02d/%2$02d/%3$04d - %4$02d:%5$02d",cal.get(Calendar.DATE),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)).toString();
		try {
			//Año Actual
			parametro.put("strAnioActual", ""+Calendar.getInstance().get(Calendar.YEAR));
			//Fecha de Emisión			
			parametro.put("strFecEmision", strFechaYHoraActual);
			//Sucursal
			for (Sucursal descSucursal : listJuridicaSucursal) {
				if(descSucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
					strSucursal = descSucursal.getJuridica().getStrRazonSocial();
					break;
				}
			}
			parametro.put("strSucursal", strSucursal);
			
			//Totales
			parametro.put("intSumatoriaProyectado", intSumatoriaProyectado);
			parametro.put("intSumatoriaEjecutado", intSumatoriaEjecutado);
			parametro.put("intSumatoriaDiferencia", intSumatoriaDiferencia);
			parametro.put("intSumatoriaAltas", intSumatoriaAltas);
			parametro.put("intSumatoriaTotal", intSumatoriaTotal);
			
			strNombreReporte = "rptAsocCaptaciones";			
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaCaptaciones), Constante.PARAM_T_TIPOREPORTE_PDF);

		} catch (Exception e) {
			log.error("Error en imprimirReporteCaptaciones ---> "+e);
		}
	}
	
	public void imprimirReporteRenuncias() {
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		String strSucursal = "";
		fmt = new Formatter();			
		Calendar cal = Calendar.getInstance();
		String strFechaYHoraActual = fmt.format("%1$02d/%2$02d/%3$04d - %4$02d:%5$02d",cal.get(Calendar.DATE),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)).toString();
		try {
			//Año Actual
			parametro.put("strAnioActual", ""+Calendar.getInstance().get(Calendar.YEAR));
			//Fecha de Emisión			
			parametro.put("strFecEmision", strFechaYHoraActual);
			//Sucursal
			for (Sucursal descSucursal : listJuridicaSucursal) {
				if(descSucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
					strSucursal = descSucursal.getJuridica().getStrRazonSocial();
					break;
				}
			}
			parametro.put("strSucursal", strSucursal);
			
			//Totales
			parametro.put("intSumatoriaProyectado", intSumatoriaProyectado);
			parametro.put("intSumatoriaEjecutado", intSumatoriaEjecutado);
			parametro.put("intSumatoriaDiferencia", intSumatoriaDiferencia);
			parametro.put("intSumatoriaBajas", intSumatoriaBajas);
			parametro.put("intSumatoriaTotal", intSumatoriaTotal);
			
			strNombreReporte = "rptAsocRenuncias";
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
					new ArrayList<Object>(listaRenuncias), Constante.PARAM_T_TIPOREPORTE_PDF);
			
		} catch (Exception e) {
			log.error("Error en imprimirReporteRenuncias ---> "+e);
		}
	}
		
	public void imprimirReportePadrones() {
//		String strNombreReporte = "";
//		HashMap<String,Object> parametro = new HashMap<String,Object>();
//		String strSucursal = "";
//		fmt = new Formatter();			
//		Calendar cal = Calendar.getInstance();
//		String strFechaYHoraActual = fmt.format("%1$02d/%2$02d/%3$04d - %4$02d:%5$02d",cal.get(Calendar.DATE),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)).toString();
//		try {
//			//Año Actual
//			parametro.put("strAnioActual", ""+Calendar.getInstance().get(Calendar.YEAR));
//			//Fecha de Emisión			
//			parametro.put("strFecEmision", strFechaYHoraActual);
//			//Sucursal
//			for (Sucursal descSucursal : listJuridicaSucursal) {
//				if(descSucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
//					strSucursal = descSucursal.getJuridica().getStrRazonSocial();
//					break;
//				}
//			}
//			parametro.put("strSucursal", strSucursal);
//			
//			
//			strNombreReporte = "rptAsocPadrones";
//			UtilManagerReport.generateReport(strNombreReporte, parametro, 
//					new ArrayList<Object>(listaRenuncias), Constante.PARAM_T_TIPOREPORTE_PDF);
//			
//			ReporterAsocPadrones reporte = new ReporterAsocPadrones();
//			reporte.executeReport(strNombreReporte, parametro);
//		} catch (Exception e) {
//			log.error("Error en imprimirReportePadrones ---> "+e);
//		}
	}
	
	public void imprimirReporteConvenios() {
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		String strSucursal = "";
		fmt = new Formatter();			
		Calendar cal = Calendar.getInstance();
		String strFechaYHoraActual = fmt.format("%1$02d/%2$02d/%3$04d - %4$02d:%5$02d",cal.get(Calendar.DATE),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)).toString();
		try {
			//Año Actual
			parametro.put("strAnioActual", ""+Calendar.getInstance().get(Calendar.YEAR));
			//Fecha de Emisión			
			parametro.put("strFecEmision", strFechaYHoraActual);
			//Sucursal
			for (Sucursal descSucursal : listJuridicaSucursal) {
				if(descSucursal.getId().getIntIdSucursal().equals(intIdSucursal)){
					strSucursal = descSucursal.getJuridica().getStrRazonSocial();
					break;
				}
			}
			parametro.put("strSucursal", strSucursal!=""?strSucursal:"TODAS");
			//Tipo Socio
			for (Asociativo conv : listaConvenios) {
				for (Tabla tipoSocio : listaDescripcionTipoSocio) {
					if(tipoSocio.getIntIdDetalle().compareTo(conv.getIntTipoSocio())==0){
						conv.setStrTipoSocio(tipoSocio.getStrDescripcion());
						break;
					}
				}	
			}
			strNombreReporte = "rptAsocConvenios";
			
			List<Asociativo> dataBeanList = getListaConvenios();
			for (Asociativo conv : dataBeanList) {
				if (conv.getStrEntidadDesc()==null) conv.setStrEntidadDesc("");
				if (conv.getStrNroRuc()==null) conv.setStrNroRuc("");
				if (conv.getStrModalidadHaberes()==null) conv.setStrModalidadHaberes("");
				if (conv.getStrModalidadIncentivos()==null) conv.setStrModalidadIncentivos("");
				if (conv.getStrModalidadCas()==null) conv.setStrModalidadCas("");
				if (conv.getStrTiempoVigencia()==null) conv.setStrTiempoVigencia("");
				if (conv.getStrFechaInicio()==null) conv.setStrFechaInicio("");
				if (conv.getStrFechaCese()==null) conv.setStrFechaCese("");
				if (conv.getStrRetencionPorcentaje()==null) conv.setStrRetencionPorcentaje("");
				if (conv.getStrRetencionMonto()==null) conv.setStrRetencionMonto("");
				if (conv.getStrClausulaCobranza()==null) conv.setStrClausulaCobranza("");
				if (conv.getStrDocumentoFisico()==null) conv.setStrDocumentoFisico("");
				if (conv.getStrAlertaPorVencimiento()==null) conv.setStrAlertaPorVencimiento("");
			}
			
			UtilManagerReport.generateReport(strNombreReporte, parametro, 
			new ArrayList<Object>(dataBeanList), Constante.PARAM_T_TIPOREPORTE_PDF);

		} catch (Exception e) {
			log.error("Error en imprimirReporteConvenios ---> "+e);
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
		Asociativo ejecutado = new Asociativo();
		listaDetalleCaptaciones.clear();
		ejecutado = (Asociativo)event.getComponent().getAttributes().get("itemGrilla");

		try {
			//Cabecera
			strFmtPeriodo = ejecutado.getStrFmtPeriodo();
			for (Sucursal descSuc : listJuridicaSucursal) {
				if(descSuc.getId().getIntIdSucursal().equals(ejecutado.getIntIdSucursal())){
					strSucursal = descSuc.getJuridica().getStrRazonSocial();
					break;
				}
			}
			if (intTipoSocio==0) {
				strTipoSocio = "Todos";
			}else {
				for (Tabla descTipoSoc : listaDescripcionTipoSocio) {
					if(descTipoSoc.getIntIdDetalle().equals(intTipoSocio)){
						strTipoSocio = descTipoSoc.getStrDescripcion();
						break;
					}
				}
			}
			if (intModalidad==0) {
				strModalidad = "Todos";
			}else {
				for (Tabla descModalidad : listaDescripcionModalidad) {
					if(descModalidad.getIntIdDetalle().equals(intModalidad)){
						strModalidad = descModalidad.getStrDescripcion();
						break;
					}
				}
			}

			listaDetalleCaptaciones = asociativoFacade.getCaptacionesEjecutadasDetalle(ejecutado.getIntPersEmpresaPk(),ejecutado.getIntIdSucursal(),
																						ejecutado.getIntIdSubSucursal(),ejecutado.getIntPeriodo(),
																						intTipoSocio!=0?intTipoSocio:null,intModalidad!=0?intModalidad:null);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public void detalleRenunciasEjecutadas(ActionEvent event) throws BusinessException{
		Asociativo ejecutado = new Asociativo();
		listaDetalleRenuncias.clear();
		ejecutado = (Asociativo)event.getComponent().getAttributes().get("itemGrilla");

		try {
			//Cabecera
			strFmtPeriodo = ejecutado.getStrFmtPeriodo();
			for (Sucursal descSuc : listJuridicaSucursal) {
				if(descSuc.getId().getIntIdSucursal().equals(ejecutado.getIntIdSucursal())){
					strSucursal = descSuc.getJuridica().getStrRazonSocial();
					break;
				}
			}
			if (intTipoSocio==0) {
				strTipoSocio = "Todos";
			}else {
				for (Tabla descTipoSoc : listaDescripcionTipoSocio) {
					if(descTipoSoc.getIntIdDetalle().equals(intTipoSocio)){
						strTipoSocio = descTipoSoc.getStrDescripcion();
						break;
					}
				}
			}
			if (intModalidad==0) {
				strModalidad = "Todos";
			}else {
				for (Tabla descModalidad : listaDescripcionModalidad) {
					if(descModalidad.getIntIdDetalle().equals(intModalidad)){
						strModalidad = descModalidad.getStrDescripcion();
						break;
					}
				}
			}

			listaDetalleRenuncias = asociativoFacade.getRenunciasEjecutadasDetalle(ejecutado.getIntPersEmpresaPk(),ejecutado.getIntIdSucursal(),
																						ejecutado.getIntIdSubSucursal(),ejecutado.getIntPeriodo(),
																						intTipoSocio!=0?intTipoSocio:null,intModalidad!=0?intModalidad:null);

		} catch (Exception e) {
			throw new BusinessException(e);
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
	
	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		AsociativoController.log = log;
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

	public AsociativoFacadeLocal getAsociativoFacade() {
		return asociativoFacade;
	}

	public void setAsociativoFacade(AsociativoFacadeLocal asociativoFacade) {
		this.asociativoFacade = asociativoFacade;
	}

	public List<Tabla> getListaDescripcionMes() {
		return listaDescripcionMes;
	}

	public void setListaDescripcionMes(List<Tabla> listaDescripcionMes) {
		this.listaDescripcionMes = listaDescripcionMes;
	}

	public List<Asociativo> getListaCaptaciones() {
		return listaCaptaciones;
	}

	public void setListaCaptaciones(List<Asociativo> listaCaptaciones) {
		this.listaCaptaciones = listaCaptaciones;
	}

	public Asociativo getBeanAsociativo() {
		return beanAsociativo;
	}

	public void setBeanAsociativo(Asociativo beanAsociativo) {
		this.beanAsociativo = beanAsociativo;
	}

	public Integer getIntSumatoriaProyectado() {
		return intSumatoriaProyectado;
	}

	public void setIntSumatoriaProyectado(Integer intSumatoriaProyectado) {
		this.intSumatoriaProyectado = intSumatoriaProyectado;
	}

	public Integer getIntSumatoriaEjecutado() {
		return intSumatoriaEjecutado;
	}

	public void setIntSumatoriaEjecutado(Integer intSumatoriaEjecutado) {
		this.intSumatoriaEjecutado = intSumatoriaEjecutado;
	}

	public Integer getIntSumatoriaDiferencia() {
		return intSumatoriaDiferencia;
	}

	public void setIntSumatoriaDiferencia(Integer intSumatoriaDiferencia) {
		this.intSumatoriaDiferencia = intSumatoriaDiferencia;
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

	public List<Asociativo> getListaDetalleCaptaciones() {
		return listaDetalleCaptaciones;
	}

	public void setListaDetalleCaptaciones(List<Asociativo> listaDetalleCaptaciones) {
		this.listaDetalleCaptaciones = listaDetalleCaptaciones;
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

	public List<Asociativo> getListaRenuncias() {
		return listaRenuncias;
	}

	public void setListaRenuncias(List<Asociativo> listaRenuncias) {
		this.listaRenuncias = listaRenuncias;
	}

	public List<Asociativo> getListaDetalleRenuncias() {
		return listaDetalleRenuncias;
	}

	public void setListaDetalleRenuncias(List<Asociativo> listaDetalleRenuncias) {
		this.listaDetalleRenuncias = listaDetalleRenuncias;
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

	public List<Asociativo> getListaConvenios() {
		return listaConvenios;
	}

	public void setListaConvenios(List<Asociativo> listaConvenios) {
		this.listaConvenios = listaConvenios;
	}

	public boolean isPoseePermisoCaptaciones() {
		return poseePermisoCaptaciones;
	}

	public void setPoseePermisoCaptaciones(boolean poseePermisoCaptaciones) {
		this.poseePermisoCaptaciones = poseePermisoCaptaciones;
	}

	public boolean isPoseePermisoRenuncias() {
		return poseePermisoRenuncias;
	}

	public void setPoseePermisoRenuncias(boolean poseePermisoRenuncias) {
		this.poseePermisoRenuncias = poseePermisoRenuncias;
	}

	public boolean isPoseePermisoPadron() {
		return poseePermisoPadron;
	}

	public void setPoseePermisoPadron(boolean poseePermisoPadron) {
		this.poseePermisoPadron = poseePermisoPadron;
	}

	public boolean isPoseePermisoConvenios() {
		return poseePermisoConvenios;
	}

	public void setPoseePermisoConvenios(boolean poseePermisoConvenios) {
		this.poseePermisoConvenios = poseePermisoConvenios;
	}

	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
}
