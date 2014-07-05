package pe.com.tumi.riesgo.cartera.facade;
import java.util.List;

import javax.ejb.Remote;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.movimiento.concepto.domain.ExpedienteId;
import pe.com.tumi.riesgo.cartera.domain.CarteraCreditoDetalle;

@Remote
public interface CarteraEstCtaFacadeRemote {
	//public List<CarteraCreditoDetalle> getListaPorMaxPerYPKExpediente(ExpedienteId pId) throws BusinessException;

}
