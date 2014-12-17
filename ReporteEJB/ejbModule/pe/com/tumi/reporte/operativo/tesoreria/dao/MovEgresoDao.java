package pe.com.tumi.reporte.operativo.tesoreria.dao;

import java.util.List;

import javax.faces.model.SelectItem;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;

public interface MovEgresoDao extends TumiDao {
	public List<SelectItem> getListFondoFijo(Object o) throws DAOException;
}
