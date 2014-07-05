package pe.com.tumi.persona.empresa.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.empresa.dao.ActividadEconomicaDao;
import pe.com.tumi.persona.empresa.dao.TipoComprobanteDao;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.TipoComprobante;

public class TipoComprobanteDaoIbatis extends TumiDaoIbatis implements TipoComprobanteDao{
	
	public TipoComprobante grabar(TipoComprobante o) throws DAOException {
		TipoComprobante dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public TipoComprobante modificar(TipoComprobante o) throws DAOException {
		TipoComprobante dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<TipoComprobante> getListaTipoComprobantePorPK(Object o) throws DAOException{
		List<TipoComprobante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<TipoComprobante> getListaTipoComprobantePorIdPersona(Object o) throws DAOException{
		List<TipoComprobante> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}