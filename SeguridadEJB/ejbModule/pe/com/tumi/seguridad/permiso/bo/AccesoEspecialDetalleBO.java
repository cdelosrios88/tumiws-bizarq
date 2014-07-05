package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.AccesoEspecialDao;
import pe.com.tumi.seguridad.permiso.dao.AccesoEspecialDetalleDao;
import pe.com.tumi.seguridad.permiso.dao.ComputadoraDao;
import pe.com.tumi.seguridad.permiso.dao.impl.AccesoEspecialDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.impl.AccesoEspecialDetalleDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.impl.ComputadoraDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecial;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalle;
import pe.com.tumi.seguridad.permiso.domain.AccesoEspecialDetalleId;
import pe.com.tumi.seguridad.permiso.domain.Computadora;
import pe.com.tumi.seguridad.permiso.domain.ComputadoraId;

public class AccesoEspecialDetalleBO {

	private AccesoEspecialDetalleDao dao = (AccesoEspecialDetalleDao)TumiFactory.get(AccesoEspecialDetalleDaoIbatis.class);
	
	public AccesoEspecialDetalle grabar(AccesoEspecialDetalle o) throws BusinessException {
		AccesoEspecialDetalle dto = null;
		try{
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AccesoEspecialDetalle modificar(AccesoEspecialDetalle o) throws BusinessException{
		AccesoEspecialDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public AccesoEspecialDetalle getPorPk(AccesoEspecialDetalleId pId) throws BusinessException{
		List<AccesoEspecialDetalle> lista = null;
		AccesoEspecialDetalle domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intItemAcceso", pId.getIntItemAcceso());
			mapa.put("intIdDiaSemana", pId.getIntIdDiaSemana());
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

	public List<AccesoEspecialDetalle> getPorCabecera(AccesoEspecial o) throws BusinessException{
		List<AccesoEspecialDetalle> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intItemAcceso", o.getIntItemAcceso());
			lista = dao.getListaPorCabecera(mapa);			
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
	
}
