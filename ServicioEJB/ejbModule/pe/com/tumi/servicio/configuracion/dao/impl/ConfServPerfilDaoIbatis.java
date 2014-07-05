package pe.com.tumi.servicio.configuracion.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.configuracion.dao.ConfServPerfilDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;

public class ConfServPerfilDaoIbatis extends TumiDaoIbatis implements ConfServPerfilDao{
	
	public ConfServPerfil grabar(ConfServPerfil o) throws DAOException {
		ConfServPerfil dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConfServPerfil modificar(ConfServPerfil o) throws DAOException {
		ConfServPerfil dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConfServPerfil> getListaPorPk(Object o) throws DAOException{
		List<ConfServPerfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfServPerfil> getListaPorCabecera(Object o) throws DAOException{
		List<ConfServPerfil> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCabecera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}