package pe.com.tumi.credito.socio.aperturaCuenta.core.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.dao.CuentaMesCobroDao;
import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaMesCobro;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CuentaMesCobroDaoIbatis extends TumiDaoIbatis implements CuentaMesCobroDao{
	
	public CuentaMesCobro grabar(CuentaMesCobro o) throws DAOException {
		CuentaMesCobro dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CuentaMesCobro modificar(CuentaMesCobro o) throws DAOException {
		CuentaMesCobro dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CuentaMesCobro> getListaCuentaMesCobroPorPK(Object o) throws DAOException{
		List<CuentaMesCobro> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}