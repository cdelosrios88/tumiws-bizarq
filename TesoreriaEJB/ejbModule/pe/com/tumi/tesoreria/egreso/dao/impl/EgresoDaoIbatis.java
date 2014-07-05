package pe.com.tumi.tesoreria.egreso.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.egreso.dao.EgresoDao;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;

public class EgresoDaoIbatis extends TumiDaoIbatis implements EgresoDao{

	public Egreso grabar(Egreso o) throws DAOException{
		Egreso dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Egreso modificar(Egreso o) throws DAOException{
		Egreso dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Egreso> getListaPorPk(Object o) throws DAOException{
		List<Egreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Egreso> getListaParaItem(Object o) throws DAOException{
		List<Egreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaItem", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Egreso> getBuscarTransferencia(Object o) throws DAOException{
		List<Egreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBuscarTransferencia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Egreso> getListaPorCFF(Object o) throws DAOException{
		List<Egreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCFF", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Egreso> getListaPorBuscar(Object o) throws DAOException{
		List<Egreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBuscar", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Egreso> getListaParaTelecredito(Object o) throws DAOException{
		List<Egreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaTelecredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Egreso> getListaPorLibroDiario(Object o) throws DAOException{
		List<Egreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorLibroDiario", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}