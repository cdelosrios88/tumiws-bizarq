package pe.com.tumi.contabilidad.cierre.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import pe.com.tumi.contabilidad.cierre.bo.LibroDiarioBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroDiarioDetalleBO;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiario;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroDiarioId;
import pe.com.tumi.contabilidad.cierre.service.LibroDiarioService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;


@Stateless
public class LibroDiarioFacade extends TumiFacade implements LibroDiarioFacadeRemote, LibroDiarioFacadeLocal {
	
	LibroDiarioService libroDiarioService = (LibroDiarioService)TumiFactory.get(LibroDiarioService.class);
	LibroDiarioBO boLibroDiario = (LibroDiarioBO)TumiFactory.get(LibroDiarioBO.class);
	LibroDiarioDetalleBO boLibroDiarioDetalle = (LibroDiarioDetalleBO)TumiFactory.get(LibroDiarioDetalleBO.class);
	
	public LibroDiario grabarLibroDiario(LibroDiario o)throws BusinessException{
		LibroDiario dto = null;
		try{
			dto = libroDiarioService.grabarLibroDiario(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public LibroDiario modificarLibroDiario(LibroDiario o)throws BusinessException{
		LibroDiario dto = null;
		try{
			dto = libroDiarioService.modificarLibroDiario(o);
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
   	public LibroDiario getLibroDiarioPorPk(LibroDiarioId pId) throws BusinessException{
    	LibroDiario dto = null;
   		try{
   			dto = libroDiarioService.getLibroDiarioPorId(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
   	}
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
   	public List<LibroDiarioDetalle> getListaLibroDiarioDetallePorLibroDiario(LibroDiario libroDiario) throws BusinessException{
    	List<LibroDiarioDetalle> lista = null;
   		try{
   			lista = boLibroDiarioDetalle.getPorLibroDiario(libroDiario);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
   	}
    
	public LibroDiarioDetalle modificarLibroDiarioDetalle(LibroDiarioDetalle o)throws BusinessException{
		LibroDiarioDetalle dto = null;
		try{
			dto = boLibroDiarioDetalle.modificar(o);
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
	public LibroDiario getLibroDiarioUltimoPorPk(LibroDiarioId pId) throws BusinessException{
		LibroDiario dto = null;
		try{
			System.out.println("--hhh"+pId);
			dto = boLibroDiario.buscarUltimoParaCodigoLibro(pId);
			System.out.println("--hhh"+dto);
		}catch(BusinessException e){
			throw e;
	   	}catch(Exception e){
	   		throw new BusinessException(e);
	   	}
	   	return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<LibroDiario> buscarLibroDiario(LibroDiario libroDiario)throws BusinessException{
		List<LibroDiario> lista = null;
		try{
			lista = boLibroDiario.getListaPorBuscar(libroDiario);
		}catch(BusinessException e){			
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<LibroDiarioDetalle> buscarLibroDiarioDetalle(LibroDiarioDetalle libroDiarioDetalle)throws BusinessException{
		List<LibroDiarioDetalle> lista = null;
		try{
			lista = boLibroDiarioDetalle.getListaPorBuscar(libroDiarioDetalle);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public LibroDiarioDetalle grabarLibroDiarioDetalle(LibroDiarioDetalle o)throws BusinessException{
		LibroDiarioDetalle dto = null;
		try{
			dto = boLibroDiarioDetalle.grabar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}

	/* Inicio - GTorresBroussetP - 05.abr.2014 */
	/* Buscar Libro Diario Detalle por Periodo y Documento */
	public List<LibroDiarioDetalle> buscarLibroDiarioDetallePorPeriodoDocumento(LibroDiarioDetalle libroDiarioDetalle)
			throws BusinessException {
		List<LibroDiarioDetalle> lista = null;
		try{
			lista = boLibroDiarioDetalle.getListaPorPeriodoDocumento(libroDiarioDetalle);
		} catch(BusinessException e){
			throw e;
		} catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/* Fin - GTorresBroussetP - 05.abr.2014 */
}