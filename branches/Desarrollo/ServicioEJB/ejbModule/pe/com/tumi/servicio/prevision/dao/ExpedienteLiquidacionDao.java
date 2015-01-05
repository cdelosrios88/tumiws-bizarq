package pe.com.tumi.servicio.prevision.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;

public interface ExpedienteLiquidacionDao extends TumiDao{
	public ExpedienteLiquidacion grabar(ExpedienteLiquidacion o) throws DAOException;
	public ExpedienteLiquidacion modificar(ExpedienteLiquidacion o) throws DAOException;
	public List<ExpedienteLiquidacion> getListaPorPk(Object o) throws DAOException;
	public List<ExpedienteLiquidacion> getListaCompleta(Object o) throws DAOException;
	public List<ExpedienteLiquidacion> getListaCompletaFiltros(Object o) throws DAOException;
	public List<ExpedienteLiquidacion> getListaPorEmpresaYCuenta(Object o) throws DAOException;
	public List<ExpedienteLiquidacion> getListaBusqExpLiqFiltros(Object o) throws DAOException;
	public List<ExpedienteLiquidacion> getListaBusqAutLiqFiltros(Object o) throws DAOException;
	public List<RequisitoLiquidacionComp> getRequisitoGiroLiquidacion(Object o) throws DAOException;
	//Autor: jchavez / Tarea: Creación / Fecha: 09.12.2014
	public Integer getCorrespondePrevision(Object o) throws DAOException;
}
