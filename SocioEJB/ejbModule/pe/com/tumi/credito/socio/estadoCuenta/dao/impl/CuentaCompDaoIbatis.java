package pe.com.tumi.credito.socio.estadoCuenta.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CuentaCompDaoIbatis extends TumiDaoIbatis implements CuentaCompDao {
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 29.10.2013
	 * Recupera Datos de Cta. Integrante Y Cuenta por Socio, Tipo Cta. y Situacion Cta.
	 */
	public List<CuentaComp> getCtaIntYCtaXSocTipYSitCta(Object o) throws DAOException{
		List<CuentaComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCtaIntYCtaXSocTipYSitCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

}