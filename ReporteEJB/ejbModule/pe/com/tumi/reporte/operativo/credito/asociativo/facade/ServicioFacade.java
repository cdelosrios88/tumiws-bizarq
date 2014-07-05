package pe.com.tumi.reporte.operativo.credito.asociativo.facade;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.credito.asociativo.bo.ServicioBO;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Servicio;

@Stateless
public class ServicioFacade extends TumiFacade implements ServicioFacadeRemote, ServicioFacadeLocal {
	protected  static Logger log = Logger.getLogger(ServicioFacade.class);
	private ServicioBO boServicio = (ServicioBO)TumiFactory.get(ServicioBO.class);

	public List<Servicio> getListaCantidadProyectadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoIndicador, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Servicio> lista = null;
		try {
			lista = boServicio.getListaCantidadProyectadas(intEmpresa, intIdSucursal, intIdSubSucursal, intTipoIndicador, intPeriodoInicio, intPeriodoFin);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Servicio> getListaCantidadCaptacionesEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin, String strRefinanciados) throws BusinessException{
		List<Servicio> lista = null;
		try {
			lista = boServicio.getListaCantidadCaptacionesEjecuadas(intEmpresa, intIdSucursal, intIdSubSucursal, intPeriodoInicio, intPeriodoFin, strRefinanciados);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Servicio> getCaptacionesEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException{
		List<Servicio> lista = null;
		try {
			lista = boServicio.getCaptacionesEjecutadasDetalle(intEmpresa, intIdSucursal, intIdSubSucursal, intPeriodo, intTipoSocio, intModalidad);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Servicio> getListaSaldoServicios(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoServicio, Integer intSubTipoServicio) throws BusinessException{
		List<Servicio> lista = null;
		try {
			lista = boServicio.getListaSaldoServicios(intIdSucursal, intIdSubSucursal, intTipoServicio, intSubTipoServicio);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Servicio> getListaPrimerDscto(Integer intParaTipoSucursal, Integer intIdSucursal, 
			Integer intIdSubSucursal, Integer intTipoServicio, Integer intTipoCreditoEmpresa,
			Integer intIdTipoFecha, Integer intIdMes, Integer intIdAnio,
			BigDecimal bdMontoMin, BigDecimal bdMontoMax, String strReprestamo, 
			Integer intIdTipoDiferencia) throws BusinessException{
		List<Servicio> lista = null;
		try {
			lista = boServicio.getListaPrimerDscto(
					intParaTipoSucursal, intIdSucursal, intIdSubSucursal, 
					intTipoServicio, intTipoCreditoEmpresa,
					intIdTipoFecha, intIdMes, intIdAnio, 
					bdMontoMin, bdMontoMax, strReprestamo, intIdTipoDiferencia);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
