package pe.com.tumi.credito.socio.estructura.dao.impl;

import java.util.List;

import pe.com.tumi.credito.socio.estructura.dao.DescuentoDao;
import pe.com.tumi.credito.socio.estructura.domain.Descuento;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;

public class DescuentoDaoIbatis extends TumiDaoIbatis implements DescuentoDao{
	
	public Descuento grabar(Descuento o) throws DAOException {
		Descuento dto = null;
		try{
			getSqlMapClientTemplate().insert(getNameSpace() + ".grabar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public Descuento modificar(Descuento o) throws DAOException {
		Descuento dto = null;
		try{
			getSqlMapClientTemplate().update(getNameSpace() + ".modificar", o);
			dto = o;
		}catch(Exception e) {
			throw new DAOException(e);
		}
		return dto;
	}
	
	public List<Descuento> getListaDescuentoPorPK(Object o) throws DAOException{
		List<Descuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorPk", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21-08-2013
	 * OBTENER COLUMNAS DESCUENTO TERCEROS (ESTADO DE CUENTA - TAB TERCEROS)
	 **/
	public List<Descuento> getListaColumnasPorPeriodoModalidadYDni(Object o) throws DAOException{
		List<Descuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaColumnasPorPeriodoModalidadYDni", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21-08-2013
	 * OBTENER FILAS DESCUENTO TERCEROS (ESTADO DE CUENTA - TAB TERCEROS)
	 **/
	public List<Descuento> getListaFilasPorPeriodoModalidadYDni(Object o) throws DAOException{
		List<Descuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaFilasPorPeriodoModalidadYDni", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 26-08-2013
	 * OBTENER MONTOS TOTALES POR EMPRESA-DESCUENTA Y PERIODO (ESTADO DE CUENTA - TAB TERCEROS)
	 **/
	public List<Descuento> getMontoTotalPorNomCptoYPeriodo(Object o) throws DAOException{
		List<Descuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMontTotPorNomCptoYPeriodo", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * 
	 * @param o
	 * @return
	 * @throws DAOException
	 */
	public List<Descuento> getListaPorAdminPadron(Object o) throws DAOException{
		List<Descuento> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getListaPorAdminPadron", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	
	
	
	
}