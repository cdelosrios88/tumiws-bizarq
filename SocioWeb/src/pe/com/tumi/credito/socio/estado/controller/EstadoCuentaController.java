package pe.com.tumi.credito.socio.estado.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoId;
import pe.com.tumi.cobranza.planilla.domain.EnviadoEfectuadoComp;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;
import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegranteId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.core.domain.Socio;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioEstructura;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.facade.SocioFacadeLocal;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeLocal;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.DataBeanEstadoCuentaPrestamos;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.EstadoCuentaComp;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.MovimientoEstCtaComp;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.PrevisionSocialComp;
import pe.com.tumi.credito.socio.estadoCuenta.facade.EstadoCtaFacadeLocal;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraId;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacadeLocal;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.empresa.domain.SucursalId;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.empresa.domain.Juridica;
import pe.com.tumi.report.bean.DataBeanEstCtaResumen;
import pe.com.tumi.report.engine.Reporteador;
import pe.com.tumi.report.engine.ReporterEstCtaTabDetalle;
import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalleId;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeNuevoRemote;
 
/*****************************************************************************
 * NOMBRE DE LA CLASE: ESTADOCUENTASCONTROLLER 
 * FUNCIONALIDAD : CLASE QUE TIENE LOS PARAMETROS DE BUSQUEDA Y VALIDACIONES 
 * REF. : 
 * AUTOR : JUNIOR CHÁVEZ VALVERDE (PININOS)
 * VERSIÓN : V1 
 * FECHA CREACIÓN : 15/07/2013 
 *****************************************************************************/

public class EstadoCuentaController {
	protected 	static Logger 	log;
	NumberFormat formato;
	/****************************** ESQUEMAS ******************************/
	// ESQUEMA SOCIO

	private SocioComp socioComp;
	private Cuenta cuenta;
	private CuentaComp cuentaComp;
	private DataBeanEstCtaResumen dataBeanEstCtaResumen;
	
	// ESQUEMA MOVIMIENTO
	private Expediente expediente;
	private Expediente expedienteMov;
	private Efectuado efectMov;
	
	/****************************** FACADES ******************************/
	private SocioFacadeLocal socioFacade;
	private EstadoCtaFacadeLocal estadoCtaFacade;	
	private CreditoFacadeLocal creditoFacade;
	private PersonaFacadeRemote personaFacade;
	private ConceptoFacadeRemote conceptoFacade;
	private EmpresaFacadeRemote empresaFacade;
	private TablaFacadeRemote tablaFacade;
	private SolicitudPrestamoFacadeNuevoRemote solicitudPrestamoFacade;
	private PlanillaFacadeRemote planillaFacade;
	private EstructuraFacadeLocal estructuraFacade;

	/****************************** LISTAS ******************************/
	private List<SelectItem> listYears;
	private List<SocioComp> listaSocioBusquedaPorApeNom;
	private List<CuentaComp> listaCuentaComp;
	private List<CuentaComp> lstCtasSocBusqueda;
	private List<Juridica> listaRazonSocialSucursal;
	private List<ExpedienteCredito> listaExpedienteCredito;
	private List<Cronograma> listaCronograma;
	private List<EstadoCredito> listaEstadoCredito;
	private List<Envioconcepto> listaEnvioconcepto;
	private List<Envioconcepto> listaEnvCptoPorCtaCptoDetYPer;
	private List<Enviomonto> listaEnviomonto;
	private List<Efectuado> listaEfectuado;
	private List<EfectuadoConcepto> listaEfectuadoConcepto;
	private List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamos;
	private List<EstadoCuentaComp> listaDatosGrillaPrestamosDetalle;
	private List<Movimiento> listaMovCtaCtoXCtaCto; 
	private List<Movimiento> listaMovCtaCtoXExpCred;
	private List<EstadoCuentaComp> listaEstadoCuentaComp;
	private List<EstadoCuentaComp> listaSumatoriaEstadoCuentaComp;
	private List<Tabla> listaDescripcionModalidadPlanilla;
	private List<Tabla> listaDescripcionCondLaboral;
	private List<Tabla> listaDescripcionTipoSocio;
	private List<Tabla> listaDescripcionTipoMovimiento;
	private List<Tabla> listaDescripcionTipoCargoAbono;
	private List<Tabla> listaDescripcionMes;
	//private List<Tabla> listaDescripcionCondLaboralDetalle;
	private List<Tabla> listaUnidadEjecutora;

	//Tab Prestamos
	private List<GarantiaCreditoComp> listaGarantiaCredito;
	
	//Tab Terceros
	//Haberes
	private List<Descuento> listaMontoDsctoTrosHaberes;
	private List<Descuento> listaNvoColumnasHaberes;
	//Incentivos
	private List<Descuento> listaMontoDsctoTrosIncentivos;
	private List<Descuento> listaNvoColumnasIncentivos;
	//CAS
	private List<Descuento> listaMontoDsctoTrosCas;
	private List<Descuento> listaNvoColumnasCas;
	
	private List<EstadoCuentaComp> listaGrillaTabPlanilla;
	private List<EnviadoEfectuadoComp> listaMontoPorPeriodoYPrioridad;
	private List<PrioridadDescuento> listaGralPrioridadDescuento;
	private List<EnviadoEfectuadoComp> lstColumnasPrioridadDscto;
	private List<Descuento> listaMontoDsctoTerceros;
	private List<EnviadoEfectuadoComp> listaEnviadoEfectuadoComp;
	private List<ExpedienteComp> listaExpedienteComp;
	//
	private List<ExpedientePrevisionComp> listaExpedientePrevisionComp;
	private List<PrevisionSocialComp> listaPrevSocialFdoSepelio;
	private List<PrevisionSocialComp> listaPrevSocialFdoRetiro;
	//
	private List<EnviadoEfectuadoComp> listaEnvEfecPlanilla;
	private List<MovimientoEstCtaComp> listaPrevSocialFdoSepelioDetalle;
	private List<MovimientoEstCtaComp> listaPrevSocialFdoRetiroDetalle;
	//
	private PrevisionSocialComp previsionSocialSelected;
	private SocioComp socioNaruralSelected;
	//
	private List<GestionCobranza> listaGestionCobranza;
	private List<GestionCobranzaSoc> listaGestionCobranzaSocio;
	/****************************** VARIABLES PUBLICAS ******************************/
	//GENERALES
	private Formatter fmt = new Formatter();
	private SimpleDateFormat formatoDeFecha = new SimpleDateFormat("dd/MM/yyyy");

	private String strFechaYHoraActual;
	private Boolean blnShowPanelDatosDelSocioYCuentas;
	private Boolean blnShowPanelResumenEstadoCta;
	private Boolean blnShowPanelTabDetalle;
	private Boolean blnShowPanelTabPrestamosResumePrestamos;
	private Boolean blnShowPanelTabPrestamosGarantizados;
	//VARIABLES PARA CONSULTA DEL FORMULARIO

	private Integer intTipoBusqueda;
	private String strIdPersonaBusqueda;	
	private Integer intMesBusqueda;
	private Integer intAnioBusqueda;
	private Integer intTipoCuentaBusqueda;
	private Integer intCtasSocBusqueda;
	private Integer intTipoCreditoBusqueda;
	private Integer intEstadoEstCtaBusqueda;
	//OBTENER CUENTA VIGENTE
	private String strFechaRegistro;
	
	
	//Tab Resumen
	private BigDecimal bdCucoSaldoAporte;
	private BigDecimal bdCucoSaldoRetiro;	
	private BigDecimal bdMontoConceptoAporte;	
	private BigDecimal bdMontoSaldoSepelio;
	private BigDecimal bdMontoSaldoMantenimiento;

	
	
	private BigDecimal bdDiferenciaMes; //CALCULO DIFERENCIA DE MESES ENTRE PERIODO DEL REGISTRO Y PERIODO ACTUAL
	//OBTENER DATOS PRESTAMOS	
	private BigDecimal bdMontoUltimoEnvio; 
	private BigDecimal bdSumatoriaUltimoEnvio;

	private BigDecimal bdSumatoriaSaldo;
	private Integer periodoTemp = 0;	
	private Integer intAnioPeriodoTemp;
	private Integer intMesPeriodoTemp;
	// VARIABLES PARA LA GRILLA DE PRESTAMOS
	private String strFechaEstado;
	private String strDescripcion;
	private BigDecimal bdTasaInteres;
	private BigDecimal bdMontoTotal;

	private BigDecimal bdSaldoCredito;
	private BigDecimal bdDiferencia;
	
	private BigDecimal bdUltimoEnvioAportes;
	private BigDecimal bdUltimoEnvioFdoSepelio;
	private BigDecimal bdUltimoEnvioFdoRetiro;
	private BigDecimal bdUltimoEnvioMant;

	//Tab Prevision Social
	private Integer intPgoCtaBeneficioFdoSepelio;
	private Integer intPgoCtaBeneficioFdoRetiro;
	//VARIABLES DETALLE ESTADO DE CUENTA
	private BigDecimal bdMontoMovPrestamos;
	private BigDecimal bdMontoMovCreditos;
	private BigDecimal bdMontoMovActividad;
	private BigDecimal bdMontoMovInteres;
	private BigDecimal bdMontoMovMulta;
	private BigDecimal bdMontoMovAportes;
	private BigDecimal bdMontoMovMntCuenta;
	private BigDecimal bdMontoMovFdoSepelio;
	private BigDecimal bdMontoMovFdoRetiro;
	private BigDecimal bdMontoMovCtaXPagar;
	private BigDecimal bdMontoMovCtaXCobrar;
	//SALDOS ACTUALES
	private String strSumatoriaMontoMovPrestamos;
	private String strSumatoriaMontoMovCreditos;
	private String strSumatoriaMontoMovActividad;
	private String strSumatoriaMontoMovMulta;
	private String strSumatoriaMontoMovAportes;
	private String strSumatoriaMontoMovFdoRetiro;
	//SUMATORIA DE FILAS
	private String strSumatoriaFilas;
	
	//Show Panel's
	private Boolean blnShowPanelTabTerceros;
	private Boolean blnShowPanelTabTercerosHaberes;
	private Boolean blnShowPanelTabTercerosIncentivos;
	private Boolean blnShowPanelTabTercerosCas;
	private Boolean blnShowPanelTabGestiones;
	private Boolean blnShowPanelTabPrestamos;
	private Boolean blnShowPanelTabPlanilla;
	private Boolean blnShowPanelTabPlanillaResumen;
	private Boolean blnShowPanelTabPlanillaDiferencia;
	private Boolean blnShowPanelTabPrevisionSocial;
	
	//Tab Prestamos
	private BigDecimal bdSumatoriaSaldoPrestamo;
	
	//ATRIBUTOS DE SESIÓN
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	
	//Para Reporte
	private Integer intShowPanel;
	private List<Tabla> listaDescripcionCondicionSocio;
	private List<Tabla> listaDescripcionTipoCondicionSocio;
	
	
	
	
	
	public EstadoCuentaController(){
		log = Logger.getLogger(this.getClass());
		
		//INICIO DE SESION
		Usuario usuarioSesion = (Usuario)getRequest().getSession().getAttribute("usuario");
		SESION_IDUSUARIO = usuarioSesion.getIntPersPersonaPk();
		SESION_IDEMPRESA = usuarioSesion.getEmpresa().getIntIdEmpresa();
		SESION_IDSUCURSAL = usuarioSesion.getSucursal().getId().getIntIdSucursal();
		SESION_IDSUBSUCURSAL = usuarioSesion.getSubSucursal().getId().getIntIdSubSucursal();
		
		try {
			socioFacade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
			estadoCtaFacade = (EstadoCtaFacadeLocal)EJBFactory.getLocal(EstadoCtaFacadeLocal.class);
			creditoFacade = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);
			estructuraFacade = (EstructuraFacadeLocal)EJBFactory.getLocal(EstructuraFacadeLocal.class);
			personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			empresaFacade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			solicitudPrestamoFacade = (SolicitudPrestamoFacadeNuevoRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeNuevoRemote.class);
			planillaFacade = (PlanillaFacadeRemote)EJBFactory.getRemote(PlanillaFacadeRemote.class);

			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);			
			listaDescripcionModalidadPlanilla = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MODALIDADPLANILLA));
			listaDescripcionCondLaboral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONLABORAL));
			listaDescripcionTipoSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOSOCIO));
			listaDescripcionTipoMovimiento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_MOVIMIENTO));
			listaDescripcionTipoCargoAbono = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CARGOABONO));
			listaDescripcionMes = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MES_CALENDARIO));
			
			listaDescripcionCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CONDICIONSOCIO));
			listaDescripcionTipoCondicionSocio = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CONDSOCIO));
			
			
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		inicio();
	}

	public void inicio(){

		socioNaruralSelected = new SocioComp();
		socioNaruralSelected.setPersona(new Persona());
		
		cuentaComp = new CuentaComp();
		cuentaComp.setCuenta(new Cuenta());
		cuentaComp.setCuentaIntegrante(new CuentaIntegrante());
		cuentaComp.getCuentaIntegrante().setId(new CuentaIntegranteId());

		cuenta = new Cuenta();
		cuenta.setId(new CuentaId());
		
		expediente = new Expediente();
		expediente.setId(new ExpedienteId());
		expedienteMov = new Expediente();
		expedienteMov.setId(new ExpedienteId());
		
		efectMov = new Efectuado();
		efectMov.setId(new EfectuadoId());

		listYears = new ArrayList<SelectItem>();
		listaSocioBusquedaPorApeNom = new ArrayList<SocioComp>();
		listaCuentaComp = new ArrayList<CuentaComp>();
		listaRazonSocialSucursal = new ArrayList<Juridica>();
		lstCtasSocBusqueda = new ArrayList<CuentaComp>();
		
		//Show Panel's
		blnShowPanelDatosDelSocioYCuentas = Boolean.FALSE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamosResumePrestamos = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE;
		intShowPanel = 0;
		intTipoBusqueda = 3;
	}	
	//OBTENER LOS DATOS DEL SOCIO POR SU "CODIGO DE PERSONA" O "DNI"
	public void buscarDatosDelSocio(){
		intShowPanel = 1;
		log.info("-------------------------------------Debugging EstadoCuentaController.buscarSocio-------------------------------------");
		// POR CODIGO = 1 / POR NOMBRES O APELLIDOS = 2 / POR DNI = 3 
		fechaHoraActual();
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 
		List<SocioComp> listSocioBusqueda = null;
		socioComp = null;
		try {
			if (intTipoBusqueda.equals(Constante.PARAM_T_TIPOBUSQUEDA_CODIGO)) {
				socioComp = new SocioComp();
				socioComp.setSocio(new Socio());
				socioComp.getSocio().setId(new SocioPK());
				socioComp.getSocio().getId().setIntIdEmpresa(SESION_IDEMPRESA);
				socioComp.getSocio().getId().setIntIdPersona(Integer.parseInt(strIdPersonaBusqueda.trim()));
				socioComp = socioFacade.getSocioNatural(socioComp.getSocio().getId());
				if (socioComp.getSocio() != null) {
					blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
					blnShowPanelResumenEstadoCta = Boolean.TRUE;
					blnShowPanelTabDetalle = Boolean.FALSE;	
					obtenerCuentaYCtaIntegrante();
				}else{
					inicio();
				}
			}
			if (intTipoBusqueda.equals(Constante.PARAM_T_TIPOBUSQUEDA_DNI)) {
				String strDni = strIdPersonaBusqueda.trim();
				if (strDni.length()==8) {					
					socioComp = socioFacade.getSocioNatuPorDocIdentidadYIdEmpresa(1, strIdPersonaBusqueda.trim(), SESION_IDEMPRESA);
					if (socioComp.getSocio().getId() != null) {
						blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
						blnShowPanelResumenEstadoCta = Boolean.TRUE;
						blnShowPanelTabDetalle = Boolean.FALSE;	
						obtenerCuentaYCtaIntegrante();
					}else{
						inicio();
					}
				}else{
					inicio();
				}
			}
			
			if (intTipoBusqueda.equals(Constante.PARAM_T_TIPOBUSQUEDA_APELLIDOSYNOMBRES)) {
				blnShowPanelDatosDelSocioYCuentas = Boolean.FALSE;
				blnShowPanelResumenEstadoCta = Boolean.FALSE;
				
				socioComp = new SocioComp();
				socioComp.setPersona(new Persona());
				socioComp.setSocio(new Socio());
				socioComp.getSocio().setId(new SocioPK());		
				socioComp.getSocio().setSocioEstructura(new SocioEstructura());
				
				socioComp.setIntFechaBusq(0);
				socioComp.setIntSubsucursalBusq(0);
				socioComp.setIntSucursalBusq(0);
				socioComp.setIntTipoSucBusq(0);
				socioComp.getPersona().setIntTipoPersonaCod(Constante.PARAM_T_TIPOPERSONA_NATURAL);
				socioComp.getSocio().getId().setIntIdEmpresa(SESION_IDEMPRESA);
				socioComp.getSocio().getSocioEstructura().setIntModalidad(Constante.PARAM_T_MODALIDADPLANILLA_TODOS);
				socioComp.getSocio().getSocioEstructura().setIntTipoSocio(Constante.PARAM_T_TIPOSOCIO_TODOS);				
				socioComp.setStrDocIdentidad("");
				socioComp.setStrNombrePersona(strIdPersonaBusqueda.trim());				
				
				SocioFacadeLocal facade = (SocioFacadeLocal)EJBFactory.getLocal(SocioFacadeLocal.class);
				listSocioBusqueda = facade.getListaSocioComp(socioComp);
				if(listSocioBusqueda!=null && listSocioBusqueda.size()>0){
					log.info("listSocioBusqueda.size(): " + listSocioBusqueda.size());
					setListaSocioBusquedaPorApeNom(listSocioBusqueda);
				}
			}			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	//BUSUQEDA POR APELLIDOS Y NOMBRES
	public void buscarDatosSocioPorApellidosYNombres(){
		log.info("-------------------------------------Debugging EstadoCuentaController.buscarSocio-------------------------------------");
		try {
			socioComp = socioFacade.getSocioNatural(socioNaruralSelected.getSocio().getId());
			if (socioComp.getSocio() != null) {
				blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
				blnShowPanelResumenEstadoCta = Boolean.TRUE;
				blnShowPanelTabDetalle = Boolean.FALSE;
				setListaSocioBusquedaPorApeNom(null);
				obtenerCuentaYCtaIntegrante();
			}else{
				setListaSocioBusquedaPorApeNom(null);
				inicio();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void obtenerCuentaYCtaIntegrante(){
		log.info("-------------------------------------Debugging EstadoCuentaController.obtenerCuentaYCtaIntegrante-------------------------------------");
		List<CuentaComp> listaCuentaComp = null;
		try {
			//Lista de Cuentas del Socio
			lstCtasSocBusqueda = estadoCtaFacade.getCtaIntYCtaXSocTipYSitCta(socioComp.getSocio().getId(),null,intTipoCuentaBusqueda,null);
			
			//Llenando Combo de Cuentas del Socio
			if (lstCtasSocBusqueda!=null && !lstCtasSocBusqueda.isEmpty()) {
				for (CuentaComp ctaSoc : lstCtasSocBusqueda) {
					if (ctaSoc.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)==0) {
						ctaSoc.setStrDescripcionSituacionCuenta("VIGENTE");
					}
					if (ctaSoc.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO)==0) {
						ctaSoc.setStrDescripcionSituacionCuenta("LIQUIDADA");
					}
				}
			}
			
			if (intCtasSocBusqueda==0) {
				listaCuentaComp = estadoCtaFacade.getCtaIntYCtaXSocTipYSitCta(socioComp.getSocio().getId(),null,intTipoCuentaBusqueda, Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
				if (listaCuentaComp != null && !listaCuentaComp.isEmpty()) {
					cuentaComp = listaCuentaComp.get(0);
				}else {
					inicio();
				}
				if (cuentaComp != null) {
					strFechaRegistro = Constante.sdf.format(cuentaComp.getCuentaIntegrante().getTsFechaIngreso());
					obtenerSocioEstructura();
				}
			}else{
				listaCuentaComp = estadoCtaFacade.getCtaIntYCtaXSocTipYSitCta(socioComp.getSocio().getId(),intCtasSocBusqueda, intTipoCuentaBusqueda, null);
				if (listaCuentaComp != null && !listaCuentaComp.isEmpty()) {
					cuentaComp = listaCuentaComp.get(0);
				}else {
					inicio();
				}
				if (cuentaComp != null) {
					strFechaRegistro = Constante.sdf.format(cuentaComp.getCuentaIntegrante().getTsFechaIngreso());
					obtenerSocioEstructura();
				}
			}
			
			obtenerCuentasNoVigentes();
			/*
			listaCtaIntegranteVigente = cuentaFacade.getLstPorSocioPKTipoCtaSituacionCta(socioComp.getSocio().getId(),intTipoCuentaBusqueda,Constante.PARAM_T_SITUACIONCUENTA_VIGENTE);
			if (listaCtaIntegranteVigente!=null && !listaCtaIntegranteVigente.isEmpty()) {
				for (CuentaIntegrante lstCtaIntVigente : listaCtaIntegranteVigente) {
					CuentaComp ctaComp = new CuentaComp();
					ctaComp.setCuentaIntegrante(lstCtaIntVigente);
					
				}
			}
			listaCtaIntegranteLiquidada = cuentaFacade.getLstPorSocioPKTipoCtaSituacionCta(socioComp.getSocio().getId(),intTipoCuentaBusqueda,Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO);
			if (listaCtaIntegranteLiquidada!=null && !listaCtaIntegranteLiquidada.isEmpty()) {
				for (CuentaIntegrante lstCtaIntLiquidada : listaCtaIntegranteLiquidada) {
					CuentaComp ctaComp = new CuentaComp();
					ctaComp.setCuentaIntegrante(lstCtaIntLiquidada);
				}
			}*/
			
			//OBTENER COMBO DE CUENTAS VIGENTES Y LIQUIDADAS			
			/*
			
			lstCtasSocBusqueda = estadoCtaFacade.getLsCtasPorPkSocioYTipCta(socioComp.getSocio().getId(), intTipoCuentaBusqueda,null,null);
			if (lstCtasSocBusqueda!=null && !lstCtasSocBusqueda.isEmpty()) {
				for (CuentaComp lstCtasSoc : lstCtasSocBusqueda) {
					if (lstCtasSoc.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE)==0) {
						lstCtasSoc.setStrDescripcionSituacionCuenta("VIGENTE");
					}
					if (lstCtasSoc.getCuenta().getIntParaSituacionCuentaCod().compareTo(Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO)==0) {
						lstCtasSoc.setStrDescripcionSituacionCuenta("LIQUIDADA");
					}
				}
			}
			
			if (intCtasSocBusqueda==0) {
				listaCuentaComp = estadoCtaFacade.getLsCtasPorPkSocioYTipCta(socioComp.getSocio().getId(), intTipoCuentaBusqueda, Constante.PARAM_T_SITUACIONCUENTA_VIGENTE,null);
				if (listaCuentaComp != null && !listaCuentaComp.isEmpty()) {
					cuentaComp = listaCuentaComp.get(0);
				}else {
					inicio();
				}
				if (cuentaComp != null) {
					strFechaRegistro = Constante.sdf.format(cuentaComp.getCuentaIntegrante().getTsFechaIngreso());
					obtenerSocioEstructura();
				}
			}else{
				listaCuentaComp = estadoCtaFacade.getLsCtasPorPkSocioYTipCta(socioComp.getSocio().getId(), intTipoCuentaBusqueda, null, intCtasSocBusqueda);
				if (listaCuentaComp != null && !listaCuentaComp.isEmpty()) {
					cuentaComp = listaCuentaComp.get(0);
				}else {
					inicio();
				}
				if (cuentaComp != null) {
					strFechaRegistro = Constante.sdf.format(cuentaComp.getCuentaIntegrante().getTsFechaIngreso());
					obtenerSocioEstructura();
				}
			}
			
			cuentaComp.getCuenta().setId(new CuentaId());
			cuentaComp.getCuenta().setId(cuenta.getId());*/
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
	//Recupera datos de Socio Estructura
	public void obtenerSocioEstructura(){
		log.info("-------------------------------------Debugging EstadoCuentaController.obtenerSocioEstructura-------------------------------------");
		listaUnidadEjecutora = new ArrayList<Tabla>();
		listaRazonSocialSucursal = new ArrayList<Juridica>();
		String strModalidad = null;
		String strCondLaboral = null;
		String strTipoSocio = null;
		SucursalId sucursalId = new SucursalId();
		Estructura estructura = null;
		Sucursal sucursal = null;
		Juridica juridica = null;
		try {			
			if (socioComp.getSocio().getListSocioEstructura()!=null && !socioComp.getSocio().getListSocioEstructura().isEmpty()) {
				for (SocioEstructura lstSocEstruc : socioComp.getSocio().getListSocioEstructura()) {
					if (lstSocEstruc.getIntEstadoCod().compareTo(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO)==0) {
						//Sucursal
						sucursalId.setIntIdSucursal(lstSocEstruc.getIntIdSucursalAdministra());
						sucursal = empresaFacade.getSucursalPorId(sucursalId.getIntIdSucursal());
						listaRazonSocialSucursal.add(sucursal.getJuridica());
						log.info("--- SUCURSAL ---"+sucursal.getJuridica().getStrRazonSocial());

						//Nombre Estructura
						EstructuraId estructuraId = new EstructuraId();
						estructuraId.setIntNivel(lstSocEstruc.getIntNivel());
						estructuraId.setIntCodigo(lstSocEstruc.getIntCodigo());
						estructura = estructuraFacade.getEstructuraPorPK(estructuraId);
						juridica = personaFacade.getJuridicaPorPK(estructura.getIntPersPersonaPk());
						
						lstSocEstruc.setStrRazonSocial(juridica.getStrRazonSocial());
						log.info("--- NOMBRE ESTRUCTURA ---"+lstSocEstruc.getStrRazonSocial());

						//Condicion Laboral
						lstSocEstruc.setIntCondicionLaboral(socioComp.getPersona().getNatural().getPerLaboral().getIntCondicionLaboral());
						for (Tabla condLab : listaDescripcionCondLaboral) {
							if(condLab.getIntIdDetalle().compareTo(lstSocEstruc.getIntCondicionLaboral())==0){
								strCondLaboral = condLab.getStrDescripcion();
							}
						}
						log.info("--- CONDICION LABORAL ---"+strCondLaboral);
						
						//Modalidad Planilla
						for (Tabla modalidad : listaDescripcionModalidadPlanilla) {
							if(modalidad.getIntIdDetalle().compareTo(lstSocEstruc.getIntModalidad())==0){
								strModalidad = modalidad.getStrDescripcion();
							}
						}
						log.info("--- MODALIDAD PLANILLA ---"+strModalidad);
						//Tipo De Socio
						for (Tabla tipoSocio : listaDescripcionTipoSocio) {
							if(tipoSocio.getIntIdDetalle().compareTo(lstSocEstruc.getIntTipoSocio())==0){
								strTipoSocio = tipoSocio.getStrDescripcion();
							}
						}
						log.info("--- TIPO DE SOCIO ---"+strTipoSocio);
						//Unidad Ejecutora : Nombre_Estructura + Condicion_Laboral + Mod_Planilla + Tipo_Socio
						String strUnidadEjecutora = "";
						strUnidadEjecutora =  lstSocEstruc.getStrRazonSocial() + " - " +strCondLaboral+" - "+strModalidad+ " - " +strTipoSocio;
						Tabla tbl = new Tabla();
						tbl.setStrAbreviatura(strUnidadEjecutora);
						listaUnidadEjecutora.add(tbl);
					}
				}
			}		
			obtenerCuentaMovimiento();
		} catch (Exception e) {			
			log.error(e.getMessage(), e);
		}	
	}
	//Recupera montos de Beneficios de la cuenta del Socio
	private void obtenerCuentaMovimiento(){
		log.info("-------------------------------------Debugging EstadoCuentaController.obtenerCuentaMovimiento-------------------------------------");
		//Cta. Aporte:
		bdMontoConceptoAporte = BigDecimal.ZERO;
		//Saldo Aporte:
		bdCucoSaldoAporte = BigDecimal.ZERO;
		//Fondo de Retiro:
		bdCucoSaldoRetiro = BigDecimal.ZERO;
		//Pendiente: Fondo Sepelio
		bdMontoSaldoSepelio = BigDecimal.ZERO;
		//Pendiente: Mantenimiento
		bdMontoSaldoMantenimiento = BigDecimal.ZERO;

		String strPeriodo = null;
		Integer intAnioPeriodo = null;
		Integer intMesPeriodo = null;
		
		CuentaId ctaId = new CuentaId();
		List<CuentaConcepto> listaCuentaConcepto = null;
		List<CuentaConceptoDetalle> listaCuentaConceptoDetalle = null;
		List<ConceptoPago> listaConceptoPago = null;		
		
		try {
			
			//Cargando objeto "Cuenta" para búsqueda de Cuenta Concepto....
			ctaId.setIntCuenta(cuentaComp.getCuenta().getId().getIntCuenta());
			ctaId.setIntPersEmpresaPk(SESION_IDEMPRESA);
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(ctaId);
			if (listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()) {
				for (CuentaConcepto ctaCto : listaCuentaConcepto) {
					listaCuentaConceptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(ctaCto.getId());
					if (listaCuentaConceptoDetalle != null && !listaCuentaConceptoDetalle.isEmpty()) {
						for (CuentaConceptoDetalle ctaCtoDet : listaCuentaConceptoDetalle) {
							switch (ctaCtoDet.getIntParaTipoConceptoCod()) {
							//Aportes --> 1
							case 1:
								bdCucoSaldoAporte = bdCucoSaldoAporte.add(ctaCto.getBdSaldo());
								bdMontoConceptoAporte = bdMontoConceptoAporte.add(ctaCtoDet.getBdMontoConcepto());	
								break;
							//Fondo de Sepelio --> 2 | Mantenimiento de Cuenta --> 10
							case 2: case 10:
								listaConceptoPago = conceptoFacade.getListaConceptoPagoPorCuentaConceptoDet(ctaCtoDet.getId());
								if (listaConceptoPago != null && !listaConceptoPago.isEmpty()) {
									//Obteniendo Saldos de Concepto Pago (COPA_MONTOSALDO) 
									Integer ultimoRegistro = 0;
									BigDecimal bdMontoSaldoUltRegistro = BigDecimal.ZERO;
									for (ConceptoPago concepPago : listaConceptoPago) {
										if(concepPago.getBdMontoSaldo()== null){
											if (ctaCtoDet.getIntParaTipoConceptoCod()==2) {
												bdMontoSaldoSepelio = bdMontoSaldoSepelio.add(BigDecimal.ZERO);
											}else if (ctaCtoDet.getIntParaTipoConceptoCod()==10){
												bdMontoSaldoMantenimiento = bdMontoSaldoMantenimiento.add(BigDecimal.ZERO);
											}									
										}else{
											if (ctaCtoDet.getIntParaTipoConceptoCod()==2) {
												bdMontoSaldoSepelio = bdMontoSaldoSepelio.add(concepPago.getBdMontoSaldo());
											}else if (ctaCtoDet.getIntParaTipoConceptoCod()==10){
												bdMontoSaldoMantenimiento = bdMontoSaldoMantenimiento.add(concepPago.getBdMontoSaldo());
											}									
										}
										//Obteniendo periodo y saldo del ultimo registro de Concepto Pago (el de mayor CMOV_ITEMCONCEPTOPAGO_N)
										if (ultimoRegistro == 0) {
											strPeriodo = Integer.toString(concepPago.getIntPeriodo());			
											bdMontoSaldoUltRegistro = concepPago.getBdMontoSaldo();
											ultimoRegistro++;
										}
									}
									intAnioPeriodo = Integer.parseInt(strPeriodo.substring(0, 4));
									intMesPeriodo = Integer.parseInt(strPeriodo.substring(4, 6));	
									
									if (ctaCtoDet.getIntParaTipoConceptoCod()==2){
										if (bdMontoSaldoSepelio.compareTo(BigDecimal.ZERO) == 0) {
											calculoDiferenciaMeses(intAnioPeriodo, intMesPeriodo);
											bdMontoSaldoSepelio = bdMontoSaldoUltRegistro.multiply(bdDiferenciaMes);
										}
									}
									if (ctaCtoDet.getIntParaTipoConceptoCod()==10) {
										if (bdMontoSaldoMantenimiento.compareTo(BigDecimal.ZERO) == 0) {
											calculoDiferenciaMeses(intAnioPeriodo, intMesPeriodo);
											bdMontoSaldoMantenimiento = bdMontoSaldoUltRegistro.multiply(bdDiferenciaMes);
										}
									}
								}/*else {
									intAnioPeriodo = Integer.parseInt(Constante.sdfAnio.format(ctaCtoDet.getTsInicio()));
									intMesPeriodo = Integer.parseInt(Constante.sdfMes.format(ctaCtoDet.getTsInicio()));
									calculoDiferenciaMeses(intAnioPeriodo, intMesPeriodo);
									
									if (ctaCtoDet.getIntParaTipoConceptoCod()==2) {
										bdMontoSaldoSepelio = ctaCtoDet.getBdMontoConcepto().multiply(bdDiferenciaMes);
									}else if (ctaCtoDet.getIntParaTipoConceptoCod()==10){
										bdMontoSaldoMantenimiento = ctaCtoDet.getBdMontoConcepto().multiply(bdDiferenciaMes);
									}	
								}*/
								break;								
							//Fondo de Retiro --> 3
							case 3:
								bdCucoSaldoRetiro = ctaCto.getBdSaldo();
								break;
							default:
								break;
							}
						}
					}
				}
			}
			obtenerPrestamos();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	/*******
	 * OBTENER DATOS DE LOS PRESTAMOS QUE TIENE EL SOCIO PARA LA CUENTA VIGENTE
	 *******/	
	public void obtenerPrestamos(){
		log.info("-------------------------------------Debugging EstadoCuentaController.obtenerPrestamos-------------------------------------");

		//Ultimo Envio Aportes
		bdUltimoEnvioAportes = BigDecimal.ZERO;
		//Ultimo Envio Fondo de Sepelio
		bdUltimoEnvioFdoSepelio = BigDecimal.ZERO;
		//Ultimo Envio Fondo de Retiro
		bdUltimoEnvioFdoRetiro = BigDecimal.ZERO;
		//Ultimo Envio Mantenimiento
		bdUltimoEnvioMant = BigDecimal.ZERO;
		
		bdSumatoriaSaldo = BigDecimal.ZERO;
		lstDataBeanEstadoCuentaPrestamos = new ArrayList<DataBeanEstadoCuentaPrestamos>();		
		intMesPeriodoTemp = null;
		intAnioPeriodoTemp = null;
		
		Integer periodoTemp = null;		
		String strCuotas = null;
		BigDecimal bdDiferencia = BigDecimal.ZERO;
		BigDecimal bdUltimoEnvioPrestamos = BigDecimal.ZERO;
		BigDecimal bdSaldoCredito = BigDecimal.ZERO;
		BigDecimal bdTasaInteres = BigDecimal.ZERO;
		BigDecimal bdMontoTotal = BigDecimal.ZERO;
		BigDecimal bdMontoUltimoEnvio = BigDecimal.ZERO;
		
		ExpedienteId expId = new ExpedienteId();
		Expediente expMov = null;
		CreditoId cId = new CreditoId();
		List<Envioconcepto> listaEnvioconcepto = null;
		List<Enviomonto> listaEnviomonto = null;
		List<Efectuado> listaEfectuado = null;
		List<EfectuadoConcepto> listaEfectuadoConcepto = null;
		Credito credito = null;		
		List<ExpedienteCredito> listaExpedienteCredito = null;
		List<EstadoCredito> listaEstadoCredito = null;
		List<Cronograma> listaCronograma = null;
		
		List<CuentaConcepto> listaCuentaConcepto = null;
		List<CuentaConceptoDetalle> listaCuentaConceptoDetalle = null;
		
		try {
			listaExpedienteCredito = solicitudPrestamoFacade.getListaExpedienteCreditoPorCuenta(cuentaComp.getCuenta());
			if (listaExpedienteCredito != null && !listaExpedienteCredito.isEmpty()) {
				for (ExpedienteCredito expCred : listaExpedienteCredito) {
					listaEstadoCredito = solicitudPrestamoFacade.getListaEstadosPorExpedienteCreditoId(expCred.getId());
					if (listaEstadoCredito != null && !listaEstadoCredito.isEmpty()) {
						for (EstadoCredito estCred : listaEstadoCredito) {
							String strFecEst = Constante.sdf.format(estCred.getTsFechaEstado());
							if ((estCred.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0 && 
									(expCred.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)!=0 ||
											expCred.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_MULTA)!=0 ||
													expCred.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)!=0)) || 
								(estCred.getIntParaEstadoCreditoCod().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)==0 && 
										(expCred.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)==0 ||
												expCred.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_MULTA)==0 ||
														expCred.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)==0))) {
								
								expId.setIntCuentaPk(estCred.getId().getIntCuentaPk());
								expId.setIntPersEmpresaPk(estCred.getId().getIntPersEmpresaPk());
								expId.setIntItemExpediente(estCred.getId().getIntItemExpediente());
								expId.setIntItemExpedienteDetalle(estCred.getId().getIntItemDetExpediente());
								expMov = conceptoFacade.getExpedientePorPK(expId);
								if (expMov != null) {							
									//Filtro: si la Situacion de la cta es "Liquidada" o ( es "Vigente" y con saldo mayor a 0)
									if ((cuentaComp.getCuenta().getIntParaSituacionCuentaCod().equals(Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO)) || (cuentaComp.getCuenta().getIntParaSituacionCuentaCod().equals(Constante.PARAM_T_SITUACIONCUENTA_VIGENTE) && expMov.getBdSaldoCredito().compareTo(BigDecimal.ZERO)!=0)) {					
										cId.setIntPersEmpresaPk(SESION_IDEMPRESA);
										cId.setIntParaTipoCreditoCod(expMov.getIntParaTipoCreditoCod());
										cId.setIntItemCredito(expMov.getIntItemCredito());
										credito = creditoFacade.getCreditoPorIdCreditoDirecto(cId);
										
										listaCronograma = conceptoFacade.getListaCronogramaPorPkExpediente(expMov.getId());
										Integer cantCtasPagadas = 0;
										Integer cantCtasAtrasadas = 0;
										if (listaCronograma != null && !listaCronograma.isEmpty()) {
											for (Cronograma cronograma : listaCronograma) {
												Timestamp  timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
												// Diferenciando conceptos: IntParaTipoConceptoCreditoCod == 1(capital)  ---  IntParaTipoConceptoCreditoCod == 2(interes) Constante.PARAM_T_TIPOCPTOCRED_CAPITAL **/
												// Cuotas Pagadas
												if ((cronograma.getIntParaTipoConceptoCreditoCod().equals(Constante.PARAM_T_TIPOCPTOCRED_CAPITAL)) && (timestamp.compareTo(cronograma.getTsFechaVencimiento()) >= 0) && (cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)==0)) {
													cantCtasPagadas ++;
												}
												// Cuotas Atrasadas
												if ((cronograma.getIntParaTipoConceptoCreditoCod().equals(Constante.PARAM_T_TIPOCPTOCRED_CAPITAL)) && (timestamp.compareTo(cronograma.getTsFechaVencimiento()) >= 0) && (cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)!=0)) {
													cantCtasAtrasadas ++;
												}
											}
										}
										
										strCuotas = String.valueOf(expMov.getIntNumeroCuota()) + "/" + String.valueOf(cantCtasPagadas) + "/" + String.valueOf(cantCtasAtrasadas);
										
										if (expMov.getBdSaldoCredito().compareTo(BigDecimal.ZERO)==0) {
											log.info("El saldo credito es 0, NO MOSTRAR "+expMov.getBdSaldoCredito());
										}else{
											bdSaldoCredito = expMov.getBdSaldoCredito();
											//Sumatoria de Saldos
											bdSumatoriaSaldo = bdSumatoriaSaldo.add(expMov.getBdSaldoCredito());
										}
										Integer ultimoEnvio = 0;
										listaEnvioconcepto = planillaFacade.getListaEnvioconceptoPorPkExpedienteCredito(expMov.getId());
										
										bdUltimoEnvioPrestamos = BigDecimal.ZERO;	
										if (listaEnvioconcepto != null && !listaEnvioconcepto.isEmpty()) {
											Integer n = 0;
											for (Envioconcepto envioConcep : listaEnvioconcepto) {
												if (n==0) {
													periodoTemp = envioConcep.getIntPeriodoplanilla();
													n++;
												}
											}
											//Año y Mes del ultimo Envio
											intAnioPeriodoTemp = Integer.parseInt(Integer.toString(periodoTemp).substring(0, 4));
											intMesPeriodoTemp = Integer.parseInt(Integer.toString(periodoTemp).substring(4, 6));
											
											BigDecimal bdMontoConceptoEnvio = BigDecimal.ZERO;
											BigDecimal bdMontoConceptoEfectuado = BigDecimal.ZERO;
													
											for (Envioconcepto envioConcep : listaEnvioconcepto) {
												if (envioConcep.getIntPeriodoplanilla().compareTo(periodoTemp)==0) {
													bdUltimoEnvioPrestamos = bdUltimoEnvioPrestamos.add(envioConcep.getBdMontoconcepto());
												}
												listaEnviomonto = planillaFacade.getListaPorEnvioConcepto(envioConcep);
												if (listaEnviomonto != null && !listaEnviomonto.isEmpty()) {
													for (Enviomonto envioMonto : listaEnviomonto) {									
														listaEfectuado = planillaFacade.getListaEfectuadoPorPkEnviomontoYPeriodo(envioMonto.getId(), envioConcep);
														if (listaEfectuado != null && !listaEfectuado.isEmpty()) {
															if (ultimoEnvio == 0) {
																bdMontoConceptoEnvio.add(envioConcep.getBdMontoconcepto());
																for (Efectuado efectuado : listaEfectuado) {
																	listaEfectuadoConcepto = planillaFacade.getListaPorEfectuadoYExpediente(efectuado.getId(),expMov);
																	if (listaEfectuadoConcepto != null && !listaEfectuadoConcepto.isEmpty()) {
																		for (EfectuadoConcepto efecConcep : listaEfectuadoConcepto) {
																			bdMontoConceptoEfectuado = efecConcep.getBdMontoConcepto();														
																		}
																	}
																}
																ultimoEnvio++;
															}
														}
													}
												}
											}
											bdDiferencia = bdMontoConceptoEnvio.subtract(bdMontoConceptoEfectuado);
										}
										//Porcetanje Interes
										bdTasaInteres = expMov.getBdPorcentajeInteres()!=null?expMov.getBdPorcentajeInteres():BigDecimal.ZERO;
										//Monto Total
										bdMontoTotal = expMov.getBdMontoTotal()!=null?expMov.getBdMontoTotal():BigDecimal.ZERO;
										//Saldo Credito
										bdSaldoCredito = expMov.getBdSaldoCredito()!=null?expMov.getBdSaldoCredito():BigDecimal.ZERO;
										
										DataBeanEstadoCuentaPrestamos dbEstCtaPrestamos = new DataBeanEstadoCuentaPrestamos();
										dbEstCtaPrestamos.setStrFecha(strFecEst);
										dbEstCtaPrestamos.setStrDescripcion(credito.getStrDescripcion());
										dbEstCtaPrestamos.setBdTasaInteres(bdTasaInteres);
										dbEstCtaPrestamos.setBdMontoTotal(bdMontoTotal);
										dbEstCtaPrestamos.setStrCuotas(strCuotas);
										dbEstCtaPrestamos.setBdSaldoCredito(bdSaldoCredito);
										dbEstCtaPrestamos.setBdDiferencia(bdDiferencia);
										dbEstCtaPrestamos.setBdUltimoEnvio(bdUltimoEnvioPrestamos);	
										lstDataBeanEstadoCuentaPrestamos.add(dbEstCtaPrestamos);
										bdMontoUltimoEnvio = bdMontoUltimoEnvio.add(bdUltimoEnvioPrestamos);
									}
								}
							}
						}
					}
				}
			}

			Collections.sort(lstDataBeanEstadoCuentaPrestamos, new Comparator<DataBeanEstadoCuentaPrestamos>() {
	            public int compare(DataBeanEstadoCuentaPrestamos e1, DataBeanEstadoCuentaPrestamos e2) {
	            	Date d1 = new Date(),d2 = new Date();
	            	try {
						d1=formatoDeFecha.parse(e1.getStrFecha());
						d2=formatoDeFecha.parse(e2.getStrFecha());
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return d1.compareTo(d2);
	            }
	        });
			
			//Obtener Envio Concepto de Cobranza
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaComp.getCuenta().getId());
			if (listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()) {
				for (CuentaConcepto ctaCto : listaCuentaConcepto) {
					listaCuentaConceptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(ctaCto.getId());
					if (listaCuentaConceptoDetalle != null && !listaCuentaConceptoDetalle.isEmpty()) {
						listaEnvioconcepto.clear();
						for (CuentaConceptoDetalle ctaCtoDet : listaCuentaConceptoDetalle) {
							switch (ctaCtoDet.getIntParaTipoConceptoCod()) {
							// Aportes --> 1
							case 1:
								listaEnvioconcepto = planillaFacade.getListaEnvioconceptoPorCtaCptoDetYPer(ctaCtoDet.getId(), periodoTemp);
								if (listaEnvioconcepto!=null && !listaEnvioconcepto.isEmpty()) {
									for (Envioconcepto envCpto : listaEnvioconcepto) {
										bdUltimoEnvioAportes = bdUltimoEnvioAportes.add(envCpto.getBdMontoconcepto());
									}
									
								}
								break;
							//Fondo de Sepelio --> 2 | Mantenimiento de Cuenta --> 10
							case 2: case 10:
								listaEnvioconcepto = planillaFacade.getListaEnvioconceptoPorCtaCptoDetYPer(ctaCtoDet.getId(), periodoTemp);
								if (listaEnvioconcepto!=null && !listaEnvioconcepto.isEmpty()) {
									for (Envioconcepto envCpto : listaEnvioconcepto) {
										if (ctaCtoDet.getIntParaTipoConceptoCod() == 2) {
											bdUltimoEnvioFdoSepelio = bdUltimoEnvioFdoSepelio.add(envCpto.getBdMontoconcepto());
										}
										if (ctaCtoDet.getIntParaTipoConceptoCod() == 10) {
											bdUltimoEnvioMant = bdUltimoEnvioMant.add(envCpto.getBdMontoconcepto());
										}
									}
								}
								break;
							//Fondo de Retiro --> 3
							case 3:		
								listaEnvioconcepto = planillaFacade.getListaEnvioconceptoPorCtaCptoDetYPer(ctaCtoDet.getId(), periodoTemp);
								if (listaEnvioconcepto!=null && !listaEnvioconcepto.isEmpty()) {
									for (Envioconcepto envCpto : listaEnvioconcepto) {
										bdUltimoEnvioFdoRetiro = bdUltimoEnvioFdoRetiro.add(envCpto.getBdMontoconcepto());
									}
								}
								break;
							default:
								break;
							}
						}
					}
				}
			}
			//Aportes
			bdUltimoEnvioAportes = bdUltimoEnvioAportes!=null?bdUltimoEnvioAportes:BigDecimal.ZERO;			
			//Fondo de Sepelio
			bdUltimoEnvioFdoSepelio = bdUltimoEnvioFdoSepelio!=null?bdUltimoEnvioFdoSepelio:BigDecimal.ZERO;
			//Fondo de Retiro
			bdUltimoEnvioFdoRetiro = bdUltimoEnvioFdoRetiro!=null?bdUltimoEnvioFdoRetiro:BigDecimal.ZERO;
			//Mantenimiento
			bdUltimoEnvioMant = bdUltimoEnvioMant!=null?bdUltimoEnvioMant:BigDecimal.ZERO;
			
			//Sumatoria de Ultimo Envio
			bdSumatoriaUltimoEnvio = bdMontoUltimoEnvio.add(bdUltimoEnvioAportes).add(bdUltimoEnvioFdoSepelio).add(bdUltimoEnvioFdoRetiro).add(bdUltimoEnvioMant);

			
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	//Recupera cuentas NO VIGENTES del Socio
	public void obtenerCuentasNoVigentes(){
		log.info("-------------------------------------Debugging EstadoCuentaController.obtenerCuentasNoVigentes-------------------------------------");
		ExpedienteLiquidacion expLiq;
		ExpedienteLiquidacionDetalle expLiqDet;
		
		List<ExpedienteLiquidacionDetalle> listaExpedienteLiquidacionDetalle = null;
		List<EstadoLiquidacion> listaEstadoLiquidacion = null;
		List<CuentaComp> listaCuentasLiquidadas = null;
		
		listaCuentaComp.clear();
		try {
			
			listaCuentasLiquidadas = estadoCtaFacade.getCtaIntYCtaXSocTipYSitCta(socioComp.getSocio().getId(),null,intTipoCuentaBusqueda,Constante.PARAM_T_SITUACIONCUENTA_LIQUIDADO);
			if (listaCuentasLiquidadas != null && !listaCuentasLiquidadas.isEmpty()) {
				for (CuentaComp ctaLiq : listaCuentasLiquidadas) {
					listaExpedienteLiquidacionDetalle = solicitudPrestamoFacade.getPorCuentaId(ctaLiq.getCuenta().getId());
					if (listaExpedienteLiquidacionDetalle!=null && !listaExpedienteLiquidacionDetalle.isEmpty()) {
						expLiqDet = new ExpedienteLiquidacionDetalle();
						expLiqDet.setId(new ExpedienteLiquidacionDetalleId());
						expLiq = new ExpedienteLiquidacion();
						expLiq.setId(new ExpedienteLiquidacionId());
						//
						expLiqDet = listaExpedienteLiquidacionDetalle.get(0);
						expLiq.getId().setIntPersEmpresaPk(expLiqDet.getId().getIntPersEmpresa());
						expLiq.getId().setIntItemExpediente(expLiqDet.getId().getIntItemExpediente());
					
						listaEstadoLiquidacion = solicitudPrestamoFacade.getPorExpediente(expLiq);
						if (listaEstadoLiquidacion!=null && !listaEstadoLiquidacion.isEmpty()) {
							for (EstadoLiquidacion lstEstLiq : listaEstadoLiquidacion) {
								if (lstEstLiq.getIntParaEstado().compareTo(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)==0) {
									ctaLiq.setStrFechaRenuncia(lstEstLiq.getTsFechaEstado()!=null?Constante.sdf.format(lstEstLiq.getTsFechaEstado()):"");
									listaCuentaComp.add(ctaLiq);
								}
							}
						}						
					}					
				}	
			}			
		} catch (Exception e) {
		log.error(e.getMessage(), e);
		}
	}
	

	public void cargarListaDetalladaBeneficiosYPrestamos(){
		intShowPanel = 2;
		blnShowPanelTabDetalle = Boolean.TRUE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 
		blnShowPanelResumenEstadoCta = Boolean.TRUE;
		listaEstadoCuentaComp = new ArrayList<EstadoCuentaComp>();
		listaSumatoriaEstadoCuentaComp = new ArrayList<EstadoCuentaComp>();
		bdMontoMovPrestamos = BigDecimal.ZERO;
		bdMontoMovCreditos = BigDecimal.ZERO;
		bdMontoMovActividad = BigDecimal.ZERO;
		bdMontoMovInteres = BigDecimal.ZERO;
		bdMontoMovMulta = BigDecimal.ZERO;
		bdMontoMovAportes = BigDecimal.ZERO;
		bdMontoMovMntCuenta = BigDecimal.ZERO;
		bdMontoMovFdoSepelio = BigDecimal.ZERO;
		bdMontoMovFdoRetiro = BigDecimal.ZERO;
		bdMontoMovCtaXPagar = BigDecimal.ZERO;
		bdMontoMovCtaXCobrar = BigDecimal.ZERO;
		BigDecimal bdSumatoriaMontoMovPrestamos = BigDecimal.ZERO;
		BigDecimal bdSumatoriaMontoMovCreditos = BigDecimal.ZERO;
		BigDecimal bdSumatoriaMontoMovActividad = BigDecimal.ZERO;
		BigDecimal bdSumatoriaMontoMovMulta = BigDecimal.ZERO;
		BigDecimal bdSumatoriaMontoMovAportes = BigDecimal.ZERO;
		BigDecimal bdSumatoriaMontoMovFdoRetiro = BigDecimal.ZERO;
		fechaHoraActual();
		
		try {
			listaMovCtaCtoXExpCred = conceptoFacade.getListXCuentaYEmpresa(SESION_IDEMPRESA,cuentaComp.getCuenta().getId().getIntCuenta());
			if (listaMovCtaCtoXExpCred != null && !listaMovCtaCtoXExpCred.isEmpty()) {
				Integer tipMovCompare = 0;
				Integer tipConcepGralCompare = 0;
				Integer itemExpCredCompare = 0;
				Integer itemDetExpCredCompare = 0;
				Integer itemCtaCto = 0;
				String nroDoc = "";
				Timestamp fecMovCompare = new Timestamp(Calendar.getInstance().getTimeInMillis());
				String strMontoMovPrestamos = "";
				String strMontoMovCreditos = "";
				String strMontoMovInteres = "";
				String strMontoMovActividad = "";
				String strMontoMovMulta = "";
				String strMontoMovAportes = "";
				String strMontoMovFdoSepelio = "";
				String strMontoMovFdoRetiro = "";
				String strMontoMovMntCuenta = "";
				String strMontoMovCtaXPagar = "";
				String strMontoMovCtaXCobrar = "";
				String strTipoMov = "";	
				String strTipoAddSubtract = "";
				Integer cont = 0;
				String strFechaDetalle = "";
				List<EstadoCuentaComp> listFechaDetalle = new ArrayList<EstadoCuentaComp>();
				for (Movimiento movCtaCto : listaMovCtaCtoXExpCred) {
					String flag = "";
					if (cont != 0) {
						if (strFechaDetalle.compareTo(Constante.sdf.format(movCtaCto.getTsFechaMovimiento()))==0) {
							flag = "S";
						}													
					}
					strFechaDetalle = Constante.sdf.format(movCtaCto.getTsFechaMovimiento());
					if (flag == "") {
						EstadoCuentaComp tbl = new EstadoCuentaComp();
						tbl.setStrFecha(strFechaDetalle);
						listFechaDetalle.add(tbl);	
					}										
					cont += 1;
				}
				
				for (EstadoCuentaComp lstFecDet : listFechaDetalle) {
					cont = 0;
					for (Movimiento movCtaCto : listaMovCtaCtoXExpCred) {
						String flag = "";
						if (lstFecDet.getStrFecha().compareTo(Constante.sdf.format(movCtaCto.getTsFechaMovimiento()))==0 && movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES)!=0) {
							if (cont != 0) {
								if (movCtaCto.getIntItemCuentaConcepto() != null) {
									if (itemCtaCto.compareTo(movCtaCto.getIntItemCuentaConcepto())==0) {
										if (tipMovCompare.compareTo(movCtaCto.getIntParaTipoMovimiento())!=0) {
											flag = "S";
											adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
													strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
													strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
											//LIMPIO VARIABLES
											strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
											strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
											strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
										}else {
											if (tipConcepGralCompare.compareTo(movCtaCto.getIntParaTipoConceptoGeneral())==0) {
												if (movCtaCto.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA)==0 || 
														movCtaCto.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_EGRESO_POR_CAJA)==0 ||
														movCtaCto.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_INGRESO_POR_CAJA)==0) {
													if (nroDoc.compareTo(movCtaCto.getStrNumeroDocumento())!=0) {
														flag = "S";
														adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
																strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
																strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
														//LIMPIO VARIABLES
														strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
														strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
														strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
													}else {
														if (fecMovCompare.compareTo(movCtaCto.getTsFechaMovimiento())!=0) {
															flag = "S";
															adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
																	strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
																	strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
															//LIMPIO VARIABLES
															strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
															strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
															strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
														}
													}
												}
											}else {
												if (nroDoc.compareTo(movCtaCto.getStrNumeroDocumento())!=0) {
													flag = "S";
													adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
															strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
															strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
													//LIMPIO VARIABLES
													strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
													strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
													strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
												}
											}
										}
									}else {
										flag = "S";
										adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
												strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
												strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
										//LIMPIO VARIABLES
										strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
										strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
										strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
									}
								}
								if (movCtaCto.getIntItemExpediente() != null && movCtaCto.getIntItemExpedienteDetalle() != null) {
									if (itemExpCredCompare.compareTo(movCtaCto.getIntItemExpediente())==0 || itemDetExpCredCompare.compareTo(movCtaCto.getIntItemExpedienteDetalle())==0) {
										if (tipMovCompare.compareTo(movCtaCto.getIntParaTipoMovimiento())!=0) {
											flag = "S";
											adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
													strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
													strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
											//LIMPIO VARIABLES
											strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
											strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
											strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
										}else {
											if (tipConcepGralCompare.compareTo(movCtaCto.getIntParaTipoConceptoGeneral())==0) {
												if (movCtaCto.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA)==0 || 
														movCtaCto.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_EGRESO_POR_CAJA)==0 ||
														movCtaCto.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_INGRESO_POR_CAJA)==0) {
													if (nroDoc.compareTo(movCtaCto.getStrNumeroDocumento())!=0) {
														flag = "S";
														adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
																strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
																strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
														//LIMPIO VARIABLES
														strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
														strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
														strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
													}else {
														if (fecMovCompare.compareTo(movCtaCto.getTsFechaMovimiento())!=0) {
															flag = "S";
															adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
																	strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
																	strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
															//LIMPIO VARIABLES
															strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
															strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
															strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
														}
													}
												}else {
													if (fecMovCompare.compareTo(movCtaCto.getTsFechaMovimiento())!=0) {
														flag = "S";
														adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
																strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
																strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
														//LIMPIO VARIABLES
														strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
														strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
														strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
													}	
												}
											}else {
												if (nroDoc.compareTo(movCtaCto.getStrNumeroDocumento())!=0) {
													flag = "S";
													adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
															strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
															strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
													//LIMPIO VARIABLES
													strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
													strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
													strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
												}
											}
										}
									}else {
										flag = "S";
										adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
												strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
												strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
										//LIMPIO VARIABLES
										strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
										strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
										strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
									}
								}
							}
							if (flag=="S" || cont==0) {
								for (Tabla tipoMov : listaDescripcionTipoMovimiento) {
									if(tipoMov.getIntIdDetalle().compareTo(movCtaCto.getIntParaTipoMovimiento())==0){
										//SI TIPOMOVIMIENTO = PLANILLA
										if (tipoMov.getIntIdDetalle().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_POR_PLANILLA)==0) {
											efectMov.getId().setIntEmpresacuentaPk(Integer.parseInt((movCtaCto.getStrNumeroDocumento().substring(0,1))));
											efectMov.getId().setIntItemefectuado(Integer.parseInt((movCtaCto.getStrNumeroDocumento().substring(2,movCtaCto.getStrNumeroDocumento().length()))));
											log.info(efectMov.getId().getIntItemefectuado());
											efectMov = planillaFacade.getEfectuadoPorPk(efectMov.getId());
											if (efectMov != null) {
												for (Tabla modalidad : listaDescripcionModalidadPlanilla) {
													if(modalidad.getIntIdDetalle().compareTo(efectMov.getIntModalidadCod())==0){
														strTipoAddSubtract = (modalidad.getStrDescripcion()).substring(0, 1);
														strTipoMov = ((tipoMov.getStrDescripcion()).substring(12, 15)+"-"+strTipoAddSubtract+"-"+movCtaCto.getIntPeriodoPlanilla()).toUpperCase();
													}
												}
											}						
										}
										//SI TIPOMOVIMIENTO = TRANSFERENCIA
										if(tipoMov.getIntIdDetalle().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_TRANSFERENCIA)==0){
											for (Tabla tipCargoAbono : listaDescripcionTipoCargoAbono) {
												if (tipCargoAbono.getIntIdDetalle().compareTo(movCtaCto.getIntParaTipoCargoAbono())==0) {
													strTipoAddSubtract = (tipCargoAbono.getStrDescripcion()).substring(0, 1);
													strTipoMov = ((tipoMov.getStrDescripcion()).substring(0, 4)+"-"+strTipoAddSubtract+"-"+movCtaCto.getStrNumeroDocumento()).toUpperCase();
												}
											}
										}							
										//SI TIPOMOVIMIENTO = INGRESO/EGRESO CAJA											
										if(tipoMov.getIntIdDetalle().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_INGRESO_POR_CAJA)==0 || tipoMov.getIntIdDetalle().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_EGRESO_POR_CAJA)==0){
											strTipoAddSubtract = (tipoMov.getStrDescripcion()).substring(0, 1);
											if (tipoMov.getIntIdDetalle().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_INGRESO_POR_CAJA)==0) {
												strTipoMov = ((tipoMov.getStrDescripcion()).substring(12, 16)+"-"+strTipoAddSubtract+"-"+movCtaCto.getStrNumeroDocumento()).toUpperCase();
											}else {
												strTipoMov = ((tipoMov.getStrDescripcion()).substring(11, 15)+"-"+strTipoAddSubtract+"-"+movCtaCto.getStrNumeroDocumento()).toUpperCase();
											}
										}
										//SI TIPOMOVIMIENTO = AUTOGENERADO									
										if(tipoMov.getIntIdDetalle().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_AUTOMATICO)==0){
											for (Tabla tipCargoAbono : listaDescripcionTipoCargoAbono) {
												if (tipCargoAbono.getIntIdDetalle().compareTo(movCtaCto.getIntParaTipoCargoAbono())==0) {
													strTipoAddSubtract = (tipCargoAbono.getStrDescripcion()).substring(0, 1);
													strTipoMov = ((tipoMov.getStrDescripcion()).substring(8, 12)+"-"+strTipoAddSubtract+"-"+movCtaCto.getStrNumeroDocumento()).toUpperCase();
												}
											}
										}	
									}
								}
							}					
							//PRESTAMOS
							if (movCtaCto.getIntItemExpediente()!=null) {
								//PRESTAMOS-CREDITO-ACTIVIDAD: LOS DIFERENCIO POR PARA_TIPOCREDITO_N(89) DE LA TABLA CMO_EXPEDIENTECREDITO 
								if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION)==0) {
									expediente.getId().setIntCuentaPk(movCtaCto.getIntCuenta());
									expediente.getId().setIntPersEmpresaPk(movCtaCto.getIntPersEmpresa());
									expediente.getId().setIntItemExpediente(movCtaCto.getIntItemExpediente());
									expediente.getId().setIntItemExpedienteDetalle(movCtaCto.getIntItemExpedienteDetalle());
									expedienteMov = conceptoFacade.getExpedientePorPK(expediente.getId());
									if (expedienteMov != null) {
										if (expedienteMov.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO)==0){
											strMontoMovPrestamos = (movCtaCto.getBdMontoMovimiento()).toString();											
										}
										if (expedienteMov.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)==0){
											strMontoMovCreditos = (movCtaCto.getBdMontoMovimiento()).toString();
										}
										if (expedienteMov.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)==0){
											strMontoMovActividad = (movCtaCto.getBdMontoMovimiento()).toString();
										}
										if (expedienteMov.getIntParaTipoCreditoCod().compareTo(Constante.PARAM_T_TIPO_CREDITO_MULTA)==0){
											strMontoMovMulta = (movCtaCto.getBdMontoMovimiento()).toString();
										}
									}
								}
								//INTERES - PARAM_T_TIPOCONCEPTOGENERAL_INTERES=2
								if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_INTERES)==0) {
									strMontoMovInteres = (movCtaCto.getBdMontoMovimiento()).toString();
								}
								//CTAXPAGAR & CTAXCOBRAR - PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR=13 / PARAM_T_TIPOCONCEPTOGENERAL_CTAXCOBRAR=12 
								if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR)==0) {
									strMontoMovCtaXPagar = (movCtaCto.getBdMontoMovimiento()).toString();
								}
								if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXCOBRAR)==0) {
									strMontoMovCtaXCobrar = (movCtaCto.getBdMontoMovimiento()).toString();
								}
							}else { //BENEFICIOS
								if (movCtaCto.getIntItemCuentaConcepto()!=null) {
									//APOTES - PARAM_T_TIPOCONCEPTOGENERAL_APORTACION=4
									if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_APORTACION)==0) {
										strMontoMovAportes = (movCtaCto.getBdMontoMovimiento()).toString();
									}								
									//MANT. DE CUENTA - PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO=5
									if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_MANTENIMIENTO)==0) {
										strMontoMovMntCuenta = (movCtaCto.getBdMontoMovimiento()).toString();
									}								
									//FDO. SEPELIO - PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO=6
									if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDOSEPELIO)==0) {
										strMontoMovFdoSepelio = (movCtaCto.getBdMontoMovimiento()).toString();
									}
									//FDO. RETIRO - PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO=7
									if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)==0) {
										strMontoMovFdoRetiro = (movCtaCto.getBdMontoMovimiento()).toString();
									}
									//CTAXPAGAR & CTAXCOBRAR - PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR=13 / PARAM_T_TIPOCONCEPTOGENERAL_CTAXCOBRAR=12 
									if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXPAGAR)==0) {
										strMontoMovCtaXPagar = (movCtaCto.getBdMontoMovimiento()).toString();
									}
									if (movCtaCto.getIntParaTipoConceptoGeneral().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_CTAXCOBRAR)==0) {
										strMontoMovCtaXCobrar = (movCtaCto.getBdMontoMovimiento()).toString();
									}
								}								
							}
							fecMovCompare = movCtaCto.getTsFechaMovimiento();
							tipMovCompare = movCtaCto.getIntParaTipoMovimiento();
							tipConcepGralCompare = movCtaCto.getIntParaTipoConceptoGeneral();
							nroDoc = movCtaCto.getStrNumeroDocumento();
							if (movCtaCto.getIntItemExpediente()!=null) {
								itemExpCredCompare = movCtaCto.getIntItemExpediente();
								itemDetExpCredCompare = movCtaCto.getIntItemExpedienteDetalle();
							}else {
								itemExpCredCompare = -1;
								itemDetExpCredCompare = -1;
							}
							if (movCtaCto.getIntItemCuentaConcepto()!=null) {
								itemCtaCto = movCtaCto.getIntItemCuentaConcepto();	
							}else {
								itemCtaCto = -1;
							}
							cont +=1 ;
						}
					}
					if (strTipoMov != "" || strMontoMovPrestamos != "" || strMontoMovCreditos != "" || strMontoMovActividad != "" || strMontoMovInteres != "" || 
							strMontoMovMulta != "" || strMontoMovAportes != "" || strMontoMovMntCuenta != "" || strMontoMovFdoSepelio != "" || strMontoMovFdoRetiro != "" || 
							strMontoMovCtaXPagar != "" || strMontoMovCtaXCobrar != "" ) {
						adicionarDatosGrillaDetalle(lstFecDet.getStrFecha(),tipMovCompare,strTipoAddSubtract,strTipoMov,strMontoMovPrestamos,strMontoMovCreditos,strMontoMovActividad,
								strMontoMovInteres,strMontoMovMulta,strMontoMovAportes,strMontoMovMntCuenta,strMontoMovFdoSepelio,
								strMontoMovFdoRetiro,strMontoMovCtaXPagar,strMontoMovCtaXCobrar);
						//LIMPIO VARIABLES
						strMontoMovPrestamos = "";	strMontoMovCreditos = "";	strMontoMovInteres = "";	strMontoMovActividad = "";	strMontoMovMulta = "";
						strMontoMovAportes = "";	strMontoMovFdoSepelio = "";	strMontoMovFdoRetiro = "";	strMontoMovMntCuenta = "";	strMontoMovCtaXPagar = "";
						strMontoMovCtaXCobrar = "";	strTipoMov = "";	strTipoAddSubtract = "";
					}
				}
			}
			
			
			EstadoCuentaComp estCtaCompSum = new EstadoCuentaComp(); 
			estCtaCompSum.setStrFecha("Saldos");
			fmt = new Formatter();	;
			estCtaCompSum.setStrDescripcion(fmt.format("01/%1$02d/%2$04d",intMesBusqueda,intAnioBusqueda).toString());
			if (bdMontoMovPrestamos.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovPrestamos("");
			}else {
				estCtaCompSum.setStrMontoMovPrestamos(convertirMontos(bdMontoMovPrestamos));
			}
			//
			if (bdMontoMovCreditos.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovCreditos("");
			}else {
				estCtaCompSum.setStrMontoMovCreditos(convertirMontos(bdMontoMovCreditos));
			}
			//
			if (bdMontoMovActividad.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovActividad("");
			}else {
				estCtaCompSum.setStrMontoMovActividad(convertirMontos(bdMontoMovActividad));
			}
			//
			if (bdMontoMovInteres.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovInteres("");
			}else {
				estCtaCompSum.setStrMontoMovInteres(convertirMontos(bdMontoMovInteres));
			}
			//
			if (bdMontoMovMulta.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovMulta("");
			}else {
				estCtaCompSum.setStrMontoMovMulta(convertirMontos(bdMontoMovMulta));
			}
			//
			if (bdMontoMovAportes.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovAportes("");
			}else {
				estCtaCompSum.setStrMontoMovAportes(convertirMontos(bdMontoMovAportes));
			}
			//
			if (bdMontoMovMntCuenta.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovMntCuenta("");
			}else {
				estCtaCompSum.setStrMontoMovMntCuenta(convertirMontos(bdMontoMovMntCuenta));
			}
			//
			if (bdMontoMovFdoSepelio.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovFdoSepelio("");
			}else {
				estCtaCompSum.setStrMontoMovFdoSepelio(convertirMontos(bdMontoMovFdoSepelio));
			}
			//
			if (bdMontoMovFdoRetiro.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovFdoRetiro("");
			}else {
				estCtaCompSum.setStrMontoMovFdoRetiro(convertirMontos(bdMontoMovFdoRetiro));
			}
			//
			if (bdMontoMovCtaXPagar.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovCtaXPagar("");
			}else {
				estCtaCompSum.setStrMontoMovCtaXPagar(convertirMontos(bdMontoMovCtaXPagar));
			}
			//
			if (bdMontoMovCtaXCobrar.compareTo(BigDecimal.ZERO)==0) {
				estCtaCompSum.setStrMontoMovCtaXCobrar("");
			}else {
				estCtaCompSum.setStrMontoMovCtaXCobrar(convertirMontos(bdMontoMovCtaXCobrar));
			}			
			listaSumatoriaEstadoCuentaComp.add(estCtaCompSum);
			
			/** FILA DE SALDOS ACTUALES **/
			for (EstadoCuentaComp listEstCtaComp : listaEstadoCuentaComp) {	
				if ((listEstCtaComp.getStrTipoAddSubtract().compareTo("C")==0 || listEstCtaComp.getStrTipoAddSubtract().compareTo("E")==0) && listEstCtaComp.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_POR_PLANILLA)!=0) {
					//PRESTAMOS
					if (listEstCtaComp.getStrMontoMovPrestamos() != "") {
						bdSumatoriaMontoMovPrestamos = bdSumatoriaMontoMovPrestamos.add(listEstCtaComp.getBdMontoMovPrestamos());
					}
					if (listEstCtaComp.getStrMontoMovCreditos() != "") {
						bdSumatoriaMontoMovCreditos = bdSumatoriaMontoMovCreditos.add(listEstCtaComp.getBdMontoMovCreditos());					
					}
					if (listEstCtaComp.getStrMontoMovActividad() != "") {
						bdSumatoriaMontoMovActividad = bdSumatoriaMontoMovActividad.add(listEstCtaComp.getBdMontoMovActividad());
					}
					if (listEstCtaComp.getStrMontoMovMulta() != "") {
						bdSumatoriaMontoMovMulta = bdSumatoriaMontoMovMulta.add(listEstCtaComp.getBdMontoMovMulta());
					}
					//BENEFICIOS
					if (listEstCtaComp.getStrMontoMovAportes() != "") {
						bdSumatoriaMontoMovAportes = bdSumatoriaMontoMovAportes.subtract(listEstCtaComp.getBdMontoMovAportes());
					}
					if (listEstCtaComp.getStrMontoMovFdoRetiro() != "") {
						bdSumatoriaMontoMovFdoRetiro = bdSumatoriaMontoMovFdoRetiro.subtract(listEstCtaComp.getBdMontoMovFdoRetiro());
					}
				}
				if (listEstCtaComp.getStrTipoAddSubtract().compareTo("A")==0 || listEstCtaComp.getStrTipoAddSubtract().compareTo("I")==0 || listEstCtaComp.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_POR_PLANILLA)==0) {
					//PRESTAMOS
					if (listEstCtaComp.getStrMontoMovPrestamos() != "") {
						bdSumatoriaMontoMovPrestamos = bdSumatoriaMontoMovPrestamos.subtract(listEstCtaComp.getBdMontoMovPrestamos());
						log.info(bdSumatoriaMontoMovPrestamos);
					}
					if (listEstCtaComp.getStrMontoMovCreditos() != "") {
						bdSumatoriaMontoMovCreditos = bdSumatoriaMontoMovCreditos.subtract(listEstCtaComp.getBdMontoMovCreditos());					
					}
					if (listEstCtaComp.getStrMontoMovActividad() != "") {
						bdSumatoriaMontoMovActividad = bdSumatoriaMontoMovActividad.subtract(listEstCtaComp.getBdMontoMovActividad());
					}
					if (listEstCtaComp.getStrMontoMovMulta() != "") {
						bdSumatoriaMontoMovMulta = bdSumatoriaMontoMovMulta.subtract(listEstCtaComp.getBdMontoMovMulta());
					}
					//BENEFICIOS
					if (listEstCtaComp.getStrMontoMovAportes() != "") {
						bdSumatoriaMontoMovAportes = bdSumatoriaMontoMovAportes.add(listEstCtaComp.getBdMontoMovAportes());
					}
					if (listEstCtaComp.getStrMontoMovFdoRetiro() != "") {
						bdSumatoriaMontoMovFdoRetiro = bdSumatoriaMontoMovFdoRetiro.add(listEstCtaComp.getBdMontoMovFdoRetiro());
					}
				}
			}
			//PRESTAMOS
			strSumatoriaMontoMovPrestamos = convertirMontos(bdSumatoriaMontoMovPrestamos.add(bdMontoMovPrestamos));
			strSumatoriaMontoMovCreditos = convertirMontos(bdSumatoriaMontoMovCreditos.add(bdMontoMovCreditos));
			strSumatoriaMontoMovActividad = convertirMontos(bdSumatoriaMontoMovActividad.add(bdMontoMovActividad));
			strSumatoriaMontoMovMulta = convertirMontos(bdSumatoriaMontoMovMulta.add(bdMontoMovMulta));
			//BENEFICIOS
			strSumatoriaMontoMovAportes = convertirMontos(bdSumatoriaMontoMovAportes.add(bdMontoMovAportes));
			strSumatoriaMontoMovFdoRetiro = convertirMontos(bdSumatoriaMontoMovFdoRetiro.add(bdMontoMovFdoRetiro));
			
			Collections.sort(listaEstadoCuentaComp, new Comparator<EstadoCuentaComp>() {
	            public int compare(EstadoCuentaComp e1, EstadoCuentaComp e2) {
	            	Date d1 = new Date(),d2 = new Date();
	            	try {
						d1=formatoDeFecha.parse(e1.getStrFecha());
						d2=formatoDeFecha.parse(e2.getStrFecha());
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return d1.compareTo(d2);
	            }
	        }); 				
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void adicionarDatosGrillaDetalle(String fecComp,Integer tipConcepComp,String strTipoAddSubtract,String strTipoMov,String strMontoMovPrestamos,String strMontoMovCreditos,String strMontoMovActividad,
											String strMontoMovInteres,String strMontoMovMulta,String strMontoMovAportes,String strMontoMovMntCuenta,String strMontoMovFdoSepelio,
											String strMontoMovFdoRetiro,String strMontoMovCtaXPagar,String strMontoMovCtaXCobrar){
		
		EstadoCuentaComp estCtaComp = new EstadoCuentaComp();
		estCtaComp.setIntParaTipoMovimiento(tipConcepComp);
		estCtaComp.setStrTipoAddSubtract(strTipoAddSubtract);
		estCtaComp.setStrFecha(fecComp);
		estCtaComp.setStrDescripcion(strTipoMov);
		
		
		estCtaComp.setBdMontoMovPrestamos(strMontoMovPrestamos==""?BigDecimal.ZERO:new BigDecimal(strMontoMovPrestamos));
		estCtaComp.setBdMontoMovCreditos(strMontoMovCreditos==""?BigDecimal.ZERO:new BigDecimal(strMontoMovCreditos));
		estCtaComp.setBdMontoMovActividad(strMontoMovActividad==""?BigDecimal.ZERO:new BigDecimal(strMontoMovActividad));
		estCtaComp.setBdMontoMovInteres(strMontoMovInteres==""?BigDecimal.ZERO:new BigDecimal(strMontoMovInteres));
		estCtaComp.setBdMontoMovMulta(strMontoMovMulta==""?BigDecimal.ZERO:new BigDecimal(strMontoMovMulta));
		estCtaComp.setBdMontoMovAportes(strMontoMovAportes==""?BigDecimal.ZERO:new BigDecimal(strMontoMovAportes));
		estCtaComp.setBdMontoMovMntCuenta(strMontoMovMntCuenta==""?BigDecimal.ZERO:new BigDecimal(strMontoMovMntCuenta));
		estCtaComp.setBdMontoMovFdoSepelio(strMontoMovFdoSepelio==""?BigDecimal.ZERO:new BigDecimal(strMontoMovFdoSepelio));
		estCtaComp.setBdMontoMovFdoRetiro(strMontoMovFdoRetiro==""?BigDecimal.ZERO:new BigDecimal(strMontoMovFdoRetiro));
		estCtaComp.setBdMontoMovCtaXPagar(strMontoMovCtaXPagar==""?BigDecimal.ZERO:new BigDecimal(strMontoMovCtaXPagar));
		estCtaComp.setBdMontoMovCtaXCobrar(strMontoMovCtaXCobrar==""?BigDecimal.ZERO:new BigDecimal(strMontoMovCtaXCobrar));
		
		
		estCtaComp.setStrMontoMovPrestamos(strMontoMovPrestamos==""?"":convertirMontos(new BigDecimal(strMontoMovPrestamos)));
		estCtaComp.setStrMontoMovCreditos(strMontoMovCreditos==""?"":convertirMontos(new BigDecimal(strMontoMovCreditos)));
		estCtaComp.setStrMontoMovActividad(strMontoMovActividad==""?"":convertirMontos(new BigDecimal(strMontoMovActividad)));
		estCtaComp.setStrMontoMovInteres(strMontoMovInteres==""?"":convertirMontos(new BigDecimal(strMontoMovInteres)));
		estCtaComp.setStrMontoMovMulta(strMontoMovMulta==""?"":convertirMontos(new BigDecimal(strMontoMovMulta)));
		estCtaComp.setStrMontoMovAportes(strMontoMovAportes==""?"":convertirMontos(new BigDecimal(strMontoMovAportes)));
		estCtaComp.setStrMontoMovMntCuenta(strMontoMovMntCuenta==""?"":convertirMontos(new BigDecimal(strMontoMovMntCuenta)));
		estCtaComp.setStrMontoMovFdoSepelio(strMontoMovFdoSepelio==""?"":convertirMontos(new BigDecimal(strMontoMovFdoSepelio)));
		estCtaComp.setStrMontoMovFdoRetiro(strMontoMovFdoRetiro==""?"":convertirMontos(new BigDecimal(strMontoMovFdoRetiro)));
		estCtaComp.setStrMontoMovCtaXPagar(strMontoMovCtaXPagar==""?"":convertirMontos(new BigDecimal(strMontoMovCtaXPagar)));
		estCtaComp.setStrMontoMovCtaXCobrar(strMontoMovCtaXCobrar==""?"":convertirMontos(new BigDecimal(strMontoMovCtaXCobrar)));
		
		Integer intAnioFecMov = Integer.parseInt(fecComp.substring(6, 10));
		Integer intMesFecMov = Integer.parseInt(fecComp.substring(3, 5));
		if (intMesFecMov.compareTo(intMesBusqueda)>=0 && intAnioFecMov.compareTo(intAnioBusqueda)>=0) {
			BigDecimal bdSumatoriaFilas = BigDecimal.ZERO;
			BigDecimal b1 = BigDecimal.ZERO;
			BigDecimal b2 = BigDecimal.ZERO;
			BigDecimal b3 = BigDecimal.ZERO;
			BigDecimal b4 = BigDecimal.ZERO;
			BigDecimal b5 = BigDecimal.ZERO;
			BigDecimal b6 = BigDecimal.ZERO;
			BigDecimal b7 = BigDecimal.ZERO;
			BigDecimal b8 = BigDecimal.ZERO;
			BigDecimal b9 = BigDecimal.ZERO;
			BigDecimal b10 = BigDecimal.ZERO;
			BigDecimal b11 = BigDecimal.ZERO;

			if (strMontoMovPrestamos.compareTo("")!=0) {b1 = new BigDecimal(strMontoMovPrestamos);}
			if (strMontoMovCreditos.compareTo("")!=0) {b2 = new BigDecimal(strMontoMovCreditos);}
			if (strMontoMovActividad.compareTo("")!=0) {b3 = new BigDecimal(strMontoMovActividad);}
			if (strMontoMovInteres.compareTo("")!=0) {b4 = new BigDecimal(strMontoMovInteres);}
			if (strMontoMovMulta.compareTo("")!=0) {b5 = new BigDecimal(strMontoMovMulta);}
			if (strMontoMovAportes.compareTo("")!=0) {b6 = new BigDecimal(strMontoMovAportes);}
			if (strMontoMovMntCuenta.compareTo("")!=0) {b7 = new BigDecimal(strMontoMovMntCuenta);}
			if (strMontoMovFdoSepelio.compareTo("")!=0) {b8 = new BigDecimal(strMontoMovFdoSepelio);}
			if (strMontoMovFdoRetiro.compareTo("")!=0) {b9 = new BigDecimal(strMontoMovFdoRetiro);}
			if (strMontoMovCtaXPagar.compareTo("")!=0) {b10 = new BigDecimal(strMontoMovCtaXPagar);}
			if (strMontoMovCtaXCobrar.compareTo("")!=0) {b11 = new BigDecimal(strMontoMovCtaXCobrar);}
			
			bdSumatoriaFilas = bdSumatoriaFilas.add(b1).add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).add(b8).add(b9).add(b10).add(b11);
			log.info("Sumatoria de filas: "+bdSumatoriaFilas);
			estCtaComp.setStrSumatoriaFilas(convertirMontos(bdSumatoriaFilas));
			listaEstadoCuentaComp.add(estCtaComp);
		}else{
			if (strTipoAddSubtract.compareTo("C")==0 || strTipoAddSubtract.compareTo("E")==0 && tipConcepComp.compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_POR_PLANILLA)!=0) {
				//PRESTAMOS
				if (strMontoMovPrestamos != "") {
					bdMontoMovPrestamos = bdMontoMovPrestamos.add(new BigDecimal(strMontoMovPrestamos));
				}
				if (strMontoMovCreditos != "") {
					bdMontoMovCreditos = bdMontoMovCreditos.add(new BigDecimal(strMontoMovCreditos));
				}
				if (strMontoMovActividad != "") {
					bdMontoMovActividad = bdMontoMovActividad.add(new BigDecimal(strMontoMovActividad));
				}
				if (strMontoMovMulta != "") {
					bdMontoMovMulta = bdMontoMovMulta.add(new BigDecimal(strMontoMovMulta));
				}
				//BENEFICIOS
				if (strMontoMovAportes != "") {		
					bdMontoMovAportes = bdMontoMovAportes.subtract(new BigDecimal(strMontoMovAportes));
				}
				if (strMontoMovFdoRetiro != "") {		
					bdMontoMovFdoRetiro = bdMontoMovFdoRetiro.subtract(new BigDecimal(strMontoMovFdoRetiro));
				}		
			}
			if (strTipoAddSubtract.compareTo("A")==0 || strTipoAddSubtract.compareTo("I")==0 || tipConcepComp.compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_POR_PLANILLA)==0) {
				//PRESTAMOS
				if (strMontoMovPrestamos != "") {
					bdMontoMovPrestamos = bdMontoMovPrestamos.subtract(new BigDecimal(strMontoMovPrestamos));
				}
				if (strMontoMovCreditos != "") {
					bdMontoMovCreditos = bdMontoMovCreditos.subtract(new BigDecimal(strMontoMovCreditos));
				}
				if (strMontoMovActividad != "") {
					bdMontoMovActividad = bdMontoMovActividad.subtract(new BigDecimal(strMontoMovActividad));
				}
				if (strMontoMovMulta != "") {
					bdMontoMovMulta = bdMontoMovMulta.subtract(new BigDecimal(strMontoMovMulta));
				}
				//BENEFICIOS
				if (strMontoMovAportes != "") {
					bdMontoMovAportes = bdMontoMovAportes.add(new BigDecimal(strMontoMovAportes));
				}
				if (strMontoMovFdoRetiro != "") {
					bdMontoMovFdoRetiro = bdMontoMovFdoRetiro.add(new BigDecimal(strMontoMovFdoRetiro));
				}
			}
			
			
			if (strMontoMovInteres != "") {
				bdMontoMovInteres = bdMontoMovInteres.add(new BigDecimal(strMontoMovInteres));
			}
			if (strMontoMovMntCuenta != "") {
				bdMontoMovMntCuenta = bdMontoMovMntCuenta.add(new BigDecimal(strMontoMovMntCuenta));
			}
			if (strMontoMovFdoSepelio != "") {
				bdMontoMovFdoSepelio = bdMontoMovFdoSepelio.add(new BigDecimal(strMontoMovFdoSepelio));
			}
		}
	}
	//EVENTO BOTON "TERCEROS"
	public void obtenerDatosTabDsctoTerceros() throws BusinessException{
		log.info("------------------------ debuging.obtenerDatosTabDsctoTerceros ------------------------");
		intShowPanel = 0;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.TRUE;
			blnShowPanelTabTercerosHaberes = Boolean.FALSE;
			blnShowPanelTabTercerosIncentivos = Boolean.FALSE;
			blnShowPanelTabTercerosCas = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 
		
		blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabPrestamosResumePrestamos = Boolean.FALSE;
		blnShowPanelTabPrestamosGarantizados = Boolean.FALSE;
		if (intMesBusqueda!=-1) {
			getListaDsctoTercerosPorPeriodo(intAnioBusqueda, intMesBusqueda, Constante.PARAM_T_MODALIDADPLANILLA_HABERES, socioComp);
			getListaDsctoTercerosPorPeriodo(intAnioBusqueda, intMesBusqueda, Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS, socioComp);
			getListaDsctoTercerosPorPeriodo(intAnioBusqueda, intMesBusqueda, Constante.PARAM_T_MODALIDADPLANILLA_CAS, socioComp);
		}
	}
	//LISTA DESCUENTOS TERCEROS POR PERIODO Y CPTO DESCUENTO
	public void getListaDsctoTercerosPorPeriodo(Integer intAnioBusqueda, Integer intMesBusqueda, Integer modPlanilla, SocioComp socioComp) throws BusinessException{
		log.info("------------------------ debuging.getListaDsctoTercerosPorPeriodo ------------------------");
		String strMesBusqueda = "";
//		List<Descuento> listaColumnas = null;
		List<Descuento> listaNvoColumnas = new ArrayList<Descuento>();
//		List<Descuento> listaFilas = null;
		List<Descuento> listaNvoFilas = null;
//		List<Descuento> listaNvoFilas = new ArrayList<Descuento>();
		List<Descuento> listaMontoTotalDsctoTerceros = null;
		
		try {			
			strMesBusqueda = intMesBusqueda<10?"0"+intMesBusqueda:intMesBusqueda.toString();
			log.info("Mes con formato: "+strMesBusqueda);
			log.info("--- DNI: "+socioComp.getPersona().getDocumento().getStrNumeroIdentidad());
			listaNvoFilas = estadoCtaFacade.getListaFilasPorPeriodoModalidadYDni(Integer.parseInt(intAnioBusqueda+strMesBusqueda), modPlanilla, socioComp.getPersona().getDocumento().getStrNumeroIdentidad());
//			listaFilas = estadoCtaFacade.getListaFilasPorPeriodoModalidadYDni(Integer.parseInt(intAnioBusqueda+strMesBusqueda), modPlanilla, socioComp.getPersona().getDocumento().getStrNumeroIdentidad());
//			listaColumnas = estadoCtaFacade.getListaColumnasPorPeriodoModalidadYDni(Integer.parseInt(intAnioBusqueda+strMesBusqueda), modPlanilla, socioComp.getPersona().getDocumento().getStrNumeroIdentidad());	
			listaNvoColumnas = estadoCtaFacade.getListaColumnasPorPeriodoModalidadYDni(Integer.parseInt(intAnioBusqueda+strMesBusqueda), modPlanilla, socioComp.getPersona().getDocumento().getStrNumeroIdentidad());
			//LIMPIANDO LISTA PARA MOSTRAR COLUMNAS DSCTOTERCEROS
//			if (listaColumnas!=null && !listaColumnas.isEmpty()) {
//				Integer cont = 0;
//				String strDsctoTer = "";
//				String strNomCpto = "";
//				for (Descuento lstColumns : listaColumnas) {
////					if (lstColumns.getIntMonto().compareTo(Integer.parseInt(intAnioBusqueda+strMesBusqueda))>=0){
//						strDsctoTer = lstColumns.getStrDsteCpto();
//						strNomCpto = lstColumns.getStrNomCpto()==null?"":lstColumns.getStrNomCpto();
//						if (cont!=0) {
//							Integer flag=0;
//							for (Descuento lstNvoColumns : listaNvoColumnas) {
//								lstNvoColumns.setStrNomCpto(lstNvoColumns.getStrNomCpto()==null?"":lstNvoColumns.getStrNomCpto());
//								if (lstNvoColumns.getStrDsteCpto().compareTo(strDsctoTer)==0 && lstNvoColumns.getStrNomCpto().compareTo(strNomCpto)==0) {
//									flag=1;
//									break;
//								}								
//							}
//							if (flag==0) listaNvoColumnas.add(lstColumns);
//						}
//						if (cont==0) listaNvoColumnas.add(lstColumns);
//						cont++;
////					}
//				}				
//			}
			//LIMPIANDO LISTA PARA MOSTRAR FILAS (PERIODOS) DSCTOTERCEROS
//			if (listaFilas!=null && !listaFilas.isEmpty()) {
//				listaNvoFilas = listaFilas;
//				for (Descuento lstFilas : listaFilas) {
//					if (lstFilas.getIntMonto().compareTo(Integer.parseInt(intAnioBusqueda+strMesBusqueda))>=0){
//						listaNvoFilas.add(lstFilas);
//					}
//				}
//			}
			if ((listaNvoColumnas!=null && !listaNvoColumnas.isEmpty()) && (listaNvoFilas!=null && !listaNvoFilas.isEmpty())) {
				for (Descuento lstNvoColumns : listaNvoColumnas) {
					for (Descuento lstNvoFilas : listaNvoFilas) {
						if(lstNvoFilas.getStrMontoDscto()==null){
							lstNvoFilas.setStrMontoDscto(new String[1]);
						}else{
							String values[] = new String[lstNvoFilas.getStrMontoDscto().length+1];
							for(int i=0; i<lstNvoFilas.getStrMontoDscto().length; i++){
								values[i] = lstNvoFilas.getStrMontoDscto()[i];
							}
							lstNvoFilas.setStrMontoDscto(values);
						}
						listaMontoTotalDsctoTerceros = estadoCtaFacade.getMontoTotalPorNomCptoYPeriodo(lstNvoColumns.getStrDsteCpto(), lstNvoColumns.getStrNomCpto(),lstNvoFilas.getId().getIntPeriodo(), lstNvoFilas.getId().getIntMes(), modPlanilla, socioComp.getPersona().getDocumento().getStrNumeroIdentidad());
						Integer intMontoDscto = 0;
						if (listaMontoTotalDsctoTerceros!=null && !listaMontoTotalDsctoTerceros.isEmpty()) {
							for (Descuento lstMontoDscto : listaMontoTotalDsctoTerceros) {
								intMontoDscto = lstMontoDscto.getIntMonto()==null?0:lstMontoDscto.getIntMonto();
							}
						}
						Integer intPteEntera = intMontoDscto/100;
						Integer intPteDecimal = intMontoDscto%100;
						//DANDO FORMATO AL MONTO RECUPERADO
						String strParteDecimal = "";
						if (intPteDecimal.toString().length()==1) {
							strParteDecimal = intPteDecimal+"0";
						}else{
							strParteDecimal = intPteDecimal.toString();
						}
						String strMontoDsctoTros = intPteEntera+"."+strParteDecimal;
						lstNvoFilas.getStrMontoDscto()[lstNvoFilas.getStrMontoDscto().length-1]=strMontoDsctoTros;
					}
				}
			}	
			for (Descuento fila : listaNvoFilas) {
				fila.setStrDstePeriodo(fmtPeriodo(fila.getIntMonto()));
				BigDecimal bdSumTotal = BigDecimal.ZERO;
				for(int i=0; i<fila.getStrMontoDscto().length; i++){
					bdSumTotal = bdSumTotal.add(new BigDecimal(fila.getStrMontoDscto()[i]));
				}
				fila.setBdSumMontoDscto(bdSumTotal);
			}			
			if (modPlanilla.equals(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)) {
				setListaNvoColumnasHaberes(listaNvoColumnas);
				setListaMontoDsctoTrosHaberes(listaNvoFilas);
				if (listaMontoDsctoTrosHaberes!=null && !listaMontoDsctoTrosHaberes.isEmpty()) {
					blnShowPanelTabTercerosHaberes = Boolean.TRUE;
				}			
			}
			if (modPlanilla.equals(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)) {
				setListaNvoColumnasIncentivos(listaNvoColumnas);
				setListaMontoDsctoTrosIncentivos(listaNvoFilas);
				if (listaMontoDsctoTrosIncentivos!=null && !listaMontoDsctoTrosIncentivos.isEmpty()) {
					blnShowPanelTabTercerosIncentivos = Boolean.TRUE;
				}
			}
			if (modPlanilla.equals(Constante.PARAM_T_MODALIDADPLANILLA_CAS)) {
				setListaNvoColumnasCas(listaNvoColumnas);
				setListaMontoDsctoTrosCas(listaNvoFilas);
				if (listaMontoDsctoTrosCas!=null && !listaMontoDsctoTrosCas.isEmpty()) {
					blnShowPanelTabTercerosCas = Boolean.TRUE;
				}
			}
			setListaMontoDsctoTerceros(listaNvoFilas);	
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	//EVENTO DEL BOTON "PRESTAMOS"
	public void obtenerDatosTabPrestamos(){
		log.info("------------------------ debuging.obtenerDatosTabPrestamos ------------------------");
		intShowPanel = 0;
		blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabPrestamosResumePrestamos = Boolean.FALSE;
		blnShowPanelTabPrestamosGarantizados = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.TRUE;
	}
	//EVENTO DEL BOTON "CONSULTAR" EN TAB PRESTAMOS - LISTA PRESTAMOS SOCIO POR FILTROS
	public void listarPrestamosSocio() {		
		log.info("------------------------ debuging.listarPrestamosSocio ------------------------");
		List<ExpedienteComp> listaExpedienteCompGirados = null;		
		List<ExpedienteComp> listaExpedienteCompAprobados = null;
		
		List<ExpedienteCreditoComp> listaExpedienteCreditoCompRechazados = null;
		listaExpedienteComp = new ArrayList<ExpedienteComp>();
		blnShowPanelTabPrestamosResumePrestamos = Boolean.TRUE;
		
		try {
			log.info("----- cuenta: "+cuentaComp.getCuenta().getId().getIntCuenta());
			BigDecimal bdSumatoriaSaldo = BigDecimal.ZERO;
			//ESTCTA_VIGENTE
			if (intEstadoEstCtaBusqueda.equals(Constante.PARAM_T_ESTADOCONSULTAESTCTA_VIGENTE)) {
				if (intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO) || intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)) {
					listaExpedienteCompGirados = estadoCtaFacade.getListaPrestamosPorCuenta(cuentaComp, Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
					if (listaExpedienteCompGirados!=null && !listaExpedienteCompGirados.isEmpty()) {
						for (ExpedienteComp lstExpGirados : listaExpedienteCompGirados) {
							if (lstExpGirados.getExpediente().getBdSaldoCredito().compareTo(BigDecimal.ZERO)>0 && lstExpGirados.getExpediente().getIntParaTipoCreditoCod().equals(intTipoCreditoBusqueda)) {
								lstExpGirados.setStrEstadoTipoCredito("V");
								bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstExpGirados.getExpediente().getBdSaldoCredito());
								listaExpedienteComp.add(lstExpGirados);
							}
						}
					}
				}				
				if (intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD) || intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_MULTA)
						|| intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)) {
					listaExpedienteCompAprobados = estadoCtaFacade.getListaPrestamosPorCuenta(cuentaComp, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
					if (listaExpedienteCompAprobados!=null && !listaExpedienteCompAprobados.isEmpty()) {
						for (ExpedienteComp lstExpAprob : listaExpedienteCompAprobados) {
							if (lstExpAprob.getExpediente().getBdSaldoCredito().compareTo(BigDecimal.ZERO)>0 && lstExpAprob.getExpediente().getIntParaTipoCreditoCod().equals(intTipoCreditoBusqueda)) {
								lstExpAprob.setStrEstadoTipoCredito("V");
								bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstExpAprob.getExpediente().getBdSaldoCredito());
								listaExpedienteComp.add(lstExpAprob);
							}
						}
					}
				}				
				bdSumatoriaSaldoPrestamo = bdSumatoriaSaldo;
			}
			//ESTCTA_CANCELADO
			if (intEstadoEstCtaBusqueda.equals(Constante.PARAM_T_ESTADOCONSULTAESTCTA_CANCELADO)) {
				if (intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO) || intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)) {
					listaExpedienteCompGirados = estadoCtaFacade.getListaPrestamosPorCuenta(cuentaComp, Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
					if (listaExpedienteCompGirados!=null && !listaExpedienteCompGirados.isEmpty()) {
						for (ExpedienteComp lstExpGirados : listaExpedienteCompGirados) {
							if (lstExpGirados.getExpediente().getBdSaldoCredito().compareTo(BigDecimal.ZERO)==0 && lstExpGirados.getExpediente().getIntParaTipoCreditoCod().equals(intTipoCreditoBusqueda)) {
								lstExpGirados.setStrEstadoTipoCredito("C");
								bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstExpGirados.getExpediente().getBdSaldoCredito());
								listaExpedienteComp.add(lstExpGirados);
							}
						}
					}
				}				
				if (intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD) || intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_MULTA)
						|| intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)) {
					listaExpedienteCompAprobados = estadoCtaFacade.getListaPrestamosPorCuenta(cuentaComp, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
					if (listaExpedienteCompAprobados!=null && !listaExpedienteCompAprobados.isEmpty()) {
						for (ExpedienteComp lstExpAprob : listaExpedienteCompAprobados) {
							if (lstExpAprob.getExpediente().getBdSaldoCredito().compareTo(BigDecimal.ZERO)==0 && lstExpAprob.getExpediente().getIntParaTipoCreditoCod().equals(intTipoCreditoBusqueda)) {
								lstExpAprob.setStrEstadoTipoCredito("C");
								bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstExpAprob.getExpediente().getBdSaldoCredito());
								listaExpedienteComp.add(lstExpAprob);
							}
						}
					}
				}				
				bdSumatoriaSaldoPrestamo = bdSumatoriaSaldo;
			}
			//ESTCTA_RECHAZADO
			if (intEstadoEstCtaBusqueda.equals(Constante.PARAM_T_ESTADOCONSULTAESTCTA_RECHAZADO)) {
				listaExpedienteCreditoCompRechazados = estadoCtaFacade.getListaPrestamosRechazadosPorCuenta(cuentaComp,intTipoCreditoBusqueda);
				ExpedienteComp exp = null;
				if (listaExpedienteCreditoCompRechazados!=null && !listaExpedienteCreditoCompRechazados.isEmpty()) {
					for (ExpedienteCreditoComp lstExpCredRechazados : listaExpedienteCreditoCompRechazados) {
						exp = new ExpedienteComp();
						//PK EXPEDIENTECREDITO
						exp.getExpediente().getId().setIntPersEmpresaPk(lstExpCredRechazados.getExpedienteCredito().getId().getIntPersEmpresaPk());
						exp.getExpediente().getId().setIntCuentaPk(lstExpCredRechazados.getExpedienteCredito().getId().getIntCuentaPk());
						exp.getExpediente().getId().setIntItemExpediente(lstExpCredRechazados.getExpedienteCredito().getId().getIntItemExpediente());
						exp.getExpediente().getId().setIntItemExpedienteDetalle(lstExpCredRechazados.getExpedienteCredito().getId().getIntItemDetExpediente());
						//FECHA SOLICITUD
						exp.setStrFechaSolicitud(lstExpCredRechazados.getStrFechaSolicitud());
						//DESCRIPCION DEL PRESTAMO
						exp.setStrDescripcionExpediente(lstExpCredRechazados.getStrDescripcionExpedienteCredito());
						//SOLICITUD
						exp.setStrNroSolicitud(lstExpCredRechazados.getStrNroSolicitud());
						//PORCENTAJE INTERES
						exp.getExpediente().setBdPorcentajeInteres(lstExpCredRechazados.getExpedienteCredito().getBdPorcentajeInteres());
						//CT/CP/CA
						exp.setStrCuotas(lstExpCredRechazados.getExpedienteCredito().getIntNumeroCuota()+"/0/0");
						//CUOTA MENSUAL
						exp.setBdCuotaMensual(lstExpCredRechazados.getBdCuotaMensual());
						//MONTO TOTAL
						exp.getExpediente().setBdMontoTotal(lstExpCredRechazados.getExpedienteCredito().getBdMontoTotal());
						//ESTADO TIPO CREDITO
						exp.setStrEstadoTipoCredito("R");
						listaExpedienteComp.add(exp);
					}
				}
				bdSumatoriaSaldoPrestamo = BigDecimal.ZERO;
			}
			//ESTCTA_TODOS
			if (intEstadoEstCtaBusqueda.equals(Constante.PARAM_T_ESTADOCONSULTAESTCTA_TODOS)) {
				//VIGENTES Y CANCELADOS TIPO_CREDITO PRESTAMO - ORDENCREDITO
				if (intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_PRESTAMO) || intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_ORDENCREDITO)) {
					listaExpedienteCompGirados = estadoCtaFacade.getListaPrestamosPorCuenta(cuentaComp, Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
					if (listaExpedienteCompGirados!=null && !listaExpedienteCompGirados.isEmpty()) {
						for (ExpedienteComp lstExpGirados : listaExpedienteCompGirados) {
							if (lstExpGirados.getExpediente().getBdSaldoCredito().compareTo(BigDecimal.ZERO)>0 && lstExpGirados.getExpediente().getIntParaTipoCreditoCod().equals(intTipoCreditoBusqueda)) {
								lstExpGirados.setStrEstadoTipoCredito("V");
								bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstExpGirados.getExpediente().getBdSaldoCredito());
								listaExpedienteComp.add(lstExpGirados);
							}
							if (lstExpGirados.getExpediente().getBdSaldoCredito().compareTo(BigDecimal.ZERO)==0 && lstExpGirados.getExpediente().getIntParaTipoCreditoCod().equals(intTipoCreditoBusqueda)) {
								lstExpGirados.setStrEstadoTipoCredito("C");
								bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstExpGirados.getExpediente().getBdSaldoCredito());
								listaExpedienteComp.add(lstExpGirados);
							}
						}
					}
				}
				//VIGENTES Y CANCELADOS TIPO_CREDITO ACTIVIDAD - MULTA - REFINANCIADO
				if (intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD) || intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_MULTA)
						|| intTipoCreditoBusqueda.equals(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)) {
					listaExpedienteCompAprobados = estadoCtaFacade.getListaPrestamosPorCuenta(cuentaComp, Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO);
					if (listaExpedienteCompAprobados!=null && !listaExpedienteCompAprobados.isEmpty()) {
						for (ExpedienteComp lstExpAprob : listaExpedienteCompAprobados) {
							if (lstExpAprob.getExpediente().getBdSaldoCredito().compareTo(BigDecimal.ZERO)>0 && lstExpAprob.getExpediente().getIntParaTipoCreditoCod().equals(intTipoCreditoBusqueda)) {
								lstExpAprob.setStrEstadoTipoCredito("V");
								bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstExpAprob.getExpediente().getBdSaldoCredito());
								listaExpedienteComp.add(lstExpAprob);
							}
							if (lstExpAprob.getExpediente().getBdSaldoCredito().compareTo(BigDecimal.ZERO)==0 && lstExpAprob.getExpediente().getIntParaTipoCreditoCod().equals(intTipoCreditoBusqueda)) {
								lstExpAprob.setStrEstadoTipoCredito("C");
								bdSumatoriaSaldo = bdSumatoriaSaldo.add(lstExpAprob.getExpediente().getBdSaldoCredito());
								listaExpedienteComp.add(lstExpAprob);
							}
						}
					}
				}
				//RECHAZADOS
				listaExpedienteCreditoCompRechazados = estadoCtaFacade.getListaPrestamosRechazadosPorCuenta(cuentaComp, intTipoCreditoBusqueda);
				ExpedienteComp exp = null;
				if (listaExpedienteCreditoCompRechazados!=null && !listaExpedienteCreditoCompRechazados.isEmpty()) {
					for (ExpedienteCreditoComp lstExpCredRechazados : listaExpedienteCreditoCompRechazados) {
						exp = new ExpedienteComp();
						//PK EXPEDIENTECREDITO
						exp.getExpediente().getId().setIntPersEmpresaPk(lstExpCredRechazados.getExpedienteCredito().getId().getIntPersEmpresaPk());
						exp.getExpediente().getId().setIntCuentaPk(lstExpCredRechazados.getExpedienteCredito().getId().getIntCuentaPk());
						exp.getExpediente().getId().setIntItemExpediente(lstExpCredRechazados.getExpedienteCredito().getId().getIntItemExpediente());
						exp.getExpediente().getId().setIntItemExpedienteDetalle(lstExpCredRechazados.getExpedienteCredito().getId().getIntItemDetExpediente());
						//FECHA SOLICITUD
						exp.setStrFechaSolicitud(lstExpCredRechazados.getStrFechaSolicitud());
						//DESCRIPCION DEL PRESTAMO
						exp.setStrDescripcionExpediente(lstExpCredRechazados.getStrDescripcionExpedienteCredito());
						//SOLICITUD
						exp.setStrNroSolicitud(lstExpCredRechazados.getStrNroSolicitud());
						//PORCENTAJE INTERES
						exp.getExpediente().setBdPorcentajeInteres(lstExpCredRechazados.getExpedienteCredito().getBdPorcentajeInteres());
						//CT/CP/CA
						exp.getExpediente().setIntNumeroCuota(lstExpCredRechazados.getExpedienteCredito().getIntNumeroCuota());
						//CUOTA MENSUAL
						exp.setBdCuotaMensual(lstExpCredRechazados.getBdCuotaMensual());
						//MONTO TOTAL
						exp.getExpediente().setBdMontoTotal(lstExpCredRechazados.getExpedienteCredito().getBdMontoTotal());
						//ESTADO TIPO CREDITO
						exp.setStrEstadoTipoCredito("R");
						listaExpedienteComp.add(exp);
					}
				}				
				bdSumatoriaSaldoPrestamo = bdSumatoriaSaldo;
			}
			listarPersonasGarantizadas();
		} catch (BusinessException e) {
			log.error("Error en listarPrestamosSocio ---> "+ e);
		}		
	}
	//EVENTO DEL BOTON "CONSULTAR" EN TAB PRESTAMOS - LISTA PERSONAS GARANTIZADAS POR EL SOCIO
	public void listarPersonasGarantizadas(){
		log.info("------------------------ debuging.listarPersonasGarantizadas ------------------------");
		listaGarantiaCredito = null;
		GarantiaCredito garantiaCredito = new GarantiaCredito();
		try {
			garantiaCredito.setIntPersEmpresaGarantePk(SESION_IDEMPRESA);
			garantiaCredito.setIntPersPersonaGarantePk(socioComp.getSocio().getId().getIntIdPersona());
			garantiaCredito.setIntPersCuentaGarantePk(cuentaComp.getCuentaIntegrante().getId().getIntCuenta());
			listaGarantiaCredito = estadoCtaFacade.getListaGarantiaCreditoCompPorEmpPersCta(garantiaCredito);
			blnShowPanelTabPrestamosGarantizados = Boolean.TRUE;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	//EVENTO DEL BOTON "PLANILLA"
	public void obtenerDatosTabPlanilla(){
		log.info("------------------------ debuging.obtenerDatosTabPlanilla ------------------------");
		intShowPanel = 0;
		blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.TRUE;
			blnShowPanelTabPlanillaResumen = Boolean.TRUE;
			blnShowPanelTabPlanillaDiferencia = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 
		List<EnviadoEfectuadoComp> listaEnvioMontoEfectuado = null;
		listaGrillaTabPlanilla = new ArrayList<EstadoCuentaComp>();
		listaEnvEfecPlanilla = new ArrayList<EnviadoEfectuadoComp>();
		EnviadoEfectuadoComp envEfecComp = null;		
		
		try {
			if (intMesBusqueda!=-1) {
				log.info("----- cuenta: "+cuentaComp.getCuenta().getId().getIntCuenta());
				listaEnvioMontoEfectuado = estadoCtaFacade.getListaEnviadoEfectuado(cuentaComp, mesAnioToPeriodo(intMesBusqueda,intAnioBusqueda));
				if (listaEnvioMontoEfectuado!=null && !listaEnvioMontoEfectuado.isEmpty()) {
					for (EnviadoEfectuadoComp lstEnvMntoEfec : listaEnvioMontoEfectuado) {
						envEfecComp = new EnviadoEfectuadoComp();
						envEfecComp.setEnviomonto(new Enviomonto());
						envEfecComp.setEfectuado(new Efectuado());
						//PERIODO fmtPeriodo
						envEfecComp.setStrPeriodoConFormato(fmtPeriodo(lstEnvMntoEfec.getIntPeriodoPlanilla()));
						log.info("--- PERIODO ---"+envEfecComp.getStrPeriodoConFormato());
						//TOTAL ENVIADO POR HABERES E INCENTIVOS
						if (lstEnvMntoEfec.getListaEnviomonto()!=null && !lstEnvMntoEfec.getListaEnviomonto().isEmpty()) {
							for (Enviomonto lstEnvMnto : lstEnvMntoEfec.getListaEnviomonto()) {
								if (lstEnvMnto.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0) {
									envEfecComp.getEnviomonto().setBdMontoenvioHaberes(lstEnvMnto.getBdMontoenvio());
									envEfecComp.getEnviomonto().setStrCobroPlanillaHaberes(lstEnvMnto.getStrCobroPlanilla());
								}
								if (lstEnvMnto.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0) {
									envEfecComp.getEnviomonto().setBdMontoenvioIncentivos(lstEnvMnto.getBdMontoenvio());
									envEfecComp.getEnviomonto().setStrCobroPlanillaIncentivos(lstEnvMnto.getStrCobroPlanilla());
								}
								if (lstEnvMnto.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_CAS)==0) {
									envEfecComp.getEnviomonto().setBdMontoenvioCas(lstEnvMnto.getBdMontoenvio());
									envEfecComp.getEnviomonto().setStrCobroPlanillaHaberes(lstEnvMnto.getStrCobroPlanilla());
								}
							}						
						}
						//SUMATORIA TOTALES ENVIADOS
						envEfecComp.setBdSumEnviomonto(lstEnvMntoEfec.getBdSumEnviomonto());
						//TOTAL EFECTUADO POR HABERES E INCENTIVOS
						if (lstEnvMntoEfec.getListaEfectuado()!=null && !lstEnvMntoEfec.getListaEfectuado().isEmpty()) {
							for (Efectuado lstEfect : lstEnvMntoEfec.getListaEfectuado()) {
								if (lstEfect.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_HABERES)==0) {
									envEfecComp.getEfectuado().setBdMontoEfectuadoHaberes(lstEfect.getBdMontoEfectuado());
								}
								if (lstEfect.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_INCENTIVOS)==0) {
									envEfecComp.getEfectuado().setBdMontoEfectuadoIncentivos(lstEfect.getBdMontoEfectuado());
								}
								if (lstEfect.getIntModalidadCod().compareTo(Constante.PARAM_T_MODALIDADPLANILLA_CAS)==0) {
									envEfecComp.getEfectuado().setBdMontoEfectuadoCas(lstEfect.getBdMontoEfectuado());
								}
							}						
						}
						//SUMATORIA TOTALES EFECTUADOS
						envEfecComp.setBdSumEfectuado(lstEnvMntoEfec.getBdSumEfectuado());
						//DIFERENCIAS
						BigDecimal bdDiferenciaPlanilla = lstEnvMntoEfec.getBdSumEnviomonto().subtract(lstEnvMntoEfec.getBdSumEfectuado());
						if (bdDiferenciaPlanilla.compareTo(BigDecimal.ZERO)<0) {
							envEfecComp.setBdSumaDifMontoConcepto(bdDiferenciaPlanilla.multiply(BigDecimal.valueOf(-1)));
						}else {
							envEfecComp.setBdSumaDifMontoConcepto(bdDiferenciaPlanilla);
						}
						listaEnvEfecPlanilla.add(envEfecComp);
					}
				}
			}
			//SETEO EN LA LISTA PARA UTILIZARLO EN "obtenerDatosTabPlanillaDiferenciaPlanila()"
			setListaEnviadoEfectuadoComp(null);
			setListaEnviadoEfectuadoComp(listaEnvioMontoEfectuado);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	//EVENTO DEL BOTON "DIFERENCIA PLANILLA" DEL TAB PLANILLA
	public void obtenerDatosTabPlanillaDiferenciaPlanila() throws BusinessException{
		intShowPanel = 0;
		log.info("------------------------ debuging.obtenerDatosTabPlanillaDiferenciaPlanila ------------------------");
		blnShowPanelTabPlanilla = Boolean.TRUE;
		blnShowPanelTabPlanillaResumen = Boolean.FALSE;
		blnShowPanelTabPlanillaDiferencia = Boolean.TRUE;
		lstColumnasPrioridadDscto = null;
		List<EnviadoEfectuadoComp> lstFilasPrioridadDscto = null; 		
		List<PrioridadDescuento> listaGralPrioridadDescuento = new ArrayList<PrioridadDescuento>();	
		
		try {
			lstFilasPrioridadDscto = listaEnviadoEfectuadoComp;
			if (lstFilasPrioridadDscto!=null && !lstFilasPrioridadDscto.isEmpty()) {
				//GUARDO TODAS LAS PRIORIDADES EN UNA LISTA
				for (EnviadoEfectuadoComp lstEnvEfecComp : lstFilasPrioridadDscto) {
					if (lstEnvEfecComp.getListaPrioridadDescuento()!=null && !lstEnvEfecComp.getListaPrioridadDescuento().isEmpty()) {
						for (PrioridadDescuento getLstPriDscto : lstEnvEfecComp.getListaPrioridadDescuento()) {
							listaGralPrioridadDescuento.add(getLstPriDscto);
						}
					}					
				}
				//LIMPIANDO LISTA PRIORIDADES DE ELEMENTOS REPETIDOS
				Integer flag=0;
				for (PrioridadDescuento lstGralPrioDscto : listaGralPrioridadDescuento) {
					if (lstColumnasPrioridadDscto!=null && !lstColumnasPrioridadDscto.isEmpty()) {
						for (EnviadoEfectuadoComp lstCols : lstColumnasPrioridadDscto) {
							if (lstGralPrioDscto.getId().getIntItemPrioridadDescuento().equals(lstCols.getIntItemPrioridadDescuento())) {
								flag=1;
								break;
							}
						}
						if (flag==0) {
							EnviadoEfectuadoComp envEfecComp= new EnviadoEfectuadoComp();
							envEfecComp.setStrDescPrioridad(lstGralPrioDscto.getStrDescripcion());
							envEfecComp.setIntItemPrioridadDescuento(lstGralPrioDscto.getId().getIntItemPrioridadDescuento());
							envEfecComp.setIntPrdeOrdenprioridad(lstGralPrioDscto.getIntPrdeOrdenprioridad());
							lstColumnasPrioridadDscto.add(envEfecComp);
						}						
					}
					if (lstColumnasPrioridadDscto==null) {
						EnviadoEfectuadoComp envEfecComp= new EnviadoEfectuadoComp();
						lstColumnasPrioridadDscto = new ArrayList<EnviadoEfectuadoComp>();
						envEfecComp.setStrDescPrioridad(lstGralPrioDscto.getStrDescripcion());
						envEfecComp.setIntItemPrioridadDescuento(lstGralPrioDscto.getId().getIntItemPrioridadDescuento());
						envEfecComp.setIntPrdeOrdenprioridad(lstGralPrioDscto.getIntPrdeOrdenprioridad());
						lstColumnasPrioridadDscto.add(envEfecComp);
					}
				}
				//ORDENANDO POR ORDEN DE PRIORIDAD
				Collections.sort(lstColumnasPrioridadDscto, new Comparator<EnviadoEfectuadoComp>() {
		            public int compare(EnviadoEfectuadoComp o1, EnviadoEfectuadoComp o2) {
		            	EnviadoEfectuadoComp e1 = (EnviadoEfectuadoComp) o1;
		            	EnviadoEfectuadoComp e2 = (EnviadoEfectuadoComp) o2;
		                return e1.getIntPrdeOrdenprioridad().compareTo(e2.getIntPrdeOrdenprioridad());
		            }
		        });
				
				//CALCULANDO DIFERENCIA ENVIOCONCEPTO Y EFECTUADOCONCEPTO
				for (EnviadoEfectuadoComp lstFilaPrdad : lstFilasPrioridadDscto) {
					BigDecimal bdSumDifMtoCpto = BigDecimal.ZERO;
					if (lstFilaPrdad.getListaEfectuadoConcepto()!=null && !lstFilaPrdad.getListaEfectuadoConcepto().isEmpty()) {
						for (Envioconcepto getLstEnvCpto : lstFilaPrdad.getListaEnvioconcepto()) {									
							for (EfectuadoConcepto getLstEfecCpto : lstFilaPrdad.getListaEfectuadoConcepto()) {
								getLstEfecCpto.setIntItemCuentaConcepto(getLstEfecCpto.getIntItemCuentaConcepto()==null?0:getLstEfecCpto.getIntItemCuentaConcepto());
								getLstEfecCpto.setIntItemExpediente(getLstEfecCpto.getIntItemExpediente()==null?0:getLstEfecCpto.getIntItemExpediente());
								getLstEfecCpto.setIntItemDetExpediente(getLstEfecCpto.getIntItemDetExpediente()==null?0:getLstEfecCpto.getIntItemDetExpediente());
								if ((getLstEfecCpto.getIntItemCuentaConcepto().equals(getLstEnvCpto.getIntItemcuentaconcepto()) 
									 && getLstEfecCpto.getIntTipoConceptoGeneralCod().equals(getLstEnvCpto.getIntTipoconceptogeneralCod()))
										|| (getLstEfecCpto.getIntItemExpediente().equals(getLstEnvCpto.getIntItemexpediente())
										&& getLstEfecCpto.getIntItemDetExpediente().equals(getLstEnvCpto.getIntItemdetexpediente()))
										&& getLstEfecCpto.getIntTipoConceptoGeneralCod().equals(getLstEnvCpto.getIntTipoconceptogeneralCod())){
									getLstEfecCpto.setBdMontoConcepto(getLstEfecCpto.getBdMontoConcepto()==null?BigDecimal.ZERO:getLstEfecCpto.getBdMontoConcepto());
									getLstEnvCpto.setBdDiferenciaMontoconcepto(getLstEnvCpto.getBdMontoconcepto().subtract(getLstEfecCpto.getBdMontoConcepto()));
								}
							}
							bdSumDifMtoCpto= bdSumDifMtoCpto.add(getLstEnvCpto.getBdDiferenciaMontoconcepto());
						}
						lstFilaPrdad.setBdSumaDifMontoConcepto(bdSumDifMtoCpto);
					}else {
						for (Envioconcepto getLstEnvCpto : lstFilaPrdad.getListaEnvioconcepto()) {	
							getLstEnvCpto.setBdDiferenciaMontoconcepto(getLstEnvCpto.getBdMontoconcepto()==null?BigDecimal.ZERO:getLstEnvCpto.getBdMontoconcepto());
							bdSumDifMtoCpto= bdSumDifMtoCpto.add(getLstEnvCpto.getBdDiferenciaMontoconcepto());
						}
						lstFilaPrdad.setBdSumaDifMontoConcepto(bdSumDifMtoCpto);
					}
				}						
				//FOREACH_ITEMS
				for (EnviadoEfectuadoComp columnas : lstColumnasPrioridadDscto) {
					for (EnviadoEfectuadoComp filas : lstFilasPrioridadDscto) {
						filas.setStrPeriodoPlanilla(fmtPeriodo(filas.getIntPeriodoPlanilla()));
						if (filas.getListaPrioridadDescuento()!=null && !filas.getListaPrioridadDescuento().isEmpty()) {
							BigDecimal bdDifMtoCpto = BigDecimal.ZERO;
							for (PrioridadDescuento lstPrioridadPorPeriodo : filas.getListaPrioridadDescuento()) {
								if (lstPrioridadPorPeriodo.getId().getIntItemPrioridadDescuento().equals(columnas.getIntItemPrioridadDescuento())) {
									for (Envioconcepto lstEnvCpto : filas.getListaEnvioconcepto()) {
										if (lstPrioridadPorPeriodo.getIntItemcuentaconcepto()!=null && lstEnvCpto.getIntItemcuentaconcepto()!=null) {
											if (lstEnvCpto.getIntItemcuentaconcepto().equals(lstPrioridadPorPeriodo.getIntItemcuentaconcepto())
													&& lstEnvCpto.getIntTipoconceptogeneralCod().equals(lstPrioridadPorPeriodo.getIntParaTipoconceptogeneral())) {
												bdDifMtoCpto = bdDifMtoCpto.add(lstEnvCpto.getBdDiferenciaMontoconcepto());
											}
										}
										if (lstPrioridadPorPeriodo.getIntItemexpediente()!=null && lstEnvCpto.getIntItemexpediente()!=null){
											if (lstEnvCpto.getIntItemexpediente().equals(lstPrioridadPorPeriodo.getIntItemexpediente())
													&& lstEnvCpto.getIntItemdetexpediente().equals(lstPrioridadPorPeriodo.getIntItemdetexpediente())
													&& lstEnvCpto.getIntTipoconceptogeneralCod().equals(lstPrioridadPorPeriodo.getIntParaTipoconceptogeneral())) {
												bdDifMtoCpto = bdDifMtoCpto.add(lstEnvCpto.getBdDiferenciaMontoconcepto());
											}
										}
									}
								}
							}
							if(filas.getLstBdMontoPorPeriodoYPrioridad()==null){
								filas.setLstBdMontoPorPeriodoYPrioridad(new BigDecimal[1]);
							}else{
								BigDecimal values[] = new BigDecimal[filas.getLstBdMontoPorPeriodoYPrioridad().length+1];
								for(int i=0; i< filas.getLstBdMontoPorPeriodoYPrioridad().length; i++){
									values[i] = filas.getLstBdMontoPorPeriodoYPrioridad()[i];
								}
								filas.setLstBdMontoPorPeriodoYPrioridad(values);
							}
							filas.getLstBdMontoPorPeriodoYPrioridad()[filas.getLstBdMontoPorPeriodoYPrioridad().length-1]=bdDifMtoCpto;
						}else {
							if(filas.getLstBdMontoPorPeriodoYPrioridad()==null){
								filas.setLstBdMontoPorPeriodoYPrioridad(new BigDecimal[1]);
							}else{
								BigDecimal values[] = new BigDecimal[filas.getLstBdMontoPorPeriodoYPrioridad().length+1];
								for(int i=0; i< filas.getLstBdMontoPorPeriodoYPrioridad().length; i++){
									values[i] = filas.getLstBdMontoPorPeriodoYPrioridad()[i];
								}
								filas.setLstBdMontoPorPeriodoYPrioridad(values);
							}
							filas.getLstBdMontoPorPeriodoYPrioridad()[filas.getLstBdMontoPorPeriodoYPrioridad().length-1]=BigDecimal.ZERO;
						}
					}
				}				
			}
			setListaMontoPorPeriodoYPrioridad(new ArrayList<EnviadoEfectuadoComp>());
			setListaMontoPorPeriodoYPrioridad(lstFilasPrioridadDscto);
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	public void retornarTabPlanilla() throws BusinessException{
		blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.TRUE;
			blnShowPanelTabPlanillaResumen = Boolean.TRUE;
			blnShowPanelTabPlanillaDiferencia = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 
	}
	//EVENTO DEL BOTON "PREVISION SOCIAL"
	public void obtenerDatosTabPrevisionSocial() throws BusinessException{
		log.info("------------------------ debuging.obtenerDatosTabPrevisionSocial ------------------------");
		intShowPanel = 0;
		blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.TRUE;
		listaExpedientePrevisionComp = new ArrayList<ExpedientePrevisionComp>();
		ExpedientePrevisionComp expPrevComp = null;
		
		try {
			log.info("----- cuenta: "+cuentaComp.getCuenta().getId().getIntCuenta());
			//BENEFICIOS OTORGADOS
			List<ExpedientePrevisionComp> lstExpPrevision = estadoCtaFacade.getListaExpedientePrevisionComp(cuentaComp);
			if (lstExpPrevision!=null && !lstExpPrevision.isEmpty()) {
				for (ExpedientePrevisionComp obj : lstExpPrevision) {
					Integer cont=0;
					for (BeneficiarioPrevision lstBenefPrev : obj.getBeneficiarioPrevision()) {
						if (cont.equals(0)) {
							expPrevComp = new ExpedientePrevisionComp();
							//FECHA
							expPrevComp.setStrFechaSolicitud(obj.getStrFechaSolicitud());
							//NUMERO DE SOLICITUD
							expPrevComp.setStrNroSolicitud(obj.getExpedientePrevision().getId().getIntItemExpediente().toString());
							//TIPO DE BENEFICIO
							expPrevComp.setStrDescripcionTipoBeneficio(obj.getStrDescripcionTipoBeneficio());
							//MONTO
							expPrevComp.setBdMontoNeto(lstBenefPrev.getBdMontoNeto());
							//DATOS DEL FALLECIDO
							expPrevComp.setIntTipoVinculoFallecido(obj.getFallecidoPrevision()!=null?obj.getFallecidoPrevision().get(0).getIntTipoViculo():null);
							expPrevComp.setStrNomApeFallecido(obj.getFallecidoPrevision()!=null?obj.getFallecidoPrevision().get(0).getStrNomApeFallecido():null);
							//DATOS DEL BENEFICIARIO
							expPrevComp.setIntTipoVinculoBeneficiario(lstBenefPrev.getIntTipoViculo());
							expPrevComp.setStrNomApeBeneficiario(lstBenefPrev.getStrNomApeBeneficiario());
							listaExpedientePrevisionComp.add(expPrevComp);
							cont++;
						}else {
							expPrevComp = new ExpedientePrevisionComp();
							//MONTO
							expPrevComp.setBdMontoNeto(lstBenefPrev.getBdMontoNeto());
							//DATOS DEL BENEFICIARIO
							expPrevComp.setIntTipoVinculoBeneficiario(lstBenefPrev.getIntTipoViculo());
							expPrevComp.setStrNomApeBeneficiario(lstBenefPrev.getStrNomApeBeneficiario());
							listaExpedientePrevisionComp.add(expPrevComp);
						}						
					}
				}
			}			
			//PAGOS DE CUOTAS DEL BENEFICIO (SOLO APLICA A FONDO SEPELIO Y FONDO RETIRO)
			List<PrevisionSocialComp> lstPrevisionSocial = estadoCtaFacade.getListaPrevisionSocialComp(cuentaComp);
			listaPrevSocialFdoSepelio = new ArrayList<PrevisionSocialComp>();
			if (lstPrevisionSocial!=null && !lstPrevisionSocial.isEmpty()) {
				for (PrevisionSocialComp lstPrevSoc : lstPrevisionSocial) {
					if (lstPrevSoc.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_SEPELIO)) {
						listaPrevSocialFdoSepelio.add(lstPrevSoc);
					}
				}
				
				//-------------------------- FDO SEPELIO --------------------------\\
				
				//ORDENANDO POR PERIODO DESCENDENTE - FDO SEPELIO
				Collections.sort(listaPrevSocialFdoSepelio, new Comparator<PrevisionSocialComp>() {
		            public int compare(PrevisionSocialComp o1, PrevisionSocialComp o2) {
		            	PrevisionSocialComp e1 = (PrevisionSocialComp) o1;
		            	PrevisionSocialComp e2 = (PrevisionSocialComp) o2;
		                return e2.getIntPeriodo().compareTo(e1.getIntPeriodo());
		            }
		        });
				
				//AÑOS APORTADOS
				intPgoCtaBeneficioFdoSepelio=0;
				Integer cont=0;
				String periodoTemp="";
				for (PrevisionSocialComp lstPrevSocFdoSepelio : listaPrevSocialFdoSepelio) {
					if (cont==0) {
						if (lstPrevSocFdoSepelio.getBdPendiente().compareTo(BigDecimal.ZERO)==0) {
							intPgoCtaBeneficioFdoSepelio++;
						}	
						periodoTemp = lstPrevSocFdoSepelio.getStrPeriodo();
						cont++;
					}else {
						if (lstPrevSocFdoSepelio.getStrPeriodo().compareTo(periodoTemp)!=0) {
							if (lstPrevSocFdoSepelio.getBdPendiente().compareTo(BigDecimal.ZERO)==0) {
								intPgoCtaBeneficioFdoSepelio++;
							}
						}else {
							if (intPgoCtaBeneficioFdoSepelio!=0) {
								if (lstPrevSocFdoSepelio.getBdPendiente().compareTo(BigDecimal.ZERO)!=0) {
									intPgoCtaBeneficioFdoSepelio=0;
								}
							}							
						}
						periodoTemp = lstPrevSocFdoSepelio.getStrPeriodo();
					}
				}
				log.info("--- AÑOS APORTADOS ---"+intPgoCtaBeneficioFdoSepelio);
				//-------------------------- FDO RETIRO --------------------------\\
				listaPrevSocialFdoRetiro = new ArrayList<PrevisionSocialComp>();
				if (lstPrevisionSocial!=null && !lstPrevisionSocial.isEmpty()) {
					for (PrevisionSocialComp lstPrevSoc : lstPrevisionSocial) {
						if (lstPrevSoc.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_RETIRO)) {
							listaPrevSocialFdoRetiro.add(lstPrevSoc);
						}
					}
				}
				//ORDENANDO POR PERIODO ASCENDENTE PARA ACUMULADO
				Collections.sort(listaPrevSocialFdoRetiro, new Comparator<PrevisionSocialComp>() {
		            public int compare(PrevisionSocialComp o1, PrevisionSocialComp o2) {
		            	PrevisionSocialComp e1 = (PrevisionSocialComp) o1;
		            	PrevisionSocialComp e2 = (PrevisionSocialComp) o2;
		                return e1.getIntPeriodo().compareTo(e2.getIntPeriodo());
		            }
		        });
				
				BigDecimal bdAcumulado = null;//BigDecimal.ZERO;
				for (PrevisionSocialComp lstPrevSocFdoRetiro : listaPrevSocialFdoRetiro) {
					if (bdAcumulado==null) {
						bdAcumulado=BigDecimal.ZERO;
						lstPrevSocFdoRetiro.setBdAcumulado(bdAcumulado.add(lstPrevSocFdoRetiro.getBdCancelado()));
						bdAcumulado = lstPrevSocFdoRetiro.getBdAcumulado();
					}else {
						lstPrevSocFdoRetiro.setBdAcumulado(bdAcumulado.add(lstPrevSocFdoRetiro.getBdCancelado()));
						bdAcumulado = lstPrevSocFdoRetiro.getBdAcumulado();
					}
					
				}
				//ORDENANDO POR PERIODO DESCENDENTE PARA MOSTRAR EN LA GRILLA
				Collections.sort(listaPrevSocialFdoRetiro, new Comparator<PrevisionSocialComp>() {
		            public int compare(PrevisionSocialComp o1, PrevisionSocialComp o2) {
		            	PrevisionSocialComp e1 = (PrevisionSocialComp) o1;
		            	PrevisionSocialComp e2 = (PrevisionSocialComp) o2;
		                return e2.getIntPeriodo().compareTo(e1.getIntPeriodo());
		            }
		        });		
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	//EVENTO DEL LINK "DETALLE" EN EL TAB PREVISION SOCIAL PARA FDO.SEPELIO
	public void obtenerPagoCuotasBenefFdoSepelioDetalle(ActionEvent event) throws BusinessException{
		log.info("------------------------ debuging.obtenerPagoCuotasBenefFdoSepelioDetalle ------------------------");
		PrevisionSocialComp prevSoc = new PrevisionSocialComp();
		//List<MovimientoEstCtaComp> listaMovimientoEstCtaComp = null;
		listaPrevSocialFdoSepelioDetalle = new ArrayList<MovimientoEstCtaComp>();
		
		try {
			//FILTRANDO MOVIMIENTOS DE FDO.SEPELIO
			prevSoc = (PrevisionSocialComp)event.getComponent().getAttributes().get("itemGrilla");
 			listaPrevSocialFdoSepelioDetalle = estadoCtaFacade.getListaMovimiento(prevSoc.getListaConceptoPago());
			//ORDENANDO POR PERIODO DESCENDENTE LOS MOVIMIENTOS DE FDO.SEPELIO
			Collections.sort(listaPrevSocialFdoSepelioDetalle, new Comparator<MovimientoEstCtaComp>() {
	            public int compare(MovimientoEstCtaComp o1, MovimientoEstCtaComp o2) {
	            	MovimientoEstCtaComp e1 = (MovimientoEstCtaComp) o1;
	            	MovimientoEstCtaComp e2 = (MovimientoEstCtaComp) o2;
	                return e2.getIntPeriodoFechaMovimiento().compareTo(e1.getIntPeriodoFechaMovimiento());
	            }
	        });
		} catch(Exception e){
			throw new BusinessException(e);
		}
	}
	//EVENTO DEL BOTON "DETALLE" EN EL TAB PREVISION SOCIAL PARA FDO.RETIRO
	public void obtenerPagoCuotasBenefFdoRetiroDetalle() throws BusinessException{
		log.info("------------------------ debuging.obtenerPagoCuotasBenefFdoRetiroDetalle ------------------------");
		List<ConceptoPago> listaConceptoPago = new ArrayList<ConceptoPago>();
		List<MovimientoEstCtaComp> listaMovEstCta = null;
		List<MovimientoEstCtaComp> listaMovEstCtaInteres = null;
		MovimientoEstCtaComp movFdoRetiroAportes = new MovimientoEstCtaComp();
		MovimientoEstCtaComp movFdoRetiroInteres = new MovimientoEstCtaComp();
		listaPrevSocialFdoRetiroDetalle = new ArrayList<MovimientoEstCtaComp>();
		
		try {
			if (listaPrevSocialFdoRetiro!=null && !listaPrevSocialFdoRetiro.isEmpty()) {
				for (PrevisionSocialComp lstPrevSocFdoRetiro : listaPrevSocialFdoRetiro) {
					if (lstPrevSocFdoRetiro.getListaConceptoPago()!=null && !lstPrevSocFdoRetiro.getListaConceptoPago().isEmpty()) {
						for (ConceptoPago lstCptoPgo : lstPrevSocFdoRetiro.getListaConceptoPago()) {
							listaConceptoPago.add(lstCptoPgo);
						}
					}
				}
			}
			listaMovEstCta = estadoCtaFacade.getListaMovimiento(listaConceptoPago);
			listaMovEstCtaInteres = estadoCtaFacade.getListaMovimientoFdoRetiroInteres(cuentaComp);
			for (MovimientoEstCtaComp lstMovEstCtaInteres : listaMovEstCtaInteres) {
				listaMovEstCta.add(lstMovEstCtaInteres);
			}
			
			if (listaPrevSocialFdoRetiro!=null && !listaPrevSocialFdoRetiro.isEmpty()) {
				for (PrevisionSocialComp lstPrevSocFdoRet : listaPrevSocialFdoRetiro) {
					if (listaMovEstCta!=null && !listaMovEstCta.isEmpty()) {
						for (MovimientoEstCtaComp lstMovEstCta : listaMovEstCta) {
							if ((lstMovEstCta.getIntPeriodoFechaMovimiento().toString().substring(0, 4)).compareTo(lstPrevSocFdoRet.getStrPeriodo())==0) {
								if (lstMovEstCta.getMovimiento().getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)) {
									//CONCEPTO
									movFdoRetiroAportes.setStrDescTipoConceptoGral(lstMovEstCta.getStrDescTipoConceptoGral());
									//PERIODO									
									movFdoRetiroAportes.setStrFechaMovimiento(lstPrevSocFdoRet.getStrPeriodo());
									Integer intMesPeriodo = Integer.valueOf(lstMovEstCta.getIntPeriodoFechaMovimiento().toString().substring(4, 6));
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_ENERO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoEne()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoEne(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoEne(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoEne(movFdoRetiroAportes.getBdMontoMovimientoEne().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoEne(movFdoRetiroAportes.getBdMontoMovimientoEne().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_FEBRERO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoFeb()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoFeb(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoFeb(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoFeb(movFdoRetiroAportes.getBdMontoMovimientoFeb().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoFeb(movFdoRetiroAportes.getBdMontoMovimientoFeb().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_MARZO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoMar()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoMar(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoMar(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoMar(movFdoRetiroAportes.getBdMontoMovimientoMar().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoMar(movFdoRetiroAportes.getBdMontoMovimientoMar().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}								
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_ABRIL)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoAbr()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoAbr(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoAbr(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoAbr(movFdoRetiroAportes.getBdMontoMovimientoAbr().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoAbr(movFdoRetiroAportes.getBdMontoMovimientoAbr().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_MAYO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoMay()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoMay(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoMay(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoMay(movFdoRetiroAportes.getBdMontoMovimientoMay().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoMay(movFdoRetiroAportes.getBdMontoMovimientoMay().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_JUNIO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoJun()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoJun(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoJun(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoJun(movFdoRetiroAportes.getBdMontoMovimientoJun().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoJun(movFdoRetiroAportes.getBdMontoMovimientoJun().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_JULIO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoJul()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoJul(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoJul(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoJul(movFdoRetiroAportes.getBdMontoMovimientoJul().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoJul(movFdoRetiroAportes.getBdMontoMovimientoJul().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_AGOSTO)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoAgo()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoAgo(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoAgo(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoAgo(movFdoRetiroAportes.getBdMontoMovimientoAgo().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoAgo(movFdoRetiroAportes.getBdMontoMovimientoAgo().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_SETIEMBRE)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoSet()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoSet(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoSet(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoSet(movFdoRetiroAportes.getBdMontoMovimientoSet().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoSet(movFdoRetiroAportes.getBdMontoMovimientoSet().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_OCTUBRE)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoOct()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoOct(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoOct(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoOct(movFdoRetiroAportes.getBdMontoMovimientoOct().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoOct(movFdoRetiroAportes.getBdMontoMovimientoOct().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_NOVIEMBRE)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoNov()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoNov(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoNov(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoNov(movFdoRetiroAportes.getBdMontoMovimientoNov().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoNov(movFdoRetiroAportes.getBdMontoMovimientoNov().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_DICIEMBRE)) {
										if (movFdoRetiroAportes.getBdMontoMovimientoDic()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoDic(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoDic(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroAportes.setBdMontoMovimientoDic(movFdoRetiroAportes.getBdMontoMovimientoDic().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroAportes.setBdMontoMovimientoDic(movFdoRetiroAportes.getBdMontoMovimientoDic().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
								}
									
								if (lstMovEstCta.getMovimiento().getIntParaTipoConceptoGeneral().equals(Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES)) {
									//CONCEPTO
									movFdoRetiroInteres.setStrDescTipoConceptoGral(lstMovEstCta.getStrDescTipoConceptoGral());
									//PERIODO									
									movFdoRetiroInteres.setStrFechaMovimiento(lstPrevSocFdoRet.getStrPeriodo());
									Integer intMesPeriodo = Integer.valueOf(lstMovEstCta.getIntPeriodoFechaMovimiento().toString().substring(4, 6));
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_ENERO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoEne()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoEne(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoEne(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoEne(movFdoRetiroInteres.getBdMontoMovimientoEne().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoEne(movFdoRetiroInteres.getBdMontoMovimientoEne().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_FEBRERO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoFeb()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoFeb(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoFeb(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoFeb(movFdoRetiroInteres.getBdMontoMovimientoFeb().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoFeb(movFdoRetiroInteres.getBdMontoMovimientoFeb().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_MARZO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoMar()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoMar(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoMar(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoMar(movFdoRetiroInteres.getBdMontoMovimientoMar().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoMar(movFdoRetiroInteres.getBdMontoMovimientoMar().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}								
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_ABRIL)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoAbr()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoAbr(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoAbr(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoAbr(movFdoRetiroInteres.getBdMontoMovimientoAbr().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoAbr(movFdoRetiroInteres.getBdMontoMovimientoAbr().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_MAYO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoMay()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoMay(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoMay(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoMay(movFdoRetiroInteres.getBdMontoMovimientoMay().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoMay(movFdoRetiroInteres.getBdMontoMovimientoMay().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_JUNIO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoJun()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoJun(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoJun(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoJun(movFdoRetiroInteres.getBdMontoMovimientoJun().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoJun(movFdoRetiroInteres.getBdMontoMovimientoJun().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_JULIO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoJul()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoJul(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoJul(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoJul(movFdoRetiroInteres.getBdMontoMovimientoJul().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoJul(movFdoRetiroInteres.getBdMontoMovimientoJul().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_AGOSTO)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoAgo()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoAgo(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoAgo(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoAgo(movFdoRetiroInteres.getBdMontoMovimientoAgo().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoAgo(movFdoRetiroInteres.getBdMontoMovimientoAgo().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_SETIEMBRE)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoSet()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoSet(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoSet(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoSet(movFdoRetiroInteres.getBdMontoMovimientoSet().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoSet(movFdoRetiroInteres.getBdMontoMovimientoSet().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_OCTUBRE)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoOct()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoOct(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoOct(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoOct(movFdoRetiroInteres.getBdMontoMovimientoOct().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoOct(movFdoRetiroInteres.getBdMontoMovimientoOct().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_NOVIEMBRE)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoNov()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoNov(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoNov(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoNov(movFdoRetiroInteres.getBdMontoMovimientoNov().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoNov(movFdoRetiroInteres.getBdMontoMovimientoNov().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
									if (intMesPeriodo.equals(Constante.PARAM_T_MES_DICIEMBRE)) {
										if (movFdoRetiroInteres.getBdMontoMovimientoDic()==null) {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoDic(lstMovEstCta.getMovimiento().getBdMontoMovimiento().multiply(BigDecimal.valueOf(-1)));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoDic(lstMovEstCta.getMovimiento().getBdMontoMovimiento());
										}else {
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_CARGO)) movFdoRetiroInteres.setBdMontoMovimientoDic(movFdoRetiroInteres.getBdMontoMovimientoDic().subtract(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
											if (lstMovEstCta.getMovimiento().getIntParaTipoCargoAbono().equals(Constante.PARAM_T_CARGOABONO_ABONO)) movFdoRetiroInteres.setBdMontoMovimientoDic(movFdoRetiroInteres.getBdMontoMovimientoDic().add(lstMovEstCta.getMovimiento().getBdMontoMovimiento()));
										}
									}
								}
							}
						}
						//APORTES POR PERIODO
						if (movFdoRetiroAportes.getStrFechaMovimiento()==null) {
							movFdoRetiroAportes.setStrDescTipoConceptoGral("APORTE");
							movFdoRetiroAportes.setStrFechaMovimiento(lstPrevSocFdoRet.getStrPeriodo());
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
						listaPrevSocialFdoRetiroDetalle.add(movFdoRetiroAportes);
						movFdoRetiroAportes = new MovimientoEstCtaComp();
						//INTERES POR PERIODO
						if (movFdoRetiroInteres.getStrFechaMovimiento()==null) {
							movFdoRetiroInteres.setStrDescTipoConceptoGral("INTERES");
							movFdoRetiroInteres.setStrFechaMovimiento(lstPrevSocFdoRet.getStrPeriodo());
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
						listaPrevSocialFdoRetiroDetalle.add(movFdoRetiroInteres);
						movFdoRetiroInteres = new MovimientoEstCtaComp();
					}
				}
			}
			log.info("--- SIZE listaPrevSocialFdoRetiroDetalle: ---"+listaPrevSocialFdoRetiroDetalle.size());
			//ACUMULADO
			BigDecimal bdMontoAcumuladoAporte = BigDecimal.ZERO;
			BigDecimal bdMontoAcumuladoInteres = BigDecimal.ZERO;
			Integer intSize = listaPrevSocialFdoRetiroDetalle.size()-1;
			for (int i = intSize; i >=0 ; i--) {
				if (listaPrevSocialFdoRetiroDetalle.get(i).getStrDescTipoConceptoGral().compareTo("INTERES")==0) {
					bdMontoAcumuladoInteres = bdMontoAcumuladoInteres.add(listaPrevSocialFdoRetiroDetalle.get(i).getBdMontoTotal());
					listaPrevSocialFdoRetiroDetalle.get(i).setBdMontoAcumulado(bdMontoAcumuladoInteres);
				}
				if (listaPrevSocialFdoRetiroDetalle.get(i).getStrDescTipoConceptoGral().compareTo("APORTE")==0) {
					bdMontoAcumuladoAporte = bdMontoAcumuladoAporte.add(listaPrevSocialFdoRetiroDetalle.get(i).getBdMontoTotal());
					listaPrevSocialFdoRetiroDetalle.get(i).setBdMontoAcumulado(bdMontoAcumuladoAporte);
				}				
			}
		} catch(Exception e){
			throw new BusinessException(e);
		}
	}
	//SELECCIONAR FILA EN BUSQUEDA SOCIO POR NOMBRES Y APELLIDOS
	public void setSelectedSocioNatural(ActionEvent event){
		log.info("-------------------------------------Debugging estadoCuentaController.setSelectedSocioNatural-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowSocioNatural"));
		setSocioNaruralSelected(null);
		String selectedRow = getRequestParameter("rowSocioNatural");
		SocioComp socioComp = new SocioComp();
		for(int i=0; i<listaSocioBusquedaPorApeNom.size(); i++){
			socioComp = listaSocioBusquedaPorApeNom.get(i);
			if(i == Integer.parseInt(selectedRow)){
				setSocioNaruralSelected(socioComp);
				break;
			}
		}
	}
	//EVENTO DEL BOTON "RESUMEN"
	public void retornarResumenEstadoCta(){
		intShowPanel = 1;
		blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
		blnShowPanelResumenEstadoCta = Boolean.TRUE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 
	}
	//EVENTO TAB "GESTIONES"
	public void obtenerDatosTabGestiones() throws BusinessException{
		intShowPanel = 0;
		blnShowPanelDatosDelSocioYCuentas = Boolean.TRUE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.TRUE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE; 
		listaGestionCobranzaSocio = null;
		String strFechaGestion = null;

		try {
			if (intMesBusqueda!=-1) {
				strFechaGestion = "01/"+(intMesBusqueda<10?"0"+intMesBusqueda:intMesBusqueda)+"/"+intAnioBusqueda;
				listaGestionCobranzaSocio = estadoCtaFacade.getListaGestionCobranzaSoc(cuentaComp.getCuenta().getId(),strFechaGestion);
			}
		} catch(Exception e){
			throw new BusinessException(e);
		}
	}
	//CONCATENA MES Y AÑO
	public static Integer mesAnioToPeriodo(Integer mes, Integer anio){
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
	public String fmtPeriodo(Integer intPeriodo) throws BusinessException{
		String strFmtPeriodo="";
		String strMesPeriodo="";
		List<Tabla> listaDescripcionMes = null;
		try {
			listaDescripcionMes = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MES_CALENDARIO));
			if (listaDescripcionMes!=null && !listaDescripcionMes.isEmpty()) {
				for (Tabla mes : listaDescripcionMes) {
					if(mes.getIntIdDetalle().compareTo(Integer.parseInt(intPeriodo.toString().substring(4,6)))==0){
						strMesPeriodo = mes.getStrDescripcion();
					}
				}
				strFmtPeriodo=strMesPeriodo+" - "+intPeriodo.toString().substring(0,4);
			}
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return strFmtPeriodo;	
	}
	//FORMATO BIGDECIMAL TO STRING (#00.00)
	public String bigDecimalToString(BigDecimal big){		
		String retorna = "";
		if (big==null) {
			retorna = "";
		}else{
			String s= big.toString();
			Integer r = s.indexOf(".");
			if (r == -1) {
				retorna = s+".00";
			}else{
				Integer largo = s.length();
				String decimal= s.substring(r+1, largo);
				if (decimal.length()==1) {
					retorna = s+"0";
				}else{
					retorna = s;
				}
			}
		}
		Integer r = retorna.indexOf("-");
		if (r != -1) {
			retorna = (new BigDecimal(retorna).multiply(BigDecimal.valueOf(-1))).toString();
		}
        return retorna;
	}	
	
	public String convertirMontos(BigDecimal bdMonto){
		//Formato de nro....
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(','); 
		formato = new DecimalFormat("#,##0.00",otherSymbols);
		String strMonto = formato.format(bdMonto);
		//Quita signo (-)...
		Integer r = strMonto.indexOf("-");
		if (r != -1) {
			strMonto = (new BigDecimal(strMonto).multiply(BigDecimal.valueOf(-1))).toString();
		}
        return strMonto;
	}
	//FECHA Y HORA ACTUAL DE EJECUCION
	public void fechaHoraActual(){
		fmt = new Formatter();			
		Calendar cal = Calendar.getInstance();
		strFechaYHoraActual = "";
		strFechaYHoraActual = fmt.format("%1$02d/%2$02d/%3$04d - %4$02d:%5$02d",cal.get(Calendar.DATE),cal.get(Calendar.MONTH)+1,cal.get(Calendar.YEAR),cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE)).toString();
	}
	//CALCULO DIFERENCIA DE MESES 
	public void calculoDiferenciaMeses(Integer anio, Integer mes){
		log.info("-------------------------------------Debugging EstadoCuentaController.calculoDiferenciaMeses-------------------------------------");
		bdDiferenciaMes = BigDecimal.ZERO;
		Integer intDiferenciaMeses = 0;
		if (anio == Calendar.getInstance().get(Calendar.YEAR)) {
			intDiferenciaMeses = Calendar.getInstance().get(Calendar.MONTH)+1 - mes;	
		}else{
			Integer intMesesEntreAños = ((Calendar.getInstance().get(Calendar.YEAR) - anio)-1)*12;
			Integer intMesesInicio = 12 - mes;
			intDiferenciaMeses = intMesesEntreAños + intMesesInicio + Calendar.getInstance().get(Calendar.MONTH)+1;
		}
		bdDiferenciaMes = new BigDecimal(intDiferenciaMeses);
	}
	public void limpiarCampos(){
		log.info("-------------------------------------Debugging EstadoCuentaController.limpiarCampos-------------------------------------");
		//SHOW PANELS
		blnShowPanelDatosDelSocioYCuentas = Boolean.FALSE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE;
		//OBJETOS
		socioComp = null;
		//LISTAS


		listaRazonSocialSucursal = new ArrayList<Juridica>();
		lstCtasSocBusqueda = new ArrayList<CuentaComp>();
		
			//new ArrayList<SocioComp>();
		/*************************** INTEGER & STRING **************************/
		strIdPersonaBusqueda = "";

		intTipoBusqueda = 3;
		intTipoCuentaBusqueda = 1;
		
		strFechaRegistro = "";

		/****************************** BIGDECIMAL *****************************/
//		bdMontoSaldoUltRegistro = BigDecimal.ZERO;
		bdDiferenciaMes = BigDecimal.ZERO;
		bdCucoSaldoRetiro = BigDecimal.ZERO;
		bdCucoSaldoAporte = BigDecimal.ZERO;
		bdMontoConceptoAporte = BigDecimal.ZERO;
		bdMontoSaldoSepelio = BigDecimal.ZERO;
		bdDiferencia = BigDecimal.ZERO;
		bdSumatoriaSaldo = BigDecimal.ZERO;
		bdMontoUltimoEnvio = BigDecimal.ZERO;
		intShowPanel = 0;
	}
	
	public void imprimirReporte(){
		String strNombreReporte = "";
//		dataBeanEstCtaResumen = new DataBeanEstCtaResumen();
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
			parametro.put("strSocioCliente", cuentaComp.getCuentaIntegrante().getId().getIntPersonaIntegrante()+" - "+socioComp.getSocio().getStrApePatSoc()+" "+socioComp.getSocio().getStrApeMatSoc()+", "+socioComp.getSocio().getStrNombreSoc());
			//Condicion:
			for (Tabla condSocio : listaDescripcionCondicionSocio) {
				if(condSocio.getIntIdDetalle().equals(cuentaComp.getCuenta().getIntParaCondicionCuentaCod())){
					strCondSocio = condSocio.getStrDescripcion();
					break;
				}
			}			
			for (Tabla tipoCondSocio : listaDescripcionTipoCondicionSocio) {
				if(tipoCondSocio.getIntIdDetalle().equals(cuentaComp.getCuenta().getIntParaSubCondicionCuentaCod())){
					strTipoCondSocio = tipoCondSocio.getStrDescripcion();
					break;
				}
			}			
			parametro.put("strCondicion", strCondSocio+" - "+strTipoCondSocio);
			//Apertura Cuenta:
			parametro.put("strFecAperturaCta", strFechaRegistro);
			//Sucursal
			//parametro.put("listaSucursal", listaRazonSocialSucursal);
			//Unidad Ejecutora
			//parametro.put("listaUnidadEjecutora", listaUnidadEjecutora);
			//Cta.Aporte
			parametro.put("strCtaAporte", convertirMontos(bdMontoConceptoAporte));
			//Saldo Aporte
			parametro.put("strSaldoAporte", convertirMontos(bdCucoSaldoAporte));
			//Fondo de Retiro
			parametro.put("strSaldoFdoRetiro", convertirMontos(bdCucoSaldoRetiro));
			//Pendiente - Fondo Sepelio
			parametro.put("strPteFdoSepelio", convertirMontos(bdMontoSaldoSepelio));
			//Pendiente - Mantenimiento
			parametro.put("strPteMantenimiento", convertirMontos(bdMontoSaldoMantenimiento));
			//Nro. de Cuenta
			parametro.put("strNumeroCuenta", cuentaComp.getCuenta().getStrNumeroCuenta());
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
				if(mes.getIntIdDetalle().equals(intMesPeriodoTemp)){
					strMes = mes.getStrDescripcion();
					break;
				}
			}
			parametro.put("strUltEnvPeriodo", strMes+" - "+intAnioPeriodoTemp);		
			//Sumatoria Ultimo Envio
			parametro.put("strUltEnvSumatoria", convertirMontos(bdSumatoriaUltimoEnvio));
			//Sumatoria Saldos
			parametro.put("strSaldosSumatoria", convertirMontos(bdSumatoriaSaldo));
			
			//MOMENTANEO
			//Sucursal
			for (Juridica sucur : listaRazonSocialSucursal) {
				strSucursal = strSucursal+"; "+sucur.getStrRazonSocial();
				break;
			}
			parametro.put("strSucursal", strSucursal);
			//Unidad Ejecutora
			for (Tabla unidEjec : listaUnidadEjecutora) {
				strUnidadEjec = strUnidadEjec+"; "+unidEjec.getStrAbreviatura();
			}
			parametro.put("strUnidadEjec", strUnidadEjec);
			//Cuentas Liquidadas
			for (CuentaComp ctaLiq : listaCuentaComp) {
				strNroCtaLiq = ctaLiq.getCuenta().getStrNumeroCuenta();
				strFchLiqCta = ctaLiq.getStrFechaRenuncia();
			}
			parametro.put("strNroCtaLiq", strNroCtaLiq);
			parametro.put("strFchLiqCta", strFchLiqCta);
			
			if (intShowPanel.equals(Constante.PARAM_T_ESTCTA_RESUMEN)) {
				strNombreReporte = "report_EstCta_Resumen";
				Reporteador reporte = new Reporteador();
				reporte.executeReport(strNombreReporte, parametro);
			}
			if (intShowPanel.equals(Constante.PARAM_T_ESTCTA_DETALLE)) {
				strNombreReporte = "report_EstCta_Detalle";
				for (EstadoCuentaComp lstSumAcumMeses : listaSumatoriaEstadoCuentaComp) {
					parametro.put("strSumAcumMesesFecha", lstSumAcumMeses.getStrFecha());
					parametro.put("strSumAcumMesesDescripcion", lstSumAcumMeses.getStrDescripcion());
					parametro.put("strSumAcumMesesMontoMovAportes", lstSumAcumMeses.getStrMontoMovAportes());
					parametro.put("strSumAcumMesesMontoMovPrestamos", lstSumAcumMeses.getStrMontoMovPrestamos());
					parametro.put("strSumAcumMesesMontoMovCreditos", lstSumAcumMeses.getStrMontoMovCreditos());
					parametro.put("strSumAcumMesesMontoMovInteres", lstSumAcumMeses.getStrMontoMovInteres());
					parametro.put("strSumAcumMesesMontoMovMntCuenta", lstSumAcumMeses.getStrMontoMovMntCuenta());
					parametro.put("strSumAcumMesesMontoMovActividad", lstSumAcumMeses.getStrMontoMovActividad());
					parametro.put("strSumAcumMesesStrMontoMovMulta", lstSumAcumMeses.getStrMontoMovMulta());
					parametro.put("strSumAcumMesesMontoMovFdoSepelio", lstSumAcumMeses.getStrMontoMovFdoSepelio());
					parametro.put("strSumAcumMesesMontoMovFdoRetiro", lstSumAcumMeses.getStrMontoMovFdoRetiro());
					parametro.put("strSumAcumMesesMontoMovCtaXPagar", lstSumAcumMeses.getStrMontoMovCtaXPagar());
					parametro.put("strSumAcumMesesMontoMovCtaXCobrar", lstSumAcumMeses.getStrMontoMovCtaXCobrar());
					break;
				}
				parametro.put("strSumatoriaMontoMovAportes", strSumatoriaMontoMovAportes);
				parametro.put("strSumatoriaMontoMovPrestamos", strSumatoriaMontoMovPrestamos);
				parametro.put("strSumatoriaMontoMovCreditos", strSumatoriaMontoMovCreditos);
				parametro.put("strSumatoriaMontoMovActividad", strSumatoriaMontoMovActividad);
				parametro.put("strSumatoriaMontoMovMulta", strSumatoriaMontoMovMulta);
				parametro.put("strSumatoriaMontoMovFdoRetiro", strSumatoriaMontoMovFdoRetiro);
				
				ReporterEstCtaTabDetalle reporte = new ReporterEstCtaTabDetalle();
				reporte.executeReport(strNombreReporte, parametro);
			}
			
			
		} catch (Exception e) {
			log.error("Error en imprimirReporteConJavaBean ---> "+e);
		}
		 
	}
	private void limpiarFormulario() {
		//Show Panel's
		blnShowPanelDatosDelSocioYCuentas = Boolean.FALSE;
		blnShowPanelResumenEstadoCta = Boolean.FALSE;
		blnShowPanelTabDetalle = Boolean.FALSE;
		blnShowPanelTabGestiones = Boolean.FALSE;
		blnShowPanelTabTerceros = Boolean.FALSE;
		blnShowPanelTabPrestamos = Boolean.FALSE;
		blnShowPanelTabPlanilla = Boolean.FALSE;
		blnShowPanelTabPrevisionSocial = Boolean.FALSE;
		
		
		listaSocioBusquedaPorApeNom.clear();
		listaRazonSocialSucursal.clear();
		lstCtasSocBusqueda.clear();
		
		intTipoBusqueda = 3;
		intAnioBusqueda = 0;
		intMesBusqueda = 0;
		
		strIdPersonaBusqueda = null;
		
		socioComp = null;
		
		//<h:outputLabel value="#{estadoCuentaController.inicioPage}"/>
	}
	
	/** Getters y Setters **/
	protected HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}
	
	public String getInicioPage() {		
		limpiarFormulario();
		return "";
	}
	
	public List<SelectItem> getListYears(){
		List<SelectItem> listYearsTemp = new ArrayList<SelectItem>(); 
		try {
			int year=Calendar.getInstance().get(Calendar.YEAR)+1;
			int cont=0;

			for(int j=year; j>=2013; j--){
				cont++;
			}
			
			for(int i=0; i<cont; i++){
				listYearsTemp.add(i, new SelectItem(year));
				year--;
			}	
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		this.listYears = listYearsTemp;
		
		return listYears;
	}
	
	public void setListYears(List<SelectItem> listYears) {
		this.listYears = listYears;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		EstadoCuentaController.log = log;
	}



	public SocioComp getSocioComp() {
		return socioComp;
	}

	public void setSocioComp(SocioComp socioComp) {
		this.socioComp = socioComp;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public CuentaComp getCuentaComp() {
		return cuentaComp;
	}

	public void setCuentaComp(CuentaComp cuentaComp) {
		this.cuentaComp = cuentaComp;
	}






	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public Expediente getExpedienteMov() {
		return expedienteMov;
	}

	public void setExpedienteMov(Expediente expedienteMov) {
		this.expedienteMov = expedienteMov;
	}


	public Efectuado getEfectMov() {
		return efectMov;
	}

	public void setEfectMov(Efectuado efectMov) {
		this.efectMov = efectMov;
	}

	public SocioFacadeLocal getSocioFacade() {
		return socioFacade;
	}

	public void setSocioFacade(SocioFacadeLocal socioFacade) {
		this.socioFacade = socioFacade;
	}

	public EstadoCtaFacadeLocal getEstadoCtaFacade() {
		return estadoCtaFacade;
	}

	public void setEstadoCtaFacade(EstadoCtaFacadeLocal estadoCtaFacade) {
		this.estadoCtaFacade = estadoCtaFacade;
	}

	public CreditoFacadeLocal getCreditoFacade() {
		return creditoFacade;
	}

	public void setCreditoFacade(CreditoFacadeLocal creditoFacade) {
		this.creditoFacade = creditoFacade;
	}

	public PersonaFacadeRemote getPersonaFacade() {
		return personaFacade;
	}

	public void setPersonaFacade(PersonaFacadeRemote personaFacade) {
		this.personaFacade = personaFacade;
	}

	public ConceptoFacadeRemote getConceptoFacade() {
		return conceptoFacade;
	}

	public void setConceptoFacade(ConceptoFacadeRemote conceptoFacade) {
		this.conceptoFacade = conceptoFacade;
	}

	public EmpresaFacadeRemote getEmpresaFacade() {
		return empresaFacade;
	}

	public void setEmpresaFacade(EmpresaFacadeRemote empresaFacade) {
		this.empresaFacade = empresaFacade;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public SolicitudPrestamoFacadeNuevoRemote getSolicitudPrestamoFacade() {
		return solicitudPrestamoFacade;
	}

	public void setSolicitudPrestamoFacade(
			SolicitudPrestamoFacadeNuevoRemote solicitudPrestamoFacade) {
		this.solicitudPrestamoFacade = solicitudPrestamoFacade;
	}

	public PlanillaFacadeRemote getPlanillaFacade() {
		return planillaFacade;
	}

	public void setPlanillaFacade(PlanillaFacadeRemote planillaFacade) {
		this.planillaFacade = planillaFacade;
	}


	public List<CuentaComp> getListaCuentaComp() {
		return listaCuentaComp;
	}

	public void setListaCuentaComp(List<CuentaComp> listaCuentaComp) {
		this.listaCuentaComp = listaCuentaComp;
	}

	public List<CuentaComp> getLstCtasSocBusqueda() {
		return lstCtasSocBusqueda;
	}

	public void setLstCtasSocBusqueda(List<CuentaComp> lstCtasSocBusqueda) {
		this.lstCtasSocBusqueda = lstCtasSocBusqueda;
	}




	public List<Juridica> getListaRazonSocialSucursal() {
		return listaRazonSocialSucursal;
	}

	public void setListaRazonSocialSucursal(List<Juridica> listaRazonSocialSucursal) {
		this.listaRazonSocialSucursal = listaRazonSocialSucursal;
	}

	public List<ExpedienteCredito> getListaExpedienteCredito() {
		return listaExpedienteCredito;
	}

	public void setListaExpedienteCredito(
			List<ExpedienteCredito> listaExpedienteCredito) {
		this.listaExpedienteCredito = listaExpedienteCredito;
	}

	public List<Cronograma> getListaCronograma() {
		return listaCronograma;
	}

	public void setListaCronograma(List<Cronograma> listaCronograma) {
		this.listaCronograma = listaCronograma;
	}

	public List<EstadoCredito> getListaEstadoCredito() {
		return listaEstadoCredito;
	}

	public void setListaEstadoCredito(List<EstadoCredito> listaEstadoCredito) {
		this.listaEstadoCredito = listaEstadoCredito;
	}

	public List<Envioconcepto> getListaEnvioconcepto() {
		return listaEnvioconcepto;
	}

	public void setListaEnvioconcepto(List<Envioconcepto> listaEnvioconcepto) {
		this.listaEnvioconcepto = listaEnvioconcepto;
	}

	public List<Envioconcepto> getListaEnvCptoPorCtaCptoDetYPer() {
		return listaEnvCptoPorCtaCptoDetYPer;
	}

	public void setListaEnvCptoPorCtaCptoDetYPer(
			List<Envioconcepto> listaEnvCptoPorCtaCptoDetYPer) {
		this.listaEnvCptoPorCtaCptoDetYPer = listaEnvCptoPorCtaCptoDetYPer;
	}

	public List<Enviomonto> getListaEnviomonto() {
		return listaEnviomonto;
	}

	public void setListaEnviomonto(List<Enviomonto> listaEnviomonto) {
		this.listaEnviomonto = listaEnviomonto;
	}

	public List<Efectuado> getListaEfectuado() {
		return listaEfectuado;
	}

	public void setListaEfectuado(List<Efectuado> listaEfectuado) {
		this.listaEfectuado = listaEfectuado;
	}

	public List<EfectuadoConcepto> getListaEfectuadoConcepto() {
		return listaEfectuadoConcepto;
	}

	public void setListaEfectuadoConcepto(
			List<EfectuadoConcepto> listaEfectuadoConcepto) {
		this.listaEfectuadoConcepto = listaEfectuadoConcepto;
	}

	public List<EstadoCuentaComp> getListaDatosGrillaPrestamosDetalle() {
		return listaDatosGrillaPrestamosDetalle;
	}

	public void setListaDatosGrillaPrestamosDetalle(
			List<EstadoCuentaComp> listaDatosGrillaPrestamosDetalle) {
		this.listaDatosGrillaPrestamosDetalle = listaDatosGrillaPrestamosDetalle;
	}

	public List<Movimiento> getListaMovCtaCtoXCtaCto() {
		return listaMovCtaCtoXCtaCto;
	}

	public void setListaMovCtaCtoXCtaCto(List<Movimiento> listaMovCtaCtoXCtaCto) {
		this.listaMovCtaCtoXCtaCto = listaMovCtaCtoXCtaCto;
	}

	public List<Movimiento> getListaMovCtaCtoXExpCred() {
		return listaMovCtaCtoXExpCred;
	}

	public void setListaMovCtaCtoXExpCred(List<Movimiento> listaMovCtaCtoXExpCred) {
		this.listaMovCtaCtoXExpCred = listaMovCtaCtoXExpCred;
	}

	public List<EstadoCuentaComp> getListaEstadoCuentaComp() {
		return listaEstadoCuentaComp;
	}

	public void setListaEstadoCuentaComp(
			List<EstadoCuentaComp> listaEstadoCuentaComp) {
		this.listaEstadoCuentaComp = listaEstadoCuentaComp;
	}

	public List<EstadoCuentaComp> getListaSumatoriaEstadoCuentaComp() {
		return listaSumatoriaEstadoCuentaComp;
	}

	public void setListaSumatoriaEstadoCuentaComp(
			List<EstadoCuentaComp> listaSumatoriaEstadoCuentaComp) {
		this.listaSumatoriaEstadoCuentaComp = listaSumatoriaEstadoCuentaComp;
	}

	public List<Tabla> getListaDescripcionModalidadPlanilla() {
		return listaDescripcionModalidadPlanilla;
	}

	public void setListaDescripcionModalidadPlanilla(
			List<Tabla> listaDescripcionModalidadPlanilla) {
		this.listaDescripcionModalidadPlanilla = listaDescripcionModalidadPlanilla;
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

	public List<Tabla> getListaDescripcionTipoMovimiento() {
		return listaDescripcionTipoMovimiento;
	}

	public void setListaDescripcionTipoMovimiento(
			List<Tabla> listaDescripcionTipoMovimiento) {
		this.listaDescripcionTipoMovimiento = listaDescripcionTipoMovimiento;
	}

	public List<Tabla> getListaDescripcionTipoCargoAbono() {
		return listaDescripcionTipoCargoAbono;
	}

	public void setListaDescripcionTipoCargoAbono(
			List<Tabla> listaDescripcionTipoCargoAbono) {
		this.listaDescripcionTipoCargoAbono = listaDescripcionTipoCargoAbono;
	}

	public List<Tabla> getListaUnidadEjecutora() {
		return listaUnidadEjecutora;
	}

	public void setListaUnidadEjecutora(List<Tabla> listaUnidadEjecutora) {
		this.listaUnidadEjecutora = listaUnidadEjecutora;
	}

	public List<GarantiaCreditoComp> getListaGarantiaCredito() {
		return listaGarantiaCredito;
	}

	public void setListaGarantiaCredito(
			List<GarantiaCreditoComp> listaGarantiaCredito) {
		this.listaGarantiaCredito = listaGarantiaCredito;
	}

	public List<EstadoCuentaComp> getListaGrillaTabPlanilla() {
		return listaGrillaTabPlanilla;
	}

	public void setListaGrillaTabPlanilla(
			List<EstadoCuentaComp> listaGrillaTabPlanilla) {
		this.listaGrillaTabPlanilla = listaGrillaTabPlanilla;
	}

	public Formatter getFmt() {
		return fmt;
	}

	public void setFmt(Formatter fmt) {
		this.fmt = fmt;
	}

	public SimpleDateFormat getFormatoDeFecha() {
		return formatoDeFecha;
	}

	public void setFormatoDeFecha(SimpleDateFormat formatoDeFecha) {
		this.formatoDeFecha = formatoDeFecha;
	}

	public String getStrFechaYHoraActual() {
		return strFechaYHoraActual;
	}

	public void setStrFechaYHoraActual(String strFechaYHoraActual) {
		this.strFechaYHoraActual = strFechaYHoraActual;
	}

	public Boolean getBlnShowPanelDatosDelSocioYCuentas() {
		return blnShowPanelDatosDelSocioYCuentas;
	}

	public void setBlnShowPanelDatosDelSocioYCuentas(
			Boolean blnShowPanelDatosDelSocioYCuentas) {
		this.blnShowPanelDatosDelSocioYCuentas = blnShowPanelDatosDelSocioYCuentas;
	}

	public Boolean getBlnShowPanelResumenEstadoCta() {
		return blnShowPanelResumenEstadoCta;
	}

	public void setBlnShowPanelResumenEstadoCta(Boolean blnShowPanelResumenEstadoCta) {
		this.blnShowPanelResumenEstadoCta = blnShowPanelResumenEstadoCta;
	}

	public Boolean getBlnShowPanelTabDetalle() {
		return blnShowPanelTabDetalle;
	}

	public void setBlnShowPanelTabDetalle(Boolean blnShowPanelTabDetalle) {
		this.blnShowPanelTabDetalle = blnShowPanelTabDetalle;
	}

	public Boolean getBlnShowPanelTabPrestamosResumePrestamos() {
		return blnShowPanelTabPrestamosResumePrestamos;
	}

	public void setBlnShowPanelTabPrestamosResumePrestamos(
			Boolean blnShowPanelTabPrestamosResumePrestamos) {
		this.blnShowPanelTabPrestamosResumePrestamos = blnShowPanelTabPrestamosResumePrestamos;
	}

	public Boolean getBlnShowPanelTabPrestamosGarantizados() {
		return blnShowPanelTabPrestamosGarantizados;
	}

	public void setBlnShowPanelTabPrestamosGarantizados(
			Boolean blnShowPanelTabPrestamosGarantizados) {
		this.blnShowPanelTabPrestamosGarantizados = blnShowPanelTabPrestamosGarantizados;
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

	public Integer getIntTipoCreditoBusqueda() {
		return intTipoCreditoBusqueda;
	}

	public void setIntTipoCreditoBusqueda(Integer intTipoCreditoBusqueda) {
		this.intTipoCreditoBusqueda = intTipoCreditoBusqueda;
	}

	public Integer getIntEstadoEstCtaBusqueda() {
		return intEstadoEstCtaBusqueda;
	}

	public void setIntEstadoEstCtaBusqueda(Integer intEstadoEstCtaBusqueda) {
		this.intEstadoEstCtaBusqueda = intEstadoEstCtaBusqueda;
	}

	public String getStrFechaRegistro() {
		return strFechaRegistro;
	}

	public void setStrFechaRegistro(String strFechaRegistro) {
		this.strFechaRegistro = strFechaRegistro;
	}

	public BigDecimal getBdCucoSaldoAporte() {
		return bdCucoSaldoAporte;
	}

	public void setBdCucoSaldoAporte(BigDecimal bdCucoSaldoAporte) {
		this.bdCucoSaldoAporte = bdCucoSaldoAporte;
	}

	public BigDecimal getBdMontoConceptoAporte() {
		return bdMontoConceptoAporte;
	}

	public void setBdMontoConceptoAporte(BigDecimal bdMontoConceptoAporte) {
		this.bdMontoConceptoAporte = bdMontoConceptoAporte;
	}

	public BigDecimal getBdMontoSaldoSepelio() {
		return bdMontoSaldoSepelio;
	}

	public void setBdMontoSaldoSepelio(BigDecimal bdMontoSaldoSepelio) {
		this.bdMontoSaldoSepelio = bdMontoSaldoSepelio;
	}

	public BigDecimal getBdMontoSaldoMantenimiento() {
		return bdMontoSaldoMantenimiento;
	}

	public void setBdMontoSaldoMantenimiento(BigDecimal bdMontoSaldoMantenimiento) {
		this.bdMontoSaldoMantenimiento = bdMontoSaldoMantenimiento;
	}

	public BigDecimal getBdCucoSaldoRetiro() {
		return bdCucoSaldoRetiro;
	}

	public void setBdCucoSaldoRetiro(BigDecimal bdCucoSaldoRetiro) {
		this.bdCucoSaldoRetiro = bdCucoSaldoRetiro;
	}



	public BigDecimal getBdDiferenciaMes() {
		return bdDiferenciaMes;
	}

	public void setBdDiferenciaMes(BigDecimal bdDiferenciaMes) {
		this.bdDiferenciaMes = bdDiferenciaMes;
	}

	public BigDecimal getBdMontoUltimoEnvio() {
		return bdMontoUltimoEnvio;
	}

	public void setBdMontoUltimoEnvio(BigDecimal bdMontoUltimoEnvio) {
		this.bdMontoUltimoEnvio = bdMontoUltimoEnvio;
	}


	public BigDecimal getBdSumatoriaSaldo() {
		return bdSumatoriaSaldo;
	}

	public void setBdSumatoriaSaldo(BigDecimal bdSumatoriaSaldo) {
		this.bdSumatoriaSaldo = bdSumatoriaSaldo;
	}

	public Integer getPeriodoTemp() {
		return periodoTemp;
	}

	public void setPeriodoTemp(Integer periodoTemp) {
		this.periodoTemp = periodoTemp;
	}

	public Integer getIntAnioPeriodoTemp() {
		return intAnioPeriodoTemp;
	}

	public void setIntAnioPeriodoTemp(Integer intAnioPeriodoTemp) {
		this.intAnioPeriodoTemp = intAnioPeriodoTemp;
	}

	public Integer getIntMesPeriodoTemp() {
		return intMesPeriodoTemp;
	}

	public void setIntMesPeriodoTemp(Integer intMesPeriodoTemp) {
		this.intMesPeriodoTemp = intMesPeriodoTemp;
	}

	public String getStrFechaEstado() {
		return strFechaEstado;
	}

	public void setStrFechaEstado(String strFechaEstado) {
		this.strFechaEstado = strFechaEstado;
	}

	public String getStrDescripcion() {
		return strDescripcion;
	}

	public void setStrDescripcion(String strDescripcion) {
		this.strDescripcion = strDescripcion;
	}

	public BigDecimal getBdTasaInteres() {
		return bdTasaInteres;
	}

	public void setBdTasaInteres(BigDecimal bdTasaInteres) {
		this.bdTasaInteres = bdTasaInteres;
	}

	public BigDecimal getBdMontoTotal() {
		return bdMontoTotal;
	}

	public void setBdMontoTotal(BigDecimal bdMontoTotal) {
		this.bdMontoTotal = bdMontoTotal;
	}

	public BigDecimal getBdSaldoCredito() {
		return bdSaldoCredito;
	}

	public void setBdSaldoCredito(BigDecimal bdSaldoCredito) {
		this.bdSaldoCredito = bdSaldoCredito;
	}

	public BigDecimal getBdDiferencia() {
		return bdDiferencia;
	}

	public void setBdDiferencia(BigDecimal bdDiferencia) {
		this.bdDiferencia = bdDiferencia;
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

	public BigDecimal getBdMontoMovPrestamos() {
		return bdMontoMovPrestamos;
	}

	public void setBdMontoMovPrestamos(BigDecimal bdMontoMovPrestamos) {
		this.bdMontoMovPrestamos = bdMontoMovPrestamos;
	}

	public BigDecimal getBdMontoMovCreditos() {
		return bdMontoMovCreditos;
	}

	public void setBdMontoMovCreditos(BigDecimal bdMontoMovCreditos) {
		this.bdMontoMovCreditos = bdMontoMovCreditos;
	}

	public BigDecimal getBdMontoMovActividad() {
		return bdMontoMovActividad;
	}

	public void setBdMontoMovActividad(BigDecimal bdMontoMovActividad) {
		this.bdMontoMovActividad = bdMontoMovActividad;
	}

	public BigDecimal getBdMontoMovInteres() {
		return bdMontoMovInteres;
	}

	public void setBdMontoMovInteres(BigDecimal bdMontoMovInteres) {
		this.bdMontoMovInteres = bdMontoMovInteres;
	}

	public BigDecimal getBdMontoMovMulta() {
		return bdMontoMovMulta;
	}

	public void setBdMontoMovMulta(BigDecimal bdMontoMovMulta) {
		this.bdMontoMovMulta = bdMontoMovMulta;
	}

	public BigDecimal getBdMontoMovAportes() {
		return bdMontoMovAportes;
	}

	public void setBdMontoMovAportes(BigDecimal bdMontoMovAportes) {
		this.bdMontoMovAportes = bdMontoMovAportes;
	}

	public BigDecimal getBdMontoMovMntCuenta() {
		return bdMontoMovMntCuenta;
	}

	public void setBdMontoMovMntCuenta(BigDecimal bdMontoMovMntCuenta) {
		this.bdMontoMovMntCuenta = bdMontoMovMntCuenta;
	}

	public BigDecimal getBdMontoMovFdoSepelio() {
		return bdMontoMovFdoSepelio;
	}

	public void setBdMontoMovFdoSepelio(BigDecimal bdMontoMovFdoSepelio) {
		this.bdMontoMovFdoSepelio = bdMontoMovFdoSepelio;
	}

	public BigDecimal getBdMontoMovFdoRetiro() {
		return bdMontoMovFdoRetiro;
	}

	public void setBdMontoMovFdoRetiro(BigDecimal bdMontoMovFdoRetiro) {
		this.bdMontoMovFdoRetiro = bdMontoMovFdoRetiro;
	}

	public BigDecimal getBdMontoMovCtaXPagar() {
		return bdMontoMovCtaXPagar;
	}

	public void setBdMontoMovCtaXPagar(BigDecimal bdMontoMovCtaXPagar) {
		this.bdMontoMovCtaXPagar = bdMontoMovCtaXPagar;
	}

	public BigDecimal getBdMontoMovCtaXCobrar() {
		return bdMontoMovCtaXCobrar;
	}

	public void setBdMontoMovCtaXCobrar(BigDecimal bdMontoMovCtaXCobrar) {
		this.bdMontoMovCtaXCobrar = bdMontoMovCtaXCobrar;
	}

	public String getStrSumatoriaMontoMovPrestamos() {
		return strSumatoriaMontoMovPrestamos;
	}

	public void setStrSumatoriaMontoMovPrestamos(
			String strSumatoriaMontoMovPrestamos) {
		this.strSumatoriaMontoMovPrestamos = strSumatoriaMontoMovPrestamos;
	}

	public String getStrSumatoriaMontoMovCreditos() {
		return strSumatoriaMontoMovCreditos;
	}

	public void setStrSumatoriaMontoMovCreditos(String strSumatoriaMontoMovCreditos) {
		this.strSumatoriaMontoMovCreditos = strSumatoriaMontoMovCreditos;
	}

	public String getStrSumatoriaMontoMovActividad() {
		return strSumatoriaMontoMovActividad;
	}

	public void setStrSumatoriaMontoMovActividad(
			String strSumatoriaMontoMovActividad) {
		this.strSumatoriaMontoMovActividad = strSumatoriaMontoMovActividad;
	}

	public String getStrSumatoriaMontoMovMulta() {
		return strSumatoriaMontoMovMulta;
	}

	public void setStrSumatoriaMontoMovMulta(String strSumatoriaMontoMovMulta) {
		this.strSumatoriaMontoMovMulta = strSumatoriaMontoMovMulta;
	}

	public String getStrSumatoriaMontoMovAportes() {
		return strSumatoriaMontoMovAportes;
	}

	public void setStrSumatoriaMontoMovAportes(String strSumatoriaMontoMovAportes) {
		this.strSumatoriaMontoMovAportes = strSumatoriaMontoMovAportes;
	}

	public String getStrSumatoriaMontoMovFdoRetiro() {
		return strSumatoriaMontoMovFdoRetiro;
	}

	public void setStrSumatoriaMontoMovFdoRetiro(
			String strSumatoriaMontoMovFdoRetiro) {
		this.strSumatoriaMontoMovFdoRetiro = strSumatoriaMontoMovFdoRetiro;
	}

	public String getStrSumatoriaFilas() {
		return strSumatoriaFilas;
	}

	public void setStrSumatoriaFilas(String strSumatoriaFilas) {
		this.strSumatoriaFilas = strSumatoriaFilas;
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

	public Boolean getBlnShowPanelTabPrestamos() {
		return blnShowPanelTabPrestamos;
	}

	public void setBlnShowPanelTabPrestamos(Boolean blnShowPanelTabPrestamos) {
		this.blnShowPanelTabPrestamos = blnShowPanelTabPrestamos;
	}

	public Boolean getBlnShowPanelTabPlanilla() {
		return blnShowPanelTabPlanilla;
	}

	public void setBlnShowPanelTabPlanilla(Boolean blnShowPanelTabPlanilla) {
		this.blnShowPanelTabPlanilla = blnShowPanelTabPlanilla;
	}


	public List<EnviadoEfectuadoComp> getListaMontoPorPeriodoYPrioridad() {
		return listaMontoPorPeriodoYPrioridad;
	}

	public void setListaMontoPorPeriodoYPrioridad(
			List<EnviadoEfectuadoComp> lstEnviadoEfectuadoComp) {
		this.listaMontoPorPeriodoYPrioridad = lstEnviadoEfectuadoComp;
	}

	public List<PrioridadDescuento> getListaGralPrioridadDescuento() {
		return listaGralPrioridadDescuento;
	}

	public void setListaGralPrioridadDescuento(
			List<PrioridadDescuento> listaGralPrioridadDescuento) {
		this.listaGralPrioridadDescuento = listaGralPrioridadDescuento;
	}

	public List<EnviadoEfectuadoComp> getLstColumnasPrioridadDscto() {
		return lstColumnasPrioridadDscto;
	}

	public void setLstColumnasPrioridadDscto(
			List<EnviadoEfectuadoComp> lstColumnasPrioridadDscto) {
		this.lstColumnasPrioridadDscto = lstColumnasPrioridadDscto;
	}

	public List<Descuento> getListaMontoDsctoTerceros() {
		return listaMontoDsctoTerceros;
	}

	public void setListaMontoDsctoTerceros(List<Descuento> listaMontoDsctoTerceros) {
		this.listaMontoDsctoTerceros = listaMontoDsctoTerceros;
	}

	public List<Descuento> getListaMontoDsctoTrosHaberes() {
		return listaMontoDsctoTrosHaberes;
	}

	public void setListaMontoDsctoTrosHaberes(
			List<Descuento> listaMontoDsctoTrosHaberes) {
		this.listaMontoDsctoTrosHaberes = listaMontoDsctoTrosHaberes;
	}

	public List<Descuento> getListaNvoColumnasHaberes() {
		return listaNvoColumnasHaberes;
	}

	public void setListaNvoColumnasHaberes(List<Descuento> listaNvoColumnasHaberes) {
		this.listaNvoColumnasHaberes = listaNvoColumnasHaberes;
	}

	public List<Descuento> getListaMontoDsctoTrosIncentivos() {
		return listaMontoDsctoTrosIncentivos;
	}

	public void setListaMontoDsctoTrosIncentivos(
			List<Descuento> listaMontoDsctoTrosIncentivos) {
		this.listaMontoDsctoTrosIncentivos = listaMontoDsctoTrosIncentivos;
	}

	public List<Descuento> getListaNvoColumnasIncentivos() {
		return listaNvoColumnasIncentivos;
	}

	public void setListaNvoColumnasIncentivos(
			List<Descuento> listaNvoColumnasIncentivos) {
		this.listaNvoColumnasIncentivos = listaNvoColumnasIncentivos;
	}

	public List<Descuento> getListaMontoDsctoTrosCas() {
		return listaMontoDsctoTrosCas;
	}

	public void setListaMontoDsctoTrosCas(List<Descuento> listaMontoDsctoTrosCas) {
		this.listaMontoDsctoTrosCas = listaMontoDsctoTrosCas;
	}

	public List<Descuento> getListaNvoColumnasCas() {
		return listaNvoColumnasCas;
	}

	public void setListaNvoColumnasCas(List<Descuento> listaNvoColumnasCas) {
		this.listaNvoColumnasCas = listaNvoColumnasCas;
	}

	public Boolean getBlnShowPanelTabPlanillaResumen() {
		return blnShowPanelTabPlanillaResumen;
	}

	public void setBlnShowPanelTabPlanillaResumen(
			Boolean blnShowPanelTabPlanillaResumen) {
		this.blnShowPanelTabPlanillaResumen = blnShowPanelTabPlanillaResumen;
	}

	public Boolean getBlnShowPanelTabPlanillaDiferencia() {
		return blnShowPanelTabPlanillaDiferencia;
	}

	public void setBlnShowPanelTabPlanillaDiferencia(
			Boolean blnShowPanelTabPlanillaDiferencia) {
		this.blnShowPanelTabPlanillaDiferencia = blnShowPanelTabPlanillaDiferencia;
	}

	public List<EnviadoEfectuadoComp> getListaEnviadoEfectuadoComp() {
		return listaEnviadoEfectuadoComp;
	}

	public void setListaEnviadoEfectuadoComp(
			List<EnviadoEfectuadoComp> listaEnviadoEfectuadoComp) {
		this.listaEnviadoEfectuadoComp = listaEnviadoEfectuadoComp;
	}

	public List<ExpedienteComp> getListaExpedienteComp() {
		return listaExpedienteComp;
	}

	public void setListaExpedienteComp(List<ExpedienteComp> listaExpedienteComp) {
		this.listaExpedienteComp = listaExpedienteComp;
	}

	public List<SocioComp> getListaSocioBusquedaPorApeNom() {
		return listaSocioBusquedaPorApeNom;
	}

	public void setListaSocioBusquedaPorApeNom(
			List<SocioComp> listaSocioBusquedaPorApeNom) {
		this.listaSocioBusquedaPorApeNom = listaSocioBusquedaPorApeNom;
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

	public Boolean getBlnShowPanelTabPrevisionSocial() {
		return blnShowPanelTabPrevisionSocial;
	}

	public void setBlnShowPanelTabPrevisionSocial(
			Boolean blnShowPanelTabPrevisionSocial) {
		this.blnShowPanelTabPrevisionSocial = blnShowPanelTabPrevisionSocial;
	}

	public List<ExpedientePrevisionComp> getListaExpedientePrevisionComp() {
		return listaExpedientePrevisionComp;
	}

	public void setListaExpedientePrevisionComp(
			List<ExpedientePrevisionComp> listaExpedientePrevisionComp) {
		this.listaExpedientePrevisionComp = listaExpedientePrevisionComp;
	}
	
	public List<PrevisionSocialComp> getListaPrevSocialFdoSepelio() {
		return listaPrevSocialFdoSepelio;
	}

	public void setListaPrevSocialFdoSepelio(
			List<PrevisionSocialComp> listaPrevSocialFdoSepelio) {
		this.listaPrevSocialFdoSepelio = listaPrevSocialFdoSepelio;
	}

	public Integer getIntPgoCtaBeneficioFdoSepelio() {
		return intPgoCtaBeneficioFdoSepelio;
	}

	public void setIntPgoCtaBeneficioFdoSepelio(Integer intPgoCtaBeneficioFdoSepelio) {
		this.intPgoCtaBeneficioFdoSepelio = intPgoCtaBeneficioFdoSepelio;
	}

	public Integer getIntPgoCtaBeneficioFdoRetiro() {
		return intPgoCtaBeneficioFdoRetiro;
	}

	public void setIntPgoCtaBeneficioFdoRetiro(Integer intPgoCtaBeneficioFdoRetiro) {
		this.intPgoCtaBeneficioFdoRetiro = intPgoCtaBeneficioFdoRetiro;
	}

	public List<PrevisionSocialComp> getListaPrevSocialFdoRetiro() {
		return listaPrevSocialFdoRetiro;
	}

	public void setListaPrevSocialFdoRetiro(
			List<PrevisionSocialComp> listaPrevSocialFdoRetiro) {
		this.listaPrevSocialFdoRetiro = listaPrevSocialFdoRetiro;
	}

	public BigDecimal getBdSumatoriaSaldoPrestamo() {
		return bdSumatoriaSaldoPrestamo;
	}

	public void setBdSumatoriaSaldoPrestamo(BigDecimal bdSumatoriaSaldoPrestamo) {
		this.bdSumatoriaSaldoPrestamo = bdSumatoriaSaldoPrestamo;
	}

	public List<EnviadoEfectuadoComp> getListaEnvEfecPlanilla() {
		return listaEnvEfecPlanilla;
	}

	public void setListaEnvEfecPlanilla(
			List<EnviadoEfectuadoComp> listaEnvEfecPlanilla) {
		this.listaEnvEfecPlanilla = listaEnvEfecPlanilla;
	}

	public List<MovimientoEstCtaComp> getListaPrevSocialFdoSepelioDetalle() {
		return listaPrevSocialFdoSepelioDetalle;
	}

	public void setListaPrevSocialFdoSepelioDetalle(
			List<MovimientoEstCtaComp> listaPrevSocialFdoSepelioDetalle) {
		this.listaPrevSocialFdoSepelioDetalle = listaPrevSocialFdoSepelioDetalle;
	}

	public List<MovimientoEstCtaComp> getListaPrevSocialFdoRetiroDetalle() {
		return listaPrevSocialFdoRetiroDetalle;
	}

	public void setListaPrevSocialFdoRetiroDetalle(
			List<MovimientoEstCtaComp> listaPrevSocialFdoRetiroDetalle) {
		this.listaPrevSocialFdoRetiroDetalle = listaPrevSocialFdoRetiroDetalle;
	}
	
	protected String getRequestParameter(String name) {
		return (String) FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(name);
	}

	public PrevisionSocialComp getPrevisionSocialSelected() {
		return previsionSocialSelected;
	}

	public void setPrevisionSocialSelected(
			PrevisionSocialComp previsionSocialSelected) {
		this.previsionSocialSelected = previsionSocialSelected;
	}

	public SocioComp getSocioNaruralSelected() {
		return socioNaruralSelected;
	}

	public void setSocioNaruralSelected(SocioComp socioNaruralSelected) {
		this.socioNaruralSelected = socioNaruralSelected;
	}

	public List<GestionCobranza> getListaGestionCobranza() {
		return listaGestionCobranza;
	}

	public void setListaGestionCobranza(List<GestionCobranza> listaGestionCobranza) {
		this.listaGestionCobranza = listaGestionCobranza;
	}


	public List<GestionCobranzaSoc> getListaGestionCobranzaSocio() {
		return listaGestionCobranzaSocio;
	}

	public void setListaGestionCobranzaSocio(
			List<GestionCobranzaSoc> listaGestionCobranzaSocio) {
		this.listaGestionCobranzaSocio = listaGestionCobranzaSocio;
	}

	public Boolean getBlnShowPanelTabGestiones() {
		return blnShowPanelTabGestiones;
	}

	public void setBlnShowPanelTabGestiones(Boolean blnShowPanelTabGestiones) {
		this.blnShowPanelTabGestiones = blnShowPanelTabGestiones;
	}

	public BigDecimal getBdSumatoriaUltimoEnvio() {
		return bdSumatoriaUltimoEnvio;
	}

	public void setBdSumatoriaUltimoEnvio(BigDecimal bdSumatoriaUltimoEnvio) {
		this.bdSumatoriaUltimoEnvio = bdSumatoriaUltimoEnvio;
	}

	public List<DataBeanEstadoCuentaPrestamos> getLstDataBeanEstadoCuentaPrestamos() {
		return lstDataBeanEstadoCuentaPrestamos;
	}

	public void setLstDataBeanEstadoCuentaPrestamos(
			List<DataBeanEstadoCuentaPrestamos> lstDataBeanEstadoCuentaPrestamos) {
		this.lstDataBeanEstadoCuentaPrestamos = lstDataBeanEstadoCuentaPrestamos;
	}

	public DataBeanEstCtaResumen getDataBeanEstCtaResumen() {
		return dataBeanEstCtaResumen;
	}

	public void setDataBeanEstCtaResumen(DataBeanEstCtaResumen dataBeanEstCtaResumen) {
		this.dataBeanEstCtaResumen = dataBeanEstCtaResumen;
	}

	public EstructuraFacadeLocal getEstructuraFacade() {
		return estructuraFacade;
	}

	public void setEstructuraFacade(EstructuraFacadeLocal estructuraFacade) {
		this.estructuraFacade = estructuraFacade;
	}

	public List<Tabla> getListaDescripcionMes() {
		return listaDescripcionMes;
	}

	public void setListaDescripcionMes(List<Tabla> listaDescripcionMes) {
		this.listaDescripcionMes = listaDescripcionMes;
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

	public Integer getIntShowPanel() {
		return intShowPanel;
	}

	public void setIntShowPanel(Integer intShowPanel) {
		this.intShowPanel = intShowPanel;
	}

	public NumberFormat getFormato() {
		return formato;
	}

	public void setFormato(NumberFormat formato) {
		this.formato = formato;
	}
}

