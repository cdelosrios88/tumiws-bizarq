package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaCondicionHabilDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoTipoGarantiaCondicionHabilDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoTipoGarantiaCondicionHabilBO {
	
private CreditoTipoGarantiaCondicionHabilDao dao = (CreditoTipoGarantiaCondicionHabilDao)TumiFactory.get(CreditoTipoGarantiaCondicionHabilDaoIbatis.class);
	
	public CondicionHabilTipoGarantia grabarCreditoTipoGarantia(CondicionHabilTipoGarantia o) throws BusinessException{
		CondicionHabilTipoGarantia dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CondicionHabilTipoGarantia modificarCreditoTipoGarantia(CondicionHabilTipoGarantia o) throws BusinessException{
		CondicionHabilTipoGarantia dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CondicionHabilTipoGarantia getCreditoTipoGarantiaPorPK(CondicionHabilTipoGarantiaId pPK) throws BusinessException{
		CondicionHabilTipoGarantia domain = null;
		List<CondicionHabilTipoGarantia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod", 	pPK.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia", 	pPK.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo", 	pPK.getIntItemGarantiaTipo());
			mapa.put("intParaTipoHabilCod",		pPK.getIntParaTipoHabilCod());
			
			lista = dao.getListaCondicionHabilTipoGarantiaPorPK(mapa);
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
	 * 
	 * @param pCreditoGarantia
	 * @return
	 * @throws BusinessException
	 */
	public List<CondicionHabilTipoGarantia> getListaCondicionHabilPorPKCreditoTipoGarantia(CreditoTipoGarantiaId pCreditoGarantia) throws BusinessException{
		List<CondicionHabilTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",		pCreditoGarantia.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",		pCreditoGarantia.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo",			pCreditoGarantia.getIntItemGarantiaTipo());
			lista = dao.getListaCondicionHabilTipoGarantiaPorCreditoTipoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CondicionHabilTipoGarantia> getListaCondicionHabilPorCreditoTipoGarantia(CreditoTipoGarantiaId pCreditoGarantia) throws BusinessException{
		List<CondicionHabilTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",		pCreditoGarantia.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",		pCreditoGarantia.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo",			pCreditoGarantia.getIntItemGarantiaTipo());
			lista = dao.getListaCondicionHabilPorCreditoTipoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
 