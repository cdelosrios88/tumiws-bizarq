package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.SolicitudCtaCteDao;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class SolicitudCtaCteDaoIbatis extends TumiDaoIbatis implements SolicitudCtaCteDao{

	public SolicitudCtaCte grabar(SolicitudCtaCte o) throws DAOException{
		SolicitudCtaCte dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public SolicitudCtaCte modificar(SolicitudCtaCte o) throws DAOException{
		SolicitudCtaCte dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<SolicitudCtaCte> getListaFilrado(Object o) throws DAOException {
		List<SolicitudCtaCte> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFilrado",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	public List<SolicitudCtaCte> getListaFilradoAtencion(Object o) throws DAOException {
		List<SolicitudCtaCte> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFilradoAtencion",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	
	public List<SolicitudCtaCte> getListaPorCuenta(Object o) throws DAOException {
		List<SolicitudCtaCte> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorCuenta",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}


}	
