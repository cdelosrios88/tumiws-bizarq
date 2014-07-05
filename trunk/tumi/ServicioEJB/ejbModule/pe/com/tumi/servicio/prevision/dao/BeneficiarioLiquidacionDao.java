package pe.com.tumi.servicio.prevision.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioLiquidacion;

public interface BeneficiarioLiquidacionDao extends TumiDao{
	public BeneficiarioLiquidacion grabar(BeneficiarioLiquidacion o) throws DAOException;
	public BeneficiarioLiquidacion modificar(BeneficiarioLiquidacion o) throws DAOException;
	public List<BeneficiarioLiquidacion> getListaPorPk(Object o) throws DAOException;
	public List<BeneficiarioLiquidacion> getListaPorExpedienteLiquidacionDetalle(Object o) throws DAOException;
	public List<BeneficiarioLiquidacion> getListaPorEgreso(Object o) throws DAOException;
	public List<BeneficiarioLiquidacion> getListaPorExpedienteLiquidacion(Object o) throws DAOException;
	public void deletePorExpediente(Object o) throws DAOException;
}
