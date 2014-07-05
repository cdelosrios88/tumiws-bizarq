package pe.com.tumi.presupuesto.core.controller;

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
import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.facade.PlanCuentaFacadeRemote;
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
import pe.com.tumi.presupuesto.core.domain.Presupuesto;
import pe.com.tumi.presupuesto.core.domain.PresupuestoId;
import pe.com.tumi.presupuesto.core.facade.PresupuestoFacadeLocal;

import pe.com.tumi.seguridad.empresa.facade.EmpresaFacadeRemote;
import pe.com.tumi.seguridad.login.domain.Usuario;

/*****************************************************************************
 * NOMBRE DE LA CLASE: PresupuestoController
 * FUNCIONALIDAD : CLASE QUE TIENE LOS PARAMETROS DE BUSQUEDA Y VALIDACIONES 
 * REF. : 
 * AUTOR : JUNIOR CHÁVEZ VALVERDE 
 * VERSIÓN : V1 
 * FECHA CREACIÓN : 23.10.2013 
 *****************************************************************************/

public class PresupuestoController {
	protected static Logger log;
	
	private Boolean blnShowPanelTabEnBloque;
	private Boolean blnShowPanelTabPorPartida;
	private Presupuesto beanPresupuesto;
	private TablaFacadeRemote tablaFacade;
	private AuditoriaFacadeRemote auditoriaFacade;
	private PlanCuentaFacadeRemote planCuentaFacade;
	
	//INICIO DE SESIÓN
	private Usuario usuarioSesion;
	private PresupuestoFacadeLocal presupuestoFacade;
	private Integer SESION_IDEMPRESA;
	private Integer SESION_IDUSUARIO;
	private Integer SESION_IDSUCURSAL;
	private Integer SESION_IDSUBSUCURSAL;
	private Integer intMesActual;
	private Integer intAnioActual;
	private Integer intDiaActual;
	
	//DESCRIPCION DE PARAMETROS
	private List<Tabla> listaDescripcionMes;
	private List<Tabla> listaMesesYaRegistrados;
	private List<Tabla> listaDescripcionEstado;	
	private Presupuesto presupuestoSelected;
	private Integer intTipoGrabacion;
	
	//FILTROS DE BÚSQUEDA
	private Integer intAnioBusqueda;
	private Integer intCboTipoCuentaBusq;
	private String strCuentaBusq;
	private Integer intIdSucursalBusqueda;
	private Integer intIdSubSucursalBusqueda;
	private List<SelectItem> listYears;
	private List<Presupuesto> listaPresupuestosPorFiltros;
	
	//FILTROS DE BÚSQUEDA EN POPUP ADD CUENTA CONTABLE
	private Integer mpIntTipoCuentaBusq;
	private Integer mpIntPeriodoBusq;
	private String mpStrCuentaBusq;
	private PlanCuenta planCuentaSelected;
	//private List<Sucursal> listJuridicaSucursalAll;
	
	//COMBOS DE SUCURSAL
	private List<Sucursal> listJuridicaSucursal;
	private List<Subsucursal> listJuridicaSubsucursalGrb;
	private List<Subsucursal> listJuridicaSubsucursalBusq;
	
	//GRABACON "POR PARTIDA"
	private Integer intAnioGrabacion;
	private Integer intTipoMoneda;
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
	
	//GRABACON "EN BLOQUE"
	private Integer intAnioBase;
	private Integer intAnioProyectado;
	private Integer intMesDesde;
	private Integer intMesHasta;
	private BigDecimal bdPorcentajeCrecimiento;
	private String rbCtaContable;
	private String strCuentaGrb;
	private String rbSucursal;
	private Integer intIdSucursalGrabacion;
	private Integer intIdSubSucursalGrabacion;
	private Integer intErrorPresupuestoPorAnioBase;
	private Integer intErrorPlanCuenta;
	private List<Presupuesto> listaPresupuestosDelAnioBase;
	private List<Presupuesto> listaValidaPresupuesto;
	private List<PlanCuenta> listaPlanCuenta;
	
	//DISABLED EN BLOQUE
	private Boolean blnDisabledSucursal;
	private Boolean blnDisabledSubSucursal;
	private Boolean blnDisabledAnioProy;
	private Boolean blnDisabledGrabar;
	private Boolean blnDisabledAddCtaContable;
	private Boolean blnDisabledTxtCuentaBusq;
	
	//DISABLED POR PARTIDA
	private Boolean blnDisabledAnio;
	private Boolean blnDisabledTipoMoneda;
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
	
	// MENSAJES DE ERROR
	private String strMsgTxtEstCierre;
	private String strMsgTxtPeriodo;
	private String strMsgTxtMes;
	private String strMsgTxtAddCtaContable;
	private String strMsgTxtSucursal;
	private String strMsgTxtSubSucursal;
	private String strMsgTxtExisteRegistro;
	private String strMsgTxtRangoMeses;
	private String strMsgTxtPorcentajeCrecimiento;
	private String strMsgTxtMonto;
	private String strMsgTxtTipoMoneda;
	
	public PresupuestoController(){
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
		//INICIALIZANDO SHOW PANEL's
		blnShowPanelTabPorPartida = Boolean.FALSE;
		blnShowPanelTabEnBloque = Boolean.FALSE;
		blnDisabledAnio = Boolean.FALSE;
		blnDisabledTipoMoneda = Boolean.FALSE;
		blnDisabledSucursal = Boolean.FALSE;
		blnDisabledSubSucursal = Boolean.FALSE;
		blnDisabledAnioProy = Boolean.TRUE;
		//
		intTipoGrabacion = 0;
		blnDisabledGrabar = Boolean.TRUE;
		//
		beanPresupuesto = new Presupuesto();
		beanPresupuesto.setId(new PresupuestoId());
		//INICIALIZANDO LISTA DE PRESUPUESTOS DEL AÑO BASE
		listaPresupuestosDelAnioBase = new ArrayList<Presupuesto>();
		listaPlanCuenta = new ArrayList<PlanCuenta>();
		listaMesesYaRegistrados = new ArrayList<Tabla>();
		//LLENANDO COMBOS DEL FORMULARIO
		getListAnios();
		getListSucursales();
		
		try {
			auditoriaFacade = (AuditoriaFacadeRemote)EJBFactory.getRemote(AuditoriaFacadeRemote.class);
			planCuentaFacade = (PlanCuentaFacadeRemote)EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			presupuestoFacade = (PresupuestoFacadeLocal)EJBFactory.getLocal(PresupuestoFacadeLocal.class);
			listaDescripcionMes = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_MES_CALENDARIO));
			listaDescripcionEstado = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_ESTADOCIERRE));			
		} catch (Exception e) {
			log.error("Error en PresupuestoController --> "+e);
		}
	}
	
	public void obtenerListaPresupuestosPorFiltros() {
		log.info("-------------------------------------Debugging PresupuestoController.obtenerListaPresupuestosPorFiltros-------------------------------------");
		listaPresupuestosPorFiltros = null;
		PresupuestoId ptoId = new PresupuestoId();
		try {
			ptoId.setIntEmpresaPresupuestoPk(SESION_IDEMPRESA);
			ptoId.setIntPeriodoPresupuesto(intAnioBusqueda==0?null:intAnioBusqueda);
			ptoId.setIntMesPresupuesto(null);
			if (intCboTipoCuentaBusq == 0) {
				ptoId.setIntEmpresaCuentaPk(null);
				ptoId.setIntPeriodoCuenta(null);
				ptoId.setStrNumeroCuenta(null);
			}else {
				ptoId.setIntEmpresaCuentaPk(SESION_IDEMPRESA);
				ptoId.setIntPeriodoCuenta(intAnioBusqueda==0?null:intAnioBusqueda);
				ptoId.setStrNumeroCuenta(strCuentaBusq.trim().isEmpty()?null:strCuentaBusq.trim());
			}				
			
			ptoId.setIntEmpresaSucursalPk(SESION_IDEMPRESA); //intIdSucursalBusqueda==0?null:SESION_IDEMPRESA
			ptoId.setIntIdSucursal(intIdSucursalBusqueda==0?null:intIdSucursalBusqueda);
			ptoId.setIntIdSubSucursal(intIdSubSucursalBusqueda==0?null:intIdSubSucursalBusqueda);
			
			listaPresupuestosPorFiltros = presupuestoFacade.getListaPresupuestoPorFiltros(ptoId,intCboTipoCuentaBusq);
		} catch (Exception e) {
			log.error("Error en obtenerListaPresupuestosPorFiltros --> "+e);
		}
	}
	//POPUP PARA ADICIONAR CUENTAS
	
	public void buscarPlanCuenta() throws BusinessException{
		log.info("-------------------------------------Debugging PresupuestoController.buscarPlanCuentas-------------------------------------");
		
		PlanCuenta beanCuentaBusq = new PlanCuenta();
		beanCuentaBusq.setId(new PlanCuentaId());
		beanCuentaBusq.getId().setIntEmpresaCuentaPk(SESION_IDEMPRESA);
		//Filtro combo para Descripcion, Numero de Cuenta
		if(getMpIntTipoCuentaBusq()!=null && getMpIntTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_DESCRIPCION)){//Por Descripción
			beanCuentaBusq.setStrDescripcion(getMpStrCuentaBusq().trim().isEmpty()?null:getMpStrCuentaBusq().trim());
		}else if(getMpIntTipoCuentaBusq()!=null && getMpIntTipoCuentaBusq().equals(Constante.PARAM_T_FILTROSELECTPLANCUENTAS_CUENTACONTABLE)){//Por Número de Cuenta
			beanCuentaBusq.getId().setStrNumeroCuenta(getMpStrCuentaBusq().trim().isEmpty()?null:getMpStrCuentaBusq().trim());
		}
		if(getIntAnioGrabacion()!=null && !getIntAnioGrabacion().equals(0))	beanCuentaBusq.getId().setIntPeriodoCuenta(getIntAnioGrabacion());
		
		List<PlanCuenta> lista = null;
		try {
			if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE)) {
				PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
				lista = planCuentaFacade.getBusqPorNroCtaDesc(beanCuentaBusq);
				log.info("listCuentaOrigenDestino.size: "+lista.size());
			}
			if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_POR_PARTIDA)) {
				//Filtro combo Tipo de Moneda
				if (intTipoMoneda.equals(0)) beanCuentaBusq.setIntIdentificadorExtranjero(null); //Identificador nulo
				if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) beanCuentaBusq.setIntIdentificadorExtranjero(0); //Identificador en Soles
				if (intTipoMoneda.compareTo(Constante.PARAM_T_TIPOMONEDA_SOLES)>0) beanCuentaBusq.setIntIdentificadorExtranjero(1); //Identificador en Moneda Extranjera
				//Filtro Movimiento
				beanCuentaBusq.setIntMovimiento(1);
				PlanCuentaFacadeRemote planCuentaFacade = (PlanCuentaFacadeRemote) EJBFactory.getRemote(PlanCuentaFacadeRemote.class);
				lista = planCuentaFacade.getListaPlanCuentaBusqueda(beanCuentaBusq);
				log.info("listCuentaOrigenDestino.size: "+lista.size());
			}
		} catch (Exception e) {
			log.error("Error en buscarPlanCuenta --> "+e);
		}		
		setListaPlanCuenta(lista);
	}
	
	public void seleccionarCuenta(){
		log.info("-----------------------Debugging PresupuestoController.seleccionarCuenta()-----------------------------");
		setPlanCuentaSelected(null);
		String selectedRow = getRequestParameter("rowCtaContable");
		PlanCuenta planCuenta = new PlanCuenta();
		for(int i=0; i<listaPlanCuenta.size(); i++){
			planCuenta = listaPlanCuenta.get(i);
			if(i == Integer.parseInt(selectedRow)){
				setPlanCuentaSelected(planCuenta);
				strCuentaGrb = planCuenta.getId().getStrNumeroCuenta();
				break;
			}
		}
	}
	//
	public void procesarEvento(){
		log.info("-------------------------------------Debugging PresupuestoController.procesarEvento-------------------------------------");
		listaValidaPresupuesto = new ArrayList<Presupuesto>();
		EmpresaFacadeRemote facade = null;
		List<Subsucursal> listaSubsucursal = null;
		listaPresupuestosDelAnioBase.clear();
		Integer mesPresupuesto = null;
		BigDecimal mntoProySoles = BigDecimal.ZERO;
		BigDecimal mntoProyExtrangero = BigDecimal.ZERO;
		intErrorPresupuestoPorAnioBase = 0;
		intErrorPlanCuenta = 0;
		listaPlanCuenta.clear();
		List<Presupuesto> listaMesesDelAnioBase = null; 
		listaMesesYaRegistrados.clear();
		try {
			//En Bloque
			if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE)) {
				if (beanPresupuesto.getId().getIntEmpresaPresupuestoPk()==null) {
					if (isValidoEnBloque() == false) {
						log.info("Datos de Presupuesto no válidos. Se aborta el proceso de grabación de Presupuesto.");
						return;
					}
					//Ejemplo: Si el Año Base es 2013 (debe EXISTIR data, de lo contrario no grabara) el proyectado será para el 2014 (serán los registros a grabar)
					//y si el Crecimiento fuera 120%, este se aplicará a los datos del 2013 (por Cuenta Contable, Sucursal y Subsucursal) y el resultado grabarlos 
					//al 2014 manteniendo como Tipo Valor el del Año Base.

					//Verificando que exista al menos 1 registro con el Año Base ingresado...
					listaPresupuestosDelAnioBase = presupuestoFacade.getListaPorRangoFechas(SESION_IDEMPRESA,intAnioBase,intMesDesde,intMesHasta);
					listaMesesDelAnioBase = presupuestoFacade.getMesesDelAnioBase(SESION_IDEMPRESA, intAnioBase, intMesDesde, intMesHasta);
										
					if (listaPresupuestosDelAnioBase!=null && !listaPresupuestosDelAnioBase.isEmpty()) {
						
						//Cuenta Contable: TODOS     Sucursal-SubSucursal: TODOS
						if (rbCtaContable.equals("1") && rbSucursal.equals("1")) {
							listaPlanCuenta = planCuentaFacade.getPlanCtaPorPeriodoBase(intAnioBase, SESION_IDEMPRESA);
							if (listaPlanCuenta!=null && !listaPlanCuenta.isEmpty()) {								
								listaPresupuestosDelAnioBase.clear();
								listaPresupuestosDelAnioBase = presupuestoFacade.getLstPstoAnioBase(SESION_IDEMPRESA, intAnioBase, intMesDesde, intMesHasta);
								if (listaPresupuestosDelAnioBase!=null && !listaPresupuestosDelAnioBase.isEmpty()) {
									for (Presupuesto lstPstoAnioBase : listaPresupuestosDelAnioBase) {
										intAnioGrabacion = intAnioProyectado;
										strCuentaGrb = lstPstoAnioBase.getId().getStrNumeroCuenta();
										intIdSucursalGrabacion = lstPstoAnioBase.getId().getIntIdSucursal();
										intIdSubSucursalGrabacion = lstPstoAnioBase.getId().getIntIdSubSucursal();
										mesPresupuesto = lstPstoAnioBase.getId().getIntMesPresupuesto();													
										mntoProySoles = lstPstoAnioBase.getBdMontoProyecSoles()!=null?lstPstoAnioBase.getBdMontoProyecSoles().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
										mntoProyExtrangero = lstPstoAnioBase.getBdMontoProyecExtranjero()!=null?lstPstoAnioBase.getBdMontoProyecExtranjero().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
										grabarPresupuesto(mesPresupuesto, mntoProySoles, mntoProyExtrangero);
									}
								}								
							}else {
								intErrorPlanCuenta = 1;
								MessageController message = (MessageController)getSessionBean("messageController");
								message.setWarningMessage("No existen Cuentas para el Año Proyectado. " +
										"Verifique.");
							}
						}

						//Cuenta Contable: TODOS
						if (rbCtaContable.equals("1") && rbSucursal.compareTo("1")!=0) {
							listaPlanCuenta = planCuentaFacade.getPlanCtaPorPeriodoBase(intAnioBase, SESION_IDEMPRESA);
							if (listaPlanCuenta!=null && !listaPlanCuenta.isEmpty()) {
								for (Presupuesto mes : listaMesesDelAnioBase) {
									for (PlanCuenta lstPlanCta : listaPlanCuenta) {
										//Comparando con datos del año base
										for (Presupuesto lstPtoDelAnioBase : listaPresupuestosDelAnioBase) {
											if (lstPtoDelAnioBase.getId().getIntMesPresupuesto().equals(mes.getId().getIntMesPresupuesto())) {
												if (lstPtoDelAnioBase.getId().getIntEmpresaCuentaPk().equals(lstPlanCta.getId().getIntEmpresaCuentaPk()) &&
														lstPtoDelAnioBase.getId().getStrNumeroCuenta().equals(lstPlanCta.getId().getStrNumeroCuenta())) {
													if (lstPtoDelAnioBase.getId().getIntIdSucursal().equals(intIdSucursalGrabacion) && 
															lstPtoDelAnioBase.getId().getIntIdSubSucursal().equals(intIdSubSucursalGrabacion)) {
														intAnioGrabacion = intAnioProyectado;
														strCuentaGrb = lstPlanCta.getId().getStrNumeroCuenta();
														mesPresupuesto = mes.getId().getIntMesPresupuesto();													
														mntoProySoles = lstPtoDelAnioBase.getBdMontoProyecSoles()!=null?lstPtoDelAnioBase.getBdMontoProyecSoles().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
														mntoProyExtrangero = lstPtoDelAnioBase.getBdMontoProyecExtranjero()!=null?lstPtoDelAnioBase.getBdMontoProyecExtranjero().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
														grabarPresupuesto(mesPresupuesto, mntoProySoles, mntoProyExtrangero);
														break;
													}
												}
											}													
										}
									}
								}
							}else {
								intErrorPlanCuenta = 1;
								MessageController message = (MessageController)getSessionBean("messageController");
								message.setWarningMessage("No existen Cuentas para el Año Proyectado. " +
										"Verifique.");
							}						
						}
										
						//Sucursal-SubSucursal: TODOS
						if (rbSucursal.equals("1") && rbCtaContable.compareTo("1")!=0) {
							for (Presupuesto mes : listaMesesDelAnioBase) {
								for (Sucursal lstSucursal : listJuridicaSucursal) {
									facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
									listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(lstSucursal.getId().getIntIdSucursal());
									if (listaSubsucursal!=null && !listaSubsucursal.isEmpty()) {
										for (Subsucursal lstSubSucu : listaSubsucursal) {
											//Comparando con datos del año base
											for (Presupuesto lstPtoDelAnioBase : listaPresupuestosDelAnioBase) {
												if (lstPtoDelAnioBase.getId().getIntMesPresupuesto().equals(mes.getId().getIntMesPresupuesto())) {
													if (lstPtoDelAnioBase.getId().getStrNumeroCuenta().equals(strCuentaGrb)) {
														if (lstPtoDelAnioBase.getId().getIntIdSucursal().equals(lstSucursal.getId().getIntIdSucursal()) && 
																lstPtoDelAnioBase.getId().getIntIdSubSucursal().equals(lstSubSucu.getId().getIntIdSubSucursal())) {
															intAnioGrabacion = intAnioProyectado;
															intIdSucursalGrabacion = lstSucursal.getId().getIntIdSucursal();
															intIdSubSucursalGrabacion = lstSubSucu.getId().getIntIdSubSucursal();
															mesPresupuesto = mes.getId().getIntMesPresupuesto();													
															mntoProySoles = lstPtoDelAnioBase.getBdMontoProyecSoles()!=null?lstPtoDelAnioBase.getBdMontoProyecSoles().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
															mntoProyExtrangero = lstPtoDelAnioBase.getBdMontoProyecExtranjero()!=null?lstPtoDelAnioBase.getBdMontoProyecExtranjero().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
															grabarPresupuesto(mesPresupuesto, mntoProySoles, mntoProyExtrangero);
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
						if (rbCtaContable.compareTo("1")!=0 && rbSucursal.compareTo("1")!=0) {
							for (Presupuesto mes : listaMesesDelAnioBase) {
								//Comparando con datos del año base
								for (Presupuesto lstPtoDelAnioBase : listaPresupuestosDelAnioBase) {
									if (lstPtoDelAnioBase.getId().getIntMesPresupuesto().equals(mes.getId().getIntMesPresupuesto())) {
										if (lstPtoDelAnioBase.getId().getStrNumeroCuenta().equals(strCuentaGrb)) {
											if (lstPtoDelAnioBase.getId().getIntIdSucursal().equals(intIdSucursalGrabacion) && 
													lstPtoDelAnioBase.getId().getIntIdSubSucursal().equals(intIdSubSucursalGrabacion)) {
												intAnioGrabacion = intAnioProyectado;
												mesPresupuesto = mes.getId().getIntMesPresupuesto();													
												mntoProySoles = lstPtoDelAnioBase.getBdMontoProyecSoles()!=null?lstPtoDelAnioBase.getBdMontoProyecSoles().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
												mntoProyExtrangero = lstPtoDelAnioBase.getBdMontoProyecExtranjero()!=null?lstPtoDelAnioBase.getBdMontoProyecExtranjero().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
												grabarPresupuesto(mesPresupuesto, mntoProySoles, mntoProyExtrangero);
												break;
											}
										}
									}													
								}							
							}
						}
						if (intErrorPlanCuenta.equals(0)) {
							if (!listaValidaPresupuesto.isEmpty()) {
								setStrMsgTxtExisteRegistro("");
								strMsgTxtExisteRegistro = "Uno o más Presupuestos ya se encuentran registrados, ";						
							}else {
								limpiarPanelEnBloque();
							}
						}							
					}else {
						intErrorPresupuestoPorAnioBase = 1;
						MessageController message = (MessageController)getSessionBean("messageController");
						message.setWarningMessage("No existen registros con el Año Base ingresado. " +
								"Verifique.");
						//limpiarPanelEnBloque();
					}
				}				
			}
			//Por Partida
			if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_POR_INDICADOR)) {
				if (beanPresupuesto.getId().getIntEmpresaPresupuestoPk()==null) {
					if (isValidoPorPartida(beanPresupuesto) == false) {
						log.info("Datos del Presupuesto no válidos. Se aborta el proceso de grabación de Presupuesto.");
						return;
					}	
					if (bdMontoProyEnero!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_ENERO, bdMontoProyEnero, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_ENERO, null, bdMontoProyEnero);
					}
					if (bdMontoProyFebrero!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_FEBRERO, bdMontoProyFebrero, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_FEBRERO, null, bdMontoProyFebrero);
					}
					if (bdMontoProyMarzo!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_MARZO, bdMontoProyMarzo, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_MARZO, null, bdMontoProyMarzo);
					}
					if (bdMontoProyAbril!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_ABRIL, bdMontoProyAbril, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_ABRIL, null, bdMontoProyAbril);
					}
					if (bdMontoProyMayo!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_MAYO, bdMontoProyMayo, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_MAYO, null, bdMontoProyMayo);
					}
					if (bdMontoProyJunio!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_JUNIO, bdMontoProyJunio, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_JUNIO, null, bdMontoProyJunio);
					}
					if (bdMontoProyJulio!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_JULIO, bdMontoProyJulio, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_JULIO, null, bdMontoProyJulio);
					}
					if (bdMontoProyAgosto!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_AGOSTO, bdMontoProyAgosto, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_AGOSTO, null, bdMontoProyAgosto);
					}
					if (bdMontoProySeptiembre!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_SETIEMBRE, bdMontoProySeptiembre, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_SETIEMBRE, null, bdMontoProySeptiembre);
					}
					if (bdMontoProyOctubre!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_OCTUBRE, bdMontoProyOctubre, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_OCTUBRE, null, bdMontoProyOctubre);
					}
					if (bdMontoProyNoviembre!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_NOVIEMBRE, bdMontoProyNoviembre, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_NOVIEMBRE, null, bdMontoProyNoviembre);
					}
					if (bdMontoProyDiciembre!=null) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) grabarPresupuesto(Constante.PARAM_T_MES_DICIEMBRE, bdMontoProyDiciembre, null);
						else grabarPresupuesto(Constante.PARAM_T_MES_DICIEMBRE, null, bdMontoProyDiciembre);
					}
					if (!listaMesesYaRegistrados.isEmpty()) {
						String descMes = "";
						setStrMsgTxtExisteRegistro("");
						for (Tabla mesYaExiste : listaMesesYaRegistrados) {
							descMes = descMes+mesYaExiste.getStrDescripcion()+"("+mesYaExiste.getStrAbreviatura()+") ";
						}
						strMsgTxtExisteRegistro = "Ya existe Registros para mes(es) "+descMes;						
					}else {
						limpiarPanelPorPartida();
						blnShowPanelTabPorPartida = Boolean.TRUE;
					}					
				}else {
					if (isValidoPorPartida(beanPresupuesto) == false) {
						log.info("Datos del Presupuesto no válidos. Se aborta el proceso de grabación de Presupuesto.");
						return;
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_ENERO)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyEnero,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyEnero,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_FEBRERO)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyFebrero,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyFebrero,presupuestoSelected);						
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_MARZO)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyMarzo,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyMarzo,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_ABRIL)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyAbril,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyAbril,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_MAYO)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyMayo,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyMayo,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_JUNIO)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyJunio,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyJunio,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_JULIO)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyJulio,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyJulio,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_AGOSTO)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyAgosto,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyAgosto,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_SETIEMBRE)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProySeptiembre,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProySeptiembre,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_OCTUBRE)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyOctubre,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyOctubre,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_NOVIEMBRE)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyNoviembre,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyNoviembre,presupuestoSelected);
					}
					if (presupuestoSelected.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_DICIEMBRE)) {
						if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyDiciembre,null,presupuestoSelected);
						else modificarPresupuesto(null,bdMontoProyDiciembre,presupuestoSelected);
					}
					limpiarPanelPorPartida();
					blnShowPanelTabPorPartida = Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Error en procesarEvento --> "+e);
		}
	}
	
	public void isValidAnioProyectado() {
		log.info("-------------------------------------Debugging PresupuestoController.isValidAnioProyectado-------------------------------------");
		listaPlanCuenta.clear();
		mpIntTipoCuentaBusq = 0;
		intErrorPresupuestoPorAnioBase = 0;
		if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE)) {
			if (intAnioProyectado == null) {
				intErrorPresupuestoPorAnioBase = 1;
				MessageController message = (MessageController)getSessionBean("messageController");
				message.setErrorMessage("Debe seleccionar un Año Base. " +
						"Verifique.");
			}else {
				intAnioGrabacion = intAnioProyectado;
			}
		}
		if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_POR_PARTIDA)) {
			log.info("pCbYears: "+getRequestParameter("pCbYears"));
			String pFiltroCombo = getRequestParameter("pCbYears");
			if(pFiltroCombo.equals("0")){
				blnDisabledAddCtaContable = Boolean.TRUE;
				setStrCuentaGrb("");
			}else{
				blnDisabledAddCtaContable = Boolean.FALSE;
			}
		}		
	}
	
	//VALIDANDO ESTADO DE CIERRE
	public void isValidaEstadoCierre() {		
		MessageController message = (MessageController)getSessionBean("messageController");
		message.setWarningMessage("No se puede Modificar o Eliminar registros con estado Cerrado. " +
				"Presione Aceptar para procesar otro registro.");
		limpiarPanelPorPartida();
		blnShowPanelTabPorPartida = Boolean.FALSE;
	}
	
	public void grabarPresupuesto(Integer mes, BigDecimal mtoProySoles, BigDecimal mntoProyExtrangero) throws ParseException {
		log.info("-------------------------------------Debugging PresupuestoController.grabarPresupuesto-------------------------------------");
		Presupuesto beanValidaPresupuesto = null;		
		try {
			//Seteando Presupuesto para la grabación
			beanPresupuesto.getId().setIntEmpresaPresupuestoPk(SESION_IDEMPRESA);
			beanPresupuesto.getId().setIntPeriodoPresupuesto(intAnioGrabacion);
			beanPresupuesto.getId().setIntMesPresupuesto(mes);
			beanPresupuesto.getId().setIntEmpresaCuentaPk(SESION_IDEMPRESA);
			beanPresupuesto.getId().setIntPeriodoCuenta(intAnioGrabacion);
			beanPresupuesto.getId().setStrNumeroCuenta(strCuentaGrb);
			beanPresupuesto.getId().setIntEmpresaSucursalPk(SESION_IDEMPRESA);
			beanPresupuesto.getId().setIntIdSucursal(intIdSucursalGrabacion);
			beanPresupuesto.getId().setIntIdSubSucursal(intIdSubSucursalGrabacion);
			
			beanPresupuesto.setBdMontoProyecSoles(mtoProySoles);
			beanPresupuesto.setBdMontoProyecExtranjero(mntoProyExtrangero);
			beanPresupuesto.setBdMontoEjecSoles(null);
			beanPresupuesto.setBdMontoEjecExtranjero(null);
			beanPresupuesto.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			beanPresupuesto.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOCIERRE_PENDIENTE);
			beanPresupuesto.setIntEmpresaUsuarioPk(SESION_IDEMPRESA);
			beanPresupuesto.setIntPersonaUsuarioPk(SESION_IDUSUARIO);
			
			//Validando que no exista el registro...
			beanValidaPresupuesto = presupuestoFacade.getPresupuestoPorPK(beanPresupuesto.getId());
			if (beanValidaPresupuesto!=null) {
				if (beanValidaPresupuesto.getIntParaEstadoCierre().equals(Constante.PARAM_T_ESTADOCIERRE_PENDIENTE)) {
					String strDescripcion = "";
					for (Tabla descMes : listaDescripcionMes) {
						if(descMes.getIntIdDetalle().compareTo(beanPresupuesto.getId().getIntMesPresupuesto())==0){
							strDescripcion = descMes.getStrDescripcion();
							break;
						}
					}					
					Tabla tbl = new Tabla();
					tbl.setStrDescripcion(strDescripcion);
					tbl.setStrAbreviatura(convertirMontos(beanValidaPresupuesto.getBdMontoProyecSoles()!=null?beanValidaPresupuesto.getBdMontoProyecSoles():beanValidaPresupuesto.getBdMontoProyecExtranjero()));
					listaMesesYaRegistrados.add(tbl);
					listaValidaPresupuesto.add(beanValidaPresupuesto);
				}
			}else {
				//Grabando registro...
				presupuestoFacade.grabarPresupuesto(beanPresupuesto);
			}
			
		} catch (Exception e) {
			log.error("Error en grabarPresupuesto --> "+e);
		}
	}
	
	public void modificarPresupuestoPorPartida() {
		log.info("-------------------------------------Debugging PresupuestoController.modificarPresupuestoPorPartida-------------------------------------");
		limpiarPanelPorPartida();		
		blnShowPanelTabPorPartida = Boolean.TRUE;
		blnShowPanelTabEnBloque = Boolean.FALSE;
		blnDisabledGrabar = Boolean.FALSE;
		List<Presupuesto> lstPsto = null;
		intTipoGrabacion = Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_POR_PARTIDA;
		
		//Seteando Llaves del Presupuesto seleccionado para la modificación...
		beanPresupuesto.getId().setIntEmpresaPresupuestoPk(presupuestoSelected.getId().getIntEmpresaPresupuestoPk());
		beanPresupuesto.getId().setIntPeriodoPresupuesto(presupuestoSelected.getId().getIntPeriodoPresupuesto());
		beanPresupuesto.getId().setIntMesPresupuesto(presupuestoSelected.getId().getIntMesPresupuesto());
		beanPresupuesto.getId().setIntEmpresaCuentaPk(presupuestoSelected.getId().getIntEmpresaCuentaPk());
		beanPresupuesto.getId().setIntPeriodoCuenta(presupuestoSelected.getId().getIntPeriodoCuenta());
		beanPresupuesto.getId().setStrNumeroCuenta(presupuestoSelected.getId().getStrNumeroCuenta());
		beanPresupuesto.getId().setIntEmpresaSucursalPk(presupuestoSelected.getId().getIntEmpresaSucursalPk());
		beanPresupuesto.getId().setIntIdSucursal(presupuestoSelected.getId().getIntIdSucursal());
		beanPresupuesto.getId().setIntIdSubSucursal(presupuestoSelected.getId().getIntIdSubSucursal());
		
		//Deshabilitando llaves en el formulario...
		blnDisabledAnio = Boolean.TRUE;
		blnDisabledTipoMoneda = Boolean.TRUE;
		blnDisabledTipoMoneda = Boolean.TRUE;
		blnDisabledSucursal = Boolean.TRUE;
		blnDisabledSubSucursal = Boolean.TRUE;
		
		//Deshabilitando Montos Proyectados que no corresponden al mes seleccionado...
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_ENERO)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_FEBRERO)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_MARZO)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_ABRIL)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_MAYO)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_JUNIO)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_JULIO)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_AGOSTO)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_SETIEMBRE)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_OCTUBRE)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_NOVIEMBRE)==0) {
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
		if (presupuestoSelected.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_DICIEMBRE)==0) {
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
		intAnioGrabacion = presupuestoSelected.getId().getIntPeriodoPresupuesto();
		intTipoMoneda = presupuestoSelected.getBdMontoProyecSoles()!=null?1:2;
		strCuentaGrb = presupuestoSelected.getId().getStrNumeroCuenta();
		intIdSucursalGrabacion = presupuestoSelected.getId().getIntIdSucursal();		
		
		EmpresaFacadeRemote facade = null;
		List<Subsucursal> listaSubsucursal = null;
		try {
			facade = (EmpresaFacadeRemote)EJBFactory.getRemote(EmpresaFacadeRemote.class);
			listaSubsucursal = facade.getListaSubSucursalPorIdSucursal(intIdSucursalGrabacion);
			setListJuridicaSubsucursalGrb(listaSubsucursal);
		} catch (Exception e) {
			log.error("Error en obtener SubSucursal --> "+e);
		}
		intIdSubSucursalGrabacion = presupuestoSelected.getId().getIntIdSubSucursal();
		
		//Mostrando montos por Empresa, Periodo, Cuenta, Sucursal y Subsucursal, esto para que no solo se vean los montos del mes selecionado, 
		//sino de todos los meses que correspondan a la PK mencionada...
		try {
			PresupuestoId beanPresupuestoId = new PresupuestoId();
			beanPresupuestoId.setIntEmpresaPresupuestoPk(presupuestoSelected.getId().getIntEmpresaPresupuestoPk());
			beanPresupuestoId.setIntPeriodoPresupuesto(presupuestoSelected.getId().getIntPeriodoPresupuesto());
			beanPresupuestoId.setIntEmpresaCuentaPk(presupuestoSelected.getId().getIntEmpresaCuentaPk());
			beanPresupuestoId.setIntPeriodoCuenta(presupuestoSelected.getId().getIntPeriodoCuenta());
			beanPresupuestoId.setStrNumeroCuenta(presupuestoSelected.getId().getStrNumeroCuenta());
			beanPresupuestoId.setIntEmpresaSucursalPk(presupuestoSelected.getId().getIntEmpresaSucursalPk());
			beanPresupuestoId.setIntIdSucursal(presupuestoSelected.getId().getIntIdSucursal());
			beanPresupuestoId.setIntIdSubSucursal(presupuestoSelected.getId().getIntIdSubSucursal());

			lstPsto = presupuestoFacade.getListaPresupuestoPorFiltros(beanPresupuestoId,0);
			
			if (lstPsto!=null && !lstPsto.isEmpty()) {
				for (Presupuesto o : lstPsto) {
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_ENERO)==0) {
						bdMontoProyEnero = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_FEBRERO)==0) {
						bdMontoProyFebrero = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_MARZO)==0) {
						bdMontoProyMarzo = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_ABRIL)==0) {
						bdMontoProyAbril = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_MAYO)==0) {
						bdMontoProyMayo = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_JUNIO)==0) {
						bdMontoProyJunio = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_JULIO)==0) {
						bdMontoProyJulio = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_AGOSTO)==0) {
						bdMontoProyAgosto = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_SETIEMBRE)==0) {
						bdMontoProySeptiembre = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_OCTUBRE)==0) {
						bdMontoProyOctubre = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_NOVIEMBRE)==0) {
						bdMontoProyNoviembre = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
					if (o.getId().getIntMesPresupuesto().compareTo(Constante.PARAM_T_MES_DICIEMBRE)==0) {
						bdMontoProyDiciembre = o.getBdMontoProyecSoles()!=null?o.getBdMontoProyecSoles():o.getBdMontoProyecExtranjero();
					}
				}
			}
		} catch (Exception e) {
			log.error("Error en obtener Montos --> "+e);
		}
	}
	
	public void modificarEnProcesoGrabacion() {
		log.info("-------------------------------------Debugging PresupuestoController.modificarEnProcesoGrabacion-------------------------------------");
		BigDecimal mntoProySoles = BigDecimal.ZERO;
		BigDecimal mntoProyExtrangero = BigDecimal.ZERO;
		try {
			if (listaValidaPresupuesto!=null && !listaValidaPresupuesto.isEmpty()) {
				for (Presupuesto lstValidPresupuesto : listaValidaPresupuesto) {
					//Seteando Presupuesto para la modificación...
					beanPresupuesto.getId().setIntEmpresaPresupuestoPk(lstValidPresupuesto.getId().getIntEmpresaPresupuestoPk());
					beanPresupuesto.getId().setIntPeriodoPresupuesto(lstValidPresupuesto.getId().getIntPeriodoPresupuesto());
					beanPresupuesto.getId().setIntMesPresupuesto(lstValidPresupuesto.getId().getIntMesPresupuesto());
					beanPresupuesto.getId().setIntEmpresaCuentaPk(lstValidPresupuesto.getId().getIntEmpresaCuentaPk());
					beanPresupuesto.getId().setIntPeriodoCuenta(lstValidPresupuesto.getId().getIntPeriodoCuenta());
					beanPresupuesto.getId().setStrNumeroCuenta(lstValidPresupuesto.getId().getStrNumeroCuenta());
					beanPresupuesto.getId().setIntEmpresaSucursalPk(lstValidPresupuesto.getId().getIntEmpresaSucursalPk());
					beanPresupuesto.getId().setIntIdSucursal(lstValidPresupuesto.getId().getIntIdSucursal());
					beanPresupuesto.getId().setIntIdSubSucursal(lstValidPresupuesto.getId().getIntIdSubSucursal());
					
					if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE)) {
						for (Presupuesto lstPtoDelAnioBase : listaPresupuestosDelAnioBase) {
							if (lstPtoDelAnioBase.getId().getIntEmpresaPresupuestoPk().equals(lstValidPresupuesto.getId().getIntEmpresaPresupuestoPk()) && 
									lstPtoDelAnioBase.getId().getIntMesPresupuesto().equals(lstValidPresupuesto.getId().getIntMesPresupuesto()) &&  
									lstPtoDelAnioBase.getId().getIntEmpresaCuentaPk().equals(lstValidPresupuesto.getId().getIntEmpresaCuentaPk()) &&  
									lstPtoDelAnioBase.getId().getStrNumeroCuenta().equals(lstValidPresupuesto.getId().getStrNumeroCuenta()) && 
									lstPtoDelAnioBase.getId().getIntEmpresaSucursalPk().equals(lstValidPresupuesto.getId().getIntEmpresaSucursalPk()) &&
									lstPtoDelAnioBase.getId().getIntIdSucursal().equals(lstValidPresupuesto.getId().getIntIdSucursal()) && 
									lstPtoDelAnioBase.getId().getIntIdSubSucursal().equals(lstValidPresupuesto.getId().getIntIdSubSucursal())) {
								mntoProySoles = lstPtoDelAnioBase.getBdMontoProyecSoles()!=null?lstPtoDelAnioBase.getBdMontoProyecSoles().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
								mntoProyExtrangero = lstPtoDelAnioBase.getBdMontoProyecExtranjero()!=null?lstPtoDelAnioBase.getBdMontoProyecExtranjero().multiply(bdPorcentajeCrecimiento).divide(new BigDecimal(100)):null;
								break;
							}
						}					
						modificarPresupuesto(mntoProySoles,mntoProyExtrangero,lstValidPresupuesto);					
					}
					
					if (intTipoGrabacion.equals(Constante.PARAM_T_INDICADOR_TIPOGRABACION_POR_INDICADOR)) {
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_ENERO)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyEnero,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyEnero,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_FEBRERO)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyFebrero,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyFebrero,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_MARZO)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyMarzo,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyMarzo,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_ABRIL)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyAbril,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyAbril,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_MAYO)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyMayo,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyMayo,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_JUNIO)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyJunio,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyJunio,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_JULIO)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyJulio,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyJulio,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_AGOSTO)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyAgosto,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyAgosto,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_SETIEMBRE)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProySeptiembre,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProySeptiembre,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_OCTUBRE)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyOctubre,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyOctubre,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_NOVIEMBRE)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyNoviembre,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyNoviembre,lstValidPresupuesto);
						}
						if (beanPresupuesto.getId().getIntMesPresupuesto().equals(Constante.PARAM_T_MES_DICIEMBRE)) {
							if (intTipoMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)) modificarPresupuesto(bdMontoProyDiciembre,null,lstValidPresupuesto);
							else modificarPresupuesto(null,bdMontoProyDiciembre,lstValidPresupuesto);
						}
					}
				}

				if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE)) {
					limpiarPanelEnBloque();
					blnShowPanelTabEnBloque = Boolean.TRUE;
				}
				if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_POR_PARTIDA)) {
					limpiarPanelPorPartida();
					blnShowPanelTabPorPartida = Boolean.TRUE;
				}
			}
		} catch (Exception e) {
			log.error("Error en modificarEnProcesoGrabacion--> "+e);
		}
	}

	public void modificarPresupuesto(BigDecimal mtoProySoles, BigDecimal mntoProyExtrangero,Presupuesto beanValAnterior) throws ParseException {
		log.info("-------------------------------------Debugging PresupuestoController.modificarPresupuesto-------------------------------------");
		List<Auditoria> listaAuditoria = null;
		try {			
			beanPresupuesto.setBdMontoProyecSoles(mtoProySoles);
			beanPresupuesto.setBdMontoProyecExtranjero(mntoProyExtrangero);
			beanPresupuesto.setBdMontoEjecSoles(null);
			beanPresupuesto.setBdMontoEjecExtranjero(null);
			beanPresupuesto.setTsFechaRegistro(new Timestamp(new Date().getTime()));
			beanPresupuesto.setIntParaEstadoCierre(Constante.PARAM_T_ESTADOCIERRE_PENDIENTE);
			beanPresupuesto.setIntEmpresaUsuarioPk(SESION_IDEMPRESA);
			beanPresupuesto.setIntPersonaUsuarioPk(SESION_IDUSUARIO);
			
			//Modificando...
			presupuestoFacade.modificarPresupuesto(beanPresupuesto);
			//Grabando Auditoria...
			listaAuditoria = generarAuditoria(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_UPDATE, beanValAnterior);
			if (listaAuditoria!=null && !listaAuditoria.isEmpty()) {
				for (Auditoria lstAudi : listaAuditoria) {
					grabarAuditoria(lstAudi);
				}
			}
		} catch (Exception e) {
			log.error("Error en modificarPresupuesto --> "+e);
		}
	}
	
	public void eliminarPresupuesto() {
		log.info("-------------------------------------Debugging PresupuestoController.eliminarPresupuesto-------------------------------------");
		List<Auditoria> listaAuditoria = null;
		try {
			//Eliminando registro...
			presupuestoFacade.eliminarPresupuesto(presupuestoSelected.getId());
			
			//Generando Auditoria...
			listaAuditoria = generarAuditoria(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_DELETE, presupuestoSelected);
			if (listaAuditoria!=null && !listaAuditoria.isEmpty()) {
				for (Auditoria lstAudi : listaAuditoria) {
					grabarAuditoria(lstAudi);
				}
			}
		} catch (Exception e) {
			log.error("Error en eliminarPresupuesto --> "+e);
		}
	}
	
	public Auditoria beanAuditoria(Integer intTipoCambio, Presupuesto beanAuditarPresupuesto) {
		Auditoria auditoria = new Auditoria();
		Calendar fecHoy = Calendar.getInstance();
		
		auditoria.setListaAuditoriaMotivo(null);
		auditoria.setStrTabla(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO);
		auditoria.setIntEmpresaPk(SESION_IDEMPRESA);
		auditoria.setStrLlave1(""+beanAuditarPresupuesto.getId().getIntEmpresaPresupuestoPk());
		auditoria.setStrLlave2(""+beanAuditarPresupuesto.getId().getIntPeriodoPresupuesto());
		auditoria.setStrLlave3(""+beanAuditarPresupuesto.getId().getIntMesPresupuesto());
		auditoria.setStrLlave4(""+beanAuditarPresupuesto.getId().getIntEmpresaCuentaPk());
		auditoria.setStrLlave5(""+beanAuditarPresupuesto.getId().getIntPeriodoCuenta());
		auditoria.setStrLlave6(""+beanAuditarPresupuesto.getId().getStrNumeroCuenta());
		auditoria.setStrLlave7(""+beanAuditarPresupuesto.getId().getIntEmpresaSucursalPk());
		auditoria.setStrLlave8(""+beanAuditarPresupuesto.getId().getIntIdSucursal());
		auditoria.setStrLlave9(""+beanAuditarPresupuesto.getId().getIntIdSubSucursal());
		
		auditoria.setIntTipo(intTipoCambio);
		auditoria.setTsFecharegistro(new Timestamp(fecHoy.getTimeInMillis()));
		auditoria.setIntPersonaPk(SESION_IDUSUARIO);
		return auditoria;
	}
	public List<Auditoria> generarAuditoria(Integer intTipoCambio, Presupuesto beanAuditarPresupuesto) throws BusinessException{
		
		Auditoria auditoria = null;
		List<Auditoria> lista = new ArrayList<Auditoria>();
		try {
			//Procedo de Eliminacion
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_DELETE)) {
				//PRDE_MONTOPROYSOLES_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
								
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRDE_MONTOPROYSOLES);
				auditoria.setStrValoranterior(beanAuditarPresupuesto.getBdMontoProyecSoles()!=null?""+beanAuditarPresupuesto.getBdMontoProyecSoles():null);
				auditoria.setStrValornuevo(null);
				
				lista.add(auditoria);
				
				//PRDE_MONTOPROYEXTRANJERO_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRDE_MONTOPROYEXTRANJERO);
				auditoria.setStrValoranterior(beanAuditarPresupuesto.getBdMontoProyecExtranjero()!=null?""+beanAuditarPresupuesto.getBdMontoProyecExtranjero():null);
				auditoria.setStrValornuevo(null);

				lista.add(auditoria);
				
				//PRDE_MONTOEJECSOLES_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRDE_MONTOEJECSOLES);
				auditoria.setStrValoranterior(beanAuditarPresupuesto.getBdMontoEjecSoles()!=null?""+beanAuditarPresupuesto.getBdMontoEjecSoles():null);
				auditoria.setStrValornuevo(null);

				lista.add(auditoria);
				
				//PRDE_MONTOEJECEXTRANJERO_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRDE_MONTOEJECEXTRANJERO);
				auditoria.setStrValoranterior(beanAuditarPresupuesto.getBdMontoEjecExtranjero()!=null?""+beanAuditarPresupuesto.getBdMontoEjecExtranjero():null);
				auditoria.setStrValornuevo(null);
				
				lista.add(auditoria);
				
				//PRES_FECHAREGISTRO_D
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRES_FECHAREGISTRO);
				auditoria.setStrValoranterior(""+beanAuditarPresupuesto.getTsFechaRegistro());
				auditoria.setStrValornuevo(null);

				lista.add(auditoria);
				
				//PARA_ESTADOCIERRE_N_COD				
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PARA_ESTADOCIERRE);
				auditoria.setStrValoranterior(""+beanAuditarPresupuesto.getIntParaEstadoCierre());
				auditoria.setStrValornuevo(null);
				
				lista.add(auditoria);
				
				//PERS_EMPRESAUSUARIO_N_PK
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PERS_EMPRESAUSUARIO);
				auditoria.setStrValoranterior(""+beanAuditarPresupuesto.getIntEmpresaUsuarioPk());
				auditoria.setStrValornuevo(null);

				lista.add(auditoria);
				
				//PERS_PERSONAUSUARIO_N_PK
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PERS_PERSONAUSUARIO);
				auditoria.setStrValoranterior(""+beanAuditarPresupuesto.getIntPersonaUsuarioPk());
				auditoria.setStrValornuevo(null);

				lista.add(auditoria);
			}
			if (intTipoCambio.equals(Constante.PARAM_T_AUDITORIA_TIPOREGISTRO_UPDATE)) {
				//PRDE_MONTOPROYSOLES_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
								
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRDE_MONTOPROYSOLES);
				auditoria.setStrValoranterior(beanAuditarPresupuesto.getBdMontoProyecSoles()!=null?""+beanAuditarPresupuesto.getBdMontoProyecSoles():null);
				auditoria.setStrValornuevo(beanPresupuesto.getBdMontoProyecSoles()!=null?""+beanPresupuesto.getBdMontoProyecSoles():null);
				
				lista.add(auditoria);
				
				//PRDE_MONTOPROYEXTRANJERO_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRDE_MONTOPROYEXTRANJERO);
				auditoria.setStrValoranterior(beanAuditarPresupuesto.getBdMontoProyecExtranjero()!=null?""+beanAuditarPresupuesto.getBdMontoProyecExtranjero():null);
				auditoria.setStrValornuevo(beanPresupuesto.getBdMontoProyecExtranjero()!=null?""+beanPresupuesto.getBdMontoProyecExtranjero():null);

				lista.add(auditoria);
				
				//PRDE_MONTOEJECSOLES_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRDE_MONTOEJECSOLES);
				auditoria.setStrValoranterior(beanAuditarPresupuesto.getBdMontoEjecSoles()!=null?""+beanAuditarPresupuesto.getBdMontoEjecSoles():null);
				auditoria.setStrValornuevo(beanPresupuesto.getBdMontoEjecSoles()!=null?""+beanPresupuesto.getBdMontoEjecSoles():null);

				lista.add(auditoria);
				
				//PRDE_MONTOEJECEXTRANJERO_N
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRDE_MONTOEJECEXTRANJERO);
				auditoria.setStrValoranterior(beanAuditarPresupuesto.getBdMontoEjecExtranjero()!=null?""+beanAuditarPresupuesto.getBdMontoEjecExtranjero():null);
				auditoria.setStrValornuevo(beanPresupuesto.getBdMontoEjecExtranjero()!=null?""+beanPresupuesto.getBdMontoEjecExtranjero():null);
				
				lista.add(auditoria);
				
				//PRES_FECHAREGISTRO_D
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PRES_FECHAREGISTRO);
				auditoria.setStrValoranterior(""+beanAuditarPresupuesto.getTsFechaRegistro());
				auditoria.setStrValornuevo(""+beanPresupuesto.getTsFechaRegistro());

				lista.add(auditoria);
				
				//PARA_ESTADOCIERRE_N_COD				
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PARA_ESTADOCIERRE);
				auditoria.setStrValoranterior(""+beanAuditarPresupuesto.getIntParaEstadoCierre());
				auditoria.setStrValornuevo(""+beanPresupuesto.getIntParaEstadoCierre());
				
				lista.add(auditoria);
				
				//PERS_EMPRESAUSUARIO_N_PK
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PERS_EMPRESAUSUARIO);
				auditoria.setStrValoranterior(""+beanAuditarPresupuesto.getIntEmpresaUsuarioPk());
				auditoria.setStrValornuevo(""+SESION_IDEMPRESA);

				lista.add(auditoria);
				
				//PERS_PERSONAUSUARIO_N_PK
				auditoria = beanAuditoria(intTipoCambio, beanAuditarPresupuesto);
				
				auditoria.setStrColumna(Constante.PARAM_T_AUDITORIA_PRE_PRESUPUESTO_COLUMNA_PERS_PERSONAUSUARIO);
				auditoria.setStrValoranterior(""+beanAuditarPresupuesto.getIntPersonaUsuarioPk());
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
			//En el proceso de grabar Auditoria, para el caso de Presupuestos no se grabará tabla AUDITORIA_MOTIVO
			auditoriaFacade.grabarAuditoria(auditoria);
			
		} catch (Exception e) {
			log.error("Error en grabarAuditoria ---> "+e);
		}		
		return auditoria;
	}
	
	//VALIDANDO DATOS INGRESADOS PARA GRABACION TAB "EN BLOQUE"
	public Boolean isValidoEnBloque() {
		Boolean validPresupuesto = true;
		limpiarMsgErrorPresupuesto();
		try {
			//1. Validación de Periodo
			if (intAnioBase == null || intAnioBase == 0) {
				setStrMsgTxtPeriodo("Debe seleccionar un Año Base.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtPeriodo("");
			}
			//2. Validación de Mes Desde - Hasta
			if ((intMesDesde == null || intMesDesde == 0) || (intMesHasta == null || intMesHasta == 0)) {
				setStrMsgTxtMes("Debe seleccionar rango de meses.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtRangoMeses("");
			}
			//3. Verificación rangos correctos
			if (intMesDesde >= intMesHasta) {
				setStrMsgTxtRangoMeses("Rango de meses incorrecto.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtRangoMeses("");
			}
			//4. Validación de % de Crecimiento
			if (bdPorcentajeCrecimiento == null || bdPorcentajeCrecimiento == BigDecimal.ZERO) {
				setStrMsgTxtPorcentajeCrecimiento("Debe ingresar % de Crecimiento.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtPorcentajeCrecimiento("");
			}
			
			if (rbCtaContable.compareTo("1")!=0) {
				//5. Validación de Cuenta Contable
				if (strCuentaGrb.trim() == null) {
					setStrMsgTxtAddCtaContable("Debe Agregar una Cuenta.");
					validPresupuesto = false;
				} else {
					setStrMsgTxtAddCtaContable("");
				}
			}
			if (rbSucursal.compareTo("1")!=0) {
				//6. Validación de Sucursal
				if (intIdSucursalGrabacion == null || intIdSucursalGrabacion == 0) {
					setStrMsgTxtSucursal("Debe seleccionar una Sucursal.");
					validPresupuesto = false;
				} else {
					setStrMsgTxtSucursal("");
				}			
				//7. Validación de Sub-Sucursal
				if (intIdSubSucursalGrabacion == null || intIdSubSucursalGrabacion == 0) {
					setStrMsgTxtSubSucursal("Debe seleccionar una Sub-Sucursal.");
					validPresupuesto = false;
				} else {
					setStrMsgTxtSubSucursal("");
				}
			}
		} catch (Exception e) {
			log.error("Error en isValidoEnBloque ---> "+e);
		}
		return validPresupuesto;
	}
	
	//VALIDANDO DATOS INGRESADOS PARA GRABACION TAB "POR PARTIDA" 
	public Boolean isValidoPorPartida(Presupuesto beanPresupuesto) {
		Boolean validPresupuesto = true;
		limpiarMsgErrorPresupuesto();
		try {
			//1. Validación de Periodo
			if (intAnioGrabacion == null || intAnioGrabacion == 0) {
				setStrMsgTxtPeriodo("Debe seleccionar un Año.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtPeriodo("");
			}
			//2. Validación de Cuenta Contable
			if (strCuentaGrb == null) {
				setStrMsgTxtAddCtaContable("Debe seleccionar una Cuenta.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtAddCtaContable("");
			}	
			//3. Validación de Sucursal
			if (intIdSucursalGrabacion == null || intIdSucursalGrabacion == 0) {
				setStrMsgTxtSucursal("Debe seleccionar una Sucursal.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtSucursal("");
			}			
			//4. Validación de Sub-Sucursal
			if (intIdSubSucursalGrabacion == null || intIdSubSucursalGrabacion == 0) {
				setStrMsgTxtSubSucursal("Debe seleccionar una Sub-Sucursal.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtSubSucursal("");
			}
			//5. Validación de Tipo Moneda
			if (intTipoMoneda == null || intTipoMoneda== 0) {
				setStrMsgTxtTipoMoneda("Debe seleccionar un Tipo de Moneda.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtTipoMoneda("");
			}		
			//6. Validación de Montos Proyectados
			if (bdMontoProyEnero==null && bdMontoProyFebrero==null && bdMontoProyMarzo==null && bdMontoProyAbril==null && bdMontoProyMayo==null && bdMontoProyJunio==null
					&& bdMontoProyJulio==null && bdMontoProyAgosto==null && bdMontoProySeptiembre==null && bdMontoProyOctubre==null && bdMontoProyNoviembre==null && bdMontoProyDiciembre==null) {
				setStrMsgTxtMonto("Debe ingresar al menos un Monto Proyectado.");
				validPresupuesto = false;
			} else {
				setStrMsgTxtMonto("");
			}
		} catch (Exception e) {
			log.error("Error en isValidoPorPartida ---> "+e);
		}
		return validPresupuesto;
	}
	
	//SELECCIONAR FILA EN BUSQUEDA SOCIO POR NOMBRES Y APELLIDOS
	public void setSelectedPresupuesto(){
		log.info("-------------------------------------Debugging PresupuestoController.setSelectedPresupuesto-------------------------------------");
		log.info("activeRowKey: "+getRequestParameter("rowPresupuesto"));
		setPresupuestoSelected(null);
		String selectedRow = getRequestParameter("rowPresupuesto");
		Presupuesto presupuesto = new Presupuesto();
		for(int i=0; i<listaPresupuestosPorFiltros.size(); i++){
			presupuesto = listaPresupuestosPorFiltros.get(i);
			if(i == Integer.parseInt(selectedRow)){
				setPresupuestoSelected(presupuesto);
				break;
			}
		}
	}
	
	public void getListSubSucursalDeSucBusq() {
		log.info("-------------------------------------Debugging PresupuestoController.getListSubSucursalDeSucBusq-------------------------------------");
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
	
	public void getListSubSucursalDeSucGrb() {
		log.info("-------------------------------------Debugging PresupuestoController.getListSubsucursalDeSucursal-------------------------------------");
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
	
	public void getListAnios() {
		log.info("-------------------------------------Debugging PresupuestoController.getListAnios-------------------------------------");
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
		log.info("-------------------------------------Debugging PresupuestoController.getAnioProyectado-------------------------------------");
		
		Integer intSelectedAnioBase = Integer.valueOf(getRequestParameter("pCboAnioBase"));
		if (intSelectedAnioBase!=0) {
			setIntAnioProyectado(intSelectedAnioBase+1);
		}else {
			setIntAnioProyectado(null);
		}
			
	}
	
	public void disabledBtnAddCtaContable(){
		log.info("-------------------------------------Debugging PresupuestoController.disabledBtnBusqCtaContable-------------------------------------");
		if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE)) {
			log.info("pRbCtaContable: "+getRequestParameter("pRbCtaContable"));
			String pFiltroRadio = getRequestParameter("pRbCtaContable");
			if (pFiltroRadio.equals("1")) {
				blnDisabledAddCtaContable = Boolean.TRUE;
			}else {
				blnDisabledAddCtaContable = Boolean.FALSE;
			}
		}
		if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_POR_PARTIDA)) {
			
		}
	}
	
	public void disabledTxtCuentaBusq(){
		log.info("-------------------------------------Debugging PresupuestoController.disabledTxtCuentaBusq-------------------------------------");
		log.info("pCboTipoCuentaBusq: "+getRequestParameter("pCboTipoCuentaBusq"));
		String pFiltroCombo = getRequestParameter("pCboTipoCuentaBusq");
		if(pFiltroCombo.equals("0")){
			blnDisabledTxtCuentaBusq = Boolean.TRUE;
			setStrCuentaBusq("");
		}else{
			blnDisabledTxtCuentaBusq = Boolean.FALSE;
		}
	}
	
	public void disabledCboSucursal(){
		log.info("-------------------------------------Debugging PresupuestoController.disabledCboSucursal-------------------------------------");
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
	
	public void habilitarCombos() {
		//Boton Agregar Cuenta
		if (rbCtaContable.equals("1")) {
			blnDisabledAddCtaContable = Boolean.TRUE;
		}else {
			blnDisabledAddCtaContable = Boolean.FALSE;
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
	
	public void showPanelEnBloque() {
		log.info("-------------------------------------Debugging PresupuestoController.showPanelEnBloque-------------------------------------");
		blnShowPanelTabPorPartida = Boolean.FALSE;
		blnShowPanelTabEnBloque = Boolean.TRUE;
		blnDisabledGrabar = Boolean.FALSE;
		
		limpiarPanelEnBloque();
		//limpiarPanelPorPartida();
		limpiarMsgErrorPresupuesto();
		
		habilitarCombos();
		intTipoGrabacion = Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE;
	}
	
	public void showPanelPorPartida() {
		log.info("-------------------------------------Debugging PresupuestoController.showPanelPorPartida-------------------------------------");
		blnShowPanelTabPorPartida = Boolean.TRUE;
		blnShowPanelTabEnBloque = Boolean.FALSE;
		blnDisabledGrabar = Boolean.FALSE;
		
		limpiarPanelEnBloque();
		limpiarPanelPorPartida();
		limpiarMsgErrorPresupuesto();
		
		intTipoGrabacion = Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_POR_PARTIDA;
	}
	
	//PARA EL BOTON DEL POPUP "mpModificaEnProcesoDeGrabacion"
	public void cancelarModificadion() {
		if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_EN_BLOQUE)) {
			showPanelEnBloque();
		}
		if (intTipoGrabacion.equals(Constante.PARAM_T_PRESUPUESTO_TIPOGRABACION_POR_PARTIDA)) {
			showPanelPorPartida();
		}
	}
	
	public void limpiarMpAddCuentaContable() {
		mpIntTipoCuentaBusq = 0;
		mpIntPeriodoBusq = null;
		mpStrCuentaBusq = null;
		listaPlanCuenta.clear();
	}
	
	public void limpiarFormularioPresupuesto() {
		intTipoGrabacion = 0;
		blnDisabledGrabar = Boolean.TRUE;
		limpiarFiltrosDeBusqueda();
		limpiarPanelPorPartida();
		limpiarPanelEnBloque();
		limpiarMsgErrorPresupuesto();
	}
	
	public void limpiarFiltrosDeBusqueda() {
		intAnioBusqueda = 0;
		intCboTipoCuentaBusq = 0;		
		strCuentaBusq = "";
		intIdSucursalBusqueda = 0;
		intIdSubSucursalBusqueda = 0;
		listaPresupuestosPorFiltros = null;		
		blnShowPanelTabPorPartida = Boolean.FALSE;
		blnShowPanelTabEnBloque = Boolean.FALSE;
	}
	
	public void limpiarPanelEnBloque() {
		intAnioBase = 0;
		intAnioProyectado = null;
		intMesDesde = 0;
		intMesHasta = 0;
		bdPorcentajeCrecimiento = null;
		
		rbCtaContable = "1";
		rbSucursal = "1";
		habilitarCombos();
		
		strCuentaGrb = null;
		
		intIdSucursalGrabacion = 0;
		intIdSubSucursalGrabacion = 0;
		
		beanPresupuesto = new Presupuesto();
		beanPresupuesto.setId(new PresupuestoId());
	}
	
	public void limpiarPanelPorPartida() {
		intAnioGrabacion = 0;
		intIdSucursalGrabacion = 0;
		intIdSubSucursalGrabacion = 0;
		intTipoMoneda = 0;
		strCuentaGrb = null;
		
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
		blnDisabledTipoMoneda = Boolean.FALSE;
		blnDisabledSucursal = Boolean.FALSE;
		blnDisabledSubSucursal = Boolean.FALSE;
		blnDisabledAddCtaContable = Boolean.TRUE;
		
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
		
		beanPresupuesto = new Presupuesto();
		beanPresupuesto.setId(new PresupuestoId());
	}
	public void limpiarMsgErrorPresupuesto() {
		setStrMsgTxtPeriodo("");
		setStrMsgTxtMes("");
		setStrMsgTxtAddCtaContable("");
		setStrMsgTxtSucursal("");
		setStrMsgTxtSubSucursal("");
		setStrMsgTxtPorcentajeCrecimiento("");
		setStrMsgTxtRangoMeses("");
		setStrMsgTxtMonto("");
		setStrMsgTxtTipoMoneda("");
	}
	
	public void cleanStrCuentaGrb() {
		strCuentaGrb = null;
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
//        
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
			limpiarFormularioPresupuesto();
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
		PresupuestoController.log = log;
	}

	public Boolean getBlnShowPanelTabEnBloque() {
		return blnShowPanelTabEnBloque;
	}

	public void setBlnShowPanelTabEnBloque(Boolean blnShowPanelTabEnBloque) {
		this.blnShowPanelTabEnBloque = blnShowPanelTabEnBloque;
	}

	public Presupuesto getBeanPresupuesto() {
		return beanPresupuesto;
	}

	public void setBeanPresupuesto(Presupuesto beanPresupuesto) {
		this.beanPresupuesto = beanPresupuesto;
	}

	public TablaFacadeRemote getTablaFacade() {
		return tablaFacade;
	}

	public void setTablaFacade(TablaFacadeRemote tablaFacade) {
		this.tablaFacade = tablaFacade;
	}

	public AuditoriaFacadeRemote getAuditoriaFacade() {
		return auditoriaFacade;
	}

	public void setAuditoriaFacade(AuditoriaFacadeRemote auditoriaFacade) {
		this.auditoriaFacade = auditoriaFacade;
	}

	public PresupuestoFacadeLocal getPresupuestoFacade() {
		return presupuestoFacade;
	}

	public void setPresupuestoFacade(PresupuestoFacadeLocal presupuestoFacade) {
		this.presupuestoFacade = presupuestoFacade;
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

	public List<Tabla> getListaDescripcionEstado() {
		return listaDescripcionEstado;
	}

	public void setListaDescripcionEstado(List<Tabla> listaDescripcionEstado) {
		this.listaDescripcionEstado = listaDescripcionEstado;
	}

	public Presupuesto getPresupuestoSelected() {
		return presupuestoSelected;
	}

	public void setPresupuestoSelected(Presupuesto presupuestoSelected) {
		this.presupuestoSelected = presupuestoSelected;
	}

	public Integer getIntTipoGrabacion() {
		return intTipoGrabacion;
	}

	public void setIntTipoGrabacion(Integer intTipoGrabacion) {
		this.intTipoGrabacion = intTipoGrabacion;
	}

	public Integer getIntAnioBusqueda() {
		return intAnioBusqueda;
	}

	public void setIntAnioBusqueda(Integer intAnioBusqueda) {
		this.intAnioBusqueda = intAnioBusqueda;
	}

	public Integer getIntCboTipoCuentaBusq() {
		return intCboTipoCuentaBusq;
	}

	public void setIntCboTipoCuentaBusq(Integer intCboTipoCuentaBusq) {
		this.intCboTipoCuentaBusq = intCboTipoCuentaBusq;
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

	public List<Presupuesto> getListaPresupuestosPorFiltros() {
		return listaPresupuestosPorFiltros;
	}

	public void setListaPresupuestosPorFiltros(
			List<Presupuesto> listaPresupuestosPorFiltros) {
		this.listaPresupuestosPorFiltros = listaPresupuestosPorFiltros;
	}

	public PlanCuentaFacadeRemote getPlanCuentaFacade() {
		return planCuentaFacade;
	}

	public void setPlanCuentaFacade(PlanCuentaFacadeRemote planCuentaFacade) {
		this.planCuentaFacade = planCuentaFacade;
	}

	public String getStrCuentaBusq() {
		return strCuentaBusq;
	}

	public void setStrCuentaBusq(String strCuentaBusq) {
		this.strCuentaBusq = strCuentaBusq;
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

	public Integer getIntAnioBase() {
		return intAnioBase;
	}

	public void setIntAnioBase(Integer intAnioBase) {
		this.intAnioBase = intAnioBase;
	}

	public Integer getIntAnioProyectado() {
		return intAnioProyectado;
	}

	public void setIntAnioProyectado(Integer intAnioProyectado) {
		this.intAnioProyectado = intAnioProyectado;
	}

	public String getRbSucursal() {
		return rbSucursal;
	}

	public BigDecimal getBdPorcentajeCrecimiento() {
		return bdPorcentajeCrecimiento;
	}

	public void setBdPorcentajeCrecimiento(BigDecimal bdPorcentajeCrecimiento) {
		this.bdPorcentajeCrecimiento = bdPorcentajeCrecimiento;
	}

	public void setRbSucursal(String rbSucursal) {
		this.rbSucursal = rbSucursal;
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

	public Integer getIntErrorPresupuestoPorAnioBase() {
		return intErrorPresupuestoPorAnioBase;
	}

	public void setIntErrorPresupuestoPorAnioBase(
			Integer intErrorPresupuestoPorAnioBase) {
		this.intErrorPresupuestoPorAnioBase = intErrorPresupuestoPorAnioBase;
	}

	public List<Presupuesto> getListaPresupuestosDelAnioBase() {
		return listaPresupuestosDelAnioBase;
	}

	public void setListaPresupuestosDelAnioBase(
			List<Presupuesto> listaPresupuestosDelAnioBase) {
		this.listaPresupuestosDelAnioBase = listaPresupuestosDelAnioBase;
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

	public Boolean getBlnDisabledAnioProy() {
		return blnDisabledAnioProy;
	}

	public void setBlnDisabledAnioProy(Boolean blnDisabledAnioProy) {
		this.blnDisabledAnioProy = blnDisabledAnioProy;
	}

	public Boolean getBlnDisabledGrabar() {
		return blnDisabledGrabar;
	}

	public void setBlnDisabledGrabar(Boolean blnDisabledGrabar) {
		this.blnDisabledGrabar = blnDisabledGrabar;
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

	public String getRbCtaContable() {
		return rbCtaContable;
	}

	public void setRbCtaContable(String rbCtaContable) {
		this.rbCtaContable = rbCtaContable;
	}

	public Boolean getBlnDisabledAddCtaContable() {
		return blnDisabledAddCtaContable;
	}

	public void setBlnDisabledAddCtaContable(Boolean blnDisabledAddCtaContable) {
		this.blnDisabledAddCtaContable = blnDisabledAddCtaContable;
	}

	public String getStrMsgTxtEstCierre() {
		return strMsgTxtEstCierre;
	}

	public void setStrMsgTxtEstCierre(String strMsgTxtEstCierre) {
		this.strMsgTxtEstCierre = strMsgTxtEstCierre;
	}

	public String getStrMsgTxtPeriodo() {
		return strMsgTxtPeriodo;
	}

	public void setStrMsgTxtPeriodo(String strMsgTxtPeriodo) {
		this.strMsgTxtPeriodo = strMsgTxtPeriodo;
	}

	public String getStrMsgTxtMes() {
		return strMsgTxtMes;
	}

	public void setStrMsgTxtMes(String strMsgTxtMes) {
		this.strMsgTxtMes = strMsgTxtMes;
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

	public String getStrMsgTxtExisteRegistro() {
		return strMsgTxtExisteRegistro;
	}

	public void setStrMsgTxtExisteRegistro(String strMsgTxtExisteRegistro) {
		this.strMsgTxtExisteRegistro = strMsgTxtExisteRegistro;
	}

	public String getStrMsgTxtRangoMeses() {
		return strMsgTxtRangoMeses;
	}

	public void setStrMsgTxtRangoMeses(String strMsgTxtRangoMeses) {
		this.strMsgTxtRangoMeses = strMsgTxtRangoMeses;
	}

	public String getStrMsgTxtPorcentajeCrecimiento() {
		return strMsgTxtPorcentajeCrecimiento;
	}

	public void setStrMsgTxtPorcentajeCrecimiento(
			String strMsgTxtPorcentajeCrecimiento) {
		this.strMsgTxtPorcentajeCrecimiento = strMsgTxtPorcentajeCrecimiento;
	}

	public String getStrCuentaGrb() {
		return strCuentaGrb;
	}

	public void setStrCuentaGrb(String strCuentaGrb) {
		this.strCuentaGrb = strCuentaGrb;
	}

	public String getStrMsgTxtAddCtaContable() {
		return strMsgTxtAddCtaContable;
	}

	public void setStrMsgTxtAddCtaContable(String strMsgTxtAddCtaContable) {
		this.strMsgTxtAddCtaContable = strMsgTxtAddCtaContable;
	}

	public Integer getIntAnioGrabacion() {
		return intAnioGrabacion;
	}

	public void setIntAnioGrabacion(Integer intAnioGrabacion) {
		this.intAnioGrabacion = intAnioGrabacion;
	}

	public List<Presupuesto> getListaValidaPresupuesto() {
		return listaValidaPresupuesto;
	}

	public void setListaValidaPresupuesto(List<Presupuesto> listaValidaPresupuesto) {
		this.listaValidaPresupuesto = listaValidaPresupuesto;
	}

	public List<PlanCuenta> getListaPlanCuenta() {
		return listaPlanCuenta;
	}

	public void setListaPlanCuenta(List<PlanCuenta> listaPlanCuenta) {
		this.listaPlanCuenta = listaPlanCuenta;
	}

	public Boolean getBlnDisabledTxtCuentaBusq() {
		return blnDisabledTxtCuentaBusq;
	}

	public void setBlnDisabledTxtCuentaBusq(Boolean blnDisabledTxtCuentaBusq) {
		this.blnDisabledTxtCuentaBusq = blnDisabledTxtCuentaBusq;
	}

	public Integer getMpIntTipoCuentaBusq() {
		return mpIntTipoCuentaBusq;
	}

	public void setMpIntTipoCuentaBusq(Integer mpIntTipoCuentaBusq) {
		this.mpIntTipoCuentaBusq = mpIntTipoCuentaBusq;
	}

	public Integer getMpIntPeriodoBusq() {
		return mpIntPeriodoBusq;
	}

	public void setMpIntPeriodoBusq(Integer mpIntPeriodoBusq) {
		this.mpIntPeriodoBusq = mpIntPeriodoBusq;
	}

	public String getMpStrCuentaBusq() {
		return mpStrCuentaBusq;
	}

	public void setMpStrCuentaBusq(String mpStrCuentaBusq) {
		this.mpStrCuentaBusq = mpStrCuentaBusq;
	}

	public PlanCuenta getPlanCuentaSelected() {
		return planCuentaSelected;
	}

	public void setPlanCuentaSelected(PlanCuenta planCuentaSelected) {
		this.planCuentaSelected = planCuentaSelected;
	}

	public Integer getIntErrorPlanCuenta() {
		return intErrorPlanCuenta;
	}

	public void setIntErrorPlanCuenta(Integer intErrorPlanCuenta) {
		this.intErrorPlanCuenta = intErrorPlanCuenta;
	}

	public Integer getIntTipoMoneda() {
		return intTipoMoneda;
	}

	public void setIntTipoMoneda(Integer intTipoMoneda) {
		this.intTipoMoneda = intTipoMoneda;
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

	public Boolean getBlnDisabledAnio() {
		return blnDisabledAnio;
	}

	public void setBlnDisabledAnio(Boolean blnDisabledAnio) {
		this.blnDisabledAnio = blnDisabledAnio;
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

	public Boolean getBlnShowPanelTabPorPartida() {
		return blnShowPanelTabPorPartida;
	}

	public void setBlnShowPanelTabPorPartida(Boolean blnShowPanelTabPorPartida) {
		this.blnShowPanelTabPorPartida = blnShowPanelTabPorPartida;
	}

	public String getStrMsgTxtMonto() {
		return strMsgTxtMonto;
	}

	public void setStrMsgTxtMonto(String strMsgTxtMonto) {
		this.strMsgTxtMonto = strMsgTxtMonto;
	}

	public String getStrMsgTxtTipoMoneda() {
		return strMsgTxtTipoMoneda;
	}

	public void setStrMsgTxtTipoMoneda(String strMsgTxtTipoMoneda) {
		this.strMsgTxtTipoMoneda = strMsgTxtTipoMoneda;
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

	public Boolean getBlnDisabledTipoMoneda() {
		return blnDisabledTipoMoneda;
	}

	public void setBlnDisabledTipoMoneda(Boolean blnDisabledTipoMoneda) {
		this.blnDisabledTipoMoneda = blnDisabledTipoMoneda;
	}

	public Usuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public void setUsuarioSesion(Usuario usuarioSesion) {
		this.usuarioSesion = usuarioSesion;
	}	
}