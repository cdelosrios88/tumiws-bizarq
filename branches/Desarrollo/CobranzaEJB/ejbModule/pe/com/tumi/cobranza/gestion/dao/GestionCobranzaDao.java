package pe.com.tumi.cobranza.gestion.dao;

import java.util.List;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface GestionCobranzaDao extends TumiDao {
	
	public List<GestionCobranza> getListaGestionCobranza(Object o) throws DAOException;
	public GestionCobranza grabar(GestionCobranza o) throws DAOException;
	public GestionCobranza modificar(GestionCobranza o) throws DAOException;
	public List<GestionCobranza> getGestionCobranzaPorPK(Object o) throws DAOException;
}
