package pe.com.tumi.seguridad.login.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.seguridad.login.dao.EmpresaUsuarioDao;
import pe.com.tumi.seguridad.login.domain.EmpresaUsuario;

public class EmpresaUsuarioDaoIbatis extends TumiDaoIbatis implements EmpresaUsuarioDao{
	
	public EmpresaUsuario grabar(EmpresaUsuario o) throws DAOException {
		EmpresaUsuario dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EmpresaUsuario modificar(EmpresaUsuario o) throws DAOException {
		EmpresaUsuario dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<EmpresaUsuario> getListaEmpresaUsuarioPorPk(Object o) throws DAOException{
		List<EmpresaUsuario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EmpresaUsuario> getListaEmpresaUsuarioPorIdEmpresa(Object o) throws DAOException{
		List<EmpresaUsuario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EmpresaUsuario> getListaPorIdPersona(Object o) throws DAOException{
		List<EmpresaUsuario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EmpresaUsuario> getListaPorIdPersonaYFechaEliminacion(Object o) throws DAOException{
		List<EmpresaUsuario> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersonaYFechaEli", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
