package pe.com.tumi.reporte.operativo.tesoreria.dao;
/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			15/12/2014     Christian De los Ríos        Creaciòn de componente         
*/

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgresoDetalle;

/**
 * Interface que gestiona el acceso a datos (DAO)
 * 
 * @author Bizarq
 */
public interface MovEgresoDao extends TumiDao {
	
	/**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param o, Objeto de tipo EgresoCaja.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws DAOException
	 */
	public List<MovEgreso> getListFondoFijo(Object o) throws DAOException;
	
	/**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun el id.
	 * 
	 * @param o, Objeto de tipo EgresoCaja.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws DAOException
	 */
	public List<MovEgreso> getListEgresoById(Object o) throws DAOException;
	
	/**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun el id.
	 * 
	 * @param o, Objeto de tipo EgresoCaja.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws DAOException
	 */
	public List<MovEgresoDetalle> getListEgresoDetalleById(Object o) throws DAOException;
	
	/**
	 * Metodo que recupera la lista de EgresosFondoFijo realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param objMovEgreso, Objeto de tipo MovimientoEgreso.
	 * @return Lista de entidades del tipo EgresoFondoFijo.
	 * 
	 * @throws DAOException
	 */
	public List<EgresoFondoFijo> getEgresos (Object objMovEgreso) throws DAOException;
	public MovEgreso getFondoFijoHead (Object objMovEgreso) throws DAOException;
}
