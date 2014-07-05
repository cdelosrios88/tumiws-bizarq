package pe.com.tumi.credito.socio.estructura.dao;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPagoDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface SolicitudPagoDetalleDao extends TumiDao{
	public SolicitudPagoDetalle grabar(SolicitudPagoDetalle o) throws DAOException;
	public List<SolicitudPagoDetalle> getLista(Object o) throws DAOException;
	public List<SolicitudPagoDetalle> getBusqueda(Object o) throws DAOException;
}
