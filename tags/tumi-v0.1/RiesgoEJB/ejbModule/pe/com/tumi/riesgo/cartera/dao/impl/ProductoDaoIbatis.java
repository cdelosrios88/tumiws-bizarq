package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.ProductoDao;
import pe.com.tumi.riesgo.cartera.domain.Producto;

public class ProductoDaoIbatis extends TumiDaoIbatis implements ProductoDao{
	
	public Producto grabar(Producto o) throws DAOException {
		Producto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Producto modificar(Producto o) throws DAOException {
		Producto dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Producto> getListaPorPk(Object o) throws DAOException{
		List<Producto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Producto> getListaPorIntItemCartera(Object o) throws DAOException{
		List<Producto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIntItemCartera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}