package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.CuentaDetalleBeneficioDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;

public class CuentaDetalleBeneficioDaoIbatis extends TumiDaoIbatis implements CuentaDetalleBeneficioDao{
	
	public CuentaDetalleBeneficio grabar(CuentaDetalleBeneficio o) throws DAOException {
		CuentaDetalleBeneficio dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CuentaDetalleBeneficio modificar(CuentaDetalleBeneficio o) throws DAOException {
		CuentaDetalleBeneficio dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CuentaDetalleBeneficio> getListaPorPK(Object o) throws DAOException{
		List<CuentaDetalleBeneficio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaDetalleBeneficio> getListaPorPKConcepto(Object o) throws DAOException{
		List<CuentaDetalleBeneficio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKConcepto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CuentaDetalleBeneficio> getListaPorPKCuenta(Object o) throws DAOException{
		List<CuentaDetalleBeneficio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKCuenta", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	public void modificarPorBeneficiario(CuentaDetalleBeneficio o) throws DAOException {
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificarPorBeneficiario", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
	}
	
}