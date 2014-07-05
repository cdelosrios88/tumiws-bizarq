package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacion;

public interface AutorizaVerificaLiquidacionDao extends TumiDao {
	public AutorizaVerificaLiquidacion grabar(AutorizaVerificaLiquidacion o) throws DAOException;
	public AutorizaVerificaLiquidacion modificar(AutorizaVerificaLiquidacion o) throws DAOException;
	public List<AutorizaVerificaLiquidacion> getListaPorPk(Object o) throws DAOException;
	public List<AutorizaVerificaLiquidacion> getListaPorExpedienteLiquidacion(Object o) throws DAOException;
	
}
