package pe.com.tumi.reporte.operativo.credito.asociativo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.PlanillaDescuento;

public interface PlanillaDescuentoDao extends TumiDao {
	public List<PlanillaDescuento> getListaPlanillaDescuento(Object o) throws DAOException;
	public List<PlanillaDescuento> getListaPendienteCobro(Object o) throws DAOException;
	public List<PlanillaDescuento> getListaMorosidadPlanilla(Object o) throws DAOException;
	public List<PlanillaDescuento> getListaSocioDiferencia(Object o) throws DAOException;
	public List<PlanillaDescuento> getListaEntidad(Object o) throws DAOException;
}
