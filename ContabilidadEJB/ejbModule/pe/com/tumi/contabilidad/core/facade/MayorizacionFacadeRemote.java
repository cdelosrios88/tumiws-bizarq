/**
* Resumen.
* Objeto: MayorizacionFacade
* Descripci�n:  Facade principal del proceso de mayorizaci�n.
* Fecha de Creaci�n: 17/09/2014.
* Requerimiento de Creaci�n: REQ14-004
* Autor: Bizarq
*/

package pe.com.tumi.contabilidad.core.facade;

import pe.com.tumi.framework.negocio.exception.BusinessException;

public interface MayorizacionFacadeRemote {
	public Integer processMayorizacion(Integer intPeriodo)throws BusinessException;
}
