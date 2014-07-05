package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaVerificaLiquidacionDao;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaVerificaLiquidacionDaoIbatis;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaVerificaPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaVerificaPrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevision;
import pe.com.tumi.servicio.prevision.domain.AutorizaVerificaPrevisionId;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;

public class AutorizaVerificaLiquidacionBO {
	
	private AutorizaVerificaLiquidacionDao dao = (AutorizaVerificaLiquidacionDao)TumiFactory.get(AutorizaVerificaLiquidacionDaoIbatis.class);

	public AutorizaVerificaLiquidacion grabar(AutorizaVerificaLiquidacion o) throws BusinessException{
		AutorizaVerificaLiquidacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaVerificaLiquidacion modificar(AutorizaVerificaLiquidacion o) throws BusinessException{
		AutorizaVerificaLiquidacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaVerificaLiquidacion getPorPk(AutorizaVerificaLiquidacionId pId) throws BusinessException{
		AutorizaVerificaLiquidacion domain = null;
		List<AutorizaVerificaLiquidacion> lista = null;
		try{ 
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacionPk", 	pId.getIntPersEmpresaLiquidacionPk());
			mapa.put("intItemExpedienteLiqui", 			pId.getIntItemExpedienteLiqui());
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
	
	public List<AutorizaVerificaLiquidacion> getListaPorPkExpedienteLiquidacion(ExpedienteLiquidacionId pId) throws BusinessException{
		List<AutorizaVerificaLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacionPk", 	pId.getIntPersEmpresaPk());
			mapa.put("intItemExpedienteLiqui", 			pId.getIntItemExpediente());
			lista = dao.getListaPorExpedienteLiquidacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
