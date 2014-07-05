package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoExcepcionDiasCobranzaDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoExcepcionDiasCobranzaDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionDiasCobranza;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionDiasCobranzaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoExcepcionDiasCobranzaBO {
	
	private CreditoExcepcionDiasCobranzaDao dao = (CreditoExcepcionDiasCobranzaDao)TumiFactory.get(CreditoExcepcionDiasCobranzaDaoIbatis.class);
	
	public CreditoExcepcionDiasCobranza grabarCreditoExepcionDiasCobranza(CreditoExcepcionDiasCobranza o) throws BusinessException{
		CreditoExcepcionDiasCobranza dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoExcepcionDiasCobranza modificarCreditoExepcionDiasCobranza(CreditoExcepcionDiasCobranza o) throws BusinessException{
		CreditoExcepcionDiasCobranza dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoExcepcionDiasCobranza getAporteNoTransPorPK(CreditoExcepcionDiasCobranzaId pPK) throws BusinessException{
		CreditoExcepcionDiasCobranza domain = null;
		List<CreditoExcepcionDiasCobranza> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intItemCreditoExcepcion",	pPK.getIntItemCreditoExcepcion());
			mapa.put("intParaDiasCobranzaCod",	pPK.getIntParaDiasCobranzaCod());
			lista = dao.getListaCreditoExcepcionDiasCobranzaPorPK(mapa);
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
	
	public List<CreditoExcepcionDiasCobranza> getListaDiasCobranzaPorPKCreditoExcepcion(CreditoExcepcionId pPK) throws BusinessException{
		List<CreditoExcepcionDiasCobranza> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intItemCreditoExcepcion", pPK.getIntItemCreditoExcepcion());
			
			lista = dao.getListaCreditoExcepcionDiasCobranzaPorPKCreditoExcepcion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
