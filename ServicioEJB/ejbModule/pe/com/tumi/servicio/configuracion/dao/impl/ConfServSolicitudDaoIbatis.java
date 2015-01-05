package pe.com.tumi.servicio.configuracion.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.configuracion.dao.ConfServSolicitudDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;

public class ConfServSolicitudDaoIbatis extends TumiDaoIbatis implements ConfServSolicitudDao{
	
	public ConfServSolicitud grabar(ConfServSolicitud o) throws DAOException {
		ConfServSolicitud dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			e.printStackTrace();
//			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConfServSolicitud modificar(ConfServSolicitud o) throws DAOException {
		ConfServSolicitud dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConfServSolicitud> getListaPorPk(Object o) throws DAOException{
		List<ConfServSolicitud> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfServSolicitud> getListaPorBuscar(Object o) throws DAOException{
		List<ConfServSolicitud> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfServSolicitud> getListaPorTipoOperacionTipoRequisito(Object o) throws DAOException{
		List<ConfServSolicitud> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoOperacionTipoRequisito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<ConfServSolicitud> getListaPorBuscarRequisito(Object o) throws DAOException{
		List<ConfServSolicitud> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscarRequisitos", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}