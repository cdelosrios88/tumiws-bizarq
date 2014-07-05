package pe.com.tumi.persona.empresa.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.persona.empresa.bo.EmpresaBO;
import pe.com.tumi.persona.empresa.dao.EmpresaJuridicaDao;
import pe.com.tumi.persona.empresa.domain.Empresa;

public class EmpresaJuridicaDaoIbatis extends TumiDaoIbatis implements EmpresaJuridicaDao{
	
	protected  static Logger log = Logger.getLogger(EmpresaJuridicaDaoIbatis.class);
	
	public List<Empresa> getListaPorPK(Object o) throws DAOException{
		List<Empresa> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Empresa> getListaEmpresa(Object o) throws DAOException {
		log.info("-----------------------Debugging EmpresaDaoIbatis.getListaEmpresa-----------------------------");
		List<Empresa> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaEmpresa", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}