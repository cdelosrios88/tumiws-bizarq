package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoInteresDao extends TumiDao{
	public CreditoInteres grabar(CreditoInteres o) throws DAOException;
	public CreditoInteres modificar(CreditoInteres o) throws DAOException;
	public List<CreditoInteres> getListaCreditoInteresPorPK(Object o) throws DAOException;
	public List<CreditoInteres> getListaCreditoInteresPorPKCredito(Object o) throws DAOException;
	public Object eliminar(Object o) throws DAOException;
}
