package pe.com.tumi.tesoreria.banco.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.banco.dao.AccesoDao;
import pe.com.tumi.tesoreria.banco.dao.AccesoDetalleDao;
import pe.com.tumi.tesoreria.banco.dao.AccesoDetalleResDao;
import pe.com.tumi.tesoreria.banco.dao.BancofondoDao;
import pe.com.tumi.tesoreria.banco.domain.Acceso;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalle;
import pe.com.tumi.tesoreria.banco.domain.AccesoDetalleRes;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

public class AccesoDetalleResDaoIbatis extends TumiDaoIbatis implements AccesoDetalleResDao{

	public AccesoDetalleRes grabar(AccesoDetalleRes o) throws DAOException{
		AccesoDetalleRes dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public AccesoDetalleRes modificar(AccesoDetalleRes o) throws DAOException{
		AccesoDetalleRes dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<AccesoDetalleRes> getListaPorPk(Object o) throws DAOException{
		List<AccesoDetalleRes> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AccesoDetalleRes> getListaPorAccesoDetalle(Object o) throws DAOException{
		List<AccesoDetalleRes> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAccesoDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	
