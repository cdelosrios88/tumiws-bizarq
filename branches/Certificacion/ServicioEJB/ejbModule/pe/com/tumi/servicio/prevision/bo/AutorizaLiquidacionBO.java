package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaLiquidacionDao;
import pe.com.tumi.servicio.prevision.dao.impl.AutorizaLiquidacionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacion;
import pe.com.tumi.servicio.prevision.domain.AutorizaLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;

public class AutorizaLiquidacionBO {
	private AutorizaLiquidacionDao dao = (AutorizaLiquidacionDao)TumiFactory.get(AutorizaLiquidacionDaoIbatis.class);

	public AutorizaLiquidacion grabar(AutorizaLiquidacion o) throws BusinessException{
		AutorizaLiquidacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaLiquidacion modificar(AutorizaLiquidacion o) throws BusinessException{
		AutorizaLiquidacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaLiquidacion getPorPk(AutorizaLiquidacionId pId) throws BusinessException{
		AutorizaLiquidacion domain = null;
		List<AutorizaLiquidacion> lista = null;
		try{ 
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacionPk", 	pId.getIntPersEmpresaLiquidacionPk());
			mapa.put("intItemExpedienteLiqui", 			pId.getIntItemExpedienteLiqui());
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
	
	public List<AutorizaLiquidacion> getListaPorPkExpedienteLiquidacion(ExpedienteLiquidacionId pId) throws BusinessException{
		List<AutorizaLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacionPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemExpedienteLiqui", 			pId.getIntItemExpediente());
			
			lista = dao.getListaPorExpedienteLiquidacion(mapa);
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
