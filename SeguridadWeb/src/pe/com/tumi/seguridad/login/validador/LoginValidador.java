package pe.com.tumi.seguridad.login.validador;

import pe.com.tumi.common.util.Constante;
import pe.com.tumi.common.util.Validacion;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.seguridad.login.bean.UsuarioMsg;
import pe.com.tumi.seguridad.login.domain.UsuarioPerfil;
import pe.com.tumi.seguridad.login.domain.UsuarioSubSucursal;
import pe.com.tumi.seguridad.login.domain.UsuarioSucursal;

public class LoginValidador {

	public static Boolean validarUsuarioUnico(UsuarioMsg msgUsuario,pe.com.tumi.seguridad.login.domain.Usuario pUsuario){
		Boolean bValidar = true;
		Integer intValue = null;
		String strValue = null;
		intValue = pUsuario.getPersona().getIntTipoPersonaCod();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msgUsuario.setTipoPersonaCod("* Debe seleccionar un Tipo de Persona.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setTipoPersonaCod(null);
	    }
	    
	    intValue = pUsuario.getPersona().getListaDocumento().get(0).getIntTipoIdentidadCod();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msgUsuario.setTipoIdentidad("* Debe seleccionar un Tipo de Documento.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setTipoIdentidad(null);
	    }
	    
	    strValue = pUsuario.getPersona().getListaDocumento().get(0).getStrNumeroIdentidad();
	    if(strValue == null || strValue.trim().equals("")){
	    	msgUsuario.setNroIdentidad("* Debe completar el Documento de la Persona.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setNroIdentidad(null);
	    }
		return bValidar;
	}
	
	public static Boolean validarUsuario(UsuarioMsg msgUsuario,pe.com.tumi.seguridad.login.domain.Usuario pUsuario,String strTipoMantenimiento){
		pe.com.tumi.seguridad.login.domain.EmpresaUsuario lEmpresaUsuario = null;
		UsuarioPerfil lUsuarioPerfilLoop = null;
		UsuarioSucursal lUsuarioSucursalLoop = null;
		UsuarioSubSucursal lUsuarioSubSucursalLoop = null;
		Boolean bValidar = true;
		Integer intValue = null;
		intValue = pUsuario.getIntTipoUsuario();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msgUsuario.setTipoUsuario("* Debe seleccionar un Tipo de Usuario.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setTipoUsuario(null);
	    }
	    
	    intValue = pUsuario.getPersona().getIntTipoPersonaCod();
	    if(intValue.compareTo(new Integer(0))==0){
	    	msgUsuario.setTipoPersonaCod("* Debe seleccionar un Tipo de Persona.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setTipoPersonaCod(null);
	    }
	    
	    if(pUsuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_NATURAL)==0){
		    if(pUsuario.getPersona().getNatural()!= null){
		    	if(pUsuario.getPersona().getListaDocumento().get(0).getStrNumeroIdentidad().trim().equals("")){
		    		msgUsuario.setNroIdentidad("* Debe completar el campo de Nro. Doc.");
			    	bValidar = false;
			    }else{
			    	msgUsuario.setNroIdentidad(null);
			    }
			    if(pUsuario.getPersona().getNatural().getStrApellidoPaterno().trim().equals("")){
			    	msgUsuario.setApellidoPaterno("* Debe completar el campo Apellido Paterno");
			    	bValidar = false;
			    }else{
			    	msgUsuario.setApellidoPaterno(null);
			    }
			    if(pUsuario.getPersona().getNatural().getStrApellidoMaterno().trim().equals("")){
			    	msgUsuario.setApellidoMaterno("* Debe completar el campo Apellido Materno");
			    	bValidar = false;
			    }else{
			    	msgUsuario.setApellidoMaterno("");
			    }
			    if(pUsuario.getPersona().getNatural().getStrNombres().trim().equals("")){
			    	msgUsuario.setNombres("* Debe completar el campo Nombres");
			    	bValidar = false;
			    }else{
			    	msgUsuario.setNombres(null);
			    }
		    }
	    }else if(pUsuario.getPersona().getIntTipoPersonaCod().compareTo(Constante.PARAM_T_TIPOPERSONA_JURIDICA)==0){
	    	if(pUsuario.getPersona().getJuridica() != null){
		    	if(pUsuario.getPersona().getJuridica().getStrRazonSocial().trim().equals("")){
		    		msgUsuario.setRazonSocial("* Debe completar el campo Razon Social");
			    	bValidar = false;
			    }else{
			    	msgUsuario.setRazonSocial(null);
			    }
		    	if(pUsuario.getPersona().getJuridica().getStrNombreComercial().trim().equals("")){
		    		msgUsuario.setNombreComercial("* Debe completar el campo Nombre Comercial");
			    	bValidar = false;
			    }else{
			    	msgUsuario.setNombreComercial(null);
			    }
	    	}
	    }
	    if(pUsuario.getStrUsuario().trim().equals("")){
	    	msgUsuario.setUsuario("* Debe completar el campo Nombre de Usuario");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setUsuario(null);
	    }
	    if(pUsuario.getStrContrasena().trim().equals("")){
	    	msgUsuario.setContrasena("* Debe completar el campo Contraseña");
	    	bValidar = false;
	    }else{
	    	if(strTipoMantenimiento.equals(Constante.MANTENIMIENTO_GRABAR)){
		    	if(Validacion.validarFormatoClave(pUsuario.getStrContrasena())){	    	
		    		msgUsuario.setContrasena(null);
		    	}else{
		    		msgUsuario.setContrasena("* La Contraseña no cumple el formato solicitado");
		    		bValidar = false;
		    	}
	    	}else{
	    		msgUsuario.setContrasena(null);
	    	}
	    }
	    if(pUsuario.getStrContrasenaValidacion().trim().equals("")){
	    	msgUsuario.setContrasenaValida("* Debe completar el campo de Validar Contraseña");
	    	bValidar = false;
	    }else{
	    	if(!pUsuario.getStrContrasena().trim().equals("") &&
	    	   !pUsuario.getStrContrasena().trim().equals(pUsuario.getStrContrasenaValidacion().trim())){
	    		msgUsuario.setContrasenaValida("* Las Contraseñas no son iguales. Asegúrese de digitar correctamente");
		    	bValidar = false;
	    	}else{
	    		msgUsuario.setContrasenaValida(null);
	    	}
	    }
	    /**********************************************************************************/
	    if(pUsuario.getPersona().getListaDomicilio() == null || pUsuario.getPersona().getListaDomicilio().size()==0){
	    	msgUsuario.setListaDomicilio("* Debe ingresar por lo menos un Domicilio de Referencia.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setListaDomicilio(null);
	    }
	    if(pUsuario.getPersona().getListaComunicacion() == null || pUsuario.getPersona().getListaComunicacion().size()==0){
	    	msgUsuario.setListaComunicacion("* Debe ingresar por lo menos un Tipo de Comunicación.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setListaComunicacion(null);
	    }
	    if(pUsuario.getListaEmpresaUsuario() == null || pUsuario.getListaEmpresaUsuario().size() == 0){
	    	msgUsuario.setListaEmpresaUsuario("* Debe agregar una Empresa al listado.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.setListaEmpresaUsuario(null);
	    	for(int i=0;i<pUsuario.getListaEmpresaUsuario().size();i++){
	    		lEmpresaUsuario = pUsuario.getListaEmpresaUsuario().get(i);
		    	
		    	if(lEmpresaUsuario.getListaUsuarioPerfil()==null || lEmpresaUsuario.getListaUsuarioPerfil().size()==0){
		    		msgUsuario.setListaUsuarioPerfil("* Debe agregar un Perfil al listado.");
			    	bValidar = false;
			    }else{
			    	for(int j=0;j<lEmpresaUsuario.getListaUsuarioPerfil().size();j++){
			    		lUsuarioPerfilLoop = lEmpresaUsuario.getListaUsuarioPerfil().get(j);
			    		intValue = lUsuarioPerfilLoop.getId().getIntIdPerfil();
			    		if(intValue.compareTo(new Integer(0))==0){
			    	    	msgUsuario.setIdPerfil("* Debe seleccionar un Perfil.");
			    	    	bValidar = false;
			    	    	break;
			    	    }else{
			    	    	msgUsuario.setIdPerfil(null);
			    	    }
			    		
			    		if(lUsuarioPerfilLoop.getId().getDtDesde() == null){
			    	    	msgUsuario.setDesdePerfil("* Debe completar la fecha de Inicio del Perfil.");
			    	    	bValidar = false;
			    	    	break;
			    	    }else{
			    	    	if(lUsuarioPerfilLoop.getBlnIndeterminado()==null || lUsuarioPerfilLoop.getBlnIndeterminado().compareTo(new Boolean(false))==0){
			    	    		if(lUsuarioPerfilLoop.getDtHasta() == null){
				    	    		msgUsuario.setHastaPerfil("* Debe completar la fecha de Fin del Perfil.");
					    	    	bValidar = false;
					    	    	break;
			    	    		}else if (lUsuarioPerfilLoop.getDtHasta() != null && lUsuarioPerfilLoop.getId().getDtDesde().after(lUsuarioPerfilLoop.getDtHasta())){
			    	    			msgUsuario.setHastaPerfil("* La fecha de Inicio debe ser menor a la fecha Fin del Perfil.");
					    	    	bValidar = false;
					    	    	break;
			    	    		}else{
			    	    			msgUsuario.setHastaPerfil(null);
			    	    		}
			    	    	}
			    	    	msgUsuario.setDesdePerfil(null);
			    	    }
			    	}
			    	msgUsuario.setListaUsuarioPerfil(null);
			    }
			    
			    if(lEmpresaUsuario.getListaUsuarioSucursal() == null || lEmpresaUsuario.getListaUsuarioSucursal().size() ==0){
			    	msgUsuario.setListaUsuarioSucursal("* Debe agregar una Sucursal al listado.");
			    	bValidar = false;
			    }else{
			    	for(int j=0;j<lEmpresaUsuario.getListaUsuarioSucursal().size();j++){
			    		lUsuarioSucursalLoop = lEmpresaUsuario.getListaUsuarioSucursal().get(j);
			    		intValue = lUsuarioSucursalLoop.getId().getIntIdSucursal();
			    		if(intValue.compareTo(new Integer(0))==0){
			    	    	msgUsuario.setIdSucursal("* Debe seleccionar una Sucursal.");
			    	    	bValidar = false;
			    	    	break;
			    	    }else{
			    	    	msgUsuario.setIdSucursal(null);
			    	    }
			    		
			    		if(lUsuarioSucursalLoop.getDtDesde() == null){
			    	    	msgUsuario.setDesdeSucursal("* Debe completar la fecha de Inicio de la Sucursal.");
			    	    	bValidar = false;
			    	    	break;
			    	    }else{
			    	    	if(lUsuarioSucursalLoop.getBlnIndeterminado()==null || lUsuarioSucursalLoop.getBlnIndeterminado().compareTo(new Boolean(false))==0){
			    	    		if(lUsuarioSucursalLoop.getDtHasta() == null){
				    	    		msgUsuario.setHastaSucursal("* Debe completar la fecha de Fin de la Sucursal.");
					    	    	bValidar = false;
					    	    	break;
			    	    		}else if (lUsuarioSucursalLoop.getDtHasta() != null && lUsuarioSucursalLoop.getDtDesde().after(lUsuarioSucursalLoop.getDtHasta())){
			    	    			msgUsuario.setHastaSucursal("* La fecha de Inicio debe ser menor a la fecha Fin de la Sucursal.");
					    	    	bValidar = false;
					    	    	break;
			    	    		}else{
			    	    			msgUsuario.setHastaSucursal(null);
			    	    		}
			    	    	}
			    	    	msgUsuario.setDesdeSucursal(null);
			    	    }
			    	}
			    	msgUsuario.setListaUsuarioSucursal(null);
			    }
			    
			    if(lEmpresaUsuario.getListaUsuarioSubSucursal()==null || lEmpresaUsuario.getListaUsuarioSubSucursal().size()==0){
			    	msgUsuario.setListaUsuarioSubSucursal("* Debe agregar una Sub-Sucursal al listado.");
			    	bValidar = false;
			    }else{
			    	for(int j=0;j<lEmpresaUsuario.getListaUsuarioSubSucursal().size();j++){
			    		lUsuarioSubSucursalLoop = lEmpresaUsuario.getListaUsuarioSubSucursal().get(j);
			    		intValue = lUsuarioSubSucursalLoop.getId().getIntIdSubSucursal();
			    		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			    	    	msgUsuario.setIdSubSucursal("* Debe seleccionar una Sub-Sucursal.");
			    	    	bValidar = false;
			    	    	break;
			    	    }else{
			    	    	msgUsuario.setIdSubSucursal(null);
			    	    }
			    		
			    		if(lUsuarioSubSucursalLoop.getDtDesde() == null){
			    	    	msgUsuario.setDesdeSubSucursal("* Debe completar la fecha de Inicio de la Sub-Sucursal.");
			    	    	bValidar = false;
			    	    	break;
			    	    }else{
			    	    	if(lUsuarioSubSucursalLoop.getBlnIndeterminado()==null || lUsuarioSubSucursalLoop.getBlnIndeterminado().compareTo(new Boolean(false))==0){
			    	    		if(lUsuarioSubSucursalLoop.getDtHasta() == null){
				    	    		msgUsuario.setHastaSubSucursal("* Debe completar la fecha de Fin de la Sucursal.");
					    	    	bValidar = false;
					    	    	break;
			    	    		}else if (lUsuarioSubSucursalLoop.getDtHasta() != null && lUsuarioSubSucursalLoop.getDtDesde().after(lUsuarioSubSucursalLoop.getDtHasta())){
			    	    			msgUsuario.setHastaSubSucursal("* La fecha de Inicio debe ser menor a la fecha Fin de la Sucursal.");
					    	    	bValidar = false;
					    	    	break;
			    	    		}else{
			    	    			msgUsuario.setHastaSubSucursal(null);
			    	    		}
			    	    	}
			    	    	msgUsuario.setDesdeSubSucursal(null);
			    	    }
			    	}
			    	msgUsuario.setListaUsuarioSubSucursal(null);
			    }
		    }
	    }
	    return bValidar;
	}
	
	public static Boolean validarDomicilio(UsuarioMsg msgUsuario,Domicilio domicilio){
		Boolean bValidar = true;
		Integer intValue = null;
		String strValue = null;
		intValue = domicilio.getIntTipoDomicilioCod(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setTipoDomicilio("* Debe seleccionar un Tipo de Domicilio.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setTipoDomicilio(null);
	    }
		intValue = domicilio.getIntTipoDireccionCod(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setTipoDireccion("* Debe seleccionar un Tipo de Direccion.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setTipoDireccion(null);
	    }
		intValue = domicilio.getIntTipoViviendaCod(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setTipoVivienda("* Debe seleccionar un Tipo de Vivienda.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setTipoVivienda(null);
	    }
		intValue = domicilio.getIntTipoViaCod(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setTipoVia("* Debe seleccionar un Tipo de Via.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setTipoVia(null);
	    }
		strValue = domicilio.getStrNombreVia(); 
		if(strValue == null || strValue.trim().equals("")){
			msgUsuario.getMsgDomicilio().setNombreVia("* Debe ingresar un Nombre de Via.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setNombreVia(null);
	    }
		intValue = domicilio.getIntNumeroVia(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setNumeroVia("* Debe Ingresar un Numero de Via.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setNumeroVia(null);
	    }
		intValue = domicilio.getIntTipoZonaCod(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setTipoZona("* Debe Seleccionar un Tipo de Zona.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setTipoZona(null);
	    }
		strValue = domicilio.getStrNombreZona(); 
		if(strValue == null || strValue.trim().equals("")){
			msgUsuario.getMsgDomicilio().setNombreZona("* Debe ingresar un Nombre de Zona.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setNombreZona(null);
	    }
		strValue = domicilio.getStrReferencia(); 
		if(strValue == null || strValue.trim().equals("")){
			msgUsuario.getMsgDomicilio().setReferencia("* Debe ingresar una Referencia.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setReferencia(null);
	    }
		
		intValue = domicilio.getIntParaUbigeoPkDpto(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setDepartamento("* Debe Seleccionar un Departamento.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setDepartamento(null);
	    }
		intValue = domicilio.getIntParaUbigeoPkProvincia(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setProvincia("* Debe Seleccionar una Provincia.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setProvincia(null);
	    }
		intValue = domicilio.getIntParaUbigeoPkDistrito(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgDomicilio().setDistrito("* Debe Seleccionar un Distrito.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgDomicilio().setDistrito(null);
	    }
		return bValidar;
	}
	
	public static Boolean validarComunicacion(UsuarioMsg msgUsuario,Comunicacion comunicacion){
		Boolean bValidar = true;
		Integer intValue = null;
		intValue = comunicacion.getIntTipoComunicacionCod(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgComunicacion().setTipoComunicacion("* Debe seleccionar un Tipo de Comunicacion.");
	    	bValidar = false;
	    }else{
	    	if(intValue.compareTo(Constante.PARAM_T_TIPOCOMUNICACION_TELEFONO)==0){
	    		intValue = comunicacion.getIntTipoLineaCod();
				if(intValue == null || intValue.compareTo(new Integer(0))==0){
					msgUsuario.getMsgComunicacion().setTipoLinea("* Debe seleccionar un Tipo de Linea.");
			    	bValidar = false;
			    }else{
			    	msgUsuario.getMsgComunicacion().setTipoLinea(null);
			    }
				intValue = comunicacion.getIntNumero(); 
				if(intValue == null || intValue.compareTo(new Integer(0))==0){
					msgUsuario.getMsgComunicacion().setNumero("* Debe ingresar un Numero Telefonico.");
			    	bValidar = false;
			    }else{
			    	msgUsuario.getMsgComunicacion().setNumero(null);
			    }
			}
	    	msgUsuario.getMsgComunicacion().setTipoComunicacion(null);
	    }
		intValue = comunicacion.getIntSubTipoComunicacionCod(); 
		if(intValue == null || intValue.compareTo(new Integer(0))==0){
			msgUsuario.getMsgComunicacion().setSubTipoComunicacion("* Debe seleccionar un Sub-Tipo de Comunicacion.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgComunicacion().setSubTipoComunicacion(null);
	    }
		if(comunicacion.getStrDescripcion()==null || comunicacion.getStrDescripcion().trim().equals("")){
			msgUsuario.getMsgComunicacion().setDescripcion("* Debe ingresar una descripcion.");
	    	bValidar = false;
	    }else{
	    	msgUsuario.getMsgComunicacion().setDescripcion(null);
	    }
		return bValidar;
	}
	
	public static void main(String args[]){
		String strCadena = null;
		strCadena = "A12P34";
		boolean bolValidar = Validacion.validarFormatoClave(strCadena);
		System.out.println(bolValidar);
	}
	
}
