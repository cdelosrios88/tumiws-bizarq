package pe.com.tumi.riesgo.cartera.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.cartera.dao.TiempoDao;
import pe.com.tumi.riesgo.cartera.dao.impl.TiempoDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.Cartera;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;
import pe.com.tumi.riesgo.cartera.domain.Tiempo;
import pe.com.tumi.riesgo.cartera.domain.TiempoId;

public class TiempoBO {
	
	private TiempoDao dao = (TiempoDao)TumiFactory.get(TiempoDaoIbatis.class);
	
	public Tiempo grabar(Tiempo o) throws BusinessException{
		Tiempo dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Tiempo modificar(Tiempo o) throws BusinessException{
		Tiempo dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Tiempo getPorPk(TiempoId pId) throws BusinessException{
		Tiempo domain = null;
		List<Tiempo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemTiempo", pId.getIntItemTiempo());
			mapa.put("intParaTipoSbsCod", pId.getIntParaTipoSbsCod());
			mapa.put("intParaTipoProvisionCod", pId.getIntParaTipoProvisionCod());
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
	
	
	public List<Tiempo> getPorCartera(Cartera cartera) throws BusinessException{
		List<Tiempo> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemCartera", cartera.getIntItemCartera());
			lista = dao.getListaPorIntItemCartera(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
