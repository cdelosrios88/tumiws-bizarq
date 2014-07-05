package pe.com.tumi.parametro.general.bo;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.parametro.general.dao.DetraccionDao;
import pe.com.tumi.parametro.general.dao.impl.DetraccionDaoIbatis;
import pe.com.tumi.parametro.general.domain.Detraccion;

public class DetraccionBO {
	
	private DetraccionDao dao = (DetraccionDao)TumiFactory.get(DetraccionDaoIbatis.class);
	
	public List<Detraccion> getListaTodos() throws BusinessException{
		List<Detraccion> lista = null;
		try{
			lista = dao.getListaTodos();
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Detraccion getPorTipoDetraccion(Integer intTipoDetraccion) throws BusinessException{
		Detraccion detraccion = null;
		try{
			List<Detraccion> lista = getListaTodos();
			for(Detraccion detraccionBD : lista){
				if(detraccionBD.getIntParaTipoDetraccion().equals(intTipoDetraccion)){
					detraccion = detraccionBD;
					break;
				}	
			}
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return detraccion;
	} 
}