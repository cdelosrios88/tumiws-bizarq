package pe.com.tumi.servicio.prevision.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.domain.ConfServDetalleId;
import pe.com.tumi.servicio.prevision.dao.RequisitoPrevisionDao;
import pe.com.tumi.servicio.prevision.dao.impl.RequisitoPrevisionDaoIbatis;
import pe.com.tumi.servicio.prevision.domain.ExpedientePrevisionId;
import pe.com.tumi.servicio.prevision.domain.RequisitoPrevision;

public class RequisitoPrevisionBO {
	private RequisitoPrevisionDao dao = (RequisitoPrevisionDao)TumiFactory.get(RequisitoPrevisionDaoIbatis.class);
	
	public RequisitoPrevision grabar(RequisitoPrevision o) throws BusinessException{
		RequisitoPrevision dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public RequisitoPrevision modificar(RequisitoPrevision o) throws BusinessException{
		RequisitoPrevision dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public RequisitoPrevision getPorPk(RequisitoPrevision pId) throws BusinessException{
		RequisitoPrevision domain = null;
		List<RequisitoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", pId.getId().getIntPersEmpresaPrevision());
			mapa.put("intCuentaPk", 			pId.getId().getIntCuentaPk());
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
	
	public List<RequisitoPrevision> getPorExpediente(ExpedientePrevisionId pId) throws BusinessException{
		List<RequisitoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", pId.getIntPersEmpresaPk());
			mapa.put("intCuentaPk", 			pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			lista = dao.getListaPorExpediente(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	/*public List<RequisitoPrevision> getPorExpediente(ExpedientePrevision pId) throws BusinessException{
		List<RequisitoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPrevision", pId.getId().getIntPersEmpresaPk()));
			mapa.put("intCuentaPk", 			pId.getId().getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getId().getIntItemExpediente());
			mapa.put("intItemRequisito", 		pId.getId().getIntItemRequisito());
			lista = dao.getListaPorExpediente(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}*/
	/**
	 * 
	 */
	public List<RequisitoPrevision> getListaPorPkExpedientePrevisionYRequisitoDetalle(ExpedientePrevisionId pId, ConfServDetalleId rId, Integer intBeneficiario) throws BusinessException{
		List<RequisitoPrevision> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intPersEmpresaPk", 		rId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", 		rId.getIntItemSolicitud());
			mapa.put("intItemDetalle", 			rId.getIntItemDetalle());
			mapa.put("intPersonaBeneficiario", 	intBeneficiario);
			
			lista = dao.getListaPorPkExpedientePrevisionYRequisitoDetalle(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	

}
