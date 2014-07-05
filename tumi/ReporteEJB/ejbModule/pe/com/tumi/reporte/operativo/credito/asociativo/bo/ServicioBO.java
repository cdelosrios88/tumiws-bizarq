package pe.com.tumi.reporte.operativo.credito.asociativo.bo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.ServicioDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.impl.ServicioDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Servicio;

public class ServicioBO {
	protected  static Logger log = Logger.getLogger(ServicioDao.class);
	private ServicioDao dao = (ServicioDao)TumiFactory.get(ServicioDaoIbatis.class);

	public List<Servicio> getListaCantidadProyectadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoIndicador, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Servicio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intTipoIndicador", intTipoIndicador);
			mapa.put("intPeriodoInicio", intPeriodoInicio);
			mapa.put("intPeriodoFin", intPeriodoFin);
			lista = dao.getListaCantidadProyectadas(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Servicio> getListaCantidadCaptacionesEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin, String strRefinanciados) throws BusinessException{
		List<Servicio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intPeriodoInicio", intPeriodoInicio);
			mapa.put("intPeriodoFin", intPeriodoFin);
			mapa.put("strRefinanciados", strRefinanciados);
			lista = dao.getListaCantidadCaptacionesEjecuadas(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Servicio> getCaptacionesEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException{
		List<Servicio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intPeriodo", intPeriodo);
			mapa.put("intTipoSocio", intTipoSocio);
			mapa.put("intModalidad", intModalidad);
			lista = dao.getCaptacionesEjecutadasDetalle(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Servicio> getListaSaldoServicios(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoServicio, Integer intSubTipoServicio) throws BusinessException{
		List<Servicio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intTipoServicio", intTipoServicio);
			mapa.put("intSubTipoServicio", intSubTipoServicio);
			lista = dao.getListaSaldoServicios(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Servicio> getListaPrimerDscto(
			Integer intParaTipoSucursal, Integer intIdSucursal, 
			Integer intIdSubSucursal, Integer intTipoServicio, Integer intTipoCreditoEmpresa,
			Integer intIdTipoFecha, Integer intIdMes, Integer intIdAnio,
			BigDecimal bdMontoMin, BigDecimal bdMontoMax, String strReprestamo,
			Integer intIdTipoDiferencia) throws BusinessException{
		List<Servicio> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdTipoSocio", intParaTipoSucursal);
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intTipoServicio", intTipoServicio);
			mapa.put("intSubTipoServicio", intTipoCreditoEmpresa);
			mapa.put("intIdTipoFecha", intIdTipoFecha);
			mapa.put("intIdMes", intIdMes);
			mapa.put("intIdAnio", intIdAnio);
			mapa.put("bdMontoMin", bdMontoMin);
			mapa.put("bdMontoMax", bdMontoMax);
			mapa.put("strReprestamo", strReprestamo);
			mapa.put("intIdTipoDiferencia", intIdTipoDiferencia);
			lista = dao.getListaPrimerDscto(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
