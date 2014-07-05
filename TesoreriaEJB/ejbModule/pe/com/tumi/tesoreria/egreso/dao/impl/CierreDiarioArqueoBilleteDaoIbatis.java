package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.CierreDiarioArqueoBilleteDao;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoBillete;

public class CierreDiarioArqueoBilleteDaoIbatis extends TumiDaoIbatis implements CierreDiarioArqueoBilleteDao{

	public CierreDiarioArqueoBillete grabar(CierreDiarioArqueoBillete o) throws DAOException{
		CierreDiarioArqueoBillete dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public CierreDiarioArqueoBillete modificar(CierreDiarioArqueoBillete o) throws DAOException{
		CierreDiarioArqueoBillete dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<CierreDiarioArqueoBillete> getListaPorPk(Object o) throws DAOException{
		List<CierreDiarioArqueoBillete> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreDiarioArqueoBillete> getListaPorCierreDiarioArqueo(Object o) throws DAOException{
		List<CierreDiarioArqueoBillete> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCierreDiarioArqueo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}