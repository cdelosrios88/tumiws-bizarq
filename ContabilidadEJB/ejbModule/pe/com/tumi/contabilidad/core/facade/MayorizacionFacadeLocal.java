/**
* Resumen.
* Objeto: MayorizacionFacade
* Descripci�n:  Facade principal del proceso de mayorizaci�n.
* Fecha de Creaci�n: 17/09/2014.
* Requerimiento de Creaci�n: REQ14-004
* Autor: Bizarq
*/
package pe.com.tumi.contabilidad.core.facade;

import java.util.List;

import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.framework.negocio.exception.BusinessException;

public interface MayorizacionFacadeLocal {
	public Integer processMayorizacion(Integer intPeriodo)throws BusinessException;
	//Inicio: REQ14-004 - bizarq - 16/09/2014
	public List<LibroMayor> buscarLibroMayoreHistorico(LibroMayor o) throws BusinessException;
	//Fin: REQ14-004 - bizarq - 16/09/2014
}
