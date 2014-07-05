package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaSituacionLaboralDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoTipoGarantiaSituacionLaboralDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantiaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoTipoGarantiaSituacionLaboralBO {
	
private CreditoTipoGarantiaSituacionLaboralDao dao = (CreditoTipoGarantiaSituacionLaboralDao)TumiFactory.get(CreditoTipoGarantiaSituacionLaboralDaoIbatis.class);
	
	public SituacionLaboralTipoGarantia grabar(SituacionLaboralTipoGarantia o) throws BusinessException{
		SituacionLaboralTipoGarantia dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SituacionLaboralTipoGarantia modificar(SituacionLaboralTipoGarantia o) throws BusinessException{
		SituacionLaboralTipoGarantia dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SituacionLaboralTipoGarantia getCreditoSituacionLaboralPorPK(SituacionLaboralTipoGarantiaId pPK) throws BusinessException{
		SituacionLaboralTipoGarantia domain = null;
		List<SituacionLaboralTipoGarantia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod", 	pPK.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia", 	pPK.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo", 	pPK.getIntItemGarantiaTipo());
			mapa.put("intParaSituacionLaboralCod",pPK.getIntParaSituacionLaboralCod());
			
			lista = dao.getListaSituacionLaboralTipoGarantiaPorPK(mapa);
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
	
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboralPorPKCreditoTipoGarantia(CreditoTipoGarantiaId pCreditoGarantia) throws BusinessException{
		List<SituacionLaboralTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",		pCreditoGarantia.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",		pCreditoGarantia.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo",			pCreditoGarantia.getIntItemGarantiaTipo());
			lista = dao.getListaSituacionLaboralTipoGarantiaPorCreditoTipoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboralPorCreditoTipoGarantia(CreditoTipoGarantiaId pCreditoGarantia) throws BusinessException{
		List<SituacionLaboralTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",		pCreditoGarantia.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",		pCreditoGarantia.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo",			pCreditoGarantia.getIntItemGarantiaTipo());
			lista = dao.getListaSituacionLaboralPorCreditoTipoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
 