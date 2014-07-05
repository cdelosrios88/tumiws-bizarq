package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.cobranza.planilla.dao.CobroPlanillasDao;
import pe.com.tumi.cobranza.planilla.domain.CobroPlanillas;

public class CobroPlanillasDaoIbatis extends TumiDaoIbatis implements CobroPlanillasDao{

	public CobroPlanillas grabar(CobroPlanillas o) throws DAOException{
		CobroPlanillas dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CobroPlanillas modificar(CobroPlanillas o) throws DAOException{
		CobroPlanillas dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<CobroPlanillas> getListaPorPk(Object o) throws DAOException{
		List<CobroPlanillas> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CobroPlanillas> getListaPorEfectuadoResumen(Object o) throws DAOException {
		List<CobroPlanillas> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEfectuadoResumen",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
}