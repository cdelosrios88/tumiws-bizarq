package pe.com.tumi.seguridad.empresa.dao;

import java.util.List;

import pe.com.tumi.empresa.domain.Zonal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface ZonalSucursalDao extends TumiDao {
	public List<Zonal> getListaZonalSucursalPorIdZonal(Object o) throws DAOException;
}
