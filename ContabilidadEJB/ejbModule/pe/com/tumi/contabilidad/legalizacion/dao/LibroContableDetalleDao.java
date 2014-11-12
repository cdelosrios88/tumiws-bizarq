package pe.com.tumi.contabilidad.legalizacion.dao;

import java.util.List;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalle;
import pe.com.tumi.contabilidad.legalizacion.domain.LibroContableDetalleComp;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface LibroContableDetalleDao extends TumiDao {
	public LibroContableDetalle grabar(LibroContableDetalle o) throws DAOException;
	public LibroContableDetalle modificar(LibroContableDetalle o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
	public List<LibroContableDetalleComp> getListaLibroContableDetalle(Object o) throws DAOException;
	public List<LibroContableDetalleComp> getListaUltimoFolioDetalle(Object o) throws DAOException;
	public List<LibroContableDetalleComp> getListaLibroContaDetaNulo(Object o) throws DAOException;
}