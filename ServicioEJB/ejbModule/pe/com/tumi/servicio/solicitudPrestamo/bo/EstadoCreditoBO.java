package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.EstadoCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.EstadoCreditoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;

public class EstadoCreditoBO {
	
	private EstadoCreditoDao dao = (EstadoCreditoDao)TumiFactory.get(EstadoCreditoDaoIbatis.class);
	
	public EstadoCredito grabar(EstadoCredito o) throws BusinessException{
		EstadoCredito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoCredito modificar(EstadoCredito o) throws BusinessException{
		EstadoCredito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoCredito getPorPk(EstadoCreditoId pId) throws BusinessException{
		EstadoCredito domain = null;
		List<EstadoCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemEstado", 			pId.getIntItemEstado());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	//ELIMINAR EN UN FUTURO
	public List<EstadoCredito> getListaPorPkExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		List<EstadoCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			lista = dao.getListaPorExpedienteCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public EstadoCredito getMaxEstadoCreditoPorPokExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		EstadoCredito domain = null;
		List<EstadoCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			lista = dao.getMaxEstadoCreditoPorPokExpedienteCredito(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public EstadoCredito getMinEstadoCreditoPorPkExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		EstadoCredito domain = null;
		List<EstadoCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			lista = dao.getMinEstadoCreditoPorPkExpedienteCredito(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 05-09-2013
	 * OBTENER ESTADO CREDITO POR ID EXPEDIENTECREDITO Y ESTADOCREDITO (OPCIONAL)
	 * @param pId
	 * @param intParaEstadoCreditoCod (OPCIONAL)
	 * @return
	 * @throws BusinessException
	 */
	public List<EstadoCredito> getListaPorExpedienteCreditoPkYEstadoCredito(ExpedienteCreditoId pId, Integer intParaEstadoCreditoCod) throws BusinessException{
		List<EstadoCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			mapa.put("intParaEstadoCreditoCod", intParaEstadoCreditoCod);
			lista = dao.getListaPorExpedienteCreditoPkYEstadoCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}