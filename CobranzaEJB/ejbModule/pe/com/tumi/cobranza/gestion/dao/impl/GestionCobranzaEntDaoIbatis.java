package pe.com.tumi.cobranza.gestion.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.GestionCobranzaEntDao;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class GestionCobranzaEntDaoIbatis extends TumiDaoIbatis implements GestionCobranzaEntDao{
	
	protected  static Logger log = Logger.getLogger(GestionCobranzaEntDaoIbatis.class);
	
	public List<GestionCobranzaEnt> getListaGestionCobranzaEnt(Object o) throws DAOException{
		log.info("-----------------------Debugging GestionCobranzaEntDaoIbatis.getListaGestionCobranzaEnt-----------------------------");
		List<GestionCobranzaEnt> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaGestionCobranzaEnt", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<GestionCobranzaEnt> getGestionCobranzaEntPorPK(Object o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaEntDaoIbatis.getGestionCobranzaEntPorPK-----------------------------");
		List<GestionCobranzaEnt> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getGestionCobranzaEntPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public GestionCobranzaEnt grabar(GestionCobranzaEnt o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaEntDaoIbatis.grabar-----------------------------");
		GestionCobranzaEnt dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public GestionCobranzaEnt modificar(GestionCobranzaEnt o) throws DAOException {
		GestionCobranzaEnt dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
}
