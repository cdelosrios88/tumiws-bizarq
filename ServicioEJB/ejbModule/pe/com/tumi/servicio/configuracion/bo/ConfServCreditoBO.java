package pe.com.tumi.servicio.configuracion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServCreditoDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServCreditoDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoId;

public class ConfServCreditoBO {
	
	private ConfServCreditoDao dao = (ConfServCreditoDao)TumiFactory.get(ConfServCreditoDaoIbatis.class);
	
	public ConfServCredito grabar(ConfServCredito o) throws BusinessException{
		ConfServCredito dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServCredito modificar(ConfServCredito o) throws BusinessException{
		ConfServCredito dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServCredito getPorPk(ConfServCreditoId pId) throws BusinessException{
		ConfServCredito domain = null;
		List<ConfServCredito> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			mapa.put("intItemCredito", pId.getIntItemCredito());
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
	
	public List<ConfServCredito> getPorCabecera(Integer intPersEmpresaPk, Integer intItemSolicitud) throws BusinessException{
		List<ConfServCredito> lista = null;
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
