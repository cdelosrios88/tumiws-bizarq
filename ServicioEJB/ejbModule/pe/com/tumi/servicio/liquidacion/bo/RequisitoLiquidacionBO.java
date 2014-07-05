package pe.com.tumi.servicio.liquidacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.prevision.dao.RequisitoLiquidacionDao;
import pe.com.tumi.servicio.prevision.dao.impl.RequisitoLiquidacionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.ExpedienteLiquidacionId;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.RequisitoLiquidacion;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;

public class RequisitoLiquidacionBO {
private RequisitoLiquidacionDao dao = (RequisitoLiquidacionDao)TumiFactory.get(RequisitoLiquidacionDaoIbatis.class);
	
	public RequisitoLiquidacion grabar(RequisitoLiquidacion o) throws BusinessException{
		RequisitoLiquidacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public RequisitoLiquidacion modificar(RequisitoLiquidacion o) throws BusinessException{
		RequisitoLiquidacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public RequisitoLiquidacion getPorPk(RequisitoLiquidacion pId) throws BusinessException{
		RequisitoLiquidacion domain = null;
		List<RequisitoLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacion", pId.getId().getIntPersEmpresaLiquidacion());
			mapa.put("intItemExpediente", 		pId.getId().getIntItemExpediente());
			mapa.put("intItemRequisito", 		pId.getId().getIntItemRequisito());
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
	
	public List<RequisitoLiquidacion> getListaPorExpediente(ExpedienteLiquidacionId pId) throws BusinessException{
		List<RequisitoLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaLiquidacion", 	pId.getIntPersEmpresaPk());
			mapa.put("intItemExpediente", 			pId.getIntItemExpediente());
			lista = dao.getListaPorExpediente(mapa);

		}catch(DAOException e){
			System.out.println("DAOException "+e);
			throw new BusinessException(e);
		}catch(Exception e1) {
			System.out.println("Exception "+e1);
			throw new BusinessException(e1);
		}
		return lista;
	}
	
	public List<RequisitoLiquidacion> getListaPorPkExpedienteLiquidacionYRequisitoDetalle(ExpedienteLiquidacionId pId, ConfServDetalleId rId, Integer intBeneficiario) throws BusinessException{
		List<RequisitoLiquidacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intPersEmpresaPk", 		rId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", 		rId.getIntItemSolicitud());
			mapa.put("intItemDetalle", 			rId.getIntItemDetalle());
			mapa.put("intPersonaBeneficiario", 	intBeneficiario);
			
			lista = dao.getListaPorPkExpedienteLiquidacionYRequisitoDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	


}
