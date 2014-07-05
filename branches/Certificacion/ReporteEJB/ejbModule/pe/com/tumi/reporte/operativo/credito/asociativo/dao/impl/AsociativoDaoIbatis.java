package pe.com.tumi.reporte.operativo.credito.asociativo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.AsociativoDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Asociativo;

public class AsociativoDaoIbatis extends TumiDaoIbatis implements AsociativoDao{

	public List<Asociativo> getListaCantidadProyectadas(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCantidadProyectadas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadCaptacionesEjecuadas(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCantCaptacionesEjec", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Asociativo> getCaptacionesEjecutadasDetalle(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCaptacionesEjecutadasDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadCaptacionesAltas(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCantidadCaptacionesAltas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadRenunciasEjecuadas(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCantRenunciasEjec", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Asociativo> getRenunciasEjecutadasDetalle(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getRenunciasEjecutadasDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadRenunciasBajas(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCantidadRenunciasBajas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaConveniosPorSucursal(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConveniosPorSucursal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	//Agregado por cdelosrios, 28/11/2013
	public List<Asociativo> getListaExpedienteProvision(Object o) throws DAOException {
		List<Asociativo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaExpedientePrevision", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 28/11/2013
}
