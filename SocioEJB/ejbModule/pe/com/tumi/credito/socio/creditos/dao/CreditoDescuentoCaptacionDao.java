package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoCaptacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoDescuentoCaptacionDao extends TumiDao{
	public CreditoDescuentoCaptacion grabar(CreditoDescuentoCaptacion o) throws DAOException;
	public CreditoDescuentoCaptacion modificar(CreditoDescuentoCaptacion o) throws DAOException;
	public List<CreditoDescuentoCaptacion> getListaCreditoDescuentoCaptacionPorPK(Object o) throws DAOException;
	public List<CreditoDescuentoCaptacion> getListaCreditoDescuentoCaptacionPorPKCreditoDescuento(Object o) throws DAOException;
}
