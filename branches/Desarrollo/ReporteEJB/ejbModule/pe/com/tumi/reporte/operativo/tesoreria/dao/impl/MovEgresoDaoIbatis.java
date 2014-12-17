package pe.com.tumi.reporte.operativo.tesoreria.dao.impl;

import java.util.List;

import javax.faces.model.SelectItem;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Asociativo;
import pe.com.tumi.reporte.operativo.tesoreria.dao.IngresoCajaDao;
import pe.com.tumi.reporte.operativo.tesoreria.dao.MovEgresoDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

public class MovEgresoDaoIbatis extends TumiDaoIbatis implements MovEgresoDao {
	public List<SelectItem> getListFondoFijo(Object o) throws DAOException {
		List<SelectItem> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCadenaCajaChica", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
