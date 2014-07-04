package pe.com.tumi.contabilidad.legalizacion.dao;

import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.domain.LibroLegalizacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface LibroLegalizacionDao extends TumiDao{

	public LibroLegalizacion grabar(LibroLegalizacion o) throws DAOException;
	public LibroLegalizacion modificar(LibroLegalizacion o) throws DAOException;
	public List<LibroLegalizacion> getListaPorPk(Object o) throws DAOException;
	public List<LibroLegalizacion> getListaPorIdPersona(Object o) throws DAOException;
}
