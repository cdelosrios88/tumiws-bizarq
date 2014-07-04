package pe.com.tumi.tesoreria.ingreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.ingreso.dao.IngresoDao;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;

public class IngresoDaoIbatis extends TumiDaoIbatis implements IngresoDao{

	public Ingreso grabar(Ingreso o) throws DAOException{
		Ingreso dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Ingreso modificar(Ingreso o) throws DAOException{
		Ingreso dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Ingreso> getListaPorPk(Object o) throws DAOException{
		List<Ingreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<Ingreso> getListaParaItem(Object o) throws DAOException{
		List<Ingreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaItem", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<Ingreso> getListaParaBuscar(Object o) throws DAOException{
		List<Ingreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<Ingreso> getListaParaDepositar(Object o) throws DAOException{
		List<Ingreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaDepositar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	public List<Ingreso> getListaIngNoEnlazados(Object o) throws DAOException{
		List<Ingreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaIngNoEnlazados", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
}