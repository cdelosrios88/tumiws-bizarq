package pe.com.tumi.credito.socio.creditos.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuento;
import pe.com.tumi.credito.socio.creditos.domain.CreditoDescuentoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface CreditoDescuentoFacadeLocal {
	public List<CreditoDescuento> getListaCreditoDescuento(CreditoId o) throws BusinessException;
	public CreditoDescuento grabarCreditoDescuento(CreditoDescuento o) throws BusinessException;
	public CreditoDescuento modificarCreditoDescuento(CreditoDescuento o) throws BusinessException;
	public CreditoDescuento getCreditoDescuentoPorIdCreditoDescuento(CreditoDescuentoId pId) throws BusinessException;
	public CreditoDescuento eliminarCreditoDescuento(CreditoDescuento o) throws BusinessException;
}
