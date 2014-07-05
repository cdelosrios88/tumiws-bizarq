package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;

public interface ConciliacionDetalleDao extends TumiDao{
	public ConciliacionDetalle grabar(ConciliacionDetalle pDto) throws DAOException;
	public ConciliacionDetalle modificar(ConciliacionDetalle o) throws DAOException;
	public List<ConciliacionDetalle> getListaPorPk(Object o) throws DAOException;
}