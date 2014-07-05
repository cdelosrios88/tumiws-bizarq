package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoExcepcionDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoExcepcionDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoExcepcionBO {
	
private CreditoExcepcionDao dao = (CreditoExcepcionDao)TumiFactory.get(CreditoExcepcionDaoIbatis.class);
	
	public CreditoExcepcion grabarCreditoExcepcion(CreditoExcepcion o) throws BusinessException{
		CreditoExcepcion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoExcepcion modificarCreditoExcepcion(CreditoExcepcion o) throws BusinessException{
		CreditoExcepcion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoExcepcion getCreditoExcepcionPorPK(CreditoExcepcionId pPK) throws BusinessException{
		CreditoExcepcion domain = null;
		List<CreditoExcepcion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intItemCreditoExcepcion", pPK.getIntItemCreditoExcepcion());
			
			lista = dao.getListaCreditoExcepcionPorPK(mapa);
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
	
	public List<CreditoExcepcion> getListaCreditoExcepcionPorPKCredito(CreditoId pCredito) throws BusinessException{
		List<CreditoExcepcion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCredito.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCredito.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCredito.getIntItemCredito());
			lista = dao.getListaCreditoExcepcionPorPKCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoExcepcion eliminarCreditoExcepcion(CreditoExcepcion o) throws BusinessException{
		CreditoExcepcion dto = null;
		try{
			dto = dao.eliminarCreditoExcepcion(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}