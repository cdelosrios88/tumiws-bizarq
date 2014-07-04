package pe.com.tumi.credito.socio.estructura.dao;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.domain.AdminPadron;
import pe.com.tumi.credito.socio.estructura.domain.Padron;
import pe.com.tumi.credito.socio.estructura.domain.SolicitudPago;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface SolicitudPagoDao extends TumiDao{
	public List<SolicitudPago> getSolicitudPagoPorPK(Object o) throws DAOException;
	public SolicitudPago grabar(SolicitudPago o) throws DAOException;
	public SolicitudPago modificar(SolicitudPago o) throws DAOException;
}
