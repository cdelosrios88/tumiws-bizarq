package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.CondicionHabilTipoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CondicionLaboralTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoTipoGarantiaCondicionLaboralDao extends TumiDao{
	public CondicionLaboralTipoGarantia grabar(CondicionLaboralTipoGarantia o) throws DAOException;
	public CondicionLaboralTipoGarantia modificar(CondicionLaboralTipoGarantia o) throws DAOException;
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboralTipoGarantiaPorPK(Object o) throws DAOException;
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboralTipoGarantiaPorCreditoTipoGarantia(Object o) throws DAOException;
	public List<CondicionLaboralTipoGarantia> getListaCondicionLaboralPorCreditoTipoGarantia(Object o) throws DAOException;
}
