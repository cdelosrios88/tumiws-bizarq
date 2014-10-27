/**
* Resumen.
* Objeto: ConciliacionFacade
* Descripción:  Facade principal del proceso de conciliacion bancaria.
* Fecha de Creación: 18/10/2014.
* Requerimiento de Creación: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.tesoreria.conciliacion.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionBO;
import pe.com.tumi.tesoreria.egreso.bo.EgresoBO;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.ConciliacionDetalle;
import pe.com.tumi.tesoreria.egreso.domain.Egreso;
import pe.com.tumi.tesoreria.egreso.domain.comp.ConciliacionComp;
import pe.com.tumi.tesoreria.ingreso.bo.IngresoBO;
import pe.com.tumi.tesoreria.ingreso.domain.Ingreso;


public class ConciliacionService {

	protected static Logger log = Logger.getLogger(pe.com.tumi.tesoreria.egreso.service.ConciliacionService.class);

	private ConciliacionBO boConciliacion = (ConciliacionBO)TumiFactory.get(ConciliacionBO.class);
	private IngresoBO boIngreso = (IngresoBO)TumiFactory.get(IngresoBO.class);
	private EgresoBO boEgreso = (EgresoBO)TumiFactory.get(EgresoBO.class);
	
	
	
	
	
	/**
	 * Recupera los expedientes de credito segun filtros de busqueda
	 * @param expedienteCompBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<Conciliacion> getListFilter(ConciliacionComp conciliacionCompBusq)throws BusinessException{
		List<Conciliacion> lstConciliacion = null;
		try {

			lstConciliacion = boConciliacion.getListFilter(conciliacionCompBusq);
		
		} catch (Exception e) {
			log.error("Error en getListaBusqConciliacionFiltros --> "+e);
		}
		return lstConciliacion;
	}
	

	

	
	/**
	 * 
	 */
	public List<ConciliacionDetalle> buscarRegistrosConciliacion(Conciliacion conciliacion)throws BusinessException{
		List<ConciliacionDetalle> listaConciliacionDetalle = new ArrayList<ConciliacionDetalle>();
		try{
			Ingreso ingresoFiltro = new Ingreso();
			ingresoFiltro.getId().setIntIdEmpresa((conciliacion.getBancoCuenta().getId().getIntEmpresaPk()));
			ingresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			ingresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			ingresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Ingreso> listaIngreso = boIngreso.getListaParaBuscar(ingresoFiltro);
			for(Ingreso ingreso : listaIngreso){
				
			}
			
			Egreso egresoFiltro = new Egreso();
			egresoFiltro.getId().setIntPersEmpresaEgreso(conciliacion.getBancoCuenta().getId().getIntEmpresaPk());
			egresoFiltro.setIntParaDocumentoGeneral(conciliacion.getIntParaDocumentoGeneralFiltro());
			egresoFiltro.setIntItemBancoFondo((conciliacion.getBancoCuenta().getId().getIntItembancofondo()));
			egresoFiltro.setIntItemBancoCuenta(conciliacion.getBancoCuenta().getId().getIntItembancocuenta());
			List<Egreso> listaEgreso = boEgreso.getListaPorBuscar(egresoFiltro, null, null);
			
		}catch (Exception e) {
			throw new BusinessException(e);
		}
		return listaConciliacionDetalle;
	}
	
	
	
}
