package pe.com.tumi.reporte.operativo.credito.asociativo.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.PlanillaDescuentoDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.PlanillaDescuento;

public class PlanillaDescuentoDaoIbatis extends TumiDaoIbatis implements PlanillaDescuentoDao{

	public List<PlanillaDescuento> getListaPlanillaDescuento(Object o) throws DAOException {
		List<PlanillaDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPlanillaDescuento", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaPendienteCobro(Object o) throws DAOException {
		List<PlanillaDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getPendienteCobro", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaMorosidadPlanilla(Object o) throws DAOException {
		List<PlanillaDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMorosidadPlanilla", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaSocioDiferencia(Object o) throws DAOException {
		List<PlanillaDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getSocioDiferencia", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaEntidad(Object o) throws DAOException {
		List<PlanillaDescuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListEntidad", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}