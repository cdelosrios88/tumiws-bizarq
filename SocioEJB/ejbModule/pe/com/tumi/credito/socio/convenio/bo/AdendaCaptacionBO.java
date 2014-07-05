package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.AdendaCaptacionDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.AdendaCaptacionDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacion;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCaptacionId;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AdendaCaptacionBO {
	
	private AdendaCaptacionDao dao = (AdendaCaptacionDao)TumiFactory.get(AdendaCaptacionDaoIbatis.class);
	
	public AdendaCaptacion grabarAdendaCaptacion(AdendaCaptacion o) throws BusinessException{
		AdendaCaptacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AdendaCaptacion modificarAdendaCaptacion(AdendaCaptacion o) throws BusinessException{
		AdendaCaptacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AdendaCaptacion getAdendaCaptacionPorPK(AdendaCaptacionId pPK) throws BusinessException{
		AdendaCaptacion domain = null;
		List<AdendaCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intConvenio", 			pPK.getIntConvenio());
			mapa.put("intItemConvenio", 		pPK.getIntItemConvenio());
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacionCod", pPK.getIntParaTipoCaptacionCod());
			mapa.put("intItem", 				pPK.getIntItem());
			lista = dao.getListaAdendaCaptacionPorPK(mapa);
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
	
	public List<AdendaCaptacion> getListaAdendaCaptacionPorPKAdenda(AdendaCaptacionId pPK) throws BusinessException{
		List<AdendaCaptacion> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdConvenio", 			pPK.getIntConvenio());
			mapa.put("intIdItemConvenio", 		pPK.getIntItemConvenio());
			mapa.put("intPersEmpresaPk",		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCaptacion", 	pPK.getIntParaTipoCaptacionCod());
			lista = dao.getListaAdendaCaptacionPorPKAdenda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}