package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.creditos.dao.CreditoDao;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoDaoIbatis extends TumiDaoIbatis implements CreditoDao{
	
	public Credito grabar(Credito o) throws DAOException {
		Credito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Credito modificar(Credito o) throws DAOException {
		Credito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Credito> getListaCreditoPorPK(Object o) throws DAOException{
		List<Credito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Credito> getListaParaFiltro(Object o) throws DAOException{
		List<Credito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaParaFiltro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public CreditoId eliminarCredito(CreditoId o) throws DAOException {
		CreditoId dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Credito> getlistaCreditoPorCredito(Object o) throws DAOException{
		List<Credito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getlistaCreditoPorCredito", o);
		}catch(Exception e) {
			System.out.println("ERROR EN getlistaCreditoPorCredito ---> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
	
}
