package pe.com.tumi.credito.socio.captacion.facade;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.credito.socio.captacion.bo.CaptacionBO;
import pe.com.tumi.credito.socio.captacion.bo.VinculoBO;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.credito.socio.captacion.domain.VinculoId;

@Stateless
public class VinculoFacade extends TumiFacade implements VinculoFacadeRemote, VinculoFacadeLocal {
	
	private VinculoBO boVinculo = (VinculoBO)TumiFactory.get(VinculoBO.class);
	private CaptacionBO boCaptacion = (CaptacionBO)TumiFactory.get(CaptacionBO.class);
	
	public List<Vinculo> listarVinculo() throws BusinessException {
		List<Vinculo> lista = null;
		try{
			lista = boVinculo.getListaVinculos();
			System.out.println("Facade lista = "+lista);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}	

	public List<Vinculo> listarVinculo(VinculoId o) throws BusinessException {
		List<Vinculo> lista = null;
		try{
			lista = boVinculo.getListaVinculos(o);
			System.out.println("Facade lista = "+lista);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Vinculo grabarVinculo(Vinculo o) throws BusinessException{
		Vinculo vinculo = null;
		try{
			vinculo = boVinculo.grabarVinculo(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return vinculo;
	}
	
	public Vinculo modificarVinculo(Vinculo o) throws BusinessException{
		Vinculo vinculo = null;
		try{
			vinculo = boVinculo.modificarVinculo(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return vinculo;
	}	
	
	public List<Vinculo> listarVinculoPorPKCaptacion(CaptacionId o) throws BusinessException {
		List<Vinculo> lista = null;
		try{
			lista = boVinculo.getListaVinculoPorPKCaptacion(o);			
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/*public Vinculo eliminarVinculo(Vinculo o) throws BusinessException{
		Vinculo vinculo = null;
		try{
			vinculo = boVinculo.eliminarVinculo(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return vinculo;
	}*/
	

}
