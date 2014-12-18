package pe.com.tumi.reporte.operativo.tesoreria.facade;
/* -----------------------------------------------------------------------------------------------------------
* Modificaciones
* Motivo                      Fecha            Nombre                      Descripción
* -----------------------------------------------------------------------------------------------------------
* REQ14-009       			15/12/2014     Christian De los Ríos        Creaciòn de componente         
*/

import java.util.List;


import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.tesoreria.domain.EgresoFondoFijo;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;

/**
 * Interface de acceso Facade Remote
 * 
 * @author Bizarq
 */
public interface MovEgresoFacadeRemote {
	
	/**
	 * Metodo que recupera la lista de Egresos realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param intSucursal, Identificador de la sucursal.
	 * @param intAnio, Anio de consulta.
	 * @param intTipoFondoFijo, Indicador de tipo de fondo fijo.
	 * @return Lista de entidades del tipo EgresosCaja.
	 * 
	 * @throws BusinessException
	 */
	public List<MovEgreso> getListFondoFijo(int intSucursal,int intAnio, int intTipoFondoFijo) throws BusinessException;
	
	 /**
	 * Metodo que recupera la lista de Egresos de monto fijo realizados a caja
	 * segun los filtros ingresados.
	 * 
	 * @param objMovEgreso, Entidad de tipo Moviemiento Egreso.
	 * @return Lista de entidades del tipo EgresoFondoFijo.
	 * 
	 * @throws BusinessException
	 */
	public List<EgresoFondoFijo> getEgresos (MovEgreso objMovEgreso) throws BusinessException;
	public MovEgreso getFondoFijoHead  (MovEgreso objMovEgreso) throws BusinessException;
}
