package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.DocumentoDao;
import pe.com.tumi.seguridad.permiso.domain.Documento;

public class DocumentoDaoIbatis extends TumiDaoIbatis implements DocumentoDao {

	protected  static Logger log = Logger.getLogger(DocumentoDaoIbatis.class);
	
	public Documento grabar(Documento o) throws DAOException {
		Documento dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Documento modificar(Documento o) throws DAOException {
		Documento dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Documento> getListaPorPk(Object o) throws DAOException {
		List<Documento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
