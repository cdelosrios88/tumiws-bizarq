package pe.com.tumi.reporte.operativo.tesoreria.dao.impl;
/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			15/12/2014     Christian De los Ríos        Creaciòn de componente         
*/

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.impl.TumiDaoIbatis;
import pe.com.tumi.reporte.operativo.tesoreria.dao.MovEgresoDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgresoDetalle;

/**
 * Clase que gestiona el acceso a datos mediante el framework Ibatis
 * 
 * @author Bizarq
 */
public class MovEgresoDaoIbatis extends TumiDaoIbatis implements MovEgresoDao {
	
	/**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param o, Objeto de tipo EgresoCaja.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws DAOException
	 */
	public List<MovEgreso> getListFondoFijo(Object o) throws DAOException {
		List<MovEgreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getCadenaCajaChica", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun el id.
	 * 
	 * @param o, Objeto de tipo EgresoCaja.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws DAOException
	 */
	public List<MovEgreso> getListEgresoById(Object o) throws DAOException {
		List<MovEgreso> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getEgresoHead", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun el id.
	 * 
	 * @param o, Objeto de tipo EgresoCaja.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws DAOException
	 */
	public List<MovEgresoDetalle> getListEgresoDetalleById(Object o) throws DAOException {
		List<MovEgresoDetalle> lista = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getEgresoBody", o);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	
	/**
	 * Metodo que recupera la lista de EgresosFondoFijo realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param objMovEgreso, Objeto de tipo MovimientoEgreso.
	 * @return Lista de entidades del tipo EgresoFondoFijo.
	 * 
	 * @throws DAOException
	 */
	public List<EgresoFondoFijo> getEgresos (Object objMovEgreso) throws DAOException {
		List<EgresoFondoFijo> lista  = null;
		try{
			lista = (List) getSqlMapClientTemplate().queryForList(getNameSpace() + ".getMovimientosCajaChicaBody", objMovEgreso);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return lista;
	}
	public MovEgreso getFondoFijoHead (Object objMovEgreso) throws DAOException {
		MovEgreso objMovEgresoHead  = null;
		try{
			objMovEgresoHead = (MovEgreso) getSqlMapClientTemplate().queryForObject(getNameSpace() + ".getMovimientosCajaChicaHead", objMovEgreso);
		}catch(Exception e) {
			throw new DAOException (e);
		}
		return objMovEgresoHead;
	}
}
