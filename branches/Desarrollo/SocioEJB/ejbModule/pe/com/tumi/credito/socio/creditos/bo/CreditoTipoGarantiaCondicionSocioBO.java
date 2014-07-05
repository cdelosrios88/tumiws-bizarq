package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CreditoDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaCondicionSocioDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoTipoGarantiaCondicionSocioDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoTipoGarantiaCondicionSocioBO {
	
private CreditoTipoGarantiaCondicionSocioDao dao = (CreditoTipoGarantiaCondicionSocioDao)TumiFactory.get(CreditoTipoGarantiaCondicionSocioDaoIbatis.class);
	
	public CondicionSocioTipoGarantia grabarCreditoTipoGarantia(CondicionSocioTipoGarantia o) throws BusinessException{
		CondicionSocioTipoGarantia dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CondicionSocioTipoGarantia modificarCreditoTipoGarantia(CondicionSocioTipoGarantia o) throws BusinessException{
		CondicionSocioTipoGarantia dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CondicionSocioTipoGarantia getCreditoTipoGarantiaPorPK(CondicionSocioTipoGarantiaId pPK) throws BusinessException{
		CondicionSocioTipoGarantia domain = null;
		List<CondicionSocioTipoGarantia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod", 	pPK.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia", 	pPK.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo", 	pPK.getIntItemGarantiaTipo());
			mapa.put("intParaCondicionSocioCod",pPK.getIntParaCondicionSocioCod());
			
			lista = dao.getListaCondicionSocioTipoGarantiaPorPK(mapa);
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
	
	public List<CondicionSocioTipoGarantia> getListaCondicionSocioPorPKCreditoTipoGarantia(CreditoTipoGarantiaId pCreditoGarantia) throws BusinessException{
		List<CondicionSocioTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 		pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",			pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",	pCreditoGarantia.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",	pCreditoGarantia.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo",		pCreditoGarantia.getIntItemGarantiaTipo());
			
			lista = dao.getListaCondicionSocioTipoGarantiaPorCreditoTipoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<CondicionSocioTipoGarantia> getListaCondicionSocioPorCreditoTipoGarantia(CreditoTipoGarantiaId pCreditoGarantia) throws BusinessException{
		List<CondicionSocioTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 		pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",			pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",	pCreditoGarantia.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia",	pCreditoGarantia.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo",		pCreditoGarantia.getIntItemGarantiaTipo());
			
			lista = dao.getListaCondicionSocioPorCreditoTipoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
 