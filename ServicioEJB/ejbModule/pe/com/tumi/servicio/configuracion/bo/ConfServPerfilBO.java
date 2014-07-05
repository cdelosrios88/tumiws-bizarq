package pe.com.tumi.servicio.configuracion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServPerfilDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServPerfilDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfilId;
import pe.com.tumi.servicio.configuracion.domain.ConfServRol;

public class ConfServPerfilBO {
	
	private ConfServPerfilDao dao = (ConfServPerfilDao)TumiFactory.get(ConfServPerfilDaoIbatis.class);
	
	public ConfServPerfil grabar(ConfServPerfil o) throws BusinessException{
		ConfServPerfil dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServPerfil modificar(ConfServPerfil o) throws BusinessException{
		ConfServPerfil dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServPerfil getPorPk(ConfServPerfilId pId) throws BusinessException{
		ConfServPerfil domain = null;
		List<ConfServPerfil> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			mapa.put("intItemPerfil", pId.getIntItemPerfil());
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
	
	public List<ConfServPerfil> getPorCabecera(Integer intPersEmpresaPk, Integer intItemSolicitud) throws BusinessException{
		List<ConfServPerfil> lista = null;
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
