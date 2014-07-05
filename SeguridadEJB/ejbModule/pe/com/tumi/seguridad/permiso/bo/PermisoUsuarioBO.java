package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.permiso.dao.PermisoUsuarioDao;
import pe.com.tumi.seguridad.permiso.dao.impl.PermisoUsuarioDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.PermisoUsuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoUsuarioId;

public class PermisoUsuarioBO {

	private PermisoUsuarioDao dao = (PermisoUsuarioDao)TumiFactory.get(PermisoUsuarioDaoIbatis.class);
	
	public PermisoUsuario grabarPermisoUsuario(PermisoUsuario o) throws BusinessException {
		PermisoUsuario dto = null;
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
	
	public PermisoUsuario modificarPermisoUsuario(PermisoUsuario o) throws BusinessException{
		PermisoUsuario dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PermisoUsuario getListaPermisoUsuarioPorPk(PermisoUsuarioId pId) throws BusinessException{
		List<PermisoUsuario> lista = null;
		PermisoUsuario domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdPerfil", pId.getIntIdPerfil());
			mapa.put("dtDesde", pId.getDtDesde());
			mapa.put("intPersPersonaPk", pId.getIntPersPersonaPk());
			mapa.put("intIdTransaccion", pId.getIntIdTransaccion());
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
