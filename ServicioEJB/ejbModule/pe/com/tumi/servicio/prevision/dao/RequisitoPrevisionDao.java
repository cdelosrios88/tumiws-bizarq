package pe.com.tumi.servicio.prevision.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;

public interface RequisitoPrevisionDao extends TumiDao{
	public RequisitoPrevision grabar(RequisitoPrevision o) throws DAOException;
	public RequisitoPrevision modificar(RequisitoPrevision o) throws DAOException;
	public List<RequisitoPrevision> getListaPorPk(Object o) throws DAOException;
	public List<RequisitoPrevision> getListaPorExpediente(Object o) throws DAOException;
	//JCHAVEZ 05.02.2014
	public List<RequisitoPrevision> getListaPorPkExpedientePrevisionYRequisitoDetalle(Object o) throws DAOException;
}
