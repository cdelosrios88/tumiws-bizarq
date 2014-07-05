package pe.com.tumi.tesoreria.banco.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.tesoreria.banco.dao.BancofondoDao;
import pe.com.tumi.tesoreria.banco.domain.Bancofondo;

public class BancofondoDaoIbatis extends TumiDaoIbatis implements BancofondoDao{

	public Bancofondo grabar(Bancofondo o) throws DAOException{
		Bancofondo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Bancofondo modificar(Bancofondo o) throws DAOException{
		Bancofondo dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Bancofondo> getListaPorPk(Object o) throws DAOException{
		List<Bancofondo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Bancofondo> getListaPorBusqueda(Object o) throws DAOException{
		List<Bancofondo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Bancofondo> getListaPorTipoFondoFijoYMoneda(Object o) throws DAOException{
		List<Bancofondo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTFFYM", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Bancofondo> getListaPorEmpresaYPersona(Object o) throws DAOException{
		List<Bancofondo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpyPers", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}	
