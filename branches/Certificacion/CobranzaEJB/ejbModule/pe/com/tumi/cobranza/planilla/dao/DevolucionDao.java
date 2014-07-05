package pe.com.tumi.cobranza.planilla.dao;

import java.util.List;

import pe.com.tumi.cobranza.planilla.domain.Devolucion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface DevolucionDao extends TumiDao{
	public Devolucion grabar(Devolucion o) throws DAOException;
	public Devolucion modificar(Devolucion o) throws DAOException;
	public List<Devolucion> getListaPorSolicitudCtaCteTipo(Object o) throws DAOException;

}
