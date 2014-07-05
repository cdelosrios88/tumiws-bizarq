package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.PlantillaDetalle;

public interface PlantillaDetalleDao extends TumiDao{
	public PlantillaDetalle grabar(PlantillaDetalle o) throws DAOException;
	public PlantillaDetalle modificar(PlantillaDetalle o) throws DAOException;
	public List<PlantillaDetalle> getListaPorPk(Object o) throws DAOException;
	public List<PlantillaDetalle> getListaTodos(Object o) throws DAOException;
}
