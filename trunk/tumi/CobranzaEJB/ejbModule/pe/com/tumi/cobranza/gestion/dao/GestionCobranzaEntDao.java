package pe.com.tumi.cobranza.gestion.dao;

import java.util.List;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaEnt;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface GestionCobranzaEntDao extends TumiDao {
	
	public List<GestionCobranzaEnt> getListaGestionCobranzaEnt(Object o) throws DAOException;
	public GestionCobranzaEnt grabar(GestionCobranzaEnt o) throws DAOException;
	public GestionCobranzaEnt modificar(GestionCobranzaEnt o) throws DAOException;
	public List<GestionCobranzaEnt> getGestionCobranzaEntPorPK(Object o) throws DAOException;
}
