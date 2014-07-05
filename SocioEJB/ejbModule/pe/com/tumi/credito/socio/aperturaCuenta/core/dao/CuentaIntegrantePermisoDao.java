package pe.com.tumi.credito.socio.aperturaCuenta.core.dao;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrantePermiso;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CuentaIntegrantePermisoDao extends TumiDao{
	public CuentaIntegrantePermiso grabar(CuentaIntegrantePermiso o) throws DAOException;
	public CuentaIntegrantePermiso modificar(CuentaIntegrantePermiso o) throws DAOException;
	public List<CuentaIntegrantePermiso> getListaCuentaIntegrantePermisoPorPK(Object o) throws DAOException;
	public List<CuentaIntegrantePermiso> getListaCuentaIntegrantePermisoPorCuentaIntegrante(Object o) throws DAOException;
}