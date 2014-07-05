package pe.com.tumi.servicio.prevision.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.BeneficiarioPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.FallecidoPrevisionDao;
import pe.com.tumi.servicio.prevision.domain.BeneficiarioPrevision;
import pe.com.tumi.servicio.prevision.domain.FallecidoPrevision;

public class FallecidoPrevisionDaoIbatis extends TumiDaoIbatis implements FallecidoPrevisionDao{

	public FallecidoPrevision grabar(FallecidoPrevision o) throws DAOException {
		FallecidoPrevision dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public FallecidoPrevision modificar(FallecidoPrevision o) throws DAOException {
		FallecidoPrevision dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<FallecidoPrevision> getListaPorPk(Object o) throws DAOException{
		List<FallecidoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<FallecidoPrevision> getListaPorExpediente(Object o) throws DAOException{
		List<FallecidoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorExpediente", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<FallecidoPrevision> getListaPorEgreso(Object o) throws DAOException{
		List<FallecidoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorEgreso", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//autor y fecha rVillarreal 18/06/2014
	public List<FallecidoPrevision> getListaNombreCompletoAes(Object o) throws DAOException{
		List<FallecidoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaNombreCompletoAes", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//autor y fecha rVillarreal 19/06/2014
	public List<FallecidoPrevision> getListaVinculoAes(Object o) throws DAOException{
		List<FallecidoPrevision> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaVinculoAes", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
