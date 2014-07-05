package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoDescuentoCaptacionDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoDescuentoDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoDescuentoCaptacionDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoDescuentoCaptacionBO {
	
private CreditoDescuentoCaptacionDao dao = (CreditoDescuentoCaptacionDao)TumiFactory.get(CreditoDescuentoCaptacionDaoIbatis.class);
	
	public CreditoDescuentoCaptacion grabarCreditoDescuento(CreditoDescuentoCaptacion o) throws BusinessException{
		CreditoDescuentoCaptacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoDescuentoCaptacion modificarCreditoDescuento(CreditoDescuentoCaptacion o) throws BusinessException{
		CreditoDescuentoCaptacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoDescuentoCaptacion getCreditoDescuentoCaptacionPorPK(CreditoDescuentoCaptacionId pPK) throws BusinessException{
		CreditoDescuentoCaptacion domain = null;
		List<CreditoDescuentoCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intItemCreditoDescuento", pPK.getIntItemCreditoDescuento());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			lista = dao.getListaCreditoDescuentoCaptacionPorPK(mapa);
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
	
	public List<CreditoDescuentoCaptacion> getListaCreditoDescuentoCaptacionPorPKCreditoDescuento(CreditoDescuentoId pCreditoDescuento) throws BusinessException{
		List<CreditoDescuentoCaptacion> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCreditoDescuento.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCreditoDescuento.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCreditoDescuento.getIntItemCredito());
			mapa.put("intItemCreditoDescuento",		pCreditoDescuento.getIntItemCreditoDescuento());
			lista = dao.getListaCreditoDescuentoCaptacionPorPKCreditoDescuento(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
 