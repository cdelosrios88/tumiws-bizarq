package pe.com.tumi.credito.socio.captacion.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Concepto;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.credito.socio.convenio.domain.composite.CaptacionComp;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.CuentaConceptoDetalle;

@Remote
public interface CaptacionFacadeRemote {
	Captacion listarCaptacionPorPK(CaptacionId o) throws BusinessException;
	List<Captacion> listarCaptacion(CaptacionId o) throws BusinessException;
	//**legal
	List<CaptacionComp> getListaCaptacionCompDeBusquedaCaptacion(Captacion o) throws BusinessException;
	Captacion grabarCaptacion(Captacion o) throws BusinessException;
	public CaptacionId eliminarCaptacion(CaptacionId o) throws BusinessException;
	Captacion modificarCaptacion(Captacion o) throws BusinessException;
	public Captacion getCaptacionPorIdCaptacion(CaptacionId pId) throws BusinessException;
	public List<Captacion> getCaptacionPorPKOpcional(CaptacionId pId) throws BusinessException;
	public List<Requisito> getListaRequisitoPorPKCaptacion (CaptacionId o) throws BusinessException;
	public List<Concepto> getListaConceptoPorPKCaptacion (CaptacionId o) throws BusinessException;
	public Captacion getCaptacionPorCuentaConceptoDetalle(CuentaConceptoDetalle cuentaConceptoDetalle) throws BusinessException;
}
