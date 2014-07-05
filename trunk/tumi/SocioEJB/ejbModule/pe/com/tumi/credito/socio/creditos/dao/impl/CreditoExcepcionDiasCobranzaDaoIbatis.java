package pe.com.tumi.credito.socio.creditos.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.dao.CondicionHabilDao;
import pe.com.tumi.credito.socio.creditos.dao.CreditoExcepcionDiasCobranzaDao;
import pe.com.tumi.credito.socio.creditos.dao.FinalidadDao;
import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionDiasCobranza;
import pe.com.tumi.credito.socio.creditos.domain.Finalidad;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class CreditoExcepcionDiasCobranzaDaoIbatis extends TumiDaoIbatis implements CreditoExcepcionDiasCobranzaDao{
	
	public CreditoExcepcionDiasCobranza grabar(CreditoExcepcionDiasCobranza o) throws DAOException {
		CreditoExcepcionDiasCobranza dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public CreditoExcepcionDiasCobranza modificar(CreditoExcepcionDiasCobranza o) throws DAOException {
		CreditoExcepcionDiasCobranza dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<CreditoExcepcionDiasCobranza> getListaCreditoExcepcionDiasCobranzaPorPK(Object o) throws DAOException{
		List<CreditoExcepcionDiasCobranza> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<CreditoExcepcionDiasCobranza> getListaCreditoExcepcionDiasCobranzaPorPKCreditoExcepcion(Object o) throws DAOException{
		List<CreditoExcepcionDiasCobranza> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkCreditoExcepcion", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}