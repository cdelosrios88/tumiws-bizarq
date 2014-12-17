package pe.com.tumi.reporte.operativo.tesoreria.facade;

import java.util.List;


import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.reporte.operativo.tesoreria.domain.MovEgreso;

public interface MovEgresoFacadeRemote {
	public List<MovEgreso> getListFondoFijo(int intSucursal,int intAnio, int intTipoFondoFijo) throws BusinessException;
}
