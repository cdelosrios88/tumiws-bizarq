package pe.com.tumi.contabilidad.cierre.dao;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface AnexoDetalleDao extends TumiDao{

	public AnexoDetalle grabar(AnexoDetalle o) throws DAOException;
	public AnexoDetalle modificar(AnexoDetalle o) throws DAOException;
	public List<AnexoDetalle> getListaPorPk(Object o) throws DAOException;
	public List<AnexoDetalle> getListaPorAnexo(Object o) throws DAOException;
	public void eliminar(Object o) throws DAOException;
}
