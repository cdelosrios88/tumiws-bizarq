package pe.com.tumi.tesoreria.egreso.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.tesoreria.egreso.domain.ControlFondosFijos;

@Local
public interface CierreFondoFijoFacadeLocal {

	public List<ControlFondosFijos> getControlFondoFijo(Integer intEmpresa, Integer intTipoFondo, Integer intIdSucursal, Integer intIdSubSucursal) throws BusinessException;
}
