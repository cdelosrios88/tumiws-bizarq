package pe.com.tumi.riesgo.cartera.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.riesgo.cartera.domain.Especificacion;

public interface EspecificacionDao extends TumiDao{
	public Especificacion grabar(Especificacion o) throws DAOException;
	public Especificacion modificar(Especificacion o) throws DAOException;
	public List<Especificacion> getListaPorPk(Object o) throws DAOException;
	public List<Especificacion> getListaPorIntItemCartera(Object o) throws DAOException;
}
