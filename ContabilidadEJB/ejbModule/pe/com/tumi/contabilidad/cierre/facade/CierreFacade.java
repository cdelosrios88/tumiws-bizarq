package pe.com.tumi.contabilidad.cierre.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


import pe.com.tumi.common.util.AnexoDetalleException;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MayorizacionException;
import pe.com.tumi.contabilidad.cierre.bo.AnexoBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleCuentaBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleOperadorBO;
import pe.com.tumi.contabilidad.cierre.bo.CuentaCierreBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroMayorBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroMayorDetalleBO;
import pe.com.tumi.contabilidad.cierre.bo.RatioBO;
import pe.com.tumi.contabilidad.cierre.bo.RatioDetalleBO;
import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.contabilidad.cierre.domain.AnexoId;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorId;
import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.contabilidad.cierre.service.AnexoService;
import pe.com.tumi.contabilidad.cierre.service.CierreService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

@Stateless
public class CierreFacade extends TumiFacade implements CierreFacadeRemote, CierreFacadeLocal{

	CierreService cierreService = (CierreService)TumiFactory.get(CierreService.class);
	LibroMayorBO boLibroMayor = (LibroMayorBO)TumiFactory.get(LibroMayorBO.class);
	LibroMayorDetalleBO boLibroMayorDetalle = (LibroMayorDetalleBO)TumiFactory.get(LibroMayorDetalleBO.class);
	CuentaCierreBO boCuentaCierre = (CuentaCierreBO)TumiFactory.get(CuentaCierreBO.class);
	AnexoService anexoService = (AnexoService)TumiFactory.get(AnexoService.class);
	AnexoBO boAnexo = (AnexoBO)TumiFactory.get(AnexoBO.class);
	AnexoDetalleBO boAnexoDetalle = (AnexoDetalleBO)TumiFactory.get(AnexoDetalleBO.class);
	AnexoDetalleOperadorBO boAnexoDetalleOperador = (AnexoDetalleOperadorBO)TumiFactory.get(AnexoDetalleOperadorBO.class);
	AnexoDetalleCuentaBO boAnexoDetalleCuenta = (AnexoDetalleCuentaBO)TumiFactory.get(AnexoDetalleCuentaBO.class);
	RatioBO boRatio = (RatioBO)TumiFactory.get(RatioBO.class);
	RatioDetalleBO boRatioDetalle = (RatioDetalleBO)TumiFactory.get(RatioDetalleBO.class);
	
	
	public LibroMayor grabarLibroMayor(LibroMayor o)throws BusinessException{
		LibroMayor dto = null;
		try{
			dto = boLibroMayor.grabar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public LibroMayor modificarLibroMayor(LibroMayor o)throws BusinessException{
		LibroMayor dto = null;
		try{
			dto = boLibroMayor.modificar(o);
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
	public LibroMayor getLibroMayorPorPk(LibroMayorId pId) throws BusinessException{
		LibroMayor dto = null;
   		try{
   			dto = boLibroMayor.getPorPk(pId);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return dto;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<LibroMayor> buscarLibroMayor(LibroMayor o) throws BusinessException{
		List<LibroMayor> lista = null;
   		try{
   			lista = boLibroMayor.buscar(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<LibroMayor> getLibroMayorTodos() throws BusinessException{
		List<LibroMayor> lista = null;
   		try{
   			LibroMayor o = new LibroMayor();
   			lista = boLibroMayor.buscar(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<LibroMayorDetalle> getListaLibroMayorDetallePorLibroMayor(LibroMayor libroMayor) throws BusinessException{
		List<LibroMayorDetalle> lista = null;
   		try{   			
   			lista = boLibroMayorDetalle.getPorLibroMayor(libroMayor);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	public LibroMayor mayorizar(LibroMayor o)throws BusinessException, MayorizacionException{
		LibroMayor dto = null;
		try{
			dto = cierreService.mayorizar(o);
		}catch(MayorizacionException e){
			context.setRollbackOnly();
			throw e;
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public boolean eliminarMayorizacion(LibroMayor o)throws BusinessException{
		boolean exito = Boolean.FALSE;
		try{
			exito = cierreService.eliminarMayorizacion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return exito;
	}
	
	public CuentaCierre grabarCuentaCierre(CuentaCierre o) throws BusinessException{
		CuentaCierre dto = null;
		try{
			dto = cierreService.grabarCuentaCierre(o);
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
	public List<CuentaCierre> getListaCuentaCierrePorBusqueda(CuentaCierre o) throws BusinessException{
		List<CuentaCierre> lista = null;
   		try{   			
   			lista = cierreService.getListaCuentaCierrePorBusqueda(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	public CuentaCierre eliminarCuentaCierre(CuentaCierre o)throws BusinessException{
		CuentaCierre dto = null;
		try{
			o.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
			dto = boCuentaCierre.modificar(o);
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
	public List<CuentaCierreDetalle> getListaCuentaCierreDetallePorCuentaCierre(CuentaCierre cuentaCierre) throws BusinessException{
		List<CuentaCierreDetalle> lista = null;
   		try{   			
   			lista = cierreService.getListaCuentaCierreDetallePorCuentaCierre(cuentaCierre);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	public CuentaCierre modificarCuentaCierre(CuentaCierre o) throws BusinessException{
		CuentaCierre dto = null;
		try{
			dto = cierreService.modificarCuentaCierre(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}	
	
	public Anexo grabarAnexo(Anexo o) throws BusinessException{
		Anexo dto = null;
		try{
			dto = anexoService.grabarAnexo(o);
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
	public Anexo getAnexoPorPK(AnexoId o) throws BusinessException{
		Anexo anexo = null;
   		try{   			
   			anexo = boAnexo.getPorPk(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return anexo;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Anexo> buscarAnexo(Anexo o) throws BusinessException{
		List<Anexo> lista = null;
   		try{
   			lista = anexoService.buscarListaAnexo(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	public void eliminarAnexo(Anexo o) throws BusinessException, AnexoDetalleException{		
		try{
			anexoService.eliminarAnexo(o);
		}catch(AnexoDetalleException e){
			context.setRollbackOnly();
			throw e;
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AnexoDetalle> getListaAnexoDetallePorAnexo(Anexo o) throws BusinessException{
		List<AnexoDetalle> lista = null;
   		try{
   			lista = boAnexoDetalle.getPorAnexo(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AnexoDetalleOperador> getListaAnexoDetalleOperadorPorAnexoDetalle(AnexoDetalle o) throws BusinessException{
		List<AnexoDetalleOperador> lista = null;
   		try{
   			lista = boAnexoDetalleOperador.getPorAnexoDetalle(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	public Anexo modificarAnexo(Anexo o) throws BusinessException, AnexoDetalleException{
		Anexo dto = null;
		try{
			dto = anexoService.modificarAnexo(o);
		}catch(AnexoDetalleException e){
			context.setRollbackOnly();
			throw e;
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
	public List<AnexoDetalleCuenta> getListaAnexoDetalleCuentaPorAnexoDetalle(AnexoDetalle o) throws BusinessException{
		List<AnexoDetalleCuenta> lista = null;
   		try{
   			lista = boAnexoDetalleCuenta.getPorAnexoDetalle(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	public Ratio grabarRatio(Ratio o) throws BusinessException{
		Ratio dto = null;
		try{
			dto = anexoService.grabarRatio(o);
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
	public List<Ratio> buscarRatio(Ratio o) throws BusinessException{
		List<Ratio> lista = null;
   		try{
   			lista = anexoService.buscarRatio(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<RatioDetalle> getListaRatioDetallePorRatio(Ratio o) throws BusinessException{
		List<RatioDetalle> lista = null;
   		try{
   			lista = boRatioDetalle.getPorRatio(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AnexoDetalle getAnexoDetallePorPK(AnexoDetalleId o) throws BusinessException{
		AnexoDetalle domain = null;
   		try{
   			domain = boAnexoDetalle.getPorPk(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return domain;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Ratio eliminarRatio(Ratio o) throws BusinessException{
		Ratio domain = null;
   		try{
   			o.setIntParaEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ANULADO);
   			domain = boRatio.modificar(o);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return domain;
	}
	
	public Ratio modificarRatio(Ratio o) throws BusinessException{
		Ratio dto = null;
		try{
			dto = anexoService.modificarRatio(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
}