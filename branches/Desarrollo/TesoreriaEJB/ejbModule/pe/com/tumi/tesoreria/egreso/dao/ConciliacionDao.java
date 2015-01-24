/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-006       			01/11/2014     		Bisarq        Nuevos Metodos        
*/
package pe.com.tumi.tesoreria.egreso.dao;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.persistencia.dao.TumiDao;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;

public interface ConciliacionDao extends TumiDao{
	public Conciliacion grabar(Conciliacion pDto) throws DAOException;
	public Conciliacion modificar(Conciliacion o) throws DAOException;
	public List<Conciliacion> getListaPorPk(Object o) throws DAOException;
	/* Inicio: REQ14-006 Bizarq - 26/10/2014 */
	public List<Conciliacion> getListFilter(Object o) throws DAOException;
	public List<Conciliacion> getLastConciliacionByCuenta(Object o) throws DAOException;
	public List<Ingreso> getListaTransCuentaIngreso(Object o) throws DAOException;
	/* Final: REQ14-006 Bizarq - 26/10/2014 */
}