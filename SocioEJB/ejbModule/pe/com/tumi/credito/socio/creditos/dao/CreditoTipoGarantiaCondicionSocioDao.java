package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CondicionSocioTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoTipoGarantiaCondicionSocioDao extends TumiDao{
	public CondicionSocioTipoGarantia grabar(CondicionSocioTipoGarantia o) throws DAOException;
	public CondicionSocioTipoGarantia modificar(CondicionSocioTipoGarantia o) throws DAOException;
	public List<CondicionSocioTipoGarantia> getListaCondicionSocioTipoGarantiaPorPK(Object o) throws DAOException;
	public List<CondicionSocioTipoGarantia> getListaCondicionSocioTipoGarantiaPorCreditoTipoGarantia(Object o) throws DAOException;
	public List<CondicionSocioTipoGarantia> getListaCondicionSocioPorCreditoTipoGarantia(Object o) throws DAOException;
}
