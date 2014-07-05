package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionDiasCobranza;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoExcepcionDiasCobranzaDao extends TumiDao{
	public CreditoExcepcionDiasCobranza grabar(CreditoExcepcionDiasCobranza o) throws DAOException;
	public CreditoExcepcionDiasCobranza modificar(CreditoExcepcionDiasCobranza o) throws DAOException;
	public List<CreditoExcepcionDiasCobranza> getListaCreditoExcepcionDiasCobranzaPorPK(Object o) throws DAOException;
	public List<CreditoExcepcionDiasCobranza> getListaCreditoExcepcionDiasCobranzaPorPKCreditoExcepcion(Object o) throws DAOException;
}
