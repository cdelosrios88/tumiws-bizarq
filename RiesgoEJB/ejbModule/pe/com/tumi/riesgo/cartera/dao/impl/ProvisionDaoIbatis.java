package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.ProvisionDao;
import pe.com.tumi.riesgo.cartera.domain.Provision;

public class ProvisionDaoIbatis extends TumiDaoIbatis implements ProvisionDao{
	
	public Provision grabar(Provision o) throws DAOException {
		Provision dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Provision modificar(Provision o) throws DAOException {
		Provision dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Provision> getListaPorPk(Object o) throws DAOException{
		List<Provision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Provision> getListaPorEspecificacion(Object o) throws DAOException{
		List<Provision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEspecificacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}