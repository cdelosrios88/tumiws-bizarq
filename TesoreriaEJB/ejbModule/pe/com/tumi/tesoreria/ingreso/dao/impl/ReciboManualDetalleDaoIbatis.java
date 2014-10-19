package pe.com.tumi.tesoreria.ingreso.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.ingreso.dao.ReciboManualDetalleDao;
import pe.com.tumi.tesoreria.ingreso.domain.ReciboManualDetalle;

public class ReciboManualDetalleDaoIbatis extends TumiDaoIbatis implements ReciboManualDetalleDao{

	public ReciboManualDetalle grabar(ReciboManualDetalle o) throws DAOException{
		ReciboManualDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public ReciboManualDetalle modificar(ReciboManualDetalle o) throws DAOException{
		ReciboManualDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<ReciboManualDetalle> getListaPorPk(Object o) throws DAOException{
		List<ReciboManualDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ReciboManualDetalle> getListaPorReciboManual(Object o) throws DAOException{
		List<ReciboManualDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorReciboManual", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ReciboManualDetalle> getListaPorIngreso(Object o) throws DAOException{
		List<ReciboManualDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIngreso", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public String existeNroReciboEnlazado(Object o) throws DAOException{
		String vResult = null;
		try{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".existeNroReciboEnlazado", o);
			m = (HashMap<String, Object>)o;
		    vResult = m.get("vRESULT").toString().trim();
			
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return vResult;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ReciboManualDetalle> getListaPorFiltros(Object o) throws DAOException{
		List<ReciboManualDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorFiltros", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}