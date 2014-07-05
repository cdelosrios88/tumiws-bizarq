package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.cobranza.planilla.dao.DescuentoIndebidoDao;
import pe.com.tumi.cobranza.planilla.domain.DescuentoIndebido;

public class DescuentoIndebidoDaoIbatis extends TumiDaoIbatis implements DescuentoIndebidoDao{

	public DescuentoIndebido grabar(DescuentoIndebido o) throws DAOException{
		DescuentoIndebido dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public DescuentoIndebido modificar(DescuentoIndebido o) throws DAOException{
		DescuentoIndebido dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<DescuentoIndebido> getListaPorEmpCptoEfeGnralyCuenta(Object o) throws DAOException{
		List<DescuentoIndebido> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpCptoEfeGnralyCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<DescuentoIndebido> getListPorEmpYCta(Object o) throws DAOException{
		List<DescuentoIndebido> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListPorEmpYCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	
