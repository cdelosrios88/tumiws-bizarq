package pe.com.tumi.seguridad.login.validador;

import pe.com.tumi.common.util.Validacion;
import pe.com.tumi.seguridad.login.bean.CambioMsg;
import pe.com.tumi.seguridad.login.domain.Usuario;

public class CambioValidador {

	public static Boolean esValidoCambio(CambioMsg msg,String contrasena,String contrasenaNueva,String contrasenaValida,Usuario usuario){
		Boolean bValidar = true;
	    if(contrasena.trim().equals("")){
	    	msg.setContrasena("* Debe completar el campo Contrase�a");
	    	bValidar = false;
	    }else{
	    	if(usuario.getStrContrasena().equals(contrasena)){	    	
	    		msg.setContrasena(null);
	    	}else{
	    		msg.setContrasena("* La Contrase�a no es igual a la contrase�a anterior");
	    		bValidar = false;
	    	}
	    }
	    if(contrasenaNueva.trim().equals("")){
	    	msg.setContrasenaNueva("* Debe completar el campo Contrase�a");
	    	bValidar = false;
	    }else{
	    	if(Validacion.validarFormatoClave(contrasenaNueva)){
	    		if(contrasena!=null && contrasena.equals(contrasenaNueva)){
	    			msg.setContrasenaNueva("* La Contrase�a no debe ser el mismo que el anterior");
		    		bValidar = false;
	    		}else{
	    			msg.setContrasenaNueva(null);
	    		}
	    	}else{
	    		msg.setContrasenaNueva("* La Contrase�a no cumple el formato solicitado");
	    		bValidar = false;
	    	}
	    }
	    if(contrasenaValida.trim().equals("")){
	    	msg.setContrasenaValida("* Debe completar el campo de Validar Contrase�a");
	    	bValidar = false;
	    }else{
	    	if(!contrasenaNueva.trim().equals("") &&
	    	   !contrasenaNueva.trim().equals(contrasenaValida.trim())){
	    		msg.setContrasenaValida("* Las Contrase�as no son iguales. Aseg�rese de digitar correctamente");
		    	bValidar = false;
	    	}else{
	    		msg.setContrasenaValida(null);
	    	}
	    }
	    return bValidar;
	}
	
}
