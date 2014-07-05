package pe.com.tumi.credito.socio.captacion.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.EstructuraCaptacionDao;
import pe.com.tumi.credito.socio.captacion.domain.EstructuraCaptacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class EstructuraCaptacionDaoIbatis extends TumiDaoIbatis implements EstructuraCaptacionDao{
	
	public EstructuraCaptacion grabar(EstructuraCaptacion o) throws DAOException {
		EstructuraCaptacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EstructuraCaptacion modificar(EstructuraCaptacion o) throws DAOException {
		EstructuraCaptacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<EstructuraCaptacion> getListaEstructuraCaptacionPorPK(Object o) throws DAOException{
		List<EstructuraCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstructuraCaptacion> getListaEstructuraCaptacionPorPKCaptacion(Object o) throws DAOException{
		List<EstructuraCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCaptacion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}