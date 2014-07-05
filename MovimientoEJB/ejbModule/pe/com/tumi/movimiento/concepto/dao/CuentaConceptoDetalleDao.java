package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;

public interface CuentaConceptoDetalleDao extends TumiDao{
	public CuentaConceptoDetalle grabar(CuentaConceptoDetalle o) throws DAOException;
	public CuentaConceptoDetalle modificar(CuentaConceptoDetalle o) throws DAOException;
	public List<CuentaConceptoDetalle> getListaPorPK(Object o) throws DAOException;
	public List<CuentaConceptoDetalle> getListaPorPKConcepto(Object o) throws DAOException;
	public List<CuentaConceptoDetalle> getListaPorPKYTipoConcepto(Object o) throws DAOException;
	public void modificarDetallePorConcepto(CuentaConceptoDetalle o) throws DAOException;
	public List<CuentaConceptoDetalle> getListaPorPKCuentaYTipoConcepto(Object o) throws DAOException;
	public List<CuentaConceptoDetalle> getMaxCuentaConceptoDetPorPKCuentaConcepto(Object o) throws DAOException;
	
	//flyalico
	public List<CuentaConceptoDetalle> getCuentaConceptoDetofCobranza(Object o) throws DAOException;
}
