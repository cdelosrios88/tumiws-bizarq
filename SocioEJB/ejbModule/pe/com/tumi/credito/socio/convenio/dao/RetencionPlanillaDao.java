package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.RetencionPlanilla;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface RetencionPlanillaDao extends TumiDao{
	public RetencionPlanilla grabar(RetencionPlanilla o) throws DAOException;
	public RetencionPlanilla modificar(RetencionPlanilla o) throws DAOException;
	public List<RetencionPlanilla> getListaRetencPllaPorPK(Object o) throws DAOException;
	public List<RetencionPlanilla> getListaRetenPllaPorPKAdenda(Object o) throws DAOException;
}
