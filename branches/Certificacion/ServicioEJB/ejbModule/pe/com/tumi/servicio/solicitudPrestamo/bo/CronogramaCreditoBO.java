package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.CronogramaCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.CronogramaCreditoDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.CronogramaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;

public class CronogramaCreditoBO {
	
	private CronogramaCreditoDao dao = (CronogramaCreditoDao)TumiFactory.get(CronogramaCreditoDaoIbatis.class);
	
	public CronogramaCredito grabar(CronogramaCredito o) throws BusinessException{
		CronogramaCredito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CronogramaCredito modificar(CronogramaCredito o) throws BusinessException{
		CronogramaCredito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CronogramaCredito getPorPk(CronogramaCreditoId pId) throws BusinessException{
		CronogramaCredito domain = null;
		List<CronogramaCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			mapa.put("intItemCronograma", 		pId.getIntItemCronograma());
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

	public void deletePorPk(CronogramaCreditoId pId) throws BusinessException{
		
		try {
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			
			dao.deletePorPk(mapa);
		} catch(Exception e) {
			throw new BusinessException(e);
		}

	}
	
	/**
	 * Recupera los cronogramas de credito segun expediente de credito.
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<CronogramaCredito> getListaPorPkExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		List<CronogramaCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			
			lista = dao.getListaPorPkExpedienteCredito(mapa);
		} catch(DAOException e){
			throw new BusinessException(e);
		} catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Recupera los cronogramas de credito segun expediente de credito y el nro de cuota
	 * @param pId
	 * @param intNroCuota
	 * @return
	 * @throws BusinessException
	 */
	public List<CronogramaCredito> getListaPorPkExpedienteCuota(ExpedienteCreditoId pId, Integer intNroCuota) throws BusinessException{
		List<CronogramaCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			mapa.put("intNroCuota", 			intNroCuota);
			
			lista = dao.getListaPorPkExpedienteCuota(mapa);
		} catch(DAOException e){
			throw new BusinessException(e);
		} catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}