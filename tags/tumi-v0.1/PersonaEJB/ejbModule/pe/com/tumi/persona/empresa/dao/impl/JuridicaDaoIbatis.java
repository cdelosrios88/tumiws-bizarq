package pe.com.tumi.persona.empresa.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.empresa.dao.JuridicaDao;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class JuridicaDaoIbatis extends TumiDaoIbatis implements JuridicaDao{
	
	protected  static Logger log = Logger.getLogger(JuridicaDaoIbatis.class);
	
	public Juridica grabar(Juridica o) throws DAOException {
		log.info("-----------------------Debugging JuridicaDaoIbatis.grabarJuridica-----------------------------");
		Juridica dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Juridica modificar(Juridica o) throws DAOException {
		Juridica dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Juridica> getListaJuridicaPorPK(Object o) throws DAOException{
		List<Juridica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Juridica> getListaJuridicaPorInPk(Object o) throws DAOException{
		List<Juridica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorInPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Juridica> getListaJuridicaPorInPkLikeRazon(Object o) throws DAOException{
		List<Juridica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorInPkLikeRazon", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Juridica> getListaJuridicaDeEmpresa(Object o) throws DAOException{
		List<Juridica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaDeEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	public List<Juridica> getJuridicaBusqueda(Object o) throws DAOException {
		List<Juridica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getJuridicaBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Juridica> getListaJuridicaPorRazonSocial(Object o) throws DAOException {
		List<Juridica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorRazonSocial", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Juridica> getListaJuridicaPorNombreComercial(Object o) throws DAOException {
		List<Juridica> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorNombreComercial", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Juridica> listaJuridicaWithFile(Object o)throws DAOException
	{
		List<Juridica> lista = null;
		try
		{
			lista = (List)getSqlMapClientTemplate().queryForList(getNameSpace()+".listaJuridicaWithFile",o);
		}catch(Exception e)
		{
			throw new DAOException(e);
		}
		return lista;
	}
}