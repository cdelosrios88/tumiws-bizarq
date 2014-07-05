package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoExcepcionAporteNoTransDao;
import pe.com.tumi.credito.socio.creditos.dao.FinalidadDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoExcepcionAporteNoTransDaoIbatis;
import pe.com.tumi.credito.socio.creditos.dao.impl.FinalidadDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionAporteNoTrans;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionAporteNoTransId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.Finalidad;
import pe.com.tumi.credito.socio.creditos.domain.FinalidadId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoExcepcionAporteNoTransBO {
	
	private CreditoExcepcionAporteNoTransDao dao = (CreditoExcepcionAporteNoTransDao)TumiFactory.get(CreditoExcepcionAporteNoTransDaoIbatis.class);
	
	public CreditoExcepcionAporteNoTrans grabarCreditoExepcionAporteNoTrans(CreditoExcepcionAporteNoTrans o) throws BusinessException{
		CreditoExcepcionAporteNoTrans dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoExcepcionAporteNoTrans modificarCreditoExepcionAporteNoTrans(CreditoExcepcionAporteNoTrans o) throws BusinessException{
		CreditoExcepcionAporteNoTrans dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoExcepcionAporteNoTrans getAporteNoTransPorPK(CreditoExcepcionAporteNoTransId pPK) throws BusinessException{
		CreditoExcepcionAporteNoTrans domain = null;
		List<CreditoExcepcionAporteNoTrans> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intItemCreditoExcepcion",	pPK.getIntItemCreditoExcepcion());
			mapa.put("intParaTipoCaptacionCod",	pPK.getIntParaTipoCaptacionCod());
			lista = dao.getListaCreditoExcepcionAporteNoTransPorPK(mapa);
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
	
	public List<CreditoExcepcionAporteNoTrans> getListaAporteNoTransPorPKCreditoExcepcion(CreditoExcepcionId pPK) throws BusinessException{
		List<CreditoExcepcionAporteNoTrans> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intItemCreditoExcepcion", pPK.getIntItemCreditoExcepcion());
			
			lista = dao.getListaCreditoExcepcionAporteNoTransPorPKCreditoExcepcion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
