package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.ComputadoraAccesoDao;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraAcceso;

public class ComputadoraAccesoDaoIbatis extends TumiDaoIbatis implements ComputadoraAccesoDao {

	protected  static Logger log = Logger.getLogger(ComputadoraAccesoDaoIbatis.class);
	
	public ComputadoraAcceso grabar(ComputadoraAcceso o) throws DAOException {
		ComputadoraAcceso dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ComputadoraAcceso modificar(ComputadoraAcceso o) throws DAOException {
		ComputadoraAcceso dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<ComputadoraAcceso> getListaPorPk(Object o) throws DAOException {
		List<ComputadoraAcceso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ComputadoraAcceso> getListaPorCabecera(Object o) throws DAOException {
		List<ComputadoraAcceso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCabecera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}