package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantiaTipoValorVenta;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoGarantiaTipoValorVentaDao extends TumiDao{
	public CreditoGarantiaTipoValorVenta grabar(CreditoGarantiaTipoValorVenta o) throws DAOException;
	public CreditoGarantiaTipoValorVenta modificar(CreditoGarantiaTipoValorVenta o) throws DAOException;
	public List<CreditoGarantiaTipoValorVenta> getListaTipoValorVentaPorPK(Object o) throws DAOException;
	public List<CreditoGarantiaTipoValorVenta> getListaTipoValorVentaPorPKCreditoGarantia(Object o) throws DAOException;
}
