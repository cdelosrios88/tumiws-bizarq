package pe.com.tumi.seguridad.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Formatter;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.FileUtil;
import pe.com.tumi.common.util.MyFile;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaDetalleMovimiento;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaDsctoTerceros;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaMontosBeneficios;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaPlanillas;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaPrestamos;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaPrevisionSocial;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaResumenPrestamos;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaSocioCuenta;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaSocioEstructura;
import pe.com.tumi.estadoCuenta.domain.DataBeanEstadoCuentaGestiones;
import pe.com.tumi.estadoCuenta.facade.EstadoCuentaFacadeLocal;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;

import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.facade.GeneralFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.report.engine.Reporteador;
import pe.com.tumi.report.engine.ReporterEstCtaTabDetalle;
import pe.com.tumi.seguridad.login.domain.Usuario;

/*****************************************************************************
 * NOMBRE DE LA CLASE: ESTADOCUENTACONTROLLER 
 * FUNCIONALIDAD : CLASE QUE TIENE LOS PARAMETROS DE BUSQUEDA Y VALIDACIONES 
 * REF. : 
 * AUTOR : JUNIOR CHÁVEZ VALVERDE 
 * VERSIÓN : V2.0
 * FECHA CREACIÓN : 25/11/2013 
 *****************************************************************************/

public class EstadoCuentaController {
	protected 	static Logger 	log;
		
	//Filtros de Búsqueda
	private Integer intTipoBusqueda;
	private String strIdPersonaBusqueda;
	private Integer intCodigoPersona;
	private Integer intMesBusqueda;
	private Integer intAnioBusqueda;
	private List<SelectItem> listYears;
	private Integer intTipoCuentaBusqueda;
	private Integer intCtasSocBusqueda;
	private Integer intCuentasVigentesYLiquidadas;
	private List<DataBeanEstadoCuentaSocioCuenta> listaSocioBusquedaPorApeNom;
	private BigDecimal bdZero;
	
	//SESIÓN
	private Usuario usuarioSesion;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	private Integer intMesActual;
	private Integer intAnioActual;
	private Integer intDiaActual;
	
	//Cabecera
	private String strFechaYHoraActual;
	private List<DataBeanEstadoCuentaMontosBeneficios> lstDataBeanEstadoCuentaMontosBeneficios;
	private DataBeanEstadoCuentaMontosBeneficios beanMontosBeneficios;
	private List<DataBeanEstadoCuentaSocioCuenta> lstDataBeanEstadoCuentaSocioCuenta;
	private DataBeanEstadoCuentaSocioCuenta beanSocioCuenta;
	private List<DataBeanEstadoCuentaSocioEstructura> lstDataBeanEstadoCuentaSocioEstructura;
	private DataBeanEstadoCuentaSocioEstructura beanSocioEstructura;
	
	//Pie de Resumen
	private BigDecimal bdSumatoriaSaldo;	
	private BigDecimal bdUltimoEnvioAportes;
	private BigDecimal bdUltimoEnvioFdoSepelio;
	private BigDecimal bdUltimoEnvioFdoRetiro;
	private BigDecimal bdUltimoEnvioMant;
	private Integer intMesUltEnvio;
	private Integer intAnioUltEnvio;
	private BigDecimal bdSumatoriaUltimoEnvio;
	
	private List<Tabla> lstCuentasLiquidadas;
	
	//Lista Descripciones
	private TablaFacadeRemote tablaFacade;
	private List<Tabla> listaDescripcionMes;	
	
	//Tab "Resumen"
	private List<DataBeanEstadoCuentaResumenPrestamos> lstDataBeanEstadoCuentaResumenPrestamos;
	
	//Tab "Planillas"
	private List<DataBeanEstadoCuentaPlanillas> lstDataBeanEstadoCuentaPlanillas;
	private List<DataBeanEstadoCuentaPlanillas> lstDataBeanEstadoCuentaDiferenciaPlanilla;
	private List<DataBeanEstadoCuentaPlanillas> listaColumnasDiferenciaPlanilla;
	private List<DataBeanEstadoCuentaPlanillas> listaFilasDiferenciaPlanilla;

	
	//Tab "Prestamos"
	private Integer intTipoCreditoBusqueda;
	private Integer intEstadoCreditoBusqueda;
	private BigDecimal bdSumatoriaSaldoPrestamo;
	private List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamos;
	private List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamosAprobados;
	private List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamosRechazados;
	private List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamosGarantizados;
	
	//Tab "Terceros"
	private List<DataBeanEstadoCuentaDsctoTerceros> lstColumnaHaberes;
	private List<DataBeanEstadoCuentaDsctoTerceros> lstFilaHaberes;	
	private List<DataBeanEstadoCuentaDsctoTerceros> lstColumnaIncentivos;
	private List<DataBeanEstadoCuentaDsctoTerceros> lstFilaIncentivos;	
	private List<DataBeanEstadoCuentaDsctoTerceros> lstColumnaCas;
	private List<DataBeanEstadoCuentaDsctoTerceros> lstFilaCas;
	
	//Tab "Gestiones"
	private List<DataBeanEstadoCuentaGestiones> lstDataBeanEstadoCuentaGestiones;
	
	//Tab "Prevision Social"
	private List<DataBeanEstadoCuentaPrevisionSocial> lstBeneficiosOtorgados;
	private List<DataBeanEstadoCuentaPrevisionSocial> lstPagoCuotasPeriodos;
	private List<DataBeanEstadoCuentaPrevisionSocial> lstPagoCuotasBeneficios;
	private List<DataBeanEstadoCuentaPrevisionSocial> lstPagoCuotasFondoSepelio;
	private List<DataBeanEstadoCuentaPrevisionSocial> lstPagoCuotasFondoRetiro;
	private Integer intPgoCtaBeneficioFdoSepelio;
	private List<DataBeanEstadoCuentaPrevisionSocial> lstDetallePagoCuotasFondoSepelio;
	private List<DataBeanEstadoCuentaPrevisionSocial> lstDetallePagoCuotasFondoRetiro;
	
	//Tab "Detalle"
	private List<DataBeanEstadoCuentaDetalleMovimiento> lstDetalleMovimiento;
	private Boolean blnShowPanelTabDetalle;
	//Fila de Sumatorias de Montos
	private BigDecimal bdSumaMontoAporte;
	private BigDecimal bdSumaMontoMantenimiento;
	private BigDecimal bdSumaMontoFondoSepelio;
	private BigDecimal bdSumaMontoFondoRetiro;
	private BigDecimal bdSumaMontoPrestamo;
	private BigDecimal bdSumaMontoCredito;
	private BigDecimal bdSumaMontoActividad;
	private BigDecimal bdSumaMontoMulta;
	private BigDecimal bdSumaMontoInteres;
	private BigDecimal bdSumaMontoCtaPorPagar;
	private BigDecimal bdSumaMontoCtaPorCobrar;
	private DataBeanEstadoCuentaDetalleMovimiento movimientoAnteriorResumen;
	private List<DataBeanEstadoCuentaDetalleMovimiento> listaMovimientoAntesDeFechaIngresada;
	private List<DataBeanEstadoCuentaDetalleMovimiento> listaMovimientoDespuesDeFechaIngresada;
	//Show Panels
	private Boolean blnShowPanelTabResumen;
	private Boolean blnShowPanelCabecera;
	private Boolean blnShowPanelTabs;
	private Boolean blnShowPanelTabGestiones;
	private Boolean blnShowPanelTabPlanilla;
		private Boolean blnShowPanelTabPlanillaResumen;
		private Boolean blnShowPanelTabPlanillaDiferencia;
	private Boolean blnShowPanelTabTerceros;
		private Boolean blnShowPanelTabTercerosHaberes;
		private Boolean blnShowPanelTabTercerosIncentivos;
		private Boolean blnShowPanelTabTercerosCas;
	private Boolean blnShowPanelTabPrestamos;
	private Boolean blnShowPanelTabPrevisionSocial;
		
	//Facades
	private EstadoCuentaFacadeLocal estadoCuentaFacade ;
	
	//Para Reporte
	private Integer intShowPanel;
	private List<Tabla> listaDescripcionCondLaboral;
	private List<Tabla> listaDescripcionTipoSocio;
	private List<Tabla> listaDescripcionCondicionSocio;
	private List<Tabla> listaDescripcionTipoCondicionSocio;
	private List<Tabla> listaDescripcionModalidadPlanilla;
	private NumberFormat formato;
	
	private Integer busqPorNombre;
	private DataBeanEstadoCuentaSocioCuenta socioNaruralSelected;
	
	//Para recuperación de foto y firma 
	private MyFile fileFotoSocio;
	private MyFile fileFirmaSocio;
	private String strFotoSocio;
	private String strFirmaSocio;

	public EstadoCuentaController(){
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

	public void cargarValoresIniciales(){
		intMesActual = Calendar.getInstance().get(Calendar.MONTH)+1;
		intAnioActual = Calendar.getInstance().get(Calendar.YEAR);
		intDiaActual = Calendar.getInstance().get(Calendar.DATE);
		getListAnios();
		intTipoBusqueda = Constante.PARAM_T_TIPOBUSQUEDA_DNI;
		intTipoCuentaBusqueda = Constante.PARAM_T_TIPOCUENTASOCIO_SOCIO;
		
		//Inicializando listas
		lstDataBeanEstadoCuentaMontosBeneficios = new ArrayList<DataBeanEstadoCuentaMontosBeneficios>();
		lstDataBeanEstadoCuentaSocioCuenta = new ArrayList<DataBeanEstadoCuentaSocioCuenta>();
		lstDataBeanEstadoCuentaSocioEstructura = new ArrayList<DataBeanEstadoCuentaSocioEstructura>();
		lstDataBeanEstadoCuentaResumenPrestamos = new ArrayList<DataBeanEstadoCuentaResumenPrestamos>();
		lstDataBeanEstadoCuentaPlanillas = new ArrayList<DataBeanEstadoCuentaPlanillas>();
		lstDataBeanEstadoCuentaDiferenciaPlanilla = new ArrayList<DataBeanEstadoCuentaPlanillas>();
		lstDataBeanEstadoCuentaGestiones = new ArrayList<DataBeanEstadoCuentaGestiones>();
		lstDataBeanEstadoCuentaPrestamos = new ArrayList<DataBeanEstadoCuentaPrestamos>();
		lstDataBeanEstadoCuentaPrestamosAprobados = new ArrayList<DataBeanEstadoCuentaPrestamos>();
		lstDataBeanEstadoCuentaPrestamosRechazados = new ArrayList<DataBeanEstadoCuentaPrestamos>();
		lstDataBeanEstadoCuentaPrestamosGarantizados = new ArrayList<DataBeanEstadoCuentaPrestamos>();
		lstBeneficiosOtorgados = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		lstPagoCuotasPeriodos = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		lstPagoCuotasBeneficios = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		lstPagoCuotasFondoSepelio = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		lstPagoCuotasFondoRetiro = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		lstDetallePagoCuotasFondoSepelio = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		lstDetallePagoCuotasFondoRetiro = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		lstCuentasLiquidadas = new ArrayList<Tabla>();
		listaSocioBusquedaPorApeNom = new ArrayList<DataBeanEstadoCuentaSocioCuenta>();
		lstDetalleMovimiento = new ArrayList<DataBeanEstadoCuentaDetalleMovimiento>();
		//Show Panel's
		blnShowPanelTabResumen = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE;
		
		listaMovimientoAntesDeFechaIngresada = new ArrayList<DataBeanEstadoCuentaDetalleMovimiento>();
		listaMovimientoDespuesDeFechaIngresada = new ArrayList<DataBeanEstadoCuentaDetalleMovimiento>();
		
		try {
			estadoCuentaFacade = (EstadoCuentaFacadeLocal)EJBFactory.getLocal(EstadoCuentaFacadeLocal.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			
			listaDescripcionMes = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MES_CALENDARIO));
			listaDescripcionModalidadPlanilla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
			listaDescripcionCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
			listaDescripcionTipoCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CONDSOCIO));
			listaDescripcionCondLaboral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONLABORAL));
			listaDescripcionTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			
		}catch (Exception e) {
			log.error("Error en EstadoCuentaController --> "+e);
		}
	}	
	
	public void getListAnios() {
		log.info("-------------------------------------Debugging PresupuestoController.getListAnios-------------------------------------");
		listYears = new ArrayList<SelectItem>(); 
		try {
			int year=intAnioActual+1;
			int cont=0;

			for(int j=year; j>=2013; j--){
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

	public void limpiarFormulario(){
		intCtasSocBusqueda = null;
		intAnioBusqueda = 2013;
		intMesBusqueda = 1;
		intAnioUltEnvio = 0;
		intMesUltEnvio = 0;
		intTipoBusqueda = Constante.PARAM_T_TIPOBUSQUEDA_DNI;
		intTipoCuentaBusqueda = Constante.PARAM_T_TIPOCUENTASOCIO_SOCIO;
		strIdPersonaBusqueda = "";
		intCodigoPersona = 0;
		busqPorNombre = 0;
		
		blnShowPanelTabs = Boolean.FALSE;
		blnShowPanelCabecera = Boolean.FALSE;
		blnShowPanelTabResumen = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;	
		blnShowPanelTabPlanilla = Boolean.FALSE;
			blnShowPanelTabPlanillaResumen = Boolean.FALSE;
			blnShowPanelTabPlanillaDiferencia = Boolean.FALSE;				
		blnShowPanelTabTerceros = Boolean.FALSE;
			blnShowPanelTabTercerosHaberes = Boolean.FALSE;
			blnShowPanelTabTercerosIncentivos = Boolean.FALSE;
			blnShowPanelTabTercerosCas = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 	
		
		lstDataBeanEstadoCuentaResumenPrestamos.clear();
		lstDataBeanEstadoCuentaMontosBeneficios.clear();
		lstDataBeanEstadoCuentaSocioCuenta.clear();
		lstDataBeanEstadoCuentaSocioEstructura.clear();
		lstDataBeanEstadoCuentaDiferenciaPlanilla.clear();
		lstDataBeanEstadoCuentaGestiones.clear();
		lstDataBeanEstadoCuentaPrestamos.clear();
		lstDataBeanEstadoCuentaPrestamosAprobados.clear();
		lstDataBeanEstadoCuentaPrestamosRechazados.clear();
		lstDataBeanEstadoCuentaPrestamosGarantizados.clear();
		listaSocioBusquedaPorApeNom.clear();
		listaMovimientoAntesDeFechaIngresada.clear();
		listaMovimientoDespuesDeFechaIngresada.clear();
		setFileFotoSocio(null);
		setFileFirmaSocio(null);
	}
	
	public void cleanTipoBusqueda(){
		log.info("pCboTipoBusqueda: "+getRequestParameter("pCboTipoBusqueda"));
		strIdPersonaBusqueda = "";
	}
	
	public void consutar(){
		DataBeanEstadoCuentaSocioEstructura porNroDocumento = null;
		DataBeanEstadoCuentaSocioEstructura porNroCuenta = null;
		DataBeanEstadoCuentaSocioCuenta porNombreYApellidos = null;
		busqPorNombre = 0;
		setFileFotoSocio(null);
        setFileFirmaSocio(null);
		try {
			//Búsqueda por código
			if (intTipoBusqueda.equals(Constante.PARAM_T_TIPOBUSQUEDA_CODIGO)){
				intCodigoPersona = Integer.valueOf(strIdPersonaBusqueda);
				obtenerDatosCabecera();
			}
			//Búsqueda por DNI
			if (intTipoBusqueda.equals(Constante.PARAM_T_TIPOBUSQUEDA_DNI)) {
				porNroDocumento = estadoCuentaFacade.getSocioPorDocumento(1, strIdPersonaBusqueda.trim());
				if (porNroDocumento!=null) {
					if (porNroDocumento.getIntIdPersona()!=null) intCodigoPersona = porNroDocumento.getIntIdPersona();	
					obtenerDatosCabecera();
				}else {
					limpiarFormulario();
				}
			}
			//Búsqueda por apellidos y nombres
			if (intTipoBusqueda.equals(Constante.PARAM_T_TIPOBUSQUEDA_APELLIDOSYNOMBRES)) {
				listaSocioBusquedaPorApeNom = estadoCuentaFacade.getSocioPorNombres(SESION_IDEMPRESA, strIdPersonaBusqueda.trim());
				if (listaSocioBusquedaPorApeNom!=null && !listaSocioBusquedaPorApeNom.isEmpty()) {
					if (listaSocioBusquedaPorApeNom.size()==1) {
						porNombreYApellidos = listaSocioBusquedaPorApeNom.get(0);
						intCodigoPersona = porNombreYApellidos.getIntIdPersona();
						obtenerDatosCabecera();
					}else {
						blnShowPanelTabs = Boolean.FALSE;
						blnShowPanelCabecera = Boolean.FALSE;
						blnShowPanelTabResumen = Boolean.FALSE;
						blnShowPanelTabDetalle = Boolean.FALSE;	
						blnShowPanelTabPlanilla = Boolean.FALSE;
							blnShowPanelTabPlanillaResumen = Boolean.FALSE;
							blnShowPanelTabPlanillaDiferencia = Boolean.FALSE;				
						blnShowPanelTabTerceros = Boolean.FALSE;
							blnShowPanelTabTercerosHaberes = Boolean.FALSE;
							blnShowPanelTabTercerosIncentivos = Boolean.FALSE;
							blnShowPanelTabTercerosCas = Boolean.FALSE;
						blnShowPanelTabGestiones = Boolean.FALSE;
						blnShowPanelTabPrestamos = Boolean.FALSE;
						blnShowPanelTabPrevisionSocial = Boolean.FALSE; 	
						
						lstDataBeanEstadoCuentaResumenPrestamos.clear();
						lstDataBeanEstadoCuentaMontosBeneficios.clear();
						lstDataBeanEstadoCuentaSocioCuenta.clear();
						lstDataBeanEstadoCuentaSocioEstructura.clear();
						lstDataBeanEstadoCuentaDiferenciaPlanilla.clear();
						lstDataBeanEstadoCuentaGestiones.clear();
						lstDataBeanEstadoCuentaPrestamos.clear();
						lstDataBeanEstadoCuentaPrestamosAprobados.clear();
						lstDataBeanEstadoCuentaPrestamosRechazados.clear();
						lstDataBeanEstadoCuentaPrestamosGarantizados.clear();
						busqPorNombre = 1;
					}
				}else {
					limpiarFormulario();
				}
			}
			//Búsqueda por numero de cuenta JCHAVEZ 22.01.2014
			if (intTipoBusqueda.equals(Constante.PARAM_T_T_TIPOBUSQUEDA_NROCTA)) {
				porNroCuenta = estadoCuentaFacade.getSocioPorNumeroCuenta(SESION_IDEMPRESA, Integer.valueOf(strIdPersonaBusqueda.trim()));
				if (porNroCuenta!=null) {
					if (porNroCuenta.getIntIdPersona()!=null) intCodigoPersona = porNroCuenta.getIntIdPersona();	
					obtenerDatosCabecera();
				}else {
					limpiarFormulario();
				}
			}
		} catch (Exception e) {
			log.error("Error en consutar --> "+e);
		}
	}
	
	public void setSelectedSocioNatural(ActionEvent event){
		log.info("-------------------------------------Debugging estadoCuentaController.setSelectedSocioNatural-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowSocioNatural"));
		setSocioNaruralSelected(null);
		String selectedRow = getRequestParameter("rowSocioNatural");
		DataBeanEstadoCuentaSocioCuenta socio = new DataBeanEstadoCuentaSocioCuenta();
		for(int i=0; i<listaSocioBusquedaPorApeNom.size(); i++){
			socio = listaSocioBusquedaPorApeNom.get(i);
			if(i == Integer.parseInt(selectedRow)){
				setSocioNaruralSelected(socio);
				break;
			}
		}
	}
	
	public void buscarDatosSocioPorApellidosYNombres(){
		log.info("-------------------------------------Debugging EstadoCuentaController.buscarSocio-------------------------------------");
		try {
			if (socioNaruralSelected!=null) {
				intCodigoPersona = socioNaruralSelected.getIntIdPersona();
				obtenerDatosCabecera();
			}else {
				log.info("Seleccionar registro");
			}			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void obtenerDatosCabecera() {
		busqPorNombre = 0;
		EstadoCuentaController estCtaController = (EstadoCuentaController)getSessionBean("estadoCuentaController");
		if (estCtaController == null) {
			estCtaController = new EstadoCuentaController();
		}else {
			blnShowPanelCabecera = Boolean.TRUE;
			blnShowPanelTabGestiones = Boolean.FALSE;
			blnShowPanelTabPlanilla = Boolean.FALSE;
			blnShowPanelTabTerceros = Boolean.FALSE;
			blnShowPanelTabPrestamos = Boolean.FALSE;
			blnShowPanelTabPrevisionSocial = Boolean.FALSE;
			blnShowPanelTabDetalle = Boolean.FALSE;
			lstDataBeanEstadoCuentaMontosBeneficios.clear();
			beanMontosBeneficios = new DataBeanEstadoCuentaMontosBeneficios();
			lstDataBeanEstadoCuentaSocioCuenta.clear();
			beanSocioCuenta = new DataBeanEstadoCuentaSocioCuenta();
			lstDataBeanEstadoCuentaSocioEstructura.clear();
			beanSocioEstructura = new DataBeanEstadoCuentaSocioEstructura();
			listaSocioBusquedaPorApeNom.clear();
			actualizarFechaYHora();
			lstCuentasLiquidadas.clear();
			Tabla ctaLiq = null;
			bdUltimoEnvioAportes = null;
			bdUltimoEnvioFdoSepelio = null;
			bdUltimoEnvioFdoRetiro = null;
			bdUltimoEnvioMant = null;
				
			bdSumatoriaUltimoEnvio = BigDecimal.ZERO;
			
			log.info("Buscando...."+intCodigoPersona);

			try {
				if (intCodigoPersona!=0) {
					lstDataBeanEstadoCuentaSocioCuenta = estadoCuentaFacade.getCabCuentasSocio(SESION_IDEMPRESA, intCodigoPersona, intTipoCuentaBusqueda);
					if (lstDataBeanEstadoCuentaSocioCuenta!=null && !lstDataBeanEstadoCuentaSocioCuenta.isEmpty()) {
						if (intCuentasVigentesYLiquidadas.equals(0)) {
							for (DataBeanEstadoCuentaSocioCuenta socioCuenta : lstDataBeanEstadoCuentaSocioCuenta) {
								if (socioCuenta.getIntSituacionCuenta().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)) {
									intCtasSocBusqueda = socioCuenta.getIntCuenta();
									beanSocioCuenta.setStrNombreCompletoSocio(socioCuenta.getStrNombreCompletoSocio());
									beanSocioCuenta.setIntCuenta(socioCuenta.getIntCuenta());
									beanSocioCuenta.setStrNumeroCuenta(socioCuenta.getStrNumeroCuenta());
									beanSocioCuenta.setIntCondicionCuenta(socioCuenta.getIntCondicionCuenta());
									beanSocioCuenta.setIntSubCondicionCuenta(socioCuenta.getIntSubCondicionCuenta());
									beanSocioCuenta.setIntSituacionCuenta(socioCuenta.getIntSituacionCuenta());
									beanSocioCuenta.setStrFechaApertura(socioCuenta.getStrFechaApertura());
									beanSocioCuenta.setStrFechaLiquidacion(socioCuenta.getStrFechaLiquidacion());
									break;
								}
							}
						}else {
							intCtasSocBusqueda = intCuentasVigentesYLiquidadas;
							for (DataBeanEstadoCuentaSocioCuenta socioCuenta2 : lstDataBeanEstadoCuentaSocioCuenta) {
								if (socioCuenta2.getIntCuenta().equals(intCtasSocBusqueda)) {
									beanSocioCuenta.setStrNombreCompletoSocio(socioCuenta2.getStrNombreCompletoSocio());
									beanSocioCuenta.setIntCuenta(socioCuenta2.getIntCuenta());
									beanSocioCuenta.setStrNumeroCuenta(socioCuenta2.getStrNumeroCuenta());
									beanSocioCuenta.setIntCondicionCuenta(socioCuenta2.getIntCondicionCuenta());
									beanSocioCuenta.setIntSubCondicionCuenta(socioCuenta2.getIntSubCondicionCuenta());
									beanSocioCuenta.setIntSituacionCuenta(socioCuenta2.getIntSituacionCuenta());
									beanSocioCuenta.setStrFechaApertura(socioCuenta2.getStrFechaApertura());
									beanSocioCuenta.setStrFechaLiquidacion(socioCuenta2.getStrFechaLiquidacion());
									break;
								}
							}
	 					}
						log.info("CUENTA SELECCIONADA: "+intCtasSocBusqueda);
						for (DataBeanEstadoCuentaSocioCuenta cuentas : lstDataBeanEstadoCuentaSocioCuenta) {
							cuentas.setStrDescSituacionCuenta(cuentas.getIntSituacionCuenta().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)?"VIGENTE":"LIQUIDADO");
							if (cuentas.getIntSituacionCuenta().equals(Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO) && cuentas.getStrExisteExpedienteLiquidacion().equals("SI")) {
								ctaLiq = new Tabla();
								ctaLiq.setStrDescripcion(cuentas.getStrNumeroCuenta());
								ctaLiq.setStrAbreviatura(cuentas.getStrFechaLiquidacion());
								lstCuentasLiquidadas.add(ctaLiq);			
							}
						}
						log.info("------------- OBTENER SOCIO ESTRUCTURA -------------");
						lstDataBeanEstadoCuentaSocioEstructura = estadoCuentaFacade.getCabSocioEstructura(SESION_IDEMPRESA, intCodigoPersona);
						if (lstDataBeanEstadoCuentaSocioEstructura!=null && !lstDataBeanEstadoCuentaSocioEstructura.isEmpty()) {
							for (DataBeanEstadoCuentaSocioEstructura socioEstructura : lstDataBeanEstadoCuentaSocioEstructura) {
								beanSocioEstructura.setIntIdPersona(socioEstructura.getIntIdPersona());
								beanSocioEstructura.setStrNombreEstructura(socioEstructura.getStrNombreEstructura());
								beanSocioEstructura.setIntIdSucursal(socioEstructura.getIntIdSucursal());
								beanSocioEstructura.setStrSucursal(socioEstructura.getStrSucursal());
								beanSocioEstructura.setIntCondicionLaboral(socioEstructura.getIntCondicionLaboral());
								beanSocioEstructura.setIntTipoSocio(socioEstructura.getIntTipoSocio());
								beanSocioEstructura.setIntModalidad(socioEstructura.getIntModalidad());
								beanSocioEstructura.setStrDocIdent(socioEstructura.getStrDocIdent());
								beanSocioEstructura.setIntTipoArchivoFirma(socioEstructura.getIntTipoArchivoFirma());
								beanSocioEstructura.setIntItemArchivoFirma(socioEstructura.getIntItemArchivoFirma());
								beanSocioEstructura.setIntItemHistoricoFirma(socioEstructura.getIntItemHistoricoFirma());
								beanSocioEstructura.setIntTipoArchivoFoto(socioEstructura.getIntTipoArchivoFoto());
								beanSocioEstructura.setIntItemArchivoFoto(socioEstructura.getIntItemArchivoFoto());
								beanSocioEstructura.setIntItemHistoricoFoto(socioEstructura.getIntItemHistoricoFoto());
								//JCHAVEZ 22.01.2014 --> Recupera Nro. de Doc.Ident.
								beanSocioEstructura.setStrDocIdent(socioEstructura.getStrDocIdent());								
							}
						}
						
						//Obteniendo la Firma
						if(beanSocioEstructura.getIntTipoArchivoFirma()!=null && beanSocioEstructura.getIntItemArchivoFirma()!=null
								&& beanSocioEstructura.getIntItemHistoricoFirma()!=null){
							//Obteniendo el contrato 
							ArchivoId archivoId = new ArchivoId();
							archivoId.setIntParaTipoCod(beanSocioEstructura.getIntTipoArchivoFirma());
							archivoId.setIntItemArchivo(beanSocioEstructura.getIntItemArchivoFirma());
							archivoId.setIntItemHistorico(beanSocioEstructura.getIntItemHistoricoFirma());
							GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
							Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
							beanSocioEstructura.setFirma(archivo);
						}
						
						//Obteniendo la Foto
						if(beanSocioEstructura.getIntTipoArchivoFoto()!=null && beanSocioEstructura.getIntItemArchivoFoto()!=null
								&& beanSocioEstructura.getIntItemHistoricoFoto()!=null){
							//Obteniendo el contrato 
							ArchivoId archivoId = new ArchivoId();
							archivoId.setIntParaTipoCod(beanSocioEstructura.getIntTipoArchivoFoto());
							archivoId.setIntItemArchivo(beanSocioEstructura.getIntItemArchivoFoto());
							archivoId.setIntItemHistorico(beanSocioEstructura.getIntItemHistoricoFoto());
							GeneralFacadeRemote generalFacade = (GeneralFacadeRemote) EJBFactory.getRemote(GeneralFacadeRemote.class);
							Archivo archivo = generalFacade.getArchivoPorPK(archivoId);
							beanSocioEstructura.setFoto(archivo);
						}				
						
						//Mostrar la Firma del Socio
						if(beanSocioEstructura.getFirma()!=null){
							Archivo firma = beanSocioEstructura.getFirma();
							log.info("Path Firma: "+firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
							File fileTemp = new File(firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
							if(fileTemp.exists()){
								byte[] byteImg = FileUtil.getDataImage(firma.getTipoarchivo().getStrRuta()+"\\"+firma.getStrNombrearchivo());
								MyFile file = new MyFile();
						        file.setLength(byteImg.length);
						        file.setName(firma.getStrNombrearchivo());
						        file.setData(byteImg);
						        setFileFirmaSocio(file);
							}
						}
						
						//Mostrar la Foto del Socio
						if(beanSocioEstructura.getFoto()!=null){
							Archivo foto = beanSocioEstructura.getFoto();
							log.info("Path Foto: "+foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
							File fileTemp = new File(foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
							if(fileTemp.exists()){
								byte[] byteImg = FileUtil.getDataImage(foto.getTipoarchivo().getStrRuta()+"\\"+foto.getStrNombrearchivo());
								MyFile file = new MyFile();
						        file.setLength(byteImg.length);
						        file.setName(foto.getStrNombrearchivo());
						        file.setData(byteImg);
						        setFileFotoSocio(file);
							}
						}
					
						lstDataBeanEstadoCuentaMontosBeneficios = estadoCuentaFacade.getCabMtosBeneficios(SESION_IDEMPRESA, intCtasSocBusqueda);
						if (lstDataBeanEstadoCuentaMontosBeneficios!=null && !lstDataBeanEstadoCuentaMontosBeneficios.isEmpty()) {
							for (DataBeanEstadoCuentaMontosBeneficios mtoBeneficios : lstDataBeanEstadoCuentaMontosBeneficios) {
								beanMontosBeneficios.setIntCuenta(mtoBeneficios.getIntCuenta());
								beanMontosBeneficios.setBdCtaAporte(mtoBeneficios.getBdCtaAporte());
								beanMontosBeneficios.setBdSaldoAporte(mtoBeneficios.getBdSaldoAporte());
								beanMontosBeneficios.setBdMontoFdoRetiro(mtoBeneficios.getBdMontoFdoRetiro());
								beanMontosBeneficios.setBdMontoFdoSepelio(mtoBeneficios.getBdMontoFdoSepelio());
								beanMontosBeneficios.setBdMontoMantenimiento(mtoBeneficios.getBdMontoMantenimiento());
								
								if (bdUltimoEnvioAportes==null) {
									bdUltimoEnvioAportes = mtoBeneficios.getBdMontoUltimoEnvioAportes();
								}
								if (bdUltimoEnvioFdoSepelio==null) {
									bdUltimoEnvioFdoSepelio = mtoBeneficios.getBdMontoUltimoEnvioFdoSepelio();				
								}
								if (bdUltimoEnvioFdoRetiro==null) {
									bdUltimoEnvioFdoRetiro = mtoBeneficios.getBdMontoUltimoEnvioFdoRetiro();
								}
								if (bdUltimoEnvioMant==null) {
									bdUltimoEnvioMant = mtoBeneficios.getBdMontoUltimoEnvioMant();
								}
								
							}
						}
						if (bdUltimoEnvioAportes==null) bdUltimoEnvioAportes = BigDecimal.ZERO;
						if (bdUltimoEnvioFdoSepelio==null) bdUltimoEnvioFdoSepelio = BigDecimal.ZERO;
						if (bdUltimoEnvioFdoRetiro==null) bdUltimoEnvioFdoRetiro = BigDecimal.ZERO;
						if (bdUltimoEnvioMant==null) bdUltimoEnvioMant = BigDecimal.ZERO;
						
						bdSumatoriaUltimoEnvio = bdSumatoriaUltimoEnvio.add(bdUltimoEnvioAportes).add(bdUltimoEnvioFdoRetiro).add(bdUltimoEnvioFdoSepelio).add(bdUltimoEnvioMant);
						obtenerDatosTabResumen();
					}else {
						limpiarFormulario();
					}
				}else {
					limpiarFormulario();
				}
			} catch (Exception e) {
				log.error("Error en obtenerDatosCabecera --> "+e);
			}
		}		
	}
	
	public void obtenerDatosTabResumen() {
		intShowPanel = 1;
		
		blnShowPanelTabs = Boolean.TRUE;
		blnShowPanelTabResumen = Boolean.TRUE;
		blnShowPanelTabDetalle = Boolean.FALSE;	
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 	
		
		lstDataBeanEstadoCuentaResumenPrestamos.clear();
		bdSumatoriaSaldo = BigDecimal.ZERO;
		intAnioUltEnvio = 0;
		intMesUltEnvio = 0;
		
		try {
			lstDataBeanEstadoCuentaResumenPrestamos = estadoCuentaFacade.getResumenPrestamos(SESION_IDEMPRESA, intCtasSocBusqueda);
			if (lstDataBeanEstadoCuentaResumenPrestamos!=null && !lstDataBeanEstadoCuentaResumenPrestamos.isEmpty()) {
				for (DataBeanEstadoCuentaResumenPrestamos lstPrestamos : lstDataBeanEstadoCuentaResumenPrestamos) {
					lstPrestamos.setBdTasaInteres(lstPrestamos.getBdTasaInteres()!=null?lstPrestamos.getBdTasaInteres():BigDecimal.ZERO);
					lstPrestamos.setStrCuotas(lstPrestamos.getIntNumeroCuota()+"/"+lstPrestamos.getIntCtasPagadas()+"/"+lstPrestamos.getIntCtasAtrasadas());
					lstPrestamos.setBdDiferencia((lstPrestamos.getBdUltimoEnvio()!=null?lstPrestamos.getBdUltimoEnvio():BigDecimal.ZERO).subtract(lstPrestamos.getBdMontoEfectuado()!=null?lstPrestamos.getBdMontoEfectuado():BigDecimal.ZERO));
					bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstPrestamos.getBdSaldoCredito()!=null?lstPrestamos.getBdSaldoCredito():BigDecimal.ZERO);
					bdSumatoriaUltimoEnvio = bdSumatoriaUltimoEnvio.add(lstPrestamos.getBdUltimoEnvio()!=null?lstPrestamos.getBdUltimoEnvio():BigDecimal.ZERO);
					intAnioUltEnvio = lstPrestamos.getStrUltimoEnvio()!=null?Integer.valueOf(lstPrestamos.getStrUltimoEnvio().substring(0, 4)):null;
					intMesUltEnvio = lstPrestamos.getStrUltimoEnvio()!=null?Integer.valueOf(lstPrestamos.getStrUltimoEnvio().substring(4, 6)):null;
				}
			}
		} catch (Exception e) {
			log.error("Error en obtenerDatosTabResumen --> "+e);
		}
	}
	
	public void obtenerDatosTabDetalle(){
		log.info("------------------- Debugging EstadoCuentaController.obtenerDatosTabDetalle -------------------");
		intShowPanel = 2;
		List<DataBeanEstadoCuentaDetalleMovimiento> listaDetalleMovimiento = null;
		lstDetalleMovimiento.clear();
		
		blnShowPanelTabs = Boolean.TRUE;
		blnShowPanelTabResumen = Boolean.TRUE;
		blnShowPanelTabDetalle = Boolean.TRUE;	
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 	
		
		Calendar fechaIngresada = Calendar.getInstance();
		listaMovimientoAntesDeFechaIngresada.clear();
		listaMovimientoDespuesDeFechaIngresada.clear();

		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		//INICIALIZANDO SALDOS ACTUALES
		bdSumaMontoAporte = BigDecimal.ZERO;
		bdSumaMontoMantenimiento = BigDecimal.ZERO;
		bdSumaMontoFondoSepelio = BigDecimal.ZERO;
		bdSumaMontoFondoRetiro = BigDecimal.ZERO;
		bdSumaMontoPrestamo = BigDecimal.ZERO;
		bdSumaMontoCredito = BigDecimal.ZERO;
		bdSumaMontoActividad = BigDecimal.ZERO;
		bdSumaMontoMulta = BigDecimal.ZERO;
		bdSumaMontoInteres = BigDecimal.ZERO;
		bdSumaMontoCtaPorPagar = BigDecimal.ZERO;
		bdSumaMontoCtaPorCobrar = BigDecimal.ZERO;		
		try {
			listaDetalleMovimiento = estadoCuentaFacade.getDetalleMovimiento(SESION_IDEMPRESA, intCodigoPersona, intCtasSocBusqueda);

			Integer cont = 1;
			DataBeanEstadoCuentaDetalleMovimiento detalleMovimientoAnterior = null;
			if (listaDetalleMovimiento!=null && !listaDetalleMovimiento.isEmpty()) {
				Integer intTamanioLista = listaDetalleMovimiento.size();
				log.info("Tamaño lista recuperada: "+intTamanioLista);
				//1. Recorremos la lista para separar los movimientos de acuerdo a la fecha ingresada
				fechaIngresada.set(intAnioBusqueda, intMesBusqueda-1, 15);
				for (DataBeanEstadoCuentaDetalleMovimiento detMov : listaDetalleMovimiento) {
					log.info("Fecha del movimiento"+formatoDelTexto.parse(detMov.getStrFechaMovimiento()));
					log.info("Fecha a comparar"+formatoDelTexto.parse(Constante.sdf.format(getUltimoDiaDelMes(fechaIngresada))));
					if (formatoDelTexto.parse(detMov.getStrFechaMovimiento()).compareTo(formatoDelTexto.parse(Constante.sdf.format(getUltimoDiaDelMes(fechaIngresada))))<=0) {
						listaMovimientoAntesDeFechaIngresada.add(detMov);
					}else listaMovimientoDespuesDeFechaIngresada.add(detMov);
				}
				//2. Si la lista de movimientos ANTES de la fecha ingresada no esta vacía...
				movimientoAnteriorResumen = new DataBeanEstadoCuentaDetalleMovimiento();
				movimientoAnteriorResumen.setStrFechaMovimiento(Constante.sdf.format(getUltimoDiaDelMes(fechaIngresada)));
				if (listaMovimientoAntesDeFechaIngresada!=null && !listaMovimientoAntesDeFechaIngresada.isEmpty()) {
					for (DataBeanEstadoCuentaDetalleMovimiento movAnt : listaMovimientoAntesDeFechaIngresada) {
//						if (movAnt.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) {
//							//Beneficios SUMA
//							movimientoAnteriorResumen.setBdSumaMontoAporte((movimientoAnteriorResumen.getBdSumaMontoAporte()!=null?movimientoAnteriorResumen.getBdSumaMontoAporte():BigDecimal.ZERO).add(movAnt.getBdMontoAporte()!=null?movAnt.getBdMontoAporte():BigDecimal.ZERO));
//							movimientoAnteriorResumen.setBdSumaMontoFondoRetiro((movimientoAnteriorResumen.getBdSumaMontoFondoRetiro()!=null?movimientoAnteriorResumen.getBdSumaMontoFondoRetiro():BigDecimal.ZERO).add(movAnt.getBdMontoFondoRetiro()!=null?movAnt.getBdMontoFondoRetiro():BigDecimal.ZERO));
//							//Prestamos RESTA
//							movimientoAnteriorResumen.setBdSumaMontoPrestamo((movimientoAnteriorResumen.getBdSumaMontoPrestamo()!=null?movimientoAnteriorResumen.getBdSumaMontoPrestamo():BigDecimal.ZERO).subtract(movAnt.getBdMontoPrestamo()!=null?movAnt.getBdMontoPrestamo():BigDecimal.ZERO));
//							movimientoAnteriorResumen.setBdSumaMontoCredito((movimientoAnteriorResumen.getBdSumaMontoCredito()!=null?movimientoAnteriorResumen.getBdSumaMontoCredito():BigDecimal.ZERO).subtract(movAnt.getBdMontoCredito()!=null?movAnt.getBdMontoCredito():BigDecimal.ZERO));
//							movimientoAnteriorResumen.setBdSumaMontoActividad((movimientoAnteriorResumen.getBdSumaMontoActividad()!=null?movimientoAnteriorResumen.getBdSumaMontoActividad():BigDecimal.ZERO).subtract(movAnt.getBdMontoActividad()!=null?movAnt.getBdMontoActividad():BigDecimal.ZERO));
//							movimientoAnteriorResumen.setBdSumaMontoMulta((movimientoAnteriorResumen.getBdSumaMontoMulta()!=null?movimientoAnteriorResumen.getBdSumaMontoMulta():BigDecimal.ZERO).subtract(movAnt.getBdMontoMulta()!=null?movAnt.getBdMontoMulta():BigDecimal.ZERO));
//						}
//						if (movAnt.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) {
//							//Beneficios RESTA
//							movimientoAnteriorResumen.setBdSumaMontoAporte((movimientoAnteriorResumen.getBdSumaMontoAporte()!=null?movimientoAnteriorResumen.getBdSumaMontoAporte():BigDecimal.ZERO).subtract(movAnt.getBdMontoAporte()!=null?movAnt.getBdMontoAporte():BigDecimal.ZERO));
//							movimientoAnteriorResumen.setBdSumaMontoFondoRetiro((movimientoAnteriorResumen.getBdSumaMontoFondoRetiro()!=null?movimientoAnteriorResumen.getBdSumaMontoFondoRetiro():BigDecimal.ZERO).subtract(movAnt.getBdMontoFondoRetiro()!=null?movAnt.getBdMontoFondoRetiro():BigDecimal.ZERO));
//							//Prestamos SUMA
//							movimientoAnteriorResumen.setBdSumaMontoPrestamo((movimientoAnteriorResumen.getBdSumaMontoPrestamo()!=null?movimientoAnteriorResumen.getBdSumaMontoPrestamo():BigDecimal.ZERO).add(movAnt.getBdMontoPrestamo()!=null?movAnt.getBdMontoPrestamo():BigDecimal.ZERO));
//							movimientoAnteriorResumen.setBdSumaMontoCredito((movimientoAnteriorResumen.getBdSumaMontoCredito()!=null?movimientoAnteriorResumen.getBdSumaMontoCredito():BigDecimal.ZERO).add(movAnt.getBdMontoCredito()!=null?movAnt.getBdMontoCredito():BigDecimal.ZERO));
//							movimientoAnteriorResumen.setBdSumaMontoActividad((movimientoAnteriorResumen.getBdSumaMontoActividad()!=null?movimientoAnteriorResumen.getBdSumaMontoActividad():BigDecimal.ZERO).add(movAnt.getBdMontoActividad()!=null?movAnt.getBdMontoActividad():BigDecimal.ZERO));
//							movimientoAnteriorResumen.setBdSumaMontoMulta((movimientoAnteriorResumen.getBdSumaMontoMulta()!=null?movimientoAnteriorResumen.getBdSumaMontoMulta():BigDecimal.ZERO).add(movAnt.getBdMontoMulta()!=null?movAnt.getBdMontoMulta():BigDecimal.ZERO));
//						}
						movimientoAnteriorResumen.setBdSumaMontoAporte((movimientoAnteriorResumen.getBdSumaMontoAporte()!=null?movimientoAnteriorResumen.getBdSumaMontoAporte():BigDecimal.ZERO).add(movAnt.getBdMontoAporte()!=null?movAnt.getBdMontoAporte():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoMantenimiento((movimientoAnteriorResumen.getBdSumaMontoMantenimiento()!=null?movimientoAnteriorResumen.getBdSumaMontoMantenimiento():BigDecimal.ZERO).add(movAnt.getBdMontoMantenimiento()!=null?movAnt.getBdMontoMantenimiento():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoFondoSepelio((movimientoAnteriorResumen.getBdSumaMontoFondoSepelio()!=null?movimientoAnteriorResumen.getBdSumaMontoFondoSepelio():BigDecimal.ZERO).add(movAnt.getBdMontoFondoSepelio()!=null?movAnt.getBdMontoFondoSepelio():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoFondoRetiro((movimientoAnteriorResumen.getBdSumaMontoFondoRetiro()!=null?movimientoAnteriorResumen.getBdSumaMontoFondoRetiro():BigDecimal.ZERO).add(movAnt.getBdMontoFondoRetiro()!=null?movAnt.getBdMontoFondoRetiro():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoPrestamo((movimientoAnteriorResumen.getBdSumaMontoPrestamo()!=null?movimientoAnteriorResumen.getBdSumaMontoPrestamo():BigDecimal.ZERO).add(movAnt.getBdMontoPrestamo()!=null?movAnt.getBdMontoPrestamo():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoCredito((movimientoAnteriorResumen.getBdSumaMontoCredito()!=null?movimientoAnteriorResumen.getBdSumaMontoCredito():BigDecimal.ZERO).add(movAnt.getBdMontoCredito()!=null?movAnt.getBdMontoCredito():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoActividad((movimientoAnteriorResumen.getBdSumaMontoActividad()!=null?movimientoAnteriorResumen.getBdSumaMontoActividad():BigDecimal.ZERO).add(movAnt.getBdMontoActividad()!=null?movAnt.getBdMontoActividad():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoMulta((movimientoAnteriorResumen.getBdSumaMontoMulta()!=null?movimientoAnteriorResumen.getBdSumaMontoMulta():BigDecimal.ZERO).add(movAnt.getBdMontoMulta()!=null?movAnt.getBdMontoMulta():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoInteres((movimientoAnteriorResumen.getBdSumaMontoInteres()!=null?movimientoAnteriorResumen.getBdSumaMontoInteres():BigDecimal.ZERO).add(movAnt.getBdMontoInteres()!=null?movAnt.getBdMontoInteres():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoCtaPorPagar((movimientoAnteriorResumen.getBdSumaMontoCtaPorPagar()!=null?movimientoAnteriorResumen.getBdSumaMontoCtaPorPagar():BigDecimal.ZERO).add(movAnt.getBdMontoCtaPorPagar()!=null?movAnt.getBdMontoCtaPorPagar():BigDecimal.ZERO));
						movimientoAnteriorResumen.setBdSumaMontoCtaPorCobrar((movimientoAnteriorResumen.getBdSumaMontoCtaPorCobrar()!=null?movimientoAnteriorResumen.getBdSumaMontoCtaPorCobrar():BigDecimal.ZERO).add(movAnt.getBdMontoCtaPorCobrar()!=null?movAnt.getBdMontoCtaPorCobrar():BigDecimal.ZERO));
					}
					
					bdSumaMontoAporte = movimientoAnteriorResumen.getBdSumaMontoAporte();
					bdSumaMontoMantenimiento = movimientoAnteriorResumen.getBdSumaMontoMantenimiento();
					bdSumaMontoFondoSepelio = movimientoAnteriorResumen.getBdSumaMontoFondoSepelio();
					bdSumaMontoFondoRetiro = movimientoAnteriorResumen.getBdSumaMontoFondoRetiro();
					bdSumaMontoPrestamo = movimientoAnteriorResumen.getBdSumaMontoPrestamo();
					bdSumaMontoCredito = movimientoAnteriorResumen.getBdSumaMontoCredito();
					bdSumaMontoActividad = movimientoAnteriorResumen.getBdSumaMontoActividad();
					bdSumaMontoMulta = movimientoAnteriorResumen.getBdSumaMontoMulta();
					bdSumaMontoInteres = movimientoAnteriorResumen.getBdSumaMontoInteres();
					bdSumaMontoCtaPorPagar = movimientoAnteriorResumen.getBdSumaMontoCtaPorPagar();
					bdSumaMontoCtaPorCobrar = movimientoAnteriorResumen.getBdSumaMontoCtaPorCobrar();	
				}
				//3. si la lista de movimientos DESPUES de la fecha ingresada no esta vacía...
				intTamanioLista = listaMovimientoDespuesDeFechaIngresada.size();
				log.info("Tamaño lista de movimientos DESPUES de la fecha ingresada"+intTamanioLista);
				if (listaMovimientoDespuesDeFechaIngresada!=null && !listaMovimientoDespuesDeFechaIngresada.isEmpty()) {
					for (DataBeanEstadoCuentaDetalleMovimiento detalleMovimientoActual : listaMovimientoDespuesDeFechaIngresada) {
						log.info("Contador ---> "+cont);
						log.info("Detalle ---> "+detalleMovimientoActual);
						if (cont!=1) {
							if (detalleMovimientoActual.getIntItemExpediente()==null && detalleMovimientoActual.getIntItemCuentaConcepto()==null) {
								if (detalleMovimientoActual.getStrFechaMovimiento().equals(detalleMovimientoAnterior.getStrFechaMovimiento()) &&
									detalleMovimientoActual.getIntParaTipoMovimiento().equals(detalleMovimientoAnterior.getIntParaTipoMovimiento()) &&
									(detalleMovimientoActual.getStrTipoNumero()!=null?detalleMovimientoActual.getStrTipoNumero():"").equals(detalleMovimientoAnterior.getStrTipoNumero()!=null?detalleMovimientoAnterior.getStrTipoNumero():"") &&
									(detalleMovimientoActual.getStrNumeroDocumento()!=null?detalleMovimientoActual.getStrNumeroDocumento():"").equals(detalleMovimientoAnterior.getStrNumeroDocumento()!=null?detalleMovimientoAnterior.getStrNumeroDocumento():"")) {
									//CUOTA POR PAGAR
									log.info("CUOTA POR PAGAR");
									detalleMovimientoAnterior.setBdMontoCtaPorPagar(detalleMovimientoAnterior.getBdMontoCtaPorPagar()!=null
											?detalleMovimientoAnterior.getBdMontoCtaPorPagar().add(detalleMovimientoActual.getBdMontoCtaPorPagar()!=null
													?detalleMovimientoActual.getBdMontoCtaPorPagar()
													:BigDecimal.ZERO)
											:detalleMovimientoActual.getBdMontoCtaPorPagar()!=null
													?detalleMovimientoActual.getBdMontoCtaPorPagar()
													:null);
									//CUOTA POR COBRAR
									log.info("CUOTA POR COBRAR");
									detalleMovimientoAnterior.setBdMontoCtaPorCobrar(detalleMovimientoAnterior.getBdMontoCtaPorCobrar()!=null
											?detalleMovimientoAnterior.getBdMontoCtaPorCobrar().add(detalleMovimientoActual.getBdMontoCtaPorCobrar()!=null
													?detalleMovimientoActual.getBdMontoCtaPorCobrar()
													:BigDecimal.ZERO)
											:detalleMovimientoActual.getBdMontoCtaPorCobrar()!=null
													?detalleMovimientoActual.getBdMontoCtaPorCobrar()
													:null);
									if (cont.equals(intTamanioLista)) {
										lstDetalleMovimiento.add(detalleMovimientoAnterior);
									}
									cont++;
								}else{
									if (cont.equals(intTamanioLista)) {
										lstDetalleMovimiento.add(detalleMovimientoAnterior);
										lstDetalleMovimiento.add(detalleMovimientoActual);
									}else{
										lstDetalleMovimiento.add(detalleMovimientoAnterior);
										detalleMovimientoAnterior = new DataBeanEstadoCuentaDetalleMovimiento();
										detalleMovimientoAnterior = detalleMovimientoActual;
										cont++;
									}									
								}
							}
							
							//BENEFICIOS
							if ((detalleMovimientoActual.getIntItemCuentaConcepto()!=null) || (detalleMovimientoActual.getIntItemExpediente()!=null && detalleMovimientoActual.getIntItemExpedienteDetalle()==null)) {
								if (detalleMovimientoAnterior.getIntItemCuentaConcepto()!=null) {
									if (detalleMovimientoActual.getStrFechaMovimiento().equals(detalleMovimientoAnterior.getStrFechaMovimiento()) &&
//										detalleMovimientoActual.getIntItemCuentaConcepto().equals(detalleMovimientoAnterior.getIntItemCuentaConcepto()) &&
//										detalleMovimientoActual.getIntParaTipoConceptoGeneral().equals(detalleMovimientoAnterior.getIntParaTipoConceptoGeneral()) &&
										detalleMovimientoActual.getIntParaTipoMovimiento().equals(detalleMovimientoAnterior.getIntParaTipoMovimiento()) &&
										(detalleMovimientoActual.getStrTipoNumero()!=null?detalleMovimientoActual.getStrTipoNumero():"").equalsIgnoreCase(detalleMovimientoAnterior.getStrTipoNumero()!=null?detalleMovimientoAnterior.getStrTipoNumero():"") &&
										(detalleMovimientoActual.getStrNumeroDocumento()!=null?detalleMovimientoActual.getStrNumeroDocumento():"").equals(detalleMovimientoAnterior.getStrNumeroDocumento()!=null?detalleMovimientoAnterior.getStrNumeroDocumento():"")) {
										//MONTO APORTE
										log.info("MONTO APORTE");
										detalleMovimientoAnterior.setBdMontoAporte(detalleMovimientoAnterior.getBdMontoAporte()!=null
												?detalleMovimientoAnterior.getBdMontoAporte().add(detalleMovimientoActual.getBdMontoAporte()!=null
														?detalleMovimientoActual.getBdMontoAporte()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoAporte()!=null
														?detalleMovimientoActual.getBdMontoAporte()
														:null);
										//MONTO MANTENIMIENTO
										log.info("MONTO MANTENIMIENTO");
										detalleMovimientoAnterior.setBdMontoMantenimiento(detalleMovimientoAnterior.getBdMontoMantenimiento()!=null
												?detalleMovimientoAnterior.getBdMontoMantenimiento().add(detalleMovimientoActual.getBdMontoMantenimiento()!=null
														?detalleMovimientoActual.getBdMontoMantenimiento()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoMantenimiento()!=null
														?detalleMovimientoActual.getBdMontoMantenimiento()
														:null);
										//MONTO FONDO SEPELIO
										log.info("MONTO FONDO SEPELIO");
										detalleMovimientoAnterior.setBdMontoFondoSepelio(detalleMovimientoAnterior.getBdMontoFondoSepelio()!=null
												?detalleMovimientoAnterior.getBdMontoFondoSepelio().add(detalleMovimientoActual.getBdMontoFondoSepelio()!=null
														?detalleMovimientoActual.getBdMontoFondoSepelio()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoFondoSepelio()!=null
														?detalleMovimientoActual.getBdMontoFondoSepelio()
														:null);
										//MONTO FONDO RETIRO
										log.info("MONTO FONDO RETIRO");
										detalleMovimientoAnterior.setBdMontoFondoRetiro(detalleMovimientoAnterior.getBdMontoFondoRetiro()!=null
												?detalleMovimientoAnterior.getBdMontoFondoRetiro().add(detalleMovimientoActual.getBdMontoFondoRetiro()!=null
														?detalleMovimientoActual.getBdMontoFondoRetiro()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoFondoRetiro()!=null
														?detalleMovimientoActual.getBdMontoFondoRetiro()
														:null);
										//CUOTA POR PAGAR
										log.info("CUOTA POR PAGAR");
										detalleMovimientoAnterior.setBdMontoCtaPorPagar(detalleMovimientoAnterior.getBdMontoCtaPorPagar()!=null
												?detalleMovimientoAnterior.getBdMontoCtaPorPagar().add(detalleMovimientoActual.getBdMontoCtaPorPagar()!=null
														?detalleMovimientoActual.getBdMontoCtaPorPagar()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoCtaPorPagar()!=null
														?detalleMovimientoActual.getBdMontoCtaPorPagar()
														:null);
										//CUOTA POR COBRAR
										log.info("CUOTA POR COBRAR");
										detalleMovimientoAnterior.setBdMontoCtaPorCobrar(detalleMovimientoAnterior.getBdMontoCtaPorCobrar()!=null
												?detalleMovimientoAnterior.getBdMontoCtaPorCobrar().add(detalleMovimientoActual.getBdMontoCtaPorCobrar()!=null
														?detalleMovimientoActual.getBdMontoCtaPorCobrar()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoCtaPorCobrar()!=null
														?detalleMovimientoActual.getBdMontoCtaPorCobrar()
														:null);
										if (cont.equals(intTamanioLista)) {
											lstDetalleMovimiento.add(detalleMovimientoAnterior);
										}
										cont++;
									}else{
										if (cont.equals(intTamanioLista)) {
											lstDetalleMovimiento.add(detalleMovimientoAnterior);
											lstDetalleMovimiento.add(detalleMovimientoActual);
										}else{
											lstDetalleMovimiento.add(detalleMovimientoAnterior);
											detalleMovimientoAnterior = new DataBeanEstadoCuentaDetalleMovimiento();
											detalleMovimientoAnterior = detalleMovimientoActual;
											cont++;
										}									
									}
								}else{
									if (cont.equals(intTamanioLista)) {
										lstDetalleMovimiento.add(detalleMovimientoAnterior);
										lstDetalleMovimiento.add(detalleMovimientoActual);
									}else{
										lstDetalleMovimiento.add(detalleMovimientoAnterior);
										detalleMovimientoAnterior = new DataBeanEstadoCuentaDetalleMovimiento();
										detalleMovimientoAnterior = detalleMovimientoActual;
										cont++;
									}								
								}
							} 
							
							//PRESTAMOS
							if (detalleMovimientoActual.getIntItemExpediente()!=null && detalleMovimientoActual.getIntItemExpedienteDetalle()!=null) {
								if (detalleMovimientoAnterior.getIntItemExpediente()!=null) {
									if (detalleMovimientoActual.getStrFechaMovimiento().equals(detalleMovimientoAnterior.getStrFechaMovimiento()) &&
										detalleMovimientoActual.getIntItemExpediente().equals(detalleMovimientoAnterior.getIntItemExpediente()) &&
										detalleMovimientoActual.getIntItemExpedienteDetalle().equals(detalleMovimientoAnterior.getIntItemExpedienteDetalle()) &&
//												detalleMovimientoActual.getIntParaTipoConceptoGeneral().equals(detalleMovimientoAnterior.getIntParaTipoConceptoGeneral()) &&
										detalleMovimientoActual.getIntParaTipoMovimiento().equals(detalleMovimientoAnterior.getIntParaTipoMovimiento()) &&
										(detalleMovimientoActual.getStrTipoNumero()!=null?detalleMovimientoActual.getStrTipoNumero():"").equalsIgnoreCase(detalleMovimientoAnterior.getStrTipoNumero()!=null?detalleMovimientoAnterior.getStrTipoNumero():"") &&
										(detalleMovimientoActual.getStrNumeroDocumento()!=null?detalleMovimientoActual.getStrNumeroDocumento():"").equals(detalleMovimientoAnterior.getStrNumeroDocumento()!=null?detalleMovimientoAnterior.getStrNumeroDocumento():"")) {
										//MONTO PRESTAMO
										log.info("MONTO PRESTAMO");
										detalleMovimientoAnterior.setBdMontoPrestamo(detalleMovimientoAnterior.getBdMontoPrestamo()!=null
												?detalleMovimientoAnterior.getBdMontoPrestamo().add(detalleMovimientoActual.getBdMontoPrestamo()!=null
														?detalleMovimientoActual.getBdMontoPrestamo()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoPrestamo()!=null
														?detalleMovimientoActual.getBdMontoPrestamo()
														:null);
										//MONTO CREDITO
										log.info("MONTO CREDITO");
										detalleMovimientoAnterior.setBdMontoCredito(detalleMovimientoAnterior.getBdMontoCredito()!=null
												?detalleMovimientoAnterior.getBdMontoCredito().add(detalleMovimientoActual.getBdMontoCredito()!=null
														?detalleMovimientoActual.getBdMontoCredito()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoCredito()!=null
														?detalleMovimientoActual.getBdMontoCredito()
														:null);
										//MONTO ACTIVIDAD
										log.info("MONTO ACTIVIDAD");
										detalleMovimientoAnterior.setBdMontoActividad(detalleMovimientoAnterior.getBdMontoActividad()!=null
												?detalleMovimientoAnterior.getBdMontoActividad().add(detalleMovimientoActual.getBdMontoActividad()!=null
														?detalleMovimientoActual.getBdMontoActividad()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoActividad()!=null
														?detalleMovimientoActual.getBdMontoActividad()
														:null);
										//MONTO MULTA
										log.info("MONTO MULTA");
										detalleMovimientoAnterior.setBdMontoMulta(detalleMovimientoAnterior.getBdMontoMulta()!=null
												?detalleMovimientoAnterior.getBdMontoMulta().add(detalleMovimientoActual.getBdMontoMulta()!=null
														?detalleMovimientoActual.getBdMontoMulta()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoMulta()!=null
														?detalleMovimientoActual.getBdMontoMulta()
														:null);
										//MONTO INTERES
										log.info("MONTO INTERES");
										detalleMovimientoAnterior.setBdMontoInteres(detalleMovimientoAnterior.getBdMontoInteres()!=null
												?detalleMovimientoAnterior.getBdMontoInteres().add(detalleMovimientoActual.getBdMontoInteres()!=null
														?detalleMovimientoActual.getBdMontoInteres()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoInteres()!=null
														?detalleMovimientoActual.getBdMontoInteres()
														:null);
										//CUOTA POR PAGAR
										log.info("CUOTA POR PAGAR");
										detalleMovimientoAnterior.setBdMontoCtaPorPagar(detalleMovimientoAnterior.getBdMontoCtaPorPagar()!=null
												?detalleMovimientoAnterior.getBdMontoCtaPorPagar().add(detalleMovimientoActual.getBdMontoCtaPorPagar()!=null
														?detalleMovimientoActual.getBdMontoCtaPorPagar()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoCtaPorPagar()!=null
														?detalleMovimientoActual.getBdMontoCtaPorPagar()
														:null);
										//CUOTA POR COBRAR
										log.info("CUOTA POR COBRAR");
										detalleMovimientoAnterior.setBdMontoCtaPorCobrar(detalleMovimientoAnterior.getBdMontoCtaPorCobrar()!=null
												?detalleMovimientoAnterior.getBdMontoCtaPorCobrar().add(detalleMovimientoActual.getBdMontoCtaPorCobrar()!=null
														?detalleMovimientoActual.getBdMontoCtaPorCobrar()
														:BigDecimal.ZERO)
												:detalleMovimientoActual.getBdMontoCtaPorCobrar()!=null
														?detalleMovimientoActual.getBdMontoCtaPorCobrar()
														:null);

										if (cont.equals(intTamanioLista)) {
											lstDetalleMovimiento.add(detalleMovimientoAnterior);
										}
										cont++;
									}else{
										if (cont.equals(intTamanioLista)) {
											lstDetalleMovimiento.add(detalleMovimientoAnterior);
											lstDetalleMovimiento.add(detalleMovimientoActual);
										}else{
											lstDetalleMovimiento.add(detalleMovimientoAnterior);
											detalleMovimientoAnterior = new DataBeanEstadoCuentaDetalleMovimiento();
											detalleMovimientoAnterior = detalleMovimientoActual;
											cont++;
										}									
									}
								}else{
									if (cont.equals(intTamanioLista)) {
										lstDetalleMovimiento.add(detalleMovimientoAnterior);
										lstDetalleMovimiento.add(detalleMovimientoActual);
									}else{
										lstDetalleMovimiento.add(detalleMovimientoAnterior);
										detalleMovimientoAnterior = new DataBeanEstadoCuentaDetalleMovimiento();
										detalleMovimientoAnterior = detalleMovimientoActual;
										cont++;
									}
								}
							}
								
								
								
								
//********************************************************************************************************************************
						//VALIDACION DEL 1° REGISTRO
						}else{
							if (cont.equals(intTamanioLista)) {
								lstDetalleMovimiento.add(detalleMovimientoActual);
							}else{
								detalleMovimientoAnterior = new DataBeanEstadoCuentaDetalleMovimiento();
								detalleMovimientoAnterior = detalleMovimientoActual;
								cont ++;
							}
						}						
					}
				}
				
				log.info("Lista Detallada de Movimientos: "+lstDetalleMovimiento);
				//4. Recorremos la lista para sumar filas y columnas
				DataBeanEstadoCuentaDetalleMovimiento sumatoriaColumnas = new DataBeanEstadoCuentaDetalleMovimiento(); 
				if (lstDetalleMovimiento!=null && !lstDetalleMovimiento.isEmpty()) {
					BigDecimal bdMontoAportePositivo = BigDecimal.ZERO;
					BigDecimal bdMontoMantenimientoPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoFondoSepelioPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoFondoRetiroPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoPrestamoPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoCreditoPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoActividadPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoMultaPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoInteresPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoCtaPorPagarPositivo = BigDecimal.ZERO;
					BigDecimal bdMontoCtaPorCobrarPositivo = BigDecimal.ZERO;
					
					for (DataBeanEstadoCuentaDetalleMovimiento lista : lstDetalleMovimiento) {
						//ASIGNACIÓN DE SIGNOS
						//MONTO APORTE
						if (lista.getBdMontoAporte()!=null) {
							if (lista.getBdMontoAporte().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoAporte().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoAporte(1);
							}
							if (lista.getBdMontoAporte().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoAporte(-1);
							}
						}
						//MONTO MANTENIMIENTO
						if (lista.getBdMontoMantenimiento()!=null) {
							if (lista.getBdMontoMantenimiento().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoMantenimiento().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoMantenimiento(1);
							}
							if (lista.getBdMontoMantenimiento().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoMantenimiento(-1);
							}
						}
						//MONTO FONDO SEPELIO
						if (lista.getBdMontoFondoSepelio()!=null) {
							if (lista.getBdMontoFondoSepelio().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoFondoSepelio().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoFondoSepelio(1);
							}
							if (lista.getBdMontoFondoSepelio().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoFondoSepelio(-1);
							}
						}
						//MONTO FONDO RETIRO
						if (lista.getBdMontoFondoRetiro()!=null) {
							if (lista.getBdMontoFondoRetiro().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoFondoRetiro().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoFondoRetiro(1);
							}
							if (lista.getBdMontoFondoRetiro().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoFondoRetiro(-1);
							}
						}
						//MONTO PRESTAMO
						if (lista.getBdMontoPrestamo()!=null) {
							if (lista.getBdMontoPrestamo().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoPrestamo().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoPrestamo(1);
							}
							if (lista.getBdMontoPrestamo().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoPrestamo(-1);
							}
						}
						//MONTO CREDITO
						if (lista.getBdMontoCredito()!=null) {
							if (lista.getBdMontoCredito().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoCredito().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoCredito(1);
							}
							if (lista.getBdMontoCredito().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoCredito(-1);
							}
						}
						//MONTO ACTIVIDAD
						if (lista.getBdMontoActividad()!=null) {
							if (lista.getBdMontoActividad().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoActividad().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoActividad(1);
							}
							if (lista.getBdMontoActividad().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoActividad(-1);
							}
						}
						//MONTO MULTA
						if (lista.getBdMontoMulta()!=null) {
							if (lista.getBdMontoMulta().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoMulta().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoMulta(1);
							}
							if (lista.getBdMontoMulta().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoMulta(-1);
							}
						}
						//MONTO INTERES
						if (lista.getBdMontoInteres()!=null) {
							if (lista.getBdMontoInteres().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoInteres().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoInteres(1);
							}
							if (lista.getBdMontoInteres().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoInteres(-1);
							}
						}
						//MONTO CTA. POR PAGAR
						if (lista.getBdMontoCtaPorPagar()!=null) {
							if (lista.getBdMontoCtaPorPagar().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoCtaPorPagar().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoCtaPorPagar(1);
							}
							if (lista.getBdMontoCtaPorPagar().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoCtaPorPagar(-1);
							}
						}
						//MONTO CTA. POR COBRAR
						if (lista.getBdMontoCtaPorCobrar()!=null) {
							if (lista.getBdMontoCtaPorCobrar().compareTo(BigDecimal.ZERO)==1 || lista.getBdMontoCtaPorCobrar().compareTo(BigDecimal.ZERO)==0) {
								lista.setIntSignoMontoCtaPorCobrar(1);
							}
							if (lista.getBdMontoCtaPorCobrar().compareTo(BigDecimal.ZERO)==-1) {
								lista.setIntSignoMontoCtaPorCobrar(-1);
							}
						}
						
						//SETEO LOS VALORES A POSITIVO PARA REALIZAR LA SUMA DE FILAS
						bdMontoAportePositivo = lista.getBdMontoAporte()!=null?lista.getBdMontoAporte().multiply(new BigDecimal(lista.getIntSignoMontoAporte())):BigDecimal.ZERO;
						bdMontoMantenimientoPositivo = lista.getBdMontoMantenimiento()!=null?lista.getBdMontoMantenimiento().multiply(new BigDecimal(lista.getIntSignoMontoMantenimiento())):BigDecimal.ZERO;
						bdMontoFondoSepelioPositivo = lista.getBdMontoFondoSepelio()!=null?lista.getBdMontoFondoSepelio().multiply(new BigDecimal(lista.getIntSignoMontoFondoSepelio())):BigDecimal.ZERO;
						bdMontoFondoRetiroPositivo = lista.getBdMontoFondoRetiro()!=null?lista.getBdMontoFondoRetiro().multiply(new BigDecimal(lista.getIntSignoMontoFondoRetiro())):BigDecimal.ZERO;
						bdMontoPrestamoPositivo = lista.getBdMontoPrestamo()!=null?lista.getBdMontoPrestamo().multiply(new BigDecimal(lista.getIntSignoMontoPrestamo())):BigDecimal.ZERO;
						bdMontoCreditoPositivo = lista.getBdMontoCredito()!=null?lista.getBdMontoCredito().multiply(new BigDecimal(lista.getIntSignoMontoCredito())):BigDecimal.ZERO;
						bdMontoActividadPositivo = lista.getBdMontoActividad()!=null?lista.getBdMontoActividad().multiply(new BigDecimal(lista.getIntSignoMontoActividad())):BigDecimal.ZERO;
						bdMontoMultaPositivo = lista.getBdMontoMulta()!=null?lista.getBdMontoMulta().multiply(new BigDecimal(lista.getIntSignoMontoMulta())):BigDecimal.ZERO;
						bdMontoInteresPositivo = lista.getBdMontoInteres()!=null?lista.getBdMontoInteres().multiply(new BigDecimal(lista.getIntSignoMontoInteres())):BigDecimal.ZERO;
						bdMontoCtaPorPagarPositivo = lista.getBdMontoCtaPorPagar()!=null?lista.getBdMontoCtaPorPagar().multiply(new BigDecimal(lista.getIntSignoMontoCtaPorPagar())):BigDecimal.ZERO;
						bdMontoCtaPorCobrarPositivo = lista.getBdMontoCtaPorCobrar()!=null?lista.getBdMontoCtaPorCobrar().multiply(new BigDecimal(lista.getIntSignoMontoCtaPorCobrar())):BigDecimal.ZERO;
						
						lista.setBdSumaMontoFila(bdMontoAportePositivo.add(bdMontoMantenimientoPositivo).add(bdMontoFondoSepelioPositivo).add(bdMontoFondoRetiroPositivo)
												 .add(bdMontoPrestamoPositivo).add(bdMontoCreditoPositivo).add(bdMontoActividadPositivo).add(bdMontoMultaPositivo)
												 .add(bdMontoInteresPositivo).add(bdMontoCtaPorPagarPositivo).add(bdMontoCtaPorCobrarPositivo));
						
//						lista.setBdSumaMontoFila(
//								(lista.getBdMontoAporte()!=null?lista.getBdMontoAporte():BigDecimal.ZERO).add(
//										lista.getBdMontoMantenimiento()!=null?lista.getBdMontoMantenimiento():BigDecimal.ZERO
//										).add(
//												lista.getBdMontoFondoSepelio()!=null?lista.getBdMontoFondoSepelio():BigDecimal.ZERO
//												).add(
//														lista.getBdMontoFondoRetiro()!=null?lista.getBdMontoFondoRetiro():BigDecimal.ZERO
//														).add(
//																lista.getBdMontoPrestamo()!=null?lista.getBdMontoPrestamo():BigDecimal.ZERO
//																).add(
//																		lista.getBdMontoCredito()!=null?lista.getBdMontoCredito():BigDecimal.ZERO
//																		).add(
//																				lista.getBdMontoActividad()!=null?lista.getBdMontoActividad():BigDecimal.ZERO
//																				).add(
//																						lista.getBdMontoMulta()!=null?lista.getBdMontoMulta():BigDecimal.ZERO
//																						).add(
//																								lista.getBdMontoInteres()!=null?lista.getBdMontoInteres():BigDecimal.ZERO
//																								).add(
//																										lista.getBdMontoCtaPorPagar()!=null?lista.getBdMontoCtaPorPagar():BigDecimal.ZERO
//																										).add(
//																												lista.getBdMontoCtaPorCobrar()!=null?lista.getBdMontoCtaPorCobrar():BigDecimal.ZERO
//																												)																									
//						);
						
																		
//						if (lista.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) {
//							//Beneficios SUMA
//							sumatoriaColumnas.setBdSumaMontoAporte((sumatoriaColumnas.getBdSumaMontoAporte()!=null?sumatoriaColumnas.getBdSumaMontoAporte():BigDecimal.ZERO).add(lista.getBdMontoAporte()!=null?lista.getBdMontoAporte():BigDecimal.ZERO));
//							sumatoriaColumnas.setBdSumaMontoFondoRetiro((sumatoriaColumnas.getBdSumaMontoFondoRetiro()!=null?sumatoriaColumnas.getBdSumaMontoFondoRetiro():BigDecimal.ZERO).add(lista.getBdMontoFondoRetiro()!=null?lista.getBdMontoFondoRetiro():BigDecimal.ZERO));
//							//Prestamos RESTA
//							sumatoriaColumnas.setBdSumaMontoPrestamo((sumatoriaColumnas.getBdSumaMontoPrestamo()!=null?sumatoriaColumnas.getBdSumaMontoPrestamo():BigDecimal.ZERO).subtract(lista.getBdMontoPrestamo()!=null?lista.getBdMontoPrestamo():BigDecimal.ZERO));
//							sumatoriaColumnas.setBdSumaMontoCredito((sumatoriaColumnas.getBdSumaMontoCredito()!=null?sumatoriaColumnas.getBdSumaMontoCredito():BigDecimal.ZERO).subtract(lista.getBdMontoCredito()!=null?lista.getBdMontoCredito():BigDecimal.ZERO));
//							sumatoriaColumnas.setBdSumaMontoActividad((sumatoriaColumnas.getBdSumaMontoActividad()!=null?sumatoriaColumnas.getBdSumaMontoActividad():BigDecimal.ZERO).subtract(lista.getBdMontoActividad()!=null?lista.getBdMontoActividad():BigDecimal.ZERO));
//							sumatoriaColumnas.setBdSumaMontoMulta((sumatoriaColumnas.getBdSumaMontoMulta()!=null?sumatoriaColumnas.getBdSumaMontoMulta():BigDecimal.ZERO).subtract(lista.getBdMontoMulta()!=null?lista.getBdMontoMulta():BigDecimal.ZERO));
//						}
//						if (lista.getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) {
//							//Beneficios RESTA
//							sumatoriaColumnas.setBdSumaMontoAporte((sumatoriaColumnas.getBdSumaMontoAporte()!=null?sumatoriaColumnas.getBdSumaMontoAporte():BigDecimal.ZERO).subtract(lista.getBdMontoAporte()!=null?lista.getBdMontoAporte():BigDecimal.ZERO));
//							sumatoriaColumnas.setBdSumaMontoFondoRetiro((sumatoriaColumnas.getBdSumaMontoFondoRetiro()!=null?sumatoriaColumnas.getBdSumaMontoFondoRetiro():BigDecimal.ZERO).subtract(lista.getBdMontoFondoRetiro()!=null?lista.getBdMontoFondoRetiro():BigDecimal.ZERO));
//							//Prestamos SUMA
//							sumatoriaColumnas.setBdSumaMontoPrestamo((sumatoriaColumnas.getBdSumaMontoPrestamo()!=null?sumatoriaColumnas.getBdSumaMontoPrestamo():BigDecimal.ZERO).add(lista.getBdMontoPrestamo()!=null?lista.getBdMontoPrestamo():BigDecimal.ZERO));
//							sumatoriaColumnas.setBdSumaMontoCredito((sumatoriaColumnas.getBdSumaMontoCredito()!=null?sumatoriaColumnas.getBdSumaMontoCredito():BigDecimal.ZERO).add(lista.getBdMontoCredito()!=null?lista.getBdMontoCredito():BigDecimal.ZERO));
//							sumatoriaColumnas.setBdSumaMontoActividad((sumatoriaColumnas.getBdSumaMontoActividad()!=null?sumatoriaColumnas.getBdSumaMontoActividad():BigDecimal.ZERO).add(lista.getBdMontoActividad()!=null?lista.getBdMontoActividad():BigDecimal.ZERO));
//							sumatoriaColumnas.setBdSumaMontoMulta((sumatoriaColumnas.getBdSumaMontoMulta()!=null?sumatoriaColumnas.getBdSumaMontoMulta():BigDecimal.ZERO).add(lista.getBdMontoMulta()!=null?lista.getBdMontoMulta():BigDecimal.ZERO));
//						}
						sumatoriaColumnas.setBdSumaMontoAporte((sumatoriaColumnas.getBdSumaMontoAporte()!=null?sumatoriaColumnas.getBdSumaMontoAporte():BigDecimal.ZERO).add(lista.getBdMontoAporte()!=null?lista.getBdMontoAporte():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoMantenimiento((sumatoriaColumnas.getBdSumaMontoMantenimiento()!=null?sumatoriaColumnas.getBdSumaMontoMantenimiento():BigDecimal.ZERO).add(lista.getBdMontoMantenimiento()!=null?lista.getBdMontoMantenimiento():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoFondoSepelio((sumatoriaColumnas.getBdSumaMontoFondoSepelio()!=null?sumatoriaColumnas.getBdSumaMontoFondoSepelio():BigDecimal.ZERO).add(lista.getBdMontoFondoSepelio()!=null?lista.getBdMontoFondoSepelio():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoFondoRetiro((sumatoriaColumnas.getBdSumaMontoFondoRetiro()!=null?sumatoriaColumnas.getBdSumaMontoFondoRetiro():BigDecimal.ZERO).add(lista.getBdMontoFondoRetiro()!=null?lista.getBdMontoFondoRetiro():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoPrestamo((sumatoriaColumnas.getBdSumaMontoPrestamo()!=null?sumatoriaColumnas.getBdSumaMontoPrestamo():BigDecimal.ZERO).add(lista.getBdMontoPrestamo()!=null?lista.getBdMontoPrestamo():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoCredito((sumatoriaColumnas.getBdSumaMontoCredito()!=null?sumatoriaColumnas.getBdSumaMontoCredito():BigDecimal.ZERO).add(lista.getBdMontoCredito()!=null?lista.getBdMontoCredito():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoActividad((sumatoriaColumnas.getBdSumaMontoActividad()!=null?sumatoriaColumnas.getBdSumaMontoActividad():BigDecimal.ZERO).add(lista.getBdMontoActividad()!=null?lista.getBdMontoActividad():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoMulta((sumatoriaColumnas.getBdSumaMontoMulta()!=null?sumatoriaColumnas.getBdSumaMontoMulta():BigDecimal.ZERO).add(lista.getBdMontoMulta()!=null?lista.getBdMontoMulta():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoInteres((sumatoriaColumnas.getBdSumaMontoInteres()!=null?sumatoriaColumnas.getBdSumaMontoInteres():BigDecimal.ZERO).add(lista.getBdMontoInteres()!=null?lista.getBdMontoInteres():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoCtaPorPagar((sumatoriaColumnas.getBdSumaMontoCtaPorPagar()!=null?sumatoriaColumnas.getBdSumaMontoCtaPorPagar():BigDecimal.ZERO).add(lista.getBdMontoCtaPorPagar()!=null?lista.getBdMontoCtaPorPagar():BigDecimal.ZERO));
						sumatoriaColumnas.setBdSumaMontoCtaPorCobrar((sumatoriaColumnas.getBdSumaMontoCtaPorCobrar()!=null?sumatoriaColumnas.getBdSumaMontoCtaPorCobrar():BigDecimal.ZERO).add(lista.getBdMontoCtaPorCobrar()!=null?lista.getBdMontoCtaPorCobrar():BigDecimal.ZERO));
					}
					bdSumaMontoAporte = bdSumaMontoAporte.add(sumatoriaColumnas.getBdSumaMontoAporte());
					bdSumaMontoMantenimiento = bdSumaMontoMantenimiento.add(sumatoriaColumnas.getBdSumaMontoMantenimiento());
					bdSumaMontoFondoSepelio = bdSumaMontoFondoSepelio.add(sumatoriaColumnas.getBdSumaMontoFondoSepelio());
					bdSumaMontoFondoRetiro = bdSumaMontoFondoRetiro.add(sumatoriaColumnas.getBdSumaMontoFondoRetiro());
					bdSumaMontoPrestamo = bdSumaMontoPrestamo.add(sumatoriaColumnas.getBdSumaMontoPrestamo());
					bdSumaMontoCredito = bdSumaMontoCredito.add(sumatoriaColumnas.getBdSumaMontoCredito());
					bdSumaMontoActividad = bdSumaMontoActividad.add(sumatoriaColumnas.getBdSumaMontoActividad());
					bdSumaMontoMulta = bdSumaMontoMulta.add(sumatoriaColumnas.getBdSumaMontoMulta());
					bdSumaMontoInteres = bdSumaMontoInteres.add(sumatoriaColumnas.getBdSumaMontoInteres());
					bdSumaMontoCtaPorPagar = bdSumaMontoCtaPorPagar.add(sumatoriaColumnas.getBdSumaMontoCtaPorPagar());
					bdSumaMontoCtaPorCobrar = bdSumaMontoCtaPorCobrar.add(sumatoriaColumnas.getBdSumaMontoCtaPorCobrar());					
				}
			}
			log.info("Lista a pintar: "+lstDetalleMovimiento);
		} catch (Exception e) {
			log.error("Error en obtenerDatosTabDetalle --> "+e);
		}		
	}
	
	public Date getUltimoDiaDelMes(Calendar fecha) {
		fecha.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH),
				fecha.getActualMaximum(Calendar.DAY_OF_MONTH),
				fecha.getMaximum(Calendar.HOUR_OF_DAY),
				fecha.getMaximum(Calendar.MINUTE),
				fecha.getMaximum(Calendar.SECOND));

		return fecha.getTime();
	}	
	
	public String convertirMontos(BigDecimal bdMonto){
		String strMonto = "";
		//Formato de nro....
		if (bdMonto!=null) {
			DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
			otherSymbols.setDecimalSeparator('.');
			otherSymbols.setGroupingSeparator(','); 
			formato = new DecimalFormat("#,##0.00",otherSymbols);
			strMonto = formato.format(bdMonto);
		}else return strMonto;

        return strMonto;
	}	
	
	public void obtenerDatosTabPlanilla(){
		log.info("------------------------ debuging.obtenerDatosTabPlanilla ------------------------");
		intShowPanel = 0;
		String strPeriodoBusqueda = "";
		blnShowPanelTabs = Boolean.TRUE;
		blnShowPanelTabResumen = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;	
		blnShowPanelTabPlanilla = Boolean.TRUE;
			blnShowPanelTabPlanillaResumen = Boolean.TRUE;
			blnShowPanelTabPlanillaDiferencia = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 	
		lstDataBeanEstadoCuentaPlanillas.clear();		
		
		Integer intPeriodoAnt = 0;
		
		List<DataBeanEstadoCuentaPlanillas> lstDataBeanEstadoCuentaPlanillasAuxiliar = null;
		DataBeanEstadoCuentaPlanillas beanPlanilla = new DataBeanEstadoCuentaPlanillas();
		try {
			if (intMesBusqueda!=-1) {
				strPeriodoBusqueda = intAnioBusqueda+""+(intMesBusqueda<10?"0"+intMesBusqueda:intMesBusqueda);
				lstDataBeanEstadoCuentaPlanillasAuxiliar = estadoCuentaFacade.getResumenPlanilla(SESION_IDEMPRESA, intCtasSocBusqueda, Integer.valueOf(strPeriodoBusqueda));
				
				if (lstDataBeanEstadoCuentaPlanillasAuxiliar!=null && !lstDataBeanEstadoCuentaPlanillasAuxiliar.isEmpty()) {
					for (DataBeanEstadoCuentaPlanillas planilla : lstDataBeanEstadoCuentaPlanillasAuxiliar) {
						if (intPeriodoAnt == 0) {
							beanPlanilla.setIntPeriodo(planilla.getIntPeriodo());
							beanPlanilla.setIntAnioPlanilla(Integer.valueOf(planilla.getIntPeriodo().toString().substring(0,4)));
							beanPlanilla.setIntMesPlanilla(Integer.valueOf(planilla.getIntPeriodo().toString().substring(4,6)));
							beanPlanilla.setBdMontoEnvioHaberes(planilla.getBdMontoEnvioHaberes());
							beanPlanilla.setBdMontoEnvioIncentivos(planilla.getBdMontoEnvioIncentivos());
							beanPlanilla.setBdMontoEnvioCas(planilla.getBdMontoEnvioCas());
							beanPlanilla.setBdMontoTotalEnviado((beanPlanilla.getBdMontoEnvioHaberes()!=null?beanPlanilla.getBdMontoEnvioHaberes():BigDecimal.ZERO)
										.add(beanPlanilla.getBdMontoEnvioIncentivos()!=null?beanPlanilla.getBdMontoEnvioIncentivos():BigDecimal.ZERO)
										.add(beanPlanilla.getBdMontoEnvioCas()!=null?beanPlanilla.getBdMontoEnvioCas():BigDecimal.ZERO));

							beanPlanilla.setBdMontoEfectuadoHaberes(planilla.getBdMontoEfectuadoHaberes());
							beanPlanilla.setBdMontoEfectuadoIncentivos(planilla.getBdMontoEfectuadoIncentivos());
							beanPlanilla.setBdMontoEfectuadoCas(planilla.getBdMontoEfectuadoCas());
							beanPlanilla.setBdMontoTotalEfectuado((beanPlanilla.getBdMontoEfectuadoHaberes()!=null?beanPlanilla.getBdMontoEfectuadoHaberes():BigDecimal.ZERO)
										.add(beanPlanilla.getBdMontoEfectuadoIncentivos()!=null?beanPlanilla.getBdMontoEfectuadoIncentivos():BigDecimal.ZERO)
										.add(beanPlanilla.getBdMontoEfectuadoCas()!=null?beanPlanilla.getBdMontoEfectuadoCas():BigDecimal.ZERO));
	
							beanPlanilla.setBdMontoTotalDiferencia(beanPlanilla.getBdMontoTotalEnviado().subtract(beanPlanilla.getBdMontoTotalEfectuado()));
							beanPlanilla.setStrEstadoPagoHaberes(planilla.getStrEstadoPagoHaberes());
							beanPlanilla.setStrEstadoPagoIncentivos(planilla.getStrEstadoPagoIncentivos());
							beanPlanilla.setStrEstadoPagoCas(planilla.getStrEstadoPagoCas());
							intPeriodoAnt = planilla.getIntPeriodo();
						}else {
							if (planilla.getIntPeriodo().equals(intPeriodoAnt)) {
								if(beanPlanilla.getBdMontoEnvioHaberes()==null) beanPlanilla.setBdMontoEnvioHaberes(planilla.getBdMontoEnvioHaberes());
								if(beanPlanilla.getBdMontoEnvioIncentivos()==null) beanPlanilla.setBdMontoEnvioIncentivos(planilla.getBdMontoEnvioIncentivos());
								if(beanPlanilla.getBdMontoEnvioCas()==null) beanPlanilla.setBdMontoEnvioCas(planilla.getBdMontoEnvioCas());
								beanPlanilla.setBdMontoTotalEnviado((beanPlanilla.getBdMontoEnvioHaberes()!=null?beanPlanilla.getBdMontoEnvioHaberes():BigDecimal.ZERO)
											.add(beanPlanilla.getBdMontoEnvioIncentivos()!=null?beanPlanilla.getBdMontoEnvioIncentivos():BigDecimal.ZERO)
											.add(beanPlanilla.getBdMontoEnvioCas()!=null?beanPlanilla.getBdMontoEnvioCas():BigDecimal.ZERO));
	
								if(beanPlanilla.getBdMontoEfectuadoHaberes()==null) beanPlanilla.setBdMontoEfectuadoHaberes(planilla.getBdMontoEfectuadoHaberes());
								if(beanPlanilla.getBdMontoEfectuadoIncentivos()==null) beanPlanilla.setBdMontoEfectuadoIncentivos(planilla.getBdMontoEfectuadoIncentivos());
								if(beanPlanilla.getBdMontoEfectuadoCas()==null) beanPlanilla.setBdMontoEfectuadoCas(planilla.getBdMontoEfectuadoCas());
								beanPlanilla.setBdMontoTotalEfectuado((beanPlanilla.getBdMontoEfectuadoHaberes()!=null?beanPlanilla.getBdMontoEfectuadoHaberes():BigDecimal.ZERO)
											.add(beanPlanilla.getBdMontoEfectuadoIncentivos()!=null?beanPlanilla.getBdMontoEfectuadoIncentivos():BigDecimal.ZERO)
											.add(beanPlanilla.getBdMontoEfectuadoCas()!=null?beanPlanilla.getBdMontoEfectuadoCas():BigDecimal.ZERO));
		
								beanPlanilla.setBdMontoTotalDiferencia(beanPlanilla.getBdMontoTotalEnviado().subtract(beanPlanilla.getBdMontoTotalEfectuado()));
								if(beanPlanilla.getStrEstadoPagoHaberes()==null) beanPlanilla.setStrEstadoPagoHaberes(planilla.getStrEstadoPagoHaberes());
								if(beanPlanilla.getStrEstadoPagoIncentivos()==null) beanPlanilla.setStrEstadoPagoIncentivos(planilla.getStrEstadoPagoIncentivos());
								if(beanPlanilla.getStrEstadoPagoCas()==null) beanPlanilla.setStrEstadoPagoCas(planilla.getStrEstadoPagoCas());								
							}else {
								lstDataBeanEstadoCuentaPlanillas.add(beanPlanilla);
								beanPlanilla = new DataBeanEstadoCuentaPlanillas();
								beanPlanilla.setIntPeriodo(planilla.getIntPeriodo());
								beanPlanilla.setIntAnioPlanilla(Integer.valueOf(planilla.getIntPeriodo().toString().substring(0,4)));
								beanPlanilla.setIntMesPlanilla(Integer.valueOf(planilla.getIntPeriodo().toString().substring(4,6)));
								beanPlanilla.setBdMontoEnvioHaberes(planilla.getBdMontoEnvioHaberes());
								beanPlanilla.setBdMontoEnvioIncentivos(planilla.getBdMontoEnvioIncentivos());
								beanPlanilla.setBdMontoEnvioCas(planilla.getBdMontoEnvioCas());
								beanPlanilla.setBdMontoTotalEnviado((beanPlanilla.getBdMontoEnvioHaberes()!=null?beanPlanilla.getBdMontoEnvioHaberes():BigDecimal.ZERO)
											.add(beanPlanilla.getBdMontoEnvioIncentivos()!=null?beanPlanilla.getBdMontoEnvioIncentivos():BigDecimal.ZERO)
											.add(beanPlanilla.getBdMontoEnvioCas()!=null?beanPlanilla.getBdMontoEnvioCas():BigDecimal.ZERO));

								beanPlanilla.setBdMontoEfectuadoHaberes(planilla.getBdMontoEfectuadoHaberes());
								beanPlanilla.setBdMontoEfectuadoIncentivos(planilla.getBdMontoEfectuadoIncentivos());
								beanPlanilla.setBdMontoEfectuadoCas(planilla.getBdMontoEfectuadoCas());
								beanPlanilla.setBdMontoTotalEfectuado((beanPlanilla.getBdMontoEfectuadoHaberes()!=null?beanPlanilla.getBdMontoEfectuadoHaberes():BigDecimal.ZERO)
											.add(beanPlanilla.getBdMontoEfectuadoIncentivos()!=null?beanPlanilla.getBdMontoEfectuadoIncentivos():BigDecimal.ZERO)
											.add(beanPlanilla.getBdMontoEfectuadoCas()!=null?beanPlanilla.getBdMontoEfectuadoCas():BigDecimal.ZERO));
		
								beanPlanilla.setBdMontoTotalDiferencia(beanPlanilla.getBdMontoTotalEnviado().subtract(beanPlanilla.getBdMontoTotalEfectuado()));
								beanPlanilla.setStrEstadoPagoHaberes(planilla.getStrEstadoPagoHaberes());
								beanPlanilla.setStrEstadoPagoIncentivos(planilla.getStrEstadoPagoIncentivos());
								beanPlanilla.setStrEstadoPagoCas(planilla.getStrEstadoPagoCas());
								intPeriodoAnt = planilla.getIntPeriodo();
							}
						}
					}
					lstDataBeanEstadoCuentaPlanillas.add(beanPlanilla);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void getListaDiferenciaPlanillaPorPeriodo() throws BusinessException{
		log.info("------------------------ debuging.obtenerDatosTabPlanillaDiferenciaPlanila ------------------------");
		blnShowPanelTabPlanilla = Boolean.TRUE;
		blnShowPanelTabPlanillaResumen = Boolean.FALSE;
		blnShowPanelTabPlanillaDiferencia = Boolean.TRUE;
		String strPeriodoBusqueda = "";
		listaColumnasDiferenciaPlanilla = null;
		listaFilasDiferenciaPlanilla = null;
		try {
			List<Tabla> listaDescPrioridadPorTipoCptoGral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL));
			List<Tabla> listaDescPrioridadPorTipoCredito = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CREDITO));
			strPeriodoBusqueda = intAnioBusqueda+""+(intMesBusqueda<10?"0"+intMesBusqueda:intMesBusqueda);
			listaFilasDiferenciaPlanilla = estadoCuentaFacade.getFilasDifPlanilla(SESION_IDEMPRESA, intCtasSocBusqueda, Integer.valueOf(strPeriodoBusqueda));			
			listaColumnasDiferenciaPlanilla = estadoCuentaFacade.getColumnasDifPlanilla(SESION_IDEMPRESA, intCtasSocBusqueda, Integer.valueOf(strPeriodoBusqueda));
			if (listaColumnasDiferenciaPlanilla!=null && !listaColumnasDiferenciaPlanilla.isEmpty()) {
				for (DataBeanEstadoCuentaPlanillas descripcion : listaColumnasDiferenciaPlanilla) {					
					if (descripcion.getIntTipoConceptoGral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION)) {
						for (Tabla descPrioridad : listaDescPrioridadPorTipoCredito) {
							if (descripcion.getIntCodigoPrioridad()!=null) {
								if(descPrioridad.getIntIdDetalle().compareTo(descripcion.getIntCodigoPrioridad())==0){
									Integer r = descPrioridad.getStrDescripcion().indexOf("-");
									if (r==-1) {
										descripcion.setStrDescripcionDiferencia(descPrioridad.getStrDescripcion());
									}else {
										descripcion.setStrDescripcionDiferencia(descPrioridad.getStrDescripcion().substring(0,r-1));
									}		
									break;
								}
							}
						}
					}else {
						for (Tabla descPrioridad : listaDescPrioridadPorTipoCptoGral) {
							if (descripcion.getIntCodigoPrioridad()!=null) {
								if(descPrioridad.getIntIdDetalle().compareTo(descripcion.getIntCodigoPrioridad())==0){
									Integer r = descPrioridad.getStrDescripcion().indexOf("-");
									if (r==-1) {
										descripcion.setStrDescripcionDiferencia(descPrioridad.getStrDescripcion());
									}else {
										descripcion.setStrDescripcionDiferencia(descPrioridad.getStrDescripcion().substring(0,r-1));
									}		
									break;
								}
							}
						}
					}					
				}
			}		
			
			lstDataBeanEstadoCuentaDiferenciaPlanilla = estadoCuentaFacade.getDiferenciaPlanilla(SESION_IDEMPRESA, intCtasSocBusqueda, Integer.valueOf(strPeriodoBusqueda));

			if ((listaColumnasDiferenciaPlanilla!=null && !listaColumnasDiferenciaPlanilla.isEmpty()) && (listaFilasDiferenciaPlanilla!=null && !listaFilasDiferenciaPlanilla.isEmpty())) {
				for (DataBeanEstadoCuentaPlanillas column : listaColumnasDiferenciaPlanilla) {
					for (DataBeanEstadoCuentaPlanillas row : listaFilasDiferenciaPlanilla) {
						if(row.getBdMontoDiferencia()==null){
							row.setBdMontoDiferencia(new BigDecimal[1]);
						}else{
							BigDecimal values[] = new BigDecimal[row.getBdMontoDiferencia().length+1];
							for(int i=0; i<row.getBdMontoDiferencia().length; i++){
								values[i] = row.getBdMontoDiferencia()[i];
							}
							row.setBdMontoDiferencia(values);
						}
						for (DataBeanEstadoCuentaPlanillas mtoDiferencia : lstDataBeanEstadoCuentaDiferenciaPlanilla) {
							if (row.getIntPeriodo().equals(mtoDiferencia.getIntPeriodo()) && column.getIntOrdenPrioridad().equals(mtoDiferencia.getIntOrdenPrioridad())) {
								log.info(mtoDiferencia.getBdMontoEnviado());
								log.info(mtoDiferencia.getBdMontoEfectuado());
								row.getBdMontoDiferencia()[row.getBdMontoDiferencia().length-1]=(mtoDiferencia.getBdMontoEnviado()!=null?mtoDiferencia.getBdMontoEnviado():BigDecimal.ZERO)
																									.subtract(mtoDiferencia.getBdMontoEfectuado()!=null?mtoDiferencia.getBdMontoEfectuado():BigDecimal.ZERO);
								break;
							}
						}
					}
				}
			}
			
			for (DataBeanEstadoCuentaPlanillas fila : listaFilasDiferenciaPlanilla) {
				fila.setStrPeriodo(formatearPeriodo(Integer.valueOf(fila.getIntPeriodo().toString().substring(0,4)),Integer.valueOf(fila.getIntPeriodo().toString().substring(4,6))));
				BigDecimal bdSumTotal = BigDecimal.ZERO;
				for(int i=0; i<fila.getBdMontoDiferencia().length; i++){
					bdSumTotal = bdSumTotal.add(fila.getBdMontoDiferencia()[i]!=null?fila.getBdMontoDiferencia()[i]:BigDecimal.ZERO);
				}
				fila.setBdSumaDiferenciaPlanilla(bdSumTotal);
			}					
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void retornarTabPlanilla() throws BusinessException{
		blnShowPanelTabPlanillaResumen = Boolean.TRUE;
		blnShowPanelTabPlanillaDiferencia = Boolean.FALSE;
	}
	
	public void obtenerDatosTabDsctoTerceros() throws BusinessException{
		intShowPanel = 0;
		blnShowPanelTabs = Boolean.TRUE;
		blnShowPanelTabResumen = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;	
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.TRUE;
			blnShowPanelTabTercerosHaberes = Boolean.FALSE;
			blnShowPanelTabTercerosIncentivos = Boolean.FALSE;
			blnShowPanelTabTercerosCas = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 	
		
		if (intMesBusqueda!=-1) {
			getListaDsctoTercerosPorPeriodo(intAnioBusqueda, intMesBusqueda, Constante.PARAM_T_MODALIDADPLANILLA_HABERES, beanSocioEstructura);
			getListaDsctoTercerosPorPeriodo(intAnioBusqueda, intMesBusqueda, Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS, beanSocioEstructura);
			getListaDsctoTercerosPorPeriodo(intAnioBusqueda, intMesBusqueda, Constante.PARAM_T_MODALIDADPLANILLA_CAS, beanSocioEstructura);
		}
	}
	//LISTA DESCUENTOS TERCEROS POR PERIODO Y CPTO DESCUENTO
	public void getListaDsctoTercerosPorPeriodo(Integer intAnioBusqueda, Integer intMesBusqueda, Integer modPlanilla, DataBeanEstadoCuentaSocioEstructura beanSocioEstructura) throws BusinessException{
		String strPeriodoBusqueda = "";
		List<DataBeanEstadoCuentaDsctoTerceros> listaNvoColumnas = new ArrayList<DataBeanEstadoCuentaDsctoTerceros>();
		List<DataBeanEstadoCuentaDsctoTerceros> listaNvoFilas = null;
		List<DataBeanEstadoCuentaDsctoTerceros> listaMontoTotalDsctoTerceros = null;
		
		try {
			strPeriodoBusqueda = intMesBusqueda<10?"0"+intMesBusqueda:intMesBusqueda.toString();
			listaNvoFilas = estadoCuentaFacade.getListaFilasPorPeriodoModalidadYDni(Integer.parseInt(intAnioBusqueda+strPeriodoBusqueda), modPlanilla, beanSocioEstructura.getStrDocIdent());
			listaNvoColumnas = estadoCuentaFacade.getListaColumnasPorPeriodoModalidadYDni(Integer.parseInt(intAnioBusqueda+strPeriodoBusqueda), modPlanilla, beanSocioEstructura.getStrDocIdent());

			if ((listaNvoColumnas!=null && !listaNvoColumnas.isEmpty()) && (listaNvoFilas!=null && !listaNvoFilas.isEmpty())) {
				for (DataBeanEstadoCuentaDsctoTerceros column : listaNvoColumnas) {
					for (DataBeanEstadoCuentaDsctoTerceros row : listaNvoFilas) {
						if(row.getStrMontoDscto()==null){
							row.setStrMontoDscto(new String[1]);
						}else{
							String values[] = new String[row.getStrMontoDscto().length+1];
							for(int i=0; i<row.getStrMontoDscto().length; i++){
								values[i] = row.getStrMontoDscto()[i];
							}
							row.setStrMontoDscto(values);
						}
						listaMontoTotalDsctoTerceros = estadoCuentaFacade.getMontoTotalPorNomCptoYPeriodo(column.getStrDsteCpto(), column.getStrNomCpto(),row.getIntAnio(), row.getIntMes(), modPlanilla, beanSocioEstructura.getStrDocIdent());
						Integer intMontoDscto = 0;
						if (listaMontoTotalDsctoTerceros!=null && !listaMontoTotalDsctoTerceros.isEmpty()) {
							for (DataBeanEstadoCuentaDsctoTerceros lstMontoDscto : listaMontoTotalDsctoTerceros) {
								intMontoDscto = lstMontoDscto.getIntMonto()==null?0:lstMontoDscto.getIntMonto();
							}
						}
						Integer intPteEntera = intMontoDscto/100;
						Integer intPteDecimal = intMontoDscto%100;
						//Se le da formato de decimales al monto
						String strParteDecimal = "";
						if (intPteDecimal.toString().length()==1) {
							strParteDecimal = intPteDecimal+"0";
						}else{
							strParteDecimal = intPteDecimal.toString();
						}
						String strMontoDsctoTros = intPteEntera+"."+strParteDecimal;
						row.getStrMontoDscto()[row.getStrMontoDscto().length-1]=strMontoDsctoTros;
					}
				}
			}	
			for (DataBeanEstadoCuentaDsctoTerceros fila : listaNvoFilas) {
				fila.setStrPeriodo(formatearPeriodo(fila.getIntAnio(), fila.getIntMes()));
				BigDecimal bdSumTotal = BigDecimal.ZERO;
				for(int i=0; i<fila.getStrMontoDscto().length; i++){
					bdSumTotal = bdSumTotal.add(new BigDecimal(fila.getStrMontoDscto()[i]));
				}
				fila.setBdSumMontoDscto(bdSumTotal);
			}			
			
			if (modPlanilla.equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)) {
				setLstColumnaHaberes(listaNvoColumnas);
				setLstFilaHaberes(listaNvoFilas);
				if (lstFilaHaberes!=null && !lstFilaHaberes.isEmpty()) {
					blnShowPanelTabTercerosHaberes = Boolean.TRUE;
				}			
			}
			if (modPlanilla.equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)) {
				setLstColumnaIncentivos(listaNvoColumnas);
				setLstFilaIncentivos(listaNvoFilas);
				if (lstFilaIncentivos!=null && !lstFilaIncentivos.isEmpty()) {
					blnShowPanelTabTercerosIncentivos = Boolean.TRUE;
				}
			}
			if (modPlanilla.equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)) {
				setLstColumnaCas(listaNvoColumnas);
				setLstFilaCas(listaNvoFilas);
				if (lstFilaCas!=null && !lstFilaCas.isEmpty()) {
					blnShowPanelTabTercerosCas = Boolean.TRUE;
				}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void obtenerDatosTabGestiones() throws BusinessException{
		intShowPanel = 0;
		blnShowPanelTabs = Boolean.TRUE;
		blnShowPanelTabResumen = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;	
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.TRUE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 	
		
		lstDataBeanEstadoCuentaGestiones.clear();
		String strFechaGestion = null;

		try {
			if (intMesBusqueda!=-1) {
				strFechaGestion = "01/"+(intMesBusqueda<10?"0"+intMesBusqueda:intMesBusqueda)+"/"+intAnioBusqueda;
				lstDataBeanEstadoCuentaGestiones = estadoCuentaFacade.getListaGestionCobranza(SESION_IDEMPRESA, intCtasSocBusqueda, strFechaGestion);
			}
		} catch(Exception e){
			throw new BusinessException(e);
		}
	}

	public void obtenerDatosTabPrevisionSocial() throws BusinessException {
		intShowPanel = 0;
		blnShowPanelTabs = Boolean.TRUE;
		blnShowPanelTabResumen = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;	
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.TRUE; 	
		
		lstPagoCuotasFondoSepelio.clear();
		lstPagoCuotasFondoRetiro.clear();
		try {
			listarBeneficiosOtorgados();
			lstPagoCuotasBeneficios = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
			listarPagoCuotasBeneficio(Constante.PARAM_T_TIPOCUENTA_FONDO_SEPELIO);
			lstPagoCuotasFondoSepelio = lstPagoCuotasBeneficios;
			//AÑOS APORTADOS
			intPgoCtaBeneficioFdoSepelio=0;
			Integer cont=0;
			Integer periodoTemp = 0;
			for (DataBeanEstadoCuentaPrevisionSocial aniosAportado : lstPagoCuotasFondoSepelio) {
				if (cont==0) {
					if (aniosAportado.getBdMontoPendiente().compareTo(BigDecimal.ZERO)==0) {
						intPgoCtaBeneficioFdoSepelio++;
					}	
					periodoTemp = aniosAportado.getIntAnioInicio();
					cont++;
				}else {
					if (aniosAportado.getIntAnioInicio().compareTo(periodoTemp)!=0) {
						if (aniosAportado.getBdMontoPendiente().compareTo(BigDecimal.ZERO)==0) {
							intPgoCtaBeneficioFdoSepelio++;
						}
					}else {
						if (intPgoCtaBeneficioFdoSepelio!=0) {
							if (aniosAportado.getBdMontoPendiente().compareTo(BigDecimal.ZERO)!=0) {
								intPgoCtaBeneficioFdoSepelio=0;
							}
						}							
					}
					periodoTemp = aniosAportado.getIntAnioInicio();
				}
			}
			lstPagoCuotasBeneficios = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
			listarPagoCuotasBeneficio(Constante.PARAM_T_TIPOCUENTA_FONDO_RETIRO);
			lstPagoCuotasFondoRetiro = lstPagoCuotasBeneficios;
			BigDecimal bdAcumulado = null;
			for (DataBeanEstadoCuentaPrevisionSocial acumulado : lstPagoCuotasFondoRetiro) {
				if (bdAcumulado==null) {
					bdAcumulado=BigDecimal.ZERO;
					acumulado.setBdMontoAcumulado(bdAcumulado.add(acumulado.getBdMontoCancelado()));
					bdAcumulado = acumulado.getBdMontoAcumulado();
				}else {
					acumulado.setBdMontoAcumulado(bdAcumulado.add(acumulado.getBdMontoCancelado()));
					bdAcumulado = acumulado.getBdMontoAcumulado();
				}
				
			}
		} catch (Exception e) {
			throw new BusinessException(e);
		}
		
	}
	public void listarBeneficiosOtorgados() throws BusinessException {
		lstBeneficiosOtorgados.clear();
		try {
			lstBeneficiosOtorgados = estadoCuentaFacade.getBenefOtorgados(SESION_IDEMPRESA, beanSocioEstructura.getIntIdPersona(), intCtasSocBusqueda);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public void listarPagoCuotasBeneficio(Integer intTipoConcepto) throws BusinessException {
		lstPagoCuotasPeriodos.clear();
		lstPagoCuotasBeneficios.clear();
		DataBeanEstadoCuentaPrevisionSocial beanPrevision = new DataBeanEstadoCuentaPrevisionSocial();
		DataBeanEstadoCuentaPrevisionSocial beanPrevMontoPago = new DataBeanEstadoCuentaPrevisionSocial();
		BigDecimal bdSumaMontoPago = BigDecimal.ZERO;
	
		try {
			//Fonde de Sepelio
			lstPagoCuotasPeriodos = estadoCuentaFacade.getPeriodoCtaCto(SESION_IDEMPRESA, intCtasSocBusqueda, intTipoConcepto);
			if (lstPagoCuotasPeriodos!=null && !lstPagoCuotasPeriodos.isEmpty()) {
				for (DataBeanEstadoCuentaPrevisionSocial periodo : lstPagoCuotasPeriodos) {
					Integer intPeriodoIni = periodo.getIntPeriodoInicio();
					Integer intPeriodoFin = periodo.getIntPeriodoFin();
					Integer intAnioPeriodoIni = periodo.getIntAnioInicio();
					Integer intMesPeriodoIni = periodo.getIntMesInicio();
					Integer intAnioPeriodoFin = periodo.getIntAnioFin();
					
					Integer intPeriodoFinTmp = intPeriodoFin==null?Calendar.getInstance().get(Calendar.YEAR):intAnioPeriodoFin;
										
					for (Integer i = intAnioPeriodoIni; i <= intPeriodoFinTmp; i++) {
						Integer intNroCtas=0;
						Integer intFecIni=0;
						Integer intFecFin=0;
						log.info("------------------------------- inicio fila -------------------------------");
						if (intPeriodoFin==null) {
							intFecIni= i.compareTo(intAnioPeriodoIni)==0?intPeriodoIni:Integer.valueOf(i+"01");
							intFecFin= Integer.valueOf(i+"12");	
							//CUOTAS-NUMERO
							intNroCtas = i.compareTo(intAnioPeriodoIni)==0?13-intMesPeriodoIni:12;
							beanPrevision.setIntNumeroCuotas(intNroCtas);
							log.info("--- CUOTAS-NUMERO ---"+intNroCtas);
						}										
						if (intPeriodoFin!=null) {
							intFecIni= i.compareTo(intAnioPeriodoIni)==0?intPeriodoIni:Integer.valueOf(i+"01");
							intFecFin= i.compareTo(intAnioPeriodoFin)==0?intPeriodoFin:Integer.valueOf(i+"12");
							//CUOTAS-NUMERO
							intNroCtas = intFecFin-intFecIni+1;
							beanPrevision.setIntNumeroCuotas(intNroCtas);
							log.info("--- CUOTAS-NUMERO ---"+intNroCtas);
						}
						List<DataBeanEstadoCuentaPrevisionSocial> lista;
						lista = estadoCuentaFacade.getConceptoPago(SESION_IDEMPRESA, intCtasSocBusqueda, periodo.getIntItemCuentaConcepto(), periodo.getIntItemCuentaConceptoDetalle(), intFecIni, intFecFin);
						if (lista!=null && !lista.isEmpty()) {
							beanPrevision.setLstConceptoPago(new ArrayList<DataBeanEstadoCuentaPrevisionSocial>());
							for (DataBeanEstadoCuentaPrevisionSocial lst : lista) {
								beanPrevision.getLstConceptoPago().add(lst);
							}
						}
						beanPrevMontoPago = estadoCuentaFacade.getSumMontoPago(SESION_IDEMPRESA, intCtasSocBusqueda, periodo.getIntItemCuentaConcepto(), periodo.getIntItemCuentaConceptoDetalle(), intFecIni, intFecFin);
						bdSumaMontoPago = beanPrevMontoPago.getBdSumaMontoPago()!=null?beanPrevMontoPago.getBdSumaMontoPago():BigDecimal.ZERO;

						beanPrevision.setIntAnioInicio(i);
						beanPrevision.setBdCuotaMensual(periodo.getBdCuotaMensual());
						log.info("--- CUOTAS-MONTO ---"+beanPrevision.getBdSumaMontoPago());
						if (intTipoConcepto.equals(Constante.PARAM_T_TIPOCUENTA_FONDO_SEPELIO)) {
							beanPrevision.setBdMontoProvisionado(beanPrevision.getBdCuotaMensual().multiply(new BigDecimal(intNroCtas)));
							log.info("--- PROVISIONADO ---"+beanPrevision.getBdCuotaMensual().multiply(new BigDecimal(intNroCtas)));
							beanPrevision.setBdMontoPendiente(beanPrevision.getBdCuotaMensual().multiply(new BigDecimal(intNroCtas)).subtract(bdSumaMontoPago));
							log.info("--- PENDIENTE ---"+(beanPrevision.getBdCuotaMensual().multiply(new BigDecimal(intNroCtas)).subtract(bdSumaMontoPago)));
						}
						beanPrevision.setBdMontoCancelado(bdSumaMontoPago);
						log.info("--- CANCELADOS ---"+bdSumaMontoPago);

						lstPagoCuotasBeneficios.add(beanPrevision);
						beanPrevision = new DataBeanEstadoCuentaPrevisionSocial();
					}
				}
			}			
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public void listarDetallePagoCuotasFdoSepelio(ActionEvent event) throws BusinessException{
		log.info("------------------------ debuging.obtenerPagoCuotasBenefFdoSepelioDetalle ------------------------");
		DataBeanEstadoCuentaPrevisionSocial fdoSepelio = new DataBeanEstadoCuentaPrevisionSocial();
		lstDetallePagoCuotasFondoSepelio.clear();
		List<DataBeanEstadoCuentaPrevisionSocial> lstMovimientoFdoSepelio = null;
		try {
			//FILTRANDO MOVIMIENTOS DE FDO.SEPELIO
			fdoSepelio = (DataBeanEstadoCuentaPrevisionSocial)event.getComponent().getAttributes().get("itemGrilla");
			if (fdoSepelio.getLstConceptoPago()!=null && !fdoSepelio.getLstConceptoPago().isEmpty()) {
				for (DataBeanEstadoCuentaPrevisionSocial detalleMov : fdoSepelio.getLstConceptoPago()) {
					lstMovimientoFdoSepelio = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
					lstMovimientoFdoSepelio = estadoCuentaFacade.getMovimientoFdoSepelio(detalleMov.getIntPersEmpresaPk(),
																						detalleMov.getIntCuentaPk(),
																						detalleMov.getIntItemCtaCpto(),
																						detalleMov.getIntItemCtaCptoDet(),
																						detalleMov.getIntItemConceptoPago());
					if (lstMovimientoFdoSepelio!=null && !lstMovimientoFdoSepelio.isEmpty()) {
						for (DataBeanEstadoCuentaPrevisionSocial mov : lstMovimientoFdoSepelio) {
							mov.setIntPeriodoFechaCptoPago(detalleMov.getIntPeriodoInicio());
							lstDetallePagoCuotasFondoSepelio.add(mov);
						}
					}
				}
			}			
		} catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	public void listarDetallePagoCuotasFdoRetiro() throws BusinessException{
		List<DataBeanEstadoCuentaPrevisionSocial> listaConceptoPago = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		List<DataBeanEstadoCuentaPrevisionSocial> lstMovimientoFdoRetiroTemp = null;
		List<DataBeanEstadoCuentaPrevisionSocial> lstMovimientoFdoRetiro = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();
		List<DataBeanEstadoCuentaPrevisionSocial> lstMovimientoFdoRetiroInteres = new ArrayList<DataBeanEstadoCuentaPrevisionSocial>();

		DataBeanEstadoCuentaPrevisionSocial movFdoRetiroAportes = new DataBeanEstadoCuentaPrevisionSocial();
		DataBeanEstadoCuentaPrevisionSocial movFdoRetiroInteres = new DataBeanEstadoCuentaPrevisionSocial();

		lstDetallePagoCuotasFondoRetiro.clear();
		
		try {
			if (lstPagoCuotasFondoRetiro!=null && !lstPagoCuotasFondoRetiro.isEmpty()) {
				for (DataBeanEstadoCuentaPrevisionSocial fdoRetiro : lstPagoCuotasFondoRetiro) {
					if (fdoRetiro.getLstConceptoPago()!=null && !fdoRetiro.getLstConceptoPago().isEmpty()) {
						for (DataBeanEstadoCuentaPrevisionSocial cptoPago : fdoRetiro.getLstConceptoPago()) {
							listaConceptoPago.add(cptoPago);
						}
					}
				}
			}
			if (listaConceptoPago!=null && !listaConceptoPago.isEmpty()) {
				for (DataBeanEstadoCuentaPrevisionSocial detalleMov : listaConceptoPago) {
					lstMovimientoFdoRetiroTemp = estadoCuentaFacade.getMovimientoFdoSepelio(detalleMov.getIntPersEmpresaPk(),
																						detalleMov.getIntCuentaPk(),
																						detalleMov.getIntItemCtaCpto(),
																						detalleMov.getIntItemCtaCptoDet(),
																						detalleMov.getIntItemConceptoPago());
					if (lstMovimientoFdoRetiroTemp!=null && !lstMovimientoFdoRetiroTemp.isEmpty()) {
						for (DataBeanEstadoCuentaPrevisionSocial movTemp : lstMovimientoFdoRetiroTemp) {
							lstMovimientoFdoRetiro.add(movTemp);
						}
					}
				}
			}
			
			lstMovimientoFdoRetiroInteres = estadoCuentaFacade.getMovFdoRetiroInteres(SESION_IDEMPRESA,intCtasSocBusqueda);
			for (DataBeanEstadoCuentaPrevisionSocial movInteres : lstMovimientoFdoRetiroInteres) {
				lstMovimientoFdoRetiro.add(movInteres);
			}
			
			if (lstPagoCuotasFondoRetiro!=null && !lstPagoCuotasFondoRetiro.isEmpty()) {
				for (DataBeanEstadoCuentaPrevisionSocial pgoCtasFdoRetiro : lstPagoCuotasFondoRetiro) {
					if (lstMovimientoFdoRetiro!=null && !lstMovimientoFdoRetiro.isEmpty()) {
						for (DataBeanEstadoCuentaPrevisionSocial movFdoRetiro : lstMovimientoFdoRetiro) {
							if ((movFdoRetiro.getIntPeriodoInicio().toString().substring(0, 4)).compareTo(pgoCtasFdoRetiro.getIntAnioInicio().toString())==0) {
								if (movFdoRetiro.getIntTipoConceptoGral().equals(7)) { //FDO.RETIRO(7)
									//CONCEPTO
									movFdoRetiroAportes.setStrDescTipoConceptoGral(movFdoRetiro.getIntTipoConceptoGral()==7?"APORTE":"INTERES");
									//PERIODO									
									movFdoRetiroAportes.setIntPeriodoInicio(pgoCtasFdoRetiro.getIntAnioInicio());
									Integer intMesPeriodo = Integer.valueOf(movFdoRetiro.getIntPeriodoInicio().toString().substring(4, 6));
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_ENERO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoEne()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoEne(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoEne(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoEne(movFdoRetiroAportes.getBdMontoMovimientoEne().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoEne(movFdoRetiroAportes.getBdMontoMovimientoEne().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_FEBRERO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoFeb()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoFeb(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoFeb(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoFeb(movFdoRetiroAportes.getBdMontoMovimientoFeb().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoFeb(movFdoRetiroAportes.getBdMontoMovimientoFeb().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_MARZO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoMar()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoMar(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoMar(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoMar(movFdoRetiroAportes.getBdMontoMovimientoMar().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoMar(movFdoRetiroAportes.getBdMontoMovimientoMar().add(movFdoRetiro.getBdMontoMovimiento()));
										}							
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_ABRIL)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoAbr()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoAbr(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoAbr(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoAbr(movFdoRetiroAportes.getBdMontoMovimientoAbr().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoAbr(movFdoRetiroAportes.getBdMontoMovimientoAbr().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_MAYO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoMay()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoMay(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoMay(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoMay(movFdoRetiroAportes.getBdMontoMovimientoMay().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoMay(movFdoRetiroAportes.getBdMontoMovimientoMay().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_JUNIO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoJun()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoJun(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoJun(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoJun(movFdoRetiroAportes.getBdMontoMovimientoJun().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoJun(movFdoRetiroAportes.getBdMontoMovimientoJun().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_JULIO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoJul()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoJul(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoJul(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoJul(movFdoRetiroAportes.getBdMontoMovimientoJul().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoJul(movFdoRetiroAportes.getBdMontoMovimientoJul().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_AGOSTO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoAgo()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoAgo(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoAgo(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoAgo(movFdoRetiroAportes.getBdMontoMovimientoAgo().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoAgo(movFdoRetiroAportes.getBdMontoMovimientoAgo().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_SETIEMBRE)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoSet()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoSet(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoSet(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoSet(movFdoRetiroAportes.getBdMontoMovimientoSet().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoSet(movFdoRetiroAportes.getBdMontoMovimientoSet().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_OCTUBRE)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoOct()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoOct(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoOct(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoOct(movFdoRetiroAportes.getBdMontoMovimientoOct().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoOct(movFdoRetiroAportes.getBdMontoMovimientoOct().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_NOVIEMBRE)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoNov()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoNov(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoNov(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoNov(movFdoRetiroAportes.getBdMontoMovimientoNov().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoNov(movFdoRetiroAportes.getBdMontoMovimientoNov().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_DICIEMBRE)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoDic()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoDic(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoDic(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroAportes.setBdMontoMovimientoDic(movFdoRetiroAportes.getBdMontoMovimientoDic().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroAportes.setBdMontoMovimientoDic(movFdoRetiroAportes.getBdMontoMovimientoDic().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
								}
									
								if (movFdoRetiro.getIntTipoConceptoGral().equals(14)) { //Fdo.Retiro Interes
									//CONCEPTO
									movFdoRetiroInteres.setStrDescTipoConceptoGral(movFdoRetiro.getIntTipoConceptoGral()==7?"APORTE":"INTERES");
									//PERIODO									
									movFdoRetiroInteres.setIntPeriodoInicio(pgoCtasFdoRetiro.getIntAnioInicio());
									Integer intMesPeriodo = Integer.valueOf(movFdoRetiro.getIntPeriodoInicio().toString().substring(4, 6));
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_ENERO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoEne()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoEne(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoEne(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoEne(movFdoRetiroInteres.getBdMontoMovimientoEne().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoEne(movFdoRetiroInteres.getBdMontoMovimientoEne().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_FEBRERO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoFeb()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoFeb(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoFeb(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoFeb(movFdoRetiroInteres.getBdMontoMovimientoFeb().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoFeb(movFdoRetiroInteres.getBdMontoMovimientoFeb().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_MARZO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoMar()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoMar(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoMar(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoMar(movFdoRetiroInteres.getBdMontoMovimientoMar().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoMar(movFdoRetiroInteres.getBdMontoMovimientoMar().add(movFdoRetiro.getBdMontoMovimiento()));
										}							
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_ABRIL)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoAbr()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoAbr(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoAbr(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoAbr(movFdoRetiroInteres.getBdMontoMovimientoAbr().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoAbr(movFdoRetiroInteres.getBdMontoMovimientoAbr().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_MAYO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoMay()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoMay(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoMay(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoMay(movFdoRetiroInteres.getBdMontoMovimientoMay().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoMay(movFdoRetiroInteres.getBdMontoMovimientoMay().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_JUNIO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoJun()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoJun(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoJun(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoJun(movFdoRetiroInteres.getBdMontoMovimientoJun().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoJun(movFdoRetiroInteres.getBdMontoMovimientoJun().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_JULIO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoJul()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoJul(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoJul(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoJul(movFdoRetiroInteres.getBdMontoMovimientoJul().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoJul(movFdoRetiroInteres.getBdMontoMovimientoJul().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_AGOSTO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoAgo()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoAgo(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoAgo(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoAgo(movFdoRetiroInteres.getBdMontoMovimientoAgo().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoAgo(movFdoRetiroInteres.getBdMontoMovimientoAgo().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_SETIEMBRE)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoSet()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoSet(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoSet(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoSet(movFdoRetiroInteres.getBdMontoMovimientoSet().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoSet(movFdoRetiroInteres.getBdMontoMovimientoSet().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_OCTUBRE)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoOct()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoOct(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoOct(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoOct(movFdoRetiroInteres.getBdMontoMovimientoOct().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoOct(movFdoRetiroInteres.getBdMontoMovimientoOct().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_NOVIEMBRE)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoNov()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoNov(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoNov(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoNov(movFdoRetiroInteres.getBdMontoMovimientoNov().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoNov(movFdoRetiroInteres.getBdMontoMovimientoNov().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_DICIEMBRE)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoDic()==null) {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoDic(movFdoRetiro.getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoDic(movFdoRetiro.getBdMontoMovimiento());
										}else {
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("C")) movFdoRetiroInteres.setBdMontoMovimientoDic(movFdoRetiroInteres.getBdMontoMovimientoDic().subtract(movFdoRetiro.getBdMontoMovimiento()));
											if (movFdoRetiro.getStrDescTipoCargoAbono().equals("A")) movFdoRetiroInteres.setBdMontoMovimientoDic(movFdoRetiroInteres.getBdMontoMovimientoDic().add(movFdoRetiro.getBdMontoMovimiento()));
										}
									}
								}
							}
						}
						//APORTES POR PERIODO
						if (movFdoRetiroAportes.getIntPeriodoInicio()==null) {
							movFdoRetiroAportes.setStrDescTipoConceptoGral("APORTE");
							movFdoRetiroAportes.setIntPeriodoInicio(Integer.valueOf(pgoCtasFdoRetiro.getIntAnioInicio()));
						}
						movFdoRetiroAportes.setBdMontoTotal(movFdoRetiroAportes.getBdMontoMovimientoEne()!=null?movFdoRetiroAportes.getBdMontoMovimientoEne():BigDecimal.ZERO
															.add(movFdoRetiroAportes.getBdMontoMovimientoFeb()!=null?movFdoRetiroAportes.getBdMontoMovimientoFeb():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoMar()!=null?movFdoRetiroAportes.getBdMontoMovimientoMar():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoAbr()!=null?movFdoRetiroAportes.getBdMontoMovimientoAbr():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoMay()!=null?movFdoRetiroAportes.getBdMontoMovimientoMay():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoJun()!=null?movFdoRetiroAportes.getBdMontoMovimientoJun():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoJul()!=null?movFdoRetiroAportes.getBdMontoMovimientoJul():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoAgo()!=null?movFdoRetiroAportes.getBdMontoMovimientoAgo():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoSet()!=null?movFdoRetiroAportes.getBdMontoMovimientoAgo():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoOct()!=null?movFdoRetiroAportes.getBdMontoMovimientoOct():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoNov()!=null?movFdoRetiroAportes.getBdMontoMovimientoNov():BigDecimal.ZERO)
															.add(movFdoRetiroAportes.getBdMontoMovimientoDic()!=null?movFdoRetiroAportes.getBdMontoMovimientoDic():BigDecimal.ZERO));
						lstDetallePagoCuotasFondoRetiro.add(movFdoRetiroAportes);
						movFdoRetiroAportes = new DataBeanEstadoCuentaPrevisionSocial();
						//INTERES POR PERIODO
						if (movFdoRetiroInteres.getStrFechaMovimiento()==null) {
							movFdoRetiroInteres.setStrDescTipoConceptoGral("INTERES");
							movFdoRetiroInteres.setIntPeriodoInicio(Integer.valueOf(pgoCtasFdoRetiro.getIntAnioInicio().toString().substring(0, 4)));
						}
						movFdoRetiroInteres.setBdMontoTotal(movFdoRetiroInteres.getBdMontoMovimientoEne()!=null?movFdoRetiroInteres.getBdMontoMovimientoEne():BigDecimal.ZERO
															.add(movFdoRetiroInteres.getBdMontoMovimientoFeb()!=null?movFdoRetiroInteres.getBdMontoMovimientoFeb():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoMar()!=null?movFdoRetiroInteres.getBdMontoMovimientoMar():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoAbr()!=null?movFdoRetiroInteres.getBdMontoMovimientoAbr():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoMay()!=null?movFdoRetiroInteres.getBdMontoMovimientoMay():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoJun()!=null?movFdoRetiroInteres.getBdMontoMovimientoJun():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoJul()!=null?movFdoRetiroInteres.getBdMontoMovimientoJul():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoAgo()!=null?movFdoRetiroInteres.getBdMontoMovimientoAgo():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoSet()!=null?movFdoRetiroInteres.getBdMontoMovimientoAgo():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoOct()!=null?movFdoRetiroInteres.getBdMontoMovimientoOct():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoNov()!=null?movFdoRetiroInteres.getBdMontoMovimientoNov():BigDecimal.ZERO)
															.add(movFdoRetiroInteres.getBdMontoMovimientoDic()!=null?movFdoRetiroInteres.getBdMontoMovimientoDic():BigDecimal.ZERO));
						lstDetallePagoCuotasFondoRetiro.add(movFdoRetiroInteres);
						movFdoRetiroInteres = new DataBeanEstadoCuentaPrevisionSocial();
					}
				}
			}
			log.info("--- SIZE listaPrevSocialFdoRetiroDetalle: ---"+lstDetallePagoCuotasFondoRetiro.size());
			//ACUMULADO
			BigDecimal bdMontoAcumuladoAporte = BigDecimal.ZERO;
			BigDecimal bdMontoAcumuladoInteres = BigDecimal.ZERO;
			if (lstDetallePagoCuotasFondoRetiro.size()!=0) {
				Integer intSize = lstDetallePagoCuotasFondoRetiro.size()-1;
				for (int i = 0; i >=intSize ; i++) {
					if (lstDetallePagoCuotasFondoRetiro.get(i).getStrDescTipoConceptoGral().compareTo("INTERES")==0) {
						bdMontoAcumuladoInteres = bdMontoAcumuladoInteres.add(lstDetallePagoCuotasFondoRetiro.get(i).getBdMontoTotal());
						lstDetallePagoCuotasFondoRetiro.get(i).setBdMontoAcumulado(bdMontoAcumuladoInteres);
					}
					if (lstDetallePagoCuotasFondoRetiro.get(i).getStrDescTipoConceptoGral().compareTo("APORTE")==0) {
						bdMontoAcumuladoAporte = bdMontoAcumuladoAporte.add(lstDetallePagoCuotasFondoRetiro.get(i).getBdMontoTotal());
						lstDetallePagoCuotasFondoRetiro.get(i).setBdMontoAcumulado(bdMontoAcumuladoAporte);
					}				
				}
			}
			
		} catch(Exception e){
			throw new BusinessException(e);
		}
	}

	public void obtenerDatosTabPrestamos(){
		blnShowPanelTabs = Boolean.TRUE;
		blnShowPanelTabResumen = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;	
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.TRUE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 	
		
		lstDataBeanEstadoCuentaPrestamos.clear();
		lstDataBeanEstadoCuentaPrestamosAprobados.clear();
		lstDataBeanEstadoCuentaPrestamosRechazados.clear();
		lstDataBeanEstadoCuentaPrestamosGarantizados.clear();
		
	}
	public void listarPrestamosYPersonasGarantizadas() throws BusinessException{
		lstDataBeanEstadoCuentaPrestamos.clear();
		bdSumatoriaSaldoPrestamo = BigDecimal.ZERO;
		try {
			if (intEstadoCreditoBusqueda.equals(Constante.PARAM_T_ESTADO_CREDITO_VIGENTE)) {
				lstDataBeanEstadoCuentaPrestamosAprobados = estadoCuentaFacade.getPrestamoAprobado(SESION_IDEMPRESA, intCtasSocBusqueda, intTipoCreditoBusqueda, intEstadoCreditoBusqueda);
				lstDataBeanEstadoCuentaPrestamos = lstDataBeanEstadoCuentaPrestamosAprobados;
			}
			if (intEstadoCreditoBusqueda.equals(Constante.PARAM_T_ESTADO_CREDITO_CANCELADO)) {
				lstDataBeanEstadoCuentaPrestamosAprobados = estadoCuentaFacade.getPrestamoAprobado(SESION_IDEMPRESA, intCtasSocBusqueda, intTipoCreditoBusqueda, intEstadoCreditoBusqueda);
				lstDataBeanEstadoCuentaPrestamos = lstDataBeanEstadoCuentaPrestamosAprobados;
			}
			if (intEstadoCreditoBusqueda.equals(Constante.PARAM_T_ESTADO_CREDITO_RECHAZADO)) {
				lstDataBeanEstadoCuentaPrestamosRechazados = estadoCuentaFacade.getPrestamoRechazado(SESION_IDEMPRESA, intCtasSocBusqueda, intTipoCreditoBusqueda);
				lstDataBeanEstadoCuentaPrestamos = lstDataBeanEstadoCuentaPrestamosRechazados;
			}
			if (intEstadoCreditoBusqueda.equals(Constante.PARAM_T_ESTADO_CREDITO_TODOS)) {
				lstDataBeanEstadoCuentaPrestamosAprobados = estadoCuentaFacade.getPrestamoAprobado(SESION_IDEMPRESA, intCtasSocBusqueda, intTipoCreditoBusqueda, intEstadoCreditoBusqueda);
				lstDataBeanEstadoCuentaPrestamosRechazados = estadoCuentaFacade.getPrestamoRechazado(SESION_IDEMPRESA, intCtasSocBusqueda, intTipoCreditoBusqueda);
				if (lstDataBeanEstadoCuentaPrestamosAprobados!=null && !lstDataBeanEstadoCuentaPrestamosAprobados.isEmpty()) {
					for (DataBeanEstadoCuentaPrestamos aprobado : lstDataBeanEstadoCuentaPrestamosAprobados) {
						lstDataBeanEstadoCuentaPrestamos.add(aprobado);
					}
				}
				if (lstDataBeanEstadoCuentaPrestamosRechazados!=null && !lstDataBeanEstadoCuentaPrestamosRechazados.isEmpty()) {
					for (DataBeanEstadoCuentaPrestamos rechazado : lstDataBeanEstadoCuentaPrestamosRechazados) {
						lstDataBeanEstadoCuentaPrestamos.add(rechazado);
					}
				}
			}
			for (DataBeanEstadoCuentaPrestamos prestamo : lstDataBeanEstadoCuentaPrestamos) {
				bdSumatoriaSaldoPrestamo = bdSumatoriaSaldoPrestamo.add(prestamo.getBdSaldoCredito()!=null?prestamo.getBdSaldoCredito():BigDecimal.ZERO);
			}
			lstDataBeanEstadoCuentaPrestamosGarantizados = estadoCuentaFacade.getPrestamoGarantizado(SESION_IDEMPRESA, beanSocioEstructura.getIntIdPersona(), intCtasSocBusqueda);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}
	
	public void imprimirReporte(){
		String strNombreReporte = "";
		HashMap<String,Object> parametro = new HashMap<String,Object>();
		String strCondSocio = null;
		String strTipoCondSocio = null;
		String strMes = null;
		String strNroCtaLiq = "";
		String strFchLiqCta = "";
		//MOMENTANEO
		String strSucursal = "";
		String strUnidadEjec = "";
		try {

			
			//Año Actual
			parametro.put("strAnioActual", ""+Calendar.getInstance().get(Calendar.YEAR));
			//Fecha de Emisión
			parametro.put("strFecEmision", strFechaYHoraActual);
			//Socio / Cliente
			parametro.put("strSocioCliente", beanSocioEstructura.getIntIdPersona()+" - "+beanSocioCuenta.getStrNombreCompletoSocio());
			//Condicion:
			for (Tabla condSocio : listaDescripcionCondicionSocio) {
				if(condSocio.getIntIdDetalle().equals(beanSocioCuenta.getIntCondicionCuenta())){
					strCondSocio = condSocio.getStrDescripcion();
					break;
				}
			}			
			for (Tabla tipoCondSocio : listaDescripcionTipoCondicionSocio) {
				if(tipoCondSocio.getIntIdDetalle().equals(beanSocioCuenta.getIntSubCondicionCuenta())){
					strTipoCondSocio = tipoCondSocio.getStrDescripcion();
					break;
				}
			}			
			parametro.put("strCondicion", strCondSocio+" - "+strTipoCondSocio);
			//Apertura Cuenta:
			parametro.put("strFecAperturaCta", beanSocioCuenta.getStrFechaApertura());
			//Cta.Aporte
			parametro.put("strCtaAporte", convertirMontos(beanMontosBeneficios.getBdCtaAporte()));
			//Saldo Aporte
			parametro.put("strSaldoAporte", convertirMontos(beanMontosBeneficios.getBdSaldoAporte()));
			//Fondo de Retiro
			parametro.put("strSaldoFdoRetiro", convertirMontos(beanMontosBeneficios.getBdMontoFdoRetiro()));
			//Pendiente - Fondo Sepelio
			parametro.put("strPteFdoSepelio", convertirMontos(beanMontosBeneficios.getBdMontoFdoSepelio()));
			//Pendiente - Mantenimiento
			parametro.put("strPteMantenimiento", convertirMontos(beanMontosBeneficios.getBdMontoMantenimiento()));
			//Nro. de Cuenta
			parametro.put("strNumeroCuenta", beanSocioCuenta.getStrNumeroCuenta());
			//Ultimo Envio Aportes
			parametro.put("strUltEnvAportes", convertirMontos(bdUltimoEnvioAportes));
			//Ultimo Envio Fdo Sepelio
			parametro.put("strUltEnvFdoSepelio", convertirMontos(bdUltimoEnvioFdoSepelio));
			//Ultimo Envio Fdo Retiro	
			parametro.put("strUltEnvFdoRetiro", convertirMontos(bdUltimoEnvioFdoRetiro));
			//Ultimo Envio Mantenimiento
			parametro.put("strUltEnvMantenimiento", convertirMontos(bdUltimoEnvioMant));
			//Ultimo envio Periodo
			for (Tabla mes : listaDescripcionMes) {
				if(mes.getIntIdDetalle().equals(intMesUltEnvio)){
					strMes = mes.getStrDescripcion();
					break;
				}
			}
			parametro.put("strUltEnvPeriodo", (strMes!=null?strMes:"")+" - "+(intAnioUltEnvio!=null?intAnioUltEnvio:""));
			log.info("Fecha de envio: "+(strMes!=null?strMes:"")+" - "+(intAnioUltEnvio!=null?intAnioUltEnvio:""));
			//Sumatoria Ultimo Envio
			parametro.put("strUltEnvSumatoria", convertirMontos(bdSumatoriaUltimoEnvio));
			//Sumatoria Saldos
			parametro.put("strSaldosSumatoria", convertirMontos(bdSumatoriaSaldo));
			
			//Sucursal
			Integer contSuc = 1;
			for (DataBeanEstadoCuentaSocioEstructura sucur : lstDataBeanEstadoCuentaSocioEstructura) {
				if (contSuc.equals(1)) {
					strSucursal = sucur.getStrSucursal();
					contSuc++;
				}else {
					strSucursal = strSucursal+"; "+sucur.getStrSucursal();
				}
			}
			parametro.put("strSucursal", strSucursal);
			//Unidad Ejecutora
			Integer contUndEjec = 1;
			String strCondLaboral = "";
			String strModalidad = "";
			String strTipoSocio = "";
			for (DataBeanEstadoCuentaSocioEstructura unidEjec : lstDataBeanEstadoCuentaSocioEstructura) {
				for (Tabla condLab : listaDescripcionCondLaboral) {
					if(condLab.getIntIdDetalle().compareTo(unidEjec.getIntCondicionLaboral())==0){
						strCondLaboral = condLab.getStrDescripcion();
						break;
					}
				}	
				//Modalidad Planilla
				for (Tabla modalidad : listaDescripcionModalidadPlanilla) {
					if(modalidad.getIntIdDetalle().compareTo(unidEjec.getIntModalidad())==0){
						strModalidad = modalidad.getStrDescripcion();
						break;
					}
				}
				//Tipo De Socio
				for (Tabla tipoSocio : listaDescripcionTipoSocio) {
					if(tipoSocio.getIntIdDetalle().compareTo(unidEjec.getIntTipoSocio())==0){
						strTipoSocio = tipoSocio.getStrDescripcion();
						break;
					}
				}				
				if (contUndEjec.equals(1)) {
					strUnidadEjec = unidEjec.getStrNombreEstructura()+"-"+strCondLaboral+"-"+strModalidad+"-"+strTipoSocio;
					contUndEjec++;
				}else {
					strUnidadEjec = strUnidadEjec+"; "+unidEjec.getStrNombreEstructura()+"-"+strCondLaboral+"-"+strModalidad+"-"+strTipoSocio;
				}
			}
			parametro.put("strUnidadEjec", strUnidadEjec);
			//Cuentas Liquidadas
			for (Tabla ctaLiq : lstCuentasLiquidadas) {
				strNroCtaLiq = ctaLiq.getStrDescripcion();
				strFchLiqCta = ctaLiq.getStrAbreviatura()!=null?ctaLiq.getStrAbreviatura():"";
			}
			parametro.put("strNroCtaLiq", strNroCtaLiq);
			parametro.put("strFchLiqCta", strFchLiqCta);
			
			if (intShowPanel.equals(1)) {
				strNombreReporte = "report_EstCta_Resumen";
				Reporteador reporte = new Reporteador();
				reporte.executeReport(strNombreReporte, parametro);
			}
			if (intShowPanel.equals(2)) {
				strNombreReporte = "report_EstCta_Detalle";

				parametro.put("strSumAcumMesesFecha", movimientoAnteriorResumen.getStrFechaMovimiento());
				parametro.put("strSumAcumMesesDescripcion", "-------");
				parametro.put("strSumAcumMesesMontoMovAportes", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoAporte()));
				parametro.put("strSumAcumMesesMontoMovPrestamos", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoPrestamo()));
				parametro.put("strSumAcumMesesMontoMovCreditos", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoCredito()));
				parametro.put("strSumAcumMesesMontoMovInteres", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoInteres()));
				parametro.put("strSumAcumMesesMontoMovMntCuenta", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoMantenimiento()));
				parametro.put("strSumAcumMesesMontoMovActividad", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoActividad()));
				parametro.put("strSumAcumMesesStrMontoMovMulta", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoMulta()));
				parametro.put("strSumAcumMesesMontoMovFdoSepelio", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoFondoSepelio()));
				parametro.put("strSumAcumMesesMontoMovFdoRetiro", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoFondoRetiro()));
				parametro.put("strSumAcumMesesMontoMovCtaXPagar", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoCtaPorPagar()));
				parametro.put("strSumAcumMesesMontoMovCtaXCobrar", convertirMontos(movimientoAnteriorResumen.getBdSumaMontoCtaPorCobrar()));

				parametro.put("strSumatoriaMontoMovAportes", convertirMontos(bdSumaMontoAporte));
				parametro.put("strSumatoriaMontoMovPrestamos", convertirMontos(bdSumaMontoPrestamo));
				parametro.put("strSumatoriaMontoMovCreditos", convertirMontos(bdSumaMontoCredito));
				parametro.put("strSumatoriaMontoMovActividad", convertirMontos(bdSumaMontoActividad));
				parametro.put("strSumatoriaMontoMovMulta", convertirMontos(bdSumaMontoMulta));
				parametro.put("strSumatoriaMontoMovFdoRetiro", convertirMontos(bdSumaMontoFondoRetiro));
				
				ReporterEstCtaTabDetalle reporte = new ReporterEstCtaTabDetalle();
				reporte.executeReport(strNombreReporte, parametro);
			}
		} catch (Exception e) {
			log.error("Error en imprimirReporteConJavaBean ---> "+e);
		}		 
	}
	
	//Concatenar Mes Y Año
	public static Integer concatenarMesAnio(Integer mes, Integer anio){
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

	//FORMATO PERIODO: NOMBRE_DEL_MES - AÑO
	public String formatearPeriodo(Integer intAnio, Integer intMes) throws BusinessException{
		String strFmtPeriodo="";
		String strMesPeriodo="";
		try {
			if (listaDescripcionMes!=null && !listaDescripcionMes.isEmpty()) {
				for (Tabla mes : listaDescripcionMes) {
					if(mes.getIntIdDetalle().equals(intMes)){
						strMesPeriodo = mes.getStrDescripcion();
					}
				}
				strFmtPeriodo=strMesPeriodo+" - "+intAnio;
			}
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return strFmtPeriodo;	
	}

	//FECHA Y HORA ACTUAL DE EJECUCION
	public void actualizarFechaYHora(){
		Formatter fmt = new Formatter();			
		Calendar cal = Calendar.getInstance();
		strFechaYHoraActual = "";
		strFechaYHoraActual = fmt.format("%1$02d/%2$02d/%3$04d - %4$02d:%5$02d",cal.get(Calendar.DATE),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)).toString();
	}

	public String getInicioPage() {
		cargarUsuario();		
		if(usuarioSesion!=null){
			limpiarFormulario();
		}else{
			log.error("--Usuario obtenido es NULL o no posee permiso.");
		}		
		return "";
	}
	
	//Getters y Setters
	public Object getSessionBean(String beanName) {
		HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
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
	public static Logger getLog() {
		return log;
	}
	public static void setLog(Logger log) {
		EstadoCuentaController.log = log;
	}
	
	public void paintImage(OutputStream stream, Object object) throws IOException {
		MyFile myFile = (MyFile)object;
		log.info("Firma myFile:"+myFile.getName());
		stream.write(myFile.getData());
    }
	
	public void paintImageFoto(OutputStream stream, Object object) throws IOException {
		MyFile myFile = (MyFile)object;
		log.info("Foto myFile:"+myFile.getName());
        stream.write(myFile.getData());
	}
	
	public Integer getIntTipoBusqueda() {
		return intTipoBusqueda;
	}
	public void setIntTipoBusqueda(Integer intTipoBusqueda) {
		this.intTipoBusqueda = intTipoBusqueda;
	}
	public String getStrIdPersonaBusqueda() {
		return strIdPersonaBusqueda;
	}
	public void setStrIdPersonaBusqueda(String strIdPersonaBusqueda) {
		this.strIdPersonaBusqueda = strIdPersonaBusqueda;
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
	public List<SelectItem> getListYears() {
		return listYears;
	}
	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}
	public Integer getIntTipoCuentaBusqueda() {
		return intTipoCuentaBusqueda;
	}
	public void setIntTipoCuentaBusqueda(Integer intTipoCuentaBusqueda) {
		this.intTipoCuentaBusqueda = intTipoCuentaBusqueda;
	}
	public Integer getIntCtasSocBusqueda() {
		return intCtasSocBusqueda;
	}
	public void setIntCtasSocBusqueda(Integer intCtasSocBusqueda) {
		this.intCtasSocBusqueda = intCtasSocBusqueda;
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
	public List<DataBeanEstadoCuentaResumenPrestamos> getLstDataBeanEstadoCuentaResumenPrestamos() {
		return lstDataBeanEstadoCuentaResumenPrestamos;
	}
	public void setLstDataBeanEstadoCuentaResumenPrestamos(
			List<DataBeanEstadoCuentaResumenPrestamos> lstDataBeanEstadoCuentaResumenPrestamos) {
		this.lstDataBeanEstadoCuentaResumenPrestamos = lstDataBeanEstadoCuentaResumenPrestamos;
	}
	public Boolean getBlnShowPanelTabResumen() {
		return blnShowPanelTabResumen;
	}
	public void setBlnShowPanelTabResumen(Boolean blnShowPanelTabResumen) {
		this.blnShowPanelTabResumen = blnShowPanelTabResumen;
	}
	public EstadoCuentaFacadeLocal getEstadoCuentaFacade() {
		return estadoCuentaFacade;
	}
	public void setEstadoCuentaFacade(EstadoCuentaFacadeLocal estadoCuentaFacade) {
		this.estadoCuentaFacade = estadoCuentaFacade;
	}
	public String getStrFechaYHoraActual() {
		return strFechaYHoraActual;
	}
	public void setStrFechaYHoraActual(String strFechaYHoraActual) {
		this.strFechaYHoraActual = strFechaYHoraActual;
	}
	public List<DataBeanEstadoCuentaMontosBeneficios> getLstDataBeanEstadoCuentaMontosBeneficios() {
		return lstDataBeanEstadoCuentaMontosBeneficios;
	}
	public void setLstDataBeanEstadoCuentaMontosBeneficios(
			List<DataBeanEstadoCuentaMontosBeneficios> lstDataBeanEstadoCuentaMontosBeneficios) {
		this.lstDataBeanEstadoCuentaMontosBeneficios = lstDataBeanEstadoCuentaMontosBeneficios;
	}
	public DataBeanEstadoCuentaMontosBeneficios getBeanMontosBeneficios() {
		return beanMontosBeneficios;
	}
	public void setBeanMontosBeneficios(
			DataBeanEstadoCuentaMontosBeneficios beanMontosBeneficios) {
		this.beanMontosBeneficios = beanMontosBeneficios;
	}
	public List<DataBeanEstadoCuentaSocioCuenta> getLstDataBeanEstadoCuentaSocioCuenta() {
		return lstDataBeanEstadoCuentaSocioCuenta;
	}
	public void setLstDataBeanEstadoCuentaSocioCuenta(
			List<DataBeanEstadoCuentaSocioCuenta> lstDataBeanEstadoCuentaSocioCuenta) {
		this.lstDataBeanEstadoCuentaSocioCuenta = lstDataBeanEstadoCuentaSocioCuenta;
	}
	public DataBeanEstadoCuentaSocioCuenta getBeanSocioCuenta() {
		return beanSocioCuenta;
	}
	public void setBeanSocioCuenta(DataBeanEstadoCuentaSocioCuenta beanSocioCuenta) {
		this.beanSocioCuenta = beanSocioCuenta;
	}
	public List<DataBeanEstadoCuentaSocioEstructura> getLstDataBeanEstadoCuentaSocioEstructura() {
		return lstDataBeanEstadoCuentaSocioEstructura;
	}
	public void setLstDataBeanEstadoCuentaSocioEstructura(
			List<DataBeanEstadoCuentaSocioEstructura> lstDataBeanEstadoCuentaSocioEstructura) {
		this.lstDataBeanEstadoCuentaSocioEstructura = lstDataBeanEstadoCuentaSocioEstructura;
	}
	public DataBeanEstadoCuentaSocioEstructura getBeanSocioEstructura() {
		return beanSocioEstructura;
	}
	public void setBeanSocioEstructura(
			DataBeanEstadoCuentaSocioEstructura beanSocioEstructura) {
		this.beanSocioEstructura = beanSocioEstructura;
	}
	public Boolean getBlnShowPanelCabecera() {
		return blnShowPanelCabecera;
	}
	public void setBlnShowPanelCabecera(Boolean blnShowPanelCabecera) {
		this.blnShowPanelCabecera = blnShowPanelCabecera;
	}
	public BigDecimal getBdSumatoriaSaldo() {
		return bdSumatoriaSaldo;
	}
	public void setBdSumatoriaSaldo(BigDecimal bdSumatoriaSaldo) {
		this.bdSumatoriaSaldo = bdSumatoriaSaldo;
	}
	public BigDecimal getBdUltimoEnvioAportes() {
		return bdUltimoEnvioAportes;
	}
	public void setBdUltimoEnvioAportes(BigDecimal bdUltimoEnvioAportes) {
		this.bdUltimoEnvioAportes = bdUltimoEnvioAportes;
	}
	public BigDecimal getBdUltimoEnvioFdoSepelio() {
		return bdUltimoEnvioFdoSepelio;
	}
	public void setBdUltimoEnvioFdoSepelio(BigDecimal bdUltimoEnvioFdoSepelio) {
		this.bdUltimoEnvioFdoSepelio = bdUltimoEnvioFdoSepelio;
	}
	public BigDecimal getBdUltimoEnvioFdoRetiro() {
		return bdUltimoEnvioFdoRetiro;
	}
	public void setBdUltimoEnvioFdoRetiro(BigDecimal bdUltimoEnvioFdoRetiro) {
		this.bdUltimoEnvioFdoRetiro = bdUltimoEnvioFdoRetiro;
	}
	public BigDecimal getBdUltimoEnvioMant() {
		return bdUltimoEnvioMant;
	}
	public void setBdUltimoEnvioMant(BigDecimal bdUltimoEnvioMant) {
		this.bdUltimoEnvioMant = bdUltimoEnvioMant;
	}
	public Integer getIntMesUltEnvio() {
		return intMesUltEnvio;
	}
	public void setIntMesUltEnvio(Integer intMesUltEnvio) {
		this.intMesUltEnvio = intMesUltEnvio;
	}
	public Integer getIntAnioUltEnvio() {
		return intAnioUltEnvio;
	}
	public void setIntAnioUltEnvio(Integer intAnioUltEnvio) {
		this.intAnioUltEnvio = intAnioUltEnvio;
	}
	public BigDecimal getBdSumatoriaUltimoEnvio() {
		return bdSumatoriaUltimoEnvio;
	}
	public void setBdSumatoriaUltimoEnvio(BigDecimal bdSumatoriaUltimoEnvio) {
		this.bdSumatoriaUltimoEnvio = bdSumatoriaUltimoEnvio;
	}
	public List<Tabla> getLstCuentasLiquidadas() {
		return lstCuentasLiquidadas;
	}
	public void setLstCuentasLiquidadas(List<Tabla> lstCuentasLiquidadas) {
		this.lstCuentasLiquidadas = lstCuentasLiquidadas;
	}
	public Boolean getBlnShowPanelTabs() {
		return blnShowPanelTabs;
	}
	public void setBlnShowPanelTabs(Boolean blnShowPanelTabs) {
		this.blnShowPanelTabs = blnShowPanelTabs;
	}
	public List<DataBeanEstadoCuentaGestiones> getLstDataBeanEstadoCuentaGestiones() {
		return lstDataBeanEstadoCuentaGestiones;
	}
	public void setLstDataBeanEstadoCuentaGestiones(
			List<DataBeanEstadoCuentaGestiones> lstDataBeanEstadoCuentaGestiones) {
		this.lstDataBeanEstadoCuentaGestiones = lstDataBeanEstadoCuentaGestiones;
	}
	public Boolean getBlnShowPanelTabGestiones() {
		return blnShowPanelTabGestiones;
	}
	public void setBlnShowPanelTabGestiones(Boolean blnShowPanelTabGestiones) {
		this.blnShowPanelTabGestiones = blnShowPanelTabGestiones;
	}
	public List<DataBeanEstadoCuentaDsctoTerceros> getLstColumnaHaberes() {
		return lstColumnaHaberes;
	}
	public void setLstColumnaHaberes(
			List<DataBeanEstadoCuentaDsctoTerceros> lstColumnaHaberes) {
		this.lstColumnaHaberes = lstColumnaHaberes;
	}
	public List<DataBeanEstadoCuentaDsctoTerceros> getLstFilaHaberes() {
		return lstFilaHaberes;
	}
	public void setLstFilaHaberes(
			List<DataBeanEstadoCuentaDsctoTerceros> lstFilaHaberes) {
		this.lstFilaHaberes = lstFilaHaberes;
	}
	public List<DataBeanEstadoCuentaDsctoTerceros> getLstColumnaIncentivos() {
		return lstColumnaIncentivos;
	}
	public void setLstColumnaIncentivos(
			List<DataBeanEstadoCuentaDsctoTerceros> lstColumnaIncentivos) {
		this.lstColumnaIncentivos = lstColumnaIncentivos;
	}
	public List<DataBeanEstadoCuentaDsctoTerceros> getLstFilaIncentivos() {
		return lstFilaIncentivos;
	}
	public void setLstFilaIncentivos(
			List<DataBeanEstadoCuentaDsctoTerceros> lstFilaIncentivos) {
		this.lstFilaIncentivos = lstFilaIncentivos;
	}
	public List<DataBeanEstadoCuentaDsctoTerceros> getLstColumnaCas() {
		return lstColumnaCas;
	}
	public void setLstColumnaCas(
			List<DataBeanEstadoCuentaDsctoTerceros> lstColumnaCas) {
		this.lstColumnaCas = lstColumnaCas;
	}
	public List<DataBeanEstadoCuentaDsctoTerceros> getLstFilaCas() {
		return lstFilaCas;
	}
	public void setLstFilaCas(List<DataBeanEstadoCuentaDsctoTerceros> lstFilaCas) {
		this.lstFilaCas = lstFilaCas;
	}
	public Boolean getBlnShowPanelTabTerceros() {
		return blnShowPanelTabTerceros;
	}
	public void setBlnShowPanelTabTerceros(Boolean blnShowPanelTabTerceros) {
		this.blnShowPanelTabTerceros = blnShowPanelTabTerceros;
	}
	public Boolean getBlnShowPanelTabTercerosHaberes() {
		return blnShowPanelTabTercerosHaberes;
	}
	public void setBlnShowPanelTabTercerosHaberes(
			Boolean blnShowPanelTabTercerosHaberes) {
		this.blnShowPanelTabTercerosHaberes = blnShowPanelTabTercerosHaberes;
	}
	public Boolean getBlnShowPanelTabTercerosIncentivos() {
		return blnShowPanelTabTercerosIncentivos;
	}
	public void setBlnShowPanelTabTercerosIncentivos(
			Boolean blnShowPanelTabTercerosIncentivos) {
		this.blnShowPanelTabTercerosIncentivos = blnShowPanelTabTercerosIncentivos;
	}
	public Boolean getBlnShowPanelTabTercerosCas() {
		return blnShowPanelTabTercerosCas;
	}
	public void setBlnShowPanelTabTercerosCas(Boolean blnShowPanelTabTercerosCas) {
		this.blnShowPanelTabTercerosCas = blnShowPanelTabTercerosCas;
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
	public List<DataBeanEstadoCuentaPlanillas> getLstDataBeanEstadoCuentaPlanillas() {
		return lstDataBeanEstadoCuentaPlanillas;
	}
	public void setLstDataBeanEstadoCuentaPlanillas(
			List<DataBeanEstadoCuentaPlanillas> lstDataBeanEstadoCuentaPlanillas) {
		this.lstDataBeanEstadoCuentaPlanillas = lstDataBeanEstadoCuentaPlanillas;
	}
	public Boolean getBlnShowPanelTabPlanillaResumen() {
		return blnShowPanelTabPlanillaResumen;
	}
	public void setBlnShowPanelTabPlanillaResumen(
			Boolean blnShowPanelTabPlanillaResumen) {
		this.blnShowPanelTabPlanillaResumen = blnShowPanelTabPlanillaResumen;
	}
	public Boolean getBlnShowPanelTabPlanilla() {
		return blnShowPanelTabPlanilla;
	}
	public void setBlnShowPanelTabPlanilla(Boolean blnShowPanelTabPlanilla) {
		this.blnShowPanelTabPlanilla = blnShowPanelTabPlanilla;
	}
	public List<DataBeanEstadoCuentaPlanillas> getLstDataBeanEstadoCuentaDiferenciaPlanilla() {
		return lstDataBeanEstadoCuentaDiferenciaPlanilla;
	}
	public void setLstDataBeanEstadoCuentaDiferenciaPlanilla(
			List<DataBeanEstadoCuentaPlanillas> lstDataBeanEstadoCuentaDiferenciaPlanilla) {
		this.lstDataBeanEstadoCuentaDiferenciaPlanilla = lstDataBeanEstadoCuentaDiferenciaPlanilla;
	}
	public Integer getIntTipoCreditoBusqueda() {
		return intTipoCreditoBusqueda;
	}
	public void setIntTipoCreditoBusqueda(Integer intTipoCreditoBusqueda) {
		this.intTipoCreditoBusqueda = intTipoCreditoBusqueda;
	}
	public Integer getIntEstadoCreditoBusqueda() {
		return intEstadoCreditoBusqueda;
	}
	public void setIntEstadoCreditoBusqueda(Integer intEstadoCreditoBusqueda) {
		this.intEstadoCreditoBusqueda = intEstadoCreditoBusqueda;
	}
	public List<DataBeanEstadoCuentaPrestamos> getLstDataBeanEstadoCuentaPrestamos() {
		return lstDataBeanEstadoCuentaPrestamos;
	}
	public void setLstDataBeanEstadoCuentaPrestamos(
			List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamos) {
		this.lstDataBeanEstadoCuentaPrestamos = lstDataBeanEstadoCuentaPrestamos;
	}
	public List<DataBeanEstadoCuentaPrestamos> getLstDataBeanEstadoCuentaPrestamosAprobados() {
		return lstDataBeanEstadoCuentaPrestamosAprobados;
	}
	public void setLstDataBeanEstadoCuentaPrestamosAprobados(
			List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamosAprobados) {
		this.lstDataBeanEstadoCuentaPrestamosAprobados = lstDataBeanEstadoCuentaPrestamosAprobados;
	}
	public List<DataBeanEstadoCuentaPrestamos> getLstDataBeanEstadoCuentaPrestamosRechazados() {
		return lstDataBeanEstadoCuentaPrestamosRechazados;
	}
	public void setLstDataBeanEstadoCuentaPrestamosRechazados(
			List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamosRechazados) {
		this.lstDataBeanEstadoCuentaPrestamosRechazados = lstDataBeanEstadoCuentaPrestamosRechazados;
	}
	public List<DataBeanEstadoCuentaPrestamos> getLstDataBeanEstadoCuentaPrestamosGarantizados() {
		return lstDataBeanEstadoCuentaPrestamosGarantizados;
	}
	public void setLstDataBeanEstadoCuentaPrestamosGarantizados(
			List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamosGarantizados) {
		this.lstDataBeanEstadoCuentaPrestamosGarantizados = lstDataBeanEstadoCuentaPrestamosGarantizados;
	}
	public Boolean getBlnShowPanelTabPrestamos() {
		return blnShowPanelTabPrestamos;
	}
	public void setBlnShowPanelTabPrestamos(Boolean blnShowPanelTabPrestamos) {
		this.blnShowPanelTabPrestamos = blnShowPanelTabPrestamos;
	}
	public BigDecimal getBdSumatoriaSaldoPrestamo() {
		return bdSumatoriaSaldoPrestamo;
	}
	public void setBdSumatoriaSaldoPrestamo(BigDecimal bdSumatoriaSaldoPrestamo) {
		this.bdSumatoriaSaldoPrestamo = bdSumatoriaSaldoPrestamo;
	}
	public List<DataBeanEstadoCuentaPlanillas> getListaColumnasDiferenciaPlanilla() {
		return listaColumnasDiferenciaPlanilla;
	}
	public void setListaColumnasDiferenciaPlanilla(
			List<DataBeanEstadoCuentaPlanillas> listaColumnasDiferenciaPlanilla) {
		this.listaColumnasDiferenciaPlanilla = listaColumnasDiferenciaPlanilla;
	}
	public List<DataBeanEstadoCuentaPlanillas> getListaFilasDiferenciaPlanilla() {
		return listaFilasDiferenciaPlanilla;
	}
	public void setListaFilasDiferenciaPlanilla(
			List<DataBeanEstadoCuentaPlanillas> listaFilasDiferenciaPlanilla) {
		this.listaFilasDiferenciaPlanilla = listaFilasDiferenciaPlanilla;
	}
	public Boolean getBlnShowPanelTabPlanillaDiferencia() {
		return blnShowPanelTabPlanillaDiferencia;
	}
	public void setBlnShowPanelTabPlanillaDiferencia(
			Boolean blnShowPanelTabPlanillaDiferencia) {
		this.blnShowPanelTabPlanillaDiferencia = blnShowPanelTabPlanillaDiferencia;
	}
	public List<DataBeanEstadoCuentaPrevisionSocial> getLstBeneficiosOtorgados() {
		return lstBeneficiosOtorgados;
	}
	public void setLstBeneficiosOtorgados(
			List<DataBeanEstadoCuentaPrevisionSocial> lstBeneficiosOtorgados) {
		this.lstBeneficiosOtorgados = lstBeneficiosOtorgados;
	}
	public Boolean getBlnShowPanelTabPrevisionSocial() {
		return blnShowPanelTabPrevisionSocial;
	}
	public void setBlnShowPanelTabPrevisionSocial(
			Boolean blnShowPanelTabPrevisionSocial) {
		this.blnShowPanelTabPrevisionSocial = blnShowPanelTabPrevisionSocial;
	}
	public List<DataBeanEstadoCuentaPrevisionSocial> getLstPagoCuotasPeriodos() {
		return lstPagoCuotasPeriodos;
	}
	public void setLstPagoCuotasPeriodos(
			List<DataBeanEstadoCuentaPrevisionSocial> lstPagoCuotasPeriodos) {
		this.lstPagoCuotasPeriodos = lstPagoCuotasPeriodos;
	}
	public List<DataBeanEstadoCuentaPrevisionSocial> getLstPagoCuotasBeneficios() {
		return lstPagoCuotasBeneficios;
	}
	public void setLstPagoCuotasBeneficios(
			List<DataBeanEstadoCuentaPrevisionSocial> lstPagoCuotasBeneficios) {
		this.lstPagoCuotasBeneficios = lstPagoCuotasBeneficios;
	}
	public List<DataBeanEstadoCuentaPrevisionSocial> getLstPagoCuotasFondoSepelio() {
		return lstPagoCuotasFondoSepelio;
	}
	public void setLstPagoCuotasFondoSepelio(
			List<DataBeanEstadoCuentaPrevisionSocial> lstPagoCuotasFondoSepelio) {
		this.lstPagoCuotasFondoSepelio = lstPagoCuotasFondoSepelio;
	}
	public List<DataBeanEstadoCuentaPrevisionSocial> getLstPagoCuotasFondoRetiro() {
		return lstPagoCuotasFondoRetiro;
	}
	public void setLstPagoCuotasFondoRetiro(
			List<DataBeanEstadoCuentaPrevisionSocial> lstPagoCuotasFondoRetiro) {
		this.lstPagoCuotasFondoRetiro = lstPagoCuotasFondoRetiro;
	}
	public Integer getIntPgoCtaBeneficioFdoSepelio() {
		return intPgoCtaBeneficioFdoSepelio;
	}
	public void setIntPgoCtaBeneficioFdoSepelio(Integer intPgoCtaBeneficioFdoSepelio) {
		this.intPgoCtaBeneficioFdoSepelio = intPgoCtaBeneficioFdoSepelio;
	}
	public List<DataBeanEstadoCuentaPrevisionSocial> getLstDetallePagoCuotasFondoSepelio() {
		return lstDetallePagoCuotasFondoSepelio;
	}
	public void setLstDetallePagoCuotasFondoSepelio(
			List<DataBeanEstadoCuentaPrevisionSocial> lstDetallePagoCuotasFondoSepelio) {
		this.lstDetallePagoCuotasFondoSepelio = lstDetallePagoCuotasFondoSepelio;
	}
	public List<DataBeanEstadoCuentaPrevisionSocial> getLstDetallePagoCuotasFondoRetiro() {
		return lstDetallePagoCuotasFondoRetiro;
	}
	public void setLstDetallePagoCuotasFondoRetiro(
			List<DataBeanEstadoCuentaPrevisionSocial> lstDetallePagoCuotasFondoRetiro) {
		this.lstDetallePagoCuotasFondoRetiro = lstDetallePagoCuotasFondoRetiro;
	}
	public List<Tabla> getListaDescripcionModalidadPlanilla() {
		return listaDescripcionModalidadPlanilla;
	}
	public void setListaDescripcionModalidadPlanilla(
			List<Tabla> listaDescripcionModalidadPlanilla) {
		this.listaDescripcionModalidadPlanilla = listaDescripcionModalidadPlanilla;
	}
	public Boolean getBlnShowPanelTabDetalle() {
		return blnShowPanelTabDetalle;
	}
	public void setBlnShowPanelTabDetalle(Boolean blnShowPanelTabDetalle) {
		this.blnShowPanelTabDetalle = blnShowPanelTabDetalle;
	}
	public BigDecimal getBdZero() {
		return bdZero;
	}
	public void setBdZero(BigDecimal bdZero) {
		this.bdZero = bdZero;
	}
	public Integer getIntCuentasVigentesYLiquidadas() {
		return intCuentasVigentesYLiquidadas;
	}
	public void setIntCuentasVigentesYLiquidadas(
			Integer intCuentasVigentesYLiquidadas) {
		this.intCuentasVigentesYLiquidadas = intCuentasVigentesYLiquidadas;
	}
	public List<DataBeanEstadoCuentaSocioCuenta> getListaSocioBusquedaPorApeNom() {
		return listaSocioBusquedaPorApeNom;
	}
	public void setListaSocioBusquedaPorApeNom(
			List<DataBeanEstadoCuentaSocioCuenta> listaSocioBusquedaPorApeNom) {
		this.listaSocioBusquedaPorApeNom = listaSocioBusquedaPorApeNom;
	}
	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}
	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}
	public Integer getIntShowPanel() {
		return intShowPanel;
	}
	public void setIntShowPanel(Integer intShowPanel) {
		this.intShowPanel = intShowPanel;
	}
	public List<Tabla> getListaDescripcionCondLaboral() {
		return listaDescripcionCondLaboral;
	}
	public void setListaDescripcionCondLaboral(
			List<Tabla> listaDescripcionCondLaboral) {
		this.listaDescripcionCondLaboral = listaDescripcionCondLaboral;
	}
	public List<Tabla> getListaDescripcionTipoSocio() {
		return listaDescripcionTipoSocio;
	}
	public void setListaDescripcionTipoSocio(List<Tabla> listaDescripcionTipoSocio) {
		this.listaDescripcionTipoSocio = listaDescripcionTipoSocio;
	}
	public List<Tabla> getListaDescripcionCondicionSocio() {
		return listaDescripcionCondicionSocio;
	}
	public void setListaDescripcionCondicionSocio(
			List<Tabla> listaDescripcionCondicionSocio) {
		this.listaDescripcionCondicionSocio = listaDescripcionCondicionSocio;
	}
	public List<Tabla> getListaDescripcionTipoCondicionSocio() {
		return listaDescripcionTipoCondicionSocio;
	}
	public void setListaDescripcionTipoCondicionSocio(
			List<Tabla> listaDescripcionTipoCondicionSocio) {
		this.listaDescripcionTipoCondicionSocio = listaDescripcionTipoCondicionSocio;
	}
	public Integer getBusqPorNombre() {
		return busqPorNombre;
	}
	public void setBusqPorNombre(Integer busqPorNombre) {
		this.busqPorNombre = busqPorNombre;
	}
	public DataBeanEstadoCuentaSocioCuenta getSocioNaruralSelected() {
		return socioNaruralSelected;
	}
	public void setSocioNaruralSelected(
			DataBeanEstadoCuentaSocioCuenta socioNaruralSelected) {
		this.socioNaruralSelected = socioNaruralSelected;
	}	
	public MyFile getFileFotoSocio() {
		return fileFotoSocio;
	}
	public void setFileFotoSocio(MyFile fileFotoSocio) {
		this.fileFotoSocio = fileFotoSocio;
	}
	public MyFile getFileFirmaSocio() {
		return fileFirmaSocio;
	}
	public void setFileFirmaSocio(MyFile fileFirmaSocio) {
		this.fileFirmaSocio = fileFirmaSocio;
	}
	public String getStrFotoSocio() {
		return strFotoSocio;
	}
	public void setStrFotoSocio(String strFotoSocio) {
		this.strFotoSocio = strFotoSocio;
	}
	public String getStrFirmaSocio() {
		return strFirmaSocio;
	}
	public void setStrFirmaSocio(String strFirmaSocio) {
		this.strFirmaSocio = strFirmaSocio;
	}
	public Integer getIntCodigoPersona() {
		return intCodigoPersona;
	}
	public void setIntCodigoPersona(Integer intCodigoPersona) {
		this.intCodigoPersona = intCodigoPersona;
	}
	public List<DataBeanEstadoCuentaDetalleMovimiento> getLstDetalleMovimiento() {
		return lstDetalleMovimiento;
	}
	public void setLstDetalleMovimiento(
			List<DataBeanEstadoCuentaDetalleMovimiento> lstDetalleMovimiento) {
		this.lstDetalleMovimiento = lstDetalleMovimiento;
	}
	public BigDecimal getBdSumaMontoAporte() {
		return bdSumaMontoAporte;
	}
	public void setBdSumaMontoAporte(BigDecimal bdSumaMontoAporte) {
		this.bdSumaMontoAporte = bdSumaMontoAporte;
	}
	public BigDecimal getBdSumaMontoMantenimiento() {
		return bdSumaMontoMantenimiento;
	}
	public void setBdSumaMontoMantenimiento(BigDecimal bdSumaMontoMantenimiento) {
		this.bdSumaMontoMantenimiento = bdSumaMontoMantenimiento;
	}
	public BigDecimal getBdSumaMontoFondoSepelio() {
		return bdSumaMontoFondoSepelio;
	}
	public void setBdSumaMontoFondoSepelio(BigDecimal bdSumaMontoFondoSepelio) {
		this.bdSumaMontoFondoSepelio = bdSumaMontoFondoSepelio;
	}
	public BigDecimal getBdSumaMontoFondoRetiro() {
		return bdSumaMontoFondoRetiro;
	}
	public void setBdSumaMontoFondoRetiro(BigDecimal bdSumaMontoFondoRetiro) {
		this.bdSumaMontoFondoRetiro = bdSumaMontoFondoRetiro;
	}
	public BigDecimal getBdSumaMontoPrestamo() {
		return bdSumaMontoPrestamo;
	}
	public void setBdSumaMontoPrestamo(BigDecimal bdSumaMontoPrestamo) {
		this.bdSumaMontoPrestamo = bdSumaMontoPrestamo;
	}
	public BigDecimal getBdSumaMontoCredito() {
		return bdSumaMontoCredito;
	}
	public void setBdSumaMontoCredito(BigDecimal bdSumaMontoCredito) {
		this.bdSumaMontoCredito = bdSumaMontoCredito;
	}
	public BigDecimal getBdSumaMontoActividad() {
		return bdSumaMontoActividad;
	}
	public void setBdSumaMontoActividad(BigDecimal bdSumaMontoActividad) {
		this.bdSumaMontoActividad = bdSumaMontoActividad;
	}
	public BigDecimal getBdSumaMontoMulta() {
		return bdSumaMontoMulta;
	}
	public void setBdSumaMontoMulta(BigDecimal bdSumaMontoMulta) {
		this.bdSumaMontoMulta = bdSumaMontoMulta;
	}
	public BigDecimal getBdSumaMontoInteres() {
		return bdSumaMontoInteres;
	}
	public void setBdSumaMontoInteres(BigDecimal bdSumaMontoInteres) {
		this.bdSumaMontoInteres = bdSumaMontoInteres;
	}
	public BigDecimal getBdSumaMontoCtaPorPagar() {
		return bdSumaMontoCtaPorPagar;
	}
	public void setBdSumaMontoCtaPorPagar(BigDecimal bdSumaMontoCtaPorPagar) {
		this.bdSumaMontoCtaPorPagar = bdSumaMontoCtaPorPagar;
	}
	public BigDecimal getBdSumaMontoCtaPorCobrar() {
		return bdSumaMontoCtaPorCobrar;
	}
	public void setBdSumaMontoCtaPorCobrar(BigDecimal bdSumaMontoCtaPorCobrar) {
		this.bdSumaMontoCtaPorCobrar = bdSumaMontoCtaPorCobrar;
	}
	public DataBeanEstadoCuentaDetalleMovimiento getMovimientoAnteriorResumen() {
		return movimientoAnteriorResumen;
	}
	public void setMovimientoAnteriorResumen(
			DataBeanEstadoCuentaDetalleMovimiento movimientoAnteriorResumen) {
		this.movimientoAnteriorResumen = movimientoAnteriorResumen;
	}
	public List<DataBeanEstadoCuentaDetalleMovimiento> getListaMovimientoAntesDeFechaIngresada() {
		return listaMovimientoAntesDeFechaIngresada;
	}
	public void setListaMovimientoAntesDeFechaIngresada(
			List<DataBeanEstadoCuentaDetalleMovimiento> listaMovimientoAntesDeFechaIngresada) {
		this.listaMovimientoAntesDeFechaIngresada = listaMovimientoAntesDeFechaIngresada;
	}
	public List<DataBeanEstadoCuentaDetalleMovimiento> getListaMovimientoDespuesDeFechaIngresada() {
		return listaMovimientoDespuesDeFechaIngresada;
	}
	public void setListaMovimientoDespuesDeFechaIngresada(
			List<DataBeanEstadoCuentaDetalleMovimiento> listaMovimientoDespuesDeFechaIngresada) {
		this.listaMovimientoDespuesDeFechaIngresada = listaMovimientoDespuesDeFechaIngresada;
	}	
}