package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServCancelado;

public interface ConfServCanceladoDao extends TumiDao{
	public ConfServCancelado grabar(ConfServCancelado o) throws DAOException;
	public ConfServCancelado modificar(ConfServCancelado o) throws DAOException;
	public List<ConfServCancelado> getListaPorPk(Object o) throws DAOException;
	public List<ConfServCancelado> getListaPorCabecera(Object o) throws DAOException;
}
