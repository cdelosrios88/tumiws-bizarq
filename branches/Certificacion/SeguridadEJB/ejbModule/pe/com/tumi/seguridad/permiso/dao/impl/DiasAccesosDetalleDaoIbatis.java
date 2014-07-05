package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.DiasAccesosDao;
import pe.com.tumi.seguridad.permiso.dao.DiasAccesosDetalleDao;
import pe.com.tumi.seguridad.permiso.dao.DocumentoDao;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalle;
import pe.com.tumi.seguridad.permiso.domain.Documento;

public class DiasAccesosDetalleDaoIbatis extends TumiDaoIbatis implements DiasAccesosDetalleDao {

	protected  static Logger log = Logger.getLogger(DiasAccesosDao.class);
	
	public DiasAccesosDetalle grabar(DiasAccesosDetalle o) throws DAOException {
		DiasAccesosDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public DiasAccesosDetalle modificar(DiasAccesosDetalle o) throws DAOException {
		DiasAccesosDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<DiasAccesosDetalle> getListaPorPk(Object o) throws DAOException {
		List<DiasAccesosDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public List<DiasAccesosDetalle> getListaPorCabecera(Object o) throws DAOException {
		List<DiasAccesosDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCabecera", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
