package pe.com.tumi.servicio.configuracion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServGrupoCtaDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServGrupoCtaDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServGrupoCta;
import pe.com.tumi.servicio.configuracion.domain.ConfServGrupoCtaId;

public class ConfServGrupoCtaBO {
	
	private ConfServGrupoCtaDao dao = (ConfServGrupoCtaDao)TumiFactory.get(ConfServGrupoCtaDaoIbatis.class);
	
	public ConfServGrupoCta grabar(ConfServGrupoCta o) throws BusinessException{
		ConfServGrupoCta dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServGrupoCta modificar(ConfServGrupoCta o) throws BusinessException{
		ConfServGrupoCta dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServGrupoCta getPorPk(ConfServGrupoCtaId pId) throws BusinessException{
		ConfServGrupoCta domain = null;
		List<ConfServGrupoCta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			mapa.put("intParaTipoCuentaCod", pId.getIntParaTipoCuentaCod());
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
	
	public List<ConfServGrupoCta> getPorCabecera(Integer intPersEmpresaPk, Integer intItemSolicitud) throws BusinessException{
		List<ConfServGrupoCta> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", intPersEmpresaPk);
			mapa.put("intItemSolicitud", intItemSolicitud);
			lista = dao.getListaPorCabecera(mapa);	
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
