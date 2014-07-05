package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.DiccionarioDao;
import pe.com.tumi.seguridad.permiso.domain.Diccionario;

public class DiccionarioDaoIbatis extends TumiDaoIbatis implements DiccionarioDao {

	protected  static Logger log = Logger.getLogger(DiccionarioDaoIbatis.class);
	
	public Diccionario grabar(Diccionario o) throws DAOException {
		Diccionario dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Diccionario modificar(Diccionario o) throws DAOException {
		Diccionario dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Diccionario> getListaPorPk(Object o) throws DAOException {
		List<Diccionario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
