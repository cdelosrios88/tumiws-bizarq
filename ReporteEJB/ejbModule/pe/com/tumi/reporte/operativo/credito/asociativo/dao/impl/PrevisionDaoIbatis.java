package pe.com.tumi.reporte.operativo.credito.asociativo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.PrevisionDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Prevision;

public class PrevisionDaoIbatis extends TumiDaoIbatis implements PrevisionDao{

	public List<Prevision> getListaFondoSepelio(Object o) throws DAOException {
		List<Prevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFondoSepelio", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Prevision> getListaFondoRetiro(Object o) throws DAOException {
		List<Prevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFondoRetiro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Prevision> getListaAes(Object o) throws DAOException {
		List<Prevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaAes", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
