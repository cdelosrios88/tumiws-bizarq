package pe.com.tumi.credito.socio.creditos.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.credito.socio.core.domain.SocioComp;
import pe.com.tumi.credito.socio.credito.domain.composite.CreditoComp;
import pe.com.tumi.credito.socio.creditos.bo.CreditoBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoInteresBO;
import pe.com.tumi.credito.socio.creditos.bo.CreditoTopeCaptacionBO;
import pe.com.tumi.credito.socio.creditos.domain.Credito;
import pe.com.tumi.credito.socio.creditos.domain.CreditoGarantia;
import pe.com.tumi.credito.socio.creditos.domain.CreditoId;
import pe.com.tumi.credito.socio.creditos.domain.CreditoInteres;
import pe.com.tumi.credito.socio.creditos.domain.CreditoTopeCaptacionId;
import pe.com.tumi.credito.socio.creditos.service.CreditoService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class CreditoFacade
 */
@Stateless
public class CreditoFacade extends TumiFacade implements CreditoFacadeRemote, CreditoFacadeLocal {
	
	private CreditoService creditoService = (CreditoService)TumiFactory.get(CreditoService.class);
	private CreditoBO boCredito = (CreditoBO)TumiFactory.get(CreditoBO.class);
	private CreditoTopeCaptacionBO boCreditoTopeCaptacion = (CreditoTopeCaptacionBO)TumiFactory.get(CreditoTopeCaptacionBO.class);
	private CreditoInteresBO boCreditoInteres = (CreditoInteresBO)TumiFactory.get(CreditoInteresBO.class);
	
	public List<CreditoComp> getListaCreditoCompDeBusquedaCredito(Credito o) throws BusinessException{
		List<CreditoComp> lista = null;
		try{
			lista = creditoService.getListaCreditoCompDeBusquedaCredito(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Credito grabarCredito(Credito o) throws BusinessException{
		Credito credito = null;
		try{
			credito = creditoService.grabarCredito(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return credito;
	}
	
	public Credito modificarCredito(Credito o) throws BusinessException{
		Credito credito = null;
		try{
			credito = creditoService.modificarCredito(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return credito;
	}
	
	public Credito getCreditoPorIdCredito(CreditoId pId) throws BusinessException{
		Credito credito = null;
		try{
			credito = creditoService.getCreditoPorIdCredito(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return credito;
	}
	
	public CreditoId eliminarCredito(CreditoId o) throws BusinessException{
		CreditoId creditoId = null;
		try{
			creditoId = boCredito.eliminarCredito(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return creditoId;
	}
	
	public Credito getCreditoPorIdCreditoDirecto(CreditoId pId) throws BusinessException{
		Credito credito = null;
		try{
			credito = boCredito.getCreditoPorPK(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return credito;
	}
	
	public void deleteCreditoTopeCaptacionPorPkPor(CreditoTopeCaptacionId pId) throws BusinessException{
		try{
			boCreditoTopeCaptacion.deletePorPk(pId);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
	}
	
	/**
	 * Recupera las Configuraciones de Credito, por cualquier campo de la configuracion.
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public List<Credito> getlistaCreditoPorCredito(Credito o) throws BusinessException{
		List<Credito> lista = null;
		List<Credito> listaCargada = null;
		try{
			lista =  boCredito.getlistaCreditoPorCredito(o);
			if(lista != null && !lista.isEmpty()){
				listaCargada = new ArrayList<Credito>();
				for (Credito credito : lista) {
					credito = creditoService.getCreditoPorIdCredito(credito.getId());
					listaCargada.add(credito);
				}
			}

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return listaCargada;
	}
	
	
	public List<CreditoInteres> getlistaCreditoInteresPorPKCreditoFiltradoPorCondicionSocio(CreditoId pPk, SocioComp socioComp) throws BusinessException{
		List<CreditoInteres> lista = null;
		try{
			lista =  creditoService.getlistaCreditoInteresPorPKCreditoFiltradoPorCondicionSocio(pPk,socioComp);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	/**
	 * Reecupera la lista de credito para  validar datos en solicitud de prestamo.
	 * Se agrega
	 */
	public List<CreditoComp> getListaCreditoValidarSolicitud(Credito o) throws BusinessException{
		List<CreditoComp> lista = null;
		try{
			lista = creditoService.getListaCreditoValidarSolicitud(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

	
	
	/**
	 * Reecupera garantia del tipo personal
	 * @param o
	 * @return
	 * @throws BusinessException
	 */
	public CreditoGarantia recuperarGarantiasPersonales(Credito o) throws BusinessException{
		CreditoGarantia dto = null;
		try{
			dto = creditoService.recuperarGarantiasPersonales(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return dto;
	}
	
	
	/**
	 * Recupera todas las conf de credito
	 * @return
	 * @throws BusinessException
	 */
	public List<Credito> getlistaCofCredito() throws BusinessException{
		List<Credito> lista = null;
		try{
			lista =  boCredito.getlistaCofCredito();

		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
}