package pe.com.tumi.movimiento.concepto.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.movimiento.concepto.bo.BloqueoCuentaBO;
import pe.com.tumi.movimiento.concepto.bo.ConceptoDetallePagoBO;
import pe.com.tumi.movimiento.concepto.bo.ConceptoPagoBO;
import pe.com.tumi.movimiento.concepto.bo.CronogramaBO;
import pe.com.tumi.movimiento.concepto.bo.CtaConceptoDetalleBloqueoBO;
import pe.com.tumi.movimiento.concepto.bo.CuentaConceptoBO;
import pe.com.tumi.movimiento.concepto.bo.CuentaConceptoDetalleBO;
import pe.com.tumi.movimiento.concepto.bo.CuentaDetalleBeneficioBO;
import pe.com.tumi.movimiento.concepto.bo.EstadoExpedienteBO;
import pe.com.tumi.movimiento.concepto.bo.ExpedienteBO;
import pe.com.tumi.movimiento.concepto.bo.InteresCanceladoBO;
import pe.com.tumi.movimiento.concepto.bo.MovimientoBO;
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
import pe.com.tumi.movimiento.concepto.service.ConceptoService;
import pe.com.tumi.parametro.general.domain.Ubigeo;

@Stateless
public class ConceptoFacade extends TumiFacade implements ConceptoFacadeRemote, ConceptoFacadeLocal {
    
	private CuentaConceptoBO boCuentaConcepto = (CuentaConceptoBO)TumiFactory.get(CuentaConceptoBO.class);
	private CuentaConceptoDetalleBO boCuentaConceptoDetalle = (CuentaConceptoDetalleBO)TumiFactory.get(CuentaConceptoDetalleBO.class);
	private CuentaDetalleBeneficioBO boCuentaDetalleBeneficio = (CuentaDetalleBeneficioBO)TumiFactory.get(CuentaDetalleBeneficioBO.class);
	private ConceptoService conceptoService = (ConceptoService)TumiFactory.get(ConceptoService.class);
	private CtaConceptoDetalleBloqueoBO boCtaConceptoDetalleBloqueo = (CtaConceptoDetalleBloqueoBO)TumiFactory.get(CtaConceptoDetalleBloqueoBO.class);
	private ExpedienteBO boExpediente = (ExpedienteBO)TumiFactory.get(ExpedienteBO.class);
	private CronogramaBO boCronograma = (CronogramaBO)TumiFactory.get(CronogramaBO.class);
	private MovimientoBO boMovimiento = (MovimientoBO)TumiFactory.get(MovimientoBO.class);
	private EstadoExpedienteBO boEstado = (EstadoExpedienteBO)TumiFactory.get(EstadoExpedienteBO.class);
	private CuentaConceptoDetalleBO boConceptoDetalle = (CuentaConceptoDetalleBO)TumiFactory.get(CuentaConceptoDetalleBO.class);
	private EstadoExpedienteBO boEstadoExpediente = (EstadoExpedienteBO)TumiFactory.get(EstadoExpedienteBO.class);
	private BloqueoCuentaBO boBloqueoCuenta = (BloqueoCuentaBO)TumiFactory.get(BloqueoCuentaBO.class);
	private ConceptoPagoBO boConceptoPago = (ConceptoPagoBO)TumiFactory.get(ConceptoPagoBO.class);	
	private InteresCanceladoBO boInteresCancelado = (InteresCanceladoBO)TumiFactory.get(InteresCanceladoBO.class);
	private ConceptoDetallePagoBO boConceptoDetallePago = (ConceptoDetallePagoBO)TumiFactory.get(ConceptoDetallePagoBO.class);
//	private InteresProvisionadoBO boInteresProvisionado = (InteresProvisionadoBO)TumiFactory.get(InteresProvisionadoBO.class);
	
	public CuentaConcepto grabarCuentaConcepto(CuentaConcepto o, Integer intIdPersona)throws BusinessException{
		CuentaConcepto dto = null;
		try{
			dto = conceptoService.grabarCuentaConcepto(o, intIdPersona);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	
	
	public CuentaConcepto modificarCuentaConcepto(CuentaConcepto o, Integer intIdPersona)throws BusinessException{
		CuentaConcepto dto = null;
		try{
			dto = conceptoService.modificarCuentaConcepto(o, intIdPersona);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<CuentaConcepto> getListaCuentaConceptoPorPkCuenta(CuentaId pId) throws BusinessException{
    	List<CuentaConcepto> lista = null;
    	CuentaConcepto cuentaConcepto = null;
   		try{
   			lista = boCuentaConcepto.getListaCuentaConceptoPorPKCuenta(pId);
   			if(lista!=null){
   				for(CuentaConcepto concepto : lista){
   					cuentaConcepto = conceptoService.getCuentaConceptoPorPk(concepto.getId());
   	   				concepto.setListaCuentaConceptoDetalle(cuentaConcepto.getListaCuentaConceptoDetalle());
   	   				concepto.setListaCuentaDetalleBeneficio(cuentaConcepto.getListaCuentaDetalleBeneficio());
   	   			}
   			}
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public CuentaDetalleBeneficio getCuentaConceptoDetallePorPk(CuentaDetalleBeneficio o)throws BusinessException{
   		CuentaDetalleBeneficio dto = null;
		try{
			dto = boCuentaDetalleBeneficio.getCuentaDetalleBeneficioPorPK(o.getId());
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
   	
   	public CuentaDetalleBeneficio modificarCuentaDetalleBeneficio(CuentaDetalleBeneficio o) throws BusinessException{
		CuentaDetalleBeneficio dto = null;
		try{
			dto = conceptoService.modificarCuentaDetalleBeneficio(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public CuentaConceptoDetalle getCuentaConceptoDetallePorPkYTipoConcepto(CuentaConceptoDetalle o)throws BusinessException{
   		CuentaConceptoDetalle dto = null;
		try{
			dto = boCuentaConceptoDetalle.getCuentaConceptoDetallePorPKYTipoConcepto(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
   	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<CuentaConcepto> getListaCuentaConceptoPorEmpresaYCuenta(Integer intEmpresa,Integer intCuenta)throws BusinessException{
   		List<CuentaConcepto> lista = null;
		try{
			lista = boCtaConceptoDetalleBloqueo.getListaCuentaConceptoPorEmpresaYCuenta(intEmpresa, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
   	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Expediente> getListaExpedienteConSaldoPorEmpresaYcuenta(Integer intEmpresa,Integer intCuenta)throws BusinessException{
   		List<Expediente> lista = null;
   		List<EstadoExpediente> listaEstados = null;
   		List<Expediente> listaExpedientes = null;
		try{
			lista = boExpediente.getListaExpedienteConSaldoPorEmpresaYcuenta(intEmpresa, intCuenta);
			if(lista != null && !lista.isEmpty()){
				listaExpedientes = new ArrayList<Expediente>();
				for (Expediente expediente : lista) {
						listaEstados = boEstadoExpediente.getListaPorPkExpedienteCredito(expediente.getId());
						if(listaEstados != null && !listaEstados.isEmpty()){
							expediente.setListaEstadosExpediente(listaEstados);	
						}
						listaExpedientes.add(expediente);
				}
				
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaExpedientes;
	}
   	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Cronograma> getListaCronogramaDeVencidoPorPkExpedienteYPeriodoYPago(ExpedienteId idExpediente,Integer intPeriodoPlanilla,Integer intFormaPago)throws BusinessException{
   		List<Cronograma> lista = null;
		try{
			lista = boCronograma.getListaCronogramaDeVencidoPorPkExpedienteYPeriodoYPago(
									idExpediente, 
									intPeriodoPlanilla, 
									intFormaPago);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
   	
	public Movimiento grabarMovimiento(Movimiento movimiento)throws BusinessException{
		Movimiento dto = null;
		try{
			dto = boMovimiento.grabar(movimiento);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Cronograma grabarCronograma(Cronograma cronograma)throws BusinessException{
		Cronograma dto = null;
		try{
			dto = boCronograma.grabarCronograma(cronograma);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Expediente grabarExpediente(Expediente expediente)throws BusinessException{
		Expediente dto = null;
		try{
			dto = boExpediente.grabarExpediente(expediente);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public CuentaConcepto getCuentaConceptoYUltimoDetallaPorId(CuentaConceptoId cuentaConceptoId)throws BusinessException{
   		CuentaConcepto dto = null;
		try{
			dto = conceptoService.getCuentaConceptoYUltimoDetallaPorId(cuentaConceptoId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
   	
   	
   	public BloqueoCuenta grabarBloqueoCuenta(BloqueoCuenta o) throws BusinessException{
   		
   		BloqueoCuenta dto = null;
   		
		try{
			dto = boBloqueoCuenta.grabarBloqueoCuenta(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		
		return dto;
	}
	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public List<BloqueoCuenta> getListaPorNroCuentaYMotivo(Integer intPersEmpresaPk,Integer intCuentaPk,Integer intParaCodigoMotivoCod) throws BusinessException{
     	List<BloqueoCuenta> lista = null;
    	try{
   			lista = boBloqueoCuenta.getListaPorNroCuentaYMotivo(intPersEmpresaPk, intCuentaPk, intParaCodigoMotivoCod);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<CuentaConceptoDetalle> getListaCuentaConceptoDetallePorCuentaConcepto(CuentaConceptoId cuentaConceptoId)throws BusinessException{
   		List<CuentaConceptoDetalle> lista = null;
		try{
			lista = boCuentaConceptoDetalle.getListaCuentaConceptoPorPKCuenta(cuentaConceptoId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
   	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<CuentaDetalleBeneficio> getListaCuentaDetalleBeneficioPorPkConcepto(CuentaConceptoId pId) throws BusinessException{
    	List<CuentaDetalleBeneficio> lista = null;
    	//CuentaDetalleBeneficio cuentaConcepto = null;
   		try{
   			lista = boCuentaDetalleBeneficio.getListaCuentaDetalleBeneficioPorPKCuentaConcepto(pId);

   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public List<Movimiento> getListaMovimientoPorCuentaEmpresaConcepto(Integer intPersEmpresa,Integer intCuenta,Integer intItemCuentaConcepto) throws BusinessException{
     	List<Movimiento> lista = null;
    	try{
   			lista = boMovimiento.getListaMovimientoPorCuentaEmpresaConcepto(intPersEmpresa,intCuenta,intItemCuentaConcepto);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Expediente> getListaExpedientePorEmpresaYCta(Integer intEmpresa,Integer intCuenta)throws BusinessException{
   		List<Expediente> lista = null;
		try{
			lista = boExpediente.getListaExpedientePorEmpresaYCta(intEmpresa, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	  public List<Movimiento> getListaMovimientoPorCtaPersonaConceptoGeneral(Integer intPersEmpresa,Integer intCuenta,Integer intPersPersonaIntegrante, Integer intItemCuentaConceptoGen) throws BusinessException{
	   	List<Movimiento> lista = null;
    	try{
   			lista = boMovimiento.getListaMovimientoPorCtaPersonaConceptoGeneral(intPersEmpresa, intCuenta, intPersPersonaIntegrante, intItemCuentaConceptoGen);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
	
	public Expediente modificarExpediente(Expediente expediente)throws BusinessException{
		Expediente dto = null;
		try{
			dto = boExpediente.modificarExpediente(expediente);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	
	/**
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public Movimiento getListPeriodoMaxCuentaEmpresa(Integer intPersEmpresa,Integer intCuenta) throws BusinessException{
     	List<Movimiento> lista = null;
     	Movimiento dto = null;
    	try{
   			lista = boMovimiento.getListPeriodoMaxCuentaEmpresa(intPersEmpresa,intCuenta);
   			if(lista != null && !lista.isEmpty()){
   				dto = lista.get(0);
   			}else{
   				dto = null;
   			}
   			
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}

	
	public Expediente getExpedientePorPK(ExpedienteId expedienteId)throws BusinessException{
		Expediente dto = null;
		try{
			dto = boExpediente.getExpedientePorPK(expedienteId);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
   	
	public CuentaConcepto modificarCuentaConcepto(CuentaConcepto ctaConcepto)throws BusinessException{
		CuentaConcepto dto = null;
		try{
			dto = boCuentaConcepto.modificarCuentaConcepto(ctaConcepto);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CuentaConceptoDetalle modificarCuentaConceptoDetalle(CuentaConceptoDetalle ctaConceptoDetalle)throws BusinessException{
		CuentaConceptoDetalle dto = null;
		try{
			dto = boConceptoDetalle.modificarCuentaConceptoDetalle(ctaConceptoDetalle);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public void modificarDetallePorConcepto(CuentaConceptoDetalle ctaConceptoDetalle)throws BusinessException{
		try{
			boConceptoDetalle.modificarDetallePorConcepto(ctaConceptoDetalle);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public List<BloqueoCuenta> getListaBloqueoCuentaPorNroCuenta(Integer intPersEmpresaPk,Integer intCuentaPk) throws BusinessException{
     	List<BloqueoCuenta> lista = null;
    	try{
   			lista = boBloqueoCuenta.getListaPorNroCuenta(intPersEmpresaPk, intCuentaPk);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Cronograma> getListaCronogramaPorPkExpediente(ExpedienteId idExpediente)throws BusinessException{
   		List<Cronograma> lista = null;
		try{
			lista = boCronograma.getListaCronogramaPorPkExpediente(idExpediente);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
   	
	public Cronograma modificarCronograma(Cronograma cronograma)throws BusinessException{
		Cronograma dto = null;
		try{
			dto = boCronograma.modificarCronograma(cronograma);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public List<Movimiento> getListXCtaExpediente(Integer intPersEmpresa,Integer intCuenta,Integer intItemExpediente, Integer intItemExpedienteDetalle) throws BusinessException{
     	List<Movimiento> lista = null;
    	try{
   			lista = boMovimiento.getListXCtaExpediente(intPersEmpresa, intCuenta, intItemExpediente, intItemExpedienteDetalle);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	

   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public Movimiento getMaximoMovimiento() throws BusinessException{
   		Movimiento dto = null;
		try{
			dto = boMovimiento.getMaximoMovimiento();
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
   	}

   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public Cronograma getCronogramaPorPK(CronogramaId cronId) throws BusinessException{
   		Cronograma dto = null;
		try{
			dto = boCronograma.getCronogramaPorPK(cronId);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
   	}
   	
   	
   	
   	public List<CuentaConceptoDetalle> getCuentaConceptoDetallePorPKCuentaYTipoConcepto(CuentaConceptoDetalle o)throws BusinessException{
		List<CuentaConceptoDetalle> dto = null;
		try{
			dto = boCuentaConceptoDetalle.getCuentaConceptoDetallePorPKCuentaYTipoConcepto(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	public EstadoExpediente grabarEstado(EstadoExpediente estadoExpediente)throws BusinessException{
		EstadoExpediente dto = null;
		try{
			dto = boEstado.grabar(estadoExpediente);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
   	
   	
   	/**
   	 * metodo que graba expediente, cronograma y estado para actividad.
   	 * @param expediente
   	 * @return
   	 * @throws BusinessException
   	 */
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Expediente grabarExpedienteCompleto(Expediente expediente)throws BusinessException{
		Expediente dto = null;
		try{
			dto = conceptoService.grabarExpedienteCompleto(expediente);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
   	
   	
   	
   	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
  	public List<EstadoExpediente> getListaPorPkExpedienteCredito(ExpedienteId pId) throws BusinessException{
     	List<EstadoExpediente> lista = null;
    	try{
   			lista = boEstadoExpediente.getListaPorPkExpedienteCredito(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
   	
   	
   	public void modificarPorBeneficiario(CuentaDetalleBeneficio cuentaDetalleBeneficio)throws BusinessException{
		try{
			boCuentaDetalleBeneficio.modificarPorBeneficiario(cuentaDetalleBeneficio);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}
   	
   	
   	/**
   	 * 
   	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	  public List<Movimiento> getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(Integer intPersEmpresa, Integer intCuenta, Integer intItemCuentaConcepto, Integer intTipoConceptoGeneral) throws BusinessException{
	   	List<Movimiento> lista = null;
  	try{	
 			lista = boMovimiento.getListaMovimientoPorCtaItemConceptoTipoConceptoGeneral(intPersEmpresa, intCuenta, intItemCuentaConcepto, intTipoConceptoGeneral);
 		}catch(BusinessException e){
 			throw e;
 		}catch(Exception e){
 			throw new BusinessException(e);
 		}
 		return lista;
 	}
	
	
	/**
	 * Recupera el ultimo movimiento en base al id de cuentaconcepto
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intItemCuentaConcepto
	 * @param intTipoConceptoGeneral
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	  public List<Movimiento> getListaMaximoMovimientoPorCuentaConcepto(Integer intPersEmpresa, Integer intCuenta, Integer intItemCuentaConcepto,Integer intTipoConceptoGeneral) throws BusinessException{
	   	List<Movimiento> lista = null;
	try{	
			lista = boMovimiento.getListaMaximoMovimientoPorCuentaConcepto(intPersEmpresa, intCuenta, intItemCuentaConcepto,intTipoConceptoGeneral);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 *  Recupera el ultimo detalle de cta concepto en base al id de cuentaconcepto
	 * @param ctaCtoId
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	  public List<CuentaConceptoDetalle> getMaxCuentaConceptoDetPorPKCuentaConcepto(CuentaConceptoId ctaCtoId) throws BusinessException{
	   	List<CuentaConceptoDetalle> lista = null;
	try{	
			lista = boCuentaConceptoDetalle.getMaxCuentaConceptoDetPorPKCuentaConcepto(ctaCtoId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los tipos de credito (1)Prestamo (2)orden de credito y (5) refinanciamiento
	 * @param intEmpresa
	 * @param intCuenta
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Expediente> getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(Integer intEmpresa,Integer intCuenta)throws BusinessException{
   		List<Expediente> lista = null;
   		List<EstadoExpediente> listaEstados = null;
   		List<Expediente> listaExpedientes = null;
		try{
			lista = boExpediente.getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(intEmpresa, intCuenta);
			if(lista != null && !lista.isEmpty()){
				listaExpedientes = new ArrayList<Expediente>();
				for (Expediente expediente : lista) {
						listaEstados = boEstadoExpediente.getListaPorPkExpedienteCredito(expediente.getId());
						if(listaEstados != null && !listaEstados.isEmpty()){
							expediente.setListaEstadosExpediente(listaEstados);	
						}
						listaExpedientes.add(expediente);
				}
				
			}
			
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaExpedientes;
	}
	
	
	
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<ConceptoPago> getListaConceptoPagoPorCuentaConceptoDet(CuentaConceptoDetalleId ctaCtoDetId)throws BusinessException{
   		List<ConceptoPago> lista = null;
		try{
			lista = boConceptoPago.getListaConceptoPagoPorCuentaConceptoDet(ctaCtoDetId);

		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	public InteresProvisionado grabarInteresProvisionado(InteresProvisionado interesProvisionado)throws BusinessException{
		InteresProvisionado dto = null;
		try{
			dto = conceptoService.grabarInteresProvisionado(interesProvisionado);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	

	
	
   	public List<InteresCancelado> getListaInteresCanceladoPorExpedienteCredito(ExpedienteId pId)throws BusinessException{
   		List<InteresCancelado> lista = null;
		try{
			lista = boInteresCancelado.getListaPorPkExpedienteCredito(pId);

		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
   	
   	public ConceptoPago grabarConceptoPago(ConceptoPago conceptoPago)throws BusinessException{
   		ConceptoPago dto = null;
		try{
			dto = conceptoService.grabarConceptoPago(conceptoPago);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	public ConceptoDetallePago grabarConceptoDetallePago(ConceptoDetallePago conceptoDetallePago)throws BusinessException{
		ConceptoDetallePago dto = null;
		try{
			dto = conceptoService.grabarConceptoDetallePago(conceptoDetallePago);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public InteresCancelado grabarInteresCancelado(InteresCancelado interesCancelado)throws BusinessException{
		InteresCancelado dto = null;
		try{
			dto = conceptoService.grabarInteresCancelado(interesCancelado);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConceptoPago modificarConceptoPago(ConceptoPago conceptoPago)throws BusinessException{
		ConceptoPago dto = null;
		try{
			dto = boConceptoPago.modificar(conceptoPago);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoExpediente grabarEstadoExpediente(EstadoExpediente estadoExpediente)throws BusinessException{
		EstadoExpediente dto = null;
		try{
			dto = conceptoService.grabarEstadoExpediente(estadoExpediente);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public CuentaConcepto getCuentaConceptoPorPK(CuentaConceptoId  o)throws BusinessException{
		CuentaConcepto dto = null;
		try{
			dto = boCuentaConcepto.getCuentaConceptoPorPK(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/**
	 * Graba un nuevo registro de CuentaDetalleBeneficio
	 */
	public CuentaDetalleBeneficio grabarCuentaDetalleBeneficio(CuentaDetalleBeneficio cuentaDetalleBeneficio)throws BusinessException{
		CuentaDetalleBeneficio dto = null;
		try{
			dto = conceptoService.grabarCuentaDetalleBeneficio(cuentaDetalleBeneficio);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 20-08-2013
	 * OBTENER MOVIMIENTOCTACTO POR NRO DE CUENTA Y EMPRESA (ORDENADOS POR FECHAMOVIMIENTO Y TIPOMOVIMIENTO)
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> getListXCuentaYEmpresa(Integer intPersEmpresa, Integer intCuenta) throws BusinessException{
	   	List<Movimiento> lista = null;
	   	try{	
			lista = boMovimiento.getListXCuentaYEmpresa(intPersEmpresa, intCuenta);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * 
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intParaTipoMovimiento
	 * @return
	 * @throws BusinessException
	 */
	public List<Movimiento> getMaxMovXCtaEmpresaTipoMov(Integer intPersEmpresa,Integer intCuenta, Integer intParaTipoMovimiento) throws BusinessException{
     	List<Movimiento> lista = null;
     	//Movimiento dto = null;
    	try{
   			lista = boMovimiento.getMaxMovXCtaEmpresaTipoMov(intPersEmpresa,intCuenta,intParaTipoMovimiento);

   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
	
	/**
	 * Recupera lista del ultimo Interes Cancelado en estado activo.
	 */
	public InteresCancelado getMaxInteresCanceladoPorExpediente(ExpedienteId pId) throws BusinessException{
		InteresCancelado dto = null;
		try{
			dto = boInteresCancelado.getMaxInteresCanceladoPorExpediente(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 25-09-2013 
	 * OBTENER CONCEPTO PAGO POR CUENTA CONCEPTO DETALLE Y PERIODO
	 * @param pId
	 * @param intPeriodoIni
	 * @param intPeriodoFin
	 * @return
	 * @throws BusinessException
	 */
	public List<ConceptoPago> getListaConceptoPagoPorCtaCptoDetYPeriodo(CuentaConceptoDetalleId pId, Integer intPeriodoIni, Integer intPeriodoFin)throws BusinessException{
		List<ConceptoPago> lista = null;
		try{
			lista = boConceptoPago.getListaConceptoPagoPorCtaCptoDetYPeriodo(pId,intPeriodoIni,intPeriodoFin);

		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}	
	
	
	/**
	 * Recupera el ultimo registro por id de expediente y  tipo concetpogeneral
	 * @param intPersEmpresa
	 * @param intCuenta
	 * @param intItemExpediente
	 * @param intItemExpedienteDetalle
	 * @param intTipoConceptoGral
	 * @return
	 * @throws BusinessException
	 */
	public Movimiento getMaxXExpYCtoGral(Integer intPersEmpresa,Integer intCuenta,Integer intItemExpediente, Integer intItemExpedienteDetalle, Integer intTipoConceptoGral) throws BusinessException{
		Movimiento dto = null;
		try{
			dto = boMovimiento.getMaxXExpYCtoGral(intPersEmpresa, intCuenta, intItemExpediente, intItemExpedienteDetalle, intTipoConceptoGral);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/**
	 * Modifica el registro de movimiento
	 * @param movimiento
	 * @return
	 * @throws BusinessException
	 */
	public Movimiento modificarMovimiento(Movimiento movimiento) throws BusinessException{
		Movimiento dto = null;
		try{
			dto = boMovimiento.modificar(movimiento);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013 
	 * OBTENER CONCEPTO DETALLE PAGO POR CONCEPTO PAGO PK 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<ConceptoDetallePago> getCptoDetPagoPorCptoPagoPK(ConceptoPagoId pId) throws BusinessException{
		List<ConceptoDetallePago> lista = null;
		try{
			lista = boConceptoDetallePago.getCptoDetPagoPorCptoPagoPK(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 27-09-2013 
	 * OBTENER MOVIMIENTO POR PK 
	 * @param intItemMovimiento
	 * @return
	 * @throws BusinessException
	 */
	public Movimiento getMovimientoPorPK(Integer intItemMovimiento) throws BusinessException{
		Movimiento dto = null;
		try{
			dto = boMovimiento.getPorPK(intItemMovimiento);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	
	/**
	 * Recupera los expediente de movimiento segun cuenta y expediente
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<Expediente> getListaExpedientePorCtaYExp (ExpedienteId pId) throws BusinessException{
		List<Expediente> lstExpedientes = null;
		try{
			lstExpedientes = boExpediente.getExpedientePorCtaYExp(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lstExpedientes;
		
	}
	
	
	
	/**
	 * Recupera el ultimo estado del expediente de movimiento 
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public EstadoExpediente getMaxEstadoExpPorPkExpediente (ExpedienteId pId) throws BusinessException{
		EstadoExpediente domain = null;
		try{
			domain = boEstadoExpediente.getMaxEstadoExpPorPkExpediente(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return domain;
		
	}
	
	
	/**
	 * Modifico estadoExpediente
	 */
	public EstadoExpediente modificarEstadoExpediente(EstadoExpediente estadoExpediente)throws BusinessException{
		EstadoExpediente dto = null;
		try{
			dto = boEstadoExpediente.modificar(estadoExpediente);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * JCHAVEZ 10.01.2014
	 * Recupera el ultimo registro de Concepto Pago por Pk Cuenta Concepto Detalle
	 * @param ctaCtoDetId
	 * @return
	 * @throws BusinessException
	 */
   	public List<ConceptoPago> getUltimoCptoPagoPorCuentaConceptoDet(CuentaConceptoDetalleId ctaCtoDetId)throws BusinessException{
   		List<ConceptoPago> lista = null;
		try{
			lista = boConceptoPago.getUltimoCptoPagoPorCuentaConceptoDet(ctaCtoDetId);

		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
   	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<ConceptoPago> getListaConceptoPagoToCobranza(CuentaConceptoDetalleId ctaCtoDetId)throws BusinessException{
   		List<ConceptoPago> lista = null;
		try{
			lista = boConceptoPago.getListaConceptoPagoToCobranza(ctaCtoDetId);

		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
   	
   	/**
	 * efectuado cobranza
	 * 
	 */
   	  @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	  public List<CuentaConceptoDetalle> getCuentaConceptoDetofCobranza(CuentaConceptoId ctaCtoId) throws BusinessException{
		   	List<CuentaConceptoDetalle> lista = null;
		try{	
				lista = boCuentaConceptoDetalle.getCuentaConceptoDetofCobranza(ctaCtoId);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lista;
		}
   	  /**
   	   * Agregado 19.05.2014 jchavez
   	   * Método que retorna lista de movimientos cta. por pagar de un determinado
   	   * expediente (PREVISION)
   	   * @param intPersEmpresa
   	   * @param intCuenta
   	   * @param intItemExpediente
   	   * @param intParaTipoConcepto
   	   * @return
   	   * @throws BusinessException
   	   */
	  public List<Movimiento> getListaMovVtaCtePorPagar(Integer intPersEmpresa,Integer intCuenta, Integer intItemExpediente, Integer intParaTipoConcepto, Integer intParaDocumentoGeneral) throws BusinessException{
		   	List<Movimiento> lista = null;
		try{	
				lista = boMovimiento.getListaMovVtaCtePorPagar(intPersEmpresa,intCuenta,intItemExpediente,intParaTipoConcepto, intParaDocumentoGeneral);
			}catch(BusinessException e){
				throw e;
			}catch(Exception e){
				throw new BusinessException(e);
			}
			return lista;
		}
  
     	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
       	public List<CuentaConcepto> getListaCuentaConceptoEmpresaCuentaOfCob(Integer intEmpresa,
       																		 Integer intCuenta)throws BusinessException{
       		List<CuentaConcepto> lista = null;
    		try{
    			lista = boCtaConceptoDetalleBloqueo.getListaCuentaConceptoEmpresaCuentaOfCob(intEmpresa, intCuenta);
    		}catch(BusinessException e){
    			throw e;
    		}catch(Exception e){
    			throw new BusinessException(e);
    		}
    		return lista;
    	}
     	//rVillarreal
     	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
       	public List<Movimiento> getMaxMovCtaCteXFechaMaxima(Integer intPersEmpresa)throws BusinessException{
     		List<Movimiento> lista = null;
    		try{
    			lista = boMovimiento.getMaxMovCtaCteXFecha(intPersEmpresa);
    		}catch(BusinessException e){
    			throw e;
    		}catch(Exception e){
    			throw new BusinessException(e);
    		}
    		return lista;
    	}
     	//rVillarreal
     	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
      	public List<BloqueoCuenta> getListaFondoSepelioMonto(Integer intEmpresa, Integer intCuenta) throws BusinessException{
         	List<BloqueoCuenta> lista = null;
        	try{
       			lista = boBloqueoCuenta.getListaFondoSepelioMonto(intEmpresa, intCuenta);
       		}catch(BusinessException e){
       			throw e;
       		}catch(Exception e){
       			throw new BusinessException(e);
       		}
       		return lista;
       	}
     /**
      * Agregado 05.06.2014
      * Método que retorna lista de movimientos cta. por pagar de un determinado
   	   * expediente (LIQUIDACION)
      * @param intPersEmpresa
      * @param intCuenta
      * @param intItemExpediente
      * @param intParaDocumentoGeneral
      * @return
      * @throws BusinessException
      */
 	public List<Movimiento> getListaMovCtaCtePorPagarLiq(Integer intPersEmpresa,Integer intCuenta, Integer intItemExpediente, Integer intParaDocumentoGeneral) throws BusinessException{
	   	List<Movimiento> lista = null;
	   	try{	
			lista = boMovimiento.getListaMovCtaCtePorPagarLiq(intPersEmpresa,intCuenta,intItemExpediente, intParaDocumentoGeneral);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}


}