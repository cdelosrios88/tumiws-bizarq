package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.Computadora;

public interface ComputadoraDao extends TumiDao {
	public Computadora grabar(Computadora o) throws DAOException;
	public Computadora modificar(Computadora o) throws DAOException;
	public List<Computadora> getListaPorPk(Object o) throws DAOException;
	public List<Computadora> getListaBusqueda(Object o) throws DAOException;
}
