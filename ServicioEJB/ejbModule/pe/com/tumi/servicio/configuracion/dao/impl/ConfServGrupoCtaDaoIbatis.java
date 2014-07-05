package pe.com.tumi.servicio.configuracion.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.configuracion.dao.ConfServGrupoCtaDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServGrupoCta;

public class ConfServGrupoCtaDaoIbatis extends TumiDaoIbatis implements ConfServGrupoCtaDao{
	
	public ConfServGrupoCta grabar(ConfServGrupoCta o) throws DAOException {
		ConfServGrupoCta dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConfServGrupoCta modificar(ConfServGrupoCta o) throws DAOException {
		ConfServGrupoCta dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConfServGrupoCta> getListaPorPk(Object o) throws DAOException{
		List<ConfServGrupoCta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfServGrupoCta> getListaPorCabecera(Object o) throws DAOException{
		List<ConfServGrupoCta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCabecera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}