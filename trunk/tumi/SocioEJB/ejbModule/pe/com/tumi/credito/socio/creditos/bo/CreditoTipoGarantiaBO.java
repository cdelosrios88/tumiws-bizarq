package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.CaptacionDao;
import pe.com.tumi.credito.socio.captacion.dao.CondicionDao;
import pe.com.tumi.credito.socio.captacion.dao.impl.CaptacionDaoIbatis;
import pe.com.tumi.credito.socio.captacion.dao.impl.CondicionDaoIbatis;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.creditos.dao.CreditoDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoGarantiaDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoTipoGarantiaDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoDaoIbatis;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoGarantiaDaoIbatis;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoTipoGarantiaDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantiaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoTipoGarantiaBO {
	
private CreditoTipoGarantiaDao dao = (CreditoTipoGarantiaDao)TumiFactory.get(CreditoTipoGarantiaDaoIbatis.class);
	
	public CreditoTipoGarantia grabarCreditoTipoGarantia(CreditoTipoGarantia o) throws BusinessException{
		CreditoTipoGarantia dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoTipoGarantia modificarCreditoTipoGarantia(CreditoTipoGarantia o) throws BusinessException{
		CreditoTipoGarantia dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoTipoGarantia getCreditoTipoGarantiaPorPK(CreditoTipoGarantiaId pPK) throws BusinessException{
		CreditoTipoGarantia domain = null;
		List<CreditoTipoGarantia> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod", 	pPK.getIntParaTipoGarantiaCod());
			mapa.put("intItemCreditoGarantia", 	pPK.getIntItemCreditoGarantia());
			mapa.put("intItemGarantiaTipo", 	pPK.getIntItemGarantiaTipo());
			
			lista = dao.getListaCreditoPorPK(mapa);
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
	
	public List<CreditoTipoGarantia> getListaCreditoTipoGarantiaPorPKCreditoGarantia(CreditoGarantiaId pCreditoGarantia) throws BusinessException{
		List<CreditoTipoGarantia> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", 			pCreditoGarantia.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 		pCreditoGarantia.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito",				pCreditoGarantia.getIntItemCredito());
			mapa.put("intParaTipoGarantiaCod",		pCreditoGarantia.getIntParaTipoGarantiaCod());
			lista = dao.getListaCreditoTipoGarantiaPorPKCreditoGarantia(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
 