package pe.com.tumi.seguridad.empresa.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.empresa.domain.Area;
import pe.com.tumi.empresa.domain.Sucursal;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AreaDao extends TumiDao {
	
	public List<Area> getListaArea(Object o) throws DAOException;
	public Area grabar(Area o) throws DAOException;
	public Area modificar(Area o) throws DAOException;
	public List<Area> getAreaPorPK(Object o) throws DAOException;
}
