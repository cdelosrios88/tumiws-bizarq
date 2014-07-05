package pe.com.tumi.seguridad.permiso.validador;

import java.util.List;

import pe.com.tumi.seguridad.permiso.bean.MenuMsg;
import pe.com.tumi.seguridad.permiso.domain.Transaccion;

public class MenuValidador {

	public static Boolean validarMenu(MenuMsg msgMenu,Transaccion pMenu,List<Transaccion> listaMenu){
		Boolean bValidar = true;
		Integer intValue = null;
		intValue = pMenu.getId().getIntPersEmpresaPk();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msgMenu.setIdEmpresa("* Debe seleccionar una Empresa.");
	    	bValidar = false;
	    }else{
	    	msgMenu.setIdEmpresa(null);
	    }
	    
	    intValue = pMenu.getIntTipoMenu();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msgMenu.setTipoMenu("* Debe seleccionar un Tipo de Menú.");
	    	bValidar = false;
	    }else{
	    	msgMenu.setTipoMenu(null);
	    }
	    
	    intValue = pMenu.getIntIdEstado();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msgMenu.setIdEstado("* Debe seleccionar un Estado del Menú.");
	    	bValidar = false;
	    }else{
	    	msgMenu.setIdEstado(null);
	    }
	    
	    /**********************************************************************************/
	    if(listaMenu == null || listaMenu.size()==0){
	    	msgMenu.setListaMenu("* Debe ingresar por lo menos un Menú.");
	    	bValidar = false;
	    }else{
	    	msgMenu.setListaMenu(null);
	    }
	    
	    return bValidar;
	}
	
	public static Boolean validarSubMenu(MenuMsg msgMenu,Transaccion subMenu){
		Boolean bValidar = true;
		Integer intValue = null;
		String strValue = null;
		intValue = subMenu.getIntCrecimiento(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgMenu.getMsgSubMenu().setTipoCrecimiento("* Debe seleccionar un Tipo de Crecimiento.");
	    	bValidar = false;
	    }else{
	    	msgMenu.getMsgSubMenu().setTipoCrecimiento(null);
	    }
		
		intValue = subMenu.getIntOrden(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgMenu.getMsgSubMenu().setTipoOrden("* Debe seleccionar un Tipo de Orden.");
	    	bValidar = false;
	    }else{
	    	msgMenu.getMsgSubMenu().setTipoOrden(null);
	    }
		
		intValue = subMenu.getIntNivel(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgMenu.getMsgSubMenu().setNivel("* Debe seleccionar un Nivel.");
	    	bValidar = false;
	    }else{
	    	msgMenu.getMsgSubMenu().setNivel(null);
	    }
		
		intValue = subMenu.getIntFinal();
		if(intValue.compareTo(new Integer(1))==0 && subMenu.getListaTransaccion()!= null && subMenu.getListaTransaccion().size()>0){
			msgMenu.getMsgSubMenu().setTipoFinal("* No Debe tener Sub-Menus Asociados.");
	    	bValidar = false;
		}else{
			msgMenu.getMsgSubMenu().setTipoFinal(null);
		}
		
		strValue = subMenu.getStrNombre();
		if(strValue == null || strValue.trim().equals("")){
			msgMenu.getMsgSubMenu().setNombre("* Debe ingresar un Nombre.");
	    	bValidar = false;
	    }else{
	    	msgMenu.getMsgSubMenu().setNombre(null);
	    }
		return bValidar;
	}
	
}
