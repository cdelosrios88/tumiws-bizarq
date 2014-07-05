package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoExcepcionDao extends TumiDao{
	public CreditoExcepcion grabar(CreditoExcepcion o) throws DAOException;
	public CreditoExcepcion modificar(CreditoExcepcion o) throws DAOException;
	public List<CreditoExcepcion> getListaCreditoExcepcionPorPK(Object o) throws DAOException;
	public List<CreditoExcepcion> getListaCreditoExcepcionPorPKCredito(Object o) throws DAOException;
	public CreditoExcepcion eliminarCreditoExcepcion(CreditoExcepcion o) throws DAOException;
}
