package pe.com.tumi.reporte.operativo.credito.asociativo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Asociativo;

public interface AsociativoDao extends TumiDao {
	public List<Asociativo> getListaCantidadProyectadas(Object o) throws DAOException;
	//Captaciones
	public List<Asociativo> getListaCantidadCaptacionesEjecuadas(Object o) throws DAOException;
	public List<Asociativo> getCaptacionesEjecutadasDetalle(Object o) throws DAOException;
	public List<Asociativo> getListaCantidadCaptacionesAltas(Object o) throws DAOException;
	//Renuncias
	public List<Asociativo> getListaCantidadRenunciasEjecuadas(Object o) throws DAOException;
	public List<Asociativo> getRenunciasEjecutadasDetalle(Object o) throws DAOException;
	public List<Asociativo> getListaCantidadRenunciasBajas(Object o) throws DAOException;
	//Convenios
	public List<Asociativo> getListaConveniosPorSucursal(Object o) throws DAOException;
	//Agregado por cdelosrios, 28/11/2013
	//Servicio
	public List<Asociativo> getListaExpedienteProvision(Object o) throws DAOException;
	//Fin agregado por cdelosrios, 28/11/2013
}
