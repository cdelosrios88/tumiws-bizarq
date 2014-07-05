package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public interface CtaConceptoDetalleBloqueoDao extends TumiDao{
	public List<CuentaConcepto> getListaPorEmpresaYCuenta(Object o) throws DAOException;
	//cobranza
	public List<CuentaConcepto> getListaEmpresaCuentaOfCob(Object o) throws DAOException;
}
