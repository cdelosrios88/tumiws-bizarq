package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoTipoGarantiaDao extends TumiDao{
	public CreditoTipoGarantia grabar(CreditoTipoGarantia o) throws DAOException;
	public CreditoTipoGarantia modificar(CreditoTipoGarantia o) throws DAOException;
	public List<CreditoTipoGarantia> getListaCreditoPorPK(Object o) throws DAOException;
	public List<CreditoTipoGarantia> getListaCreditoTipoGarantiaPorPKCreditoGarantia(Object o) throws DAOException;
}
