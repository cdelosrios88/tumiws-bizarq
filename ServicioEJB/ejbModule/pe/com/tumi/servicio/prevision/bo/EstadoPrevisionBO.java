package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.EstadoPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.EstadoPrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevision;
import pe.com.tumi.servicio.prevision.domain.EstadoPrevisionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevision;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;

public class EstadoPrevisionBO {
	
	private EstadoPrevisionDao dao = (EstadoPrevisionDao)TumiFactory.get(EstadoPrevisionDaoIbatis.class);
	
	public EstadoPrevision grabar(EstadoPrevision o) throws BusinessException{
		EstadoPrevision dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoPrevision modificar(EstadoPrevision o) throws BusinessException{
		EstadoPrevision dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public EstadoPrevision getPorPk(EstadoPrevisionId pId) throws BusinessException{
		EstadoPrevision domain = null;
		List<EstadoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", pId.getIntCuentaPk());
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
	
	public List<EstadoPrevision> getPorExpediente(ExpedientePrevision expedientePrevision) throws BusinessException{
		List<EstadoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", expedientePrevision.getId().getIntPersEmpresaPk());
			mapa.put("intCuentaPk", expedientePrevision.getId().getIntCuentaPk());
			mapa.put("intItemExpediente", expedientePrevision.getId().getIntItemExpediente());
			lista = dao.getListaPorExpediente(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	
	public EstadoPrevision getMaxEstadoPrevisionPorPokExpediente(ExpedientePrevision expedientePrevision) throws BusinessException{
		EstadoPrevision domain = null;
		List<EstadoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", expedientePrevision.getId().getIntPersEmpresaPk());
			mapa.put("intCuentaPk", expedientePrevision.getId().getIntCuentaPk());
			mapa.put("intItemExpediente", expedientePrevision.getId().getIntItemExpediente());
			lista = dao.getMaxEstadoPrevisionPorPokExpediente(mapa);
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
	
	
	//getMaxEstadoPrevisionPorPokExpediente
	
}