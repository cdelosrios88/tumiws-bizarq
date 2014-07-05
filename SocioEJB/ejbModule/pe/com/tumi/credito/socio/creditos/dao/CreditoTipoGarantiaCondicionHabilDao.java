package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoTipoGarantiaCondicionHabilDao extends TumiDao{
	public CondicionHabilTipoGarantia grabar(CondicionHabilTipoGarantia o) throws DAOException;
	public CondicionHabilTipoGarantia modificar(CondicionHabilTipoGarantia o) throws DAOException;
	public List<CondicionHabilTipoGarantia> getListaCondicionHabilTipoGarantiaPorPK(Object o) throws DAOException;
	public List<CondicionHabilTipoGarantia> getListaCondicionHabilTipoGarantiaPorCreditoTipoGarantia(Object o) throws DAOException;
	public List<CondicionHabilTipoGarantia> getListaCondicionHabilPorCreditoTipoGarantia(Object o) throws DAOException;
}
