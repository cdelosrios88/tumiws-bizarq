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
import pe.com.tumi.reporte.operativo.tesoreria.dao.IngresoCajaDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

/**
 * Clase de acceso a datos para consultar la entidad de IngresoCaja.
 * 
 * @author Bizarq
 */
public class IngresoCajaDaoIbatis extends TumiDaoIbatis implements IngresoCajaDao{
	
	/**
	 * Metodo para retornar la lista de ingresos a caja.
	 * 
	 * @param o, Object con el Bean de IngresoCaja.
	 * @return Lista de componentes IngresoCaja.
	 * 
	 * @throws DAOException
	 */
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
