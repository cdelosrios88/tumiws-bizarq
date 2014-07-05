package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCte;

public interface SolicitudCtaCteDao extends TumiDao{
	public SolicitudCtaCte grabar(SolicitudCtaCte pDto) throws DAOException;
	public SolicitudCtaCte modificar(SolicitudCtaCte o) throws DAOException;
	public List<SolicitudCtaCte> getListaFilrado(Object o) throws DAOException;
	public List<SolicitudCtaCte> getListaFilradoAtencion(Object o) throws DAOException;
	public List<SolicitudCtaCte> getListaPorCuenta(Object o) throws DAOException;
	
}	
