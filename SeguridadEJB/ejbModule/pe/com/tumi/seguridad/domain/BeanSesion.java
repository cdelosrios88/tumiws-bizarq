package pe.com.tumi.seguridad.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import pe.com.tumi.common.domain.EntidadBase;

public class BeanSesion extends EntidadBase {
	
	private static 	 Integer 	intIdUsuario;
	private static 	 Integer 	intIdEmpresa;
	private static 	 Integer 	intIdPerfil;
	private static 	 String 	strPerfil;
	private static 	 Integer 	intIdSucursal;
	
	private static	 String 	strCodigoUsu;
	private static 	 String 	strContrasena;
	private static 	 String 	strNombreUsu;
	private static 	 String 	strApepaUsu;
	private static 	 String 	strApemaUsu;
	private static 	 String 	strCorreoElectronico;
	private static 	 Integer 	intEstado;
	private static 	 Date 		dtFechaCambioPass;
	private static 	 List 		listRoles = new ArrayList();
	private static 	 Integer 	intAreaGestora;
	
	public static Integer getIntIdUsuario() {
		return intIdUsuario;
	}
	public static void setIntIdUsuario(Integer intIdUsuario) {
		BeanSesion.intIdUsuario = intIdUsuario;
	}
	public static String getStrCodigoUsu() {
		return strCodigoUsu;
	}
	public static void setStrCodigoUsu(String strCodigoUsu) {
		BeanSesion.strCodigoUsu = strCodigoUsu;
	}
	public static String getStrContrasena() {
		return strContrasena;
	}
	public static void setStrContrasena(String strContrasena) {
		BeanSesion.strContrasena = strContrasena;
	}
	public static String getStrNombreUsu() {
		return strNombreUsu;
	}
	public static void setStrNombreUsu(String strNombreUsu) {
		BeanSesion.strNombreUsu = strNombreUsu;
	}
	public static String getStrApepaUsu() {
		return strApepaUsu;
	}
	public static void setStrApepaUsu(String strApepaUsu) {
		BeanSesion.strApepaUsu = strApepaUsu;
	}
	public static String getStrApemaUsu() {
		return strApemaUsu;
	}
	public static void setStrApemaUsu(String strApemaUsu) {
		BeanSesion.strApemaUsu = strApemaUsu;
	}
	public static String getStrCorreoElectronico() {
		return strCorreoElectronico;
	}
	public static void setStrCorreoElectronico(String strCorreoElectronico) {
		BeanSesion.strCorreoElectronico = strCorreoElectronico;
	}
	public static Integer getIntEstado() {
		return intEstado;
	}
	public static void setIntEstado(Integer intEstado) {
		BeanSesion.intEstado = intEstado;
	}
	public static Date getDtFechaCambioPass() {
		return dtFechaCambioPass;
	}
	public static void setDtFechaCambioPass(Date dtFechaCambioPass) {
		BeanSesion.dtFechaCambioPass = dtFechaCambioPass;
	}
	public static List getListRoles() {
		return listRoles;
	}
	public static void setListRoles(List listRoles) {
		BeanSesion.listRoles = listRoles;
	}
	public static Integer getIntAreaGestora() {
		return intAreaGestora;
	}
	public static void setIntAreaGestora(Integer intAreaGestora) {
		BeanSesion.intAreaGestora = intAreaGestora;
	}
	public static Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public static void setIntIdEmpresa(Integer intIdEmpresa) {
		BeanSesion.intIdEmpresa = intIdEmpresa;
	}
	public static Integer getIntIdPerfil() {
		return intIdPerfil;
	}
	public static void setIntIdPerfil(Integer intIdPerfil) {
		BeanSesion.intIdPerfil = intIdPerfil;
	}
	public static String getStrPerfil() {
		return strPerfil;
	}
	public static void setStrPerfil(String strPerfil) {
		BeanSesion.strPerfil = strPerfil;
	}
	public static Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public static void setIntIdSucursal(Integer intIdSucursal) {
		BeanSesion.intIdSucursal = intIdSucursal;
	}
	
}