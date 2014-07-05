package pe.com.tumi.presupuesto.indicador.bo;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.presupuesto.indicador.dao.IndicadorDao;
import pe.com.tumi.presupuesto.indicador.dao.impl.IndicadorDaoIbatis;
import pe.com.tumi.presupuesto.indicador.domain.Indicador;
import pe.com.tumi.presupuesto.indicador.domain.IndicadorId;

public class IndicadorBO {
	protected  static Logger log = Logger.getLogger(IndicadorDao.class);
	private IndicadorDao dao = (IndicadorDao)TumiFactory.get(IndicadorDaoIbatis.class);
	
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Indicador grabar(Indicador o) throws BusinessException{
		Indicador dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Indicador modificar(Indicador o) throws BusinessException{
		Indicador dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}	
	/**
	 * AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	 * Recupera Indicador por Indicador Id
	 * @param pId
	 * @return
	 * @throws BusinessException
	 */
	public Indicador getIndicadorPorPK(IndicadorId pId) throws BusinessException{
		Indicador domain = null;
		List<Indicador> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaIndicadorPk", pId.getIntEmpresaIndicadorPk());
			mapa.put("intPeriodoIndicador", pId.getIntPeriodoIndicador());
			mapa.put("intMesIndicador", pId.getIntMesIndicador());
			mapa.put("intParaTipoIndicador", pId.getIntParaTipoIndicador());
			mapa.put("intEmpresaSucursalPk", pId.getIntEmpresaSucursalPk());
			mapa.put("intIdSucursal", pId.getIntIdSucursal());
			mapa.put("intIdSubSucursal", pId.getIntIdSubSucursal());	
			lista = dao.getListaPorPK(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
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
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaIndicadorPk", pId.getIntEmpresaIndicadorPk());
			mapa.put("intPeriodoIndicador", pId.getIntPeriodoIndicador());
			mapa.put("intMesIndicador", pId.getIntMesIndicador());
			mapa.put("intParaTipoIndicador", pId.getIntParaTipoIndicador());
			mapa.put("intEmpresaSucursalPk", pId.getIntEmpresaSucursalPk());
			mapa.put("intIdSucursal", pId.getIntIdSucursal());
			mapa.put("intIdSubSucursal", pId.getIntIdSubSucursal());			
			lista = dao.getListaIndicadorPorFiltros(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//AUTOR Y FECHA CREACION: JCHAVEZ / 09.10.2013
	public Indicador eliminar(IndicadorId o) throws BusinessException{
		Indicador dto = null;
		try{
			dto = dao.eliminar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public List<Indicador> getListaPorRangoFechas(Integer intEmpresaIndicadorPk, Integer intPeriodoIndicador, Integer intMesDesde, Integer intMesHasta) throws BusinessException{
		List<Indicador> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaIndicadorPk", intEmpresaIndicadorPk);
			mapa.put("intPeriodoIndicador", intPeriodoIndicador);
			mapa.put("intMesDesde", intMesDesde);
			mapa.put("intMesHasta", intMesHasta);
			lista = dao.getListaPorRangoFechas(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
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
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresaIndicadorPk", intEmpresaIndicadorPk);
			mapa.put("intPeriodoIndicador", intPeriodoIndicador);
			mapa.put("intMesDesde", intMesDesde);
			mapa.put("intMesHasta", intMesHasta);
			lista = dao.getMesesDelAnioBase(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
