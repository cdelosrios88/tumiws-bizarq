package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.PerfilValidacionDao;
import pe.com.tumi.credito.socio.convenio.domain.PerfilValidacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class PerfilValidacionDaoIbatis extends TumiDaoIbatis implements PerfilValidacionDao{
	
	public PerfilValidacion grabar(PerfilValidacion o) throws DAOException {
		PerfilValidacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public PerfilValidacion modificar(PerfilValidacion o) throws DAOException {
		PerfilValidacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<PerfilValidacion> getListaPerfilValidacionPorPK(Object o) throws DAOException{
		List<PerfilValidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PerfilValidacion> getListaPerfilValidacionPorEmpresaYPerfil(Object o) throws DAOException{
		List<PerfilValidacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEmpresaYPerfil", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}