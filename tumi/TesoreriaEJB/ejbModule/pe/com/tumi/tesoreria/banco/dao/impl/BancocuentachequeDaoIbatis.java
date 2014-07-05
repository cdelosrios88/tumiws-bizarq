package pe.com.tumi.tesoreria.banco.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.banco.dao.BancocuentachequeDao;
import pe.com.tumi.tesoreria.banco.domain.Bancocuentacheque;

public class BancocuentachequeDaoIbatis extends TumiDaoIbatis implements BancocuentachequeDao{

	public Bancocuentacheque grabar(Bancocuentacheque o) throws DAOException{
		Bancocuentacheque dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Bancocuentacheque modificar(Bancocuentacheque o) throws DAOException{
		Bancocuentacheque dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Bancocuentacheque> getListaPorPk(Object o) throws DAOException{
		List<Bancocuentacheque> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Bancocuentacheque> getListaPorBancoCuenta(Object o) throws DAOException{
		List<Bancocuentacheque> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBancoCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	
