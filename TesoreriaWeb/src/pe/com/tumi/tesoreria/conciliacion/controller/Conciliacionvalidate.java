/**
* Resumen.
* Objeto: Conciliacionvalidate
* Descripci�n:  Clase validadora
* Fecha de Creaci�n: 30/10/2014.
* Requerimiento de Creaci�n: REQ14-006
* Autor: Bizarq
*/
package pe.com.tumi.tesoreria.conciliacion.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.tesoreria.conciliacion.facade.ConciliacionFacadeLocal;
import pe.com.tumi.tesoreria.conciliacion.service.ConciliacionService;
import pe.com.tumi.tesoreria.egreso.bo.ConciliacionBO;
import pe.com.tumi.tesoreria.egreso.domain.Conciliacion;
import pe.com.tumi.tesoreria.egreso.domain.comp.ConciliacionComp;


public class Conciliacionvalidate {
	protected static Logger log;

	private ConciliacionFacadeLocal conciliacionFacade;
	private ConciliacionService conciliacionService;
	private ConciliacionBO conciliacionBO;
	
	
	/**
	 * 
	 */
	public Conciliacionvalidate(){
		log = Logger.getLogger(this.getClass());
		cargarValoresIniciales();
		
	}
	
	/**
	 * 
	 */
	public void cargarValoresIniciales(){
		try {
			conciliacionFacade = (ConciliacionFacadeLocal) EJBFactory.getLocal(ConciliacionFacadeLocal.class);
			conciliacionService = (ConciliacionService)TumiFactory.get(ConciliacionService.class);
			conciliacionBO = (ConciliacionBO)TumiFactory.get(ConciliacionBO.class);
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
			
	}
	
	/**
	 * Valida si ya existe conciliaicon registrada, anulada o conciliaada.
	 * Segun Cuenta Bancaria y Fecha.
	 * @param conciliacionNew
	 * @return
	 */
	public boolean isValidCrearConciliacion(Conciliacion conciliacionNew){
		boolean isValid = true;
		ConciliacionComp concilComp = new ConciliacionComp();
		List<Conciliacion> lstConciliacion= null;
		try {
				concilComp.setIntBusqItemBancoCuenta(conciliacionNew.getIntItemBancoCuenta());
				concilComp.setIntBusqItemBancoFondo(conciliacionNew.getIntItemBancoFondo());
				concilComp.setDtBusqFechaDesde(new Date(conciliacionNew.getTsFechaConciliacion().getTime()));
				concilComp.setDtBusqFechaHasta(new Date(conciliacionNew.getTsFechaConciliacion().getTime()));

				lstConciliacion = conciliacionBO.getListFilter(concilComp);
				if(lstConciliacion!= null && lstConciliacion.size() >0){
					for (Conciliacion conciliacion : lstConciliacion) {
						if(conciliacion.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_REGISTRADO)==0
							||conciliacion.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_ANULADO)==0
							||conciliacion.getIntParaEstado().compareTo(Constante.INT_EST_CONCILIACION_CONCILIADO)==0){
								isValid = false;
							}
					}
				}
				
		} catch (BusinessException e) {
			e.printStackTrace();
		}

		return isValid;
	}
	
	
	/**
	 * Valida que al momento de Grabar o Grabar COncilaicion diara, se haya seleccionado cuenta bancaria y que existan
	 * registros de conciliaicon detalle.
	 * @param conciliacion
	 * @return
	 */
	public boolean procedeAccion(Conciliacion conciliacion){
		boolean isProcede = false;
		if(conciliacion == null 
			|| conciliacion.getListaConciliacionDetalle() == null 
			|| conciliacion.getListaConciliacionDetalle().size()==0
			|| conciliacion.getBancoCuenta() == null){
			return true;	
		}
		return isProcede;
	}
	
	
	
	
	
}
