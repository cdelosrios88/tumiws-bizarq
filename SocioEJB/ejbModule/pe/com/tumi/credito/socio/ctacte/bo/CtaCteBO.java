package pe.com.tumi.credito.socio.ctacte.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.ctacte.dao.CtaCteDao;
import pe.com.tumi.credito.socio.ctacte.dao.impl.CtaCteDaoIbatis;
import pe.com.tumi.credito.socio.ctacte.domain.CtaCte;
import pe.com.tumi.credito.socio.ctacte.domain.CtaCteId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CtaCteBO {
	
	private CtaCteDao dao = (CtaCteDao)TumiFactory.get(CtaCteDaoIbatis.class);
	
	public CtaCte grabarCtaCte(CtaCte o) throws BusinessException{
		CtaCte dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CtaCte modificarCtaCte(CtaCte o) throws BusinessException{
		CtaCte dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CtaCte getCtaCtePorPK(CtaCteId pPK) throws BusinessException{
		CtaCte domain = null;
		List<CtaCte> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCtacteCod", pPK.getIntParaTipoCtacteCod());
			mapa.put("intItemCtacte", pPK.getIntItemCtacte());
			lista = dao.getListaCtaCtePorPK(mapa);
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
	
}
