package pe.com.tumi.credito.socio.ctacte.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.ctacte.dao.CtaCteCondicionDao;
import pe.com.tumi.credito.socio.ctacte.dao.impl.CtaCteCondicionDaoIbatis;
import pe.com.tumi.credito.socio.ctacte.domain.CtaCteCondicion;
import pe.com.tumi.credito.socio.ctacte.domain.CtaCteCondicionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class CtaCteCondicionBO {
	
	private CtaCteCondicionDao dao = (CtaCteCondicionDao)TumiFactory.get(CtaCteCondicionDaoIbatis.class);
	
	public CtaCteCondicion grabarCtaCteCondicion(CtaCteCondicion o) throws BusinessException{
		CtaCteCondicion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CtaCteCondicion modificarCtaCteCondicion(CtaCteCondicion o) throws BusinessException{
		CtaCteCondicion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public CtaCteCondicion getCtaCteCondicionPorPK(CtaCteCondicionId pPK) throws BusinessException{
		CtaCteCondicion domain = null;
		List<CtaCteCondicion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCtacteCod", pPK.getIntParaTipoCtacteCod());
			mapa.put("intItemCtacte", pPK.getIntItemCtacte());
			mapa.put("intParaCondicionSocioCod", pPK.getIntParaCondicionSocioCod());
			lista = dao.getListaCtaCteCondicionPorPK(mapa);
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
