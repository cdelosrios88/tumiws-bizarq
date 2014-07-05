package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.MovilidadDetalleDao;
import pe.com.tumi.tesoreria.egreso.domain.MovilidadDetalle;

public class MovilidadDetalleDaoIbatis extends TumiDaoIbatis implements MovilidadDetalleDao{

	public MovilidadDetalle grabar(MovilidadDetalle o) throws DAOException{
		MovilidadDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public MovilidadDetalle modificar(MovilidadDetalle o) throws DAOException{
		MovilidadDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<MovilidadDetalle> getListaPorPk(Object o) throws DAOException{
		List<MovilidadDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<MovilidadDetalle> getListaPorMovilidad(Object o) throws DAOException{
		List<MovilidadDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorMovilidad", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}	
