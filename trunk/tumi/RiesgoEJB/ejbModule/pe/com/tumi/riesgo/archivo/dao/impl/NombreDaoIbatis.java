package pe.com.tumi.riesgo.archivo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.archivo.dao.NombreDao;
import pe.com.tumi.riesgo.archivo.domain.Nombre;

public class NombreDaoIbatis extends TumiDaoIbatis implements NombreDao{
	
	public Nombre grabar(Nombre o) throws DAOException {
		Nombre dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Nombre modificar(Nombre o) throws DAOException {
		Nombre dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Nombre> getListaPorPk(Object o) throws DAOException{
		List<Nombre> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Nombre> getListaPorIntItemConfiguracion(Object o) throws DAOException{
		List<Nombre> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIntItemConfiguracion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}