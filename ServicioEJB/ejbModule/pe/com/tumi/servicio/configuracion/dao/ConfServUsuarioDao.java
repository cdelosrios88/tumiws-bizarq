package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;

public interface ConfServUsuarioDao extends TumiDao{
	public ConfServUsuario grabar(ConfServUsuario o) throws DAOException;
	public ConfServUsuario modificar(ConfServUsuario o) throws DAOException;
	public List<ConfServUsuario> getListaPorPk(Object o) throws DAOException;
	public List<ConfServUsuario> getListaPorCabecera(Object o) throws DAOException;
}
