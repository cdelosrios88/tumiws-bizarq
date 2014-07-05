package pe.com.tumi.reporte.operativo.credito.asociativo.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Asociativo;

@Remote
public interface AsociativoFacadeRemote {
	public List<Asociativo> getListaCantidadProyectadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoIndicador, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException;
	//Captaciones
	public List<Asociativo> getListaCantidadCaptacionesEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException;
	public List<Asociativo> getCaptacionesEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException;
	public List<Asociativo> getListaCantidadCaptacionesAltas(String strTabla, String strColumna, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException;
	//Renuncias
	public List<Asociativo> getListaCantidadRenunciasEjecuadas(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException;
	public List<Asociativo> getRenunciasEjecutadasDetalle(Integer intEmpresa, Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSocio, Integer intModalidad) throws BusinessException;
	public List<Asociativo> getListaCantidadRenunciasBajas(String strTabla, String strColumna, Integer intPeriodoInicio, Integer intPeriodoFin) throws BusinessException;
	//Convenios
	public List<Asociativo> getListaConveniosPorSucursal(Integer intEmpresa, Integer intIdSucursal) throws BusinessException;
	//Agregado por cdelosrios, 28/11/2013
	public List<Asociativo> getListaExpedientePrevision(
			Integer intIdSucursal, 
			Integer intIdSubSucursal,
			Integer intTipoFiltro,
			String strTipoFiltro,
			Integer intIdTipoVinculo,
			Integer intIdEstadoSolicitud,
			Integer intIdEstadoPago,
			Date dtFechaSolicitud,
			Date dtFechaSolicitudHasta) throws BusinessException;
	//Fin agregado por cdelosrios, 28/11/2013
}
