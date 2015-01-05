package pe.com.tumi.servicio.configuracion.bo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.configuracion.dao.ConfServSolicitudDao;
import pe.com.tumi.servicio.configuracion.dao.impl.ConfServSolicitudDaoIbatis;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitud;
import pe.com.tumi.servicio.configuracion.domain.ConfServSolicitudId;

public class ConfServSolicitudBO {
	
	private ConfServSolicitudDao dao = (ConfServSolicitudDao)TumiFactory.get(ConfServSolicitudDaoIbatis.class);
	
	public ConfServSolicitud grabar(ConfServSolicitud o) throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServSolicitud modificar(ConfServSolicitud o) throws BusinessException{
		ConfServSolicitud dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public ConfServSolicitud getPorPk(ConfServSolicitudId pId) throws BusinessException{
		ConfServSolicitud domain = null;
		List<ConfServSolicitud> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intItemSolicitud", pId.getIntItemSolicitud());
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
	
	public List<ConfServSolicitud> buscar(ConfServSolicitud confServSolicitud, Date fechaFiltroInicio, Date fechaFiltroFin) throws BusinessException{
		List<ConfServSolicitud> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intParaTipoOperacionCod", confServSolicitud.getIntParaTipoOperacionCod());
			mapa.put("fechaFiltroInicio", fechaFiltroInicio);
			mapa.put("fechaFiltroFin", fechaFiltroFin);
			mapa.put("intParaEstadoCod", confServSolicitud.getIntParaEstadoCod());
			lista = dao.getListaPorBuscar(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
	public List<ConfServSolicitud> getListaPorTipoOperacionTipoRequisito(ConfServSolicitud confServSolicitud) throws BusinessException{
		List<ConfServSolicitud> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intParaTipoRequertoAutorizaCod", confServSolicitud.getIntParaTipoRequertoAutorizaCod());
			mapa.put("intParaTipoOperacionCod", confServSolicitud.getIntParaTipoOperacionCod());
			mapa.put("intParaSubtipoOperacionCod", confServSolicitud.getIntParaSubtipoOperacionCod());
			
			lista = dao.getListaPorTipoOperacionTipoRequisito(mapa);

		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	public List<ConfServSolicitud> buscarRequisito(ConfServSolicitud confServSolicitud, Date fechaFiltroInicio, Date fechaFiltroFin) throws BusinessException{
		List<ConfServSolicitud> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intParaTipoOperacionCod", confServSolicitud.getIntParaTipoOperacionCod());
			mapa.put("fechaFiltroInicio", fechaFiltroInicio);
			mapa.put("fechaFiltroFin", fechaFiltroFin);
			mapa.put("intParaEstadoCod", confServSolicitud.getIntParaEstadoCod());
			lista = dao.getListaPorBuscarRequisito(mapa);
			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
