package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServCaptacion;

public interface ConfServCaptacionDao extends TumiDao{
	public ConfServCaptacion grabar(ConfServCaptacion o) throws DAOException;
	public ConfServCaptacion modificar(ConfServCaptacion o) throws DAOException;
	public List<ConfServCaptacion> getListaPorPk(Object o) throws DAOException;
	public List<ConfServCaptacion> getListaPorCabecera(Object o) throws DAOException;
}
