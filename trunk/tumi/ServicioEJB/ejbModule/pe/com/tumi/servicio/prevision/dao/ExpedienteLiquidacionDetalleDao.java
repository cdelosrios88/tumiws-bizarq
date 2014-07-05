package pe.com.tumi.servicio.prevision.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionDetalle;

public interface ExpedienteLiquidacionDetalleDao extends TumiDao{
	public ExpedienteLiquidacionDetalle grabar(ExpedienteLiquidacionDetalle o) throws DAOException;
	public ExpedienteLiquidacionDetalle modificar(ExpedienteLiquidacionDetalle o) throws DAOException;
	public List<ExpedienteLiquidacionDetalle> getListaPorPk(Object o) throws DAOException;
	public List<ExpedienteLiquidacionDetalle> getListaPorCuenta(Object o) throws DAOException;
	public List<ExpedienteLiquidacionDetalle> getListaPorExpediente(Object o) throws DAOException;
}
