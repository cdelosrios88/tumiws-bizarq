package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.SolicitudCambioDao;
import pe.com.tumi.seguridad.permiso.dao.impl.SolicitudCambioDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.SolicitudCambio;
import pe.com.tumi.seguridad.permiso.domain.SolicitudCambioId;

public class SolicitudCambioBO {

	private SolicitudCambioDao dao = (SolicitudCambioDao)TumiFactory.get(SolicitudCambioDaoIbatis.class);
	
	public SolicitudCambio grabarSolicitudCambio(SolicitudCambio o) throws BusinessException {
		SolicitudCambio dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SolicitudCambio modificarSolicitudCambio(SolicitudCambio o) throws BusinessException{
		SolicitudCambio dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public SolicitudCambio getListaSolicitudCambioPorPk(SolicitudCambioId pId) throws BusinessException{
		List<SolicitudCambio> lista = null;
		SolicitudCambio domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
			mapa.put("intItem", pId.getIntItem());
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
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return domain;
	}
}
