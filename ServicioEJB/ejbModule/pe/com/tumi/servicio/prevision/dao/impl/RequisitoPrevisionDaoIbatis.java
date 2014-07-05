package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.RequisitoPrevisionDao;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;

public class RequisitoPrevisionDaoIbatis extends TumiDaoIbatis implements RequisitoPrevisionDao{
	
	
	public RequisitoPrevision grabar(RequisitoPrevision o) throws DAOException{
		RequisitoPrevision dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public RequisitoPrevision modificar(RequisitoPrevision o) throws DAOException{
		RequisitoPrevision dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	
	public List<RequisitoPrevision> getListaPorPk(Object o) throws DAOException{
		List<RequisitoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<RequisitoPrevision> getListaPorExpediente(Object o) throws DAOException{
		List<RequisitoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<RequisitoPrevision> getListaPorPkExpedientePrevisionYRequisitoDetalle(Object o) throws DAOException{
		List<RequisitoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkExpedientePrevisionYRequisitoDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}		
}
