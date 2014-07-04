package pe.com.tumi.riesgo.cartera.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.cartera.dao.ProciclicoDao;
import pe.com.tumi.riesgo.cartera.domain.Prociclico;

public class ProciclicoDaoIbatis extends TumiDaoIbatis implements ProciclicoDao{
	
	public Prociclico grabar(Prociclico o) throws DAOException {
		Prociclico dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Prociclico modificar(Prociclico o) throws DAOException {
		Prociclico dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Prociclico> getListaPorPk(Object o) throws DAOException{
		List<Prociclico> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Prociclico> getListaPorEspecificacion(Object o) throws DAOException{
		List<Prociclico> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEspecificacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}