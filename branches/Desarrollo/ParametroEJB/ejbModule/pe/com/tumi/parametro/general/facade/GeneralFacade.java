package pe.com.tumi.parametro.general.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.auditoria.bo.AuditoriaBO;
import pe.com.tumi.parametro.auditoria.bo.AuditoriaMotivoBO;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.domain.AuditoriaMotivo;
import pe.com.tumi.parametro.general.bo.ArchivoBO;
import pe.com.tumi.parametro.general.bo.DetraccionBO;
import pe.com.tumi.parametro.general.bo.TarifaBO;
import pe.com.tumi.parametro.general.bo.TipoArchivoBO;
import pe.com.tumi.parametro.general.bo.TipoCambioBO;
import pe.com.tumi.parametro.general.bo.UbigeoBO;
import pe.com.tumi.parametro.general.domain.Archivo;
import pe.com.tumi.parametro.general.domain.ArchivoId;
import pe.com.tumi.parametro.general.domain.Detraccion;
import pe.com.tumi.parametro.general.domain.Tarifa;
import pe.com.tumi.parametro.general.domain.TarifaId;
import pe.com.tumi.parametro.general.domain.TipoArchivo;
import pe.com.tumi.parametro.general.domain.TipoCambio;
import pe.com.tumi.parametro.general.domain.TipoCambioId;
import pe.com.tumi.parametro.general.domain.Ubigeo;
import pe.com.tumi.parametro.general.service.ArchivoService;

@Stateless
public class GeneralFacade extends TumiFacade implements GeneralFacadeRemote, GeneralFacadeLocal {
	private UbigeoBO boUbigeo = (UbigeoBO)TumiFactory.get(UbigeoBO.class);
	private TipoArchivoBO boTipoArchivo = (TipoArchivoBO)TumiFactory.get(TipoArchivoBO.class);
	private ArchivoService archivoService = (ArchivoService)TumiFactory.get(ArchivoService.class);
	private TipoCambioBO boTipoCambio = (TipoCambioBO)TumiFactory.get(TipoCambioBO.class);
	private TarifaBO boTarifa = (TarifaBO)TumiFactory.get(TarifaBO.class);
	private ArchivoBO boArchivo = (ArchivoBO)TumiFactory.get(ArchivoBO.class);
	private DetraccionBO boDetraccion = (DetraccionBO)TumiFactory.get(DetraccionBO.class);
	private AuditoriaBO boAuditoria = (AuditoriaBO)TumiFactory.get(AuditoriaBO.class);
	private AuditoriaMotivoBO boAuditoriaMotivo = (AuditoriaMotivoBO)TumiFactory.get(AuditoriaMotivoBO.class);

	protected static Logger log = Logger.getLogger(GeneralFacade.class);
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Ubigeo> getListaUbigeoDeDepartamento() throws BusinessException{
		List<Ubigeo> lista = null;
		try{
			lista = boUbigeo.getListaUbigeoDeDepartamento();
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Ubigeo> getListaUbigeoDeProvinciaPorIdUbigeo(Integer pIntId) throws BusinessException{
		List<Ubigeo> lista = null;
		try{
			lista = boUbigeo.getListaUbigeoDeProvinciaPorIdUbigeo(pIntId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Ubigeo> getListaUbigeoDeDistritoPorIdUbigeo(Integer pIntId) throws BusinessException{
		List<Ubigeo> lista = null;
		try{
			lista = boUbigeo.getListaUbigeoDeDistritoPorIdUbigeo(pIntId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TipoArchivo getTipoArchivoPorPk(Integer pId) throws BusinessException{
		TipoArchivo dto = null;
		try{
			dto = boTipoArchivo.getTipoArchivoPorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Archivo grabarArchivo(Archivo o)throws BusinessException{
		Archivo dto = null;
		try{
			dto = archivoService.grabarArchivo(o);
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
	public Archivo getArchivoPorPK(ArchivoId pId) throws BusinessException{
		Archivo dto = null;
		try{
			dto = archivoService.getArchivoPorPK(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public TipoCambio grabarTipoCambio(TipoCambio o) throws BusinessException{
		TipoCambio dto = null;
		try{
			dto = boTipoCambio.grabarTipoCambio(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public TipoCambio modificarTipoCambio(TipoCambio o) throws BusinessException{
		TipoCambio dto = null;
		try{
			dto = boTipoCambio.modificarTipoCambio(o);
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
	public TipoCambio getTipoCambioPorPK(TipoCambioId pId) throws BusinessException{
		TipoCambio dto = null;
		try{
			dto = boTipoCambio.getTipoCambioPorPK(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TipoCambio> getListaTipoCambioBusqueda(TipoCambio pTipoCambio) throws BusinessException{
		List<TipoCambio> lista = null;
		try{
			lista = boTipoCambio.getTipoCambioBusqueda(pTipoCambio);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Tarifa grabarTarifa(Tarifa o) throws BusinessException{
		Tarifa dto = null;
		try{
			dto = boTarifa.grabarTarifa(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Tarifa modificarTarifa(Tarifa o) throws BusinessException{
		Tarifa dto = null;
		try{
			dto = boTarifa.modificarTarifa(o);
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
	public Tarifa getTarifaPorPK(TarifaId pId) throws BusinessException{
		Tarifa dto = null;
		try{
			dto = boTarifa.getTarifaPorPK(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Tarifa> getListaTarifaBusqueda(Tarifa pTarifa) throws BusinessException{
		List<Tarifa> lista = null;
		try{
			lista = boTarifa.getTarifaBusqueda(pTarifa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Archivo getListaArchivoDeVersionFinalPorTipoYItem(Integer intParaTipoCod , Integer intItemArchivo) throws BusinessException{
		Archivo dto = new Archivo();
		try{
			dto = boArchivo.getListaArchivoDeVersionFinalPorTipoYItem(intParaTipoCod, intItemArchivo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Detraccion> getListaDetraccionTodos() throws BusinessException{
		List<Detraccion> lista = null;
		try{
			lista = boDetraccion.getListaTodos();
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Tarifa getTarifaIGV(Integer intIdEmpresa, Date dtFecha) throws BusinessException{
		Tarifa dto = null;
		try{
			dto = boTarifa.getTarifaIGV(intIdEmpresa, dtFecha);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Tarifa getTarifaIGVActual(Integer intIdEmpresa) throws BusinessException{
		Tarifa dto = null;
		try{
			dto = boTarifa.getTarifaIGVActual(intIdEmpresa);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Detraccion getDetraccionPorTipo(Integer intTipoDetraccion) throws BusinessException{
		Detraccion dto = null;
		try{
			dto = boDetraccion.getPorTipoDetraccion(intTipoDetraccion);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TipoCambio getTipoCambioActualPorClaseYMoneda(Integer pIntPersEmpresa,Integer pIntParaClaseTipoCambio,Integer pIntParaMoneda) throws BusinessException{
		TipoCambio dto = null;
		try{
			dto = boTipoCambio.getTipoCambioActualPorClaseYMoneda(pIntPersEmpresa,pIntParaClaseTipoCambio,pIntParaMoneda);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public TipoCambio getTipoCambioPorMonedaYFecha(Integer intParaMoneda, Date dtFechaCambio, Integer intIdEmpresa) throws BusinessException{
		TipoCambio tipoCambio = new TipoCambio();
		log.info("intParaMoneda"+intParaMoneda);
		try{
			log.info(1);
			if(intParaMoneda.equals(Constante.PARAM_T_TIPOMONEDA_SOLES)){
				log.info(2);
				tipoCambio = new TipoCambio();
				tipoCambio.setId(new TipoCambioId());
				tipoCambio.getId().setIntParaMoneda(intParaMoneda);
				tipoCambio.setBdPromedio(new BigDecimal(1));			
			}else{
				log.info(3);
				TipoCambioId tipoCambioId = new TipoCambioId();
				tipoCambioId.setIntPersEmpresa(intIdEmpresa);
				tipoCambioId.setDtParaFecha(dtFechaCambio);
				tipoCambioId.setIntParaClaseTipoCambio(Constante.PARAM_T_TIPOCAMBIO_BANCARIO);
				tipoCambioId.setIntParaMoneda(intParaMoneda);
				log.info(tipoCambioId);
				tipoCambio = getTipoCambioPorPK(tipoCambioId);
				log.info(tipoCambio);
				if(tipoCambio==null){
					tipoCambio = new TipoCambio();
					tipoCambio.setId(new TipoCambioId());
					tipoCambio.getId().setIntParaMoneda(intParaMoneda);
					tipoCambio.setBdPromedio(new BigDecimal(1));
				}
			}
			log.info(4);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return tipoCambio;
	}
	

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Auditoria grabarAuditoria(Auditoria pAuditoria) throws BusinessException{
		try{
			if(pAuditoria != null){
				pAuditoria = boAuditoria.grabarAuditoria(pAuditoria);
				if(pAuditoria.getListaAuditoriaMotivo() != null && !pAuditoria.getListaAuditoriaMotivo().isEmpty()){
					List<AuditoriaMotivo> lstMotivo = pAuditoria.getListaAuditoriaMotivo();
					for (AuditoriaMotivo motivo : lstMotivo) {
						motivo.setIntIdcodigo(pAuditoria.getIntIdcodigo());
						motivo = boAuditoriaMotivo.grabar(motivo);	
					}
				}
			}
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return pAuditoria;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AuditoriaMotivo grabarAuditoriaMotivo(AuditoriaMotivo pAuditoriaMotivo) throws BusinessException{
		AuditoriaMotivo dto = null;
		try{
			dto = boAuditoriaMotivo.grabar(pAuditoriaMotivo);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	public void eliminarArchivo(Archivo o)throws BusinessException{
		Archivo dto = null;
		try{
			dto = archivoService.eliminarArchivo(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}
	/** 
	 * 22.04.2014 RILLARREAL
	 * RECUPERA LA DESCRIPCION DEL UBIGEO.
	 * @param pIntId
	 * @return
	 * @throws BusinessException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Ubigeo> getListaPorIdUbigeo(Integer pIntId) throws BusinessException{
		List<Ubigeo> lista = null;
		try{
			lista = boUbigeo.getListaPorIdUbigeo(pIntId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}