package pe.com.tumi.reporte.operativo.tesoreria.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

public interface IngresoCajaDao extends TumiDao {
	public List<IngresoCaja> getListaIngresosByTipoIngreso(Object o) throws DAOException;
}
