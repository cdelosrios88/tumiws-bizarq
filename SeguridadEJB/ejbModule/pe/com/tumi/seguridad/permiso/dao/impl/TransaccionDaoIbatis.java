package pe.com.tumi.seguridad.permiso.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.TransaccionDao;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;

public class TransaccionDaoIbatis extends TumiDaoIbatis implements TransaccionDao {

	protected  static Logger log = Logger.getLogger(TransaccionDaoIbatis.class);
	
	public Transaccion grabar(Transaccion o) throws DAOException {
		Transaccion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Transaccion modificar(Transaccion o) throws DAOException {
		Transaccion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<Transaccion> getListaPorPk(Object o) throws DAOException {
		List<Transaccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaPrincipalPorIdEmpresa(Object o) throws DAOException {
		List<Transaccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPrincipalPorIdEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Transaccion> getListaPrincipalPorIdPerfil(Object o) throws DAOException {
		List<Transaccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPrincipalPorIdPerfil", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaPorPkPadre(Object o) throws DAOException {
		List<Transaccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkPadre", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaPorPkPadreYIdPerfil(Object o) throws DAOException {
		List<Transaccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkPadreYIdPerfil", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaDeBusquedaPrincipal(Object o) throws DAOException {
		List<Transaccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeBusquedaPrincipal", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaDeBusqueda(Object o) throws DAOException {
		List<Transaccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Transaccion> getListaDeBusquedaPorPkPadre(Object o) throws DAOException {
		List<Transaccion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeBusquedaPorPkPadre", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
