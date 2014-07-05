package pe.com.tumi.persona.vinculo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.vinculo.dao.VinculoDao;
import pe.com.tumi.persona.vinculo.domain.Vinculo;

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
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Vinculo> getListaVinculoPorPKPersonaEmpresa(Object o) throws DAOException{
		List<Vinculo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKPersonaEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Vinculo> getListaPorPKPersonaEmpresaYTipoVinculo(Object o) throws DAOException{
		List<Vinculo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKPerEmpYTipoVin", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Vinculo> getListaVinculoPorPKPersEmpresaYPkPersona(Object o) throws DAOException{
		List<Vinculo> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPKPersEmpYPkPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}