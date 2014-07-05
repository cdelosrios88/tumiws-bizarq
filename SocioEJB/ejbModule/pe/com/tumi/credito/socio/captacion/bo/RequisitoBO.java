package pe.com.tumi.credito.socio.captacion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.captacion.dao.RequisitoDao;
import pe.com.tumi.credito.socio.captacion.dao.impl.RequisitoDaoIbatis;
import pe.com.tumi.credito.socio.captacion.domain.CaptacionId;
import pe.com.tumi.credito.socio.captacion.domain.Requisito;
import pe.com.tumi.credito.socio.captacion.domain.RequisitoId;
import pe.com.tumi.credito.socio.captacion.domain.Vinculo;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class RequisitoBO {
	
	private RequisitoDao dao = (RequisitoDao)TumiFactory.get(RequisitoDaoIbatis.class);
	
	public Requisito grabarRequisito(Requisito o) throws BusinessException{
		Requisito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Requisito modificarRequisito(Requisito o) throws BusinessException{
		Requisito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public Requisito getRequisitoPorPK(RequisitoId pPK) throws BusinessException{
		Requisito domain = null;
		List<Requisito> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			mapa.put("intParaTipoRequisitoBenef", pPK.getIntParaTipoRequisitoBenef());
			mapa.put("intItemRequisito", pPK.getIntItemRequisito());
			lista = dao.getListaRequisitoPorPK(mapa);
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
	
	public List<Requisito> getListaRequisitoPorPKCaptacion(CaptacionId pPK) throws BusinessException{
		List<Requisito> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", pPK.getIntItem());
			lista = dao.getListaRequisitoPorPKCaptacion(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public Object eliminarRequisito(CaptacionId pPK, String strPkRequisito) throws BusinessException{
		try{
			HashMap mapa = new HashMap();
			mapa.put("intPersEmpresaPk", 	pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", 			pPK.getIntItem());
			mapa.put("strPkRequisito", 		strPkRequisito);
			dao.eliminar(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return strPkRequisito;
	}
}
