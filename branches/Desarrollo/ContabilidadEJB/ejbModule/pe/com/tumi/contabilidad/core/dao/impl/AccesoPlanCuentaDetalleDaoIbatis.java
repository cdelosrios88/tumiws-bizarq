package pe.com.tumi.contabilidad.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.contabilidad.core.dao.AccesoPlanCuentaDetalleDao;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuentaDetalle;

public class AccesoPlanCuentaDetalleDaoIbatis extends TumiDaoIbatis implements AccesoPlanCuentaDetalleDao{

	public AccesoPlanCuentaDetalle grabar(AccesoPlanCuentaDetalle o) throws DAOException{
		AccesoPlanCuentaDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public AccesoPlanCuentaDetalle modificar(AccesoPlanCuentaDetalle o) throws DAOException{
		AccesoPlanCuentaDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<AccesoPlanCuentaDetalle> getListaPorPk(Object o) throws DAOException{
		List<AccesoPlanCuentaDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AccesoPlanCuentaDetalle> getListaPorAccesoPlanCuenta(Object o) throws DAOException{
		List<AccesoPlanCuentaDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAccesoPlanCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
}