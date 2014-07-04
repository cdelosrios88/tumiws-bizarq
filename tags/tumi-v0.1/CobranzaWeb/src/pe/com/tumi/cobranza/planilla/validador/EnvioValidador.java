package pe.com.tumi.cobranza.planilla.validador;

import pe.com.tumi.cobranza.planilla.bean.EnvioMsg;
import pe.com.tumi.cobranza.planilla.domain.composite.EnvioConceptoComp;

public class EnvioValidador {

	public static Boolean validarUnicidad(EnvioMsg msg,EnvioConceptoComp dto){
		Boolean bValidar = true;
		Integer intValue = null;
		String strValue = null;
		/*
		intValue = dto.getEnvioConcepto().getIntCuentaPk();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msg.setTipoPersonaCod("* Debe seleccionar un Tipo de Persona.");
	    	bValidar = false;
	    }else{
	    	msg.setTipoPersonaCod(null);
	    }
	    
	    strValue = dto.getEstructuraDetalle().getStrCodigoExterno();
	    if(strValue == null || strValue.trim().equals("")){
	    	msg.setNroIdentidad("* Debe completar el Documento de la Persona.");
	    	bValidar = false;
	    }else{
	    	msg.setNroIdentidad(null);
	    }
	    */
		return bValidar;
	}
	
	public static Boolean validarMatenimiento(EnvioMsg msg,EnvioConceptoComp dto){
		Boolean bValidar = true;
		Integer intValue = null;
		String strValue = null;
		/*
		intValue = dto.getEnvioConcepto().getIntCuentaPk();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msg.setTipoUsuario("* Debe seleccionar un Tipo de Usuario.");
	    	bValidar = false;
	    }else{
	    	msg.setTipoUsuario(null);
	    }
	    
	    strValue = dto.getEstructuraDetalle().getStrCodigoExterno();
	    if(strValue!=null && strValue.trim().equals("")){
	    	msg.setUsuario("* Debe completar el campo Nombre de Usuario");
	    	bValidar = false;
	    }else{
	    	msg.setUsuario(null);
	    }
	    */
		bValidar = true;
	    return bValidar;
	}
	
}
