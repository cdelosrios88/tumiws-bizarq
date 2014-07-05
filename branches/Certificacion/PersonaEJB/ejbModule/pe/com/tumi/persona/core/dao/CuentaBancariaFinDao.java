package pe.com.tumi.persona.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.core.domain.CuentaBancariaFin;

public interface CuentaBancariaFinDao extends TumiDao{
	public CuentaBancariaFin grabar(CuentaBancariaFin o) throws DAOException;
	public List<CuentaBancariaFin> getListaPorPK(Object o) throws DAOException;
	public List<CuentaBancariaFin> getListaPorCuentaBancaria(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
}
