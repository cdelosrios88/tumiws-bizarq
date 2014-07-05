package pe.com.tumi.contabilidad.core.dao.impl;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.contabilidad.core.dao.ModeloDetalleDao;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalle;
import pe.com.tumi.contabilidad.core.domain.ModeloDetalleId;

public class ModeloDetalleDaoIbatis extends TumiDaoIbatis implements ModeloDetalleDao{

	public ModeloDetalle grabar(ModeloDetalle o) throws DAOException{
		ModeloDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public ModeloDetalle modificar(ModeloDetalle o) throws DAOException{
		ModeloDetalle dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<ModeloDetalle> getListaPorPk(Object o) throws DAOException{
		List<ModeloDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ModeloDetalle> getListaPorModeloId(Object o) throws DAOException{
		List<ModeloDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorModeloId", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
	
	public ModeloDetalle eliminar(ModeloDetalleId o) throws DAOException{
		ModeloDetalle dto = null;
		try{
			dto = new ModeloDetalle();
			dto.setId(o);
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<ModeloDetalle> getListaDebeOfCobranza(Object o) throws DAOException
	{
		List<ModeloDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace()+".getListaDebeOfCobranza",o);
		}catch(Exception e)
		{
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIO10(Object o) throws DAOException
	{
		List<ModeloDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace()+".getListaDebeOfCobranzaUSUARIO10",o);
		}catch(Exception e)
		{
			throw new DAOException (e);
		}
		return lista;
	}

	public List<ModeloDetalle> getListaDebeOfCobranzaUSUARIONO10(Object o) throws DAOException

	{
		List<ModeloDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace()+".getListaDebeOfCobranzaUSUARIONO10",o);
		}catch(Exception e)
		{
			throw new DAOException (e);
		}
		return lista;
	}
	

}	
