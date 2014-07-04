package pe.com.tumi.persona.empresa.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.empresa.dao.ActividadEconomicaDao;
import pe.com.tumi.persona.empresa.dao.impl.ActividadEconomicaDaoIbatis;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.ActividadEconomicaPK;

public class ActividadEconomicaBO {
	
	private ActividadEconomicaDao dao = (ActividadEconomicaDao)TumiFactory.get(ActividadEconomicaDaoIbatis.class);
	
	public ActividadEconomica grabarActividadEconomica(ActividadEconomica o) throws BusinessException{
		ActividadEconomica dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ActividadEconomica modificarActividadEconomica(ActividadEconomica o) throws BusinessException{
		ActividadEconomica dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ActividadEconomica getActividadEconomicaPorPK(ActividadEconomicaPK pPK) throws BusinessException{
		ActividadEconomica domain = null;
		List<ActividadEconomica> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			mapa.put("intItemActividad", pPK.getIntItemActividad());
			lista = dao.getListaActividadEconomicaPorPK(mapa);
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
	
	public List<ActividadEconomica> getListaActividadEconomicaPorIdPersona(Integer pId) throws BusinessException{
		List<ActividadEconomica> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pId);
			lista = dao.getListaActividadEconomicaPorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
