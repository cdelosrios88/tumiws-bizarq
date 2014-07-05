package pe.com.tumi.reporte.operativo.credito.asociativo.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.PlanillaDescuentoDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.ServicioDao;
import pe.com.tumi.reporte.operativo.credito.asociativo.dao.impl.PlanillaDescuentoDaoIbatis;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.PlanillaDescuento;

public class PlanillaDescuentoBO {
	protected  static Logger log = Logger.getLogger(ServicioDao.class);
	private PlanillaDescuentoDao dao = (PlanillaDescuentoDao)TumiFactory.get(PlanillaDescuentoDaoIbatis.class);

	public List<PlanillaDescuento> getListaPlanillaDescuento(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoSocio, Integer intModalidad, Integer intTipoSucursal, Integer intPeriodo) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intTipoSocio", intTipoSocio);
			mapa.put("intModalidad", intModalidad);
			mapa.put("intTipoSucursal", intTipoSucursal);
			mapa.put("intPeriodo", intPeriodo);
			lista = dao.getListaPlanillaDescuento(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaPendienteCobro(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoSocio, Integer intModalidad, Date dtFecCorte) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intTipoSocio", intTipoSocio);
			mapa.put("intModalidad", intModalidad);
			mapa.put("dtFechaCorte", dtFecCorte);
			lista = dao.getListaPendienteCobro(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaMorosidadPlanilla(Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSucursal) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intPeriodo", intPeriodo);
			mapa.put("intTipoSucursal", intTipoSucursal);
			lista = dao.getListaMorosidadPlanilla(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaSocioDiferencia(Integer intIdSucursal, Integer intIdSubSucursal, Integer intIdUnidadEjecutora, Integer intPeriodo, Integer intTipoDiferencia, Integer intIdTipoSocio, Integer intIdModalidad) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSucursal", intIdSucursal);
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			mapa.put("intIdUnidadEjecutora", intIdUnidadEjecutora);
			mapa.put("intPeriodo", intPeriodo);
			mapa.put("intTipoDiferencia", intTipoDiferencia);
			mapa.put("intIdTipoSocio", intIdTipoSocio);
			mapa.put("intIdModalidad", intIdModalidad);
			lista = dao.getListaSocioDiferencia(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaEntidad(Integer intIdSubSucursal) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intIdSubSucursal", intIdSubSucursal);
			lista = dao.getListaEntidad(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
