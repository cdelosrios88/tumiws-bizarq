package pe.com.tumi.contabilidad.core.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.contabilidad.core.bo.ModeloBO;
import pe.com.tumi.contabilidad.core.bo.ModeloDetalleBO;
import pe.com.tumi.contabilidad.core.bo.ModeloDetalleNivelBO;
//import pe.com.tumi.contabilidad.core.bo.PlanCuentaBO;
import pe.com.tumi.contabilidad.core.domain.Modelo;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivel;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelComp;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleNivelId;
import pe.com.tumi.contabilidad.core.domain.ModeloId;
//import pe.com.tumi.contabilidad.core.domain.PlanCuenta;
//import pe.com.tumi.contabilidad.core.domain.PlanCuentaId;
import pe.com.tumi.contabilidad.core.service.ModeloService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
//import pe.com.tumi.parametro.general.domain.TipoCambio;

@Stateless
public class ModeloFacade extends TumiFacade implements ModeloFacadeRemote, ModeloFacadeLocal {
	ModeloBO boModelo = (ModeloBO)TumiFactory.get(ModeloBO.class);
	ModeloDetalleBO boModeloDetalle = (ModeloDetalleBO)TumiFactory.get(ModeloDetalleBO.class);
	ModeloDetalleNivelBO boModeloDetalleNivel = (ModeloDetalleNivelBO)TumiFactory.get(ModeloDetalleNivelBO.class);
	
	ModeloService modeloService = (ModeloService)TumiFactory.get(ModeloService.class);
	
	public Modelo grabarModelo(Modelo o)throws BusinessException{
		Modelo dto = null;
		try{
			dto = modeloService.grabarModelo(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Modelo modificarModelo(Modelo o)throws BusinessException{
		Modelo dto = null;
		try{
			dto = modeloService.modificarModelo(o);
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
   	public Modelo getModeloPorPk(ModeloId pId) throws BusinessException{
    	Modelo dto = null;
   		try{
   			dto = boModelo.getModeloPorPk(pId);	
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Modelo> getListaModeloBusqueda(Modelo pModelo) throws BusinessException{
		List<Modelo> lista = null;
		try{
			lista = modeloService.getListaModeloBusqueda(pModelo);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public Modelo getModeloConDetallePorId(ModeloId pId) throws BusinessException{
    	Modelo dto = null;
   		try{
   			dto = boModelo.getModeloPorPk(pId);
   			if(dto!=null){
   				dto.setListModeloDetalle(boModeloDetalle.getListaPorModeloId(dto.getId()));
   				
   			}
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Modelo> obtenerTipoModeloActual(Integer intCodigoModelo, Integer intIdEmpresa) throws BusinessException{
    	List<Modelo> lista = null;
   		try{
   			lista = modeloService.obtenerTipoModeloActual(intCodigoModelo, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<ModeloDetalle> getListaModeloDetallePorModeloId(ModeloId pId) throws BusinessException{
    	List<ModeloDetalle> lista = null;
   		try{
   			lista = boModeloDetalle.getListaPorModeloId(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<ModeloDetalleNivel> getListaModeloDetNivelPorModeloDetalleId(ModeloDetalleId pId) throws BusinessException{
    	List<ModeloDetalleNivel> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getListaPorModeloDetalleId(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<Modelo> obtenerTipoModeloRefinanciamiento(Integer intTipoModelo, Integer intIdEmpresa) throws BusinessException{
    	List<Modelo> lista = null;
   		try{
   			lista = modeloService.obtenerTipoModeloRefinanciamiento(intTipoModelo, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    /**
     * JCHAVEZ 30.12.2013
	 * Recupera Modelo Detalle Nivel en Giro Prestamos segun filtros
     * @param o
     * @return
     * @throws BusinessException
     */
    public List<ModeloDetalleNivelComp> getModeloGiroPrestamo(ModeloDetalleNivel o) throws BusinessException{
    	List<ModeloDetalleNivelComp> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getModeloGiroPrestamo(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
	/**
	 * JCHAVEZ 02.01.2014
	 * Recupera la pk de Modelo Detalle Nivel del préstamo a cancelar, haciendo
     * un cruce entre las cuentas obtenidas por el tipo de crédito y las obtenidas por la categoría
     * de RIESGO
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
    public List<ModeloDetalleNivelId> getPkModeloXReprestamo(ModeloDetalleNivel modeloFiltro, Integer intTipoCredito, Integer intItemCredito, Integer intCategoriaRiesgo) throws BusinessException{
    	List<ModeloDetalleNivelId> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getPkModeloXReprestamo(modeloFiltro, intTipoCredito, intItemCredito, intCategoriaRiesgo);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
	/**
	 * JCHAVEZ 02.01.2014
	 * Recupera el campo que se va a guardar en la grabación del libro diario
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
    public List<ModeloDetalleNivelComp> getCampoXPkModelo(ModeloDetalleNivel modeloFiltro) throws BusinessException{
    	List<ModeloDetalleNivelComp> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getCampoXPkModelo(modeloFiltro);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}    
    /**
     * JCHAVEZ 30.12.2013
	 * Recupera Modelo Detalle Nivel en Giro Prevision segun filtros
     * @param o
     * @return
     * @throws BusinessException
     */
    public List<ModeloDetalleNivelComp> getModeloGiroPrevision(ModeloDetalleNivel o) throws BusinessException{
    	List<ModeloDetalleNivelComp> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getModeloGiroPrevision(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    /**
     * JCHAVEZ 23.01.2014
	 * Recupera Modelo Detalle Nivel en Planilla Movilidad segun filtros
     * @param o
     * @return
     * @throws BusinessException
     */
    public List<ModeloDetalleNivelComp> getModeloPlanillaMovilidad(ModeloDetalleNivel o) throws BusinessException{
    	List<ModeloDetalleNivelComp> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getModeloPlanillaMovilidad(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	} 

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ModeloDetalle> getListaDebeOfCobranza(Integer intEmpresa,    												
													  Integer intPeriodo,												
													  Integer intCodigoModelo) throws BusinessException{
		    	List<ModeloDetalle> lista = null;
		   		try{
		   			lista = boModeloDetalle.getListaDebeOfCobranza(intEmpresa,
													   			   intPeriodo,										   					
													   			    intCodigoModelo);
		   		}catch(BusinessException e){
		   			throw e;
		   		}catch(Exception e){
		   			throw new BusinessException(e);
		   		}
		   		return lista;
		   	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIO10(Integer empresaDebe,
															  Integer periodoDebe,
															  Integer codigoModeloDebe,
															  Integer codigo) throws BusinessException{
		    	List<ModeloDetalle> lista = null;
		   		try{
		   			lista = boModeloDetalle.getListaDebeOfCobranzaUSUARIO10(empresaDebe,
														   					periodoDebe,										   					
														   					codigoModeloDebe,
														   					codigo);
		   		}catch(BusinessException e){
		   			throw e;
		   		}catch(Exception e){
		   			throw new BusinessException(e);
		   		}
		   		return lista;
		   	}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIONO10(Integer empresaDebe,
																  Integer periodoDebe,
																  Integer codigoModeloDebe,
																  Integer codigo) throws BusinessException{
		    	List<ModeloDetalle> lista = null;
		   		try{
		   			lista = boModeloDetalle.getListaDebeOfCobranzaUSUARIONO10(empresaDebe,
															   					periodoDebe,										   					
															   					codigoModeloDebe,
															   					codigo);
		   		}catch(BusinessException e){
		   			throw e;
		   		}catch(Exception e){
		   			throw new BusinessException(e);
		   		}
		   		return lista;
		   	}
    
	public String getCuentaPorPagar(Integer intEmpresa,								   
								Integer intPeriodo)throws BusinessException
			{
				String str = null;
		   		try{
		   			str = boModeloDetalleNivel.getCuentaPorPagar(intEmpresa,
		   													intPeriodo);
		   		}catch(BusinessException e){
		   			throw e;
		   		}catch(Exception e){
		   			throw new BusinessException(e);
		   		}
		   		return str;
			}
    
    

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<ModeloDetalleNivel> getNumeroCuentaPrestamo(Integer empresa,
										   									Integer periodo,
										   									Integer paraTipoRiesgo,
										   									Integer itemConcepto,
										   									Integer categoria,
										   									Integer conceptoGeneral,
										   									Integer intTipoModeloContable) throws BusinessException{
    	List<ModeloDetalleNivel> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getNumeroCuentaPrestamo(empresa,periodo,paraTipoRiesgo,itemConcepto,categoria,conceptoGeneral,intTipoModeloContable);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}


    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<ModeloDetalleNivel> getNroCtaPrestamoSinCategoria(Integer empresa,
										   						Integer periodo,
										   						Integer paraTipoRiesgo,
										   						Integer itemConcepto,
										   						Integer conceptoGeneral,
										   						Integer intTipoModeloContable) throws BusinessException{
    	List<ModeloDetalleNivel> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getNroCtaPrestamoSinCategoria(empresa,periodo,paraTipoRiesgo,itemConcepto,conceptoGeneral,intTipoModeloContable);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}

	/**
	 * jchavez 27.05.2014
	 * obtiene el modelo de provision de fdo. de retiro
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelComp> getModeloProvisionRetiro(ModeloDetalleNivel o) throws BusinessException{
		List<ModeloDetalleNivelComp> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getModeloProvisionRetiro(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
		return lista;
	}
	/**
	 * jchavez 27.05.2014
	 * obtiene el modelo de provision de fdo. de retiro interes generado y capitalizado
	 * @param modeloFiltro
	 * @return
	 * @throws BusinessException
	 */
	public List<ModeloDetalleNivelComp> getModeloProvRetiroInteres(ModeloDetalleNivel o) throws BusinessException{
		List<ModeloDetalleNivelComp> lista = null;
   		try{
   			lista = boModeloDetalleNivel.getModeloProvRetiroInteres(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}

	//Autor: fyalico / Tarea: Creación / Fecha: 11.09.2014 
	public String getCuentaPorCobrar(Integer intEmpresa,								   
									Integer intPeriodo)throws BusinessException
			{
				String str = null;
		   		try{
		   			str = boModeloDetalleNivel.getCuentaPorCobrar(intEmpresa,
		   													intPeriodo);
		   		}catch(BusinessException e){
		   			throw e;
		   		}catch(Exception e){
		   			throw new BusinessException(e);
		   		}
		   		return str;
			}	 
}


