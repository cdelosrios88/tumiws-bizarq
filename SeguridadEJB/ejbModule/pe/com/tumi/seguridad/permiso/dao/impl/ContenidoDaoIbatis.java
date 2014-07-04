package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.ContenidoDao;
import pe.com.tumi.seguridad.permiso.domain.Contenido;

public class ContenidoDaoIbatis extends TumiDaoIbatis implements ContenidoDao {

	protected  static Logger log = Logger.getLogger(ContenidoDaoIbatis.class);
	
	public Contenido grabar(Contenido o) throws DAOException {
		Contenido dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Contenido modificar(Contenido o) throws DAOException {
		Contenido dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Contenido> getListaPorPk(Object o) throws DAOException {
		List<Contenido> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
