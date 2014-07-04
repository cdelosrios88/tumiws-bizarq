package pe.com.tumi.seguridad.permiso.bo;

import java.util.HashMap;
import java.util.List;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.exception.DAOException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.domain.PerfilId;
import pe.com.tumi.seguridad.permiso.dao.PermisoPerfilDao;
import pe.com.tumi.seguridad.permiso.dao.impl.PermisoPerfilDaoIbatis;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;

public class PermisoPerfilBO {

	private PermisoPerfilDao dao = (PermisoPerfilDao)TumiFactory.get(PermisoPerfilDaoIbatis.class);
	
	public PermisoPerfil grabarPermisoPerfil(PermisoPerfil o) throws BusinessException {
		PermisoPerfil dto = null;
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
	
	public PermisoPerfil modificarPermisoPerfil(PermisoPerfil o) throws BusinessException{
		PermisoPerfil dto = null;
		try{
			dto = dao.modificar(o);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return dto;
	}
	
	public PermisoPerfil getPermisoPerfilPorPk(PermisoPerfilId pId) throws BusinessException{
		List<PermisoPerfil> lista = null;
		PermisoPerfil domain = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdPerfil", pId.getIntIdPerfil());
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
	
	public List<PermisoPerfil> getListaPermisoPerfilPorPkPerfil(PerfilId pId) throws BusinessException{
		List<PermisoPerfil> lista = null;
		try{
			HashMap<String, Object> mapa = new HashMap<String, Object>();
			mapa.put("intPersEmpresaPk", pId.getIntPersEmpresaPk());
			mapa.put("intIdPerfil", pId.getIntIdPerfil());
			lista = dao.getListaPorPkPerfil(mapa);
		}catch(DAOException e){
			throw new BusinessException(e);
		}catch(Exception e) {
			throw new BusinessException(e);
		}
		return lista;
	}
}
