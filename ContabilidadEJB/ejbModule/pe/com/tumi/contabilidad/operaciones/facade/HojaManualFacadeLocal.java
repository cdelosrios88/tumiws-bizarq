package pe.com.tumi.contabilidad.operaciones.facade;
import java.util.List;

import javax.ejb.Local;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalle;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface HojaManualFacadeLocal {
	public HojaManual grabarHojaManual(HojaManual o)throws BusinessException;
	public HojaManual modificarHojaManual(HojaManual o)throws BusinessException;
	public HojaManual getHojaManualPorPk(HojaManualId pId) throws BusinessException;
	public List<HojaManual> getListHojaManualBusqueda(HojaManualDetalle o) throws BusinessException;
}
