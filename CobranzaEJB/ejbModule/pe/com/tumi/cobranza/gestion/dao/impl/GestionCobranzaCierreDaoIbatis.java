package pe.com.tumi.cobranza.gestion.dao.impl;

import java.util.Date;

import org.apache.log4j.Logger;

import pe.com.tumi.cobranza.gestion.dao.GestionCobranzaCierreDao;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaCierre;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class GestionCobranzaCierreDaoIbatis extends TumiDaoIbatis implements GestionCobranzaCierreDao{
	
	protected  static Logger log = Logger.getLogger(GestionCobranzaCierreDaoIbatis.class);

	public GestionCobranzaCierre grabar(GestionCobranzaCierre o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaDaoIbatis.grabar-----------------------------");
		GestionCobranzaCierre dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Date obtUltimaFechaGestion(GestionCobranzaCierre o) throws DAOException {
		log.info("-----------------------Debugging GestionCobranzaDaoIbatis.obtUltimaFechaGestion-----------------------------");
		Date dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".obtUltimaFechaGestion", o);
			dto = o.getDtUltFechaCierre();
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	
}
