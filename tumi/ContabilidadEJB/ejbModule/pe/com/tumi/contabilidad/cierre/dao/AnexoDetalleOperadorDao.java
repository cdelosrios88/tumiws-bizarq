package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AnexoDetalleOperadorDao extends TumiDao{

	public AnexoDetalleOperador grabar(AnexoDetalleOperador o) throws DAOException;
	public AnexoDetalleOperador modificar(AnexoDetalleOperador o) throws DAOException;
	public List<AnexoDetalleOperador> getListaPorPk(Object o) throws DAOException;
	public List<AnexoDetalleOperador> getListaPorAnexoDetalle(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
}
