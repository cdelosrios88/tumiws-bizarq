/**
* Resumen.
* Objeto: MayorizacionFacade
* Descripción:  Facade principal del proceso de mayorización.
* Fecha de Creación: 17/09/2014.
* Requerimiento de Creación: REQ14-004
* Autor: Bizarq
*/

package pe.com.tumi.contabilidad.core.facade;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.framework.negocio.exception.BusinessException;

public interface MayorizacionFacadeRemote {
	public Integer processMayorizacion(LibroMayor o)throws BusinessException;
	public List<LibroMayor> buscarLibroMayoreHistorico(LibroMayor o) throws BusinessException;
}
