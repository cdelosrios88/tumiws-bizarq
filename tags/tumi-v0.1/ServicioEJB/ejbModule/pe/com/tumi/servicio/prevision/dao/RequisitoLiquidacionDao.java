package pe.com.tumi.servicio.prevision.dao;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;

public interface RequisitoLiquidacionDao extends TumiDao{
	public RequisitoLiquidacion grabar(RequisitoLiquidacion o) throws DAOException;
	public RequisitoLiquidacion modificar(RequisitoLiquidacion o) throws DAOException;
	public List<RequisitoLiquidacion> getListaPorPk(Object o) throws DAOException;
	public List<RequisitoLiquidacion> getListaPorExpediente(Object o) throws DAOException;
	//JCHAVEZ 08.02.2014
	public List<RequisitoLiquidacion> getListaPorPkExpedienteLiquidacionYRequisitoDetalle(Object o) throws DAOException;
}