package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.DiasAccesosDao;
import pe.com.tumi.seguridad.permiso.dao.DiasAccesosDetalleDao;
import pe.com.tumi.seguridad.permiso.dao.DocumentoDao;
import pe.com.tumi.seguridad.permiso.dao.impl.DiasAccesosDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.impl.DiasAccesosDetalleDaoIbatis;
import pe.com.tumi.seguridad.permiso.dao.impl.DocumentoDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesos;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalle;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosDetalleId;
import pe.com.tumi.seguridad.permiso.domain.DiasAccesosId;
import pe.com.tumi.seguridad.permiso.domain.Documento;
import pe.com.tumi.seguridad.permiso.domain.DocumentoId;

public class DiasAccesosDetalleBO {

	private DiasAccesosDetalleDao dao = (DiasAccesosDetalleDao)TumiFactory.get(DiasAccesosDetalleDaoIbatis.class);
	
	public DiasAccesosDetalle grabar(DiasAccesosDetalle o) throws BusinessException {
		DiasAccesosDetalle dto = null;
		try{
			o.setIntIdEstado(Constante.PARAM_T_ESTADOUNIVERSAL_ACTIVO);
			dto = dao.grabar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public DiasAccesosDetalle modificar(DiasAccesosDetalle o) throws BusinessException{
		DiasAccesosDetalle dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public DiasAccesosDetalle getListaPorPk(DiasAccesosDetalleId pId) throws BusinessException{
		List<DiasAccesosDetalle> lista = null;
		DiasAccesosDetalle domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresa", pId.getIntPersEmpresa());
			mapa.put("intIdTipoSucursal", pId.getIntIdTipoSucursal());
			mapa.put("intIdDiaSemana", pId.getIntIdDiaSemana());
			mapa.put("intItemDiasAccesos", pId.getIntItemDiasAccesos());
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
	
	public List <DiasAccesosDetalle> getListaPorCabecera(DiasAccesos da) throws BusinessException{
		List<DiasAccesosDetalle> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresa", da.getId().getIntPersEmpresa());
			mapa.put("intIdTipoSucursal", da.getId().getIntIdTipoSucursal());
			mapa.put("intItemDiasAccesos", da.getId().getIntItemDiaAccesos());
			lista = dao.getListaPorCabecera(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
