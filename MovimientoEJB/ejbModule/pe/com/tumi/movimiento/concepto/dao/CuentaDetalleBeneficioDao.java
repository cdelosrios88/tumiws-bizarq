package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaDetalleBeneficio;

public interface CuentaDetalleBeneficioDao extends TumiDao{
	public CuentaDetalleBeneficio grabar(CuentaDetalleBeneficio o) throws DAOException;
	public CuentaDetalleBeneficio modificar(CuentaDetalleBeneficio o) throws DAOException;
	public List<CuentaDetalleBeneficio> getListaPorPK(Object o) throws DAOException;
	public List<CuentaDetalleBeneficio> getListaPorPKConcepto(Object o) throws DAOException;
	public List<CuentaDetalleBeneficio> getListaPorPKCuenta(Object o) throws DAOException;
	public void modificarPorBeneficiario(CuentaDetalleBeneficio o) throws DAOException;
}
