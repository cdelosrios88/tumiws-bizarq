package pe.com.tumi.reporte.operativo.credito.asociativo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.ServicioDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Servicio;

public class ServicioDaoIbatis extends TumiDaoIbatis implements ServicioDao{

	public List<Servicio> getListaCantidadProyectadas(Object o) throws DAOException {
		List<Servicio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCantidadProyectadas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Servicio> getListaCantidadCaptacionesEjecuadas(Object o) throws DAOException {
		List<Servicio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCantCaptacionesEjec", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Servicio> getCaptacionesEjecutadasDetalle(Object o) throws DAOException {
		List<Servicio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCaptacionesEjecutadasDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Servicio> getListaSaldoServicios(Object o) throws DAOException {
		List<Servicio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getSaldoServicios", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Servicio> getListaPrimerDscto(Object o) throws DAOException {
		List<Servicio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPrimerDscto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}