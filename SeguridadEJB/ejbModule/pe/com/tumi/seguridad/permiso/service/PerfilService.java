package pe.com.tumi.seguridad.permiso.service;

import pe.com.tumi.framework.negocio.exception.BusinessException;
import pe.com.tumi.framework.negocio.factory.TumiFactory;
import pe.com.tumi.seguridad.login.bo.PerfilBO;
import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.permiso.bo.PermisoPerfilBO;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;

public class PerfilService {

	private PerfilBO boPerfil = (PerfilBO)TumiFactory.get(PerfilBO.class);
	private PermisoPerfilBO boPermisoPerfil = (PermisoPerfilBO)TumiFactory.get(PermisoPerfilBO.class);
	
	public Perfil grabarPerfilYPermiso(Perfil o)throws BusinessException{
		Perfil dto = null;
		PermisoPerfil permiso = null;
		try {
			dto = boPerfil.grabarPerfil(o);
			for(int i=0;i<o.getListaPermisoPerfil().size();i++){
				permiso = o.getListaPermisoPerfil().get(i);
				permiso.getId().setIntIdPerfil(dto.getId().getIntIdPerfil());
				boPermisoPerfil.grabarPermisoPerfil(permiso);
			}
		} catch (BusinessException e) {
			throw e;
		}
		return dto;
	}
	
}