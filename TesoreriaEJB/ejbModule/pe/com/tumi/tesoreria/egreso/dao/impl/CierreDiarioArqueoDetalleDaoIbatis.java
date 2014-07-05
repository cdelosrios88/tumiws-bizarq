package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.CierreDiarioArqueoDetalleDao;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueoDetalle;

public class CierreDiarioArqueoDetalleDaoIbatis extends TumiDaoIbatis implements CierreDiarioArqueoDetalleDao{

	public CierreDiarioArqueoDetalle grabar(CierreDiarioArqueoDetalle o) throws DAOException{
		CierreDiarioArqueoDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public CierreDiarioArqueoDetalle modificar(CierreDiarioArqueoDetalle o) throws DAOException{
		CierreDiarioArqueoDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<CierreDiarioArqueoDetalle> getListaPorPk(Object o) throws DAOException{
		List<CierreDiarioArqueoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreDiarioArqueoDetalle> getListaPorCierreDiarioArqueo(Object o) throws DAOException{
		List<CierreDiarioArqueoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCierreDiarioArqueo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	
