package pe.com.tumi.persona.core.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.persona.core.domain.CuentaBancaria;

public interface CuentaBancariaDao extends TumiDao{
	public CuentaBancaria grabar(CuentaBancaria o) throws DAOException;
	
	public CuentaBancaria modificar(CuentaBancaria o) throws DAOException;
	public List<CuentaBancaria> getListaCuentaBancariaPorPK(Object o) throws DAOException;
	public List<CuentaBancaria> getListaCuentaBancariaPorIdPersona(Object o) throws DAOException;
	public List<CuentaBancaria> getListaPorStrNroCuentaBancaria(Object o) throws DAOException;
}
