package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.CuadroComparativoProveedor;

public interface CuadroComparativoProveedorDao extends TumiDao{
	public CuadroComparativoProveedor grabar(CuadroComparativoProveedor pDto) throws DAOException;
	public CuadroComparativoProveedor modificar(CuadroComparativoProveedor o) throws DAOException;
	public List<CuadroComparativoProveedor> getListaPorPk(Object o) throws DAOException;
	public List<CuadroComparativoProveedor> getListaPorCuadroComparativo(Object o) throws DAOException;
}