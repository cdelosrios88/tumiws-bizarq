package pe.com.tumi.servicio.prevision.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;

public interface EstadoPrevisionDao extends TumiDao{
	public EstadoPrevision grabar(EstadoPrevision o) throws DAOException;
	public EstadoPrevision modificar(EstadoPrevision o) throws DAOException;
	public List<EstadoPrevision> getListaPorPk(Object o) throws DAOException;
	public List<EstadoPrevision> getListaPorExpediente(Object o) throws DAOException;
	public List<EstadoPrevision> getMaxEstadoPrevisionPorPokExpediente(Object o) throws DAOException;

}
