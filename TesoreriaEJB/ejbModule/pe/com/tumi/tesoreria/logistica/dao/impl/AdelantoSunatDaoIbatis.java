package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.AdelantoSunatDao;
import pe.com.tumi.tesoreria.logistica.domain.AdelantoSunat;

public class AdelantoSunatDaoIbatis extends TumiDaoIbatis implements AdelantoSunatDao{

	public AdelantoSunat grabar(AdelantoSunat o) throws DAOException{
		AdelantoSunat dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public AdelantoSunat modificar(AdelantoSunat o) throws DAOException{
		AdelantoSunat dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<AdelantoSunat> getListaPorPk(Object o) throws DAOException{
		List<AdelantoSunat> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AdelantoSunat> getListaPorOrdenCompraDocumento(Object o) throws DAOException{
		List<AdelantoSunat> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorOrdenCompraDocumento", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	//Agregado por cdelosrios, 13/11/2013
	public List<AdelantoSunat> getListaPorDocumentoSunat(Object o) throws DAOException{
		List<AdelantoSunat> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorDocumentoSunat", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 13/11/2013
}