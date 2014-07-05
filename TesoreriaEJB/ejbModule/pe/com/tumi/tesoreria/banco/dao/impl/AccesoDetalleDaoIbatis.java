package pe.com.tumi.tesoreria.banco.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.banco.dao.AccesoDao;
import pe.com.tumi.tesoreria.banco.dao.AccesoDetalleDao;
import pe.com.tumi.tesoreria.banco.dao.BancofondoDao;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

public class AccesoDetalleDaoIbatis extends TumiDaoIbatis implements AccesoDetalleDao{

	public AccesoDetalle grabar(AccesoDetalle o) throws DAOException{
		AccesoDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public AccesoDetalle modificar(AccesoDetalle o) throws DAOException{
		AccesoDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<AccesoDetalle> getListaPorPk(Object o) throws DAOException{
		List<AccesoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AccesoDetalle> getListaPorAcceso(Object o) throws DAOException{
		List<AccesoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAcceso", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}	
