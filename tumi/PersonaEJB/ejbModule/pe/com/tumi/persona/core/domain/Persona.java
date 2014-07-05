package pe.com.tumi.persona.core.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.contacto.domain.Comunicacion;
import pe.com.tumi.persona.contacto.domain.Documento;
import pe.com.tumi.persona.contacto.domain.Domicilio;
import pe.com.tumi.persona.empresa.domain.Empresa;
import pe.com.tumi.persona.empresa.domain.Juridica;

import java.util.Date;
import java.util.List;

public class Persona extends TumiDomain{

	private Integer intIdPersona;
	private Integer intEstadoCod;
	private Date dtFechaBajaRuc;
	private String strFechaBajaRuc;
	private String strRuc;
	private Integer intTipoPersonaCod;

	private List<Comunicacion> listaComunicacion;
	private List<CuentaBancaria> listaCuentaBancaria;
	private List<Domicilio> listaDomicilio;
	private List<Documento> listaDocumento;
	private List<Persona> listaPersona;
	private Juridica juridica;
	private Natural natural;
	private List<Empresa> listaEmpresa;
	private List<PersonaEmpresa> listaPersonaEmpresa;
	private PersonaEmpresa personaEmpresa;
	private Documento documento;
	private String strEtiqueta;
	private String strRoles;
	private Documento ruc;
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntEstadoCod() {
		return intEstadoCod;
	}
	public void setIntEstadoCod(Integer intEstadoCod) {
		this.intEstadoCod = intEstadoCod;
	}
	public Date getDtFechaBajaRuc() {
		return dtFechaBajaRuc;
	}
	public void setDtFechaBajaRuc(Date dtFechaBajaRuc) {
		this.dtFechaBajaRuc = dtFechaBajaRuc;
	}
	public String getStrRuc() {
		return strRuc;
	}
	public void setStrRuc(String strRuc) {
		this.strRuc = strRuc;
	}
	public Integer getIntTipoPersonaCod() {
		return intTipoPersonaCod;
	}
	public void setIntTipoPersonaCod(Integer intTipoPersonaCod) {
		this.intTipoPersonaCod = intTipoPersonaCod;
	}
	public List<Comunicacion> getListaComunicacion() {
		return listaComunicacion;
	}
	public void setListaComunicacion(List<Comunicacion> listaComunicacion) {
		this.listaComunicacion = listaComunicacion;
	}
	public List<CuentaBancaria> getListaCuentaBancaria() {
		return listaCuentaBancaria;
	}
	public void setListaCuentaBancaria(List<CuentaBancaria> listaCuentaBancaria) {
		this.listaCuentaBancaria = listaCuentaBancaria;
	}
	public List<Domicilio> getListaDomicilio() {
		return listaDomicilio;
	}
	public void setListaDomicilio(List<Domicilio> listaDomicilio) {
		this.listaDomicilio = listaDomicilio;
	}
	public Juridica getJuridica() {
		return juridica;
	}
	public void setJuridica(Juridica juridica) {
		this.juridica = juridica;
	}
	public Natural getNatural() {
		return natural;
	}
	public void setNatural(Natural natural) {
		this.natural = natural;
	}
	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
	}
	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
	}
	public String getStrFechaBajaRuc() {
		return strFechaBajaRuc;
	}
	public void setStrFechaBajaRuc(String strFechaBajaRuc) {
		this.strFechaBajaRuc = strFechaBajaRuc;
	}
	public PersonaEmpresa getPersonaEmpresa() {
		return personaEmpresa;
	}
	public void setPersonaEmpresa(PersonaEmpresa personaEmpresa) {
		this.personaEmpresa = personaEmpresa;
	}
	public List<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(List<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
	public List<Documento> getListaDocumento() {
		return listaDocumento;
	}
	public void setListaDocumento(List<Documento> listaDocumento) {
		this.listaDocumento = listaDocumento;
	}
	public List<PersonaEmpresa> getListaPersonaEmpresa() {
		return listaPersonaEmpresa;
	}
	public void setListaPersonaEmpresa(List<PersonaEmpresa> listaPersonaEmpresa) {
		this.listaPersonaEmpresa = listaPersonaEmpresa;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public String getStrEtiqueta() {
		return strEtiqueta;
	}
	public void setStrEtiqueta(String strEtiqueta) {
		this.strEtiqueta = strEtiqueta;
	}
	public String getStrRoles() {
		return strRoles;
	}
	public void setStrRoles(String strRoles) {
		this.strRoles = strRoles;
	}
	
	public Documento getRuc() {
		return ruc;
	}
	public void setRuc(Documento ruc) {
		this.ruc = ruc;
	}
	@Override
	public String toString() {
		return "Persona [intIdPersona=" + intIdPersona + ", intEstadoCod="
				+ intEstadoCod + ", dtFechaBajaRuc=" + dtFechaBajaRuc
				+ ", strFechaBajaRuc=" + strFechaBajaRuc + ", strRuc=" + strRuc
				+ ", intTipoPersonaCod=" + intTipoPersonaCod + "]";
	}
	
}