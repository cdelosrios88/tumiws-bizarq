package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.SolicitudCambioDao;
import pe.com.tumi.seguridad.permiso.domain.SolicitudCambio;

public class SolicitudCambioDaoIbatis extends TumiDaoIbatis implements SolicitudCambioDao {

	protected  static Logger log = Logger.getLogger(SolicitudCambioDaoIbatis.class);
	
	public SolicitudCambio grabar(SolicitudCambio o) throws DAOException {
		SolicitudCambio dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public SolicitudCambio modificar(SolicitudCambio o) throws DAOException {
		SolicitudCambio dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<SolicitudCambio> getListaPorPk(Object o) throws DAOException {
		List<SolicitudCambio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
