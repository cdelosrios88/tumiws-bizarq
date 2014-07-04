package pe.com.tumi.credito.socio.creditos.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CondicionCreditoDao;
import pe.com.tumi.credito.socio.creditos.dao.CondicionHabilDao;
import pe.com.tumi.credito.socio.creditos.dao.impl.CondicionCreditoDaoIbatis;
import pe.com.tumi.credito.socio.creditos.dao.impl.CondicionHabilDaoIbatis;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CondicionCreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CondicionHabilBO {
	
	private CondicionHabilDao dao = (CondicionHabilDao)TumiFactory.get(CondicionHabilDaoIbatis.class);
	
	public CondicionHabil grabarCondicionHabil(CondicionHabil o) throws BusinessException{
		CondicionHabil dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CondicionHabil modificarCondicionHabil(CondicionHabil o) throws BusinessException{
		CondicionHabil dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CondicionHabil getCondicionHabilPorPK(CondicionHabilId pPK) throws BusinessException{
		CondicionHabil domain = null;
		List<CondicionHabil> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			mapa.put("intParaTipoHabilCod",		pPK.getIntParaTipoHabilCod());
			lista = dao.getListaCondicionHabilPorPK(mapa);
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
	
	public List<CondicionHabil> getListaPorPKCredito(CreditoId pPK) throws BusinessException{
		List<CondicionHabil> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			
			lista = dao.getListaCondicionHabilPorPKCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
