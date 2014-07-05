package pe.com.tumi.riesgo.cartera.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.cartera.dao.EspecificacionDao;
import pe.com.tumi.riesgo.cartera.dao.impl.EspecificacionDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;
import pe.com.tumi.riesgo.cartera.domain.EspecificacionId;

public class EspecificacionBO {
	
	private EspecificacionDao dao = (EspecificacionDao)TumiFactory.get(EspecificacionDaoIbatis.class);
	
	public Especificacion grabar(Especificacion o) throws BusinessException{
		Especificacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Especificacion modificar(Especificacion o) throws BusinessException{
		Especificacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Especificacion getPorPk(EspecificacionId pId) throws BusinessException{
		Especificacion domain = null;
		List<Especificacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemEspecificacion", pId.getIntItemEspecificacion());
			mapa.put("intItemCartera", pId.getIntItemCartera());
			lista = dao.getListaPorPk(mapa);
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
	
	public List<Especificacion> getPorIntItemCartera(Integer intItemCartera) throws BusinessException{
		Especificacion domain = null;
		List<Especificacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemCartera", intItemCartera);
			lista = dao.getListaPorIntItemCartera(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
