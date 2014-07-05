package pe.com.tumi.seguridad.usuario.domain;

import java.util.List;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.core.domain.CuentaBancaria;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class Usuario extends TumiDomain{
	// Tabla SEG_M_USUARIO
	private Integer	intIdPersona;
	private Integer	intIdTipoUsuario;
	private String	strTipoUsuario;
	private String  strPersona;
	private Integer intIdTipoPersona;
	private String	strRazonSocial;
	private Integer	intIdEstado;
	private Integer	intIdEmpresa;
	private String	strEmpresaUsuario;
	private Integer	intIdPerfil;
	private String	strPerfil;
	private Integer	intIdSucursal;
	private Integer	intIdSubSucursal;
	private String	daFecRegistro;
	private Integer	intIdTipoDoc;
	private String  strNroDoc;
	private String	strApepat;
	private String	strApemat;
	private String	strNombres;
	private Boolean chkDireccion;
	private Boolean chkComunicacion;
	private Boolean chkPerfilUsu;
	private String	strNombreUsuario;
	private String	strClaveUsuario;
	private Boolean chkValidEsp;
	private Integer	intValidEsp;
	private Boolean chkValidHoraIng;
	private Integer	intHoraIng;
	private Boolean chkValidCambiarClave;
	private Integer	intValidCambClave;
	private Boolean chkVigenciaClave;
	private Integer	intVigenciaCambClave;
	private Integer	intNroDias;
	private String  strSucsubcursal;
	private String	rbVigClave;
	private String	strImagen;
	private Integer	intErrIntentos;
	
	private Persona persona;
	private Comunicacion comunicacion;
	private Domicilio domicilio;

	//Getters y Setters
	private String	strEstado;
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntIdTipoUsuario() {
		return intIdTipoUsuario;
	}
	public void setIntIdTipoUsuario(Integer intIdTipoUsuario) {
		this.intIdTipoUsuario = intIdTipoUsuario;
	}
	public String getStrTipoUsuario() {
		return strTipoUsuario;
	}
	public void setStrTipoUsuario(String strTipoUsuario) {
		this.strTipoUsuario = strTipoUsuario;
	}
	public String getStrPersona() {
		return strPersona;
	}
	public void setStrPersona(String strPersona) {
		this.strPersona = strPersona;
	}
	public Integer getIntIdTipoPersona() {
		return intIdTipoPersona;
	}
	public void setIntIdTipoPersona(Integer intIdTipoPersona) {
		this.intIdTipoPersona = intIdTipoPersona;
	}
	public String getStrRazonSocial() {
		return strRazonSocial;
	}
	public void setStrRazonSocial(String strRazonSocial) {
		this.strRazonSocial = strRazonSocial;
	}
	public Integer getIntHoraIng() {
		return intHoraIng;
	}
	public void setIntHoraIng(Integer intHoraIng) {
		this.intHoraIng = intHoraIng;
	}
	public Integer getIntIdEstado() {
		return intIdEstado;
	}
	public void setIntIdEstado(Integer intIdEstado) {
		this.intIdEstado = intIdEstado;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public String getStrEmpresaUsuario() {
		return strEmpresaUsuario;
	}
	public void setStrEmpresaUsuario(String strEmpresaUsuario) {
		this.strEmpresaUsuario = strEmpresaUsuario;
	}
	public Integer getIntIdPerfil() {
		return intIdPerfil;
	}
	public void setIntIdPerfil(Integer intIdPerfil) {
		this.intIdPerfil = intIdPerfil;
	}
	public String getStrPerfil() {
		return strPerfil;
	}
	public void setStrPerfil(String strPerfil) {
		this.strPerfil = strPerfil;
	}
	public Integer getIntIdSucursal() {
		return intIdSucursal;
	}
	public void setIntIdSucursal(Integer intIdSucursal) {
		this.intIdSucursal = intIdSucursal;
	}
	public Integer getIntIdSubSucursal() {
		return intIdSubSucursal;
	}
	public void setIntIdSubSucursal(Integer intIdSubSucursal) {
		this.intIdSubSucursal = intIdSubSucursal;
	}
	public String getDaFecRegistro() {
		return daFecRegistro;
	}
	public void setDaFecRegistro(String daFecRegistro) {
		this.daFecRegistro = daFecRegistro;
	}
	public Integer getIntIdTipoDoc() {
		return intIdTipoDoc;
	}
	public void setIntIdTipoDoc(Integer intIdTipoDoc) {
		this.intIdTipoDoc = intIdTipoDoc;
	}
	public String getStrNroDoc() {
		return strNroDoc;
	}
	public void setStrNroDoc(String strNroDoc) {
		this.strNroDoc = strNroDoc;
	}
	public String getStrApepat() {
		return strApepat;
	}
	public void setStrApepat(String strApepat) {
		this.strApepat = strApepat;
	}
	public String getStrApemat() {
		return strApemat;
	}
	public void setStrApemat(String strApemat) {
		this.strApemat = strApemat;
	}
	public String getStrNombres() {
		return strNombres;
	}
	public void setStrNombres(String strNombres) {
		this.strNombres = strNombres;
	}
	public Boolean getChkDireccion() {
		return chkDireccion;
	}
	public void setChkDireccion(Boolean chkDireccion) {
		this.chkDireccion = chkDireccion;
	}
	public Boolean getChkComunicacion() {
		return chkComunicacion;
	}
	public void setChkComunicacion(Boolean chkComunicacion) {
		this.chkComunicacion = chkComunicacion;
	}
	public Boolean getChkPerfilUsu() {
		return chkPerfilUsu;
	}
	public void setChkPerfilUsu(Boolean chkPerfilUsu) {
		this.chkPerfilUsu = chkPerfilUsu;
	}
	public String getStrNombreUsuario() {
		return strNombreUsuario;
	}
	public void setStrNombreUsuario(String strNombreUsuario) {
		this.strNombreUsuario = strNombreUsuario;
	}
	public String getStrClaveUsuario() {
		return strClaveUsuario;
	}
	public void setStrClaveUsuario(String strClaveUsuario) {
		this.strClaveUsuario = strClaveUsuario;
	}
	public Boolean getChkValidEsp() {
		return chkValidEsp;
	}
	public void setChkValidEsp(Boolean chkValidEsp) {
		this.chkValidEsp = chkValidEsp;
	}
	public Integer getIntValidEsp() {
		return intValidEsp;
	}
	public void setIntValidEsp(Integer intValidEsp) {
		this.intValidEsp = intValidEsp;
	}
	public Boolean getChkValidHoraIng() {
		return chkValidHoraIng;
	}
	public void setChkValidHoraIng(Boolean chkValidHoraIng) {
		this.chkValidHoraIng = chkValidHoraIng;
	}
	public Boolean getChkValidCambiarClave() {
		return chkValidCambiarClave;
	}
	public void setChkValidCambiarClave(Boolean chkValidCambiarClave) {
		this.chkValidCambiarClave = chkValidCambiarClave;
	}
	public Integer getIntValidCambClave() {
		return intValidCambClave;
	}
	public void setIntValidCambClave(Integer intValidCambClave) {
		this.intValidCambClave = intValidCambClave;
	}
	public Boolean getChkVigenciaClave() {
		return chkVigenciaClave;
	}
	public void setChkVigenciaClave(Boolean chkVigenciaClave) {
		this.chkVigenciaClave = chkVigenciaClave;
	}
	public Integer getIntVigenciaCambClave() {
		return intVigenciaCambClave;
	}
	public void setIntVigenciaCambClave(Integer intVigenciaCambClave) {
		this.intVigenciaCambClave = intVigenciaCambClave;
	}
	public Integer getIntNroDias() {
		return intNroDias;
	}
	public void setIntNroDias(Integer intNroDias) {
		this.intNroDias = intNroDias;
	}
	public String getStrSucsubcursal() {
		return strSucsubcursal;
	}
	public void setStrSucsubcursal(String strSucsubcursal) {
		this.strSucsubcursal = strSucsubcursal;
	}
	public String getRbVigClave() {
		return rbVigClave;
	}
	public void setRbVigClave(String rbVigClave) {
		this.rbVigClave = rbVigClave;
	}
	public String getStrImagen() {
		return strImagen;
	}
	public void setStrImagen(String strImagen) {
		this.strImagen = strImagen;
	}
	public Integer getIntErrIntentos() {
		return intErrIntentos;
	}
	public void setIntErrIntentos(Integer intErrIntentos) {
		this.intErrIntentos = intErrIntentos;
	}
	public String getStrEstado() {
		return strEstado;
	}
	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Comunicacion getComunicacion() {
		return comunicacion;
	}
	public void setComunicacion(Comunicacion comunicacion) {
		this.comunicacion = comunicacion;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
}
