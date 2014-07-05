package pe.com.tumi.parametro.auditoria.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.parametro.auditoria.dao.AuditoriaDao;
import pe.com.tumi.parametro.auditoria.dao.AuditoriaMotivoDao;
import pe.com.tumi.parametro.auditoria.domain.Auditoria;
import pe.com.tumi.parametro.auditoria.domain.AuditoriaMotivo;

public class AuditoriaMotivoDaoIbatis extends TumiDaoIbatis implements AuditoriaMotivoDao{

	public AuditoriaMotivo grabar(AuditoriaMotivo o) throws DAOException{
		AuditoriaMotivo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public AuditoriaMotivo modificar(AuditoriaMotivo o) throws DAOException{
		AuditoriaMotivo dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<AuditoriaMotivo> getListaPorPk(Object o) throws DAOException{
		List<AuditoriaMotivo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AuditoriaMotivo> getListaPorAuditoria(Object o) throws DAOException{
		List<AuditoriaMotivo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAuditoria", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
