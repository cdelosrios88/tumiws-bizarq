package pe.com.tumi.reporte.operativo.tesoreria.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.tesoreria.dao.IngresoCajaDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

public class IngresoCajaDaoIbatis extends TumiDaoIbatis implements IngresoCajaDao{
	public List<IngresoCaja> getListaIngresosByTipoIngreso(Object o) throws DAOException {
		List<IngresoCaja> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListIngresosByTipo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
