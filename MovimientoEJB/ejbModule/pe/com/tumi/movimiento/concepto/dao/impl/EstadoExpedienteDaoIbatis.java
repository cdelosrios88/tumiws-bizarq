package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.EstadoExpedienteDao;
import pe.com.tumi.movimiento.concepto.dao.ExpedienteDao;
import pe.com.tumi.movimiento.concepto.domain.EstadoExpediente;
import pe.com.tumi.movimiento.concepto.domain.Expediente;
import pe.com.tumi.servicio.solicitudPrestamo.domain.EstadoCredito;

public class EstadoExpedienteDaoIbatis extends TumiDaoIbatis implements EstadoExpedienteDao {

	public EstadoExpediente grabar(EstadoExpediente o) throws DAOException {
		EstadoExpediente dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EstadoExpediente modificar(EstadoExpediente o) throws DAOException {
		EstadoExpediente dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<EstadoExpediente> getListaPorPK(Object o) throws DAOException{
		List<EstadoExpediente> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoExpediente> getListaPorExpediente(Object o) throws DAOException{
		List<EstadoExpediente> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recupera el ultimo estado del expediente de movimiento.
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<EstadoExpediente> getMaxEstadoExpPorPkExpediente(Object o) throws DAOException{
		List<EstadoExpediente> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaxEstadoExpPorPkExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}
