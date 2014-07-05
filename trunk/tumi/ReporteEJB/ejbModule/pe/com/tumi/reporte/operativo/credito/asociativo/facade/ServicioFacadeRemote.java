package pe.com.tumi.reporte.operativo.credito.asociativo.facade;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Servicio;

@Remote
public interface ServicioFacadeRemote {
	public List<Servicio> getListaCantidadProyectadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoIndicador, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException;
	//Captaciones
	public List<Servicio> getListaCantidadCaptacionesEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin, String strRefinanciados) throws BusinessException;
	public List<Servicio> getCaptacionesEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException;
	public List<Servicio> getListaSaldoServicios(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoServicio, Integer intSubTipoServicio) throws BusinessException;
	public List<Servicio> getListaPrimerDscto(Integer intParaTipoSucursal, Integer intIdSucursal, 
			Integer intIdSubSucursal, Integer intTipoServicio, Integer intTipoCreditoEmpresa,
			Integer intIdTipoFecha, Integer intIdMes, Integer intIdAnio,
			BigDecimal bdMontoMin, BigDecimal bdMontoMax, String strReprestamo, 
			Integer intIdTipoDiferencia) throws BusinessException;
}
