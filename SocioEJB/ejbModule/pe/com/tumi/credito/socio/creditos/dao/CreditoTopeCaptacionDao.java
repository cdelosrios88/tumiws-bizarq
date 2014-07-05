package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CondicionHabil;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoTopeCaptacionDao extends TumiDao{
	public CreditoTopeCaptacion grabar(CreditoTopeCaptacion o) throws DAOException;
	public CreditoTopeCaptacion modificar(CreditoTopeCaptacion o) throws DAOException;
	public List<CreditoTopeCaptacion> getListaCreditoTopeCaptacionPorPK(Object o) throws DAOException;
	public List<CreditoTopeCaptacion> getListaCreditoTopeCaptacionTipoMinMaxPorPK(Object o) throws DAOException;
	public List<CreditoTopeCaptacion> getListaCondicionHabilPorPKCredito(Object o) throws DAOException;
	public void deletePorPk(Object o)throws DAOException;
	
}
