package pe.com.tumi.credito.socio.estadoCuenta.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CuentaCompDao extends TumiDao {
	//AUTOR Y FECHA CREACION: JCHAVEZ / 29.10.2013
	public List<CuentaComp> getCtaIntYCtaXSocTipYSitCta(Object o) throws DAOException;
}
