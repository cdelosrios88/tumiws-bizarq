package pe.com.tumi.credito.socio.creditos.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.credito.socio.creditos.domain.CondicionCredito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Remote
public interface CondicionCreditoFacadeRemote {
	public List<CondicionCredito> listarCondicion(CreditoId o) throws BusinessException;
	public CondicionCredito grabar(CondicionCredito o) throws BusinessException;
}
