package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.ConvenioDetalleDao;
import pe.com.tumi.credito.socio.estructura.domain.ConvenioEstructuraDetalle;
import pe.com.tumi.credito.socio.estructura.domain.composite.ConvenioEstructuraDetalleComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class ConvenioDetalleDaoIbatis extends TumiDaoIbatis implements ConvenioDetalleDao{
	
	public ConvenioEstructuraDetalle grabar(ConvenioEstructuraDetalle o) throws DAOException {
		ConvenioEstructuraDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public ConvenioEstructuraDetalle modificar(ConvenioEstructuraDetalle o) throws DAOException {
		ConvenioEstructuraDetalle dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<ConvenioEstructuraDetalle> getListaConvenioDetallePorPK(Object o) throws DAOException{
		List<ConvenioEstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConvenioEstructuraDetalleComp> getListaConvenioDetallePorPKConvenio(Object o) throws DAOException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkConvenio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConvenioEstructuraDetalleComp> getConvenioDetallePorPKEstructuraDet(Object o) throws DAOException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConvDetPorEstructDet", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConvenioEstructuraDetalle> getConvenioDetallePorPKEstructuraDetalle(Object o) throws DAOException{
		List<ConvenioEstructuraDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConvDetPorPkEstructDet", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<ConvenioEstructuraDetalleComp> getListaConvenioEstructuraDetallePorEstructuraDetCompleto(Object o) throws DAOException{
		List<ConvenioEstructuraDetalleComp> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaConvDetPorEstructDet", o);
		}catch(Exception e) {
			System.out.println("getListaConvenioEstructuraDetallePorEstructuraDetCompleto ---> "+e);
			throw new DAOException (e);
		}
		return lista;
	}
}