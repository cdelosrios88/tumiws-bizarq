package pe.com.tumi.servicio.solicitudPrestamo.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.servicio.solicitudPrestamo.dao.AutorizaVerificacionDao;
import pe.com.tumi.servicio.solicitudPrestamo.dao.impl.AutorizaVerificacionDaoIbatis;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaCredito;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacion;
import pe.com.tumi.servicio.solicitudPrestamo.domain.AutorizaVerificacionId;
import pe.com.tumi.servicio.solicitudPrestamo.domain.ExpedienteCreditoId;

public class AutorizaVerificacionBO {
	
	private AutorizaVerificacionDao dao = (AutorizaVerificacionDao)TumiFactory.get(AutorizaVerificacionDaoIbatis.class);
	
	public AutorizaVerificacion grabar(AutorizaVerificacion o) throws BusinessException{
		AutorizaVerificacion dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaVerificacion modificar(AutorizaVerificacion o) throws BusinessException{
		AutorizaVerificacion dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AutorizaVerificacion getPorPk(AutorizaVerificacionId pId) throws BusinessException{
		AutorizaVerificacion domain = null;
		List<AutorizaVerificacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			mapa.put("intItemAutorizaVerifica", pId.getIntItemAutorizaVerifica());
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
	
	public List<AutorizaVerificacion> getListaPorPkExpedienteCredito(ExpedienteCreditoId pId) throws BusinessException{
		List<AutorizaVerificacion> lista = null;
		try{
			HashMap<String,Object> mapa = new HashMap<String,Object>();
			mapa.put("intPersEmpresaPk", 		pId.getIntPersEmpresaPk());
			mapa.put("intCuenta", 				pId.getIntCuentaPk());
			mapa.put("intItemExpediente", 		pId.getIntItemExpediente());
			mapa.put("intItemDetExpediente", 	pId.getIntItemDetExpediente());
			lista = dao.getListaPorExpedienteCredito(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}