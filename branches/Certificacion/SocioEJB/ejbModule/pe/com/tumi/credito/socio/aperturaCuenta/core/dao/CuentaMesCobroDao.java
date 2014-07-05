package pe.com.tumi.credito.socio.aperturaCuenta.core.dao;

import java.util.List;

import pe.com.tumi.credito.socio.aperturaCuenta.core.domain.CuentaMesCobro;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CuentaMesCobroDao extends TumiDao{
	public CuentaMesCobro grabar(CuentaMesCobro o) throws DAOException;
	public CuentaMesCobro modificar(CuentaMesCobro o) throws DAOException;
	public List<CuentaMesCobro> getListaCuentaMesCobroPorPK(Object o) throws DAOException;
}