package pe.com.tumi.credito.socio.captacion.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.VinculoDao;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class VinculoDaoIbatis extends TumiDaoIbatis implements VinculoDao{
	
	public Vinculo grabar(Vinculo o) throws DAOException {
		Vinculo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Vinculo modificar(Vinculo o) throws DAOException {
		Vinculo dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Vinculo> getListaVinculoPorPK(Object o) throws DAOException{
		List<Vinculo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Vinculo> getListaVinculoPorPKCaptacion(Object o) throws DAOException{
		List<Vinculo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Vinculo> getListaVinculos() throws DAOException{
		List<Vinculo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLista");
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