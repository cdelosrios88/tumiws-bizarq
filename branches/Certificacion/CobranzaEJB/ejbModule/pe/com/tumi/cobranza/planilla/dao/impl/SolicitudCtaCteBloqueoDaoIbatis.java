package pe.com.tumi.cobranza.planilla.dao.impl;

import java.util.List;

import pe.com.tumi.cobranza.planilla.dao.SolicitudCtaCteBloqueoDao;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteBloqueo;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;

public class SolicitudCtaCteBloqueoDaoIbatis extends TumiDaoIbatis implements SolicitudCtaCteBloqueoDao{

	public SolicitudCtaCteBloqueo grabar(SolicitudCtaCteBloqueo o) throws DAOException{
		SolicitudCtaCteBloqueo dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<SolicitudCtaCteBloqueo> getListaPorTipoSol(Object o) throws DAOException {
		List<SolicitudCtaCteBloqueo> lista = null;
		
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorTipoSol",o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		
		return lista;
	}
	
	
	
}	
