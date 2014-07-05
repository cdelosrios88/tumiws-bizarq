package pe.com.tumi.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;

public class Empresa  extends TumiDomain{    

	// Tabla PER_PERSONA
	private  	Integer 	intIdPersona;
    private  	Integer 	intIdtipopersona;
    private  	String	 	strRuc;
    private  	Long	 	intRuc;
    private  	Integer 	intIdestado_persona;
    // Tabla PER_JURIDICA
    private  	String  	strRazonsocial; 
    private  	String  	strSiglas;
    private  	Integer 	intIdtipoempresa;
    // Tabla SEG_EMPRESA
    private		Integer		intIdEmpresa;
    private  	String  	strTiemposesion; 
    private  	String  	strAlertasesion; 
    private  	Integer 	intIdestado_empresa;
    private  	String  	strVigenciaclaves; 
    private  	String  	strAlertacaducidad; 
    private  	String  	strIntentosingreso; 
    private  	Boolean  	blnControlhoraingreso; 
    private  	Boolean  	blnControlregistro; 
    private  	Boolean  	blnControlcambioclave;
    // NRO de sucursales
    private  int 			intNrosucursales;
    private  	String	 	strEstado;    
	private  	String	 	strTipoempresa;
	private		String		strEmpresa;
	private 	Integer		intCantidadSucursal;
    
	
	public String getStrEstado() {
		return strEstado;
	}
	public void setStrEstado(String strEstado) {
		this.strEstado = strEstado;
	}
	public String getStrTipoempresa() {
		return strTipoempresa;
	}
	public void setStrTipoempresa(String strTipoempresa) {
		this.strTipoempresa = strTipoempresa;
	}
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public String getStrRuc() {
		return strRuc;
	}
	public void setStrRuc(String strRuc) {
		this.strRuc = strRuc;
	}
	public String getStrRazonsocial() {
		return strRazonsocial;
	}
	public void setStrRazonsocial(String strRazonsocial) {
		this.strRazonsocial = strRazonsocial;
	}
	public String getStrSiglas() {
		return strSiglas;
	}
	public void setStrSiglas(String strSiglas) {
		this.strSiglas = strSiglas;
	}
	public String getStrTiemposesion() {
		return strTiemposesion;
	}
	public void setStrTiemposesion(String strTiemposesion) {
		this.strTiemposesion = strTiemposesion;
	}
	public String getStrAlertasesion() {
		return strAlertasesion;
	}
	public void setStrAlertasesion(String strAlertasesion) {
		this.strAlertasesion = strAlertasesion;
	}
	public String getStrVigenciaclaves() {
		return strVigenciaclaves;
	}
	public void setStrVigenciaclaves(String strVigenciaclaves) {
		this.strVigenciaclaves = strVigenciaclaves;
	}
	public String getStrAlertacaducidad() {
		return strAlertacaducidad;
	}
	public void setStrAlertacaducidad(String strAlertacaducidad) {
		this.strAlertacaducidad = strAlertacaducidad;
	}
	public String getStrIntentosingreso() {
		return strIntentosingreso;
	}
	public void setStrIntentosingreso(String strIntentosingreso) {
		this.strIntentosingreso = strIntentosingreso;
	}
	public Boolean getBlnControlhoraingreso() {
		return blnControlhoraingreso;
	}
	public void setBlnControlhoraingreso(Boolean blnControlhoraingreso) {
		this.blnControlhoraingreso = blnControlhoraingreso;
	}
	public Boolean getBlnControlregistro() {
		return blnControlregistro;
	}
	public void setBlnControlregistro(Boolean blnControlregistro) {
		this.blnControlregistro = blnControlregistro;
	}
	public Boolean getBlnControlcambioclave() {
		return blnControlcambioclave;
	}
	public void setBlnControlcambioclave(Boolean blnControlcambioclave) {
		this.blnControlcambioclave = blnControlcambioclave;
	}
	public Long getIntRuc() {
		return intRuc;
	}
	public void setIntRuc(Long intRuc) {
		this.intRuc = intRuc;
	}
	public int getIntNrosucursales() {
		return intNrosucursales;
	}
	public void setIntNrosucursales(int intNrosucursales) {
		this.intNrosucursales = intNrosucursales;
	}
	public Integer getIntIdtipopersona() {
		return intIdtipopersona;
	}
	public void setIntIdtipopersona(Integer intIdtipopersona) {
		this.intIdtipopersona = intIdtipopersona;
	}
	public Integer getIntIdtipoempresa() {
		return intIdtipoempresa;
	}
	public void setIntIdtipoempresa(Integer intIdtipoempresa) {
		this.intIdtipoempresa = intIdtipoempresa;
	}
	public Integer getIntIdEmpresa() {
		return intIdEmpresa;
	}
	public void setIntIdEmpresa(Integer intIdEmpresa) {
		this.intIdEmpresa = intIdEmpresa;
	}
	public Integer getIntIdestado_persona() {
		return intIdestado_persona;
	}
	public void setIntIdestado_persona(Integer intIdestadoPersona) {
		intIdestado_persona = intIdestadoPersona;
	}
	public Integer getIntIdestado_empresa() {
		return intIdestado_empresa;
	}
	public void setIntIdestado_empresa(Integer intIdestadoEmpresa) {
		intIdestado_empresa = intIdestadoEmpresa;
	}
	public String getStrEmpresa() {
		return strEmpresa;
	}
	public void setStrEmpresa(String strEmpresa) {
		this.strEmpresa = strEmpresa;
	}
	public Integer getIntCantidadSucursal() {
		return intCantidadSucursal;
	}
	public void setIntCantidadSucursal(Integer intCantidadSucursal) {
		this.intCantidadSucursal = intCantidadSucursal;
	}
}
