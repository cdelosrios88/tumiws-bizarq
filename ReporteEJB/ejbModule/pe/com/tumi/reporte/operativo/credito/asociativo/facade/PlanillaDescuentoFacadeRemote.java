package pe.com.tumi.reporte.operativo.credito.asociativo.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.PlanillaDescuento;

@Remote
public interface PlanillaDescuentoFacadeRemote {
	public List<PlanillaDescuento> getListaPlanillaDescuento(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoSocio, Integer intModalidad, Integer intTipoSucursal, Integer intPeriodo) throws BusinessException;
	public List<PlanillaDescuento> getListaPendienteCobro(Integer intIdSucursal, Integer intIdSubSucursal, Integer intTipoSocio, Integer intModalidad, Date dtFecCorte) throws BusinessException;
	public List<PlanillaDescuento> getListaMorosidadPlanilla(Integer intIdSucursal, Integer intIdSubSucursal, Integer intPeriodo, Integer intTipoSucursal) throws BusinessException;
	public List<PlanillaDescuento> getListaSocioDiferencia(Integer intIdSucursal, Integer intIdSubSucursal, Integer intIdUnidadEjecutora, Integer intPeriodo, Integer intTipoDiferencia, Integer intIdTipoSocio, Integer intIdModalidad) throws BusinessException;
}
