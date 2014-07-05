package pe.com.tumi.persona.empresa.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.persona.empresa.dao.ActividadEconomicaDao;
import pe.com.tumi.persona.empresa.dao.TipoComprobanteDao;
import pe.com.tumi.persona.empresa.dao.impl.ActividadEconomicaDaoIbatis;
import pe.com.tumi.persona.empresa.dao.impl.TipoComprobanteDaoIbatis;
import pe.com.tumi.persona.empresa.domain.ActividadEconomica;
import pe.com.tumi.persona.empresa.domain.ActividadEconomicaPK;
import pe.com.tumi.persona.empresa.domain.TipoComprobante;
import pe.com.tumi.persona.empresa.domain.TipoComprobantePK;

public class TipoComprobanteBO {
	
	private TipoComprobanteDao dao = (TipoComprobanteDao)TumiFactory.get(TipoComprobanteDaoIbatis.class);
	
	public TipoComprobante grabarTipoComprobante(TipoComprobante o) throws BusinessException{
		TipoComprobante dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public TipoComprobante modificarTipoComprobante(TipoComprobante o) throws BusinessException{
		TipoComprobante dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public TipoComprobante getTipoComprobantePorPK(TipoComprobantePK pPK) throws BusinessException{
		TipoComprobante domain = null;
		List<TipoComprobante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pPK.getIntIdPersona());
			mapa.put("intItemComprobante", pPK.getIntItemTipoComprobante());
			lista = dao.getListaTipoComprobantePorPK(mapa);
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
	
	public List<TipoComprobante> getListaTipoComprobantePorIdPersona(Integer pId) throws BusinessException{
		List<TipoComprobante> lista = null;
		try{
			HashMap mapa = new HashMap();
			mapa.put("intIdPersona", pId);
			lista = dao.getListaTipoComprobantePorIdPersona(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
