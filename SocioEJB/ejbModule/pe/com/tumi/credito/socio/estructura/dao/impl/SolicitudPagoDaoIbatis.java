package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.SolicitudPagoDao;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class SolicitudPagoDaoIbatis extends TumiDaoIbatis implements SolicitudPagoDao{
	
	public List<SolicitudPago> getSolicitudPagoPorPK(Object o) throws DAOException{
		List<SolicitudPago> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public SolicitudPago grabar(SolicitudPago o) throws DAOException {
		SolicitudPago dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	@Override
	public SolicitudPago modificar(SolicitudPago o) throws DAOException {
		SolicitudPago dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	
}