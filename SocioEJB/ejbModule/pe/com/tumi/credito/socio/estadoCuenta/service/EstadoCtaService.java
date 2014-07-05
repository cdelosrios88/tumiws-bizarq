package pe.com.tumi.credito.socio.estadoCuenta.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaId;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.cobranza.gestion.facade.GestionCobranzaFacadeRemote;
import pe.com.tumi.cobranza.planilla.domain.CobroPlanillas;
import pe.com.tumi.cobranza.planilla.domain.Efectuado;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoConcepto;

import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.EnviadoEfectuadoComp;
import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;

import pe.com.tumi.cobranza.planilla.facade.PlanillaFacadeRemote;
import pe.com.tumi.cobranza.prioridad.domain.PrioridadDescuento;
import pe.com.tumi.cobranza.prioridad.facade.PrioridadDescuentoFacadeRemote;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.bo.CuentaIntegranteBO;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.facade.CaptacionFacadeLocal;
import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.core.domain.SocioPK;
import pe.com.tumi.credito.socio.core.service.SocioService;
import pe.com.tumi.credito.socio.creditos.bo.CreditoBO;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.facade.CreditoFacadeLocal;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.MovimientoEstCtaComp;
import pe.com.tumi.credito.socio.estadoCuenta.domain.composite.PrevisionSocialComp;

import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;
import pe.com.tumi.movimiento.concepto.domain.composite.ExpedienteComp;
import pe.com.tumi.movimiento.concepto.facade.ConceptoFacadeRemote;
import pe.com.tumi.parametro.tabla.domain.Tabla;
import pe.com.tumi.parametro.tabla.facade.TablaFacadeRemote;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.facade.PersonaFacadeRemote;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;
import pe.com.tumi.riesgo.cartera.facade.CarteraFacadeRemote;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;
import pe.com.tumi.servicio.prevision.domain.composite.ExpedientePrevisionComp;
import pe.com.tumi.servicio.prevision.facade.PrevisionFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.ExpedienteCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;
import pe.com.tumi.servicio.solicitudPrestamo.facade.GarantiaCreditoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.facade.PrestamoFacadeRemote;
import pe.com.tumi.servicio.solicitudPrestamo.facade.SolicitudPrestamoFacadeNuevoRemote;

public class EstadoCtaService {
	protected static Logger log = Logger.getLogger(EstadoCtaService.class);
	private SocioService socioService = (SocioService)TumiFactory.get(SocioService.class);
	private CuentaIntegranteBO boCuentaIntegrante = (CuentaIntegranteBO)TumiFactory.get(CuentaIntegranteBO.class);
	private CreditoBO boCredito = (CreditoBO)TumiFactory.get(CreditoBO.class);
	private Calendar calen = Calendar.getInstance();
	private TablaFacadeRemote tablaFacade;
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.09.2013 
	 * Recupera lista de personas garantizadas (Datos Grales. y Prestamos).
	 * @param gCred
	 * @return
	 * @throws BusinessException
	 */
	public List<GarantiaCreditoComp> getListaGarantiaCreditoCompPorEmpPersCta(GarantiaCredito gCred) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaGarantiaCreditoCompPorEmpPersCta-------------------------------------");
		List<GarantiaCreditoComp> lstRetorna = new ArrayList<GarantiaCreditoComp>();
		List<CuentaIntegrante> listaCtaIntegrante = new ArrayList<CuentaIntegrante>();
		SocioComp socioComp = null;
		SocioPK socioPK = null;
		CuentaId ctaId = null;
		GarantiaCreditoComp gCredComp = null;		
		try {
			GarantiaCreditoFacadeRemote grantiaCreditoFacade = (GarantiaCreditoFacadeRemote)EJBFactory.getRemote(GarantiaCreditoFacadeRemote.class);
			SolicitudPrestamoFacadeNuevoRemote solicitudPrestamoFacade = (SolicitudPrestamoFacadeNuevoRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeNuevoRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			List<GarantiaCredito> listaGarantiaCredito = grantiaCreditoFacade.getListaGarantiaCreditoPorEmpPersCta(gCred);
			List<EstadoCredito> lstEstadoCredito = new ArrayList<EstadoCredito>();
			List<CuentaConcepto> listaCuentaConcepto = new ArrayList<CuentaConcepto>();
			List<CuentaConceptoDetalle> listaCuentaConceptoDetalle = new ArrayList<CuentaConceptoDetalle>();
			ctaId 		= new CuentaId();
			gCredComp 	= new GarantiaCreditoComp();
			socioPK 	= new SocioPK();
			
			if (listaGarantiaCredito!=null &&!listaGarantiaCredito.isEmpty()) {
				for (GarantiaCredito lstGarantiaCred : listaGarantiaCredito) {
					ctaId.setIntPersEmpresaPk(lstGarantiaCred.getId().getIntPersEmpresaPk());
					ctaId.setIntCuenta(lstGarantiaCred.getId().getIntCuentaPk());
					listaCtaIntegrante = boCuentaIntegrante.getListaCuentaIntegrantePorPKCuenta(ctaId);
					if (listaCtaIntegrante!=null && !listaCtaIntegrante.isEmpty()) {
						for (CuentaIntegrante lstCtaInt : listaCtaIntegrante) {
							socioPK.setIntIdEmpresa(lstCtaInt.getId().getIntPersEmpresaPk());
							socioPK.setIntIdPersona(lstCtaInt.getId().getIntPersonaIntegrante());
						}
					}
					socioComp = socioService.getSocioNatural(socioPK);					
					//MOVIMIENTOS DE LA PERSONA GARANTIZADA
					ExpedienteCreditoId expCredId = new ExpedienteCreditoId();
					expCredId.setIntCuentaPk(lstGarantiaCred.getId().getIntCuentaPk());
					expCredId.setIntPersEmpresaPk(lstGarantiaCred.getId().getIntPersEmpresaPk());
					expCredId.setIntItemExpediente(lstGarantiaCred.getId().getIntItemExpediente());
					expCredId.setIntItemDetExpediente(lstGarantiaCred.getId().getIntItemDetExpediente());
					lstEstadoCredito = solicitudPrestamoFacade.getListaPorExpedienteCreditoPkYEstadoCredito(expCredId,Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO);
					ExpedienteComp expedienteComp = new ExpedienteComp();
					Integer intItem = 1;
					BigDecimal bdSaldoAporte = BigDecimal.ZERO;
					if (lstEstadoCredito != null && !lstEstadoCredito.isEmpty()) {
						for (EstadoCredito estCred : lstEstadoCredito) {
							Expediente expediente = new Expediente();
							expediente.getId().setIntCuentaPk(lstGarantiaCred.getId().getIntCuentaPk());
							expediente.getId().setIntPersEmpresaPk(lstGarantiaCred.getId().getIntPersEmpresaPk());
							expediente.getId().setIntItemExpediente(lstGarantiaCred.getId().getIntItemExpediente());
							expediente.getId().setIntItemExpedienteDetalle(lstGarantiaCred.getId().getIntItemDetExpediente());
							expediente = conceptoFacade.getExpedientePorPK(expediente.getId());
							if (expediente != null) {
								if (expediente.getBdSaldoCredito().compareTo(BigDecimal.ZERO)>0) {
									//ITEM
									log.info("--- ITEM: "+intItem);	
									//APORTE
									ctaId = new CuentaId();
									ctaId.setIntPersEmpresaPk(lstGarantiaCred.getId().getIntPersEmpresaPk());
									ctaId.setIntCuenta(lstGarantiaCred.getId().getIntCuentaPk());									
									listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(ctaId);
									if (listaCuentaConcepto != null && !listaCuentaConcepto.isEmpty()) {
										for (CuentaConcepto ctaCto : listaCuentaConcepto) {
											listaCuentaConceptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(ctaCto.getId());
											if (listaCuentaConceptoDetalle != null && !listaCuentaConceptoDetalle.isEmpty()) {
												for (CuentaConceptoDetalle ctaCtoDet : listaCuentaConceptoDetalle) {
													if (ctaCtoDet.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCUENTA_APORTES)) {
														bdSaldoAporte = ctaCto.getBdSaldo();
													}
												}
											}
										}
									}
									log.info("--- APORTE: "+bdSaldoAporte);
									//FECHA
									log.info("--- FECHA: "+estCred.getTsFechaEstado());	
									//DESCRIPCION DEL PRESTAMO
									Credito credito = new Credito();
									CreditoId creditoId = new CreditoId();
									creditoId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
									creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
									creditoId.setIntItemCredito(expediente.getIntItemCredito());
									credito = boCredito.getCreditoPorPK(creditoId);
									expediente.setStrDescripcion(credito.getStrDescripcion());
									log.info("--- CONCEPTO: "+expediente.getStrDescripcion());
									//SOLICITUD
									log.info("--- SOLICITUD: "+expediente.getId().getIntItemExpediente()+""+expediente.getId().getIntItemExpedienteDetalle());
									//MONTO TOTAL
									log.info("--- MONTO TOTAL: "+expediente.getBdMontoTotal());
									//CRONOGRAMA
									List<Cronograma> lstCronograma = new ArrayList<Cronograma>();
									lstCronograma = conceptoFacade.getListaCronogramaPorPkExpediente(expediente.getId());
									Integer cantCtasPagadas = 0;
									Integer cantCtasAtrasadas = 0;
									if (lstCronograma != null && !lstCronograma.isEmpty()) {
										for (Cronograma cronograma : lstCronograma) {
											Timestamp  timestamp = new Timestamp(calen.getTimeInMillis());
											//EL CONCEPTO CREDITO DEBE DE SER CAPITAL (IntParaTipoConceptoCreditoCod = 1) Y NO SE DEBE DE TOMAR EN CUENTA LA CUOTA 0 (intNumeroCuota!=0)
											//CUOTAS PAGADAS: FECHA DE VENCIMIENTO (tsFechaVencimiento) < QUE LA FECHA ACTUAL Y SALDO CREDITO (bdSaldoDetalleCredito) = 0
											if ((cronograma.getIntNumeroCuota().compareTo(0)!=0) && (cronograma.getIntParaTipoConceptoCreditoCod() == 1) && (timestamp.compareTo(cronograma.getTsFechaVencimiento()) >= 0) && (cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)==0)) {
												cantCtasPagadas ++;
											}
											//CUOTAS ATRASADAS: FECHA DE VENCIMIENTO (tsFechaVencimiento) < QUE LA FECHA ACTUAL Y SALDO CREDITO (bdSaldoDetalleCredito) != 0
											if ((cronograma.getIntNumeroCuota().compareTo(0)!=0) && (cronograma.getIntParaTipoConceptoCreditoCod() == 1) && (timestamp.compareTo(cronograma.getTsFechaVencimiento()) >= 0) && (cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)!=0)) {
												cantCtasAtrasadas ++;
											}
										}
									}
									String strCuotas = expediente.getIntNumeroCuota()+"/"+cantCtasPagadas+"/"+cantCtasAtrasadas;
									log.info("--- CUOTAS: "+strCuotas);
									//SALDO
									log.info("--- SALDO: "+expediente.getBdSaldoCredito());
									//SETEANDO DATOS A EXPEDIENTE COMP
									expedienteComp.setExpediente(expediente);
									expedienteComp.setStrCuotas(strCuotas);
									expedienteComp.setTsFechaSolicitud(estCred.getTsFechaEstado());
									expedienteComp.setStrFechaSolicitud(Constante.sdf.format(estCred.getTsFechaEstado()));
									//AGREGANDO A LA LISTA DE RETORNO
									gCredComp.setIntItem(intItem);
									gCredComp.setBdSaldoAporte(bdSaldoAporte);
									gCredComp.setSocioComp(socioComp);
									gCredComp.setExpedienteComp(expedienteComp);
				       				lstRetorna.add(gCredComp);
				       				intItem++;
				       				gCredComp = new GarantiaCreditoComp();
								}																	
							}
						}
					}
            	}
			}
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 10.09.2013
	 * Recupera Envio Concepto, Envio Monto, Efectuado, Efectuado Concepto y Prioridad Descuento por Cuenta PK y desde un peiorio en adelante.
	 * @param cuentaComp
	 * @param intPeriodo
	 * @return
	 * @throws BusinessException
	 */
	public List<EnviadoEfectuadoComp> getListaEnviadoEfectuado(CuentaComp cuentaComp, Integer intPeriodo) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaEnviadoEfectuado-------------------------------------");
		List<Envioconcepto> listaEnvioconcepto = null;
		List<Envioconcepto> listaEnvioconceptoPorPeriodo = null;
		List<Enviomonto> listaEnviomonto = null;
		List<Efectuado> listaEfectuado = null;
		List<EfectuadoConcepto> listaEfectuadoConcepto = null;
		List<EfectuadoResumen> listaEfectuadoResumen = null;
		List<CobroPlanillas> listaCobroPlanillas = null;
		List<EnviadoEfectuadoComp> listaEnviadoEfectuadoComp = new ArrayList<EnviadoEfectuadoComp>();		
		List<PrioridadDescuento> listaPrioridadDescuento = null;		
		EnviadoEfectuadoComp enviadoEfectuadoComp = new EnviadoEfectuadoComp();
		BigDecimal bdSumEnviomonto = BigDecimal.ZERO;
		BigDecimal bdSumEfectuado = BigDecimal.ZERO;
		try {
			PlanillaFacadeRemote planillaFacade = (PlanillaFacadeRemote)EJBFactory.getRemote(PlanillaFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			PrioridadDescuentoFacadeRemote prioridadDescuentoFacade = (PrioridadDescuentoFacadeRemote)EJBFactory.getRemote(PrioridadDescuentoFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			List<Tabla> listaDescPrioridadPorTipoCptoGral = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCONCEPTOGENERAL));
			List<Tabla> listaDescPrioridadPorTipoCredito = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_CREDITO));
			
			listaEnvioconcepto = planillaFacade.getListaPorCuentaYPeriodo(cuentaComp.getCuenta().getId().getIntPersEmpresaPk(), cuentaComp.getCuenta().getId().getIntCuenta(), intPeriodo); //cuentaComp.getCuentaIntegrante().getId().getIntCuenta()
			if (listaEnvioconcepto!=null && !listaEnvioconcepto.isEmpty()) {
				for (Envioconcepto lstEnvCpto : listaEnvioconcepto) {					
					//OBTENER LISTA DE ENVIOCONCEPTO POR PERIODO
					listaEnvioconceptoPorPeriodo = planillaFacade.getListaEnvioconceptoPorCtaYPer(lstEnvCpto.getId().getIntEmpresacuentaPk(),lstEnvCpto.getIntCuentaPk(),lstEnvCpto.getIntPeriodoplanilla());
					if (listaEnvioconceptoPorPeriodo!=null && !listaEnvioconceptoPorPeriodo.isEmpty()) {
						enviadoEfectuadoComp.setListaEnvioconcepto(new ArrayList<Envioconcepto>());
						listaPrioridadDescuento = new ArrayList<PrioridadDescuento>();
						for (Envioconcepto lstEnvCptoXPeriodo : listaEnvioconceptoPorPeriodo) {												
							enviadoEfectuadoComp.getListaEnvioconcepto().add(lstEnvCptoXPeriodo);
							//DIFERENCIA PLANILLA POR PRIORIDAD
							String strDescripcion = "";
							Expediente expediente = new Expediente();
							CuentaConceptoDetalle ctaCptoDet = new CuentaConceptoDetalle();
							PrioridadDescuento prioridadDescuento = null;
							if (lstEnvCptoXPeriodo.getIntItemcuentaconcepto()!=null) {
								CuentaConceptoId ctaCptoId = new CuentaConceptoId();
								ctaCptoId.setIntPersEmpresaPk(lstEnvCptoXPeriodo.getId().getIntEmpresacuentaPk());
								ctaCptoId.setIntCuentaPk(lstEnvCptoXPeriodo.getIntCuentaPk());
								ctaCptoId.setIntItemCuentaConcepto(lstEnvCptoXPeriodo.getIntItemcuentaconcepto());
								List<CuentaConceptoDetalle> listaCuentaConceptoDetalle = new ArrayList<CuentaConceptoDetalle>();
								listaCuentaConceptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(ctaCptoId);
								if (listaCuentaConceptoDetalle!=null && !listaCuentaConceptoDetalle.isEmpty()) {
									for (CuentaConceptoDetalle lstCtaCptoDet : listaCuentaConceptoDetalle) {
										if (lstCtaCptoDet.getTsFin()==null) {
											ctaCptoDet = lstCtaCptoDet;
											log.info("--- Captacion (TipoConcepto)"+lstCtaCptoDet.getIntParaTipoConceptoCod());
											log.info("--- Item Captacion (ItemConcepto)"+lstCtaCptoDet.getIntItemConcepto());
										}										
									} 
								}
								//DESCRIPCION DE LA PRIORIDAD
								for (Tabla descPrioridad : listaDescPrioridadPorTipoCptoGral) {
									if(descPrioridad.getIntIdDetalle().compareTo(lstEnvCptoXPeriodo.getIntTipoconceptogeneralCod())==0){
										strDescripcion = descPrioridad.getStrDescripcion();
										break;
									}
								}
							}else {
								ExpedienteId expId = new ExpedienteId();
								expId.setIntPersEmpresaPk(lstEnvCptoXPeriodo.getId().getIntEmpresacuentaPk());
								expId.setIntCuentaPk(lstEnvCptoXPeriodo.getIntCuentaPk());
								expId.setIntItemExpediente(lstEnvCptoXPeriodo.getIntItemexpediente());
								expId.setIntItemExpedienteDetalle(lstEnvCptoXPeriodo.getIntItemdetexpediente());
								expediente = conceptoFacade.getExpedientePorPK(expId);
								if (expediente.getId().getIntPersEmpresaPk()!=null) {
									log.info("--- Credito (TipoCredito)"+expediente.getIntParaTipoCreditoCod());
									log.info("--- Item Credito (ItemCredito)"+expediente.getIntItemCredito());
								}
								//DESCRIPCION DE LA PRIORIDAD
								if (lstEnvCptoXPeriodo.getIntTipoconceptogeneralCod().compareTo(Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION)==0) {
									for (Tabla descPrioridad : listaDescPrioridadPorTipoCredito) {
										if(descPrioridad.getIntIdDetalle().compareTo(expediente.getIntParaTipoCreditoCod())==0){
											strDescripcion = descPrioridad.getStrDescripcion();
											break;
										}
									}
								}else{
									for (Tabla descPrioridad : listaDescPrioridadPorTipoCptoGral) {
										if(descPrioridad.getIntIdDetalle().compareTo(lstEnvCptoXPeriodo.getIntTipoconceptogeneralCod())==0){
											strDescripcion = descPrioridad.getStrDescripcion();
											break;
										}
									}
								}
							}
							prioridadDescuento = prioridadDescuentoFacade.getPrioridadPorTipoCptoGralCtaCptoExpediente(lstEnvCptoXPeriodo, Constante.PARAM_T_TIPO_MOVIMIENTO_PROCESO_POR_PLANILLA, ctaCptoDet, expediente);
							if (prioridadDescuento!=null) {
								prioridadDescuento.setIntItemcuentaconcepto(lstEnvCptoXPeriodo.getIntItemcuentaconcepto());
								prioridadDescuento.setIntItemexpediente(lstEnvCptoXPeriodo.getIntItemexpediente());
								prioridadDescuento.setIntItemdetexpediente(lstEnvCptoXPeriodo.getIntItemdetexpediente());
								prioridadDescuento.setIntParaTipoconceptogeneral(lstEnvCptoXPeriodo.getIntTipoconceptogeneralCod());
								Integer r = strDescripcion.indexOf("-");
								if (r==-1) {
									prioridadDescuento.setStrDescripcion(strDescripcion);
								}else {
									prioridadDescuento.setStrDescripcion(strDescripcion.substring(0,r-1));
								}								
								listaPrioridadDescuento.add(prioridadDescuento);
							}							
						}
					}
					enviadoEfectuadoComp.setIntPeriodoPlanilla(lstEnvCpto.getIntPeriodoplanilla());
					listaEnviomonto = planillaFacade.getListaPorEnvioConcepto(lstEnvCpto);
					if (listaEnviomonto!=null && !listaEnviomonto.isEmpty()) {
						enviadoEfectuadoComp.setListaEnviomonto(new ArrayList<Enviomonto>());
						for (Enviomonto lstEnvMnto : listaEnviomonto) {
							//ENVIOMONTO: EN TEORIA DEBERIA HABER MAXIMO 3 REGISTROS POR PERIODO Y CUENTA (HABERES - INCENTIVOS - CAS)							
							bdSumEnviomonto = bdSumEnviomonto.add(lstEnvMnto.getBdMontoenvio());
							listaEfectuado = planillaFacade.getListaEfectuadoPorPkEnviomontoYPeriodo(lstEnvMnto.getId(), lstEnvCpto);
							if (listaEfectuado!=null && !listaEfectuado.isEmpty()) {
								enviadoEfectuadoComp.setListaEfectuado(new ArrayList<Efectuado>());
								for (Efectuado lstEfect : listaEfectuado) {
									//EFECTUADO: EN TEORIA DEBERIA HABER MAXIMO 3 REGISTROS POR PERIODO Y CUENTA (HABERES - INCENTIVOS - CAS)									
									bdSumEfectuado = bdSumEfectuado.add(lstEfect.getBdMontoEfectuado());
									//OBTENER "ESTADO DE PAGO DEPENDENCIA"
									listaEfectuadoResumen = planillaFacade.getListaPorEntidadyPeriodo(lstEfect.getId().getIntEmpresacuentaPk(), lstEfect.getIntPeriodoPlanilla(),
											lstEfect.getIntTiposocioCod(), lstEfect.getIntModalidadCod(), lstEfect.getIntNivel(), lstEfect.getIntCodigo());
									if (listaEfectuadoResumen!=null && !listaEfectuadoResumen.isEmpty()) {
										for (EfectuadoResumen lstEfectResumen : listaEfectuadoResumen) {
											listaCobroPlanillas = planillaFacade.getPorEfectuadoResumen(lstEfectResumen);
											if (listaCobroPlanillas!=null && !listaCobroPlanillas.isEmpty()) {
												//COBRADO
												lstEnvMnto.setStrCobroPlanilla("C");
											}else{
												//PENDIENTE
												lstEnvMnto.setStrCobroPlanilla("P");
											}
											enviadoEfectuadoComp.getListaEfectuado().add(lstEfect);
										}
									}
									//OBTENER EFECTUADOCONCEPTO									
									listaEfectuadoConcepto = planillaFacade.getListaPorEfectuado(lstEfect.getId());
									if (listaEfectuadoConcepto!=null && !listaEfectuadoConcepto.isEmpty()) {
										enviadoEfectuadoComp.setListaEfectuadoConcepto(new ArrayList<EfectuadoConcepto>());
										for (EfectuadoConcepto lstEfecCpto : listaEfectuadoConcepto) {											
											enviadoEfectuadoComp.getListaEfectuadoConcepto().add(lstEfecCpto);
										}
									}									
								}
							}else lstEnvMnto.setStrCobroPlanilla("P");
							enviadoEfectuadoComp.getListaEnviomonto().add(lstEnvMnto);
						}
					}
					enviadoEfectuadoComp.setBdSumEnviomonto(bdSumEnviomonto);
					enviadoEfectuadoComp.setBdSumEfectuado(bdSumEfectuado);
					enviadoEfectuadoComp.setListaPrioridadDescuento(listaPrioridadDescuento);
					listaEnviadoEfectuadoComp.add(enviadoEfectuadoComp);
					enviadoEfectuadoComp = new EnviadoEfectuadoComp();
					bdSumEnviomonto = BigDecimal.ZERO;
					bdSumEfectuado = BigDecimal.ZERO;
				}
			}
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaEnviadoEfectuadoComp;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 17.09.2013
	 * Recupera lista de préstamos rechazados de Expediente Credito (Mod.SERVICIOS).
	 * @param cuentaComp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteCreditoComp> getListaPrestamosRechazadosPorCuenta (CuentaComp cuentaComp, Integer intTipoCredito) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaPrestamosRechazadosPorCuenta-------------------------------------");
		List<ExpedienteCreditoComp> lstRetorna = new ArrayList<ExpedienteCreditoComp>();
		List<ExpedienteCredito> lstExpCredServicio = null;
		List<EstadoCredito> lstEstadoCredito = null;
		try {
			SolicitudPrestamoFacadeNuevoRemote solicitudPrestamoFacade = (SolicitudPrestamoFacadeNuevoRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeNuevoRemote.class);
			PrestamoFacadeRemote prestamoFacade = (PrestamoFacadeRemote)EJBFactory.getRemote(PrestamoFacadeRemote.class);
			CreditoFacadeLocal creditoFacade = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);			
			lstExpCredServicio = solicitudPrestamoFacade.getListaExpedienteCreditoPorCuenta(cuentaComp.getCuenta());
			
			if (lstExpCredServicio != null && !lstExpCredServicio.isEmpty()) {
				for (ExpedienteCredito expCred : lstExpCredServicio) {
					if (expCred.getIntParaTipoCreditoCod().equals(intTipoCredito)) {
						ExpedienteCreditoId expCredId = new ExpedienteCreditoId();
						expCredId.setIntCuentaPk(expCred.getId().getIntCuentaPk());
						expCredId.setIntPersEmpresaPk(expCred.getId().getIntPersEmpresaPk());
						expCredId.setIntItemExpediente(expCred.getId().getIntItemExpediente());
						expCredId.setIntItemDetExpediente(expCred.getId().getIntItemDetExpediente());
						lstEstadoCredito = solicitudPrestamoFacade.getListaPorExpedienteCreditoPkYEstadoCredito(expCredId,Constante.PARAM_T_ESTADOSOLICPRESTAMO_RECHAZADO);
						if (lstEstadoCredito != null && !lstEstadoCredito.isEmpty()) {
							ExpedienteCreditoComp expCredComp = new ExpedienteCreditoComp(); 
							BigDecimal bdCuotaMensual = BigDecimal.ZERO;
							for (EstadoCredito estCred : lstEstadoCredito) {
								expCredComp.setExpedienteCredito(expCred);
								//FECHA SOLICITUD
								expCredComp.setStrFechaSolicitud(Constante.sdf.format(estCred.getTsFechaEstado()));
								log.info("--- FECHA SOLICITUD ---"+expCredComp.getStrFechaSolicitud());
								//DESCRIPCION DEL PRESTAMO
								Credito credito = new Credito();
								credito.getId().setIntPersEmpresaPk(expCredId.getIntPersEmpresaPk());
								credito.getId().setIntParaTipoCreditoCod(expCred.getIntParaTipoCreditoCod());
								credito.getId().setIntItemCredito(expCred.getIntItemCredito());
								credito = creditoFacade.getCreditoPorIdCreditoDirecto(credito.getId());
								expCredComp.setStrDescripcionExpedienteCredito(credito.getStrDescripcion());
								log.info("--- DESCRIPCION ---"+expCredComp.getStrDescripcionExpedienteCredito());
								//SOLICITUD
								expCredComp.setStrNroSolicitud(expCred.getId().getIntItemExpediente()+""+expCred.getId().getIntItemDetExpediente());
								log.info("--- SOLICITUD ---"+expCredComp.getStrNroSolicitud());
								//PORCENTAJE INTERES
								log.info("--- PORCENTAJE INTERES ---"+expCredComp.getExpedienteCredito().getBdPorcentajeInteres());
								//CT/CP/CA
								log.info("--- CT/CP/CA ---"+expCredComp.getExpedienteCredito().getIntNumeroCuota());
								//CUOTA MENSUAL
								List<CronogramaCredito> lstCronograma = prestamoFacade.getListaCronogramaCreditoPorExpedienteCredito(expCred);
								if (lstCronograma != null && !lstCronograma.isEmpty()) {
									for (CronogramaCredito cronograma : lstCronograma) {
										bdCuotaMensual = cronograma.getBdMontoConcepto();
										break;						
									}
								}
								expCredComp.setBdCuotaMensual(bdCuotaMensual);
								log.info("--- CUOTA MENSUAL ---"+expCredComp.getBdCuotaMensual());
								//MONTO TOTAL
								log.info("--- MONTO TOTAL ---"+expCredComp.getExpedienteCredito().getBdMontoTotal());
								lstRetorna.add(expCredComp);
							}
						}
					}					
				}
			}							
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 17.09.2013
	 * Recupera lista de préstamos girados o aprobados de Expediente Credito (Mod.MOVIMIENTO).
	 * Girados: Préstamo y Orden de Crédito.
	 * Aprobados: Actividad, Multa y Refinanciado.
	 * @param cuentaComp
	 * @param intEstadoSolicPrestamo
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteComp> getListaPrestamosAprobadosPorCuenta (CuentaComp cuentaComp, Integer intEstadoSolicPrestamo) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaPrestamosAprobadosPorCuenta-------------------------------------");
		List<ExpedienteComp> lstRetorna = new ArrayList<ExpedienteComp>();
		List<ExpedienteCredito> lstExpCredServicio = null;
		List<EstadoCredito> lstEstadoCredito = null;
		List<CarteraCreditoDetalle> listaCarteraCreditoDetalle = null;
		List<Movimiento> listaMovimiento = null;
		
		try {
			SolicitudPrestamoFacadeNuevoRemote solicitudPrestamoFacade = (SolicitudPrestamoFacadeNuevoRemote)EJBFactory.getRemote(SolicitudPrestamoFacadeNuevoRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			CarteraFacadeRemote carteraFacade = (CarteraFacadeRemote)EJBFactory.getRemote(CarteraFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);			
			CreditoFacadeLocal creditoFacade = (CreditoFacadeLocal)EJBFactory.getLocal(CreditoFacadeLocal.class);			
			
			List<Tabla> listaTipoCategoriaRiesgo = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPOCATEGORIADERIESGO));
			
			lstExpCredServicio = solicitudPrestamoFacade.getListaExpedienteCreditoPorCuenta(cuentaComp.getCuenta());
			if (lstExpCredServicio != null && !lstExpCredServicio.isEmpty()) {
				for (ExpedienteCredito expCred : lstExpCredServicio) {
					ExpedienteCreditoId expCredId = new ExpedienteCreditoId();
					expCredId.setIntCuentaPk(expCred.getId().getIntCuentaPk());
					expCredId.setIntPersEmpresaPk(expCred.getId().getIntPersEmpresaPk());
					expCredId.setIntItemExpediente(expCred.getId().getIntItemExpediente());
					expCredId.setIntItemDetExpediente(expCred.getId().getIntItemDetExpediente());
					lstEstadoCredito = solicitudPrestamoFacade.getListaPorExpedienteCreditoPkYEstadoCredito(expCredId,intEstadoSolicPrestamo);
					if (lstEstadoCredito != null && !lstEstadoCredito.isEmpty()) {
						for (EstadoCredito estCred : lstEstadoCredito) {
							Expediente expediente = new Expediente();
							expediente.getId().setIntCuentaPk(estCred.getId().getIntCuentaPk());
							expediente.getId().setIntPersEmpresaPk(estCred.getId().getIntPersEmpresaPk());
							expediente.getId().setIntItemExpediente(estCred.getId().getIntItemExpediente());
							expediente.getId().setIntItemExpedienteDetalle(estCred.getId().getIntItemDetExpediente());
							expediente = conceptoFacade.getExpedientePorPK(expediente.getId());
							ExpedienteComp expComp = new ExpedienteComp();
							BigDecimal bdCuotaMensual = BigDecimal.ZERO;
							Integer intTipoCredFlag = 0;
							if (intEstadoSolicPrestamo.equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_APROBADO)) {
								if (expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_ACTIVIDAD)
										|| expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_MULTA)
										|| expediente.getIntParaTipoCreditoCod().equals(Constante.PARAM_T_TIPO_CREDITO_REFINANCIADO)) {									
								}else{ 
									intTipoCredFlag = 1;
								}
							}
							if (expediente.getId().getIntPersEmpresaPk() != null && intTipoCredFlag==0) {
								expComp.setExpediente(expediente);
								//FECHA SOLICITUD
								expComp.setStrFechaSolicitud(Constante.sdf.format(estCred.getTsFechaEstado()));
								log.info("--- FECHA SOLICITUD ---"+expComp.getStrFechaSolicitud());
								//DESCRIPCION DEL PRESTAMO
								Credito credito = new Credito();
								CreditoId creditoId = new CreditoId();
								creditoId.setIntPersEmpresaPk(expediente.getId().getIntPersEmpresaPk());
								creditoId.setIntParaTipoCreditoCod(expediente.getIntParaTipoCreditoCod());
								creditoId.setIntItemCredito(expediente.getIntItemCredito());
								credito = creditoFacade.getCreditoPorIdCreditoDirecto(creditoId);
								expComp.setStrDescripcionExpediente(credito.getStrDescripcion());
								log.info("--- DESCRIPCION ---"+expComp.getStrDescripcionExpediente());
								//SOLICITUD
								expComp.setStrNroSolicitud(expediente.getId().getIntItemExpediente()+""+expediente.getId().getIntItemExpedienteDetalle());
								log.info("--- SOLICITUD ---"+expComp.getStrNroSolicitud());
								//PORCENTAJE INTERES
								log.info("--- PORCENTAJE INTERES ---"+expediente.getBdPorcentajeInteres());
								//CRONOGRAMA - CT/CP/CA
								List<Cronograma> lstCronograma = conceptoFacade.getListaCronogramaPorPkExpediente(expediente.getId());
								Integer cantCtasPagadas = 0;
								Integer cantCtasAtrasadas = 0;
								if (lstCronograma != null && !lstCronograma.isEmpty()) {
									Integer contador=0;
									for (Cronograma cronograma : lstCronograma) {
										Timestamp  timestamp = new Timestamp(calen.getTimeInMillis());
										// DIFERENCIANDO LOS CONCEPTOS: IntParaTipoConceptoCreditoCod = 1(CAPITAL)  ---  IntParaTipoConceptoCreditoCod = 2(INTERES)
										// CUOTAS PAGADAS 
										if ((cronograma.getIntParaTipoConceptoCreditoCod() == Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION) && (timestamp.compareTo(cronograma.getTsFechaVencimiento()) >= 0) && (cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)==0)) {
											cantCtasPagadas ++;
										}
										// CUOTAS ATRASADAS
										if ((cronograma.getIntParaTipoConceptoCreditoCod() == Constante.PARAM_T_TIPOCONCEPTOGENERAL_AMORTIZACION) && (timestamp.compareTo(cronograma.getTsFechaVencimiento()) >= 0) && (cronograma.getBdSaldoDetalleCredito().compareTo(BigDecimal.ZERO)!=0)) {
											cantCtasAtrasadas ++;
										}
										if (contador==0) {
											bdCuotaMensual = cronograma.getBdMontoConcepto();
										}
										contador++;
									}
								}
								expComp.setStrCuotas(expediente.getIntNumeroCuota()+"/"+cantCtasPagadas+"/"+cantCtasAtrasadas);
								log.info("--- CT/CP/CA ---"+expComp.getStrCuotas());
								//CUOTA MENSUAL
								expComp.setBdCuotaMensual(bdCuotaMensual);
								log.info("--- CUOTA MENSUAL ---"+expComp.getBdCuotaMensual());
								//MONTO TOTAL
								log.info("--- MONTO TOTAL ---"+expediente.getBdMontoTotal());
								//SALDO
								log.info("--- SALDO ---"+expediente.getBdSaldoCredito());
								//CATEGORIA CARTERA
								log.info("--- ENTRANDO A LA CARTERA ----- "+expediente.getId().getIntCuentaPk()+" "+expediente.getId().getIntPersEmpresaPk()+" "+expediente.getId().getIntItemExpediente()+" "+expediente.getId().getIntItemExpedienteDetalle());
								listaCarteraCreditoDetalle = carteraFacade.getListaXExpOCtaCpto(expediente.getId());
								log.info("LLEGA A PASAR EL FACADE");
								String strCategoriaCartera="";
								if (listaCarteraCreditoDetalle!=null && !listaCarteraCreditoDetalle.isEmpty()) {
									Integer intMesActual = Calendar.getInstance().get(Calendar.MONTH)+1;
									Integer intAnioActual = Calendar.getInstance().get(Calendar.YEAR);
									Integer intPeriodoActual = mesAnioToPeriodo(intMesActual,intAnioActual);
									for (CarteraCreditoDetalle lstCarCredDet : listaCarteraCreditoDetalle) {
										log.info("--- PERIODO DE LA CARTERA ---"+lstCarCredDet.getId().getIntCriePperiodocartera());
										log.info("--- PERIODO ACTUAL ---"+intPeriodoActual);
										if (lstCarCredDet.getId().getIntCriePperiodocartera().equals(intPeriodoActual) || lstCarCredDet.getId().getIntCriePperiodocartera().equals(intPeriodoActual-1)) {
											for (Tabla tipCatRiesgo : listaTipoCategoriaRiesgo) {
												log.info("--- IntParaTipocategoriariesgo: "+lstCarCredDet.getIntParaTipocategoriariesgo());
												if (tipCatRiesgo.getIntIdDetalle().compareTo(lstCarCredDet.getIntParaTipocategoriariesgo())==0) {
													strCategoriaCartera = tipCatRiesgo.getStrDescripcion();
													break;
												}
											}
										}										
									}
								}
								expComp.setStrCategoriaCartera(strCategoriaCartera);
								log.info("--- CATEGORIA CARTERA ---"+expComp.getStrCategoriaCartera());
								//GIRO
								listaMovimiento = conceptoFacade.getListXCtaExpediente(expediente.getId().getIntPersEmpresaPk(),expediente.getId().getIntCuentaPk(), expediente.getId().getIntItemExpediente(), expediente.getId().getIntItemExpedienteDetalle());
								if (listaMovimiento!=null && !listaMovimiento.isEmpty()) {
									for (Movimiento lstMov : listaMovimiento) {
										if (lstMov.getIntParaTipoMovimiento().compareTo(Constante.PARAM_T_TIPO_MOVIMIENTO_EGRESO_POR_CAJA)==0) {
											//FECHA
											expComp.setStrFechaGiro(Constante.sdf.format(lstMov.getTsFechaMovimiento()));
											log.info("--- FECHA GIRO ---"+expComp.getStrFechaGiro());
											//DOCUMENTO
											expComp.setStrDocumentoGiro(lstMov.getStrNumeroDocumento());
											log.info("--- DOCUMENTO GIRO ---"+expComp.getStrDocumentoGiro());
											break;
										}
									}
								}
								lstRetorna.add(expComp);
							}
						}
					}
				}
			}							
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 23.09.2013
	 * Recupera Expediente Prevision (Mod.SERVICIO) por Cuenta PK.
	 * @param cuentaComp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedientePrevisionComp> getListaExpedientePrevisionComp (CuentaComp cuentaComp) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaExpedientePrevisionComp-------------------------------------");
		List<ExpedientePrevisionComp> lstRetorna = new ArrayList<ExpedientePrevisionComp>();
		ExpedientePrevisionComp expedientePrevisionComp = new ExpedientePrevisionComp();
		List<ExpedientePrevision> listaExpedientePrevision = null;
		List<EstadoPrevision> listaEstadoPrevision = null;
		List<BeneficiarioPrevision> listaBeneficiarioPrevision = null;
		List<FallecidoPrevision> listaFallecidoPrevision = null;
		Captacion captacion = null;
		Vinculo vinculo = null;
		try {
			PrevisionFacadeRemote previsionFacade = (PrevisionFacadeRemote)EJBFactory.getRemote(PrevisionFacadeRemote.class);
			CaptacionFacadeLocal captacionFacade = (CaptacionFacadeLocal)EJBFactory.getLocal(CaptacionFacadeLocal.class);
			PersonaFacadeRemote personaFacade = (PersonaFacadeRemote)EJBFactory.getRemote(PersonaFacadeRemote.class);
			listaExpedientePrevision = previsionFacade.getListaPorCuenta(cuentaComp.getCuenta());
			if (listaExpedientePrevision!=null && !listaExpedientePrevision.isEmpty()) {
				for (ExpedientePrevision lstExpPrevSoc : listaExpedientePrevision) {
					listaEstadoPrevision = previsionFacade.getEstPrevPorExpediente(lstExpPrevSoc);
					if (listaEstadoPrevision!=null && !listaEstadoPrevision.isEmpty()) {
						for (EstadoPrevision lstEstPrevSoc : listaEstadoPrevision) {
							if (lstEstPrevSoc.getIntParaEstado().equals(Constante.PARAM_T_ESTADOSOLICPRESTAMO_GIRADO)) {
								Integer titularFallecido=0;
								//FECHA SOLICITUD
								expedientePrevisionComp.setStrFechaSolicitud(Constante.sdf.format(lstEstPrevSoc.getTsFechaEstado()));
								log.info("--- FECHA SOLICITUD ---"+expedientePrevisionComp.getStrFechaSolicitud());
								//EXPEDIENTE PREVISION
								expedientePrevisionComp.setExpedientePrevision(lstExpPrevSoc);
								log.info("--- EXPEDIENTE PREVISION ---"+expedientePrevisionComp.getExpedientePrevision());
								//TIPO DE BENEFICIO
								CaptacionId cId = new CaptacionId();
								cId.setIntPersEmpresaPk(lstExpPrevSoc.getIntPersEmpresa());
								cId.setIntParaTipoCaptacionCod(lstExpPrevSoc.getIntParaTipoCaptacion());
								cId.setIntItem(lstExpPrevSoc.getIntItem());
								captacion = captacionFacade.listarCaptacionPorPK(cId);
								if (captacion!=null) {
									expedientePrevisionComp.setStrDescripcionTipoBeneficio(captacion.getStrDescripcion());
									log.info("--- TIPO DE BENEFICIO ---"+expedientePrevisionComp.getStrDescripcionTipoBeneficio());
								}
								//DATOS DEL FALLECIDO, SI EL TIPO DE BENEFICIO ES DISTINTO DE "AES" 
								if (lstExpPrevSoc.getIntParaTipoCaptacion().compareTo(Constante.PARAM_T_TIPOCUENTA_AES)!=0) {
									listaFallecidoPrevision = previsionFacade.getFallecidoPorExpediente(lstExpPrevSoc);
									expedientePrevisionComp.setFallecidoPrevision(new ArrayList<FallecidoPrevision>());									
									if (listaFallecidoPrevision!=null && !listaFallecidoPrevision.isEmpty()) {
										for (FallecidoPrevision lstFallecidoPrevSoc : listaFallecidoPrevision) {
											expedientePrevisionComp.getFallecidoPrevision().add(lstFallecidoPrevSoc);
											if (lstFallecidoPrevSoc.getIntPersPersonaFallecido().equals(cuentaComp.getCuentaIntegrante().getId().getIntPersonaIntegrante())) {
												titularFallecido=1;
											}
											//TIPO DE VINCULO
											if (titularFallecido==1) {
												lstFallecidoPrevSoc.setIntTipoViculo(Constante.PARAM_T_TIPOVINCULO_TITULAR);
											}else {
												vinculo = personaFacade.getVinculoPorId(lstFallecidoPrevSoc.getIntItemViculo());
												lstFallecidoPrevSoc.setIntTipoViculo(vinculo.getIntTipoVinculoCod());
											}											
											log.info("--- TIPO DE VINCULO ---"+lstFallecidoPrevSoc.getIntTipoViculo());
											//NOMBRE - APELLIDOS DEL FALLECIDO
											Natural natural=null;
											if (vinculo!=null) {
												//BUSQUEDA DE DATOS EN PERSONA NATURAL PERSONA NARUTAL
												natural = personaFacade.getNaturalDetalladaPorIdPersona(lstFallecidoPrevSoc.getIntPersPersonaFallecido());
												if (natural!=null) {
													lstFallecidoPrevSoc.setStrNomApeFallecido(natural.getStrNombres()+" "+natural.getStrApellidoPaterno()+" "+natural.getStrApellidoMaterno());
													log.info("--- NOMBRES Y APELLIDOS BENEF ---"+lstFallecidoPrevSoc.getStrNomApeFallecido());
												}
											}
										}
									}
								}
								//BENEFICIARIOS PREVISION: SI EL TIPO DE BENEFICIO ES "AES" O "FDO.RETIRO" O "SEPELIO"
								if (lstExpPrevSoc.getIntParaTipoCaptacion().equals(Constante.PARAM_T_TIPOCUENTA_AES) || (lstExpPrevSoc.getIntParaTipoCaptacion().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_RETIRO))
										|| (lstExpPrevSoc.getIntParaTipoCaptacion().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_SEPELIO))) {
									listaBeneficiarioPrevision = previsionFacade.getBenefPorExpediente(lstExpPrevSoc);
									expedientePrevisionComp.setBeneficiarioPrevision(new ArrayList<BeneficiarioPrevision>());
									if (listaBeneficiarioPrevision!=null && !listaBeneficiarioPrevision.isEmpty()) {
										for (BeneficiarioPrevision lstBenefPrevSoc : listaBeneficiarioPrevision) {
											Integer titularBeneficiario=0;
											if (lstBenefPrevSoc.getIntPersPersonaBeneficiario().equals(cuentaComp.getCuentaIntegrante().getId().getIntPersonaIntegrante())) {
												titularBeneficiario=1;
											}
											//ADICIONO BENEFICIARIOS A LISTA
											expedientePrevisionComp.getBeneficiarioPrevision().add(lstBenefPrevSoc);
											//MONTO NETO
											if (lstExpPrevSoc.getIntParaTipoCaptacion().equals(Constante.PARAM_T_TIPOCUENTA_AES)) {
												lstBenefPrevSoc.setBdMontoNeto(lstExpPrevSoc.getBdMontoBrutoBeneficio());
												log.info("--- MONTO NETO ---"+lstBenefPrevSoc.getBdMontoNeto());
											}
											if (lstExpPrevSoc.getIntParaTipoCaptacion().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_RETIRO)) {
												lstBenefPrevSoc.setBdMontoNeto((lstExpPrevSoc.getBdMontoBrutoBeneficio().add(lstExpPrevSoc.getBdMontoInteresBeneficio())).multiply(lstBenefPrevSoc.getBdPorcentajeBeneficio()).divide(new BigDecimal(100)));
												log.info("--- MONTO NETO ---"+lstBenefPrevSoc.getBdMontoNeto());
											}
											if (lstExpPrevSoc.getIntParaTipoCaptacion().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_SEPELIO)) {
												if (titularFallecido==1) { //SI EL FALLECIDO ES EL TITULAR
													lstBenefPrevSoc.setBdMontoNeto((lstExpPrevSoc.getBdMontoBrutoBeneficio().subtract(lstExpPrevSoc.getBdMontoGastosADM())).multiply(lstBenefPrevSoc.getBdPorcentajeBeneficio()).divide(new BigDecimal(100)));
													log.info("--- MONTO NETO ---"+lstBenefPrevSoc.getBdMontoNeto());
												} else { //SI EL FALLECIDO ES UNO DE LOS BENEFICIARIOS
													lstBenefPrevSoc.setBdMontoNeto((lstExpPrevSoc.getBdMontoBrutoBeneficio().subtract(lstExpPrevSoc.getBdMontoGastosADM())));
													log.info("--- MONTO NETO ---"+lstBenefPrevSoc.getBdMontoNeto());
												}												
											}
											//TIPO DE VINCULO 
											if (titularBeneficiario==1) {
												lstBenefPrevSoc.setIntTipoViculo(Constante.PARAM_T_TIPOVINCULO_TITULAR);
											}else {
												vinculo = personaFacade.getVinculoPorId(lstBenefPrevSoc.getIntItemViculo());
												lstBenefPrevSoc.setIntTipoViculo(vinculo.getIntTipoVinculoCod());
											}											
											log.info("--- TIPO DE VINCULO ---"+lstBenefPrevSoc.getIntTipoViculo());
											//NOMBRE - APELLIDOS DE LOS BENEFICIARIOS
											Natural natural=null;
											if (vinculo!=null) {
												//BUSQUEDA DE DATOS EN PERSONA NATURAL PERSONA NARUTAL
												natural = personaFacade.getNaturalDetalladaPorIdPersona(lstBenefPrevSoc.getIntPersPersonaBeneficiario());
												if (natural!=null) {
													lstBenefPrevSoc.setStrNomApeBeneficiario(natural.getStrNombres()+" "+natural.getStrApellidoPaterno()+" "+natural.getStrApellidoMaterno());
													log.info("--- NOMBRES Y APELLIDOS BENEF ---"+lstBenefPrevSoc.getStrNomApeBeneficiario());
												}
											}									
										}
									}
								}
								lstRetorna.add(expedientePrevisionComp);								
								expedientePrevisionComp = new ExpedientePrevisionComp();
								break;
							}							
						}
					}
				}				
			}			
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 26.09.2013
	 * Recupera cuotas pagadas y a pagar por periodo del beneficio prestado.
	 * Aplica solo a Fdo. de Sepelio y Fdo. de Retiro.
	 * @param cuentaComp
	 * @return
	 * @throws BusinessException
	 */
	public List<PrevisionSocialComp> getListaPrevisionSocialComp (CuentaComp cuentaComp) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaPrevisionSocialComp-------------------------------------");
		//PAGOS DE CUOTAS DEL BENEFICIO 25-09-2013
		List<CuentaConcepto> listaCuentaConcepto = null;
		List<CuentaConceptoDetalle> listaCuentaConceptoDetalle = null;
		List<ConceptoPago> listaCptoPagoPorCtaCptoDetYPeriodo = null;
		List<PrevisionSocialComp> lstRetorna = new ArrayList<PrevisionSocialComp>();
		PrevisionSocialComp previsionSocial = null;
		try {
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			
			listaCuentaConcepto = conceptoFacade.getListaCuentaConceptoPorPkCuenta(cuentaComp.getCuenta().getId());
			if (listaCuentaConcepto!=null && !listaCuentaConcepto.isEmpty()) {
				for (CuentaConcepto lstCtaCpto : listaCuentaConcepto) {
					listaCuentaConceptoDetalle = conceptoFacade.getListaCuentaConceptoDetallePorCuentaConcepto(lstCtaCpto.getId());
					if (listaCuentaConceptoDetalle!=null && !listaCuentaConceptoDetalle.isEmpty()) {
						for (CuentaConceptoDetalle lstCtaCptoDet : listaCuentaConceptoDetalle) {
							previsionSocial = new PrevisionSocialComp();
							//previsionSocial.setListaConceptoPagoId(new ArrayList<ConceptoPagoId>());}
							previsionSocial.setListaConceptoPago(new ArrayList<ConceptoPago>());
							//FORMATO PERIODO
							Integer intPeriodoIni= mesAnioToPeriodo(Integer.parseInt(Constante.sdfMes.format(lstCtaCptoDet.getTsInicio())), 
																	Integer.parseInt(Constante.sdfAnio.format(lstCtaCptoDet.getTsInicio())));
							log.info("*PERIODO INICIO: "+intPeriodoIni);
							Integer intPeriodoFin= lstCtaCptoDet.getTsFin()!=null?mesAnioToPeriodo(Integer.parseInt(Constante.sdfMes.format(lstCtaCptoDet.getTsFin())), 
																								   Integer.parseInt(Constante.sdfAnio.format(lstCtaCptoDet.getTsFin())))
																								   :null;
							
							log.info("*PERIODO FIN: "+intPeriodoFin);
							//AÑO Y MES DEL PERIODO INICIO
							Integer intAnioPeriodoIni = Integer.parseInt(intPeriodoIni.toString().substring(0, 4));
							log.info("AÑO DEL PERIODO INICIAL: "+intAnioPeriodoIni);
							Integer intMesPeriodoIni = Integer.parseInt(intPeriodoIni.toString().substring(4, 6));
							log.info("MES DEL PERIODO INICIAL: "+intMesPeriodoIni);
							//AÑO Y MES DEL PERIODO FIN
							Integer intAnioPeriodoFin = intPeriodoFin!=null?Integer.parseInt(intPeriodoFin.toString().substring(0, 4)):null;
							log.info("AÑO DEL PERIODO FIN: "+intAnioPeriodoFin);
							Integer intMesPeriodoFin = intPeriodoFin!=null?Integer.parseInt(intPeriodoFin.toString().substring(4, 6)):null;
							log.info("MES DEL PERIODO FIN: "+intMesPeriodoFin);
							//FONDO DE SEPELIO
							if (lstCtaCptoDet.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_SEPELIO) || lstCtaCptoDet.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_RETIRO)) {
								Integer intPeriodoFinTmp = intPeriodoFin==null?Calendar.getInstance().get(Calendar.YEAR):intAnioPeriodoFin;
								for (Integer i = intAnioPeriodoIni; i <= intPeriodoFinTmp; i++) {
									listaCptoPagoPorCtaCptoDetYPeriodo = null;
									Integer intNroCtas=0;
									Integer intFecIni=0;
									Integer intFecFin=0;
									log.info("------------------------------- inicio fila -------------------------------");
									if (intPeriodoFin==null) {
										intFecIni= i.compareTo(intAnioPeriodoIni)==0?intPeriodoIni:Integer.valueOf(i+"01");
										intFecFin= Integer.valueOf(i+"12");	
										//CUOTAS-NUMERO
										intNroCtas = i.compareTo(intAnioPeriodoIni)==0?13-intMesPeriodoIni:12;
										previsionSocial.setIntNumeroCuotas(intNroCtas);
										log.info("--- CUOTAS-NUMERO ---"+intNroCtas);
									}										
									if (intPeriodoFin!=null) {
										intFecIni= i.compareTo(intAnioPeriodoIni)==0?intPeriodoIni:Integer.valueOf(i+"01");
										intFecFin= i.compareTo(intAnioPeriodoFin)==0?intPeriodoFin:Integer.valueOf(i+"12");
										//CUOTAS-NUMERO
										intNroCtas = intFecFin-intFecIni+1;
										previsionSocial.setIntNumeroCuotas(intNroCtas);
										log.info("--- CUOTAS-NUMERO ---"+intNroCtas);
									}
									listaCptoPagoPorCtaCptoDetYPeriodo = conceptoFacade.getListaConceptoPagoPorCtaCptoDetYPeriodo(lstCtaCptoDet.getId(), intFecIni, intFecFin);
									//PERIODO
									previsionSocial.setIntPeriodo(intFecIni);
									previsionSocial.setStrPeriodo(i.toString());
									log.info("--- PERIODO ---"+i);	
									BigDecimal bdMontoPago = BigDecimal.ZERO;
									if (listaCptoPagoPorCtaCptoDetYPeriodo!=null && !listaCptoPagoPorCtaCptoDetYPeriodo.isEmpty()) {											
										for (ConceptoPago lstCptoPgo : listaCptoPagoPorCtaCptoDetYPeriodo) {
											previsionSocial.getListaConceptoPago().add(lstCptoPgo);
											bdMontoPago = bdMontoPago.add(lstCptoPgo.getBdMontoPago());
										}										
									}
									previsionSocial.setBdMontoCuotas(lstCtaCptoDet.getBdMontoConcepto());
									log.info("--- CUOTAS-MONTO ---"+lstCtaCptoDet.getBdMontoConcepto());
									if (lstCtaCptoDet.getIntParaTipoConceptoCod().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_SEPELIO)) {
										previsionSocial.setBdProvisionado(lstCtaCptoDet.getBdMontoConcepto().multiply(new BigDecimal(intNroCtas)));
										log.info("--- PROVISIONADO ---"+lstCtaCptoDet.getBdMontoConcepto().multiply(new BigDecimal(intNroCtas)));
										previsionSocial.setBdPendiente(lstCtaCptoDet.getBdMontoConcepto().multiply(new BigDecimal(intNroCtas)).subtract(bdMontoPago));
										log.info("--- PENDIENTE ---"+(lstCtaCptoDet.getBdMontoConcepto().multiply(new BigDecimal(intNroCtas)).subtract(bdMontoPago)));
									}
									previsionSocial.setBdCancelado(bdMontoPago);
									log.info("--- CANCELADOS ---"+bdMontoPago);
									previsionSocial.setIntParaTipoConceptoCod(lstCtaCptoDet.getIntParaTipoConceptoCod());
									log.info("--- REGISTRO DE FONDO DE ---"+lstCtaCptoDet.getIntParaTipoConceptoCod());
									log.info("-------------------------------------------------------------------------------");
									lstRetorna.add(previsionSocial);
									previsionSocial = new PrevisionSocialComp();
									previsionSocial.setListaConceptoPago(new ArrayList<ConceptoPago>());
								}
							}							
						}
					}
				}
			}			
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 27.09.2013 
	 * Recupera lista de movimientos por Concepto Pago.
	 * Aplica para Fdo. de sepelio y Fdo. de Retiro, si su concepto general es Aporte (PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO)
	 * @param listaConceptoPago
	 * @return
	 * @throws BusinessException
	 */
	public List<MovimientoEstCtaComp> getListaMovimiento (List<ConceptoPago> listaConceptoPago) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaMovimiento-------------------------------------");
		List<MovimientoEstCtaComp> lstRetorna = new ArrayList<MovimientoEstCtaComp>();
		List<ConceptoDetallePago> listaConceptoDetallePago = null;
		Movimiento movCtaCte = null;
		MovimientoEstCtaComp movEstCta = null;
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			List<Tabla> listaDescTipoCargoAbono = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CARGOABONO));
			List<Tabla> listaDescTipoMovimiento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_MOVIMIENTO));
			if (listaConceptoPago!=null && !listaConceptoPago.isEmpty()) {
				for (ConceptoPago lstCptoPgo : listaConceptoPago) {
					listaConceptoDetallePago = conceptoFacade.getCptoDetPagoPorCptoPagoPK(lstCptoPgo.getId());
					if (listaConceptoDetallePago!=null && !listaConceptoDetallePago.isEmpty()) {
						for (ConceptoDetallePago lstCptoDetPgo : listaConceptoDetallePago) {
							/* 
							 * 02.10.2013 - JCHAVEZ
							 * LISTA LOS MOVIMIENTOS QUE SE ENCUENTRAN EN CONCEPTO DETALLE PAGO, ESTOS CUMPLEN CON
							 * ITEMCUENTACONCEPTO = PARAM_T_TIPOCUENTA_FONDO_SEPELIO || 
							 * ITEMCUENTACONCEPTO = PARAM_T_TIPOCUENTA_FONDO_RETIRO && PARATIPOCONCEPTOGRAL = PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO
							 */
							movEstCta = new MovimientoEstCtaComp(); 
							String strDescCargoAbono = "";
							String strDescTipoMov = "";							
							movCtaCte = conceptoFacade.getMovimientoPorPK(lstCptoDetPgo.getId().getIntItemMovCtaCte());
							//MOVIMIENTO
							movEstCta.setMovimiento(movCtaCte);
							//FECHA
							movEstCta.setStrFechaMovimiento(Constante.sdf.format(movCtaCte.getTsFechaMovimiento()));
							log.info("--- FECHA ---"+movEstCta.getStrFechaMovimiento());
							//PERIODO (DE CONCEPTOPAGO)
							movEstCta.setIntPeriodoFechaMovimiento(Integer.parseInt(movEstCta.getStrFechaMovimiento().substring(6, 10)+movEstCta.getStrFechaMovimiento().substring(3, 5)));
							movEstCta.setIntPeriodoFechaCptoPago(lstCptoPgo.getIntPeriodo());
							log.info("--- PERIODO ---"+movEstCta.getIntPeriodoFechaMovimiento());
							//TIPO DE MOVIMIENTO
							for (Tabla descTipoMov: listaDescTipoMovimiento) {
								if(descTipoMov.getIntIdDetalle().compareTo(movCtaCte.getIntParaTipoMovimiento())==0){
									strDescTipoMov = descTipoMov.getStrDescripcion().substring(0,4).toUpperCase();
									break;
								}
							}
							movEstCta.setStrDescTipoMovimiento(strDescTipoMov);					
							log.info("--- TIPO DE MOVIMIENTO ---"+movEstCta.getStrDescTipoMovimiento());
							//NUMERO DE DOCUMENTO
							log.info("--- NUMERO DE DOCUMENTO ---"+movEstCta.getMovimiento().getStrNumeroDocumento());
							//CARGO / ABONO
							for (Tabla descCargoAbono: listaDescTipoCargoAbono) {
								if(descCargoAbono.getIntIdDetalle().compareTo(movCtaCte.getIntParaTipoCargoAbono())==0){
									strDescCargoAbono = descCargoAbono.getStrDescripcion().substring(0,1);
									break;
								}
							}
							movEstCta.setStrDescTipoCargoAbono(strDescCargoAbono);
							log.info("--- CARGO / ABONO ---"+movEstCta.getStrDescTipoCargoAbono());
							//MONTO
							log.info("--- MONTO ---"+movEstCta.getMovimiento().getBdMontoMovimiento());
							//TIPO CONCEPTO
							if (movCtaCte.getIntItemCuentaConcepto().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_SEPELIO)) movEstCta.setStrDescTipoConceptoGral("SEPELIO");
							if (movCtaCte.getIntItemCuentaConcepto().equals(Constante.PARAM_T_TIPOCUENTA_FONDO_RETIRO)) movEstCta.setStrDescTipoConceptoGral("APORTE");
							//INTERES - CASO FDO.RETIRO
							log.info("--- INTERES - CASO FDO.RETIRO ---"+movEstCta.getMovimiento().getBdMontoMovimiento());
							lstRetorna.add(movEstCta);
						}
					}
				}
			}
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 02.10.2013 
	 * Lista los movimientos que cumplen:
	 * itemCuentaConcepto = PARAM_T_TIPOCUENTA_FONDO_RETIRO && paraTipoConceptoGeneral = PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES.
	 * Estos no se encuentran registrados en CMO_CUENTACONCEPTO, solo se registran en CMO_MOVIMIENTOCTACTE (proceso batch).
	 * @param cuentaComp
	 * @return
	 * @throws BusinessException
	 */
	public List<MovimientoEstCtaComp> getListaMovimientoFdoRetiroInteres (CuentaComp cuentaComp) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaMovimientoFdoRetiroInteres-------------------------------------");
		List<Movimiento> listaMovimiento = null;
		MovimientoEstCtaComp movEstCta = null;
		List<MovimientoEstCtaComp> lstRetorna = new ArrayList<MovimientoEstCtaComp>();
		try {
			ConceptoFacadeRemote conceptoFacade = (ConceptoFacadeRemote)EJBFactory.getRemote(ConceptoFacadeRemote.class);
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
			List<Tabla> listaDescTipoMovimiento = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_TIPO_MOVIMIENTO));
			List<Tabla> listaDescTipoCargoAbono = tablaFacade.getListaTablaPorIdMaestro(new Integer(Constante.PARAM_T_CARGOABONO));
			
			listaMovimiento = conceptoFacade.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(
																			cuentaComp.getCuenta().getId().getIntPersEmpresaPk(),
																			cuentaComp.getCuenta().getId().getIntCuenta(),
																		    Constante.PARAM_T_TIPOCUENTA_FONDO_RETIRO,
																		    Constante.PARAM_T_TIPOCONCEPTOGENERAL_FDORETIRO_INTERES);
			if (listaMovimiento!=null && !listaMovimiento.isEmpty()) {
				for (Movimiento lstMovCtaCte : listaMovimiento) {
					movEstCta = new MovimientoEstCtaComp(); 
					String strDescCargoAbono = "";
					String strDescTipoMov = "";							
					//MOVIMIENTO
					movEstCta.setMovimiento(lstMovCtaCte);
					//FECHA
					movEstCta.setStrFechaMovimiento(Constante.sdf.format(lstMovCtaCte.getTsFechaMovimiento()));
					log.info("--- FECHA ---"+movEstCta.getStrFechaMovimiento());
					//PERIODO
					movEstCta.setIntPeriodoFechaMovimiento(Integer.parseInt(movEstCta.getStrFechaMovimiento().substring(6, 10)+movEstCta.getStrFechaMovimiento().substring(3, 5)));
					log.info("--- PERIODO ---"+movEstCta.getIntPeriodoFechaMovimiento());
					//TIPO DE MOVIMIENTO
					for (Tabla descTipoMov: listaDescTipoMovimiento) {
						if(descTipoMov.getIntIdDetalle().compareTo(lstMovCtaCte.getIntParaTipoMovimiento())==0) {
							strDescTipoMov = descTipoMov.getStrDescripcion().substring(0,4).toUpperCase();
							break;
						}
					}
					movEstCta.setStrDescTipoMovimiento(strDescTipoMov);					
					log.info("--- TIPO DE MOVIMIENTO ---"+movEstCta.getStrDescTipoMovimiento());
					//NUMERO DE DOCUMENTO
					log.info("--- NUMERO DE DOCUMENTO ---"+movEstCta.getMovimiento().getStrNumeroDocumento());
					//CARGO / ABONO
					for (Tabla descCargoAbono: listaDescTipoCargoAbono) {
						if(descCargoAbono.getIntIdDetalle().compareTo(lstMovCtaCte.getIntParaTipoCargoAbono())==0) {
							strDescCargoAbono = descCargoAbono.getStrDescripcion().substring(0,1);
							break;
						}
					}
					movEstCta.setStrDescTipoCargoAbono(strDescCargoAbono);
					log.info("--- CARGO / ABONO ---"+movEstCta.getStrDescTipoCargoAbono());
					//MONTO
					log.info("--- MONTO ---"+movEstCta.getMovimiento().getBdMontoMovimiento());
					//TIPO CONCEPTO
					movEstCta.setStrDescTipoConceptoGral("INTERES");
					//INTERES - CASO FDO.RETIRO
					log.info("--- INTERES - CASO FDO.RETIRO ---"+movEstCta.getMovimiento().getBdMontoMovimiento());
					lstRetorna.add(movEstCta);
				}
			}
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	 * Recupera Gestion Cobranza Soc por Cuenta Id desde un peiorio en adelante
	 * @param cId
	 * @return
	 * @throws BusinessException
	 */
	public List<GestionCobranzaSoc> getListaGestionCobranzaSoc (CuentaId cId, String strFechaGestion) throws BusinessException{
		log.info("-------------------------------------Debugging EstadoCtaService.getListaGestionCobranzaSoc-------------------------------------");
		List<GestionCobranzaSoc> lstRetorna = new ArrayList<GestionCobranzaSoc>();
		List<GestionCobranzaSoc> listaGestionCobranzaSoc = null;
		GestionCobranza gestionCobranza = null;
		GestionCobranza gestionCobranzaTmp = new GestionCobranza();
		gestionCobranzaTmp.setId(new GestionCobranzaId());
		try {
			GestionCobranzaFacadeRemote gestionCobranzaFacade = (GestionCobranzaFacadeRemote)EJBFactory.getRemote(GestionCobranzaFacadeRemote.class);
			listaGestionCobranzaSoc = gestionCobranzaFacade.getListaPorCuentaPkYPeriodo(cId, strFechaGestion);
			if (listaGestionCobranzaSoc!=null && !listaGestionCobranzaSoc.isEmpty()) {
				for (GestionCobranzaSoc lstGestionCobSoc : listaGestionCobranzaSoc) {
					gestionCobranzaTmp.getId().setIntPersEmpresaGestionPK(lstGestionCobSoc.getId().getIntPersEmpresaGestion());
					gestionCobranzaTmp.getId().setIntItemGestionCobranza(lstGestionCobSoc.getId().getIntItemGestionCobranza());
					gestionCobranza = gestionCobranzaFacade.getGestionCobranza(gestionCobranzaTmp);
					if (gestionCobranza!=null) {
						lstGestionCobSoc.setGestionCobranza(gestionCobranza);
					}
					lstRetorna.add(lstGestionCobSoc);
				}
			}
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lstRetorna;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 13.09.2013 
	 * Concatena mes y año.
	 * @param mes
	 * @param anio
	 * @return
	 */
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
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 13.09.2013
	 * Formato del Periodo: Nombre_Mes - Año
	 * @param intPeriodo
	 * @return
	 * @throws BusinessException
	 */
	public String fmtPeriodo(Integer intPeriodo) throws BusinessException{
		String strFmtPeriodo="";
		String strMesPeriodo="";
		List<Tabla> listaDescripcionMes = null;
		try {
			tablaFacade = (TablaFacadeRemote)EJBFactory.getRemote(TablaFacadeRemote.class);
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
}