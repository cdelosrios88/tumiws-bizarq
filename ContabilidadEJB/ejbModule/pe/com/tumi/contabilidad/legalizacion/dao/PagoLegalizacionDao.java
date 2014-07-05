package pe.com.tumi.contabilidad.legalizacion.dao;

import java.util.List;

import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacion;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface PagoLegalizacionDao extends TumiDao{

	public PagoLegalizacion grabar(PagoLegalizacion o) throws DAOException;
	public PagoLegalizacion modificar(PagoLegalizacion o) throws DAOException;
	public List<PagoLegalizacion> getListaPorPk(Object o) throws DAOException;
	public List<PagoLegalizacion> getListaPorLibroLegalizacion(Object o) throws DAOException;
}
