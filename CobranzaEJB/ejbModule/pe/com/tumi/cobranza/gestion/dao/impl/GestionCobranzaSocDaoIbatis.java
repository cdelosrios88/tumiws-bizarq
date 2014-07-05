package pe.com.tumi.cobranza.gestion.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.GestionCobranzaSocDao;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaSoc;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class GestionCobranzaSocDaoIbatis extends TumiDaoIbatis implements GestionCobranzaSocDao{
	
	protected  static Logger log = Logger.getLogger(GestionCobranzaSocDaoIbatis.class);
	
	public List<GestionCobranzaSoc> getListaGestionCobranzaSoc(Object o) throws DAOException{
		log.info("-----------------------Debugging GestionCobranzaSocDaoIbatis.getListaGestionCobranzaSoc-----------------------------");
		List<GestionCobranzaSoc> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaGestionCobranzaSoc", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<GestionCobranzaSoc> getGestionCobranzaSocPorPK(Object o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaSocDaoIbatis.getGestionCobranzaSocPorPK-----------------------------");
		List<GestionCobranzaSoc> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getGestionCobranzaSocPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public GestionCobranzaSoc grabar(GestionCobranzaSoc o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaSocDaoIbatis.grabar-----------------------------");
		GestionCobranzaSoc dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public GestionCobranzaSoc modificar(GestionCobranzaSoc o) throws DAOException {
		GestionCobranzaSoc dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 04.10.2013
	 * Recupera Gestion Cobranza Soc por Cuenta Id 
	 */
	public List<GestionCobranzaSoc> getListaPorCuentaPkYPeriodo(Object o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaSocDaoIbatis.getGestionCobranzaSocPorPK-----------------------------");
		List<GestionCobranzaSoc> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuentaPkYPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}
