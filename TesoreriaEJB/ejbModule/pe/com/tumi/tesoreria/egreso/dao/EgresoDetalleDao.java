package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.EgresoDetalle;

public interface EgresoDetalleDao extends TumiDao{
	public EgresoDetalle grabar(EgresoDetalle pDto) throws DAOException;
	public EgresoDetalle modificar(EgresoDetalle o) throws DAOException;
	public List<EgresoDetalle> getListaPorPk(Object o) throws DAOException;
	public List<EgresoDetalle> getListaPorEgreso(Object o) throws DAOException;
	public List<EgresoDetalle> getListaPorBuscar(Object o) throws DAOException;
}