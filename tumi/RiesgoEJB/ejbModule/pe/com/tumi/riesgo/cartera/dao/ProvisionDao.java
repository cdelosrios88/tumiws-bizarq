package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.Provision;

public interface ProvisionDao extends TumiDao{
	public Provision grabar(Provision o) throws DAOException;
	public Provision modificar(Provision o) throws DAOException;
	public List<Provision> getListaPorPk(Object o) throws DAOException;
	public List<Provision> getListaPorEspecificacion(Object o) throws DAOException;
	
}
