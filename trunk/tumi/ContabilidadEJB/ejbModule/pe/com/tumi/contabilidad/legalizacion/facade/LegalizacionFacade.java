package pe.com.tumi.contabilidad.legalizacion.facade;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


import pe.com.tumi.common.util.AnexoDetalleException;
import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.MayorizacionException;
import pe.com.tumi.contabilidad.cierre.bo.AnexoBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleCuentaBO;
import pe.com.tumi.contabilidad.cierre.bo.AnexoDetalleOperadorBO;
import pe.com.tumi.contabilidad.cierre.bo.CuentaCierreBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroMayorBO;
import pe.com.tumi.contabilidad.cierre.bo.LibroMayorDetalleBO;
import pe.com.tumi.contabilidad.cierre.bo.RatioBO;
import pe.com.tumi.contabilidad.cierre.bo.RatioDetalleBO;
import pe.com.tumi.contabilidad.cierre.domain.Anexo;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalle;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleCuenta;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleId;
import pe.com.tumi.contabilidad.cierre.domain.AnexoDetalleOperador;
import pe.com.tumi.contabilidad.cierre.domain.AnexoId;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierre;
import pe.com.tumi.contabilidad.cierre.domain.CuentaCierreDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayor;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorDetalle;
import pe.com.tumi.contabilidad.cierre.domain.LibroMayorId;
import pe.com.tumi.contabilidad.cierre.domain.Ratio;
import pe.com.tumi.contabilidad.cierre.domain.RatioDetalle;
import pe.com.tumi.contabilidad.cierre.service.AnexoService;
import pe.com.tumi.contabilidad.cierre.service.CierreService;
import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacion;
import pe.com.tumi.contabilidad.legalizacion.service.LibroLegalizacionService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

@Stateless
public class LegalizacionFacade extends TumiFacade implements LegalizacionFacadeRemote, LegalizacionFacadeLocal{

	LibroLegalizacionService libroLegalizacionService = (LibroLegalizacionService)TumiFactory.get(LibroLegalizacionService.class);
	
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<PagoLegalizacion> obtenerPagoLegalizacionPorPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException{
		List<PagoLegalizacion> lista = null;
   		try{
   			lista = libroLegalizacionService.obtenerPagoLegalizacionPorPersona(intIdPersona, intIdEmpresa);
   		}catch(BusinessException e){
   			throw e;
   		}catch(Exception e){
   			throw new BusinessException(e);
   		}
   		return lista;
	}
}