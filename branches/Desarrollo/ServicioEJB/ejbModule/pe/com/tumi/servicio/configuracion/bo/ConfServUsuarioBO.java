package pe.com.tumi.servicio.configuracion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServUsuarioDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServUsuarioDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuarioId;

public class ConfServUsuarioBO {
	
	private ConfServUsuarioDao dao = (ConfServUsuarioDao)TumiFactory.get(ConfServUsuarioDaoIbatis.class);
	
	public ConfServUsuario grabar(ConfServUsuario o) throws BusinessException{
		ConfServUsuario dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServUsuario modificar(ConfServUsuario o) throws BusinessException{
		ConfServUsuario dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServUsuario getPorPk(ConfServUsuarioId pId) throws BusinessException{
		ConfServUsuario domain = null;
		List<ConfServUsuario> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			mapa.put("intItemUsuario", pId.getIntItemUsuario());
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
	
	public List<ConfServUsuario> getPorCabecera(Integer intPersEmpresaPk, Integer intItemSolicitud) throws BusinessException{
		List<ConfServUsuario> lista = null;
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
