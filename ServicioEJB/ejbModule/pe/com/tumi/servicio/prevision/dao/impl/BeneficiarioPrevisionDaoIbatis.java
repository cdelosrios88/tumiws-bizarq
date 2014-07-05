package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.BeneficiarioPrevisionDao;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;

public class BeneficiarioPrevisionDaoIbatis extends TumiDaoIbatis implements BeneficiarioPrevisionDao{
	
	public BeneficiarioPrevision grabar(BeneficiarioPrevision o) throws DAOException {
		BeneficiarioPrevision dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public BeneficiarioPrevision modificar(BeneficiarioPrevision o) throws DAOException {
		BeneficiarioPrevision dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<BeneficiarioPrevision> getListaPorPk(Object o) throws DAOException{
		List<BeneficiarioPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<BeneficiarioPrevision> getListaPorExpediente(Object o) throws DAOException{
		List<BeneficiarioPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<BeneficiarioPrevision> getListaPorEgreso(Object o) throws DAOException{
		List<BeneficiarioPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEgreso", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public List<BeneficiarioPrevision> getListaNombreCompletoBeneficiario(Object o) throws DAOException{
		List<BeneficiarioPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaNombreCompleto", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<BeneficiarioPrevision> getListaVinculo(Object o) throws DAOException{
		List<BeneficiarioPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaVinculo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}