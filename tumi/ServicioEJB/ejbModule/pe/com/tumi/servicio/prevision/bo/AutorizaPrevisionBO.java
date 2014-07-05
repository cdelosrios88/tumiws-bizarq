package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaPrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevisionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCreditoId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;

public class AutorizaPrevisionBO {
	
	private AutorizaPrevisionDao dao = (AutorizaPrevisionDao)TumiFactory.get(AutorizaPrevisionDaoIbatis.class);

	public AutorizaPrevision grabar(AutorizaPrevision o) throws BusinessException{
		AutorizaPrevision dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaPrevision modificar(AutorizaPrevision o) throws BusinessException{
		AutorizaPrevision dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaPrevision getPorPk(AutorizaPrevisionId pId) throws BusinessException{
		AutorizaPrevision domain = null;
		List<AutorizaPrevision> lista = null;
		try{ 
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevisionPk", 	pId.getIntPersEmpresaPrevisionPk());
			mapa.put("intCuentaPk", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 			pId.getIntItemExpediente());
			mapa.put("intItemAutoriza", 			pId.getIntItemAutoriza());
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
	
	public List<AutorizaPrevision> getListaPorPkExpedientePrevision(ExpedientePrevisionId pId) throws BusinessException{
		List<AutorizaPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevisionPk", 	pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 			pId.getIntItemExpediente());
			
			lista = dao.getListaPorExpedientePrevision(mapa);
		}catch(DAOException e){
			System.out.println("getListaPorPkExpedientePrevision----> "+e);
			throw new BusinessException(e);
		}catch(Exception e1) {
			System.out.println("BusinessExceptionBusinessException---> "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}
	
	
	

}
