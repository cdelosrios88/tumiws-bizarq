package pe.com.tumi.persona.contacto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.contacto.dao.DomicilioDao;
import pe.com.tumi.persona.contacto.domain.Domicilio;

public class DomicilioDaoIbatis extends TumiDaoIbatis implements DomicilioDao{
	
	public Domicilio grabar(Domicilio o) throws DAOException {
		Domicilio dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Domicilio modificar(Domicilio o) throws DAOException {
		Domicilio dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Domicilio> getListaDomicilioPorPK(Object o) throws DAOException{
		List<Domicilio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Domicilio> getListaDomicilioPorIdPersona(Object o) throws DAOException{
		List<Domicilio> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorIdPersona", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public Domicilio eliminarDomicilio(Domicilio o) throws DAOException {
		Domicilio dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".eliminar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
}