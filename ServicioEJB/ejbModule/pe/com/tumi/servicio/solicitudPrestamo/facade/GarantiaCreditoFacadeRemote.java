package pe.com.tumi.servicio.solicitudPrestamo.facade;
import java.util.List;

import javax.ejb.Remote;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.GarantiaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.composite.GarantiaCreditoComp;

@Remote
public interface GarantiaCreditoFacadeRemote {
	public List<GarantiaCreditoComp> getListaGarantiaCreditoCompPorExpediente(ExpedienteCreditoId pId) throws BusinessException;
	public GarantiaCredito modificarGarantiaCredito(GarantiaCredito o) throws BusinessException;
	public GarantiaCredito getGarantiaCredito(GarantiaCreditoId pId) throws BusinessException;
	public GarantiaCredito grabarGarantiaCredito(GarantiaCredito o) throws BusinessException;
	//AUTOR Y FECHA CREACION: JCHAVEZ / 04-09-2013
	public List<GarantiaCredito> getListaGarantiaCreditoPorEmpPersCta(GarantiaCredito garantiaCredito) throws BusinessException;
		
		
}
