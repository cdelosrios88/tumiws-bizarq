package pe.com.tumi.riesgo.cartera.bo;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;
import pe.com.tumi.riesgo.cartera.dao.CarteraDao;
import pe.com.tumi.riesgo.cartera.dao.impl.CarteraDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.Cartera;

public class CarteraBO {
	
	private CarteraDao dao = (CarteraDao)TumiFactory.get(CarteraDaoIbatis.class);
	
	public Cartera grabar(Cartera o) throws BusinessException{
		Cartera dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Cartera modificar(Cartera o) throws BusinessException{
		Cartera dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Cartera getPorPk(Cartera pId) throws BusinessException{
		Cartera domain = null;
		List<Cartera> lista = null;
		try{
			HashMap mapa = new HashMap();
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
	
	public List<Cartera> buscar(Cartera c) throws BusinessException{
		List<Cartera> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("strNombre", c.getStrNombre());
			lista = dao.getListaPorBusqueda(mapa);
				
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
