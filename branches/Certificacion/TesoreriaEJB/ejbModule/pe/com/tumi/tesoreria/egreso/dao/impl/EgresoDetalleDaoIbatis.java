package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.EgresoDetalleDao;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;

public class EgresoDetalleDaoIbatis extends TumiDaoIbatis implements EgresoDetalleDao{

	public EgresoDetalle grabar(EgresoDetalle o) throws DAOException{
		EgresoDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public EgresoDetalle modificar(EgresoDetalle o) throws DAOException{
		EgresoDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<EgresoDetalle> getListaPorPk(Object o) throws DAOException{
		List<EgresoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EgresoDetalle> getListaPorEgreso(Object o) throws DAOException{
		List<EgresoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEgreso", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EgresoDetalle> getListaPorBuscar(Object o) throws DAOException{
		List<EgresoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}