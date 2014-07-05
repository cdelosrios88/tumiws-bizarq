package pe.com.tumi.riesgo.cartera.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.cartera.dao.PlantillaDao;
import pe.com.tumi.riesgo.cartera.dao.impl.PlantillaDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.Plantilla;
import pe.com.tumi.riesgo.cartera.domain.PlantillaId;

public class PlantillaBO {
	
	private PlantillaDao dao = (PlantillaDao)TumiFactory.get(PlantillaDaoIbatis.class);
	
	public Plantilla grabar(Plantilla o) throws BusinessException{
		Plantilla dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Plantilla modificar(Plantilla o) throws BusinessException{
		Plantilla dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Plantilla getPorPk(PlantillaId pId) throws BusinessException{
		Plantilla domain = null;
		List<Plantilla> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intParaTipoProvisionCod", pId.getIntParaTipoProvisionCod());
			mapa.put("intParaTipoSbsCod", pId.getIntParaTipoSbsCod());
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
	
	public List<Plantilla> getTodos() throws BusinessException{
		List<Plantilla> lista = null;
		try{
			lista = dao.getListaTodos();
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
