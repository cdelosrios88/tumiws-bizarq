package pe.com.tumi.seguridad.login.validador;

import pe.com.tumi.seguridad.login.bean.PortalMsg;

public class PortalValidador {

	public static Boolean validarUsuario(PortalMsg msg,pe.com.tumi.seguridad.login.domain.Usuario pUsuario){
		Boolean bValidar = true;
	    if(pUsuario.getStrUsuario().trim().equals("")){
	    	msg.setUsuario("* Debe completar el campo Nombre de Usuario");
	    	bValidar = false;
	    }else{
	    	msg.setUsuario(null);
	    }
	    if(pUsuario.getStrContrasena().trim().equals("")){
	    	msg.setContrasena("* Debe completar el campo Contraseña");
	    	bValidar = false;
	    }else{
	    	msg.setContrasena(null);
	    }
	    return bValidar;
	}
	
	public static Boolean validarEmpresa(PortalMsg msg,Integer intIdEmpresa,Integer intIdSucursal,Integer intIdSubSucursal,Integer intIdPerfil){
		Boolean bValidar = true;
		if(intIdEmpresa == null || intIdEmpresa.compareTo(new Integer(0))==0){
			msg.setIdEmpresa("* Debe seleccionar una Empresa.");
	    	bValidar = false;
	    }else{
	    	msg.setIdEmpresa(null);
	    }
		if(intIdSucursal == null || intIdSucursal.compareTo(new Integer(0))==0){
			msg.setIdSucursal("* Debe seleccionar una Sucursal.");
	    	bValidar = false;
	    }else{
	    	msg.setIdSucursal(null);
	    }
		if(intIdSubSucursal == null || intIdSubSucursal.compareTo(new Integer(0))==0){
			msg.setIdSubSucursal("* Debe seleccionar una SubSucursal.");
	    	bValidar = false;
	    }else{
	    	msg.setIdSubSucursal(null);
	    }
		if(intIdPerfil == null || intIdPerfil.compareTo(new Integer(0))==0){
			msg.setIdPerfil("* Debe seleccionar un Perfil.");
	    	bValidar = false;
	    }else{
	    	msg.setIdPerfil(null);
	    }
		return bValidar;
	}
}
