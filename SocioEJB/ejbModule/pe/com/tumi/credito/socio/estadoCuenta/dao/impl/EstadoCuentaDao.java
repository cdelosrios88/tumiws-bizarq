package pe.com.tumi.credito.socio.estadoCuenta.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface EstadoCuentaDao extends TumiDao {
	//public List<SocioComp> getListaPorPKSocioYTipoCuenta(Object o) throws DAOException;
	public List<CuentaComp> getLsCtasPorPkSocioYTipCta(Object o) throws DAOException;
}
