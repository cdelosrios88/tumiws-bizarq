package pe.com.tumi.contabilidad.operaciones.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.contabilidad.operaciones.domain.HojaManual;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualDetalle;
import pe.com.tumi.contabilidad.operaciones.domain.HojaManualId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface HojaManualFacadeRemote {
	public HojaManual grabarHojaManual(HojaManual o)throws BusinessException;
	public HojaManual modificarHojaManual(HojaManual o)throws BusinessException;
	public HojaManual getHojaManualPorPk(HojaManualId pId) throws BusinessException;
	public List<HojaManual> getListHojaManualBusqueda(HojaManualDetalle o) throws BusinessException;
}
