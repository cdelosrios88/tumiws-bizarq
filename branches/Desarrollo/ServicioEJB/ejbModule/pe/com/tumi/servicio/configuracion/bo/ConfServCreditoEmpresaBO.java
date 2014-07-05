package pe.com.tumi.servicio.configuracion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServCreditoEmpresaDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServCreditoEmpresaDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoEmpresa;
import pe.com.tumi.servicio.configuracion.domain.ConfServCreditoEmpresaId;
import pe.com.tumi.servicio.configuracion.domain.ConfServPerfil;

public class ConfServCreditoEmpresaBO {
	
	private ConfServCreditoEmpresaDao dao = (ConfServCreditoEmpresaDao)TumiFactory.get(ConfServCreditoEmpresaDaoIbatis.class);
	
	public ConfServCreditoEmpresa grabar(ConfServCreditoEmpresa o) throws BusinessException{
		ConfServCreditoEmpresa dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServCreditoEmpresa modificar(ConfServCreditoEmpresa o) throws BusinessException{
		ConfServCreditoEmpresa dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServCreditoEmpresa getPorPk(ConfServCreditoEmpresaId pId) throws BusinessException{
		ConfServCreditoEmpresa domain = null;
		List<ConfServCreditoEmpresa> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			mapa.put("intParaTipoCreditoEmpresaCod", pId.getIntParaTipoCreditoEmpresaCod());
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
	
	public List<ConfServCreditoEmpresa> getPorCabecera(Integer intPersEmpresaPk, Integer intItemSolicitud) throws BusinessException{
		List<ConfServCreditoEmpresa> lista = null;
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
