package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.AccesoEspecialDao;
import pe.com.tumi.seguridad.permiso.dao.AccesoEspecialDetalleDao;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalle;

public class AccesoEspecialDetalleDaoIbatis extends TumiDaoIbatis implements AccesoEspecialDetalleDao {

	protected  static Logger log = Logger.getLogger(AccesoEspecialDetalleDaoIbatis.class);
	
	public AccesoEspecialDetalle grabar(AccesoEspecialDetalle o) throws DAOException {
		AccesoEspecialDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AccesoEspecialDetalle modificar(AccesoEspecialDetalle o) throws DAOException {
		AccesoEspecialDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<AccesoEspecialDetalle> getListaPorPk(Object o) throws DAOException {
		List<AccesoEspecialDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AccesoEspecialDetalle> getListaPorCabecera(Object o) throws DAOException {
		List<AccesoEspecialDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCabecera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}