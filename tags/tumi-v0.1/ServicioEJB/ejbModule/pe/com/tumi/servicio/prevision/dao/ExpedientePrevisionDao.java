package pe.com.tumi.servicio.prevision.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoPrevisionComp;

public interface ExpedientePrevisionDao extends TumiDao{
	public ExpedientePrevision grabar(ExpedientePrevision o) throws DAOException;
	public ExpedientePrevision modificar(ExpedientePrevision o) throws DAOException;
	public List<ExpedientePrevision> getListaPorPk(Object o) throws DAOException;
	public List<ExpedientePrevision> getListaPorCuenta(Object o) throws DAOException;
	public List<ExpedientePrevision> getListaExpedientePrevisionBusqueda() throws DAOException;
	//public List<ExpedientePrevision> getListaExpedientePrevisionBusqueda(Object o) throws DAOException;	
	public List<ExpedientePrevision> getListaBusqPrevisionFiltros(Object o) throws DAOException;
	public List<ExpedientePrevision> getListaBusqExpPrevFiltros(Object o) throws DAOException;
	public List<ExpedientePrevision> getListaBusqAutExpPrevFiltros(Object o) throws DAOException;
	public List<ExpedientePrevision> getUltimoEstadoExpPrev(Object o) throws DAOException;
	public List<RequisitoPrevisionComp> getRequisitoGiroPrevision(Object o) throws DAOException;
}
