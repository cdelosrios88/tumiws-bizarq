package pe.com.tumi.contabilidad.cierre.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.dao.RatioDetalleDao;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class RatioDetalleDaoIbatis extends TumiDaoIbatis implements RatioDetalleDao{

	public RatioDetalle grabar(RatioDetalle o) throws DAOException {
		RatioDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public RatioDetalle modificar(RatioDetalle o) throws DAOException{
		RatioDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<RatioDetalle> getListaPorPk(Object o) throws DAOException{
		List<RatioDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<RatioDetalle> getListaPorRatio(Object o) throws DAOException{
		List<RatioDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorRatio", o);
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
	
	public List<RatioDetalle> getListaPorAnexoDetalle(Object o) throws DAOException{
		List<RatioDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAnexoDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
