package pe.com.tumi.cobranza.planilla.domain.composite;

import pe.com.tumi.cobranza.planilla.domain.Envioconcepto;
import pe.com.tumi.cobranza.planilla.domain.Enviomonto;

import pe.com.tumi.credito.socio.estructura.domain.Estructura;
import pe.com.tumi.credito.socio.estructura.domain.EstructuraDetalle;
import pe.com.tumi.framework.negocio.domain.TumiDomain;
import pe.com.tumi.persona.empresa.domain.Juridica;

public class EnvioConceptoComp extends TumiDomain{
	
	private Envioconcepto envioConcepto;
	private Enviomonto envioMonto;
	private EstructuraDetalle estructuraDetalle;
	private Estructura estructura;
	private Integer intPeriodoMes;
	private Integer intPeriodoAnio;
	private Boolean blnActivo;
	private Boolean blnHaberes;
	private Boolean blnIncentivo;
	private Boolean blnCas;
	private Integer intParaTipoSocio;
	private Integer intParaModalidadPlanilla;
	private Juridica juridicaUnidadEjecutora;
	private Juridica juridicaSucursal;
	private Integer intPeriodo;
	
	private String strTipoSocio;
	private String strModalidad;
	private Boolean blnCAS;
	private String strModalidad2;
	
	public Envioconcepto getEnvioConcepto() {
		return envioConcepto;
	}
	public void setEnvioConcepto(Envioconcepto envioConcepto) {
		this.envioConcepto = envioConcepto;
	}
	
	public Enviomonto getEnvioMonto() {
		return envioMonto;
	}
	public void setEnvioMonto(Enviomonto envioMonto) {
		this.envioMonto = envioMonto;
	}
	
	public EstructuraDetalle getEstructuraDetalle() {
		return estructuraDetalle;
	}
	public void setEstructuraDetalle(EstructuraDetalle estructuraDetalle) {
		this.estructuraDetalle = estructuraDetalle;
	}
	public Estructura getEstructura() {
		return estructura;
	}
	public void setEstructura(Estructura estructura) {
		this.estructura = estructura;
	}
	public Juridica getJuridicaSucursal() {
		return juridicaSucursal;
	}
	public void setJuridicaSucursal(Juridica juridicaSucursal) {
		this.juridicaSucursal = juridicaSucursal;
	}

	public Juridica getJuridicaUnidadEjecutora() {
		return juridicaUnidadEjecutora;
	}
	public void setJuridicaUnidadEjecutora(Juridica juridicaUnidadEjecutora) {
		this.juridicaUnidadEjecutora = juridicaUnidadEjecutora;
	}
	
	public Integer getIntPeriodoMes() {
		return intPeriodoMes;
	}
	public void setIntPeriodoMes(Integer intPeriodoMes) {
		this.intPeriodoMes = intPeriodoMes;
	}
	
	public Integer getIntPeriodoAnio() {
		return intPeriodoAnio;
	}
	public void setIntPeriodoAnio(Integer intPeriodoAnio) {
		this.intPeriodoAnio = intPeriodoAnio;
	}
	
	public Boolean getBlnActivo() {
		return blnActivo;
	}
	public void setBlnActivo(Boolean blnActivo) {
		this.blnActivo = blnActivo;
	}
	
	public Boolean getBlnHaberes() {
		return blnHaberes;
	}
	public void setBlnHaberes(Boolean blnHaberes) {
		this.blnHaberes = blnHaberes;
	}
	
	public Boolean getBlnIncentivo() {
		return blnIncentivo;
	}
	public void setBlnIncentivo(Boolean blnIncentivo) {
		this.blnIncentivo = blnIncentivo;
	}
	
	public Boolean getBlnCas() {
		return blnCas;
	}
	public void setBlnCas(Boolean blnCas) {
		this.blnCas = blnCas;
	}
	
	public Integer getIntParaTipoSocio() {
		return intParaTipoSocio;
	}
	public void setIntParaTipoSocio(Integer intParaTipoSocio) {
		this.intParaTipoSocio = intParaTipoSocio;
	}
	
	public Integer getIntParaModalidadPlanilla() {
		return intParaModalidadPlanilla;
	}
	public void setIntParaModalidadPlanilla(Integer intParaModalidadPlanilla) {
		this.intParaModalidadPlanilla = intParaModalidadPlanilla;
	}
	public Integer getIntPeriodo() {
		return intPeriodo;
	}
	public void setIntPeriodo(Integer intPeriodo) {
		this.intPeriodo = intPeriodo;
	}
	
	
	
	public String getStrTipoSocio() {
		return strTipoSocio;
	}
	public void setStrTipoSocio(String strTipoSocio) {
		this.strTipoSocio = strTipoSocio;
	}
	public String getStrModalidad() {
		return strModalidad;
	}
	public void setStrModalidad(String strModalidad) {
		this.strModalidad = strModalidad;
	}
	public Boolean getBlnCAS() {
		return blnCAS;
	}
	
	public void setBlnCAS(Boolean blnCAS) {
		this.blnCAS = blnCAS;
	}
	
	
	public String getStrModalidad2() {
		return strModalidad2;
	}
	public void setStrModalidad2(String strModalidad2) {
		this.strModalidad2 = strModalidad2;
	}
	@Override
	public String toString() {
		return "EnvioConceptoComp [estructuraDetalle=" + estructuraDetalle
				+ ", juridicaUnidadEjecutoraRazonSocial=" + juridicaUnidadEjecutora 
				+ ", intParaModalidadPlanilla=" + intParaModalidadPlanilla
				+ ", intPeriodoMes=" + intPeriodoMes
				+ ", intPeriodoAnio=" + intPeriodoAnio
				+ ", envioConceptointParaEstadoCod=" +envioConcepto+					 			
				"]";
	}
}

