package pe.com.tumi.credito.socio.convenio.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.credito.socio.convenio.dao.AdendaCreditoDao;
import pe.com.tumi.credito.socio.convenio.dao.impl.AdendaCreditoDaoIbatis;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCredito;
import pe.com.tumi.credito.socio.convenio.domain.AdendaCreditoId;
import pe.com.tumi.credito.socio.convenio.domain.AdendaId;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;

public class AdendaCreditoBO {
	
	private AdendaCreditoDao dao = (AdendaCreditoDao)TumiFactory.get(AdendaCreditoDaoIbatis.class);
	
	public AdendaCredito grabarAdendaCredito(AdendaCredito o) throws BusinessException{
		AdendaCredito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AdendaCredito modificarAdendaCredito(AdendaCredito o) throws BusinessException{
		AdendaCredito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AdendaCredito getAdendaCreditoPorPK(AdendaCreditoId pPK) throws BusinessException{
		AdendaCredito domain = null;
		List<AdendaCredito> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intItemConvenio", 		pPK.getIntConvenio());
			mapa.put("intItemItemConvenio", 	pPK.getIntItemConvenio());
			mapa.put("intPersEmpresaPk", 		pPK.getIntPersEmpresaPk());
			mapa.put("intParaTipoCreditoCod", 	pPK.getIntParaTipoCreditoCod());
			mapa.put("intItemCredito", 			pPK.getIntItemCredito());
			lista = dao.getListaAdendaCreditoPorPK(mapa);
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
	
	public List<AdendaCredito> getListaAdendaCreditoPorPKAdenda(AdendaId pPK) throws BusinessException{
		List<AdendaCredito> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdConvenio", 		pPK.getIntConvenio());
			mapa.put("intIdItemConvenio", 	pPK.getIntItemConvenio());
			lista = dao.getListaAdendaCreditoPorPKAdenda(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}