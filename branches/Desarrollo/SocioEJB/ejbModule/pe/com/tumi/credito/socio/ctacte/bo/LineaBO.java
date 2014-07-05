package pe.com.tumi.credito.socio.ctacte.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.ctacte.dao.LineaDao;
import pe.com.tumi.credito.socio.ctacte.dao.impl.LineaDaoIbatis;
import pe.com.tumi.credito.socio.ctacte.domain.Linea;
import pe.com.tumi.credito.socio.ctacte.domain.LineaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class LineaBO {
	
	private LineaDao dao = (LineaDao)TumiFactory.get(LineaDaoIbatis.class);
	
	public Linea grabarLinea(Linea o) throws BusinessException{
		Linea dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Linea modificarLinea(Linea o) throws BusinessException{
		Linea dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Linea getLineaPorPK(LineaId pPK) throws BusinessException{
		Linea domain = null;
		List<Linea> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCtacteCod", pPK.getIntParaTipoCtacteCod());
			mapa.put("intItemCtacte", pPK.getIntItemCtacte());
			mapa.put("intItem", pPK.getIntItem());
			lista = dao.getListaLineaPorPK(mapa);
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

