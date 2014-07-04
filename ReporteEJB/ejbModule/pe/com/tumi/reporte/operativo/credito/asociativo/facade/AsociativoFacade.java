package pe.com.tumi.reporte.operativo.credito.asociativo.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.credito.asociativo.bo.AsociativoBO;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Asociativo;

@Stateless
public class AsociativoFacade extends TumiFacade implements AsociativoFacadeRemote, AsociativoFacadeLocal {
	protected  static Logger log = Logger.getLogger(AsociativoFacade.class);
	private AsociativoBO boAsociativo = (AsociativoBO)TumiFactory.get(AsociativoBO.class);	

	public List<Asociativo> getListaCantidadProyectadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoIndicador, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try {
			lista = boAsociativo.getListaCantidadProyectadas(intEmpresa, intIdSucursal, intIdSubSucursal, intTipoIndicador, intPeriodoInicio, intPeriodoFin);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadCaptacionesEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try {
			lista = boAsociativo.getListaCantidadCaptacionesEjecuadas(intEmpresa, intIdSucursal, intIdSubSucursal, intPeriodoInicio, intPeriodoFin);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getCaptacionesEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException{
		List<Asociativo> lista = null;
		try {
			lista = boAsociativo.getCaptacionesEjecutadasDetalle(intEmpresa, intIdSucursal, intIdSubSucursal, intPeriodo, intTipoSocio, intModalidad);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadCaptacionesAltas(String strTabla, String strColumna, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try {
			lista = boAsociativo.getListaCantidadCaptacionesAltas(strTabla,strColumna,intPeriodoInicio,intPeriodoFin);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadRenunciasEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try {
			lista = boAsociativo.getListaCantidadRenunciasEjecuadas(intEmpresa, intIdSucursal, intIdSubSucursal, intPeriodoInicio, intPeriodoFin);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getRenunciasEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException{
		List<Asociativo> lista = null;
		try {
			lista = boAsociativo.getRenunciasEjecutadasDetalle(intEmpresa, intIdSucursal, intIdSubSucursal, intPeriodo, intTipoSocio, intModalidad);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<Asociativo> getListaCantidadRenunciasBajas(String strTabla, String strColumna, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException{
		List<Asociativo> lista = null;
		try {
			lista = boAsociativo.getListaCantidadRenunciasBajas(strTabla,strColumna,intPeriodoInicio,intPeriodoFin);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	@Override
	public List<Asociativo> getListaConveniosPorSucursal(Integer intEmpresa, Integer intIdSucursal) throws BusinessException {
		List<Asociativo> lista = null;
		try {
			lista = boAsociativo.getListaConveniosPorSucursal(intEmpresa,intIdSucursal);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	//Agregado por cdelosrios, 28/11/2013
	public List<Asociativo> getListaExpedientePrevision(
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
		try {
			lista = boAsociativo.getListaExpedienteProvision(
					intIdSucursal,intIdSubSucursal,intTipoFiltro,strTipoFiltro,
					intIdTipoVinculo,intIdEstadoSolicitud,intIdEstadoPago,dtFechaSolicitudDesde,dtFechaSolicitudHasta);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	//Fin agregado por cdelosrios, 28/11/2013
}
