package pe.com.tumi.presupuesto.indicador.facade;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.presupuesto.indicador.bo.IndicadorBO;
import pe.com.tumi.presupuesto.indicador.domain.Indicador;
import pe.com.tumi.presupuesto.indicador.domain.IndicadorId;
import pe.com.tumi.presupuesto.indicador.service.IndicadorService;

@Stateless
public class IndicadorFacade extends TumiFacade implements IndicadorFacadeRemote, IndicadorFacadeLocal {
	
	protected  static Logger log = Logger.getLogger(IndicadorFacade.class);
	private IndicadorBO boIndicador = (IndicadorBO)TumiFactory.get(IndicadorBO.class);
	private IndicadorService indicadorService = (IndicadorService)TumiFactory.get(IndicadorService.class);
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Indicador grabarIndicador(Indicador indicador)throws BusinessException{
		Indicador dto = null;
		try{
			dto = indicadorService.grabarIndicador(indicador);
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
	public Indicador modificarIndicador(Indicador indicador)throws BusinessException{
		Indicador dto = null;
		try{
			dto = boIndicador.modificar(indicador);
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
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 10.10.2013
	 * Recupera lista de Indicador por empresa, periodo, mes, tipo indicador, sucursal o subsucursal
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public List<Indicador> getListaIndicadorPorFiltros(IndicadorId pId) throws BusinessException{
		List<Indicador> lista = null;
		try {
			lista = boIndicador.getListaIndicadorPorFiltros(pId);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 10.10.2013
	 * Recupera Indicador por Indicador Id
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public Indicador getIndicadorPorPK(IndicadorId pId) throws BusinessException{
		Indicador dto = null;
		try {
			dto = boIndicador.getIndicadorPorPK(pId);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 16.10.2013
	public Indicador eliminarIndicador(IndicadorId o)throws BusinessException{
		Indicador dto = null;
		try{
			dto = boIndicador.eliminar(o);
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
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 24.10.2013
	 * Recupera lista por Rango de Fechas
	 * @param intEmpresaIndicadorPk
	 * @param intPeriodoIndicador
	 * @param intMesDesde
	 * @param intMesHasta
	 * @return
	 * @throws BusinessException
	 */
	public List<Indicador> getListaPorRangoFechas(Integer intEmpresaIndicadorPk, Integer intPeriodoIndicador, Integer intMesDesde, Integer intMesHasta) throws BusinessException{
		List<Indicador> lista = null;
		try {
			lista = boIndicador.getListaPorRangoFechas(intEmpresaIndicadorPk,intPeriodoIndicador,intMesDesde,intMesHasta);
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
	 * @param intEmpresaIndicadorPk
	 * @param intPeriodoIndicador
	 * @param intMesDesde
	 * @param intMesHasta
	 * @return
	 * @throws BusinessException
	 */
	public List<Indicador> getMesesDelAnioBase(Integer intEmpresaIndicadorPk, Integer intPeriodoIndicador, Integer intMesDesde, Integer intMesHasta) throws BusinessException{
		List<Indicador> lista = null;
		try {
			lista = boIndicador.getMesesDelAnioBase(intEmpresaIndicadorPk,intPeriodoIndicador,intMesDesde,intMesHasta);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
