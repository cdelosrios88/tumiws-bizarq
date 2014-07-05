package pe.com.tumi.credito.socio.captacion.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.AfectoDao;
import pe.com.tumi.credito.socio.captacion.domain.Afecto;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AfectoDaoIbatis extends TumiDaoIbatis implements AfectoDao{
	
	public Afecto grabar(Afecto o) throws DAOException {
		Afecto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Afecto modificar(Afecto o) throws DAOException {
		Afecto dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Afecto> getListaAfectoPorPK(Object o) throws DAOException{
		List<Afecto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Afecto> getListaAfectoPorPKCaptacion(Object o) throws DAOException{
		List<Afecto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Afecto> getListaAfectadasPorPKCaptacion(Object o) throws DAOException{
		List<Afecto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAfectPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}