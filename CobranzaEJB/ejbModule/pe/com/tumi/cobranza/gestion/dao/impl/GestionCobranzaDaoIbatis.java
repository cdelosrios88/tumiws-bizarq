package pe.com.tumi.cobranza.gestion.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;


import pe.com.tumi.cobranza.gestion.dao.GestionCobranzaDao;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class GestionCobranzaDaoIbatis extends TumiDaoIbatis implements GestionCobranzaDao{
	
	protected  static Logger log = Logger.getLogger(GestionCobranzaDaoIbatis.class);
	
	public List<GestionCobranza> getListaGestionCobranza(Object o) throws DAOException{
		log.info("-----------------------Debugging GestionCobranzaDaoIbatis.getListaGestionCobranza-----------------------------");
		List<GestionCobranza> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaGestionCobranza", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<GestionCobranza> getGestionCobranzaPorPK(Object o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaDaoIbatis.getGestionCobranzaPorPK-----------------------------");
		List<GestionCobranza> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getGestionCobranzaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public GestionCobranza grabar(GestionCobranza o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaDaoIbatis.grabar-----------------------------");
		GestionCobranza dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public GestionCobranza modificar(GestionCobranza o) throws DAOException {
		GestionCobranza dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
}
