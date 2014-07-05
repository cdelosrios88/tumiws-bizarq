package pe.com.tumi.movimiento.concepto.facade;

import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;
import pe.com.tumi.movimiento.concepto.domain.ConceptoDetallePago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPago;
import pe.com.tumi.movimiento.concepto.domain.ConceptoPagoId;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;
import pe.com.tumi.movimiento.concepto.domain.CronogramaId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalleId;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoId;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.movimiento.concepto.domain.InteresCancelado;
import pe.com.tumi.movimiento.concepto.domain.InteresProvisionado;
import pe.com.tumi.movimiento.concepto.domain.Movimiento;

@Remote
public interface ConceptoFacadeRemote {
	public CuentaConcepto grabarCuentaConcepto(CuentaConcepto o, Integer intIdPersona)throws BusinessException;
	public CuentaConcepto modificarCuentaConcepto(CuentaConcepto o, Integer intIdPersona)throws BusinessException;
	public List<CuentaConcepto> getListaCuentaConceptoPorPkCuenta(CuentaId pId) throws BusinessException;
	public CuentaDetalleBeneficio getCuentaConceptoDetallePorPk(CuentaDetalleBeneficio o)throws BusinessException;
	public CuentaDetalleBeneficio modificarCuentaDetalleBeneficio(CuentaDetalleBeneficio o) throws BusinessException;
	public CuentaConceptoDetalle getCuentaConceptoDetallePorPkYTipoConcepto(CuentaConceptoDetalle o)throws BusinessException;
	public List<CuentaConcepto> getListaCuentaConceptoPorEmpresaYCuenta(Integer intEmpresa,Integer intCuenta)throws BusinessException;
	public List<Expediente> getListaExpedienteConSaldoPorEmpresaYcuenta(Integer intEmpresa,Integer intCuenta)throws BusinessException;
	public List<Cronograma> getListaCronogramaDeVencidoPorPkExpedienteYPeriodoYPago(ExpedienteId idExpediente,Integer intPeriodoPlanilla,Integer intFormaPago)throws BusinessException;
	public Movimiento grabarMovimiento(Movimiento movimiento)throws BusinessException;
	public Expediente grabarExpediente(Expediente expediente)throws BusinessException;
	public Cronograma grabarCronograma(Cronograma cronograma)throws BusinessException;
	public CuentaConcepto getCuentaConceptoYUltimoDetallaPorId(CuentaConceptoId cuentaConceptoId)throws BusinessException;
	
 	public BloqueoCuenta grabarBloqueoCuenta(BloqueoCuenta o) throws BusinessException;
 	public List<BloqueoCuenta> getListaPorNroCuentaYMotivo(Integer intPersEmpresaPk,Integer intCuentaPk,Integer intParaCodigoMotivoCod) throws BusinessException;
 	public List<CuentaConceptoDetalle> getListaCuentaConceptoDetallePorCuentaConcepto(CuentaConceptoId cuentaConceptoId)throws BusinessException;
 	public List<CuentaDetalleBeneficio> getListaCuentaDetalleBeneficioPorPkConcepto(CuentaConceptoId pId) throws BusinessException; 
 	
 	public List<Movimiento> getListaMovimientoPorCuentaEmpresaConcepto(Integer intPersEmpresa,Integer intCuenta,Integer intItemCuentaConcepto) throws BusinessException;
	public List<Expediente> getListaExpedientePorEmpresaYCta(Integer intEmpresa,Integer intCuenta)throws BusinessException;
	public Movimiento getListPeriodoMaxCuentaEmpresa(Integer intPersEmpresa,Integer intCuenta) throws BusinessException;
	public Expediente getExpedientePorPK(ExpedienteId expedienteId)throws BusinessException;
	public List<Movimiento> getListaMovimientoPorCtaPersonaConceptoGeneral(Integer intPersEmpresa,Integer intCuenta,Integer intPersPersonaIntegrante, Integer intItemCuentaConceptoGen) throws BusinessException;
	public Expediente modificarExpediente(Expediente expediente)throws BusinessException;
	public CuentaConcepto modificarCuentaConcepto(CuentaConcepto ctaConcepto)throws BusinessException;
	public CuentaConceptoDetalle modificarCuentaConceptoDetalle(CuentaConceptoDetalle ctaConceptoDetalle)throws BusinessException;
	public void modificarDetallePorConcepto(CuentaConceptoDetalle ctaConceptoDetalle)throws BusinessException;
	public List<BloqueoCuenta> getListaBloqueoCuentaPorNroCuenta(Integer intPersEmpresaPk,Integer intCuentaPk) throws BusinessException;
 	public List<Cronograma> getListaCronogramaPorPkExpediente(ExpedienteId idExpediente)throws BusinessException;
 	public Cronograma modificarCronograma(Cronograma cronograma)throws BusinessException;
 	
 	public List<Movimiento> getListXCtaExpediente(Integer intPersEmpresa,Integer intCuenta,Integer intItemExpediente, Integer intItemExpedienteDetalle) throws BusinessException;
 	public Movimiento getMaximoMovimiento() throws BusinessException;	
 	public Cronograma getCronogramaPorPK(CronogramaId cronId) throws BusinessException;
 	public List<CuentaConceptoDetalle> getCuentaConceptoDetallePorPKCuentaYTipoConcepto(CuentaConceptoDetalle o)throws BusinessException;
 	public EstadoExpediente grabarEstado(EstadoExpediente estadoExpediente)throws BusinessException;
 	public Expediente grabarExpedienteCompleto(Expediente expediente)throws BusinessException;
 	public List<EstadoExpediente> getListaPorPkExpedienteCredito(ExpedienteId pId) throws BusinessException;
 	public void modificarPorBeneficiario(CuentaDetalleBeneficio cuentaDetalleBeneficio)throws BusinessException;	
	
 	public List<Movimiento> getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(Integer intPersEmpresa, Integer intCuenta, Integer intItemCuentaConcepto, Integer intTipoConceptoGeneral) throws BusinessException;
 	//public List<Movimiento> getListaMaximoMovimientoPorCuentaConcepto(Integer intPersEmpresa, Integer intCuenta, Integer intItemCuentaConcepto) throws BusinessException;
 	public List<CuentaConceptoDetalle> getMaxCuentaConceptoDetPorPKCuentaConcepto(CuentaConceptoId ctaCtoId) throws BusinessException;
 	
 	public List<Expediente> getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(Integer intEmpresa,Integer intCuenta)throws BusinessException;
 	
 	public List<ConceptoPago> getListaConceptoPagoPorCuentaConceptoDet(CuentaConceptoDetalleId ctaCtoDetId)throws BusinessException;
 	public InteresProvisionado grabarInteresProvisionado(InteresProvisionado interesProvisionado)throws BusinessException;
 	public List<InteresCancelado> getListaInteresCanceladoPorExpedienteCredito(ExpedienteId pId) throws BusinessException;
 	public ConceptoPago grabarConceptoPago(ConceptoPago conceptoPago) throws BusinessException;
 	public ConceptoDetallePago grabarConceptoDetallePago(ConceptoDetallePago conceptoDetallePago) throws BusinessException;
 	public InteresCancelado grabarInteresCancelado(InteresCancelado interesCancelado) throws BusinessException;
 	public ConceptoPago modificarConceptoPago(ConceptoPago conceptoPago) throws BusinessException;
 	public CuentaConcepto getCuentaConceptoPorPK(CuentaConceptoId  o)throws BusinessException;
 	public CuentaDetalleBeneficio grabarCuentaDetalleBeneficio(CuentaDetalleBeneficio cuentaDetalleBeneficio)throws BusinessException;
 	/** CREADO 20-08-2013 **/
 	public List<Movimiento> getListXCuentaYEmpresa(Integer intPersEmpresa, Integer intCuenta) throws BusinessException;
 	
 	public List<Movimiento> getMaxMovXCtaEmpresaTipoMov(Integer intPersEmpresa,Integer intCuenta, Integer intParaTipoMovimiento) throws BusinessException;
 	public List<Movimiento> getListaMaximoMovimientoPorCuentaConcepto(Integer intPersEmpresa, Integer intCuenta, Integer intItemCuentaConcepto,Integer intTipoConceptoGeneral) throws BusinessException;
 	public InteresCancelado getMaxInteresCanceladoPorExpediente(ExpedienteId pId) throws BusinessException;
 	//AUTOR Y FECHA CREACION: JCHAVEZ / 25-09-2013
 	public List<ConceptoPago> getListaConceptoPagoPorCtaCptoDetYPeriodo(CuentaConceptoDetalleId pId, Integer intPeriodoIni, Integer intPeriodoFin)throws BusinessException;
 	
 	public Movimiento getMaxXExpYCtoGral(Integer intPersEmpresa,Integer intCuenta,Integer intItemExpediente, Integer intItemExpedienteDetalle, Integer intTipoConceptoGral) throws BusinessException;
 	public Movimiento modificarMovimiento(Movimiento movimiento) throws BusinessException;
 	//AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013
 	public List<ConceptoDetallePago> getCptoDetPagoPorCptoPagoPK(ConceptoPagoId pId) throws BusinessException;
 	//AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013
 	public Movimiento getMovimientoPorPK(Integer intItemMovimiento) throws BusinessException;
 	public List<Expediente> getListaExpedientePorCtaYExp (ExpedienteId pId) throws BusinessException;
 	public EstadoExpediente getMaxEstadoExpPorPkExpediente (ExpedienteId pId) throws BusinessException;
 	//AUTOR Y FECHA CREACION: FYALICO / 18-11-2013
 	public EstadoExpediente modificarEstadoExpediente(EstadoExpediente estadoExpediente)throws BusinessException;
 	//JCHAVEZ 10.01.2014
 	public List<ConceptoPago> getUltimoCptoPagoPorCuentaConceptoDet(CuentaConceptoDetalleId ctaCtoDetId)throws BusinessException;
 	
	public List<ConceptoPago> getListaConceptoPagoToCobranza(CuentaConceptoDetalleId ctaCtoDetId)throws BusinessException;
	

 	//begin flyalico
 	public List<CuentaConceptoDetalle> getCuentaConceptoDetofCobranza(CuentaConceptoId ctaCtoId) throws BusinessException;


 	public List<CuentaConcepto> getListaCuentaConceptoEmpresaCuentaOfCob(Integer intEmpresa,
 																		Integer intCuenta)throws BusinessException;
 	//finish flyalico
 	//jchavez 19.05.2014
 	public List<Movimiento> getListaMovVtaCtePorPagar(Integer intPersEmpresa,Integer intCuenta, Integer intItemExpediente, Integer intParaTipoConcepto, Integer intParaDocumentoGeneral) throws BusinessException;
 	//fin jchavez 19.05.2014
 	public List<Movimiento> getMaxMovCtaCteXFechaMaxima(Integer intPersEmpresa)throws BusinessException;
 	//rVillarreal 26.05.2014
 	public List<BloqueoCuenta> getListaFondoSepelioMonto(Integer intEmpresa,Integer intCuenta)throws BusinessException;
 	//jchavez 05.06.2014
 	public List<Movimiento> getListaMovCtaCtePorPagarLiq(Integer intPersEmpresa,Integer intCuenta, Integer intItemExpediente, Integer intParaDocumentoGeneral) throws BusinessException;
 }
