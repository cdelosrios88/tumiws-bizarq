package pe.com.tumi.presupuesto.indicador.dao.impl;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.presupuesto.indicador.dao.IndicadorDao;
import pe.com.tumi.presupuesto.indicador.domain.Indicador;
import pe.com.tumi.presupuesto.indicador.domain.IndicadorId;

public class IndicadorDaoIbatis extends TumiDaoIbatis implements IndicadorDao {
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Indicador grabar(Indicador o) throws DAOException {
		Indicador dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}

	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Indicador modificar(Indicador o) throws DAOException {
		Indicador dto = null;
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
	 * Recupera Indicador por Indicador Id
	 */
	public List<Indicador> getListaPorPK(Object o) throws DAOException{
		List<Indicador> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPK", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 10.10.2013
	 * Recupera lista de Indicador por empresa, periodo, mes, tipo indicador, sucursal o subsucursal
	 */
	public List<Indicador> getListaIndicadorPorFiltros(Object o) throws DAOException{
		List<Indicador> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaIndicadorPorFiltros", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	//AUTOR Y FECHA CREACION: JCHAVEZ / 16.10.2013
	public Indicador eliminar(IndicadorId o) throws DAOException {
		Indicador dto = null;
		try{
			dto = new Indicador();
			dto.setId(o);
			getSqlMapClientTemplate().delete(getNameSpace() + ".eliminar", o);
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista por Rango de Fechas
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Indicador> getListaPorRangoFechas(Object o) throws DAOException{
		List<Indicador> lista = null;
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
	public List<Indicador> getMesesDelAnioBase(Object o) throws DAOException{
		List<Indicador> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMesesDelAnioBase", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
}
