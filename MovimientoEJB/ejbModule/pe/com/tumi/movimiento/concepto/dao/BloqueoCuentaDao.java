package pe.com.tumi.movimiento.concepto.dao;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.BloqueoCuenta;

public interface BloqueoCuentaDao extends TumiDao{
	public BloqueoCuenta grabar(BloqueoCuenta o) throws DAOException;
	public List<BloqueoCuenta> getListaPorNroCuentaYMotivo(Object o) throws DAOException;
	public List<BloqueoCuenta> getListaPorNroCuenta(Object o) throws DAOException;
	//rVillarreal
	public List<BloqueoCuenta> getListaFondoSepelio(Object o) throws DAOException;
}
