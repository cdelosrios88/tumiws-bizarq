package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.CuadroComparativoProveedorDetalleDao;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedorDetalle;

public class CuadroComparativoProveedorDetalleDaoIbatis extends TumiDaoIbatis implements CuadroComparativoProveedorDetalleDao{

	public CuadroComparativoProveedorDetalle grabar(CuadroComparativoProveedorDetalle o) throws DAOException{
		CuadroComparativoProveedorDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public CuadroComparativoProveedorDetalle modificar(CuadroComparativoProveedorDetalle o) throws DAOException{
		CuadroComparativoProveedorDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<CuadroComparativoProveedorDetalle> getListaPorPk(Object o) throws DAOException{
		List<CuadroComparativoProveedorDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<CuadroComparativoProveedorDetalle> getListaPorCuadroComparativoProveedor(Object o) throws DAOException{
		List<CuadroComparativoProveedorDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuadroCompPro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}