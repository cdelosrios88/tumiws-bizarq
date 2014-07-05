package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.CompetenciaDao;
import pe.com.tumi.credito.socio.convenio.domain.Competencia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CompetenciaDaoIbatis extends TumiDaoIbatis implements CompetenciaDao{
	
	public Competencia grabar(Competencia o) throws DAOException {
		Competencia dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Competencia modificar(Competencia o) throws DAOException {
		Competencia dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Competencia> getListaCompetenciaPorPK(Object o) throws DAOException{
		List<Competencia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Competencia> getListaCompetenciaPorPKConvenio(Object o) throws DAOException{
		List<Competencia> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkConvenio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}