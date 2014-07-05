package pe.com.tumi.servicio.solicitudPrestamo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.dao.RequisitoCreditoDao;
import pe.com.tumi.servicio.solicitudPrestamo.domain.RequisitoCredito;

public class RequisitoCreditoDaoIbatis extends TumiDaoIbatis implements RequisitoCreditoDao{
	
	public RequisitoCredito grabar(RequisitoCredito o) throws DAOException {
		RequisitoCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public RequisitoCredito modificar(RequisitoCredito o) throws DAOException {
		RequisitoCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;	
	}
	
	public List<RequisitoCredito> getListaPorPk(Object o) throws DAOException{
		List<RequisitoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<RequisitoCredito> getListaPorExpedienteCredito(Object o) throws DAOException{
		List<RequisitoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpedienteCredito", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<RequisitoCredito> getListaVersionFinPorTipoYItem(Object o) throws DAOException{
		List<RequisitoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaVersionFinPorTipoYItem", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<RequisitoCredito> getListaPorPkExpedienteCreditoYRequisitoDetalle(Object o) throws DAOException{
		List<RequisitoCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkExpedienteCreditoYRequisitoDetalle", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}