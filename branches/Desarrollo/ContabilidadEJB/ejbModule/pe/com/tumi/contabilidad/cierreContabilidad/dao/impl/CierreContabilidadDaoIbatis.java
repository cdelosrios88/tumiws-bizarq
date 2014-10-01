package pe.com.tumi.contabilidad.cierreContabilidad.dao.impl;

import java.util.List;

import pe.com.tumi.contabilidad.cierreContabilidad.dao.CierreContabilidadDao;
import pe.com.tumi.contabilidad.cierreContabilidad.domain.CierreContabilidad;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CierreContabilidadDaoIbatis extends TumiDaoIbatis implements CierreContabilidadDao{
	
	public CierreContabilidad grabarCierreCon(CierreContabilidad o) throws DAOException{
		CierreContabilidad dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabarCierreCon", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CierreContabilidad modificarCierreCon(CierreContabilidad o) throws DAOException{
		CierreContabilidad dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificarCierreCon", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CierreContabilidad> getListaBuscarCierre(Object o) throws DAOException {
		List<CierreContabilidad> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaBuscarCierre", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CierreContabilidad> getListaCierre(Object o) throws DAOException {
		List<CierreContabilidad> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaCierre", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
