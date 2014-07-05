package pe.com.tumi.seguridad.empresa.dao;

import java.util.List;

import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface ZonalDao extends TumiDao {
	public Zonal grabar(Zonal o) throws DAOException;
	public Zonal modificar(Zonal o) throws DAOException;
	public List<Zonal> getListaZonalPorPk(Object o) throws DAOException;
	public List<Zonal> getListaZonalPorIdZonal(Object o) throws DAOException;
	public List<Zonal> getListaZonalPorIdPersona(Object o) throws DAOException;
	public List<Zonal> getListaZonalDeBusqueda(Object o) throws DAOException;
}
