package pe.com.tumi.reporte.operativo.credito.asociativo.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.reporte.operativo.credito.asociativo.bo.PrevisionBO;
import pe.com.tumi.reporte.operativo.credito.asociativo.domain.Prevision;

@Stateless
public class PrevisionFacade extends TumiFacade implements PrevisionFacadeRemote, PrevisionFacadeLocal {
	protected  static Logger log = Logger.getLogger(PrevisionFacade.class);
	private PrevisionBO boPrevision = (PrevisionBO)TumiFactory.get(PrevisionBO.class);	

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
			Date dtFechaSolicitudHasta) throws BusinessException {
		List<Prevision> lista = null;
		try {
			lista = boPrevision.getListaFondoSepelio(
					intIdDocumentoGeneral,intIdSucursal,intIdSubSucursal,intTipoFiltro,strTipoFiltro,
					intIdTipoVinculo,intIdEstadoSolicitud,intIdEstadoPago,dtFechaSolicitudDesde,dtFechaSolicitudHasta);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
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
			Date dtFechaSolicitudHasta) throws BusinessException {
		List<Prevision> lista = null;
		try {
			lista = boPrevision.getListaFondoRetiro(
					intIdDocumentoGeneral,intIdSucursal,intIdSubSucursal,intTipoFiltro,strTipoFiltro,
					intIdTipoVinculo,intIdEstadoSolicitud,intIdEstadoPago,dtFechaSolicitudDesde,dtFechaSolicitudHasta);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
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
			Date dtFechaSolicitudHasta) throws BusinessException {
		List<Prevision> lista = null;
		try {
			lista = boPrevision.getListaAes(
					intIdDocumentoGeneral,intIdSucursal,intIdSubSucursal,intTipoFiltro,strTipoFiltro,
					intIdTipoVinculo,intIdEstadoSolicitud,intIdEstadoPago,dtFechaSolicitudDesde,dtFechaSolicitudHasta);
		} catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
}
