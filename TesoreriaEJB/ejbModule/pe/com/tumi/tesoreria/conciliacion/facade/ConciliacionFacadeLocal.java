/**
* Resumen.
* Objeto: ConciliacionFacade
* Descripción:  Facade principal del proceso de conciliacion bancaria.
* Fecha de Creación: 18/10/2014.
* Requerimiento de Creación: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.tesoreria.conciliacion.facade;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionId;
import pe.com.tumi.tesoreria.egreso.domain.comp.ConciliacionComp;

public interface ConciliacionFacadeLocal {

	public Conciliacion getPorPk(ConciliacionId conciliacionId) throws BusinessException;
	public List<Conciliacion> getListFilter(ConciliacionComp conciliacionCompBusq)throws BusinessException;
	public List<ConciliacionDetalle> buscarRegistrosConciliacion(Conciliacion conciliacion)throws BusinessException;
	public Conciliacion getConciliacionEdit(ConciliacionId pId) throws BusinessException;
	//public Conciliacion getUltimaConciliacion(ConciliacionComp conciliacionCompBusq)throws BusinessException;
	//public List<ConciliacionDetalle> getListConcilDet(ConciliacionDetalle pConcilDet) throws BusinessException;
	//public Conciliacion getConciliacionConDetalleValidado(Conciliacion pConciliacion) throws BusinessException;
	public void anularConciliacion(ConciliacionComp pConciliacionCompAnul) throws BusinessException;
	public void grabarConciliacionDiaria(Conciliacion conciliacion) throws BusinessException;
	
	
}
