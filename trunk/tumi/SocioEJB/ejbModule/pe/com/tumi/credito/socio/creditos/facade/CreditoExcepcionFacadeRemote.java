package pe.com.tumi.credito.socio.creditos.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcion;
import pe.com.tumi.credito.socio.creditos.domain.CreditoExcepcionId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface CreditoExcepcionFacadeRemote {
	
	public List<CreditoExcepcion> getListaCreditoExcepcion(CreditoId o) throws BusinessException;
	public CreditoExcepcion grabarCreditoExcepcion(CreditoExcepcion o) throws BusinessException;
	public CreditoExcepcion modificarCreditoExcepcion(CreditoExcepcion o) throws BusinessException;
	public CreditoExcepcion getCreditoExcepcionPorIdCreditoExcepcion(CreditoExcepcionId pId) throws BusinessException;
	public CreditoExcepcion eliminarCreditoExcepcion(CreditoExcepcion o) throws BusinessException;
	
}
