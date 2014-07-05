package pe.com.tumi.riesgo.archivo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.riesgo.archivo.dao.ConfiguracionDao;
import pe.com.tumi.riesgo.archivo.domain.Configuracion;

public class ConfiguracionDaoIbatis extends TumiDaoIbatis implements ConfiguracionDao{
	
	public Configuracion grabar(Configuracion o) throws DAOException {
		Configuracion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Configuracion modificar(Configuracion o) throws DAOException {
		Configuracion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Configuracion> getListaPorPk(Object o) throws DAOException{
		List<Configuracion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Configuracion> getListaPorBusqueda(Object o) throws DAOException{
		List<Configuracion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Configuracion> getListaPorBusquedaConEstructura(Object o) throws DAOException{
		List<Configuracion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBusquedaConEstructura", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Configuracion> getListaPorBusquedaSinEstructura(Object o) throws DAOException{
		List<Configuracion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorBusquedaSinEstructura", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}