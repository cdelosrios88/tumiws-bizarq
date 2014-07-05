package pe.com.tumi.credito.socio.aperturaCuenta.core.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaIntegranteDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaIntegrantePermisoDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrante;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaIntegrantePermiso;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CuentaIntegrantePermisoDaoIbatis extends TumiDaoIbatis implements CuentaIntegrantePermisoDao{
	
	public CuentaIntegrantePermiso grabar(CuentaIntegrantePermiso o) throws DAOException {
		CuentaIntegrantePermiso dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CuentaIntegrantePermiso modificar(CuentaIntegrantePermiso o) throws DAOException {
		CuentaIntegrantePermiso dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CuentaIntegrantePermiso> getListaCuentaIntegrantePermisoPorPK(Object o) throws DAOException{
		List<CuentaIntegrantePermiso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaIntegrantePermiso> getListaCuentaIntegrantePermisoPorCuentaIntegrante(Object o) throws DAOException{
		List<CuentaIntegrantePermiso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuentaIntegrante", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}