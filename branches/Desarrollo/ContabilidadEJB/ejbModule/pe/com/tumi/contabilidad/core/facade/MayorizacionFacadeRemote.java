/**
* Resumen.
* Objeto: MayorizacionFacade
* Descripción:  Facade principal del proceso de mayorización.
* Fecha de Creación: 17/09/2014.
* Requerimiento de Creación: REQ14-004
* Autor: Bizarq
*/

package pe.com.tumi.contabilidad.core.facade;

import pe.com.tumi.framework.negocio.exception.BusinessException;

public interface MayorizacionFacadeRemote {
	public Integer processMayorizacion(Integer intPeriodo)throws BusinessException;
}
