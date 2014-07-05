package pe.com.tumi.persona.empresa.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.empresa.dao.EmpresaDao;
import pe.com.tumi.persona.empresa.domain.Empresa;

public class EmpresaDaoIbatis extends TumiDaoIbatis implements EmpresaDao{
	
	protected  static Logger log = Logger.getLogger(EmpresaDaoIbatis.class);
	
	public Empresa grabar(Empresa o) throws DAOException {
		log.info("-----------------------Debugging EmpresaDaoIbatis.getListaEmpresa-----------------------------");
		Empresa dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Empresa modificar(Empresa o) throws DAOException {
		Empresa dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Empresa> getListaEmpresaPorPK(Object o) throws DAOException{
		List<Empresa> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Empresa> getListaTodos(Object o) throws DAOException{
		List<Empresa> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaTodos", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}