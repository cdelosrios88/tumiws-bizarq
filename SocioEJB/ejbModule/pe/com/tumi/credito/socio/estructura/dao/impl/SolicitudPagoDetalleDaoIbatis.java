package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.credito.socio.estructura.dao.PadronDao;
import pe.com.tumi.credito.socio.estructura.dao.SolicitudPagoDao;
import pe.com.tumi.credito.socio.estructura.dao.SolicitudPagoDetalleDao;
import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPagoDetalle;
import pe.com.tumi.credito.socio.estructura.facade.EstructuraFacade;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class SolicitudPagoDetalleDaoIbatis extends TumiDaoIbatis implements SolicitudPagoDetalleDao{
	
	protected  static Logger log = Logger.getLogger(SolicitudPagoDetalleDaoIbatis.class);
	
	public SolicitudPagoDetalle grabar(SolicitudPagoDetalle o) throws DAOException {
		SolicitudPagoDetalle dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	public List<SolicitudPagoDetalle> getLista(Object o) throws DAOException {
		List<SolicitudPagoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLista", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	@Override
	public List<SolicitudPagoDetalle> getBusqueda(Object o)throws DAOException {
		List<SolicitudPagoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getBusqueda", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
}