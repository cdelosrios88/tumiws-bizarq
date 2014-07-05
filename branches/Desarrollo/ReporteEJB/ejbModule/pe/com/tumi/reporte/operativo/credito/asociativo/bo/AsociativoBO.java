package pe.com.tumi.reporte.operativo.credito.asociativo.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.AsociativoDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.impl.AsociativoDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Asociativo;

public class AsociativoBO {
	protected  static Logger log = Logger.getLogger(AsociativoDao.class);
	private AsociativoDao dao = (AsociativoDao)TumiFactory.get(AsociativoDaoIbatis.class);

	public List<Asociativo> getListaCantidadProyectadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoIndicador, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
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
	
	public List<Asociativo> getListaCantidadCaptacionesEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intPeriodoInicio", intPeriodoInicio);
			mapa.put("intPeriodoFin", intPeriodoFin);
			lista = dao.getListaCantidadCaptacionesEjecuadas(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getCaptacionesEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException{
		List<Asociativo> lista = null;
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
	
	public List<Asociativo> getListaCantidadCaptacionesAltas(String strTabla, String strColumna, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strTabla", strTabla);
			mapa.put("strColumna", strColumna);
			mapa.put("intPeriodoInicio", intPeriodoInicio);
			mapa.put("intPeriodoFin", intPeriodoFin);
			lista = dao.getListaCantidadCaptacionesAltas(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadRenunciasEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intPeriodoInicio", intPeriodoInicio);
			mapa.put("intPeriodoFin", intPeriodoFin);
			lista = dao.getListaCantidadRenunciasEjecuadas(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getRenunciasEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException{
		List<Asociativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intPeriodo", intPeriodo);
			mapa.put("intTipoSocio", intTipoSocio);
			mapa.put("intModalidad", intModalidad);
			lista = dao.getRenunciasEjecutadasDetalle(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadRenunciasBajas(String strTabla, String strColumna, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("strTabla", strTabla);
			mapa.put("strColumna", strColumna);
			mapa.put("intPeriodoInicio", intPeriodoInicio);
			mapa.put("intPeriodoFin", intPeriodoFin);
			lista = dao.getListaCantidadCaptacionesAltas(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaConveniosPorSucursal(Integer intEmpresa, Integer intIdSucursal) throws BusinessException {
		List<Asociativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intEmpresa", intEmpresa);
			mapa.put("intIdSucursal", intIdSucursal);
			lista = dao.getListaConveniosPorSucursal(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Agregado por cdelosrios, 27/11/2013
	public List<Asociativo> getListaExpedienteProvision(
				Integer intIdSucursal, 
				Integer intIdSubSucursal,
				Integer intTipoFiltro,
				String strTipoFiltro,
				Integer intIdTipoVinculo,
				Integer intIdEstadoSolicitud,
				Integer intIdEstadoPago,
				Date dtFechaSolicitudDesde,
				Date dtFechaSolicitudHasta) throws BusinessException {
		List<Asociativo> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal", 		intIdSucursal);
			mapa.put("intIdSubSucursal", 	intIdSubSucursal);
			mapa.put("intTipoFiltro", 		intTipoFiltro);
			mapa.put("strTipoFiltro", 		strTipoFiltro);
			mapa.put("intIdTipoVinculo", 	intIdTipoVinculo);
			mapa.put("intIdEstadoSolicitud",intIdEstadoSolicitud);
			mapa.put("intIdEstadoPago", 	intIdEstadoPago);
			mapa.put("dtFechaSolicitudDesde",dtFechaSolicitudDesde);
			mapa.put("dtFechaSolicitudHasta",dtFechaSolicitudHasta);
			lista = dao.getListaExpedienteProvision(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 27/11/2013
}
