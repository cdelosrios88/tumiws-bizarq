package pe.com.tumi.tesoreria.ingreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.ingreso.domain.DepositoIngreso;

public interface DepositoIngresoDao extends TumiDao{
	public DepositoIngreso grabar(DepositoIngreso o) throws DAOException;
	public DepositoIngreso modificar(DepositoIngreso o) throws DAOException;
	public List<DepositoIngreso> getListaPorPk(Object o) throws DAOException;
	public List<DepositoIngreso> getListaPorIngreso(Object o) throws DAOException;
	public List<DepositoIngreso> getListaPorIngresoDeposito(Object o) throws DAOException;
}