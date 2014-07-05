package pe.com.tumi.seguridad.permiso.validador;

import pe.com.tumi.seguridad.login.domain.Perfil;
import pe.com.tumi.seguridad.permiso.bean.PerfilMsg;
import pe.com.tumi.seguridad.permiso.domain.PermisoPerfil;

public class PerfilValidador {

	public static Boolean validarPerfil(PerfilMsg msgPerfil,Perfil pPerfil){
		Boolean bValidar = true;
		Integer intValue = null;
		String strValue = null;
		
		intValue = pPerfil.getId().getIntPersEmpresaPk();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msgPerfil.setIdEmpresa("* Debe seleccionar una Empresa.");
	    	bValidar = false;
	    }else{
	    	msgPerfil.setIdEmpresa(null);
	    }
	    
	    strValue = pPerfil.getStrDescripcion();
	    if(strValue!=null && strValue.trim().equals("")){
	    	msgPerfil.setNombre("* Debe Ingresar un Nombre.");
	    	bValidar = false;
	    }else{
	    	msgPerfil.setNombre(null);
	    }
	    
	    intValue = pPerfil.getIntTipoPerfil();
	    if(intValue == null || intValue.compareTo(new Integer(0))==0){
	    	msgPerfil.setIdTipoPerfil("* Debe seleccionar un Perfil.");
	    	bValidar = false;
	    }else{
	    	msgPerfil.setIdTipoPerfil(null);
	    }
	    
	    if(pPerfil.getDtDesde() == null){
	    	msgPerfil.setDtDesde("* Debe Ingresar una Fecha Inicial.");
	    	bValidar = false;
	    }else{
	    	msgPerfil.setDtDesde(null);
	    }
	    
	    if(pPerfil.getDtHasta() == null){
	    	//msgPerfil.setDtHasta("* Debe Ingresar una Fecha Final.");
	    	//bValidar = false;
	    	msgPerfil.setDtHasta(null);
	    }else{
	    	if(pPerfil.getDtDesde()!= null && pPerfil.getDtDesde().before(pPerfil.getDtHasta())){
	    		msgPerfil.setDtHasta(null);
	    	}else{
	    		msgPerfil.setDtHasta("* Fecha Final Debe ser menor a Fecha Inicial.");
	    		bValidar = false;
	    	}
	    }
	    
	    intValue = pPerfil.getIntIdEstado();
	    if(intValue ==null || intValue.compareTo(new Integer(0))==0){
	    	msgPerfil.setIdEstado("* Debe seleccionar un Estado del Menú.");
	    	bValidar = false;
	    }else{
	    	msgPerfil.setIdEstado(null);
	    }
	    
	    if(pPerfil.getListaPermisoPerfil() == null || pPerfil.getListaPermisoPerfil().size()==0){
	    	msgPerfil.setListaPerfil("* Debe ingresar por lo menos un Permiso.");
	    	bValidar = false;
	    }else{
	    	msgPerfil.setListaPerfil(null);
	    }
	    
	    return bValidar;
	}
	
	public static Boolean validarPermiso(PerfilMsg msgPerfil,PermisoPerfil permiso){
		Boolean bValidar = true;
		Integer intValue = null;
		
		intValue = permiso.getIntIdEstado();
	    if(intValue == null || intValue.compareTo(new Integer(0))==0){
	    	msgPerfil.getMsgPermiso().setIdEstado("* Debe seleccionar una Estado.");
	    	bValidar = false;
	    }else{
	    	msgPerfil.getMsgPermiso().setIdEstado(null);
	    }
	    return bValidar;
	}
	
}
