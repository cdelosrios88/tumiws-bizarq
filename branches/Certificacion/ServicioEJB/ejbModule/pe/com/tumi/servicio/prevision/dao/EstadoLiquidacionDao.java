package pe.com.tumi.servicio.prevision.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;

public interface EstadoLiquidacionDao extends TumiDao{
	public EstadoLiquidacion grabar(EstadoLiquidacion o) throws DAOException;
	public EstadoLiquidacion modificar(EstadoLiquidacion o) throws DAOException;
	public List<EstadoLiquidacion> getListaPorPk(Object o) throws DAOException;
	public List<EstadoLiquidacion> getListaPorExpediente(Object o) throws DAOException;
	public List<EstadoLiquidacion> getMaxEstadoliquidacionPorPkExpediente(Object o) throws DAOException;
	public List<EstadoLiquidacion> getMinEstadoliquidacionPorPkExpediente(Object o) throws DAOException;
}
