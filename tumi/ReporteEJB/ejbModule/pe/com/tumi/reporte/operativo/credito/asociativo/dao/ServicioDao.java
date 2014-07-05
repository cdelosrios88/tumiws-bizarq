package pe.com.tumi.reporte.operativo.credito.asociativo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Servicio;

public interface ServicioDao extends TumiDao {
	public List<Servicio> getListaCantidadProyectadas(Object o) throws DAOException;
	//Captaciones
	public List<Servicio> getListaCantidadCaptacionesEjecuadas(Object o) throws DAOException;
	public List<Servicio> getCaptacionesEjecutadasDetalle(Object o) throws DAOException;
	public List<Servicio> getListaSaldoServicios(Object o) throws DAOException;
	public List<Servicio> getListaPrimerDscto(Object o) throws DAOException;
}
