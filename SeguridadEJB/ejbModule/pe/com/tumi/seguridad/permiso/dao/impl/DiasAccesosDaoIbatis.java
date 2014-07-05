package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.DiasAccesosDao;
import pe.com.tumi.seguridad.permiso.dao.DocumentoDao;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.Documento;

public class DiasAccesosDaoIbatis extends TumiDaoIbatis implements DiasAccesosDao {

	protected  static Logger log = Logger.getLogger(DiasAccesosDao.class);
	
	public DiasAccesos grabar(DiasAccesos o) throws DAOException {
		DiasAccesos dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public DiasAccesos modificar(DiasAccesos o) throws DAOException {
		DiasAccesos dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<DiasAccesos> getListaPorPk(Object o) throws DAOException {
		List<DiasAccesos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DiasAccesos> getListaPorTipoSucursalYEstado(Object o) throws DAOException {
		List<DiasAccesos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoSucursalYEstado", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DiasAccesos> getAccesoPorEmpresaYSucursal(Object o) throws DAOException {
		List<DiasAccesos> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".validarAccesoPorEmpresaYSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
