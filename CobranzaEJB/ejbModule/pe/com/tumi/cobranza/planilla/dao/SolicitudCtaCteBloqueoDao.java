package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteBloqueo;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;

public interface SolicitudCtaCteBloqueoDao extends TumiDao{
	public SolicitudCtaCteBloqueo grabar(SolicitudCtaCteBloqueo o) throws DAOException;
	public List<SolicitudCtaCteBloqueo> getListaPorTipoSol(Object o) throws DAOException ;
		
}
