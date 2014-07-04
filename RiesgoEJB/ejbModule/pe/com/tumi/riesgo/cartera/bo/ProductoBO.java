package pe.com.tumi.riesgo.cartera.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.riesgo.cartera.dao.ProductoDao;
import pe.com.tumi.riesgo.cartera.dao.impl.ProductoDaoIbatis;
import pe.com.tumi.riesgo.cartera.domain.Producto;
import pe.com.tumi.riesgo.cartera.domain.ProductoId;

public class ProductoBO {
	
	private ProductoDao dao = (ProductoDao)TumiFactory.get(ProductoDaoIbatis.class);
	
	public Producto grabar(Producto o) throws BusinessException{
		Producto dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Producto modificar(Producto o) throws BusinessException{
		Producto dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Producto getPorPk(ProductoId pId) throws BusinessException{
		Producto domain = null;
		List<Producto> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemCartera", pId.getIntItemCartera());
			mapa.put("intParaTipoOperacionCod", pId.getIntParaTipoOperacionCod());
			mapa.put("intParaTipoConcepto", pId.getIntParaTipoConceptoCod());
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
	
	public List<Producto> getPorIntItemCartera(Integer intItemCartera) throws BusinessException{
		Producto domain = null;
		List<Producto> lista = null;
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
