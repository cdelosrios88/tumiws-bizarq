package pe.com.tumi.contabilidad.legalizacion.facade;

import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacion;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface LegalizacionFacadeRemote {

	public List<PagoLegalizacion> obtenerPagoLegalizacionPorPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
}
