package pe.com.tumi.contabilidad.legalizacion.facade;

import java.util.List;

import javax.ejb.Local;


import pe.com.tumi.contabilidad.legalizacion.domain.PagoLegalizacion;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface LegalizacionFacadeLocal {

	public List<PagoLegalizacion> obtenerPagoLegalizacionPorPersona(Integer intIdPersona, Integer intIdEmpresa) throws BusinessException;
}
