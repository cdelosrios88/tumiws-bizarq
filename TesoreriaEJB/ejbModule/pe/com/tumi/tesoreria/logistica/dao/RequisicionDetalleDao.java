package pe.com.tumi.tesoreria.logistica.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.logistica.domain.RequisicionDetalle;

public interface RequisicionDetalleDao extends TumiDao{
	public RequisicionDetalle grabar(RequisicionDetalle pDto) throws DAOException;
	public RequisicionDetalle modificar(RequisicionDetalle o) throws DAOException;
	public List<RequisicionDetalle> getListaPorPk(Object o) throws DAOException;
	public List<RequisicionDetalle> getListaPorRequisicion(Object o) throws DAOException;
}	
