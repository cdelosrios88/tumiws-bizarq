package pe.com.tumi.cobranza.gestion.dao;

import java.util.Date;
import java.util.List;

import pe.com.tumi.cobranza.gestion.domain.GestionCobranza;
import pe.com.tumi.cobranza.gestion.domain.GestionCobranzaCierre;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface GestionCobranzaCierreDao extends TumiDao {
	
	public GestionCobranzaCierre grabar(GestionCobranzaCierre o) throws DAOException;
	public Date obtUltimaFechaGestion(GestionCobranzaCierre o) throws DAOException 	;

}
