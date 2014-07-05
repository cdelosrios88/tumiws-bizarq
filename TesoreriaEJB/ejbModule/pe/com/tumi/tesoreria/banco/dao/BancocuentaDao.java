package pe.com.tumi.tesoreria.banco.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.banco.domain.Bancocuenta;

public interface BancocuentaDao extends TumiDao{
	public Bancocuenta grabar(Bancocuenta pDto) throws DAOException;
	public Bancocuenta modificar(Bancocuenta o) throws DAOException;
	public List<Bancocuenta> getListaPorPk(Object o) throws DAOException;
	public List<Bancocuenta> getListaPorBancoFondo(Object o) throws DAOException;
	public List<Bancocuenta> getListaPorPlanCuenta(Object o) throws DAOException;
}	
