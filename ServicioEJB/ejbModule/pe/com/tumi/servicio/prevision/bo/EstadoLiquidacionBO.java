package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.EstadoLiquidacionDao;
import pe.com.tumi.servicio.prevision.dao.impl.EstadoLiquidacionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.EstadoLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacion;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;

public class EstadoLiquidacionBO {
	
	private EstadoLiquidacionDao dao = (EstadoLiquidacionDao)TumiFactory.get(EstadoLiquidacionDaoIbatis.class);
	
	public EstadoLiquidacion grabar(EstadoLiquidacion o) throws BusinessException{
		EstadoLiquidacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoLiquidacion modificar(EstadoLiquidacion o) throws BusinessException{
		EstadoLiquidacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoLiquidacion getPorPk(EstadoLiquidacionId pId) throws BusinessException{
		EstadoLiquidacion domain = null;
		List<EstadoLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemExpediente", pId.getIntItemExpediente());
			mapa.put("intItemEstado", pId.getIntItemEstado());
			lista = dao.getListaPorPk(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public List<EstadoLiquidacion> getPorExpediente(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
		List<EstadoLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", expedienteLiquidacion.getId().getIntPersEmpresaPk());
			mapa.put("intItemExpediente", expedienteLiquidacion.getId().getIntItemExpediente());
			lista = dao.getListaPorExpediente(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}	
	
	public EstadoLiquidacion getMaxEstadoLiquidacionPorPkExpediente(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
		EstadoLiquidacion domain = null;
		List<EstadoLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", expedienteLiquidacion.getId().getIntPersEmpresaPk());
			mapa.put("intItemExpediente", expedienteLiquidacion.getId().getIntItemExpediente());
			lista = dao.getMaxEstadoliquidacionPorPkExpediente(mapa);
			//getMaxEstadoPrevisionPorPokExpediente(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
	
	public EstadoLiquidacion getMinEstadoLiquidacionPorPkExpediente(ExpedienteLiquidacion expedienteLiquidacion) throws BusinessException{
		EstadoLiquidacion domain = null;
		List<EstadoLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", expedienteLiquidacion.getId().getIntPersEmpresaPk());
			mapa.put("intItemExpediente", expedienteLiquidacion.getId().getIntItemExpediente());
			lista = dao.getMinEstadoliquidacionPorPkExpediente(mapa);
			//getMaxEstadoPrevisionPorPokExpediente(mapa);
			if(lista!=null){
				if(lista.size()==1){
				   domain = lista.get(0);
				}else if(lista.size()==0){
				   domain = null;
				}else{
				   throw new BusinessException("Obtención de mas de un registro coincidente");
				}
			}	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(BusinessException e){
			throw e;
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
}