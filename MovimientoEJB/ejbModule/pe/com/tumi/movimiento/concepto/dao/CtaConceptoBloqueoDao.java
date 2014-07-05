package pe.com.tumi.movimiento.concepto.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.movimiento.concepto.domain.CuentaConcepto;

public interface CtaConceptoBloqueoDao extends TumiDao{
	public List<CuentaConcepto> getListaPorEmpresaYCuenta(Object o) throws DAOException;
}
