package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;

public interface ConciliacionDao extends TumiDao{
	public Conciliacion grabar(Conciliacion pDto) throws DAOException;
	public Conciliacion modificar(Conciliacion o) throws DAOException;
	public List<Conciliacion> getListaPorPk(Object o) throws DAOException;
}