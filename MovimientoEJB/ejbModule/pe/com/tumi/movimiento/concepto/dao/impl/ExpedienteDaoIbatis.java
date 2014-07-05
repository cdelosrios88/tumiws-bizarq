package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.ExpedienteDao;
import pe.com.tumi.movimiento.concepto.domain.Expediente;

public class ExpedienteDaoIbatis extends TumiDaoIbatis implements ExpedienteDao{
	
	public Expediente grabar(Expediente o) throws DAOException {
		Expediente dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Expediente modificar(Expediente o) throws DAOException {
		Expediente dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Expediente> getListaPorPK(Object o) throws DAOException{
		List<Expediente> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Expediente> getListaConSaldoPorEmpresaYcuenta(Object o) throws DAOException{
		List<Expediente> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConSaldoPorEmpresaYCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Expediente> getListaPorEmpresaYCta(Object o) throws DAOException{
		List<Expediente> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaYCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Expediente> getListaExpedienteConSaldoPorEmpresaCtaYTipoCred(Object o) throws DAOException{
		List<Expediente> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaExpedienteConSaldoPorEmpresaCtaYTipoCred", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Recueprta los expedientes segun empresa, cta y itemexpediete
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Expediente> getListaXEmpCtaExp(Object o) throws DAOException{
		List<Expediente> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaXEmpCtaExp", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	
}