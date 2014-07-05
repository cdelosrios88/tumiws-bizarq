package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.CuadroComparativoProveedorDao;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;

public class CuadroComparativoProveedorDaoIbatis extends TumiDaoIbatis implements CuadroComparativoProveedorDao{

	public CuadroComparativoProveedor grabar(CuadroComparativoProveedor o) throws DAOException{
		CuadroComparativoProveedor dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public CuadroComparativoProveedor modificar(CuadroComparativoProveedor o) throws DAOException{
		CuadroComparativoProveedor dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<CuadroComparativoProveedor> getListaPorPk(Object o) throws DAOException{
		List<CuadroComparativoProveedor> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuadroComparativoProveedor> getListaPorCuadroComparativo(Object o) throws DAOException{
		List<CuadroComparativoProveedor> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuadroComparativo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}