package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.AdendaCaptacionDao;
import pe.com.tumi.credito.socio.convenio.dao.AdendaCreditoDao;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacion;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCredito;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AdendaCreditoDaoIbatis extends TumiDaoIbatis implements AdendaCreditoDao{
	
	public AdendaCredito grabar(AdendaCredito o) throws DAOException {
		AdendaCredito dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public AdendaCredito modificar(AdendaCredito o) throws DAOException {
		AdendaCredito dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<AdendaCredito> getListaAdendaCreditoPorPK(Object o) throws DAOException{
		List<AdendaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<AdendaCredito> getListaAdendaCreditoPorPKAdenda(Object o) throws DAOException{
		List<AdendaCredito> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkAdenda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}