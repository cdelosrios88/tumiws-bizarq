package pe.com.tumi.contabilidad.reclamosDevoluciones.facade;

import java.util.List;

import javax.ejb.Stateless;

import pe.com.tumi.contabilidad.reclamosDevoluciones.bo.ReclamosDevolucionesBo;
import pe.com.tumi.contabilidad.reclamosDevoluciones.domain.ReclamosDevoluciones;
import pe.com.tumi.contabilidad.reclamosDevoluciones.service.ReclamosDevolucionesService;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.facade.TumiFacade;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

/**
 * Session Bean implementation class reclamosDevolucionesFacade
 */
//Autor: Rodolfo Villarreal / Tarea: Creación / Fecha: 08.08.2014 /
@Stateless
public class ReclamosDevolucionesFacade extends TumiFacade implements ReclamosDevolucionesFacadeRemote, ReclamosDevolucionesFacadeLocal {
	ReclamosDevolucionesBo boReclamosDevolucionesBO = (ReclamosDevolucionesBo)TumiFactory.get(ReclamosDevolucionesBo.class);
	ReclamosDevolucionesService reclamosDevolucionesService = (ReclamosDevolucionesService)TumiFactory.get(ReclamosDevolucionesService.class);
	@Override
	public ReclamosDevoluciones grabarReclamos(ReclamosDevoluciones o)throws BusinessException{
	ReclamosDevoluciones dto = null;
	try{
		dto = reclamosDevolucionesService.grabarReclamos(o);
	}catch(BusinessException e){
		context.setRollbackOnly();
		throw e;
	}catch(Exception e){
		context.setRollbackOnly();
		throw new BusinessException(e);
	}
	return dto;
	}
	@Override
	public ReclamosDevoluciones modificarReclamos(ReclamosDevoluciones o)throws BusinessException{
	ReclamosDevoluciones dto = null;
	try{
		dto = boReclamosDevolucionesBO.modificarReclamos(o);
	}catch(BusinessException e){
		context.setRollbackOnly();
		throw e;
	}catch(Exception e){
		context.setRollbackOnly();
		throw new BusinessException(e);
	}
	return dto;
	}
	@Override
	public List<ReclamosDevoluciones> getListaParametro(ReclamosDevoluciones o) throws BusinessException {
		List<ReclamosDevoluciones> lista = null;
		try{
			lista = boReclamosDevolucionesBO.getListaParametro(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}
	
	@Override
	public List<ReclamosDevoluciones> getBuscar(ReclamosDevoluciones o) throws BusinessException {
		List<ReclamosDevoluciones> lista = null;
		try{
			lista = boReclamosDevolucionesBO.getBuscar(o);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return lista;
	}

}
