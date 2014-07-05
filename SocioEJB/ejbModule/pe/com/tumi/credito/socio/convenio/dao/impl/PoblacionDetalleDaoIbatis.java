package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PoblacionDetalleDao;
import pe.com.tumi.credito.socio.convenio.domain.PoblacionDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PoblacionDetalleDaoIbatis extends TumiDaoIbatis implements PoblacionDetalleDao{
	
	public PoblacionDetalle grabar(PoblacionDetalle o) throws DAOException {
		PoblacionDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PoblacionDetalle modificar(PoblacionDetalle o) throws DAOException {
		PoblacionDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<PoblacionDetalle> getListaPoblacionDetallePorPK(Object o) throws DAOException{
		List<PoblacionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PoblacionDetalle> getListaPoblacionDetallePorPKPoblacion(Object o) throws DAOException{
		List<PoblacionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkPoblacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}