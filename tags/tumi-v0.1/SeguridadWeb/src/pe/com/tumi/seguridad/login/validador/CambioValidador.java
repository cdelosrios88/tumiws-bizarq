package pe.com.tumi.seguridad.login.validador;

import pe.com.tumi.common.util.Validacion;
import pe.com.tumi.seguridad.login.bean.CambioMsg;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class CambioValidador {

	public static Boolean esValidoCambio(CambioMsg msg,String contrasena,String contrasenaNueva,String contrasenaValida,Usuario usuario){
		Boolean bValidar = true;
	    if(contrasena.trim().equals("")){
	    	msg.setContrasena("* Debe completar el campo Contraseña");
	    	bValidar = false;
	    }else{
	    	if(usuario.getStrContrasena().equals(contrasena)){	    	
	    		msg.setContrasena(null);
	    	}else{
	    		msg.setContrasena("* La Contraseña no es igual a la contraseña anterior");
	    		bValidar = false;
	    	}
	    }
	    if(contrasenaNueva.trim().equals("")){
	    	msg.setContrasenaNueva("* Debe completar el campo Contraseña");
	    	bValidar = false;
	    }else{
	    	if(Validacion.validarFormatoClave(contrasenaNueva)){
	    		if(contrasena!=null && contrasena.equals(contrasenaNueva)){
	    			msg.setContrasenaNueva("* La Contraseña no debe ser el mismo que el anterior");
		    		bValidar = false;
	    		}else{
	    			msg.setContrasenaNueva(null);
	    		}
	    	}else{
	    		msg.setContrasenaNueva("* La Contraseña no cumple el formato solicitado");
	    		bValidar = false;
	    	}
	    }
	    if(contrasenaValida.trim().equals("")){
	    	msg.setContrasenaValida("* Debe completar el campo de Validar Contraseña");
	    	bValidar = false;
	    }else{
	    	if(!contrasenaNueva.trim().equals("") &&
	    	   !contrasenaNueva.trim().equals(contrasenaValida.trim())){
	    		msg.setContrasenaValida("* Las Contraseñas no son iguales. Asegúrese de digitar correctamente");
		    	bValidar = false;
	    	}else{
	    		msg.setContrasenaValida(null);
	    	}
	    }
	    return bValidar;
	}
	
}
