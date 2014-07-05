package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.cobranza.planilla.dao.EfectuadoResumenDao;
import pe.com.tumi.cobranza.planilla.domain.EfectuadoResumen;
import pe.com.tumi.cobranza.planilla.domain.Envioresumen;

public class EfectuadoResumenDaoIbatis extends TumiDaoIbatis implements EfectuadoResumenDao{

	public EfectuadoResumen grabar(EfectuadoResumen o) throws DAOException{
		EfectuadoResumen dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EfectuadoResumen modificar(EfectuadoResumen o) throws DAOException{
		EfectuadoResumen dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<EfectuadoResumen> getListaPorPk(Object o) throws DAOException{
		List<EfectuadoResumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EfectuadoResumen> getListaFaltaCancelar(Object o) throws DAOException{
		List<EfectuadoResumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFaltaCancelar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EfectuadoResumen> getListaPorEntidadyPeriodo(Object o) throws DAOException{
		List<EfectuadoResumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEntidadyPeriodo", o);
		}catch(Exception e){
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EfectuadoResumen> getListaEfectuadoResumen(Object o) throws DAOException{
		List<EfectuadoResumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEfectuadoResumen", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	@Override
	public String getNumeroCuenta(Object o)
			throws DAOException {
		String escalar = null;
		try
		{
			HashMap<String, Object> m = null;
			getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getNumeroCuenta", o);
			m = (HashMap<String, Object>)o;
			escalar = (String)m.get("strEscalar");
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return escalar;
	}
	
	public List<EfectuadoResumen> getMaximoPeriodo(Object o) throws DAOException{
		List<EfectuadoResumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMaximoPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//jchavez 19.06.2014
	public List<EfectuadoResumen> getLstPendientesPorEnitdad(Object o) throws DAOException{
		List<EfectuadoResumen> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPendientesPorEnitdad", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}