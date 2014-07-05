package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaCondicionLaboralDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoTipoGarantiaCondicionLaboralDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoTipoGarantiaCondicionLaboralBO {
	
private CreditoTipoGarantiaCondicionLaboralDao dao = (CreditoTipoGarantiaCondicionLaboralDao)TumiFactory.get(CreditoTipoGarantiaCondicionLaboralDaoIbatis.class);
	
	public CondicionLaboralTipoGarantia grabar(CondicionLaboralTipoGarantia o) throws BusinessException{
		CondicionLaboralTipoGarantia dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CondicionLaboralTipoGarantia modificar(CondicionLaboralTipoGarantia o) throws BusinessException{
		CondicionLaboralTipoGarantia dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CondicionLaboralTipoGarantia getCreditoCondicionLaboralPorPK(CondicionLaboralTipoGarantiaId pPK) throws BusinessException{
		CondicionLaboralTipoGarantia domain = null;
		List<CondicionLaboralTipoGarantia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod", 	pPK.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia", 	pPK.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo", 	pPK.getIntItemGarantiaTipo());
			mapa.put("intParaCondicionLaboralCod",pPK.getIntParaCondicionLaboralCod());
			
			lista = dao.getListaCondicionLaboralTipoGarantiaPorPK(mapa);
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
	 * Recupera registros en cualquier estado
	 * @param pCreditoGarantia
	 * @return
	 * @throws BusinessException
	 */
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboralPorPKCreditoTipoGarantia(CreditoTipoGarantiaId pCreditoGarantia) throws BusinessException{
		List<CondicionLaboralTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",		pCreditoGarantia.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",		pCreditoGarantia.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo",			pCreditoGarantia.getIntItemGarantiaTipo());
			lista = dao.getListaCondicionLaboralTipoGarantiaPorCreditoTipoGarantia(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboralPorCreditoTipoGarantia(CreditoTipoGarantiaId pCreditoGarantia) throws BusinessException{
		List<CondicionLaboralTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",		pCreditoGarantia.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",		pCreditoGarantia.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo",			pCreditoGarantia.getIntItemGarantiaTipo());
			lista = dao.getListaCondicionLaboralPorCreditoTipoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
 