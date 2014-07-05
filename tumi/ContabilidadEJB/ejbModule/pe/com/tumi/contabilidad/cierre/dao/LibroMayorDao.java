package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface LibroMayorDao extends TumiDao{

	public LibroMayor grabar(LibroMayor o) throws DAOException;
	public LibroMayor modificar(LibroMayor o) throws DAOException;
	public List<LibroMayor> getListaPorPk(Object o) throws DAOException;
	public List<LibroMayor> getListaPorBuscar(Object o) throws DAOException;
}
