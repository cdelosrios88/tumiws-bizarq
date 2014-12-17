package pe.com.tumi.reporte.operativo.tesoreria.facade;

import java.util.List;

import javax.faces.model.SelectItem;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.tesoreria.domain.IngresoCaja;

public interface MovEgresoFacadeRemote {
	public List<SelectItem> getListFondoFijo(int intSucursal,int intAnio, int intTipoFondoFijo) throws BusinessException;
}
