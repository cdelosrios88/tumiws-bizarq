package pe.com.tumi.reporte.operativo.tesoreria.dao;
/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			15/12/2014     Christian De los Ríos        Creaciòn de componente         
*/

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

/**
 * Interface de acceso al componente DAO.
 * 
 * @author Bizarq
 */
public interface IngresoCajaDao extends TumiDao {
	
	/**
	 * Metodo para retornar la lista de ingresos a caja.
	 * 
	 * @param o, Object con el Bean de IngresoCaja.
	 * @return Lista de componentes IngresoCaja.
	 * 
	 * @throws DAOException
	 */
	public List<IngresoCaja> getListaIngresosByTipoIngreso(Object o) throws DAOException;
}
