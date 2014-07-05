package pe.com.tumi.contabilidad.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.contabilidad.core.dao.AccesoPlanCuentaDetalleDao;
import pe.com.tumi.contabilidad.core.domain.AccesoPlanCuentaDetalle;

public interface AccesoPlanCuentaDetalleDao extends TumiDao{
	public AccesoPlanCuentaDetalle grabar(AccesoPlanCuentaDetalle pDto) throws DAOException;
	public AccesoPlanCuentaDetalle modificar(AccesoPlanCuentaDetalle o) throws DAOException;
	public List<AccesoPlanCuentaDetalle> getListaPorPk(Object o) throws DAOException;
	public List<AccesoPlanCuentaDetalle> getListaPorAccesoPlanCuenta(Object o) throws DAOException;
}	
