package pe.com.tumi.presupuesto.indicador.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.empresa.domain.Subsucursal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactoryException;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.mensaje.MessageController;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.facade.AuditoriaFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.presupuesto.indicador.domain.Indicador;
import pe.com.tumi.presupuesto.indicador.domain.IndicadorId;
import pe.com.tumi.presupuesto.indicador.facade.IndicadorFacadeLocal;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

/*****************************************************************************
 * NOMBRE DE LA CLASE: IndicadoresDeGestionController
 * FUNCIONALIDAD : CLASE QUE TIENE LOS PARAMETROS DE BUSQUEDA Y VALIDACIONES 
 * REF. : 
 * AUTOR : JUNIOR CHÁVEZ VALVERDE 
 * VERSIÓN : V1 
 * FECHA CREACIÓN : 10.10.2013 
 *****************************************************************************/

public class IndicadoresDeGestionController {
	protected 	static Logger 	log;
	
	private Boolean blnShowPanelTabPorIndicador;
	private Boolean blnShowPanelTabEnBloque;
	private Indicador beanIndicador;
	private TablaFacadeRemote tablaFacade;
	private AuditoriaFacadeRemote auditoriaFacade;
	
	//DESCRIPCION DE PARAMETROS
	private List<Tabla> listaDescripcionMes;
	private List<Tabla> listaMesesYaRegistrados;
	private List<Tabla> listaDescripcionTipoIndicador;
	private List<Tabla> listaDescripcionTipoValor;
	private List<Tabla> listaDescripcionEstado;
	
	private Indicador indicadorSelected;
	private Integer intTipoGrabacion;
	
	//INICIO DE SESIÓN
	private Usuario usuarioSesion;
	private IndicadorFacadeLocal indicadorFacade;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	private Integer intIdEmpresa;
	private Integer intMesActual;
	private Integer intAnioActual;
	private Integer intDiaActual;
	
	//FILTROS DE BÚSQUEDA
	private Integer intMesBusqueda;
	private Integer intAnioBusqueda;
	private Integer intTipoIndicadorBusqueda;
	private Integer intIdSucursalBusqueda;
	private Integer intIdSubSucursalBusqueda;
	private List<SelectItem> listYears;
	private List<Indicador> listaIndicadoresPorFiltros;
	
	//GRABACION "POR INDICADOR"
	private Integer intMesGrabacion;
	private Integer intAnioGrabacion;
	private Integer intTipoIndicadorGrabacion;
	private Integer intTipoValorGrabacion;
	private Integer intIdSucursalGrabacion;
	private Integer intIdSubSucursalGrabacion;
	private BigDecimal bdMontoProyEnero;
	private BigDecimal bdMontoProyFebrero;
	private BigDecimal bdMontoProyMarzo;
	private BigDecimal bdMontoProyAbril;
	private BigDecimal bdMontoProyMayo;
	private BigDecimal bdMontoProyJunio;
	private BigDecimal bdMontoProyJulio;
	private BigDecimal bdMontoProyAgosto;
	private BigDecimal bdMontoProySeptiembre;
	private BigDecimal bdMontoProyOctubre;
	private BigDecimal bdMontoProyNoviembre;
	private BigDecimal bdMontoProyDiciembre;
	private List<Indicador> listaValidaIndicador;
	
	//GRABACON "POR BLOQUE"
	private Integer intAnioBase;
	private Integer intAnioProyectado;
	private Integer intMesDesde;
	private Integer intMesHasta;
	private BigDecimal bdPorcentajeCrecimiento;
	private String rbTipoIndicador;
	private String rbSucursal;
	private Integer intErrorIndPorAnioBase;
	private List<Indicador> listaIndicadoresDelAnioBase;
	
	//COMBOS DE SUCURSAL
	private List<Sucursal> listJuridicaSucursal;
	private List<Subsucursal> listJuridicaSubsucursalGrb;
	private List<Subsucursal> listJuridicaSubsucursalBusq;
	
	// MENSAJE DE ERROR PARA INDICADOR
	private String strMsgTxtEstCierre;
	private String strMsgTxtMonto;
	private String strMsgTxtPeriodo;
	private String strMsgTxtMes;
	private String strMsgTxtTipoIndicador;
	private String strMsgTxtTipoValor;
	private String strMsgTxtSucursal;
	private String strMsgTxtSubSucursal;
	private String strMsgTxtExisteRegistro;
	private String strMsgTxtRangoMeses;
	private String strMsgTxtPorcentajeCrecimiento;
	
	//DISABLED
	private Boolean blnDisabledAnio;
	private Boolean blnDisabledTipoIndicador;
	private Boolean blnDisabledSucursal;
	private Boolean blnDisabledSubSucursal;
	private Boolean blnDisabledAnioProy;
	private Boolean blnDisabledGrabar;
	
	private Boolean blnDisabledBdMontoProyEnero;
	private Boolean blnDisabledBdMontoProyFebrero;
	private Boolean blnDisabledBdMontoProyMarzo;
	private Boolean blnDisabledBdMontoProyAbril;
	private Boolean blnDisabledBdMontoProyMayo;
	private Boolean blnDisabledBdMontoProyJunio;
	private Boolean blnDisabledBdMontoProyJulio;
	private Boolean blnDisabledBdMontoProyAgosto;
	private Boolean blnDisabledBdMontoProySeptiembre;
	private Boolean blnDisabledBdMontoProyOctubre;
	private Boolean blnDisabledBdMontoProyNoviembre;
	private Boolean blnDisabledBdMontoProyDiciembre;
	
	public IndicadoresDeGestionController(){
		log = Logger.getLogger(this.getClass());
		cargarUsuario();
		if (usuarioSesion!=null) {
			cargarValoresIniciales();
		}else log.error("--Usuario obtenido es NULL.");		
	}
	
	private void cargarUsuario(){
		usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
	}
	
	public void cargarValoresIniciales() {
		intMesActual = Calendar.getInstance().get(Calendar.MONTH)+1;
		intAnioActual = Calendar.getInstance().get(Calendar.YEAR);
		intDiaActual = Calendar.getInstance().get(Calendar.DATE);
		//INICIALIZANDO SHOW PANEL's
		blnShowPanelTabPorIndicador = Boolean.FALSE;
		blnShowPanelTabEnBloque = Boolean.FALSE;
		blnDisabledAnio = Boolean.FALSE;
		blnDisabledTipoIndicador = Boolean.FALSE;
		blnDisabledSucursal = Boolean.FALSE;
		blnDisabledSubSucursal = Boolean.FALSE;
		blnDisabledAnioProy = Boolean.TRUE;
		//
		intTipoGrabacion = 0;
		blnDisabledGrabar = Boolean.TRUE;		
		listaMesesYaRegistrados = new ArrayList<Tabla>();		
		//LLENANDO COMBOS DEL FORMULARIO
		getListAnios();
		getListSucursales();
		//
		beanIndicador = new Indicador();
		beanIndicador.setId(new IndicadorId());

		try {
			indicadorFacade = (IndicadorFacadeLocal)EJBFactory.getLocal(IndicadorFacadeLocal.class);
			auditoriaFacade = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			listaDescripcionMes = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MES_CALENDARIO));
			listaDescripcionTipoIndicador = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOINDICADOR));
			listaDescripcionTipoValor = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOVALOR));
			listaDescripcionEstado = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ESTADOCIERRE));			
		} catch (Exception e) {
			log.error("Error en IndicadoresDeGestionController --> "+e);
		}
	}
	
	public void obtenerListaIndicadoresPorFiltros() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.obtenerListaIndicadoresPorFiltros-------------------------------------");
		listaIndicadoresPorFiltros = null;
		IndicadorId indId = new IndicadorId();
		try {
			indId.setIntEmpresaIndicadorPk(SESION_IDEMPRESA);
			indId.setIntPeriodoIndicador(intAnioBusqueda!=0?intAnioBusqueda:null);
			indId.setIntMesIndicador(intMesBusqueda!=0?intMesBusqueda:null);
			indId.setIntParaTipoIndicador(intTipoIndicadorBusqueda!=0?intTipoIndicadorBusqueda:null);
			indId.setIntEmpresaSucursalPk(SESION_IDEMPRESA); //intIdSucursalBusqueda!=0?SESION_IDEMPRESA:null
			indId.setIntIdSucursal(intIdSucursalBusqueda!=0?intIdSucursalBusqueda:null);
			indId.setIntIdSubSucursal(intIdSubSucursalBusqueda!=0?intIdSubSucursalBusqueda:null);
			listaIndicadoresPorFiltros = indicadorFacade.getListaIndicadorPorFiltros(indId);
		} catch (Exception e) {
			log.error("Error en obtenerListaIndicadoresPorFiltros --> "+e);
		}
	}
	
	//SELECCIONAR FILA EN BUSQUEDA SOCIO POR NOMBRES Y APELLIDOS
	public void setSelectedIndicador(){
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.setSelectedIndicador-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowIndicador"));
		setIndicadorSelected(null);
		String selectedRow = getRequestParameter("rowIndicador");
		Indicador indicador = new Indicador();
		for(int i=0; i<listaIndicadoresPorFiltros.size(); i++){
			indicador = listaIndicadoresPorFiltros.get(i);
			if(i == Integer.parseInt(selectedRow)){
				setIndicadorSelected(indicador);
				break;
			}
		}
	}
	
	public void modificarIndGestionPorIndicador() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.grabarIndGestionPorIndicador-------------------------------------");
		limpiarPanelPorIndicador();		
		blnShowPanelTabPorIndicador = Boolean.TRUE;
		blnShowPanelTabEnBloque = Boolean.FALSE;
		blnDisabledGrabar = Boolean.FALSE;
		List<Indicador> lstInd = null;
		intTipoGrabacion = Constante.PARAM_T_INDICADOR_TIPOGRABACION_POR_INDICADOR;
		
		//Seteando Llaves del Indicador seleccionado para la modificación...
		beanIndicador.getId().setIntEmpresaIndicadorPk(indicadorSelected.getId().getIntEmpresaIndicadorPk());
		beanIndicador.getId().setIntPeriodoIndicador(indicadorSelected.getId().getIntPeriodoIndicador());
		beanIndicador.getId().setIntMesIndicador(indicadorSelected.getId().getIntMesIndicador());
		beanIndicador.getId().setIntParaTipoIndicador(indicadorSelected.getId().getIntParaTipoIndicador());
		beanIndicador.getId().setIntEmpresaSucursalPk(indicadorSelected.getId().getIntEmpresaSucursalPk());
		beanIndicador.getId().setIntIdSucursal(indicadorSelected.getId().getIntIdSucursal());
		beanIndicador.getId().setIntIdSubSucursal(indicadorSelected.getId().getIntIdSubSucursal());
		
		//Deshabilitando llaves en el formulario...
		blnDisabledAnio = Boolean.TRUE;
		blnDisabledTipoIndicador = Boolean.TRUE;
		blnDisabledSucursal = Boolean.TRUE;
		blnDisabledSubSucursal = Boolean.TRUE;
		
		//Deshabilitando Montos Proyectados que no corresponden al mes seleccionado...
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_ENERO)==0) {
			blnDisabledBdMontoProyEnero = Boolean.FALSE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_FEBRERO)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.FALSE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_MARZO)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.FALSE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_ABRIL)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.FALSE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_MAYO)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.FALSE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_JUNIO)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.FALSE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_JULIO)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.FALSE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_AGOSTO)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.FALSE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_SETIEMBRE)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.FALSE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_OCTUBRE)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.FALSE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_NOVIEMBRE)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.FALSE;
			blnDisabledBdMontoProyDiciembre = Boolean.TRUE;
		}
		if (indicadorSelected.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_DICIEMBRE)==0) {
			blnDisabledBdMontoProyEnero = Boolean.TRUE;
			blnDisabledBdMontoProyFebrero = Boolean.TRUE;
			blnDisabledBdMontoProyMarzo = Boolean.TRUE;
			blnDisabledBdMontoProyAbril = Boolean.TRUE;
			blnDisabledBdMontoProyMayo = Boolean.TRUE;
			blnDisabledBdMontoProyJunio = Boolean.TRUE;
			blnDisabledBdMontoProyJulio = Boolean.TRUE;
			blnDisabledBdMontoProyAgosto = Boolean.TRUE;
			blnDisabledBdMontoProySeptiembre = Boolean.TRUE;
			blnDisabledBdMontoProyOctubre = Boolean.TRUE;
			blnDisabledBdMontoProyNoviembre = Boolean.TRUE;
			blnDisabledBdMontoProyDiciembre = Boolean.FALSE;
		}

		//Mostrando datos del registro recuperado...
		intAnioGrabacion = indicadorSelected.getId().getIntPeriodoIndicador();
		intTipoIndicadorGrabacion = indicadorSelected.getId().getIntParaTipoIndicador();
		intTipoValorGrabacion = indicadorSelected.getIntParaTipoValor();
		intIdSucursalGrabacion = indicadorSelected.getId().getIntIdSucursal();		
		
		EmpresaFacadeRemote facade = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursalGrabacion);
			setListJuridicaSubsucursalGrb(listaSubsucursal);
		} catch (Exception e) {
			log.error("Error en obtener SubSucursal --> "+e);
		}
		intIdSubSucursalGrabacion = indicadorSelected.getId().getIntIdSubSucursal();
		
		//Mostrando montos por Empresa, Periodo, Tipo Indicador, Sucursal y Subsucursal, esto para que no solo se vean los montos del mes selecionado, 
		//sino de todos los meses que correspondan a la PK mencionada...
		try {
			IndicadorId beanIndicadorId = new IndicadorId();
			beanIndicadorId.setIntEmpresaIndicadorPk(indicadorSelected.getId().getIntEmpresaIndicadorPk());
			beanIndicadorId.setIntPeriodoIndicador(indicadorSelected.getId().getIntPeriodoIndicador());
			beanIndicadorId.setIntMesIndicador(null);
			beanIndicadorId.setIntParaTipoIndicador(indicadorSelected.getId().getIntParaTipoIndicador());
			beanIndicadorId.setIntEmpresaSucursalPk(indicadorSelected.getId().getIntEmpresaSucursalPk());
			beanIndicadorId.setIntIdSucursal(indicadorSelected.getId().getIntIdSucursal());
			beanIndicadorId.setIntIdSubSucursal(indicadorSelected.getId().getIntIdSubSucursal());
			lstInd = indicadorFacade.getListaIndicadorPorFiltros(beanIndicadorId);
			if (lstInd!=null && !lstInd.isEmpty()) {
				for (Indicador mntoInd : lstInd) {
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_ENERO)==0) {
						bdMontoProyEnero = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_FEBRERO)==0) {
						bdMontoProyFebrero = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_MARZO)==0) {
						bdMontoProyMarzo = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_ABRIL)==0) {
						bdMontoProyAbril = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_MAYO)==0) {
						bdMontoProyMayo = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_JUNIO)==0) {
						bdMontoProyJunio = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_JULIO)==0) {
						bdMontoProyJulio = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_AGOSTO)==0) {
						bdMontoProyAgosto = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_SETIEMBRE)==0) {
						bdMontoProySeptiembre = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_OCTUBRE)==0) {
						bdMontoProyOctubre = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_NOVIEMBRE)==0) {
						bdMontoProyNoviembre = mntoInd.getBdMontoProyectado();
					}
					if (mntoInd.getId().getIntMesIndicador().compareTo(Constante.PARAM_T_MES_DICIEMBRE)==0) {
						bdMontoProyDiciembre = mntoInd.getBdMontoProyectado();
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en obtener Montos --> "+e);
		}
	}
	
	
	public void showPanelEnBloque() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.showPanelEnBloque-------------------------------------");
		blnShowPanelTabPorIndicador = Boolean.FALSE;
		blnShowPanelTabEnBloque = Boolean.TRUE;
		blnDisabledGrabar = Boolean.FALSE;
		
		limpiarPanelEnBloque();
		//limpiarPanelPorIndicador();
		limpiarMsgErrorIndicador();
		
		intTipoGrabacion = Constante.PARAM_T_INDICADOR_TIPOGRABACION_EN_BLOQUE;
	}
	
	public void showPanelPorIndicador() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.showPanelPorIndicador-------------------------------------");
		blnShowPanelTabPorIndicador = Boolean.TRUE;
		blnShowPanelTabEnBloque = Boolean.FALSE;
		blnDisabledGrabar = Boolean.FALSE;
		
		limpiarPanelEnBloque();
		limpiarPanelPorIndicador();
		limpiarMsgErrorIndicador();
		
		intTipoGrabacion = Constante.PARAM_T_INDICADOR_TIPOGRABACION_POR_INDICADOR;
	}
	
	//PARA EL BOTON DEL POPUP "mpModificaEnProcesoDeGrabacion"
	public void cancelarModificadion() {
		if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_EN_BLOQUE)) {
			showPanelEnBloque();
		}
		if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_POR_INDICADOR)) {
			showPanelPorIndicador();
		}
	}
	
	public void habilitarCombos() {
		//Combo Tipo de Indicador
		if (rbTipoIndicador.equals("1")) {
			intTipoIndicadorGrabacion = 0;
			blnDisabledTipoIndicador = Boolean.TRUE;
		}else {
			blnDisabledTipoIndicador = Boolean.FALSE;
		}
		//Combo Sucursal - SubSucursal
		if (rbSucursal.equals("1")) {
			intIdSucursalGrabacion = 0;
			intIdSubSucursalGrabacion = 0;
			blnDisabledSucursal = Boolean.TRUE;
			blnDisabledSubSucursal = Boolean.TRUE;
		}else {
			blnDisabledSucursal = Boolean.FALSE;
			blnDisabledSubSucursal = Boolean.FALSE;
		}	
	}

	public void disabledCboSucursal(){
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.disabledCboSucursal-------------------------------------");
		log.info("pRbSucursal: "+getRequestParameter("pRbSucursal"));
		String pFiltroRadio = getRequestParameter("pRbSucursal");
		if (pFiltroRadio.equals("1")) {
			intIdSucursalGrabacion = 0;
			intIdSubSucursalGrabacion = 0;
			blnDisabledSucursal = Boolean.TRUE;
			blnDisabledSubSucursal = Boolean.TRUE;
		}else {
			blnDisabledSucursal = Boolean.FALSE;
			blnDisabledSubSucursal = Boolean.FALSE;
		}
	}
	
	public void disabledCboTipIndGrb(){
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.disabledCboTipIndGrb-------------------------------------");
		log.info("pRbTipoInicador: "+getRequestParameter("pRbTipoInicador"));
		String pFiltroRadio = getRequestParameter("pRbTipoInicador");
		if (pFiltroRadio.equals("1")) {
			intTipoIndicadorGrabacion = 0;
			blnDisabledTipoIndicador = Boolean.TRUE;
		}else {
			blnDisabledTipoIndicador = Boolean.FALSE;
		}
	}
		
	public void procesarEvento(){
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.procesarEvento-------------------------------------");
		listaMesesYaRegistrados.clear();
		listaValidaIndicador = new ArrayList<Indicador>();
		EmpresaFacadeRemote facade = null;
		List<Subsucursal> listaSubsucursal = null;
		listaIndicadoresDelAnioBase = new ArrayList<Indicador>();
		Integer mesIndicador = null;
		BigDecimal mntoProy = BigDecimal.ZERO;
		intErrorIndPorAnioBase = 0;
		List<Indicador> listaMesesDelAnioBase = null; 
		try {
			//En Bloque
			if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_EN_BLOQUE)) {
				if (beanIndicador.getId().getIntEmpresaIndicadorPk()==null) {
					if (isValidoEnBloque(beanIndicador) == false) {
						log.info("Datos de Indicador no válidos. Se aborta el proceso de grabación de Indicador de Gestión.");
						return;
					}
					//Ejemplo: Si el Año Base es 2013 (debe EXISTIR data, de lo contrario no grabara) el proyectado será para el 2014 (serán los registros a grabar)
					//y si el Crecimiento fuera 120%, este se aplicará a los datos del 2013 (por Tipo de Indicador, Sucursal y Subsucursal) y el resultado grabarlos 
					//al 2014 manteniendo como Tipo Valor el del Año Base.

					//Verificando que exista al menos 1 registro con el Año Base ingresado...
					listaIndicadoresDelAnioBase = indicadorFacade.getListaPorRangoFechas(SESION_IDEMPRESA,intAnioBase,intMesDesde,intMesHasta);
					listaMesesDelAnioBase = indicadorFacade.getMesesDelAnioBase(SESION_IDEMPRESA, intAnioBase, intMesDesde, intMesHasta);
					
					
					if (listaIndicadoresDelAnioBase!=null && !listaIndicadoresDelAnioBase.isEmpty()) {
						//Tipo de Indicadores: TODOS     Sucursal-SubSucursal: TODOS
						if (rbTipoIndicador.equals("1") && rbSucursal.equals("1")) {
							for (Indicador mes : listaMesesDelAnioBase) {
								for (Tabla lstTipoIndicador : listaDescripcionTipoIndicador) {
									for (Sucursal lstSucursal : listJuridicaSucursal) {
										facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
										listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(lstSucursal.getId().getIntIdSucursal());
										if (listaSubsucursal!=null && !listaSubsucursal.isEmpty()) {
											for (Subsucursal lstSubSucu : listaSubsucursal) {
												//Comparando con datos del año base
												for (Indicador lstIndDelAnioBase : listaIndicadoresDelAnioBase) {
													if (lstIndDelAnioBase.getId().getIntMesIndicador().equals(mes.getId().getIntMesIndicador())) {
														if (lstIndDelAnioBase.getId().getIntParaTipoIndicador().equals(lstTipoIndicador.getIntIdDetalle())) {
															if (lstIndDelAnioBase.getId().getIntIdSucursal().equals(lstSucursal.getId().getIntIdSucursal()) && 
																	lstIndDelAnioBase.getId().getIntIdSubSucursal().equals(lstSubSucu.getId().getIntIdSubSucursal())) {
																intAnioGrabacion = intAnioProyectado;
																intTipoIndicadorGrabacion = lstTipoIndicador.getIntIdDetalle();
																intIdSucursalGrabacion = lstSucursal.getId().getIntIdSucursal();
																intIdSubSucursalGrabacion = lstSubSucu.getId().getIntIdSubSucursal();
																intTipoValorGrabacion = lstIndDelAnioBase.getIntParaTipoValor();
																mesIndicador = mes.getId().getIntMesIndicador();													
																mntoProy = lstIndDelAnioBase.getBdMontoProyectado().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100));
																grabarIndicador(mesIndicador, mntoProy);
																break;
															}
														}
													}													
												}
											}
										}
									}
								}
							}
						}				
						
						//Tipo de Indicadores: TODOS 
						if (rbTipoIndicador.equals("1") && rbSucursal.compareTo("1")!=0) {
							for (Indicador mes : listaMesesDelAnioBase) {
								for (Tabla lstTipoIndicador : listaDescripcionTipoIndicador) {
									//Comparando con datos del año base
									for (Indicador lstIndDelAnioBase : listaIndicadoresDelAnioBase) {
										if (lstIndDelAnioBase.getId().getIntMesIndicador().equals(mes.getId().getIntMesIndicador())) {
											if (lstIndDelAnioBase.getId().getIntParaTipoIndicador().equals(lstTipoIndicador.getIntIdDetalle())) {
												if (lstIndDelAnioBase.getId().getIntIdSucursal().equals(intIdSucursalGrabacion) && 
														lstIndDelAnioBase.getId().getIntIdSubSucursal().equals(intIdSubSucursalGrabacion)) {
													intAnioGrabacion = intAnioProyectado;
													intTipoIndicadorGrabacion = lstTipoIndicador.getIntIdDetalle();
													intTipoValorGrabacion = lstIndDelAnioBase.getIntParaTipoValor();
													mesIndicador = mes.getId().getIntMesIndicador();
													mntoProy = lstIndDelAnioBase.getBdMontoProyectado().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100));
													grabarIndicador(mesIndicador, mntoProy);
													break;
												}
											}
										}													
									}
								}
							}
						}
										
						//Sucursal-SubSucursal: TODOS
						if (rbSucursal.equals("1") && rbTipoIndicador.compareTo("1")!=0) {
							for (Indicador mes : listaMesesDelAnioBase) {
								for (Sucursal lstSucursal : listJuridicaSucursal) {
									facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
									listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(lstSucursal.getId().getIntIdSucursal());
									if (listaSubsucursal!=null && !listaSubsucursal.isEmpty()) {
										for (Subsucursal lstSubSucu : listaSubsucursal) {
											//Comparando con datos del año base
											for (Indicador lstIndDelAnioBase : listaIndicadoresDelAnioBase) {
												if (lstIndDelAnioBase.getId().getIntMesIndicador().equals(mes.getId().getIntMesIndicador())) {
													if (lstIndDelAnioBase.getId().getIntParaTipoIndicador().equals(intTipoIndicadorGrabacion)) {
														if (lstIndDelAnioBase.getId().getIntIdSucursal().equals(lstSucursal.getId().getIntIdSucursal()) && 
																lstIndDelAnioBase.getId().getIntIdSubSucursal().equals(lstSubSucu.getId().getIntIdSubSucursal())) {
															intAnioGrabacion = intAnioProyectado;
															intIdSucursalGrabacion = lstSucursal.getId().getIntIdSucursal();
															intIdSubSucursalGrabacion = lstSubSucu.getId().getIntIdSubSucursal();
															intTipoValorGrabacion = lstIndDelAnioBase.getIntParaTipoValor();
															mesIndicador = mes.getId().getIntMesIndicador();
															mntoProy = lstIndDelAnioBase.getBdMontoProyectado().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100));
															grabarIndicador(mesIndicador, mntoProy);
															break;
														}
													}
												}													
											}
										}
									}
								}
							}
						}
						//
						if (rbTipoIndicador.compareTo("1")!=0 && rbSucursal.compareTo("1")!=0) {
							for (Indicador mes : listaMesesDelAnioBase) {
								//Comparando con datos del año base
								for (Indicador lstIndDelAnioBase : listaIndicadoresDelAnioBase) {
									if (lstIndDelAnioBase.getId().getIntMesIndicador().equals(mes.getId().getIntMesIndicador())) {
										if (lstIndDelAnioBase.getId().getIntParaTipoIndicador().equals(intTipoIndicadorGrabacion)) {
											if (lstIndDelAnioBase.getId().getIntIdSucursal().equals(intIdSucursalGrabacion) && 
													lstIndDelAnioBase.getId().getIntIdSubSucursal().equals(intIdSubSucursalGrabacion)) {
												intAnioGrabacion = intAnioProyectado;
												intTipoValorGrabacion = lstIndDelAnioBase.getIntParaTipoValor();
												mesIndicador = mes.getId().getIntMesIndicador();;													
												mntoProy = lstIndDelAnioBase.getBdMontoProyectado().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100));
												grabarIndicador(mesIndicador, mntoProy);
												break;
											}
										}
									}													
								}							
							}
						}
						if (!listaValidaIndicador.isEmpty()) {
							setStrMsgTxtExisteRegistro("");
							strMsgTxtExisteRegistro = "Uno o más Indicadores ya se encuentran registrados, ";						
						}else {
							limpiarPanelEnBloque();
						}	
					}else {
						intErrorIndPorAnioBase = 1;
						MessageController message = (MessageController)getSessionBean("messageController");
						message.setWarningMessage("No existe registros con el Año Base ingresado. " +
								"Verifique.");
						log.info("No existe ningun registro en el año base");
						limpiarPanelEnBloque();
					}
				}
			}
			//Por Indicador
			if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_POR_INDICADOR)) {
				if (beanIndicador.getId().getIntEmpresaIndicadorPk()==null) {
					if (isValidoPorIndicador(beanIndicador) == false) {
						log.info("Datos de Indicador no válidos. Se aborta el proceso de grabación de Indicador de Gestión.");
						return;
					}	
					if (bdMontoProyEnero!=null) {
						grabarIndicador(Constante.PARAM_T_MES_ENERO, bdMontoProyEnero);
					}
					if (bdMontoProyFebrero!=null) {
						grabarIndicador(Constante.PARAM_T_MES_FEBRERO, bdMontoProyFebrero);
					}
					if (bdMontoProyMarzo!=null) {
						grabarIndicador(Constante.PARAM_T_MES_MARZO, bdMontoProyMarzo);
					}
					if (bdMontoProyAbril!=null) {
						grabarIndicador(Constante.PARAM_T_MES_ABRIL, bdMontoProyAbril);
					}
					if (bdMontoProyMayo!=null) {
						grabarIndicador(Constante.PARAM_T_MES_MAYO, bdMontoProyMayo);
					}
					if (bdMontoProyJunio!=null) {
						grabarIndicador(Constante.PARAM_T_MES_JUNIO, bdMontoProyJunio);
					}
					if (bdMontoProyJulio!=null) {
						grabarIndicador(Constante.PARAM_T_MES_JULIO, bdMontoProyJulio);
					}
					if (bdMontoProyAgosto!=null) {
						grabarIndicador(Constante.PARAM_T_MES_AGOSTO, bdMontoProyAgosto);
					}
					if (bdMontoProySeptiembre!=null) {
						grabarIndicador(Constante.PARAM_T_MES_SETIEMBRE, bdMontoProySeptiembre);
					}
					if (bdMontoProyOctubre!=null) {
						grabarIndicador(Constante.PARAM_T_MES_OCTUBRE, bdMontoProyOctubre);
					}
					if (bdMontoProyNoviembre!=null) {
						grabarIndicador(Constante.PARAM_T_MES_NOVIEMBRE, bdMontoProyNoviembre);
					}
					if (bdMontoProyDiciembre!=null) {
						grabarIndicador(Constante.PARAM_T_MES_DICIEMBRE, bdMontoProyDiciembre);
					}
					if (!listaMesesYaRegistrados.isEmpty()) {
						String descMes = "";
						setStrMsgTxtExisteRegistro("");
						for (Tabla mesYaExiste : listaMesesYaRegistrados) {
							descMes = descMes+mesYaExiste.getStrDescripcion()+"("+mesYaExiste.getStrAbreviatura()+") ";
						}
						strMsgTxtExisteRegistro = "Ya existe Registros para mes(es) "+descMes;						
					}else {
						limpiarPanelPorIndicador();
						blnShowPanelTabPorIndicador = Boolean.TRUE;
					}					
				}else {
					if (isValidoPorIndicador(beanIndicador) == false) {
						log.info("Datos de Indicador no válidos. Se aborta el proceso de grabación de Indicador de Gestión.");
						return;
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_ENERO)) {
						modificarIndicador(bdMontoProyEnero,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_FEBRERO)) {
						modificarIndicador(bdMontoProyFebrero,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_MARZO)) {
						modificarIndicador(bdMontoProyMarzo,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_ABRIL)) {
						modificarIndicador(bdMontoProyAbril,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_MAYO)) {
						modificarIndicador(bdMontoProyMayo,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_JUNIO)) {
						modificarIndicador(bdMontoProyJunio,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_JULIO)) {
						modificarIndicador(bdMontoProyJulio,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_AGOSTO)) {
						modificarIndicador(bdMontoProyAgosto,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_SETIEMBRE)) {
						modificarIndicador(bdMontoProySeptiembre,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_OCTUBRE)) {
						modificarIndicador(bdMontoProyOctubre,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_NOVIEMBRE)) {
						modificarIndicador(bdMontoProyNoviembre,indicadorSelected);
					}
					if (indicadorSelected.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_DICIEMBRE)) {
						modificarIndicador(bdMontoProyDiciembre,indicadorSelected);
					}
					limpiarPanelPorIndicador();
					blnShowPanelTabPorIndicador = Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Error en procesarEvento --> "+e);
		}
	}
	
	public void modificarEnProcesoGrabacion() {
		BigDecimal mntoProy = BigDecimal.ZERO;
		try {
			if (listaValidaIndicador!=null && !listaValidaIndicador.isEmpty()) {
				for (Indicador lstValidIndicador : listaValidaIndicador) {
					//Seteando Indicador para la modificación...
					beanIndicador.getId().setIntEmpresaIndicadorPk(lstValidIndicador.getId().getIntEmpresaIndicadorPk());
					beanIndicador.getId().setIntPeriodoIndicador(lstValidIndicador.getId().getIntPeriodoIndicador());
					beanIndicador.getId().setIntMesIndicador(lstValidIndicador.getId().getIntMesIndicador());
					beanIndicador.getId().setIntParaTipoIndicador(lstValidIndicador.getId().getIntParaTipoIndicador());
					beanIndicador.getId().setIntEmpresaSucursalPk(lstValidIndicador.getId().getIntEmpresaSucursalPk());
					beanIndicador.getId().setIntIdSucursal(lstValidIndicador.getId().getIntIdSucursal());
					beanIndicador.getId().setIntIdSubSucursal(lstValidIndicador.getId().getIntIdSubSucursal());
					
					if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_EN_BLOQUE)) {
						for (Indicador lstIndDelAnioBase : listaIndicadoresDelAnioBase) {
							if (lstIndDelAnioBase.getId().getIntEmpresaIndicadorPk().equals(lstValidIndicador.getId().getIntEmpresaIndicadorPk()) && 
									lstIndDelAnioBase.getId().getIntMesIndicador().equals(lstValidIndicador.getId().getIntMesIndicador()) && 
									lstIndDelAnioBase.getId().getIntParaTipoIndicador().equals(lstValidIndicador.getId().getIntParaTipoIndicador()) &&  
									lstIndDelAnioBase.getId().getIntEmpresaSucursalPk().equals(lstValidIndicador.getId().getIntEmpresaSucursalPk()) &&  
									lstIndDelAnioBase.getId().getIntIdSucursal().equals(lstValidIndicador.getId().getIntIdSucursal()) && 
									lstIndDelAnioBase.getId().getIntIdSubSucursal().equals(lstValidIndicador.getId().getIntIdSubSucursal())) {
								intTipoValorGrabacion = lstIndDelAnioBase.getIntParaTipoValor();
								mntoProy = lstIndDelAnioBase.getBdMontoProyectado().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100));
								break;
							}
						}			
						modificarIndicador(mntoProy,lstValidIndicador);
					}
					
					if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_POR_INDICADOR)) {
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_ENERO)) {
							modificarIndicador(bdMontoProyEnero,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_FEBRERO)) {
							modificarIndicador(bdMontoProyFebrero,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_MARZO)) {
							modificarIndicador(bdMontoProyMarzo,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_ABRIL)) {
							modificarIndicador(bdMontoProyAbril,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_MAYO)) {
							modificarIndicador(bdMontoProyMayo,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_JUNIO)) {
							modificarIndicador(bdMontoProyJunio,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_JULIO)) {
							modificarIndicador(bdMontoProyJulio,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_AGOSTO)) {
							modificarIndicador(bdMontoProyAgosto,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_SETIEMBRE)) {
							modificarIndicador(bdMontoProySeptiembre,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_OCTUBRE)) {
							modificarIndicador(bdMontoProyOctubre,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_NOVIEMBRE)) {
							modificarIndicador(bdMontoProyNoviembre,lstValidIndicador);
						}
						if (beanIndicador.getId().getIntMesIndicador().equals(Constante.PARAM_T_MES_DICIEMBRE)) {
							modificarIndicador(bdMontoProyDiciembre,lstValidIndicador);
						}
					}					
				}

				if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_EN_BLOQUE)) {
					limpiarPanelEnBloque();
					blnShowPanelTabEnBloque = Boolean.TRUE;
				}
				if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_POR_INDICADOR)) {
					limpiarPanelPorIndicador();
					blnShowPanelTabPorIndicador = Boolean.TRUE;
				}				
			}
		} catch (Exception e) {
			log.error("Error en modificarEnProcesoGrabacion--> "+e);
		}
	}
	
	//GRABACION DE INDICADORES
	public void grabarIndicador(Integer mes, BigDecimal mtoProy) throws ParseException {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.grabarIndicador-------------------------------------");
		Indicador beanValidaIndicador = null;		
		try {
			//Seteando Indicador para la grabación
			beanIndicador.getId().setIntEmpresaIndicadorPk(SESION_IDEMPRESA);
			beanIndicador.getId().setIntPeriodoIndicador(intAnioGrabacion);
			beanIndicador.getId().setIntMesIndicador(mes);
			beanIndicador.getId().setIntParaTipoIndicador(intTipoIndicadorGrabacion);
			beanIndicador.getId().setIntEmpresaSucursalPk(SESION_IDEMPRESA);
			beanIndicador.getId().setIntIdSucursal(intIdSucursalGrabacion);
			beanIndicador.getId().setIntIdSubSucursal(intIdSubSucursalGrabacion);
			
			beanIndicador.setIntParaTipoValor(intTipoValorGrabacion);
			beanIndicador.setBdMontoProyectado(mtoProy);
			beanIndicador.setBdMontoEjecutado(null);
			beanIndicador.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			beanIndicador.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOCIERRE_PENDIENTE);
			beanIndicador.setIntEmpresaUsuarioPk(SESION_IDEMPRESA);
			beanIndicador.setIntPersonaUsuarioPk(SESION_IDUSUARIO);
			
			//Validando que no exista el registro...
			beanValidaIndicador = indicadorFacade.getIndicadorPorPK(beanIndicador.getId());
			if (beanValidaIndicador!=null) {
				if (beanValidaIndicador.getIntParaEstadoCierre().equals(Constante.PARAM_T_ESTADOCIERRE_PENDIENTE)) {
					String strDescripcion = "";
					for (Tabla descMes : listaDescripcionMes) {
						if(descMes.getIntIdDetalle().compareTo(beanIndicador.getId().getIntMesIndicador())==0){
							strDescripcion = descMes.getStrDescripcion();
							break;
						}
					}
					Tabla tbl = new Tabla();
					tbl.setStrDescripcion(strDescripcion);
					tbl.setStrAbreviatura(convertirMontos(beanValidaIndicador.getBdMontoProyectado()));
					listaMesesYaRegistrados.add(tbl);
					listaValidaIndicador.add(beanValidaIndicador);
				}
			}else {
				//Grabando registro...
				indicadorFacade.grabarIndicador(beanIndicador);
			}
			
		} catch (Exception e) {
			log.error("Error en grabarIndicador --> "+e);
		}
	}
	
	public void modificarIndicador(BigDecimal mtoProy, Indicador beanValAnterior) throws ParseException {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.modificarIndicador-------------------------------------");
		List<Auditoria> listaAuditoria = null;
		try {
			beanIndicador.setIntParaTipoValor(intTipoValorGrabacion);
			beanIndicador.setBdMontoProyectado(mtoProy);
			beanIndicador.setBdMontoEjecutado(null);
			beanIndicador.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			beanIndicador.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOCIERRE_PENDIENTE);
			beanIndicador.setIntEmpresaUsuarioPk(SESION_IDEMPRESA);
			beanIndicador.setIntPersonaUsuarioPk(SESION_IDUSUARIO);
			
			//Modificando...
			indicadorFacade.modificarIndicador(beanIndicador);
			//Grabando Auditoria...
			listaAuditoria = generarAuditoria(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_UPDATE, beanValAnterior);
			if (listaAuditoria!=null && !listaAuditoria.isEmpty()) {
				for (Auditoria lstAudi : listaAuditoria) {
					grabarAuditoria(lstAudi);
				}
			}
		} catch (Exception e) {
			log.error("Error en modificarIndicador --> "+e);
		}
	}
	
	public void eliminarIndicador() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.eliminarRegistroIndGestion-------------------------------------");
		//blnShowPanelTabPorIndicador = Boolean.FALSE;
		List<Auditoria> listaAuditoria = null;
		try {
			//Eliminando registro...
			indicadorFacade.eliminarIndicador(indicadorSelected.getId());
			
			//Generando Auditoria...
			listaAuditoria = generarAuditoria(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_DELETE, indicadorSelected);
			if (listaAuditoria!=null && !listaAuditoria.isEmpty()) {
				for (Auditoria lstAudi : listaAuditoria) {
					grabarAuditoria(lstAudi);
				}
			}
		} catch (Exception e) {
			log.error("Error en eliminarIndicador --> "+e);
		}
	}
	public Auditoria beanAuditoria(Integer intTipoCambio, Indicador beanAuditarIndicador) {
		Auditoria auditoria = new Auditoria();
		Calendar fecHoy = Calendar.getInstance();
		
		auditoria.setListaAuditoriaMotivo(null);
		auditoria.setStrTabla(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR);
		auditoria.setIntEmpresaPk(SESION_IDEMPRESA);
		auditoria.setStrLlave1(""+beanAuditarIndicador.getId().getIntEmpresaIndicadorPk());
		auditoria.setStrLlave2(""+beanAuditarIndicador.getId().getIntPeriodoIndicador());
		auditoria.setStrLlave3(""+beanAuditarIndicador.getId().getIntMesIndicador());
		auditoria.setStrLlave4(""+beanAuditarIndicador.getId().getIntParaTipoIndicador());
		auditoria.setStrLlave5(""+beanAuditarIndicador.getId().getIntEmpresaSucursalPk());
		auditoria.setStrLlave6(""+beanAuditarIndicador.getId().getIntIdSucursal());
		auditoria.setStrLlave7(""+beanAuditarIndicador.getId().getIntIdSubSucursal());
		
		auditoria.setIntTipo(intTipoCambio);			
		auditoria.setTsFecharegistro(new Timestamp(fecHoy.getTimeInMillis()));
		auditoria.setIntPersonaPk(SESION_IDUSUARIO);
		return auditoria;
	}
	//
	public List<Auditoria> generarAuditoria(Integer intTipoCambio, Indicador beanAuditarIndicador) throws BusinessException{
		
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			//Procedo de Eliminacion
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_DELETE)) {
				//PARA_TIPOVALOR_N_COD
				auditoria = beanAuditoria(intTipoCambio,beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_PARA_TIPOVALOR);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getIntParaTipoValor());
				auditoria.setStrValornuevo(null);
								
				lista.add(auditoria);
				
				//INDE_MONTOPROY_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_INDE_MONTOPROY);
				auditoria.setStrValoranterior(beanAuditarIndicador.getBdMontoProyectado()==null?null:""+beanAuditarIndicador.getBdMontoProyectado());
				auditoria.setStrValornuevo(null);
				
				lista.add(auditoria);
				
				//INDE_MONTOEJEC_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_INDE_MONTOEJEC);
				auditoria.setStrValoranterior(beanAuditarIndicador.getBdMontoEjecutado()==null?null:""+beanAuditarIndicador.getBdMontoEjecutado());
				auditoria.setStrValornuevo(null);
				
				lista.add(auditoria);
				
				//INDI_FECHAREGISTRO_D
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_INDI_FECHAREGISTRO);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getTsFechaRegistro());
				auditoria.setStrValornuevo(null);

				lista.add(auditoria);
				
				//PARA_ESTADOCIERRE_N_COD
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_PARA_ESTADOCIERRE);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getIntParaEstadoCierre());
				auditoria.setStrValornuevo(null);

				lista.add(auditoria);
				
				//PERS_EMPRESAUSUARIO_N_PK
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_PERS_EMPRESAUSUARIO);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getIntEmpresaUsuarioPk());
				auditoria.setStrValornuevo(null);

				lista.add(auditoria);
				
				//PERS_PERSONAUSUARIO_N_PK
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_PERS_PERSONAUSUARIO);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getIntPersonaUsuarioPk());
				auditoria.setStrValornuevo(null);
				
				lista.add(auditoria);
			}
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_UPDATE)) {
				//PARA_TIPOVALOR_N_COD
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_PARA_TIPOVALOR);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getIntParaTipoValor());
				auditoria.setStrValornuevo(""+beanIndicador.getIntParaTipoValor());

				lista.add(auditoria);
				
				//INDE_MONTOPROY_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_INDE_MONTOPROY);
				auditoria.setStrValoranterior(beanAuditarIndicador.getBdMontoProyectado()==null?null:""+beanAuditarIndicador.getBdMontoProyectado());
				auditoria.setStrValornuevo(""+beanIndicador.getBdMontoProyectado());
				
				lista.add(auditoria);
				
				//INDE_MONTOEJEC_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_INDE_MONTOEJEC);
				auditoria.setStrValoranterior(beanAuditarIndicador.getBdMontoEjecutado()==null?null:""+beanAuditarIndicador.getBdMontoEjecutado());
				auditoria.setStrValornuevo(null);
				
				lista.add(auditoria);
				
				//INDI_FECHAREGISTRO_D
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_INDI_FECHAREGISTRO);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getTsFechaRegistro());
				auditoria.setStrValornuevo(""+beanIndicador.getTsFechaRegistro());
				
				lista.add(auditoria);
				
				//PARA_ESTADOCIERRE_N_COD
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_PARA_ESTADOCIERRE);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getIntParaEstadoCierre());
				auditoria.setStrValornuevo(""+beanIndicador.getIntParaEstadoCierre());
				
				lista.add(auditoria);
				
				//PERS_EMPRESAUSUARIO_N_PK
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_PERS_EMPRESAUSUARIO);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getIntEmpresaUsuarioPk());
				auditoria.setStrValornuevo(""+SESION_IDEMPRESA);
				
				lista.add(auditoria);
				
				//PERS_PERSONAUSUARIO_N_PK
				auditoria = beanAuditoria(intTipoCambio, beanAuditarIndicador);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_INDICADOR_COLUMNA_PERS_PERSONAUSUARIO);
				auditoria.setStrValoranterior(""+beanAuditarIndicador.getIntPersonaUsuarioPk());
				auditoria.setStrValornuevo(""+SESION_IDUSUARIO);
				
				lista.add(auditoria);
			}
		} catch (Exception e) {
			log.error("Error en generarAuditoria --> "+e);
		}
		return lista;
	}
	//
	public Auditoria grabarAuditoria(Auditoria auditoria)throws BusinessException {		
		try {
			//En el proceso de grabar Auditoria, para el caso de Indicadores no se grabará tabla AUDITORIA_MOTIVO
			auditoriaFacade.grabarAuditoria(auditoria);
			
		} catch (Exception e) {
			log.error("Error en grabarAuditoria ---> "+e);
		}		
		return auditoria;
	}
	
	//VALIDANDO DATOS INGRESADOS PARA GRABACION TAB "EN BLOQUE"
	public Boolean isValidoEnBloque(Indicador beanIndicador) {
		Boolean validIndicador = true;
		limpiarMsgErrorIndicador();
		try {
			//1. Validación de Periodo
			if (intAnioBase == null || intAnioBase == 0) {
				setStrMsgTxtPeriodo("Debe seleccionar un Año Base.");
				validIndicador = false;
			} else {
				setStrMsgTxtPeriodo("");
			}
			//2. Validación de Mes Desde - Hasta
			if ((intMesDesde == null || intMesDesde == 0) || (intMesHasta == null || intMesHasta == 0)) {
				setStrMsgTxtMes("Debe seleccionar rango de meses.");
				validIndicador = false;
			} else {
				setStrMsgTxtRangoMeses("");
			}
			//3. Verificación rangos correctos
			if (intMesDesde >= intMesHasta) {
				setStrMsgTxtRangoMeses("Rango de meses incorrecto.");
				validIndicador = false;
			} else {
				setStrMsgTxtRangoMeses("");
			}
			//4. Validación de % de Crecimiento
			if (bdPorcentajeCrecimiento == null || bdPorcentajeCrecimiento == BigDecimal.ZERO) {
				setStrMsgTxtPorcentajeCrecimiento("Debe ingresar % de Crecimiento.");
				validIndicador = false;
			} else {
				setStrMsgTxtPorcentajeCrecimiento("");
			}
			if (rbTipoIndicador.compareTo("1")!=0) {
				//5. Validación de Tipo Indicador
				if (intTipoIndicadorGrabacion == null || intTipoIndicadorGrabacion == 0) {
					setStrMsgTxtTipoIndicador("Debe seleccionar un Tipo de Indicador.");
					validIndicador = false;
				} else {
					setStrMsgTxtTipoIndicador("");
				}
			}
			if (rbSucursal.compareTo("1")!=0) {
				//6. Validación de Sucursal
				if (intIdSucursalGrabacion == null || intIdSucursalGrabacion == 0) {
					setStrMsgTxtSucursal("Debe seleccionar una Sucursal.");
					validIndicador = false;
				} else {
					setStrMsgTxtSucursal("");
				}			
				//7. Validación de Sub-Sucursal
				if (intIdSubSucursalGrabacion == null || intIdSubSucursalGrabacion == 0) {
					setStrMsgTxtSubSucursal("Debe seleccionar una Sub-Sucursal.");
					validIndicador = false;
				} else {
					setStrMsgTxtSubSucursal("");
				}
			}
		} catch (Exception e) {
			log.error("Error en isValidoEnBloque ---> "+e);
		}
		return validIndicador;
	}
	
	//VALIDANDO ESTADO DE CIERRE
	public void isValidaEstadoCierre() {
		
		MessageController message = (MessageController)getSessionBean("messageController");
		message.setWarningMessage("No se puede Modificar o Eliminar registros con estado Cerrado. " +
				"Presione Aceptar para procesar otro registro.");
		limpiarPanelPorIndicador();
		blnShowPanelTabPorIndicador = Boolean.FALSE;
	}
	
	//VALIDANDO DATOS INGRESADOS PARA GRABACION TAB "POR INDICADOR"
	public Boolean isValidoPorIndicador(Indicador beanIndicador) {
		Boolean validIndicador = true;
		limpiarMsgErrorIndicador();
		try {
			//1. Validación de Periodo
			if (intAnioGrabacion == null || intAnioGrabacion == 0) {
				setStrMsgTxtPeriodo("Debe seleccionar un Año.");
				validIndicador = false;
			} else {
				setStrMsgTxtPeriodo("");
			}
			//2. Validación de Tipo Indicador
			if (intTipoIndicadorGrabacion == null || intTipoIndicadorGrabacion == 0) {
				setStrMsgTxtTipoIndicador("Debe seleccionar un Tipo de Indicador.");
				validIndicador = false;
			} else {
				setStrMsgTxtTipoIndicador("");
			}
			//3. Validación de Tipo Valor
			if (intTipoValorGrabacion == null || intTipoValorGrabacion == 0) {
				setStrMsgTxtTipoValor("Debe seleccionar un Tipo Valor.");
				validIndicador = false;
			} else {
				setStrMsgTxtTipoValor("");
			}			
			//4. Validación de Sucursal
			if (intIdSucursalGrabacion == null || intIdSucursalGrabacion == 0) {
				setStrMsgTxtSucursal("Debe seleccionar una Sucursal.");
				validIndicador = false;
			} else {
				setStrMsgTxtSucursal("");
			}			
			//5. Validación de Sub-Sucursal
			if (intIdSubSucursalGrabacion == null || intIdSubSucursalGrabacion == 0) {
				setStrMsgTxtSubSucursal("Debe seleccionar una Sub-Sucursal.");
				validIndicador = false;
			} else {
				setStrMsgTxtSubSucursal("");
			}			
			//6. Validación de Montos Proyectados
			if (bdMontoProyEnero==null && bdMontoProyFebrero==null && bdMontoProyMarzo==null && bdMontoProyAbril==null && bdMontoProyMayo==null && bdMontoProyJunio==null
					&& bdMontoProyJulio==null && bdMontoProyAgosto==null && bdMontoProySeptiembre==null && bdMontoProyOctubre==null && bdMontoProyNoviembre==null && bdMontoProyDiciembre==null) {
				setStrMsgTxtMonto("Debe ingresar al menos un Monto Proyectado.");
				validIndicador = false;
			} else {
				setStrMsgTxtMonto("");
			}
		} catch (Exception e) {
			log.error("Error en isValidoPorIndicador ---> "+e);
		}
		return validIndicador;
	}
		
	//RECARGAR COMBO SUB-SUCURSAL
	public void getListSubSucursalDeSucGrb() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.getListSubsucursalDeSucursal-------------------------------------");
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
		setListJuridicaSubsucursalGrb(listaSubsucursal);
	}
	
	public void getListSubSucursalDeSucBusq() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.getListSubsucursalDeSucursal-------------------------------------");
		EmpresaFacadeRemote facade = null;
		Integer intIdSucursal = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			intIdSucursal = Integer.valueOf(getRequestParameter("pCboSucursalBusq"));
			
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
		setListJuridicaSubsucursalBusq(listaSubsucursal);
	}
	
	public void getListAnios() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.getListAnios-------------------------------------");
		listYears = new ArrayList<SelectItem>(); 
		try {
			int year=intAnioActual+5;
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
		log.info("-------------------------------------Debugging PresupuestoController.getListSucursales-------------------------------------");
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
	
	public void getAnioProyectado() {
		log.info("-------------------------------------Debugging IndicadoresDeGestionController.getAnhoProyectado-------------------------------------");
		
		Integer intSelectedAnioBase = Integer.valueOf(getRequestParameter("pCboAnioBase"));
		log.info("año base: "+intSelectedAnioBase);
		if (intSelectedAnioBase!=0) {
			setIntAnioProyectado(intSelectedAnioBase+1);
			log.info("año proyectado: "+intAnioProyectado);
		}		
	}
	
	public void limpiarFormularioIndicadoresDeGestion() {
		intTipoGrabacion = 0;
		blnDisabledGrabar = Boolean.TRUE;
		limpiarFiltrosDeBusqueda();
		limpiarPanelPorIndicador();
		limpiarPanelEnBloque();
		limpiarMsgErrorIndicador();
	}

	public void limpiarFiltrosDeBusqueda() {
		intMesBusqueda = 0;
		intAnioBusqueda = 0;
		intTipoIndicadorBusqueda = 0;
		intIdSucursalBusqueda = 0;
		intIdSubSucursalBusqueda = 0;
		listaIndicadoresPorFiltros = null;		
		blnShowPanelTabPorIndicador = Boolean.FALSE;
		blnShowPanelTabEnBloque = Boolean.FALSE;
	}

	public void limpiarPanelPorIndicador() {
		intMesGrabacion = 0;
		intAnioGrabacion = 0;
		intTipoIndicadorGrabacion = 0;
		intTipoValorGrabacion = 0;
		intIdSucursalGrabacion = 0;
		intIdSubSucursalGrabacion = 0;
		
		bdMontoProyEnero = null;
		bdMontoProyFebrero = null;
		bdMontoProyMarzo = null;
		bdMontoProyAbril = null;
		bdMontoProyMayo = null;
		bdMontoProyJunio = null;
		bdMontoProyJulio = null;
		bdMontoProyAgosto = null;
		bdMontoProySeptiembre = null;
		bdMontoProyOctubre = null;
		bdMontoProyNoviembre = null;
		bdMontoProyDiciembre = null;

		blnDisabledAnio = Boolean.FALSE;
		blnDisabledTipoIndicador = Boolean.FALSE;
		blnDisabledSucursal = Boolean.FALSE;
		blnDisabledSubSucursal = Boolean.FALSE;
		
		blnDisabledBdMontoProyEnero = Boolean.FALSE;
		blnDisabledBdMontoProyFebrero = Boolean.FALSE;
		blnDisabledBdMontoProyMarzo = Boolean.FALSE;
		blnDisabledBdMontoProyAbril = Boolean.FALSE;
		blnDisabledBdMontoProyMayo = Boolean.FALSE;
		blnDisabledBdMontoProyJunio = Boolean.FALSE;
		blnDisabledBdMontoProyJulio = Boolean.FALSE;
		blnDisabledBdMontoProyAgosto = Boolean.FALSE;
		blnDisabledBdMontoProySeptiembre = Boolean.FALSE;
		blnDisabledBdMontoProyOctubre = Boolean.FALSE;
		blnDisabledBdMontoProyNoviembre = Boolean.FALSE;
		blnDisabledBdMontoProyDiciembre = Boolean.FALSE;
		
		beanIndicador = new Indicador();
		beanIndicador.setId(new IndicadorId());
	}
	
	public void limpiarPanelEnBloque() {
		intAnioBase = 0;
		intAnioProyectado = null;
		intMesDesde = 0;
		intMesHasta = 0;
		bdPorcentajeCrecimiento = null;
		
		rbTipoIndicador = "1";
		rbSucursal = "1";
		habilitarCombos();
		
		intTipoIndicadorGrabacion = 0;
		intIdSucursalGrabacion = 0;
		intIdSubSucursalGrabacion = 0;
		
		beanIndicador = new Indicador();
		beanIndicador.setId(new IndicadorId());
	}
	
	public void limpiarMsgErrorIndicador() {
		setStrMsgTxtMonto("");
		setStrMsgTxtPeriodo("");
		setStrMsgTxtMes("");
		setStrMsgTxtTipoIndicador("");
		setStrMsgTxtTipoValor("");
		setStrMsgTxtSucursal("");
		setStrMsgTxtSubSucursal("");
		setStrMsgTxtPorcentajeCrecimiento("");
		setStrMsgTxtRangoMeses("");
	}
	
	public String convertirMontos(BigDecimal bdMonto)	{
//		String retorna = "";
//		if (big==null) {
//			retorna = "";
//		}else{
//			String s= big.toString();
//			Integer r = s.indexOf(".");
//			if (r == -1) {
//				retorna = s+".00";
//			}else{
//				Integer largo = s.length();
//				String decimal= s.substring(r+1, largo);
//				if (decimal.length()==1) {
//					retorna = s+"0";
//				}else{
//					retorna = s;
//				}
//			}
//		}
//        return retorna;
		//Formato de nro....
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		NumberFormat formato = new DecimalFormat("#,##0.00",otherSymbols);
		String strMonto = formato.format(bdMonto);
        return strMonto;          
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
		cargarUsuario();		
		if(usuarioSesion!=null){
			limpiarFormularioIndicadoresDeGestion();
		}else log.error("--Usuario obtenido es NULL o no posee permiso.");
		return "";
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
		IndicadoresDeGestionController.log = log;
	}

	public IndicadorFacadeLocal getIndicadorFacade() {
		return indicadorFacade;
	}

	public void setIndicadorFacade(IndicadorFacadeLocal indicadorFacade) {
		this.indicadorFacade = indicadorFacade;
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

	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}

	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}

	public Integer getIntMesActual() {
		return intMesActual;
	}

	public void setIntMesActual(Integer intMesActual) {
		this.intMesActual = intMesActual;
	}

	public Integer getIntAnioActual() {
		return intAnioActual;
	}

	public void setIntAnioActual(Integer intAnioActual) {
		this.intAnioActual = intAnioActual;
	}

	public Integer getIntDiaActual() {
		return intDiaActual;
	}

	public void setIntDiaActual(Integer intDiaActual) {
		this.intDiaActual = intDiaActual;
	}

	public Integer getIntMesBusqueda() {
		return intMesBusqueda;
	}

	public void setIntMesBusqueda(Integer intMesBusqueda) {
		this.intMesBusqueda = intMesBusqueda;
	}

	public Integer getIntAnioBusqueda() {
		return intAnioBusqueda;
	}

	public void setIntAnioBusqueda(Integer intAnioBusqueda) {
		this.intAnioBusqueda = intAnioBusqueda;
	}

	public Integer getIntTipoIndicadorBusqueda() {
		return intTipoIndicadorBusqueda;
	}

	public void setIntTipoIndicadorBusqueda(Integer intTipoIndicadorBusqueda) {
		this.intTipoIndicadorBusqueda = intTipoIndicadorBusqueda;
	}

	public Integer getIntIdSucursalBusqueda() {
		return intIdSucursalBusqueda;
	}

	public void setIntIdSucursalBusqueda(Integer intIdSucursalBusqueda) {
		this.intIdSucursalBusqueda = intIdSucursalBusqueda;
	}

	public Integer getIntIdSubSucursalBusqueda() {
		return intIdSubSucursalBusqueda;
	}

	public void setIntIdSubSucursalBusqueda(Integer intIdSubSucursalBusqueda) {
		this.intIdSubSucursalBusqueda = intIdSubSucursalBusqueda;
	}

	public List<Sucursal> getListJuridicaSucursal() {
		return listJuridicaSucursal;
	}

	public void setListJuridicaSucursal(List<Sucursal> listJuridicaSucursal) {
		this.listJuridicaSucursal = listJuridicaSucursal;
	}

	public List<Subsucursal> getListJuridicaSubsucursalGrb() {
		return listJuridicaSubsucursalGrb;
	}

	public void setListJuridicaSubsucursalGrb(
			List<Subsucursal> listJuridicaSubsucursalGrb) {
		this.listJuridicaSubsucursalGrb = listJuridicaSubsucursalGrb;
	}

	public List<Subsucursal> getListJuridicaSubsucursalBusq() {
		return listJuridicaSubsucursalBusq;
	}

	public void setListJuridicaSubsucursalBusq(
			List<Subsucursal> listJuridicaSubsucursalBusq) {
		this.listJuridicaSubsucursalBusq = listJuridicaSubsucursalBusq;
	}

	public Integer getIntMesGrabacion() {
		return intMesGrabacion;
	}

	public void setIntMesGrabacion(Integer intMesGrabacion) {
		this.intMesGrabacion = intMesGrabacion;
	}

	public Integer getIntAnioGrabacion() {
		return intAnioGrabacion;
	}

	public void setIntAnioGrabacion(Integer intAnioGrabacion) {
		this.intAnioGrabacion = intAnioGrabacion;
	}

	public Integer getIntTipoIndicadorGrabacion() {
		return intTipoIndicadorGrabacion;
	}

	public void setIntTipoIndicadorGrabacion(Integer intTipoIndicadorGrabacion) {
		this.intTipoIndicadorGrabacion = intTipoIndicadorGrabacion;
	}

	public Integer getIntIdSucursalGrabacion() {
		return intIdSucursalGrabacion;
	}

	public void setIntIdSucursalGrabacion(Integer intIdSucursalGrabacion) {
		this.intIdSucursalGrabacion = intIdSucursalGrabacion;
	}

	public Integer getIntIdSubSucursalGrabacion() {
		return intIdSubSucursalGrabacion;
	}

	public void setIntIdSubSucursalGrabacion(Integer intIdSubSucursalGrabacion) {
		this.intIdSubSucursalGrabacion = intIdSubSucursalGrabacion;
	}

	public Integer getIntTipoValorGrabacion() {
		return intTipoValorGrabacion;
	}

	public void setIntTipoValorGrabacion(Integer intTipoValorGrabacion) {
		this.intTipoValorGrabacion = intTipoValorGrabacion;
	}

	public BigDecimal getBdMontoProyEnero() {
		return bdMontoProyEnero;
	}

	public void setBdMontoProyEnero(BigDecimal bdMontoProyEnero) {
		this.bdMontoProyEnero = bdMontoProyEnero;
	}

	public BigDecimal getBdMontoProyFebrero() {
		return bdMontoProyFebrero;
	}

	public void setBdMontoProyFebrero(BigDecimal bdMontoProyFebrero) {
		this.bdMontoProyFebrero = bdMontoProyFebrero;
	}

	public BigDecimal getBdMontoProyMarzo() {
		return bdMontoProyMarzo;
	}

	public void setBdMontoProyMarzo(BigDecimal bdMontoProyMarzo) {
		this.bdMontoProyMarzo = bdMontoProyMarzo;
	}

	public BigDecimal getBdMontoProyAbril() {
		return bdMontoProyAbril;
	}

	public void setBdMontoProyAbril(BigDecimal bdMontoProyAbril) {
		this.bdMontoProyAbril = bdMontoProyAbril;
	}

	public BigDecimal getBdMontoProyMayo() {
		return bdMontoProyMayo;
	}

	public void setBdMontoProyMayo(BigDecimal bdMontoProyMayo) {
		this.bdMontoProyMayo = bdMontoProyMayo;
	}

	public BigDecimal getBdMontoProyJunio() {
		return bdMontoProyJunio;
	}

	public void setBdMontoProyJunio(BigDecimal bdMontoProyJunio) {
		this.bdMontoProyJunio = bdMontoProyJunio;
	}

	public BigDecimal getBdMontoProyJulio() {
		return bdMontoProyJulio;
	}

	public void setBdMontoProyJulio(BigDecimal bdMontoProyJulio) {
		this.bdMontoProyJulio = bdMontoProyJulio;
	}

	public BigDecimal getBdMontoProyAgosto() {
		return bdMontoProyAgosto;
	}

	public void setBdMontoProyAgosto(BigDecimal bdMontoProyAgosto) {
		this.bdMontoProyAgosto = bdMontoProyAgosto;
	}

	public BigDecimal getBdMontoProySeptiembre() {
		return bdMontoProySeptiembre;
	}

	public void setBdMontoProySeptiembre(BigDecimal bdMontoProySeptiembre) {
		this.bdMontoProySeptiembre = bdMontoProySeptiembre;
	}

	public BigDecimal getBdMontoProyOctubre() {
		return bdMontoProyOctubre;
	}

	public void setBdMontoProyOctubre(BigDecimal bdMontoProyOctubre) {
		this.bdMontoProyOctubre = bdMontoProyOctubre;
	}

	public BigDecimal getBdMontoProyNoviembre() {
		return bdMontoProyNoviembre;
	}

	public void setBdMontoProyNoviembre(BigDecimal bdMontoProyNoviembre) {
		this.bdMontoProyNoviembre = bdMontoProyNoviembre;
	}

	public BigDecimal getBdMontoProyDiciembre() {
		return bdMontoProyDiciembre;
	}

	public void setBdMontoProyDiciembre(BigDecimal bdMontoProyDiciembre) {
		this.bdMontoProyDiciembre = bdMontoProyDiciembre;
	}

	public Boolean getBlnShowPanelTabPorIndicador() {
		return blnShowPanelTabPorIndicador;
	}

	public void setBlnShowPanelTabPorIndicador(Boolean blnShowPanelTabPorIndicador) {
		this.blnShowPanelTabPorIndicador = blnShowPanelTabPorIndicador;
	}

	public List<Indicador> getListaIndicadoresPorFiltros() {
		return listaIndicadoresPorFiltros;
	}

	public void setListaIndicadoresPorFiltros(
			List<Indicador> listaIndicadoresPorFiltros) {
		this.listaIndicadoresPorFiltros = listaIndicadoresPorFiltros;
	}

	public String getStrMsgTxtMonto() {
		return strMsgTxtMonto;
	}

	public void setStrMsgTxtMonto(String strMsgTxtMonto) {
		this.strMsgTxtMonto = strMsgTxtMonto;
	}

	public String getStrMsgTxtPeriodo() {
		return strMsgTxtPeriodo;
	}

	public void setStrMsgTxtPeriodo(String strMsgTxtPeriodo) {
		this.strMsgTxtPeriodo = strMsgTxtPeriodo;
	}

	public String getStrMsgTxtTipoIndicador() {
		return strMsgTxtTipoIndicador;
	}

	public void setStrMsgTxtTipoIndicador(String strMsgTxtTipoIndicador) {
		this.strMsgTxtTipoIndicador = strMsgTxtTipoIndicador;
	}

	public String getStrMsgTxtTipoValor() {
		return strMsgTxtTipoValor;
	}

	public void setStrMsgTxtTipoValor(String strMsgTxtTipoValor) {
		this.strMsgTxtTipoValor = strMsgTxtTipoValor;
	}

	public String getStrMsgTxtSucursal() {
		return strMsgTxtSucursal;
	}

	public void setStrMsgTxtSucursal(String strMsgTxtSucursal) {
		this.strMsgTxtSucursal = strMsgTxtSucursal;
	}

	public String getStrMsgTxtSubSucursal() {
		return strMsgTxtSubSucursal;
	}

	public void setStrMsgTxtSubSucursal(String strMsgTxtSubSucursal) {
		this.strMsgTxtSubSucursal = strMsgTxtSubSucursal;
	}

	public Indicador getBeanIndicador() {
		return beanIndicador;
	}

	public void setBeanIndicador(Indicador beanIndicador) {
		this.beanIndicador = beanIndicador;
	}

	public String getStrMsgTxtExisteRegistro() {
		return strMsgTxtExisteRegistro;
	}

	public void setStrMsgTxtExisteRegistro(String strMsgTxtExisteRegistro) {
		this.strMsgTxtExisteRegistro = strMsgTxtExisteRegistro;
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

	public List<Tabla> getListaMesesYaRegistrados() {
		return listaMesesYaRegistrados;
	}

	public void setListaMesesYaRegistrados(List<Tabla> listaMesesYaRegistrados) {
		this.listaMesesYaRegistrados = listaMesesYaRegistrados;
	}

	public List<Tabla> getListaDescripcionTipoIndicador() {
		return listaDescripcionTipoIndicador;
	}

	public void setListaDescripcionTipoIndicador(
			List<Tabla> listaDescripcionTipoIndicador) {
		this.listaDescripcionTipoIndicador = listaDescripcionTipoIndicador;
	}

	public List<Tabla> getListaDescripcionTipoValor() {
		return listaDescripcionTipoValor;
	}

	public void setListaDescripcionTipoValor(List<Tabla> listaDescripcionTipoValor) {
		this.listaDescripcionTipoValor = listaDescripcionTipoValor;
	}

	public List<Tabla> getListaDescripcionEstado() {
		return listaDescripcionEstado;
	}

	public void setListaDescripcionEstado(List<Tabla> listaDescripcionEstado) {
		this.listaDescripcionEstado = listaDescripcionEstado;
	}

	public Indicador getIndicadorSelected() {
		return indicadorSelected;
	}

	public void setIndicadorSelected(Indicador indicadorSelected) {
		this.indicadorSelected = indicadorSelected;
	}

	public Boolean getBlnDisabledAnio() {
		return blnDisabledAnio;
	}

	public void setBlnDisabledAnio(Boolean blnDisabledAnio) {
		this.blnDisabledAnio = blnDisabledAnio;
	}

	public Boolean getBlnDisabledTipoIndicador() {
		return blnDisabledTipoIndicador;
	}

	public void setBlnDisabledTipoIndicador(Boolean blnDisabledTipoIndicador) {
		this.blnDisabledTipoIndicador = blnDisabledTipoIndicador;
	}

	public Boolean getBlnDisabledSucursal() {
		return blnDisabledSucursal;
	}

	public void setBlnDisabledSucursal(Boolean blnDisabledSucursal) {
		this.blnDisabledSucursal = blnDisabledSucursal;
	}

	public Boolean getBlnDisabledSubSucursal() {
		return blnDisabledSubSucursal;
	}

	public void setBlnDisabledSubSucursal(Boolean blnDisabledSubSucursal) {
		this.blnDisabledSubSucursal = blnDisabledSubSucursal;
	}

	public Boolean getBlnShowPanelTabEnBloque() {
		return blnShowPanelTabEnBloque;
	}

	public void setBlnShowPanelTabEnBloque(Boolean blnShowPanelTabEnBloque) {
		this.blnShowPanelTabEnBloque = blnShowPanelTabEnBloque;
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

	public BigDecimal getBdPorcentajeCrecimiento() {
		return bdPorcentajeCrecimiento;
	}

	public void setBdPorcentajeCrecimiento(BigDecimal bdPorcentajeCrecimiento) {
		this.bdPorcentajeCrecimiento = bdPorcentajeCrecimiento;
	}

	public String getRbTipoIndicador() {
		return rbTipoIndicador;
	}

	public void setRbTipoIndicador(String rbTipoIndicador) {
		this.rbTipoIndicador = rbTipoIndicador;
	}

	public String getRbSucursal() {
		return rbSucursal;
	}

	public void setRbSucursal(String rbSucursal) {
		this.rbSucursal = rbSucursal;
	}

	public String getStrMsgTxtMes() {
		return strMsgTxtMes;
	}

	public void setStrMsgTxtMes(String strMsgTxtMes) {
		this.strMsgTxtMes = strMsgTxtMes;
	}

	public String getStrMsgTxtPorcentajeCrecimiento() {
		return strMsgTxtPorcentajeCrecimiento;
	}

	public void setStrMsgTxtPorcentajeCrecimiento(
			String strMsgTxtPorcentajeCrecimiento) {
		this.strMsgTxtPorcentajeCrecimiento = strMsgTxtPorcentajeCrecimiento;
	}

	public Boolean getBlnDisabledBdMontoProyEnero() {
		return blnDisabledBdMontoProyEnero;
	}

	public void setBlnDisabledBdMontoProyEnero(Boolean blnDisabledBdMontoProyEnero) {
		this.blnDisabledBdMontoProyEnero = blnDisabledBdMontoProyEnero;
	}

	public Boolean getBlnDisabledBdMontoProyFebrero() {
		return blnDisabledBdMontoProyFebrero;
	}

	public void setBlnDisabledBdMontoProyFebrero(
			Boolean blnDisabledBdMontoProyFebrero) {
		this.blnDisabledBdMontoProyFebrero = blnDisabledBdMontoProyFebrero;
	}

	public Boolean getBlnDisabledBdMontoProyMarzo() {
		return blnDisabledBdMontoProyMarzo;
	}

	public void setBlnDisabledBdMontoProyMarzo(Boolean blnDisabledBdMontoProyMarzo) {
		this.blnDisabledBdMontoProyMarzo = blnDisabledBdMontoProyMarzo;
	}

	public Boolean getBlnDisabledBdMontoProyAbril() {
		return blnDisabledBdMontoProyAbril;
	}

	public void setBlnDisabledBdMontoProyAbril(Boolean blnDisabledBdMontoProyAbril) {
		this.blnDisabledBdMontoProyAbril = blnDisabledBdMontoProyAbril;
	}

	public Boolean getBlnDisabledBdMontoProyMayo() {
		return blnDisabledBdMontoProyMayo;
	}

	public void setBlnDisabledBdMontoProyMayo(Boolean blnDisabledBdMontoProyMayo) {
		this.blnDisabledBdMontoProyMayo = blnDisabledBdMontoProyMayo;
	}

	public Boolean getBlnDisabledBdMontoProyJunio() {
		return blnDisabledBdMontoProyJunio;
	}

	public void setBlnDisabledBdMontoProyJunio(Boolean blnDisabledBdMontoProyJunio) {
		this.blnDisabledBdMontoProyJunio = blnDisabledBdMontoProyJunio;
	}

	public Boolean getBlnDisabledBdMontoProyJulio() {
		return blnDisabledBdMontoProyJulio;
	}

	public void setBlnDisabledBdMontoProyJulio(Boolean blnDisabledBdMontoProyJulio) {
		this.blnDisabledBdMontoProyJulio = blnDisabledBdMontoProyJulio;
	}

	public Boolean getBlnDisabledBdMontoProyAgosto() {
		return blnDisabledBdMontoProyAgosto;
	}

	public void setBlnDisabledBdMontoProyAgosto(Boolean blnDisabledBdMontoProyAgosto) {
		this.blnDisabledBdMontoProyAgosto = blnDisabledBdMontoProyAgosto;
	}

	public Boolean getBlnDisabledBdMontoProySeptiembre() {
		return blnDisabledBdMontoProySeptiembre;
	}

	public void setBlnDisabledBdMontoProySeptiembre(
			Boolean blnDisabledBdMontoProySeptiembre) {
		this.blnDisabledBdMontoProySeptiembre = blnDisabledBdMontoProySeptiembre;
	}

	public Boolean getBlnDisabledBdMontoProyOctubre() {
		return blnDisabledBdMontoProyOctubre;
	}

	public void setBlnDisabledBdMontoProyOctubre(
			Boolean blnDisabledBdMontoProyOctubre) {
		this.blnDisabledBdMontoProyOctubre = blnDisabledBdMontoProyOctubre;
	}

	public Boolean getBlnDisabledBdMontoProyNoviembre() {
		return blnDisabledBdMontoProyNoviembre;
	}

	public void setBlnDisabledBdMontoProyNoviembre(
			Boolean blnDisabledBdMontoProyNoviembre) {
		this.blnDisabledBdMontoProyNoviembre = blnDisabledBdMontoProyNoviembre;
	}

	public Boolean getBlnDisabledBdMontoProyDiciembre() {
		return blnDisabledBdMontoProyDiciembre;
	}

	public void setBlnDisabledBdMontoProyDiciembre(
			Boolean blnDisabledBdMontoProyDiciembre) {
		this.blnDisabledBdMontoProyDiciembre = blnDisabledBdMontoProyDiciembre;
	}

	public List<Indicador> getListaValidaIndicador() {
		return listaValidaIndicador;
	}

	public void setListaValidaIndicador(List<Indicador> listaValidaIndicador) {
		this.listaValidaIndicador = listaValidaIndicador;
	}

	public String getStrMsgTxtEstCierre() {
		return strMsgTxtEstCierre;
	}

	public void setStrMsgTxtEstCierre(String strMsgTxtEstCierre) {
		this.strMsgTxtEstCierre = strMsgTxtEstCierre;
	}

	public Integer getIntTipoGrabacion() {
		return intTipoGrabacion;
	}

	public void setIntTipoGrabacion(Integer intTipoGrabacion) {
		this.intTipoGrabacion = intTipoGrabacion;
	}

	public AuditoriaFacadeRemote getAuditoriaFacade() {
		return auditoriaFacade;
	}

	public void setAuditoriaFacade(AuditoriaFacadeRemote auditoriaFacade) {
		this.auditoriaFacade = auditoriaFacade;
	}

	public Boolean getBlnDisabledAnioProy() {
		return blnDisabledAnioProy;
	}

	public void setBlnDisabledAnioProy(Boolean blnDisabledAnioProy) {
		this.blnDisabledAnioProy = blnDisabledAnioProy;
	}

	public Integer getIntAnioProyectado() {
		return intAnioProyectado;
	}

	public void setIntAnioProyectado(Integer intAnioProyectado) {
		this.intAnioProyectado = intAnioProyectado;
	}

	public Integer getIntAnioBase() {
		return intAnioBase;
	}

	public void setIntAnioBase(Integer intAnioBase) {
		this.intAnioBase = intAnioBase;
	}

	public String getStrMsgTxtRangoMeses() {
		return strMsgTxtRangoMeses;
	}

	public void setStrMsgTxtRangoMeses(String strMsgTxtRangoMeses) {
		this.strMsgTxtRangoMeses = strMsgTxtRangoMeses;
	}

	public Integer getIntErrorIndPorAnioBase() {
		return intErrorIndPorAnioBase;
	}

	public void setIntErrorIndPorAnioBase(Integer intErrorIndPorAnioBase) {
		this.intErrorIndPorAnioBase = intErrorIndPorAnioBase;
	}

	public List<Indicador> getListaIndicadoresDelAnioBase() {
		return listaIndicadoresDelAnioBase;
	}

	public void setListaIndicadoresDelAnioBase(
			List<Indicador> listaIndicadoresDelAnioBase) {
		this.listaIndicadoresDelAnioBase = listaIndicadoresDelAnioBase;
	}

	public Boolean getBlnDisabledGrabar() {
		return blnDisabledGrabar;
	}

	public void setBlnDisabledGrabar(Boolean blnDisabledGrabar) {
		this.blnDisabledGrabar = blnDisabledGrabar;
	}
}
