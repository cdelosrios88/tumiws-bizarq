package pe.com.tumi.riesgo.archivo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.archivo.dao.ConfDetalleDao;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalle;

public class ConfDetalleDaoIbatis extends TumiDaoIbatis implements ConfDetalleDao{
	
	public ConfDetalle grabar(ConfDetalle o) throws DAOException {
		ConfDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConfDetalle modificar(ConfDetalle o) throws DAOException {
		ConfDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConfDetalle> getListaPorPk(Object o) throws DAOException{
		List<ConfDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConfDetalle> getListaPorIntItemConfiguracion(Object o) throws DAOException{
		List<ConfDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIntItemConfiguracion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}