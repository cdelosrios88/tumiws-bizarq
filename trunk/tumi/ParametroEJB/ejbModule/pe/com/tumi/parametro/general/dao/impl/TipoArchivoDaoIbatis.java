package pe.com.tumi.parametro.general.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.parametro.general.dao.TipoArchivoDao;
import pe.com.tumi.parametro.general.domain.TipoArchivo;

public class TipoArchivoDaoIbatis extends TumiDaoIbatis implements TipoArchivoDao{
	
	public TipoArchivo grabar(TipoArchivo o) throws DAOException {
		TipoArchivo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public TipoArchivo modificar(TipoArchivo o) throws DAOException {
		TipoArchivo dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<TipoArchivo> getListaPorPk(Object o) throws DAOException{
		List<TipoArchivo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}