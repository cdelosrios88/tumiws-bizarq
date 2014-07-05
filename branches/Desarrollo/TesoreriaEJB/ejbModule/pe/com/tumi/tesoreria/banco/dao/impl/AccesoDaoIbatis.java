package pe.com.tumi.tesoreria.banco.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.banco.dao.AccesoDao;
import pe.com.tumi.tesoreria.banco.dao.BancofondoDao;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

public class AccesoDaoIbatis extends TumiDaoIbatis implements AccesoDao{

	public Acceso grabar(Acceso o) throws DAOException{
		Acceso dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Acceso modificar(Acceso o) throws DAOException{
		Acceso dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Acceso> getListaPorPk(Object o) throws DAOException{
		List<Acceso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Acceso> getListaPorBusqueda(Object o) throws DAOException{
		List<Acceso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	
