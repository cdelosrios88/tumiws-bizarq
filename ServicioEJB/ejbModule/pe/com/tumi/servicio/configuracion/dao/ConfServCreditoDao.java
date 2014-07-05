package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;

public interface ConfServCreditoDao extends TumiDao{
	public ConfServCredito grabar(ConfServCredito o) throws DAOException;
	public ConfServCredito modificar(ConfServCredito o) throws DAOException;
	public List<ConfServCredito> getListaPorPk(Object o) throws DAOException;
	public List<ConfServCredito> getListaPorCabecera(Object o) throws DAOException;
}
