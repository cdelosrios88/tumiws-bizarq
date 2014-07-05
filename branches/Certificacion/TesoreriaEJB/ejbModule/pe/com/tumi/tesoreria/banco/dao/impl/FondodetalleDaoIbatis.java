package pe.com.tumi.tesoreria.banco.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.banco.dao.FondodetalleDao;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;
import pe.com.tumi.tesoreria.banco.domain.Fondodetalle;

public class FondodetalleDaoIbatis extends TumiDaoIbatis implements FondodetalleDao{

	public Fondodetalle grabar(Fondodetalle o) throws DAOException{
		Fondodetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Fondodetalle modificar(Fondodetalle o) throws DAOException{
		Fondodetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Fondodetalle> getListaPorPk(Object o) throws DAOException{
		List<Fondodetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Fondodetalle> getListaPorBancoFondo(Object o) throws DAOException{
		List<Fondodetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBancoFondo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Fondodetalle> getListaPorSubSucursalPK(Object o) throws DAOException{
		List<Fondodetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSubSucursalPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}	
