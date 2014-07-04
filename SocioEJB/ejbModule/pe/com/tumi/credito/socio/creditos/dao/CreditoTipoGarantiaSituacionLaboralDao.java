package pe.com.tumi.credito.socio.creditos.dao;

import java.util.List;

import pe.com.tumi.credito.socio.creditos.domain.SituacionLaboralTipoGarantia;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface CreditoTipoGarantiaSituacionLaboralDao extends TumiDao{
	public SituacionLaboralTipoGarantia grabar(SituacionLaboralTipoGarantia o) throws DAOException;
	public SituacionLaboralTipoGarantia modificar(SituacionLaboralTipoGarantia o) throws DAOException;
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboralTipoGarantiaPorPK(Object o) throws DAOException;
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboralTipoGarantiaPorCreditoTipoGarantia(Object o) throws DAOException;
	public List<SituacionLaboralTipoGarantia> getListaSituacionLaboralPorCreditoTipoGarantia(Object o) throws DAOException;
}
