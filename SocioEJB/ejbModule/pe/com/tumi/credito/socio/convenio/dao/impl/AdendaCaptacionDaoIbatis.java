package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.AdendaCaptacionDao;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AdendaCaptacionDaoIbatis extends TumiDaoIbatis implements AdendaCaptacionDao{
	
	public AdendaCaptacion grabar(AdendaCaptacion o) throws DAOException {
		AdendaCaptacion dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AdendaCaptacion modificar(AdendaCaptacion o) throws DAOException {
		AdendaCaptacion dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AdendaCaptacion> getListaAdendaCaptacionPorPK(Object o) throws DAOException{
		List<AdendaCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AdendaCaptacion> getListaAdendaCaptacionPorPKAdenda(Object o) throws DAOException{
		List<AdendaCaptacion> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkAdenda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}