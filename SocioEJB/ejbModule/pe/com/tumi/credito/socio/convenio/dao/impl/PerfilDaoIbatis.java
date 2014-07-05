package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PerfilDao;
import pe.com.tumi.credito.socio.convenio.domain.Perfil;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PerfilDaoIbatis extends TumiDaoIbatis implements PerfilDao{
	
	public Perfil grabar(Perfil o) throws DAOException {
		Perfil dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Perfil modificar(Perfil o) throws DAOException {
		Perfil dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Perfil> getListaPerfilPorPK(Object o) throws DAOException{
		List<Perfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Perfil> getListaPerfilPorPKAdenda(Object o) throws DAOException{
		List<Perfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkAdenda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}