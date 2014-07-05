package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.CuentaCierreDao;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CuentaCierreDaoIbatis extends TumiDaoIbatis implements CuentaCierreDao{

	public CuentaCierre grabar(CuentaCierre o) throws DAOException {
		CuentaCierre dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CuentaCierre modificar(CuentaCierre o) throws DAOException{
		CuentaCierre dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<CuentaCierre> getListaPorPk(Object o) throws DAOException{
		List<CuentaCierre> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaCierre> getListaPorBusqueda(Object o) throws DAOException{
		List<CuentaCierre> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
