package pe.com.tumi.seguridad.service;

import java.util.HashMap;

import pe.com.tumi.common.service.GenericService;
import pe.com.tumi.common.util.DaoException;
import pe.com.tumi.seguridad.domain.Usuario;

public interface LoginService extends GenericService{

	public abstract HashMap validateUsuario (Usuario usuario, int intento, Long sistema) throws DaoException ;
	
	public abstract HashMap validateContrasenia (Usuario usuario, String newPassword, String verifyPassword, Long sistema) throws DaoException;
}
