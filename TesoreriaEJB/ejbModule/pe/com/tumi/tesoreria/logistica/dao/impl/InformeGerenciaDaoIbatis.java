package pe.com.tumi.tesoreria.logistica.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.logistica.dao.InformeGerenciaDao;
import pe.com.tumi.tesoreria.logistica.domain.InformeGerencia;

public class InformeGerenciaDaoIbatis extends TumiDaoIbatis implements InformeGerenciaDao{

	public InformeGerencia grabar(InformeGerencia o) throws DAOException{
		InformeGerencia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public InformeGerencia modificar(InformeGerencia o) throws DAOException{
		InformeGerencia dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<InformeGerencia> getListaPorPk(Object o) throws DAOException{
		List<InformeGerencia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<InformeGerencia> getListaPorBuscar(Object o) throws DAOException{
		List<InformeGerencia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}