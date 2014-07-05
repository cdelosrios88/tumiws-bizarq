package pe.com.tumi.credito.socio.convenio.dao;

import java.util.List;

import pe.com.tumi.credito.socio.convenio.domain.PoblacionDetalle;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PoblacionDetalleDao extends TumiDao{
	public PoblacionDetalle grabar(PoblacionDetalle o) throws DAOException;
	public PoblacionDetalle modificar(PoblacionDetalle o) throws DAOException;
	public List<PoblacionDetalle> getListaPoblacionDetallePorPK(Object o) throws DAOException;
	public List<PoblacionDetalle> getListaPoblacionDetallePorPKPoblacion(Object o) throws DAOException;
}
