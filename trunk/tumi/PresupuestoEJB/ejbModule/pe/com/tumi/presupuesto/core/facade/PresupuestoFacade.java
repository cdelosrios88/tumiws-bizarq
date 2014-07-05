package pe.com.tumi.presupuesto.core.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.presupuesto.core.bo.PresupuestoBO;
import pe.com.tumi.presupuesto.core.domain.Presupuesto;
import pe.com.tumi.presupuesto.core.domain.PresupuestoId;
import pe.com.tumi.presupuesto.core.service.PresupuestoService;

@Stateless
public class PresupuestoFacade extends TumiFacade implements PresupuestoFacadeRemote, PresupuestoFacadeLocal {
	
	protected  static Logger log = Logger.getLogger(PresupuestoFacade.class);
	private PresupuestoBO boPresupuesto = (PresupuestoBO)TumiFactory.get(PresupuestoBO.class);
	private PresupuestoService presupuestoService = (PresupuestoService)TumiFactory.get(PresupuestoService.class);
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Presupuesto grabarPresupuesto(Presupuesto presupuesto)throws BusinessException{
		Presupuesto dto = null;
		try{
			dto = presupuestoService.grabarPresupuesto(presupuesto);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Presupuesto modificarPresupuesto(Presupuesto presupuesto)throws BusinessException{
		Presupuesto dto = null;
		try{
			dto = boPresupuesto.modificar(presupuesto);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 21.10.2013
	 * Recupera lista de Presupuestos por empresa, periodo, cuenta, sucursal o subsucursal
	 * @param pId
	 * @param intCboTipoCuentaBusq
	 * @return
	 * @throws BusinessException
	 */
	public List<Presupuesto> getListaPresupuestoPorFiltros(PresupuestoId pId, Integer intTipoCuentaBusq) throws BusinessException{
		List<Presupuesto> lista = null;
		try {
			lista = boPresupuesto.getListaPresupuestoPorFiltros(pId,intTipoCuentaBusq);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Presupuesto getPresupuestoPorPK(PresupuestoId pId) throws BusinessException{
		Presupuesto dto = null;
		try {
			dto = boPresupuesto.getPresupuestoPorPK(pId);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista por Rango de Fechas
	 * @param intEmpresaPresupuestoPk
	 * @param intPeriodoPresupuesto
	 * @param intMesDesde
	 * @param intMesHasta
	 * @return
	 * @throws BusinessException
	 */
	public List<Presupuesto> getListaPorRangoFechas(Integer intEmpresaPresupuestoPk, Integer intPeriodoPresupuesto, Integer intMesDesde, Integer intMesHasta) throws BusinessException{
		List<Presupuesto> lista = null;
		try {
			lista = boPresupuesto.getListaPorRangoFechas(intEmpresaPresupuestoPk,intPeriodoPresupuesto,intMesDesde,intMesHasta);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista de meses existente en el año base por rango de fechas
	 * @param intEmpresaPresupuestoPk
	 * @param intPeriodoPresupuesto
	 * @param intMesDesde
	 * @param intMesHasta
	 * @return
	 * @throws BusinessException
	 */
	public List<Presupuesto> getMesesDelAnioBase(Integer intEmpresaPresupuestoPk, Integer intPeriodoPresupuesto, Integer intMesDesde, Integer intMesHasta) throws BusinessException{
		List<Presupuesto> lista = null;
		try {
			lista = boPresupuesto.getMesesDelAnioBase(intEmpresaPresupuestoPk,intPeriodoPresupuesto,intMesDesde,intMesHasta);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	//AUTOR Y FECHA CREACION: JCHAVEZ / 25.10.2013
	public Presupuesto eliminarPresupuesto(PresupuestoId o)throws BusinessException{
		Presupuesto dto = null;
		try{
			dto = boPresupuesto.eliminar(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return dto;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 28.10.2013
	 * Recupera lista de Presupuestos del Año Base por todas las sucursales y subsucursales
	 * ACTIVAS y todas las cuentas que existan tanto en el Año Base como en el Año Proyectado
	 * que esten ACTIVAS y que reciban movimiento (PLCU_MOVIMIENTO_N=1)
	 * @param intEmpresa
	 * @param intPeriodo
	 * @param intMesDesde
	 * @param intMesHasta
	 * @return
	 * @throws BusinessException
	 */
	public List<Presupuesto> getLstPstoAnioBase(Integer intEmpresa, Integer intPeriodo, Integer intMesDesde, Integer intMesHasta) throws BusinessException {
		List<Presupuesto> lista = null;
		try {
			lista = boPresupuesto.getLstPstoAnioBase(intEmpresa,intPeriodo,intMesDesde,intMesHasta);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
