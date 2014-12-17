package pe.com.tumi.reporte.operativo.tesoreria.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;

public interface MovEgresoDao extends TumiDao {
	public List<MovEgreso> getListFondoFijo(Object o) throws DAOException;
}
