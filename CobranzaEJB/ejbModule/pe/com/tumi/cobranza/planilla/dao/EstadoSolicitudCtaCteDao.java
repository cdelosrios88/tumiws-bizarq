package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;

public interface EstadoSolicitudCtaCteDao extends TumiDao{
	public EstadoSolicitudCtaCte grabar(EstadoSolicitudCtaCte pDto) throws DAOException;
	public EstadoSolicitudCtaCte modificar(EstadoSolicitudCtaCte o) throws DAOException;
	public List<EstadoSolicitudCtaCte> getListaPorPk(Object o) throws DAOException;
	public List<EstadoSolicitudCtaCte> getListaPorSolicitudCtacte(Object o) throws DAOException;
}	
