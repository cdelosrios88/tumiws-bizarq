package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.CuadroComparativoDao;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativo;

public class CuadroComparativoDaoIbatis extends TumiDaoIbatis implements CuadroComparativoDao{

	public CuadroComparativo grabar(CuadroComparativo o) throws DAOException{
		CuadroComparativo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public CuadroComparativo modificar(CuadroComparativo o) throws DAOException{
		CuadroComparativo dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<CuadroComparativo> getListaPorPk(Object o) throws DAOException{
		List<CuadroComparativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<CuadroComparativo> getListaPorBuscar(Object o) throws DAOException{
		List<CuadroComparativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}