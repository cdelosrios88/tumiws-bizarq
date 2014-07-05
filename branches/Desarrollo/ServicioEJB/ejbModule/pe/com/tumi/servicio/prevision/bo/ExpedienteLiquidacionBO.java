package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.ExpedienteLiquidacionDao;
import pe.com.tumi.servicio.prevision.dao.impl.ExpedienteLiquidacionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionComp;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.composite.RequisitoLiquidacionComp;

public class ExpedienteLiquidacionBO {
	private ExpedienteLiquidacionDao dao = (ExpedienteLiquidacionDao)TumiFactory.get(ExpedienteLiquidacionDaoIbatis.class);
	
	public ExpedienteLiquidacion grabar(ExpedienteLiquidacion o) throws BusinessException{
		ExpedienteLiquidacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedienteLiquidacion modificar(ExpedienteLiquidacion o) throws BusinessException{
		ExpedienteLiquidacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ExpedienteLiquidacion getPorPk(ExpedienteLiquidacionId pId) throws BusinessException{
		ExpedienteLiquidacion domain = null;
		List<ExpedienteLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			lista = dao.getListaPorPk(mapa);
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
	
	public ExpedienteLiquidacion getPorPk(Integer intPersEmpresaPk, Integer intItemExpediente) throws BusinessException{
		ExpedienteLiquidacion domain = null;
		List<ExpedienteLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", intPersEmpresaPk);
			mapa.put("intItemExpediente", intItemExpediente);
			lista = dao.getListaPorPk(mapa);
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
	
	public List<ExpedienteLiquidacion> getPorIdEmpresa(Integer intPersEmpresaPk) throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", intPersEmpresaPk);
			mapa.put("intItemExpediente", null);
			lista = dao.getListaPorPk(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacion> getListaCompleta() throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", null);
			mapa.put("intItemExpediente", null);
			lista = dao.getListaCompleta(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacion> getListaCompletaFiltros(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			//mapa.put("intPersEmpresaPk", null);
			mapa.put("intItemExpediente", expedienteLiquidacion.getId().getIntItemExpediente());
			mapa.put("intParaSubTipoOperacion", expedienteLiquidacion.getIntParaSubTipoOperacion());
			lista = dao.getListaCompletaFiltros(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ExpedienteLiquidacion> getListaPorEmpresaYCuenta(Integer intEmpresa, Integer intCuenta) throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresa", intEmpresa);
			mapa.put("intCuenta", intCuenta);
			lista = dao.getListaPorEmpresaYCuenta(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/**
	 * Recupera los expedientes de liquidacion segun filtros de busqueda
	 * @param expCmp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteLiquidacion> getListaBusqExpLiqFiltros(ExpedienteLiquidacionComp expCmp) throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusqTipo", 			expCmp.getIntBusquedaTipo());
			mapa.put("strBusqCadena", 			expCmp.getStrBusqCadena());
			mapa.put("strBusqNroSol", 			expCmp.getStrBusqNroSol());
			mapa.put("intBusqSucursal", 		expCmp.getIntBusqSucursal());
			mapa.put("intBusqEstado", 			expCmp.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", 	expCmp.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", 	expCmp.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqTipoLiquidacion", 	expCmp.getIntBusqTipoLiquidacion());
		   	 
			lista = dao.getListaBusqExpLiqFiltros(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	/**
	 * Recupera los expedientes de liquidacion para autorizacion segun filtros de busqueda
	 * @param expCmp
	 * @return
	 * @throws BusinessException
	 */
	public List<ExpedienteLiquidacion> getListaBusqAutLiqFiltros(ExpedienteLiquidacionComp expCmp) throws BusinessException{
		List<ExpedienteLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intBusqTipo", 			expCmp.getIntBusquedaTipo());
			mapa.put("strBusqCadena", 			expCmp.getStrBusqCadena());
			mapa.put("strBusqNroSol", 			expCmp.getStrBusqNroSol());
			mapa.put("intBusqSucursal", 		expCmp.getIntBusqSucursal());
			mapa.put("intBusqEstado", 			expCmp.getIntBusqEstado());
			mapa.put("dtBusqFechaEstadoDesde", 	expCmp.getDtBusqFechaEstadoDesde());
			mapa.put("dtBusqFechaEstadoHasta", 	expCmp.getDtBusqFechaEstadoHasta());
			mapa.put("intBusqTipoLiquidacion", 	expCmp.getIntBusqTipoLiquidacion());
		   	 
			lista = dao.getListaBusqAutLiqFiltros(mapa);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

	public List<RequisitoLiquidacionComp> getRequisitoGiroLiquidacion(ExpedienteLiquidacion expLiq) throws BusinessException{
		List<RequisitoLiquidacionComp> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa",			expLiq.getId().getIntPersEmpresaPk());
//			mapa.put("intParaTipoOperacion", 	expCred.getIntParaTipoOperacion());	
			mapa.put("intParaSubTipoOperacion", 		expLiq.getIntParaSubTipoOperacion());
			lista = dao.getRequisitoGiroLiquidacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
}