package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CondicionHabilDao;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CondicionHabilDaoIbatis extends TumiDaoIbatis implements CondicionHabilDao{
	
	public CondicionHabil grabar(CondicionHabil o) throws DAOException {
		CondicionHabil dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CondicionHabil modificar(CondicionHabil o) throws DAOException {
		CondicionHabil dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CondicionHabil> getListaCondicionHabilPorPK(Object o) throws DAOException{
		List<CondicionHabil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CondicionHabil> getListaCondicionHabilPorPKCredito(Object o) throws DAOException{
		List<CondicionHabil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}