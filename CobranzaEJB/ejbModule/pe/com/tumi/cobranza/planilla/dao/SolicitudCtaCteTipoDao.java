package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.cobranza.planilla.domain.SolicitudCtaCteTipo;

public interface SolicitudCtaCteTipoDao extends TumiDao{
	public SolicitudCtaCteTipo grabar(SolicitudCtaCteTipo pDto) throws DAOException;
	public SolicitudCtaCteTipo modificar(SolicitudCtaCteTipo o) throws DAOException;
	public List<SolicitudCtaCteTipo> getListaPorPk(Object o) throws DAOException;
	public List<SolicitudCtaCteTipo> getListaPorSolCtacteSinEstado(Object o) throws DAOException;	
	public List<SolicitudCtaCteTipo> getListaPorSolicitudCtacte(Object o) throws DAOException;
	public void eliminarPorSolicitudCtacte(Object o) throws DAOException;
}	
