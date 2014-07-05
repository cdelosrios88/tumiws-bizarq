package pe.com.tumi.credito.socio.aperturaCuenta.core.dao;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.Cuenta;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CuentaDao extends TumiDao{
	public Cuenta grabar(Cuenta o) throws DAOException;
	public Cuenta modificar(Cuenta o) throws DAOException;
	public List<Cuenta> getListaCuentaPorPK(Object o) throws DAOException;
	public Cuenta actualizarNroCuentaYSecuencia(Cuenta o) throws DAOException;
	public List<Cuenta> getListaCuentaPorPKYSituacion(Object o) throws DAOException;
	public List<Cuenta> getListaCuentaPorPkTodoEstado(Object o) throws DAOException;
}