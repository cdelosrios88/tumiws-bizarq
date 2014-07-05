package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoGarantiaDao extends TumiDao{
	public CreditoGarantia grabar(CreditoGarantia o) throws DAOException;
	public CreditoGarantia modificar(CreditoGarantia o) throws DAOException;
	public List<CreditoGarantia> getListaCreditoPorPK(Object o) throws DAOException;
	public List<CreditoGarantia> getListaCreditoGarantiaPorPKCreditoGarantia(Object o) throws DAOException;
	public CreditoGarantia eliminarCreditoGarantia(CreditoGarantia o) throws DAOException;
}
