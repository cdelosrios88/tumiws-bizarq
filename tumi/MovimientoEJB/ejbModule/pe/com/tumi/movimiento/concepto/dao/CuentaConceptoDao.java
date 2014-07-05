package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public interface CuentaConceptoDao extends TumiDao{
	public CuentaConcepto grabar(CuentaConcepto o) throws DAOException;
	public CuentaConcepto modificar(CuentaConcepto o) throws DAOException;
	public List<CuentaConcepto> getListaPorPK(Object o) throws DAOException;
	public List<CuentaConcepto> getListaPorPKCuenta(Object o) throws DAOException;
}
