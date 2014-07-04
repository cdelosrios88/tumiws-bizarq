package pe.com.tumi.riesgo.archivo.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.archivo.domain.ConfDetalle;

public interface ConfDetalleDao extends TumiDao{
	public ConfDetalle grabar(ConfDetalle o) throws DAOException;
	public ConfDetalle modificar(ConfDetalle o) throws DAOException;
	public List<ConfDetalle> getListaPorPk(Object o) throws DAOException;
	public List<ConfDetalle> getListaPorIntItemConfiguracion(Object o) throws DAOException;
}
