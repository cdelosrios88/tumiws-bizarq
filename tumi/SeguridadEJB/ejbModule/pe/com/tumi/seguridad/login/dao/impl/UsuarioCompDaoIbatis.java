package pe.com.tumi.seguridad.login.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.login.dao.UsuarioCompDao;
import pe.com.tumi.seguridad.login.domain.composite.UsuarioComp;

public class UsuarioCompDaoIbatis extends TumiDaoIbatis implements UsuarioCompDao {

	protected  static Logger log = Logger.getLogger(UsuarioCompDaoIbatis.class);
	
	public List<UsuarioComp> getListaDeBusqueda(Object o) throws DAOException {
		List<UsuarioComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
