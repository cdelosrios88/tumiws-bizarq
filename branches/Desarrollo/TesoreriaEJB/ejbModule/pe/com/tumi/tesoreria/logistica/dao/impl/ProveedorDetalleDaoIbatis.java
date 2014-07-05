package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.ProveedorDetalleDao;
import pe.com.tumi.tesoreria.logistica.domain.ProveedorDetalle;

public class ProveedorDetalleDaoIbatis extends TumiDaoIbatis implements ProveedorDetalleDao{

	public ProveedorDetalle grabar(ProveedorDetalle o) throws DAOException{
		ProveedorDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public ProveedorDetalle modificar(ProveedorDetalle o) throws DAOException{
		ProveedorDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<ProveedorDetalle> getListaPorPk(Object o) throws DAOException{
		List<ProveedorDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ProveedorDetalle> getListaPorProveedor(Object o) throws DAOException{
		List<ProveedorDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorProveedor", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public void eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().queryForList(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
	}
}	
