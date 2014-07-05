package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaPrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaVerificaPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaVerificaPrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaPrevisionId;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevisionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;

public class AutorizaVerificaPrevisionBO {
	
	private AutorizaVerificaPrevisionDao dao = (AutorizaVerificaPrevisionDao)TumiFactory.get(AutorizaVerificaPrevisionDaoIbatis.class);

	public AutorizaVerificaPrevision grabar(AutorizaVerificaPrevision o) throws BusinessException{
		AutorizaVerificaPrevision dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaVerificaPrevision modificar(AutorizaVerificaPrevision o) throws BusinessException{
		AutorizaVerificaPrevision dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaVerificaPrevision getPorPk(AutorizaVerificaPrevisionId pId) throws BusinessException{
		AutorizaVerificaPrevision domain = null;
		List<AutorizaVerificaPrevision> lista = null;
		try{ 
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevisionPk", 	pId.getIntPersEmpresaPrevisionPk());
			mapa.put("intCuentaPk", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 			pId.getIntItemExpediente());
			mapa.put("intItemAutorizaVerifica", 	pId.getIntItemAutorizaVerifica());
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
	
	public List<AutorizaVerificaPrevision> getListaPorPkExpedientePrevision(ExpedientePrevisionId pId) throws BusinessException{
		List<AutorizaVerificaPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevisionPk", 	pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 			pId.getIntItemExpediente());
			lista = dao.getListaPorExpedientePrevision(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}

}
