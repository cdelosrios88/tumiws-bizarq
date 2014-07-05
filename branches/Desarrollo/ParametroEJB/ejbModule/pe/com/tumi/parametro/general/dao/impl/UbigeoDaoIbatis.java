package pe.com.tumi.parametro.general.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.parametro.general.dao.UbigeoDao;
import pe.com.tumi.parametro.general.domain.Ubigeo;

public class UbigeoDaoIbatis extends TumiDaoIbatis implements UbigeoDao{
	
	public List<Ubigeo> getListaUbigeoDeDepartamento() throws DAOException{
		List<Ubigeo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDepartamento");
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Ubigeo> getListaUbigeoDeProvinciaPorIdUbigeo(Object pO) throws DAOException{
		List<Ubigeo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaProvinciaPorIdUbigeo", pO);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Ubigeo> getListaUbigeoDeDistritoPorIdUbigeo(Object pO) throws DAOException{
		List<Ubigeo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDistritoPorIdUbigeo", pO);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Ubigeo> getListaPorIdUbigeo(Object pO) throws DAOException{
		List<Ubigeo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdUbigeo", pO);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}