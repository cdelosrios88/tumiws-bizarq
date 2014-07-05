package pe.com.tumi.credito.socio.captacion.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.RequisitoDao;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class RequisitoDaoIbatis extends TumiDaoIbatis implements RequisitoDao{
	
	public Requisito grabar(Requisito o) throws DAOException {
		Requisito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Requisito modificar(Requisito o) throws DAOException {
		Requisito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Requisito> getListaRequisitoPorPK(Object o) throws DAOException{
		List<Requisito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Requisito> getListaRequisitoPorPKCaptacion(Object o) throws DAOException{
		List<Requisito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Object eliminar(Object o) throws DAOException{
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return o;
		
	}
}