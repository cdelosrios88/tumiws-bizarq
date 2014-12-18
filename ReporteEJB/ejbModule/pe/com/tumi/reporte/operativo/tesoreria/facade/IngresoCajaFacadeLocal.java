package pe.com.tumi.reporte.operativo.tesoreria.facade;
/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			15/12/2014     Christian De los Ríos        Creaciòn de componente         
*/

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

/**
 * Interface de acceso local a entidad Facade
 * 
 * @author Bizarq
 */
public interface IngresoCajaFacadeLocal {
	
	/**
	 * Metodo para retornar la lista de ingresos a caja.
	 * 
	 * @param o, Object con el Bean de IngresoCaja.
	 * @return Lista de componentes IngresoCaja.
	 * 
	 * @throws BusinessException
	 */
	public List<IngresoCaja> getListaIngresosByTipoIngreso(IngresoCaja o) throws BusinessException;
}
