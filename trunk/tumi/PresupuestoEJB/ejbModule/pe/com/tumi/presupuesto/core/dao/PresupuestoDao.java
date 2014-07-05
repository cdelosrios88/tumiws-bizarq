package pe.com.tumi.presupuesto.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.presupuesto.core.domain.Presupuesto;
import pe.com.tumi.presupuesto.core.domain.PresupuestoId;

public interface PresupuestoDao extends TumiDao {
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Presupuesto grabar(Presupuesto o) throws DAOException;
	public Presupuesto modificar(Presupuesto o) throws DAOException;
	public List<Presupuesto> getListaPorPK(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 21.10.2013
	public List<Presupuesto> getListaPresupuestoPorFiltros(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013	
	public List<Presupuesto> getListaPorRangoFechas(Object o) throws DAOException;
	public List<Presupuesto> getMesesDelAnioBase(Object o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 25.10.2013	
	public Presupuesto eliminar(PresupuestoId o) throws DAOException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 28.10.2013		
	public List<Presupuesto> getLstPstoAnioBase(Object o) throws DAOException;
}
