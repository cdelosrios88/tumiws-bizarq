package pe.com.tumi.seguridad.permiso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraAcceso;

public interface ComputadoraAccesoDao extends TumiDao {
	public ComputadoraAcceso grabar(ComputadoraAcceso o) throws DAOException;
	public ComputadoraAcceso modificar(ComputadoraAcceso o) throws DAOException;
	public List<ComputadoraAcceso> getListaPorPk(Object o) throws DAOException;
	public List<ComputadoraAcceso> getListaPorCabecera(Object o) throws DAOException;
}
