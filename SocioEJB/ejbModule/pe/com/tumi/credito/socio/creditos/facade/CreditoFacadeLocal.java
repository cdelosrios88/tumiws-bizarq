package pe.com.tumi.credito.socio.creditos.facade;
import java.util.List;

import javax.ejb.Local;

import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacionId;
import pe.com.tumi.framework.negocio.exception.BusinessException;

@Local
public interface CreditoFacadeLocal {
	public List<CreditoComp> getListaCreditoCompDeBusquedaCredito(Credito o) throws BusinessException;
	public Credito grabarCredito(Credito o) throws BusinessException;
	public Credito modificarCredito(Credito o) throws BusinessException;
	public Credito getCreditoPorIdCredito(CreditoId pId) throws BusinessException;
	public CreditoId eliminarCredito(CreditoId o) throws BusinessException;
	public Credito getCreditoPorIdCreditoDirecto(CreditoId pId) throws BusinessException;
	public void deleteCreditoTopeCaptacionPorPkPor(CreditoTopeCaptacionId pId) throws BusinessException;
	public List<Credito> getlistaCreditoPorCredito(Credito o) throws BusinessException;
	public List<CreditoInteres> getlistaCreditoInteresPorPKCreditoFiltradoPorCondicionSocio(CreditoId pPk, SocioComp socioComp) throws BusinessException;
	public List<CreditoComp> getListaCreditoValidarSolicitud(Credito o) throws BusinessException;
	
	/**
	 * Recupera SOLO garantia del tipo Personal
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public CreditoGarantia recuperarGarantiasPersonales(Credito o) throws BusinessException;
	public List<Credito> getlistaCofCredito() throws BusinessException;
}
