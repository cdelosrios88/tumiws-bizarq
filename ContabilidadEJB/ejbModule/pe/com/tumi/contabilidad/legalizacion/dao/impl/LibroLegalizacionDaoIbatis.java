package pe.com.tumi.contabilidad.legalizacion.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.dao.LibroLegalizacionDao;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class LibroLegalizacionDaoIbatis extends TumiDaoIbatis implements LibroLegalizacionDao{

	public LibroLegalizacion grabar(LibroLegalizacion o) throws DAOException {
		LibroLegalizacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public LibroLegalizacion modificar(LibroLegalizacion o) throws DAOException{
		LibroLegalizacion dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<LibroLegalizacion> getListaPorPk(Object o) throws DAOException{
		List<LibroLegalizacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<LibroLegalizacion> getListaPorIdPersona(Object o) throws DAOException{
		List<LibroLegalizacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}
