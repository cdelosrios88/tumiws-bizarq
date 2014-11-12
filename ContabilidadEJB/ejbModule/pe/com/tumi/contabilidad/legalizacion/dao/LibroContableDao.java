package pe.com.tumi.contabilidad.legalizacion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContable;

public interface LibroContableDao extends TumiDao{
	public LibroContable grabar(LibroContable pDto) throws DAOException;
	public LibroContable modificar(LibroContable o) throws DAOException;
	public List<LibroContable> getListaPorPk(Object o) throws DAOException;
	public List<LibroContable> getBusqueda(Object o) throws DAOException;
}