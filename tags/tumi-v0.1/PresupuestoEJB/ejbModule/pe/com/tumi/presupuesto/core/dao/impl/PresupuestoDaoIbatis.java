package pe.com.tumi.presupuesto.core.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.presupuesto.core.dao.PresupuestoDao;
import pe.com.tumi.presupuesto.core.domain.Presupuesto;
import pe.com.tumi.presupuesto.core.domain.PresupuestoId;

public class PresupuestoDaoIbatis extends TumiDaoIbatis implements PresupuestoDao {
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Presupuesto grabar(Presupuesto o) throws DAOException {
		Presupuesto dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Presupuesto modificar(Presupuesto o) throws DAOException {
		Presupuesto dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	 * Recupera Presupuesto por Presupuesto Id
	 */
	public List<Presupuesto> getListaPorPK(Object o) throws DAOException{
		List<Presupuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21.10.2013
	 * Recupera lista por filtros de búsqueda
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Presupuesto> getListaPresupuestoPorFiltros(Object o) throws DAOException{
		List<Presupuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPresupuestoPorFiltros", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista por Rango de Fechas
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Presupuesto> getListaPorRangoFechas(Object o) throws DAOException{
		List<Presupuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorRangoFechas", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista de meses existente en el año base por rango de fechas
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Presupuesto> getMesesDelAnioBase(Object o) throws DAOException{
		List<Presupuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMesesDelAnioBase", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}

	//AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	public Presupuesto eliminar(PresupuestoId o) throws DAOException {
		Presupuesto dto = null;
		try{
			dto = new Presupuesto();
			dto.setId(o);
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista de Presupuestos del Año Base
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Presupuesto> getLstPstoAnioBase(Object o) throws DAOException{
		List<Presupuesto> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getLstPstoAnioBase", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
