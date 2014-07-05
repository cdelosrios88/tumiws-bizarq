package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.EstadoSolicitudCtaCteDao;
import pe.com.tumi.cobranza.planilla.domain.EstadoSolicitudCtaCte;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class EstadoSolicitudCtaCteDaoIbatis extends TumiDaoIbatis implements EstadoSolicitudCtaCteDao{

	public EstadoSolicitudCtaCte grabar(EstadoSolicitudCtaCte o) throws DAOException{
		EstadoSolicitudCtaCte dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public EstadoSolicitudCtaCte modificar(EstadoSolicitudCtaCte o) throws DAOException{
		EstadoSolicitudCtaCte dto = null;
		try {
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e){
			throw new DAOException(e);
		}
		return dto;
	}  

	public List<EstadoSolicitudCtaCte> getListaPorPk(Object o) throws DAOException{
		List<EstadoSolicitudCtaCte> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EstadoSolicitudCtaCte> getListaPorSolicitudCtacte(Object o) throws DAOException {
		List<EstadoSolicitudCtaCte> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorSolicitudCtacte",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return lista;
	}
	
	
}	
