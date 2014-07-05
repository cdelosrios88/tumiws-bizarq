package pe.com.tumi.parametro.general.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.parametro.general.dao.ArchivoDao;
import pe.com.tumi.parametro.general.domain.Archivo;

public class ArchivoDaoIbatis extends TumiDaoIbatis implements ArchivoDao{
	
	public Archivo grabar(Archivo o) throws DAOException {
		Archivo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Archivo grabarVersion(Archivo o) throws DAOException {
		Archivo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarVersion", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Archivo modificar(Archivo o) throws DAOException {
		Archivo dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Archivo> getListaPorPK(Object o) throws DAOException{
		List<Archivo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Archivo> getListaVersionFinPorTipoYItem(Object o) throws DAOException{
		List<Archivo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaVersionFinPorTipoYItem", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}