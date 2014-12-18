package pe.com.tumi.reporte.operativo.tesoreria.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.tesoreria.dao.MovEgresoDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgresoDetalle;

public class MovEgresoDaoIbatis extends TumiDaoIbatis implements MovEgresoDao {
	public List<MovEgreso> getListFondoFijo(Object o) throws DAOException {
		List<MovEgreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCadenaCajaChica", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<MovEgreso> getListEgresoById(Object o) throws DAOException {
		List<MovEgreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getEgresoHead", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<MovEgresoDetalle> getListEgresoDetalleById(Object o) throws DAOException {
		List<MovEgresoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getEgresoBody", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<EgresoFondoFijo> getEgresos (Object objMovEgreso) throws DAOException {
		List<EgresoFondoFijo> lista  = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMovimientosCajaChicaBody", objMovEgreso);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
