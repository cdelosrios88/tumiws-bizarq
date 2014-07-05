package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.CierreDiarioArqueoDao;
import pe.com.tumi.tesoreria.egreso.domain.CierreDiarioArqueo;

public class CierreDiarioArqueoDaoIbatis extends TumiDaoIbatis implements CierreDiarioArqueoDao{

	public CierreDiarioArqueo grabar(CierreDiarioArqueo o) throws DAOException{
		CierreDiarioArqueo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public CierreDiarioArqueo modificar(CierreDiarioArqueo o) throws DAOException{
		CierreDiarioArqueo dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<CierreDiarioArqueo> getListaPorPk(Object o) throws DAOException{
		List<CierreDiarioArqueo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreDiarioArqueo> getListaPorBuscar(Object o) throws DAOException{
		List<CierreDiarioArqueo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreDiarioArqueo> getListaParaDiaAnterior(Object o) throws DAOException{
		List<CierreDiarioArqueo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaDiaAnterior", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<HashMap> getListaFechas(Object o) throws DAOException{
		List<HashMap> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaSaldoFechas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}