package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.Producto;

public interface ProductoDao extends TumiDao{
	public Producto grabar(Producto o) throws DAOException;
	public Producto modificar(Producto o) throws DAOException;
	public List<Producto> getListaPorPk(Object o) throws DAOException;
	public List<Producto> getListaPorIntItemCartera(Object o) throws DAOException;	
}
