package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.ExpedienteActividadDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteActividad;

public class ExpedienteActividadDaoIbatis extends TumiDaoIbatis implements ExpedienteActividadDao {

	public ExpedienteActividad grabar(ExpedienteActividad o) throws DAOException {
		ExpedienteActividad dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			System.out.println("Error en ExpedienteActividadDaoIbatis grabar ---> "+e);
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ExpedienteActividad modificar(ExpedienteActividad o) throws DAOException {
		ExpedienteActividad dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			System.out.println("Error en ExpedienteActividadDaoIbatis modificar ---> "+e);
			throw new DAOException(e);
		}
		return dto;
	}

	public List<ExpedienteActividad> getListaPorExpedienteCredito(Object o) throws DAOException{
		List<ExpedienteActividad> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteCredito", o);
		}catch(Exception e) {
			System.out.println("Error en ExpedienteActividadDaoIbatis getListaPorExpedienteCredito ---> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
}
