package pe.com.tumi.servicio.configuracion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServCaptacionDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServCaptacionDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServCaptacion;
import pe.com.tumi.servicio.configuracion.domain.ConfServCaptacionId;
import pe.com.tumi.servicio.configuracion.domain.ConfServCredito;

public class ConfServCaptacionBO {
	
	private ConfServCaptacionDao dao = (ConfServCaptacionDao)TumiFactory.get(ConfServCaptacionDaoIbatis.class);
	
	public ConfServCaptacion grabar(ConfServCaptacion o) throws BusinessException{
		ConfServCaptacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServCaptacion modificar(ConfServCaptacion o) throws BusinessException{
		ConfServCaptacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServCaptacion getPorPk(ConfServCaptacionId pId) throws BusinessException{
		ConfServCaptacion domain = null;
		List<ConfServCaptacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			mapa.put("intItemCaptacion", pId.getIntItemCaptacion());
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
	
	public List<ConfServCaptacion> getPorCabecera(Integer intPersEmpresaPk, Integer intItemSolicitud) throws BusinessException{
		List<ConfServCaptacion> lista = null;
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
