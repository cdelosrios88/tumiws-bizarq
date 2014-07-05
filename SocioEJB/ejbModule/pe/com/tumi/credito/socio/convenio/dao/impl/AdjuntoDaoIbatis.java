package pe.com.tumi.credito.socio.convenio.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.AdjuntoDao;
import pe.com.tumi.credito.socio.convenio.domain.Adjunto;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class AdjuntoDaoIbatis extends TumiDaoIbatis implements AdjuntoDao{
	
	public Adjunto grabar(Adjunto o) throws DAOException {
		Adjunto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Adjunto modificar(Adjunto o) throws DAOException {
		Adjunto dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Adjunto> getListaAdjuntoPorPK(Object o) throws DAOException{
		List<Adjunto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<Adjunto> getListaAdjuntoPorPKAdenda(Object o) throws DAOException{
		List<Adjunto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPkAdenda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
}