package pe.com.tumi.servicio.configuracion.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoEmpresa;

public interface ConfServCreditoEmpresaDao extends TumiDao{
	public ConfServCreditoEmpresa grabar(ConfServCreditoEmpresa o) throws DAOException;
	public ConfServCreditoEmpresa modificar(ConfServCreditoEmpresa o) throws DAOException;
	public List<ConfServCreditoEmpresa> getListaPorPk(Object o) throws DAOException;
	public List<ConfServCreditoEmpresa> getListaPorCabecera(Object o) throws DAOException;
	
}
