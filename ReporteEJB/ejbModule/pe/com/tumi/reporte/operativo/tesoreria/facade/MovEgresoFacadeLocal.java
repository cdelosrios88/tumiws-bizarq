package pe.com.tumi.reporte.operativo.tesoreria.facade;

import java.util.List;

import javax.faces.model.SelectItem;

import pe.com.tumi.framework.negocio.exception.BusinessException;

public interface MovEgresoFacadeLocal {
	public List<SelectItem> getListFondoFijo(int intSucursal,int intAnio, int intTipoFondoFijo) throws BusinessException;
}
