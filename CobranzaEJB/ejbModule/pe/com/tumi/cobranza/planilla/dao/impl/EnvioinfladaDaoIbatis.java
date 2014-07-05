package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

import pe.com.tumi.cobranza.planilla.dao.EnvioinfladaDao;
import pe.com.tumi.cobranza.planilla.domain.Envioinflada;

public class EnvioinfladaDaoIbatis extends TumiDaoIbatis implements EnvioinfladaDao{

	public Envioinflada grabar(Envioinflada o) throws DAOException{
		Envioinflada dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public Envioinflada modificar(Envioinflada o) throws DAOException{
		Envioinflada dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<Envioinflada> getListaPorPk(Object o) throws DAOException{
		List<Envioinflada> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Envioinflada> getListaPorEnvioMonto(Object o) throws DAOException{
		List<Envioinflada> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEnvioMonto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
}	
