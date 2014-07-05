package pe.com.tumi.adminCuenta.domain;

import java.util.Date;
import java.util.List;

import pe.com.tumi.empresa.domain.Persona;

public class PersonaJuridica extends Persona {
	//Tabla Per_M_Juridica
	private String 	strRazonSocial;
	private String 	strNombreComercial;
	private String  strSiglas;
	private Date 	dtFechaInscripcion;
	private String  strFechaInscripcion;
	private Date 	dtFechaInicioActi;
	private String  strFechaInicioActi;
	private Integer intNumTrabajadores;
	private Integer intTipoEmpresa;
	private Integer intTipoContribuyente;
	private Integer intCondContribuyente;
	private String  strCondContribuyente;
	private Integer intEstadoContribuyente;
	private String  strEstadoContribuyente;
	private Integer intEmisionComprobante;
	private String 	strEmisionComprobante;
	private Integer intSistemaContable;
	private String 	strSistemaContable;
	private Integer intComercioExterior;
	private String  strComercioExterior;
	private List 	listRepLegal;
	private List 	listCtaBancaria;
	
	//Variables de Salida
	private Integer intIdPersonaOut;
	private List	cursorLista;
	
	//Numero de Sucursales que tiene la empresa
	private Integer intNrosucursales;
	
	
	public String getStrRazonSocial() {
		return strRazonSocial;
	}
	public void setStrRazonSocial(String strRazonSocial) {
		this.strRazonSocial = strRazonSocial;
	}
	public String getStrNombreComercial() {
		return strNombreComercial;
	}
	public void setStrNombreComercial(String strNombreComercial) {
		this.strNombreComercial = strNombreComercial;
	}
	public String getStrSiglas() {
		return strSiglas;
	}
	public void setStrSiglas(String strSiglas) {
		this.strSiglas = strSiglas;
	}
	public Date getDtFechaInscripcion() {
		return dtFechaInscripcion;
	}
	public void setDtFechaInscripcion(Date dtFechaInscripcion) {
		this.dtFechaInscripcion = dtFechaInscripcion;
	}
	public String getStrFechaInscripcion() {
		return strFechaInscripcion;
	}
	public void setStrFechaInscripcion(String strFechaInscripcion) {
		this.strFechaInscripcion = strFechaInscripcion;
	}
	public Integer getIntNumTrabajadores() {
		return intNumTrabajadores;
	}
	public void setIntNumTrabajadores(Integer intNumTrabajadores) {
		this.intNumTrabajadores = intNumTrabajadores;
	}
	public Integer getIntTipoEmpresa() {
		return intTipoEmpresa;
	}
	public void setIntTipoEmpresa(Integer intTipoEmpresa) {
		this.intTipoEmpresa = intTipoEmpresa;
	}
	public Integer getIntTipoContribuyente() {
		return intTipoContribuyente;
	}
	public void setIntTipoContribuyente(Integer intTipoContribuyente) {
		this.intTipoContribuyente = intTipoContribuyente;
	}
	public Integer getIntCondContribuyente() {
		return intCondContribuyente;
	}
	public void setIntCondContribuyente(Integer intCondContribuyente) {
		this.intCondContribuyente = intCondContribuyente;
	}
	public Integer getIntEstadoContribuyente() {
		return intEstadoContribuyente;
	}
	public void setIntEstadoContribuyente(Integer intEstadoContribuyente) {
		this.intEstadoContribuyente = intEstadoContribuyente;
	}
	public Integer getIntEmisionComprobante() {
		return intEmisionComprobante;
	}
	public void setIntEmisionComprobante(Integer intEmisionComprobante) {
		this.intEmisionComprobante = intEmisionComprobante;
	}
	public Integer getIntSistemaContable() {
		return intSistemaContable;
	}
	public void setIntSistemaContable(Integer intSistemaContable) {
		this.intSistemaContable = intSistemaContable;
	}
	public Integer getIntComercioExterior() {
		return intComercioExterior;
	}
	public void setIntComercioExterior(Integer intComercioExterior) {
		this.intComercioExterior = intComercioExterior;
	}
	public Date getDtFechaInicioActi() {
		return dtFechaInicioActi;
	}
	public void setDtFechaInicioActi(Date dtFechaInicioActi) {
		this.dtFechaInicioActi = dtFechaInicioActi;
	}
	public String getStrFechaInicioActi() {
		return strFechaInicioActi;
	}
	public void setStrFechaInicioActi(String strFechaInicioActi) {
		this.strFechaInicioActi = strFechaInicioActi;
	}
	public Integer getIntIdPersonaOut() {
		return intIdPersonaOut;
	}
	public void setIntIdPersonaOut(Integer intIdPersonaOut) {
		this.intIdPersonaOut = intIdPersonaOut;
	}
	public List getCursorLista() {
		return cursorLista;
	}
	public void setCursorLista(List cursorLista) {
		this.cursorLista = cursorLista;
	}
	public String getStrCondContribuyente() {
		return strCondContribuyente;
	}
	public void setStrCondContribuyente(String strCondContribuyente) {
		this.strCondContribuyente = strCondContribuyente;
	}
	public String getStrEstadoContribuyente() {
		return strEstadoContribuyente;
	}
	public void setStrEstadoContribuyente(String strEstadoContribuyente) {
		this.strEstadoContribuyente = strEstadoContribuyente;
	}
	public String getStrEmisionComprobante() {
		return strEmisionComprobante;
	}
	public void setStrEmisionComprobante(String strEmisionComprobante) {
		this.strEmisionComprobante = strEmisionComprobante;
	}
	public String getStrSistemaContable() {
		return strSistemaContable;
	}
	public void setStrSistemaContable(String strSistemaContable) {
		this.strSistemaContable = strSistemaContable;
	}
	public String getStrComercioExterior() {
		return strComercioExterior;
	}
	public void setStrComercioExterior(String strComercioExterior) {
		this.strComercioExterior = strComercioExterior;
	}
	public List getListRepLegal() {
		return listRepLegal;
	}
	public void setListRepLegal(List listRepLegal) {
		this.listRepLegal = listRepLegal;
	}
	public List getListCtaBancaria() {
		return listCtaBancaria;
	}
	public void setListCtaBancaria(List listCtaBancaria) {
		this.listCtaBancaria = listCtaBancaria;
	}
	public Integer getIntNrosucursales() {
		return intNrosucursales;
	}
	public void setIntNrosucursales(Integer intNrosucursales) {
		this.intNrosucursales = intNrosucursales;
	}
}
