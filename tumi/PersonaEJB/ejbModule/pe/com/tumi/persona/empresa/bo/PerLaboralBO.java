package pe.com.tumi.persona.empresa.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.empresa.dao.ActividadEconomicaDao;
import pe.com.tumi.persona.empresa.dao.PerLaboralDao;
import pe.com.tumi.persona.empresa.dao.impl.ActividadEconomicaDaoIbatis;
import pe.com.tumi.persona.empresa.dao.impl.PerLaboralDaoIbatis;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.ActividadEconomicaPK;
import pe.com.tumi.persona.empresa.domain.PerLaboral;
import pe.com.tumi.persona.empresa.domain.PerLaboralPK;

public class PerLaboralBO {
	
	private PerLaboralDao dao = (PerLaboralDao)TumiFactory.get(PerLaboralDaoIbatis.class);
	
	public PerLaboral grabarPerLaboral(PerLaboral o) throws BusinessException{
		PerLaboral dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PerLaboral modificarPerLaboral(PerLaboral o) throws BusinessException{
		PerLaboral dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PerLaboral getPerLaboralPorPK(PerLaboralPK pPK) throws BusinessException{
		PerLaboral domain = null;
		List<PerLaboral> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			mapa.put("intItemLaboral", pPK.getIntItemLaboral());
			lista = dao.getListaPerLaboralPorPK(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de más de un registro coincidente");
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
	
	public List<PerLaboral> getListaPerLaboralPorIdPersona(Integer pId) throws BusinessException{
		List<PerLaboral> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pId);
			lista = dao.getListaPerLaboralPorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
