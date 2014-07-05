package pe.com.tumi.parametro.auditoria.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import  pe.com.tumi.parametro.auditoria.dao.AuditoriaDao;
import  pe.com.tumi.parametro.auditoria.domain.Auditoria;

public class AuditoriaDaoIbatis extends TumiDaoIbatis implements AuditoriaDao{

	public Auditoria grabar(Auditoria o) throws DAOException{
		Auditoria dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Auditoria modificar(Auditoria o) throws DAOException{
		Auditoria dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Auditoria> getListaPorPk(Object o) throws DAOException{
		List<Auditoria> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Auditoria> getListaDeMaxPkPorTabColLlave(Object o) throws DAOException{
		List<Auditoria> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeMaxPkPorTabColLlave", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	
