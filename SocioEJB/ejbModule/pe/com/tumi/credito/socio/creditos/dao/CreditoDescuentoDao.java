package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoDescuentoDao extends TumiDao{
	public CreditoDescuento grabar(CreditoDescuento o) throws DAOException;
	public CreditoDescuento modificar(CreditoDescuento o) throws DAOException;
	public List<CreditoDescuento> getListaCreditoDescuentoPorPK(Object o) throws DAOException;
	public List<CreditoDescuento> getListaCreditoDescuentoPorPKCredito(Object o) throws DAOException;
	public CreditoDescuento eliminarCreditoDescuento(CreditoDescuento o) throws DAOException;
}
