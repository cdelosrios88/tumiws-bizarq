package pe.com.tumi.tesoreria.ingreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.ingreso.dao.IngresoDetalleDao;
import pe.com.tumi.tesoreria.ingreso.domain.IngresoDetalle;

public class IngresoDetalleDaoIbatis extends TumiDaoIbatis implements IngresoDetalleDao{

	public IngresoDetalle grabar(IngresoDetalle o) throws DAOException{
		IngresoDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public IngresoDetalle modificar(IngresoDetalle o) throws DAOException{
		IngresoDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<IngresoDetalle> getListaPorPk(Object o) throws DAOException{
		List<IngresoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<IngresoDetalle> getListaPorIngreso(Object o) throws DAOException{
		List<IngresoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIngreso", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<IngresoDetalle> getPorControlFondosFijos(Object o) throws DAOException{
		List<IngresoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPorControlFondosFijos", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}