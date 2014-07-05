package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.AccesoEspecialDao;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;

public class AccesoEspecialDaoIbatis extends TumiDaoIbatis implements AccesoEspecialDao {

	protected  static Logger log = Logger.getLogger(AccesoEspecialDaoIbatis.class);
	
	public AccesoEspecial grabar(AccesoEspecial o) throws DAOException {
		AccesoEspecial dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AccesoEspecial modificar(AccesoEspecial o) throws DAOException {
		AccesoEspecial dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<AccesoEspecial> getListaPorPk(Object o) throws DAOException {
		List<AccesoEspecial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AccesoEspecial> getListaPorBusqueda(Object o) throws DAOException {
		List<AccesoEspecial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AccesoEspecial> getAccesoPorEmpresaUsuario(Object o) throws DAOException{
		List<AccesoEspecial> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getAccesoPorEmpresaUsuario", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}