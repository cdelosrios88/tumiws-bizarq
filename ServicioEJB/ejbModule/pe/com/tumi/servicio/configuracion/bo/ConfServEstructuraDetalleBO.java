package pe.com.tumi.servicio.configuracion.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServEstructuraDetalleDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServEstructuraDetalleDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalle;
import pe.com.tumi.servicio.configuracion.domain.ConfServEstructuraDetalleId;

public class ConfServEstructuraDetalleBO {
	
	private ConfServEstructuraDetalleDao dao = (ConfServEstructuraDetalleDao)TumiFactory.get(ConfServEstructuraDetalleDaoIbatis.class);
	
	public ConfServEstructuraDetalle grabar(ConfServEstructuraDetalle o) throws BusinessException{
		ConfServEstructuraDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServEstructuraDetalle modificar(ConfServEstructuraDetalle o) throws BusinessException{
		ConfServEstructuraDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServEstructuraDetalle getPorPk(ConfServEstructuraDetalleId pId) throws BusinessException{
		ConfServEstructuraDetalle domain = null;
		List<ConfServEstructuraDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			mapa.put("intItemEstructura", pId.getIntItemEstructura());
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
	
	public List<ConfServEstructuraDetalle> getPorCabecera(ConfServEstructuraDetalleId pId) throws BusinessException{
		List<ConfServEstructuraDetalle> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
			lista = dao.getListaPorCabecera(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConfServEstructuraDetalle> getPorCabecera(Integer intPersEmpresaPk, Integer intItemSolicitud) throws BusinessException{
		List<ConfServEstructuraDetalle> lista = null;
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