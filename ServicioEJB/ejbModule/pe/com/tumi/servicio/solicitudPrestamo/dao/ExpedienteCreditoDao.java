package pe.com.tumi.servicio.solicitudPrestamo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.RequisitoCreditoComp;

public interface ExpedienteCreditoDao extends TumiDao{
	public ExpedienteCredito grabar(ExpedienteCredito o) throws DAOException;
	public ExpedienteCredito modificar(ExpedienteCredito o) throws DAOException;
	public List<ExpedienteCredito> getListaPorPk(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusquedaPorExpCredComp(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusquedaAutorizacionPorExpCredComp(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaPorCuenta(Object o) throws DAOException;
	public ExpedienteCredito grabarRefinanciamiento(ExpedienteCredito o) throws DAOException;
	public List<ExpedienteCredito> getListaBusquedaPorExpRefComp(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaPorExpediente(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusquedaPorExpActComp(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusqRefinanFiltros(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusqAutRefFiltros(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusqCreditoFiltros(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusqCreditosAutFiltros(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusqActividadFiltros(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusqActAutFiltros(Object o) throws DAOException;
	public List<ExpedienteCredito> getListaBusqCreditoEspecialFiltros(Object o) throws DAOException;
	//JCHAVEZ 31.12.2013
	public List<ExpedienteCredito> getRiesgoExpCredACancelar(Object o) throws DAOException;
	public List<ExpedienteCredito> getMaxExpedienteRefinan(Object o) throws DAOException;

	public List<RequisitoCreditoComp> getRequisitoGiroPrestamo(Object o) throws DAOException;
}
