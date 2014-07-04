package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionAporteNoTrans;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoExcepcionAporteNoTransDao extends TumiDao{
	public CreditoExcepcionAporteNoTrans grabar(CreditoExcepcionAporteNoTrans o) throws DAOException;
	public CreditoExcepcionAporteNoTrans modificar(CreditoExcepcionAporteNoTrans o) throws DAOException;
	public List<CreditoExcepcionAporteNoTrans> getListaCreditoExcepcionAporteNoTransPorPK(Object o) throws DAOException;
	public List<CreditoExcepcionAporteNoTrans> getListaCreditoExcepcionAporteNoTransPorPKCreditoExcepcion(Object o) throws DAOException;
}
