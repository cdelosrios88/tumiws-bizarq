package pe.com.tumi.credito.socio.captacion.facade;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.credito.socio.captacion.bo.CaptacionBO;
import pe.com.tumi.credito.socio.captacion.bo.CondicionBO;
import pe.com.tumi.credito.socio.captacion.domain.Captacion;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Condicion;
import pe.com.tumi.credito.socio.captacion.domain.CondicionId;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.credito.socio.captacion.domain.VinculoId;

@Stateless
public class CondicionFacade extends TumiFacade implements CondicionFacadeRemote, CondicionFacadeLocal {
	
	private CondicionBO boCondicion = (CondicionBO)TumiFactory.get(CondicionBO.class);	
	
	public List<Condicion> listarCondicion(CaptacionId o) throws BusinessException {
		List<Condicion> lista = null;
		try{
			lista = boCondicion.getListaPorPKCaptacion(o);
			System.out.println("Facade lista = "+lista);
		/*}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}*/
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Condicion grabar(Condicion o) throws BusinessException{
		Condicion condicion = null;
		try{
			condicion = boCondicion.grabarCondicion(o);
		}catch(BusinessException e){
			context.setRollbackOnly();
			throw e;
		}catch(Exception e){
			context.setRollbackOnly();
			throw new BusinessException(e);
		}
		return condicion;
	}


	
}
