package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.AnexoDetalleCuentaDao;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AnexoDetalleCuentaDaoIbatis extends TumiDaoIbatis implements AnexoDetalleCuentaDao{

	public AnexoDetalleCuenta grabar(AnexoDetalleCuenta o) throws DAOException {
		AnexoDetalleCuenta dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AnexoDetalleCuenta modificar(AnexoDetalleCuenta o) throws DAOException {
		AnexoDetalleCuenta dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}


	public List<AnexoDetalleCuenta> getListaPorPk(Object o) throws DAOException{
		List<AnexoDetalleCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public void eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().queryForList(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
	}
	
	public List<AnexoDetalleCuenta> getListaPorAnexoDetalle(Object o) throws DAOException{
		List<AnexoDetalleCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAnexoDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	//Agregado por cdelosrios, 16/09/2013
	public List<AnexoDetalleCuenta> getListaPorPlanCuenta(Object o) throws DAOException{
		List<AnexoDetalleCuenta> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPlanCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 16/09/2013
}
