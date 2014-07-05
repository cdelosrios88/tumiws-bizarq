package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface RatioDetalleDao extends TumiDao{

	public RatioDetalle grabar(RatioDetalle o) throws DAOException;
	public RatioDetalle modificar(RatioDetalle o) throws DAOException;
	public List<RatioDetalle> getListaPorPk(Object o) throws DAOException;
	public List<RatioDetalle> getListaPorRatio(Object o) throws DAOException;
	public List<RatioDetalle> getListaPorAnexoDetalle(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
}
