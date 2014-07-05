package pe.com.tumi.movimiento.concepto.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.dao.CronogramaDao;
import pe.com.tumi.movimiento.concepto.domain.Cronograma;

public class CronogramaDaoIbatis extends TumiDaoIbatis implements CronogramaDao{
	
	public Cronograma grabar(Cronograma o) throws DAOException {
		Cronograma dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Cronograma modificar(Cronograma o) throws DAOException {
		Cronograma dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Cronograma> getListaPorPK(Object o) throws DAOException{
		List<Cronograma> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Cronograma> getListaPorPkExpediente(Object o) throws DAOException{
		List<Cronograma> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Cronograma> getListaDeVencidoPorPkExpedienteYPeriodoYPago(Object o) throws DAOException{
		List<Cronograma> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstVencidoPorPkExpYPerYPago", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}	
}