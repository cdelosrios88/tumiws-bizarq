package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.RequisicionDetalleDao;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionDetalle;

public class RequisicionDetalleDaoIbatis extends TumiDaoIbatis implements RequisicionDetalleDao{

	public RequisicionDetalle grabar(RequisicionDetalle o) throws DAOException{
		RequisicionDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public RequisicionDetalle modificar(RequisicionDetalle o) throws DAOException{
		RequisicionDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<RequisicionDetalle> getListaPorPk(Object o) throws DAOException{
		List<RequisicionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<RequisicionDetalle> getListaPorRequisicion(Object o) throws DAOException{
		List<RequisicionDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorRequisicion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}