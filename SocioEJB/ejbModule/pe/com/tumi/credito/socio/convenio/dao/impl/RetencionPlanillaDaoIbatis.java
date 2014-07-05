package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.RetencionPlanillaDao;
import pe.com.tumi.credito.socio.convenio.domain.RetencionPlanilla;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class RetencionPlanillaDaoIbatis extends TumiDaoIbatis implements RetencionPlanillaDao{
	
	public RetencionPlanilla grabar(RetencionPlanilla o) throws DAOException {
		RetencionPlanilla dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public RetencionPlanilla modificar(RetencionPlanilla o) throws DAOException {
		RetencionPlanilla dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<RetencionPlanilla> getListaRetencPllaPorPK(Object o) throws DAOException{
		List<RetencionPlanilla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<RetencionPlanilla> getListaRetenPllaPorPKAdenda(Object o) throws DAOException{
		List<RetencionPlanilla> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkAdenda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}