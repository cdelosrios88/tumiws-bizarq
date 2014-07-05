package pe.com.tumi.riesgo.cartera.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.cartera.dao.PlantillaDetalleDao;
import pe.com.tumi.riesgo.cartera.dao.impl.PlantillaDetalleDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.PlantillaDetalle;
import pe.com.tumi.riesgo.cartera.domain.PlantillaDetalleId;

public class PlantillaDetalleBO {
	
	private PlantillaDetalleDao dao = (PlantillaDetalleDao)TumiFactory.get(PlantillaDetalleDaoIbatis.class);
	
	public PlantillaDetalle grabar(PlantillaDetalle o) throws BusinessException{
		PlantillaDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PlantillaDetalle modificar(PlantillaDetalle o) throws BusinessException{
		PlantillaDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PlantillaDetalle getPorPk(PlantillaDetalleId pId) throws BusinessException{
		PlantillaDetalle domain = null;
		List<PlantillaDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemPlantillaDetalle", pId.getIntItemPlantillaDetalle());
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
	
	public List<PlantillaDetalle> getTodos(Integer intTipoProvision) throws BusinessException{
		List<PlantillaDetalle> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intTipoProvision", intTipoProvision);
			lista = dao.getListaTodos(mapa);				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
