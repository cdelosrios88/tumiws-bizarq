package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.creditos.dao.CreditoDescuentoDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoDescuentoDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoDescuentoBO {
	
private CreditoDescuentoDao dao = (CreditoDescuentoDao)TumiFactory.get(CreditoDescuentoDaoIbatis.class);
	
	public CreditoDescuento grabarCreditoDescuento(CreditoDescuento o) throws BusinessException{
		CreditoDescuento dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoDescuento modificarCreditoDescuento(CreditoDescuento o) throws BusinessException{
		CreditoDescuento dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoDescuento getCreditoDescuentoPorPK(CreditoDescuentoId pPK) throws BusinessException{
		CreditoDescuento domain = null;
		List<CreditoDescuento> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intItemCreditoDescuento", pPK.getIntItemCreditoDescuento());
			
			lista = dao.getListaCreditoDescuentoPorPK(mapa);
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
	
	public List<CreditoDescuento> getListaCreditoDescuentoPorPKCredito(CreditoId pCredito) throws BusinessException{
		List<CreditoDescuento> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCredito.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCredito.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCredito.getIntItemCredito());
			lista = dao.getListaCreditoDescuentoPorPKCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public CreditoDescuento eliminarCreditoDescuento(CreditoDescuento o) throws BusinessException{
		CreditoDescuento dto = null;
		try{
			dto = dao.eliminarCreditoDescuento(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
}