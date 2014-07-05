package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;
import java.util.Map;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.Saldo;

public interface SaldoDao extends TumiDao{
	public Saldo grabar(Saldo saldo) throws DAOException;
	public Saldo modificar(Saldo o) throws DAOException;
	public List<Saldo> getListaPorPk(Object o) throws DAOException;
	public List<Saldo> getListaAnterior(Object o) throws DAOException;
	public List<Saldo> getListaUltimaFechaRegistro(Object o) throws DAOException;
	public List<Saldo> getListaUltimaFechaSaldo(Object o) throws DAOException;
	public List<Saldo> getListaPorBuscar(Object o) throws DAOException;
	public List<Map> getListaFechas(Object o) throws DAOException;
}