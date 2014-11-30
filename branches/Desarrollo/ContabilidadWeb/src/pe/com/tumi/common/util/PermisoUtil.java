package pe.com.tumi.common.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import pe.com.tumi.framework.negocio.ejb.factory.EJBFactory;
import pe.com.tumi.seguridad.login.domain.Usuario;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfilId;
import pe.com.tumi.seguridad.permiso.facade.PermisoFacadeRemote;


public class PermisoUtil {

	protected static Logger log = Logger.getLogger(PermisoUtil.class);
	
	public static boolean poseePermiso(Integer intIdTransaccion){
		boolean poseePermiso = Boolean.FALSE;
		try{
			
			Usuario usuario = (Usuario)getRequest().getSession().getAttribute("usuario");
			Integer intIdEmpresa = usuario.getPerfil().getId().getIntPersEmpresaPk();
			
			PermisoPerfilId permisoPerfilid = null;
			permisoPerfilid = new PermisoPerfilId();
			permisoPerfilid.setIntPersEmpresaPk(intIdEmpresa);
			permisoPerfilid.setIntIdTransaccion(intIdTransaccion);
			permisoPerfilid.setIntIdPerfil(usuario.getPerfil().getId().getIntIdPerfil());
			PermisoFacadeRemote permisoFacade = (PermisoFacadeRemote)EJBFactory.getRemote(PermisoFacadeRemote.class);
			
			PermisoPerfil permisoPerfil = permisoFacade.getPermisoPerfilPorPk(permisoPerfilid);
			
			if(permisoPerfil != null && permisoPerfil.getId()!=null){
				log.info("Posee permiso de transaccion "+intIdTransaccion);
				poseePermiso = Boolean.TRUE;
			}
		}catch(Exception e){
			log.error(e.getMessage(),e);
		}
		return poseePermiso;
	}
	
	protected static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
}