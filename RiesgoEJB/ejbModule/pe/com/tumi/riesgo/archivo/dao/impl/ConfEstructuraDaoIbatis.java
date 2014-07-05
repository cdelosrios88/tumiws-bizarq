package pe.com.tumi.riesgo.archivo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.archivo.dao.ConfEstructuraDao;
import pe.com.tumi.riesgo.archivo.domain.ConfEstructura;

public class ConfEstructuraDaoIbatis extends TumiDaoIbatis implements ConfEstructuraDao{
	
	public ConfEstructura grabar(ConfEstructura o) throws DAOException {
		ConfEstructura dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConfEstructura modificar(ConfEstructura o) throws DAOException {
		ConfEstructura dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConfEstructura> getListaPorPk(Object o) throws DAOException{
		List<ConfEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfEstructura> getListaModTipoSoEmp(Object o) throws DAOException{
		List<ConfEstructura> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaModTipoSoEmp", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}