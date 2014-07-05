package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;

public interface AutorizaLiquidacionDao extends TumiDao {
	public AutorizaLiquidacion grabar(AutorizaLiquidacion o) throws DAOException;
	public AutorizaLiquidacion modificar(AutorizaLiquidacion o) throws DAOException;
	public List<AutorizaLiquidacion> getListaPorPk(Object o) throws DAOException;
	public List<AutorizaLiquidacion> getListaPorExpedienteLiquidacion(Object o) throws DAOException;
}
