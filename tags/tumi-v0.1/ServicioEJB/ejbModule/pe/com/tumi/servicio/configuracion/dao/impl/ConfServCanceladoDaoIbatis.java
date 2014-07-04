package pe.com.tumi.servicio.configuracion.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.configuracion.dao.ConfServCanceladoDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServCancelado;

public class ConfServCanceladoDaoIbatis extends TumiDaoIbatis implements ConfServCanceladoDao{
	
	public ConfServCancelado grabar(ConfServCancelado o) throws DAOException {
		ConfServCancelado dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConfServCancelado modificar(ConfServCancelado o) throws DAOException {
		ConfServCancelado dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConfServCancelado> getListaPorPk(Object o) throws DAOException{
		List<ConfServCancelado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfServCancelado> getListaPorCabecera(Object o) throws DAOException{
		List<ConfServCancelado> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCabecera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}