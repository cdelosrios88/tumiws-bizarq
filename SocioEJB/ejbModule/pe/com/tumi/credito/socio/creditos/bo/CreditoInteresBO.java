package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.creditos.dao.CondicionCreditoDao;
import pe.com.tumi.credito.socio.creditos.dao.CondicionHabilDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoInteresDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CondicionCreditoDaoIbatis;
import pe.com.tumi.credito.socio.creditos.dao.impl.CondicionHabilDaoIbatis;
import pe.com.tumi.credito.socio.creditos.dao.impl.CreditoInteresDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteresId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CreditoInteresBO {
	
	private CreditoInteresDao dao = (CreditoInteresDao)TumiFactory.get(CreditoInteresDaoIbatis.class);
	
	public CreditoInteres grabarCreditoInteres(CreditoInteres o) throws BusinessException{
		CreditoInteres dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoInteres modificarCreditoInteres(CreditoInteres o) throws BusinessException{
		CreditoInteres dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CreditoInteres getCreditoInteresPorPK(CreditoInteresId pPK) throws BusinessException{
		CreditoInteres domain = null;
		List<CreditoInteres> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intItemInteres",			pPK.getIntItemInteres());
			lista = dao.getListaCreditoInteresPorPK(mapa);
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
	
	public List<CreditoInteres> getListaPorPKCredito(CreditoId pPK) throws BusinessException{
		List<CreditoInteres> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			
			lista = dao.getListaCreditoInteresPorPKCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Object eliminarCreditoInteres(CreditoId pPK, String strPkCreditoInteres) throws BusinessException{
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("strPkCreditoInteres", 	strPkCreditoInteres);
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return strPkCreditoInteres;
	}
}
