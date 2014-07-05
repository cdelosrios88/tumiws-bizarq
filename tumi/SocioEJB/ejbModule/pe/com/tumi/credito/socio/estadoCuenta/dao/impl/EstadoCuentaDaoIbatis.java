package pe.com.tumi.credito.socio.estadoCuenta.dao.impl;


import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.composite.CuentaComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class EstadoCuentaDaoIbatis extends TumiDaoIbatis implements EstadoCuentaDao {
	
	public List<CuentaComp> getLsCtasPorPkSocioYTipCta(Object o) throws DAOException{
		List<CuentaComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLsCtasPorPkSocioYTipCta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	

}