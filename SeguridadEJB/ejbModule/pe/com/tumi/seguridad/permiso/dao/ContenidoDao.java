package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.Contenido;

public interface ContenidoDao extends TumiDao {
	public Contenido grabar(Contenido o) throws DAOException;
	public Contenido modificar(Contenido o) throws DAOException;
	public List<Contenido> getListaPorPk(Object o) throws DAOException;
}
