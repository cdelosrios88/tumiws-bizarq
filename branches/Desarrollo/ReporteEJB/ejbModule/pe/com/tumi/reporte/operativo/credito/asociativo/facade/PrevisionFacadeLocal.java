package pe.com.tumi.reporte.operativo.credito.asociativo.facade;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Prevision;

@Local
public interface PrevisionFacadeLocal {
	public List<Prevision> getListaFondoSepelio(
			Integer intIdDocumentoGeneral,
			Integer intIdSucursal, 
			Integer intIdSubSucursal,
			Integer intTipoFiltro,
			String strTipoFiltro,
			Integer intIdTipoVinculo,
			Integer intIdEstadoSolicitud,
			Integer intIdEstadoPago,
			Date dtFechaSolicitudDesde,
			Date dtFechaSolicitudHasta) throws BusinessException;
	
	public List<Prevision> getListaFondoRetiro(
			Integer intIdDocumentoGeneral,
			Integer intIdSucursal, 
			Integer intIdSubSucursal,
			Integer intTipoFiltro,
			String strTipoFiltro,
			Integer intIdTipoVinculo,
			Integer intIdEstadoSolicitud,
			Integer intIdEstadoPago,
			Date dtFechaSolicitudDesde,
			Date dtFechaSolicitudHasta) throws BusinessException;
	
	public List<Prevision> getListaAes(
			Integer intIdDocumentoGeneral,
			Integer intIdSucursal, 
			Integer intIdSubSucursal,
			Integer intTipoFiltro,
			String strTipoFiltro,
			Integer intIdTipoVinculo,
			Integer intIdEstadoSolicitud,
			Integer intIdEstadoPago,
			Date dtFechaSolicitudDesde,
			Date dtFechaSolicitudHasta) throws BusinessException;
}
