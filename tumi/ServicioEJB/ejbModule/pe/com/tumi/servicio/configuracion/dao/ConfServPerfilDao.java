package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;

public interface ConfServPerfilDao extends TumiDao{
	public ConfServPerfil grabar(ConfServPerfil o) throws DAOException;
	public ConfServPerfil modificar(ConfServPerfil o) throws DAOException;
	public List<ConfServPerfil> getListaPorPk(Object o) throws DAOException;
	public List<ConfServPerfil> getListaPorCabecera(Object o) throws DAOException;
}
