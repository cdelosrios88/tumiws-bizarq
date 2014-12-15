package pe.com.tumi.reporte.operativo.tesoreria.facade;

import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

public interface IngresoCajaFacadeLocal {
	public List<IngresoCaja> getListaIngresosByTipoIngreso(IngresoCaja o) throws BusinessException;
}
