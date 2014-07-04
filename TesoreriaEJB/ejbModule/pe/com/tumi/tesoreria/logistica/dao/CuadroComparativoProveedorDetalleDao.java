package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedorDetalle;

public interface CuadroComparativoProveedorDetalleDao extends TumiDao{
	public CuadroComparativoProveedorDetalle grabar(CuadroComparativoProveedorDetalle pDto) throws DAOException;
	public CuadroComparativoProveedorDetalle modificar(CuadroComparativoProveedorDetalle o) throws DAOException;
	public List<CuadroComparativoProveedorDetalle> getListaPorPk(Object o) throws DAOException;
	public List<CuadroComparativoProveedorDetalle> getListaPorCuadroComparativoProveedor(Object o) throws DAOException;
}