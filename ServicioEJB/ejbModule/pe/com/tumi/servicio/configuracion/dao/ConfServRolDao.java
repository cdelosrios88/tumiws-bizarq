package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServRol;

public interface ConfServRolDao extends TumiDao{
	public ConfServRol grabar(ConfServRol o) throws DAOException;
	public ConfServRol modificar(ConfServRol o) throws DAOException;
	public List<ConfServRol> getListaPorPk(Object o) throws DAOException;	
	public List<ConfServRol> getListaPorCabecera(Object o) throws DAOException;
}
