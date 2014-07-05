package pe.com.tumi.reporte.operativo.credito.asociativo.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.credito.asociativo.bo.PlanillaDescuentoBO;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.PlanillaDescuento;

@Stateless
public class PlanillaDescuentoFacade extends TumiFacade implements PlanillaDescuentoFacadeRemote, PlanillaDescuentoFacadeLocal {
	protected  static Logger log = Logger.getLogger(PlanillaDescuentoFacade.class);
	private PlanillaDescuentoBO boPlanillaDescuento = (PlanillaDescuentoBO)TumiFactory.get(PlanillaDescuentoBO.class);

	public List<PlanillaDescuento> getListaPlanillaDescuento(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoSocio, Integer intModalidad, Integer intTipoSucursal, Integer intPeriodo) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try {
			lista = boPlanillaDescuento.getListaPlanillaDescuento(intIdSucursal, intIdSubSucursal, intTipoSocio, intModalidad, intTipoSucursal, intPeriodo);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaPendienteCobro(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoSocio, Integer intModalidad, Date dtFecCorte) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try {
			lista = boPlanillaDescuento.getListaPendienteCobro(intIdSucursal, intIdSubSucursal, intTipoSocio, intModalidad, dtFecCorte);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaMorosidadPlanilla(Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSucursal) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try {
			lista = boPlanillaDescuento.getListaMorosidadPlanilla(intIdSucursal, intIdSubSucursal, intPeriodo, intTipoSucursal);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaSocioDiferencia(Integer intIdSucursal, Integer intIdSubSucursal, Integer intIdUnidadEjecutora, Integer intPeriodo, Integer intTipoDiferencia, Integer intIdTipoSocio, Integer intIdModalidad) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try {
			lista = boPlanillaDescuento.getListaSocioDiferencia(intIdSucursal, intIdSubSucursal, intIdUnidadEjecutora, intPeriodo, intTipoDiferencia, intIdTipoSocio, intIdModalidad);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<PlanillaDescuento> getListaEntidad(Integer intIdSubSucursal) throws BusinessException{
		List<PlanillaDescuento> lista = null;
		try {
			lista = boPlanillaDescuento.getListaEntidad(intIdSubSucursal);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}