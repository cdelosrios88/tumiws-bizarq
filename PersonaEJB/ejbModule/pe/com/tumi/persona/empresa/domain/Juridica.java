package pe.com.tumi.persona.empresa.domain;

import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.core.domain.Natural;
import pe.com.tumi.persona.core.domain.Persona;

import java.util.Date;
import java.util.List;

public class Juridica extends TumiDomain{

	private Integer intIdPersona;
	private Integer intComercioExterior;
	private Integer intCondContribuyente;
	private Integer intEmisionComprobante;
	private Integer intEstadoContribuyenteCod;
	private Date dtFechaInscripcion;
	private String strFechaInscripcion;
	private String strNombreComercial;
	private Integer intNroTrabajadores;
	private String strRazonSocial;
	private String strSiglas;
	private Integer intSistemaContable;
	private Integer intTipoContribuyenteCod;
	private Integer intTipoEmpresaCod;
	private Date dtFechaInicioAct;
	private String strFichaRegPublico;

	private List<ActividadEconomica> listaActividadEconomica;
	private List<TipoComprobante> listaTipoComprobante;
	private Persona persona;
	
	
	public Integer getIntIdPersona() {
		return intIdPersona;
	}
	public void setIntIdPersona(Integer intIdPersona) {
		this.intIdPersona = intIdPersona;
	}
	public Integer getIntComercioExterior() {
		return intComercioExterior;
	}
	public void setIntComercioExterior(Integer intComercioExterior) {
		this.intComercioExterior = intComercioExterior;
	}
	public Integer getIntCondContribuyente() {
		return intCondContribuyente;
	}
	public void setIntCondContribuyente(Integer intCondContribuyente) {
		this.intCondContribuyente = intCondContribuyente;
	}
	public Integer getIntEmisionComprobante() {
		return intEmisionComprobante;
	}
	public void setIntEmisionComprobante(Integer intEmisionComprobante) {
		this.intEmisionComprobante = intEmisionComprobante;
	}
	public Integer getIntEstadoContribuyenteCod() {
		return intEstadoContribuyenteCod;
	}
	public void setIntEstadoContribuyenteCod(Integer intEstadoContribuyenteCod) {
		this.intEstadoContribuyenteCod = intEstadoContribuyenteCod;
	}
	public Date getDtFechaInscripcion() {
		return dtFechaInscripcion;
	}
	public void setDtFechaInscripcion(Date dtFechaInscripcion) {
		this.dtFechaInscripcion = dtFechaInscripcion;
	}
	public String getStrNombreComercial() {
		return strNombreComercial;
	}
	public void setStrNombreComercial(String strNombreComercial) {
		this.strNombreComercial = strNombreComercial;
	}
	public Integer getIntNroTrabajadores() {
		return intNroTrabajadores;
	}
	public void setIntNroTrabajadores(Integer intNroTrabajadores) {
		this.intNroTrabajadores = intNroTrabajadores;
	}
	public String getStrRazonSocial() {
		return strRazonSocial;
	}
	public void setStrRazonSocial(String strRazonSocial) {
		this.strRazonSocial = strRazonSocial;
	}
	public String getStrSiglas() {
		return strSiglas;
	}
	public void setStrSiglas(String strSiglas) {
		this.strSiglas = strSiglas;
	}
	public Integer getIntSistemaContable() {
		return intSistemaContable;
	}
	public void setIntSistemaContable(Integer intSistemaContable) {
		this.intSistemaContable = intSistemaContable;
	}
	public Integer getIntTipoContribuyenteCod() {
		return intTipoContribuyenteCod;
	}
	public void setIntTipoContribuyenteCod(Integer intTipoContribuyenteCod) {
		this.intTipoContribuyenteCod = intTipoContribuyenteCod;
	}
	public Integer getIntTipoEmpresaCod() {
		return intTipoEmpresaCod;
	}
	public void setIntTipoEmpresaCod(Integer intTipoEmpresaCod) {
		this.intTipoEmpresaCod = intTipoEmpresaCod;
	}
	public List<ActividadEconomica> getListaActividadEconomica() {
		return listaActividadEconomica;
	}
	public void setListaActividadEconomica(
			List<ActividadEconomica> listaActividadEconomica) {
		this.listaActividadEconomica = listaActividadEconomica;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getStrFechaInscripcion() {
		return strFechaInscripcion;
	}
	public void setStrFechaInscripcion(String strFechaInscripcion) {
		this.strFechaInscripcion = strFechaInscripcion;
	}
	public Date getDtFechaInicioAct() {
		return dtFechaInicioAct;
	}
	public void setDtFechaInicioAct(Date dtFechaInicioAct) {
		this.dtFechaInicioAct = dtFechaInicioAct;
	}
	public List<TipoComprobante> getListaTipoComprobante() {
		return listaTipoComprobante;
	}
	public void setListaTipoComprobante(List<TipoComprobante> listaTipoComprobante) {
		this.listaTipoComprobante = listaTipoComprobante;
	}
	public String getStrFichaRegPublico() {
		return strFichaRegPublico;
	}
	public void setStrFichaRegPublico(String strFichaRegPublico) {
		this.strFichaRegPublico = strFichaRegPublico;
	}
	@Override
	public String toString() {
		return "Juridica [intIdPersona=" + intIdPersona 
//				+ ", intComercioExterior=" + intComercioExterior
//				+ ", intCondContribuyente=" + intCondContribuyente
//				+ ", intEmisionComprobante=" + intEmisionComprobante
//				+ ", intEstadoContribuyenteCod=" + intEstadoContribuyenteCod
//				+ ", dtFechaInscripcion=" + dtFechaInscripcion
//				+ ", strFechaInscripcion=" + strFechaInscripcion
//				+ ", strNombreComercial=" + strNombreComercial
//				+ ", intNroTrabajadores=" + intNroTrabajadores
				+ ", strRazonSocial=" + strRazonSocial +
//				+ ", strSiglas="+ strSiglas +
//				 ", intSistemaContable=" + intSistemaContable
//				+ ", intTipoContribuyenteCod=" + intTipoContribuyenteCod
//				+ ", intTipoEmpresaCod=" + intTipoEmpresaCod
//				+ ", dtFechaInicioAct=" + dtFechaInicioAct
//				+ ", strFichaRegPublico=" + strFichaRegPublico + 
				"]";
	}
	
	

}