package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.ComputadoraDao;
import pe.com.tumi.seguridad.permiso.domain.Computadora;

public class ComputadoraDaoIbatis extends TumiDaoIbatis implements ComputadoraDao {

	protected  static Logger log = Logger.getLogger(ComputadoraDaoIbatis.class);
	
	public Computadora grabar(Computadora o) throws DAOException {
		Computadora dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Computadora modificar(Computadora o) throws DAOException {
		Computadora dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Computadora> getListaPorPk(Object o) throws DAOException {
		List<Computadora> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	
	public List<Computadora> getListaBusqueda(Object o) throws DAOException {
		List<Computadora> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}
