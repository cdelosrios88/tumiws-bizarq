package pe.com.tumi.credito.socio.captacion.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.ConceptoDao;
import pe.com.tumi.credito.socio.captacion.domain.Concepto;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class ConceptoDaoIbatis extends TumiDaoIbatis implements ConceptoDao{
	
	public Concepto grabar(Concepto o) throws DAOException {
		Concepto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Concepto modificar(Concepto o) throws DAOException {
		Concepto dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Concepto> getListaConceptoPorPK(Object o) throws DAOException{
		List<Concepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Concepto> getListaConceptoPorPKCaptacion(Object o) throws DAOException{
		List<Concepto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}