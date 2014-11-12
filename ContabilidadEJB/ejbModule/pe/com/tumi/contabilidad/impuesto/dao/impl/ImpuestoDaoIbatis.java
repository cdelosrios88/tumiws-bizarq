package pe.com.tumi.contabilidad.impuesto.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.impuesto.dao.ImpuestoDao;
import pe.com.tumi.contabilidad.impuesto.domain.Impuesto;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class ImpuestoDaoIbatis extends TumiDaoIbatis implements ImpuestoDao{
	
	public Impuesto grabar(Impuesto o) throws DAOException{
		Impuesto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Impuesto modificar(Impuesto o) throws DAOException{
		Impuesto dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Impuesto> getListaPersonaJuridica(Object o) throws DAOException {
		List<Impuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPersonaJurid", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Impuesto> getListaNombreDniRol(Object o) throws DAOException {
		List<Impuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaNombreDniRol", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<Impuesto> getBuscar(Object o) throws DAOException {
		List<Impuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<Impuesto> getListaImpuesto(Object o) throws DAOException {
		List<Impuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListImpuesto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Autor: jchavez / Tarea: Creación / Fecha: 18.08.2014 / 
	public List<Impuesto> getListaPorPk(Object o) throws DAOException {
		List<Impuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
