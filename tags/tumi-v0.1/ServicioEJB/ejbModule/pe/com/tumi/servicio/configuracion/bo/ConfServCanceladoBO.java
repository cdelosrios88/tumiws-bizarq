package pe.com.tumi.servicio.configuracion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServCanceladoDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServCanceladoDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServCancelado;
import pe.com.tumi.servicio.configuracion.domain.ConfServCanceladoId;
import pe.com.tumi.servicio.configuracion.domain.ConfServUsuario;

public class ConfServCanceladoBO {
	
	private ConfServCanceladoDao dao = (ConfServCanceladoDao)TumiFactory.get(ConfServCanceladoDaoIbatis.class);
	
	public ConfServCancelado grabar(ConfServCancelado o) throws BusinessException{
		ConfServCancelado dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServCancelado modificar(ConfServCancelado o) throws BusinessException{
		ConfServCancelado dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServCancelado getPorPk(ConfServCanceladoId pId) throws BusinessException{
		ConfServCancelado domain = null;
		List<ConfServCancelado> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			mapa.put("intItemCancelado", pId.getIntItemCancelado());
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
	
	public List<ConfServCancelado> getPorCabecera(Integer intPersEmpresaPk, Integer intItemSolicitud) throws BusinessException{
		List<ConfServCancelado> lista = null;
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
